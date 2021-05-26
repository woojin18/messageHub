import projectManage from '../views/bm-project-manage'

import projectMain from '../views/bm-project-main'

import callbackManage from '../views/bm-project-callback-manage'

import callbackReg from '../views/bm-project-callback-reg'
import callbackPreReg from '../views/bm-project-callback-preReg'
import callbackPreRegEx from '../views/bm-project-callback-preRegEx'

import baseInfoManage from '../views/bm-project-baseInfo-manage'
export default [
    {
        path: '/ac/project/manage',
        name: 'projectManage',
        component: projectManage
    },{
        path: '/ac/project/main',
        name: 'projectMain',
        component: projectMain
    },
    {
        path: '/ac/project/callbackManage',
        name: 'callbackManage',
        component: callbackManage
    },
    {
        path: '/ac/project/callbackReg',
        name: 'callbackReg',
        component: callbackReg
    },
    {
        path: '/ac/project/callbackPreReg',
        name: 'callbackPreReg',
        component: callbackPreReg
    },
    {
        path: '/ac/project/callbackPreRegEx',
        name: 'callbackPreRegEx',
        component: callbackPreRegEx
    },
    {
        path: '/ac/project/baseInfoManage',
        name: 'baseInfoManage',
        component: baseInfoManage
    },
]