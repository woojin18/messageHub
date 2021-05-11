<template>
	<div>
		<div class="Dashboard01 border-line">
			<div class="mt20">	
			<!-- 본문 -->	
				<div class="row row-no-margin of_h">
					<!-- 메세지 포멧 -->
					<div v-for="(item, i) in msgFormData" :key="i" class="col-xs-6 bgColor_f1" style="width:47%; padding:20px; margin:12px;">
						<div class="row">
							<div class="col-xs-6">
								<img v-if="item.productCardType == 'sms'" src="../../../../public/se2/images/RCSTemplate3.svg" class="img-responsive inline-block" style="width: 98%;">
								<img v-if="item.productCardType == 'LMS'" src="../../../../public/se2/images/RCSTemplate4.svg" class="img-responsive inline-block" style="width: 98%;">
								<img v-if="item.productCardType == 'Carousel Small'" src="../../../../public/se2/images/pushTemplate10.svg" class="img-responsive inline-block" style="width: 98%;">
								<img v-if="item.productCardType == 'Carousel Medium'" src="../../../../public/se2/images/pushTemplate11.svg" class="img-responsive inline-block" style="width: 98%;">
								<img v-if="item.productCardType == 'Standalone Horizontal Left'" src="../../../../public/se2/images/pushTemplate8.svg" class="img-responsive inline-block" style="width: 98%;">
								<img v-if="item.productCardType == 'Standalone Horizontal Right'" src="../../../../public/se2/images/pushTemplate9.svg" class="img-responsive inline-block" style="width: 98%;">
								<img v-if="item.productCardType == 'Standalone Media Top Medium'" src="../../../../public/se2/images/pushTemplate6.svg" class="img-responsive inline-block" style="width: 98%;">
								<img v-if="item.productCardType == 'Standalone Media Top Tall'" src="../../../../public/se2/images/pushTemplate7.svg" class="img-responsive inline-block" style="width: 98%;">
							</div>	
							<div class="col-xs-6">
								<ul class="inline-block vertical-top ml20">
									<li class="mt8" style="height: 25px;">포맷 ID</li>
									<li class="mt8" style="height: 25px;">상품명</li>
									<li class="mt8" style="height: 25px;">상품코드</li>
									<li class="mt8" style="height: 25px;">메시지타입</li>
									<li class="mt8" style="height: 25px;">카드장수</li>
									<li class="mt8" style="height: 25px;">제목글자수</li>
									<li class="mt8" style="height: 25px;">본문글자수</li>
									<li class="mt8" style="height: 25px;">버튼개수</li>
									<li class="mt8" style="height: 25px;">이미지(px)</li>
									<li class="mt8" style="height: 25px;">이미지용량</li>
								</ul>
								<ul class="inline-block vertical-top ml20" style="width : 55%; ">
									<li class="mt8" style="height: 25px;">{{item.messagebaseformId}}</li>
									<li class="mt8" style="height: 25px;">{{item.tmpltName}}</li>
									<li class="mt8" style="height: 25px;">{{item.productCode}}</li>
									<li class="mt8" style="height: 25px;">{{item.productCardType}}</li>
									<li class="mt8" style="height: 25px;">{{item.cardCount}}</li>
									<li class="mt8" style="height: 25px;">{{item.maxTitleSize}}</li>
									<li class="mt8" style="height: 25px;">{{item.maxDescriptionSize}}</li>
									<li class="mt8" style="height: 25px;">{{item.maxButtonCount}}</li>
									<li class="mt8" style="height: 25px;">{{item.imageWidth}}*{{item.imageHeight}}</li>
									<li class="mt8" style="height: 25px;">{{item.maxMediaSize}}</li>
								</ul>
							</div>
						</div>						
					</div>
					<!-- 메세지 포멧 -->
				</div>
			</div>
			<!-- 본문 -->	
		</div>
	</div>
</template>


<script>
import Api from '../service/api'

export default {
  components: {
  },
  data() {
    return {
      // 프로젝트 정보
      projectId : '',
      msgFormData : [
		{
			"formatId" : 1,
			"productName" : "123",
			"img" : "/public/se2/images/03_5_1_4_imgage03.svg"
		},
		{
			"formatId" : 2,
			"productName" : "222"
		},
		{
			"formatId" : 3,
			"productName" : "333"
		}
	  ]
    }
  },
  mounted() {
    this.projectId = this.$route.params.projectId;
    this.fnSearch();
  },
  methods: {
    // 검색
    fnSearch() {
      var params = {
        "brandId"     : 'common'
      }
       
      Api.selectRcsBrandMsgBaseList(params).then(response =>{
        this.msgFormData = response.data.data;
      });
    }
  }
}
</script>