<template>
<div class="modal fade modalStyle" id="regPop" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:650px">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h">
						<h2>발신번호 등록</h2>
						<hr>
						<div class="row">
							<div class="col-xs-12 consoleCon">				
								<div class="of_h">
									<h4 class="inline-block" style="width:22%">브랜드명</h4>				
									<div class="float-right" style="width:76%">
                  <select id="cate1" class="selectStyle2" style="width:100%" v-model="brandId">
                    <option v-for="(option, i) in brandList" v-bind:value="option.brandId" v-bind:key="i">
                      {{ option.brandName }}
                    </option>
                  </select>
									<p class="txtCaption color3">발신번호 등록은 브랜드가 먼저 등록되어야 하며, 승인완료 된 브랜드만 보여집니다.</p>
									</div>
								</div>
                <div class="of_h consolMarginTop">
                  <h4 class="inline-block" style="width:22%">추가발신번호</h4>
                  <div class="float-right" style="width:76%">
                    <p class="txtCaption color3">대표번호문자수신서비스(MO)를 사용하실 경우 MO체크박스에 체크해주세요.</p>
                    <table class="table_skin1 consolMarginTop" style="width:100%">
                      <colgroup>
                        <col style="width:40%">
                        <col style="width:40%">
                        <col style="width:10%">
                        <col style="width:10%">
                      </colgroup>
                      <thead>
                      <tr>
                        <th class="text-center end">발신번호</th>
                        <th class="text-center end">발신번호명</th>
                        <th class="text-center end">MO</th>
                        <th class="text-center end"></th>
                      </tr>
                      </thead>
                      <tbody class="of_h">
                        <tr v-for="(item, i) in chatbots" :key="i">	
                          <td class="end"><input v-model="chatbots[i].mdn" :id="'mdn' + i"  type="text" class="inputStyle" style="width:100%"></td>
                          <td class="end"><input v-model="chatbots[i].subTitle" :id="'subTitle' + i" type="text" class="inputStyle" style="width:100%"></td>
                          <td class="end"><div class="text-center"><input type="checkbox" id="MO01" class="checkStyle2" value="MO01"><label for="MO01"></label></div></td>
                          <td class="end"><a @click="fnDeleteChatbotTr" class="btnStyle1 borderGray ml10" style="padding: 0 10px"><i class="far fa-minus"></i></a></td>
                        </tr>
                        <tr>
                          <td class="end"></td>															
                          <td class="end"></td>															
                          <td class="end"></td>															
                          <td class="end"><a @click="fnAddChatbotTr" class="btnStyle1 borderGray ml10" style="padding: 0 10px"><i class="far fa-plus"></i></a></td>															
                        </tr>								  
                      </tbody>
                    </table>
                  </div>	
                </div>
								<div class="of_h consolMarginTop">
									<h4 class="inline-block vertical-middle" style="width:22%">통신서비스<br>가입증명원 *</h4>
									<div class="float-right" style="width:76%">
										<input type="file" class="btnStyle7 minWidthAuto float float-right" style="width:100%" ref="certiImgFile"/>
										<!-- <div class="color3 consolMarginTop">
											<div class="consolCheck float-left"><input type="checkbox" id="Lmit" class="checkStyle2" value="Lmit"><label for="Lmit"></label></div>
											<p class="txtCaption color000">메시지 발송 시 기본템플릿(구.Free템플릿)은 정보성에 한하여 이용 가능합니다. 규정위반 시 서비스 이용이 제한될 수 있습니다.</p>
										</div> -->
									</div>
								</div>
							</div>
						</div>
					</div>						
					<div class="text-center mt20">
						<a @click="fnApproval" class="btnStyle1 backBlack">승인요청</a>
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

import confirm from "@/modules/commonUtil/service/confirm"

export default {
  name: 'preRegExPop',
  data() {
    return {
      brandId   : "",
      brandList : [
        {
          // 임시용
          "brandId" : "BR.xphojy",
          "brandName" : "brandName2"
        }
      ],
      chatbotCnt : 0,
      chatbots  : [],
      mainMdn   : "",
      mainTitle : "",
      rcsReply  : "",
      rcsReplyYn : ""
    }
  },
  mounted() {
    this.fnBrandList();
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
      jQuery("#regPop").modal("hide");
    },
    fnBrandList(){
      var params = { "projectId" : this.projectId }

      projectApi.selectApprovalBrandList(params).then(response =>{
        this.brandList = response.data.data;
      });
    },
    // 발신번호추가
    fnAddChatbotTr(){
      this.chatbotCnt++;
      this.chatbots.push({
        "mdn"       : "",		  // 발신번호
        "rcsReply"	: "1",		// 대표번호문자 수신서비스 0 = x / 1 = o
        "subTitle"	: "",		  // 발신번호명
        "service"   : "a2p",	// a2p 고정값
        "display"   : "10"		// '10' 고정값
      });
    },
    // 발신번호삭제
    fnDeleteChatbotTr(){
      this.chatbotCnt--;
      this.chatbots.splice(-1);
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
      var list = [];

      for( var i = 0; i < this.chatbots.length; i++ ){
        var obj = JSON.stringify(this.chatbots[i]);
        list.push(obj);
      }
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
