import signUp from '../views/signUp'
import mailSend from '../views/signUpMailSend'
import signUpMain from '../views/signUpMain'

export default [
    {
        path: '/signUp',
        name: 'signUp',
        component: signUp
    },
    {
        path: '/mailSend',
        name: 'mailSend',
        component: mailSend 
    },
    {
        path: '/signUpMain',
        name: 'signUpMain',
        component: signUpMain
    },
]