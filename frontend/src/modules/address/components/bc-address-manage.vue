<template>
	<div>
		<article>
			<div class="contentHeader">
				<h2>주소록 > 주소록</h2>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<div class="menuBox">
						<div>
							<h4 class="font-normal inline-block" style="width:12%">주소록 선택</h4>
							<select ref="selectAddrCateGrp" v-on:change="fnSelected" class="selectStyle2" style="width:25%" v-model="selectAddressCategoryGrpId">
								<option value="" selected>주소록을 선택해 주세요</option>
								<option v-for="(content, index) in addrCateGrpList" :key="index" :value="content.addressCategoryGrpId">
									{{ content.addressCategoryGrpFullName }}
								</option>
							</select>
							<a @click="fnRegAddrPop()" class="btnStyle1 ml20" title="주소록 등록">등록</a>
							<a @click="fnModAddrPop()" class="btnStyle1 ml5" title="주소록 수정">수정</a>
							<a @click="fnDelAddrPopConfirm()" class="btnStyle1 ml5" title="주소록 삭제">삭제</a>
						</div>
						<p class="txtCaption color4" style="margin-left:12.5%;margin-top:10px">{{ selectedAddrCateGrp.addressCategoryGrpDesc }}</p>
					</div>
				</div>
			</div>
			<div class="row mt15">
				<div class="col-xs-4">
					<div class="fl border-line" style="width:100%">
						<div class="bb-d5 pd20">
							<div v-if="!this.$gfnCommonUtils.isEmpty(selectedAddrCateGrp.addressCategoryGrpName)">
								<h4 class="mb0 mt0">{{ selectedAddrCateGrp.addressCategoryGrpName }}</h4>
							</div>
							<div v-else>
								<h4 class="mb0 mt0">주소록</h4>
							</div>
						</div>
						<div class="bb-d5 pd20 scroll-y" style="height:380px">
							<div v-if="!this.$gfnCommonUtils.isEmpty(selectedAddrCateGrp.addressCategoryGrpId)">
								<div id="addrTreeMenu">
								</div>
							</div>
						</div>

						<div class="of_h pd20">
							<div class="of_h text-right">
								<div class="of_h" style="width:100%">
									<a @click="fnSaveMemberPop('I')" class="btnStyle1 borderGray mr10">그룹 추가</a>
									<a @click="fnSaveMemberPop('U')" class="btnStyle1 borderGray mr10">수정</a>
									<a @click="fndelAddrCate" class="btnStyle1 borderGray" activity="SAVE">삭제</a>
								</div>
							</div>
						</div>

					</div>
				</div>
				<div class="col-xs-8">
					<div class="fl border-line" style="width:100%">
						<div class="bb-d5 pd20">
							<h4 class="mb0 mt0">구성원</h4>
						</div>
						<div class="bb-d5 scroll-y" style="height:380px">
							<table class="table_skin4"  style="width:100%">
								<colgroup>
									<col style="width:10%">
									<col style="width:22%">
									<col style="width:22%">
									<col style="width:22%">
									<col>
								</colgroup>
								<tbody>
									<tr v-for="(data, index) in memberList" :key="index">
										<td class="text-center">
											<div class="consolCheck ml10">
												<input type="checkbox" :id="'listCheck_'+index" class="checkStyle2" :value="data.cuInfoId+','+data.addressCategoryId" v-model="listChkBox">
												<label :for="'listCheck_'+index"></label>
											</div>
										</td>
										<td class="text-left color4"> {{ data.addressCategoryName }} </td>
										<td class="text-left color4"> {{ data.cuName }} </td>
										<td class="text-left color4"> {{ data.cuid }} </td>
										<td class="text-center color4 end"> {{ data.hpNumber | phoneNumber }} </td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="pd20">
							<div class="of_h">
								<div class="float-left" style="margin-left:24px">
									<div class="consolCheck">
										<input type="checkbox" id="listCheck_all" class="checkStyle2" @change="fnListChkAll" v-model="listAllChecked">
										<label for="listCheck_all">전체선택</label>
									</div>
								</div>
								<div class="float-right">
									<a @click="fnRegisterMemberPop" class="btnStyle1 borderGray ml10">추가</a>
									<a @click="fnDeletemember" class="btnStyle1 backWhite ml10" activity="SAVE">삭제</a>
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
		<!-- Member register Modal -->
		<MemberRegisterLayer
			:searchAddrCateId="searchAddrCateId"
			:memberRegisterOpen="memberRegisterOpen"
		></MemberRegisterLayer>
		<!-- address category register Modal -->
		<AddrCategoryLayer
			:addrCateName="selectAddrName"
			:addrCateGrpId="selectedAddrCateGrp.addressCategoryGrpId"
			:parAddrCateId="searchAddrCateId"
			:addrCateOpen="addrCateOpen"
			:saveStatus="saveStatus"
			:addrCateGrpName="selectedAddrCateGrp.addressCategoryGrpName"
			:parDepthNumber="selectDepthNumber"
		></AddrCategoryLayer>
	</div>
</template>

<script>
import addressApi from '../service/addressApi'
import tokenSvc from '@/common/token-service';
import confirm from '@/modules/commonUtil/service/confirm';
import AddrModifyLayer from '../components/bc-address-modify.vue';
import AddrRegisterLayer from '../components/bc-address-register.vue';
import AddrCategoryLayer from '../components/bc-category-register.vue';
import MemberRegisterLayer from '../components/bc-member-register.vue';
import {eventBus} from '@/modules/commonUtil/service/eventBus';

export default {
	name: 'addressManageList',
	components: {
		AddrRegisterLayer,
		AddrModifyLayer,
		MemberRegisterLayer,
		AddrCategoryLayer,
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
	filters:{
		phoneNumber(val) {
			if(!val) return val

			val = val.replace(/[^0-9]/g, '')

			let tmp = ''
			if( val.length < 4){
				return val;
			} else if(val.length < 7) {
				tmp += val.substr(0, 3);
				tmp += '-';
				tmp += val.substr(3);
				return tmp;
			} else if(val.length == 8) {
				tmp += val.substr(0, 4);
				tmp += '-';
				tmp += val.substr(4);
				return tmp;
			} else if(val.length < 10) {
				if(val.substr(0, 2) =='02') { //02-123-5678
					tmp += val.substr(0, 2);
					tmp += '-';
					tmp += val.substr(2, 3);
					tmp += '-';
					tmp += val.substr(5);
					return tmp;
				}
			} else if(val.length < 11) {
				if(val.substr(0, 2) =='02') { //02-1234-5678
					tmp += val.substr(0, 2);
					tmp += '-';
					tmp += val.substr(2, 4);
					tmp += '-';
					tmp += val.substr(6);
					return tmp;
				} else { //010-123-4567
					tmp += val.substr(0, 3);
					tmp += '-';
					tmp += val.substr(3, 3);
					tmp += '-';
					tmp += val.substr(6);
					return tmp;
				}
			} else { //010-1234-5678
				tmp += val.substr(0, 3);
				tmp += '-';
				tmp += val.substr(3, 4);
				tmp += '-';
				tmp += val.substr(7);
				return tmp;
			}
		}
	},
	created () {
		window.fnAddrCateMem = this.fnAddrCateMem.bind(this);
	},
	data() {
		return {
			//주소록관리
			title: '주소록 관리',
			selectedAddrCateGrp: {},
			addrCateGrpList: [],
			addrTreeList: [],
			// 주소록 등록
			addrRegisterLayerView: false,
			addrRegisterOpen: false,
			//주소록 수정
			addrModifyOpen: false,
			// 주소 카테고리 등록/수정
			addrCateOpen: false,
			saveStatus: '',
			// 구성원 목록
			memberList: [],
			memberRegisterOpen: false,
			listAllChecked: false,
			listChkBox: [],
			searchAddrCateId: -1,
			//Tree 목록에서 선택값
			selectAddrName: '',
			//Root선택여부
			isSelectRoot: false,
			//Tree 목록에서 선택 건의 뎁스
			selectDepthNumber: -1,
			selectAddressCategoryGrpId : ""
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
					confirm.fnAlert(this.title, result.message);
				}
			});
		},
		// 주소록 선택시 리스트 재출력
		fnSelected() {
			var index = this.$refs.selectAddrCateGrp.selectedIndex;
			this.memberList = [];
			this.searchAddrCateId = -1;
			this.selectAddrName = '';
			this.isSelectRoot = false;
			this.selectDepthNumber = -1;

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
					confirm.fnAlert(this.title, result.message);
				}
			});
		},
		//TreeView Root 생성
		fnDrawTreeMenu() {
			let setTag = '';
			setTag += '<ul class="addList">';
			setTag += '<li class="addList_minus">';
			setTag += '<a href="javascript:void(0)" onclick="fnAddrCateMem(null, \'' + this.addrTreeList[0].addressName + '\',' + this.addrTreeList[0].parAddressCategoryId +', this, true);">';
			setTag += '<i class="fal fa-minus-square addIcon"></i>'
			setTag += this.addrTreeList[0].addressName;
			setTag += '</a>';
			setTag += this.fndDrawSubitems(this.addrTreeList[0].subItems);
			setTag += '</li>';
			setTag += '</ul>';
			jQuery("#addrTreeMenu").html(setTag);
		},
		//TreeView 하위 노드 생성
		fndDrawSubitems(child) {
			if(this.$gfnCommonUtils.isEmpty(child) || child.length == 0) {
				return '';
			}
			let tag = '<ul>';
			for(let i = 0; i < child.length; i++) {
				if(child[i].subItems.length > 0) {
					tag += '<li class="addList_minus">'; // class only subItem
				} else if( child.length == i+1) {
					tag += '<li class="last">';
				} else {
					tag += '<li>';
				}
				tag += '<a href="javascript:void(0)" onclick="fnAddrCateMem('+ child[i].addressCategoryId + ',\'' + child[i].addressName + '\',' + child[i].depthNumber +', this, false);">';

				if(child[i].subItems.length > 0) {
					tag += '<i class="fal fa-minus-square addIcon"></i>'; // if only subItem
				}
				tag += child[i].addressName;
				tag += '</a>';
				tag += this.fndDrawSubitems(child[i].subItems);
				tag += '</li>';
			}
			tag += '</ul>';
			return tag;
		},
		//주소목록을 트리구조로 변경
		fnSetAddrListToTree(addrList) {
			const addrCateGrp = Object.assign([], addrList.addrCateGrp);
			let addrCateList = Object.assign([], addrList.addrCateList);

			//주소록 그룹 put
			this.fnSetSubItems(addrCateList, addrCateGrp, 'Y');
			this.addrTreeList.push(addrCateGrp);
			this.fnDrawTreeMenu();
		},
		// target을 tree형태로 변경
		fnSetSubItems(addrCateList, target, targetGrpYn) {
			const vm = this;
			const addrCateId = target.addressCategoryId;
			const addrCateGrpId = target.addressCategoryGrpId;
			let addrCateInfo;

			if (!('subItems' in target)) target.subItems = [];

			for(let i=0; i < addrCateList.length; i++) {
				addrCateInfo = addrCateList[i];
				if(targetGrpYn == 'Y' && addrCateInfo.parAddressCategoryId == 0) { // Address Category Group
					if(addrCateGrpId == addrCateInfo.addressCategoryGrpId) { // 주소그룹과 카테고리주소가 동일한 주소그룹이면
						target.subItems.push(addrCateInfo);
						vm.fnSetSubItems(addrCateList, target.subItems[target.subItems.length - 1], 'N');
					}
				} else { // Address Category
					if(addrCateId == addrCateInfo.parAddressCategoryId) { // child item 판단
						target.subItems.push(addrCateInfo);
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
					vm.listChkBox.push(memberInfo.cuInfoId + ',' + memberInfo.addressCategoryId);
				});
			} else {
				this.listChkBox = [];
			}
		},
		//주소 카테고리 클릭
		fnAddrCateMem(addrCateId, addrName, parDepthNumber, obj, isRoot) {
			//기존 선택건 초기화
			jQuery('#addrTreeMenu .active').removeClass('active');

			// 선택건 강조표시
			jQuery(obj).addClass('active');

			// 체크박스 초기화
			this.fnResetChkbox();

			// root 선택
			if(isRoot) {
				this.memberList = [];
				this.searchAddrCateId = -1;
				this.selectAddrName = addrName;
				this.isSelectRoot = true;
				this.selectDepthNumber = parDepthNumber;
				return;
			}

			this.isSelectRoot = false;
			this.searchAddrCateId = addrCateId;
			this.selectAddrName = addrName;
			this.selectDepthNumber = parDepthNumber;
			this.fnSearchMemberList();
		},
		// 주소 하위 카테고리 등록/수정
		fnSaveMemberPop(saveStatus) {
			if(this.searchAddrCateId == -1 && this.$gfnCommonUtils.isEmpty(this.selectAddrName) && !this.isSelectRoot) {
				confirm.fnAlert('', '주소 카테고리명을 선택해 주세요.');
				return false;
			}

			if(saveStatus == 'U' && this.isSelectRoot) {
				confirm.fnAlert('', '최상위 카테고리명은 수정할 수 없습니다.');
				return false;
			}

			if(saveStatus == 'I' && this.selectDepthNumber == 3) {
				confirm.fnAlert('', '3단계 이상의 하위 카테고리를 추가할 수 없습니다.');
				return false;
			}

			this.saveStatus = saveStatus;
			this.addrCateOpen = !this.addrCateOpen;
			jQuery('#AddrCategoryLayer').modal('show');
		},
		// 주소 카테고리 삭제
		fndelAddrCate() {
			if(this.isSelectRoot) {
				confirm.fnAlert('', '최상위 카테고리명은 삭제할 수 없습니다.');
				return false;
			}

			if(this.searchAddrCateId == -1) {
				confirm.fnAlert('', '주소 카테고리명을 선택해 주세요.');
				return false;
			}
			eventBus.$on('callbackEventBus', this.fndelAddrCateCallBack);
			confirm.fnConfirm('[' + this.selectAddrName + '] 카테고리 삭제', this.selectAddrName + ' 를 포함한 하위 카테고리 및 구성원이 삭제됩니다. \n삭제하시겠습니까?', '확인');
		},
		fndelAddrCateCallBack() {
			let params = {
				'regId': tokenSvc.getToken().principal.userId,
				'addressCategoryId': this.searchAddrCateId,
			};

			addressApi.deleteAddressCategory(params).then(response =>{
				var result = response.data;
				if(result.success) {
					confirm.fnAlert('카테고리 삭제','삭제에 성공했습니다.');
					// 삭제한 주소 카테고리 초기화
					this.memberList = [];
					this.searchAddrCateId = -1;
					this.selectAddrName = '';
					// 카테고리 재조회
					this.fnGetAddrList();
				} else {
					confirm.fnAlert('카테고리 삭제',result.message);
				}
			});
		},
		async fnSearchMemberList() {
			let params = {
				addressCategoryId: this.searchAddrCateId,
			};
			await addressApi.selectMemberList(params).then(response =>{
				var result = response.data;
				if(result.success) {
					this.memberList = Object.assign([], result.data);
				} else {
					confirm.fnAlert(this.title, result.message);
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
			jQuery("#layerPop").modal("show");
		},
		// 주소록 카테고리 그룹 수정
		fnModAddrPop() {
			if(this.$refs.selectAddrCateGrp.selectedIndex == 0) {
				confirm.fnAlert(this.title, '주소록을 선택해 주세요');
				return false;
			}
			this.addrModifyOpen = !this.addrModifyOpen;
			jQuery('#AddrModifyLayer').modal('show');
		},
		// 구성원 추가
		fnRegisterMemberPop() {
			if(this.searchAddrCateId == -1) {
				confirm.fnAlert('구성원 추가', '주소 카테고리명을 선택해 주세요.');
				return false;
			}
			this.memberRegisterOpen = !this.memberRegisterOpen;
			jQuery('#MemberRegisterLayer').modal('show');
		},
		// 구성원 삭제
		fnDeletemember() {
			if(this.listChkBox == null || this.listChkBox.length == 0) {
				confirm.fnAlert('구성원 삭제', '구성원을 선택해주세요.');
				return false;
			}

			eventBus.$on('callbackEventBus', this.fnDeletememberCallBack);
			confirm.fnConfirm('구성원 삭제', '삭제하시겠습니까?', '확인');
		},
		fnDeletememberCallBack() {
			let params = {
				//'addressCategoryId': this.searchAddrCateId,
				'memberList': this.listChkBox,
			};

			addressApi.deleteMember(params).then(response =>{
				var result = response.data;
				if(result.success) {
					confirm.fnAlert('구성원 삭제', '구성원을 삭제 했습니다.');
					this.fnSearchMemberList();
					this.fnResetChkbox();
				} else {
					confirm.fnAlert('구성원 삭제', result.message);
				}
			});
		},
		fnResetChkbox() {
			this.listAllChecked = false;
			this.listChkBox = [];
		},
		//주소록 삭제 확인
		fnDelAddrPopConfirm(){
			if( this.selectAddressCategoryGrpId === "" ){
				confirm.fnAlert('', '삭제할 주소록을 선택해주세요.');
				return;
			}
			eventBus.$on('callbackEventBus', this.fnDeleteAddr);
			confirm.fnConfirm('주소록 삭제', '해당 주소록을 삭제하시겠습니까?', '확인');
		},
		fnDeleteAddr(){
			let params = {
				'addressCategoryGrpId': this.selectAddressCategoryGrpId,
			};

			addressApi.deleteAddress(params).then(response =>{
				var result = response.data;
				if(result.success) {
					confirm.fnAlert('주소록 삭제', '주소록을 삭제 했습니다.');
					this.fnSearchAddressCateGrpList();
					this.selectAddressCategoryGrpId = "";
				} else {
					confirm.fnAlert('주소록 삭제', result.message);
				}
			});
		}
	}
}
</script>