<template>
  <div class="of_h mb20">
    <div class="float-left">
      전체 : <span class="color1"><strong>{{pageInfo.rowNum}}</strong></span>건
      <select v-model="pageInfo.selPageCnt" @change="fnPageChange('')" class="selectStyle2 width120 ml20">
        <option v-for="item in pageInfo.pageCnt" :key="item" v-bind:value="item">{{item}}개씩 보기</option>
      </select>
    </div>
  </div>
</template>

<script>

export default {
  name: 'PagingCnt',
  props: {
    pageInfo: {
      type: Object,
      require: true
    }
  },
  methods: {
    fnPageChange: function(index) {
      if(!index || index < 1) index = 1;
      if(index > this.maxPage) index = this.maxPage;
      
      this.pageInfo.selPage = index;
      this.pageInfo.selPageCnt = this.pageInfo.selPageCnt;
      this.$parent.fnSearch();
    }
  }
}
</script>

<style lang="scss">
</style>