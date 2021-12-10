<template>
	<div >
		<article>
			<div class="contentHeader mb20">
				<h2>정산 > 이용금액</h2>
			</div>
			<!-- 본문 -->
			<div class="contentBody">
				<ul class="tab_s3">
					<li :class="{active:(selMainTab == 1)}" @click="selMainTab=1"><a>이용금액</a></li>
					<li :class="{active:(selMainTab == 2)}" @click="selMainTab=2"><a>서비스요금</a></li>
				</ul>
			</div>
			<!-- 이용내역 탭 -->
			<useHistory v-if="selMainTab==1"></useHistory>
			<!-- 서비스요금 탭 -->
			<serviceCharge v-if="selMainTab==2"></serviceCharge>
		</article>
	</div>
</template>


<script>
import useHistory from "@/modules/use/components/bc-use-history.vue";
import serviceCharge from "@/modules/use/components/bc-use-serviceCharge.vue";
export default {
	components: {
		useHistory,
		serviceCharge
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