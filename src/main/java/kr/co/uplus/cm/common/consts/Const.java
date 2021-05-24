package kr.co.uplus.cm.common.consts;

import java.util.Arrays;
import java.util.List;

public class Const {
    public static final String COMM_YES = "Y";
    public static final String COMM_NO = "N";
    public static final String OTHER_PROJECT_USE_ID = "ALL";

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
	public static final Long SEND_RSRV_LIMIT_DAY = 30L; // 발송예약제한일

	// 템플릿 ID 첨두
	public static final String TMPLT_PREFIX = "TPL";

    // 이미지업로드 ID 첨두
    public static final String FILE_UPLOAD_PREFIX = "FLE";
	
	//Controller 파라메터 점검(배열값에 입력불가능한 필드 기록)
	public final static String[] DISALLOWED_FIELDS = new String[]{"details.role","is_admin"};

    // 채널
    public static class Ch {
        public static final String PUSH = "PUSH";
        public static final String SMS = "SMS";
        public static final String MMS = "MMS";
        public static final String FRIENDTALK = "FRIENDTALK";
        public static final String ALIMTALK = "ALIMTALK";
    }

    // 메시지발송 ID 접두(WEB_REQ_ID)
    public static class WebReqIdPrefix {
        public static final String PUSH_PREFIX = "PSH";
        public static final String SMS_PREFIX = "SMS";
        public static final String MMS_PREFIX = "MMS";
        public static final String FRND_TALK_PREFIX = "FRD";
    }

    //메시지구분
    public static class MsgKind {
        public static final String AD = "A";  //광고성
        public static final String INFO = "I";  //정보성
    }

    //메시지타입
    public static class MsgType {
        public static final String IMAGE = "IMAGE";  //이미지
        public static final String BASE = "BASE";  //기본
    }

    //대체발송타입
    public static class RplcSendType {
        public static final String NONE = "NONE";  //미사용
        public static final String SMS = "SMS";  //SMS
        public static final String LMS = "LMS";  //LMS
        public static final String MMS = "MMS";  //MMS
    }

    //대체발송타입
    public static class MsgCh {
        public static final String PUSH = "PUSH";  //PUSH
    }

    //발송타입
    public static class SenderType {
        public static final String SMART = "S";
        public static final String MERGER = "M";
        public static final String CHANNEL = "C";
    }

    //발송진행상태
    public static class MsgSendStatus {
        public static final String SEND_WAIT = "SEND_WAIT";
        public static final String RES_WAIT = "RES_WAIT";
        public static final String REPORT_WAIT = "REPORT_WAIT";
        public static final String COMPLETED = "COMPLETED";
        public static final String FAIL = "FAIL";
    }

    //파일 업로드 설정
    public static class FileUploadSet {
        public static final String SEND_IMAGE = "SEND_IMAGE";
        public static final String EXCEL = "EXCEL";
    }

    //파일 업로드 설정 키
    public static class FileUploadSetKey {
        public static final String PERMIT_EXTEN = "permitExten";
        public static final String RLT_TMP_PATH = "rltTmpPath";
    }

    //이미지 리사이징 사이즈
    public static final String IMG_RESIZE_WIDTH = "width";
    public static final String IMG_RESIZE_HEIGHT = "height";

    //파일 업로드 설정 키
    public static class ApprovalStatus {
        public static final String APPROVE = "승인";
        public static final String WAIT = "승인대기";
        public static final String REJECT = "반려";
    }

    //메시지 발송 오류 설정
    public static class SendMsgErrorSet {
        public static final String ERROR_PREFIX = "ERR";
        public static final String GW_RESULT_CODE = "ERROR";
        public static final String GW_RESULT_DESC = "3회재시도 에러";
    }

    //카카오 발신프로필키 타입
    public static class KkoSenderKeyType {
        public static final String NOMAL = "NOMAL";
        public static final String GROUP = "GROUP";
    }

    // 카카오 서비스 코드
    public enum KkoSvcUseCode {
        ALIMTALK("ALIMTALK_USE_YN"),
        FRIENDTALK("FRIENDTALK_USE_YN");

        private String code;

        KkoSvcUseCode(String code) {
            this.code = code;
        }

        public static String getType(String name) {
            String rtnType = "";
            for (KkoSvcUseCode e : KkoSvcUseCode.values()) {
                if(e.name().equals(name)) {
                    rtnType = e.code;
                    break;
                }
            }
            return rtnType;
        }
    }

    // 메세지 상품 타입
    public enum MsgProductCode {
        PUSH("PUSH"),
        SMS("SMS"),
        LMS( "LMS"),
        MMS("MMS"),
        FRENDTALK_TEXT("FRIENDTALK_01"),
        FRENDTALK_IMAGE("FRIENDTALK_02"),
        FRENDTALK_WIDE("FRIENDTALK_03"),
        ALIM_TALK("ALIMTALK");

        private String code;

        MsgProductCode(String code) {
            this.code = code;
        }

        public static String getType(String name) {
            String rtnType = "";
            for (MsgProductCode e : MsgProductCode.values()) {
                if(e.name().equals(name)) {
                    rtnType = e.code;
                    break;
                }
            }
            return rtnType;
        }
    }


}
