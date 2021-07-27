<template>
  <div class="modal fade modalStyle" id="noticeDetailLayer" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:800px">
      <div class="modal-content">
        <div class="modal-body">
          <div>
            <h2>공지사항</h2>
            <hr>
            <div class="boardWrite">
              <div class="title">
                <p>
                  <span v-if="!$gfnCommonUtils.isEmpty(noticeData.noticeTypeCdName)" class="new2">{{noticeData.noticeTypeCdName}}</span>
                  {{noticeData.title | unescapeXss}}
                </p>
                <span class="date">{{noticeData.regDt}}</span>
              </div>
              <div class="content scroll-y">
                <p v-html="noticeData.content"></p>
              </div>
            </div>
          </div>
          <div class="text-center mt20">
            <a href="#self" class="btnStyle1 backWhite" data-dismiss="modal" title="닫기">닫기</a>
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