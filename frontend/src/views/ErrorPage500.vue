<template>

  <article id="content" class="content error_page">
    <div class="error_wrap center">
      <div class="title_area rcs_icon icon_error500">
        <h2 class="h2_title">죄송합니다.<br>서비스가 일시적으로 중단되었습니다. (500 Error)</h2><span class="h2_desc mar_t20">일시적으로 페이지 접근이 불가능합니다.<br>잠시 후 다시 이용 부탁드리며 이용에 불편을 드려 사과드립니다.</span>
      </div>
      <div class="btn_wrap center mar_t60"><a href="javascript:void(0);" class="btn big cd_black" @click="backGo"><span>이전</span></a><a href="/" class="btn big point"><span>메인</span></a></div>
    </div>
  </article>

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