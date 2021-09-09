<template>
  <div v-if="frndTalkContsOpen" @click.self="fnClose" class="modalStyle" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          
          <div class="of_h">
            <div class="float-left" style="width:20%"><h5>내용 *</h5></div>
            <div class="float-right" style="width:79%">
              <textarea class="textareaStyle height120" :placeholder="contentAreaPlaceholder" v-model="frndTalkContent" :maxlength="msgLimitLength" @input="fnSetCurrLength"></textarea>
              <strong class="letter">({{msgCurrLength}} / {{msgLimitLength}})</strong><br/>
              <span>(친구톡 최대 1000자, 이미지 사용시 400자, 와이드이미지 76자 입력가능)</span>
            </div>
            <!-- #10476 : 친구톡은 enduser가 별도 수신차단 가능하여, 해당 문구 필요없음 -->
            <!-- <p class="txtCaption color5" style="margin-left:21%">광고 메시지 대체 발송 시, 080수신 거부번호가 설정되어 있지 않으면 대체 발송 실패할 수 있습니다.</p> -->
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
  name: "frndTalkContentsPopup",
  props: {
    useCh: String,
    frndTalkContsOpen: {
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
        return '친구톡 발송 내용';
      }
    },
  },
  data() {
    return {
      msgCurrLength: 0,
      msgLimitLength: 1000,
      frndTalkContent: '',
      contentAreaPlaceholder: '변수로 설정하고자 하는 내용을 #{ }표시로 작성해 주십시오.\n:예) 이름과 출금일을 변수 설정\n:예) #{name}님 #{yyyymmdd} 출금 예정입니다.',
    }
  },
  watch: {
    frndTalkContsOpen(val){
      if(val) this.fnSetFrndTalkInfo();
    }
  },
  methods: {
    fnGetLimitLength(msgLimitLength){
      this.msgLimitLength = msgLimitLength;
    },
    fnSetCurrLength(){
      let body = this.$gfnCommonUtils.defaultIfEmpty(this.frndTalkContent, '');
      this.msgCurrLength = body.length;
    },
    fnSetFrndTalkInfo(){
      this.frndTalkContent = this.$gfnCommonUtils.defaultIfEmpty(this.sendData.frndTalkContent, '');
      this.fnSetCurrLength();
    },
    //입력정보 callback
    fnCallbackInputData(){
      if(!this.frndTalkContent){
        confirm.fnAlert(this.componentsTitle, '내용을 선택해주세요.');
        return;
      }
      
      this.$parent.fnSetFrndTalkInfo(this.$data);
      this.fnClose();
    },
    //팝업 닫기
    fnClose(){
      this.fnResetData();
      this.$emit('update:frndTalkContsOpen', false)
    },
    fnResetData(){
      Object.assign(this.$data, this.$options.data());
    }
  }
}
</script>