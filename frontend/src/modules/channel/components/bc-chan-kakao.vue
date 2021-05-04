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
								<input type="text" class="inputStyle" style="width:15%">
								<h4 class="inline-block vertical-middle ml100" style="width:11%">플러스친구 상태</h4>
								<select name="admin030404_1" class="selectStyle2" style="width:15%">
									<option value="">전체</option>
									<option value="">전체</option>
								</select>
								<a class="btnStyle1 float-right">검색</a>
							</div>						
						</div>

						<div class="consolMarginTop">
							<div class="of_h inline">
								<div class="float-right">
									<a @click="fnReg" class="btnStyle2 borderGray" data-toggle="modal" data-target="#Register">발신 프로필 추가</a>
									<a href="https://center-pf.kakao.com/" target="_blank" class="btnStyle2 borderGray ml10">카카오톡 채널 생성</a>
								</div>
							</div>
							
							<!-- 15개씩 보기 -->
							<div class="of_h inline">
								<div class="float-left">전체 : <span class="color1"><strong>20</strong></span>건
									<select name="admin030404_2" class="selectStyle2 width120 ml20">
										<option value="">15개씩 보기</option>
										<option value="">30개씩 보기</option>
									</select>
								</div>
							</div>
							<!-- //15개씩 보기 -->
						</div>
						<div class="row">
							<div class="col-xs-12">

								<!-- table -->
								<table  class="table_skin1 bt-000 tbl-striped consolMarginTop">
									<colgroup>
										<col style="width:10%">
										<col>
										<col style="width:10%">
										<col style="width:18%">
										<col style="width:14%">
										<col style="width:20%">
									</colgroup>
									<thead>
										<tr>
										<th class="text-center lc-1">플러스친구 ID</th>
										<th class="text-center lc-1">발신키</th>
										<th class="text-center lc-1">상태</th>
										<th class="text-center lc-1">타 프로젝트  사용여부</th>
										<th class="text-center lc-1">등록일시</th>
										<th class="text-center lc-1 end">관리</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="text-center">일반탭</td>
											<td class="text-center">-</td>
											<td class="text-center">정상</td>
											<td class="text-center">공용</td>
											<td class="text-center">2020-05-13 23:39:39</td>
											<td class="end"><a @click="fnGrpMng" class="btnStyle1 borderLightGray small" data-toggle="modal" data-target="#Profile">발신 프로필 그룹 관리</a></td>
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
						<!-- //templateList -->
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
						<div class="row">
							<div class="col-xs-12">

								<!-- table -->
								<table  class="table_skin1 bt-000 tbl-striped consolMarginTop">
									<colgroup>
										<col style="width:10%">
										<col>
										<col style="width:10%">
										<col style="width:18%">
										<col style="width:14%">
										<col style="width:20%">
									</colgroup>
									<thead>
										<tr>
										<th class="text-center lc-1">플러스친구 ID</th>
										<th class="text-center lc-1">발신키</th>
										<th class="text-center lc-1">상태</th>
										<th class="text-center lc-1">타 프로젝트  사용여부</th>
										<th class="text-center lc-1">등록일시</th>
										<th class="text-center lc-1 end">관리</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="text-center">그룹탭</td>
											<td class="text-center">-</td>
											<td class="text-center">정상</td>
											<td class="text-center">공용</td>
											<td class="text-center">2020-05-13 23:39:39</td>
											<td class="end"><a @click="fnGrpMng" class="btnStyle1 borderLightGray small" data-toggle="modal" data-target="#Profile">발신 프로필 그룹 관리</a></td>
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
						<!-- //templateList -->
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

export default {
  components: {
    regPopup,
    grpMngPopup,
    grpAddPopup
  },
  data() {
    return {
      // 프로젝트 정보
      projectId : '',
      projectName : ''
    }
  },
  mounted() {
    this.projectId    = this.$route.params.projectId;
    this.projectName  = this.$route.params.projectName;

    console.log(this.$parent.selSubTab);
  },
  /* watch: {
    visibleTmplt: function(newVal, oldVal) {
      if(newVal) {
        jQuery("#confirmTmplt").modal("show");
      } else {
        jQuery("#confirmTmplt").modal("hide");
      }
    },
    row_data: function(newVal, oldVal) {
      this.fnSearchRegTmplt();
    }
  }, */
  methods: {
    // 검색
    /* fnSearch() {
      var vm = this;
      var params = {
        "projectId"     : this.projectId
      }
       
      Api.selectRcsBrandList(params).then(response =>{
        vm.items = response.data.data;
      });
    }, */
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