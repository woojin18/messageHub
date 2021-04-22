<template>
  <div>
		<article>
			<div class="contentHeader mb20">
				<h2>{{this.projectName}}</h2>
			</div>

			<!-- 본문 -->
			<div class="contentBody mb40">
				<ul class="tab_s3">
					<li @click="fnMoveMainTab('dashBoard')"><a class="width120">대시보드</a></li>
					<li @click="fnMoveMainTab('info')"><a class="width120">기본정보</a></li>
					<li @click="fnMoveMainTab('member')"><a class="width120">멤버관리</a></li>
					<li @click="fnMoveMainTab('channel')" class="active"><a class="width120">채널관리</a></li>
					<li @click="fnMoveMainTab('callback')"><a class="width120">발신번호관리</a></li>
					<li @click="fnMoveMainTab('spam')"><a class="width120">스팸관리</a></li>
				</ul>			
			</div>
			<ul class="tabStyle tab6 bgColor_tapGray mt30">
				<li @click="fnMoveSubTab('chan-rcs')" class="active"><a>RCS</a></li>
				<li @click="fnMoveSubTab('chan-smsmms')"><a>SMS/MMS</a></li>
				<li @click="fnMoveSubTab('chan-push')"><a>PUSH</a></li>
				<li @click="fnMoveSubTab('chan-kakao')"><a>카카오톡</a></li>
				<li @click="fnMoveSubTab('chan-mo')"><a>MO</a></li>
			</ul>
			
			<ul class="mt30 tab_s5">
				<li @click="fnMoveRcsTab('brand')"><a>브랜드 관리정보</a></li>
				<li class="active" @click="fnMoveRcsTab('message')"><a>메시지 포맷정보</a></li>
			</ul>

      <div class="Dashboard01 border-line">
				<p>전체: <span class="color1"><strong>20</strong></span>건</p>
				<div class="mt20">	
					<!-- 본문 -->	
					<div class="row row-no-margin of_h">
						<!-- 메세지 포멧 -->
						<div v-for="(item, i) in msgFormData" :key="i" class="col-xs-6 bgColor_f1" style="width:48%; padding:30px; margin:12px;">
							<div class="row">
								<div class="col-xs-6">
									<img :src="item.img" class="img-responsive inline-block">
								</div>	
								<div class="col-xs-6">
									<ul class="inline-block vertical-top ml20">
										<li class="mt8">포맷 ID</li>
										<li class="mt8">상품명</li>
										<li class="mt8">상품코드</li>
										<li class="mt8">메시지타입</li>
										<li class="mt8">카드장수</li>
										<li class="mt8">제목글자수</li>
										<li class="mt8">본문글자수</li>
										<li class="mt8">버튼개수</li>
										<li class="mt8">이미지(px)</li>
										<li class="mt8">이미지용량</li>
									</ul>
									<ul class="inline-block vertical-top ml20">
										<li class="mt8">{{item.formatId}}</li>
										<li class="mt8">{{item.productName}}</li>
										<li class="mt8">SMS</li>
										<li class="mt8">Standalone</li>
										<li class="mt8">1</li>
										<li class="mt8">0</li>
										<li class="mt8">100</li>
										<li class="mt8">1</li>
									</ul>
								</div>
							</div>						
						</div>
						<!-- 메세지 포멧 -->
					</div>
					<!-- 본문 -->	
				</div>

				<!-- <div class="text-center mt50"><a href="#self" class="btnStyle2 width180">더보기</a></div> -->


			</div>

			<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
		</article>
	</div>
</template>


<script>
import Api from '../service/api'

export default {
  components: {
  },
  data() {
    return {
      // 프로젝트 정보
      projectId : '',
      msgFormData : [
		{
			"formatId" : 1,
			"productName" : "123",
			"img" : "/public/se2/images/03_5_1_4_imgage03.svg"
		},
		{
			"formatId" : 2,
			"productName" : "222"
		},
		{
			"formatId" : 3,
			"productName" : "333"
		}
	  ]
    }
  },
  mounted() {
    this.projectId = this.$route.params.projectId;
    this.fnSearch();
  },
  methods: {
    fnMoveMainTab(moveTabName){
      this.$router.push( {name:moveTabName, params:{"projectId" : this.projectId, "projectName" : this.projectName }} );
    },
    fnMoveSubTab(moveTabName){
      this.$router.push( {name:moveTabName, params:{"projectId" : this.projectId, "projectName" : this.projectName }} );
    },
    fnMoveRcsTab(moveTabName){
      var pageName = 'chan-rcs';
      if( moveTabName === 'brand' ){
        pageName = 'chan-rcs';
      } else if( moveTabName === 'message' ){
        pageName = 'chan-rcs-message';
      }
      this.$router.push( {name : pageName, params : {"projectId" : this.projectId, "projectName" : this.projectName }} );
    },
    // 검색
    fnSearch() {
      var vm = this;
      var params = {
        "projectId"     : this.projectId,
      }
       
      /* Api.selectRcsBrandList(params).then(response =>{
        vm.items = response.data.data;
      }); */
    }
  }
}
</script>