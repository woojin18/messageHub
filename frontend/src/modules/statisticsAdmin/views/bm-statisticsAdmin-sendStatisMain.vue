<template>
	<div >
		<article>
			<div class="contentHeader">
				<h2>발송일별통계</h2>
			</div>
			<!-- 본문 -->
			<div class="contentBody">
				<ul class="tab_s6" style="position: relative; top: -2px;">
					<li :class="{active:(selMainTab == 1)}" @click="selMainTab=1; selMidTab=1; selSubTab==1"><a>Push</a></li>
					<li :class="{active:(selMainTab == 2)}" @click="selMainTab=2; selMidTab=1; selSubTab==1"><a>RCS</a></li>
					<li :class="{active:(selMainTab == 3)}" @click="selMainTab=3; selMidTab=1; selSubTab==1"><a>카카오톡</a></li>
					<li :class="{active:(selMainTab == 4)}" @click="selMainTab=4; selMidTab=1; selSubTab==1"><a>SMS/MMS</a></li>
				</ul>
			</div>
			<!-- Push 탭 -->
			<push v-if="selMainTab==1"></push>
			<!-- RCS 탭 -->
			<rcs v-if="selMainTab==2"></rcs>
			<!-- 카카오톡 탭 -->
			<kakaotalk v-if="selMainTab==3"></kakaotalk>
			<!-- SMS/MMS 탭 -->
			<smsmms v-if="selMainTab==4"></smsmms>
		</article>
	</div>
</template>


<script>
import push from "@/modules/statisticsAdmin/components/bc-push-main.vue";
import rcs from "@/modules/statisticsAdmin/components/bc-rcs-main.vue";
import kakaotalk from "@/modules/statisticsAdmin/components/bc-kakaotalk-main.vue";
import smsmms from "@/modules/statisticsAdmin/components/bc-smsmms-main.vue";

export default {
	components: {
		push,
		rcs,
		kakaotalk,
		smsmms,
	},
	data() {
		return {
			selMainTab	: 1,
			selMidTab	: 1,
			selSubTab	: 1,
		}
	},
	mounted() {
		// 
	},
	methods: {
	}
}
</script>