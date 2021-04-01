<template>
  <div id="content">
		<article>
			<div class="contentHeader mb20">
				<h2>비트큐브 프로젝트</h2>
			</div>

			<!-- 본문 -->
			<div class="contentBody mb40">
				<ul class="tab_s3">
					<li><a href="#self" class="width120">대시보드</a></li>
					<li><a href="#self" class="width120">기본정보</a></li>
					<li><a href="#self" class="width120">멤버관리</a></li>
					<li class="active"><a href="#self" class="width120">채널관리</a></li>
					<li><a href="#self" class="width120">발신번호관리</a></li>
					<li><a href="#self" class="width120">스팸관리</a></li>
				</ul>			
			</div>
			<ul class="tabStyle tab6 bgColor_tapGray mt30">
				<li class="active"><a href="#">RCS</a></li>
				<li><a href="#">SMS/MMS</a></li>
				<li><a href="#">PUSH</a></li>
				<li><a href="#">카카오톡</a></li>
				<li><a href="#">MO</a></li>
			</ul>
			
			<ul class="mt30 tab_s5">
				<li class="active"><a href="#">브랜드 관리정보</a></li>
				<li><a href="#">메시지 포맷정보</a></li>
			</ul>

      <!-- 검색창 -->
			<div class="Dashboard01 border-line">
				<div class="row">
					<div class="col-xs-12">
						<div class="menuBox">						
							<div>
                <select id="srcBrandType">
                  <option value="brandId">브랜드 아이디</option>
                  <option value="brandName">브랜드 명</option>
                </select>
								<input id="srcBrandText" type="text" class="inputStyle ml20 vertical-baseline" style="width:65%">
								<button @click="fnSearch"><a class="btnStyle2 float-right">검색</a></button>
							</div>						
						</div>
					</div>
				</div>
        <!-- 검색창 --> 


				<div class="row mt20">
					<div class="col-xs-12">		
						<div class="of_h">
							<div class="float-right">
								<a @click="fnRcsBrandReg" class="btnStyle3 gray font13 minWidth120">브랜드 등록</a>
							</div>
						</div>
					</div>			
				</div>

				

        <!-- 그리드 -->
				<div class="row mt20">
					<div class="col-xs-12">
						<div class="of_h mb20">
							<div class="float-left">전체 : <span class="color1"><strong>20</strong></span>건
								<select name="admin03040101_2" class="selectStyle2 width120 ml20">
									<option value="">15개씩 보기</option>
									<option value="">30개씩 보기</option>
								</select>
							</div>
						</div>

            <table id="list" class="table_skin1 bt-000 tbl-striped">
              <thead>
                <th class="text-center lc-1">No.</th>
                <th class="text-center lc-1">브랜드명</th>
                <th class="text-center lc-1">브랜드ID</th>
                <th class="text-center lc-1">타 프로젝트<br>사용여부</th>
                <th class="text-center lc-1">등록템플릿<br>개수</th>
                <th class="text-center lc-1">등록발신번호<br>개수</th>
                <th class="text-center lc-1">브랜드<br>승인상태</th>
                <th class="text-center lc-1">승인요청일</th>
                <th class="text-center lc-1 end">승인완료일</th>
              </thead>
              <tbody>
                <tr v-for="(data, index) in items" :key="index">
                  <td>
                    {{ index + 1 }}
                    <!-- <a href="javascript:void(0);" @click="fnUpdateListPop(index)">{{ data.COL1 }}</a> -->
                  </td>
                  <td>
                    {{ data.brandName }}
                  </td>
                  <td>
                    {{ data.brandId }}
                  </td>
                  <td>
                    {{ data.other_project_use_yn }}
                  </td>
                  <td>
                    {{ data.tmpl_cnt }}
                  </td>
                  <td>
                    {{ data.num_cnt }}
                  </td>
                  <td>
                    {{ data.appr_yn }}
                  </td>
                  <td>
                    {{ data.req_dt }}
                  </td>
                  <td>
                    {{ data.appr_dt }}
                  </td>
                </tr>
              </tbody>
            </table>
					</div>			
				</div>

				<!-- 페이징 -->
				<div class="row mt40">
					<div class="col-xs-12">
						<div class="pagination1 text-center">
							<a href="#" title="10페이지 이전 페이지로 이동"><i class="far fa-chevron-double-left"></i></a>
							<a href="#" title="이전 페이지로 이동"><i class="far fa-chevron-left"></i></a>
							<a href="#" title="1페이지로 이동" class="number active">1</a>
							<a href="#" title="2페이지로 이동" class="number">2</a>
							<a href="#" title="3페이지로 이동" class="number">3</a>
							<a href="#" title="4페이지로 이동" class="number">4</a>
							<a href="#" title="5페이지로 이동" class="number">5</a>
							<a href="#" title="다음 페이지로 이동"><i class="far fa-chevron-right"></i></a>
							<a href="#" title="10페이지 다음 페이지로 이동"><i class="far fa-chevron-double-right"></i></a>
						</div>
					</div>
				</div>
        <div id="pageContent">
          <PageLayer @fnClick="fnClick" :listTotalCnt="count" :selected="selected" ref="updatePaging" > </PageLayer>
        </div>
				<!-- 페이징 -->


			</div>
			<!-- //본문 -->

			<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
		</article>
	</div>
</template>


<script>
import Api from '../service/api'
import SelectLayer from '@/components/SelectLayer.vue';
import PageLayer from '@/components/PageLayer.vue';

export default {
  components: {
      SelectLayer
    , PageLayer
  },
  data() {
    return {
      save_status : '', // 등록 수정 여부
      // 검색 조건
      col1 : "",
      col2 : "",
      // select 박스 value (출력 갯수 이벤트)
      selected : 10,
      // 현재 페이징 위치
      pagingCnt : 1,
      // 리스트 
      items : [],
      count : 0,
      // 프로젝트 정보
      projectId : ''
    }
  },
  mounted() {
    var vm = this;
    this.projectId = this.$route.params.projectId;
    this.fnSearch();
  },
  methods: {
    // 검색
    fnSearch() {
      var vm = this;
      var params = {
        "projectId"     : this.projectId,
        "srcBrandType"  : $("#srcBrandType").val(),
        "srcBrandText"  : $("#srcBrandText").val(),
        "rows"          : this.selected,
        "paging"        : this.pagingCnt
      }
       
      Api.selectRcsBrandList(params).then(response =>{
        vm.items = response.data.data;
      });
    },
    // 등록창
    fnRcsBrandReg : function(){
      this.$router.push( {name:"chan-rcs-detail",params:{"projectId" : this.projectId, "save_status" : 'C' }} );
    },
    // 리스트 등록 팝업
    fnInsertListPop() {
      /* this.layerView = true;
      this.layerTitle = "리스트 등록"; */
    },
    // 리스트 수정 팝업
    fnUpdateListPop(row) {
      /* this.updateLayerView = true;
      this.updateLayerTitle = "리스트 수정";
      this.updateLayerSeq = this.items[row].SEQ;  */
    },

    // select 박스 선택시 리스트 재출력
    fnSelected(selected) {
      this.selected = selected;
      this.$refs.updatePaging.fnAllDecrease();
    },
    // page 선택
    fnClick(pageNum) {
      this.pagingCnt = pageNum;
      this.fnSearch();
    }
  }
}
</script>