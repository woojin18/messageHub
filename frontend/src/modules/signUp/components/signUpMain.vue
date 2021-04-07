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
					<input type="text" class="inputStyle" placeholder="비밀번호 확인" v-model="passwordchk">
				</div>
			</div>

			<div class="of_h mt20">
				<div class="float-left" style="width:22%"><h4 class="font-normal font-size14 mt3">SMS 인증여부*</h4></div>
				<div class="float-left" style="width:78%">
					<input type="radio" name="payment" value="Y" class="cBox" id="sms_agree01" checked="" v-model="smsChk"> <label for="sms_agree01" class="font-normal mr30">예</label>
					<input type="radio" name="payment" value="N" class="cBox" id="sms_agree02" v-model="smsChk"> <label for="sms_agree02" class="font-normal">아니요</label>
				</div>
			</div>
		</div>

		<h3 class="font-size18 mt40">담당자 휴대폰 인증*</h3>
		<div class="joinBox mt10">
			<div class="of_h">
				<div class="float-left" style="width:22%"><a href="#self" class="btnStyle7 vertical-middle" style="min-width:auto; width:100%">휴대폰 인증</a></div>
				<div class="float-right" style="width:75%">
					<input type="text" class="inputStyle" placeholder="">
				</div>
			</div>
		</div>

		<h3 class="font-size18 mt40">사업자 정보</h3>
		<div class="joinBox mt10">
			<div class="of_h">
				<div class="float-left" style="width:22%"><h4 class="font-normal font-size14 mt15">사업자번호*</h4></div>
				<div class="float-left" style="width:78%">
					<input type="text" class="inputStyle" placeholder="사업자번호 ( - 없이 입력 )" v-model="busiNum">
				</div>
			</div>

			<div class="of_h mt10">
				<div class="float-left" style="width:22%"><h4 class="font-normal font-size14 mt15">사업자명*</h4></div>
				<div class="float-left" style="width:78%">
					<input type="text" class="inputStyle" placeholder="사업자명" v-model="busiNm">
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
					<div class="float-left" style="width:30%"><input type="text" class="inputStyle" placeholder="업태"></div>
					<div class="float-right" style="width:50%">
						<div class="float-left" style="width:35%"><h4 class="font-normal font-size14 mt15">종목*</h4></div>
						<div class="float-left" style="width:65%">
							<input type="text" class="inputStyle" placeholder="종목">
						</div>
					</div>
				</div>
			</div>

			<div class="of_h mt10">
				<div class="float-left" style="width:22%"><h4 class="font-normal font-size14 mt15">사업장주소*</h4></div>
				<div class="float-left" style="width:78%">
					<div class="float-left" style="width:75%"><input type="text" class="inputStyle" placeholder="주소입력"></div>
					<div class="float-right" style="width:22%"><a href="#self" class="btnStyle7 vertical-middle" style="min-width:auto; width:100%">주소 조회</a></div>					
				</div>
				<div class="float-right mt5" style="width:78%"><input type="text" class="inputStyle" placeholder="상세주소 입력"></div>
			</div>

			<div class="of_h mt10">
				<div class="float-left" style="width:22%"><h4 class="font-normal font-size14 mt15">유선전화번호</h4></div>
				<div class="float-left" style="width:78%">
					<input type="text" class="inputStyle" placeholder="전화번호 ( - 없이 입력 )">
				</div>
			</div>

			<div class="of_h mt10">
				<div class="float-left" style="width:22%"><h4 class="font-normal font-size14 mt15">사업자등록증*</h4></div>
				<div class="float-left" style="width:78%">
					<div class="float-left" style="width:75%"><input type="text" class="inputStyle" placeholder="파일첨부"></div>
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
					<div class="float-left" style="width:75%"><input type="text" class="inputStyle" placeholder="영어, 숫자 3자 이상 30자 이내"></div>
					<div class="float-right" style="width:22%"><a href="#s elf" class="btnStyle7 vertical-middle" style="min-width:auto; width:100%">중복체크</a></div>					
				</div>
			</div>
		</div>
		<div class="text-center mt70"><a href="#self" class="btn btn-login">가입완료</a></div>
	</section>

	<confirm></confirm>
	</div>
</template>

<script>
import confirm from "@/modules/commonUtil/service/confirm";

export default {
  components: {
    //modal
  },
  data() {
    return {
		// 약관 동의 체크 
		agree0 : false,
		agree1 : false,
		agree2 : false,
		agree3 : false,
		email : ""
    }
  },
  mounted() {
console.log('this = ', this);
  },
  methods: {
	 
  }
}
</script>