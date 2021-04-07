<template>
  <div class="modal fade modalStyle" id="confirm"
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
									<input id="project_name" type="text" class="inputStyle float-left" title="프로젝트명 입력란">
								</div>
							</div>
							<div class="mt15 of_h">
								<h4 class="font-normal inline-block float-left" style="width:20%">프로젝트 설명</h4>
								<div style="width:78%" class="of_h">
									<input id="project_desc" type="text" class="inputStyle input2" style="width:100%" title="프로젝트 설명 입력란">
								</div>
							</div>
							<div class="mt30 of_h">
								<h4 class="font-normal inline-block float-left mt5" style="width:20%">결재조건 *</h4>
								<input type="radio" name="pay_type" value="Y" class="cBox" id="payment01" :disabled="this.save_status != 'C'"> <label for="payment01" class="payment mr30 font-size12">선불</label>
								<input type="radio" name="pay_type" value="N" class="cBox" id="payment02" :disabled="this.save_status != 'C'"> <label for="payment02" class="payment font-size12">후불</label>								
							</div>
							<p class="mt10 lc-1 font-size12 color3" style="margin-left:20%">프로젝트별 결제조건(선/후불)을 선택할 수 있으며, 프로젝트 등록 후 선택된 <br>결제조건은 변경이 불가합니다.</p>
							<div class="mt30 of_h">
								<h4 class="font-normal inline-block mt5" style="width:20%">사용여부</h4>
								<input type="radio" name="use_yn" value="Y" class="cBox" id="yes" checked=""> <label for="yes" class="payment mr30 font-size12">예</label>
								<input type="radio" name="use_yn" value="N" class="cBox" id="no"> <label for="no" class="payment font-size12">아니요</label>							
							</div>
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
                        <input type="radio" name="radio_rcs" value="Y" class="cBox" id="yes1" checked=""> <label for="yes1" class="payment mr30 font-size12">사용</label>
                        <input type="radio" name="radio_rcs" value="N" class="cBox" id="no1"> <label for="no1" class="payment font-size12">미사용</label>	
                      </td>							
									  </tr>
									  <tr>
                      <td class="text-left end">SMS/MMS</td>
                      <td class="text-right end">
                        <input type="radio" name="radio_mms" value="Y" class="cBox" id="yes2" checked=""> <label for="yes2" class="payment mr30 font-size12">사용</label>
                        <input type="radio" name="radio_mms" value="N" class="cBox" id="no2"> <label for="no2" class="payment font-size12">미사용</label>	
                      </td>							
									  </tr>
									  <tr>
                      <td class="text-left end">PUSH</td>
                      <td class="text-right end">
                        <input type="radio" name="radio_push" value="Y" class="cBox" id="yes3" checked=""> <label for="yes3" class="payment mr30 font-size12">사용</label>
                        <input type="radio" name="radio_push" value="N" class="cBox" id="no3"> <label for="no3" class="payment font-size12">미사용</label>	
                      </td>							
									  </tr>
									  <tr>
                      <td class="text-left end">카카오</td>
                      <td class="text-right end">
                        <input type="radio" name="radio_kko" value="Y" class="cBox" id="yes4" checked=""> <label for="yes4" class="payment mr30 font-size12">사용</label>
                        <input type="radio" name="radio_kko" value="N" class="cBox" id="no4"> <label for="no4" class="payment font-size12">미사용</label>	
                      </td>							
									  </tr>
									  <tr>
                      <td class="text-left end">MO</td>
                      <td class="text-right end">
                        <input type="radio" name="radio_mo" value="Y" class="cBox" id="yes6" checked=""> <label for="yes6" class="payment mr30 font-size12">사용</label>
                        <input type="radio" name="radio_mo" value="N" class="cBox" id="no6"> <label for="no6" class="payment font-size12">미사용</label>	
                      </td>							
									  </tr>
									</tbody>
								</table>
								
							</div>
							
						</div>
					
					</div>
					<div class="text-center mt40">
						<a @click="fnSave" class="btnStyle3 black font14" >등록</a>
						<!-- <a @click="fnClose" class="btnStyle3 white font14">닫기</a>						 -->
            <a @click="fnClose" class="btnStyle3 white font14">닫기</a>						
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import projectApi from '../service/projectApi'
import tokenSvc from '@/common/token-service';

export default {
  name: 'MenuManagePopup',
  props: {
    visible: {
      type: Boolean,
      require: true,
      default: false
    },
    modal_title: {
      type: String,
      require: false,
    },
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
    visible: function(newVal, oldVal) {
      if(newVal) {
        $("#confirm").modal("show");
      } else {
        $("#confirm").modal("hide");
      }
    },
    row_data: function(newVal, oldVal) {
      if(this.save_status === 'C'){
        $("#project_name").val('');
        $("#project_desc").val('');
        $('input:radio[name=pay_type]:input[value="Y"]').prop("checked", true);
        $('input:radio[name=use_yn]:input[value="Y"]').prop("checked", true);

        $('input:radio[name=radio_rcs]:input[value="Y"]').prop("checked", true);
        $('input:radio[name=radio_mms]:input[value="Y"]').prop("checked", true);
        $('input:radio[name=radio_push]:input[value="Y"]').prop("checked", true);
        $('input:radio[name=radio_kakao]:input[value="Y"]').prop("checked", true);
        $('input:radio[name=radio_mo]:input[value="Y"]').prop("checked", true);
        
      } else if(this.save_status === 'R' || this.save_status === 'U'){
        $("#project_name").val(this.row_data.PROJECT_NAME);
        $("#project_desc").val(this.row_data.PROJECT_NAME);
        $('input:radio[name=pay_type]:input[value="' + this.row_data.PAY_TYPE + '"]').prop("checked", true);
        $('input:radio[name=use_yn]:input[value="' + this.row_data.USE_YN + '"]').prop("checked", true);

        $('input:radio[name=radio_rcs]:input[value="' + this.row_data.RCS_YN + '"]').prop("checked", true);
        $('input:radio[name=radio_mms]:input[value="' + this.row_data.SMSMMS_YN + '"]').prop("checked", true);
        $('input:radio[name=radio_push]:input[value="' + this.row_data.PUSH_YN + '"]').prop("checked", true);
        $('input:radio[name=radio_kakao]:input[value="' + this.row_data.KAKAO_YN + '"]').prop("checked", true);
        $('input:radio[name=radio_mo]:input[value="' + this.row_data.MO_YN + '"]').prop("checked", true);
      }
    }
  },
  mounted() {
    var vm = this;
  },
  methods: {
    // 닫기
    fnClose(){
      this.$emit('update:visible', false);
    },
    // 프로젝트명 중복체크
    fnCheckProjectName(){
      var check_project_name = $("#project_name").val();

      if( check_project_name === '' ){
        alert("프로젝트명을 입력해주세요.");
        return;
      }

      var params = {
        "check_project_name" : check_project_name
      }

      projectApi.checkProjectNameDuplicate(params).then(response =>{
        var result = response.data;
        if(result.success) {
          alert("사용가능한 프로젝트명입니다.");
          $("#checkYn").val("Y");
        } else {
          alert("중복된 프로젝트명입니다.");
          $("#checkYn").val("N");
        }
      });
    },
    // 등록, 수정
    fnSave(){
      var params = {
          "project_id"      : this.row_data.PROJECT_ID,
          "project_name"    : $("#project_name").val(),
          "project_desc"    : $("#project_desc").val(),
          "pay_type"        : $("input[name='pay_type']:checked").val(),
          "use_yn"          : $("input[name='use_yn']:checked").val(),
          "radio_rcs"       : $("input[name='radio_rcs']:checked").val(),
          "radio_mms"       : $("input[name='radio_mms']:checked").val(),
          "radio_push"      : $("input[name='radio_push']:checked").val(),
          "radio_kko"       : $("input[name='radio_kko']:checked").val(),
          "radio_mo"        : $("input[name='radio_mo']:checked").val(),
          "sts"             : this.save_status,
          "userDto"         : tokenSvc.getToken().principal    
      };

      projectApi.saveProject(params).then(response =>{
        var result = response.data;

        if(result.success) {
          alert("저장되었습니다.");
          // 부모창 리스트 조회
          this.$parent.fnSearch();
          // 창닫기
          this.fnClose();
        } else {
          alert(result.message);
        }
      });
    }
  },
}
</script>