<template>
  <div class="modal modalStyle" id="noticeDetailLayer" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:800px">
      <div class="modal-content">
        <div class="modal-body">
          <div id="layerPopup1" class="layerPopStyle">	
            <h3>공지사항</h3>
            <div class="popBox">
              <div class="popTitle">
                <span v-if="!$gfnCommonUtils.isEmpty(noticeData.noticeTypeCdName)" :class="noticeData.noticeType | getNotiTypeClass">{{noticeData.noticeTypeCdName}}</span>
                <span>{{noticeData.title | unescapeXss}}</span>
                <span class="popDate">{{noticeData.regDt}}</span>
              </div>
              <div class="popCont">
                <p v-html="noticeData.content"></p>
              </div>
            </div>
            <div class="text-center" style="padding-top: 20px;">
              <a href="#" data-dismiss="modal" class="btnStyle3 white font14" title="닫기">닫기</a>						
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import customereApi from "@/modules/customer/service/customerApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: "noticeLayer",
  props: {
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
  methods: {
    fnSetNoticeInfo(noticeId){
      if(!this.$gfnCommonUtils.isEmpty(noticeId)){
        const vm = this;
        const params = {
          noticeId: noticeId
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
    }
  }
}
</script>