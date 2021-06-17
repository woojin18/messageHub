<template>
	<div id="content">
		<article>
			<div class="contentHeader mb20">
				<h2>이용내역</h2>
			</div>
			<div class="menuBox">
				<div class="row">
					<div class="col-xs-6 text-center">
						<!--
						<h4 class="mt20">{{date.year}}년 {{date.fullMonth}} 이용금액 <i class="fal fa-calendar-alt"></i></h4>
						<p class="color4">{{date.year}}년 {{date.fullMonth}} 01일 ~ {{date.year}}년 {{date.fullMonth}} {{lastDate}}일</p>
						<h4 class="color000 mt10"><span class="color1">{{totalSumAmount | comma}} </span>원</h4>
						
						<h4 class="mt20">2021년 06월 이용금액 <i class="fal fa-calendar-alt"></i></h4>
						-->
						<h4 class="mt20">{{searchData.searchYear}}년 {{searchData.searchMonth}}월 이용금액 <input type="hidden" id="monthcalendar" :value="searchData.searchStartDate" /> </h4>
						<p class="color4">{{searchData.searchYear}}년 {{searchData.searchMonth}}월 01일 ~ {{searchData.searchYear}}년 {{searchData.searchMonth}}월 {{monthLastDate}}일</p>
						<h4 class="color000 mt10"><span class="color1">{{totalSumAmount | comma}} </span>원</h4>
					</div>
					<div class="col-xs-6 text-center">
						<table class="table_skin1">
							<tbody>
								<tr>
									<td class="text-center">결제유형</td>
									<td class="text-center">선불</td>
									<td class="end">후불</td>
								</tr>
								<tr>
									<td class="text-center color4">이용금액</td>
									<td class="text-center ">{{prepaidAmount | comma}}원</td>
									<td class="end">{{postpaidAmount | comma}}원</td>
								</tr>
								<tr>
									<td class="text-center color4">부가세</td>
									<td class="text-center ">{{prepaidTaxAmount | comma}}원</td>
									<td class="end">{{postpaidTaxAmount | comma}}원</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="row consolMarginTop">
				<div class="col-xs-12">
					<div class="of_h inline">
						<div class="float-right">
							<a @click="fnExcelDownLoad" class="btnStyle2 borderGray" activity="READ"> 다운로드</a>
						</div>
					</div>
					<div class="of_h inline">
						<div class="float-left">전체 : <span class="color1"><strong>{{totCnt}}</strong></span>건
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 consolMarginTop">
							<table class="table_skin1 bt-000 tbl-striped">
								<colgroup>
									<col style="width:15%">
									<col>
									<col style="width:15%">
									<col style="width:15%">
									<col style="width:15%">
									<col style="width:15%">
								</colgroup>
								<thead>
									<tr>
									<th class="text-center lc-1">프로젝트</th>
									<th class="text-center lc-1">이용채널</th>
									<th class="text-center lc-1">결제유형</th>
									<th class="text-center lc-1">신청일</th>
									<th class="text-center lc-1">건수</th>
									<th class="text-center lc-1 end">이용요금</th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="(data, index) in useHistoryList" :key="index">
										<td class="text-left">{{ data.projectName }}</td>
										<td class="text-left">{{ data.useCh }}</td>
										<td class="text-center">{{ data.payTypeName }}</td>
										<td class="text-center">{{ data.regDt }}</td>
										<td class="text-right">{{ data.cnt | comma}}</td>
										<td class="text-right end">{{ data.sumChGrpAmount | comma}}원</td>
									</tr>
									<tr class="of_h">
									<th class="text-left end bgColor_sky" colspan="6">합계<span class="float-right">{{sumAmount | comma}}원</span></th>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<!-- pagination Start -->
			<div id="pageContent">
				<PageLayer @fnClick="fnSearch" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
			</div>
			<!-- pagination End-->
			<!-- //본문 -->
		</article>
	</div>
</template>

<script>
import useApi from '../service/useApi'
import confirm from "@/modules/commonUtil/service/confirm";
import tokenSvc from '@/common/token-service';
import PageLayer from '@/components/PageLayer.vue';

export default {
	name: "useHistoryInfo",
	components: {
		PageLayer,
	},
	props: {
		searchData : {
			type: Object,
			require: false,
			default: function() {
				return {
					'searchStartDate' : this.$gfnCommonUtils.getCurretDate('yyyymm'),
					'searchYear' : this.$gfnCommonUtils.getCurretDate('yyyy'),
					'searchMonth' : this.$gfnCommonUtils.getCurretDate('mm'),
				}
			}
		}
	},
	data() {
		return {
			// searchDate : this.$gfnCommonUtils.getCurretDate(),
			listSize : 10,	// select 박스 value (출력 갯수 이벤트)
			pageNo : 1,		// 현재 페이징 위치
			totCnt : 0,		//전체 리스트 수
			offset : 0,		 //페이지 시작점
			searchMon: '',
			useHistoryList: [],
			sumAmount : '',
			totalSumAmount: '',
			prepaidAmount: '',
			postpaidAmount: '',
			prepaidTaxAmount: '',
			postpaidTaxAmount: '',
			monthLastDate: new Date(this.$gfnCommonUtils.getCurretDate('yyyy'), this.$gfnCommonUtils.getCurretDate('mm'), 0).getDate(),
		}
	},
	mounted() {
		this.fnInit();
		this.fnSearch();
		
	},
	filters:{
		comma(val) {
			return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		}
	},
	methods: {
		fnInit() {
			this.fnCalendarInit();
		},
		fnCalendarInit() {
			var vm = this;
			jQuery("#monthcalendar").monthpicker({
				monthNames:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
				monthNamesShort:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
				changeYear:true,
				changeMonth:true,
				dateFormat:'yymm',
				buttonImage:'../../se2/images/datepicker.png',
				buttonImageOnly: true,
				showOn: 'button',
				onSelect: function() { // 선택월의 말일계산
					var date = jQuery(this).val();
					vm.searchData.searchStartDate = date;
					vm.searchData.searchYear = date.substring(0,4);
					vm.searchData.searchMonth = date.substring(4,6);
					vm.monthLastDate = new Date(parseInt(vm.searchData.searchYear), parseInt(vm.searchData.searchMonth), 0).getDate();
					vm.fnSearchUseHistory();
				},
			});
		},

		fnSearch(pageNum) {
			this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNum, '1'))*1;
			this.fnSearchUseHistory();
		},
		// 이용내역 조회
		async fnSearchUseHistory() {
			let params = {
				pageNo: this.pageNo,
				listSize: this.listSize,
				role: tokenSvc.getToken().principal.role,
				corpId: tokenSvc.getToken().principal.corpId,
				searchMonth: jQuery('#monthcalendar').val(),
				userId: tokenSvc.getToken().principal.userId,
			};

			await useApi.selectUseHistory(params).then(response =>{
				var result = response.data;
				if(result.success) {
					this.useHistoryList = result.data.useHistoryList;
					this.sumAmount = result.data.sumAmount;
					this.totalSumAmount = result.data.totalSumAmount;
					this.prepaidAmount = result.data.prepaidAmount;
					this.postpaidAmount = result.data.postpaidAmount;
					this.prepaidTaxAmount = result.data.prepaidTaxAmount;
					this.postpaidTaxAmount = result.data.postpaidTaxAmount;
					this.totCnt = result.pageInfo.totCnt;
					this.offset = result.pageInfo.offset;
				} else {
					confirm.fnAlert("", result.message);
				}
			});
		},
		//엑셀 다운로드
		fnExcelDownLoad() {
			let params = {
				role: tokenSvc.getToken().principal.role,
				corpId: tokenSvc.getToken().principal.corpId,
				searchMonth: this.searchMonth,
				userId: tokenSvc.getToken().principal.userId,
			};
			useApi.excelDownloadUseHistory(params);
		},
	}
}
</script>