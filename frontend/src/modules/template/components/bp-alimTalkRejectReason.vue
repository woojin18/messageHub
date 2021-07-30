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
                <p><pre>{{rejectReson | unescapeXss}}</pre></p>
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
import confirm from "@/modules/commonUtil/service/confirm.js";
import templateApi from "@/modules/template/service/templateApi.js";

export default {
  name: "alimTalkRejectReason",
  props: {
    alimTalkRejectReasonOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    tmpltKey: {
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
  data() {
    return {
      rejectReson : '',
    }
  },
  watch: {
    alimTalkRejectReasonOpen(val){
      if(val){
        this.fnSelectKkoTmpltRejResn();
      }
    }
  },
  methods: {
    async fnSelectKkoTmpltRejResn(){
      var params = {tmpltKey : this.tmpltKey};
      await templateApi.selectKkoTmpltRejResn(params).then(response =>{
        const result = response.data;
        if(result.success) {
          console.log('result3 ==== >>>> ', result);
          if(result.data.rejectReson){
            this.rejectReson = result.data.rejectReson;
          }
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnClose(){
      this.$emit('update:alimTalkRejectReasonOpen', false);
    }
  }
}
</script>