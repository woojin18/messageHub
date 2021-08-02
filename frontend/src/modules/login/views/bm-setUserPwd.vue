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
			<div class="text-center mt40"><a href="#self" @click.prevent="fnSetUserPwd" class="btnStyle3 black font14 minWidth120">비밀번호 저장</a></div>
		</section>
	</article>
</template>

<script>
import loginApi from '@/modules/login/service/api';
import * as utils from '@/common/utils';
import confirm from "@/modules/commonUtil/service/confirm";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import signUpApi from "@/modules/signUp/service/api"

export default {
	data: function() {
		return {
			authKey : this.$route.query.authKey,
			pwd : "",
			pwdChk : "",
			loginId : ""
		};
	},
	created() {
	},
	mounted() {
		this.fnChkCertify();
	},
	methods: {
		fnChkCertify() {
			var params = {
				authKey : this.authKey
			};

			signUpApi.certifyMailByAuthKey(params).then(response => {
				var result = response.data;
				var vm = this;
				if(!result.success){
					confirm.fnAlert("", result.message);
					vm.$router.push({name : "findUserPwd"});
				} else {
					this.loginId = result.data.email
				}
			})
		},
		fnSetUserPwd(){
			if(this.pwd.length == 0 || this.pwdChk.length == 0){
				confirm.fnAlert("비밀번호는 필수 입력 사항입니다.");
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
					this.$router.push({name : "findUserPwd"});
				}
			});
		},
		fnSetLoginPage(){
			this.$router.push({name : "login"});
		}
	}
};
</script>