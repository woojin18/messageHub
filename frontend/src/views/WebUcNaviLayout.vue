<template>
	<div>
		<web-header></web-header>
		<tree-menu></tree-menu>
		<article>
			<section id="content">
				<router-view :key="$route.fullPath"></router-view>
				<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
				<confirm></confirm>
			</section>
		</article>
		<!-- 로딩레이어-->
		<div class="loading_layer">
			<div class="loader"></div>
		</div>
	</div>
</template>

<script>
import Vue from 'vue';
import WebHeader from '../components/WebUcHeader';
import treeMenu from '../components/treeUcMenu';
import confirm from '@/modules/commonUtil/service/confirm';
import common from '@/assets/js/common';

Vue.use(confirm);
Vue.use(common);

export default {
	name: "WebUcNaviLayout",
	components: {
		WebHeader,
		treeMenu
	},
};
</script>