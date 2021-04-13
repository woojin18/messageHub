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
						<img src="common/images/phone_01.svg" alt="프리 템플릿">
						<div class="phoneTextWrap2">
							<div>
								<div class="text">
									<p class="text-main">전화번호</p>
									<p class="text-sub">{{this.inputVal.regBrand.tel}}</p>
								</div>
								<div class="text mt15">
									<p class="text-main">웹사이트</p>
									<p class="text-sub">{{this.inputVal.regBrand.webSiteUrl}}</p>
								</div>
								<div class="text mt15">
									<p class="text-main">이메일</p>
									<p class="text-sub">{{this.inputVal.regBrand.email}}</p>
								</div>
								<div class="text mt15">
									<p class="text-main">주소</p>
									<p class="text-sub">
										{{this.inputVal.regBrand.zipCode}} {{this.inputVal.regBrand.roadAddress}}
										<br>{{this.inputVal.regBrand.detailAddress}}
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 미리보기 -->
				<!-- 인풋 -->
				<div class="col-xs-6 of_h">								
					<div class="of_h">
						<p class="float-left font-size18" style="width:28%">API Key</p>
						<input id="apiKey" type="text" class="inputStyle float-left" style="width:72%" v-model="inputVal.apikey">
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">API Secret Key</p>
						<input id="apiSecretKey" type="text" class="inputStyle float-left" style="width:54%">
						<a @click="fnCheckApiKey" class="btnStyle7 minWidthAuto float-right" style="width:17%">중복확인</a>
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">브랜드명 *</p>
						<input id="brandName" type="text" class="inputStyle float-left" style="width:72%" v-model="inputVal.regBrand.name">
					</div>								
					<div class="mt20 of_h">
						<p class="float-left vertical-top font-size18" style="width:28%">브랜드 설명</p>
						<input id="brandDesc" type="text" class="inputStyle float-left" style="width:72%; height:160px" v-model="inputVal.regBrand.description">
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
						<!-- <input id="backImg" type="text" class="inputStyle float-left" style="width:54%" v-model="inputVal.bgImgFilePath"> -->
						<!-- <a class="btnStyle7 minWidthAuto float-right" style="width:17%">파일선택</a> -->
						<input type="file" class="btnStyle7 minWidthAuto float" style="width=72%" @change="fnFileUploadToApi($event.target.files[0], 'bg')"/>
						<input type="hidden" v-model="inputVal.bgImgFilePath"/>
					</div>
					<p class="font-size12 color3 mt10 ml_28"><i class="far fa-info-circle"></i>최대사이즈 : 1080X1080px / 1:1 비율 권장 / 파일형식 : jpg, png (최대 1MB)</p>

					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">프로필 이미지 *</p>
						<input id="profileImg" type="text" class="inputStyle float-left"  style="width:54%" v-model="inputVal.profileImgFilePath">
						<a  class="btnStyle7 minWidthAuto float-right" style="width:17%">파일선택</a>
						<p class="font-size12 color3 mt10 ml_28"><i class="far fa-info-circle"></i>최대사이즈 : 1080X1080px / 1:1 비율 권장 / 파일형식 : jpg, png (최대 1MB)</p>
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">전화번호 *</p>
						<input id="phoneNum" type="text" class="inputStyle float-left" style="width: 72%" v-model="inputVal.regBrand.tel">
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">메뉴버튼설정 *</p>
						<div class="float-left" style="width:72%">
							<div class="consolCheck" style="width:19%"><input type="checkbox" id="Chat" class="checkStyle2" value="Chat"><label for="Chat">Chat</label></div>
							<div class="consolCheck" style="width:19%"><input type="checkbox" id="Call" class="checkStyle2" value="Call"><label for="Call">Call</label></div>
							<div class="consolCheck" style="width:19%"><input type="checkbox" id="Web" class="checkStyle2" value="Web"><label for="Web">Web</label></div>
							<div class="consolCheck" style="width:19%"><input type="checkbox" id="Store" class="checkStyle2" value="Store"><label for="Store">Store</label></div>
							<div class="consolCheck" style="width:19%"><input type="checkbox" id="Order" class="checkStyle2" value="Order"><label for="Order">Order</label></div>
						</div>
						<div class="ml_28 float-left mt10" style="width:72%">
							<div class="consolCheck" style="width:19%"><input type="checkbox" id="Buy" class="checkStyle2" value="Buy"><label for="Buy">Buy</label></div>
							<div class="consolCheck" style="width:19%"><input type="checkbox" id="Ticket" class="checkStyle2" value="Ticket"><label for="Ticket">Ticket</label></div>
							<div class="consolCheck" style="width:25%"><input type="checkbox" id="More-Info" class="checkStyle2" value="More-Info"><label for="More-Info">More Info</label></div>
						</div>
					</div>
					<div class="mt20 ml_28 of_h">
						<p class="inline-block font-size16 float-left mt10" style="width:15%">-Web *</p>
						<input type="text" class="inputStyle" style="width:85%">
					</div>
					<div class="mt20 ml_28 of_h">
						<p class="inline-block font-size16 float-left mt10" style="width:15%">-Store *</p>
						<input type="text" class="inputStyle" style="width:85%">
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">카테고리 1 *</p>
						<select id="cate1" class="float-left selectStyle3" style="width:72%" v-model="inputVal.regBrand.categoryId" @change="fnChangeCate2">
							<option v-for="(option, i) in category" v-bind:value="option.categoryId" v-bind:key="i">
								{{ option.categoryName }}
							</option>
						</select>
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">카테고리 2 *</p>
						<select id="cate2" class="selectStyle3" style="width:72%" v-model="cate2Selected">
							<option v-for="(option, i) in subCategory" v-bind:value="option.subCategoryId" v-bind:key="i">
								{{ option.subCategoryName }}
							</option>
						</select>
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">카테고리 3 *</p>
						<input id="cate3" type="text" class="inputStyle" style="width:72%" v-model="inputVal.regBrand.categoryOpt">
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">홈페이지 주소</p>
						<input id="homepageAddr" type="text" class="inputStyle" style="width:72%" v-model="inputVal.regBrand.webSiteUrl">
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">이메일</p>
						<input id="email1" type="text" class="inputStyle float-left mr20" style="width:31%" v-model="inputVal.regBrand.email">
						@
						<input id="email2" type="text" class="inputStyle ml20" style="width:31%; margin-left: 10px !important;">
						<select id="emailSelect" class="selectStyle3 ml_28 mt10" style="width:72%">
							<option value="1" selected>직접입력</option>
							<option value="naver.com">naver.com</option>
							<option value="hanmail.net">hanmail.net</option>
							<option value="hotmail.com">hotmail.com</option>
							<option value="nate.com">nate.com</option>
							<option value="yahoo.co.kr">yahoo.co.kr</option>
							<option value="empas.com">empas.com</option>
							<option value="dreamwiz.com">dreamwiz.com</option>
							<option value="freechal.com">freechal.com</option>
							<option value="lycos.co.kr">lycos.co.kr</option>
							<option value="korea.com">korea.com</option>
							<option value="gmail.com">gmail.com</option>
							<option value="hanmir.com">hanmir.com</option>
							<option value="paran.com">paran.com</option>
							<option value="daum.com">daum.com</option>
						</select>
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">주소</p>
						<input id="postAddr" type="text" class="inputStyle" style="width:32%" v-model="inputVal.regBrand.zipCode">
						<a  class="btnStyle7 vertical-middle ml10 minWidthAuto" style="width:17%">우편번호</a>
					</div>
					<div class="ml_28">
						<input id="addr1" type="text" class="inputStyle mr10 mt10" style="width:65%" v-model="inputVal.regBrand.roadAddress">
						<input id="addr2" type="text" class="inputStyle mt10" style="width:32%" v-model="inputVal.regBrand.detailAddress">						
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
						<a  class="btnStyle7 vertical-middle float-left minWidthAuto" style="width:17%">파일선택</a>
						<input type="text" class="inputStyle float-right" style="width:54%" v-model="inputVal.certiFilePath">
						
						<div class="font-size12 color3 mt10 ml_28 float-left" style="width:73%">
							<div class="consolCheck float-left mb40"><input type="checkbox" id="Lmit" class="checkStyle2" value="Lmit"><label for="Lmit"></label></div>
							<p>메시지 발송 시 기본템플릿(구.Free템플릿)은 정보성에 한하여 이용 가능합니다.<br>규정위반 시 서비스 이용이 제한될 수 있습니다.</p>
						</div>						
					</div>
					<div class="mt20 float-right">
						<a v-if="this.save_status == 'C'" @click="fnSaveRcsBrandReqForApi" class="btnStyle5 red float-left width120">저장</a>
						<a v-if="this.save_status == 'C'" @click="fnSaveRcsBrandReqForApi" class="btnStyle5 red float-left ml10 width120">승인요청</a>
						<a v-if="this.save_status == 'U'" @click="fnSaveRcsBrandReqForApi" class="btnStyle5 red float-left ml10 width120">수정요청</a>
						<a v-if="this.save_status == 'U'" @click="fnSaveRcsBrandReqForApi" class="btnStyle5 red float-left ml10 width120">삭제요청</a>
						<a @click="fnBack" class="btnStyle5 white float-left ml10 width120">목록</a>
					</div>
				</div>	
        <!-- 인풋 -->
				<p></p>
				<div class="col-xs-2">
				</div>			
			</div>
			<!-- //본문 -->

			<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
		</article>
	</div>
</template>

<script>
import api from "@/modules/channel/service/api";
import axios from 'axios'

export default {
  name: 'bcChanRcsDetail',
  data() {
	return {
		vm : this,
		save_status : '', // 등록 수정 여부
		projectId : '',
		inputVal : {
			corpId : "",
			projectId : "",
			apikey : "",
			apiSecretKey : "",
			regBrand : {
				"name": "브랜드 이름",
				"description": "브랜드 설명",
				"tel": "브랜드 전화번호",
				"menus": {},
				"categoryId": "버튼 정보",
				"subCategoryId": "버튼 정보",
				"categoryOpt": "검색용 키워드",
				"zipCode": "우편번호",
				"roadAddress": "도로명주소",
				"detailAddress": "상세주소",
				"email": "이메일주소",
				"webSiteUrl": "홈페이지 주소"
			},
			mainMdn: "",
			profileImgFilePath: "",
			bgImgFilePath: "",
			/* bgImgFile : new file(), */
			certiFilePath: "",
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
		cate1 : '',
		cate1Selected: 'A',
		cate1Options: [],
		cate2 : '',
		cate2Selected: 'A',
		cate2Options: [],
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
	//this.inputVal.projectId = 
    this.save_status = this.$route.params.save_status;

	
  },
  methods: {
    // 닫기
    fnClose(){
      this.$emit('update:visible', false);
    },
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
		var cate1Id		= this.inputVal.regBrand.categoryId;
		console.log(categoryArr.length);
		for( var i = 0; i < categoryArr.length; i++ ){
			if( categoryArr[i].categoryId == cate1Id ){
				this.subCategory = categoryArr[i].subCategories;
			}
		}
	},
	async fnFileUploadToApi(file, fileType){
		var params = {
			"file" : file
		}
			

		/* api.fileUploadToApi(params).then(response =>{
			var result = response.data.data;
			console.log(result);
			if( fileType === 'bg' ){
				this.inputVal.bgImgFilePath = '';
			}
		}); */
		var fd = new FormData();
		fd.append('files', file);

		console.log( '------------------------------------- file ??? ');
		console.log(fd);
		console.log( '------------------------------------- file ??? ');

		await axios.post('/projectApi/channel/fileUploadToApi',
			fd, {
			headers: {
				'Content-Type': 'multipart/form-data'
			}
		}).then( response => {
			console.log(response.data);
			// 창닫기
			this.fnClose();
			// 부모창 리스트 조회
			this.$parent.fnSearch();
			// 파일 초기화
			this.files = [];
			alert("등록되었습니다.");
		})
		.catch(function () {
			console.log('FAILURE!!');
			alert("등록에 실패했습니다.");
		});
	},
    // 등록, 수정
    fnSaveRcsBrandReqForApi(){
		var params = {
			"inputVal" : this.inputVal
		}

		api.saveRcsBrandReqForApi(params).then(response =>{
			var result = response.data.data;
			this.cate1Options = result.data;
		});
    },
    // 삭제
    fnDelete(){
      
    }
  }
}
</script>