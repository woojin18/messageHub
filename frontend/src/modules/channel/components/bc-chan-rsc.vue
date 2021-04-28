<template>
  <div>
    <modalTmplt 
      :visibleTmplt.sync="visibleTmplt"
      :row_data="row_data"
    >
    </modalTmplt>
    <modalCallback 
      :visibleCallback.sync="visibleCallback"
      :row_data="row_data"
    >
    </modalCallback>
    
		<article>
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
								<a @click="fnSearch" class="btnStyle2 float-right">검색</a>
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
                  </td>
                  <td>
                    <a class="color:blue; text-decoration: underline; cursor:pointer;" @click="fnRcsBrandDetail(data)">{{ data.brandName }}</a>
                  </td>
                  <td>
                    {{ data.brandId }}
                  </td>
                  <td>
                    {{ data.otherProjectUseYn }}
                  </td>
                  <td>
                    <a class="color:blue; text-decoration: underline; cursor:pointer;" @click="fnRcsTmpltDetail(data)">{{ data.tmplCnt }}</a>
                  </td>
                  <td>
                    <a class="color:blue; text-decoration: underline; cursor:pointer;" @click="fnRcsCallbackDetail(data)">{{ data.callbackCnt }}</a>
                  </td>
                  <td>
                    {{ data.approvalStatus }}
                  </td>
                  <td>
                    {{ data.reqDt }}
                  </td>
                  <td>
                    {{ data.approvalDt }}
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
							<a title="10페이지 이전 페이지로 이동"><i class="far fa-chevron-double-left"></i></a>
							<a title="이전 페이지로 이동"><i class="far fa-chevron-left"></i></a>
							<a title="1페이지로 이동" class="number active">1</a>
							<a title="2페이지로 이동" class="number">2</a>
							<a title="3페이지로 이동" class="number">3</a>
							<a title="4페이지로 이동" class="number">4</a>
							<a title="5페이지로 이동" class="number">5</a>
							<a title="다음 페이지로 이동"><i class="far fa-chevron-right"></i></a>
							<a title="10페이지 다음 페이지로 이동"><i class="far fa-chevron-double-right"></i></a>
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

import modalTmplt from "./bp-chan-rcs-tmplt-cnt.vue";
import modalCallback from "./bp-chan-rcs-callback-cnt.vue";

export default {
  components: {
      SelectLayer
    , PageLayer
    , modalTmplt
    , modalCallback
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
      projectId : '',
      projectName : '',
      visibleTmplt : false,  // 레이어 팝업 
      visibleCallback : false,  // 레이어 팝업
      row_data : {}
    }
  },
  mounted() {
    var vm = this;
    this.projectId = this.$parent.projectId;
    this.projectName = this.$parent.projectName;
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
        "srcBrandType"  : jQuery("#srcBrandType").val(),
        "srcBrandText"  : jQuery("#srcBrandText").val(),
        "rows"          : this.selected,
        "paging"        : this.pagingCnt
      }
       
      Api.selectRcsBrandList(params).then(response =>{
        vm.items = response.data.data;
      });
    },
    // 등록창
    fnRcsBrandReg : function(){
      
      var inputVal = {
        corpId 		  	: "",
        projectId   	: "",
        apiKey 		  	: "lng04154",
        apiSecretKey 	: "SK.I9SrjuVvmDI8CdS",
        name			    : "",
        description		: "",
        tel				    : "",
        // 메뉴버튼설정
        chat			    : "",
        call			    : false,
        callWeblink		: "",
        web				    : false,
        webWeblink		: "",
        store			    : false,
        storeWeblink	: "",
        order			    : false,
        orderWeblink	: "",
        buy				    : false,
        buyWeblink		: "",
        ticket			  : false,
        ticketWeblink	: "",
        moreInfo		  : false,
        moreInfoWeblink	: "",

        categoryId		: "",
        subCategoryId	: "",
        categoryOpt		: "",
        zipCode			  : "",
        roadAddress		: "",
        detailAddress	: "",
        email			    : "",
        email2			  : "",
        webSiteUrl		: "",
        mainMdn			  : "",	// 대표발신번호
        mainTitle		  : "",
        // 파일
        profileImgFilePath  : "",
        preProfileImg       : "/se2/images/rcsProfileImageSample.jpg",
        bgImgFilePath       : "",
        preBgImg            : "/se2/images/rcsImageSample.jpg",
        certiFilePath       : "",

        // 발신번호 관련
        rcsReply : "",
        chatbotCnt : 1,
        chatbots: []
      }
      this.$router.push( {name:"chan-rcs-detail",params:{"projectId" : this.projectId, "brandId" : "", "save_status" : 'C', "inputVal" : inputVal }} );
    },
    fnRcsBrandDetail(data){
      this.$router.push( {name:"chan-rcs-detail",params:{"projectId" : this.projectId, "brandId" : data.brandId, "save_status" : 'U', "inputVal" : data.inputVal }} );
    },
    // 등록 템플릿 상세 
    fnRcsTmpltDetail(data){
      this.visibleTmplt = !this.visibleTmplt;
      this.row_data = data;
    },
    // 등록 발신번호 상세 
    fnRcsCallbackDetail(data){
      this.visibleCallback = !this.visibleCallback;
      this.row_data = data;
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