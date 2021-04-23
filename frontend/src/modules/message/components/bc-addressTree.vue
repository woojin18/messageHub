<template>

  <ul class="addList2">

    <li class="addList_plus">
      <a @click="fnAddrCatgMem(id)">
        <i class="fal fa-plus-square addIcon"></i>
        <i class="fad fa-folder-open color3"></i>
        {{item}}
      </a>
    </li>

    <addr-tree-menu
      v-for = "each in subItems"
      :key = "each"
      :item = "each.addressName"
      :id = "each.addressCategoryId"
      :subItems = "each.subItems"
    ></addr-tree-menu>

  </ul>

</template>

<script>
export default {
  name: "addrTreeMenu",
  props: [ 'item', 'id', 'subItems' ],
  methods: {
    fnAddrCatgMem(id){
      if(!this.$gfnCommonUtils.isEmpty(id)){
        this.$parent.fnAddrCatgMem(null);
      }
      this.$parent.fnAddrCatgMem(id);
    }
  }
}
</script>


<style>
/* TODO - CSS 파일에 넣자 */
.addList_plus ~ ul {
	display: none;
}
.addList_minus ~ ul {

}
</style>