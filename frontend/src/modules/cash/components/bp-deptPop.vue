<template>
  <div class="modal modalStyle" id="deptPop" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h">
						<h2>부서 {{popupTitle}}</h2>
						<hr>
						<div>
							<p class="color4 bgColor_f1 pd20">※ 청구계정이 없을 경우 청구계정 신규 생성을 선택하시고 청구 정보를 입력해 주십시오.</p>
						</div>

						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:18%">부서코드 *</h5>
							<input type="text" class="inputStyle float-right" style="width:80%" :disabled="this.rowData.billId != null" v-model="dept.deptCode">
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:18%">부서명 *</h5>
							<input type="text" class="inputStyle float-right" style="width:80%" v-model="dept.deptName">
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:18%">프로젝트 *</h5>
							<select v-model="dept.projectId" class="selectStyle2 float-right" style="width:80%">
								<option value="">선택해 주세요</option>
								<option v-for="(data, idx) in projectList" :value="data.projectId">{{ data.projectName }}</option>
							</select>
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:18%">사용여부 *</h5>
							<div class="float-right" style="width:80%">
								<input type="radio" name="use2" value="Y" v-model="dept.useYn" id="yes2"> <label for="yes2" class="mr30">예</label>
								<input type="radio" name="use2" value="N" v-model="dept.useYn" id="no2"> <label for="no2">아니오</label>	
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:18%">청구계정 *</h5>
							<div class="float-right" style="width:80%">
								<div v-if="rowData.billId == null" style="width:100%" class="mb10">
									<input type="radio" name="use3" v-model="isNew" value="Y" id="yes3"> <label for="yes3" class="mr30">신규 생성</label>
									<input type="radio" name="use3" v-model="isNew" value="N" id="no3"> <label for="no3">기존 청구</label>	
								</div>		
								<div v-if="rowData.billId == null && isNew == 'Y'" style="width:100%">
									<a @click="fnBill" class="btnStyle1 backLightGray" title="청구정보 입력">청구정보 입력</a>
								</div>	
								<select v-if="isNew == 'N'" v-model="dept.billId" class="selectStyle2 float-right" style="width:100%">
									<option value="">청구계정을 선택해 주세요</option>
                  					<option v-for="(data, idx) in billList" :value="data.billId">{{ data.billName }}</option>
								</select>
							</div>
						</div>
					</div>						
					<div class="text-center mt20">
						<a @click="fnSave" class="btnStyle1 backBlack">저장</a>
						<a @click="fnClose" class="btnStyle1 backWhite">취소</a>						
					</div>
				</div>
			</div>
		</div>
		<billRegPopup :popReset="popReset1" :bill.sync="bill"></billRegPopup>
	</div>
</template>

<script>
import cashApi from "@/modules/cash/service/api"
import confirm from "@/modules/commonUtil/service/confirm.js"
import billRegPopup from "@/modules/signUp/components/bp-bill"

export default {
  name: 'deptPopup',
  props: {
    popupTitle: {
      type: String,
      require: false,
    },
    rowData : {
      type: Object,
      require: false,
    },
    popReset :{
      type : Number  
    }
  },
  components : {
	  billRegPopup
  },
  data(){
    return {
		dept: {},
		isNew : 'Y',
		projectList: [],
		billList: [],
		popReset1 : 0,				// 팝업 초기화할 num
		bill : {
			  billRegNo : ''
			, billType : 'DEPART'
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
			, bankNm : ''
			, bankNo : ''
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
		}
    }
  },
  watch : {
    popReset(){
	this.load();
      this.init();
    }
  },
  mounted() {
  },
  methods: {
	load() {
	  var vm = this
      cashApi.selectBillList({}).then(response => {
        var result = response.data
        if(result.success) {
          vm.billList = result.data
        }
      })
      cashApi.selectProjectList({}).then(response => {
        var result = response.data
        if(result.success) {
          vm.projectList = result.data
        }
      })
	},
  init() {
	  this.isNew = this.rowData.billId == null ? 'Y' : 'N'
      this.dept = Object.assign({
            isNew : true,
		    deptCode : '',
		    depttName : '',
			projectId : '',
		    useYn : 'Y',
		    billId : ''
	  }, this.rowData)
	  this.bill = {
			  billRegNo : ''
			, billType : 'DEPART'
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
			, bankNm : ''
			, bankNo : ''
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
		}
  },
	fnSave() {
      //필수항목 check
      if(jQuery.trim(this.dept.deptCode).length == 0){
        confirm.fnAlert("", "부서코드를 입력해주세요.");
        return;
      }
      if(jQuery.trim(this.dept.deptName).length == 0){
        confirm.fnAlert("", "부서명을 입력해주세요.");
        return;
      }
      if(jQuery.trim(this.dept.projectId).length == 0){
        confirm.fnAlert("", "프로젝트를 선택해주세요.");
        return;
      }
      if(this.isNew == 'N' && jQuery.trim(this.dept.billId).length == 0){
        confirm.fnAlert("", "청구계정을 선택해주세요.");
        return;
      }
	  if (this.isNew == 'Y') {
		var billVali = this.billVali();
		if (!billVali) return false;
	  }
      var params = Object.assign({}, this.dept, this.bill)
	  cashApi.saveDept(params).then(response =>{
       	var result = response.data;
       	if(result.success) {
	   	  confirm.fnAlert( "부서 저장", "저장되었습니다.");
        // 리스트 조회
        this.$parent.fnSearch();
        this.fnClose();
		} else {
		  confirm.fnAlert("", result.message);
		}
      });
	},
	fnClose() {
        jQuery("#deptPop").modal("hide");
	},
	billVali() {
		if(this.bill.billName == ""){
			confirm.fnAlert("", "청구계정명을 입력해주세요.");
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
		return true;
	},
	// 청구정보 팝업
	fnBill(){
		this.popReset1 += 1;
		jQuery("#billRegPopup").modal("show");
	},
  }
}
</script>
