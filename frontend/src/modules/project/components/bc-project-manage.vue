<template>
  <div>
    <modal
      :save_status.sync="save_status"
      :row_data="row_data"
    >
    </modal>
    
    <!--  분배서비스 관리 -->
    <!-- <disRatioPop
      :rowData="row_data"
      :corpid="row_data.corpId"
      :projectid="row_data.projectId"
      :insert_or_update.sync="save_status"
      :smartChCode="row_data.smartChCode"
    >
    </disRatioPop> -->

    <!--  분배서비스 관리 -->
    <disRatioPopNew
      :smartChCode="row_data.smartChCode"
    >
    </disRatioPopNew>

    <article>
      <div class="contentHeader">
        <h2>프로젝트 목록</h2>
      </div>

      <!-- 검색창 -->
      <div class="row">
        <div class="col-xs-12">
          <div class="menuBox">
            <div class="mt20">
              <div class="of_h">
                <h4 class="font-normal inline-block" style="width:10%">프로젝트 명</h4>
                <input id="srcProjectName" type="text" class="inputStyle inline-block" style="width:30%" v-model="srcProjectName"  @keypress.enter="fnSearch()">
                <h4 class="font-normal inline-block ml40" style="width:10%">사용여부</h4>
                <select id="srcUseYn" class="selectStyle2 inline-block" style="width:30%" v-model="srcUseYn" @change="fnSearch">
                  <option value="">전체</option>
                  <option value="Y">Y</option>
                  <option value="N">N</option>
                </select>
                <a @click="fnSearch" class="btnStyle2 float-right" activity="READ">검색</a>
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
              <a v-if="distId != 'default'" @click="fnDistDetail" class="btnStyle2 borderGray">메시지 발송 분배율 확인</a>
              <a @click="fnProjectReg" class="btnStyle2 borderGray  ml20">추가</a>
            </div>
          </div>
          <table cellspacing="0" id="list" class="table_skin1 bt-000 tbl-striped" style="width:100%; margin-top : 10px;">
            <thead>
              <th class="text-center lc-1">프로젝트</th>
              <th class="text-center lc-1">프로젝트ID</th>
              <th class="text-center lc-1">이용 서비스</th>
              <th class="text-center lc-1">생성일</th>
              <th class="text-center lc-1">결재유형</th>
              <th class="text-center lc-1">멤버</th>
              <th class="text-center lc-1">최종접속일</th>
              <th class="text-center lc-1 end">관리</th>
            </thead>
            <tbody>
              <tr v-for="(data, index) in items" :key="index">
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
                  {{ data.payTypeName }}
                </td>
                <td>
                  {{ data.projectMemberCnt }}
                </td>
                <td>
                  {{ data.regDt }}
                </td>
                <td  class="end">
                  <button class="btnStyle1 borderLightGray small mr5" @click="fnProjectDetail(data)" activity="SAVE"><a>상세</a></button>
                  <button class="btnStyle1 borderLightGray small mr5" @click="fnProjectUpdate(data)" activity="SAVE"><a>수정</a></button>
                  <button class="btnStyle1 borderLightGray small mr5" @click="fnProjectDeleteConfirm(data)" activity="SAVE"><a>삭제</a></button>
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
import disRatioPop from './bp-distributionServiceManage.vue';
import disRatioPopNew from './bp-distributionServiceManageNew.vue';

export default {
  components: {
    modal,
    disRatioPop,
    disRatioPopNew,
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
      items : [],
      // 분배 id
      distId : "default",
      distName : "기본분배정책",
      distInfo : {},
    }
  },
  mounted() {
    this.fnSearch();
    this.fnDistDetailInit();
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
    fnDistDetailInit(){
      var params = {
      }
       
      projectApi.selectDistDetail(params).then(response =>{
        this.distId = response.data.data[0];
        this.distName = response.data.data[1];
        this.distInfo = response.data.data[2];

      });
    },
    fnDistDetail(){

      jQuery("#DS_NAME").val(this.distId);
      this.makeDistTable(this.distInfo);
      
      jQuery("#disRatioPopNew").modal("show");
    },
    makeDistTable : function(){
      jQuery('#RSTab > tbody').remove(); //기존 테이블 삭제
      var params = this.distName.chRelayType;
      params = params.split(',');
      var params2 = this.distInfo;
      
      for(var i=0; i<params.length; i++){
        var make_tbody = "";
        make_tbody += '<tbody id="'+params[i]+'">';
        make_tbody += '</tbody>';
        jQuery('#RSTab:last').append(make_tbody);
        var make_tr = "";
        make_tr += '<tr>';
        make_tr += '<td class="text-center">채널명</td>';
        make_tr += '<td colspan="2" class="text-center"><input type="text" value="'+params[i]+'" name="chname" class="inputStyle input3 float-left" style="width:100%; background:#D5D5D5" readonly></td>';
        make_tr += '</tr>';
        jQuery('#'+params[i]+':last').append(make_tr);

        var keyname = params[i]+"cidGroup";
        var keyArr = params2[keyname];
        keyArr = keyArr.split(',');
        var rationame = params[i]+"ratio";
        var ratioArr ="";

        ratioArr = params2[rationame];
        ratioArr = ratioArr.split(',');

        for(var k=0; k<keyArr.length; k++){
          var make_tr2 = "";
          make_tr2 += '<tr>';
          make_tr2 += '<td class="text-center">'
          if(k==0){
            make_tr2 += '분배율(%)';
          }
          make_tr2 += '</td>';
          make_tr2 += '<td class="text-center"><input type="text" name="relay" value="'+keyArr[k]+'" class="inputStyle input3 float-left" style="width:100%" readonly></td>';
          make_tr2 += '<td class="text-center"><input type="text" name="ratio" value="'+ratioArr[k]+'" class="inputStyle input3 float-left" style="width:100%" readonly></td>';
          make_tr2 += '</tr>';
          jQuery('#'+params[i]+':last').append(make_tr2);
          make_tr2 = "";
        }
      }
    },
    // 등록창
    fnProjectReg : function(){
        this.save_status = 'C';
        this.row_data = {};
        jQuery("#projectPop").modal("show");
      },
    // 상세창
    fnProjectDetail(data) {
      this.$router.push( {name:"projectMain",params:{
          "projectId" : data.projectId
        , "projectName" : data.projectName
       /*  , "rcsYn" : data.rcsYn
        , "smsmmsYn" : data.smsmmsYn
        , "pushYn" : data.pushYn
        , "kakaoYn" : data.kakaoYn
        , "moYn" : data.moYn */
      }});
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
    },
    /* // 분배율관리
    fnDisRatioManage(data) {
      var vm = this;
      this.row_data = data;

      var params = {
        SMART_CH_CODE : data.smartChCode,
        CORP_ID : data.corpId,
      }


      if(this.$gfnCommonUtils.isEmpty(data.smartChCode) || data.smartChCode === '') {
        projectApi.selectBasicDisRatio(params).then(response =>{
          this.row_data.USE_YN="Y";
          vm.makeTable(response.data);
        });
      } else {
        projectApi.selectDisRatio(params).then(response =>{
          jQuery("#DS_NAME").val(response.data.data[1].SMART_CH_NAME);
          jQuery('input:radio[name=useYN]:input[value="' + response.data.data[1].USE_YN + '"]').prop('checked', true);
          vm.makeTable(response.data);
        });
      }

      jQuery("#disRatioPop").modal("show");
    },
    makeTable : function(responsedata){
      jQuery('#RSTab > tbody').remove(); //기존 테이블 삭제
      var params = responsedata.data[0].chRelayType;
      params = params.split(',');
      var params2 = responsedata.data[1];
      var params3 = responsedata.data[2];
      var isExist = responsedata.data[0].isExist;
      if(isExist == "T"){
        this.save_status = 'U';
      }else{
        this.save_status = 'I';
      }
      
      for(var i=0; i<params.length; i++){
        var make_tbody = "";
        make_tbody += '<tbody id="'+params[i]+'">';
        make_tbody += '</tbody>';
        jQuery('#RSTab:last').append(make_tbody);
        var make_tr = "";
        make_tr += '<tr>';
        make_tr += '<td class="text-center">채널명</td>';
        make_tr += '<td colspan="2" class="text-center"><input type="text" value="'+params[i]+'" name="chname" class="inputStyle input3 float-left" style="width:100%; background:#D5D5D5" readonly></td>';
        make_tr += '</tr>';
        jQuery('#'+params[i]+':last').append(make_tr);

        var keyname = params[i]+"relay";
        var keyArr = params2[keyname];
        keyArr = keyArr.split(',');
        var rationame = params[i]+"ratio";
        var ratioArr ="";

        if(isExist == "T"){
          ratioArr = params2[rationame];
          ratioArr = ratioArr.split(',');
        }
        for(var k=0; k<keyArr.length; k++){
          var make_tr2 = "";
          make_tr2 += '<tr>';
          make_tr2 += '<td class="text-center">'
          if(k==0){
            make_tr2 += '분배율(%)';
          }
          make_tr2 += '</td>';
          make_tr2 += '<td class="text-center"><input type="text" name="relay" value="'+keyArr[k]+'" class="inputStyle input3 float-left" style="width:100%" readonly></td>';
          if(isExist == "T"){
            make_tr2 += '<td class="text-center"><input type="text" name="ratio" value="'+ratioArr[k]+'" class="inputStyle input3 float-left" style="width:100%"></td>';
            make_tr2 += '<td class="text-center"><input type="hidden" name="vendorYn" value="'+params3[params[i]+"vendorYn"]+'" ></td>';
          }else{
            make_tr2 += '<td class="text-center"><input type="text" name="ratio" value="0" class="inputStyle input3 float-left" style="width:100%"></td>';
            make_tr2 += '<td class="text-center"><input type="hidden" name="vendorYn" value="'+params3[params[i]+"vendorYn"]+'" ></td>';
          }
          make_tr2 += '</tr>';
          jQuery('#'+params[i]+':last').append(make_tr2);
          make_tr2 = "";
        }
      }
    } */
  }
}
</script>