import httpClient from '@/common/http-client';

const logout = () => {
	return httpClient.get('/api/auth/logout', {headers: {"show-layer": "Yes"}});
};

export default {
	logout
};