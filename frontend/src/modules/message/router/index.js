import SendPushMain from '../views/bm-sendPushMain.vue'
import SendSmsMain from '../views/bm-sendSmsMain.vue'

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
    }
]