<template>
  <!-- head_type_user -->
  <header class="head_type_user">
    <div class="wrap_head">
      <div class="box_gnb">
        <h1 class="tit_logo"><a href="/main" class="userLogo"><img src="../../public/se2/images/userLogo.svg" alt="LGU+ 통합메시징클라우드 로고"></a></h1>
        <nav id="userGnb">
          <ul class="list_gnb" role="menu">
            <li>
              <router-link :to="{name: 'intro'}" tag="a" title="서비스 소개 페이지로 이동">서비스 소개</router-link>
            </li>
            <li>
              <router-link :to="{name: 'sendPay'}" tag="a" title="요금제 페이지로 이동">요금제</router-link>
            </li>
            <li><a href="#self" title="고객센터 페이지로 이동">고객센터</a>
              <ul class="dep2">
                <li><a href="#self" title="FAQ로 이동">FAQ</a></li>
                <li><a href="#self">공지사항</a></li>
                <li><a href="#self">자료실</a></li>
                <li><a href="#self" data-toggle="modal" data-target="#Inquiry">서비스 문의</a></li>
              </ul>
            </li>
            <li><a href="#self" title="사용자 가이드 페이지로 이동">사용자 가이드</a></li>
            <li class="console"><a href="#self" title="CONSOLE 페이지로 이동">CONSOLE</a></li>
          </ul>
        </nav>
        <ul class="list_login">
          <li class="active"><a @click.prevent="clickLoginPage">로그인</a></li>
          <li><a @click.prevent="signUp">회원가입</a></li>
        </ul>
      </div>
    </div>
  </header>
  <!-- //head_type_user -->
</template>
<script>
export default {
  methods: {
    signUp: function() {
      this.$router.push({name : "signUp"});
    },
    clickLoginPage() {
      this.$nextTick(() => this.$router.replace('/login'));
    }
  }
};
</script>