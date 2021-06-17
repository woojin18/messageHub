<template>
  <div class="modal fade modalStyle" id="Inquiry" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          <div>
            <h2 class="color000 font-size18">1:1 문의</h2>
            <hr>
            <div class="of_h">
              <h5 class="inline-block" style="width:20%">이름입력 *</h5>
              <input type="text" class="inputStyle float-right" style="width:80%" title="이름 입력란" v-model="inputData.inputName" maxlength="20">
            </div>
            <div class="of_h consolMarginTop">
              <h5 class="inline-block" style="width:20%">휴대폰 번호 *</h5>
              <input 
                type="text" 
                class="inputStyle float-right" 
                style="width:80%" 
                title="휴대폰 번호 입력란"
                placeholder="-를 제외하고 입력해주세요."
                v-model="inputData.hpNumber" 
                maxlength="20"
              >
            </div>
            <div class="of_h consolMarginTop">
              <h5 class="inline-block" style="width:20%">E-mail *</h5>
              <div class="float-right" style="width:80%">
                <input 
                  type="text" 
                  class="inputStyle vertical-middle" 
                  style="width:24%" 
                  title="이메일 입력란" 
                  v-model="inputData.emailId" 
                  maxlength="20"
                >
                <span class="ml10 mr10">@</span>
                <input type="text" 
                  class="inputStyle" 
                  style="width:24%" 
                  title="직접입력"
                  :disabled="!$gfnCommonUtils.isEmpty(sltDomain)"
                  v-model="inputData.emailDomain" 
                  maxlength="20"
                >
                <select class="selectStyle2 float-right" style="width:40%" title="메일 선택란" v-model="sltDomain" @change="fnChgMailDomain">
                  <option value="">직접입력</option>
                  <option value="gmail.com">gmail.com</option>
                  <option value="naver.com">naver.com</option>
                  <option value="daum.net">daum.net</option>
                  <option value="hotmail.com">hotmail.com</option>
                  <option value="nate.com">nate.com</option>
              </select>
              </div>
            </div>
            <div class="of_h consolMarginTop">
              <h5 class="inline-block" style="width:20%">문의종류 *</h5>
              <select class="selectStyle2 float-right" style="width:80%" title="문의종류 선택란" v-model="inputData.questType">
                <option value="">선택</option>
                <option v-for="(inqueiryType, idx) in inqueiryTypeList" :key="idx" :value="inqueiryType.codeVal1">{{inqueiryType.codeName1}}</option>
              </select>
            </div>
            <div class="of_h consolMarginTop">
              <h5 class="inline-block" style="width:20%">제목 *</h5>
              <input 
                type="text" 
                class="inputStyle float-right" 
                style="width:80%" 
                title="제목 입력란"
                v-model="inputData.title" 
                maxlength="100"
              >
            </div>
            <div class="of_h consolMarginTop">
              <h5 class="inline-block" style="width:20%">내용 *</h5>
              <div class="float-right" style="width:80%">
                <textarea 
                  class="textareaStyle height120" 
                  placeholder=""
                  v-model="inputData.content" 
                  maxlength="4000"
                ></textarea>
              </div>
            </div>
            
          </div>
          <div class="text-center mt20">
            <a href="#" 
              @click.prevent="fnRegisterInquiry" 
              class="btnStyle2 backBlack" 
              title="문의신청"
            >문의신청</a>
          </div>
        </div>
      </div>
      <div class="PopupBtn_Wrap">
        <a class="PopupBtn" data-dismiss="modal" ref="btnClose">
          <img src="@/assets/images/common/btn_close.png" alt="팝업 닫기" class="PopupClose">
        </a>
      </div>
    </div>
    
  </div>
</template>

<script>
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import customereApi from "@/modules/customer/service/customerApi.js";

export default {
  name: "inquiryPopup",
  props: {
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '1:1 문의';
      }
    },
  },
  data() {
    return {
      inqueiryTypeList: [],
      sltDomain : '',
      inputData: {
        inputName: '',
        hpNumber : '',
        emailId : '',
        emailDomain : '',
        questType : '',
        email : '',
        title : '',
        content :'',
      }
    }
  },
  watch: {
    'inputData.hpNumber'(){
      return this.inputData.hpNumber = this.inputData.hpNumber.replace(/[^0-9]/g, '');
    }
  },
  methods: {
    fnIsValid(){
      if(!this.inputData.inputName){
        confirm.fnAlert(this.componentsTitle, '이름을 입력해주세요.');
        return false;
      }
      if(!this.inputData.hpNumber){
        confirm.fnAlert(this.componentsTitle, '휴대폰 번호를 입력해주세요.');
        return false;
      }
      if(this.inputData.hpNumber.length < 10){
        confirm.fnAlert(this.componentsTitle, '잘못된 휴대폰 번호 입니다.');
        return false;
      }
      if(!this.inputData.emailId || !this.inputData.emailDomain){
        confirm.fnAlert(this.componentsTitle, 'E-mail 을 입력해주세요.');
        return false;
      }
      this.inputData.email = this.inputData.emailId + '@' + this.inputData.emailDomain;
      if(!this.inputData.questType){
        confirm.fnAlert(this.componentsTitle, '문의종류를 선택해주세요.');
        return false;
      }
      if(!this.inputData.title){
        confirm.fnAlert(this.componentsTitle, '제목을 입력해주세요.');
        return false;
      }
      if(!this.inputData.content){
        confirm.fnAlert(this.componentsTitle, '내용을 입력해주세요.');
        return false;
      }
      
      return true;
    },
    fnRegisterInquiry(){
      if(this.fnIsValid() == false) return;
      eventBus.$on('callbackEventBus', this.fnProcRegisterInquiry);
      confirm.fnConfirm(this.componentsTitle, "문의 하시겠습니까?", "확인");
    },
    async fnProcRegisterInquiry(){
      let params = Object.assign({}, this.inputData);
      params.userId = this.inputData.inputName;

      await customereApi.insertQuestBoard(params).then(response => {
        const result = response.data;
        if(result.success) {
          confirm.fnAlert(this.componentsTitle, '저장되었습니다.');
          this.$refs.btnClose.click();
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnSelectFaqType(){
      const params = {
        codeTypeCd : "QNA_TYPE",
        useYN : "Y"
      };
      customereApi.selectCodeList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          this.inqueiryTypeList = Object.assign({}, result.data);
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnChgMailDomain(){
      this.inputData.emailDomain = this.sltDomain;
    },
    fnRestData(){
      Object.assign(this.$data, this.$options.data());
    }
  }
}
</script>