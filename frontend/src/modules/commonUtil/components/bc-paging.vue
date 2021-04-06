<template>
 <div class="row mt40">
  <div class="col-xs-12">
    <div id="paging" class="pagination1 text-center">
      <a @click="fnPageChange(1)"><i class="far fa-chevron-double-left"></i></a>
      <a @click="fnPageChange(selPage-1)"><i class="far fa-chevron-left"></i></a>
      <a v-for="index in paging" :key="index" :class="{active:(index == selPage)}" @click="fnPageChange(index)"
          class="number">{{index}}</a>
      <a @click="fnPageChange(selPage+1)"><i class="far fa-chevron-right"></i></a>
      <a @click="fnPageChange(maxPage)"><i class="far fa-chevron-double-right"></i></a>
    </div>
  </div>
</div>
</template>

<script>

export default {
  name: 'Paging',
  props: {
    pageInfo: {
      type: Object,
      require: true
    }
  },
  data() {
    return {
      selPage: 1,               //선택한 페이지
      pageCnt: [1],             //표시할 개수 리스트
      selPageCnt: 1,            //선택한 표시 개수
      rowNum: 1,                //총개수
      paging: [1, 2, 3, 4, 5],  //페이지
      maxPage: 1                //총개수와 표시 개수로 계산한 최대 페이지
    }
  },
  watch: {
    pageInfo: function(newVal, oldVal) {
      this.selPage = this.pageInfo.selPage;
      this.pageCnt = this.pageInfo.pageCnt;
      if(this.pageInfo.selPageCnt) {
        this.selPageCnt = this.pageInfo.selPageCnt;
      } else {
        this.selPageCnt = this.pageCnt[0];
      }
      this.rowNum = this.pageInfo.rowNum;
      
      this.paging = [];
      var startPage = 1;
      var endPage = 1;
      this.maxPage = Math.ceil(this.rowNum/this.selPageCnt);

      if(this.selPage <= 3) {
        if(this.maxPage > 5) endPage = 5;
        else endPage = this.maxPage;
      } else if(this.selPage + 2 > this.maxPage) {
        if(this.maxPage < 5) startPage = 1;
        else startPage = this.maxPage - 4;
        endPage = this.maxPage;
      } else {
        startPage = this.selPage - 2;
        endPage = this.selPage +2;
      }

      for(var i = startPage; i <= endPage; i++) {
        this.paging.push(i);
      }
    }
  },
  methods: {
    fnPageChange: function(index) {
      if(!index || index < 1) index = 1;
      if(index > this.maxPage) index = this.maxPage;
      
      this.pageInfo.selPage = index;
      this.pageInfo.selPageCnt = this.selPageCnt;
      this.$parent.fnSearch();
    }
  }
}
</script>
