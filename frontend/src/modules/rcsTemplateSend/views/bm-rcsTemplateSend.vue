<template>
		<article>
			<div class="contentHeader">
				<h2>발송 > RCS</h2>
				<!-- <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
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
									<input v-model="templateRadioBtn" type="radio" name="RCSTemplate1" value="des" id="pushTemplate1-1" class="radioStyle" checked=""><label for="pushTemplate1-1"></label>
									
								</div>
							</li>
							<li>
								<img src="@/assets/images/common/RCSTemplate1.png" alt="템플릿 승인(스타일)"><h6>템플릿 승인(스타일)</h6>
								<div class="consolMarginTop">
									<input v-model="templateRadioBtn" type="radio" name="RCSTemplate1" value="cell" id="pushTemplate1-2" class="radioStyle"><label for="pushTemplate1-2"></label>
								</div>
							</li>
							<li>
								<img src="@/assets/images/common/RCSTemplate2.png" alt="텍스트 미승인형"><h6>텍스트 미승인형</h6>
								<div class="consolMarginTop">
									<input v-model="templateRadioBtn" type="radio" name="RCSTemplate1" value="text" id="pushTemplate1-3" class="radioStyle"><label for="pushTemplate1-3"></label>
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
									<input v-model="templateRadioBtn" type="radio" name="RCSTemplate1" value="SS000000" id="RCSTemplate2-1" class="radioStyle" checked=""><label for="RCSTemplate2-1"></label>
								</div>
							</li>
							<li>
								<img src="@/assets/images/common/RCSTemplate4.png" alt="텍스트(LMS)"><h6>텍스트(LMS)</h6>
								<div class="consolMarginTop">
									<input v-model="templateRadioBtn" type="radio" name="RCSTemplate1" value="SL000000" id="RCSTemplate2-2" class="radioStyle"><label for="RCSTemplate2-2"></label>
								</div>
							</li>
							<li class="ml40">
								<img src="@/assets/images/common/RCSTemplate6.png" alt="세로형(short)"><h6>세로형 (Medium)</h6>
								<div class="consolMarginTop">
									<input v-model="templateRadioBtn" type="radio" name="RCSTemplate1" value="SMwThM00" id="RCSTemplate2-6-1" class="radioStyle"><label for="RCSTemplate2-6-1"></label>
								</div>
							</li>
							<li class="ml40">
								<img src="@/assets/images/common/RCSTemplate7.png" alt="세로형(Tall)"><h6>세로형 (Tall)</h6>
								<div class="consolMarginTop">
									<input v-model="templateRadioBtn" type="radio" name="RCSTemplate1" value="SMwThT00" id="RCSTemplate2-7" class="radioStyle"><label for="RCSTemplate2-7"></label>
								</div>
							</li>
							<li class="ml40">
								<img src="@/assets/images/common/RCSTemplate10.png" alt="캐러셀(short)"><h6>캐러셀 (Small)</h6>
								<div class="consolMarginTop">
									<input v-model="templateRadioBtn" type="radio" name="RCSTemplate1" value="carouselSmall" id="RCSTemplate2-10" class="radioStyle"><label for="RCSTemplate2-10"></label>
								</div>
							</li>
							<li>
								<img src="@/assets/images/common/RCSTemplate11.png" alt="캐러셀(Tall)"><h6>캐러셀 (Medium)</h6>
								<div class="consolMarginTop">
									<input v-model="templateRadioBtn" type="radio" name="RCSTemplate2" value="carouselMedium" id="RCSTemplate2-11" class="radioStyle"><label for="RCSTemplate2-11"></label>
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
						<div class="phoneWrap" style="height:660px">
							<img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
							<div v-show="pushTab=='push'" class="phoneCardWrap">
								<ul class="cardBxslider mt10">
									<li v-for="n in carouselSelect" class="slide cardBox">
										<img v-if="templateRadioBtn == 'carouselSmall' && sendData.carouselObj.imgUrl[n-1]==''" src="@/assets/images/common/cardThumImg.png" alt="프리 템플릿">
										<div v-if="templateRadioBtn == 'carouselSmall' && sendData.carouselObj.imgUrl[n-1]!=''" :style="'background-image: url('+sendData.carouselObj.imgUrl[n-1]+');padding:81px;'" class="mt10 text-center simulatorImg"> </div>
										<img v-if="templateRadioBtn == 'carouselMedium' && sendData.carouselObj.imgUrl[n-1]==''" src="@/assets/images/common/cardThumImg2_1.png" alt="프리 템플릿">
										<div v-if="templateRadioBtn == 'carouselMedium' && sendData.carouselObj.imgUrl[n-1]!=''" :style="'background-image: url('+sendData.carouselObj.imgUrl[n-1]+');padding:123px;'" class="mt10 text-center simulatorImg"> </div>
										<div class="relative">
											<div class="scroll-y" style="min-height:150px">
												<p class="color000 font-size13"><span v-if="sendData.adYn == 'yes'">(광고)</span>{{sendData.carouselObj.textTitle[n-1]}}</p>
												<p class="color3 mt5"><pre>{{sendData.carouselObj.textContents[n-1]}}</pre></p>
												<p v-if="sendData.freeReceiveNum != ''" class="color3">무료수신거부 : {{sendData.freeReceiveNum}}</p>
											</div>
										</div>
									</li>
								</ul>
								<div id="slide-counter"><span class="current"></span> / <span class="total"></span></div>
							</div>
							<div v-show="pushTab!='push'" class="phoneCardWrap">
								<div class="phoneText1">
									<div class="scroll-y">
										<div v-if="sendData.callbackImgUrl != ''" :style="'background-image: url('+sendData.callbackImgUrl+');padding:85px;'" class="mt10 text-center simulatorImg"> </div>
										<p class="mt15 font-size13"><span v-if="sendData.adYn=='yes' && sendData.senderType=='LMS'">(광고)</span>{{sendData.callbackTitle}}</p>
										<p class="mt15"><span v-if="sendData.adYn=='yes' && sendData.senderType=='SMS'">(광고)</span><pre>{{sendData.callbackContents}}</pre></p>
										<p calss="mt15" v-if="sendData.freeReceiveNum != ''">무료수신거부 : {{sendData.freeReceiveNum}}</p>
									</div>
								</div>
							</div>
						</div>
						<!-- //phoneWrap -->						
					</div>
					<div class="phone_04_btn">
						<a @click.prevent="fnclickPushBtn('push')" href="#self" class="btnStyle1 backBlack" title="RCS">RCS</a>
						<a @click.prevent="fnclickPushBtn('SMS')" v-if="sendData.senderType=='SMS'" href="#self" class="btnStyle1 backWhite" title="Push">SMS</a>
						<a @click.prevent="fnclickPushBtn('LMS')" v-if="sendData.senderType=='LMS'" href="#self" class="btnStyle1 backWhite" title="Push">LMS</a>								
						<a @click.prevent="fnclickPushBtn('MMS')" v-if="sendData.senderType=='MMS'" href="#self" class="btnStyle1 backWhite" title="Push">MMS</a>								
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
									<p class="mt15"><pre>{{text}}</pre></p>
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
									<p v-for="n in btnCnt" class="text-center mt20" style="color:#69C8FF">{{btnNm[n-1]}}</p>
								</div>
								 
							</div>
							<div v-if="templateRadioBtn =='text' || templateRadioBtn == 'SS000000' || templateRadioBtn == 'SL000000'" class="phoneText1">
								<div class="scroll-y">
									<h5 v-if="sendData.textTitle!=''"><span v-if="sendData.adYn=='yes' && templateRadioBtn == 'SL000000'">(광고)</span>{{sendData.textTitle}}</h5>
									<p class="mt15"><pre><span v-if="sendData.adYn=='yes' && templateRadioBtn == 'SS000000'">(광고)</span>{{sendData.textContents}}</pre></p>
									<p class="mt15" v-if="sendData.freeReceiveNum!=''">무료수신거부 : {{sendData.freeReceiveNum}}</p>
								</div>
								<p v-for="n in btnCnt" class="text-center mt20" style="color:#69C8FF">{{btnNm[n-1]}}</p>
							</div>
							<div v-if="templateRadioBtn == 'SMwThM00'">
								<img v-if="sendData.imgUrl == ''" :src="sendData.SMwThM00Img" alt="프리 템플릿">
								<div v-if="sendData.imgUrl != ''" :style="'background-image: url('+sendData.imgUrl+');padding:65px;'" class="mt10 text-center simulatorImg"> </div>
								<div style="background:#fff; border-radius: 0 0 5px 5px; min-height:180px" class="pd20">
									<h5><span v-if="sendData.adYn=='yes'">(광고)</span>{{sendData.textTitle}}</h5>
									<div class="scroll-y3">
										<p class="color6"><pre>{{sendData.textContents}}</pre></p>
										<p class="mt15" v-if="sendData.freeReceiveNum!=''">무료수신거부 : {{sendData.freeReceiveNum}}</p>
									</div>								
								</div>
								<p v-for="n in btnCnt" class="text-center mt20" style="color:#69C8FF">{{btnNm[n-1]}}</p>
							</div>
							<div v-if="templateRadioBtn == 'SMwThT00'">
								<img v-if="sendData.imgUrl == ''" :src="sendData.SMwThM00Img" alt="프리 템플릿">
								<div v-if="sendData.imgUrl != ''" :style="'background-image: url('+sendData.imgUrl+');padding:85px;'" class="mt10 text-center simulatorImg"> </div>
								<div style="background:#fff; border-radius: 0 0 5px 5px; min-height:170px" class="pd20">
									<h5><span v-if="sendData.adYn=='yes'">(광고)</span>{{sendData.textTitle}}</h5>
									<div class="scroll-y6">
										<p class="color6"><pre>{{sendData.textContents}}</pre></p>
										<p class="mt15" v-if="sendData.freeReceiveNum!=''">무료수신거부 : {{sendData.freeReceiveNum}}</p>
									</div>		
									<p v-for="n in btnCnt" class="text-center mt20" style="color:#69C8FF">{{btnNm[n-1]}}</p>						
								</div>
								
							</div>
						</div>
						<div  v-if="pushTab!='push'" class="phoneTextWrap">
							<div class="phoneText1">
								<div class="scroll-y">
									<div v-if="sendData.callbackImgUrl != ''" :style="'background-image: url('+sendData.callbackImgUrl+');padding:85px;'" class="mt10 text-center simulatorImg"> </div>
									<p class="mt15 font-size13"><span v-if="sendData.adYn=='yes' && sendData.senderType=='LMS'">(광고)</span>{{sendData.callbackTitle}}</p>
									<p class="mt15"><span v-if="sendData.adYn=='yes' && sendData.senderType=='SMS'">(광고)</span><pre>{{sendData.callbackContents}}</pre></p>
									<p calss="mt15" v-if="sendData.freeReceiveNum != ''">무료수신거부 : {{sendData.freeReceiveNum}}</p>
								</div>
							</div>
						</div>
						<div class="phone_04_btn">
							<a @click.prevent="fnclickPushBtn('push')" href="#self" class="btnStyle1 backBlack" title="RCS">RCS</a>
							<a @click.prevent="fnclickPushBtn('SMS')" v-if="sendData.senderType=='SMS'" href="#self" class="btnStyle1 backWhite" title="Push">SMS</a>
							<a @click.prevent="fnclickPushBtn('LMS')" v-if="sendData.senderType=='LMS'" href="#self" class="btnStyle1 backWhite" title="Push">LMS</a>								
							<a @click.prevent="fnclickPushBtn('MMS')" v-if="sendData.senderType=='MMS'" href="#self" class="btnStyle1 backWhite" title="Push">MMS</a>								
						</div>
					</div>
					<!-- //phoneWrap -->
				</div>
				<div class="of_h inline-block vertical-top consoleCon" style="width:60%">
				<div class="user-phone of_h">
						<div class="float-left" style="width:24%">
							<h4>01  브랜드 선택</h4>
						</div>
						<div class="float-left" style="width:76%">
							<div class="of_h consolMarginTop">
								<div style="width:18%" class="float-left">
									<h5>브랜드</h5>
								</div>
								<div style="width:82%">
									<select v-model="sendData.brandId" name="userConsole_sub020201_1" class="selectStyle2" style="width:70%" :disabled="templateSet">
										<option v-for="option in sendData.brandArr" v-bind:value="option.BRAND_ID">
											{{option.BRAND_NAME}}
										</option>
									</select>
								</div>
							</div>
						</div>	
					</div>
					<hr>
					<div class="user-phone of_h">
						<div class="float-left" style="width:24%">
							<h4>02  메시지 내용</h4>
						</div>
						<div class="float-left" style="width:76%">
							<a v-if="templateRadioBtn=='des' || templateRadioBtn=='cell'" @click.prevent="fnOpenRcsTemplatePopup" activity="READ" href="#self" class="btnStyle1 backLightGray mr10" data-toggle="modal" data-target="#templatePop" title="RCS 템플릿 선택">RCS 템플릿 선택</a>
							<a v-if="formatCard==true" @click.prevent="fnOpenMsgPop" activity="READ" href="#self" class="btnStyle1 backLightGray mr10" data-toggle="modal" data-target="#message" title="메세지보관함">메세지보관함</a>
							<a v-if="formatCard==true && carouSelType==false" @click.prevent="fnOpenRcsContentPop(0)" activity="READ" href="#self" class="btnStyle1 borderLightGray mr10" title="내용입력">내용입력</a>
							<a v-if="formatCard==true && carouSelType==false && templateRadioBtn!='text'" @click.prevent="fnOpenRcsBtnPop(0)" activity="READ" href="#self" class="btnStyle1 borderLightGray mr10" data-toggle="modal" data-target="#Recipient" title="버튼입력">버튼입력</a>
							<div v-if="carouSelType==true" class="inline-block ml20">
								<p class="inline-block mr10">카드개수</p>
								<select v-model="carouselSelect" name="userConsole_sub020204_1" class="selectStyle2" @change="changeCarousel">
									<option value=3>3</option>
									<option value=4>4</option>
									<option value=5>5</option>
									<option value=6>6</option>
								</select>
							</div>
							<div v-if="carouSelType==true" class="consolMarginTop">
								<ul class="tab_s7" role="tablist">
									<li @click="fnClickTab(n)" v-for="n in carouselSelect" role="presentation"><a href="#card" aria-controls="card" role="tab" data-toggle="tab">카드 {{n}}</a></li>
								</ul>

								<div class="tab-content">
									<!--  tab1 -->
									<div role="tabpanel" class="tab-paneactive" id="card">
										<div class="templateBox pd20">
											<!-- templateList -->
											<a @click.prevent="fnOpenRcsContentPop(carouSelTabCnt)" activity="READ" href="#self" class="btnStyle1 borderLightGray mr10" title="내용입력">내용입력</a>
											<a @click.prevent="fnOpenRcsBtnPop(carouSelTabCnt)" activity="READ" href="#self" class="btnStyle1 borderLightGray" title="버튼입력">버튼입력</a>
											<!-- //templateList -->
										</div>
									</div>
								</div>
							</div>
							<div v-if="formatCard==true && templateRadioBtn!='text'" class="of_h consolMarginTop">
								<div style="width:18%" class="float-left">
									<h5>광고</h5>
								</div>
								<div class="of_h">
									<input @change="fnChangeDataSet" v-model="sendData.adYn" type="radio" name="ad" value="no" id="Display" checked=""> <label for="Display" class="mr30">미표시</label>
									<input @change="fnChangeDataSet" v-model="sendData.adYn" type="radio" name="ad" value="yes" id="not-Display"> <label for="not-Display" class="mr20">표시</label>
									<div class="inline-block">
										<p class="inline-block mr10">무료수신거부</p><input v-model="sendData.freeReceiveNum" type="text" class="inputStyle" style="width:50%">
									</div>
								</div>
							</div>
							<div class="of_h consolMarginTop">
								<div style="width:18%" class="float-left">
									<h5>복사가능여부</h5>
								</div>
								<div style="width:82%">
									<input @change="fnChangeDataSet" v-model="sendData.copy" type="radio" name="copy" value="no" id="no" checked=""> <label for="no" class="mr30">불가능</label>
									<input @change="fnChangeDataSet" v-model="sendData.copy" type="radio" name="copy" value="yes" id="yes"> <label for="yes" class="mr30">가능</label>
									<p class="txtCaption color4">※ 단말 내 메시지에 대한 복사가능여부 설정입니다.</p>
								</div>
							</div>
							<div class="of_h consolMarginTop">
								<div style="width:18%" class="float-left">
									<h5>발신번호 *</h5>
								</div>
								<div style="width:82%">
									<select @change="fnChangeDataSet" v-model="sendData.callback" name="userConsole_sub020201_1" class="selectStyle2" style="width:30%">
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
									<input @change="fnChangeDataSet" v-model="sendData.senderType" type="radio" name="substitution" value="UNUSED" id="UNUSED" checked="" activity="READ"> <label for="UNUSED" class="mr30">미사용</label>
									<input @change="fnChangeDataSet" v-model="sendData.senderType" type="radio" name="substitution" value="SMS" id="SMS" activity="READ"> <label for="SMS" class="mr30" data-toggle="modal" data-target="#sender">SMS</label>
									<input @change="fnChangeDataSet" v-model="sendData.senderType" type="radio" name="substitution" value="LMS" id="LMS" activity="READ"> <label for="LMS" class="mr30" data-toggle="modal" data-target="#sender">LMS</label>
									<input v-if="templateRadioBtn == 'SMwThM00' || templateRadioBtn == 'SMwThT00' || templateRadioBtn == 'carouselSmall' || templateRadioBtn == 'carouselMedium'" @change="fnChangeDataSet" v-model="sendData.senderType" type="radio" name="substitution" value="MMS" id="MMS" activity="READ"> <label v-if="templateRadioBtn == 'SMwThM00' || templateRadioBtn == 'SMwThT00' || templateRadioBtn == 'carouselSmall' || templateRadioBtn == 'carouselMedium'" for="MMS" class="mr30" data-toggle="modal" data-target="#sender">MMS</label>
								</div>
							</div>	
						</div>	
					</div>
					<hr>
					<div class="of_h user-phone">
						<div class="float-left" style="width:24%">
							<h4>03  수신자 선택</h4>
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
										<input onclick="this.value=null;" ref="excelFile" type="file" style="display:none;" @change="fnReadFile" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
									</div>
								</div>
							</div>
							<div class="of_h">
								<div class="of_h float-right" style="width:82%">
									<p>수신자 : {{recvCnt}}명<a @click="fnRemoveRecvInfo();" class="btnStyle1 small backWhite ml10" title="수신자 삭제">수신자 삭제</a></p>
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
							<h4>04  발송옵션 선택</h4>
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
									<input type="text" class="inputStyle" style="width:100%" placeholder="캠페인 ID를 입력해주세요. (영문, 숫자만 가능)" v-model="sendData.campaignId" maxlength="20">
								</div>
							</div>
						</div>
					</div>

					<hr>
					<div class="of_h user-phone">
					<div style="width:24%" class="float-left">
						<h4>발송제한 금액</h4>
					</div>
					<div style="width:76%" class="float-left">
						<div class="of_h">
							<div style="width:40%" class="float-left">
								<h5 style="margin: 5px 0;">일 발송금액 / 일 발송제한금액</h5>
							</div>
							<div class="of_h" style="width:60%;">
								<p style="font-size: 14px; margin-top: 3px;">{{dayAmount | comma}} / {{daySenderLimitAmout | comma}}</p>
							</div>
						</div>
						<div class="of_h">
							<div style="width:40%" class="float-left">
								<h5 style="margin: 5px 0;">월 발송금액 / 월 발송제한금액</h5>
							</div>
							<div class="of_h" style="width:60%;">
								<p style="font-size: 14px; margin-top: 3px;">{{monthAmount | comma}} / {{monSenderLimitAmout | comma}}</p>
							</div>
						</div>
					</div>
					</div>
					<div class="float-right mt20">
						<a v-if="templateRadioBtn!='des' && templateRadioBtn !='cell'" activity="SAVE" data-toggle="modal" data-target="#save" href="#self" class="btnStyle2 backWhite float-left" title="저장">저장</a>
						<a @click.prevent="fnOpenTestSendInputPopup" activity="SAVE" href="#self" class="btnStyle2 float-left ml10" title="테스트 발송">테스트 발송</a>
						<a @click.prevent="fnSendData" activity="SAVE" href="#self" class="btnStyle2 backRed float-left ml10" title="발송">발송</a>
					</div>
				</div>
			</div>	

			<RcsTemplatePopup :propBrandId.sync="sendData.brandId" :templateRadioBtn.sync="templateRadioBtn" ref="rcsTemplatePop" @fnResult="fnSetTemplate"></RcsTemplatePopup>
			<RcsMsgPopup :templateRadioBtn.sync="templateRadioBtn" :carouselSmall.sync="carouselSmall" :carouselMedium.sync="carouselMedium" ref="rcsMsgPop" @fnTmpMsgSet="fnTmpMsgSet" ></RcsMsgPopup>
			<RcsContentPopup :templateRadioBtn.sync="templateRadioBtn" :contentPopCnt.sync="contentPopCnt" :dataSet.sync="dataSet" :sendData.sync="sendData" ref="rcsContentPop" @fnAddResult="fnSetAddContents"></RcsContentPopup>
			<RcsBtnPopup :templateRadioBtn.sync="templateRadioBtn" :btnPopCnt.sync="btnPopCnt" ref="rcsBtnPop" @fnAddBtnResult="fnSetAddBtns"></RcsBtnPopup>
			<RcsSenderPopup :senderType.sync="sendData.senderType" ref="rcsSenderPop"></RcsSenderPopup>
			<RcsSavePopup ref="rcsSavePop"></RcsSavePopup>
			<ConfirmPopup :newval.sync="newval" :oldval.sync="oldval" ref="confirmPop"></ConfirmPopup>
			<DirectInputPopup :directInputOpen.sync="directInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid" :recvInfoLst="sendData.recvInfoLst"></DirectInputPopup>
			<AddressInputPopup :addressInputOpen.sync="addressInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid"></AddressInputPopup>
			<TestSendInputPopup :testSendInputOpen.sync="testSendInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid" ref="testSendInputPopup"></TestSendInputPopup>
		</article>
</template>

<script>
import RcsTemplatePopup from "@/modules/rcsTemplateSend/components/bp-rcsTemplatePop.vue";
import RcsMsgPopup from "@/modules/rcsTemplateSend/components/bp-rcsMsgPopup.vue";
import RcsContentPopup from "@/modules/rcsTemplateSend/components/bp-rcsContentPopup.vue";
import RcsBtnPopup from "@/modules/rcsTemplateSend/components/bp-RcsBtnPopup.vue";
import RcsSenderPopup from "@/modules/rcsTemplateSend/components/bp-rcsTemplateSenderPop.vue";
import RcsSavePopup from "@/modules/rcsTemplateSend/components/bp-rcsSavePopup.vue";
import DirectInputPopup from "@/modules/message/components/bp-directInput.vue";
import AddressInputPopup from "@/modules/message/components/bp-addressInput.vue";
import TestSendInputPopup from "@/modules/message/components/bc-testSendInput.vue";
import Calendar from "@/components/Calendar.vue";
import rcsTemplateSendApi from "@/modules/rcsTemplateSend/service/api.js";
import messageApi from "@/modules/message/service/messageApi.js";
import ConfirmPopup from "@/modules/rcsTemplateSend/components/bp-confirmPopup.vue";

import XLSX from 'xlsx';

import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

var slider;

export default {
  name: "pushTemplateList",
  components: {
	  RcsTemplatePopup,
	  RcsMsgPopup,
	  RcsContentPopup,
	  RcsBtnPopup,
	  RcsSenderPopup,
	  RcsSavePopup,
	  DirectInputPopup,
	  AddressInputPopup,
	  TestSendInputPopup,
	  Calendar,
	  ConfirmPopup
  },
  filters: {
	comma (val) {
		return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ',')
	}
  },
  data() {
    return {
		newval : '',
		oldval : '',
		dataSet : false,				// dataSet (데이터가 입력될시 true로 변환 radio버튼으로 발송 스타일 변경시 true인 경우 경고)
		templateSet : false,			// template 형 선택시 brand disabled처리를 위한 flag
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
		carouselSelect : 3,				// 캐러셀 selectBox
		carouSelTabCnt : 1,				// 캐러셀 탭
		messagebaseId : "",				// 템플릿 선택 Id
		directInputOpen : false,		// 수신자 직접입력 버튼
		addressInputOpen : false,		// 주소록 검색 버튼
		testSendInputOpen : false,		// 테스트 발송 팝업 버튼
        templateRadioBtn : "des",       // 템플릿형 라디오 버튼
		recvCnt : 0,  					// 수신자명수
		carouselSmall : 'CMwShS0300',	// 캐러셀형 msgId
		carouselMedium : 'CMwMhM0300',	// 캐러셀형 msgId
		contentPopCnt : 0,				// 내용입력 폼 갯수
		btnPopCnt : 0,					// 버튼입력 폼 갯수
		tempFile: [],
		beforeCuInputType: '',
		monthAmount : 0,
		dayAmount : 0,
		apiKeyName : '',
		monSenderLimitAmout : '없음',
		daySenderLimitAmout : '없음',
		sendData : {
			messagebaseId : "",							// MSG ID
			brandId : "",								// 브랜드 ID
			brandArr : [],								// 브랜드 Arr
			textTitle : "",								// 텍스트 제목
			textContents : "",							// 텍스트 내용
			btnCnt : 0,                     			// 버튼 갯수
      		selectBtn: [],		  			 			// selectBox
			btnNm:[],			           				// 버튼 이름
			contents:[],		           				// 내용
			calendarTitle: [],	     
			calendarDes: [],	 
			initStartDate: [],
			initEndDate: [],

			imgUrl : "",								// 이미지
			fileId : "",								// 이미지
			wideImgYn : "",								// 이미지
			SMwThM00Img : require("@/assets/images/common/cardThumImg2_2.png"),	// 이미지 출력 src 세로형 Medium
			SMwThT00Img : require("@/assets/images/common/cardThumImg2_1.png"),	// 이미지 출력 src 세로형 Tall
			callback: '',  								// 발신번호
			callbackArr: [],							// 발신번호 selectBox
			copy: 'no',									// 복사 가능여부
			adYn: 'no',									// 광고여부
			freeReceiveNum: '',							// 무료수신거부
			senderType : 'UNUSED',						// 대체발송
			callbackTitle : "",							// 대체발송 TITLE
			callbackContents : "",						// 대체발송 CONTENTS
			callbackImgUrl : "",						// 대체발송 IMG			
			callbackFileId : "",						// 대체발송 fileId
			saveContent : "",							// 저장 메시지명
			carouselObj : {
				textTitle : ['','','','','',''],
				textContents : ['','','','','',''],
				imgUrl : ['','','','','',''],
				fileId : [],
				wideImgYn : [],
				btnArr : [],
			},							// 캐러셀형 데이터

			requiredCuid : false,  //app 로그인 ID 필수여부
			requiredCuPhone : true,  //수신자 폰번호 필수여부
			cuInputType:'',  //DICT, ADDR, EXCEL
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
			excelLimitRow: 0
		}

    }
  },
  watch : {
	  carouselSelect(newval, oldval) {
		  // type 변경
		  this.carouselSelect = newval*1;
	  },
	  'sendData.campaignId'(newval, oldval) {
		  return this.sendData.campaignId = this.sendData.campaignId.replace(/[^a-zA-Z0-9]/g, '');
	  },
	  templateRadioBtn (newval, oldval) {
		  this.newval = newval;
		  this.oldval = oldval;

		  this.fnConfirmChangeRadioBtn(newval, oldval);
	  },
	  'sendData.brandId' (newval, oldval){
		var vm = this;
		var params = {
			"approvalStatus" : "승인",
			"brandId" : newval
		}
		rcsTemplateSendApi.selectCallbackList(params).then(response => {
			var result = response.data;
			vm.sendData.callbackArr = result.data;
			if(vm.sendData.callbackArr.length > 0) {
				vm.sendData.callback = result.data[0].callback;
			} else {
				vm.sendData.callback = "";
			}
			
		});
	  },
	  recvCnt (newval, oldval) {
		  if(newval>30000) {
			  confirm.fnAlert("RCS 발송", "발송 최대 수신자 수는 30000명을 넘길 수 없습니다.");
			  this.fnRemoveRecvInfo();
		  }
	  }
  },
  mounted() {
	  this.fnExistApiKey();
	  this.fnInit();
  },
  methods: {
	async fnExistApiKey(){
      let params = {};
      await messageApi.selectApiKey(params).then(response =>{
        const result = response.data;
        if(result.success) {
          if(this.$gfnCommonUtils.isEmpty(result.data)){
            confirm.fnAlert('RCS 템플릿 발송', '해당 프로젝트의 사용가능한 API 키가 존재하지 않습니다.\n메시지 발송하실 수 없습니다.');
          } else {
            // 사용가능한 api 키가 존재하면 발송제한 금액 세팅
            this.fnSetSentAmount();
          }
        }
      });
    },
	// RCS 발송 발신번호 세팅
	fnInit() {
		var vm = this;
		var params = {};
		rcsTemplateSendApi.rcsTemplatePopInit(params).then(response => {
            var result = response.data;
			vm.sendData.brandId = result.data[0].BRAND_ID;
			vm.sendData.brandArr = result.data;
        });
	},
	fnSetSentAmount() {
		let params = {};
      	messageApi.setSentAmout(params).then(response =>{
        const result = response.data;
        if(result.success) {
          let resultData = result.data;
          this.monthAmount = resultData.amountMap.month + "원";
          this.dayAmount = resultData.amountMap.day + "원";
          this.apiKeyName = resultData.returnApiKeyMap.apiKey;
          this.monSenderLimitAmout = resultData.returnApiKeyMap.monSenderLimitAmount=="없음" ? resultData.returnApiKeyMap.monSenderLimitAmount : resultData.returnApiKeyMap.monSenderLimitAmount+"원";
          this.daySenderLimitAmout = resultData.returnApiKeyMap.daySenderLimitAmount=="없음" ? resultData.returnApiKeyMap.daySenderLimitAmount : resultData.returnApiKeyMap.daySenderLimitAmount+"원";
        }
      });
	},
	fnRemoveRecvInfo(){
		this.fnCallbackRecvInfoLst(null);
		this.$refs.excelFile.value = '';
		this.tempFile = [];
	},

	fnResetData() {
		var vm = this;
		var templateRadioBtn = this.templateRadioBtn;

		if(templateRadioBtn == "des" || templateRadioBtn == "cell" || templateRadioBtn == "text") {
			vm.fnSetTemplateCard(templateRadioBtn);
		} else if(templateRadioBtn == "carouselSmall" || templateRadioBtn == "carouselMedium") {
			vm.fnSetCarousel();
		} else {
			vm.fnSetFormatCard();
		}

		// 데이터 초기화
		this.dataSet = false;
		this.templateSet = false;
		this.pushTab = "push";
		this.imgsrc = require("@/assets/images/common/approve.png");
		this.text = "테스트입니다.[한글]";
		this.styleContentCnt = 0;			
		this.styleInput = [];				
		this.styleInputSec = [];				
		this.styleChk = [];	        	   
		this.contentTitle = "";				
		this.contentText = "";			
        this.btnCnt = 0;		     	  
		this.btnNm = [];		
		this.carouselSelect = 3;
		this.carouSelTabCnt = 1;
		this.messagebaseId = "";
		this.recvCnt = 0;
		this.carouselSmall = 'CMwShS0300';
		this.carouselMedium = 'CMwMhM0300';
		this.contentPopCnt = 0;
		this.btnPopCnt = 0;
		this.sendData.messagebaseId = "";
		this.sendData.textTitle = "";
		this.sendData.textContents = "";
		this.sendData.btnCnt = 0;
		this.sendData.selectBtn = [];
		this.sendData.btnNm = [];
		this.sendData.contents = [];
		this.sendData.calendarTitle = [];
		this.sendData.calendarDes = [];
		this.sendData.initStartDate = [];
		this.sendData.initEndDate = [];
		this.sendData.imgUrl = "";
		this.sendData.fileId = "";
		this.sendData.wideImgYn = "";
		this.sendData.SMwThM00Img = require("@/assets/images/common/cardThumImg2_2.png");
		this.sendData.SMwThT00Img = require("@/assets/images/common/cardThumImg2_1.png");
		this.sendData.callback = "";
		this.sendData.callbackArr = [];
		this.sendData.copy = "no";
		this.sendData.adYn = "no";
		this.sendData.freeReceiveNum = "";
		this.sendData.senderType = "UNUSED";
		this.sendData.callbackTitle = "";
		this.sendData.callbackContents = "";
		this.sendData.callbackImgUrl = "";
		this.sendData.callbackFileId = "";
		this.sendData.saveContent = "";
		this.sendData.cuInputType = "DICT";
		this.sendData.cuInfo = "";
		this.sendData.rsrvSendYn = "N";
		this.sendData.rsrvDate = this.$gfnCommonUtils.getCurretDate();
		this.sendData.rsrvHH = "00";
		this.sendData.rsrvMM = "00";
		this.sendData.campaignId = "";
		this.sendData.imgInfoList = [];
		this.sendData.recvInfoLst = [];
		this.sendData.contsVarNms = [];
		this.sendData.testRecvInfoLst = [];
		this.sendData.carouselObj.textTitle = ['','','','','',''];
		this.sendData.carouselObj.textContents = ['','','','','',''];
		this.sendData.carouselObj.imgUrl = ['','','','','',''];
		this.sendData.carouselObj.fileId = [];
		this.sendData.carouselObj.wideImgYn = [];
		this.sendData.carouselObj.btnArr = [];

	},
	
	callbackList() {
		var vm = this;
		var params = {
			"approvalStatus" : "승인",
			"brandId" : vm.sendData.brandId
		}
		rcsTemplateSendApi.selectCallbackList(params).then(response => {
			var result = response.data;
			vm.sendData.callbackArr = result.data;
			if(vm.sendData.callbackArr.length>0) {
				vm.sendData.callback = result.data[0].callback;
			} else {
				vm.sendData.callback = "";
			}		
		});
	},


	fnSetSlider() {
		slider = jQuery('.cardBxslider').bxSlider({
			auto: false,
			autoControls: false,
			slideWidth: 204,
			minSlides: 1,
			maxSlides: 6,
			slideMargin: 10,
			controls: true,
			pager: false,
			pagerType: 'short',
			touchEnabled : (navigator.maxTouchPoints > 0),
			autoHover: false,
			pause: 6000,
			onSliderLoad: function (currentIndex) {
				jQuery("#slide-counter .current").text(currentIndex+1);
			},
			onSlideBefore: function ($slideElement, oldIndex, newIndex) {
				jQuery("#slide-counter .current").text(newIndex+1);
			}
		});
		jQuery("#slide-counter .total").text(slider.getSlideCount());
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

	fnOpenRcsBtnPop(cnt) {
		this.btnPopCnt = cnt;
		jQuery("#recipient").modal("show");
	},
	
	fnSetTemplate(data) {
		var vm = this;
		vm.dataSet = true;
		vm.templateSet = true;
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
			vm.sendData.textContents = data.desContent;
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
		vm.dataSet = true;
		if(!vm.carouSelType) {
			vm.sendData.textTitle = params.title;
			vm.sendData.textContents = params.contents;
			vm.sendData.imgUrl = params.imgUrl;
			vm.sendData.fileId = params.fileId;
			vm.sendData.wideImgYn = params.wideImgYn;
			
		} else {
			var carouSelTabCnt = vm.carouSelTabCnt;

			vm.$set(vm.sendData.carouselObj.textTitle, carouSelTabCnt-1, params.title);
			vm.$set(vm.sendData.carouselObj.textContents, carouSelTabCnt-1, params.contents);
			vm.$set(vm.sendData.carouselObj.imgUrl, carouSelTabCnt-1, params.imgUrl);
			vm.$set(vm.sendData.carouselObj.fileId, carouSelTabCnt-1, params.fileId);
			vm.$set(vm.sendData.carouselObj.wideImgYn, carouSelTabCnt-1, params.wideImgYn);
		}
	},

	// 버튼 내용 입력 return
	fnSetAddBtns(data) {
		var vm = this;
		vm.dataSet = true;
		if(!vm.carouSelType) {
			vm.btnCnt = data.btnCnt;
			vm.btnNm = data.btnNm;
			vm.sendData.btnCnt = data.btnCnt;
			vm.sendData.selectBtn = data.selectBtn;
			vm.sendData.btnNm = data.btnNm;
			vm.sendData.contents = data.contents;
			vm.sendData.calendarTitle = data.calendarTitle;
			vm.sendData.calendarDes = data.calendarDes;
			vm.sendData.initStartDate = data.initStartDate;
			vm.sendData.initEndDate = data.initEndDate;
		} else {
			var carouSelTabCnt = vm.carouSelTabCnt;
			vm.sendData.carouselObj.btnArr[carouSelTabCnt-1] = new Object();
			vm.$set(vm.sendData.carouselObj.btnArr[carouSelTabCnt-1] , 'btnCnt' , data.btnCnt);
			vm.$set(vm.sendData.carouselObj.btnArr[carouSelTabCnt-1] , 'selectBtn' , data.selectBtn);
			vm.$set(vm.sendData.carouselObj.btnArr[carouSelTabCnt-1] , 'btnNm' , data.btnNm);
			vm.$set(vm.sendData.carouselObj.btnArr[carouSelTabCnt-1] , 'contents' , data.contents);
			vm.$set(vm.sendData.carouselObj.btnArr[carouSelTabCnt-1] , 'calendarTitle' , data.calendarTitle);
			vm.$set(vm.sendData.carouselObj.btnArr[carouSelTabCnt-1] , 'calendarDes' , data.calendarDes);
			vm.$set(vm.sendData.carouselObj.btnArr[carouSelTabCnt-1] , 'initStartDate' , data.initStartDate);
			vm.$set(vm.sendData.carouselObj.btnArr[carouSelTabCnt-1] , 'initEndDate' , data.initEndDate);
		}
	},

	// 대체발송 버튼 미리보기
	fnclickPushBtn(senderType) {
		var vm = this;
		var carouSelType = vm.carouSelType;
		if(carouSelType) {
			if(senderType=="push") {
				setTimeout(function() {
					slider.reloadSlider();
				}, 10);
			} 
		}
		this.pushTab = senderType;
	},

	// 대체발송 팝업 callback
	fnSenderTypeSet(data) {
		this.sendData.callbackTitle = data.senderTitle;
		this.sendData.callbackContents = data.senderContents;
		this.sendData.callbackImgUrl = data.senderImgUrl;
		this.sendData.callbackFileId = data.senderFileId;
	},

	fnClickCuInputType(e){
		this.tempFile = [];
		this.tempFile.push.apply(this.tempFile, this.$refs.excelFile.files);
		this.$refs.excelFile.value = '';

		if(this.sendData.cuInputType == e.target.value){
			this.fnChgCuInputType('N');
		}
		this.beforeCuInputType = this.sendData.cuInputType;
	},

	//수신자 입력 타입 변경시
    fnChgCuInputType(chgYn){
      if(this.$gfnCommonUtils.defaultIfEmpty(chgYn, 'Y') == 'Y'){
        if((this.beforeCuInputType != 'DICT' && this.beforeCuInputType != 'ADDR')
          || (this.sendData.cuInputType != 'DICT' && this.sendData.cuInputType != 'ADDR')){
          this.fnCallbackRecvInfoLst(null);  //수신자 입력 타입 변경시 수신자 정보 초기화
        }
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
	  var vm = this;
      const rsvNmSet = new Set(['phone']);

		// RCS 상품에 따라서 Validation 처리 및 textContents 확인해야됨.
	  var templateRadioBtn = vm.templateRadioBtn;
	  var conts = "";

	  // 버튼 종류에 따른 validation 세팅
	  if("des" == templateRadioBtn) {
		  conts = this.text;
	  } else if("cell" == templateRadioBtn) {
		var styleContentCnt = vm.styleContentCnt;
		for(var i=0; i<styleContentCnt; i++) {
			conts += vm.styleInput[i];
			conts += vm.styleInputSec[i];
		}
	  } else if("text" == templateRadioBtn) {
		  conts = vm.sendData.textContents;
	  } else if("SS000000" == templateRadioBtn) {
		  conts = vm.sendData.textContents;
	  } else if("SL000000" == templateRadioBtn || "SMwThM00" == templateRadioBtn || "SMwThT00" == templateRadioBtn) {
		  conts = vm.sendData.textTitle;
		  conts += vm.sendData.textContents;
	  } else {
		  for(var i=0; i<vm.sendData.carouselObj.textTitle.length; i++) {
			  conts += vm.sendData.carouselObj.textTitle[i];
			  conts += vm.sendData.carouselObj.textContents[i];
		  }
	  }

	  if("" == conts) {
		  confirm.fnAlert("RCS 발송", "전송하실 RCS 내용을 먼저 입력해 주세요.");
		  return false;
	  }

      let varNms = [];
      let containRsvNm = false;
      conts.replace(/\{\{(([a-z|A-Z|0-9|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|_])+)\}\}/g, function($0, $1) {
        if(rsvNmSet.has($1)){
          containRsvNm = true;
          return false;
        }
        varNms.push($1);
      });

	  var senderType = vm.sendData.senderType;
	  if(senderType != "UNUSED") {
		    var callbackContents = vm.sendData.callbackContents;
		  	callbackContents.replace(/#\{(([a-z|A-Z|0-9|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|_])+)\}/g, function($0, $1) {
			if(rsvNmSet.has($1)){
			containRsvNm = true;
			return false;
			}
			varNms.push($1);
		});
	  }

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
      	await rcsTemplateSendApi.excelDownSendRcsRecvTmplt(params);
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

		var carouselType = this.carouSelType;
		var templateRadioBtn = this.templateRadioBtn
		if(carouselType) {
			if(templateRadioBtn == "carouselSmall") {
				templateRadioBtn = this.carouselSmall;
			} else if(templateRadioBtn == "carouselMedium") {
				templateRadioBtn = this.carouselMedium;
			}
		}

		var params = {
			"data" : this.sendData,
			"templateRadioBtn" : templateRadioBtn,
			"carouSelType" : this.carouSelType
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
		vm.dataSet = true;
		var returnRadioBtn = data.templateRadioBtn;
		var saveBoxId = data.saveBoxId;

		var params = {
			"saveBoxId" : saveBoxId,
			"templateRadioBtn" : returnRadioBtn,
			"carouSelType" : vm.carouSelType
		}

		rcsTemplateSendApi.selectRcsMsgDetail(params).then(response => {
			var result = response.data;
			var data = result.data;
			var carouSelType = this.carouSelType;
			if(!carouSelType) {
				vm.sendData.brandId = data.brandId;						// brandId
				vm.sendData.saveContent = data.saveContent;				// 저장 메시지 명
				vm.sendData.textTitle = data.textTitle;					// 텍스트 제목 
				vm.sendData.textContents = data.textContents;			// 텍스트 내용
				vm.sendData.copy = data.copy;							// 복사 가능여부
				vm.sendData.adYn = data.adYn;							// 광고여부
				vm.sendData.freeReceiveNum = data.freeReceiveNum;		// 무료수신거부
				vm.sendData.senderType = data.senderType;				// 대체발송 radio
				vm.sendData.callbackTitle = data.callbackTitle;			// 대체발송 title
				vm.sendData.callbackContents = data.callbackContents;	// 대체발송 contents
				vm.sendData.callbackFileId = data.fileId;				// 대체발송 fileId
				vm.sendData.callbackImgUrl = data.imgUrl;				// 대체발송 imgUrl
				vm.sendData.imgUrl = data.imgUrl;						// 이미지
				vm.sendData.fileId = data.fileId;						// 이미지
				vm.sendData.btnCnt = data.btnCnt;                   	// 버튼 갯수
				vm.btnCnt = data.btnCnt;
				for(var i=0; i<data.btnCnt; i++) {
					vm.$set(vm.btnNm, i, data.btnNm[i]);
					vm.$set(vm.sendData.selectBtn, i, data.selectBtn[i]);
					vm.$set(vm.sendData.btnNm, i, data.btnNm[i]);
					vm.$set(vm.sendData.contents, i, data.contents[i]);
					vm.$set(vm.sendData.calendarTitle, i, data.calendarTitle[i]);
					vm.$set(vm.sendData.calendarDes, i, data.calendarDes[i]);
					vm.$set(vm.sendData.initStartDate, i, data.initStartDate[i]);
					vm.$set(vm.sendData.initEndDate, i, data.initEndDate[i]);
				}
			} else {
				vm.sendData.brandId = data.brandId;							// brandId
				vm.sendData.saveContent = data.saveContent;					// 저장 메시지 명
				vm.sendData.copy = data.copy;								// 복사 가능여부
				vm.sendData.adYn = data.adYn;								// 광고여부
				vm.sendData.freeReceiveNum = data.freeReceiveNum;			// 무료수신거부
				vm.sendData.senderType = data.senderType;					// 대체발송 radio
				vm.sendData.callbackTitle = data.callbackTitle;				// 대체발송 title
				vm.sendData.callbackContents = data.callbackContents;		// 대체발송 contents
				vm.sendData.callbackFileId = data.fileId;					// 대체발송 fileId
				vm.sendData.callbackImgUrl = data.imgUrl;					// 대체발송 imgUrl
				vm.sendData.carouselObj.textTitle = data.textTitle;			// 텍스트 제목
				vm.sendData.carouselObj.textContents = data.textContents;	// 텍스트 내용
				vm.sendData.carouselObj.imgUrl = data.imgUrl;				// 이미지
				vm.sendData.carouselObj.fileId = data.fileId;				// 이미지
				vm.sendData.carouselObj.btnArr = data.btnArr;				// 버튼

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
		this.fnInit();

		this.formatCard = true;
		this.carouSelType = true;
		var vm = this;

		setTimeout(function() {
			vm.fnSetSlider();
		}, 10)

	},

	fnConfirmChangeRadioBtn(oldval, newval) {
		var dataSet = this.dataSet;
		if(dataSet) {
			jQuery("#confirm").modal("show");
		} else {
			this.fnResetData();
		}
	},
	parentFunction() {
		this.templateRadioBtn = this.oldval;
	},

	// 캐러셀형 탭 클릭 이벤트 처리
	fnClickTab(n) {
		this.carouSelTabCnt = n;
	},

	// 캐러셀 형 카드 갯수 변경시 msgId 세팅
	changeCarousel () {
		var vm = this;
		var carouselSelect = vm.carouselSelect;

		if(carouselSelect==3) {
			vm.carouselSmall = 'CMwShS0300';
			vm.carouselMedium = 'CMwMhM0300';
		} else if(carouselSelect==4) {
			vm.carouselSmall = 'CMwShS0400';
			vm.carouselMedium = 'CMwMhM0400';
		} else if(carouselSelect==5) {
			vm.carouselSmall = 'CMwShS0500';
			vm.carouselMedium = 'CMwMhM0500';
		} else if(carouselSelect==6) {
			vm.carouselSmall = 'CMwShS0600';
			vm.carouselMedium = 'CMwMhM0600';
		}

		setTimeout(function() {
			slider.reloadSlider();
		}, 10);
		jQuery("#slide-counter .total").text(carouselSelect);
	},

	fnSendData() {
		var vali = this.validation("real");
		if(!vali) return false;

		var vm = this;

		// 캐러셀형 msgId 세팅
		var templateRadioBtn = this.templateRadioBtn;
		if(templateRadioBtn == "carouselSmall") {
			templateRadioBtn = vm.carouselSmall;
		} else if(templateRadioBtn == "carouselMedium") {
			templateRadioBtn = vm.carouselMedium;
		}

		var params = {
			"data" : this.sendData,
			"templateRadioBtn" : templateRadioBtn,
			"brandId" : this.sendData.brandId,
			"carouselSelect" : this.carouselSelect,
			"real" : true
		}

		let fd = new FormData();
		if(this.sendData.cuInputType == 'EXCEL'){
			fd.append('file', this.tempFile[0]);
			params.excelLimitRow = this.recvCnt;
			this.$refs.excelFile.value = '';
			this.tempFile = [];
		}
		fd.append('paramString', JSON.stringify(params));
		rcsTemplateSendApi.sendRcsData(fd).then(response => {
			var result = response.data;
			var success = result.success;
			if(success) {
				confirm.fnAlert("RCS 발송", "메시지 발송처리를 완료하였습니다. 메시지 발송 성공/실패는 조회의 '전체' 메뉴에서 확인이 가능합니다.");
				// 발송 완료 후 데이터 리셋처리
				this.fnResetData();
			} else {
				confirm.fnAlert("RCS 발송", result.message);
			}
		});

		// 
		this.fnSetSentAmount();
	},

	// 테스트 발송
	fnTestSendData() {
		var vali = this.validation("test");
		if(!vali) return false;

		// 캐러셀형 msgId 세팅
		var templateRadioBtn = this.templateRadioBtn;
		if(templateRadioBtn == "carouselSmall") {
			templateRadioBtn = vm.carouselSmall;
		} else if(templateRadioBtn == "carouselMedium") {
			templateRadioBtn = vm.carouselMedium;
		}

		var params = {
			"data" : this.sendData,
			"templateRadioBtn" : this.templateRadioBtn,
			"brandId" : this.sendData.brandId,
			"carouselSelect" : this.carouselSelect,
			"real" : false
		}

		rcsTemplateSendApi.sendRcsData(params).then(response => {
			var result = response.data;
			var success = result.success;
			if(success) {
				if("" == result.message || null == result.message) confirm.fnAlert("RCS 발송", "테스트 메세지 발송처리를 완료하였습니다.");
				else confirm.fnAlert("RCS 발송", result.message);

			} else {
				confirm.fnAlert("RCS 발송", result.message);
			}
		});
	},

	fnOpenTestSendInputPopup(){
	  this.fnSetContsVarNms();
      this.$refs.testSendInputPopup.fnSetTestRecvInfoLst(this.sendData.testRecvInfoLst);
      this.testSendInputOpen = !this.testSendInputOpen;
    },

	fnCallbackTestRecvInfoLst(testRecvInfoLst){
      if(testRecvInfoLst != null){
        this.sendData.testRecvInfoLst = Object.assign([], testRecvInfoLst);
        this.fnTestSendData();
      } else {
        this.sendData.testRecvInfoLst = [];
      }
    },

	validation(sendFlag) {
		var vm = this;
		var templateRadioBtn = this.templateRadioBtn;
		if(templateRadioBtn == "des" || templateRadioBtn == "cell") {
			var messagebaseId = vm.sendData.messagebaseId;
			if("" == messagebaseId) {
				confirm.fnAlert("RCS 발송", "템플릿을 선택해주세요.");
				return false;
			}
		} else if(templateRadioBtn == "text") {
			var textContents = vm.sendData.textContents;
			if("" == textContents) {
				confirm.fnAlert("RCS 발송", "내용을 입력해주세요.");
				return false;
			}
		} else if(templateRadioBtn == "SS000000") {
			var textContents = vm.sendData.textContents;
			if("" == textContents) {
				confirm.fnAlert("RCS 발송", "내용을 입력해주세요.");
				return false;
			}
		} else if(templateRadioBtn == "SL000000") {
			var textTitle = vm.sendData.textTitle;
			var textContents = vm.sendData.textContents;
			if("" == textTitle) {
				confirm.fnAlert("RCS 발송", "제목을 입력해주세요.");
				return false;
			}
			if("" == textContents) {
				confirm.fnAlert("RCS 발송", "내용을 입력해주세요.");
				return false;
			}
		} else if(templateRadioBtn == "SMwThM00" || templateRadioBtn == "SMwThT00") {
			var textTitle = vm.sendData.textTitle;
			var textContents = vm.sendData.textContents;
			var imgUrl = vm.sendData.imgUrl;
			if("" == textTitle) {
				confirm.fnAlert("RCS 발송", "제목을 입력해주세요.");
				return false;
			}
			if("" == textContents) {
				confirm.fnAlert("RCS 발송", "내용을 입력해주세요.");
				return false;
			}
			if("" == imgUrl) {
				confirm.fnAlert("RCS 발송", "이미지를 선택해주세요.");
				return false;
			}
		} else {
			var carouselObj = vm.sendData.carouselObj;
			var carouselSelect = vm.carouselSelect;
			for(var i=0; i<carouselSelect; i++) {
				if("" == carouselObj.textTitle[i]) {
					confirm.fnAlert("RCS 발송", "내용입력을 완료해주세요.");
					return false;
				}
				if("" == carouselObj.textContents[i]) {
					confirm.fnAlert("RCS 발송", "내용입력을 완료해주세요.");
					return false;
				}
			}
		}

		// 무료 수신 거부 validation
		var adYn = vm.sendData.adYn;
		if(adYn == "yes") {
			var freeReceiveNum = vm.sendData.freeReceiveNum;
			if(freeReceiveNum == "") {
				confirm.fnAlert("RCS 발송", "무료수신거부 번호를 입력해 주세요.");
				return false;
			}
		}

		// 발신번호 validation
		var callback = vm.sendData.callback;
		if(callback == "") {
			confirm.fnAlert("RCS 발송", "발신번호를 선택해 주세요.");
			return false;
		}

		// 대체 발송 validation
		var senderType = vm.sendData.senderType;
		if(senderType == "SMS") {
			var callbackContents = vm.sendData.callbackContents;
			if(callbackContents == "") {
				confirm.fnAlert("RCS 발송", "대체발송 내용을 입력해 주세요.");
				return false;
			}
		} else if (senderType == "LMS") {
			var callbackTitle = vm.sendData.callbackTitle;
			var callbackContents = vm.sendData.callbackContents;
			if(callbackTitle == "") {
				confirm.fnAlert("RCS 발송", "대체발송 제목을 입력해 주세요.");
				return false;
			}
			if(callbackContents == "") {
				confirm.fnAlert("RCS 발송", "대체발송 내용을 입력해 주세요.");
				return false;
			}
		}

		// 수신자 목록 validation 실제 발송일 경우에만 체크
		if(sendFlag == "real") {
			var recvInfoLst = vm.sendData.recvInfoLst;
			var cuInputType = vm.sendData.cuInputType;

			if(cuInputType != "EXCEL") {
				if(recvInfoLst.length==0) {
					confirm.fnAlert("RCS 발송", "수신자를 입력해 주세요.");
					return false;
				}
			} else {
				if(this.$refs.excelFile.value != 0){
					this.tempFile = [];
					this.tempFile.push.apply(this.tempFile, this.$refs.excelFile.files);
				}
				var excelFile = this.tempFile[0];
				if(excelFile == null) {
					confirm.fnAlert("RCS 발송", "수신자를 입력해 주세요.");
					return false;
				}
			}

		}

		return true;
	},

	// 수정 여부 dataset 처리
	fnChangeDataSet() {
		this.dataSet = true;
	},

	fnReadFile() {
		if(this.$refs.excelFile.files && this.$refs.excelFile.files.length > 0){
			const file = this.$refs.excelFile.files[0];
			let reader = new FileReader();

			reader.onload = (e) => {
			let data = reader.result;
			let workbook = XLSX.read(data, {type: 'binary'});
			let sheetName = '';
			let excelArray = [];
			
			if(workbook.SheetNames && workbook.SheetNames.length > 0){
				sheetName = workbook.SheetNames[0];
			}

			if(this.$gfnCommonUtils.isEmpty(sheetName) == false){
				excelArray = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName]);
			}

			let recvArr = [];

			for(var i=0; i<excelArray.length; i++) {
				if(i!=(excelArray.length-1)) recvArr[i] = excelArray[i+1].Template;
			}

			recvArr = new Set(recvArr);

			this.recvCnt = recvArr.size;
			};
			reader.readAsBinaryString(file);
		} else {
			this.recvCnt = 0;
		}
	}
  }
}
</script>