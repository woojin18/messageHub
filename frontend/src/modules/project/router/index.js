import projectManage from '../views/bm-project-manage'

import callbackManage from '../views/bm-project-callback-manage'

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
    }
]