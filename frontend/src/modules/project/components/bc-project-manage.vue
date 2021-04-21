<template>
  <div>
    <modal
      :save_status.sync="save_status"
      :row_data="row_data"
    >
    </modal>
    
		<article>
      <div class="contentHeader">
        <h2> > 프로젝트 목록</h2>
      </div>

      <!-- 검색창 -->
      <div class="row">
        <div class="col-xs-12">
          <div class="menuBox">
            <div class="mt20">
              <div class="of_h">
                <h4 class="font-normal inline-block" style="width:10%">프로젝트 명</h4>
                <input id="srcProjectName" type="text" class="inputStyle inline-block" style="width:30%" title="프로젝트 명 입력란" v-model="srcProjectName">
                <h4 class="font-normal inline-block ml40" style="width:10%">사용여부</h4>
                <select id="srcUseYn" class="selectStyle3 inline-block" style="width:30%" title="사용여부 선택란" v-model="srcUseYn">
                  <option value="">전체</option>
                  <option value="Y">Y</option>
                  <option value="N">N</option>
                </select>
                <a @click="fnSearch" class="btnStyle2 float-right" title="검색">검색</a>
              </div>	
            </div>
          </div>
        </div>
      </div>
      <!-- 검색창 -->

      <!-- 리스트 -->
      <div class="row mt20">
        <div class="col-xs-12">
          <div class="of_h">
            <div class="float-right">
              <a @click="fnProjectReg" class="btnStyle3 gray font13 minWidth120">추가</a>
            </div>
          </div>
          <table cellspacing="0" id="list" class="table_skin1 tbl-striped" style="width:100%">
            <thead>
              <th>No.</th>
              <th>프로젝트</th>
              <th>프로젝트ID</th>
              <th>이용 서비스</th>
              <th>생성일</th>
              <th>결재유형</th>
              <th>멤버</th>
              <th>최종접속일</th>
              <th>관리</th>
              <th>분배율관리</th>
            </thead>
            <tbody>
              <tr v-for="(data, index) in items" :key="index">
                <td>
                  {{ index + 1 }}
                </td>
                <td>
                  {{ data.projectName }}
                </td>
                <td>
                  {{ data.projectId }}
                </td>
                <td>
                  {{ data.useCh }}
                </td>
                <td>
                  {{ data.regDt }}
                </td>
                <td>
                  {{ data.payType }}
                </td>
                <td>
                  {{ data.projectMemberCnt }}
                </td>
                <td>
                  {{ data.regDt }}
                </td>
                <td>
                  <button @click="fnProjectDetail(data)"><a>상세</a></button>
                  <button @click="fnProjectUpdate(data)"><a>수정</a></button>
                  <button @click="fnProjectDeleteConfirm(data)"><a>삭제</a></button>
                </td>
                <td>
                  <button><a>분배율관리</a></button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <!-- 리스트 -->
    </article>
  </div>
</template>

<script>
import projectApi from '../service/projectApi'
import tokenSvc from '@/common/token-service';

import {eventBus} from "@/modules/commonUtil/service/eventBus";
import confirm from "@/modules/commonUtil/service/confirm"

import modal from "./bp-project-manage-detail.vue";

export default {
  components: {
    modal
  },
  data() {
    return {
      save_status : '', // 등록 수정 여부
      row_data : {},
      // 검색 조건
      srcProjectName : "",
      srcUseYn : "",
      // 삭제용
      deleteProjectId : "",
      // 리스트 
      items : []
    }
  },
  mounted() {
    this.fnSearch();

  },
  methods: {
    // 검색
    fnSearch() {
      var vm = this;
      var params = {
        "srcProjectName"  : this.srcProjectName,
        "srcUseYn"        : this.srcUseYn,
        "loginId"         : tokenSvc.getToken().principal.userId,
        "roleCd"         : tokenSvc.getToken().principal.roleCd
      }
       
      projectApi.selectProjectList(params).then(response =>{
        vm.items = response.data.data;
      });
    },
    // 등록창
    fnProjectReg : function(){
        this.save_status = 'C';
        this.row_data = {};
        jQuery("#projectPop").modal("show");
      },
    // 상세창
    fnProjectDetail(data) {
      eventBus.$emit('projectId', data.projectId);
      this.$router.push( {name:"projectMain",params:{"projectId" : data.projectId, "projectName" : data.projectName}} );
    },
    // 수정창
    fnProjectUpdate(data) {
      jQuery("#projectPop").modal("show");
      this.save_status = 'U';
      this.row_data = data;
    },
    // 삭제확인
    fnProjectDeleteConfirm(data){
      this.deleteProjectId = data.projectId;
      eventBus.$on('callbackEventBus', this.fnProjectDelete);
      confirm.fnConfirm("", "삭제처리된 프로젝트는 복구할 수 없습니다. 계속 진행하시겠습니까?", "확인");
      
    },
    // 삭제
    fnProjectDelete(){
      var params = {
        "projectId"   : this.deleteProjectId,
        "sts"         : "D",
        "userDto"     : tokenSvc.getToken().principal    
      };

      projectApi.saveProject(params).then(response =>{
        var result = response.data;

        if(result.success) {
          confirm.fnAlert("", "삭제되었습니다");
          // 리스트 조회
          this.fnSearch();
        } else {
          var msg = response.data.message;
          confirm.fnAlert("", msg);
        }
      }); 
    }
  }
}
</script>