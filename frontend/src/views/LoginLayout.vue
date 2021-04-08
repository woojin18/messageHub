<template>
	<div>
		<login-Header></login-Header>
		<router-view :key="$route.fullPath"></router-view>
		<login-Footer></login-Footer>
		<!-- 로딩레이어-->
		<div class="loading_layer">
			<div class="loader"></div>
		</div>
	</div>
</template>

<script>
import LoginHeader from "../components/LoginHeader";
import LoginFooter from "../components/LoginFooter";

export default {
	name: "loginLayout",
	components: {
		LoginHeader,
		LoginFooter
	},
};
</script>