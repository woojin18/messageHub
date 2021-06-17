<template>
	<!-- Register Modal -->
	<!--div class="modal fade modalStyle" id="Register" tabindex="-1" role="dialog" aria-hidden="true"-->
	<div v-if="layerView" class="layerPopup">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>사용자 등록</h2>
						<hr>
						<div class="of_h">
							<h5 class="inline-block" style="width:20%">사용자 ID *</h5>
							<div style="width:80%" class="of_h inline-block float-right">
								<input type="text" v-model="loginId" @change="fnChangeUserId" class="inputStyle" style="width:70%" title="사용자 ID 입력란" placeholder="이메일 정보를 입력해 주세요">
								<a @click="fnCheckDupcUser()" class="btnStyle1 backLightGray float-right" style="width:28%" activity="READ" title="중복체크">중복체크</a>
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:20%">사용자 명*</h5>
							<input type="text" v-model="userName" class="inputStyle float-right" style="width:80%" title="사용자 명 입력란" placeholder="사용자명을 입력해 주세요">
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:20%">휴대폰 번호*</h5>
							<input oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');" type="text" v-model="hpNumber" class="inputStyle float-right" style="width:80%" title="휴대폰 번호 입력란" placeholder="- 없이 입력해 주세요">
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:20%">이용권한</h5>
							<select v-model="roleCd" class="selectStyle2 float-right" style="width:80%" title="이용권한 선택란">
								<option value="USER">USER</option>
								<option value="ADMIN">ADMIN</option>
							</select>
						</div>
						<p class="color3 consolMarginTop"><i class="far fa-info-circle"></i> 로그인 시 SMS 인증을 하기 위해서는 휴대폰번호를 반드시 등록하셔야 합니다.</p>
					</div>
					<div class="text-center mt20">
						<a @click="fnRegisterUser()" class="btnStyle1 backBlack mr5" activity="SAVE" title="등록">등록</a>
						<a @click="fnCloseLayer()" class="btnStyle1 backWhite" title="취소">취소</a>
					</div>
				</div>
			</div>
		</div>
	<!--/div-->
	</div>
</template>

<script>
import userApi from '../service/userApi'
import tokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
	name: 'RegisterLayer',
	props: {
		layerView: {
			type: Boolean,
			require: true,
			default: false
		},
		title: {
			type: String,
			require: false
		},
		componentsTitle: {
			type: String,
			require: false,
			default: function() {
				return '사용자 등록';
			}
		},
		registerLayerOpen: {
			type: Boolean,
			require: true,
			default: false
		},
	},
	watch: {
		registerLayerOpen: function() {
			this.isDupcUser = false;
			this.loginId = '';
			this.userName = '';
			this.hpNumber = '';
			this.roleCd = 'USER';
		}
	},
	data() {
		return {
			isDupcUser	: false,
			loginId		: '',
			userName	: '',
			hpNumber	: '',
			roleCd		: 'USER',
		}
	},
	methods: {
		// 닫기
		fnCloseLayer(){
			this.$emit('update:layerView', false);
		},
		//중복체크
		fnCheckDupcUser() {
			// 사용자ID 입력체크
			if(this.loginId == "" || this.loginId == null) {
				confirm.fnAlert(this.componentsTitle, "사용자ID를 입력하세요.");
				return false;
			}

			// 사용자ID(이메일) 정규식체크
			if(!this.fnIsEmail(this.loginId)) return false;

			var params = {
				"loginId" :this.loginId
			}

			userApi.checkDupcUser(params).then(response =>{
				var result = response.data;
				if(result.success) {
					confirm.fnAlert(this.componentsTitle, "사용 가능한 사용자ID 입니다.");
					this.isDupcUser =  true;
				} else {
					confirm.fnAlert(this.componentsTitle, "사용할 수 없는 사용자ID 입니다.");
					this.isDupcUser = false;
				}
			});
		},
		//사용자 등록
		fnRegisterUser() {
			// 필수값 입력 확인
			if(!this.fnInputCheckReq()) return false;

			//중복체크 확인
			if(!this.fnIsDupcChk()) return false;

			// 이메일 정규식 체크
			if(!this.fnIsEmail(this.loginId)) return false;

			eventBus.$on('callbackEventBus', this.fnRegisterUserCallBack);
			confirm.fnConfirm(this.componentsTitle, "저장하시겠습니까?", "확인");
		},
		fnRegisterUserCallBack() {
			var params = {
				"loginId"	: this.loginId,
				"userName"	: this.userName,
				"hpNumber"	: this.hpNumber,
				"roleCd"	: this.roleCd,
				"regId"		: tokenSvc.getToken().principal.userId,
				"corpId"	: tokenSvc.getToken().principal.corpId,
			}

			userApi.registerUser(params).then(response =>{
				var result = response.data;
				if(result.success) {
					alert("사용자 등록에 성공했습니다.");
					this.$parent.fnSelectUserList();
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
			this.fnCloseLayer();
		},
		// 중복체크여부
		fnIsDupcChk() {
			if(!this.isDupcUser) {
				confirm.fnAlert(this.componentsTitle, "사용자ID 중복체크를 확인해주세요.");
				return false;
			}
			return true;
		},
		// 이메일 정규식
		fnIsEmail(asValue) {
			var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
			if(!regExp.test(asValue)) {
				confirm.fnAlert(this.componentsTitle, "입력한 이메일 형식이 올바르지 않습니다.");
				return false;
			}
			return true;
		},
		// 필수값 입력 체크
		fnInputCheckReq() {
			var loginId = this.loginId;
			var userName = this.userName;
			var hpNumber = this.hpNumber;

			if(loginId == "" || loginId == null) {
				confirm.fnAlert(this.componentsTitle, "사용자ID를 입력하세요.");
				return false;
			}
			
			if(userName == "" || userName == null) {
			confirm.fnAlert(this.componentsTitle, "사용자명을 입력하세요.");
			return false;
			}

			if(hpNumber == "" || hpNumber == null) {
				confirm.fnAlert(this.componentsTitle, "휴대폰번호를 입력하세요.");
				return false;
			}

			return true;
		},
		// 사용자ID 입력
		fnChangeUserId() {
			this.isDupcUser = false;
		},
		// 숫자만 입력
		fnCorrectNumberInput(event) {
			event.target.value = event.target.value.replace(/[^0-9]/g, "");
		},
	}
}
</script>