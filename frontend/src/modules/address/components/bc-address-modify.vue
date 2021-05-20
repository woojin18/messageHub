<template>
	<!-- Making Modal -->
	<div class="modal fade modalStyle" id="AddrModifyLayer" tabindex="-1" role="dialog" aria-hidden="true">
	<!--div v-if="layerView" class="layerPopup"-->
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h">
						<h2>주소록 수정</h2>
						<hr>
						<div class="of_h">
							<h5 class="inline-block" style="width:18%">주소록명 *</h5>
							<div class="float-right" style="width:80%">
								<input type="text" id="addressCategoryGrpName" class="inputStyle" />
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block">주소록 설명</h5>
							<div class="float-right" style="width:80%">
								<input type="text" id="addressCategoryGrpDesc" class="inputStyle" />
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block">타 프로젝트<br>사용여부 *</h5>
							<div class="float-right consolMarginTop" style="width:80%">
								<input type="radio" id="otherProjectUseY" name="otherProjectUseYn" value="Y">
								<label @click="fnShowProjectList('ALL')" for="otherProjectUseY">공용</label>
								<input type="radio" id="otherProjectUseN" name="otherProjectUseYn" value="N">
								<label @click="fnShowProjectList('NOTALL')" for="otherProjectUseN">전용</label>
							</div>
						</div>
						<span v-if="isShowProjectList == true">
							<div class="of_h consolMarginTop">
								<h5 class="inline-block">프로젝트</h5>
								<div class="float-right" style="width:80%">
									<select id="selectProjectId" class="selectStyle2" style="width:50%" @change="fnSelected()">
										<option value="">전체</option>
										<option v-for="(content, index) in projectItems" :key="index" :value="content.projectId">
											{{ content.projectName }}
										</option>
									</select>
								</div>
							</div>
						</span>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block">사용여부</h5>
							<div class="float-right consolMarginTop" style="width:80%">
								<input type="radio" id="useY" name="useYn" value="Y">
								<label for="useY">사용</label>
								<input type="radio" id="useN" name="useYn" value="N">
								<label for="useN">미사용</label>
							</div>
						</div>
						<p class="consolMarginTop"><i class="far fa-info-circle"></i> 타프로젝트 사용여부를 전용을 선택하신 경우 프로젝트를 선택하셔야 합니다.</p>
					</div>
					<div class="text-center mt20">
						<a @click="fnMdfyAddr()" class="btnStyle1 backBlack mr5">수정</a>
						<a @click="fnClose()" ref="closeBtn" class="btnStyle1 backWhite">취소</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--/div-->
</template>

<script>
import addressApi from '../service/addressApi.js'
import confirm from "@/modules/commonUtil/service/confirm";

export default {
	name: 'AddrModifyLayer',
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
		row_data: {
			type: Object,
			require: true
		},
	},
	watch: {
		row_data: function(newVal, oldVal) {
			jQuery("#addressCategoryGrpName").val(this.row_data.addressCategoryGrpName);
			jQuery("#addressCategoryGrpDesc").val(this.row_data.addressCategoryGrpDesc);
			if(this.row_data.projectId == 'ALL') {
				jQuery('input:radio[name=otherProjectUseYn]:input[value="Y"]').prop("checked", true);
				this.isShowProjectList = false;
			} else {
				jQuery('input:radio[name=otherProjectUseYn]:input[value="N"]').prop("checked", true);
				jQuery('#selectProjectId').val(this.row_data.projectId).attr("selected", "selected");
				this.isShowProjectList = true;
			}
			if(this.row_data.useYn == 'Y') {
				jQuery('input:radio[name=useYn]:input[value="Y"]').prop("checked", true);
			} else {
				jQuery('input:radio[name=useYn]:input[value="N"]').prop("checked", true);
			}
			
		}
	},
	data() {
		return {
			projectItems: [],
			isShowProjectList : true,
		}
	},
	mounted() {
		this.fnSearchProjectList();
	},
	methods: {
		async fnSearchProjectList() {
			var params = {
			}
			await addressApi.selectProjectList(params).then(response =>{
				var result = response.data;
				if(result.success) {
					this.projectItems = result.data;
				} else {
					confirm.fnAlert("", result.message);
				}
			});
		},
		// 닫기
		fnClose() {
			jQuery("#AddrModifyLayer").modal("hide");
		},
		//주소록 수정
		fnMdfyAddr() {
			let projectId;
			let otherProjectUseYn = jQuery("input[name='otherProjectUseYn']:checked").val();
			if(otherProjectUseYn == 'Y') {
				projectId = 'ALL';
			} else {
				projectId = jQuery("#selectProjectId option:selected").val();
			}

			// 필수값 입력 확인
			if(!this.fnInputCheckReq()) return false;
			var params = {
				"addressCategoryGrpName"	: jQuery("#addressCategoryGrpName").val(),
				"addressCategoryGrpDesc"	: jQuery("#addressCategoryGrpDesc").val(),
				"projectId"					: projectId,
				"addressCategoryGrpId"		: this.row_data.addressCategoryGrpId,
			}
			addressApi.modifyAddr(params).then(response =>{
				var result = response.data;
				if(result.success) {
					confirm.fnAlert("", "주소록 수정을 성공했습니다.");
					//this.$parent.fnSearchAddressCateGrpList();
				} else {
					confirm.fnAlert("", result.message);
				}
			});
			this.fnClose();
			this.fnInit();
		},
		// 필수값 입력 체크
		fnInputCheckReq() {
			let addressCategoryGrpName = jQuery("#addressCategoryGrpName").val();
			let projectId = jQuery("#selectProjectId option:selected").val();
			console.log(">> fnInputCheckReqv - projectId : " + projectId);

			if(addressCategoryGrpName == "" || addressCategoryGrpName == null) {
				confirm.fnAlert("", "주소록명을 입력하세요.");
				return false;
			}

			if(projectId == "" || this.projectId == null) {
				confirm.fnAlert("", "프로젝트를 선택하세요.");
				return false;
			}

			return true;
		},
		// 프로젝트 목록
		fnShowProjectList(status) {
			if(status == 'ALL') {
				this.isShowProjectList = false;
			} else {
				this.isShowProjectList = true;
			}
			console.log('fnProjectListStatus End');
		},
		// 입력값 초기화
		fnInit() {
		},
		// select 박스 선택
		fnSelected() {
			console.log("fnSelected Start");
			var index = jQuery("#selectProjectId option").index(jQuery("#selectProjectId option:selected"));
			console.log("index : " + index);
			if(index != 0) {
				this.projectId = this.projectItems[index-1].projectId;
			}
			console.log("fnSelected End");
		},
	}
}
</script>