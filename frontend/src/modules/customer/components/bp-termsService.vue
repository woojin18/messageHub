<template>
  <div class="modal fade modalStyle" id="termsServiceLayer" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:800px">
      <div class="modal-content">
        <div class="modal-body">
          <div>
            <h2>서비스 이용약관</h2>
            <hr>
            <div class="boardWrite">
              <select class="selectStyle2" style="width:30%" title="이전 약관 보기" v-model="sltSvcTermsId" @change="fnSelectCmUseTermsInfo">
                <option 
                  v-for="(useTermsHistInfo, idx) in useTermsHistList"
                  :key="idx"
                  :value="useTermsHistInfo.svcTermsId"
                >{{useTermsHistInfo.reviseDt}}</option>
              </select>
              <div class="content scroll-y">
                <p v-html="useTermsInfo.termsContent"></p>
              </div>
            </div>
          </div>
          <div class="text-center mt20">
            <a href="#self" class="btnStyle1 backWhite" data-dismiss="modal" title="닫기">닫기</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import confirm from "@/modules/commonUtil/service/confirm.js";
import customereApi from "@/modules/customer/service/customerApi.js";

export default {
  name: "termsService",
  props: {
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '서비스 이용약관';
      }
    },
  },
  data() {
    return {
      useTermsHistList : []
      , useTermsInfo : {}
      , sltSvcTermsId : ''
    }
  },
  methods: {
    async fnSelectCmUseTermsHistList(){
      const params = {
        termsCd : "SVC"
      };
      await customereApi.selectCmUseTermsHistList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          this.useTermsHistList = Object.assign([], result.data);
          if(this.useTermsHistList.length > 0){
            this.sltSvcTermsId = this.useTermsHistList[0].svcTermsId;
            this.fnSelectCmUseTermsInfo();
          }
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    async fnSelectCmUseTermsInfo(){
      const params = {
        svcTermsId : this.sltSvcTermsId
      };
      await customereApi.selectCmUseTermsInfo(params).then(response =>{
        const result = response.data;
        if(result.success) {
          this.useTermsInfo = Object.assign({}, result.data);
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    }

  }
}
</script>