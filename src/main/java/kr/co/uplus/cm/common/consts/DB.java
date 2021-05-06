package kr.co.uplus.cm.common.consts;

public class DB {

	//공통
    public final static String QRY_INSERT_IMAGE_FILE = "common.insertImageFile";
	public final static String QRY_SELECT_IMAGE_LIST_CNT = "common.selectImageListCnt";
	public final static String QRY_SELECT_IMAGE_LIST = "common.selectImageList";
	public final static String QRY_DELETE_IMAGE = "common.deleteImage";
	public final static String QRY_SELECT_CORP_INFO = "common.selectCorpInfoByUserId";
    public final static String QRY_SELECT_FILE_ID_USE_CNT = "common.selectFileIdUseCnt";
    public final static String QRY_SELECT_API_KEY = "common.selectApiKey";
    public final static String QRY_SELECT_IMG_UPLOAD_CH_SET = "common.selectImgUploadChSet";
    public final static String QRY_SELECT_FILE_UPLOAD_SET = "common.selectFileUploadSet";

	// 로그인
	public final static String QRY_SELECT_USERNAME	= "login.qrySelectUserName";
	public final static String QRY_INSERT_USERNAME	= "login.qryInsertAuthUser";
	// 푸시 템플릿
	public final static String QRY_SELECT_PUSH_TMPLT_LIST_CNT = "template.selectPushTemplateListCnt";
	public final static String QRY_SELECT_PUSH_TMPLT_LIST = "template.selectPushTemplateList";
	public final static String QRY_INSERT_PUSH_TMPLT = "template.insertPushTemplate";
	public final static String QRY_UPDATE_PUSH_TMPLT = "template.updatePushTemplate";
	public final static String QRY_DELETE_PUSH_TMPLT = "template.deletePushTemplate";
	// RCS 템플릿
	public final static String QRY_SELECT_RCS_TMPLT = "template.selectRcsTemplate";
	// 알림톡 템플릿
	public final static String QRY_SELECT_KAKAOTALK_TMPLT = "template.selectKakaoTalkTemplate";
	// 친구톡 템플릿
	public final static String QRY_SELECT_FRIENDTALK_TMPLT = "template.selectFriendTalkTemplate";
	public final static String QRY_INSERT_FRIENDTALK_TMPLT = "template.insertFriendTalkTemplate";
	public final static String QRY_UPDATE_FRIENDTALK_TMPLT = "template.updateFriendTalkTemplate";
	// SMS/MMS 템플릿
	public final static String QRY_SELECT_SMS_TMPLT_LIST_CNT = "template.selectSmsTmpltListCnt";
	public final static String QRY_SELECT_SMS_TMPLT_LIST = "template.selectSmsTmpltList";
	public final static String QRY_INSERT_SMS_TMPLT = "template.insertSmsTemplate";
	public final static String QRY_UPDATE_SMS_TMPLT = "template.updateSmsTmplt";
	public final static String QRY_DELETE_SMS_TMPLT = "template.deleteSmsTmplt";
	// 통합발송 템플릿
	public final static String QRY_SELECT_MULTISEND_TMPLT = "template.selectMultiSendTemplate";
	public final static String QRY_INSERT_MULTISEND_TMPLT = "template.insertMultiSendTemplate";
	public final static String QRY_UPDATE_MULTISEND_TMPLT = "template.updateMultiSendTemplate";
	// 스마트발송 템플릿
	public final static String QRY_SELECT_SMART_TMPLT = "template.selectSmartTemplate";
	public final static String QRY_INSERT_SMART_TMPLT = "template.insertSmartTemplate";
	public final static String QRY_UPDATE_SMART_TMPLT = "template.updateSmartTemplate";
	// 푸시메시지 발송
    public final static String QRY_SELECT_APP_ID_LIST = "sendMessage.selectAppIdList";
    public final static String QRY_SELECT_CALLBACK_LIST = "sendMessage.selectCallbackList";
    public final static String QRY_SELECT_ADDR_GRP_LIST = "sendMessage.selectAddrGrpList";
    public final static String QRY_SELECT_ADDR_CTGY_LIST = "sendMessage.selectAddrCtgyList";
    public final static String QRY_SELECT_CM_CU_LIST = "sendMessage.selectCmCuList";
    public final static String QRY_SELECT_CM_CU_LIST_CNT = "sendMessage.selectCmCuListCnt";
    public final static String QRY_SELECT_ALL_ADDR_CU_LIST = "sendMessage.selectAllAddrCuList";
    public final static String QRY_SELECT_PAY_TYPE = "sendMessage.selectPayType";
    public final static String QRY_SELECT_MSG_FEE_PER_ONE = "sendMessage.selectMsgFeePerOne";
    public final static String QRY_INSERT_CM_WEB_MSG = "sendMessage.insertCmWebMsg";
	// RCS 채널 브랜드 정보
    public final static String QRY_SELECT_RCS_BRANDLIST_CNT = "channel.selectRcsBrandListCnt";
	public final static String QRY_SELECT_RCS_BRANDLIST = "channel.selectRcsBrandList";
	// RCS 등록 템플릿 조회
	public final static String QRY_SELECT_RCS_REGTMPLTLIST = "channel.selectRcsRegTmpltList";
	// RCS 채널 브랜드
	public final static String QRY_INSERT_RCS_BRANDREQ = "channel.insertRcsReqTemp";
	public final static String QRY_UPDATE_RCS_BRANDREQ = "channel.updateRcsReqTemp";
	public final static String QRY_DELETE_RCS_TEMP_BRANDREQ  = "channel.deleteRcsReqTemp";
	public final static String QRY_INSERT_RCS_CHATBOTREQ = "channel.insertRcsChatbotReqTemp";
	public final static String QRY_DELETE_RCS_TEMP_CHATBOTREQ = "channel.deleteRcsChatbotReqTemp";
	// RCS 발신번호 상세
	public final static String QRY_SELECT_RCS_CALLBACKLIST = "channel.selectRcsCallbackList";
	//웹캐시
	public final static String QRY_INSERT_WEB_CASH_INFO = "cash.insertWebCashInfo";
	public final static String QRY_SELECT_WEB_CASH_INFO = "cash.selectWebCashInfo";
	public final static String QRY_UPDATE_WEB_CASH_INFO = "cash.updateWebCashInfo";
	public final static String QRY_SELECT_CASH_HIST_COUNT = "cash.selectCashHist_count";
	public final static String QRY_SELECT_CASH_HIST = "cash.selectCashHist";
	public final static String QRY_SELECT_UCUBE_INFO = "cash.selectUcubeInfo";
	public final static String QRY_SELECT_PROJECT_INFO = "cash.selectProjectInfo";
	public final static String QRY_SELECT_PROJECT_INFO_COUNT = "cash.selectProjectInfoCnt";
	public final static String QRY_SELECT_PROJECT_SUB_BILL_CODE_COUNT = "cash.selectProjectSubBillCodeCnt";
	public final static String QRY_SELECT_PROJECT_SUB_BILL_CODE = "cash.selectProjectSubBillCode";
	public final static String QRY_INSERT_UCUBE_INFO = "cash.insertUcubeInfo";
	public final static String QRY_UPDATE_PROJECT_BILL_ID = "cash.updateProjectBillId";
	public final static String QRY_INSERT_PROJECT_SUB_BILL_CODE = "cash.insertProjectSubBillCode";
	public final static String QRY_UPDATE_PROJECT_SUB_BILL_CODE = "cash.updateProjectSubBillCode";
	public final static String QRY_DELETE_PROJECT_SUB_BILL_CODE = "cash.deleteProjectSubBillCode";
	// 회원 가입
	public final static String QRY_SELECT_DOMAIN_CHK_COUNT = "signUp.selectDomainChkCount";
	public final static String QRY_INSERT_CM_CORP = "signUp.insertCmCorp";
	public final static String QRY_INSERT_CM_USER = "signUp.insertCmUser";
	public final static String QRY_UPDATE_CM_USER = "signUp.updateCmUser";
	public final static String QRY_SELECT_CM_USE_TERMS = "signUp.selectCmUseTerms";
	public final static String QRY_SELECT_CM_USE_TERMS_PRI_VERSION = "signUp.selectCmUseTermsPriVersion";
	// 사용자 등록
	public final static String QRY_SELECT_USER_LIST = "user.selectUserList";
	public final static String QRY_SELECT_USER_LIST_CNT = "user.selectUserListCnt";
	public final static String QRY_UPDATE_STOP_USER = "user.stopUser";
	public final static String QRY_UPDATE_RELEASE_USER = "user.releaseUser";
	public final static String QRY_DELETE_USER = "user.deleteUser";
	public final static String QRY_UPDATE_USER = "user.updateUser";
	public final static String QRY_INSERT_USER = "user.insertUser";
	public final static String QRY_SELECT_USER_DUPC_CNT = "user.selectUserDupcCnt";
	// 프로젝트 발신번호 관리
	public final static String QRY_SELECT_CALLBACK_MANAGE_LIST_CNT = "project.selectCallbackManageListCnt";
	public final static String QRY_SELECT_CALLBACK_MANAGE_LIST = "project.selectCallbackManageList";
	public final static String QRY_SELECT_CALLBACK_MANAGE_DETAIL = "project.selectCallbackManageDetail";
}
