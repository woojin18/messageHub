<template>
    <div class="modal modalStyle" id="message" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:784px">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h">
						<h2>RCS 메시지 보관함</h2>
						<hr>
						<div class="bgColor_f1 pd10">
							<select v-model="srcSelect" name="userConsole_sub020202_2" class="selectStyle2 ml5" style="width:28%">
								<option value="brand">브랜드명</option>
                                <option value="msg">메세지명</option>
							</select>
							<input v-model="srcInput" type="text" class="inputStyle vertical-unset ml5" style="width:34%">
							<a @click.prevent="fnSearch()" href="#self" activity="READ" class="btnStyle1 backBlack float-right" style="width:10%" title="검색">검색</a>							
						</div>			
						<div class="float-right consolMarginTop">
							<a @click.prevent="fnSelect" activity="READ" href="#self" class="btnStyle1 borderLightGray" title="선택">선택</a>
							<a @click.prevent="fnDelete" activity="SAVE" href="#self" class="btnStyle1 borderLightGray ml20" title="삭제">삭제</a>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<!-- table -->
								<table class="table_skin1 consolMarginTop" style="width:100%">
								  <caption>브랜드	메시지명, 작성자명, 작성일시을 제공하는 표</caption>
								  <colgroup>
									<col style="width:9%">								
									<col style="width:26%">
									<col style="width:28%">
									<col>
									<col style="width:26%">
								  </colgroup>
								  <thead>
									<tr>
									  <th class="text-center lc-1"><input v-model="checkboxAll" @click="checkedAll($event.target.checked)" type="checkbox" id="all_listCheck" class="boardCheckStyle"><label for="all_listCheck"></label></th>
									  <th class="text-center lc-1">브랜드</th>
									  <th class="text-center lc-1">메시지명</th>
									  <th class="text-center lc-1">작성자명</th>
									  <th class="text-center lc-1 end">작성일시</th>
									</tr>
								  </thead>
									<tbody>
									   <tr v-for="(data, index) in data">
										<td class="text-center"><input v-model="checkboxArr" type="checkbox" :id="data.SAVE_BOX_ID" class="boardCheckStyle" :value="data.SAVE_BOX_ID"><label :for="data.SAVE_BOX_ID"></label></td>
										<td class="text-left">{{data.BRAND_NAME}}</td>
										<td class="text-left">{{data.SAVE_BOX_NAME}}</td>									
										<td class="text-center">{{data.USER_NAME}}</td>
										<td class="text-center end">{{data.REG_DT}}</td>
									  </tr>
									</tbody>
								</table>
								<!-- //table -->
								<!-- pagination -->
								<div id="pageContent">
									<PageLayer @fnClick="fnSearch" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
								</div>
								<!-- //pagination -->
							</div>								
						</div>
					</div>
					<div class="text-center mt20">
						<a @click.prevent="fnClose" href="#self" class="btnStyle2 backWhite" title="닫기">닫기</a>										
					</div>
				</div>
				
			</div>
		</div>
	</div>
</template>

<script>
import rcsTemplateSendApi from "@/modules/rcsTemplateSend/service/api.js";
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import PageLayer from '@/components/PageLayer.vue';

export default {
  name: "rcsMsgPop",
  components : {
	  PageLayer
  },
  props : {
        templateRadioBtn: {
            type: String,
            require: true,
            default: false,
        },
		carouselSmall: {
            type: String,
            require: true,
            default: false,
        },
		carouselMedium: {
            type: String,
            require: true,
            default: false,
        }
  },
  data() {
    return { 
		listSize : 5,  // select 박스 value (출력 갯수 이벤트)
	    pageNo : 1,  // 현재 페이징 위치
	    totCnt : 0,  //전체 리스트 수
	    offset : 0, //페이지 시작점
        srcSelect : "brand",    // 검색 select box
        srcInput : "" ,         // 검색 input box
		checkboxAll : false,	// checkboxAll
		checkboxArr: [],		// checkbox
		data : {}				// 검색 Data
    }
  },
  methods: {
	fnInit(){
		this.fnSearch();
	},

	fnSearch(pageNum) {
		this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNum, '1'))*1;
		this.fnSelectMsgList();
	},

    fnSelectMsgList() {
		var vm = this;
		var srcInput = this.srcInput;
		var srcSelect = this.srcSelect;
		var templateRadioBtn = this.templateRadioBtn;
		if("" == srcInput) {
			srcSelect = "";
		}

		if(templateRadioBtn == "carouselSmall") {
			templateRadioBtn = vm.carouselSmall;
		} else if(templateRadioBtn == "carouselMedium") {
			templateRadioBtn = vm.carouselMedium;
		}

		var params = {
			"pageNo" : this.pageNo,
			"listSize" : this.listSize,
			"srcInput" : srcInput,
			"srcSelect" : srcSelect,
			"templateRadioBtn" : templateRadioBtn
		};

		rcsTemplateSendApi.selectRcsMsgList(params).then(response => {
			var result = response.data;
			for(var i=0; i<result.data; i++) {
				vm.checkboxArr[i] = false;
			}
			vm.totCnt = result.pageInfo.totCnt;
			vm.offset = result.pageInfo.offset;
			vm.data = result.data;
		});

    },

	// 전체선택 event
	checkedAll(checked) {
		var vm = this;
 		this.allChecked = checked
		for (let i in vm.data) {
			if(checked) {
				vm.checkboxArr[i] = vm.data[i].SAVE_BOX_ID;
			} else {
				vm.checkboxArr = [];
			}
		}
	},

	// 선택
	fnSelect() {
		var vm = this;
		var checkData = this.checkboxArr;
		if(checkData.length>0 && checkData.length!=1) {
			confirm.fnAlert("복수건은 선택할 수 없습니다.","");
		} else if(checkData.length == 0) {
			confirm.fnAlert("선택하실 메시지를 선택해주세요.","");
		} else {
			var data = {
				"saveBoxId" : checkData[0],
				"templateRadioBtn" : vm.templateRadioBtn
			}

			vm.$emit('fnTmpMsgSet', data);
			vm.fnClose();
		}
	},

	// 삭제
	fnDelete() {
		var checkData = this.checkboxArr;
		if(!checkData.length>0) {
			confirm.fnAlert("삭제하실 메세지를 선택해주세요.","");
		} else {
			eventBus.$on('callbackEventBus', this.fnDeleteList);
      		confirm.fnConfirm("RCS 메시지", "선택하신 메시지를 삭제하시겠습니까?", "확인");
		}
	},

	fnDeleteList() {
		var vm = this;
		var checkData = this.checkboxArr;
		var params = {
			"saveBoxIdArr" : checkData
		};
		rcsTemplateSendApi.deleteRcsTmpMsgbase(params).then(response => {
			var result = response.data;
			var success = result.success;

			if(success) {
				confirm.fnAlert("선택하신 메시지를 삭제하였습니다.","");
			} else {
				confirm.fnAlert(result.message,"");
			}
			vm.fnSearch();
		});
	},

    //팝업 닫기
    fnClose() {
      //데이터 초기화
      this.checkboxArr = [];
      this.data = {};
	  this.srcInput = "";
	  jQuery("#message").modal("hide");
    },
  }
}
</script>