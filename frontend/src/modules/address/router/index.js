import addressManage from '../views/bm-address-manage'
import receiverManage from '../views/bm-receiver-manage'
export default [
	{
		path: '/ac/address/manage',
		name: 'addressManage',
		meta: { menu: 'AC_ADDR_LIST'},
		component: addressManage,
		props: true
	},
	{
		path: '/ac/address/receiver/manage',
		name: 'receiverManage',
		meta: { menu: 'AC_RCVR_LIST'},
		component: receiverManage,
		props: true
	},
]