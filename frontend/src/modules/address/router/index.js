import addressManage from '../views/bm-address-manage'
import receiverManage from '../views/bm-receiver-manage'
export default [
	{
		path: '/ac/address/manage',
		name: 'addressManage',
		component: addressManage,
		props: true
	},
	{
		path: '/ac/address/receiver/manage',
		name: 'receiverManage',
		component: receiverManage,
		props: true
	},
]