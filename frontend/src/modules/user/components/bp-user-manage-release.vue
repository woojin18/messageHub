<template>
	<!-- Release Modal -->
	<div v-if="layerView" class="layerPopup">
	<!--div class="modal fade modalStyle" id="Releasep" tabindex="-1" role="dialog" aria-hidden="true"-->
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<h2>이용정지 해제</h2>
					<hr/>
					<p class="text-center mt20">사용자의 이용정지 해제 처리 시 정상적으로 Console에 접근할 수 있습니다.</p>
					<div class="text-center mt20">
						<a @click="fnReleaseUser()" class="btnStyle1 backBlack mr5" activity="SAVE" title="확인">확인</a>
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
	name: 'ReleaseLayer',
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
		releaseUserId: {
			type: String,
			require: true
		}
	},
	methods: {
		// 닫기
		fnCloseLayer(){
			this.$emit('update:layerView', false);
		},
		//이용정지 해제
		fnReleaseUser() {
			var params = {
				"userId":this.releaseUserId
			}
			userApi.releaseUser(params).then(response =>{
				var result = response.data;
				if(result.success) {
					confirm.fnAlert("", "이용정지 해제에 성공했습니다.");
					this.$parent.fnSelectUserList();
				} else {
					alert(result.message);
					confirm.fnAlert("", result.message);
				}
			});
			this.fnCloseLayer();
		}
	}
}
</script>