<template>
  <div>
    <regPopup :save_status="save_status" :row_data="row_data" :projectId="projectId" />
    <grpMngPopup :save_status="save_status" :row_data="row_data" :projectId="projectId" />
    <grpAddPopup :save_status="save_status" :row_data="row_data" :projectId="projectId" />
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
								<input type="text" class="inputStyle" style="width:15%" v-model="srcKkoChId">

								<h4 class="inline-block vertical-middle ml100" style="width:11%">프로필 상태</h4>
								<select name="admin030404_1" class="selectStyle2" style="width:15%" v-model="srcUseYn">
									<option value="">전체</option>
									<option value="Y">Y</option>
									<option value="N">N</option>
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
							<div class="float-left">전체 : <span class="color1"><strong>{{totCnt}}</strong></span>건
								<SelectLayer @fnSelected="fnSelected" classProps="selectStyle2 width120 ml20"></SelectLayer>
							</div>
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
												{{row.kkoChId}}
											</td>
											<td>
												{{row.senderKey}}
											</td>
											<td>
												{{ row.useYn }}
											</td>
											<td>
												{{row.otherProjectUseYn}}
											</td>
											<td>
												{{row.regDt}}
											</td>
											<td>
												<button class="btnStyle1 borderLightGray small mr5" @click="fnMod(row)" activity="SAVE"><a>상세</a></button>
												<button class="btnStyle1 borderLightGray small mr5" @click="fnMod(row)" activity="SAVE"><a>수정</a></button>
											</td>
										</tr>
									</tbody>
								</table>
							</div>			
						</div>
						<div id="pageContent">
							<PageLayer @fnClick="fnSearch" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
						</div>
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
								<div class="of_h inline">
									<div class="float-left">전체 : <span class="color1"><strong>{{totCnt}}</strong></span>건
										<SelectLayer @fnSelected="fnSelected" classProps="selectStyle2 width120 ml20"></SelectLayer>
									</div>
								</div>
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
						<div id="pageContent">
							<PageLayer @fnClick="fnSearch2" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
						</div>
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

import SelectLayer from '@/components/SelectLayer.vue';
import PageLayer from '@/components/PageLayer.vue';


export default {
  components: {
    regPopup,
    grpMngPopup,
    grpAddPopup,
	PageLayer,
	SelectLayer
  },
  data() {
    return {
		// 리스트 
		// 프로필 일반
		data : {},
		listSize : 10,  // select 박스 value (출력 갯수 이벤트)
		pageNo : 1,  // 현재 페이징 위치
		totCnt : 0,  //전체 리스트 수
		offset : 0, //페이지 시작점
		row_data : {},
		// 프로필 그룹
		data2 : {},
		listSize2 : 10,  // select 박스 value (출력 갯수 이벤트)
		pageNo2 : 1,  // 현재 페이징 위치
		totCnt2 : 0,  //전체 리스트 수
		offset2 : 0, //페이지 시작점
		row_data2 : {},
		// 프로젝트 정보
		projectId : '',
      	projectName : '',
		save_status : '',
		// 검색조건
		srcKkoChId : '',
		srcUseYn : ''
    }
  },
  mounted() {
    this.projectId = this.$parent.projectId;
    this.projectName = this.$parent.projectName;

    this.fnSearch(1);
	/* this.fnSearch2(1); */
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
			"projectId"     : this.projectId,
			"srcKkoChId"  	: this.srcKkoChId,
			"srcUseYn"  	: this.srcUseYn,
			"pageNo"		: (this.$gfnCommonUtils.defaultIfEmpty(pageNo, '1'))*1,
			"listSize"		: this.listSize
		}
		
		Api.selectKkoCh(params).then(response =>{
			var result = response.data;
			if(result.success) {
				this.data = result.data; 
				this.totCnt = result.pageInfo.totCnt;
				this.offset = result.pageInfo.offset;
			}
		});
    },
	// 검색
    fnSearch2() {
		this.pageInfo2.selPage = 1;
		var params = {
			"projectId"     : this.projectId,
			"srcSenderKey"  : this.srcSenderKey,
			"srcUseYn"  	: this.srcUseYn,
			"pageNo"		: (this.$gfnCommonUtils.defaultIfEmpty(pageNo, '1'))*1,
			"listSize"		: this.listSize
		}
		
		Api.selectKkoChGroup(params).then(response =>{
			/* console.log(response); */
			/* this.data2 = response.data.data; */
			var result = response.data;
			if(result.success) {
				this.data = result.data; 
				this.totCnt2 = result.pageInfo.totCnt;
				this.offset2 = result.pageInfo.offset;
			}
		});
    },
    // 발신 프로필 그룹 추가
    fnReg(){
		this.save_status = "C";
		this.row_data = { 
			"phoneNumber" 	    : "",
			"categoryId"  		: "",
			"kkoChId"    		: "",
			"otherProjectYn" 	: "Y" 
		}
		jQuery("#regPopup").modal('show');
    },
    fnMod(row_data){
		this.save_status = "U";
		this.row_data = row_data;
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