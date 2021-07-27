<template autocomplete="off">
	<div class="row row-no-margin">
		<div class="contentHeader">
			<h2>통합발송 템플릿</h2>
			<a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="이용안내">이용안내 <i class="fal fa-book-open"></i></a>
		</div>

		<ImageManagePopUp @img-callback="fnPushCallbackImgInfo" :imgMngOpen.sync="pushImgMngOpen" :useCh="pushUseCh" ref="pushImgMng"></ImageManagePopUp>
		<ImageUploadPopUp :imgUploadOpen.sync="pushImgUploadOpen"></ImageUploadPopUp>

		<Image90ManagePopUp @img-callback="fnRcs90CallbackImgInfo" :imgMngOpen.sync="rcs90ImgMngOpen" :useCh="rcs90UseCh" ref="rcs90ImgMng"></Image90ManagePopUp>
		<Image90UploadPopUp :imgUploadOpen.sync="rcs90ImgUploadOpen"></Image90UploadPopUp>

		<Image91ManagePopUp @img-callback="fnRcs91CallbackImgInfo" :imgMngOpen.sync="rcs91ImgMngOpen" :useCh="rcs91UseCh" ref="rcs91ImgMng"></Image91ManagePopUp>
		<Image91UploadPopUp :imgUploadOpen.sync="rcs91ImgUploadOpen"></Image91UploadPopUp>

		<Image92ManagePopUp @img-callback="fnRcs92CallbackImgInfo" :imgMngOpen.sync="rcs92ImgMngOpen" :useCh="rcs92UseCh" ref="rcs92ImgMng"></Image92ManagePopUp>
		<Image92UploadPopUp :imgUploadOpen.sync="rcs92ImgUploadOpen"></Image92UploadPopUp>

		<Image93ManagePopUp @img-callback="fnRcs93CallbackImgInfo" :imgMngOpen.sync="rcs93ImgMngOpen" :useCh="rcs93UseCh" ref="rcs93ImgMng"></Image93ManagePopUp>
		<Image93UploadPopUp :imgUploadOpen.sync="rcs93ImgUploadOpen"></Image93UploadPopUp>

		<Image94ManagePopUp @img-callback="fnRcs94CallbackImgInfo" :imgMngOpen.sync="rcs94ImgMngOpen" :useCh="rcs94UseCh" ref="rcs94ImgMng"></Image94ManagePopUp>
		<Image94UploadPopUp :imgUploadOpen.sync="rcs94ImgUploadOpen"></Image94UploadPopUp>

		<Image95ManagePopUp @img-callback="fnRcs95CallbackImgInfo" :imgMngOpen.sync="rcs95ImgMngOpen" :useCh="rcs95UseCh" ref="rcs95ImgMng"></Image95ManagePopUp>
		<Image95UploadPopUp :imgUploadOpen.sync="rcs95ImgUploadOpen"></Image95UploadPopUp>

		<Image100ManagePopUp @img-callback="fnRcs100CallbackImgInfo" :imgMngOpen.sync="rcs100ImgMngOpen" :useCh="rcs100UseCh" ref="rcs100ImgMng"></Image100ManagePopUp>
		<Image100UploadPopUp :imgUploadOpen.sync="rcs100ImgUploadOpen"></Image100UploadPopUp>

		<Image101ManagePopUp @img-callback="fnRcs101CallbackImgInfo" :imgMngOpen.sync="rcs101ImgMngOpen" :useCh="rcs101UseCh" ref="rcs101ImgMng"></Image101ManagePopUp>
		<Image101UploadPopUp :imgUploadOpen.sync="rcs101ImgUploadOpen"></Image101UploadPopUp>

		<Image102ManagePopUp @img-callback="fnRcs102CallbackImgInfo" :imgMngOpen.sync="rcs102ImgMngOpen" :useCh="rcs102UseCh" ref="rcs102ImgMng"></Image102ManagePopUp>
		<Image102UploadPopUp :imgUploadOpen.sync="rcs102ImgUploadOpen"></Image102UploadPopUp>

		<Image103ManagePopUp @img-callback="fnRcs103CallbackImgInfo" :imgMngOpen.sync="rcs103ImgMngOpen" :useCh="rcs103UseCh" ref="rcs103ImgMng"></Image103ManagePopUp>
		<Image103UploadPopUp :imgUploadOpen.sync="rcs103ImgUploadOpen"></Image103UploadPopUp>

		<Image104ManagePopUp @img-callback="fnRcs104CallbackImgInfo" :imgMngOpen.sync="rcs104ImgMngOpen" :useCh="rcs104UseCh" ref="rcs104ImgMng"></Image104ManagePopUp>
		<Image104UploadPopUp :imgUploadOpen.sync="rcs104ImgUploadOpen"></Image104UploadPopUp>

		<Image105ManagePopUp @img-callback="fnRcs105CallbackImgInfo" :imgMngOpen.sync="rcs105ImgMngOpen" :useCh="rcs105UseCh" ref="rcs105ImgMng"></Image105ManagePopUp>
		<Image105UploadPopUp :imgUploadOpen.sync="rcs105ImgUploadOpen"></Image105UploadPopUp>

		<ImageShortManagePopUp @img-callback="fnRcsShortCallbackImgInfo" :imgMngOpen.sync="rcsShortImgMngOpen" :useCh="rcsShortUseCh" ref="rcsShortImgMng"></ImageShortManagePopUp>
		<ImageShortUploadPopUp :imgUploadOpen.sync="rcsShortImgUploadOpen"></ImageShortUploadPopUp>

		<ImageTallManagePopUp @img-callback="fnRcsTallCallbackImgInfo" :imgMngOpen.sync="rcsTallImgMngOpen" :useCh="rcsTallUseCh" ref="rcsTallImgMng"></ImageTallManagePopUp>
		<ImageTallUploadPopUp :imgUploadOpen.sync="rcsTallImgUploadOpen"></ImageTallUploadPopUp>

		<ImageFriendTalkManagePopUp @img-callback="fnFriendTalkCallbackImgInfo" :imgMngOpen.sync="friendTalkImgMngOpen" :useCh="friendTalkUseCh" ref="friendTalkImgMng"></ImageFriendTalkManagePopUp>
		<ImageFriendTalkUploadPopUp :imgUploadOpen.sync="friendTalkImgUploadOpen"></ImageFriendTalkUploadPopUp>

		<ImageSmsManagePopUp @img-callback="fnSmsCallbackImgInfo" :imgMngOpen.sync="smsImgMngOpen" :useCh="smsUseCh" ref="smsImgMng"></ImageSmsManagePopUp>
		<ImageSmsUploadPopUp :imgUploadOpen.sync="smsImgUploadOpen"></ImageSmsUploadPopUp>

		<!-- 본문 -->
		<div class="row">
			<div class="col-xs-7" style="padding-right:100px">
				<h4 class="topH4">01 발송정보</h4>
				<div class="of_h">
					<div class="float-left" style="width:34%"><h5>메시지 구분*</h5></div>
					<div class="float-left" style="width:66%">
						<input type="radio" name="mSort" value="I" id="mSort1" checked="" v-model="rowData.msgKind" v-on:click="checkMsgKind('I')"><label for="mSort1" class="mr20">정보성</label>
						<input type="radio" name="mSort" value="A" id="mSort2" v-model="rowData.msgKind" v-on:click="checkMsgKind('A')"><label for="mSort2">광고용</label>
						<span class="txtCaption vertical-middle colorRed">광고 메시지는 20시~8시 발송이 제한됩니다.</span>
					</div>
				</div>
				<div class="of_h mt15">
					<div class="float-left" style="width:34%"><h5>메시지 타입*</h5></div>
					<div class="float-left" style="width:66%">
						<input type="radio" name="mType" value="BASE" id="mType_text" checked="" v-model="rowData.msgType"> <label for="mType_text" class="mr20">텍스트</label>
						<input type="radio" name="mType" value="IMAGE" id="mType_image" v-model="rowData.msgType"> <label for="mType_image">이미지</label>
					</div>
				</div>
				<div class="of_h mt15">
					<div class="float-left" style="width:34%"><h5>타 프로젝트 사용여부*</h5></div>
					<div class="float-left" style="width:66%">
						<input type="radio" name="otherUse" value="Y" id="otherUse1" checked="" v-model="rowData.otherProjectUseYn"> <label for="otherUse1" class="mr20">공용</label>
						<input type="radio" name="otherUse" value="N" id="otherUse2" v-model="rowData.otherProjectUseYn"> <label for="otherUse2">전용</label>
					</div>
				</div>
				<div class="of_h mt15">
					<div class="float-left" style="width:34%"><h5>템플릿 명</h5></div>
					<div class="float-left" style="width:66%">
						<div class="float-left" style="width:68%"><input type="text" class="inputStyle" v-model="rowData.tmpltTitle"></div>
					</div>
				</div>
			</div>

			<div class="col-xs-4" style="border-left:1px solid #D5D5D5; padding-left:100px">
				<h4 class="topH4">02 채널 선택</h4>
				<div class="of_h">
					<div class="float-left" style="width:50%">
						<div class="of_h">
							<div class="float-right text-center" style="width:100%">&nbsp;</div>
						</div>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:60%"><h5>PUSH</h5></div>
							<div class="float-left mt5" style="width:40%">
								<input type="checkbox" id="PUSH" class="checkStyle2" value="PUSH" v-model="rowData.checkedChannel" @click="toggleOnOffPush">
								<label for="PUSH"></label>
							</div>
						</div>
						<div class="of_h mt15">
							<div class="float-left" style="width:60%"><h5>RCS</h5></div>
							<div class="float-left mt5" style="width:40%">
								<input type="checkbox" id="RCS" class="checkStyle2" value="RCS" v-model="rowData.checkedChannel" @click="toggleOnOffRCS">
								<label for="RCS"></label>
							</div>
						</div>
						<div class="of_h mt15">
							<div class="float-left" style="width:60%"><h5>카카오톡</h5></div>
							<div class="float-left mt5" style="width:40%">
								<input type="checkbox" id="KAKAO" class="checkStyle2" value="KAKAO" v-model="rowData.checkedChannel" @click="toggleOnOffKakao">
								<label for="KAKAO"></label>
							</div>
						</div>
						<div class="of_h mt15">
							<div class="float-left" style="width:60%"><h5>SMS/MMS</h5></div>
							<div class="float-left mt5" style="width:40%">
								<input type="checkbox" id="SMSMMS" class="checkStyle2" value="SMSMMS" v-model="rowData.checkedChannel" @click="toggleOnOffSmsMms">
								<label for="SMSMMS"></label>
							</div>
						</div>
					</div>
					<div class="float-right text-center" style="width:50%">
						<div class="of_h">
							<div class="float-right text-center" style="width:100%">발송순서</div>
						</div>
						<div v-for="(channel, index) in rowData.checkedChannel" :key="channel" class="of_h mt13">
							<div class="float-left" style="width:100%"><a class="btnStyle1 borderLightGray " style="min-width:auto; width:100%"  @click="putData(index)">{{channel}}</a></div>
						</div>
					</div>
				</div>
				<div class="of_h consolMarginTop">
					<div class="float-right text-center" style="width:50%">
						<a @click="moveup()" title="위버튼"><i class="far fa-chevron-up channelBtn"></i></a>
						<a @click="movedown()" title="아래버튼"><i class="far fa-chevron-down channelBtn"></i></a>
					</div>
				</div>
			</div>
		</div>
		<hr>
		<h4>03 채널 설정</h4>
		<div class="contentBody">
			<ul class="tab_s3">
				<li v-bind:class="[ channelTab === 0 ? 'active' : '']"><a title="Push 페이지로 이동"           v-on:click="channelTab=0" v-bind:class="[ channelTab === 0 ? 'active' : '']" v-if="checkedPush">Push</a></li>
				<li v-bind:class="[ channelTab === 1 ? 'active' : '']"><a title="RCS 페이지로 이동"            v-on:click="channelTab=1" v-bind:class="[ channelTab === 1 ? 'active' : '']" v-if="checkedRCS">RCS</a></li>
				<li v-bind:class="[ channelTab === 2 ? 'active' : '']"><a title="카카오톡 페이지로 이동"         v-on:click="channelTab=2" v-bind:class="[ channelTab === 2 ? 'active' : '']" v-if="checkedKakao">카카오톡</a></li>
				<li v-bind:class="[ channelTab === 3 ? 'active' : '']"><a title="SMS/MMS 페이지로 이동"        v-on:click="channelTab=3" v-bind:class="[ channelTab === 3 ? 'active' : '']" v-if="checkedSmsMms">SMS/MMS</a></li>
			</ul>
		</div>

		<!-- PUSH -->
		<div class="of_h mt20" v-show="channelTab === 0" >
			<div class="float-left" style="width:28%">
				<!-- phoneWrap -->
				<div class="phoneWrap">
					<img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
					<div class="phoneTextWrap scroll-y">
						<div class="phoneText2">
							<p v-if="fnIsEmpty(rowData.pushTitle)">템플릿 제목</p>
							<p v-else>{{rowData.pushTitle}}</p>
						</div>
						<div v-if="rowData.msgType == 'IMAGE' && fnIsEmpty(rowData.pushImgInfo.imgUrl)" class="phoneText2 mt10 text-center" style="padding:65px">
							<i class="fas fa-image-polaroid" style="font-size:38px; color:#D5D5D5"></i>
							<p class="font-size14 color3 mt15">이미지 영역</p>
						</div>
						<div v-if="rowData.msgType == 'IMAGE' && !fnIsEmpty(rowData.pushImgInfo.imgUrl)" class="phoneText2 mt10 text-center"
							:style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.pushImgInfo.imgUrl+');'">
						</div>
						<div>
							<p v-if="fnIsEmpty(rowData.pushContent) && (rowData.msgKind != 'A' || fnIsEmpty(rowData.pushHowToDenyReceipt))" class="font-size14 color4 mt10">템플릿 내용</p>
							<p v-else class="font-size14 color4 mt10">
								<span><pre>{{rowData.pushContent}}</pre></span>
								<br v-if="!fnIsEmpty(rowData.pushContent)"/>
								<span v-if="rowData.msgKind == 'A' && !fnIsEmpty(rowData.pushHowToDenyReceipt)">
									{{rowData.pushHowToDenyReceipt}}
								</span>
							</p>
						</div>
					</div>
				</div>
				<!-- //phoneWrap -->        
			</div>
			<div class="float-left consoleCon" style="width:72%">
				<div class="of_h">
					<div class="float-left" style="width:13%"><h4>발송 정책 *</h4></div>
					<div class="float-left" style="width:57%">
						<input type="radio" name="sendP" value="ALL" id="sendP1" checked="" v-model="rowData.pushSend"> <label for="sendP1" class="mr30">ALL</label>
						<input type="radio" name="sendP" value="FCM" id="sendP2" v-model="rowData.pushSend"> <label for="sendP2" class="mr30">FCM</label>
						<input type="radio" name="sendP" value="APNS" id="sendP3" v-model="rowData.pushSend"> <label for="sendP3">APNS</label>
					</div>
				</div>
				<div class="of_h">
					<div class="float-left" style="width:13%"><h4>제목</h4></div>
					<div class="float-left" style="width:57%">
						<input type="text" class="inputStyle" name="pushTitle" v-model="rowData.pushTitle" id="pushTitleId" placeholder="최대 50자 입력 가능합니다." @keyup="fnTextLength('제목', '#pushTitleId', '', '50')">
					</div>
				</div>
				<div class="of_h">
					<div class="float-left" style="width:13%"><h4>내용*</h4></div>
					<div class="float-left" style="width:57%">
						<textarea class="textareaStyle height190" name="pushContent" :placeholder="pushPlaceHoder" v-model="rowData.pushContent" id="pushContentId" @keyup="fnTextLength('내용', '#pushContentId', '#pushTextLength', '512')"></textarea>
						<strong class="letter" id="pushTextLength">(00 / 512)</strong>
						<p class="color5 txtCaption">광고성 메시지 발송시, 자동으로 (광고)가 표시되오니, 내용에 (광고)문구는 입력하지 않아도 됩니다.</p>
					</div>
				</div>
				<div v-if="rowData.msgKind == 'A'">
					<div class="of_h consolMarginTop">
						<div class="float-left" style="width:13%"><h4>수신거부방법 *</h4></div>
						<div class="float-left" style="width:57%">
							<input type="text" class="inputStyle" name="pushHowToDenyReceipt" v-model="rowData.pushHowToDenyReceipt" placeholder="설정 > 푸시 알림 설정 변경">
							<p class="color5 txtCaption">광고성 메시지 발송시, 자동으로 (광고)가 표시되오니, 내용에 (광고)문구는 입력하지 않아도 됩니다.</p>
						</div>
					</div>
				</div>
				<div class="of_h">
					<div class="of_h consolMarginTop">
						<div class="float-left" style="width:13%"><h4>부가정보(EXT)</h4></div>
						<div class="float-left" style="width:57%">
							<input type="text" class="inputStyle" name="adtnInfo" v-model="rowData.adtnInfo">
						</div>
					</div>
				</div>
				<div v-if="rowData.msgType == 'IMAGE'">
					<div class="of_h consolMarginTop">
						<div class="float-left" style="width:13%"><h4>이미지</h4></div>
						<div class="float-left" style="width:57%">
							<div class="float-left" style="width:25%">
								<a @click="fnPushOpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
							</div>
							<ul class="float-right attachList" style="width:74%; padding:5px 15px; height:30px;">
								<li><a @click="fnPushDelImg">{{fnSubString(rowData.pushImgInfo.imgUrl, 0, 35)}}  <i v-if="!isEmpty(rowData.pushImgInfo.imgUrl)" class="fal fa-times"></i></a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="of_h consolMarginTop">
					<div class="float-left" style="width:13%"><h4>APP_ID</h4></div>
					<div class="float-left" style="width:57%">
						<div class="float-left" style="width:49%">
							<select class="selectStyle2 width100_" style="width:30%" v-model="rowData.pushAppId">
								<option v-for="aplnId in aplnIdList" :key="aplnId.aplnId" :value="aplnId.aplnId">{{aplnId.aplnId}}</option>
							</select>
						</div>
					</div>
				</div>
			</div>
		</div>	

		<!-- RCS -->
		<div class="of_h mt20" v-show="channelTab === 1">
			<div class="templateBox">
				<!-- templateList -->
				<ul class="templateList">
					<li>
						<img src="../../../common/images/pushTemplate1.svg" alt="프리 템플릿"><h6>프리 템플릿</h6>
						<div class="consolMarginTop">
							<input type="radio" name="rcsTemplate1" value="0" id="rcsTemplate1-1" class="radioStyle" checked="" v-on:click="rcsTemplateTable=0" v-model="rcsTemplateTableChecked">
							<label for="rcsTemplate1-1"></label><i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
						</div>
					</li>
					<li v-if="rowData.msgKind == 'I'"><!-- 정보성인경우만 사용가능 -->
						<img src="../../../common/images/pushTemplate2.svg" alt="템플릿 승인(서술)"><h6>템플릿 승인<br>(서술)</h6>
						<div class="consolMarginTop">
							<input type="radio" name="rcsTemplate1" value="1" id="rcsTemplate1-2" class="radioStyle" v-on:click="rcsTemplateTable=1" v-model="rcsTemplateTableChecked">
							<label for="rcsTemplate1-2"></label><i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
						</div>
					</li>
					<li v-if="rowData.msgKind == 'I'"><!-- 정보성인경우만 사용가능 -->
						<img src="../../../common/images/pushTemplate3.svg" alt="템플릿 승인(스타일)"><h6>템플릿 승인<br>(스타일)</h6>
						<div class="consolMarginTop">
							<input type="radio" name="rcsTemplate1" value="2" id="rcsTemplate1-3" class="radioStyle" v-on:click="rcsTemplateTable=2" v-model="rcsTemplateTableChecked">
							<label for="rcsTemplate1-3"></label><i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
						</div>
					</li>
					<li>
						<img src="../../../common/images/pushTemplate4.svg" alt="SMS"><h6>SMS</h6>
						<div class="consolMarginTop">
							<input type="radio" name="rcsTemplate1" value="3" id="rcsTemplate1-4" class="radioStyle" v-on:click="rcsTemplateTable=3" v-model="rcsTemplateTableChecked">
							<label for="rcsTemplate1-4"></label><i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
						</div>
					</li>
					<li>
						<img src="../../../common/images/pushTemplate5.svg" alt="LMS"><h6>LMS</h6>
						<div class="consolMarginTop">
							<input type="radio" name="rcsTemplate1" value="4" id="rcsTemplate1-5" class="radioStyle" v-on:click="rcsTemplateTable=4" v-model="rcsTemplateTableChecked">
							<label for="rcsTemplate1-5"></label><i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
						</div>
					</li>
					<li>
						<img src="../../../common/images/pushTemplate6.svg" alt="세로형(SHORT)"><h6>세로형<br>(SHORT)</h6>
						<div class="consolMarginTop">
							<input type="radio" name="rcsTemplate1" value="5" id="rcsTemplate1-6" class="radioStyle" v-on:click="rcsTemplateTable=5" v-model="rcsTemplateTableChecked">
							<label for="rcsTemplate1-6"></label><i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
						</div>
					</li>
					<li>
						<img src="../../../common/images/pushTemplate7.svg" alt="세로형(Tall)"><h6>세로형<br>(Tall)</h6>
						<div class="consolMarginTop">
							<input type="radio" name="rcsTemplate1" value="6" id="rcsTemplate1-7" class="radioStyle" v-on:click="rcsTemplateTable=6" v-model="rcsTemplateTableChecked">
							<label for="rcsTemplate1-7"></label><i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
						</div>
					</li>
					<li>
						<img src="../../../common/images/pushTemplate10.svg" alt="캐러셀(SHORT)"><h6>캐러셀<br>(SHORT)</h6>
						<div class="consolMarginTop">
							<input type="radio" name="rcsTemplate1" value="9" id="rcsTemplate1-10" class="radioStyle" v-on:click="rcsTemplateTable=9" v-model="rcsTemplateTableChecked">
							<label for="rcsTemplate1-10"></label><i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
						</div>
					</li>
					<li>
						<img src="../../../common/images/pushTemplate11.svg" alt="캐러셀(Tall)"><h6>캐러셀<br>(Tall)</h6>
						<div class="consolMarginTop">
							<input type="radio" name="rcsTemplate1" value="10" id="rcsTemplate1-11" class="radioStyle" v-on:click="rcsTemplateTable=10" v-model="rcsTemplateTableChecked">
							<label for="rcsTemplate1-11"></label><i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
						</div>
					</li>
				</ul>
				<!-- //templateList -->
			</div>

			<!-- 프리 템플릿 -->
			<div v-if="rcsTemplateTable === 0">
				<h4>내용작성</h4>
				<div class="of_h mt20">
					<div class="float-left" style="width:28%">
						<!-- phoneWrap -->
						<div class="phoneWrap">
							<img src="../../../common/images/phoneMockup1.svg" alt="프리 템플릿">
							<div class="phoneTextWrap">
								<div class="phoneText1">
									<pre>{{rowData.rcs0Content}}</pre>
								</div>
							</div>
						</div>
						<!-- //phoneWrap -->
					</div>
					<div class="float-left consoleCon" style="width:72%">
						<div class="of_h">
							<div class="float-left" style="width:13%"><h4>브랜드명*</h4></div>
							<select class="selectStyle2" v-model="rowData.brandNm" style="width:24%" title="브랜드명 선택란">
								<option value="">선택해주세요.</option>
								<option v-for="option in brandNmList" v-bind:value="option.BRAND_ID">{{option.BRAND_NAME}}</option>
							</select>
						</div>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:13%"><h4>내용*</h4></div>
							<div class="float-left" style="width:57%">
								<textarea class="textareaStyle height190" v-model="rowData.rcs0Content" :placeholder="rcsPlaceHoder"></textarea>
							</div>
						</div>
						<div class="of_h consolMarginTop" v-if="rowData.msgKind == 'A'">
							<div class="float-left" style="width:13%"><h4>무료수신거부*</h4></div>
							<div class="float-left" style="width:57%">
								<input type="text" class="inputStyle" v-model="rowData.rcsBlockNumber" placeholder="수신거부번호 설정" maxlength="15">
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:13%"><h5>발신번호 *</h5></div>
							<div class="float-left" style="width:57%">
								<select v-model="rowData.callback" class="selectStyle2 float-right" style="width:100%">
									<option value="">선택해주세요.</option>
									<option v-for="info in rcs0CallbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
								</select>
							</div>
						</div>
					</div>
				</div>		
			</div>

			<!-- 템플릿 승인(서술) 메시지구분이 광고일경우 사용불가 -->
			<div v-if="rcsTemplateTable === 1 ">
				<h4>내용작성</h4>
				<div class="of_h mt20">
					<div class="float-left" style="width:28%">
						<!-- phoneWrap -->
						<div class="phoneWrap">
							<img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
							<div class="phoneTextWrap">
								<div class="phoneText1 relative scroll-y4">
									<p><img :src="rcsImgsrc" style="width:70px;"></p>
									<div class="scroll-y5">
										<pre class="mt15 lc-1">{{rowData.rcs1Content}}</pre>
									</div>
									<div v-for="(buttonInfo, idx) in rowData.rcsDesButtons" :key="idx">
										<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)" class="btnStyle1 backLightGray">{{buttonInfo.action.displayText}}</a>
									</div>
								</div>
							</div>
						</div>
						<!-- //phoneWrap -->
					</div>
					<div class="float-left consoleCon" style="width:72%">
						<div class="text-right" style="width:70%">
							<a href="#" class="btnStyle1 backBlack" style="min-width:auto" @click.prevent="fnOpenRcsTemplatePopup" data-toggle="modal" data-target="#templatePop" title="RCS 템플릿 선택">RCS 템플릿 선택</a>
						</div>
						<div v-if="selectedRcsDesTemplate">
							<div class="of_h mt20">
								<div class="float-left" style="width:13%"><h4>브랜드명</h4></div>
								<select class="selectStyle2" v-model="rowData.brandNm" style="width:24%" title="브랜드명 선택란" disabled>
									<option v-for="option in brandNmList" v-bind:value="option.BRAND_ID">{{option.BRAND_NAME}}</option>
								</select>
							</div>
							<div class="of_h">						
								<div class="float-left" style="width:13%"><h4>유형</h4></div>
								<select class="selectStyle2" v-model="rowData.rcsDesMessagebaseformId" title="유형 선택란" disabled>
									<option v-for="rcsType in rcsDesFormNmList" v-bind:value="rcsType.MESSAGEBASEFORM_ID">{{rcsType.FORM_NAME}}</option>
								</select>
							</div>
							<div class="of_h">
								<div class="float-left" style="width:13%"><h4>내용*</h4></div>
								<div class="float-left" style="width:57%">
									<textarea class="textareaStyle height190"  v-model="rowData.rcs1Content" id="rcs1ContentId" disabled></textarea>
									<strong class="letter" id="rcs1TextLength">(00 / 90)</strong>
								</div>
							</div>
							<div class="of_h consolMarginTop">
								<div class="float-left" style="width:13%"><h4>버튼</h4></div>
								<div class="float-left" style="width:57%">
									<table class="table_skin1 mt0" style="width:100%">
										<colgroup>
											<col style="width:22%">
											<col style="width:20%">
											<col>
										</colgroup>
										<thead>
											<tr>
												<th class="text-center">타입</th>
												<th class="text-center">버튼이름</th>
												<th class="text-center end">내용</th>
											</tr>
										</thead>
										<tbody>
											<tr v-for="(row,index) in rowData.rcsDesButtons" v-bind:key="index">
												<td class="text-center">
													<select class="selectStyle2" style="width:100%" v-model="row.action.linkType" disabled>
														<option v-for="rcsButtonType in rcsButtonTypeList" :key="rcsButtonType.type" :value="rcsButtonType.type">{{rcsButtonType.name}}</option>
													</select>
												</td>
												<td class="text-left"><input v-model="row.action.displayText" type="text" class="inputStyle" disabled></td>
												<td v-if="row.action.linkType=='urlAction'" class="text-center"><input v-model="row.action.urlAction.openUrl.url" type="text" class="inputStyle" disabled></td>
												<td v-if="row.action.linkType=='clipboardAction'" class="text-center"><input v-model="row.action.clipboardAction.copyToClipboard.text" type="text" class="inputStyle" disabled></td>
												<td v-if="row.action.linkType=='dialerAction'" class="text-center"><input v-model="row.action.dialerAction.dialPhoneNumber.phoneNumber" type="text" class="inputStyle" disabled></td>
												<td v-if="row.action.linkType=='calendarAction'" class="text-center">
													<input v-model="row.action.calendarAction.createCalendarEvent.title" type="text" class="inputStyle" placeholder="제목입력" disabled>
													<input v-model="row.action.calendarAction.createCalendarEvent.description" type="text" class="inputStyle consolMarginTop" placeholder="내용입력" disabled>
													<div class="consolMarginTop of_h">
														<span class="float-left mt5" style="width:20%">시작일</span>
														<div class="float-right" style="width:80%">
															<Calendar classProps="datepicker inputStyle" :initDate="row.action.calendarAction.createCalendarEvent.startTime"></Calendar>
														</div>
													</div>
													<div class="consolMarginTop of_h">
														<span class="float-left mt5" style="width:20%">종료일</span>
														<div class="float-right" style="width:80%">
															<Calendar classProps="datepicker inputStyle" :initDate="row.action.calendarAction.createCalendarEvent.endTime"></Calendar>
														</div>
													</div>
												</td>
												<td v-if="row.action.linkType=='mapAction'" class="text-center"><input v-model="row.action.mapAction.requestLocationPush" type="text" class="inputStyle" disabled></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<div class="of_h consolMarginTop">
								<div class="float-left" style="width:13%"><h4>발신번호 *</h4></div>
								<div class="float-left" style="width:57%">
									<select v-model="rowData.callback" class="selectStyle2 float-right" style="width:100%">
										<option value="">선택해주세요.</option>
										<option v-for="info in rcs1CallbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
									</select>
								</div>
							</div>
						</div>
						<div v-else>
							<span class="txtCaption vertical-middle colorRed">템플릿 승인형 RCS상품은 기 등록 된, 템플릿 정보를 선택하여서 사용 할 수 있습니다.</span>
						</div>
					</div>
				</div>
			</div>

			<!-- 템플릿 승인(스타일) 메시지구분이 광고일경우 사용불가 -->
			<div v-if="rcsTemplateTable === 2 ">
				<h4>내용작성</h4>
				<div class="of_h mt20">
					<div class="float-left" style="width:28%">
						<!-- phoneWrap -->
						<div class="phoneWrap">
							<img src="../../../common/images/phoneMockup1.svg" alt="프리 템플릿">
							<div class="phoneTextWrap">
								<div class="phoneText1 of_h">
									<p><img src="common/images/phone_Icon08.png" alt="인증 아이콘"></p>
									<div class="scroll-y">
										<p class="mt15 lc-1">인증번호 안내</p>
									</div>
									<p class="mt10 lc-1 inline-block">인증번호</p>
									<p class="mt10 lc-1 inline-block float-right">{{}}</p>
									<p class="text-center mt30" style="color:#69C8FF">홈페이지 연결하기</p>
									<p class="text-center mt10" style="color:#69C8FF">인증번호 복사하기</p>
								</div>
							</div>
						</div>
						<!-- //phoneWrap -->
					</div>
					<div class="float-left consoleCon" style="width:72%">
						<div class="text-right" style="width:70%">
							<a href="#" class="btnStyle1 backBlack" style="min-width:auto" @click.prevent="fnOpenRcsTemplatePopup" data-toggle="modal" data-target="#templatePop" title="RCS 템플릿 선택">RCS 템플릿 선택</a>
						</div>
						<div class="of_h mt20">						
							<div class="float-left" style="width:13%"><h4>유형</h4></div>
							<div class="float-left" style="width:57%">
								<input type="text" class="inputStyle" placeholder="인증" v-model="rowData.rcs2Title" readOnly>
							</div>
						</div>
						<div class="of_h">
							<div class="float-left" style="width:13%"><h4>내용</h4></div>
							<div class="float-left" style="width:57%">
								<input type="text" class="inputStyle" placeholder="인증번호 안내" v-model="rowData.rcs2Content1" readOnly>
								<input type="hidden" class="inputStyle" placeholder="" v-model="rowData.rcs2MessageFormId" id="rcs2MessageFormId" >
							</div>
							<div class="float-left of_h consolMarginTop" style="width:57%">
								<input type="text" class="inputStyle" style="width:49%" placeholder="인증번호" v-model="rowData.rcs2Content2" readOnly>
								<input type="text" class="inputStyle float-right" style="width:49%" v-model="rowData.rcs2Content3" readOnly>
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:13%"><h4>버튼</h4></div>
              <div class="float-left" style="width:57%">
                <table class="table_skin1 mt0" style="width:100%">
                  <colgroup>
                    <col style="width:22%">
                    <col style="width:20%">
                    <col>
                  </colgroup>
                  <thead>
                    <tr>
                    <th class="text-center">타입</th>
                    <th class="text-center">버튼이름</th>
                    <th class="text-center end">내용</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(row,index) in rowData.rcs2Buttons" v-bind:key="index">
                      <td class="text-center">{{row.buttonTypeName}}</td>
                      <td class="text-left">{{row.buttonName}}</td>
                      <td class="text-center">{{row.buttonLink}}
                        <input type="hidden" class="inputStyle" v-model="row.buttonType">
                        <input type="hidden" class="inputStyle" v-model="row.buttonName">
                        <input type="hidden" class="inputStyle" v-model="row.buttonLink">
                        <input type="hidden" class="inputStyle" v-model="row.buttonLink1">
                        <input type="hidden" class="inputStyle" v-model="row.startDate">
                        <input type="hidden" class="inputStyle" v-model="row.endDate">
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>

            <div class="of_h consolMarginTop">
	            <div class="float-left" style="width:13%"><h5>발신번호 *</h5></div>
	            <div class="float-left" style="width:57%">
	              <select v-model="rowData.callback" class="selectStyle2 float-right" style="width:100%">
	                <option v-for="info in rcs2CallbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
	              </select>
	            </div>
			      </div>

          </div>
        </div>
      </div>

<!-- SMS -->
      <div v-if="rcsTemplateTable === 3 ">

        <h4>내용작성</h4>
        <div class="of_h mt20">
          <div class="float-left" style="width:28%">
            <!-- phoneWrap -->
            <div class="phoneWrap">
              <img src="../../../common/images/phoneMockup1.svg" alt="프리 템플릿">
              <div class="phoneTextWrap">
                <div class="phoneText1 scroll-y2">
                  <pre>{{rowData.rcsSMSContent}}</pre>
                </div>
              </div>
            </div>
            <!-- //phoneWrap -->
          </div>
          <div class="float-left consoleCon" style="width:72%">

            <div class="of_h">
              <div class="float-left" style="width:13%"><h4>내용*</h4></div>
              <div class="float-left" style="width:57%">
                <textarea class="textareaStyle height190" maxlength="100" v-model="rowData.rcsSMSContent" :placeholder="rcsPlaceHoder" id="rcsSMSContentId" @keyup="fnTextLength('내용', '#rcsSMSContentId', '#rcsSMSTextLength', '1300')"></textarea>
                <strong class="letter" id="rcsSMSTextLength">(00 / 1300)</strong>
              </div>
            </div>

            <div v-if="rowData.msgKind == 'A'">
              <div class="of_h consolMarginTop">
                <div class="float-left" style="width:13%"><h4>무료수신거부 *</h4></div>
                <div class="float-left" style="width:57%">
                  <input type="text" class="inputStyle" name="rcsSMSHowToDenyReceipt" v-model="rowData.rcsSMSHowToDenyReceipt" placeholder="설정 > 푸시 알림 설정 변경">
                  <!--<p class="color5 txtCaption">광고성 메시지 발송시, 자동으로 (광고)가 표시되오니, 내용에 (광고)문구는 입력하지 않아도 됩니다.</p>-->
                </div>
              </div>
            </div>

            <div class="of_h consolMarginTop">
                   <div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRowSMS" class="btnStyle1 backBlack">추가 +</a></div>
                  <div class="float-left" style="width:57%">
                    <table class="table_skin1 mt0" style="width:100%" v-if="buttonSMSFlag">
                      <colgroup>
                        <col style="width:22%">
                        <col style="width:20%">
                        <col>
                        <col style="width:15%">
                      </colgroup>
                      <thead>
                        <tr>
                        <th class="text-center">타입</th>
                        <th class="text-center">버튼이름</th>
                        <th class="text-center">버튼링크</th>
                        <th class="text-center end">구분</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(row,index) in rowData.rcsSMSButtons" v-bind:key="index">
                          <td class="text-center">
                            <select name="userConsole04060202_1" class="selectStyle2" style="width:100%" v-model="row.buttonType">
                              <option value="U">URL 링크</option>
                              <option value="C">복사하기</option>
                              <option value="T">전화걸기</option>
                              <option value="S">일정추가</option>
                              <option value="M">지도맵</option>
                            </select>
                          </td>
                          <td class="text-left"><input type="text" class="inputStyle" v-model="row.buttonName"></td>
                          <td class="text-center">
                            <input v-if="row.buttonType == 'U'" type="text" class="inputStyle" placeholder="URL입력(http:// 또는 https:// 필수입력)" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'C'" type="text" class="inputStyle" placeholder="복사할 값 입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'T'" type="text" class="inputStyle" placeholder="전화번호입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="제목입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="내용입력" v-model="row.buttonLink1">
                            <p v-if="row.buttonType == 'S'">시작일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcsSMSButtonSD" :calendarId="row.startDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.startDate" :params="{idx:index}"></Calendar>
		                        <p v-if="row.buttonType == 'S'">종료일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcsSMSButtonED" :calendarId="row.endDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.endDate"  :params="{idx:index}"></Calendar>
                            <input v-if="row.buttonType == 'M'" type="text" class="inputStyle" placeholder="현재위치공유" readOnly>
                          </td>
                          <td class="text-center end"><a @click="addRowSMS" title="추가버튼"><i class="far fa-plus channelBtn"></i></a> <a @click="removeRowSMS(index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a></td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
            </div>

            <div class="of_h consolMarginTop">
	            <div class="float-left" style="width:13%"><h5>발신번호 *</h5></div>
	            <div class="float-left" style="width:57%">
	              <select v-model="rowData.callback" class="selectStyle2 float-right" style="width:100%">
	                <option v-for="info in rcsSMSCallbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
	              </select>
	            </div>
			      </div>

          </div>
        </div>

      </div>

<!-- LMS -->
      <div v-if="rcsTemplateTable === 4">

        <h4>내용작성</h4>
        <div class="of_h mt20">
          <div class="float-left" style="width:28%">
            <!-- phoneWrap -->
            <div class="phoneWrap">
              <img src="../../../common/images/phoneMockup1.svg" alt="프리 템플릿">
              <div class="phoneTextWrap">
                <div class="phoneText1 scroll-y4">
                  <pre>{{rowData.rcsLMSContent}}</pre>
                </div>
              </div>
            </div>
            <!-- //phoneWrap -->
          </div>
          <div class="float-left consoleCon" style="width:72%">
            <div class="of_h">
              <div class="float-left" style="width:13%"><h4>제목</h4></div>
              <div class="float-left" style="width:57%">
                <input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." maxlength="30" v-model="rowData.rcsLMSTitle" id="rcsLMSTitleId" @keyup="fnTextLength('제목', '#rcsLMSTitleId', '', '30')">
              </div>
            </div>

            <div class="of_h">
              <div class="float-left" style="width:13%"><h4>내용*</h4></div>
              <div class="float-left" style="width:57%">
                <textarea class="textareaStyle height190" maxlength="1300" v-model="rowData.rcsLMSContent" :placeholder="rcsPlaceHoder" id="rcsLMSContentId" @keyup="fnTextLength('내용', '#rcsLMSContentId', '#rcsLMSTextLength', '1300')"></textarea>
                <strong class="letter" id="rcsLMSTextLength">(00 / 1300)</strong>
              </div>
            </div>

            <div v-if="rowData.msgKind == 'A'">
              <div class="of_h consolMarginTop">
                <div class="float-left" style="width:13%"><h4>무료수신거부 *</h4></div>
                <div class="float-left" style="width:57%">
                  <input type="text" class="inputStyle" name="rcsLMSHowToDenyReceipt" v-model="rowData.rcsLMSHowToDenyReceipt" placeholder="설정 > 푸시 알림 설정 변경">
                  <!--<p class="color5 txtCaption">광고성 메시지 발송시, 자동으로 (광고)가 표시되오니, 내용에 (광고)문구는 입력하지 않아도 됩니다.</p>-->
                </div>
              </div>
            </div>

            <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRowLMS" class="btnStyle1 backBlack">추가 +</a></div>
                  <div class="float-left" style="width:57%">
                    <table class="table_skin1 mt0" style="width:100%" v-if="buttonLMSFlag">
                      <colgroup>
                        <col style="width:22%">
                        <col style="width:20%">
                        <col>
                        <col style="width:15%">
                      </colgroup>
                      <thead>
                        <tr>
                        <th class="text-center">타입</th>
                        <th class="text-center">버튼이름</th>
                        <th class="text-center">버튼링크</th>
                        <th class="text-center end">구분</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(row,index) in rowData.rcsLMSButtons" v-bind:key="index">
                          <td class="text-center">
                            <select name="userConsole04060202_1" class="selectStyle2" style="width:100%" v-model="row.buttonType">
                              <option value="U">URL 링크</option>
                              <option value="C">복사하기</option>
                              <option value="T">전화걸기</option>
                              <option value="S">일정추가</option>
                              <option value="M">지도맵</option>
                            </select>
                          </td>
                          <td class="text-left"><input type="text" class="inputStyle" v-model="row.buttonName"></td>
                          <td class="text-center">
                            <input v-if="row.buttonType == 'U'" type="text" class="inputStyle" placeholder="URL입력(http:// 또는 https:// 필수입력)" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'C'" type="text" class="inputStyle" placeholder="복사할 값 입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'T'" type="text" class="inputStyle" placeholder="전화번호입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="제목입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="내용입력" v-model="row.buttonLink1">
                            <p v-if="row.buttonType == 'S'">시작일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcsLMSButtonSD" :calendarId="row.startDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.startDate" :params="{idx:index}"></Calendar>
		                        <p v-if="row.buttonType == 'S'">종료일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcsLMSButtonED" :calendarId="row.endDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.endDate"  :params="{idx:index}"></Calendar>
                            <input v-if="row.buttonType == 'M'" type="text" class="inputStyle" placeholder="현재위치공유" readOnly>
                          </td>
                          <td class="text-center end"><a @click="addRowLMS" title="추가버튼"><i class="far fa-plus channelBtn"></i></a> <a @click="removeRowLMS(index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a></td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
            </div>

            <div class="of_h consolMarginTop">
	            <div class="float-left" style="width:13%"><h5>발신번호 *</h5></div>
	            <div class="float-left" style="width:57%">
	              <select v-model="rowData.callback" class="selectStyle2 float-right" style="width:100%">
	                <option v-for="info in rcsLMSCallbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
	              </select>
	            </div>
			      </div>

          </div>
        </div>

      </div>

<!-- 세로형 SHORT -->
      <div v-if="rcsTemplateTable === 5 ">

        <h4>내용작성</h4>
        <div class="of_h mt20">
          <div class="float-left" style="width:28%">
            <!-- phoneWrap -->
            <div class="phoneWrap">
              <img src="../../../common/images/phoneMockup1.svg" alt="프리 템플릿">
              <div class="phoneTextWrap">
		            <div v-for="imgIdx in rcsShortImgLimitSize" :key="imgIdx">  
		            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcsShortImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
			                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcsShortImgInfoList[imgIdx-1].imgUrl+');'">  
		            		</div>
		            		<div v-else>
		            			<img src="@/assets/images/common/cardThumImg2_2.png" alt="카드 썸네일">
		            		</div>
		            </div>              
              
              
<!--
					<div v-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcsShortImgInfoList[0])" class="phoneText2 mt10 text-center">
		                <img src="@/assets/images/common/cardThumImg2_2.png" alt="카드 썸네일">
		            </div>
		            <div v-else-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcsShortImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
		                <img src="@/assets/images/common/cardThumImg2_2.png" alt="카드 썸네일">
		            </div>
		            <div v-else-if="rowData.msgType == 'IMAGE' && !isEmpty(rowData.rcsShortImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
		                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcsShortImgInfoList[0].imgUrl+');'">
		            </div>
-->
					
					<div style="background:#fff; border-radius: 0 0 5px 5px; min-height:180px" class="pd20">
						<h5>{{rowData.rcsShortTitle}}</h5>
						<div class="scroll-y3">
							<pre class="color6">{{rowData.rcsShortContent}}</pre>
						</div>								
					</div>              
              </div>
            </div>
            <!-- //phoneWrap -->
          </div>
          <div class="float-left consoleCon" style="width:72%">
            <div class="of_h">
              <div class="float-left" style="width:13%"><h4>제목</h4></div>
              <div class="float-left" style="width:57%">
                <input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcsShortTitle" id="rcsShortTitleId" @keyup="fnTextLength('제목', '#rcsShortTitleId', '', '30')">
              </div>
            </div>

            <div class="of_h">
              <div class="float-left" style="width:13%"><h4>내용*</h4></div>
              <div class="float-left" style="width:57%">
                <textarea class="textareaStyle height190" v-model="rowData.rcsShortContent" :placeholder="rcsPlaceHoder" id="rcsShortContentId" @keyup="fnTextLength('내용', '#rcsShortContentId', '#rcsShortTextLength', '1300')"></textarea>
                <strong class="letter" id="rcsShortTextLength">(00 / 1300)</strong>
              </div>
            </div>

            <div class="of_h consolMarginTop" v-if="rowData.msgType == 'IMAGE'">
              <div class="float-left" style="width:13%"><h4>이미지</h4></div>
              <div class="float-left" style="width:57%">
                <div class="float-left" style="width:25%">
                  <a @click="fnRcsShortOpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
                </div>
 
                <ul v-for="imgIdx in rcsShortImgLimitSize" :key="imgIdx" class="float-right attachList" style="width:75%; padding:5px 15px; height:30px;">
                  <li v-if="rowData.rcsShortImgInfoList.length > imgIdx -1">
                    <a @click="fnRcsShortDelImg(imgIdx-1)">{{fnSubString(rowData.rcsShortImgInfoList[imgIdx-1].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a>
                  </li>
                  <li v-else>
                    <a></a>
                  </li>
                </ul>
              </div>
            </div>

            <div v-if="rowData.msgKind == 'A'">
              <div class="of_h consolMarginTop">
                <div class="float-left" style="width:13%"><h4>무료수신거부 *</h4></div>
                <div class="float-left" style="width:57%">
                  <input type="text" class="inputStyle" name="rcsShortHowToDenyReceipt" v-model="rowData.rcsShortHowToDenyReceipt" placeholder="설정 > 푸시 알림 설정 변경">
                  <!--<p class="color5 txtCaption">광고성 메시지 발송시, 자동으로 (광고)가 표시되오니, 내용에 (광고)문구는 입력하지 않아도 됩니다.</p>-->
                </div>
              </div>
            </div>

            <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRowShort" class="btnStyle1 backBlack">추가 +</a></div>
                  <div class="float-left" style="width:57%">
                    <table class="table_skin1 mt0" style="width:100%" v-if="buttonShortFlag">
                      <colgroup>
                        <col style="width:22%">
                        <col style="width:20%">
                        <col>
                        <col style="width:15%">
                      </colgroup>
                      <thead>
                        <tr>
                        <th class="text-center">타입</th>
                        <th class="text-center">버튼이름</th>
                        <th class="text-center">버튼링크</th>
                        <th class="text-center end">구분</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(row,index) in rowData.rcsShortButtons" v-bind:key="index">
                          <td class="text-center">
                            <select name="userConsole04060202_1" class="selectStyle2" style="width:100%" v-model="row.buttonType">
                              <option value="U">URL 링크</option>
                              <option value="C">복사하기</option>
                              <option value="T">전화걸기</option>
                              <option value="S">일정추가</option>
                              <option value="M">지도맵</option>
                            </select>
                          </td>
                          <td class="text-left"><input type="text" class="inputStyle" v-model="row.buttonName"></td>
                          <td class="text-center">
                            <input v-if="row.buttonType == 'U'" type="text" class="inputStyle" placeholder="URL입력(http:// 또는 https:// 필수입력)" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'C'" type="text" class="inputStyle" placeholder="복사할 값 입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'T'" type="text" class="inputStyle" placeholder="전화번호입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="제목입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="내용입력" v-model="row.buttonLink1">
                            <p v-if="row.buttonType == 'S'">시작일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcsShortButtonSD" :calendarId="row.startDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.startDate" :params="{idx:index}"></Calendar>
		                    <p v-if="row.buttonType == 'S'">종료일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcsShortButtonED" :calendarId="row.endDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.endDate"  :params="{idx:index}"></Calendar>
                            <input v-if="row.buttonType == 'M'" type="text" class="inputStyle" placeholder="현재위치공유" readOnly>
                          </td>
                          <td class="text-center end"><a @click="addRowShort" title="추가버튼"><i class="far fa-plus channelBtn"></i></a> <a @click="removeRowShort(index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a></td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
            </div>
            
            <div class="of_h consolMarginTop">
	            <div class="float-left" style="width:13%"><h5>발신번호 *</h5></div>
	            <div class="float-left" style="width:57%">
	              <select v-model="rowData.callback" class="selectStyle2 float-right" style="width:100%">
	                <option v-for="info in rcsShortCallbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
	              </select>
	            </div>
			</div>
          </div>
        </div>	

      </div>


<!-- 세로형 TALL-->
      <div v-if="rcsTemplateTable === 6 ">

        <h4>내용작성</h4>
        <div class="of_h mt20">
          <div class="float-left" style="width:28%">
            <!-- phoneWrap -->
            <div class="phoneWrap">
              <img src="../../../common/images/phoneMockup1.svg" alt="프리 템플릿">
              <div class="phoneTextWrap">
		            <div v-for="imgIdx in rcsTallImgLimitSize" :key="imgIdx">  
		            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcsTallImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
			                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcsTallImgInfoList[imgIdx-1].imgUrl+');'">  
		            		</div>
		            		<div v-else>
		            			<img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
		            		</div>
		            </div>
<!--
					<div v-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcsTallImgInfoList[0])" class="phoneText2 mt10 text-center">
		                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
		            </div>
		            <div v-else-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcsTallImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
		                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
		            </div>
		            <div v-else-if="rowData.msgType == 'IMAGE' && !isEmpty(rowData.rcsTallImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
		                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcsTallImgInfoList[0].imgUrl+');'">
		            </div>
-->		            
					<div style="background:#fff; border-radius: 0 0 5px 5px; min-height:170px" class="pd20">
						<h5>{{rowData.rcsTallTitle}}</h5>
						<div class="scroll-y6">
							<pre class="color6">{{rowData.rcsTallContent}}</pre>
						</div>								
					</div>              
              </div>
            </div>
            <!-- //phoneWrap -->
          </div>
          <div class="float-left consoleCon" style="width:72%">
            <div class="of_h">
              <div class="float-left" style="width:13%"><h4>제목</h4></div>
              <div class="float-left" style="width:57%">
                <input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcsTallTitle" id="rcsTallTitleId" @keyup="fnTextLength('제목', '#rcsTallTitleId', '', '30')">
              </div>
            </div>

            <div class="of_h">
              <div class="float-left" style="width:13%"><h4>내용*</h4></div>
              <div class="float-left" style="width:57%">
                <textarea class="textareaStyle height190" v-model="rowData.rcsTallContent" :placeholder="rcsPlaceHoder" id="rcsTallContentId" @keyup="fnTextLength('내용', '#rcsTallContentId', '#rcsTallTextLength', '1300')"></textarea>
                <strong class="letter" id="rcsTallTextLength">(00 / 1300)</strong>
              </div>
            </div>

            <div class="of_h consolMarginTop" v-if="rowData.msgType == 'IMAGE'">
              <div class="float-left" style="width:13%"><h4>이미지</h4></div>
              <div class="float-left" style="width:57%">
                <div class="float-left" style="width:25%">
                  <a @click="fnRcsTallOpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
                </div>
 
                <ul v-for="imgIdx in rcsTallImgLimitSize" :key="imgIdx" class="float-right attachList" style="width:75%; padding:5px 15px; height:30px;">
                  <li v-if="rowData.rcsTallImgInfoList.length > imgIdx -1">
                    <a @click="fnRcsTallDelImg(imgIdx-1)">{{fnSubString(rowData.rcsTallImgInfoList[imgIdx-1].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a>
                  </li>
                  <li v-else>
                    <a></a>
                  </li>
                </ul>

              </div>
            </div>

            <div v-if="rowData.msgKind == 'A'">
              <div class="of_h consolMarginTop">
                <div class="float-left" style="width:13%"><h4>무료수신거부 *</h4></div>
                <div class="float-left" style="width:57%">
                  <input type="text" class="inputStyle" name="rcsTallHowToDenyReceipt" v-model="rowData.rcsTallHowToDenyReceipt" placeholder="설정 > 푸시 알림 설정 변경">
                  <!--<p class="color5 txtCaption">광고성 메시지 발송시, 자동으로 (광고)가 표시되오니, 내용에 (광고)문구는 입력하지 않아도 됩니다.</p>-->
                </div>
              </div>
            </div>

            <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRowTall" class="btnStyle1 backBlack">추가 +</a></div>
                  <div class="float-left" style="width:57%">
                    <table class="table_skin1 mt0" style="width:100%" v-if="buttonTallFlag">
                      <colgroup>
                        <col style="width:22%">
                        <col style="width:20%">
                        <col>
                        <col style="width:15%">
                      </colgroup>
                      <thead>
                        <tr>
                        <th class="text-center">타입</th>
                        <th class="text-center">버튼이름</th>
                        <th class="text-center">버튼링크</th>
                        <th class="text-center end">구분</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(row,index) in rowData.rcsTallButtons" v-bind:key="index">
                          <td class="text-center">
                            <select name="userConsole04060202_1" class="selectStyle2" style="width:100%" v-model="row.buttonType">
                              <option value="U">URL 링크</option>
                              <option value="C">복사하기</option>
                              <option value="T">전화걸기</option>
                              <option value="S">일정추가</option>
                              <option value="M">지도맵</option>
                            </select>
                          </td>
                          <td class="text-left"><input type="text" class="inputStyle" v-model="row.buttonName"></td>
                          <td class="text-center">
                            <input v-if="row.buttonType == 'U'" type="text" class="inputStyle" placeholder="URL입력(http:// 또는 https:// 필수입력)" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'C'" type="text" class="inputStyle" placeholder="복사할 값 입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'T'" type="text" class="inputStyle" placeholder="전화번호입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="제목입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="내용입력" v-model="row.buttonLink1">
                            <p v-if="row.buttonType == 'S'">시작일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcsTallButtonSD" :calendarId="row.startDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.startDate" :params="{idx:index}"></Calendar>
		                        <p v-if="row.buttonType == 'S'">종료일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcsTallButtonED" :calendarId="row.endDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.endDate"  :params="{idx:index}"></Calendar>
                            <input v-if="row.buttonType == 'M'" type="text" class="inputStyle" placeholder="현재위치공유" readOnly>
                          </td>
                          <td class="text-center end"><a @click="addRowTall" title="추가버튼"><i class="far fa-plus channelBtn"></i></a> <a @click="removeRowTall(index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a></td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
            </div>
            <div class="of_h consolMarginTop">
	            <div class="float-left" style="width:13%"><h5>발신번호 *</h5></div>
	            <div class="float-left" style="width:57%">
	              <select v-model="rowData.callback" class="selectStyle2 float-right" style="width:100%">
	                <option v-for="info in rcsTallCallbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
	              </select>
	            </div>
			</div>            
          </div>
        </div>	

      </div>

<!-- 캐러셀 SHORT -->
      <div v-if="rcsTemplateTable === 9" >

        <h4>내용작성</h4>
        <div class="of_h mt20">
          <div class="float-left" style="height:660px">
            <!-- phoneWrap -->
            <div class="phoneWrap">
              	<img src="../../../common/images/phoneMockup1.svg" alt="프리 템플릿">
				<div class="phoneCardWrap">
					<p class="color000">[WEB발신] (광고)</p>
					<ul class="cardBxslider mt10">
						<li class="slide cardBox" v-show="cShortTab === 0">
				            <div v-for="imgIdx in rcs90ImgLimitSize" :key="imgIdx">  
				            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs90ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
					                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs90ImgInfoList[imgIdx-1].imgUrl+');'">  
				            		</div>
				            		<div v-else>
				            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            		</div>
				            </div>
<!--
							<div v-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs90ImgInfoList[0])" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs90ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && !isEmpty(rowData.rcs90ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
				                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs90ImgInfoList[0].imgUrl+');'">
				            </div>
-->
							<div class="relative">
								<div class="scroll-y" style="min-height:150px">
									<p class="color000 font-size13">{{rowData.rcs90Title}}</p>
									<pre class="color3 mt5">{{rowData.rcs90Content}}</pre>
								</div>
								<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs9HowToDenyReceipt}}</p>
							</div>
						</li>
						<li class="slide cardBox" v-show="cShortTab === 1">
				            <div v-for="imgIdx in rcs91ImgLimitSize" :key="imgIdx">  
				            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs91ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
					                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs91ImgInfoList[imgIdx-1].imgUrl+');'">  
				            		</div>
				            		<div v-else>
				            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            		</div>
				            </div>
<!--						
							<div v-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs91ImgInfoList[0])" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs91ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && !isEmpty(rowData.rcs91ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
				                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs91ImgInfoList[0].imgUrl+');'">
				            </div>
-->				            
							<div class="relative">
								<div class="scroll-y" style="min-height:150px">
									<p class="color000 font-size13">{{rowData.rcs91Title}}</p>
									<pre class="color3 mt5">{{rowData.rcs91Content}}</pre>
								</div>
								<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs9HowToDenyReceipt}}</p>
							</div>
						</li>
						<li class="slide cardBox" v-show="cShortTab === 2">
				            <div v-for="imgIdx in rcs92ImgLimitSize" :key="imgIdx">  
				            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs92ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
					                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs92ImgInfoList[imgIdx-1].imgUrl+');'">  
				            		</div>
				            		<div v-else>
				            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            		</div>
				            </div>
<!--						
							<div v-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs92ImgInfoList[0])" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs92ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && !isEmpty(rowData.rcs92ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
				                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs92ImgInfoList[0].imgUrl+');'">
				            </div>
-->				            
							<div class="relative">
								<div class="scroll-y" style="min-height:150px">
									<p class="color000 font-size13">{{rowData.rcs92Title}}</p>
									<pre class="color3 mt5">{{rowData.rcs92Content}}</pre>
								</div>
								<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs9HowToDenyReceipt}}</p>
							</div>
						</li>
						<li class="slide cardBox" v-show="cShortTab === 3">
				            <div v-for="imgIdx in rcs93ImgLimitSize" :key="imgIdx">  
				            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs93ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
					                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs93ImgInfoList[imgIdx-1].imgUrl+');'">  
				            		</div>
				            		<div v-else>
				            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            		</div>
				            </div>
<!--						
							<div v-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs93ImgInfoList[0])" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs93ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && !isEmpty(rowData.rcs93ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
				                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs93ImgInfoList[0].imgUrl+');'">
				            </div>
-->				            
							<div class="relative">
								<div class="scroll-y" style="min-height:150px">
									<p class="color000 font-size13">{{rowData.rcs93Title}}</p>
									<pre class="color3 mt5">{{rowData.rcs93Content}}</pre>
								</div>
								<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs9HowToDenyReceipt}}</p>
							</div>
						</li>
						<li class="slide cardBox" v-show="cShortTab === 4">
				            <div v-for="imgIdx in rcs94ImgLimitSize" :key="imgIdx">  
				            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs94ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
					                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs94ImgInfoList[imgIdx-1].imgUrl+');'">  
				            		</div>
				            		<div v-else>
				            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            		</div>
				            </div>
<!--											
							<div v-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs94ImgInfoList[0])" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs94ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && !isEmpty(rowData.rcs94ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
				                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs94ImgInfoList[0].imgUrl+');'">
				            </div>
-->				            
							<div class="relative">
								<div class="scroll-y" style="min-height:150px">
									<p class="color000 font-size13">{{rowData.rcs94Title}}</p>
									<pre class="color3 mt5">{{rowData.rcs94Content}}</pre>
								</div>
								<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs9HowToDenyReceipt}}</p>
							</div>
						</li>
						<li class="slide cardBox" v-show="cShortTab === 5">
				            <div v-for="imgIdx in rcs95ImgLimitSize" :key="imgIdx">  
				            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs95ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
					                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs95ImgInfoList[imgIdx-1].imgUrl+');'">  
				            		</div>
				            		<div v-else>
				            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            		</div>
				            </div>
<!--						
							<div v-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs95ImgInfoList[0])" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs95ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && !isEmpty(rowData.rcs95ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
				                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs95ImgInfoList[0].imgUrl+');'">
				            </div>
-->				            
							<div class="relative">
								<div class="scroll-y" style="min-height:150px">
									<p class="color000 font-size13">{{rowData.rcs95Title}}</p>
									<pre class="color3 mt5">{{rowData.rcs95Content}}</pre>
								</div>
								<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs9HowToDenyReceipt}}</p>
							</div>
						</li>						
					</ul>
				</div>              
            </div>
            <!-- //phoneWrap -->
          </div>
          <div class="float-left consoleCon" style="width:72%">
            <div class="of_h">
              <div class="float-left" style="width:13%"><h4>카드개수</h4></div>
              <div class="float-left" style="width:57%">
                <select name="userConsole04060204_1" class="selectStyle2" style="width:70px" v-model="rcs9CardCount">
                  <option value="3">3</option>
                  <option value="4">4</option>
                  <option value="5">5</option>
                  <option value="6">6</option>
                </select>
              </div>
            </div>

            <div class="of_h">
              <div class="float-right" style="width:87%">
                <ul class="tab_s6" role="tablist">
                  <li role="presentation" class="active"><a href="#card91" aria-controls="card91" role="tab" data-toggle="tab" v-on:click="cShortTab=0">카드 1</a></li>
                  <li role="presentation"><a href="#card92" aria-controls="card92" role="tab" data-toggle="tab" v-on:click="cShortTab=1">카드 2</a></li>
                  <li role="presentation"><a href="#card93" aria-controls="card93" role="tab" data-toggle="tab" v-on:click="cShortTab=2">카드 3</a></li>
                  <li role="presentation" v-if="rcs9CardCount == '4' || rcs9CardCount == '5' || rcs9CardCount == '6' "><a href="#card94" aria-controls="card94" role="tab" data-toggle="tab" v-on:click="cShortTab=3">카드 4</a></li>
                  <li role="presentation" v-if="rcs9CardCount == '5' || rcs9CardCount == '6' "><a href="#card95" aria-controls="card95" role="tab" data-toggle="tab" v-on:click="cShortTab=4">카드 5</a></li>
                  <li role="presentation" v-if="rcs9CardCount == '6'"><a href="#card96" aria-controls="card96" role="tab" data-toggle="tab" v-on:click="cShortTab=5">카드 6</a></li>
                </ul>
              </div>
            </div>

            <div class="tab-content">
              <!--  tab1 -->
              <div role="tabpanel" class="tab-pane active" id="card91">
                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>제목</h4></div>
                  <div class="float-left" style="width:57%">
                    <input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcs90Title" id="rcs90TitleId" @keyup="fnTextLength('제목', '#rcs90TitleId', '', '30')">
                  </div>
                </div>

                <div class="of_h">
                  <div class="float-left" style="width:13%"><h4>내용*</h4></div>
                  <div class="float-left" style="width:57%">
                    <textarea class="textareaStyle height190" v-model="rowData.rcs90Content" :placeholder="rcsPlaceHoder" id="rcs90ContentId" @keyup="fnTextLength('내용', '#rcs90ContentId', '#rcs90TextLength', '1300')"></textarea>
                <strong class="letter" id="rcs90TextLength">(00 / 1300)</strong>
                  </div>
                </div>

                <div class="of_h consolMarginTop" v-if="rowData.msgType == 'IMAGE'">
                  <div class="float-left" style="width:13%"><h4>이미지</h4></div>
                  <div class="float-left" style="width:57%">
                    <div class="float-left" style="width:25%">
                      <a @click="fnRcs90OpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
                    </div>
                    <ul v-for="imgIdx in rcs90ImgLimitSize" :key="imgIdx" class="float-right attachList" style="width:75%; padding:5px 15px; height:30px;">
                      <li v-if="rowData.rcs90ImgInfoList.length > imgIdx -1">
                        <a @click="fnRcs90DelImg(imgIdx-1)">{{fnSubString(rowData.rcs90ImgInfoList[imgIdx-1].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a>
                      </li>
                      <li v-else>
                        <a></a>
                      </li>
                    </ul>
                  </div>
                </div>

                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRow90" class="btnStyle1 backBlack">추가 +</a></div>
                  <div class="float-left" style="width:57%">
                    <table class="table_skin1 mt0" style="width:100%" v-if="button90Flag">
                      <colgroup>
                        <col style="width:22%">
                        <col style="width:20%">
                        <col>
                        <col style="width:15%">
                      </colgroup>
                      <thead>
                        <tr>
                        <th class="text-center">타입</th>
                        <th class="text-center">버튼이름</th>
                        <th class="text-center">버튼링크</th>
                        <th class="text-center end">구분</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(row,index) in rowData.rcs90Buttons" v-bind:key="index">
                          <td class="text-center">
                            <select name="userConsole04060202_1" class="selectStyle2" style="width:100%" v-model="row.buttonType">
                              <option value="U">URL 링크</option>
                              <option value="C">복사하기</option>
                              <option value="T">전화걸기</option>
                              <option value="S">일정추가</option>
                              <option value="M">지도맵</option>
                            </select>
                          </td>
                          <td class="text-left"><input type="text" class="inputStyle" v-model="row.buttonName"></td>
                          <td class="text-center">
                            <input v-if="row.buttonType == 'U'" type="text" class="inputStyle" placeholder="URL입력(http:// 또는 https:// 필수입력)" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'C'" type="text" class="inputStyle" placeholder="복사할 값 입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'T'" type="text" class="inputStyle" placeholder="전화번호입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="제목입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="내용입력" v-model="row.buttonLink1">
                            <p v-if="row.buttonType == 'S'">시작일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs90ButtonSD" :calendarId="row.startDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.startDate" :params="{idx:index}"></Calendar>
		                        <p v-if="row.buttonType == 'S'">종료일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs90ButtonED" :calendarId="row.endDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.endDate"  :params="{idx:index}"></Calendar>
                            <input v-if="row.buttonType == 'M'" type="text" class="inputStyle" placeholder="현재위치공유" readOnly>
                          </td>
                          <td class="text-center end"><a @click="addRow90" title="추가버튼"><i class="far fa-plus channelBtn"></i></a> <a @click="removeRow90(index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a></td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <!--  //tab1 -->

              <!--  tab2 -->
              <div role="tabpanel" class="tab-pane" id="card92">
                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>제목</h4></div>
                  <div class="float-left" style="width:57%">
                    <input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcs91Title" id="rcs91TitleId" @keyup="fnTextLength('제목', '#rcs91TitleId', '', '30')">
                  </div>
                </div>

                <div class="of_h">
                  <div class="float-left" style="width:13%"><h4>내용*</h4></div>
                  <div class="float-left" style="width:57%">
                    <textarea class="textareaStyle height190" v-model="rowData.rcs91Content" :placeholder="rcsPlaceHoder" id="rcs91ContentId" @keyup="fnTextLength('내용', '#rcs91ContentId', '#rcs91TextLength', '1300')"></textarea>
                <strong class="letter" id="rcs91TextLength">(00 / 1300)</strong>
                  </div>
                </div>

                <div class="of_h consolMarginTop" v-if="rowData.msgType == 'IMAGE'">
                  <div class="float-left" style="width:13%"><h4>이미지</h4></div>
                  <div class="float-left" style="width:57%">
                    <div class="float-left" style="width:25%">
                      <a @click="fnRcs91OpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
                    </div>
                    <ul v-for="imgIdx in rcs91ImgLimitSize" :key="imgIdx" class="float-right attachList" style="width:75%; padding:5px 15px; height:30px;">
                      <li v-if="rowData.rcs91ImgInfoList.length > imgIdx -1">
                        
                        <a @click="fnRcs91DelImg(imgIdx-1)">{{fnSubString(rowData.rcs91ImgInfoList[imgIdx-1].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a>
                      </li>
                      <li v-else>
                        <a></a>
                      </li>
                    </ul>
                  </div>
                </div>

                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRow91" class="btnStyle1 backBlack">추가 +</a></div>
                  <div class="float-left" style="width:57%">
                    <table class="table_skin1 mt0" style="width:100%" v-if="button91Flag">
                      <colgroup>
                        <col style="width:22%">
                        <col style="width:20%">
                        <col>
                        <col style="width:15%">
                      </colgroup>
                      <thead>
                        <tr>
                        <th class="text-center">타입</th>
                        <th class="text-center">버튼이름</th>
                        <th class="text-center">버튼링크</th>
                        <th class="text-center end">구분</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(row,index) in rowData.rcs91Buttons" v-bind:key="index">
                          <td class="text-center">
                            <select name="userConsole04060202_1" class="selectStyle2" style="width:100%" v-model="row.buttonType">
                              <option value="U">URL 링크</option>
                              <option value="C">복사하기</option>
                              <option value="T">전화걸기</option>
                              <option value="S">일정추가</option>
                              <option value="M">지도맵</option>
                            </select>
                          </td>
                          <td class="text-left"><input type="text" class="inputStyle" v-model="row.buttonName"></td>
                          <td class="text-center">
                            <input v-if="row.buttonType == 'U'" type="text" class="inputStyle" placeholder="URL입력(http:// 또는 https:// 필수입력)" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'C'" type="text" class="inputStyle" placeholder="복사할 값 입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'T'" type="text" class="inputStyle" placeholder="전화번호입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="제목입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="내용입력" v-model="row.buttonLink1">
                            <p v-if="row.buttonType == 'S'">시작일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs91ButtonSD" :calendarId="row.startDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.startDate" :params="{idx:index}"></Calendar>
		                        <p v-if="row.buttonType == 'S'">종료일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs91ButtonED" :calendarId="row.endDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.endDate"  :params="{idx:index}"></Calendar>
                            <input v-if="row.buttonType == 'M'" type="text" class="inputStyle" placeholder="현재위치공유" readOnly>
                          </td>
                          <td class="text-center end"><a @click="addRow91" title="추가버튼"><i class="far fa-plus channelBtn"></i></a> <a @click="removeRow91(index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a></td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <!--  //tab2 -->

              <!--  tab3 -->
              <div role="tabpanel" class="tab-pane" id="card93">
                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>제목</h4></div>
                  <div class="float-left" style="width:57%">
                    <input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcs92Title" id="rcs92TitleId" @keyup="fnTextLength('제목', '#rcs92TitleId', '', '30')">
                  </div>
                </div>

                <div class="of_h">
                  <div class="float-left" style="width:13%"><h4>내용*</h4></div>
                  <div class="float-left" style="width:57%">
                    <textarea class="textareaStyle height190" v-model="rowData.rcs92Content" :placeholder="rcsPlaceHoder" id="rcs92ContentId" @keyup="fnTextLength('내용', '#rcs92ContentId', '#rcs92TextLength', '1300')"></textarea>
                <strong class="letter" id="rcs92TextLength">(00 / 1300)</strong>
                  </div>
                </div>

                <div class="of_h consolMarginTop" v-if="rowData.msgType == 'IMAGE'">
                  <div class="float-left" style="width:13%"><h4>이미지</h4></div>
                  <div class="float-left" style="width:57%">
                    <div class="float-left" style="width:25%">
                      <a @click="fnRcs92OpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
                    </div>
                    <ul v-for="imgIdx in rcs92ImgLimitSize" :key="imgIdx" class="float-right attachList" style="width:75%; padding:5px 15px; height:30px;">
                      <li v-if="rowData.rcs92ImgInfoList.length > imgIdx -1">
                        
                        <!-- <a @click="fnRcs92DelImg(imgIdx-1)" v-if="rowData.rcs92ImgInfoList.length > 0">{{fnSubString(rowData.rcs92ImgInfoList[imgIdx].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a> -->
                        <a @click="fnRcs92DelImg(imgIdx-1)">{{fnSubString(rowData.rcs92ImgInfoList[imgIdx-1].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a>
                      </li>
                      <li v-else>
                        <a></a>
                      </li>
                    </ul>
                  </div>
                </div>

                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRow92" class="btnStyle1 backBlack">추가 +</a></div>
                  <div class="float-left" style="width:57%">
                    <table class="table_skin1 mt0" style="width:100%" v-if="button92Flag">
                      <colgroup>
                        <col style="width:22%">
                        <col style="width:20%">
                        <col>
                        <col style="width:15%">
                      </colgroup>
                      <thead>
                        <tr>
                        <th class="text-center">타입</th>
                        <th class="text-center">버튼이름</th>
                        <th class="text-center">버튼링크</th>
                        <th class="text-center end">구분</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(row,index) in rowData.rcs92Buttons" v-bind:key="index">
                          <td class="text-center">
                            <select name="userConsole04060202_1" class="selectStyle2" style="width:100%" v-model="row.buttonType">
                              <option value="U">URL 링크</option>
                              <option value="C">복사하기</option>
                              <option value="T">전화걸기</option>
                              <option value="S">일정추가</option>
                              <option value="M">지도맵</option>
                            </select>
                          </td>
                          <td class="text-left"><input type="text" class="inputStyle" v-model="row.buttonName"></td>
                          <td class="text-center">
                            <input v-if="row.buttonType == 'U'" type="text" class="inputStyle" placeholder="URL입력(http:// 또는 https:// 필수입력)" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'C'" type="text" class="inputStyle" placeholder="복사할 값 입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'T'" type="text" class="inputStyle" placeholder="전화번호입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="제목입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="내용입력" v-model="row.buttonLink1">
                            <p v-if="row.buttonType == 'S'">시작일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs92ButtonSD" :calendarId="row.startDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.startDate" :params="{idx:index}"></Calendar>
		                        <p v-if="row.buttonType == 'S'">종료일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs92ButtonED" :calendarId="row.endDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.endDate"  :params="{idx:index}"></Calendar>
                            <input v-if="row.buttonType == 'M'" type="text" class="inputStyle" placeholder="현재위치공유" readOnly>
                          </td>
                          <td class="text-center end"><a @click="addRow92" title="추가버튼"><i class="far fa-plus channelBtn"></i></a> <a @click="removeRow92(index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a></td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <!--  //tab3 -->

              <!--  tab4 -->
              <div role="tabpanel" class="tab-pane" id="card94">
                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>제목</h4></div>
                  <div class="float-left" style="width:57%">
                    <input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcs93Title" id="rcs93TitleId" @keyup="fnTextLength('제목', '#rcs93TitleId', '', '30')">
                  </div>
                </div>

                <div class="of_h">
                  <div class="float-left" style="width:13%"><h4>내용*</h4></div>
                  <div class="float-left" style="width:57%">
                    <textarea class="textareaStyle height190" v-model="rowData.rcs93Content" :placeholder="rcsPlaceHoder" id="rcs93ContentId" @keyup="fnTextLength('내용', '#rcs93ContentId', '#rcs93TextLength', '1300')"></textarea>
                <strong class="letter" id="rcs93TextLength">(00 / 1300)</strong>
                  </div>
                </div>

                <div class="of_h consolMarginTop" v-if="rowData.msgType == 'IMAGE'">
                  <div class="float-left" style="width:13%"><h4>이미지</h4></div>
                  <div class="float-left" style="width:57%">
                    <div class="float-left" style="width:25%">
                      <a @click="fnRcs93OpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
                    </div>
                    <ul v-for="imgIdx in rcs93ImgLimitSize" :key="imgIdx" class="float-right attachList" style="width:75%; padding:5px 15px; height:30px;">
                      <li v-if="rowData.rcs93ImgInfoList.length > imgIdx -1">
                        
                        <!-- <a @click="fnRcs93DelImg(imgIdx-1)" v-if="rowData.rcs93ImgInfoList.length > 0">{{fnSubString(rowData.rcs93ImgInfoList[imgIdx].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a> -->
                        <a @click="fnRcs93DelImg(imgIdx-1)">{{fnSubString(rowData.rcs93ImgInfoList[imgIdx-1].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a>
                      </li>
                      <li v-else>
                        <a></a>
                      </li>
                    </ul>
                  </div>
                </div>

                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRow93" class="btnStyle1 backBlack">추가 +</a></div>
                  <div class="float-left" style="width:57%">
                    <table class="table_skin1 mt0" style="width:100%" v-if="button93Flag">
                      <colgroup>
                        <col style="width:22%">
                        <col style="width:20%">
                        <col>
                        <col style="width:15%">
                      </colgroup>
                      <thead>
                        <tr>
                        <th class="text-center">타입</th>
                        <th class="text-center">버튼이름</th>
                        <th class="text-center">버튼링크</th>
                        <th class="text-center end">구분</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(row,index) in rowData.rcs93Buttons" v-bind:key="index">
                          <td class="text-center">
                            <select name="userConsole04060202_1" class="selectStyle2" style="width:100%" v-model="row.buttonType">
                              <option value="U">URL 링크</option>
                              <option value="C">복사하기</option>
                              <option value="T">전화걸기</option>
                              <option value="S">일정추가</option>
                              <option value="M">지도맵</option>
                            </select>
                          </td>
                          <td class="text-left"><input type="text" class="inputStyle" v-model="row.buttonName"></td>
                          <td class="text-center">
                            <input v-if="row.buttonType == 'U'" type="text" class="inputStyle" placeholder="URL입력(http:// 또는 https:// 필수입력)" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'C'" type="text" class="inputStyle" placeholder="복사할 값 입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'T'" type="text" class="inputStyle" placeholder="전화번호입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="제목입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="내용입력" v-model="row.buttonLink1">
                            <p v-if="row.buttonType == 'S'">시작일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs93ButtonSD" :calendarId="row.startDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.startDate" :params="{idx:index}"></Calendar>
		                        <p v-if="row.buttonType == 'S'">종료일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs93ButtonED" :calendarId="row.endDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.endDate"  :params="{idx:index}"></Calendar>
                            <input v-if="row.buttonType == 'M'" type="text" class="inputStyle" placeholder="현재위치공유" readOnly>
                          </td>
                          <td class="text-center end"><a @click="addRow93" title="추가버튼"><i class="far fa-plus channelBtn"></i></a> <a @click="removeRow93(index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a></td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <!--  //tab4 -->

              <!--  tab5 -->
              <div role="tabpanel" class="tab-pane" id="card95">
                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>제목</h4></div>
                  <div class="float-left" style="width:57%">
                    <input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcs94Title" id="rcs94TitleId" @keyup="fnTextLength('제목', '#rcs94TitleId', '', '30')">
                  </div>
                </div>

                <div class="of_h">
                  <div class="float-left" style="width:13%"><h4>내용*</h4></div>
                  <div class="float-left" style="width:57%">
                    <textarea class="textareaStyle height190" v-model="rowData.rcs94Content" :placeholder="rcsPlaceHoder" id="rcs94ContentId" @keyup="fnTextLength('내용', '#rcs94ContentId', '#rcs94TextLength', '1300')"></textarea>
                <strong class="letter" id="rcs94TextLength">(00 / 1300)</strong>
                  </div>
                </div>

                <div class="of_h consolMarginTop" v-if="rowData.msgType == 'IMAGE'">
                  <div class="float-left" style="width:13%"><h4>이미지</h4></div>
                  <div class="float-left" style="width:57%">
                    <div class="float-left" style="width:25%">
                      <a @click="fnRcs94OpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
                    </div>
                    <ul v-for="imgIdx in rcs94ImgLimitSize" :key="imgIdx" class="float-right attachList" style="width:75%; padding:5px 15px; height:30px;">
                      <li v-if="rowData.rcs94ImgInfoList.length > imgIdx -1">
                        
                        <!-- <a @click="fnRcs94DelImg(imgIdx-1)" v-if="rowData.rcs94ImgInfoList.length > 0">{{fnSubString(rowData.rcs94ImgInfoList[imgIdx].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a> -->
                        <a @click="fnRcs94DelImg(imgIdx-1)">{{fnSubString(rowData.rcs94ImgInfoList[imgIdx-1].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a>
                      </li>
                      <li v-else>
                        <a></a>
                      </li>
                    </ul>
                  </div>
                </div>

                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRow94" class="btnStyle1 backBlack">추가 +</a></div>
                  <div class="float-left" style="width:57%">
                    <table class="table_skin1 mt0" style="width:100%" v-if="button94Flag">
                      <colgroup>
                        <col style="width:22%">
                        <col style="width:20%">
                        <col>
                        <col style="width:15%">
                      </colgroup>
                      <thead>
                        <tr>
                        <th class="text-center">타입</th>
                        <th class="text-center">버튼이름</th>
                        <th class="text-center">버튼링크</th>
                        <th class="text-center end">구분</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(row,index) in rowData.rcs94Buttons" v-bind:key="index">
                          <td class="text-center">
                            <select name="userConsole04060202_1" class="selectStyle2" style="width:100%" v-model="row.buttonType">
                              <option value="U">URL 링크</option>
                              <option value="C">복사하기</option>
                              <option value="T">전화걸기</option>
                              <option value="S">일정추가</option>
                              <option value="M">지도맵</option>
                            </select>
                          </td>
                          <td class="text-left"><input type="text" class="inputStyle" v-model="row.buttonName"></td>
                          <td class="text-center">
                            <input v-if="row.buttonType == 'U'" type="text" class="inputStyle" placeholder="URL입력(http:// 또는 https:// 필수입력)" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'C'" type="text" class="inputStyle" placeholder="복사할 값 입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'T'" type="text" class="inputStyle" placeholder="전화번호입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="제목입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="내용입력" v-model="row.buttonLink1">
                            <p v-if="row.buttonType == 'S'">시작일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs94ButtonSD" :calendarId="row.startDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.startDate" :params="{idx:index}"></Calendar>
		                        <p v-if="row.buttonType == 'S'">종료일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs94ButtonED" :calendarId="row.endDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.endDate"  :params="{idx:index}"></Calendar>
                            <input v-if="row.buttonType == 'M'" type="text" class="inputStyle" placeholder="현재위치공유" readOnly>
                          </td>
                          <td class="text-center end"><a @click="addRow94" title="추가버튼"><i class="far fa-plus channelBtn"></i></a> <a @click="removeRow94(index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a></td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <!--  //tab5 -->

              <!--  tab6 -->
              <div role="tabpanel" class="tab-pane" id="card96">
                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>제목</h4></div>
                  <div class="float-left" style="width:57%">
                    <input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcs95Title" id="rcs95TitleId" @keyup="fnTextLength('제목', '#rcs95TitleId', '', '30')">
                  </div>
                </div>

                <div class="of_h">
                  <div class="float-left" style="width:13%"><h4>내용*</h4></div>
                  <div class="float-left" style="width:57%">
                    <textarea class="textareaStyle height190" v-model="rowData.rcs95Content" :placeholder="rcsPlaceHoder" id="rcs95ContentId" @keyup="fnTextLength('내용', '#rcs95ContentId', '#rcs95TextLength', '1300')"></textarea>
                <strong class="letter" id="rcs95TextLength">(00 / 1300)</strong>
                  </div>
                </div>

                <div class="of_h consolMarginTop" v-if="rowData.msgType == 'IMAGE'">
                  <div class="float-left" style="width:13%"><h4>이미지</h4></div>
                  <div class="float-left" style="width:57%">
                    <div class="float-left" style="width:25%">
                      <a @click="fnRcs95OpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
                    </div>
                    <ul v-for="imgIdx in rcs95ImgLimitSize" :key="imgIdx" class="float-right attachList" style="width:75%; padding:5px 15px; height:30px;">
                      <li v-if="rowData.rcs95ImgInfoList.length > imgIdx -1">
                        
                        <!-- <a @click="fnRcs95DelImg(imgIdx-1)" v-if="rowData.rcs95ImgInfoList.length > 0">{{fnSubString(rowData.rcs95ImgInfoList[imgIdx].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a> -->
                        <a @click="fnRcs95DelImg(imgIdx-1)">{{fnSubString(rowData.rcs95ImgInfoList[imgIdx-1].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a>
                      </li>
                      <li v-else>
                        <a></a>
                      </li>
                    </ul>
                  </div>
                </div>

                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRow95" class="btnStyle1 backBlack">추가 +</a></div>
                  <div class="float-left" style="width:57%">
                    <table class="table_skin1 mt0" style="width:100%" v-if="button95Flag">
                      <colgroup>
                        <col style="width:22%">
                        <col style="width:20%">
                        <col>
                        <col style="width:15%">
                      </colgroup>
                      <thead>
                        <tr>
                        <th class="text-center">타입</th>
                        <th class="text-center">버튼이름</th>
                        <th class="text-center">버튼링크</th>
                        <th class="text-center end">구분</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(row,index) in rowData.rcs95Buttons" v-bind:key="index">
                          <td class="text-center">
                            <select name="userConsole04060202_1" class="selectStyle2" style="width:100%" v-model="row.buttonType">
                              <option value="U">URL 링크</option>
                              <option value="C">복사하기</option>
                              <option value="T">전화걸기</option>
                              <option value="S">일정추가</option>
                              <option value="M">지도맵</option>
                            </select>
                          </td>
                          <td class="text-left"><input type="text" class="inputStyle" v-model="row.buttonName"></td>
                          <td class="text-center">
                            <input v-if="row.buttonType == 'U'" type="text" class="inputStyle" placeholder="URL입력(http:// 또는 https:// 필수입력)" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'C'" type="text" class="inputStyle" placeholder="복사할 값 입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'T'" type="text" class="inputStyle" placeholder="전화번호입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="제목입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="내용입력" v-model="row.buttonLink1">
                            <p v-if="row.buttonType == 'S'">시작일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs95ButtonSD" :calendarId="row.startDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.startDate" :params="{idx:index}"></Calendar>
		                        <p v-if="row.buttonType == 'S'">종료일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs95ButtonED" :calendarId="row.endDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.endDate"  :params="{idx:index}"></Calendar>
                            <input v-if="row.buttonType == 'M'" type="text" class="inputStyle" placeholder="현재위치공유" readOnly>
                          </td>
                          <td class="text-center end"><a @click="addRow95" title="추가버튼"><i class="far fa-plus channelBtn"></i></a> <a @click="removeRow95(index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a></td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <!--  //tab6 -->

              <div v-if="rowData.msgKind == 'A'">
                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>무료수신거부 *</h4></div>
                  <div class="float-left" style="width:57%">
                    <input type="text" class="inputStyle" name="rcs9HowToDenyReceipt" v-model="rowData.rcs9HowToDenyReceipt" placeholder="설정 > 푸시 알림 설정 변경">
                    <!--<p class="color5 txtCaption">광고성 메시지 발송시, 자동으로 (광고)가 표시되오니, 내용에 (광고)문구는 입력하지 않아도 됩니다.</p>-->
                  </div>
                </div>
              </div>

              <div class="of_h consolMarginTop">
                      <div class="float-left" style="width:13%"><h5>발신번호 *</h5></div>
                      <div class="float-left" style="width:57%">
                        <select v-model="rowData.callback" class="selectStyle2 float-right" style="width:100%">
                          <option v-for="info in rcs9CallbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
                        </select>
                      </div>
              </div>                
            </div>


          </div>
        </div>	

      </div>






<!-- 캐러셀 Tall-->
      <div v-if="rcsTemplateTable === 10" >

        <h4>내용작성</h4>
        <div class="of_h mt20">
          <div class="float-left" style="width:28%">
            <!-- phoneWrap -->
            <div class="phoneWrap">
              	<img src="../../../common/images/phoneMockup1.svg" alt="프리 템플릿">
				<div class="phoneCardWrap">
					<p class="color000">[WEB발신] (광고)</p>
					<ul class="cardBxslider mt10">
						<li class="slide cardBox" v-show="cTallTab === 0">
				            <div v-for="imgIdx in rcs100ImgLimitSize" :key="imgIdx">  
				            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs100ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
					                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs100ImgInfoList[imgIdx-1].imgUrl+');'">  
				            		</div>
				            		<div v-else>
				            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            		</div>
				            </div>
<!--						
							<div v-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs100ImgInfoList[0])" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs100ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && !isEmpty(rowData.rcs100ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
				                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs100ImgInfoList[0].imgUrl+');'">
				            </div>
-->				            
							<div class="relative">
								<div class="scroll-y6" style="min-height:140px">
									<p class="color000 font-size13">{{rowData.rcs100Title}}</p>
									<pre class="color3 mt5">{{rowData.rcs100Content}}</pre>
								</div>
								<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs10HowToDenyReceipt}}</p>
							</div>
						</li>
						<li class="slide cardBox" v-show="cTallTab === 1">
				            <div v-for="imgIdx in rcs101ImgLimitSize" :key="imgIdx">  
				            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs101ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
					                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs101ImgInfoList[imgIdx-1].imgUrl+');'">  
				            		</div>
				            		<div v-else>
				            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            		</div>
				            </div>
<!--						
							<div v-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs101ImgInfoList[0])" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs101ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && !isEmpty(rowData.rcs101ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
				                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs101ImgInfoList[0].imgUrl+');'">
				            </div>
-->				            
							<div class="relative">
								<div class="scroll-y6" style="min-height:140px">
									<p class="color000 font-size13">{{rowData.rcs101Title}}</p>
									<pre class="color3 mt5">{{rowData.rcs101Content}}</pre>
								</div>
								<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs10HowToDenyReceipt}}</p>
							</div>
						</li>
						<li class="slide cardBox" v-show="cTallTab === 2">
				            <div v-for="imgIdx in rcs102ImgLimitSize" :key="imgIdx">  
				            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs102ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
					                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs102ImgInfoList[imgIdx-1].imgUrl+');'">  
				            		</div>
				            		<div v-else>
				            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            		</div>
				            </div>
<!--						
							<div v-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs102ImgInfoList[0])" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs102ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && !isEmpty(rowData.rcs102ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
				                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs102ImgInfoList[0].imgUrl+');'">
				            </div>
-->				            
							<div class="relative">
								<div class="scroll-y6" style="min-height:140px">
									<p class="color000 font-size13">{{rowData.rcs102Title}}</p>
									<pre class="color3 mt5">{{rowData.rcs102Content}}</pre>
								</div>
								<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs10HowToDenyReceipt}}</p>
							</div>
						</li>
						<li class="slide cardBox" v-show="cTallTab === 3">
				            <div v-for="imgIdx in rcs103ImgLimitSize" :key="imgIdx">  
				            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs103ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
					                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs103ImgInfoList[imgIdx-1].imgUrl+');'">  
				            		</div>
				            		<div v-else>
				            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            		</div>
				            </div>
<!--						
							<div v-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs103ImgInfoList[0])" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs103ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && !isEmpty(rowData.rcs103ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
				                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs103ImgInfoList[0].imgUrl+');'">
				            </div>
-->				            
							<div class="relative">
								<div class="scroll-y6" style="min-height:140px">
									<p class="color000 font-size13">{{rowData.rcs103Title}}</p>
									<pre class="color3 mt5">{{rowData.rcs103Content}}</pre>
								</div>
								<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs10HowToDenyReceipt}}</p>
							</div>
						</li>
						<li class="slide cardBox" v-show="cTallTab === 4">
				            <div v-for="imgIdx in rcs104ImgLimitSize" :key="imgIdx">  
				            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs104ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
					                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs104ImgInfoList[imgIdx-1].imgUrl+');'">  
				            		</div>
				            		<div v-else>
				            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            		</div>
				            </div>
<!--						
							<div v-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs104ImgInfoList[0])" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs104ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && !isEmpty(rowData.rcs104ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
				                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs104ImgInfoList[0].imgUrl+');'">
				            </div>
-->				            
							<div class="relative">
								<div class="scroll-y6" style="min-height:140px">
									<p class="color000 font-size13">{{rowData.rcs104Title}}</p>
									<pre class="color3 mt5">{{rowData.rcs104Content}}</pre>
								</div>
								<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs10HowToDenyReceipt}}</p>
							</div>
						</li>
						<li class="slide cardBox" v-show="cTallTab === 5">
				            <div v-for="imgIdx in rcs105ImgLimitSize" :key="imgIdx">  
				            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs105ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
					                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs105ImgInfoList[imgIdx-1].imgUrl+');'">  
				            		</div>
				            		<div v-else>
				            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
				            		</div>
				            </div>
<!--						
							<div v-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs105ImgInfoList[0])" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && isEmpty(rowData.rcs105ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && !isEmpty(rowData.rcs105ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
				                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs105ImgInfoList[0].imgUrl+');'">
				            </div>
-->				            
							<div class="relative">
								<div class="scroll-y6" style="min-height:140px">
									<p class="color000 font-size13">{{rowData.rcs105Title}}</p>
									<pre class="color3 mt5">{{rowData.rcs105Content}}</pre>
								</div>
								<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs10HowToDenyReceipt}}</p>
							</div>
						</li>						
					</ul>
				</div>              
            </div>
            <!-- //phoneWrap -->
          </div>
          <div class="float-left consoleCon" style="width:72%">
            <div class="of_h">
              <div class="float-left" style="width:13%"><h4>카드개수</h4></div>
              <div class="float-left" style="width:57%">
                <select name="userConsole04060204_1" class="selectStyle2" style="width:70px" v-model="rcs10CardCount">
                  <option value="3">3</option>
                  <option value="4">4</option>
                  <option value="5">5</option>
                  <option value="6">6</option>
                </select>
              </div>
            </div>

            <div class="of_h">
              <div class="float-right" style="width:87%">
                <ul class="tab_s6" role="tablist">
                  <li role="presentation" class="active"><a href="#card101" aria-controls="card101" role="tab" data-toggle="tab" v-on:click="cTallTab=0">카드 1</a></li>
                  <li role="presentation"><a href="#card102" aria-controls="card102" role="tab" data-toggle="tab" v-on:click="cTallTab=1">카드 2</a></li>
                  <li role="presentation"><a href="#card103" aria-controls="card103" role="tab" data-toggle="tab" v-on:click="cTallTab=2">카드 3</a></li>
                  <li role="presentation" v-if="rcs10CardCount == '4' || rcs10CardCount == '5' || rcs10CardCount == '6' "><a href="#card104" aria-controls="card104" role="tab" data-toggle="tab" v-on:click="cTallTab=3">카드 4</a></li>
                  <li role="presentation" v-if="rcs10CardCount == '5' || rcs10CardCount == '6' "><a href="#card105" aria-controls="card105" role="tab" data-toggle="tab" v-on:click="cTallTab=4">카드 5</a></li>
                  <li role="presentation" v-if="rcs10CardCount == '6'"><a href="#card106" aria-controls="card106" role="tab" data-toggle="tab" v-on:click="cTallTab=5">카드 6</a></li>
                </ul>
              </div>
            </div>

            <div class="tab-content">
              <!--  tab1 -->
              <div role="tabpanel" class="tab-pane active" id="card101">
                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>제목</h4></div>
                  <div class="float-left" style="width:57%">
                    <input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcs100Title" id="rcs100TitleId" @keyup="fnTextLength('제목', '#rcs100TitleId', '', '30')">
                  </div>
                </div>

                <div class="of_h">
                  <div class="float-left" style="width:13%"><h4>내용*</h4></div>
                  <div class="float-left" style="width:57%">
                    <textarea class="textareaStyle height190" v-model="rowData.rcs100Content" :placeholder="rcsPlaceHoder" id="rcs100ContentId" @keyup="fnTextLength('내용', '#rcs100ContentId', '#rcs100TextLength', '1300')"></textarea>
                <strong class="letter" id="rcs100TextLength">(00 / 1300)</strong>
                  </div>
                </div>

                <div class="of_h consolMarginTop" v-if="rowData.msgType == 'IMAGE'">
                  <div class="float-left" style="width:13%"><h4>이미지</h4></div>
                  <div class="float-left" style="width:57%">
                    <div class="float-left" style="width:25%">
                      <a @click="fnRcs100OpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
                    </div>
                    <ul v-for="imgIdx in rcs100ImgLimitSize" :key="imgIdx" class="float-right attachList" style="width:75%; padding:5px 15px; height:30px;">
                      <li v-if="rowData.rcs100ImgInfoList.length > imgIdx -1">
                        <a @click="fnRcs100DelImg(imgIdx-1)">{{fnSubString(rowData.rcs100ImgInfoList[imgIdx-1].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a>
                      </li>
                      <li v-else>
                        <a></a>
                      </li>
                    </ul>
                  </div>
                </div>

                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRow100" class="btnStyle1 backBlack">추가 +</a></div>
                  <div class="float-left" style="width:57%">
                    <table class="table_skin1 mt0" style="width:100%" v-if="button100Flag">
                      <colgroup>
                        <col style="width:22%">
                        <col style="width:20%">
                        <col>
                        <col style="width:15%">
                      </colgroup>
                      <thead>
                        <tr>
                        <th class="text-center">타입</th>
                        <th class="text-center">버튼이름</th>
                        <th class="text-center">버튼링크</th>
                        <th class="text-center end">구분</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(row,index) in rowData.rcs100Buttons" v-bind:key="index">
                          <td class="text-center">
                            <select name="userConsole04060202_1" class="selectStyle2" style="width:100%" v-model="row.buttonType">
                              <option value="U">URL 링크</option>
                              <option value="C">복사하기</option>
                              <option value="T">전화걸기</option>
                              <option value="S">일정추가</option>
                              <option value="M">지도맵</option>
                            </select>
                          </td>
                          <td class="text-left"><input type="text" class="inputStyle" v-model="row.buttonName"></td>
                          <td class="text-center">
                            <input v-if="row.buttonType == 'U'" type="text" class="inputStyle" placeholder="URL입력(http:// 또는 https:// 필수입력)" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'C'" type="text" class="inputStyle" placeholder="복사할 값 입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'T'" type="text" class="inputStyle" placeholder="전화번호입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="제목입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="내용입력" v-model="row.buttonLink1">
                            <p v-if="row.buttonType == 'S'">시작일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs100ButtonSD" :calendarId="row.startDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.startDate" :params="{idx:index}"></Calendar>
		                        <p v-if="row.buttonType == 'S'">종료일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs100ButtonED" :calendarId="row.endDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.endDate"  :params="{idx:index}"></Calendar>
                            <input v-if="row.buttonType == 'M'" type="text" class="inputStyle" placeholder="현재위치공유" readOnly>
                          </td>
                          <td class="text-center end"><a @click="addRow100" title="추가버튼"><i class="far fa-plus channelBtn"></i></a> <a @click="removeRow100(index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a></td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <!--  //tab1 -->

              <!--  tab2 -->
              <div role="tabpanel" class="tab-pane" id="card102">
                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>제목</h4></div>
                  <div class="float-left" style="width:57%">
                    <input type="text" class="inputStyle" placeholder="최대 30자 입력 가능합니다." v-model="rowData.rcs101Title" id="rcs101TitleId" @keyup="fnTextLength('제목', '#rcs101TitleId', '', '30')">
                  </div>
                </div>

                <div class="of_h">
                  <div class="float-left" style="width:13%"><h4>내용*</h4></div>
                  <div class="float-left" style="width:57%">
                    <textarea class="textareaStyle height190" v-model="rowData.rcs101Content" :placeholder="rcsPlaceHoder" id="rcs101ContentId" @keyup="fnTextLength('내용', '#rcs101ContentId', '#rcs101TextLength', '1300')"></textarea>
                <strong class="letter" id="rcs101TextLength">(00 / 1300)</strong>
                  </div>
                </div>

                <div class="of_h consolMarginTop" v-if="rowData.msgType == 'IMAGE'">
                  <div class="float-left" style="width:13%"><h4>이미지</h4></div>
                  <div class="float-left" style="width:57%">
                    <div class="float-left" style="width:25%">
                      <a @click="fnRcs101OpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
                    </div>
                    <ul v-for="imgIdx in rcs101ImgLimitSize" :key="imgIdx" class="float-right attachList" style="width:75%; padding:5px 15px; height:30px;">
                      <li v-if="rowData.rcs101ImgInfoList.length > imgIdx -1">
                        <a @click="fnRcs101DelImg(imgIdx-1)">{{fnSubString(rowData.rcs101ImgInfoList[imgIdx-1].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a>
                      </li>
                      <li v-else>
                        <a></a>
                      </li>
                    </ul>
                  </div>
                </div>

                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRow101" class="btnStyle1 backBlack">추가 +</a></div>
                  <div class="float-left" style="width:57%">
                    <table class="table_skin1 mt0" style="width:100%" v-if="button101Flag">
                      <colgroup>
                        <col style="width:22%">
                        <col style="width:20%">
                        <col>
                        <col style="width:15%">
                      </colgroup>
                      <thead>
                        <tr>
                        <th class="text-center">타입</th>
                        <th class="text-center">버튼이름</th>
                        <th class="text-center">버튼링크</th>
                        <th class="text-center end">구분</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(row,index) in rowData.rcs101Buttons" v-bind:key="index">
                          <td class="text-center">
                            <select name="userConsole04060202_1" class="selectStyle2" style="width:100%" v-model="row.buttonType">
                              <option value="U">URL 링크</option>
                              <option value="C">복사하기</option>
                              <option value="T">전화걸기</option>
                              <option value="S">일정추가</option>
                              <option value="M">지도맵</option>
                            </select>
                          </td>
                          <td class="text-left"><input type="text" class="inputStyle" v-model="row.buttonName"></td>
                          <td class="text-center">
                            <input v-if="row.buttonType == 'U'" type="text" class="inputStyle" placeholder="URL입력(http:// 또는 https:// 필수입력)" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'C'" type="text" class="inputStyle" placeholder="복사할 값 입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'T'" type="text" class="inputStyle" placeholder="전화번호입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="제목입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="내용입력" v-model="row.buttonLink1">
                            <p v-if="row.buttonType == 'S'">시작일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs101ButtonSD" :calendarId="row.startDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.startDate" :params="{idx:index}"></Calendar>
		                        <p v-if="row.buttonType == 'S'">종료일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs101ButtonED" :calendarId="row.endDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.endDate"  :params="{idx:index}"></Calendar>
                            <input v-if="row.buttonType == 'M'" type="text" class="inputStyle" placeholder="현재위치공유" readOnly>
                          </td>
                          <td class="text-center end"><a @click="addRow101" title="추가버튼"><i class="far fa-plus channelBtn"></i></a> <a @click="removeRow101(index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a></td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <!--  //tab2 -->

              <!--  tab3 -->
              <div role="tabpanel" class="tab-pane" id="card103">
                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>제목</h4></div>
                  <div class="float-left" style="width:57%">
                    <input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcs102Title" id="rcs102TitleId" @keyup="fnTextLength('제목', '#rcs102TitleId', '', '30')">
                  </div>
                </div>

                <div class="of_h">
                  <div class="float-left" style="width:13%"><h4>내용*</h4></div>
                  <div class="float-left" style="width:57%">
                    <textarea class="textareaStyle height190" v-model="rowData.rcs102Content" :placeholder="rcsPlaceHoder" id="rcs102ContentId" @keyup="fnTextLength('내용', '#rcs102ContentId', '#rcs102TextLength', '1300')"></textarea>
                <strong class="letter" id="rcs102TextLength">(00 / 1300)</strong>
                  </div>
                </div>

                <div class="of_h consolMarginTop" v-if="rowData.msgType == 'IMAGE'">
                  <div class="float-left" style="width:13%"><h4>이미지</h4></div>
                  <div class="float-left" style="width:57%">
                    <div class="float-left" style="width:25%">
                      <a @click="fnRcs102OpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
                    </div>
                    <ul v-for="imgIdx in rcs102ImgLimitSize" :key="imgIdx" class="float-right attachList" style="width:75%; padding:5px 15px; height:30px;">
                      <li v-if="rowData.rcs102ImgInfoList.length > imgIdx -1">
                        <a @click="fnRcs102DelImg(imgIdx-1)">{{fnSubString(rowData.rcs102ImgInfoList[imgIdx-1].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a>
                      </li>
                      <li v-else>
                        <a></a>
                      </li>
                    </ul>
                  </div>
                </div>

                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRow102" class="btnStyle1 backBlack">추가 +</a></div>
                  <div class="float-left" style="width:57%">
                    <table class="table_skin1 mt0" style="width:100%" v-if="button102Flag">
                      <colgroup>
                        <col style="width:22%">
                        <col style="width:20%">
                        <col>
                        <col style="width:15%">
                      </colgroup>
                      <thead>
                        <tr>
                        <th class="text-center">타입</th>
                        <th class="text-center">버튼이름</th>
                        <th class="text-center">버튼링크</th>
                        <th class="text-center end">구분</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(row,index) in rowData.rcs102Buttons" v-bind:key="index">
                          <td class="text-center">
                            <select name="userConsole04060202_1" class="selectStyle2" style="width:100%" v-model="row.buttonType">
                              <option value="U">URL 링크</option>
                              <option value="C">복사하기</option>
                              <option value="T">전화걸기</option>
                              <option value="S">일정추가</option>
                              <option value="M">지도맵</option>
                            </select>
                          </td>
                          <td class="text-left"><input type="text" class="inputStyle" v-model="row.buttonName"></td>
                          <td class="text-center">
                            <input v-if="row.buttonType == 'U'" type="text" class="inputStyle" placeholder="URL입력(http:// 또는 https:// 필수입력)" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'C'" type="text" class="inputStyle" placeholder="복사할 값 입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'T'" type="text" class="inputStyle" placeholder="전화번호입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="제목입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="내용입력" v-model="row.buttonLink1">
                            <p v-if="row.buttonType == 'S'">시작일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs102ButtonSD" :calendarId="row.startDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.startDate" :params="{idx:index}"></Calendar>
		                        <p v-if="row.buttonType == 'S'">종료일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs102ButtonED" :calendarId="row.endDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.endDate"  :params="{idx:index}"></Calendar>
                            <input v-if="row.buttonType == 'M'" type="text" class="inputStyle" placeholder="현재위치공유" readOnly>
                          </td>
                          <td class="text-center end"><a @click="addRow102" title="추가버튼"><i class="far fa-plus channelBtn"></i></a> <a @click="removeRow102(index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a></td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <!--  //tab3 -->

              <!--  tab4 -->
              <div role="tabpanel" class="tab-pane" id="card104">
                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>제목</h4></div>
                  <div class="float-left" style="width:57%">
                    <input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcs103Title" id="rcs103TitleId" @keyup="fnTextLength('제목', '#rcs103TitleId', '', '30')">
                  </div>
                </div>

                <div class="of_h">
                  <div class="float-left" style="width:13%"><h4>내용*</h4></div>
                  <div class="float-left" style="width:57%">
                    <textarea class="textareaStyle height190" v-model="rowData.rcs103Content" :placeholder="rcsPlaceHoder" id="rcs103ContentId" @keyup="fnTextLength('내용', '#rcs103ContentId', '#rcs103TextLength', '1300')"></textarea>
                <strong class="letter" id="rcs103TextLength">(00 / 1300)</strong>
                  </div>
                </div>

                <div class="of_h consolMarginTop" v-if="rowData.msgType == 'IMAGE'">
                  <div class="float-left" style="width:13%"><h4>이미지</h4></div>
                  <div class="float-left" style="width:57%">
                    <div class="float-left" style="width:25%">
                      <a @click="fnRcs103OpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
                    </div>
                    <ul v-for="imgIdx in rcs103ImgLimitSize" :key="imgIdx" class="float-right attachList" style="width:75%; padding:5px 15px; height:30px;">
                      <li v-if="rowData.rcs103ImgInfoList.length > imgIdx -1">
                        <a @click="fnRcs103DelImg(imgIdx-1)">{{fnSubString(rowData.rcs103ImgInfoList[imgIdx-1].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a>
                      </li>
                      <li v-else>
                        <a></a>
                      </li>
                    </ul>
                  </div>
                </div>

                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRow103" class="btnStyle1 backBlack">추가 +</a></div>
                  <div class="float-left" style="width:57%">
                    <table class="table_skin1 mt0" style="width:100%" v-if="button103Flag">
                      <colgroup>
                        <col style="width:22%">
                        <col style="width:20%">
                        <col>
                        <col style="width:15%">
                      </colgroup>
                      <thead>
                        <tr>
                        <th class="text-center">타입</th>
                        <th class="text-center">버튼이름</th>
                        <th class="text-center">버튼링크</th>
                        <th class="text-center end">구분</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(row,index) in rowData.rcs103Buttons" v-bind:key="index">
                          <td class="text-center">
                            <select name="userConsole04060202_1" class="selectStyle2" style="width:100%" v-model="row.buttonType">
                              <option value="U">URL 링크</option>
                              <option value="C">복사하기</option>
                              <option value="T">전화걸기</option>
                              <option value="S">일정추가</option>
                              <option value="M">지도맵</option>
                            </select>
                          </td>
                          <td class="text-left"><input type="text" class="inputStyle" v-model="row.buttonName"></td>
                          <td class="text-center">
                            <input v-if="row.buttonType == 'U'" type="text" class="inputStyle" placeholder="URL입력(http:// 또는 https:// 필수입력)" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'C'" type="text" class="inputStyle" placeholder="복사할 값 입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'T'" type="text" class="inputStyle" placeholder="전화번호입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="제목입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="내용입력" v-model="row.buttonLink1">
                            <p v-if="row.buttonType == 'S'">시작일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs103ButtonSD" :calendarId="row.startDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.startDate" :params="{idx:index}"></Calendar>
		                        <p v-if="row.buttonType == 'S'">종료일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs103ButtonED" :calendarId="row.endDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.endDate"  :params="{idx:index}"></Calendar>
                            <input v-if="row.buttonType == 'M'" type="text" class="inputStyle" placeholder="현재위치공유" readOnly>
                          </td>
                          <td class="text-center end"><a @click="addRow103" title="추가버튼"><i class="far fa-plus channelBtn"></i></a> <a @click="removeRow103(index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a></td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <!--  //tab4 -->

              <!--  tab5 -->
              <div role="tabpanel" class="tab-pane" id="card105">
                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>제목</h4></div>
                  <div class="float-left" style="width:57%">
                    <input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcs104Title" id="rcs104TitleId" @keyup="fnTextLength('제목', '#rcs104TitleId', '', '30')">
                  </div>
                </div>

                <div class="of_h">
                  <div class="float-left" style="width:13%"><h4>내용*</h4></div>
                  <div class="float-left" style="width:57%">
                    <textarea class="textareaStyle height190" v-model="rowData.rcs104Content" :placeholder="rcsPlaceHoder" id="rcs104ContentId" @keyup="fnTextLength('내용', '#rcs104ContentId', '#rcs104TextLength', '1300')"></textarea>
                <strong class="letter" id="rcs104TextLength">(00 / 1300)</strong>
                  </div>
                </div>

                <div class="of_h consolMarginTop" v-if="rowData.msgType == 'IMAGE'">
                  <div class="float-left" style="width:13%"><h4>이미지</h4></div>
                  <div class="float-left" style="width:57%">
                    <div class="float-left" style="width:25%">
                      <a @click="fnRcs104OpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
                    </div>
                    <ul v-for="imgIdx in rcs104ImgLimitSize" :key="imgIdx" class="float-right attachList" style="width:75%; padding:5px 15px; height:30px;">
                      <li v-if="rowData.rcs104ImgInfoList.length > imgIdx -1">
                         <a @click="fnRcs104DelImg(imgIdx-1)">{{fnSubString(rowData.rcs104ImgInfoList[imgIdx-1].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a>
                      </li>
                      <li v-else>
                        <a></a>
                      </li>
                    </ul>
                  </div>
                </div>

                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRow104" class="btnStyle1 backBlack">추가 +</a></div>
                  <div class="float-left" style="width:57%">
                    <table class="table_skin1 mt0" style="width:100%" v-if="button104Flag">
                      <colgroup>
                        <col style="width:22%">
                        <col style="width:20%">
                        <col>
                        <col style="width:15%">
                      </colgroup>
                      <thead>
                        <tr>
                        <th class="text-center">타입</th>
                        <th class="text-center">버튼이름</th>
                        <th class="text-center">버튼링크</th>
                        <th class="text-center end">구분</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(row,index) in rowData.rcs104Buttons" v-bind:key="index">
                          <td class="text-center">
                            <select name="userConsole04060202_1" class="selectStyle2" style="width:100%" v-model="row.buttonType">
                              <option value="U">URL 링크</option>
                              <option value="C">복사하기</option>
                              <option value="T">전화걸기</option>
                              <option value="S">일정추가</option>
                              <option value="M">지도맵</option>
                            </select>
                          </td>
                          <td class="text-left"><input type="text" class="inputStyle" v-model="row.buttonName"></td>
                          <td class="text-center">
                            <input v-if="row.buttonType == 'U'" type="text" class="inputStyle" placeholder="URL입력(http:// 또는 https:// 필수입력)" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'C'" type="text" class="inputStyle" placeholder="복사할 값 입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'T'" type="text" class="inputStyle" placeholder="전화번호입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="제목입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="내용입력" v-model="row.buttonLink1">
                            <p v-if="row.buttonType == 'S'">시작일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs104ButtonSD" :calendarId="row.startDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.startDate" :params="{idx:index}"></Calendar>
		                        <p v-if="row.buttonType == 'S'">종료일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs104ButtonED" :calendarId="row.endDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.endDate"  :params="{idx:index}"></Calendar>
                            <input v-if="row.buttonType == 'M'" type="text" class="inputStyle" placeholder="현재위치공유" readOnly>
                          </td>
                          <td class="text-center end"><a @click="addRow104" title="추가버튼"><i class="far fa-plus channelBtn"></i></a> <a @click="removeRow104(index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a></td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <!--  //tab5 -->

              <!--  tab6 -->
              <div role="tabpanel" class="tab-pane" id="card106">
                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>제목</h4></div>
                  <div class="float-left" style="width:57%">
                    <input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcs105Title" id="rcs105TitleId" @keyup="fnTextLength('제목', '#rcs105TitleId', '', '30')">
                  </div>
                </div>

                <div class="of_h">
                  <div class="float-left" style="width:13%"><h4>내용*</h4></div>
                  <div class="float-left" style="width:57%">
                    <textarea class="textareaStyle height190" v-model="rowData.rcs105Content" :placeholder="rcsPlaceHoder" id="rcs105ContentId" @keyup="fnTextLength('내용', '#rcs105ContentId', '#rcs105TextLength', '1300')"></textarea>
                <strong class="letter" id="rcs105TextLength">(00 / 1300)</strong>
                  </div>
                </div>

                <div class="of_h consolMarginTop" v-if="rowData.msgType == 'IMAGE'">
                  <div class="float-left" style="width:13%"><h4>이미지</h4></div>
                  <div class="float-left" style="width:57%">
                    <div class="float-left" style="width:25%">
                      <a @click="fnRcs105OpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
                    </div>
                    <ul v-for="imgIdx in rcs105ImgLimitSize" :key="imgIdx" class="float-right attachList" style="width:75%; padding:5px 15px; height:30px;">
                      <li v-if="rowData.rcs105ImgInfoList.length > imgIdx -1">
                        <a @click="fnRcs105DelImg(imgIdx-1)">{{fnSubString(rowData.rcs105ImgInfoList[imgIdx-1].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a>
                      </li>
                      <li v-else>
                        <a></a>
                      </li>
                    </ul>
                  </div>
                </div>

                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRow105" class="btnStyle1 backBlack">추가 +</a></div>
                  <div class="float-left" style="width:57%">
                    <table class="table_skin1 mt0" style="width:100%" v-if="button105Flag">
                      <colgroup>
                        <col style="width:22%">
                        <col style="width:20%">
                        <col>
                        <col style="width:15%">
                      </colgroup>
                      <thead>
                        <tr>
                        <th class="text-center">타입</th>
                        <th class="text-center">버튼이름</th>
                        <th class="text-center">버튼링크</th>
                        <th class="text-center end">구분</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(row,index) in rowData.rcs105Buttons" v-bind:key="index">
                          <td class="text-center">
                            <select name="userConsole04060202_1" class="selectStyle2" style="width:100%" v-model="row.buttonType">
                              <option value="U">URL 링크</option>
                              <option value="C">복사하기</option>
                              <option value="T">전화걸기</option>
                              <option value="S">일정추가</option>
                              <option value="M">지도맵</option>
                            </select>
                          </td>
                          <td class="text-left"><input type="text" class="inputStyle" v-model="row.buttonName"></td>
                          <td class="text-center">
                            <input v-if="row.buttonType == 'U'" type="text" class="inputStyle" placeholder="URL입력(http:// 또는 https:// 필수입력)" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'C'" type="text" class="inputStyle" placeholder="복사할 값 입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'T'" type="text" class="inputStyle" placeholder="전화번호입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="제목입력" v-model="row.buttonLink">
                            <input v-if="row.buttonType == 'S'" type="text" class="inputStyle" placeholder="내용입력" v-model="row.buttonLink1">
                            <p v-if="row.buttonType == 'S'">시작일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs105ButtonSD" :calendarId="row.startDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.startDate" :params="{idx:index}"></Calendar>
		                        <p v-if="row.buttonType == 'S'">종료일</p><Calendar v-if="row.buttonType == 'S'" @update-date="fnRcs105ButtonED" :calendarId="row.endDateId" classProps="datepicker inputStyle maxWidth200" :initDate="row.endDate"  :params="{idx:index}"></Calendar>

                            <input v-if="row.buttonType == 'M'" type="text" class="inputStyle" placeholder="현재위치공유" readOnly>
                          </td>
                          <td class="text-center end"><a @click="addRow105" title="추가버튼"><i class="far fa-plus channelBtn"></i></a> <a @click="removeRow105(index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a></td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <!--  //tab6 -->

              <div v-if="rowData.msgKind == 'A'">
                <div class="of_h consolMarginTop">
                  <div class="float-left" style="width:13%"><h4>무료수신거부 *</h4></div>
                  <div class="float-left" style="width:57%">
                    <input type="text" class="inputStyle" name="rcs10HowToDenyReceipt" v-model="rowData.rcs10HowToDenyReceipt" placeholder="설정 > 푸시 알림 설정 변경">
                    <!--<p class="color5 txtCaption">광고성 메시지 발송시, 자동으로 (광고)가 표시되오니, 내용에 (광고)문구는 입력하지 않아도 됩니다.</p>-->
                  </div>
                </div>
              </div>

              <div class="of_h consolMarginTop">
                    <div class="float-left" style="width:13%"><h5>발신번호 *</h5></div>
                    <div class="float-left" style="width:57%">
                      <select v-model="rowData.callback" class="selectStyle2 float-right" style="width:100%">
                        <option v-for="info in rcs10CallbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
                      </select>
                    </div>
              </div>               
            </div>


          </div>
        </div>	

			</div>
		</div>

		<!-- KAKAO -->
		<div class="of_h mt20" v-show="channelTab === 2" >
			<h4>카카오톡 상품</h4>
			<div class="templateBox" style="width:80%">
				<ul class="of_h">
					<li class="float-left" style="width:11%">
						<div class="talk_Gray"><h5>친구톡</h5></div>
						<div style="width:78px">
							<div class="consolMarginTop text-center">
								<input type="radio" name="kakao" value="friend" id="friend" class="radioStyle" v-on:click="kakaoTemplateTable=0" checked><label for="friend"></label>
							</div>
						</div>
					</li>
					<li class="float-left" style="width:11%" v-if="rowData.msgKind == 'I'"><!-- 메시지 구분이 광고성일 경우 사용불가 -->
						<div class="talk_Yellow"><h5>알림톡</h5></div>
						<div style="width:78px">
							<div class="consolMarginTop text-center">
								<input type="radio" name="kakao" value="Notification" id="Notification" class="radioStyle" v-on:click="kakaoTemplateTable=1"><label for="Notification"></label>
							</div>
						</div>
					</li>
				</ul>
			</div>
			<!-- KAKAO 친구톡 -->
			<div class="of_h mt20" v-if="kakaoTemplateTable === 0">
				<div class="float-left" style="width:28%">
					<!-- phoneWrap -->
					<div class="phoneWrap">
						<img src="@/assets/images/common/phoneMockup2_1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap4 scroll-y">
							<p v-if="rowData.msgKind == 'A'">[광고]</p>
							<div class="mt5">
								<div class="text-sub-wrap">
									<div v-if="!$gfnCommonUtils.isEmpty(rowData.friendTalkImgInfo.imgUrl)" class="phoneText2 mt10 text-center" :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.friendTalkImgInfo.imgUrl+');'">
									</div>
									<br v-if="$gfnCommonUtils.isEmpty(rowData.friendTalkImgInfo.imgUrl)"/>
									<span><pre>{{rowData.friendTalkContent}}</pre></span>
									<div v-for="(buttonInfo, idx) in rowData.friendTalkButtons" :key="idx">
										<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.name)" class="btnStyle1 backLightGray">{{buttonInfo.name}}</a>
									</div>
								</div>
								<p class="text-sub_2" v-if="rowData.msgKind == 'A'">수신거부: 홈 > 친구차단</p>
							</div>
						</div>
					</div>
					<!-- //phoneWrap -->
				</div>
				<div class="float-left consoleCon" style="width:72%">
					<div class="of_h">
						<div class="float-left" style="width:13%"><h4>발신 프로필 *</h4></div>
						<div class="float-left" style="width:59%">
							<select class="float-left selectStyle2" style="width:100%" v-model="rowData.friendTalkSenderKey">
								<option value="">선택해주세요.</option>
								<option v-for="senderKeyInfo in friendTalkSenderKeyList" :key="senderKeyInfo.senderKey" :value="senderKeyInfo.senderKey">{{senderKeyInfo.senderKey}}</option>
							</select>
						</div>
					</div>
					<div class="of_h consolMarginTop" v-if="rowData.msgType == 'IMAGE'">
						<div class="float-left" style="width:13%"><h4>이미지</h4></div>
						<div class="float-left" style="width:59%">
							<div class="float-left" style="width:25%">
								<a @click="fnFriendTalkOpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
							</div>
							<ul class="float-right attachList" style="width:74%; padding:5px 15px; height:30px;">
								<li><a @click="fnFriendTalkDelImg">{{fnSubString(rowData.friendTalkImgInfo.imgUrl, 0, 35)}}  <i v-if="!isEmpty(rowData.friendTalkImgInfo.imgUrl)" class="fal fa-times"></i></a></li>
							</ul>
						</div>
					</div>
					<div class="of_h consolMarginTop" v-if="rowData.msgType == 'IMAGE'">
						<div class="float-left" style="width:13%"><h4>이미지 링크</h4></div>
						<div class="float-left" style="width:59%">
							<input type="text" class="inputStyle" placeholder="http://..." id="friendTalkImageLinkId" v-model="rowData.friendTalkImageLink">
						</div>
					</div>
					<div class="of_h consolMarginTop">
						<div class="float-left" style="width:13%"><h4>내용*</h4></div>
						<div class="float-left" style="width:59%">
							<textarea class="textareaStyle height190" v-model="rowData.friendTalkContent" :placeholder="kakaoPlaceHoder"></textarea>
						</div>
					</div>					
					<div class="of_h consolMarginTop">
						<div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRowFriendTalk" class="btnStyle1 backBlack">추가 +</a></div>
						<div class="float-left" style="width:57%">
							<table class="table_skin1 mt0" style="width:100%" v-if="buttonFriendTalkFlag">
								<colgroup>
									<col style="width:22%">
									<col style="width:20%">
									<col>
									<col style="width:15%">
								</colgroup>
								<thead>
									<tr>
										<th class="text-center">타입</th>
										<th class="text-center">버튼이름</th>
										<th class="text-center">버튼링크</th>
										<th class="text-center end">구분</th>
									</tr>
								</thead>
								<tbody>
									<template v-for="(buttonInfo, idx) in rowData.friendTalkButtons">
										<tr :key="idx">
											<td class="text-center" :rowspan="buttonInfo.type == 'WL' || buttonInfo.type == 'AL' ? '2' : '1'">
												<select class="float-left selectStyle2" style="width:100%" v-model="buttonInfo.type" @change="fnChgBtnType(idx)">
													<option v-for="friendTalkButtonType in friendTalkButtonTypeList" :key="friendTalkButtonType.type" :value="friendTalkButtonType.type">{{friendTalkButtonType.name}}</option>
												</select>
											</td>
											<td class="text-left" :rowspan="buttonInfo.type == 'WL' || buttonInfo.type == 'AL' ? '2' : '1'">
												<input type="text" class="inputStyle" v-model="buttonInfo.name" maxlength="20">
											</td>
											<td v-if="buttonInfo.type == 'WL' || buttonInfo.type == 'AL'" class="text-center">
												<div v-if="buttonInfo.type == 'WL'">
													<h6 class="font-normal float-left" style="width:20%">Mobile</h6>
													<input type="text" class="inputStyle float-right" style="width:68%" v-model="buttonInfo['linkUrl1']" maxlength="200">
												</div>
												<div v-if="buttonInfo.type == 'AL'">
													<h6 class="font-normal float-left" style="width:20%">Android</h6>
													<input type="text" class="inputStyle float-right" style="width:68%" v-model="buttonInfo['linkUrl1']" maxlength="200">
												</div>
											</td>
											<td v-else>
											</td>
											<td class="text-center end" :rowspan="buttonInfo.type == 'WL' || buttonInfo.type == 'AL' ? '2' : '1'">
												<a @click="addRowFriendTalk" title="추가버튼"><i class="far fa-plus channelBtn"></i></a>
												<a @click="removeRowFriendTalk(idx)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a>
											</td>
										</tr>
										<tr v-if="buttonInfo.type == 'WL' || buttonInfo.type == 'AL'" :key="idx+'_sub'">
											<td class="text-left of_h">
												<div v-if="buttonInfo.type == 'WL'">
													<h6 class="font-normal float-left" style="width:20%">PC</h6>
													<input type="text" class="inputStyle float-right" style="width:68%" v-model="buttonInfo['linkUrl2']" maxlength="200">
												</div>
												<div v-if="buttonInfo.type == 'AL'">
													<h6 class="font-normal float-left" style="width:20%">IOS</h6>
													<input type="text" class="inputStyle float-right" style="width:68%" v-model="buttonInfo['linkUrl2']" maxlength="200">
												</div>
											</td>
										</tr>
									</template>
								</tbody>
							</table>		                    
						</div>
					</div>
				</div>
			</div>	
			<!-- KAKAO 알림톡 -->			
			<div class="of_h mt20" v-if="kakaoTemplateTable === 1">
				<div class="float-left" style="width:28%">
					<!-- phoneWrap -->
					<div class="phoneWrap">
						<img src="@/assets/images/common/phoneMockup3.svg" alt="알림톡 템플릿">
						<div class="phoneTextWrap3 scroll-y">
							<div>
								<p class="text-main"><i class="fal fa-envelope-open-text"></i> 알림톡 도착</p>
								<div v-if="rowData.emphasizeType == 'TEXT'" class="text-sub-wrap" style="padding:10px;">
									<p v-if="!$gfnCommonUtils.isEmpty(rowData.tmpltEmpsSubTitle)" class="text-sub_1">{{rowData.tmpltEmpsSubTitle}}</p>
									<p v-if="!$gfnCommonUtils.isEmpty(rowData.tmpltEmpsTitle)" class="text-sub scroll-y3">{{rowData.tmpltEmpsTitle}}</p>
								</div>
								<div class="text-sub-wrap" style="padding:10px;">
									<span><pre>{{rowData.tmpltContent}}</pre></span>
								</div>
								<div v-for="(buttonInfo, idx) in rowData.buttonList" :key="idx">
									<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.name)" class="btnStyle1 backLightGray">{{buttonInfo.name}}</a>
								</div>
							</div>
						</div>
					</div>
					<!-- //phoneWrap -->
				</div>
				<div class="float-left consoleCon" style="width:72%">
					<div class="text-right" style="width:72%">
						<a href="#" class="btnStyle1 backBlack" style="min-width:auto" @click.prevent="fnOpenAlimTalkTemplatePopup" title="알림톡 템플릿 불러오기">알림톡 템플릿 선택</a>
					</div>
					<div v-if="selectedAlimTalkTemplate">
						<div class="of_h mt20">
							<div class="float-left" style="width:15%"><h4>발신프로필</h4></div>
							<div class="float-left" style="width:57%">
								<select class="float-left selectStyle2" style="width:100%" v-model="rowData.senderKey" disabled>
									<option value="">선택해주세요.</option>
									<option v-for="senderKeyInfo in friendTalkSenderKeyList" :key="senderKeyInfo.senderKey" :value="senderKeyInfo.senderKey">{{senderKeyInfo.senderKey}}</option>
								</select>
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:15%"><h4>템플릿 명</h4></div>
							<div class="float-left" style="width:57%"><input type="text" class="inputStyle" v-model="rowData.tmpltName" disabled></div>
						</div>
						<div class="of_h consolMarginTop" v-if="rowData.emphasizeType == 'TEXT'">
							<div class="float-left" style="width:15%"><h4>템플릿강조제목</h4></div>
							<div class="float-left" style="width:57%"><input type="text" class="inputStyle" v-model="rowData.tmpltEmpsTitle" disabled></div>
						</div>
						<div class="of_h consolMarginTop" v-if="rowData.emphasizeType == 'TEXT'">
							<div class="float-left" style="width:15%"><h4>템플릿강조부제목</h4></div>
							<div class="float-left" style="width:57%"><input type="text" class="inputStyle" v-model="rowData.tmpltEmpsSubTitle" disabled></div>
						</div>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:15%"><h4>내용</h4></div>
							<div class="float-left" style="width:57%">
								<textarea class="textareaStyle height190" v-model="rowData.tmpltContent" disabled></textarea>
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:15%"><h4>버튼</h4></div>
							<div class="float-left" style="width:57%">
								<table class="table_skin1 mt0" style="width:100%" v-if="buttonAlimTalkFlag">
									<colgroup>
										<col style="width:22%">
										<col style="width:25%">
										<col style="width:53%">
									</colgroup>
									<thead>
										<tr>
											<th class="text-center">타입</th>
											<th class="text-center">버튼이름</th>
											<th class="text-center">버튼링크</th>
										</tr>
									</thead>
									<tbody>
										<template v-for="(buttonInfo, idx) in rowData.buttonList">
											<tr :key="idx">
												<td class="text-left" :rowspan="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL' ? '2' : '1'">
													<input type="text" class="inputStyle float-left" :value="fnGetButtonNm(buttonInfo.linkType)" disabled>
												</td>
												<td class="text-center" :rowspan="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL' ? '2' : '1'">
													<input v-if="buttonInfo.linkType == 'AC'" type="text" class="inputStyle float-left" disabled v-model="buttonInfo.name">
													<input v-else type="text" class="inputStyle float-left" disabled v-model="buttonInfo.name">
												</td>
												<td :class="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL' ? 'text-left' : 'text-left of_h'">
													<h6 v-if="buttonInfo.linkType == 'DS'" class="float-left" v-html="buttonDSDescription"></h6>
													<h6 v-if="buttonInfo.linkType == 'WL'" class="float-left" style="width:25%">Mobile*</h6>
													<h6 v-if="buttonInfo.linkType == 'AL'" class="float-left" style="width:25%">Android*</h6>
													<input v-if="buttonInfo.linkType == 'WL'" type="text" class="inputStyle float-left" style="width:75%" v-model="buttonInfo.linkMo" disabled>
													<input v-if="buttonInfo.linkType == 'AL'" type="text" class="inputStyle float-left" style="width:75%" v-model="buttonInfo.linkAnd" disabled>
												</td>
											</tr>
											<tr v-if="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL'" :key="idx+'_sub'">
												<td class="text-left">
													<div v-if="buttonInfo.linkType == 'WL'">
														<h6 class="float-left" style="width:25%">PC*</h6>
														<input type="text" class="inputStyle float-left" style="width:75%" v-model="buttonInfo.linkPc" disabled>
													</div>
													<div v-if="buttonInfo.linkType == 'AL'">
														<h6 class="float-left" style="width:25%">IOS*</h6>
														<input type="text" class="inputStyle float-left" style="width:75%" v-model="buttonInfo.linkIos" disabled>
													</div>
												</td>
											</tr>
										</template>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div v-else>
						<span class="txtCaption vertical-middle colorRed">알림톡은 기 등록 된, 템플릿 정보를 선택하여서 사용 할 수 있습니다.</span>
					</div>
				</div>
			</div>	
		</div>

		<!-- SMS/MMS -->
		<div class="of_h mt20" v-show="channelTab === 3" >
			<div class="float-left" style="width:28%">
				<!-- phoneWrap -->
				<div class="phoneWrap">
					<img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
					<div class="phoneTextWrap scroll-y">
						<div v-if="smsTemplateTable === 1" class="phoneText2 mb10">
							<p v-if="$gfnCommonUtils.isEmpty(rowData.smsTitle)">템플릿 제목</p>
							<p v-else>{{rowData.smsTitle}}</p>
						</div>
						<div v-if="smsTemplateTable === 1">
							<div v-for="(imgInfo, idx) in rowData.smsImgInfoList" :key="idx" class="phoneText2 mt10 text-center" :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+imgInfo.imgUrl+');'">
							</div>
						</div>
						<div class="phoneText1">
							<p v-if="$gfnCommonUtils.isEmpty(rowData.smsContent) && (rowData.msgKind != 'A' || $gfnCommonUtils.isEmpty(rowData.smsRcvblcNumber))" class="font-size14 color4 mt10">템플릿 내용</p>
							<p v-else class="font-size14 color4 mt10">
								<span><pre>{{rowData.smsContent}}</pre></span>
								<br v-if="!$gfnCommonUtils.isEmpty(rowData.smsContent)"/>
								<span v-if="rowData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(rowData.smsRcvblcNumber)">{{rowData.smsRcvblcNumber}}</span>
							</p>
						</div>
					</div>
				</div>
				<!-- //phoneWrap -->
			</div>
			<div class="float-left consoleCon" style="width:72%">
				<div class="of_h">
					<div class="float-left" style="width:13%"><h4>발송 유형 *</h4></div>
					<div class="float-left" style="width:57%">
						<input type="radio" name="smsSendType" value="S" id="smsSendType1"  v-on:click="checkSmsSend('S')" v-model="rowData.smsSendType"> <label for="smsSendType1" class="mr30">SMS</label>
						<input type="radio" name="smsSendType" value="M" id="smsSendType2"  v-on:click="checkSmsSend('M')" v-model="rowData.smsSendType"> <label for="smsSendType2">MMS</label>
					</div>
				</div>
				<div v-if="smsTemplateTable === 0">
					<div class="of_h" >
						<div class="float-left" style="width:13%"><h4>내용*</h4></div>
						<div class="float-left" style="width:57%">
							<textarea class="textareaStyle height190" :placeholder="smsmmsPlaceHoder" v-model="rowData.smsContent" id="smsContent" @keyup="fnByteLength('내용', '#smsContent', '#smsTextLength', '90')"  key="smsContent0"></textarea>
							<strong class="letter" id="smsTextLength" key="smsTextLength0">00 / 90 Byte</strong>
						</div>
					</div>
					<div class="of_h" v-if="rowData.msgKind == 'A'">
						<div class="float-left" style="width:13%"><h4>광고성메시지<br/>수신거부번호</h4></div>
						<div class="float-left mt10" style="width:57%">
							<input type="text" class="inputStyle" v-model="rowData.smsRcvblcNumber" maxlength="10">
						</div>
					</div>
				</div>
				<div v-else-if="smsTemplateTable === 1">
					<div class="of_h" >
						<div class="float-left" style="width:13%"><h4>제목</h4></div>
						<div class="float-left" style="width:57%">
							<input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.smsTitle" id="smsTitle" @keyup="fnByteLength('제목', '#smsTitle', '#smsTitleLength', '40')">
							<strong class="letter" id="smsTitleLength" key="smsTitleLength1">00 / 40 Byte</strong>
						</div>
					</div>
					<div class="of_h" >
						<div class="float-left" style="width:13%"><h4>내용*</h4></div>
						<div class="float-left" style="width:57%">
							<textarea class="textareaStyle height190" :placeholder="smsmmsPlaceHoder" v-model="rowData.smsContent" id="smsContent" @keyup="fnByteLength('내용', '#smsContent', '#smsTextLength', '2000')" key="smsContent1"></textarea>
							<strong class="letter" id="smsTextLength" key="smsTextLength1">00 / 2000 Byte</strong>
						</div>
					</div>
					<div class="of_h" v-if="rowData.msgKind == 'A'">
						<div class="float-left" style="width:13%"><h4>광고성메시지<br/>수신거부번호</h4></div>
						<div class="float-left mt10" style="width:57%">
							<input type="text" class="inputStyle" v-model="rowData.smsRcvblcNumber" maxlength="10">
						</div>
					</div>
					<div class="of_h consolMarginTop"  v-if="rowData.msgType == 'IMAGE'">
						<div class="float-left" style="width:13%"><h4>이미지</h4></div>
						<div class="float-left" style="width:57%">
							<div class="float-left" style="width:25%">
								<a @click="fnSmsOpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
							</div>
							<ul v-for="imgIdx in smsImgLimitSize" :key="imgIdx" class="float-right attachList" style="width:75%; padding:5px 15px; height:30px;">
								<li v-if="rowData.smsImgInfoList.length > imgIdx -1">
									<a @click="fnSmsDelImg(imgIdx-1)">{{fnSubString(rowData.smsImgInfoList[imgIdx-1].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a>
								</li>
								<li v-else>
									<a></a>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="of_h consolMarginTop">
					<div class="float-left" style="width:13%"><h4>발신번호 *</h4></div>
					<div class="float-left" style="width:57%">
						<select v-model="rowData.callback" class="selectStyle2 float-right" style="width:100%">
							<option v-for="info in smsCallbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
						</select>
					</div>
				</div>
			</div>
		</div>
		<hr>
		<div class="float-right">						
			<a @click="save()" class="btnStyle2 borderGray ml10" data-toggle="modal" title="저장" activity="SAVE">저장</a>
			<a @click="complete()" class="btnStyle2 backBlack ml10" data-toggle="modal" title="등록" activity="SAVE">등록</a>
			<router-link :to="{ name: 'multiSendTemplateList' }" tag="a" class="btnStyle2 backRed ml10">취소</router-link>
		</div>

		<RcsTemplatePopup :templateRadioBtn.sync="templateRadioBtn" ref="rcsTemplatePop" @fnResult="fnSetRcsTemplate"></RcsTemplatePopup>
		<AlimTalkTemplatePopup :alimTalkTemplateOpen.sync="alimTalkTemplateOpen" ref="alimTalkTmplPopup"></AlimTalkTemplatePopup>
	</div>
</template>

<script>
import templateApi from "@/modules/template/service/templateApi.js";
import messageApi from "@/modules/message/service/messageApi.js";
import RcsTemplatePopup from "@/modules/rcsTemplateSend/components/bp-rcsTemplatePop.vue";
import AlimTalkTemplatePopup from "@/modules/message/components/bp-alimTalkTemplate.vue";

import ImageManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import ImageUploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";

import Image90ManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import Image90UploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";
import Image91ManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import Image91UploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";
import Image92ManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import Image92UploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";
import Image93ManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import Image93UploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";
import Image94ManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import Image94UploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";
import Image95ManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import Image95UploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";

import Image100ManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import Image100UploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";
import Image101ManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import Image101UploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";
import Image102ManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import Image102UploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";
import Image103ManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import Image103UploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";
import Image104ManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import Image104UploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";
import Image105ManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import Image105UploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";

import ImageShortManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import ImageShortUploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";
import ImageTallManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import ImageTallUploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";

import ImageFriendTalkManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import ImageFriendTalkUploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";

import ImageSmsManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import ImageSmsUploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";

import TokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import Calendar from "@/components/Calendar.vue";

export default {
	name: 'multiSendTemplateManage',
	components : {
		RcsTemplatePopup,
		AlimTalkTemplatePopup,

		ImageManagePopUp,      ImageUploadPopUp,   /* Push */

		Image90ManagePopUp,    Image90UploadPopUp, /* RCS 캐러셀 SHORT 카드1 */
		Image91ManagePopUp,    Image91UploadPopUp, /* RCS 캐러셀 SHORT 카드2 */
		Image92ManagePopUp,    Image92UploadPopUp, /* RCS 캐러셀 SHORT 카드3 */
		Image93ManagePopUp,    Image93UploadPopUp, /* RCS 캐러셀 SHORT 카드4 */
		Image94ManagePopUp,    Image94UploadPopUp, /* RCS 캐러셀 SHORT 카드5 */
		Image95ManagePopUp,    Image95UploadPopUp, /* RCS 캐러셀 SHORT 카드6 */

		Image100ManagePopUp,   Image100UploadPopUp, /* RCS 캐러셀 TALL 카드1 */
		Image101ManagePopUp,   Image101UploadPopUp, /* RCS 캐러셀 TALL 카드2 */
		Image102ManagePopUp,   Image102UploadPopUp, /* RCS 캐러셀 TALL 카드3 */
		Image103ManagePopUp,   Image103UploadPopUp, /* RCS 캐러셀 TALL 카드4 */
		Image104ManagePopUp,   Image104UploadPopUp, /* RCS 캐러셀 TALL 카드5 */
		Image105ManagePopUp,   Image105UploadPopUp, /* RCS 캐러셀 TALL 카드6 */

		ImageShortManagePopUp, ImageShortUploadPopUp, /* RCS 세로형 SHORT */
		ImageTallManagePopUp,  ImageTallUploadPopUp,  /* RCS 세로형 Tall */
		ImageFriendTalkManagePopUp,  ImageFriendTalkUploadPopUp,  /* kakao friend talk */
		ImageSmsManagePopUp, ImageSmsUploadPopUp,  /* sms/mms mms */

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
					'checkedChannel':[],
					'multiSendTmpltCode':'',
					'msgType':'BASE', //BASE: 텍스트, IMAGE:이미지
					'msgKind':'I', //정보성 I, 광고용: A
					'otherProjectUseYn':'Y', //Y:공용, N:전용
					'tmpltType':'M',  //tmpltType:통합발송 M, 스마트발송:S
					'tmpltTitle':'', //템플릿 명
					'pushHowToDenyReceipt':'',
					'adtnInfo':'',
					'pushAppId': '',
					'brandNm': '', //브랜드

					'rcsSMSsHowToDenyReceipt':'',
					'rcsLMSHowToDenyReceipt':'',
					'rcsShortHowToDenyReceipt':'',
					'rcsTallHowToDenyReceipt':'',
					'rcs9HowToDenyReceipt':'',
					'rcs10HowToDenyReceipt':'',

					'pushImgInfo':{}, //Push이미지정보  //하나의 이미지만 들어감..그래서 {}

					'rcsShortImgInfoList':[], //RCS 세로형 SHORT 이미지정보
					'rcsTallImgInfoList':[], //RCS 세로형 TALL 이미지정보

					'rcs90ImgInfoList':[], //RCS 캐러셀 카드1 이미지정보
					'rcs91ImgInfoList':[], //RCS 캐러셀 카드2 이미지정보
					'rcs92ImgInfoList':[], //RCS 캐러셀 카드3 이미지정보
					'rcs93ImgInfoList':[], //RCS 캐러셀 카드4 이미지정보
					'rcs94ImgInfoList':[], //RCS 캐러셀 카드5 이미지정보
					'rcs95ImgInfoList':[], //RCS 캐러셀 카드6 이미지정보

					'rcs100ImgInfoList':[], //RCS 캐러셀 카드1 이미지정보
					'rcs101ImgInfoList':[], //RCS 캐러셀 카드2 이미지정보
					'rcs102ImgInfoList':[], //RCS 캐러셀 카드3 이미지정보
					'rcs103ImgInfoList':[], //RCS 캐러셀 카드4 이미지정보
					'rcs104ImgInfoList':[], //RCS 캐러셀 카드5 이미지정보
					'rcs105ImgInfoList':[], //RCS 캐러셀 카드6 이미지정보

					'rcsDesButtons':[], // RCS 서술형 버튼리스트
					'rcs90Buttons':[], //button 은 초기에 숨겨져있고, 입력부분없이 제목만 출력
					'rcs91Buttons':[],
					'rcs92Buttons':[],
					'rcs93Buttons':[],
					'rcs94Buttons':[],
					'rcs95Buttons':[],

					'rcs100Buttons':[], //button 은 초기에 숨겨져있고, 입력부분없이 제목만 출력
					'rcs101Buttons':[],
					'rcs102Buttons':[],
					'rcs103Buttons':[],
					'rcs104Buttons':[],
					'rcs105Buttons':[],

					'rcsSMSButtons':[],
					'rcsLMSButtons':[],
					'rcsShortButtons':[], //button 은 초기에 숨겨져있고, 입력부분없이 제목만 출력
					'rcsTallButtons':[],

					'friendTalkButtons':[], //kakao friend talk 버튼정보
					'friendTalkButtonsSize': 5, //kakao friend talk 버튼 최대 갯수
					'friendTalkImgInfo':{}, //kakao friend talk 이미지정보

					'buttonList': [], //kakao alim talk 버튼정보
					'tmpltKey': '', //kakao template Key

					'callback':'',
					'smsImgInfoList':[], //sms/mms이미지정보

					'smsSendType': 'S'  //sms/mms 발송유형
					//, 'buttonType': ''  //RCS 버튼 타입 (U: url입력, C:복사값입력, T:전화번호입력, S:날짜입력)
				}
			}
		}
	},
	data() {
		return {
			channelTab: 9,						//채널설정 Push/RCS/카카오톡/SMSMMS 탭 show/hide
			rcsTemplateTable: 0,				//채널설정 RCS탭 내 템플릿 구분 //저장등록시 유효성 체크 대상으로 사용
			rcsTemplateTableChecked: 0,	//채널설정 RCS탭 내 템플릿 구분 //v-model이용 radio 선택표시용으로 사용 //rcsTemplateTable로 사용하려 했으나 작동하지 않음
			kakaoTemplateTable: 0,			//채널설정 kakao내 템플릿 구분
			smsTemplateTable: 0,				//채널설정 SMS/MMS내 구분
			cShortTab:0,							//캐러셀 CSHORT 탭변경에 따른 미리보기 변경용
			cTallTab:0,								//캐러셀 CTALL 탭변경에 따른 미리보기 변경용
			checkedPush: false,				//채널선택에서 push 선택여부
			checkedRCS: false,					//채널선택에서 RCS 선택여부
			checkedKakao: false,				//채널선택에서 kakaotalk 선택여부
			checkedSmsMms: false,			//채널선택에서 sms/mms 선택여부
			aplnIdList: {},						//app_id selectbox설정용
			kakaoChKeyList: {},				//카카오채널키 selectbox설정용
			indexData:0,
			itemDatas:[],
			rcs0CallbackList: [],				//RCS 프리템플릿 발신번호 리스트
			rcs1CallbackList: [],				//RCS 서술 발신번호 리스트
			rcs2CallbackList: [],				//RCS 스타일 발신번호 리스트
			rcsSMSCallbackList: [],			//RCS sms 발신번호 리스트
			rcsLMSCallbackList: [],			//RCS lms 발신번호 리스트
			rcsShortCallbackList: [],			//RCS SHORT 발신번호 리스트
			rcsTallCallbackList: [],				//RCS TALL 발신번호 리스트
			rcs9CallbackList: [],				//RCS 캐러셀 Short 카드 발신번호 리스트
			rcs10CallbackList: [],				//RCS 캐러셀 Tall 카드 발신번호 리스트

			smsCallbackList: [],				//smslms 발신번호 리스트

			rcsDesMessagebaseId: '',		//서술형 MessagebaseId
			rcsDesMessagebaseformId: '',	//서술형 유형
			rcsDesFormNm: '',	//서술형 유형명
			rcsDesFormNmList: [],			//RCS 서술형 유형 selectBox
			rcsStyleFormNm: '',				// 스타일형 유형
			rcsStyleFormNmList: [],			// 스타일형 유형 selectBox
			rcsImgsrc : require("@/assets/images/common/approve.png"),			// 이미지

			detailTitle:'통합발송 템플릿',

			pushImgMngOpen : false,		/* Push 이미지 */
			pushImgUploadOpen : false,
			pushUseCh : 'PUSH',
			pushImgLimitSize : 1,

			rcs90ImgMngOpen : false,		/* RCS 캐러셀 Short 카드1 이미지 */
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
			rcs9CardCount: 3,					// rcs탭 캐러셀 Short 카드탭 갯수  //카드탭은 갯수만큼 service단에서 배열로 저장되어야 한다.

			button90Flag: false,				//버튼 추가 여부
			button91Flag: false,
			button92Flag: false,
			button93Flag: false,
			button94Flag: false,
			button95Flag: false,

			rcs100ImgMngOpen : false,	/* RCS 캐러셀 TALL 카드1 이미지 */
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
			rcs10CardCount: 3,				// RCS탭 캐러셀 TALL 카드탭 갯수  //카드탭은 갯수만큼 service단에서 배열로 저장되어야 한다.

			button100Flag: false,				//버튼 추가 여부
			button101Flag: false,
			button102Flag: false,
			button103Flag: false,
			button104Flag: false,
			button105Flag: false,

			rcsShortImgMngOpen : false,	/* RCS 세로형 SHORT 이미지 */
			rcsShortImgUploadOpen : false,
			rcsShortUseCh : 'RCS',
			rcsShortImgLimitSize : 1,

			rcsTallImgMngOpen : false,		/* RCS 세로형 TALL 이미지 */
			rcsTallImgUploadOpen : false,
			rcsTallUseCh : 'RCS',
			rcsTallImgLimitSize : 1,

			friendTalkImgMngOpen : false,	/* MMS 이미지 */
			friendTalkImgUploadOpen : false,
			friendTalkUseCh : 'FRIENDTALK',
			friendTalkImgLimitSize : 1,

			friendTalkSenderKeyType: 'NOMAL',	//NOMAL, GROUP //friendTalk 발신프로필 그룹
			friendTalkSenderKeyList: [],

			rcsButtonTypeList : [
				{type:'urlAction', name:'URL 링크'},
				{type:'clipboardAction', name:'복사하기'},
				{type:'dialerAction', name:'전화걸기'},
				{type:'calendarAction', name:'일정추가'},
				{type:'mapAction', name:'지도맵'}
			],
			friendTalkButtonTypeList : [
				{type:'WL', name:'웹 링크'},
				{type:'AL', name:'앱 링크'},
				{type:'BK', name:'봇 키워드'},
				{type:'MD', name:'메시지전달'}
			],
			alimTalkButtonTypeList : [
				{linkType:'DS', name:'배송 조회'},
				{linkType:'WL', name:'웹 링크'},
				{linkType:'AL', name:'앱 링크'},
				{linkType:'BK', name:'봇 키워드'},
				{linkType:'MD', name:'메시지 전달'},
				{linkType:'BC', name:'상담톡 전환'},
				{linkType:'BT', name:'봇 전환'},
				//{type:'AC', name:'채널 추가'},  //광고 추가/복합형만
			],

			templateRadioBtn: 'des',					// 템플릿형 라디오 버튼
			alimTalkTemplateOpen: false,
			selectedRcsDesTemplate: false,		// RCS 템플릿(승인형) 선택 전
			selectedAlimTalkTemplate: false,		// 알림톡 템플릿 선택 전

			smsImgMngOpen : false, /* MMS 이미지 */
			smsImgUploadOpen : false,
			smsUseCh : 'MMS',
			smsImgLimitSize : 2, /* 2개까지 가능 */

			buttonSMSFlag: false,
			buttonLMSFlag: false,
			buttonShortFlag: false,
			buttonTallFlag: false,
			buttonFriendTalkFlag: false,
			buttonAlimTalkFlag: false,

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

			useYn : 'Y',
			brandNmList: [],		// 브랜드 명 selectBox

			pushPlaceHoder: 	"변수로 설정하고자 하는 내용을 {{ }}표시로 작성해 주십시오.\n예) 이름과 출금일을 변수 설정: 예) {{고객}}님 {{YYMMDD}} 출금 예정입니다.",
			rcsPlaceHoder: 	"변수로 설정하고자 하는 내용을 {{ }}표시로 작성해 주십시오.\n예) 이름과 출금일을 변수 설정: 예) {{고객}}님 {{YYMMDD}} 출금 예정입니다.",
			kakaoPlaceHoder: 	"변수로 설정하고자 하는 내용을 {{ }}표시로 작성해 주십시오.\n예) 이름과 출금일을 변수 설정: 예) {{고객}}님 {{YYMMDD}} 출금 예정입니다.",
			smsmmsPlaceHoder: "변수로 설정하고자 하는 내용을 {{ }}표시로 작성해 주십시오.\n예) 이름과 출금일을 변수 설정: 예) {{고객}}님 {{YYMMDD}} 출금 예정입니다.",

			buttonDSDescription : '카카오 메세지에 택배사 명과 송장번호를 기재한 후, 배송 조회 버튼을 추가하시면 메세지에서 택배사 명과 송장번호를 추출하여 배송 조회 카카오 검색페이지 링크가 자동으로 생성됩니다. 카카오에서 지원하는 택배사명과 운송장번호가 알림톡 메시지 내에 포함된 경우에만 배송조회 버튼이 표시됩니다. 배송 조회가 가능한 택배사는 <span style="color:#e11d21"><strong>카카오와 해당 택배사와의 계약 관계에 의해 변동될 수 있음을 유의해주시기 바랍니다.</strong></span>',
		}
	},
	watch: {
		// RCS 템플릿 종류 선택에 따라 v-if를 이용하여 active되었을 경우에만 값을 가져온다.
		rcsTemplateTable(val){
			if (val === 0) {//rcsTemplateTable === 0 프리템플릿
				this.fnRcs0SelectCallbackList();
				this.fnSelectBrandList();
			} else if(val === 1) {//rcsTemplateTable === 1
				this.fnRcs1SelectCallbackList();
			} else if(val === 2) {//rcsTemplateTable === 2
				this.fnRcs2SelectCallbackList();
			} else if(val === 3) {//rcsTemplateTable === 3
				this.fnRcsSMSSelectCallbackList();
			} else if(val === 4) {//rcsTemplateTable === 4
				this.fnRcsLMSSelectCallbackList();
			} else if(val === 5) {//rcsTemplateTable === 5 SHORT
				this.fnRcsShortSelectCallbackList();
			} else if(val === 6) {//rcsTemplateTable === 6 TALL
				this.fnRcsTallSelectCallbackList();
			} else if(val === 9) {//rcsTemplateTable === 9 캐러셀 SHORT
				this.fnRcs9SelectCallbackList();
			} else if(val === 10) {//rcsTemplateTable === 10 캐러셀 TALL
				this.fnRcs10SelectCallbackList();
			}
		}
	},
	mounted() {
		this.init();
		this.fnPushGetAppId();// v-show 사용으로 항상 살아있어서 mounted에 놓음
		this.fnSelectFriendTalkSenderKeyList();
		this.fnRcs0SelectCallbackList();
		this.fnSMSSelectCallbackList();
		this.fnSelectBrandList();
		this.fnSetMultiSendTemplateInfo();
	},
	methods: {
		init() {
			var params = {};
			var vm = this;

			templateApi.rcsTemplateInit(params).then(response => {
				var result = response.data;
				var resultData = result.data;

				vm.rcsDesMessagebaseformId = resultData.desFormList[0].MESSAGEBASEFORM_ID;
				vm.rcsDesFormNmList = resultData.desFormList;
				vm.rcsStyleFormNm = resultData.styleFormList[0].MESSAGEBASEFORM_ID;
				vm.rcsStyleFormNmList = resultData.styleFormList;
			});
		},
		//채널선택에서 체크박스를 클릭하면 채널설정탭을 설정한다.
		toggleOnOffPush:function(){
			if (this.checkedPush == true) {
				this.checkedPush = false;

				for (let i = 0; i < this.rowData.checkedChannel.length; i++) {
					if (this.rowData.checkedChannel[i] === 'PUSH') {
						this.rowData.checkedChannel.splice(i, 1);
						i--;
					}
				}
				if (this.rowData.checkedChannel.length === 0) {
					this.channelTab = 9;
				} else {
					if (this.rowData.checkedChannel[0] === 'RCS') {
						this.channelTab = 1;
					} else if (this.rowData.checkedChannel[0] === 'KAKAO') {
						this.channelTab = 2;
					} else if (this.rowData.checkedChannel[0] === 'SMSMMS') {
						this.channelTab = 3;
					}
				}
			} else {
				this.checkedPush = true;
				this.channelTab = 0;
			}
		},
		toggleOnOffRCS:function(){
			if (this.checkedRCS == true) {
				this.checkedRCS = false;

				for (let i = 0; i < this.rowData.checkedChannel.length; i++) {
					if (this.rowData.checkedChannel[i] === 'RCS') {
						this.rowData.checkedChannel.splice(i, 1);
						i--;
					}
				}

				if (this.rowData.checkedChannel.length === 0) {
					this.channelTab = 9;
				} else {
					if (this.rowData.checkedChannel[0] === 'PUSH') {
						this.channelTab = 0;
					} else if (this.rowData.checkedChannel[0] === 'KAKAO') {
						this.channelTab = 2;
					} else if (this.rowData.checkedChannel[0] === 'SMSMMS') {
						this.channelTab = 3;
					}
				}
			} else {
				this.checkedRCS = true;
				this.channelTab = 1;
			}
		},
		toggleOnOffKakao:function(){
			if (this.checkedKakao == true) {
				this.checkedKakao = false;

				for (let i = 0; i < this.rowData.checkedChannel.length; i++) {
					if (this.rowData.checkedChannel[i] === 'KAKAO') {
						this.rowData.checkedChannel.splice(i, 1);
						i--;
					}
				}

				if (this.rowData.checkedChannel.length === 0) {
					this.channelTab = 9;
				} else {
					if (this.rowData.checkedChannel[0] === 'PUSH') {
						this.channelTab = 0;
					} else if (this.rowData.checkedChannel[0] === 'RCS') {
						this.channelTab = 1;
					} else if (this.rowData.checkedChannel[0] === 'SMSMMS') {
						this.channelTab = 3;
					}
				}
			} else {
				this.checkedKakao = true;
				this.channelTab = 2;
			}
		},
		toggleOnOffSmsMms:function(){
			if (this.checkedSmsMms == true) {
				this.checkedSmsMms = false;

				for (let i = 0; i < this.rowData.checkedChannel.length; i++) {
					if (this.rowData.checkedChannel[i] === 'SMSMMS') {
						this.rowData.checkedChannel.splice(i, 1);
						i--;
					}
				}
	
				if (this.rowData.checkedChannel.length === 0) {
					this.channelTab = 9;
				} else {
					if (this.rowData.checkedChannel[0] === 'PUSH') {
						this.channelTab = 0;
					} else if (this.rowData.checkedChannel[0] === 'RCS') {
						this.channelTab = 1;
					} else if (this.rowData.checkedChannel[0] === 'KAKAO') {
						this.channelTab = 2;
					}
				}
			} else {
				this.checkedSmsMms = true;
				this.channelTab = 3;
			}
		},
		//빈값확인
		fnIsEmpty(str) {
			if(str) return false;
			else return true
		},
		fnChgBtnType(idx){
			const vm = this;
			Object.keys(this.rowData.friendTalkButtons[idx]).forEach(function(key){
				if (key != 'linkType') {
					delete vm.rowData.friendTalkButtons[idx][key];
				}
			});
		},
		fnPushGetAppId(){
			var params = {};
			messageApi.selectAppIdList(params).then(response => {
				var result = response.data;
				if (result.success) {
					this.aplnIdList = result.data;
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
		fnSelectFriendTalkSenderKeyList(){
			const params = {kkoSvc: 'FRIENDTALK', senderKeyType: this.friendTalkSenderKeyType};
			templateApi.selectSenderKeyList(params).then(response => {
				const result = response.data;
				if (result.success) {
					this.rowData.friendTalkSenderKey = '';
					this.friendTalkSenderKeyList = Object.assign({}, result.data);
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
		// RCS 템플릿 불러오기 팝업
		fnOpenRcsTemplatePopup() {
			this.$refs.rcsTemplatePop.fnInit();
		},
		// 알림톡 템플릿 불러오기 팝업
		fnOpenAlimTalkTemplatePopup(){
			this.$refs.alimTalkTmplPopup.fnSelectSenderKeyList();
			this.alimTalkTemplateOpen = true;
		},
		// 알림톡 버튼명 가져오기
		fnGetButtonNm(type){
			let buttonNm = '';
			this.alimTalkButtonTypeList.forEach(function(buttonInfo){
				if (buttonInfo.linkType == type) {
					buttonNm = buttonInfo.name;
					return false;
				}
			});
			return buttonNm;
		},
		//rcs 프로템플릿 발신번호 리스트 조회
		fnRcs0SelectCallbackList(){
			var params = {};
			messageApi.selectCallbackList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.rcs0CallbackList = result.data;
				} else {
					confirm.fnAlert("rcs 프로템플릿 발신번호 리스트 조회", result.message);
				}
			});
		},
		fnRcs1SelectCallbackList(){
			var params = {};
			messageApi.selectCallbackList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.rcs1CallbackList = result.data;
				} else {
					confirm.fnAlert("RCS 템플릿 승인 서술 발신번호 리스트 조회", result.message);
				}
			});
		},
		fnRcs2SelectCallbackList(){
			var params = {};
			messageApi.selectCallbackList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.RCS2CallbackList = result.data;
				} else {
					confirm.fnAlert("RCS 템플릿 승인 스타일 발신번호 리스트 조회", result.message);
				}
			});
		},
		fnRcsSMSSelectCallbackList(){
			var params = {};
			messageApi.selectCallbackList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.rcsSMSCallbackList = result.data;
				} else {
					confirm.fnAlert("RCS SMS 발신번호 리스트 조회", result.message);
				}
			});
		},
		fnRcsLMSSelectCallbackList(){
			var params = {};
			messageApi.selectCallbackList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.rcsLMSCallbackList = result.data;
				} else {
					confirm.fnAlert("RCS LMS 발신번호 리스트 조회", result.message);
				}
			});
		},
		fnRcsShortSelectCallbackList(){
			var params = {};
			messageApi.selectCallbackList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.rcsShortCallbackList = result.data;
				} else {
					confirm.fnAlert("RCS 세로형 SHORT 발신번호 리스트 조회", result.message);
				}
			});
		},
		fnRcsTallSelectCallbackList(){
			var params = {};
			messageApi.selectCallbackList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.rcsTallCallbackList = result.data;
				} else {
					confirm.fnAlert("RCS 세로형 TALL 발신번호 리스트 조회", result.message);
				}
			});
		},
		fnRcs9SelectCallbackList(){
			var params = {};
			messageApi.selectCallbackList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.rcs9CallbackList = result.data;
				} else {
					confirm.fnAlert("RCS 캐러셀 TALL 발신번호 리스트 조회", result.message);
				}
			});
		},
		fnRcs10SelectCallbackList(){
			var params = {};
			messageApi.selectCallbackList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.rcs10CallbackList = result.data;
				} else {
					confirm.fnAlert("RCS 캐러셀 TALL 발신번호 리스트 조회", result.message);
				}
			});
		},
		fnSMSSelectCallbackList(){
			var params = {};
			messageApi.selectCallbackList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.smsCallbackList = result.data;
				} else {
					confirm.fnAlert("SMSMMS 발신번호 리스트 조회", result.message);
				}
			});
		},
		fnSelectBrandList(){
			var params = {};
			templateApi.selectBrandList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.brandNmList = result.data;
				} else {
					confirm.fnAlert("브랜드 리스트 조회", result.message);
				}
			});
		},
    // showRcsTemplateTable(num){
    //   if(num == 0){

    //   }else if(num == 1){

    //   }else if(num == 2){

    //   }else if(num == 3){

    //   }else if(num == 4){

    //   }else if(num == 5){

    //   }else if(num == 6){

    //   }else if(num == 7){

    //   }else if(num == 8){

    //   }else if(num == 9){

    //   }else if(num == 10){

    //   }
    // },
		// 채널 발송순서 변경시 클릭한 채널 인덱스를 가져온다
		putData:function(idx){
			this.idxData = idx;
		},
		// 채널 발송순서를 위로
		moveup:function () {
			if (this.idxData > 0) {
				this.rowData.checkedChannel.splice (this.idxData-1,0, (this.rowData.checkedChannel [this.idxData]));
				//Delete the next item
				this.rowData.checkedChannel.splice (this.idxData + 1,1);
				this.idxData = this.idxData -1;
			}
		},
		// 채널 발송순서를 아래로
		movedown:function () {
			if (this.idxData < this.rowData.checkedChannel.length-1) {
				//Insert the item in the next item
				this.rowData.checkedChannel.splice (this.idxData + 2,0, (this.rowData.checkedChannel [this.idxData]));
				this.rowData.checkedChannel.splice (this.idxData, 1);
				this.idxData = this.idxData +1;
			}
		},
		async fnSaveMultiSendTemplate(){
			var params = this.rowData;

			//유효성 검사
			params.tmpltStatus = 'SAVE';
			params.rcsTemplateTable = this.rcsTemplateTable;//rcs일경우 사용한 템플릿 번호를 가져간다.
			params.rcs9CardCount = this.rcs9CardCount;//캐러셀 SHORT 카드 수
			params.rcs10CardCount = this.rcs10CardCount;//캐러셀 TALL 카드 수
			params.kakaoTemplateTable = this.kakaoTemplateTable;//kakao일 경우 friendTalk, alimTalk 구분값을 가져간다
			params.useYn = this.useYn; //사용여부
			await templateApi.insertMultiSendTemplate(params).then(response =>{
				var result = response.data;
				if (result.success) {
					confirm.fnAlert(this.detailTitle, '저장 되었습니다.');
					this.$router.push('multiSendTemplateList')
				} else {
					confirm.fnAlert(this.detailTitle, result.message);
				}
			});
		},

		async fnCompleteIntegratedTemplate(){
			var params = this.rowData;

			// 유효성 검사
			//if(this.fnIsValid() == false) return;
			params.tmpltStatus = 'COMPLETE';//템플릿 상태값 저장:SAVE, 완료:COMPLETE
			//params.projectId = this.testProjectId;
			params.rcsTemplateTable = this.rcsTemplateTable;//rcs일경우 사용한 템플릿 번호를 가져간다.
			params.rcs9CardCount = this.rcs9CardCount;//캐러셀 SHORT 카드 수
			params.rcs10CardCount = this.rcs10CardCount;//캐러셀 TALL 카드 수
			params.kakaoTemplateTable = this.kakaoTemplateTable;//kakao일 경우 friendTalk, alimTalk 구분값을 가져간다
			params.useYn = this.useYn; //사용여부

			await templateApi.insertMultiSendTemplate(params).then(response =>{
				var result = response.data;
				if (result.success) {
					confirm.fnAlert(this.detailTitle, '등록 되었습니다.');
					this.$router.push('multiSendTemplateList');
				} else {
					confirm.fnAlert(this.detailTitle, result.message);
				}
			});
		},
		// 유효성 체크
		fnIsValid(){
			if (!this.rowData.tmpltTitle) {
				confirm.fnAlert(this.detailTitle, '템플릿명을 입력해주세요.');
				return false;
			}
			if (!this.rowData.msgType) {
				confirm.fnAlert(this.detailTitle, '메시지타입을 선택해주세요.');
				return false;
			}
			if (!this.rowData.msgKind) {
				confirm.fnAlert(this.detailTitle, '메시지구분을 선택해주세요.');
				return false;
			}
			if (!this.rowData.otherProjectUseYn) {
				confirm.fnAlert(this.detailTitle, '타 프로젝트 사용여부를 선택해주세요.');
				return false;
			}

			if (this.rowData.checkedChannel.length == 0) {
				confirm.fnAlert(this.detailTitle, '채널을 선택해주세요.');
				return false;
			} else {
				if (this.rowData.checkedChannel.includes('PUSH')) {
					if (!this.rowData.pushSend) {
						confirm.fnAlert(this.detailTitle, 'Push 발송타입을 선택해주세요.');
						return false;
					}
					if (!this.rowData.pushTitle) {
						confirm.fnAlert(this.detailTitle, 'Push 제목을 입력해주세요.');
						return false;
					}
					if (!this.rowData.pushContent) {
						confirm.fnAlert(this.detailTitle, 'Push 메시지를 입력해주세요.');
						return false;
					}
					if (this.rowData.msgKind == 'A' && !this.rowData.pushHowToDenyReceipt) {
						confirm.fnAlert(this.detailTitle, 'Push 수신거부 방법을 입력해주세요.');
						return false;
					}
					if (this.rowData.msgType == 'IMAGE' && !this.rowData.pushImgInfo.imgUrl) {
						confirm.fnAlert(this.detailTitle, 'Push 이미지를 선택해주세요.');
						return false;
					}
					if (this.rowData.msgType == 'IMAGE' && !this.rowData.pushImgInfo.fileId) {
						confirm.fnAlert(this.detailTitle, 'Push 이미지 정보가 잘못되었습니다. 다시 이미지를 선택해주세요.');
						return false;
					}
					if (!this.rowData.pushAppId) {
						confirm.fnAlert(this.detailTitle, 'Push APP ID를 선택해주세요.');
						return false;
					}
				}

				if (this.rowData.checkedChannel.includes('RCS')) {
					if (this.rcsTemplateTable === 0) {  //FREE
						if (!this.rowData.rcs0Content) { 
							confirm.fnAlert(this.detailTitle, 'RCS FREE 템플릿 내용을 입력해주세요.');
							return false;
						}
						if (!this.rowData.callback) { 
							confirm.fnAlert(this.detailTitle, 'RCS FREE 템플릿 발신번호를 선택해주세요.');
							return false;
						}
						if (this.rowData.msgKind == 'A') {
							if (!this.rowData.rcsBlockNumber) { 
								confirm.fnAlert(this.detailTitle, 'RCS FREE 템플릿 수신거부번호를 입력해주세요.');
								return false;
							}
						}
					}

					if (this.rcsTemplateTable === 1) {  //DESCRIPTION
						if (!this.selectedRcsDesTemplate) { 
							confirm.fnAlert(this.detailTitle, 'RCS 승인 서술형 템플릿을 팝업을 통해 선택해 주세요.');
							return false;
						}
						if (!this.rowData.callback) { 
							confirm.fnAlert(this.detailTitle, 'RCS 서술형 템플릿 발신번호를 선택해주세요.');
							return false;
						}
					}

					if (this.rcsTemplateTable === 2) {  //CELL
						if (!this.rowData.rcs2MessageFormId) { 
							confirm.fnAlert(this.detailTitle, 'RCS 승인 스타일 템플릿을 팝업을 통해 선택해 주세요.');
							return false;
						}
					}
	
					if (this.rcsTemplateTable === 3) {  //SMS
						if (!this.rowData.rcsSMSContent) { 
							confirm.fnAlert(this.detailTitle, 'RCS SMS 내용을 입력해주세요.');
							return false;
						}
						if (this.rowData.msgKind == 'A' && !this.rowData.rcsSMSHowToDenyReceipt) { 
							confirm.fnAlert(this.detailTitle, 'RCS SMS 무료수신거부 정보를 입력해주세요.');
							return false;
						}
					}

					if (this.rcsTemplateTable === 4) {  //LMS
						if (!this.rowData.rcsLMSTitle) { 
							confirm.fnAlert(this.detailTitle, 'RCS LMS 제목을 입력해주세요.');
							return false;
						}
						if (!this.rowData.rcsLMSContent) { 
							confirm.fnAlert(this.detailTitle, 'RCS LMS 내용을 입력해주세요.');
							return false;
						}
						if (this.rowData.msgKind == 'A' && !this.rowData.rcsLMSHowToDenyReceipt) { 
							confirm.fnAlert(this.detailTitle, 'RCS LMS 무료수신거부 정보를 입력해주세요.');
							return false;
						}
					}

					if (this.rcsTemplateTable === 5) {  //shart
						if (!this.rowData.rcsShortTitle) { 
							confirm.fnAlert(this.detailTitle, 'RCS SHORT 제목을 입력해주세요.');
							return false;
						}
						if (!this.rowData.rcsShortContent) { 
							confirm.fnAlert(this.detailTitle, 'RCS SHORT 내용을 입력해주세요.');
							return false;
						}
						if (this.rowData.msgKind == 'A' && !this.rowData.rcsShortHowToDenyReceipt) { 
							confirm.fnAlert(this.detailTitle, 'RCS SHORT 무료수신거부 정보를 입력해주세요.');
							return false;
						}
					}

					if (this.rcsTemplateTable === 6) {  //TALL
						if (!this.rowData.rcsTallTitle) { 
							confirm.fnAlert(this.detailTitle, 'RCS TALL 제목을 입력해주세요.');
							return false;
						}
						if (!this.rowData.rcsTallContent) { 
							confirm.fnAlert(this.detailTitle, 'RCS TALL 내용을 입력해주세요.');
							return false;
						}
						if (this.rowData.msgKind == 'A' && !this.rowData.rcsTallHowToDenyReceipt) { 
							confirm.fnAlert(this.detailTitle, 'RCS TALL 무료수신거부 정보를 입력해주세요.');
							return false;
						}
					}

					if (this.rcsTemplateTable === 9) {  //CSHORT
						if (this.rcs9CardCount >= 3) {
							if (!this.rowData.rcs90Title) { 
								confirm.fnAlert(this.detailTitle, 'RCS CSHORT 카드1의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcs90Content) { 
								confirm.fnAlert(this.detailTitle, 'RCS CSHORT 카드1의 내용을 입력해주세요.');
								return false;
							}
							if (!this.rowData.callback) { 
								confirm.fnAlert(this.detailTitle, 'RCS CSHORT 발신번호를 선택해주세요.');
								return false;
							}
							if (!this.rowData.rcs91Title) { 
								confirm.fnAlert(this.detailTitle, 'RCS CSHORT 카드2의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcs91Content) { 
								confirm.fnAlert(this.detailTitle, 'RCS CSHORT 카드2의 내용을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcs92Title) { 
								confirm.fnAlert(this.detailTitle, 'RCS CSHORT 카드3의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcs92Content) { 
								confirm.fnAlert(this.detailTitle, 'RCS CSHORT 카드3의 내용을 입력해주세요.');
								return false;
							}
						}

						if (this.rcs9CardCount >= 4) {
							if (!this.rowData.rcs93Title) { 
								confirm.fnAlert(this.detailTitle, 'RCS CSHORT 카드4의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcs93Content) { 
								confirm.fnAlert(this.detailTitle, 'RCS CSHORT 카드4의 내용을 입력해주세요.');
								return false;
							}
						}

						if (this.rcs9CardCount >= 5) {
							if (!this.rowData.rcs94Title) {
								confirm.fnAlert(this.detailTitle, 'RCS CSHORT 카드5의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcs94Content) { 
								confirm.fnAlert(this.detailTitle, 'RCS CSHORT 카드5의 내용을 입력해주세요.');
								return false;
							}
						}

						if (this.rcs9CardCount >= 6) {
							if (!this.rowData.rcs95Title) {
								confirm.fnAlert(this.detailTitle, 'RCS CSHORT 카드6의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcs95Content) { 
								confirm.fnAlert(this.detailTitle, 'RCS CSHORT 카드6의 내용을 입력해주세요.');
								return false;
							}
						}
					}

					if (this.rcsTemplateTable === 10) {  //CTALL
						if (this.rcs10CardCount >= 3) {
							if (!this.rowData.rcs100Title) { 
								confirm.fnAlert(this.detailTitle, 'RCS CTALL 카드1의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcs100Content) { 
								confirm.fnAlert(this.detailTitle, 'RCS CTALL 카드1의 내용을 입력해주세요.');
								return false;
							}
							if (!this.rowData.callback) { 
								confirm.fnAlert(this.detailTitle, 'RCS CTALL 발신번호를 선택해주세요.');
								return false;
							}
							if (!this.rowData.rcs101Title) { 
								confirm.fnAlert(this.detailTitle, 'RCS CTALL 카드2의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcs101Content) { 
								confirm.fnAlert(this.detailTitle, 'RCS CTALL 카드2의 내용을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcs102Title) { 
								confirm.fnAlert(this.detailTitle, 'RCS CTALL 카드3의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcs102Content) { 
								confirm.fnAlert(this.detailTitle, 'RCS CTALL 카드3의 내용을 입력해주세요.');
								return false;
							}
						}

						if (this.rcs10CardCount >= 4) {
							if (!this.rowData.rcs103Title) { 
								confirm.fnAlert(this.detailTitle, 'RCS CTALL 카드4의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcs103Content) { 
								confirm.fnAlert(this.detailTitle, 'RCS CTALL 카드4의 내용을 입력해주세요.');
								return false;
							}
						}

						if (this.rcs10CardCount >= 5) {
							if (!this.rowData.rcs104Title) { 
								confirm.fnAlert(this.detailTitle, 'RCS CTALL 카드5의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcs104Content) { 
								confirm.fnAlert(this.detailTitle, 'RCS CTALL 카드5의 내용을 입력해주세요.');
								return false;
							}
						}

						if (this.rcs10CardCount >= 6) {
							if (!this.rowData.rcs105Title) { 
								confirm.fnAlert(this.detailTitle, 'RCS CTALL 카드6의 제목을 입력해주세요.');
								return false;
							}
	
							if (!this.rowData.rcs105Content) { 
								confirm.fnAlert(this.detailTitle, 'RCS CTALL 카드6의 내용을 입력해주세요.');
								return false;
							}
						}
					}
				}

				if (this.rowData.checkedChannel.includes('KAKAO')) {
					if (this.kakaoTemplateTable === 0) { //FRIENDTALK
						if (!this.rowData.friendTalkSenderKey) {
							confirm.fnAlert(this.detailTitle, '친구톡 발신프로필을 선택해주세요.');
							return false;
						}
						if (!this.rowData.friendTalkContent) {
							confirm.fnAlert(this.detailTitle, '친구톡 내용을 입력해주세요.');
							return false;
						}
					}

					if (this.kakaoTemplateTable === 1) { //ALIMTALK
						if (!this.selectedAlimTalkTemplate) {
							confirm.fnAlert(this.detailTitle, '알림톡 템플릿을 선택해주세요.');
							return false;
						}
					}
				}

				if (this.rowData.checkedChannel.includes('SMSMMS')) {
					if (this.smsTemplateTable === 0) { //SMS
						if (!this.rowData.smsContent) {
							confirm.fnAlert(this.detailTitle, 'sms 내용을 입력해주세요.');
							return false;
						}
					}

					if (this.smsTemplateTable === 1) { //MMS
						if (!this.rowData.smsTitle) {
							confirm.fnAlert(this.detailTitle, 'MMS 제목을 입력해주세요.');
							return false;
						}
						if (!this.rowData.smsContent) {
							confirm.fnAlert(this.detailTitle, 'MMS 내용을 입력해주세요.');
							return false;
						}
					}
				}
			}
			return true;
		},
		// 임시저장 => 채널 설정관련 유효성 체크를  무시한다.
		save:function(){
			this.registYn = false;
			//유효성 검사
			if (this.fnIsValid() == false) return;
			eventBus.$on('callbackEventBus', this.fnSaveMultiSendTemplate);
			confirm.fnConfirm(this.detailTitle, "템플릿을 저장 하시겠습니까?", "확인");
		},
		// 등록 => 채널 설정관련 유효성 체크 필수
		complete:function(){
			this.registYn = true;
			//유효성 검사
			if (this.fnIsValid() == false) return;

			eventBus.$on('callbackEventBus', this.fnCompleteIntegratedTemplate);
			confirm.fnConfirm(this.detailTitle, "템플릿을 등록 하시겠습니까?", "확인");
		},
		// RCS 템플릿 정보 Set
		fnSetRcsTemplate(data) {
			var vm = this;
			var formNm = data.formNm;
			if (formNm.indexOf("승인") != -1) {
				this.rcsImgsrc = require("@/assets/images/common/approve.png");
			}
			if (formNm.indexOf("취소") != -1) {
				this.rcsImgsrc = require("@/assets/images/common/cancel.png");
			}
			if (formNm.indexOf("인증") != -1) {
				this.rcsImgsrc = require("@/assets/images/common/certification.png");
			}
			if (formNm.indexOf("출고") != -1) {
				this.rcsImgsrc = require("@/assets/images/common/delivery.png");
			}
			if (formNm.indexOf("안내") != -1) {
				this.rcsImgsrc = require("@/assets/images/common/infomation.png");
			}
			if (formNm.indexOf("회원가입") != -1) {
				this.rcsImgsrc = require("@/assets/images/common/join.png");
			}
			if (formNm.indexOf("주문") != -1) {
				this.rcsImgsrc = require("@/assets/images/common/order.png");
			}
			if (formNm.indexOf("출금") != -1) {
				this.rcsImgsrc = require("@/assets/images/common/payment.png");
			}
			if (formNm.indexOf("입금") != -1) {
				this.rcsImgsrc = require("@/assets/images/common/receipts.png");
			}
			if (formNm.indexOf("예약") != -1) {
				this.rcsImgsrc = require("@/assets/images/common/reservation.png");
			}
			if (formNm.indexOf("배송") != -1) {
				this.rcsImgsrc = require("@/assets/images/common/ship.png");
			}
			if (formNm.indexOf("명세서") != -1) {
				this.rcsImgsrc = require("@/assets/images/common/specifications.png");
			}

			if (data.radioBtn == 'des') {
				vm.rowData.rcsDesMessagebaseId = data.messagebaseId;
				vm.rowData.brandNm = data.brandId;
				vm.rowData.rcsDesFormNm = data.formNm;
				vm.rowData.rcsDesMessagebaseformId = data.messagebaseformId;
				vm.rowData.rcs1Content = data.desContent;
				vm.rowData.rcsDesButtons = data.rcsButtonList;
			} else {
				//vm.sendData.messagebaseId = data.messagebaseId;
				//vm.styleContentCnt = data.styleContentCnt;
				//vm.styleInput  = data.styleInput ;
				//vm.styleInputSec = data.styleInputSec;
				//vm.styleChk = data.styleChk;
				//vm.btnCnt = data.btnCnt;
				//vm.btnNm = data.btnNm;
			}

			this.selectedRcsDesTemplate = true;
		},
		//템플릿 정보 Set
		fnSetTemplateInfo(templateInfo){
			Object.assign(this.rowData, templateInfo);
			this.selectedAlimTalkTemplate = true;
			this.buttonAlimTalkFlag = true;
		},
		// template 정보 조회
		fnSetMultiSendTemplateInfo(){
			if (!this.isEmpty(this.tmpltCodeP)) {
				this.fnSelectMultiSendTemplateInfo();
			}
		},
		// 템플릿 정보 조회
		fnSelectMultiSendTemplateInfo() {
			const params = {multiSendTmpltCode: this.tmpltCodeP};
			templateApi.integratedTemplateInfo(params).then(response => {
				const result = response.data;
				if (result.success) {
					if (result.data != null && result.data.length > 0) {
						let rtnData = result.data[0];

						//1. 템플릿설정 => 메시지 구분, 메시지 타입, 타프로젝트 사용여부 체크,  템플릿 명 적용
						//2. 채널선택 체크 및 발송순서 처리
						//3. 1) Push인 경우 해당 내용 적용
						//   2) RCS인 경우 1-1) 상품 선택 체크  => rcsPrdType을 이용해서 처리
						//               1-2) 해당 내용 적용
						//        3) 카카오톡인 경우
						//        4) SMS/MMS인 경우

						this.rowData.checkedChannel = rtnData.chTypeList.split(',');
						for (var i=0; i < this.rowData.checkedChannel.length; i++) {
							if (this.rowData.checkedChannel[i] == 'PUSH') {
								this.channelTab = 0;
								this.checkedPush = true;
							} else if(this.rowData.checkedChannel[i] == 'RCS') {
								this.channelTab = 1;
								this.checkedRCS = true;
							} else if(this.rowData.checkedChannel[i] == 'KAKAO') {
								this.channelTab = 2;
								this.checkedKakao = true;
							} else if(this.rowData.checkedChannel[i] == 'SMSMMS') {
								this.channelTab = 3;
								this.checkedSmsMms = true;
							}
						}
						var firstChannel = this.rowData.checkedChannel[0];
						if (firstChannel == 'PUSH') {
							this.channelTab = 0;
							this.checkedPush = true;
						} else if (firstChannel == 'RCS') {
							this.channelTab = 1;
							this.checkedRCS = true;
						} else if (firstChannel == 'KAKAO') {
							this.channelTab = 2;
							this.checkedKakao = true;
						} else if (firstChannel == 'SMSMMS') {
							this.channelTab = 3;
							this.checkedSmsMms = true;
						}
						this.rowData.multiSendTmpltCode	= rtnData.tmpltCode;
						this.rowData.msgType						= rtnData.msgType;
						this.rowData.msgKind						= rtnData.msgKind;
						this.rowData.tmpltTitle					= this.$gfnCommonUtils.unescapeXss(rtnData.tmpltTitle);
						this.rowData.otherProjectUseYn		= rtnData.otherProjectUseYn;

						// PUSH DATA SET
						this.rowData.pushContent = this.$gfnCommonUtils.unescapeXss(rtnData.pushMsg);
						this.rowData.pushAppId = rtnData.pushAppId;
						this.rowData.pushTitle = this.$gfnCommonUtils.unescapeXss(rtnData.pushTitle);
						this.rowData.pushImgInfo.fileId = rtnData.pushFileId;
						this.rowData.pushImgInfo.imgUrl = rtnData.pushChImgUrl;
						this.rowData.pushSend = rtnData.pushSendType;
						this.rowData.pushHowToDenyReceipt = rtnData.pushRcvblcInput; // 수신거부방법
						this.rowData.adtnInfo = this.$gfnCommonUtils.unescapeXss(rtnData.adtnInfo); // 부가정보

						// RCS DATA SET
						if (rtnData.rcsPrdType == 'FREE') {
							this.rcsTemplateTable = 0;
							this.rcsTemplateTableChecked = 0;
						} else if (rtnData.rcsPrdType == 'DESCRIPTION') {
							this.rcsTemplateTable = 1;
							this.rcsTemplateTableChecked = 1;
							this.selectedRcsDesTemplate = true;
						} else if (rtnData.rcsPrdType == 'CELL') {
							this.rcsTemplateTable = 2;
							this.rcsTemplateTableChecked = 2;
						} else if (rtnData.rcsPrdType == 'SMS') {
							this.rcsTemplateTable = 3;
							this.rcsTemplateTableChecked = 3;
						} else if (rtnData.rcsPrdType == 'LMS') {
							this.rcsTemplateTable = 4;
							this.rcsTemplateTableChecked = 4;
						} else if (rtnData.rcsPrdType == 'SHORT') {
							this.rcsTemplateTable = 5;
							this.rcsTemplateTableChecked = 5;
						} else if (rtnData.rcsPrdType == 'TALL') {
							this.rcsTemplateTable = 6;
							this.rcsTemplateTableChecked = 6;
						} else if (rtnData.rcsPrdType == 'CSHORT') {
							this.rcsTemplateTable = 9;
							this.rcsTemplateTableChecked = 9;
						} else if (rtnData.rcsPrdType == 'CTALL') {
							this.rcsTemplateTable = 10;
							this.rcsTemplateTableChecked = 10;
						}

						if (rtnData.rcsPrdType == 'FREE') {
							this.rowData.brandNm				= rtnData.rcsBrandNm;
							this.rowData.rcs0Content			= this.$gfnCommonUtils.unescapeXss(rtnData.rcs0Content);
							this.rowData.callback					= rtnData.rcsCallback; //발신번호
							this.rowData.rcsBlockNumber		= rtnData.rcsBlockNumber; //수신거부번호
						}

						if (rtnData.rcsPrdType == 'DESCRIPTION') {
							this.rowData.rcsDesMessagebaseId			= rtnData.rcsMessagebaseId;
							this.rowData.brandNm							= rtnData.rcsBrandNm;
							console.log(rtnData.rcsDesFormNm);
							console.log(rtnData.rcsMessagebaseformId);
							this.rowData.rcsDesFormNm					= rtnData.rcsDesFormNm;
							this.rowData.rcsDesMessagebaseformId	= rtnData.rcsMessagebaseformId;
							this.rowData.rcs1Content						= this.$gfnCommonUtils.unescapeXss(rtnData.rcs0Content);
							this.rowData.callback								= rtnData.rcsCallback; //발신번호
							this.rowData.rcsDesButtons						= rtnData.rcsButton0Data;

							if (rtnData.rcsDesFormNm.indexOf("승인") != -1) {
								this.rcsImgsrc = require("@/assets/images/common/approve.png");
							}
							if (rtnData.rcsDesFormNm.indexOf("취소") != -1) {
								this.rcsImgsrc = require("@/assets/images/common/cancel.png");
							}
							if (rtnData.rcsDesFormNm.indexOf("인증") != -1) {
								this.rcsImgsrc = require("@/assets/images/common/certification.png");
							}
							if (rtnData.rcsDesFormNm.indexOf("출고") != -1) {
								this.rcsImgsrc = require("@/assets/images/common/delivery.png");
							}
							if (rtnData.rcsDesFormNm.indexOf("안내") != -1) {
								this.rcsImgsrc = require("@/assets/images/common/infomation.png");
							}
							if (rtnData.rcsDesFormNm.indexOf("회원가입") != -1) {
								this.rcsImgsrc = require("@/assets/images/common/join.png");
							}
							if (rtnData.rcsDesFormNm.indexOf("주문") != -1) {
								this.rcsImgsrc = require("@/assets/images/common/order.png");
							}
							if (rtnData.rcsDesFormNm.indexOf("출금") != -1) {
								this.rcsImgsrc = require("@/assets/images/common/payment.png");
							}
							if (rtnData.rcsDesFormNm.indexOf("입금") != -1) {
								this.rcsImgsrc = require("@/assets/images/common/receipts.png");
							}
							if (rtnData.rcsDesFormNm.indexOf("예약") != -1) {
								this.rcsImgsrc = require("@/assets/images/common/reservation.png");
							}
							if (rtnData.rcsDesFormNm.indexOf("배송") != -1) {
								this.rcsImgsrc = require("@/assets/images/common/ship.png");
							}
							if (rtnData.rcsDesFormNm.indexOf("명세서") != -1) {
								this.rcsImgsrc = require("@/assets/images/common/specifications.png");
							}
						}

						if (rtnData.rcsPrdType == 'SMS') {
							this.rowData.rcsSMSContent 			= rtnData.rcsBodyMessage;
							this.rowData.rcsSMSHowToDenyReceipt = rtnData.rcsFooter;   //무료수신거부번호
							this.rowData.callback 				= rtnData.rcsCallback; //발신번호
	   
							if (rtnData.rcsButton0ButtonType) {//RCS SMS는 버튼 1개까지 추가 가능
								this.buttonSMSFlag = true;
								this.rowData.rcsSMSButtons.push(JSON.parse(rtnData.rcs0ButtonArr0));
							}
						}
	
						if (rtnData.rcsPrdType == 'LMS') {
							this.rowData.rcsLMSTitle			= rtnData.rcsBodyTitle;
							this.rowData.rcsLMSContent 			= rtnData.rcsBodyMessage;
							this.rowData.rcsLMSHowToDenyReceipt = rtnData.rcsFooter;   //무료수신거부번호
							this.rowData.callback 				= rtnData.rcsCallback; //발신번호
	
							if (rtnData.rcsButton0ButtonType) {//RCS LMS는 버튼 3개까지 추가 가능
								this.buttonLMSFlag = true;
								this.rowData.rcsLMSButtons.push(JSON.parse(rtnData.rcs0ButtonArr0));
							}
	
							if (rtnData.rcsButton1ButtonType) {
								this.buttonLMSFlag = true;
								this.rowData.rcsLMSButtons.push(JSON.parse(rtnData.rcs0ButtonArr1));
							}
	
							if (rtnData.rcsButton2ButtonType) {
								this.buttonLMSFlag = true;
								this.rowData.rcsLMSButtons.push(JSON.parse(rtnData.rcs0ButtonArr2));
							}
						}
	
						if (rtnData.rcsPrdType == 'SHORT') {
							this.rowData.rcsShortTitle				= rtnData.rcsBodyTitle;
							this.rowData.rcsShortContent 			= rtnData.rcsBodyMessage;
							this.rowData.rcsShortHowToDenyReceipt 	= rtnData.rcsFooter;   //무료수신거부번호
							this.rowData.callback 					= rtnData.rcsCallback; //발신번호
	
							if (rtnData.rcsBodyMediaUrl) {
								this.rowData.rcsShortImgInfoList.push({'fileId':rtnData.rcsBodyMedia, 'imgUrl':rtnData.rcsBodyMediaUrl});
							}
							if (rtnData.rcsButton0ButtonType) {//RCS SHORT은 버튼 2개까지 추가 가능
								this.buttonShortFlag = true;
								this.rowData.rcsShortButtons.push(JSON.parse(rtnData.rcs0ButtonArr0));
							}
							if (rtnData.rcsButton1ButtonType) {
								this.buttonTallFlag = true;
								this.rowData.rcsShortButtons.push(JSON.parse(rtnData.rcs0ButtonArr1));
							}
						}
	
						if (rtnData.rcsPrdType == 'TALL') {
							this.rowData.rcsTallTitle				= rtnData.rcsBodyTitle;
							this.rowData.rcsTallContent 			= rtnData.rcsBodyMessage;
							this.rowData.rcsTallHowToDenyReceipt 	= rtnData.rcsFooter;   //무료수신거부번호
							this.rowData.callback 					= rtnData.rcsCallback; //발신번호
	
							if (rtnData.rcsBodyMediaUrl) {
								this.rowData.rcsTallImgInfoList.push({'fileId':rtnData.rcsBodyMedia, 'imgUrl':rtnData.rcsBodyMediaUrl});
							}
							if (rtnData.rcsButton0ButtonType) {//RCS TALL은 버튼 2개까지 추가 가능
								this.buttonTallFlag = true;
								this.rowData.rcsTallButtons.push(JSON.parse(rtnData.rcs0ButtonArr0));
							}
							if (rtnData.rcsButton1ButtonType) {
								this.buttonTallFlag = true;
								this.rowData.rcsTallButtons.push(JSON.parse(rtnData.rcs0ButtonArr1));
							}
						}
	
						if (rtnData.rcsPrdType == 'CSHORT') {
							this.rcs9CardCount				= rtnData.rcsCardCount;
							this.rowData.rcs9HowToDenyReceipt 		= rtnData.rcsFooter;   //무료수신거부번호
							this.rowData.callback 					= rtnData.rcsCallback; //발신번호
	
							this.rowData.rcs90Title					= rtnData.rcsBodyTitle;
							this.rowData.rcs90Content				= rtnData.rcsBodyMessage;
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
	
							if (rtnData.rcsBodyMediaUrl) {
								this.rowData.rcs90ImgInfoList.push({'fileId':rtnData.rcsBodyMedia, 'imgUrl':rtnData.rcsBodyMediaUrl});
							}
							if (rtnData.rcs1BodyMediaUrl) {
								this.rowData.rcs91ImgInfoList.push({'fileId':rtnData.rcs1BodyMedia, 'imgUrl':rtnData.rcs1BodyMediaUrl});
							}
							if (rtnData.rcs2BodyMediaUrl) {
								this.rowData.rcs92ImgInfoList.push({'fileId':rtnData.rcs2BodyMedia, 'imgUrl':rtnData.rcs2BodyMediaUrl});
							}
							if (rtnData.rcs3BodyMediaUrl) {
								this.rowData.rcs93ImgInfoList.push({'fileId':rtnData.rcs3BodyMedia, 'imgUrl':rtnData.rcs3BodyMediaUrl});
							}
							if (rtnData.rcs4BodyMediaUrl) {
								this.rowData.rcs94ImgInfoList.push({'fileId':rtnData.rcs4BodyMedia, 'imgUrl':rtnData.rcs4BodyMediaUrl});
							}
							if (rtnData.rcs5BodyMediaUrl) {
								this.rowData.rcs95ImgInfoList.push({'fileId':rtnData.rcs5BodyMedia, 'imgUrl':rtnData.rcs5BodyMediaUrl});
							}
							if (rtnData.rcsButton0ButtonType) {//RCS CSHORT은 카드 6개, 버튼 2개까지 추가 가능
								this.button90Flag = true;
								this.rowData.rcs90Buttons.push(JSON.parse(rtnData.rcs0ButtonArr0));
							}
							if (rtnData.rcsButton1ButtonType) {
								this.button90Flag = true;
								this.rowData.rcs90Buttons.push(JSON.parse(rtnData.rcs0ButtonArr1));
							}
							if (rtnData.rcs1Button0ButtonType) {
								this.button91Flag = true;
								this.rowData.rcs91Buttons.push(JSON.parse(rtnData.rcs1ButtonArr0));
							}
							if (rtnData.rcs1Button1ButtonType) {
								this.button91Flag = true;
								this.rowData.rcs91Buttons.push(JSON.parse(rtnData.rcs1ButtonArr1));
							}
							if (rtnData.rcs2Button0ButtonType) {
								this.button92Flag = true;
								this.rowData.rcs92Buttons.push(JSON.parse(rtnData.rcs2ButtonArr0));
							}
							if (rtnData.rcs2Button1ButtonType) {
								this.button92Flag = true;
								this.rowData.rcs92Buttons.push(JSON.parse(rtnData.rcs2ButtonArr1));
							}
							if (rtnData.rcs3Button0ButtonType) {
								this.button93Flag = true;
								this.rowData.rcs93Buttons.push(JSON.parse(rtnData.rcs3ButtonArr0));
							}
							if (rtnData.rcs3Button1ButtonType) {
								this.button93Flag = true;
								this.rowData.rcs93Buttons.push(JSON.parse(rtnData.rcs3ButtonArr1));
							}
							if (rtnData.rcs4Button0ButtonType) {
								this.button94Flag = true;
								this.rowData.rcs94Buttons.push(JSON.parse(rtnData.rcs4ButtonArr0));
							}
							if (rtnData.rcs4Button1ButtonType) {
								this.button94Flag = true;
								this.rowData.rcs94Buttons.push(JSON.parse(rtnData.rcs4ButtonArr1));
							}
							if (rtnData.rcs5Button0ButtonType) {
								this.button95Flag = true;
								this.rowData.rcs95Buttons.push(JSON.parse(rtnData.rcs5ButtonArr0));
							}
							if (rtnData.rcs5Button1ButtonType) {
								this.button95Flag = true;
								this.rowData.rcs95Buttons.push(JSON.parse(rtnData.rcs5ButtonArr1));
							}
						}
	
						if (rtnData.rcsPrdType == 'CTALL') {
							this.rcs10CardCount				= rtnData.rcsCardCount;
							this.rowData.rcs10HowToDenyReceipt 		= rtnData.rcsFooter;   //무료수신거부번호
							this.rowData.callback 				= rtnData.rcsCallback; //발신번호
	
							this.rowData.rcs100Title				= rtnData.rcsBodyTitle;
							this.rowData.rcs100Content				= rtnData.rcsBodyMessage;
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
	
							if (rtnData.rcsBodyMediaUrl) {
								this.rowData.rcs100ImgInfoList.push({'fileId':rtnData.rcsBodyMedia, 'imgUrl':rtnData.rcsBodyMediaUrl});
							}
							if (rtnData.rcs1BodyMediaUrl) {
								this.rowData.rcs101ImgInfoList.push({'fileId':rtnData.rcs1BodyMedia, 'imgUrl':rtnData.rcs1BodyMediaUrl});
							}
							if (rtnData.rcs2BodyMediaUrl) {
								this.rowData.rcs102ImgInfoList.push({'fileId':rtnData.rcs2BodyMedia, 'imgUrl':rtnData.rcs2BodyMediaUrl});
							}
							if (rtnData.rcs3BodyMediaUrl) {
								this.rowData.rcs103ImgInfoList.push({'fileId':rtnData.rcs3BodyMedia, 'imgUrl':rtnData.rcs3BodyMediaUrl});
							}
							if (rtnData.rcs4BodyMediaUrl) {
								this.rowData.rcs104ImgInfoList.push({'fileId':rtnData.rcs4BodyMedia, 'imgUrl':rtnData.rcs4BodyMediaUrl});
							}
							if (rtnData.rcs5BodyMediaUrl) {
								this.rowData.rcs105ImgInfoList.push({'fileId':rtnData.rcs5BodyMedia, 'imgUrl':rtnData.rcs5BodyMediaUrl});
							}
							if (rtnData.rcsButton0ButtonType) {//RCS CTALL은 카드 6개, 버튼 2개까지 추가 가능
								this.button100Flag = true;
								this.rowData.rcs100Buttons.push(JSON.parse(rtnData.rcs0ButtonArr0));
							}
							if (rtnData.rcsButton1ButtonType) {
								this.button100Flag = true;
								this.rowData.rcs100Buttons.push(JSON.parse(rtnData.rcs0ButtonArr1));
							}
							if (rtnData.rcs1Button0ButtonType) {
								this.button101Flag = true;
								this.rowData.rcs101Buttons.push(JSON.parse(rtnData.rcs1ButtonArr0));
							}
							if (rtnData.rcs1Button1ButtonType) {
								this.button101Flag = true;
								this.rowData.rcs101Buttons.push(JSON.parse(rtnData.rcs1ButtonArr1));
							}
							if (rtnData.rcs2Button0ButtonType) {
								this.button102Flag = true;
								this.rowData.rcs102Buttons.push(JSON.parse(rtnData.rcs2ButtonArr0));
							}
							if (rtnData.rcs2Button1ButtonType) {
								this.button102Flag = true;
								this.rowData.rcs102Buttons.push(JSON.parse(rtnData.rcs2ButtonArr1));
							}
							if (rtnData.rcs3Button0ButtonType) {
								this.button103Flag = true;
								this.rowData.rcs103Buttons.push(JSON.parse(rtnData.rcs3ButtonArr0));
							}
							if (rtnData.rcs3Button1ButtonType) {
								this.button103Flag = true;
								this.rowData.rcs103Buttons.push(JSON.parse(rtnData.rcs3ButtonArr1));
							}
							if (rtnData.rcs4Button0ButtonType) {
								this.button104Flag = true;
								this.rowData.rcs104Buttons.push(JSON.parse(rtnData.rcs4ButtonArr0));
							}
							if (rtnData.rcs4Button1ButtonType) {
								this.button104Flag = true;
								this.rowData.rcs104Buttons.push(JSON.parse(rtnData.rcs4ButtonArr1));
							}
							if (rtnData.rcs5Button0ButtonType) {
								this.button105Flag = true;
								this.rowData.rcs105Buttons.push(JSON.parse(rtnData.rcs5ButtonArr0));
							}
							if (rtnData.rcs5Button1ButtonType) {
								this.button105Flag = true;
								this.rowData.rcs105Buttons.push(JSON.parse(rtnData.rcs5ButtonArr1));
							}
						}
	
						// FRIENDTALK DATA SET
						this.rowData.friendTalkSenderKeyType	= rtnData.friendTalkSenderKeyType;
						this.rowData.friendTalkSenderKey 		= rtnData.friendTalkSenderKey;
						this.rowData.friendTalkImageLink 		= rtnData.friendTalkImageLink;
						this.rowData.friendTalkContent 			= this.$gfnCommonUtils.unescapeXss(rtnData.friendTalkContent);
						this.rowData.friendTalkImgInfo.fileId 	= rtnData.friendTalkFileId;
						this.rowData.friendTalkImgInfo.imgUrl	= rtnData.friendTalkImgUrl;
						this.rowData.friendTalkImgInfo.wideImgYn = rtnData.friendTalkWideImageYn;
	
						if (rtnData.friendTalkPrdType == 'FRIENDTALK') {//카카오톡 친구톡은 5개 버튼까지 추가 가능
							if (rtnData.friendTalkButton0Type) {
								this.buttonFriendTalkFlag = true;
								this.rowData.friendTalkButtons.push(JSON.parse(rtnData.friendTalkButtonArr0));
							}
	
							if (rtnData.friendTalkButton1Type) {
								this.buttonFriendTalkFlag = true;
								this.rowData.friendTalkButtons.push(JSON.parse(rtnData.friendTalkButtonArr1));
							}
	
							if (rtnData.friendTalkButton2Type) {
								this.buttonFriendTalkFlag = true;
								this.rowData.friendTalkButtons.push(JSON.parse(rtnData.friendTalkButtonArr2));
							}
	
							if (rtnData.friendTalkButton3Type) {
								this.buttonFriendTalkFlag = true;
								this.rowData.friendTalkButtons.push(JSON.parse(rtnData.friendTalkButtonArr3));
							}
	
							if (rtnData.friendTalkButton4Type) {
								this.buttonFriendTalkFlag = true;
								this.rowData.friendTalkButtons.push(JSON.parse(rtnData.friendTalkButtonArr4));
							}
						}
	
						//ALIMTALK DATA SET
						if (rtnData.alimTalkPrdType == 'ALIMTALK') {//카카오톡 알림톡은 5개 버튼까지 추가 가능
							jQuery('input:radio[name=kakao]:input[value="Notification"]').click();
							this.selectedAlimTalkTemplate = true;
						}

						this.rowData.senderKey = rtnData.alimTalkSenderKey;
						this.rowData.tmpltName = this.$gfnCommonUtils.unescapeXss(rtnData.alimTalkTmpltName);
						this.rowData.emphasizeType = rtnData.alimTalkEmphasizeType;
						this.rowData.tmpltEmpsTitle = rtnData.alimTalkTmpltEmpsTitle;
						this.rowData.tmpltEmpsSubTitle = rtnData.alimTalkTmpltEmpsSubTitle;
						this.rowData.tmpltContent = this.$gfnCommonUtils.unescapeXss(rtnData.alimTalkTmpltContent);

						if (rtnData.alimTalkPrdType == 'ALIMTALK') {//카카오톡 알림톡은 5개 버튼까지 추가 가능
							if (rtnData.alimTalkButton0Type) {
								this.buttonAlimTalkFlag = true;
								this.rowData.buttonList.push(JSON.parse(rtnData.alimTalkButtonArr0));
							}
						}

						//SMS/MMS DATA SET
						if (rtnData.smsSendType == 'S') {
							this.smsTemplateTable = 0;
						} else if(rtnData.smsSendType == 'M') {
							this.smsTemplateTable = 1;
						}
						if (rtnData.smsSendType == 'S' || rtnData.smsSendType == 'M') {
							this.rowData.smsSendType = rtnData.smsSendType;
							this.rowData.smsTitle = this.$gfnCommonUtils.unescapeXss(rtnData.smsTitle);
							this.rowData.smsContent = this.$gfnCommonUtils.unescapeXss(rtnData.smsContent);
	
							if (rtnData.smsFileId0) {
								this.rowData.smsImgInfoList.push({'fileId':rtnData.smsFileId0 , 'imgUrl':rtnData.mmsChImgUrl0});
							}
	
							if (rtnData.smsFileId1) {
								this.rowData.smsImgInfoList.push({'fileId':rtnData.smsFileId1, 'imgUrl':rtnData.mmsChImgUrl1});
							}
	
							this.rowData.callback = rtnData.smsCallback;
							this.rowData.smsRcvblcNumber = rtnData.smsRcvblcNumber;
						}
					}
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
		fnPushOpenImageManagePopUp(){
			this.$refs.pushImgMng.fnSearch();
			this.pushImgMngOpen = !this.pushImgMngOpen;
		},
		fnPushCallbackImgInfo(imgInfo){
			this.rowData.pushImgInfo.imgUrl = imgInfo.chImgUrl;
			this.rowData.pushImgInfo.fileId = imgInfo.fileId;
		},
		// 푸시 이미지 삭제
		fnPushDelImg(){
			this.rowData.pushImgInfo = {};
		},
		// RCS(SMS상품) 버튼 추가
		addRowSMS: function(){
			if (this.rowData.rcsSMSButtons.length >= 1) {
				alert("button은 1개까지 추가가능합니다.");
			} else {
				this.buttonSMSFlag = true;
				this.smsButtonsMaxLen = this.smsButtonsMaxLen + 1;
				var startDateId = 'rcsSMSStartDateId'	+ this.smsButtonsMaxLen;
				var endDateId 	= 'rcsSMSEndDateId'		+ this.smsButtonsMaxLen;

				this.rowData.rcsSMSButtons.push({'buttonType':'', 'buttonName':'', 'buttonLink':'', 'startDate':'', 'endDate':'', 'startDateId':startDateId, 'endDateId':endDateId, 'buttonLink1':''});
			}
		},
		// RCS(SMS상품) 버튼 삭제
		removeRowSMS: function(row){
			if (this.rowData.rcsSMSButtons.length <= 1) {
				this.rowData.rcsSMSButtons.splice(row,1);
				this.rowData.rcsSMSButtons.push({'buttonType':'', 'buttonName':'', 'buttonLink':''});//입력할수 있도록 빈공백의 한칸은 남겨둔다.
			} else {
				this.rowData.rcsSMSButtons.splice(row,1);
			}
		},
		// RCS(LMS상품) 버튼 추가
		addRowLMS: function(){
			if (this.rowData.rcsLMSButtons.length >= 3) {
				alert("button은 3개까지 추가가능합니다.");
			} else {
				this.buttonLMSFlag = true;
				this.lmsButtonsMaxLen = this.lmsButtonsMaxLen + 1;
				var startDateId = 'rcsLMSStartDateId'+this.lmsButtonsMaxLen;
				var endDateId = 'rcsLMSEndDateId'+this.lmsButtonsMaxLen;

				this.rowData.rcsLMSButtons.push({'buttonType':'', 'buttonName':'', 'buttonLink':'', 'startDate':'', 'endDate':'', 'startDateId':startDateId, 'endDateId':endDateId, 'buttonLink1':''});
			}
		},
		// RCS(LMS상품) 버튼 삭제
		removeRowLMS: function(row){
			if (this.rowData.rcsLMSButtons.length <= 1) {
				this.rowData.rcsLMSButtons.splice(row,1);
				this.rowData.rcsLMSButtons.push({'buttonType':'', 'buttonName':'', 'buttonLink':''});//입력할수 있도록 빈공백의 한칸은 남겨둔다.
			} else {
				this.rowData.rcsLMSButtons.splice(row,1);
			}
		},
		addRow90: function(){
			if (this.rowData.rcs90Buttons.length >= 2) {
				alert("button은 2개까지 추가가능합니다.");
			} else {
				this.button90Flag = true;
				this.rcs90ButtonsMaxLen = this.rcs90ButtonsMaxLen + 1;
				var startDateId = 'rcs90StartDateId'	+ this.rcs90ButtonsMaxLen;
				var endDateId 	= 'rcs90EndDateId'		+ this.rcs90ButtonsMaxLen;

				this.rowData.rcs90Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':'', 'startDate':'', 'endDate':'', 'startDateId':startDateId, 'endDateId':endDateId, 'buttonLink1':''});
			}
		},
		removeRow90: function(row){
			if (this.rowData.rcs90Buttons.length <= 1) {
				this.rowData.rcs90Buttons.splice(row,1);
				this.rowData.rcs90Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':''});//입력할수 있도록 빈공백의 한칸은 남겨둔다.
			} else {
				this.rowData.rcs90Buttons.splice(row,1);
			}
		},
		addRow91: function(){
			if (this.rowData.rcs91Buttons.length >= 2) {
				alert("button은 2개까지 추가가능합니다.");
			} else {
				this.button91Flag = true;
				this.rcs91ButtonsMaxLen = this.rcs91ButtonsMaxLen + 1;
				var startDateId = 'rcs91StartDateId'	+ this.rcs91ButtonsMaxLen;
				var endDateId 	= 'rcs91EndDateId'		+ this.rcs91ButtonsMaxLen;

				this.rowData.rcs91Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':'', 'startDate':'', 'endDate':'', 'startDateId':startDateId, 'endDateId':endDateId, 'buttonLink1':''});
			}
		},
		removeRow91: function(row){
			if (this.rowData.rcs91Buttons.length <= 1) {
				this.rowData.rcs91Buttons.splice(row,1);
				this.rowData.rcs91Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':''});//입력할수 있도록 빈공백의 한칸은 남겨둔다.
			} else {
				this.rowData.rcs91Buttons.splice(row,1);
			}
		},
		addRow92: function(){
			if (this.rowData.rcs92Buttons.length >= 2) {
				alert("button은 2개까지 추가가능합니다.");
			} else {
				this.button92Flag = true;
				this.rcs92ButtonsMaxLen = this.rcs92ButtonsMaxLen + 1;
				var startDateId = 'rcs92StartDateId'	+ this.rcs92ButtonsMaxLen;
				var endDateId 	= 'rcs92EndDateId'		+ this.rcs92ButtonsMaxLen;

				this.rowData.rcs92Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':'', 'startDate':'', 'endDate':'', 'startDateId':startDateId, 'endDateId':endDateId, 'buttonLink1':''});
			}
		},
		removeRow92: function(row){
			if (this.rowData.rcs92Buttons.length <= 1) {
				this.rowData.rcs92Buttons.splice(row,1);
				this.rowData.rcs92Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':''});//입력할수 있도록 빈공백의 한칸은 남겨둔다.
			} else {
				this.rowData.rcs92Buttons.splice(row,1);
			}
		},
		addRow93: function(){
			if (this.rowData.rcs93Buttons.length >= 2) {
				alert("button은 2개까지 추가가능합니다.");
			} else {
				this.button93Flag = true;
				this.rcs93ButtonsMaxLen = this.rcs93ButtonsMaxLen + 1;
				var startDateId = 'rcs93StartDateId'	+ this.rcs93ButtonsMaxLen;
				var endDateId 	= 'rcs93EndDateId'		+ this.rcs93ButtonsMaxLen;

				this.rowData.rcs93Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':'', 'startDate':'', 'endDate':'', 'startDateId':startDateId, 'endDateId':endDateId, 'buttonLink1':''});
			}
		},
		removeRow93: function(row){
			if (this.rowData.rcs93Buttons.length <= 1) {
				this.rowData.rcs93Buttons.splice(row,1);
				this.rowData.rcs93Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':''});//입력할수 있도록 빈공백의 한칸은 남겨둔다.
			} else {
				this.rowData.rcs93Buttons.splice(row,1);
			}
		},
		addRow94: function(){
			if (this.rowData.rcs94Buttons.length >= 2) {
				alert("button은 2개까지 추가가능합니다.");
			} else {
				this.button94Flag = true;
				this.rcs94ButtonsMaxLen = this.rcs94ButtonsMaxLen + 1;
				var startDateId = 'rcs94StartDateId'	+ this.rcs94ButtonsMaxLen;
				var endDateId 	= 'rcs94EndDateId'		+ this.rcs94ButtonsMaxLen;

				this.rowData.rcs94Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':'', 'startDate':'', 'endDate':'', 'startDateId':startDateId, 'endDateId':endDateId, 'buttonLink1':''});
			}
		},
		removeRow94: function(row){
			if (this.rowData.rcs94Buttons.length <= 1) {
				this.rowData.rcs94Buttons.splice(row,1);
				this.rowData.rcs94Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':''});//입력할수 있도록 빈공백의 한칸은 남겨둔다.
			} else {
				this.rowData.rcs94Buttons.splice(row,1);
			}
		},
		addRow95: function(){
			if (this.rowData.rcs95Buttons.length >= 2) {
				alert("button은 2개까지 추가가능합니다.");
			} else {
				this.button95Flag = true;
				this.rcs95ButtonsMaxLen = this.rcs95ButtonsMaxLen + 1;
				var startDateId = 'rcs95StartDateId'	+ this.rcs95ButtonsMaxLen;
				var endDateId 	= 'rcs95EndDateId'		+ this.rcs95ButtonsMaxLen;

				this.rowData.rcs95Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':'', 'startDate':'', 'endDate':'', 'startDateId':startDateId, 'endDateId':endDateId, 'buttonLink1':''});
			}
		},
		removeRow95: function(row){
			if (this.rowData.rcs95Buttons.length <= 1) {
				this.button95Flag = false;
				this.rowData.rcs95Buttons.splice(row,1);
				//this.rowData.rcs95Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':''});//입력할수 있도록 빈공백의 한칸은 남겨둔다.
			} else {
				this.rowData.rcs95Buttons.splice(row,1);
			}
		},
		addRowShort: function(){
			if (this.rowData.rcsShortButtons.length >= 1) {
				alert("button은 1개까지 추가가능합니다.");
			} else {
				this.buttonShortFlag = true;
				this.rcsShortButtonsMaxLen = this.rcsShortButtonsMaxLen + 1;
				var startDateId = 'rcsShortStartDateId'	+ this.rcsShortButtonsMaxLen;
				var endDateId 	= 'rcsShortEndDateId'	+ this.rcsShortButtonsMaxLen;

				this.rowData.rcsShortButtons.push({'buttonType':'', 'buttonName':'', 'buttonLink':'', 'startDate':'', 'endDate':'', 'startDateId':startDateId, 'endDateId':endDateId, 'buttonLink1':''});
			}
		},
		removeRowShort: function(row){
			if (this.rowData.rcsShortButtons.length <= 1) {
				this.rowData.rcsShortButtons.splice(row,1);
				this.rowData.rcsShortButtons.push({'buttonType':'', 'buttonName':'', 'buttonLink':''});//입력할수 있도록 빈공백의 한칸은 남겨둔다.
			} else {
				this.rowData.rcsShortButtons.splice(row,1);
			}
		},
		addRowTall: function(){
			if (this.rowData.rcsTallButtons.length >= 1) {
				alert("button은 1개까지 추가가능합니다.");
			} else {
				this.buttonTallFlag = true;
				this.rcsTallButtonsMaxLen = this.rcsTallButtonsMaxLen + 1;
				var startDateId = 'rcsTallStartDateId'	+ this.rcsTallButtonsMaxLen;
				var endDateId 	= 'rcsTallEndDateId'	+ this.rcsTallButtonsMaxLen;

				this.rowData.rcsTallButtons.push({'buttonType':'', 'buttonName':'', 'buttonLink':'', 'startDate':'', 'endDate':'', 'startDateId':startDateId, 'endDateId':endDateId, 'buttonLink1':''});
			}
		},
		removeRowTall: function(row){
			if (this.rowData.rcsTallButtons.length <= 1) {
				this.rowData.rcsTallButtons.splice(row,1);
				this.rowData.rcsTallButtons.push({'buttonType':'', 'buttonName':'', 'buttonLink':''});//입력할수 있도록 빈공백의 한칸은 남겨둔다.
			} else {
				this.rowData.rcsTallButtons.splice(row,1);
			}
		},
		addRowFriendTalk: function(){
			if (this.rowData.friendTalkButtons.length >= 5) {
				alert("button은 5개까지 추가가능합니다.");
			} else {
				this.buttonFriendTalkFlag = true;
				this.friendTalkButtonsMaxLen = this.friendTalkButtonsMaxLen + 1;
				var startDateId = 'friendTalkStartDateId'+this.friendTalkButtonsMaxLen;
				var endDateId = 'friendTalkEndDateId'+this.friendTalkButtonsMaxLen;
        
				this.rowData.friendTalkButtons.push({'type':'WL', 'name':'', 'buttonLink':'', 'startDate':'', 'endDate':'', 'startDateId':startDateId, 'endDateId':endDateId, 'buttonLink1':''});
			}
		},
		removeRowFriendTalk: function(row){
			if (this.rowData.friendTalkButtons.length <= 1) {
				this.rowData.friendTalkButtons.splice(row,1);
				this.rowData.friendTalkButtons.push({'type':'', 'name':'', 'buttonLink':'', 'startDate':'', 'endDate':'', 'startDateId':'', 'endDateId':'', 'buttonLink1':''});//입력할수 있도록 빈공백의 한칸은 남겨둔다.
			} else {
				this.rowData.friendTalkButtons.splice(row,1);
			}
		},
		fnRcs90OpenImageManagePopUp(){
			if (this.fnRcs90ImgLimitSize() == false) return;
			this.$refs.rcs90ImgMng.fnSearch();
			this.rcs90ImgMngOpen = !this.rcs90ImgMngOpen;
		},
		fnRcs90CallbackImgInfo(imgInfo){
			if (this.fnRcs90ImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};

			this.rowData.rcs90ImgInfoList.push(temp);
			this.fnRcs90DelDuplImgInfo();
		},
		fnRcs90DelDuplImgInfo(){
			const vm = this;
			this.rowData.rcs90ImgInfoList = this.rowData.rcs90ImgInfoList.filter(function(item, i) {
				return (
					vm.rowData.rcs90ImgInfoList.findIndex((item2) => {
						return item.fileId === item2.fileId;
					}) === i
				);
			});
		},
		fnRcs90ImgLimitSize(){
			if (this.rowData.rcs90ImgInfoList != null && this.rowData.rcs90ImgInfoList.length >= this.rcs90ImgLimitSize) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 ' + this.rcs90ImgLimitSize +' 개까지 등록 가능합니다.');
				return false;
			}
		},
		fnRcs90DelImg(idx){
			this.rowData.rcs90ImgInfoList.splice(idx, 1);
		},
		fnRcs91OpenImageManagePopUp(){
			if (this.fnRcs91ImgLimitSize() == false) return;
			this.$refs.rcs91ImgMng.fnSearch();
			this.rcs91ImgMngOpen = !this.rcs91ImgMngOpen;
		},
		fnRcs91CallbackImgInfo(imgInfo){
			if (this.fnRcs91ImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};
			this.rowData.rcs91ImgInfoList.push(temp);
			this.fnRcs91DelDuplImgInfo();
		},
		fnRcs91DelDuplImgInfo(){
			const vm = this;
			this.rowData.rcs91ImgInfoList = this.rowData.rcs91ImgInfoList.filter(function(item, i) {
				return (
					vm.rowData.rcs91ImgInfoList.findIndex((item2) => {
						return item.fileId === item2.fileId;
					}) === i
				);
			});
		},
		fnRcs91ImgLimitSize(){
			if (this.rowData.rcs91ImgInfoList != null && this.rowData.rcs91ImgInfoList.length >= this.rcs91ImgLimitSize) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 ' + this.rcs91ImgLimitSize +' 개까지 등록 가능합니다.');
				return false;
			}
		},
		fnRcs91DelImg(idx){
			this.rowData.rcs91ImgInfoList.splice(idx, 1);
		},
		fnRcs92OpenImageManagePopUp(){
			if (this.fnRcs92ImgLimitSize() == false) return;
			this.$refs.rcs92ImgMng.fnSearch();
			this.rcs92ImgMngOpen = !this.rcs92ImgMngOpen;
		},
		fnRcs92CallbackImgInfo(imgInfo){
			if (this.fnRcs92ImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};
			this.rowData.rcs92ImgInfoList.push(temp);
			this.fnRcs92DelDuplImgInfo();
		},
		fnRcs92DelDuplImgInfo(){
			const vm = this;
			this.rowData.rcs92ImgInfoList = this.rowData.rcs92ImgInfoList.filter(function(item, i) {
				return (
					vm.rowData.rcs92ImgInfoList.findIndex((item2) => {
						return item.fileId === item2.fileId;
					}) === i
				);
			});
		},
		fnRcs92ImgLimitSize(){
			if (this.rowData.rcs92ImgInfoList != null && this.rowData.rcs92ImgInfoList.length >= this.rcs92ImgLimitSize) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 ' + this.rcs92ImgLimitSize +' 개까지 등록 가능합니다.');
				return false;
			}
		},
		fnRcs92DelImg(idx){
			this.rowData.rcs92ImgInfoList.splice(idx, 1);
		},
		fnRcs93OpenImageManagePopUp(){
			if (this.fnRcs93ImgLimitSize() == false) return;
			this.$refs.rcs93ImgMng.fnSearch();
			this.rcs93ImgMngOpen = !this.rcs93ImgMngOpen;
		},
		fnRcs93CallbackImgInfo(imgInfo){
			if (this.fnRcs93ImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};
			this.rowData.rcs93ImgInfoList.push(temp);
			this.fnRcs93DelDuplImgInfo();
		},
		fnRcs93DelDuplImgInfo(){
			const vm = this;
			this.rowData.rcs93ImgInfoList = this.rowData.rcs93ImgInfoList.filter(function(item, i) {
				return (
					vm.rowData.rcs93ImgInfoList.findIndex((item2) => {
						return item.fileId === item2.fileId;
					}) === i
				);
			});
		},
		fnRcs93ImgLimitSize(){
			if (this.rowData.rcs93ImgInfoList != null && this.rowData.rcs93ImgInfoList.length >= this.rcs93ImgLimitSize) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 ' + this.rcs93ImgLimitSize +' 개까지 등록 가능합니다.');
				return false;
			}
		},
		fnRcs93DelImg(idx){
			this.rowData.rcs93ImgInfoList.splice(idx, 1);
		},
		fnRcs94OpenImageManagePopUp(){
			if (this.fnRcs94ImgLimitSize() == false) return;
			this.$refs.rcs94ImgMng.fnSearch();
			this.rcs94ImgMngOpen = !this.rcs94ImgMngOpen;
		},
		fnRcs94CallbackImgInfo(imgInfo){
			if (this.fnRcs94ImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};
			this.rowData.rcs94ImgInfoList.push(temp);
			this.fnRcs94DelDuplImgInfo();
		},
		fnRcs94DelDuplImgInfo(){
			const vm = this;
			this.rowData.rcs94ImgInfoList = this.rowData.rcs94ImgInfoList.filter(function(item, i) {
				return (
					vm.rowData.rcs94ImgInfoList.findIndex((item2) => {
						return item.fileId === item2.fileId;
					}) === i
				);
			});
		},
		fnRcs94ImgLimitSize(){
			if (this.rowData.rcs94ImgInfoList != null && this.rowData.rcs94ImgInfoList.length >= this.rcs94ImgLimitSize) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 ' + this.rcs94ImgLimitSize +' 개까지 등록 가능합니다.');
				return false;
			}
		},
		fnRcs94DelImg(idx){
			this.rowData.rcs94ImgInfoList.splice(idx, 1);
		},
		fnRcs95OpenImageManagePopUp(){
			if (this.fnRcs95ImgLimitSize() == false) return;
			this.$refs.rcs95ImgMng.fnSearch();
			this.rcs95ImgMngOpen = !this.rcs95ImgMngOpen;
		},
		fnRcs95CallbackImgInfo(imgInfo){
			if (this.fnRcs95ImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};
			this.rowData.rcs95ImgInfoList.push(temp);
			this.fnRcs95DelDuplImgInfo();
		},
		fnRcs95DelDuplImgInfo(){
			const vm = this;
			this.rowData.rcs95ImgInfoList = this.rowData.rcs95ImgInfoList.filter(function(item, i) {
				return (
					vm.rowData.rcs95ImgInfoList.findIndex((item2) => {
						return item.fileId === item2.fileId;
					}) === i
				);
			});
		},
		fnRcs95ImgLimitSize(){
			if (this.rowData.rcs95ImgInfoList != null && this.rowData.rcs95ImgInfoList.length >= this.rcs95ImgLimitSize) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 ' + this.rcs95ImgLimitSize +' 개까지 등록 가능합니다.');
				return false;
			}
		},
		fnRcs95DelImg(idx){
			this.rowData.rcs95ImgInfoList.splice(idx, 1);
		},
		addRow100: function(){
			if (this.rowData.rcs100Buttons.length >= 2) {
				alert("button은 2개까지 추가가능합니다.");
			} else {
				this.button100Flag = true;
				this.rcs100ButtonsMaxLen = this.rcs100ButtonsMaxLen + 1;
				var startDateId = 'rcs100StartDateId'	+ this.rcs100ButtonsMaxLen;
				var endDateId 	= 'rcs100EndDateId'	+ this.rcs100ButtonsMaxLen;

				this.rowData.rcs100Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':'', 'startDate':'', 'endDate':'', 'startDateId':startDateId, 'endDateId':endDateId, 'buttonLink1':''});
			}
		},
		removeRow100: function(row){
			if (this.rowData.rcs100Buttons.length <= 1){
				this.rowData.rcs100Buttons.splice(row,1);
				this.rowData.rcs100Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':''});//입력할수 있도록 빈공백의 한칸은 남겨둔다.
			} else {
				this.rowData.rcs100Buttons.splice(row,1);
			}
		},
		addRow101: function(){
			if (this.rowData.rcs101Buttons.length >= 2) {
				alert("button은 2개까지 추가가능합니다.");
			} else {
				this.button101Flag = true;
				this.rcs101ButtonsMaxLen = this.rcs101ButtonsMaxLen + 1;
				var startDateId = 'rcs101StartDateId'	+ this.rcs101ButtonsMaxLen;
				var endDateId 	= 'rcs101EndDateId'	+ this.rcs101ButtonsMaxLen;

				this.rowData.rcs101Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':'', 'startDate':'', 'endDate':'', 'startDateId':startDateId, 'endDateId':endDateId, 'buttonLink1':''});
			}
		},
		removeRow101: function(row){
			if (this.rowData.rcs101Buttons.length <= 1) {
				this.rowData.rcs101Buttons.splice(row,1);
				this.rowData.rcs101Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':''});//입력할수 있도록 빈공백의 한칸은 남겨둔다.
			} else {
				this.rowData.rcs101Buttons.splice(row,1);
			}
		},
		addRow102: function(){
			if (this.rowData.rcs102Buttons.length >= 2) {
				alert("button은 2개까지 추가가능합니다.");
			} else {
				this.button102Flag = true;
				this.rcs102ButtonsMaxLen = this.rcs102ButtonsMaxLen + 1;
				var startDateId = 'rcs102StartDateId'	+ this.rcs102ButtonsMaxLen;
				var endDateId 	= 'rcs102EndDateId'	+ this.rcs102ButtonsMaxLen;

				this.rowData.rcs102Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':'', 'startDate':'', 'endDate':'', 'startDateId':startDateId, 'endDateId':endDateId, 'buttonLink1':''});
			}
		},
		removeRow102: function(row){
			if (this.rowData.rcs102Buttons.length <= 1) {
				this.rowData.rcs102Buttons.splice(row,1);
				this.rowData.rcs102Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':''});//입력할수 있도록 빈공백의 한칸은 남겨둔다.
			} else {
				this.rowData.rcs102Buttons.splice(row,1);
			}
		},
		addRow103: function(){
			if (this.rowData.rcs103Buttons.length >= 2) {
				alert("button은 2개까지 추가가능합니다.");
			} else {
				this.button103Flag = true;
				this.rcs103ButtonsMaxLen = this.rcs103ButtonsMaxLen + 1;
				var startDateId = 'rcs103StartDateId'	+ this.rcs103ButtonsMaxLen;
				var endDateId 	= 'rcs103EndDateId'	+ this.rcs103ButtonsMaxLen;

				this.rowData.rcs103Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':'', 'startDate':'', 'endDate':'', 'startDateId':startDateId, 'endDateId':endDateId, 'buttonLink1':''});
			}
		},
		removeRow103: function(row){
			if (this.rowData.rcs103Buttons.length <= 1) {
				this.rowData.rcs103Buttons.splice(row,1);
				this.rowData.rcs103Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':''});//입력할수 있도록 빈공백의 한칸은 남겨둔다.
			} else {
				this.rowData.rcs103Buttons.splice(row,1);
			}
		},
		addRow104: function(){
			if (this.rowData.rcs104Buttons.length >= 2) {
				alert("button은 2개까지 추가가능합니다.");
			} else {
				this.button104Flag = true;
				this.rcs104ButtonsMaxLen = this.rcs104ButtonsMaxLen + 1;
				var startDateId = 'rcs104StartDateId'	+ this.rcs104ButtonsMaxLen;
				var endDateId 	= 'rcs104EndDateId'	+ this.rcs104ButtonsMaxLen;

				this.rowData.rcs104Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':'', 'startDate':'', 'endDate':'', 'startDateId':startDateId, 'endDateId':endDateId, 'buttonLink1':''});
			}
		},
		removeRow104: function(row){
			if (this.rowData.rcs104Buttons.length <= 1) {
				this.rowData.rcs104Buttons.splice(row,1);
				this.rowData.rcs104Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':''});//입력할수 있도록 빈공백의 한칸은 남겨둔다.
			} else {
				this.rowData.rcs104Buttons.splice(row,1);
			}
		},
		addRow105: function(){
			if (this.rowData.rcs105Buttons.length >= 2) {
				alert("button은 2개까지 추가가능합니다.");
			} else {
				this.button105Flag = true;
				this.rcs105ButtonsMaxLen = this.rcs105ButtonsMaxLen + 1;
				var startDateId = 'rcs105StartDateId'	+ this.rcs105ButtonsMaxLen;
				var endDateId 	= 'rcs105EndDateId'	+ this.rcs105ButtonsMaxLen;

				this.rowData.rcs105Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':'', 'startDate':'', 'endDate':'', 'startDateId':startDateId, 'endDateId':endDateId, 'buttonLink1':''});
			}
		},
		removeRow105: function(row){
			if (this.rowData.rcs105Buttons.length <= 1) {
				this.rowData.rcs105Buttons.splice(row,1);
				this.rowData.rcs105Buttons.push({'buttonType':'', 'buttonName':'', 'buttonLink':''});//입력할수 있도록 빈공백의 한칸은 남겨둔다.
			} else {
				this.rowData.rcs105Buttons.splice(row,1);
			}
		},
		fnRcs100OpenImageManagePopUp(){
			if (this.fnRcs100ImgLimitSize() == false) return;
			this.$refs.rcs100ImgMng.fnSearch();
			this.rcs100ImgMngOpen = !this.rcs100ImgMngOpen;
		},
		fnRcs100CallbackImgInfo(imgInfo){
			if (this.fnRcs100ImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};
			this.rowData.rcs100ImgInfoList.push(temp);
			this.fnRcs100DelDuplImgInfo();
		},
		fnRcs100DelDuplImgInfo(){
			const vm = this;
			this.rowData.rcs100ImgInfoList = this.rowData.rcs100ImgInfoList.filter(function(item, i) {
				return (
					vm.rowData.rcs100ImgInfoList.findIndex((item2) => {
						return item.fileId === item2.fileId;
					}) === i
				);
			});
		},
		fnRcs100ImgLimitSize(){
			if (this.rowData.rcs100ImgInfoList != null && this.rowData.rcs100ImgInfoList.length >= this.rcs100ImgLimitSize) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 ' + this.rcs100ImgLimitSize +' 개까지 등록 가능합니다.');
				return false;
			}
		},
		fnRcs100DelImg(idx){
			this.rowData.rcs100ImgInfoList.splice(idx, 1);
		},
		fnRcs101OpenImageManagePopUp(){
			if (this.fnRcs101ImgLimitSize() == false) return;
			this.$refs.rcs101ImgMng.fnSearch();
			this.rcs101ImgMngOpen = !this.rcs101ImgMngOpen;
		},
		fnRcs101CallbackImgInfo(imgInfo){
			if (this.fnRcs101ImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};
			this.rowData.rcs101ImgInfoList.push(temp);
			this.fnRcs101DelDuplImgInfo();
		},
		fnRcs101DelDuplImgInfo(){
			const vm = this;
			this.rowData.rcs101ImgInfoList = this.rowData.rcs101ImgInfoList.filter(function(item, i) {
				return (
					vm.rowData.rcs101ImgInfoList.findIndex((item2) => {
						return item.fileId === item2.fileId;
					}) === i
				);
			});
		},
		fnRcs101ImgLimitSize(){
			if (this.rowData.rcs101ImgInfoList != null && this.rowData.rcs101ImgInfoList.length >= this.rcs101ImgLimitSize) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 ' + this.rcs101ImgLimitSize +' 개까지 등록 가능합니다.');
				return false;
			}
		},
		fnRcs101DelImg(idx){
			this.rowData.rcs101ImgInfoList.splice(idx, 1);
		},
		fnRcs102OpenImageManagePopUp(){
			if (this.fnRcs102ImgLimitSize() == false) return;
			this.$refs.rcs102ImgMng.fnSearch();
			this.rcs102ImgMngOpen = !this.rcs102ImgMngOpen;
		},
		fnRcs102CallbackImgInfo(imgInfo){
			if (this.fnRcs102ImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};
			this.rowData.rcs102ImgInfoList.push(temp);
			this.fnRcs102DelDuplImgInfo();
		},
		fnRcs102DelDuplImgInfo(){
			const vm = this;
			this.rowData.rcs102ImgInfoList = this.rowData.rcs102ImgInfoList.filter(function(item, i) {
				return (
					vm.rowData.rcs102ImgInfoList.findIndex((item2) => {
						return item.fileId === item2.fileId;
					}) === i
				);
			});
		},
		fnRcs102ImgLimitSize(){
			if (this.rowData.rcs102ImgInfoList != null && this.rowData.rcs102ImgInfoList.length >= this.rcs102ImgLimitSize) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 ' + this.rcs102ImgLimitSize +' 개까지 등록 가능합니다.');
				return false;
			}
		},
		fnRcs102DelImg(idx){
			this.rowData.rcs102ImgInfoList.splice(idx, 1);
		},
		fnRcs103OpenImageManagePopUp(){
			if (this.fnRcs103ImgLimitSize() == false) return;
			this.$refs.rcs103ImgMng.fnSearch();
			this.rcs103ImgMngOpen = !this.rcs103ImgMngOpen;
		},
		fnRcs103CallbackImgInfo(imgInfo){
			if (this.fnRcs103ImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};
			this.rowData.rcs103ImgInfoList.push(temp);
			this.fnRcs103DelDuplImgInfo();
		},
		fnRcs103DelDuplImgInfo(){
			const vm = this;
			this.rowData.rcs103ImgInfoList = this.rowData.rcs103ImgInfoList.filter(function(item, i) {
				return (
					vm.rowData.rcs103ImgInfoList.findIndex((item2) => {
						return item.fileId === item2.fileId;
					}) === i
				);
			});
		},
		fnRcs103ImgLimitSize(){
			if (this.rowData.rcs103ImgInfoList != null && this.rowData.rcs103ImgInfoList.length >= this.rcs103ImgLimitSize) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 ' + this.rcs103ImgLimitSize +' 개까지 등록 가능합니다.');
				return false;
			}
		},
		fnRcs103DelImg(idx){
			this.rowData.rcs103ImgInfoList.splice(idx, 1);
		},
		fnRcs104OpenImageManagePopUp(){
			if (this.fnRcs104ImgLimitSize() == false) return;
			this.$refs.rcs104ImgMng.fnSearch();
			this.rcs104ImgMngOpen = !this.rcs104ImgMngOpen;
		},
		fnRcs104CallbackImgInfo(imgInfo){
			if (this.fnRcs104ImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};
			this.rowData.rcs104ImgInfoList.push(temp);
			this.fnRcs104DelDuplImgInfo();
		},
		fnRcs104DelDuplImgInfo(){
			const vm = this;
			this.rowData.rcs104ImgInfoList = this.rowData.rcs104ImgInfoList.filter(function(item, i) {
				return (
					vm.rowData.rcs104ImgInfoList.findIndex((item2) => {
						return item.fileId === item2.fileId;
					}) === i
				);
			});
		},
		fnRcs104ImgLimitSize(){
			if (this.rowData.rcs104ImgInfoList != null && this.rowData.rcs104ImgInfoList.length >= this.rcs104ImgLimitSize) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 ' + this.rcs104ImgLimitSize +' 개까지 등록 가능합니다.');
				return false;
			}
		},
		fnRcs104DelImg(idx){
			this.rowData.rcs104ImgInfoList.splice(idx, 1);
		},
		fnRcs105OpenImageManagePopUp(){
			if (this.fnRcs105ImgLimitSize() == false) return;
			this.$refs.rcs105ImgMng.fnSearch();
			this.rcs105ImgMngOpen = !this.rcs105ImgMngOpen;
		},
		fnRcs105CallbackImgInfo(imgInfo){
			if (this.fnRcs105ImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};
			this.rowData.rcs105ImgInfoList.push(temp);
			this.fnRcs105DelDuplImgInfo();
		},
		fnRcs105DelDuplImgInfo(){
			const vm = this;
			this.rowData.rcs105ImgInfoList = this.rowData.rcs105ImgInfoList.filter(function(item, i) {
				return (
					vm.rowData.rcs105ImgInfoList.findIndex((item2) => {
						return item.fileId === item2.fileId;
					}) === i
				);
			});
		},
		fnRcs105ImgLimitSize(){
			if (this.rowData.rcs105ImgInfoList != null && this.rowData.rcs105ImgInfoList.length >= this.rcs105ImgLimitSize) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 ' + this.rcs105ImgLimitSize +' 개까지 등록 가능합니다.');
				return false;
			}
		},
		fnRcs105DelImg(idx){
			this.rowData.rcs105ImgInfoList.splice(idx, 1);
		},
		fnRcsShortOpenImageManagePopUp(){
			if (this.fnRcsShortImgLimitSize() == false) return;
			this.$refs.rcsShortImgMng.fnSearch();
			this.rcsShortImgMngOpen = !this.rcsShortImgMngOpen;
		},
		fnRcsShortCallbackImgInfo(imgInfo){
			if (this.fnRcsShortImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};
			this.rowData.rcsShortImgInfoList.push(temp);
			this.fnRcsShortDelDuplImgInfo();
		},
		fnRcsShortDelDuplImgInfo(){
			const vm = this;
			this.rowData.rcsShortImgInfoList = this.rowData.rcsShortImgInfoList.filter(function(item, i) {
				return (
					vm.rowData.rcsShortImgInfoList.findIndex((item2) => {
						return item.fileId === item2.fileId;
					}) === i
				);
			});
		},
		fnRcsShortImgLimitSize(){
			if (this.rowData.rcsShortImgInfoList != null && this.rowData.rcsShortImgInfoList.length >= this.rcsShortImgLimitSize) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 ' + this.rcsShortImgLimitSize +' 개까지 등록 가능합니다.');
				return false;
			}
		},
		fnRcsShortDelImg(idx){
			this.rowData.rcsShortImgInfoList.splice(idx, 1);
		},
		fnRcsTallOpenImageManagePopUp(){
			if(this.fnRcsTallImgLimitSize() == false) return;
			this.$refs.rcsTallImgMng.fnSearch();
			this.rcsTallImgMngOpen = !this.rcsTallImgMngOpen;
		},
		fnRcsTallCallbackImgInfo(imgInfo){
			if (this.fnRcsTallImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};
			this.rowData.rcsTallImgInfoList.push(temp);
			this.fnRcsTallDelDuplImgInfo();
		},
		fnRcsTallDelDuplImgInfo(){
			const vm = this;
			this.rowData.rcsTallImgInfoList = this.rowData.rcsTallImgInfoList.filter(function(item, i) {
				return (
					vm.rowData.rcsTallImgInfoList.findIndex((item2) => {
						return item.fileId === item2.fileId;
					}) === i
				);
			});
		},
		fnRcsTallImgLimitSize(){
			if (this.rowData.rcsTallImgInfoList != null && this.rowData.rcsTallImgInfoList.length >= this.rcsTallImgLimitSize) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 ' + this.rcsTallImgLimitSize +' 개까지 등록 가능합니다.');
				return false;
			}
		},
		fnRcsTallDelImg(idx){
			this.rowData.rcsTallImgInfoList.splice(idx, 1);
		},
		fnFriendTalkOpenImageManagePopUp(){
			this.$refs.friendTalkImgMng.fnSearch();
			this.friendTalkImgMngOpen = !this.friendTalkImgMngOpen;
		},
		fnFriendTalkCallbackImgInfo(imgInfo){
			this.rowData.friendTalkImgInfo.imgUrl = imgInfo.chImgUrl;
			this.rowData.friendTalkImgInfo.fileId = imgInfo.fileId;
			this.rowData.friendTalkImgInfo.wideImgYn = imgInfo.wideImgYn;
		},
		fnFriendTalkDelImg(){
			this.rowData.friendTalkImgInfo = {};
		},
		fnSmsOpenImageManagePopUp(){
			if (this.fnSmsImgLimitSize() == false) return;
			this.$refs.smsImgMng.fnSearch();
			this.smsImgMngOpen = !this.smsImgMngOpen;
		},
		fnSmsCallbackImgInfo(imgInfo){
			if (this.fnSmsImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};

			this.rowData.smsImgInfoList.push(temp);
			this.fnSmsDelDuplImgInfo();
		},
		fnSmsDelDuplImgInfo(){
			const vm = this;
			this.rowData.smsImgInfoList = this.rowData.smsImgInfoList.filter(function(item, i){
				return (
					vm.rowData.smsImgInfoList.findIndex((item2) => {
						return item.fileId === item2.fileId;
					}) === i
				);
			});
		},
		fnSmsImgLimitSize(){
			if (this.rowData.smsImgInfoList != null && this.rowData.smsImgInfoList.length >= this.smsImgLimitSize) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 ' + this.smsImgLimitSize +' 개까지 등록 가능합니다.');
				return false;
			}
		},
		fnSmsDelImg(idx){
			this.rowData.smsImgInfoList.splice(idx, 1);
		},
		fnRcsSMSButtonSD(sltDate, params){
			this.rowData.rcsSMSButtons[params.idx].startDate = sltDate;
		},
		fnRcsSMSButtonED(sltDate, params){
			this.rowData.rcsSMSButtons[params.idx].endDate = sltDate;
		},
		fnRcsLMSButtonSD(sltDate, params){
			this.rowData.rcsLMSButtons[params.idx].startDate = sltDate;
		},
		fnRcsLMSButtonED(sltDate, params){
			this.rowData.rcsLMSButtons[params.idx].endDate = sltDate;
		},
		fnRcsShortButtonSD(sltDate, params){
			this.rowData.rcsShortButtons[params.idx].startDate = sltDate;
		},
		fnRcsShortButtonED(sltDate, params){
			this.rowData.rcsShortButtons[params.idx].endDate = sltDate;
		},
		fnRcsShortButtonDel(idx){
			this.rowData.rcsShortButtons.splice(idx, 1);
		},
		fnRcsTallButtonSD(sltDate, params){
			this.rowData.rcsTallButtons[params.idx].startDate = sltDate;
		},
		fnRcsTallButtonED(sltDate, params){
			this.rowData.rcsTallButtons[params.idx].endDate = sltDate;
		},
		fnRcsTallButtonDel(idx){
			this.rowData.rcsTallButtons.splice(idx, 1);
		},
		fnFriendTalkButtonSD(sltDate, params){
			this.rowData.friendTalkButtons[params.idx].startDate = sltDate;
		},
		fnFriendTalkButtonED(sltDate, params){
			this.rowData.friendTalkButtons[params.idx].endDate = sltDate;
		},
		fnRcs90ButtonSD(sltDate, params){
			this.rowData.rcs90Buttons[params.idx].startDate = sltDate;
		},
		fnRcs90ButtonED(sltDate, params){
			this.rowData.rcs90Buttons[params.idx].endDate = sltDate;
		},
		fnRcs91ButtonSD(sltDate, params){
			this.rowData.rcs91Buttons[params.idx].startDate = sltDate;
		},
		fnRcs91ButtonED(sltDate, params){
			this.rowData.rcs91Buttons[params.idx].endDate = sltDate;
		},
		fnRcs92ButtonSD(sltDate, params){
			this.rowData.rcs92Buttons[params.idx].startDate = sltDate;
		},
		fnRcs92ButtonED(sltDate, params){
			this.rowData.rcs92Buttons[params.idx].endDate = sltDate;
		},
		fnRcs93ButtonSD(sltDate, params){
			this.rowData.rcs93Buttons[params.idx].startDate = sltDate;
		},
		fnRcs93ButtonED(sltDate, params){
			this.rowData.rcs93Buttons[params.idx].endDate = sltDate;
		},
		fnRcs94ButtonSD(sltDate, params){
			this.rowData.rcs94Buttons[params.idx].startDate = sltDate;
		},
		fnRcs94ButtonED(sltDate, params){
			this.rowData.rcs94Buttons[params.idx].endDate = sltDate;
		},
		fnRcs95ButtonSD(sltDate, params){
			this.rowData.rcs95Buttons[params.idx].startDate = sltDate;
		},
		fnRcs95ButtonED(sltDate, params){
			this.rowData.rcs95Buttons[params.idx].endDate = sltDate;
		},
		fnRcs100ButtonSD(sltDate, params){
			this.rowData.rcs100Buttons[params.idx].startDate = sltDate;
		},
		fnRcs100ButtonED(sltDate, params){
			this.rowData.rcs100Buttons[params.idx].endDate = sltDate;
		},
		fnRcs101ButtonSD(sltDate, params){
			this.rowData.rcs101Buttons[params.idx].startDate = sltDate;
		},
		fnRcs101ButtonED(sltDate, params){
			this.rowData.rcs101Buttons[params.idx].endDate = sltDate;
		},
		fnRcs102ButtonSD(sltDate, params){
			this.rowData.rcs102Buttons[params.idx].startDate = sltDate;
		},
		fnRcs102ButtonED(sltDate, params){
			this.rowData.rcs102Buttons[params.idx].endDate = sltDate;
		},
		fnRcs103ButtonSD(sltDate, params){
			this.rowData.rcs103Buttons[params.idx].startDate = sltDate;
		},
		fnRcs103ButtonED(sltDate, params){
			this.rowData.rcs103Buttons[params.idx].endDate = sltDate;
		},
		fnRcs104ButtonSD(sltDate, params){
			this.rowData.rcs104Buttons[params.idx].startDate = sltDate;
		},
		fnRcs104ButtonED(sltDate, params){
			this.rowData.rcs104Buttons[params.idx].endDate = sltDate;
		},
		fnRcs105ButtonSD(sltDate, params){
			this.rowData.rcs105Buttons[params.idx].startDate = sltDate;
		},
		fnRcs105ButtonED(sltDate, params){
			this.rowData.rcs105Buttons[params.idx].endDate = sltDate;
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
			if (!this.isEmpty(str)) {
				shortStr = str.toString();
				if (shortStr.length > length) {
					shortStr = shortStr.substring(sIdx, length) + '...  ';
				} else {
					shortStr = shortStr + '  ';
				}
			}
			return shortStr;
		},
		fnTextLength(title, sid, tid, len){
			var val = jQuery(sid).val();
			if (val.length > len) {
				var msg = title + '의 최대 입력 길이는 ' + len + '입니다.';
				alert(msg);
				var temp = val.substr(0,len);
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
				if (nBytes > len){
					var temp = value.substr(0,idx);
					jQuery(sid).val(temp);
					nBytes -= term;
				}
			}
			var text = ""+nBytes+" / "+len+"Byte";
			if(tid) jQuery(tid).html(text);
		},
		checkSmsSend(flag) {
			if (flag === "S") {
				this.smsTemplateTable = 0;
			} else if(flag === "M") {
				this.smsTemplateTable = 1;
			}
			this.rowData.smsTitle = "";
			this.rowData.smsContent = "";
		},
		// 메시지 구분 선택 시 EVENT
		checkMsgKind(flag) {
			if (flag === "A") {
				// 메시지 구분 광고성 선택 시, 카카오톡 상품 친구톡 클릭
				jQuery('input:radio[name=kakao]:input[value="friend"]').click();
				// 메시지 구분 광고성 선택 시, RCS상품이 템플릿 승인형인 경우 프리템플릿으로 초기화
				if (jQuery("input:radio[name=rcsTemplate1]:checked").val() == 0 || jQuery("input:radio[name=rcsTemplate1]:checked").val() == 1) {
					jQuery("input:radio[id=rcsTemplate1-1]").click();
				}
			}
		},
		fnImgReset(){
			this.rowData.rcsShortImgInfoList.push({'fileId':'', 'imgUrl':''});
			this.rowData.rcsTallImgInfoList.push({'fileId':'', 'imgUrl':''});
			this.rowData.rcs90ImgInfoList.push({'fileId':'', 'imgUrl':''});
			this.rowData.rcs91ImgInfoList.push({'fileId':'', 'imgUrl':''});
			this.rowData.rcs92ImgInfoList.push({'fileId':'', 'imgUrl':''});
			this.rowData.rcs93ImgInfoList.push({'fileId':'', 'imgUrl':''});
			this.rowData.rcs94ImgInfoList.push({'fileId':'', 'imgUrl':''});
			this.rowData.rcs95ImgInfoList.push({'fileId':'', 'imgUrl':''});
			this.rowData.rcs100ImgInfoList.push({'fileId':'', 'imgUrl':''});
			this.rowData.rcs101ImgInfoList.push({'fileId':'', 'imgUrl':''});
			this.rowData.rcs102ImgInfoList.push({'fileId':'', 'imgUrl':''});
			this.rowData.rcs103ImgInfoList.push({'fileId':'', 'imgUrl':''});
			this.rowData.rcs104ImgInfoList.push({'fileId':'', 'imgUrl':''});
			this.rowData.rcs105ImgInfoList.push({'fileId':'', 'imgUrl':''});
			this.rowData.smsImgInfoList.push({'fileId':'', 'imgUrl':''});    
		},
		isEmpty(str){
			if (typeof str == "undefined" || str == null || str == "")
				return true;
			else
				return false ;
		}
	}
}
</script>