<template>
  <article>
    <div class="contentHeader mb20">
      <h2>정산관리</h2>
    </div>
    <div class="contentBody mb20">
      <ul class="tab_s3">
        <li class="active"><a class="width120" title="선불정산관리 페이지로 이동">선불정산관리</a></li>
        <li><a class="width120" title="후불정산관리 페이지로 이동">후불정산관리</a></li>
      </ul>			
    </div>
    <prepaidCash></prepaidCash>
    
  </article>
</template>

<script>
import prepaidCash from '../components/bc-prepaidCash.vue';

export default {
  components: {
    prepaidCash
  },
  data() {
    return {
    }
  }
}
</script>
