<template>
	<div >
		<article>
			<div class="contentHeader mb20">
				<h2>이용현황 > 이용현황</h2>
			</div>
			<!-- 본문 -->
			<div class="contentBody">
				<ul class="tab_s3">
					<li :class="{active:(selMainTab == 1)}" @click="selMainTab=1"><a>이용상세</a></li>
					<li :class="{active:(selMainTab == 2)}" @click="selMainTab=2"><a>이용현황</a></li>
				</ul>
			</div>
			<!-- 이용내역 탭 -->
			<useDetail v-if="selMainTab==1"></useDetail>
			<!-- 서비스요금 탭 -->
			<useStatus v-if="selMainTab==2"></useStatus>
		</article>
	</div>
</template>


<script>
import useDetail from "@/modules/use/components/bc-use-detail.vue";
import useStatus from "@/modules/use/components/bc-use-status.vue";
export default {
	components: {
		useDetail,
		useStatus
	},
	data() {
		return {
			selMainTab	: 1
		}
	},
	mounted() {
	},
	methods: {
	}
}
</script>