import * as utils from './utils';
import { consts } from './config';

const tokenSvc = {
    getToken() {
        var jwtPart1 = utils.getCookie(consts.tokenPart1);
        if (!jwtPart1)
            return null;
        var payload = utils.base64decode(jwtPart1.split('.').pop());
        return JSON.parse(payload);
    }
};

export default tokenSvc;
