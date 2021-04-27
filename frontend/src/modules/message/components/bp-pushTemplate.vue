<template>
  <div v-if="pushTemplateOpen" @click.self="fnClose" class="modalStyle" id="Tamplet" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:1120px">
      <div class="modal-content">
        <div class="modal-body">
          <div>
            <h2>템플릿 선택</h2>
            <hr>
            <div class="row row-no-margin">
              <div class="col-xs-4 border-line2" style="height:569px; padding:30px">
                <h4 class="mt0">템플릿 검색</h4>
                <div>
                  <input type="text" class="inputStyle" style="width:75%" v-model="searchData.searchText">
                  <a @click="fnSearch" class="btnStyle1 backLightGray float-right" title="메시지 내용 조회">조회</a>
                </div>
                <div>
                  <h4>템플릿 검색 <span class="">총 {{totCnt}}건</span></h4>
                </div>
                <div class="border-line2" style="max-height:420px; overflow-y:scroll">
                  <ul class="color4">
                    <li @click="fnGetTemplateInfo(idx, templateInfo.tmpltId)" v-for="(templateInfo, idx) in templateList" :key="templateInfo.tmpltId" class="bb-ddd pd5">
                      {{templateInfo.tmpltName}}({{templateInfo.tmpltTitle}})
                    </li>
                  </ul>
                </div>
              </div>
              <div class="col-xs-4 of_h">
                <div class="of_h">
                  <h5 style="width:41%" class="float-left ml30 color000">메시지구분</h5>
                  <h5 style="width:40%" class="float-right color4 word-break-all">{{templateData.msgKindName}}</h5>
                </div>
                <div class="of_h">
                  <h5 style="width:41%" class="float-left ml30 color000">메시지타입</h5>
                  <h5 style="width:40%" class="float-right color4 word-break-all">{{templateData.msgTypeName}}</h5>
                </div>
                <div class="of_h">
                  <h5 style="width:41%" class="float-left ml30 color000">타 프로젝트 사용여부</h5>
                  <h5 style="width:40%" class="float-right color4 word-break-all">{{templateData.otherProjectUseYn}}</h5>
                </div>
                <div class="of_h">
                  <h5 style="width:41%" class="float-left ml30 color000">템플릿명</h5>
                  <h5 style="width:40%" class="float-right color4 word-break-all">{{templateData.tmpltName}}</h5>
                </div>
                <div class="of_h">
                  <h5 style="width:41%" class="float-left ml30 color000">제목</h5>
                  <h5 style="width:40%" class="float-right color4 word-break-all">{{templateData.tmpltTitle}}</h5>
                </div>
                <div class="of_h">
                  <h5 style="width:41%" class="float-left ml30 color000">내용</h5>
                  <h5 style="width:40%" class="float-right color4 word-break-all">{{templateData.tmpltContent}}</h5>
                </div>
                <div class="of_h">
                  <h5 style="width:41%" class="float-left ml30 color000">이미지</h5>
                  <h5 style="width:40%" class="float-right color4 word-break-all">{{templateData.imgUrl}}</h5>
                </div>
              </div>
              <div class="col-xs-4">
                <!-- phoneWrap -->
                <div class="phoneWrap">
                  <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
                  <div class="phoneTextWrap">
                    <div class="phoneText1">
                      <p v-if="fnIsEmpty(templateData.tmpltTitle)">제목</p>
                      <p v-else>{{templateData.tmpltTitle}}</p>
                    </div>
                    <div v-if="templateData.msgType == 'IMAGE' && fnIsEmpty(templateData.imgUrl)" class="phoneText2 mt10 text-center" style="padding:65px">
                      <i class="fas fa-image-polaroid" style="font-size:38px; color:#D5D5D5"></i>
                      <p class="font-size14 color3 mt15">이미지 영역</p>
                    </div>
                    <div v-if="templateData.msgType == 'IMAGE' && !fnIsEmpty(templateData.imgUrl)" class="phoneText2 mt10 text-center"
                      :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+templateData.imgUrl+');'">
                    </div>
                    <p v-if="templateData.msgKind != 'A' || (fnIsEmpty(templateData.tmpltContent) && fnIsEmpty(templateData.rcvblcNumber))" class="font-size14 color4 mt10">템플릿 내용</p>
                    <p v-else class="font-size14 color4 mt10">
                      {{templateData.tmpltContent}}
                      <br v-if="!fnIsEmpty(templateData.tmpltContent)"/>
                      {{templateData.rcvblcNumber}}
                    </p>
                  </div>
                </div>
                <!-- //phoneWrap -->
              </div>
            </div>
          </div>
          <div class="text-center mt20">
            <a @click="fnSelectTemplate" class="btnStyle1 backBlack" data-toggle="modal" title="템플릿 선택">템플릿 선택</a>
            <a @click="fnClose" class="btnStyle1 backWhite" data-dismiss="modal" title="취소">취소</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TemplateApi from "@/modules/template/service/templateApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: "directInputPopup",
  props: {
    pushTemplateOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    searchData : {
      type: Object,
      require: false,
      default: function() {
        return {
          'searchCondi' : 'tmpltName',
          'searchText' : '',
          'pageNo' : 1,
          'listSize': 999999999
        }
      }
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '푸시 템플릿 검색';
      }
    },
  },
  data() {
    return {
      totCnt:0,
      templateList:[],
      templateData:{}
    }
  },
  methods: {
    //템플릿선택
    fnSelectTemplate(){
      if(Object.keys(this.templateData).length == 0){
        confirm.fnAlert(this.componentsTitle, '템플릿을 선택해주세요.');
        return;
      }
      this.$parent.fnSetTemplateInfo(this.templateData);
      this.fnClose();
    },
    //빈값확인
    fnIsEmpty(str){
      if(str) return false;
      else return true
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
    },
    //템플릿 리스트 검색
    async fnSearch(){
      await TemplateApi.selectPushTmpltList(this.searchData).then(response =>{
        var result = response.data;
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
      //데이터 초기화
      this.templateList = [];
      this.templateData = {};
      this.$emit('update:pushTemplateOpen', false)
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