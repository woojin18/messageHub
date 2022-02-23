<template>
  <div>

    <div class="contentHeader">
      <h2>템플릿 > 알림톡</h2>
      <!-- <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="알림톡 템플릿 관리 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
    </div>

    <div class="row">
      <div class="col-xs-12">
        <div class="menuBox">
          <div class="of_h">
            <div class="inline-block" style="width:8%"><h4>검색조건</h4></div>
            <div class="inline-block" style="width:91%">
              <select class="selectStyle2" style="width:15%" title="검색조건 선택란" v-model="searchData.searchCondi">
                <option value="tmpltName">템플릿명</option>
                <option value="tmpltKey">템플릿키</option>
              </select>
              <input type="text" class="inputStyle vertical-top ml10" style="width:37.5%" title="검색조건 입력란" v-model="searchData.searchText" @keypress.enter="fnPageNoResetSearch">
            </div>
          </div>

          <!--<div class="of_h consolMarginTop">
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
          </div>-->

          <div class="of_h consolMarginTop">
            <div class="inline-block" style="width:8%"><h4>상태</h4></div>
            <div class="inline-block" style="width:91%">
              <div class="consolCheck vertical-top">
                <input type="checkbox" id="tmpltStatCode_all" class="checkStyle2" @change="fnSearchtmpltStatCodeChkAll" v-model="tmpltStatCodeAllSelected">
                <label for="tmpltStatCode_all" class="mr30">전체</label>
              </div>
              <div class="consolCheck vertical-top">
                <template v-for="(tmpltStatCodeInfo, idx) in tmpltStatCodeList">
                  <input
                    :key="idx"
                    type="checkbox"
                    :id="'tmpltStatCode_'+tmpltStatCodeInfo.codeVal1"
                    name="tmpltStatCode"
                    class="checkStyle2"
                    :value="tmpltStatCodeInfo.codeVal1"
                    v-model="searchData.searchTmpltStatCodes"
                  >
                  <label :key="idx+'_for'" :for="'tmpltStatCode_'+tmpltStatCodeInfo.codeVal1" class="mr30">{{tmpltStatCodeInfo.codeName1}}</label>
                </template>
              </div>
              <a href="#" @click.prevent="fnPageNoResetSearch" class="btnStyle1 float-right" title="통합발송 검색" activity="READ">검색</a>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="row">
        <div class="col-xs-12 consolMarginTop">
          <div class="of_h inline">
            <div class="float-right">
              <router-link :to="{ name: 'alimTalkTemplateManage' }" tag="a" class="btnStyle2 backBlack" title="템플릿 등록" activity="SAVE">템플릿 등록</router-link>
              <a href="#" @click.prevent="fnDeleteReqTmplt" class="btnStyle2 borderGray ml10" title="삭제" activity="SAVE">삭제</a>
              <a @click="fnExcelDownLoad" class="btnStyle2 borderGray ml10" title="엑셀 다운로드" activity="READ">엑셀 다운로드 <i class="fal fa-arrow-to-bottom"></i></a>
            </div>
          </div>
          <!-- 15개씩 보기 -->
          <div class="of_h inline">
            <div class="float-left">전체 : <span class="color1"><strong>{{totCnt | formatComma}}</strong></span>건
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
                  <col style="width:17%">
                  <col>
                  <col style="width:8%">
                  <col style="width:10%">
                  <col style="width:15%">
                  <col style="width:12%">
                </colgroup>
                <thead>
                  <tr>
                  <th class="text-center lc-1">
                    <input type="checkbox" id="listCheck_all" class="boardCheckStyle" value="listCheck_all" @change="fnListChkAll" v-model="listAllChecked">
                    <label for="listCheck_all"></label>
                  </th>
                  <th class="text-center lc-1">No.</th>
                  <th class="text-center lc-1">템플릿 키</th>
                  <th class="text-center lc-1">템플릿명</th>
                  <th class="text-center lc-1">상태</th>
                  <th class="text-center lc-1">카카오채널 타입</th>
                  <!-- 카카오 채널 그룹관리 기능 제외 -->
                  <!-- <th class="text-center lc-1">카카오 채널/그룹</th> -->
                  <th class="text-center lc-1">카카오 채널</th>
                  <th class="text-center lc-1">최종수정일자</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(contant, idx) in contants" :key="idx">
                    <td class="text-center">
                      <input v-if="contant.tmpltStatCode == 'T'" type="checkbox" :id="'listCheck_'+idx" name="listCheck_" class="boardCheckStyle" :value="contant.tmpltKey" v-model="listChkBox">
                      <input v-else type="checkbox" :id="'listCheck_'+idx" name="listCheck_" class="boardCheckStyle" :value="contant.tmpltKey" v-model="listChkBox" disabled="disabled" >
                      <label :for="'listCheck_'+idx"></label>
                    </td>
                    <td class="text-center">{{totCnt-offset-contant.rownum+1 | formatComma}}</td>
                    <td class="text-center">
                      <u><router-link :to="{ name: 'alimTalkTemplateManage', params: { tmpltKey: contant.tmpltKey }}">{{contant.tmpltKey}}</router-link></u>
                    </td>
                    <td class="text-left">{{contant.tmpltName}}</td>
                    <td v-if="contant.tmpltStatCode == 'S' && contant.existsRejResnYn == 'Y'" class="text-center">
                      <u><a
                        href="#"
                        @click.prevent="fnOpenAlimTalkRejectReasonLayer(contant.tmpltKey)"
                      >{{contant.tmpltStatCodeName}}</a></u>
                    </td>
                    <td v-else class="text-center">
                      {{contant.tmpltStatCodeName}}
                    </td>
                    <td class="text-center">{{contant.senderKeyTypeName}}</td>
                    <td class="text-left">{{contant.kkoChId}}</td>
                    <td class="text-center">{{contant.updDt}}</td>
                  </tr>
                  <tr v-if="contants.length == 0">
                    <td class="text-center">
                      <input type="checkbox" id="listCheck_0" class="boardCheckStyle">
                      <label for="listCheck_0"></label>
                    </td>
                    <td class="text-center" colspan="7">검색된 내용이 없습니다.</td>
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
      <AlimTalkRejectReasonLayer
        :alimTalkRejectReasonOpen.sync="alimTalkRejectReasonOpen"
        :tmpltKey="rejectTmpltKey"
      ></AlimTalkRejectReasonLayer>
  </div>
</template>

<script>
import Calendar from "@/components/Calendar.vue";
import PageLayer from '@/components/PageLayer.vue';
import SelectLayer from '@/components/SelectLayer.vue';
import AlimTalkRejectReasonLayer from "@/modules/template/components/bp-alimTalkRejectReason.vue";

import templateApi from "@/modules/template/service/templateApi.js";
import commonApi from "@/modules/commonUtil/service/commonUtilApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  name: "alimTalkTemplateList",
  components: {
    Calendar,
    PageLayer,
    SelectLayer,
    AlimTalkRejectReasonLayer
  },
  props: {
    searchData : {
      type: Object,
      require: false,
      default: function() {
        return {
          searchCondi: 'tmpltName',
          searchText: '',
          //searchStartDate: this.$gfnCommonUtils.getCurretDate(),
          //searchEndDate: this.$gfnCommonUtils.getCurretDate(),
          searchTmpltStatCodes: []
        }
      }
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '알림톡 템플릿';
      }
    },
  },
  data() {
    return {
      alimTalkRejectReasonOpen : false,
      rejectTmpltKey: '',
      tmpltStatCodeAllSelected: true,
      listAllChecked: false,
      listChkBox: [],
      searchDateInterval: 7,
      tmpltStatCodeList : [],
      listSize : 10,  // select 박스 value (출력 갯수 이벤트)
      pageNo : 1,  // 현재 페이징 위치
      totCnt : 0,  //전체 리스트 수
      offset : 0, //페이지 시작점
      contants: [],
    }
  },
  async mounted() {
    await this.fnExistApiKey();
    await this.fnValidUseChGrp();
    //this.fnSetIntervalSearchDate(this.searchDateInterval);
    this.fnSelectTmpltStatCodeList();
    this.fnPageNoResetSearch();
  },
  methods: {
    async fnExistApiKey(){
      let params = {};
      await templateApi.selectApiKey(params).then(response =>{
        const result = response.data;
        if(result.success) {
          if(this.$gfnCommonUtils.isEmpty(result.data)){
            confirm.fnAlert(this.componentsTitle, '해당 프로젝트의 사용가능한 API 키가 존재하지 않습니다.\n템플릿을 등록/수정/검수요청 하실 수 없습니다.');
          }
        }
      });
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
    fnOpenAlimTalkRejectReasonLayer(rejectTmpltKey){
      this.rejectTmpltKey = rejectTmpltKey;
      this.alimTalkRejectReasonOpen = true;
    },
    //템플릿 엑셀 다운로드
    fnExcelDownLoad(){
      const params = this.searchData;
      templateApi.excelDownloadAlimTalkTmplt(params);
    },
    fnDeleteReqTmplt(){
      //유효성 검사
      if(this.listChkBox == null || this.listChkBox.length == 0){
        confirm.fnAlert(this.componentsTitle, '삭제할 항목을 선택해주세요.');
        return;
      }

      eventBus.$on('callbackEventBus', this.fnProcDeleteReqTmplt);
      confirm.fnConfirm(this.componentsTitle, "선택한 템플릿을 삭제요청 하시겠습니까?", "확인");
    },
    async fnProcDeleteReqTmplt(){
      var params = {tmpltKeys : this.listChkBox};
      await templateApi.procDeleteRequestKkoTmplt(params).then(response =>{
        var result = response.data;
        confirm.fnAlert(this.componentsTitle, result.message);
        if(result.success) {
          this.listAllChecked = false;
          this.fnListChkAll();
          this.fnPageNoResetSearch();
        }
      });
    },
    fnUpdateStartDate(sltDate) {
      this.searchData.searchStartDate = sltDate;
    },
    fnUpdateEndDate(sltDate) {
      this.searchData.searchEndDate = sltDate;
    },
    fnSearchtmpltStatCodeChkAll(){
      const vm = this;
      this.searchData.searchTmpltStatCodes = [];
      if(this.tmpltStatCodeAllSelected){
        this.tmpltStatCodeList.forEach(function(codeInfo){
          vm.searchData.searchTmpltStatCodes.push(codeInfo.codeVal1);
        });
      }
    },
    async fnSelectTmpltStatCodeList(){
      let params = {
        codeTypeCd: 'TMPLT_STAT_CODE',
        useYn: 'Y'
      };
      await commonApi.selectCodeList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          this.tmpltStatCodeList = [];
          let tempStatCodeList = result.data
            //R-검수중(롯데), Q-검수중(카카오) => (업체) 제거 요청
            .map((code) => {
              let codeVal = code.codeVal1;
              let codeName = code.codeName1;
              if(codeVal === 'R' || codeVal === 'Q'){
                codeVal = 'RQ';
                codeName = '검수중'
              }
              return { ...code, codeVal1: codeVal, codeName1: codeName };
            })
            //D-삭제 미노출 및 R-검수중(롯데), Q-검수중(카카오) 검수중 중복제거
            .filter((code, idx, callback) => {
              return code.codeVal1 !== 'D' && idx === callback.findIndex(t => t.codeVal1 === code.codeVal1)
            });

          this.tmpltStatCodeList = Object.assign([], tempStatCodeList);
          this.fnSearchtmpltStatCodeChkAll();
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    async fnSelectAlimTalkTmpltList(){
      //유효성 검사
      if(this.searchData.searchStartDate && this.searchData.searchEndDate){
        if(this.searchData.searchStartDate.replace(/[^0-9]/g, '') > this.searchData.searchEndDate.replace(/[^0-9]/g, '')){
          confirm.fnAlert(this.componentsTitle, '시작일은 종료일보다 클 수 없습니다.');
          return false;
        }
      }

      this.contants = [];
      let params = Object.assign({}, this.searchData);
      params.pageNo = this.pageNo;
      params.listSize = this.listSize;
      await templateApi.selectAlimTalkTmpltList(params).then(response =>{
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
    //검색일자변경
    fnSetIntervalSearchDate(interval){
      this.searchDateInterval = interval;
      this.searchData.searchEndDate = this.$gfnCommonUtils.getCurretDate();
      this.searchData.searchStartDate = this.$gfnCommonUtils.strDateAddDay(this.searchData.searchEndDate, -this.searchDateInterval);
    },
    //리스트 전체 체크박스
    fnListChkAll(){
      const vm = this;
      if(this.listAllChecked){
        this.contants.forEach(function(contant){
          if(contant.tmpltStatCode == 'T'){
            vm.listChkBox.push(contant.tmpltKey);
          }
        });
      } else {
        this.listChkBox = [];
      }
    },
    fnSelected(listSize) {
      this.listSize = Number(listSize);
      this.$refs.updatePaging.fnAllDecrease();
    },
    fnPageNoResetSearch(){
      this.$refs.updatePaging.fnAllDecrease();
    },
    fnSearch(pageNo) {
      this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNo, '1'))*1;
      this.fnSelectAlimTalkTmpltList();
    }
  }
}
</script>