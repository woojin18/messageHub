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
                      <input type="text" class="inputStyle" style="width:55%" v-model="row_data.chatbotId" readonly>
                      <p class="color3 inline-block ml10">휴대폰번호 등록 불가</p>
                    </div>
                  </div>
                  <div class="of_h">
                    <h4 class="inline-block" style="width:22%">발신 번호명 *</h4>
                    <input type="text" class="inputStyle float-right" style="width:76%" v-model="row_data.subTitle">
                  </div>
                  <div class="of_h consolMarginTop">
                    <h4 class="inline-block vertical-middle" style="width:22%">통신서비스<br>가입증명원 *</h4>
                    <div class="float-right" style="width:76%">
                      <input type="file" class="btnStyle7 minWidthAuto float float-right" style="width:100%" ref="certiImgFile"/>
                    </div>
                  </div>
                </div>
              </div>
            </div>						
            <div class="text-center mt20">
              <a @click="fnApproval" class="btnStyle1 backBlack" activity="SAVE">수정요청</a>
              <a @click="fnCloseLayer" class="btnStyle1 backWhite">닫기</a>						
            </div>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
import axios from 'axios'
import confirm from "@/modules/commonUtil/service/confirm"
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
    async fnApproval(){
      // 벨리데이션 처리
     /*  if( !this.fnValidate() ){
        return;
      } */
      var fd = new FormData();
      fd.append('sts'			    , 'U');
      fd.append('saveCorpId'	, this.row_data.corpId);
      fd.append('projectId'		, this.projectId);
      fd.append('brandId'			, this.row_data.brandId);
      fd.append('chatbotId'		, this.row_data.chatbotId);
      fd.append('mainMdn'			, this.mainMdn);
      fd.append('mainTitle'		, this.mainTitle);

      // 첨부파일 정리
      fd.append('certiFile'		, this.$refs.certiImgFile.files[0]);

      // 챗봇(발신번호) 정리
      var list = [];
      var obj = JSON.stringify({
        "mdn"       : this.row_data.chatbotId,
        "rcsReply"  : this.row_data.rcsReply,
        "subTitle"  : this.row_data.subTitle,
        "service"   : "a2p",
        "display"   : "01"
      });
      list.push(obj);
      fd.append('chatbots'		, list) ;

      await axios.post('/projectApi/manage/saveRcsChatbotReqForApi',
        fd, {headers: {'Content-Type': 'multipart/form-data'}
      }).then( response => {
        var result = response.data;
        if(result.success) {
          confirm.fnAlert("", "저장에 성공했습니다.");
          jQuery("#detailPop").modal("hide");
        } else {
          confirm.fnAlert("", result.message);
        }
      }).catch(function () {
        confirm.fnAlert("", "저장에 실패했습니다.");
      });
    }
  }
}
</script>
