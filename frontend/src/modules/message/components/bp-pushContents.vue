<template>
    <div v-if="pushContsOpen" @click.self="fnClose" class="modalStyle" id="contents" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          <div class="of_h">
            <div class="float-left" style="width:20%"><h4 class="font-normal font-size14 mt15">제목</h4></div>
            <div class="float-right" style="width:79%">
              <input type="text" class="inputStyle" title="제목 입력란" v-model="pushTitle" maxlength="45">
            </div>
          </div>
          <div class="of_h mt15">
            <div class="float-left" style="width:20%"><h4 class="font-normal font-size14 mt15">내용 *</h4></div>
            <div class="float-right" style="width:79%">
              <textarea class="textareaStyle height120" placeholder="" v-model="pushContent" maxlength="512"></textarea>
            </div>
            <div v-if="sendData.msgKind == 'A'">
              <p class="font-size12 color5 lc-1 mt5" style="margin-left:21%">광고성 메시지 발송 시, 자동으로 광고가 표기 됩니다.</p>
              <div class="float-right mt10" style="width:79%">
                <input type="text" class="inputStyle" title="내용 입력란" placeholder="" v-model="rcvblcNumber">
              </div>
              <p class="font-size12 color5 lc-1 mt5" style="margin-left:21%">푸시 수신거부 방법을 입력해주세요. 푸시 메시지에 (수신거부:거부 방법)이 포함됩니다.</p>
            </div>
          </div>
          <div class="of_h mt15">
            <div class="float-left" style="width:20%"><h4 class="font-normal font-size14 mt15">부가정보(EXT)</h4></div>
            <div class="float-right" style="width:79%">
              <input type="text" class="inputStyle" title="부가정보(EXT) 입력란" v-model="adtnInfo" maxlength="45">
            </div>
          </div>
          <div class="text-center mt60">
            <a @click="fnCallbackInputData" class="btnStyle3 black font14" title="입력">입력</a>
            <a @click="fnClose" class="btnStyle3 white font14" data-dismiss="modal" title="닫기">닫기</a>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
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
  },
  data() {
    return {
      pushTitle: '',
      pushContent : '',
      rcvblcNumber: '',
      adtnInfo: ''
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
      if(!this.pushContent){
        alert('내용을 입력해주세요.');
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
      //입력 데이터 넘기기 필요
      this.$emit('update:pushContsOpen', false)
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