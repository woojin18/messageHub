import statisticsSendByDay from '../views/bm-statistics-sendByDay'
import statisticsSendByMonth from '../views/bm-statistics-sendByMonth'

export default [
	{
		path: '/uc/statistics/sendByDay',
		name: 'sendByDay',
		meta: { menu: 'UC_DAY_CH_STATIS' },
		component: statisticsSendByDay
	},
	{
		path: '/uc/statistics/sendByMonth',
		name: 'sendByMonth',
		meta: { menu: 'UC_MONTH_CH_STATIS' },
		component: statisticsSendByMonth
	},
]