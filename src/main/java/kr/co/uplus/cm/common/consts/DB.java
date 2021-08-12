package kr.co.uplus.cm.common.consts;

public class DB {

	// 공통
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
	public final static String QRY_SELECT_RELAY_SVC_ID = "common.selectRelaySvcId";
	public final static String QRY_SELECT_CODEVAL1_BY_CODENAME1 = "common.selectCodeVal1ByCodeName1";
	public final static String QRY_INSERT_FILE_INFO = "common.insertFileInfo";
	public final static String QRY_SELECT_USE_CH_GRP_INFO = "common.selectUseChGrpInfo";
	public final static String QRY_SELECT_IMAGE_URL_INFO = "common.selectImageUrlInfo";
	public final static String QRY_UPDATE_CM_CMD = "common.updateCmCmdForRedis";

	// 메인
	public final static String QRY_SELECT_PRD_UNIT_INFO_LIST = "main.selectPrdUnitInfoList";

	// 고객센터
	public final static String QRY_SELECT_FAQ_TYPE_LIST = "customer.selectFaqTypeList";
	public final static String QRY_SELECT_FAQ_LIST_CNT = "customer.selectFaqListCnt";
	public final static String QRY_SELECT_FAQ_LIST = "customer.selectFaqList";
	public final static String QRY_SELECT_NOTICE_LIST_CNT = "customer.selectNoticeListCnt";
	public final static String QRY_SELECT_NOTICE_LIST = "customer.selectNoticeList";
	public final static String QRY_SELECT_LIBRARY_LIST_CNT = "customer.selectLibraryListCnt";
	public final static String QRY_SELECT_LIBRARY_LIST = "customer.selectLibraryList";
	public final static String QRY_SELECT_ATTACH_FILE_INFO = "customer.selectAttachFileInfo";
	public final static String qry_SELECT_CM_USE_TERMS_HIST_LIST = "customer.selectCmUseTermsHistList";
	public final static String qry_SELECT_CM_USE_TERMS_INFO = "customer.selectCmUseTermsInfo";

	// 로그인
	public final static String QRY_SELECT_USERNAME = "login.qrySelectUserName";
	public final static String QRY_INSERT_USERNAME = "login.qryInsertAuthUser";
	public final static String QRY_UPDATE_USER_PASSWORD = "login.updatePassword";
	public final static String QRY_UPDATE_RECENT_CONN_DT = "login.updateRecentConnDt";
	public final static String QRY_UPDATE_LOGIN_FAIL_CNT = "login.updateLoginFailCnt";
	// 푸시 템플릿
	public final static String QRY_SELECT_PUSH_TMPLT_LIST_CNT = "template.selectPushTemplateListCnt";
	public final static String QRY_SELECT_PUSH_TMPLT_LIST = "template.selectPushTemplateList";
	public final static String QRY_INSERT_PUSH_TMPLT = "template.insertPushTemplate";
	public final static String QRY_UPDATE_PUSH_TMPLT = "template.updatePushTemplate";
	public final static String QRY_DELETE_PUSH_TMPLT = "template.deletePushTemplate";
	// RCS 템플릿
	public final static String QRY_SELECT_RCS_TMPLT = "template.selectRcsTemplate";
	// 알림톡 템플릿
	public final static String QRY_SELECT_KKO_TMPLT_CAT_GRP_LIST = "template.selectKkoTmpltCatGrpList";
	public final static String QRY_SELECT_KKO_TMPLT_CAT_LIST = "template.selectKkoTmpltCatList";
	public final static String QRY_SELECT_ALIM_TALK_TMPLT_LIST = "template.selectAlimTalkTmpltList";
	public final static String QRY_SELECT_ALIM_TALK_TMPLT_LIST_CNT = "template.selectAlimTalkTmpltListCnt";
	public final static String QRY_SELECT_KKO_TMPLT_REJ_RESN = "template.selectKkoTmpltRejResn";
	// 친구톡 템플릿
	public final static String QRY_INSERT_FRND_TALK_TEMPLATE = "template.insertFrndTalkTemplate";
	public final static String QRY_SELECT_FRND_TALK_TMPLT_LIST_CNT = "template.selectFrndTalkTmpltListCnt";
	public final static String QRY_SELECT_FRND_TALK_TMPLT_LIST = "template.selectFrndTalkTmpltList";
	public final static String QRY_UPDATE_FRND_TALK_TMPLT = "template.updateFrndTalkTmplt";
	public final static String QRY_DELETE_FRND_TALK_TMPLT = "template.deleteFrndTalkTmplt";
	// SMS/MMS 템플릿
	public final static String QRY_SELECT_SMS_TMPLT_LIST_CNT = "template.selectSmsTmpltListCnt";
	public final static String QRY_SELECT_SMS_TMPLT_LIST = "template.selectSmsTmpltList";
	public final static String QRY_INSERT_SMS_TMPLT = "template.insertSmsTemplate";
	public final static String QRY_UPDATE_SMS_TMPLT = "template.updateSmsTmplt";
	public final static String QRY_DELETE_SMS_TMPLT = "template.deleteSmsTmplt";
	// 통합발송 템플릿
	public final static String QRY_SELECT_MULTISEND_TMPLT_LIST_CNT = "template.selectMultiSendTemplateListCnt";
	public final static String QRY_SELECT_MULTISEND_TMPLT_LIST = "template.selectMultiSendTemplateList";
	public final static String QRY_SELECT_MULTISEND_TMPLT_DETAIL = "template.selectMultiSendTemplateDetail";
	public final static String QRY_INSERT_MULTISEND_TMPLT = "template.insertMultiSendTemplate";
	public final static String QRY_UPDATE_MULTISEND_TMPLT = "template.updateMultiSendTemplate";
	public final static String QRY_DELETE_MULTISEND_TMPLT = "template.deleteMultiSendTemplate";
	// 스마트발송 템플릿
	public final static String QRY_SELECT_SMART_TMPLT = "template.selectSmartTemplate";
	public final static String QRY_INSERT_SMART_TMPLT = "template.insertSmartTemplate";
	public final static String QRY_UPDATE_SMART_TMPLT = "template.updateSmartTemplate";
	// 메시지 발송
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
	public final static String QRY_SELECT_ALL_APP_USER_LIST = "sendMessage.selectAllAppUserList";
	public final static String QRY_SELECT_KKO_SENDER_KEY_LIST = "sendMessage.selectKkoSenderKeyList";
	public final static String QRY_SELECT_KKO_SENDER_GRP_KEY_LIST = "sendMessage.selectKkoSenderGrpKeyList";
	public final static String QRY_SELECT_SMART_TMPLT_INFO = "sendMessage.selectSmartTmpltInfo";
	public final static String QRY_SELECT_SMART_TMPLT_LIST_CNT = "sendMessage.selectSmartTmpltListCnt";
	public final static String QRY_SELECT_SMART_TMPLT_LIST = "sendMessage.selectSmartTmpltList";
	public final static String QRY_SELECT_SMART_TMPLT_FRND_PRDT_INFO = "sendMessage.selectSmartTmpltFrndPrdtInfo";
	public final static String QRY_SELECT_SMART_TMPLT_RCS_INFO = "sendMessage.selectSmartTmpltRcsInfo";

	// RCS 채널 브랜드 정보
	public final static String QRY_SELECT_RCS_BRANDLIST_CNT = "channel.selectRcsBrandListCnt";
	public final static String QRY_SELECT_RCS_BRANDLIST = "channel.selectRcsBrandList";
	public final static String QRY_SELECT_RCS_BRAND_MSGBASE_LIST = "channel.selectRcsBrandMsgBaseList";
	public final static String QRY_SELECT_RCS_BRAND_CHATBOT = "channel.selectRcsBrandChatbotList";
	public final static String QRY_SELECT_RCS_BRAND_LIST_CALLBACK_LIST = "channel.selectRcsBrandListCallbackList";
	public final static String QRY_SELECT_RCS_BRAND_LIST_CALLBACK_LIST_CNT = "channel.selectRcsBrandListCallbackListCount";
	// RCS 등록 템플릿 조회
	public final static String QRY_SELECT_RCS_REGTMPLTLIST = "channel.selectRcsRegTmpltList";
	public final static String QRY_SELECT_RCS_REGTMPLTLIST_CNT = "channel.selectRcsRegTmpltListCount";
	// RCS 채널 브랜드
	public final static String QRY_INSERT_RCS_BRANDREQ = "channel.insertRcsReqTemp";
	public final static String QRY_UPDATE_RCS_BRANDREQ = "channel.updateRcsReqTemp";
	public final static String QRY_DELETE_RCS_TEMP_BRANDREQ = "channel.deleteRcsReqTemp";
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
	public final static String QRY_SELECT_MO_CALLBACK_DUPL = "channel.selectMoCallbackDupl";
	public final static String QRY_INSERT_MO_CALLBACK = "channel.insertMoCallback";
	public final static String QRY_UPDATE_MO_CALLBACK = "channel.updateMoCallback";
	public final static String QRY_DELETE_MO_CALLBACK = "channel.deleteMoCallback";
	// 웹캐시
	public final static String QRY_INSERT_WEB_CASH_INFO = "cash.insertWebCashInfo";
	public final static String QRY_SELECT_WEB_CASH_INFO = "cash.selectWebCashInfo";
	public final static String QRY_UPDATE_WEB_CASH_INFO = "cash.updateWebCashInfo";
	public final static String QRY_SELECT_WEB_CASH_ID = "cash.selectCashId";
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
	public final static String QRY_CHK_MAIL_CERTIFY_BY_AUTHKEY = "signUp.checkMailCertifyByAuthKey";
	public final static String QRY_INSERT_MAIL_CERTIFY = "signUp.insertCmMailCertify";
	public final static String QRY_SELECT_EXISTS_CUSTNO = "signUp.selectIsExistCustNo";
	
	// 사용자 등록
	public final static String QRY_SELECT_USER_LIST = "user.selectUserList";
	public final static String QRY_SELECT_USER_LIST_CNT = "user.selectUserListCnt";
	public final static String QRY_UPDATE_STOP_USER = "user.stopUser";
	public final static String QRY_UPDATE_RELEASE_USER = "user.releaseUser";
	public final static String QRY_DELETE_USER = "user.deleteUser";
	public final static String QRY_UPDATE_USER = "user.updateUser";
	public final static String QRY_INSERT_USER = "user.insertUser";
	public final static String QRY_SELECT_USER_DUPC_CNT = "user.selectUserDupcCnt";
	public final static String QRY_CHK_PASSWORD = "user.checkPassword";
	public final static String QRY_UPDATE_USER_ROLE_ADMIN = "user.updateUserRoleAdmin";

	// 프로젝트 발신번호 관리
	public final static String QRY_SELECT_CALLBACK_MANAGE_LIST_CNT = "project.selectCallbackManageListCnt";
	public final static String QRY_SELECT_CALLBACK_MANAGE_LIST = "project.selectCallbackManageList";
	public final static String QRY_SELECT_CALLBACK_MANAGE_APPROVAL_BRAND_LIST = "project.selectCallbackManageApprovalBrandList";
	public final static String QRY_SELECT_CALLBACK_MANAGE_DETAIL = "project.selectCallbackManageDetail";
	public final static String QRY_SELECT_CALLBACK_APPROVAL_BRAND_LIST = "project.selectApprovalBrandList";

	// RCS 템플릿 관리
	public final static String QRY_SELECT_RCS_TEMPLATE_LIST = "rcsTemplate.selectRcsTemplate";
	public final static String QRY_SELECT_RCS_TEMPLATE_LIST_CNT = "rcsTemplate.selectRcsTemplateCnt";
	public final static String QRY_SELECT_CORP_BRAND_CNT = "rcsTemplate.selectCorpBrandCnt";
	public final static String QRY_SELECT_BRAND_LIST = "rcsTemplate.selectBrandList";
	public final static String QRY_SELECT_RCS_MSGBASEFORM_LIST = "rcsTemplate.selectRcsMsgbaseformList";
	public final static String QRY_SELECT_RCS_TEMPLATE_UPDATE_FORM = "rcsTemplate.selectRcsTemplateUpdateForm";
	public final static String QRY_SELECT_RCS_BASE_FORM = "rcsTemplate.selectRcsBaseForm";

	// RCS 템플릿 등록
	public final static String QRY_SELECT_RCS_BASE_FORM_POP = "rcsTemplateSend.selectRcsBaseFormPop";
	public final static String QRY_SELECT_RCS_MESSAGE_FORM = "rcsTemplateSend.selectRcsMessageForm";
	public final static String QRY_SELECT_RCS_MESSAGE_ID = "rcsTemplateSend.selectRcsMessageId";
	public final static String QRY_INSERT_RCS_TMP_MSGBASE = "rcsTemplateSend.insertRcsTmpMsgbase";
	public final static String QRY_SELECT_RCS_TMP_MSGBASE_CNT = "rcsTemplateSend.selectRcsTmpMsgbaseCnt";
	public final static String QRY_SELECT_RCS_TMP_MSGBASE = "rcsTemplateSend.selectRcsTmpMsgbase";
	public final static String QRY_DELETE_RCS_TMP_BSGBASE = "rcsTemplateSend.deleteRcsTmpMsgbase";
	public final static String QRY_SELECT_RCS_TMP_MSGBASE_DETAIL = "rcsTemplateSend.selectRcsTmpMsgbaseDetail";
	public final static String QRY_SELECT_RCS_CALLBACK_LIST = "rcsTemplateSend.selectRcsCallbackList";

	// 주소록 관리
	public final static String QRY_SELECT_ADDR_CATE_GRP_LIST = "address.selectAddrCateGrpList";
	public final static String QRY_SELECT_ADDR_CATE_GRP = "address.selectAddrCateGrp";
	public final static String QRY_SELECT_ADDR_CATE_LIST = "address.selectAddrCateList";
	public final static String QRY_SELECT_ADDR_MEM_LIST_CNT = "address.selectAddrMemListCnt";
	public final static String QRY_SELECT_ADDR_MEM_LIST = "address.selectAddrMemList";
	public final static String QRY_INSERT_ADDR_CATE_GRP = "address.insertAddrCateGrp";
	public final static String QRY_UPDATE_ADDR_CATE_GRP = "address.updateAddrCateGrp";
	public final static String QRY_SELECT_ADDR_PROJECT_LIST = "address.selectAddrProjectList";
	public final static String QRY_SELECT_ADDR_CM_CU_LIST_CNT = "address.selectAddrCmCuListCnt";
	public final static String QRY_SELECT_ADDR_CM_CU_LIST = "address.selectAddrCmCuList";
	public final static String QRY_INSERT_ADDR_MEMBER_LIST = "address.insertAddrMemberList";
	public final static String QRY_DELETE_ADDR_MEMBER = "address.deleteAddrMember";
	public final static String QRY_SELECT_ADDR_RCVR_LIST_CNT = "address.selectAddrRcvrListCnt";
	public final static String QRY_SELECT_ADDR_RCVR_LIST = "address.selectAddrRcvrList";
	public final static String QRY_INSERT_ADDR_RCVR = "address.insertAddRcvr";
	public final static String QRY_UPDATE_ADDR_RCVR = "address.updateAddrRcvr";
	public final static String QRY_DELETE_ADDR_RCVR = "address.deleteAddrRcvr";
	public final static String QRY_SELECT_ADDR_RCVR_CNT = "address.selectAddrRcvrCnt";
	public final static String QRY_INSERT_ADDR_CATE = "address.insertAddrCate";
	public final static String QRY_UPDATE_ADDR_CATE = "address.updateAddrCate";
	public final static String QRY_DELETE_ADDR_CATE = "address.deleteAddrCate";
	public final static String QRY_DELETE_ADDR_CATE_MEMBER = "address.deleteAddrCateMember";

	// 마이페이지
	// 회원정보
	public final static String QRY_SELECT_MEMBER_INFO = "myPage.selectMemberInfo";
	public final static String QRY_UPDATE_MEMBER_INFO = "myPage.updateMemberInfo";
	public final static String QRY_SELECT_SMS_CERTIFY_NUMBER = "myPage.selectSmsCertifyNumber";
	public final static String QRY_SELECT_PROJECT_LIST_BY_USERID = "myPage.selectProejctListByUserId";
	public final static String QRY_UPDATE_USER_SMS_CERTIFY_NUMB = "myPage.updateUserSmsCertifyNumb";
	// 나의 문의 내역서
	public final static String QRY_SELECT_QNA_LIST_COUNT = "myPage.selectQnaList_count";
	public final static String QRY_SELECT_QNA_LIST = "myPage.selectQnaList";
	public final static String QRY_INSERT_QUEST_BOARD = "myPage.insertQnaInfo";
	public final static String QRY_UPDATE_QUEST_BOARD = "myPage.updateQnaInfo";
	public final static String QRY_DELETE_QUEST_BOARD = "myPage.deleteQnaInfo";

	// 고객사 정보
	public final static String QRY_UPDATE_CORP_INFO = "myPage.updateCorpInfo";

	// 프로젝트 기본정보
	public final static String QRY_SELECT_PROJECT_BASE_INFO = "baseInfo.selectProjectBaseInfo";
	public final static String QRY_UPDATE_PROJECT_BASE_INFO = "baseInfo.updateProjectBaseInfo";
	public final static String QRY_SELECT_APIKEY_LIST = "baseInfo.selectApiKeyList";
	public final static String QRY_INSERT_APIKEY = "baseInfo.insertApiKey";
	public final static String QRY_UPDATE_APIKEY = "baseInfo.updateApiKey";
	public final static String QRY_SELECT_APIKEY_PASSWORD = "baseInfo.selectApiKeyPassword";

	// 프로젝트 멤버
	public final static String QRY_SELECT_PROJECT_MEMBER_LIST_CNT = "member.selectProjectMemberListCnt";
	public final static String QRY_SELECT_PROJECT_MEMBER_LIST = "member.selectProjectMemberList";
	public final static String QRY_INSERT_PROJECT_MEMBER = "member.insertProjectMember";
	public final static String QRY_DELETE_PROJECT_MEMBER = "member.deleteProjectMember";
	public final static String QRY_SELECT_PROJECT_USER_LIST_CNT = "member.selectProjectUserListCnt";
	public final static String QRY_SELECT_PROJECT_USER_LIST = "member.selectProjectUserList";

	// 이용내역
	public final static String QRY_SELECT_USE_HISTORY_LIST_CNT = "use.selectUseHistoryListCnt";
	public final static String QRY_SELECT_USE_HISTORY_LIST = "use.selectUseHistoryList";
	public final static String QRY_SELECT_SIX_MONTH_USE_CNT = "use.selectSixMonthUseCnt";
	public final static String QRY_SELECT_SIX_MONTH_CHAN_USE_CNT = "use.selectSixMonthChanUseCnt";
	public final static String QRY_SELECT_SIX_MONTH_PRE_PAY_AMT = "use.selectSixMonthPrePayAmt";
	public final static String QRY_SELECT_SIX_MONTH_DEF_PAY_AMT = "use.selectSixMonthDefPayAmt";

	// 서비스요금
	public final static String QRY_SELECT_ISEXISTS_CORP_PRODUCTUNIT = "use.selectIsExistsCorpProductUnit";
	public final static String QRY_SELECT_CORP_PRODUCTUNIT = "use.selectCorpProductUnit";

	// 분배서비스
	public final static String QRY_SELECT_BASICDIS_RATIO = "project.selectBasicDisRatio";
	public final static String QRY_SELECT_DIS_RATIO = "project.seletDisRatio";
	public final static String QRY_SELECT_DUPLICATE_DISNAME = "project.selectDuplicateDistribution";
	public final static String QRY_UPDATE_DISTRIBUTIONDATA = "project.updateDistributionData";
	public final static String QRY_UPDATE_PROJECT_DISTRIBUTION = "project.updateProjectDistribution";
	public final static String QRY_INSERT_DISTRIBUTIONDATA = "project.insertDistributionData";

	// 대시보드
	public final static String QRY_SELECT_DASH_PROJECT_INFO = "dashboard.selectProjectInfo";
	public final static String QRY_SELECT_DASH_PROJECT_LIST = "dashboard.selectProjectList";
	public final static String QRY_SELECT_DASH_NOTICE_LIST = "dashboard.selectNoticeList";
	public final static String QRY_SELECT_CH_TOTAL_COUNT_INFO = "selectChTotCnt";
	public final static String QRY_SELECT_CH_SUCC_FAIL_COUNT_LIST = "selectChSuccFailCntList";
	public final static String QRY_SELECT_CH_FAIL_CODE_LIST = "selectChFailCodeList";
	public final static String QRY_SELECT_CH_FAIL_CODE_COUNT_LIST = "selectChFailCodeCntList";
	public final static String QRY_SELECT_RT_USED_TIME_LINE_LIST = "selectRtUsedTimeLineList";
	public final static String QRY_SELECT_RT_USED_DATA_LIST = "selectRtUsedDataList";
	public final static String QRY_SELECT_MONTH_USED_TIME_LINE_LIST = "selectMonthUsedTimeLineList";
	public final static String QRY_SELECT_MONTH_USED_DATA_LIST = "selectMonthUsedDataList";
	public final static String QRY_SELECT_SIX_MONTH_USED_TIME_LINE_LIST = "selectSixMonthUsedTimeLineList";
	public final static String QRY_SELECT_SIX_MONTH_USED_DATA_LIST = "selectSixMonthUsedDataList";

	// 통계
	public final static String QRY_SELECT_STATIS_SND_LIST = "statisticsUser.selectStatisSndList";
	public final static String QRY_SELECT_STATIS_SND_CNT_LIST = "statisticsUser.selectStatisSndCntList";
	public final static String QRY_SELECT_STATIS_SND_MONTH_CNT_LIST = "statisticsUser.selectStatisSndMonthCntList";
	public final static String QRY_SELECT_STATIS_LIST = "statisticsAdmin.selectStatisList";
	public final static String QRY_SELECT_STATIS_PROJECT_LIST = "statisticsAdmin.selectStatisProjectList";

}
