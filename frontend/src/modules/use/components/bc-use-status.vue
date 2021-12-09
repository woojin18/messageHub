<template>
	<div class="mt20">
		<article>
			<div class="row">
				<div class="col-xs-6">
					<h4 class="topH4">이용(발송성공)건수 추이 (최근 6개월)</h4>
					<div class="Dashboard01 fl border-line">
						<bar-chart v-if="loaded" :chartData="sixMonthUsedCntData" :height="200"></bar-chart>
					</div>
				</div>
				<div class="col-xs-6">
					<h4 class="topH4">이용서비스별추이 (최근 6개월)</h4>
					<div class="Dashboard01 fl border-line">
						<DoughnutChart v-if="loaded" :chartData="doughnutChartData" :chartOptions="doughnutOptions" :height="200"></DoughnutChart>
					</div>
				</div>
			</div>
			<div class="row mt10">
				<div class="col-xs-6">
					<h4>선불 결제내역 (최근 6개월)</h4>
					<div class="Dashboard01 fl border-line">
						<table class="table_skin1 bt-000 tbl-striped consolMarginTop">
							<colgroup>
								<col style="width:33%">
								<col style="width:33%">
								<col>
							</colgroup>
							<thead>
								<tr>
									<th class="text-center lc-1">결제 월</th>
									<th class="text-center lc-1">결제 건</th>
									<th class="text-center lc-1 end">결제 금액</th>
								</tr>
							</thead>
							<tbody>
								<tr v-for="(data, index) in prePayItems" :key="index">
									<td class="text-center">{{ data.payMonth }}</td>
									<td class="text-center">{{ data.payCnt | comma}}</td>
									<td class="text-right end">{{ data.prepaymentAmount | comma}}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="col-xs-6">
					<h4>후불 청구내역 (최근 6개월)</h4>
					<div class="Dashboard01 fl border-line">
							<table class="table_skin1 bt-000 tbl-striped consolMarginTop">
								<colgroup>
									<col style="width:33.3%">
									<col style="width:33.3%">
									<col style="width:33.3%">
									<col>
								</colgroup>
								<thead>
									<tr>
									<th class="text-center lc-1">청구월</th>
									<th class="text-center lc-1">가입번호</th>
									<th class="text-center lc-1 end">성공건수</th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="(data, index) in defPayItems" :key="index">
										<td class="text-center">{{ data.ym }}</td>
										<td class="text-right">{{ data.serviceId }}</td>
										<td class="text-right end">{{ data.calcAmount | comma}}</td>
									</tr>
								</tbody>
							</table>
					</div>
				</div>
			</div>
		</article>
	</div>
</template>

<script>
import BarChart from '@/components/Chart.vue';
import DoughnutChart from '@/components/DoughnutChart.vue';
import useApi from '../service/useApi'

export default {
	components: {
		BarChart,
		DoughnutChart,
	},
		props: {
		//
	},
	data() {
		return {
			componentsTitle: '이용현황',
			prePayItems: [],	// 선불결제내역
			defPayItems: [],	//후불청구내역
			// useCntItem: [],
			chanUseCntItem: [],

			sixMonthUsedCntData: null,			// 이용건수추이 차트 정의
			sixMonthUsedCntDataLabels: [],		// 이용건수추이 라벨
			sixMonthUsedCntDatasetDatas: [],	// 이용건수추이 Data

			doughnutChartData: null,			// 이용서비스별추이 차트 정의
			doughnutOptions: null,				// 이용서비스별추이 옵션 옵션 정의
			doughnutChartDataLabels: [],		// 이용서비스별추이 라벨
			doughnutChartDataDatas: [],			// 이용서비스별추이 Data

			loaded: false,
		}
	},
	filters:{
		comma(val) {
			return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		}
	},
	mounted() {
		this.fnSearch();
	},
	watch: {
		//
	},
	methods: {
		// 차트생성
		fillData() {
			// 바차트
			this.sixMonthUsedCntData = {
				labels: this.sixMonthUsedCntDataLabels,
				datasets: [
					{
						label: '이용건수',
						backgroundColor: '#f87979',
						pointBackgroundColor: 'white',
						borderWidth: 1,
						//Data to be represented on y-axis
						pointBorderColor: '#249EBF',
						data: this.sixMonthUsedCntDatasetDatas,
					},
				]
			};
			// 도넛차트
			this.doughnutChartData= {
				labels: this.doughnutChartDataLabels ,
				datasets: [
					{
						data: this.doughnutChartDataDatas,
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
			};
			this.doughnutOptions = {
				responsive: true,
				legend: {
					position: 'bottom',
				},
				title: {
					display: false,
					text: '이용서비스별 추이'
				},
				animation: {
					animateScale: true,
					animateRotate: true
				},
				tooltips: {
					callbacks: {
						label: function(tooltipItem, data) {
							var dataset = data.datasets[tooltipItem.datasetIndex];
							var total = dataset.data.reduce(function(previousValue, currentValue, currentIndex, array) {
								return previousValue + currentValue;
							});
							var currentValue = dataset.data[tooltipItem.index];
							var percentage = Math.floor(((currentValue/total) * 100)+0.5);
							return percentage + "%";
						}
					}
				}
			};
			this.loaded = true;
		},
		fnSearch() {
			this.fnSelectSixMonthUseCount();
			this.fnSelectSixMonthPrePayAmt();
			this.fnSelectSixMonthDefPayAmt();
		},
		// 이용건수 조회
		async fnSelectSixMonthUseCount() {
			let params = {};
			let vm = this;
			await useApi.selectSixMonthUseCount(params).then(response =>{
				let result = response.data;
				if(result.success) {
					// 이용건수추이 차트 Data Set
					let useCntItem = result.data.sixMonthUseCnt;
					for( let i = 0; i < useCntItem.length; i++) {
						vm.sixMonthUsedCntDataLabels[i] = useCntItem[i].ym;
						vm.sixMonthUsedCntDatasetDatas[i] = useCntItem[i].cnt;
					}

					// 이용서비스별추이 차트 Data Set
					let sixMonthChanUseCntItem = result.data.sixMonthChanUseCnt;
					for( let i = 0; i < sixMonthChanUseCntItem.length; i++) {
						vm.doughnutChartDataLabels[i] = sixMonthChanUseCntItem[i].ch;
						vm.doughnutChartDataDatas[i] = sixMonthChanUseCntItem[i].cnt;
					}

					this.fillData(); // 차트생성
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
		// 선불결제내역 조회
		async fnSelectSixMonthPrePayAmt() {
			var params = {};
			await useApi.selectSixMonthPrePayAmtList(params).then(response =>{
				var result = response.data;
				if(result.success) {
					this.prePayItems = result.data;
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
		// 후불결제내역 조회
		async fnSelectSixMonthDefPayAmt() {
			var params = {};
			await useApi.selectSixMonthDefPayAmt(params).then(response =>{
				var result = response.data;
				if(result.success) {
					this.defPayItems = result.data;
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
	},
};
</script>
