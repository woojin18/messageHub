import integratedTemplate from '../views/bm-integrated-template'
import integratedTemplateManage from '../views/bm-integrated-template-manage'

export default [
    {
        path: '/uc/integratedTemplateManage',
        name: 'integratedTemplateManage',
        component: integratedTemplateManage,
        props: true
    },
    {
        path: '/uc/integratedTemplate',
        name: 'integratedTemplate',
        meta: {menu: 'UC_INTG_TPL'},
        component: integratedTemplate
    }
]