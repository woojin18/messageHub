<template>
<div class="modal modalStyle" id="billPopup" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <div>
                    <h2>청구정보</h2>
                    <hr>
                    <h4 v-if="bill.billType == 'PROJECT'" class="mt10">월 발송 예상 건수</h4>
                    <div v-if="bill.billType == 'PROJECT'" class="joinBox mt10">							
                        <div class="of_h">
                            <div class="float-left" style="width:45%">
                                <div class="float-left" style="width:30%"><h5>문자</h5></div>
                                <div class="float-left" style="width:70%">
                                    <input type="text" class="inputStyle" placeholder="발송 건수(숫자만 입력)" :disabled="isRead" v-model="bill.smsExpCnt">
                                </div>
                            </div>
                            <div class="float-right" style="width:45%">
                                <div class="float-left" style="width:30%"><h5>RCS</h5></div>
                                <div class="float-left" style="width:70%">
                                    <input type="text" class="inputStyle" placeholder="발송 건수(숫자만 입력)" :disabled="isRead" v-model="bill.rcsExpCnt">
                                </div>
                            </div>
                        </div>
                        <div class="of_h mt10">
                            <div class="float-left" style="width:45%">
                                <div class="float-left" style="width:30%"><h5>카카오</h5></div>
                                <div class="float-left" style="width:70%">
                                    <input type="text" class="inputStyle" placeholder="발송 건수(숫자만 입력)" :disabled="isRead" v-model="bill.kkoExpCnt">
                                </div>
                            </div>
                            <div class="float-right" style="width:45%">
                                <div class="float-left" style="width:30%"><h5>Push</h5></div>
                                <div class="float-left" style="width:70%">
                                    <input type="text" class="inputStyle" placeholder="발송 건수(숫자만 입력)" :disabled="isRead" v-model="bill.pushExpCnt">
                                </div>
                            </div>
                        </div>
                        <div class="color4 mt15" style="width:100%">월 한도금액 제한 기준을 위해 메시지 발송 예상 건수를 받습니다.<br>기본 월 한도 금액은 100만원으로 제한합니다. <br>만약 100만원 초과 한도를 설정하시려면 고객센터로 문의 바랍니다.<br>(신용정보, 담보 등에 따라 초고 가능)</div>
                    </div>
                    <div v-if="bill.billType == 'DEPART'" class="of_h mt10">
                        <div class="float-left" style="width:22%"><h5>청구계정 명*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="text" class="inputStyle" placeholder="청구계정 명" :disabled="isRead" v-model="bill.billName">
                        </div>
                    </div>
                    <div class="of_h mt10">
                        <div class="float-left" style="width:22%"><h5>청구서 수신</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="radio" name="sms_agree" value="Y" :disabled="isRead" v-model="bill.billKind" id="sms_agree05"> <label for="sms_agree05" class="mr30">이메일</label>
                            <input type="radio" name="sms_agree" value="C" :disabled="isRead" v-model="bill.billKind" id="sms_agree06"> <label for="sms_agree06" class="mr30">문자</label>
                            <input type="radio" name="sms_agree" value="N" :disabled="isRead" v-model="bill.billKind" id="sms_agree07"> <label for="sms_agree07">우편</label>
                        </div>
                    </div>
                    <div class="of_h mt10">
                        <div class="float-left" style="width:22%"><h5>청구 사업자번호*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="text" class="inputStyle" placeholder="사업자번호(-없이 입력)" :disabled="isRead" v-model="bill.billRegNo">
                        </div>
                    </div>
                    <div class="of_h mt10" v-show="bill.billKind == 'C'">
                        <div class="float-left" style="width:22%"><h5>청구서 수신 휴대폰*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="text" class="inputStyle" placeholder="휴대폰번호(-없이 입력)" :disabled="isRead" v-model="bill.billTelNo">
                        </div>
                    </div>
                    <div class="of_h mt10" v-show="bill.billKind == 'Y'">
                        <div class="float-left" style="width:22%"><h5>청구 이메일*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="text" class="inputStyle" placeholder="ex) example@uplus.co.kr" :disabled="isRead" v-model="bill.billEmail">
                        </div>
                    </div>

                    <div class="of_h mt10" v-show="bill.billKind == 'N'">
                        <div class="float-left" style="width:22%"><h5>청구 주소*</h5></div>
                        <div class="float-left" style="width:78%">
                            <div class="float-left" style="width:72%"><input type="text" class="inputStyle" disabled="disabled" v-model="bill.billZip"></div>
                            <div class="float-right" style="width:25%"><a class="btnStyle1 backLightGray" style="min-width:auto; width:100%" @click="fnAddrPopup1">우편번호</a></div>	
                        </div>
                        <div class="float-right mt5" style="width:78%"><input type="text" class="inputStyle" disabled="disabled" v-model="bill.billJuso"></div>
                        <div class="float-right mt5" style="width:78%"><input type="text" class="inputStyle" placeholder="상세주소 입력" :disabled="isRead" v-model="bill.billJuso2"></div>
                    </div>
                    <div class="of_h mt10">
                        <div class="float-left" style="width:22%"><h5>납부고객구분*</h5></div>
                        <div class="float-left" style="width:78%">
                            <select class="selectStyle2" style="width:50%" title="납부고객구분 선택란" :disabled="isRead" v-model="bill.napCustKdCd" @change="fnCngCertInfo">
                                <option value="">선택하세요</option>
                                <option  v-for="(row, index) in napCustKdCdArr" :key="index" :value="row.codeVal1"> {{ row.codeName1 }} </option>
                            </select>
                        </div>
                    </div>
                    <div class="of_h mt10">
                        <div class="float-left" style="width:22%"><h5>납부방법*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="radio" name="pay02" value="CM" :disabled="isRead" v-model="bill.payMthdCd" id="pay04" @change="fnCngCertInfo"> <label for="pay04" class="mr30">자동이체</label>
                            <input type="radio" name="pay02" value="CC" :disabled="isRead" v-model="bill.payMthdCd" id="pay05" @change="fnCngCertInfo"> <label for="pay05" class="mr30">신용카드</label>
                            <input type="radio" name="pay02" value="GR" :disabled="isRead" v-model="bill.payMthdCd" id="pay06" @change="fnCngCertInfo"> <label for="pay06">지로</label>
                        </div>
                    </div>
                    <div class="of_h mt10" v-show="bill.payMthdCd != 'GR'">
                        <div class="float-left" style="width:22%"><h5>납부일*</h5></div>
                        <div class="float-left" style="width:78%">
                            <select class="selectStyle2" style="width:50%" title="납부일 선택란" :disabled="isRead" v-model="bill.payDt">
                                <option value="">선택하세요</option>
                                <option value="18">18일</option>
                                <option value="22">22일</option>
                                <option value="26">26일</option>
                                <option value="LD">말일</option>
                            </select>
                        </div>
                    </div>
                    <div class="of_h mt10">
                        <div class="float-left" style="width:22%"><h5>납부자명*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="text" class="inputStyle" placeholder="납부자명" :disabled="isRead" v-model="bill.napCmpNm" @change="fnCngCertInfo">
                        </div>
                    </div>
                    <div class="of_h mt10">
                        <div class="float-left" style="width:22%"><h5>생년월일/<br>사업자번호*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="text" class="inputStyle" placeholder="생년월일(YYYYMMDD)/사업자번호" :disabled="isRead" v-model="bill.napJumin" @change="fnCngCertInfo" maxlength="12">
                        </div>
                    </div>
                    <div class="of_h mt10" v-show="bill.payMthdCd == 'CM'">
                        <div class="float-left" style="width:22%"><h5>은행*</h5></div>
                        <div class="float-left" style="width:78%">
                            <select class="selectStyle2" style="width:50%" title="은행 선택란" :disabled="isRead" v-model="bill.bankCd" @change="fnCngCertInfo">
                                <option value="">선택하세요</option>
                                <option  v-for="(row, index) in bankCdArr" :key="index" :value="row.codeVal1">{{ row.codeName1 }}</option>
                            </select>
                        </div>
                    </div>
                    <div class="of_h mt10" v-show="bill.payMthdCd == 'CM'">
                        <div class="float-left" style="width:22%"><h5>계좌번호*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="text" class="inputStyle" placeholder="계좌번호(- 제외)" :disabled="isRead" v-model="bill.bankNo" @change="fnCngCertInfo">
                            <a v-if="isRead == false" @click="fnBank" class="btnStyle1 backRed vertical-middle mt10" style="min-width:auto; width:24.5%">계좌 인증</a>
                        </div>
                    </div>

                    <div class="of_h mt10" v-show="bill.payMthdCd == 'CC'">
                        <div class="float-left" style="width:22%"><h5>카드종류*</h5></div>
                        <div class="float-left" style="width:78%">
                            <select class="selectStyle2" style="width:50%" title="카드종류 선택란" :disabled="isRead" v-model="bill.cardCd" @change="fnCngCertInfo">
                                <option value="">선택하세요</option>
                                <option  v-for="(row, index) in cardCdArr" :key="index" :value="row.codeVal1">{{ row.codeName1 }}</option>
                            </select>
                        </div>
                    </div>
                    <div class="of_h mt10" v-show="bill.payMthdCd == 'CC'">
                        <div class="float-left" style="width:22%"><h5>카드번호*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="text" class="inputStyle" style="width:24.5%" :disabled="isRead" v-model="bill.cardNo1" @change="fnCngCertInfo" maxlength="4">
                            <input type="password" class="inputStyle" style="width:24.5%" :disabled="isRead" v-model="bill.cardNo2" @change="fnCngCertInfo" maxlength="4">
                            <input type="password" class="inputStyle" style="width:24.5%" :disabled="isRead" v-model="bill.cardNo3" @change="fnCngCertInfo" maxlength="4">
                            <input type="text" class="inputStyle" style="width:24.5%" :disabled="isRead" v-model="bill.cardNo4" @change="fnCngCertInfo" maxlength="4">
                        </div>
                    </div>
                    <div class="of_h mt10" v-show="bill.payMthdCd == 'CC'">
                        <div class="float-left" style="width:22%"><h5>카드유효기간*</h5></div>
                        <div class="float-left" style="width:78%">
                            <div class="float-left" style="width:50%">
                                <div class="float-left" style="width:49%">
                                    <input type="text" class="inputStyle" placeholder="4자리" :disabled="isRead" v-model="bill.cardValdEndYymm1" @change="fnCngCertInfo" maxlength="4">
                                </div>
                                <div class="float-left ml10" style="width:10%"><h5>년</h5></div>
                            </div>
                            <div class="float-right" style="width:50%">
                                <div class="float-left" style="width:49%">
                                    <input type="text" class="inputStyle" placeholder="2자리" :disabled="isRead" v-model="bill.cardValdEndYymm2" @change="fnCngCertInfo" maxlength="2">
                                </div>
                                <div class="float-left ml10" style="width:10%"><h5>월</h5></div>
                            </div>
                            <a v-if="isRead == false" @click="fnCard" class="btnStyle1 backRed vertical-middle mt10" style="min-width:auto; width:24.5%">카드 인증</a>
                        </div>
                    </div>
                    <h4 v-if="bill.billStatus == 'RET'" class="mt40">반려 사유</h4>
                    <div v-if="bill.billStatus == 'RET'" class="joinBox">		
                        <div class="color7" style="width:100%" v-html="bill.handleReason"></div>
                    </div>
                    <div v-if="isRead == false" class="joinBox mt10" v-show="bill.payMthdCd != 'GR'">	
                        <div class="checks">
                            <input type="checkbox" id="chkID" class="checkStyle2" v-model="bill.isAgree"><label for="chkID">본인은 서비스 이용요금을 매월 본인 또는 법인의 계좌나 카드에서 지정된 출금일 또는<br>결제일에 인출 하는 것에 동의합니다.<br>
                            본인은 LG유플러스가 이용요금 청구 및 수납을 위해 뒷면에 기재된 개인정보 활용동의서의 내용에 따라 위의 정보를 수집 후 이용 또는 정보 위탁하는 것에 동의한다.</label>	
                        </div>
                    </div>

                    <div class="text-center mt40">
                        <a v-show="bill.isCert" @click="fnConfirm" class="btnStyle3 black font14">확인</a>
                        <a v-if="isRead==true && corpInfo.billStatus == 'RET'" @click="fnDel" class="btnStyle2 backGray font14">삭제</a>
                        <a @click="fnClose"   class="btnStyle3 white font14">닫기</a>						
                    </div>
                    <addrPopup :popReset="popReset1"  :selAddr.sync="selAddr"></addrPopup>
                </div>
            </div>
        </div>
    </div>
</div>
</template>
<script>
import addrPopup from "@/modules/signUp/components/bp-addrPopup1"
import customereApi from "@/modules/customer/service/customerApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import signUpApi from "@/modules/signUp/service/api";
import homeApi from '@/modules/acHome/service/api';

export default {
    name : 'billPopup',
	components: {
		addrPopup
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
			bill : {
				  billId : ''
                , billRegNo : ''
				, billType : ''
				, billName : ''
				, billStatus : ''
				, napCustKdCd : ''
				, billKind : 'Y'
				, billEmail : ''
				, billTelNo : ''
				, billZip : ''
				, billJuso : ''
				, billJuso2 : ''
				, payMthdCd : 'CM'
				, payDt : ''
				, napCmpNm : ''
				, napJumin : ''
				, bankCd : ''
				, bankNo : ''
				, cardCd : ''
				, cardNo1 : ''
				, cardNo2 : ''
				, cardNo3 : ''
				, cardNo4 : ''
				, cardValdEndYymm1 : ''
				, cardValdEndYymm2 : ''
				, serviceId : ''
				, smsExpCnt : null
				, rcsExpCnt : null
				, kkoExpCnt : null
				, pushExpCnt : null
				, monthExpAmount : 0
				, handleReason : ''
				, handleId : ''
				, handleDt : ''
                , isCert : false
                , isAgree : false
			},
			napCustKdCdArr : [],
			bankCdArr : [],
			cardCdArr : [],
			selAddr : {},				// 선택한 주소 정보
			popReset1 : 0,				// 팝업 초기화할 num
        }
    },
    watch : {
        popReset() {
            this.fnGetBill();
        },
		selAddr() {
			this.fnSetCorpAddr();
		},
		'bill.smsExpCnt'() {
			return this.bill.smsExpCnt = this.bill.smsExpCnt.toString().replace(/[^0-9]/g, '');
		},
		'bill.rcsExpCnt'() {
			return this.bill.rcsExpCnt = this.bill.rcsExpCnt.toString().replace(/[^0-9]/g, '');
		},
		'bill.kkoExpCnt'() {
			return this.bill.kkoExpCnt = this.bill.kkoExpCnt.toString().replace(/[^0-9]/g, '');
		},
		'bill.pushExpCnt'() {
			return this.bill.pushExpCnt = this.bill.pushExpCnt.toString().replace(/[^0-9]/g, '');
		},
		'bill.billRegNo'() {
			return this.bill.billRegNo = this.bill.billRegNo.replace(/[^0-9]/g, '');
		},
		'bill.billTelNo'() {
            if (this.bill.billTelNo == null) return;
			return this.bill.billTelNo = this.bill.billTelNo.replace(/[^0-9]/g, '');
		},
		'bill.napJumin'() {
			return this.bill.napJumin = this.bill.napJumin.replace(/[^0-9]/g, '');
		},
		'bill.bankNo'() {
            if (this.bill.bankNo == null) return;
			return this.bill.bankNo = this.bill.bankNo.replace(/[^0-9]/g, '');
		},
		'bill.cardNo1'() {
            if (this.bill.cardNo1 == null) return;
			return this.bill.cardNo1 = this.bill.cardNo1.replace(/[^0-9]/g, '');
		},
		'bill.cardNo2'() {
            if (this.bill.cardNo2 == null) return;
			return this.bill.cardNo2 = this.bill.cardNo2.replace(/[^0-9]/g, '');
		},
		'bill.cardNo3'() {
            if (this.bill.cardNo3 == null) return;
			return this.bill.cardNo3 = this.bill.cardNo3.replace(/[^0-9]/g, '');
		},
		'bill.cardNo4'() {
            if (this.bill.cardNo4 == null) return;
			return this.bill.cardNo4 = this.bill.cardNo4.replace(/[^0-9]/g, '');
		},
		'bill.cardValdEndYymm1'() {
            if (this.bill.cardValdEndYymm1 == null) return;
			return this.bill.cardValdEndYymm1 = this.bill.cardValdEndYymm1.replace(/[^0-9]/g, '');
		},
		'bill.cardValdEndYymm2'() {
            if (this.bill.cardValdEndYymm2 == null) return;
			return this.bill.cardValdEndYymm2 = this.bill.cardValdEndYymm2.replace(/[^0-9]/g, '');
		}
    },
	mounted() {
        this.fnGetCode();
    },
    methods : {
		fnGetCode(){
			var params2 = {
				codeTypeCd	: "NAP_CUST_KD",
				useYN		: "Y"
			};
			customereApi.selectCodeList(params2).then(response =>{
				var result = response.data;
				if(result.success){
					if(result.data.length > 0){
						this.napCustKdCdArr = result.data;
					}
				}
			});
			var params3 = {
				codeTypeCd	: "BANK",
				useYN		: "Y"
			};
			customereApi.selectCodeList(params3).then(response =>{
				var result = response.data;
				if(result.success){
					if(result.data.length > 0){
						this.bankCdArr = result.data;
					}
				}
			});
			var params4 = {
				codeTypeCd	: "CARD",
				useYN		: "Y"
			};
			customereApi.selectCodeList(params4).then(response =>{
				var result = response.data;
				if(result.success){
					if(result.data.length > 0){
						this.cardCdArr = result.data;
					}
				}
			});
		},
        fnGetBill() {
            if (this.isRead == false) {
                this.bill = {
                      billId : this.corpInfo.billId
                    , billRegNo : ''
                    , billType : 'PROJECT'
                    , billName : this.corpInfo.corpName
                    , billStatus : ''
                    , napCustKdCd : ''
                    , billKind : 'Y'
                    , billEmail : ''
                    , billTelNo : ''
                    , billZip : ''
                    , billJuso : ''
                    , billJuso2 : ''
                    , payMthdCd : 'CM'
                    , payDt : ''
                    , napCmpNm : ''
                    , napJumin : ''
                    , bankCd : ''
                    , bankNo : ''
                    , cardCd : ''
                    , cardNo1 : ''
                    , cardNo2 : ''
                    , cardNo3 : ''
                    , cardNo4 : ''
                    , cardValdEndYymm1 : ''
                    , cardValdEndYymm2 : ''
                    , serviceId : ''
                    , smsExpCnt : null
                    , rcsExpCnt : null
                    , kkoExpCnt : null
                    , pushExpCnt : null
                    , monthExpAmount : 0
                    , handleReason : ''
                    , handleId : ''
                    , handleDt : ''
                    , isCert : false
                    , isAgree : false
                };
            } else {
                let params = {billId : this.corpInfo.billId};
                homeApi.selectBill(params).then(response =>{
                    var result = response.data;
                    if (result.success) {
                        for(var k in this.bill) {
                            this.bill[k] = result.data[k];
                        }
                        if (this.bill.payMthdCd == "CC") {
                            this.bill.cardNo1 = result.data.cardNo.substring(0,4)
                            this.bill.cardNo2 = result.data.cardNo.substring(4,8)
                            this.bill.cardNo3 = result.data.cardNo.substring(8,12)
                            this.bill.cardNo4 = result.data.cardNo.substring(12,16)
                            this.bill.cardValdEndYymm1 = result.data.cardValdEndYymm.substring(0,4)
                            this.bill.cardValdEndYymm2 = result.data.cardValdEndYymm.substring(4,6)
                        }
                    } else {
                        confirm.fnAlert("", result.message);
                    }
                });
            }
        },
		// 주소 조회 팝업
		fnAddrPopup1(){
			this.popReset1 += 1;
			jQuery("#addrPopup1").modal("show");
		},
		// 선택한 주소 set
		fnSetCorpAddr(){
			this.bill.billZip = this.selAddr.mailNo;
			this.bill.billJuso = this.selAddr.roadMailFullAddr + " " + this.selAddr.roadMailFullAddr2;
		},
        fnCngCertInfo() {
            this.bill.isCert = this.bill.payMthdCd == 'GR'
        },
        fnBank() {
            if(this.bill.napCustKdCd == ""){
                confirm.fnAlert("", "납부고객구분을 선택해주세요.");
                return;
            }
            if(this.bill.napCmpNm == ""){
                confirm.fnAlert("", "납부자명을 입력해주세요.");
                return;
            }
            if(this.bill.napJumin == ""){
                confirm.fnAlert("", "생년월일/사업자번호를 입력해주세요.");
                return;
            }
            if(this.bill.bankCd == ""){
                confirm.fnAlert("", "은행을 선택해주세요.");
                return;
            }
            if(this.bill.bankNo == ""){
                confirm.fnAlert("", "계좌번호를 선택해주세요.");
                return;
            }
			var params = {
				ownerName : this.bill.napCmpNm
              , ownerNo : this.bill.napJumin
              , bankCode : this.bill.bankCd
              , backAcntNo : this.bill.bankNo
              , custDvCd : this.bill.napCustKdCd.substring(0,1)
              , custKdCd : this.bill.napCustKdCd
			};

			signUpApi.chkBank(params).then(response => {
				var result = response.data;
				var vm = this;
				if(result.success){
                    confirm.fnAlert("", "인증되었습니다.");
                    vm.bill.isCert = true;
				} else {
				    confirm.fnAlert("", result.message + '\n인증에 실패했습니다.정확한 정보를 입력바랍니다.');
				}
			})
        },
        fnCard() {
            if(this.bill.napCustKdCd == ""){
                confirm.fnAlert("", "납부고객구분을 선택해주세요.");
                return;
            }
            if(this.bill.napCmpNm == ""){
                confirm.fnAlert("", "납부자명을 입력해주세요.");
                return;
            }
            if(this.bill.napJumin == ""){
                confirm.fnAlert("", "생년월일/사업자번호를 입력해주세요.");
                return;
            }
            if(this.bill.cardCd == ""){
                confirm.fnAlert("", "카드종류를 선택해주세요.");
                return;
            }
            if(this.bill.cardNo1 == "" || this.bill.cardNo2 == "" || this.bill.cardNo3 == "" || this.bill.cardNo4 == ""){
                confirm.fnAlert("", "카드번호를 입력해주세요.");
                return;
            }
            if(this.bill.cardValdEndYymm1 == "" || this.bill.cardValdEndYymm2 == ""){
                confirm.fnAlert("", "카드유효기간를 입력해주세요.");
                return;
            }
			var params = {
				ownerName : this.bill.napCmpNm
              , ownerNo : this.bill.napJumin
              , cardCode : this.bill.cardCd
              , cardNo : this.bill.cardNo1 + this.bill.cardNo2 + this.bill.cardNo3 + this.bill.cardNo4
              , cardExYYMM : this.bill.cardValdEndYymm1 + this.bill.cardValdEndYymm2
              , custDvCd : this.bill.napCustKdCd.substring(0,1)
              , custKdCd : this.bill.napCustKdCd
			};

			signUpApi.chkCard(params).then(response => {
				var result = response.data;
				var vm = this;
				if(result.success){
                    confirm.fnAlert("", "인증되었습니다.");
                    vm.bill.isCert = true;
				} else {
				    confirm.fnAlert("", result.message + '\n인증에 실패했습니다.정확한 정보를 입력바랍니다.');
				}
			})
        },
        fnConfirm(){
            if(this.bill.smsExpCnt == null || this.bill.smsExpCnt == ""){
                confirm.fnAlert("", "월 발송 예상 건수(문자)를 입력해주세요.");
                return false;
            }
            if(this.bill.rcsExpCnt == null || this.bill.rcsExpCnt == ""){
                confirm.fnAlert("", "월 발송 예상 건수(RCS)를 입력해주세요.");
                return false;
            }
            if(this.bill.kkoExpCnt == null || this.bill.kkoExpCnt == ""){
                confirm.fnAlert("", "월 발송 예상 건수(카카오)를 입력해주세요.");
                return false;
            }
            if(this.bill.pushExpCnt == null || this.bill.pushExpCnt == ""){
                confirm.fnAlert("", "월 발송 예상 건수(Push)를 입력해주세요.");
                return false;
            }
            if(this.bill.billRegNo == ""){
                confirm.fnAlert("", "청구 사업자번호를 입력해주세요.");
                return false;
            }
            if (this.bill.billTelNo == "" && this.bill.billKind == "C") {
                confirm.fnAlert("", "청구서 수신 휴대폰를 입력해주세요.");
                return false;
            }
            if (this.bill.billEmail == "" && this.bill.billKind == "Y") {
                confirm.fnAlert("", "청구 이메일을 입력해주세요.");
                return false;
            }
            if(this.bill.billZip == "" && this.bill.billKind == "N"){
                confirm.fnAlert("", "청구 주소를 선택해주세요.");
                return false;
            }
            if(this.bill.billJuso2 == "" && this.bill.billKind == "N"){
                confirm.fnAlert("", "청구 상세주소를 입력해주세요.");
                return false;
            }
            if(this.bill.napCustKdCd == ""){
                confirm.fnAlert("", "납부고객구분을 선택해주세요.");
                return false;
            }
            if(this.bill.payDt == "" && this.bill.payMthdCd != "GR"){
                confirm.fnAlert("", "납부일을 선택해주세요.");
                return false;
            }
            if(this.bill.napCmpNm == ""){
                confirm.fnAlert("", "납부자명을 입력해주세요.");
                return false;
            }
            if(this.bill.napJumin == ""){
                confirm.fnAlert("", "생년월일/사업자번호를 입력해주세요.");
                return false;
            }
            if(this.bill.bankCd == "" && this.bill.payMthdCd == "CM"){
                confirm.fnAlert("", "은행을 선택해주세요.");
                return false;
            }
            if(this.bill.bankNo == "" && this.bill.payMthdCd == "CM"){
                confirm.fnAlert("", "계좌번호를 선택해주세요.");
                return false;
            }
            if(this.bill.cardCd == "" && this.bill.payMthdCd == "CC"){
                confirm.fnAlert("", "카드종류를 선택해주세요.");
                return false;
            }
            if((this.bill.cardNo1 == "" || this.bill.cardNo2 == "" || this.bill.cardNo3 == "" || this.bill.cardNo4 == "") && this.bill.payMthdCd == "CC"){
                confirm.fnAlert("", "카드번호를 입력해주세요.");
                return false;
            }
            if((this.bill.cardValdEndYymm1 == "" || this.bill.cardValdEndYymm2 == "") && this.bill.payMthdCd == "CC"){
                confirm.fnAlert("", "카드유효기간를 입력해주세요.");
                return false;
            }
            if(this.bill.isAgree == false && (this.bill.payMthdCd == "CM" || this.bill.payMthdCd == "CC")){
                confirm.fnAlert("", "이용동의안에 동의해야 합니다.");
                return false;
            }
            if (this.bill.billKind != "Y") {
                this.bill.billEmail = "";
            }
            if (this.bill.billKind != "C") {
                this.bill.billTelNo = "";
            }
            if (this.bill.billKind != "N") {
                this.bill.billZip = "";
                this.bill.billJuso = "";
                this.bill.billJuso2 = "";
            }
            if (this.bill.payMthdCd != "CM") {
                this.bill.bankCd = "";
                this.bill.bankNo = "";
            }
            if (this.bill.payMthdCd != "CC") {
                this.bill.cardCd = "";
                this.bill.cardNo1 = "";
                this.bill.cardNo2 = "";
                this.bill.cardNo3 = "";
                this.bill.cardNo4 = "";
                this.bill.cardValdEndYymm1 = "";
                this.bill.cardValdEndYymm2 = "";
            }
            if (this.bill.payMthdCd != "GR") {
                this.bill.payDt = "";
            }
			eventBus.$on('callbackEventBus', this.fnConfirmCallback);
			confirm.fnConfirm('청구정보', '정말로 요청하시겠습니까?', '확인');
        },
        fnConfirmCallback(){
            let params = Object.assign({}, this.bill)
			homeApi.saveBill(params).then(response =>{
				var result = response.data;
				if(result.success) {
					confirm.fnAlert('청구정보', '정상적으로 요청 되었습니다.');
                    if (this.bill.billId == '') {
                        this.corpInfo.billId = '0';
                    }
                    this.corpInfo.billStatus = 'REQ';
                    this.corpInfo.billStatusNm = '후불 사용 요청 중';
                    jQuery("#billPopup").modal("hide")
				} else {
					confirm.fnAlert('청구정보', result.message);
				}
			});
        },
        fnDel(){
			eventBus.$on('callbackEventBus', this.fnDelCallback);
			confirm.fnConfirm('청구정보', '정말로 삭제하시겠습니까?', '확인');
        },
        fnDelCallback(){
            let params = Object.assign({}, this.corpInfo)
			homeApi.delBill(params).then(response =>{
				var result = response.data;
				if(result.success) {
					confirm.fnAlert('청구정보', '정상적으로 삭제 되었습니다.');
                    this.corpInfo.billId = null;
                    this.corpInfo.billStatus = null;
                    this.corpInfo.billStatusNm = null;
                    jQuery("#billPopup").modal("hide")
				} else {
					confirm.fnAlert('청구정보', result.message);
				}
			});
        },
        fnClose() {
            jQuery("#billPopup").modal("hide")
        }
    }
}
</script>