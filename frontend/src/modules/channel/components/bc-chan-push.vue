<template>
  <div>
    <modal
      :visible.sync="visible"
      :row_data="row_data"
    >
    </modal>
    
		<article>
			<h4 class="mt40">Push APP ID 관리</h4>
			<p class="font-size12 color3 mt30 inline-block"><i class="far fa-info-circle"></i> APP ID는 5개까지 발급받을 수 있으며, 서비스 이용 시 인증에 사용됩니다.</p>
			
			<div class="Dashboard01 border-line">
				<div class="row mt20">
					<div class="col-xs-12">		
						<div class="of_h">
							<div class="float-right">
								<a @click="fnReg" class="btnStyle3 gray font13 minWidth120">발신번호 등록 안내</a>
							</div>
						</div>
					</div>			
				</div>

				

        <!-- 그리드 -->
				<div class="row mt20">
					<div class="col-xs-12">
            <table  class="table_skin1 bt-000 tbl-striped">
              <colgroup>
                <col style="width:15%">
                <col>
                <col style="width:15%">
                <col style="width:15%">
                <col style="width:10%">
                <col style="width:15%">
              </colgroup>
              <thead>
                <tr>
                <th class="text-center lc-1">APP ID 명</th>
                <th class="text-center lc-1">APP ID</th>
                <th class="text-center lc-1">FCM 인증서 등록여부</th>
                <th class="text-center lc-1">APNS 인증서 등록여부</th>
                <th class="text-center lc-1">생성일</th>
                <th class="text-center lc-1 end">관리</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                <td class="text-center">PUSH_APP_01</td>
                <td class="text-center">B3a1ef685ac84de692471252b6eda121</td>
                <td class="text-center">예</td>
                <td class="text-center">아니오</td>
                <td class="text-center">2021-01-02</td>
                <td class="end"><a href="#self" class="btnStyle8 mr5">수정</a><a href="#self" class="btnStyle8 mr5">삭제</a></td>
                </tr>
                <tr>
                <td class="text-center">PUSH_APP_02</td>
                <td class="text-center">B3a1ef685ac84de692471252b6eda122</td>
                <td class="text-center">예</td>
                <td class="text-center">아니오</td>
                <td class="text-center">2021-01-02</td>
                <td class="end"><a href="#self" class="btnStyle8 mr5">수정</a><a href="#self" class="btnStyle8 mr5">삭제</a></td>
                </tr>
                <tr>
                <td class="text-center">PUSH_APP_03</td>
                <td class="text-center">B3a1ef685ac84de692471252b6eda123</td>
                <td class="text-center">예</td>
                <td class="text-center">아니오</td>
                <td class="text-center">2021-01-02</td>
                <td class="end"><a href="#self" class="btnStyle8 mr5">수정</a><a href="#self" class="btnStyle8 mr5">삭제</a></td>
                </tr>
                <tr>
                <td class="text-center">PUSH_APP_04</td>
                <td class="text-center">B3a1ef685ac84de692471252b6eda124</td>
                <td class="text-center">예</td>
                <td class="text-center">아니오</td>
                <td class="text-center">2021-01-02</td>
                <td class="end"><a href="#self" class="btnStyle8 mr5">수정</a><a href="#self" class="btnStyle8 mr5">삭제</a></td>
                </tr>
              </tbody>
            </table>
					</div>			
				</div>

			</div>
			<!-- //본문 -->

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
      this.$router.push( {name:"chan-push-detail",params:{"projectId" : this.projectId, "save_status" : 'C' }} );
    }
    
  }
}
</script>