<template>
<div class="modal modalStyle" id="billPopup" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <div>
                    <h2>청구정보</h2>
                    <hr>
                    <h4 class="mt10">월 발송 예상 건수</h4>
                    <div class="joinBox mt10">							
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

                    <div class="of_h mt10">
                        <div class="float-left" style="width:22%"><h5>청구서 수신</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="radio" name="sms_agree" value="Y" v-model="set.billKind" id="sms_agree05"> <label for="sms_agree05" class="mr30">이메일</label>
                            <input type="radio" name="sms_agree" value="C" v-model="set.billKind" id="sms_agree06"> <label for="sms_agree06" class="mr30">문자</label>
                            <input type="radio" name="sms_agree" value="N" v-model="set.billKind" id="sms_agree07"> <label for="sms_agree07">우편</label>
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
                            <input type="text" class="inputStyle" placeholder="휴대폰번호(-없이 입력)" v-model="set.billPhone">
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
                            <select class="selectStyle2" style="width:50%" title="납부고객구분 선택란" v-model="set.napCustKdCd">
                                <option value="">선택하세요</option>
                                <option  v-for="(row, index) in napCustKdCdArr" :key="index" :value="row.codeVal1"> {{ row.codeName1 }} </option>
                            </select>
                        </div>
                    </div>
                    <div class="of_h mt10">
                        <div class="float-left" style="width:22%"><h5>납부방법*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="radio" name="pay02" value="CM" v-model="set.payMthdCd" id="pay04"> <label for="pay04" class="mr30">자동이체</label>
                            <input type="radio" name="pay02" value="CC" v-model="set.payMthdCd" id="pay05"> <label for="pay05" class="mr30">신용카드</label>
                            <input type="radio" name="pay02" value="GR" v-model="set.payMthdCd" id="pay06"> <label for="pay06">지로</label>
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
                            <input type="text" class="inputStyle" placeholder="납부자명" v-model="set.napCmpNm">
                        </div>
                    </div>
                    <div class="of_h mt10">
                        <div class="float-left" style="width:22%"><h5>생년월일/<br>사업자번호*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="text" class="inputStyle" placeholder="생년월일(YYYYMMDD)/사업자번호" v-model="set.napJumin">
                        </div>
                    </div>
                    <div class="of_h mt10" v-show="set.payMthdCd == 'CM'">
                        <div class="float-left" style="width:22%"><h5>은행*</h5></div>
                        <div class="float-left" style="width:78%">
                            <select class="selectStyle2" style="width:50%" title="은행 선택란" v-model="set.bankCd">
                                <option value="">선택하세요</option>
                                <option  v-for="(row, index) in bankCdArr" :key="index" :value="row.codeVal1"> {{ row.codeName1 }} </option>
                            </select>
                        </div>
                    </div>
                    <div class="of_h mt10" v-show="set.payMthdCd == 'CM'">
                        <div class="float-left" style="width:22%"><h5>계좌번호*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="text" class="inputStyle" placeholder="계좌번호(- 제외)" v-model="set.bankNo">
                            <a class="btnStyle1 backRed vertical-middle mt10" style="min-width:auto; width:24.5%">계좌 인증</a>
                        </div>
                    </div>

                    <div class="of_h mt10" v-show="set.payMthdCd == 'CC'">
                        <div class="float-left" style="width:22%"><h5>카드종류*</h5></div>
                        <div class="float-left" style="width:78%">
                            <select class="selectStyle2" style="width:50%" title="카드종류 선택란" v-model="set.cardCd">
                                <option value="">선택하세요</option>
                                <option  v-for="(row, index) in cardCdArr" :key="index" :value="row.codeVal1"> {{ row.codeName1 }} </option>
                            </select>
                        </div>
                    </div>
                    <div class="of_h mt10" v-show="set.payMthdCd == 'CC'">
                        <div class="float-left" style="width:22%"><h5>카드번호*</h5></div>
                        <div class="float-left" style="width:78%">
                            <input type="text" class="inputStyle" style="width:24.5%" v-model="set.cardNo1">
                            <input type="text" class="inputStyle" style="width:24.5%" v-model="set.cardNo2">
                            <input type="text" class="inputStyle" style="width:24.5%" v-model="set.cardNo3">
                            <input type="text" class="inputStyle" style="width:24.5%" v-model="set.cardNo4">
                        </div>
                    </div>
                    <div class="of_h mt10" v-show="set.payMthdCd == 'CC'">
                        <div class="float-left" style="width:22%"><h5>카드유효기간*</h5></div>
                        <div class="float-left" style="width:78%">
                            <div class="float-left" style="width:50%">
                                <div class="float-left" style="width:49%">
                                    <input type="text" class="inputStyle" placeholder="4자리" v-model="set.cardValdEndYymm1">
                                </div>
                                <div class="float-left ml10" style="width:10%"><h5>년</h5></div>
                            </div>
                            <div class="float-right" style="width:50%">
                                <div class="float-left" style="width:49%">
                                    <input type="text" class="inputStyle" placeholder="2자리" v-model="set.cardValdEndYymm2">
                                </div>
                                <div class="float-left ml10" style="width:10%"><h5>월</h5></div>
                            </div>
                            <a class="btnStyle1 backRed vertical-middle mt10" style="min-width:auto; width:24.5%">카드 인증</a>
                        </div>
                    </div>
                    <div class="joinBox mt10" v-show="set.payMthdCd != 'GR'">	
                        <div class="checks">
                            <input type="checkbox" id="chkID" class="checkStyle2" value="아이디 저장"><label for="chkID">본인은 서비스 이용요금을 매월 본인 또는 법인의 계좌나 카드에서 지정된 출금일 또는<br>결제일에 인출 하는 것에 동의합니다.<br>
                            본인은 LG유플러스가 이용요금 청구 및 수납을 위해 뒷면에 기재된 개인정보 활용동의서의 내용에 따라 위의 정보를 수집 후 이용 또는 정보 위탁하는 것에 동의한다.</label>	
                        </div>
                    </div>

                    <div class="text-center mt40">
                        <a @click="fnConfirm" class="btnStyle3 black font14">확인</a>
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
    name : 'billPopup',
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
				, billPhone : ''
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
        },
		selAddr() {
			this.fnSetCorpAddr();
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
        fnConfirm(){
			this.$emit("update:bill", this.set);
            jQuery("#billPopup").modal("hide")
        },
        fnClose() {
            jQuery("#billPopup").modal("hide")
        }
    }
}
</script>