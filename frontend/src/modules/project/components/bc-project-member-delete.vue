<template>
	<!-- delWrap Modal -->
	<div class="modal modalStyle" id="delMemberPop" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<h2>삭제</h2>
					<hr>
					<p class="text-center mt20">삭제 처리된 사용자는 멤버 추가 기능을 이용하여 다시 등록할 수 있습니다.</p>
					<div class="text-center mt20">
						<a @click="fnDeleteProjectMember" class="btnStyle1 backBlack mr5" activity="SAVE" title="삭제">삭제</a>
						<a @click="fnClose" class="btnStyle1 backWhite" title="취소">취소</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import memberApi from '../service/memberApi.js'
import confirm from "@/modules/commonUtil/service/confirm";

export default {
	name: 'MemberDeletePop',
	props: {
		memberDeleteOpen: {
			type: Boolean,
			require: true,
			default: false,
		},
		delMember: {
			type: Object,
			require: true,
		}
	},
	data() {
			return {
				
			}
	},
	methods: {
		// 멤버 삭제
		fnDeleteProjectMember() {
			let params = {
				"projectId": this.$parent.projectId,
				"userId": this.delMember.userId,
			};

			memberApi.deleteProjectMember(params).then(response =>{
				var result = response.data;
				if(result.success) {
					this.$parent.fnSearch(1);
					confirm.fnAlert("", "삭제에 성공했습니다.");
					this.fnClose();
				} else {
					confirm.fnAlert("", result.message);
				}
			});
		},
		// 닫기
		fnClose() {
			jQuery("#delMemberPop").modal("hide");
		},
	},
}
</script>