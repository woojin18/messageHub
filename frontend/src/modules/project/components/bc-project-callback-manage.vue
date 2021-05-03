<template>
	<div>
		<layerPopup  :data = "data" />
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
					<div class="of_h mb20">
						<div class="float-left">전체 : <span class="color1"><strong>20</strong></span>건
							<select name="admin0305_4" class="selectStyle2 width120 ml20">
								<option value="">15개씩 보기</option>
								<option value="">30개씩 보기</option>
							</select>
						</div>
					</div>
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
							<tr v-for="(data, index) in items" :key="index">
								<td class="text-center">{{ index + 1 }}</td>
								<td class="text-center">{{data.brandName}}</td>
								<td class="text-center">{{data.subTitle}}</td>
								<td class="text-center">{{data.mdn}}</td>
								<td class="text-center">{{data.useYnName}}</td>
								<td class="text-center">{{data.approvalName}}</td>
								<td class="text-center">{{data.regDt}}</td>
								<td class="text-center">{{data.approvalDt}}</td>
								<td class="end">
									<a @click="fnCallbackDetail(data)" class="btnStyle8 mr5">수정</a>
									<a @click="fnCallbackDelete(data)" class="btnStyle8 mr5">삭제</a>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- //table -->
				</div>			
			</div>

			<!-- pagination -->
			<div class="row mt40">
				<div class="col-xs-12">
					<div class="pagination1 text-center">
						<a href="#" title="10페이지 이전 페이지로 이동"><i class="far fa-chevron-double-left"></i></a>
						<a href="#" title="이전 페이지로 이동"><i class="far fa-chevron-left"></i></a>
						<a href="#" title="1페이지로 이동" class="number active">1</a>
						<a href="#" title="2페이지로 이동" class="number">2</a>
						<a href="#" title="3페이지로 이동" class="number">3</a>
						<a href="#" title="4페이지로 이동" class="number">4</a>
						<a href="#" title="5페이지로 이동" class="number">5</a>
						<a href="#" title="다음 페이지로 이동"><i class="far fa-chevron-right"></i></a>
						<a href="#" title="10페이지 다음 페이지로 이동"><i class="far fa-chevron-double-right"></i></a>
					</div>
				</div>
			</div>
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

export default {
	components: {
		layerPopup
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
			selected : 10,
			pagingCnt : 1,
			items : [{},{},{}],
			count : 0,
			data : {}
		}
	},
	mounted() {
		this.projectId = this.$route.params.projectId;
		this.projectName = this.$route.params.projectName;
	},
	methods: {
		fnMoveMainTab(moveTabName){
			this.$router.push( {name:moveTabName, params:{"projectId" : this.projectId, "projectName" : this.projectName }} );
		},
		// 검색
		fnSearch() {
			var vm = this;
			var params = {
			
				"rows": vm.selected,
				"paging": vm.pagingCnt
			}
			
			projectApi.selectProjectList(params).then(response =>{
				vm.items = response.data.data;
			});
		},
		fnCallbackDetail(data){
			this.data = data;
			jQuery("#detailPop").modal("show");
		},
		fnCallbackDelete(data){
			this.data = data;
		}
	}
}
</script>