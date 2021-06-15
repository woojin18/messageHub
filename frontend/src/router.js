import Vue from 'vue';
import Router from 'vue-router';
import searchcondition from './store'
import tokenSvc from '@/common/token-service';

import WebUcNaviLayout from './views/WebUcNaviLayout.vue';
import WebNaviLayout from './views/WebNaviLayout.vue';
//import LoginLayout from './views/LoginLayout.vue';
import PublicLayout from './views/publicLayout.vue';

import mainRoutes from './modules/main/router';
import acHomeRoutes from './modules/acHome/router';
import ucHomeRoutes from './modules/ucHome/router';
import loginRoutes from './modules/login/router';
import listRoutes from './modules/list/router';
import channelRoutes from './modules/channel/router';
import projectRoutes from './modules/project/router';
import messageRoutes from './modules/message/router';
import templateRoutes from './modules/template/router';
import signUpRoutes from './modules/signUp/router';
import cashRoutes from './modules/cash/router';
import userRoutes from './modules/user/router';
import messageStatusRoutes from './modules/messageStatus/router';
import integratedTemplateRoutes from './modules/integratedTemplate/router';
import addressRoutes from './modules/address/router';
import acMyPageRoutes from './modules/myPage/router';
import ucMyPageRoutes from './modules/ucMyPage/router';
import integratedSendRoutes from './modules/integratedSend/router';
import smartTemplateRoutes from './modules/smartTemplate/router';
import useRoutes from './modules/use/router';
import smartSendRoutes from './modules/smartSend/router';
import customerRoutes from './modules/customer/router';

//import confirm from "@/modules/commonUtil/service/confirm.js";

Vue.use(Router)

const requireAuth = () => (to, from, next) => {
  if (tokenSvc.getToken().principal.svcTypeCd == 'BO' && tokenSvc.getToken().principal.role !== 'GUEST') {
    alert("Back Ofiice 계정으로는 Console 로그인이 불가합니다.");
    //confirm.fnAlert("", "Back Ofiice 계정으로는 Console 로그인이 불가합니다.");
    return next('/login');
  }
  next();

  if (tokenSvc.getToken().principal.svcTypeCd == 'UC') {
    alert("User 권한 사용자는 Admin Console 사용이 불가합니다.");
    return next('/uc');
  }
  next();
};

const requireAuthLogin = () => (to, from, next) => {
  if (tokenSvc.getToken() !== null) {
    if (tokenSvc.getToken().principal.svcTypeCd == 'UC') {
      return next('/uc');
    } else if (tokenSvc.getToken().principal.svcTypeCd == 'AC') {
      return next('/ac');
    }
  }
  next();
};

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      redirect: '/public/main'
    },
    {
      path: '/public',
      component: PublicLayout,
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
        ...mainRoutes,
        ...customerRoutes
      ]
    },
    {
      path: '/login',
      component: PublicLayout,
      beforeEnter: requireAuthLogin(),
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
      path: '/ac',
      component: WebNaviLayout,
      beforeEnter: requireAuth(),
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
        ...acHomeRoutes,
        ...listRoutes,
        ...channelRoutes,
        ...projectRoutes,
        ...cashRoutes,
        ...userRoutes,
        ...addressRoutes,
        ...acMyPageRoutes,
        ...useRoutes
      ],
    },
    {
      path: '/uc',
      component: WebUcNaviLayout,
      //beforeEnter: requireAuth(),
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
        ...ucHomeRoutes,
        ...messageRoutes,
        ...templateRoutes,
        ...messageStatusRoutes,
        ...integratedTemplateRoutes,
        ...ucMyPageRoutes,
        ...integratedSendRoutes,
        ...smartTemplateRoutes,
        ...smartSendRoutes
      ]
    },
    {path: '*', redirect: '/view/error/404'}
  ]
});

router.beforeEach((to, from, next) => {
  const isPublic = to.matched.some(record => record.meta.public);
  const loggedIn = !!tokenSvc.getToken();

  if (!isPublic && !loggedIn) {
    return next('/login');
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
var timeout = 1500;
router.afterEach((to, from) => {
  jQuery('#M_menusCd').val(to.name);
  if (to.name == 'home') {
    timeout = 1500;
  }
  Vue.nextTick(() => {
    setTimeout(function() {
      jQuery('#M_menusCd').val(to.name);
      var menu = jQuery('#M_'+to.name);
      var isRead = menu.attr('r');
      var isSave = menu.attr('w');
      isRead = isRead == 'true';
      isSave = isSave == 'true';
      console.log('MENU CODE : ' + to.name + ', READ:' + isRead + ', SAVE:' + isSave);
      if (isRead) {
        jQuery("[role='READ']").removeAttr('role');
      } else {
        jQuery("[role='READ']").remove();
      }
      if (isSave) {
        jQuery("[role='SAVE']").removeAttr('role');
      } else {
        jQuery("[role='SAVE']").remove();
      }
    }, timeout);
    timeout = 0;
  })
});

export default router;
