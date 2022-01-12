<template>
	<div>
		<article>
			<div class="clear">
				<h4 class="inline-block">발신번호 조회</h4>	
			</div>

			<div class="row">
				<div class="col-xs-12">
					<div class="menuBox">						
						<p class="colo4"><i class="far fa-info-circle vertical-baseline"></i> [RCS 발신번호 연결] 버튼을 이용하여 다른 프로젝트에서 등록한 RCS 발신번호를 연결 할 수 있습니다.</p>
						<p class="colo4"><i class="far fa-info-circle vertical-baseline"></i> [문자 발신번호 연결] 버튼을 이용하여 다른 프로젝트에서 등록한 문자 발신번호를 연결 할 수 있습니다.</p>
						<p class="colo4"><i class="far fa-info-circle vertical-baseline"></i> [연결해제]버튼은 현재 프로젝트와 발신번호를 연결해제 합니다. (RCS 발신번호 연결해제는 문자 발신번호도 연결해제 됩니다.)</p>
					</div>
				</div>
			</div>

			<div class="row mt15">
				<div class="col-xs-12">
					<div class="menuBox">						
						<div class="of_h">
							<div>
								<h4 class="inline-block" style="width:6%">발신번호</h4>
								<input type="text" class="inputStyle" style="width:16%" v-model="srcCallNum">
								<h4 class="inline-block ml60" style="width:6%">RCS 상태</h4>
								<select class="selectStyle2" style="width:16%" v-model="srcRcsState" @change="fnSearch(1)">
									<option value="">전체</option>
									<option value="승인대기">승인대기</option>
									<option value="승인">승인</option>
									<option value="반려">반려</option>
								</select>
								<h4 class="inline-block ml60" style="width:6%">문자상태</h4>
								<select class="selectStyle2" style="width:16%" v-model="srcSmsState" @change="fnSearch(1)">
									<option value="">전체</option>
									<option value="10">요청</option>
									<option value="20">접수</option>
									<option value="80">승인</option>
									<option value="90">반려</option>
									<option value="91">차단</option>
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
							<a @click="fnRcsPop" class="btnStyle2 borderGray">RCS 발신번호 연결</a>
							<a @click="fnSmsPop" class="btnStyle2 borderGray ml10">문자 발신번호 연결</a>
						</div>
					</div>
				</div>			
			</div>
			
			<div class="row">
				<div class="col-xs-12">
					<!-- table -->
					<table class="table_skin1 bt-000 tbl-striped mt10">
						<colgroup>
							<col style="width:8%">
							<col style="width:5%">
							<col style="width:7%">
							<col style="width:5%">
							<col style="width:7%">
							<col style="width:7%">
							<col>
							<col>
							<col style="width:7%">
							<col style="width:7%">
							<col style="width:7%">
						</colgroup>
						<thead>
							<tr>
							<th class="text-center lc-1">발신번호</th>
							<th class="text-center lc-1">RCS 발신</th>
							<th class="text-center lc-1">RCS 상태</th>
							<th class="text-center lc-1">문자 발신</th>
							<th class="text-center lc-1">문자 상태</th>
							<th class="text-center lc-1">등록 방법</th>
							<th class="text-center lc-1">브랜드</th>
							<th class="text-center lc-1">연결 프로젝트</th>
							<th class="text-center lc-1">요청일</th>
							<th class="text-center lc-1">승인일</th>
							<th class="text-center lc-1 end">관리</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(row, index) in data" :key="index">
								<td class="text-center">{{row.callNum}}</td>
								<td class="text-center">{{row.rcsSend}}</td>
								<td v-if="row.rcsState != '반려'" class="text-center">{{row.rcsState}}</td>
								<td v-if="row.rcsState == '반려'" class="text-center"><a @click="fnStop2(index)" class="color1">{{row.rcsState}}</a></td>
								<td class="text-center">{{row.smsSend}}</td>
								<td v-if="!(row.smsState == '90' || row.smsState == '91')" class="text-center">{{row.smsStateNm}}</td>
								<td v-if="row.smsState == '90' || row.smsState == '91'" class="text-center"><a @click="fnStop(index)" class="color1">{{row.smsStateNm}}</a></td>
								<td class="text-center">{{row.regWay}}</td>
								<td class="text-center">{{row.brand}}</td>
								<td v-if="row.project.indexOf('\n') < 0" class="text-center">{{row.project}}</td>
								<td v-if="row.project.indexOf('\n') > 0" class="text-center"><a @click="fnProject(index)" style="text-decoration:underline">{{row.project.split('\n')[0]}} 외 {{row.project.split('\n').length-1}}건</a></td>
								<td class="text-center">{{row.reqDt}}</td>
								<td class="text-center">{{row.handleDt}}</td>
								<td v-if="row.smsState == '' || row.smsState == '10' || row.smsState == '20'" class="end"></td>
								<td v-if="!(row.smsState == '' || row.smsState == '10' || row.smsState == '20')" class="end"><a @click="fnClearConfirm(index)" class="btnStyle1 borderLightGray small">연결해제</a></td>
							</tr>
							<tr v-if="data.length == 0"> 
								<td class="text-center" colspan="10">검색된 내용이 없습니다.</td>
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
		</article>
		<!-- rcs register Modal -->
		<rcsPop :srcProjectId="projectId" :rcsOpen="rcsOpen"></rcsPop>
		<smsPop :smsOpen="smsOpen"></smsPop>
	</div>
</template>


<script>
import projectApi from '../service/projectApi';

import SelectLayer from '@/components/SelectLayer.vue';
import PageLayer from '@/components/PageLayer.vue';

import {eventBus} from "@/modules/commonUtil/service/eventBus";
import confirm from "@/modules/commonUtil/service/confirm";

import rcsPop from '../components/bp-project-callnum-rcs.vue';
import smsPop from '../components/bp-project-callnum-sms.vue';

export default {
	components: {
		PageLayer,
		SelectLayer,
		rcsPop,
		smsPop
	},
	data() {
		return {
			// 프로젝트 정보
			projectId : '',
			projectName : '',

			// 검색 조건
			srcCallNum	: "",
			srcRcsState	: "",
			srcSmsState	: "",

			// 리스트 
			data : {},
			pageInfo: {},
			row_data : {},
			listSize : 10,  // select 박스 value (출력 갯수 이벤트)
			pageNo : 1,  // 현재 페이징 위치
			totCnt : 0,  //전체 리스트 수
			offset : 0, //페이지 시작점
			detailCnt : 0,
			rcsOpen: false,
			smsOpen: false,
			idx : 0
		}
	},
	mounted() {
		this.projectId = this.$route.params.projectId;
		this.projectName = this.$route.params.projectName;

		this.fnSearch(1);
	},
	methods: {
		// select 박스 선택시 리스트 재출력
		fnSelected(listSize) {
			this.listSize = Number(listSize);
			this.$refs.updatePaging.fnAllDecrease();
		},
		// 검색
		fnSearch(pageNo) {
			var params = {
				"projectId"		: this.projectId,
				"srcCallNum"	: this.srcCallNum,
				"srcRcsState"	: this.srcRcsState,
				"srcSmsState"	: this.srcSmsState,
				"pageNo"		: (this.$gfnCommonUtils.defaultIfEmpty(pageNo, '1'))*1,
				"listSize"		: this.listSize
			}
			
			projectApi.getCallNumList(params).then(response =>{
				var result = response.data;
				
				if(result.success) {
					this.data = result.data; 
					this.totCnt = result.pageInfo.totCnt;
					this.offset = result.pageInfo.offset;
				}
			});
		},
		// RCS 추가
		fnRcsPop() {
			this.rcsOpen = !this.rcsOpen;
			jQuery('#rcsPop').modal('show');
		},
		// SMS 추가
		fnSmsPop() {
			this.smsOpen = !this.smsOpen;
			jQuery('#smsPop').modal('show');
		},
		fnCallbackDetail(row_data){
			this.detailCnt = this.detailCnt + 1;
			this.row_data = row_data;
			jQuery("#detailPop").modal("show");
		},
		fnProject(idx) {
			confirm.fnAlert("", this.data[idx].project);
		},
		fnStop(idx) {
			confirm.fnAlert("", this.data[idx].handleReason);
		},
		fnStop2(idx) {
			confirm.fnAlert("", this.data[idx].approvalReason == 'null' ? '' : this.data[idx].approvalReason);
		},
		fnClearConfirm(idx){
			this.idx = idx;
			eventBus.$on('callbackEventBus', this.fnClear);
			if (this.data[this.idx].project.indexOf('\n') > 0) {
				confirm.fnConfirm("", "프로젝트와의 연결을 해제합니다.\n해제 후 다시 연결이 가능합니다.\n해제하시겠습니까?", "확인");
			} else {
				confirm.fnConfirm("", "프로젝트와 마지막으로 연결된 발신번호라서 삭제 처리됩니다.\n삭제 후 다시 연결이 불가능합니다.\n연결해제 하시겠습니까?", "확인");
			}
		},
		fnClear() {
			var params = {
				'projectId': this.projectId,
				'callNum'  : this.data[this.idx].callNum,
				'chatbotId'  : this.data[this.idx].chatbotId
			};

			projectApi.delCallNum(params).then(response =>{
				var result = response.data;
				if( result.success ){
					confirm.fnAlert("", "연결해제 처리되었습니다.");
					this.fnSearch(1)
				} else {
					confirm.fnAlert("", response.data.message);
				}
			});
		}
	}
}
</script>