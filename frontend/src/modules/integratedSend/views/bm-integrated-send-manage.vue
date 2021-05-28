<template autocomplete="off">
  <div class="row row-no-margin">
    <div class="contentHeader">
      <h2>통합 발송</h2>
      <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="이용안내">이용안내 <i class="fal fa-book-open"></i></a>
    </div>

	<!-- 본문 -->
	<div class="row">
		<div class="phone3 inline-block" style="width:30%">
			<div class="phoneFixed">
				<!-- phoneWrap -->
				<!-- PUSH -->
		          <div v-if="previewMessageType == 'PUSH'" class="phoneWrap">
		            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
		            <div class="phoneTextWrap">
		              <div class="phoneText1">
		                <p v-if="fnIsEmpty(sendData.pushTitle)">제목</p>
		                <p v-else>{{sendData.pushTitle}}</p>
		              </div>
		              <div v-if="sendData.msgType == 'IMAGE' && fnIsEmpty(sendData.imgUrl)" class="phoneText2 mt10 text-center" style="padding:65px">
		                <i class="fas fa-image-polaroid" style="font-size:38px; color:#D5D5D5"></i>
		                <p class="font-size14 color3 mt15">이미지 영역</p>
		              </div>
		              <div v-if="sendData.msgType == 'IMAGE' && !fnIsEmpty(sendData.imgUrl)" class="phoneText2 mt10 text-center"
		                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+sendData.imgUrl+');'">
		              </div>
		              <div class="scroll-y">
		                <p v-if="sendData.msgKind != 'A' || (fnIsEmpty(sendData.pushContent) && fnIsEmpty(sendData.rcvblcNumber))" class="font-size14 color4 mt10">내용</p>
		                <p v-else class="font-size14 color4 mt10">
		                  <span v-html="$gfnCommonUtils.newLineToBr(sendData.pushContent)"></span>
		                  <br v-if="!fnIsEmpty(sendData.pushContent)"/>
		                  {{sendData.rcvblcNumber}}
		                </p>
		              </div>
		            </div>
		          </div>
		        <!--// PUSH -->

		        <!-- SMS -->
		          <div v-if="previewMessageType == 'SMS'" class="phoneWrap">
		            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
					<div class="phoneTextWrap">
						<div class="phoneText1 scroll-y2">
							<p>내용이 들어갑니다.</p>
						</div>
					</div>
		          </div>
		        <!--// SMS -->

		        <!-- MMS -->
		          <div v-if="previewMessageType == 'MMS'" class="phoneWrap">
		            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
					<div class="phoneTextWrap">
						<div class="phoneText1 scroll-y4">
							<p>내용이 들어갑니다.</p>
						</div>
					</div>
		          </div>
		        <!--// MMS -->
		        
		        <!-- RCS -->
		        	<!-- free -->
			          <div v-if="previewMessageType == 'FREE'" class="phoneWrap">
			          	<img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div class="phoneText1 scroll-y2">
								<p>내용이 들어갑니다.</p>
							</div>
						</div>
			          </div>
			    	<!-- //free -->
			    	
		        	<!-- description -->
			          <div v-if="previewMessageType == 'DESCRIPTION'" class="phoneWrap">
			            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div class="phoneText1 relative scroll-y4">
								<p><img src="common/images/phone_Icon10.png" alt="주문 아이콘"></p>
								<div class="scroll-y5">
									<p class="mt15 lc-1">{{name}}입니다.{{date}} 할인/특가<br>상품을 안내해 드립니다.<br>본 알림은 {{name}} 회원전용 서비스<br>입니다.</p>
								</div>
								<div class="absolute" style="bottom:25px; left:85px">
									<p class="text-center mt20" style="color:#69C8FF">사이트 연결</p>
								</div>
							</div>
						</div>
			          </div>
			    	<!-- //description -->			    	
			    			        
		        	<!-- style -->
			          <div v-if="previewMessageType == 'STYLE'" class="phoneWrap">
						<img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div class="phoneText1 of_h">
								<p><img src="common/images/phone_Icon08.png" alt="인증 아이콘"></p>
								<div class="scroll-y">
									<p class="mt15 lc-1">인증번호 안내</p>
								</div>
								<p class="mt10 lc-1 inline-block">인증번호</p>
								<p class="mt10 lc-1 inline-block float-right">{{desc_var1}}</p>
								<p class="text-center mt30" style="color:#69C8FF">홈페이지 연결하기</p>
								<p class="text-center mt10" style="color:#69C8FF">인증번호 복사하기</p>
							</div>
						</div>
			          </div>
			    	<!-- //style -->

			        <!-- SMS -->
			          <div v-if="previewMessageType == 'RCSSMS'" class="phoneWrap">
			            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div class="phoneText1 scroll-y2">
								<p>내용이 들어갑니다.</p>
							</div>
						</div>
			          </div>
			        <!--// SMS -->
		        			    				    			        
			        <!-- LMS -->
			          <div v-if="previewMessageType == 'RCSLMS'" class="phoneWrap">
			            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div class="phoneText1 scroll-y4">
								<p>내용이 들어갑니다.</p>
							</div>
						</div>
			          </div>
			        <!--// LMS -->
			        		        			    				    			        
		        	<!-- short -->
			          <div v-if="previewMessageType == 'SHORT'" class="phoneWrap">
			            <img src="common/images/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<!--<img src="common/images/cardThumImg2_2.png" alt="프리 템플릿">-->
							<div style="background:#fff; border-radius: 0 0 5px 5px; min-height:180px" class="pd20">
								<h5>타이틀영역</h5>
								<div class="scroll-y3">
									<p class="color6">내용입력</p>
								</div>								
							</div>
						</div>
			          </div>
			    	<!-- //short -->	
			    	<!-- tall -->
			          <div v-if="previewMessageType == 'TALL'" class="phoneWrap">
			            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<!--<img src="@/assets/images/common/cardThumImg2_1.png" alt="프리 템플릿">-->
							<div style="background:#fff; border-radius: 0 0 5px 5px; min-height:170px" class="pd20">
								<h5>타이틀영역</h5>
								<div class="scroll-y6">
									<p class="color6">내용입력</p>
								</div>								
							</div>
						</div>
			          </div>
			    	<!-- //tall -->

		        	<!-- cshort -->
					<div v-if="previewMessageType == 'CSHORT'" class="phoneWrap" style="height:660px">
						<img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneCardWrap">
							<p class="color000">[WEB발신] (광고)</p>
							<ul class="cardBxslider mt10">
								<li class="slide cardBox">
									<img src="common/images/cardThumImg.png" alt="카드 썸네일">
									<div class="relative">
										<div class="scroll-y" style="min-height:150px">
											<p class="color000 font-size13">타이틀 영역1</p>
											<p class="color3 mt5">내용입력</p>
										</div>
										<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:</p>
									</div>
								</li>
								<li class="slide cardBox">
									<img src="common/images/cardThumImg.png" alt="카드 썸네일">
									<div class="relative">
										<div class="scroll-y" style="min-height:150px">
											<p class="color000 font-size13">타이틀 영역2</p>
											<p class="color3 mt5">내용입력</p>
										</div>
										<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:</p>
									</div>
								</li>
								<li class="slide cardBox">
									<img src="common/images/cardThumImg.png" alt="카드 썸네일">
									<div class="relative">
										<div class="scroll-y" style="min-height:150px">
											<p class="color000 font-size13">타이틀 영역3</p>
											<p class="color3 mt5">내용입력</p>
										</div>
										<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:</p>
									</div>
								</li>
							</ul>
						</div>
					</div>
			    	<!-- //cshort -->	
			    	<!-- ctall -->
			          <div v-if="previewMessageType == 'CTALL'" class="phoneWrap" style="height:660px">
						<img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneCardWrap">
							<p class="color000">[WEB발신] (광고)</p>
							<ul class="cardBxslider mt10">
								<li class="slide cardBox">
									<img src="common/images/cardThumImg2_1.png" alt="카드 썸네일">
									<div class="relative">
										<div class="scroll-y6" style="min-height:140px">
											<p class="color000 font-size13">타이틀 영역1</p>
											<p class="color3 mt5">내용입력</p>
										</div>
										<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:</p>
									</div>
								</li>
								<li class="slide cardBox">
									<img src="common/images/cardThumImg2_1.png" alt="카드 썸네일">
									<div class="relative">
										<div class="scroll-y6" style="min-height:140px">
											<p class="color000 font-size13">타이틀 영역2</p>
											<p class="color3 mt5">내용입력</p>
										</div>
										<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:</p>
									</div>
								</li>
								<li class="slide cardBox">
									<img src="common/images/cardThumImg2_1.png" alt="카드 썸네일">
									<div class="relative">
										<div class="scroll-y6" style="min-height:140px">
											<p class="color000 font-size13">타이틀 영역3</p>
											<p class="color3 mt5">내용입력</p>
										</div>
										<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:</p>
									</div>
								</li>
							</ul>
						</div>
					</div>
			    	<!-- //ctall -->
			    				    	
		        	<!-- friendTalk -->
			          <div v-if="previewMessageType == 'FRIENDTALK'" class="phoneWrap">
			            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div class="phoneText1 scroll-y2">
								<p>내용이 들어갑니다.</p>
							</div>
						</div>
			          </div>
			    	<!-- //friendTalk -->
			    	
		        	<!-- noticeTalk -->
			          <div v-if="previewMessageType == 'NOTICETALK'" class="phoneWrap">
			            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div class="phoneText1 scroll-y2">
								<p>내용이 들어갑니다.</p>
							</div>
						</div>
			          </div>
			    	<!-- noticeTalk -->			    	
		        <!--// RCS -->
		                  		        
				<!-- //phoneWrap -->
				<div class="phone_04_btn6">
					<div>
						<a href="#self" class="btnStyle1 backBlack" title="Push">Push</a>
						<a href="#self" class="btnStyle1 backWhite" title="RCS">RCS</a>						
						<a href="#self" class="btnStyle1 backWhite" title="알림톡">알림톡</a>						
					</div>
					<div class="mt5">
						<a href="#self" class="btnStyle1 backWhite" title="친구톡">친구톡</a>						
						<a href="#self" class="btnStyle1 backWhite" title="SMS">SMS</a>						
						<a href="#self" class="btnStyle1 backWhite" title="MMS">MMS</a>						
					</div>
				</div>
			</div>
		</div>
		<div class="of_h inline-block vertical-top consoleCon" style="width:60%">
			<div class="of_h user-phone">
				<div class="float-left" style="width:24%">
					<h4>01  템플릿</h4>
				</div>
				<div class="float-left" style="width:76%">
					<div class="of_h">
						<div style="width:22%" class="float-left">
							<h5>텍스트  형,  정보 형</h5>
						</div>
						<div style="width:78%">
							<input type="radio" name="send" value="ALL" id="ALL" checked=""> <label for="ALL" class="mr30">ALL</label>
							<input type="radio" name="send" value="FCM" id="FCM"> <label for="FCM" class="mr30">FCM</label>
							<input type="radio" name="send" value="APNS" id="APNS"> <label for="APNS">APNS</label>
						</div>
						<div class="float-right" style="width:78%">
							<input type="text" class="inputStyle">
						</div>
					</div>													
				</div>						
			</div>
			
			<hr>

			<div class="of_h user-phone">
				<div class="float-left" style="width:24%">
					<h4>02  수신자 선택</h4>
				</div>
				<div class="float-left" style="width:76%">
					<div class="of_h">
						<div style="width:22%" class="height40 float-left">
							<h5>수신자 *</h5>
						</div>
						<div style="width:100%">
							<div>
								<input type="radio" name="Recipient" value="Directly" id="Directly" checked=""> <label for="Directly" class="mr30">수신자 직접입력</label>
								<input type="radio" name="Recipient" value="search" id="search"> <label for="search" class="mr30">주소록 검색</label>
								<input type="radio" name="Recipient" value="excel" id="excel"> <label for="excel" class="mr10">엑셀 업로드</label>
								<a href="#self" class="btnStyle1 backLightGray" title="샘플">샘플 <i class="far fa-arrow-to-bottom"></i></a>
							</div>
						</div>
					</div>
					<div class="of_h consolMarginTop">
						<div style="width:22%" class="float-left">
							<h5>APP_ID</h5>
						</div>
						<div class="of_h" style="width:78%">
							<input type="text" class="inputStyle" style="width:100%">
						</div>
					</div>
					<div class="of_h consolMarginTop">
						<div class="of_h float-right" style="width:78%">
							<div class="float-right" style="width:100%">
								<textarea class="textareaStyle height120" placeholder="Ex) [{“userId”, “phoneNumber”}] => 1명 발송 [{“aaa”, “0101231234”}]"></textarea>
							</div>
							<p class="float-right">발송 대상자 : 0명</p>
						</div>
					</div>													
				</div>
				
			</div>
			<hr>

			<div class="of_h user-phone">
				<div style="width:22%" class="float-left">
					<h4>03  발송옵션 선택</h4>
				</div>
				<div style="width:78%" class="float-left">
					<div class="of_h">
						<div class="float-left" style="width:22%">
							<h5>발송시간 *</h5>
						</div>								
						<div class="float-left" style="width:23%">
							<input type="radio" name="time" value="right" id="right" checked=""> <label for="right" class="mr20">즉시</label>
							<input type="radio" name="time" value="Reservation" id="Reservation"> <label for="Reservation">예약</label>								
						</div>
						<div class="float-left" style="width:18%">
							<input type="text" class="datepicker inputStyle" title="시작날짜 입력란">
						</div>
						<div class="float-right" style="width:36%">
							<input type="time" name="stime1" class="inputStyle" style="width:47%" value=""> : <input type="time" name="etime1" class="inputStyle" style="width:47%" value="">
						</div>
					</div>
					<div class="of_h consolMarginTop">
						<div style="width:22%" class="float-left">
							<h5>태그</h5>
						</div>
						<div class="of_h" style="width:78%">
							<input type="text" class="inputStyle" style="width:100%" placeholder="캠페인 ID를 입력해주세요.">
						</div>
					</div>													
				</div>	
			</div>

			<hr>

			<div class="of_h user-phone">
				<div class="float-left" style="width:22%">
					<h4>04  테스트 발송</h4>
				</div>
				<div class="float-left" style="width:78%">
					<p>모든 채널에 메시지를 보냅니다.</p>
					<a href="#self" class="btnStyle1 backLightGray consolMarginTop" title="테스트 발송">테스트 발송</a>											
				</div>
				
			</div>
			<div class="mt20 float-right">
				<a href="#self" class="btnStyle2 backRed float-left mr10" title="발송">발송</a>
				<a href="#self" class="btnStyle2 float-left" title="목록">목록</a>
			</div>
		</div>			
	</div>

	
	<!-- //본문 -->
  </div>
</template>

<script>
import integratedSendApi from "@/modules/integratedSend/service/integratedSendApi.js";

import TokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import Calendar from "@/components/Calendar.vue";


export default {
  name: 'integratedSendManage',

  components : {
    Calendar
  },
  props: {
    tmpltCodeP: {
      type: String,
      require: false,
      default: function() {
        return '';
      }
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '통합 템플릿 관리';
      }
    },
        
    rowData : {
      type: Object,
      require: false,
      default: function() {
        return {
                'checkedChannel':[]
              , 'tmpltCode':''
              , 'msgType':'BASE' //BASE: 텍스트, IMAGE:이미지
              , 'msgKind':'I' //정보성 I, 광고용: A
              , 'otherProjectUseYn':'Y' //Y:공용, N:전용
              , 'tmpltType':'M'  //tmpltType:통합발송 M, 스마트발송:S
              , 'tmpltTitle':'' //템플릿 명
              , 'pushHowToDenyReceipt':''
              , 'pushAppId': ''
              } 
      }
    }

  },
  data() {
    return {
 
    }
  },

  watch: {

  },

  mounted() {
    
    this.fnPushGetAppId();// v-show 사용으로 항상 살아있어서 mounted에 놓음
    this.fnKakaoGetChKey();//
    this.fnRcs0SelectCallbackList();
    this.fnSMSSelectCallbackList();
    this.fnSetIntegratedTemplateInfo();
  },

  methods: {

    fnPushGetAppId(){
      var params = {};
      messageApi.selectAppIdList(params).then(response => {
        var result = response.data;
        if(result.success) {
          this.aplnIdList = result.data;
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    

    fnKakaoGetChKey(){
      var params = {};
      messageApi.selectKkoSenderKeyList(params).then(response => {
        var result = response.data;
        if(result.success) {
          this.kakaoChKeyList = result.data;
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
        
   async fnSaveIntegratedTemplate(){
      var params = this.rowData;

      console.log("fnSaveIntegratedTemplate params : ",params);

      //유효성 검사
      //if(this.fnIsValid() == false) return;

      params.tmpltStatus = 'SAVE';
      //params.projectId = this.testProjectId;
      params.rcsTemplateTable = this.rcsTemplateTable;//rcs일경우 사용한 템플릿 번호를 가져간다.
      params.rcs9CardCount = this.rcs9CardCount;//캐러셀 short 카드 수
      params.rcs10CardCount = this.rcs10CardCount;//캐러셀 tall 카드 수
      params.kakaoTemplateTable = this.kakaoTemplateTable;//kakao일 경우 friendTalk, noticeTalk 구분값을 가져간다
      params.useYn = this.useYn; //사용여부

      await integratedTemplateApi.insertIntegratedTemplate(params).then(response =>{
        var result = response.data;
        if(result.success) {
          confirm.fnAlert(this.detailTitle, '저장 되었습니다.');
          this.$router.push('integratedTemplate')
        } else {
          confirm.fnAlert(this.detailTitle, result.message);
        }
      });
    },

    //유효성 체크
    fnIsValid(){
      if(!this.rowData.tmpltTitle){
        confirm.fnAlert(this.detailTitle, '템플릿명을 입력해주세요.');
        return false;
      }
      if(!this.rowData.msgType){
        confirm.fnAlert(this.detailTitle, '메시지타입을 선택해주세요.');
        return false;
      }
      if(!this.rowData.msgKind){
        confirm.fnAlert(this.detailTitle, '메시지구분을 선택해주세요.');
        return false;
      }
      if(!this.rowData.otherProjectUseYn){
        confirm.fnAlert(this.detailTitle, '타 프로젝트 사용여부를 선택해주세요.');
        return false;
      }
      if(this.rowData.checkedChannel.length == 0){
        confirm.fnAlert(this.detailTitle, '채널을 선택해주세요.');
        return false;
      }



      return true;
    },

    //임시저장 => 채널 설정관련 유효성 체크를  무시한다.
    save:function(){
      this.registYn = false;
      //유효성 검사
      if(this.fnIsValid() == false) return;
      
      eventBus.$on('callbackEventBus', this.fnSaveIntegratedTemplate);
      confirm.fnConfirm(this.detailTitle, "템플릿을 저장 하시겠습니까?", "확인");
    },

    //template 정보 조회
    fnSetIntegratedTemplateInfo(){
    	console.log("this.tmpltCode : "+this.tmpltCodeP);
      if(!this.$gfnCommonUtils.isEmpty(this.tmpltCodeP)){
        this.fnSelectIntegratedTemplateInfo();
      }
    },

    //템플릿 정보 조회
    fnSelectIntegratedTemplateInfo(){
      const params = {tmpltCode: this.tmpltCodeP};
      integratedTemplateApi.integratedTemplateInfo(params).then(response => {
        const result = response.data;
        if(result.success) {
          if(result.data != null && result.data.length > 0){
            let rtnData = result.data[0];
            
            this.rowData.tmpltCode 		= rtnData.tmpltCode;
            this.rowData.msgType 		= rtnData.msgType;
            this.rowData.msgKind 		= rtnData.msgKind;
            this.rowData.tmpltTitle 	= rtnData.tmpltTitle;
            this.rowData.otherProjectUseYn = rtnData.otherProjectUseYn;
            this.rowData.rcs100ImgInfoList.push({'fileId':rtnData.rcsBodyMedia, 'imgUrl':rtnData.rcsBodyMediaUrl});
            
            if(rtnData.rcsPrdType == 'sms'){
	 			this.rowData.rcsSMSContent 			= rtnData.rcsBodyDescription;
	            this.rowData.rcsSMSHowToDenyReceipt = rtnData.rcsFooter;   //무료수신거부번호
	            this.rowData.rcsSMSCallback 		= rtnData.rcsCallback; //발신번호
                        
	            if(rtnData.rcsButton0ButtonType){
	            	this.buttonSMSFlag = true;
	            	this.rowData.rcsSMSButtons.push({'buttonType':rtnData.rcsButton0ButtonType
	            	                                   , 'buttonName':rtnData.rcsButton0DisplayText
	            	                                   , 'buttonLink':rtnData.rcsButton0Title
	            	                                   , 'startDate':rtnData.rcsButton0StartTime
	            	                                   , 'endDate':rtnData.rcsButton0EndTime
	            	                                   , 'buttonLink1':rtnData.rcsButton0Description});
	            }
            }
            
          }
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
          //this.rowData = {imgUrl:'', buttonList:[]};
        }
      });
    },

    fnRcsSMSButtonSD(sltDate, params){
      this.rowData.rcsSMSButtons[params.idx].startDate = sltDate;
    },
    fnRcsSMSButtonED(sltDate, params){
      this.rowData.rcsSMSButtons[params.idx].endDate = sltDate;
    },
    //get 문자열 byte
    getByte(str) {
      return str
        .split('')
        .map(s => s.charCodeAt(0))
        .reduce((prev, c) => (prev + ((c === 10) ? 2 : ((c >> 7) ? 2 : 1))), 0);
    },

    fnSubString(str, sIdx, length){
      let shortStr = ''
      if(!this.$gfnCommonUtils.isEmpty(str)){
        shortStr = str.toString();
        if(shortStr.length > length){
          shortStr = shortStr.substring(sIdx, length) + '...  ';
        } else {
          shortStr = shortStr + '  ';
        }
      }
      return shortStr;
    },

    //빈값확인
    fnIsEmpty(str){
      if(str) return false;
      else return true
    },

    fnTextLength(title, sid, tid, len){
      var val = jQuery(sid).val();
      console.log("val : "+val.length);
      if(val.length > len){
        var msg = title + '의 최대 입력 길이는 ' + len + '입니다.';
        alert(msg);
        var temp = val.substr(0,len);
        console.log("temp : "+temp.length);
        jQuery(sid).val(temp);
        val = jQuery(sid).val();
      }
      var text = "("+val.length+" / "+len+")";
      if(tid) jQuery(tid).html(text);
    },

    
    fnByteLength(title, sid, tid, len){
        var value = jQuery(sid).val();
	    var nBytes = 0;
	    var term = 0;
	    for (var idx = 0; idx < value.length; idx++) {
	        term = 0;
	        var oneChar = escape(value.charAt(idx));
	        if ( oneChar.length == 1 ) {
	            term = 1;
	        } else if (oneChar.indexOf("%u") != -1) {
	            term = 2;
	        } else if (oneChar.indexOf("%") != -1) {
	            term = 1;
	        }
	        nBytes += term;
	        if(nBytes > len){
	        	var temp = value.substr(0,idx);
	        	jQuery(sid).val(temp);
	        	nBytes -= term;
	        }
	    }
	    var text = ""+nBytes+" / "+len+"Byte";
        if(tid) jQuery(tid).html(text);
    }
    
  }
}
</script>