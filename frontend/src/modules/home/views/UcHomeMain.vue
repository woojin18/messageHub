<template>
	<div>
		<div class="contentHeader">
			<h2>대시보드</h2>
			<a href="#self" class="btnStyle1 absolute top0 right0" onClick="window.location.reload()" title="비트큐브 대시보드 이용안내">이용안내 <i class="fal fa-book-open"></i></a>
		</div>

		<!-- 본문 -->
		<div class="row">
			<div class="col-xs-12">
				<h4 class="lc-1 mt0 mb15">채널별 성공/실패율 현황</h4>
				<ul class="tab_s4 of-h" style="width:100%">
					<li style="width:16.6%">
						<a href="#self" class="inline-block text-center" title="채널별 성공/실패율 현황 PUSH">
							<p class="tab_maintext mb20">PUSH</p>
							<div><span class="inline-block btnStyle6 mb20" style="border-radius:19px">전체</span></div>
							<p class="inline-block color1 pr10 border-right"><span class="number">85%<br></span><span class="text">성공</span></p>
							<p class="inline-block pl10"><span class="number">15%<br></span><span class="text">실패</span></p>					
						</a>
					</li>
					<li style="width:16.6%">
						<a href="#self" class="inline-block text-center" title="채널별 성공/실패율 현황 RCS">
							<p class="tab_maintext mb20">RCS</p>
							<div><span class="inline-block btnStyle6 mb20" style="border-radius:19px">전체</span></div>
							<p class="inline-block color1 pr10 border-right"><span class="number">0<br></span><span class="text">성공</span></p>
							<p class="inline-block pl10"><span class="number">0<br></span><span class="text">실패</span></p>					
						</a>
					</li>
					<li style="width:16.6%">
						<a href="#self" class="inline-block text-center" title="채널별 성공/실패율 현황 친구톡">
							<p class="tab_maintext mb20">친구톡</p>
							<div><span class="inline-block btnStyle6 mb20" style="border-radius:19px">전체</span></div>
							<p class="inline-block color1 pr10 border-right"><span class="number">-<br></span><span class="text">성공</span></p>
							<p class="inline-block pl10"><span class="number">-<br></span><span class="text">실패</span></p>					
						</a>
					</li>
					<li style="width:16.6%">
						<a href="#self" class="inline-block text-center" title="채널별 성공/실패율 현황 알림톡">
							<p class="tab_maintext mb20">알림톡</p>
							<div><span class="inline-block btnStyle6 mb20" style="border-radius:19px">전체</span></div>
							<p class="inline-block color1 pr10 border-right"><span class="number">-<br></span><span class="text">성공</span></p>
							<p class="inline-block pl10"><span class="number">-<br></span><span class="text">실패</span></p>					
						</a>
					</li>
					<li style="width:16.6%">
						<a href="#self" class="inline-block text-center" title="채널별 성공/실패율 현황 SMS">
							<p class="tab_maintext mb20">SMS</p>
							<div><span class="inline-block btnStyle6 mb20" style="border-radius:19px">전체</span></div>
							<p class="inline-block color1 pr10 border-right"><span class="number">-<br></span><span class="text">성공</span></p>
							<p class="inline-block pl10"><span class="number">-<br></span><span class="text">실패</span></p>					
						</a>
					</li>
					<li style="width:16.6%">
						<a href="#self" class="inline-block text-center" title="채널별 성공/실패율 현황 MMS">
							<p class="tab_maintext mb20">MMS</p>
							<div><span class="inline-block btnStyle6 mb20" style="border-radius:19px">전체</span></div>
							<p class="inline-block color1 pr10 border-right"><span class="number">-<br></span><span class="text">성공</span></p>
							<p class="inline-block pl10"><span class="number">-<br></span><span class="text">실패</span></p>					
						</a>
					</li>
				</ul>
			</div>
		</div>

		<div class="row mt20">
			<div class="col-xs-12">
				<input type="text" class="datepicker inputStyle maxWidth160"><span style="padding:0 11px">~</span><input type="text" class="datepicker inputStyle maxWidth160">
				<ul class="tab_s2 ml20">
					<li><a href="#" title="오늘 날짜 검색">오늘</a></li>
					<li><a href="#" title="1주일 날짜 검색">1주일</a></li>
					<li><a href="#" title="15일 날짜 검색">15일</a></li>
					<li><a href="#" title="1개월 날짜 검색">1개월</a></li>
				</ul>
			</div>
		</div>

		<div class="row">
			<div class="col-xs-12">
				<h4 class="lc-1 mb20 mt40">발송통계</h4>
				<img src="../../../../public/se2/images/chart_sub03_1.svg" alt="차트01" class="img_chart01 img-responsive mt20">
			</div>
		</div>

		<div class="row">
			<div class="col-xs-12">
				<div class="Dashboard01 pd0">
					<h4 class="lc-1 mt40 mb20">공지사항</h4>
					<table id="#admin_notice1" class="table_skin1" style="width:100%">
						<colgroup>
							<col style="width:10%">
							<col>
							<col style="width:10%">
							<col style="width:15%">
							<col style="width:15%">
						</colgroup>
						<thead>
							<tr>
							<th class="text-center">No.</th>
							<th class="text-center">공지사항 제목</th>
							<th class="text-center">공지구분</th>
							<th class="text-center">등록자</th>
							<th class="text-center end">등록일자</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-center">5</td>
								<td class="text-left"><a href="#self" data-toggle="modal" data-target="#notiView" title="SMS MO 페이지로 이동합니다">SMS MO</a></td>
								<td class="text-center">안내</td>
								<td class="text-center">siga77@bitcube.co.kr</td>
								<td class="text-center end">2021.02.08 00:00:00</td>
							</tr>
							<tr>
								<td class="text-center">4</td>
								<td class="text-left"><a href="#self" data-toggle="modal" data-target="#notiView" title="SMS MO 페이지로 이동합니다">SMS MO</a></td>
								<td class="text-center">안내</td>
								<td class="text-center">James@bitcube.co.kr</td>
								<td class="text-center end">2021.02.08 00:00:00</td>
							</tr>
							<tr>
								<td class="text-center">3</td>
								<td class="text-left"><a href="#self" data-toggle="modal" data-target="#notiView" title="SMS MO 페이지로 이동합니다">SMS MO</a></td>
								<td class="text-center">안내</td>
								<td class="text-center">Lucia@bitcube.co.kr</td>
								<td class="text-center end">2021.02.08 00:00:00</td>
							</tr>
							<tr>
								<td class="text-center">2</td>
								<td class="text-left"><a href="#self" data-toggle="modal" data-target="#notiView" title="SMS MO 페이지로 이동합니다">SMS MO</a></td>
								<td class="text-center">안내</td>
								<td class="text-center">hong@bitcube.co.kr</td>
								<td class="text-center end">2021.02.08 00:00:00</td>
							</tr>
							<tr>
								<td class="text-center">1</td>
								<td class="text-left"><a href="#self" data-toggle="modal" data-target="#notiView" title="SMS MO 페이지로 이동합니다">SMS MO</a></td>
								<td class="text-center">안내</td>
								<td class="text-center">siga77@bitcube.co.kr</td>
								<td class="text-center end">2021.02.08 00:00:00</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!-- //본문 -->

		<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
	</div>
	<!-- //content -->
</template>

<script>
// @ is an alias to /src
import tokenSvc from '@/common/token-service';
import HomeMain from '../components/UcHomeMain.vue';

export default {
	components: {
		HomeMain,
	},
	created: function() {
		console.log('created HomeMain');
		console.log('token:', tokenSvc.getToken());
	},
	methods: {
	},
};
</script>
