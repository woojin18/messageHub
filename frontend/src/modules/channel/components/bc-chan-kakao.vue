<template>
  <div>
    <regPopup />
    <grpMngPopup/>
    <grpAddPopup/>
		<article>
			<div class="tab-content" style="border-top:1px solid #D5D5D5">
				<!--  tab1 -->
				<div role="tabpanel" v-if="this.$parent.selSubTab === 1">
					<div class="mt20">
						<!-- templateList -->
						<p class="color3"><i class="far fa-info-circle"></i> 카카오톡 비즈메시지를 발송하려면 카카오톡 채널을 먼저 등록해야 합니다.<br>
							<i class="far fa-info-circle"></i> 카카오톡 채널은 카카오톡 비즈니스 ID로, 카카오톡 홈페이지(https://center-pf.kakao.com)에서 무료로 만들 수 있습니다.<br>
							<i class="far fa-info-circle"></i> 담당자는 등록한 카카오톡 채널과 서류를 확인하고 본인 여부 등의 심사를 진행합니다.<br>
							<i class="far fa-info-circle"></i> 카카오톡 채널 등록 후, 관리자 연락처로 받은 인증 토큰 6자리를 입력해야 사용할 수 있습니다.<br>
							<i class="far fa-info-circle"></i> 카카오톡 채널 상태에 대한 자세한 설명은 플러스친구 개요 문서를 참고하시기 바랍니다.
						</p>

						<div class="menuBox mt10">						
							<div class="of_h">
								<h4 class="inline-block" style="width:10%">발신 프로필</h4>
								<input type="text" class="inputStyle" style="width:15%" v-modal="srcSenderKey">

								<h4 class="inline-block vertical-middle ml100" style="width:11%">플러스친구 상태</h4>
								<select name="admin030404_1" class="selectStyle2" style="width:15%" v-modal="srcUseYn">
									<option value="">전체</option>
									<option value="">전체</option>
								</select>
								<a class="btnStyle1 float-right" @click="fnSearch">검색</a>
							</div>						
						</div>

						<div class="consolMarginTop">
							<div class="of_h inline">
								<div class="float-right">
									<a @click="fnReg" class="btnStyle2 borderGray" data-toggle="modal" data-target="#Register">발신 프로필 추가</a>
									<a href="https://center-pf.kakao.com/" target="_blank" class="btnStyle2 borderGray ml10">카카오톡 채널 생성</a>
								</div>
							</div>
							
							<PagingCnt :pageInfo.sync="pageInfo" />
						</div>
						<div class="row">
							<div class="col-xs-12">
								<table id="list" class="table_skin1 bt-000 tbl-striped">
									<thead>
										<th class="text-center lc-1">발신프로필</th>
										<th class="text-center lc-1">발신키</th>
										<th class="text-center lc-1">프로필 상태</th>
										<th class="text-center lc-1">타 프로젝트<br>사용여부</th>
										<th class="text-center lc-1">등록일시</th>
										<th class="text-center lc-1 end">관리</th>
									</thead>
									<tbody>
										<tr v-for="(row, index) in data" :key="index">
											<td>
												{{row.senderKey}}
											</td>
											<td>
												{{row.senderKey}}
											</td>
											<td>
												{{ row.otherProjectUseYn }}
											</td>
											<td>
												{{row.otherProjectUseYn}}
											</td>
											<td>
												{{row.regDt}}
											</td>
											<td>
												<button class="btnStyle1 borderLightGray small mr5" @click="fnProjectDetail(data)" activity="SAVE"><a>상세</a></button>
												<button class="btnStyle1 borderLightGray small mr5" @click="fnProjectUpdate(data)" activity="SAVE"><a>수정</a></button>
											</td>
										</tr>
									</tbody>
								</table>
							</div>			
						</div>

						<Paging :pageInfo.sync="pageInfo" />
					</div>
				</div>

				<!--  tab2 -->
				<div role="tabpanel" v-if="this.$parent.selSubTab === 2"> 
					<div class="mt20">
						<!-- templateList -->
						<p class="color3">
							<i class="far fa-info-circle"></i> 그룹으로 심사 승인된 알림톡 템플릿은 그룹에 속한 발신 프로필에서 사용할 수 있습니다.<br>
							<i class="far fa-info-circle"></i> 동일한 템플릿을 여러 발신 프로필에서 사용할 때 유용합니다.<br>
							<i class="far fa-info-circle"></i> 그룹간 동일 템플릿 코드, 템플릿 이름을 사용 할 수 없습니다.
						</p>						
						<div class="consolMarginTop">
							<div class="of_h inline">
								<div class="float-right">
									<a @click="fnGrpAdd" class="btnStyle2 borderGray" data-toggle="modal" data-target="#Group">발신 프로필 그룹 추가</a>
								</div>
							</div>						
						</div>
						<!-- 리스트 -->
						<div class="row mt20">
							<div class="col-xs-12">
								<!-- 페이징 카운트 -->
								<PagingCnt :pageInfo.sync="pageInfo2" />
								<!-- 페이징 카운트 -->
								<!-- 본문 -->
								<table id="list" class="table_skin1 bt-000 tbl-striped">
									<thead>
										<th class="text-center lc-1">발신 프로필<br/>그룹 ID</th>
										<th class="text-center lc-1">발신키</th>
										<th class="text-center lc-1">상태</th>
										<th class="text-center lc-1">등록일시</th>
										<th class="text-center lc-1 end">관리</th>
									</thead>
									<tbody>
										<tr v-for="(row, index) in data2" :key="index">
											<td>
												<a class="color:blue; text-decoration: underline; cursor:pointer;" @click="fnRcsBrandDetail(row)">{{ row.senderKey }}</a>
											</td>
											<td>
												{{ row.senderKey }}
											</td>
											<td>
												{{ row.senderKey }}
											</td>
											<td>
												{{ row.reqDt }}
											</td>
											<td>
												<button class="btnStyle1 borderLightGray small mr5" @click="fnProjectDetail(data)" activity="SAVE"><a>발신 프로필 그룹 관리</a></button>
											</td>
										</tr>
									</tbody>
								</table>
							</div>			
						</div>
						<!-- 본문 -->
						<!-- 페이징 -->
						<Paging :pageInfo.sync="pageInfo2" />
						<!-- 페이징 -->
					</div>
				</div>


			</div>
		</article>
	</div>
</template>


<script>
import Api from '../service/api'
import regPopup from "./bp-chan-kakao.vue";
import grpMngPopup from "./bp-chan-kakao-groupManage.vue";
import grpAddPopup from "./bp-chan-kakao-groupAdd.vue";


import Paging from "@/modules/commonUtil/components/bc-paging"
import PagingCnt from "@/modules/commonUtil/components/bc-pagingCnt"


export default {
  components: {
    regPopup,
    grpMngPopup,
    grpAddPopup,
	Paging,
	PagingCnt
  },
  data() {
    return {
		// 리스트 
		data : {},
		pageInfo: {},
		data2 : {},
		pageInfo2: {},
		// 프로젝트 정보
		projectId : '',
      	projectName : '',
		// 검색조건
		srcSenderKey : '',
		srcUseYn : ''
    }
  },
  mounted() {
    this.projectId = this.$parent.projectId;
    this.projectName = this.$parent.projectName;

	this.pageInfo = {
		"pageCnt"   : [10, 30, 50],  //표시할 개수 리스트
		"selPageCnt": 10,          //선택한 표시 개수
		"selPage"   : 1,          //선택한 페이지
		"rowNum"    : 1           //총개수
	};

	this.pageInfo2 = {
		"pageCnt"   : [10, 30, 50],  //표시할 개수 리스트
		"selPageCnt": 10,          //선택한 표시 개수
		"selPage"   : 1,          //선택한 페이지
		"rowNum"    : 1           //총개수
	};

    this.fnSearch();
	this.fnSearch2();
  },
  methods: {
    // 검색
    fnSearch() {
		this.pageInfo.selPage = 1;
		var params = {
			"projectId"     : this.projectId,
			"srcSenderKey"  : this.srcSenderKey,
			"srcUseYn"  	: this.srcUseYn,
			"pageInfo"    	: this.pageInfo
		}
		
		Api.selectKkoCh(params).then(response =>{
			console.log(response);
			this.data = response.data.data;
		});
    },
	// 검색
    fnSearch2() {
		this.pageInfo2.selPage = 1;
		var params = {
			"projectId"     : this.projectId,
			"srcSenderKey"  : this.srcSenderKey,
			"srcUseYn"  	: this.srcUseYn,
			"pageInfo"    	: this.pageInfo2
		}
		
		Api.selectRcsBrandList(params).then(response =>{
			console.log(response);
			this.data2 = response.data.data;
		});
    },
    // 발신 프로필 그룹 추가
    fnReg(){
      jQuery("#regPopup").modal('show');
    },
    // 발신 프로필 그룹 추가
    fnGrpMng(){
      jQuery("#grpMngPopup").modal('show');
    },
    // 발신 프로필 그룹 추가
    fnGrpAdd(){
      jQuery("#grpAddPopup").modal('show');
    }
  }
}
</script>