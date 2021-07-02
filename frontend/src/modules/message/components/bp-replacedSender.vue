<template>
  <div v-if="rplcSendOpen" @click.self="fnClose" class="modalStyle" id="send" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">

          <div class="of_h">
            <div class="float-left" style="width:32%"><h5>대체발송 발신번호</h5></div>
            <select v-model="fbInfo.callback" class="selectStyle2 float-right" style="width:66%">
              <option v-for="info in callbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
            </select>
          </div>

          <div v-if="msgKind == 'A'" class="of_h consolMarginTop">
            <div class="float-left" style="width:32%"><h5>광고성메시지 수신거부번호</h5></div>
            <div class="float-right" style="width:66%">
              <input type="text" class="inputStyle" title="광고성메시지 수신거부번호 입력란" v-model="fbInfo.rcvblcNumber" maxlength="20">
            </div>
          </div>

          <div v-if="fbInfo.ch != 'SMS'" class="of_h consolMarginTop">
            <div class="float-left" style="width:32%"><h5>제목</h5></div>
            <div class="float-right" style="width:66%">
              <input type="text" class="inputStyle" title="제목 입력란" v-model="fbInfo.title">
            </div>
          </div>

          <div class="of_h consolMarginTop">
            <div class="float-left" style="width:32%"><h5>내용</h5></div>
            <div class="float-right" style="width:66%">
              <textarea class="textareaStyle height120" :placeholder="contentAreaPlaceholder" v-model="fbInfo.msg"></textarea>
            </div>
          </div>

          <div v-if="fbInfo.ch == 'MMS'" class="of_h consolMarginTop">
            <div class="float-left" style="width:32%"><h5>이미지</h5></div>
            <div class="of_h float-right" style="width:66%">
              <a @click="fnOpenImageManagePopUp" class="btnStyle1 backLightGray" title="메시지 내용 이미지선택">이미지선택</a>
              <ul class="float-right attachList" style="width:68%; padding:5px 15px; height:30px;">
                <li><a @click="fnDelImg">{{shortImgUrl}} <i v-if="!fnIsEmpty(fbInfo.imgUrl)" class="fal fa-times"></i></a></li>
              </ul>
            </div>
          </div>

          <div class="text-center mt20">
            <a @click="fnCallbackInputData" class="btnStyle1 backBlack" title="입력">입력</a>
            <a @click="fnClose" class="btnStyle1 backWhite" data-dismiss="modal" title="닫기">닫기</a>
          </div>

        </div>
      </div>
    </div>

    <ImageManagePopUp @img-callback="fnCallbackImgInfo" :imgMngOpen.sync="imgMngOpen" :useCh="useCh" ref="imgMngPopup"></ImageManagePopUp>

  </div>
</template>

<script>
import MessageApi from "@/modules/message/service/messageApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";
import ImageManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";

export default {
  name: "replacedSenderPopup",
  components : {
    ImageManagePopUp
  },
  props: {
    rplcSendOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '대체발송';
      }
    },
  },
  data() {
    return {
      imgMngOpen : false,
      useCh : 'MMS',
      callbackList: [],
      msgKind : 'A',
      fbInfo: {
        callback:'',
        ch:'',
        title:'',
        msg:'',
        rcvblcNumber:'',
        fileId:'',
        imgUrl:'',
      },
      shortImgUrl:'',
      contentAreaPlaceholder: '변수로 설정하고자 하는 내용을 #{ }표시로 작성해 주십시오.\n:예) 이름과 출금일을 변수 설정\n:예) #{name}님 #{yyyymmdd} 출금 예정입니다.',
    }
  },
  watch: {
    rplcSendOpen(val){
      if(val) this.fnSelectCallbackList();
    }
  },
  methods: {
    fnDelImg(){
      this.shortImgUrl = '';
      this.fbInfo.imgUrl = '';
      this.fbInfo.fileId = '';
    },
    //대체발송 정보 Set
    fnSetfbInfo(sendData){
      this.msgKind = sendData.msgKind;
      this.fbInfo = Object.assign({}, sendData.fbInfo);
    },
    //입력정보 callback
    fnCallbackInputData(){
      if(!this.fbInfo.callback){
        confirm.fnAlert(this.componentsTitle, '대체발송 발신번호를 선택해주세요.');
        return;
      }
      if(this.msgKind == 'A' && !this.fbInfo.rcvblcNumber){
        confirm.fnAlert(this.componentsTitle, '광고성메시지 수신거부번호를 입력해주세요.');
        return false;
      }
      if(this.fbInfo.ch != 'SMS' && !this.fbInfo.title){
        confirm.fnAlert(this.componentsTitle, '제목을 입력해주세요.');
        return false;
      }
      if(!this.fbInfo.msg){
        confirm.fnAlert(this.componentsTitle, '내용을 입력해주세요.');
        return;
      }

      let msgLimitByte;
      const totalMsg = this.fbInfo.title + this.fbInfo.msg + '\n' + this.fbInfo.rcvblcNumber;
      const totByte = this.getByte(totalMsg);

      if(this.fbInfo.ch == 'SMS'){
        msgLimitByte = 80;
      } else if(this.fbInfo.ch == 'LMS'){
        msgLimitByte = 1000;
      } else if(this.fbInfo.ch == 'MMS'){
        msgLimitByte = 2000;
      }

      if(msgLimitByte < totByte){
        const alertMsg = (this.fbInfo.ch == 'SMS' ? '' : '제목 + ') + '내용 + 광고성메시지 수신거부번호가 '+msgLimitByte+'를 넘지 않아야됩니다.\n(현재 : '+totByte+'byte)';
        confirm.fnAlert(this.componentsTitle, alertMsg);
        return;
      }
      this.$parent.fnCallbackFbInfo(this.fbInfo);
      this.fnClose();
    },
    //발신번호 리스트 조회
    async fnSelectCallbackList(){
      var params = {};
      await MessageApi.selectCallbackList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.callbackList = result.data;
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    //팝업 닫기
    fnClose(){
      //입력 데이터 넘기기 필요
      this.$emit('update:rplcSendOpen', false)
    },
    //get 문자열 byte
    getByte(str) {
      return str
        .split('')
        .map(s => s.charCodeAt(0))
        .reduce((prev, c) => (prev + ((c === 10) ? 2 : ((c >> 7) ? 2 : 1))), 0);
    },
    //문자열 byte cut
    sliceByByte(str, maxByte, i, b, c) {
      for(let b=i=0;b<maxByte;i++) {
        c=str.charCodeAt(i);
        b+=c>>7?2:1;
      }
      return str.substring(0,i);
    },
    fnOpenImageManagePopUp(){
      this.$refs.imgMngPopup.fnSearch();
      this.imgMngOpen = !this.imgMngOpen;
    },
    //이미지선택 callback
    fnCallbackImgInfo(imgInfo) {
      this.fbInfo.imgUrl = imgInfo.chImgUrl;
      this.fbInfo.fileId = imgInfo.fileId;
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
    }
  }
}
</script>