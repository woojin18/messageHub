<template>
  <div class="modal modalStyle" id="cashPop" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 600px;">
      <div class="modal-content">
        <div class="modal-body">
          <div>
						<h2 class="lc-1">캐시 충전</h2>
						<hr>
					</div>

          <div class="of_h">
            <h5 class="inline-block" style="width:20%">충전금액 </h5>
            <div style="width:80%" class="of_h inline-block float-right">
              <input v-model="amount" class="inputStyle width291">
              <p class="txtCaption mt5">* 충전금액은 1,000원 단위로 가능합니다.</p>
            </div>
          </div>
          <div class="of_h consolMarginTop">
            <h5 class="inline-block" style="width:20%%">결제수단 선택</h5>
            <div style="width:80%" class="of_h inline-block float-right">
              <div class="row ml0 mr0">
                <div class="col-xs-4 text-center pd5">
                  <label for="pushTemplate1-1">
                    <div class="bgColor_f1 pd30">
                      <i class="far fa-credit-card inline-block font-size30 color1"></i>
                      <h6 class="mt10">신용카드</h6>
                    </div>
                  </label>
                  <div class="consolMarginTop">
                    <input v-model="cashType" value="card" type="radio" name="radio1" id="pushTemplate1-1" class="radioStyle" checked=""><label for="pushTemplate1-1"></label>
                  </div>
                </div>	

                <div class="col-xs-4 text-center pd5">
                  <label for="pushTemplate1-2">
                    <div class="bgColor_f1 pd30">
                      <i class="far fa-money-check-alt inline-block font-size30 color1"></i>
                      <h6 class="mt10">계좌이체</h6>
                    </div>
                  </label>
                  <div class="consolMarginTop">
                    <input v-model="cashType" value="acc_trans" type="radio" name="radio1" id="pushTemplate1-2" class="radioStyle" checked=""><label for="pushTemplate1-2"></label>											
                  </div>
                </div>	

                <div class="col-xs-4 text-center pd5">
                  <label for="pushTemplate1-3">
                    <div class="bgColor_f1 pd30">
                      <i class="far fa-desktop inline-block font-size30 color1"></i>
                      <h6 class="mt10">가상계좌</h6>
                    </div>
                  </label>
                  <div class="consolMarginTop">
                    <input v-model="cashType" value="vir_acc" type="radio" name="radio1" id="pushTemplate1-3" class="radioStyle" checked=""><label for="pushTemplate1-3"></label>											
                  </div>
                </div>	

              </div>
            </div>
          </div>

          
          <div class="text-center mt30">
            <button @click="fnConfirm" class="btnStyle4 red mr10">결제</button>
            <button @click="fnCloseLayer" class="btnStyle3 grey">닫기</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import cashApi from "@/modules/cash/service/api"
import tokenSvc from '@/common/token-service';

import confirm from "@/modules/commonUtil/service/confirm"
import {eventBus} from "@/modules/commonUtil/service/eventBus";

/* $ npm install @tosspayments/sdk */
import { loadTossPayments } from '@tosspayments/sdk';

export default {
  name: 'prePaidCashPop',
  data() {
    return {
      amount : "0",
      cashType : "card",
      virAccUrl : ''
    }
  },
  mounted() {
    var pgMessage = this.$route.query.pgMessage;

    if(pgMessage) {
      if(pgMessage == "0") {
        pgMessage = "결제에 실패하였습니다."
      }

      eventBus.$on('callbackEventBus', this.Refresh);
      confirm.fnConfirm("", pgMessage, "확인");
    }

    this.fnInit();
  },
  watch: {
    amount(val) {
      const reg1 = /^[0-9]*$/;
      
      val = val.replace(/,/gi, '');
      val = parseInt(val);

      if(isNaN(val)) {
        val = 0;
      }else if(!reg1.test(val)) {
        val = val.toString().slice(0, -1);
      } else {
        val = val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      }
      
      return this.amount = val;
    }
  },
  methods: {
    fnInit : function() {
      cashApi.selectVirAccUrl().then(response => {
        var result = response.data;
        if(result.success) {
          var data = result.data;
          this.virAccUrl = data.virAccUrl;
        }
      });
    },

    fnCloseLayer: function() {
      jQuery("#cashPop").modal("hide");
    },

    fnConfirm: function() {
      var amount = this.amount.replace(/,/gi, '');
      var cashType = this.cashType;
      var virAccUrl = this.virAccUrl;

      if(amount < 1000) {
        confirm.fnAlert("", "가격은 1,000 이상이어야 합니다.");
      } else if(amount % 1000 != 0) {
        confirm.fnAlert("", "가격은 1,000 원 단위이어야 합니다.");
      } else if(amount >= 100000000) {
        confirm.fnAlert("", "가격은 100,000,000 원을 넘길 수 없습니다.");
      } else {
        var params = {
          "userId"  : tokenSvc.getToken().principal.userId,
          "amount"  : amount,
          "cashType": cashType
        };

        cashApi.insertWebCashInfo(params).then(response => {
          var result = response.data;
          if(result.success) {
            var data = result.data;

            if(cashType == "card") {

              loadTossPayments(data.clientKey).then(tossPayments => {
                tossPayments.requestPayment('카드', {
                  amount: amount,                                                       //amount (필수) · number    실제 결제되는 금액입니다.
                  orderId: data.orderId,                                                //orderId (필수) · string   가맹점에서 사용하는 해당 주문에 대한 ID입니다. 각 주문마다 유니크해야 합니다.
                  orderName: data.orderName,                                            //orderName (필수) · string 결제에 대한 주문명입니다. 예를 들면 '생수 외 1건' 같은 형식입니다.
                  customerName: data.customerName,                                      //customerName · string 결제하는 고객의 실명입니다. 결제 완료 시 보내주는 이메일 등에 사용됩니다.
                  successUrl: window.location.origin + '/projectApi/cash/cardSuccess',  //successUrl (필수) · string  결제가 성공하고 나면 리다이렉트(Redirect)되는 URL입니다. 결제 승인 처리에 필요한 값들이 쿼리 파라미터(Query Parameter)로 함께 전달됩니다. 반드시 오리진(origin)을 포함해야 합니다. 예를 들면 'https://www.example.com/success'와 같은 형태입니다.
                  failUrl: window.location.origin + '/projectApi/cash/paymentFail',        //failUrl (필수) · string     결제가 실패하면 리다이렉트되는 URL입니다. 에러 코드 및 에러 메시지가 쿼리 파라미터로 함께 전송됩니다. 반드시 오리진(origin)을 포함해야 합니다.
                });
              });

            } else if(cashType == "acc_trans") {

              loadTossPayments(data.clientKey).then(tossPayments => {
                tossPayments.requestPayment('계좌이체', {
                  amount: amount,                                                       //amount (필수) · number    실제 결제되는 금액입니다.
                  orderId: data.orderId,                                                //orderId (필수) · string   가맹점에서 사용하는 해당 주문에 대한 ID입니다. 각 주문마다 유니크해야 합니다.
                  orderName: data.orderName,                                            //orderName (필수) · string 결제에 대한 주문명입니다. 예를 들면 '생수 외 1건' 같은 형식입니다.
                  customerName: data.customerName,                                      //customerName · string 결제하는 고객의 실명입니다. 결제 완료 시 보내주는 이메일 등에 사용됩니다.
                  successUrl: window.location.origin + '/projectApi/cash/accTransSuccess',  //successUrl (필수) · string  결제가 성공하고 나면 리다이렉트(Redirect)되는 URL입니다. 결제 승인 처리에 필요한 값들이 쿼리 파라미터(Query Parameter)로 함께 전달됩니다. 반드시 오리진(origin)을 포함해야 합니다. 예를 들면 'https://www.example.com/success'와 같은 형태입니다.
                  failUrl: window.location.origin + '/projectApi/cash/paymentFail',        //failUrl (필수) · string     결제가 실패하면 리다이렉트되는 URL입니다. 에러 코드 및 에러 메시지가 쿼리 파라미터로 함께 전송됩니다. 반드시 오리진(origin)을 포함해야 합니다.
                });
              }); 

            } else if(cashType == "vir_acc") {
              
              loadTossPayments(data.clientKey).then(tossPayments => {
                tossPayments.requestPayment('가상계좌', {
                  amount: amount,                                                       //amount (필수) · number    실제 결제되는 금액입니다.
                  orderId: data.orderId,                                                //orderId (필수) · string   가맹점에서 사용하는 해당 주문에 대한 ID입니다. 각 주문마다 유니크해야 합니다.
                  orderName: data.orderName,                                            //orderName (필수) · string 결제에 대한 주문명입니다. 예를 들면 '생수 외 1건' 같은 형식입니다.
                  customerName: data.customerName,                                      //customerName · string 결제하는 고객의 실명입니다. 결제 완료 시 보내주는 이메일 등에 사용됩니다.
                  successUrl: window.location.origin + '/projectApi/cash/virAccSuccess',  //successUrl (필수) · string  결제가 성공하고 나면 리다이렉트(Redirect)되는 URL입니다. 결제 승인 처리에 필요한 값들이 쿼리 파라미터(Query Parameter)로 함께 전달됩니다. 반드시 오리진(origin)을 포함해야 합니다. 예를 들면 'https://www.example.com/success'와 같은 형태입니다.
                  failUrl: window.location.origin + '/projectApi/cash/paymentFail',        //failUrl (필수) · string     결제가 실패하면 리다이렉트되는 URL입니다. 에러 코드 및 에러 메시지가 쿼리 파라미터로 함께 전송됩니다. 반드시 오리진(origin)을 포함해야 합니다.
                  valiHours: 72,                                                           // 입금 가능 시간
                  virtualAccountCallbackUrl: virAccUrl // 입금확인 url
                });
              }); 

            }

          } else {
            alert(result.message);
          }
        })

        
      }
    },

    Refresh: function() {
      this.$router.push({
        path: "/ac/cash"
      });
    }
  }
}
</script>
