<template>
  <!-- head_type_user -->
  <header class="head_type_user">
    <div class="wrap_head">
      <div class="box_gnb">
        <h1 class="tit_logo"><a href="/public/main" class="userLogo"><img src="../../public/se2/images/userLogo.svg" alt="LGU+ 통합메시징클라우드 로고"></a></h1>
        <nav id="userGnb">
          <ul class="list_gnb" role="menu">
            <li>
              <router-link :to="{name: 'intro'}" tag="a" title="서비스 소개 페이지로 이동">서비스 소개</router-link>
            </li>
            <li>
              <router-link :to="{name: 'sendPay'}" tag="a" title="요금제 페이지로 이동">요금제</router-link>
            </li>
            <li><a title="고객센터 페이지로 이동">고객센터</a>
              <ul class="dep2">
                <li>
                  <router-link :to="{ name: 'faq' }" tag="a" title="FAQ로 이동">FAQ</router-link>
                </li>
                <li>
                  <router-link :to="{ name: 'notice' }" tag="a" title="공지사항으로 이동">공지사항</router-link>
                </li>
                <li>
                  <router-link :to="{ name: 'library' }" tag="a" title="자료실로 이동">자료실</router-link>
                </li>
                <li><a href="#" @click.prevent="fnOpenInquiryModal">서비스 문의</a></li>
              </ul>
            </li>
            <li><a @click="fnGoUserGuide" title="사용자 가이드 페이지로 이동">사용자 가이드</a></li>
            <li class="console"><router-link to="/ac/home" title="CONSOLE 페이지로 이동">CONSOLE</router-link></li>
          </ul>
        </nav>
        <ul class="list_login">
          <li v-if="isLogin == false" class="active"><router-link :to="{name: 'login'}" tag="a" title="로그인 페이지로 이동">로그인</router-link></li>
          <li v-if="isLogin == true" class="active"><a href="#" @click.prevent="clickLogout">로그아웃</a></li>
          <li v-if="isLogin == false"><a href="#" @click.prevent="signUp">회원가입</a></li>
        </ul>
      </div>
    </div>
    <InquiryPopup ref="inquiryPopup"></InquiryPopup>
  </header>
  <!-- //head_type_user -->
</template>

<script>
import InquiryPopup from "@/modules/customer/components/bp-inquiry.vue";

import loginApi from '@/modules/login/service/api';
import customerApi from '@/modules/customer/service/customerApi';
import tokenSvc from '@/common/token-service';

export default {
  name: "PublicHeader",
  components : {
    InquiryPopup
  },
  data() {
    return {
      isLogin: false,
      userGuideUrl : "www.naver.com"
    }
  },
  created() {
    this.isLogin = !!tokenSvc.getToken();
    let curPage = location.pathname;
    if (this.isLogin && curPage != "/login") {
      this.$store.commit("login/isLogin", true);
    } else {
      this.$store.commit("login/isLogin", false);
      this.isLogin = false;
    }
    this.fnInit()
  },
  methods: {
    fnInit() {
      customerApi.selectUserGuideUrl({}).then(response => {
        if (response.data.success) {
          this.userGuideUrl = response.data.data
        }
      });
    },
    fnGoUserGuide(){
      window.open(this.userGuideUrl)
    },
    fnOpenInquiryModal(){
      this.$refs.inquiryPopup.fnRestData();
      jQuery("#Inquiry").modal("show");
      this.$refs.inquiryPopup.fnSelectFaqType();
    },
    signUp: function() {
      this.$router.push({name : "signUp"});
    },
    clickLoginPage() {
      this.$nextTick(() => this.$router.replace('/login'));
    },
    clickLogout() {
      let result = confirm("로그아웃 하시겠습니까?");
      if (result) {
        loginApi.logout().then(response => {
          if (response.data.success) {
            this.$router.go(this.$router.currentRoute);
          }
        });
      }
    },
  }
};
</script>
