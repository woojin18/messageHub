const { VUE_APP_TEST_PROP, NODE_ENV = '' } = process.env;

const environment = NODE_ENV.toLowerCase();
const testProp = VUE_APP_TEST_PROP;

const consts = {
	tokenPart1: 'JwtPart1',
	projectId: 'project'
}

export { environment, testProp, consts };
