<template>
	<div>
		<article>
			<div class="contentHeader">
				<h2>비트큐브 대시보드</h2>
				<a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="새로고침">새로고침 <i class="fal fa-redo"></i></a>
			</div>

			<!-- 본문 -->
			<div class="row">
				<div class="col-xs-6">
					<div class="Dashboard01 fl border-line">
						<h4 class="inline-block topH4">이용중인 프로젝트 현황</h4>
						<a href="#" class="moreBtn inline-block float-right" title="더보기"><img src="../../../common/images/icon_Plus_black.svg" class="fr" alt="더보기"></a>
						<table class="table_skin1" style="width:100%">
							<colgroup>
								<col style="width:45%">
								<col style="width:25%">
								<col style="width:30%">
							</colgroup>
							<thead>
								<tr>
								<th class="text-center">프로젝트</th>
								<th class="text-center">신청 서비스</th>
								<th class="text-center end">관리</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="text-left"><a href="#" class="boardTitle" title="엘지유플러스 페이지로 이동">엘지유플러스</a></td>
									<td class="text-center">5개</td>
									<td class="text-center end"><a href="#self" class="btnStyle1 borderLightGray small mr5" title="상세">상세</a><a href="#self" class="btnStyle1 borderLightGray small" title="UMS">UMS</a></td>
								</tr>
								<tr>
									<td class="text-left"><a href="#" class="boardTitle" title="이커머스테크 페이지로 이동">이커머스테크</a></td>
									<td class="text-center">3개</td>
									<td class="text-center end"><a href="#self" class="btnStyle1 borderLightGray small mr5" title="상세">상세</a><a href="#self" class="btnStyle1 borderLightGray small" title="UMS">UMS</a></td>
								</tr>
								<tr>
									<td class="text-left"><a href="#" class="boardTitle" title="비트큐브 페이지로 이동">비트큐브</a></td>
									<td class="text-center">2개</td>
									<td class="text-center end"><a href="#self" class="btnStyle1 borderLightGray small mr5" title="상세">상세</a><a href="#self" class="btnStyle1 borderLightGray small" title="UMS">UMS</a></td>
								</tr>
								<tr>
									<td class="text-left"><a href="#" class="boardTitle" title="아이디알인터랙티브 페이지로 이동">아이디알인터랙티브</a></td>
									<td class="text-center">5개</td>
									<td class="text-center end"><a href="#self" class="btnStyle1 borderLightGray small mr5" title="상세">상세</a><a href="#self" class="btnStyle1 borderLightGray small" title="UMS">UMS</a></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="col-xs-6">
					<div class="Dashboard02 fr border-line" style="height:291px">
						<h4 class="topH4">당월 이용현황</h4>
						<img src="../../../common/images/chart_sub01.svg" alt="이용중인 서비스가 없습니다" class="img-responsive">						
					</div>			
				</div>
			</div>
			
			<div class="border-line mt20">
				<div class="row">
					<div class="col-xs-12">
						<div class="Menu01 menuBox">
							<div class="of_h">
								<h4 class="inline-block" style="width:10%">프로젝트 선택</h4>
								<select name="admin01_1" class="inline-block selectStyle2" style="width:89%" title="프로젝트 선택란">
									<option value="전체 Console">전체</option>
									<option value="전체 Console">전체</option>
								</select>
							</div>	
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-xs-12">
						<div class="Dashboard03">
							<h4 class="topH4">채널별 성공/실패율</h4>
							<ul class="tabStyle tab6_sub01 mt0">
								<li class="active"><a href="#" title="전체 페이지로 이동">전체</a></li>
								<li><a href="#" title="Push 사용현황 페이지로 이동">Push 사용현황</a></li>
								<li><a href="#" title="RCS 사용현황 페이지로 이동">RCS 사용현황</a></li>
								<li><a href="#" title="알림톡 사용현황 페이지로 이동">알림톡 사용현황</a></li>
								<li><a href="#" title="친구톡 사용현황 페이지로 이동">친구톡 사용현황</a></li>
								<li><a href="#" title="SMS 사용현황 페이지로 이동">SMS 사용현황</a></li>
							</ul>
							<div class="of_h mt10">
								<div class="date"><a href="#" title="이전 달력 보기"><i class="far fa-chevron-left"></i></a>2020.12.27 ~ 2021.01.03<a href="#" title="다음 달력 보기"><i class="far fa-chevron-right"></i></a></div>
								<ul class="tab_s ml20">
									<li><a href="#" title="오늘">오늘</a></li>
									<li class="active"><a href="#" title="1주일">1주일</a></li>
									<li><a href="#" title="15일">15일</a></li>
									<li><a href="#" title="1개월">1개월</a></li>
								</ul>
								<p class="inline-block float-right">총 <span class="color1">190</span>건 발송</p>
								<img src="../../../common/images/chart_01.svg" alt="차트01" class="img_chart01 img-responsive mt10">
							</div>
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
import tokenSvc from '@/common/token-service';
import HomeMain from '../components/bc-homeMain.vue';
import homeApi from '@/modules/acHome/service/api';
import loginApi from '@/modules/login/service/api';
import axios from 'axios';

export default {
	components: {
		HomeMain,
	},
	created: function() {
		console.log('created HomeMain');
		console.log('token:', tokenSvc.getToken());
	},
	methods: {
		logout: function () {
			alert("test");
			axios.get('/api/auth/logout').then(res => {
				alert('asdfasdfasdf');
			});
		}
	},
};
</script>