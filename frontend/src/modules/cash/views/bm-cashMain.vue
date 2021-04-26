<template>
  <article>
    <div class="contentHeader mb20">
      <h2>정산관리</h2>
    </div>
    <div class="contentBody mb20">
      <ul class="tab_s3">
        <li :class="{active:(selTab == 1)}" @click="selTab=1"><a title="선불정산관리 페이지로 이동">선불정산관리</a></li>
        <li :class="{active:(selTab == 2)}" @click="selTab=2"><a title="후불정산관리 페이지로 이동">후불정산관리</a></li>
      </ul>			
    </div>
    <prepaidCash v-show="selTab == 1"></prepaidCash>
    <deferredCash v-show="selTab == 2"></deferredCash>
  </article>
</template>

<script>
import prepaidCash from '../components/bc-prepaidCash.vue';
import deferredCash from '../components/bc-deferredCash.vue';

export default {
  components: {
    prepaidCash,
    deferredCash
  },
  data() {
    return {
      selTab: 2
    }
  }
}
</script>
