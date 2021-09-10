<template>
	<article class="content">
		<section class="userSection width540 mt70">
			<h2 class="text-center">비밀번호 재설정</h2>
			<p class="text-center font-size12 mt20">비밀번호 재설정을 위해 이메일주소를 입력해주세요.</p>
			<div class="row mt40">
				<div class="col-xs-12">
					<div class="of_h">
						<h4 class="font-normal float-left font-size14 mt15" style="width:20%">이메일 *</h4>
						<div class="float-left" style="width:80%">
							<input type="text" class="inputStyle" placeholder="이메일 입력" v-model="email">
							<p class="font-size12 color3 mt20">이메일 인증 유효시간은 1시간입니다.</p>
						</div>
						<div class="float-left mt50">
							<p class="font-size12 color3">Google메일을 이용할 시 이메일 인증에는 문제가 없으나,</p>
							<p class="font-size12 color3">구글의 보안프록시 서버를 사용하여 이미지를 제공하기 때문에 본문 상 이미지가 보이지 않을 수 있습니다.</p>
						</div>
					</div>
				</div>
			</div>
			<div class="text-center mt30"><a href="#self" @click.prevent="fnSendMail" class="btnStyle3 black font14 minWidth120">인증메일 발송</a></div>
		</section>
	</article>
</template>

<script>
import loginApi from '@/modules/login/service/api';
import * as utils from '@/common/utils';
import confirm from "@/modules/commonUtil/service/confirm";

export default {
	data: function() {
		return {
			errors: [],
			userId: '',
			loginPwd: null,
			status: '',
			email : ""
		};
	},
	created() {
	},
	mounted() {
	},
	methods: {
		fnSendMail(){
			var params = {
				email : this.email,
				location : "/login/setUserPwd"
			};

			loginApi.sendCertifyMail(params).then(response => {
				var result = response.data;
				var vm = this;
				if(!result.success){
					confirm.fnAlert("", result.message);
					return;
				} else {
					vm.$router.push({name : "mailSend"});
				}
			});
		}
	}
};
</script>