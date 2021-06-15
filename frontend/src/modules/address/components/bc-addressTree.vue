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
			:subItems = "data.subItems"
		>
		</addr-tree-menu>
	</ul>
</template>

<script>
export default {
	name: "addrTreeMenu",
	props: {
		item: {
			type: String,
		},
		id: {
			type: String,
		},
		subItems: {
			type: Object,
		},
	},
	methods: {
		fnAddrCateMem(id, addrName){
			//alert('id : ' + id + ', addrName :' + addrName);
			if(!this.$gfnCommonUtils.isEmpty(id)) {
				this.$parent.fnAddrCateMem(id, addrName);
			}else if(this.$gfnCommonUtils.isEmpty(id) && !this.$gfnCommonUtils.isEmpty(addrName)) { //root 클릭
				this.$parent.fnAddrCateMem(null, addrName);
			}
		}
	}
}
</script>