<template>
	<!-- Making Modal -->
	<!--div class="modal fade modalStyle" id="Making" tabindex="-1" role="dialog" aria-hidden="true"-->
	<div v-if="layerView" class="layerPopup">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h">
						<h2>주소록 등록</h2>
						<hr>
						<h5 class="inline-block">주소록 설명</h5>
						<div class="of_h">
							<h5 class="inline-block" style="width:18%">주소록명 *</h5>
							<div class="float-right" style="width:80%">
								<input type="text" v-model="addressCategoryGrpName" class="inputStyle" />
							</div>
						</div>
						<div class="of_h consolMarginTop">
								<div class="float-right" style="width:80%">
								<input type="text" v-model="addressCategoryGrpDesc" class="inputStyle" />
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block">타 프로젝트<br>사용여부 *</h5>
							<div class="float-right consolMarginTop" style="width:80%">
								<input type="radio" id="Y" name="otherProjectUseYn" value="Y" v-model="otherProjectUseYN">
								<label @click="fnProjectListStatus(true)" for="Y">사용</label>
								<input type="radio" id="N" name="otherProjectUseYn" value="N" v-model="otherProjectUseYN">
								<label @click="fnProjectListStatus(false)" for="N">미사용</label>
							</div>
						</div>
						<span v-if="showProjectFlag != true ">
							<div class="of_h consolMarginTop">
								<h5 class="inline-block">프로젝트</h5>
								<div class="float-right" style="width:80%">
									<select ref="addCateGrp"  class="selectStyle2" style="width:50%" v-model="newProjectId">
									<option value="" selected>전체</option>
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
								<input type="radio" id="regUseY" name="regUseYn" value="Y" v-model="regUseYn">
								<label for="regUseY">사용</label>
								<input type="radio" id="regYseN" name="regUseYn" value="N" v-model="regUseYn">
								<label for="regYseN">미사용</label>
							</div>
						</div>
						<p class="consolMarginTop"><i class="far fa-info-circle"></i> 타프로젝트 사용여부를 전용을 선택하신 경우 프로젝트를 선택하셔야 합니다.</p>
					</div>
					<div class="text-center mt20">
						<a @click="fnRegisterAddr()" class="btnStyle1 backBlack mr5" >등록</a>
						<a @click="fnCloseLayer()" class="btnStyle1 backWhite">취소</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import addressApi from '../service/addressApi.js'
import confirm from "@/modules/commonUtil/service/confirm";
import tokenSvc from '@/common/token-service';

export default {
	name: 'AddrRegMdfyLayer',
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
		addrRegMdfyStatus: {
			type: String,
			require: true
		}
	},
	data() {
		return {
			isDupcAddr: false,
			projectItems: [],
			addressCategoryGrpName : '',
			addressCategoryGrpDesc : '',
			showProjectFlag : true,
			otherProjectUseYN: 'Y',
			newProjectId: '',
			regUseYn: 'Y',
		}
	},
	mounted() {
		this.fnSearchProjectList();
	},
	methods: {
		async fnSearchProjectList() {
			console.log("fnSearchProjectList Start");
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
		fnCloseLayer(){
			this.$emit('update:layerView', false);
		},
		//주소록 등록 / 수정
		fnRegisterAddr() {
			if(this.otherProjectUseYN == 'Y') this.newProjectId = 'ALL';

			// 필수값 입력 확인
			if(!this.fnInputCheckReq()) return false;

			var params = {
				"addressCategoryGrpName"	: this.addressCategoryGrpName,
				"addressCategoryGrpDesc"	: this.addressCategoryGrpDesc,
				"newProjectId"				: this.newProjectId,
				"addrRegMdfyStatus"			: this.addrRegMdfyStatus,
				"loginId"					: tokenSvc.getToken().principal.loginId,
				"regUseYn"					: this.regUseYn,
			}

			addressApi.registerAddr(params).then(response =>{
				var result = response.data;
				if(result.success) {
					confirm.fnAlert("", "주소록 등록을 성공했습니다.");
					this.$parent.fnSearchAddressCateGrpList();
				} else {
					confirm.fnAlert("", result.message);
				}
			});
			this.fnCloseLayer();
			this.fnInit();
		},
		// 필수값 입력 체크
		fnInputCheckReq() {
			if(this.addressCategoryGrpName == "" || this.addressCategoryGrpName == null) {
				confirm.fnAlert("", "주소록명을 입력하세요.");
				return false;
			}

			if(this.otherProjectUseYN == "N" && (this.newProjectId == "" || this.newProjectId == null) ) {
				confirm.fnAlert("", "프로젝트를 선택하세요.");
				return false;
			}

			return true;
		},
		// 프로젝트 목록
		fnProjectListStatus(flag) {
			if(flag) this.newProjectId = "ALL";
			this.showProjectFlag = flag;
		},
		// 입력값 초기화
		fnInit() {
			this.addressCategoryGrpName = '';
			this.addressCategoryGrpDesc = '';
			this.newProjectId = '';
		}
	}
}
</script>