<template>
  <div>
    <signUpMain  :authKey="$route.query.authKey"/>
  </div>
</template>

<script>
import signUpMain from '../components/signUpMain.vue';

export default {
  components: {
    signUpMain
  }
}
</script>