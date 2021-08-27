<template>
	<article class="content">
		<section class="userSection width540 mt70">
			<h2 class="text-center user_join_h2">비밀번호 재설정</h2>
			<p class="text-center font-size12 mt20">비밀번호 재설정을 위해 새비밀번호를 입력해주세요.</p>
			<div class="row mt40">
				<div class="col-xs-12">								
					<div class="of_h width420 auto">
						<input type="password" v-model="pwd" class="inputStyle" placeholder="새비밀번호 (8~16자)">
						<input type="password" v-model="pwdChk" class="inputStyle mt10" placeholder="새비밀번호 확인">
					</div>
					<p class="font-size12 color3 mt30">*사용자의 패스워드는 8자리 이상의 영문 대/소문자, 숫자, 특수 문자의 조합을 사용하여 생성해 주세요.<br>
					*ID, 연속적인 숫자, 생일, 전화번호 패스워드 사용 제한됩니다.<br>
					*이전 사용되었던 패스워드는 재사용 할 수 없습니다.</p>
				</div>
			</div>
			
			<div class="mt40">
				<my-captcha :callSuccess="captchaBtn" color="black" resolve="text" style="margin: 0 auto;"></my-captcha>
			</div>

			<div class="text-center mt40">
				<a id="btnSave" v-bind:disabled="btndis" @click.prevent="fnSetUserPwd" class="btnStyle3 black font14 minWidth120">비밀번호 저장</a>
			</div>
		</section>
	</article>
</template>

<script>
import loginApi from '@/modules/login/service/api';
import * as utils from '@/common/utils';
import confirm from "@/modules/commonUtil/service/confirm";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import signUpApi from "@/modules/signUp/service/api"
import myCaptcha from 'vue-captcha'
import jq from 'jquery'

export default {
	components: {
    	'my-captcha': myCaptcha
  	},
	data: function() {
		return {
			authKey : this.$route.query.authKey,
			pwd : "",
			pwdChk : "",
			loginId : "",
			btndis: true
		};
	},
	created() {
	},
	mounted() {
		this.fnChkCertify();
	},
	methods: {
		captchaBtn () {
			this.btndis = false;
			jq("#VueCaptcha div:nth-child(2)").attr("style", "display : none !important");
		},
		fnChkCertify() {
			var params = {
				authKey : this.authKey
			};

			signUpApi.certifyMailByAuthKey(params).then(response => {
				var result = response.data;
				var vm = this;
				if(!result.success){
					vm.$router.push({name : "chkCertifyFail"});
				} else {
					this.loginId = result.data.email
				}
			})
		},
		fnSetUserPwd(){
			if(this.btndis) {
				confirm.fnAlert("화면에 보이는 문자를 입력해주세요.");
				return;
			}

			if(this.pwd.length == 0 || this.pwdChk.length == 0){
				confirm.fnAlert("비밀번호는 필수 입력 사항입니다.");
				return;
			}
			
			var numReg = /^(?=.*?[a-z])(?=.*?[0-9]).{10,}$/;
			var engReg = /^(?=.*?[a-z])(?=.*?[A-Z]).{10,}$/;
			var speReg = /^(?=.*?[a-z])(?=.*?[?!@#$%^&*+=-]).{10,}$/;
			// var speReg = /^(?=.*?[a-z])(?=.*?[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]).{10,}$/;

			var numReg2 = /^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[?!@#$%^&*+=-]).{8,}$/;
			var engReg2 = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[?!@#$%^&*+=-]).{8,}$/;
			var speReg2 = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[A-Z]).{8,}$/;

			if(!numReg.test(this.pwd) && !engReg.test(this.pwd)&& !speReg.test(this.pwd)
			&& !numReg2.test(this.pwd) && !engReg2.test(this.pwd) && !speReg2.test(this.pwd)){
				confirm.fnAlert("", "비밀번호는 대/소문자, 숫자, 특수문자 중 2가지 이상을 조합하여 10자리 이상\n또는 3가지 이상을 조합하여 8자리 이상의 길이로 구성해주세요.\n(소문자 필수 입력)");
				return;
			}

			if(this.pwd != this.pwdChk){
				confirm.fnAlert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
				return;
			}

			eventBus.$on('callbackEventBus', this.fnSetPwdCallback);
			confirm.fnConfirm("", "비밀번호로 설정하시겠습니까?","확인");
			
		},
		fnSetPwdCallback(){
			var params = {
				loginId : this.loginId,
				password : this.pwd
			}

			loginApi.updatePassword(params).then(response => {
				var result = response.data;
				if(result.success){
					eventBus.$on('callbackEventBus', this.fnSetLoginPage);
					confirm.fnConfirm("", "비밀번호 설정이 완료되었습니다.\n확인창을 누르면 로그인 창으로 이동합니다.","확인");
				} else {
					confirm.fnAlert("", result.message);
					return;
					// this.$router.push({name : "findUserPwd"});
				}
			});
		},
		fnSetLoginPage(){
			this.$router.push({name : "login"});
		}
	}
};
</script>

<style scope>
#btnSave[disabled] {
	cursor: not-allowed;
	opacity: 0.6;
}
button[title="Written"] {
	display: none !important;
}
#VueCaptcha div:nth-child(2) {
	display: block !important;
}
</style> 