<template>
  <div v-if="testSendInputOpen" class="modalStyle" id="test" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:821px">
      <div class="modal-content">
        <div class="modal-body">
          <h2>테스트 발송</h2>
          <hr>
          <div :class="header=='cuid'? 'of_h' : 'of_h mt10'" v-for="header in headerList" :key="header">
            <div class="inline-block float-left text-center background-color6 colorfff consolLineheight" style="width:19%">
                * {{header=='cuid' ? 'APP 로그인ID' : header}}
            </div>
            <div v-for="rowIdx in loopCnt" :key="rowIdx">
              <div v-if="testRecvInfoLst.length >= rowIdx">
                <input v-if="header == 'cuid'" :ref="header+'_'+rowIdx" v-model="testRecvInfoLst[rowIdx-1][header]"
                  type="text" class="inputStyle float-left ml20" style="width:24%" :title="header+' 입력란'+rowIdx">
                <input v-else :ref="header+'_'+rowIdx" v-model="testRecvInfoLst[rowIdx-1].mergeData[header]"
                  type="text" class="inputStyle float-left ml20" style="width:24%" :title="header+' 입력란'+rowIdx">
              </div>
              <div v-else>
                <input v-if="header == 'cuid'" :ref="header+'_'+rowIdx"
                  type="text" class="inputStyle float-left ml20" style="width:24%" :title="header+' 입력란'+rowIdx">
                <input v-else :ref="header+'_'+rowIdx"
                  type="text" class="inputStyle float-left ml20" style="width:24%" :title="header+' 입력란'+rowIdx">
              </div>
            </div>
          </div>

          <div class="text-center mt20">
            <a @click="fnSendTestData" class="btnStyle1 backBlack" title="발송">발송</a>
            <a @click="fnClose" class="btnStyle1 backWhite" title="닫기">닫기</a>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: "testSendInputPopup",
  props: {
    testSendInputOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    contsVarNms : {
      type: Array,
      require: true
    },
    testRecvInfoLst : {
      type: Array,
      require: false,
      default: function() {
        return [];
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
    contsVarNms: {
      handler: function(){
        let headerList = [];
        headerList.push('cuid');
        headerList = headerList.concat(this.contsVarNms);
        this.headerList = headerList.filter((item, pos) => headerList.indexOf(item) === pos);
      }
    },
    testRecvInfoLst: {
      handler: function(){
        const vm = this;
        let testRecvInfo = {};
        let mergeData = {};
        while(this.testRecvInfoLst.length < 3){
          testRecvInfo = {};
          mergeData = {};

          vm.contsVarNms.forEach(function(varNm){
            mergeData[varNm] = '';
          });
          testRecvInfo.cuid = '';
          testRecvInfo.mergeData = mergeData;
          vm.testRecvInfoLst.push(testRecvInfo);
        }
      }
    }
  },
  methods: {
    //발송버튼 클릭시
    fnSendTestData(){
      const vm = this;
      let recvInfoLst = [];
      let recvInfo = {};
      let hasEmptyProp = false;
      
      for(let idx=1; idx<=this.loopCnt; idx++){
        recvInfo = {cuid:'',mergeData:{}};
        hasEmptyProp = false;

        //데이터 담기
        if(vm.fnIsEmpty(vm.$refs['cuid_'+idx][0].value)){
          continue;
        } else {
          recvInfo.cuid = vm.$refs['cuid_'+idx][0].value;
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

      if(hasEmptyProp){
        confirm.fnAlert(this.componentsTitle, 'APP 로그인 ID를 입력한 행은 빈값이 존재하면 안됩니다.');
        return;
      }
      if(recvInfoLst.length == 0){
        confirm.fnAlert(this.componentsTitle, '수신자 정보를 입력해주세요.');
        return;
      }
      
      //console.log(recvInfoLst);
      this.$parent.fnCallbackTestRecvInfoLst(recvInfoLst);
      this.fnClose();
    },
    //빈값확인
    fnIsEmpty(str){
      if(str) return false;
      else return true
    },
    //팝업 닫기
    fnClose(){
      this.loopCnt=3;
      this.$emit('update:testSendInputOpen', false)
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