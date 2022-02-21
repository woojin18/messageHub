<template>
<div class="modal modalStyle" id="regPop" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:650px">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h">
						<h2>발신번호 등록</h2>
						<hr>
						<div class="row">
							<div class="col-xs-12 consoleCon">				
								<div class="of_h">
									<h5 class="inline-block" style="width:22%">브랜드명</h5>
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
                  <h5 class="inline-block" style="width:22%">추가발신번호</h5>
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
                          <td class="end"><input v-model="chatbots[i].mdn" :id="'mdn' + i"  type="text" class="inputStyle" style="width:100%" @input="fnHpNumberAddDash($event)"></td>
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
									<h5 class="inline-block vertical-middle" style="width:22%">통신서비스<br>가입증명원 *</h5>
									<div class="float-right" style="width:76%">
										<input id="certiImgFile" type="file" class="btnStyle7 minWidthAuto float float-right" style="width:100%" ref="certiImgFile"/>
									</div>
								</div>
								<p class="txtCaption mt20">첨부 파일 확장자는 <strong>png, jpg, pdf, zip, tif, tiff</strong>만 허용됩니다.</p>
								<p class="txtCaption color3">(5M 이하 파일 가능)</p>
							</div>
						</div>
					</div>						
					<div class="text-center mt20">
						<a @click="fnApproval" class="btnStyle1 backBlack" activity="SAVE">승인요청</a>
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
      rcsReplyYn : "",
    }
  },
  mounted() {
  },
  props: {
    srcProjectId : {
      type: String,
      require: true
    },
    detailCnt : {
      type : Number,
      require : true
    }
  },
  watch: {
    srcProjectId(){
      this.fnBrandList();
    },
    detailCnt (){
      this.chatbotCnt = 0;
      this.chatbots = [];
      jQuery("#certiImgFile").val("");
    }
  },
  methods: {
    // 닫기
    fnCloseLayer: function() {
      jQuery("#regPop").modal("hide");
    },
    fnBrandList(){
      var params = { "srcProjectId" : this.srcProjectId }

      projectApi.selectApprovalBrandList(params).then(response =>{
        this.brandList = response.data.data;
        this.brandId = this.brandList[0].brandId;
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
    //전화번호 포맷
    fnHpNumberAddDash(event){
      var arrId = event.target.id.substr(3);
	  	this.chatbots[arrId].mdn = this.$gfnCommonUtils.hpNumberAddDash(event.target.value.replace(/[^0-9]/g, ''));  
    },
    // 승인요청
    async fnApproval(){

      if(this.chatbots.length == 0) {
                confirm.fnAlert("", "추가 발신번호를 하나 이상 입력해주세요.");
        return;
      }
      var isMdn = true;
      var isSubTitle = true;
      this.chatbots.map(function(value) {
        if(value.mdn == '') {
          isMdn = false;
          return;
        }
        if(value.subTitle == '') {
          isSubTitle = false;
          return;
        }
      })

      if (isMdn == false) {
        confirm.fnAlert("", "등록할 발신번호를 입력해주세요.");
        return;
      }
      if (isSubTitle == false) {
        confirm.fnAlert("", "등록할 발신번호명을 입력해주세요.");
        return;
      }
      
      if(this.$refs.certiImgFile.files[0] === null || this.$refs.certiImgFile.files[0] === undefined ){
                confirm.fnAlert("", "통신서비스 가입증명원 파일을 등록해주세요.");
        return;
      }

      var fd = new FormData();
      fd.append('sts'			    , "C");
      fd.append('saveCorpId'	, tokenSvc.getToken().principal.corpId);
      fd.append('projectId'		, this.srcProjectId);
      fd.append('brandId'			, this.brandId);
      fd.append('chatbotId'		, '');
      fd.append('mainMdn'			, this.mainMdn);
      fd.append('mainTitle'		, this.mainTitle);

      // 첨부파일 정리
      fd.append('certiFile'		, this.$refs.certiImgFile.files[0]);

      // 챗봇(발신번호) 정리
      var list = [];

      for( var i = 0; i < this.chatbots.length; i++ ){
        var chatbot = Object.assign({}, this.chatbots[i]);
				chatbot.mdn = this.$gfnCommonUtils.hpNumberRemoveDash(chatbot.mdn);
        var obj = JSON.stringify(chatbot);
        // var obj = JSON.stringify(this.chatbots[i]);    //원본
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
          jQuery("#regPop").modal("hide");
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
