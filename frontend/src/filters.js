import Vue from 'vue';

//핸도폰 번호 '-' 적용 필터
Vue.filter('phoneNumAddDash', (value) => {
  return Vue.prototype.$gfnCommonUtils.phoneNumAddDash(value);
});

//핸도폰 번호 '-' 적용 필터2
Vue.filter('hpNumberAddDash', (value) => {
  return Vue.prototype.$gfnCommonUtils.hpNumberAddDash(value);
});

//가격 콤마 처리
Vue.filter('formatPrice', (value) => {
  return Vue.prototype.$gfnCommonUtils.formatPrice(value);
});
//숫자 콤마 처리(아무 값 입력 안되어있는 경우 리턴 0)
Vue.filter('formatComma', (value) => {
  return Vue.prototype.$gfnCommonUtils.formatComma(value);
});
//xss unescape
Vue.filter('unescapeXss', (value) => {
  return Vue.prototype.$gfnCommonUtils.unescapeXss(value);
});

//NOTICE 아이콘 class(noticeType -> class)
Vue.filter('getNotiTypeClass', (noticeType) => {
  if(noticeType == 'INFO') return 'Information';
  else if(noticeType == 'INSPEC') return 'Inspect';
  else if(noticeType == 'FAULT') return 'Fault';
  else return 'newIcon';
});

//MMS -> LMS/MMS
Vue.filter('lmsmms', (value) => {
  if(!value) return '';
  else if(value == 'MMS') return 'LMS/MMS';
  else return value;
});

//FCM(안드로이드), APNS(IOS)
Vue.filter('pushSvcCd', (value) => {
  if(!value) return '';
  else if(value == 'APNS') return 'APNS(IOS)';
  else if(value == 'FCM') return 'FCM(안드로이드)';
  else return value;
});

//RCS 아이콘 URL
Vue.filter('getIconURlByFormNm', (value) => {
  if(!value) return '';
  value = value.toString();
  let rtnStr = '';

  if(value.indexOf("승인") != -1) {
    rtnStr = require("@/assets/images/common/approve.png");
  }
  if(value.indexOf("취소") != -1) {
    rtnStr = require("@/assets/images/common/cancel.png");
  }
  if(value.indexOf("인증") != -1) {
    rtnStr = require("@/assets/images/common/certification.png");
  }
  if(value.indexOf("출고") != -1) {
    rtnStr = require("@/assets/images/common/delivery.png");
  }
  if(value.indexOf("안내") != -1) {
    rtnStr = require("@/assets/images/common/infomation.png");
  }
  if(value.indexOf("회원가입") != -1) {
    rtnStr = require("@/assets/images/common/join.png");
  }
  if(value.indexOf("주문") != -1) {
    rtnStr = require("@/assets/images/common/order.png");
  }
  if(value.indexOf("출금") != -1) {
    rtnStr = require("@/assets/images/common/payment.png");
  }
  if(value.indexOf("입금") != -1) {
    rtnStr = require("@/assets/images/common/receipts.png");
  }
  if(value.indexOf("예약") != -1) {
    rtnStr = require("@/assets/images/common/reservation.png");
  }
  if(value.indexOf("배송") != -1) {
    rtnStr = require("@/assets/images/common/ship.png");
  }
  if(value.indexOf("명세서") != -1) {
    rtnStr = require("@/assets/images/common/specifications.png");
  }
  return rtnStr;
});