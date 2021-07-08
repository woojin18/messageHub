<template>
    <div>
        <div class="contentHeader mb20">
            <h2>나의 문의내역</h2>
        </div>
        <!-- 본문 -->
        <div class="row">
				<div class="col-xs-12">
					<div class="menuBox">						
						<div class="of_h">	
							<h4 class="inline-block" style="width:7%">문의유형</h4>
							<select v-model="srcQnaType" class="selectStyle2" style="width:16%">
								<option value="">전체</option>
								<option  v-for="(row, index) in qnaTypeInfo" :key="index" :value="row.codeVal1"> {{ row.codeName1 }} </option>
							</select>
							<h4 class="inline-block ml60" style="width:7%">문의상태</h4>
							<select id="srcQnaStatus" name="srcQnaStatus" class="selectStyle2" style="width:16%" v-model="srcQnaStatus">
								<option value="">전체</option>
								<option  v-for="(row, index) in qnaStatusInfo" :key="index" :value="row.codeVal1"> {{ row.codeName1 }} </option>
							</select>
							<h4 class="inline-block ml60" style="width:7%">문의제목</h4>
							<input type="text" id="srcTitle" class="inputStyle vertical-baseline" style="width:24%" v-model="srcTitle" @keypress.enter="fnSearch">
							<a @click="fnSearch" class="btnStyle1 float-right">검색</a>
						</div>						
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 consolMarginTop">
					<div class="of_h inline">
						<div class="float-right">
							<a @click="fnAddQna" class="btnStyle2 borderGray" data-toggle="modal" data-target="#Inquiry">1:1 문의하기</a>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 consolMarginTop">
							<!-- 15개씩 보기 -->
							<PagingCnt :pageInfo.sync="pageInfo" />
							<!-- //15개씩 보기 -->
							<!-- table -->
							<table class="table_skin1 bt-000 tbl-striped">
								<colgroup>
									<col style="width:3%">
									<col>
									<col style="width:8%">
									<col style="width:10%">
									<col style="width:10%">
									<col style="width:8%">
									<col style="width:8%">
									<col style="width:10%">
									<col style="width:14%">
								</colgroup>
								<thead>
									<tr>
									<th class="text-center lc-1">No.</th>
									<th class="text-center lc-1">문의제목</th>
									<th class="text-center lc-1">문의유형</th>
									<th class="text-center lc-1">이메일</th>
									<th class="text-center lc-1">연락처</th>
									<th class="text-center lc-1">문의상태</th>
									<th class="text-center lc-1">답변자</th>
									<th class="text-center lc-1">답변일자</th>
									<th class="text-center lc-1 end">관리</th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="(row, index) in data" :key="index">
									<td class="text-center">{{ index + 1 }}</td>
									<td class="text-left clickClass">
										<span class="clickClass"  @click="fnDetailQna(row)">{{ row.title}}</span>
									</td>
									<td class="text-center">{{ row.questTypeStr }}</td>
									<td class="text-center">{{ row.email }}</td>
									<td class="text-center">{{ row.hpNumber }}</td>
									<td class="text-center">{{ row.questStatusStr }}</td>
									<td class="text-center">{{ row.replyUser }}</td>
									<td class="text-center">{{ row.replyDt }}</td>
									<td class="end">
										<a @click="fnEditQna(row)" class="btnStyle1 borderLightGray small mr5">수정</a>
										<a @click="fnDeleteQna(row)" class="btnStyle1 borderLightGray small mr5">삭제</a>
									</td>
									</tr>
								</tbody>
							</table>
							<!-- //table -->
							<!-- pagination -->
							<Paging :pageInfo.sync="pageInfo" />
							<!-- //pagination -->
						</div>			
					</div>
				</div>			
			</div>
			<acQnaPop :status="status" :selectRow="selectRow" :popReset="popReset"></acQnaPop>
    </div>
</template>
<script>
import commonUtilApi from "@/modules/commonUtil/service/commonUtilApi";
import myPageApi from '@/modules/myPage/service/myPageApi';
import Paging from "@/modules/commonUtil/components/bc-paging"
import PagingCnt from "@/modules/commonUtil/components/bc-pagingCnt"
import acQnaPop from "@/modules/myPage/components/bp-acQna"
import tokenSvc from '@/common/token-service';

import confirm from "@/modules/commonUtil/service/confirm"
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
	name: 'acQna',
	components : {
		PagingCnt,
		Paging,
		acQnaPop
	},
	data(){
		return {
			srcTitle : '',
			srcQnaType : '',
			srcQnaStatus : '',
			data : [],
			pageInfo: {},
			status : '',
			selectRow : {},
			popReset : 0,
			qnaTypeInfo : [],
			qnaStatusInfo : []
		}
	},
	mounted() {
		this.pageInfo = {
			"pageCnt"   : [15, 30, 50],  //표시할 개수 리스트
			"selPageCnt": 15,          //선택한 표시 개수
			"selPage"   : 1,          //선택한 페이지
			"rowNum"    : 1           //총개수
		};

		this.fnQnaTypeInit();
		this.fnQnaStatusInit();
		this.fnSearch();
	},
	methods : {
		//QNA 문의 유형 코드
		fnQnaTypeInit(){
			var params = {
				codeTypeCd	: "QNA_TYPE",
				useYN		: "Y"
			};
			commonUtilApi.selectCodeList(params).then(response =>{
				var result = response.data;
				if(result.success){
					this.qnaTypeInfo = result.data;
				}
			});
		},
		//QNA 문의 상태 코드
		fnQnaStatusInit(){
			var params = {
				codeTypeCd	: "QNA_STATUS",
				useYN		: "Y"
			};
			commonUtilApi.selectCodeList(params).then(response =>{
				var result = response.data;
				if(result.success){
					this.qnaStatusInfo = result.data;
				}
			});
		},
		// 조회
		fnSearch(){
			var params = {
				srcQuestType : this.srcQnaType,
				srcQuestStatus : this.srcQnaStatus,
				srcTitle : this.srcTitle,
				pageInfo : this.pageInfo,
				userId : tokenSvc.getToken().principal.userId
			};

			myPageApi.selectQnaList(params).then(response => {
				var result = response.data;
				if(result.success) {
					if(result.data.length > 0){
						this.data = result.data; 
						this.pageInfo = result.pageInfo;
					} else {
						confirm.fnAlert("","검색된 내용이 없습니다.");
					}
				}
			});
		},
		// 문의 내역 등록
		fnAddQna(){
			this.status = "add";
			this.selectRow = new Object;
			this.popReset = this.popReset+1;
			jQuery("#acQnaPopup").modal("show");
		},
		// 문의 내역 수정
		fnEditQna(row){
			if(row.questStatus != "01"){
				confirm.fnAlert("","요청 중인 경우만 수정이 가능합니다.");
				return;
			} else {
				this.status = "edit";
				this.selectRow = row;
				this.popReset = this.popReset+1;
				jQuery("#acQnaPopup").modal("show");
			}
		},
		// 문의 내역 상세
		fnDetailQna(row){
			this.status = "detail";
			this.selectRow = row;
			this.popReset = this.popReset+1;
			jQuery("#acQnaPopup").modal("show");
		},
		// 문의 내역 삭제
		fnDeleteQna(row){
			if(row.questStatus != "01"){
				confirm.fnAlert("","요청 중인 경우만 삭제가 가능합니다.");
				return;
			} else {
				this.selectRow = row;
				eventBus.$on('callbackEventBus', this.fnDeleteCallBack);
				confirm.fnConfirm("", " 해당 문의내역을 삭제하시겠습니까?", "확인");
			}
		},
		// 문의 내역 삭제 callback
		fnDeleteCallBack(){
			var params = {
				questBoardId : this.selectRow.questBoardId
			};
			myPageApi.deleteQnaInfo(params).then(response => {
				var result = response.data;
				if(result.success){
					confirm.fnAlert("","삭제되었습니다.");
					this.fnSearch();
				} else {
					confirm.fnAlert("",result.message);
				}
			});

		}
	}
}
</script>
<style lang="scss">
table tbody .clickClass {
  cursor: pointer;
  text-decoration: underline;
}
</style>