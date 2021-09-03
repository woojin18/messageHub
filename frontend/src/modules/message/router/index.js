import SendPushMain from '../views/bm-sendPushMain.vue'
import SendSmsMain from '../views/bm-sendSmsMain.vue'
import SendFrndTalkMain from '../views/bm-sendFrndTalkMain.vue'
import SendAlimTalkMain from '../views/bm-sendAlimTalkMain.vue'
import SmartSendList from '../views/bm-smartSendList.vue'
import MultiSendList from '../views/bm-multiSendList.vue'
import SmartSendMain from '../views/bm-smartSendMain.vue'

export default [
  {
    path: '/uc/message/sendPush',
    name: 'sendPush',
    meta: { menu: 'UC_SND_PUSH_MSG' },
    component: SendPushMain,
    props: true
  },
  {
    path: '/uc/message/sendSms',
    name: 'sendSms',
    meta: { menu: 'UC_SND_SMS_MSG' },
    component: SendSmsMain,
    props: true
  },
  {
    path: '/uc/message/sendFrndTalk',
    name: 'sendFrndTalk',
    meta: { menu: 'UC_SND_FRND_MSG' },
    component: SendFrndTalkMain,
    props: true
  },
  {
    path: '/uc/message/sendAlimTalk',
    name: 'sendAlimTalk',
    meta: { menu: 'UC_SND_ALIM_MSG' },
    component: SendAlimTalkMain,
    props: true
  },
  {
    path: '/uc/message/smartSendList',
    name: 'smartSendList',
    meta: { menu: 'UC_SND_SMRT_MSG' },
    component: SmartSendList,
    props: true
  },
  {
    path: '/uc/message/smartSendMain',
    name: 'smartSendMain',
    meta: { menu: 'UC_SND_SMRT_MSG' },
    component: SmartSendMain,
    props: true
  },
  {
    path: '/uc/message/multiSendList',
    name: 'multiSendList',
    meta: { menu: 'UC_SND_INTG_MSG' },
    component: MultiSendList,
    props: true
  },
  {
    path: '/uc/message/smartSendMain',
    name: 'multiSendMain',
    meta: { menu: 'UC_SND_INTG_MSG' },
    component: SmartSendMain,
    props: true
  }
]