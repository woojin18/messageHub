<template>
<div class="row row-no-margin">
  <div class="contentHeader">
      <h2> > MO 수신현황</h2>
      <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="MO 수신현황 이용안내">이용안내 <i class="fal fa-book-open"></i></a>
  </div>
  <!-- 본문 -->
  <div class="row">
    <div class="col-xs-12">
      <div class="menuBox">
        <div class="of_h">
          <div class="inline-block" style="width:8%"><h4 class="font-normal mt15">수신일자</h4></div>
          <div class="inline-block" style="width:91%">
            <Calendar @update-date="fnUpdateStartDate" calendarId="searchStartDate" classProps="datepicker inputStyle maxWidth200" :initDate="searchData.searchStartDate" ></Calendar>
            <span style="padding:0 11px">~</span>
            <Calendar @update-date="fnUpdateEndDate" calendarId="searchEndDate" classProps="datepicker inputStyle maxWidth200" :initDate="searchData.searchEndDate"></Calendar>
            <ul class="tab_s2 ml20">
                <li :class="this.searchDateInterval==0 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(0);" title="오늘 날짜 등록일자 검색">오늘</a></li>
                <li :class="this.searchDateInterval==7 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(7);" title="1주일 등록일자 검색">1주일</a></li>
                <li :class="this.searchDateInterval==15 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(15);" title="15일 등록일자 검색">15일</a></li>
                <li :class="this.searchDateInterval==30 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(30);" title="1개월 등록일자 검색">1개월</a></li>
            </ul>
          </div>  
        </div>

        <div class="consolMarginTop">
          <h4 class="inline-block" style="width:8%">수신번호 : </h4>
          <div class="inline-block" style="width:29%" ><!-- <span v-html="receptionNumber"></span> -->
            <div v-for="(data, idx) in receptionDatas" :key="idx">
              <input type="checkbox" id="searchReceptionNumber" name="searchReceptionNumber" class="checkStyle2" :value="data.moNumber" v-model="searchData.searchReceptionNumber">
              <label for="searchReceptionNumber">{{ data.moNumber }}</label>
            </div>
            <!-- <input type="checkbox" id="searchReceptionNumber" name="searchReceptionNumber" class="checkStyle2" value="1111" v-model="searchData.searchReceptionNumber"><label for="searchReceptionNumber">1111</label>
            <input type="checkbox" id="searchReceptionNumber" name="searchReceptionNumber" class="checkStyle2" value="2222" v-model="searchData.searchReceptionNumber"><label for="searchReceptionNumber">2222</label> -->
          </div>
          <h4 class="inline-block" style="width:8%">발신번호</h4>
          <input type="text" class="inputStyle ml10" id="searchSendNumber" name="searchSendNumber" v-model="searchData.searchSendNumber" style="width:37.5%" title="발신번호">
        </div>	
        <div class="consolMarginTop">
          <h4 class="inline-block" style="width:8%">상태</h4>
          <select v-model="searchData.searchCondi"  class="selectStyle2" style="width:15%" title="상태 선택란">
            <option v-for="data in condiDatas" :key="data.codeName" :value="data.codeName">{{data.codeVal}}</option>
          </select>
          <a @click="fnSearch()" class="btnStyle1 float-right" title="검색" activity="READ">검색</a>
        </div>

      </div>
    </div>
  </div>


  <!-- 리스트 -->
  <div class="row">
    <div class="col-xs-12 consolMarginTop">
      <div class="of_h inline">
        <div class="float-right">
          <a @click="fnExcelDownLoad" class="btnStyle2 borderGray" title="엑셀 다운로드" activity="READ">엑셀 다운로드 <i class="fal fa-arrow-to-bottom"></i></a>
        </div>
      </div>

      <!-- 15개씩 보기 -->
      <div class="of_h inline">
        <div class="float-left">전체 : <span class="color1"><strong>{{totCnt}}</strong></span>건
          <SelectLayer @fnSelected="fnSelected"></SelectLayer>
        </div>
      </div>
      <!-- //15개씩 보기 -->

      <div class="row">
        <div class="col-xs-12 consolMarginTop">
          <!-- table -->
          <table class="table_skin1 bt-000 tbl-striped">
            <colgroup>
                <col style="width:5%">
									<col>
									<col style="width:15%">
									<col style="width:15%">
									<col style="width:8%">
									<col style="width:8%">
									<col style="width:8%">
									<col style="width:11%">
									<col style="width:18%">
            </colgroup>
            <thead>
              <tr>
                <th class="text-center lc-1">No.</th>
                  <th class="text-center lc-1">MO 타입</th>
                  <th class="text-center lc-1">MO 수신번호</th>
                  <th class="text-center lc-1">MO 발신번호</th>
                  <th class="text-center lc-1">상품코드</th>
                  <th class="text-center lc-1">MO 제목</th>
                  <th class="text-center lc-1">이통사</th>
                  <th class="text-center lc-1">상태</th>
                  <th class="text-center lc-1 end">등록일자</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(data, idx) in datas" :key="data.row_num">
                  <td>{{ idx + 1 }}</td>
                  <td class="text-center">{{data.moType}}</td>
                  <td class="text-center">{{data.moNumber}}</td>
                  <td class="text-center">{{data.moCallback}}</td>
                  <td class="text-center">{{data.productCode}}</td>
                  <td class="text-center">{{data.moTitle}}</td>
                  <td class="text-center">{{data.telco}}</td>
                  <td class="text-center">{{data.codeVal}}</td>
                  <td class="text-center end">{{data.moRecvDt}}</td>
              </tr>
              <tr v-if="datas.length == 0">
                  <td class="text-center" colspan="9">검색된 내용이 없습니다.</td>
              </tr>
            </tbody>
          </table>
          <!-- //table -->
        </div>                  
      </div>
    </div>
  </div>
  <!-- pagination -->
  <div id="pageContent">
      <PageLayer @fnClick="fnSearch" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
  </div>
</div>
</template>


<script>
import messageStatusApi from '../service/messageStatusApi'
import PageLayer from '@/components/PageLayer.vue';
import SelectLayer from '@/components/SelectLayer.vue';
import Calendar from "@/components/Calendar.vue";
import tokenSvc from '@/common/token-service';

export default {
  components: {
    SelectLayer,
    PageLayer,
    Calendar
  },
  props: {
    searchData : {
      type: Object,
      require: false,
      default: function() {
        return {
          'searchCondi' : '',
          'searchSendNumber' : '',
          'searchStartDate' : this.$gfnCommonUtils.getCurretDate(),
          'searchEndDate' : this.$gfnCommonUtils.getCurretDate(),
          'searchReceptionNumber' : []
        }
      }
    }
  },
  data() {
    return {
      resultYnAllSelected: false,
      listSize : 10,  // select 박스 value (출력 갯수 이벤트)
      pageNo : 1,  // 현재 페이징 위치
      totCnt : 0,  //전체 리스트 수
      offset : 0, //페이지 시작점
      searchDateInterval: 7,
      datas: [],
      condiDatas: [],
      receptionDatas: [],
      receptionNumber: '',
      //testProjectId: '313431323336706A74' //test용 projectId 관리방법이 정해지면 변경필요 (세션이 좋을듯)
    }
    
        
  },
  mounted() {
    this.fnSetIntervalSearchDate(this.searchDateInterval);
    this.fnSearchCondi();
    this.fnSearchReceptionNumber();
    this.fnSearch();
  },
  methods: {
    //검색일자변경
    fnSetIntervalSearchDate(interval){
      this.searchDateInterval = interval;
      this.searchData.searchEndDate = this.$gfnCommonUtils.getCurretDate();
      this.searchData.searchStartDate = this.$gfnCommonUtils.strDateAddDay(this.searchData.searchEndDate, -this.searchDateInterval);
    },
    fnUpdateStartDate(sltDate) {
      this.searchData.searchStartDate = sltDate;
    },
    fnUpdateEndDate(sltDate) {
      this.searchData.searchEndDate = sltDate;
    },

    //엑셀 다운로드
    fnExcelDownLoad(){
      var params = this.searchData;
      //params.projectId = this.testProjectId;
      messageStatusApi.excelDownloadMoReceptionStatus(params);
    },

    // 검색
    async fnSelectMoReceptionStatusList() {

      //유효성 검사
      if(this.searchData.searchStartDate && this.searchData.searchEndDate){
        if(this.searchData.searchStartDate.replace(/[^0-9]/g, '') > this.searchData.searchEndDate.replace(/[^0-9]/g, '')){
          alert('시작일은 종료일보다 클 수 없습니다.');
          return false;
        }
      }
          
      var params = Object.assign({}, this.searchData);
      params.pageNo = this.pageNo;
      params.listSize = this.listSize;

      params.loginId = tokenSvc.getToken().principal.userId;
      params.roleCd = tokenSvc.getToken().principal.roleCd;
      //params.projectId = this.testProjectId;

      await messageStatusApi.selectMoReceptionStatusList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.datas = result.data;
          //this.totCnt = result.pageDto.totCnt;
          //this.offset = result.pageDto.offset;
          this.totCnt = result.pageInfo.totCnt;
          this.offset = result.pageInfo.offset;
        } else {
          alert(result.message);
        }
      });
    },
 
    // select 박스 선택시 리스트 재출력
    fnSelected(listSize) {
      this.listSize = Number(listSize);
      this.$refs.updatePaging.fnAllDecrease();
    },
    fnSearch(pageNum) {
      this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNum, '1'))*1;
      this.fnSelectMoReceptionStatusList();
    },

    fnSearchCondi(){
      this.selectConditionList();
    },
 

    async selectConditionList(){
      var params = {};
      await messageStatusApi.selectConditionList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.condiDatas = result.data;
          //console.log(">>>>"+this.condiDatas[0].codeName);
        } else {
          alert(result.message);
        }
      });
    },


    fnSearchReceptionNumber(){
      this.selectReceptionNumber();
    },
 

    async selectReceptionNumber(){
       var params = {};
      //  "projectId" : this.testProjectId 
      //};
      await messageStatusApi.selectReceptionNumberList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.receptionDatas = result.data;
          this.receptionNumber = result.data[0].receptionNumber;
          console.log("receptionNumber >>>>"+ this.receptionNumber);
        } else {
          alert(result.message);
        }
      });
    }


  }
}
</script>