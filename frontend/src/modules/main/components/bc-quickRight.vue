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
    <InquiryPopup ref="inquiryPopup"></InquiryPopup>
  </div>
</template>

<script>
import InquiryPopup from "@/modules/customer/components/bp-inquiry.vue";

export default {
  name: 'quickRight',
  components : {
    InquiryPopup
  },
  mounted() {
    this.fnSetQuickRight();
  },
  methods: {
    fnOpenInquiryModal(){
      this.$refs.inquiryPopup.fnRestData();
      jQuery("#Inquiry").modal("show");
      this.$refs.inquiryPopup.fnSelectFaqType();
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
    }
  }
}
</script>
