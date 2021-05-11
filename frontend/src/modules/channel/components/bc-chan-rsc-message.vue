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
								<img v-if="item.productCardType == 'sms'" src="../../../../public/se2/images/03_5_1_4_imgage01.png" class="img-responsive inline-block" style="width: 98%;">
								<img v-if="item.productCardType == 'LMS'" src="../../../../public/se2/images/03_5_1_4_imgage02.png" class="img-responsive inline-block" style="width: 98%;">
								<img v-if="item.productCardType == 'Carousel Small'" src="../../../../public/se2/images/03_5_1_4_imgage07.png" class="img-responsive inline-block" style="width: 98%;">
								<img v-if="item.productCardType == 'Carousel Medium'" src="../../../../public/se2/images/03_5_1_4_imgage08.png" class="img-responsive inline-block" style="width: 98%;">
								<img v-if="item.productCardType == 'Standalone Horizontal Left'" src="../../../../public/se2/images/03_5_1_4_imgage06.png" class="img-responsive inline-block" style="width: 98%;">
								<img v-if="item.productCardType == 'Standalone Horizontal Right'" src="../../../../public/se2/images/03_5_1_4_imgage05.png" class="img-responsive inline-block" style="width: 98%;">
								<img v-if="item.productCardType == 'Standalone Media Top Medium'" src="../../../../public/se2/images/03_5_1_4_imgage03.png" class="img-responsive inline-block" style="width: 98%;">
								<img v-if="item.productCardType == 'Standalone Media Top Tall'" src="../../../../public/se2/images/03_5_1_4_imgage04.png" class="img-responsive inline-block" style="width: 98%;">
							</div>	
							<div class="col-xs-6">
								<!-- <table style="width: 80%; margin-left: 20px"> -->
								<table>
									<colgroup>
										<col style="width:75px" />
										<col style="width:auto"/>
									</colgroup>
									<tr height="30">
										<td>포맷 ID</td>
										<td>{{item.messagebaseformId}}</td>
									</tr>
									<tr height="30">
										<td>상품명</td>
										<td>{{item.tmpltName}}</td>
									</tr>
									<tr height="30">
										<td>상품코드</td>
										<td>{{item.productCode}}</td>
									</tr>
									<tr height="30">
										<td>메시지타입</td>
										<td>{{item.productCardType}}</td>
									</tr>
									<tr height="30">
										<td>카드장수</td>
										<td>{{item.cardCount}}</td>
									</tr>
									<tr height="30">
										<td>제목글자수</td>
										<td>{{item.maxTitleSize}}</td>
									</tr>
									<tr height="30">
										<td>본문글자수</td>
										<td>{{item.maxDescriptionSize}}</td>
									</tr>
									<tr height="30">
										<td>버튼개수</td>
										<td>{{item.maxButtonCount}}</td>
									</tr>
									<tr height="30">
										<td>이미지(px)</td>
										<td>{{item.imageWidth}}*{{item.imageHeight}}</td>
									</tr>
									<tr height="30">
										<td>이미지용량</td>
										<td>{{item.maxMediaSize}}</td>
									</tr>
								</table>
								<!-- <ul class="inline-block vertical-top ml20">
									<li class="mt8">포맷 ID</li>
									<li class="mt8">상품명</li>
									<li class="mt8">상품코드</li>
									<li class="mt8">메시지타입</li>
									<li class="mt8">카드장수</li>
									<li class="mt8">제목글자수</li>
									<li class="mt8">본문글자수</li>
									<li class="mt8">버튼개수</li>
									<li class="mt8">이미지(px)</li>
									<li class="mt8">이미지용량</li>
								</ul>
								<ul class="inline-block vertical-top ml20" style="width : 55%; ">
									<li class="mt8">{{item.messagebaseformId}}</li>
									<li class="mt8">{{item.tmpltName}}</li>
									<li class="mt8">{{item.productCode}}</li>
									<li class="mt8">{{item.productCardType}}</li>
									<li class="mt8">{{item.cardCount}}</li>
									<li class="mt8">{{item.maxTitleSize}}</li>
									<li class="mt8">{{item.maxDescriptionSize}}</li>
									<li class="mt8">{{item.maxButtonCount}}</li>
									<li class="mt8">{{item.imageWidth}}*{{item.imageHeight}}</li>
									<li class="mt8">{{item.maxMediaSize}}</li>
								</ul> -->
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