import statisticsUserSendByDay from '../views/bm-statisticsUser-sendByDay'
import statisticsUserSendByMonth from '../views/bm-statisticsUser-sendByMonth'
import statisticsUserSendByMain from '../views/bm-statisticsUser-sendStatisMain'

export default [
	{
		path: '/uc/statisticsUser/sendByDay',
		name: 'sendByDay',
		meta: { menu: 'UC_DAY_CH_STATIS' },
		component: statisticsUserSendByDay
	},
	{
		path: '/uc/statisticsUser/sendByMonth',
		name: 'sendByMonth',
		meta: { menu: 'UC_MONTH_CH_STATIS' },
		component: statisticsUserSendByMonth
	},
	{
		path: '/uc/statisticsUser/sendStatisMain',
		name: 'sendByMonth',
		meta: { menu: 'UC_MAIN_CH_STATIS' },
		component: statisticsUserSendByMain
	},
]