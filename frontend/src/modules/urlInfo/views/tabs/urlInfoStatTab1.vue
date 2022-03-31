<template>
	<div role="tabpanel" class="tab-pane active" id="productCate1">
		<div class="border-line2 pd10 consolMarginTop">
			<p class="color4">
				* 발송 클릭 율 : 총 발송수에 대한 클릭률 로서 고객의 채널에 대한 반응을 비율로 확인 할 수 있습니다.<br>
				* 발송 개수 및 클릭 수 등의 수치는 전일 기준 Data 입니다.
			</p>
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
							v-for="(item, idx) in itemList" :key="idx"
						>
							<td class="text-center">{{ item.ch }}</td>
							<td class="text-center">{{ item.totCnt }}</td>
							<td class="text-center">{{ item.conCnt }}</td>
							<td class="text-center end">{{ item.per }}%</td>
						</tr>	
					</tbody>
				</table>
				<!-- //table -->

				<!-- 차트 영역 -->
				<h4 class="lc-1 text-left">채널 별 고객 클릭 반응 추이 (발송 클릭율)</h4>
				<div class="Dashboard01 fl border-line">
					<DoughnutChart 
						v-if="loaded" 
						:chartData="chartData" 
						:chartOptions="chartOptions" 
						:height="60"/>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import confirm from '@/modules/commonUtil/service/confirm.js'
import urlInfoApi from '@/modules/urlInfo/service/urlInfoApi.js'

import DoughnutChart from '@/components/DoughnutChart'

export default {
  name: 'urlInfoStatTab1',
  components: {
		DoughnutChart,
	},
  props: {
		urlId: {
      type: String,
      require: true,
			default: '',
    },
	},
  computed: {},
	watch: {
		'urlId' () {
      this.selectUrlInfoStatDetail()
		},
	},
  data() {
    return {
			componentsTitle: '단축 URL+ & 통계 > 유입채널',
      itemList: [],
			chartList: [],
			chartData: null,            
			chartOptions: {
				responsive: true,
				legend: {
					position: 'bottom',
				},
				title: {
					display: false,
					text: '채널 별 고객 클릭 반응 추이 (발송 클릭율)'
				},
				animation: {
					animateScale: true,
					animateRotate: true
				},
				tooltips: {
					callbacks: {
						label: (tooltipItem, data) => {
							var dataset = data.datasets[tooltipItem.datasetIndex];
							var total = dataset.data.reduce((previousValue, currentValue, /*currentIndex, array*/) => {
									return previousValue + currentValue;
							});
							var currentValue = dataset.data[tooltipItem.index];
							var percentage = Math.floor(((currentValue/total) * 100)+0.5);
							return percentage + "%";
						}
					}
				}
			},
			loaded: false,
    };
  },
  mounted() {
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
          this.itemList = [...result.data.chartData]
          this.chartList = [...result.data.chartData]

					// '전체' 행 생성
					this.makeSumRow()

					// 도넛 차트 생성
					this.drawChart()
        } else {
          confirm.fnAlert(this.componentsTitle, result.message)
        }
      });
    },
		// 도넛차트 생성
		drawChart() {
			const labels = []
			const dataset = []

			this.chartList.forEach((item) => {
				switch (item.ch) {
					case '-':
						item.ch = '발송실패 또는 처리중'	
						break;
					case 'SMS':
						item.ch = '문자'	
						break;
					case 'KAKAO':
						item.ch = '카카오'	
						break;
					default:
						break;
				}

				labels.push(item.ch)
				dataset.push(item.conCnt)
			})

			this.chartData = {
				labels,
				datasets: [
					{
						data: dataset,
						backgroundColor: [
							'rgb(255, 99, 132)',
							'rgb(54, 162, 235)',
							'rgb(255, 205, 86)',
							'rgb(247, 153, 91)',
							'rgb(52, 181, 121)',
							'rgb(117, 63, 135)',
						],
						hoverOffset: 4,
					}
				]
			}

			this.loaded = true
		},
		makeSumRow() {
			let conCntSum = 0
			let totCntSum = 0
			this.itemList.forEach((item) => {
				if(item.conCnt && item.conCnt > 0)
					conCntSum += item.conCnt

				if(item.totCnt && item.totCnt > 0)
					totCntSum += item.totCnt
			})

			const sumRow = {
				ch: '전체',
				conCnt: conCntSum,
				totCnt: totCntSum,
				per: (totCntSum === 0) ? 0 : Math.trunc(conCntSum / totCntSum * 100)
			}

			this.itemList.splice(0, 0, sumRow)
		},
  },
};
</script>

<style>
</style>