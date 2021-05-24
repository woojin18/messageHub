<template>
  <div>
    <div class="contentHeader">
      <h2>친구톡 템플릿</h2>
      <!-- <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="친구톡 템플릿 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
    </div>

    <!-- 본문 -->
    <div class="of_h mt30">
      <div class="phone3 inline-block" style="width:30%">
        <div class="phoneFixed">
          <!-- phoneWrap -->
          <div class="phoneWrap">
            <img src="@/assets/images/common/phoneMockup2_1.svg" alt="프리 템플릿">
            <div class="phoneTextWrap4">
              <p v-if="tmpltData.msgKind == 'A'">[광고]</p>
              <div class="mt5">
                <div class="text-sub-wrap scroll-y">
                  <!-- <p class="text-sub scroll-y"> -->
                    <!-- {{tmpltData.tmpltName}} -->
                    <div v-if="!$gfnCommonUtils.isEmpty(tmpltData.imgUrl)" class="phoneText2 mt10 text-center"
                      :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+tmpltData.imgUrl+');'">
                    </div>
                    <!-- <br v-if="!$gfnCommonUtils.isEmpty(tmpltData.tmpltName) && $gfnCommonUtils.isEmpty(tmpltData.imgUrl)"/> -->
                    <br v-if="$gfnCommonUtils.isEmpty(tmpltData.imgUrl)"/>
                    <span v-html="$gfnCommonUtils.newLineToBr(tmpltData.tmpltContent)"></span>
                    <div v-for="(buttonInfo, idx) in tmpltData.buttonList" :key="idx">
                      <a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.name)" class="btnStyle1 backLightGray width100_">{{buttonInfo.name}}</a>
                    </div>
                  <!-- </p> -->
                </div>
                <p class="text-sub_2" v-if="tmpltData.msgKind == 'A'">수신거부: 홈 > 친구차단</p>
              </div>
            </div>
          </div>
          <!-- //phoneWrap -->
        </div>
      </div>
      <div class="of_h inline-block vertical-top consoleCon" style="width:60%">
        <div class="of_h user-phone">
          <div class="float-left" style="width:27%">
            <h4>템플릿명 *</h4>
          </div>
          <div class="float-left" style="width:73%">
            <input type="text" class="inputStyle float-right" title="템플릿명 입력란" v-model="tmpltData.tmpltName" maxlength="100">
          </div>
        </div>
        <div class="of_h user-phone">
          <div class="float-left" style="width:27%">
            <h4>메시지구분 *</h4>
          </div>
          <div class="float-left" style="width:73%">
            <input type="radio" id="msgKind_A" name="msgKind" value="A" v-model="tmpltData.msgKind">
            <label for="msgKind_A" class="mr30">광고성</label>
            <input type="radio" id="msgKind_I" name="msgKind" value="I" v-model="tmpltData.msgKind">
            <label for="msgKind_I">정보성</label>
          </div>
        </div>
        <div class="of_h user-phone">
          <div class="float-left" style="width:27%">
            <h4>타 프로젝트 사용여부  *</h4>
          </div>
          <div class="float-left" style="width:73%">
            <input type="radio" id="otherProjectUseYn_Y" name="otherProjectUseYn" value="Y" v-model="tmpltData.otherProjectUseYn">
            <label for="otherProjectUseYn_Y" class="mr30">공용</label>
            <input type="radio" id="otherProjectUseYn_N" name="otherProjectUseYn" value="N" v-model="tmpltData.otherProjectUseYn">
            <label for="otherProjectUseYn_N">전용</label>
          </div>
        </div>
        <div class="of_h user-phone">
          <div class="float-left" style="width:27%">
            <h4>내용 *</h4>
          </div>
          <div class="float-left" style="width:73%">
            <textarea class="textareaStyle height120" v-model="tmpltData.tmpltContent" maxlength="2000"></textarea>
          </div>
        </div>

        <div class="of_h user-phone consolMarginTop">
          <div class="float-left" style="width:27%">
            <h4>이미지</h4>
          </div>
          <div class="float-left" style="width:73%">
            <div class="of_h">
              <div class="float-left" style="width:25%"><a @click="fnOpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a></div>
              <ul class="float-right attachList" style="width:74%; padding:5px 15px; height:30px;">
                <li><a @click="fnDelImg">{{fnSubString(tmpltData.imgUrl, 0, 45)}}  <i v-if="!$gfnCommonUtils.isEmpty(tmpltData.imgUrl)" class="fal fa-times"></i></a></li>
              </ul>
            </div>
          </div>
        </div>
        <div v-if="!$gfnCommonUtils.isEmpty(tmpltData.imgUrl)" class="of_h user-phone">
          <div class="float-left" style="width:27%">
            <h4>이미지 링크 *</h4>
          </div>
          <div class="float-left" style="width:73%">
            <input type="text" class="inputStyle float-right" title="이미지 링크 입력란" placeholder="http://" v-model="tmpltData.imgLink" maxlength="200">
          </div>
        </div>
        <div class="of_h user-phone consolMarginTop">
          <div class="float-left" style="width:27%">
            <h4 class="inline-block mr20">버튼</h4>
            <a @click="fnAddButton" class="btnStyle1 backBlack">추가 +</a>
          </div>
          <div class="float-left" style="width:73%">
            <div class="of_h">
              <table class="table_skin1" style="width:100%">
                <colgroup>
                  <col style="width:20%">
                  <col style="width:20%">
                  <col>
                  <col style="width:80px">
                </colgroup>
                <thead>
                <tr>
                  <th class="text-center">타입</th>
                  <th class="text-center">버튼이름</th>
                  <th class="text-center">버튼링크</th>
                  <th class="text-center end"></th>
                </tr>
                </thead>
                <tbody class="of_h">
                  <template v-for="(buttonInfo, idx) in tmpltData.buttonList">
                    <tr :key="idx">
                      <td class="text-left" :rowspan="buttonInfo.type == 'WL' || buttonInfo.type == 'AL' ? '2' : '1'">
                        <select class="float-left selectStyle2" style="width:100%" v-model="buttonInfo.type" @change="fnChgBtnType(idx)">
                          <option v-for="bottonType in bottonTypeList" :key="bottonType.type" :value="bottonType.type">{{bottonType.name}}</option>
                        </select>
                      </td>
                      <td class="text-center" :rowspan="buttonInfo.type == 'WL' || buttonInfo.type == 'AL' ? '2' : '1'">
                        <input type="text" class="inputStyle float-left" v-model="buttonInfo.name" maxlength="20">
                      </td>
                      <td v-if="buttonInfo.type == 'WL' || buttonInfo.type == 'AL'" class="text-left of_h">
                        <div v-if="buttonInfo.type == 'WL'">
                          <h6 class="font-normal float-left" style="width:20%">Mobile</h6>
                          <input type="text" class="inputStyle float-right" style="width:68%" v-model="buttonInfo['url_mobile']" maxlength="200">
                        </div>
                        <div v-if="buttonInfo.type == 'AL'">
                          <h6 class="font-normal float-left" style="width:20%">Android</h6>
                          <input type="text" class="inputStyle float-right" style="width:68%" v-model="buttonInfo['scheme_android']" maxlength="200">
                        </div>
                      </td>
                      <td v-else>
                      </td>
                      <td class="text-center end" :rowspan="buttonInfo.type == 'WL' || buttonInfo.type == 'AL' ? '2' : '1'">
                        <a @click="fnDelButton(idx)" class="btnStyle1 backLightGray">삭제</a>
                      </td>
                    </tr>
                    <tr v-if="buttonInfo.type == 'WL' || buttonInfo.type == 'AL'" :key="idx+'_sub'">
                      <td class="text-left of_h">
                        <div v-if="buttonInfo.type == 'WL'">
                          <h6 class="font-normal float-left" style="width:20%">PC</h6>
                          <input type="text" class="inputStyle float-right" style="width:68%" v-model="buttonInfo['url_pc']" maxlength="200">
                        </div>
                        <div v-if="buttonInfo.type == 'AL'">
                          <h6 class="font-normal float-left" style="width:20%">IOS</h6>
                          <input type="text" class="inputStyle float-right" style="width:68%" v-model="buttonInfo['scheme_ios']" maxlength="200">
                        </div>
                      </td>
                    </tr>
                  </template>
                  
                </tbody>
              </table>
            </div>
          </div>  
          <div class="mt20 float-right">
            <a v-if="isInsert" @click="fnSaveFrndTalkTemplate" class="btnStyle2 backRed float-left" title="등록">등록</a>
            <a v-else @click="fnSaveFrndTalkTemplate" class="btnStyle2 backWhite float-left ml10" title="수정">수정</a>
            <router-link :to="{ name: 'frndTalkTemplateList' }" tag="a" class="btnStyle2 ml10">취소</router-link>
          </div>
        </div>
      </div>
      <ImageManagePopUp @img-callback="fnCallbackImgInfo" :imgMngOpen.sync="imgMngOpen" :useCh="useCh" ref="imgMng"></ImageManagePopUp>
    </div>
  </div>
</template>

<script>
import ImageManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";

import templateApi from "@/modules/template/service/templateApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  name: 'frndTalkTemplateManage',
  components : {
    ImageManagePopUp
  },
  props: {
    tmpltId: {
      type: String,
      require: false,
      default: function() {
        return '';
      }
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '친구톡 템플릿 관리';
      }
    },
  },
  data() {
    return {
      imgMngOpen : false,
      imgUploadOpen : false,
      buttonLimitSize : 5,
      useCh : 'FRIENDTALK',
      isInsert : true,
      bottonTypeList : [
        {type:'WL', name:'웹 링크'},
        {type:'AL', name:'앱 링크'},
        {type:'BK', name:'봇 키워드'},
        {type:'MD', name:'메시지전달'}
      ],
      tmpltData : {imgUrl:'', buttonList:[]}
    }
  },
  mounted() {
    this.fnSetTemplateInfo();
  },
  methods: {
    //template 정보 조회
    fnSetTemplateInfo(){
      if(!this.$gfnCommonUtils.isEmpty(this.tmpltId)){
        this.isInsert = false;
        this.fnSelectfrndTalkTmpltInfo();
      } else {
        this.isInsert = true;
        this.fnAddButton();
      }
    },
    //템플릿 정보 조회
    fnSelectfrndTalkTmpltInfo(){
      const params = {tmpltId: this.tmpltId};
      templateApi.selectFrndTalkInfo(params).then(response => {
        const result = response.data;
        if(result.success) {
          if(result.data != null && result.data.length > 0){
            let rtnData = result.data[0];
            rtnData.buttonList = [];
            this.tmpltData = Object.assign({}, rtnData);
            this.tmpltData.buttonList = JSON.parse(rtnData.tmpltButtons);
            this.tmpltData.otherProjectUseYn = (rtnData.projectId == 'ALL' ? 'Y' : 'N');
          }
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
          this.tmpltData = {imgUrl:'', buttonList:[]};
          this.isInsert = true;
        }
      });
    },
    fnChgBtnType(idx){
      const vm = this;
      Object.keys(this.tmpltData.buttonList[idx]).forEach(function(key){
        if(key != 'type'){
          delete vm.tmpltData.buttonList[idx][key];
        }
      });
    },
    //유효성 체크
    fnIsValid(){
      if(!this.tmpltData.tmpltName){
        confirm.fnAlert(this.componentsTitle, '템플릿명을 입력해주세요.');
        return false;
      }
      if(!this.tmpltData.msgKind){
        confirm.fnAlert(this.componentsTitle, '메시지구분을 선택해주세요.');
        return false;
      }
      if(!this.tmpltData.otherProjectUseYn){
        confirm.fnAlert(this.componentsTitle, '타 프로젝트 사용여부를 선택해주세요.');
        return false;
      }
      if(!this.tmpltData.tmpltContent){
        confirm.fnAlert(this.componentsTitle, '메시지 내용을 입력해주세요.');
        return false;
      }
      if(this.tmpltData.imgUrl && !this.tmpltData.fileId){
        confirm.fnAlert(this.componentsTitle, '이미지 정보가 잘못되었습니다. 다시 이미지를 선택해주세요.');
        return false;
      }
      if(this.tmpltData.imgUrl && !this.tmpltData.imgLink){
        confirm.fnAlert(this.componentsTitle, '이미지 입력시 이미지 링크 URL은 필수입니다.');
        return false;
      }
      if(this.tmpltData.imgLink && !this.$gfnCommonUtils.isUrl(this.tmpltData.imgLink)){
        confirm.fnAlert(this.componentsTitle, '유효하지 않은 이미지 링크 URL 입니다.');
        return false;
      }
      const vm = this;
      let buttonValid = true;
      this.tmpltData.buttonList.forEach(function(buttonInfo){
        if(vm.$gfnCommonUtils.isEmpty(buttonInfo.type)){
          confirm.fnAlert(vm.componentsTitle, '버튼 타입을 선택해주세요.');
          buttonValid = false;
          return false;
        }
        if(vm.$gfnCommonUtils.isEmpty(buttonInfo.name)){
          confirm.fnAlert(vm.componentsTitle, '버튼 이름을 입력해주세요.');
          buttonValid = false;
          return false;
        }
        if(buttonInfo.type == 'WL' && vm.$gfnCommonUtils.isEmpty(buttonInfo['url_mobile'])){
          confirm.fnAlert(vm.componentsTitle, 'Mobile 버튼링크를 입력해주세요.');
          buttonValid = false;
          return false;
        }
        if(buttonInfo.type == 'WL' && vm.$gfnCommonUtils.isEmpty(buttonInfo['url_pc'])){
          confirm.fnAlert(vm.componentsTitle, 'PC 버튼링크를 입력해주세요.');
          buttonValid = false;
          return false;
        }
        if(buttonInfo.type == 'AL' && vm.$gfnCommonUtils.isEmpty(buttonInfo['scheme_android'])){
          confirm.fnAlert(vm.componentsTitle, 'Android 버튼링크를 입력해주세요.');
          buttonValid = false;
          return false;
        }
        if(buttonInfo.type == 'AL' && vm.$gfnCommonUtils.isEmpty(buttonInfo['scheme_ios'])){
          confirm.fnAlert(vm.componentsTitle, 'IOS 버튼링크를 입력해주세요.');
          buttonValid = false;
          return false;
        }
      });
      if(!buttonValid) return false;

      return true;
    },
    fnSaveFrndTalkTemplate(){
      if(this.fnIsValid() == false) return;

      const saveType = (this.isInsert ? '등록' : '수정');
      eventBus.$on('callbackEventBus', this.fnProcSaveFrndTalkTemplate);
      confirm.fnConfirm(this.componentsTitle, "친구톡 템플릿을 "+saveType+"하시겠습니까?", "확인");
    },
    async fnProcSaveFrndTalkTemplate(){
      //DATA Set
      let params = Object.assign({}, this.tmpltData);
      if(!params.imgUrl){
        params.fileId = '';
        params.imgLink = '';
      }
      params.tmpltButtonsStr = JSON.stringify(this.tmpltData.buttonList);

      //저장처리
      await templateApi.saveFrndTalkTmplt(params).then(response => {
        const result = response.data;
        if(result.success) {
          eventBus.$on('callbackEventBus', this.fnSaveAlertCallback);
          confirm.fnAlert(this.componentsTitle, '저장되었습니다.', 'CALLBACK');
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnSaveAlertCallback(){
      if(this.isInsert){
        this.$router.push('frndTalkTemplateList')
      }
    },
    fnDelButton(idx){
      if(idx <= this.tmpltData.buttonList.length){
        this.tmpltData.buttonList.splice(idx, 1);
      }
    },
    fnAddButton(){
      if(this.tmpltData.buttonList.length < this.buttonLimitSize){
        const baseButtonInfo = {
          name : '',
          type : 'WL',
          url_pc: '',
          url_mobile: '',
        };
        this.tmpltData.buttonList.push(baseButtonInfo);
      } else {
        confirm.fnAlert(this.componentsTitle, '버튼은 최대 '+this.buttonLimitSize+'개 까지 입력 가능합니다.');
      }
    },
    fnDelImg(){
      this.tmpltData.imgUrl = '';
      this.tmpltData.fileId = '';
      this.tmpltData.wideImgYn = 'N';
      this.tmpltData.imgLink = '';
    },
    fnSubString(str, sIdx, length){
      let shortStr = ''
      if(!this.$gfnCommonUtils.isEmpty(str)){
        shortStr = str.toString();
        if(shortStr.length > length){
          shortStr = shortStr.substring(sIdx, length) + '...  ';
        } else {
          shortStr = shortStr + '  ';
        }
      }
      return shortStr;
    },
    fnOpenImageManagePopUp(){
      this.$refs.imgMng.fnSearch();
      this.imgMngOpen = !this.imgMngOpen;
    },
    fnCallbackImgInfo(imgInfo) {
      this.tmpltData.imgUrl = imgInfo.chImgUrl;
      this.tmpltData.fileId = imgInfo.fileId;
      this.tmpltData.wideImgYn = imgInfo.wideImgYn;
    },
  }
}
</script>