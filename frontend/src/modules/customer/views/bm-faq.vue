
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
              <li class="active"><a href="#self">전체</a></li>
              <li v-for="(faqTypeInfo, idx) in faqTypeList" :key="idx">
                <a href="#self">{{faqTypeInfo.faqTypeCdName}}</a>
              </li>
            </ul>
            <ul class="faqList">
              <li>
                <div class="faqTitle"><span class="faqBg">Q 1</span><p>회원가입을 하고 싶은데 어떻게 하면 될까요?<span class="faqIcon">아이콘</span></p></div>
                <div class="faqAn">답변1</div>
              </li>
              <li>
                <div class="faqTitle"><span class="faqBg">Q 2</span><p>계정에 등록된 회원정보를 바꾸고 싶은데 어떻게 하면 되나요?<span class="faqIcon">아이콘</span></p></div>
                <div class="faqAn">답변2</div>
              </li>
              <li>
                <div class="faqTitle"><span class="faqBg">Q 3</span><p>개인계정을 사업자계정으로 변경하거나, 사업자계정을 개인계정으로 변경할 수 있나요?<span class="faqIcon">아이콘</span></p></div>
                <div class="faqAn">답변3</div>
              </li>
              <li>
                <div class="faqTitle"><span class="faqBg">Q 4</span><p>결제카드를 변경 및 삭제하고 싶은데 어떻게 하나요?<span class="faqIcon">아이콘</span></p></div>
                <div class="faqAn">답변4</div>
              </li>
              <li>
                <div class="faqTitle"><span class="faqBg">Q 5</span><p>결제방법을 계좌이체로 변경하고 싶은데 어떻게 하면 되나요?<span class="faqIcon">아이콘</span></p></div>
                <div class="faqAn">답변5</div>
              </li>
              <li class="active">
                <div class="faqTitle"><span class="faqBg">Q 6</span><p>메시지 발송은 어떻게 하나요?<span class="faqIcon">아이콘</span></p></div>
                <div class="faqAn">개인계정을 사업자계정으로 변경하거나, 사업자계정을 개인계정으로 변경할 수 있습니다.</div>
              </li>
            </ul>
            <!-- pagination -->
            <div class="row mt40">
              <div class="col-xs-12">
                <div class="pagination1 text-center">
                  <a href="#" title="10페이지 이전 페이지로 이동"><i class="far fa-chevron-double-left"></i></a>
                  <a href="#" title="이전 페이지로 이동"><i class="far fa-chevron-left"></i></a>
                  <a href="#" title="1페이지로 이동" class="number active">1</a>
                  <a href="#" title="2페이지로 이동" class="number">2</a>
                  <a href="#" title="3페이지로 이동" class="number">3</a>
                  <a href="#" title="4페이지로 이동" class="number">4</a>
                  <a href="#" title="5페이지로 이동" class="number">5</a>
                  <a href="#" title="다음 페이지로 이동"><i class="far fa-chevron-right"></i></a>
                  <a href="#" title="10페이지 다음 페이지로 이동"><i class="far fa-chevron-double-right"></i></a>
                </div>
              </div>
            </div>
            <!-- //pagination -->
          </section>
        </div>
      </div>
      
    <QuickRight></QuickRight>
    </div>
  </div>
</template>

<script>
import QuickRight from "@/modules/main/components/bc-quickRight.vue";

import customereApi from "@/modules/customer/service/customerApi.js";

export default {
  name: 'faq',
  components : {
    QuickRight
  },
  data() {
    return {
      faqTypeList: []
    }
  },
  mounted() {
    this.fnSelectSmsTmpltList();
  },
  methods: {
    fnSelectSmsTmpltList(){
      const params = {};
      customereApi.selectFaqTypeList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          this.faqTypeList = Object.assign({}, result.data);
          console.log(this.faqTypeList);
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
  }
}
</script>