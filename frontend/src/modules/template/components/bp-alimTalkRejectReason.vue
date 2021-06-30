<template>
  <div v-if="alimTalkRejectReasonOpen" class="modalStyle" id="test" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:800px">
      <div class="modal-content">
        <div class="modal-body">
          <div>
            <h2>알림톡 반려 사유</h2>
            <hr>
            <div class="boardWrite">
              <div class="content scroll-y">
                <p v-html="$gfnCommonUtils.newLineToBr(rejectReason)"></p>
              </div>
            </div>
          </div>
          <div class="text-center mt20">
            <a @click="fnClose" class="btnStyle1 backWhite" title="닫기">닫기</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "alimTalkRejectReason",
  props: {
    alimTalkRejectReasonOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    rejectReason: {
      type: String,
      require: true
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '알림톡 반려 사유';
      }
    },
  },
  methods: {
    fnClose(){
      this.loopCnt=3;
      this.$emit('update:alimTalkRejectReasonOpen', false)
    }
  }
}
</script>