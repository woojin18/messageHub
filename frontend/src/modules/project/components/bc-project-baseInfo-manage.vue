<template>
	<!-- content -->
	<div>
		<article>
			<div class="row">
				<div class="col-xs-12">
					<h4>프로젝트 기본정보</h4>
					<div class="Dashboard01 fl border-line pd20">
						<div class="of_h consoleCon">
							<h5 class="inline-block" style="width:10%">프로젝트명 *</h5>
							<div style="width:88%" class="of_h float-right">
								<div style="width:83%">
									<input id="projectName" type="text" class="inputStyle float-left" maxlength="25">
								</div>
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:10%">프로젝트명 설명</h5>
							<div style="width:88%" class="of_h float-right">
								<div>
									<textarea id="projectDesc" class="textareaStyle height120" maxlength="100"></textarea>
								</div>
							</div>
						</div>

						<div class="of_h">
							<h5 class="inline-block float-left" style="width:10%">결제유형</h5>
							<div style="width:88%" class="float-right">
								<p class="color4 consolMarginTop">{{ baseInfoData.payTypeName }}</p>
							</div>
						</div>

						<!-- <div class="clear consolMarginTop">
							<h5 class="inline-block" style="width:10%">야간 메시지 발송<i class="fas fa-question-circle toolTip ml5"><span class="toolTipText" style="width:250px">{{nightSendSthh}}:{{nightSendStmm}} ~ 다음날 {{nightSendEdhh}}:{{nightSendEdmm}} 까지 야간 메시지<br>발송에 대한 여부</span></i></h5>
							<div style="width:88%" class="float-right">
								<input type="radio" name="nightSendYn" value="Y" id="nightSendY" @click="fnModalView"><label for="nightSendY" class="mr30">예</label>
								<input type="radio" name="nightSendYn" value="N" id="nightSendN"> <label for="nightSendN">아니요</label>	
							</div>
						</div> -->

						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:10%">사용여부</h5>
							<div style="width:88%" class="float-right">
								<input type="radio" name="useYn" value="Y" id="yes" checked=""><label for="yes" class="mr30">예</label>
								<input type="radio" name="useYn" value="N" id="no"> <label for="no">아니요</label>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-xs-12">
					<div class="Dashboard01 pd0 of_h">
						<h4 class="inline-block">API Key 관리</h4>
						<div class="float-right h4Button">
							<a @click="fnRegisterApiKeyPop" class="btnStyle2 borderGray">KEY 생성<i class="far fa-plus-circle ml10"></i></a>
						</div>
						<p class="color4">* 프로젝트 API Key는 5개까지 발급받을 수 있으며, 서비스 이용 시 인증에 사용됩니다.</p>

						<table class="table_skin1 mt10" style="width:100%">
							<colgroup>
								<col style="width:25%">
								<col style="width:25%">
								<col style="width:10%">
								<col style="width:10%">
								<col style="width:20%">
								<col style="width:10%">
							</colgroup>
							<thead>
								<tr>
									<th class="text-center">API Key 명</th>
									<th class="text-center">API Key</th>
									<th class="text-center">웹 사용</th>
									<th class="text-center">상태</th>
									<th class="text-center">생성일</th>
									<th class="text-center end">관리</th>
								</tr>
							</thead>
							<tbody>
								<tr v-for="(data) in apiKeyList" :key="data.apiKey">
									<td class="text-center">{{ data.apiKeyName }}</td>
									<td class="text-center">{{ data.apiKey }}</td>
									<td class="text-center">{{ data.webSenderYnName }}</td>
									<td class="text-center">{{ data.statusName }}</td>
									<td class="text-center">{{ data.regDt }}</td>
									<td class="text-center end"><a @click="fnUpdateApiKeyPop(data)" class="btnStyle1 borderLightGray small mr5">수정</a></td>
								</tr>
								<tr v-if="apiKeyList.length == 0">
									<td class="text-center" colspan="5">검색된 내용이 없습니다.</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-xs-12">
					<div class="Dashboard01 pd0">
						<h4>사용채널 선택</h4>
						<table class="table_skin1 tbl-striped-odd mt0" style="width:100%">
							<colgroup>
								<col style="width:65%">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<td class="text-left">RCS</td>
									<td class= "end">
										<input type="radio" name="radioRcs" value="Y" id="yes1" disabled checked=""> <label for="yes1" class="mr30">사용</label>
										<input type="radio" name="radioRcs" value="N" id="no1" disabled> <label for="no1">미사용</label>
									</td>
								</tr>
								<tr>
									<td class="text-left">SMS/MMS</td>
									<td class="end">
										<input type="radio" name="radioMms" value="Y" id="yes2" disabled checked=""> <label for="yes2" class="mr30">사용</label>
										<input type="radio" name="radioMms" value="N" id="no2" disabled> <label for="no2">미사용</label>
									</td>
								</tr>
								<tr>
									<td class="text-left">PUSH</td>
									<td class="end">
										<input type="radio" name="radioPush" value="Y" id="yes3" checked=""> <label for="yes3" class="mr30">사용</label>
										<input type="radio" name="radioPush" value="N" id="no3"> <label for="no3">미사용</label>
									</td>
								</tr>
								<tr>
									<td class="text-left">카카오톡</td>
									<td class="end">
										<input type="radio" name="radioKakao" value="Y" id="yes4" checked=""> <label for="yes4" class="mr30">사용</label>
										<input type="radio" name="radioKakao" value="N" id="no4"> <label for="no4">미사용</label>
									</td>
								</tr>
								<tr>
								<td class="text-left">MO</td>
									<td class="end">
										<input type="radio" name="radioMo" value="Y" id="yes7" :disabled="this.baseInfoData.payType === 'PRE'" checked=""> <label for="yes7" class="mr30">사용</label>
										<input type="radio" name="radioMo" value="N" id="no7" :disabled="this.baseInfoData.payType === 'PRE'"> <label for="no7">미사용</label>
									</td>
								</tr>
							</tbody>
						</table>
						<p class="color4 consolMarginTop">* 채널 설정을 위해서는  반드시 해당 채널을 [사용]으로 선택하셔야 합니다. </p>
					</div>
				</div>
			</div>

			<div class="text-center mt20">
				<a @click="fnSave" class="btnStyle2 backRed" activity="SAVE">저장</a>
			</div>
			<!-- //본문 -->
		</article>

		<!--  Modal -->
		<apiKeyPop :saveStatus="saveStatus" :apiKeyData="apiKeyData" :apiKeyOpen="apiKeyOpen"></apiKeyPop>

		<div class="modal fade modalStyle" id="nightSendModal" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog" style="width: 410px;">
				<div class="modal-content">
					<div class="modal-body">
						<h2>야간 메시지 발송 동의</h2>
						<hr>

						<div class="of_h consolMarginTop">
							<p>정보통신망 이용촉진 및 정보보호 등에 관한 법률 제50조(영리목적의 광고성 정보 전송 제한)에 의거 야간({{nightSendSthh}}:{{nightSendStmm}} ~ 다음날 {{nightSendEdhh}}:{{nightSendEdmm}})에 광고 메세지를 보낼 경우 야간 광고 메시지 별도 수신 동의를 받은 고객에 한해서만 메시지를 발송하겠습니다.</p>
						</div>

						<div class="text-center mt20">
							<a @click="fnRadioChg('Y')" class="btnStyle2 backBlack" data-dismiss="modal" title="확인">확인</a>	
							<a @click="fnRadioChg('N')" class="btnStyle2 backPink" data-dismiss="modal" title="거부">거부</a>	
						</div>	

					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- //content -->
</template>

<script>
import baseInfoApi from '../service/baseInfoApi'
import confirm from '@/modules/commonUtil/service/confirm';
import {eventBus} from '@/modules/commonUtil/service/eventBus';
import apiKeyPop from '../components/bc-baseinfo-apiKey.vue';
import tokenSvc from '@/common/token-service';

export default {
	name: 'baseInfoManage',
	components: {
		apiKeyPop,
	},
	props: {
	},
	data() {
		return {
			baseInfoData: {},
			apiKeyList: [],
			saveStatus: '',
			apiKeyData: {},
			apiKeyOpen: false,
			title: 'API Key 관리',
			nightSendSthh: '',
			nightSendStmm: '',
			nightSendEdhh: '',
			nightSendEdmm: ''
		}
	},
	mounted() {
		this.fnSearchProject();
		//this.fnNightSendTime();
	},
	watch: {
		baseInfoData: function() {
			jQuery('#projectName').val(this.baseInfoData.projectName);
			jQuery('#projectDesc').val(this.baseInfoData.projectDesc);
			jQuery('input:radio[name=useYn]:input[value="' + this.baseInfoData.useYn + '"]').prop('checked', true);
			jQuery('input:radio[name=radioRcs]:input[value="' + this.baseInfoData.rcsYn + '"]').prop('checked', true);
			jQuery('input:radio[name=radioMms]:input[value="' + this.baseInfoData.smsmmsYn + '"]').prop('checked', true);
			jQuery('input:radio[name=radioPush]:input[value="' + this.baseInfoData.pushYn + '"]').prop('checked', true);
			jQuery('input:radio[name=radioKakao]:input[value="' + this.baseInfoData.kakaoYn + '"]').prop('checked', true);
			jQuery('input:radio[name=radioMo]:input[value="' + this.baseInfoData.moYn + '"]').prop('checked', true);
			//jQuery('input:radio[name=nightSendYn]:input[value="' + this.baseInfoData.nightSendYn + '"]').prop('checked', true);
		}
	},
	methods: {
		// 프로젝트 조회
		async fnSearchProject() {
			let params = {
				projectId: this.$parent.projectId,
				corpId: tokenSvc.getToken().principal.corpId,
			};

			await baseInfoApi.selectProjectBaseInfo(params).then(response =>{
				var result = response.data;
				if(result.success) {
					this.baseInfoData = result.data.baseInfo;
					this.apiKeyList = result.data.apiKeyList;
				} else {
					confirm.fnAlert(this.title, result.message);
				}
			});
		},
		// 저장
		fnSave() {
			let projectName = jQuery('#projectName').val();
			if(projectName == null || projectName == '') {
				confirm.fnAlert(this.title, '프로젝트명을 입력하세요.');
				return false;
			}
			eventBus.$on('callbackEventBus', this.fnSaveCallBack);
			confirm.fnConfirm(this.title, '저장하시겠습니까?', '확인');
		},
		fnSaveCallBack() {
			var params = {
				'projectId'		: this.baseInfoData.projectId,
				'projectName'	: jQuery('#projectName').val(),
				'projectDesc'	: jQuery('#projectDesc').val(),
				'useYn'			: jQuery('input[name="useYn"]:checked').val(),
				'radioRcs'		: jQuery('input[name="radioRcs"]:checked').val(),
				'radioMms'		: jQuery('input[name="radioMms"]:checked').val(),
				'radioPush'		: jQuery('input[name="radioPush"]:checked').val(),
				'radioKko'		: jQuery('input[name="radioKakao"]:checked').val(),
				'radioMo'		: jQuery('input[name="radioMo"]:checked').val(),
				//'nightSendYN'	: jQuery('input[name="nightSendYn"]:checked').val(),
			};

			baseInfoApi.saveProjectBaseInfo(params).then(response =>{
				var result = response.data;

				if(result.success) {
					confirm.fnAlert(this.title, '저장되었습니다.');
					this.fnSearchProject();
				} else {
					confirm.fnAlert(this.title, result.message);
				}
			});
		},
		// API키 등록
		fnRegisterApiKeyPop() {
			if(this.apiKeyList != null && this.apiKeyList.length >= 5) {
				confirm.fnAlert(this.title, 'API Key는 5개까지 발급됩니다.');
				return false;
			}
			this.apiKeyOpen = !this.apiKeyOpen;
			this.apiKeyData = {};
			this.apiKeyData.projectId = this.$parent.projectId;
			this.saveStatus = 'R';
			jQuery('#apiKeyPop').modal('show');
		},
		// API키 수정
		fnUpdateApiKeyPop(data) {
			jQuery('#apiKeyPop').modal('show');
			this.apiKeyOpen = !this.apiKeyOpen;
			this.apiKeyData = data;
			this.saveStatus = 'U';
		},
		fnRadioChg(value){
			jQuery('input:radio[name=nightSendYn]:input[value="'+value+'"]').prop('checked', true);
		},
		fnModalView($event){
			$event.preventDefault();
			jQuery('#nightSendModal').modal('show');
		},
		// 야간 메시지 전송 시간 확인
		async fnNightSendTime() {
			let params = {};
			await baseInfoApi.selectNightSendTime(params).then(response =>{
				var result = response.data;
				if(result.success) {
					this.nightSendSthh = result.data.nightSendSthh;
					this.nightSendStmm = result.data.nightSendStmm;
					this.nightSendEdhh = result.data.nightSendEdhh;
					this.nightSendEdmm = result.data.nightSendEdmm;
				} else {
					confirm.fnAlert(this.title, result.message);
				}
			});
		},
	}
}
</script>