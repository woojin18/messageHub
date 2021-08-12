<template>
	<div>
		<article>
			<div class="contentHeader">
				<h2>{{ projectInfoData.projectName }} 대시보드</h2>
				<!-- <a @click="fnPageReload()" class="btnStyle2 backPink absolute top0 right0" title="비트큐브 대시보드 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
			</div>
			<!-- 본문 -->
			<div class="row">
				<div class="col-xs-6">
					<div class="fl" style="width:100%">
						<h4 class="lc-1 mb20">프로젝트 정보</h4>
						<div style="height:160px; overflow-y:none">
							<table class="table_skin1">
								<colgroup>
									<col style="width:20%">
									<col style="width:80%">
								</colgroup>
								<thead>
								</thead>
								<tbody>
									<tr>
										<td class="text-left">생성일</td>
										<td class="text-left end">{{ projectInfoData.regDt }}</td>
									</tr>
									<tr>
										<td class="text-left">생성자</td>
										<td class="text-left end">{{ projectInfoData.userInfo }}</td>
									</tr>
									<tr>
										<td class="text-left">이용 서비스</td>
										<td class="text-left end">{{ projectInfoData.useService }}</td>
									</tr>
									<tr>
										<td class="text-left">프로젝트 ID</td>
										<td class="text-left end">{{ projectInfoData.projectId }}</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="col-xs-6">
					<div class="fl" style="width:100%">
						<h4 class="lc-1 mb20">공지사항</h4>
						<div style="height:160px; overflow-y:none">
							<table class="table_skin1" style="width:100%">
								<colgroup>
									<col style="width:80%">
									<col style="width:20%">
								</colgroup>
								<thead>
									<tr>
									<th class="text-center">제목</th>
									<th class="text-center end">날짜</th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="(ntc, idx) in notices" :key="ntc.noticeId">
										<td class="text-left">
											<span v-if="ntc.noticeType == 'INFO'" class="Information">{{ntc.noticeTypeCdName}}</span>
											<span v-else-if="ntc.noticeType == 'INSPEC'" class="Inspect">{{ntc.noticeTypeCdName}}</span>
											<span v-else-if="ntc.noticeType == 'FAULT'" class="Fault">{{ntc.noticeTypeCdName}}</span>
											<a @click.prevent="fnOpenNoticePopupModal(ntc.noticeId)" title="해당 게시글이 열립니다"><u>{{ntc.title}}</u></a>
										</td>
										<td class="text-center end">{{ntc.regDtYmd}}</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>		
				</div>
			</div>

			<div class="row">
				<div class="col-xs-12">
					<div class="">
						<h4 class="lc-1 text-left">채널별 성공/실패율</h4>
						<div class="mt20">
							<Calendar @update-date="fnUpdateStartDate" calendarId="searchStartDate" classProps="datepicker inputStyle" styleProps="width:15%" :initDate="searchData.searchStartDate" disabled></Calendar>
							<span style="padding:0 11px">~</span>
							<Calendar @update-date="fnUpdateEndDate" calendarId="searchEndDate" classProps="datepicker inputStyle" styleProps="width:15%" :initDate="searchData.searchEndDate" disabled></Calendar>
							<ul class="tab_s2 ml20">
								<li :class="this.searchDateInterval==7 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(7);" title="1주일 서비스 검색">1주일</a></li>
								<li :class="this.searchDateInterval==15 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(15);" title="15일 서비스 검색">15일</a></li>
								<li :class="this.searchDateInterval==30 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(30);" title="1개월 서비스 검색">1개월</a></li>
							</ul>
						</div>
						<div class="mt10">
							<ul class="tab_s4_2 of-h" style="width:100%">
								<li @click="fnSetChartData('PUSH')" id="setPush" style="width:16.7%" class="active">
									<a class="inline-block text-center active">
										<h5>PUSH 전체</h5>
										<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.pushSuccCnt }}<br></span><span class="text">성공</span></p>
										<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.pushFailCnt }}<br></span><span class="text">실패</span></p>
									</a>
								</li>
								<li @click="fnSetChartData('RCS')" id="setRcs" style="width:16.7%">
									<a class="inline-block text-center">
										<h5>RCS 전체</h5>
										<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.rcsSuccCnt }}<br></span><span class="text">성공</span></p>
										<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.rcsFailCnt }}<br></span><span class="text">실패</span></p>
									</a>
								</li>
								<li @click="fnSetChartData('ALIMTALK')" id="setKakaotalk" style="width:16.6%">
									<a class="inline-block text-center">
										<h5>알림톡 전체</h5>
										<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.alimSuccCnt }}<br></span><span class="text">성공</span></p>
										<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.alimFailCnt }}<br></span><span class="text">실패</span></p>
									</a>
								</li>
								<li @click="fnSetChartData('FRIENDTALK')" id="setFriendtalk" style="width:16.7%">
									<a class="inline-block text-center">
										<h5>친구톡 전체</h5>
										<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.friendSuccCnt }}<br></span><span class="text">성공</span></p>
										<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.friendFailCnt }}<br></span><span class="text">실패</span></p>
									</a>
								</li>
								<li @click="fnSetChartData('SMS')" id="setSms" style="width:16.7%">
									<a class="inline-block text-center">
										<h5>SMS 전체</h5>
										<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.smsSuccCnt }}<br></span><span class="text">성공</span></p>
										<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.smsFailCnt }}<br></span><span class="text">실패</span></p>
									</a>
								</li>
								<li @click="fnSetChartData('MMS')" id="setMms" style="width:16.6%">
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
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-xs-12">
					<div class="">
						<h4 class="lc-1">당일 실시간 이용현황</h4>
						<div class="mt20"></div>
						<div class="Dashboard01 border-line2 pd20">
							<bar-chart :chart-data="rtUsedResultData" :height="100"></bar-chart>
						</div>
					</div>
				</div>
			</div>
			<!-- //본문 -->

			<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
		</article>
		<NoticeLayer ref="noticeLayer"></NoticeLayer>
	</div>
</template>

<script>
// @ is an alias to /src
import HomeMain from '../components/bc-homeMain.vue';
import tokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm";
import homeApi from '@/modules/ucHome/service/api';
import NoticeLayer from "@/modules/customer/components/bp-noticeLayer.vue";
import Calendar from "@/components/Calendar.vue";
import BarChart from '@/components/Chart.vue';
import * as utils from '@/common/utils';
import { consts } from '@/common/config';

export default {
	components: {
		HomeMain,
		NoticeLayer,
		Calendar,
		BarChart
	},
	props: {
		searchData : {
			type: Object,
			require: false,
			default: function() {
				return {
					'searchStartDate' : this.$gfnCommonUtils.getCurretDate(),
					'searchEndDate' : this.$gfnCommonUtils.getCurretDate()
				}
			}
		}
	},
	componentsTitle: {
		type: String,
		require: false,
		default: function() {
			return 'User Console DashBoard';
		}
	},
	data () {
		return {
			projectInfoData: {},
			notices: [],
			chName: 'Push',
			channelTotalCountInfo: {},
			searchDateInterval: 7,
			dateLine: [],
			successCnt: [],
			failCnt: [],
			failCodeResultDataset: [],
			failCodeCnt: [],
			defaultBackgroundColor: [
				'#4A7AF7', '#83BFFA', '#E86560', '#F1AA72', '#F6CF74', '#F361DC', '#D5D5D5', '#F29661', '#CEF279', '#B2CCFF',
				'#B2CCFF', '#DE4F4F', '#FAED7D', '#41FF3A', '#F35AA6', '#949494', '#FFFF6C', '#79ABFF', '#FF3636', '#FF9797'
			],
			backgroundColor: [],
			timeLine: [],
			rtUsedPushList: [],
			rtUsedRcsList: [],
			rtUsedFriendtalkList: [],
			rtUsedAlimtalkList: [],
			rtUsedSmsList: [],
			rtUsedMmsList: [],
			successFailResultData: {},
			failCodeResultData: {},
			rtUsedResultData: {}
		}
	},
	created: function() {
		console.log('created HomeMain');
	},
	mounted() {
		this.fnInit();
		this.fnSetIntervalSearchDate(this.searchDateInterval);
		this.fnGetProjectInfo();
		this.fnGetNoticeList();
		this.fnGetChTotCntInfo();
		this.fnGetRtUsedTimeLineList();
	},
	methods: {
		fnInit() {
			var svcTypeCd = tokenSvc.getToken().principal.svcTypeCd;
			if (svcTypeCd == 'AC') {
				console.log("AC start");
				let params = {
					userId: tokenSvc.getToken().principal.userId
				};

				homeApi.selectProjectList(params).then(response =>{
					var result = response.data;
					if (result.success) {
						if (result.data.length == 0) {
							alert("참여되어 있는 프로젝트가 없습니다.");
							this.$router.replace("/ac/home");
						}
					} else {
						confirm.fnAlert(this.componentsTitle, result.message);
					}
				});
			}
		},
		fnGetProjectInfo() {
			let params = {
				projectId: utils.getCookie(consts.projectId),
				corpId: tokenSvc.getToken().principal.corpId
			};

			homeApi.selectProjectInfo(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.projectInfoData = result.data.projectInfo;
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
		fnGetNoticeList() {
			let params = {
			};

			homeApi.selectNoticeList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.notices = result.data;
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
		fnOpenNoticePopupModal(noticeId){
			this.$refs.noticeLayer.fnSetNoticeInfo(noticeId);
			jQuery("#noticeDetailLayer").modal("show");
		},
		fnGetChTotCntInfo() {
			let params = {
				projectId: utils.getCookie(consts.projectId),
				corpId: tokenSvc.getToken().principal.corpId,
				serviceType: 'UC',
				startDateStr: this.searchData.searchStartDate,
				endDateStr: this.searchData.searchEndDate
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
			let params = {
				projectId: utils.getCookie(consts.projectId),
				corpId: tokenSvc.getToken().principal.corpId,
				serviceType: 'UC',
				startDateStr: this.searchData.searchStartDate,
				endDateStr: this.searchData.searchEndDate,
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

			jQuery('.mt10 > ul > li').removeClass('active');
			if (channel == 'PUSH') {
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
			} else if (channel == 'MMS') {
				jQuery("#setMms").addClass('active');
				this.chName = 'MMS';
			}
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
					label: result[i].resultCode,
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
		// 당일 이용현황 시간대 조회
		fnGetRtUsedTimeLineList() {
			let params = {
				projectId: utils.getCookie(consts.projectId),
				corpId: tokenSvc.getToken().principal.corpId
			};

			homeApi.selectRtUsedTimeLineList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					for (var i = 0; i < result.data.length; i++) {
						this.timeLine.push(result.data[i].date);
					}
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});

			this.fnGetRtUsedDataList('PUSH');
			this.fnGetRtUsedDataList('RCS');
			this.fnGetRtUsedDataList('FRIENDTALK');
			this.fnGetRtUsedDataList('ALIMTALK');
			this.fnGetRtUsedDataList('SMS');
			this.fnGetRtUsedDataList('MMS');
		},
		// 당일 이용현황 채널별 데이터 조회
		fnGetRtUsedDataList(channel) {
			let params = {
				projectId: utils.getCookie(consts.projectId),
				corpId: tokenSvc.getToken().principal.corpId,
				channel: channel
			};

			homeApi.selectRtUsedDataList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					for (var i = 0; i < result.data.length; i++) {
						if (channel == 'PUSH') {
							this.rtUsedPushList.push(result.data[i].totCnt);
						} else if (channel == 'RCS') {
							this.rtUsedRcsList.push(result.data[i].totCnt);
						} else if (channel == 'FRIENDTALK') {
							this.rtUsedFriendtalkList.push(result.data[i].totCnt);
						} else if (channel == 'ALIMTALK') {
							this.rtUsedAlimtalkList.push(result.data[i].totCnt);
						} else if (channel == 'SMS') {
							this.rtUsedSmsList.push(result.data[i].totCnt);
						} else if (channel == 'MMS') {
							this.rtUsedMmsList.push(result.data[i].totCnt);
						}
					}

					this.rtUsedResultData = {
						labels: this.timeLine,
						datasets: [
							{
								label: 'Push',
								backgroundColor: '#f87979',
								pointBackgroundColor: 'white',
								borderWidth: 1,
								pointBorderColor: '#249EBF',
								data: this.rtUsedPushList
							},
							{
								label: 'RCS',
								backgroundColor: '#FFE08C',
								pointBackgroundColor: 'white',
								borderWidth: 1,
								pointBorderColor: '#249EBF',
								data: this.rtUsedRcsList
							},
							{
								label: '친구톡',
								backgroundColor: '#FFBB00',
								pointBackgroundColor: 'white',
								borderWidth: 1,
								pointBorderColor: '#249EBF',
								data: this.rtUsedFriendtalkList
							},
							{
								label: '알림톡',
								backgroundColor: '#5CD1E5',
								pointBackgroundColor: 'white',
								borderWidth: 1,
								pointBorderColor: '#249EBF',
								data: this.rtUsedAlimtalkList
							},
							{
								label: 'SMS',
								backgroundColor: '#8041D9',
								pointBackgroundColor: 'white',
								borderWidth: 1,
								pointBorderColor: '#249EBF',
								data: this.rtUsedSmsList
							},
							{
								label: 'MMS',
								backgroundColor: '#1DDB16',
								pointBackgroundColor: 'white',
								borderWidth: 1,
								pointBorderColor: '#249EBF',
								data: this.rtUsedMmsList
							}
						]
					}
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
			this.fnGetChTotCntInfo();
			this.fnSetChartData(this.fnGetChInfo());
		},
		fnUpdateStartDate(sltDate) {
			if (sltDate && this.searchData.searchEndDate) {
				if (sltDate.replace(/[^0-9]/g, '') > this.searchData.searchEndDate.replace(/[^0-9]/g, '')) {
					confirm.fnAlert(this.componentsTitle, '시작일은 종료일보다 클 수 없습니다.');
					jQuery("#searchStartDate").val(this.searchData.searchStartDate);
					return false;
				} else {
					this.searchData.searchStartDate = sltDate;
				}
			}
		},
		fnUpdateEndDate(sltDate) {
			if (this.searchData.searchStartDate && sltDate) {
				if (this.searchData.searchStartDate.replace(/[^0-9]/g, '') > sltDate.replace(/[^0-9]/g, '')) {
					confirm.fnAlert(this.componentsTitle, '시작일은 종료일보다 클 수 없습니다.');
					jQuery("#searchEndDate").val(this.searchData.searchEndDate);
					return false;
				} else {
					this.searchData.searchEndDate = sltDate;
				}
			}
		},
		fnPageReload() {
			this.$router.replace('/');
			this.$nextTick(() => this.$router.replace("/uc/home"));
		},
		fnGetChInfo() {
			var chInfo = '';

			if (jQuery("#setPush").prop("class") == "active") {
				chInfo = 'PUSH';
			} else if (jQuery("#setRcs").prop("class") == "active") {
				chInfo = 'RCS';
			} else if (jQuery("#setKakaotalk").prop("class") == "active") {
				chInfo = 'ALIMTALK';
			} else if (jQuery("#setFriendtalk").prop("class") == "active") {
				chInfo = 'FRIENDTALK';
			} else if (jQuery("#setSms").prop("class") == "active") {
				chInfo = 'SMS';
			} else if (jQuery("#setMms").prop("class") == "active") {
				chInfo = 'MMS';
			}

			return chInfo;
		}
	}
};
</script>