<template>
  <article>
    <div class="contentHeader mb20">
      <h2>정산 > 충전관리</h2>
    </div>
    <prepaidCash></prepaidCash>
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
      selTab: 1
    }
  }
}
</script>
