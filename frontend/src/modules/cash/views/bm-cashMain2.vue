<template>
    <div>	
		<article>
			<div class="contentHeader mb20">
				<h2>정산금액</h2>
			</div>
			<!-- 본문 -->				
			<div class="menuBox mt15 of_h">
				<h4 class="inline-block" style="width:6%">정산년월</h4>
				<input type="text" id="startDate" class="monthpicker inputStyle maxWidth120 mr5" :value="params.srcMonth" readonly style="width:16%">
				<a @click="fnPageNoResetSearch" class="btnStyle1 float-right" activity="READ" title="검색">검색</a>
			</div>	

			<div class="row mt30">
				<div class="col-xs-12">		
					<div class="of_h inline">
						<div class="float-right mb15">
							<a @click="fnExcelDownLoad" class="btnStyle2 borderGray" title="다운로드">다운로드 <i class="fal fa-arrow-to-bottom"></i></a>
						</div>
					</div>
					<!-- 15개씩 보기 -->
					<div style="display:none">
						<div class="float-left">전체 : <span class="color1"><strong>{{totCnt}}</strong></span>건
							<SelectLayer @fnSelected="fnSelected" classProps="selectStyle2 width120 ml20"></SelectLayer>
						</div>
					</div>
					<!-- //15개씩 보기 -->
					<!-- table -->
					<table class="table_skin1 bt-000 tbl-striped">
						<colgroup>
							<col style="width:15%" span="2">
							<col style="width:*%">
							<col style="width:15%" span="3">
						</colgroup>
						<thead>
							<tr>
							<th class="text-center lc-1">정산년월</th>
							<th class="text-center lc-1">청구유형</th>
							<th class="text-center lc-1">프로젝트/부서명</th>
							<th class="text-center lc-1">상품명</th>
							<th class="text-center lc-1">건수</th>
							<th class="text-center lc-1 end">정산금액</th>
							</tr>
						</thead>
            <tbody>
              <tr v-for="(data, idx) in list">
								<td class="text-center">{{ data.ym }}</td>
								<td class="text-center">{{ data.billType }}</td>
								<td class="text-center">{{ data.projectName }}</td>
								<td class="text-center">{{ data.productName }}</td>
								<td class="text-right">{{ data.cnt | comma }}</td>
								<td class="text-right end">{{ data.amt | comma }}</td>
              </tr>	
              <tr v-if="list.length == 0">
                <td class="text-center" colspan="6">검색된 내용이 없습니다.</td>
              </tr>
          </tbody>
					</table>
					<!-- //table -->

                    <!-- pagination -->
                    <div id="pageContent">
                    <PageLayer @fnClick="fnSearch" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
                    </div>
				</div>			
			</div>
			<!-- //본문 -->
		</article>
    </div>
</template>

<script>
import cashApi from "@/modules/cash/service/api"
import PageLayer from '@/components/PageLayer.vue';
import SelectLayer from '@/components/SelectLayer.vue';
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  data() {
    return {
      params: {
        srcMonth : this.$gfnCommonUtils.strDateAddMonth(this.$gfnCommonUtils.getCurretDate(), -1)
      },
      listSize : 10,  // select 박스 value (출력 갯수 이벤트)
      pageNo : 1,  // 현재 페이징 위치
      totCnt : 0,  //전체 리스트 수
      offset : 0, //페이지 시작점
      list: [],
      rowData: {},
      popReset: 0
    }
  },
  components: {
    SelectLayer,
    PageLayer
  },
	filters: {
		comma (val) {
			return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ',')
		}
	},
  mounted() {
	this.fnCalendarInit();
    this.fnPageNoResetSearch();
  },
  methods: {
	fnCalendarInit() {
		jQuery("#startDate").monthpicker({
			monthNames:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			monthNamesShort:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			changeYear:false,
			dateFormat:'yy-mm',
			buttonImage:'../../se2/images/datepicker.png',
			buttonImageOnly: true,
			showOn: 'button',
			maxDate: this.$gfnCommonUtils.strDateAddMonth(this.$gfnCommonUtils.getCurretDate(), -1)
		});
	},
    fnSelected(listSize) {
      this.listSize = Number(listSize);
      this.$refs.updatePaging.fnAllDecrease();
    },
    fnPageNoResetSearch(){
      this.$refs.updatePaging.fnAllDecrease();
    },
    async fnSearch(pageNo) {
      this.rowData = {}
      this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNo, '1'))*1;
			this.params.srcMonth = jQuery("#startDate").val();
      var params = Object.assign({}, this.params)
      params.pageNo = this.pageNo;
      params.listSize = this.listSize;
      await cashApi.selectCalList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.list = result.data;
          this.totCnt = result.pageInfo.totCnt;
          this.offset = result.pageInfo.offset;
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
		//엑셀 다운로드
		fnExcelDownLoad() {			
			this.params.srcMonth = jQuery("#startDate").val();
      var params = Object.assign({}, this.params)

			cashApi.excelDownloadCalList(params);
		}
  }
}
</script>

