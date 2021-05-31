<template>
	<!-- content -->
	<div id="content">
		<article>
			<div class="row mt30">
				<div class="col-xs-12">
					<div class="menuBox">
						<div class="of_h">
							<h4 class="inline-block" style="width:6%">사용자명</h4>
							<input type="text" v-model="searchData.userName" class="inputStyle" style="width:14%" title="사용자명 입력란">
							<h4 class="inline-block ml30" style="width:5%">아이디</h4>
							<input type="text" v-model="searchData.loginId" class="inputStyle" style="width:14%" title="아이디 입력란">
							<h4 class="inline-block ml30" style="width:6%">이용권한</h4>
							<select v-model="searchData.roleCd" class="selectStyle2" style="width:14%" title="이용권한 선택란">
								<option value="">전체</option>
								<option value="OWNER">Owner</option>
								<option value="ADMIN">Admin</option>
								<option value="USER">User</option>
							</select>
							<h4 class="inline-block ml30" style="width:3%">상태</h4>
							<select id="selectApprovalStatus" class="selectStyle2" style="width:14%" title="상태 선택란">
								<option value="">전체</option>
							</select>
							<a @click="fnSearch()" class="btnStyle1 float-right" title="검색">검색</a>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 consolMarginTop">
					<div class="of_h inline">
						<div v-if="role == 'ADMIN' || role == 'OWNER' " class="float-right">
							<a @click="fnRegisterMemberPop" class="btnStyle2 borderGray" title="멤버추가 창이 열립니다">멤버추가</a>
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
									<th class="text-center lc-1">아이디</th>
									<th class="text-center lc-1">이용권한</th>
									<th class="text-center lc-1">상태</th>
									<th class="text-center lc-1 end">관리</th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="(data, index) in memberList" :key="index">
										<td class="text-center vertical-middle">{{ data.rownum + offset }}</td>
										<td class="text-center vertical-middle">{{ data.userName }}</td>
										<td class="text-center lc-1 vertical-middle">{{ data.loginId }}</td>
										<td class="text-center lc-1 vertical-middle">{{ data.roleCd}}</td>
										<td class="text-center lc-1 vertical-middle">{{ data.approvalStatusName }}</td>
										<td class="text-center end vertical-middle">
											<a @click="fnDeleteMemberPop(data)" class="btnStyle1 borderLightGray small mr5" title="삭제">삭제</a>
										</td>
									</tr>
									<!--
									<tr>
										<td class="text-center vertical-middle">4</td>
										<td class="text-center vertical-middle">윤상훈</td>
										<td class="text-center lc-1 vertical-middle">designhon@gmail.com</td>
										<td class="text-center lc-1 vertical-middle">OWNER</td>
										<td class="text-center lc-1 vertical-middle">정상</td>
										<td class="text-center end vertical-middle">
											<a href="#self" class="btnStyle1 borderLightGray small mr5" data-toggle="modal" data-target="#delWrap" title="삭제">삭제</a>
										</td>
									</tr>
									-->
									<tr v-if="memberList.length == 0">
										<td class="text-center"></td>
										<td class="text-center" colspan="7">검색된 내용이 없습니다.</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<!-- pagination Start -->
			<div id="pageContent">
				<PageLayer @fnClick="fnPageClick" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
			</div>
			<!-- pagination End-->
			<!--
			<footer>CopyrightⓒLG Plus Corp. All Rights Reserved.</footer>
			-->
		</article>

		<!-- Member register Modal -->
		<regMembmerPop :memberRegisterOpen="memberRegisterOpen"></regMembmerPop>

		<!-- Member delete Modal -->
		<delMemberPop :memberDeleteOpen="memberDeleteOpen" :delMember="delMember"></delMemberPop>
	</div>
</template>

<script>
import PageLayer from '@/components/PageLayer.vue';
import memberApi from '../service/memberApi.js'
import SelectLayer from '@/components/SelectLayer.vue';
import tokenSvc from '@/common/token-service';
import commonUtilApi from "@/modules/commonUtil/service/commonUtilApi.js";
import regMembmerPop from '../components/bc-project-member-register.vue';
import delMemberPop from '../components/bc-project-member-delete.vue';
import confirm from "@/modules/commonUtil/service/confirm";

export default {
	name: 'MemberLayer',
	components: {
		SelectLayer,
		PageLayer,
		regMembmerPop,
		delMemberPop,
	},
	props: {
		searchData : {
			type: Object,
			require: false,
			default: function() {
				return {
					'userName'			: '',
					'approvalStatus'	: '',
					'corpId'			: tokenSvc.getToken().principal.corpId,
					'roleCd'			: '',
					'loginId'			: ''
				}
			}
		}
	},

data() {
		return {
			memberList: [],
			listSize : 10,	// select 박스 value (출력 갯수 이벤트)
			pageNo : 1,		// 현재 페이징 위치
			totCnt : 0,		// 전체 리스트 수
			offset : 0,		// 페이지 시작점
			memberRegisterOpen: false,
			memberDeleteOpen: false,
			projectId : this.$parent.projectId,
			delMember : {},
			role : tokenSvc.getToken().principal.role,
		}
	},
	mounted() {
		this.fnStatusInit();
		this.fnSearch();
	},
	watch: {
	},
	methods: {
		fnSearch(pageNum) {
			this.$refs.updatePaging.fnAllDecrease();
			this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNum, '1'))*1;
			this.fnSearchMemberList();
		},
		//멤버 조회
		async fnSearchMemberList() {
			let params = Object.assign({}, this.searchData);
			params.pageNo = this.pageNo;
			params.listSize = this.listSize;
			params.projectId = this.$parent.projectId;
			params.corpId = tokenSvc.getToken().principal.corpId;

			await memberApi.selectProjectMemberList(params).then(response =>{
				const result = response.data;

				if(result.success) {
					this.memberList = Object.assign([], result.data);
					this.totCnt = result.pageInfo.totCnt;
					this.offset = result.pageInfo.offset;
				} else {
					confirm.fnAlert('', result.message);
				}
			});
		},
		// select 박스 선택시 리스트 재출력
		fnSelected(listSize) {
			this.listSize = Number(listSize);
			this.$refs.updatePaging.fnAllDecrease();
		},
		// 삭제
		// fnDeleteUserPop(index) {
		// 	// this.deleteLayerView = true;
		// 	// this.deleteLayerTitle = "UserDelete";
		// 	// this.deleteLayerUserId = this.items[index].userId;
		// },
		fnStatusInit() {
			var params = {
				codeTypeCd	: "APPROVAL_STATUS",
				useYN		: "Y"
			};
			commonUtilApi.selectCodeList(params).then(response =>{
				var result = response.data.data;
				for(var i = 0; i < result.length; i++){
					jQuery("#selectApprovalStatus").append('<option value="'+result[i].codeVal1+'">'+result[i].codeName1+'</option>');
				}
			});
		},
		fnPageClick(pageNo) {
			this.pageNo = pageNo;
			this.fnSearchMemberList();
		},
		// 멤버 추가
		fnRegisterMemberPop() {
			this.memberRegisterOpen = !this.memberRegisterOpen;
			jQuery("#regMembmerPop").modal("show");
		},
		// 멤버 삭제
		fnDeleteMemberPop(delMember) {
			this.delMember = delMember
			this.memberDeleteOpen = !this.memberDeleteOpen;
			jQuery("#delMemberPop").modal("show");
		},
	}
}
</script>