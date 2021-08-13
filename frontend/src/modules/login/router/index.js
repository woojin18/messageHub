import loginMain from '../views/bm-loginMain'
import findUserId from '../views/bm-findUserId'
import findUserPwd from '../views/bm-findUserPwd'
import setUserPwd from '../views/bm-setUserPwd'
import chkCertifyFail from '../views/bm-chkCertifyFail'
import resetUserPwd from '../views/bm-resetUserPwd'

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
	},
	{
		path: '/login/setUserPwd',
		name: 'setUserPwd',
		component: setUserPwd,
		meta: { public: true }
	},
	{
		path: '/login/chkCertifyFail',
		name: 'chkCertifyFail',
		component: chkCertifyFail,
		meta: { public: true }
	},
	{
		path: '/login/resetUserPwd',
		name: 'resetUserPwd',
		component: resetUserPwd,
		meta: { public: true }
	}
]