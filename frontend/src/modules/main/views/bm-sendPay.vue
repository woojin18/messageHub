<template>
  <div>
    <article id="subVisualWrap02">
      <section>
        <div class="text-center">
          <h2  class="user_sub_h2">요금제 안내</h2>
          <p>채널별 메시지 발송 요금을 확인하실 수 있습니다.</p>
        </div>
      </section>
    </article>

    <div id="contentWrap">
      <div class="sub02_conWrap">
			<ul class="tab3" role="tablist">
				<li role="presentation" :class="tab==1 ? 'active' : ''"><a @click="fnSetPayTab(1)" aria-controls="sub02_1" role="tab" data-toggle="tab" aria-expanded="true">선불 요금제</a></li>
				<li role="presentation" :class="tab==2 ? 'active' : ''"><a @click="fnSetPayTab(2)" aria-controls="sub02_2" role="tab" data-toggle="tab" aria-expanded="false">후불 요금제</a></li>
			</ul>
			<div class="tab-content">

				<!--  tab1 -->
				<div role="tabpanel" :class="tab==1 ? 'tab-pane active' : 'tab-pane'" id="sub02_1">
					<div class="subVisualWrap02 wow  fadeInUp animated">
						<p class="tableTitle"><i class="fas fa-chevron-square-right"></i> 선불 요금제</p>
						<p class="infoText">일정금액을 결제하여 충전한 후, 사용할 때마다 충전된 건수가 차감되는 요금제입니다. <br>충전된캐시는 유효 기간 없이 사용 가능합니다.</p>
					</div>
					<div class="subVisualWrap02 wow  fadeInUp animated">
						<p class="tableTitle"><i class="fas fa-chevron-square-right"></i> 선불 요금제 이용방법</p>
						<div class="image-center"><img src="@/common/images/user_01.png" alt="선불 요금제 이용방법"></div>
					</div>

					<div class="subVisualWrap02 wow  fadeInUp animated">
						<p class="tableTitle2">SMS / LMS / MMS<img src="@/common/images/tableTitle2_bg.png" alt="tableTitle2_bg"></p>
						<table class="user_table_skin3">
							<colgroup>
								<col style="width:50%" span="2">
								<col>
							</colgroup>
							<thead>
								<tr class="lg">
									<th rowspan="2">상품명</th>
									<th rowspan="2" class="end">건별 요금 (VAT별도)</th>
								</tr>
							</thead>
							<tbody v-if="!fnIsEmptyObj(prdUnitInfo)">
								<template v-for="smsPayInfo in prdUnitInfo.sms">
									<tr v-for="(feeData, idx) in smsPayInfo.postFeeData" :key="smsPayInfo.productCode+idx">
										<td v-if="idx == 0" :rowspan="smsPayInfo.postFeeData.length">{{smsPayInfo.productName}}</td>
										<td v-if="idx == 0" :rowspan="smsPayInfo.postFeeData.length" class="end">{{smsPayInfo.preFee | formatPrice}} 원</td>
										<!-- <td>{{feeData.FEE_START_CNT | formatPrice}} ~ {{feeData.FEE_END_CNT | formatPrice}}</td>
										<td class="end">{{feeData.POST_FEE | formatPrice}} 원</td> -->
									</tr>
								</template>
							</tbody>
						</table>
					</div>
					
					<div class="subVisualWrap02 wow  fadeInUp animated">
						<p class="tableTitle2">RCS<img src="@/common/images/tableTitle2_bg.png" alt="tableTitle2_bg"></p>
						<table class="user_table_skin3">
							<colgroup>
								<col style="width:50%" span="2">
								<col>
							</colgroup>
							<thead>
								<tr class="lg">
									<th rowspan="2">상품명</th>
									<th rowspan="2" class="end">건별 요금 (VAT별도)</th>
								</tr>
							</thead>
							<tbody v-if="!fnIsEmptyObj(prdUnitInfo)">
								<template v-for="rcsPayInfo in prdUnitInfo.rcs">
									<tr v-for="(feeData, idx) in rcsPayInfo.postFeeData" :key="rcsPayInfo.productCode+idx">
										<td v-if="idx == 0" :rowspan="rcsPayInfo.postFeeData.length">{{rcsPayInfo.productName}}</td>
										<td v-if="idx == 0" :rowspan="rcsPayInfo.postFeeData.length" class="end">{{rcsPayInfo.preFee | formatPrice}} 원</td>
									</tr>
								</template>
							</tbody>
						</table>	
					</div>

					<div class="subVisualWrap02 wow animated fadeInUp">
						<p class="tableTitle2">KakaoTalk Bizmessage<img src="@/common/images/tableTitle2_bg.png" alt="tableTitle2_bg"></p>
						<table class="user_table_skin3">
							<colgroup>
								<col style="width:50%" span="2">
								<col>
							</colgroup>
							<thead>
								<tr class="lg">
									<th rowspan="2">상품명</th>
									<th rowspan="2" class="end">건별 요금 (VAT별도)</th>
								</tr>
							</thead>
							<tbody v-if="!fnIsEmptyObj(prdUnitInfo)">
								<template v-for="kkoPayInfo in prdUnitInfo.kko">
									<tr v-for="(feeData, idx) in kkoPayInfo.postFeeData" :key="kkoPayInfo.productCode+idx">
										<td v-if="idx == 0" :rowspan="kkoPayInfo.postFeeData.length">{{kkoPayInfo.productName}}</td>
										<td v-if="idx == 0" :rowspan="kkoPayInfo.postFeeData.length" class="end">{{kkoPayInfo.preFee | formatPrice}} 원</td>
									</tr>
								</template>
							</tbody>
						</table>	
					</div>

					<div class="subVisualWrap02 wow animated fadeInUp">
						<p class="tableTitle2">Push<img src="@/common/images/tableTitle2_bg.png" alt="tableTitle2_bg"></p>
						<table class="user_table_skin3">
							<colgroup>
								<col style="width:50%" span="2">
								<col>
							</colgroup>
							<thead>
								<tr class="lg">
									<th rowspan="2">상품명</th>
									<th rowspan="2" class="end">건별 요금 (VAT별도)</th>
								</tr>
							</thead>
							<tbody v-if="!fnIsEmptyObj(prdUnitInfo)">
								<template v-for="pushPayInfo in prdUnitInfo.push">
									<tr v-for="(feeData, idx) in pushPayInfo.postFeeData" :key="pushPayInfo.productCode + idx">
										<td v-if="idx == 0" :rowspan="pushPayInfo.postFeeData.length">{{pushPayInfo.productName}}</td>
										<td v-if="idx == 0" :rowspan="pushPayInfo.postFeeData.length"  class="end">{{pushPayInfo.preFee | formatPrice}} 원</td>
									</tr>
								</template>
							</tbody>
						</table>	
					</div>
				</div>

				<!--  tab2 -->
				<div role="tabpanel" :class="tab==2 ? 'tab-pane active' : 'tab-pane'" id="sub02_2">
					<div class="subVisualWrap02 wow  fadeInUp animated">
						<p class="tableTitle"><i class="fas fa-chevron-square-right"></i> 후불 요금제</p>
						<p class="infoText">서비스를 사용한 후, 1개월마다 사용량을 정산하여 그 금액만큼 후불로 결제하는 요금제입니다. <br>
							매월 1일~ 말 일의 발송건수에 대한 요금이 익월 청구됩니다.<br>
							<span class="color3">*1~3년 약정 계약 시 단가 할인 적용이 가능합니다.</span></p>
					</div>
					<div class="subVisualWrap02 wow  fadeInUp animated">
						<p class="tableTitle"><i class="fas fa-chevron-square-right"></i> 후불 요금제 이용방법</p>
						<div class="image-center"><img src="@/common/images/user_02.png" alt="후불 요금제 이용방법"></div>
					</div>

					<div class="subVisualWrap02 wow  fadeInUp animated">
						<p class="tableTitle2">SMS / LMS / MMS<img src="@/common/images/tableTitle2_bg.png" alt="tableTitle2_bg"></p>
						<table class="user_table_skin3">
							<colgroup>
								<col style="width:50%" span="2">
								<col>
							</colgroup>
							<thead>
								<tr class="lg">
									<th rowspan="2">상품명</th>
									<th rowspan="2" class="end">건별 요금 (VAT별도)</th>
								</tr>
							</thead>
							<tbody v-if="!fnIsEmptyObj(prdUnitInfo)">
								<template v-for="smsPayInfo in prdUnitInfo.sms">
									<tr v-for="(feeData, idx) in smsPayInfo.postFeeData" :key="smsPayInfo.productCode+idx">
										<td v-if="idx == 0" :rowspan="smsPayInfo.postFeeData.length">{{smsPayInfo.productName}}</td>
										<td v-if="idx == 0" :rowspan="smsPayInfo.postFeeData.length" class="end">{{feeData.POST_FEE | formatPrice}} 원</td>
									</tr>
								</template>
							</tbody>
						</table>
					</div>
					
					<div class="subVisualWrap02 wow  fadeInUp animated">
						<p class="tableTitle2">RCS<img src="@/common/images/tableTitle2_bg.png" alt="tableTitle2_bg"></p>
						<table class="user_table_skin3">
							<colgroup>
								<col style="width:50%" span="2">
								<col>
							</colgroup>
							<thead>
								<tr class="lg">
									<th rowspan="2">상품명</th>
									<th rowspan="2" class="end">건별 요금 (VAT별도)</th>
								</tr>
							</thead>
							<tbody v-if="!fnIsEmptyObj(prdUnitInfo)">
								<template v-for="rcsPayInfo in prdUnitInfo.rcs">
									<tr v-for="(feeData, idx) in rcsPayInfo.postFeeData" :key="rcsPayInfo.productCode+idx">
										<td v-if="idx == 0" :rowspan="rcsPayInfo.postFeeData.length">{{rcsPayInfo.productName}}</td>
										<td v-if="idx == 0" :rowspan="rcsPayInfo.postFeeData.length" class="end">{{feeData.POST_FEE | formatPrice}} 원</td>
									</tr>
								</template>
							</tbody>
						</table>	
					</div>

					<div class="subVisualWrap02 wow  fadeInUp animated">
						<p class="tableTitle2">KakaoTalk Bizmessage<img src="@/common/images/tableTitle2_bg.png" alt="tableTitle2_bg"></p>
						<table class="user_table_skin3">
							<colgroup>
								<col style="width:50%" span="2">
								<col>
							</colgroup>
							<thead>
								<tr class="lg">
									<th rowspan="2">상품명</th>
									<th rowspan="2" class="end">건별 요금 (VAT별도)</th>
								</tr>
							</thead>
							<tbody v-if="!fnIsEmptyObj(prdUnitInfo)">
								<template v-for="kkoPayInfo in prdUnitInfo.kko">
									<tr v-for="(feeData, idx) in kkoPayInfo.postFeeData" :key="kkoPayInfo.productCode+idx">
										<td v-if="idx == 0" :rowspan="kkoPayInfo.postFeeData.length">{{kkoPayInfo.productName}}</td>
										<td v-if="idx == 0" :rowspan="kkoPayInfo.postFeeData.length" class="end">{{feeData.POST_FEE | formatPrice}} 원</td>
									</tr>
								</template>
							</tbody>
						</table>
					</div>

					<div class="subVisualWrap02 wow  fadeInUp animated">
						<p class="tableTitle2">Push<img src="@/common/images/tableTitle2_bg.png" alt="tableTitle2_bg"></p>
						<table class="user_table_skin3">
							<colgroup>
								<col style="width:50%" span="2">
								<col>
							</colgroup>
							<thead>
								<tr class="lg">
									<th rowspan="2">상품명</th>
									<th rowspan="2" class="end">건별 요금 (VAT별도)</th>
								</tr>
							</thead>
							<tbody v-if="!fnIsEmptyObj(prdUnitInfo)">
								<template v-for="pushPayInfo in prdUnitInfo.push">
									<tr v-for="(feeData, idx) in pushPayInfo.postFeeData" :key="pushPayInfo.productCode + idx">
										<td v-if="idx == 0" :rowspan="pushPayInfo.postFeeData.length">{{pushPayInfo.productName}}</td>
										<td v-if="idx == 0" :rowspan="pushPayInfo.postFeeData.length"  class="end">{{feeData.POST_FEE | formatPrice}} 원</td>
									</tr>
								</template>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
    </div>
    <QuickRight></QuickRight>
  </div>
</template>

<script>
import QuickRight from "@/modules/main/components/bc-quickRight.vue";

import mainApi from "@/modules/main/service/mainApi.js";

export default {
  name: 'sendPay',
  components : {
    QuickRight
  },
  props: {
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '요금제 안내';
      }
    },
  },
  data() {
    return {
/*
      productCodes:{
        PUSH: ['PUSH'],
        RCS: ['RCS_TMP', 'RCS_SMS', 'RCS_LMS', 'RCS_MMS'],
        SMS: ['SMS', 'LMS', 'MMS'],
        KKO: ['ALIMTALK', 'FRIENDTALK_01', 'FRIENDTALK_02', 'FRIENDTALK_03'],
      },
       */
      prdUnitInfo: {},
      tab : 1
    }
  },
  created() {
    this.fnSelectPrdUnitInfoList();
  },
  mounted() {
    this.fnSetPayTab(this.tab);
  },
  methods: {
    fnSetPayTab(tab){
      this.tab = tab;
    },
    async fnSelectPrdUnitInfoList(){
      const params = {};
      await mainApi.selectPrdUnitInfoList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          Object.keys(result.data).forEach(function(chGrp){
            result.data[chGrp].forEach(function(obj, idx){
              result.data[chGrp][idx].postFeeData = JSON.parse(obj.postFeeInfo);
            });
          });
          this.prdUnitInfo = Object.assign({}, result.data);
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnJsonStrToJson(str){
      return JSON.parse(str);
    },
    //빈오브젝트확인
    fnIsEmptyObj(obj){
      if(typeof obj === 'undefined') return true;
      if(Object.keys(obj).length === 0) return true;
      return false;
    }
  }
}
</script>