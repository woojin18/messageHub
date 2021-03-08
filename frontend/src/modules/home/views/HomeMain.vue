<template>
  <div class="home">
    <br>
    <HomeMain msg="HomeMain" />
    <button v-on:click="createdTest">TEST</button>
    <br>

  </div>
</template>

<script>
  // @ is an alias to /src
  import tokenSvc from '@/common/token-service';
  import HomeMain from '../components/HomeMain.vue';
  import homeApi from "@/modules/home/service/api"
  import loginApi from "@/modules/login/service/api"
  import axios from 'axios';

  export default {
    components: {
      HomeMain
    },
    created: function() {
      console.log('created HomeMain');
      console.log('token:', tokenSvc.getToken());
    },
    methods: {
      createdTest: function () {
        alert("test");
        axios.get('/api/auth/logoutTest').then(res => {
          alert('asdfasdfasdf');
        });
      }

    },
  };
</script>
