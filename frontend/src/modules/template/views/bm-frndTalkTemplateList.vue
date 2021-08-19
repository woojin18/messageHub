<template>

  <div>
    <div class="contentHeader">
        <h2>{{componentsTitle}}</h2>
        <!-- <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="친구톡 템플릿 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
      </div>

      <!-- 본문 -->
      <div class="row">
        <div class="col-xs-12">
          <div class="menuBox">
            <div class="of_h">
              <div class="inline-block" style="width:8%"><h4>검색조건</h4></div>
              <div class="inline-block" style="width:91%">
                <select name="userConsole_sub040401_1" class="selectStyle2" style="width:15%" title="검색조건 선택란" v-model="searchData.searchCondi">
                  <option value="tmpltName">템플릿명</option>
                  <option value="tmpltId">템플릿ID</option>
                </select>
                <input type="text" class="inputStyle vertical-top ml10" style="width:37.5%" title="검색조건 입력란" v-model="searchData.searchText" @keypress.enter="fnPageNoResetSearch">
              </div>
            </div>

            <div class="of_h consolMarginTop">
              <div class="inline-block" style="width:8%"><h4>등록일자</h4></div>
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
            
            <div class="of_h">
            <div class="inline-block" style="width:8%"><h4>메시지구분</h4></div>
            <div class="inline-block" style="width:91%">
              <div class="consolCheck consolMarginTop">
                <input type="checkbox" id="otherUse_all" class="checkStyle2" @change="fnSearchMsgKindCdChkAll" v-model="msgKindCdAllSelected">
                <label for="otherUse_all" class="mr30">전체</label>
                <input type="checkbox" id="searchMsgKindCd_A" class="checkStyle2" value="A" v-model="searchData.searchMsgKindCd">
                <label for="searchMsgKindCd_A" class="mr30">광고성</label>
                <input type="checkbox" id="searchMsgKindCd_I" class="checkStyle2" value="I" v-model="searchData.searchMsgKindCd">
                <label for="searchMsgKindCd_I" class="mr30">정보성</label>
              </div>
              <a @click="fnPageNoResetSearch" class="btnStyle1 float-right" title="검색" activity="READ">검색</a>
            </div>
          </div>

          </div>
        </div>
      </div>
      
      <div class="row">
        <div class="col-xs-12 consolMarginTop">

          <div class="of_h inline">
            <div class="float-right">
              <router-link :to="{ name: 'frndTalkTemplateManage' }" tag="a" class="btnStyle2 backBlack mr10" title="템플릿 등록" activity="SAVE">템플릿 등록</router-link>
              <a @click="fnDeleteFrndTalkTemplate" class="btnStyle2 borderGray mr10" title="엑셀 다운로드" activity="SAVE">삭제</a>
              <a @click="fnExcelDownLoad" class="btnStyle2 borderGray" title="엑셀 다운로드" activity="READ">엑셀 다운로드 <i class="fal fa-arrow-to-bottom"></i></a>
            </div>
          </div>
          <!-- 15개씩 보기 -->
          <div class="of_h inline">
            <div class="float-left">전체 : <span class="color1"><strong>{{totCnt}}</strong></span>건
              <SelectLayer @fnSelected="fnSelected" classProps="selectStyle2 width120 ml20"></SelectLayer>
            </div>
          </div>
          <!-- //15개씩 보기 -->
          
          <div class="row">
            <div class="col-xs-12 consolMarginTop">
              <!-- table -->
              <table class="table_skin1 bt-000 tbl-striped">
                <colgroup>
                  <col style="width:5%">
                  <col style="width:5%">
                  <col style="width:14%">
                  <col>
                  <col style="width:15%">
                  <col style="width:10%">
                  <col style="width:19%">
                </colgroup>
                <thead>
                  <tr>
                    <th class="text-center lc-1">
                      <input type="checkbox" id="listCheck_all" class="boardCheckStyle" value="listCheck_all" @change="fnListChkAll" v-model="listAllChecked">
                      <label for="listCheck_all"></label>
                    </th>
                    <th class="text-center lc-1">No.</th>
                    <th class="text-center lc-1">템플릿 ID</th>
                    <th class="text-center lc-1">템플릿명</th>
                    <th class="text-center lc-1">메시지구분</th>
                    <th class="text-center lc-1">등록자</th>
                    <th class="text-center lc-1 end">등록일자</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(contant, idx) in contants" :key="contant.tmpltId">
                    <td class="text-center">
                      <input type="checkbox" :id="'listCheck_'+idx" name="listCheck_" class="boardCheckStyle" :value="contant.tmpltId" v-model="listChkBox">
                      <label :for="'listCheck_'+idx"></label>
                    </td>
                    <td class="text-center">{{totCnt-offset-contant.rownum+1}}</td>
                    <td class="text-center">
                      <u><router-link :to="{ name: 'frndTalkTemplateManage', params: { tmpltId: contant.tmpltId }}">{{contant.tmpltId}}</router-link></u>
                    </td>
                    <td class="text-left">{{contant.tmpltName | unescapeXss}}</td>
                    <td class="text-center">{{contant.msgKindName}}</td>
                    <td class="text-center">{{contant.regNm}}</td>
                    <td class="text-center end">{{contant.regDt}}</td>
                  </tr>
                  <tr v-if="contants.length == 0">
                  <td class="text-center">
                    <input type="checkbox" id="listCheck_0" class="boardCheckStyle">
                    <label for="listCheck_0"></label>
                  </td>
                  <td class="text-center" colspan="6">검색된 내용이 없습니다.</td>
                </tr>
                </tbody>
              </table>
              <!-- //table -->
            </div>
          </div>
        </div>
      </div>

      <!-- pagination -->
      <PageLayer @fnClick="fnSearch" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
      <!-- //pagination -->
  </div>

</template>

<script>
import Calendar from "@/components/Calendar.vue";
import PageLayer from '@/components/PageLayer.vue';
import SelectLayer from '@/components/SelectLayer.vue';

import templateApi from "@/modules/template/service/templateApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  name: "frndTalkTemplateList",
  components: {
    Calendar,
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
          'searchStartDate' : this.$gfnCommonUtils.getCurretDate(),
          'searchEndDate' : this.$gfnCommonUtils.getCurretDate(),
          'searchMsgKindCd' : []
        }
      }
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '친구톡 템플릿';
      }
    },
  },
  data() {
    return {
      msgKindCdAllSelected: true,
      listAllChecked: false,
      listChkBox: [],
      listSize : 10,  // select 박스 value (출력 갯수 이벤트)
      pageNo : 1,  // 현재 페이징 위치
      totCnt : 0,  //전체 리스트 수
      offset : 0, //페이지 시작점
      searchDateInterval: 7,
      contants: []
    }
  },
  async mounted() {
    this.fnValidUseChGrp();
    this.fnSetIntervalSearchDate(this.searchDateInterval);
    this.fnSearchMsgKindCdChkAll();
    this.fnPageNoResetSearch();
  },
  methods: {
    //메시지구분 전체 선택시
    fnSearchMsgKindCdChkAll(){
      if(this.msgKindCdAllSelected){
        this.searchData.searchMsgKindCd = ['A', 'I'];
      } else {
        this.searchData.searchMsgKindCd = [];
      }
    },
    async fnValidUseChGrp(){
      let params = {chGrp: 'KKO'};
      await templateApi.selectValidUseChGrp(params).then(response =>{
        const result = response.data;
        if(result.success) {
          if(this.$gfnCommonUtils.isEmpty(result.data)){
            confirm.fnAlert(this.componentsTitle, '이용하실 수 없는 채널입니다.');
            this.$router.back();
          }
        } else {
          confirm.fnAlert(this.componentsTitle, '시스템 오류입니다. 잠시 후 다시 시도하세요.');
          this.$router.back();
        }
      });
    },
    //템플릿 삭제
    fnDeleteFrndTalkTemplate(){
      //유효성 검사
      if(this.listChkBox == null || this.listChkBox.length == 0){
        confirm.fnAlert(this.componentsTitle, '삭제할 항목을 선택해주세요.');
        return;
      }

      eventBus.$on('callbackEventBus', this.fnProcDeleteFrndTalkTemplate);
      confirm.fnConfirm(this.componentsTitle, "선택한 템플릿을 삭제하시겠습니까?", "확인");
    },
    //푸시 템플릿 삭제 처리
    async fnProcDeleteFrndTalkTemplate(){
      var params = {tmpltIds : this.listChkBox};
      await templateApi.deleteFrndTalkTmplt(params).then(response =>{
        var result = response.data;
        if(result.success) {
          confirm.fnAlert(this.componentsTitle, '삭제되었습니다.');
          this.listAllChecked = false;
          this.fnListChkAll();
          this.fnPageNoResetSearch();
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    //템플릿 엑셀 다운로드
    fnExcelDownLoad(){
      const params = this.searchData;
      templateApi.excelDownloadFrndTalkTmplt(params);
    },
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
    //리스트 전체 체크박스
    fnListChkAll(){
      const vm = this;
      if(this.listAllChecked){
        this.contants.forEach(function(contant){
          vm.listChkBox.push(contant.tmpltId);
        });
      } else {
        this.listChkBox = [];
      }
    },
    async fnSelectFrndTalkTmpltList(){
      //유효성 검사
      if(this.searchData.searchStartDate && this.searchData.searchEndDate){
        if(this.searchData.searchStartDate.replace(/[^0-9]/g, '') > this.searchData.searchEndDate.replace(/[^0-9]/g, '')){
          confirm.fnAlert(this.componentsTitle, '시작일은 종료일보다 클 수 없습니다.');
          return false;
        }
      }

      let params = Object.assign({}, this.searchData);
      params.pageNo = this.pageNo;
      params.listSize = this.listSize;
      await templateApi.selectFrndTalkList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          this.contants = result.data;
          this.totCnt = result.pageInfo.totCnt;
          this.offset = result.pageInfo.offset;
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    // select 박스 선택시 리스트 재출력
    fnSelected(listSize) {
      this.listSize = Number(listSize);
      this.$refs.updatePaging.fnAllDecrease();
    },
    fnPageNoResetSearch(){
      this.$refs.updatePaging.fnAllDecrease();
    },
    fnSearch(pageNo) {
      this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNo, '1'))*1;
      this.fnSelectFrndTalkTmpltList();
    }
  }
}
</script>