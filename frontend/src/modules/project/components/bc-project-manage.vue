<template>
  <div>
    <modal
      :save_status.sync="save_status"
      :row_data="row_data"
      :corpInfo = "corpInfo"
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
      <div class="contentHeader mb20">
        <h2>프로젝트 > 프로젝트 목록</h2>
      </div>
      <div class="color4 bgColor_f1 pd20">
				<ul class="text-left dotList">
					<li>프로젝트 생성을 통해 메시지를 보내기 위한 채널을 설정 할 수 있습니다.</li>
					<li>후불 요금제 고객은 후불 사용 승인을 받아야 프로젝트를 생성할 수 있습니다. (승인 처리는 요청 후 1~ 3일 소요됩니다.)</li>
				</ul>
			</div>
      <!-- 검색창 -->
      <div class="row">
        <div class="col-xs-12">
          <div class="menuBox mt50">
            <div class="of_h">
              <h4 class="inline-block" style="width:10%">프로젝트 명</h4>
              <input id="srcProjectName" type="text" class="inputStyle inline-block" style="width:30%" v-model="srcProjectName"  @keypress.enter="fnSearch()">
              <h4 class="inline-block ml40" style="width:10%">사용여부</h4>
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
      <!-- 검색창 -->

      <!-- 리스트 -->
      <div class="row mt20">
        <div class="col-xs-12">
          <div class="of_h">
            <div class="float-right">
              <a v-if="distId != 'default'" @click="fnDistDetail" class="btnStyle2 borderGray">메시지 발송 분배율 확인</a>
              <a @click="fnProjectReg" class="btnStyle2 borderGray  ml20">프로젝트 생성</a>
            </div>
          </div>
          <table cellspacing="0" id="list" class="table_skin1 bt-000 tbl-striped" style="width:100%; margin-top : 10px;">
            <thead>
              <th class="text-center lc-1">프로젝트</th>
              <th class="text-center lc-1">프로젝트ID</th>
              <th class="text-center lc-1">사용여부</th>
              <th class="text-center lc-1">이용 서비스</th>
              <th class="text-center lc-1">생성일</th>
              <!--<th class="text-center lc-1">결제유형</th>-->
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
                  {{ data.useYn == 'Y' ? '예' : '아니오' }}
                </td>
                <td>
                  {{ data.useCh }}
                </td>
                <td>
                  {{ data.regDt }}
                </td>
                <!--<td>
                  {{ data.payTypeName }}
                </td>-->
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
              <tr v-if="items.length == 0">
                <td class="text-center" colspan="9">검색된 내용이 없습니다.</td>
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
import homeApi from '@/modules/acHome/service/api';
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
      distNameArr : {},
      distInfo : {},
			corpInfo : {},
    }
  },
  mounted() {
    this.fnSelectCorpDistId();
    this.fnGetCorpInfo();
    this.fnSearch();
  },
  methods: {
    fnSelectCorpDistId(){
      var params = {}

      projectApi.selectCorpDistId(params).then(response =>{

        this.distId = response.data.data;
        
        this.fnDistDetailInit();
      });
    },
		async fnGetCorpInfo() {
			this.roleCd = tokenSvc.getToken().principal.role;
			await homeApi.selectCorpInfo({}).then(response =>{
				var result = response.data;
				if (result.success) {
					this.corpInfo = result.data
				} else {
					confirm.fnAlert("", result.message);
				}
			});
		},
    // 검색
    fnSearch() {
      var vm = this;
      var params = {
        "srcProjectName"  : this.srcProjectName,
        "srcUseYn"        : this.srcUseYn,
        "loginId"         : tokenSvc.getToken().principal.userId,
        //"roleCd"         : tokenSvc.getToken().principal.roleCd,
        "roleCd"         : tokenSvc.getToken().principal.role,
      }

      projectApi.selectProjectList(params).then(response =>{
        vm.items = response.data.data;
      });
    },
    fnDistDetailInit(){
      if(this.distId != 'default'){
        var params = {
        }
        
        projectApi.selectDistDetail(params).then(response =>{
          this.distId = response.data.data[0];
          this.distName = response.data.data[2].DIS_NAME;
          this.distNameArr = response.data.data[1];
          this.distInfo = response.data.data[2];
        });
      }
      
    },
    fnDistDetail(){

      jQuery("#DS_NAME").val(this.distName);
      this.makeDistTable(this.distInfo);
      
      jQuery("#disRatioPopNew").modal("show");
    },
    makeDistTable : function(){
      jQuery('#RSTab > tbody').remove(); //기존 테이블 삭제
      var params = this.distNameArr.chRelayType;
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
          make_tr2 += '<td class="text-center"><input type="text" name="ratio" value="'+this.$gfnCommonUtils.formatPrice(ratioArr[k])+'" class="inputStyle input3 float-left" style="width:100%" readonly></td>';
          make_tr2 += '</tr>';
          jQuery('#'+params[i]+':last').append(make_tr2);
          make_tr2 = "";
        }
      }
    },
    // 등록창
    fnProjectReg : function(){
      if (this.corpInfo.feeType == 'PRE' || (this.corpInfo.feeType == 'POST' && this.corpInfo.billStatus == 'APP')) {
        this.save_status = 'C';
        this.row_data = {};
        jQuery("#projectPop").modal("show");
      } else {
        confirm.fnAlert("", "프로젝트 생성을 위해서는 후불 사용승인을 받아야 합니다.");
      }
      },
    // 상세창
    fnProjectDetail(data) {
      this.$router.push( {name:"projectMain",params:{
          "projectId" : data.projectId
        , "projectName" : data.projectName
        , "selMainTab" : 1
        , "selMidTab" : 1
        , "selSubTab" : 1
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
          jQuery('#M_subCnt').val(parseInt(jQuery('#M_subCnt').val(),10)-1);
          // 리스트 조회
          this.fnSearch();
        } else {
          var msg = response.data.message;
          confirm.fnAlert("", msg);
        }
      }); 
    },
  }
}
</script>