<template>
	<article>
		<div class="row">
			<div class="col-xs-12 mt10">
				<div class="menuBox">
					<div>
						<h4 class="inline-block" style="width:6%">발송일</h4>
						<div class="inline-block" style="width:25%">
							<div v-show="searchData.searchDateType == 'DAY'">
								<Calendar @update-date="fnUpdateStartDate" calendarId="searchStartDate" classProps="datepicker inputStyle" styleProps="width:40%" :initDate="searchData.searchStartDate"></Calendar>
								<span style="padding:0 11px">~</span>
								<Calendar @update-date="fnUpdateEndDate" calendarId="searchEndDate" classProps="datepicker inputStyle" styleProps="width:40%" :initDate="searchData.searchEndDate"></Calendar>
							</div>
							<div v-show="searchData.searchDateType == 'MONTH'">
								<input type="text" id="startDate" class="monthpicker inputStyle maxWidth120 mr5" :value="searchStartMonth">
								<span style="padding:0 11px">~</span>
								<input type="text" id="endDate" class="monthpicker inputStyle maxWidth120 mr5" :value="searchEndMonth">
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
						<h4 class="inline-block" style="width:6%">프로젝트명</h4>
						<select class="selectStyle2" style="width:25%" v-model="searchData.searchProjectId">
							<option value="">전체</option>
							<option v-for="(content, index) in projectItems" :key="index" :value="content.projectId">
								{{ content.projectName }}
							</option>
						</select>
						<div class="inline-block" style="width:30%">
							<input type="radio" name="smsmmsType" id="all" value="" v-model="searchData.searchSmsmmsType">
							<label for="all" class="ml20">전체</label>
							<input type="radio" name="smsmmsType" id="sms" value="SMS" v-model="searchData.searchSmsmmsType">
							<label for="sms">SMS</label>
							<input type="radio" name="smsmmsType" id="mms" value="MMS" v-model="searchData.searchSmsmmsType">
							<label for="mms">MMS</label>
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
				</div>
				<div v-else>
					<p class="mt10">* 발송일 기준 최근 5년까지 조회가 가능합니다.(최대 조회 가능 범위 : 12개월)</p>
				</div>
				<div class="row">
					<div class="col-xs-12 consolMarginTop">
						<table class="table_skin1 bt-000 tbl-striped">
							<colgroup>
								<col style="width:10%">
								<col style="width:25%">
								<col style="width:25%">
								<col style="width:10%">
								<col style="width:10%">
								<col style="width:10%">
							</colgroup>
							<thead>
								<tr>
								<th class="text-center lc-1">날짜</th>
								<th class="text-center lc-1">프로젝트명</th>
								<th class="text-center lc-1">API KEY</th>
								<th class="text-center lc-1">발송</th>
								<th class="text-center lc-1">성공</th>
								<th class="text-center lc-1 end">성공율</th>
								</tr>
							</thead>
							<tbody>
								<tr v-for="(data, index) in statisItem" :key="index">
									<td class="text-center">{{data.sendDate}}</td>
									<td class="text-left">{{data.projectName}}</td>
									<td class="text-left">{{data.apiKey}}</td>
									<td class="text-right">{{data.totCnt | comma}}</td>
									<td class="text-right">{{data.succCnt | comma}}</td>
									<td class="text-right end">{{data.succRatio}}</td>
								</tr>
								<tr v-if="statisItem.length > 0" class="of_h">
									<th class="text-left end bgColor_sky" colspan="3">합계</th>
									<th class="text-right">{{sumTotCnt | comma}}</th>
									<th class="text-right">{{sumSuccCnt | comma}}</th>
									<th class="text-right end">{{totalSuccRatio}}</th>
								</tr>
								<tr v-if="statisItem.length == 0">
									<td class="text-center"></td>
									<td class="text-center" colspan="6">검색된 내용이 없습니다.</td>
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
import statisticsAdminApi from '../service/statisticsAdminApi'
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
					'searchProjectId' : '',
					'searchDateType' : 'DAY',
					'searchChanType' : 'SMSMMS',
					'searchSmsmmsType' : '',
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
			projectItems: [],
			statisItem: [],
			sumTotCnt: 0,
			sumSuccCnt: 0,
			totalSuccRatio: 0,
			searchStartMonth : this.$gfnCommonUtils.strDateAddMonth(this.$gfnCommonUtils.getCurretDate(), -6+1),
			searchEndMonth : this.$gfnCommonUtils.getCurretDate('yyyy-mm'),
		}
	},
	mounted() {
		this.fnCalendarInit();
		this.fnSearchProjectList();
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

			await statisticsAdminApi.selectStatisList(params).then(response =>{
				let result = response.data;
				if (result.success) {
					this.statisItem = result.data;
					this.fnSumCalc();
				} else {
					confirm.fnAlert(this.title, result.message);
				}
			});
		},
		//프로젝트 목록 조회
		async fnSearchProjectList() {
			var params = {
			}
			await statisticsAdminApi.selectProjectList(params).then(response =>{
				var result = response.data;
				if(result.success) {
					this.projectItems = result.data;
				} else {
					confirm.fnAlert(this.title, result.message);
				}
			});
		},
		//검색일자변경
		/*
		fnSetIntervalSearchDate(interval){
			this.searchDateInterval = interval;
			this.searchData.searchEndDate = this.$gfnCommonUtils.getCurretDate();
			this.searchData.searchStartDate = this.$gfnCommonUtils.strDateAddDay(this.searchData.searchEndDate, -this.searchDateInterval);
		},*/
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
				maxDate:0,
			});
			jQuery("#endDate").monthpicker({
				monthNames:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
				monthNamesShort:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
				changeYear:false,
				dateFormat:'yy-mm',
				buttonImage:'../../se2/images/datepicker.png',
				buttonImageOnly: true,
				showOn: 'button',
				maxDate:0,
			});
		},
		// 합계
		fnSumCalc() {
			this.sumTotCnt = 0;
			this.sumSuccCnt = 0;
			this.totalSuccRatio = '';

			let item = this.statisItem;
			for(var i in item) {
				this.sumTotCnt += item[i].totCnt;
				this.sumSuccCnt += item[i].succCnt;
			}
			this.totalSuccRatio = Math.floor(((this.sumSuccCnt/this.sumTotCnt) * 100)+0.5);
			this.totalSuccRatio = this.totalSuccRatio + '%';
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
	},

}
</script>