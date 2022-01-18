<template>
  <div class="modal modalStyle" id="virAccPop" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width: 500px;">
      <div class="modal-content">
        <div class="modal-body">
          <div>
            <h2 class="lc-1">가상계좌 정보</h2>
            <hr>

            <div class="menuBox mb30">
               <p>* 유효기간 내 가상 계좌에 입금이 안되면 신청 정보는 자동으로 삭제됩니다.</p>
            </div>

            <div class="of_h">
                <h5 style="width:30%" class="float-left color000">가상 계좌 번호</h5>
                <h5 style="width:70%" class="float-right color4 word-break-all">{{accountNumber}}</h5>
            </div>
            <div class="of_h">
                <h5 style="width:30%" class="float-left color000">가상 계좌 유효일자</h5>
                <h5 style="width:70%" class="float-right color4 word-break-all">{{dueDate}}</h5>
            </div>
             <div class="of_h">
                <h5 style="width:30%" class="float-left color000">가상 계좌 예금주 명</h5>
                <h5 style="width:70%" class="float-right color4 word-break-all">{{customerName}}</h5>
            </div>
             <div class="of_h">
                <h5 style="width:30%" class="float-left color000">가상 계좌 입금액</h5>
                <h5 style="width:70%" class="float-right color4 word-break-all">{{cashChargAmount | comma}} 원</h5>
            </div>
          </div>
          <div class="text-center mt30">
            <button @click="fnCloseLayer" class="btnStyle3 grey">닫기</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import cashApi from "@/modules/cash/service/api"

export default {
  name: 'virAccPop',
  data() {
    return {
        accountNumber : "",
        customerName : "",
        cashChargAmount : 0,
        dueDate : ""

    }
  },
  props: {
    popOrderId : {
      type: String,
      default: ""
    }
  },
  filters: {
    comma (val) {
      return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    }
  },
  methods: {
    fnCloseLayer: function() {
      jQuery("#virAccPop").modal("hide");
    },

    fnInit: function() {
      var vm = this;
      var params = {
          "orderId" : this.popOrderId
      }

      cashApi.selectVirAccDetail(params).then(response => {
        var result = response.data;
        if(result.success) {
            var data = result.data;
          // 팝업 오픈  
            vm.accountNumber = data.accountNumber;
            vm.customerName = data.customerName;
            vm.cashChargAmount = data.cashChargAmount;
            vm.dueDate = data.dueDate;
        } 
      });
    }
  }
}
</script>
