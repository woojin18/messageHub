<template>
  <div>
    	<div class="modal modalStyle" id="deptInfoPop" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-body">
            <div class="of_h">
              <h2 v-if="state == 'add'">부서 등록</h2>
              <h2 v-else>부서 수정</h2>
              <hr>
              <div class="of_h consolMarginTop">
                <h5 class="inline-block" style="width:18%">부서코드</h5>
                <input v-model="deptCode" :disabled="state == 'edit'" type="text" class="inputStyle float-right" style="width:80%">
              </div>
              <div class="of_h consolMarginTop">
                <h5 class="inline-block" style="width:18%">부서명</h5>
                <input v-model="deptName" type="text" class="inputStyle float-right" style="width:80%">
              </div>
              <div class="of_h consolMarginTop">
                <h5 class="inline-block" style="width:18%">사용여부</h5>
                <div class="float-right" style="width:80%">
                  <input v-model="useYn" type="radio" name="use" value="Y" id="yes">
                  <label for="yes" class="mr30">사용</label>
                  <input v-model="useYn" type="radio" name="use" value="N" id="no">
                  <label for="no">미사용</label>
                </div>
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

    <div class="modal modalStyle" id="delDeptPop" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-body">					
            <h2>부서 삭제</h2>
            <hr>
            <p class="text-center">해당 부서를 삭제하시겠습니까?</p>					
            <div class="text-center mt20">
              <a @click="fnDelDept" class="btnStyle1 backBlack mr5">삭제</a>
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
import confirm from "@/modules/commonUtil/service/confirm"
import tokenSvc from '@/common/token-service'

export default {
  name: 'deptPop',
  data() {
    return {
      deptCode: "",
      deptName: "",
      useYn: "",
    }
  },
  props: {
    deptInfo: {
      type: Object
    },
    ucubeData: {
      type: Array
    },
    popReset: {
      type: Number
    },
    state: {
      type: String
    }
  },
  watch: {
    popReset() {
      this.fnReset();
    }
  },
  mounted() {
  },
  methods: {
    fnReset: function() {
      this.deptCode = this.deptInfo.deptCode;
      this.deptName = this.deptInfo.deptName;
      this.useYn = this.deptInfo.useYn;
    },

    fnSaveDept: function() {
      if(!this.deptCode) {
        confirm.fnAlert("", "부서코드를 입력해주세요.");
      } else if(!this.deptName) {
        confirm.fnAlert("", "부서명를 입력해주세요.");
      }

      var params = {
        "corpId" : tokenSvc.getToken().principal.corpId,
        "deptCode" : this.deptCode,
        "deptName": this.deptName,
        "useYn" : this.useYn,
        "billId" : this.billId,
        "state" : this.state
      };

      cashApi.saveProjectSubBillCode(params).then(response => {
        var result = response.data;
        if(result.success) {
          this.$parent.fnSelectDeptInfo();
          this.fnCloseDept();
        } else {
          confirm.fnAlert("", result.message);
        }
      });
    },

    fnCloseDept: function() {
      jQuery("#deptInfoPop").modal("hide");
    },

    fnDelDept: function() {
      if(this.useYn != "N") {
        confirm.fnAlert("", "사용여부가 아니오인 부서만 삭제할 수 있습니다.");
        this.fnCloseDelDeptPop();
      } else {
        var params = {
          "corpId" : tokenSvc.getToken().principal.corpId,
          "deptCode" : this.deptCode
        };

        cashApi.deleteProjectSubBillCode(params).then(response => {
          var result = response.data;
          if(result.success) {
            confirm.fnAlert("", "등록되었습니다.");
            this.$parent.fnSelectDeptInfo();
            this.$parent.fnSelectUcubeInfo();
            this.fnSelectUcubePopInfo();
            this.fnCloseDelDeptPop();
          } else {
            confirm.fnAlert("", result.message);
          }
        });
      }
    },

    fnCloseDelDeptPop: function() {
      jQuery("#delDeptPop").modal("hide");
    }
  }
}
</script>
