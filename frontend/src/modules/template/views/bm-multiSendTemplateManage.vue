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
						<span class="txtCaption vertical-middle colorRed">친구톡 광고는 20시~8시 발송이 제한됩니다.</span>
					</div>
				</div>
				<div class="of_h mt15">
					<div class="float-left" style="width:34%"><h5>메시지 타입*</h5></div>
					<div class="float-left" style="width:66%">
						<input type="radio" name="mType" value="BASE" id="mType_text" checked="" v-model="rowData.msgType" v-on:click="checkMsgType('BASE')"><label for="mType_text" class="mr20">텍스트</label>
						<input type="radio" name="mType" value="IMAGE" id="mType_image" v-model="rowData.msgType" v-on:click="checkMsgType('IMAGE')"><label for="mType_image">이미지</label>
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
							<div class="float-left" style="width:100%"><a class="btnStyle1 borderLightGray" style="min-width:auto; width:100%"  @click="putData(index)" :id="channelName(index)">{{channel}}</a></div>
						</div>
					</div>
				</div>
				<div class="of_h consolMarginTop">
					<div class="float-right text-center" style="width:50%">
						<a @click="moveup()" title="위버튼"><i class="far fa-chevron-up channelBtn"></i></a>
						<a @click="movedown()" title="아래버튼"><i class="far fa-chevron-down channelBtn"></i></a>
					</div>
				</div>
				<div class="of_h mt15">
					<div class="float-right" style="width:50%"><a @click="prioritySort()" class="btnStyle1 backBlack" style="min-width:auto; width:100%" title="저가 우선정렬">저가 우선정렬</a></div>
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
						<input type="radio" name="sendP" value="FCM" id="sendP2" v-model="rowData.pushSend"> <label for="sendP2" class="mr30">FCM(안드로이드)</label>
						<input type="radio" name="sendP" value="APNS" id="sendP3" v-model="rowData.pushSend"> <label for="sendP3">APNS(IOS)</label>
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
						<textarea class="textareaStyle height190" :placeholder="pushPlaceHoder" v-model="rowData.pushContent" id="pushContentId" @keyup="fnTextLength('내용', '#pushContentId', '#pushTextLength', '512')"></textarea>
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
								<li><a @click="fnPushDelImg">{{fnSubString(rowData.pushImgInfo.imgUrl, 0, 35)}}  <i v-if="!$gfnCommonUtils.isEmpty(rowData.pushImgInfo.imgUrl)" class="fal fa-times"></i></a></li>
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
				<ul class="templateList">
					<li>
						<img src="../../../common/images/pushTemplate1.svg" alt="프리 템플릿"><h6>프리 템플릿</h6>
						<div class="consolMarginTop">
							<input type="radio" name="rcsTemplate1" value="0" id="rcsTemplate1-1" class="radioStyle" checked="" v-on:click="rcsTemplateTable=0" v-model="rcsTemplateTableChecked">
							<label for="rcsTemplate1-1"></label>
						</div>
					</li>
					<li v-if="rowData.msgKind == 'I'"><!-- 정보성인경우만 사용가능 -->
						<img src="../../../common/images/pushTemplate2.svg" alt="템플릿 승인(서술)"><h6>템플릿 승인<br>(서술)</h6>
						<div class="consolMarginTop">
							<input type="radio" name="rcsTemplate1" value="1" id="rcsTemplate1-2" class="radioStyle" v-on:click="rcsTemplateTable=1" v-model="rcsTemplateTableChecked">
							<label for="rcsTemplate1-2"></label>
						</div>
					</li>
					<li v-if="rowData.msgKind == 'I'"><!-- 정보성인경우만 사용가능 -->
						<img src="../../../common/images/pushTemplate3.svg" alt="템플릿 승인(스타일)"><h6>템플릿 승인<br>(스타일)</h6>
						<div class="consolMarginTop">
							<input type="radio" name="rcsTemplate1" value="2" id="rcsTemplate1-3" class="radioStyle" v-on:click="rcsTemplateTable=2" v-model="rcsTemplateTableChecked">
							<label for="rcsTemplate1-3"></label>
						</div>
					</li>
					<li>
						<img src="../../../common/images/pushTemplate4.svg" alt="SMS"><h6>SMS</h6>
						<div class="consolMarginTop">
							<input type="radio" name="rcsTemplate1" value="3" id="rcsTemplate1-4" class="radioStyle" v-on:click="rcsTemplateTable=3" v-model="rcsTemplateTableChecked">
							<label for="rcsTemplate1-4"></label>
						</div>
					</li>
					<li>
						<img src="../../../common/images/pushTemplate5.svg" alt="LMS"><h6>LMS</h6>
						<div class="consolMarginTop">
							<input type="radio" name="rcsTemplate1" value="4" id="rcsTemplate1-5" class="radioStyle" v-on:click="rcsTemplateTable=4" v-model="rcsTemplateTableChecked">
							<label for="rcsTemplate1-5"></label>
						</div>
					</li>
					<li v-if="rowData.msgType == 'IMAGE'"><!-- 메시지타입이 이미지인 경우만 사용가능 -->
						<img src="../../../common/images/pushTemplate6.svg" alt="세로형(SHORT)"><h6>세로형<br>(SHORT)</h6>
						<div class="consolMarginTop">
							<input type="radio" name="rcsTemplate1" value="5" id="rcsTemplate1-6" class="radioStyle" v-on:click="rcsTemplateTable=5" v-model="rcsTemplateTableChecked">
							<label for="rcsTemplate1-6"></label>
						</div>
					</li>
					<li v-if="rowData.msgType == 'IMAGE'"><!-- 메시지타입이 이미지인 경우만 사용가능 -->
						<img src="../../../common/images/pushTemplate7.svg" alt="세로형(Tall)"><h6>세로형<br>(Tall)</h6>
						<div class="consolMarginTop">
							<input type="radio" name="rcsTemplate1" value="6" id="rcsTemplate1-7" class="radioStyle" v-on:click="rcsTemplateTable=6" v-model="rcsTemplateTableChecked">
							<label for="rcsTemplate1-7"></label>
						</div>
					</li>
					<li>
						<img src="../../../common/images/pushTemplate10.svg" alt="캐러셀(SHORT)"><h6>캐러셀<br>(SHORT)</h6>
						<div class="consolMarginTop">
							<input type="radio" name="rcsTemplate1" value="9" id="rcsTemplate1-10" class="radioStyle" v-on:click="rcsTemplateTable=9" v-model="rcsTemplateTableChecked">
							<label for="rcsTemplate1-10"></label>
						</div>
					</li>
					<li>
						<img src="../../../common/images/pushTemplate11.svg" alt="캐러셀(Tall)"><h6>캐러셀<br>(Tall)</h6>
						<div class="consolMarginTop">
							<input type="radio" name="rcsTemplate1" value="10" id="rcsTemplate1-11" class="radioStyle" v-on:click="rcsTemplateTable=10" v-model="rcsTemplateTableChecked">
							<label for="rcsTemplate1-11"></label>
						</div>
					</li>
				</ul>
				<span id="rcsPrdNoti" class="txtCaption vertical-middle colorRed" style="display:none;">* RCS상품은 수정 할 수 없습니다.</span>
			</div>

			<!-- 프리 템플릿 -->
			<div v-if="rcsTemplateTable === 0">
				<h4>내용작성</h4>
				<div class="of_h mt20">
					<div class="float-left" style="width:28%">
						<!-- phoneWrap -->
						<div class="phoneWrap">
							<img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
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
							<select class="selectStyle2" v-model="rowData.brandNm" @change="fnChgBrandValue(rowData.brandNm)" style="width:24%" title="브랜드명 선택란">
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
							<div class="float-left" style="width:13%"><h4>발신번호 *</h4></div>
							<div class="float-left" style="width:57%">
								<select v-model="rowData.callback" class="selectStyle2 float-right" style="width:100%">
									<option value="">선택해주세요.</option>
									<option v-for="info in rcsCallbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
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
							<img src="@/assets/images/common/phoneMockup1.svg" alt="서술형 템플릿">
							<div class="phoneTextWrap">
								<div class="phoneText1 relative scroll-y4">
									<p><img :src="rowData.rcsDesFormNm | getIconURlByFormNm" style="width:70px;"></p>
									<div class="scroll-y5">
										<pre class="mt15 lc-1">{{rowData.rcs0Content}}</pre>
									</div>
									<div v-for="(buttonInfo, idx) in rowData.rcsDesButtons" :key="idx">
										<p v-if="idx == 0" class="text-center mt30" style="color:#69C8FF">
											<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
										</p>
										<p v-if="idx != 0" class="text-center mt10" style="color:#69C8FF">
											<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
										</p>
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
									<textarea class="textareaStyle height190"  v-model="rowData.rcs0Content" disabled></textarea>
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
											<tr v-for="(buttonInfo, index) in rowData.rcsDesButtons" v-bind:key="index">
												<td class="text-center">
													<select class="selectStyle2" style="width:100%" v-model="buttonInfo.action.linkType" disabled>
														<option v-for="rcsButtonType in rcsButtonTypeList" :key="rcsButtonType.type" :value="rcsButtonType.type">{{rcsButtonType.name}}</option>
													</select>
												</td>
												<td class="text-left"><input v-model="buttonInfo.action.displayText" type="text" class="inputStyle" disabled></td>
												<td v-if="buttonInfo.action.linkType=='urlAction'" class="text-center"><input v-model="buttonInfo.action.urlAction.openUrl.url" type="text" class="inputStyle" disabled></td>
												<td v-if="buttonInfo.action.linkType=='clipboardAction'" class="text-center"><input v-model="buttonInfo.action.clipboardAction.copyToClipboard.text" type="text" class="inputStyle" disabled></td>
												<td v-if="buttonInfo.action.linkType=='dialerAction'" class="text-center"><input v-model="buttonInfo.action.dialerAction.dialPhoneNumber.phoneNumber" type="text" class="inputStyle" disabled></td>
												<td v-if="buttonInfo.action.linkType=='calendarAction'" class="text-center">
													<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.title" type="text" class="inputStyle" placeholder="제목입력" disabled>
													<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.description" type="text" class="inputStyle consolMarginTop" placeholder="내용입력" disabled>
													<div class="consolMarginTop of_h">
														<span class="float-left mt5" style="width:20%">시작일</span>
														<div class="float-right" style="width:80%">
															<Calendar classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.startTime"></Calendar>
														</div>
													</div>
													<div class="consolMarginTop of_h">
														<span class="float-left mt5" style="width:20%">종료일</span>
														<div class="float-right" style="width:80%">
															<Calendar classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.endTime"></Calendar>
														</div>
													</div>
												</td>
												<td v-if="buttonInfo.action.linkType=='mapAction'" class="text-center"><input v-model="buttonInfo.action.mapAction.requestLocationPush" type="text" class="inputStyle" disabled></td>
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
										<option v-for="info in rcsCallbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
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
							<img src="@/assets/images/common/phoneMockup1.svg" alt="스타일형 템플릿">
							<div class="phoneTextWrap">
								<div class="phoneText1 of_h">
									<p><img :src="rowData.rcsStyleFormNm | getIconURlByFormNm" style="width:70px;"></p>
									<div v-for="n in rowData.rcsStyleContentCnt" class="of_h consolMarginTop">
										<pre class="mt15 lc-1">{{rowData.rcsStyleInput[n-1]}}</pre>
										<pre class="float-right">{{rowData.rcsStyleInputSec[n-1]}}</pre>
										<hr v-if="rowData.rcsStyleChk[n-1]">
									</div>
									<div v-for="(buttonInfo, idx) in rowData.rcsStyleButtons" :key="idx">
										<p v-if="idx == 0" class="text-center mt30" style="color:#69C8FF">
											<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
										</p>
										<p v-if="idx != 0" class="text-center mt10" style="color:#69C8FF">
											<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
										</p>
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
						<div v-if="selectedRcsStyTemplate">
							<div class="of_h mt20">
								<div class="float-left" style="width:13%"><h4>브랜드명</h4></div>
								<select class="selectStyle2" v-model="rowData.brandNm" style="width:24%" title="브랜드명 선택란" disabled>
									<option v-for="option in brandNmList" v-bind:value="option.BRAND_ID">{{option.BRAND_NAME}}</option>
								</select>
							</div>
							<div class="of_h mt20">						
								<div class="float-left" style="width:13%"><h4>유형</h4></div>
								<select class="selectStyle2" v-model="rowData.rcsStyleMessagebaseformId" title="유형 선택란" disabled>
									<option v-for="rcsType in rcsStyleFormNmList" v-bind:value="rcsType.MESSAGEBASEFORM_ID">{{rcsType.FORM_NAME}}</option>
								</select>
							</div>
							<div class="of_h">
								<div class="float-left" style="width:13%"><h4>내용</h4></div>
								<div class="float-left of_h consolMarginTop" style="width:57%" v-for="n in rowData.rcsStyleContentCnt">
									<input v-if="rowData.rcsStyleChk[n-1] == true" type="text" class="inputStyle" v-model="rowData.rcsStyleInput[n-1]" disabled>
									<input v-if="rowData.rcsStyleChk[n-1] == false" type="text" class="inputStyle" style="width:49%" v-model="rowData.rcsStyleInput[n-1]" disabled>
									<input v-if="rowData.rcsStyleChk[n-1] == false" type="text" class="inputStyle float-right" style="width:49%" v-model="rowData.rcsStyleInputSec[n-1]" disabled>
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
											<tr v-for="(buttonInfo, index) in rowData.rcsStyleButtons" v-bind:key="index">
												<td class="text-center">
													<select class="selectStyle2" style="width:100%" v-model="buttonInfo.action.linkType" disabled>
														<option v-for="rcsButtonType in rcsButtonTypeList" :key="rcsButtonType.type" :value="rcsButtonType.type">{{rcsButtonType.name}}</option>
													</select>
												</td>
												<td class="text-left"><input v-model="buttonInfo.action.displayText" type="text" class="inputStyle" disabled></td>
												<td v-if="buttonInfo.action.linkType=='urlAction'" class="text-center"><input v-model="buttonInfo.action.urlAction.openUrl.url" type="text" class="inputStyle" disabled></td>
												<td v-if="buttonInfo.action.linkType=='clipboardAction'" class="text-center"><input v-model="buttonInfo.action.clipboardAction.copyToClipboard.text" type="text" class="inputStyle" disabled></td>
												<td v-if="buttonInfo.action.linkType=='dialerAction'" class="text-center"><input v-model="buttonInfo.action.dialerAction.dialPhoneNumber.phoneNumber" type="text" class="inputStyle" disabled></td>
												<td v-if="buttonInfo.action.linkType=='calendarAction'" class="text-center">
													<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.title" type="text" class="inputStyle" placeholder="제목입력" disabled>
													<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.description" type="text" class="inputStyle consolMarginTop" placeholder="내용입력" disabled>
													<div class="consolMarginTop of_h">
														<span class="float-left mt5" style="width:20%">시작일</span>
														<div class="float-right" style="width:80%">
															<Calendar classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.startTime"></Calendar>
														</div>
													</div>
													<div class="consolMarginTop of_h">
														<span class="float-left mt5" style="width:20%">종료일</span>
														<div class="float-right" style="width:80%">
															<Calendar classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.endTime"></Calendar>
														</div>
													</div>
												</td>
												<td v-if="buttonInfo.action.linkType=='mapAction'" class="text-center"><input v-model="buttonInfo.action.mapAction.requestLocationPush" type="text" class="inputStyle" disabled></td>
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
										<option v-for="info in rcsCallbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
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

			<!-- SMS -->
			<div v-if="rcsTemplateTable === 3 ">
				<h4>내용작성</h4>
				<div class="of_h mt20">
					<div class="float-left" style="width:28%">
						<!-- phoneWrap -->
						<div class="phoneWrap">
							<img src="@/assets/images/common/phoneMockup1.svg" alt="SMS 템플릿">
							<div class="phoneTextWrap">
								<div class="phoneText1 scroll-y2">
									<pre>{{rowData.rcs0Content}}</pre>
									<br v-if="!fnIsEmpty(rowData.rcs0Content)"/>
									<span v-if="rowData.msgKind == 'A' && !fnIsEmpty(rowData.rcsBlockNumber)">
										{{rowData.rcsBlockNumber}}
									</span>
									<div v-for="(buttonInfo, idx) in rowData.rcsSMSButtons" :key="idx">
										<p v-if="idx == 0" class="text-center mt30" style="color:#69C8FF">
											<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
										</p>
										<p v-if="idx != 0" class="text-center mt10" style="color:#69C8FF">
											<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
										</p>
									</div>
								</div>
							</div>
						</div>
						<!-- //phoneWrap -->
					</div>
					<div class="float-left consoleCon" style="width:72%">
						<div class="of_h">
							<div class="float-left" style="width:13%"><h4>브랜드명*</h4></div>
							<select class="selectStyle2" v-model="rowData.brandNm" @change="fnChgBrandValue(rowData.brandNm)" style="width:24%" title="브랜드명 선택란">
								<option value="">선택해주세요.</option>
								<option v-for="option in brandNmList" v-bind:value="option.BRAND_ID">{{option.BRAND_NAME}}</option>
							</select>
						</div>
						<div class="of_h">
							<div class="float-left" style="width:13%"><h4>내용*</h4></div>
							<div class="float-left" style="width:57%">
								<textarea class="textareaStyle height190" maxlength="100" v-model="rowData.rcs0Content" :placeholder="rcsPlaceHoder" id="rcsSMSContentId" @keyup="fnTextLength('내용', '#rcsSMSContentId', '#rcsSMSTextLength', '1300')"></textarea>
								<strong class="letter" id="rcsSMSTextLength">(00 / 1300)</strong>
							</div>
						</div>
						<div class="of_h consolMarginTop" v-if="rowData.msgKind == 'A'">
							<div class="float-left" style="width:13%"><h4>무료수신거부 *</h4></div>
							<div class="float-left" style="width:57%">
								<input type="text" class="inputStyle" v-model="rowData.rcsBlockNumber" placeholder="수신거부번호 설정">
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRowRcsButton('SMS')" class="btnStyle1 backBlack">추가 +</a></div>
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
										<tr v-for="(buttonInfo, index) in rowData.rcsSMSButtons" v-bind:key="index">
											<td class="text-center">
												<select class="selectStyle2" style="width:100%" v-model="buttonInfo.action.linkType">
													<option v-for="rcsButtonType in rcsButtonTypeList" :key="rcsButtonType.type" :value="rcsButtonType.type">{{rcsButtonType.name}}</option>
												</select>
											</td>
											<td class="text-left"><input v-model="buttonInfo.action.displayText" type="text" class="inputStyle"></td>
											<td v-if="buttonInfo.action.linkType=='urlAction'" class="text-center"><input v-model="buttonInfo.action.urlAction.openUrl.url" type="text" class="inputStyle"></td>
											<td v-if="buttonInfo.action.linkType=='clipboardAction'" class="text-center"><input v-model="buttonInfo.action.clipboardAction.copyToClipboard.text" type="text" class="inputStyle"></td>
											<td v-if="buttonInfo.action.linkType=='dialerAction'" class="text-center"><input v-model="buttonInfo.action.dialerAction.dialPhoneNumber.phoneNumber" type="text" class="inputStyle"></td>
											<td v-if="buttonInfo.action.linkType=='calendarAction'" class="text-center">
												<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.title" type="text" class="inputStyle" placeholder="제목입력">
												<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.description" type="text" class="inputStyle consolMarginTop" placeholder="내용입력">
												<div class="consolMarginTop of_h">
													<span class="float-left mt5" style="width:20%">시작일</span>
													<div class="float-right" style="width:80%">
														<Calendar @update-date="fnRcsSmsButtonSD" :calendarId="fnCalendarStartDateId(index)" classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.startTime"></Calendar>
													</div>
												</div>
												<div class="consolMarginTop of_h">
													<span class="float-left mt5" style="width:20%">종료일</span>
													<div class="float-right" style="width:80%">
														<Calendar @update-date="fnRcsSmsButtonED" :calendarId="fnCalendarEndDateId(index)" classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.endTime"></Calendar>
													</div>
												</div>
											</td>
											<td v-if="buttonInfo.action.linkType=='mapAction'" class="text-center"><input v-model="buttonInfo.action.mapAction.requestLocationPush" type="text" class="inputStyle"></td>
											<td class="text-center end"><a @click="addRowRcsButton('SMS')" title="추가버튼"><i class="far fa-plus channelBtn"></i></a> <a @click="removeRowRcsButton('SMS', index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a></td>
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
									<option v-for="info in rcsCallbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
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
							<img src="@/assets/images/common/phoneMockup1.svg" alt="LMS 템플릿">
							<div class="phoneTextWrap">
								<div class="phoneText1">
									<h5>{{rowData.rcs0Title}}</h5>
									<div class="scroll-y">
										<pre class="color6">{{rowData.rcs0Content}}</pre>
									</div>
									<br v-if="!fnIsEmpty(rowData.rcs0Content)"/>
									<span v-if="rowData.msgKind == 'A' && !fnIsEmpty(rowData.rcsBlockNumber)">
										{{rowData.rcsBlockNumber}}
									</span>
									<div v-for="(buttonInfo, idx) in rowData.rcsLMSButtons" :key="idx">
										<p v-if="idx == 0" class="text-center mt30" style="color:#69C8FF">
											<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
										</p>
										<p v-if="idx != 0" class="text-center mt10" style="color:#69C8FF">
											<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
										</p>
									</div>
								</div>
							</div>
						</div>
						<!-- //phoneWrap -->
					</div>
					<div class="float-left consoleCon" style="width:72%">
						<div class="of_h">
							<div class="float-left" style="width:13%"><h4>브랜드명*</h4></div>
							<select class="selectStyle2" v-model="rowData.brandNm" @change="fnChgBrandValue(rowData.brandNm)" style="width:24%" title="브랜드명 선택란">
								<option value="">선택해주세요.</option>
								<option v-for="option in brandNmList" v-bind:value="option.BRAND_ID">{{option.BRAND_NAME}}</option>
							</select>
						</div>
						<div class="of_h">
							<div class="float-left" style="width:13%"><h4>제목*</h4></div>
							<div class="float-left" style="width:57%">
								<input type="text" class="inputStyle" v-model="rowData.rcs0Title" placeholder="최대 30자 입력 가능니다." maxlength="30">
							</div>
						</div>
						<div class="of_h">
							<div class="float-left" style="width:13%"><h4>내용*</h4></div>
							<div class="float-left" style="width:57%">
								<textarea class="textareaStyle height190" maxlength="1300" v-model="rowData.rcs0Content" :placeholder="rcsPlaceHoder" id="rcsLMSContentId" @keyup="fnTextLength('내용', '#rcsLMSContentId', '#rcsLMSTextLength', '1300')"></textarea>
								<strong class="letter" id="rcsLMSTextLength">(00 / 1300)</strong>
							</div>
						</div>
						<div class="of_h consolMarginTop" v-if="rowData.msgKind == 'A'">
							<div class="float-left" style="width:13%"><h4>무료수신거부 *</h4></div>
							<div class="float-left" style="width:57%">
								<input type="text" class="inputStyle" v-model="rowData.rcsBlockNumber" placeholder="수신거부번호 설정">
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRowRcsButton('LMS')" class="btnStyle1 backBlack">추가 +</a></div>
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
										<tr v-for="(buttonInfo, index) in rowData.rcsLMSButtons" v-bind:key="index">
											<td class="text-center">
												<select class="selectStyle2" style="width:100%" v-model="buttonInfo.action.linkType">
													<option v-for="rcsButtonType in rcsButtonTypeList" :key="rcsButtonType.type" :value="rcsButtonType.type">{{rcsButtonType.name}}</option>
												</select>
											</td>
											<td class="text-left"><input v-model="buttonInfo.action.displayText" type="text" class="inputStyle"></td>
											<td v-if="buttonInfo.action.linkType=='urlAction'" class="text-center"><input v-model="buttonInfo.action.urlAction.openUrl.url" type="text" class="inputStyle"></td>
											<td v-if="buttonInfo.action.linkType=='clipboardAction'" class="text-center"><input v-model="buttonInfo.action.clipboardAction.copyToClipboard.text" type="text" class="inputStyle"></td>
											<td v-if="buttonInfo.action.linkType=='dialerAction'" class="text-center"><input v-model="buttonInfo.action.dialerAction.dialPhoneNumber.phoneNumber" type="text" class="inputStyle"></td>
											<td v-if="buttonInfo.action.linkType=='calendarAction'" class="text-center">
												<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.title" type="text" class="inputStyle" placeholder="제목입력">
												<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.description" type="text" class="inputStyle consolMarginTop" placeholder="내용입력">
												<div class="consolMarginTop of_h">
													<span class="float-left mt5" style="width:20%">시작일</span>
													<div class="float-right" style="width:80%">
														<Calendar @update-date="fnRcsLmsButtonSD" :calendarId="fnCalendarStartDateId(index)" classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.startTime"></Calendar>
													</div>
												</div>
												<div class="consolMarginTop of_h">
													<span class="float-left mt5" style="width:20%">종료일</span>
													<div class="float-right" style="width:80%">
														<Calendar @update-date="fnRcsLmsButtonED" :calendarId="fnCalendarEndDateId(index)"classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.endTime"></Calendar>
													</div>
												</div>
											</td>
											<td v-if="buttonInfo.action.linkType=='mapAction'" class="text-center"><input v-model="buttonInfo.action.mapAction.requestLocationPush" type="text" class="inputStyle"></td>
											<td class="text-center end"><a @click="addRowRcsButton('LMS')" title="추가버튼"><i class="far fa-plus channelBtn"></i></a> <a @click="removeRowRcsButton('LMS', index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a></td>
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
									<option v-for="info in rcsCallbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
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
							<img src="@/assets/images/common/phoneMockup1.svg" alt="세로형(short) 템플릿">
							<div class="phoneTextWrap">
								<div v-for="imgIdx in rcsShortImgLimitSize" :key="imgIdx">  
									<div v-if="rowData.msgType == 'IMAGE' && rowData.rcsShortImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
										:style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcsShortImgInfoList[imgIdx-1].imgUrl+');'">  
									</div>
									<div v-else>
										<img src="@/assets/images/common/cardThumImg2_2.png" alt="카드 썸네일">
									</div>
								</div>
								<div style="background:#fff; border-radius: 0 0 5px 5px; min-height:180px" class="pd20">
									<h5>{{rowData.rcs0Title}}</h5>
									<div class="scroll-y3">
										<pre class="color6">{{rowData.rcs0Content}}</pre>
									</div>
									<br v-if="!fnIsEmpty(rowData.rcs0Content)"/>
									<span v-if="rowData.msgKind == 'A' && !fnIsEmpty(rowData.rcsBlockNumber)">
										{{rowData.rcsBlockNumber}}
									</span>
									<div v-for="(buttonInfo, idx) in rowData.rcsShortButtons" :key="idx">
										<p v-if="idx == 0" class="text-center mt30" style="color:#69C8FF">
											<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
										</p>
										<p v-if="idx != 0" class="text-center mt10" style="color:#69C8FF">
											<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
										</p>
									</div>
								</div>
							</div>
						</div>
						<!-- //phoneWrap -->
					</div>
					<div class="float-left consoleCon" style="width:72%">
						<div class="of_h">
							<div class="float-left" style="width:13%"><h4>브랜드명*</h4></div>
							<select class="selectStyle2" v-model="rowData.brandNm" @change="fnChgBrandValue(rowData.brandNm)" style="width:24%" title="브랜드명 선택란">
								<option value="">선택해주세요.</option>
								<option v-for="option in brandNmList" v-bind:value="option.BRAND_ID">{{option.BRAND_NAME}}</option>
							</select>
						</div>
						<div class="of_h">
							<div class="float-left" style="width:13%"><h4>제목</h4></div>
							<div class="float-left" style="width:57%">
								<input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcs0Title">
							</div>
						</div>
						<div class="of_h">
							<div class="float-left" style="width:13%"><h4>내용*</h4></div>
							<div class="float-left" style="width:57%">
								<textarea class="textareaStyle height190" v-model="rowData.rcs0Content" :placeholder="rcsPlaceHoder" id="rcsShortContentId" @keyup="fnTextLength('내용', '#rcsShortContentId', '#rcsShortTextLength', '1300')"></textarea>
								<strong class="letter" id="rcsShortTextLength">(00 / 1300)</strong>
							</div>
						</div>
						<div class="of_h consolMarginTop">
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
						<div class="of_h consolMarginTop" v-if="rowData.msgKind == 'A'">
							<div class="float-left" style="width:13%"><h4>무료수신거부 *</h4></div>
							<div class="float-left" style="width:57%">
								<input type="text" class="inputStyle" v-model="rowData.rcsBlockNumber" placeholder="수신거부번호 설정">
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRowRcsButton('SHORT')" class="btnStyle1 backBlack">추가 +</a></div>
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
										<tr v-for="(buttonInfo, index) in rowData.rcsShortButtons" v-bind:key="index">
											<td class="text-center">
												<select class="selectStyle2" style="width:100%" v-model="buttonInfo.action.linkType">
													<option v-for="rcsButtonType in rcsButtonTypeList" :key="rcsButtonType.type" :value="rcsButtonType.type">{{rcsButtonType.name}}</option>
												</select>
											</td>
											<td class="text-left"><input v-model="buttonInfo.action.displayText" type="text" class="inputStyle"></td>
											<td v-if="buttonInfo.action.linkType=='urlAction'" class="text-center"><input v-model="buttonInfo.action.urlAction.openUrl.url" type="text" class="inputStyle"></td>
											<td v-if="buttonInfo.action.linkType=='clipboardAction'" class="text-center"><input v-model="buttonInfo.action.clipboardAction.copyToClipboard.text" type="text" class="inputStyle"></td>
											<td v-if="buttonInfo.action.linkType=='dialerAction'" class="text-center"><input v-model="buttonInfo.action.dialerAction.dialPhoneNumber.phoneNumber" type="text" class="inputStyle"></td>
											<td v-if="buttonInfo.action.linkType=='calendarAction'" class="text-center">
												<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.title" type="text" class="inputStyle" placeholder="제목입력">
												<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.description" type="text" class="inputStyle consolMarginTop" placeholder="내용입력">
												<div class="consolMarginTop of_h">
													<span class="float-left mt5" style="width:20%">시작일</span>
													<div class="float-right" style="width:80%">
														<Calendar @update-date="fnRcsShortButtonSD" :calendarId="fnCalendarStartDateId(index)" classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.startTime"></Calendar>
													</div>
												</div>
												<div class="consolMarginTop of_h">
													<span class="float-left mt5" style="width:20%">종료일</span>
													<div class="float-right" style="width:80%">
														<Calendar @update-date="fnRcsShortButtonED" :calendarId="fnCalendarEndDateId(index)" classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.endTime"></Calendar>
													</div>
												</div>
											</td>
											<td v-if="buttonInfo.action.linkType=='mapAction'" class="text-center"><input v-model="buttonInfo.action.mapAction.requestLocationPush" type="text" class="inputStyle"></td>
											<td class="text-center end"><a @click="addRowRcsButton('SHORT')" title="추가버튼"><i class="far fa-plus channelBtn"></i></a> <a @click="removeRowRcsButton('SHORT', index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a></td>
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
									<option v-for="info in rcsCallbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
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
							<img src="@/assets/images/common/phoneMockup1.svg" alt="세로형(Tall) 템플릿">
							<div class="phoneTextWrap">
								<div v-for="imgIdx in rcsTallImgLimitSize" :key="imgIdx">  
									<div v-if="rowData.msgType == 'IMAGE' && rowData.rcsTallImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
										:style="'padding:120px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcsTallImgInfoList[imgIdx-1].imgUrl+');'">  
									</div>
									<div v-else>
										<img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
									</div>
								</div>
								<div style="background:#fff; border-radius: 0 0 5px 5px; min-height:170px" class="pd20">
									<h5>{{rowData.rcs0Title}}</h5>
									<div class="scroll-y6">
										<pre class="color6">{{rowData.rcs0Content}}</pre>
									</div>
									<br v-if="!fnIsEmpty(rowData.rcs0Content)"/>
									<span v-if="rowData.msgKind == 'A' && !fnIsEmpty(rowData.rcsBlockNumber)">
										{{rowData.rcsBlockNumber}}
									</span>
									<div v-for="(buttonInfo, idx) in rowData.rcsTallButtons" :key="idx">
										<p v-if="idx == 0" class="text-center mt30" style="color:#69C8FF">
											<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
										</p>
										<p v-if="idx != 0" class="text-center mt10" style="color:#69C8FF">
											<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
										</p>
									</div>
								</div>
							</div>
						</div>
						<!-- //phoneWrap -->
					</div>
					<div class="float-left consoleCon" style="width:72%">
						<div class="of_h">
							<div class="float-left" style="width:13%"><h4>브랜드명*</h4></div>
							<select class="selectStyle2" v-model="rowData.brandNm" @change="fnChgBrandValue(rowData.brandNm)" style="width:24%" title="브랜드명 선택란">
								<option value="">선택해주세요.</option>
								<option v-for="option in brandNmList" v-bind:value="option.BRAND_ID">{{option.BRAND_NAME}}</option>
							</select>
						</div>
						<div class="of_h">
							<div class="float-left" style="width:13%"><h4>제목</h4></div>
							<div class="float-left" style="width:57%">
								<input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcs0Title">
							</div>
						</div>
						<div class="of_h">
							<div class="float-left" style="width:13%"><h4>내용*</h4></div>
							<div class="float-left" style="width:57%">
								<textarea class="textareaStyle height190" v-model="rowData.rcs0Content" :placeholder="rcsPlaceHoder" id="rcsTallContentId" @keyup="fnTextLength('내용', '#rcsTallContentId', '#rcsTallTextLength', '1300')"></textarea>
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
						<div class="of_h consolMarginTop" v-if="rowData.msgKind == 'A'">
							<div class="float-left" style="width:13%"><h4>무료수신거부 *</h4></div>
							<div class="float-left" style="width:57%">
								<input type="text" class="inputStyle" v-model="rowData.rcsBlockNumber" placeholder="수신거부번호 설정">
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRowRcsButton('TALL')" class="btnStyle1 backBlack">추가 +</a></div>
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
										<tr v-for="(buttonInfo, index) in rowData.rcsTallButtons" v-bind:key="index">
											<td class="text-center">
												<select class="selectStyle2" style="width:100%" v-model="buttonInfo.action.linkType">
													<option v-for="rcsButtonType in rcsButtonTypeList" :key="rcsButtonType.type" :value="rcsButtonType.type">{{rcsButtonType.name}}</option>
												</select>
											</td>
											<td class="text-left"><input v-model="buttonInfo.action.displayText" type="text" class="inputStyle"></td>
											<td v-if="buttonInfo.action.linkType=='urlAction'" class="text-center"><input v-model="buttonInfo.action.urlAction.openUrl.url" type="text" class="inputStyle"></td>
											<td v-if="buttonInfo.action.linkType=='clipboardAction'" class="text-center"><input v-model="buttonInfo.action.clipboardAction.copyToClipboard.text" type="text" class="inputStyle"></td>
											<td v-if="buttonInfo.action.linkType=='dialerAction'" class="text-center"><input v-model="buttonInfo.action.dialerAction.dialPhoneNumber.phoneNumber" type="text" class="inputStyle"></td>
											<td v-if="buttonInfo.action.linkType=='calendarAction'" class="text-center">
												<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.title" type="text" class="inputStyle" placeholder="제목입력">
												<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.description" type="text" class="inputStyle consolMarginTop" placeholder="내용입력">
												<div class="consolMarginTop of_h">
													<span class="float-left mt5" style="width:20%">시작일</span>
													<div class="float-right" style="width:80%">
														<Calendar @update-date="fnRcsTallButtonSD" :calendarId="fnCalendarStartDateId(index)" classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.startTime"></Calendar>
													</div>
												</div>
												<div class="consolMarginTop of_h">
													<span class="float-left mt5" style="width:20%">종료일</span>
													<div class="float-right" style="width:80%">
														<Calendar @update-date="fnRcsTallButtonED" :calendarId="fnCalendarEndDateId(index)" classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.endTime"></Calendar>
													</div>
												</div>
											</td>
											<td v-if="buttonInfo.action.linkType=='mapAction'" class="text-center"><input v-model="buttonInfo.action.mapAction.requestLocationPush" type="text" class="inputStyle"></td>
											<td class="text-center end"><a @click="addRowRcsButton('TALL')" title="추가버튼"><i class="far fa-plus channelBtn"></i></a> <a @click="removeRowRcsButton('TALL', index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a></td>
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
									<option v-for="info in rcsCallbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
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
					<div class="float-left" style="width:28%">
						<div class="cardBxsliderWrap">
							<!-- phoneWrap -->
							<div class="phoneWrap" style="height:660px">
								<img src="@/assets/images/common/phoneMockup1.svg" alt="캐러셀(short) 템플릿">
								<div class="phoneCardWrap">
									<p class="color000">
										<span>[WEB발신]</span>
										<span v-if="rowData.msgKind == 'A'"> (광고)</span>
									</p>
									<ul class="cardBxslider mt10">
										<li class="slide cardBox">
											<div v-for="imgIdx in rcs90ImgLimitSize" :key="imgIdx">
												<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs90ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
													:style="'padding:25%;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs90ImgInfoList[imgIdx-1].imgUrl+');'">  
												</div>
												<div v-else-if="rowData.msgType == 'IMAGE' && rowData.rcs90ImgInfoList.length == 0">
													<img src="@/assets/images/common/cardThumImg2.png" alt="카드 썸네일">
												</div>
											</div>
											<div class="relative">
												<h5>{{rowData.rcs0Title}}</h5>
												<div class="scroll-y" style="min-height:150px">
													<pre class="color6">{{rowData.rcs0Content}}</pre>
												</div>
												<p v-if="rowData.msgKind == 'A' && !fnIsEmpty(rowData.rcsBlockNumber)" class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcsBlockNumber}}</p>
												<div v-for="(buttonInfo, idx) in rowData.rcs90Buttons" :key="idx">
													<p v-if="idx == 0" class="text-center mt30" style="color:#69C8FF">
														<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
													</p>
													<p v-if="idx != 0" class="text-center mt10" style="color:#69C8FF">
														<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
													</p>
												</div>
											</div>
										</li>
										<li class="slide cardBox">
											<div v-for="imgIdx in rcs91ImgLimitSize" :key="imgIdx">
												<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs91ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
													:style="'padding:25%;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs91ImgInfoList[imgIdx-1].imgUrl+');'">  
												</div>
												<div v-else-if="rowData.msgType == 'IMAGE' && rowData.rcs91ImgInfoList.length == 0">
													<img src="@/assets/images/common/cardThumImg2.png" alt="카드 썸네일">
												</div>
											</div>
											<div class="relative">
												<h5>{{rowData.rcs1Title}}</h5>
												<div class="scroll-y" style="min-height:150px">
													<pre class="color6">{{rowData.rcs1Content}}</pre>
												</div>
												<p v-if="rowData.msgKind == 'A' && !fnIsEmpty(rowData.rcsBlockNumber)" class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcsBlockNumber}}</p>
												<div v-for="(buttonInfo, idx) in rowData.rcs91Buttons" :key="idx">
													<p v-if="idx == 0" class="text-center mt30" style="color:#69C8FF">
														<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
													</p>
													<p v-if="idx != 0" class="text-center mt10" style="color:#69C8FF">
														<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
													</p>
												</div>
											</div>
										</li>
										<li class="slide cardBox">
											<div v-for="imgIdx in rcs92ImgLimitSize" :key="imgIdx">
												<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs92ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
													:style="'padding:25%;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs92ImgInfoList[imgIdx-1].imgUrl+');'">  
												</div>
												<div v-else-if="rowData.msgType == 'IMAGE' && rowData.rcs92ImgInfoList.length == 0">
													<img src="@/assets/images/common/cardThumImg2.png" alt="카드 썸네일">
												</div>
											</div>
											<div class="relative">
												<h5>{{rowData.rcs2Title}}</h5>
												<div class="scroll-y" style="min-height:150px">
													<pre class="color6">{{rowData.rcs2Content}}</pre>
												</div>
												<p v-if="rowData.msgKind == 'A' && !fnIsEmpty(rowData.rcsBlockNumber)" class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcsBlockNumber}}</p>
												<div v-for="(buttonInfo, idx) in rowData.rcs92Buttons" :key="idx">
													<p v-if="idx == 0" class="text-center mt30" style="color:#69C8FF">
														<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
													</p>
													<p v-if="idx != 0" class="text-center mt10" style="color:#69C8FF">
														<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
													</p>
												</div>
											</div>
										</li>
										<li class="slide cardBox" v-show="cShortTab === 3">
											<div v-for="imgIdx in rcs93ImgLimitSize" :key="imgIdx">
												<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs93ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
													:style="'padding:25%;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs93ImgInfoList[imgIdx-1].imgUrl+');'">  
												</div>
												<div v-else-if="rowData.msgType == 'IMAGE' && rowData.rcs93ImgInfoList.length == 0">
													<img src="@/assets/images/common/cardThumImg2.png" alt="카드 썸네일">
												</div>
											</div>
											<div class="relative">
												<h5>{{rowData.rcs3Title}}</h5>
												<div class="scroll-y" style="min-height:150px">
													<pre class="color6">{{rowData.rcs3Content}}</pre>
												</div>
												<p v-if="rowData.msgKind == 'A' && !fnIsEmpty(rowData.rcsBlockNumber)" class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcsBlockNumber}}</p>
												<div v-for="(buttonInfo, idx) in rowData.rcs93Buttons" :key="idx">
													<p v-if="idx == 0" class="text-center mt30" style="color:#69C8FF">
														<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
													</p>
													<p v-if="idx != 0" class="text-center mt10" style="color:#69C8FF">
														<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
													</p>
												</div>
											</div>
										</li>
										<li class="slide cardBox" v-show="cShortTab === 4">
											<div v-for="imgIdx in rcs94ImgLimitSize" :key="imgIdx">
												<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs94ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
													:style="'padding:25%;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs94ImgInfoList[imgIdx-1].imgUrl+');'">  
												</div>
												<div v-else-if="rowData.msgType == 'IMAGE' && rowData.rcs94ImgInfoList.length == 0">
													<img src="@/assets/images/common/cardThumImg2.png" alt="카드 썸네일">
												</div>
											</div>
											<div class="relative">
												<h5>{{rowData.rcs4Title}}</h5>
												<div class="scroll-y" style="min-height:150px">
													<pre class="color6">{{rowData.rcs4Content}}</pre>
												</div>
												<p v-if="rowData.msgKind == 'A' && !fnIsEmpty(rowData.rcsBlockNumber)" class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcsBlockNumber}}</p>
												<div v-for="(buttonInfo, idx) in rowData.rcs94Buttons" :key="idx">
													<p v-if="idx == 0" class="text-center mt30" style="color:#69C8FF">
														<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
													</p>
													<p v-if="idx != 0" class="text-center mt10" style="color:#69C8FF">
														<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
													</p>
												</div>
											</div>
										</li>
										<li class="slide cardBox" v-show="cShortTab === 5">
											<div v-for="imgIdx in rcs95ImgLimitSize" :key="imgIdx">
												<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs95ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
													:style="'padding:25%;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs95ImgInfoList[imgIdx-1].imgUrl+');'">  
												</div>
												<div v-else-if="rowData.msgType == 'IMAGE' && rowData.rcs95ImgInfoList.length == 0">
													<img src="@/assets/images/common/cardThumImg2.png" alt="카드 썸네일">
												</div>
											</div>
											<div class="relative">
												<h5>{{rowData.rcs5Title}}</h5>
												<div class="scroll-y" style="min-height:150px">
													<pre class="color6">{{rowData.rcs5Content}}</pre>
												</div>
												<p v-if="rowData.msgKind == 'A' && !fnIsEmpty(rowData.rcsBlockNumber)" class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcsBlockNumber}}</p>
												<div v-for="(buttonInfo, idx) in rowData.rcs95Buttons" :key="idx">
													<p v-if="idx == 0" class="text-center mt30" style="color:#69C8FF">
														<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
													</p>
													<p v-if="idx != 0" class="text-center mt10" style="color:#69C8FF">
														<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
													</p>
												</div>
											</div>
										</li>
									</ul>
								</div>
							</div>
							<!-- //phoneWrap -->
						</div>
					</div>
					<div class="float-left consoleCon" style="width:72%">
						<div class="of_h">
							<div class="float-left" style="width:13%"><h4>브랜드명*</h4></div>
							<select class="selectStyle2" v-model="rowData.brandNm" @change="fnChgBrandValue(rowData.brandNm)" style="width:24%" title="브랜드명 선택란">
								<option value="">선택해주세요.</option>
								<option v-for="option in brandNmList" v-bind:value="option.BRAND_ID">{{option.BRAND_NAME}}</option>
							</select>
						</div>
						<div class="of_h">
							<div class="float-left" style="width:13%"><h4>카드개수</h4></div>
							<div class="float-left" style="width:57%">
								<select class="selectStyle2" style="width:70px" v-model="rcs9CardCount">
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
										<input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcs0Title">
									</div>
								</div>
								<div class="of_h">
									<div class="float-left" style="width:13%"><h4>내용*</h4></div>
									<div class="float-left" style="width:57%">
										<textarea class="textareaStyle height190" v-model="rowData.rcs0Content" :placeholder="rcsPlaceHoder" id="rcsCarouselTab1" @keyup="fnTextLength('내용', '#rcsCarouselTab1', '#rcsCarouselTab1TextLength', '1300')"></textarea>
										<strong class="letter" id="rcsCarouselTab1TextLength">(00 / 1300)</strong>
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
									<div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRowRcsButton('CSHORT_TAB1')" class="btnStyle1 backBlack">추가 +</a></div>
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
												<tr v-for="(buttonInfo, index) in rowData.rcs90Buttons" v-bind:key="index">
													<td class="text-center">
														<select class="selectStyle2" style="width:100%" v-model="buttonInfo.action.linkType">
															<option v-for="rcsButtonType in rcsButtonTypeList" :key="rcsButtonType.type" :value="rcsButtonType.type">{{rcsButtonType.name}}</option>
														</select>
													</td>
													<td class="text-left"><input v-model="buttonInfo.action.displayText" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='urlAction'" class="text-center"><input v-model="buttonInfo.action.urlAction.openUrl.url" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='clipboardAction'" class="text-center"><input v-model="buttonInfo.action.clipboardAction.copyToClipboard.text" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='dialerAction'" class="text-center"><input v-model="buttonInfo.action.dialerAction.dialPhoneNumber.phoneNumber" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='calendarAction'" class="text-center">
														<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.title" type="text" class="inputStyle" placeholder="제목입력">
														<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.description" type="text" class="inputStyle consolMarginTop" placeholder="내용입력">
														<div class="consolMarginTop of_h">
															<span class="float-left mt5" style="width:20%">시작일</span>
															<div class="float-right" style="width:80%">
																<Calendar classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.startTime"></Calendar>
															</div>
														</div>
														<div class="consolMarginTop of_h">
															<span class="float-left mt5" style="width:20%">종료일</span>
															<div class="float-right" style="width:80%">
																<Calendar classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.endTime"></Calendar>
															</div>
														</div>
													</td>
													<td v-if="buttonInfo.action.linkType=='mapAction'" class="text-center"><input v-model="buttonInfo.action.mapAction.requestLocationPush" type="text" class="inputStyle"></td>
													<td class="text-center end">
														<a @click="addRowRcsButton('CSHORT_TAB1')" title="추가버튼"><i class="far fa-plus channelBtn"></i></a>
														<a @click="removeRowRcsButton('CSHORT_TAB1', index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a>
													</td>
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
										<input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcs1Title">
									</div>
								</div>
								<div class="of_h">
									<div class="float-left" style="width:13%"><h4>내용*</h4></div>
									<div class="float-left" style="width:57%">
										<textarea class="textareaStyle height190" v-model="rowData.rcs1Content" :placeholder="rcsPlaceHoder" id="rcsCarouselTab2" @keyup="fnTextLength('내용', '#rcsCarouselTab2', '#rcsCarouselTab2TextLength', '1300')"></textarea>
										<strong class="letter" id="rcsCarouselTab2TextLength">(00 / 1300)</strong>
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
									<div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRowRcsButton('CSHORT_TAB2')" class="btnStyle1 backBlack">추가 +</a></div>
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
												<tr v-for="(buttonInfo, index) in rowData.rcs91Buttons" v-bind:key="index">
													<td class="text-center">
														<select class="selectStyle2" style="width:100%" v-model="buttonInfo.action.linkType">
															<option v-for="rcsButtonType in rcsButtonTypeList" :key="rcsButtonType.type" :value="rcsButtonType.type">{{rcsButtonType.name}}</option>
														</select>
													</td>
													<td class="text-left"><input v-model="buttonInfo.action.displayText" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='urlAction'" class="text-center"><input v-model="buttonInfo.action.urlAction.openUrl.url" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='clipboardAction'" class="text-center"><input v-model="buttonInfo.action.clipboardAction.copyToClipboard.text" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='dialerAction'" class="text-center"><input v-model="buttonInfo.action.dialerAction.dialPhoneNumber.phoneNumber" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='calendarAction'" class="text-center">
														<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.title" type="text" class="inputStyle" placeholder="제목입력">
														<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.description" type="text" class="inputStyle consolMarginTop" placeholder="내용입력">
														<div class="consolMarginTop of_h">
															<span class="float-left mt5" style="width:20%">시작일</span>
															<div class="float-right" style="width:80%">
																<Calendar classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.startTime"></Calendar>
															</div>
														</div>
														<div class="consolMarginTop of_h">
															<span class="float-left mt5" style="width:20%">종료일</span>
															<div class="float-right" style="width:80%">
																<Calendar classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.endTime"></Calendar>
															</div>
														</div>
													</td>
													<td v-if="buttonInfo.action.linkType=='mapAction'" class="text-center"><input v-model="buttonInfo.action.mapAction.requestLocationPush" type="text" class="inputStyle"></td>
													<td class="text-center end">
														<a @click="addRowRcsButton('CSHORT_TAB2')" title="추가버튼"><i class="far fa-plus channelBtn"></i></a>
														<a @click="removeRowRcsButton('CSHORT_TAB2', index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a>
													</td>
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
										<input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcs2Title">
									</div>
								</div>
								<div class="of_h">
									<div class="float-left" style="width:13%"><h4>내용*</h4></div>
									<div class="float-left" style="width:57%">
										<textarea class="textareaStyle height190" v-model="rowData.rcs2Content" :placeholder="rcsPlaceHoder" id="rcsCarouselTab3" @keyup="fnTextLength('내용', '#rcsCarouselTab3', '#rcsCarouselTab3TextLength', '1300')"></textarea>
										<strong class="letter" id="rcsCarouselTab3TextLength">(00 / 1300)</strong>
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
												<a @click="fnRcs92DelImg(imgIdx-1)">{{fnSubString(rowData.rcs92ImgInfoList[imgIdx-1].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a>
											</li>
											<li v-else>
												<a></a>
											</li>
										</ul>
									</div>
								</div>
								<div class="of_h consolMarginTop">
									<div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRowRcsButton('CSHORT_TAB3')" class="btnStyle1 backBlack">추가 +</a></div>
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
												<tr v-for="(buttonInfo, index) in rowData.rcs92Buttons" v-bind:key="index">
													<td class="text-center">
														<select class="selectStyle2" style="width:100%" v-model="buttonInfo.action.linkType">
															<option v-for="rcsButtonType in rcsButtonTypeList" :key="rcsButtonType.type" :value="rcsButtonType.type">{{rcsButtonType.name}}</option>
														</select>
													</td>
													<td class="text-left"><input v-model="buttonInfo.action.displayText" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='urlAction'" class="text-center"><input v-model="buttonInfo.action.urlAction.openUrl.url" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='clipboardAction'" class="text-center"><input v-model="buttonInfo.action.clipboardAction.copyToClipboard.text" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='dialerAction'" class="text-center"><input v-model="buttonInfo.action.dialerAction.dialPhoneNumber.phoneNumber" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='calendarAction'" class="text-center">
														<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.title" type="text" class="inputStyle" placeholder="제목입력">
														<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.description" type="text" class="inputStyle consolMarginTop" placeholder="내용입력">
														<div class="consolMarginTop of_h">
															<span class="float-left mt5" style="width:20%">시작일</span>
															<div class="float-right" style="width:80%">
																<Calendar classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.startTime"></Calendar>
															</div>
														</div>
														<div class="consolMarginTop of_h">
															<span class="float-left mt5" style="width:20%">종료일</span>
															<div class="float-right" style="width:80%">
																<Calendar classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.endTime"></Calendar>
															</div>
														</div>
													</td>
													<td v-if="buttonInfo.action.linkType=='mapAction'" class="text-center"><input v-model="buttonInfo.action.mapAction.requestLocationPush" type="text" class="inputStyle"></td>
													<td class="text-center end">
														<a @click="addRowRcsButton('CSHORT_TAB3')" title="추가버튼"><i class="far fa-plus channelBtn"></i></a>
														<a @click="removeRowRcsButton('CSHORT_TAB3', index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a>
													</td>
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
										<input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcs3Title">
									</div>
								</div>
								<div class="of_h">
									<div class="float-left" style="width:13%"><h4>내용*</h4></div>
									<div class="float-left" style="width:57%">
										<textarea class="textareaStyle height190" v-model="rowData.rcs3Content" :placeholder="rcsPlaceHoder" id="rcsCarouselTab4" @keyup="fnTextLength('내용', '#rcsCarouselTab4', '#rcsCarouselTab4TextLength', '1300')"></textarea>
										<strong class="letter" id="rcsCarouselTab4TextLength">(00 / 1300)</strong>
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
												<a @click="fnRcs93DelImg(imgIdx-1)">{{fnSubString(rowData.rcs93ImgInfoList[imgIdx-1].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a>
											</li>
											<li v-else>
												<a></a>
											</li>
										</ul>
									</div>
								</div>
								<div class="of_h consolMarginTop">
									<div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRowRcsButton('CSHORT_TAB4')" class="btnStyle1 backBlack">추가 +</a></div>
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
												<tr v-for="(buttonInfo, index) in rowData.rcs93Buttons" v-bind:key="index">
													<td class="text-center">
														<select class="selectStyle2" style="width:100%" v-model="buttonInfo.action.linkType">
															<option v-for="rcsButtonType in rcsButtonTypeList" :key="rcsButtonType.type" :value="rcsButtonType.type">{{rcsButtonType.name}}</option>
														</select>
													</td>
													<td class="text-left"><input v-model="buttonInfo.action.displayText" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='urlAction'" class="text-center"><input v-model="buttonInfo.action.urlAction.openUrl.url" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='clipboardAction'" class="text-center"><input v-model="buttonInfo.action.clipboardAction.copyToClipboard.text" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='dialerAction'" class="text-center"><input v-model="buttonInfo.action.dialerAction.dialPhoneNumber.phoneNumber" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='calendarAction'" class="text-center">
														<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.title" type="text" class="inputStyle" placeholder="제목입력">
														<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.description" type="text" class="inputStyle consolMarginTop" placeholder="내용입력">
														<div class="consolMarginTop of_h">
															<span class="float-left mt5" style="width:20%">시작일</span>
															<div class="float-right" style="width:80%">
																<Calendar classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.startTime"></Calendar>
															</div>
														</div>
														<div class="consolMarginTop of_h">
															<span class="float-left mt5" style="width:20%">종료일</span>
															<div class="float-right" style="width:80%">
																<Calendar classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.endTime"></Calendar>
															</div>
														</div>
													</td>
													<td v-if="buttonInfo.action.linkType=='mapAction'" class="text-center"><input v-model="buttonInfo.action.mapAction.requestLocationPush" type="text" class="inputStyle"></td>
													<td class="text-center end">
														<a @click="addRowRcsButton('CSHORT_TAB4')" title="추가버튼"><i class="far fa-plus channelBtn"></i></a>
														<a @click="removeRowRcsButton('CSHORT_TAB4', index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a>
													</td>
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
										<input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcs4Title">
									</div>
								</div>
								<div class="of_h">
									<div class="float-left" style="width:13%"><h4>내용*</h4></div>
									<div class="float-left" style="width:57%">
										<textarea class="textareaStyle height190" v-model="rowData.rcs4Content" :placeholder="rcsPlaceHoder" id="rcsCarouselTab5" @keyup="fnTextLength('내용', '#rcsCarouselTab5', '#rcsCarouselTab5TextLength', '1300')"></textarea>
										<strong class="letter" id="rcsCarouselTab5TextLength">(00 / 1300)</strong>
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
												<a @click="fnRcs94DelImg(imgIdx-1)">{{fnSubString(rowData.rcs94ImgInfoList[imgIdx-1].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a>
											</li>
											<li v-else>
												<a></a>
											</li>
										</ul>
									</div>
								</div>
								<div class="of_h consolMarginTop">
									<div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRowRcsButton('CSHORT_TAB5')" class="btnStyle1 backBlack">추가 +</a></div>
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
												<tr v-for="(buttonInfo, index) in rowData.rcs94Buttons" v-bind:key="index">
													<td class="text-center">
														<select class="selectStyle2" style="width:100%" v-model="buttonInfo.action.linkType">
															<option v-for="rcsButtonType in rcsButtonTypeList" :key="rcsButtonType.type" :value="rcsButtonType.type">{{rcsButtonType.name}}</option>
														</select>
													</td>
													<td class="text-left"><input v-model="buttonInfo.action.displayText" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='urlAction'" class="text-center"><input v-model="buttonInfo.action.urlAction.openUrl.url" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='clipboardAction'" class="text-center"><input v-model="buttonInfo.action.clipboardAction.copyToClipboard.text" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='dialerAction'" class="text-center"><input v-model="buttonInfo.action.dialerAction.dialPhoneNumber.phoneNumber" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='calendarAction'" class="text-center">
														<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.title" type="text" class="inputStyle" placeholder="제목입력">
														<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.description" type="text" class="inputStyle consolMarginTop" placeholder="내용입력">
														<div class="consolMarginTop of_h">
															<span class="float-left mt5" style="width:20%">시작일</span>
															<div class="float-right" style="width:80%">
																<Calendar classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.startTime"></Calendar>
															</div>
														</div>
														<div class="consolMarginTop of_h">
															<span class="float-left mt5" style="width:20%">종료일</span>
															<div class="float-right" style="width:80%">
																<Calendar classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.endTime"></Calendar>
															</div>
														</div>
													</td>
													<td v-if="buttonInfo.action.linkType=='mapAction'" class="text-center"><input v-model="buttonInfo.action.mapAction.requestLocationPush" type="text" class="inputStyle"></td>
													<td class="text-center end">
														<a @click="addRowRcsButton('CSHORT_TAB5')" title="추가버튼"><i class="far fa-plus channelBtn"></i></a>
														<a @click="removeRowRcsButton('CSHORT_TAB5', index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a>
													</td>
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
										<input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcs5Title">
									</div>
								</div>
								<div class="of_h">
									<div class="float-left" style="width:13%"><h4>내용*</h4></div>
									<div class="float-left" style="width:57%">
										<textarea class="textareaStyle height190" v-model="rowData.rcs5Content" :placeholder="rcsPlaceHoder" id="rcsCarouselTab6" @keyup="fnTextLength('내용', '#rcsCarouselTab6', '#rcsCarouselTab6TextLength', '1300')"></textarea>
										<strong class="letter" id="rcsCarouselTab6TextLength">(00 / 1300)</strong>
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
												<a @click="fnRcs95DelImg(imgIdx-1)">{{fnSubString(rowData.rcs95ImgInfoList[imgIdx-1].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a>
											</li>
											<li v-else>
												<a></a>
											</li>
										</ul>
									</div>
								</div>
								<div class="of_h consolMarginTop">
									<div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRowRcsButton('CSHORT_TAB6')" class="btnStyle1 backBlack">추가 +</a></div>
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
												<tr v-for="(buttonInfo, index) in rowData.rcs95Buttons" v-bind:key="index">
													<td class="text-center">
														<select class="selectStyle2" style="width:100%" v-model="buttonInfo.action.linkType">
															<option v-for="rcsButtonType in rcsButtonTypeList" :key="rcsButtonType.type" :value="rcsButtonType.type">{{rcsButtonType.name}}</option>
														</select>
													</td>
													<td class="text-left"><input v-model="buttonInfo.action.displayText" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='urlAction'" class="text-center"><input v-model="buttonInfo.action.urlAction.openUrl.url" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='clipboardAction'" class="text-center"><input v-model="buttonInfo.action.clipboardAction.copyToClipboard.text" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='dialerAction'" class="text-center"><input v-model="buttonInfo.action.dialerAction.dialPhoneNumber.phoneNumber" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='calendarAction'" class="text-center">
														<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.title" type="text" class="inputStyle" placeholder="제목입력">
														<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.description" type="text" class="inputStyle consolMarginTop" placeholder="내용입력">
														<div class="consolMarginTop of_h">
															<span class="float-left mt5" style="width:20%">시작일</span>
															<div class="float-right" style="width:80%">
																<Calendar classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.startTime"></Calendar>
															</div>
														</div>
														<div class="consolMarginTop of_h">
															<span class="float-left mt5" style="width:20%">종료일</span>
															<div class="float-right" style="width:80%">
																<Calendar classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.endTime"></Calendar>
															</div>
														</div>
													</td>
													<td v-if="buttonInfo.action.linkType=='mapAction'" class="text-center"><input v-model="buttonInfo.action.mapAction.requestLocationPush" type="text" class="inputStyle"></td>
													<td class="text-center end">
														<a @click="addRowRcsButton('CSHORT_TAB6')" title="추가버튼"><i class="far fa-plus channelBtn"></i></a>
														<a @click="removeRowRcsButton('CSHORT_TAB6', index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<!--  //tab6 -->

							<div class="of_h consolMarginTop" v-if="rowData.msgKind == 'A'">
								<div class="float-left" style="width:13%"><h4>무료수신거부 *</h4></div>
								<div class="float-left" style="width:57%">
									<input type="text" class="inputStyle" v-model="rowData.rcsBlockNumber" placeholder="수신거부번호 설정">
								</div>
							</div>
							<div class="of_h consolMarginTop">
								<div class="float-left" style="width:13%"><h4>발신번호 *</h4></div>
								<div class="float-left" style="width:57%">
									<select v-model="rowData.callback" class="selectStyle2 float-right" style="width:100%">
										<option value="">선택해주세요.</option>
										<option v-for="info in rcsCallbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
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
						<div class="cardBxsliderWrap">
							<!-- phoneWrap -->
							<div class="phoneWrap" style="height:660px">
								<img src="@/assets/images/common/phoneMockup1.svg" alt="캐러셀(Tall) 템플릿">
								<div class="phoneCardWrap">
									<p class="color000">
										<span>[WEB발신]</span>
										<span v-if="rowData.msgKind == 'A'"> (광고)</span>
									</p>
									<ul class="cardBxslider mt10">
										<li v-for="(rcsCTallImgInfo, cardIdx) in rowData.rcsCTallImgInfoList" :key="cardIdx" class="slide cardBox scroll-yc">
											<img v-if="rowData.msgType == 'IMAGE' && rcsCTallImgInfo.imgUrl" :src="rcsCTallImgInfo.imgUrl" style="width: 204px;height: 255px;" alt="캐러셀(Tall) 템플릿">
											<div>
												<div>
													<p class="color000 font-size13">{{rcsCTallImgInfo.rcsTitle}}</p>
													<p class="mt15"><pre>{{rcsCTallImgInfo.rcsContent}}</pre></p>
												</div>
											</div>
											<div v-if="rcsCTallImgInfo.rcsButtons.length > 0">
												<p v-for="(buttonInfo, idx) in rcsCTallImgInfo.rcsButtons" :key="idx" class="text-center mt10" style="color:#69C8FF">
													<a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.action.displayText)">{{buttonInfo.action.displayText}}</a>
												</p>
											</div>
											<p v-if="rowData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(rowData.rcsBlockNumber)" class="color3 font-size10 mt5">수신거부번호: {{rowData.rcsBlockNumber}}</p>
										</li>
									</ul>
									<div id="slide-counter"><span class="current"></span> / <span class="total"></span></div>
								</div>
							</div>
							<!-- //phoneWrap -->
						</div>
					</div>
					<div class="float-left consoleCon" style="width:72%">
						<div class="of_h">
							<div class="float-left" style="width:13%"><h4>브랜드명*</h4></div>
							<select class="selectStyle2" v-model="rowData.brandNm" @change="fnChgBrandValue(rowData.brandNm)" style="width:24%" title="브랜드명 선택란">
								<option value="">선택해주세요.</option>
								<option v-for="option in brandNmList" v-bind:value="option.BRAND_ID">{{option.BRAND_NAME}}</option>
							</select>
						</div>
						<div class="of_h">
							<div class="float-left" style="width:13%"><h4>카드개수</h4></div>
							<div class="float-left" style="width:57%">
								<select class="selectStyle2" style="width:70px" v-model="rcs10CardCount" @change="fnChgCTallCardCount(rcs10CardCount)">
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
							<!--  testTab -->
							<div v-for="(rcsCTallImgInfo, tabIdx) in rowData.rcsCTallImgInfoList" :key="tabIdx" class="tab-pane" role="tabpanel" :id="fnCarouselString('card10', tabIdx, '')">
								<div class="of_h consolMarginTop">
									<div class="float-left" style="width:13%"><h4>제목</h4></div>
									<div class="float-left" style="width:57%">
										<input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.rcsCTallImgInfoList[tabIdx].rcsTitle">
									</div>
								</div>
								<div class="of_h">
									<div class="float-left" style="width:13%"><h4>내용*</h4></div>
									<div class="float-left" style="width:57%">
										<textarea class="textareaStyle height190" v-model="rowData.rcsCTallImgInfoList[tabIdx].rcsContent" :placeholder="rcsPlaceHoder" :id="fnCarouselString('rcsCarouselTab', tabIdx, '')" @keyup="fnTextLength('내용', fnCarouselString('#rcsCarouselTab', tabIdx, ''), fnCarouselString('#rcsCarouselTab', tabIdx, 'TextLength'), '1300')"></textarea>
										<strong class="letter" :id="fnCarouselString('rcsCarouselTab', tabIdx, 'TextLength')">(00 / 1300)</strong>
									</div>
								</div>
								<div class="of_h consolMarginTop" v-if="rowData.msgType == 'IMAGE'">
									<div class="float-left" style="width:13%"><h4>이미지</h4></div>
									<div class="float-left" style="width:57%">
										<div class="float-left" style="width:25%">
											<a @click="fnRcsCarouselTallOpenImageManagePopUp(tabIdx)" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
										</div>
										<ul class="float-right attachList" style="width:75%; padding:5px 15px; height:30px;">
											<li v-if="rowData.rcsCTallImgInfoList[tabIdx].fileId.length > 0 && rowData.rcsCTallImgInfoList[tabIdx].imgUrl.length > 0">
												<a @click="fnRcsCarouselTallDelImg(tabIdx)">{{fnSubString(rowData.rcsCTallImgInfoList[tabIdx].imgUrl, 0, 55)}} <i class="fal fa-times"></i></a>
											</li>
											<li v-else>
												<a></a>
											</li>
										</ul>
									</div>
								</div>
								<div class="of_h consolMarginTop">
									<div class="float-left" style="width:13%"><h4>버튼</h4><a @click="addRowRcsButton(fnCarouselString('CTALL_TAB', tabIdx, ''))" class="btnStyle1 backBlack">추가 +</a></div>
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
												<tr v-for="(buttonInfo, index) in rowData.rcsCTallImgInfoList[tabIdx].rcsButtons" v-bind:key="index">
													<td class="text-center">
														<select class="selectStyle2" style="width:100%" v-model="buttonInfo.action.linkType">
															<option v-for="rcsButtonType in rcsButtonTypeList" :key="rcsButtonType.type" :value="rcsButtonType.type">{{rcsButtonType.name}}</option>
														</select>
													</td>
													<td class="text-left"><input v-model="buttonInfo.action.displayText" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='urlAction'" class="text-center"><input v-model="buttonInfo.action.urlAction.openUrl.url" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='clipboardAction'" class="text-center"><input v-model="buttonInfo.action.clipboardAction.copyToClipboard.text" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='dialerAction'" class="text-center"><input v-model="buttonInfo.action.dialerAction.dialPhoneNumber.phoneNumber" type="text" class="inputStyle"></td>
													<td v-if="buttonInfo.action.linkType=='calendarAction'" class="text-center">
														<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.title" type="text" class="inputStyle" placeholder="제목입력">
														<input v-model="buttonInfo.action.calendarAction.createCalendarEvent.description" type="text" class="inputStyle consolMarginTop" placeholder="내용입력">
														<div class="consolMarginTop of_h">
															<span class="float-left mt5" style="width:20%">시작일</span>
															<div class="float-right" style="width:80%">
																<Calendar classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.startTime"></Calendar>
															</div>
														</div>
														<div class="consolMarginTop of_h">
															<span class="float-left mt5" style="width:20%">종료일</span>
															<div class="float-right" style="width:80%">
																<Calendar classProps="datepicker inputStyle" :initDate="buttonInfo.action.calendarAction.createCalendarEvent.endTime"></Calendar>
															</div>
														</div>
													</td>
													<td v-if="buttonInfo.action.linkType=='mapAction'" class="text-center"><input v-model="buttonInfo.action.mapAction.requestLocationPush" type="text" class="inputStyle"></td>
													<td class="text-center end">
														<a @click="addRowRcsButton(fnCarouselString('CTALL_TAB', tabIdx, ''))" title="추가버튼"><i class="far fa-plus channelBtn"></i></a>
														<a @click="removeRowRcsButton(fnCarouselString('CTALL_TAB', tabIdx, ''), index)" title="삭제버튼"><i class="far fa-minus channelBtn"></i></a>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<!--  //testTab -->

							<div class="of_h consolMarginTop" v-if="rowData.msgKind == 'A'">
								<div class="float-left" style="width:13%"><h4>무료수신거부 *</h4></div>
								<div class="float-left" style="width:57%">
									<input type="text" class="inputStyle" v-model="rowData.rcsBlockNumber" placeholder="수신거부번호 설정">
								</div>
							</div>
							<div class="of_h consolMarginTop">
								<div class="float-left" style="width:13%"><h4>발신번호 *</h4></div>
								<div class="float-left" style="width:57%">
									<select v-model="rowData.callback" class="selectStyle2 float-right" style="width:100%">
										<option value="">선택해주세요.</option>
										<option v-for="info in rcsCallbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
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
									<div v-if="rowData.msgType == 'IMAGE' && !$gfnCommonUtils.isEmpty(rowData.friendTalkImgInfo.imgUrl)" class="phoneText2 mt10 text-center" :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.friendTalkImgInfo.imgUrl+');'">
									</div>
									<br v-if="rowData.msgType == 'IMAGE' && $gfnCommonUtils.isEmpty(rowData.friendTalkImgInfo.imgUrl)"/>
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
								<li><a @click="fnFriendTalkDelImg">{{fnSubString(rowData.friendTalkImgInfo.imgUrl, 0, 35)}}  <i v-if="!$gfnCommonUtils.isEmpty(rowData.friendTalkImgInfo.imgUrl)" class="fal fa-times"></i></a></li>
							</ul>
						</div>
					</div>
					<div class="of_h consolMarginTop" v-if="rowData.msgType == 'IMAGE'">
						<div class="float-left" style="width:13%"><h4>이미지 링크</h4></div>
						<div class="float-left" style="width:59%">
							<input type="text" class="inputStyle" placeholder="https://..." v-model="rowData.friendTalkImageLink">
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
						<div v-if="rowData.smsSendType == 'M'" class="phoneText2 mb10">
							<p v-if="$gfnCommonUtils.isEmpty(rowData.smsTitle)">템플릿 제목</p>
							<p v-else><span v-if="rowData.msgKind == 'A'">(광고)</span>{{rowData.smsTitle}}</p>
						</div>
						<div v-if="rowData.smsSendType == 'M'">
							<div v-for="(imgInfo, idx) in rowData.smsImgInfoList" :key="idx" class="phoneText2 mt10 text-center" :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+imgInfo.imgUrl+');'">
							</div>
						</div>
						<div class="phoneText1">
							<p v-if="$gfnCommonUtils.isEmpty(rowData.smsContent) && (rowData.msgKind != 'A' || $gfnCommonUtils.isEmpty(rowData.smsRcvblcNumber))" class="font-size14 color4 mt10">템플릿 내용</p>
							<p v-else class="font-size14 color4 mt10">
								<span><pre><span v-if="rowData.smsSendType == 'S' && rowData.msgKind == 'A'">(광고)</span>{{rowData.smsContent}}</pre></span>
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
							<textarea class="textareaStyle height190" :placeholder="smsmmsPlaceHoder" v-model="rowData.smsContent" @input="fnSetSmsCurrByte"></textarea>
							<strong class="letter">({{msgSmsCurrByte}} / {{msgSmsLimitByte}})</strong>
						</div>
					</div>
					<div class="of_h" v-if="rowData.msgKind == 'A'">
						<div class="float-left" style="width:13%">
							<h4>광고성메시지<br/>수신거부번호*</h4>
							<a href="#" class="btnStyle1 backLightGray" @click.prevent="rcvblcNumOpen=true" title="수신거부번호 선택" activity="READ">선택</a>
						</div>
						<div class="float-left mt10" style="width:57%">
							<input type="text" class="inputStyle" title="광고성메시지 수신거부번호 입력란" v-model="rowData.smsRcvblcNumber" maxlength="20" @input="fnSetSmsCurrByte">
						</div>
					</div>
				</div>
				<div v-else-if="smsTemplateTable === 1">
					<div class="of_h" >
						<div class="float-left" style="width:13%"><h4>제목*</h4></div>
						<div class="float-left" style="width:57%">
							<input type="text" class="inputStyle" placeholder="최대 30자 입력 가능니다." v-model="rowData.smsTitle" @input="fnSetSmsCurrByte">
						</div>
					</div>
					<div class="of_h" >
						<div class="float-left" style="width:13%"><h4>내용*</h4></div>
						<div class="float-left" style="width:57%">
							<textarea class="textareaStyle height190" :placeholder="smsmmsPlaceHoder" v-model="rowData.smsContent" @input="fnSetSmsCurrByte"></textarea>
							<strong class="letter">({{msgSmsCurrByte}} / {{msgSmsLimitByte}})</strong>
						</div>
					</div>
					<div class="of_h" v-if="rowData.msgKind == 'A'">
						<div class="float-left" style="width:13%">
							<h4>광고성메시지<br/>수신거부번호*</h4>
							<a href="#" class="btnStyle1 backLightGray" @click.prevent="rcvblcNumOpen=true" title="수신거부번호 선택" activity="READ">선택</a>
						</div>
						<div class="float-left mt10" style="width:57%">
							<input type="text" class="inputStyle" title="광고성메시지 수신거부번호 입력란" v-model="rowData.smsRcvblcNumber" maxlength="20" @input="fnSetSmsCurrByte">
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
			<a v-if="rowData.tmpltStatus != 'COMPLETE'" @click="save()" class="btnStyle2 borderGray ml10" data-toggle="modal" title="저장" activity="SAVE">저장</a>
			<a @click="complete()" class="btnStyle2 backBlack ml10" data-toggle="modal" title="등록" activity="SAVE">등록</a>
			<router-link :to="{ name: 'multiSendTemplateList' }" tag="a" class="btnStyle2 backRed ml10">취소</router-link>
		</div>

		<RcsTemplatePopup :templateRadioBtn.sync="templateRadioBtn" ref="rcsTemplatePop" @fnResult="fnSetRcsTemplate"></RcsTemplatePopup>
		<AlimTalkTemplatePopup :alimTalkTemplateOpen.sync="alimTalkTemplateOpen" ref="alimTalkTmplPopup"></AlimTalkTemplatePopup>
		<RcvblcNumPopup @callback-func="fnCallbackRcvblcNum" :rcvblcNumOpen.sync="rcvblcNumOpen"></RcvblcNumPopup>
	</div>
</template>

<script>
import templateApi from "@/modules/template/service/templateApi.js";
import messageApi from "@/modules/message/service/messageApi.js";
import RcsTemplatePopup from "@/modules/rcsTemplateSend/components/bp-rcsTemplatePop.vue";
import AlimTalkTemplatePopup from "@/modules/message/components/bp-alimTalkTemplate.vue";
import RcvblcNumPopup from "@/modules/message/components/bp-rcvblcNumManage.vue";

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

var slider;

export default {
	name: 'multiSendTemplateManage',
	components : {
		RcsTemplatePopup,
		AlimTalkTemplatePopup,
		RcvblcNumPopup,

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
					'rcsCTallImgInfoList':[], //RCS 캐러셀 카드전체 이미지정보

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

					'rcsDesButtons':[], // RCS 서술형 버튼리스트
					'rcsStyleButtons':[], // RCS 스타일형 버튼리스트
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
			rcsCallbackList: [],					//RCS 발신번호 리스트
			smsCallbackList: [],				//smslms 발신번호 리스트
			rcvblcNumOpen: false,
			beforeSenderType: 'S',
			beforeMsgKind: 'I',
			msgSmsCurrByte: 0,
			msgSmsLimitByte: 0,

			rcsDesMessagebaseId: '',		//서술형 MessagebaseId
			rcsDesMessagebaseformId: '',	//서술형 유형
			rcsDesFormNm: '',	//서술형 유형명
			rcsDesFormNmList: [],				//RCS 서술형 유형 selectBox
			rcsStyleMessagebaseId: '',			//스타일형 MessagebaseId
			rcsStyleMessagebaseformId: '',	//스타일형 유형
			rcsStyleFormNm: '',					// 스타일형 유형
			rcsStyleFormNmList: [],				// 스타일형 유형 selectBox
			rcsStyleContentCnt: 0,				// 스타일형 inputLine count
			rcsStyleInput: [],						// 스타일형 첫 input
			rcsStyleInputSec: [],					// 스타일형 두번째 input
			rcsStyleChk: [],							// 스타일형 lineChk
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
			rcsCarouselTallImgLimitSize : 1,
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
			selectedRcsDesTemplate: false,		// RCS 템플릿(서술형) 선택 전
			selectedRcsStyTemplate: false,		// RCS 템플릿(스타일형) 선택 전
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

			rcsSmsStartDate:["rcsSmsFirstStartDate","rcsSmsSecondStartDate"],
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

			pushPlaceHoder: 	"변수로 설정하고자 하는 내용을 #{ }표시로 작성해 주십시오.\n예) 이름과 출금일을 변수 설정: 예) #{고객}님 #{YYMMDD} 출금 예정입니다.",
			rcsPlaceHoder: 	"변수로 설정하고자 하는 내용을 {{ }}표시로 작성해 주십시오.\n예) 이름과 출금일을 변수 설정: 예) {{고객}}님 {{YYMMDD}} 출금 예정입니다.",
			kakaoPlaceHoder: 	"변수로 설정하고자 하는 내용을 #{ }표시로 작성해 주십시오.\n예) 이름과 출금일을 변수 설정: 예) #{고객}님 #{YYMMDD} 출금 예정입니다.",
			smsmmsPlaceHoder: "변수로 설정하고자 하는 내용을 #{ }표시로 작성해 주십시오.\n예) 이름과 출금일을 변수 설정: 예) #{고객}님 #{YYMMDD} 출금 예정입니다.",

			buttonDSDescription : '카카오 메세지에 택배사 명과 송장번호를 기재한 후, 배송 조회 버튼을 추가하시면 메세지에서 택배사 명과 송장번호를 추출하여 배송 조회 카카오 검색페이지 링크가 자동으로 생성됩니다. 카카오에서 지원하는 택배사명과 운송장번호가 알림톡 메시지 내에 포함된 경우에만 배송조회 버튼이 표시됩니다. 배송 조회가 가능한 택배사는 <span style="color:#e11d21"><strong>카카오와 해당 택배사와의 계약 관계에 의해 변동될 수 있음을 유의해주시기 바랍니다.</strong></span>',
		}
	},
	watch: {
		// RCS 템플릿 종류 선택에 따라 v-if를 이용하여 active되었을 경우에만 값을 가져온다.
		rcsTemplateTable(val){
			var vm = this;
			if (val === 0) {//rcsTemplateTable === 0 프리템플릿
				this.fnSelectBrandList();
			} else if(val === 1) {//rcsTemplateTable === 1
				this.templateRadioBtn = 'des';
			} else if(val === 2) {//rcsTemplateTable === 2
				this.templateRadioBtn = 'cell';
			} else if(val === 3) {//rcsTemplateTable === 3

			} else if(val === 4) {//rcsTemplateTable === 4

			} else if(val === 5) {//rcsTemplateTable === 5 SHORT

			} else if(val === 6) {//rcsTemplateTable === 6 TALL

			} else if(val === 9) {//rcsTemplateTable === 9 캐러셀 SHORT
				setTimeout(function() {
					vm.fnSetSlider();
				}, 100);
			} else if(val === 10) {//rcsTemplateTable === 10 캐러셀 TALL
				if (this.rowData.rcsCTallImgInfoList.length == 0) {
					this.rowData.rcsCTallImgInfoList.push({'fileId':'', 'imgUrl':'', 'rcsTitle':'', 'rcsContent':'', 'rcsButtons':[]});
					this.rowData.rcsCTallImgInfoList.push({'fileId':'', 'imgUrl':'', 'rcsTitle':'', 'rcsContent':'', 'rcsButtons':[]});
					this.rowData.rcsCTallImgInfoList.push({'fileId':'', 'imgUrl':'', 'rcsTitle':'', 'rcsContent':'', 'rcsButtons':[]});
				}
				setTimeout(function() {
					vm.fnSetSlider();
					jQuery("#card101").attr("class", "tab-pane active");
				}, 100);
				jQuery("#slide-counter .current").text(1);
				jQuery("#slide-counter .total").text(this.rcs10CardCount);
			}
		},
		rowData: {
			deep: true,
			handler(nData) {
				if (nData.smsSendType != this.beforeSenderType) {
					this.beforeSenderType = nData.smsSendType;
					this.fnGetSmsLimitByte();
				}
				if (nData.msgKind != this.beforeMsgKind) {
					this.beforeMsgKind = nData.msgKind;
					this.fnSetSmsCurrByte();
				}
			}
		}
	},
	mounted() {
		this.init();
		this.fnSetSlider();
		this.fnPushGetAppId();// v-show 사용으로 항상 살아있어서 mounted에 놓음
		this.fnSelectFriendTalkSenderKeyList();
		this.fnSMSSelectCallbackList();
		this.fnSetMultiSendTemplateInfo();
		if (jQuery("#SMSMMS").prop("checked") == true) {
			this.fnGetSmsLimitByte();
			this.fnSetSmsCurrByte();
		}
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
				vm.rcsStyleMessagebaseformId = resultData.styleFormList[0].MESSAGEBASEFORM_ID;
				vm.rcsStyleFormNmList = resultData.styleFormList;
			});
		},
		fnSetSlider(){
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
			if (this.rcsTemplateTable == 9) {
				jQuery("#slide-counter .total").text(this.rcs9CardCount);
			} else {
				jQuery("#slide-counter .total").text(this.rcs10CardCount);
			}
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
				this.fnSelectBrandList();
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
				this.fnGetSmsLimitByte();
			}
		},
		// 빈값확인
		fnIsEmpty(str) {
			if(str) return false;
			else return true
		},
		// 친구톡 버튼타입 변경 이벤트
		fnChgBtnType(idx){
			const vm = this;
			Object.keys(this.rowData.friendTalkButtons[idx]).forEach(function(key){
				if (key != 'type') {
					delete vm.rowData.friendTalkButtons[idx][key];
				}
			});
		},
		// RCS 브랜드 변경 이벤트
		fnChgBrandValue(brandNm) {
			if (brandNm != null && brandNm != '') {
				var params = {brandId: brandNm};
				// rcs 템플릿 발신번호 리스트 조회
				messageApi.selectCallbackList(params).then(response =>{
					var result = response.data;
					if (result.success) {
						this.rcsCallbackList = result.data;
					} else {
						confirm.fnAlert("RCS 템플릿 발신번호 리스트 조회", result.message);
					}
				});
			} else {
				this.rcsCallbackList = [];
			}
		},
		// RCS 캐러셀(Tall) 카드개수 변경 이벤트
		fnChgCTallCardCount(rcs10CardCount) {
			if (rcs10CardCount == 3) {
				if (this.rowData.rcsCTallImgInfoList.length == 4) {
					this.rowData.rcsCTallImgInfoList.splice(3, 1);
				} else if (this.rowData.rcsCTallImgInfoList.length == 5) {
					this.rowData.rcsCTallImgInfoList.splice(4, 1);
					this.rowData.rcsCTallImgInfoList.splice(3, 1);
				} else if (this.rowData.rcsCTallImgInfoList.length == 6) {
					this.rowData.rcsCTallImgInfoList.splice(5, 1);
					this.rowData.rcsCTallImgInfoList.splice(4, 1);
					this.rowData.rcsCTallImgInfoList.splice(3, 1);
				}
			} else if (rcs10CardCount == 4) {
				if (this.rowData.rcsCTallImgInfoList.length == 3) {
					this.rowData.rcsCTallImgInfoList.push({'fileId':'', 'imgUrl':'', 'rcsTitle':'', 'rcsContent':'', 'rcsButtons':[]});
				} else if (this.rowData.rcsCTallImgInfoList.length == 5) {
					this.rowData.rcsCTallImgInfoList.splice(4, 1);
				} else if (this.rowData.rcsCTallImgInfoList.length == 6) {
					this.rowData.rcsCTallImgInfoList.splice(5, 1);
					this.rowData.rcsCTallImgInfoList.splice(4, 1);
				}
			} else if (rcs10CardCount == 5) {
				if (this.rowData.rcsCTallImgInfoList.length == 3) {
					this.rowData.rcsCTallImgInfoList.push({'fileId':'', 'imgUrl':'', 'rcsTitle':'', 'rcsContent':'', 'rcsButtons':[]});
					this.rowData.rcsCTallImgInfoList.push({'fileId':'', 'imgUrl':'', 'rcsTitle':'', 'rcsContent':'', 'rcsButtons':[]});
				} else if (this.rowData.rcsCTallImgInfoList.length == 4) {
					this.rowData.rcsCTallImgInfoList.push({'fileId':'', 'imgUrl':'', 'rcsTitle':'', 'rcsContent':'', 'rcsButtons':[]});
				} else if (this.rowData.rcsCTallImgInfoList.length == 6) {
					this.rowData.rcsCTallImgInfoList.splice(5, 1);
				}
			} else if (rcs10CardCount == 6) {
				if (this.rowData.rcsCTallImgInfoList.length == 3) {
					this.rowData.rcsCTallImgInfoList.push({'fileId':'', 'imgUrl':'', 'rcsTitle':'', 'rcsContent':'', 'rcsButtons':[]});
					this.rowData.rcsCTallImgInfoList.push({'fileId':'', 'imgUrl':'', 'rcsTitle':'', 'rcsContent':'', 'rcsButtons':[]});
					this.rowData.rcsCTallImgInfoList.push({'fileId':'', 'imgUrl':'', 'rcsTitle':'', 'rcsContent':'', 'rcsButtons':[]});
				} else if (this.rowData.rcsCTallImgInfoList.length == 4) {
					this.rowData.rcsCTallImgInfoList.push({'fileId':'', 'imgUrl':'', 'rcsTitle':'', 'rcsContent':'', 'rcsButtons':[]});
					this.rowData.rcsCTallImgInfoList.push({'fileId':'', 'imgUrl':'', 'rcsTitle':'', 'rcsContent':'', 'rcsButtons':[]});
				} else if (this.rowData.rcsCTallImgInfoList.length == 5) {
					this.rowData.rcsCTallImgInfoList.push({'fileId':'', 'imgUrl':'', 'rcsTitle':'', 'rcsContent':'', 'rcsButtons':[]});
				}
			}
			setTimeout(function() {
				slider.reloadSlider();
			}, 100);
			jQuery("#slide-counter .total").text(rcs10CardCount);
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
			jQuery(".borderLightGray").removeClass("active");
			jQuery("#channel"+idx).addClass("active");
		},
		channelName:function(idx){
			var str = "channel" + idx;
			return str;
		},
		// 채널 발송순서를 위로
		moveup:function() {
			if (this.idxData > 0) {
				this.rowData.checkedChannel.splice(this.idxData-1, 0 , (this.rowData.checkedChannel[this.idxData]));
				//Delete the next item
				this.rowData.checkedChannel.splice(this.idxData+1, 1);
				this.idxData = this.idxData -1;
			}
		},
		// 채널 발송순서를 아래로
		movedown:function() {
			if (this.idxData < this.rowData.checkedChannel.length-1) {
				//Insert the item in the next item
				this.rowData.checkedChannel.splice(this.idxData+2, 0, (this.rowData.checkedChannel[this.idxData]));
				this.rowData.checkedChannel.splice(this.idxData, 1);
				this.idxData = this.idxData +1;
			}
		},
		// 저가 우선정렬
		prioritySort:function() {
			if (this.rowData.checkedChannel.length < 2) {
				confirm.fnAlert(this.componentsTitle, "채널을 2개 이상 선택하세요.");
			} else {
				var pushFlag = false;
				var kakaoFlag = false;
				var rcsFlag = false;
				var smsFlag = false;
				if (this.rowData.checkedChannel.length == 4) {
					for (var i=0; i < this.rowData.checkedChannel.length; i++) {
						this.rowData.checkedChannel.splice(i, 1);
					}
					this.rowData.checkedChannel[0] = 'PUSH';
					this.rowData.checkedChannel[1] = 'KAKAO'
					this.rowData.checkedChannel[2] = 'RCS';
					this.rowData.checkedChannel[3] = 'SMSMMS';
					jQuery(".borderLightGray").removeClass("active");
				} else if (this.rowData.checkedChannel.length == 3) {
					for (var i=0; i < this.rowData.checkedChannel.length; i++) {
						if (this.rowData.checkedChannel[i] == 'PUSH') {
							pushFlag = true;
						} else if(this.rowData.checkedChannel[i] == 'RCS') {
							rcsFlag = true;
						} else if(this.rowData.checkedChannel[i] == 'KAKAO') {
							kakaoFlag = true;
						} else if(this.rowData.checkedChannel[i] == 'SMSMMS') {
							smsFlag = true;
						}
					}
					for (var i=0; i < this.rowData.checkedChannel.length; i++) {
						this.rowData.checkedChannel.splice(i, 1);
					}
					if (pushFlag == false) {
						this.rowData.checkedChannel[0] = 'KAKAO';
						this.rowData.checkedChannel[1] = 'RCS'
						this.rowData.checkedChannel[2] = 'SMSMMS';
					}
					if (kakaoFlag == false) {
						this.rowData.checkedChannel[0] = 'PUSH';
						this.rowData.checkedChannel[1] = 'RCS'
						this.rowData.checkedChannel[2] = 'SMSMMS';
					}
					if (rcsFlag == false) {
						this.rowData.checkedChannel[0] = 'PUSH';
						this.rowData.checkedChannel[1] = 'KAKAO'
						this.rowData.checkedChannel[2] = 'SMSMMS';
					}
					if (smsFlag == false) {
						this.rowData.checkedChannel[0] = 'PUSH';
						this.rowData.checkedChannel[1] = 'KAKAO'
						this.rowData.checkedChannel[2] = 'RCS';
					}
					jQuery(".borderLightGray").removeClass("active");
				} else if (this.rowData.checkedChannel.length == 2) {
					for (var i=0; i < this.rowData.checkedChannel.length; i++) {
						if (this.rowData.checkedChannel[i] == 'PUSH') {
							pushFlag = true;
						} else if(this.rowData.checkedChannel[i] == 'RCS') {
							rcsFlag = true;
						} else if(this.rowData.checkedChannel[i] == 'KAKAO') {
							kakaoFlag = true;
						} else if(this.rowData.checkedChannel[i] == 'SMSMMS') {
							smsFlag = true;
						}
					}
					for (var i=0; i < this.rowData.checkedChannel.length; i++) {
						this.rowData.checkedChannel.splice(i, 1);
					}
					if (pushFlag == true) {
						if (kakaoFlag == true) {
							this.rowData.checkedChannel[0] = 'PUSH';
							this.rowData.checkedChannel[1] = 'KAKAO'
						}
						if (rcsFlag == true) {
							this.rowData.checkedChannel[0] = 'PUSH';
							this.rowData.checkedChannel[1] = 'RCS'
						}
						if (smsFlag == true) {
							this.rowData.checkedChannel[0] = 'PUSH';
							this.rowData.checkedChannel[1] = 'SMSMMS'
						}
					}
					if (kakaoFlag == true) {
						if (rcsFlag == true) {
							this.rowData.checkedChannel[0] = 'KAKAO';
							this.rowData.checkedChannel[1] = 'RCS'
						}
						if (smsFlag == true) {
							this.rowData.checkedChannel[0] = 'KAKAO';
							this.rowData.checkedChannel[1] = 'SMSMMS'
						}
					}
					if (rcsFlag == true) {
						if (smsFlag == true) {
							this.rowData.checkedChannel[0] = 'RCS';
							this.rowData.checkedChannel[1] = 'SMSMMS'
						}
					}
					jQuery(".borderLightGray").removeClass("active");
				}
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
						if (!this.rowData.brandNm) {
							confirm.fnAlert(this.detailTitle, 'RCS FREE 템플릿 브랜드명을 선택해주세요.');
							return false;
						}
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
						if (!this.rowData.brandNm) {
							confirm.fnAlert(this.detailTitle, 'RCS 승인 서술형 템플릿 브랜드명을 선택해주세요.');
							return false;
						}
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
						if (!this.selectedRcsStyTemplate) { 
							confirm.fnAlert(this.detailTitle, 'RCS 승인 스타일 템플릿을 팝업을 통해 선택해 주세요.');
							return false;
						}
					}
	
					if (this.rcsTemplateTable === 3) {  //SMS
						if (!this.rowData.brandNm) {
							confirm.fnAlert(this.detailTitle, 'RCS SMS 템플릿 브랜드명을 선택해주세요.');
							return false;
						}
						if (!this.rowData.rcs0Content) { 
							confirm.fnAlert(this.detailTitle, 'RCS SMS 템플릿 내용을 입력해주세요.');
							return false;
						}
						if (this.rowData.msgKind == 'A' && !this.rowData.rcsBlockNumber) { 
							confirm.fnAlert(this.detailTitle, 'RCS SMS 템플릿 무료수신거부 정보를 입력해주세요.');
							return false;
						}
						if (!this.rowData.callback) { 
							confirm.fnAlert(this.detailTitle, 'RCS SMS 템플릿 발신번호를 선택해주세요.');
							return false;
						}
					}

					if (this.rcsTemplateTable === 4) {  //LMS
						if (!this.rowData.brandNm) {
							confirm.fnAlert(this.detailTitle, 'RCS LMS 템플릿 브랜드명을 선택해주세요.');
							return false;
						}
						if (!this.rowData.rcs0Title) { 
							confirm.fnAlert(this.detailTitle, 'RCS LMS 템플릿 제목을 입력해주세요.');
							return false;
						}
						if (!this.rowData.rcs0Content) { 
							confirm.fnAlert(this.detailTitle, 'RCS LMS 템플릿 내용을 입력해주세요.');
							return false;
						}
						if (this.rowData.msgKind == 'A' && !this.rowData.rcsBlockNumber) { 
							confirm.fnAlert(this.detailTitle, 'RCS LMS 템플릿 무료수신거부 정보를 입력해주세요.');
							return false;
						}
						if (!this.rowData.callback) { 
							confirm.fnAlert(this.detailTitle, 'RCS LMS 템플릿 발신번호를 선택해주세요.');
							return false;
						}
					}

					if (this.rcsTemplateTable === 5) {  //SHORT
						if (!this.rowData.brandNm) {
							confirm.fnAlert(this.detailTitle, 'RCS 세로형(SHORT) 템플릿 브랜드명을 선택해주세요.');
							return false;
						}
						if (!this.rowData.rcs0Title) { 
							confirm.fnAlert(this.detailTitle, 'RCS 세로형(SHORT) 제목을 입력해주세요.');
							return false;
						}
						if (!this.rowData.rcs0Content) { 
							confirm.fnAlert(this.detailTitle, 'RCS 세로형(SHORT) 내용을 입력해주세요.');
							return false;
						}
						if (this.rowData.msgKind == 'A' && !this.rowData.rcsBlockNumber) { 
							confirm.fnAlert(this.detailTitle, 'RCS 세로형(SHORT) 무료수신거부 정보를 입력해주세요.');
							return false;
						}
						if (!this.rowData.callback) { 
							confirm.fnAlert(this.detailTitle, 'RCS 세로형(SHORT) 템플릿 발신번호를 선택해주세요.');
							return false;
						}
					}

					if (this.rcsTemplateTable === 6) {  //TALL
						if (!this.rowData.brandNm) {
							confirm.fnAlert(this.detailTitle, 'RCS 세로형(TALL) 템플릿 브랜드명을 선택해주세요.');
							return false;
						}
						if (!this.rowData.rcs0Title) { 
							confirm.fnAlert(this.detailTitle, 'RCS 세로형(TALL) 제목을 입력해주세요.');
							return false;
						}
						if (!this.rowData.rcs0Content) { 
							confirm.fnAlert(this.detailTitle, 'RCS 세로형(TALL) 내용을 입력해주세요.');
							return false;
						}
						if (this.rowData.msgKind == 'A' && !this.rowData.rcsBlockNumber) { 
							confirm.fnAlert(this.detailTitle, 'RCS 세로형(TALL) 무료수신거부 정보를 입력해주세요.');
							return false;
						}
						if (!this.rowData.callback) { 
							confirm.fnAlert(this.detailTitle, 'RCS 세로형(TALL) 템플릿 발신번호를 선택해주세요.');
							return false;
						}
					}

					if (this.rcsTemplateTable === 9) {  //CSHORT
						if (this.rowData.msgKind == 'A' && !this.rowData.rcsBlockNumber) { 
							confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(SHORT) 무료수신거부 정보를 입력해주세요.');
							return false;
						}
						if (!this.rowData.callback) { 
							confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(SHORT) 템플릿 발신번호를 선택해주세요.');
							return false;
						}
						if (this.rcs9CardCount >= 3) {
							if (!this.rowData.rcs0Title) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(SHORT) 카드1의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcs0Content) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(SHORT) 카드1의 내용을 입력해주세요.');
								return false;
							}
							if (!this.rowData.callback) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(SHORT) 발신번호를 선택해주세요.');
								return false;
							}
							if (!this.rowData.rcs1Title) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(SHORT) 카드2의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcs1Content) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(SHORT) 카드2의 내용을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcs2Title) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(SHORT) 카드3의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcs2Content) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(SHORT) 카드3의 내용을 입력해주세요.');
								return false;
							}
						}

						if (this.rcs9CardCount >= 4) {
							if (!this.rowData.rcs3Title) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(SHORT) 카드4의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcs3Content) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(SHORT) 카드4의 내용을 입력해주세요.');
								return false;
							}
						}

						if (this.rcs9CardCount >= 5) {
							if (!this.rowData.rcs4Title) {
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(SHORT) 카드5의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcs4Content) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(SHORT) 카드5의 내용을 입력해주세요.');
								return false;
							}
						}

						if (this.rcs9CardCount >= 6) {
							if (!this.rowData.rcs5Title) {
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(SHORT) 카드6의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcs5Content) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(SHORT) 카드6의 내용을 입력해주세요.');
								return false;
							}
						}
					}

					if (this.rcsTemplateTable === 10) {  //CTALL
						if (this.rowData.msgKind == 'A' && !this.rowData.rcsBlockNumber) { 
							confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(TALL) 무료수신거부 정보를 입력해주세요.');
							return false;
						}
						if (!this.rowData.callback) { 
							confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(TALL) 발신번호를 선택해주세요.');
							return false;
						}
						if (this.rcs10CardCount >= 3) {
							if (!this.rowData.rcsCTallImgInfoList[0].rcsTitle) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(TALL) 카드1의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcsCTallImgInfoList[0].rcsContent) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(TALL) 카드1의 내용을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcsCTallImgInfoList[1].rcsTitle) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(TALL) 카드2의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcsCTallImgInfoList[1].rcsContent) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(TALL) 카드2의 내용을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcsCTallImgInfoList[2].rcsTitle) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(TALL) 카드3의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcsCTallImgInfoList[2].rcsContent) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(TALL) 카드3의 내용을 입력해주세요.');
								return false;
							}
						}

						if (this.rcs10CardCount >= 4) {
							if (!this.rowData.rcsCTallImgInfoList[3].rcsTitle) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(TALL) 카드4의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcsCTallImgInfoList[3].rcsContent) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(TALL) 카드4의 내용을 입력해주세요.');
								return false;
							}
						}

						if (this.rcs10CardCount >= 5) {
							if (!this.rowData.rcsCTallImgInfoList[4].rcsTitle) {
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(TALL) 카드5의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcsCTallImgInfoList[4].rcsContent) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(TALL) 카드5의 내용을 입력해주세요.');
								return false;
							}
						}

						if (this.rcs10CardCount >= 6) {
							if (!this.rowData.rcsCTallImgInfoList[5].rcsTitle) {
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(TALL) 카드6의 제목을 입력해주세요.');
								return false;
							}
							if (!this.rowData.rcsCTallImgInfoList[5].rcsContent) { 
								confirm.fnAlert(this.detailTitle, 'RCS 캐러셀형(TALL) 카드6의 내용을 입력해주세요.');
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
						if (this.rowData.msgKind == 'A') {
							if (!this.rowData.smsRcvblcNumber) {
								confirm.fnAlert(this.detailTitle, '광고성메시지 수선거부번호를 입력해주세요.');
								return false;
							}
						}
						if (this.msgSmsCurrByte > this.msgSmsLimitByte) {
							confirm.fnAlert(this.detailTitle, '허용 된 메시지 길이를 초과하였습니다.');
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
						if (this.rowData.msgKind == 'A') {
							if (!this.rowData.smsRcvblcNumber) {
								confirm.fnAlert(this.detailTitle, '광고성메시지 수선거부번호를 입력해주세요.');
								return false;
							}
						}
						if (this.msgSmsCurrByte > this.msgSmsLimitByte) {
							confirm.fnAlert(this.detailTitle, '허용 된 메시지 길이를 초과하였습니다.');
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

			if (data.radioBtn == 'des') {
				vm.rowData.rcsDesMessagebaseId = data.messagebaseId;
				vm.rowData.brandNm = data.brandId;
				vm.rowData.rcsDesFormNm = data.formNm;
				vm.rowData.rcsDesMessagebaseformId = data.messagebaseformId;
				vm.rowData.rcs0Content = data.desContent;
				vm.rowData.rcsDesButtons = data.rcsButtonList;

				this.selectedRcsDesTemplate = true;
			} else {
				vm.rowData.rcsStyleMessagebaseId = data.messagebaseId;
				vm.rowData.brandNm = data.brandId;
				vm.rowData.rcsStyleFormNm = data.formNm;
				vm.rowData.rcsStyleMessagebaseformId = data.messagebaseformId;
				vm.rowData.rcsStyleContentCnt = data.styleContentCnt;
				vm.rowData.rcsStyleInput = data.styleInput;
				vm.rowData.rcsStyleInputSec = data.styleInputSec;
				vm.rowData.rcsStyleChk = data.styleChk;
				vm.rowData.rcsStyleButtons = data.rcsButtonList;

				this.selectedRcsStyTemplate = true;
			}

			var params = {brandId : data.brandId};
			messageApi.selectCallbackList(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.rcsCallbackList = result.data;
				} else {
					confirm.fnAlert("RCS 템플릿 승인 서술 발신번호 리스트 조회", result.message);
				}
			});
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
								this.fnSelectBrandList();
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
						this.rowData.tmpltStatus				= rtnData.tmpltStatus;

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
							this.templateRadioBtn = 'des';
						} else if (rtnData.rcsPrdType == 'CELL') {
							this.rcsTemplateTable = 2;
							this.rcsTemplateTableChecked = 2;
							this.selectedRcsStyTemplate = true;
							this.templateRadioBtn = 'cell';
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
						jQuery('input:radio[name=rcsTemplate1]').attr('disabled', true);
						jQuery("#rcsPrdNoti").attr("style", "display:block;");

						if (rtnData.rcsPrdType == 'FREE') {
							this.rowData.brandNm				= rtnData.rcsBrandNm;
							this.fnChgBrandValue(rtnData.rcsBrandNm);
							this.rowData.rcs0Content			= this.$gfnCommonUtils.unescapeXss(rtnData.rcs0Content);
							this.rowData.callback					= rtnData.rcsCallback; //발신번호
							this.rowData.rcsBlockNumber		= rtnData.rcsBlockNumber; //수신거부번호
						}

						if (rtnData.rcsPrdType == 'DESCRIPTION') {
							this.rowData.rcsDesMessagebaseId			= rtnData.rcsMessagebaseId;
							this.rowData.brandNm							= rtnData.rcsBrandNm;
							this.fnChgBrandValue(rtnData.rcsBrandNm);
							this.rowData.rcsDesFormNm					= rtnData.rcsDesFormNm;
							this.rowData.rcsDesMessagebaseformId	= rtnData.rcsMessagebaseformId;
							this.rowData.rcs0Content						= this.$gfnCommonUtils.unescapeXss(rtnData.rcs0Content);
							this.rowData.callback								= rtnData.rcsCallback; //발신번호
							this.rowData.rcsDesButtons						= rtnData.rcsButton0Data;
						}

						if (rtnData.rcsPrdType == 'CELL') {
							this.rowData.rcsStyleMessagebaseId		= rtnData.rcsMessagebaseId;
							this.rowData.brandNm							= rtnData.rcsBrandNm;
							this.fnChgBrandValue(rtnData.rcsBrandNm);
							this.rowData.rcsStyleFormNm					= rtnData.rcsStyleFormNm;
							this.rowData.rcsStyleMessagebaseformId	= rtnData.rcsMessagebaseformId;
							this.rowData.rcsStyleContentCnt				= rtnData.rcsStyleCount;
							this.rowData.rcsStyleInput						= rtnData.rcsStyleInput;
							this.rowData.rcsStyleInputSec					= rtnData.rcsStyleInputSec;
							this.rowData.rcsStyleChk						= rtnData.rcsStyleChk;
							this.rowData.callback								= rtnData.rcsCallback; //발신번호
							this.rowData.rcsStyleButtons					= rtnData.rcsButton0Data;
						}

						if (rtnData.rcsPrdType == 'SMS') {
							this.rowData.brandNm				= rtnData.rcsBrandNm;
							this.fnChgBrandValue(rtnData.rcsBrandNm);
							this.rowData.rcs0Content			= this.$gfnCommonUtils.unescapeXss(rtnData.rcs0Content);
							this.rowData.rcsBlockNumber		= rtnData.rcsBlockNumber;		//수신거부번호
							this.rowData.callback					= rtnData.rcsCallback;			//발신번호

							if (!this.isEmpty(rtnData.rcsButton0Data)) {
								if (rtnData.rcsButton0Data.length > 0) {
									this.buttonSMSFlag = true;
									this.rowData.rcsSMSButtons	= rtnData.rcsButton0Data;
								}
							}
						}
	
						if (rtnData.rcsPrdType == 'LMS') {
							this.rowData.brandNm				= rtnData.rcsBrandNm;
							this.fnChgBrandValue(rtnData.rcsBrandNm);
							this.rowData.rcs0Title					= this.$gfnCommonUtils.unescapeXss(rtnData.rcs0Title);
							this.rowData.rcs0Content			= this.$gfnCommonUtils.unescapeXss(rtnData.rcs0Content);
							this.rowData.rcsBlockNumber		= rtnData.rcsBlockNumber;		//수신거부번호
							this.rowData.callback					= rtnData.rcsCallback;			//발신번호

							if (!this.isEmpty(rtnData.rcsButton0Data)) {
								if (rtnData.rcsButton0Data.length > 0) {
									this.buttonLMSFlag = true;
									this.rowData.rcsLMSButtons	= rtnData.rcsButton0Data;
								}
							}
						}
	
						if (rtnData.rcsPrdType == 'SHORT') {
							this.rowData.brandNm				= rtnData.rcsBrandNm;
							this.fnChgBrandValue(rtnData.rcsBrandNm);
							this.rowData.rcs0Title					= this.$gfnCommonUtils.unescapeXss(rtnData.rcs0Title);
							this.rowData.rcs0Content 			= this.$gfnCommonUtils.unescapeXss(rtnData.rcs0Content);
							this.rowData.rcsBlockNumber		= rtnData.rcsBlockNumber;		//수신거부번호
							this.rowData.callback					= rtnData.rcsCallback;			//발신번호
							if (rtnData.rcs0Media != '' && rtnData.rcs0MediaUrl != '') {
								this.rowData.rcsShortImgInfoList.push({'fileId':rtnData.rcs0Media, 'imgUrl':rtnData.rcs0MediaUrl});
							}

							if (!this.isEmpty(rtnData.rcsButton0Data)) {
								if (rtnData.rcsButton0Data.length > 0) {
									this.buttonShortFlag = true;
									this.rowData.rcsShortButtons	= rtnData.rcsButton0Data;
								}
							}
						}
	
						if (rtnData.rcsPrdType == 'TALL') {
							this.rowData.brandNm				= rtnData.rcsBrandNm;
							this.fnChgBrandValue(rtnData.rcsBrandNm);
							this.rowData.rcs0Title					= this.$gfnCommonUtils.unescapeXss(rtnData.rcs0Title);
							this.rowData.rcs0Content 			= this.$gfnCommonUtils.unescapeXss(rtnData.rcs0Content);
							this.rowData.rcsBlockNumber		= rtnData.rcsBlockNumber;		//수신거부번호
							this.rowData.callback					= rtnData.rcsCallback;			//발신번호
							if (rtnData.rcs0Media != '' && rtnData.rcs0MediaUrl != '') {
								this.rowData.rcsTallImgInfoList.push({'fileId':rtnData.rcs0Media, 'imgUrl':rtnData.rcs0MediaUrl});
							}

							if (!this.isEmpty(rtnData.rcsButton0Data)) {
								if (rtnData.rcsButton0Data.length > 0) {
									this.buttonTallFlag = true;
									this.rowData.rcsTallButtons	= rtnData.rcsButton0Data;
								}
							}
						}
	
						if (rtnData.rcsPrdType == 'CSHORT') {
							this.rowData.brandNm				= rtnData.rcsBrandNm;
							this.fnChgBrandValue(rtnData.rcsBrandNm);
							this.rcs9CardCount						= rtnData.rcsCardCount;
							this.rowData.rcsBlockNumber		= rtnData.rcsBlockNumber;   //수신거부번호
							this.rowData.callback					= rtnData.rcsCallback; //발신번호
	
							this.rowData.rcs0Title					= this.$gfnCommonUtils.unescapeXss(rtnData.rcs0Title);
							this.rowData.rcs0Content			= this.$gfnCommonUtils.unescapeXss(rtnData.rcs0Content);
							this.rowData.rcs1Title					= this.$gfnCommonUtils.unescapeXss(rtnData.rcs1Title);
							this.rowData.rcs1Content			= this.$gfnCommonUtils.unescapeXss(rtnData.rcs1Content);
							this.rowData.rcs2Title					= this.$gfnCommonUtils.unescapeXss(rtnData.rcs2Title);
							this.rowData.rcs2Content			= this.$gfnCommonUtils.unescapeXss(rtnData.rcs2Content);
							this.rowData.rcs3Title					= this.$gfnCommonUtils.unescapeXss(rtnData.rcs3Title);
							this.rowData.rcs3Content			= this.$gfnCommonUtils.unescapeXss(rtnData.rcs3Content);
							this.rowData.rcs4Title					= this.$gfnCommonUtils.unescapeXss(rtnData.rcs4Title);
							this.rowData.rcs4Content			= this.$gfnCommonUtils.unescapeXss(rtnData.rcs4Content);
							this.rowData.rcs5Title					= this.$gfnCommonUtils.unescapeXss(rtnData.rcs5Title);
							this.rowData.rcs5Content			= this.$gfnCommonUtils.unescapeXss(rtnData.rcs5Content);

							if (rtnData.rcs0Media != '' && rtnData.rcs0MediaUrl != '') {
								this.rowData.rcs90ImgInfoList.push({'fileId':rtnData.rcs0Media, 'imgUrl':rtnData.rcs0MediaUrl});
							}
							if (rtnData.rcs1Media != '' && rtnData.rcs1MediaUrl != '') {
								this.rowData.rcs91ImgInfoList.push({'fileId':rtnData.rcs1Media, 'imgUrl':rtnData.rcs1MediaUrl});
							}
							if (rtnData.rcs2Media != '' && rtnData.rcs2MediaUrl != '') {
								this.rowData.rcs92ImgInfoList.push({'fileId':rtnData.rcs2Media, 'imgUrl':rtnData.rcs2MediaUrl});
							}
							if (rtnData.rcs3Media != '' && rtnData.rcs3MediaUrl != '') {
								this.rowData.rcs93ImgInfoList.push({'fileId':rtnData.rcs3Media, 'imgUrl':rtnData.rcs3MediaUrl});
							}
							if (rtnData.rcs4Media != '' && rtnData.rcs4MediaUrl != '') {
								this.rowData.rcs94ImgInfoList.push({'fileId':rtnData.rcs4Media, 'imgUrl':rtnData.rcs4MediaUrl});
							}
							if (rtnData.rcs5Media != '' && rtnData.rcs5MediaUrl != '') {
								this.rowData.rcs95ImgInfoList.push({'fileId':rtnData.rcs5Media, 'imgUrl':rtnData.rcs5MediaUrl});
							}

							if (!this.isEmpty(rtnData.rcsButton0Data)) {
								if (rtnData.rcsButton0Data.length > 0) {
									this.button90Flag = true;
									this.rowData.rcs90Buttons	= rtnData.rcsButton0Data;
								}
							}
							if (!this.isEmpty(rtnData.rcsButton1Data)) {
								if (rtnData.rcsButton1Data.length > 0) {
									this.button91Flag = true;
									this.rowData.rcs91Buttons	= rtnData.rcsButton1Data;
								}
							}
							if (!this.isEmpty(rtnData.rcsButton2Data)) {
								if (rtnData.rcsButton2Data.length > 0) {
									this.button92Flag = true;
									this.rowData.rcs92Buttons	= rtnData.rcsButton2Data;
								}
							}
							if (!this.isEmpty(rtnData.rcsButton3Data)) {
								if (rtnData.rcsButton3Data.length > 0) {
									this.button93Flag = true;
									this.rowData.rcs93Buttons	= rtnData.rcsButton3Data;
								}
							}
							if (!this.isEmpty(rtnData.rcsButton4Data)) {
								if (rtnData.rcsButton4Data.length > 0) {
									this.button94Flag = true;
									this.rowData.rcs94Buttons	= rtnData.rcsButton4Data;
								}
							}
							if (!this.isEmpty(rtnData.rcsButton5Data)) {
								if (rtnData.rcsButton5Data.length > 0) {
									this.button95Flag = true;
									this.rowData.rcs95Buttons	= rtnData.rcsButton5Data;
								}
							}
						}
	
						if (rtnData.rcsPrdType == 'CTALL') {
							this.rowData.brandNm				= rtnData.rcsBrandNm;
							this.fnChgBrandValue(rtnData.rcsBrandNm);
							this.rcs10CardCount					= rtnData.rcsCardCount;
							this.rowData.rcsBlockNumber		= rtnData.rcsBlockNumber;   //수신거부번호
							this.rowData.callback					= rtnData.rcsCallback; //발신번호
	
							this.rowData.rcs0Title					= this.$gfnCommonUtils.unescapeXss(rtnData.rcs0Title);
							this.rowData.rcs0Content			= this.$gfnCommonUtils.unescapeXss(rtnData.rcs0Content);
							this.rowData.rcs1Title					= this.$gfnCommonUtils.unescapeXss(rtnData.rcs1Title);
							this.rowData.rcs1Content			= this.$gfnCommonUtils.unescapeXss(rtnData.rcs1Content);
							this.rowData.rcs2Title					= this.$gfnCommonUtils.unescapeXss(rtnData.rcs2Title);
							this.rowData.rcs2Content			= this.$gfnCommonUtils.unescapeXss(rtnData.rcs2Content);
							this.rowData.rcs3Title					= this.$gfnCommonUtils.unescapeXss(rtnData.rcs3Title);
							this.rowData.rcs3Content			= this.$gfnCommonUtils.unescapeXss(rtnData.rcs3Content);
							this.rowData.rcs4Title					= this.$gfnCommonUtils.unescapeXss(rtnData.rcs4Title);
							this.rowData.rcs4Content			= this.$gfnCommonUtils.unescapeXss(rtnData.rcs4Content);
							this.rowData.rcs5Title					= this.$gfnCommonUtils.unescapeXss(rtnData.rcs5Title);
							this.rowData.rcs5Content			= this.$gfnCommonUtils.unescapeXss(rtnData.rcs5Content);

							if (rtnData.rcs0Media != '' && rtnData.rcs0MediaUrl != '') {
								this.rowData.rcs100ImgInfoList.push({'fileId':rtnData.rcs0Media, 'imgUrl':rtnData.rcs0MediaUrl});
							}
							if (rtnData.rcs1Media != '' && rtnData.rcs1MediaUrl != '') {
								this.rowData.rcs101ImgInfoList.push({'fileId':rtnData.rcs1Media, 'imgUrl':rtnData.rcs1MediaUrl});
							}
							if (rtnData.rcs2Media != '' && rtnData.rcs2MediaUrl != '') {
								this.rowData.rcs102ImgInfoList.push({'fileId':rtnData.rcs2Media, 'imgUrl':rtnData.rcs2MediaUrl});
							}
							if (rtnData.rcs3Media != '' && rtnData.rcs3MediaUrl != '') {
								this.rowData.rcs103ImgInfoList.push({'fileId':rtnData.rcs3Media, 'imgUrl':rtnData.rcs3MediaUrl});
							}
							if (rtnData.rcs4Media != '' && rtnData.rcs4MediaUrl != '') {
								this.rowData.rcs104ImgInfoList.push({'fileId':rtnData.rcs4Media, 'imgUrl':rtnData.rcs4MediaUrl});
							}
							if (rtnData.rcs5Media != '' && rtnData.rcs5MediaUrl != '') {
								this.rowData.rcs105ImgInfoList.push({'fileId':rtnData.rcs5Media, 'imgUrl':rtnData.rcs5MediaUrl});
							}

							if (!this.isEmpty(rtnData.rcsButton0Data)) {
								if (rtnData.rcsButton0Data.length > 0) {
									this.button100Flag = true;
								}
							}
							if (!this.isEmpty(rtnData.rcsButton1Data)) {
								if (rtnData.rcsButton1Data.length > 0) {
									this.button101Flag = true;
								}
							}
							if (!this.isEmpty(rtnData.rcsButton2Data)) {
								if (rtnData.rcsButton2Data.length > 0) {
									this.button102Flag = true;
								}
							}
							if (!this.isEmpty(rtnData.rcsButton3Data)) {
								if (rtnData.rcsButton3Data.length > 0) {
									this.button103Flag = true;
								}
							}
							if (!this.isEmpty(rtnData.rcsButton4Data)) {
								if (rtnData.rcsButton4Data.length > 0) {
									this.button104Flag = true;
								}
							}
							if (!this.isEmpty(rtnData.rcsButton5Data)) {
								if (rtnData.rcsButton5Data.length > 0) {
									this.button105Flag = true;
								}
							}

							this.rowData.rcsCTallImgInfoList.push({'fileId':rtnData.rcs0Media, 'imgUrl':rtnData.rcs0MediaUrl, 'rcsTitle':this.$gfnCommonUtils.unescapeXss(rtnData.rcs0Title), 'rcsContent':this.$gfnCommonUtils.unescapeXss(rtnData.rcs0Content),  'rcsButtons':rtnData.rcsButton0Data});
							this.rowData.rcsCTallImgInfoList.push({'fileId':rtnData.rcs1Media, 'imgUrl':rtnData.rcs1MediaUrl, 'rcsTitle':this.$gfnCommonUtils.unescapeXss(rtnData.rcs1Title), 'rcsContent':this.$gfnCommonUtils.unescapeXss(rtnData.rcs1Content),  'rcsButtons':rtnData.rcsButton1Data});
							this.rowData.rcsCTallImgInfoList.push({'fileId':rtnData.rcs2Media, 'imgUrl':rtnData.rcs2MediaUrl, 'rcsTitle':this.$gfnCommonUtils.unescapeXss(rtnData.rcs2Title), 'rcsContent':this.$gfnCommonUtils.unescapeXss(rtnData.rcs2Content),  'rcsButtons':rtnData.rcsButton2Data});
							if (rtnData.rcsCardCount == 4 || rtnData.rcsCardCount == 5 || rtnData.rcsCardCount == 6) {
								this.rowData.rcsCTallImgInfoList.push({'fileId':rtnData.rcs3Media, 'imgUrl':rtnData.rcs3MediaUrl, 'rcsTitle':this.$gfnCommonUtils.unescapeXss(rtnData.rcs3Title), 'rcsContent':this.$gfnCommonUtils.unescapeXss(rtnData.rcs3Content),  'rcsButtons':rtnData.rcsButton3Data});
							}
							if (rtnData.rcsCardCount == 5 || rtnData.rcsCardCount == 6) {
								this.rowData.rcsCTallImgInfoList.push({'fileId':rtnData.rcs4Media, 'imgUrl':rtnData.rcs4MediaUrl, 'rcsTitle':this.$gfnCommonUtils.unescapeXss(rtnData.rcs4Title), 'rcsContent':this.$gfnCommonUtils.unescapeXss(rtnData.rcs4Content),  'rcsButtons':rtnData.rcsButton4Data});
							}
							if (rtnData.rcsCardCount == 6) {
								this.rowData.rcsCTallImgInfoList.push({'fileId':rtnData.rcs5Media, 'imgUrl':rtnData.rcs5MediaUrl, 'rcsTitle':this.$gfnCommonUtils.unescapeXss(rtnData.rcs5Title), 'rcsContent':this.$gfnCommonUtils.unescapeXss(rtnData.rcs5Content),  'rcsButtons':rtnData.rcsButton5Data});
							}
							console.log(this.rowData.rcsCTallImgInfoList);
						}
	
						// FRIENDTALK DATA SET
						this.rowData.friendTalkSenderKeyType	= rtnData.friendTalkSenderKeyType;
						this.rowData.friendTalkSenderKey 		= rtnData.friendTalkSenderKey;
						this.rowData.friendTalkImageLink 		= rtnData.friendTalkImageLink;
						this.rowData.friendTalkContent 			= this.$gfnCommonUtils.unescapeXss(rtnData.friendTalkContent);
						if (this.rowData.msgType == 'IMAGE') {
							this.rowData.friendTalkImgInfo.fileId 	= rtnData.friendTalkFileId;
							this.rowData.friendTalkImgInfo.imgUrl	= rtnData.friendTalkImgUrl;
							this.rowData.friendTalkImgInfo.wideImgYn = rtnData.friendTalkWideImageYn;
						}
	
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
							this.rowData.smsRcvblcNumber = this.$gfnCommonUtils.unescapeXss(rtnData.smsRcvblcNumber);
							this.fnGetSmsLimitByte();
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
		// RCS 버튼 추가
		addRowRcsButton: function(rcsType) {
			var action = {
				linkType : 'urlAction',
				displayText : '',
				postback : {data : ''},
				urlAction : {openUrl : {url : ''}},
				clipboardAction : {copyToClipboard : {text : ''}},
				dialerAction : {dialPhoneNumber : {phoneNumber : ''}},
				calendarAction : {createCalendarEvent : {title : '', description : '', startTime : this.$gfnCommonUtils.getCurretDate(), endTime : this.$gfnCommonUtils.getCurretDate()}},
				mapAction : {}
			};
			let temp = {
				'action': action
			};

			if (rcsType == 'SMS') {
				if (this.rowData.rcsSMSButtons.length >= 1) {
					confirm.fnAlert(this.componentsTitle, "button은 1개까지 추가가능합니다.");
				} else {
					this.buttonSMSFlag = true;
					this.rowData.rcsSMSButtons.push(temp);
				}
			} else if (rcsType == 'LMS') {
				if (this.rowData.rcsLMSButtons.length >= 3) {
					confirm.fnAlert(this.componentsTitle, "button은 3개까지 추가가능합니다.");
				} else {
					this.buttonLMSFlag = true;
					this.rowData.rcsLMSButtons.push(temp);
				}
			} else if (rcsType == 'SHORT') {
				if (this.rowData.rcsShortButtons.length >= 2) {
					confirm.fnAlert(this.componentsTitle, "button은 2개까지 추가가능합니다.");
				} else {
					this.buttonShortFlag = true;
					this.rowData.rcsShortButtons.push(temp);
				}
			} else if (rcsType == 'TALL') {
				if (this.rowData.rcsTallButtons.length >= 2) {
					confirm.fnAlert(this.componentsTitle, "button은 2개까지 추가가능합니다.");
				} else {
					this.buttonTallFlag = true;
					this.rowData.rcsTallButtons.push(temp);
				}
			} else if (rcsType == 'CSHORT_TAB1') {
				if (this.rowData.rcs90Buttons.length >= 2) {
					confirm.fnAlert(this.componentsTitle, "button은 2개까지 추가가능합니다.");
				} else {
					this.button90Flag = true;
					this.rowData.rcs90Buttons.push(temp);
				}
			} else if (rcsType == 'CSHORT_TAB2') {
				if (this.rowData.rcs91Buttons.length >= 2) {
					confirm.fnAlert(this.componentsTitle, "button은 2개까지 추가가능합니다.");
				} else {
					this.button91Flag = true;
					this.rowData.rcs91Buttons.push(temp);
				}
			} else if (rcsType == 'CSHORT_TAB3') {
				if (this.rowData.rcs92Buttons.length >= 2) {
					confirm.fnAlert(this.componentsTitle, "button은 2개까지 추가가능합니다.");
				} else {
					this.button92Flag = true;
					this.rowData.rcs92Buttons.push(temp);
				}
			} else if (rcsType == 'CSHORT_TAB4') {
				if (this.rowData.rcs93Buttons.length >= 2) {
					confirm.fnAlert(this.componentsTitle, "button은 2개까지 추가가능합니다.");
				} else {
					this.button93Flag = true;
					this.rowData.rcs93Buttons.push(temp);
				}
			} else if (rcsType == 'CSHORT_TAB5') {
				if (this.rowData.rcs94Buttons.length >= 2) {
					confirm.fnAlert(this.componentsTitle, "button은 2개까지 추가가능합니다.");
				} else {
					this.button94Flag = true;
					this.rowData.rcs94Buttons.push(temp);
				}
			} else if (rcsType == 'CSHORT_TAB6') {
				if (this.rowData.rcs95Buttons.length >= 2) {
					confirm.fnAlert(this.componentsTitle, "button은 2개까지 추가가능합니다.");
				} else {
					this.button95Flag = true;
					this.rowData.rcs95Buttons.push(temp);
				}
			} else if (rcsType == 'CTALL_TAB1') {
				if (this.rowData.rcsCTallImgInfoList[0].rcsButtons.length >= 2) {
					confirm.fnAlert(this.componentsTitle, "button은 2개까지 추가가능합니다.");
				} else {
					this.button100Flag = true;
					this.rowData.rcsCTallImgInfoList[0].rcsButtons.push(temp);
				}
			} else if (rcsType == 'CTALL_TAB2') {
				if (this.rowData.rcsCTallImgInfoList[1].rcsButtons.length >= 2) {
					confirm.fnAlert(this.componentsTitle, "button은 2개까지 추가가능합니다.");
				} else {
					this.button101Flag = true;
					this.rowData.rcsCTallImgInfoList[1].rcsButtons.push(temp);
				}
			} else if (rcsType == 'CTALL_TAB3') {
				if (this.rowData.rcsCTallImgInfoList[2].rcsButtons.length >= 2) {
					confirm.fnAlert(this.componentsTitle, "button은 2개까지 추가가능합니다.");
				} else {
					this.button102Flag = true;
					this.rowData.rcsCTallImgInfoList[2].rcsButtons.push(temp);
				}
			} else if (rcsType == 'CTALL_TAB4') {
				if (this.rowData.rcsCTallImgInfoList[3].rcsButtons.length >= 2) {
					confirm.fnAlert(this.componentsTitle, "button은 2개까지 추가가능합니다.");
				} else {
					this.button103Flag = true;
					this.rowData.rcsCTallImgInfoList[3].rcsButtons.push(temp);
				}
			} else if (rcsType == 'CTALL_TAB5') {
				if (this.rowData.rcsCTallImgInfoList[4].rcsButtons.length >= 2) {
					confirm.fnAlert(this.componentsTitle, "button은 2개까지 추가가능합니다.");
				} else {
					this.button104Flag = true;
					this.rowData.rcsCTallImgInfoList[4].rcsButtons.push(temp);
				}
			} else if (rcsType == 'CTALL_TAB6') {
				if (this.rowData.rcsCTallImgInfoList[5].rcsButtons.length >= 2) {
					confirm.fnAlert(this.componentsTitle, "button은 2개까지 추가가능합니다.");
				} else {
					this.button105Flag = true;
					this.rowData.rcsCTallImgInfoList[5].rcsButtons.push(temp);
				}
			}
		},
		// RCS 버튼 삭제
		removeRowRcsButton: function(rcsType, row){
			if (rcsType == 'SMS') {
				this.rowData.rcsSMSButtons.splice(row,1);
			} else if (rcsType == 'LMS') {
				this.rowData.rcsLMSButtons.splice(row,1);
			} else if (rcsType == 'SHORT') {
				this.rowData.rcsShortButtons.splice(row,1);
			} else if (rcsType == 'TALL') {
				this.rowData.rcsTallButtons.splice(row,1);
			} else if (rcsType == 'CSHORT_TAB1') {
				this.rowData.rcs90Buttons.splice(row,1);
			} else if (rcsType == 'CSHORT_TAB2') {
				this.rowData.rcs91Buttons.splice(row,1);
			} else if (rcsType == 'CSHORT_TAB3') {
				this.rowData.rcs92Buttons.splice(row,1);
			} else if (rcsType == 'CSHORT_TAB4') {
				this.rowData.rcs93Buttons.splice(row,1);
			} else if (rcsType == 'CSHORT_TAB5') {
				this.rowData.rcs94Buttons.splice(row,1);
			} else if (rcsType == 'CSHORT_TAB6') {
				this.rowData.rcs95Buttons.splice(row,1);
			} else if (rcsType == 'CTALL_TAB1') {
				this.rowData.rcsCTallImgInfoList[0].rcsButtons.splice(row,1);
			} else if (rcsType == 'CTALL_TAB2') {
				this.rowData.rcsCTallImgInfoList[1].rcsButtons.splice(row,1);
			} else if (rcsType == 'CTALL_TAB3') {
				this.rowData.rcsCTallImgInfoList[2].rcsButtons.splice(row,1);
			} else if (rcsType == 'CTALL_TAB4') {
				this.rowData.rcsCTallImgInfoList[3].rcsButtons.splice(row,1);
			} else if (rcsType == 'CTALL_TAB5') {
				this.rowData.rcsCTallImgInfoList[4].rcsButtons.splice(row,1);
			} else if (rcsType == 'CTALL_TAB6') {
				this.rowData.rcsCTallImgInfoList[5].rcsButtons.splice(row,1);
			}
		},
		addRowFriendTalk: function(){
			if (this.rowData.friendTalkButtons.length >= 5) {
				confirm.fnAlert(this.componentsTitle, "button은 5개까지 추가가능합니다.");
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
		// 통합발송 캐러셀형(카드1) 이미지 추가 팝업 호출
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
		fnRcsCarouselTallOpenImageManagePopUp(idx){
			if (idx == 0) {
				if (this.fnRcs100ImgLimitSize() == false) return;
				this.$refs.rcs100ImgMng.fnSearch();
				this.rcs100ImgMngOpen = !this.rcs100ImgMngOpen;
			} else if (idx == 1) {
				if (this.fnRcs101ImgLimitSize() == false) return;
				this.$refs.rcs101ImgMng.fnSearch();
				this.rcs101ImgMngOpen = !this.rcs101ImgMngOpen;
			} else if (idx == 2) {
				if (this.fnRcs102ImgLimitSize() == false) return;
				this.$refs.rcs102ImgMng.fnSearch();
				this.rcs102ImgMngOpen = !this.rcs102ImgMngOpen;
			} else if (idx == 3) {
				if (this.fnRcs103ImgLimitSize() == false) return;
				this.$refs.rcs103ImgMng.fnSearch();
				this.rcs103ImgMngOpen = !this.rcs103ImgMngOpen;
			} else if (idx == 4) {
				if (this.fnRcs104ImgLimitSize() == false) return;
				this.$refs.rcs104ImgMng.fnSearch();
				this.rcs104ImgMngOpen = !this.rcs104ImgMngOpen;
			} else if (idx == 5) {
				if (this.fnRcs105ImgLimitSize() == false) return;
				this.$refs.rcs105ImgMng.fnSearch();
				this.rcs105ImgMngOpen = !this.rcs105ImgMngOpen;
			}
		},
		fnRcsCarouselTallDelImg(idx){
			if (idx == 0) {
				this.rowData.rcs100ImgInfoList.splice(idx, 1);
				this.rowData.rcsCTallImgInfoList[0].fileId = '';
				this.rowData.rcsCTallImgInfoList[0].imgUrl = '';
			} else if (idx == 1) {
				this.rowData.rcs101ImgInfoList.splice(idx, 1);
				this.rowData.rcsCTallImgInfoList[1].fileId = '';
				this.rowData.rcsCTallImgInfoList[1].imgUrl = '';
			} else if (idx == 2) {
				this.rowData.rcs102ImgInfoList.splice(idx, 1);
				this.rowData.rcsCTallImgInfoList[2].fileId = '';
				this.rowData.rcsCTallImgInfoList[2].imgUrl = '';
			} else if (idx == 3) {
				this.rowData.rcs103ImgInfoList.splice(idx, 1);
				this.rowData.rcsCTallImgInfoList[3].fileId = '';
				this.rowData.rcsCTallImgInfoList[3].imgUrl = '';
			} else if (idx == 4) {
				this.rowData.rcs104ImgInfoList.splice(idx, 1);
				this.rowData.rcsCTallImgInfoList[4].fileId = '';
				this.rowData.rcsCTallImgInfoList[4].imgUrl = '';
			} else if (idx == 5) {
				this.rowData.rcs105ImgInfoList.splice(idx, 1);
				this.rowData.rcsCTallImgInfoList[5].fileId = '';
				this.rowData.rcsCTallImgInfoList[5].imgUrl = '';
			}
		},
		fnRcs100CallbackImgInfo(imgInfo){
			if (this.fnRcs100ImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};
			this.rowData.rcs100ImgInfoList.push(temp);
			this.rowData.rcsCTallImgInfoList[0].fileId = temp.fileId;
			this.rowData.rcsCTallImgInfoList[0].imgUrl = temp.imgUrl;
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
			if (this.rowData.rcsCTallImgInfoList[0].fileId.length > 0 && this.rowData.rcsCTallImgInfoList[0].imgUrl.length > 0) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 1개까지 등록 가능합니다.');
				return false;
			}
		},
		fnRcs101CallbackImgInfo(imgInfo){
			if (this.fnRcs101ImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};
			this.rowData.rcs101ImgInfoList.push(temp);
			this.rowData.rcsCTallImgInfoList[1].fileId = temp.fileId;
			this.rowData.rcsCTallImgInfoList[1].imgUrl = temp.imgUrl;
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
			if (this.rowData.rcsCTallImgInfoList[1].fileId.length > 0 && this.rowData.rcsCTallImgInfoList[1].imgUrl.length > 0) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 1개까지 등록 가능합니다.');
				return false;
			}
		},
		fnRcs102CallbackImgInfo(imgInfo){
			if (this.fnRcs102ImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};
			this.rowData.rcs102ImgInfoList.push(temp);
			this.rowData.rcsCTallImgInfoList[2].fileId = temp.fileId;
			this.rowData.rcsCTallImgInfoList[2].imgUrl = temp.imgUrl;
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
			if (this.rowData.rcsCTallImgInfoList[2].fileId.length > 0 && this.rowData.rcsCTallImgInfoList[2].imgUrl.length > 0) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 1개까지 등록 가능합니다.');
				return false;
			}
		},
		fnRcs103CallbackImgInfo(imgInfo){
			if (this.fnRcs103ImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};
			this.rowData.rcs103ImgInfoList.push(temp);
			this.rowData.rcsCTallImgInfoList[3].fileId = temp.fileId;
			this.rowData.rcsCTallImgInfoList[3].imgUrl = temp.imgUrl;
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
			if (this.rowData.rcsCTallImgInfoList[3].fileId.length > 0 && this.rowData.rcsCTallImgInfoList[3].imgUrl.length > 0) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 1개까지 등록 가능합니다.');
				return false;
			}
		},
		fnRcs104CallbackImgInfo(imgInfo){
			if (this.fnRcs104ImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};
			this.rowData.rcs104ImgInfoList.push(temp);
			this.rowData.rcsCTallImgInfoList[4].fileId = temp.fileId;
			this.rowData.rcsCTallImgInfoList[4].imgUrl = temp.imgUrl;
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
			if (this.rowData.rcsCTallImgInfoList[4].fileId.length > 0 && this.rowData.rcsCTallImgInfoList[4].imgUrl.length > 0) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 1개까지 등록 가능합니다.');
				return false;
			}
		},
		fnRcs105CallbackImgInfo(imgInfo){
			if (this.fnRcs105ImgLimitSize() == false) return;
			let temp = {
				imgUrl: imgInfo.chImgUrl,
				fileId: imgInfo.fileId
			};
			this.rowData.rcs105ImgInfoList.push(temp);
			this.rowData.rcsCTallImgInfoList[5].fileId = temp.fileId;
			this.rowData.rcsCTallImgInfoList[5].imgUrl = temp.imgUrl;
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
			if (this.rowData.rcsCTallImgInfoList[5].fileId.length > 0 && this.rowData.rcsCTallImgInfoList[5].imgUrl.length > 0) {
				confirm.fnAlert(this.componentsTitle, '이미지는 최대 1개까지 등록 가능합니다.');
				return false;
			}
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
		fnCarouselString(str1, sIdx, str2) {
			let str = '';
			sIdx = sIdx+1;
			if (!this.isEmpty(str1)) {
				str = str1 + sIdx + str2;
			}
			return str;
		},
		fnCalendarStartDateId(index) {
			return 'searchStartDate' + index;
		},
		fnCalendarEndDateId(index) {
			return 'searchEndDate' + index;
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
		fnSetSmsCurrByte() {
			let title = this.$gfnCommonUtils.defaultIfEmpty(this.rowData.smsTitle, '');
			let body = this.$gfnCommonUtils.defaultIfEmpty(this.rowData.smsContent, '');
			let rcvblcNum = this.$gfnCommonUtils.defaultIfEmpty(this.rowData.smsRcvblcNumber, '');
			let totalMsg = title + body ;
			if (this.rowData.msgKind == 'A') {
				totalMsg += '\n' + rcvblcNum + '(광고)';
			}
			this.msgSmsCurrByte = this.getByte(totalMsg);
		},
		fnGetSmsLimitByte() {
			if (this.rowData.smsSendType == 'S') {
				this.msgSmsLimitByte = 80;
			} else if (this.rowData.smsSendType == 'M') {
				this.msgSmsLimitByte = 2000;
			}
		},
		checkSmsSend(flag) {
			if (flag === "S") {
				this.smsTemplateTable = 0;
			} else if(flag === "M") {
				this.smsTemplateTable = 1;
			}
			this.rowData.smsTitle = "";
			this.rowData.smsContent = "";
			if (this.rowData.msgKind == 'A') {
				this.rowData.smsRcvblcNumber = "";
			}
			this.fnSetSmsCurrByte();
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
		// 메시지 타입 선택 시 EVENT
		checkMsgType(flag) {
			if (flag === "BASE") {
				// 메시지 타입 텍스트 선택 시, RCS상품이 세로형(short, tall)인 경우 프리템플릿으로 초기화
				if (jQuery("input:radio[name=rcsTemplate1]:checked").val() == 5 || jQuery("input:radio[name=rcsTemplate1]:checked").val() == 6) {
					jQuery("input:radio[id=rcsTemplate1-1]").click();
				}
			} else {
				if (this.kakaoTemplateTable == 0) {
					if (this.isEmpty(this.rowData.friendTalkImageLink) || this.rowData.friendTalkImageLink == 'https://') {
						this.rowData.friendTalkImageLink = 'https://';
					}
				}
			}
		},
		fnCallbackRcvblcNum(rcvblcNum){
			this.rowData.smsRcvblcNumber = rcvblcNum;
		},
		fnRcsSmsButtonSD(sltDate, index){
			this.rowData.rcsSMSButtons[index].action.calendarAction.createCalendarEvent.startTime = sltDate;
		},
		fnRcsSmsButtonED(sltDate, index){
			this.rowData.rcsSMSButtons[index].action.calendarAction.createCalendarEvent.endTime = sltDate;
		},
		fnRcsLmsButtonSD(sltDate, index){
			this.rowData.rcsLMSButtons[index].action.calendarAction.createCalendarEvent.startTime = sltDate;
		},
		fnRcsLmsButtonED(sltDate, index){
			this.rowData.rcsLMSButtons[index].action.calendarAction.createCalendarEvent.endTime = sltDate;
		},
		fnRcsShortButtonSD(sltDate, index){
			this.rowData.rcsShortButtons[index].action.calendarAction.createCalendarEvent.startTime = sltDate;
		},
		fnRcsShortButtonED(sltDate, index){
			this.rowData.rcsShortButtons[index].action.calendarAction.createCalendarEvent.endTime = sltDate;
		},
		fnRcsTallButtonSD(sltDate, index){
			this.rowData.rcsTallButtons[index].action.calendarAction.createCalendarEvent.startTime = sltDate;
		},
		fnRcsTallButtonED(sltDate, index){
			this.rowData.rcsTallButtons[index].action.calendarAction.createCalendarEvent.endTime = sltDate;
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
