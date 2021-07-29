<template>
	<div>
	<provisionViewService :popReset="popReset" />
	<provisionViewPri :popReset="popReset" />
	<section class="userSection width540 mt70">
		<h2 class="text-center">회원가입</h2>

		<input type="checkbox" id="agree0" class="checkStyle3 mainCheck" value="전체 동의" v-model="agree0" @change="checkAll(agree0)"><label for="agree0">전체 이용약관에 동의합니다.</label>	

		<hr>

		<div class="of_h">
			<input type="checkbox" id="agree1" class="checkStyle3 subCheck" value="서비스 이용약관 동의" v-model="agree1"><label for="agree1"><span class="color1">[필수]</span> 서비스 이용약관 동의</label>
			<a href="#self" class="float-right provisionMore" data-toggle="modal" @click.prevent="svcPopView">약관보기 <i class="far fa-chevron-right"></i></a>
		</div>
		<div class="of_h mt10">
			<input type="checkbox" id="agree2" class="checkStyle3 subCheck" value="개인정보수집 및 이용동의" v-model="agree2"><label for="agree2"><span class="color1">[필수]</span> 개인정보수집 및 이용동의</label>
			<a href="#self" class="float-right provisionMore" data-toggle="modal" @click.prevent="priPopView">약관보기 <i class="far fa-chevron-right"></i></a>
		</div>
		<div class="of_h mt10">
			<input type="checkbox" id="agree3" class="checkStyle3 subCheck" value="홍보성 정보 수신 동의" v-model="agree3"><label for="agree3">[선택] 홍보성 정보 수신 동의</label>
		</div>

		<hr>

		<div class="of_h">
			<h4 class="font-normal float-left font-size14 mt15" style="width:20%">이메일 *</h4>
			<div class="float-left" style="width:80%">
				<input type="text" id="email" class="inputStyle" placeholder="이메일 입력" v-model="email" maxlength="100">
				<p class="font-size12 color3 mt10">이메일 인증 유효시간은 <span class="color1">1시간</span>입니다.</p>
			</div>
		</div>

		<button type="submit" class="btn btn-login mt45" @click.prevent="emailSend">인증메일 발송</button>
	</section>

	</div>
</template>

<script>
import confirm from "@/modules/commonUtil/service/confirm";
import signUpApi from "@/modules/signUp/service/api"
import provisionViewPri from "./provisionViewService.vue";
import provisionViewService from "./provisionViewPri.vue";

export default {
	components: {
		//modal
		provisionViewService,
		provisionViewPri
	},
	data() {
		return {
			// 약관 동의 체크 
			agree0 : false,
			agree1 : false,
			agree2 : false,
			agree3 : false,
			email : "",
			randonNum : "",
			// 약관 호출 팝업 flag
			popReset : 0
		}
	},
	watch : {
		agree1(){
			if(this.agree1 == false){
				this.agree0 = false;
			} else if (this.agree1 && this.agree2 && this.agree3){
				this.agree0 = true;
			}
		},
		agree2(){
			if(this.agree2 == false){
				this.agree0 = false;
			} else if (this.agree1 && this.agree2 && this.agree3){
				this.agree0 = true;
			}
		},
		agree3(){
			if(this.agree3 == false){
				this.agree0 = false;
			} else if (this.agree1 && this.agree2 && this.agree3){
				this.agree0 = true;
			}
		}
	},
	methods: {
		emailSend() {
			// 약관 동의 validation 처리 확인
			let termsVali = this.termsValidation();
			if(!termsVali) return false;

			// 이메일 입력 처리 확인
			let emailVali = this.emailValidation();
			if(!emailVali) return false;

			// 이메일 정규식 체크
			let isEmail = this.isEmail(this.email);
			if(!isEmail) {
				confirm.fnAlert("", "입력한 이메일 형식이 올바르지 않습니다.");
				return false;
			}
			// 이메일 인증 메일 발송

			// 메일 발송 정상 처리 후 CM_USER 회원 INSERT -> 이후 페이지 이동
			this.insertEmailCertify();
		},

		// 약관 동의 체크 validation
		termsValidation() {
			if(!this.agree1){
				confirm.fnAlert("", "서비스 이용약관 동의는 필수입니다.");
				return false;
			} else if(!this.agree2) {
				confirm.fnAlert("", "개인정보수집 및 이용동의는 필수입니다.");
				return false;
			} else {
				return true;
			}
		},

		// email validation (email 체크 정규식은 아직 추가 안함.)
		emailValidation() {
			var email = this.email;
			
			if(this.$gfnCommonUtils.isEmpty(email)){
				confirm.fnAlert("", "인증메일 발송을 위한 email을 입력해 주세요.");
				return false;
			} else {
				return true;
			}
		},
		isEmail(asValue) {
			var regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

			return regExp.test(asValue); // 형식에 맞는 경우 true 리턴	
		},

		// 전체 약관 동의 체크
		checkAll(check) {
			if(check) {
				this.agree1 = true;
				this.agree2 = true;
				this.agree3 = true;
			} else { 
				this.agree1 = false;
				this.agree2 = false;
				this.agree3 = false;
			}
		},

		// 회원 INSERT -> 페이지 이동
		insertEmailCertify() {
			var vm = this;

			var promotionYn = "N";
			if(this.agree3){
				promotionYn = "Y";
			}
			
			var params = {
				"email" : this.email,
				promotionYn : promotionYn
			}
			/*
			if(randomNum == "") {
				confirm.fnAlert("", "인증번호가 올바르지 않습니다. 메일 발송을 다시 진행해 주세요.");
				return false;
			}*/
			


			signUpApi.insertEmailCertify(params).then(function(response) { 
				var result = response.data.success;		  
				if(result) {
					vm.$router.push({name : "mailSend"});
				} else {
					var message = response.data.message;
					confirm.fnAlert("", message);
				}
			});
		},

		// 팝업 호출 닫기
		svcPopView() {
			this.popReset++;
			jQuery("#provisionView1").modal('show');
		},
		priPopView () {
			this.popReset++;
			jQuery("#provisionView2").modal('show');
		}
	}
}
</script>