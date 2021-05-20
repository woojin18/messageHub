<template>
  <div class="modal fade modalStyle in" id="myPagePopup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          <div class="of_h">
              <h5 class="lc-1">회원 정보</h5>
              <hr>
          </div>
          <div class="mt20">
            <h4 class="font-normal inline-block" style="width:23%">사용자ID</h4>
            <h4 class="font-normal inline-block" style="width:75%">{{ userId }}</h4>
          </div>
          <div class="mt10">
        <h4 class="font-normal inline-block" style="width:23%">이름</h4>
        <input type="text" class="inputStyle height41" style="width:75%" title="이름" v-model="userName">
          </div>
          <div class="mt10">
            <h4 class="font-normal inline-block" style="width:23%; line-height: 35px;">비밀번호</h4>
            <h4 class="font-normal inline-block" style="width:75%">최종 변경일 : {{ pwdUpdDt }}
              <button class="btnStyle3 gray font13 width120 ml10" title="비밀번호 변경" @click="fnChgPwdDiv">비밀번호 변경</button>
            </h4>
          </div>
          <div id="chgPwdDiv" style="display:none;">
            <div class="mt10">
              <h4 class="font-normal inline-block" style="width:23%">비밀번호 변경</h4>
              <input type="password" class="inputStyle height41" style="width:75%" title="비밀번호 변경 입력란" v-model="loginPwd" placeholder="새 비밀번호">
            </div>
            <div class="mt10">
              <h4 class="font-normal inline-block" style="width:23%">비밀번호 확인</h4>
              <input type="password" class="inputStyle height41" style="width:75%" title="비밀번호 변경 확인 입력란" v-model="chkLoginPwd" placeholder="새 비밀번호 확인">
            </div>
          </div>
          <div class="mt20">
            <h4 class="font-normal inline-block" style="width:23%">휴대폰 번호</h4>
            <input type="text" class="inputStyle height41" style="width:56%" title="휴대폰 번호 입력란" v-model="hpNumber" :disabled="true">
            <button class="btnStyle3 gray font13 width90 ml10 mt5" title="번호 변경" @click="fnChgHpNumDiv">번호 변경</button>
          </div>
          <div id="fnChgHpNumDiv" style="display:none;">
            <div class="mt10">
              <h4 class="font-normal inline-block" style="width:23%">휴대폰 번호 변경</h4>
              <input type="text" class="inputStyle height41" style="width:50%" title="휴대폰 번호 변경 입력란" v-model="chgHpNumber" placeholder="-없이 입력">
              <button class="btnStyle3 gray font13 width120 ml10 mt5" title="인증번호 받기">인증번호 받기</button>
            </div>
            <div class="mt10">
              <h4 class="font-normal inline-block" style="width:23%">인증번호</h4>
              <input type="text" class="inputStyle height41" style="width:75%" title="인증번호 입력란" placeholder="인증번호를 입력해주세요." v-model="authNumber">
            </div>
          </div>
          <div class="text-right mt30">
            <button @click="fnSave" class="btnStyle3 black font14" title="저장">저장</button>
            <button @click="fnCloseLayer" class="btnStyle3 white font14 ml10" title="취소">취소</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import myPageApi from '@/modules/myPage/service/myPageApi';

export default {
  name: 'myPagePopup',
  data() {
    return {
      userId    : '',
      userName  : '',
      pwdUpdDt  : '',
      hpNumber  : '',
      loginPwd  : '',
      chkLoginPwd : '',
      chgHpNumber : '',
      authNumber  : ''
    }
  },
  props: {
    layerView: {
			type: Boolean,
			require: true,
			default: false
		},
    memberInfo : {
      type : Object,
      require : true
    },
    popReset: {
      type: Number
    }
  },
  mounted() {
    this.fnReset();
  },
  watch: {
    popReset() {
      // 비밀번호 변경 버튼을 눌러야 display 처리
      jQuery("#chgPwdDiv").css("display", "none");
      // 번호 변경 버튼을 눌러야 display 처리
      jQuery("#fnChgHpNumDiv").css("display", "none");
      this.fnReset();
    }
  },
  methods: {
      fnCloseLayer(){
          jQuery("#myPagePopup").modal("hide");
      },
      // 데이터 초기화
      fnReset(){
        this.userId   = this.memberInfo.loginId;
        this.hpNumber = this.memberInfo.hpNumber;
        this.pwdUpdDt = this.memberInfo.pwdUpdDt;
        this.userName = this.memberInfo.userName;
      },
      // 번호 변경 버튼 선택시
      fnChgHpNumDiv(){
        if(jQuery("#fnChgHpNumDiv").is(":visible")){
          this.loginPwd     = '',
          this.chkLoginPwd  = ''
          jQuery("#fnChgHpNumDiv").css("display", "none");
        } else {
          jQuery("#fnChgHpNumDiv").css("display", "block");
        }
      },
      // 비밀번호 변경 버튼 선택시
      fnChgPwdDiv(){
        if(jQuery("#chgPwdDiv").is(":visible")){
          this.loginPwd     = '',
          this.chkLoginPwd  = ''
          jQuery("#chgPwdDiv").css("display", "none");
        } else {
          jQuery("#chgPwdDiv").css("display", "block");
        }
      },
      // 저장
      fnSave(){
        if(this.loginPwd.trim().length > 0){
          if(this.loginPwd != this.chkLoginPwd){
            confirm.fnAlert("비밀번호가 일치하지 않습니다.", "");
            return;
          }
        }

        var hpNumberTrim = this.chgHpNumber.trim();
        if(hpNumberTrim.length > 0){
          var reghpNumExp = RegExp(/^01(?:0|1|[6-9])(?:\d{3}|\d{4})\d{4}$/);
          if(reghpNumExp.test(hpNumberTrim) == false){
            confirm.fnAlert("", "휴대폰 번호를 정확히 입력해주세요.");
            return;
          }
        } else {
          confirm.fnAlert("", "휴대폰 번호는 필수 입력사항입니다.");
          return;
        }

        eventBus.$on('callbackEventBus', this.fnSaveCallBack);
        confirm.fnConfirm( "회원 정보 저장", "저장하시겠습니까?", "저장");
      },
      fnSaveCallBack(){
        var params = {
          loginPwd : this.loginPwd.trim(),
          hpNumber : this.hpNumber.trim(),
          chgHpNumber : this.chgHpNumber.trim(),
          authNumber  : this.authNumber
        }

        myPageApi.saveMemberInfo(params).then(response =>{
        var result = response.data;
        if(result.success) {
          confirm.fnAlert( "저장되었습니다.", "");
          this.fnCloseLayer();
        } else {
          confirm.fnAlert(result.message, "");
        }
      });
      }
  }
}
</script>
