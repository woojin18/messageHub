<template>
  <div v-if="alimTalkTemplateOpen" @click.self="fnClose" class="modalStyle" id="Tamplet" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:1120px">
      <div class="modal-content">
        <div class="modal-body">
          <div>
            <h2>템플릿 선택</h2>
            <hr>
            <div class="of_h consoleCon">
              <div style="width:11%" class="float-left">
                <h4>*카카오 채널</h4>
              </div>
              <div style="width:42%">
                <!-- 카카오채널그룹관리 기능 제외
                <select class="selectStyle2" style="width:18%" v-model="searchData.senderKeyType" @change="fnSelectSenderKeyList">
                  <option value="S">일반</option>
                  <option value="G">그룹</option>
                </select>
                -->
                <select class="selectStyle2" style="width:53%" v-model="searchData.senderKey" @change="fnChgSenderKey" :disabled="propSenderKey!=''">
                  <option value="">선택하세요</option>
                  <option 
                    v-for="(senderKeyInfo, idx) in senderKeyList"
                    :key="idx"
                    :value="senderKeyInfo.senderKey"
                  >{{senderKeyInfo.kkoChId}}</option>
                </select>
              </div>
            </div>
            <div class="row row-no-margin">
              <div class="col-xs-4 border-line2 pd20" style="height:569px">
                <h4>템플릿 검색</h4>
                <div>
                  <input type="text" class="inputStyle" style="width:75%" v-model="searchData.searchText" @keypress.enter="fnSearch">
                  <a href="#" @click.prevent="fnSearch" class="btnStyle1 backLightGray float-right" title="메시지 내용 조회">조회</a>
                </div>
                <div>
                  <h4 class="inline-block">템플릿 명(템플릿 제목)</h4>
                  <h4 class="inline-block  float-right">총 {{totCnt}}건</h4>
                </div>
                <div class="border-line2" style="height:354px; overflow-y:scroll;">
                  <ul class="color4">
                    <li 
                      @click="fnGetTemplateInfo(idx, templateInfo.tmpltCode)"
                      v-for="(templateInfo, idx) in templateList" 
                      :key="idx" 
                      class="bb-ddd pd5"
                    >{{templateInfo.tmpltName}}</li>
                    <li v-if="templateList.length == 0" class="bb-ddd pd5 text-center">검색된 내용이 없습니다.</li>
                  </ul>
                </div>
              </div>
            
              <div class="col-xs-4 of_h">
                <div class="of_h">
                  <h5 style="width:41%" class="float-left ml30">카카오 채널</h5>
                  <h5 style="width:40%" class="float-right ml30 color4 word-break-all">{{templateData.kkoChId}}</h5>
                </div>
                <div class="of_h">
                  <h5 style="width:41%" class="float-left ml30">템플릿명</h5>
                  <h5 style="width:40%" class="float-right ml30 color4 word-break-all scroll-y_modal">{{templateData.tmpltName}}</h5>
                </div>
                <div v-if="templateData.emphasizeType == 'TEXT'" class="of_h">
                  <h5 style="width:41%" class="float-left ml30">템플릿강조제목</h5>
                  <h5 style="width:40%" class="float-right ml30 color4 word-break-all scroll-y_modal">{{templateData.tmpltEmpsTitle}}</h5>
                </div>
                <div v-if="templateData.emphasizeType == 'TEXT'" class="of_h">
                  <h5 style="width:41%" class="float-left ml30">템플릿강조부제</h5>
                  <h5 style="width:40%" class="float-right ml30 color4 word-break-all scroll-y_modal">{{templateData.tmpltEmpsSubTitle}}</h5>
                </div>
                <div class="of_h">
                  <h5 style="width:41%" class="float-left ml30">내용</h5>
                  <h5 style="width:40%" class="float-right ml30 color4 word-break-all scroll-y_modal">{{templateData.tmpltContent}}</h5>
                </div>
              </div>

              <div class="col-xs-4">
                <!-- phoneWrap -->
                <div class="phoneWrap">
                  <img src="@/assets/images/common/phoneMockup3.svg" alt="알림톡 템플릿">
                  <div class="phoneTextWrap3 scroll-y">
                    <div>
                      <p class="text-main"><i class="fal fa-envelope-open-text"></i> 알림톡 도착</p>
                      <div v-if="templateData.emphasizeType == 'TEXT'" class="text-sub-wrap" style="padding:10px;">
                        <p v-if="!$gfnCommonUtils.isEmpty(templateData.tmpltEmpsSubTitle)" class="text-sub_1">{{templateData.tmpltEmpsSubTitle}}</p>
                        <p v-if="!$gfnCommonUtils.isEmpty(templateData.tmpltEmpsTitle)" class="text-sub scroll-y3">{{templateData.tmpltEmpsTitle}}</p>
                      </div>
                      <div class="text-sub-wrap" style="padding:10px;">
                        <span><pre>{{templateData.tmpltContent}}</pre></span>
                      </div>
                      <!-- <p class="text-sub_2">템플릿 테스트입니다.<br>템플릿 테스트 템플릿 테스트 템플릿 테스트 템플릿 테스트</p> -->
                      <div v-for="(buttonInfo, idx) in templateData.buttonList" :key="idx">
                        <a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.name)" class="btnStyle1 backLightGray">{{buttonInfo.name}}</a>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- //phoneWrap -->
              </div>
            </div>  
            <div class="text-center">
              <a href="#" @click.prevent="fnSelectTemplate" class="btnStyle2 backBlack mr5" title="템플릿 선택">템플릿 선택</a>
              <a href="#" @click.prevent="fnClose" class="btnStyle2 backWhite" title="취소">취소</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import templateApi from "@/modules/template/service/templateApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: "alimTalkTemplatePopup",
  props: {
    alimTalkTemplateOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '템플릿 선택';
      }
    },
    propSenderKey : {
      type: String,
      require: false,
      default: ""
    }
  },
  data() {
    return {
      totCnt:0,
      senderKeyList: [],
      templateList:[],
      templateData:{buttonList:[]},
      searchData:{
        useCh : 'ALIMTALK',
        senderKeyType: 'S',
        senderKey: '',
        searchCondi : 'tmpltName',
        searchText : '',
        searchTmpltStatCodes: ['A'],
        pageNo : 1,
        listSize: 999999999
      }
    }
  },
  watch: {
    alimTalkTemplateOpen(val){
      if(!val){
        this.fnResetData();
      }
    }
  },
  mounted() {
    this.fnSelectSenderKeyList();
  },
  methods: {
    fnResetData(){
      Object.assign(this.$data, this.$options.data());
    },
    //템플릿선택
    fnSelectTemplate(){
      if(Object.keys(this.templateData).length == 0 || this.$gfnCommonUtils.isEmpty(this.templateData.tmpltCode)){
        confirm.fnAlert(this.componentsTitle, '템플릿을 선택해주세요.');
        return;
      }
      this.$parent.fnSetTemplateInfo(this.templateData);
      this.fnClose();
    },
    //템플릿 리스트 선택
    fnGetTemplateInfo(idx, tmpltCode){
      let sltData;
      if(this.templateList[idx].tmpltCode == tmpltCode){
        sltData = this.templateList[idx];
      } else {
        this.templateList.forEach(function(){
          if(this.tmpltCode == tmpltCode){
            sltData = this;
            return false;
          }
        });
      }

      sltData.buttonList = [];
      let tmpltInfo = JSON.parse(sltData.tmpltInfo);
      sltData.emphasizeType = tmpltInfo.templateEmphasizeType;
      sltData.tmpltEmpsTitle = tmpltInfo.templateTitle;
      sltData.tmpltEmpsSubTitle = tmpltInfo.templateSubtitle;
      sltData.tmpltContent = tmpltInfo.templateContent;
      sltData.buttonList = tmpltInfo.buttons;

      this.templateData = Object.assign({}, sltData);
      this.templateData.tmpltInfo = '';
    },
    fnChgSenderKey(){
      if(!this.$gfnCommonUtils.isEmpty(this.searchData.senderKey)){
        this.fnSearch();
      }
    },
    //템플릿 리스트 검색
    fnAlimTalkPopOpen() {
      let propSenderKey = this.propSenderKey;
      if(propSenderKey!="") {
        this.searchData.senderKey = propSenderKey;
        this.fnSearch();
      }
    },
    async fnSearch(){
      
      if(this.$gfnCommonUtils.isEmpty(this.searchData.senderKey)){
        confirm.fnAlert(this.componentsTitle, '카카오 채널 키를 선택해주세요.');
        return;
      }

      await templateApi.selectAlimTalkTmpltList(this.searchData).then(response =>{
        const result = response.data;
        if(result.success) {
          this.templateList = result.data;
          this.totCnt = result.pageInfo.totCnt;
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    //카카오톡 발신 프로필키 리스트 조회
    fnSelectSenderKeyList(){
      const params = {kkoSvc: this.searchData.useCh, senderKeyType: this.searchData.senderKeyType};
      templateApi.selectSenderKeyList(params).then(response => {
        const result = response.data;
        if(result.success) {
          this.senderKeyList = Object.assign([], result.data);
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    //팝업 닫기
    fnClose(){
      this.$emit('update:alimTalkTemplateOpen', false)
    }
  }
}
</script>