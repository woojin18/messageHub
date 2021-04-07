/*
공통 컨펌창

간단하게 사용할 수 있는 공통 컨펌 창

해당 파일을 import한 뒤
import confirm from "@/modules/commonUtil/service/confirm"

confirm 태그를 넣어준다.
<confirm></confirm>

confirm.fnConfirm("타이틀", "메세지", callback 버튼 여부, "callback 버튼", "callback 버튼 클릭 시 callback메소드");

로 사용할 수 있다.

callback메소드는 반드시 해당 파일을 imoport한 페이지에 존재하여야 한다.

부모창에서 공통 컨펌창을 사용하면서 팝업창에서 공통 컴펌창을 사용할 경우
팝업창에는 태그는 넣지않아야 하며 callback메소드는 사용할 수 없다.

예시는 테스트 메뉴를 확인
*/

import Vue from 'vue';
var eventBus = new Vue();

const fnConfirm = (cTitle, cMessage, cButtonView, cButton, cCallBack) => {
  var confirmData = {"cTitle" : cTitle, "cMessage" : cMessage, "cButtonView" : cButtonView, "cButton" : cButton, "cCallBack" : cCallBack}
  eventBus.$emit('confirmEventBus', confirmData);
  $("#confirm").modal("show");
}

const fnAlert = (cTitle) => {
  fnConfirm(cTitle, "", false, "", "");
}

Vue.component('confirm', {
  template: 
    '<div class="modal modalStyle" id="confirm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'
    +  '<div class="modal-dialog">'
    +    '<div class="modal-content">'
    +      '<div class="modal-body">'
    +        '<div class="float-left">'
    +          '<h5 class="lc-1">{{cTitle}}</h5>'
    +          '<p class="mt5 lc-1">{{cMessage}}</p>'
    +        '</div>'
    +        '<div class="float-right">'
    +          '<a class="btnStyle4 red" v-if="cButtonView" @click="fnCallback">{{cButton}}</a>'
    +          '<a class="btnStyle4 gray ml5" @click="fnConfirmHide">닫기</a>'
    +        '</div>'
    +      '</div>'
    +    '</div>'
    +  '</div>'
    +'</div>',
  created: function() {
      var vm = this;
      eventBus.$on('confirmEventBus', function(value){
        vm.cTitle = value.cTitle;
        vm.cMessage = value.cMessage;
        vm.cButtonView = value.cButtonView;
        vm.cButton = value.cButton;
        vm.cCallBack = value.cCallBack;
      });
  },
  data() {
    return {
        cTitle: "",
        cMessage: "",
        cButtonView: true,
        cButton: "",
        cCallBack: ""
    }
  },
  methods: {
    fnCallback: function() {
      if(this.cCallBack) {
        eval("this.$parent." + this.cCallBack + "()");
      }
      this.fnConfirmHide();
    },
    fnConfirmHide: function() {
      $("#confirm").modal("hide");
    }
  }
});

export default {
  fnConfirm,
  fnAlert
};
