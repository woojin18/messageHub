import SendPushMain from '../views/bm-sendPushMain.vue'
import SendSmsMain from '../views/bm-sendSmsMain.vue'
import SendFrndTalkMain from '../views/bm-sendFrndTalkMain.vue'
import SendAlimTalkMain from '../views/bm-sendAlimTalkMain.vue'

export default [
    {
        path: '/uc/message/sendPush',
        name: 'sendPush',
        component: SendPushMain
    },
    {
        path: '/uc/message/sendSms',
        name: 'sendSms',
        component: SendSmsMain
    },
    {
        path: '/uc/message/sendFrndTalk',
        name: 'sendFrndTalk',
        component: SendFrndTalkMain
    },
    {
        path: '/uc/message/sendAlimTalk',
        name: 'sendAlimTalk',
        component: SendAlimTalkMain
    }
]