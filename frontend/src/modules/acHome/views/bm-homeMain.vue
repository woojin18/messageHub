<template>
	<div>
		<article>
			<div class="contentHeader">
				<h2>대시보드</h2>
				<a class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="새로고침">새로고침 <i class="fal fa-redo"></i></a>
			</div>
			<!-- 본문 -->
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
											<router-link :to="{name: 'projectMain', params: { projectId: pjt.projectId, projectName: pjt.projectName }}">{{pjt.projectName}}</router-link>
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
											<router-link :to="{name: 'noticeDetail', params: { noticeId: ntc.noticeId }}">{{ntc.title}}</router-link>
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
							<Calendar @update-date="fnUpdateStartDate" calendarId="searchStartDate" classProps="datepicker inputStyle" styleProps="width:15%" :initDate="searchData.searchStartDate"></Calendar>
							<span style="padding:0 11px">~</span>
							<Calendar @update-date="fnUpdateEndDate" calendarId="searchEndDate" classProps="datepicker inputStyle" styleProps="width:15%" :initDate="searchData.searchEndDate"></Calendar>
							<ul class="tab_s2 ml20">
								<li :class="this.searchDateInterval==0 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(0);" title="오늘 날짜 서비스 검색">오늘</a></li>
								<li :class="this.searchDateInterval==7 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(7);" title="1주일 서비스 검색">1주일</a></li>
								<li :class="this.searchDateInterval==15 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(15);" title="15일 서비스 검색">15일</a></li>
								<li :class="this.searchDateInterval==30 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(30);" title="1개월 서비스 검색">1개월</a></li>
							</ul>
						</div>
						<div class="mt10">
							<ul class="tab_s4_2 of-h" style="width:100%">
								<li @click="setRandomData('push')" id="setPush" style="width:16.7%" class="active">
									<a class="inline-block text-center active">
										<h5>PUSH 전체</h5>
										<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">55<br></span><span class="text">성공</span></p>
										<p class="inline-block pl10"><span class="number">0<br></span><span class="text">실패</span></p>					
									</a>
								</li>
								<li @click="setRandomData('rcs')" id="setRcs" style="width:16.7%">
									<a class="inline-block text-center">
										<h5>RCS 전체</h5>
										<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">0<br></span><span class="text">성공</span></p>
										<p class="inline-block pl10"><span class="number">0<br></span><span class="text">실패</span></p>					
									</a>
								</li>
								<li @click="setRandomData('kakaotalk')" id="setKakaotalk" style="width:16.6%">
									<a class="inline-block text-center">
										<h5>알림톡 전체</h5>
										<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">-<br></span><span class="text">성공</span></p>
										<p class="inline-block pl10"><span class="number">-<br></span><span class="text">실패</span></p>					
									</a>
								</li>
								<li @click="setRandomData('friendtalk')" id="setFriendtalk" style="width:16.7%">
									<a class="inline-block text-center">
										<h5>친구톡 전체</h5>
										<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">-<br></span><span class="text">성공</span></p>
										<p class="inline-block pl10"><span class="number">-<br></span><span class="text">실패</span></p>					
									</a>
								</li>
								<li @click="setRandomData('sms')" id="setSms" style="width:16.7%">
									<a class="inline-block text-center">
										<h5>SMS 전체</h5>
										<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">-<br></span><span class="text">성공</span></p>
										<p class="inline-block pl10"><span class="number">-<br></span><span class="text">실패</span></p>					
									</a>
								</li>
								<li @click="setRandomData('mms')" id="setMms" style="width:16.6%">
									<a class="inline-block text-center">
										<h5>MMS 전체</h5>
										<p class="inline-block color1 pr10 border-right consolMarginTop"><span class="number">-<br></span><span class="text">성공</span></p>
										<p class="inline-block pl10"><span class="number">-<br></span><span class="text">실패</span></p>					
									</a>
								</li>
							</ul>
						</div>
						<div class="Dashboard01 border-line3 pd20" style="margin-top:-1px">
							<h4 class="lc-1 text-left mt20">Push 성공/실패 현황</h4>
							<bar-chart :chart-data="successFailResultData" :height="100"></bar-chart>
							<h4 class="lc-1 text-left mt20">Push 실패코드 현황</h4>
							<bar-chart :chart-data="failCodeResultData" :height="100"></bar-chart>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-xs-12">
					<div class="">
						<h4 class="lc-1">당월 이용현황</h4>
						<div class="Dashboard01 border-line2 pd20">
							<bar-chart :chart-data="monthUsedResultData" :height="100"></bar-chart>
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
							<bar-chart :chart-data="sixMonthUsedResultData" :height="100"></bar-chart>
						</div>
					</div>
				</div>
			</div>
			<!-- //본문 -->

			<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
		</article>
	</div>
</template>

<script>
// @ is an alias to /src
import HomeMain from '../components/bc-homeMain.vue';
import tokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm";
import homeApi from '@/modules/acHome/service/api';
import loginApi from '@/modules/login/service/api';
import Calendar from "@/components/Calendar.vue";
import BarChart from '@/components/Chart.vue';
import * as utils from '@/common/utils';
import { consts } from '@/common/config';
import axios from 'axios';

export default {
	components: {
		HomeMain,
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
	data () {
		return {
			projects: [],
			notices: [],
			searchDateInterval: 7,
			successFailResultData: {
				labels: ['20210609', '20210610', '20210611', '20210612', '20210613', '20210614', '20210615'],
				datasets: [
					{
						label: '발송성공',
						backgroundColor: '#f87979',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [40, 20, 30, 10, 50, 60, 50]
					},
					{
						label: '발송실패',
						backgroundColor: '#8C8C8C',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [10, 0, 10, 10, 20, 30, 20]
					}
				]
			},
			failCodeResultData: {
				labels: ['20210609', '20210610', '20210611', '20210612', '20210613', '20210614', '20210615'],
				datasets: [
					{
						label: 'Code1',
						backgroundColor: '#0100FF',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [40, 20, 30, 10, 50, 60, 50]
					},
					{
						label: 'Code2',
						backgroundColor: '#00D8FF',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [10, 0, 10, 10, 20, 30, 20]
					},
					{
						label: 'Code3',
						backgroundColor: '#FF0000',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [110, 100, 110, 110, 120, 130, 120]
					},
					{
						label: 'Code4',
						backgroundColor: '#F2CB61',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [60, 30, 40, 50, 120, 130, 70]
					}
				]
			},
			monthUsedResultData: {
				labels: ['20210601', '20210602', '20210603', '20210604', '20210605', '20210606', '20210607', '20210608', '20210609', '20210610', '20210611', '20210612', '20210613', '20210614', '20210615'],
				datasets: [
					{
						label: 'Push',
						backgroundColor: '#f87979',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [140, 120, 130, 110, 150, 160, 150, 70, 320, 210, 60, 90, 150, 260, 400]
					},
					{
						label: 'RCS',
						backgroundColor: '#FFE08C',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [110, 100, 110, 110, 120, 130, 120, 30, 60, 90, 210, 270, 120, 100, 190]
					},
					{
						label: '친구톡',
						backgroundColor: '#FFBB00',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [60, 50, 60, 60, 70, 80, 70, 60, 50, 60, 60, 70, 80, 70, 120]
					},
					{
						label: '알림톡',
						backgroundColor: '#5CD1E5',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [30, 10, 10, 50, 70, 90, 120, 30, 10, 10, 50, 70, 90, 160, 190]
					},
					{
						label: 'SMS',
						backgroundColor: '#8041D9',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [210, 200, 110, 160, 170, 330, 320, 190, 70, 130, 90, 160, 210, 300, 110]
					},
					{
						label: 'MMS',
						backgroundColor: '#1DDB16',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [110, 100, 60, 60, 70, 230, 220, 90, 20, 30, 40, 60, 110, 200, 60]
					}
				]
			},
			sixMonthUsedResultData: {
				labels: ['202101', '202102', '202103', '202104', '202105', '202106'],
				datasets: [
					{
						label: 'Push',
						backgroundColor: '#f87979',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [1140, 1120, 1130, 1110, 1150, 1160]
					},
					{
						label: 'RCS',
						backgroundColor: '#FFE08C',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [1110, 1100, 1110, 1110, 1120, 1130]
					},
					{
						label: '친구톡',
						backgroundColor: '#FFBB00',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [660, 650, 660, 660, 670, 680]
					},
					{
						label: '알림톡',
						backgroundColor: '#5CD1E5',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [730, 710, 710, 750, 770, 790]
					},
					{
						label: 'SMS',
						backgroundColor: '#8041D9',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [810, 800, 710, 760, 770, 930]
					},
					{
						label: 'MMS',
						backgroundColor: '#1DDB16',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [510, 500, 410, 460, 470, 630]
					}
				]
			}
		}
	},
	created: function() {
		console.log('created HomeMain');
	},
	mounted() {
		this.fnSetIntervalSearchDate(this.searchDateInterval);
		this.fnGetNoticeList();
		this.fnGetProjectList();
	},
	methods: {
		fnGetProjectList() {
			let params = {
				userId: tokenSvc.getToken().principal.userId
			};

			homeApi.selectProjectList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.projects = result.data;
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
		setRandomData(channel) {
			this.successFailResultData = {
				labels: ['20210609', '20210610', '20210611', '20210612', '20210613', '20210614', '20210615'],
				datasets: [
					{
						label: '발송성공',
						backgroundColor: '#f87979',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt()]
					},
					{
						label: '발송실패',
						backgroundColor: '#8C8C8C',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt()]
					}
				]
			},
			this.failCodeResultData = {
				labels: ['20210609', '20210610', '20210611', '20210612', '20210613', '20210614', '20210615'],
				datasets: [
					{
						label: 'Code1',
						backgroundColor: '#0100FF',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt()]
					},
					{
						label: 'Code2',
						backgroundColor: '#00D8FF',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt()]
					},
					{
						label: 'Code3',
						backgroundColor: '#FF0000',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt()]
					},
					{
						label: 'Code4',
						backgroundColor: '#F2CB61',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt(), this.getRandomInt()]
					}
				]
			}

			jQuery('.mt10 > ul > li').removeClass('active');
			if (channel == 'push') {
				jQuery("#setPush").addClass('active');
			} else if (channel == 'rcs') {
				jQuery("#setRcs").addClass('active');
			} else if (channel == 'kakaotalk') {
				jQuery("#setKakaotalk").addClass('active');
			} else if (channel == 'friendtalk') {
				jQuery("#setFriendtalk").addClass('active');
			} else if (channel == 'sms') {
				jQuery("#setSms").addClass('active');
			} else if (channel == 'mms') {
				jQuery("#setMms").addClass('active');
			}
		},
		getRandomInt () {
			return Math.floor(Math.random() * (50 - 5 + 1)) + 5
		}
	},
};
</script>