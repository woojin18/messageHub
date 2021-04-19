import PushTemplateManage from '../views/bm-pushTemplateManage.vue'
import PushTemplateList from '../views/bm-pushTemplateList.vue'


export default [
    {
        path: '/uc/template/pushTemplateManage',
        name: 'pushTemplateManage',
        component: PushTemplateManage,
        props: true
    },
    {
        path: '/uc/template/pushTemplateList',
        name: 'pushTemplateList',
        component: PushTemplateList
    }
]