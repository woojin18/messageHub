import userManage from '../views/bm-user-manage'

export default [
	{
		path: '/ac/user/manage',
		name: 'userManage',
        meta: { menu: 'AC_USER_LIST' },
		component: userManage,
		props: true
	}
]