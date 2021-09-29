<template>
	<div class="modal modalStyle" id="receptGroupPopup" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>수신그룹 {{popupTitle}}</h2>
						<hr>
						<div class="of_h">
							<h5 class="inline-block" style="width:20%">수신그룹 명 *</span></h5>
							<div class="of_h float-right" style="width:80%">								
								<div class="float-left" style="width:100%"><input type="text" class="inputStyle" title="수신그룹 명 입력란" v-model="receptGroup.receptGroupName"></div>
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:20%">사용여부 *</span></h5>
							<div class="of_h float-right" style="width:80%">	
								<input id="useYn-Y" type="radio" value="Y" class="cBox" v-model="receptGroup.useYn"><label for="useYn-Y" class="payment mt10 mr30">사용</label>
								<input id="useYn-N" type="radio" value="N" class="cBox" v-model="receptGroup.useYn"><label for="useYn-N" class="payment mt10">미사용</label>	
							</div>
						</div>
					</div>
					<div class="text-center mt40">
						<a @click="fnSave" class="btnStyle1 backBlack" activity="SAVE" title="저장">저장</a>&nbsp;
						<a @click="fnClose" class="btnStyle1 backWhite" title="닫기">닫기</a>						
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import receptGroupApi from "@/modules/alarm/service/receptGroupApi"
import confirm from "@/modules/commonUtil/service/confirm.js"

export default {
  name: 'receptGroupPopup',
  props: {
    popupTitle: {
      type: String,
      require: false,
    },
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
  data(){
    return {
      receptGroup: {
		receptGroupId : null,
		receptGroupName : null,
		useYn : "Y"
	  }
    }
  },
  watch : {
    popReset(){
      this.init();
    }
  },
  methods: {
    init() {
      this.receptGroup = Object.assign({useYn:'Y'}, this.rowData)
    },
	fnSave() {
      //필수항목 check
      if(jQuery.trim(this.receptGroup.receptGroupName).length == 0){
        confirm.fnAlert("", "수신그룹명은 필수사항입니다.");
        return;
      }
      var params = Object.assign({}, this.receptGroup)
	  receptGroupApi.saveReceptGroup(params).then(response =>{
       	var result = response.data;
       	if(result.success) {
	   	  confirm.fnAlert( "수신그룹 저장", "저장되었습니다.");
		  // 리스트 조회
		  this.$parent.fnSearch1();
		  this.fnClose();
		} else {
		  confirm.fnAlert("", result.message);
		}
      });
	},
	fnClose() {
        jQuery("#receptGroupPopup").modal("hide");
	}
  }
}
</script>
