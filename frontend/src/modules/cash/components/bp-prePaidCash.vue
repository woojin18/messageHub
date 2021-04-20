<template>
  <div class="modal modalStyle" id="cashPop" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 500px;">
      <div class="modal-content">
        <div class="modal-body">
          <div>
						<h5 class="lc-1">캐시 충전</h5>
						<hr>
					  <!-- <div class="mt15">
              <h4 class="font-normal inline-block width90 mr10">결제유형</h4>
              <select class="inputStyle width291"></select>
            </div> -->
            <div class="mt15">
              <h4 class="font-normal inline-block width90 mr10">충전금액</h4>
              <input v-model="amount" class="inputStyle width291">
            </div>
            <div class="mt15">
              * 충전금액은 1,000원 단위로 가능합니다.
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
  name: 'prePaidCashLayer',
  data() {
    return {
      amount : 0
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
    fnCloseLayer: function() {
      jQuery("#cashPop").modal("hide");
    },

    fnConfirm: function() {
      var amount = this.amount.replace(/,/gi, '');

      if(amount < 1000) {
        confirm.fnAlert("", "가격은 1,000 이상이어야 합니다.");
      } else if(amount % 1000 != 0) {
        confirm.fnAlert("", "가격은 1,000 원 단위이어야 합니다.");
      } else if(amount >= 100000000) {
        confirm.fnAlert("", "가격은 100,000,000 원을 넘길 수 없습니다.");
      } else {
        var params = {
          "userId"  : tokenSvc.getToken().principal.userId,
          "amount"  : amount
        };

        cashApi.insertWebCashInfo(params).then(response => {
          var result = response.data;
          if(result.success) {
            var data = result.data;

            loadTossPayments(data.clientKey).then(tossPayments => {
              tossPayments.requestPayment('카드', {
                amount: amount,                                                       //amount (필수) · number    실제 결제되는 금액입니다.
                orderId: data.orderId,                                                //orderId (필수) · string   가맹점에서 사용하는 해당 주문에 대한 ID입니다. 각 주문마다 유니크해야 합니다.
                orderName: data.orderName,                                            //orderName (필수) · string 결제에 대한 주문명입니다. 예를 들면 '생수 외 1건' 같은 형식입니다.
                customerName: data.customerName,                                      //customerName · string 결제하는 고객의 실명입니다. 결제 완료 시 보내주는 이메일 등에 사용됩니다.
                successUrl: window.location.origin + '/projectApi/cash/cardSuccess',  //successUrl (필수) · string  결제가 성공하고 나면 리다이렉트(Redirect)되는 URL입니다. 결제 승인 처리에 필요한 값들이 쿼리 파라미터(Query Parameter)로 함께 전달됩니다. 반드시 오리진(origin)을 포함해야 합니다. 예를 들면 'https://www.example.com/success'와 같은 형태입니다.
                failUrl: window.location.origin + '/projectApi/cash/cardFail',        //failUrl (필수) · string     결제가 실패하면 리다이렉트되는 URL입니다. 에러 코드 및 에러 메시지가 쿼리 파라미터로 함께 전송됩니다. 반드시 오리진(origin)을 포함해야 합니다.
                //cardCompany · enum                  카드사가 고정된 상태로 결제창이 열립니다. 예를 들어, BC라는 값을 주면 카드사가 BC카드로 고정된 결제창이 열립니다. 사용할 수 있는 값은 카드사 코드를 참조하세요.
                //cardInstallmentPlan · number        할부 개월 수가 고정된 상태로 결제창이 열립니다.
                //maxCardInstallmentPlan · number     최대 할부 개월 수를 제한하기 위해 사용합니다. 값은 최소 1(일시불), 최대 12까지 사용할 수 있습니다. cardInstallmentPlan과 동시에 사용할 수 없습니다.
                //useCardPoint · boolean              카드사 포인트 사용 여부를 결정합니다. 값을 주지 않으면 사용자는 카드사 포인트를 쓸 지 여부에 대해 결정할 수 있습니다. 이 값을 true로 주면 카드사 포인트 사용이 체크된 상태로 결제창이 열립니다. 이 값을 false로 주면 사용자는 카드사 포인트를 사용할 수 없습니다.
                //customerEmail · string              결제하는 고객의 이메일입니다. 값을 주면 이메일이 채워진 채로 결제창이 열립니다.
                //customerMobilePhone · string        결제하는 고객의 휴대폰 번호입니다. 값을 주면 휴대폰 번호가 채워진 채로 결제창이 열립니다. 대시(-)없이 숫자만 사용할 수 있습니다. 예를 들면 01012345678과 같은 형식입니다.
                //taxFreeAmount · number              결제하는 상품에 대한 면세 금액입니다.
                //useInternationalCardOnly · boolean  이 값이 true이면 결제창에서 해외카드(Visa, MasterCard, UnionPay)로만 결제가 가능합니다. 또한, 결제창이 영문으로 열립니다. * 해외카드 가맹점만 사용 가능합니다.
                //flowMode · 'DEFAULT' | 'DIRECT'     이 값이 'DIRECT'이면, 토스페이먼츠의 약관 동의 페이지와 카드 선택 페이지를 건너뛰고 바로 카드사 페이지를 엽니다. 따라서 이 값이 'DIRECT'일 때는 cardCompany 값이 필수입니다.
                //discountCode · string               할인 코드입니다. 이 값을 주면 할인 코드에 해당하는 할인이 적용됩니다. 카드 프로모션 조회 API를 통해 적용할 수 있는 할인 코드의 목록을 조회할 수 있습니다. 이 파라미터는 flowMode가 'DIRECT'일 때만 사용이 가능합니다.
                //appScheme · string                  모바일 ISP 앱에서 가맹점 앱으로 전환하기 위해 사용됩니다. 가맹점의 앱 스킴을 지정하면 됩니다. 예를 들면 'testapp://' 같은 형태입니다. * 브라우저 상의 결제에서는 사용되지 않습니다.
              });
            });
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
