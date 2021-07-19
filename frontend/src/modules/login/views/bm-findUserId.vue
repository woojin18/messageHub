<template>
	<article class="content">
		<section class="userSection width540 mt70">
			<h2 class="text-center">아이디 찾기</h2>
			<p class="text-center font-size12 mt20">회원가입 시 등록한 휴대폰을 인증하여 아이디를 찾을 수 있습니다.</p>
			<div class="row mt40">
				<div class="col-xs-12">
					<div class="of_h">
						<h4 class="font-normal float-left font-size14 mt15" style="width:20%">이름 *</h4>
						<input type="text" class="inputStyle float-left" placeholder="이름 입력" style="width:80%">
					</div>
					<div class="mt10 of_h">
						<h4 class="font-normal float-left font-size14 mt15" style="width:20%">휴대폰 번호 *</h4>
						<input type="text" class="inputStyle float-left" placeholder="휴대폰번호(-) 없이 입력" style="width:80%">
					</div>
				</div>
			</div>
			<div class="text-center mt30"><a href="user_find_id2.html" class="btnStyle3 black font14 minWidth120">아이디 찾기</a></div>
		</section>
	</article>
</template>

<script>
import loginApi from '@/modules/login/service/api';
import * as utils from '@/common/utils';

export default {
	data: function() {
		return {
			errors: [],
			userId: '',
			loginPwd: null,
			status: ''
		};
	},
	created() {
	},
	mounted() {
	},
	methods: {
	}
};
</script>