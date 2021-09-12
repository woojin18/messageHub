<template>
    <div class="modal modalStyle" id="recipientPopup" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div>
                        <h2>수신자 {{popupTitle}}</h2>
                        <hr>
                        <div class="of_h">
                            <h5 class="inline-block" style="width:20%">수신자명 *</span></h5>				
                            <div class="float-right" style="width:80%"><input type="text" class="inputStyle" title="수신그룹 명 입력란" v-model="recipient.recipientName"></div>
                        </div>
                        <div class="of_h consolMarginTop">
                            <h5 class="inline-block" style="width:20%">휴대폰 *</span></h5>				
                            <div class="float-right" style="width:80%"><input type="text" class="inputStyle" title="휴대폰 입력란" v-model="recipient.hpNumber"></div>
                        </div>
                        <div class="of_h consolMarginTop">
                            <h5 class="inline-block" style="width:20%">알람그룹</span></h5>
                            <div class="of_h float-right" style="width:80%">
                                <div>
                                    <select id="receptGroupId" class="selectStyle2" style="width:100%" v-model="recipient.receptGroupId" @change="changeReceptGroup">
                                        <option value="">선택</option>
                                        <option v-for="option in receptGroupList" v-bind:value="option.receptGroupId">{{option.receptGroupName}}</option>
                                    </select>
                                    <div class="consolMarginTop">
                                        <div v-for="(item, key) in recipient.receptGroups" @click="$delete(recipient.receptGroups, key)" class="consolCheck" style="width:20%">{{item.receptGroupName}} <i class="fal fa-times"></i></div>
                                    </div>
                                </div>
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
import recipientApi from "@/modules/alarm/service/recipientApi"
import confirm from "@/modules/commonUtil/service/confirm.js"

export default {
  name: 'recipientPopup',
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
      recipient: {
		recipientId : null,
		recipientName : null,
		hpNumber : null,
		receptGroupId : "",
		receptGroups : []
	  },
	  receptGroupList: []
    }
  },
  watch : {
    popReset(){
      this.init();
    }
  },
  mounted() {
	this.load();
  },
  methods: {
	load() {
	  var vm = this
      receptGroupApi.selectReceptGroupList2({}).then(response => {
        var result = response.data
        if(result.success) {
          vm.receptGroupList = result.data
        }
      })
	},
    init() {
      this.recipient = Object.assign({receptGroupId:"", receptGroups:[]}, this.rowData)
	  if (this.recipient.recipientId > 0) {
		var vm = this
		receptGroupApi.selectReceptGroupList3(vm.recipient).then(response => {
			var result = response.data
			if(result.success) {
			vm.recipient.receptGroups = result.data
			}
		})
	  }
    },
	changeReceptGroup(event) {
		var isExist = false
		var receptGroupId = event.target.value
		this.recipient.receptGroups.forEach(function(value, index) {
			if (value.receptGroupId == receptGroupId) {
				isExist = true;
				return false;
			}
		})
		if (isExist == true) {
			this.recipient.receptGroupId = ""
			return
		}
		var receptGroupName = jQuery('#receptGroupId>option[value='+receptGroupId+']').text()
		this.recipient.receptGroups.push({receptGroupId:receptGroupId, receptGroupName:receptGroupName})
		this.recipient.receptGroupId = ""
	},
	fnSave() {
      //필수항목 check
      if(jQuery.trim(this.recipient.recipientName).length == 0){
        confirm.fnAlert("", "수신자명은 필수사항입니다.");
        return;
      }
      if(jQuery.trim(this.recipient.hpNumber).length == 0){
        confirm.fnAlert("", "휴대폰번호는 필수사항입니다.");
        return;
      }
      var params = Object.assign({}, this.recipient)
	  recipientApi.saveRecipient(params).then(response =>{
       	var result = response.data;
       	if(result.success) {
	   	  confirm.fnAlert( "수신자 저장", "저장되었습니다.");
		  // 리스트 조회
		  this.$parent.fnSearch();
		  this.fnClose();
		} else {
		  confirm.fnAlert("", result.message);
		}
      });
	},
	fnClose() {
        jQuery("#recipientPopup").modal("hide");
	}
  }
}
</script>
