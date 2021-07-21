<template>
	<div >
		<article>
			<div class="contentHeader mb20">
				<h2>{{this.projectName}}</h2>
			</div>

			<!-- 본문 -->
			<div class="contentBody">
				<ul class="tab_s3">
					<li :class="{active:(selMainTab == 1)}" @click="selMainTab=1; selMidTab=1; selSubTab==1"><a>대시보드</a></li>
					<li :class="{active:(selMainTab == 2)}" @click="selMainTab=2; selMidTab=1; selSubTab==1"><a>기본정보</a></li>
					<li :class="{active:(selMainTab == 3)}" @click="selMainTab=3; selMidTab=1; selSubTab==1"><a>멤버관리</a></li>
					<li :class="{active:(selMainTab == 4)}" @click="selMainTab=4; selMidTab=1; selSubTab==1"><a>채널관리</a></li>
					<li :class="{active:(selMainTab == 5)}" @click="selMainTab=5; selMidTab=1; selSubTab==1"><a>발신번호관리</a></li>
				</ul>			
			</div>
			<ul v-if="selMainTab==4" class="tabStyle tab6 bgColor_tapGray">
				<li :class="{active:(selMidTab == 1)}" @click="selMidTab=1; selSubTab=1" v-if="this.rcsYn ==='Y'"><a>RCS</a></li>

				<li :class="{active:(selMidTab == 3)}" @click="selMidTab=3; selSubTab=1" v-if="this.pushYn ==='Y'"><a>PUSH</a></li>
				<li :class="{active:(selMidTab == 4)}" @click="selMidTab=4; selSubTab=1" v-if="this.kakaoYn ==='Y'"><a>카카오톡</a></li>
				<li :class="{active:(selMidTab == 5)}" @click="selMidTab=5; selSubTab=1" v-if="this.moYn ==='Y'"><a>MO</a></li>
			</ul>

			<ul v-if="selMainTab==4&&selMidTab==1" class="tab_s6 mt10">
				<!-- 체널관리 - RCS 탭 -->
				<li :class="{active:(selMainTab==4&&selMidTab==1&&selSubTab==1)}" @click="selSubTab=1"><a>브랜드 관리정보</a></li>
				<li :class="{active:(selMainTab==4&&selMidTab==1&&selSubTab==2)}" @click="selSubTab=2"><a>메시지 포맷정보</a></li>
				<!-- 체널관리 - RCS 탭 -->
			</ul>

			<ul v-if="selMainTab==4&&selMidTab==4" class="tab_s6 mt10">
				<!-- 체널관리 - 카카오톡 탭 -->
				<li :class="{active:(selMainTab==4&&selMidTab==4&&selSubTab==1)}" @click="selSubTab=1"><a>발신 프로필 일반</a></li>
				<!-- <li :class="{active:(selMainTab==4&&selMidTab==4&&selSubTab==2)}" @click="selSubTab=2"><a>발신 프로필 그룹</a></li> -->
				<!-- 체널관리 - 카카오톡 탭 -->
			</ul>

			<!-- 대시보드 탭 -->
			<dashboardManage v-if="selMainTab==1"></dashboardManage>
			<!-- 대시보드 탭 -->

			<!-- 기본정보 탭 -->
			<baseInfoManage v-if="selMainTab==2"></baseInfoManage>
			<!-- 기본정보 탭 -->

			<!-- 멤버관리 탭 -->
			<memberManage v-if="selMainTab==3"></memberManage>
			<!-- 멤버관리 탭 -->

			<!-- 체널관리 탭 -->
			<rcs        v-if="selMainTab==4&&selMidTab==1&&selSubTab==1"></rcs>
			<rcsMassage v-if="selMainTab==4&&selMidTab==1&&selSubTab==2"></rcsMassage>
			<smsmms     v-if="selMainTab==4&&selMidTab==2"></smsmms>
			<push       v-if="selMainTab==4&&selMidTab==3"></push>
			<kakao      v-if="selMainTab==4&&selMidTab==4"></kakao>
			<mo         v-if="selMainTab==4&&selMidTab==5"></mo>
			<!-- 체널관리 탭 -->

			<!-- 발신번호관리 탭 -->
			<!-- <div v-if="selMainTab==5">
			<h4 class="mt40">발신번호 사전 등록</h4>
			<p class="font-size12 color3 mt30 inline-block"><i class="far fa-info-circle"></i> 발신번호 미등록 시 메시지가 정상적으로 전송되지 않을 수 있습니다.<br><i class="far fa-info-circle"></i> 발신번호는 문자발송장비 사용자의 최대 3배까지만 등록할 수 있습니다.</p>

			<ul class="tabStyle tab6 bgColor_tapGray mt30">
				<li @click="fnMoveMainTab('callbackPreReg')" class="active"><a style="width:23%">발신번호 인증 등록</a></li>
				<li @click="fnMoveMainTab('callbackPreRegEx')"><a style="width:23%">사전 등록 예외 대상 사업자로 등록</a></li>
			</ul>
			</div> -->
			<callbackManage v-if="selMainTab==5"></callbackManage>
			<!-- 발신번호관리 탭 -->
		</article>
	</div>
</template>


<script>
// 체널관리
import rcs    from "@/modules/channel/components/bc-chan-rsc.vue"; 
import rcsMassage   from "@/modules/channel/components/bc-chan-rsc-message.vue"; 
import smsmms from "@/modules/channel/components/bc-chan-smsmms.vue";
import push   from "@/modules/channel/components/bc-chan-push.vue";
import kakao  from "@/modules/channel/components/bc-chan-kakao.vue";
import mo     from "@/modules/channel/components/bc-chan-mo.vue";
// 대시보드
import dashboardManage     from "@/modules/project/components/bc-project-dashboard-manage.vue";
// 발신번호관리
import callbackManage     from "@/modules/project/components/bc-project-callback-manage.vue";
// 기본정보
import baseInfoManage     from "@/modules/project/components/bc-project-baseInfo-manage.vue";
// 멤버관리
import memberManage     from "@/modules/project/components/bc-project-member-manage.vue";

export default {
  components: {
      rcs
    , rcsMassage
    , smsmms
    , push
    , kakao
    , mo

    , callbackManage
    , dashboardManage
    , baseInfoManage
	, memberManage
  },
  data() {
    return {
      projectId   : this.$route.params.projectId,
      projectName : this.$route.params.projectName,
      rcsYn : this.$route.params.rcsYn,
      smsmmsYn : this.$route.params.smsmmsYn,
      pushYn : this.$route.params.pushYn,
      kakaoYn : this.$route.params.kakaoYn,
      moYn : this.$route.params.moYn,
      selMainTab  : 1,
      selMidTab   : 1,
      selSubTab   : 1
    }
  },
  mounted() {
    console.log(this.$route.params.selMainTab);
    if( this.$route.params.selMainTab != null || this.$route.params.selMainTab != undefined ){
      this.selMainTab = this.$route.params.selMainTab;
    }
    if( this.$route.params.selMidTab != null || this.$route.params.selMidTab != undefined ){
      this.selMidTab = this.$route.params.selMidTab;
    }
    // 내부에서 새로그침시 프로젝트 화면으로 이동하게 처리
    if( this.projectId === undefined || this.projectId === null ){
      this.$router.push( {name:'projectManage', params:{}} );
    }
  },
  methods: {
  }
}
</script>