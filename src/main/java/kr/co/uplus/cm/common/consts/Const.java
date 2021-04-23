package kr.co.uplus.cm.common.consts;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Const {
    public static final String COMM_YES = "Y";
    public static final String COMM_NO = "N";

	public static final String KEY_MDC_LOG = "logKey";
	public static final String KEY_LOG_OBJ = "logObj";
	public static final String KEY_LOAD_USER = "loadUser"; // UserDetailsService에서 로딩하는 사용자정보
	public static final String KEY_TOKEN_EXIST = "jwtTokenExist";

	public static final List<String> NOT_LOG_PARAMS = Arrays.asList("userPwd", "curPwd", "newPwd", "cfmPwd");

	public static final int SESSION_EXPIRED = 418;

	// 정책
	public static final int PWD_EXPIRE_DAYS = 90; // 비밀번호 만료기간 (일)
	public static final int MAX_LOGIN_FAIL = 5; // 5회 로그인 실패하면 계정 잠김
	public static final int AUTHNUM_EXPIRE_MINS = 3; // 인증번호 만료기간 (분)
	public static final int MAX_AUTHNUM_FAIL = 5; // 5회 인증번호 검증 실패하면 발행 잠김
	public static final int AUTHNUM_LOCK_EXPIRE_MINS = 30; // 인증번호발행 잠김 만료기간 (분)

	// 템플릿 ID 첨두
	public static final String TMPLT_PREFIX = "TPL";

    // 이미지업로드 ID 첨두
    public static final String FILE_UPLOAD_PREFIX = "FLE";

    //이미지 리사이징 사이즈
    public static final String IMG_RESIZE_WIDTH = "width";
    public static final String IMG_RESIZE_HEIGHT = "height";


    //API
    public static final String API_SUCCESS = "10000";  //API 성공 코드(코드정보를 받으면 enum에 등록)
    public static final String FILE_UPLOAD_API_URL = "/console/v1/file/";  //파일 업로드 API

}
