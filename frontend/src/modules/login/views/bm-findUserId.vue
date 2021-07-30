<template>
	<article class="content">
		<section class="userSection width540 mt70" v-if=" findedLoginId == '' ">
			<h2 class="text-center">아이디 찾기</h2>
			<p class="text-center font-size12 mt20">회원가입 시 등록한 휴대폰을 인증하여 아이디를 찾을 수 있습니다.</p>
			<div class="row mt40">
				<div class="col-xs-12">
					<div class="of_h">
						<h4 class="font-normal float-left font-size14 mt15" style="width:20%">이름 *</h4>
						<input type="text" class="inputStyle float-left" placeholder="이름 입력" style="width:80%" v-model="findUserName" @keypress.enter="fnFindLoginId()">
					</div>
					<div class="mt10 of_h">
						<h4 class="font-normal float-left font-size14 mt15" style="width:20%">휴대폰 번호 *</h4>
						<input type="text" class="inputStyle float-left" placeholder="휴대폰번호(-) 없이 입력" style="width:80%" v-model="findHpNumber" @keypress.enter="fnFindLoginId">
					</div>
				</div>
			</div>
			<div class="text-center mt30"><a @click="fnFindLoginId" class="btnStyle3 black font14 minWidth120">아이디 찾기</a></div>
		</section>
		<section class="userSection width540 mt70"  v-if=" findedLoginId != '' ">
			<h2 class="text-center user_join_h2">아이디 찾기</h2>
			<p class="text-center font-size13 mt60">입력하신 정보로 찾은 아이디입니다.</p>
			<p class="text-center font-size13 mt5 color1">{{this.findedLoginId}}</p>
			<p class="text-center font-size13 mt30">개인정보를 위해 아이디 앞자리는 ***로 표시됩니다.</p>

			<div class="text-center mt40"><a v-bind:href="loginUrl" class="btnStyle3 black font14 minWidth120">로그인</a></div>
		</section>
	</article>
</template>

<script>
import loginApi from '@/modules/login/service/api';
import confirm from "@/modules/commonUtil/service/confirm"

export default {
	data: function() {
		return {
			findUserName : "",
			findHpNumber : "",
			findedLoginId : "",
			loginUrl : "/login"
		};
	},
	mounted() {
	},
	methods: {
		fnFindLoginId(){
			if( this.findUserName === '' ){
				confirm.fnAlert("", "이름을 입력해주세요.");
				return;
			} else if( this.findHpNumber === '' ){
				confirm.fnAlert("", "휴대폰 번호를 입력해주세요.");
				return;
			}

			var params = {
				"findUserName"		: this.findUserName,
				"findHpNumber"    	: this.findHpNumber
			}
			
			loginApi.findLoginId(params).then(response =>{
				console.log(response);
				var result = response.data;
				this.data = response.data.data;
				if( result.success ){
					this.findedLoginId = result.data;
				} else {
					confirm.fnAlert("", result.message);
				}
			});
		}
	}
};
</script>