<template>
    <!-- foot_type_user -->
  <div class="foot_type_user">
    <div>
      <img src="../../public/se2/images/userLogo_foot.svg" alt="LGU+ 메시지허브 로고" class="foot_logo">
      <div class="foot_info_wrap">
        <ul class="foot_info">
          <li><a href="#" @click.prevent="fnOpenTermsServiceLayer" title="서비스 이용약관 페이지로 이동">서비스 이용약관</a></li>
          <li><a href="#" @click.prevent="fnOpenPrivacyPolicyLayer" title="개인정보처리방침 페이지로 이동">개인정보처리방침</a></li>
        </ul>
        <div class="foot_copy">
          <p>(주)엘지유플러스<span>|</span>주소  서울특별시 용산구 한강대로 32</p>
          <p>대표이사 황현식<span>|</span>사업자번호  220-81-39938<span>|</span>통신판매신고 제 2010-서울중구-0968호<span>|</span>고객센터 1544-5992<span>|</span>email smshelp@lguplus.co.kr</p>
          <p>Copyright©LG Plus Corp. All Rights Reserved.</p>
        </div>
      </div>
    </div>
    <TermsServiceLayer ref="termsServiceLayer"></TermsServiceLayer>
    <PrivacyPolicyLayer ref="privacyPolicyLayer"></PrivacyPolicyLayer>
  </div>
  <!-- //foot_type_user -->
</template>

<script>
import TermsServiceLayer from "@/modules/customer/components/bp-termsService.vue";
import PrivacyPolicyLayer from "@/modules/customer/components/bp-privacyPolicy.vue";

export default {
  name: "PublicFooter",
  components : {
    TermsServiceLayer,
    PrivacyPolicyLayer
  },
  methods: {
    fnOpenTermsServiceLayer(){
      this.$refs.termsServiceLayer.fnSelectCmUseTermsHistList();
      jQuery("#termsServiceLayer").modal("show");
    },
    fnOpenPrivacyPolicyLayer(){
      this.$refs.privacyPolicyLayer.fnSelectCmUseTermsHistList();
      jQuery("#privacyPolicyLayer").modal("show");
    },
  }
};
</script>