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
		          <div v-if="channelType == 'Push'" class="phoneWrap">
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
		          <div v-if="channelType == 'smsMms'" v-show="previewMessageType == 'sms'" class="phoneWrap">
		            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
					<div class="phoneTextWrap">
						<div class="phoneText1 scroll-y2">
							<p>내용이 들어갑니다.</p>
						</div>
					</div>
		          </div>
		        <!--// SMS -->

		        <!-- MMS -->
		          <div v-if="channelType == 'smsMms'" v-show="previewMessageType == 'mms'" class="phoneWrap">
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
			          <div v-if="channelType == 'rcs'" v-show="previewMessageType == 'free'" class="phoneWrap">
			          	<img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div class="phoneText1 scroll-y2">
								<p>내용이 들어갑니다.</p>
							</div>
						</div>
			          </div>
			    	<!-- //free -->
			    	
		        	<!-- description -->
			          <div v-if="channelType == 'rcs'" v-show="previewMessageType == 'description'" class="phoneWrap">
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
			          <div v-if="channelType == 'rcs'" v-show="previewMessageType == 'style'" class="phoneWrap">
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
			          <div v-if="channelType == 'rcs'" v-show="previewMessageType == 'sms'" class="phoneWrap">
			            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div class="phoneText1 scroll-y2">
								<p>내용이 들어갑니다.</p>
							</div>
						</div>
			          </div>
			        <!--// SMS -->
		        			    				    			        
			        <!-- LMS -->
			          <div v-if="channelType == 'rcs'" v-show="previewMessageType == 'lms'" class="phoneWrap">
			            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div class="phoneText1 scroll-y4">
								<p>내용이 들어갑니다.</p>
							</div>
						</div>
			          </div>
			        <!--// LMS -->
			        		        			    				    			        
		        	<!-- short -->
			          <div v-if="channelType == 'rcs'" v-show="previewMessageType == 'short'" class="phoneWrap">
			            <img src="common/images/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<img src="common/images/cardThumImg2_2.png" alt="프리 템플릿">
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
			          <div vv-if="channelType == 'rcs'" v-show="previewMessageType == 'tall'" class="phoneWrap">
			            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<img src="@/assets/images/common/cardThumImg2_1.png" alt="프리 템플릿">
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
					<div vv-if="channelType == 'rcs'" v-show="previewMessageType == 'cShort'" class="phoneWrap" style="height:660px">
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
			          <div v-if="channelType == 'rcs'" v-show="previewMessageType == 'cTall'" class="phoneWrap" style="height:660px">
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
			          <div v-if="channelType == 'kakao'" v-show="previewMessageType == 'friendtalk'" class="phoneWrap">
			            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div class="phoneText1 scroll-y2">
								<p>내용이 들어갑니다.</p>
							</div>
						</div>
			          </div>
			    	<!-- //friendTalk -->
			    	
		        	<!-- noticeTalk -->
			          <div v-if="channelType == 'kakao'" v-show="previewMessageType == 'alimtalk'" class="phoneWrap">
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
					<div class="of_h consolMarginTop" v-for="(channel, index) in rowData.checkedChannel" :key="channel">
		              <div class="float-left" style="width:100%"><a class="btnStyle1 backWhite" :id="channel" style="min-width:auto; width:100%"  @click="enablePreview(channel)">{{channel}}</a></div>
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
						<div style="width:78%" v-if="channelType == 'Push'">
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
								<input type="radio" name="Recipient" value="Directly" id="Directly" checked="" @change="fnChgCuInputType()" @click="fnClickCuInputType"> <label for="Directly" class="mr30">수신자 직접입력</label>
								<input type="radio" name="Recipient" value="search" id="search" @change="fnChgCuInputType()" @click="fnClickCuInputType"> <label for="search" class="mr30">주소록 검색</label>
								<input type="radio" name="Recipient" value="excel" id="excel" @change="fnChgCuInputType()" @click="fnClickCuInputType"> <label for="excel" class="mr10">엑셀 업로드</label>
								<a @click="fnExcelTmplteDownLoad" class="btnStyle1 backLightGray" title="샘플">샘플 <i class="far fa-arrow-to-bottom"></i></a>
								<input ref="excelFile" type="file" style="display:none;">
							</div>
						</div>
					</div>
					<div class="of_h consolMarginTop" v-if="channelType == 'Push'">
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
import integratedTemplateApi from "@/modules/integratedTemplate/service/integratedTemplateApi.js";
//import TokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
//import Calendar from "@/components/Calendar.vue";


export default {
  name: 'integratedSendManage',

  components : {
    //Calendar
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
              

              , 'rcsSMSsHowToDenyReceipt':''
              , 'rcsLMSHowToDenyReceipt':''
              , 'rcsShortHowToDenyReceipt':''
              , 'rcsTallHowToDenyReceipt':''
              , 'rcs9HowToDenyReceipt':''
              , 'rcs10HowToDenyReceipt':''

              , 'pushImgInfo':{} //Push이미지정보  //하나의 이미지만 들어감..그래서 {}

              , 'rcsShortImgInfoList':[] //RCS 세로형 short 이미지정보
              , 'rcsTallImgInfoList':[] //RCS 세로형 tall 이미지정보

              , 'rcs90ImgInfoList':[] //RCS 캐러셀 카드1 이미지정보
              , 'rcs91ImgInfoList':[] //RCS 캐러셀 카드2 이미지정보
              , 'rcs92ImgInfoList':[] //RCS 캐러셀 카드3 이미지정보
              , 'rcs93ImgInfoList':[] //RCS 캐러셀 카드4 이미지정보
              , 'rcs94ImgInfoList':[] //RCS 캐러셀 카드5 이미지정보
              , 'rcs95ImgInfoList':[] //RCS 캐러셀 카드6 이미지정보

              , 'rcs100ImgInfoList':[] //RCS 캐러셀 카드1 이미지정보
              , 'rcs101ImgInfoList':[] //RCS 캐러셀 카드2 이미지정보
              , 'rcs102ImgInfoList':[] //RCS 캐러셀 카드3 이미지정보
              , 'rcs103ImgInfoList':[] //RCS 캐러셀 카드4 이미지정보
              , 'rcs104ImgInfoList':[] //RCS 캐러셀 카드5 이미지정보
              , 'rcs105ImgInfoList':[] //RCS 캐러셀 카드6 이미지정보

              , 'rcs90Buttons':[] //button 은 초기에 숨겨져있고, 입력부분없이 제목만 출력
              , 'rcs91Buttons':[]
              , 'rcs92Buttons':[]
              , 'rcs93Buttons':[]
              , 'rcs94Buttons':[]
              , 'rcs95Buttons':[]

              , 'rcs100Buttons':[] //button 은 초기에 숨겨져있고, 입력부분없이 제목만 출력
              , 'rcs101Buttons':[]
              , 'rcs102Buttons':[]
              , 'rcs103Buttons':[]
              , 'rcs104Buttons':[]
              , 'rcs105Buttons':[]

              , 'rcsSMSButtons':[]
              , 'rcsLMSButtons':[]
              , 'rcsShortButtons':[] //button 은 초기에 숨겨져있고, 입력부분없이 제목만 출력
              , 'rcsTallButtons':[]

			  , 'friendTalkButtons':[] //kakao friend talk 버튼정보
			  , 'friendTalkButtonsSize': 5 //kakao friend talk 버튼 최대 갯수
              , 'friendTalkImgInfo':{} //kakao friend talk 이미지정보
              
              
              , 'smsImgInfoList':[] //sms/mms이미지정보
              
              , 'smsSendType': 'S'  //sms/mms 발송유형
              } 
      }
    }

  },
  data() {
    return {
      channelType:'',  		//push/rcs/kakao/smsmms,  preview를 enable/diable시킨다
	  previewMessageType:'', 	//채널안의 tab(rcs의 경우 free, short, tall...)들에 대한 preview를 show한다.
	  
	  
	  
      channelTab: 9,  //채널설정 Push/RCS/카카오톡/SMSMMS 탭 show/hide
      rcsTemplateTable: 0, //채널설정 RCS탭 내 템플릿 구분 //저장등록시 유효성 체크 대상으로 사용
      rcsTemplateTableChecked: 0, //채널설정 RCS탭 내 템플릿 구분 //v-model이용 radio 선택표시용으로 사용 //rcsTemplateTable로 사용하려 했으나 작동하지 않음
      kakaoTemplateTable: 0, //채널설정 kakao내 템플릿 구분
      smsTemplateTable: 0, //채널설정 SMS/MMS내 구분
      cShortTab:0, //캐러셀 cshort 탭변경에 따른 미리보기 변경용
      cTallTab:0, //캐러셀 ctall 탭변경에 따른 미리보기 변경용
      checkedPush: false,   //채널선택에서 push 선택여부
      checkedRCS: false,    //채널선택에서 rcs 선택여부
      checkedKakao: false,  //채널선택에서 kakaotalk 선택여부
      checkedSmsMms: false, //채널선택에서 sms/mms 선택여부
      aplnIdList: {}, //app_id selectbox설정용
      kakaoChKeyList: {}, //카카오채널키 selectbox설정용
      indexData:0,
      itemDatas:[],
      rcs0CallbackList: [], //rcs 프리템플릿 발신번호 리스트
      rcs1CallbackList: [], //rcs 서술 발신번호 리스트
      rcs2CallbackList: [], //rcs 스타일 발신번호 리스트
      rcsSMSCallbackList: [], //rcs sms 발신번호 리스트
      rcsLMSCallbackList: [], //rcs lsm 발신번호 리스트
      rcsShortCallbackList: [], //rcs short 발신번호 리스트
      rcsTallCallbackList: [], //rcs tall 발신번호 리스트

      rcs9CallbackList: [], //RCS 캐러셀 Short 카드 발신번호 리스트
      rcs10CallbackList: [], //RCS 캐러셀 Tall 카드 발신번호 리스트

      smsCallbackList: [], //smslms 발신번호 리스트

      detailTitle:'통합발송 템플릿',
      
      pushImgMngOpen : false, /* Push 이미지 */
      pushImgUploadOpen : false,
      pushUseCh : 'PUSH',
      pushImgLimitSize : 1,

      rcs90ImgMngOpen : false, /* RCS 캐러셀 Short 카드1 이미지 */
      rcs90ImgUploadOpen : false,
      rcs90UseCh : 'RCS',
      rcs90ImgLimitSize : 1,
      rcs91ImgMngOpen : false,
      rcs91ImgUploadOpen : false,
      rcs91UseCh : 'RCS',
      rcs91ImgLimitSize : 1,
      rcs92ImgMngOpen : false,
      rcs92ImgUploadOpen : false,
      rcs92UseCh : 'RCS',
      rcs92ImgLimitSize : 1,
      rcs93ImgMngOpen : false,
      rcs93ImgUploadOpen : false,
      rcs93UseCh : 'RCS',
      rcs93ImgLimitSize : 1,
      rcs94ImgMngOpen : false,
      rcs94ImgUploadOpen : false,
      rcs94UseCh : 'RCS',
      rcs94ImgLimitSize : 1,
      rcs95ImgMngOpen : false,
      rcs95ImgUploadOpen : false,
      rcs95UseCh : 'RCS',
      rcs95ImgLimitSize : 1,
      rcs9CardCount: 3, // rcs탭 캐러셀 Short 카드탭 갯수  //카드탭은 갯수만큼 service단에서 배열로 저장되어야 한다.

      button90Flag: false, //버튼 추가 여부
      button91Flag: false,
      button92Flag: false,
      button93Flag: false,
      button94Flag: false,
      button95Flag: false,

      rcs100ImgMngOpen : false, /* RCS 캐러셀 Tall 카드1 이미지 */
      rcs100ImgUploadOpen : false,
      rcs100UseCh : 'RCS',
      rcs100ImgLimitSize : 1,
      rcs101ImgMngOpen : false,
      rcs101ImgUploadOpen : false,
      rcs101UseCh : 'RCS',
      rcs101ImgLimitSize : 1,
      rcs102ImgMngOpen : false,
      rcs102ImgUploadOpen : false,
      rcs102UseCh : 'RCS',
      rcs102ImgLimitSize : 1,
      rcs103ImgMngOpen : false,
      rcs103ImgUploadOpen : false,
      rcs103UseCh : 'RCS',
      rcs103ImgLimitSize : 1,
      rcs104ImgMngOpen : false,
      rcs104ImgUploadOpen : false,
      rcs104UseCh : 'RCS',
      rcs104ImgLimitSize : 1,
      rcs105ImgMngOpen : false,
      rcs105ImgUploadOpen : false,
      rcs105UseCh : 'RCS',
      rcs105ImgLimitSize : 1,
      rcs10CardCount: 3, // rcs탭 캐러셀 Tall 카드탭 갯수  //카드탭은 갯수만큼 service단에서 배열로 저장되어야 한다.

      button100Flag: false, //버튼 추가 여부
      button101Flag: false,
      button102Flag: false,
      button103Flag: false,
      button104Flag: false,
      button105Flag: false,

      rcsShortImgMngOpen : false, /* RCS 세로형 short 이미지 */
      rcsShortImgUploadOpen : false,
      rcsShortUseCh : 'RCS',
      rcsShortImgLimitSize : 1,

      rcsTallImgMngOpen : false, /* RCS 세로형 tall 이미지 */
      rcsTallImgUploadOpen : false,
      rcsTallUseCh : 'RCS',
      rcsTallImgLimitSize : 1,
      
      friendTalkImgMngOpen : false, /* MMS 이미지 */
      friendTalkImgUploadOpen : false,
      friendTalkUseCh : 'friendtalk',
      friendTalkImgLimitSize : 1, 
      
      friendTalkSenderKeyType: 'NOMAL',  //NOMAL, GROUP //friendTalk 발신프로필 그룹
      friendTalkSenderKeyList: [],
      
      smsImgMngOpen : false, /* MMS 이미지 */
      smsImgUploadOpen : false,
      smsUseCh : 'MMS',
      smsImgLimitSize : 2, /* 2개까지 가능 */
      
      buttonSMSFlag: false,
      buttonLMSFlag: false,
      buttonShortFlag: false,
      buttonTallFlag: false,
      buttonFriendTalkFlag: false,
      
      smsButtonsMaxLen: 0, 		//start, endDate의 ID값을 유일하게 잡기위해 설정
	  lmsButtonsMaxLen: 0, 		//start, endDate의 ID값을 유일하게 잡기위해 설정
	  rcs90ButtonsMaxLen: 0, 	//start, endDate의 ID값을 유일하게 잡기위해 설정
	  rcs91ButtonsMaxLen: 0, 	//start, endDate의 ID값을 유일하게 잡기위해 설정
	  rcs92ButtonsMaxLen: 0, 	//start, endDate의 ID값을 유일하게 잡기위해 설정
	  rcs93ButtonsMaxLen: 0, 	//start, endDate의 ID값을 유일하게 잡기위해 설정
	  rcs94ButtonsMaxLen: 0, 	//start, endDate의 ID값을 유일하게 잡기위해 설정
	  rcs95ButtonsMaxLen: 0, 	//start, endDate의 ID값을 유일하게 잡기위해 설정
	  rcs100ButtonsMaxLen: 0, 	//start, endDate의 ID값을 유일하게 잡기위해 설정
	  rcs101ButtonsMaxLen: 0, 	//start, endDate의 ID값을 유일하게 잡기위해 설정
	  rcs102ButtonsMaxLen: 0, 	//start, endDate의 ID값을 유일하게 잡기위해 설정
	  rcs103ButtonsMaxLen: 0, 	//start, endDate의 ID값을 유일하게 잡기위해 설정
	  rcs104ButtonsMaxLen: 0, 	//start, endDate의 ID값을 유일하게 잡기위해 설정
	  rcs105ButtonsMaxLen: 0, 	//start, endDate의 ID값을 유일하게 잡기위해 설정
	  rcsShortButtonsMaxLen: 0, //start, endDate의 ID값을 유일하게 잡기위해 설정
	  rcsTallButtonsMaxLen: 0, 	//start, endDate의 ID값을 유일하게 잡기위해 설정
      friendTalkButtonsMaxLen: 0, //start, endDate의 ID값을 유일하게 잡기위해 설정

      useYn : 'Y'

    }
  },


  watch: {

  },

  mounted() {
    
    this.fnSetIntegratedTemplateInfo();
  },

  methods: {
        
   async fnIntegratedSend(){
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
            this.rowData.checkedChannel = rtnData.chTypeList.split(',');
            this.rowData.tmpltCode 		= rtnData.tmpltCode;
            this.rowData.msgType 		= rtnData.msgType;
            this.rowData.msgKind 		= rtnData.msgKind;
            this.rowData.tmpltTitle 	= rtnData.tmpltTitle;
            this.rowData.otherProjectUseYn = rtnData.otherProjectUseYn;
            
//PUSH DATA SET            
            this.rowData.pushHowToDenyReceipt = rtnData.pushHowToDenyReceipt;
            this.rowData.pushContent = rtnData.pushMsg;
            this.rowData.pushAppId = rtnData.pushAppId;
            this.rowData.pushTitle = rtnData.pushTitle;
            this.rowData.pushImgInfo.fileId = rtnData.pushFileId;
            this.rowData.pushImgInfo.imgUrl = rtnData.pushChImgUrl;
            this.rowData.pushSend = rtnData.pushSendType;
            this.rowData.pushHowToDenyReceipt = rtnData.pushRcvblcInput;//수신거부방법
            
//RCS DATA SET            
            console.log(">>>>>>>>>>>>>>>>rtnData.rcsPrdType : "+rtnData.rcsPrdType);
            if(rtnData.rcsPrdType == 'free'){
				this.rcsTemplateTable = 0;
				this.rcsTemplateTableChecked = 0;
			}else if(rtnData.rcsPrdType == 'description'){
				this.rcsTemplateTable = 1;
				this.rcsTemplateTableChecked = 1;
			}else if(rtnData.rcsPrdType == 'style'){
				this.rcsTemplateTable = 2;
				this.rcsTemplateTableChecked = 2;
			}else if(rtnData.rcsPrdType == 'sms'){
				this.rcsTemplateTable = 3;
				this.rcsTemplateTableChecked = 3;
			}else if(rtnData.rcsPrdType == 'lms'){
				this.rcsTemplateTable = 4;
				this.rcsTemplateTableChecked = 4;
			}else if(rtnData.rcsPrdType == 'short'){
				this.rcsTemplateTable = 5;
				this.rcsTemplateTableChecked = 5;
			}else if(rtnData.rcsPrdType == 'tall'){
				this.rcsTemplateTable = 6;
				this.rcsTemplateTableChecked = 6;
			}else if(rtnData.rcsPrdType == 'cShort'){
				this.rcsTemplateTable = 9;
				this.rcsTemplateTableChecked = 9;
			}else if(rtnData.rcsPrdType == 'cTall'){
				this.rcsTemplateTable = 10;
				this.rcsTemplateTableChecked = 10;
			}
			console.log(">>>>>>>>>>>>>>>>this.rcsTemplateTable : "+this.rcsTemplateTable);
			
           
            
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
            

            
            if(rtnData.rcsPrdType == 'lms'){
	            this.rowData.rcsLMSTitle			= rtnData.rcsBodyTitle;
	            this.rowData.rcsLMSContent 			= rtnData.rcsBodyDescription;
	            this.rowData.rcsLMSHowToDenyReceipt = rtnData.rcsFooter;   //무료수신거부번호
	            this.rowData.rcsLMSCallback 		= rtnData.rcsCallback; //발신번호
                        
	            if(rtnData.rcsButton0ButtonType){
	            	this.buttonLMSFlag = true;
	            	this.rowData.rcsLMSButtons.push({'buttonType':rtnData.rcsButton0ButtonType
	            	                                   , 'buttonName':rtnData.rcsButton0DisplayText
	            	                                   , 'buttonLink':rtnData.rcsButton0Title
	            	                                   , 'startDate':rtnData.rcsButton0StartTime
	            	                                   , 'endDate':rtnData.rcsButton0EndTime
	            	                                   , 'buttonLink1':rtnData.rcsButton0Description});
	            }
            
	            if(rtnData.rcsButton1ButtonType){
	            	this.buttonLMSFlag = true;
	            	this.rowData.rcsLMSButtons.push({'buttonType':rtnData.rcsButton1ButtonType
	            	                                   , 'buttonName':rtnData.rcsButton1DisplayText
	            	                                   , 'buttonLink':rtnData.rcsButton1Title
	            	                                   , 'startDate':rtnData.rcsButton1StartTime
	            	                                   , 'endDate':rtnData.rcsButton1EndTime
	            	                                   , 'buttonLink1':rtnData.rcsButton1Description});
	            }

	            if(rtnData.rcsButton2ButtonType){
	            	this.buttonLMSFlag = true;
	            	this.rowData.rcsLMSButtons.push({'buttonType':rtnData.rcsButton2ButtonType
	            	                                   , 'buttonName':rtnData.rcsButton2DisplayText
	            	                                   , 'buttonLink':rtnData.rcsButton2Title
	            	                                   , 'startDate':rtnData.rcsButton2StartTime
	            	                                   , 'endDate':rtnData.rcsButton2EndTime
	            	                                   , 'buttonLink1':rtnData.rcsButton2Description});
	            }
            }
            

            
            if(rtnData.rcsPrdType == 'short'){
	            this.rowData.rcsShortTitle				= rtnData.rcsBodyTitle;
	            this.rowData.rcsShortContent 			= rtnData.rcsBodyDescription;
	            this.rowData.rcsShortHowToDenyReceipt 	= rtnData.rcsFooter;   //무료수신거부번호
	            this.rowData.rcsShortCallback 			= rtnData.rcsCallback; //발신번호
	            
	            this.rowData.rcsShortImgInfoList.push({'fileId':rtnData.rcsBodyMedia, 'imgUrl':rtnData.rcsBodyMediaUrl});    
	                    
	            if(rtnData.rcsButton0ButtonType){
	            	this.buttonShortFlag = true;
	            	this.rowData.rcsShortButtons.push({'buttonType':rtnData.rcsButton0ButtonType
	            	                                   , 'buttonName':rtnData.rcsButton0DisplayText
	            	                                   , 'buttonLink':rtnData.rcsButton0Title
	            	                                   , 'startDate':rtnData.rcsButton0StartTime
	            	                                   , 'endDate':rtnData.rcsButton0EndTime
	            	                                   , 'buttonLink1':rtnData.rcsButton0Description});
	            }
	            if(rtnData.rcsButton1ButtonType){
	            	this.buttonTallFlag = true;
	            	this.rowData.rcsShortButtons.push({'buttonType':rtnData.rcsButton1ButtonType
	            	                                   , 'buttonName':rtnData.rcsButton1DisplayText
	            	                                   , 'buttonLink':rtnData.rcsButton1Title
	            	                                   , 'startDate':rtnData.rcsButton1StartTime
	            	                                   , 'endDate':rtnData.rcsButton1EndTime
	            	                                   , 'buttonLink1':rtnData.rcsButton1Description});
	            }
			}


            
            if(rtnData.rcsPrdType == 'tall'){
            	this.rowData.rcsTallTitle				= rtnData.rcsBodyTitle;
	            this.rowData.rcsTallContent 			= rtnData.rcsBodyDescription;
	            this.rowData.rcsTallHowToDenyReceipt 	= rtnData.rcsFooter;   //무료수신거부번호
	            this.rowData.rcsTallCallback 			= rtnData.rcsCallback; //발신번호
	            
	            this.rowData.rcsTallImgInfoList.push({'fileId':rtnData.rcsBodyMedia, 'imgUrl':rtnData.rcsBodyMediaUrl});
            
	            if(rtnData.rcsButton0ButtonType){
	            	this.buttonTallFlag = true;
	            	this.rowData.rcsTallButtons.push({'buttonType':rtnData.rcsButton0ButtonType
	            	                                   , 'buttonName':rtnData.rcsButton0DisplayText
	            	                                   , 'buttonLink':rtnData.rcsButton0Title
	            	                                   , 'startDate':rtnData.rcsButton0StartTime
	            	                                   , 'endDate':rtnData.rcsButton0EndTime
	            	                                   , 'buttonLink1':rtnData.rcsButton0Description});
	            }            
	            if(rtnData.rcsButton1ButtonType){
	            	this.buttonTallFlag = true;
	            	this.rowData.rcsTallButtons.push({'buttonType':rtnData.rcsButton1ButtonType
	            	                                   , 'buttonName':rtnData.rcsButton1DisplayText
	            	                                   , 'buttonLink':rtnData.rcsButton1Title
	            	                                   , 'startDate':rtnData.rcsButton1StartTime
	            	                                   , 'endDate':rtnData.rcsButton1EndTime
	            	                                   , 'buttonLink1':rtnData.rcsButton1Description});
	            }

			}

            if(rtnData.rcsPrdType == 'cShort'){
            console.log(">>>>>>>>>>>>>>>>>>>>> cShort");
            console.log(">>>>>>>>>>>>>>>>>>>>> cShort");
            console.log(">>>>>>>>>>>>>>>>>>>>> cShort");
	            this.rowData.rcs9CardCount				= rtnData.rcs9CardCount;
	            this.rowData.rcs9HowToDenyReceipt 		= rtnData.rcsFooter;   //무료수신거부번호
	            this.rowData.rcs9Callback 				= rtnData.rcsCallback; //발신번호
	            
	            this.rowData.rcs90Title					= rtnData.rcsBodyTitle;
	            this.rowData.rcs90Content				= rtnData.rcsBodyDescription;
	            this.rowData.rcs91Title					= rtnData.rcs1BodyTitle;
	            this.rowData.rcs91Content				= rtnData.rcs1BodyDescription;
	            this.rowData.rcs92Title					= rtnData.rcs2BodyTitle;
	            this.rowData.rcs92Content				= rtnData.rcs2BodyDescription;
	            this.rowData.rcs93Title					= rtnData.rcs3BodyTitle;
	            this.rowData.rcs93Content				= rtnData.rcs3BodyDescription;
	            this.rowData.rcs94Title					= rtnData.rcs4BodyTitle;
	            this.rowData.rcs94Content				= rtnData.rcs4BodyDescription;
	            this.rowData.rcs95Title					= rtnData.rcs5BodyTitle;
	            this.rowData.rcs95Content				= rtnData.rcs5BodyDescription;
	            
	            this.rowData.rcs90ImgInfoList.push({'fileId':rtnData.rcsBodyMedia, 'imgUrl':rtnData.rcsBodyMediaUrl});
	            this.rowData.rcs91ImgInfoList.push({'fileId':rtnData.rcs1BodyMedia, 'imgUrl':rtnData.rcs1BodyMediaUrl});
            	this.rowData.rcs92ImgInfoList.push({'fileId':rtnData.rcs2BodyMedia, 'imgUrl':rtnData.rcs2BodyMediaUrl});
	            this.rowData.rcs93ImgInfoList.push({'fileId':rtnData.rcs3BodyMedia, 'imgUrl':rtnData.rcs3BodyMediaUrl});
	            this.rowData.rcs94ImgInfoList.push({'fileId':rtnData.rcs4BodyMedia, 'imgUrl':rtnData.rcs4BodyMediaUrl});
	            this.rowData.rcs95ImgInfoList.push({'fileId':rtnData.rcs5BodyMedia, 'imgUrl':rtnData.rcs5BodyMediaUrl});
            
            	console.log(">>>>>>>>>>>>>>>>>>>>> rcsButton0ButtonType",rtnData.rcsButton0ButtonType);
	            if(rtnData.rcsButton0ButtonType){
	            	this.button90Flag = true;
	            	this.rowData.rcs90Buttons.push({'buttonType':rtnData.rcsButton0ButtonType
	            	                                   , 'buttonName':rtnData.rcsButton0DisplayText
	            	                                   , 'buttonLink':rtnData.rcsButton0Title
	            	                                   , 'startDate':rtnData.rcsButton0StartTime
	            	                                   , 'endDate':rtnData.rcsButton0EndTime
	            	                                   , 'startDateId':rtnData.rcsButton0StartTimeId
	            	                                   , 'endDateId':rtnData.rcsButton0EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcsButton0Description});
	            }      
	            
	            if(rtnData.rcsButton1ButtonType){
	            	this.button90Flag = true;
	            	this.rowData.rcs90Buttons.push({'buttonType':rtnData.rcsButton1ButtonType
	            	                                   , 'buttonName':rtnData.rcsButton1DisplayText
	            	                                   , 'buttonLink':rtnData.rcsButton1Title
	            	                                   , 'startDate':rtnData.rcsButton1StartTime
	            	                                   , 'endDate':rtnData.rcsButton1EndTime
	            	                                   , 'startDateId':rtnData.rcsButton1StartTimeId
	            	                                   , 'endDateId':rtnData.rcsButton1EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcsButton1Description});
	            }
	            console.log(">>>>>>>>>>>>>>>>>>>>> rcsButton1ButtonType",rtnData.rcsButton1ButtonType); 
	            if(rtnData.rcs1Button0ButtonType){
	            	this.button91Flag = true;
	            	this.rowData.rcs91Buttons.push({'buttonType':rtnData.rcs1Button0ButtonType
	            	                                   , 'buttonName':rtnData.rcs1Button0DisplayText
	            	                                   , 'buttonLink':rtnData.rcs1Button0Title
	            	                                   , 'startDate':rtnData.rcs1Button0StartTime
	            	                                   , 'endDate':rtnData.rcs1Button0EndTime
	            	                                   , 'startDateId':rtnData.rcs1Button0StartTimeId
	            	                                   , 'endDateId':rtnData.rcs1Button0EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcs1Button0Description});
	            }            
	            if(rtnData.rcs1Button1ButtonType){
	            	this.button91Flag = true;
	            	this.rowData.rcs91Buttons.push({'buttonType':rtnData.rcs1Button1ButtonType
	            	                                   , 'buttonName':rtnData.rcs1Button1DisplayText
	            	                                   , 'buttonLink':rtnData.rcs1Button1Title
	            	                                   , 'startDate':rtnData.rcs1Button1StartTime
	            	                                   , 'endDate':rtnData.rcs1Button1EndTime
	            	                                   , 'startDateId':rtnData.rcs1Button1StartTimeId
	            	                                   , 'endDateId':rtnData.rcs1Button1EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcs1Button1Description});
	            }
	            console.log(">>>>>>>>>>>>>>>>>>>>> rcs2Button0ButtonType",rtnData.rcs2Button0ButtonType); 
	            if(rtnData.rcs2Button0ButtonType){
	            	this.button92Flag = true;
	            	this.rowData.rcs92Buttons.push({'buttonType':rtnData.rcs2Button0ButtonType
	            	                                   , 'buttonName':rtnData.rcs2Button0DisplayText
	            	                                   , 'buttonLink':rtnData.rcs2Button0Title
	            	                                   , 'startDate':rtnData.rcs2Button0StartTime
	            	                                   , 'endDate':rtnData.rcs2Button0EndTime
	            	                                   , 'startDateId':rtnData.rcs2Button0StartTimeId
	            	                                   , 'endDateId':rtnData.rcs2Button0EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcs2Button0Description});
	            }            
	            if(rtnData.rcs2Button1ButtonType){
	            	this.button92Flag = true;
	            	this.rowData.rcs92Buttons.push({'buttonType':rtnData.rcs2Button1ButtonType
	            	                                   , 'buttonName':rtnData.rcs2Button1DisplayText
	            	                                   , 'buttonLink':rtnData.rcs2Button1Title
	            	                                   , 'startDate':rtnData.rcs2Button1StartTime
	            	                                   , 'endDate':rtnData.rcs2Button1EndTime
	            	                                   , 'startDateId':rtnData.rcs2Button1StartTimeId
	            	                                   , 'endDateId':rtnData.rcs2Button1EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcs2Button1Description});
	            }
	            
	            if(rtnData.rcs3Button0ButtonType){
	            	this.button93Flag = true;
	            	this.rowData.rcs93Buttons.push({'buttonType':rtnData.rcs3Button0ButtonType
	            	                                   , 'buttonName':rtnData.rcs3Button0DisplayText
	            	                                   , 'buttonLink':rtnData.rcs3Button0Title
	            	                                   , 'startDate':rtnData.rcs3Button0StartTime
	            	                                   , 'endDate':rtnData.rcs3Button0EndTime
	            	                                   , 'startDateId':rtnData.rcs3Button0StartTimeId
	            	                                   , 'endDateId':rtnData.rcs3Button0EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcs3Button0Description});
	            }            
	            if(rtnData.rcs3Button1ButtonType){
	            	this.button93Flag = true;
	            	this.rowData.rcs93Buttons.push({'buttonType':rtnData.rcs3Button1ButtonType
	            	                                   , 'buttonName':rtnData.rcs3Button1DisplayText
	            	                                   , 'buttonLink':rtnData.rcs3Button1Title
	            	                                   , 'startDate':rtnData.rcs3Button1StartTime
	            	                                   , 'endDate':rtnData.rcs3Button1EndTime
	            	                                   , 'startDateId':rtnData.rcs3Button1StartTimeId
	            	                                   , 'endDateId':rtnData.rcs3Button1EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcs3Button1Description});
	            }
	            
	            if(rtnData.rcs4Button0ButtonType){
	            	this.button94Flag = true;
	            	this.rowData.rcs94Buttons.push({'buttonType':rtnData.rcs4Button0ButtonType
	            	                                   , 'buttonName':rtnData.rcs4Button0DisplayText
	            	                                   , 'buttonLink':rtnData.rcs4Button0Title
	            	                                   , 'startDate':rtnData.rcs4Button0StartTime
	            	                                   , 'endDate':rtnData.rcs4Button0EndTime
	            	                                   , 'startDateId':rtnData.rcs4Button0StartTimeId
	            	                                   , 'endDateId':rtnData.rcs4Button0EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcs4Button0Description});
	            }            
	            if(rtnData.rcs4Button1ButtonType){
	            	this.button94Flag = true;
	            	this.rowData.rcs94Buttons.push({'buttonType':rtnData.rcs4Button1ButtonType
	            	                                   , 'buttonName':rtnData.rcs4Button1DisplayText
	            	                                   , 'buttonLink':rtnData.rcs4Button1Title
	            	                                   , 'startDate':rtnData.rcs4Button1StartTime
	            	                                   , 'endDate':rtnData.rcs4Button1EndTime
	            	                                   , 'startDateId':rtnData.rcs4Button1StartTimeId
	            	                                   , 'endDateId':rtnData.rcs4Button1EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcs4Button1Description});
	            }
	            
	            if(rtnData.rcs5Button0ButtonType){
	            	this.button95Flag = true;
	            	this.rowData.rcs95Buttons.push({'buttonType':rtnData.rcs5Button0ButtonType
	            	                                   , 'buttonName':rtnData.rcs5Button0DisplayText
	            	                                   , 'buttonLink':rtnData.rcs5Button0Title
	            	                                   , 'startDate':rtnData.rcs5Button0StartTime
	            	                                   , 'endDate':rtnData.rcs5Button0EndTime
	            	                                   , 'startDateId':rtnData.rcs5Button0StartTimeId
	            	                                   , 'endDateId':rtnData.rcs5Button0EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcs5Button0Description});
	            }            
	            if(rtnData.rcs5Button1ButtonType){
	            	this.button95Flag = true;
	            	this.rowData.rcs95Buttons.push({'buttonType':rtnData.rcs5Button1ButtonType
	            	                                   , 'buttonName':rtnData.rcs5Button1DisplayText
	            	                                   , 'buttonLink':rtnData.rcs5Button1Title
	            	                                   , 'startDate':rtnData.rcs5Button1StartTime
	            	                                   , 'endDate':rtnData.rcs5Button1EndTime
	            	                                   , 'startDateId':rtnData.rcs5Button1StartTimeId
	            	                                   , 'endDateId':rtnData.rcs5Button1EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcs5Button1Description});
	            }

			}
            

           if(rtnData.rcsPrdType == 'cTall'){
	            this.rowData.rcs10CardCount				= rtnData.rcs10CardCount;
	            this.rowData.rcs10HowToDenyReceipt 		= rtnData.rcsFooter;   //무료수신거부번호
	            this.rowData.rcs10Callback 				= rtnData.rcsCallback; //발신번호
	            
	            this.rowData.rcs100Title				= rtnData.rcsBodyTitle;
	            this.rowData.rcs100Content				= rtnData.rcsBodyDescription;
	            this.rowData.rcs101Title				= rtnData.rcs1BodyTitle;
	            this.rowData.rcs101Content				= rtnData.rcs1BodyDescription;
	            this.rowData.rcs102Title				= rtnData.rcs2BodyTitle;
	            this.rowData.rcs102Content				= rtnData.rcs2BodyDescription;
	            this.rowData.rcs103Title				= rtnData.rcs3BodyTitle;
	            this.rowData.rcs103Content				= rtnData.rcs3BodyDescription;
	            this.rowData.rcs104Title				= rtnData.rcs4BodyTitle;
	            this.rowData.rcs104Content				= rtnData.rcs4BodyDescription;
	            this.rowData.rcs105Title				= rtnData.rcs5BodyTitle;
	            this.rowData.rcs105Content				= rtnData.rcs5BodyDescription;
	            
	            console.log("imgUrl1 ["+rtnData.rcsBodyMediaUrl+"]");
	            console.log("imgUrl2 ["+rtnData.rcs1BodyMediaUrl+"]");
	            console.log("imgUrl3 ["+rtnData.rcs2BodyMediaUrl+"]");
	            this.rowData.rcs100ImgInfoList.push({'fileId':rtnData.rcsBodyMedia, 'imgUrl':rtnData.rcsBodyMediaUrl});
	            this.rowData.rcs101ImgInfoList.push({'fileId':rtnData.rcs1BodyMedia, 'imgUrl':rtnData.rcs1BodyMediaUrl});
	            this.rowData.rcs102ImgInfoList.push({'fileId':rtnData.rcs2BodyMedia, 'imgUrl':rtnData.rcs2BodyMediaUrl});
	            this.rowData.rcs103ImgInfoList.push({'fileId':rtnData.rcs3BodyMedia, 'imgUrl':rtnData.rcs3BodyMediaUrl});
	            this.rowData.rcs104ImgInfoList.push({'fileId':rtnData.rcs4BodyMedia, 'imgUrl':rtnData.rcs4BodyMediaUrl});
	            this.rowData.rcs105ImgInfoList.push({'fileId':rtnData.rcs5BodyMedia, 'imgUrl':rtnData.rcs5BodyMediaUrl});
            
            
	            if(rtnData.rcsButton0ButtonType){
	            	this.button100Flag = true;
	            	this.rowData.rcs100Buttons.push({'buttonType':rtnData.rcsButton0ButtonType
	            	                                   , 'buttonName':rtnData.rcsButton0DisplayText
	            	                                   , 'buttonLink':rtnData.rcsButton0Title
	            	                                   , 'startDate':rtnData.rcsButton0StartTime
	            	                                   , 'endDate':rtnData.rcsButton0EndTime
	            	                                   , 'startDateId':rtnData.rcsButton0StartTimeId
	            	                                   , 'endDateId':rtnData.rcsButton0EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcsButton0Description});
	            }            
	            if(rtnData.rcsButton1ButtonType){
	            	this.button100Flag = true;
	            	this.rowData.rcs100Buttons.push({'buttonType':rtnData.rcsButton1ButtonType
	            	                                   , 'buttonName':rtnData.rcsButton1DisplayText
	            	                                   , 'buttonLink':rtnData.rcsButton1Title
	            	                                   , 'startDate':rtnData.rcsButton1StartTime
	            	                                   , 'endDate':rtnData.rcsButton1EndTime
	            	                                   , 'startDateId':rtnData.rcsButton1StartTimeId
	            	                                   , 'endDateId':rtnData.rcsButton1EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcsButton1Description});
	            }
	            
	            if(rtnData.rcs1Button0ButtonType){
	            	this.button101Flag = true;
	            	this.rowData.rcs101Buttons.push({'buttonType':rtnData.rcs1Button0ButtonType
	            	                                   , 'buttonName':rtnData.rcs1Button0DisplayText
	            	                                   , 'buttonLink':rtnData.rcs1Button0Title
	            	                                   , 'startDate':rtnData.rcs1Button0StartTime
	            	                                   , 'endDate':rtnData.rcs1Button0EndTime
	            	                                   , 'startDateId':rtnData.rcs1Button0StartTimeId
	            	                                   , 'endDateId':rtnData.rcs1Button0EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcs1Button0Description});
	            }            
	            if(rtnData.rcs1Button1ButtonType){
	            	this.button101Flag = true;
	            	this.rowData.rcs101Buttons.push({'buttonType':rtnData.rcs1Button1ButtonType
	            	                                   , 'buttonName':rtnData.rcs1Button1DisplayText
	            	                                   , 'buttonLink':rtnData.rcs1Button1Title
	            	                                   , 'startDate':rtnData.rcs1Button1StartTime
	            	                                   , 'endDate':rtnData.rcs1Button1EndTime
	            	                                   , 'startDateId':rtnData.rcs1Button1StartTimeId
	            	                                   , 'endDateId':rtnData.rcs1Button1EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcs1Button1Description});
	            }
	            
	            if(rtnData.rcs2Button0ButtonType){
	            	this.button102Flag = true;
	            	this.rowData.rcs102Buttons.push({'buttonType':rtnData.rcs2Button0ButtonType
	            	                                   , 'buttonName':rtnData.rcs2Button0DisplayText
	            	                                   , 'buttonLink':rtnData.rcs2Button0Title
	            	                                   , 'startDate':rtnData.rcs2Button0StartTime
	            	                                   , 'endDate':rtnData.rcs2Button0EndTime
	            	                                   , 'startDateId':rtnData.rcs2Button0StartTimeId
	            	                                   , 'endDateId':rtnData.rcs2Button0EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcs2Button0Description});
	            }            
	            if(rtnData.rcs1Button2ButtonType){
	            	this.button102Flag = true;
	            	this.rowData.rcs102Buttons.push({'buttonType':rtnData.rcs2Button1ButtonType
	            	                                   , 'buttonName':rtnData.rcs2Button1DisplayText
	            	                                   , 'buttonLink':rtnData.rcs2Button1Title
	            	                                   , 'startDate':rtnData.rcs2Button1StartTime
	            	                                   , 'endDate':rtnData.rcs2Button1EndTime
	            	                                   , 'startDateId':rtnData.rcs2Button1StartTimeId
	            	                                   , 'endDateId':rtnData.rcs2Button1EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcs2Button1Description});
	            }
	            
	            if(rtnData.rcs3Button0ButtonType){
	            	this.button103Flag = true;
	            	this.rowData.rcs103Buttons.push({'buttonType':rtnData.rcs3Button0ButtonType
	            	                                   , 'buttonName':rtnData.rcs3Button0DisplayText
	            	                                   , 'buttonLink':rtnData.rcs3Button0Title
	            	                                   , 'startDate':rtnData.rcs3Button0StartTime
	            	                                   , 'endDate':rtnData.rcs3Button0EndTime
	            	                                   , 'startDateId':rtnData.rcs3Button0StartTimeId
	            	                                   , 'endDateId':rtnData.rcs3Button0EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcs3Button0Description});
	            }            
	            if(rtnData.rcs1Button3ButtonType){
	            	this.button103Flag = true;
	            	this.rowData.rcs103Buttons.push({'buttonType':rtnData.rcs3Button1ButtonType
	            	                                   , 'buttonName':rtnData.rcs3Button1DisplayText
	            	                                   , 'buttonLink':rtnData.rcs3Button1Title
	            	                                   , 'startDate':rtnData.rcs3Button1StartTime
	            	                                   , 'endDate':rtnData.rcs3Button1EndTime
	            	                                   , 'startDateId':rtnData.rcs3Button1StartTimeId
	            	                                   , 'endDateId':rtnData.rcs3Button1EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcs3Button1Description});
	            }
	            
	            if(rtnData.rcs4Button0ButtonType){
	            	this.button104Flag = true;
	            	this.rowData.rcs104Buttons.push({'buttonType':rtnData.rcs4Button0ButtonType
	            	                                   , 'buttonName':rtnData.rcs4Button0DisplayText
	            	                                   , 'buttonLink':rtnData.rcs4Button0Title
	            	                                   , 'startDate':rtnData.rcs4Button0StartTime
	            	                                   , 'endDate':rtnData.rcs4Button0EndTime
	            	                                   , 'startDateId':rtnData.rcs4Button0StartTimeId
	            	                                   , 'endDateId':rtnData.rcs4Button0EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcs4Button0Description});
	            }            
	            if(rtnData.rcs1Button4ButtonType){
	            	this.button104Flag = true;
	            	this.rowData.rcs104Buttons.push({'buttonType':rtnData.rcs4Button1ButtonType
	            	                                   , 'buttonName':rtnData.rcs4Button1DisplayText
	            	                                   , 'buttonLink':rtnData.rcs4Button1Title
	            	                                   , 'startDate':rtnData.rcs4Button1StartTime
	            	                                   , 'endDate':rtnData.rcs4Button1EndTime
	            	                                   , 'startDateId':rtnData.rcs4Button1StartTimeId
	            	                                   , 'endDateId':rtnData.rcs4Button1EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcs4Button1Description});
	            }
	            
	            if(rtnData.rcs5Button0ButtonType){
	            	this.button105Flag = true;
	            	this.rowData.rcs105Buttons.push({'buttonType':rtnData.rcs5Button0ButtonType
	            	                                   , 'buttonName':rtnData.rcs5Button0DisplayText
	            	                                   , 'buttonLink':rtnData.rcs5Button0Title
	            	                                   , 'startDate':rtnData.rcs5Button0StartTime
	            	                                   , 'endDate':rtnData.rcs5Button0EndTime
	            	                                   , 'startDateId':rtnData.rcs5Button0StartTimeId
	            	                                   , 'endDateId':rtnData.rcs5Button0EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcs5Button0Description});
	            }            
	            if(rtnData.rcs1Button5ButtonType){
	            	this.button105Flag = true;
	            	this.rowData.rcs105Buttons.push({'buttonType':rtnData.rcs5Button1ButtonType
	            	                                   , 'buttonName':rtnData.rcs5Button1DisplayText
	            	                                   , 'buttonLink':rtnData.rcs5Button1Title
	            	                                   , 'startDate':rtnData.rcs5Button1StartTime
	            	                                   , 'endDate':rtnData.rcs5Button1EndTime
	            	                                   , 'startDateId':rtnData.rcs5Button1StartTimeId
	            	                                   , 'endDateId':rtnData.rcs5Button1EndTimeId
	            	                                   , 'buttonLink1':rtnData.rcs5Button1Description});
	            }

			}
			
			

//FRIENDTALK DATA SET            
            this.rowData.friendTalkSenderKeyType	= rtnData.friendTalkSenderKeyType;
            this.rowData.friendTalkSenderKey 		= rtnData.friendTalkSenderKey;
            this.rowData.friendTalkImageLink 		= rtnData.friendTalkImageLink;
            this.rowData.friendTalkContent 			= rtnData.friendTalkContent;
            this.rowData.friendTalkImgInfo.fileId 	= rtnData.friendTalkFileId;
            this.rowData.friendTalkImgInfo.imgUrl 	= rtnData.friendTalkImgUrl;
            
            if(rtnData.friendTalkPrdType == 'friendTalk'){
	            if(rtnData.friendTalkButton0ButtonType){
	            	this.buttonFriendTalkFlag = true;
	            	this.rowData.friendTalkButtons.push({'buttonType':rtnData.friendTalkButton0ButtonType
	            	                                   , 'buttonName':rtnData.friendTalkButton0DisplayText
	            	                                   , 'buttonLink':rtnData.friendTalkButton0Title
	            	                                   , 'startDate':rtnData.friendTalkButton0StartTime
	            	                                   , 'endDate':rtnData.friendTalkButton0EndTime
	            	                                   , 'buttonLink1':rtnData.friendTalkButton0Description});
	            }
	            
	            if(rtnData.friendTalkButton1ButtonType){
	            	this.buttonFriendTalkFlag = true;
	            	this.rowData.friendTalkButtons.push({'buttonType':rtnData.friendTalkButton1ButtonType
	            	                                   , 'buttonName':rtnData.friendTalkButton1DisplayText
	            	                                   , 'buttonLink':rtnData.friendTalkButton1Title
	            	                                   , 'startDate':rtnData.friendTalkButton1StartTime
	            	                                   , 'endDate':rtnData.friendTalkButton1EndTime
	            	                                   , 'buttonLink1':rtnData.friendTalkButton1Description});
	            }
	            
	            if(rtnData.friendTalkButton2ButtonType){
	            	this.buttonFriendTalkFlag = true;
	            	this.rowData.friendTalkButtons.push({'buttonType':rtnData.friendTalkButton2ButtonType
	            	                                   , 'buttonName':rtnData.friendTalkButton2DisplayText
	            	                                   , 'buttonLink':rtnData.friendTalkButton2Title
	            	                                   , 'startDate':rtnData.friendTalkButton2StartTime
	            	                                   , 'endDate':rtnData.friendTalkButton2EndTime
	            	                                   , 'buttonLink1':rtnData.friendTalkButton2Description});
	            }
	            
	            if(rtnData.friendTalkButton3ButtonType){
	            	this.buttonFriendTalkFlag = true;
	            	this.rowData.friendTalkButtons.push({'buttonType':rtnData.friendTalkButton3ButtonType
	            	                                   , 'buttonName':rtnData.friendTalkButton3DisplayText
	            	                                   , 'buttonLink':rtnData.friendTalkButton3Title
	            	                                   , 'startDate':rtnData.friendTalkButton3StartTime
	            	                                   , 'endDate':rtnData.friendTalkButton3EndTime
	            	                                   , 'buttonLink1':rtnData.friendTalkButton3Description});
	            }
	            
	            if(rtnData.friendTalkButton4ButtonType){
	            	this.buttonFriendTalkFlag = true;
	            	this.rowData.friendTalkButtons.push({'buttonType':rtnData.friendTalkButton4ButtonType
	            	                                   , 'buttonName':rtnData.friendTalkButton4DisplayText
	            	                                   , 'buttonLink':rtnData.friendTalkButton4Title
	            	                                   , 'startDate':rtnData.friendTalkButton4StartTime
	            	                                   , 'endDate':rtnData.friendTalkButton4EndTime
	            	                                   , 'buttonLink1':rtnData.friendTalkButton4Description});
	            }
			}

//NOTICETALK DATA SET

//SMS/MMS DATA SET


			if(rtnData.smsSendType == 'S'){
				this.smsTemplateTable = 0;
			}else if(rtnData.smsSendType == 'M'){
				this.smsTemplateTable = 1;
			}

            this.rowData.smsSendType = rtnData.smsSendType;
            this.rowData.smsTitle = rtnData.smsTitle;
            this.rowData.smsContent = rtnData.smsContent;
            
            if(rtnData.smsFileId0){
            	this.rowData.smsImgInfoList.push({'fileId':rtnData.smsFileId0
            	                                , 'imgUrl':rtnData.mmsChImgUrl0});
            }
            
            if(rtnData.smsFileId1){
            	this.rowData.smsImgInfoList.push({'fileId':rtnData.smsFileId1
            	                                , 'imgUrl':rtnData.mmsChImgUrl1});
            }
            
            this.rowData.smsCallback = rtnData.smsCallback;
            
            
            //rtnData.buttonList = [];
            //this.rowData = Object.assign({}, rtnData);
            //this.rowData.buttonList = JSON.parse(rtnData.tmpltButtons);
            //this.rowData.otherProjectUseYn = (rtnData.projectId == 'ALL' ? 'Y' : 'N');
          }
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
          //this.rowData = {imgUrl:'', buttonList:[]};
        }
      });
    },

    enablePreview(channel){
      this.channelType = channel;
      
      jQuery('#Push').attr('class','btnStyle1 backWhite');
      jQuery('#RCS').attr('class','btnStyle1 backWhite');
      jQuery('#kakao').attr('class','btnStyle1 backWhite');
      jQuery('#smsMms').attr('class','btnStyle1 backWhite');
      
      jQuery('#'+channel).attr('class','btnStyle1 backBlack')
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
    },


    fnClickCuInputType(e){
      if(this.sendData.cuInputType == e.target.value){
        this.fnChgCuInputType('N');
      }
    },
    
        
    //수신자 입력 타입 변경시
    fnChgCuInputType(chgYn){
      if(this.$gfnCommonUtils.defaultIfEmpty(chgYn, 'Y') == 'Y'){
        this.fnCallbackRecvInfoLst(null);  //수신자 입력 타입 변경시 수신자 정보 초기화
      }
      if(this.sendData.cuInputType == 'ALL'){  //전체발송
        return;
      }
      if(!this.sendData.pushContent){
        confirm.fnAlert(this.componentsTitle, '메시지 내용을 먼저 입력해주세요.');
        this.sendData.cuInputType = 'ALL'
        this.$refs.cuInputType_ALL.click();
        return;
      }

      if(this.fnSetContsVarNms() == false){
        this.sendData.cuInputType = 'ALL'
        this.$refs.cuInputType_ALL.click();
        return;
      }

      if(this.sendData.cuInputType == 'DICT'){  //직접입력
        //수신자 직접입력 팝업 호출
        this.directInputOpen = !this.directInputOpen;
      } else if(this.sendData.cuInputType == 'ADDR'){  //주소록
        //주소록 검색 팝업 호출
        this.addressInputOpen = !this.addressInputOpen;
      } else if(this.sendData.cuInputType == 'EXCEL'){  //엑셀
        //엑셀파일찾기 호출
        this.$refs.excelFile.click();
      }
    },

    
    
  }
}
</script>