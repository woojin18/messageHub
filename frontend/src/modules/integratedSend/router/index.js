import integratedSend from '../views/bm-integrated-send'
/* import integratedSendManage from '../views/bm-integrated-send-manage' */
import SendIntegMain from '../views/bm-integrated-sendMain'

export default [
/*     {
        path: '/uc/integratedSendManage',
        name: 'integratedSendManage',
        component: integratedSendManage,
        props: true
    }, */
    {
        path: '/uc/integratedSend',
        name: 'integratedSend',
        meta: { menu: 'UC_SND_INTG_MSG' },
        component: integratedSend
    },
    {
        path: '/uc/sendIntegMessage',
        name: 'sendIntegMessage',
        meta: { menu: 'UC_SND_INTG_MSG' },
        component: SendIntegMain,
        props: true
    }
]