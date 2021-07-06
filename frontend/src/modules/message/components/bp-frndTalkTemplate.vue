<template>
  <div v-if="frndTalkTemplateOpen" @click.self="fnClose" class="modalStyle" id="Tamplet" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:1120px">
      <div class="modal-content">
        <div class="modal-body consoleCon">
          <div>
            <h2>템플릿 선택</h2>
            <hr>
            <div class="row row-no-margin">
              <div class="col-xs-4 border-line2 pd20" style="height:569px">
                <h4 class="color000">템플릿 검색</h4>
                <div>
                  <input type="text" class="inputStyle" style="width:70%" v-model="searchData.searchText" @keypress.enter="fnSearch">
                  <a @click="fnSearch" class="btnStyle1 backLightGray float-right" style="width:28%" title="메시지 내용 조회">조회</a>
                </div>
                <div>
                  <h4 class="inline-block">템플릿 검색</h4>
                  <h4 class="color000 inline-block float-right">총 {{totCnt}}건</h4>
                </div>
                <div class="border-line2" style="height:354px; overflow-y:scroll;">
                  <ul class="color4">
                    <li @click="fnGetTemplateInfo(idx, templateInfo.tmpltId)" v-for="(templateInfo, idx) in templateList" :key="templateInfo.tmpltId" class="bb-ddd pd5">
                      {{templateInfo.tmpltName}}
                    </li>
                    <li v-if="templateList.length == 0" class="bb-ddd pd5 text-center">검색된 내용이 없습니다.</li>
                  </ul>
                </div>
              </div>
              
              <div class="col-xs-4 of_h">
                <div class="of_h">
                  <h5 style="width:41%" class="float-left ml30 color000">메시지구분</h5>
                  <h5 style="width:40%" class="float-right ml30 color4 word-break-all">{{templateData.msgKindName}}</h5>
                </div>
                <div class="of_h">
                  <h5 style="width:41%" class="float-left ml30 color000">템플릿명</h5>
                  <h5 style="width:40%" class="float-right ml30 color4 word-break-all scroll-y_modal">{{templateData.tmpltName}}</h5>
                </div>
                <div class="of_h">
                  <h5 style="width:41%" class="float-left ml30 color000">내용</h5>
                  <h5 style="width:40%" class="float-right ml30 color4 word-break-all scroll-y_modal">{{templateData.tmpltContent}}</h5>
                </div>
                <div class="of_h">
                  <h5 style="width:41%" class="float-left ml30 color000">이미지</h5>
                  <h5 style="width:40%" class="float-right ml30 color4 word-break-all">{{templateData.imgUrl}}</h5>
                </div>
              </div>
              <div class="col-xs-4">
                <!-- phoneWrap -->
                <div class="phoneWrap">
                  <img src="@/assets/images/common/phoneMockup2_1.svg" alt="프리 템플릿">
                  <div class="phoneTextWrap4 scroll-y">
                    <p v-if="templateData.msgKind == 'A'">[광고]</p>
                    <div class="mt5">
                      <div class="text-sub-wrap">
                        <!-- <p class="text-sub scroll-y">친구톡 발송테스트</p> -->
                        <div v-if="!$gfnCommonUtils.isEmpty(templateData.imgUrl)" class="phoneText2 mt10 text-center"
                          :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+templateData.imgUrl+');'">
                        </div>
                        <br v-if="$gfnCommonUtils.isEmpty(templateData.imgUrl)"/>
                        <span v-html="$gfnCommonUtils.newLineToBr(templateData.tmpltContent)"></span>
                        <div v-for="(buttonInfo, idx) in templateData.buttonList" :key="idx">
                          <a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.name)" class="btnStyle1 backLightGray">{{buttonInfo.name}}</a>
                        </div>
                      </div>
                      <p v-if="templateData.msgKind == 'A'" class="text-sub_2">수신거부: 홈 &gt; 친구차단</p>
                    </div>
                  </div>
                </div>
                <!-- //phoneWrap -->
              </div>
            </div>
          </div>
          <div class="text-center mt20">
            <a @click="fnSelectTemplate" class="btnStyle2 backBlack" data-toggle="modal" title="템플릿 선택">템플릿 선택</a>
            <a @click="fnClose" class="btnStyle2 backWhite" data-dismiss="modal" title="취소">취소</a> 
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
  name: "frndTalkTemplatePopup",
  components : {
  },
  props: {
    frndTalkTemplateOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '친구톡 템플릿 검색';
      }
    },
  },
  data() {
    return {
      totCnt:0,
      templateList:[],
      templateData:{buttonList:[]},
      searchData:{
        searchCondi: 'tmpltName',
        searchText: '',
        pageNo: 1,
        listSize: 999999999
      }
    }
  },
  watch: {
    frndTalkTemplateOpen(val){
      if(!val){
        this.fnResetData();
      }
    }
  },
  methods: {
    fnResetData(){
      Object.assign(this.$data, this.$options.data());
    },
    //템플릿선택
    fnSelectTemplate(){
      if(Object.keys(this.templateData).length == 0){
        confirm.fnAlert(this.componentsTitle, '템플릿을 선택해주세요.');
        return;
      }
      this.$parent.fnSetTemplateInfo(this.templateData);
      this.fnClose();
    },
    //템플릿 리스트 선택
    fnGetTemplateInfo(idx, tmpltId){
      if(this.templateList[idx].tmpltId == tmpltId){
        this.templateData = this.templateList[idx];
      } else {
        var vm = this;
        this.templateList.forEach(function(){
          if(this.tmpltId == tmpltId){
            vm.templateData = this;
            return false;
          }
        });
      }
      this.templateData.buttonList = JSON.parse(this.templateData.tmpltButtons);
    },
    //템플릿 리스트 검색
    async fnSearch(){
      await templateApi.selectFrndTalkList(this.searchData).then(response =>{
        const result = response.data;
        if(result.success) {
          this.templateList = result.data;
          this.totCnt = result.pageInfo.totCnt;
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    //팝업 닫기
    fnClose(){
      this.$emit('update:frndTalkTemplateOpen', false)
    }
  }
}
</script>