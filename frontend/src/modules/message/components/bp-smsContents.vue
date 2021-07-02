<template>
  <div v-if="smsContsOpen" @click.self="fnClose" class="modalStyle" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">

          <div class="of_h consolMarginTop">
            <div class="float-left" style="width:34%"><h5>발신번호 *</h5></div>
            <div class="float-right" style="width:66%">
              <select v-model="callback" class="selectStyle2 float-right" style="width:100%">
                <option value="">선택</option>
                <option v-for="info in callbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
              </select>
            </div>
          </div>
          
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
import messageApi from "@/modules/message/service/messageApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: "smsContentsPopup",
  props: {
    smsContsOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    isSpecialBusi: {
      type: Boolean,
      require: false,
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
      callback: '',
      smsTitle: '',
      smsContent : '',
      rcvblcNumber: '',
      contentAreaPlaceholder: '변수로 설정하고자 하는 내용을 #{ }표시로 작성해 주십시오.\n:예) 이름과 출금일을 변수 설정\n:예) #{name}님 #{yyyymmdd} 출금 예정입니다.',
      callbackList: [],
    }
  },
  watch: {
    smsContsOpen(val){
      if(val){
        this.fnSetPushInfo();
        this.fnSelectCallbackList();
      }
    }
  },
  methods: {
    //입력정보 callback
    fnCallbackInputData(){
      if(!this.callback && !this.isSpecialBusi){
        confirm.fnAlert(this.componentsTitle, '발신번호를 선택해주세요.');
        return;
      }
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
      this.callback = this.$gfnCommonUtils.defaultIfEmpty(this.sendData.callback, '');
      this.smsTitle = this.$gfnCommonUtils.defaultIfEmpty(this.sendData.smsTitle, '');
      this.smsContent = this.$gfnCommonUtils.defaultIfEmpty(this.sendData.smsContent, '');
      this.rcvblcNumber = this.$gfnCommonUtils.defaultIfEmpty(this.sendData.rcvblcNumber, '');
    },
    //팝업 닫기
    fnClose(){
      this.$emit('update:smsContsOpen', false)
    },
    //발신번호 리스트 조회
    async fnSelectCallbackList(){
      var params = {};
      await messageApi.selectCallbackList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.callbackList = result.data;
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
  }
}
</script>