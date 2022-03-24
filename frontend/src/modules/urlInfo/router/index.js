import urlInfoStatList from '../views/urlInfoStatList.vue'
import urlInfoStatDetail from '../views/urlInfoStatDetail.vue'

export default [
  {
    path: '/uc/urlInfo/urlInfoStatList',
    name: 'urlInfoStatList',
    meta: { menu: 'UC_LAB_URL' },
    component: urlInfoStatList,
  },
  {
    path: '/uc/urlInfo/urlInfoStatList/:urlId',
    name: 'urlInfoStatDetail',
    meta: { menu: 'UC_LAB_URL' },
    component: urlInfoStatDetail,
    props: true
  },
]