<template>
	<!-- Register Modal -->
	<div class="modal modalStyle" id="reCorpPop" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:650px">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>하위고객 브랜드 수신 등록</h2>
						<hr>
                    </div>
					<div class="of_h">
                        <div class="menuBox">						
                            <p class="color1"><i class="far fa-info-circle vertical-baseline"></i> 하위고객 브랜드를 가져오기 위해서는 사전에 RBC에서 하위고객 브랜드에 유플러스와 재판매업체를 대행사로 지정해야 합니다.</p>
                            <p class="color4"><i class="far fa-info-circle vertical-baseline"></i> 하위고객 브랜드는 RBC 로그인 ID와 RBC API Key를 입력하시고 [인증]버튼을 눌러 인증을 하셔야 등록 하실 수 있습니다.</p>
                            <p class="color4"><i class="far fa-info-circle vertical-baseline"></i> [하위고객 브랜드 수신 제외]는 하위고객의 브랜드를 제외하고 가져오게 됩니다.</p>
                            <p class="color4"><i class="far fa-info-circle vertical-baseline"></i> [설정 등록] 버튼을 클릭하면 바로 하위고객 브랜드 정보를 가져오고 다음 날 새벽부터 변경된 브랜드 정보를 싱크하게 됩니다.</p>
                            <p class="color4"><i class="far fa-info-circle vertical-baseline"></i> 하위고객 브랜드를 가져오면 브랜드, 메시지베이스, 챗봇 그리고 문자발신번호가 메시지허브에 등록됩니다.</p>
                        </div>
                    </div>
                    <div class="of_h consolMarginTop">
                        <h5 class="inline-block" style="width:20%;">RBC 로그인 ID</h5>
                        <input type="text" v-model="rbcLoginId" class="inputStyle" style="width:60%">
                    </div>
                    <div class="of_h consolMarginTop">
                        <h5 class="inline-block" style="width:20%;">RBC API Key</h5>
                        <input type="text" v-model="rbcApiKey" class="inputStyle" style="width:60%">
                        <a @click.prevent="fnCertify" class="btnStyle1 backWhite ml20"  title="닫기">인증</a>
                    </div>
                    <div class="of_h consolMarginTop">
                        <h5 class="inline-block" style="width:20%;">브랜드 제외</h5>
                        <a @click.prevent="fnRemoveSubCorpList" class="btnStyle1 backGray" style="width:70%" title="닫기">하위고객 브랜드 수신 제외</a>
                    </div>
					<div class="text-center mt20">
						<a @click.prevent="fnSetRegi" v-if="confYn=='N'" class="btnStyle1 backRed mr20"  title="닫기">설정 등록</a>
						<a @click.prevent="fnSetClear" v-if="confYn=='Y'" class="btnStyle1 backRed mr20"  title="닫기">설정 해제</a>
						<a @click.prevent="fnClose" class="btnStyle1 backWhite"  title="닫기">닫기</a>
					</div>
				</div>
			</div>
		</div>
		<removeSubCorpPop :setRbcLoginId="setRbcLoginId" :setRbcApiKey="setRbcApiKey" :removeCorpPopCnt="removeCorpPopCnt"></removeSubCorpPop>
	</div>
</template>

<script>
import api from '../service/api'
import confirm from '@/modules/commonUtil/service/confirm'
import removeSubCorpPop from './bp-chan-removeSubCorpPop.vue';


export default {
	name: 'reCorpPop',
	components: {
		removeSubCorpPop
	},
	props: {
		reCorpPopCnt: {
			type: Number,
			require: true,
			default: false
		},
		projectId: {
			type: String,
			require: true,
			default: false
		}
        
	},
	data() {
		return {
			removeCorpPopCnt : 0,
			contentTitle : "하위고객 브랜드 수신 등록",
			certify : false,			// RBC 인증 check
			confYn : "N",				// N인경우 설정등록 버튼, Y인경우 설정 해제 버튼 출력
			subCorpInsert : "N",		// N인경우 설정등록시 insert, Y인경우 설정등록시 update 처리
            rbcLoginId : "",			// input 입력 RBC 로그인 ID
            rbcApiKey : "",				// input 입력 API KEY
			setRbcLoginId : "",			// 인증 완료된 RBC 로그인 ID
			setRbcApiKey : "",			// 인증 완료된 API KEY
		}
	},
	watch: {
		reCorpPopCnt() {	
            this.setSubCorpBrandConf();
		}
	},
	methods: {
		// 닫기
		fnClose(){
			this.fnInit();
			jQuery('#reCorpPop').modal('hide');
		},
        // 해당 고객 브랜드 수신 상태 체크
        setSubCorpBrandConf() {
			var vm = this;
            var params = {};

            api.selectSubCorpBrandConf(params).then(response =>{
                var result = response.data;
                if(result.success) {
                    var data = result.data;
					vm.subCorpInsert = data.subCorpInsert;
					vm.confYn = data.confYn;
                }
            });
        },
		// 입력값 초기화
		fnInit() {
			this.certify = false;
			this.confYn = "N";
			this.subCorpInsert = "N";
			this.rbcLoginId = "";
            this.rbcApiKey = "";
			this.setRbcLoginId = "";
			this.setRbcApiKey = "";	
		},
        fnCertify() {
			var vm = this;
            var rbcLoginId = this.rbcLoginId;
            var rbcApiKey = this.rbcApiKey;
			var projectId = this.projectId;

			if(rbcLoginId == "") {
				confirm.fnAlert(vm.contentTitle, "인증하실 RBC 로그인 ID를 입력해 주세요.");
				return false;
			}
			if(rbcApiKey == "") {
				confirm.fnAlert(vm.contentTitle, "인증하실 RBC API KEY를 입력해 주세요.");
				return false;
			}

			var params = {
				"rbcLoginId" : rbcLoginId,
				"rbcApiKey" : rbcApiKey,
				"projectId" : projectId
			}

			api.checkRbcCertify(params).then(response =>{
                var result = response.data;
                if(result.success) {
					confirm.fnAlert(vm.contentTitle, "인증이 완료되었습니다.");
					vm.setRbcLoginId = vm.rbcLoginId;
					vm.setRbcApiKey = vm.rbcApiKey;
					vm.certify = true;
                } else {
					confirm.fnAlert(vm.contentTitle, result.message);
				}
            });

        },
		// 설정 등록
		fnSetRegi() {
			var vm = this;
			var certify = vm.certify;
			if(!certify) {
				confirm.fnAlert(vm.contentTitle, "RBC 인증처리를 해주세요.");
				return false;
			}

			var params = {
				"confYn" : vm.confYn,						// N인경우 설정등록 버튼, Y인경우 설정 해제 버튼 출력
				"subCorpInsert" : vm.subCorpInsert,			// N인경우 설정등록시 insert, Y인경우 설정등록시 update 처리
				"setRbcLoginId" : vm.setRbcLoginId,			// 인증 완료된 RBC 로그인 ID
				"setRbcApiKey" : vm.setRbcApiKey,			// 인증 완료된 API KEY
			}

			api.setSubCorpSync(params).then(response =>{
                var result = response.data;
                if(result.success) {
					confirm.fnAlert(vm.contentTitle, "하위고객 브랜드 설정 등록처리가 완료되었습니다.");
					vm.fnClose();
                } else {
					confirm.fnAlert(vm.contentTitle, result.message);
				}
            });
		}, 
		// 설정 해제
		fnSetClear() {
			var vm = this;
			var certify = vm.certify;
			if(!certify) {
				confirm.fnAlert(vm.contentTitle, "RBC 인증처리를 해주세요.");
				return false;
			}

			var params = {
				"confYn" : vm.confYn,						// N인경우 설정등록 버튼, Y인경우 설정 해제 버튼 출력
				"subCorpInsert" : vm.subCorpInsert,			// N인경우 설정등록시 insert, Y인경우 설정등록시 update 처리
				"setRbcLoginId" : vm.setRbcLoginId,			// 인증 완료된 RBC 로그인 ID
				"setRbcApiKey" : vm.setRbcApiKey,			// 인증 완료된 API KEY
			}

			api.setSubCorpSync(params).then(response =>{
                var result = response.data;
                if(result.success) {
					confirm.fnAlert(vm.contentTitle, "하위고객 브랜드 설정 해제처리가 완료되었습니다.");
					vm.fnClose();
                } else {
					confirm.fnAlert(vm.contentTitle, result.message);
				}
            });
		},

		// 하위고객 브랜드 수신 제외
		fnRemoveSubCorpList() {
			var vm = this;
			var certify = vm.certify;
			if(!certify) {
				confirm.fnAlert(vm.contentTitle, "RBC 인증처리를 해주세요.");
				return false;
			}

			this.removeCorpPopCnt = this.removeCorpPopCnt+1;
      		jQuery("#removeSubCorpPop").modal("show");

		}
	}
}
</script>