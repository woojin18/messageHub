<template>
	<div id="content">
		<article>
			<div class="contentHeader">
				<h2>사용자 목록</h2>
			</div>

			<!-- 검색창 Start -->
			<div class="row">
				<div class="col-xs-12">
					<div class="menuBox">
						<div class="of_h">
							<h4 class="inline-block" style="width:6%" v-focus @keyup.enter="fnSearch">사용자명</h4>
							<input id="userName" name="userName" v-model="searchData.userName" type="text" class="inputStyle" style="width:14%" title="사용자명 입력란">
							<h4 class="inline-block ml30" style="width:5%" v-focus @keyup.enter="fnSearch">아이디</h4>
							<input type="text" v-model="searchData.loginId" class="inputStyle" style="width:14%" title="아이디 입력란">
							<h4 class="inline-block ml30" style="width:3%">상태</h4>
							<select @fnSelected="fnSelected" v-model="searchData.approvalStatus" class="selectStyle2" style="width:14%" title="상태 선택란">
								<option value="">전체</option>
								<option value="ING">초대중</option>
								<option value="Y">정상</option>
								<option value="N">이용정지</option>
							</select>
							<a @click="fnSearch()" class="btnStyle1 float-right" title="검색">검색</a>
						</div>
					</div>
				</div>
			</div>
			<!-- 검색창 End -->

			<!-- 리스트 Start -->
			<div class="row">
				<div class="col-xs-12 consolMarginTop">
					<div class="of_h inline">
						<div class="float-right">
							<a @click="fnRegisterUserPop()" class="btnStyle2 borderGray" title="사용자 등록창이 열립니다">사용자 등록</a>
							<!--a href="#self" class="btnStyle3 gray font13 minWidth120 ml10" data-toggle="modal" data-target="#Modify" title="정보 수정창이 열립니다">정보 수정</a-->
						</div>
					</div>

					<!-- 페이지당 리스트 개수 -->
					<div class="of_h inline">
						<div class="float-left">전체 : <span class="color1"><strong>{{totCnt}}</strong></span>건
							<SelectLayer @fnSelected="fnSelected"></SelectLayer>
						</div>
					</div>

					<div class="row">
						<div class="col-xs-12 consolMarginTop">
							<table class="table_skin1 tbl-striped" style="width:100%">
								<caption>사용자 목록의 No, 사용자명, 이용 프로젝트, 아이디, 이용권한, 상태, 관리를 제공하는 표</caption>
								<colgroup>
									<col style="width:5%">
									<col style="width:10%">
									<col style="width:20%">
									<col style="width:15%">
									<col style="width:10%">
									<col style="width:10%">
									<col>
								</colgroup>
								<thead>
									<tr>
										<th class="text-center lc-1">No.</th>
										<th class="text-center lc-1">사용자명</th>
										<th class="text-center lc-1">이용 프로젝트</th>
										<th class="text-center lc-1">아이디</th>
										<th class="text-center lc-1">이용권한</th>
										<th class="text-center lc-1">상태</th>
										<th class="text-center lc-1 end">관리</th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="(data, index) in items" :key="index">
										<td class="text-center vertical-middle">{{ index + 1 }}</td>
										<td class="text-center vertical-middle">{{ data.userName }}</td>
										<td class="text-center vertical-middle">
											<div v-html="data.projectName" />
										</td>
										<td class="text-center lc-1 vertical-middle">{{ data.loginId }}</td>
										<td class="text-center lc-1 vertical-middle">{{ data.roleName }}</td>
										<td class="text-center lc-1 vertical-middle">{{ data.approvalStatusName }}</td>
										<td class="text-center end vertical-middle">
											<a href="#self" class="btnStyle1 borderLightGray small mr5" title="비밀번호설정 메일보내기">비밀번호설정 메일보내기</a>
											<span v-if="data.approvalStatus == 'Y' ">
												<a @click="fnStopUserPop(index)" class="btnStyle1 borderLightGray small mr5" title="이용정지">이용정지</a>
											</span>
											<span v-else-if="data.approvalStatus == 'N' ">
												<a @click="fnReleaseUserPop(index)" class="btnStyle1 borderLightGray small mr5" title="이용정지 해제">이용정지 해제</a>
											</span>
											<a @click="fnModifyUserPop(index)" class="btnStyle1 borderLightGray small mr5" title="수정">수정</a>
											<a @click="fnDeleteUserPop(index)" class="btnStyle1 borderLightGray small mr5" title="삭제">삭제</a>
										</td>
									</tr>
									<tr v-if="items.length == 0">
										<td class="text-center"></td>
										<td class="text-center" colspan="6">검색된 내용이 없습니다.</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<!-- 리스트 End -->

			<!-- pagination Start -->
			<div id="pageContent">
				<PageLayer @fnClick="fnSearch" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
			</div>
			<!-- pagination End-->

			<!-- 본문 End -->
			<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
		</article>

		<!-- stop Modal -->
		<StopLayer :title="stopLayerTitle" :layerView.sync="stopLayerView" :stopUserId="stopLayerUserId"></StopLayer>

		<!-- Release Modal -->
		<ReleaseLayer :title="releaseLayerTitle" :layerView.sync="releaseLayerView" :releaseUserId="releaseLayerUserId"></ReleaseLayer>

		<!-- delete Modal -->
		<DeleteLayer :title="deleteLayerTitle" :layerView.sync="deleteLayerView" :deleteUserId="deleteLayerUserId"></DeleteLayer>

		<!-- modify Modal -->
		<ModifyLayer :title="modifyLayerTitle" :layerView.sync="modifyLayerView" :modifyUserId="modifyLayerUserId" :modifyUserName="modifyLayerUserName" :modifyHpNumber="modifyLayerHpNumber" :modifyRoleCd="modifyLayerRoleCd" :modifyLoginId="modifyLayerLoginId"></ModifyLayer>

		<!-- register Modal -->
		<RegisterLayer :title="registerLayerTitle" :layerView.sync="registerLayerView"></RegisterLayer>
	</div>
	<!-- content End-->
</template>


<script>
import userApi from '../service/userApi'
import PageLayer from '@/components/PageLayer.vue';
import SelectLayer from '@/components/SelectLayer.vue';
import StopLayer from '../components/bp-user-manage-stop.vue';
import ReleaseLayer from '../components/bp-user-manage-release.vue';
import DeleteLayer from '../components/bp-user-manage-delete.vue';
import RegisterLayer from '../components/bp-user-manage-register.vue';
import ModifyLayer from '../components/bp-user-manage-modify.vue';
import tokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm";

export default {
	name: "userManageList",
	components: {
		SelectLayer,
		PageLayer,
		StopLayer,
		ReleaseLayer,
		DeleteLayer,
		RegisterLayer,
		ModifyLayer
	},
	props: {
		searchData : {
			type: Object,
			require: false,
			default: function() {
				return {
					'userName'			: '',
					'userId'			: '',
					'approvalStatus'	: '',
					'corpId'			: tokenSvc.getToken().principal.corpId,
					'loginId'			: ''
				}
			}
		}
	},
	data() {
		return {
			listSize : 10,	// select 박스 value (출력 갯수 이벤트)
			pageNo : 1,		// 현재 페이징 위치
			totCnt : 0,		//전체 리스트 수
			offset : 0,		 //페이지 시작점
			items: [],
			// Popup
			stopLayerView: false,
			stopLayerTitle: "이용정지",
			stopLayerUserId: "",

			releaseLayerView: false,
			releaseLayerTitle: "이용정지 해제",
			releaseLayerUserId: "",

			deleteLayerView: false,
			deleteLayerTitle: "삭제",
			deleteLayerUserId: "",

			modifyLayerView: false,
			modifyLayerTitle: "사용자정보 수정",
			modifyLayerUserId: "",
			modifyLayerUserName: "",
			modifyLayerHpNumber: "",
			modifyLayerRoleCd: "",
			modifyLayerLoginId: "",

			registerLayerView: false,
			registerLayerTitle: "사용자 등록",
			registerLayerUserId: ""
		}
	},
	mounted() {
		//console.log('token:', tokenSvc.getToken());
		this.fnSearch();
		
	},
	methods: {
		fnSearch(pageNum) {
			this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNum, '1'))*1;
			this.fnSelectUserList();
		},
		// select 박스 선택시 리스트 재출력
		fnSelected(selected) {
			this.selected = selected;
			this.$refs.updatePaging.fnAllDecrease();
		},
		//사용자 리스트 검색
		async fnSelectUserList(){
			var params = Object.assign({}, this.searchData);
			params.pageNo = this.pageNo;
			params.listSize = this.listSize;
			await userApi.selectUserList(params).then(response =>{
				var result = response.data;
				if(result.success) {

					//프로젝트명이 n개일때 개행처리
					for (var i in result.data){
						if(result.data[i].projectName != null) {
							result.data[i].projectName = result.data[i].projectName.replaceAll('||', '<br />');
						}
					}

					this.items = result.data;
					this.totCnt = result.pageInfo.totCnt;
					this.offset = result.pageInfo.offset;
				} else {
					confirm.fnAlert("", result.message);
				}
			});
		},
		//이용정지
		fnStopUserPop(index) {
			console.log(">>> start fnStopUserPop. index : " + index);
			this.stopLayerView = true;
			this.stopLayerTitle = "UserStop";
			this.stopLayerUserId = this.items[index].userId;
			console.log(">>> ended fnStopUserPop");
		},
		//이용정지 해제
		fnReleaseUserPop(index) {
			console.log(">>> start fnReleaseUserPop. index : " + index);
			this.releaseLayerView = true;
			this.releaseLayerTitle = "UserRelease";
			this.releaseLayerUserId = this.items[index].userId;
			console.log(">>> ended fnReleaseUserPop");
		},
		// 삭제
		fnDeleteUserPop(index) {
			console.log(">>> start fnDeleteUserPop. index : " + index);
			this.deleteLayerView = true;
			this.deleteLayerTitle = "UserDelete";
			this.deleteLayerUserId = this.items[index].userId;
			console.log(">>> ended fnDeleteUserPop");
		},
		 // 사용자정보 수정
		fnModifyUserPop(index) {
			console.log(">>> start fnModifyUserPop. index : " + index);
			this.modifyLayerView = true;
			this.modifyLayerTitle = "UserModify";
			this.modifyLayerUserId = this.items[index].userId;
			this.modifyLayerUserName = this.items[index].userName;
			this.modifyLayerHpNumber = this.items[index].hpNumber;
			this.modifyLayerRoleCd = this.items[index].roleCd;
			this.modifyLayerLoginId = this.items[index].loginId
			console.log(">>> ended fnModifyUserPop");
		},
		// 사용자 등록
		fnRegisterUserPop() {
			console.log(">>> start fnRegisterUserPop.");
			this.registerLayerView = true;
			this.registerLayerTitle = "UserRegister";
			console.log(">>> ended fnRegisterUserPop");
		}
	}
}
</script>