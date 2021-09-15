<template>
  <div>
    <!-- 본문 -->
    <div class="of_h h4Minus">
      <h4 class="inline-block">미사용 청구ID 조회(후불)</h4>
      <!-- <div class="float-right h4Button">
        <a @click="fnCreateClaimId" class="btnStyle2 borderGray">청구ID 등록</a>
      </div> -->
    </div>

    <div class="row consolMarginTop">
      <div class="col-xs-12">									
        <!-- table -->
        <table class="table_skin1 bt-000 tbl-striped">
          <thead>
          <tr>
            <!-- <th class="text-center lc-1">가입번호</th> -->
            <th class="text-center lc-1">청구ID</th>
            <th class="text-center lc-1 end">청구ID 생성일</th>
          </tr>
          </thead>
          <tbody>
            <tr v-for="(data, index) in ucubeData" :key="index">
              <!-- <td class="text-center">{{data.a}}</td> -->
              <td class="text-center">{{data.billId}}</td>
              <td class="text-center end">{{data.regDt}}</td>
            </tr>	
            <tr v-if="ucubeData.length == 0">
              <td class="text-center" colspan="2">검색된 내용이 없습니다.</td>
            </tr>
          </tbody>
        </table>
        <!-- //table -->
      </div>			
    </div>

    <h4>프로젝트별 청구</h4>
    <div class="row">
      <div class="col-xs-12">
        <!-- 15개씩 보기 -->
        <div class="of_h mb20">
          <div class="float-left">전체 : <span class="color1"><strong>{{projectCnt}}</strong></span>건
          </div>
        </div>
        <!-- //15개씩 보기 -->
        
        <!-- table -->
        <table  class="table_skin1 bt-000 tbl-striped">
          <colgroup>
            <col style="width:20%">
            <col>
            <col style="width:15%">
            <col style="width:15%">
            <col style="width:15%">
            <col style="width:15%">
          </colgroup>
          <thead>
          <tr>
            <th class="text-center lc-1">프로젝트 명</th>
            <th class="text-center lc-1">프로젝트 ID</th>
            <th class="text-center lc-1">프로젝트 생성일</th>
            <th class="text-center lc-1">사용여부</th>
            <th class="text-center lc-1">가입ID</th>
            <th class="text-center lc-1 end">청구ID</th>
          </tr>
          </thead>
          <tbody>
            <tr v-for="(data, index) in projectData" :key="index">
              <td class="text-left">{{data.projectName}}</td>
              <td class="text-center">{{data.projectId}}</td>
              <td class="text-center">{{data.regDt}}</td>
              <td class="text-center" v-if="data.useYn=='Y'">예</td>
              <td class="text-center" v-else>아니오</td>
              <td class="text-center">{{data.serviceId}}</td>
              <td @click="fnModClaimId(data)" class="text-center end" style="text-decoration: underline; cursor: pointer;">{{data.billId}}</td>
            </tr>
            <tr v-if="projectData.length == 0">
              <td class="text-center" colspan="6">검색된 내용이 없습니다.</td>
            </tr>
          </tbody>
        </table>
        <!-- //table -->
      </div>			
    </div>

    <h4>부서별 청구</h4>
    <div class="row">
      <div class="col-xs-12">
        <div class="of_h inline">
          <div class="float-right">
            <a @click="fnCreateDept" class="btnStyle2 borderGray">부서 등록</a>
          </div>
        </div>

        <!-- 15개씩 보기 -->
        <div class="of_h inline">
          <div class="float-left">전체 : <span class="color1"><strong>{{deptCnt}}</strong></span>건
          </div>
        </div>
        <!-- //15개씩 보기 -->
        
        <div class="row">
					<div class="col-xs-12 consolMarginTop">
            <!-- table -->
            <table class="table_skin1 bt-000 tbl-striped">
              <colgroup>
                <col style="width:20%">
                <col>
                <col style="width:15%">
                <col style="width:15%">
                <col style="width:15%">
                <col style="width:15%">
              </colgroup>
              <thead>
              <tr>
                <th class="text-center lc-1">부서코드</th>
                <th class="text-center lc-1">부서명</th>
                <th class="text-center lc-1">부서 수정일</th>
                <th class="text-center lc-1">사용여부</th>
                <th class="text-center lc-1">청구ID</th>
                <th class="text-center lc-1 end">관리</th>
              </tr>
              </thead>
              <tbody>
                <tr v-for="(data, index) in deptData" :key="index">
                  <td class="text-left">{{data.deptCode}}</td>
                  <td class="text-center">{{data.deptName}}</td>
                  <td class="text-center">{{data.updDt}}</td>
                  <td class="text-center" v-if="data.useYn=='Y'">예</td>
                  <td class="text-center" v-else>아니오</td>
                  <td class="text-center">{{data.billId}}</td>
                  <td class="text-center end">
                    <a @click="fnModDept(data)" class="btnStyle1 borderLightGray small mr5">수정</a>
                    <a @click="fnDelDept(data)" class="btnStyle1 borderLightGray small mr5">삭제</a>
                  </td>
                </tr>	
                <tr v-if="deptData.length == 0">
                  <td class="text-center" colspan="6">검색된 내용이 없습니다.</td>
                </tr>
              </tbody>
            </table>
            <!-- //table -->
          </div>
        </div>
      </div>			
    </div>
    <deptPop :popReset="popReset" :state="popState" :deptInfo="selDeptInfo" :ucubeData="ucubeData"></deptPop>
    <claimIdPop :popReset="popReset" :ucubePopData="ucubePopData" :selProjectInfo="selProjectInfo"></claimIdPop>
  </div>
</template>

<script>
import deptPop from "@/modules/cash/components/bp-deptPop"
import claimIdPop from "@/modules/cash/components/bp-claimIdPop"

import cashApi from "@/modules/cash/service/api"
import tokenSvc from '@/common/token-service'

export default {
  name: 'deferredCash',
  data() {
    return {
      ucubeData: [],
      ucubePopData : [],
      projectCnt: 0,
      projectData: [],
      deptCnt: 0,
      deptData: [],
      selDeptInfo: {},
      selProjectInfo: {},
      popReset: 0,
      popState: ""
    }
  },
  components: {
    deptPop,
    claimIdPop
  },
  mounted() {
    this.fnSelectUcubeInfo();
    this.fnSelectUcubePopInfo();
    this.fnSelectProjectInfo();
    this.fnSelectDeptInfo();
  },
  methods: {
    fnSelectUcubeInfo: function() {
      var params = {
        "corpId" : tokenSvc.getToken().principal.corpId
      };

      cashApi.selectUcubeInfo(params).then(response => {
        var result = response.data;
        if(result.success) {
          this.ucubeData = result.data;
        }
      });
    },

    
    fnSelectUcubePopInfo: function() {
      var params = {
        "corpId" : tokenSvc.getToken().principal.corpId
      };

      cashApi.selectUcubePopInfo(params).then(response => {
        var result = response.data;
        if(result.success) {
          this.ucubePopData = result.data;
        }
      });
    },

    fnSelectProjectInfo: function() {
      var params = {
        "corpId" : tokenSvc.getToken().principal.corpId
      };

      cashApi.selectProjectInfo(params).then(response => {
        var result = response.data;
        if(result.success) {
          this.projectCnt = result.data.count;
          this.projectData = result.data.list;
        }
      });
    },

    fnSelectDeptInfo: function() {
      var params = {
        "corpId" : tokenSvc.getToken().principal.corpId
      };

      cashApi.selectProjectSubBillCode(params).then(response => {
        var result = response.data;
        if(result.success) {
          this.deptData = result.data.list;
          this.deptCnt = result.data.count;
        }
      });
    },

    fnCreateClaimId: function() {
      jQuery("#createClaimIdPop").modal("show");
    },

    fnModClaimId: function(projectInfo) {
      this.selProjectInfo = projectInfo;
      this.popReset = this.popReset +1;
      jQuery("#modClaimIdPop").modal("show");
    },

    fnCreateDept: function() {
      this.selDeptInfo = {"deptCode":"", "deptName":"", "useYn":"Y", "billId":""};
      this.popReset = this.popReset +1;
      this.popState = "add";
      jQuery("#deptInfoPop").modal("show");
    },

    fnModDept: function(deptInfo) {
      this.selDeptInfo = deptInfo;
      this.popReset = this.popReset +1;
      this.popState = "edit";
      jQuery("#deptInfoPop").modal("show");
    },

    fnDelDept: function(deptInfo) {
      this.selDeptInfo = deptInfo;
      this.popReset = this.popReset +1;
      jQuery("#delDeptPop").modal("show");
    }
  }
}
</script>