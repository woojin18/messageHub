import 'babel-polyfill'
import Vue from 'vue';
import VueCookies from 'vue-cookies';
import App from './App.vue';
import router from './router';
import store from './store';
import { coreUiMixin } from "@/common/vue-mixins";
import common from '@/assets/js/common';
import './filters'

Vue.use(VueCookies);
Vue.use(common);
Vue.$cookies.config("1d");
Vue.config.productionTip = false;

Vue.mixin(coreUiMixin);
Vue.prototype.runModeType = "LIVE";

Vue.directive('campaignIdFilter', {
  bind(el) {
    el.addEventListener('keyup', () => {
      el.value = el.value.replace(/[^a-zA-Z0-9-_]/g,'');
    });
  }
});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

