<template>
<div v-if="layerView" class="modalStyle">
	<!-- <div class="modal modalStyle" id="failure" tabindex="-1" role="dialog" aria-hidden="true"> -->
		<div class="modal-dialog" style="width:784px">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h">
						<h2>발송실패 정보</h2>
						<hr>
						<div class="of_h">
							<h5 style="width:18%" class="float-left color000">총 발송건수 :</h5>
							<h5 style="width:47%" class="ml20 color4 word-break-all">{{senderCnt}}</h5>
						</div>
						<div class="of_h">
							<h5 style="width:18%" class="float-left color000">발송타입 :</h5>
							<h5 style="width:47%" class="ml20 color4 word-break-all">{{senderTypeNm}}</h5>
						</div>
						<!-- <div class="of_h">
							<h5 style="width:18%" class="float-left ml color000">발송채널 :</h5>
							<h5 style="width:47%" class="ml20 color4 word-break-all">Push</h5>
						</div> -->
						
						<div class="of_h" v-if="senderType === 'C'"></div>
						<div class="of_h" v-else>
							<h5 style="width:18%" class="float-left ml color000">발송순서 :</h5>
							<span v-html="chString"></span>
						</div>
						<div class="of_h mt20">
							<div class="float-right">
								<a @click="fnExcelDownLoad" class="btnStyle2 borderGray" title="엑셀 다운로드" activity="READ">엑셀 다운로드 <i class="fal fa-arrow-to-bottom"></i></a>
							</div>
						</div>

						<div class="row">
							<div class="col-xs-12">
							<div class="of_h mb20">
								<p class="float-left">발송실패 건수 : <span class="color1"><strong>{{failCnt}}</strong></span>건</p>
							</div>
								<div style="height:337px; overflow-y:auto; overflow-x: hidden;">
									<!-- table -->
									<table class="table_skin1 bt-000 tbl-striped">
										<colgroup>
											<col style="width:10%">
											<col style="width:23%">
											<col style="width:23%">
											<col style="width:23%">
											<col style="width:23%">
										</colgroup>
										<thead>
											<tr>								
												<th class="text-center lc-1" rowspan="2">No.</th>
												<th class="text-center lc-1" colspan="2" style="border-bottom:1px solid #d5d5d5 !important">발송 대상자</th>
												<th class="text-center lc-1 end" colspan="2" style="border-bottom:1px solid #d5d5d5 !important">발송 실패</th>
											</tr>
											<tr>		
												<th class="text-center lc-1">휴대폰</th>
												<th class="text-center lc-1">아이디</th>
												<th class="text-center lc-1">코드</th>
												<th class="text-center lc-1">사유</th>
											</tr>
										</thead>
										<tbody>
                      						<tr v-for="(data, idx) in resultList" :key="idx">
												<td class="text-center">{{data.rowNum}}</td>
												<td class="text-center">{{data.phone}}</td>
												<td class="text-center">{{data.pushCuid}}</td>
												<td class="text-center">{{data.gwResultCode}}</td>
												<td class="text-center end">{{data.gwResultDesc}}</td>
											</tr>
											<tr v-if="resultList.length == 0">
												<td class="text-center" colspan="5">검색된 내용이 없습니다.</td>
											</tr>
										</tbody>
									</table>
									<!-- //table -->
								</div>
							</div>			
						</div>

						<!-- pagination -->
						<div id="pageContent">
							<PageLayer @fnClick="fnSearch" :listTotalCnt="failCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
						</div>
						<!-- //pagination -->
						<div class="text-center mt20">
							<a class="btnStyle1 backBlack" @click.self="fnCloseLayer" title="닫기">닫기</a>	
						</div>			
					</div>				
				</div>
			</div>
		</div>
	<!-- </div> -->
</div>
</template>

<script>
import messageStatusApi from '../service/messageStatusApi'
import PageLayer from '@/components/PageLayer.vue';


export default {
  components: {
    PageLayer
  },

  name: 'UpdateLayer',
  props: {//부모로 부터 넘겨받을 값들 require: true => 필수로 받아야됨
    layerView: {
      type: Boolean,
      require: true,
      default: false
    },
    title: {
      type: String,
      require: false
    },
    detailWebReqId: {
      type: String,
      require: true
    }
  },
  data() {//변수 초기화
    return {
	  listSize : 10,  // select 박스 value (출력 갯수 이벤트)
      pageNo : 1,  // 현재 페이징 위치
      failCnt : 0,  //전체 리스트 수
	  offset : 0, //페이지 시작점
	  resultList: [],
	  senderTypeNm : '',
	  senderType : '',
	  senderCnt : '',
	  chString : ''
    }
  },
 // mounted() {
 //   this.fnSetMessageStatusDetail();
 // },
  watch: {
    layerView: function(newVal) {
        // LayerView가 true를 반환해 팝업이 출력됫을 시에만 값을 세팅하여 수정 팝업을 만든다.
        if(newVal) {
            //console.log("this.detailWebReqId : "+this.detailWebReqId);
            var params = {
				"webReqId": this.detailWebReqId,
				"pageNo": this.pageNo,
				"listSize": this.listSize
            }

             messageStatusApi.selectWebSendFailList(params).then(response=> {
				var result			= response.data.data;
				
				this.resultList 	= result.list;
				this.senderCnt 		= result.totCnt;
				this.failCnt		= result.failCnt;
				this.senderTypeNm   = result.senderTypeNm;
				this.senderType   	= result.senderType;
				this.chString  		= result.chString;
            });                    
   
        }
    },
  },

methods: {

	      // 검색
    async selectWebSendFailList() {
          
	  var params = Object.assign({}, this.searchData);
	  
	  params.pageNo = this.pageNo;
      params.listSize = this.listSize;
      params.webReqId = this.detailWebReqId;

      await messageStatusApi.selectWebSendFailList(params).then(response =>{
        var result = response.data;
		
        if(result.success) {
          this.resultList 	= result.data.list;
          this.failCnt 		= result.pageDto.totCnt;
          this.offset 		= result.pageDto.offset;
        } else {
          alert(result.message);
        }
      });
	},

    //엑셀 다운로드
    fnExcelDownLoad(){
	  //var params = this.searchData;
	  var params = {
				"webReqId": this.detailWebReqId
			}
			
      messageStatusApi.excelDownloadWebSendFail(params);
	},
	
	fnSearch(pageNum) {
      this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNum, '1'))*1;
      this.selectWebSendFailList();
    },
    // 닫기
    fnCloseLayer(){
      this.$emit('update:layerView', false);
    },
    fnBack(){
      this.$router.go(-1);
    }
  }
}
</script>
