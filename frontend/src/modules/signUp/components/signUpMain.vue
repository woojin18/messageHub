<template>
	<div>
		<section class="userSection width540 mt70">
			<h2 class="text-center color333">회원가입</h2>

			<h4 class="mt70">로그인 정보</h4>
			<div class="joinBox mt10">
				<div class="of_h">
					<div class="float-left" style="width:22%"><h5>아이디</h5></div>
					<div class="float-left" style="width:78%">
						<p class="color1 font-size14">{{ loginId }}</p>
					</div>
				</div>

				<div class="of_h">
					<div class="float-left" style="width:22%"><h5>이름</h5></div>
					<div class="float-left" style="width:78%">
						<input type="text" class="inputStyle" id="userNm" placeholder="휴대폰 인증 후 자동 입력됩니다." v-model="userNm" disabled>
					</div>
				</div>

				<div class="of_h mt10">
					<div class="float-left" style="width:22%"><h5>비밀번호*</h5></div>
					<div class="float-left" style="width:78%">
						<input type="password" class="inputStyle" placeholder="비밀번호 [대/소문자,숫자,특수문자 조합(길이8~16자리)]" maxlength="16" v-model="password">
					</div>
				</div>
				
				<div class="of_h mt10">
					<div class="float-left" style="width:22%"><h5>비밀번호 확인 *</h5></div>
					<div class="float-left" style="width:78%">
						<input type="password" class="inputStyle" placeholder="비밀번호 확인" v-model="passwordChk" maxlength="16">
					</div>
				</div>

				<!-- <div class="of_h mt10">
					<div class="float-left" style="width:22%"><h5>SMS 인증여부*</h5></div>
					<div class="float-left" style="width:78%">
						<input type="radio" name="payment" value="Y" class="cBox" id="sms_agree01" v-model="smsCertifyYn"> <label for="sms_agree01" class="font-normal mr30">예</label>
						<input type="radio" name="payment" value="N" class="cBox" id="sms_agree02" v-model="smsCertifyYn"> <label for="sms_agree02" class="font-normal">아니요</label>
					</div>
				</div> -->
			</div>

			<h4 class="mt40">담당자 휴대폰 인증*</h4>
			<div class="joinBox mt10">
				<div class="of_h">
					<div class="float-left" style="width:22%">
						<a @click="fnPopup" class="btnStyle1 backLightGray vertical-middle" style="min-width:auto; width:100%">휴대폰 인증</a>
					</div>
					<div class="float-right" style="width:75%">
						<input type="text" id="phoneCerti" class="inputStyle" placeholder="" v-model="phoneCerti" disabled>
						<input type="hidden" id="gender" v-model="gender" disabled>
						<input type="hidden" id="coInfo" v-model="coInfo" disabled>
						<button id="nice" style="display:none;" @click="fnGetNiceCheckInfo"></button>
					</div>
				</div>
			</div>

			<h4 class="mt40">사업자정보</h4>
			<div class="of_h">
				<div class="float-left" style="width:22%"><h5>사업자번호*</h5></div>
				<div class="float-left" style="width:78%">
					<input type="text" class="inputStyle" placeholder="사업자번호 ( - 없이 입력 )" style="width:72%" v-model="regno" @keypress.enter="fnSelCorp">
					<input type="hidden" v-model="selRegno" disabled>
					<div class="float-right" style="width:25%">
						<a href="#self" @click.prevent="fnSelCorp" class="btnStyle1 backLightGray" style="min-width:auto; width:100%">확인</a>
					</div>
				</div>
			</div>

			<div class="joinBox mt10">
				<div class="of_h mt10" v-if="selCorpCnt == 0">
					<div class="float-left" style="width:22%"><h5>고객번호*</h5></div>
					<div class="float-left" style="width:78%">
						<input type="text" class="inputStyle" placeholder="고객사 신규 등록의 경우 자동 생성됩니다."  v-model="custNo" disabled>
					</div>
				</div>

				<div class="of_h mt10" v-if="selCorpCnt == 0">
					<div class="float-left" style="width:22%"><h5>고객유형*</h5></div>
					<div class="float-right" style="width:78%">
						<select class="selectStyle2" title="고객유형선택란" v-model="custKdCd" :disabled="selCorpCnt > 0" style="width:50%">
							<option value="">선택하세요</option>
							<option  v-for="(row, index) in custTypeArr" :key="index" :value="row.codeVal1"> {{ row.codeName1 }} </option>
						</select>
						<input type="hidden" class="inputStyle" placeholder="고객유형"  v-model="custKdCd" :disabled="selCorpCnt > 0">
					</div>
				</div> 


				<div class="of_h mt10" v-if="selCorpCnt == 0">
					<div class="float-left" style="width:22%"><h5>생년월일/<br>법인번호</h5></div>
					<div class="float-left" style="width:78%">
						<input type="text" class="inputStyle" placeholder="개인사업자의 생년월일(YYYYMMDD) 또는 법인번호"  v-model="custrnmNo" :disabled="selCorpCnt > 0">
					</div>
				</div>
				<div class="of_h mt10">
					<div class="float-left" style="width:22%"><h5>사업자명*</h5></div>
					<div class="float-left" style="width:78%">
						<input type="text" class="inputStyle" placeholder="사업자명"  v-model="corpNm" :disabled="selCorpCnt > 0">
					</div>
				</div>

				<div class="of_h mt10">
					<div class="float-left" style="width:22%"><h5> 대표자명*</h5></div>
					<div class="float-left" style="width:78%">
						<input type="text" class="inputStyle" placeholder="대표자명"  v-model="rtnCustNm" :disabled="selCorpCnt > 0">
						<input type="hidden" class="inputStyle" placeholder="대표자명"  v-model="ceoNm">
					</div>
				</div>
				<div class="of_h mt10" v-if="selCorpCnt == 0">
					<div class="float-left" style="width:22%"><h5>업태*</h5></div>
					<div class="float-right" style="width:78%">
						<input type="text" class="inputStyle" placeholder="업태"  v-model="busiType" :disabled="selCorpCnt > 0">
					</div>
				</div>
				<div class="of_h mt10" v-if="selCorpCnt == 0">
					<div class="float-left" style="width:22%"><h5>종목*</h5></div>
					<div class="float-right" style="width:78%">
						<input type="text" class="inputStyle" placeholder="종목"  v-model="busiClass" :disabled="selCorpCnt > 0">
					</div>
				</div>
				<div class="of_h mt10">
					<div class="float-left" style="width:22%"><h5>사업장주소*</h5></div>
					<div class="float-left" style="width:78%">
						<div class="float-left" style="width:72%"><input type="text" class="inputStyle" placeholder="우편번호" v-model="custAddrZip" disabled ></div>
						<div class="float-right" style="width:25%"><a class="btnStyle1 backLightGray" style="min-width:auto; width:100%" @click="fnAddrPopup">주소 조회</a></div>					
					</div>
					<div class="float-right mt5" style="width:78%"><input type="text" class="inputStyle" placeholder="주소입력" v-model="woplaceAddress" disabled></div>
					<div class="float-right mt5" style="width:78%"><input type="text" class="inputStyle" placeholder="상세주소 입력" v-model="woplaceAddressDetail"  :disabled="selCorpCnt > 0"></div>
				</div>

				<div class="of_h mt10" v-if="selCorpCnt == 0">
					<div class="float-left" style="width:22%"><h5>유선전화번호*</h5></div>
					<div class="float-left" style="width:78%">
						<input type="text" class="inputStyle" placeholder="전화번호 ( - 없이 입력 )" v-model="wireTel" :disabled="selCorpCnt > 0">
					</div>
				</div>

				<div class="of_h mt10">
					<div class="float-left" style="width:22%"><h5>첨부파일 *</h5></div>
					<div class="float-left" style="width:78%">
						<div class="float-left" style="width:72%">
							<input type="text" class="inputStyle" placeholder="파일첨부" v-model="attachFileNm" disabled>
							<input type="file" id="attachCorpImg" @change="fnFileChange" ref="attachFile" style="display:none;">
						</div>
						<div class="float-right" style="width:25%">
							<button @click="fnAttachCorpImg" class="btnStyle1 backLightGray" style="min-width:auto; width:100%">파일첨부</button>
						</div>					
					</div>
					<div class="float-right color3 mt5" style="width:78%">필수 첨부서류 <br>1. 사업자등록증 사본 1부<br>2. 통신사실 증명원(첨부된 사업자등록증과 같은 명의)<br>3. 대리인 위임장 1부<br>4. 대리인 재직증명서 or 신분증 1부<br>* 필수 첨부 서류들을 zip 파일로 압축하여 첨부해주세요.</div>
				</div>
				<div class="of_h mt10">
					<div class="float-left" style="width:22%"><h5>영업사원</h5></div>
					<div class="float-right" style="width:78%">
						<select class="selectStyle2" title="영업사원 선택란" v-model="salesMan" style="width:50%">
							<option value="">선택하세요</option>
							<option  v-for="(row, index) in salesManArr" :key="index" :value="row.codeVal1"> {{ row.codeName1 }} </option>
						</select>
					</div>
					<div class="float-right color3 mt5" style="width:78%">영업사원 미선택시 자동으로 '박진표'로 선택됩니다.</div>
				</div>
			</div>

			<!-- <h4 class="mt40">도메인설정</h4>
			<div class="joinBox mt10">
				<div class="of_h mt10">
					<div class="float-left" style="width:22%"><h5>도메인 이름*</h5></div>
					<div class="float-left" style="width:78%">
						<div class="float-left" style="width:72%">
							<input type="text" class="inputStyle" placeholder="영어, 숫자 3자 이상 30자 이내" v-model="domainName" :disabled="this.domainNameChk==true" maxlength="30">
						</div>
						<div class="float-right" style="width:25%">
							<a href="#self" class="btnStyle1 backLightGray" style="min-width:auto; width:100%" data-toggle="modal" data-target="#save" @click.prevent="domainNameChkBtn">중복체크</a>
						</div>					
					</div>
				</div>
			</div> -->
			<div class="text-center mt70"><a href="#" class="btn btn-login" @click.prevent="signUp">가입완료</a></div>
		</section>
		<chkCorpPopup :popReset="popReset" :dataList="dataList" :selCorp.sync="selCorp"></chkCorpPopup>
		<addrPopup :popReset="popReset"  :selAddr.sync="selAddr"></addrPopup>
		<!-- 본인인증 서비스 팝업을 호출하기 위해서는 다음과 같은 form이 필요합니다. -->
		<form name="form_chk" method="post">
			<input type="hidden" name="m" value="checkplusService">						<!-- 필수 데이타로, 누락하시면 안됩니다. -->
			<input type="hidden" name="EncodeData" v-model="sEncData">		<!-- 위에서 업체정보를 암호화 한 데이타입니다. -->
		</form>
	</div>
</template>

<script>
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import signUpApi from "@/modules/signUp/service/api"
import chkCorpPopup from "@/modules/signUp/components/bp-selectCorpPopup"
import addrPopup from "@/modules/signUp/components/bp-addrPopup"

import customereApi from "@/modules/customer/service/customerApi.js";
import axios from 'axios'

export default {
	components: {
		//modal
		chkCorpPopup,
		addrPopup
	},
	props: {
    	authKey : String,
		require : true
  	},
	data() {
		return {
			loginId : "",				// 아이디
			userNm : "",
			password : "",				// 비밀번호
			passwordChk : "",			// 비밀번호 체크
			// smsCertifyYn : "Y",			// sms 인증여부
			phoneCerti : "",			// 담당자 휴대폰 인증 전화번호
			phoneCertiChk : false,		// 담당자 휴대폰 인증 TF (모듈 추가후 defalte false로 변경해야됨)
			regno : "",					// 사업자번호
			corpNm : "",				// 사업자명
			ceoNm : "",					// 대표자명
			rtnCustNm : "",		
			busiType : "",				// 업태
			busiClass : "",				// 종목
			woplaceAddress : "",		// 주소
			woplaceAddressDetail : "",	// 상세주소
			wireTel : "",				// 유선 전화번호
			attachFileNm : "",			// 첨부파일명
			// domainName : "",			// 도메인 이름
			// domainNameChk : "N",		// 도메인 이름 TF
			custAddrZip : "",			// 우편번호
			custKdCd : "",				// 고객 유형
			gender : "",				// 성별
			coInfo : "",				// ci 값
			promotionYn : "",			// 홍보성 정보 동의 여부
			salesMan : "",

			selCorp : {},				// 선택한 고객사 정보
			selAddr : {},				// 선택한 주소 정보

			popReset : 0,				// 팝업 초기화할 num
			custNo : '',				// 고객번호
			custrnmNo : '',				// 고객식별번호
			selRegno : '',				// 사업자 등록 번호(input hidden)
			selCorpCnt : 1,				// 사업자 번호로 조회한 고객사의 수

			sEncData : '', 				// 나이스본인인증 암호화 정보
			dataList : [],
			
			custTypeArr : [],			// 고객 유형 select box
			salesManArr : []			// 영업사원 selectbox
		}
	},
	// 도메인 이름 영어(소문자), 숫자만 입력 가능하도록 처리
	// 핸드폰번호 사업자번호 숫자만 입력 가능하도록 처리
	watch: {
		// domainName : function() {
		// 	return this.domainName = this.domainName.replace(/[^a-z0-9]/g, '');
		// },
		phoneCerti : function() {
			return this.phoneCerti = this.phoneCerti.replace(/[^0-9]/g, '');
		},
		regno : function() {
			return this.regno = this.regno.replace(/[^0-9]/g, '');
		},
		wireTel : function() {
			return this.wireTel = this.wireTel.replace(/[^0-9]/g, '');
		},
		selCorp(){
			this.fnSetCorpInfo();
		},
		selAddr(){
			this.fnSetCorpAddr();
		}
	},
	mounted() {
		this.fnChkMailCertify();
	},
	methods: {
		fnChkMailCertify(){
			var params = {
				authKey : this.authKey
			};

			signUpApi.certifyMailByAuthKey(params).then(response => {
				var result = response.data;
				var vm = this;
				if(result.success){
					this.loginId = result.data.email;
					this.promotionYn = result.data.promotionYn;
					this.fnGetNiceCheck();		// nice 본인인증 인증키 조회
					this.fnGetCustType();		// 고객사 고객 유형 코드 값 조회		
					this.fnGetSalesMan();		// 영업사원 목록
				} else {
					vm.$router.push({name : "chkCertifyFail"});
				}
			})
		},
		fnGetCustType(){
			var params = {
				codeTypeCd	: "CORP_CUST_TYPE",
				useYN		: "Y"
			};
			customereApi.selectCodeList(params).then(response =>{
				var result = response.data;
				if(result.success){
					if(result.data.length > 0){
						this.custTypeArr = result.data;
					}
				}
			});
		},
		fnGetSalesMan(){
			var params = {
				codeTypeCd	: "SALES_MAN",
				useYN		: "Y"
			};
			customereApi.selectCodeList(params).then(response =>{
				var result = response.data;
				if(result.success){
					if(result.data.length > 0){
						this.salesManArr = result.data;
					}
				}
			});
		},
		signUp () {
			// 기초 validation 처리
			var defaultVali = true;
			defaultVali = this.defaultVali();
			if(!defaultVali) return false;

			// 신규 고객사 validation
			if (this.selRegno == ""){
				var custVali = true;
				custVali = this.custVali();
				if(!custVali) return false;
			} 

			var regno =  this.$gfnCommonUtils.isEmpty(this.selRegno) ? this.regno : this.selRegno;
			
			// 사업자 번호 유효성 검사
			var regnoVali = this.regnoVali(regno);
			if(!regnoVali) return false;

			eventBus.$on('callbackEventBus', this.signUpSubmit);
     		confirm.fnConfirm( "", "가입하시겠습니까?", "확인");
		},
		// 기초 validation 처리
		defaultVali() {
			// 비밀번호 정책 validation
			// var reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,20}$/;
			var numReg = /^(?=.*?[a-z])(?=.*?[0-9]).{10,16}$/;
			var engReg = /^(?=.*?[a-z])(?=.*?[A-Z]).{10,16}$/;
			var speReg = /^(?=.*?[a-z])(?=.*?[?!@#$%^&*+=-_|,.]).{10,16}$/;

			var numReg2 = /^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[?!@#$%^&*+=-_|,.]).{8,16}$/;
			var engReg2 = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[?!@#$%^&*+=-_|,.]).{8,16}$/;
			var speReg2 = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[A-Z]).{8,16}$/;

			if(this.userNm == ""){
				confirm.fnAlert("", "이름을 입력해주세요.");
				return false;
			} else if(this.password == "" || this.passwordChk == ""){
				confirm.fnAlert("", "비밀번호를 입력해주세요.");
				return false;
			} else if (!numReg.test(this.password) && !engReg.test(this.password)&& !speReg.test(this.password)
						&& !numReg2.test(this.password) && !engReg2.test(this.password) && !speReg2.test(this.password)){
				confirm.fnAlert("", "비밀번호는 대/소문자, 숫자, 특수문자 중 2가지 이상을 조합하여 10~16자리\n또는 3가지 이상을 조합하여 8~16자리로 구성해주세요.\n(소문자 필수 입력)");
				return;
			} else if (this.password != this.passwordChk) {
				confirm.fnAlert("", "비밀번호가 일치하지 않습니다.");
				this.password = "";
				this.passwordChk = "";
				return false;
			// } else if (this.smsCertifyYn == "") {
			// 	confirm.fnAlert("", "SMS 인증여부 선택은 필수 사항입니다.");
			// 	return false;
			} else if (this.phoneCert == "" || this.phoneCertiChk == false) {
				confirm.fnAlert("", "담당자 휴대폰 인증을 진행해 주세요.");
				return false;
			} else if (this.regno == "") {
				confirm.fnAlert("", "사업자 등록번호를 입력해주세요.");
				return false;
			} else if (this.attachFileNm == "") {
				confirm.fnAlert("", "사업자등록증 파일을 첨부해주세요.");
				return false;
			// } else if (this.domainName == "") {
			// 	confirm.fnAlert("", "도메인 이름을 입력해주세요.");
			// 	return false;
			// } else if (this.domainNameChk == false) {
			// 	confirm.fnAlert("", "도메인 이름 충복체크를 진행해주세요.");
			// 	return false;
			} else {
				return true;
			}
		},
		// 신규 고객사 validation
		custVali(){
			if(this.custKdCd == ""){
				confirm.fnAlert("", "고객 유형을 선택해주세요.");
				return false;
			}
			if(this.custrnmNo != ""){
				if(this.custKdCd == "II" || this.custKdCd == "GEX"){
					var regex = /^(19[0-9][0-9]|20\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/;
					if(!regex.test(this.custrnmNo)) {
						confirm.fnAlert("", "개인 사업자의 생년월일(YYYYMMDD) 형식으로 입력해주세요.");
						return false;
					}
				} 
			}
			if (this.corpNm == "") {
				confirm.fnAlert("", "사업자명을 입력해주세요.");
				return false;	
			}
			if (this.rtnCustNm == "") {
				confirm.fnAlert("", "대표자명을 입력해주세요.");
				return false;
			}
			if (this.busiType == "") {
				confirm.fnAlert("", "업태를 입력해주세요.");
				return false;
			}
			if (this.busiClass == "") {
				confirm.fnAlert("", "종목을 입력해주세요.");
				return false;
			}
			if (this.custAddrZip == "") {
				confirm.fnAlert("", "우편번호를 입력해주세요.");
				return false;
			}
			if (this.woplaceAddress == "") {
				confirm.fnAlert("", "주소를 입력해주세요.");
				return false;
			}
			if (this.woplaceAddressDetail == "") {
				confirm.fnAlert("", "상세 주소를 입력해주세요.");
				return false;
			}
			if (this.wireTel == "") {
				confirm.fnAlert("", "유선 전화번호를 입력해주세요.");
				return false;
			}
			return true;
		},


		// 사업자 등록번호 validation
		regnoVali(value) {
			var valueMap = value.replace(/-/gi, '').split('').map(function(item) {
				return parseInt(item, 10);
			});

			if (valueMap.length === 10) {
				// var multiply = new Array(1, 3, 7, 1, 3, 7, 1, 3, 5);
				// var checkSum = 0;

				// for (var i = 0; i < multiply.length; ++i) {
				// 	checkSum += multiply[i] * valueMap[i];
				// }

				// checkSum += parseInt((multiply[8] * valueMap[8]) / 10, 10);

				// return Math.floor(valueMap[9]) === (10 - (checkSum % 10));
			} else {
				confirm.fnAlert("","사업자번호(10자리)를 정확히 입력해주세요.");
				return false;
			}
			return true;
		},

		// 도메인 중복 체크 function
		domainNameChkBtn() {
			var vm = this;
			var params = {
				"domainName": this.domainName
			}
			var domainName = vm.domainName;

			// 도메인 이름 3자 미만 validation 처리
			if(3>domainName.length) {
				confirm.fnAlert("", "도메인 이름은 3자 이상 30자 이내만 가능합니다.");
				return false;
			}
			
			// 도메인 이름 확인후 있으면 alert 없으면 domainNameChk 값 변경후 input disabled 처리
			signUpApi.domainChk(params).then(function(response) {
				var domainChkCnt = response.data;
				if(domainChkCnt>0) {
					confirm.fnAlert("", "이미 사용중인 도메인 이름입니다. 다시 입력해 주세요.");
				} else {
					confirm.fnAlert("", "사용가능한 도메인 이름입니다.");
					vm.domainNameChk = true;
				}
			});
		},
		async signUpSubmit() {
			var fd = new FormData();
			fd.append('loginId', this.loginId);								// 아이디
			fd.append('userNm', this.userNm);								// 이름
			fd.append('password', this.password);							// 비밀번호
			// fd.append('smsCertifyYn', this.smsCertifyYn);					// sms 인증 여부
			fd.append('phoneCerti', this.phoneCerti);						// 휴대폰 번호
			fd.append('regno', this.$gfnCommonUtils.isEmpty(this.selRegno) ? this.regno : this.selRegno);								// 사업자번호
			fd.append('custKdCd', this.custKdCd)							// 고객유형
			fd.append('custNo', this.custNo);								// 고객번호
			fd.append('corpNm', this.corpNm);								// 사업명
			fd.append('ceoNm', this.$gfnCommonUtils.isEmpty(this.ceoNm) ? this.rtnCustNm : this.ceoNm);									// 사업자 명
			fd.append('busiType', this.busiType);							// 업태
			fd.append('busiClass', this.busiClass);							// 업종
			fd.append('zipCode', this.custAddrZip);							// 우편번호
			fd.append('woplaceAddress', this.woplaceAddress);				// 주소
			fd.append('woplaceAddressDetail', this.woplaceAddressDetail);	// 상세주소
			fd.append('wireTel', this.wireTel);								// 유선 전화
			fd.append('attachFile', this.$refs.attachFile.files[0]);		// 사업자 등록증
			// fd.append('domainName', this.domainName);						// 도메인 주소
			fd.append('custrnmNo', this.custrnmNo);							// 고객 식별번호
			fd.append('coInfo', this.coInfo);								// 본인인증 토큰 (개인사업자 필수)
			fd.append('genderCode', this.gender);							// 성별 (1: 남성 / 2: 여성)
			fd.append('promotionYn', this.promotionYn);						// 홍보성 정보 수신 동의
			fd.append('salesMan', this.salesMan);							// 영업사원

			await axios.post('/api/public/signUp/insertSignUp',
				fd,
				{
					headers: {
					'Content-Type': 'multipart/form-data'
				}
			}).then( response => {
				var result = response.data;
				if(!result.success) {
				confirm.fnAlert("", result.message);
				} else {
					eventBus.$on('callbackEventBus', this.fnEmitLoginPage);
     				confirm.fnConfirm( "", "가입완료하였습니다. 확인 창을 누르면 로그인 창으로 이동합니다.", "확인");
				}
			}).catch(function () {
				confirm.fnAlert("", result.message);
			});
		},
		fnEmitLoginPage(){
			this.$router.push({name : "login"});
		},
		fnSelCorp(){
			if(!this.phoneCertiChk){
				confirm.fnAlert("","휴대폰 본인인증을 완료해주세요.");
				return false;
			}

			if(this.regno == ""){
				confirm.fnAlert("","사업자번호는 필수 입력입니다.");
				return false;
			}
			// 사업자번호 입력 후 조회하여 고객사 선택 고객사가 있으면 고객사 관련 정보 disabled 처리
			var params = {
				regno : this.regno
			}
			
			var vm = this;
			signUpApi.selectCorpCustList(params).then(response => {
				var result = response.data;
				if(result.success){
					var cnt = result.data.length;
					if(cnt > 0){
						vm.popReset += 1;
						vm.selCorpCnt = cnt;
						vm.selCorp = {};
						vm.dataList = result.data;

						jQuery("#chkCorpPopup").modal("show");
					} else {
						// 선택할 수 있는 고객사가 없는 경우 고객번호 제외 입력 가능
						confirm.fnAlert("","신규 고객사 정보를 입력해주세요.");
						vm.selCorpCnt = 0;
						vm.selCorp = {};
					}
				} else{
					confirm.fnAlert("", result.message);
				}
			});
		},
		fnSetCorpInfo(){
			this.custNo					= this.selCorp.custNo == undefined ? "" : this.selCorp.custNo;							// 고객번호
			this.custrnmNo				= this.selCorp.custrnmNo == undefined ? "" : this.selCorp.custrnmNo;					// 고객 식별번호
			this.ceoNm					= this.selCorp.custNm == undefined ? "" : this.selCorp.custNm;						// 대표자 명
			this.rtnCustNm				= this.selCorp.rtnCustNm == undefined ? "" : this.selCorp.rtnCustNm;
			this.corpNm					= this.selCorp.bizCompNm == undefined ? "" : this.selCorp.bizCompNm;					// 사업자명
			this.selRegno				= this.selCorp.bsRegNo == undefined ? "" : this.selCorp.bsRegNo;						// 선택한 사업자 번호(hidden)
			this.custAddrZip			= this.selCorp.custAddrZip == undefined ? "" : this.selCorp.custAddrZip;				// 우편번호
			this.woplaceAddress			= this.selCorp.custVilgAbvAddr == undefined ? "" : this.selCorp.custVilgAbvAddr;		// 주소1
			this.woplaceAddressDetail	= this.selCorp.custVilgBlwAddr == undefined ? "" : this.selCorp.custVilgBlwAddr;		// 상세주소
			this.wireTel				= this.selCorp.wireTel == undefined ? "" : this.selCorp.wireTel;						// 유선 전화번호
			this.busiType				= this.selCorp.bsstNm == undefined ? "" : this.selCorp.bsstNm;							// 업태
			this.busiClass				= this.selCorp.inkndNm == undefined ? "" : this.selCorp.inkndNm;						// 업종
			this.custKdCd				= this.selCorp.custKdCd == undefined ? "" : this.selCorp.custKdCd;						// 고객유형
		},
		// 본인인증 팝업
		fnPopup(){
				window.open('', 'popupChk', 'width=400, height=705, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
				document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
				document.form_chk.target = "popupChk";
				document.form_chk.submit();
		},
		// 본인인증시 사용될 데이터 조회
		fnGetNiceCheck(){
			let sEcnDataVal = '';

			signUpApi.getNiceCheck().then((response) => {
				var result = response.data;
				if(result.success){
					sEcnDataVal = result.data.sEncData;
					this.sEncData = sEcnDataVal;
				} else {
					confirm.fnAlert("", result.message);
				}
			});
		},
		// 본인인증 성공시 본인인증시 사용한 핸드폰 번호 입력
		fnGetNiceCheckInfo(){
			confirm.fnAlert("본인인증 성공", "본인인증에 성공하였습니다.");
			this.phoneCerti = jQuery("#phoneCerti").val();
			this.gender = jQuery("#gender").val();
			this.coInfo = jQuery("#coInfo").val();
			this.userNm = jQuery("#userNm").val();
			this.phoneCertiChk = true;
		},
		// 주소 조회 팝업
		fnAddrPopup(){
			if(this.selCorpCnt != 0){
				confirm.fnAlert("", "고객사를 신규로 입력하실 경우에만 사용 가능합니다.");
				return false;
			};
			this.popReset += 1;
			jQuery("#addrPopup").modal("show");
		},
		// 선택한 주소 set
		fnSetCorpAddr(){
			this.custAddrZip = this.selAddr.mailNo;
			this.woplaceAddress = this.selAddr.roadMailFullAddr + " " + this.selAddr.roadMailFullAddr2;
		},
		// 사업자 등록증 입력
		fnAttachCorpImg(){
			jQuery("#attachCorpImg").click();
		},
		fnFileChange() {
			var attachFileNm = this.$refs.attachFile.files[0].name;
			// 확장자 valid
			var ext = attachFileNm.slice(attachFileNm.lastIndexOf(".") + 1).toLowerCase();
			if(!(ext && /^(zip)$/.test(ext))) {
				confirm.fnAlert("", "필수 첨부서류들을 zip으로 압축하여 등록해주세요.");
				return false;
			}
			this.attachFileNm = attachFileNm;
		},
	}
}
</script>