<template>
	<div role="tabpanel" class="tab-pane" id="productCate2">
		<div class="border-line2 pd10 consolMarginTop">
			<p class="color4">· 일자 별 채널의 유입되는 수를 보여줍니다.<br>· 유입 기간은 15일을 넘길 수  없습니다.<br>· 그래프 위 채널(전체, 문자, RCS, 카카오, Push)을 선택 및 해제 하시면 채널의 표현 개수를 조절할 수 있습니다.</p>
		</div>
		<div class="menuBox consolMarginTop">	
			<div>
				<h4 class="inline-block" style="width:6%">유입일</h4>
				<div class="inline-block" style="width:25%">
					<input type="text" class="datepicker inputStyle hasDatepicker" title="시작날짜 입력란" id="dp1623916254295" style="width:48%"> ~ 
					<input type="text" class="datepicker inputStyle hasDatepicker" title="시작날짜 입력란" id="dp1623916254295" style="width:48%">
				</div>		
				<ul class="tab_s2 ml10">
					<li class="active"><a href="#" title="1주일 등록일자 검색">1주일</a></li>
					<li><a href="#" title="15일 등록일자 검색">15일</a></li>
				</ul>						
				<a href="#self" class="btnStyle1 float-right">검색</a>
			</div>						
		</div>

		<!-- 차트 영역 -->
		<img src="common/images/chart210614_1.png" alt="차트210614_1" class="img_chart02 img-responsive mt20">
	</div>
</template>

<script>
import confirm from '@/modules/commonUtil/service/confirm.js'
import urlInfoApi from '@/modules/urlInfo/service/urlInfoApi.js'

export default {
  name: 'urlInfoStatTab2',
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
			componentsTitle: '단축 URL+ & 통계 > 타임라인(일)',
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