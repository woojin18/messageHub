<template>
  <div>
  <!-- <div id="content"> -->
    <modal 
      :modal_title.sync="modal_title" 
      :visible.sync="visible"
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
                <input id="src_project_name" type="text" class="inputStyle inline-block" style="width:30%" title="프로젝트 명 입력란">
                <h4 class="font-normal inline-block ml40" style="width:10%">사용여부</h4>
                <select id="src_use_yn" class="selectStyle3 inline-block" style="width:30%" title="사용여부 선택란">
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
            </thead>
            <tbody>
              <tr v-for="(data, index) in items" :key="index">
                <td>
                  {{ index + 1 }}
                </td>
                <td>
                  <!-- <a href="javascript:void(0);" @click="fnUpdateListPop(index)">{{ data.COL1 }}</a> -->
                  {{ data.PROJECT_NAME }}
                </td>
                <td>
                  {{ data.PROJECT_ID }}
                </td>
                <td>
                  {{ data.USE_CH }}
                </td>
                <td>
                  {{ data.REG_DT }}
                </td>
                <td>
                  {{ data.PAY_TYPE_NAME }}
                </td>
                <td>
                  {{ data.PROJECT_MEMBER_CNT }}
                </td>
                <td>
                  {{ data.REG_DT }}
                </td>
                <td>
                  <!-- {{ index + 1 }} -->
                  <button @click="fnProjectDetail(data)"><a>상세</a></button>
                  <button @click="fnProjectUpdate(data)"><a>수정</a></button>
                  <button @click="fnProjectDelete(data)"><a>삭제</a></button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <!-- 리스트 -->


      <div class="contentHeader">
        <h2> > 서비스 분배 관리</h2>
      </div>
      <!-- 리스트 -->
      <div class="row mt20">
        <div class="col-xs-12">
          <div class="of_h">
            <div class="float-right">
              <a @click="fnProjectReg" class="btnStyle3 gray font13 minWidth120">분배율 등록</a>
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
            </thead>
            <tbody>
              <tr v-for="(data, index) in items" :key="index">
                <td>
                  {{ index + 1 }}
                </td>
                <td>
                  <!-- <a href="javascript:void(0);" @click="fnUpdateListPop(index)">{{ data.COL1 }}</a> -->
                  {{ data.PROJECT_NAME }}
                </td>
                <td>
                  {{ data.PROJECT_ID }}
                </td>
                <td>
                  {{ data.USE_CH_LIST }}
                </td>
                <td>
                  {{ data.REG_DT }}
                </td>
                <td>
                  {{ data.PAY_TYPE }}
                </td>
                <td>
                  {{ data.PROJECT_MEMBER_CNT }}
                </td>
                <td>
                  {{ data.REG_DT }}
                </td>
                <td>
                  <!-- {{ index + 1 }} -->
                  <button><a>상세</a></button>
                  <button><a>수정</a></button>
                  <button><a>삭제</a></button>
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

import modal from "./bp-project-manage-detail.vue";

export default {
  components: {
    modal
  },
  data() {
    return {
      visible : false,  // 레이어 팝업 visible
      modal_title : '', // 레이어 팝업 타이틀
      save_status : '', // 등록 수정 여부
      row_data : {},
      // 검색 조건
      col1 : "",
      col2 : "",
      // select 박스 value (출력 갯수 이벤트)
      selected : 10,
      // 현재 페이징 위치
      pagingCnt : 1,
      // 리스트 
      items : [],
      count : 0
    }
  },
  mounted() {
    var vm = this;

    this.fnSearch();
  },
  methods: {
    // 검색
    fnSearch() {
      
      var vm = this;
      var params = {
        "src_project_name"  : $("#src_project_name").val(),
        "src_use_yn"        : $("#src_use_yn").val(),
        "rows"              : vm.selected,
        "paging"            : vm.pagingCnt
      }
       
      projectApi.selectProjectList(params).then(response =>{
        vm.items = response.data.data;
      });
    },
    // 등록창
    fnProjectReg : function(){
        this.visible = !this.visible;
        this.save_status = 'C';
        this.row_data = {};
      },
    // 상세창
    fnProjectDetail(data) {
      this.visible = !this.visible;
      this.save_status = 'R';
      this.row_data = data;
    },
    // 수정창
    fnProjectUpdate(data) {
      this.visible = !this.visible;
      this.save_status = 'U';
      this.row_data = data;
    },
    fnProjectDelete(data){
      var params = {
        "project_id"  : data.PROJECT_ID,
        "sts"         : "D",
        "userDto"     : tokenSvc.getToken().principal    
      };

      projectApi.saveProject(params).then(response =>{
        var result = response.data;

        if(result.success) {
          alert("삭제되었습니다.");
          // 리스트 조회
          this.fnSearch();
        } else {
          alert("저장에 실패했습니다.");
        }
      }); 
    }



  }
}
</script>