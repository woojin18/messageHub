<template>
  <div>
    <layerPopup  :reqType = "reqType" />
    <article v-if="!preRegYn">
		<h4 class="mt40">발신번호 사전 등록</h4>
		<p class="font-size12 color3 mt30 inline-block">
			<i class="far fa-info-circle"></i> 발신번호 미등록 시 메시지가 정상적으로 전송되지 않을 수 있습니다.
			<br>
			<i class="far fa-info-circle"></i> 발신번호는 문자발송장비 사용자의 최대 3배까지만 등록할 수 있습니다.
		</p>

		<ul class="tabStyle tab6 bgColor_tapGray mt30">
			<li @click="fnMoveMainTab('callbackPreReg')"><a style="width:23%">발신번호 인증 등록</a></li>
			<li @click="fnMoveMainTab('callbackPreRegEx')" class="active"><a style="width:23%">SMS 사전 등록 예외 대상 사업자로 등록</a></li>
		</ul>

		<p class="font-size12 color3 mt10"><i class="far fa-info-circle"></i> 웹발송방식 및 서버발송방식을 통한 문자 발송 시 모두 이용 가능합니다.</p>


		<div class="row bgColor_f1 row-no-margin consolMarginTop">
			<div class="col-xs-3" style="border-right:1px solid #fff">
				<h4 class="ml30 line-height1-5em topH4" style="padding:20% 0">위법한 문자를 발송한 적이<br>없는 사업자<br>(스팸 문자 미발송 사업자)</h4>
			</div>
			<div class="col-xs-6">					
				<div class="of_h pd20">
					<p class="color4">사기, 도박, 불법의약품 및 성매매알선 등 위법한 영리목적의 광고성 정보를 발송하지 않는 자에 한하여 KISA에서 확인서를 발급받은 사업자</p>
					<p class="color4 consolMarginTop">[신청절차]<br>&nbsp;&nbsp;- 한국인터넷진흥원(이하 KISA)확인서 발급요청(요청서 작성, 첨부01 참고)→KISA 심사후 결과 통보(1주일 예상) <br>&nbsp;&nbsp; → 예외사업자 등록 완료(1년간만 유효, 1년 이후 갱신필요)</p>	
					<p class="color4 consolMarginTop">[제출서류]<br>&nbsp;&nbsp; - 신청서 1부 
						<a @click="fnFileDown('FLEZv2KRZJ', '위법한 문자를 발송한 적이 없는 사업자.zip')" class="btnStyle1 borderLightGray small mr5 ml10" >다운로드 </a>
					</p>	
				</div>
			</div>	
			<div class="col-xs-3 text-center" style="padding:7% 0; border-left:1px solid #fff">
				<a @click="fnReg('NOSPAM')"  class="btnStyle1 font13" data-toggle="modal" data-target="#Register">발신번호 예외사업자 등록 요청</a>
			</div>
		</div>

		
		<div class="row bgColor_f1 row-no-margin mt10">
			<div class="col-xs-3" style="border-right:1px solid #fff">
				<h4 class="ml30 line-height1-5em topH4" style="padding:20% 0">발신번호 사전등록<br>시스템을 갖춘 사업자</h4>
			</div>
			<div class="col-xs-6">					
				<div class="of_h pd20">
					<p class="color4">
						[제출서류]<br>&nbsp;&nbsp; - 자체 발신번호 시스템 구축 사실 공문 1부
						<a @click="fnFileDown('FLEZ9BwU67', '발신번호 사전등록 시스템_구축사실 공문.zip')"  class="btnStyle1 borderLightGray small mr5 ml10">다운로드 </a>
					</p>
					<p class="color4 consolMarginTop">
						&nbsp;&nbsp; - 발신번호 사전등록 시스템 설계화면
						<a @click="fnFileDown('FLEIZyTr7Y', '발신번호 사전등록 시스템_설계도.zip')"  class="btnStyle1 borderLightGray small mr5 ml10">다운로드 </a>
					</p>
					<p class="color4 consolMarginTop">
						&nbsp;&nbsp; - 발신번호 사전등록 화면
						<a @click="fnFileDown('FLEPxALXTj', '발신번호 사전등록 시스템_발신번호 사전등록 화면.zip')"  class="btnStyle1 borderLightGray small mr5 ml10">다운로드 </a>
					</p>
					<p class="color4 consolMarginTop">
						&nbsp;&nbsp; - 미등록 발신번호 차단처리 화면
						<a @click="fnFileDown('FLEuuvKvJp', '발신번호 사전등록 시스템_잘못된 발신번호 입력시 차단화면.zip')"  class="btnStyle1 borderLightGray small mr5 ml10">다운로드 </a>
					</p>	
				</div>
			</div>	
			<div class="col-xs-3 text-center" style="padding:6% 0; border-left:1px solid #fff">
				<a @click="fnReg('REGNUM')" class="btnStyle1" data-toggle="modal" data-target="#Register">발신번호 예외사업자 등록 요청</a>
			</div>
		</div>

		
		<div class="row bgColor_f1 row-no-margin mt10">
			<div class="col-xs-3" style="border-right:1px solid #fff">
				<h4 class="ml30 topH4" style="padding:24% 0">공공기관</h4>
			</div>
			<div class="col-xs-6">					
				<div class="of_h pd20">
					<p class="color4">국가, 지방자치단체 또는 [공공기관의 운영에 관한 법룔]에 따른 공공기관등에서 공익 목적으로 문자메시지를 발송하는 경우</p>	
					<p class="color4 consolMarginTop">[공공기관의 운영에 관한 법률]에 따른 공공기관 316개에 해당되는 사업자
						<br>(<a href="http://www.alio.go.kr/management.do?p=public02" target="blank">http://www.alio.go.kr/management.do?p=public02</a> 에서 확인 가능)
					</p>	
					<p class="color4 consolMarginTop">[제출서류]<br>&nbsp;&nbsp;- 고유번호증(고유번호증이 없는 공공기관의 경우 사업자등록증)
						<a @click="fnFileDown('FLEBMfrTJi', '고유번호증 샘플.jpg')" class="btnStyle1 borderLightGray small mr5 ml10" download>다운로드 </a>
					</p>	
				</div>
			</div>	
			<div class="col-xs-3 text-center" style="border-left:1px solid #fff; padding:6% 0">
				<a @click="fnReg('PUBLIC')" class="btnStyle1" data-toggle="modal" data-target="#Register">발신번호 예외사업자 등록 요청</a>
			</div>
		</div>

		<div class="row bgColor_f1 row-no-margin mt10">
			<div class="col-xs-3" style="border-right:1px solid #fff">
				<h4 class="ml30 topH4" style="padding:31% 0">특수부가통신사업자</h4>
			</div>
			<div class="col-xs-6">					
				<div class="of_h pd20">
					<p class="color4">- 중앙전파관리소에서 지정한 부가통신사업 등록서류<br>&nbsp;&nbsp; (서류 종류 및 제출 방법에 대해서는 중앙전파관리소
						<br>&nbsp;&nbsp;&nbsp; 홈페이지 <a href="http://www.crmo.go.kr/administ/brdcstadm/specialaddedSMS.do" target="blank">http://www.crmo.go.kr/administ/brdcstadm/specialaddedSMS.do</a> 참고)
					</p>
					<p class="color4 consolMarginTop">
						- 중앙전파관리소로부터 특수부가통신사업자로 등록 완료된 리스트를 전달받아 등록 처리됨.<br>
						&nbsp;&nbsp; 단, 정기적으로 전달되지 않으므로 당사 홈페이지에서 예외사업자로 적용되었는지 반드시 확인 필요
					</p>
					<p class="color4 consolMarginTop">
						[제출서류]<br>- 특수부가통신사업자 등록증 1부
						<a @click="fnFileDown('FLEaPOER1r', '특수부가통신사업자 등록증 샘플.jpg')" class="btnStyle1 borderLightGray small mr5 ml10">다운로드 </a>
					</p>	
				</div>
			</div>	
			<div class="col-xs-3 text-center" style="border-left:1px solid #fff; padding:7.5% 0">
				<a @click="fnReg('SPECIAL')" class="btnStyle1" data-toggle="modal" data-target="#Register">발신번호 예외사업자 등록 요청</a>
			</div>
		</div>

		<div>
			<div class="text-center">
				<a @click="fnMovePjtTab" class="btnStyle3 white font14" data-dismiss="modal">목록</a>						
			</div>
		</div>
		<p class="font-size12 color3 mt30 inline-block"><i class="far fa-info-circle"></i>U+ Biz SMS 고객센터 : 이메일 smshelp@lguplus.co.kr, 팩스 02) 6919-1000<br><i class="far fa-info-circle"></i>사전 등록 예외 사업자는 서버 전송 시에만 적용됩니다. 웹 전송 시에는 등록된 발신번호가 없으면 전송이 실패되오니 유의하시기 바랍니다.</p>
		<!-- //본문 -->
	</article>
    <!-- 이미 사전등록 예외로 등록되어있는 경우 -->
    <article v-if="preRegYn">
		<h4 class="mt40">발신번호 사전 등록</h4>
		<p class="font-size12 color3 mt30 inline-block"><i class="far fa-info-circle"></i> 발신번호 미등록 시 메시지가 정상적으로 전송되지 않을 수 있습니다.<br><i class="far fa-info-circle"></i> 발신번호는 문자발송장비 사용자의 최대 3배까지만 등록할 수 있습니다.</p>

		<ul class="tabStyle tab6 bgColor_tapGray mt30">
			<li @click="fnMoveMainTab('callbackPreReg')"><a style="width:23%">발신번호 인증 등록</a></li>
				<li @click="fnMoveMainTab('callbackPreRegEx')" class="active"><a style="width:23%">SMS 사전 등록 예외 대상 사업자로 등록</a></li>
		</ul>

		<p class="font-size12 color3 mt10"><i class="far fa-info-circle"></i> 특수부가통신사업자 으로 사전등록 예외 사업자로 등록되어 있습니다.</p>

		<div class="text-center">
			<a @click="fnMovePjtTab" class="btnStyle3 white font14" data-dismiss="modal">목록</a>						
		</div>
		
		<p class="font-size12 color3 mt30 inline-block"><i class="far fa-info-circle"></i>U+ Biz SMS 고객센터 : 이메일 smshelp@lguplus.co.kr, 팩스 02) 6919-1000<br><i class="far fa-info-circle"></i>사전 등록 예외 사업자는 서버 전송 시에만 적용됩니다. 웹 전송 시에는 등록된 발신번호가 없으면 전송이 실패되오니 유의하시기 바랍니다.</p>
		<!-- //본문 -->

		<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
	</article>
  </div>
</template>


<script>
import api from '../service/projectApi'
import commonUtilApi from "@/modules/commonUtil/service/commonUtilApi";

import layerPopup from "./bp-project-callback-preRegEx.vue";
import tokenSvc from '@/common/token-service';

export default {
  components: {
    layerPopup
  },  
  data() {
    return {
      // 프로젝트 정보
      projectId : '',
      projectName : '',
      reqType : '',
      preRegYn : 'Y'
    }
  },
  mounted() {
    this.projectId = this.$route.params.projectId;
    this.projectName = this.$route.params.projectName;

    this.fnCheckPreRegYn();
  },
  methods: {
    fnMoveMainTab(moveTabName){
      this.$router.push( {name:moveTabName, params:{"projectId" : this.projectId, "projectName" : this.projectName }} );
    },
	fnMovePjtTab(){
		this.$router.push( {name:'projectMain'
			, params:{"projectId" : this.projectId, "projectName" : this.projectName, "selMainTab" : 5, "selMidTab" : 1, "selSubTab" : 1  }} 
		);
	},
    // 등록팝업창
    fnReg(reqType) {
      this.reqType = reqType;
      jQuery("#preRegExPop").modal("show");
    },
    // 발신번호 예외사업지 등록 요청된 업체인지 확인
    fnCheckPreRegYn(){
      var params = {
        "corpId"  : tokenSvc.getToken().principal.corpId,
      } 
       
      api.checkPreRegYn(params).then(response =>{
        this.preRegYn = response.data.success;
      });
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
    }

  }
}
</script>