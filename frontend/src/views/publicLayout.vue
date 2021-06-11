<template>
  <div>
    <PublicHeader></PublicHeader>
    <router-view :key="$route.fullPath"></router-view>
    <PublicFooter></PublicFooter>
    <!-- 로딩레이어-->
    <div class="loading_layer">
      <div class="loader"></div>
    </div>
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
  mounted() {
    let style = document.createElement('link');
    style.id = "usercss"
    style.type = "text/css";
    style.rel = "stylesheet";
    style.href = '/se2/css/user.css';
    document.head.appendChild(style);
  },
  destroyed(){
    document.getElementById('usercss').disabled=true;
  }
};
</script>

