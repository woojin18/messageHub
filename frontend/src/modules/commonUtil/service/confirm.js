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

2021-04-29
1. alert 후 callback이나 alert 후 alert 기능 추가
2. 메시지에 개행(\n) 처리되게 변경
//cTitle(필수) - 타이틀
//cMessage(필수) - 메세지
//cAfterType - CALLBACK 또는 ALERT(alert 후 callback이나 alert이 필요한 경우 사용)
//cParam - (callback function 에 파라미터를 넘겨야되는 경우 사용)
confirm.fnAlert(cTitle, cMessage, cAfterType, cParam)
*/
import Vue from "vue";
import {eventBus} from "./eventBus";

const fnAlert = (cTitle, cMessage, cAfterType, cParam) => {
  var confirmData = {"cTitle" : cTitle, "cMessage" : cMessage, "cButtonView" : false, "cButton" : "", "cAfterType" : (cAfterType ? cAfterType : ''), "cParam" : cParam}
  eventBus.$emit('confirmEventBus', confirmData);
  jQuery("#confirm").modal("show");
}

const fnConfirm = (cTitle, cMessage, cButton) => {
  var confirmData = {"cTitle" : cTitle, "cMessage" : cMessage, "cButtonView" : true, "cButton" : cButton}
  eventBus.$emit('confirmEventBus', confirmData);
  jQuery("#confirm").modal("show");
}

Vue.component('confirm', {
  template:
    '<div class="modal modalStyle fade" id="confirm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">'
    +  '<div class="modal-dialog">'
    +    '<div class="modal-content">'
    +      '<div class="modal-body">'
    +        '<div class="float-left">'
    +          '<h5 class="lc-1">{{cTitle}}</h5>'
    +          '<p class="mt5 lc-1"><pre>{{cMessage}}</pre></p>'
    +        '</div>'
    +        '<div class="float-right">'
    +          '<a class="btnStyle4 red" v-if="cButtonView" @click="fnCallback">{{cButton}}</a>'
    +          '<a class="btnStyle4 gray ml5" v-if="cAfterType==\'CALLBACK\'" @click="fnAfterCallback">닫기</a>'
    +          '<a class="btnStyle4 gray ml5" v-else-if="cAfterType==\'ALERT\'" @click="fnAfterAlert">닫기</a>'
    +          '<a class="btnStyle4 gray ml5" v-else @click="fnConfirmHide">닫기</a>'
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
        vm.cAfterType = (value.cAfterType ? value.cAfterType : '');
        vm.cParam = value.cParam;
      });
  },
  data() {
    return {
        cTitle: "",
        cMessage: "",
        cButtonView: true,
        cButton: "",
        cAfterType: "",
        cParam: null,
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
      jQuery("#confirm").modal("hide");
    },
    fnAfterCallback: function(){
      eventBus.$emit('callbackEventBus', this.cParam);
      this.fnConfirmHide();
    },
    fnAfterAlert: function(){
      eventBus.$emit('callbackEventBus', this.cParam);
    }
  }
});

export default {
  fnConfirm,
  fnAlert
};
