<template>
	<div id="content">
		<article>
			<h4 class="mt40">Application 등록</h4>
			<div class="row mt30">
				<div class="col-xs-12">
					<div class="menuBox">						
						<div class="of_h">
							<h4 class="font-normal inline-block vertical-middle" style="width:15%">APP 명*</h4>
							<input type="text" class="inputStyle" style="width:72%" v-model="appNm">
						</div>
						<div class="of_h mt10">
							<h4 class="font-normal inline-block vertical-middle" style="width:15%">APP ID*</h4>
							<input type="text" class="inputStyle" style="width:72%" v-model="appId" readonly>
						</div>	
						<div class="of_h mt10">
							<h4 class="font-normal inline-block vertical-middle" style="width:15%">타 프로젝트 사용여부*</h4>
							<div class="inline-block float-right" style="width:72%">
								<input type="radio" name="otherProjectYn" value="Y" class="cBox" id="yes" v-model="otherProjectYn">
								<label for="yes" class="payment mt10 mr30">공용</label>
								<input type="radio" name="otherProjectYn" value="N" class="cBox" id="no" v-model="otherProjectYn">
								<label for="no" class="payment mt10">전용</label>		
							</div>
						</div>				
					</div>
				</div>
			</div>
			
			<h4 class="mt40">안드로이드(FCM)</h4>
			<p class="font-size12 color3 mt20 inline-block"><i class="far fa-info-circle"></i>Android 푸시 메시지 발송에 필요한 API Key입니다. 등록시 유효성을 검증합니다.</p>
			<div class="row mt10">
				<div class="col-xs-12">
					<div class="menuBox">						
						<div>
							<h4 class="font-normal inline-block" style="width:15%">Package name</h4>
							<input type="text" class="inputStyle" style="width:72%" v-model="fcmPackageName">
						</div>
						<div class="mt10">
							<h4 class="font-normal inline-block" style="width:15%">FCM Server Key</h4>
							<input type="text" class="inputStyle" style="width:72%" v-model="fcmServerKey">
						</div>
						<div class="mt10">
							<h4 class="font-normal inline-block" style="width:15%">Sender ID</h4>
							<input type="text" class="inputStyle" style="width:72%" v-model="senderId">
						</div>				
					</div>
				</div>
			</div>
			
			<h4 class="mt40">IOS(APNS)</h4>
			<div class="row mt10">
				<div class="col-xs-12">
					<div class="menuBox">
						<!-- <div class="of_h">
							<h4 class="font-normal inline-block" style="width:15%">P8인증서</h4>
							<div class="consolCheck display-block mt5" style="width:72%">
								{{this.apnsFileName}}
								<input type="file" class="btnStyle7 minWidthAuto float float-right" style="width:65%; height: 40px;" @change="fnFileNameChange" ref="apnsCetificationFile"/>
							</div>
						</div> -->
						<div class="of_h">
							<h4 class="font-normal inline-block" style="width:15%">P8인증서</h4>
							<input type="text" class="inputStyle vertical-top" style="width:72%" v-model="apnsFileName" disabled>
							<input type="file" class="btnStyle7 minWidthAuto float float-right" style="width:8%; height: 40px;" @change="fnFileNameChange" ref="apnsCetificationFile"/>
						</div>
						<div class="mt10">
							<h4 class="font-normal inline-block" style="width:15%">Bundle ID</h4>
							<input type="text" class="inputStyle" style="width:72%" v-model="iosAppId">
						</div>
						<div class="mt15">
							<h4 class="font-normal inline-block" style="width:15%">Team ID</h4>
							<input type="text" class="inputStyle" style="width:72%" v-model="teamKey">
						</div>
						<div class="mt10">
							<h4 class="font-normal inline-block" style="width:15%">Key ID</h4>
							<input type="text" class="inputStyle" style="width:72%" v-model="keyId">
						</div>	
					</div>
				</div>
			</div>
			
			<p class="font-size12 color3 mt10 inline-block"><i class="far fa-info-circle"></i> 푸시 메시지를 이용할 OS 및 유형에 따라 각 인증서의 항목을 모두 입력해 주세요.</p>
			
			<div class="mt50 text-center">
				<a @click="fnSave" class="btnStyle5 red width120" activity="SAVE">저장</a>
				<a @click="fnBack" ref="backBtn" class="btnStyle5 white width120">목록</a>
			</div>
			<!-- //본문 -->
		</article>
	</div>
</template>

<script>
import axios from 'axios'
import confirm from "@/modules/commonUtil/service/confirm"
import tokenSvc from '@/common/token-service';

export default {
	name: 'bcChanRcsDetail',
	data() {
		return {
			mainProjectId : '', // 선택한 프로젝트 ID
			mainProjectName : '',// 선택한 프로젝트 명
			save_status	: '', // 등록 수정 여부
			projectId		: '',
			// 상세
			corpId				: '',
			appId				: '',
			appNm				: '',
			otherProjectYn		: '',
			fcmPackageName		: '',
			fcmServerKey		: '',
			senderId			: '',
			apnsCetification	: '',
			apnsFileName		: '',
			iosAppId			: '',
			teamKey				: '',
			keyId				: ''
		}
	},
	mounted() {
		this.mainProjectId		= this.$route.params.mainProjectId;
		this.mainProjectName	= this.$route.params.mainProjectName;

		this.projectId		= this.$route.params.projectId;
		this.save_status	= this.$route.params.save_status;

		if( this.save_status === 'U' ){
			this.corpId				= this.$route.params.rowData.corpId;
			this.appId				= this.$route.params.rowData.appId;
			this.appNm				= this.$route.params.rowData.appNm;
			this.otherProjectYn		= this.$route.params.rowData.otherProjectYn;
			this.fcmPackageName		= this.$route.params.rowData.fcmPackageName;
			this.fcmServerKey		= this.$route.params.rowData.fcmServerKey;
			this.senderId			= this.$route.params.rowData.senderId;
			this.apnsCetification	= this.$route.params.rowData.apnsCetification;
			this.apnsFileName		= this.$route.params.rowData.apnsFileName;			
			this.iosAppId			= this.$route.params.rowData.iosAppId;
			this.teamKey			= this.$route.params.rowData.teamKey;
			this.keyId				= this.$route.params.rowData.keyId;
		} else {
			this.corpId				= '';
			this.appId				= '';
			this.appNm				= '';
			this.otherProjectYn		= 'Y';
			this.fcmPackageName		= '';
			this.fcmServerKey		= '';
			this.senderId			= '';
			this.apnsCetification	= '';
			this.apnsFileName		= '';
			this.iosAppId			= '';
			this.teamKey			= '';
			this.keyId				= '';
		}
	},
	methods: {
		// 목록
		fnBack(){
			//this.$router.push( {name:"projectMain",params:{"projectId" : this.projectId, "selMainTab" : 4, "selMidTab" : 3 }} );
			
			this.$router.push( {name:"projectMain",params:{
					"projectId" : this.mainProjectId,
					"projectName" : this.mainProjectName,
					"selMainTab" : 4, 
					"selMidTab" : 3, 
					"selSubTab" : 1,
					"rcsYn" : this.$route.params.rcsYn,
					"smsmmsYn" : this.$route.params.smsmmsYn,
					"pushYn" : this.$route.params.pushYn,
					"kakaoYn" : this.$route.params.kakaoYn,
					"moYn" : this.$route.params.moYn,
				}
			} );
		},
		fnFileNameChange(){
			this.apnsFileName = this.$refs.apnsCetificationFile.files[0].name;
		},
		fnValidate(){
			if( this.appNm == '' ){
				confirm.fnAlert("", "APP명은 필수입니다.");
				return false;
			} else if( this.fcmPackageName != '' || this.fcmServerKey != '' || this.senderId != '' ){
				if( this.fcmPackageName == '' || this.fcmServerKey == '' || this.senderId == '' ){
					confirm.fnAlert("", "안드로이드(FCM)를 입력하시려는 경우, 안드로이드(FCM)의 모든 항목은 필수입니다.");
					return false;
				} 
			} else if ( this.apnsFileName != '' || this.iosAppId != '' || this.teamKey != '' || this.keyId != '' ){
				if( this.apnsFileName == '' || this.iosAppId == '' || this.teamKey == '' || this.keyId == '' ){
					confirm.fnAlert("", "IOS(APNS)를 입력하시려는 경우, IOS(APNS)의 모든 항목은 필수입니다.");
					return false;
				} 
			}
			return true;
		},
		// 등록, 수정
		async fnSave(){
			// 벨리데이션 처리
			if( !this.fnValidate() ){
				return;
			}

			var fd = new FormData();
			fd.append('loginId'			, tokenSvc.getToken().principal.loginId);
			fd.append('sts'				, this.save_status);
			if( this.otherProjectYn === 'Y' ){
				fd.append('projectId'		, 'ALL');
			} else {
				fd.append('projectId'		, this.projectId);
			}
			fd.append('saveProjectId'	, this.projectId);
			fd.append('appId'			, this.appId);
			fd.append('appNm'			, this.appNm);
			fd.append('fcmPackageName'	, this.fcmPackageName);
			fd.append('fcmServerKey'	, this.fcmServerKey);
			fd.append('senderId'		, this.senderId);
			fd.append('apnsCetification', this.$refs.apnsCetificationFile.files[0]);
			fd.append('iosAppId'		, this.iosAppId);
			fd.append('teamKey'			, this.teamKey);
			fd.append('keyId'			, this.keyId);

			await axios.post('/projectApi/channel/savePushManage',
			fd, {
				headers: {
				'Content-Type': 'multipart/form-data'
				}
			}
			).then( response => {
				var result = response.data;
				if(result.success) {
					confirm.fnAlert("", "저장에 성공했습니다.");
					// 목록 버튼
					this.$refs.backBtn.click();
				} else {
					confirm.fnAlert("", result.message);
				}
			})
			.catch(function () {
				confirm.fnAlert("", "저장에 실패했습니다.???");
			});
		},
	}
}
</script>