<template>
  <div>
		<article>
			<h4 class="inline-block">Push APP ID 관리</h4>
			<div class="float-right h4Button">
				<a @click="fnReg" class="btnStyle2 borderGray">발신번호 등록 안내</a>
			</div>
			<p class="color3"><i class="far fa-info-circle"></i> APP ID는 5개까지 발급받을 수 있으며, 서비스 이용 시 인증에 사용됩니다.</p>

			<div class="row">
				<div class="col-xs-12 consolMarginTop">
					<!-- table -->
					<table  class="table_skin1 bt-000 tbl-striped">
						<colgroup>
              <col style="width:15%">
							<col>
							<col style="width:15%">
							<col style="width:15%">
              <col style="width:15%">
							<col style="width:10%">
							<col style="width:10%">
						</colgroup>
						<thead>
							<tr>
                <th class="text-center lc-1">APP 명</th>
                <th class="text-center lc-1">APP ID</th>
                <th class="text-center lc-1">FCM 인증서 등록여부</th>
                <th class="text-center lc-1">APNS 인증서 등록여부</th>
                <th class="text-center lc-1">타 프로젝트 사용여부</th>
                <th class="text-center lc-1">생성일</th>
                <th class="text-center lc-1 end">관리</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(row, index) in data" :key="index">
                <td class="text-center">{{row.appNm}}</td>
                <td class="text-center">{{row.appId}}</td>
                <td class="text-center">{{row.fcmYn}}</td>
                <td class="text-center">{{row.apnsYn}}</td>
                <td class="text-center">{{row.otherProjectYn}}</td>
                <td class="text-center">{{row.regDt}}</td>
                <td class="end">
                  <a @click="fnMod(row)" class="btnStyle1 borderLightGray small mr5" activity="SAVE">수정</a>
                  <a @click="fnDeleteConfirm(row)" class="btnStyle1 borderLightGray small mr5" activity="SAVE">삭제</a>
                </td>
							</tr>
						</tbody>
					</table>
					<!-- //table -->
				</div>			
			</div>
		</article>
	</div>
</template>


<script>
import api from '../service/api'

import tokenSvc from '@/common/token-service';

import {eventBus} from "@/modules/commonUtil/service/eventBus";
import confirm from "@/modules/commonUtil/service/confirm"

export default {
  components: {
    api
  },
  data() {
    return {
      // 프로젝트 정보
      projectId   : '',
      projectName : '',
      rowData     : {},
      data        : {}
    }
  },
  mounted() {
    this.projectId    = this.$parent.projectId;
    this.projectName  = this.$parent.projectName;
    this.fnSearch();
  },
  methods: {
    // 검색
    fnSearch() {
      var params = {
        "projectId" : this.projectId,
        "loginId"   : tokenSvc.getToken().principal.loginId,
      }
       
      api.selectPushManageList(params).then(response =>{
        this.data = response.data.data;
      });
    },
    // 등록 템플릿 상세 
    fnReg(){
      this.$router.push( {name:"chan-push-detail",params:{"projectId" : this.projectId, "save_status" : 'C' }} );
    },
    // 수정하기위한 상세창
    fnMod(rowData){
      this.$router.push( {name:"chan-push-detail",params:{"projectId" : this.projectId, "save_status" : 'U', "rowData" : rowData }} );
    },
    // 삭제 확인
    fnDeleteConfirm(rowData){
      this.rowData = rowData;
      eventBus.$on('callbackEventBus', this.fnDelete);
      confirm.fnConfirm("", "삭제처리된 PUSH는 복구할 수 없습니다. 계속 진행하시겠습니까?", "확인");
    },
    // 삭제
    fnDelete(){
      var params = {
        "projectId" : this.projectId,
        "loginId"   : tokenSvc.getToken().principal.loginId,
        "appId"     : this.rowData.appId
      }
       
      api.deletePushManage(params).then(response =>{
        var result = response.data;

        if(result.success) {
          confirm.fnAlert("", "삭제되었습니다.");
          // 리스트 조회
          this.fnSearch();
          // 창닫기
        } else {
          confirm.fnAlert("", result.message);
        }
      });
    }
  }
}
</script>