import Vue from 'vue';

//핸도폰 번호 '-' 적용 필터
Vue.filter('phoneNumAddDash', (value) => {
  return Vue.prototype.$gfnCommonUtils.phoneNumAddDash(value);
});

//가격 콤마 처리
Vue.filter('formatPrice', (value) => {
  return Vue.prototype.$gfnCommonUtils.formatPrice(value);
});