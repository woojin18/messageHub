<template>
	<div id="content">
		<article>
			<div class="contentHeader mb20">
				<h2>이용내역</h2>
				<!--
				<month-picker @change="showDate" lang="ko" ></month-picker>
				-->
			</div>
			<div class="menuBox">
				<div class="row">
					<div class="col-xs-6 text-center">
						<!--
						<h4 class="mt20">{{date.year}}년 {{date.fullMonth}} 이용금액 <i class="fal fa-calendar-alt"></i></h4>
						<p class="color4">{{date.year}}년 {{date.fullMonth}} 01일 ~ {{date.year}}년 {{date.fullMonth}} {{lastDate}}일</p>
						<h4 class="color000 mt10"><span class="color1">{{totalSumAmount | comma}} </span>원</h4>
						-->
						<h4 class="mt20">2021년 06월 이용금액 <i class="fal fa-calendar-alt"></i></h4>
						<p class="color4">2021년 06월 01일 ~ 2021년년 06월 31일</p>
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
							<a @click="fnExcelDownLoad" class="btnStyle2 borderGray">다운로드</a>
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
	<!-- //content -->
</template>

<script>
import useApi from '../service/useApi'
import confirm from "@/modules/commonUtil/service/confirm";
//import {eventBus} from "@/modules/commonUtil/service/eventBus";
import tokenSvc from '@/common/token-service';
//import { MonthPickerInput } from 'vue-month-picker'
//import { MonthPicker } from 'vue-month-picker'
import PageLayer from '@/components/PageLayer.vue';

export default {
	name: "useHistoryInfo",
	components: {
		// MonthPickerInput,
		//MonthPicker,
		PageLayer,
	},
	props: {
		// lang: {
		// 	type: String,
		// 	require: false,
		// 	default: function() {
		// 		return 'ko';
		// 	}
		// }
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
			// date: {
			// 	from: null,
			// 	to: null,
			// 	month: null,
			// 	year: null,
			// 	fullMonth: null,
			// },
			sumAmount : '',
			totalSumAmount: '',
			prepaidAmount: '',
			postpaidAmount: '',
			prepaidTaxAmount: '',
			postpaidTaxAmount: '',
			lastDate: 0,
			searchMonth: '',
		}
	},
	mounted() {
		//this.fnSearch();
		//var lastDate = new Date(2021, 7, 0).getDate();
		//console.log(">>> month :" + this.date.month);
		console.log(">>> this.$gfnCommonUtils.getCurretDate() :" + this.$gfnCommonUtils.getCurretDate());
		
	},
	filters:{
		comma(val) {
			return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		}
	},
	methods: {
		fnSearch(pageNum) {
			this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNum, '1'))*1;
			this.fnSearchUseHistory();
		},
		// showDate(date) {
		// 	this.date = date;
		// 	this.date.fullMonth = this.date.month;
		// 	let length = this.date.month.length;
		// 	if(length == 2) this.date.fullMonth = '0' + this.date.fullMonth;
		// 	//월 말일 계산
		// 	let endIndex = this.date.fullMonth.lastIndexOf('월');
		// 	let monthNum = parseInt(this.date.fullMonth.substring(0, endIndex));
		// 	this.lastDate = new Date(this.date.year, monthNum, 0).getDate();

		// 	this.searchMonth = this.date.year + this.date.fullMonth;
		// 	endIndex = this.searchMonth.lastIndexOf('월');
		// 	this.searchMonth = this.searchMonth.substring(0, endIndex);
		// 	this.fnSearch();
		// },
		// 이용내역 조회
		async fnSearchUseHistory() {
			let params = {
				pageNo: this.pageNo,
				listSize: this.listSize,
				role: tokenSvc.getToken().principal.role,
				corpId: tokenSvc.getToken().principal.corpId,
				searchMonth: this.searchMonth,
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