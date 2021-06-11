<template>
	<div id="content">
		<article>
			<div class="contentHeader">
				<h2>주소록</h2>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<div class="menuBox">
						<div>
							<h4 class="font-normal inline-block" style="width:12%">주소록 선택</h4>
							<select ref="selectAddrCateGrp" v-on:change="fnSelected" class="selectStyle2" style="width:25%">
								<option value="" selected>주소록을 선택해 주세요</option>
								<option v-for="(content, index) in addrCateGrpList" :key="index" :value="content.addressCategoryGrpId">
									{{ content.addressCategoryGrpName }}
								</option>
							</select>
							<a @click="fnRegAddrPop()" class="btnStyle1 ml20" title="주소록 등록">등록</a>
							<a @click="fnModAddrPop()" class="btnStyle1 ml5" title="주소록 수정">수정</a>
						</div>
						<p class="txtCaption color4" style="margin-left:12.5%;margin-top:10px">{{ selectedAddrCateGrp.addressCategoryGrpDesc }}</p>
					</div>
				</div>
			</div>
			<div class="row mt15">
				<div class="col-xs-4">
					<div class="fl border-line" style="width:100%; height:500px">
						<div class="bb-d5 pd20">
							<div v-if="!this.$gfnCommonUtils.isEmpty(selectedAddrCateGrp.addressCategoryGrpName)">
								<h4>{{ selectedAddrCateGrp.addressCategoryGrpName }}</h4>
							</div>
							<div v-else>
								<h4>주소록</h4>
							</div>
						</div>
						<div class="bb-d5 pd20" style="height:340px">
							<div v-if="!this.$gfnCommonUtils.isEmpty(selectedAddrCateGrp.addressCategoryGrpId)">
								<!-- addList -->
								<addr-tree-menu v-for="(addrTreeData, idx) in addrTreeList" :key="idx"
									:item="addrTreeData.addressName"
									:id="addrTreeData.addressCategoryId"
									:subItems="addrTreeData.subItems">
								</addr-tree-menu>
								<!--
								<ul class="addList">
									<li class="addList_minus">
										<a href="#self">
											<i class="fal fa-minus-square addIcon"></i>
												{{ curAddCateGrpName }}
										</a>
										<ul>
											<li>
												<a href="#self">
													<i class="fal fa-minus-square addIcon"></i>
													관리팀
												</a>
												<ul>
													<li><a href="#self">사업관리팀</a></li>
												</ul>
											</li>
											<li class="addList_minus">
												<a href="#self">
													<i class="fal fa-minus-square addIcon"></i>
													마케팅팀
												</a>
												<ul>
													<li>
														<a href="#self" class="active">통합메시징팀</a>
													</li>
													<li class="last">
														<a href="#self">테스트팀</a>
													</li>
												</ul>
											</li>
											<li class="addList_plus">
												<a href="#self">
													<i class="fal fa-plus-square addIcon"></i>
													신규 사업팀
												</a
												<ul>
													<li><a href="#self">통합메시징팀</a></li>
													<li><a href="#self">통합메시징팀</a></li>
													<li><a href="#self">통합메시징팀</a></li>
													<li class="last"><a href="#self">테스트팀</a></li>
												</ul>
											</li>
										</ul>
									</li>
								</ul>
								-->
								<!-- //addList -->
							</div>
						</div>

						<div class="of_h pd20">
							<div class="of_h text-right">
								<div class="of_h" style="width:100%">
									<a href="#self" class="btnStyle1 borderGray mr10">하위 카테고리 추가</a>
									<a href="#self" class="btnStyle1 borderGray">카테고리 삭제</a>
								</div>
							</div>
						</div>

					</div>
				</div>
				<div class="col-xs-8">
					<div class="fl border-line" style="width:100%">
						<div class="bb-d5 pd20">
							<h4>구성원</h4>
						</div>
						<div class="bb-d5">
							<!--table class="table_skin4"  style="width:100%"-->
							<table class="table_skin1 tbl-striped" style="width:100%">
								<colgroup>
									<col style="width:10%">
									<col style="width:20%">
									<col style="width:30%">
									<col>
								</colgroup>
								<thead>
									<tr>
										<th class="text-center lc-1">
											<div class="consolCheck ml10">
												<input type="checkbox" id="listCheck_all" class="checkStyle2" @change="fnListChkAll" v-model="listAllChecked">
												<label for="listCheck_all"></label>
											</div>
										</th>
										<th class="text-center lc-1">사용자명</th>
										<th class="text-center lc-1">사용자 아이디</th>
										<th class="text-center lc-1 end">휴대폰번호</th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="(data, index) in memberList" :key="data.cuInfoId">
										<td class="text-cneter">
											<!--
											<div class="consolCheck ml10">
												<input type="checkbox" id="check1" class="checkStyle2" value="check1" />
													<label for="check1"></label>
											</div>
											-->
											<div class="consolCheck ml10">
												<input type="checkbox" :id="'listCheck_'+index" class="checkStyle2" :value="data.cuInfoId" v-model="listChkBox">
												<label :for="'listCheck_'+index"></label>
											</div>
										</td>
										<td class="text-left color4"> {{ data.cuName }} </td>
										<td class="text-left color4"> {{ data.cuid }} </td>
										<td class="text-cneter color4 end"> {{ data.hpNumber }} </td>
									</tr>
									<tr v-if="memberList.length == 0">
										<td class="text-center"></td>
										<td class="text-center" colspan="4">검색된 내용이 없습니다.</td>
									</tr>
								</tbody>
							</table>
						</div>
						<!-- pagination Start -->
						<div id="pageContent">
							<PageLayer @fnClick="fnPageClick" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
						</div>
						<!-- pagination End-->
						<div class="bb-d5 height120">
						</div>
						<div class="pd20">
							<div class="of_h">
								<div class="float-right">
									<a @click="fnRegisterMemberPop" class="btnStyle1 borderGray ml10">구성원 추가</a>
									<a @click="fnDeletemember" class="btnStyle1 backWhite ml10">구성원 삭제</a>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
			<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
		</article>

		<!-- address register /-->
		<AddrRegisterLayer :layerView.sync="addrRegisterLayerView" :addrRegisterOpen="addrRegisterOpen"></AddrRegisterLayer>
		<!--  modify Modal -->
		<AddrModifyLayer :data="selectedAddrCateGrp" :addrModifyOpen="addrModifyOpen"></AddrModifyLayer>
		<!-- Receiver register Modal -->
		<!--
		<RcvrRegLayer :title="rcvrRegLayerTitle" :layerView.sync="rcvrRegLayerView"></RcvrRegLayer>
		-->
		<!-- Member register Modal -->
		<MemberRegisterLayer :searchCategoryId="searchCategoryId" :memberRegisterOpen="memberRegisterOpen"></MemberRegisterLayer>
	</div>
</template>

<script>
import addressApi from '../service/addressApi'
import tokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm";
import AddrTreeMenu from "@/modules/address/components/bc-addressTree.vue";
import AddrRegisterLayer from '../components/bc-address-register.vue';
import AddrModifyLayer from '../components/bc-address-modify.vue';
import MemberRegisterLayer from '../components/bc-member-register.vue';
import PageLayer from '@/components/PageLayer.vue';

export default {
	name: "addressManageList",
	components: {
		AddrTreeMenu,
		AddrRegisterLayer,
		AddrModifyLayer,
		MemberRegisterLayer,
		PageLayer,
	},
	props: {
		searchCateGrpData : {
			type: Object,
			require: false,
			default: function() {
				return {
					'addressCategoryGrpId'		: '',
					'corpId'					: tokenSvc.getToken().principal.corpId,
					'projectId'					: '',
					'addressCategoryGrpName'	: '',
					'addressCategoryGrpDesc'	: '',
					'disOrder'					: '',
					'useYn'						: '',
					'regId'						: '',
					'regDt'						: ''
				}
			}
		},
	},
	data() {
		return {
			selectedAddrCateGrp: {},
			componentsTitle: '주소록 관리',
			addrCateGrpList: [],			//주소 카테고리 그룹 리스트
			addrTreeList: [],
			memberList: [],					//구성원 리스트
			searchCategoryId: -1,
			listAllChecked: false,
			listChkBox: [],

			// 주소록 등록
			addrRegisterLayerView: false,
			addrRegisterOpen: false,

			//주소록 수정
			addrModifyOpen: false,
			memberRegisterOpen: false,

			// 구성원 목록 페이징
			listSize : 5,	// select 박스 value (출력 갯수 이벤트)
			pageNo : 1,		// 현재 페이징 위치
			totCnt : 0,		// 전체 리스트 수
			offset : 0,		// 페이지 시작점
		}
	},
	mounted() {
		this.fnSearchAddressCateGrpList();
	},
	methods: {
		// 주소 카테고리 그룹 조회
		fnSearchAddressCateGrpList() {
			var params = Object.assign({}, this.searchCateGrpData);
			addressApi.selectAddressCateGrpList(params).then(response =>{
				var result = response.data;
				if(result.success) {
					this.addrCateGrpList = result.data;
					this.fnSelected();
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
		// 주소록 선택시 리스트 재출력
		fnSelected() {
			var index = this.$refs.selectAddrCateGrp.selectedIndex;
			if(index == 0) {
				this.selectedAddrCateGrp = {}; // init
			} else {
				this.selectedAddrCateGrp = this.addrCateGrpList[index-1];
			}
			this.fnGetAddrList();
		},
		//주소록 카테고리 리스트 조회
		async fnGetAddrList() {
			this.addrTreeList = []; //초기화
			let params = {
				addressCategoryGrpId: this.selectedAddrCateGrp.addressCategoryGrpId,
			};
			await addressApi.selectAddressList(params).then(response =>{
				var result = response.data;
				if(result.success) {
					this.fnSetAddrListToTree(result.data);
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
		//주소목록을 트리구조로 변경
		fnSetAddrListToTree(addrList) {
			const vm = this;
			const addrCateGrp = Object.assign([], addrList.addrCateGrp);
			let addrCateList = Object.assign([], addrList.addrCateList);

			//주소록 그룹 put
			vm.fnSetSubItems(addrCateList, addrCateGrp, 'Y');
			vm.addrTreeList.push(addrCateGrp);
		},
		// target을 tree형태로 변경
		fnSetSubItems(addrCateList, target, targetGrpYn) {
			const vm = this;
			const addCateId = target.addressCategoryId;
			const addCateGrpId = target.addressCategoryGrpId;
			let addCateInfo;

			if (!('subItems' in target)) target.subItems = [];

			for(let i=0; i < addrCateList.length; i++) {
				addCateInfo = addrCateList[i];
				if(targetGrpYn == 'Y' && addCateInfo.parAddressCategoryId == 0) { // Address Category Group
					if(addCateGrpId == addCateInfo.addressCategoryGrpId) {
						target.subItems.push(addCateInfo);
						vm.fnSetSubItems(addrCateList, target.subItems[target.subItems.length - 1], 'N');
					}
				} else { // Address Category
					if(addCateId == addCateInfo.parAddressCategoryId) { // child item 판단
						target.subItems.push(addCateInfo);
							vm.fnSetSubItems(addrCateList, target.subItems[target.subItems.length - 1], 'N');
					}
				}
			}
		},
		//리스트 전체 체크박스
		fnListChkAll() {
			var vm = this;
			if(this.listAllChecked){
				this.memberList.forEach(function(memberInfo){
					vm.listChkBox.push(memberInfo.cuInfoId);
				});
			} else {
				this.listChkBox = [];
			}
		},
		//주소 카테고리 클릭
		fnAddrCatgMem(addressCategoryId) {
			if(this.$gfnCommonUtils.isEmpty(addressCategoryId)){
				this.memberList = [];
				return;
			}
			this.searchCategoryId = addressCategoryId;
			this.fnSearchMember();
		},
		fnSearchMember(pageNum) {
			this.$refs.updatePaging.fnAllDecrease();
			this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNum, '1'))*1;
			this.fnSearchMemberList();
		},
		//주소록 구성원 조회
		async fnSearchMemberList() {
			let params = {
				addressCategoryId: this.searchCategoryId,
				pageNo: this.pageNo,
				listSize: this.listSize,
			};
			await addressApi.selectMemberList(params).then(response =>{
				var result = response.data;
				if(result.success) {
					//this.memberList = result.data;
					this.memberList = Object.assign([], result.data);
					this.totCnt = result.pageInfo.totCnt;
					this.offset = result.pageInfo.offset;
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
		//페이지 이동
		fnPageClick(pageNo) {
			this.pageNo = pageNo;
			this.fnSearchMemberList();
		},
		// 주소록 카테고리 그룹 등록
		fnRegAddrPop() {
			this.addrRegisterLayerView = true;
			this.addrRegisterOpen = !this.addrRegisterOpen;
		},
		// 주소록 카테고리 그룹 수정
		fnModAddrPop() {
			if(this.$refs.selectAddrCateGrp.selectedIndex == 0) {
				confirm.fnAlert(this.componentsTitle, "주소록을 선택해 주세요");
				return false;
			}
			this.addrModifyOpen = !this.addrModifyOpen;
			jQuery("#AddrModifyLayer").modal("show");
		},
		// 구성원 추가
		fnRegisterMemberPop() {
			this.memberRegisterOpen = !this.memberRegisterOpen;
			jQuery("#MemberRegisterLayer").modal("show");
		},
		// 구성원 삭제
		fnDeletemember() {
			if(this.listChkBox == null || this.listChkBox.length == 0) {
				confirm.fnAlert('구성원 검색', '구성원을 선택해주세요.');
				return false;
			}

			let params = {
				"addressCategoryId": this.searchCategoryId,
				"memberList": this.listChkBox,
			};

			addressApi.deleteMember(params).then(response =>{
				var result = response.data;
				if(result.success) {
					confirm.fnAlert("구성원 삭제", "삭제 했습니다.");
					this.fnSearchMemberList();
					this.fnResetChkbox();
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
		fnResetChkbox() {
			this.listAllChecked = false;
			this.listChkBox = [];
		},
	}
}
</script>