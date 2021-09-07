<template>
  <div>
    <modalTmplt 
      :visibleTmplt.sync="visibleTmplt"
      :row_data="row_data"
    >
    </modalTmplt>
    <modalCallback 
      :visibleCallback.sync="visibleCallback"
      :callback_row_data="callback_row_data"
    >
    </modalCallback>
    
		<article>
      <!-- 검색창 -->
			<div class="Dashboard01 border-line">
				<div class="row">
					<div class="col-xs-12">
						<div class="menuBox">						
							<div>
                <select id="srcBrandType" v-model="srcBrandType" class="selectStyle2" style="width:20%">
                  <option value="brandId">브랜드 아이디</option>
                  <option value="brandName">브랜드 명</option>
                </select>
								<input id="srcBrandText" type="text" class="inputStyle ml20 vertical-baseline" style="width:65%" v-model="srcBrandText" @keypress.enter="fnSearch(1)">
								<a @click="fnSearch(1)" class="btnStyle2 float-right" activity="READ">검색</a>
							</div>						
						</div>
					</div>
				</div>
        <!-- 검색창 --> 


				<div class="row mt20">
					<div class="col-xs-12">		
						<div class="of_h">
							
						</div>
					</div>			
				</div>

				

        <!-- 리스트 -->
				<div class="row mt20">
					<div class="col-xs-12">
            <!-- 페이징 카운트 -->
						<div class="of_h inline">
              <div class="float-left">전체 : <span class="color1"><strong>{{totCnt}}</strong></span>건
                <SelectLayer @fnSelected="fnSelected" classProps="selectStyle2 width120 ml20"></SelectLayer>
              </div>
            </div>
            <div class="float-right mb10">
								<a @click="fnRcsBrandReg" class="btnStyle3 gray font13 minWidth120" activity="SAVE">브랜드 등록</a>
							</div>
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
                    {{totCnt-offset-row.rownum+1}}
                  </td>
                  <td style="text-decoration: underline; cursor: pointer;">
                    <a @click="fnRcsBrandDetail(row)">{{ row.brandName }}</a>
                  </td>
                  <td>
                    {{ row.brandId }}
                  </td>
                  <td>
                    {{ row.otherProjectUseYn }}
                  </td>
                  <td style="text-decoration: underline; cursor: pointer;">
                    <a @click="fnRcsTmpltDetail(row)">{{ row.tmplCnt }}</a>
                  </td>
                  <td style="text-decoration: underline; cursor: pointer;">
                    <a @click="fnRcsCallbackDetail(row)">{{ row.callbackCnt }}</a>
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
                <tr v-if="data.length == 0">
                  <td class="text-center" colspan="9">검색된 내용이 없습니다.</td>
                </tr>
              </tbody>
            </table>
					</div>			
				</div>
        <!-- 본문 -->
				<!-- 페이징 -->
				<div id="pageContent">
          <PageLayer @fnClick="fnSearch" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
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

import modalTmplt from "./bp-chan-rcs-tmplt-cnt.vue";
import modalCallback from "./bp-chan-rcs-callback-cnt.vue";

import SelectLayer from '@/components/SelectLayer.vue';
import PageLayer from '@/components/PageLayer.vue';
import confirm from "@/modules/commonUtil/service/confirm";

export default {
  components: {
    PageLayer,
    SelectLayer,
    modalTmplt,
    modalCallback
  },
  data() {
    return {
      save_status : '', // 등록 수정 여부
      // 검색 조건
      srcBrandType : "brandId",
      srcBrandText : "",
      // 리스트 
			data : {},
			listSize : 10,  // select 박스 value (출력 갯수 이벤트)
			pageNo : 1,  // 현재 페이징 위치
			totCnt : 0,  //전체 리스트 수
			offset : 0, //페이지 시작점

      // 프로젝트 정보
      projectId : '',
      projectName : '',
      visibleTmplt : false,  // 레이어 팝업 
      visibleCallback : false,  // 레이어 팝업
      row_data : {},
      callback_row_data : {},
      projectApiKey : "",
    }
  },
  mounted() {
    this.projectId = this.$parent.projectId;
    this.projectName = this.$parent.projectName;

    this.fnSearch(1);
    this.fnFindApiKeyFromProject();
  },
  methods: {
    fnFindApiKeyFromProject(){
      var params = {
        "projectId"     : this.projectId,
      }

      Api.findApiKeyFromProject(params).then(response =>{
        var result = response.data;
				if(result.success) {
          this.projectApiKey = result.data;
				}
      });
    },
		// select 박스 선택시 리스트 재출력
		fnSelected(listSize) {
			this.listSize = Number(listSize);
			this.$refs.updatePaging.fnAllDecrease();
		},
    // 검색
    fnSearch(pageNo) {
      
      var params = {
        "projectId"     : this.projectId,
        "srcBrandType"  : this.srcBrandType,
        "srcBrandText"  : this.srcBrandText,
				"pageNo"		: (this.$gfnCommonUtils.defaultIfEmpty(pageNo, '1'))*1,
				"listSize"		: this.listSize
      }

      Api.selectRcsBrandList(params).then(response =>{
        var result = response.data;
				if(result.success) {
          this.data = result.data; 
					this.totCnt = result.pageInfo.totCnt;
          this.offset = result.pageInfo.offset;
				}
      });
    },
    // 등록창
    fnRcsBrandReg : function(){
      if( this.projectApiKey === "" || this.projectApiKey === undefined ){
					confirm.fnAlert("", "API Key를 등록 후, 진행 가능합니다. 프로젝트 기본정보 탭에서 API Key를 등록해주세요.");
          return;
      }


      // 브랜드 등록 데이터 초기화
      var inputVal = {
        corpId 		  	: "",
        projectId   	: "",
        apiKey 		  	: "",  
        apiSecret 	: "", 
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
        tickets			  : false,
        ticketsWeblink	: "",
        moreinfo		  : false,
        moreinfoWeblink	: "",

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
      this.$router.push( {name:"chan-rcs-detail",params:{
          "mainProjectId" : this.projectId, 
          "mainProjectName" : this.projectName,
          "rcsYn" : this.$route.params.rcsYn,
          "smsmmsYn" : this.$route.params.smsmmsYn,
          "pushYn" : this.$route.params.pushYn,
          "kakaoYn" : this.$route.params.kakaoYn,
          "moYn" : this.$route.params.moYn,

          "projectId" : this.projectId,
          "brandId" : "", 
          "save_status" : 'C', 
          "inputVal" : inputVal 
        }
      } );
    },
    fnRcsBrandDetail(data){
      this.$router.push( {name:"chan-rcs-detail",params:{
          "mainProjectId" : this.projectId,
          "mainProjectName" : this.projectName,
          "rcsYn" : this.$route.params.rcsYn,
          "smsmmsYn" : this.$route.params.smsmmsYn,
          "pushYn" : this.$route.params.pushYn,
          "kakaoYn" : this.$route.params.kakaoYn,
          "moYn" : this.$route.params.moYn,
          "brandId" : data.brandId,
          "save_status" : 'U',
          "projectIdStr" : data.projectId,
          /* "inputVal" : data.inputVal, */
          "approvalStatus" : this.approvalStatus,
          "otherProjectYn" : data.otherProjectUseYn 
        }
      });
    },
    // 등록 템플릿 상세 
    fnRcsTmpltDetail(row){
      //this.visibleTmplt = !this.visibleTmplt;
      this.row_data = row;
      jQuery("#confirmTmplt").modal("show");
    },
    // 등록 발신번호 상세 
    fnRcsCallbackDetail(row){
      //this.visibleCallback = !this.visibleCallback;
      this.callback_row_data = row;
      jQuery("#confirmCallback").modal("show");
    },
  }
}
</script>