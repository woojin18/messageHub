<template>
    <div class="mt20">
        <article>
            <div class="row mt15">
                <div class="col-xs-12">
                    <div class="menuBox">
                        <div class="of_h">
                            <div class="inline-block" style="width:10%"><h4>조회기간</h4></div>
                            <div class="inline-block" style="width:30%">
                                <input type="text" id="startDate" class="monthpicker inputStyle maxWidth120 mr5" :value="searchData.searchStartDate" maxlength="7" readonly>
                                ~
                                <input type="text" id="endDate" class="monthpicker inputStyle maxWidth120 mr5" :value="searchData.searchEndDate" maxlength="7"  readonly>
                            </div>
                            <ul class="tab_s2 ml20">
                                <li :class="this.searchDateInterval==1 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(1);" title="1개월 서비스 검색">1개월</a></li>
                                <li :class="this.searchDateInterval==3 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(3);" title="3개월 서비스 검색">3개월</a></li>
                                <li :class="this.searchDateInterval==6 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(6);" title="6개월 서비스 검색">6개월</a></li>
                            </ul>
                        </div>

                        <div class="of_h consolMarginTop">
                            <div class="inline-block" style="width:10%"><h4>채널</h4></div>
                            <div class="inline-block" style="width:88%">
                                <div class="consolCheck consolMarginTop">
                                    <input type="checkbox" id="otherUse_all" class="checkStyle2" checked @change="fnCheckAll($event)"><label for="otherUse_all" class="mr30">전체</label>
                                    <input type="checkbox" id="otherUse1" class="checkStyle2" value="SMS" v-model="searchData.checkedChannel"><label for="otherUse1" class="mr30">문자</label>
                                    <input type="checkbox" id="otherUse2" class="checkStyle2" value="RCS" v-model="searchData.checkedChannel"><label for="otherUse2" class="mr30">RCS</label>
                                    <input type="checkbox" id="otherUse3" class="checkStyle2" value="KAKAO" v-model="searchData.checkedChannel"><label for="otherUse3" class="mr30">카카오톡</label>
                                    <input type="checkbox" id="otherUse4" class="checkStyle2" value="PUSH" v-model="searchData.checkedChannel"><label for="otherUse4" class="mr30">PUSH</label>
                                </div>
                            </div>
                        </div>

                        <div class="of_h consolMarginTop">
                            <div class="inline-block" style="width:10%"><h4>검색조건</h4></div>
                            <div class="inline-block" style="width:15%">
                                <input type="radio" name="useDate1" value="ALL" id="useDate1-1" v-model="searchData.searchType" checked> <label for="useDate1-1">전체</label>
                                <input type="radio" name="useDate1" value="PROJECT" id="useDate1-2" v-model="searchData.searchType"> <label for="useDate1-2" class="ml10">프로젝트</label>
                            </div>
                            <div class="inline-block" style="width:68%" v-if="searchData.searchType == 'PROJECT'">
                                <select name="userConsole_sub040501_1" class="selectStyle2" style="width:18%" title="검색조건 선택란" @change="fnSelectProject($event)">
                                    <option value="">선택하세요</option>
                                    <option v-for="(content, index) in projectItems" :key="index" :value="content.projectId">{{ content.projectName }}</option>
                                </select>
                                <a class="ml20" v-for="(content, index) in searchData.projectItems" @click="$delete(searchData.projectItems, index)">{{ content.projectName }} <i class="fal fa-times"></i></a>
                            </div>
                            <a @click="fnSearch" class="btnStyle1 float-right" activity="READ">조회</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row mt20">
                <div class="col-xs-6">
                    <h4 class="topH4">이용(발송성공)건수 추이</h4>
                    <div class="Dashboard01 fl border-line">
                        <bar-chart v-if="loaded" :chartData="sixMonthUsedCntData" :height="200"></bar-chart>
                    </div>
                </div>
                <div class="col-xs-6">
                    <h4 class="topH4">이용서비스별추이</h4>
                    <div class="Dashboard01 fl border-line">
                        <DoughnutChart v-if="loaded" :chartData="doughnutChartData" :chartOptions="doughnutOptions" :height="200"></DoughnutChart>
                    </div>
                </div>
            </div>
        </article>
    </div>
</template>

<script>
import Calendar from "@/components/Calendar.vue";
import statisticsAdminApi from '../../statisticsAdmin/service/statisticsAdminApi'
import confirm from "@/modules/commonUtil/service/confirm";
import BarChart from '@/components/Chart.vue';
import DoughnutChart from '@/components/DoughnutChart.vue';
import useApi from '../service/useApi'

export default {
    components: {
        Calendar,
        BarChart,
        DoughnutChart,
    },
    props: {
        searchData : {
            type: Object,
            require: false,
            default: function() {
                return {
                    'searchStartDate' : this.$gfnCommonUtils.strDateAddMonth(this.$gfnCommonUtils.getCurretDate(), -1),
                    'searchEndDate' : this.$gfnCommonUtils.strDateAddMonth(this.$gfnCommonUtils.getCurretDate(), 0),
                    searchType : 'ALL',
                    checkedChannel : ['SMS','RCS','KAKAO','PUSH'],
                    projectItems: []
                }
            }
        }
    },
    data() {
        return {
            searchAll : 'Y',
            searchDateInterval: 1,
            componentsTitle: '이용현황',
            prePayItems: [],    // 선불결제내역
            defPayItems: [],    //후불청구내역
            // useCntItem: [],
            chanUseCntItem: [],

            sixMonthUsedCntData: null,          // 이용건수추이 차트 정의
            sixMonthUsedCntDataLabels: [],      // 이용건수추이 라벨
            sixMonthUsedCntDatasetDatas: [],    // 이용건수추이 Data

            doughnutChartData: null,            // 이용서비스별추이 차트 정의
            doughnutOptions: null,              // 이용서비스별추이 옵션 옵션 정의
            doughnutChartDataLabels: [],        // 이용서비스별추이 라벨
            doughnutChartDataDatas: [],         // 이용서비스별추이 Data

            loaded: false,
            projectItems: []
        }
    },
    filters:{
        comma(val) {
            return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        }
    },
    mounted() {
        this.fnCalendarInit();
        this.fnSetIntervalSearchDate(this.searchDateInterval);
        this.fnSearchProjectList();
        this.fnSearch();
    },
    methods: {
        fnCalendarInit() {
            jQuery("#startDate").monthpicker({
                monthNames:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
                monthNamesShort:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
                changeYear:false,
                dateFormat:'yy-mm',
                buttonImage:'../../se2/images/datepicker.png',
                buttonImageOnly: true,
                showOn: 'button',
                maxDate: this.$gfnCommonUtils.strDateAddMonth(this.$gfnCommonUtils.getCurretDate(), 0)
            });
            jQuery("#endDate").monthpicker({
                monthNames:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
                monthNamesShort:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
                changeYear:false,
                dateFormat:'yy-mm',
                buttonImage:'../../se2/images/datepicker.png',
                buttonImageOnly: true,
                showOn: 'button',
                maxDate: this.$gfnCommonUtils.strDateAddMonth(this.$gfnCommonUtils.getCurretDate(), 0)
            });
        },
        //검색일자변경
        fnSetIntervalSearchDate(interval){
            this.searchDateInterval = interval;
            this.searchData.searchStartDate = this.$gfnCommonUtils.strDateAddMonth(this.$gfnCommonUtils.getCurretDate(), -this.searchDateInterval);
            this.searchData.searchEndDate = this.$gfnCommonUtils.strDateAddMonth(this.$gfnCommonUtils.getCurretDate(), 0);
        },
        // 유효성 검사
        fnChkValidation() {
            // 5년전 월
            let date = new Date();
            date.setFullYear(date.getFullYear() -5);
            let beforeFiveYear = this.$gfnCommonUtils.formatDate(date, 'yyyymm');

            // 종료월의 12개월 이전 월
            let searchStartMonth = jQuery("#startDate").val();
            let searchEndMonth = jQuery("#endDate").val();

            let dateParts = searchEndMonth.split('-');
            let endMonth = new Date(dateParts[0], dateParts[1] - 1);
            endMonth.setMonth(endMonth.getMonth()-12);
            let before12Month = this.$gfnCommonUtils.formatDate(endMonth, 'yyyymm');

            if(searchStartMonth && searchEndMonth) {
                if(searchStartMonth.replace(/[^0-9]/g, '') > searchEndMonth.replace(/[^0-9]/g, '')){
                    confirm.fnAlert(this.title, '시작월은 종료월보다 클 수 없습니다.');
                    return false;
                }
            }

            if(searchStartMonth.replace(/[^0-9]/g, '') < beforeFiveYear
                || searchEndMonth.replace(/[^0-9]/g, '') < beforeFiveYear) {
                    confirm.fnAlert(this.title, '발송일 기준 최근 5년까지 조회가 가능합니다.');
                    return false;
                }

            if(searchStartMonth.replace(/[^0-9]/g, '') <= before12Month) {
                confirm.fnAlert(this.title, '최대 조회 가능 범위는 12개월 입니다.');
                return false;
            }
            return true;
        },
        fnCheckAll(e) {
            if (e.target.checked) {
                this.searchData.checkedChannel = ['SMS','RCS','KAKAO','PUSH'];
            } else {
                this.searchData.checkedChannel = [];
            }
        },
        fnSelectProject(e) {
            if (e.target.selectedIndex < 1) return;
            var project = this.projectItems[e.target.selectedIndex-1];

            var isExist = false
            this.searchData.projectItems.forEach((value, index) => {
                if (value.projectId == project.projectId) {
                    isExist = true 
                }
            });
            if (isExist) return;
            this.searchData.projectItems.push(project);
        },
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
            if(!this.fnChkValidation()) return false;
            this.searchData.searchStartDate = jQuery("#startDate").val();
            this.searchData.searchEndDate = jQuery("#endDate").val();
            this.fnSelectSixMonthUseCount();
        },
        //프로젝트 목록 조회
        async fnSearchProjectList() {
            var params = {
            }
            await statisticsAdminApi.selectProjectList(params).then(response =>{
                var result = response.data;
                if(result.success) {
                    this.projectItems = result.data;
                } else {
                    confirm.fnAlert(this.title, result.message);
                }
            });
        },
        // 이용건수 조회
        async fnSelectSixMonthUseCount() {
            let params = Object.assign({}, this.searchData);
            let vm = this;
            vm.sixMonthUsedCntDataLabels = []
            vm.sixMonthUsedCntDatasetDatas = []
            vm.doughnutChartDataLabels = []
            vm.doughnutChartDataDatas = []
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
        }
    },
};
</script>
