import consoleMain from '../views/bm-consoleMain.vue'
import serviceIntro from '../views/bm-serviceIntro.vue'
import sendPay from '../views/bm-sendPay.vue'

export default [
  {
    path: '/main',
    name: 'main',
    component: consoleMain,
    meta: { public: true }
  },
  {
    path: '/intro',
    name: 'intro',
    component: serviceIntro,
    meta: { public: true }
  },
  {
    path: '/sendPay',
    name: 'sendPay',
    component: sendPay,
    meta: { public: true }
  }
  
]