<template>
  <div>
    <modal 
      :modal_title.sync="modal_title" 
      :visible.sync="visible"
      :save_status.sync="save_status"
    >
    </modal>
    <div class="contentHeader">
      <h2>프로젝트 목록</h2>
      <button @click="fnSearch"><a class="btnStyle1 absolute top0 right0">조회</a></button>
    </div>

    <!-- 검색창 -->
    <div class="row">
      <div class="col-xs-12">
        <div class="menuBox">
          <div class="mt20">
            <h4 class="font-normal inline-block width90 ml120">
              <select id="src_brand_type">
                <option value="brand_id">브랜드 아이디</option>
                <option value="brand_name">브랜드 아이디</option>
              </select>
            </h4>
            <input type="text" class="inputStyle maxWidth350" id="src_brand_text">
            
            <span class="custom_input small">
              <SelectLayer @fnSelected="fnSelected"></SelectLayer>
            </span>
          </div>
        </div>
      </div>
    </div>
    <!-- 검색창 -->

    <!-- 그리드 -->
    <div class="row mt20 row-no-margin borderC2">
      <div class="col-xs-12">
        <div class="of_h">
          <div class="float-right">
            <!-- <button @click="fnAdminUserRoleAdd"><a href="#self" class="btnStyle3 black font13 minWidth120 ml10">권한 사용자 추가</a></button> -->
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
                <a href="javascript:void(0);" @click="fnUpdateListPop(index)">{{ data.COL1 }}</a>
              </td>
              <td>
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
                {{ data.MEM_CNT }}
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
    <!-- 그리드 -->
    <!-- 페이징 -->
    <div id="pageContent">
      <PageLayer @fnClick="fnClick" :listTotalCnt="count" :selected="selected" ref="updatePaging" > </PageLayer>
    </div>
    <!-- 페이징 -->
  </div>
</template>


<script>
import Api from '../service/api'
import SelectLayer from '@/components/SelectLayer.vue';
import PageLayer from '@/components/PageLayer.vue';
import modal from "./bp-project-manage-detail.vue";

export default {
  components: {
      SelectLayer
    , PageLayer
    , modal
  },
  data() {
    return {
      visible : false,  // 레이어 팝업 visible
      modal_title : '', // 레이어 팝업 타이틀
      save_status : '', // 등록 수정 여부
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
    /* 
    $("#list").DataTable({
      columns: this.columns,
      data: this.data,
      paging : false, // 페이징
      info : false,   // 셀렉트 등의 정보
      searching : false,  // 검색창
      select :{
        style : 'single' // multi
      }
    });

    $('#list').on('click', '.clickClass', function(){
      var rowData = $("#list").DataTable().row(this).data();
      vm.roleNm = rowData.ROLE_NAME;
      vm.fnSelectUserRoles(rowData);
    }); */

    /* this.fnSearchRoles(); */
  },
  methods: {
    // 검색
    fnSearch() {
      
      var vm = this;
      var params = {
        "src_brand_type"  : $("#src_brand_type").val(),
        "src_brand_text"  : $("#src_brand_text").val(),
        "rows"            : vm.selected,
        "paging"          : vm.pagingCnt
      }
       
      Api.selectProjectList(params).then(response =>{
        vm.items = response.data.data;
      });
    },
    // 등록창
    fnAdminUserRoleAdd : function(){
        // 선택한 권한
        var roleData = $("#list").DataTable().row( {selected:true} ).data();

        if( roleData == null || roleData == undefined ){
          alert("사용자를 추가할 권한을 먼저 선택해주세요");
          return;
        }

        this.visible = !this.visible;
        this.modal_title = '권한 사용자 등록';
        this.roleData = roleData;
      },
    // 리스트 등록 팝업
    fnInsertListPop() {
      /* this.layerView = true;
      this.layerTitle = "리스트 등록"; */
    },
    // 리스트 수정 팝업
    fnUpdateListPop(row) {
      /* this.updateLayerView = true;
      this.updateLayerTitle = "리스트 수정";
      this.updateLayerSeq = this.items[row].SEQ;  */
    },

    // select 박스 선택시 리스트 재출력
    fnSelected(selected) {
      this.selected = selected;
      this.$refs.updatePaging.fnAllDecrease();
    },
    // page 선택
    fnClick(pageNum) {
      this.pagingCnt = pageNum;
      this.fnSearch();
    }
  }
}
</script>