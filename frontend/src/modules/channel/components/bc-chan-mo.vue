<template>
	<div>
		<layerPopup :save_status="save_status" :row_data="row_data" :projectId="projectId" />
		<article>
			<h4 class="mt40">MO 수신번호 현황</h4>
			
			<div class="row mt20">
				<div class="col-xs-12">		
					<div class="of_h">
						<div class="float-right">
							<a class="btnStyle3 gray font13 width180" data-toggle="modal" data-target="#Register" @click="fnReg" activity="SAVE">MO 수신번호 등록</a>
						</div>
					</div>
				</div>			
			</div>
			
			<div class="row">
				<div class="col-xs-12">
					<!-- 페이징 카운트 -->
					<PagingCnt :pageInfo.sync="pageInfo" />
					<!-- 페이징 카운트 -->
					<!-- 본문 -->
					<table class="table_skin1 bt-000 tbl-striped">
						<colgroup>
							<col style="width:15%">
							<col style="width:10%">
							<col>
							<col style="width:10%">
							<col style="width:10%">
							<!-- <col style="width:10%"> -->
						</colgroup>
						<thead>
							<tr>
								<th class="text-center lc-1">API KEY</th>
								<th class="text-center lc-1">서비스 유형</th>
								<th class="text-center lc-1">수신번호</th>
								<th class="text-center lc-1">사용여부</th>
								<th class="text-center lc-1 end">등록일시</th>
								<!-- <th class="text-center lc-1 end">수정일시</th> -->
							</tr>
						</thead>
						<tbody>
							<tr v-for="(item, i) in data" :key="i">
								<td class="text-center">{{item.apiKey}}</td>
								<td class="text-center">{{item.moType}}</td>
								<td class="text-center"><a @click="fnMod(item)">{{item.moNumber}}</a></td>
								<td class="text-center">{{item.useYn}}</td>
								<td class="end">{{item.regDt}}</td>
								<!-- <td class="end">{{item.updDt}}</td> -->
							</tr>
						</tbody>
					</table>
				</div>			
			</div>
			<!-- 본문 -->
			<!-- 페이징 -->
			<Paging :pageInfo.sync="pageInfo" />
			<!-- 페이징 -->
		</article>
	</div>
</template>


<script>
import Api from '../service/api'

import Paging from "@/modules/commonUtil/components/bc-paging"
import PagingCnt from "@/modules/commonUtil/components/bc-pagingCnt"

import layerPopup from "./bp-chan-mo.vue";

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
			data : {},
			pageInfo: {},
			save_status : "",
			row_data : {}
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

		this.fnSearch();
	},
	methods: {
		// 검색
		fnSearch() {
			var params = {
				"projectId"		: this.projectId,
				"pageInfo"    	: this.pageInfo
			}
			
			Api.selectMoCallbackList(params).then(response =>{
				this.data = response.data.data;
			});
		},
		// 등록 템플릿 상세 
		fnReg(){
			// 초기화
			this.save_status = "C";
			this.row_data = { 
				"appKey"    : "",
				"moNumber"  : "",
				"moType"    : "SMSMO",
				"pjtAllNo"  : "ALL" 
			}
			jQuery("#layerPopup").modal("show");
		},
		fnMod(row_data){
			/* this.save_status = "U";
			this.row_data = row_data;
			jQuery("#layerPopup").modal("show"); */
		}
	}
}
</script>