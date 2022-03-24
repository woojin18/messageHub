<template>
  <div v-if="rcsTemplateSenderPopOpen" @click.self="fnClose" class="modalStyle" tabindex="-1" role="dialog" aria-hidden="true">

		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">								
					
					<div v-if="sendData.senderType=='LMS' || sendData.senderType=='MMS'" class="of_h">
						<div class="float-left" style="width:25%"><h5>제목</h5></div>
						<div class="float-right" style="width:75%">
							<input v-model="senderPopData.senderTitle" type="text" class="inputStyle" placeholder="LMS 문자로 대체발송 될 제목입력" title="제목 입력란">
						</div>
					</div>

					<div class="of_h consolMarginTop">
						<div class="float-left" style="width:25%">
              <h5>내용</h5>
              <span class="float-left color3 mt5">
                광고성 메시지 및 <br>무료수신거부의 내용이 <br>내용제한에 포함됩니다.
              </span>
              <!-- <a class="btnStyle1 backBlack mt10"
                title="단축 URL+"
                data-toggle="modal"
                data-target="#shortened_URL"
              >단축 URL+</a> 
              <i class="fas fa-question-circle toolTip ml5">
                <span class="toolTipText" style="width:250px">발송된 메시지의 단축URL+를 고객들이 클릭 해 보았는지 알 수 있도록 지원합니다.</span>
              </i> -->
            </div>
						<div class="float-right" style="width:75%">
							<textarea @input="fnSetCheckTextCnt" v-model="senderPopData.senderContents" class="textareaStyle height120" :placeholder="preText" ></textarea>
              <strong class="letter">({{msgCurrByte | formatComma}} / {{msgLimitByte | formatComma}})</strong>
						</div>
					</div>

          <div v-if="sendData.senderType=='MMS'" class="of_h">
						<div class="float-left" style="width:25%"><h5>이미지</h5></div>
            <div class="of_h float-right" style="width:75%">
              <a @click="fnOpenImageManagePopUp" class="btnStyle1 backLightGray" title="메시지 내용 이미지선택">이미지선택</a>
              <ul class="float-right attachList" style="width:68%; padding:5px 15px; height:30px;">
                <li><a @click="fnDelImg">{{shortImgUrl}} <i v-if="!fnIsEmpty(senderPopData.senderImgUrl)" class="fal fa-times"></i></a></li>
              </ul>
            </div>
					</div>
					
					<div class="text-center mt20">
						<a @click.prevent="fnSaveAdd" href="#self" class="btnStyle1 backBlack" data-dismiss="modal" title="입력">입력</a>
						<a @click.prevent="fnClose" href="#self" class="btnStyle1 backWhite ml20" data-dismiss="modal" title="닫기">닫기</a>						
					</div>

				</div>
			</div>
		</div>

    <ImageManagePopUp @img-callback="fnCallbackImgInfo" :imgMngOpen.sync="imgMngOpen" :useCh="useCh" ref="imgMngPopup"></ImageManagePopUp>

    <shortenedUrlListPopup @btnSelect="btnSelect" />
    <shortenedUrlAddPopup/>
	</div>
</template>

<script>
import confirm from "@/modules/commonUtil/service/confirm.js";
import ImageManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";

import shortenedUrlListPopup from "@/modules/urlInfo/components/shortenedUrlListPopup"
import shortenedUrlAddPopup from "@/modules/urlInfo/components/shortenedUrlAddPopup"

export default {
  name: "rcsSenderPop",
  components : {
    ImageManagePopUp,
    shortenedUrlListPopup,
    shortenedUrlAddPopup,
  },
  props : {
    sendData: {
        type: Object,
        require: true
    },
    rcsTemplateSenderPopOpen: {
      type: Boolean,
      require: true,
      default: false,
    }
  },
  watch: {
    rcsTemplateSenderPopOpen(val){
      if(val){
        this.fnInit();
        //this.fnSetCheckTextCnt();
      }
    }
  },
  data() {
    return {
      preText : "변수로 설정하고자 하는 내용을 #{ }표시로 작성해 주십시오. 예) 이름과 출금일을 변수 설정: 예) #{고객}님 #{YYMMDD} 출금 예정입니다.",
      imgMngOpen : false,
      shortImgUrl : "",
      useCh : "MMS",
      msgCurrByte : 0,
      msgLimitByte : 0,
      senderPopData : {
        senderTitle : "",
        senderContents : "",
        senderImgUrl : "",
        senderFileId : "",
      }
    }
  },
  methods: {
    // 초기세팅
    fnInit() {
      var senderType = this.sendData.senderType;
      var adYn = this.sendData.adYn;
      var freeReceiveNum = this.sendData.freeReceiveNum;

      // 최대 글자수 세팅
      if(senderType == "SMS") {
        this.msgLimitByte = 90;
      } else if(senderType == "LMS") {
        this.msgLimitByte = 1000;
      } else if(senderType == "MMS") {
        this.msgLimitByte = 2000;
      }
      
      // 데이터 세팅
      if(adYn == "no") {
        this.senderPopData.senderTitle = this.sendData.callbackTitle;
      } else {
        if(this.sendData.callbackTitle == "" || this.sendData.callbackTitle == null) {
          if(this.sendData.callbackContents == "" || this.sendData.callbackConents == null) {
            this.senderPopData.senderTitle = "(광고)";
          } else {
            this.senderPopData.senderTitle = "";
          }
        } else {
          this.senderPopData.senderTitle = this.sendData.callbackTitle;
        }
      }
      if(senderType == "SMS") {
        if(this.sendData.callbackContents == "" || this.sendData.callbackTitle == null) {
          this.senderPopData.senderContents = this.sendData.callbackContents;
        } else {
          if("광고".indexOf(this.sendData.callbackContents) != -1) {
            this.senderPopData.senderContents = this.sendData.callbackContents;
          } else {
            this.senderPopData.senderContents = this.sendData.callbackContents.replace("(광고)", "");
          }
        }
      } else {
        this.senderPopData.senderContents = this.sendData.callbackContents;
      }
      
      this.senderPopData.senderImgUrl = this.sendData.callbackImgUrl;
      this.senderPopData.senderFileId = this.sendData.callbackFileId;

      // 현재 글자수 세팅
      if(adYn == "no") {
        if(freeReceiveNum == "") {
          this.msgCurrByte = this.getByte(this.senderPopData.senderContents);
        } else {
          this.msgCurrByte = this.getByte("무료수신거부 : " + freeReceiveNum + this.senderPopData.senderContents);
        }
      } else {
        if(senderType == "SMS") {
          this.msgCurrByte = this.getByte("(광고)" + "무료수신거부 : " + freeReceiveNum + this.senderPopData.senderContents);
        } else {
          this.msgCurrByte = this.getByte("무료수신거부 : " + freeReceiveNum + this.senderPopData.senderContents);
        }
      }
    },
    fnSetCheckTextCnt() {
      var senderType = this.sendData.senderType;
      var freeReceiveNum = this.sendData.freeReceiveNum;
      var adYn = this.sendData.adYn;
      // 현재 글자수 세팅
      if(adYn == "no") {
        if(freeReceiveNum == "") {
          this.msgCurrByte = this.getByte(this.senderPopData.senderContents);
        } else {
          this.msgCurrByte = this.getByte("무료수신거부 : " + freeReceiveNum + this.senderPopData.senderContents);
        }
      } else {
        if(senderType == "SMS") {
          this.msgCurrByte = this.getByte("(광고)" + "무료수신거부 : " + freeReceiveNum + this.senderPopData.senderContents);
        } else {
          this.msgCurrByte = this.getByte("무료수신거부 : " + freeReceiveNum + this.senderPopData.senderContents);
        }
      }
    },
    // 저장
    fnSaveAdd(){
      if(this.msgCurrByte > this.msgLimitByte) {
        const alertMsg = '내용이 '+this.msgLimitByte+'byte를 넘지 않아야됩니다.\n(현재 : '+this.msgCurrByte+'byte)';
        confirm.fnAlert("RCS 템플릿", alertMsg);
        return false;
      }

      var senderType = this.sendData.senderType;
      // var adYn = this.sendData.adYn;
      if(senderType == "SMS") {
        if(this.senderPopData.senderContents == "") {
          confirm.fnAlert("RCS 템플릿", "내용을 입력해 주세요.");
          return false;
        }
      } else { 
        if(this.senderPopData.senderTitle == "") {
          confirm.fnAlert("RCS 템플릿", "제목을 입력해 주세요.");
          return false;
        }
        if(this.senderPopData.senderContents == "") {
          confirm.fnAlert("RCS 템플릿", "내용을 입력해 주세요.");
          return false;
        }

      }

      this.$parent.fnSenderTypeSet(this.senderPopData);
      this.fnClose();
    },

    // 팝업 닫기
    fnClose(){
      this.senderPopData.senderTitle = "";
      this.senderPopData.senderContents = "";
      this.senderPopData.senderImgUrl = "";
      this.senderPopData.senderFileId = "";
      this.$emit('update:rcsTemplateSenderPopOpen', false);
    },

    fnOpenImageManagePopUp(){
      this.$refs.imgMngPopup.fnSearch();
      this.imgMngOpen = !this.imgMngOpen;
    },

    fnDelImg(){
      this.shortImgUrl = '';
      this.fbInfo.imgUrl = '';
      this.fbInfo.fileId = '';
    },

    //이미지선택 callback
    fnCallbackImgInfo(imgInfo) {
      this.senderPopData.senderImgUrl = imgInfo.chImgUrl;
      this.senderPopData.senderFileId = imgInfo.fileId;
      this.shortImgUrl = this.fnSubString(imgInfo.chImgUrl, 0, 30);
    },

    fnSubString(str, sIdx, length){
      var shortStr = ''
      if(!this.fnIsEmpty(str)){
        shortStr = str.toString();
        if(shortStr.length > length){
          shortStr = shortStr.substring(sIdx, length) + '...  ';
        } else {
          shortStr = shortStr + '  ';
        }
      }
      return shortStr;
    },
    //빈값확인
    fnIsEmpty(str){
      if(str) return false;
      else return true
    },
    getByte(str) {
      return str
        .split('')
        .map(s => s.charCodeAt(0))
        .reduce((prev, c) => (prev + ((c === 10) ? 2 : ((c >> 7) ? 2 : 1))), 0);
    },
    //단축 URL 선택
    btnSelect(shortendUrl){
      if(this.senderPopData && this.senderPopData.senderContents && this.senderPopData.senderContents.length > 0){
        this.senderPopData.senderContents += '\n'
        shortendUrl = this.senderPopData.senderContents + shortendUrl
      }
      
      this.$set(this.senderPopData, 'senderContents', shortendUrl)
    },
  }
}
</script>