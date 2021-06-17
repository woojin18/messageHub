import messageStatus from '../views/bm-message-status'
import messageStatusDetail from '../views/bm-message-status-detail'
import webSend from '../views/bm-web-send'
import webSendDetail from '../views/bm-web-send-detail'
import bookingSend from '../views/bm-booking-send'
import bookingSendCancel from '../views/bm-booking-send-cancel'
import moReceptionStatus from '../views/bm-mo-reception-status'

export default [

    {
        path: '/uc/messageStatus',
        name: 'messageStatus',
        meta: {menu: 'UC_MEST_RCV'},
        component: messageStatus
    },
    {
        path: '/uc/messageStatusDetail',
        name: 'messageStatusDetail',
        component: messageStatusDetail,
        props: true
    },    
    {
        path: '/uc/webSend',
        name: 'webSend',
        meta: {menu: 'UC_WBSN_RCV'},
        component: webSend
    },    
    {
        path: '/uc/webSendDetail',
        name: 'webSendDetail',
        component: webSendDetail
    },    
    {
        path: '/uc/bookingSend',
        name: 'bookingSend',
        meta: {menu: 'UC_BKSN_RCV'},
        component: bookingSend
    },    
    {
        path: '/uc/bookingSendCancel',
        name: 'bookingSendCancel',
        component: bookingSendCancel
    },    
    {
        path: '/uc/moReceptionStatus',
        name: 'moReceptionStatus',
        meta: {menu: 'UC_MORE_RCV'},
        component: moReceptionStatus
    }
]