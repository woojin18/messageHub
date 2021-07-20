<template>
  <div>
    <CsMenuSubHeader></CsMenuSubHeader>
    <div id="contentWrap">
      <div class="sub03_conWrap">
        <div class="con01 wow animated fadeInUp">
          <section>
            <div class="boardTitle">
              <p class="titleText">공지사항</p>
              <p class="subText">공지 사항 및 신규 서비스에 관련된 소식을 전해드립니다.</p>
            </div>

            <div class="boardWrite">
              <div class="title">
                <p>
                  <span v-if="!$gfnCommonUtils.isEmpty(noticeData.noticeTypeCdName)" class="new2">{{noticeData.noticeTypeCdName}}</span>
                  {{$gfnCommonUtils.unescapeXss(noticeData.title)}}
                </p>
                <span class="date">{{noticeData.regDt}}</span>
              </div>
              <div class="content">
                <p v-html="noticeData.content"></p>
              </div>
            </div>
            <div class="btn_more">
              <router-link :to="{name: 'notice'}" tag="a" title="목록">목록 ></router-link>
            </div>
          </section>
        </div>
      </div>
    </div>
    <QuickRight></QuickRight>
  </div>
</template>

<script>
import QuickRight from "@/modules/main/components/bc-quickRight.vue";
import CsMenuSubHeader from "@/modules/customer/components/bc-csMenuSubHeader.vue";

import customereApi from "@/modules/customer/service/customerApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: 'noticeDetail',
  components : {
    QuickRight,
    CsMenuSubHeader
  },
  props: {
    noticeId: {
      type: String,
      require: true,
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '공지사항 상세';
      }
    },
  },
  data() {
    return {
      noticeData: {}
    }
  },
  mounted() {
    this.fnSelectNoticeInfo();
  },
  methods: {
    fnSelectNoticeInfo(){
      if(!this.$gfnCommonUtils.isEmpty(this.noticeId)){
        const vm = this;
        const params = {
          noticeId: this.noticeId
        };
        customereApi.selectNoticeList(params).then(response =>{
          const result = response.data;
          if(result.success) {
            result.data.forEach(function(obj){
              vm.noticeData = obj;
            });
          } else {
            confirm.fnAlert(this.componentsTitle, result.message);
          }
        });
      }
    },
  }
}
</script>