<template>
  <ul>

    <!--<li class="page-item" v-if="prev">
      <router-link :to="`/list?no=${ (startPageIndex - 1) * listRowCount }`" class="page-link" aria-label="Previous" @click.native="movePage(startPageIndex - 1)">
        <span aria-hidden="true">&laquo;</span>
      </router-link>
    </li>

    <li v-for="index in endPageIndex-startPageIndex + 1 " :key="index" class="page-item" :class="{active:( (startPageIndex + index - 1) == currentPageIndex)}">
      <router-link :to="`/list?no=${ (startPageIndex + index - 1) * listRowCount }`" class="page-link"  @click.native="movePage(startPageIndex + index - 1)">{{ startPageIndex + index - 1 }}</router-link>
    </li>

    <li class="page-item" v-if="next">
      <router-link :to="`/list?no=${ (endPageIndex + 1) * listRowCount }`" class="page-link" aria-label="Next"  @click.native="movePage(endPageIndex + 1)">
        <span aria-hidden="true">&raquo;</span>
      </router-link>
    </li>-->

    <li>
      <a href="javascript:void(0);" class="btn big point on" @click.prevent="fnSearch"><span>검색</span></a>
    </li>
  </ul>
</template>

<script>
//import http from "@/util/http-common";

export default {
  name: "row",
  props: ['totalCount']
  ,
  data() {
    return {
      totalListItemCount: 0,
      listRowCount: 10,
      pageLinkCount: 10,
      currentPageIndex: 1,

      pageCount: 0,
      startPageIndex: 0,
      endPageIndex: 0,
      prev: false,
      next: false

    };
  },
  methods: {
    movePage( param ) {
      this.currentPageIndex = param;
      this.initComponent();
    },

    initComponent(){
      /*http
        .get("/board/pagelink/count")
        .then(({ data }) => {
          this.totalListItemCount = data;
          this.initUI();
        })
        .catch(() => {
          alert("에러가 발생했습니다.");
        });
        */
      
      
    },
    initUI(){

      this.pageCount = Math.ceil(this.totalListItemCount/this.listRowCount);

      if( (this.currentPageIndex % this.pageLinkCount) == 0 ){
        this.startPageIndex = ((this.currentPageIndex / this.pageLinkCount)-1)*this.pageLinkCount + 1
      }else{
        this.startPageIndex = Math.floor(this.currentPageIndex / this.pageLinkCount)*this.pageLinkCount + 1
      }

      if( (this.currentPageIndex % this.pageLinkCount) == 0 ){ //10, 20...맨마지막
        this.endPageIndex = ((this.currentPageIndex / this.pageLinkCount)-1)*this.pageLinkCount + this.pageLinkCount
      }else{
        this.endPageIndex =  Math.floor(this.currentPageIndex / this.pageLinkCount)*this.pageLinkCount + this.pageLinkCount;
      }

      if(this.endPageIndex > this.pageCount){
        this.endPageIndex = this.pageCount
      }

      if( this.currentPageIndex <= this.pageLinkCount ){
        this.prev = false;
      }else{
        this.prev = true;
      }

      if(this.endPageIndex >= this.pageCount){
        this.endPageIndex = this.pageCount;
        this.next = false;
      }else{
        this.next = true;
      }
    }
  },
  /*watch:{
    currentPageIndex: function(){
      this.initUI();
    }
  },*/
  created() {
    //this.initComponent();
    console.log("asdfasdfasdf" + this.totalCount);
    alert("child");
    alert("child alert" + this.totalCount);
  },
  mounted(){
    
  }
};
</script>