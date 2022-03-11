<template>
	<div>
		<layerPopup :save_status="save_status" :row_data="row_data" :projectId="projectId" />
		<article>
			<h4 class="mt40">MO 수신번호 현황</h4>
			<p class="color3">
				<i class="far fa-info-circle"></i> MO 서비스는 휴대폰에서 PC나 서버로 메시지를 받을 수 있는 서비스입니다. <br>
				<i class="far fa-info-circle"></i> MO 서비스를 도입하면 휴대폰 사용자가 특정 번호가 부여된 서버나 PC로 메시지를 보낼 수 있습니다. <br>
			</p>
			<div class="row mt20 mb10">
				<div class="col-xs-12">		
					<div class="of_h">
						<!-- 페이징 카운트 -->
						<div class="of_h inline mb10">
							<div class="float-left">전체 : <span class="color1"><strong>{{totCnt}}</strong></span>건
							<SelectLayer @fnSelected="fnSelected" classProps="selectStyle2 width120 ml20"></SelectLayer>
							</div>
						</div>
						<!-- 페이징 카운트 -->
						<div class="float-right">
							<a class="btnStyle3 gray font13 width180" data-toggle="modal" data-target="#Register" @click="fnReg" activity="SAVE">MO 수신번호 등록</a>
						</div>
					</div>
				</div>			
			</div>
			
			<div class="row">
				<div class="col-xs-12">
					<!-- 본문 -->
					<table class="table_skin1 bt-000 tbl-striped">
						<colgroup>
							<col style="width:10%">
							<col style="width:10%">
							<col>
							<col style="width:20%">
							<col style="width:10%">
							<col style="width:15%">
							<!-- <col style="width:10%"> -->
						</colgroup>
						<thead>
							<tr>
								<th class="text-center lc-1">서비스 유형</th>
								<th class="text-center lc-1">수신번호</th>
								<th class="text-center lc-1">웹훅 URL</th>
								<th class="text-center lc-1">API KEY</th>
								<th class="text-center lc-1">등록일시</th>
								<th class="text-center lc-1 end">관리</th>
								<!-- <th class="text-center lc-1 end">수정일시</th> -->
							</tr>
						</thead>
						<tbody>
							<tr v-for="(item, i) in data" :key="i">
								<td class="text-center">{{item.moType}}</td>
								<!-- <td class="text-center"><a @click="fnMod(item)">{{item.moNumber}}</a></td> -->
								<td class="text-center">{{item.moNumber | hpNumberAddDash}}</td>
								<td class="text-left">{{item.webhookUrl}}</td>
								<td class="text-left">{{item.apiKey}}</td>
								<td class="text-center">{{item.regDt}}</td>
								<td class="text-center end">
									<button class="btnStyle6 font13" @click="fnMod(item)">수정</button>&nbsp;
									<button class="btnStyle6 font13" @click="fnDel(item)">삭제</button>
								</td>
								<!-- <td class="end">{{item.updDt}}</td> -->
							</tr>
							<tr v-if="data.length == 0">
								<td class="text-center" colspan="4">검색된 내용이 없습니다.</td>
							</tr>
						</tbody>
					</table>
				</div>			
			</div>
			<!-- 본문 -->
			<!-- 페이징 -->
			<div id="pageContent">
				<PageLayer @fnClick="fnSearch" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
			</div>
			<!-- 페이징 -->
		</article>
	</div>
</template>


<script>
import Api from '../service/api'

import SelectLayer from '@/components/SelectLayer.vue';
import PageLayer from '@/components/PageLayer.vue';

import confirm from "@/modules/commonUtil/service/confirm"
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import layerPopup from "./bp-chan-mo.vue";

export default {
	components: {
		layerPopup,
		SelectLayer,
		PageLayer
	},
	data() {
		return {
			// 프로젝트 정보
			projectId : '',
			projectName : '',
			data : {},
			pageInfo: {},
			save_status : "",
			row_data : {},
			del_data : {},

			listSize : 10,  // select 박스 value (출력 갯수 이벤트)
			pageNo : 1,  // 현재 페이징 위치
			totCnt : 0,  //전체 리스트 수
			offset : 0, //페이지 시작점
		}
	},
	mounted() {
		this.projectId		= this.$route.params.projectId;
		this.projectName	= this.$route.params.projectName;

		this.pageInfo = {
			"pageCnt"   : [10, 30, 50],  //표시할 개수 리스트
			"selPageCnt": 10,          //선택한 표시 개수
			"selPage"   : 1,          //선택한 페이지
			"rowNum"    : 1           //총개수
		};

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
				"pageNo"		: (this.$gfnCommonUtils.defaultIfEmpty(pageNo, '1'))*1,
				"pageInfo"    	: this.pageInfo,
				"listSize"		: this.listSize
			}
			
			Api.selectMoCallbackList(params).then(response =>{
				var result = response.data;
				if(result.success) {
					this.data = result.data; 
					this.totCnt = result.pageInfo.totCnt;
					this.offset = result.pageInfo.offset;
				}
			});
		},
		// 등록 템플릿 상세 
		fnReg(){
			// 초기화
			this.save_status = "C";
			this.row_data = { 
				"appKey"    : "",
				"apiKey"	: "",
				"moNumber"  : "",
				"moType"    : "SMSMO",
				"pjtAllNo"  : "ALL" 
			}
			jQuery("#layerPopup").modal("show");
		},
		fnMod(row_data){
			this.save_status = "U";
			this.row_data = row_data;
			jQuery("#layerPopup").modal("show");
		},
		// mo 삭제 처리(useYn 수정)
		fnDel(row_data){
			this.del_data = row_data;
			eventBus.$on('callbackEventBus', this.fnDelCallBack);
     		confirm.fnConfirm( "", "삭제하시겠습니까?", "삭제");
		},
		fnDelCallBack(){
			var row_data = this.del_data;
			var params = {
				sts : "D",
				apiKey : row_data.apiKey,
				moNumber : row_data.moNumber,
				moType : row_data.moType
			};

			Api.saveMoCallback(params).then(response =>{
				var result = response.data;
				if(result.success){
					confirm.fnAlert("", "삭제되었습니다.");
					this.fnSearch();
				} else {
					confirm.fnAlert("", result.message);
				}
			});
			
			this.del_data = {};
		},
	}
}
</script>