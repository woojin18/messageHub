<template>
  <div id="content">
		<article>
			<div class="contentHeader mb20">
				<h2>브랜드 등록</h2>				
			</div>

			<!-- 본문 -->
			<div class="row">
				<!-- 미리보기 -->
				<div class="col-xs-4 sub03_4_1_1_1">
					<div class="phoneWrap">
						<!-- <img src="/se2/images/phone_01.svg" alt="프리 템플릿"> -->
						<div class="phoneWrap">
							<img src="/se2/images/rcsPhone.svg" alt="">
							<div class="rcsWrap">
								<div class="rcsImage"><img v-bind:src="inputVal.preBgImg" style="height : 200; width : 100%;"></div>
								<div class="rcsProfileImage"><img v-bind:src="inputVal.preProfileImg" width="58px" height="58px"></div>
								<div class="rcsCon">
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
											<p class="text-sub">{{this.inputVal.email}}</p>
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
						<!-- <div class="phoneTextWrap2">
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
									<p class="text-sub">{{this.inputVal.email}}</p>
								</div>
								<div class="text mt15">
									<p class="text-main">주소</p>
									<p class="text-sub">
										{{this.inputVal.zipCode}} {{this.inputVal.roadAddress}}
										<br>{{this.inputVal.detailAddress}}
									</p>
								</div>
							</div>
						</div> -->
					</div>
				</div>
				<!-- 미리보기 -->
				<!-- 인풋 -->
				<div class="col-xs-6 of_h">								
					<div class="of_h">
						<p class="float-left font-size18" style="width:28%">API Key</p>
						<input id="apiKey" type="text" class="inputStyle float-left" style="width:72%" v-model="inputVal.apiKey">
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">API Secret Key</p>
						<input id="apiSecretKey" type="text" class="inputStyle float-left" style="width:54%" v-model="inputVal.apiSecretKey">
						<a @click="fnCheckApiKey" class="btnStyle7 minWidthAuto float-right" style="width:17%">중복확인</a>
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">브랜드명 *</p>
						<input id="brandName" type="text" class="inputStyle float-left" style="width:72%" v-model="inputVal.name">
					</div>								
					<div class="mt20 of_h">
						<p class="float-left vertical-top font-size18" style="width:28%">브랜드 설명</p>
						<input id="brandDesc" type="text" class="inputStyle float-left" style="width:72%; height:160px" v-model="inputVal.description">
					</div>				
									
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">타 프로젝트<br>사용여부</p>
						<div class="float-left; width:72%">
							<input type="radio" name="otherProjectYn" value="Y" class="cBox" id="yes" checked=""> <label for="yes" class="payment mt10 mr30">공용</label>
							<input type="radio" name="otherProjectYn" value="N" class="cBox" id="no"> <label for="no" class="payment mt10">전용</label>		
						</div>
					</div>

					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">백그라운드 이미지</p>
						<input type="file" class="btnStyle7 minWidthAuto float" style="width:72%" ref="bgImgFile" @change="fnPreviewImg('bg')"/>
					</div>
					<p class="font-size12 color3 mt10 ml_28"><i class="far fa-info-circle"></i>최대사이즈 : 1080X1080px / 1:1 비율 권장 / 파일형식 : jpg, png (최대 1MB)</p>

					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">프로필 이미지 *</p>
						<input type="file" class="btnStyle7 minWidthAuto float" style="width:72%" ref="profileImgFile" @change="fnPreviewImg('profile')"/>
					</div>
					<p class="font-size12 color3 mt10 ml_28"><i class="far fa-info-circle"></i>최대사이즈 : 1080X1080px / 1:1 비율 권장 / 파일형식 : jpg, png (최대 1MB)</p>

					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">전화번호 *</p>
						<input id="phoneNum" type="text" class="inputStyle float-left" style="width: 72%" v-model="inputVal.tel">
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">메뉴버튼설정 *</p>
						<div class="float-left" style="width:72%">
							<div class="consolCheck" style="width:19%"><input type="checkbox" id="Chat" class="checkStyle2" checked="checked" disabled><label for="Chat">Chat</label></div>
							<div class="consolCheck" style="width:19%"><input @click="fnClickMenu('call')" type="checkbox" id="Call" class="checkStyle2" v-model="inputVal.call"><label for="Call">Call</label></div>
							<div class="consolCheck" style="width:19%"><input @click="fnClickMenu('web')" type="checkbox" id="Web" class="checkStyle2" v-model="inputVal.web"><label for="Web">Web</label></div>
							<div class="consolCheck" style="width:19%"><input @click="fnClickMenu('store')" type="checkbox" id="Store" class="checkStyle2" v-model="inputVal.store"><label for="Store">Store</label></div>
							<div class="consolCheck" style="width:19%"><input @click="fnClickMenu('order')" type="checkbox" id="Order" class="checkStyle2" v-model="inputVal.order"><label for="Order">Order</label></div>
						</div>
						<div class="ml_28 float-left mt10" style="width:72%">
							<div class="consolCheck" style="width:19%"><input @click="fnClickMenu('buy')" type="checkbox" id="Buy" class="checkStyle2" v-model="inputVal.buy"><label for="Buy">Buy</label></div>
							<div class="consolCheck" style="width:19%"><input @click="fnClickMenu('ticket')" type="checkbox" id="Ticket" class="checkStyle2" v-model="inputVal.ticket"><label for="Ticket">Ticket</label></div>
							<div class="consolCheck" style="width:25%"><input @click="fnClickMenu('moreInfo')" type="checkbox" id="More-Info" class="checkStyle2" v-model="inputVal.moreInfo"><label for="More-Info">More Info</label></div>
						</div>
					</div>

					<div v-if="inputVal.call != ''" class="mt20 ml_28 of_h">
						<p class="inline-block font-size16 float-left mt10" style="width:15%">-Call *</p>
						<input type="text" class="inputStyle" style="width:85%">
					</div>
					<div v-if="inputVal.web != ''" class="mt20 ml_28 of_h">
						<p class="inline-block font-size16 float-left mt10" style="width:15%">-Web *</p>
						<input type="text" class="inputStyle" style="width:85%">
					</div>
					<div v-if="inputVal.store != ''" class="mt20 ml_28 of_h">
						<p class="inline-block font-size16 float-left mt10" style="width:15%">-Store *</p>
						<input type="text" class="inputStyle" style="width:85%">
					</div>
					<div v-if="inputVal.order != ''" class="mt20 ml_28 of_h">
						<p class="inline-block font-size16 float-left mt10" style="width:15%">-Order *</p>
						<input type="text" class="inputStyle" style="width:85%">
					</div>
					<div v-if="inputVal.buy != ''" class="mt20 ml_28 of_h">
						<p class="inline-block font-size16 float-left mt10" style="width:15%">-Buy *</p>
						<input type="text" class="inputStyle" style="width:85%">
					</div>
					<div v-if="inputVal.ticket != ''" class="mt20 ml_28 of_h">
						<p class="inline-block font-size16 float-left mt10" style="width:15%">-Ticket *</p>
						<input type="text" class="inputStyle" style="width:85%">
					</div>
					<div v-if="inputVal.moreInfo != ''" class="mt20 ml_28 of_h">
						<p class="inline-block font-size16 float-left mt10" style="width:15%">-More Info *</p>
						<input type="text" class="inputStyle" style="width:85%">
					</div>

					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">카테고리 1 *</p>
						<select id="cate1" class="float-left selectStyle3" style="width:72%" v-model="inputVal.categoryId" @change="fnChangeCate2">
							<option v-for="(option, i) in category" v-bind:value="option.categoryId" v-bind:key="i">
								{{ option.categoryName }}
							</option>
						</select>
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">카테고리 2 *</p>
						<select id="cate2" class="selectStyle3" style="width:72%" v-model="inputVal.subCategoryId">
							<option v-for="(option, i) in subCategory" v-bind:value="option.subCategoryId" v-bind:key="i">
								{{ option.subCategoryName }}
							</option>
						</select>
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">카테고리 3 *</p>
						<input id="cate3" type="text" class="inputStyle" style="width:72%" v-model="inputVal.categoryOpt">
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">홈페이지 주소</p>
						<input id="homepageAddr" type="text" class="inputStyle" style="width:72%" v-model="inputVal.webSiteUrl">
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">이메일</p>
						<input id="email1" type="text" class="inputStyle float-left mr20" style="width:31%" v-model="inputVal.email">
						@
						<input id="email2" type="text" class="inputStyle ml20" style="width:31%; margin-left: 10px !important;" v-model="inputVal.email2">
						<select id="emailSelect" class="selectStyle3 ml_28 mt10" style="width:72%"  v-model="inputVal.email2">
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
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">주소</p>
						<input id="postAddr" type="text" class="inputStyle" style="width:32%" v-model="inputVal.zipCode">
						<a  class="btnStyle7 vertical-middle ml10 minWidthAuto" style="width:17%">우편번호</a>
					</div>
					<div class="ml_28">
						<input id="addr1" type="text" class="inputStyle mr10 mt10" style="width:65%" v-model="inputVal.roadAddress">
						<input id="addr2" type="text" class="inputStyle mt10" style="width:32%" v-model="inputVal.detailAddress">						
					</div>

					<hr class="mt60">
					<h2 class="mt60 font-size22">발신번호 등록</h2>
					<p class="font-size12 color3 mt10"><i class="far fa-info-circle"></i>웹템플릿에서 등록한 브랜드와 승인 완료된 브랜드의 발신번호만 보여집니다.</p>

					<div class="mt30 of_h">
						<p class="float-left font-size18" style="width:28%">대표발신번호 *</p>
						<div class="consolCheck" style="width:25%"><input type="checkbox" id="same" class="checkStyle2" value="same"><label for="same">전화번호와 동일</label></div>
						<input type="text" class="inputStyle" style="width:26%">
						<p class="font-size12 color3 inline-block ml10" style="width:17%">*휴대폰 등록 불가</p>
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">발신번호명 *</p>
						<input type="text" class="inputStyle float-left" style="width:72%">
					</div>
					<div class="mt20 of_h">
						<p class="float-left line-height1-5em font-size18" style="width:28%">대표번호<br>문자수신 서비스 *</p>
						<div class="consolCheck vertical-top"  style="width:72%"><input type="checkbox" id="use" class="checkStyle2" value="use"><label for="use">사용중</label>
						<p class="font-size12 color3 mt5">대표번호 문자수신(MO)서비스를 현재 사용하고 계실 경우 반드시 해당정보를 체크해 주셔야 합니다.</p>
						</div>						
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">추가발신번호</p>
						<input type="text" class="inputStyle" style="width:61%"><a  class="btnStyle7 vertical-middle ml10 minWidth46"><i class="far fa-plus"></i></a>
						<p class="font-size12 color3 mt5 ml_28">추가발신번호가 있는 경우 + 버튼을 클릭하세요. 최대 10개까지 개별등록 가능합니다.</p>
						<input type="text" class="inputStyle ml_28" style="width: 61%"><a  class="btnStyle7 ml10 minWidth46 mt10"><i class="far fa-minus"></i></a>
						<input type="text" class="inputStyle ml_28" style="width: 61%"><a  class="btnStyle7 ml10 minWidth46 mt10"><i class="far fa-minus"></i></a>
					</div>
					<div class="mt20 of_h">
						<p class="float-left vertical-middle line-height1-5em font-size18" style="width:28%">통신서비스<br>가입증명원 *</p>
						<input type="file" class="btnStyle7 minWidthAuto float" style="width:72%" ref="certiImgFile"/>
						
						<div class="font-size12 color3 mt10 ml_28 float-left" style="width:73%">
							<div class="consolCheck float-left mb40"><input type="checkbox" id="Lmit" class="checkStyle2" value="Lmit"><label for="Lmit"></label></div>
							<p>메시지 발송 시 기본템플릿(구.Free템플릿)은 정보성에 한하여 이용 가능합니다.<br>규정위반 시 서비스 이용이 제한될 수 있습니다.</p>
						</div>						
					</div>
					<div class="mt20 float-right">
						<a v-if="this.save_status == 'C'" @click="fnSave('temp')" class="btnStyle5 red float-left width120">저장</a>
						<a v-if="this.save_status == 'C'" @click="fnSave('save')" class="btnStyle5 red float-left ml10 width120">승인요청</a>
						<a v-if="this.save_status == 'U'" @click="fnSave('update')" class="btnStyle5 red float-left ml10 width120">수정요청</a>
						<a v-if="this.save_status == 'U'" @click="fnSave('delete')" class="btnStyle5 red float-left ml10 width120">삭제요청</a>
						<a @click="fnBack" ref="backBtn" class="btnStyle5 white float-left ml10 width120">목록</a>
					</div>
				</div>	
				<!-- 인풋 -->
				<p></p>
				<div class="col-xs-2">
				</div>			
			</div>
			<!-- //본문 -->
		</article>
	</div>
</template>

<script>
import api from "@/modules/channel/service/api";
import axios from 'axios'
import tokenSvc from '@/common/token-service';

export default {
  name: 'bcChanRcsDetail',
  data() {
	return {
		save_status : '', // 등록 수정 여부
		projectId : '',
		inputVal : {
			corpId 			: "",
			projectId 		: "",
			apiKey 			: "",
			apiSecretKey 	: "",
			name:			"브랜드 이름",
			description:	"브랜드 설명",
			tel:			"브랜드 전화번호",
			// 메뉴버튼설정
			chat			: "",
			call			: "",
			web				: "",
			store			: "",
			order			: "",
			buy				: "",
			ticket			: "",
			moreInfo		: "",
			categoryId		: "버튼 정보",
			subCategoryId	: "버튼 정보",
			categoryOpt		: "검색용 키워드",
			zipCode			: "우편번호",
			roadAddress		: "도로명주소",
			detailAddress	: "상세주소",
			email			: "이메일주소",
			email2			: "",
			webSiteUrl		: "홈페이지 주소",
			mainMdn			: "",
			// 파일
			profileImgFilePath: "",
			preProfileImg : "/se2/images/rcsProfileImageSample.jpg",
			bgImgFilePath: "",
			preBgImg : "/se2/images/rcsImageSample.jpg",
			certiFilePath: ""
			// 발신번호 관련 --> 문의 필요함
			/* "chatbots": [
				{
					"mdn": "string",
					"subnum": "string",
					"rcsReply": "string",
					"subTitle": "string",
					"service": "string",
					"display": "string",
					"webhook": "string"
				}
			] */
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
    this.projectId = this.$route.params.projectId;
    this.save_status = this.$route.params.save_status;
  },
  methods: {
	// 목록
	fnBack(){
		this.$router.go(-1);
	},
	// API 중복확인
	fnCheckApiKey(){
		var params = {
			"apiKey"		: this.inputVal.apiKey,
			"apiSecretKey"	: this.inputVal.apiSecretKey
		};
		api.checkApiKey(params).then(response =>{
			var result = response.data.data;
			this.category = result.data;
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
	fnPreviewImg(type){
		if( type === 'bg' ){
			this.inputVal.preBgImg = URL.createObjectURL(this.$refs.bgImgFile.files[0]);
		} else if( type === 'profile' ){
			this.inputVal.preProfileImg = URL.createObjectURL(this.$refs.profileImgFile.files[0]);
		}
	},
	// 메뉴버튼설정 관리 펑션
	fnClickMenu(checkName){
		var menuCnt = 0;

console.log(this.inputVal.call);

		if( this.inputVal.call ){ menuCnt++;}
		if( this.inputVal.web ){ menuCnt++;}
		if( this.inputVal.store ){ menuCnt++;}
		if( this.inputVal.order ){ menuCnt++;}
		if( this.inputVal.buy ){ menuCnt++;}
		if( this.inputVal.ticket ){ menuCnt++;}
		if( this.inputVal.moreInfo ){ menuCnt++;}
		
		if( menuCnt >= 3 ){
			alert("3개이상 메뉴 안됨");
			if( checkName === 'call' ){ this.inputVal.call = '';}
			if( checkName === 'web' ){ this.inputVal.web = '';}
			if( checkName === 'store' ){ this.inputVal.store = '';}
			if( checkName === 'order' ){ this.inputVal.order = '';}
			if( checkName === 'buy' ){ this.inputVal.buy = '';}
			if( checkName === 'ticket' ){ this.inputVal.ticket = '';}
			if( checkName === 'moreInfo' ){ this.inputVal.moreInfo = '';}
		}
	},
    // 등록, 수정
	async fnSave(sts){
		// 벨리데이션 처리

      var fd = new FormData();
      fd.append('sts'			, sts);
      fd.append('loginId'		, tokenSvc.getToken().principal.userId);
      fd.append('corpId'		, tokenSvc.getToken().principal.corpId);
      fd.append('projectId'		, this.projectId);
      fd.append('apiKey'		, this.inputVal.apiKey);
      fd.append('apiSecretKey'	, this.inputVal.apiSecretKey);
      fd.append('name'			, this.inputVal.name);
      fd.append('description'	, this.inputVal.description);
      fd.append('tel'			, this.inputVal.tel);
      fd.append('categoryId'	, this.inputVal.categoryId);
      fd.append('subCategoryId'	, this.inputVal.subCategoryId);
      fd.append('categoryOpt'	, this.inputVal.categoryOpt);
      fd.append('zipCode'		, this.inputVal.zipCode);
      fd.append('roadAddress'	, this.inputVal.roadAddress);
      fd.append('detailAddress'	, this.inputVal.detailAddress);
      fd.append('email'			, this.inputVal.email);
      fd.append('email2'		, this.inputVal.email2);
      fd.append('mainMdn'		, this.inputVal.mainMdn);

      // 첨부파일 정리
      fd.append('profileImgFile', this.$refs.bgImgFile.files[0]);
      fd.append('bgImgFile'		, this.$refs.profileImgFile.files[0]);
      fd.append('certiFile'		, this.$refs.certiImgFile.files[0]);
      
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
          confirm.fnAlert("", "저장에 실패했습니다.");
        }
      })
      .catch(function () {
        confirm.fnAlert("", "저장에 실패했습니다.");
      });
	},
    // 삭제 요청
    fnDelete(){
      
    }
  }
}
</script>