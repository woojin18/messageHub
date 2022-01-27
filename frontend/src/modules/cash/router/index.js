import cashMain from '../views/bm-cashMain'
import cashMain2 from '../views/bm-cashMain2'
import cashDept from '../views/bm-cashDept'

export default [
    {
        path: '/ac/cash',
        name: 'cashMain',
        meta: { menu: 'AC_SETTLE_MNG1' },
        props: true,
        component: cashMain
    },
    {
        path: '/ac/cash2',
        name: 'cashMain2',
        meta: { menu: 'AC_SETTLE_MNG1' },
        props: true,
        component: cashMain2
    },
    {
        path: '/ac/cash/dept',
        name: 'cashDept',
        meta: { menu: 'AC_SETTLE_MNG3' },
        props: true,
        component: cashDept
    }
]