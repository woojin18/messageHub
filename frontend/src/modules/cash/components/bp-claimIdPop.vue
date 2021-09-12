<template>
  <div>  
    <div class="modal modalStyle" id="createClaimIdPop" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-body">
            <div class="of_h">
              <h2>청구ID 생성</h2>
              <hr>						
              <p class="text-center">청구ID는 자동생성 됩니다.<br>청구ID를 생성하시겠습니까?</p>
              <div class="text-center mt20">
                <a @click="fnCreateClaimId" class="btnStyle1 backBlack mr5">생성</a>
                <a @click="fnCloseCreateClaimIdPop" class="btnStyle1 backWhite">취소</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="modal modalStyle" id="modClaimIdPop" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-body">
            <div class="of_h">
              <h2>청구ID 수정</h2>
              <hr>						
              <div class="of_h">
                <h5 class="inline-block" style="width:18%">청구ID</h5>
                <select v-model="billId" class="selectStyle2 float-right" style="width:80%">
                  <option value="">청구ID를 선택해 주세요</option>
                  <option v-for="(data, index) in ucubeData" :key="index" :value="data.billId">{{data.a}}</option>
                </select>
              </div>
            </div>
            <div class="text-center mt20">
              <a @click="fnUpdateProjectBillId" class="btnStyle1 backBlack mr5">등록</a>
              <a @click="fnCloseModClaimIdPop" class="btnStyle1 backWhite">취소</a>						
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import cashApi from "@/modules/cash/service/api"
import tokenSvc from '@/common/token-service'
import confirm from "@/modules/commonUtil/service/confirm"

export default {
  name: 'claimIdPop',
  data() {
    return {
      billId: ""
    }
  },
  props: {
    ucubeData: {
      type: Array
    },
    selProjectInfo: {
      type: Object
    },
    popReset: {
      type: Number
    }
  },
  watch: {
    popReset() {
      this.fnReset();
    }
  },
  mounted() {
  },
  methods: {
    fnReset: function() {
      this.billId = this.selProjectInfo.billId;
    },

    fnCreateClaimId: function() {
      var params = {
        "corpId" : tokenSvc.getToken().principal.corpId
      };

      cashApi.insertUbubeInfo(params).then(response => {
        var result = response.data;
        if(result.success) {
          this.$parent.fnSelectUcubeInfo();
          this.fnCloseCreateClaimIdPop();
        } else {
          confirm.fnAlert("", result.message);
        }
      });
    },

    fnUpdateProjectBillId: function() {
      var params = {
        "corpId" : tokenSvc.getToken().principal.corpId,
        "projectId": this.selProjectInfo.selProjectId,
        "billId": this.billId
      };

      console.log(params);
      cashApi.updateProjectBillId(params).then(response => {
        var result = response.data;
        if(result.success) {
          this.$parent.fnSelectProjectInfo();
          this.fnCloseModClaimIdPop();
        } else {
          confirm.fnAlert("", result.message);
        }
      });
    },

    fnCloseCreateClaimIdPop: function() {
      jQuery("#createClaimIdPop").modal("hide");
    },

    fnCloseModClaimIdPop: function() {
      jQuery("#modClaimIdPop").modal("hide");
    }
  }
}
</script>
