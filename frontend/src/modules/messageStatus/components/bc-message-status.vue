<template>
<div class="row row-no-margin">
  <div class="contentHeader">
      <h2>조회 > 전체</h2><span>발송일자와 수신자정보는 검색 시 필수 입니다. (수신자정보는 수신자 전화번호 또는 PUSH 앱 아이디 입니다.)</span>
      <!-- <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="메시지 상세조회 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
  </div>
  <!-- 본문 -->
  <div class="row">
    <div class="col-xs-12">
      <div class="menuBox">
        <div class="of_h">
          <div class="inline-block" style="width:8%"><h4 class="font-normal mt15">발송일자</h4></div>
          <div class="inline-block" style="width:91%">
            <Calendar @update-date="fnUpdateStartDate" calendarId="searchStartDate" classProps="datepicker inputStyle maxWidth200" :initDate="searchData.searchStartDate" ></Calendar>
            <span style="padding:0 11px">~</span>
            <Calendar @update-date="fnUpdateEndDate" calendarId="searchEndDate" classProps="datepicker inputStyle maxWidth200" :initDate="searchData.searchEndDate"></Calendar>
            <ul class="tab_s2 ml20">
                <!--<li :class="this.searchDateInterval==0 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(0);" title="오늘 날짜 발송일자 검색">전체</a></li>-->
                <li :class="this.searchDateInterval==7 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(7);" title="1주일 등록일자 검색">1주일</a></li>
                <li :class="this.searchDateInterval==15 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(15);" title="15일 등록일자 검색">15일</a></li>
                <li :class="this.searchDateInterval==30 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(30);" title="1개월 등록일자 검색">1개월</a></li>
            </ul>
          <!-- </div> -->

          </div>
        </div>

        <div class="of_h consolMarginTop">
          <div class="inline-block" style="width:8%"><h4 class="font-normal mt15">수신자정보</h4></div>
          <div class="inline-block" style="width:25%">
            <input type="text" class="inputStyle" id="searchText" name="searchText" v-model="searchData.searchText">
          </div>
          <div class="inline-block" style="width:8%;">
            <h4 class="font-normal mt15 ml50">태그</h4>
          </div>
          <div class="inline-block" style="width: 25%;">
            <input type="text" id="srcTag" name="srcTag" title="태그" v-model="searchData.srcTag"  class="inputStyle">
          </div>
        </div>
        <div class="of_h consolMarginTop">
          <div class="inline-block" style="width:8%"><h4 class="font-normal mt3">결과</h4></div>
          <div class="inline-block" style="width:91%">
            <div class="consolCheck">
              <input type="checkbox" id="searchResultYn_SUCC" class="checkStyle2" v-model="searchData.searchResultY">
              <label for="searchResultYn_SUCC" class="mr30">성공</label>
              <input type="checkbox" id="searchResultYn_FAIL" class="checkStyle2" v-model="searchData.searchResultN">
              <label for="searchResultYn_FAIL">실패</label>
            </div>
            <a @click="fnSearch()" class="btnStyle2 float-right" title="검색" activity="READ">검색</a>
          </div>
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
          <DetailLayer :title="detailLayerTitle" :layerView.sync="detailLayerView" :detailMsgKey="detailLayerMsgKey"></DetailLayer>
        </div>
      </div>

      <div class="of_h inline">
        <div class="float-left">전체 : <span class="color1"><strong>{{totCnt | formatComma}}</strong></span>건
          <SelectLayer @fnSelected="fnSelected" classProps="selectStyle2 width120 ml20"></SelectLayer>
          <span class="ml20">총건수 : {{totCnt | formatComma}}건</span>  <span class="ml10">성공건수 : {{successCnt | formatComma}}건  </span><span class="ml10">실패건수 : {{failCnt | formatComma}}건</span>
        </div>
      </div>

      <div class="row">
        <div class="col-xs-12 consolMarginTop">
          <!-- table -->
          <table class="table_skin1 bt-000 tbl-striped">
            <colgroup>
                <col style="width:10%">
                <col style="width:18%">
                <col style="width:10%">
                <col style="width:10%">
                <col style="width:10%">
                <col style="width:10%">
                <col style="width:8%">
            </colgroup>
            <thead>
              <tr>
                <th class="text-center lc-1">수신자정보</th>
                <th class="text-center lc-1">식별값</th>
                <th class="text-center lc-1">발송일시</th>
                <th class="text-center lc-1">발송구분</th>
                <th class="text-center lc-1">요청채널</th>
                <th class="text-center lc-1">발송채널</th>
                <th class="text-center lc-1 end">결과</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(data, idx) in datas" :key="data.rowNum">
                  <td class="text-center"><a @click="fnDetailPop(idx)"><u>{{data.recvUserInfo}}</u></a></td>
                  <td class="text-center">{{data.cliKey}}</td>
                  <td class="text-center">{{data.regDt}}</td>
                  <td class="text-center">{{data.sendType}}</td>
                  <td class="text-center">{{data.reqCh}}</td>
                  <td class="text-center">{{data.finalCh}}</td>
                  <td class="text-center end">
                    <span v-if="data.gwResultNm != '성공'"><a @click="fnResultDesc(data.gwResultDesc)"><u>{{data.gwResultNm}}</u></a></span>
                    <span v-if="data.gwResultNm == '성공'">{{data.gwResultNm}}</span>
                  </td>
              </tr>
              <tr v-if="datas.length == 0">
                  <td class="text-center" colspan="8">검색된 내용이 없습니다.</td>
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
import DetailLayer from '../components/bp-message-status-detail.vue';
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  components: {
    SelectLayer,
    PageLayer,
    Calendar,
    DetailLayer
  },
  props: {
    searchData : {
      type: Object,
      require: false,
      default: function() {
        return {
          'srcTag' : '',
          'searchText' : '',
          'searchStartDate' : this.$gfnCommonUtils.getCurretDate(),
          'searchEndDate' : this.$gfnCommonUtils.getCurretDate(),
          'searchSendCloud' : true,
          'searchSendAPI' : true,
          'searchResultY' : true,
          'searchResultN' : true,
        }
      }
    }
  },
  data() {
    return {
      listSize : 10,  // select 박스 value (출력 갯수 이벤트)
      pageNo : 1,  // 현재 페이징 위치
      totCnt : 0,  //전체 리스트 수
      successCnt : 0,
      failCnt : 0,
      offset : 0, //페이지 시작점
      searchDateInterval: 7,
      datas: [],
      // 팝업
      detailLayerView: false,
      detailLayerTitle: "제목",
      detailLayerMsgKey:""
    }
    
        
  },
  mounted() {
    this.fnSetIntervalSearchDate(this.searchDateInterval);
    //this.fnSearch();
  },
  methods: {
    //검색일자변경
    fnSetIntervalSearchDate(interval){
      this.searchDateInterval = interval;
      if(interval == 0) {
        this.searchData.searchEndDate = "";
        this.searchData.searchStartDate = "";
      } else {
        this.searchData.searchEndDate = this.$gfnCommonUtils.getCurretDate();
        this.searchData.searchStartDate = this.$gfnCommonUtils.strDateAddDay(this.searchData.searchEndDate, -this.searchDateInterval);
      }
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
      messageStatusApi.excelDownloadMessageStatus(params);
    },
    
    // 검색
    async fnSelectMessageStatusList() {   
      var params = Object.assign({}, this.searchData);
      params.pageNo = this.pageNo;
      params.listSize = this.listSize;

      params.loginId = tokenSvc.getToken().principal.userId;
      params.roleCd = tokenSvc.getToken().principal.roleCd;
      var searchData = this.searchData;
      var searchSendCloud = searchData.searchSendCloud;
      var searchSendAPI = searchData.searchSendAPI;
      var searchResultY = searchData.searchResultY;
      var searchResultN = searchData.searchResultN;
      var searchText = searchData.searchText;

      if(searchSendCloud == false && searchSendAPI == false) {
        confirm.fnAlert("", "발송 구분을 선택해주세요.");
        return false;
      }

      if(searchResultY == false && searchResultN == false) {
        confirm.fnAlert("", "결과를 선택해주세요.");
        return false;
      }

      if(searchText == "") {
        confirm.fnAlert("", "수신자정보를 입력해주세요.");
        return false;
      }

      // 전체 검색조건 추가에 따른 validation 처리 (등록일자 검색조건을 하나만 입력하는 경우)
      if(this.searchData.searchStartDate=="" && this.searchData.searchEndDate!="") {
        confirm.fnAlert("", "발송일자를 올바르게 입력해 주세요.");
        return false;
      }

      if(this.searchData.searchStartDate!="" && this.searchData.searchEndDate=="") {
        confirm.fnAlert("", "발송일자를 올바르게 입력해 주세요.");
        return false;
      }


      await messageStatusApi.selectMessageStatusList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.datas = result.data;
          this.totCnt = result.pageInfo.totCnt;
          this.successCnt = result.pageInfo.successCnt;
          this.failCnt = result.pageInfo.failCnt;
          this.offset = result.pageInfo.offset;
        } else { 
          confirm.fnAlert("", result.message);
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
      this.fnSelectMessageStatusList();
    },

    // 현황상세 팝업
    fnDetailPop(row) {
        this.detailLayerView = true;
        this.detailLayerTitle = "메시지현황 상세";
        this.detailLayerMsgKey = this.datas[row].msgKey; 
        
      },
    fnResultDesc(desc){
      confirm.fnAlert("",desc);
    }
  }
}
</script>