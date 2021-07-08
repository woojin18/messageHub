import 'babel-polyfill'
import Vue from 'vue';
import VueCookies from 'vue-cookies';
import App from './App.vue';
import router from './router';
import store from './store';
import { coreUiMixin } from "@/common/vue-mixins";
import common from '@/assets/js/common';

Vue.use(VueCookies);
Vue.use(common);
Vue.$cookies.config("1d");
Vue.config.productionTip = false;

Vue.mixin(coreUiMixin);
Vue.prototype.runModeType = "LIVE";

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

