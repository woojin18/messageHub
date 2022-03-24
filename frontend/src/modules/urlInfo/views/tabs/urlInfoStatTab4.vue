<template>
	<div role="tabpanel" class="tab-pane" id="productCate4">
		<div class="row">
			<div class="col-xs-6 consolMarginTop">							
				<h4 class="lc-1 text-left">디바이스</h4>
				<!-- table -->
				<table class="table_skin1 bt-000 tbl-striped">
					<colgroup>
						<col style="width:33%" span="3">							
					</colgroup>
					<thead>
						<tr>
						<th class="text-center lc-1">디바이스</th>
						<th class="text-center lc-1">클릭수</th>
						<th class="text-center lc-1 end">비율</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="text-center">갤럭시</td>
							<td class="text-center">10</td>
							<td class="text-center end">20.00%</td>
						</tr>	
						<tr>
							<td class="text-center">갤럭시</td>
							<td class="text-center">10</td>
							<td class="text-center end">20.00%</td>
						</tr>	
						<tr>
							<td class="text-center">갤럭시</td>
							<td class="text-center">10</td>
							<td class="text-center end">20.00%</td>
						</tr>	
						<tr>
							<td class="text-center">갤럭시</td>
							<td class="text-center">10</td>
							<td class="text-center end">20.00%</td>
						</tr>	
						<tr>
							<td class="text-center">갤럭시</td>
							<td class="text-center">10</td>
							<td class="text-center end">20.00%</td>
						</tr>	
						<tr>
							<td class="text-center">갤럭시</td>
							<td class="text-center">10</td>
							<td class="text-center end">20.00%</td>
						</tr>	
					</tbody>
				</table>
				<!-- //table -->
			</div>	
			
			<div class="col-xs-6 consolMarginTop">							
				<h4 class="lc-1 text-left">브라우저</h4>
				<!-- table -->
				<table class="table_skin1 bt-000 tbl-striped">
					<colgroup>
						<col style="width:33%" span="3">							
					</colgroup>
					<thead>
						<tr>
						<th class="text-center lc-1">브라우저</th>
						<th class="text-center lc-1">클릭수</th>
						<th class="text-center lc-1 end">비율</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="text-center">크롬</td>
							<td class="text-center">10</td>
							<td class="text-center end">20.00%</td>
						</tr>	
						<tr>
							<td class="text-center">크롬</td>
							<td class="text-center">10</td>
							<td class="text-center end">20.00%</td>
						</tr>	
						<tr>
							<td class="text-center">크롬</td>
							<td class="text-center">10</td>
							<td class="text-center end">20.00%</td>
						</tr>	
						<tr>
							<td class="text-center">크롬</td>
							<td class="text-center">10</td>
							<td class="text-center end">20.00%</td>
						</tr>	
						<tr>
							<td class="text-center">크롬</td>
							<td class="text-center">10</td>
							<td class="text-center end">20.00%</td>
						</tr>	
						<tr>
							<td class="text-center">크롬</td>
							<td class="text-center">10</td>
							<td class="text-center end">20.00%</td>
						</tr>	
					</tbody>
				</table>
				<!-- //table -->
			</div>	
		</div>
	</div>
</template>

<script>
import confirm from '@/modules/commonUtil/service/confirm.js'
import urlInfoApi from '@/modules/urlInfo/service/urlInfoApi.js'

export default {
  name: 'urlInfoStatTab4',
  components: {},
  props: {
		urlId: {
      type: String,
      require: true,
    },
	},
  computed: {
  },
  data() {
    return {
			componentsTitle: '단축 URL+ & 통계 > 클릭 수신자',
      itemList: [],
			chartInfo: {},
    };
  },
  mounted() {
    // this.selectUrlInfoStatDetail()
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
          this.itemList = result.data.itemList
          this.chartInfo = result.data.chartInfo
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