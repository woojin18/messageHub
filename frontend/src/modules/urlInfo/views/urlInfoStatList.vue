<template>
  <article>
    <div class="contentHeader">
      <h2>LAB > {{ componentsTitle }}</h2>
    </div>
    <div class="border-line2 pd10">
      <p class="color4">* 메시지에 링크된 단축URL+를 고객에게 보내고 고객이 클릭한 통계를 수치화하여 보여줍니다.<br>* [상세보기] 버튼을 누르면 유입 채널 별 그리고 타임라인  클릭 통계정보를 보실 수 있습니다.</p>
    </div>

    <!-- 본문 -->
    <div class="row">
      <div class="col-xs-12 mt10">
        <div class="menuBox">		
          <div>
            <h4 class="inline-block" style="width:6%">제목</h4>
            <div class="inline-block" style="width:15%">
              <input type="text" class="inputStyle" title="제목 입력란" v-model="searchCond.title" @keydown.enter="goPage(1)">
            </div>
            <h4 class="inline-block ml40" style="width:6%">단축URL+ ID</h4>
            <div class="inline-block" style="width:15%">
              <input type="text" class="inputStyle" title="단축URL+ ID 입력란" v-model="searchCond.urlId" @keydown.enter="goPage(1)">
            </div>
            <h4 class="inline-block ml40" style="width:6%">원본 URL</h4>
            <div class="inline-block" style="width:15%">
              <input type="text" class="inputStyle" title="원본 URL 입력란" v-model="searchCond.orgUrl" @keydown.enter="goPage(1)">
            </div>
          </div>
          <div class="consolMarginTop">
            <h4 class="inline-block" style="width:6%">등록일</h4>
            <div class="inline-block" style="width:25%">
              <Calendar
                @update-date="(pickDate) => { searchCond.startDt = pickDate }"
                calendarId="startDt"
                classProps="datepicker inputStyle maxWidth160"
                :initDate="searchCond.startDt"
              ></Calendar> ~ 
              <Calendar
                @update-date="(pickDate) => { searchCond.endDt = pickDate }"
                calendarId="endDt"
                classProps="datepicker inputStyle maxWidth160"
                :initDate="searchCond.endDt"
              ></Calendar>
              <!-- <input 
                type="text" 
                class="datepicker inputStyle hasDatepicker" 
                title="시작날짜 입력란" id="dp1623916254295" style="width:48%">
                  ~ 
              <input 
                type="text" 
                class="datepicker inputStyle hasDatepicker" 
                title="시작날짜 입력란" 
                id="dp1623916254295" 
                style="width:48%"> -->
            </div>		
            <ul class="tab_s2 ml10">
              <li
                v-for="(item, idx) in periodMonthList" 
                :key="idx"
                :class="searchCond.periodMonth === item ? 'active' : ''"
              >
                <a 
                  href="#" 
                  :title="`${item}개월 등록일자 검색`"
                  @click.prevent="searchCond.periodMonth = item"
                >{{ item }}개월</a>
              </li>
            </ul>
            <a 
              href="#self" 
              class="btnStyle1 float-right"
              @click.prevent="selectUrlInfoStatList()"
            >검색</a>
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-xs-12 consolMarginTop">
        <div class="of_h mt20">
          <a
            class="float-right btnStyle1 backBlack"
            data-toggle="modal"
            data-target="#shortened_URL_add"
            title="단축 URL 생성"
            @click="initPop()"
          >단축 URL+ 생성</a>
        </div>

        <!-- 엑셀 다운로드 -->
        <div 
          v-if="excelDownFlag"
          class="of_h inline">
          <div class="float-right">
            <a
              href="#self" 
              class="btnStyle2 borderGray" 
              title="엑셀 다운로드"
              @click.prevent="downloadExcel()" 
            >엑셀 다운로드 <i class="fal fa-arrow-to-bottom"></i>
            </a>
          </div>
        </div>

        <!-- 페이지당 리스트 개수 -->
        <div class="of_h inline">
          <div class="float-left">전체 : <span class="color1"><strong>{{ pageInfo.totCnt |formatComma }}</strong></span>건
            <SelectLayer @fnSelected="(selCnt)=>{pageInfo.listSize = selCnt}" classProps="selectStyle2 width120 ml20"/>
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
                <col style="width:*%">
                <col style="width:7%">
                <col style="width:7%">
                <col style="width:7%">
                <col style="width:7%">
                <col style="width:10%">
              </colgroup>
              <thead>
                <tr>								
                  <th class="text-center lc-1">No.</th>
                  <th class="text-center lc-1">제목</th>
                  <th class="text-center lc-1">단축URL+ ID</th>
                  <th class="text-center lc-1">원본URL</th>
                  <th class="text-center lc-1">등록일/만료일</th>
                  <th class="text-center lc-1">총 발송수
                    <i class="fas fa-question-circle toolTip ml5">
                      <span class="toolTipText">단축 URL+ 메시지 전송 성공한 발송 개수</span>
                    </i>
                  </th>
                  <th class="text-center lc-1">클릭수
                    <i class="fas fa-question-circle toolTip ml5">
                      <span class="toolTipText">메시지를 클릭하여 들어온 URL 유입 수(중복 사용자 제거)</span>
                    </i>
                  </th>
                  <th class="text-center lc-1">클릭율
                    <i class="fas fa-question-circle toolTip ml5">
                      <span class="toolTipText">총 발송수에 대한 클릭율(%)</span>
                    </i>
                  </th>
                  <th class="text-center lc-1 end">보기</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(item, idx) in itemList"
                  :key="idx"
                >
                  <td class="text-center">{{ item.rownum }}</td>
                  <td class="text-center">{{ item.title }}</td>
                  <td class="text-center">{{ item.urlId }}</td>
                  <td>{{ item.orgUrl }}</td>
                  <td class="text-center">{{ `${$moment(item.regDt).format('YYYY.MM.DD')}/${$moment(item.expDt).format('YYYY.MM.DD')}` }}</td>
                  <td class="text-right">{{ item.totCnt }}</td>
                  <td class="text-right">{{ item.conCnt }}</td>
                  <td class="text-right">{{ (item.totCnt === 0) ? 0 : Math.trunc(item.conCnt / item.totCnt * 100) }}%</td>
                  <td class="text-center">
                    <a 
                      href="#self" 
                      class="btnStyle1 backLightGray" 
                      style="min-width:auto; width:100%" 
                      title="선택"
                      @click.prevent="goUrl(item)"
                    >상세보기</a>
                  </td>
                </tr>
                <tr
                  v-if="itemList.length === 0"
                >
                  <td class="text-center" colspan="9">데이터가 없습니다.</td>
                </tr>
              </tbody>
            </table>
            <!-- //table -->
          </div>			
        </div>
      </div>			
    </div>

    <div class="row mt40">
      <div class="col-xs-12">
        <div class="pagination1 text-center">
          <a
            v-if="hasPrevPageList"
            @click="goPage(pageInfo.pageNo - 10)"
            href="#"
            title="10페이지 이전 페이지로 이동"
          >
            <i class="far fa-chevron-double-left"></i>
          </a>
          <a 
            v-if="hasPrevPage"
            @click.prevent="goPage(pageInfo.pageNo - 1)"
            href="#" 
            title="이전 페이지로 이동"
          >
            <i class="far fa-chevron-left"></i>
          </a>
          <a
            v-for="(item, idx) in pagingList"
            :key="idx"
            @click="goPage(item)"
            href="#"
            :title="`${item}페이지로 이동`"
            class="number"
            :class="item === pageInfo.pageNo ? 'active' : ''"
            >{{ item }}</a
          >
          <a 
            v-if="hasNextPage"
            @click.prevent="goPage(pageInfo.pageNo + 1)"
            href="#" 
            title="다음 페이지로 이동"
          >
            <i class="far fa-chevron-right"></i>
          </a>
          <a
            v-if="hasNextPageList"
            @click="goPage(pageInfo.pageNo - 10)"
            href="#"
            title="10페이지 다음 페이지로 이동"
            ><i class="far fa-chevron-double-right"></i
          ></a>
        </div>
      </div>
    </div>
    <!-- //본문 -->

    <shortenedUrlAddPopup ref="shortenedUrlAddPopup"/>

    <!-- <footer>Copyright©LG Plus Corp. All Rights Reserved.</footer> -->
  </article>
</template>

<script>
import confirm from '@/modules/commonUtil/service/confirm.js'
import urlInfoApi from '@/modules/urlInfo/service/urlInfoApi.js'

import Calendar from '@/components/Calendar'
import SelectLayer from '@/components/SelectLayer'
import shortenedUrlAddPopup from "@/modules/urlInfo/components/shortenedUrlAddPopup"

export default {
  name: 'urlInfoStatList',
  components: {
    Calendar,
    SelectLayer,
    shortenedUrlAddPopup,
  },
  props: {},
  computed: {
    totPages() {
      return Math.ceil(this.pageInfo.totCnt / this.pageInfo.listSize)
    },
    hasPrevPage() {
      return this.pageInfo.pageNo-1 > 0
    },
    hasNextPage() {
      return this.pageInfo.pageNo+1 <= this.totPages
    },
    hasPrevPageList() {
      return this.pageInfo.pageNo - 10 > 0
    },
    hasNextPageList() {
      return this.pageInfo.pageNo + 10 <= this.totPages
    },
    pagingList() {
      let pagingList = []

      let idx = 1
      while(idx <= this.totPages) {
        pagingList.push(idx++)
      }

      return pagingList
    },
  },
  watch: {
		'pageInfo.listSize' () {
      this.pageInfo.pageNo = 1
      this.selectUrlInfoStatList()
		},
    'searchCond.periodMonth' (curVal) {
      this.$set(this.searchCond, 'startDt', this.$gfnCommonUtils.strDateAddMonth(this.$gfnCommonUtils.getCurretDate(), -1*curVal))
      this.$set(this.searchCond, 'endDt', this.$gfnCommonUtils.getCurretDate())

      this.pageInfo.pageNo = 1
      this.selectUrlInfoStatList()
		},
  },
  data() {
    return {
      searchCond: {
        title: '',
        urlId: '',
        orgUrl: '',
        startDt: this.$gfnCommonUtils.strDateAddMonth(this.$gfnCommonUtils.getCurretDate(), -3),
        endDt: this.$gfnCommonUtils.getCurretDate(),
        periodMonth: 3,
      },
      pageInfo: {
        pageNo: 1,
        offset: 0,
        listSize: 10,
        totCnt: 0,
      },
      pagingSize: [10, 20, 30],
      periodMonthList: [3, 6, 12],
      componentsTitle: '단축 URL+ & 통계',
      itemList: [],
      excelDownFlag: false,  // 엑셀 다운로드 사용유무
    };
  },
  mounted() {
    this.selectUrlInfoStatList()
  },
  methods: {
    async selectUrlInfoStatList() {
      let params = {
        ...this.pageInfo,
        ...this.searchCond,
      }

      await urlInfoApi.selectUrlInfoStatList(params).then((response) => {
        const result = response.data;
        if (result.success) {
          this.itemList = result.data
          this.pageInfo = result.pageInfo
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    goPage(pageNo) {
      this.pageInfo.pageNo = pageNo
      this.selectUrlInfoStatList()
    },
    goUrl(item) {
      const params = {
        urlId: item.urlId,
        urlInfo: {...item}
      }
      this.$router.push({name: 'urlInfoStatDetail', params})
    },
    // 엑셀 다운로드 (미구현)
    downloadExcel() {
      console.log('########## downloadExcel ########')
    },
    initPop() {
      this.$refs['shortenedUrlAddPopup'].initVal()
    },
  },
};
</script>

<style>
</style>