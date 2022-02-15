<template>
  <div class="modal modalStyle" id="projectPop"
    tabindex="-1" role="dialog" aria-hidden="true"
  >
    <!-- @click="fnClose" -->
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2 v-if="this.save_status === 'C'">프로젝트 등록</h2>
            <h2 v-if="this.save_status != 'C'">프로젝트 상세</h2>
						<hr>
            <div v-if="corpInfo.feeType == 'POST'" class="color4 bgColor_f1 pd20">
							<ul class="text-left dotList">
								<li>부서별 청구를 하기 위해서는 부서별 빌링 여부를 [예]로 선택해 주시고 프로젝트를<br>생성해 주십시오. <br>(이후 정산의 부서별 청구 메뉴를 통해 부서별로 청구서를 생성 할 수 있습니다.)</li>
							</ul>
						</div>
						<div class="clear mt20">
							<h5 class="inline-block" style="width:25%">프로젝트명 *</h5>
							<div style="width:75%" class="float-right">
									<input id="projectName" type="text" class="inputStyle float-left" title="프로젝트명 입력란" maxlength="25">
							</div>
							<div class="of_h consolMarginTop">
                <h5 class="inline-block" style="width:25%">프로젝트 설명</h5>
								<div style="width:75%" class="float-right">
									<input id="projectDesc" type="text" class="inputStyle float-left" style="width:100%" title="프로젝트 설명 입력란" maxlength="100">
								</div>
							</div>
							<div class="of_h consolMarginTop">
                <h5 class="inline-block" style="width:25%">사용여부</h5>
								<input type="radio" name="useYn" value="Y" class="cBox" id="yes" checked=""> <label for="yes" class="payment mr30 font-size12">예</label>
								<input type="radio" name="useYn" value="N" class="cBox" id="no"> <label for="no" class="payment font-size12">아니요</label>							
							</div>

              <div class="clear consolMarginTop">
                <h5 class="inline-block" style="width:25%">재발송 제목 <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:340px">RCS 발송 실패 시 문자로 보낼 때 중복 가능성이 있으면 문자 내용 앞에 자동 추가되는 문구입니다.<br>(RCS의 문자 처리 응답이 늦는 경우가 가끔 있는데, 응답이 늦을 경우 실패로 보고 문자로 전송 시 재 전송의 가능성이 있으므로 재발송 제목을 추가하여 보냅니다.)</span></i></h5>
								<div style="width:75%" class="float-right">
									<input id="resendTitle" type="text" class="inputStyle float-left" maxlength="4" placeholder="최대 4글자까지 가능합니다. 예: re), 재)" style="width:240px;">
								</div>
							</div>
              <div v-if="corpInfo.feeType == 'POST'" class="clear consolMarginTop" >
                <h5 class="inline-block" style="width:25%">부서별 빌링 여부 <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:340px">말풍선 내용 없음</span></i></h5>
								<input type="radio" name="subbillYn" v-model="subbillStartDayForDiv" value="Y" class="cBox" id="subbillYnY"> <label for="subbillYnY" class="payment mr30 font-size12">예</label>
								<input type="radio" name="subbillYn" v-model="subbillStartDayForDiv" value="N" class="cBox" id="subbillYnN"> <label for="subbillYnN" class="payment font-size12">아니요</label>							
							</div>
              <div v-show="corpInfo.feeType == 'POST' && subbillStartDayForDiv == 'Y'" class="of_h consolMarginTop">
                <h5 class="inline-block" style="width:25%">부서별 빌링 시작일</h5>
								<Calendar calendarId="subbillStartDay" classProps="datepicker inputStyle maxWidth200"></Calendar>
							</div>
							<div class="clear consolMarginTop">
                <h5 class="inline-block" style="width:25%">사용채널
                  <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:340px"> 
                    <ul class="text-left dotList">
                      <li>문자 채널은 기본 사용 채널입니다.</li>
                      <li>RCS, PUSH, 카카오 채널은 필요에 따라 미사용으로 설정 가능합니다.</li>
                      <li>MO는 선불일 경우 사용이 불가 합니다.</li>
                    </ul>
                  </span></i>
                </h5>
								<table  id="admin_sub03_1_project1" class="table_skin1 tbl-striped-odd mt0 float-right" style="width:75%">
                  <colgroup>
                    <col style="width:50%">
                    <col>
                  </colgroup>
									<tbody>
                    <tr>
                      <td class="text-left end">RCS</td>
                      <td class="text-right end">
                        <input type="radio" name="radioRcs" value="Y" class="cBox" id="yes1" checked="" disabled> <label for="yes1" class="mr30">사용</label>
                        <input type="radio" name="radioRcs" value="N" class="cBox" id="no1" disabled> <label for="no1" class=" mr30">미사용</label>	
                      </td>							
                    </tr>
                    <tr>
                      <td class="text-left end">문자</td>
                      <td class="text-right end">
                        <input type="radio" name="radioMms" value="Y" class="cBox" id="yes2" checked="" disabled> <label for="yes2" style="margin-right:115px !important">사용</label>
                      </td>							
                    </tr>
                    <tr>
                      <td class="text-left end">PUSH</td>
                      <td class="text-right end">
                        <input type="radio" name="radioPush" value="Y" class="cBox" id="yes3" checked=""> <label for="yes3" class="mr30">사용</label>
                        <input type="radio" name="radioPush" value="N" class="cBox" id="no3"> <label for="no3" class=" mr30">미사용</label>	
                      </td>							
                    </tr>
                    <tr>
                      <td class="text-left end">카카오</td>
                      <td class="text-right end">
                        <input type="radio" name="radioKko" value="Y" class="cBox" id="yes4" checked=""> <label for="yes4" class="mr30">사용</label>
                        <input type="radio" name="radioKko" value="N" class="cBox" id="no4"> <label for="no4" class=" mr30">미사용</label>	
                      </td>							
                    </tr>
                    <tr>
                      <td class="text-left end">MO</td>
                      <td class="text-right end">
                        <input v-if="corpInfo.feeType == 'POST'" type="radio" name="radioMo" value="Y" class="cBox" id="yes6"> <label v-if="corpInfo.feeType == 'POST'" for="yes6" class="mr30">사용</label>
                        <input type="radio" name="radioMo" value="N" class="cBox" id="no6"> <label for="no6" class=" mr30">미사용</label>	
                      </td>							
                    </tr>
									</tbody>
								</table>
								
							</div>
							
						</div>
					
					</div>
					<div class="text-center mt40">
						<a @click="fnSave" class="btnStyle3 black font14 mr5" activity="SAVE">저장</a>
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
      payTypeForDIv : 'Y',
      subbillStartDayForDiv : 'N',
      moDisable : true,
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
    corpInfo : {
        type : Object,
        require : false
    },
    row_data : {
      type: Object,
      require: false,
    }
  },
  watch: {
    row_data: function(newVal, oldVal) {
      // 초기화 
      if(this.save_status === 'C'){
        jQuery("#projectName").val('');
        jQuery("#projectDesc").val('');
        jQuery('input:radio[name=payType]:input[value="PRE"]').prop("checked", true);
        jQuery('input:radio[name=useYn]:input[value="Y"]').prop("checked", true);

        jQuery("#resendTitle").val('');
        this.subbillStartDayForDiv = 'N'
        jQuery('input:radio[name=subbillYn]:input[value="N"]').prop("checked", true);
        jQuery("#subbillStartDay").val('');
        this.fnSelectBillIdForApi('PRE');

        jQuery('input:radio[name=radioRcs]:input[value="Y"]').prop("checked", true);
        jQuery('input:radio[name=radioMms]:input[value="Y"]').prop("checked", true);
        jQuery('input:radio[name=radioPush]:input[value="Y"]').prop("checked", true);
        jQuery('input:radio[name=radioKakao]:input[value="Y"]').prop("checked", true);
        jQuery('input:radio[name=radioMo]:input[value="N"]').prop("checked", true);
        
      } else if(this.save_status === 'R' || this.save_status === 'U'){
        jQuery("#projectName").val(this.row_data.projectName);
        jQuery("#projectDesc").val(this.row_data.projectDesc);
        jQuery('input:radio[name=payType]:input[value="' + this.row_data.payType + '"]').prop("checked", true);
        this.fnSelectBillIdForApi(this.row_data.payType);

        jQuery('input:radio[name=useYn]:input[value="' + this.row_data.useYn + '"]').prop("checked", true);

        jQuery("#resendTitle").val(this.row_data.resendTitle);
        jQuery('input:radio[name=subbillYn]:input[value="' + this.row_data.subbillYn + '"]').prop("checked", true);
        if( this.row_data.subbillYn === 'Y' ){
          this.subbillStartDayForDiv = 'Y';
          jQuery("#subbillStartDay").val(this.row_data.subbillStartDay);
        } else {
          this.subbillStartDayForDiv = 'N';
          jQuery("#subbillStartDay").val("");
        }
        jQuery('input:radio[name=radioRcs]:input[value="' + this.row_data.radioYn + '"]').prop("checked", true);
        jQuery('input:radio[name=radioMms]:input[value="' + this.row_data.smsmmsYn + '"]').prop("checked", true);
        jQuery('input:radio[name=radioPush]:input[value="' + this.row_data.pushYn + '"]').prop("checked", true);
        jQuery('input:radio[name=radioKko]:input[value="' + this.row_data.kakaoYn + '"]').prop("checked", true);
        jQuery('input:radio[name=radioMo]:input[value="' + this.row_data.moYn + '"]').prop("checked", true);
      }
    }
  },
  mounted() {
  },
  methods: {
    // 닫기
    fnClose(){
      jQuery("#projectPop").modal("hide");
    },
    // 부서별 별링 체크
    fnCheckSubblillYn(){
      var value = jQuery("input[name='subbillYn']:checked").val();
      if( value === 'N' ){
        jQuery("#subbillStartDay").val("")
        jQuery("#subbillStartDay").prop("disabled", true);
        this.subbillStartDayForDiv = 'N';
      } else {
        jQuery("#subbillStartDay").prop("disabled", false);
        this.subbillStartDayForDiv = 'Y';
      }
    },
    fnSelectBillIdForApi(payType){

      this.payTypeForDIv = payType;
      if( payType === 'PRE' ){
        this.moDisable = true;
        jQuery('input:radio[name=subbillYn]:input[value="N"]').prop("checked", true);
        this.subbillStartDayForDiv = "N";
        jQuery('input:radio[name=radioMo]:input[value="N"]').prop("checked", true);
      } else {
        this.moDisable = false;
        jQuery('input:radio[name=subbillYn]:input[value="N"]').prop("checked", true);
        this.subbillStartDayForDiv = "N";
      }

    },
    // 등록, 수정
    fnSave(){
      //벨리데이션
      if( jQuery("#projectName").val() === ''		|| jQuery("#projectName").val() === undefined  ){
          confirm.fnAlert("", "프로젝트명을 입력해주세요."); return false;
      }
      if( jQuery("input[name='subbillYn']:checked").val() === 'Y' && (jQuery("#subbillStartDay").val() === ''		|| jQuery("#subbillStartDay").val() === undefined ) ){
          confirm.fnAlert("", "부서별 별링 시작일을 입력해주세요.."); return false;
      }

      var subbillYn = jQuery("input[name='subbillYn']:checked").val();
      if( this.corpInfo.feeType === 'PRE' ){
          subbillYn = 'N';
      }

      var params = {
          "projectId"      : this.row_data.projectId,
          "projectName"    : jQuery("#projectName").val(),
          "projectDesc"    : jQuery("#projectDesc").val(),
          "payType"        : this.corpInfo.feeType,
          "billId"         : this.corpInfo.billId,
          "useYn"          : jQuery("input[name='useYn']:checked").val(),
          "resendTitle"    : jQuery("#resendTitle").val(),
          "subbillYn"      : subbillYn,
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