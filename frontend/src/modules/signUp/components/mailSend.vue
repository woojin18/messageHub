<template>
	<div>
	<section class="userSection width540 mt70">
		<h2 class="text-center">이메일이 발송되었습니다.</h2>

		<p class="text-center mt80"><img src="../../../../public/se2/images/join_img.png" alt=""></p>
		<p class="text-center font-size14 mt30">회원가입을 완료하기 위해 이메일 인증을 진행해주세요.</p>

		<div class="text-center mt70"><a href="user_join3.html" @click.prevent="joinHome" class="btn btn-login maxWidth260">홈으로 이동</a></div>
	</section>
	<confirm></confirm>
	</div>
</template>

<script>
import confirm from "@/modules/commonUtil/service/confirm";

export default {
  methods: {
	  joinHome() {
          this.$router.push({name : "login"});
      }
  }
}
</script>