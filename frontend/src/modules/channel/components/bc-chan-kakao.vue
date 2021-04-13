<template>
  <div>
    <modal
      :visible.sync="visible"
      :row_data="row_data"
    >
    </modal>
    
		<article>
			<div class="contentHeader mb20">
				<h2>{{this.projectName}}</h2>
			</div>

			<!-- 본문 -->
			<div class="contentBody mb40">
				<ul class="tab_s3">
					<li @click="fnMoveMainTab('dashBoard')"><a class="width120">대시보드</a></li>
					<li @click="fnMoveMainTab('info')"><a class="width120">기본정보</a></li>
					<li @click="fnMoveMainTab('member')"><a class="width120">멤버관리</a></li>
					<li @click="fnMoveMainTab('channel')" class="active"><a class="width120">채널관리</a></li>
					<li @click="fnMoveMainTab('callbackManage')"><a class="width120">발신번호관리</a></li>
					<li @click="fnMoveMainTab('spam')"><a class="width120">스팸관리</a></li>
				</ul>			
			</div>
			<ul class="tabStyle tab6 bgColor_tapGray mt30">
				<li @click="fnMoveSubTab('chan-rcs')"><a>RCS</a></li>
				<li @click="fnMoveSubTab('chan-smsmms')"><a>SMS/MMS</a></li>
				<li @click="fnMoveSubTab('chan-push')"><a>PUSH</a></li>
				<li @click="fnMoveSubTab('chan-kakao')" class="active"><a>카카오톡</a></li>
				<li @click="fnMoveSubTab('chan-mo')"><a>MO</a></li>
			</ul>
			
			<h4 class="mt40">카카오톡 채널(플러스친구) 추가</h4>
			<p class="font-size12 color3 mt30 inline-block line-height1-5em"><i class="far fa-info-circle"></i> 카카오톡 비즈메시지를 발송하려면 카카오톡 채널을 먼저 등록해야 합니다.<br>
				<i class="far fa-info-circle"></i> 카카오톡 채널은 카카오톡 비즈니스 ID로, 카카오톡 홈페이지(https://center-pf.kakao.com)에서 무료로 만들 수 있습니다.<br>
				<i class="far fa-info-circle"></i> 담당자는 등록한 카카오톡 채널과 서류를 확인하고 본인 여부 등의 심사를 진행합니다.<br>
				<i class="far fa-info-circle"></i> 카카오톡 채널 등록 후, 관리자 연락처로 받은 인증 토큰 6자리를 입력해야 사용할 수 있습니다.<br>
				<i class="far fa-info-circle"></i> 카카오톡 채널 상태에 대한 자세한 설명은 플러스친구 개요 문서를 참고하시기 바랍니다.
			</p>
			
      <!-- 검색창 -->
      <div class="menuBox mt30">						
				<div class="of_h">
					<h4 class="font-normal inline-block vertical-middle" style="width:12%">플러스친구 ID</h4>
					<input type="text" class="inputStyle" style="width:15%">
					<h4 class="font-normal inline-block vertical-middle ml40" style="width:14%">플러스친구 상태</h4>
					<select name="admin030404_1" class="selectStyle3" style="width:15%">
						<option value="">전체</option>
						<option value="">전체</option>
					</select>
					<a href="#self" class="btnStyle2 float-right">검색</a>
				</div>						
			</div>
      <!-- 검색창 -->

      <!-- 리스트 -->
			<div class="row mt20">
				<div class="col-xs-12">		
					<div class="of_h">
						<div class="float-right">
							<a href="#self" class="btnStyle3 gray font13 width180" data-toggle="modal" data-target="#Register">카카오톡 채널 추가</a>
							<a href="#self" class="btnStyle3 gray font13 width180 ml10">카카오톡 채널 생성</a>
						</div>
					</div>
				</div>			
			</div>
      <div class="row">
        <div class="col-xs-12">
          <!-- 15개씩 보기 -->
          <div class="of_h mb20">
            <div class="float-left">전체 : <span class="color1"><strong>20</strong></span>건
              <select name="admin030404_2" class="selectStyle2 width120 ml20">
                <option value="">15개씩 보기</option>
                <option value="">30개씩 보기</option>
              </select>
            </div>
          </div>
          <!-- //15개씩 보기 -->

          <table  class="table_skin1 bt-000 tbl-striped">
            <colgroup>
              <col style="width:10%">
              <col>
              <col style="width:10%">
              <col style="width:18%">
              <col style="width:14%">
              <col style="width:10%">
              <col style="width:12%">
              <col style="width:20%">
            </colgroup>
            <thead>
              <tr>
              <th class="text-center lc-1">플러스친구 ID</th>
              <th class="text-center lc-1">발신키</th>
              <th class="text-center lc-1">플러스친구 상태</th>
              <th class="text-center lc-1">카카오 플러스 친구<br>프로필 상태</th>
              <th class="text-center lc-1">카카오 플러스<br>친구 상태</th>
              <th class="text-center lc-1">공용/개별</th>
              <th class="text-center lc-1">등록일시</th>
              <th class="text-center lc-1 end">관리</th>
              </tr>
            </thead>
            <tbody>
              <tr>
              <td class="text-center">@rcs</td>
              <td class="text-center">-</td>
              <td class="text-center">토큰 미등록</td>
              <td class="text-center">결과 없음</td>
              <td class="text-center">결과 없음</td>
              <td class="text-center">공용</td>
              <td class="text-center">2020-05-13 23:39:39</td>
              <td class="end"><a href="#self" class="btnStyle8 mr5">관리자 인증</a><a href="#self" class="btnStyle8 mr5">삭제</a></td>
              </tr>	
              <tr>
              <td class="text-center">@플러스</td>
              <td class="text-center">b45ef1ba8962507b5650c3e2ca3934d21f5f1f9a</td>
              <td class="text-center">정상등록</td>
              <td class="text-center">활성화</td>
              <td class="text-center">정상</td>
              <td class="text-center">개별</td>
              <td class="text-center">2020-05-13 23:39:39</td>
              <td class="end"><a href="#self" class="btnStyle8 mr5">관리자 인증</a><a href="#self" class="btnStyle8 mr5">삭제</a></td>
              </tr>	
            </tbody>
          </table>
        </div>			
      </div>
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
      <!-- 리스트 -->
			<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
		</article>
	</div>
</template>


<script>
import Api from '../service/api'
import modal from "./bp-chan-rcs-tmplt-cnt.vue";

export default {
  components: {
    modal
  },
  data() {
    return {
      // 프로젝트 정보
      projectId : '',
      projectName : '',
      visible : false,  // 레이어 팝업 
    }
  },
  mounted() {
    this.projectId = this.$route.params.projectId;
    this.projectName = this.$route.params.projectName;
    //this.fnSearch();
  },
  methods: {
    fnMoveMainTab(moveTabName){
      this.$router.push( {name:moveTabName, params:{"projectId" : this.projectId, "projectName" : this.projectName }} );
    },
    fnMoveSubTab(moveTabName){
      this.$router.push( {name:moveTabName, params:{"projectId" : this.projectId, "projectName" : this.projectName }} );
    },
    // 검색
    fnSearch() {
      var vm = this;
      var params = {
        "projectId"     : this.projectId
      }
       
      Api.selectRcsBrandList(params).then(response =>{
        vm.items = response.data.data;
      });
    },
    // 등록 템플릿 상세 
    fnReg(){
      this.visible = !this.visible;
    }
  }
}
</script>