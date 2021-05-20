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
    public final static String QRY_SELECT_CODE = "common.selectCodeList";

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
	public final static String QRY_INSERT_FRND_TALK_TEMPLATE = "template.insertFrndTalkTemplate";
	public final static String QRY_SELECT_FRND_TALK_TMPLT_LIST_CNT = "template.selectFrndTalkTmpltListCnt";
	public final static String QRY_SELECT_FRND_TALK_TMPLT_LIST = "template.selectFrndTalkTmpltList";
	public final static String QRY_UPDATE_FRND_TALK_TMPLT = "template.updateFrndTalkTmplt";
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
    public final static String QRY_SELECT_RE_SEND_CD_LIST = "sendMessage.selectReSendCdList";
    public final static String QRY_INSERT_CM_MSG = "sendMessage.insertCmMsg";
	// RCS 채널 브랜드 정보
    public final static String QRY_SELECT_RCS_BRANDLIST_CNT = "channel.selectRcsBrandListCnt";
	public final static String QRY_SELECT_RCS_BRANDLIST = "channel.selectRcsBrandList";
	public final static String QRY_SELECT_RCS_BRAND_MSGBASE_LIST = "channel.selectRcsBrandMsgBaseList";
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
	// 채널관리
	public final static String QRY_SELECT_PUSH_MANAGE_LIST = "channel.selectPushManageList";
	public final static String QRY_SELECT_PUSH_MANAGE_LIST_CNT = "channel.selectPushManageListCnt";

	public final static String QRY_INSERT_PUSH_MANAGE = "channel.insertPushManage";
	public final static String QRY_UPDATE_PUSH_MANAGE = "channel.updatePushManage";
	public final static String QRY_DELETE_PUSH_MANAGE = "channel.deletePushManage";

	public final static String QRY_SELECT_MO_CALLBACK_LIST_CNT = "channel.selectMoCallbackListCnt";
	public final static String QRY_SELECT_MO_CALLBACK_LIST = "channel.selectMoCallbackList";
	public final static String QRY_INSERT_MO_CALLBACK = "channel.insertMoCallback";
	public final static String QRY_UPDATE_MO_CALLBACK = "channel.updateMoCallback";
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
	
	// RCS 템플릿 관리
	public final static String QRY_SELECT_RCS_TEMPLATE_LIST = "rcsTemplate.selectRcsTemplate";
	public final static String QRY_SELECT_RCS_TEMPLATE_LIST_CNT = "rcsTemplate.selectRcsTemplateCnt";
	public final static String QRY_SELECT_CORP_BRAND_CNT = "rcsTemplate.selectCorpBrandCnt";
	// 주소록 관리
	public final static String QRY_SELECT_ADDRESS_CATE_GRP_LIST = "address.selectAddressCateGrpList";
	public final static String QRY_SELECT_ADDRESS_GRP = "address.selectAddressGrp";
	public final static String QRY_SELECT_ADDRESS_CATE_LIST = "address.selectAddressCateList";
	public final static String QRY_SELECT_ADDRESS_MEM_LIST = "address.selectAddressMemList";
	public final static String QRY_INSERT_ADDR_CATE_GRP = "address.insertAddressCateGrp";
	public final static String QRY_UPDATE_ADDR_CATE_GRP = "address.updateAddressCateGrp";
	public final static String QRY_SELECT_PROJECT_LIST = "address.selectProjectList";

	// 마이페이지
	// 회원정보
	public final static String QRY_SELECT_MEMBER_INFO		= "myPage.selectMemberInfo";
	public final static String QRY_UPDATE_MEMBER_INFO		= "myPage.updateMemberInfo";
	// 나의 문의 내역서
	public final static String QRY_SELECT_QNA_LIST_COUNT	= "myPage.selectQnaList_count";
	public final static String QRY_SELECT_QNA_LIST			= "myPage.selectQnaList";
}
