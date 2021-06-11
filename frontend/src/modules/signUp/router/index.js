import signUp from '../views/signUp'
import mailSend from '../views/signUpMailSend'
import signUpMain from '../views/signUpMain'

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
]