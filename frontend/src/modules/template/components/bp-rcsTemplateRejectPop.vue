<template>
  <div v-if="rcsTemplateRejectPopOpen" class="modalStyle" id="test" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:600px">
      <div class="modal-content">
        <div class="modal-body">
          <div>
            <h2>RCS 템플릿 반려 사유</h2>
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
  name: "rcsTemplateRejectPop",
  props: {
    rcsTemplateRejectPopOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    msgId: {
      type: String,
      require: true
    },
    
  },
  data() {
    return {
      rejectReson : '',
    }
  },
  watch: {
    rcsTemplateRejectPopOpen(val){
      if(val){
        this.fnSearchReject();
      }
    }
  },
  methods: {
    async fnSearchReject(){
      var params = {msgId : this.msgId};
      await templateApi.selectRcsTemplateRejectReason(params).then(response =>{
        const result = response.data;
        if(result.success) {
            this.rejectReson = result.data;
        } else {
            confirm.fnAlert("RCS 템플릿", result.message);
        }
      });
    },
    fnClose(){
      this.$emit('update:rcsTemplateRejectPopOpen', false);
    }
  }
}
</script>