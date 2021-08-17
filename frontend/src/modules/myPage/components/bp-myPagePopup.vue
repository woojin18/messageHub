<template>
  <div class="modal fade modalStyle in" id="myPagePopup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
					<div class="of_h">
						<h2>회원정보</h2>
						<hr>
						<div class="of_h">
							<h5 class="inline-block" style="width:20%">사용자ID</h5>
							<h5 class="inline-block float-right" style="width:80%">{{ loginId }}</h5>
						</div>	
					
						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:20%">이름</h5>
							<input type="text" class="inputStyle float-right" style="width:80%" title="이름 입력란"  v-model="userName" disabled>
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:20%">대표 프로젝트</h5>
							<select class="selectStyle2 float-right"  style="width:80%" v-model="repProjectId">
								<option value="">선택</option>
								<option  v-for="(row, index) in repProjectArr" :key="index" :value="row.projectId"> {{ row.projectName }} </option>
							</select>
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:20%">비밀번호</h5>
							<h5 class="inline-block" style="width:53%">최종변경일 : {{ pwdUpdDt }}</h5>
							<a class="btnStyle1 backLightGray float-right width120" title="비밀번호 변경" @click="fnChgPwdDiv">비밀번호 변경</a>
						</div>
            <div id="chgPwdDiv" style="display:none;">
              <div class="of_h consolMarginTop">
                <h5 class="inline-block" style="width:20%">비밀번호 변경</h5>
                <div class="float-right" style="width:80%">
                  <input type="password" class="inputStyle" title="비밀번호 변경 입력란" v-model="loginPwd" placeholder="새 비밀번호">
                  <input type="password" class="inputStyle consolMarginTop" title="비밀번호 변경 확인 입력란" v-model="chkLoginPwd" placeholder="새 비밀번호 확인">
                </div>
              </div>
            </div>
            <div class="of_h consolMarginTop">
              <h5 class="inline-block" style="width:20%">휴대폰 번호</h5>
              <input type="text" class="inputStyle" style="width:55%" title="휴대폰 번호 입력란"  v-model="hpNumber" :disabled="true">
              <a href="#self" class="btnStyle1 backLightGray float-right width120" title="번호 변경"  @click="fnChgHpNumDiv">번호 변경</a>
            </div>
            <div id="fnChgHpNumDiv" style="display:none;">
              <div class="of_h consolMarginTop">
                <h5 class="inline-block" style="width:20%">휴대폰 번호 변경</h5>
                <input type="text" class="inputStyle" style="width:55%" title="휴대폰 번호 변경 입력란" v-model="chgHpNumber" placeholder="-없이 입력">
                <button @click="fnSetCertifyNumber" id="certifyBtn" class="btnStyle1 backLightGray float-right width120" title="인증번호 받기" >{{ certifyMsg }}</button>
              </div>
              <div class="of_h consolMarginTop">
                <h5 class="inline-block" style="width:20%">인증번호</h5>
                <input type="text" class="inputStyle float-right" style="width:80%" title="인증번호 입력란" placeholder="인증번호 입력" v-model="certifyNumber">
              </div>
            </div>
						<div class="of_h mt30">
							<div class="text-center">
								<a @click="fnSave" class="btnStyle1 backBlack" title="저장">저장</a>
								<a @click="fnCloseLayer" class="btnStyle1 backWhite ml10" title="취소">취소</a>
							</div>
						</div>
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
      loginId    : '',
      userName  : '',
      pwdUpdDt  : '',
      hpNumber  : '',
      loginPwd  : null,
      chkLoginPwd : null,
      chgHpNumber : '',
      certifyNumber  : '',
      certifyMsg : '인증번호 받기',
      repProjectId : "",
      repProjectArr : []
    }
  },
  props: {
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
	this.fnGetRepProjectByUserId();
  },
  watch: {
    popReset() {
      // 비밀번호 변경 버튼을 눌러야 display 처리
      jQuery("#chgPwdDiv").css("display", "none");
      // 번호 변경 버튼을 눌러야 display 처리
      jQuery("#fnChgHpNumDiv").css("display", "none");
      // 인증번호 받기 버튼 활성화
      jQuery("#certifyBtn").prop("disabled", false);
      this.fnReset();
    },
    chgHpNumber(){
      return this.chgHpNumber = this.chgHpNumber.replace(/[^0-9]/g, '');
    }
  },
  methods: {
      fnCloseLayer(){
        jQuery("#myPagePopup").modal("hide");
      },
      // 데이터 초기화
      fnReset(){
        this.loginId  = this.memberInfo.loginId;
        this.hpNumber = this.memberInfo.hpNumber;
        this.pwdUpdDt = this.memberInfo.pwdUpdDt;
        this.userName = this.memberInfo.userName;
        this.certifyMsg = "인증번호 받기";
        this.repProjectId = this.memberInfo.repProjectId != "" && this.memberInfo.repProjectId != null ? this.memberInfo.repProjectId : "";
        
        // 변경할 비밀번호, 전화번호 초기화
        this.chgHpNumber  = '';
        this.certifyNumber   = '';
        this.loginPwd     = null;
        this.chkLoginPwd  = null;
      },
      // 번호 변경 버튼 선택시
      fnChgHpNumDiv(){
        if(jQuery("#fnChgHpNumDiv").is(":visible")){
          this.chgHpNumber  = '';
          this.certifyNumber   = '';
          jQuery("#fnChgHpNumDiv").css("display", "none");
        } else {
          jQuery("#fnChgHpNumDiv").css("display", "block");
        }
      },
      // 비밀번호 변경 버튼 선택시
      fnChgPwdDiv(){
        if(jQuery("#chgPwdDiv").is(":visible")){
          this.loginPwd     = null;
          this.chkLoginPwd  = null;
          jQuery("#chgPwdDiv").css("display", "none");
        } else {
          jQuery("#chgPwdDiv").css("display", "block");
        }
      },
      // 휴대폰 변경 인증번호 받기
      fnSetCertifyNumber(){
        if(this.chgHpNumber.trim().length == 0){
            confirm.fnAlert("", "변경하실 휴대폰 번호를 입력해주세요.");
            return;
        }

        this.certifyMsg = "전송 중..";
        var params = {
          chgHpNumber : this.chgHpNumber
        };

        myPageApi.setCertifyNumber(params).then(response => {
          var result = response.data;
          if(result.success){
            this.certifyMsg = "인증 요청";

          } else {
            confirm.fnAlert("", result.message);
            return;
          }
        })
      },
      // 저장
      fnSave(){
        if(jQuery.trim(this.loginPwd).length > 0){
          var reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;

          if(!reg.test(this.loginPwd)){
            confirm.fnAlert("", "비밀번호는 8자 이상이어야 하며, 숫자/대문자/소문자/특수문자를 모두 포함해야 합니다.");
            return;
          }
          if(this.loginPwd != this.chkLoginPwd){
            confirm.fnAlert("", "변경하려는 비밀번호가 일치하지 않습니다.");
            return;
          }
        }

        if(jQuery.trim(this.chgHpNumber).length > 0){
          if(this.certifyNumber.trim().length == 0){
            confirm.fnAlert("", "인증번호를 입력해주세요.");
            return;
          }
        }

        eventBus.$on('callbackEventBus', this.fnSaveCallBack);
        confirm.fnConfirm( "회원 정보 저장", "저장하시겠습니까?", "저장");
      },
      fnSaveCallBack(){
        var params = {
          loginPwd : this.loginPwd,
          hpNumber : jQuery.trim(this.chgHpNumber),
          certifyNumber : jQuery.trim(this.certifyNumber),
          repProjectId : this.repProjectId
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
      },
      fnGetRepProjectByUserId(){
        // 대표 프로젝트 리스트 조회
        myPageApi.selectProejctList().then(response=>{
          var result = response.data;
          if(result.success){
            this.repProjectArr = result.data;
          }
        });
      }
  }
}
</script>
