import rsc from '../views/bm-chan-rsc'
import rcsDetail from '../views/bm-chan-rcs-detail'

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
    }
]