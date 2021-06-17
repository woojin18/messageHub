import signUp from '../views/signUp'
import mailSend from '../views/signUpMailSend'
import signUpMain from '../views/signUpMain'
import checkPlusSuccess from '../views/bm-checkPlus-success'
import checkPlusFail from '../views/bm-checkPlus-fail'

export default [
    {
        path: '/signUp',
        name: 'signUp',
        component: signUp,
        meta: { public: true }
    },
    {
        path: '/mailSend',
        name: 'mailSend',
        component: mailSend,
        meta: { public: true }
    },
    {
        path: '/signUpMain',
        name: 'signUpMain',
        component: signUpMain,
        meta: { public: true }
    },
    {
        path: '/checkPlusSuccess',
        name: 'checkPlusSuccess',
        component: checkPlusSuccess,
        meta: { public: true }
    },
    {
        path: '/checkPlusFail',
        name: 'checkPlusFail',
        component: checkPlusFail,
        meta: { public: true }
    },
]