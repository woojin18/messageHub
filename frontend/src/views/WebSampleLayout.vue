<template>
  <body>
      <web-sample-header></web-sample-header>

      <div class="container">
        <router-view :key="$route.fullPath"></router-view>

        <div class="footer_wrap">
          <footer class="footer">
            <div class="footer_info"> 
              <p><strong>㈜엘지유플러스</strong><span><em>주소</em> 서울특별시 용산구 한강대로 32</span></p>
              <p><span><em>대표이사</em>하현회</span><span><em>사업자번호</em>220-81-39938</span><span><em>통신판매신고</em>제 2010-서울중구-0968호</span><span><em>고객센터</em>1544-5992</span><span><em>e-Mail</em>smshelp@lguplus.co.kr</span></p>
            </div>
            <p class="copy">Copyright © LG Uplus Corp. All Rights Reserved.</p>
          </footer>
        </div>

      </div>
<!-- 로딩레이어-->
      <div class="loading_layer">
        <div class="loader"></div>
      </div>
  </body>
</template>

<script>
import WebSampleHeader from "../components/WebSampleHeader";
import vuejsDatepicker from "../components/vuejs-datepicker";
import "../common/ko.js";

import "../assets/css/layout.css";
import "../assets/css/content.css";
import "../assets/css/tiny-slider.css";

export default {
  name: "webSampleLayout",
  components: {
    WebSampleHeader,
  },

};
</script>