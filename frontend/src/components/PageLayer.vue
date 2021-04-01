<template>
    
    <span>
        <!-- <ul>
        <li @click="fnAllDecrease()">맨 앞으로</li>
        <li @click="fnOneDecrease()">한칸 앞으로 </li>
        <li v-for="(pageCntList, index) in pageCntList()" @click="fnPageClick(pageCntList)">{{pageCntList}}</li>
        <li @click="fnOneIncrease()">한칸 뒤로</li>
        <li @click="fnAllIncrease()">맨 뒤로</li>
        </ul> -->
        <div class="row mt40">
            <div class="col-xs-12">
                <div class="pagination1 text-center">
                    <a @click="fnAllDecrease()"><i class="far fa-chevron-double-left"></i></a>
                    <a @click="fnOneDecrease()"><i class="far fa-chevron-left"></i></a>
                    <!-- <a v-for="(pageCntList, index) in pageCntList()" @click="fnPageClick(pageCntList)" :key="index" class="number active">{{pageCntList}}</a> -->
                    <li v-for="(pageCntList, index) in pageCntList()" @click="fnPageClick(pageCntList)" :key="index"><a @click="fnPageClick(pageCntList)">{{pageCntList}}</a></li>
                    <a @click="fnOneIncrease()"><i class="far fa-chevron-right"></i></a>
                    <a @click="fnAllIncrease()"><i class="far fa-chevron-double-right"></i></a>
                </div>
            </div>
        </div>
    </span>
    
			


</template>

<script>
export default {
  name: 'SelectLayer',
  props: {
      listTotalCnt: Number,         // ToTal List Count
      selected: Number              // select Box Count (10개씩 보기 ... 등)
  },
   data: () => ({
        pageNum: 1,                 // 현재 페이지
        pageStartNum: 1,            // 페이징 시작 cnt (1, 11, 21 ...)
        pageTotalCnt: 10,           // 한번에 보여주는 페이징 (1,2,3,4,5,6,7,8,9,10)
        pageCntList () {
            var list = [];
            var listTotalCnt = this.listTotalCnt;
            var selected = this.selected;
            var listPageTotalCnt = Math.ceil(listTotalCnt/selected);
            for (var i=this.pageStartNum; i<this.pageTotalCnt+this.pageStartNum; i++) {
                if(i>listPageTotalCnt) break;
                list.push(i);
            }
            return list;
        }   
    }),
  methods: {
    fnPageClick(pageCntList) {
        this.pageNum = pageCntList;
        this.$emit('fnClick', this.pageNum);
    },
    // 한칸 뒤로
    fnOneIncrease() {
        var totalCount = this.listTotalCnt;     // 총 리스트 수
        var curPageNum = this.pageNum;          // 현재 페이지 위치
        var selected = this.selected;           // select Box Count
        var listPageTotalCnt = Math.ceil(totalCount/selected);    // 총 페이지 수
        var curPageMax = this.pageTotalCnt+this.pageStartNum-1;        // 현재 페이지징의 Max 값

        // 페이지 수 보다 작으면 pageNum increase 후 메소드 실행
        if(listPageTotalCnt>curPageNum) {
            if(curPageNum == curPageMax){
                // 10, 20, 30 에서 클릭시 다음 페이징으로 update
                this.pageStartNum = this.pageStartNum+this.pageTotalCnt;
                this.pageNum++;
                this.$emit('fnClick', this.pageNum);
            } else {
                this.pageNum++;
                this.$emit('fnClick', this.pageNum);
            }
        }
    },
    // 맨 뒤로
    fnAllIncrease() {
        var totalCount = this.listTotalCnt;     // 총 리스트 수
        var selected = this.selected;           // select Box Count
        var pageTotalCnt = this.pageTotalCnt;   // 한번에 출력되는 페이징 수
        var listPageTotalCnt = Math.ceil(totalCount/selected);    // 총 페이지 수

        var pageBlockCnt = Math.ceil(listPageTotalCnt/pageTotalCnt);
        pageBlockCnt--;
        pageBlockCnt=(pageBlockCnt*pageTotalCnt)+1;
        
        this.pageStartNum = pageBlockCnt;
        this.pageNum = listPageTotalCnt;
        this.$emit('fnClick', this.pageNum);
    },
    // 한칸 앞으로
    fnOneDecrease() {
        var totalCount = this.listTotalCnt;     // 총 리스트 수
        var curPageNum = this.pageNum;          // 현재 페이지 위치
        var selected = this.selected;           // select Box Count
        var pageStartNum = this.pageStartNum;   // 현재 페이지 시작 Cnt

        // 1페이지 보다 클 경우에만 메소드 실행
        if(1<curPageNum) {
            if(curPageNum == pageStartNum){
                // 11, 21, 31 에서 클릭시 전 페이징으로 update
                this.pageStartNum = this.pageStartNum-this.pageTotalCnt;
                this.pageNum++;
                this.$emit('fnClick', this.pageNum);
            } else {
                this.pageNum--;
                this.$emit('fnClick', this.pageNum);
            }
        }
    },
    // 맨 앞으로
    fnAllDecrease() {
        this.pageNum = 1;
        this.pageStartNum = 1;
        this.$emit('fnClick', this.pageNum);
    }
  }
}
</script>

<style>
    ul {
    list-style:none;
    margin:0;
    padding:0;
    }

</style>