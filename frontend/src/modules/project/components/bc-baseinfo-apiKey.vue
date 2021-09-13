<template>
	<!-- key Modal -->
	<div class="modal modalStyle" id="apiKeyPop" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>API Key 상세</h2>
						<hr>
						<div class="of_h">
							<div class="float-left" style="width:24%"><h5>API KEY 명</h5></div>
							<div class="float-left" style="width:76%">
								<input type="text" id="apiKeyName" class="inputStyle" maxlength="25">
							</div>
						</div>
						<div id="apiKeyDiv" class="of_h consolMarginTop">
							<div class="float-left" style="width:24%"><h5>API KEY</h5></div>
							<div class="float-left" style="width:76%">
								<input type="text" id="apiKey" class="inputStyle" :disabled="true" >
							</div>
						</div>
						<div class="of_h consolMarginTop" v-if="this.saveStatus == 'R'">
							<div class="float-left" style="width:24%"><h5>API 암호</h5></div>
							<div class="float-left" style="width:76%">
								<input type="password" id="apiPwd" class="inputStyle">
							</div>
						</div>
						<div class="of_h consolMarginTop" v-if="this.saveStatus == 'U'">
							<div class="float-left" style="width:24%"><h5>API 암호</h5></div>
							<div class="float-left" style="width:61%">
								<input type="password" id="apiPwd" class="inputStyle">
							</div>
							<a @click="fnPasswordChange" class="btnStyle1 backWhite ml10">변경</a>
						</div>
						<div id="apiPwdConfirmDiv" class="of_h consolMarginTop">
							<div class="float-left" style="width:24%"><h5>API 암호 확인</h5></div>
							<div class="float-left" style="width:76%">
								<input type="password" id="apiPwdConfirm" class="inputStyle">
							</div>
						</div>
						
						<div v-if="this.changeYn" class="of_h consolMarginTop">
							<div class="float-left" style="width:24%"><h5>변경 API 암호</h5></div>
							<div class="float-left" style="width:76%">
								<input type="password" v-model="changeApiPwd" class="inputStyle">
							</div>
						</div>
						<div v-if="this.changeYn" class="of_h consolMarginTop">
							<div class="float-left" style="width:24%"><h5>변경 API 암호 확인</h5></div>
							<div class="float-left" style="width:76%">
								<input type="password" v-model="changeApiPwdConfirm" class="inputStyle">
							</div>
						</div>

						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:24%"><h5>웹 사용</h5></div>
							<div class="float-left" style="width:76%">
								<input type="radio" name="webSenderYn" value="Y" id="agree3-1" checked=""> <label for="agree3-1" class="mr30">예</label>
								<input type="radio" name="webSenderYn" value="N" id="agree3-2"> <label for="agree3-2">아니오</label>
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:24%"><h5>IP 체크</h5></div>
							<div class="float-left" style="width:76%">
								<input type="radio" name="ipChkYn" value="Y" id="agree3-3" @click="fnIpListView(true)" checked=""> <label for="agree3-3" class="mr30">예</label>
								<input type="radio" name="ipChkYn" value="N" id="agree3-4" @click="fnIpListView(false)"> <label for="agree3-4">아니오</label>
							</div>
						</div>
						<span id="ipListSpan">
							<div class="of_h consolMarginTop">
								<div v-for="index in loopCnt" :key="index">
									<!-- 기존 ip 목록 -->
									<div v-if="index == 1"> <!-- 첫번째 항목-->
										<div class="float-left" style="width:24%">
											<h5>고객사 접속 IP</h5>
										</div>
										<div class="float-left" style="width:76%">
											<div class="float-left" style="width:70%">
												<input oninput="javascript: this.value = this.value.replace(/[^0-9.]/g, '');" type="text" class="inputStyle" v-model="ipList[index-1]">
											</div>
											<div class="float-left ml10" style="width:20%">
												<a @click="fnAddRow" class="btnStyle1 borderGray" style="padding:0 10px" title="고객사 접속IP 입력란 추가">
													<i class="far fa-plus"></i>
												</a>
											</div>
										</div>
									</div>
									<div v-else> <!-- 나머지 항목-->
										<div class="float-right consolMarginTop" style="width:76%">
											<div class="float-left" style="width:70%">
												<input type="text" class="inputStyle" v-model="ipList[index-1]">
											</div>
											<div class="float-left ml10" style="width:27%">
												<a @click="fnRemoveRow(index-1)" class="btnStyle1 borderGray" style="padding:0 10px" title="고객사 접속IP 입력란 삭제">
													<i class="far fa-minus"></i>
												</a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</span>
						<!-- <div class="of_h consolMarginTop">
							<div class="float-left" style="width:24%"><h5>초당 발송 건수</h5></div>
							<div class="float-left" style="width:20%">
								<input type="text" id="cps" class="inputStyle" @input="fnCorrectNumberInput" maxlength="4">
							</div>
						</div> -->
						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:24%">상태</h5>
							<select id="selectStatus" class="selectStyle2" style="width:20%">
								<option value="USE">사용</option>
								<option value="CANCEL">미사용</option>
							</select>
						</div>
						<!-- <div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:24%">라인 타입</h5>
							<select id="selectLineType" class="selectStyle2" style="width:20%">
								<option value="">선택</option>
							</select>
						</div> -->
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:24%"><h5>결과수신여부</h5></div>
							<div class="float-left" style="width:76%">
								<input type="radio" name="rptYn" value="Y" id="agree3-5" checked=""> <label for="agree3-5" class="mr30">예</label>
								<input type="radio" name="rptYn" value="N" id="agree3-6"> <label for="agree3-6">아니오</label>
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:24%"><h5>중복체크</h5></div>
							<div class="float-left" style="width:76%">
								<input type="radio" name="dupChkYn" value="Y" id="agree3-7" checked=""> <label for="agree3-7" class="mr30">예</label>
								<input type="radio" name="dupChkYn" value="N" id="agree3-8"> <label for="agree3-8">아니오</label>
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:24%"><h5>일 최대 발송 체크</h5></div>
							<div class="float-left" style="width:76%">
								<input type="radio" name="daySenderChkYn" value="Y" id="limited1-1"  @click="fnDayAmountView(true)" checked=""> <label for="limited1-1" class="mr30">예</label>
								<input type="radio" name="daySenderChkYn" value="N" id="limited1-2"  @click="fnDayAmountView(false)"> <label for="limited1-2">아니오</label>
							</div>
						</div>
						<span id="daySenderLimitAmountSpan">
							<div class="of_h consolMarginTop">
								<div class="float-left" style="width:24%"><h5>일 제한금액</h5></div>
								<div class="float-left" style="width:20%">
									<input type="text" id="daySenderLimitAmount" class="inputStyle" @input="fnCorrectNumberInput">
								</div>
								<div class="float-left ml5" style="width:20%">원</div>
							</div>
						</span>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:24%"><h5>월 최대 발송 체크</h5></div>
							<div class="float-left" style="width:76%">
								<input type="radio" name="monSenderChkYn" value="Y" id="limited2-1" @click="fnMonAmountView(true)" checked=""> <label for="limited2-1" class="mr30">예</label>
								<input type="radio" name="monSenderChkYn" value="N" id="limited2-2" @click="fnMonAmountView(false)"> <label for="limited2-2">아니오</label>
							</div>
						</div>
						<span id="monSenderLimitAmountSpan">
							<div class="of_h consolMarginTop">
								<div class="float-left" style="width:24%"><h5>월 제한금액</h5></div>
								<div class="float-left" style="width:20%">
									<input type="text" id="monSenderLimitAmount" class="inputStyle" @input="fnCorrectNumberInput">
								</div>
								<div class="float-left ml5" style="width:20%">원</div>
							</div>
						</span>
					</div>
					<div class="text-center mt20">
						<a @click="fnApiKeySave" class="btnStyle1 backBlack mr5" activity="SAVE">저장</a>
						<a @click="fnClose" class="btnStyle1 backWhite">취소</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import baseInfoApi from '../service/baseInfoApi'
import confirm from '@/modules/commonUtil/service/confirm';
import tokenSvc from '@/common/token-service';
import commonUtilApi from '@/modules/commonUtil/service/commonUtilApi';
import {eventBus} from '@/modules/commonUtil/service/eventBus';

export default {
	name: 'BaseInfoApiKeyPop',
	props: {
		saveStatus: {
			status: String,
			require: true,
		},
		apiKeyData: {
			status: Object,
			require: false,
		},
		apiKeyOpen: {
			status: Boolean,
			require: true,
		},
	},
	watch: {
		apiKeyOpen: function() {
			this.changeYn = false;
			this.changeApiPwd = '';
			this.changeApiPwdConfirm = '';
			if(this.saveStatus === 'R') {
				this.apiKeyData.apiKey = '';
				jQuery('#apiKeyDiv').hide();
				jQuery('#apiPwdConfirmDiv').show();
				jQuery('#apiKey').val('');
				jQuery('#apiPwd').val('');
				jQuery('#apiPwdConfirm').val('');
				jQuery('input:radio[name=webSenderYn]:input[value="Y"]').prop('checked', true);
				jQuery('input:radio[name=ipChkYn]:input[value="Y"]').prop('checked', true);
				jQuery('#ipListSpan').show();
				// 고객사 접속 IP 입력칸 기본 생성
				this.ipList = [];
				this.ipList.push('');
				this.loopCnt = 1;
				jQuery('#cps').val(0);
				jQuery('#selectStatus').val('USE').attr('selected', 'selected');
				jQuery('#selectLineType').val('').attr('selected', 'selected');
				jQuery('input:radio[name=rptYn]:input[value="Y"]').prop('checked', true);
				jQuery('input:radio[name=dupChkYn]:input[value="Y"]').prop('checked', true);
				jQuery('input:radio[name=daySenderChkYn]:input[value="N"]').prop('checked', true);
				jQuery('#daySenderLimitAmount').val(null);
				jQuery('#daySenderLimitAmountSpan').hide();
				jQuery('input:radio[name=monSenderChkYn]:input[value="N"]').prop('checked', true);
				jQuery('#monSenderLimitAmount').val(null);
				jQuery('#monSenderLimitAmountSpan').hide();
				jQuery('#apiKeyName').val(null);
				
			}
			if(this.saveStatus === 'U') {
				jQuery('#apiKeyDiv').show();
				jQuery('#apiPwdConfirmDiv').hide();
				jQuery('#apiKey').val(this.apiKeyData.apiKey);
				jQuery('#apiPwd').val('');
				jQuery('input:radio[name=webSenderYn]:input[value="' + this.apiKeyData.webSenderYn + '"]').prop('checked', true);
				jQuery('input:radio[name=ipChkYn]:input[value="' + this.apiKeyData.ipChkYn + '"]').prop('checked', true);

				if(this.apiKeyData.ipChkYn == 'Y')
					jQuery('#ipListSpan').show();
				else
					jQuery('#ipListSpan').hide();

				// 고객사 접속 IP 입력칸 생성 초기화
				this.ipList = [];
				this.ipList.push('');
				this.loopCnt = 1;
				// 고객사 접속 IP 입력칸 생성
				if(!this.$gfnCommonUtils.isEmpty(this.apiKeyData.ipList)) {
					this.ipList = JSON.parse(this.apiKeyData.ipList);
					this.loopCnt = this.ipList.length;
				}

				jQuery('#cps').val(this.apiKeyData.cps);
				jQuery('#selectStatus').val(this.apiKeyData.status).attr('selected', 'selected');
				jQuery('#selectLineType').val(this.apiKeyData.lineType).attr('selected', 'selected');
				jQuery('input:radio[name=rptYn]:input[value="' + this.apiKeyData.rptYn + '"]').prop('checked', true);
				jQuery('input:radio[name=dupChkYn]:input[value="' + this.apiKeyData.dupChkYn + '"]').prop('checked', true);
				jQuery('input:radio[name=daySenderChkYn]:input[value="' + this.apiKeyData.daySenderChkYn + '"]').prop('checked', true);

				if(this.apiKeyData.daySenderChkYn == 'Y')
					jQuery('#daySenderLimitAmountSpan').show();
				else
					jQuery('#daySenderLimitAmountSpan').hide();

				jQuery('#daySenderLimitAmount').val(this.apiKeyData.daySenderLimitAmount);
				jQuery('input:radio[name=monSenderChkYn]:input[value="' + this.apiKeyData.monSenderChkYn + '"]').prop('checked', true);

				if(this.apiKeyData.monSenderChkYn == 'Y')
					jQuery('#monSenderLimitAmountSpan').show();
				else
					jQuery('#monSenderLimitAmountSpan').hide();

				jQuery('#monSenderLimitAmount').val(this.apiKeyData.monSenderLimitAmount);
				jQuery('#apiKeyName').val(this.apiKeyData.apiKeyName);
			}
		},
	},
	data() {
		return {
			ipList: [],
			loopCnt: 0,
			title: 'API Key 상세',
			changeYn : false,
			changeApiPwd : "",
			changeApiPwdConfirm : "",
		}
	},
	mounted() {
		this.fnLineTypeInit();
	},
	methods: {
		// 필수값 입력 체크
		fnInputCheckReq() {
			let saveStatus				= this.saveStatus;
			let apiPwd					= jQuery('#apiPwd').val();
			let apiPwdConfirm			= jQuery('#apiPwdConfirm').val();
			let ipChkYn					= jQuery('input:radio[name=ipChkYn]:checked').val();
			let cps						= jQuery('#cps').val();
			let lineType				= jQuery('#selectLineType').val();
			let daySenderChkYn			= jQuery('input:radio[name=daySenderChkYn]:checked').val();
			let monSenderChkYn			= jQuery('input:radio[name=monSenderChkYn]:checked').val();
			let daySenderLimitAmount	= jQuery('#daySenderLimitAmount').val();
			let monSenderLimitAmount	= jQuery('#monSenderLimitAmount').val();
			
			let ipList					= this.ipList;
			if(this.$gfnCommonUtils.isEmpty(apiPwd)) {
				confirm.fnAlert(this.title, 'API 암호를 입력하세요');
				return false;
			}

			if(saveStatus =='R' && this.$gfnCommonUtils.isEmpty(apiPwdConfirm)) {
				confirm.fnAlert(this.title, 'API 암호 확인을 입력하세요');
				return false;
			}

			if(saveStatus =='R' && apiPwd != apiPwdConfirm) {
				confirm.fnAlert(this.title, '입력하신 API 암호가 일치하지 않습니다.');
				return false;
			}

			// 비밀번호 변경 시
			if( this.changeYn ){
				if( this.changeApiPwd === '' || this.changeApiPwdConfirm === '' ){
					confirm.fnAlert(this.title, '변경 API 암호, 암호 확인을 입력하세요.');
					return false;
				}
				if( this.changeApiPwd != this.changeApiPwdConfirm  ){
					confirm.fnAlert(this.title, '입력하신 변경 API 암호가 일치하지 않습니다.');
					return false;
				}
			}

			// validate an IP address
			if(ipChkYn == 'Y') {
				for(let i = 0; i < ipList.length; i++) {
					// IP입력 한개는 필수
					if( ipList[i] == null || ipList[i] == '') {
						confirm.fnAlert(this.title, 'IP를 입력하세요');
						return false;
					}
					if(!/^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/.test(ipList[i])) {
						confirm.fnAlert(this.title, '올바른 IP를 입력하세요');
						return false;
					}
					// 중복입력 체크
					for(let j = i+1; j < ipList.length; j++) {
						if( ipList[i] != '' && ipList[j] != '' && ipList[i] == ipList[j]) {
							confirm.fnAlert(this.title, '입력하신 IP가 중복됩니다.');
							return false;
						}
					}
				}
			}

			/* if(this.$gfnCommonUtils.isEmpty(cps)) {
				confirm.fnAlert(this.title, '초당 발송 건수를 입력하세요');
				return false;
			} */

			/* if(parseInt(cps) < 0 || parseInt(cps) > 9999) {
				confirm.fnAlert(this.title, '초당 발송 건수는 0~9999 사이의 값을 입력하세요');
				return false;
			}

			if(this.$gfnCommonUtils.isEmpty(lineType)) {
				confirm.fnAlert(this.title, '라인 타입을 선택하세요');
				return false;
			} */

			if(daySenderChkYn == 'Y'
					&& (this.$gfnCommonUtils.isEmpty(daySenderLimitAmount)
						|| parseInt(daySenderLimitAmount) < 1)) {
				confirm.fnAlert(this.title, '일 제한금액을 입력하세요');
				return false;
			}

			if(monSenderChkYn == 'Y'
					&& (this.$gfnCommonUtils.isEmpty(monSenderLimitAmount)
						|| parseInt(monSenderLimitAmount) < 1)) {
				confirm.fnAlert(this.title, '월 제한금액을 입력하세요');
				return false;
			}

			
			if(monSenderChkYn == 'Y' && daySenderChkYn == 'Y'
					&& ( parseInt(monSenderLimitAmount) < parseInt(daySenderLimitAmount))) {
				confirm.fnAlert(this.title, '월 제한금액은 일 제한금액보다 적을 수 없습니다.');
				return false;
			}

			return true;
		},
		fnApiKeySave() {
			if(!this.fnInputCheckReq()) return;
			eventBus.$on('callbackEventBus', this.fnApiKeySaveCallBack);
			confirm.fnConfirm(this.title, '저장하시겠습니까?', '확인');
		},
		fnApiKeySaveCallBack() {
			let params = {
				'newProjectId'			: this.apiKeyData.projectId,
				'apiKey'				: this.apiKeyData.apiKey,
				'apiPwdConfirm'			: jQuery('#apiPwdConfirm').val(),
				'changeApiPwd'			: this.changeApiPwd,
				'corpId'				: tokenSvc.getToken().principal.corpId,
				'apiPwd'				: jQuery('#apiPwd').val(),
				'ipChkYn'				: jQuery('input[name="ipChkYn"]:checked').val(),
				'cps'					: 0,
				'daySenderChkYn'		: jQuery('input[name="daySenderChkYn"]:checked').val(),
				'monSenderChkYn'		: jQuery('input[name="monSenderChkYn"]:checked').val(),
				'apiKeyName'			: jQuery('#apiKeyName').val(),
				'status'				: jQuery('#selectStatus option:selected').val(),
				'rptYn'					: jQuery('input[name="rptYn"]:checked').val(),
				'dupChkYn'				: jQuery('input[name="dupChkYn"]:checked').val(),
				'webSenderYn'			: jQuery('input[name="webSenderYn"]:checked').val(),
				'daySenderLimitAmount'	: jQuery('#daySenderLimitAmount').val(),
				'monSenderLimitAmount'	: jQuery('#monSenderLimitAmount').val(),
				'ipList'				: this.ipList,
				'saveStatus'			: this.saveStatus,

			};

			baseInfoApi.saveApiKey(params).then(response =>{
				var result = response.data;

				if(result.success) {
					confirm.fnAlert(this.title, '저장되었습니다.');
					this.$parent.fnSearchProject();
					jQuery('#apiKeyPop').modal('hide');
				} else {
					confirm.fnAlert(this.title, result.message);
				}
			});
		},
		fnAddRow() {
			this.ipList.push('');
			++this.loopCnt;
		},
		fnRemoveRow(index) {
			this.ipList.splice(index, 1);
			--this.loopCnt;
		},
		fnLineTypeInit() {
			var params = {
				codeTypeCd	: 'LINE_TYPE',
				useYN		: 'Y'
			};
			commonUtilApi.selectCodeList(params).then(response =>{
				var result = response.data.data;
				for(var i = 0; i < result.length; i++){
					jQuery('#selectLineType').append('<option value="'+result[i].codeVal1+'">'+result[i].codeName1+'</option>');
				}
			});
		},
		fnIpListView(isView) {
			if(isView) {
				jQuery('#ipListSpan').show();
			} else {
				this.ipList = [];
				this.loopCnt = 1;
				jQuery('#ipListSpan').hide();
			}
		},
		fnDayAmountView(isView) {
			if(isView) {
				jQuery('#daySenderLimitAmountSpan').show();
			} else {
				jQuery('#daySenderLimitAmount').val('');
				jQuery('#daySenderLimitAmountSpan').hide();
			}
		},
		fnMonAmountView(isView) {
			if(isView) {
				jQuery('#monSenderLimitAmountSpan').show();
			} else {
				jQuery('#monSenderLimitAmount').val('');
				jQuery('#monSenderLimitAmountSpan').hide();
			}
		},
		// 숫자만 입력
		fnCorrectNumberInput(event) {
			event.target.value = event.target.value.replace(/[^0-9]/g, '');
		},
		// 닫기
		fnClose() {
			jQuery('#apiKeyPop').modal('hide');
		},
		fnPasswordChange(){
			this.changeYn = !this.changeYn;
			this.changeApiPwd = '';
			this.changeApiPwdConfirm = '';
		},
	}
}
</script>