<template>
<div class="modal modalStyle" id="billRegPopup" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <div>
                    <h2>청구정보</h2>
                    <hr>
                    <h4 v-if="set.billType == 'PROJECT'" class="mt10">월 발송 예상 건수</h4>
                    <div v-if="set.billType == 'PROJECT'" class="joinBox mt10">							
                        <div class="of_h">
                            <div class="float-left" style="width:45%">
                                <div class="float-left" style="width:30%"><h5>문자</h5></div>
                                <div class="float-left" style="width:70%">
                                    <input type="text" class="inputStyle" placeholder="발송 건수(숫자만 입력)" v-model="set.smsExpCnt">
                                </div>
                            </div>
                            <div class="float-right" style="width:45%">
                                <div class="float-left" style="width:30%"><h5>RCS</h5></div>
                                <div class="float-left" style="width:70%">
                                    <input type="text" class="inputStyle" placeholder="발송 건수(숫자만 입력)" v-model="set.rcsExpCnt">
                                </div>
                            </div>
                        </div>
                        <div class="of_h mt10">
                            <div class="float-left" style="width:45%">
                                <div class="float-left" style="width:30%"><h5>카카오</h5></div>
                                <div class="float-left" style="width:70%">
                                    <input type="text" class="inputStyle" placeholder="발송 건수(숫자만 입력)" v-model="set.kkoExpCnt">
                                </div>
                            </div>
                            <div class="float-right" style="width:45%">
                                <div class="float-left" style="width:30%"><h5>Push</h5></div>
                                <div class="float-left" style="width:70%">
                                    <input type="text" class="inputStyle" placeholder="발송 건수(숫자만 입력)" v-model="set.pushExpCnt">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div v-if="set.billType == 'DEPART'" class="of_h mt10">
                        <div class="float-left" style="width:22%"><h5>청구계정 명*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="text" class="inputStyle" placeholder="청구계정 명" v-model="set.billName">
                        </div>
                    </div>
                    <div class="of_h mt10">
                        <div class="float-left" style="width:22%"><h5>청구서 수신</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="radio" name="set.billKind" value="Y" v-model="set.billKind" id="sms_agree05"> <label for="sms_agree05" class="mr30">이메일</label>
                            <input type="radio" name="set.billKind" value="C" v-model="set.billKind" id="sms_agree06"> <label for="sms_agree06" class="mr30">문자</label>
                            <input type="radio" name="set.billKind" value="N" v-model="set.billKind" id="sms_agree07"> <label for="sms_agree07">우편</label>
                        </div>
                    </div>
                    <div class="of_h mt10">
                        <div class="float-left" style="width:22%"><h5>청구 사업자번호*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="text" class="inputStyle" placeholder="사업자번호(-없이 입력)" v-model="set.billRegNo">
                        </div>
                    </div>
                    <div class="of_h mt10" v-show="set.billKind == 'C'">
                        <div class="float-left" style="width:22%"><h5>청구서 수신 휴대폰*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="text" class="inputStyle" placeholder="휴대폰번호(-없이 입력)" v-model="set.billTelNo">
                        </div>
                    </div>
                    <div class="of_h mt10" v-show="set.billKind == 'Y'">
                        <div class="float-left" style="width:22%"><h5>청구 이메일*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="text" class="inputStyle" placeholder="ex) example@uplus.co.kr" v-model="set.billEmail">
                        </div>
                    </div>

                    <div class="of_h mt10" v-show="set.billKind == 'N'">
                        <div class="float-left" style="width:22%"><h5>청구 주소*</h5></div>
                        <div class="float-left" style="width:78%">
                            <div class="float-left" style="width:72%"><input type="text" class="inputStyle" disabled="disabled" v-model="set.billZip"></div>
                            <div class="float-right" style="width:25%"><a class="btnStyle1 backLightGray" style="min-width:auto; width:100%" @click="fnAddrPopup1">우편번호</a></div>	
                        </div>
                        <div class="float-right mt5" style="width:78%"><input type="text" class="inputStyle" disabled="disabled" v-model="set.billJuso"></div>
                        <div class="float-right mt5" style="width:78%"><input type="text" class="inputStyle" placeholder="상세주소 입력" v-model="set.billJuso2"></div>
                    </div>
                    <div class="of_h mt10">
                        <div class="float-left" style="width:22%"><h5>납부고객구분*</h5></div>
                        <div class="float-left" style="width:78%">
                            <select class="selectStyle2" style="width:50%" title="납부고객구분 선택란" v-model="set.napCustKdCd" @change="fnCngCertInfo">
                                <option value="">선택하세요</option>
                                <option  v-for="(row, index) in napCustKdCdArr" :key="index" :value="row.codeVal1"> {{ row.codeName1 }} </option>
                            </select>
                        </div>
                    </div>
                    <div class="of_h mt10">
                        <div class="float-left" style="width:22%"><h5>납부방법*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="radio" name="set.payMthdCd" value="CM" v-model="set.payMthdCd" id="pay04" @change="fnCngCertInfo"> <label for="pay04" class="mr30">자동이체</label>
                            <input type="radio" name="set.payMthdCd" value="CC" v-model="set.payMthdCd" id="pay05" @change="fnCngCertInfo"> <label for="pay05" class="mr30">신용카드</label>
                            <input type="radio" name="set.payMthdCd" value="GR" v-model="set.payMthdCd" id="pay06" @change="fnCngCertInfo"> <label for="pay06">지로</label>
                        </div>
                    </div>
                    <div class="of_h mt10" v-show="set.payMthdCd != 'GR'">
                        <div class="float-left" style="width:22%"><h5>납부일*</h5></div>
                        <div class="float-left" style="width:78%">
                            <select class="selectStyle2" style="width:50%" title="납부일 선택란" v-model="set.payDt">
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
                            <input type="text" class="inputStyle" placeholder="납부자명" v-model="set.napCmpNm" @change="fnCngCertInfo">
                        </div>
                    </div>
                    <div class="of_h mt10">
                        <div class="float-left" style="width:22%"><h5>생년월일/<br>사업자번호*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="text" class="inputStyle" placeholder="생년월일(YYYYMMDD)/사업자번호" v-model="set.napJumin" @change="fnCngCertInfo" maxlength="12">
                        </div>
                    </div>
                    <div class="of_h mt10" v-show="set.payMthdCd == 'CM'">
                        <div class="float-left" style="width:22%"><h5>은행*</h5></div>
                        <div class="float-left" style="width:78%">
                            <select class="selectStyle2" style="width:50%" id="bankCd" title="은행 선택란" v-model="set.bankCd" @change="fnCngCertInfo">
                                <option value="">선택하세요</option>
                                <option  v-for="(row, index) in bankCdArr" :key="index" :value="row.codeVal1">{{ row.codeName1 }}</option>
                            </select>
                        </div>
                    </div>
                    <div class="of_h mt10" v-show="set.payMthdCd == 'CM'">
                        <div class="float-left" style="width:22%"><h5>계좌번호*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="text" class="inputStyle" placeholder="계좌번호(- 제외)" v-model="set.bankNo" @change="fnCngCertInfo">
                            <a @click="fnBank" class="btnStyle1 backRed vertical-middle mt10" style="min-width:auto; width:24.5%">계좌 인증</a>
                        </div>
                    </div>

                    <div class="of_h mt10" v-show="set.payMthdCd == 'CC'">
                        <div class="float-left" style="width:22%"><h5>카드종류*</h5></div>
                        <div class="float-left" style="width:78%">
                            <select class="selectStyle2" style="width:50%" id="cardCd" title="카드종류 선택란" v-model="set.cardCd" @change="fnCngCertInfo">
                                <option value="">선택하세요</option>
                                <option  v-for="(row, index) in cardCdArr" :key="index" :value="row.codeVal1">{{ row.codeName1 }}</option>
                            </select>
                        </div>
                    </div>
                    <div class="of_h mt10" v-show="set.payMthdCd == 'CC'">
                        <div class="float-left" style="width:22%"><h5>카드번호*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="text" class="inputStyle" style="width:24.5%" v-model="set.cardNo1" @change="fnCngCertInfo" maxlength="4">
                            <input type="password" class="inputStyle" style="width:24.5%" v-model="set.cardNo2" @change="fnCngCertInfo" maxlength="4">
                            <input type="password" class="inputStyle" style="width:24.5%" v-model="set.cardNo3" @change="fnCngCertInfo" maxlength="4">
                            <input type="text" class="inputStyle" style="width:24.5%" v-model="set.cardNo4" @change="fnCngCertInfo" maxlength="4">
                        </div>
                    </div>
                    <div class="of_h mt10" v-show="set.payMthdCd == 'CC'">
                        <div class="float-left" style="width:22%"><h5>카드유효기간*</h5></div>
                        <div class="float-left" style="width:78%">
                            <div class="float-left" style="width:50%">
                                <div class="float-left" style="width:49%">
                                    <input type="text" class="inputStyle" placeholder="4자리" v-model="set.cardValdEndYymm1" @change="fnCngCertInfo" maxlength="4">
                                </div>
                                <div class="float-left ml10" style="width:10%"><h5>년</h5></div>
                            </div>
                            <div class="float-right" style="width:50%">
                                <div class="float-left" style="width:49%">
                                    <input type="text" class="inputStyle" placeholder="2자리" v-model="set.cardValdEndYymm2" @change="fnCngCertInfo" maxlength="2">
                                </div>
                                <div class="float-left ml10" style="width:10%"><h5>월</h5></div>
                            </div>
                            <a @click="fnCard" class="btnStyle1 backRed vertical-middle mt10" style="min-width:auto; width:24.5%">카드 인증</a>
                        </div>
                    </div>
                    <div class="joinBox mt10" v-show="set.payMthdCd != 'GR'">	
                        <div class="checks">
                            <input type="checkbox" id="chkID" class="checkStyle2" v-model="set.isAgree"><label for="chkID">본인은 서비스 이용요금을 매월 본인 또는 법인의 계좌나 카드에서 지정된 출금일 또는<br>결제일에 인출 하는 것에 동의합니다.<br>
                            본인은 LG유플러스가 이용요금 청구 및 수납을 위해 뒷면에 기재된 개인정보 활용동의서의 내용에 따라 위의 정보를 수집 후 이용 또는 정보 위탁하는 것에 동의한다.</label>	
                        </div>
                    </div>

                    <div class="text-center mt40">
                        <a v-show="set.isCert" @click="fnConfirm" class="btnStyle3 black font14">확인</a>
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
import signUpApi from "@/modules/signUp/service/api";

export default {
    name : 'billRegPopup',
	components: {
		addrPopup
	},
    props : {
        popReset : {
            type : Number,
            require : false
        },
        bill : {
            type : Object,
            require : false
        }
    },
    data(){
        return {
			set : {
				  billRegNo : ''
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
                , bankNm : ''
				, cardCd : ''
                , cardCmpName : ''
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
            this.set = Object.assign({}, this.bill)
            this.$forceUpdate()
        },
		selAddr() {
			this.fnSetCorpAddr();
		},
		'set.smsExpCnt'() {
			return this.set.smsExpCnt = this.set.smsExpCnt.replace(/[^0-9]/g, '');
		},
		'set.rcsExpCnt'() {
			return this.set.rcsExpCnt = this.set.rcsExpCnt.replace(/[^0-9]/g, '');
		},
		'set.kkoExpCnt'() {
			return this.set.kkoExpCnt = this.set.kkoExpCnt.replace(/[^0-9]/g, '');
		},
		'set.pushExpCnt'() {
			return this.set.pushExpCnt = this.set.pushExpCnt.replace(/[^0-9]/g, '');
		},
		'set.billRegNo'() {
			return this.set.billRegNo = this.set.billRegNo.replace(/[^0-9]/g, '');
		},
		'set.billTelNo'() {
			return this.set.billTelNo = this.set.billTelNo.replace(/[^0-9]/g, '');
		},
		'set.napJumin'() {
			return this.set.napJumin = this.set.napJumin.replace(/[^0-9]/g, '');
		},
		'set.bankNo'() {
			return this.set.bankNo = this.set.bankNo.replace(/[^0-9]/g, '');
		},
		'set.cardNo1'() {
			return this.set.cardNo1 = this.set.cardNo1.replace(/[^0-9]/g, '');
		},
		'set.cardNo2'() {
			return this.set.cardNo2 = this.set.cardNo2.replace(/[^0-9]/g, '');
		},
		'set.cardNo3'() {
			return this.set.cardNo3 = this.set.cardNo3.replace(/[^0-9]/g, '');
		},
		'set.cardNo4'() {
			return this.set.cardNo4 = this.set.cardNo4.replace(/[^0-9]/g, '');
		},
		'set.cardValdEndYymm1'() {
			return this.set.cardValdEndYymm1 = this.set.cardValdEndYymm1.replace(/[^0-9]/g, '');
		},
		'set.cardValdEndYymm2'() {
			return this.set.cardValdEndYymm2 = this.set.cardValdEndYymm2.replace(/[^0-9]/g, '');
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
		// 주소 조회 팝업
		fnAddrPopup1(){
			this.popReset1 += 1;
			jQuery("#addrPopup1").modal("show");
		},
		// 선택한 주소 set
		fnSetCorpAddr(){
			this.set.billZip = this.selAddr.mailNo;
			this.set.billJuso = this.selAddr.roadMailFullAddr + " " + this.selAddr.roadMailFullAddr2;
		},
        fnCngCertInfo() {
            this.set.isCert = this.set.payMthdCd == 'GR'
        },
        fnBank() {
            if(this.set.napCustKdCd == ""){
                confirm.fnAlert("", "납부고객구분을 선택해주세요.");
                return;
            }
            if(this.set.napCmpNm == ""){
                confirm.fnAlert("", "납부자명을 입력해주세요.");
                return;
            }
            if(this.set.napJumin == ""){
                confirm.fnAlert("", "생년월일/사업자번호를 입력해주세요.");
                return;
            }
            if(this.set.bankCd == ""){
                confirm.fnAlert("", "은행을 선택해주세요.");
                return;
            }
            if(this.set.bankNo == ""){
                confirm.fnAlert("", "계좌번호를 선택해주세요.");
                return;
            }
			var params = {
				ownerName : this.set.napCmpNm
              , ownerNo : this.set.napJumin
              , bankCode : this.set.bankCd
              , backAcntNo : this.set.bankNo
              , custDvCd : this.set.napCustKdCd.substring(0,1)
              , custKdCd : this.set.napCustKdCd
			};

			signUpApi.chkBank(params).then(response => {
				var result = response.data;
				var vm = this;
				if(result.success && result.data != null && (result.data.serviceId == 'CM061' && result.data.resultCode == 'Y' && (result.data.cmsResultCode == '00' || result.data.cmsResultCode == '0000'))){
                    confirm.fnAlert("", "인증되었습니다.");
                    vm.set.isCert = true;
				} else {
				    confirm.fnAlert("", result.data.resultMsg + '\n인증에 실패했습니다.정확한 정보를 입력바랍니다.');
				}
			})
        },
        fnCard() {
            if(this.set.napCustKdCd == ""){
                confirm.fnAlert("", "납부고객구분을 선택해주세요.");
                return;
            }
            if(this.set.napCmpNm == ""){
                confirm.fnAlert("", "납부자명을 입력해주세요.");
                return;
            }
            if(this.set.napJumin == ""){
                confirm.fnAlert("", "생년월일/사업자번호를 입력해주세요.");
                return;
            }
            if(this.set.cardCd == ""){
                confirm.fnAlert("", "카드종류를 선택해주세요.");
                return;
            }
            if(this.set.cardNo1 == "" || this.set.cardNo2 == "" || this.set.cardNo3 == "" || this.set.cardNo4 == ""){
                confirm.fnAlert("", "카드번호를 입력해주세요.");
                return;
            }
            if(this.set.cardValdEndYymm1 == "" || this.set.cardValdEndYymm2 == ""){
                confirm.fnAlert("", "카드유효기간를 입력해주세요.");
                return;
            }
			var params = {
				ownerName : this.set.napCmpNm
              , ownerNo : this.set.napJumin
              , cardCode : this.set.cardCd
              , cardNo : this.set.cardNo1 + this.set.cardNo2 + this.set.cardNo3 + this.set.cardNo4
              , cardExYYMM : this.set.cardValdEndYymm1 + this.set.cardValdEndYymm2
              , custDvCd : this.set.napCustKdCd.substring(0,1)
              , custKdCd : this.set.napCustKdCd
			};

			signUpApi.chkCard(params).then(response => {
				var result = response.data;
				var vm = this;
				if(result.success && result.data != null && (result.data.serviceId == 'CM061' && result.data.resultCode == 'Y' && (result.data.cmsResultCode == '00' || result.data.cmsResultCode == '0000'))){
                    confirm.fnAlert("", "인증되었습니다.");
                    vm.set.isCert = true;
				} else {
				    confirm.fnAlert("", result.data.resultMsg + '\n인증에 실패했습니다.정확한 정보를 입력바랍니다.');
				}
			})
        },
        fnConfirm(){
            if (this.set.billType == 'PROJECT') {
                if(this.set.smsExpCnt == null || this.set.smsExpCnt == ""){
                    confirm.fnAlert("", "월 발송 예상 건수(문자)를 입력해주세요.");
                    return false;
                }
                if(this.set.rcsExpCnt == null || this.set.rcsExpCnt == ""){
                    confirm.fnAlert("", "월 발송 예상 건수(RCS)를 입력해주세요.");
                    return false;
                }
                if(this.set.kkoExpCnt == null || this.set.kkoExpCnt == ""){
                    confirm.fnAlert("", "월 발송 예상 건수(카카오)를 입력해주세요.");
                    return false;
                }
                if(this.set.pushExpCnt == null || this.set.pushExpCnt == ""){
                    confirm.fnAlert("", "월 발송 예상 건수(Push)를 입력해주세요.");
                    return false;
                }
            }
            if(this.set.billType == 'DEPART' && this.set.billName == ""){
                confirm.fnAlert("", "청구계정명을 입력해주세요.");
                return false;
            }
            if(this.set.billRegNo == ""){
                confirm.fnAlert("", "청구 사업자번호를 입력해주세요.");
                return false;
            }
            if (this.set.billTelNo == "" && this.set.billKind == "C") {
                confirm.fnAlert("", "청구서 수신 휴대폰를 입력해주세요.");
                return false;
            }
            if (this.set.billEmail == "" && this.set.billKind == "Y") {
                confirm.fnAlert("", "청구 이메일을 입력해주세요.");
                return false;
            }
            if(this.set.billZip == "" && this.set.billKind == "N"){
                confirm.fnAlert("", "청구 주소를 선택해주세요.");
                return false;
            }
            if(this.set.billJuso2 == "" && this.set.billKind == "N"){
                confirm.fnAlert("", "청구 상세주소를 입력해주세요.");
                return false;
            }
            if(this.set.napCustKdCd == ""){
                confirm.fnAlert("", "납부고객구분을 선택해주세요.");
                return false;
            }
            if(this.set.payDt == "" && this.set.payMthdCd != "GR"){
                confirm.fnAlert("", "납부일을 선택해주세요.");
                return false;
            }
            if(this.set.napCmpNm == ""){
                confirm.fnAlert("", "납부자명을 입력해주세요.");
                return false;
            }
            if(this.set.napJumin == ""){
                confirm.fnAlert("", "생년월일/사업자번호를 입력해주세요.");
                return false;
            }
            if(this.set.bankCd == "" && this.set.payMthdCd == "CM"){
                confirm.fnAlert("", "은행을 선택해주세요.");
                return false;
            }
            if(this.set.bankNo == "" && this.set.payMthdCd == "CM"){
                confirm.fnAlert("", "계좌번호를 선택해주세요.");
                return false;
            }
            if(this.set.cardCd == "" && this.set.payMthdCd == "CC"){
                confirm.fnAlert("", "카드종류를 선택해주세요.");
                return false;
            }
            if((this.set.cardNo1 == "" || this.set.cardNo2 == "" || this.set.cardNo3 == "" || this.set.cardNo4 == "") && this.set.payMthdCd == "CC"){
                confirm.fnAlert("", "카드번호를 입력해주세요.");
                return false;
            }
            if((this.set.cardValdEndYymm1 == "" || this.set.cardValdEndYymm2 == "") && this.set.payMthdCd == "CC"){
                confirm.fnAlert("", "카드유효기간를 입력해주세요.");
                return false;
            }
            if(this.set.isAgree == false && (this.set.payMthdCd == "CM" || this.set.payMthdCd == "CC")){
                confirm.fnAlert("", "이용동의안에 동의해야 합니다.");
                return false;
            }
            if (this.set.billKind != "Y") {
                this.set.billEmail = "";
            }
            if (this.set.billKind != "C") {
                this.set.billTelNo = "";
            }
            if (this.set.billKind != "N") {
                this.set.billZip = "";
                this.set.billJuso = "";
                this.set.billJuso2 = "";
            }
            if (this.set.payMthdCd != "CM") {
                this.set.bankCd = "";
                this.set.bankNo = "";
                this.set.bankNm = "";
            } else {
                this.set.bankNm = jQuery("#bankCd option:selected").text();
            }
            if (this.set.payMthdCd != "CC") {
                this.set.cardCd = "";
                this.set.cardCmpName = "";
                this.set.cardNo1 = "";
                this.set.cardNo2 = "";
                this.set.cardNo3 = "";
                this.set.cardNo4 = "";
                this.set.cardValdEndYymm1 = "";
                this.set.cardValdEndYymm2 = "";
            } else {
                this.set.cardCmpName = jQuery("#cardCd option:selected").text();
            }
			this.$emit("update:bill", this.set);
            jQuery("#billRegPopup").modal("hide")
        },
        fnClose() {
            jQuery("#billRegPopup").modal("hide")
        }
    }
}
</script>