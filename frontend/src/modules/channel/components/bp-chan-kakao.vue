<template>
	<div id="regPopup" class="modal modalStyle" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:750px">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>카카오 채널 추가</h2>
						<hr>
						<div class="consoleCon">
							<div class="of_h">
								<h5 class="inline-block float-left" style="width:25%">채널 검색용 아이디 *</h5>
								<input type="text" class="inputStyle float-right" style="width:72%" v-model="kkoChId" placeholder="카카오에 등록된 검색용아이디를 넣어주세요. 예) @servicename">
							</div>
							<div class="clear consolMarginTop">
								<h5 class="inline-block float-left" style="width:25%">관리자 연락처 * <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:320px">카카오 비즈니스 관리자 센터에서 입력한 카카오채널ID에<br>관리자로 등록되어 있는 사용자 연락처입니다.</span></i></h5>
								<input type="text" class="inputStyle float-right" style="width:72%" placeholder="카카오 채널에 등록된 관리자 연락처" v-model="phoneNumber">
							</div>
							<div class="of_h consolMarginTop" style="display:none">
								<h5 class="inline-block float-left" style="width:25%">API KEY *</h5>
								<div class="float-right" style="width:72%">
									<select class="selectStyle2" style="width:100%" v-model="apiKey">
										<option v-for="(option, i) in apiKeyList" v-bind:value="option.apiKey" v-bind:key="i">
											{{ option.apiKey }}
										</option>
									</select>
								</div>
							</div>
							<div class="clear consolMarginTop">
								<h5 class="inline-block float-left" style="width:25%">인증번호 * <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:290px">관리자연락처 입력 후 토큰요청을 하시면<br>카카오톡으로 토큰정보를 수신받을 수 있습니다.<br>받은 토큰정보를 입력해 주십시오.</span></i></h5>
								<div class="inline-block float-right" style="width:72%">
									<input id="token" type="text" class="inputStyle float-left" style="width:70%" v-model="token">
									<a @click="fnGetSenderKeyToken" class="btnStyle1 borderLightGray backWhite minWidthAuto float-right" style="width:27%">토큰요청</a>
								</div>
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block float-left" style="width:25%">사업자 카테고리 *</h5>
								<div class="float-right" style="width:72%">
									<select class="selectStyle2" style="width:32%" @change="fnCate1Code" v-model="cate1Code">
										<option value="">선택</option>
										<option v-for="(option, i) in cate1" v-bind:value="option.code" v-bind:key="i">
											{{ option.name }}
										</option>
									</select>&nbsp;
									<select class="selectStyle2" style="width:32%" @change="fnCate2Code" v-model="cate2Code">
										<option value="">선택</option>
										<option v-for="(option, i) in cate2" v-bind:value="option.code" v-bind:key="i">
											{{ option.name }}
										</option>
									</select>&nbsp;
									<select class="selectStyle2" style="width:33%" v-model="categoryCode">
										<option value="">선택</option>
										<option v-for="(option, i) in cate3" v-bind:value="option.code" v-bind:key="i">
											{{ option.name }}
										</option>
									</select>
								</div>
							</div>
						</div>
					
					</div>
					<div class="text-center mt20">
						<a @click="fnSaveVali" class="btnStyle1 backBlack ml10" data-toggle="modal">저장</a>
						<a @click="fnClose" class="btnStyle1 backWhite" data-dismiss="modal">닫기</a>						
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import api from '../service/api'
import confirm from "@/modules/commonUtil/service/confirm"
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  name: 'bpChanKakao',
  data() {
    return {
		tokenYn : 'N',
		kkoChId : '',
		phoneNumber : '',
		token : '',
		apiKeyList : [],
		apiKey : '',
		category : [],
		cate1 : [],
		cate2 : [],
		cate3 : [],
		cate1Code : '',
		cate2Code : '',
		categoryCode : '',
		otherProjectYn : 'N'
    }
  },
  props: {
    save_status: {
      type: String,
      require: true,
      default: 'C'
    },
    row_data : {
      type: Object,
      require: false,
    },
    projectId : {
      type : String,
      require : true
    }
  },
  watch: {
    row_data: function(newVal, oldVal) {
		this.kkoChId = newVal.kkoChId;
		this.phoneNumber = newVal.phoneNumber;
		this.token = newVal.token;
		this.cate1Code = '';
		this.cate2Code = '';
		this.categoryCode = '';

		this.fnGetApiKeyListForKko();
    }
  },
  mounted() {
    this.fnGetKkoCategory();
	this.otherProjectYn = 'Y';
  },
  methods: {
    // 닫기
    fnClose(){
      jQuery("#regPopup").modal('hide');
    },
	// API KEY 리스트 불러오기
	fnGetApiKeyListForKko(){
		var params = {
			"projectId" : this.projectId
		};
		api.getApiKeyListForKko(params).then(response =>{
			var result = response.data;
			if( result.success ){
				this.apiKeyList = result.data;
				this.apiKey = result.data[0].apiKey
			} else {
				confirm.fnAlert("", "API KEY 리스트 불러오기에 실패했습니다.");
			}
		});
	},
	// 카카오톡 카테고리 불러오기
	fnGetKkoCategory(){
		var params = {};
		api.getKkoCategory(params).then(response =>{
			var result = response.data;
			if( result.success ){
				this.category = result.data;
				this.cate1 = this.category.root.childs;
				//this.cate1Code = this.cate1[0].code;
			} else {
				confirm.fnAlert("", "카테고리 불러오기에 실패했습니다.");
			}
		});
	},
	fnGetSenderKeyToken(){
		var params = {
			"apiKey"		: this.apiKey,
			"kkoChId"		: this.kkoChId,
			"phoneNumber"	: this.phoneNumber,
		};
		api.getSenderKeyToken(params).then(response =>{
			var result = response.data;
			if( result.success ){
				this.tokenYn = 'Y';
				confirm.fnAlert("", "토큰이 발급되었습니다. 토큰을 입력해주세요. ");
			} else {
				confirm.fnAlert("", result.message);
			}
		});
	},
	fnSave(){
		eventBus.$on('callbackEventBus', this.fnSaveObj);
		confirm.fnConfirm("", "선택한 카테고리 정보가 최초 등록된 카테고리 정보와 다를 경우 자동 변경됩니다. 저장하시겠습니까?", "확인");
	},
	fnSaveObj() {
		var params = {
			"sts"			: this.save_status,
			"apiKey"		: this.apiKey,
			"kkoChId"		: this.kkoChId,
			"phoneNumber"	: this.phoneNumber,
			"token"			: this.token,
			"categoryCode"	: this.categoryCode,
			"projectId"		: this.projectId,
			"otherProjectYn": this.otherProjectYn
		};

		api.saveKkoChForApi(params).then(response =>{
			var result = response.data;

			if( result.success ){
				if (result.code = 'SS_LOCK') {
					var categoryCode = result.data.data.categoryCode
					//var categoryCode = '01300010001'
					this.cate2 = this.category[categoryCode.substr(0,3)].childs;	
					this.cate3 = this.category[categoryCode.substr(0,7)].childs;	
					this.cate1Code = categoryCode.substr(0,3);
					this.cate2Code = categoryCode.substr(0,7);
					this.categoryCode = categoryCode;
					confirm.fnAlert("", "기존에 등록된 카카오 채널의 카테고리 코드와 일치하지 않습니다.\n최초 등록한 카테고리로 자동세팅합니다.\n다시 한번 저장버튼을 클릭해 주십시오");
					
				} else {
					confirm.fnAlert("", "저장에 성공했습니다.");
					this.tokenYn = 'N';
				}
			} else {
				confirm.fnAlert("", result.message);
			}
		});
	},
	fnSaveVali() {
		// 기본 validation
		if( this.tokenYn != 'Y' ){
			confirm.fnAlert("", "토큰 발급 후, 진행해주세요.");
			return;
		} else if( this.kkoChId === '' ){
			confirm.fnAlert("", "카카오 채널ID를 입력해주세요.");
			return;
		} else if( this.phoneNumber === '' ){
			confirm.fnAlert("", "관리자 연락처를 입력해주세요.");
			return;
		} else if( this.categoryCode === '' ){
			confirm.fnAlert("", "사업자 카테고리를 선택해주세요.");
			return;
		} else if( this.token === '' ){
			confirm.fnAlert("", "발급된 토큰을 입력해주세요.");
			return;
		} else if( this.apiKey === ''){
			confirm.fnAlert("", "API Key가 존재하지 않습니다. API Key를 등록해주세요.");
			return;
		}

		// 중복 여부 확인 후 저장 
		var params = {
			"kkoChId"		: this.kkoChId,
			"projectId"		: this.projectId,
			"otherProjectYn": this.otherProjectYn
		};

		api.chkEqualKakaoChannel(params).then(response => {
			var result = response.data;
			if(result.success) {
				this.fnSave();
			} else {
				confirm.fnAlert("", result.message);
			}
		});
	},
	fnCate1Code: function (event) {
		this.cate2Code = '';
		this.categoryCode = '';
		this.cate2 = [];
		this.cate3 = [];
		if (event.target.value != null && event.target.value != '') {
			this.cate2 = this.category[event.target.value].childs;
			//this.cate2Code = this.cate2[0].code;
		}
	},
	fnCate2Code: function (event) {
		this.categoryCode = '';
		this.cate3 = [];
		if (event.target.value != null && event.target.value != '') {
			this.cate3 = this.category[event.target.value].childs;
			//this.categoryCode = this.cate3[0].code;
		}
	}
  }
}
</script>