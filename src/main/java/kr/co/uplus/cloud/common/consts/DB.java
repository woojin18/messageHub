package kr.co.uplus.cloud.common.consts;

public class DB {

	//공통
	public final static String QRY_SELECT_IMAGE_LIST_CNT = "common.selectImageListCnt";
	public final static String QRY_SELECT_IMAGE_LIST = "common.selectImageList";
	public final static String QRY_DELETE_IMAGE = "common.deleteImage";
	public final static String QRY_SELECT_CORP_INFO = "common.selectCorpInfoByUserId";
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
	public final static String QRY_SELECT_SMS_TMPLT = "template.selectSmsTemplate";
	public final static String QRY_INSERT_SMS_TMPLT = "template.insertSmsTemplate";
	public final static String QRY_UPDATE_SMS_TMPLT = "template.updateSmsTemplate";
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
	public final static String QRY_INSERT_SEND_PUSH_MSG = "sendMessage.qryInsertSendPushMessage";
	// RCS 채널 브랜드 정보
	public final static String QRY_SELECT_RCS_BRANDLIST = "channel.selectRcsBrandList";
	// RCS 등록 템플릿 조회
	public final static String QRY_SELECT_RCS_REGTMPLTLIST = "channel.selectRcsRegTmpltList";
	// RCS 발신번호 상세
	public final static String QRY_SELECT_RCS_CALLBACKLIST = "channel.selectRcsCallbackList";
	//웹캐시
	public final static String QRY_INSERT_WEB_CASH_INFO = "cash.insertWebCashInfo";
	public final static String QRY_SELECT_WEB_CASH_INFO = "cash.selectWebCashInfo";
	
}
