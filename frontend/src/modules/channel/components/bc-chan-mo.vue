<template>
	<div>
		<layerPopup />
		<article>			
			<h4 class="mt40">MO 수신번호 현황</h4>
			
			<div class="row mt20">
				<div class="col-xs-12">		
					<div class="of_h">
						<div class="float-right">
							<a class="btnStyle3 gray font13 width180" data-toggle="modal" data-target="#Register" @click="fnReg">MO 수신번호 등록</a>
						</div>
					</div>
				</div>			
			</div>
			
			<div class="row">
				<div class="col-xs-12">
					<table class="table_skin1 bt-000 tbl-striped">
						<colgroup>
							<col style="width:10%">
							<col>
							<col style="width:10%">
							<col style="width:20%">
							<col style="width:20%">
							<col style="width:15%">
						</colgroup>
						<thead>
							<tr>
							<th class="text-center lc-1">MO ID</th>
							<th class="text-center lc-1">수신번호</th>
							<th class="text-center lc-1">사용여부</th>
							<th class="text-center lc-1">등록일시</th>
							<th class="text-center lc-1">등록일시</th>
							<th class="text-center lc-1 end">처리상태</th>
							</tr>
						</thead>
						<tbody>
							<tr>
							<td class="text-center">MO123</td>
							<td class="text-center">0103334444</td>
							<td class="text-center">예</td>
							<td class="text-center">2020-05-13 23:39:39</td>
							<td class="text-center">2020-05-13 23:39:39</td>
							<td class="end">등록완료</td>
							</tr>	
							<tr>
							<td class="text-center">MO124</td>
							<td class="text-center">0103334444</td>
							<td class="text-center">아니오</td>
							<td class="text-center">2020-05-13 23:39:39</td>
							<td class="text-center">2020-05-13 23:39:39</td>
							<td class="end">등록완료</td>
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
import layerPopup from "./bp-chan-rcs-mo.vue";

export default {
  components: {
    layerPopup
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
    var vm = this;
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
	// 등록팝업창
    fnReg() {
      jQuery("#layerPopup").modal("show");
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