<template>
	<div>
		<layerPopup :row_data="row_data" :projectId="projectId" />
		<article>
			<div class="row mt15">
				<div class="col-xs-12">
					<div class="menuBox">						
						<div class="of_h">
							<div>
								<h4 class="inline-block" style="width:6%">브랜드명</h4>
								<input type="text" class="inputStyle" style="width:16%" v-model="srcBrandName">
								<h4 class="inline-block ml60" style="width:6%">발신번호명</h4>
								<input type="text" class="inputStyle" style="width:16%" v-model="srcSubTitle">
								<h4 class="inline-block ml60" style="width:6%">발신번호</h4>
								<input type="text" class="inputStyle" style="width:16%" v-model="srcSubNum">
							</div>	
							<div class="consolMarginTop">
								<h4 class="inline-block vertical-middle" style="width:6%">SMSMO<br>사용여부</h4>
								<select name="admin0305_1" class="selectStyle2 vertical-middle" style="width:16%" v-model="srcRcsReply">
									<option value="">전체</option>
									<option value="">대기</option>
								</select>
								<h4 class="inline-block ml60" style="width:6%">사용여부</h4>
								<select name="admin0305_2" class="selectStyle2" style="width:16%" v-model="srcIsuse">
									<option value="">전체</option>
									<option value="">대기</option>
								</select>
								<h4 class="inline-block ml60" style="width:6%">승인상태</h4>
								<select name="admin0305_3" class="selectStyle2" style="width:16%" v-model="srcApprovalResult">
									<option value="">전체</option>
									<option value="">대기</option>
								</select>
								<a @click="fnSearch" class="btnStyle1 float-right">검색</a>
							</div>
						</div>						
					</div>
				</div>
			</div>

			
			<div class="row mt20">
				<div class="col-xs-12">		
					<div class="of_h">
						<div class="float-right">
							<a @click="fnMoveMainTab('callbackPreReg')" class="btnStyle3 gray font13 width180">발신번호 사전 등록</a>
						</div>
					</div>
				</div>			
			</div>
			
			<div class="row">
				<div class="col-xs-12">
					<!-- 15개씩 보기 -->
					<PagingCnt :pageInfo.sync="pageInfo" />
					<!-- //15개씩 보기 -->
					
					<!-- table -->
					<table class="table_skin1 bt-000 tbl-striped">
						<colgroup>
							<col style="width:5%">
							<col>
							<col style="width:18%">
							<col style="width:10%">
							<col style="width:9%">
							<col style="width:8%">
							<col style="width:13%">
							<col style="width:13%">
							<col style="width:10%">
						</colgroup>
						<thead>
							<tr>
								<th class="text-center lc-1">No.</th>
								<th class="text-center lc-1">브랜드명</th>
								<th class="text-center lc-1">발송번호 명</th>
								<th class="text-center lc-1">발신번호</th>
								<th class="text-center lc-1">SMSMO<br>사용여부</th>
								<th class="text-center lc-1">승인상태</th>
								<th class="text-center lc-1">승인요청일</th>
								<th class="text-center lc-1">승인완료일</th>
								<th class="text-center lc-1 end">관리</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(row, index) in data" :key="index">
								<td class="text-center">{{ index + 1 }}</td>
								<td class="text-center">{{row.brandName}}</td>
								<td class="text-center">{{row.subTitle}}</td>
								<td class="text-center">{{row.chatbotId}}</td>
								<td class="text-center">{{row.rcsReply}}</td>
								<td class="text-center">{{row.approvalStatus}}</td>
								<td class="text-center">{{row.regDt}}</td>
								<td class="text-center">{{row.approvalDt}}</td>
								<td class="end">
									<a @click="fnCallbackDetail(row)" class="btnStyle8 mr5">수정</a>
									<a @click="fnCallbackDeleteConfirm(row)" class="btnStyle8 mr5">삭제</a>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- //table -->
				</div>			
			</div>

			<!-- pagination -->
			<Paging :pageInfo.sync="pageInfo" />
			<!-- //pagination -->


			<!-- //본문 -->

			<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
		</article>
	</div>
</template>


<script>
import layerPopup from "./bp-project-callback-detail.vue";
import projectApi from '../service/projectApi'
import tokenSvc from '@/common/token-service';


import Paging from "@/modules/commonUtil/components/bc-paging"
import PagingCnt from "@/modules/commonUtil/components/bc-pagingCnt"

import {eventBus} from "@/modules/commonUtil/service/eventBus";
import confirm from "@/modules/commonUtil/service/confirm"

export default {
	components: {
		layerPopup,
		Paging,
		PagingCnt
	},
	data() {
		return {
			// 프로젝트 정보
			projectId : '',
			projectName : '',

			// 검색 조건
			srcBrandName	: "",
			srcSubTitle	: "",
			srcSubNum		: "",
			srcRcsReply	: "",
			srcIsuse		: "",
			srcApprovalResult	: "",

			// 리스트 
			data : {},
			pageInfo: {},
			row_data : {}
		}
	},
	mounted() {
		this.projectId = this.$route.params.projectId;
		this.projectName = this.$route.params.projectName;

		this.pageInfo = {
			"pageCnt"   : [10, 30, 50],  //표시할 개수 리스트
			"selPageCnt": 10,          //선택한 표시 개수
			"selPage"   : 1,          //선택한 페이지
			"rowNum"    : 1           //총개수
		};

		this.fnSearch();
	},
	methods: {
		fnMoveMainTab(moveTabName){
			this.$router.push( {name:moveTabName, params:{"projectId" : this.projectId, "projectName" : this.projectName }} );
		},
		// 검색
		fnSearch() {
			var params = {
				"projectId"	: this.projectId,
				"pageInfo"	: this.pageInfo
			}
			
			projectApi.selectCallbackManageList(params).then(response =>{
				var result = response.data;
				if(result.success) {
					this.data = result.data; 
					this.pageInfo = result.pageInfo;
				}
			});
		},
		fnCallbackDetail(row_data){
			this.row_data = row_data;
			console.log(row_data);
			console.log("fnCallbackDetail");
			jQuery("#detailPop").modal("show");
		},
		fnCallbackDeleteConfirm(row_data){
			this.row_data = row_data;
			eventBus.$on('callbackEventBus', this.fnCallbackDelete);
			confirm.fnConfirm("", "삭제처리된 발신번호는 복구할 수 없습니다. 계속 진행하시겠습니까?", "확인");
		},
		fnCallbackDelete(){
			var params = {
				"brandId"		: this.row_data.brandId,
				"chatbotId"		: this.row_data.chatbotId,
				"corpId"		: this.row_data.corpId,
			};

			console.log(params);

			projectApi.deleteCallbackForApi(params).then(response =>{
				var result = response.data.data;
				console.log(result);
			});
		}
	}
}
</script>