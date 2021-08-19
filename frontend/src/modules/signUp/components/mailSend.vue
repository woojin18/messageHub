<template>
	<div>
	<section class="userSection width540 mt70">
		<h2 class="text-center">이메일이 발송되었습니다.</h2>

		<p class="text-center mt80"><img src="../../../../public/se2/images/join_img.png" alt=""></p>
		<p class="text-center font-size14 mt30">이메일 인증을 진행해주세요.</p>
		
		<p class="text-center mt40">Google메일을 이용할 시 이메일 인증에는 문제가 없으나,</p>
		<p class="text-center"> 구글의 보안프록시 서버를 사용하여 이미지를 제공하기 때문에 본문 상 이미지가 보이지 않을 수 있습니다.</p>

		<div class="text-center mt70"><a href="user_join3.html" @click.prevent="joinHome" class="btn btn-login maxWidth260">홈으로 이동</a></div>
	</section>
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