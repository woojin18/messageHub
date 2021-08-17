<template>
	<article class="content">
		<section class="userSection width540 mt70">
			<h2 class="text-center user_join_h2">비밀번호 재설정</h2>
			<p class="text-center font-size12 mt20">비밀번호를 설정하신지 90일이 지났습니다.<br>비밀번호를 재등록해 주십시오!</p>
			<div class="row mt40">
				<div class="col-xs-12">		
					<div class="of_h width420 auto">
						<input type="password" v-model="pwd" class="inputStyle" placeholder="새비밀번호 (8~16자)">
						<input type="password" v-model="pwdChk" class="inputStyle mt10" placeholder="새비밀번호 확인">
					</div>
					<div class="mt30">
						<p class="font-size12 color3 width420 auto">*사용자의 패스워드는 8자리 이상의 영문 대/소문자, 숫자, 특수 문자의 조합을 사용하여 생성해 주세요.<br>
						*ID, 연속적인 숫자, 생일, 전화번호 패스워드 사용 제한됩니다.<br>
						*이전 사용되었던 패스워드는 재사용 할 수 없습니다.</p>
					</div>
				</div>
			</div>

			<div class="mt40">
				<my-captcha :callSuccess="captchaBtn" color="black" resolve="text" style="margin: 0 auto;"></my-captcha>
			</div>

			<div class="text-center mt40">
				<a id="btnSave" v-bind:disabled="btndis" @click.prevent="fnSetUserPwd" class="btnStyle3 black font14 minWidth120">비밀번호 저장</a>
				<a @click="changeNext" class="btnStyle3 gray font14 minWidth120 ml10">다음에 변경</a>
			</div>
		</section>
	</article>
</template>

<script>
import loginApi from '@/modules/login/service/api';
import tokenSvc from '@/common/token-service';
import * as utils from '@/common/utils';
import confirm from "@/modules/commonUtil/service/confirm";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import myCaptcha from 'vue-captcha'
import jq from 'jquery'

export default {
	components: {
    	'my-captcha': myCaptcha
  	},
	data: function() {
		return {
			pwd : "",
			pwdChk : "",
			loginId : tokenSvc.getToken().principal.loginId,
			svcTypeCd : tokenSvc.getToken().principal.svcTypeCd,
			btndis: true
		};
	},
	mounted() {
		jq("button[title='Written']").click();
	},
	methods: {
		captchaBtn () {
			this.btndis = false
		},
		fnSetUserPwd(){
			if(this.btndis) {
				confirm.fnAlert("화면에 보이는 문자를 입력해주세요.");
				return;
			}
			if (this.pwd.length == 0 || this.pwdChk.length == 0) {
				confirm.fnAlert("비밀번호는 필수 입력 사항입니다.");
				return;
			}
			if (this.pwd != this.pwdChk) {
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
				if (result.success) {
					alert("비밀번호 재설정이 완료되었습니다.");
					if (this.svcTypeCd == 'AC') {
						this.$router.push("/ac/home");
					} else {
						this.$router.push("/uc/home");
					}
				} else {
					alert(result.message);
					if (this.svcTypeCd == 'AC') {
						this.$router.push("/ac/home");
					} else {
						this.$router.push("/uc/home");
					}
				}
			});
		},
		changeNext: function() {
			if (this.svcTypeCd == 'AC') {
				this.$router.push("/ac/home");
			} else {
				this.$router.push("/uc/home");
			}
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
</style> 