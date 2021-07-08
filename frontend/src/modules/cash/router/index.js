import cashMain from '../views/bm-cashMain'

export default [
    {
        path: '/ac/cash',
        name: 'cashMain',
        meta: { menu: 'AC_SETTLE_MNG' },
        props: true,
        component: cashMain
    }
]