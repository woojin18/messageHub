<template>
	<!-- delWrap Modal -->
	<div v-if="layerView" class="layerPopup">
		<!--div class="modal fade modalStyle" id="delWrap" tabindex="-1" role="dialog" aria-hidden="true"-->
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<h2>삭제</h2>
					<hr>
					<p class="text-center mt20">삭제처리된 사용자는 복구할 수 없습니다.<br>서비스 이용을 위해서는 사용자 등록을 다시 진행하셔야 합니다.</p>
					<div class="text-center mt20">
						<a @click="fnDeleteUser()" class="btnStyle1 backBlack mr5" role="SAVE" title="확인">확인</a>
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
import confirm from "@/modules/commonUtil/service/confirm";

export default {
	name: 'DeleteLayer',
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
		deleteUserId: {
			type: String,
			require: true
		}
	},
	methods: {
		//닫기
		fnCloseLayer(){
			this.$emit('update:layerView', false);
		},
		//삭제처리
		fnDeleteUser() {
			console.log("userId : " + this.deleteUserId);
			var params = {
				"userId":this.deleteUserId
			}
			userApi.deleteUser(params).then(response =>{
				var result = response.data;
				if(result.success) {
                    confirm.fnAlert("", "삭제처리에 성공했습니다.");
					this.$parent.fnSelectUserList();
				} else {
                    confirm.fnAlert("", result.message);
				}
			});
			this.fnCloseLayer();
		}
	}
}
</script>