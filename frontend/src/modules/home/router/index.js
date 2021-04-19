import HomeMain from '../views/HomeMain'
import UcHomeMain from '../views/UcHomeMain'

export default [
	{
		path: '/',
		name: 'acHome',
		component: HomeMain
	},
	{
		path: '/ac',
		name: 'acHome',
		component: HomeMain
	},
	{
		path: '/uc',
		name: 'ucHome',
		component: UcHomeMain
	},
	{
		path: '/ac/home',
		name: 'acHome',
		component: HomeMain
	},
	{
		path: '/uc/home',
		name: 'ucHome',
		component: UcHomeMain
	}
]