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
								<input type="text" class="inputStyle" style="width:16%" v-model="srcBrandName" @keypress.enter="fnSearch(1)">
								<h4 class="inline-block ml60" style="width:6%">발신번호명</h4>
								<input type="text" class="inputStyle" style="width:16%" v-model="srcSubTitle" @keypress.enter="fnSearch(1)">
								<h4 class="inline-block ml60" style="width:6%">발신번호</h4>
								<input type="text" class="inputStyle" style="width:16%" v-model="srcChatbotId" @keypress.enter="fnSearch(1)">
							</div>	
							<div class="consolMarginTop">
								<h4 class="inline-block vertical-middle" style="width:6%">SMSMO<br>사용여부</h4>
								<select name="admin0305_1" class="selectStyle2 vertical-middle" style="width:16%" v-model="srcRcsReply" @change="fnSearch">
									<option value="">전체</option>
									<option value="0">Y</option>
									<option value="1">N</option>
								</select>
								<h4 class="inline-block ml60" style="width:6%">사용여부</h4>
								<select name="admin0305_2" class="selectStyle2" style="width:16%" v-model="srcIsuse" @change="fnSearch">
									<option value="">전체</option>
									<option value="Y" selected="selected">사용</option>
									<option value="N">미사용</option>
								</select>
								<h4 class="inline-block ml60" style="width:6%">승인상태</h4>
								<select name="admin0305_3" class="selectStyle2" style="width:16%" v-model="srcApprovalStatus" @change="fnSearch">
									<option value="">전체</option>
									<option value="승인대기">승인대기</option>
									<option value="승인" selected="selected">승인</option>
									<option value="반려">반려</option>
								</select>
								<a @click="fnSearch(1)" class="btnStyle1 float-right">검색</a>
							</div>
						</div>						
					</div>
				</div>
			</div>

			
			<div class="row mt20">
				<div class="col-xs-12">		
					<div class="of_h">
						<!-- 15개씩 보기 -->
						<div class="of_h inline">
							<div class="float-left">전체 : <span class="color1"><strong>{{totCnt}}</strong></span>건
								<SelectLayer @fnSelected="fnSelected" classProps="selectStyle2 width120 ml20"></SelectLayer>
							</div>
						</div>
						<!-- //15개씩 보기 -->
						
						<div class="float-right">
							<a @click="fnMoveMainTab('callbackPreReg')" class="btnStyle3 gray font13 width180">발신번호 사전 등록</a>
						</div>
					</div>
				</div>			
			</div>
			
			<div class="row">
				<div class="col-xs-12">
					<!-- table -->
					<table class="table_skin1 bt-000 tbl-striped mt10">
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
								<td class="text-center">{{totCnt-offset-row.rownum+1}}</td>
								<td class="text-center">{{row.brandName}}</td>
								<td class="text-center">{{row.subTitle}}</td>
								<td class="text-center">{{row.chatbotId}}</td>
								<td class="text-center">{{row.rcsReplyText}}</td>
								<td class="text-center">{{row.approvalStatus}}</td>
								<td class="text-center">{{row.regDt}}</td>
								<td class="text-center">{{row.approvalDt}}</td>
								<td class="end">
									<a v-if="row.approvalStatus === '승인' || row.approvalStatus === '반려(수정)'" @click="fnCallbackDetail(row)" class="btnStyle8 mr5">수정</a>
									<a v-if="row.approvalStatus === '승인' || row.approvalStatus === '반려(수정)'" @click="fnCallbackDeleteConfirm(row)" class="btnStyle8 mr5">삭제</a>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- //table -->
				</div>			
			</div>

			<!-- pagination -->
			<div id="pageContent">
				<PageLayer @fnClick="fnSearch" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
			</div>
			<!-- //pagination -->


			<!-- //본문 -->

			<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
		</article>
	</div>
</template>


<script>
import layerPopup from "./bp-project-callback-detail.vue";
import projectApi from '../service/projectApi';

import SelectLayer from '@/components/SelectLayer.vue';
import PageLayer from '@/components/PageLayer.vue';

import {eventBus} from "@/modules/commonUtil/service/eventBus";
import confirm from "@/modules/commonUtil/service/confirm";

export default {
	components: {
		layerPopup,
		PageLayer,
		SelectLayer
	},
	data() {
		return {
			// 프로젝트 정보
			projectId : '',
			projectName : '',

			// 검색 조건
			srcBrandName	: "",
			srcSubTitle	: "",
			srcChatbotId		: "",
			srcRcsReply	: "",
			srcIsuse		: "",
			srcApprovalStatus	: "",

			// 리스트 
			data : {},
			pageInfo: {},
			row_data : {},
			listSize : 10,  // select 박스 value (출력 갯수 이벤트)
			pageNo : 1,  // 현재 페이징 위치
			totCnt : 0,  //전체 리스트 수
			offset : 0, //페이지 시작점
		}
	},
	mounted() {
		this.projectId = this.$route.params.projectId;
		this.projectName = this.$route.params.projectName;

		this.fnSearch(1);
	},
	methods: {
		fnMoveMainTab(moveTabName){
			this.$router.push( {name:moveTabName, params:{"projectId" : this.projectId, "projectName" : this.projectName }} );
		},
		// select 박스 선택시 리스트 재출력
		fnSelected(listSize) {
			this.listSize = Number(listSize);
			this.$refs.updatePaging.fnAllDecrease();
		},
		// 검색
		fnSearch(pageNo) {
			var params = {
				"projectId"		: this.projectId,
				"srcBrandName"	: this.srcBrandName,
				"srcSubTitle"	: this.srcSubTitle,
				"srcChatbotId"	: this.srcChatbotId,
				"srcRcsReply"	: this.srcRcsReply,
				"srcIsuse"		: this.srcIsuse,
				"srcApprovalStatus"	: this.srcApprovalStatus,
				"pageNo"		: (this.$gfnCommonUtils.defaultIfEmpty(pageNo, '1'))*1,
				"listSize"		: this.listSize
			}
			
			projectApi.selectCallbackManageList(params).then(response =>{
				var result = response.data;
				
				if(result.success) {
					this.data = result.data; 
					this.totCnt = result.pageInfo.totCnt;
          			this.offset = result.pageInfo.offset;
				}
			});
		},
		fnCallbackDetail(row_data){
			this.row_data = row_data;
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
				"saveCorpId"	: this.row_data.corpId,
			};

			projectApi.deleteCallbackForApi(params).then(response =>{
				var result = response.data;
				if( result.success ){
					confirm.fnAlert("", "삭제처리되었습니다.");
				} else {
					confirm.fnAlert("", response.data.message);
				}
			});
		}
	}
}
</script>