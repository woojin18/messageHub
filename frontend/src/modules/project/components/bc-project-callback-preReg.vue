<template>
  <div>
    <article>
      <h4 class="mt40">발신번호 사전 등록<input type="hidden" id="message"><button id="nice" style="display:none;" @click="fnMessage"></button></h4>
      <p class="font-size12 color3 mt30 inline-block">
        <i class="far fa-info-circle"></i> 발신번호 사전등록제의 시행으로 인하여, 2015년 10월 16일부로 사전에 등록하지 않은 발신번호는 SMS/LMS/MMS/RCS 발송이 제한됩니다.<br>
        <i class="far fa-info-circle"></i> 발신번호 등록을 위해서는 RCS Biz Center 가입 후, 브랜드를 생성해야 합니다. 발신번호는 브랜드 1개당 N개 추가할 수 있습니다.
      </p>


		<!-- 본인인증 서비스 팝업을 호출하기 위해서는 다음과 같은 form이 필요합니다. -->
		<form name="form_chk" method="post">
			<input type="hidden" name="m" value="checkplusService">						<!-- 필수 데이타로, 누락하시면 안됩니다. -->
			<input type="hidden" name="EncodeData" v-model="sEncData">		<!-- 위에서 업체정보를 암호화 한 데이타입니다. -->
		</form>


      <ul class="tabStyle tab6 bgColor_tapGray mt30">
        <li @click="fnMoveMainTab('callbackPreReg')" class="active"><a style="width:23%">발신번호 인증 등록</a></li>
        <li @click="fnMoveMainTab('callbackPreRegEx')"><a style="width:23%">SMS 사전 등록 예외 대상 사업자로 등록</a></li>
      </ul>

      <p class="font-size12 color3 mt10"><i class="far fa-info-circle"></i> 웹발송방식 및 서버발송방식을 통한 문자 발송 시 모두 이용 가능합니다.</p>

      <div class="row bgColor_f1 row-no-margin consolMarginTop">
				<div class="col-xs-3" style="padding: 7% 0; border-right:1px solid #fff">
					<h4 class="ml30 topH4">발신번호 등록 안내</h4>
				</div>
				<div class="col-xs-9">					
					<div class="of_h pd20">
						<p class="color4">
							<i class="far fa-info-circle"></i> 발신번호 등록은 다음의 절차로 진행됩니다.<br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1) RCS Biz Center(이하 RBC)에 서비스 신청을 합니다. (https://www.rcsbizcenter.com)<br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2) RBC 로그인 후 "내 정보관리"에서 API Client IP에 3.36.221.48, 3.38.76.80를 추가 후 저장합니다.<br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3) "내 정보관리"의 API Key*를 적어 둡니다.<br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4) 메시지허브에서 브랜드 등록 및 발신번호 등록 신청을 할수 있습니다. (RBC ID, RBC API Key 필요)<br><br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; RBC에 이미 등록된 브랜드를 등록하는 경우)<br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 브랜드를 선택 후 "운영 관리" > "권한관리" 기능에서 "엘지유플러스"를 대행사로 추가합니다.<br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 자동으로 메시지허브에 등록됩니다. (최대 24시간 소요)
						</p>
						<p class="color4 consolMarginTop"><i class="far fa-info-circle"></i> 이미 등록된 브랜드가 있는 경우 추가 발신번호만 등록하실 수 있습니다.</p>	
						<p class="color4 consolMarginTop"><i class="far fa-info-circle"></i> 통신서비스 이용 증명원 등록 후, RCS Biz Center(RBC) 에서 서류 심사 후 발신번호로 등록<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[통신서버스 이용증명원 발급방법안내]<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 등록할 <span class="color1">휴대폰 번호 또는 유선번호의 해당 통신사 고객센터를 통해 발급</span> 받을 수 있습니다.</p>	
						<p class="color4 consolMarginTop"><i class="far fa-info-circle"></i> 발신번호별 검수로 서류심사가 늦어질 수 있는 점 양해 부탁드립니다.</p>	
					</div>
				</div>				
			</div>


      <div class="row bgColor_f1 row-no-margin mt10">
				<div class="col-xs-3" style="padding: 2% 0; border-right:1px solid #fff">
					<h4 class="ml30 topH4">브랜드 등록이 필요한 경우</h4>
				</div>
				<div class="col-xs-9">					
					<div class="of_h pd20">
						<h5>대리인으로 등록</h5>
						<p class="color4 inline-block"><i class="far fa-info-circle"></i> RCS Biz Center 에서 발급받은 API Key를 사용하여 브랜드와 발신번호를 등록 신청할 수 있습니다.</p>	
						<a @click="fnMoveRcsTab()" class="btnStyle4 white font13 width276 mt10 float-right">브랜드 등록 요청</a>
					</div>
				</div>				
			</div>

      <div class="row bgColor_f1 row-no-margin mt10">
				<div class="col-xs-3" style="padding: 3% 0; border-right:1px solid #fff">
					<h4 class="ml20 topH4">브랜드 등록이 필요없는 경우</h4>
				</div>
				<div class="col-xs-9">					
					<div class="of_h pd20">
						<h5>대리인으로 등록</h5>
						<p class="font-size14 color4 mt20 line-height2em inline-block"><i class="far fa-info-circle"></i> 이미 등록된 브랜드에 발신번호를 등록 신청할  수 있습니다.</p>	
            <a @click="fnCallbackReg" class="btnStyle4 white font13 width276 mt10 float-right">추가 발신번호 등록 요청</a>
					</div>
				</div>				
			</div>

      <div>
        <div class="text-center mt40">
          <a @click="fnMovePjtTab('callbackManage')" class="btnStyle3 white font14" data-dismiss="modal">목록</a>						
        </div>
      </div>
      <!-- //본문 -->

      <footer>Copyright©LG Uplus Corp. All Rights Reserved.</footer>
    </article>
    <layerPopup  :srcProjectId = "srcProjectId" :detailCnt="detailCnt"/>
  </div>
</template>


<script>
import layerPopup from "./bp-project-callback-preReg.vue";
import confirm from '@/modules/commonUtil/service/confirm';
import projectApi from '../service/projectApi';

export default {
  components: {
    layerPopup
  },
  data() {
    return {
      // 프로젝트 정보
      projectId : '',
      projectName : '',
      srcProjectId : '',
      detailCnt : 0,
			sEncData : '', 				// 나이스본인인증 암호화 정보
    }
  },
  mounted() {
    this.projectId = this.$route.params.projectId;
    this.projectName = this.$route.params.projectName;
    this.fnGetNiceCheck();
  },
  methods: {
    fnMoveMainTab(moveTabName){
      this.$router.push( {name:moveTabName
        , params:{"projectId" : this.$route.params.projectId, "projectName" : this.projectName, "selMainTab" : 5, "selMidTab" : 1, "selSubTab" : 1  }} 
      );
    },
    fnMovePjtTab(){
      this.$router.push( {name:'projectMain'
        , params:{"projectId" : this.$route.params.projectId, "projectName" : this.projectName, "selMainTab" : 5, "selMidTab" : 1, "selSubTab" : 1  }} 
      );
    },
    fnMoveRcsTab(){
      this.$router.push( {name:'projectMain'
        , params:{"projectId" : this.$route.params.projectId, "projectName" : this.projectName, "selMainTab" : 4, "selMidTab" : 1, "selSubTab" : 1  }} 
      );
    },
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
    fnCallbackReg(){
      this.detailCnt = this.detailCnt + 1;
      this.srcProjectId = this.$route.params.projectId;
      console.log(this.detailCnt);
      jQuery("#regPop").modal("show");
    },
  }
}
</script>