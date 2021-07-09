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
        <div class="subVisualWrap02 wow animated fadeInUp">
          <section>
            <p class="tableTitle"><i class="fas fa-chevron-square-right"></i> Push</p>
            <p class="tableText">VAT 별도</p>
            <table class="user_table_skin1">
              <colgroup>
                <col style="width:25%">
                <col style="width:25%">
                <col style="width:25%">
                <col>
              </colgroup>
              <thead>
                <tr>
                  <th rowspan="2">상품명</th>
                  <th rowspan="2">선불 수신 건별 요금<br>(VAT별도)</th>
                  <th colspan="2" class="end">후불요금</th>
                </tr>
                <tr style="border-top: 1px solid #D5D5D5;">
                  <th>월 수신건</th>
                  <th class="end">후불 수신 건별 요금<br>(VAT별도)</th>
                </tr>
              </thead>
              <tbody v-if="!fnIsEmptyObj(prdUnitInfo)">
                <template v-for="pushPayInfo in prdUnitInfo.push">
                  <tr v-for="(feeData, idx) in pushPayInfo.postFeeData" :key="pushPayInfo.productCode + idx">
                    <td v-if="idx == 0" rowspan="3">{{pushPayInfo.productName}}</td>
                    <td v-if="idx == 0" rowspan="3">{{pushPayInfo.preFee | formatPrice}} 원</td>
                    <td>{{feeData.FEE_START_CNT | formatPrice}} ~ {{feeData.FEE_END_CNT | formatPrice}}</td>
                    <td class="end">{{feeData.POST_FEE | formatPrice}} 원</td>
                  </tr>
                </template>
              </tbody>
            </table>
          </section>
        </div>

        <div class="subVisualWrap02 wow animated fadeInUp">
          <section>
            <p class="tableTitle"><i class="fas fa-chevron-square-right"></i> RCS</p>
            <p class="tableText">VAT 별도</p>
            <table class="user_table_skin1">
              <colgroup>
                <col style="width:25%">
                <col style="width:25%">
                <col style="width:25%">
                <col>
              </colgroup>
              <thead>
                <tr>
                  <th rowspan="2">상품명</th>
                  <th rowspan="2">선불 수신 건별 요금<br>(VAT별도)</th>
                  <th colspan="2" class="end">후불요금</th>
                </tr>
                <tr style="border-top: 1px solid #D5D5D5;">
                  <th>월 수신건</th>
                  <th class="end">후불 수신 건별 요금<br>(VAT별도)</th>
                </tr>
              </thead>
              <tbody v-if="!fnIsEmptyObj(prdUnitInfo)">
                <template v-for="rcsPayInfo in prdUnitInfo.rcs">
                  <tr v-for="(feeData, idx) in rcsPayInfo.postFeeData" :key="rcsPayInfo.productCode+idx">
                    <td v-if="idx == 0" rowspan="3">{{rcsPayInfo.productName}}</td>
                    <td v-if="idx == 0" rowspan="3">{{rcsPayInfo.preFee | formatPrice}} 원</td>
                    <td>{{feeData.FEE_START_CNT | formatPrice}} ~ {{feeData.FEE_END_CNT | formatPrice}}</td>
                    <td class="end">{{feeData.POST_FEE | formatPrice}} 원</td>
                  </tr>
                </template>
              </tbody>
            </table>

          </section>
        </div>

        <div class="subVisualWrap02 wow animated fadeInUp">
          <section>
            <p class="tableTitle"><i class="fas fa-chevron-square-right"></i> SMS/MMS</p>
            <p class="tableText">VAT 별도</p>
            <table class="user_table_skin1">
              <colgroup>
                <col style="width:25%">
                <col style="width:25%">
                <col style="width:25%">
                <col>
              </colgroup>
              <thead>
                <tr>
                  <th rowspan="2">상품명</th>
                  <th rowspan="2">선불 수신 건별 요금<br>(VAT별도)</th>
                  <th colspan="2" class="end">후불요금</th>
                </tr>
                <tr style="border-top: 1px solid #D5D5D5;">
                  <th>월 수신건</th>
                  <th class="end">후불 수신 건별 요금<br>(VAT별도)</th>
                </tr>
              </thead>
              <tbody v-if="!fnIsEmptyObj(prdUnitInfo)">
                <template v-for="smsPayInfo in prdUnitInfo.sms">
                  <tr v-for="(feeData, idx) in smsPayInfo.postFeeData" :key="smsPayInfo.productCode+idx">
                    <td v-if="idx == 0" rowspan="3">{{smsPayInfo.productName}}</td>
                    <td v-if="idx == 0" rowspan="3">{{smsPayInfo.preFee | formatPrice}} 원</td>
                    <td>{{feeData.FEE_START_CNT | formatPrice}} ~ {{feeData.FEE_END_CNT | formatPrice}}</td>
                    <td class="end">{{feeData.POST_FEE | formatPrice}} 원</td>
                  </tr>
                </template>
              </tbody>
            </table>
            <p class="infoText"><i class="fas fa-info-circle"></i> SMS 서비스에서 SMS는 메시지 길이 90바이트 이하의 단문 메시지이며, LMS는 메시지 길이 2,000바이트 이하의 장문 메시지, MMS는 멀티미디어 파일(사진, 컬러티콘, 배경 음악)이 첨부된 메시지를 의미합니다.</p>
          </section>
        </div>

        <div class="subVisualWrap02 wow animated fadeInUp">
          <section>
            <p class="tableTitle"><i class="fas fa-chevron-square-right"></i> KakaoTalk Bizmessage</p>
            <p class="tableText">VAT 별도</p>
            <table class="user_table_skin1">
              <colgroup>
                <col style="width:25%">
                <col style="width:25%">
                <col style="width:25%">
                <col>
              </colgroup>
              <thead>
                <tr>
                  <th rowspan="2">상품명</th>
                  <th rowspan="2">선불 수신 건별 요금<br>(VAT별도)</th>
                  <th colspan="2" class="end">후불요금</th>
                </tr>
                <tr style="border-top: 1px solid #D5D5D5;">
                  <th>월 수신건</th>
                  <th class="end">후불 수신 건별 요금<br>(VAT별도)</th>
                </tr>
              </thead>
              <tbody v-if="!fnIsEmptyObj(prdUnitInfo)">
                <template v-for="kkoPayInfo in prdUnitInfo.kko">
                  <tr v-for="(feeData, idx) in kkoPayInfo.postFeeData" :key="kkoPayInfo.productCode+idx">
                    <td v-if="idx == 0" rowspan="3">{{kkoPayInfo.productName}}</td>
                    <td v-if="idx == 0" rowspan="3">{{kkoPayInfo.preFee | formatPrice}} 원</td>
                    <td>{{feeData.FEE_START_CNT | formatPrice}} ~ {{feeData.FEE_END_CNT | formatPrice}}</td>
                    <td class="end">{{feeData.POST_FEE | formatPrice}} 원</td>
                  </tr>
                </template>
              </tbody>
            </table>
          </section>
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
      prdUnitInfo: {}
    }
  },
  filters: {
    formatPrice(val){
      if(String(val).indexOf('.') > 0){
        let arrVal = String(val).split('.');
        if(arrVal.length == 2){
          return String(arrVal[0]).replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '.' + arrVal[1];
        }
      }
      return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
  },
  created() {
    this.fnSelectPrdUnitInfoList();
  },
  methods: {
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