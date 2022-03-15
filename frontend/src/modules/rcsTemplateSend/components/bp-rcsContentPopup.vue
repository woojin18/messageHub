<template>
  <div class="modal modalStyle" id="contentPop" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>						
            <div v-if="templateRadioBtn!='text' && templateRadioBtn!='SS000000' && templateRadioBtn!='SL000000'" class="of_h consolMarginTop">
							<div class="float-left" style="width:15%"><h5>이미지선택</h5></div>
							<div class="float-left" style="width:85%">
                <div class="float-left" style="width:24%"><a @click="fnOpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지 선택">이미지선택</a></div>
                  <ul class="float-right attachList" style="width: 61%; margin: 0px 25px 0px 0px;padding: 5px 15px; height: 30px;">
                    <li v-if="imgUrl">
                      <a @click="fnDelImg">{{fnSubString(imgUrl, 0, 40)}} <i class="fal fa-times"></i></a>
                    </li>
                  </ul>
              </div>
						</div>
            <div v-if="templateRadioBtn!='text' && templateRadioBtn!='SS000000'" class="of_h consolMarginTop">
							<div class="float-left" style="width:15%"><h5>제목</h5></div>
							<div class="float-left" style="width:80%">
								<input v-model="title" type="text" class="inputStyle" placeholder="타이틀 입력 (최대 30자)" title="제목 입력란">
							</div>							
						</div>
						<div class="clear consolMarginTop">
							<div class="float-left" style="width:25%">
                <h5>내용</h5>
                <template v-if="templateRadioBtn === 'text'">
                  <a class="btnStyle1 backBlack mt60" 
                    title="단축 URL+" 
                    data-toggle="modal" 
                    data-target="#shortened_URL"
                  >단축 URL+</a>
                  <i class="fas fa-question-circle toolTip ml5"><span class="toolTipText" style="width:250px">발송된 메시지의 단축URL+를 고객들이 클릭 해 보았는지 알 수 있도록 지원합니다.</span></i>
                </template>
                <p 
                  v-else
                  class="color3">버튼이 존재하는 RCS<br>상품은 단축URL+이 [버튼<br>입력]에 존재합니다.</p>
              </div>
							<div class="float-left" style="width:75%">
								<textarea 
                  v-model="contents" 
                  class="textareaStyle height120" 
                  :placeholder="holder"
                ></textarea>
								<p class="color3">
                  <i class="far fa-info-circle"></i> {{textCnt | formatComma}}자 입력 / 변수 포함 최대 {{txtMaxLength | formatComma}}글자 <i class="fas fa-question-circle toolTip"></i>
                </p>
							</div>							
						</div>
					</div>	
					<div class="text-center mt20">
						<a @click.prevent="fnAdd" href="#self" class="btnStyle2 backBlack" title="입력">입력</a>
						<a @click.prevent="fnClose" href="#self" class="btnStyle2 backWhite ml20" data-dismiss="modal" title="닫기">닫기</a>																
					</div>
				</div>				
			</div>
		</div>

    <ImageManagePopUp @img-callback="fnCallbackImgInfo" :imgMngOpen.sync="imgMngOpen" useCh="RCS" ref="imgMngPopup"></ImageManagePopUp>

    <shortenedUrlListPopup @btnSelect="btnSelect" />
    <shortenedUrlAddPopup/>
	</div>
</template>

<script>
// import rcsTemplateSendApi from "@/modules/rcsTemplateSend/service/api.js";
import ImageManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import confirm from "@/modules/commonUtil/service/confirm.js";

import shortenedUrlListPopup from "@/modules/urlInfo/components/shortenedUrlListPopup"
import shortenedUrlAddPopup from "@/modules/urlInfo/components/shortenedUrlAddPopup"

export default {
  name: "rcsContentPop",
  components : {
    ImageManagePopUp,
    shortenedUrlListPopup,
    shortenedUrlAddPopup,
  },
  props : {
        templateRadioBtn: {
            type: String,
            require: true,
            default: "",
        },
        contentPopCnt: {
            type: Number,
            default: 0
        },
        sendData: {
            type: Object
        },
        dataSet: {
            type: Boolean,
            default: false
        }
  },
  data() {
    return { 
      holder : "변수로 설정하고자 하는 내용을 {{ }}표시로 작성해 주십시오. 예) 이름과 출금일을 변수 설정: 예) {{고객}}님 {{YYMMDD}} 출금 예정입니다.",  // 내용 holder
      textCnt : 0,    // 글자 수 체크
      title: "",      // 제목
      contents : "",  // 내용
      imgMngOpen : false, // 이미지 팝업
      imgUrl : "",      // 이미지 URL
      fileId : "",      // FILE ID
      wideImgYn : "N",
      txtMaxLength : 0     // 내용 입력시 최대 글자수
    }
  },
  watch : {
      contents : function(newVal) {
        this.textCnt = newVal.length;
      },
      templateRadioBtn(){
        if(this.templateRadioBtn == 'text'){    // 텍스트 미승인형
          this.txtMaxLength = 90;
        } else if(this.templateRadioBtn == 'SS000000'){    // sms
          this.txtMaxLength = 100;
        } else {    // lms, 세로형(medium, tall), 캐러셀(medium, tall)
          this.txtMaxLength = 1300;
        }
      }
  },

  methods: {
    // 팝업 기초 세팅 
    fnInit(cnt) {
      let vm = this;
      vm.contentPopCnt = cnt;
      if(vm.dataSet) {
        vm.fnSetData();
      }
    },

    fnSetData() {
      var sendData = this.sendData;
      var contentPopCnt = this.contentPopCnt;

      // contentPopCnt가 0보다 큰 경우 carousel Type
      if(contentPopCnt > 0) {
        this.title = sendData.carouselObj.textTitle[contentPopCnt-1];
        this.contents = sendData.carouselObj.textContents[contentPopCnt-1];
        this.imgUrl = sendData.carouselObj.imgUrl[contentPopCnt-1];
        this.fileId = sendData.carouselObj.fileId[contentPopCnt-1];

      } else {
        this.title = sendData.textTitle;
        this.contents = sendData.textContents;
        this.imgUrl = sendData.imgUrl;
        this.fileId = sendData.fileId;
      }

    },

    // 업 닫기
    fnClose(){
      //데이터 초기화
      this.textCnt = 0;
      this.title = "";
      this.contents = "";
      this.imgUrl = "";
      this.fileId = "";
      this.wideImgYn = "N"; 

      jQuery("#contentPop").modal("hide");
    },

    // 입력 버튼
    fnAdd() {
      var vm = this;
      var templateRadioBtn = vm.templateRadioBtn;
      var contents = vm.contents;
      var textCnt = vm.textCnt;
      var txtMaxLength = vm.txtMaxLength;
      var title = vm.title;
      // var fileId = vm.fileId;
      var params = {
        "title" : vm.title,
        "contents" : vm.contents,
        "imgUrl" : vm.imgUrl,
        "fileId" : vm.fileId,
        "wideImgYn" : vm.wideImgYn
      };

      var titleTf = false;
      // var imgTf = false;

      // validation
      if(templateRadioBtn == "SMwThM00" || templateRadioBtn == "SMwThT00" ||
        templateRadioBtn == "carouselSmall" || templateRadioBtn == "carouselMedium" ) {
          titleTf = true;
          // imgTf = true;
        }
      if(templateRadioBtn == "SL000000") {
        titleTf = true;
      }

      if(contents == "") {
        confirm.fnAlert("내용을 입력해주세요.","");
      } else if (textCnt > txtMaxLength) {
        confirm.fnAlert("내용을 "+txtMaxLength+"자 이내로 입력해주세요.","");
      } else if(titleTf) {
        if(title == "") {
          confirm.fnAlert("제목을 입력해주세요.", "");
        } else {
          this.$emit('fnAddResult', params);
          this.fnClose();
        }
      } else {
        this.$emit('fnAddResult', params);
        this.fnClose();
      }
    },

    // IMG 팝업
    fnOpenImageManagePopUp(){
      this.$refs.imgMngPopup.fnSearch();
      this.imgMngOpen = !this.imgMngOpen;
    },

    fnCallbackImgInfo(imgInfo) {
      this.imgUrl = imgInfo.chImgUrl;
      this.fileId = imgInfo.fileId;
      this.wideImgYn = imgInfo.wideImgYn;
    },

    fnDelImg() {
      this.imgUrl = '';
      this.fileId = '';
      this.wideImgYn = 'N';
    },

    fnSubString(str, sIdx, length){
      let shortStr = ''
      if(!this.$gfnCommonUtils.isEmpty(str)){
        shortStr = str.toString();
        if(shortStr.length > length){
          shortStr = shortStr.substring(sIdx, length) + '...  ';
        } else {
          shortStr = shortStr + '  ';
        }
      }
      return shortStr;
    },
    //단축 URL 선택
    btnSelect(shortendUrl){
      if(this.contents.length > 0)
        this.contents += '\n'
      
      this.contents += shortendUrl
    },
  }
}
</script>