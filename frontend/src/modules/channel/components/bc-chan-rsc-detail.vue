<template>
	<!-- <div id="content"> -->
		<article>
			<div class="contentHeader mb20">
				<h2>브랜드 등록</h2>				
			</div>

			<!-- 본문 -->
			<div class="row">
				<!-- 미리보기 -->
				<div class="col-xs-4 sub03_4_1_1_1">
					<div class="phoneFixed">
						<div class="phoneWrap">
							<div class="phoneWrap">
								<img src="/se2/images/rcsPhone.svg" alt="">
								<div class="rcsWrap">
									<div class="rcsImage"><img v-bind:src="inputVal.preBgImg" style="height : 200; width : 100%;"></div>
									<div class="rcsProfileImage"><img v-bind:src="inputVal.preProfileImg" width="58px" height="58px"></div>
									<div class="rcsCon" style="height: 275px;">
										<div class="phoneIcon">
											<i class="fas fa-comment color7"></i>
											<i v-if="inputVal.call != ''" class="fas fa-phone color7"></i>
											<i v-if="inputVal.web != ''" class="fas fa-globe color7"></i>
											<i v-if="inputVal.store != ''" class="fas fa-shopping-bag color7"></i>
											<i v-if="inputVal.order != ''" class="fas fa-clipboard-list-check color7"></i>
											<i v-if="inputVal.buy != ''" class="fas fa-usd-circle color7"></i>
											<i v-if="inputVal.ticket != ''" class="fas fa-ticket color7"></i>
											<i v-if="inputVal.moreInfo != ''" class="fas fa-info-circle color7"></i>
										</div>							
										<div>
											<div class="text">
												<p class="text-main">전화번호</p>
												<p class="text-sub">{{this.inputVal.tel}}</p>
											</div>
											<div class="text mt15">
												<p class="text-main">웹사이트</p>
												<p class="text-sub">{{this.inputVal.webSiteUrl}}</p>
											</div>
											<div class="text mt15">
												<p class="text-main">이메일</p>
												<p class="text-sub">{{this.inputVal.email}}@{{this.inputVal.email2}}</p>
											</div>
											<div class="text mt15">
												<p class="text-main">주소</p>
												<p class="text-sub">
													{{this.inputVal.zipCode}} {{this.inputVal.roadAddress}}
													<br>{{this.inputVal.detailAddress}}
												</p>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 미리보기 -->
				<!-- 인풋 -->
				<div class="col-xs-6 of_h consoleCon">
					<div class="of_h">
						<h4 style="width:28%" class="inline-block">API Key</h4>
						<input id="apiKey" type="text" class="inputStyle float-right" style="width:72%" v-model="inputVal.apiKey">
					</div>
					<div class="of_h">
						<h4 style="width:28%" class="inline-block">API Secret Key *</h4>
						<div class="inline-block float-right" style="width:72%">
							<input id="apiSecret" type="text" class="inputStyle float-left" style="width:70%" v-model="inputVal.apiSecret">
							<a @click="fnCheckApiKey" class="btnStyle1 borderLightGray backWhite minWidthAuto float-right" style="width:27%">확인</a>
						</div>
					</div>
					<div class="of_h">
						<h4 style="width:28%" class="inline-block">브랜드명 *</h4>
						<input id="brandName" type="text" class="inputStyle float-right" style="width:72%" v-model="inputVal.name" :disabled="this.duplCheckYn == 'N'">
					</div>								
					<div class="of_h">
						<h4 style="width:28%" class="inline-block">브랜드 설명 *</h4>
						<input id="brandDesc" type="text" class="inputStyle float-right" style="width:72%;" v-model="inputVal.description" :disabled="this.duplCheckYn == 'N'" >
					</div>				
									
					<div class="of_h consolMarginTop">
						<h4 style="width:28%" class="inline-block topH4">타 프로젝트<br>사용여부</h4>
						<div class="inline-block float-right" style="width:72%">
							<input type="radio" name="otherProjectYn" value="Y" class="cBox" id="yes" checked="" :disabled="this.duplCheckYn == 'N'"> <label for="yes" class="payment mt10 mr30">공용</label>
							<input type="radio" name="otherProjectYn" value="N" class="cBox" id="no" :disabled="this.duplCheckYn == 'N'"> <label for="no" class="payment mt10">전용</label>		
						</div>
					</div>

					<div class="of_h">
						<h4 style="width:28%" class="inline-block">백그라운드 이미지</h4>
						<input type="file" class="btnStyle7 minWidthAuto float float-right" style="width:72%" ref="bgImgFile" @change="fnPreviewImg('bg')" :disabled="this.duplCheckYn == 'N'"/>
					</div>
					<p class="font-size12 color3 mt10 ml_28"><i class="far fa-info-circle"></i>최대사이즈 : 1080X1080px / 1:1 비율 권장 / 파일형식 : jpg, png (최대 1MB)</p>

					<div class="of_h consolMarginTop">
						<h4 style="width:28%" class="inline-block">프로필 이미지 *</h4>
						<input type="file" class="btnStyle7 minWidthAuto float float-right" style="width:72%" ref="profileImgFile" @change="fnPreviewImg('profile')" :disabled="this.duplCheckYn == 'N'"/>
					</div>
					<p class="font-size12 color3 mt10 ml_28"><i class="far fa-info-circle"></i>최대사이즈 : 1080X1080px / 1:1 비율 권장 / 파일형식 : jpg, png (최대 1MB)</p>

					<div class="of_h consolMarginTop">
						<h4 style="width:28%" class="inline-block">전화번호 *</h4>
						<input id="phoneNum" type="text" class="inputStyle float-right" style="width: 72%" v-model="inputVal.tel" :disabled="this.duplCheckYn == 'N'">
					</div>
					<div class="of_h">
						<h4 style="width:28%" class="inline-block">메뉴버튼설정 *</h4>
						<div class="float-right" style="width:72%">
							<div class="consolCheck" style="width:19%">
								<input type="checkbox" id="Chat" class="checkStyle2" checked="checked" disabled>
								<label for="Chat">Chat</label>
							</div>
							<div class="consolCheck" style="width:19%">
								<input @change="fnClickMenu('call', $event.target.checked)" type="checkbox" id="Call" class="checkStyle2" v-model="inputVal.call" :disabled="this.duplCheckYn == 'N'">
								<label for="Call">Call</label>
							</div>
							<div class="consolCheck" style="width:19%">
								<input @change="fnClickMenu('web', $event.target.checked)" type="checkbox" id="Web" class="checkStyle2" v-model="inputVal.web" :disabled="this.duplCheckYn == 'N'">
								<label for="Web">Web</label>
							</div>
							<div class="consolCheck" style="width:19%">
								<input @change="fnClickMenu('store', $event.target.checked)" type="checkbox" id="Store" class="checkStyle2" v-model="inputVal.store" :disabled="this.duplCheckYn == 'N'">
								<label for="Store">Store</label>
							</div>
							<div class="consolCheck" style="width:19%">
								<input @change="fnClickMenu('order', $event.target.checked)" type="checkbox" id="Order" class="checkStyle2" v-model="inputVal.order" :disabled="this.duplCheckYn == 'N'">
								<label for="Order">Order</label>
							</div>
						</div>
						<div class="ml_28 float-left mt10" style="width:72%">
							<div class="consolCheck" style="width:19%">
								<input @change="fnClickMenu('buy', $event.target.checked)" type="checkbox" id="Buy" class="checkStyle2" v-model="inputVal.buy" :disabled="this.duplCheckYn == 'N'">
								<label for="Buy">Buy</label>
							</div>
							<div class="consolCheck" style="width:19%">
								<input @change="fnClickMenu('ticket', $event.target.checked)" type="checkbox" id="Ticket" class="checkStyle2" v-model="inputVal.ticket" :disabled="this.duplCheckYn == 'N'">
								<label for="Ticket">Ticket</label>
							</div>
							<div class="consolCheck" style="width:25%">
								<input @change="fnClickMenu('moreInfo', $event.target.checked)" type="checkbox" id="More-Info" class="checkStyle2" v-model="inputVal.moreInfo" :disabled="this.duplCheckYn == 'N'">
								<label for="More-Info">More Info</label>
							</div>
						</div>
					</div>

					<div v-if="inputVal.call != ''" class="ml_28 of_h consolMarginTop">
						<p class="inline-block font-size16 float-left mt10" style="width:15%">-Call *</p>
						<input type="text" class="inputStyle float-right" style="width:85%" v-model="inputVal.callWeblink" :disabled="this.duplCheckYn == 'N'">
					</div>
					<div v-if="inputVal.web != ''" class="ml_28 of_h consolMarginTop">
						<p class="inline-block font-size16 float-left mt10" style="width:15%">-Web *</p>
						<input type="text" class="inputStyle float-right" style="width:85%" v-model="inputVal.webWeblink" :disabled="this.duplCheckYn == 'N'">
					</div>
					<div v-if="inputVal.store != ''" class="ml_28 of_h consolMarginTop">
						<p class="inline-block font-size16 float-left mt10" style="width:15%">-Store *</p>
						<input type="text" class="inputStyle float-right" style="width:85%" v-model="inputVal.storeWeblink" :disabled="this.duplCheckYn == 'N'">
					</div>
					<div v-if="inputVal.order != ''" class="ml_28 of_h consolMarginTop">
						<p class="inline-block font-size16 float-left mt10" style="width:15%">-Order *</p>
						<input type="text" class="inputStyle float-right" style="width:85%" v-model="inputVal.orderWeblink" :disabled="this.duplCheckYn == 'N'">
					</div>
					<div v-if="inputVal.buy != ''" class="ml_28 of_h consolMarginTop">
						<p class="inline-block font-size16 float-left mt10" style="width:15%">-Buy *</p>
						<input type="text" class="inputStyle float-right" style="width:85%" v-model="inputVal.buyWeblink" :disabled="this.duplCheckYn == 'N'">
					</div>
					<div v-if="inputVal.ticket != ''" class="ml_28 of_h consolMarginTop">
						<p class="inline-block font-size16 float-left mt10" style="width:15%">-Ticket *</p>
						<input type="text" class="inputStyle float-right" style="width:85%" v-model="inputVal.ticketWeblink" :disabled="this.duplCheckYn == 'N'">
					</div>
					<div v-if="inputVal.moreInfo != ''" class="ml_28 of_h consolMarginTop">
						<p class="inline-block font-size16 float-left mt10" style="width:15%">-More Info *</p>
						<input type="text" class="inputStyle float-right" style="width:85%" v-model="inputVal.moreInfoWeblink" :disabled="this.duplCheckYn == 'N'">
					</div>

					<div class="of_h consolMarginTop">
						<h4 style="width:28%" class="inline-block">카테고리 1 *</h4>
						<select id="cate1" class="float-right selectStyle2" style="width:72%" v-model="inputVal.categoryId" @change="fnChangeCate2" :disabled="this.duplCheckYn == 'N'">
							<option v-for="(option, i) in category" v-bind:value="option.categoryId" v-bind:key="i">
								{{ option.categoryName }}
							</option>
						</select>
					</div>
					<div class="of_h">
						<h4 style="width:28%" class="inline-block">카테고리 2 *</h4>
						<select id="cate2" class="float-right selectStyle2" style="width:72%" v-model="inputVal.subCategoryId" :disabled="this.duplCheckYn == 'N'">
							<option v-for="(option, i) in subCategory" v-bind:value="option.subCategoryId" v-bind:key="i">
								{{ option.subCategoryName }}
							</option>
						</select>
					</div>
					<div class="of_h">
						<h4 style="width:28%" class="inline-block">카테고리 3 *</h4>
						<input id="cate3" type="text" class="inputStyle float-right" style="width:72%" v-model="inputVal.categoryOpt" :disabled="this.duplCheckYn == 'N'">
					</div>
					<div class="of_h">
						<h4 style="width:28%" class="inline-block">홈페이지 주소</h4>
						<input id="homepageAddr" type="text" class="inputStyle float-right" style="width:72%" v-model="inputVal.webSiteUrl" :disabled="this.duplCheckYn == 'N'">
					</div>
					<div class="of_h">
						<h4 style="width:28%" class="inline-block">이메일 *</h4>
						<div style="width:72%" class="float-right">
							<input id="email1" type="text" class="inputStyle mr20" style="width:30%" v-model="inputVal.email" :disabled="this.duplCheckYn == 'N'">
							@
							<input id="email2" type="text" class="inputStyle ml20" style="width:30%; margin-right: 10px !important;" v-model="inputVal.email2" :disabled="this.duplCheckYn == 'N'">
							<select id="emailSelect" class="selectStyle2 consolMarginTop" style="width:100%"  v-model="inputVal.email2" :disabled="this.duplCheckYn == 'N'">
								<option value="" selected>직접입력</option>
								<option value="naver.com">naver.com</option>
								<option value="daum.com">daum.com</option>
								<option value="gmail.com">gmail.com</option>
								<option value="nate.com">nate.com</option>
								<!-- <option value="hanmail.net">hanmail.net</option>
								<option value="hotmail.com">hotmail.com</option>
								<option value="yahoo.co.kr">yahoo.co.kr</option>
								<option value="empas.com">empas.com</option>
								<option value="dreamwiz.com">dreamwiz.com</option>
								<option value="freechal.com">freechal.com</option>
								<option value="lycos.co.kr">lycos.co.kr</option>
								<option value="korea.com">korea.com</option>
								<option value="hanmir.com">hanmir.com</option>
								<option value="paran.com">paran.com</option> -->
							</select>
						</div>
					</div>
					<div class="of_h consolMarginTop">
						<h4 style="width:28%" class="inline-block">주소 *</h4>
						<div style="width:72%" class="float-right">
							<input id="postAddr" type="text" class="inputStyle vertical-top" style="width:50%" v-model="inputVal.zipCode" :disabled="this.duplCheckYn == 'N'">
							<a  class="btnStyle1 backLightGray" style="width:24%">우편번호</a>
						</div>
					</div>
					<div class="ml_28">
						<input id="addr1" type="text" class="inputStyle mr10" style="width:65%" v-model="inputVal.roadAddress" :disabled="this.duplCheckYn == 'N'">
						<input id="addr2" type="text" class="inputStyle" style="width:32%" v-model="inputVal.detailAddress" :disabled="this.duplCheckYn == 'N'">						
					</div>

					<hr>
					<div v-if="this.save_status == 'C'">
						<h2>발신번호 등록</h2>
						<p class="txtCaption color3">발신번호 등록은 브랜드가 먼저 등록되어야 하며, 승인완료 된 브랜드만 보여집니다.</p>

						<div class="of_h consolMarginTop">
							<h4 style="width:28%" class="inline-block">대표발신번호 *</h4>
							<div class="consolCheck" style="width:25%">
								<input type="checkbox" id="same" class="checkStyle2" @change="fnChangeSameTel($event.target.checked)" :disabled="this.duplCheckYn == 'N'">
								<label for="same">전화번호와 동일</label>
							</div>
							<input type="text" class="inputStyle" style="width:26%" v-model="inputVal.mainMdn" :disabled="this.duplCheckYn == 'N'">
							<p class="font-size12 color3 inline-block ml10" style="width:17%">*휴대폰 등록 불가</p>
						</div>
						<div class="of_h">
							<h4 class="inline-block" style="width:22%">발신 번호명 *</h4>
							<input type="text" class="inputStyle float-right" style="width:72%" v-model="inputVal.mainTitle" :disabled="this.duplCheckYn == 'N'">
						</div>
						<div class="of_h">
							<h4 class="inline-block" style="width:22%">대표번호<br>문자수신 서비스</h4>
							<div class="consolCheck vertical-top float-right" style="width:72%">
								<input id="use" type="checkbox" class="checkStyle2" v-model="inputVal.rcsReply" :disabled="this.duplCheckYn == 'N'"><label for="use">사용중</label>
								<p class="font-size12 color3 mt5">대표번호 문자수신(MO)서비스를 현재 사용하고 계실 경우 반드시 해당정보를 체크해 주셔야 합니다.</p>
							</div>						
						</div>
						<div class="of_h consolMarginTop">
							<h4 class="inline-block" style="width:22%">추가발신번호</h4>
							<div class="float-right" style="width:72%">
								<p class="txtCaption color3">대표번호문자수신서비스(MO)를 사용하실 경우 MO체크박스에 체크해주세요.</p>
								<table class="table_skin1 consolMarginTop" style="width:100%">
									<colgroup>
										<col style="width:40%">
										<col style="width:40%">
										<col style="width:10%">
										<col style="width:10%">
									</colgroup>
									<thead>
									<tr>
										<th class="text-center end">발신번호</th>
										<th class="text-center end">발신번호명</th>
										<th class="text-center end">MO</th>
										<th class="text-center end"></th>
									</tr>
									</thead>
									<tbody class="of_h">
										<tr v-for="(item, i) in inputVal.chatbots" :key="i">	
											<td class="end"><input v-model="inputVal.chatbots[i].mdn" :id="'mdn' + i"  type="text" class="inputStyle" style="width:100%"></td>
											<td class="end"><input v-model="inputVal.chatbots[i].subTitle" :id="'subTitle' + i" type="text" class="inputStyle" style="width:100%"></td>
											<td class="end"><div class="text-center"><input type="checkbox" id="MO01" class="checkStyle2" value="MO01"><label for="MO01"></label></div></td>
											<td class="end"><a @click="fnDeleteChatbotTr" class="btnStyle1 borderGray ml10" style="padding: 0 10px"><i class="far fa-minus"></i></a></td>
										</tr>
										<tr>
											<td class="end"></td>															
											<td class="end"></td>															
											<td class="end"></td>															
											<td class="end"><a @click="fnAddChatbotTr" class="btnStyle1 borderGray ml10" style="padding: 0 10px"><i class="far fa-plus"></i></a></td>															
										</tr>								  
									</tbody>
								</table>
							</div>	
						</div>
						<div class="of_h consolMarginTop">
							<h4 class="inline-block vertical-middle" style="width:22%">통신서비스<br>가입증명원 *</h4>
							<input type="file" class="btnStyle7 minWidthAuto float float-right" style="width:72%" ref="certiImgFile"/>
							<div class="font-size12 color3 mt10 ml_28 float-left" style="width:73%">
								<div class="consolCheck float-left mb40"><input type="checkbox" id="Lmit" class="checkStyle2" value="Lmit"><label for="Lmit"></label></div>
								<p>메시지 발송 시 기본템플릿(구.Free템플릿)은 정보성에 한하여 이용 가능합니다.<br>규정위반 시 서비스 이용이 제한될 수 있습니다.</p>
							</div>						
						</div>
					</div>
					<div  v-if="this.save_status == 'U'">
						<!-- 인풋 -->
						<p></p>
						<div class="of_h consolMarginTop">
							<h4 style="width:28%" class="inline-block">대표발신번호</h4>
							<div class="consolCheck" style="width:25%">
								{{this.inputVal.mainMdn}}
							</div>
						</div>
						<div class="of_h">
							<h4 class="inline-block" style="width:28%">발신 번호명</h4>
							<div class="consolCheck" style="width:25%">
								{{this.inputVal.mainTitle}}
							</div>
						</div>
						<input type="file" class="btnStyle7 minWidthAuto float float-right" style="width:72%; visibility : hidden;" ref="certiImgFile" />
					</div>
					<div class="float-right">
						<a v-if="this.save_status == 'C'" @click="fnSave('save')" class="btnStyle5 red float-left width120">임시저장</a>
						<a v-if="this.save_status == 'C'" @click="fnSave('approval')" class="btnStyle5 red float-left ml10 width120">승인요청</a>

						<a v-if="this.save_status == 'U' && this.tmpBrandYn == 'Y' " @click="fnSave('save')" class="btnStyle5 red float-left width120">저장</a>
						<a v-if="this.save_status == 'U' && this.tmpBrandYn == 'Y' " @click="fnSave('approval')" class="btnStyle5 red float-left ml10 width120">승인요청</a>

						<a v-if="this.save_status == 'U' && this.tmpBrandYn == 'N'" @click="fnSave('update')" class="btnStyle5 red float-left ml10 width120">수정요청</a>
						<a v-if="this.save_status == 'U' && this.tmpBrandYn == 'N'" @click="fnSave('delete')" class="btnStyle5 red float-left ml10 width120">삭제요청</a>
						<a @click="fnBack" ref="backBtn" class="btnStyle5 white float-left ml10 width120">목록</a>
					</div>
				</div>		
			</div>
			<!-- //본문 -->
		</article>
	<!-- </div> -->
</template>

<script>
import api from "@/modules/channel/service/api";
import axios from 'axios'
import tokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm"

export default {
  name: 'bcChanRcsDetail',
  data() {
	return {
		save_status : '', // 등록 수정 여부
		projectId : '',
		brandId : "",
		tmpBrandYn : "N",
		duplCheckYn : "N",
		inputVal : {
			corpId 			: "",
			projectId 		: "",
			apiKey 			: "kangyj94",
			apiSecret 	: "SK.E7nddvJlfZp8JXU",
			name			: "브랜드 이름",
			description		: "브랜드 설명",
			tel				: "브랜드 전화번호",
			// 메뉴버튼설정
			chat			: "",
			call			: false,
			callWeblink		: "",
			web				: false,
			webWeblink		: "",
			store			: false,
			storeWeblink	: "",
			order			: false,
			orderWeblink	: "",
			buy				: false,
			buyWeblink		: "",
			ticket			: false,
			ticketWeblink	: "",
			moreInfo		: false,
			moreInfoWeblink	: "",

			categoryId		: "버튼 정보",
			subCategoryId	: "버튼 정보",
			categoryOpt		: "검색용 키워드",
			zipCode			: "우편번호",
			roadAddress		: "도로명주소",
			detailAddress	: "상세주소",
			email			: "이메일주소",
			email2			: "",
			webSiteUrl		: "홈페이지 주소",
			mainMdn			: "",	// 대표발신번호
			mainTitle		: "대표발신명",
			// 파일
			profileImgFilePath: "",
			preProfileImg : "/se2/images/rcsProfileImageSample.jpg",
			bgImgFilePath: "",
			preBgImg : "/se2/images/rcsImageSample.jpg",
			certiFilePath: "",

			// 발신번호 관련
			rcsReply : "",
			chatbotCnt : 1,
			chatbots: []
		},
		category : [
			{
				"categoryId": "",
				"categoryName": "",
				"subCategories": [
					{
						"subCategoryId": "",
						"subCategoryName": ""
					}
				]
			}
		],
		subCategory : {}
	}
  },
  mounted() {
	this.save_status	= this.$route.params.save_status;
    this.projectId		= this.$route.params.projectId;
	this.brandId		= this.$route.params.brandId;
	this.inputVal		= this.$route.params.inputVal;

	if( this.brandId.substring(0,1) === 'T' ){
		this.tmpBrandYn = "Y";
	} else {
		this.tmpBrandYn = "N";
	}

  },
  methods: {
	// 목록
	fnBack(){
		//this.$router.go(-1);
		this.$router.push( {name:"projectMain",params:{"projectId" : this.projectId, "selMainTab" : 4, "selMidTab" : 1 }} );
	},
	// API 중복확인
	fnCheckApiKey(){
		console.log(this.inputVal.apiKey);
		var params = {
			"apiKey"		: this.inputVal.apiKey,
			"apiSecret"	: this.inputVal.apiSecret
		};
		api.checkApiKey(params).then(response =>{
			var result = response.data.data;
			this.category = result.data;
			if( result.data != null && result.data != undefined ){
				this.duplCheckYn = 'Y';
				confirm.fnAlert("", "정상적으로 확인되었습니다.");
				if(this.save_status === 'U'){
					this.fnChangeCate2();
				}
			} else {
				this.duplCheckYn = 'N';
				confirm.fnAlert("", "확인에 실패했습니다.");
			}
		});
	},
	// 카테고리1 변경시 카테고리2 변경
	fnChangeCate2(){
		var categoryArr	= this.category;
		var cate1Id		= this.inputVal.categoryId;
		
		for( var i = 0; i < categoryArr.length; i++ ){
			if( categoryArr[i].categoryId == cate1Id ){
				this.subCategory = categoryArr[i].subCategories;
			}
		}
	},
	// 이메일 선택 시 제어
	fnSelectEmail(){
		console.log(this.email2);
		this.email2 = jQuery("#emailSelect").val();
	},
	// 이미지 미리보기
	fnPreviewImg(type){
		if( type === 'bg' ){
			this.inputVal.preBgImg = URL.createObjectURL(this.$refs.bgImgFile.files[0]);
		} else if( type === 'profile' ){
			this.inputVal.preProfileImg = URL.createObjectURL(this.$refs.profileImgFile.files[0]);
		}
	},
	// 메뉴버튼설정 관리 펑션
	fnClickMenu(checkName, checkYn){
		var menuCnt = 0;

		if( this.inputVal.call		){ menuCnt++;}
		if( this.inputVal.web		){ menuCnt++;}
		if( this.inputVal.store		){ menuCnt++;}
		if( this.inputVal.order		){ menuCnt++;}
		if( this.inputVal.buy		){ menuCnt++;}
		if( this.inputVal.ticket	){ menuCnt++;}
		if( this.inputVal.moreInfo	){ menuCnt++;}
		
		if( menuCnt > 3 ){
			/* if( checkName === 'call'	){ this.inputVal.call		= false;}
			if( checkName === 'web'		){ this.inputVal.web		= false;}
			if( checkName === 'store'	){ this.inputVal.store		= false;}
			if( checkName === 'order'	){ this.inputVal.order		= false;}
			if( checkName === 'buy'		){ this.inputVal.buy		= false;}
			if( checkName === 'ticket'	){ this.inputVal.ticket		= false;}
			if( checkName === 'moreInfo'){ this.inputVal.moreInfo 	= false;} */
			if( checkName === 'call'	){ this.inputVal.call		= '';}
			if( checkName === 'web'		){ this.inputVal.web		= '';}
			if( checkName === 'store'	){ this.inputVal.store		= '';}
			if( checkName === 'order'	){ this.inputVal.order		= '';}
			if( checkName === 'buy'		){ this.inputVal.buy		= '';}
			if( checkName === 'ticket'	){ this.inputVal.ticket		= '';}
			if( checkName === 'moreInfo'){ this.inputVal.moreInfo 	= '';}
		//	alert("3개이상 메뉴 안됨");
			console.log(  this.inputVal.order );
		}
	},
	// 대표발신번호 동일 체크여부
	fnChangeSameTel(sameTelYn){
		if( sameTelYn ){
			this.inputVal.mainMdn = this.inputVal.tel;
		} else {
			this.inputVal.mainMdn = '';
		}
	},
	// 발신번호추가
	fnAddChatbotTr(){
		this.inputVal.chatbotCnt++;
		this.inputVal.chatbots.push({
			"mdn"		: "",		// 발신번호
            "rcsReply"	: "1",		// 대표번호문자 수신서비스 0 = x / 1 = o
            "subTitle"	: "",		// 발신번호명
            "service"	: "a2p",	// a2p 고정값
            "display"	: "10"		// '10' 고정값
		});
	},
    // 발신번호삭제
	fnDeleteChatbotTr(){
		this.inputVal.chatbotCnt--;
		this.inputVal.chatbots.splice(-1);
	},
	fnValidate(){
		if( this.inputVal.apiKey === ''			|| this.inputVal.apiKey === undefined ) {		confirm.fnAlert("", "API KEY를 입력해주세요.");  return false;}
		if( this.inputVal.apiSecret === ''	|| this.inputVal.apiSecret === undefined ) {	confirm.fnAlert("", "API SECRET KEY를 입력해주세요.");   return false;}
		if( this.inputVal.name === ''			|| this.inputVal.name === undefined ) {			confirm.fnAlert("", "브랜드명을 입력해주세요."); return false;}
		if( this.inputVal.tel === ''			|| this.inputVal.tel === undefined ) {			confirm.fnAlert("", "전화번호를 입력해주세요."); return false;}

		if( this.inputVal.categoryId === ''		|| this.inputVal.categoryId === undefined ) {	confirm.fnAlert("", "카테고리1을 선택해주세요."); return false;}
		if( this.inputVal.subCategoryId === ''	|| this.inputVal.subCategoryId === undefined ) {confirm.fnAlert("", "카테고리2를 선택해주세요."); return false;}
		if( this.inputVal.categoryOpt === ''	|| this.inputVal.categoryOpt === undefined ) {	confirm.fnAlert("", "카테고리3을 입력해주세요."); return false;}
		if( this.inputVal.email === ''			|| this.inputVal.email === undefined ) {		confirm.fnAlert("", "이메일 앞자리를 입력해주세요."); return false;}
		if( this.inputVal.email2 === ''			|| this.inputVal.email2 === undefined ) {		confirm.fnAlert("", "이메일 뒤자리를 입력 혹은 선택해주세요."); return false;}
		if( this.inputVal.zipCode === ''		|| this.inputVal.zipCode === undefined ) {		confirm.fnAlert("", "우편번호를 입력해주세요."); return false;}
		if( this.inputVal.roadAddress === ''	|| this.inputVal.roadAddress === undefined ) {	confirm.fnAlert("", "주소 앞자리를 입력해주세요."); return false;}
		if( this.inputVal.detailAddress === ''	|| this.inputVal.detailAddress === undefined ) {confirm.fnAlert("", "주소 뒤자리를 입력해주세요."); return false;}

		if( this.inputVal.mainMdn === ''		|| this.inputVal.mainMdn === undefined ) {		confirm.fnAlert("", "대표발신번호를 입력해주세요."); return false;}
		if( this.inputVal.mainTitle === ''		|| this.inputVal.mainTitle === undefined ) {	confirm.fnAlert("", "발신번호명을 입력해주세요."); return false;}

		if( this.$refs.bgImgFile.files[0] === ''		|| this.$refs.bgImgFile.files[0] === undefined ) {	confirm.fnAlert("", "백그라운드 이미지를 등록해주세요."); return false;}
		if( this.$refs.profileImgFile.files[0] === ''	|| this.$refs.profileImgFile.files[0] === undefined ) {	confirm.fnAlert("", "프로필 이미지를 등록해주세요."); return false;}
		if( this.save_status == 'C' ){
			if( this.$refs.certiImgFile.files[0] === ''	|| this.$refs.certiImgFile.files[0] === undefined ) {	confirm.fnAlert("", "통신서비스 가입증명원을 등록해주세요."); return false;}
		}
		return true;
	},
    // 등록, 수정
	async fnSave(sts){
		// 벨리데이션 처리
		if( !this.fnValidate() ){
			return;
		}

		var fd = new FormData();
		fd.append('sts'				, sts);
		fd.append('loginId'			, tokenSvc.getToken().principal.userId);
		fd.append('corpId'			, tokenSvc.getToken().principal.corpId);
		if( this.otherProjectYn === 'Y' ){
			fd.append('projectId'		, 'ALL');
		} else {
			fd.append('projectId'		, this.projectId);
		}
		fd.append('brandId'			, this.brandId);
		fd.append('apiKey'			, this.inputVal.apiKey);
		fd.append('apiSecret'	, this.inputVal.apiSecret);
		fd.append('name'			, this.inputVal.name);
		fd.append('description'		, this.inputVal.description);
		fd.append('tel'				, this.inputVal.tel);
		fd.append('categoryId'		, this.inputVal.categoryId);
		fd.append('subCategoryId'	, this.inputVal.subCategoryId);
		fd.append('categoryOpt'		, this.inputVal.categoryOpt);
		fd.append('zipCode'			, this.inputVal.zipCode);
		fd.append('roadAddress'		, this.inputVal.roadAddress);
		fd.append('detailAddress'	, this.inputVal.detailAddress);
		fd.append('email'			, this.inputVal.email);
		fd.append('email2'			, this.inputVal.email2);
		fd.append('mainMdn'			, this.inputVal.mainMdn);
		fd.append('mainTitle'		, this.inputVal.mainTitle);

		fd.append('call'			, this.inputVal.call);
		fd.append('callWeblink'		, this.inputVal.callWeblink);
		fd.append('web'				, this.inputVal.web);
		fd.append('webWeblink'		, this.inputVal.webWeblink);
		fd.append('store'			, this.inputVal.store);
		fd.append('storeWeblink'	, this.inputVal.storeWeblink);
		fd.append('order'			, this.inputVal.order);
		fd.append('orderWeblink'	, this.inputVal.orderWeblink);
		fd.append('buy'				, this.inputVal.buy);
		fd.append('buyWeblink'		, this.inputVal.buyWeblink);
		fd.append('ticket'			, this.inputVal.ticket);
		fd.append('ticketWeblink'	, this.inputVal.ticketWeblink);
		fd.append('moreInfo'		, this.inputVal.moreInfo);
		fd.append('moreInfoWeblink'	, this.inputVal.moreInfoWeblink);

		// 첨부파일 정리
		fd.append('bgImgFile'		, this.$refs.bgImgFile.files[0]);
		fd.append('profileImgFile'	, this.$refs.profileImgFile.files[0]);
		if( this.save_status == 'C' ){
			fd.append('certiFile'		, this.$refs.certiImgFile.files[0]);
		}

		// 챗봇(발신번호) 정리
		if( this.save_status == 'C' ){
			var mainRcsReplyYn = this.inputVal.rcsReplyYn;
			
			if( mainRcsReplyYn ){ this.inputVal.rcsReply = 1 } else { this.inputVal.rcsReply = 0 }
			// 메인 발신번호 세팅
			this.inputVal.chatbots.push({
				"mdn"		: this.inputVal.mainMdn,		// 발신번호
				"rcsReply"	: this.inputVal.rcsReply,		// 대표번호문자 수신서비스 0 = x / 1 = o
				"subTitle"	: this.inputVal.mainTitle,		// 발신번호명
				"service"	: "a2p",	// a2p 고정값
				"display"	: "10"		// '10' 고정값
			});
		}
		var list = [];

		for( var i = 0; i < this.inputVal.chatbots.length; i++ ){
			var obj = JSON.stringify(this.inputVal.chatbots[i]);
			list.push(obj);
		}
		var listString = list.join(", ");
		fd.append('chatbots'		, listString) ;

      
		await axios.post('/projectApi/channel/saveRcsBrandReqForApi',
		fd, {
			headers: {
			'Content-Type': 'multipart/form-data'
			}
		}
		).then( response => {
		var result = response.data;
		if(result.success) {
			confirm.fnAlert("", "저장에 성공했습니다.");
			// 목록 버튼
			this.$refs.backBtn.click();
		} else {
			confirm.fnAlert("", result.message);
		}
		})
		.catch(function () {
		confirm.fnAlert("", "저장에 실패했습니다.???");
		});
	},
    // 삭제 요청
    fnDelete(){
      
    }
  }
}
</script>