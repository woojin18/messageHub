<template>
	<div class="modal modalStyle" id="AddrCategoryLayer" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h">
						<div v-if="saveStatus == 'I'">
							<h2>카테고리 등록</h2>
						</div>
						<div v-else>
							<h2>카테고리 수정</h2>
						</div>
						<hr>
						<!-- 상위카테고리는 등록시에만 표기 -->
						<div v-if="saveStatus == 'I'">
							<div class="of_h">
								<h5 class="inline-block" style="width:18%">상위카테고리</h5>
								<div class="float-right" style="width:80%">
									{{addrCateName}}
								</div>
							</div>
						</div>
						<div v-else>
							<div class="of_h">
								<h5 class="inline-block" style="width:18%">상위카테고리</h5>
								<div class="float-right" style="width:80%">
									{{addrCateGrpName}}
								</div>
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block">카테고리명</h5>
							<div class="float-right" style="width:80%">
								<input type="text" class="inputStyle" v-model="cateName" maxlength="50" />
							</div>
						</div>
					</div>
					<div class="text-center mt20">
						<a @click="fnSaveAddrCate" class="btnStyle1 backBlack mr5" activity="SAVE">저장</a>
						<a @click="fnClose" class="btnStyle1 backWhite">취소</a>
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
import {eventBus} from '@/modules/commonUtil/service/eventBus';

export default {
	name: 'CategoryLayer',
	props: {
		addrCateGrpId: {
			type: Number,
			require: false,
		},
		parAddrCateId: {
			type: Number,
			require: true,
		},
		addrCateOpen: {
			type: Boolean,
			require: true,
		},
		addrCateName: {
			type: String,
			require: true,
		},
		topAddrCateId: {
			type: Number,
			require: true,
		},
		saveStatus: {
			type: String,
			require: true,
		},
		addrCateGrpName: {
			type: String,
			require: false,
		},
		parDepthNumber: {
			type: Number,
			require: false,
		},
	},
	data() {
		return {
			title: '카테고리 등록/수정',
			cateName: '',
		}
	},
	watch: {
		addrCateOpen: function() {
			this.cateName = '';
			if(this.saveStatus == 'U') {
				this.cateName = this.addrCateName;
			}
		}
	},
	mounted() {
		//
	},
	methods: {
		// 주소카테고리저장
		fnSaveAddrCate() {
			//유효성 검사
			if(this.$gfnCommonUtils.isEmpty(this.cateName)) {
				confirm.fnAlert(this.title, '카테고리명을 입력하세요');
				return false;
			}

			eventBus.$on('callbackEventBus', this.fnSaveAddrCateCallBack);
			confirm.fnConfirm(this.title, '저장하시겠습니까?', '확인');
		},
		fnSaveAddrCateCallBack() {
			let params;
			if(this.saveStatus == 'I') {
				params = {
					'addressCategoryGrpId': this.addrCateGrpId,
					'regId': tokenSvc.getToken().principal.userId,
					'addressCategoryName': this.cateName,
					'saveStatus': this.saveStatus,
					'depthNumber' : this.parDepthNumber + 1, // 상위 카테고리의 depth + 1이 현재 depth
				};

				if(this.parAddrCateId  == -1) { // root 하위에 카테고리 추가
					params.parAddressCategoryId = 0;
				} else {
					params.parAddressCategoryId = this.parAddrCateId;
				}
			} else {
				params = {
					'regId': tokenSvc.getToken().principal.userId,
					'addressCategoryName': this.cateName,
					'saveStatus': this.saveStatus,
					'addressCategoryId': this.parAddrCateId,
				};
			}

			addressApi.saveAddressCategory(params).then(response =>{
				var result = response.data;
				if(result.success) {
					confirm.fnAlert(this.title, '저장에 성공했습니다.');
					this.$parent.fnGetAddrList();
				} else {
					confirm.fnAlert(this.title, result.message);
				}
			});

			this.cateName = '';
			this.fnClose();
		},
		// 닫기
		fnClose() {
			jQuery('#AddrCategoryLayer').modal('hide');
		},
	}
}

</script>