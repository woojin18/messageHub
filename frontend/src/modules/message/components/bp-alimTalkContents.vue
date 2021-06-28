<template>
  <div v-if="alimTalkContsOpen" @click.self="fnClose" class="modalStyle" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          
          <div class="of_h">
            <div class="float-left" style="width:20%"><h5>내용 *</h5></div>
            <div class="float-right" style="width:79%">
              <textarea class="textareaStyle height120" placeholder=""></textarea>
            </div>
            <p class="color5" style="margin-left:21%">광고 메시지 대체 발송 시, 080수신 거부번호가 설정되어 있지 않으면 대체 발송 실패할 수 있습니다.</p>
          </div>
          <div class="text-center mt20">
            <a href="#" @click.prevent="fnClose" class="btnStyle2 backWhite" data-dismiss="modal" title="닫기">닫기</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "alimTalkContentsPopup",
  props: {
    alimTalkContsOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '알림톡 발송 내용';
      }
    },
  },
  watch: {
    frndTalkContsOpen(val){
      if(val) this.fnSetFrndTalkInfo();
    }
  },
  methods: {
    //팝업 닫기
    fnClose(){
      this.$emit('update:alimTalkContsOpen', false)
    }
  }
}
</script>