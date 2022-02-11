<template>
	<div>
		<web-header></web-header>
		<tree-menu></tree-menu>
		<section id="content">
			<article>			
				<router-view :key="$route.fullPath"></router-view>
				<footer>Copyright©LG Uplus Corp. All Rights Reserved.</footer>
				<confirm></confirm>
			</article>
		</section>
		<!-- 로딩레이어-->
		<div class="loading_layer">
			<div class="loader"></div>
		</div>
		<div class="loading hidden">
			<div class='uil-ring-css' style='transform:scale(0.79);'>
				<div></div>
			</div>
		</div>
	</div>
</template>

<script>
import Vue from 'vue';
import WebHeader from '../components/WebUcHeader';
import treeMenu from '../components/treeUcMenu';
import confirm from '@/modules/commonUtil/service/confirm';

Vue.use(confirm);

export default {
	name: "WebUcNaviLayout",
	components: {
		WebHeader,
		treeMenu
	},
};
</script>