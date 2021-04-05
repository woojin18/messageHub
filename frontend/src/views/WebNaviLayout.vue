<template>
  <div>
      <web-header></web-header>
      <!-- <tree-menu></tree-menu> -->
      <article>
        <section id="content">
          <router-view :key="$route.fullPath"></router-view>
          <footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
        </section>
      </article>
<!-- 로딩레이어-->
      <div class="loading_layer">
        <div class="loader"></div>
      </div>
  </div>
</template>

<script>
import WebHeader from "../components/WebHeader";
import treeMenu from "../components/treeMenu";

export default {
  name: "webNaviLayout",
  components: {
    WebHeader,
    treeMenu
  },

};
</script>