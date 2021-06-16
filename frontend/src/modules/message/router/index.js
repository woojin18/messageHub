import SendPushMain from '../views/bm-sendPushMain.vue'
import SendSmsMain from '../views/bm-sendSmsMain.vue'
import SendFrndTalkMain from '../views/bm-sendFrndTalkMain.vue'
import SendAlimTalkMain from '../views/bm-sendAlimTalkMain.vue'

export default [
    {
        path: '/uc/message/sendPush',
        name: 'sendPush',
        meta: { menu: 'UC_SND_PUSH_MSG' },
        component: SendPushMain
    },
    {
        path: '/uc/message/sendSms',
        name: 'sendSms',
        meta: { menu: 'UC_SND_SMS_MSG' },
        component: SendSmsMain
    },
    {
        path: '/uc/message/sendFrndTalk',
        name: 'sendFrndTalk',
        meta: { menu: 'UC_SND_FRND_MSG' },
        component: SendFrndTalkMain
    },
    {
        path: '/uc/message/sendAlimTalk',
        name: 'sendAlimTalk',
        meta: { menu: 'UC_SND_ALIM_MSG' },
        component: SendAlimTalkMain
    }
]