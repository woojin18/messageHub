<template>
	<!-- Recipient Modal -->
	<div class="modal modalStyle" id="RcvrRegMdfyLayer" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h">
						<span v-if="status == 'R'">
							<h2>수신자 등록</h2>
						</span>
						<span v-else>
							<h2>수신자 수정</h2>
						</span>
						<hr>
						<div class="of_h">
							<h5 class="inline-block" style="width:18%">수신자명 *</h5>
							<input type="text" id="cuName" class="inputStyle float-right" style="width:80%" maxlength="30" placeholder="수신자명을 입력해 주세요">
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:18%">수신자 아이디</h5>
							<input type="text" id="cuid" class="inputStyle float-right" style="width:80%"  maxlength="200" placeholder="수신자아이디를 입력해 주세요">
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:18%">휴대폰 번호 *</h5>
							<input @input="fnCorrectNumberInput" type="text" id="hpNumber" class="inputStyle float-right" style="width:80%" maxlength="13" placeholder="- 없이 입력해 주세요">
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:18%">사용여부</h5>
							<input type="radio" name="useYn" value="Y" id="yes" checked=""> <label for="yes" class="mr30">사용</label>
							<input type="radio" name="useYn" value="N" id="no"> <label for="no">미사용</label>
						</div>
					</div>
					<div class="text-center mt20">
						<a @click="fnSave" class="btnStyle1 backBlack mr5" activity="SAVE">
							<span v-if="status == 'R'">등록</span>
							<span v-else>수정</span>
						</a>
						<a @click="fnClose" ref="closeBtn" class="btnStyle1 backWhite">취소</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import addressApi from '../service/addressApi.js'
import tokenSvc from '@/common/token-service';
import confirm from '@/modules/commonUtil/service/confirm';

export default {
	name: 'ReceiverLayer',
	components: {
	},
	props: {
		rowData: {
			type: Object,
			require: false,
		},
		status: {
			type: String,
			require: true,
		}
	},
	data() {
		return {
			componentsTitle: '수신자 등록',
		}
	},
	watch: {
		rowData: function() {
			if(this.status == 'R') {
				jQuery('#cuName').val('');
				jQuery('#cuid').val('');
				jQuery('#hpNumber').val('');
				jQuery('input:radio[name=useYn]:input[value="Y"]').prop('checked', true);
			} else if(this.status == 'U') {
				jQuery('#cuName').val(this.rowData.cuName);
				jQuery('#cuid').val(this.rowData.cuid);
				jQuery('#hpNumber').val(this.$gfnCommonUtils.hpNumberAddDash(this.rowData.hpNumber));
				jQuery('input:radio[name=useYn]:input[value="' + this.rowData.useYn + '"]').prop('checked', true);
			}
		}
	},
	mounted() {
		// var vm = this;
	},
	methods: {
		// 닫기
		fnClose(){
			this.fnInit();
			jQuery('#RcvrRegMdfyLayer').modal('hide');
		},
		// 수신자 등록, 수정
		fnSave() {
			// 필수값 입력 체크
			if(!this.fnInputCheckReq()) return false;

			let params = {
				'cuName'	: jQuery('#cuName').val(),
				'cuid'		: jQuery('#cuid').val(),
				'hpNumber'	: jQuery('#hpNumber').val(),
				'useYn'		: jQuery('input[name="useYn"]:checked').val(),
				'status'	: this.status,
				'loginId'	: tokenSvc.getToken().principal.loginId,
				'cuInfoId'	: this.rowData.cuInfoId,
			};

			params.hpNumber = this.$gfnCommonUtils.hpNumberRemoveDash(params.hpNumber);
			
			addressApi.saveReceiver(params).then(response =>{
				var result = response.data;
				if(result.success) {
					confirm.fnAlert(this.componentsTitle, '저장되었습니다.');
					// 닫기 버튼
					this.$refs.closeBtn.click();
					// 부모창 리스트 조회
					this.$parent.fnSearch();
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});

			this.fnInit();
		},
		// 입력값 초기화
		fnInit() {
			jQuery('#cuName').val('');
			jQuery('#cuid').val('');
			jQuery('#hpNumber').val('');
		},
		// 필수값 입력 체크
		fnInputCheckReq() {
			let cuName = jQuery('#cuName').val();
			let hpNumber = jQuery('#hpNumber').val();

			if(this.$gfnCommonUtils.isEmpty(cuName)) {
				confirm.fnAlert(this.componentsTitle, '수신자명을 입력하세요');
				return false;
			}

			if(this.$gfnCommonUtils.isEmpty(hpNumber)) {
				confirm.fnAlert(this.componentsTitle, '휴대폰번호를 입력하세요');
				return false;
			}
			if(hpNumber.length < 10) {
				confirm.fnAlert(this.componentsTitle, '휴대폰번호를 정확히 입력해주세요');
				return false;
			}
			return true;
		},
		// 숫자만 입력
		fnCorrectNumberInput(event) {
			event.target.value = this.$gfnCommonUtils.hpNumberAddDash(event.target.value);
		},
	},
}
</script>