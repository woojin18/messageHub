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
            <th class="text-center lc-1">충전캐시</th>
            <th class="text-center lc-1">충전유형</th>
            <th class="text-center lc-1">결제유형</th>
            <th class="text-center lc-1">발생일자</th>
            <th class="text-center lc-1 end">유효기간</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(data, index) in data" :key="index">
              <td class="text-center">{{index+1}}</td>
              <td class="text-center">{{data.amount | formatPrice}}</td>
              <td class="text-center">{{data.cashType}}</td>
              <td class="text-center">{{data.payMtd}}</td>
              <td class="text-center">{{data.regDt}}</td>
              <td class="text-center end">{{data.eventDt}}</td>
            </tr>
          </tbody>
        </table>
        <!-- //table -->

        <Paging :pageInfo.sync="pageInfo" />
      </div>			
    </div>
    <layerPopup />
  </div>
</template>

<script>
import cashApi from "@/modules/cash/service/api"
import tokenSvc from '@/common/token-service';

import layerPopup from "@/modules/cash/components/bp-prePaidCash"

import Paging from "@/modules/commonUtil/components/bc-paging"
import PagingCnt from "@/modules/commonUtil/components/bc-pagingCnt"

export default {
  name: 'prepaidCash',
  data() {
    return {
      data: [],
      pageInfo: {},
      cashBalance : 0,
      eventCashBalance : 0,
      balance : 0,
    }
  },
  components: {
    layerPopup,
    Paging,
    PagingCnt
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
    this.pageInfo = {
      "pageCnt"   : [3, 5, 7],  //표시할 개수 리스트
      "selPageCnt": 5,          //선택한 표시 개수
      "selPage"   : 1,          //선택한 페이지
      "rowNum"    : 1           //총개수
    };

    this.fnSearch();
    this.fnSearchCash();
  },
  methods: {
    fnTossPay: function() {
      jQuery("#cashPop").modal("show");
    },

    fnSearch: function() {
      var params = {
        "pageInfo" : this.pageInfo
      };

      cashApi.selectCashHist(params).then(response => {
        var result = response.data;
        if(result.success) {
          this.data = result.data; 
          this.pageInfo = result.pageInfo;
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