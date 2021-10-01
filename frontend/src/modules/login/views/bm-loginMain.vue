<template>
	<article class="content">
		<section class="userSection width540 mt150">
			<!-- userLogin-form -->
			<div class="userLogin-form">
				<h2 class="text-center">U+ 메시지허브 로그인</h2>
				<input type="email" class="form-control" id="inputEmail" placeholder="이메일 주소를 입력하세요"
					name="userId" v-model="userId" :maxlength="30" v-focus @keyup.enter="ajaxlogin">
				<input type="password" class="form-control mt20" id="inputPassword" placeholder="비밀번호를 입력하세요"
					name="loginPwd" v-model="loginPwd" :maxlength="30" @keyup.enter="ajaxlogin"> 
				<!-- 에러메세지 출력 -->
				<div class="message_area" v-if="errors.length">
					<p class="text_error">{{ errors[0] }}</p>
				</div>
				<div class="row mt20">
					<div class="checkbox col-md-6">
						<div class="checks">
							<input type="checkbox" id="chkID" class="checkStyle4" value="아이디 저장" ref="chkSaveId">
							<label for="chkID">아이디 저장</label>	
						</div>
					</div>
					<div class="forgot col-md-6">
						<a v-bind:href="findUserIdUrl">아이디 찾기</a><span>·</span>
						<a v-bind:href="findUserPwdUrl"><i class="fas fa-lock-alt mr5"></i> 비밀번호 분실</a>
					</div>
				</div>	
				<button type="submit" class="btn btn-login mt45" data-toggle="modal" data-target="#smsAcc" @click.prevent="ajaxlogin">로그인</button>
				<a @click="signUp" class="btn btn-join mt15">회원가입</a>
			</div>
			<!-- //userLogin-form -->
		</section>
	</article>
</template>

<script>
import loginApi from '@/modules/login/service/api';
import confirm from '@/modules/commonUtil/service/confirm';
import * as utils from '@/common/utils';

export default {
	data: function() {
		return {
			errors: [],
			userId: '',
			loginPwd: null,
			status: '',
			findUserIdUrl: '/login/findUserId',
			findUserPwdUrl: '/login/findUserPwd'
		};
	},
	created() {
		// 로그인 페이지 진입시
		this.$store.commit("login/isLogin", false);
		this.$store.commit("login/isErrorPage", false);
	},
	mounted() {
		this.$refs.chkSaveId.checked = true;

		if (localStorage.wtmpltUserId) {  
			this.userId = localStorage.wtmpltUserId;
		}

		let cont = document.querySelector("article.content");
		let contain = document.querySelector("div.container");
	},
	destroyed() {
		let cont = document.querySelector("article.content");
		let contain = document.querySelector("div.container");
	},
	methods: {
		chgChkUserId() {
			if (this.$refs.chkSaveId.checked == true) {
				localStorage.wtmpltUserId = this.userId;
			} else {
				delete localStorage.wtmpltUserId;
			}
		},
		toRegister(){
			this.$router.push({ name: 'register'});
		},
		formCheck: function() {
			this.errors = [];

			if (!this.userId) this.errors.push('아이디를 입력해 주세요.');
			if (!this.loginPwd) this.errors.push('비밀번호를 입력해 주세요.');

			return this.errors.length == 0;
		},
		ajaxlogin: function() {
			var vm = this;
			
			vm.$refs.chkSaveId.focus();
			vm.errmsg = null;

			if (!vm.formCheck()) return false;

			// FormData 객체를 파라미터로 넘기면 Content-Type: multipart/form-data; 요청을 한다.
			// 일반 Object를 파라미터로 넘기면 Content-Type: application/json;charset=UTF-8 요청을 한다.
			var params = {
				"userId": this.userId,
				"userPwd": this.loginPwd,
			}

			loginApi.login(params).then(function(response) {
				var rsp = response.data;
				console.log('rsp:', rsp);
				if (rsp.success) {
					vm.chgChkUserId();
					vm.$store.commit("login/isLogin", true);
					vm.$router.push({ path: rsp.data });
				} else {
					vm.$store.commit("login/isLogin", false);
					var msg = null,
					next = null;
					switch (rsp.code) {
						case 'CE_ID_PWD': // ID/PWD 불일치
							vm.errors.push('로그인 실패하였습니다. (5회 실패 시 계정 잠김)');
							msg = '로그인 실패하였습니다.';
							break;
						case 'SS_NOT_AUTH': // 미인증 계정
							msg = 'Email 인증 후 이용할 수 있습니다.';
							break;
						case 'SS_NOT_USE': // 중지 계정
							msg = '관리자 승인 후 이용할 수 있습니다.';
							break;
						case 'SS_LOCK': // 잠김 계정
							msg = '관리자 승인 후 이용할 수 있습니다.';
							break;
						case 'SS_DEL_USE': // 삭제 계정
							msg = '삭제 된 계정입니다.';
							break;
						case 'SS_PWD_EXPIRE': // 비밀번호 만료
							msg = '비밀번호를 변경하신지 90일이 지났습니다.\n비밀번호 변경 화면으로 이동합니다.';
							next = '/login?expire';
							break;
						case 'SS_INIT_PWD': // 최초 비밀번호 변경 필요
							msg = '새로운 비밀번호로 변경이 필요합니다.\n비밀번호 변경 화면으로 이동합니다.';
							next = function() {
								// vue-router를 사용하여 데이터 전달
								vm.$router.push({ name: 'changepwd', params: { userId: vm.userId } });
							};
							break;
						case 'CE_TO_LOCK': // ID/PWD 불일치 횟수초과로 계정 잠김
							msg = '5회 이상 로그인 실패하여 해당 아이디에 대한 계정이 잠금처리되었습니다.\n관리자에게 문의하세요.';
							break;
						case 'SE_INTERNAL': // 최근 접속일시 업데이트 실패
							msg = '데이터베이스와 통신에 실패하였습니다.';
							break;
						case 'SS_NOT_PROJECT': // 참여 프로젝트 없음
							msg = '참여되어 있는 프로젝트가 존재하지 않습니다.';
							break;
						case 'SS_NOT_USE_CORP': // 사용정지된 고객사
							msg = '사용정지 된 고객사의 사용자입니다.';
							break;
						case 'SS_NOT_EXIST_CORP': // 삭제된 고객사
							msg = '삭제 된 고객사의 사용자입니다.';
							break;
						default:
							vm.errmsg = '인증에 실패했습니다.';
					}

					if (msg != null) {
						confirm.fnAlert('', msg);
						if (next != null) {
							next();
						}
					}
				}
			});
		},
		signUp: function() {
			this.$router.push({name : "signUp"});
		}
	}
};
</script>
