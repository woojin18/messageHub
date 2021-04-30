import loginMain from '../views/bm-loginMain'
import findUserId from '../views/bm-findUserId'
import findUserPwd from '../views/bm-findUserPwd'

export default [
	{
		path: '/login',
		name: 'login',
		component: loginMain,
		meta: { public: true }
	},
	{
		path: '/login/findUserId',
		name: 'findUserId',
		component: findUserId,
		meta: { public: true }
	},
	{
		path: '/login/findUserPwd',
		name: 'findUserPwd',
		component: findUserPwd,
		meta: { public: true }
	}
]