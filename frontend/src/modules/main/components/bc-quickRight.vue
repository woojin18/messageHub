<template>
  <div>
    <div class="quickRightWrap">
      <ul class="quickRight">
        <li>
          <a href="#" @click.prevent="fnOpenInquiryModal" class="quick_inquiry" title="1:1 문의하기" data-toggle="modal" data-target="inquiryLayer">
            <img src="@/assets/images/main/quickIcon_Inquiry.png" alt="1:1 문의하기">
          </a>
        </li>
        <li><a href="#" class="quick_top"><img src="@/assets/images/main/quickIcon_top.png" alt=""></a></li>
      </ul>
    </div>
    <InquiryPopup2 ref="inquiryPopup2" :loginUserInfo="loginUserInfo"></InquiryPopup2>
  </div>
</template>

<script>
import InquiryPopup2 from "@/modules/customer/components/bp-inquiry2.vue";
import tokenSvc from '@/common/token-service';
import myPageApi from '@/modules/myPage/service/myPageApi';

export default {
  name: 'quickRight',
  components : {
    InquiryPopup2
  },
  created(){
    this.isLogin = !!tokenSvc.getToken();
  },
  mounted() {
    this.fnSetQuickRight();
  },
  data(){
    return {
      isLogin : false,
      loginUserInfo : {}
    }
  },
  methods: {
    fnOpenInquiryModal(){
      this.$refs.inquiryPopup2.fnRestData();
      jQuery("#Inquiry2").modal("show");
      this.$refs.inquiryPopup2.fnSelectFaqType();
    },
    fnSetQuickRight(){
      jQuery(window).scroll(function(){
        let y = jQuery(this).scrollTop();
        if(y >= 950){
          jQuery('.quickRight').addClass('fix');
        }
        else {
          jQuery('.quickRight').removeClass('fix');
        }
      });
      
      if(this.isLogin){
        this.fnSetUserInfo();
      }
    },
    fnSetUserInfo(){
      var params = {
        userId : tokenSvc.getToken().principal.userId
      };
      myPageApi.selectMemberInfo(params).then(response => {
        var result = response.data;
        if(result.success){
          this.loginUserInfo.inputName  = result.data.userName;
          this.loginUserInfo.hpNumber   = result.data.hpNumber;
          this.loginUserInfo.email      = result.data.loginId;
          this.loginUserInfo.corpName   = result.data.corpName;
        } else {
          this.loginUserInfo = {};
        }
      });
    }
  }
}
</script>
