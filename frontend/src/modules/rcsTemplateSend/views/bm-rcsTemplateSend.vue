<template>
		<article>
			<div class="contentHeader">
				<h2>RCS 발송</h2>
				<a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="이용안내">이용안내 <i class="fal fa-book-open"></i></a>
			</div>
			<!-- 본문 -->
			<div>
				<p class="color4 bgColor_f1 pd20">좌측 탭의 RCS 유형을 먼저 선택해 주십시오. 템플릿형은 광고성 메시지를 보내실 수 없습니다.</p>
			</div>
			<h4>RCS 상품</h4>
			<ul class="tab_s6" role="tablist">
				<li role="presentation" class="active"><a href="#productCate1" aria-controls="productCate1" role="tab" data-toggle="tab">템플릿형 <i class="fas fa-question-circle toolTip"><span class="toolTipText text-left" style="width:470px">정보성 메시지를 사전에 등록 및 승인을 받아 메시지를 발송할 수 있습니다.<br>스타일/서술 템플릿 형식은 승인완료된 양식만 발송 할 수 있습니다.<br>템플릿형은 광고성 메시지를 발송할 수 없습니다.</span></i></a></li>
				<li role="presentation"><a href="#productCate2" aria-controls="productCate2" role="tab" data-toggle="tab">포맷카드형 <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:380px">이미지와 버튼을 활용한 다양한 형태 구성이 가능합니다.<br>정보/안내/광고 등 모든 분야의 메시지 발송을 지원합니다.<br>8가지 레이아웃 구성을 제공합니다.</span></i></a></li>
			</ul>
			<div class="tab-content">
				<!--  tab1 -->
				<div role="tabpanel" class="tab-pane active" id="productCate1">
					<div class="templateBox">
						<!-- templateList -->
						<ul class="templateList">
							<li>
								<img src="@/assets/images/common/RCSTemplate1.png" alt="템플릿 승인(서술)"><h6>템플릿 승인(서술)</h6>
								<div class="consolMarginTop">
									<input v-model="templateRadioBtn" @click="fnSetTemplateCard('des')" type="radio" name="RCSTemplate1" value="des" id="pushTemplate1-1" class="radioStyle" checked=""><label for="pushTemplate1-1"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:280px">브랜드 포탈에서 승인이 완료된<br>서술형 템플릿만 발송 할 수 있습니다.</span></i>
								</div>
							</li>
							<li>
								<img src="@/assets/images/common/RCSTemplate1.png" alt="템플릿 승인(스타일)"><h6>템플릿 승인(스타일)</h6>
								<div class="consolMarginTop">
									<input v-model="templateRadioBtn" @click="fnSetTemplateCard('cell')" type="radio" name="RCSTemplate1" value="cell" id="pushTemplate1-2" class="radioStyle"><label for="pushTemplate1-2"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:280px">브랜드 포탈에서 승인이 완료된<br>스타일 템플릿만 발송 할 수 있습니다.</span></i>
								</div>
							</li>
							<li>
								<img src="@/assets/images/common/RCSTemplate2.png" alt="텍스트 미승인형"><h6>텍스트 미승인형</h6>
								<div class="consolMarginTop">
									<input v-model="templateRadioBtn" @click="fnSetTemplateCard('text')" type="radio" name="RCSTemplate1" value="text" id="pushTemplate1-3" class="radioStyle"><label for="pushTemplate1-3"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">90자 이내의 단문의 안내성<br>메시지 발송에 적합합니다.</span></i>
								</div>
							</li>
							
						</ul>
						<!-- //templateList -->
					</div>
				</div>

				<!--  tab2 -->
				<div role="tabpanel" class="tab-pane" id="productCate2">
					<div class="templateBox">
						<!-- templateList -->
						<ul class="templateList">
							<li>
								<img src="@/assets/images/common/RCSTemplate3.png" alt="텍스트(SMS)"><h6>텍스트(SMS)</h6>
								<div class="consolMarginTop">
									<input v-model="templateRadioBtn" @click="fnSetFormatCard" type="radio" name="RCSTemplate1" value="SS000000" id="RCSTemplate2-1" class="radioStyle" checked=""><label for="RCSTemplate2-1"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
								</div>
							</li>
							<li>
								<img src="@/assets/images/common/RCSTemplate4.png" alt="텍스트(LMS)"><h6>텍스트(LMS)</h6>
								<div class="consolMarginTop">
									<input v-model="templateRadioBtn" @click="fnSetFormatCard" type="radio" name="RCSTemplate1" value="SL000000" id="RCSTemplate2-2" class="radioStyle"><label for="RCSTemplate2-2"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
								</div>
							</li>
							<li class="ml40">
								<img src="@/assets/images/common/RCSTemplate6.png" alt="세로형(short)"><h6>세로형 (Medium)</h6>
								<div class="consolMarginTop">
									<input v-model="templateRadioBtn" @click="fnSetFormatCard" type="radio" name="RCSTemplate1" value="SMwThM00" id="RCSTemplate2-6-1" class="radioStyle"><label for="RCSTemplate2-6-1"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
								</div>
							</li>
							<li class="ml40">
								<img src="@/assets/images/common/RCSTemplate7.png" alt="세로형(Tall)"><h6>세로형 (Tall)</h6>
								<div class="consolMarginTop">
									<input v-model="templateRadioBtn" @click="fnSetFormatCard" type="radio" name="RCSTemplate1" value="SMwThT00" id="RCSTemplate2-7" class="radioStyle"><label for="RCSTemplate2-7"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
								</div>
							</li>
							<li class="ml40">
								<img src="@/assets/images/common/RCSTemplate10.png" alt="캐러셀(short)"><h6>캐러셀 (Small)</h6>
								<div class="consolMarginTop">
									<input v-model="templateRadioBtn" @click="fnSetCarousel" type="radio" name="RCSTemplate1" :value="carouselSmall" id="RCSTemplate2-10" class="radioStyle"><label for="RCSTemplate2-10"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
								</div>
							</li>
							<li>
								<img src="@/assets/images/common/RCSTemplate11.png" alt="캐러셀(Tall)"><h6>캐러셀 (Medium)</h6>
								<div class="consolMarginTop">
									<input v-model="templateRadioBtn" @click="fnSetCarousel" type="radio" name="RCSTemplate2" :value="carouselMedium" id="RCSTemplate2-11" class="radioStyle"><label for="RCSTemplate2-11"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
								</div>
							</li>
						</ul>
						<!-- //templateList -->
					</div>
				</div>
			</div>
			<h4>내용입력</h4>
			<div class="of_h mt20">
				<div v-if="carouSelType" class="phone3 inline-block" style="width:30%">
					<div class="cardBxsliderWrap">						
						<!-- phoneWrap -->
						<div class="phoneWrap">
							<img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
							<div class="phoneCardWrap">
								<p class="color000">[WEB발신] (광고)</p>
								<ul class="cardBxslider mt10">
									<li class="slide cardBox">
										<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
										<div>
											<div class="scroll-y">
												<p class="color000 font-size13">타이틀 영역1</p>
											</div>
											<p class="color3 font-size10 mt5">무료수신거부:</p>
										</div>
									</li>
									<li class="slide cardBox">
										<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
										<div>
											<div class="scroll-y">
												<p class="color000 font-size13">타이틀 영역2</p>
											</div>
											<p class="color3 font-size10 mt5">무료수신거부:</p>
										</div>
									</li>
									<li class="slide cardBox">
										<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
										<div>
											<div class="scroll-y">
												<p class="color000 font-size13">타이틀 영역3</p>
											</div>
											<p class="color3 font-size10 mt5">무료수신거부:</p>
										</div>
									</li>
								</ul>
							</div>
						</div>
						<!-- //phoneWrap -->						
					</div>
					<div class="phone_04_btn mt50" style="margin-left:70px">
						<a href="#self" class="btnStyle1 backBlack" title="Push">Push</a>
						<a href="#self" class="btnStyle1 backWhite" title="Push">SMS</a>						
					</div>
				</div>
				<div v-if="!carouSelType" class="phone3 inline-block" style="width:30%">
					<!-- phoneWrap -->
					<div class="phoneWrap">
						<img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div  v-if="pushTab=='push'" class="phoneTextWrap">
							<div v-if="templateRadioBtn =='des'" class="phoneText1">
								<img :src="imgsrc" style="width:70px;">
								<div class="scroll-y">
									<p class="mt15">{{text}}</p>
								</div>
								<p v-for="n in btnCnt" class="text-center mt20" style="color:#69C8FF">{{btnNm[n-1]}}</p>
							</div>
							<div v-if="templateRadioBtn =='cell'" class="phoneText1">
								<img :src="imgsrc" style="width:70px;">
								<div class="scroll-y">
									<div v-for="n in styleContentCnt" class="scroll-y3">
										<div>
											<p class="lc-1 inline-block">{{styleInput[n-1]}}</p>
											<p class="lc-1 inline-block float-right">{{styleInputSec[n-1]}}</p>
										</div>
										<hr v-if="styleChk[n-1]==true">
									</div>
								</div>
								<p v-for="n in btnCnt" class="text-center mt20" style="color:#69C8FF">{{btnNm[n-1]}}</p>
							</div>
							<div v-if="templateRadioBtn =='text' || templateRadioBtn == 'SS000000' || templateRadioBtn == 'SL000000'" class="phoneText1">
								<div class="scroll-y">
									<h5 v-if="sendData.textTitle!=''">{{sendData.textTitle}}</h5>
									<p class="mt15">{{sendData.textContents}}</p>
								</div>
								<p v-for="n in btnCnt" class="text-center mt20" style="color:#69C8FF">{{btnNm[n-1]}}</p>
							</div>
							<div v-if="templateRadioBtn == 'SMwThM00'">
								<img :src="sendData.SMwThM00Img" alt="프리 템플릿">
								<div style="background:#fff; border-radius: 0 0 5px 5px; min-height:180px" class="pd20">
									<h5>{{sendData.textTitle}}</h5>
									<div class="scroll-y3">
										<p class="color6">{{sendData.textContents}}</p>
									</div>								
								</div>
							</div>
							<div v-if="templateRadioBtn == 'SMwThT00'">
								<img :src="sendData.SMwThT00Img" alt="프리 템플릿">
								<div style="background:#fff; border-radius: 0 0 5px 5px; min-height:170px" class="pd20">
									<h5>{{sendData.textTitle}}</h5>
									<div class="scroll-y6">
										<p class="color6">{{sendData.textContents}}</p>
									</div>								
								</div>
							</div>
						</div>
						<div  v-if="pushTab!='push'" class="phoneTextWrap">
							<div class="phoneText1">
								<div class="scroll-y">
									<p class="mt15">{{sendData.callbackTitle}}</p>
									<p class="mt15">{{sendData.callbackContents}}</p>
								</div>
							</div>
						</div>
						<div class="phone_04_btn">
							<a @click.prevent="fnclickPushBtn('push')" href="#self" class="btnStyle1 backBlack" title="Push">Push</a>
							<a @click.prevent="fnclickPushBtn('SMS')" v-if="sendData.senderType=='SMS'" href="#self" class="btnStyle1 backWhite" title="Push">SMS</a>
							<a @click.prevent="fnclickPushBtn('LMS')" v-if="sendData.senderType=='LMS'" href="#self" class="btnStyle1 backWhite" title="Push">LMS</a>								
						</div>
					</div>
					<!-- //phoneWrap -->
				</div>
				<div class="of_h inline-block vertical-top consoleCon" style="width:60%">
					<div class="user-phone of_h">
						<div class="float-left" style="width:24%">
							<h4>01  메시지 내용</h4>
						</div>
						<div class="float-left" style="width:76%">
							<a v-if="templateRadioBtn=='des' || templateRadioBtn=='cell'" @click.prevent="fnOpenRcsTemplatePopup" activity="READ" href="#self" class="btnStyle1 backLightGray mr10" data-toggle="modal" data-target="#templatePop" title="RCS 템플릿 선택">RCS 템플릿 선택</a>
							<a v-if="formatCard==true" @click.prevent="fnOpenMsgPop" activity="READ" href="#self" class="btnStyle1 backLightGray mr10" data-toggle="modal" data-target="#message" title="메세지보관함">메세지보관함</a>
							<a v-if="formatCard==true && carouSelType==false" @click.prevent="fnOpenRcsContentPop(0)" activity="READ" href="#self" class="btnStyle1 borderLightGray mr10" title="내용입력">내용입력</a>
							<a v-if="formatCard==true && carouSelType==false && templateRadioBtn!='text'" activity="READ" href="#self" class="btnStyle1 borderLightGray mr10" data-toggle="modal" data-target="#Tamplet" title="버튼입력">버튼입력</a>
							<div v-if="carouSelType==true" class="inline-block ml20">
								<p class="inline-block mr10">카드개수</p>
								<select v-model="carouSelSelect" name="userConsole_sub020204_1" class="selectStyle2">
									<option value=3>3</option>
									<option value=4>4</option>
									<option value=5>5</option>
									<option value=6>6</option>
								</select>
							</div>
							<div v-if="carouSelType==true" class="consolMarginTop">
								<ul class="tab_s7" role="tablist">
									<li @click="fnClickTab(n)" v-for="n in carouSelSelect" role="presentation"><a href="#card" aria-controls="card" role="tab" data-toggle="tab">카드 {{n}}</a></li>
								</ul>

								<div class="tab-content">
									<!--  tab1 -->
									<div role="tabpanel" class="tab-paneactive" id="card">
										<div class="templateBox pd20">
											<!-- templateList -->
											<a @click.prevent="fnOpenRcsContentPop(carouSelTabCnt)" href="#self" class="btnStyle1 borderLightGray mr10" title="내용입력">내용입력</a>
											<a href="#self" class="btnStyle1 borderLightGray" title="버튼입력">버튼입력</a>
											<!-- //templateList -->
										</div>
									</div>
								</div>
							</div>
							<div v-if="formatCard==true" class="of_h consolMarginTop">
								<div style="width:18%" class="float-left">
									<h5>광고</h5>
								</div>
								<div class="of_h">
									<input type="radio" name="ad" value="Display" id="Display"> <label for="Display" class="mr30">미표시</label>
									<input type="radio" name="ad" value="not-Display" id="not-Display" checked=""> <label for="not-Display" class="mr20">표시</label>
									<div class="inline-block">
										<p class="inline-block mr10">무료수신거부</p><input type="text" class="inputStyle" style="width:50%">
									</div>
								</div>
							</div>
							<div class="of_h consolMarginTop">
								<div style="width:18%" class="float-left">
									<h5>복사가능여부</h5>
								</div>
								<div style="width:82%">
									<input v-model="sendData.copy" type="radio" name="copy" value="no" id="no" checked=""> <label for="no" class="mr30">불가능</label>
									<input v-model="sendData.copy" type="radio" name="copy" value="yes" id="yes"> <label for="yes" class="mr30">가능</label>
									<p class="txtCaption color4">※ 단말 내 메시지에 대한 복사가능여부 설정입니다.</p>
								</div>
							</div>
							<div class="of_h consolMarginTop">
								<div style="width:18%" class="float-left">
									<h5>발신번호 *</h5>
								</div>
								<div style="width:82%">
									<select v-model="sendData.callback" name="userConsole_sub020201_1" class="selectStyle2" style="width:30%">
										<option v-for="options in sendData.callbackArr">
											{{options.callback}}
										</option>
									</select>
								</div>
							</div>
							<div class="of_h consolMarginTop">
								<div style="width:18%" class="float-left">
									<h5>대체발송</h5>
								</div>
								<div style="width:82%">
									<input v-model="sendData.senderType" type="radio" name="substitution" value="UNUSED" id="UNUSED" checked="" activity="READ"> <label for="UNUSED" class="mr30">미사용</label>
									<input v-model="sendData.senderType" type="radio" name="substitution" value="SMS" id="SMS" activity="READ"> <label for="SMS" class="mr30" data-toggle="modal" data-target="#sender">SMS</label>
									<input v-model="sendData.senderType" type="radio" name="substitution" value="LMS" id="LMS" activity="READ"> <label for="LMS" class="mr30" data-toggle="modal" data-target="#sender">LMS</label>
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
								<div style="width:18%" class="float-left">
									<h5>수신자 *</h5>
								</div>
								<div style="width:100%">
									<div>
										<input type="radio" id="cuInputType_DICT" name="cuInputType" value="DICT" v-model="sendData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType" activity="READ">
										<label for="cuInputType_DICT" class="mr30">수신자 직접입력</label>
										<input type="radio" id="cuInputType_ADDR" name="cuInputType" value="ADDR" v-model="sendData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType" activity="READ">
										<label for="cuInputType_ADDR" class="mr30">주소록 검색</label>
										<input type="radio" id="cuInputType_EXCEL" name="cuInputType" value="EXCEL" v-model="sendData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType" activity="READ">
										<label for="cuInputType_EXCEL" class="mr10">엑셀 업로드</label>
										<a @click="fnExcelTmplteDownLoad" class="btnStyle1 backLightGray" title="샘플" activity="READ">샘플 <i class="far fa-arrow-to-bottom"></i></a>
										<input ref="excelFile" type="file" style="display:none;">
									</div>
								</div>
							</div>
							<div class="of_h">
								<div class="of_h float-right" style="width:82%">
									<p>수신자 : {{recvCnt}}명</p>
									<div class="float-right consolMarginTop" style="width:100%">
										<textarea class="textareaStyle height120" v-model="sendData.cuInfo" disabled></textarea>
									</div>
								</div>
							</div>
						</div>
					</div>
					<hr>
					<div class="of_h user-phone">
						<div style="width:24%" class="float-left">
							<h4>03  발송옵션 선택</h4>
						</div>
						<div style="width:76%" class="float-left">
							<div class="of_h">
								<div class="float-left" style="width:18%">
									<h5>발송시간 *</h5>
								</div>
							<div class="float-left" style="width:26%">
								<input type="radio" id="rsrvSendYn_N" value="N" v-model="sendData.rsrvSendYn">
								<label for="rsrvSendYn_N" class="mr30">즉시</label>
								<input type="radio" id="rsrvSendYn_Y" value="Y" v-model="sendData.rsrvSendYn">
								<label for="rsrvSendYn_Y">예약</label>
							</div>
							<div v-if="sendData.rsrvSendYn == 'Y'" class="float-left" style="width:18%">
								<Calendar @update-date="fnUpdateRsrvDate" calendarId="rsrvDate" classProps="datepicker inputStyle" :initDate="sendData.rsrvDate"></Calendar>
							</div>
							<div v-if="sendData.rsrvSendYn == 'Y'" class="float-right" style="width:36%">
								<select class="selectStyle2" style="width:47%" v-model="sendData.rsrvHH">
								<option value="00">00</option>
								<option v-for="hh in 23" :key="hh" :value="hh > 9 ? hh : '0'+hh">{{hh > 9 ? hh : '0'+hh}}</option>
								</select>
								: <select class="selectStyle2" style="width:47%" v-model="sendData.rsrvMM">
								<option value="00">00</option>
								<option v-for="mm in 5" :key="mm" :value="mm+'0'">{{mm+'0'}}</option>
								</select>
							</div>
						</div>
							<div class="of_h consolMarginTop">
								<div style="width:18%" class="float-left">
									<h5>태그</h5>
								</div>
								<div class="of_h" style="width:82%">
									<input type="text" class="inputStyle" style="width:100%" placeholder="캠페인 ID를 입력해주세요." v-model="sendData.campaignId" maxlength="20">
								</div>
							</div>
						</div>
					</div>
					<div class="float-right mt20">
						<a v-if="templateRadioBtn!='des' && templateRadioBtn !='cell'" activity="SAVE" data-toggle="modal" data-target="#save" href="#self" class="btnStyle2 backWhite float-left" title="저장">저장</a>
						<a href="#self" class="btnStyle2 float-left ml10" title="테스트 발송">테스트 발송</a>
						<a href="#self" class="btnStyle2 backRed float-left ml10" title="발송">발송</a>
					</div>
				</div>
			</div>	

			<RcsTemplatePopup :templateRadioBtn.sync="templateRadioBtn" ref="rcsTemplatePop" @fnResult="fnSetTemplate"></RcsTemplatePopup>
			<RcsMsgPopup :templateRadioBtn.sync="templateRadioBtn" ref="rcsMsgPop" @fnTmpMsgSet="fnTmpMsgSet" ></RcsMsgPopup>
			<RcsContentPopup :templateRadioBtn.sync="templateRadioBtn" :contentPopCnt.sync="contentPopCnt" ref="rcsContentPop" @fnAddResult="fnSetAddContents"></RcsContentPopup>
			<RcsSenderPopup :senderType.sync="sendData.senderType" ref="rcsSenderPop"></RcsSenderPopup>
			<RcsSavePopup ref="rcsSavePop"></RcsSavePopup>
			<DirectInputPopup :directInputOpen.sync="directInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid" :recvInfoLst="sendData.recvInfoLst"></DirectInputPopup>
			<AddressInputPopup :addressInputOpen.sync="addressInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid"></AddressInputPopup>
		</article>
</template>

<script>
import RcsTemplatePopup from "@/modules/rcsTemplateSend/components/bp-rcsTemplatePop.vue";
import RcsMsgPopup from "@/modules/rcsTemplateSend/components/bp-rcsMsgPopup.vue";
import RcsContentPopup from "@/modules/rcsTemplateSend/components/bp-rcsContentPopup.vue";
import RcsSenderPopup from "@/modules/rcsTemplateSend/components/bp-rcsTemplateSenderPop.vue";
import RcsSavePopup from "@/modules/rcsTemplateSend/components/bp-rcsSavePopup.vue";
import DirectInputPopup from "@/modules/message/components/bp-directInput.vue";
import AddressInputPopup from "@/modules/message/components/bp-addressInput.vue";
import Calendar from "@/components/Calendar.vue";
import rcsTemplateSendApi from "@/modules/rcsTemplateSend/service/api.js";
import messageApi from "@/modules/message/service/messageApi.js";

import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  name: "pushTemplateList",
  components: {
	  RcsTemplatePopup,
	  RcsMsgPopup,
	  RcsContentPopup,
	  RcsSenderPopup,
	  RcsSavePopup,
	  DirectInputPopup,
	  AddressInputPopup,
	  Calendar
  },
  data() {
    return {
		pushTab : "push",				// push 버튼
		imgsrc : require("@/assets/images/common/approve.png"),			// 이미지
		text : "테스트입니다.[한글]",    //	미리보기 text (des형)
		styleContentCnt: 0,				// 스타일형 inputLine count
		styleInput: [],					// 스타일형 첫 input
		styleInputSec: [],				// 스타일형 두번째 input
		styleChk: [],	        	    // 스타일형 lineChk
		contentTitle : "",				// 미승인 SMS,LMS TITLE
		contentText : "",				// 미승인 SMS,LMS 내용
        btnCnt: 0,			     	    // 버튼 개수
		btnNm:[],			     	    // 버튼 이름
		formatCard : false,				// 버튼입력 버튼
		carouSelType : false,			// 캐러셀 영역 세팅
		test : "",						// 
		carouSelSelect : 3,				// 캐러셀 selectBox
		carouSelTabCnt : 1,				// 캐러셀 탭
		messagebaseId : "",				// 템플릿 선택 Id
		directInputOpen : false,		// 수신자 직접입력 버튼
		addressInputOpen : false,		// 주소록 검색 버튼
		rcsTemplateSavePopOpen : false,	// 저장 버튼
        templateRadioBtn : "des",       // 템플릿형 라디오 버튼
		recvCnt : 0,  					// 수신자명수
		carouselSmall : 'CMwShS0300',	// 캐러셀형 msgId
		carouselMedium : 'CMwMhM0300',	// 캐러셀형 msgId
		contentPopCnt : 0,				// 내용입력 폼 갯수
		btnPopCnt : 0,					// 버튼입력 폼 갯수
		sendData : {
			messagebaseId : "",							// MSG ID
			brandId : "",								// 브랜드 ID
			textTitle : "",								// 텍스트 제목
			textContents : "",							// 텍스트 내용
			imgUrl : "",								// 이미지
			fileId : "",								// 이미지
			wideImgYn : "",								// 이미지
			SMwThM00Img : require("@/assets/images/common/cardThumImg2_2.png"),	// 이미지 출력 src 세로형 Medium
			SMwThT00Img : require("@/assets/images/common/cardThumImg2_1.png"),	// 이미지 출력 src 세로형 Tall
			callback: '',  								// 발신번호
			callbackArr: [],							// 발신번호 selectBox
			copy: 'no',									// 복사 가능여부
			senderType : 'UNUSED',						// 대체발송
			callbackTitle : "",							// 대체발송 TITLE
			callbackContents : "",						// 대체발송 CONTENTS
			saveContent : "",							// 저장 메시지명
			carouselArr : [],							// 캐러셀형 데이터 배열처리

			requiredCuid : false,  //app 로그인 ID 필수여부
			requiredCuPhone : true,  //수신자 폰번호 필수여부
			cuInputType:'DICT',  //DICT, ADDR, EXCEL
			smsTitle:'',  //제목
			smsContent:'',  //내용
			rcvblcNumber:'',  //수신거부
			cuInfo:'',
			rsrvSendYn:'N',  //예약발송여부
			rsrvDate:this.$gfnCommonUtils.getCurretDate(),
			rsrvHH:'00',
			rsrvMM:'00',
			campaignId:'',
			imgInfoList: [],
			recvInfoLst: [],  //수신자정보
			contsVarNms: [], //메세지 내용 변수명
			testRecvInfoLst: [],  //테스트 수신자정보
		}

    }
  },
  watch : {
	  carouSelSelect(newval, oldval) {
		  // type 변경
		  this.carouSelSelect = newval*1;
	  },
	  carouSelType(newval, oldval) {
		  var vm = this;
		  if(newval) {
			  setTimeout(function() {
				  vm.fnSetSlider();
			  }, 3000)
		  } 
	  }
  },
  mounted() {
	  this.fnInit();
  },
  methods: {
	// RCS 발송 발신번호 세팅
	fnInit() {
		var vm = this;
		var params = {
			"approvalStatus" : "승인"
		}
		rcsTemplateSendApi.selectCallbackList(params).then(response => {
			var result = response.data;
			vm.sendData.callbackArr = result.data;
			vm.sendData.callback = result.data[0].callback;
		});
	},

	fnSetSlider() {
		jQuery('.cardBxslider').bxSlider({
			auto: false,
			autoControls: false,
			slideWidth: 204,
			minSlides: 1,
			maxSlides: 2,
			slideMargin: 10,
			controls: true,
			pager: true,
			pagerType: 'short',
			touchEnabled : (navigator.maxTouchPoints > 0),
			autoHover: false,
			pause: 6000
		});
	},

	fnOpenRcsTemplatePopup() {
		this.$refs.rcsTemplatePop.fnInit();
	},

	fnOpenMsgPop() {
		this.$refs.rcsMsgPop.fnInit();
	},

	fnOpenRcsContentPop(cnt) {
		this.$refs.rcsContentPop.fnInit();
		this.contentPopCnt = cnt;
		jQuery("#contentPop").modal("show");
	},
	
	fnSetTemplate(data) {
		var vm = this;
		var formNm = data.formNm;
		if(formNm.indexOf("승인") != -1) {
			this.imgsrc = require("@/assets/images/common/approve.png");
		}
		if(formNm.indexOf("취소") != -1) {
			this.imgsrc = require("@/assets/images/common/cancel.png");
		}
		if(formNm.indexOf("인증") != -1) {
			this.imgsrc = require("@/assets/images/common/certification.png");
		}
		if(formNm.indexOf("출고") != -1) {
			this.imgsrc = require("@/assets/images/common/delivery.png");
		}
		if(formNm.indexOf("안내") != -1) {
			this.imgsrc = require("@/assets/images/common/infomation.png");
		}
		if(formNm.indexOf("회원가입") != -1) {
			this.imgsrc = require("@/assets/images/common/join.png");
		}
		if(formNm.indexOf("주문") != -1) {
			this.imgsrc = require("@/assets/images/common/order.png");
		}
		if(formNm.indexOf("출금") != -1) {
			this.imgsrc = require("@/assets/images/common/payment.png");
		}
		if(formNm.indexOf("입금") != -1) {
			this.imgsrc = require("@/assets/images/common/receipts.png");
		}
		if(formNm.indexOf("예약") != -1) {
			this.imgsrc = require("@/assets/images/common/reservation.png");
		}
		if(formNm.indexOf("배송") != -1) {
			this.imgsrc = require("@/assets/images/common/ship.png");
		}
		if(formNm.indexOf("명세서") != -1) {
			this.imgsrc = require("@/assets/images/common/specifications.png");
		}

		if(data.radioBtn == 'des') {
			vm.sendData.messagebaseId = data.messagebaseId;
			vm.text = data.desContent;
			vm.btnCnt = data.btnCnt;
			vm.btnNm = data.btnNm;
		} else {
			vm.sendData.messagebaseId = data.messagebaseId;
			vm.styleContentCnt = data.styleContentCnt;
			vm.styleInput  = data.styleInput ;
			vm.styleInputSec = data.styleInputSec;
			vm.styleChk = data.styleChk;
			vm.btnCnt = data.btnCnt;
			vm.btnNm = data.btnNm;
		}
	},

	// 메시지 내용 입력 return
	fnSetAddContents(params) {
		var vm = this;
		if(!vm.carouSelType) {
			vm.sendData.brandId = params.brandId;
			vm.sendData.textTitle = params.title;
			vm.sendData.textContents = params.contents;
			vm.sendData.imgUrl = params.imgUrl;
			vm.sendData.fileId = params.fileId;
			vm.sendData.wideImgYn = params.wideImgYn;
			if("SMwThM00" == data.radioBtn) {
				vm.sendData.SMwThM00Img = require(params.imgUrl);
			} 
			if("SMwThT00Img" == data.radioBtn) {
				vm.sendData.SMwThT00Img = require(params.imgUrl);
			}
		} else {
			var carouSelTabCnt = vm.carouSelTabCnt;
			vm.sendData.brandId = params.brandId;
			vm.sendData.carouselArr[carouSelTabCnt].textTitle = params.title;
			vm.sendData.carouselArr[carouSelTabCnt].textContents = params.contents;
			vm.sendData.carouselArr[carouSelTabCnt].imgUrl = params.imgUrl;
			vm.sendData.carouselArr[carouSelTabCnt].fileId = params.fileId;
			vm.sendData.carouselArr[carouSelTabCnt].wideImgYn = params.wideImgYn;
		}
	},

	fnClickCuInputType(e){
      if(this.sendData.cuInputType == e.target.value){
        this.fnChgCuInputType('N');
      }
    },

	// 대체발송 버튼 미리보기
	fnclickPushBtn(senderType) {
		this.pushTab = senderType;
	},

	// 대체발송 팝업 callback
	fnSenderTypeSet(data) {
		this.sendData.callbackTitle = data.senderTitle;
		this.sendData.callbackContents = data.senderContents;
	},

	//수신자 입력 타입 변경시
    fnChgCuInputType(chgYn){
      if(this.$gfnCommonUtils.defaultIfEmpty(chgYn, 'Y') == 'Y'){
        this.fnCallbackRecvInfoLst(null);  //수신자 입력 타입 변경시 수신자 정보 초기화
      }
      if(this.fnSetContsVarNms() == false){
        return;
      }

      if(this.sendData.cuInputType == 'DICT'){  //직접입력
        //수신자 직접입력 팝업 호출
        this.directInputOpen = true;
      } else if(this.sendData.cuInputType == 'ADDR'){  //주소록
        //주소록 검색 팝업 호출
        this.addressInputOpen = true;
      } else if(this.sendData.cuInputType == 'EXCEL'){  //엑셀
        //엑셀파일찾기 호출
        this.$refs.excelFile.click();
      }
    },

	//수신자 정보 callback
    fnCallbackRecvInfoLst(recvInfoLst, addYn) {
      if(recvInfoLst != null){
        if(this.$gfnCommonUtils.defaultIfEmpty(addYn, 'N') == 'Y'){
          this.sendData.recvInfoLst = this.sendData.recvInfoLst.concat(recvInfoLst);
        } else {
          this.sendData.recvInfoLst = recvInfoLst;
        }
        //수신자 중복제거
        this.fnDelDuplRecvInfo();

        this.recvCnt = this.sendData.recvInfoLst.length;
        this.sendData.cuInfo = JSON.stringify(this.sendData.recvInfoLst);
      } else {
        this.recvCnt = 0;
        this.sendData.recvInfoLst = [];
        this.sendData.cuInfo = '';
      }
    },

	//수신자 중복 제거
    fnDelDuplRecvInfo(){
      const vm = this;
      this.sendData.recvInfoLst = this.sendData.recvInfoLst.filter(function(item, i){
        return (
          vm.sendData.recvInfoLst.findIndex((item2) => {
            return item.phone === item2.phone
          }) === i
        );
      });
    },

	fnSetContsVarNms(){
      const rsvNmSet = new Set(['phone']);
      const conts = this.sendData.smsContent;
      let varNms = [];
      let containRsvNm = false;
      conts.replace(/\{\{(\w+)\}\}/g, function($0, $1) {
        if(rsvNmSet.has($1)){
          containRsvNm = true;
          return false;
        }
        varNms.push($1);
      });
      if(containRsvNm){
        confirm.fnAlert('RCS 발송', '발송 내용 변수 phone 은 예약어로 사용하실 수 없습니다.');
        return false;
      } else {
        this.sendData.contsVarNms = this.fnSetArrayRemoveDupliVal(varNms);
        return true;
      }
    },
	//array에 중복 항목을 제거한다.
    fnSetArrayRemoveDupliVal(array){
      let seen = {};
      return array.filter(function(item) {
        return seen.hasOwnProperty(item) ? false : (seen[item] = true);
      });
    },

	//SMS 템플릿 엑셀 다운로드
    async fnExcelTmplteDownLoad(){
    	if(this.fnSetContsVarNms() == false) return;
    	const params = {
        	contsVarNms : this.sendData.contsVarNms
      	};
      	await messageApi.excelDownSendSmsRecvTmplt(params);
    },

	// 저장 버튼 처리
	fnSaveRcsMsg(saveContent) {
		var vm = this;
		vm.sendData.saveContent = saveContent;

		eventBus.$on('callbackEventBus', this.fnSave);
      	confirm.fnConfirm("RCS 메시지", "RCS 메시지를저장하시겠습니까?", "확인");
	},

	// 저장
	fnSave() {
		var vali = this.fnSaveVali();
		if(!vali) return false;
		var params = {
			"data" : this.sendData,
			"templateRadioBtn" : this.templateRadioBtn
		}

		rcsTemplateSendApi.rcsMsgSave(params).then(response => {
			var result = response.data;
			var success = result.success;
			if(success) {
				confirm.fnAlert("저장 되었습니다.","");
			} else {
				confirm.fnAlert(result.message,"관리자에게 문의하세요.");
			}
		});


	},

	// 저장 유효성 검사
	fnSaveVali() {
		if(this.sendData.saveContent == "") {
			confirm.fnAlert("메세지 명을 입력해주세요.","");
			return false;
		} else {
			return true;
		}
	},

	// 메시지 보관함 세팅
	fnTmpMsgSet(data) {
		var vm = this;
		var returnRadioBtn = data.templateRadioBtn;
		var saveBoxId = data.saveBoxId;

		var params = {
			"saveBoxId" : saveBoxId,
			"templateRadioBtn" : returnRadioBtn
		}

		rcsTemplateSendApi.selectRcsMsgDetail(params).then(response => {
			var result = response.data;
			var data = result.data;
			if(returnRadioBtn == "text") {

				vm.sendData.brandId = data.brandId;
				vm.sendData.saveContent = data.saveContent;
				vm.sendData.textContents = data.textContents;
			} else if (returnRadioBtn == "te2t") {

			}
		});
	},

	// 템플릿 카드 버튼
	fnSetTemplateCard(type) {
		if("text"==type) {
			this.formatCard = true;
			this.carouSelType = false;
		} else {
			this.formatCard = false;
			this.carouSelType = false;
		}
	},

	// 포멧카드 선택시 버튼입력 버튼 활성화
	fnSetFormatCard() {
		this.formatCard = true;
		this.carouSelType = false;
	},

	// 캐러셀형
	fnSetCarousel() {
		this.formatCard = true;
		this.carouSelType = true;
	},

	// 캐러셀형 탭 클릭 이벤트 처리
	fnClickTab(n) {
		this.carouSelTabCnt = n;
	}
  }
}
</script>