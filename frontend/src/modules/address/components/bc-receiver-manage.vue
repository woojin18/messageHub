<template>
	<div>
		<article>
			<div class="contentHeader mb20">
				<h2>주소록 > 수신자 관리</h2>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<div class="menuBox">
						<div>
							<select class="selectStyle2 vertical-middle" style="width:10%" v-model="searchData.searchTextType">
								<option value="cuName">수신자명</option>
								<option value="cuid">아이디</option>
								<option value="hpNumber">휴대폰번호</option>
							</select>
							<input type="text" class="inputStyle ml10" style="width:15%" v-model="searchData.searchText" @keyup.enter="fnPageNoResetSearch()">
							<h4 class="inline-block ml50" style="width:7%">사용여부</h4>
							<select class="selectStyle2" style="width:15%" v-model="searchData.useYn">
								<option value="">전체</option>
								<option value="Y">사용</option>
								<option value="N">미사용</option>
							</select>
							<a @click="fnPageNoResetSearch()" class="btnStyle1 float-right" activity="READ">검색</a>
						</div>
					</div>
				</div>
			</div>

			<div class="row consolMarginTop">
				<div class="col-xs-12">		
					<div class="of_h inline">
						<div class="float-right">
							<a @click="fnRegisterReceiverPop" class="btnStyle2 borderGray mr5" >수신자 추가</a>
							<a @click="fnExcelDownLoad" class="btnStyle2 borderGray mr5" activity="READ">엑셀 다운로드</a>
							<a @click="fnExcelUpload" class="btnStyle2 borderGray mr5" activity="SAVE">엑셀 업로드</a>
							<a @click="fnExcelUploadTemplate" class="btnStyle1 backLightGray ml10" title="업로드샘플" activity="READ">업로드샘플 <i class="far fa-arrow-to-bottom"></i></a>
							<input ref="excelFile" type="file" style="display:none;" @change="fnChgInputFile()">
						</div>
					</div>
				
					<!-- 페이지당 리스트 개수 -->
					<div class="of_h inline">
						<div class="float-left">전체 : <span class="color1"><strong>{{totCnt | formatComma}}</strong></span>건
							<SelectLayer @fnSelected="fnSelected" classProps="selectStyle2 width120 ml20"></SelectLayer>
						</div>
					</div>
					
					<div class="row">
						<div class="col-xs-12 consolMarginTop">
							<!-- table -->
							<table  class="table_skin1 bt-000 tbl-striped">
								<colgroup>
									<col style="width:10%">
									<col>
									<col style="width:22%">
									<col style="width:20%">
									<col style="width:12%">
									<col style="width:15%">
								</colgroup>
								<thead>
									<tr>
										<th class="text-center lc-1">No.</th>
										<th class="text-center lc-1">수신자명</th>
										<th class="text-center lc-1">앱로그인 ID</th>
										<th class="text-center lc-1">휴대폰 번호</th>
										<th class="text-center lc-1">사용여부</th>
										<th class="text-center lc-1 end">관리</th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="(data) in items" :key="data.cuInfoId">
										<td class="text-center"> {{ totCnt-offset-data.rownum+1 | formatComma }} </td>
										<td class="text-left"> {{ data.cuName }} </td>
										<td class="text-left"> {{ data.cuid }} </td>
										<td class="text-center"> {{ data.hpNumber | phoneNumber }} </td>
										<td v-if="data.useYn == 'Y'" class="text-center"> 사용 </td>
										<td v-else class="text-center"> 미사용 </td>
										<td class="end">
											<a @click="fnModifyReceiverPop(data)" class="btnStyle1 borderLightGray small mr5">수정</a>
											<a @click="fnDeleteReceiver(data)" class="btnStyle1 borderLightGray small mr5" activity="SAVE">삭제</a>
										</td>
									</tr>	
									<tr v-if="items.length == 0">
										<td class="text-center"></td>
										<td class="text-center" colspan="6">검색된 내용이 없습니다.</td>
									</tr>	
								</tbody>
							</table>
						</div>	
					</div>
				</div>			
			</div>
			<!-- pagination Start -->
			<div id="pageContent">
				<PageLayer @fnClick="fnPageClick" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
			</div>
			<!-- pagination End-->
			<footer>Copyright©LG Uplus Corp. All Rights Reserved.</footer>
		</article>

		<!--  Modal -->
		<RcvrRegMdfyLayer :rowData="rowData" :status.sync="status"></RcvrRegMdfyLayer>
	</div>
</template>

<script>
import addressApi from '../service/addressApi'
//import tokenSvc from '@/common/token-service';
import confirm from '@/modules/commonUtil/service/confirm';
import PageLayer from '@/components/PageLayer.vue';
import SelectLayer from '@/components/SelectLayer.vue';
import RcvrRegMdfyLayer from '../components/bc-receiver-register.vue';
import {eventBus} from '@/modules/commonUtil/service/eventBus';

export default {
	name: 'receiverManageList',
	components: {
		SelectLayer,
		PageLayer,
		RcvrRegMdfyLayer,
	},
	props: {
		searchData : {
			type: Object,
			require: false,
			default: function() {
				return {
					'cuName'		: '',
					'hpNumber'		: '',
					'cuId'			: '',
					'searchTextType': 'cuName',
					'searchText'	: '',
					'useYn'			: '',
				}
			}
		}
	},
	filters:{
		phoneNumber(val) {
			if(!val) return val

			val = val.replace(/[^0-9]/g, '')

			let tmp = ''
			if( val.length < 4){
				return val;
			} else if(val.length < 7) {
				tmp += val.substr(0, 3);
				tmp += '-';
				tmp += val.substr(3);
				return tmp;
			} else if(val.length == 8) {
				tmp += val.substr(0, 4);
				tmp += '-';
				tmp += val.substr(4);
				return tmp;
			} else if(val.length < 10) {
				if(val.substr(0, 2) =='02') { //02-123-5678
					tmp += val.substr(0, 2);
					tmp += '-';
					tmp += val.substr(2, 3);
					tmp += '-';
					tmp += val.substr(5);
					return tmp;
				}
			} else if(val.length < 11) {
				if(val.substr(0, 2) =='02') { //02-1234-5678
					tmp += val.substr(0, 2);
					tmp += '-';
					tmp += val.substr(2, 4);
					tmp += '-';
					tmp += val.substr(6);
					return tmp;
				} else { //010-123-4567
					tmp += val.substr(0, 3);
					tmp += '-';
					tmp += val.substr(3, 3);
					tmp += '-';
					tmp += val.substr(6);
					return tmp;
				}
			} else { //010-1234-5678
				tmp += val.substr(0, 3);
				tmp += '-';
				tmp += val.substr(3, 4);
				tmp += '-';
				tmp += val.substr(7);
				return tmp;
			}
		}
	},
	data() {
		return {
			listSize : 10,	// select 박스 value (출력 갯수 이벤트)
			pageNo : 1,		// 현재 페이징 위치
			totCnt : 0,		//전체 리스트 수
			offset : 0,		 //페이지 시작점
			items: [],
			status: '',
			rowData: {},
			cuInfoId: -1,
		}
	},
	mounted() {
		this.fnPageNoResetSearch();
	},
	methods: {
		fnSearch(pageNum) {
			this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNum, '1'))*1;
			this.fnSearchReceiverList();
		},
		// select 박스 선택시 리스트 재출력
		fnSelected(listSize) {
			this.listSize = Number(listSize);
			this.$refs.updatePaging.fnAllDecrease();
		},
		fnPageNoResetSearch(){
			this.$refs.updatePaging.fnAllDecrease();
		},
		//수신자 리스트 검색
		async fnSearchReceiverList(){
			var params = Object.assign({}, this.searchData);
			params.pageNo = this.pageNo;
			params.listSize = this.listSize;

			await addressApi.selectReceiverList(params).then(response =>{
				var result = response.data;
				if(result.success) {
					this.items = result.data;
					this.totCnt = result.pageInfo.totCnt;
					this.offset = result.pageInfo.offset;
				} else {
					confirm.fnAlert('', result.message);
				}
			});
		},
		// 삭제
		fnDeleteReceiver(data) {
			this.cuInfoId = data.cuInfoId;
			eventBus.$on('callbackEventBus', this.fnProcDeleteReceiver);
			confirm.fnConfirm('', '선택 항목을 삭제하시겠습니까?', '확인');
		},
		fnProcDeleteReceiver() {
			let params = {
				cuInfoId: this.cuInfoId
			};

			addressApi.deleteReceiver(params).then(response =>{
				const result = response.data;
				if(result.success) {
					confirm.fnAlert('', '삭제했습니다.');
					this.cuInfoId = -1;
					this.fnSearchReceiverList();
				} else {
					confirm.fnAlert('', result.message);
				}
			});
		},
		// 수신자 수정
		fnModifyReceiverPop(data) {
			this.status = 'U';
			this.rowData = data
			jQuery('#RcvrRegMdfyLayer').modal('show');
			
		},
		// 수신자 등록
		fnRegisterReceiverPop() {
			this.status = 'R';
			this.rowData = {};
			jQuery('#RcvrRegMdfyLayer').modal('show');
		},
		fnPageClick(pageNo) {
			this.pageNo = pageNo;
			this.fnSearchReceiverList();
		},
		//엑셀 다운로드
		fnExcelDownLoad() {
			var params = this.searchData;
			addressApi.excelDownloadReceiverList(params);
		},
		//엑셀 업로용 템플릿 다운로드
		fnExcelUploadTemplate() {
			let params = {
			};
			addressApi.excelUploadTemplate(params);
		},
		//엑셀 업로드
		fnExcelUpload() {
			this.$refs.excelFile.click();
		},
		//엑셀 유효성 체크
		fnValidExcel() {
			const uploadFile = this.$refs.excelFile;
			if(uploadFile.value == 0) {
				confirm.fnAlert(this.componentsTitle, '엑셀파일을 등록해주세요.');
				return false;
			}
			const permitExten = 'xls,xlsx'.split(',');
			const extnIdx = uploadFile.value.lastIndexOf('.');
			const extn = uploadFile.value.substring(extnIdx+1);
			if((permitExten.indexOf(extn) < 0)) {
				confirm.fnAlert(this.componentsTitle, '허용되지 않는 확장자입니다.');
				return false;
			}
		},
		async fnChgInputFile() {
			this.fnValidExcel();
			const uploadFile = this.$refs.excelFile;
			let fd = new FormData();
			fd.append('file', this.$refs.excelFile.files[0]);
			//const vmSelectLayer = this;
			await addressApi.excelUploadReceiver(fd).then(response =>{
				const result = response.data;
				if(result.success) {
					this.fnSearchReceiverList();
					confirm.fnAlert(this.componentsTitle, '완료하였습니다.');
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
	}
}
</script>


