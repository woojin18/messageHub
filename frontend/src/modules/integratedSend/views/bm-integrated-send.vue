<template>
<div class="row row-no-margin">
  <div class="contentHeader">
      <h2> > 통합발송</h2>
      <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="메시지 상세조회 이용안내">이용안내 <i class="fal fa-book-open"></i></a>
  </div>
  <!-- 본문 -->
  <div class="row">
    <div class="col-xs-12">
      <div class="menuBox">

        <div class="of_h">
          <div class="inline-block" style="width:8%"><h4 class="font-normal mt15">검색조건</h4></div>
          <div class="inline-block" style="width:91%">
            <select v-model="searchData.searchCondi" class="selectStyle2" style="width:15%" title="검색조건">
                <option value="templateName">템플릿명</option>
                <option value="templateChannel">템플릿채널</option>
            </select>
            <input type="text" class="inputStyle vertical-top ml10" id="searchText" name="searchText" v-model="searchData.searchText" style="width:37.5%" title="">
          </div>
        </div>
        <div class="of_h consolMarginTop">
          <div class="inline-block" style="width:8%"><h4 class="font-normal mt15">등록일자</h4></div>
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
            <a @click="fnSearch()" class="btnStyle2 float-right" title="검색">검색</a>
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
          <router-link :to="{ name: 'integratedTemplate' }" tag="a" class="btnStyle2 borderGray" >통합발송 템플릿 관리<i class="fal fa-arrow-to-bottom"></i></router-link>
          <!--<router-link :to="{ name: 'integratedSendManage' }" tag="a" class="btnStyle2 borderGray" >통합 메시지 발송<i class="fal fa-arrow-to-bottom"></i></router-link>-->
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
				<col style="width:10%">
				<col style="width:20%">
				<col style="width:10%">
				<col style="width:10%">
				<col style="width:12%">
				<col style="width:12%">
				<col style="width:20%">
			</colgroup>
			<thead>
				<tr>
				<th class="text-center lc-1">No.</th>
				<th class="text-center lc-1">템플릿 ID</th>
				<th class="text-center lc-1">템플릿명</th>
				<th class="text-center lc-1">템플릿 채널</th>
				<th class="text-center lc-1">템플릿유형</th>
				<th class="text-center lc-1">메시지 타입</th>
				<th class="text-center lc-1">템플릿 구분</th>
				<th class="text-center lc-1">등록자</th>
				<th class="text-center lc-1 end">등록일자</th>
				</tr>
			</thead>
            <tbody>
              <tr v-for="(data, idx) in datas" :key="data.row_num">
                <td>{{ idx + 1 }}</td>
                <td class="text-center"><router-link :to="{ name: 'integratedSendManage', params: {'tmpltCodeP': data.tmpltCode }}">{{data.tmpltCode}}</router-link> </td>
                <td class="text-center">{{data.tmpltTitle}}</td>
                <td class="text-center">{{data.tmpltChannel}}</td>
                <td class="text-center">{{data.msgKind}}</td>
                <td class="text-center">{{data.msgType}}</td>
                <td class="text-center">{{data.otherProjectUseYn}}</td>
                <td class="text-center">{{data.regId}}</td>
                <td class="text-center end">{{data.regDt}}</td>
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
import integratedSendApi from '../service/integratedSendApi'
import PageLayer from '@/components/PageLayer.vue';
import SelectLayer from '@/components/SelectLayer.vue';
import Calendar from "@/components/Calendar.vue";
import tokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

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
          'searchCondi' : 'templateName',
          'searchText' : '',
          'searchStartDate' : this.$gfnCommonUtils.getCurretDate(),
          'searchEndDate' : this.$gfnCommonUtils.getCurretDate()
        }
      }
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '통합 발송 리스트';
      }
    },
  },
  data() {
    return {
      listAllChecked: false,
      listChkBox: [],
      listSize : 10,  // select 박스 value (출력 갯수 이벤트)
      pageNo : 1,  // 현재 페이징 위치
      totCnt : 0,  //전체 리스트 수
      offset : 0, //페이지 시작점
      searchDateInterval: 7,
      datas: []
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



    // 검색
    async fnSelectIntegratedSendList() {

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

      await integratedSendApi.selectIntegratedSendList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.datas = result.data;
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
      this.fnSelectIntegratedSendList();
    }
  }
}
</script>