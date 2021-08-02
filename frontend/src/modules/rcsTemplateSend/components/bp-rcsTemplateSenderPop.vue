<template>
	<div class="modal fade modalStyle" id="sender" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">								
					
					<div v-if="senderType=='LMS'"class="of_h">
						<div class="float-left" style="width:32%"><h5>제목</h5></div>
						<div class="float-right" style="width:66%">
							<input v-model="senderPopData.senderTitle" type="text" class="inputStyle" placeholder="LMS 문자로 대체발송 될 제목입력" title="제목 입력란">
						</div>
					</div>

					<div class="of_h consolMarginTop">
						<div class="float-left" style="width:32%"><h5>내용</h5></div>
						<div class="float-right" style="width:66%">
							<textarea v-model="senderPopData.senderContents" class="textareaStyle height120" ></textarea>
						</div>
					</div>
					
					<div class="text-center mt20">
						<a @click.prevent="fnSaveAdd" href="#self" class="btnStyle1 backBlack" data-dismiss="modal" title="입력">입력</a>
						<a @click.prevent="fnClose" href="#self" class="btnStyle1 backWhite" data-dismiss="modal" title="닫기">닫기</a>						
					</div>

				</div>
			</div>
		</div>
	</div>
</template>

<script>
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: "rcsSenderPop",
  components : {
  },
  props : {
        senderType: {
            type: String,
            require: true,
            default: "",
        },
  },
  data() {
    return {
        senderPopData : {
            senderTitle : "",
            senderContents : ""
        }
    }
  },
  methods: {
    // 저장
    fnSaveAdd(){
       this.$parent.fnSenderTypeSet(this.senderPopData);
       this.fnClose();
    },

    // 팝업 닫기
    fnClose(){
        this.senderPopData.senderTitle = "";
        this.senderPopData.senderContents = "";
        jQuery("#sender").modal("hide");
    },

  }
}
</script>