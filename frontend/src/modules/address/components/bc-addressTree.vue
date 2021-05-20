<template>
	<ul class="addList">
		<li class="addList_minus">
			<a @click="fnAddrCatgMem(id)">
				<i class="fal fa-minus-square addIcon"></i>
				{{ item }}
			</a>
		</li>
		<addr-tree-menu
			v-for = "each in subItems"
			:key = "each.addressCategoryId"
			:item = "each.addressName"
			:id = "each.addressCategoryId"
			:subItems = "each.subItems">
		</addr-tree-menu>
	</ul>
</template>
<!--
			<li>
				<a href="#self">
					<i class="fal fa-minus-square addIcon"></i>
					관리팀
				</a>
				<ul>
					<li><a href="#self">사업관리팀</a></li>
				</ul>
			</li>
			<li class="addList_minus">
				<a href="#self">
					<i class="fal fa-minus-square addIcon"></i>
					마케팅팀
				</a>
				<ul>
					<li>
						<a href="#self" class="active">통합메시징팀</a>
					</li>
					<li class="last">
						<a href="#self">테스트팀</a>
					</li>
				</ul>
			</li>
			<li class="addList_plus">
				<a href="#self">
					<i class="fal fa-plus-square addIcon"></i>신규 사업팀
				</a>
				<ul>
					<li>
						<a href="#self">통합메시징팀</a>
					</li>
					<li>
						<a href="#self">통합메시징팀</a>
					</li>
					<li>
						<a href="#self">통합메시징팀</a>
					</li>
					<li class="last">
						<a href="#self">테스트팀</a>
					</li>
				</ul>
			</li>
-->

<script>
export default {
	name: "addrTreeMenu",
	props: [ 'item', 'id', 'subItems' ],
	methods: {
		fnAddrCatgMem(id){
			if(!this.$gfnCommonUtils.isEmpty(id)) {
				this.$parent.fnAddrCatgMem(null);
			}
			this.$parent.fnAddrCatgMem(id);
		}
	}
}
</script>