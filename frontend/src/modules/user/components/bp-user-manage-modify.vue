<template>
	<!-- Modify Modal -->
	<!--div class="modal fade modalStyle" id="Modify" tabindex="-1" role="dialog" aria-hidden="true"-->
	<div v-if="layerView" class="layerPopup">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>사용자정보 수정</h2>
						<hr>
						<div class="of_h">
							<h5 class="inline-block" style="width:20%">사용자 ID</h5>
							<input type="text" :value="modifyLoginId" class="inputStyle float-right" style="width:80%" disabled title="사용자 ID 입력란">
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block">사용자 명*</h5>
							<input ref="userName" type="text" :value="modifyUserName" class="inputStyle float-right" style="width:80%" title="사용자 명 입력란" placeholder="사용자명을 입력해 주세요">
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block">휴대폰 번호*</h5>
							<input @input="fnCorrectNumberInput" ref="hpNumber" type="text" :value="modifyHpNumber" class="inputStyle float-right" style="width:80%" title="휴대폰 번호 입력란" placeholder="- 없이 입력해 주세요">
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block">이용권한</h5>
							<select ref="roleCd" :value="modifyRoleCd" class="selectStyle2 float-right inline-block" title="이용권한 선택란" style="width:80%">
								<option value="USER">USER</option>
								<option value="OWNER">OWNER</option>
								<option value="ADMIN">ADMIN</option>
							</select>
						</div>
						<p class="color3 consolMarginTop">로그인 시 SMS 인증을 하기 위해서는 휴대폰번호를 반드시 등록하셔야 합니다.</p>
					</div>
					<div class="text-center mt40">
						<a @click="fnModifyUser()" class="btnStyle3 black font14 mr5" title="수정">수정</a>
						<a @click="fnCloseLayer()" class="btnStyle3 white font14" title="취소">취소</a>
					</div>
				</div>
			</div>
		</div>
	<!--/div-->
	</div>
</template>

<script>
import userApi from '../service/userApi'
import confirm from "@/modules/commonUtil/service/confirm";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
	name: 'ModifyLayer',
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
		modifyUserId: {
			type: String,
			require: true
		},
		modifyUserName: {
			type: String,
			require: false
		},
		modifyHpNumber: {
			type: String,
			require: false
		},
		modifyRoleCd: {
			type: String,
			require: false
		},
		modifyLoginId: {
			type: String,
			require: false
		},
		modifyLayerTitle: {
			type: String,
			require: false,
			default: function() {
				return '사용자 수정';
			}
		}
	},
	data() {
		return {
			userName	: '',
			hpNumber	: '',
			roleCd		: ''
		}
	},
	methods: {
		// 닫기
		fnCloseLayer() {
			this.$emit('update:layerView', false);
		},
		//사용자정보 수정
		fnModifyUser() {
			// Value assign
			this.fnAssignValue();

			// 필수값 입력 확인
			if(!this.fnInputCheckReq()) return false;

			//변경 내용 확인
			if(!this.fnInputCheckChange()) return false;

			eventBus.$on('callbackEventBus', this.fnModifyUserCallBack);
			confirm.fnConfirm(this.modifyLayerTitle, "저장하시겠습니까?", "확인");
		},
		fnModifyUserCallBack() {
			var params = {
				"userId"	:this.modifyUserId,
				"userName"	:this.userName,
				"hpNumber"	:this.hpNumber,
				"roleCd"	:this.roleCd,
			}

			userApi.modifyUser(params).then(response => {
				var result = response.data;
				if(result.success) {
					confirm.fnAlert(this.modifyLayerTitle, "사용자정보 수정에 성공했습니다.");
					this.$parent.fnSelectUserList();
				} else {
					confirm.fnAlert(this.modifyLayerTitle, result.message);
				}
			});
			this.fnCloseLayer();
		},
		// 필수값 입력 체크
		fnInputCheckReq() {
			if(this.userName == "" || this.userName == null) {
				confirm.fnAlert(this.modifyLayerTitle, "사용자명을 입력하세요.");
				return false;
			}

			if(this.hpNumber == "" || this.hpNumber == null) {
				confirm.fnAlert(this.modifyLayerTitle, "휴대폰번호를 입력하세요.");
				return false;
			}
			return true;
		},
		// Value assign
		fnAssignValue() {
			this.userName = this.$refs.userName.value;
			this.hpNumber = this.$refs.hpNumber.value;
			this.roleCd = this.$refs.roleCd.value;
		},
		// 변경내역 확인
		fnInputCheckChange() {
			if(this.userName == this.modifyUserName
				&& this.hpNumber == this.modifyHpNumber
				&& this.roleCd == this.modifyRoleCd) {
				confirm.fnAlert(this.modifyLayerTitle, "변경된 내역이 없습니다.");
				return false;
			}
			return true;
		},
		// 숫자만 입력
		fnCorrectNumberInput(event) {
			event.target.value = event.target.value.replace(/[^0-9]/g, "");
		},
	}
}
</script>