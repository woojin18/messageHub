import projectManage from '../views/bm-project-manage'

import projectMain from '../views/bm-project-main'

import callbackManage from '../views/bm-project-callback-manage'

import callbackReg from '../views/bm-project-callback-reg'
import callbackPreReg from '../views/bm-project-callback-preReg'
import callbackPreRegEx from '../views/bm-project-callback-preRegEx'

import baseInfoManage from '../views/bm-project-baseInfo-manage'
import memberManage from '../views/bm-project-member-manage'

export default [
    {
        path: '/ac/project/manage',
        name: 'projectManage',
        meta: { menu: 'AC_PROJECT' },
        component: projectManage
    },
    {
        path: '/ac/project/main',
        name: 'projectMain',
        meta: { menu: 'AC_PROJECT' },
        component: projectMain
    },
    {
        path: '/ac/project/callbackManage',
        name: 'callbackManage',
        meta: { menu: 'AC_PROJECT' },
        component: callbackManage
    },
    {
        path: '/ac/project/callbackReg',
        name: 'callbackReg',
        meta: { menu: 'AC_PROJECT' },
        component: callbackReg
    },
    {
        path: '/ac/project/callbackPreReg',
        name: 'callbackPreReg',
        meta: { menu: 'AC_PROJECT' },
        component: callbackPreReg
    },
    {
        path: '/ac/project/callbackPreRegEx',
        name: 'callbackPreRegEx',
        meta: { menu: 'AC_PROJECT' },
        component: callbackPreRegEx
    },
    {
        path: '/ac/project/baseInfoManage',
        name: 'baseInfoManage',
        meta: { menu: 'AC_PROJECT' },
        component: baseInfoManage
    },
    {
        path: '/ac/project/memberManage',
        name: 'memberManage',
        meta: { menu: 'AC_PROJECT' },
        component: memberManage
    },
]