<template>
	<div role="tabpanel" class="tab-pane" id="productCate3">
		<div class="border-line2 pd10 consolMarginTop">
			<p class="color4">
				· 유입 기간은 1달을 넘길 수  없습니다. (전일 기준 Data 입니다.)<br>
				· 건수는 유입 기간에 대한 시간대 유입 건수 평균값 또는 합을 나타냅니다.<br>
				· 그래프 위 채널(전체, 문자, RCS, 카카오, Push)을 선택 및 해제 하시면 채널의 표현 개수를 조절할 수 있습니다.<br>
				· 발송실패 또는 처리중 Data는 통계 Data에 포함되지 않습니다.
			</p>
		</div>
		<div class="menuBox consolMarginTop">	
			<div>
				<h4 class="inline-block" style="width:6%">유입일</h4>
				<div class="inline-block" style="width:30%">
					<Calendar
            @update-date="(pickDate) => { searchCond.startDt = pickDate }"
            calendarId="startDt"
            classProps="datepicker inputStyle maxWidth160"
            :initDate="searchCond.startDt"
						:maxDate="searchCond.maxDate"
          ></Calendar> ~ 
          <Calendar
            @update-date="(pickDate) => { searchCond.endDt = pickDate }"
            calendarId="endDt"
            classProps="datepicker inputStyle maxWidth160"
            :initDate="searchCond.endDt"
						:maxDate="searchCond.maxDate"
          ></Calendar>
				</div>		
				<ul class="tab_s2 ml10" style="width:23%">
					<li
            v-for="(item, idx) in periodDayList" 
            :key="idx"
            :class="searchCond.periodDay === item.val ? 'active' : ''">
            <a 
              href="#" 
              :title="`${item.name} 등록일자 검색`"
              @click.prevent="searchCond.periodDay = item.val"
            >{{ item.name }}</a>
          </li>
				</ul>
				<h4 class="inline-block">건수</h4>
				<div class="inline-block" style="width:20%">								
					<input 
						type="radio" 
						value="AVG" 
						id="average" 
						checked="checked"
						v-model="searchCond.compVal"
					>
					<label for="average" class="ml20">평균</label>
					<input 
						type="radio" 
						value="SUM" 
						id="sum"
						v-model="searchCond.compVal"
					>
					<label for="sum">합</label>
				</div>
				<div 
					v-if="isDev"
					class="inline-block">
					<input 
						type="checkbox" 
						id="demo" 
						class="checkStyle2" 
						v-model="demo"
					>
					<label for="demo">Demo</label>
				</div>
				<a 
          href="#self" 
          class="btnStyle1 float-right"
          @click.prevent="selectUrlInfoStatDetail()"
        >검색</a>
			</div>						
		</div>

		<!-- 차트 영역 -->
		<LineChart
      v-if="loaded"
      :chartData="chartData" 
      :chartOptions="chartOption" 
      :height="100"/>
	</div>
</template>

<script>
import confirm from '@/modules/commonUtil/service/confirm.js'
import urlInfoApi from '@/modules/urlInfo/service/urlInfoApi.js'

import Calendar from '@/components/Calendar'
import LineChart from '@/components/LineChart'

export default {
  name: 'urlInfoStatTab3',
  components: {
		Calendar,
    LineChart,
	},
  props: {
		urlId: {
      type: String,
      require: true,
    },
	},
  computed: {
  },
	watch: {
    'searchCond.periodDay' (curVal) {
      this.$set(this.searchCond, 'startDt', this.$gfnCommonUtils.strDateAddDay(this.$gfnCommonUtils.getCurretDate(), -1*curVal -1))
      this.$set(this.searchCond, 'endDt', this.$gfnCommonUtils.strDateAddDay(this.$gfnCommonUtils.getCurretDate(), -1))

      this.selectUrlInfoStatDetail()
		},
		'searchCond.compVal' () {
      this.selectUrlInfoStatDetail()
		},
  },
  data() {
    return {
			componentsTitle: '단축 URL+ & 통계 > 타임라인(시간)',
      searchCond: {
        startDt: this.$gfnCommonUtils.strDateAddDay(this.$gfnCommonUtils.getCurretDate(), -8),
        endDt: this.$gfnCommonUtils.strDateAddDay(this.$gfnCommonUtils.getCurretDate(), -1),
				maxDate : this.$gfnCommonUtils.strDateAddDay(this.$gfnCommonUtils.getCurretDate(), -1),
        periodDay: 7,
				compVal: 'AVG',
      },
      chartList: [],
      chartData: null,
      chartOption: {
				responsive: true,
				scales: {
					yAxes: [{
						ticks: {
							beginAtZero: true,
							callback: function(value, /*index, values*/) {
								if (parseInt(value) >= 1000) {
									return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '건'
								} else {
									return value + '건'
								}
							}
						},
					}]
				}
			},
      loaded: false,
      borderColor: [
				'#4A7AF7', '#83BFFA', '#E86560', '#F1AA72', '#F6CF74', '#F361DC', '#D5D5D5', '#F29661', '#CEF279', '#B2CCFF',
				'#B2CCFF', '#DE4F4F', '#FAED7D', '#41FF3A', '#F35AA6', '#949494', '#FFFF6C', '#79ABFF', '#FF3636', '#FF9797'
			],
      periodDayList: [
        {val:7, name: '1주일'},
        {val:15, name: '15일'},
      ],
      demo: false,
			isDev: true,
    };
  },
  mounted() {
    this.selectUrlInfoStatDetail()
  },
  methods: {
    async selectUrlInfoStatDetail() {
      const {startDt, endDt} = this.searchCond
      const diffTm = new Date(endDt).getTime() - new Date(startDt).getTime()
      const diffDay = (diffTm) / (1000*60*60*24)

      if(!this.demo && diffDay > 30){
        confirm.fnAlert(this.componentsTitle, '유입 기간은 1달을 넘길 수 없습니다.')
        return
      }

      this.loaded = false

      let params = {
				urlId: this.urlId,
				tabId: 'tab3',
        startDt: this.searchCond.startDt,
        endDt: this.searchCond.endDt,
				compVal: this.searchCond.compVal,
			}

      await urlInfoApi.selectUrlInfoStatDetail(params).then((response) => {
        const result = response.data;
        if (result.success) {
          this.chartList = result.data.chartData

          // 라인 차트 생성
          this.drawChart()
        } else {
          confirm.fnAlert(this.componentsTitle, result.message)
        }
      });
    },
    // 라인 차트 생성
    drawChart() {
			const dateLine = []
			const smsCnt = []
      const rcsCnt = []
      const kakaoCnt = []
      const pushCnt = []

      this.chartList.forEach((item) => {
        if(this.demo){
					if(item.sms === 0)
						item.sms = 50
					if(item.rcs === 0)
						item.rcs = 50
					if(item.kakao === 0)
						item.kakao = 50
					if(item.push === 0)
						item.push = 50

          const rand1 = Math.floor(Math.random() * 100)
          const rand2 = Math.floor(Math.random() * 100)
          const rand3 = Math.floor(Math.random() * 100)
          const rand4 = Math.floor(Math.random() * 100)
          item.sms += rand1
          item.rcs += rand2
          item.kakao += rand3
          item.push += rand4
        }

        dateLine.push(item.hh)
        smsCnt.push(item.sms)
        rcsCnt.push(item.rcs)
        kakaoCnt.push(item.kakao)
        pushCnt.push(item.push)
      })

			this.chartData = {
				labels: dateLine,
				datasets: [
					{
						label: '문자',
						borderColor: this.borderColor[1],
						pointBackgroundColor: 'white',
						borderWidth: 2,
						pointBorderColor: '#249EBF',
						fill: false,
						data: smsCnt,
					},
					{
						label: 'RCS',
						borderColor: this.borderColor[2],
						pointBackgroundColor: 'white',
						borderWidth: 2,
						pointBorderColor: '#249EBF',
						fill: false,
						data: rcsCnt,
					},
          {
						label: '카카오',
						borderColor: this.borderColor[3],
						pointBackgroundColor: 'white',
						borderWidth: 2,
						pointBorderColor: '#249EBF',
						fill: false,
						data: kakaoCnt,
					},
          {
						label: 'Push',
						borderColor: this.borderColor[4],
						pointBackgroundColor: 'white',
						borderWidth: 2,
						pointBorderColor: '#249EBF',
						fill: false,
						data: pushCnt,
					},
				]
			}

			this.$forceUpdate()
      this.loaded = true
    },
  },
};
</script>

<style>
</style>