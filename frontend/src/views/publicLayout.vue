<template>
  <div id="publicLayout">
    <PublicHeader></PublicHeader>
    <router-view :key="$route.fullPath"></router-view>
    <PublicFooter></PublicFooter>
  </div>
</template>

<script>
import PublicHeader from "../components/PublicHeader";
import PublicFooter from "../components/PublicFooter";

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
  }
};
</script>

