<template>
	<div>
	<section class="userSection width540 mt70">
		<h2 class="text-center">회원가입</h2>

		<h3 class="font-size18 mt70">로그인 정보</h3>
		<div class="joinBox mt10">
			<div class="of_h">
				<div class="float-left" style="width:22%"><h4 class="font-normal font-size14 mt3">아이디</h4></div>
				<div class="float-left" style="width:78%">
					<p class="color1 font-size14">test@uplus.co.kr</p>
				</div>
			</div>

			<div class="of_h mt20">
				<div class="float-left" style="width:22%"><h4 class="font-normal font-size14 mt15">비밀번호*</h4></div>
				<div class="float-left" style="width:78%">
					<input type="password" class="inputStyle" placeholder="비밀번호(8~16자리)" v-model="password">
				</div>
			</div>

			<div class="of_h mt10">
				<div class="float-left" style="width:22%"><h4 class="font-normal font-size14 mt15">비밀번호 확인 *</h4></div>
				<div class="float-left" style="width:78%">
					<input type="text" class="inputStyle" placeholder="비밀번호 확인" v-model="passwordChk">
				</div>
			</div>

			<div class="of_h mt20">
				<div class="float-left" style="width:22%"><h4 class="font-normal font-size14 mt3">SMS 인증여부*</h4></div>
				<div class="float-left" style="width:78%">
					<input type="radio" name="payment" value="Y" class="cBox" id="sms_agree01" v-model="smsCertifyYn"> <label for="sms_agree01" class="font-normal mr30">예</label>
					<input type="radio" name="payment" value="N" class="cBox" id="sms_agree02" v-model="smsCertifyYn"> <label for="sms_agree02" class="font-normal">아니요</label>
				</div>
			</div>
		</div>

		<h3 class="font-size18 mt40">담당자 휴대폰 인증*</h3>
		<div class="joinBox mt10">
			<div class="of_h">
				<div class="float-left" style="width:22%"><a href="#self" class="btnStyle7 vertical-middle" style="min-width:auto; width:100%">휴대폰 인증</a></div>
				<div class="float-right" style="width:75%">
					<input type="text" class="inputStyle" placeholder="" v-model="phoneCerti">
				</div>
			</div>
		</div>

		<h3 class="font-size18 mt40">사업자 정보</h3>
		<div class="joinBox mt10">
			<div class="of_h">
				<div class="float-left" style="width:22%"><h4 class="font-normal font-size14 mt15">사업자번호*</h4></div>
				<div class="float-left" style="width:78%">
					<input type="text" class="inputStyle" placeholder="사업자번호 ( - 없이 입력 )" v-model="regno">
				</div>
			</div>

			<div class="of_h mt10">
				<div class="float-left" style="width:22%"><h4 class="font-normal font-size14 mt15">사업자명*</h4></div>
				<div class="float-left" style="width:78%">
					<input type="text" class="inputStyle" placeholder="사업자명" v-model="corpNm">
				</div>
			</div>

			<div class="of_h mt10">
				<div class="float-left" style="width:22%"><h4 class="font-normal font-size14 mt15">대표자명*</h4></div>
				<div class="float-left" style="width:78%">
					<input type="text" class="inputStyle" placeholder="대표자명" v-model="ceoNm">
				</div>
			</div>

			<div class="of_h mt10">
				<div class="float-left" style="width:22%"><h4 class="font-normal font-size14 mt15">업태*</h4></div>
				<div class="float-left" style="width:78%">
					<div class="float-left" style="width:30%"><input type="text" class="inputStyle" placeholder="업태" v-model="busiType"></div>
					<div class="float-right" style="width:50%">
						<div class="float-left" style="width:35%"><h4 class="font-normal font-size14 mt15">종목*</h4></div>
						<div class="float-left" style="width:65%">
							<input type="text" class="inputStyle" placeholder="종목" v-model="busiClass">
						</div>
					</div>
				</div>
			</div>

			<div class="of_h mt10">
				<div class="float-left" style="width:22%"><h4 class="font-normal font-size14 mt15">사업장주소*</h4></div>
				<div class="float-left" style="width:78%">
					<div class="float-left" style="width:75%"><input type="text" class="inputStyle" placeholder="주소입력" v-model="woplaceAddress"></div>
					<div class="float-right" style="width:22%"><a href="#self" class="btnStyle7 vertical-middle" style="min-width:auto; width:100%">주소 조회</a></div>					
				</div>
				<div class="float-right mt5" style="width:78%"><input type="text" class="inputStyle" placeholder="상세주소 입력" v-model="woplaceAddressDetail"></div>
			</div>

			<div class="of_h mt10">
				<div class="float-left" style="width:22%"><h4 class="font-normal font-size14 mt15">유선전화번호</h4></div>
				<div class="float-left" style="width:78%">
					<input type="text" class="inputStyle" placeholder="전화번호 ( - 없이 입력 )" v-model="wireTel">
				</div>
			</div>

			<div class="of_h mt10">
				<div class="float-left" style="width:22%"><h4 class="font-normal font-size14 mt15">사업자등록증*</h4></div>
				<div class="float-left" style="width:78%">
					<div class="float-left" style="width:75%"><input type="text" class="inputStyle" placeholder="파일첨부" v-model="attachFileSeq"></div>
					<div class="float-right" style="width:22%"><a href="#self" class="btnStyle7 vertical-middle" style="min-width:auto; width:100%">파일첨부</a></div>					
				</div>
				<div class="float-right font-size12 color3 mt5" style="width:78%">* PDF, JPG, JPEG, PNG 형식으로 등록해주세요. (최대용량: 5MB)</div>
			</div>
		</div>

		<h3 class="font-size18 mt40">사업자 정보</h3>
		<div class="joinBox mt10">
			<div class="of_h mt10">
				<div class="float-left" style="width:22%"><h4 class="font-normal font-size14 mt15">도메인 이름*</h4></div>
				<div class="float-left" style="width:78%">
					<div class="float-left" style="width:75%"><input type="text" class="inputStyle" placeholder="영어, 숫자 3자 이상 30자 이내" v-model="domainName" :disabled="this.domainNameChk==true" maxlength="30"></div>
					<div class="float-right" style="width:22%"><a href="#s elf" class="btnStyle7 vertical-middle" style="min-width:auto; width:100%" @click.prevent="domainNameChkBtn">중복체크</a></div>					
				</div>
			</div>
		</div>
		<div class="text-center mt70"><a href="#" class="btn btn-login" @click.prevent="signUp">가입완료</a></div>
	</section>
	</div>
</template>

<script>
import confirm from "@/modules/commonUtil/service/confirm";
import signUpApi from "@/modules/signUp/service/api"

export default {
  components: {
    //modal
  },
  data() {
    return {
		loginId : "",				// 아이디
		password : "",				// 비밀번호
		passwordChk : "",			// 비밀번호 체크
		smsCertifyYn : "Y",			// sms 인증여부
		phoneCerti : "01050959083",			// 담당자 휴대폰 인증 전화번호 (모듈 추가후 전화번호 없애야됨)
		phoneCertiChk : true,		// 담당자 휴대폰 인증 TF (모듈 추가후 defalte false로 변경해야됨)
		regno : "",					// 사업자번호
		corpNm : "",				// 사업자명
		ceoNm : "",					// 대표자명
		busiType : "",				// 업태
		busiClass : "",				// 종목
		woplaceAddress : "",		// 주소
		woplaceAddressDetail : "",	// 상세주소
		wireTel : "",				// 유선 전화번호
		attachFileSeq : "",			// 첨부파일
		domainName : "",			// 도메인 이름
		domainNameChk : "N",		// 도메인 이름 TF
    }
  },
  // 도메인 이름 영어(소문자), 숫자만 입력 가능하도록 처리
  // 핸드폰번호 사업자번호 숫자만 입력 가능하도록 처리
  watch: {
	  domainName : function() {
		  return this.domainName = this.domainName.replace(/[^a-z0-9]/g, '');
	  },
	  phoneCerti : function() {
		  return this.phoneCerti = this.phoneCerti.replace(/[^0-9]/g, '');
	  },
	  regno : function() {
		  return this.regno = this.regno.replace(/[^0-9]/g, '');
	  },
	  wireTel : function() {
		  return this.wireTel = this.wireTel.replace(/[^0-9]/g, '');
	  }
  },
  mounted() {

  },
  methods: {
	 signUp () {
		// 비밀번호 정책 validation

		// 기초 validation 처리
		var defaultVali = this.defaultVali();
		if(!defaultVali) return false;

		// 사업자등록번호 validation 처리
		var regnoVali = this.regnoVali();
		if(!regnoVali) return false;

		this.signUpSubmit();
	 },
	 // 기초 validation 처리
	 defaultVali() {
		if(this.password != this.passwordChk) {
			confirm.fnAlert("", "비밀번호가 일치하지 않습니다.");
			this.password = "";
			this.passwordChk = "";
			return false;
		} else if ("" == this.smsCertifyYn) {
			confirm.fnAlert("", "SMS 인증여부 선택은 필수 사항입니다.");
			return false;
		} else if ("" == this.phoneCerti && !this.phoneCertiChk) {
			confirm.fnAlert("", "담당자 휴대폰 인증을 진행해 주세요.");
			return false;
		} else if ("" == this.regNo) {
			confirm.fnAlert("", "사업자 등록번호를 입력해주세요.");
			return false;
		} else if ("" == this.corpNm) {
			confirm.fnAlert("", "사업자명을 입력해주세요.");
			return false;	
		} else if ("" == this.ceoNm) {
			confirm.fnAlert("", "대표자명을 입력해주세요.");
			return false;
		} else if ("" == this.busiType) {
			confirm.fnAlert("", "업태를 입력해주세요.");
			return false;
		} else if ("" == this.busiClass) {
			confirm.fnAlert("", "종목을 입력해주세요.");
			return false;
		} else if ("" == this.woplaceAddress) {
			confirm.fnAlert("", "주소를 입력해주세요.");
			return false;
		} else if ("" == this.attachFileSeq) {
			confirm.fnAlert("", "사업자등록증 파일을 첨부해주세요.");
			return false;
		} else if ("" == this.domainName) {
			confirm.fnAlert("", "도메인 이름을 입력해주세요.");
			return false;
		} else if (!this.domainNameChk) {
			confirm.fnAlert("", "도메인 이름 충복체크를 진행해주세요.");
			return false;
		} else {
			return true;
		}
	 },

	 // 사업자 등록번호 validation
	 regnoVali(value) {
		var valueMap = value.replace(/-/gi, '').split('').map(function(item) {
        	return parseInt(item, 10);
    	});

		if (valueMap.length === 10) {
			var multiply = new Array(1, 3, 7, 1, 3, 7, 1, 3, 5);
			var checkSum = 0;

			for (var i = 0; i < multiply.length; ++i) {
				checkSum += multiply[i] * valueMap[i];
			}

			checkSum += parseInt((multiply[8] * valueMap[8]) / 10, 10);
			return Math.floor(valueMap[9]) === (10 - (checkSum % 10));
		}

		return false;
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
	 // 회원가입 ajax
	 signUpSubmit() {
		var params = {
			"loginId"				: this.loginId,
			"password"				: this.password,
			"smsCertifyYn"			: this.smsCertifyYn,
			"phoneCerti"			: this.phoneCerti,
			"regno"					: this.regno,
			"corpNm"				: this.corpNm,
			"ceoNm"					: this.ceoNm,
			"busiType"				: this.busiType,
			"busiClass"				: this.busiClass,
			"woplaceAddress"		: this.woplaceAddress,
			"woplaceAddressDetail"	: this.woplaceAddressDetail,
			"wireTel"				: this.wireTel,
			"attachFileSeq"			: this.attachFileSeq,
			"domainName"			: this.domainName
		}
		signUpApi.insertSignUp(params).then(function(response) {



		});
	 }
  }
}
</script>