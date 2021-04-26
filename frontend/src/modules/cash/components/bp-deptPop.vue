<template>
  <div>
    	<div class="modal fade modalStyle" id="deptInfoPop" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-body">
            <div class="of_h">
              <h2>부서 등록</h2>
              <hr>
              <div class="of_h consolMarginTop">
                <h5 class="inline-block" style="width:18%">부서ID *</h5>
                <input v-model="deptCode" type="text" class="inputStyle float-right" style="width:80%">
              </div>
              <div class="of_h consolMarginTop">
                <h5 class="inline-block" style="width:18%">부서명</h5>
                <input v-model="deptName" type="text" class="inputStyle float-right" style="width:80%">
              </div>
              <div class="of_h consolMarginTop">
                <h5 class="inline-block" style="width:18%">사용여부</h5>
                <div class="float-right" style="width:80%">
                  <input type="radio" name="use" value="Y" id="yes" checked="">
                  <label for="yes" class="mr30">사용</label>
                  <input type="radio" name="use" value="N" id="no">
                  <label for="no">미사용</label>
                </div>
              </div>
              <div class="of_h consolMarginTop">
                <h5 class="inline-block" style="width:18%">청구ID</h5>
                <select class="selectStyle2 float-right" style="width:80%">
                  <option value="">청구ID를 선택해 주세요</option>
                </select>
              </div>
            </div>
              
            <div class="text-center mt20">
              <a @click="fnSaveDept" class="btnStyle1 backBlack mr5">등록</a>
              <a @click="fnCloseDept" class="btnStyle1 backWhite">취소</a>						
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="modal fade modalStyle" id="delDeptPop" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-body">					
            <h2>부서 삭제</h2>
            <hr>
            <p class="text-center">해당 부서를 삭제하시겠습니까?</p>					
            <div class="text-center mt20">
              <a @click="fnDelDept" class="btnStyle1 backBlack mr5">생성</a>
              <a @click="fnCloseDelDeptPop" class="btnStyle1 backWhite">취소</a>						
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import cashApi from "@/modules/cash/service/api"

export default {
  name: 'deptPop',
  data() {
    return {
      deptCode: "",
      deptName: "",
      useYn: ""
    }
  },
  props: {
    deptInfo: {
      type: Object
    }
  },
  watch: {
    deptInfo(val) {
      this.deptCode = val.deptCode;
      this.deptName = val.deptName;
      this.useYn = val.useYn;
    }
  },
  mounted() {
  },
  methods: {
    fnSaveDept: function() {
      alert(this.deptCode);
    },

    fnCloseDept: function() {
      jQuery("#deptInfoPop").modal("hide");
    },

    fnDelDept: function() {

    },

    fnCloseDelDeptPop: function() {
      jQuery("#delDeptPop").modal("hide");
    }
  }
}
</script>
