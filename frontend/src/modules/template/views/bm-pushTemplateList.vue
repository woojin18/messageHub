<template>
  <div>
    <hr/>
    <span>푸시 템플릿 관리</span>

    <!-- 검색영역 -->
    <hr/>
    <span>검색조건</span>
    <select v-model="searchData.searchCondi">
      <option value="tmpltName">템플릿명</option>
      <option value="tmpltId">템플릿ID</option>
    </select>
    <input id="searchText" name="searchText" v-model="searchData.searchText">
    <br/>
    <span>등록일자</span>
    <input id="searchStartDate" name="searchStartDate" v-model="searchData.searchStartDate">
    <input id="searchEndDate" name="searchEndDate" v-model="searchData.searchEndDate">
    <br/>
    <span>타 프로젝트 사용여부</span>
    <br/>
    <button :class="othPrjUseYnAllSelected ? 'bg-red' : ''" @click="fnSearchOthPrjUseYnChkAll">전체</button>
    <input type="checkbox" id="searchOthPrjUseYn_BASE" value="Y" v-model="searchData.searchOthPrjUseYn">
    <label for="searchOthPrjUseYn_BASE">공용</label>
    <input type="checkbox" id="searchOthPrjUseYn_IMAGE" value="N" v-model="searchData.searchOthPrjUseYn">
    <label for="searchOthPrjUseYn_IMAGE">전용</label>
    <br/>
    <button @click="fnSearch">검색</button>
    <hr/>
    <!--// 검색영역 -->

    <!-- 리스트영역 -->
    <router-link :to="{ name: 'pushTemplateManage' }" tag="button">템플릿등록</router-link>
    <button @click="fnDeletePushTemplate">삭제</button>
    <button  @click="fnExcelDownLoad">다운로드</button>

    <div>
      <table id="list">
        <thead>
          <tr>
            <th><input type="checkbox" @change="fnListChkAll" v-model="listAllChecked"></th>
            <th>템플릿 ID</th>
            <th>템플릿명</th>
            <th>타 프로젝트 사용여부</th>
            <th>메시지타입</th>
            <th>메세지구분</th>
            <th>등록자</th>
            <th>등록일시</th>
          </tr>
        </thead>
        <tbody id="contants">
          <tr v-for="contant in contants" v-bind:key="contant">
            <td>
              <input type="checkbox" :value="contant.tmpltId" v-model="listChkBox">
            </td>
            <td><router-link :to="{ name: 'pushTemplateManage', params: { rowData: {'tmpltId' : contant.tmpltId} }}">{{contant.tmpltId}}</router-link></td>
            <td>{{contant.tmpltName}}</td>
            <td>{{contant.otherProjectUseYn}}</td>
            <td>{{contant.msgType}}</td>
            <td>{{contant.msgKind}}</td>
            <td>{{contant.regId}}</td>
            <td>{{contant.regDt}}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <!--// 리스트영역 -->


  </div>
</template>

<script>
import TemplateApi from "@/modules/template/service/templateApi.js";

export default {
  name: "pushTemplateList",
  props: {
    searchData : {
      type: Object,
      require: false,
      default: function() {
        return {
          'searchCondi' : 'tmpltName',
          'searchText' : '',
          'searchStartDate' : '2021-03-30',
          'searchEndDate' : '2021-03-31',
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
      listChkBox: []
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

      var params = this.searchData;
      await TemplateApi.selectPushTmpltList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.contants = result.data;
        } else {
          alert(result.message);
        }
      });
    },
    //타 프로젝트 사용여부 전체 선택시
    fnSearchOthPrjUseYnChkAll(){
      this.othPrjUseYnAllSelected = !this.othPrjUseYnAllSelected;
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
    }
  }
}
</script>

<style lang="scss">
.bg-red {
  background-color: red;
}
input, table, th, td {
  border: 1px solid black;
}
</style>