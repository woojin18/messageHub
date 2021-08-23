<template>
	<div class="modal fade modalStyle" id="recipientListPopup" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>그룹 수신자 추가</h2>
						<hr>
						<div class="of_h">
							<h5 class="inline-block" style="width:20%">수신자 명 *</span></h5>
							<div class="of_h float-right" style="width:80%">								
								<div class="float-left" style="width:75%"><input type="text" class="inputStyle" title="수신자 명 입력란" v-model="params.srcRecipientName"></div>
								<div class="float-right" style="width:23%"><a class="btnStyle1 borderGray" style="width:100%" title="검색" @click="fnSearch">검색</a></div>
							</div>
						</div>
						<div class="scroll-y5">
							<table  class="table_skin1 tbl-striped consolMarginTop" style="width:100%">
								<caption>수신자명, 휴대폰번호를 제공하는 표</caption>
							<colgroup>
								<col style="width:10%">
								<col style="width:*">
								<col style="width:30%">
							</colgroup>
							<thead>
								<tr>
								<th class="text-center lc-1"><div class="consolCheck ml10"><input type="checkbox" id="recChk_all" class="checkStyle2"><label for="recChk_all"></label></div></th>
								<th class="text-center lc-1">수신자명</th>
								<th class="text-center lc-1 end">휴대폰 번호</th>
								</tr>
							</thead>
								<tbody>
								<tr v-for="(data, idx) in list">
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" name="recChk" v-bind:id="'recChk'+idx" v-bind:value="data.recipientId" class="checkStyle2"><label v-bind:for="'recChk'+idx"></label></div></td>
									<td class="text-left">{{data.recipientName}}</td>
									<td class="end">{{data.hpNumber}}</td>
								</tr>
								<tr v-if="list.length == 0">
								  <td class="text-center" colspan="3">검색된 내용이 없습니다.</td>
								</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="text-center mt40">
						<a @click="fnSave" class="btnStyle1 backBlack" activity="SAVE" title="선택">선택</a>&nbsp;
						<a @click="fnClose" class="btnStyle1 backWhite" title="닫기">닫기</a>						
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import groupRecipientApi from "@/modules/alarm/service/groupRecipientApi"
import confirm from "@/modules/commonUtil/service/confirm.js"

export default {
  name: 'recipientList2Popup',
  props: {
    rowData : {
      type: Object,
      require: false,
    },
    popReset :{
      type : Number  
    }
  },
  components : {
  },
  data() {
    return {
      params: {
        srcReceptGroupId : null,
		srcRecipientName : null,
		recipientIds:[]
      },
	  list : []
    }
  },
  components: {
  },
  watch : {
    popReset() {
      this.params.srcReceptGroupId = this.rowData.srcReceptGroupId
      this.fnSearch()
    }
  },
  mounted() {
	this.fnInit()
  },
  methods: {
	fnInit() {
	  jQuery("#recChk_all").click(function(){
		//클릭되었으면
		if(jQuery("#recChk_all").prop("checked")){
		  jQuery("input[name=recChk]").prop("checked",true);
		}else{
	      jQuery("input[name=recChk]").prop("checked",false);
		}
	  })
	},
    fnSearch() {
      var params = Object.assign({}, this.params)
      groupRecipientApi.selectGroupRecipientList1(params).then(response => {
        var result = response.data
        if(result.success) {
          this.list = result.data
        }
      })
    },
	fnSave() {
		var checked = jQuery(":checkbox[name='recChk']:checked");
		if( checked.length == 0 ){
			confirm.fnAlert( "그룹 수신자 추가", "추가하실 수신자를 선택해주세요.");
			return;
		} else {
			var vm = this
			vm.params.recipientIds = []
			checked.each(function() {
                var value = jQuery(this).val();
                vm.params.recipientIds.push(value);
            })
			var params = Object.assign({}, this.params)
			groupRecipientApi.saveGroupRecipient(params).then(response =>{
				var result = response.data;
				if(result.success) {
				confirm.fnAlert( "그룹 수신자 추가", "추가되었습니다.");
				// 리스트 조회
				this.$parent.fnSearch2();
				this.fnClose();
				} else {
				confirm.fnAlert("", result.message);
				}
			});
		}
	},
	fnClose() {
        jQuery("#recipientListPopup").modal("hide");
	}
  }
}
</script>
