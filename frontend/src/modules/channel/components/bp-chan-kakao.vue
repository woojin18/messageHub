<template>
	<div id="regPopup" class="modal fade modalStyle" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>발신 프로필 추가</h2>
						<hr>
						<div class="consoleCon">
							<div class="of_h">
								<h5 class="inline-block float-left" style="width:25%">플러스 친구 ID *</h5>
								<input type="text" class="inputStyle float-right" style="width:72%" v-model="kkoChId">
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block float-left" style="width:25%">관리자 연락처 *</h5>
								<input type="text" class="inputStyle float-right" style="width:72%" v-model="phoneNumber">
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block float-left" style="width:25%">API KEY *</h5>
								<div class="float-right" style="width:72%">
									<select class="selectStyle2" style="width:100%" v-model="apiKey">
										<option v-for="(option, i) in apiKeyList" v-bind:value="option.apiKey" v-bind:key="i">
											{{ option.apiKey }}
										</option>
									</select>
								</div>
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block float-left" style="width:25%">발신토큰 *</h5>
								<div class="inline-block float-right" style="width:72%">
									<input id="token" type="text" class="inputStyle float-left" style="width:70%" v-model="token">
									<a @click="fnGetSenderKeyToken" class="btnStyle1 borderLightGray backWhite minWidthAuto float-right" style="width:27%">토큰요청</a>
								</div>
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block float-left" style="width:25%">사업자 카테고리 *</h5>
								<div class="float-right" style="width:72%">
									<select class="selectStyle2" style="width:100%" v-model="categoryCode">
										<option v-for="(option, i) in category" v-bind:value="option.code" v-bind:key="i">
											{{ option.name }}
										</option>
									</select>
								</div>
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block float-left" style="width:25%">프로젝트 공용 여부 *</h5>
								<div class="float-right" style="width:72%">
									<input type="radio" v-model="otherProjectYn" name="otherProjectYn" value="Y" class="cBox" id="Y"> <label for="Y" class="payment mr30 font-size12">공용</label>
									<input type="radio" v-model="otherProjectYn" name="otherProjectYn" value="N" class="cBox" id="N"> <label for="N" class="payment font-size12">전용</label>
								</div>
							</div>
						</div>
					
					</div>
					<div class="text-center mt20">
						<a @click="fnSave" class="btnStyle1 backBlack ml10" data-toggle="modal">저장</a>
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
		categoryCode : '',
		otherProjectYn : 'Y'
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
				this.category = result.data.data;
				this.categoryCode = result.data.data[0].code
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
				confirm.fnAlert("", "토큰 발급에 실패했습니다.");
			}
		});
	},
	fnSave(){
		if( this.tokenYn != 'Y' ){
			confirm.fnAlert("", "토큰 발급 후, 진행해주세요.");
			return;
		} else if( this.kkoChId === '' ){
			confirm.fnAlert("", "플러스 친구 ID를 입력해주세요.");
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
		}

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
				confirm.fnAlert("", "저장에 성공했습니다.");
				this.tokenYn = 'N';
			} else {
				confirm.fnAlert("", result.message);
			}
		});
	}
  }
}
</script>