<template>
  <div>
    <checkPlusSuccess :EncodeData="$route.query.EncodeData"/>
  </div>
</template>

<script>
import checkPlusSuccess from '../components/bc-checkPlus-success.vue';

export default {
  components: {
    checkPlusSuccess
  }
}
</script>