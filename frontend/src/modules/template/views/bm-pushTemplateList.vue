<template>
  <!-- content -->
  <div id="content">
    <article>
      <div class="contentHeader">
        <h2>푸시 템플릿</h2>
        <!-- <a href="#self" class="btnStyle1 absolute top0 right0" onClick="window.location.reload()" title="푸시 템플릿 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
      </div>

      <!-- 본문 -->
      <div class="row">
        <div class="col-xs-12">
          <div class="menuBox">
            <div class="of_h">
              <div class="float-left" style="width:8%"><h4 class="font-normal mt15">검색조건</h4></div>
              <div class="float-left" style="width:92%">
                <select v-model="searchData.searchCondi" class="selectStyle3 height45" style="width:15%" title="검색조건 선택란">
                  <option value="tmpltName">템플릿명</option>
                  <option value="tmpltId">템플릿ID</option>
                </select>
                <input type="text" class="inputStyle vertical-top ml10" id="searchText" name="searchText" v-model="searchData.searchText" style="width:37.5%" title="검색조건 입력란">
              </div>
            </div>

            <div class="of_h mt20">
              <div class="float-left" style="width:8%"><h4 class="font-normal mt15">등록일자</h4></div>
              <div class="float-left" style="width:92%">
                <input type="text" class="datepicker inputStyle maxWidth200 hasDatepicker" title="시작날짜 입력란" id="searchStartDate" name="searchStartDate" v-model="searchData.searchStartDate">
                <span style="padding:0 11px">~</span>
                <input type="text" class="datepicker inputStyle maxWidth200 hasDatepicker" title="마지막날짜 입력란" id="searchEndDate" name="searchEndDate" v-model="searchData.searchEndDate">
                <ul class="tab_s2 ml20">
                  <li><a href="#" title="오늘 날짜 등록일자 검색">오늘</a></li>
                  <li class="active"><a href="#" title="1주일 등록일자 검색">1주일</a></li>
                  <li><a href="#" title="15일 등록일자 검색">15일</a></li>
                  <li><a href="#" title="1개월 등록일자 검색">1개월</a></li>
                </ul>
              </div>
            </div>

            <div class="of_h mt20">
              <div class="float-left" style="width:13%"><h4 class="font-normal mt3">타 프로젝트<br/>사용여부</h4></div>
              <div class="float-left" style="width:87%">
                <div class="consolCheck vertical-top">
                  <input type="checkbox" id="otherUse_all" class="checkStyle2" @change="fnSearchOthPrjUseYnChkAll" v-model="othPrjUseYnAllSelected">
                  <label for="otherUse_all" class="mr30">전체</label>
                  <input type="checkbox" id="searchOthPrjUseYn_BASE" class="checkStyle2" value="Y" v-model="searchData.searchOthPrjUseYn">
                  <label for="searchOthPrjUseYn_BASE" class="mr30">공용</label>
                  <input type="checkbox" id="searchOthPrjUseYn_IMAGE" class="checkStyle2" value="N" v-model="searchData.searchOthPrjUseYn">
                  <label for="searchOthPrjUseYn_IMAGE">전용</label>
                </div>
                <a @click="fnSearch" class="btnStyle2 float-right" title="통합발송 검색">검색</a>  
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <div class="of_h mt20">
        <div class="float-right">
          <router-link :to="{ name: 'pushTemplateManage' }" tag="a" class="btnStyle3 black font13 minWidth120 mr10" title="엑셀 다운로드">템플릿 등록</router-link>
          <a @click="fnDeletePushTemplate" class="btnStyle3 gray font13 minWidth120 mr10" title="엑셀 다운로드">삭제</a>
          <a @click="fnExcelDownLoad" class="btnStyle3 gray font13 minWidth120" title="엑셀 다운로드">엑셀 다운로드 <i class="fal fa-arrow-to-bottom"></i></a>
        </div>
      </div>

      <div class="row">
        <div class="col-xs-12">
          
          <!-- 15개씩 보기 -->
          <div class="of_h mb20">
            <div class="float-left">전체 : <span class="color1"><strong>{{totCnt}}</strong></span>건
              <SelectLayer @fnSelected="fnSelected"></SelectLayer>
            </div>
          </div>
          <!-- //15개씩 보기 -->

          <!-- table -->
          <table class="table_skin1 bt-000 tbl-striped">
            <colgroup>
              <col style="width:5%">
              <col>
              <col style="width:17%">
              <col style="width:14%">
              <col style="width:15%">
              <col style="width:10%">
              <col style="width:10%">
              <col style="width:8%">
              <col style="width:19%">
            </colgroup>
            <thead>
              <tr>
                <th class="text-center lc-1">
                  <input type="checkbox" id="listCheck_all" class="boardCheckStyle" @change="fnListChkAll" v-model="listAllChecked">
                  <label for="listCheck_all"></label>
                </th>
                <th class="text-center lc-1">No.</th>
                <th class="text-center lc-1">템플릿 ID</th>
                <th class="text-center lc-1">템플릿명</th>
                <th class="text-center lc-1">타 프로젝트 사용여부</th>
                <th class="text-center lc-1">메시지타입</th>
                <th class="text-center lc-1">메시지구분</th>
                <th class="text-center lc-1">등록자</th>
                <th class="text-center lc-1 end">등록일자</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(contant, idx) in contants" v-bind:key="contant">
                <td class="text-center">
                  <input type="checkbox" :id="'listCheck_'+idx" class="boardCheckStyle" :value="contant.tmpltId" v-model="listChkBox">
                  <label :for="'listCheck_'+idx"></label>
                </td>
                <td class="text-center">{{totCnt-offset-contant.rownum+1}}</td>
                <td class="text-center">
                  <router-link :to="{ name: 'pushTemplateManage', params: { rowData: {'tmpltId' : contant.tmpltId} }}">{{contant.tmpltId}}</router-link>
                </td>
                <td class="text-center">{{contant.tmpltName}}</td>
                <td class="text-center">{{contant.otherProjectUseYn}}</td>
                <td class="text-center">{{contant.msgType}}</td>
                <td class="text-center">{{contant.msgKind}}</td>
                <td class="text-center">{{contant.regId}}</td>
                <td class="text-center end">{{contant.regDt}}</td>
              </tr>
            </tbody>
          </table>
          <!-- //table -->
        </div>
      </div>

      <!-- pagination -->
      <div id="pageContent">
        <PageLayer @fnClick="fnClick" :listTotalCnt="totCnt" :selected="selected" ref="updatePaging"></PageLayer>
      </div>
      
      <!-- //본문 -->
    </article>
  </div>
  <!-- //content -->
</template>

<script>
import TemplateApi from "@/modules/template/service/templateApi.js";
import PageLayer from '@/components/PageLayer.vue';
import SelectLayer from '@/components/SelectLayer.vue';

export default {
  name: "pushTemplateList",
  components: {
    SelectLayer,
    PageLayer
  },
  props: {
    searchData : {
      type: Object,
      require: false,
      default: function() {
        return {
          'searchCondi' : 'tmpltName',
          'searchText' : '',
          'searchStartDate' : '2021-03-30',
          'searchEndDate' : '2021-04-01',
          'searchOthPrjUseYn' : []
        }
      }
    },
    contants: []
  },
  mounted() {
    this.fnSearch();
  },
  data() {
    return {
      othPrjUseYnAllSelected: false,
      listAllChecked: false,
      listChkBox: [],
      selected : 10,  // select 박스 value (출력 갯수 이벤트)
      pagingCnt : 1,  // 현재 페이징 위치
      totCnt : 0,  //전체 리스트 수
      offset : 0  //페이지 시작점
    }
  },
  methods: {
    //푸시 템플릿 엑셀 다운로드
    fnExcelDownLoad(){
      var params = this.searchData;
      TemplateApi.excelDownloadPushTmplt(params);
    },
    //푸시 템플릿 삭제
    async fnDeletePushTemplate(){
      //유효성 검사
      if(this.listChkBox == null || this.listChkBox.length == 0){
        alert('삭제할 항목을 선택해주세요.');
        return false;
      }

      if(confirm('선택한 템플릿을 삭제하시겠습니까?') == false) return;

      var params = {tmpltIds : this.listChkBox};
      await TemplateApi.deletePushTmplt(params).then(response =>{
        var result = response.data;
        if(result.success) {
          alert('삭제되었습니다.');
          this.listChkBox = [];
          this.fnSearch();
        } else {
          alert(result.message);
        }
      });
    },
    //푸시 템플릿 리스트 검색
    async fnSearch(){
      //유효성 검사
      if(this.searchData.searchStartDate && this.searchData.searchEndDate){
        if(this.searchData.searchStartDate.replace(/[^0-9]/g, '') > this.searchData.searchEndDate.replace(/[^0-9]/g, '')){
          alert('시작일은 종료일보다 클 수 없습니다.');
          return false;
        }
      }

      var params = Object.create(this.searchData);
      params.pageNo = this.pagingCnt;
      params.listSize = this.selected;

      await TemplateApi.selectPushTmpltList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.contants = result.data;
          this.totCnt = result.pageInfo.totCnt;
          this.offset = result.pageInfo.offset;
        } else {
          alert(result.message);
        }
      });
    },
    //타 프로젝트 사용여부 전체 선택시
    fnSearchOthPrjUseYnChkAll(){
      if(this.othPrjUseYnAllSelected){
        this.searchData.searchOthPrjUseYn = ['Y', 'N'];
      } else {
        this.searchData.searchOthPrjUseYn = [];
      }
    },
    //리스트 전체 체크박스 
    fnListChkAll(){
      var vm = this;
      if(this.listAllChecked){
        this.contants.forEach(function(contant){
          vm.listChkBox.push(contant.tmpltId);
        });
      } else {
        this.listChkBox = [];
      }
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
      //this.$refs.updatePaging.fnAllDecrease();
    }
  }
}
</script>