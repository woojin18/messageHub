<template>
	<div>
		<article>
			<div class="contentHeader">
				<h2>RCS 템플릿</h2>
				<!-- <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="RCS 템플릿 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
			</div>

			<!-- 본문 -->
			<div class="border-line2 pd10">
				<p class="color4">RCS 템플릿은 서술형과 스타일형으로만 작성이 가능합니다.<br>RCS 템플릿은 승인과정을 통해서 등록이 됩니다. (저장 상태에서는 수정/삭제가 가능하고 승인요청 후에는 수정/삭제가 불가능 합니다.)<br>상태가 승인인 템플릿은 RCS 메시지 발송을 할 수 있습니다.</p>
			</div>

			<div class="row consolMarginTop">
				<div class="col-xs-12">
					<div class="menuBox">
						<div>
							<select name="userConsole_sub040201_1" class="selectStyle2 mr10" style="width:15%" title="RCS 템플릿 선택란" v-model="status">
								<option value="">전체</option>
                				<option value="저장">저장</option>
                				<option value="승인대기">승인대기</option>
                				<option value="검수시작">검수시작</option>
                				<option value="승인">승인</option>
                				<option value="반려">반려</option>
                				<option value="검수완료">검수완료</option>
                				<option value="승인대기(수정)">승인대기(수정)</option>
                				<option value="검수시작(수정)">검수시작(수정)</option>
                				<option value="반려(수정)">반려(수정)</option>
                				<option value="검수완료(수정)">검수완료(수정)</option>
							</select>
							<select name="userConsole_sub040201_2" class="selectStyle2 " style="width:15%" title="RCS 템플릿 선택란" v-model="searchTag">
								<option value="temNm">템플릿명</option>
                				<option value="temId">템플릿ID</option>
                				<option value="brandNm">브랜드명</option>
							</select>
							<input type="text" class="inputStyle vertical-top ml10" style="width:30%" title="검색조건 입력란" v-model="inputTag">
							<a href="#self" @click.prevent="fnSearch" class="btnStyle1 float-right" title="검색">검색</a>	
						</div>					
					</div>
				</div>				
			</div>
			
			<div class="of_h mt20">
				<div class="float-right">
					<a href="#" class="btnStyle2 backBlack" @click.prevent="templateInsert">템플릿 등록</a>
				</div>
			</div>

			<div class="row">
				<div class="col-xs-12">
          <!-- 15개씩 보기 -->
					<PagingCnt :pageInfo.sync="pageInfo" />
					<!-- //15개씩 보기 -->
					<!-- table -->
					<table class="table_skin1 bt-000 tbl-striped">
						<colgroup>
							<col style="width:5%">
							<col>
							<col style="width:10%">
							<col style="width:10%">
							<col style="width:10%">
							<col style="width:10%">
							<col style="width:10%">
							<col style="width:8%">
							<col style="width:10%">
              				<col style="width:9%">
						</colgroup>
						<thead>
							<tr>							
								<th class="text-center lc-1">No.</th>
								<th class="text-center lc-1">템플릿 ID</th>
								<th class="text-center lc-1">템플릿명</th>
								<th class="text-center lc-1">브랜드명</th>
								<th class="text-center lc-1">속성</th>
								<th class="text-center lc-1">승인상태</th>
								<th class="text-center lc-1">승인일</th>
								<th class="text-center lc-1">등록자</th>
                				<th class="text-center lc-1">등록일자</th>
								<th class="text-center lc-1 end">템플릿 복사</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(contant, idx) in contants">
							<td class="text=center">{{ idx + 1 }}</td>
							<td class="text-center"><a href="#" @click.prevent="templateUpdate(contant.MESSAGEBASE_ID)">{{contant.MESSAGEBASE_ID}}</a></td>
							<td class="text-center">{{contant.TMPLT_NAME}}</td>
							<td class="text-center">{{contant.BRAND_NAME}}</td>
							<td class="text-center">{{contant.BRAND_NAME}}</td>
							<td class="text-center">{{contant.APPROVAL_STATUS}}</td>
							<td class="text-center">{{contant.APPROVAL_DT}}</td>
							<td class="text-center">{{contant.REG_ID}}</td>
							<td class="text-center">{{contant.REG_DT}}</td>
							<td class="text-center end"><a href="#" @click="templateCopy(contant.MESSAGEBASE_ID)" class="btnStyle6 minWidthAuto" style="width: 65%;">템플릿 복사</a></td>
							</tr>
							<tr v-if="contants.length == 0">
								<td class="text-center" colspan="10">검색된 내용이 없습니다.</td>
							</tr>
						</tbody>
					</table>
					<!-- //table -->
				</div>			
			</div>

      		<!-- pagination -->
			<Paging :pageInfo.sync="pageInfo" />
			<!-- //pagination -->
		</article>
	</div>
</template>

<script>
import confirm from "@/modules/commonUtil/service/confirm";
import templateApi from "@/modules/template/service/templateApi.js";
import Paging from "@/modules/commonUtil/components/bc-paging";
import PagingCnt from "@/modules/commonUtil/components/bc-pagingCnt";

export default {
  components: {
    PagingCnt,
    Paging
  },
  data() {
    return {
      status: "",
      searchTag: "temNm",
      inputTag: "",
      pageInfo: {},
      contants: []
    }
  },
  mounted() {
    this.pageInfo = {
      "pageCnt"   : [10, 20, 30],   //표시할 개수 리스트
      "selPageCnt": 10,             //선택한 표시 개수
      "selPage"   : 1,              //선택한 페이지
      "rowNum"    : 0               //총개수
    };

    this.fnSearch();
  },

  methods: {
    // 검색
    fnSearch() {
		var params = {
			"pageInfo" : this.pageInfo,
			"status" : this.status,
			"searchTag" : this.searchTag,
			"inputTag" : this.inputTag
    	};

		templateApi.selectRcsTemplateList(params).then(response => {
		  	var result = response.data;
			if(result.success) {
			this.contants = result.data;
			this.pageInfo = result.pageInfo;
			}
		});
    },
	// 템플릿 등록 페이지 이동
	templateInsert () {
		var params = {};
		
		templateApi.corpBrandCnt(params).then(response => {
			var result = response.data;
			var resultCnt = result.data;
			if(resultCnt > 0) {
				this.$router.push({name : "rcsTemplateMod", params: {status:"INS"}});
			} else {
				confirm.fnAlert("등록된 브랜드가 없습니다.", "");
			}
		});
	},
	// 템플릿 수정
	templateUpdate(msgId) {
		this.$router.push({name:"rcsTemplateMod", params: {status:"UPT", msgId:msgId}})
	},
    // 템플릿 복사
    templateCopy (msgId) {
		this.$router.push({name:"rcsTemplateMod", params: {status:"CPY", msgId:msgId}})
    }
  }
}
</script>