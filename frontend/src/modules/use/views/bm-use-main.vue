<template>
	<div >
		<article>
			<div class="contentHeader mb20">
				<h2>이용현황 > 이용현황</h2>
			</div>
			<!-- 본문 -->
			<div class="contentBody">
				<ul class="tab_s3">
					<li :class="{active:(selMainTab == 1)}" @click="selMainTab=1; selMidTab=1; selSubTab==1"><a>이용내역</a></li>
					<li :class="{active:(selMainTab == 2)}" @click="selMainTab=2; selMidTab=1; selSubTab==1"><a>이용현황</a></li>
					<li :class="{active:(selMainTab == 3)}" @click="selMainTab=3; selMidTab=1; selSubTab==1"><a>서비스요금</a></li>
				</ul>
			</div>
			<!-- 이용내역 탭 -->
			<useHistory v-if="selMainTab==1"></useHistory>
			<!-- 이용현황 탭 -->
			<useStatus v-if="selMainTab==2"></useStatus>
			<!-- 서비스요금 탭 -->
			<serviceCharge v-if="selMainTab==3"></serviceCharge>
		</article>
	</div>
</template>


<script>
import useHistory from "@/modules/use/components/bc-use-history.vue";
import useStatus from "@/modules/use/components/bc-use-status.vue";
import serviceCharge from "@/modules/use/components/bc-use-serviceCharge.vue";
export default {
	components: {
		useHistory,
		serviceCharge,
		useStatus,
	},
	data() {
		return {
			// projectId   : this.$route.params.projectId,
			// projectName : this.$route.params.projectName,
			selMainTab	: 1,
			selMidTab	: 1,
			selSubTab	: 1,
		}
	},
	mounted() {
		/*
		console.log(this.$route.params.selMainTab);
		if( this.$route.params.selMainTab != null || this.$route.params.selMainTab != undefined ) {
			this.selMainTab = this.$route.params.selMainTab;
		}
		if( this.$route.params.selMidTab != null || this.$route.params.selMidTab != undefined ) {
			this.selMidTab = this.$route.params.selMidTab;
		}
		*/
		// 내부에서 새로그침시 이용내역 화면으로 이동하게 처리 유지할지 고민중
		/*if( this.projectId === undefined || this.projectId === null ) {
			this.$router.push( {name:'useHistory', params:{}} );
		}*/
	},
	methods: {
	}
}
</script>