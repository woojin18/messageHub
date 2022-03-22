<template>
  <!-- content -->
	<!-- <div id="content"> -->
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
								<input type="text" class="inputStyle" title="제목 입력란">	
							</div>
							<h4 class="inline-block ml40" style="width:6%">단축URL+ ID</h4>
							<div class="inline-block" style="width:15%">
								<input type="text" class="inputStyle" title="단축URL+ ID 입력란">	
							</div>
							<h4 class="inline-block ml40" style="width:6%">원본 URL</h4>
							<div class="inline-block" style="width:15%">
								<input type="text" class="inputStyle" title="원본 URL 입력란">	
							</div>
						</div>
						<div class="consolMarginTop">
							<h4 class="inline-block" style="width:6%">등록일</h4>
							<div class="inline-block" style="width:25%">
                <Calendar
                  @update-date="(pickDate) => { startDt = pickDate }"
                  calendarId="startDt"
                  classProps="datepicker inputStyle maxWidth160"
                  :initDate="startDt"
                  :customOption="{ minDate: '-0y' }"
                ></Calendar> ~ 
                <Calendar
                  @update-date="(pickDate) => { endDt = pickDate }"
                  calendarId="endDt"
                  classProps="datepicker inputStyle maxWidth160"
                  :initDate="endDt"
                  :customOption="{ minDate: '-0y' }"
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
								<li class="active"><a href="#" title="3개월 등록일자 검색">3개월</a></li>
								<li><a href="#" title="6개월 등록일자 검색">6개월</a></li>
								<li><a href="#" title="12개월 등록일자 검색">12개월</a></li>
							</ul>						
							<a href="#self" class="btnStyle1 float-right">검색</a>
						</div>						
					</div>
				</div>
			</div>


			<div class="row">
				<div class="col-xs-12 consolMarginTop">
					<div class="of_h inline">
						<div class="float-right">
							<a href="#self" class="btnStyle2 borderGray" title="엑셀 다운로드">엑셀 다운로드 <i class="fal fa-arrow-to-bottom"></i></a>
						</div>
					</div>
					<!-- 15개씩 보기 -->
					<div class="of_h inline">
						<div class="float-left">전체 : <span class="color1"><strong>20</strong></span>건
							<select 
                v-model="pageInfo.listSize"
                name="userConsole_sub0302_1" 
                class="selectStyle2 width120 ml20"
              >
                <option 
                  v-for="(item, idx) in pagingSize" :key="idx"
                  :value="item"
                >{{ item }}개씩 보기</option>
                <!-- 
								<option value="">15개씩 보기</option>
								<option value="">30개씩 보기</option>
                 -->
							</select>
						</div>
					</div>
					<!-- //15개씩 보기 -->
					
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
										<th class="text-center lc-1">총 발송수<i class="fas fa-question-circle toolTip ml5"><span class="toolTipText">단축 URL+ 메시지 전송 성공한 발송 개수</span></i></th>
										<th class="text-center lc-1">클릭수<i class="fas fa-question-circle toolTip ml5"><span class="toolTipText">메시지를 클릭하여 들어온 URL 유입 수(중복 사용자 제거)</span></i></th>
										<th class="text-center lc-1">클릭율<i class="fas fa-question-circle toolTip ml5"><span class="toolTipText">총 발송수에 대한 클릭율(%)</span></i></th>
										<th class="text-center lc-1 end">보기</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="text-center">5</td>
										<td class="text-center">11월 메인 이벤트</td>
										<td class="text-center">Ie64tz</td>
										<td>https://ko.reactjs.org/blog/2021/06/08/the-plan-for-react-18.html</td>
										<td class="text-center">2021.12.21/2022.01.20</td>
										<td class="text-right">000</td>
										<td class="text-right">000</td>
										<td class="text-right">0%</td>
										<td class="text-center"><a href="#self" class="btnStyle1 backLightGray" style="min-width:auto; width:100%" title="선택">상세보기</a></td>
									</tr>
									<tr>
										<td class="text-center">5</td>
										<td class="text-center">11월 메인 이벤트</td>
										<td class="text-center">Ie64tz</td>
										<td>https://ko.reactjs.org/blog/2021/06/08/the-plan-for-react-18.html</td>
										<td class="text-center">2021.12.21/2022.01.20</td>
										<td class="text-right">000</td>
										<td class="text-right">000</td>
										<td class="text-right">0%</td>
										<td class="text-center"><a href="#self" class="btnStyle1 backLightGray" style="min-width:auto; width:100%" title="선택">상세보기</a></td>
									</tr>
									<tr>
										<td class="text-center">5</td>
										<td class="text-center">11월 메인 이벤트</td>
										<td class="text-center">Ie64tz</td>
										<td>https://ko.reactjs.org/blog/2021/06/08/the-plan-for-react-18.html</td>
										<td class="text-center">2021.12.21/2022.01.20</td>
										<td class="text-right">000</td>
										<td class="text-right">000</td>
										<td class="text-right">0%</td>
										<td class="text-center"><a href="#self" class="btnStyle1 backLightGray" style="min-width:auto; width:100%" title="선택">상세보기</a></td>
									</tr>
									<tr>
										<td class="text-center">5</td>
										<td class="text-center">11월 메인 이벤트</td>
										<td class="text-center">Ie64tz</td>
										<td>https://ko.reactjs.org/blog/2021/06/08/the-plan-for-react-18.html</td>
										<td class="text-center">2021.12.21/2022.01.20</td>
										<td class="text-right">000</td>
										<td class="text-right">000</td>
										<td class="text-right">0%</td>
										<td class="text-center"><a href="#self" class="btnStyle1 backLightGray" style="min-width:auto; width:100%" title="선택">상세보기</a></td>
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
            <!-- <a href="#" title="이전 페이지로 이동"><i class="far fa-chevron-left"></i></a> -->
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
            <!-- <a href="#" title="다음 페이지로 이동"><i class="far fa-chevron-right"></i></a> -->
            <!-- <a href="#" title="10페이지 다음 페이지로 이동"><i class="far fa-chevron-double-right"></i></a> -->
            <a
              v-if="hasNextPageList"
              @click="goPage(pageInfo.pageNo - 10)"
              href="#"
              title="10페이지 다음 페이지로 이동"
              ><i class="far fa-chevron-double-right"></i
            ></a>
            <!-- 
						<a href="#" title="10페이지 이전 페이지로 이동"><i class="far fa-chevron-double-left"></i></a>
						<a href="#" title="이전 페이지로 이동"><i class="far fa-chevron-left"></i></a>
						<a href="#" title="1페이지로 이동" class="number active">1</a>
						<a href="#" title="2페이지로 이동" class="number">2</a>
						<a href="#" title="3페이지로 이동" class="number">3</a>
						<a href="#" title="4페이지로 이동" class="number">4</a>
						<a href="#" title="5페이지로 이동" class="number">5</a>
						<a href="#" title="다음 페이지로 이동"><i class="far fa-chevron-right"></i></a>
						<a href="#" title="10페이지 다음 페이지로 이동"><i class="far fa-chevron-double-right"></i></a>
             -->
					</div>
				</div>
			</div>
			<!-- //본문 -->

			<!-- <footer>Copyright©LG Plus Corp. All Rights Reserved.</footer> -->
		</article>
	<!-- </div> -->
	<!-- //content -->
</template>

<script>
import confirm from '@/modules/commonUtil/service/confirm.js'
import urlInfoApi from '@/modules/urlInfo/service/urlInfoApi.js'
// import {eventBus} from '@/modules/commonUtil/service/eventBus'
import Calendar from '@/components/Calendar'

export default {
  name: 'urlInfoStatList',
  components: {
    Calendar,
  },
  props: {
		urlId: {
      type: String,
      require: true,
    },
	},
  computed: {
    shortendUrl() {
      if(this.urlId)
        return `https://m-hub.kr/#URL{${this.urlId}}`
      else
        return ''
    },
    totPages() {
      return Math.ceil(this.pageInfo.totCnt / this.pageInfo.listSize)
    },
    startPage() {
      return Math.ceil((this.pageInfo.offset / this.pageInfo.listSize) + 1)
    },
    hasPrevPageList() {
      return this.pageInfo.pageNo - 10 > 0
    },
    hasNextPageList() {
      return this.pageInfo.pageNo + 10 <= this.totPages
    },
    pagingList() {
      let pageNo = this.startPage
      let pagingList = []

      while(pageNo <= this.totPages && pageNo < this.startPage + 10) {
        pagingList.push(pageNo++)
      }

      return pagingList
    },
  },
  data() {
    return {
      pageInfo: {
        pageNo: 1,
        offset: 0,
        listSize: 10,
        totCnt: 0,
      },
      pagingSize: [10, 20, 30],
      componentsTitle: '단축 URL+ & 통계',
      title: '',
      // urlId: '',
      orgUrl: '',
      startDt: null,
      endDt: null,
      itemList: [],
    };
  },
  mounted() {
		// console.log('############## detail mounted ##############', this.urlId)
    this.title = ''
    this.selectUrlInfoStatDetail()
  },
  methods: {
    async selectUrlInfoStatDetail() {
      let params = {
				urlId: this.urlId
			}

      await urlInfoApi.selectUrlInfoStatDetail(params).then((response) => {
        const result = response.data;
        if (result.success) {
          this.itemList = result.data
          this.pageInfo = result.pageInfo
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
  },
};
</script>

<style>
</style>