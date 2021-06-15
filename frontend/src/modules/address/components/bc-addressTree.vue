<template>
	<ul class="addList">
		<li class="addList_minus">
			<a @click="fnAddrCateMem(id, item)">
				<i class="fal fa-minus-square addIcon"></i>
				{{ item }}
			</a>
		</li>
		<addr-tree-menu v-for = "data in subItems" :key = "data.addressCategoryId"
			:item = "data.addressName"
			:id = "data.addressCategoryId"
			:topAddressCategoryId = "data.topAddressCategoryId"
			:subItems = "data.subItems"
		>
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
	props: [ 'item', 'id', 'subItems', 'topAddressCategoryId'],
	methods: {
		fnAddrCateMem(id, addrName, topAddressCategoryId){
			console.log('id : ' +id + ', addrName :' + addrName + ', topAddressCategoryId : ' + topAddressCategoryId);
			if(!this.$gfnCommonUtils.isEmpty(id)) {
				this.$parent.fnAddrCateMem(null, null, null);
			}
			this.$parent.fnAddrCateMem(id, addrName, topAddressCategoryId);
		}
	}
}
</script>