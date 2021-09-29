<template>
  <div>
    <section class="userSection width540 mt70">
      <div style="font-size:100px; color:#FFB612" class="text-center"><i class="far fa-exclamation-triangle"></i></div>
      <h3 class="consolLineheight font-size18 text-center">죄송합니다.<br>요청하신 페이지를 찾을 수 없습니다. (404 Error)</h3>
      <div class="mt40">							
        <div class="mt30">
          <p class="font-size14 color3 auto text-center">존재하지 않는 주소를 입력하셨거나 기술적인 문제로 일시적으로 접속되지 않았습니다.<br>잠시 후 다시 이용 부탁드리며 이용에 불편을 드려 사과드립니다.</p>
        </div>
      </div>
      <div class="text-center mt40"><a href="user_login.html" class="btnStyle3 white font-size16 minWidth120">이전</a><a href="user_login.html" class="btnStyle3 black font-size16 minWidth120 ml20">메인</a></div>
    </section>
  </div>
</template>

<script>
  export default {
    data: function() {
      return {
        
      };
    },
    created() {
      this.$store.commit("login/isErrorPage", true);
    },
    mounted() {
      
    },
    destroyed() {
      this.$store.commit("login/isErrorPage", false);
    },
    methods: {
      backGo() {
        this.$router.go(-1);
      }
    }
  };
</script>
