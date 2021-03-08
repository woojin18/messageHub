const state = {
  token: null,
  isLogin: false,
  isErrorPage: false,
};

const getters = {
  getToken: state => state.token,
  isLogin: state => state.isLogin,
  isErrorPage: state => state.isErrorPage,
};

const mutations = {
  saveToken: (state, token) => {
    console.log('saveToken:', token);
    state.token = token;
  },
  removeToken: (state) => {
    console.log('removeToken:');
    state.token = null;
  },
  isLogin: (state, value) => {
    console.log("isLogin mutation : " + value);
    state.isLogin = value;
  },
  isErrorPage: (state, value) => {
    console.log("isErrorPage mutation : " + value);
    state.isErrorPage = value;
  },
};

const actions = {
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};
