<template>

  <article id="content" class="content">
    
    <div class="login_wrap">
      <div class="box_login">
        <div class="top_title_area">
          <h2 class="h2_title"> <img src="@/assets/images/common/logo_login.png" alt="유플러스 RCS"></h2>
        </div>
        
        <ul class="input_area">
          <!--<li><span class="custom_input big">
              <input type="text" placeholder="고객아이디" name="corpId" v-model="corpId" :maxlength="15" ></span></li>  -->
          <li><span class="custom_input big">
              <input type="text" placeholder="아이디" name="userId" v-model="userId" :maxlength="15" v-focus @keyup.enter="ajaxlogin"></span></li>
          <li><span class="custom_input big">
              <input type="password" placeholder="비밀번호" name="userPwd" v-model="userPwd" :maxlength="20" @keyup.enter="ajaxlogin"></span></li>
        </ul>
        <!-- 에러메세지 출력 -->
        <div class="message_area" v-if="errors.length">
          <p class="text_error">{{ errors[0] }}</p>
        </div>
        <div class="check_area"><span class="custom_checkbox">
            <input type="checkbox" id="saveId" ref="chkSaveId">
            <label for="saveId">아이디 저장</label></span></div>
        <!-- -->
        <div class="btn_wrap row">
          <a href="javascript:void(0);" class="btn big point on" @click.prevent="ajaxlogin"><span>로그인</span></a>
        </div>
        
      </div>
    </div>
    
  </article>

</template>

<script>
  import loginApi from "@/modules/login/service/api"
  import * as utils from '@/common/utils';

  export default {
    data: function() {
      return {
        errors: [],
        userId: '',
        userPwd: ''
      };
    },
    created() {
      // 로그인 페이지 진입시
      this.$store.commit("login/isLogin", false);
      this.$store.commit("login/isErrorPage", false);

    },
    mounted() {
      this.$refs.chkSaveId.checked = true;

      if (localStorage.wtmpltUserId) {  
        this.userId = localStorage.wtmpltUserId;
      }

      let cont = document.querySelector("article.content");

      let contain = document.querySelector("div.container");
    },
    destroyed() {
      let cont = document.querySelector("article.content");

      let contain = document.querySelector("div.container");
    },
    methods: {
      chgChkUserId() {
        if (this.$refs.chkSaveId.checked == true) {
          localStorage.wtmpltUserId = this.userId;
        } else {
          delete localStorage.wtmpltUserId;
        }
      },
      toRegister(){
        this.$router.push({ name: 'register'});
      },
      formCheck: function() {
        this.errors = [];

        if (!this.userId) this.errors.push('아이디를 입력해 주세요.');
        if (!this.userPwd) this.errors.push('비밀번호를 입력해 주세요.');

        return this.errors.length == 0;
      },
      ajaxlogin: function() {
        var vm = this;
        vm.errmsg = null;

        if (!vm.formCheck()) return false;

        // FormData 객체를 파라미터로 넘기면 Content-Type: multipart/form-data; 요청을 한다.
        // 일반 Object를 파라미터로 넘기면 Content-Type: application/json;charset=UTF-8 요청을 한다.
        var params = {
          //"corpId": this.corpId,
          "userId": this.userId,
          "userPwd": this.userPwd,
        }
        
        loginApi.login(params).then(function(response) {
          var rsp = response.data;
          console.log('rsp:', rsp);
          if (rsp.success) {
            vm.chgChkUserId();
            vm.$store.commit("login/isLogin", true);
            vm.$router.push({ path: rsp.data });
          } else {
            vm.$store.commit("login/isLogin", false);
            var msg = null,
              next = null;
            switch (rsp.code) {
              case 'CE_ID_PWD': // ID/PWD 불일치
                vm.errors.push('로그인 실패하였습니다. (5회 실패 시 계정 잠김)');
                break;
              case 'SS_NOT_USE': // 중지 계정
              case 'SS_LOCK': // 잠김 계정
                msg = '관리자 승인 후 이용할 수 있습니다.';
                break;
              case 'SS_PWD_EXPIRE': // 비밀번호 만료
                msg = '비밀번호를 변경하신지 90일이 지났습니다.\n비밀번호 변경 화면으로 이동합니다.';
                next = '/login?expire';
                break;
              case 'SS_INIT_PWD': // 최초 비밀번호 변경 필요
                msg = '새로운 비밀번호로 변경이 필요합니다.\n비밀번호 변경 화면으로 이동합니다.';
                next = function() {
                  // vue-router를 사용하여 데이터 전달
                  vm.$router.push({ name: 'changepwd', params: { userId: vm.userId } });
                };
                break;
              case 'CE_TO_LOCK': // ID/PWD 불일치 횟수초과로 계정 잠김
                msg = '5회 이상 로그인 실패하여 해당 아이디에 대한 계정이 잠금처리되었습니다.\n관리자에게 문의하세요.';
                break;
              default:
                vm.errmsg = '인증에 실패했습니다.';
            }

            if (msg != null) {
              alert(msg);
              if (next != null) {
                next();
              }
            }
          }
        });
      }
    }
  };
</script>
