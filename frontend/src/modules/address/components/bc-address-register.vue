<template>
	<!-- Making Modal -->
	<!--div class="modal modalStyle" id="Making" tabindex="-1" role="dialog" aria-hidden="true"-->
	
	<div class="modal modalStyle" id="layerPop" tabindex="-1" role="dialog" aria-hidden="true" >
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h">
						<h2>주소록 등록</h2>
						<hr>
						<div class="of_h">
							<h5 class="inline-block" style="width:18%">주소록명 *</h5>
							<div class="float-right" style="width:80%">
								<input type="text" v-model="addressCategoryGrpName" class="inputStyle" maxlength="50" />
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block">주소록 설명</h5>
							<div class="float-right" style="width:80%">
								<input type="text" v-model="addressCategoryGrpDesc" class="inputStyle" maxlength="200" />
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block">타 프로젝트<br>사용여부 *</h5>
							<div class="float-right consolMarginTop" style="width:80%">
								<input type="radio" id="Y" value="Y" v-model="otherProjectUseYn">
								<label @click="fnShowProjectList(false)" for="Y">공용</label>
								<input type="radio" id="N" value="N" v-model="otherProjectUseYn">
								<label @click="fnShowProjectList(true)" for="N">전용</label>
							</div>
						</div>
						<span v-if="isShowProjectList">
							<div class="of_h consolMarginTop">
								<h5 class="inline-block">프로젝트</h5>
								<div class="float-right" style="width:80%">
									<select class="selectStyle2" style="width:50%" v-model="newProjectId">
										<option value="" disabled selected>전체</option>
										<option v-for="(data, index) in projectItems" :key="index" :value="data.projectId">
											{{ data.projectName }}
										</option>
									</select>
								</div>
							</div>
						</span>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block">사용여부</h5>
							<div class="float-right consolMarginTop" style="width:80%">
								<input type="radio" id="regUseY" value="Y" v-model="regUseYn">
								<label for="regUseY">사용</label>
								<input type="radio" id="regYseN" value="N" v-model="regUseYn">
								<label for="regYseN">미사용</label>
							</div>
						</div>
						<p class="consolMarginTop"><i class="far fa-info-circle"></i> 타프로젝트 사용여부를 전용을 선택하신 경우 프로젝트를 선택하셔야 합니다.</p>
					</div>
					<div class="text-center mt20">
						<a @click="fnRegisterAddr" class="btnStyle1 backBlack mr5" activity="SAVE">저장</a>
						<a @click="fnCloseLayer" class="btnStyle1 backWhite">취소</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import addressApi from '../service/addressApi.js'
import confirm from '@/modules/commonUtil/service/confirm';
import tokenSvc from '@/common/token-service';

export default {
	name: 'AddrRegMdfyLayer',
	props: {
		layerView: {
			type: Boolean,
			require: true,
			default: false
		},
		addrRegisterOpen: {
			type: Boolean,
			require: true,
		},
	},
	data() {
		return {
			title: '주소록 등록',
			isDupcAddr: false,
			projectItems: [],
			addressCategoryGrpName : '',
			addressCategoryGrpDesc : '',
			isShowProjectList : false,
			otherProjectUseYn: 'Y',
			newProjectId: '',
			regUseYn: 'Y',
		}
	},
	watch: {
		addrRegisterOpen: function() {
			this.addressCategoryGrpName = '';
			this.addressCategoryGrpDesc = '';
			this.otherProjectUseYn = 'Y'
			this.newProjectId = '';
			this.isShowProjectList = false;
			this.regUseYn = 'Y'
		}
	},
	mounted() {
		this.fnSearchProjectList();
	},
	methods: {
		async fnSearchProjectList() {
			let params = {};
			await addressApi.selectProjectList(params).then(response =>{
				var result = response.data;
				if(result.success) {
					this.projectItems = result.data;
				} else {
					confirm.fnAlert(this.title, result.message);
				}
			});
		},
		// 닫기
		fnCloseLayer(){
			jQuery("#layerPop").modal("hide");
		},
		//주소록 카테고리 그룹 등록
		fnRegisterAddr() {
			//타 프로젝트 사용여부
			let newProjectId = ''
			if(this.otherProjectUseYn == 'Y') // '사용'
				newProjectId = 'ALL';
			else // 미사용 (프로젝트 선택)
				newProjectId = this.newProjectId;

			// 필수값 입력 확인
			if(!this.fnInputCheckReq()) return false;

			// 주소록 카테고리 그룹 등록
			var params = {
				'addressCategoryGrpName'	: this.addressCategoryGrpName,
				'addressCategoryGrpDesc'	: this.addressCategoryGrpDesc,
				'newProjectId'				: newProjectId,
				'regId'						: tokenSvc.getToken().principal.userId,
				'regUseYn'					: this.regUseYn,
			}

			addressApi.registerAddr(params).then(response =>{
				var result = response.data;
				if(result.success) {
					confirm.fnAlert(this.title, '주소록 등록을 성공했습니다.');
					this.$parent.fnSearchAddressCateGrpList();
				} else {
					confirm.fnAlert(this.title, result.message);
				}
			});

			this.fnCloseLayer();
		},
		// 필수값 입력 체크
		fnInputCheckReq() {
			if(this.$gfnCommonUtils.isEmpty(this.addressCategoryGrpName)) {
				confirm.fnAlert(this.title, '주소록명을 입력하세요.');
				return false;
			}
			if(this.otherProjectUseYn == 'N' && this.$gfnCommonUtils.isEmpty(this.newProjectId) ) {
				confirm.fnAlert(this.title, '프로젝트를 선택하세요.');
				return false;
			}
			return true;
		},
		// 프로젝트 목록
		fnShowProjectList(flag) {
			this.isShowProjectList = flag;
		},
	}
}
</script>