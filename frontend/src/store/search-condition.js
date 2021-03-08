const state = {
  searchCondition: null
}

const getters = {
  getSearchCondition: state => state.searchCondition,
}

const mutations = {
  updateSearchCondition: (state, data) => {
    state.searchCondition = data;
  }
}

const actions = {
}

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};