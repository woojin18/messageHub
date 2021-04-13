<template>
  <div>
    <h4 class="lc-1 mb20">선불 충전관리</h4>
    <div class="menuBox">
      <div class="row">
        <div class="col-xs-6 text-center">
          <!-- table -->
          <table class="table_skin4" style="width:50%">
            <tbody>
              <tr>
              <td class="text-left color4">남은 충전캐시</td>
              <td class="text-right end">1,490,000원</td>
              </tr>	
              <tr>
              <td class="text-left color4">남은 이벤트 캐시</td>
              <td class="text-right end">10,000원</td>
              </tr>	
              <tr>
              <td class="text-left color4 bgColor_sky">남은 캐시</td>
              <td class="text-right end bgColor_sky">1,500,000원</td>
              </tr>	
            </tbody>
          </table>
          <!-- //table -->
        </div>	
        <div class="col-xs-6 text-center" style="padding: 2% 0">	
          <a @click="fnTossPayCard" class="btnStyle4 white font13 width180 mt10">캐시 충전</a>
        </div>
      </div>
    </div>

    <h4 class="lc-1 mb20 mt40">충전 이력조회</h4>
    <div class="row">
      <div class="col-xs-12">
        <PagingCnt :pageInfo.sync="pageInfo" />
        
        <!-- table -->
        <table class="table_skin1 bt-000 tbl-striped">
          <colgroup>
            <col style="width:10%">
            <col>
            <col style="width:15%">
            <col style="width:15%">
            <col style="width:15%">
            <col style="width:15%">
            <col>
          </colgroup>
          <thead>
            <tr>
            <th class="text-center lc-1">NO.</th>
            <th class="text-center lc-1">발생내역</th>
            <th class="text-center lc-1">충전캐시</th>
            <th class="text-center lc-1">남은캐시</th>
            <th class="text-center lc-1">결제유형</th>
            <th class="text-center lc-1">발생일자</th>
            <th class="text-center lc-1 end">유효기관</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td class="text-center">15</td>
              <td class="text-left">캐시 충전</td>
              <td class="text-center">500,000</td>
              <td class="text-center">500,000</td>
              <td class="text-center">선불</td>
              <td class="text-center">2021-02-01</td>
              <td class="text-center end">-</td>
            </tr>	
          </tbody>
        </table>
        <!-- //table -->

        <Paging :pageInfo.sync="pageInfo" />
      </div>			
    </div>
  </div>
</template>

<script>
import Paging from "@/modules/commonUtil/components/bc-paging"
import PagingCnt from "@/modules/commonUtil/components/bc-pagingCnt"

/* $ npm install @tosspayments/sdk */
/* import { loadTossPayments } from '@tosspayments/sdk'; */
const clientKey = 'test_ck_ZORzdMaqN3wXJPGmEADV5AkYXQGw';

export default {
  name: 'prepaidCash',
  data() {
    return {
      pageInfo: {}
    }
  },
  components: {
    Paging,
    PagingCnt
  },
  mounted() {
    this.pageInfo = {
      "pageCnt"   : [3, 5, 7],  //표시할 개수 리스트
      "selPageCnt": 5,          //선택한 표시 개수
      "selPage"   : 1,          //선택한 페이지
      "rowNum"    : 1           //총개수
    };
  },
  methods: {
    fnTossPayCard: function() {
      loadTossPayments(clientKey).then(tossPayments => {
        tossPayments.requestPayment('카드', {
          amount: 15000,                                              //amount (필수) · number    실제 결제되는 금액입니다.
          orderId: '7k270DX_TR8A7KWmd_2bO',                           //orderId (필수) · string   가맹점에서 사용하는 해당 주문에 대한 ID입니다. 각 주문마다 유니크해야 합니다.
          orderName: '토스 티셔츠 외 2건',                              //orderName (필수) · string 결제에 대한 주문명입니다. 예를 들면 '생수 외 1건' 같은 형식입니다.
          customerName: '박토스',
          successUrl: window.location.origin + '/api/cash/success',   //successUrl (필수) · string  결제가 성공하고 나면 리다이렉트(Redirect)되는 URL입니다. 결제 승인 처리에 필요한 값들이 쿼리 파라미터(Query Parameter)로 함께 전달됩니다. 반드시 오리진(origin)을 포함해야 합니다. 예를 들면 'https://www.example.com/success'와 같은 형태입니다.
          failUrl: window.location.origin + '/api/cash/fail',         //failUrl (필수) · string     결제가 실패하면 리다이렉트되는 URL입니다. 에러 코드 및 에러 메시지가 쿼리 파라미터로 함께 전송됩니다. 반드시 오리진(origin)을 포함해야 합니다.
          //cardCompany · enum                  카드사가 고정된 상태로 결제창이 열립니다. 예를 들어, BC라는 값을 주면 카드사가 BC카드로 고정된 결제창이 열립니다. 사용할 수 있는 값은 카드사 코드를 참조하세요.
          //cardInstallmentPlan · number        할부 개월 수가 고정된 상태로 결제창이 열립니다.
          //maxCardInstallmentPlan · number     최대 할부 개월 수를 제한하기 위해 사용합니다. 값은 최소 1(일시불), 최대 12까지 사용할 수 있습니다. cardInstallmentPlan과 동시에 사용할 수 없습니다.
          //useCardPoint · boolean              카드사 포인트 사용 여부를 결정합니다. 값을 주지 않으면 사용자는 카드사 포인트를 쓸 지 여부에 대해 결정할 수 있습니다. 이 값을 true로 주면 카드사 포인트 사용이 체크된 상태로 결제창이 열립니다. 이 값을 false로 주면 사용자는 카드사 포인트를 사용할 수 없습니다.
          //customerName · string               결제하는 고객의 실명입니다. 결제 완료 시 보내주는 이메일 등에 사용됩니다.
          //customerEmail · string              결제하는 고객의 이메일입니다. 값을 주면 이메일이 채워진 채로 결제창이 열립니다.
          //customerMobilePhone · string        결제하는 고객의 휴대폰 번호입니다. 값을 주면 휴대폰 번호가 채워진 채로 결제창이 열립니다. 대시(-)없이 숫자만 사용할 수 있습니다. 예를 들면 01012345678과 같은 형식입니다.
          //taxFreeAmount · number              결제하는 상품에 대한 면세 금액입니다.
          //useInternationalCardOnly · boolean  이 값이 true이면 결제창에서 해외카드(Visa, MasterCard, UnionPay)로만 결제가 가능합니다. 또한, 결제창이 영문으로 열립니다. * 해외카드 가맹점만 사용 가능합니다.
          //flowMode · 'DEFAULT' | 'DIRECT'     이 값이 'DIRECT'이면, 토스페이먼츠의 약관 동의 페이지와 카드 선택 페이지를 건너뛰고 바로 카드사 페이지를 엽니다. 따라서 이 값이 'DIRECT'일 때는 cardCompany 값이 필수입니다.
          //discountCode · string               할인 코드입니다. 이 값을 주면 할인 코드에 해당하는 할인이 적용됩니다. 카드 프로모션 조회 API를 통해 적용할 수 있는 할인 코드의 목록을 조회할 수 있습니다. 이 파라미터는 flowMode가 'DIRECT'일 때만 사용이 가능합니다.
          //appScheme · string                  모바일 ISP 앱에서 가맹점 앱으로 전환하기 위해 사용됩니다. 가맹점의 앱 스킴을 지정하면 됩니다. 예를 들면 'testapp://' 같은 형태입니다. * 브라우저 상의 결제에서는 사용되지 않습니다.
        });
      });
    }
  }
}
</script>