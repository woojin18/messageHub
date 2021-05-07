<template>
  <div v-if="smsContsOpen" @click.self="fnClose" class="modalStyle" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          
          <div v-if="sendData.senderType == 'MMS'" class="of_h consolMarginTop">
            <div class="float-left" style="width:34%"><h5>제목 *</h5></div>
            <div class="float-right" style="width:66%">
              <input type="text" class="inputStyle" title="제목 입력란" v-model="smsTitle" maxlength="45">
            </div>
          </div>

          <div class="of_h consolMarginTop">
            <div class="float-left" style="width:34%"><h5>내용 *</h5></div>
            <div class="float-right" style="width:66%">
              <textarea class="textareaStyle height120" :placeholder="contentAreaPlaceholder" v-model="smsContent" maxlength="2000"></textarea>
            </div>
          </div>
          <div v-if="sendData.msgKind == 'A'" class="of_h consolMarginTop">
            <div class="float-left" style="width:34%"><h5>광고성메시지 수신거부번호 *</h5></div>
            <div class="float-right" style="width:66%">
              <input type="text" class="inputStyle" title="광고성메시지 수신거부번호 입력란" v-model="rcvblcNumber" maxlength="10">
            </div>
          </div>
          <div class="text-center mt20">
            <a @click="fnCallbackInputData" class="btnStyle2 backBlack" title="입력">입력</a>
            <a @click="fnClose" class="btnStyle2 backWhite" data-dismiss="modal" title="닫기">닫기</a>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: "smsContentsPopup",
  props: {
    smsContsOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    sendData : {
      type: Object,
      require: true,
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return 'SMS 발송 내용';
      }
    },
  },
  data() {
    return {
      smsTitle: '',
      smsContent : '',
      rcvblcNumber: '',
      contentAreaPlaceholder: '변수로 설정하고자 하는 내용을 {{ }}표시로 작성해 주십시오.\n:예) 이름과 출금일을 변수 설정:예) {{name}}님 {{yyyymmdd}} 출금 예정입니다.',
    }
  },
  watch: {
    smsContsOpen(val){
      if(val) this.fnSetPushInfo();
    }
  },
  methods: {
    //입력정보 callback
    fnCallbackInputData(){
      if(this.sendData.senderType == 'MMS' && !this.smsTitle){
        confirm.fnAlert(this.componentsTitle, '제목을 입력해주세요.');
        return false;
      }
      if(!this.smsContent){
        confirm.fnAlert(this.componentsTitle, '내용을 입력해주세요.');
        return false;
      }
      if(this.sendData.msgKind == 'A' && !this.rcvblcNumber){
        confirm.fnAlert(this.componentsTitle, '광고성메시지 수신거부번호를 입력해주세요.');
        return false;
      }
      this.$parent.fnSetSmsInfo(this.$data);
      this.fnClose();
    },
    //초기 정보 Set
    fnSetPushInfo(){
      this.smsTitle = this.$gfnCommonUtils.defaultIfEmpty(this.sendData.smsTitle, '');
      this.smsContent = this.$gfnCommonUtils.defaultIfEmpty(this.sendData.smsContent, '');
      this.rcvblcNumber = this.$gfnCommonUtils.defaultIfEmpty(this.sendData.rcvblcNumber, '');
    },
    //팝업 닫기
    fnClose(){
      this.$emit('update:smsContsOpen', false)
    }
  }
}
</script>
<style lang="scss">
$module: 'modalStyle';
.#{$module} {
  // This is modal bg
  background-color: rgba(0,0,0,.7);
  top: 0; right: 0; bottom: 0; left: 0;
  position: fixed;
  overflow: auto;
  margin: 0;
  z-index: 9999;
}
</style>