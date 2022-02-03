<template>
	<div>
		<article>
			<div class="contentHeader">
				<h2>대시보드</h2>
				<a @click="fnPageReload()" class="btnStyle2 backPink absolute top0 right0" title="새로고침">새로고침 <i class="fal fa-redo"></i></a>
			</div>
			<!-- 본문 -->
			<div v-if="corpInfo.feeType == 'POST'" class="clear">
				<div v-if="corpInfo.billStatus != null" class="float-left mr30"><a @click="fnBill1" class="btnStyle1 backLightGray" style="min-width:auto; width:160px">{{corpInfo.billStatusNm}}</a></div>
				<div v-if="corpInfo.billStatus == null || corpInfo.billStatus == 'RET'" class="float-left mr30"><a @click="fnBill2" class="btnStyle1 backPink" style="min-width:auto; width:160px">후불 사용 요청</a></div>	
				<span v-if="corpInfo.billStatus == 'APP'" class="float-left font-size14 color4 mr30 line-height30">고객 월 한도금액 : {{corpInfo.monSenderLimitAmount | comma}}원</span>
				<div v-if="corpInfo.billStatus == 'APP' && corpInfo.senderUpStatus != null" class="float-left mr30"><a @click="fnSenderUp1" class="btnStyle1 backLightGray" style="min-width:auto; width:160px">{{corpInfo.senderUpStatusNm}}</a></div>
				<div v-if="corpInfo.billStatus == 'APP' && (corpInfo.senderUpStatus == null || corpInfo.senderUpStatus == 'RET')" class="float-left mr30"><a @click="fnSenderUp2" class="btnStyle1 backPink" style="min-width:auto; width:160px">한도금액 상향 요청</a></div>	
			</div>
			<div class="row">
				<div class="col-xs-6">
					<div class="fl" style="width:100%">
						<h4 class="lc-1 mb20">프로젝트 목록</h4>
						<div style="height:160px; overflow-y:none">
							<table class="table_skin1" style="width:100%">
								<colgroup>
									<col style="width:29%">
									<col style="width:17%">
									<col style="width:39%">
									<col style="width:15%">
								</colgroup>
								<thead>
									<tr>
									<th class="text-center">프로젝트 명</th>
									<th class="text-center">프로젝트 타입</th>
									<th class="text-center">이용서비스</th>
									<th class="text-center end">생성일</th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="(pjt, idx) in projects" :key="pjt.projectId">
										<td class="text-left">
											<router-link :to="{name: 'projectMain', params: { projectId: pjt.projectId, projectName: pjt.projectName, selMainTab : 1, selMidTab : 1, selSubTab : 1 }}"><u>{{pjt.projectName}}</u></router-link>
										</td>
										<td class="text-center">{{pjt.payTypeCdName}}</td>
										<td class="text-left">{{pjt.useService}}</td>
										<td class="text-center end">{{pjt.regDtYmd}}</td>
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
											<a @click.prevent="fnOpenNoticePopupModal(ntc.noticeId)" title="해당 게시글이 열립니다"><u>{{ntc.title | unescapeXss}}</u></a>
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
					<h4 class="inline-block topH4">당일 실시간 발송 현황 
						<span class="font-size14 color4 ml30">{{chMinCount}}초 후 
						<!--<a href="#" class="color-blue" @click.prevent="fnGetChMinList" title="새로고침">[갱신]</a>-->
						</span>
					</h4>
					<p class="color3 float-right">단위 : 건수</p>					
					<table class="table_skin1" style="width:100%">
						<colgroup>
							<col style="">
						</colgroup>
						<thead>
							<tr class="bb">
								<th class="text-center cellActive">발송 상태</th>
								<th class="text-center cellActive">전체</th>
								<th class="text-center">SMS</th>
								<th class="text-center">LMS</th>
								<th class="text-center">MMS</th>
								<th class="text-center">RCS</th>
								<th class="text-center">알림톡</th>
								<th class="text-center">친구톡</th>
								<th class="text-center end">PUSH</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-center cellActive">발송 요청</td>
								<td class="text-center cellActive">{{channelMinInfo.totalTotCnt | comma}}</td>
								<td class="text-center">{{channelMinInfo.smsTotCnt | comma}}</td>
								<td class="text-center">{{channelMinInfo.lmsTotCnt | comma}}</td>
								<td class="text-center">{{channelMinInfo.mmsTotCnt | comma}}</td>
								<td class="text-center">{{channelMinInfo.rcsTotCnt | comma}}</td>
								<td class="text-center">{{channelMinInfo.alimTotCnt | comma}}</td>
								<td class="text-center">{{channelMinInfo.friendTotCnt | comma}}</td>
								<td class="text-center end">{{channelMinInfo.pushTotCnt | comma}}</td>
							</tr>
							<tr>
								<td class="text-center cellActive">발송 중</td>
								<td class="text-center cellActive">{{channelMinInfo.totalSendingCnt | comma}}</td>
								<td class="text-center">{{channelMinInfo.smsSendingCnt | comma}}</td>
								<td class="text-center">{{channelMinInfo.lmsSendingCnt | comma}}</td>
								<td class="text-center">{{channelMinInfo.mmsSendingCnt | comma}}</td>
								<td class="text-center">{{channelMinInfo.rcsSendingCnt | comma}}</td>
								<td class="text-center">{{channelMinInfo.alimSendingCnt | comma}}</td>
								<td class="text-center">{{channelMinInfo.friendSendingCnt | comma}}</td>
								<td class="text-center end">{{channelMinInfo.pushSendingCnt | comma}}</td>
							</tr>
							<tr>
								<td class="text-center cellActive">발송 결과대기</td>
								<td class="text-center cellActive">{{channelMinInfo.totalWaitRptCnt | comma}}</td>
								<td class="text-center">{{channelMinInfo.smsWaitRptCnt | comma}}</td>
								<td class="text-center">{{channelMinInfo.lmsWaitRptCnt | comma}}</td>
								<td class="text-center">{{channelMinInfo.mmsWaitRptCnt | comma}}</td>
								<td class="text-center">{{channelMinInfo.rcsWaitRptCnt | comma}}</td>
								<td class="text-center">{{channelMinInfo.alimWaitRptCnt | comma}}</td>
								<td class="text-center">{{channelMinInfo.friendWaitRptCnt | comma}}</td>
								<td class="text-center end">{{channelMinInfo.pushWaitRptCnt | comma}}</td>
							</tr>
							<tr>
								<td class="text-center cellActive color-blue">발송 성공</td>
								<td class="text-center cellActive color-blue">{{channelMinInfo.totalSuccCnt | comma}}</td>
								<td class="text-center color-blue">{{channelMinInfo.smsSuccCnt | comma}}</td>
								<td class="text-center color-blue">{{channelMinInfo.lmsSuccCnt | comma}}</td>
								<td class="text-center color-blue">{{channelMinInfo.mmsSuccCnt | comma}}</td>
								<td class="text-center color-blue">{{channelMinInfo.rcsSuccCnt | comma}}</td>
								<td class="text-center color-blue">{{channelMinInfo.alimSuccCnt | comma}}</td>
								<td class="text-center color-blue">{{channelMinInfo.friendSuccCnt | comma}}</td>
								<td class="text-center end color-blue">{{channelMinInfo.pushSuccCnt | comma}}</td>
							</tr>
							<tr>
								<td class="text-center cellActive color-red">발송 실패</td>
								<td class="text-center cellActive color-red">{{channelMinInfo.totalFailCnt | comma}}</td>
								<td class="text-center color-red">{{channelMinInfo.smsFailCnt | comma}}</td>
								<td class="text-center color-red">{{channelMinInfo.lmsFailCnt | comma}}</td>
								<td class="text-center color-red">{{channelMinInfo.mmsFailCnt | comma}}</td>
								<td class="text-center color-red">{{channelMinInfo.rcsFailCnt | comma}}</td>
								<td class="text-center color-red">{{channelMinInfo.alimFailCnt | comma}}</td>
								<td class="text-center color-red">{{channelMinInfo.friendFailCnt | comma}}</td>
								<td class="text-center end color-red">{{channelMinInfo.pushFailCnt | comma}}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<div class="row">
				<div class="col-xs-12">
					<div class="">
						<h4 class="lc-1 text-left">채널별 성공/실패율</h4>
						<div class="mt20">
							<Calendar @update-date="fnUpdateStartDate" calendarId="searchStartDate" classProps="inputStyle" styleProps="width:130px;" :initDate="searchData.searchStartDate" disabled></Calendar>
							<span style="padding:0 11px">~</span>
							<Calendar @update-date="fnUpdateEndDate" calendarId="searchEndDate" classProps="inputStyle" styleProps="width:130px" :initDate="searchData.searchEndDate" disabled></Calendar>
							<ul class="tab_s2 ml20">
								<li :class="this.searchDateInterval==7 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(7);" title="1주일 서비스 검색">1주일</a></li>
								<li :class="this.searchDateInterval==15 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(15);" title="15일 서비스 검색">15일</a></li>
								<li :class="this.searchDateInterval==30 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(30);" title="1개월 서비스 검색">1개월</a></li>
							</ul>
						</div>
						<div class="mt10">
							<ul class="tab_s4_2 of-h" style="width:100%">
								<li @click="fnSetChartData('ALL')" id="setAll" style="width:12.5%" class="active">
									<a class="inline-block text-center active">
										<h5>총계</h5>
										<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.totalSuccCnt | comma }}<br></span><span class="text">성공</span></p>
										<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.totalFailCnt | comma }}<br></span><span class="text">실패</span></p>					
									</a>
								</li>
								<li @click="fnSetChartData('SMS')" id="setSms" style="width:12.5%">
									<a class="inline-block text-center">
										<h5>SMS 전체</h5>
										<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.smsSuccCnt | comma }}<br></span><span class="text">성공</span></p>
										<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.smsFailCnt | comma }}<br></span><span class="text">실패</span></p>
									</a>
								</li>
								<li @click="fnSetChartData('LMS')" id="setLms" style="width:12.5%">
									<a class="inline-block text-center">
										<h5>LMS 전체</h5>
										<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.lmsSuccCnt | comma }}<br></span><span class="text">성공</span></p>
										<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.lmsFailCnt | comma }}<br></span><span class="text">실패</span></p>
									</a>
								</li>
								<li @click="fnSetChartData('MMS')" id="setMms" style="width:12.5%">
									<a class="inline-block text-center">
										<h5>MMS 전체</h5>
										<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.mmsSuccCnt | comma }}<br></span><span class="text">성공</span></p>
										<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.mmsFailCnt | comma }}<br></span><span class="text">실패</span></p>
									</a>
								</li>
								<li @click="fnSetChartData('RCS')" id="setRcs" style="width:12.5%">
									<a class="inline-block text-center">
										<h5>RCS 전체</h5>
										<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.rcsSuccCnt | comma }}<br></span><span class="text">성공</span></p>
										<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.rcsFailCnt | comma }}<br></span><span class="text">실패</span></p>					
									</a>
								</li>
								<li @click="fnSetChartData('ALIMTALK')" id="setKakaotalk" style="width:12.5%">
									<a class="inline-block text-center">
										<h5>알림톡 전체</h5>
										<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.alimSuccCnt | comma }}<br></span><span class="text">성공</span></p>
										<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.alimFailCnt | comma }}<br></span><span class="text">실패</span></p>					
									</a>
								</li>
								<li @click="fnSetChartData('FRIENDTALK')" id="setFriendtalk" style="width:12.5%">
									<a class="inline-block text-center">
										<h5>친구톡 전체</h5>
										<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.friendSuccCnt | comma }}<br></span><span class="text">성공</span></p>
										<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.friendFailCnt | comma }}<br></span><span class="text">실패</span></p>
									</a>
								</li>
								<li @click="fnSetChartData('PUSH')" id="setPush" style="width:12.5%">
									<a class="inline-block text-center active">
										<h5>PUSH 전체</h5>
										<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">{{ channelTotalCountInfo.pushSuccCnt | comma }}<br></span><span class="text">성공</span></p>
										<p class="inline-block pl10"><span class="number">{{ channelTotalCountInfo.pushFailCnt | comma }}<br></span><span class="text">실패</span></p>					
									</a>
								</li>
							</ul>
						</div>
						<div class="Dashboard01 border-line3 pd20" style="margin-top:-1px">
							<h4 class="lc-1 text-left mt20">{{ this.chName }} 성공/실패 현황</h4>
							<bar-chart :chart-data="successFailResultData" :options="successFailResultOption" :height="100"></bar-chart>
							<h4 class="lc-1 text-left mt20">{{ this.chName }} 실패코드 현황</h4>
							<bar-chart :chart-data="failCodeResultData" :options="failCodeResultOption" :height="200"></bar-chart>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-xs-12">
					<div class="">
						<h4 class="lc-1">당월 이용현황</h4>
						<div class="Dashboard01 border-line2 pd20">
							<bar-chart :chart-data="monthUsedResultData" :options="monthUsedResultOption" :height="100"></bar-chart>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-xs-12">
					<div class="">
						<h4 class="lc-1">최근 6개월간 이용현황</h4>
						<div class="mt20"></div>
						<div class="Dashboard01 border-line2 pd20">
							<bar-chart :chart-data="sixMonthUsedResultData" :options="sixMonthUsedResultOption" :height="100"></bar-chart>
						</div>
					</div>
				</div>
			</div>
			<!-- //본문 -->

			<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
		</article>
		<NoticeLayer ref="noticeLayer"></NoticeLayer>
		<billPopup :popReset="popReset1" :isRead="isRead" :corpInfo="corpInfo"></billPopup>
		<senderUpPopup :popReset="popReset2" :isRead="isRead" :corpInfo="corpInfo"></senderUpPopup>
	</div>
</template>

<script>
// @ is an alias to /src
import HomeMain from '../components/bc-homeMain.vue';
import tokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm";
import homeApi from '@/modules/acHome/service/api';
import customereApi from "@/modules/customer/service/customerApi.js";
import NoticeLayer from "@/modules/customer/components/bp-noticeLayer.vue";
import Calendar from "@/components/Calendar.vue";
import BarChart from '@/components/Chart.vue';
import billPopup from "@/modules/acHome/components/bp-bill"
import senderUpPopup from "@/modules/acHome/components/bp-senderUp"

export default {
	components: {
		HomeMain,
		NoticeLayer,
		Calendar,
		BarChart,
		billPopup,
		senderUpPopup
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
			return 'Admin Console DashBoard';
		}
	},
	filters: {
		comma (val) {
			return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ',')
		}
	},
	data () {
		return {
			projects: [],
			notices: [],
			chName: 'Push',
			channelTotalCountInfo: {totalSuccCnt:0, totalFailCnt:0, pushSuccCnt:0, pushFailCnt:0, rcsSuccCnt:0, rcsFailCnt:0, alimSuccCnt:0, alimFailCnt:0, friendSuccCnt:0, friendFailCnt:0, smsSuccCnt:0, smsFailCnt:0, lmsSuccCnt:0, lmsFailCnt:0, mmsSuccCnt:0, mmsFailCnt:0},
			channelMinInfo: {
				totalTotCnt:0, totalSendingCnt:0, totalWaitRptCnt:0, totalSuccCnt:0, totalFailCnt:0,
				smsTotCnt:0, smsSendingCnt:0, smsWaitRptCnt:0, smsSuccCnt:0, smsFailCnt:0,
				lmsTotCnt:0, lmsSendingCnt:0, lmsWaitRptCnt:0, lmsSuccCnt:0, lmsFailCnt:0,
				mmsTotCnt:0, mmsSendingCnt:0, mmsWaitRptCnt:0, mmsSuccCnt:0, mmsFailCnt:0,
				pushTotCnt:0, pushSendingCnt:0, pushWaitRptCnt:0, pushSuccCnt:0, pushFailCnt:0,
				alimTotCnt:0, alimSendingCnt:0, alimWaitRptCnt:0, alimSuccCnt:0, alimFailCnt:0,
				friendTotCnt:0, friendSendingCnt:0, friendWaitRptCnt:0, friendSuccCnt:0, friendFailCnt:0,
				rcsTotCnt:0, rcsSendingCnt:0, rcsWaitRptCnt:0, rcsSuccCnt:0, rcsFailCnt:0,
			},
			chMinCount: 90,
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
			monthUsedPushList: [],
			monthUsedRcsList: [],
			monthUsedFriendtalkList: [],
			monthUsedAlimtalkList: [],
			monthUsedSmsList: [],
			monthUsedLmsList: [],
			monthUsedMmsList: [],
			sixMonthTimeLine: [],
			sixMonthUsedPushList: [],
			sixMonthUsedRcsList: [],
			sixMonthUsedFriendtalkList: [],
			sixMonthUsedAlimtalkList: [],
			sixMonthUsedSmsList: [],
			sixMonthUsedLmsList: [],
			sixMonthUsedMmsList: [],
			successFailResultData: {},
			successFailResultOption: {},
			failCodeResultData: {},
			failCodeResultOption: {},
			monthUsedResultData: {},
			monthUsedResultOption: {},
			sixMonthUsedResultData: {},
			sixMonthUsedResultOption: {},
			roleCd : '',
			corpInfo : {},
			popReset1 : 0,				// 팝업 초기화할 num
			popReset2 : 0,				// 팝업 초기화할 num
			isRead : true
		}
	},
	created: function() {
		// console.log('created HomeMain');
	},
	watch: {
		chMinCount(val) {
			if(val == 0) {
				this.fnGetChMinList();
			}
		}
	},
	mounted() {
		var vm = this;
		vm.fnOpenNoticePopup();
		vm.fnGetCorpInfo();
		vm.fnGetProjectList();
		vm.fnGetNoticeList();
		vm.fnSetIntervalSearchDate(vm.searchDateInterval);
		vm.fnGetMonthUsedTimeLineList();
		vm.fnGetSixMonthUsedTimeLineList();
		vm.fnGetChMinList();
		vm.fnSetChMinInterval();
	},
	destroyed() {
		clearInterval(this.setCh);
	},
	methods: {
		fnSetChMinInterval(){
			this.setCh = setInterval(() => {
				this.chMinCount--;
			}, 1000);	
		},
		async fnOpenNoticePopup() {
			var roleCd = tokenSvc.getToken().principal.role;
			// USER일경우 팝업 오픈 하지않음.
			if(roleCd == "USER") {
				return false;
			}

			let noticePopupList = [];
			let noticeIdArr = [];
			let exceptNoticeIds = this.$cookies.get('exceptNoticeIds');
			if(!this.$gfnCommonUtils.isEmpty(exceptNoticeIds)){
				noticeIdArr = exceptNoticeIds.split(',');
			}

			const params = {
				sltPopupYn: 'Y',
				exceptNoticeIds: noticeIdArr
			};
			await customereApi.selectNoticeList(params).then(response =>{
				const result = response.data;
				if(result.success) {
				noticePopupList = result.data;
				} else {
				confirm.fnAlert(this.componentsTitle, result.message);
				}
			});

			const vm = this;
			let routeData = '';
			const noticePopupNm = 'noticePopup';
			const popupOtion = 'width=820,height=640,left=20,top=20,scrollbars=yes';
			noticePopupList.forEach(function(info){
				routeData = vm.$router.resolve({name: noticePopupNm, query: {noticeId: info.noticeId}});
				vm.fnOpenWindowPop(routeData.href, noticePopupNm+info.noticeId, popupOtion);
			});
		},

		fnOpenWindowPop(url, name, specs){
			window.open(url, name, specs);
		},

		async fnGetCorpInfo() {
			this.roleCd = tokenSvc.getToken().principal.role;
			await homeApi.selectCorpInfo({}).then(response =>{
				var result = response.data;
				if (result.success) {
					this.corpInfo = result.data
					if (this.corpInfo.billStatus == 'REQ') {
						this.corpInfo.billStatusNm = '후불 사용 요청 중'
					} else if (this.corpInfo.billStatus == 'REV') {
						this.corpInfo.billStatusNm = '후불 사용 검토 중'
					} else if (this.corpInfo.billStatus == 'APP') {
						this.corpInfo.billStatusNm = '후불 사용 승인'
					} else if (this.corpInfo.billStatus == 'RET') {
						this.corpInfo.billStatusNm = '후불 사용 반려'
					}
					if (this.corpInfo.senderUpStatus == 'REQ') {
						this.corpInfo.senderUpStatusNm = '한도금액 상향 요청 중'
					} else if (this.corpInfo.senderUpStatus == 'REV') {
						this.corpInfo.senderUpStatusNm = '한도금액 상향 검토 중'
					} else if (this.corpInfo.senderUpStatus == 'APP') {
						this.corpInfo.senderUpStatusNm = '한도금액 상향 승인'
					} else if (this.corpInfo.senderUpStatus == 'RET') {
						this.corpInfo.senderUpStatusNm = '한도금액 상향 반려'
					}
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
		// 청구정보 팝업
		fnBill1(){
			if (this.roleCd != 'OWNER') return;
			this.isRead = true;
			this.popReset1 += 1;
			jQuery("#billPopup").modal("show");
		},
		fnBill2(){
			if (this.roleCd != 'OWNER') return;
			this.isRead = false;
			this.popReset1 += 1;
			jQuery("#billPopup").modal("show");
		},
		// 한도상향 팝업
		fnSenderUp1(){
			if (this.roleCd != 'OWNER') return;
			this.isRead = true;
			this.popReset2 += 1;
			jQuery("#senderUpPopup").modal("show");
		},
		fnSenderUp2(){
			if (this.roleCd != 'OWNER') return;
			this.isRead = false;
			this.popReset2 += 1;
			jQuery("#senderUpPopup").modal("show");
		},
		async fnGetProjectList() {
			let params = {
				userId: tokenSvc.getToken().principal.userId
			};

			await homeApi.selectProjectList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.projects = result.data;
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
		async fnGetNoticeList() {
			let params = {
			};

			await homeApi.selectNoticeList(params).then(response =>{
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
		async fnGetChTotCntInfo() {
			let params = {
				corpId: tokenSvc.getToken().principal.corpId,
				startDateStr: this.searchData.searchStartDate,
				endDateStr: this.searchData.searchEndDate
			};

			await homeApi.selectChTotCntInfo(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.channelTotalCountInfo = result.data.chTotCntInfo;
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
		async fnSetChartData(channel) {
			let params = {
				corpId: tokenSvc.getToken().principal.corpId,
				startDateStr: this.searchData.searchStartDate,
				endDateStr: this.searchData.searchEndDate,
				channel: channel
			};

			await homeApi.selectChSuccFailCntList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.fnGetChSuccFailCntList(result.data);
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});

			await homeApi.selectChFailCodeList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.fnGetChFailCodeList(result.data);
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});

			jQuery('.mt10 > ul > li').removeClass('active');
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
		async fnGetChMinList() {
			let params = {};

			await homeApi.selectChMinList(params).then(response =>{
				let result = response.data;
				let success = result.success;
				if(success) {
					let data = result.data;
					this.channelMinInfo.smsTotCnt = data.smsTotCnt;
					this.channelMinInfo.smsSendingCnt = data.smsSendingCnt;
					this.channelMinInfo.smsWaitRptCnt = data.smsWaitRptCnt;
					this.channelMinInfo.smsSuccCnt = data.smsSuccCnt;
					this.channelMinInfo.smsFailCnt = data.smsFailCnt;

					this.channelMinInfo.lmsTotCnt = data.lmsTotCnt;
					this.channelMinInfo.lmsSendingCnt = data.lmsSendingCnt;
					this.channelMinInfo.lmsWaitRptCnt = data.lmsWaitRptCnt;
					this.channelMinInfo.lmsSuccCnt = data.lmsSuccCnt;
					this.channelMinInfo.lmsFailCnt = data.lmsFailCnt;

					this.channelMinInfo.mmsTotCnt = data.mmsTotCnt;
					this.channelMinInfo.mmsSendingCnt = data.mmsSendingCnt;
					this.channelMinInfo.mmsWaitRptCnt = data.mmsWaitRptCnt;
					this.channelMinInfo.mmsSuccCnt = data.mmsSuccCnt;
					this.channelMinInfo.mmsFailCnt = data.mmsFailCnt;

					this.channelMinInfo.rcsTotCnt = data.rcsTotCnt;
					this.channelMinInfo.rcsSendingCnt = data.rcsSendingCnt;
					this.channelMinInfo.rcsWaitRptCnt = data.rcsWaitRptCnt;
					this.channelMinInfo.rcsSuccCnt = data.rcsSuccCnt;
					this.channelMinInfo.rcsFailCnt = data.rcsFailCnt;

					this.channelMinInfo.alimTotCnt = data.alimTotCnt;
					this.channelMinInfo.alimSendingCnt = data.alimSendingCnt;
					this.channelMinInfo.alimWaitRptCnt = data.alimWaitRptCnt;
					this.channelMinInfo.alimSuccCnt = data.alimSuccCnt;
					this.channelMinInfo.alimFailCnt = data.alimFailCnt;

					this.channelMinInfo.friendTotCnt = data.friendTotCnt;
					this.channelMinInfo.friendSendingCnt = data.friendSendingCnt;
					this.channelMinInfo.friendWaitRptCnt = data.friendWaitRptCnt;
					this.channelMinInfo.friendSuccCnt = data.friendSuccCnt;
					this.channelMinInfo.friendFailCnt = data.friendFailCnt;

					this.channelMinInfo.pushTotCnt = data.pushTotCnt;
					this.channelMinInfo.pushSendingCnt = data.pushSendingCnt;
					this.channelMinInfo.pushWaitRptCnt = data.pushWaitRptCnt;
					this.channelMinInfo.pushSuccCnt = data.pushSuccCnt;
					this.channelMinInfo.pushFailCnt = data.pushFailCnt;

					this.channelMinInfo.totalTotCnt = data.smsTotCnt + data.lmsTotCnt + data.mmsTotCnt + data.rcsTotCnt + data.alimTotCnt + data.friendTotCnt + data.pushTotCnt;
					this.channelMinInfo.totalSendingCnt = data.smsSendingCnt + data.lmsSendingCnt + data.mmsSendingCnt + data.rcsSendingCnt + data.alimSendingCnt + data.friendSendingCnt + data.pushSendingCnt;
					this.channelMinInfo.totalWaitRptCnt = data.smsWaitRptCnt + data.lmsWaitRptCnt + data.mmsWaitRptCnt + data.rcsWaitRptCnt + data.alimWaitRptCnt + data.friendWaitRptCnt + data.pushWaitRptCnt;
					this.channelMinInfo.totalSuccCnt = data.smsSuccCnt + data.lmsSuccCnt + data.mmsSuccCnt + data.rcsSuccCnt + data.alimSuccCnt + data.friendSuccCnt + data.pushSuccCnt;
					this.channelMinInfo.totalFailCnt = data.smsFailCnt + data.lmsFailCnt + data.mmsFailCnt + data.rcsFailCnt + data.alimFailCnt + data.friendFailCnt + data.pushFailCnt;
					
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}

				// 시간 초기화
				this.chMinCount = 90;
				
			});
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
			this.successFailResultOption = {
				responsive: true,
				scales: {
					yAxes: [{
						ticks: {
							beginAtZero: true,
							callback: function(value, index, values) {
								if (parseInt(value) >= 1000) {
									return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '건';
								} else {
									return value + '건';
								}
							}
						},
					}]
				}
			}
			this.$forceUpdate()
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
				labels: this.dateLine,
				datasets: this.failCodeResultDataset
			}
			this.failCodeResultOption = {
				responsive: true,
				scales: {
					yAxes: [{
						ticks: {
							beginAtZero: true,
							callback: function(value, index, values) {
								if (parseInt(value) >= 1000) {
									return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '건';
								} else {
									return value + '건';
								}
							}
						},
					}]
				}
			}
			this.$forceUpdate()
		},
		// 당월 이용현황 시간대 조회
		async fnGetMonthUsedTimeLineList() {
			let params = {
				corpId: tokenSvc.getToken().principal.corpId,
			};

			await homeApi.selectMonthUsedDataList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					for (var i = 0; i < result.data.length; i++) {
						this.timeLine.push(result.data[i].date);
						this.monthUsedPushList.push(result.data[i].pushCnt);
						this.monthUsedRcsList.push(result.data[i].rcsCnt);
						this.monthUsedFriendtalkList.push(result.data[i].friendtalkCnt);
						this.monthUsedAlimtalkList.push(result.data[i].alimtalkCnt);
						this.monthUsedSmsList.push(result.data[i].smsCnt);
						this.monthUsedLmsList.push(result.data[i].lmsCnt);
						this.monthUsedMmsList.push(result.data[i].mmsCnt);
					}

					this.monthUsedResultData = {
						labels: this.timeLine,
						datasets: [
							{
								label: 'SMS',
								backgroundColor: '#8041D9',
								pointBackgroundColor: 'white',
								borderWidth: 1,
								pointBorderColor: '#249EBF',
								data: this.monthUsedSmsList
							},
							{
								label: 'LMS',
								backgroundColor: '#0054FF',
								pointBackgroundColor: 'white',
								borderWidth: 1,
								pointBorderColor: '#249EBF',
								data: this.monthUsedLmsList
							},
							{
								label: 'MMS',
								backgroundColor: '#1DDB16',
								pointBackgroundColor: 'white',
								borderWidth: 1,
								pointBorderColor: '#249EBF',
								data: this.monthUsedMmsList
							},
							{
								label: 'RCS',
								backgroundColor: '#FFE08C',
								pointBackgroundColor: 'white',
								borderWidth: 1,
								pointBorderColor: '#249EBF',
								data: this.monthUsedRcsList
							},
							{
								label: '친구톡',
								backgroundColor: '#FFBB00',
								pointBackgroundColor: 'white',
								borderWidth: 1,
								pointBorderColor: '#249EBF',
								data: this.monthUsedFriendtalkList
							},
							{
								label: '알림톡',
								backgroundColor: '#5CD1E5',
								pointBackgroundColor: 'white',
								borderWidth: 1,
								pointBorderColor: '#249EBF',
								data: this.monthUsedAlimtalkList
							},
							{
								label: 'Push',
								backgroundColor: '#f87979',
								pointBackgroundColor: 'white',
								borderWidth: 1,
								pointBorderColor: '#249EBF',
								data: this.monthUsedPushList
							}
						]
					}
					this.monthUsedResultOption = {
						responsive: true,
						scales: {
							yAxes: [{
								ticks: {
									beginAtZero: true,
									callback: function(value, index, values) {
										if (parseInt(value) >= 1000) {
											return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '건';
										} else {
											return value + '건';
										}
									}
								},
							}]
						}
					}
					this.$forceUpdate()
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
		// 최근 6개월간 이용현황 시간대 조회
		async fnGetSixMonthUsedTimeLineList() {
			let params = {
				corpId: tokenSvc.getToken().principal.corpId
			};

			await homeApi.selectSixMonthUsedDataList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					for (var i = 0; i < result.data.length; i++) {
						this.sixMonthTimeLine.push(result.data[i].date);
						this.sixMonthUsedPushList.push(result.data[i].pushCnt);
						this.sixMonthUsedRcsList.push(result.data[i].rcsCnt);
						this.sixMonthUsedFriendtalkList.push(result.data[i].friendtalkCnt);
						this.sixMonthUsedAlimtalkList.push(result.data[i].alimtalkCnt);
						this.sixMonthUsedSmsList.push(result.data[i].smsCnt);
						this.sixMonthUsedLmsList.push(result.data[i].lmsCnt);
						this.sixMonthUsedMmsList.push(result.data[i].mmsCnt);
					}

					this.sixMonthUsedResultData = {
						labels: this.sixMonthTimeLine,
						datasets: [
							{
								label: 'SMS',
								backgroundColor: '#8041D9',
								pointBackgroundColor: 'white',
								borderWidth: 1,
								pointBorderColor: '#249EBF',
								data: this.sixMonthUsedSmsList
							},
							{
								label: 'LMS',
								backgroundColor: '#0054FF',
								pointBackgroundColor: 'white',
								borderWidth: 1,
								pointBorderColor: '#249EBF',
								data: this.sixMonthUsedLmsList
							},
							{
								label: 'MMS',
								backgroundColor: '#1DDB16',
								pointBackgroundColor: 'white',
								borderWidth: 1,
								pointBorderColor: '#249EBF',
								data: this.sixMonthUsedMmsList
							},
							{
								label: 'RCS',
								backgroundColor: '#FFE08C',
								pointBackgroundColor: 'white',
								borderWidth: 1,
								pointBorderColor: '#249EBF',
								data: this.sixMonthUsedRcsList
							},
							{
								label: '친구톡',
								backgroundColor: '#FFBB00',
								pointBackgroundColor: 'white',
								borderWidth: 1,
								pointBorderColor: '#249EBF',
								data: this.sixMonthUsedFriendtalkList
							},
							{
								label: '알림톡',
								backgroundColor: '#5CD1E5',
								pointBackgroundColor: 'white',
								borderWidth: 1,
								pointBorderColor: '#249EBF',
								data: this.sixMonthUsedAlimtalkList
							},
							{
								label: 'Push',
								backgroundColor: '#f87979',
								pointBackgroundColor: 'white',
								borderWidth: 1,
								pointBorderColor: '#249EBF',
								data: this.sixMonthUsedPushList
							}
						]
					}
					this.sixMonthUsedResultOption = {
						responsive: true,
						scales: {
							yAxes: [{
								ticks: {
									beginAtZero: true,
									callback: function(value, index, values) {
										if (parseInt(value) >= 1000) {
											return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '건';
										} else {
											return value + '건';
										}
									}
								},
							}]
						}
					}
					this.$forceUpdate()
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
		// 검색일자변경
		async fnSetIntervalSearchDate(interval){
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
			this.$nextTick(() => this.$router.replace("/ac/home"));
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
		}
	}
};
</script>