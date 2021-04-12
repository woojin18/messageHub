<template>
  <div v-if="directInputOpen" class="modalStyle" id="Direct-input" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:821px">
      <div class="modal-content">
        <div class="modal-body">
          <h5 class="lc-1">수신자 직접입력</h5>
          <hr>
          <div class="of_h">
            <p class="font-size14 color000 inline-block mt15">입력 후 선택 버튼을 누르면 수신자에 추가됩니다. </p>
            <div class="float-right">
              <a @click="fnAddCu" class="btnStyle3 black font-size14 width120" title="수신자 추가">수신자 추가</a>
            </div>
          </div>
          
          <div class="of_h mt20">
            <div v-if="requiredCuid" class="inline-block float-left text-center" style="width:31.5%; background:#505050">
              <p class="inline-block colorfff" style="padding:12px">* APP 로그인 ID</p>
            </div>
            <div v-if="requiredCuPhone" class="inline-block float-left text-center ml20" style="width:31.5%; background:#505050">
              <p class="inline-block colorfff" style="padding:12px">* 휴대폰번호</p>
            </div>
            <div v-for="varNm in contsVarNms" :key="varNm" class="inline-block float-left text-center ml20" style="width:31.5%; background:#505050">
              <p class="inline-block colorfff" style="padding:12px">* {{varNm}}</p>
            </div>
          </div>

          <div v-for="rowIdx in loopCnt" :key="rowIdx" class="of_h mt10">
            <div v-if="recvInfoLst.length >= rowIdx">
              <input v-if="requiredCuid" type="text" class="inputStyle float-left" style="width:31.5%" title="APP 로그인ID 입력란" :ref="'cuid_'+rowIdx" v-model="recvInfoLst[rowIdx-1].cuid">
              <input v-if="requiredCuPhone" type="text" class="inputStyle float-left ml20" style="width:31.5%" title="휴대폰번호 입력란" :ref="'phone_'+rowIdx" v-model="recvInfoLst[rowIdx-1].phone">
              <input v-for="varNm in contsVarNms" :key="varNm" type="text" class="inputStyle float-left ml20" style="width:31.5%" :ref="varNm+'_'+rowIdx" v-model="recvInfoLst[rowIdx-1].mergeData[varNm]">
            </div>
            <div v-else>
              <input v-if="requiredCuid" type="text" class="inputStyle float-left" style="width:31.5%" title="APP 로그인ID 입력란1" :ref="'cuid_'+rowIdx">
              <input v-if="requiredCuPhone" type="text" class="inputStyle float-left ml20" style="width:31.5%" title="휴대폰번호 입력란1" :ref="'phone_'+rowIdx">
              <input v-for="varNm in contsVarNms" :key="varNm" type="text" class="inputStyle float-left ml20" style="width:31.5%" :ref="varNm+'_'+rowIdx">
            </div>
          </div>
            
          <div class="text-center mt60">
            <a @click="fnCallbackInputData" class="btnStyle3 black font14" title="선택">선택</a>
            <a @click="fnClose" class="btnStyle3 white font14" data-dismiss="modal" title="닫기">닫기</a>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>



<script>
export default {
  name: "directInputPopup",
  props: {
    directInputOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    requiredCuPhone: {
      type: Boolean,
      require: true,
      default: true,
    },
    requiredCuid: {
      type: Boolean,
      require: true,
      default: true,
    },
    contsVarNms : {
      type: Array,
      require: true
    },
    recvInfoLst : {
      type: Array,
      require: false,
      default: function() {
        return [];
      }
    }
  },
  data() {
    return {
      loopCnt: 3,
    }
  },
  watch: {
    directInputOpen(val){
      if(val){
        if(this.recvInfoLst.length > this.loopCnt) this.loopCnt = this.recvInfoLst.length;
      }
    }
  },
  methods: {
    //수신자 추가
    fnAddCu(){
      ++this.loopCnt;
    },
    //빈값확인
    fnIsEmpty(str){
      if(str) return false;
      else return true
    },
    //빈오브젝트확인
    fnIsEmptyObj(obj){
      if(typeof obj === 'undefined') return true;
      if(Object.keys(obj).length === 0) return true;
      return false;
    },
    //직접입력 선택 버튼 클릭시
    fnCallbackInputData(){
      const vm = this;
      let recvInfoLst = [];
      let recvInfo = {};
      let hasEmptyKey = false;
      let hasEmptyProp = false;
      
      for(let idx=1; idx<=this.loopCnt; idx++){
        recvInfo = {phone:'',cuid:'',mergeData:{}};
        hasEmptyProp = false;

        //데이터 담기
        if(!(vm.requiredCuid && !vm.fnIsEmpty(vm.$refs['cuid_'+idx][0].value))
          && !(vm.requiredCuPhone && !vm.fnIsEmpty(vm.$refs['phone_'+idx][0].value))){
          continue;
        }
        if(vm.requiredCuid){
          if(vm.fnIsEmpty(vm.$refs['cuid_'+idx][0].value)){
            hasEmptyKey = true;
            break;
          } else {
            recvInfo.cuid = vm.$refs['cuid_'+idx][0].value;
          }
        } else {
          delete recvInfo.cuid;
        }
        if(vm.requiredCuPhone){
          if(vm.fnIsEmpty(vm.$refs['phone_'+idx][0].value)){
            hasEmptyKey = true;
            break;
          } else {
            recvInfo.phone = vm.$refs['phone_'+idx][0].value;
          }
        } else {
          delete recvInfo.phone;
        }
        
        vm.contsVarNms.forEach(function(varNm){
          if(vm.fnIsEmpty(vm.$refs[varNm+'_'+idx][0].value)){
            hasEmptyProp = true;
            return false;
          } else {
            recvInfo.mergeData[varNm] = vm.$refs[varNm+'_'+idx][0].value
          }
        });
        if(hasEmptyProp) break;
        recvInfoLst.push(recvInfo);
      }
      
      if(hasEmptyKey){
        const alertStr = (this.requiredCuid ? 'APP 로그인' : '') + (this.requiredCuid && this.requiredCuPhone ? ', ' : '') + (this.requiredCuPhone ? '휴대폰번호' : '');
        alert(alertStr+'는(은) 필수입니다.');
        return;
      }
      if(hasEmptyProp){
        alert('APP 로그인 ID나 휴대폰번호를 입력한 행은 빈값이 존재하면 안됩니다.');
        return;
      }
      if(recvInfoLst.length == 0){
        alert('수신자 정보를 입력해주세요.');
        return;
      }
      
      //데이터 부모에게 던지자
      this.$parent.fnCallbackRecvInfoLst(recvInfoLst);
      this.fnClose();
    },
    //팝업 닫기
    fnClose(){
      this.loopCnt=3;
      this.$emit('update:directInputOpen', false)
    }
  }
}
</script>