/*
공통 컨펌창

간단하게 사용할 수 있는 공통 컨펌 창

해당 파일을 import한 뒤
import confirm from "@/modules/commonUtil/service/confirm"

alert창을 쓸 경우
confirm.fnAlert("타이틀", "메세지");

confirm창을 쓸 경우 이벤트버스를 import하고
import {eventBus} from "@/modules/commonUtil/service/eventBus";

eventBus.$on('callbackEventBus', this.fnCallback);
confirm.fnConfirm("타이틀", "메세지", "callback 버튼텍스트");

로 사용할 수 있다.

callback메소드는 반드시 해당 파일을 imoport한 페이지에 존재하여야 한다.

예시는 테스트 메뉴를 확인
*/
import Vue from "vue";
import {eventBus} from "./eventBus";

const fnAlert = (cTitle, cMessage) => {
  var confirmData = {"cTitle" : cTitle, "cMessage" : cMessage, "cButtonView" : false, "cButton" : ""}
  eventBus.$emit('confirmEventBus', confirmData);
  $("#confirm").modal("show");
}

const fnConfirm = (cTitle, cMessage, cButton) => {
  var confirmData = {"cTitle" : cTitle, "cMessage" : cMessage, "cButtonView" : true, "cButton" : cButton}
  eventBus.$emit('confirmEventBus', confirmData);
  $("#confirm").modal("show");
}

Vue.component('confirm', {
  template: 
    '<div class="modal modalStyle" id="confirm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">'
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
      });
  },
  data() {
    return {
        cTitle: "",
        cMessage: "",
        cButtonView: true,
        cButton: ""
    }
  },
  methods: {
    fnCallback: function() {
      if(this.cButtonView) {
        eventBus.$emit('callbackEventBus');
      }
      this.fnConfirmHide();
    },
    fnConfirmHide: function() {
      eventBus.$off('callbackEventBus');
      $("#confirm").modal("hide");
    }
  }
});

export default {
  fnConfirm,
  fnAlert
};