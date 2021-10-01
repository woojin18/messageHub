<template>
  <div>
    <h4 class="topH4">선불 충전관리</h4>
    <div class="menuBox">
      <div class="row">
        <div class="col-xs-6 text-center">
          <!-- table -->
          <table class="table_skin1 ml40" style="width:60%">
            <tbody>
              <tr>
              <td class="text-left color4">남은 충전캐시</td>
              <td class="text-right end">{{this.cashBalance | formatPrice}}</td>
              </tr>	
              <tr>
              <td class="text-left color4">남은 이벤트 캐시</td>
              <td class="text-right end">{{this.eventCashBalance | formatPrice}}</td>
              </tr>	
              <tr>
              <td class="text-left color4 bgColor_sky">남은 캐시</td>
              <td class="text-right end bgColor_sky">{{this.balance | formatPrice}}</td>
              </tr>	
            </tbody>
          </table>
          <!-- //table -->
        </div>	
        <div class="col-xs-6 text-center" style="padding: 2.5% 0">	
          <a @click="fnTossPay" class="btnStyle1">캐시 충전</a>
        </div>
      </div>
    </div>

    <h4>충전 이력조회</h4>
    <div class="row">
      <div class="col-xs-12 mb10">
        <div class="float-left mb10">전체 : <span class="color1"><strong>{{totCnt}}</strong></span>건
          <SelectLayer @fnSelected="fnSelected" classProps="selectStyle2 width120 ml20"></SelectLayer>
        </div>
        
        <!-- table -->
        <table class="table_skin1 bt-000 tbl-striped">
          <colgroup>
            <col style="width:10%">
            <col>
            <col style="width:17%">
            <col style="width:17%">
            <col style="width:17%">
            <col style="width:25%">
            <col>
          </colgroup>
          <thead>
            <tr>
            <th class="text-center lc-1">NO.</th>
            <th class="text-center lc-1">충전캐시</th>
            <th class="text-center lc-1">충전유형</th>
            <th class="text-center lc-1">결제유형</th>
            <th class="text-center lc-1">발생일자</th>
            <th class="text-center lc-1 end">유효기간</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(data, index) in data" :key="index">
              <td class="text-center">{{totCnt-offset-data.rownum+1}}</td>
              <td class="text-center">{{data.amount | formatPrice}}</td>
              <td class="text-center">{{data.cashType}}</td>
              <td class="text-center">{{data.payMtd}}</td>
              <td class="text-center">{{data.regDt}}</td>
              <td class="text-center end">{{data.eventDt}}</td>
            </tr>
          </tbody>
        </table>
        <!-- //table -->

				<PageLayer @fnClick="fnSearch" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
			
      </div>			
    </div>
    <layerPopup />
  </div>
</template>

<script>
import cashApi from "@/modules/cash/service/api"
import tokenSvc from '@/common/token-service';

import layerPopup from "@/modules/cash/components/bp-prePaidCash"

import SelectLayer from '@/components/SelectLayer.vue';
import PageLayer from '@/components/PageLayer.vue';

export default {
  name: 'prepaidCash',
  data() {
    return {
      data: [],
      cashBalance : 0,
      eventCashBalance : 0,
      balance : 0,
			listSize : 10,  // select 박스 value (출력 갯수 이벤트)
			pageNo : 1,  // 현재 페이징 위치
			totCnt : 0,  //전체 리스트 수
			offset : 0, //페이지 시작점
    }
  },
  components: {
    layerPopup,
		PageLayer,
		SelectLayer
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
  mounted() {
		this.fnSearch(1);
    this.fnSearchCash();
  },
  methods: {
		// select 박스 선택시 리스트 재출력
		fnSelected(listSize) {
			this.listSize = Number(listSize);
			this.$refs.updatePaging.fnAllDecrease();
		},
    fnTossPay: function() {
      jQuery("#cashPop").modal("show");
    },

    fnSearch: function(pageNo) {
      var params = {
				"pageNo"		: (this.$gfnCommonUtils.defaultIfEmpty(pageNo, '1'))*1,
				"listSize"		: this.listSize
      };

      cashApi.selectCashHist(params).then(response => {
        var result = response.data;
        if(result.success) {
          this.data = result.data; 
					this.totCnt = result.pageInfo.totCnt;
          this.offset = result.pageInfo.offset;
        }
      });
    },
    // 캐시 불러오기
    fnSearchCash(){
      var params = {
        
      };
      cashApi.selectCashBalance(params).then(response => {
        var result = response.data;
        if(result.success) {
          this.cashBalance = result.data.cashBalance;
          this.eventCashBalance = result.data.eventCashBalance;
          this.balance = result.data.balance;
        }
      });
    }
  }
}
</script>