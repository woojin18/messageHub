<template>
    <div class="modal fade modalStyle" id="save" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>RCS 메시지 저장</h2>
						<hr>
						<div class="of_h">
							<div class="float-left" style="width:24%"><h5>메시지명</h5></div>
							<div class="float-left" style="width:76%">
                <input v-model="saveContent" type="text" class="inputStyle" style="width:100%" placeholder="메시지명을 입력해주세요." maxlength="50">
							</div>
							<p class="color3 float-right"><i class="far fa-info-circle"></i> 최대 50byte까지 입력 가능</p>
						</div>						
					</div>	
					<div class="text-center mt20">
						<a @click.prevent="fnClose" href="#self" class="btnStyle1 backWhite" data-dismiss="modal" title="취소">취소</a>	
						<a @click.prevent="fnSaveAdd" href="#self" class="btnStyle1 backBlack" data-dismiss="modal" title="저장">저장</a>										
					</div>
				</div>				
			</div>
		</div>
	</div>
</template>


<script>
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: "rcsSavePop",
  components : {
  },
  props : {
        rcsSavePopOpen: {
            type: Boolean,
            require: true,
            default: false,
        },
  },
  data() {
    return {
        saveContent : ""
    }
  },
  methods: {
    // 저장
    fnSaveAdd(){
       this.$parent.fnSaveRcsMsg(this.saveContent);
       this.fnClose();
    },

    // 팝업 닫기
    fnClose(){
        this.saveContent = "";
        JQuery("#save").model("hide");
    },

  }
}
</script>