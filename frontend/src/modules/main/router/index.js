import consoleMain from '../views/bm-consoleMain.vue'
import sendPay from '../views/bm-sendPay.vue'
import channelIntro from '../views/bm-channelIntro.vue'

export default [
  {
    path: '/public/main',
    name: 'main',
    component: consoleMain, 
    meta: { public: true }
  },
  {
    path: '/public/sendPay',
    name: 'sendPay',
    component: sendPay,
    meta: { public: true }
  },
  {
    path: '/public/channelIntro',
    name: 'channelIntro',
    component: channelIntro,
    meta: { public: true }
  }
  
]