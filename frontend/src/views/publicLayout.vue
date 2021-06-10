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

require('../../public/se2/css/user.css');

export default {
  name: "publicLayout",
  components: {
    PublicHeader,
    PublicFooter
  },

};
</script>