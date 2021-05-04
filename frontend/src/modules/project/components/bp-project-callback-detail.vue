<template>
  <div class="modal fade modalStyle" id="detailPop" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog" style="width:650px">
        <div class="modal-content">
          <div class="modal-body">
            <div class="of_h">
              <h2>발신번호 상세</h2>
              <hr>
              <div class="row">
                <div class="col-xs-12 consoleCon">
                  <div class="of_h consolMarginTop">
                    <h4 class="inline-block" style="width:22%">발신번호 *</h4>
                    <div class="float-right" style="width:76%">
                      <input type="text" class="inputStyle" style="width:55%" v-model="row_data.chatbotId">
                      <p class="color3 inline-block ml10">휴대폰번호 등록 불가</p>
                    </div>
                  </div>
                  <div class="of_h">
                    <h4 class="inline-block" style="width:22%">발신 번호명 *</h4>
                    <input type="text" class="inputStyle float-right" style="width:76%" v-model="row_data.subTitle">
                  </div>
                </div>
              </div>
            </div>						
            <div class="text-center mt20">
              <a @click="fnModApproval" class="btnStyle1 backBlack">수정요청</a>
              <a @click="fnCloseLayer" class="btnStyle1 backWhite">닫기</a>						
            </div>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
import tokenSvc from '@/common/token-service';
import projectApi from '../service/projectApi'

export default {
  name: 'callbackDetail',
  data() {
    return {
    }
  },
  props: {
    projectId : {
      type: String,
      require: true
    },
    row_data : {
      type : Object,
      require: false
    }
  },
  mounted() {
  },
  methods: {
    // 닫기
    fnCloseLayer: function() {
      jQuery("#detailPop").modal("hide");
    },
    // 승인요청
    fnModApproval(){
      var params = {
        "projectId"    : this.projectId,
        "chatbotId"    : this.row_data.chatbotId,
        "subTitle"     : this.row_data.subTitle,
      };

      console.log(params);

      projectApi.updateCallbackForApi(params).then(response =>{
        var result = response.data.data;
        console.log(result);
      });
    }
  }
}
</script>
