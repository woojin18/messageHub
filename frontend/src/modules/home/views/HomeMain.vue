<template>
	<div class="home">
		<br>
		<HomeMain msg="HomeMain" />
		<button><a href="/project/manage"> testtttttttttttt </a></button>
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
		logout: function () {
			alert("test");
			axios.get('/api/auth/logout').then(res => {
				alert('asdfasdfasdf');
			});
		}
	},
};
</script>