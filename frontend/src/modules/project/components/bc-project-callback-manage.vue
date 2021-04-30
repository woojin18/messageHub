<template>
<div>
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
						<tr>
						<td class="text-center">4</td>
						<td class="text-center">테스트 브랜드</td>
						<td class="text-center">메시징플러스 고객센터</td>
						<td class="text-center">1544-1544</td>
						<td class="text-center">사용</td>
						<td class="text-center">승인</td>
						<td class="text-center">2021-01-02</td>
						<td class="text-center">2021-03-02</td>
						<td class="end"><a href="#self" class="btnStyle8 mr5">수정</a></td>
						</tr>	
						<tr>
						<td class="text-center">3</td>
						<td class="text-center">테스트 브랜드</td>
						<td class="text-center">메시징플러스 고객센터</td>
						<td class="text-center">02-3333-4444</td>
						<td class="text-center">사용</td>
						<td class="text-center">승인</td>
						<td class="text-center">2021-01-02</td>
						<td class="text-center">2021-03-02</td>
						<td class="end"><a href="#self" class="btnStyle8 mr5">수정</a></td>
						</tr>
						<tr>
						<td class="text-center">2</td>
						<td class="text-center">LG 브랜드</td>
						<td class="text-center">통합클라우드메시지기술지원</td>
						<td class="text-center">1544-1544</td>
						<td class="text-center">사용</td>
						<td class="text-center">승인</td>
						<td class="text-center">2021-01-02</td>
						<td class="text-center">2021-03-02</td>
						<td class="end"><a href="#self" class="btnStyle8 mr5">수정</a></td>
						</tr>
						<tr>
						<td class="text-center">1</td>
						<td class="text-center">LG 브랜드</td>
						<td class="text-center">통합클라우드메시지기술지원</td>
						<td class="text-center">02-3333-4444</td>
						<td class="text-center">사용</td>
						<td class="text-center">반려</td>
						<td class="text-center">2021-01-02</td>
						<td class="text-center">2021-03-02</td>
						<td class="end"><a href="#self" class="btnStyle8 mr5">수정</a></td>
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
import projectApi from '../service/projectApi'
import tokenSvc from '@/common/token-service';

export default {
	components: {

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

		// select 박스 value (출력 갯수 이벤트)
		selected : 10,
		// 현재 페이징 위치
		pagingCnt : 1,
		// 리스트 
		items : [],
		count : 0
		}
	},
	mounted() {
		var vm = this;

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
		// 등록창
		fnProjectReg : function(){
			this.visible = !this.visible;
			this.save_status = 'C';
			this.row_data = {};
		},
		// 상세창
		fnProjectDetail(data) {
			this.$router.push( {name:"chan-rcs",params:{"projectId" : data.PROJECT_ID, "projectName" : data.PROJECT_NAME}} );
		},
	}
}
</script>