<template>
    <div v-if="pushContsOpen" @click.self="fnClose" class="modalStyle" id="contents" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          <div class="of_h">
            <div class="float-left" style="width:20%"><h5>제목</h5></div>
            <div class="float-right" style="width:79%">
              <input type="text" class="inputStyle" title="제목 입력란" v-model="pushTitle" maxlength="40">
            </div>
          </div>
          <div class="of_h mt7">
            <div class="float-left" style="width:20%"><h5>내용 *</h5></div>
            <div class="float-right" style="width:79%">
              <textarea class="textareaStyle height120" :placeholder="contentAreaPlaceholder" v-model="pushContent" maxlength="2000"></textarea>
            </div>
            <div v-if="sendData.msgKind == 'A'">
              <p class="txtCaption color5 lc-1 mt7" style="margin-left:21%">광고성 메시지 발송 시, 자동으로 광고가 표기 됩니다.</p>
              <div class="float-right mt10" style="width:79%">
                <input type="text" class="inputStyle" title="내용 입력란" placeholder="" v-model="rcvblcNumber">
              </div>
              <p class="txtCaption color5 lc-1 mt7" style="margin-left:21%">PUSH 수신거부 방법을 입력해주세요. PUSH 메시지에 (수신거부:거부 방법)이 포함됩니다.</p>
            </div>
          </div>
          <div class="of_h mt7">
            <div class="float-left" style="width:20%"><h5>부가정보(EXT)</h5></div>
            <div class="float-right" style="width:79%">
              <input type="text" class="inputStyle" title="부가정보(EXT) 입력란" v-model="adtnInfo" maxlength="45">
            </div>
          </div>
          <div class="text-center mt20">
            <a @click="fnCallbackInputData" class="btnStyle1 backBlack" title="입력">입력</a>
            <a @click="fnClose" class="btnStyle1 backWhite" title="닫기">닫기</a>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: "pushContentsPopup",
  props: {
    useCh: String,
    pushContsOpen: {
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
        return 'PUSH 발송 내용';
      }
    },
  },
  data() {
    return {
      pushTitle: '',
      pushContent : '',
      rcvblcNumber: '',
      adtnInfo: '',
      contentAreaPlaceholder: '변수로 설정하고자 하는 내용을 #{ }표시로 작성해 주십시오.\n:예) 이름과 출금일을 변수 설정\n:예) #{name}님 #{yyyymmdd} 출금 예정입니다.',
    }
  },
  watch: {
    pushContsOpen(val){
      if(val) this.fnSetPushInfo();
    }
  },
  methods: {
    //입력정보 callback
    fnCallbackInputData(){
      if(this.sendData.msgKind == 'A' && !this.rcvblcNumber){
        confirm.fnAlert(this.componentsTitle, 'PUSH 수신거부 방법을 입력해주세요.');
        return false;
      }
      if(!this.pushContent){
        confirm.fnAlert(this.componentsTitle, '내용을 입력해주세요.');
        return false;
      }
      this.$parent.fnSetPushInfo(this.pushTitle, this.pushContent, this.rcvblcNumber, this.adtnInfo);
      this.fnClose();
    },
    //초기 정보 Set
    fnSetPushInfo(){
      this.pushTitle = this.sendData.pushTitle;
      this.pushContent = this.sendData.pushContent;
      this.rcvblcNumber = this.sendData.rcvblcNumber;
      this.adtnInfo = this.sendData.adtnInfo;
    },
    //팝업 닫기
    fnClose(){
      this.$emit('update:pushContsOpen', false)
    }
  }
}
</script>