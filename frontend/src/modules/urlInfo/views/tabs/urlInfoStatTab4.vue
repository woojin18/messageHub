<template>
	<div role="tabpanel" class="tab-pane" id="productCate4">
		<div class="border-line2 pd10 consolMarginTop">
			<p class="color4">· 단축URL+ 클릭한 수신자 정보를 확인할 수 있습니다.<br>· 수신자 중 접속일시가 없는 Data는 클릭을 하지 않은 사용자 입니다.</p>
		</div>
		<!-- 본문 -->
    <div class="row">
      <div class="col-xs-12 mt10">
        <div class="menuBox">		
          <div>
            <h4 class="inline-block" style="width:6%">수신자</h4>
            <div class="inline-block" style="width:20%">
              <input 
								type="text" 
								class="inputStyle" 
								title="수신자 입력란" 
								placeholder="-를 제외한 전화번호 입력"
								v-model="searchCond.recvTelNo" 
								@keydown.enter="goPage(1)">
            </div>
            <h4 class="inline-block ml40" style="width:6%">&nbsp;</h4>
            <div class="inline-block" style="width:15%">
							<input 
								type="checkbox" 
								id="showClickOnly" 
								class="checkStyle2" 
								v-model="searchCond.showClickOnly"
							>
							<label for="showClickOnly">클릭한 수신자만 보기</label>
            </div>
						<a 
							href="#self" 
							class="btnStyle1 float-right"
							@click.prevent="selectUrlInfoStatDetail()"
						>검색</a>
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-xs-12 consolMarginTop">
        <!-- 엑셀 다운로드 -->
        <div class="of_h inline">
          <div class="float-right">
            <a
              class="btnStyle2 borderGray" 
              title="엑셀 다운로드"
              activity="READ"
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
                <col style="width:25%">
                <col style="width:20%">
                <col style="width:20%">
                <col style="width:20%">
								<col style="width:15%">
              </colgroup>
              <thead>
                <tr>
                  <th class="text-center lc-1">수신자</th>
                  <th class="text-center lc-1">발송 일시</th>
                  <th class="text-center lc-1">발송 채널</th>
                  <th class="text-center lc-1">접속 일시</th>
                  <th class="text-center lc-1 end">모바일 여부</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(item, idx) in itemList"
                  :key="idx"
                >
                  <td class="text-center">{{ item.phone |phoneNumAddDash }}</td>
                  <td class="text-center">{{ item.regDt }}</td>
									<td class="text-center">{{ item.finalCh }}</td>
                  <td class="text-center">{{ item.conDt }}</td>
                  <td class="text-center">{{ item.isMobile }}</td>
                </tr>
                <tr
                  v-if="itemList.length === 0"
                >
                  <td class="text-center" colspan="5">데이터가 없습니다.</td>
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
	</div>
</template>

<script>
import confirm from '@/modules/commonUtil/service/confirm.js'
import urlInfoApi from '@/modules/urlInfo/service/urlInfoApi.js'

import SelectLayer from '@/components/SelectLayer'

export default {
  name: 'urlInfoStatTab4',
  components: {
    SelectLayer,
	},
  props: {
		urlId: {
      type: String,
      require: true,
    },
	},
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
      this.selectUrlInfoStatDetail()
		},
		'searchCond.showClickOnly' () {
      this.pageInfo.pageNo = 1
      this.selectUrlInfoStatDetail()
		},
  },
  data() {
    return {
			searchCond: {
        recvTelNo: '',
				showClickOnly: false,
      },
      pageInfo: {
        pageNo: 1,
        offset: 0,
        listSize: 10,
        totCnt: 0,
      },
      pagingSize: [10, 20, 30],
			componentsTitle: '단축 URL+ & 통계 > 클릭 수신자',
      itemList: [],
    };
  },
  mounted() {
    this.selectUrlInfoStatDetail()
  },
  methods: {
    async selectUrlInfoStatDetail() {
      const params = {
				...this.pageInfo,
				urlId: this.urlId,
				tabId: 'tab4',
				recvTelNo: this.searchCond.recvTelNo,
				showClickOnlyYn: this.searchCond.showClickOnly ? 'Y' : 'N',
			}

      await urlInfoApi.selectUrlInfoStatDetail(params).then((response) => {
        const result = response.data;
        if (result.success) {
          this.itemList = result.data.chartData
					this.pageInfo = result.pageInfo
        } else {
          confirm.fnAlert(this.componentsTitle, result.message)
        }
      });
    },
		goPage(pageNo) {
      this.pageInfo.pageNo = pageNo
      this.selectUrlInfoStatDetail()
    },
		//엑셀 다운로드
		downloadExcel() {
      const params = {
				urlId: this.urlId,
				tabId: 'tab4',
				recvTelNo: this.searchCond.recvTelNo,
				showClickOnlyYn: this.searchCond.showClickOnly ? 'Y' : 'N',
			}
			urlInfoApi.excelDownClickRecvList(params)
		},
  },
};
</script>

<style>
</style>