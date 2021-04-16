<template>
  <div>
    <layerPopup 
      :reqType = "reqType"
    />
    <article v-if="!preRegYn">
		<div class="contentHeader mb20">
			<h2>비트큐브 프로젝트</h2>
		</div>

		<!-- 본문 -->
		<div class="contentBody mb40">
			<ul class="tab_s3">
				<li @click="fnMoveMainTab('dashBoard')"><a class="width120">대시보드</a></li>
				<li @click="fnMoveMainTab('info')"><a class="width120">기본정보</a></li>
				<li @click="fnMoveMainTab('member')"><a class="width120">멤버관리</a></li>
				<li @click="fnMoveMainTab('chan-rcs')"><a class="width120">채널관리</a></li>
				<li @click="fnMoveMainTab('callbackManage')" class="active"><a class="width120">발신번호관리</a></li>
				<li @click="fnMoveMainTab('spam')"><a class="width120">스팸관리</a></li>
			</ul>			
		</div>
		
		<h4 class="mt40">발신번호 사전 등록</h4>
		<p class="font-size12 color3 mt30 inline-block"><i class="far fa-info-circle"></i> 발신번호 미등록 시 메시지가 정상적으로 전송되지 않을 수 있습니다.<br><i class="far fa-info-circle"></i> 발신번호는 문자발송장비 사용자의 최대 3배까지만 등록할 수 있습니다.</p>

		<ul class="tabStyle tab6 bgColor_tapGray mt30">
			<li @click="fnMoveMainTab('callbackPreReg')"><a style="width:23%">발신번호 인증 등록</a></li>
			<li @click="fnMoveMainTab('callbackPreRegEx')" class="active"><a style="width:23%">사전 등록 예외 대상 사업자로 등록</a></li>
		</ul>

		<p class="font-size12 color3 mt10"><i class="far fa-info-circle"></i> 웹발송방식 및 서버발송방식을 통한 문자 발송 시 모두 이용 가능합니다.</p>

		<div class="row bgColor_f1 row-no-margin mt20">
			<div class="col-xs-3" style="border-right:1px solid #fff">
				<h4 class="text-left ml40 line-height2em" style="padding:55% 0">위법한 문자를 발송한 적이<br>없는 사업자<br>(스팸 문자 미발송 사업자)</h4>
			</div>
			<div class="col-xs-6" style="border-right:1px solid #fff">					
				<div class="of_h" style="padding:55px 20px">
					<p class="font-size14 color4 line-height2em">사기, 도박, 불법의약품 및 성매매알선 등 위법한 영리목적의 광고성 정보를 발송하지 않는 자에 한하여 KISA에서 확인서를 발급받은 사업자</p>
					<p class="font-size14 color4 mt20 line-height2em">[신청절차]<br>&nbsp;&nbsp;- 한국인터넷진흥원(이하 KISA)확인서 발급요청(요청서 작성, 첨부01 참고)→KISA 심사후 결과 통보(1주일 예상) <br>&nbsp;&nbsp; → 예외사업자 등록 완료(1년간만 유효, 1년 이후 갱신필요)</p>	
					<p class="font-size14 color4 mt20 line-height2em">[제출서류]<br>&nbsp;&nbsp; - 신청서 1부 <a @click="fnReg" class="btnStyle8 mr5 ml10">다운로드 </a></p>	
				</div>
			</div>	
			<div class="col-xs-3 text-center" style="padding:9% 0">
				<a @click="fnReg('spam')" class="btnStyle4 white font13 width204 mt10" data-toggle="modal" data-target="#Register">발신번호 예외사업자 등록 요청</a>
			</div>
		</div>

		<div class="row bgColor_f1 row-no-margin mt10">
			<div class="col-xs-3" style="border-right:1px solid #fff">
				<h4 class="text-left ml40 line-height2em" style="padding:32% 0">발신번호 사전등록<br>시스템을 갖춘 사업자</h4>
			</div>
			<div class="col-xs-6" style="border-right:1px solid #fff">					
				<div class="of_h" style="padding:40px 20px">
					<p class="font-size14 color4 line-height2em">[제출서류]<br>&nbsp;&nbsp; - 자체 발신번호 시스템 구축 사실 공문 1부<a @click="fnReg" class="btnStyle8 mr5 ml10">다운로드 </a><br>&nbsp;&nbsp; - 발신번호 사전등록 시스템 설계화면<a @click="fnReg" class="btnStyle8 mr5 ml10">다운로드 </a><br>&nbsp;&nbsp; - 발신번호 사전등록 화면<a @click="fnReg" class="btnStyle8 mr5 ml10">다운로드 </a><br>&nbsp;&nbsp; - 미등록 발신번호 차단처리 화면<a @click="fnReg" class="btnStyle8 mr5 ml10">다운로드 </a></p>	
				</div>
			</div>	
			<div class="col-xs-3 text-center" style="padding:6% 0">
				<a @click="fnReg('system')" class="btnStyle4 white font13 width204 mt10" data-toggle="modal" data-target="#Register">발신번호 예외사업자 등록 요청</a>
			</div>
		</div>

		<div class="row bgColor_f1 row-no-margin mt10">
			<div class="col-xs-3" style="border-right:1px solid #fff">
				<h4 class="text-left ml40 line-height2em" style="padding:44% 0">공공기관</h4>
			</div>
			<div class="col-xs-6" style="border-right:1px solid #fff">					
				<div class="of_h" style="padding:40px 20px">
					<p class="font-size14 color4 line-height2em">국가, 지방자치단체 또는 [공공기관의 운영에 관한 법룔]에 따른 공공기관등에서 공익 목적으로 문자메시지를 발송하는 경우<br>[공공기관의 운영에 관한 법률]에 따른 공공기관 316개에 해당되는 사업자<br>(http://www.alio.go.kr/management.do?p=public02 에서 확인 가능)<br>[제출서류]<br>&nbsp;&nbsp;- 고유번호증(고유번호증이 없는 공공기관의 경우 사업자등록증)<a @click="fnReg" class="btnStyle8 mr5 ml10">다운로드 </a></p>	
				</div>
			</div>	
			<div class="col-xs-3 text-center" style="padding:5.5% 0">
				<a @click="fnReg('government')" class="btnStyle4 white font13 width204 mt10" data-toggle="modal" data-target="#Register">발신번호 예외사업자 등록 요청</a>
			</div>
		</div>

		<div class="row bgColor_f1 row-no-margin mt10">
			<div class="col-xs-3" style="border-right:1px solid #fff">
				<h4 class="text-left ml60 line-height2em" style="padding:61% 0">특수부가통신사업자</h4>
			</div>
			<div class="col-xs-6" style="border-right:1px solid #fff">					
				<div class="of_h" style="padding:40px 20px">
					<p class="font-size14 color4 line-height2em">- 중앙전파관리소에서 지정한 부가통신사업 등록서류<br>&nbsp;&nbsp; (서류 종류 및 제출 방법에 대해서는 중앙전파관리소 홈페이지 http://www.crmo.go.kr/administ/brdcstadm/specialaddedSMS.do 참고)<br>- 중앙전파관리소로부터 특수부가통신사업자로 등록 완료된 리스트를 전달받아 등록 처리됨.<br>&nbsp;&nbsp; 단, 정기적으로 전달되지 않으므로 당사 홈페이지에서 예외사업자로 적용되었는지 반드시 확인 필요<br>[제출서류]<br>- 특수부가통신사업자 등록증 1부<a @click="fnReg" class="btnStyle8 mr5 ml10">다운로드 </a></p>	
				</div>
			</div>	
			<div class="col-xs-3 text-center" style="padding:7.5% 0">
				<a @click="fnReg('special')" class="btnStyle4 white font13 width204 mt10" data-toggle="modal" data-target="#Register">발신번호 예외사업자 등록 요청</a>
			</div>
		</div>
			
		<div class="row bgColor_f1 row-no-margin mt10">
			<div class="text-center mt40">
			<a @click="fnMoveMainTab('callbackManage')" class="btnStyle3 white font14" data-dismiss="modal">목록</a>						
			</div>
		</div>
		<p class="font-size12 color3 mt30 inline-block"><i class="far fa-info-circle"></i>U+ Biz SMS 고객센터 : 이메일 smshelp@lguplus.co.kr, 팩스 02) 6919-1000<br><i class="far fa-info-circle"></i>사전 등록 예외 사업자는 서버 전송 시에만 적용됩니다. 웹 전송 시에는 등록된 발신번호가 없으면 전송이 실패되오니 유의하시기 바랍니다.</p>
		<!-- //본문 -->

		<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
	</article>
    <!-- 이미 사전등록 예외로 등록되어있는 경우 -->
    <article v-if="preRegYn">
		<div class="contentHeader mb20">
			<h2>비트큐브 프로젝트</h2>
		</div>

		<!-- 본문 -->
		<div class="contentBody mb40">
			<ul class="tab_s3">
				<li @click="fnMoveMainTab('dashBoard')"><a class="width120">대시보드</a></li>
				<li @click="fnMoveMainTab('info')"><a class="width120">기본정보</a></li>
				<li @click="fnMoveMainTab('member')"><a class="width120">멤버관리</a></li>
				<li @click="fnMoveMainTab('chan-rcs')"><a class="width120">채널관리</a></li>
				<li @click="fnMoveMainTab('callbackManage')" class="active"><a class="width120">발신번호관리</a></li>
				<li @click="fnMoveMainTab('spam')"><a class="width120">스팸관리</a></li>
			</ul>			
		</div>
			
		<h4 class="mt40">발신번호 사전 등록</h4>
		<p class="font-size12 color3 mt30 inline-block"><i class="far fa-info-circle"></i> 발신번호 미등록 시 메시지가 정상적으로 전송되지 않을 수 있습니다.<br><i class="far fa-info-circle"></i> 발신번호는 문자발송장비 사용자의 최대 3배까지만 등록할 수 있습니다.</p>

		<ul class="tabStyle tab6 bgColor_tapGray mt30">
			<li @click="fnMoveMainTab('callbackPreReg')"><a style="width:23%">발신번호 인증 등록</a></li>
				<li @click="fnMoveMainTab('callbackPreRegEx')" class="active"><a style="width:23%">사전 등록 예외 대상 사업자로 등록</a></li>
		</ul>

		<p class="font-size12 color3 mt10"><i class="far fa-info-circle"></i> 특수부가통신사업자 으로 사전등록 예외 사업자로 등록되어 있습니다.</p>

		<div class="row bgColor_f1 row-no-margin mt10">
			<div class="text-center mt40">
			<a @click="fnMoveMainTab('callbackManage')" class="btnStyle3 white font14" data-dismiss="modal">목록</a>						
			</div>
		</div>
		<p class="font-size12 color3 mt30 inline-block"><i class="far fa-info-circle"></i>U+ Biz SMS 고객센터 : 이메일 smshelp@lguplus.co.kr, 팩스 02) 6919-1000<br><i class="far fa-info-circle"></i>사전 등록 예외 사업자는 서버 전송 시에만 적용됩니다. 웹 전송 시에는 등록된 발신번호가 없으면 전송이 실패되오니 유의하시기 바랍니다.</p>
		<!-- //본문 -->

		<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
	</article>
  </div>
</template>


<script>
import api from '../service/projectApi'
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
    // 등록팝업창
    fnReg(reqType) {
      this.reqType = reqType;
      $("#preRegExPop").modal("show");
    },
    // 발신번호 예외사업지 등록 요청된 업체인지 확인
    fnCheckPreRegYn(){
      var params = {
        "corpId"  : tokenSvc.getToken().principal.corpId,
      } 
       
      api.checkPreRegYn(params).then(response =>{
        this.preRegYn = response.data.success;
      });
    }
  }
}
</script>