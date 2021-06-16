<template>
	<!-- stop Modal -->
	<div v-if="layerView" class="layerPopup">
		<!--div class="modal fade modalStyle" id="stop" tabindex="-1" role="dialog" aria-hidden="true"-->
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body">
						<h2>이용정지</h2>
						<p class="text-center mt20">사용자의 이용을 정지하면 Console에 더 이상 접근할 수 없습니다.</p>
						<div class="text-center mt20">
							<a @click="fnStopUser()" class="btnStyle1 backBlack mr5" activity="SAVE" title="확인">확인 </a>
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
	name: 'StopLayer',
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
		stopUserId: {
			type: String,
			require: true
		}
	},
	methods: {
		// 닫기
		fnCloseLayer(){
			this.$emit('update:layerView', false);
		},
		//이용정지
		fnStopUser() {
			console.log("userId : " + this.stopUserId);
			var params = {
				"userId":this.stopUserId
			}
			userApi.stopUser(params).then(response =>{
				var result = response.data;
				if(result.success) {
					confirm.fnAlert("", "이용중지에 성공했습니다.");
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