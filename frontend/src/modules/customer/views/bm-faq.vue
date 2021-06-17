
<template>
  <div>
    <!-- subVisualWrap03 -->
    <article id="subVisualWrap03">
      <section>
        <div class="text-center">
          <h2>고객센터</h2>
          <p>반갑습니다. U+ 통합 메시징 클라우드 서비스에 대한<br>최신소식 및 자료실, 궁금하신 내용 등을 찾아보실 수 있습니다.</p>
        </div>
      </section>
    </article>
    <!-- //subVisualWrap03 -->
    
    <div id="contentWrap">
      <div class="sub03_conWrap">
        <div class="con01 wow animated fadeInUp">
          <section>

            <div class="boardTitle">
              <p class="titleText">FAQ</p>
              <p class="subText">U+ 통합 메시징 클라우드를 이용하시는 회원님들의 자주 묻는 질문을 모았습니다.</p>
            </div>
            <ul class="tab5">
              <li :class="(faqTypeCd == '' ? 'active' : '')"><a href="#" @click.prevent="fnSetFaqTypeCd('')">전체</a></li>
              <li v-for="(faqTypeInfo, idx) in faqTypeList" :key="idx" :class="(faqTypeCd == faqTypeInfo.codeVal1 ? 'active' : '')">
                <a href="#" @click.prevent="fnSetFaqTypeCd(faqTypeInfo.codeVal1)">{{faqTypeInfo.codeName1}}</a>
              </li>
            </ul>
            <ul class="faqList">
              <li v-for="(contant, idx) in contants" :key="idx">
                <div class="faqTitle">
                  <span class="faqBg">Q{{contant.rownum+offset}}</span>
                  <p>{{contant.titleContent}}<span class="faqIcon">아이콘</span></p>
                </div>
                <div class="faqAn">{{contant.answerContent}}</div>
              </li>
            </ul>
            <!-- pagination -->
            <div id="pageContent">
              <PageLayer @fnClick="fnSearch" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
            </div>
            <!-- //pagination -->
          </section>
        </div>
      </div>
    </div>
    <QuickRight></QuickRight>
  </div>
</template>

<script>
import PageLayer from '@/components/PageLayer.vue';
import QuickRight from "@/modules/main/components/bc-quickRight.vue";

import customereApi from "@/modules/customer/service/customerApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: 'faq',
  components : {
    QuickRight,
    PageLayer
  },
  props: {
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return 'FAQ';
      }
    },
  },
  data() {
    return {
      faqTypeCd: '',
      faqTypeList: [],
      contants: [],
      listSize : 10,  // 한페이지 출력 리스트 수
      pageNo : 1,  // 현재 페이징 위치
      totCnt : 0,  //전체 리스트 수
      offset : 0, //페이지 시작점
    }
  },
  mounted() {
    this.fnSelectFaqType();
    this.fnSetFaqTypeCd(this.faqTypeCd);
  },
  methods: {
    fnSetFaqTypeCd(faqTypeCd){
      this.faqTypeCd = faqTypeCd;
      this.$refs.updatePaging.fnAllDecrease();
      this.fnSelectFaqList();
    },
    async fnSelectFaqList(){
      const params = {
        faqType: this.faqTypeCd,
        pageNo: this.pageNo,
        listSize:  this.listSize
      };
      await customereApi.selectFaqList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          this.contants = result.data;
          this.totCnt = result.pageInfo.totCnt;
          this.offset = result.pageInfo.offset;
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnSelectFaqType(){
      const params = {
        codeTypeCd : "FAQ_TYPE",
        useYN : "Y"
      };
      customereApi.selectCodeList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          this.faqTypeList = Object.assign({}, result.data);
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnSearch(pageNo) {
      this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNo, '1'))*1;
      this.fnSelectFaqList();
    },
  }
}
</script>