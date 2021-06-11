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
        component: smartSend
    }
]