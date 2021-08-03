<template>
    <div id="confirm" class="modal fade modalStyle" tabindex="-1" role="dialog" aria-hidden="true">
   <!-- <div class="modalStyle" id="confirm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">'-->
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-body">
            <div class="float-left">
              <h5 class="lc-1">RCS 발송</h5>
              <p class="mt5 lc-1"><pre>템플릿을 변경하면 입력된 데이터가 모두 초기화됩니다. 변경하시겠습니까?</pre></p>
            </div>
            <div class="float-right">
              <a class="btnStyle4 red" data-dismiss="modal" @click="fnCallback">확인</a>
              <a class="btnStyle4 gray ml5" data-dismiss="modal" @click="fnClose">닫기</a>
            </div>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: "confirmPopup",
  props : {
        oldval: {
            type: String,
            require: true,
            default: "",
        },
        newval: {
            type: String,
            require: true,
            default: "",
        }
  },
  data() {
    return { 
    }
  },

  methods: {
      fnCallback() {
        var vm = this;
        vm.$parent.fnResetData();
      },
      fnClose() {
        var vm = this;
        vm.$parent.parentFunction();
      }
  }
}
</script>