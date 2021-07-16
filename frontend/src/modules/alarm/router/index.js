import alarm from '../views/bm-alarm'
import receptGroup from '../views/bm-receptGroup'
import recipient from '../views/bm-recipient'
export default [
	{
		path: '/ac/alarm/alarm',
		name: 'alarm',
		meta: { menu: 'AC_AL_ALARM'},
		component: alarm
	},
	{
		path: '/ac/alarm/receptGroup',
		name: 'receptGroup',
		meta: { menu: 'AC_AL_RECEPT_GRP'},
		component: receptGroup
	},
	{
		path: '/ac/alarm/recipient',
		name: 'recipient',
		meta: { menu: 'AC_AL_RECIPIENT'},
		component: recipient
	}
]