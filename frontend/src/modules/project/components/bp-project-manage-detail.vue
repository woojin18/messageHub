<template>
  <div class="modal fade modalStyle" id="projectPop"
    tabindex="-1" role="dialog" aria-hidden="true"
  >
    <!-- @click="fnClose" -->
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h5 class="lc-1">프로젝트 등록</h5>
						<hr>
						<div class="mt20 of_h">
							<h4 class="font-normal inline-block float-left" style="width:20%">프로젝트명 *</h4>
							<div style="width:78%" class="of_h">
								<div>
									<input id="projectName" type="text" class="inputStyle float-left" title="프로젝트명 입력란">
								</div>
							</div>
							<div class="mt30 of_h">
								<h4 class="font-normal inline-block float-left" style="width:20%">프로젝트 설명</h4>
								<div style="width:78%" class="of_h">
									<input id="projectDesc" type="text" class="inputStyle float-left" style="width:100%" title="프로젝트 설명 입력란">
								</div>
							</div>
							<div class="mt30 of_h">
								<h4 class="font-normal inline-block float-left mt5" style="width:20%">결재조건 *</h4>
								<input @click="fnSelectBillIdForApi('Y')" type="radio" name="payType" value="Y" class="cBox" id="payment01" :disabled="this.save_status != 'C'"> <label for="payment01" class="payment mr30 font-size12">선불</label>
								<input @click="fnSelectBillIdForApi('N')" type="radio" name="payType" value="N" class="cBox" id="payment02" :disabled="this.save_status != 'C'"> <label for="payment02" class="payment font-size12">후불</label>								
							</div>
              <div class="mt30 of_h" v-if=" this.payTypeForDIv === 'N' ">
								<h4 class="font-normal inline-block float-left mt5" style="width:20%">청구번호 *</h4>
                <select class="selectStyle2" style="width:72%" v-model="this.billId">
                  <option v-for="(option, i) in resultList" v-bind:value="option.billAcntNo" v-bind:key="i">
                    {{ option.custNm }}({{ option.billAcntNo }})
                  </option>
                </select>
							</div>
							<p class="mt10 lc-1 font-size12 color3" style="margin-left:20%">프로젝트별 결제조건(선/후불)을 선택할 수 있으며, 프로젝트 등록 후 선택된 <br>결제조건은 변경이 불가합니다.</p>
							<div class="mt30 of_h">
								<h4 class="font-normal inline-block mt5" style="width:20%">사용여부</h4>
								<input type="radio" name="useYn" value="Y" class="cBox" id="yes" checked=""> <label for="yes" class="payment mr30 font-size12">예</label>
								<input type="radio" name="useYn" value="N" class="cBox" id="no"> <label for="no" class="payment font-size12">아니요</label>							
							</div>

              <div class="mt15 of_h">
								<h4 class="font-normal inline-block float-left" style="width:20%">재발송 제목</h4>
								<div style="width:78%" class="of_h">
									<input id="resendTitle" type="text" class="inputStyle float-left" >
								</div>
							</div>
              <div class="mt30 of_h">
								<h4 class="font-normal inline-block mt5" style="width:20%">개별빌링 여부</h4>
								<input type="radio" name="subbillYn" value="Y" class="cBox" id="subbillYnY" @click="fnCheckSubblillYn()" checked=""> <label for="subbillYnY" class="payment mr30 font-size12">예</label>
								<input type="radio" name="subbillYn" value="N" class="cBox" id="subbillYnN" @click="fnCheckSubblillYn()"> <label for="subbillYnN" class="payment font-size12">아니요</label>							
							</div>
              <div class="mt30 of_h">
								<h4 class="font-normal inline-block mt5" style="width:20%">개별빌링 시작일</h4>
								<Calendar calendarId="subbillStartDay" classProps="datepicker inputStyle maxWidth200"></Calendar>
							</div>
              
              <!-- classProps="datepicker inputStyle maxWidth200" :initDate="searchData.searchEndDate" -->



							<div class="mt30 of_h">
								<h4 class="font-normal inline-block mt15" style="width:20%">사용채널선택</h4>
								<table  id="admin_sub03_1_project1" class="table_skin1 tbl-striped-odd mt0 float-right" style="width:80%">
                  <colgroup>
                    <col style="width:50%">
                    <col>
                  </colgroup>
									<tbody>
                    <tr>
                      <td class="text-left end">RCS</td>
                      <td class="text-right end">
                        <input type="radio" name="radioRcs" value="Y" class="cBox" id="yes1" checked=""> <label for="yes1" class="payment mr30 font-size12">사용</label>
                        <input type="radio" name="radioRcs" value="N" class="cBox" id="no1"> <label for="no1" class="payment font-size12">미사용</label>	
                      </td>							
                    </tr>
                    <tr>
                      <td class="text-left end">SMS/MMS</td>
                      <td class="text-right end">
                        <input type="radio" name="radioMms" value="Y" class="cBox" id="yes2" checked=""> <label for="yes2" class="payment mr30 font-size12">사용</label>
                        <input type="radio" name="radioMms" value="N" class="cBox" id="no2"> <label for="no2" class="payment font-size12">미사용</label>	
                      </td>							
                    </tr>
                    <tr>
                      <td class="text-left end">PUSH</td>
                      <td class="text-right end">
                        <input type="radio" name="radioPush" value="Y" class="cBox" id="yes3" checked=""> <label for="yes3" class="payment mr30 font-size12">사용</label>
                        <input type="radio" name="radioPush" value="N" class="cBox" id="no3"> <label for="no3" class="payment font-size12">미사용</label>	
                      </td>							
                    </tr>
                    <tr>
                      <td class="text-left end">카카오</td>
                      <td class="text-right end">
                        <input type="radio" name="radioKko" value="Y" class="cBox" id="yes4" checked=""> <label for="yes4" class="payment mr30 font-size12">사용</label>
                        <input type="radio" name="radioKko" value="N" class="cBox" id="no4"> <label for="no4" class="payment font-size12">미사용</label>	
                      </td>							
                    </tr>
                    <tr>
                      <td class="text-left end">MO</td>
                      <td class="text-right end">
                        <input type="radio" name="radioMo" value="Y" class="cBox" id="yes6" checked=""> <label for="yes6" class="payment mr30 font-size12">사용</label>
                        <input type="radio" name="radioMo" value="N" class="cBox" id="no6"> <label for="no6" class="payment font-size12">미사용</label>	
                      </td>							
                    </tr>
									</tbody>
								</table>
								
							</div>
							
						</div>
					
					</div>
					<div class="text-center mt40">
						<a @click="fnSave" class="btnStyle3 black font14" activity="SAVE">등록</a>
            <a @click="fnClose" ref="closeBtn" class="btnStyle3 white font14">닫기</a>						
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import projectApi from '../service/projectApi'
import tokenSvc from '@/common/token-service';
import Calendar from "@/components/Calendar.vue";

import confirm from "@/modules/commonUtil/service/confirm"

export default {
  data() {
    return {
      resultList : [],
      billId : '',
      payTypeForDIv : 'Y'
    }
  },
  components: {
    Calendar
  },
  props: {
    save_status : {
      type: String,
      require: false,
    },
    row_data : {
      type: Object,
      require: false,
    }
  },
  watch: {
    row_data: function(newVal, oldVal) {
      if(this.save_status === 'C'){
        jQuery("#projectName").val('');
        jQuery("#projectDesc").val('');
        jQuery('input:radio[name=payType]:input[value="Y"]').prop("checked", true);
        jQuery('input:radio[name=useYn]:input[value="Y"]').prop("checked", true);

        jQuery("#resendTitle").val('');
        jQuery('input:radio[name=subbillYn]:input[value="Y"]').prop("checked", true);
        jQuery("#subbillStartDay").val('');

        jQuery('input:radio[name=radioRcs]:input[value="Y"]').prop("checked", true);
        jQuery('input:radio[name=radioMms]:input[value="Y"]').prop("checked", true);
        jQuery('input:radio[name=radioPush]:input[value="Y"]').prop("checked", true);
        jQuery('input:radio[name=radioKakao]:input[value="Y"]').prop("checked", true);
        jQuery('input:radio[name=radioMo]:input[value="Y"]').prop("checked", true);
        
      } else if(this.save_status === 'R' || this.save_status === 'U'){
        jQuery("#projectName").val(this.row_data.projectName);
        jQuery("#projectDesc").val(this.row_data.projectDesc);
        jQuery('input:radio[name=payType]:input[value="' + this.row_data.payType + '"]').prop("checked", true);
        this.fnSelectBillIdForApi(this.row_data.payType);

        jQuery('input:radio[name=useYn]:input[value="' + this.row_data.useYn + '"]').prop("checked", true);

        jQuery("#resendTitle").val(this.row_data.resendTitle);
        jQuery('input:radio[name=subbillYn]:input[value="' + this.row_data.subbillYn + '"]').prop("checked", true);
        jQuery("#subbillStartDay").val(this.row_data.subbillStartDay);

        jQuery('input:radio[name=radioRcs]:input[value="' + this.row_data.radioYn + '"]').prop("checked", true);
        jQuery('input:radio[name=radioMms]:input[value="' + this.row_data.smsmmsYn + '"]').prop("checked", true);
        jQuery('input:radio[name=radioPush]:input[value="' + this.row_data.pushYn + '"]').prop("checked", true);
        jQuery('input:radio[name=radioKakao]:input[value="' + this.row_data.kakaoYn + '"]').prop("checked", true);
        jQuery('input:radio[name=radioMo]:input[value="' + this.row_data.moYn + '"]').prop("checked", true);
      }
    }
  },
  mounted() {
    var vm = this;
  },
  methods: {
    // 닫기
    fnClose(){
      jQuery("#projectPop").modal("hide");
    },
    // 개별빌링 체크
    fnCheckSubblillYn(){
      var value = jQuery("input[name='subbillYn']:checked").val();
      if( value === 'N' ){
        jQuery("#subbillStartDay").val("")
        jQuery("#subbillStartDay").prop("disabled", true);
      } else {
        jQuery("#subbillStartDay").prop("disabled", false);
      }
    },
    fnSelectBillIdForApi(payType){

      this.payTypeForDIv = payType;

      var params = {
      };

      projectApi.selectBillIdForApi(params).then(response =>{
        var result = response.data;
        console.log(response);
        console.log(response.data.data.resultList);
        if(result.success) {
          this.resultList = response.data.data.resultList;

          if(this.save_status === 'C'){
            this.billId = response.data.data.resultList[0].billAcntNo;
          } else {
            this.billId = this.row_data.billId;
          }
        }
      });
    },
    // 등록, 수정
    fnSave(){
      var params = {
          "projectId"      : this.row_data.projectId,
          "projectName"    : jQuery("#projectName").val(),
          "projectDesc"    : jQuery("#projectDesc").val(),
          "payType"        : jQuery("input[name='payType']:checked").val(),
          "billId"         : this.billId,
          "useYn"          : jQuery("input[name='useYn']:checked").val(),
          "resendTitle"    : jQuery("#resendTitle").val(),
          "subbillYn"      : jQuery("input[name='subbillYn']:checked").val(),
          "subbillStartDay": jQuery("#subbillStartDay").val(),
          "radioRcs"       : jQuery("input[name='radioRcs']:checked").val(),
          "radioMms"       : jQuery("input[name='radioMms']:checked").val(),
          "radioPush"      : jQuery("input[name='radioPush']:checked").val(),
          "radioKko"       : jQuery("input[name='radioKko']:checked").val(),
          "radioMo"        : jQuery("input[name='radioMo']:checked").val(),
          "sts"             : this.save_status,
          "userDto"         : tokenSvc.getToken().principal    
      };

      projectApi.saveProject(params).then(response =>{
        var result = response.data;

        if(result.success) {
          confirm.fnAlert("", "저장되었습니다.");
          // 닫기 버튼
          this.$refs.closeBtn.click();
          // 부모창 리스트 조회
          this.$parent.fnSearch();
          // 창닫기
        } else {
          confirm.fnAlert("", result.message);
        }
      });
    }
  },
}
</script>