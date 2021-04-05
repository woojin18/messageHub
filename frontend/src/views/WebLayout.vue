<template>
  <div class="loginBg1">
      <div class="container">
        <router-view :key="$route.fullPath"></router-view>
      </div>
<!-- 로딩레이어-->
      <div class="loading_layer">
        <div class="loader"></div>
      </div>
  </div>
</template>

<script>
import "../common/ko.js";

export default {
  name: "webSampleLayout",
  components: {
  }

};
</script>