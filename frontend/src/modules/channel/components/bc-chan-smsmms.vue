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
				<li @click="fnMoveSubTab('chan-rcs')"><a>RCS</a></li>
				<li @click="fnMoveSubTab('chan-smsmms')" class="active"><a>SMS/MMS</a></li>
				<li @click="fnMoveSubTab('chan-push')"><a>PUSH</a></li>
				<li @click="fnMoveSubTab('chan-kakao')"><a>카카오톡</a></li>
				<li @click="fnMoveSubTab('chan-mo')"><a>MO</a></li>
			</ul>
			
      <h4 class="mt40">중복메세지 차단</h4>
			<p class="font-size12 color3 mt30 inline-block line-height1-5em">
        <i class="far fa-info-circle"></i> 중복된 메세지는 발송하지 않도록 구성되어 있습니다.<br>
				<i class="far fa-info-circle"></i> 중복차단 시간 동안 동일한 요청에 대한 발송 건은 실패 처리됩니다.<br>
				<i class="far fa-info-circle"></i> 중복 판단 기준은 다음과 같습니다.<br>
				<i class="far fa-info-circle"></i>    - 메세지타입(SMS/LMS/MMS), 발송타입(일반/대량), 발신번호, 수신번호, 제목, 본문<br>
			</p>
			<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
		</article>
	</div>
</template>


<script>
export default {
  components: {
  },
  data() {
    return {
      // 프로젝트 정보
      projectId : '',
      projectName : '',
    }
  },
  mounted() {
    var vm = this;
    this.projectId = this.$route.params.projectId;
    this.projectName = this.$route.params.projectName;
  },
  methods: {
    fnMoveMainTab(moveTabName){
      this.$router.push( {name:moveTabName, params:{"projectId" : this.projectId, "projectName" : this.projectName }} );
    },
    fnMoveSubTab(moveTabName){
      this.$router.push( {name:moveTabName, params:{"projectId" : this.projectId, "projectName" : this.projectName }} );
    }
  }
}
</script>