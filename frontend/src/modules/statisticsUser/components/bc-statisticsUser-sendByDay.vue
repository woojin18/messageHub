<template>
	<div>
		<article>
			<div class="contentHeader mb20">
				<h2>일별발송통계</h2>
			</div>
			<div class="row">
				<div class="col-xs-12 mt10">
					<div class="menuBox">
						<div>
							<h4 class="inline-block" style="width:6%">조회기간</h4>
							<div class="inline-block" style="width:30%">
								<Calendar @update-date="fnUpdateStartDate" calendarId="searchStartDate" classProps="datepicker inputStyle" styleProps="width:40%" :initDate="searchData.searchStartDate"></Calendar>
								<span style="padding:0 11px">~</span>
								<Calendar @update-date="fnUpdateEndDate" calendarId="searchEndDate" classProps="datepicker inputStyle" styleProps="width:40%" :initDate="searchData.searchEndDate"></Calendar>
							</div>
							<ul class="tab_s2 ml20">
								<li :class="this.searchDateInterval==7 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(7);" title="1주일 서비스 검색">1주일</a></li>
								<li :class="this.searchDateInterval==15 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(15);" title="15일 서비스 검색">15일</a></li>
							</ul>
							<a @click="fnSearch" class="btnStyle1 float-right" activity="READ">조회</a>
						</div>
					</div>
				</div>
			</div>
			<div v-if="loaded" class="mt20">
				<ul class="tab_s4_2 of-h" style="width:100%">
					<li @click="fnSetStatisData('ALL')" id="setAll" style="width:12.5%" class="active">
						<a class="inline-block text-center active">
							<h5>전체</h5>
							<p>총 {{allInfo.totCnt}}건</p>
							<p class="inline-block color1 pr10 border-right consolMarginTop">
								<span class="number">{{allInfo.succRatio}}%<br></span>
								<span class="text">성공</span>
							</p>
							<p class="inline-block pl10">
								<span class="number">{{allInfo.failRatio}}%<br></span>
								<span class="text">실패</span>
							</p>
						</a>
					</li>
					<li @click="fnSetStatisData('PUSH')" id="setPush" style="width:12.5%">
						<a class="inline-block text-center active">
							<h5>Push</h5>
							<p>총 {{pushInfo.totCnt}}건</p>
							<p class="inline-block color1 pr10 border-right consolMarginTop">
								<span class="number">{{pushInfo.succRatio}}%<br></span>
								<span class="text">성공</span>
							</p>
							<p class="inline-block pl10">
								<span class="number">{{pushInfo.failRatio}}%<br></span>
								<span class="text">실패</span>
							</p>
						</a>
					</li>
					<li @click="fnSetStatisData('RCS')" id="setRcs" style="width:12.5%">
						<a class="inline-block text-center">
							<h5>RCS</h5>
							<p>총 {{rcsInfo.totCnt}}건</p>
							<p class="inline-block color1 pr10 border-right consolMarginTop">
								<span class="number">{{rcsInfo.succRatio}}%<br></span>
								<span class="text">성공</span>
							</p>
							<p class="inline-block pl10">
								<span class="number">{{rcsInfo.failRatio}}%<br></span>
								<span class="text">실패</span>
							</p>
						</a>
					</li>
					<li @click="fnSetStatisData('ALIMTALK')" id="setKakaotalk" style="width:12.5%">
						<a class="inline-block text-center">
							<h5>알림톡</h5>
							<p>총 {{alimtalkInfo.totCnt}}건</p>
							<p class="inline-block color1 pr10 border-right consolMarginTop">
								<span class="number">{{alimtalkInfo.succRatio}}%<br></span>
								<span class="text">성공</span>
							</p>
							<p class="inline-block pl10">
								<span class="number">{{alimtalkInfo.failRatio}}%<br></span>
								<span class="text">실패</span>
							</p>
						</a>
					</li>
					<li @click="fnSetStatisData('FRIENDTALK')" id="setFriendtalk" style="width:12.5%">
						<a class="inline-block text-center">
							<h5>친구톡</h5>
							<p>총 {{friendtalkInfo.totCnt}}건</p>
							<p class="inline-block color1 pr10 border-right consolMarginTop">
								<span class="number">{{friendtalkInfo.succRatio}}%<br></span>
								<span class="text">성공</span>
							</p>
							<p class="inline-block pl10">
								<span class="number">{{friendtalkInfo.failRatio}}%<br></span>
								<span class="text">실패</span>
							</p>
						</a>
					</li>
					<li @click="fnSetStatisData('SMS')" id="setSms" style="width:12.5%">
						<a class="inline-block text-center">
							<h5>SMS</h5>
							<p>총 {{smsInfo.totCnt}}건</p>
							<p class="inline-block color1 pr10 border-right consolMarginTop">
								<span class="number">{{smsInfo.succRatio}}%<br></span>
								<span class="text">성공</span>
							</p>
							<p class="inline-block pl10">
								<span class="number">{{smsInfo.failRatio}}%<br></span>
								<span class="text">실패</span>
							</p>
						</a>
					</li>
					<li @click="fnSetStatisData('MMS')" id="setMms" style="width:12.5%">
						<a class="inline-block text-center">
							<h5>MMS</h5>
							<p>총 {{mmsInfo.totCnt}}건</p>
							<p class="inline-block color1 pr10 border-right consolMarginTop">
								<span class="number">{{mmsInfo.succRatio}}%<br></span>
								<span class="text">성공</span>
							</p>
							<p class="inline-block pl10">
								<span class="number">{{mmsInfo.failRatio}}%<br></span>
								<span class="text">실패</span>
							</p>
						</a>
					</li>
					<li @click="fnSetStatisData('LMS')" id="setLms" style="width:12.5%">
						<a class="inline-block text-center">
							<h5>LMS</h5>
							<p>총 {{lmsInfo.totCnt}}건</p>
							<p class="inline-block color1 pr10 border-right consolMarginTop">
								<span class="number">{{lmsInfo.succRatio}}%<br></span>
								<span class="text">성공</span>
							</p>
							<p class="inline-block pl10">
								<span class="number">{{lmsInfo.failRatio}}%<br></span>
								<span class="text">실패</span>
							</p>
						</a>
					</li>
				</ul>
			</div>
			<div v-if="loaded" class="Dashboard01 border-line3 pd20" style="margin-top:-1px">
				<div v-show="selectedChannel == 'ALL'">
					<bar-chart :chart-data="allSuccFailRsltData" :height="150"></bar-chart>
				</div>
				<div v-show="selectedChannel == 'PUSH'">
					<bar-chart :chart-data="pushSuccFailRsltData" :height="150"></bar-chart>
				</div>
				<div v-show="selectedChannel == 'RCS'">
					<bar-chart :chart-data="rcsSuccFailRsltData" :height="150"></bar-chart>
				</div>
				<div v-show="selectedChannel == 'MMS'">
					<bar-chart :chart-data="mmsSuccFailRsltData" :height="150"></bar-chart>
				</div>
				<div v-show="selectedChannel == 'LMS'">
					<bar-chart :chart-data="lmsSuccFailRsltData" :height="150"></bar-chart>
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
import Calendar from "@/components/Calendar.vue";
import BarChart from '@/components/Chart.vue';
import statisticsUserApi from '../service/statisticsUserApi'
import tokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm";

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
					'searchStartDate' : this.$gfnCommonUtils.strDateAddDay(this.$gfnCommonUtils.getCurretDate(), -7),
					'searchEndDate' : this.$gfnCommonUtils.getCurretDate(),
				}
			}
		}
	},
	data () {
		return {
			searchDateInterval: 7,
			componentsTitle: '발송 통계',
			statisDataList: [],
			allInfo: {},
			pushInfo: {},
			rcsInfo: {},
			alimtalkInfo: {},
			friendtalkInfo: {},
			smsInfo: {},
			mmsInfo: {},
			lmsInfo: {},
			loaded: false,
			allChanSuccFailRsltData: [], // 전체채널
			// All
			allSuccFailRsltData: [],
			allSuccFailRsltDataLabels: [],
			allSuccRsltDatasets: [],
			allFailRsltDatasets: [],
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
			// LMS
			lmsSuccFailRsltData: [],
			lmsSuccFailRsltDataLabels: [],
			lmsSuccRsltDatasets: [],
			lmsFailRsltDatasets: [],
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
			selectedChannel: 'ALL',
		}
	},
	mounted() {
		//this.selectedChannel = 'PUSH';
		this.fnSetIntervalSearchDate(this.searchDateInterval);
		this.fnStatisList();
		this.fnStatisCntList();
	},
	methods: {
		fnSearch() {
			if(!this.fnChkValidation()) return false;
			this.selectedChannel = 'ALL';
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
			params.dateStatus = 'DAY';

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
			params.dateStatus = 'DAY';
			params.chType = 'ALL';

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
			this.searchData.searchEndDate = this.$gfnCommonUtils.getCurretDate();
			this.searchData.searchStartDate = this.$gfnCommonUtils.strDateAddDay(this.searchData.searchEndDate, -this.searchDateInterval);
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
			if (channel == 'ALL') {
				jQuery("#setAll").addClass('active');
			} else if (channel == 'PUSH') {
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
			} else if (channel == 'LMS') {
				jQuery("#setLms").addClass('active');
			}
		},
		// 상당 타이틀 데이터
		fnAssignTitleData() {
			let dataList		= this.statisDataList;
			let allInfo			= this.allInfo;
			let pushInfo		= this.pushInfo;
			let rcsInfo			= this.rcsInfo;
			let alimtalkInfo	= this.alimtalkInfo;
			let friendtalkInfo	= this.friendtalkInfo;
			let smsInfo			= this.smsInfo;
			let mmsInfo			= this.mmsInfo;
			let lmsInfo			= this.lmsInfo;

			let ch = 'ALL';
			let allTotCnt = 0;
			let allSuccCnt = 0;
			let allFailCnt = 0;

			for(var i in dataList) {
				allTotCnt		+= dataList[i].totCnt * 1;
				allSuccCnt		+= dataList[i].succCnt * 1;
				allFailCnt		+= dataList[i].failCnt * 1;
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
				} else if(dataList[i].ch == 'LMS') {
					lmsInfo.ch			= dataList[i].ch;
					lmsInfo.totCnt		= dataList[i].totCnt;
					lmsInfo.succRatio	= dataList[i].succRatio;
					lmsInfo.failRatio	= dataList[i].failRatio;
				}
			}

			allInfo.ch			= ch;
			allInfo.totCnt		= allTotCnt;
			allInfo.succRatio	= Math.round((allSuccCnt/allTotCnt) * 100);
			allInfo.failRatio	= Math.round((allFailCnt/allTotCnt) * 100);

			this.allInfo		= allInfo;
			this.pushInfo		= pushInfo;
			this.rcsInfo		= rcsInfo;
			this.alimtalkInfo	= alimtalkInfo;
			this.friendtalkInfo	= friendtalkInfo;
			this.smsInfo		= smsInfo;
			this.mmsInfo		= mmsInfo;
			this.lmsInfo		= lmsInfo;
		},
		// 차트생성
		fillData() {
			this.allSuccFailRsltData = {
				labels: this.allSuccFailRsltDataLabels,
				datasets: [
					{
						label: '발송성공',
						backgroundColor: '#f87979',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: this.allSuccRsltDatasets,
					},
					{
						label: '발송실패',
						backgroundColor: '#8C8C8C',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: this.allFailRsltDatasets,
					}
				]
			};

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

			this.lmsSuccFailRsltData = {
				labels: this.lmsSuccFailRsltDataLabels,
				datasets: [
					{
						label: '발송성공',
						backgroundColor: '#f87979',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: this.lmsSuccRsltDatasets,
					},
					{
						label: '발송실패',
						backgroundColor: '#8C8C8C',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: this.lmsFailRsltDatasets,
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
				if(allDataList[i].ch == 'ALL') {
					this.allSuccFailRsltDataLabels.push(allDataList[i].ymd);
				this.allSuccRsltDatasets.push(allDataList[i].succCnt);
				this.allFailRsltDatasets.push(allDataList[i].failCnt);
				} else if(allDataList[i].ch == 'PUSH') {
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
				} else if(allDataList[i].ch == 'LMS') {
					this.lmsSuccFailRsltDataLabels.push(allDataList[i].ymd);
					this.lmsSuccRsltDatasets.push(allDataList[i].succCnt);
					this.lmsFailRsltDatasets.push(allDataList[i].failCnt);
				}
			}
		},
		// 차트 초기화
		fnInitChart() {
			this.allChanSuccFailRsltData = []; // 전체채널
			// All
			this.allSuccFailRsltData = [];
			this.allSuccFailRsltDataLabels = [];
			this.allSuccRsltDatasets = [];
			this.allFailRsltDatasets = [];
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
			// LMS
			this.lmsSuccFailRsltData = [];
			this.lmsSuccFailRsltDataLabels = [];
			this.lmsSuccRsltDatasets = [];
			this.lmsFailRsltDatasets = [];
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
		},
		// 유효성 검사
		fnChkValidation() {
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

			return true;
		},
	},
	
}
</script>
