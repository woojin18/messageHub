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
                <select id="srcBrandType" v-model="srcBrandType">
                  <option value="brandId">브랜드 아이디</option>
                  <option value="brandName">브랜드 명</option>
                </select>
								<input id="srcBrandText" type="text" class="inputStyle ml20 vertical-baseline" style="width:65%" v-model="srcBrandText">
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

				

        <!-- 리스트 -->
				<div class="row mt20">
					<div class="col-xs-12">
            <!-- 페이징 카운트 -->
						<PagingCnt :pageInfo.sync="pageInfo" />
            <!-- 페이징 카운트 -->
            <!-- 본문 -->
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
                <tr v-for="(row, index) in data" :key="index">
                  <td>
                    {{ index + 1 }}
                  </td>
                  <td>
                    <a class="color:blue; text-decoration: underline; cursor:pointer;" @click="fnRcsBrandDetail(row)">{{ row.brandName }}</a>
                  </td>
                  <td>
                    {{ row.brandId }}
                  </td>
                  <td>
                    {{ row.otherProjectUseYn }}
                  </td>
                  <td>
                    <a class="color:blue; text-decoration: underline; cursor:pointer;" @click="fnRcsTmpltDetail(row)">{{ row.tmplCnt }}</a>
                  </td>
                  <td>
                    <a class="color:blue; text-decoration: underline; cursor:pointer;" @click="fnRcsCallbackDetail(row)">{{ row.callbackCnt }}</a>
                  </td>
                  <td>
                    {{ row.approvalStatus }}
                  </td>
                  <td>
                    {{ row.reqDt }}
                  </td>
                  <td>
                    {{ row.approvalDt }}
                  </td>
                </tr>
              </tbody>
            </table>
					</div>			
				</div>
        <!-- 본문 -->
				<!-- 페이징 -->
				<Paging :pageInfo.sync="pageInfo" />
				<!-- 페이징 -->


			</div>
			<!-- //본문 -->

			<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
		</article>
	</div>
</template>


<script>
import Api from '../service/api'

import modalTmplt from "./bp-chan-rcs-tmplt-cnt.vue";
import modalCallback from "./bp-chan-rcs-callback-cnt.vue";

import Paging from "@/modules/commonUtil/components/bc-paging"
import PagingCnt from "@/modules/commonUtil/components/bc-pagingCnt"

export default {
  components: {
      Paging
    , PagingCnt
    , modalTmplt
    , modalCallback
  },
  data() {
    return {
      save_status : '', // 등록 수정 여부
      // 검색 조건
      srcBrandType : "brandId",
      srcBrandText : "",
      // 리스트 
			data : {},
			pageInfo: {},

      // 프로젝트 정보
      projectId : '',
      projectName : '',
      visibleTmplt : false,  // 레이어 팝업 
      visibleCallback : false,  // 레이어 팝업
      row_data : {}
    }
  },
  mounted() {
    this.projectId = this.$parent.projectId;
    this.projectName = this.$parent.projectName;

		this.pageInfo = {
			"pageCnt"   : [10, 30, 50],  //표시할 개수 리스트
			"selPageCnt": 10,          //선택한 표시 개수
			"selPage"   : 1,          //선택한 페이지
			"rowNum"    : 1           //총개수
		};

    this.fnSearch();
  },
  methods: {
    // 검색
    fnSearch() {
      var params = {
        "projectId"     : this.projectId,
        "srcBrandType"  : this.srcBrandType,
        "srcBrandText"  : this.srcBrandText,
        "pageInfo"    	: this.pageInfo
      }
       
      Api.selectRcsBrandList(params).then(response =>{
        var result = response.data;
				if(result.success) {
          this.data = result.data; 
          this.pageInfo = result.pageInfo;
				}
      });
    },
    // 등록창
    fnRcsBrandReg : function(){
      // 브랜드 등록 데이터 초기화
      var inputVal = {
        corpId 		  	: "",
        projectId   	: "",
        apiKey 		  	: "lng04154",             // 테스트용 임시 키
        apiSecretKey 	: "SK.I9SrjuVvmDI8CdS",   // 테스트용 임시 키
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
    fnRcsTmpltDetail(row){
      this.visibleTmplt = !this.visibleTmplt;
      this.row_data = row;
    },
    // 등록 발신번호 상세 
    fnRcsCallbackDetail(row){
      this.visibleCallback = !this.visibleCallback;
      this.row_data = row;
    },
  }
}
</script>