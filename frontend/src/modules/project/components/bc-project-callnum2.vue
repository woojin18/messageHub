<template>
	<div>
		<div class="clear">
			<h4 class="inline-block">발신번호 등록</h4>	
		</div>

		<div class="row">
			<div class="col-xs-12">
				<div class="menuBox">						
					<p class="colo4"><i class="far fa-info-circle vertical-baseline"></i> 전기통신사업법 제 84조 <strong>'SMS 발신 번호 사전 등록제'</strong> 가 2015년 10월 16일부로 시행됨에 따라 SMS를 발송하려면 발신 번호를 먼저 등록하셔야 합니다.</p>
					<p class="colo4"><i class="far fa-info-circle vertical-baseline"></i> <strong> RCS 발신번호를 등록하면 문자 발신번호까지 동시</strong>에 등록됩니다.</p>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-xs-12">
				<table class="table_skin1 consolMarginTop" style="width:100%">
					<caption>발신번호 등록 방법의 RCS 발신번호, 문자 발신번호를 제공하는 표</caption>
					<colgroup>
						<col style="width:270px">
						<col style="">
						<col style="width:250px">
					</colgroup>
					<tbody>
						<tr>
							<td rowspan="2" class="text-center vertical-middle">
								<h4 class="topH4">RCS 발신번호 등록</h4>
								<ul class="text-left dotList">
									<li>등록이 완료되면 문자 발신번호도 등록됩니다.</li>
									<li>통신서비스 가입증명원이 필요합니다.</li>
								</ul>
							</td>
							<td class="text-left vertical-middle">
								<h5>RCS 발신번호 등록</h5>
								<p class="color4 mt0">브랜드 등록 요청 -> RBC 검토 -> RBC 승인 -> 메시지허브 등록 (1~3일 소요)</p>
							</td>
							<td class="text-center vertical-middle end">
								<a  @click="$emit('fnMoveMain', '4', '1')" class="btnStyle1 font13 width180">브랜드 등록 요청</a>
							</td>
						</tr>
						<tr>
							<td class="text-left vertical-middle">
								<h5>이미 등록된 브랜드에 발신번호를 등록 요청합니다.</h5>
								<p class="color4 mt0">발신번호 등록 요청 -> RBC 검토 -> RBC 승인 -> 메시지허브 등록 (1~3일 소요)</p>
							</td>
							<td class="text-center vertical-middle end">
								<a @click="fnCallbackReg" class="btnStyle1 font13 width180">RCS 발신번호 등록 요청</a>
							</td>
						</tr>
						<tr>
							<td rowspan="3" class="text-center vertical-middle bb2">
								<h4 class="topH4">문자 발신번호 등록</h4>
								<ul class="text-left dotList">
									<li>문자 발신번호에만 등록됩니다.</li>
								</ul>
							</td>
							<td class="text-left vertical-middle">
								<h5>휴대폰 인증</h5>
								<p class="color4 mt0">안심본인인증을 통한 등록 (즉시 등록)</p>
							</td>
							<td class="text-center vertical-middle end">
								<a @click="fnPopup" class="btnStyle1 font13 width180">휴대폰 인증</a>
								<input type="hidden" id="message"><button id="nice" style="display:none;" @click="fnMessage"></button>
								<!-- 본인인증 서비스 팝업을 호출하기 위해서는 다음과 같은 form이 필요합니다. -->
								<form name="form_chk" method="post">
									<input type="hidden" name="m" value="checkplusService">						<!-- 필수 데이타로, 누락하시면 안됩니다. -->
									<input type="hidden" name="EncodeData" v-model="sEncData">		<!-- 위에서 업체정보를 암호화 한 데이타입니다. -->
								</form>
							</td>
						</tr>
						<tr>
							<td class="text-left vertical-middle">
								<h5>서류 인증</h5>
								<p class="color4 mt0">휴대폰/유선번호 입력 -> 통신 서비스 이용증명원 첨부 -> 심사 -> 메시지허브 등록 (1~3일 소요)</p>
							</td>
							<td class="text-center vertical-middle end">
								<a @click="fnCallNumDoc" class="btnStyle1 font13 width180">서류 인증</a>
							</td>
						</tr>
						<tr>
							<td class="text-left vertical-middle">
								<h5>대리인 등록 요청</h5>
								<p class="color4 mt0">대리인 등록 절차 : 대리인 등록 신청서를 작성하여 증빙서류와 함께 대리인 등록 요청 하시면 서류 심사 후 대리인으로 등록됩니다.<br>대리인 등록이 되면 발신번호를 직접 등록할 수 있습니다. (1~3일 소요)</p>
								<a @click="fnFileDown('FLE6AarFhH', '대리인신청_구비서류.zip')" class="btnStyle1 borderLightGray small mt10">대리인 등록 신청서 다운로드 받기</a>
								<p class="consolMarginTop">제출서류</p>
								<ul>
									<li>- 발신번호 변작방지 및 사전등록 동의서</li>
									<li>- 대리인 등록 신청서</li>
									<li>- 인감증명서</li>
									<li>- 인감이 날인된 위임장</li>
									<li>- 재직증명서</li>
								</ul>
							</td>
							<td class="text-center vertical-middle end" v-if="proxyState == ''">
								<a @click="fnCallNumProxy" class="btnStyle1 font13 width180">대리인 등록 요청</a><br>
								대리인은 기업 또는 기관으로부터<br>권한을 위임 받은 사람으로서 기업<br>또는 기관을 대신하여 발신번호를<br>등록, 수정, 삭제 할 수 있으며 이에<br>대한 권한과 책임을 갖습니다.
							</td>
							<td class="text-center vertical-middle end" v-if="proxyState == '10'">
								<h5 class="color000">요청 중..</h5>
								<p>대리인은 기업 또는 기관으로부터 권한을 위임 받은 사람으로서 기업 또는 기관을 대신하여 발신번호를 등록, 수정, 삭제 할 수 있으며 이에 대한 권한과 책임을 갖습니다.</p>
							</td>
							<td class="text-center vertical-middle end" v-if="proxyState == '20'">
								<h5 class="color000">접수/심사 중..</h5>
								<p>대리인은 기업 또는 기관으로부터 권한을 위임 받은 사람으로서 기업 또는 기관을 대신하여 발신번호를 등록, 수정, 삭제 할 수 있으며 이에 대한 권한과 책임을 갖습니다.</p>
							</td>
							<td class="text-center vertical-middle end" v-if="proxyState == '80'">
								<h5 class="color000">등록할 발신번호</h5>
								<div class="of_h">
									<input type="text" class="inputStyle" style="width:65%" title="발신번호 입력란" placeholder="-를 생략하고 입력하세요" maxlength="11" v-model="callNum">
									<a @click="fnSaveProxy" class="btnStyle1 backLightGray float-right" style="width:32%" title="등록">등록</a>
								</div>
								<div class="of_h text-center mt10"><a @click="fnCallNumAll" class="btnStyle1 backLightGray" style="width:40%" title="일괄등록">일괄등록</a></div>
								<p class="mt10">대리인은 기업 또는 기관으로부터 권한을 위임 받은 사람으로서 기업 또는 기관을 대신하여 발신번호를 등록, 수정, 삭제 할 수 있으며 이에 대한 권한과 책임을 갖습니다.</p>
							</td>
							<td class="text-center vertical-middle end" v-if="proxyState == '90'">
								<h5 class="color000"><a data-toggle="modal" data-target="#reject">반려 <i class="far fa-info-circle vertical-baseline"></i></a></h5>
								<div class="of_h text-center mt10"><a @click="fnCallNumProxy" class="btnStyle1 font13 width180">대리인 등록 요청</a></div>
								<p class="mt10">대리인은 기업 또는 기관으로부터 권한을 위임 받은 사람으로서 기업 또는 기관을 대신하여 발신번호를 등록, 수정, 삭제 할 수 있으며 이에 대한 권한과 책임을 갖습니다.</p>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- //본문 -->
    	<layerPopup  :srcProjectId = "srcProjectId" :detailCnt="detailCnt"/>
    	<layerPopupDoc  :srcProjectId = "srcProjectId" :detailCnt="detailCnt"/>
    	<layerPopupProxy  :srcProjectId = "srcProjectId" :detailCnt="detailCnt"/>
    	<layerPopupAll  :srcProjectId = "srcProjectId" :detailCnt="detailCnt"/>
		
		<!-- reject Modal -->
		<div class="modal modalStyle" id="reject" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog" style="width:650px">
				<div class="modal-content">
					<div class="modal-body">					
						<h2>반려</h2>
						<hr>
						<p class="text-center mt20">{{proxyReason}}</p>		
						<div class="text-center mt20">
							<a class="btnStyle1 backWhite" data-dismiss="modal" title="취소">닫기</a>						
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import layerPopup from "./bp-project-callback-preReg.vue";
import layerPopupDoc from "./bp-project-callnum-doc.vue";
import layerPopupProxy from "./bp-project-callnum-proxy.vue";
import layerPopupAll from "./bp-project-callnum-all.vue";
import confirm from '@/modules/commonUtil/service/confirm';
import projectApi from '../service/projectApi';
import tokenSvc from '@/common/token-service';
import commonUtilApi from "@/modules/commonUtil/service/commonUtilApi";

export default {
  components: {
	  layerPopup,
	  layerPopupDoc,
	  layerPopupProxy,
	  layerPopupAll
  },
  watch: {
    callNum () {
      this.callNum = this.callNum.replace(/[^0-9]/g, '')
    }
  },
  data() {
    return {
      // 프로젝트 정보
      projectId : '',
      projectName : '',
      srcProjectId : '',
      detailCnt : 0,
	  sEncData : '', 				// 나이스본인인증 암호화 정보
	  proxyState : '',
	  proxyReason : '',
	  callNum : ''
    }
  },
  mounted() {
    this.projectId = this.$route.params.projectId;
    this.projectName = this.$route.params.projectName;
	this.fnGetCallNumProxy();
    this.fnGetNiceCheck();
  }, 
  methods: {
	// 본인인증시 사용될 데이터 조회
	fnGetNiceCheck(){
		let sEcnDataVal = '';

		projectApi.getNiceCheck().then((response) => {
			var result = response.data;
			if(result.success){
				sEcnDataVal = result.data.sEncData;
				this.sEncData = sEcnDataVal;
			} else {
				confirm.fnAlert("", result.message);
			}
		});
	},
	// 본인인증 팝업
	fnPopup(){
			window.open('', 'popupChk', 'width=400, height=705, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
			document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
			document.form_chk.target = "popupChk";
			document.form_chk.submit();
	},
	fnMessage() {
		confirm.fnAlert("", jQuery("#message").val());
	},
	// 대리인 데이터 조회
	fnGetCallNumProxy(){
		let params = {'corpId':tokenSvc.getToken().principal.corpId}

		projectApi.getCallNumProxy(params).then((response) => {
			var result = response.data;
			if(result.success){
				this.proxyState = result.data.state;
				this.proxyReason = result.data.handleReason;
			} else {
				confirm.fnAlert("", result.message);
			}
		});
	},
    fnCallbackReg(){
      this.detailCnt = this.detailCnt + 1;
      this.srcProjectId = this.$route.params.projectId;
      jQuery("#regPop").modal("show");
    },
    fnCallNumDoc(){
      this.detailCnt = this.detailCnt + 1;
      this.srcProjectId = this.$route.params.projectId;
      jQuery("#docPop").modal("show");
    },
    fnCallNumProxy(){
      this.detailCnt = this.detailCnt + 1;
      this.srcProjectId = this.$route.params.projectId;
      jQuery("#proxyPop").modal("show");
    },
    fnCallNumAll(){
      this.detailCnt = this.detailCnt + 1;
      this.srcProjectId = this.$route.params.projectId;
      jQuery("#allPop").modal("show");
    },
	// 파일다운로드
    fnFileDown: function(fileId, name) {
      if(fileId != "") {
        var params = {
              fileId    : fileId,
              fileNm      : name
          };

        commonUtilApi.downloadFile(params).then(response =>{
            var result = response;

            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement("a");
            link.href = url;
            link.setAttribute("download", name); //or any other extension
            document.body.appendChild(link);
            link.click();
        });
      }
    },
	// 대리인 등록 
	fnSaveProxy() {
		//유효성 검사
		if(this.callNum == null || this.callNum == '') {
			confirm.fnAlert('', '발신번호를 입력해주세요.');
			return false;
		}
 
		let params = {
			'projectId': this.projectId,
			'callNum': this.callNum
		};

		projectApi.saveCallNumPro(params).then(response =>{
			var result = response.data;
			if(result.success) {
				this.callNum = '';
				confirm.fnAlert('', '등록을 성공했습니다.');
			} else {
				confirm.fnAlert('', result.message);
			}
		});
	}
  }
}
</script>