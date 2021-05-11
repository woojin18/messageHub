import PushTemplateManage from '../views/bm-pushTemplateManage.vue'
import PushTemplateList from '../views/bm-pushTemplateList.vue'
import SmsTemplateManage from '../views/bm-smsTemplateManage.vue'
import SmsTemplateList from '../views/bm-smsTemplateList.vue'
import FrndTalkTemplateManage from '../views/bm-frndTalkTemplateManage.vue'

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
    },
    {
        path: '/uc/template/smsTemplateManage',
        name: 'smsTemplateManage',
        component: SmsTemplateManage,
        props: true
    },
    {
        path: '/uc/template/smsTemplateList',
        name: 'smsTemplateList',
        component: SmsTemplateList
    },
    {
        path: '/uc/template/frndTalkTemplateManage',
        name: 'frndTalkTemplateManage',
        component: FrndTalkTemplateManage,
        props: true
    }
]