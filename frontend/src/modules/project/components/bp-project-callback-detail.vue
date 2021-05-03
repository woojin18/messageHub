<template>
  <div class="modal fade modalStyle" id="detailPop" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog" style="width:650px">
        <div class="modal-content">
          <div class="modal-body">
            <div class="of_h">
              <h2>발신번호 상세</h2>{{this.projectId}}
              <hr>
              <div class="row">
                <div class="col-xs-12 consoleCon">				
                  <div class="of_h">
                    <h4 class="inline-block" style="width:22%">브랜드명</h4>				
                    <div class="float-right" style="width:76%">
                    {{this.brandName}}
                    <p class="txtCaption color3">발신번호 등록은 브랜드가 먼저 등록되어야 하며, 승인완료 된 브랜드만 보여집니다.</p>
                    </div>
                  </div>
                  <div class="of_h consolMarginTop">
                    <h4 class="inline-block" style="width:22%">발신번호 *</h4>
                    <div class="float-right" style="width:76%">
                      <input type="text" class="inputStyle" style="width:55%" v-model="mainMdn">
                      <p class="color3 inline-block ml10">휴대폰번호 등록 불가</p>
                    </div>
                  </div>
                  <div class="of_h">
                    <h4 class="inline-block" style="width:22%">발신 번호명 *</h4>
                    <input type="text" class="inputStyle float-right" style="width:76%" v-model="mainTitle">
                  </div>
                  <div class="of_h">
                    <h4 class="inline-block" style="width:22%">대표번호<br>문자수신 서비스 *</h4>
                    <div class="consolCheck vertical-top float-right" style="width:76%">
                      <input type="checkbox" id="use" class="checkStyle2" v-model="rcsReplyYn"><label for="use">사용중</label>
                      <p class="txtCaption color3">대표번호 문자수신(MO)서비스를 현재 사용하고 계실 경우 반드시 해당정보를 체크해 주셔야 합니다.</p>
                    </div>						
                  </div>
                  <div class="of_h consolMarginTop">
                    <h4 class="inline-block vertical-middle" style="width:22%">통신서비스<br>가입증명원 *</h4>
                    <div class="float-right" style="width:76%">
                      <input type="file" class="btnStyle7 minWidthAuto float float-right" style="width:100%" ref="certiImgFile"/>
                      <div class="color3 consolMarginTop">
                        <div class="consolCheck float-left"><input type="checkbox" id="Lmit" class="checkStyle2" value="Lmit"><label for="Lmit"></label></div>
                        <p class="txtCaption color000">메시지 발송 시 기본템플릿(구.Free템플릿)은 정보성에 한하여 이용 가능합니다. 규정위반 시 서비스 이용이 제한될 수 있습니다.</p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>						
            <div class="text-center mt20">
              <a @click="fnApproval" class="btnStyle1 backBlack">수정요청</a>
              <a @click="fnCloseLayer" class="btnStyle1 backWhite">닫기</a>						
            </div>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
import axios from 'axios'
import tokenSvc from '@/common/token-service';
import projectApi from '../service/projectApi'

export default {
  name: 'callbackDetail',
  data() {
    return {
      brandId   : "",
      brandName : "",
      brandList : [
        {
          // 임시용
          "brandId" : "BR.xphojy",
          "brandName" : "brandName2"
        }
      ],
      chatbots  : [],
      mainMdn   : "",
      mainTitle : "",
      rcsReply  : "",
      rcsReplyYn : ""
    }
  },
  mounted() {
    //this.fnBrandList();
  },
  props: {
    projectId : {
      type: String,
      require: true
    }
  },
  methods: {
    // 닫기
    fnCloseLayer: function() {
      jQuery("#detailPop").modal("hide");
    },
    // 승인요청
    async fnApproval(sts){
      // 벨리데이션 처리
     /*  if( !this.fnValidate() ){
        return;
      } */

      var fd = new FormData();
      fd.append('loginId'			, tokenSvc.getToken().principal.userId);
      fd.append('corpId'			, tokenSvc.getToken().principal.corpId);
      fd.append('projectId'		, this.projectId);
      fd.append('brandId'			, this.brandId);
      fd.append('mainMdn'			, this.mainMdn);
      fd.append('mainTitle'		, this.mainTitle);

      // 첨부파일 정리
      fd.append('certiFile'		, this.$refs.certiImgFile.files[0]);

      // 챗봇(발신번호) 정리
      var mainRcsReplyYn = this.rcsReplyYn;
      
      if( mainRcsReplyYn ){ this.rcsReply = 1 } else { this.rcsReply = 0 }
      // 메인 발신번호 세팅
      this.chatbots.push({
        "mdn"	    	: this.mainMdn,		// 발신번호
        "rcsReply"	: this.rcsReply,		// 대표번호문자 수신서비스 0 = x / 1 = o
        "subTitle"	: this.mainTitle,		// 발신번호명
        "service"	  : "a2p",	// a2p 고정값
        "display"	  : "10"		// '10' 고정값
      });

      var list = [];

      var obj = JSON.stringify(this.chatbots[0]);
      list.push(obj);
      
      var listString = list.join(", ");
      fd.append('chatbots'		, listString) ;

        
      await axios.post('/projectApi/manage/saveRcsChatbotReqForApi',
        fd, {headers: {'Content-Type': 'multipart/form-data'}
      }).then( response => {
        var result = response.data;
        if(result.success) {
          confirm.fnAlert("", "저장에 성공했습니다.");
          // 목록 버튼
          this.$refs.backBtn.click();
        } else {
          confirm.fnAlert("", result.message);
        }
      }).catch(function () {
        confirm.fnAlert("", "저장에 실패했습니다.???");
      });
    }
  }
}
</script>
