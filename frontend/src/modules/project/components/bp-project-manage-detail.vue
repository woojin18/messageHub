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
							<div class="mt15 of_h">
								<h4 class="font-normal inline-block float-left" style="width:20%">프로젝트 설명</h4>
								<div style="width:78%" class="of_h">
									<input id="projectDesc" type="text" class="inputStyle input2" style="width:100%" title="프로젝트 설명 입력란">
								</div>
							</div>
							<div class="mt30 of_h">
								<h4 class="font-normal inline-block float-left mt5" style="width:20%">결재조건 *</h4>
								<input type="radio" name="payType" value="Y" class="cBox" id="payment01" :disabled="this.save_status != 'C'"> <label for="payment01" class="payment mr30 font-size12">선불</label>
								<input type="radio" name="payType" value="N" class="cBox" id="payment02" :disabled="this.save_status != 'C'"> <label for="payment02" class="payment font-size12">후불</label>								
							</div>
							<p class="mt10 lc-1 font-size12 color3" style="margin-left:20%">프로젝트별 결제조건(선/후불)을 선택할 수 있으며, 프로젝트 등록 후 선택된 <br>결제조건은 변경이 불가합니다.</p>
							<div class="mt30 of_h">
								<h4 class="font-normal inline-block mt5" style="width:20%">사용여부</h4>
								<input type="radio" name="useYn" value="Y" class="cBox" id="yes" checked=""> <label for="yes" class="payment mr30 font-size12">예</label>
								<input type="radio" name="useYn" value="N" class="cBox" id="no"> <label for="no" class="payment font-size12">아니요</label>							
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
						<a @click="fnSave" class="btnStyle3 black font14" >등록</a>
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

export default {
  name: 'MenuManagePopup',
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
        $("#projectName").val('');
        $("#projectDesc").val('');
        $('input:radio[name=payType]:input[value="Y"]').prop("checked", true);
        $('input:radio[name=useYn]:input[value="Y"]').prop("checked", true);

        $('input:radio[name=radioRcs]:input[value="Y"]').prop("checked", true);
        $('input:radio[name=radioMms]:input[value="Y"]').prop("checked", true);
        $('input:radio[name=radioPush]:input[value="Y"]').prop("checked", true);
        $('input:radio[name=radioKakao]:input[value="Y"]').prop("checked", true);
        $('input:radio[name=radioMo]:input[value="Y"]').prop("checked", true);
        
      } else if(this.save_status === 'R' || this.save_status === 'U'){

console.log(this.save_status);
console.log(this.row_data);

        $("#projectName").val(this.row_data.projectName);
        $("#projectDesc").val(this.row_data.projectDesc);
        $('input:radio[name=payType]:input[value="' + this.row_data.payType + '"]').prop("checked", true);
        $('input:radio[name=useYn]:input[value="' + this.row_data.useYn + '"]').prop("checked", true);

        $('input:radio[name=radioRcs]:input[value="' + this.row_data.radioYn + '"]').prop("checked", true);
        $('input:radio[name=radioMms]:input[value="' + this.row_data.smsmmsYn + '"]').prop("checked", true);
        $('input:radio[name=radioPush]:input[value="' + this.row_data.pushYn + '"]').prop("checked", true);
        $('input:radio[name=radioKakao]:input[value="' + this.row_data.kakaoYn + '"]').prop("checked", true);
        $('input:radio[name=radioMo]:input[value="' + this.row_data.moYn + '"]').prop("checked", true);
      }
    }
  },
  mounted() {
    var vm = this;
  },
  methods: {
    // 닫기
    fnClose(){
      $("#projectPop").modal("hide");
    },
    // 프로젝트명 중복체크
    fnCheckProjectName(){
      var checkProjectName = $("#projectName").val();

      if( checkProjectName === '' ){
        alert("프로젝트명을 입력해주세요.");
        return;
      }

      var params = {
        "checkProjectName" : checkProjectName
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
          "projectId"      : this.row_data.projectId,
          "projectName"    : $("#projectName").val(),
          "projectDesc"    : $("#projectDesc").val(),
          "payType"        : $("input[name='payType']:checked").val(),
          "useYn"          : $("input[name='useYn']:checked").val(),
          "radioRcs"       : $("input[name='radioRcs']:checked").val(),
          "radioMms"       : $("input[name='radioMms']:checked").val(),
          "radioPush"      : $("input[name='radioPush']:checked").val(),
          "radioKko"       : $("input[name='radioKko']:checked").val(),
          "radioMo"        : $("input[name='radioMo']:checked").val(),
          "sts"             : this.save_status,
          "userDto"         : tokenSvc.getToken().principal    
      };

      projectApi.saveProject(params).then(response =>{
        var result = response.data;

        if(result.success) {
          alert("저장되었습니다.");
          // 닫기 버튼
          this.$refs.closeBtn.click();
          // 부모창 리스트 조회
          this.$parent.fnSearch();
          // 창닫기
        } else {
          alert(result.message);
        }
      });
    }
  },
}
</script>