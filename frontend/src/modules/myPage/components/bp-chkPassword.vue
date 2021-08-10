<template>
  <div class="modal fade modalStyle in" id="chkPwdPopup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          <div class="of_h">
            <h2>비밀번호 확인</h2>
            <hr>
            <div class="of_h">
              <h5 class="inline-block" style="width:20%">비밀번호</h5>
              <input type="password" class="inputStyle float-right" style="width:80%" title="비밀번호 입력란" v-model="password" @keyup.enter="fnChkPwd">
              <p class="mt10 lc-1 Modaltext2 font-size12"><i class="far fa-info-circle"></i> 안전을 위하여 비밀번호를 한번 더 입력해 주시기 바랍니다.</p>
            </div>		
          </div>
          <div class="text-center mt40">
            <a @click="fnChkPwd" class="btnStyle2 backBlack" data-toggle="modal" data-target="#Client" title="저장">확인</a>
            <a @click="fnCloseLayer" class="btnStyle2 backWhite  ack ml10" title="닫기">닫기</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import confirm from "@/modules/commonUtil/service/confirm.js";
import myPageApi from '@/modules/myPage/service/myPageApi';
import tokenSvc from '@/common/token-service';

export default {
  name: 'chkPwdPopup',
  data() {
    return {
      password : ''
    }
  },
  props: {
    popReset: {
      type: Number
    },
    type : {
      type : String,
      require : false
    }
  },
  mounted() {
  },
  watch: {
    popReset(){
      this.password = '';
    }
  },
  methods: {
      fnCloseLayer(){
          jQuery("#chkPwdPopup").modal("hide");
      },
      // 비밀번호 체크
      fnChkPwd(){
        var params = {
          password : this.password,
          userId : tokenSvc.getToken().principal.userId
        }
        myPageApi.checkPassword(params).then(response =>{
          var result = response.data;
          if(result.success) {
            this.fnCloseLayer();
            if(this.type == 'corp'){
              this.$parent.fnMyCorp();
            } else {
              this.$parent.fnMyPage();
            }
          } else {
            confirm.fnAlert("", result.message);
          }
        });
      }
  }
}
</script>
