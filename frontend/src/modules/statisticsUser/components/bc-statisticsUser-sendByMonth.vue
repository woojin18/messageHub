<template>
	<div>
		<article>
			<div class="contentHeader mb20">
				<h2>채널별 월별 발송통계</h2>
			</div>
			<div class="row">
				<div class="col-xs-12 mt10">
					<div class="menuBox">
						<div>
							<h4 class="inline-block" style="width:6%">조회기간</h4>
							<div class="inline-block" style="width:30%">
								<input type="text" id="startDate" class="monthpicker inputStyle maxWidth120 mr5" :value="searchData.searchStartDate">
								~
								<input type="text" id="endDate" class="monthpicker inputStyle maxWidth120 mr5" :value="searchData.searchEndDate">
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
			<div v-if="loaded" class="mt20">
				<ul class="tab_s4_2 of-h" style="width:100%">
					<li @click="fnSetStatisData('PUSH')" id="setPush" style="width:16.6%" class="active">
						<a class="inline-block text-center active">
							<h5>Push</h5>
							<p>총 {{pushInfo.totCnt}}건</p>
							<p class="inline-block color1 pr10 border-right consolMarginTop">
								<span class="number">{{pushInfo.succRatio}}<br></span>
								<span class="text">성공</span>
							</p>
							<p class="inline-block pl10">
								<span class="number">{{pushInfo.failRatio}}<br></span>
								<span class="text">실패</span>
							</p>
						</a>
					</li>
					<li @click="fnSetStatisData('RCS')" id="setRcs" style="width:16.6%">
						<a class="inline-block text-center">
							<h5>RCS</h5>
							<p>총 {{rcsInfo.totCnt}}건</p>
							<p class="inline-block color1 pr10 border-right consolMarginTop">
								<span class="number">{{rcsInfo.succRatio}}<br></span>
								<span class="text">성공</span>
							</p>
							<p class="inline-block pl10">
								<span class="number">{{rcsInfo.failRatio}}<br></span>
								<span class="text">실패</span>
							</p>
						</a>
					</li>
					<li @click="fnSetStatisData('ALIMTALK')" id="setKakaotalk" style="width:16.6%">
						<a class="inline-block text-center">
							<h5>알림톡</h5>
							<p>총 {{alimtalkInfo.totCnt}}건</p>
							<p class="inline-block color1 pr10 border-right consolMarginTop">
								<span class="number">{{alimtalkInfo.succRatio}}<br></span>
								<span class="text">성공</span>
							</p>
							<p class="inline-block pl10">
								<span class="number">{{alimtalkInfo.failRatio}}<br></span>
								<span class="text">실패</span>
							</p>
						</a>
					</li>
					<li @click="fnSetStatisData('FRIENDTALK')" id="setFriendtalk" style="width:16.6%">
						<a class="inline-block text-center">
							<h5>친구톡</h5>
							<p>총 {{friendtalkInfo.totCnt}}건</p>
							<p class="inline-block color1 pr10 border-right consolMarginTop">
								<span class="number">{{friendtalkInfo.succRatio}}<br></span>
								<span class="text">성공</span>
							</p>
							<p class="inline-block pl10">
								<span class="number">{{friendtalkInfo.failRatio}}<br></span>
								<span class="text">실패</span>
							</p>
						</a>
					</li>
					<li @click="fnSetStatisData('SMS')" id="setSms" style="width:16.6%">
						<a class="inline-block text-center">
							<h5>SMS</h5>
							<p>총 {{smsInfo.totCnt}}건</p>
							<p class="inline-block color1 pr10 border-right consolMarginTop">
								<span class="number">{{smsInfo.succRatio}}<br></span>
								<span class="text">성공</span>
							</p>
							<p class="inline-block pl10">
								<span class="number">{{smsInfo.failRatio}}<br></span>
								<span class="text">실패</span>
							</p>
						</a>
					</li>
					<li @click="fnSetStatisData('MMS')" id="setMms" style="width:16.6%">
						<a class="inline-block text-center">
							<h5>MMS</h5>
							<p>총 {{mmsInfo.totCnt}}건</p>
							<p class="inline-block color1 pr10 border-right consolMarginTop">
								<span class="number">{{mmsInfo.succRatio}}%<br></span>
								<span class="text">성공</span>
							</p>
							<p class="inline-block pl10">
								<span class="number">{{mmsInfo.failRatio}}<br></span>
								<span class="text">실패</span>
							</p>
						</a>
					</li>
				</ul>
			</div>
			<div v-if="loaded" class="Dashboard01 border-line3 pd20" style="margin-top:-1px">
				<div v-show="selectedChannel == 'PUSH'">
					<bar-chart :chart-data="pushSuccFailRsltData" :height="150"></bar-chart>
				</div>
				<div v-show="selectedChannel == 'RCS'">
					<bar-chart :chart-data="rcsSuccFailRsltData" :height="150"></bar-chart>
				</div>
				<div v-show="selectedChannel == 'MMS'">
					<bar-chart :chart-data="mmsSuccFailRsltData" :height="150"></bar-chart>
				</div>
				<div v-show="selectedChannel == 'SMS'">
					<bar-chart :chart-data="smsSuccFailRsltData" :height="150"></bar-chart>
				</div>
				<div v-show="selectedChannel == 'ALIMTALK'">
					<bar-chart :chart-data="alimtalkSuccFailRsltData" :height="150"></bar-chart>
				</div>
				<div v-show="selectedChannel == 'FRIENDTALK'">
					<bar-chart :chart-data="friendtalkSuccFailRsltData" :height="150"></bar-chart>
				</div>
			</div>
		</article>
	</div>
</template>

<script>
import BarChart from '@/components/Chart.vue';
import statisticsUserApi from '../service/statisticsUserApi'
import tokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm";

export default {
components: {
		BarChart,
	},
	props: {
		searchData : {
			type: Object,
			require: false,
			default: function() {
				return {
					'searchStartDate' : '',
					'searchEndDate' : '',
				}
			}
		}
	},
	data () {
		return {
			searchDateInterval: 3,
			componentsTitle: '발송 통계',
			statisDataList: [],
			pushInfo: {},
			rcsInfo: {},
			alimtalkInfo: {},
			friendtalkInfo: {},
			smsInfo: {},
			mmsInfo: {},
			loaded: false,
			allChanSuccFailRsltData: [], // 전체채널
			// Push
			pushSuccFailRsltData: [],
			pushSuccFailRsltDataLabels: [],
			pushSuccRsltDatasets: [],
			pushFailRsltDatasets: [],
			// RCS
			rcsSuccFailRsltData: [],
			rcsSuccFailRsltDataLabels: [],
			rcsSuccRsltDatasets: [],
			rcsFailRsltDatasets: [],
			// MMS
			mmsSuccFailRsltData: [],
			mmsSuccFailRsltDataLabels: [],
			mmsSuccRsltDatasets: [],
			mmsFailRsltDatasets: [],
			// SMS
			smsSuccFailRsltData: [],
			smsSuccFailRsltDataLabels: [],
			smsSuccRsltDatasets: [],
			smsFailRsltDatasets: [],
			// ALIMTALK
			alimtalkSuccFailRsltData: [],
			alimtalkSuccFailRsltDataLabels: [],
			alimtalkSuccRsltDatasets: [],
			alimtalkFailRsltDatasets: [],
			// FRIENDTALK
			friendtalkSuccFailRsltData: [],
			friendtalkSuccFailRsltDataLabels: [],
			friendtalkSuccRsltDatasets: [],
			friendtalkFailRsltDatasets: [],
			// 선택한 차트
			selectedChannel: 'PUSH',
		}
	},
	mounted() {
		this.fnCalendarInit();
		this.fnSetIntervalSearchDate(this.searchDateInterval);
		this.fnStatisList();
		this.fnStatisCntList();
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
		fnSearch() {
			this.selectedChannel = 'PUSH';
			this.loaded = false;
			this.fnInitChart();
			this.fnStatisList();
			this.fnStatisCntList();
		},
		// 일별 전송건수 및 비율
		fnStatisList() {
			let vm = this;
			let params = Object.assign({}, this.searchData);
			params.corpId = tokenSvc.getToken().principal.corpId;
			params.dateStatus = 'MONTH';

			statisticsUserApi.selectSendByList(params).then(response =>{
				let result = response.data;
				if (result.success) {
					vm.statisDataList = result.data;
					vm.fnAssignTitleData();
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
		// 일별 전송건수 (차트)
		fnStatisCntList() {
			let vm = this;
			let params = Object.assign({}, this.searchData);
			params.corpId = tokenSvc.getToken().principal.corpId;
			params.dateStatus = 'MONTH';

			statisticsUserApi.selectSendByCntList(params).then(response =>{
				let result = response.data;
				if (result.success) {
					vm.allChanSuccFailRsltData = result.data;
					// Chart Data set
					vm.fnAssignChartData();
					// Chart make
					vm.fillData();
					// Chaart Loding Complete
					vm.loaded = true;
				} else { 
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
		//검색일자변경
		fnSetIntervalSearchDate(interval){
			this.searchDateInterval = interval;
			this.searchData.searchEndDate = this.$gfnCommonUtils.getCurretDate('yyyy-mm');
			this.searchData.searchStartDate = this.$gfnCommonUtils.strDateAddMonth(this.$gfnCommonUtils.getCurretDate(), -this.searchDateInterval +1);
		},
		fnUpdateStartDate(sltDate) {
			this.searchData.searchStartDate = sltDate;
		},
		fnUpdateEndDate(sltDate) {
			this.searchData.searchEndDate = sltDate;
		},
		// 채널 선택
		fnSetStatisData(channel) {
			this.selectedChannel = channel;

			jQuery('.mt20 > ul > li').removeClass('active');
			if (channel == 'PUSH') {
				jQuery("#setPush").addClass('active');
			} else if (channel == 'RCS') {
				jQuery("#setRcs").addClass('active');
			} else if (channel == 'ALIMTALK') {
				jQuery("#setKakaotalk").addClass('active');
			} else if (channel == 'FRIENDTALK') {
				jQuery("#setFriendtalk").addClass('active');
			} else if (channel == 'SMS') {
				jQuery("#setSms").addClass('active');
			} else if (channel == 'MMS') {
				jQuery("#setMms").addClass('active');
			}
		},
		// 상당 타이틀 데이터
		fnAssignTitleData() {
			let dataList		= this.statisDataList;
			let pushInfo		= this.pushInfo;
			let rcsInfo			= this.rcsInfo;
			let alimtalkInfo	= this.alimtalkInfo;
			let friendtalkInfo	= this.friendtalkInfo;
			let smsInfo			= this.smsInfo;
			let mmsInfo			= this.mmsInfo;

			for(var i in dataList) {
				if(dataList[i].ch == 'PUSH') {
					pushInfo.ch			= dataList[i].ch;
					pushInfo.totCnt		= dataList[i].totCnt;
					pushInfo.succRatio	= dataList[i].succRatio;
					pushInfo.failRatio	= dataList[i].failRatio;
				} else if(dataList[i].ch == 'RCS') {
					rcsInfo.ch			= dataList[i].ch;
					rcsInfo.totCnt		= dataList[i].totCnt;
					rcsInfo.succRatio	= dataList[i].succRatio;
					rcsInfo.failRatio	= dataList[i].failRatio;
				} else if(dataList[i].ch == 'ALIMTALK') {
					alimtalkInfo.ch			= dataList[i].ch;
					alimtalkInfo.totCnt		= dataList[i].totCnt;
					alimtalkInfo.succRatio	= dataList[i].succRatio;
					alimtalkInfo.failRatio	= dataList[i].failRatio;
				} else if(dataList[i].ch == 'FRIENDTALK') {
					friendtalkInfo.ch			= dataList[i].ch;
					friendtalkInfo.totCnt		= dataList[i].totCnt;
					friendtalkInfo.succRatio	= dataList[i].succRatio;
					friendtalkInfo.failRatio	= dataList[i].failRatio;
				} else if(dataList[i].ch == 'SMS') {
					smsInfo.ch			= dataList[i].ch;
					smsInfo.totCnt		= dataList[i].totCnt;
					smsInfo.succRatio	= dataList[i].succRatio;
					smsInfo.failRatio	= dataList[i].failRatio;
				} else if(dataList[i].ch == 'MMS') {
					mmsInfo.ch			= dataList[i].ch;
					mmsInfo.totCnt		= dataList[i].totCnt;
					mmsInfo.succRatio	= dataList[i].succRatio;
					mmsInfo.failRatio	= dataList[i].failRatio;
				}
			}
			this.pushInfo		= pushInfo;
			this.rcsInfo		= rcsInfo;
			this.alimtalkInfo	= alimtalkInfo;
			this.friendtalkInfo	= friendtalkInfo;
			this.smsInfo		= smsInfo;
			this.mmsInfo		= mmsInfo;
		},
		// 차트생성
		fillData() {
			this.pushSuccFailRsltData = {
				labels: this.pushSuccFailRsltDataLabels,
				datasets: [
					{
						label: '발송성공',
						backgroundColor: '#f87979',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: this.pushSuccRsltDatasets,
					},
					{
						label: '발송실패',
						backgroundColor: '#8C8C8C',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: this.pushFailRsltDatasets,
					}
				]
			};

			this.rcsSuccFailRsltData = {
				labels: this.rcsSuccFailRsltDataLabels,
				datasets: [
					{
						label: '발송성공',
						backgroundColor: '#f87979',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: this.rcsSuccRsltDatasets,
					},
					{
						label: '발송실패',
						backgroundColor: '#8C8C8C',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: this.rcsFailRsltDatasets,
					}
				]
			};

			this.mmsSuccFailRsltData = {
				labels: this.mmsSuccFailRsltDataLabels,
				datasets: [
					{
						label: '발송성공',
						backgroundColor: '#f87979',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: this.mmsSuccRsltDatasets,
					},
					{
						label: '발송실패',
						backgroundColor: '#8C8C8C',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: this.mmsFailRsltDatasets,
					}
				]
			};

			this.smsSuccFailRsltData = {
				labels: this.smsSuccFailRsltDataLabels,
				datasets: [
					{
						label: '발송성공',
						backgroundColor: '#f87979',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: this.smsSuccRsltDatasets,
					},
					{
						label: '발송실패',
						backgroundColor: '#8C8C8C',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: this.smsFailRsltDatasets,
					}
				]
			};

			this.alimtalkSuccFailRsltData = {
				labels: this.alimtalkSuccFailRsltDataLabels,
				datasets: [
					{
						label: '발송성공',
						backgroundColor: '#f87979',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: this.alimtalkSuccRsltDatasets,
					},
					{
						label: '발송실패',
						backgroundColor: '#8C8C8C',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: this.alimtalkFailRsltDatasets,
					}
				]
			};

			this.friendtalkSuccFailRsltData = {
				labels: this.friendtalkSuccFailRsltDataLabels,
				datasets: [
					{
						label: '발송성공',
						backgroundColor: '#f87979',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: this.friendtalkSuccRsltDatasets,
					},
					{
						label: '발송실패',
						backgroundColor: '#8C8C8C',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: this.friendtalkFailRsltDatasets,
					}
				]
			};
		},
		// 차트 데이터
		fnAssignChartData() {
			let allDataList = this.allChanSuccFailRsltData;
			for(var i in allDataList) {
				if(allDataList[i].ch == 'PUSH') {
					this.pushSuccFailRsltDataLabels.push(allDataList[i].ymd);
					this.pushSuccRsltDatasets.push(allDataList[i].succCnt);
					this.pushFailRsltDatasets.push(allDataList[i].failCnt);
				} else if(allDataList[i].ch == 'RCS') {
					this.rcsSuccFailRsltDataLabels.push(allDataList[i].ymd);
					this.rcsSuccRsltDatasets.push(allDataList[i].succCnt);
					this.rcsFailRsltDatasets.push(allDataList[i].failCnt);
				} else if(allDataList[i].ch == 'ALIMTALK') {
					this.alimtalkSuccFailRsltDataLabels.push(allDataList[i].ymd);
					this.alimtalkSuccRsltDatasets.push(allDataList[i].succCnt);
					this.alimtalkFailRsltDatasets.push(allDataList[i].failCnt);
				} else if(allDataList[i].ch == 'FRIENDTALK') {
					this.friendtalkSuccFailRsltDataLabels.push(allDataList[i].ymd);
					this.friendtalkSuccRsltDatasets.push(allDataList[i].succCnt);
					this.friendtalkFailRsltDatasets.push(allDataList[i].failCnt);
				} else if(allDataList[i].ch == 'SMS') {
					this.smsSuccFailRsltDataLabels.push(allDataList[i].ymd);
					this.smsSuccRsltDatasets.push(allDataList[i].succCnt);
					this.smsFailRsltDatasets.push(allDataList[i].failCnt);
				} else if(allDataList[i].ch == 'MMS') {
					this.mmsSuccFailRsltDataLabels.push(allDataList[i].ymd);
					this.mmsSuccRsltDatasets.push(allDataList[i].succCnt);
					this.mmsFailRsltDatasets.push(allDataList[i].failCnt);
				}
			}
		},
		// 차트 초기화
		fnInitChart() {
			this.allChanSuccFailRsltData = []; // 전체채널
			// Push
			this.pushSuccFailRsltData = [];
			this.pushSuccFailRsltDataLabels = [];
			this.pushSuccRsltDatasets = [];
			this.pushFailRsltDatasets = [];
			// RCS
			this.rcsSuccFailRsltData = [];
			this.rcsSuccFailRsltDataLabels = [];
			this.rcsSuccRsltDatasets = [];
			this.rcsFailRsltDatasets = [];
			// MMS
			this.mmsSuccFailRsltData = [];
			this.mmsSuccFailRsltDataLabels = [];
			this.mmsSuccRsltDatasets = [];
			this.mmsFailRsltDatasets = [];
			// SMS
			this.smsSuccFailRsltData = [];
			this.smsSuccFailRsltDataLabels = [];
			this.smsSuccRsltDatasets = [];
			this.smsFailRsltDatasets = [];
			// ALIMTALK
			this.alimtalkSuccFailRsltData = [];
			this.alimtalkSuccFailRsltDataLabels = [];
			this.alimtalkSuccRsltDatasets = [];
			this.alimtalkFailRsltDatasets = [];
			// FRIENDTALK
			this.friendtalkSuccFailRsltData = [];
			this.friendtalkSuccFailRsltDataLabels = [];
			this.friendtalkSuccRsltDatasets = [];
			this.friendtalkFailRsltDatasets = [];
		}
	},
}
</script>
