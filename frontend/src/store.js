import Vue from 'vue'
import Vuex from 'vuex'
import searchcondition from './store/search-condition'
import login from './modules/login/store'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    searchcondition,
    login,
  }
})
