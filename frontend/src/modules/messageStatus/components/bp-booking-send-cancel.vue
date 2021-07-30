<template>
  <!-- <div v-if="layerView" class="layerPopup"> -->
    <div class="modal fade modalStyle" id="cancel" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-body">
            <div class="of_h">
              <h2>예약발송 취소</h2>
              <hr>						
              <div class="of_h">
                <div class="float-left" style="width:15%"><h5>메시지</h5></div>
                <div class="float-right" style="width:84%">
                  <!--<textarea class="textareaStyle height120" placeholder="" v-model="msg"></textarea>-->
                  <pre>{{msg}}</pre>
                </div>							
              </div>
              <div class="of_h consolMarginTop">
                <div class="float-left" style="width:15%"><h5>취소사유</h5></div>
                <div class="float-right" style="width:84%">
                  <textarea class="textareaStyle height120" placeholder="" v-model="rowData.cancelCause" ></textarea>
                </div>							
              </div>				
                    
            </div>						
            <div class="text-center mt20">
              <a class="btnStyle1 backBlack" @click="fnCancelLayer" title="등록" activity="SAVE">등록</a>
              <a class="btnStyle1 backWhite" data-dismiss="modal" @click="fnCloseLayer" title="닫기">닫기</a>						
            </div>
          </div>
        </div>
      </div>
    <!-- </div> -->
  </div>
</template>

<script>
import messageStatusApi from '../service/messageStatusApi';
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {

  name: 'UpdateLayer',
  props: {//부모로 부터 넘겨받을 값들 require: true => 필수로 받아야됨
    rowData : {
      type: Object,
      require: false,
      default: function() {
        return {'cancelCause':''}
      }
    },
    layerView: {
      type: Boolean,
      require: true,
      default: false
    },
    detailTitle: {
      type: String,
      require: true
    },
    detailWebReqId: {
      type: String,
      require: true
    },
    detailMsg: {
      type: String,
      require: true
    }
  },
  data() {//변수 초기화
    return {
      listSize : 10,  // select 박스 value (출력 갯수 이벤트)
      pageNo : 1,  // 현재 페이징 위치
      totCnt : 0,  //전체 리스트 수
      offset : 0, //페이지 시작점
      msg : ''
    }
  },

  watch: {
    layerView: function(newVal) {
        // LayerView가 true를 반환해 팝업이 출력됫을 시에만 값을 세팅하여 수정 팝업을 만든다.
        if(newVal) {
          this.msg = this.detailMsg;
        }
    },
  },

  methods: {
    fnCancelLayer(){
      eventBus.$on('callbackEventBus', this.fnProcBookingSendCancel);
      confirm.fnConfirm(this.detailTitle, "예약발송 취소처리를 하시겠습니까?", "저장");
    },
    fnProcBookingSendCancel(){
      var params = this.rowData;
      params.webReqId = this.detailWebReqId;

       messageStatusApi.cancelBookingSend(params).then(response =>{
        var result = response.data;
        if(result.success) {
          confirm.fnAlert(this.detailTitle, '예약발송 취소처리 되었습니다.');
          this.$parent.fnSearch(1);
          this.fnCloseLayer();
        } else {
          confirm.fnAlert(this.detailTitle, result.message);
        }
      });
    },
    // 닫기
    fnCloseLayer(){
      jQuery("#cancel").modal("hide");
    },
  }
}
</script>
