import smartTemplate from '../views/bm-smart-template'
import smartTemplateManage from '../views/bm-smart-template-manage'

export default [
    {
        path: '/uc/smartTemplateManage',
        name: 'smartTemplateManage',
        component: smartTemplateManage,
        props: true
    },
    {
        path: '/uc/smartTemplate',
        name: 'smartTemplate',
        meta: {menu: 'UC_SMRT_TPL'},
        component: smartTemplate
    }
]