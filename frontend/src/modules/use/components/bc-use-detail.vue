<template>
	<article>
		<div class="row mt15">
			<div class="col-xs-12">
				<div class="menuBox">
					<div>
						<h4 class="inline-block" style="width:10%">발송일</h4>
						<div class="inline-block" style="width:25%">
							<div v-show="searchData.searchDateType == 'DAY'">
								<Calendar @update-date="fnUpdateStartDate" calendarId="searchStartDate" classProps="datepicker inputStyle" styleProps="width:40%" :initDate="searchData.searchStartDate" :maxDate="searchData.maxDate"></Calendar>
								<span style="padding:0 11px">~</span>
								<Calendar @update-date="fnUpdateEndDate" calendarId="searchEndDate" classProps="datepicker inputStyle" styleProps="width:40%" :initDate="searchData.searchEndDate" :maxDate="searchData.maxDate"></Calendar>
							</div>
							<div v-show="searchData.searchDateType == 'MONTH'">
								<input type="text" id="startDate" class="monthpicker inputStyle maxWidth120 mr5" :value="searchStartMonth" @change="fnSearch" readonly>
								<span style="padding:0 11px">~</span>
								<input type="text" id="endDate" class="monthpicker inputStyle maxWidth120 mr5" :value="searchEndMonth" @change="fnSearch" readonly>
							</div>
						</div>
						<div class="inline-block" style="width:30%">
							<input type="radio" name="dayMonth" value="DAY" id="day" checked="" v-model="searchData.searchDateType">
							<label for="day" class="ml20">일별</label>
							<input type="radio" name="dayMonth" value="MONTH" id="month" v-model="searchData.searchDateType">
							<label for="month">월별</label>
						</div>
					</div>
					<div class="consolMarginTop">
						<h4 class="inline-block" style="width:10%">사업자/프로젝트</h4>
						<div class="inline-block" style="width:30%">
							<input type="radio" id="corp" value="corp" v-model="searchData.searchType">
							<label for="corp">사업자</label>
							<input type="radio" id="project" value="project" v-model="searchData.searchType">
							<label for="project" class="ml10">프로젝트</label>
						</div>
						<a @click="fnSearch" class="btnStyle1 float-right" activity="READ">조회</a>
					</div>
				</div>
			</div>
		</div>

		<div class="tab-content" style="border-top:1px solid #D5D5D5">
			<div role="tabpanel" class="tab-pane active" id="productCate3">
				<div v-if="searchData.searchDateType == 'DAY'">
					<p class="mt10">* 발송일 기준 최근 12개월까지 조회가 가능합니다.(최대 조회 가능 범위 : 1개월)</p>
					<div class="float-right">
						<a @click="fnExcelDownLoad" class="btnStyle2 borderGray" activity="READ"> 다운로드</a>
					</div>
				</div>
				<div v-else>
					<p class="mt10">* 발송일 기준 최근 5년까지 조회가 가능합니다.(최대 조회 가능 범위 : 12개월)</p>
					<div class="float-right">
						<a @click="fnExcelDownLoad" class="btnStyle2 borderGray" activity="READ"> 다운로드</a>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 consolMarginTop">
						<table class="table_skin1 bt-000 tbl-striped">
							<colgroup>
								<col style="width:10%">
								<col style="width:10%">
								<col style="width:6%">
								<col style="width:6%">
								<col style="width:6%">
								<col style="width:6%">
								<col style="width:6%">
								<col style="width:6%">
								<col style="width:6%">
								<col style="width:6%">
								<col style="width:6%">
								<col style="width:6%">
								<col style="width:6%">
								<col style="width:6%">
								<col style="width:6%">
							</colgroup>
							<thead>
								<tr>
								<th class="text-center lc-1" rowspan="2">날짜</th>
								<th class="text-center lc-1" rowspan="2">사업자/프로젝트</th>
								<th class="text-center lc-1 end" colspan="13">건수 / 금액(원,VAT별도)</th>
								</tr>
								<tr style="border-top: 1px solid #D5D5D5;">
								<th class="text-center lc-1">합계</th>
								<th class="text-center lc-1">SMS</th>
								<th class="text-center lc-1">LMS</th>
								<th class="text-center lc-1">MMS</th>
								<th class="text-center lc-1">RCS<br/>템플릿</th>
								<th class="text-center lc-1">RCS<br/>SMS</th>
								<th class="text-center lc-1">RCS<br/>LMS</th>
								<th class="text-center lc-1">RCS<br/>MMS</th>
								<th class="text-center lc-1">알림톡</th>
								<th class="text-center lc-1">친구톡<br/>Text</th>
								<th class="text-center lc-1">친구톡<br/>이미지</th>
								<th class="text-center lc-1">친구톡<br/>와이드</th>
								<th class="text-center lc-1 end">PUSH</th>
								</tr>
							</thead>
							<tbody>
								<tr v-for="(data, index) in statisItem" :key="index">
									<td class="text-center">{{data.ymd}}</td>
									<td class="text-center">{{data.projectName}}</td>
									<td class="text-right">{{data.succCnt | comma}}<br/>{{data.succAmt | comma}}</td>
									<td class="text-right">{{data.smsCnt | comma}}<br/>{{data.smsAmt | comma}}</td>
									<td class="text-right">{{data.lmsCnt | comma}}<br/>{{data.lmsAmt | comma}}</td>
									<td class="text-right">{{data.mmsCnt | comma}}<br/>{{data.mmsAmt | comma}}</td>
									<td class="text-right">{{data.rtplCnt | comma}}<br/>{{data.rtplAmt | comma}}</td>
									<td class="text-right">{{data.rsmsCnt | comma}}<br/>{{data.rsmsAmt | comma}}</td>
									<td class="text-right">{{data.rlmsCnt | comma}}<br/>{{data.rlmsAmt | comma}}</td>
									<td class="text-right">{{data.rmmsCnt | comma}}<br/>{{data.rmmsAmt | comma}}</td>
									<td class="text-right">{{data.kalt1Cnt | comma}}<br/>{{data.kalt1Amt | comma}}</td>
									<td class="text-right">{{data.kfrt1Cnt | comma}}<br/>{{data.kfrt1Amt | comma}}</td>
									<td class="text-right">{{data.kfrm2Cnt | comma}}<br/>{{data.kfrm2Amt | comma}}</td>
									<td class="text-right">{{data.kfrm3Cnt | comma}}<br/>{{data.kfrm3Amt | comma}}</td>
									<td class="text-right">{{data.pushCnt | comma}}<br/>{{data.pushAmt | comma}}</td>
								</tr>
								<tr v-if="statisItem.length == 0">
									<td class="text-center"></td>
									<td class="text-center" colspan="9">검색된 내용이 없습니다.</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</article>
</template>

<script>
import Calendar from "@/components/Calendar.vue";
import useApi from '../service/useApi'
import tokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm";

export default {
	components: {
		Calendar,
	},
	props: {
		searchData : {
			type: Object,
			require: false,
			default: function() {
				return {
					'searchStartDate' : this.$gfnCommonUtils.strDateAddDay(this.$gfnCommonUtils.getCurretDate(), -7),
					'searchEndDate' : this.$gfnCommonUtils.getCurretDate(),
					'maxDate' : this.$gfnCommonUtils.getCurretDate(),
					'searchDateType' : 'DAY',
					'searchType' : 'corp'
				}
			}
		},
	},
	filters:{
		comma(val) {
			return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		}
	},
	data() {
		return {
			title: '발송일별통계',
			statisItem: [],
			searchStartMonth : this.$gfnCommonUtils.strDateAddMonth(this.$gfnCommonUtils.getCurretDate(), -3),
			searchEndMonth : this.$gfnCommonUtils.strDateAddMonth(this.$gfnCommonUtils.getCurretDate(), 0)
		}
	},
	mounted() {
		this.fnCalendarInit();
		this.fnSearch();
	},
	methods: {
		fnSearch() {
			if(!this.fnChkValidation()) return false;
			this.fnStatisList();
		},
		// 전송건수 리스트 조회
		async fnStatisList() {
			let params = Object.assign({}, this.searchData);
			params.corpId = tokenSvc.getToken().principal.corpId;
			params.searchStartMonth = jQuery("#startDate").val();
			params.searchEndMonth = jQuery("#endDate").val();
			this.searchStartMonth = jQuery("#startDate").val();
			this.searchEndMonth = jQuery("#endDate").val();

			await useApi.selectUseDetailList(params).then(response =>{
				let result = response.data;
				if (result.success) {
					this.statisItem = result.data;
				} else {
					confirm.fnAlert(this.title, result.message);
				}
			});
		},
		fnUpdateStartDate(sltDate) {
			this.searchData.searchStartDate = sltDate;
		},
		fnUpdateEndDate(sltDate) {
			this.searchData.searchEndDate = sltDate;
		},
		fnCalendarInit() {
			jQuery("#startDate").monthpicker({
				monthNames:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
				monthNamesShort:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
				changeYear:false,
				dateFormat:'yy-mm',
				buttonImage:'../../se2/images/datepicker.png',
				buttonImageOnly: true,
				showOn: 'button',
				maxDate: this.$gfnCommonUtils.strDateAddMonth(this.$gfnCommonUtils.getCurretDate(), 0)
			});
			jQuery("#endDate").monthpicker({
				monthNames:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
				monthNamesShort:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
				changeYear:false,
				dateFormat:'yy-mm',
				buttonImage:'../../se2/images/datepicker.png',
				buttonImageOnly: true,
				showOn: 'button',
				maxDate: this.$gfnCommonUtils.strDateAddMonth(this.$gfnCommonUtils.getCurretDate(), 0)
			});
		},
		// 유효성 검사
		fnChkValidation() {
			if(this.searchData.searchDateType == 'DAY') {
				// 1년전 일자
				let beforeOneYear = this.$gfnCommonUtils.strDateAddDay(this.$gfnCommonUtils.getCurretDate(), -365).replace(/[^0-9]/g, '');
				// 종료일의 한달 전 일자
				let dateParts = this.searchData.searchEndDate.split('-');
				let beforeOneMonth = new Date(dateParts[0], dateParts[1] - 1, dateParts[2]);
				beforeOneMonth.setMonth(beforeOneMonth.getMonth() -1 );
				beforeOneMonth = this.$gfnCommonUtils.formatDate(beforeOneMonth, 'yyyymmdd');

				if(this.searchData.searchStartDate && this.searchData.searchEndDate) {
					if(this.searchData.searchStartDate.replace(/[^0-9]/g, '') > this.searchData.searchEndDate.replace(/[^0-9]/g, '')){
						confirm.fnAlert(this.title, '시작일은 종료일보다 클 수 없습니다.');
						return false;
					}
				}

				if(this.searchData.searchStartDate.replace(/[^0-9]/g, '') < beforeOneYear
				|| this.searchData.searchEndDate.replace(/[^0-9]/g, '') < beforeOneYear) {
					confirm.fnAlert(this.title, '발송일 기준 최근 12개월까지 조회가 가능합니다.');
					return false;
				}

				if(this.searchData.searchStartDate.replace(/[^0-9]/g, '') < beforeOneMonth) {
					confirm.fnAlert(this.title, '최대 조회 가능 범위는 1개월 입니다.');
					return false;
				}

			} else {
				// 5년전 월
				let date = new Date();
				date.setFullYear(date.getFullYear() -5);
				let beforeFiveYear = this.$gfnCommonUtils.formatDate(date, 'yyyymm');

				// 종료월의 12개월 이전 월
				let searchStartMonth = jQuery("#startDate").val();
				let searchEndMonth = jQuery("#endDate").val();
				
				let dateParts = searchEndMonth.split('-');
				let endMonth = new Date(dateParts[0], dateParts[1] - 1);
				endMonth.setMonth(endMonth.getMonth()-12);
				let before12Month = this.$gfnCommonUtils.formatDate(endMonth, 'yyyymm');
				
				if(searchStartMonth && searchEndMonth) {
					if(searchStartMonth.replace(/[^0-9]/g, '') > searchEndMonth.replace(/[^0-9]/g, '')){
						confirm.fnAlert(this.title, '시작월은 종료월보다 클 수 없습니다.');
						return false;
					}
				}

				if(searchStartMonth.replace(/[^0-9]/g, '') < beforeFiveYear
				|| searchEndMonth.replace(/[^0-9]/g, '') < beforeFiveYear) {
					confirm.fnAlert(this.title, '발송일 기준 최근 5년까지 조회가 가능합니다.');
					return false;
				}

				if(searchStartMonth.replace(/[^0-9]/g, '') < before12Month) {
					confirm.fnAlert(this.title, '최대 조회 가능 범위는 12개월 입니다.');
					return false;
				}
			}
			return true;
		},
		
		//엑셀 다운로드
		fnExcelDownLoad() {			
			let params = Object.assign({}, this.searchData);
			params.corpId = tokenSvc.getToken().principal.corpId;
			params.searchStartMonth = jQuery("#startDate").val();
			params.searchEndMonth = jQuery("#endDate").val();
			this.searchStartMonth = jQuery("#startDate").val();
			this.searchEndMonth = jQuery("#endDate").val();

			useApi.excelDownloadUseDetailList(params);
		},
	},

}
</script>