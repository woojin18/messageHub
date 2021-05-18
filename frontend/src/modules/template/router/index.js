import PushTemplateManage from '../views/bm-pushTemplateManage.vue'
import PushTemplateList from '../views/bm-pushTemplateList.vue'
import SmsTemplateManage from '../views/bm-smsTemplateManage.vue'
import SmsTemplateList from '../views/bm-smsTemplateList.vue'
import FrndTalkTemplateManage from '../views/bm-frndTalkTemplateManage.vue'
import FrndTalkTemplateList from '../views/bm-frndTalkTemplateList.vue'
import RcsTemplateList from '../views/bm-rcsTemplateList.vue'
import RcsTemplateMod from '../views/bm-rcsTemplateMod.vue'
import AlimTalkTemplateManage from '../views/bm-alimTalkTemplateManage.vue'
import AlimTalkTemplateList from '../views/bm-alimTalkTemplateList.vue'

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
        component: PushTemplateList,
        props: true
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
        component: SmsTemplateList,
        props: true
    },
    {
        path: '/uc/template/frndTalkTemplateManage',
        name: 'frndTalkTemplateManage',
        component: FrndTalkTemplateManage,
        props: true
    },
    {
        path: '/uc/template/frndTalkTemplateList',
        name: 'frndTalkTemplateList',
        component: FrndTalkTemplateList,
        props: true
    },
    {
        path: '/uc/template/rcsTemplateList',
        name: 'rcsTemplateList',
        component: RcsTemplateList
    },
    {
        path: '/uc/template/rcsTemplateMod',
        name: 'rcsTemplateMod',
        component: RcsTemplateMod,
        props: true
    },
    {
        path: '/uc/template/alimTalkTemplateList',
        name: 'alimTalkTemplateList',
        component: AlimTalkTemplateList,
        props: true
    },
    {
        path: '/uc/template/alimTalkTemplateManage',
        name: 'alimTalkTemplateManage',
        component: AlimTalkTemplateManage,
        props: true
    }
]