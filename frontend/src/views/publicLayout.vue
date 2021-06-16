<template>
  <div id="publicLayout">
    <PublicHeader></PublicHeader>
    <router-view :key="$route.fullPath"></router-view>
    <PublicFooter></PublicFooter>
    <confirm></confirm>
  </div>
</template>

<script>
import Vue from 'vue';
import PublicHeader from "../components/PublicHeader";
import PublicFooter from "../components/PublicFooter";
import confirm from '@/modules/commonUtil/service/confirm';

Vue.use(confirm);

export default {
  name: "publicLayout",
  components: {
    PublicHeader,
    PublicFooter
  },
  created(){

  },
  mounted() {
    /* 
    let style = document.createElement('link');
    style.id = "usercss"
    style.type = "text/css";
    style.rel = "stylesheet";
    style.href = '/se2/css/user.css';
    document.getElementById('publicLayout').appendChild(style);
    */

    let recaptchaScript = document.createElement('script')
    recaptchaScript.setAttribute('src', '/se2/js/user.js')
    document.getElementById('publicLayout').appendChild(recaptchaScript);
  }
};
</script>

