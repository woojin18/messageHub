<template>
  <div>
      <div class="Dashboard01 border-line">
				<p>전체: <span class="color1"><strong>20</strong></span>건</p>
				<div class="mt20">	
					<!-- 본문 -->	
					<div class="row row-no-margin of_h">
						<!-- 메세지 포멧 -->
						<div v-for="(item, i) in msgFormData" :key="i" class="col-xs-6 bgColor_f1" style="width:48%; padding:30px; margin:12px;">
							<div class="row">
								<div class="col-xs-6">
									<img :src="item.img" class="img-responsive inline-block">
								</div>	
								<div class="col-xs-6">
									<ul class="inline-block vertical-top ml20">
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
									<ul class="inline-block vertical-top ml20">
										<li class="mt8">{{item.formatId}}</li>
										<li class="mt8">{{item.productName}}</li>
										<li class="mt8">SMS</li>
										<li class="mt8">Standalone</li>
										<li class="mt8">1</li>
										<li class="mt8">0</li>
										<li class="mt8">100</li>
										<li class="mt8">1</li>
									</ul>
								</div>
							</div>						
						</div>
						<!-- 메세지 포멧 -->
					</div>
					<!-- 본문 -->	
				</div>

				<!-- <div class="text-center mt50"><a href="#self" class="btnStyle2 width180">더보기</a></div> -->


			</div>

			<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
		</article>
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
    fnMoveMainTab(moveTabName){
      this.$router.push( {name:moveTabName, params:{"projectId" : this.projectId, "projectName" : this.projectName }} );
    },
    fnMoveSubTab(moveTabName){
      this.$router.push( {name:moveTabName, params:{"projectId" : this.projectId, "projectName" : this.projectName }} );
    },
    fnMoveRcsTab(moveTabName){
      var pageName = 'chan-rcs';
      if( moveTabName === 'brand' ){
        pageName = 'chan-rcs';
      } else if( moveTabName === 'message' ){
        pageName = 'chan-rcs-message';
      }
      this.$router.push( {name : pageName, params : {"projectId" : this.projectId, "projectName" : this.projectName }} );
    },
    // 검색
    fnSearch() {
      var vm = this;
      var params = {
        "projectId"     : this.projectId,
      }
       
      /* Api.selectRcsBrandList(params).then(response =>{
        vm.items = response.data.data;
      }); */
    }
  }
}
</script>