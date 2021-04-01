import PushTemplateManage from '../views/bm-pushTemplateManage.vue'
import PushTemplateList from '../views/bm-pushTemplateList.vue'


export default [
    {
        path: '/template/pushTemplateManage',
        name: 'pushTemplateManage',
        component: PushTemplateManage,
        props: true
    },
    {
        path: '/template/pushTemplateList',
        name: 'pushTemplateList',
        component: PushTemplateList
    }
]