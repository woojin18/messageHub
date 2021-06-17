import smartSend from '../views/bm-smart-send'
import smartSendManage from '../views/bm-smart-send-manage'

export default [
    {
        path: '/uc/smartSendManage',
        name: 'smartSendManage',
        component: smartSendManage,
        props: true
    },
    {
        path: '/uc/smartSend',
        name: 'smartSend',
        meta: {menu: 'UC_SND_SMRT_MSG'},
        component: smartSend
    }
]