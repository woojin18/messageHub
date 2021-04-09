import Vue from 'vue';
import Router from 'vue-router';
import searchcondition from './store'
import tokenSvc from '@/common/token-service';

import WebNaviLayout from './views/WebNaviLayout.vue';
import LoginLayout from './views/LoginLayout.vue';

import homeRoutes from './modules/home/router';
import loginRoutes from './modules/login/router';
import listRoutes from './modules/list/router';
import channelRoutes from './modules/channel/router';
import projectRoutes from './modules/project/router';
import messageRoutes from './modules/message/router';
import templateRoutes from './modules/template/router';
import signUpRoutes from './modules/signUp/router';
import cashRoutes from './modules/cash/router';

Vue.use(Router)

const router = new Router({
	mode: 'history',
	base: process.env.BASE_URL,
	routes: [
		{
			path: '/login',
			component: LoginLayout,
			children: [
				{
					path: '/view/error/404',
					component: () => import('./views/ErrorPage404.vue'),
					meta: { public: true }
				},
				{
					path: '/view/error/500',
					component: () => import('./views/ErrorPage500.vue'),
					meta: { public: true }
				},
				...loginRoutes,
				...signUpRoutes
			]
		},
		{
			path: '/',
			component: WebNaviLayout,
			// beforeEnter: requireAuth(),
			children: [
				{
					path: '/view/error/404',
					component: () => import('./views/ErrorPage404.vue'),
					meta: { public: true }
				},
				{
					path: '/view/error/500',
					component: () => import('./views/ErrorPage500.vue'),
					meta: { public: true }
				},
				...homeRoutes,
				...listRoutes,
				...channelRoutes,
				...projectRoutes,
				...cashRoutes,
				...messageRoutes,
				...templateRoutes
			]
		},
		{path: '*', redirect: '/view/error/404'}
	]
});

router.beforeEach((to, from, next) => {
	const isPublic = to.matched.some(record => record.meta.public);
	const loggedIn = !!tokenSvc.getToken();

	if (!isPublic && !loggedIn) {
		//return next('/login');
	}

	to.matched.some(record => {
		if (record.meta.usingSearchCondition) {
			const shareList = record.meta.shareList;
			if (from.name && shareList && shareList.includes(from.name)) {
				// shareList에 포함되어 있는 라우터에서 온 경우 검색 조건을 유지한다.
				// console.log("패밀리");
			} else {
				// 그 외의 경우 검색 조건 초기화
				searchcondition.commit("searchcondition/updateSearchCondition", null);
				// console.log("낫패밀리");
			}
		}
		next();
	});
});

export default router;
