<template>
	<div role="tabpanel" class="tab-pane active" id="productCate1">
		<div class="border-line2 pd10 consolMarginTop">
			<p class="color4">* 발송 클릭 율 : 총 발송수에 대한 클릭률 로서 고객의 채널에 대한 반응을 비율로 확인 할 수 있습니다.</p>
		</div>
		<div class="row">
			<div class="col-xs-12 consolMarginTop">
				<!-- table -->
				<table class="table_skin1 bt-000 tbl-striped">
					<colgroup>
						<col style="width:25%" span="4">							
					</colgroup>
					<thead>
						<tr>
							<th class="text-center lc-1">채널</th>
							<th class="text-center lc-1">총 발송수</th>
							<th class="text-center lc-1">클릭수</th>
							<th class="text-center lc-1 end">발송 클릭율
								<i class="fas fa-question-circle toolTip ml5">
									<span class="toolTipText" style="width:250px">총 발송수에 대한 클릭율(%)<br>고객의 채널에 대한 반응을확인할 수 있습니다.</span>
								</i>
							</th>
						</tr>
					</thead>
					<tbody>
						<tr
							v-for="(item, idx) in chartList" :key="idx"
						>
							<td class="text-center">{{ item.ch }}</td>
							<td class="text-center">{{ item.totCnt }}</td>
							<td class="text-center">{{ item.conCnt }}</td>
							<td class="text-center end">{{ item.per }}%</td>
						</tr>	
						<!-- <tr>
							<td class="text-center">전체</td>
							<td class="text-center">100</td>
							<td class="text-center">10</td>
							<td class="text-center end">10.00%</td>
						</tr>	
						<tr>
							<td class="text-center">전체</td>
							<td class="text-center">100</td>
							<td class="text-center">10</td>
							<td class="text-center end">10.00%</td>
						</tr>	
						<tr>
							<td class="text-center">전체</td>
							<td class="text-center">100</td>
							<td class="text-center">10</td>
							<td class="text-center end">10.00%</td>
						</tr>	
						<tr>
							<td class="text-center">전체</td>
							<td class="text-center">100</td>
							<td class="text-center">10</td>
							<td class="text-center end">10.00%</td>
						</tr>	 -->
					</tbody>
				</table>
				<!-- //table -->

				<!-- 차트 영역 -->
				<h4 class="lc-1 text-left">채널 별 고객 클릭 반응 추이 (발송 클릭율)</h4>
				<img src="common/images/chart210614_1.png" alt="차트210614_1" class="img_chart02 img-responsive mt20">
			</div>			
		</div>
	</div>
</template>

<script>
import confirm from '@/modules/commonUtil/service/confirm.js'
import urlInfoApi from '@/modules/urlInfo/service/urlInfoApi.js'

export default {
  name: 'urlInfoStatTab1',
  components: {},
  props: {
		urlId: {
      type: String,
      require: true,
			default: '',
    },
	},
  computed: {},
	watch: {
		'urlId' (curVal) {
			console.log('############ watch / urlId ############', curVal);
      this.selectUrlInfoStatDetail()
		},
	},
  data() {
    return {
			componentsTitle: '단축 URL+ & 통계 > 유입채널',
      itemList: [],
			chartList: [],
    };
  },
  mounted() {
		console.log('############## mounted / urlId #############', this.urlId);
		// if(this.urlId)
			this.selectUrlInfoStatDetail()
  },
  methods: {
    async selectUrlInfoStatDetail() {
      let params = {
				urlId: this.urlId,
				tabId: 'tab1',
			}

      await urlInfoApi.selectUrlInfoStatDetail(params).then((response) => {
        const result = response.data;
        if (result.success) {
          this.itemList = result.data.chart1
          this.chartList = result.data.chart1
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