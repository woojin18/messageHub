<template>
	<div id="content">
		<article>
			<div class="contentHeader mb20">
				<h2>수신자 관리</h2>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<div class="menuBox">
						<div>
							<select class="selectStyle2 vertical-middle" style="width:15%" v-model="searchData.searchTextType">
								<option value="cuName">수신자명</option>
								<option value="cuid">아이디</option>
								<option value="hpNumber">휴대폰번호</option>
							</select>
							<input type="text" class="inputStyle ml10" style="width:44%" v-model="searchData.searchText">
							<h4 class="inline-block ml50" style="width:7%">사용여부</h4>
							<select class="selectStyle2" style="width:15%" v-model="searchData.useYn">
								<option value="">전체</option>
								<option value="Y">사용</option>
								<option value="N">미사용</option>
							</select>
							<a @click="fnSearch()" class="btnStyle1 float-right">검색</a>
						</div>
					</div>
				</div>
			</div>

			<div class="row consolMarginTop">
				<div class="col-xs-12">		
					<div class="of_h inline">
						<div class="float-right">
							<a @click="fnRegisterReceiverPop" class="btnStyle2 borderGray" >수신자 추가</a>
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
							<!-- table -->
							<table  class="table_skin1 bt-000 tbl-striped">
								<colgroup>
									<col style="width:10%">
									<col>
									<col style="width:22%">
									<col style="width:20%">
									<col style="width:12%">
									<col style="width:15%">
								</colgroup>
								<thead>
									<tr>
										<th class="text-center lc-1">No.</th>
										<th class="text-center lc-1">수신자명</th>
										<th class="text-center lc-1">앱로그인 ID</th>
										<th class="text-center lc-1">휴대폰 번호</th>
										<th class="text-center lc-1">사용여부</th>
										<th class="text-center lc-1 end">관리</th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="(data) in items" :key="data.cuInfoId">
										<td class="text-center"> {{ data.rownum + offset}} </td>
										<td class="text-left"> {{ data.cuName }} </td>
										<td class="text-left"> {{ data.cuid }} </td>
										<td class="text-center"> {{ data.hpNumber }} </td>
										<td v-if="data.useYn == 'Y'" class="text-center"> 사용 </td>
										<td v-else class="text-center"> 미사용 </td>
										<td class="end">
											<a @click="fnModifyReceiverPop(data)" class="btnStyle1 borderLightGray small mr5">수정</a>
											<a @click="fnDeleteReceiver(data)" class="btnStyle1 borderLightGray small mr5">삭제</a>
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
			<!-- pagination Start -->
			<div id="pageContent">
				<PageLayer @fnClick="fnPageClick" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
			</div>
			<!-- pagination End-->
			<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
		</article>

		<!--  Modal -->
		<RcvrRegMdfyLayer :rowData="rowData" :status.sync="status"></RcvrRegMdfyLayer>
	</div>
</template>

<script>
import addressApi from '../service/addressApi'
//import tokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm";
import PageLayer from '@/components/PageLayer.vue';
import SelectLayer from '@/components/SelectLayer.vue';
import RcvrRegMdfyLayer from '../components/bc-receiver-register.vue';

export default {
	name: "receiverManageList",
	components: {
		SelectLayer,
		PageLayer,
		RcvrRegMdfyLayer,
	},
	props: {
		searchData : {
			type: Object,
			require: false,
			default: function() {
				return {
					'cuName'		: '',
					'hpNumber'		: '',
					'cuId'			: '',
					'searchTextType': 'cuName',
					'searchText'	: '',
					'useYn'			: '',
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
			status: '',
			rowData: {},
		}
	},
	mounted() {
		this.fnSearch();
	},
	methods: {
		fnSearch(pageNum) {
			this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNum, '1'))*1;
			this.fnSearchReceiverList();
		},
		// select 박스 선택시 리스트 재출력
		fnSelected(listSize) {
			this.listSize = Number(listSize);
			this.$refs.updatePaging.fnAllDecrease();
		},
		//수신자 리스트 검색
		async fnSearchReceiverList(){
			var params = Object.assign({}, this.searchData);
			params.pageNo = this.pageNo;
			params.listSize = this.listSize;

			await addressApi.selectReceiverList(params).then(response =>{
				var result = response.data;
				if(result.success) {
					this.items = result.data;
					this.totCnt = result.pageInfo.totCnt;
					this.offset = result.pageInfo.offset;
console.log('totCnt : ' + this.totCnt);
console.log('offset : ' + this.offset);
				} else {
					confirm.fnAlert("", result.message);
				}
			});
		},
		// 삭제
		fnDeleteReceiver(rowData) {
			return rowData;
		},
		// 수신자 수정
		fnModifyReceiverPop(data) {
			this.status = 'U';
			this.rowData = data
			jQuery("#RcvrRegMdfyLayer").modal("show");
			
		},
		// 수신자 등록
		fnRegisterReceiverPop() {
			this.status = 'R';
			this.rowData = {};
			jQuery("#RcvrRegMdfyLayer").modal("show");
		},
		fnPageClick(pageNo) {
			this.pageNo = pageNo;
			this.fnSearchReceiverList();
		},
	}
}
</script>


