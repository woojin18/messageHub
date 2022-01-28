<template>
<div class="modal modalStyle" id="senderUpPopup" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <div>
                    <h2>한도금액 상향 요청</h2>
                    <hr>
                    <div class="color4 bgColor_f1 pd20">
                        <ul class="text-left dotList">
                            <li>현재 월 한도 금액에 대한 상향을 요청합니다.</li>
                            <li>승인을 위해서 상향 근거를 입력해야 합니다.</li>
                            <li>업체의 예상 매출, 신용정보, 담보 등에 따라 요청이 반려될 수 있습니다.</li>
                            <li>요청 후 검토 및 승인/반려 처리까지 약 1~3일 소요됩니다<br>( 처리결과는 요청자에게 SMS로 전달됩니다.)</li>
                        </ul>
                    </div>

                    <div class="of_h mt10">
                        <div class="float-left" style="width:25%"><h5>월 한도금액</h5></div>
                        <div class="float-left" style="width:75%">
                            <h5>{{corpInfo.monSenderLimitAmount | comma}} 원</h5>
                        </div>
                    </div>
                    <div class="of_h mt10">
                        <div class="float-left" style="width:25%"><h5>월 상향 요청 금액 *</h5></div>
                        <div class="float-left" style="width:75%">
                            <input type="text" class="inputStyle" placeholder="숫자만 입력 가능" :disabled="isRead" v-model="info.reqAmount">
                        </div>
                    </div>
                    <div class="of_h mt10">
                        <div class="float-left" style="width:100%"><h5>요청 사유 *</h5></div>
                        <textarea class="textareaStyle" style="width:100%; height:100px" :disabled="isRead" v-model="info.reqReason"></textarea>
                    </div>

                    <h4 v-if="isRead==true && corpInfo.senderUpStatus == 'RET'" class="mt30">반려 사유</h4>
                    <div v-if="isRead==true && corpInfo.senderUpStatus == 'RET'" class="joinBox">		
                        <div class="color7" style="width:100%" v-html="corpInfo.senderUpReason"></div>
                    </div>
                    
                    <div class="text-center mt40">
                        <a v-if="isRead==false" @click="fnConfirm" class="btnStyle2 backBlack font14">상향 요청</a>
                        <a v-if="isRead==true && corpInfo.senderUpStatus == 'RET'" @click="fnDel" class="btnStyle2 backGray font14">삭제</a>
                        <a @click="fnClose" class="btnStyle2 backWhite font14">닫기</a>			
                    </div>
                
                </div>
            </div>
        </div>
    </div>
    </div>
</template>
<script>
import homeApi from '@/modules/acHome/service/api';
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
    name : 'senderUpPopup',
	components: {
	},
	filters: {
		comma (val) {
			return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ',')
		}
	},
    props : {
        popReset : {
            type : Number,
            require : false
        },
        isRead : {
            type : Boolean,
            require : false
        },
        corpInfo : {
            type : Object,
            require : false
        }
    },
    data(){
        return {
			popReset1 : 0,				// 팝업 초기화할 num
            info : {
                reqAmount : ''
              , reqReason : ''
            }
        }
    },
    watch : {
        popReset() {
            if (this.isRead) {
                this.info.reqAmount = this.corpInfo.reqAmount;
                this.info.reqReason = this.corpInfo.reqReason;
            } else {
                this.info.reqAmount = "";
                this.info.reqReason = "";
            }
        },
		'info.reqAmount'() {
            if (this.info.reqAmount == null) return;
			return this.info.reqAmount = this.info.reqAmount.toString().replace(/[^0-9]/g, '');
		},
    },
	mounted() {
    },
    methods : {
        fnConfirm(){
            if(this.info.reqAmount == ""){
                confirm.fnAlert("", "월 상향 요청 금액을 입력해주세요.");
                return;
            }
            if(this.info.reqReason == ""){
                confirm.fnAlert("", "요청 사유를 입력해주세요.");
                return;
            }
			eventBus.$on('callbackEventBus', this.fnConfirmCallback);
			confirm.fnConfirm('한도금액 상향 요청', '한도금액 상향 요청하시겠습니까?', '확인');
        },
        fnConfirmCallback(){
            let params = Object.assign({}, this.info)
			homeApi.saveSenderUp(params).then(response =>{
				var result = response.data;
				if(result.success) {
					confirm.fnAlert('한도금액 상향 요청', '한도금액 상향 요청이 정상적으로 되었습니다.');
                    this.corpInfo.senderUpStatusNm = "한도금액 상향 요청 중"
                    this.corpInfo.senderUpStatus = 'REQ';
                    this.corpInfo.reqAmount = this.info.reqAmount ;
                    this.corpInfo.reqReason = this.info.reqReason ;
                    jQuery("#senderUpPopup").modal("hide")
				} else {
					confirm.fnAlert('한도금액 상향 요청', result.message);
				}
			});
        },
        fnDel(){
			eventBus.$on('callbackEventBus', this.fnDelCallback);
			confirm.fnConfirm('한도금액 상향 요청', '정말로 삭제하시겠습니까?', '확인');
        },
        fnDelCallback(){
			homeApi.delSenderUp({}).then(response =>{
				var result = response.data;
				if(result.success) {
					confirm.fnAlert('한도금액 상향 요청', '정상적으로 삭제 되었습니다.');
                    this.corpInfo.senderUpStatus = null;
                    this.corpInfo.senderUpStatusNm = null;
                    jQuery("#senderUpPopup").modal("hide")
				} else {
					confirm.fnAlert('한도금액 상향 요청', result.message);
				}
			});
        },
        fnClose() {
            jQuery("#senderUpPopup").modal("hide")
        }
    }
}
</script>