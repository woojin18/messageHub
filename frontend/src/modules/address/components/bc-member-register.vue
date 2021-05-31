<template>
	<!-- member Modal -->
	<div class="modal fade modalStyle" id="MemberRegisterLayer" tabindex="-1" role="dialog" aria-hidden="true">
	<!--div v-if="layerView" class="layerPopup"-->
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h">
						<h2>구성원 추가</h2>
						<hr>
						<!-- 검색창 Start -->
						<div class="row">
							<div class="col-xs-12">
								<div class="menuBox">
									<div class="of_h">
										<select class="selectStyle2" style="width:30%" v-model="searchTextType">
											<option value="cuName">수신자명</option>
											<option value="cuid">아이디</option>
											<option value="hpNumber">휴대폰번호</option>
										</select>
										<input type="text" class="inputStyle ml10" style="width:50%" v-model="searchText">
										<a @click='fnSearch()' class="btnStyle1 float-right">검색</a>
									</div>
								</div>
							</div>
						</div>
						<!-- 검색창 End -->

						<!-- 리스트 Start -->
						<div class="row consolMarginTop">
							<div class="col-xs-12">
								<table  class="table_skin1 bt-000 tbl-striped">
									<thead>
										<tr>
											<th class="text-center lc-1">
												<div class="consolCheck ml10">
													<input type="checkbox" id="listMemCheck_all" class="checkStyle2" @change="fnListMemChkAll" v-model="listMemAllChecked">
													<label for="listMemCheck_all"></label>
												</div>
											</th>
											<th class="text-center lc-1">수신자명</th>
											<th class="text-center lc-1">아이디</th>
											<th class="text-center lc-1 end">휴대폰 번호</th>
										</tr>
									</thead>
									<tbody>
										<tr v-for="(data, index) in cmCuList" :key="index">
											<td class="text-cneter">
												<div class="consolCheck ml10">
													<input type="checkbox" :id="'listMemCheck_'+index" class="checkStyle2" :value="data.cuInfoId" v-model="listMemChkBox">
													<label :for="'listMemCheck_'+index"></label>
												</div>
											</td>
											<td class="text-center">{{ data.cuName }}</td>
											<td class="text-center">{{ data.cuid }}</td>
											<td class="text-center end">{{ data.hpNumber }}</td>
										</tr>
										<tr v-if="cmCuList.length == 0">
											<td class="text-center"></td>
											<td class="text-center" colspan="4">검색된 내용이 없습니다.</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
						<!-- 리스트 End -->
						<!-- pagination Start -->
						<div id="pageContent">
							<PageLayer @fnClick="fnPageClick" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
						</div>
						<!-- pagination End-->
					</div>
					<div class="text-center mt20">
						<a @click="fnRegisterMember" class="btnStyle1 backBlack mr5">추가</a>
						<a @click="fnCloseLayer" class="btnStyle1 backWhite">취소</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import addressApi from '../service/addressApi.js'
import confirm from "@/modules/commonUtil/service/confirm";
import PageLayer from '@/components/PageLayer.vue';
import tokenSvc from '@/common/token-service';

export default {
	name: 'MemberLayer',
	components: {
		PageLayer,
	},
	props: {
		searchCategoryId: {
			type: Number,
			require: true
		},
		memberRegisterOpen: {
			type: Boolean,
			require: true,
			default: false,
		},
		componentsTitle: {
			type: String,
			require: false,
			default: function() {
				return '주소록 검색';
			},
		},
	},
	data() {
		return {
			cmCuList: [],
			listSize : 10,	// select 박스 value (출력 갯수 이벤트)
			pageNo : 1,		// 현재 페이징 위치
			totCnt : 0,		// 전체 리스트 수
			offset : 0,		// 페이지 시작점
			searchTextType: 'cuName',
			searchText: '',
			listMemAllChecked: false,
			listMemChkBox: [],
		}
	},
	watch: {
		memberRegisterOpen: function() {
			this.fnSearchCmCuList();
		}
	},
	methods: {
		fnSearch(pageNum) {
			this.$refs.updatePaging.fnAllDecrease();
			this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNum, '1'))*1;
			this.fnSearchCmCuList();
		},
		//수신자 조회
		async fnSearchCmCuList() {
			let params = {
				addressCategoryId: this.searchCategoryId,
				searchTextType: this.searchTextType,
				searchText: this.searchText,
				pageNo: this.pageNo,
				listSize: this.listSize
			};
			await addressApi.selectCmCuList(params).then(response =>{
				const result = response.data;
				this.fnResetChkbox();

				if(result.success) {
					this.cmCuList = Object.assign([], result.data);
					this.totCnt = result.pageInfo.totCnt;
					this.offset = result.pageInfo.offset;
				} else {
					confirm.fnAlert('', result.message);
				}
			});
		},
		fnPageClick(pageNo) {
			this.pageNo = pageNo;
			this.fnSearchCmCuList();
			this.fnResetChkbox();
		},
		//리스트 전체 체크박스
		fnListMemChkAll() {
			var vm = this;
			if(this.listMemAllChecked){
				this.cmCuList.forEach(function(cmcuInfo){
					vm.listMemChkBox.push(cmcuInfo.cuInfoId);
				});
			} else {
				this.listMemChkBox = [];
			}
		},
		// 구성원 추가
		fnRegisterMember() {
			//유효성 검사
			if(this.listMemChkBox == null || this.listMemChkBox.length == 0) {
				confirm.fnAlert(this.componentsTitle, "수신자를 선택해주세요.");
				return false;
			}

			let params = {
				"loginId": tokenSvc.getToken().principal.loginId,
				"addressCategoryId": this.searchCategoryId,
				"memberList": this.listMemChkBox,
			};
			
			addressApi.registerMember(params).then(response =>{
				var result = response.data;
				if(result.success) {
					confirm.fnAlert("", "구성원 등록을 성공했습니다.");
					this.$parent.fnSearchMemberList();					
				} else {
					confirm.fnAlert("", result.message);
				}
			});
			
			this.fnInit();
			this.fnResetChkbox();
			this.fnCloseLayer();
		},
		// 닫기
		fnCloseLayer(){
			this.fnInit();
			this.fnResetChkbox();
			jQuery("#MemberRegisterLayer").modal("hide");
		},
		// 입력값 초기화
		fnInit() {
			this.cmCuList = [];
			this.searchTextType = 'cuName';
			this.searchText = '';
			this.pageNo = 1;
			this.offset = 0;
			this.totCnt = 0;
			this.fnResetChkbox();
		},
		fnResetChkbox(){
			this.listMemAllChecked = false;
			this.listMemChkBox = [];
		},
	},
}
</script>