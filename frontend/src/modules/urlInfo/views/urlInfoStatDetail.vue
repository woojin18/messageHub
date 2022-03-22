<template>
	<article>
		<div class="contentHeader">
			<h2>LAB > {{ componentsTitle }} > {{ urlInfo.title }}</h2>
		</div>

			<!-- 본문 -->
			<div class="row">
				<div class="col-xs-12 consolMarginTop">
					<!-- table -->
					<table class="table_skin1 bt-000 tbl-striped">
						<colgroup>
							<col style="width:10%">
							<col style="width:10%">
							<col style="width:*%">
							<col style="width:15%">
							<col style="width:8%">
							<col style="width:8%">
							<col style="width:8%">
						</colgroup>
						<thead>
							<tr>								
								<th class="text-center lc-1">제목</th>
								<th class="text-center lc-1">단축URL+ ID</th>
								<th class="text-center lc-1">원본URL</th>
								<th class="text-center lc-1">등록일/만료일</th>
								<th class="text-center lc-1">총 발송수</th>
								<th class="text-center lc-1">클릭수</th>
								<th class="text-center lc-1 end">클릭율</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-center">{{ urlInfo.title }}</td><!-- 제목 -->
								<td class="text-center">{{ urlInfo.urlId }}</td><!-- 단축URL+ ID -->
								<td>{{ urlInfo.orgUrl }}</td><!-- 원본URL -->
								<td class="text-center">{{ `${$moment(urlInfo.regDt).format('yyyy.mm.DD')}/${$moment(urlInfo.expDt).format('yyyy.mm.DD')}` }}</td><!-- 등록일/만료일 -->
								<td class="text-right">{{ urlInfo.totCnt }}</td><!-- 총 발송수 -->
								<td class="text-right">{{ urlInfo.conCnt }}</td><!-- 클릭수 -->
								<td class="text-right end">{{ (urlInfo.totCnt === 0) ? 0 : Math.trunc(urlInfo.conCnt / urlInfo.totCnt * 100) }}%</td><!-- 클릭율 -->
							</tr>
						</tbody>
					</table>
					<!-- //table -->
				</div>			
			</div>

			<!-- 탭 메뉴 -->
			<ul class="tab_s6 mt50" role="tablist">
				<li 
					v-for="(item, idx) in tabList" :key="idx"
					role="presentation" 
					:class="selTabId === (idx+1) ? 'active' : ''">
					<a 
						:href="`#productCate${idx+1}`" 
						:aria-controls="`productCate${idx+1}`" 
						role="tab" 
						data-toggle="tab"
					>{{ item.tabNm }}</a>
				</li>
			</ul>

			<div class="tab-content" style="border-top:1px solid #D5D5D5">

				<!--  tab1 -->
				<div role="tabpanel" class="tab-pane active" id="productCate1">
					<urlInfoStatTab1 :urlId.sync="urlId"/>
				</div>

				<!--  tab2 -->
				<div role="tabpanel" class="tab-pane" id="productCate2">
					<urlInfoStatTab2 :urlId.sync="urlId"/>
				</div>

				<!--  tab3 -->
				<div role="tabpanel" class="tab-pane" id="productCate3">
					<urlInfoStatTab3 :urlId.sync="urlId"/>
				</div>

				<!--  tab4 -->
				<div role="tabpanel" class="tab-pane" id="productCate4">
					<urlInfoStatTab4 :urlId.sync="urlId"/>
				</div>
			</div>
			<!-- //본문 -->

		<!-- <footer>Copyright©LG Plus Corp. All Rights Reserved.</footer> -->
	</article>
</template>

<script>
import confirm from '@/modules/commonUtil/service/confirm.js'
import urlInfoApi from '@/modules/urlInfo/service/urlInfoApi.js'

import urlInfoStatTab1 from "@/modules/urlInfo/views/tabs/urlInfoStatTab1"
import urlInfoStatTab2 from "@/modules/urlInfo/views/tabs/urlInfoStatTab2"
import urlInfoStatTab3 from "@/modules/urlInfo/views/tabs/urlInfoStatTab3"
import urlInfoStatTab4 from "@/modules/urlInfo/views/tabs/urlInfoStatTab4"

export default {
  name: 'urlInfoStatDetail',
  components: {
		urlInfoStatTab1,
		urlInfoStatTab2,
		urlInfoStatTab3,
		urlInfoStatTab4,
	},
  props: {
		urlId: {
      type: String,
      require: true,
    },
		urlInfo: {
      type: Object,
      require: false,
			default: () => {
				return {
					title: '',
					urlId: '',
					orgUrl: '',
					regDt: null,
					expDt: null,
					totCnt: 0,
					conCnt: 0,
				}
			}
    },
	},
  computed: {},
  data() {
    return {
      componentsTitle: '단축 URL+ & 통계',
      itemList: [],
			tabList: [
				{tabNm: '유입채널'},
				{tabNm: '타임라인(일)'},
				{tabNm: '타임라인(시간)'},
				{tabNm: '클릭 수신자'},
			],
			selTabId: 1,
    };
  },
  mounted() {
		console.log('######## detail moutend #######', this.$route.params);
		// console.log('########### urlId #############', this.urlId);
		// console.log('########### urlInfo #############', this.urlInfo);
    // this.selectUrlInfoStatDetail()
  },
  methods: {
    // async selectUrlInfoStatDetail() {
    //   let params = {
		// 		urlId: this.urlId
		// 	}

    //   await urlInfoApi.selectUrlInfoStatDetail(params).then((response) => {
    //     const result = response.data;
    //     if (result.success) {
    //       this.itemList = result.data
    //       // this.pageInfo = result.pageInfo
    //     } else {
    //       confirm.fnAlert(this.componentsTitle, result.message);
    //     }
    //   });
    // },
  },
};
</script>

<style>
</style>