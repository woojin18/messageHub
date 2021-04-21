<template>
	<div>
		<article>
		<h4 class="mt40">중복메세지 차단</h4>
			<p class="font-size12 color3 mt30 inline-block line-height1-5em">
				<i class="far fa-info-circle"></i> 중복된 메세지는 발송하지 않도록 구성되어 있습니다.<br>
				<i class="far fa-info-circle"></i> 중복차단 시간 동안 동일한 요청에 대한 발송 건은 실패 처리됩니다.<br>
				<i class="far fa-info-circle"></i> 중복 판단 기준은 다음과 같습니다.<br>
				<i class="far fa-info-circle"></i>    - 메세지타입(SMS/LMS/MMS), 발송타입(일반/대량), 발신번호, 수신번호, 제목, 본문<br>
			</p>
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
    this.projectId = this.$route.params.projectId;
    this.projectName = this.$route.params.projectName;
  }
}
</script>