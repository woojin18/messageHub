<template>
	<div>
		<article>
			<div class="contentHeader mb20">
				<h2>월별발송통계</h2>
			</div>
			<div class="row">
				<div class="col-xs-12 mt10">
					<div class="menuBox">
						<div>
							<h4 class="inline-block" style="width:6%">조회기간</h4>
							<div class="inline-block" style="width:30%">
								<input type="text" id="startDate" class="monthpicker inputStyle maxWidth120 mr5" :value="searchData.searchStartDate" maxlength="7" readonly>
								~
								<input type="text" id="endDate" class="monthpicker inputStyle maxWidth120 mr5" :value="searchData.searchEndDate" maxlength="7"  readonly>
							</div>
							<ul class="tab_s2 ml20">
								<li :class="this.searchDateInterval==3 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(3);" title="3개월 서비스 검색">3개월</a></li>
								<li :class="this.searchDateInterval==6 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(6);" title="6개월 서비스 검색">6개월</a></li>
								<li :class="this.searchDateInterval==12 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(12);" title="12개월 서비스 검색">12개월</a></li>
							</ul>
							<a @click="fnSearch" class="btnStyle1 float-right" activity="READ">조회</a>
						</div>
					</div>
				</div>
			</div>
			<div class="mt20">
				<ul class="tab_s4_2 of-h" style="width:100%">
					<li @click="fnSetChartData('ALL')" id="setAll" style="width:12.5%" class="active">
						<a class="inline-block text-center active">
							<h5>총계</h5>
							<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.totalSuccCnt }}<br></span><span class="text">성공</span></p>
							<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.totalFailCnt }}<br></span><span class="text">실패</span></p>
						</a>
					</li>
					<li @click="fnSetChartData('PUSH')" id="setPush" style="width:12.5%">
						<a class="inline-block text-center">
							<h5>PUSH 전체</h5>
							<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.pushSuccCnt }}<br></span><span class="text">성공</span></p>
							<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.pushFailCnt }}<br></span><span class="text">실패</span></p>
						</a>
					</li>
					<li @click="fnSetChartData('RCS')" id="setRcs" style="width:12.5%">
						<a class="inline-block text-center">
							<h5>RCS 전체</h5>
							<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.rcsSuccCnt }}<br></span><span class="text">성공</span></p>
							<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.rcsFailCnt }}<br></span><span class="text">실패</span></p>
						</a>
					</li>
					<li @click="fnSetChartData('ALIMTALK')" id="setKakaotalk" style="width:12.5%">
						<a class="inline-block text-center">
							<h5>알림톡 전체</h5>
							<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.alimSuccCnt }}<br></span><span class="text">성공</span></p>
							<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.alimFailCnt }}<br></span><span class="text">실패</span></p>
						</a>
					</li>
					<li @click="fnSetChartData('FRIENDTALK')" id="setFriendtalk" style="width:12.5%">
						<a class="inline-block text-center">
							<h5>친구톡 전체</h5>
							<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.friendSuccCnt }}<br></span><span class="text">성공</span></p>
							<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.friendFailCnt }}<br></span><span class="text">실패</span></p>
						</a>
					</li>
					<li @click="fnSetChartData('SMS')" id="setSms" style="width:12.5%">
						<a class="inline-block text-center">
							<h5>SMS 전체</h5>
							<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.smsSuccCnt }}<br></span><span class="text">성공</span></p>
							<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.smsFailCnt }}<br></span><span class="text">실패</span></p>
						</a>
					</li>
					<li @click="fnSetChartData('LMS')" id="setLms" style="width:12.5%">
						<a class="inline-block text-center">
							<h5>LMS 전체</h5>
							<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.lmsSuccCnt }}<br></span><span class="text">성공</span></p>
							<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.lmsFailCnt }}<br></span><span class="text">실패</span></p>
						</a>
					</li>
					<li @click="fnSetChartData('MMS')" id="setMms" style="width:12.5%">
						<a class="inline-block text-center">
							<h5>MMS 전체</h5>
							<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.mmsSuccCnt }}<br></span><span class="text">성공</span></p>
							<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.mmsFailCnt }}<br></span><span class="text">실패</span></p>
						</a>
					</li>
				</ul>
			</div>
			<div class="Dashboard01 border-line3 pd20" style="margin-top:-1px">
				<h4 class="lc-1 text-left mt20">{{ this.chName }} 성공/실패 현황</h4>
				<bar-chart :chart-data="successFailResultData" :height="100"></bar-chart>
				<h4 class="lc-1 text-left mt20">{{ this.chName }} 실패코드 현황</h4>
				<bar-chart :chart-data="failCodeResultData" :height="100"></bar-chart>
			</div>


		</article>
	</div>
</template>

<script>
import Calendar from "@/components/Calendar.vue";
import BarChart from '@/components/Chart.vue';
import tokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm";
import homeApi from '@/modules/ucHome/service/api';
import * as utils from '@/common/utils';
import { consts } from '@/common/config';

export default {
components: {
		Calendar,
		BarChart,
	},
	props: {
		searchData : {
			type: Object,
			require: false,
			default: function() {
				return {
					'searchStartDate' : this.$gfnCommonUtils.strDateAddMonth(this.$gfnCommonUtils.getCurretDate(), -3),
					'searchEndDate' : this.$gfnCommonUtils.strDateAddMonth(this.$gfnCommonUtils.getCurretDate(), -1),
				}
			}
		}
	},
	data () {
		return {
			searchDateInterval: 3,
			componentsTitle: '발송 통계',
			chName: '전체',
			channelTotalCountInfo: {},
			dateLine: [],
			successCnt: [],
			failCnt: [],
			successFailResultData: {},
			failCodeResultData: {},
			defaultBackgroundColor: [
				'#4A7AF7', '#83BFFA', '#E86560', '#F1AA72', '#F6CF74', '#F361DC', '#D5D5D5', '#F29661', '#CEF279', '#B2CCFF',
				'#B2CCFF', '#DE4F4F', '#FAED7D', '#41FF3A', '#F35AA6', '#949494', '#FFFF6C', '#79ABFF', '#FF3636', '#FF9797'
			]
		}
	},
	mounted() {
		this.fnCalendarInit();
		this.fnSetIntervalSearchDate(this.searchDateInterval);
		this.fnGetChTotCntInfo();
		this.fnSetChartData(this.fnGetChInfo());
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
			jQuery("#endDate").monthpicker({
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
		fnSearch() {
			if(!this.fnChkValidation()) return false;
			this.fnGetChTotCntInfo();
			this.fnSetChartData(this.fnGetChInfo());
		},
		fnGetChTotCntInfo() {
			this.searchData.searchStartDate = jQuery("#startDate").val();
			this.searchData.searchEndDate = jQuery("#endDate").val();

			let params = {
				projectId: utils.getCookie(consts.projectId),
				corpId: tokenSvc.getToken().principal.corpId,
				serviceType: 'UC',
				startMonthStr: this.searchData.searchStartDate,
				endMonthStr: this.searchData.searchEndDate
			};

			homeApi.selectChTotCntInfo(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.channelTotalCountInfo = result.data.chTotCntInfo;
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
		fnSetChartData(channel) {
			this.searchData.searchStartDate = jQuery("#startDate").val();
			this.searchData.searchEndDate = jQuery("#endDate").val();

			let params = {
				projectId: utils.getCookie(consts.projectId),
				corpId: tokenSvc.getToken().principal.corpId,
				serviceType: 'UC',
				startMonthStr: this.searchData.searchStartDate,
				endMonthStr: this.searchData.searchEndDate,
				channel: channel
			};

			homeApi.selectChSuccFailCntList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.fnGetChSuccFailCntList(result.data);
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});

			homeApi.selectChFailCodeList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.fnGetChFailCodeList(result.data);
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});

			jQuery('.mt20 > ul > li').removeClass('active');
			if (channel == 'ALL') {
				jQuery("#setAll").addClass('active');
				this.chName = '전체';
			} else if (channel == 'PUSH') {
				jQuery("#setPush").addClass('active');
				this.chName = 'Push';
			} else if (channel == 'RCS') {
				jQuery("#setRcs").addClass('active');
				this.chName = 'RCS';
			} else if (channel == 'ALIMTALK') {
				jQuery("#setKakaotalk").addClass('active');
				this.chName = '알림톡';
			} else if (channel == 'FRIENDTALK') {
				jQuery("#setFriendtalk").addClass('active');
				this.chName = '친구톡';
			} else if (channel == 'SMS') {
				jQuery("#setSms").addClass('active');
				this.chName = 'SMS';
			} else if (channel == 'LMS') {
				jQuery("#setLms").addClass('active');
				this.chName = 'LMS';
			} else if (channel == 'MMS') {
				jQuery("#setMms").addClass('active');
				this.chName = 'MMS';
			}
		},
		fnGetChInfo() {
			var chInfo = '';

			if (jQuery("#setAll").prop("class") == "active") {
				chInfo = 'ALL';
			} else if (jQuery("#setPush").prop("class") == "active") {
				chInfo = 'PUSH';
			} else if (jQuery("#setRcs").prop("class") == "active") {
				chInfo = 'RCS';
			} else if (jQuery("#setKakaotalk").prop("class") == "active") {
				chInfo = 'ALIMTALK';
			} else if (jQuery("#setFriendtalk").prop("class") == "active") {
				chInfo = 'FRIENDTALK';
			} else if (jQuery("#setSms").prop("class") == "active") {
				chInfo = 'SMS';
			} else if (jQuery("#setLms").prop("class") == "active") {
				chInfo = 'LMS';
			} else if (jQuery("#setMms").prop("class") == "active") {
				chInfo = 'MMS';
			}

			return chInfo;
		},
		fnGetChSuccFailCntList(result) {
			this.dateLine = [];
			this.successCnt = [];
			this.failCnt = [];
			for (var i = 0; i < result.length; i++) {
				this.dateLine.push(result[i].date);
				this.successCnt.push(result[i].succCnt);
				this.failCnt.push(result[i].failCnt);
			}

			this.successFailResultData = {
				labels: this.dateLine,
				datasets: [
					{
						label: '발송성공',
						backgroundColor: '#FD7FA6',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: this.successCnt
					},
					{
						label: '발송실패',
						backgroundColor: '#A9A9A9',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: this.failCnt
					}
				]
			}
		},
		fnGetChFailCodeList(result) {
			this.failCodeResultDataset = [];
			for (var i = 0; i < result.length; i++) {
				this.failCodeDateLine = [];
				this.failCodeCnt = [];
				for (var j = 0; j < result[i].failCodeCnt.length; j++) {
					this.failCodeDateLine.push(result[i].failCodeCnt[j].date);
					this.failCodeCnt.push(result[i].failCodeCnt[j].cnt);
				}

				this.failCodeResultDataset[i] = {
					label: result[i].resultMessage,
					backgroundColor: this.defaultBackgroundColor[i],
					pointBackgroundColor: 'white',
					borderWidth: 1,
					pointBorderColor: '#249EBF',
					data: this.failCodeCnt
				}
			}

			this.failCodeResultData = {
				labels: this.failCodeDateLine,
				datasets: this.failCodeResultDataset
			}
		},
		//검색일자변경
		fnSetIntervalSearchDate(interval){
			this.searchDateInterval = interval;
			this.searchData.searchStartDate = this.$gfnCommonUtils.strDateAddMonth(this.$gfnCommonUtils.getCurretDate(), -this.searchDateInterval);
			this.searchData.searchEndDate = this.$gfnCommonUtils.strDateAddMonth(this.$gfnCommonUtils.getCurretDate(), -1);
		},
		// 유효성 검사
		fnChkValidation() {
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
			return true;
		},
	},
}
</script>
