<template>
  <div>
    <checkPlusFail :EncodeData="$route.query.EncodeData"/>
  </div>
</template>

<script>
import checkPlusFail from '../components/bc-checkPlus-fail.vue';

export default {
  components: {
    checkPlusFail
  }
}
</script>