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
              <td class="text-center">{{data.amount}}</td>
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
      pageInfo: {}
    }
  },
  components: {
    layerPopup,
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

    this.fnSearch();
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
    }
  }
}
</script>