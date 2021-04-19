import rsc from '../views/bm-chan-rsc'
import rcsMessage from '../views/bm-chan-rsc-message'
import rcsDetail from '../views/bm-chan-rcs-detail'

import smsmms from '../views/bm-chan-smsmms'

import push from '../views/bm-chan-push'
import pushDetail from '../views/bm-chan-push-detail'

import kakao from '../views/bm-chan-kakao'

import mo from '../views/bm-chan-mo'

export default [
    {
        path: '/ac/project/channel/rcs',
        name: 'chan-rcs',
        props: true,
        component: rsc
    },
    {
        path: '/ac/project/channel/rcsMessage',
        name: 'chan-rcs-message',
        props: true,
        component: rcsMessage
    },
    {
        path: '/ac/project/channel/rcsDetail',
        name: 'chan-rcs-detail',
        props: true,
        component: rcsDetail
    },
    {
        path: '/ac/project/channel/smsmms',
        name: 'chan-smsmms',
        props: true,
        component: smsmms
    },
    {
        path: '/ac/project/channel/push',
        name: 'chan-push',
        props: true,
        component: push
    },
    {
        path: '/ac/project/channel/pushDetail',
        name: 'chan-push-detail',
        props: true,
        component: pushDetail
    },
    {
        path: '/ac/project/channel/kakao',
        name: 'chan-kakao',
        props: true,
        component: kakao
    },
    {
        path: '/ac/project/channel/mo',
        name: 'chan-mo',
        props: true,
        component: mo
    }
]