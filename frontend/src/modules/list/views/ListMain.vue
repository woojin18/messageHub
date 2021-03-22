<template>
  <div class="home">
    <br>
    <ListMain msg="ListMain" />
    <div id="serachDiv">
      <tr>
         <ul class="input_area">
          <li>
            <span class="custom_input small">
              <input type="text" placeholder="col1" name="col1" v-model="col1" :maxlength="15" v-focus @keyup.enter="fnSearch">
            </span>
            <span class="custom_input small">
              <input type="text" placeholder="col2" name="col2" v-model="col2" :maxlength="20" @keyup.enter="fnSearch">
            </span>
            <span class="custom_input small">
              <SelectLayer @fnSelected="fnSelected"></SelectLayer>
            </span>
              <a href="javascript:void(0);" class="btn big point on" @click.prevent="fnSearch"><span>검색</span></a>
          </li>
        </ul>
      </tr>
    </div>
    <div>
      <button @click="fnInsertListPop">등록</button>
      <input type="file" @change="readFile" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" />
      <button @click="fnExcelDownload">엑셀다운로드(axios)</button>
      <button @click="fnXlsxDownload">엑셀다운로드(.xlsx)</button>
      <a href="/listCtl/excelDownload">test</a>
      <InsertLayer :title="layerTitle" :layerView.sync="layerView"></InsertLayer>
      <UpdateLayer :title="updateLayerTitle" :layerView.sync="updateLayerView" :updateSeq="updateLayerSeq"></UpdateLayer>
      </div>
    <div id="test">
      <table>
        <thead>
          <th>No.</th>
          <th>COL1</th>
          <th>COL2</th>
          <th>COL3</th>
          <th>COL4</th>
          <th>COL5</th>
          <th>COL6</th>
        </thead>
        <tbody>
          <tr v-for="(data, index) in items">
            <td>
              {{ index + 1 }}
            </td>
            <td>
              <a href="javascript:void(0);" @click="fnUpdateListPop(index)">{{ data.COL1 }}</a>
            </td>
            <td>
              {{ data.COL2 }}
            </td>
            <td>
              {{ data.COL3 }}
            </td>
            <td>
              {{ data.COL4 }}
            </td>
            <td>
              {{ data.COL5 }}
            </td>
            <td>
              {{ data.COL6 }}
            </td>
            <td>
              {{ data.SEQ }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div id="pageContent">
      <PageLayer @fnClick="fnClick" :listTotalCnt="count" :selected="selected" ref="updatePaging" > </PageLayer>
    </div>

  </div>
</template>

<script>
  // @ is an alias to /src
  import ListMain from '../components/ListMain.vue';
  import PageLink from '../components/pageLink.vue';
  import InsertLayer from '../components/InsertLayer.vue';
  import UpdateLayer from '../components/UpdateLayer.vue';
  import SelectLayer from '@/components/SelectLayer.vue';
  import PageLayer from '@/components/PageLayer.vue';
  import listApi from "@/modules/list/service/api";
  import XLSX from 'xlsx';

  export default {
    components: {
      ListMain,
      PageLink,
      InsertLayer,
      UpdateLayer,
      SelectLayer,
      PageLayer
    },
    data () {
      return {
        // 검색 조건
        col1 : "",
        col2 : "",
        // select 박스 value (출력 갯수 이벤트)
        selected : 10,
        // 현재 페이징 위치
        pagingCnt : 1,
        // 리스트 
        items : [],
        count : "",
        // 엑셀 업로드 Json 변환 데이터
        excelJsonData : [],
        // 팝업
        layerView: false,
        layerTitle: "제목",
        updateLayerView: false,
        updateLayerTitle: "제목",
        updateLayerSeq:""
      }
    },
    created() {
      // 최초 검색
      this.fnSearch();
    },
    methods: {
      // 검색
      fnSearch() {
        
        var vm = this;
        var params = {
          "col1": vm.col1,
          "col2": vm.col2,
          "rows" : vm.selected,
          "paging" : vm.pagingCnt
        }
        listApi.fnSearch(params).then(function(response) {
          vm.items = response.data.data.listData;
          vm.count = response.data.data.listDataCnt;
        });
      },
      // 리스트 등록 팝업
      fnInsertListPop() {
        this.layerView = true;
        this.layerTitle = "리스트 등록";
      },
      // 리스트 수정 팝업
      fnUpdateListPop(row) {
        this.updateLayerView = true;
        this.updateLayerTitle = "리스트 수정";
        this.updateLayerSeq = this.items[row].SEQ; 
      },

      // 엑셀 업로드 function
      readFile(event) {
      const file = event.target.files[0];
      let reader = new FileReader();
      let tmpResult = {};
      reader.onload = (e) => {
          let data = reader.result;
          let workbook = XLSX.read(data, {type: 'binary'});
          workbook.SheetNames.forEach(sheetName => {

            // sheetname 변경
              workbook.Sheets[sheetName].A1.w = "col1";
              workbook.Sheets[sheetName].B1.w = "col2";
              workbook.Sheets[sheetName].C1.w = "col3";
              workbook.Sheets[sheetName].D1.w = "col4";
              workbook.Sheets[sheetName].E1.w = "col5";
              workbook.Sheets[sheetName].F1.w = "col6";
            
              const roa = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName]);
              tmpResult = roa;
          });

          this.excelJsonData=tmpResult;
          this.fnExcelUpload();
      };
      reader.readAsBinaryString(file);
      },

      // json data 바인딩 후 실제로 backend에서 로직 처리하는 axios
      fnExcelUpload() {
        var params = {
          "excelJsonData": this.excelJsonData
        }
        listApi.excelUpload(params).then(function(response) {
          var result = response.data.success;
          if(result) {
            alert("upload 완료");
          } else {
            alert("upload 실패");
          }
        });
        this.fnSearch();
      },

      // 엑셀 다운로드 
      fnExcelDownload() {
        var vm = this;
        var params = {
          "col1": vm.col1,
          "col2": vm.col2,
          "rows" : vm.selected,
          "paging" : vm.pagingCnt
        }
        listApi.excelDownload(params)
      },
      // 엑셀 다운로드 
      fnXlsxDownload() {
        var colLabels = ["No", "컬럼1", "컬럼2", "컬럼3", "컬럼4", "컬럼5", "컬럼6"];

        var colIds = ["SEQ", "COL1", "COL2", "COL3", "COL4", "COL5", "COL6"];
        
        var numColIds = ["COL3"];
        var sheetTitle = "엑셀 다운로드 샘플";
        var excelFileName = "Excel Sample";
        var figureColIds = [];
        var fieldSearchParamArray = new Array();
          fieldSearchParamArray[0] = "col1";
          fieldSearchParamArray[1] = "col2";
    
       this.$fnExportExcelToSvc(colLabels, colIds, numColIds, sheetTitle, excelFileName, "/listCtl/xlsxDownload", fieldSearchParamArray , figureColIds);
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
    },
  };
</script>
