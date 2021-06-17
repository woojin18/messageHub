<template>
<div class="row row-no-margin">
  <div class="contentHeader">
      <h2> > 웹 발송현황</h2>
      <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title=" 웹 발송현황 이용안내">이용안내 <i class="fal fa-book-open"></i></a>
  </div>
  <!-- 본문 -->
  <div class="row">
    <div class="col-xs-12">
      <div class="menuBox">
        <div class="of_h">
          <div class="inline-block" style="width:8%"><h4>발송일자</h4></div>
          <div class="inline-block" style="width:91%">
            <Calendar @update-date="fnUpdateStartDate" calendarId="searchStartDate" classProps="datepicker inputStyle maxWidth200" :initDate="searchData.searchStartDate"></Calendar>
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
        <div class="of_h consolMarginTop">
          <div class="inline-block" style="width:8%"><h4>발송자ID</h4></div>
          <div class="inline-block" style="width:16%">
            <input type="text" class="inputStyle vertical-top ml10" id="searchText" name="searchText" v-model="searchData.searchText" style="width:37.5%" title="수신자정보">
          </div>
          <a @click="fnSearch()" class="btnStyle2 float-right" title="검색" activity="READ">검색</a>
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
          <DetailLayer :title="detailLayerTitle" :layerView.sync="detailLayerView" :detailWebReqId="detailLayerWebReqId"></DetailLayer>
        </div>
      </div>

      <div class="of_h inline">
        <!-- 15개씩 보기 -->
        <div class="of_h mb20">
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
                <col style="width:9%">
                <col style="width:13%">
                <col style="width:5%">
                <col style="width:5%">
                <col style="width:5%">
                <col style="width:5%">
                <col style="width:5%">
                <col style="width:8%">
                
                <col style="width:13%">
              </colgroup>
              <thead>
                <tr>								
                  <th class="text-center lc-1" rowspan="2">No.</th>
                  <th class="text-center lc-1" rowspan="2">발송자</th>
                  <th class="text-center lc-1" rowspan="2">전체발송수</th>
                  <th class="text-center lc-1" rowspan="2">발송타입</th>
                  <th class="text-center lc-1" colspan="5" style="border-bottom:1px solid #d5d5d5 !important">발송 채널</th>
                  <th class="text-center lc-1" rowspan="2">발송실패</th>
                  
                  <th class="text-center lc-1 end" rowspan="2">발송일자</th>
                </tr>
                <tr>		
                  <th class="text-center lc-1">Push</th>
                  <th class="text-center lc-1">RCS</th>
                  <th class="text-center lc-1">알림톡</th>
                  <th class="text-center lc-1">친구톡</th>
                  <th class="text-center lc-1">SMS</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(data, idx) in datas" :key="data.row_num">
                    <td>{{ idx + 1 }}</td>
                    <!-- <td class="text-center"><a href="#self" title="메세지상세" data-toggle="modal" data-target="#Message">{{data.phoneNumber}}</a></td> -->
                    <td class="text-center">{{data.corpName}}</td>
                    <td class="text-center">{{data.senderCnt}}</td>
                    <td class="text-center">{{data.senderTypeNm}}</td>

                    <td class="text-center">{{data.push}}</td>
                    <td class="text-center">{{data.rcs}}</td>
                    <td class="text-center">{{data.alimTalk}}</td>
                    <td class="text-center">{{data.friendTalk}}</td>
                    <td class="text-center">{{data.sms}}</td>

                    <td class="text-center"><a href="javascript:void(0);" @click="fnDetailPop(idx)">{{data.sendFailCnt}}</a></td>
                    <td class="text-center end">{{data.regDt}}</td>
                </tr>
                <tr v-if="datas.length == 0">
                    <td class="text-center" colspan="11">검색된 내용이 없습니다.</td>
                </tr>
              </tbody>
            </table>
        <!-- //table -->
          </div>
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
import DetailLayer from '../components/bp-web-send-detail.vue';

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
          'searchText' : '',
          'searchStartDate' : this.$gfnCommonUtils.getCurretDate(),
          'searchEndDate' : this.$gfnCommonUtils.getCurretDate()
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
      // 팝업
      detailLayerView: false,
      detailLayerTitle: "제목",
      detailLayerWebReqId:""
    }
    
        
  },
  mounted() {
    this.fnSetIntervalSearchDate(this.searchDateInterval);
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
      messageStatusApi.excelDownloadWebSend(params);
    },
    //결과 전체 선택시
    searchResultYnChkAll(){
      if(this.resultYnAllSelected){
        this.searchData.searchResultYn = ['Y', 'N'];
      } else {
        this.searchData.searchResultYn = [];
      }
    },
    // 검색
    async fnSelectWebSendList() {

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
      params.roleCd = tokenSvc.getToken().principal.roleCd
      console.log('token:', tokenSvc.getToken());

      await messageStatusApi.selectWebSendList(params).then(response =>{
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
      this.fnSelectWebSendList();
    },

        // 현황상세 팝업
    fnDetailPop(row) {
      //alert(row);
      //alert(this.datas[row].msgKey);
        this.detailLayerView = true;
        this.detailLayerTitle = "발송실패 리스트";
        this.detailLayerWebReqId = this.datas[row].webReqId; 
        
      },

 
  }
}
</script>