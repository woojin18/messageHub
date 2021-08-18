<template>
  <div v-if="rcvblcNumOpen" @click.self="fnClose" class="modalStyle" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          <h2>수신거부번호 관리</h2>
          <hr>
          <div class="of_h">
            <input 
              type="text"
              class="inputStyle"
              style="width:30%" 
              v-model="rejectPreText"
            >
            <input 
              type="text" 
              class="inputStyle" 
              style="width:55%; margin-left: 5px;" 
              placeholder="080-0000-0000" 
              @keypress="fnPreventPhoneNumInput"
              v-model="rejectNumber"
            >
            <a href="#" @click.prevent="fnInsertRejectNum" class="btnStyle1 backLightGray float-right" title="등록">등록</a>
          </div>
          <div class="consolMarginTop pd0">
            <!-- table -->
            <table class="table_skin1 bt-000">
              <colgroup>
                <col>
                <col style="width:11%">
                <col style="width:11%">
              </colgroup>
              <tbody>
                <tr v-for="(contant, idx) in contants" :key="idx">
                  <td class="text-left end">{{contant.phone}}</td>
                  <td class="text-center end"><a href="#" @click.prevent="fnSelectRejectNum(contant.phone)" class="btnStyle1 small backLightGray" title="선택">선택</a></td>
                  <td class="text-center end"><a href="#" @click.prevent="fnDeleteRejectNum(contant.rejectId)" class="btnStyle1 small backLightGray" title="삭제">삭제</a></td>
                </tr>
              </tbody>
            </table>
            <!-- //table -->
          </div>

          <div class="text-center mt20">
            <a href="#" @click.prevent="fnClose" class="btnStyle1 backWhite" title="닫기">닫기</a>
          </div>

        </div>
      </div>
    </div>
  </div>
  
</template>

<script>
import messageApi from "@/modules/message/service/messageApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  name: "rcvblcNumManage",
  props: {
    rcvblcNumOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '수신거부번호 관리';
      }
    },
  },
  data() {
    return {
      limitByte: 30,
      rejectPreText: '수신거부방법 : ',
      rejectNumber: '',
      rejectId: '',
      contants: [],
    }
  },
  watch: {
    rcvblcNumOpen(val){
      if(val){
        this.fnSelectRejectPhoneList();
      }
    }
  },
  methods: {
    fnSelectRejectNum(rcvblcNum){
      this.$emit('callback-func', rcvblcNum);
      this.fnClose();
    },
    fnPreventPhoneNumInput($event){
      let keyCode = ($event.keyCode ? $event.keyCode : $event.which);
      if ((keyCode < 48 || keyCode > 57) && keyCode !== 45) {
        $event.preventDefault();
      }
    },
    //유효성 체크
    fnIsValid(){
      if(!this.rejectPreText){
        confirm.fnAlert(this.componentsTitle, '수신거부문구를 입력해주세요.');
        return false;
      }
      if(!this.rejectNumber){
        confirm.fnAlert(this.componentsTitle, '수신거부번호를 입력해주세요.');
        return false;
      }
      if(!this.$gfnCommonUtils.isDashNum(this.rejectNumber)){
        confirm.fnAlert(this.componentsTitle, '수신거부번호 형식에 맞지않습니다.');
        return false;
      }
      if(this.$gfnCommonUtils.getByte(this.rejectPreText + this.rejectNumber) > this.limitByte){
        confirm.fnAlert(this.componentsTitle, '수신거부문구 + 수신거부번호는 최대 '+this.limitByte+'까지 입력가능합니다.');
        return false;
      }
    },
    //등록
    fnInsertRejectNum(){
      //유효성 검사
      if(this.fnIsValid() == false) return;

      eventBus.$on('callbackEventBus', this.fnProcInsertRejectNum);
      confirm.fnConfirm(this.componentsTitle, "입력하신 정보를 등록하시겠습니까?", "확인");
    },
    async fnProcInsertRejectNum(){
      let params = {
        phone: this.rejectPreText + this.rejectNumber
      };
      //저장처리
      await messageApi.insertRejectPhone(params).then(response => {
        const result = response.data;
        if(result.success) {
          confirm.fnAlert(this.componentsTitle, '등록되었습니다.');
          this.fnSelectRejectPhoneList();
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    //삭제
    fnDeleteRejectNum(rejectId){
      this.rejectId = rejectId;
      eventBus.$on('callbackEventBus', this.fnProcDeleteRejectNum);
      confirm.fnConfirm(this.componentsTitle, "삭제하시겠습니까?", "확인");
    },
    async fnProcDeleteRejectNum(){
      let params = {
        rejectId: this.rejectId
      };
      //삭제처리
      await messageApi.deleteRejectPhoneList(params).then(response => {
        const result = response.data;
        if(result.success) {
          confirm.fnAlert(this.componentsTitle, '삭제되었습니다.');
          this.fnSelectRejectPhoneList();
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnSelectRejectPhoneList(){
      let params = {};
      messageApi.selectRejectPhoneList(params).then(response => {
        const result = response.data;
        if(result.success) {
          this.contants = Object.assign([], result.data);
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    //팝업 닫기
    fnClose(){
      this.$emit('update:rcvblcNumOpen', false)
    },
  }
}
</script>
