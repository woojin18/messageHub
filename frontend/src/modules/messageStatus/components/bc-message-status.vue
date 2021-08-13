<template>
<div class="row row-no-margin">
  <div class="contentHeader">
      <h2>메시지 상세조회</h2>
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
            <ul class="tab_s2 ml20">
                <li :class="this.searchDateInterval==0 ? 'active' : ''"><a @click="fnSetIntervalSearchDate();" title="오늘 날짜 발송일자 검색">오늘</a></li>
            </ul>
          <!-- </div> -->

            <!-- <div class="inline-block " style="width:8%"><h4 class="font-normal mt3">발송구분</h4></div> -->
            <h4 class="inline-block vertical-middle ml30 mr20">발송구분 :</h4>
            <div class="inline-block" style="width:27%">
                <!-- <div class="consolCheck vertical-middle"> -->
                    <input type="checkbox" id="searchSendFlag_CLOUD" class="checkStyle2" v-model="searchData.searchSendCloud">
                    <label for="searchSendFlag_CLOUD" class="mr30">클라우드발송</label>
                    <input type="checkbox" id="searchSendFlag_API" class="checkStyle2" v-model="searchData.searchSendAPI">
                    <label for="searchSendFlag_API">API발송</label>
                <!-- </div> -->
            </div>
          </div>
        </div>

        <div class="of_h consolMarginTop">
          <div class="inline-block" style="width:8%"><h4 class="font-normal mt15">검색조건</h4></div>
          <div class="inline-block" style="width:91%">
            <select v-model="searchData.searchCondi" class="selectStyle2" style="width:15%" title="수신자정보 검색조건">
                <option value="receiverPhone">수신자 전화번호</option>
                <option value="receiverId">앱아이디</option>
            </select>
            <input type="text" class="inputStyle vertical-top ml10" id="searchText" name="searchText" v-model="searchData.searchText" style="width:37.5%" title="수신자정보">
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
        <div class="float-left">전체 : <span class="color1"><strong>{{totCnt}}</strong></span>건
          <SelectLayer @fnSelected="fnSelected" classProps="selectStyle2 width120 ml20"></SelectLayer>
        </div>
      </div>

      <div class="row">
        <div class="col-xs-12 consolMarginTop">
          <!-- table -->
          <table class="table_skin1 bt-000 tbl-striped">
            <colgroup>
                <col style="width:5%">
                <col style="width:10%">
                <col style="width:10%">
                <col style="width:10%">
                <col style="width:10%">
                <col style="width:8%">
                <col style="width:8%">
                <col style="width:8%">
                <col style="width:5%">
            </colgroup>
            <thead>
              <tr>
                <th class="text-center lc-1">No.</th>
                <th class="text-center lc-1">수신자휴대폰</th>
                <th class="text-center lc-1">Push로그인ID</th>
                <th class="text-center lc-1">클라이언트키</th>
                <th class="text-center lc-1">발송일시</th>
                <th class="text-center lc-1">발송타입</th>
                <th class="text-center lc-1">발송채널</th>
                <th class="text-center lc-1">태그</th>
                <th class="text-center lc-1 end">결과</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(data, idx) in datas" :key="data.rowNum">
                  <td>{{data.rowNum}}</td>
                  <td class="text-center"><a @click="fnDetailPop(idx)"><u>{{data.phoneNumber}}</u></a></td>
                  <td class="text-center">{{data.pushCuid}}</td>
                  <td class="text-center">{{data.cliKey}}</td>
                  <td class="text-center">{{data.regDt}}</td>
                  <td class="text-center">{{data.senderTypeNm}}</td>
                  <td class="text-center">{{data.finalCh}}</td>
                  <td class="text-center">{{data.campaignId}}</td>
                  <td class="text-center end">{{data.gwResultNm}}</td>
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
          'searchCondi' : 'receiverPhone',
          'searchText' : '',
          'searchStartDate' : this.$gfnCommonUtils.getCurretDate(),
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
    this.fnSetIntervalSearchDate();
    //this.fnSearch();
  },
  methods: {
    //검색일자변경
    fnSetIntervalSearchDate(){
      this.searchData.searchStartDate = this.$gfnCommonUtils.getCurretDate();
    },
    fnUpdateStartDate(sltDate) {
      this.searchData.searchStartDate = sltDate;
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
        confirm.fnAlert("메시지 상세조회", "발송 구분을 선택해주세요.");
        return false;
      }

      if(searchResultY == false && searchResultN == false) {
        confirm.fnAlert("메시지 상세조회", "결과를 선택해주세요.");
        return false;
      }

      if(searchText == "") {
        confirm.fnAlert("메시지 상세조회", "검색조건을 입력해주세요.");
        return false;
      }

      await messageStatusApi.selectMessageStatusList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.datas = result.data;
          this.totCnt = result.pageInfo.totCnt;
          this.offset = result.pageInfo.offset;
        } else { 
          confirm.fnAlert("메시지 상세조회", result.message);
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
  }
}
</script>