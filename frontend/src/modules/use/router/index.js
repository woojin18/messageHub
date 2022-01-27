import useHistory from '../views/bm-use-main'
import useState from '../views/bm-use-state'

export default [
	{
		path: '/ac/use/history',
		name: 'useHistory',
		meta: { menu: 'AC_SETTLE_MNG2'},
		component: useHistory
	},
	{
		path: '/ac/use/state',
		name: 'useState',
		meta: { menu: 'AC_USE_STATE'},
		component: useState
	},
]