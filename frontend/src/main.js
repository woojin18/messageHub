import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import { coreUiMixin } from "@/common/vue-mixins";

Vue.config.productionTip = false;

Vue.mixin(coreUiMixin);
Vue.prototype.runModeType = "LIVE";

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

