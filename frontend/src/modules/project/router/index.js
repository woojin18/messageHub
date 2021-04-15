import projectManage from '../views/bm-project-manage'

import callbackManage from '../views/bm-project-callback-manage'

import callbackPreReg from '../views/bm-project-callback-preReg'
import callbackPreRegEx from '../views/bm-project-callback-preRegEx'

export default [
    {
        path: '/project/manage',
        name: 'projectManage',
        component: projectManage
    },
    {
        path: '/project/callbackManage',
        name: 'callbackManage',
        component: callbackManage
    },
    {
        path: '/project/callbackPreReg',
        name: 'callbackPreReg',
        component: callbackPreReg
    },
    {
        path: '/project/callbackPreRegEx',
        name: 'callbackPreRegEx',
        component: callbackPreRegEx
    }
]