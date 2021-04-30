<template>
  <div v-if="directInputOpen" class="modalStyle" id="Direct-input" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:821px">
      <div class="modal-content">
        <div class="modal-body">
          <h2>수신자 직접입력</h2>
          <hr>
          <div class="of_h">
            <p class="color000 inline-block">입력 후 선택 버튼을 누르면 수신자에 추가됩니다. </p>
            <div class="float-right">
              <a @click="fnAddCu" class="btnStyle1 backBlack" title="수신자 추가">수신자 추가</a>
            </div>
          </div>

          <div class="of_h mt10">
            <div class="overflow-x-auto white-space-nowrap">

              <div class="inline-block mr20 WidthAuto" v-for="header in headerList" :key="header">
                <div class="text-center background-color6 colorfff consolLineheight">* {{header=='cuid' ? 'APP 로그인ID' : (header=='phone' ? '휴대폰번호' : header)}}</div>

                <div class="consolMarginTop" v-for="rowIdx in loopCnt" :key="rowIdx">
                  <div v-if="recvInfoLst.length >= rowIdx">
                    <input v-if="header == 'cuid' || header == 'phone'" type="text" class="inputStyle" 
                      :ref="header+'_'+rowIdx" v-model="recvInfoLst[rowIdx-1][header]">
                    <input v-else type="text" class="inputStyle" 
                      :ref="header+'_'+rowIdx" v-model="recvInfoLst[rowIdx-1].mergeData[header]">
                  </div>
                  <div v-else>
                    <input type="text" class="inputStyle" :ref="header+'_'+rowIdx">
                  </div>
                </div>
              </div>

            </div>
          </div>
          
          <div class="text-center mt20">
            <a @click="fnCallbackInputData" class="btnStyle1 backBlack" title="선택">선택</a>
            <a @click="fnClose" class="btnStyle1 backWhite" data-dismiss="modal" title="닫기">닫기</a>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import confirm from "@/modules/commonUtil/service/confirm.js";

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
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '수신자 직접입력';
      }
    },
  },
  data() {
    return {
      loopCnt: 3,
      headerList: [],
    }
  },
  watch: {
    directInputOpen(val){
      if(val){
        if(this.recvInfoLst.length > this.loopCnt) this.loopCnt = this.recvInfoLst.length;
      }
    },
    contsVarNms: {
      handler: function(){
        let headerList = [];
        if(this.requiredCuid) headerList.push('cuid');
        if(this.requiredCuPhone) headerList.push('phone');
        headerList = headerList.concat(this.contsVarNms);
        this.headerList = headerList.filter((item, pos) => headerList.indexOf(item) === pos);
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
        confirm.fnAlert(this.componentsTitle, alertStr+'는(은) 필수입니다.');
        return;
      }
      if(hasEmptyProp){
        confirm.fnAlert(this.componentsTitle, 'APP 로그인 ID나 휴대폰번호를 입력한 행은 빈값이 존재하면 안됩니다.');
        return;
      }
      if(recvInfoLst.length == 0){
        confirm.fnAlert(this.componentsTitle, '수신자 정보를 입력해주세요.');
        return;
      }
      
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
<style lang="scss">
$module: 'modalStyle';
.#{$module} {
  // This is modal bg
  background-color: rgba(0,0,0,.7);
  top: 0; right: 0; bottom: 0; left: 0;
  position: fixed;
  overflow: auto;
  margin: 0;
  z-index: 9999;
}
</style>