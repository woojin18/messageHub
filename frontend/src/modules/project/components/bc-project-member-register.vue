<template>
	<!-- Register Modal -->
	<div class="modal fade modalStyle" id="regMembmerPop" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>멤버추가</h2>
						<hr>
						<!-- 검색창 Start -->
						<div class="of_h">
							<select class="selectStyle2 inline-block float-left" style="width:20%" v-model="searchTextType" title="이용권한 선택란">
								<option value="userName">사용자명</option>
								<option value="loginId">아이디</option>
							</select>
							<div style="width:78%" class="of_h inline-block float-right">
								<input type="text" class="inputStyle" style="width:70%" v-model="searchText" title="사용자 ID 입력란" @keypress.enter="fnPageNoResetSearch()">
								<a @click="fnPageNoResetSearch()" class="btnStyle1 backLightGray float-right" style="width:28%" activity="READ" title="중복체크">검색</a>
							</div>
						</div>
						<!-- 검색창 End -->

						<!-- 리스트 Start -->
						<div class="row">
							<div class="col-xs-12 consolMarginTop">
								<table class="table_skin1 tbl-striped" style="width:100%">
									<caption>사용자 목록의 No, 사용자명, 이용 프로젝트, 아이디, 이용권한, 상태, 관리를 제공하는 표</caption>
									<colgroup>
										<col>
										<col style="width:33%">
										<col style="width:33%">
										<col style="width:*">
									</colgroup>
									<thead>
										<tr>
											<th class="text-center lc-1">
												<div class="consolCheck ml10">
													<input type="checkbox" id="listMemCheck_all" class="checkStyle2" @change="fnListMemChkAll" v-model="listMemAllChecked">
													<label for="listMemCheck_all"></label>
												</div>
											</th>
											<th class="text-center lc-1">사용자명</th>
											<th class="text-center lc-1">아이디</th>
											<th class="text-center lc-1 end">이용권한</th>
										</tr>
									</thead>
									<tbody>
										<tr v-for="(data, index) in userList" :key="index">
											<td class="text-cneter">
												<div class="consolCheck ml10">
													<input type="checkbox" :id="'listMemCheck_'+index" class="checkStyle2" :value="data.userId" v-model="listMemChkBox">
													<label :for="'listMemCheck_'+index"></label>
												</div>
											</td>
											<td class="text-center">{{ data.userName }}</td>
											<td class="text-center lc-1">{{ data.loginId}}</td>
											<td class="text-center lc-1 end">{{ data.roleCd }}</td>
										</tr>
										<tr v-if="userList.length == 0">
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
						<p class="color3 consolMarginTop"><i class="far fa-info-circle"></i> * 멤버를 추가하기 위해서는 사용자관리에 우선 등록이 되어 있어야 합니다.</p>
					</div>
					<div class="text-center mt20">
						<a @click="fnRegisterMember" class="btnStyle1 backBlack mr5" activity="SAVE" title="추가">추가</a>
						<a @click="fnClose" class="btnStyle1 backWhite"  title="취소">취소</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import memberApi from '../service/memberApi.js'
import tokenSvc from '@/common/token-service';
import PageLayer from '@/components/PageLayer.vue';
import confirm from '@/modules/commonUtil/service/confirm';

export default {
	name: 'MemberRegisterPop',
	components: {
		PageLayer,
	},
	props: {
		memberRegisterOpen: {
			type: Boolean,
			require: true,
			default: false,
		},
	},
	data() {
		return {
			userList: [],
			listSize : 10,	// select 박스 value (출력 갯수 이벤트)
			pageNo : 1,		// 현재 페이징 위치
			totCnt : 0,		// 전체 리스트 수
			offset : 0,		// 페이지 시작점
			searchTextType: 'userName',
			searchText: '',
			listMemAllChecked: false,
			listMemChkBox: [],
		}
	},
	mounted() {
		this.fnPageNoResetSearch();
	},
	watch: {
		memberRegisterOpen: function() {
			jQuery('#selectStatus').val('userName').attr('selected', 'selected');
			this.fnSearchUserList();
		}
	},
	methods: {
		fnSearchUser(pageNum) {
			this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNum, '1'))*1;
			this.fnSearchUserList();
		},
		fnPageNoResetSearch(){
			this.$refs.updatePaging.fnAllDecrease();
		},
		//사용자 조회
		async fnSearchUserList() {
			let params = {
				searchTextType: this.searchTextType,
				searchText: this.searchText,
				pageNo: this.pageNo,
				listSize: this.listSize,
				corpId: tokenSvc.getToken().principal.corpId,
				projectId: this.$parent.projectId,
			};

			await memberApi.selectUserList(params).then(response =>{
				const result = response.data;
				this.fnResetChkbox();

				if(result.success) {
					this.userList = Object.assign([], result.data);
					this.totCnt = result.pageInfo.totCnt;
					this.offset = result.pageInfo.offset;
				} else {
					confirm.fnAlert('', result.message);
				}
			});
		},
		fnPageClick(pageNo) {
			this.pageNo = pageNo;
			this.fnSearchUserList();
			this.fnResetChkbox();
		},
		// 닫기
		fnClose(){
			this.fnInit();
			jQuery('#regMembmerPop').modal('hide');
		},
		// 입력값 초기화
		fnInit() {
			this.userList = [];
			this.searchTextType = 'userName';
			this.searchText = '';
			this.pageNo = 1;
			this.offset = 0;
			this.totCnt = 0;
		},
		//리스트 전체 체크박스
		fnListMemChkAll() {
			var vm = this;
			if(this.listMemAllChecked) {
				this.userList.forEach(function(data) {
					vm.listMemChkBox.push(data.userId);
				});
			} else {
				this.listMemChkBox = [];
			}
		},
		// 멤버 추가
		fnRegisterMember() {
			//유효성 검사
			if(this.listMemChkBox == null || this.listMemChkBox.length == 0) {
				confirm.fnAlert('', '사용자를 선택해주세요.');
				return false;
			}

			let params = {
				'loginId': tokenSvc.getToken().principal.loginId,
				'projectId': this.$parent.projectId,
				'userList': this.listMemChkBox,
			};

			memberApi.registerMember(params).then(response =>{
				var result = response.data;
				if(result.success) {
					this.$parent.fnSearch(1);
					confirm.fnAlert('', '등록을 성공했습니다.');
				} else {
					confirm.fnAlert('', result.message);
				}
			});
			this.fnInit();
			this.fnResetChkbox();
			this.fnClose();
		},
		fnResetChkbox() {
			this.listMemAllChecked = false;
			this.listMemChkBox = [];
		},
	}
}
</script>