<template>
	<div id="grpAddPopup" class="modal modalStyle" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>발신 프로필 그룹 추가</h2>
						<hr>
						<p class="color6">발신 프로필 그룹을 추가 하는데는 3~4일이 소요됩니다. <br>(영업일 기준, 주말 및 공휴일 제외)</p>
						<div class="consoleCon">							
							<div class="of_h consolMarginTop">
								<h5 class="inline-block float-left" style="width:25%">발신 프로필 그룹 ID *</h5>
								<input type="text" class="inputStyle float-right" style="width:72%" v-model="grpKey">
							</div>
						</div>
						<div class="consoleCon mt10">							
							<div class="of_h consolMarginTop">
								<h5 class="inline-block float-left" style="width:25%">발신키 *</h5>
								<input type="text" class="inputStyle float-right" style="width:72%" v-model="senderKey">
							</div>
						</div>
					</div>
					<div class="text-center mt20">
						<a @click="fnSave" class="btnStyle1 backBlack" data-toggle="modal">저장</a>
						<a @click="fnClose" class="btnStyle1 backWhite" data-dismiss="modal">취소</a>						
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import api from '../service/api'
import confirm from "@/modules/commonUtil/service/confirm"

export default {
	data() {
		return {
			grpKey : "",
			senderKey : ""
		}
	},
	mounted() {
			
	},
	methods: {
		// 닫기
		fnClose(){
			jQuery("#grpAddPopup").modal('hide');
		},
		fnSave(){
			var params = {
				"grpKey"      : this.grpKey,
				"senderKey"   : this.senderKey,
			}

			api.saveKkoChGroupForApi(params).then(response =>{
				var result = response.data;

				if(result.success) {
					confirm.fnAlert("", "저장되었습니다.");
					// 닫기 버튼
					this.$refs.closeBtn.click();
					// 부모창 리스트 조회
					this.$parent.fnSearch2();
				} else {
					confirm.fnAlert("", result.message);
				}
			});
		}
	}
}
</script>