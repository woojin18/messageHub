import rsc from '../views/bm-chan-rsc'
import rcsDetail from '../views/bm-chan-rcs-detail'

import smsmms from '../views/bm-chan-smsmms'

import push from '../views/bm-chan-push'

import kakao from '../views/bm-chan-kakao'

import mo from '../views/bm-chan-mo'

export default [
    {
        path: '/project/channel/rcs',
        name: 'chan-rcs',
        props: true,
        component: rsc
    },
    {
        path: '/project/channel/rcsDetail',
        name: 'chan-rcs-detail',
        props: true,
        component: rcsDetail
    },
    {
        path: '/project/channel/smsmms',
        name: 'chan-smsmms',
        props: true,
        component: smsmms
    },
    {
        path: '/project/channel/push',
        name: 'chan-push',
        props: true,
        component: push
    },
    {
        path: '/project/channel/kakao',
        name: 'chan-kakao',
        props: true,
        component: kakao
    },
    {
        path: '/project/channel/mo',
        name: 'chan-mo',
        props: true,
        component: mo
    }
]