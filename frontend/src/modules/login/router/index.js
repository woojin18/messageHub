import LoginMain from '../views/LoginMain'

export default [
  {
    path: '/login',
    name: 'login',
    component: LoginMain,
    meta: { public: true }
  }
]