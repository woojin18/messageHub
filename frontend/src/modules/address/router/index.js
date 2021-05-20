import addressManage from '../views/bm-address-manage'
//import recipientManage from '../views/bm-recipient-manage'
export default [
	{
		path: '/ac/address/manage',
		name: 'addressManage',
		component: addressManage,
		props: true
	}/*,
	{
		path: '/ac/address/recipient/manage',
		name: 'recipientManage',
		component: recipientManage,
		props: true
	}*/
]