import faq from '../views/bm-faq.vue'
import notice from '../views/bm-notice.vue'
import noticeDetail from '../views/bm-noticeDetail.vue'
import library from '../views/bm-library.vue'
import libraryDetail from '../views/bm-libraryDetail.vue'

export default [
  {
    path: '/public/faq',
    name: 'faq',
    component: faq,
    props: true,
    meta: { public: true }
  },
  {
    path: '/public/notice',
    name: 'notice',
    component: notice,
    props: true,
    meta: { public: true }
  },
  {
    path: '/public/noticeDetail',
    name: 'noticeDetail',
    component: noticeDetail,
    props: true,
    meta: { public: true }
  },
  {
    path: '/public/library',
    name: 'library',
    component: library,
    props: true,
    meta: { public: true }
  },
  {
    path: '/public/libraryDetail',
    name: 'libraryDetail',
    component: libraryDetail,
    props: true,
    meta: { public: true }
  }
]