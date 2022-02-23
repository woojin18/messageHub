<template>
  <div v-if="testSendInputOpen" class="modalStyle" id="test" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:821px">
      <div class="modal-content">
        <div class="modal-body">
          <h2>테스트 발송</h2>
          <hr>
          <div class="of_h">
            <p class="color000 inline-block">테스트발송시 대체발송은 하지 않습니다.</p>
          </div>

          <div class="of_h mt10">
            <div class="overflow-x-auto white-space-nowrap">

              <div class="inline-block mr20 WidthAuto" v-for="header in headerList" :key="header">
                <div class="text-center background-color6 colorfff consolLineheight">* {{header=='cuid' ? 'APP 로그인ID' : (header=='phone' ? '휴대폰번호' : header)}}</div>
                <div class="consolMarginTop" v-for="rowIdx in loopCnt" :key="rowIdx">
                  <div v-if="testRecvInfoLst.length >= rowIdx">
                    <input v-if="header == 'phone'" type="text" class="inputStyle" 
                      :ref="header+'_'+rowIdx" v-model="testRecvInfoLst[rowIdx-1][header]" @keypress="fnOnlyNumber" @keydown="fnOnlyNumber" autocomplete="off">
                    <input v-else-if="header == 'cuid'" type="text" class="inputStyle" 
                      :ref="header+'_'+rowIdx" v-model="testRecvInfoLst[rowIdx-1][header]">
                    <input v-else type="text" class="inputStyle" 
                      :ref="header+'_'+rowIdx" v-model="testRecvInfoLst[rowIdx-1].mergeData[header]">
                  </div>
                  <div v-else>
                    <input v-if="header == 'phone'" type="text" class="inputStyle" :ref="header+'_'+rowIdx" @keypress="fnOnlyNumber" @keydown="fnOnlyNumber" autocomplete="off">
                    <input v-else type="text" class="inputStyle" :ref="header+'_'+rowIdx">
                  </div>
                </div>
              </div>

            </div>
          </div>


          <!-- 
          <div :class="idx == 0 ? 'of_h' : 'of_h mt10'" v-for="(header, idx) in headerList" :key="header">
            <div class="inline-block float-left text-center background-color6 colorfff consolLineheight" style="width:19%">
                * {{header=='cuid' ? 'APP 로그인ID' : (header=='phone' ? '휴대폰번호' : header)}}
            </div>
            <div v-for="rowIdx in loopCnt" :key="rowIdx">
              <div v-if="testRecvInfoLst.length >= rowIdx">
                <input v-if="header == 'cuid' || header == 'phone'" :ref="header+'_'+rowIdx" v-model="testRecvInfoLst[rowIdx-1][header]"
                  type="text" class="inputStyle float-left ml20" style="width:24%" :title="header+' 입력란'+rowIdx">
                <input v-else :ref="header+'_'+rowIdx" v-model="testRecvInfoLst[rowIdx-1].mergeData[header]"
                  type="text" class="inputStyle float-left ml20" style="width:24%" :title="header+' 입력란'+rowIdx">
              </div>
              <div v-else>
                <input :ref="header+'_'+rowIdx"
                  type="text" class="inputStyle float-left ml20" style="width:24%" :title="header+' 입력란'+rowIdx">
              </div>
            </div>
          </div>
          -->

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
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '테스트 발송';
      }
    },
  },
  data() {
    return {
      testRecvInfoLst: [],
      loopCnt: 3,
      headerList: [],
    }
  },
  watch: {
    contsVarNms: {
      handler: function(){
        let headerList = [];
        if(this.requiredCuid) headerList.push('cuid');
        if(this.requiredCuPhone) headerList.push('phone');
        headerList = headerList.concat(this.contsVarNms);
        this.headerList = headerList.filter((item, pos) => headerList.indexOf(item) === pos);
      }
    },
    testRecvInfoLst: {
      //deep:true,
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
          //testRecvInfo.phone = '';
          testRecvInfo.mergeData = mergeData;
          vm.testRecvInfoLst.push(testRecvInfo);
        }

        // if(this.testRecvInfoLst.length == 3){
        //     this.testRecvInfoLst.map(function(value, key) {
        //       if(value.phone != ''){
        //         var val = value.phone.replace(/[^0-9]/g, '');
        //         let tmp = ''
        //         if( val.length < 4){
        //           tmp = val;
        //         } else if(val.length <= 7) {
        //           tmp += val.substr(0, 3);
        //           tmp += '-';
        //           tmp += val.substr(3);
        //         } else if(val.length == 8) {
        //           tmp += val.substr(0, 4);
        //           tmp += '-';
        //           tmp += val.substr(4);
        //         } else if(val.length < 10) {
        //             tmp += val.substr(0, 2);
        //             tmp += '-';
        //             tmp += val.substr(2, 3);
        //             tmp += '-';
        //             tmp += val.substr(5);
        //         } else if(val.length < 11) {
        //           if(val.substr(0, 2) =='02') { //02-1234-5678
        //             tmp += val.substr(0, 2);
        //             tmp += '-';
        //             tmp += val.substr(2, 4);
        //             tmp += '-';
        //             tmp += val.substr(6);
        //           } else { //010-123-4567
        //             tmp += val.substr(0, 3);
        //             tmp += '-';
        //             tmp += val.substr(3, 3);
        //             tmp += '-';
        //             tmp += val.substr(6);
        //           }
        //         } else { //010-1234-5678
        //           tmp += val.substr(0, 3);
        //           tmp += '-';
        //           tmp += val.substr(3, 4);
        //           tmp += '-';
        //           tmp += val.substr(7);
        //         }
        //         value.phone = tmp;
        //       }
        //     });
        // }
      }
    }
  },
  methods: {
    fnSetTestRecvInfoLst(testRecvInfoLst){
      this.testRecvInfoLst = Object.assign([], testRecvInfoLst);
    },
    //발송버튼 클릭시
    fnSendTestData(){
      const vm = this;
      let recvInfoLst = [];
      let recvInfo = {};
      let hasEmptyKey = false;
      let hasEmptyProp = false;
      const alertKeyStr = (this.requiredCuid ? 'APP 로그인' : '') + (this.requiredCuid && this.requiredCuPhone ? ', ' : '') + (this.requiredCuPhone ? '휴대폰번호' : '');

      for(let idx=1; idx<=this.loopCnt; idx++){
        recvInfo = {cuid:'',mergeData:{}};
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
            //recvInfo.phone = this.$gfnCommonUtils.hpNumberRemoveDash(vm.$refs['phone_'+idx][0].value);
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
        confirm.fnAlert(this.componentsTitle, alertKeyStr+'는(은) 필수입니다.');
        return;
      }
      if(hasEmptyProp){
        confirm.fnAlert(this.componentsTitle, alertKeyStr+'를(을) 입력한 행은 빈값이 존재하면 안됩니다.');
        return;
      }
      if(recvInfoLst.length == 0){
        confirm.fnAlert(this.componentsTitle, '수신자 정보를 입력해주세요.');
        return;
      }

      this.$parent.fnCallbackTestRecvInfoLst(recvInfoLst);
      this.fnClose();
    },
    fnOnlyNumber($event) {
      var keyCode = $event.which;
      if(keyCode != "8" && keyCode != "46") {
        if (!/\d/.test($event.key)) return $event.preventDefault();
      }
      // var val = this.$gfnCommonUtils.hpNumberAddDash(event.target.value);
      // event.target.value = val;
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