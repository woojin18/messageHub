<template>
  <div>

    <div class="contentHeader">
      <h2>알림톡 템플릿 등록/상세</h2>
      <!-- <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="알림톡 템플릿 등록/상세 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
    </div>

    <div class="row">
        <div class="phone3 inline-block" style="width:30%">
          <div class="phoneFixed">
            <!-- phoneWrap -->
            <div class="phoneWrap">
              <img src="@/assets/images/common/phoneMockup3.svg" alt="알림톡 템플릿">
              <div class="phoneTextWrap3 scroll-y">
                <div>
                  <p class="text-main"><i class="fal fa-envelope-open-text"></i> 알림톡 도착</p>
                  <div v-if="tmpltData.emphasizeType == 'TEXT'" class="text-sub-wrap" style="padding:10px;">
                    <p v-if="!$gfnCommonUtils.isEmpty(tmpltData.tmpltEmpsSubTitle)" class="text-sub_1">{{tmpltData.tmpltEmpsSubTitle}}</p>
                    <p v-if="!$gfnCommonUtils.isEmpty(tmpltData.tmpltEmpsTitle)" class="text-sub scroll-y3">{{tmpltData.tmpltEmpsTitle}}</p>
                  </div>
                  <div class="text-sub-wrap" style="padding:10px;">
                    <span v-html="$gfnCommonUtils.newLineToBr(tmpltData.tmpltContent)"></span>
                  </div>
                  <!-- <p class="text-sub_2">템플릿 테스트입니다.<br>템플릿 테스트 템플릿 테스트 템플릿 테스트 템플릿 테스트</p> -->
                  <div v-for="(buttonInfo, idx) in tmpltData.buttonList" :key="idx">
                    <a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.name)" class="btnStyle1 backLightGray">{{buttonInfo.name}}</a>
                  </div>
                </div>
              </div>
            </div>
            <!-- //phoneWrap -->
          </div>
        </div>
        <div class="of_h inline-block vertical-top consoleCon" style="width:60%">
          <div class="of_h">
            <div class="float-left" style="width:22%"><h4>발신 프로필/그룹 *</h4></div>
            <div class="float-left" style="width:78%">
              <select 
                class="float-left selectStyle2" 
                style="width:20%" 
                v-model="tmpltData.senderKeyType" 
                @change="fnSelectSenderKeyList"
                :disabled="!isInsert"
              >
                <option value="S">일반</option>
                <option value="G">그룹</option>
              </select>
              <select v-if="isInsert" class="float-left selectStyle2" style="width:80%" v-model="tmpltData.senderKey">
                <option value="">선택해주세요.</option>
                <option v-for="senderKeyInfo in senderKeyList" :key="senderKeyInfo.senderKey" :value="senderKeyInfo.senderKey">
                  <!-- {{tmpltData.senderKeyType == 'S' ? senderKeyInfo.kkoChId : senderKeyInfo.senderKey}} -->
                  {{senderKeyInfo.senderKey}}
                </option>
              </select>
              <select v-else class="float-left selectStyle2" style="width:80%" disabled>
                <option value="">{{tmpltData.senderKey}}</option>
              </select>
            </div>
          </div>
          <div class="of_h">
            <div class="float-left" style="width:22%"><h4>템플릿명 *</h4></div>
            <div class="float-left" style="width:78%">
              <input type="text" class="inputStyle" placeholder="20자 이내 입력" v-model="tmpltData.tmpltName" maxlength="20">
            </div>
          </div>
          <div class="of_h">
            <div class="float-left" style="width:22%"><h4>템플릿강조유형</h4></div>
            <div class="float-left" style="width:78%">
              <input type="radio" id="emphasizeType_NONE" name="emphasizeType" value="NONE" v-model="tmpltData.emphasizeType">
              <label for="emphasizeType_NONE" class="mr30">선택 안 함</label>
              <input type="radio" id="emphasizeType_TEXT" name="emphasizeType" value="TEXT" v-model="tmpltData.emphasizeType">
              <label for="emphasizeType_TEXT">강조 표기형</label>
            </div>
          </div>
          <div v-if="tmpltData.emphasizeType == 'TEXT'" class="of_h">
            <div class="float-left" style="width:22%"><h4>템플릿강조제목 *</h4></div>
            <div class="float-left" style="width:78%">
              <input type="text" class="inputStyle" v-model="tmpltData.tmpltEmpsTitle" maxlength="50">
            </div>
          </div>
          <div v-if="tmpltData.emphasizeType == 'TEXT'" class="of_h">
            <div class="float-left" style="width:22%"><h4>템플릿강조부제목 *</h4></div>
            <div class="float-left" style="width:78%">
              <input type="text" class="inputStyle" v-model="tmpltData.tmpltEmpsSubTitle" maxlength="50">
            </div>
          </div>
          <div class="of_h">
            <div class="float-left" style="width:22%"><h4>내용 *</h4></div>
            <div class="float-left" style="width:78%">
              <!-- 템플릿 내용/부가 정보/광고성 메시지 합 최대 1,000자 -->
              <textarea class="textareaStyle height190" v-model="tmpltData.tmpltContent" placeholder="템플릿 내용 최대 1,000자" maxlength="1000"></textarea>
            </div>
          </div>
          <div class="of_h consolMarginTop">
            <div class="float-left" style="width:22%"><h4>카테고리 *</h4></div>
            <div class="float-left" style="width:78%">
              <div class="float-left" style="width:49.5%">
                <select name="userConsole_sub040402_2" class="float-left selectStyle2" style="width:100%" v-model="categoryGrpName" @change="fnSelectKkoTmpltCatList">
                  <option value="">대분류</option>
                  <option v-for="(kkoTmpltCatGrpInfo, idx) in kkoTmpltCatGrpList" :key="idx"
                    :value="kkoTmpltCatGrpInfo.categoryGrpName">{{kkoTmpltCatGrpInfo.categoryGrpName}}</option>
                </select>
              </div>
              <div class="float-right" style="width:49.5%">
                <select name="userConsole_sub040402_3" class="float-left selectStyle2" style="width:100%" v-model="tmpltData.categoryCode">
                  <option value="">중분류</option>
                  <option v-for="(kkoTmpltCatInfo, idx) in kkoTmpltCatList" :key="idx"
                    :value="kkoTmpltCatInfo.categoryCode">{{kkoTmpltCatInfo.categoryName}}</option>
                </select>
              </div>
            </div>
          </div>
          <div class="of_h">
            <div class="float-left" style="width:22%">
              <h4 class="inline-block mr20">버튼</h4><a @click="fnAddButton" class="btnStyle1 backBlack">추가 +</a>
            </div>
            <div class="float-left" style="width:78%">
              <table class="table_skin1">
                <colgroup>
                  <col style="width:20%">
                  <col style="width:20%">
                  <col>
                  <col style="width:18%">
                </colgroup>
                <thead>
                  <tr>
                  <th class="text-center">타입</th>
                  <th class="text-center">버튼이름</th>
                  <th class="text-center">버튼링크</th>
                  <th class="text-center end"></th>
                  </tr>
                </thead>
                <tbody>
                  <template v-for="(buttonInfo, idx) in tmpltData.buttonList">
                    <tr :key="idx">
                      <td class="text-left" :rowspan="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL' ? '2' : '1'">
                        <select class="float-left selectStyle2" style="width:100%" v-model="buttonInfo.linkType" @change="fnChgBtnLinkType(idx)">
                          <option v-for="buttonLinkType in buttonLinkTypeList" :key="buttonLinkType.linkType" :value="buttonLinkType.linkType">{{buttonLinkType.name}}</option>
                        </select>
                      </td>
                      <td class="text-center" :rowspan="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL' ? '2' : '1'">
                        <input v-if="buttonInfo.linkType == 'AC'" type="text" class="inputStyle float-left" v-model="buttonInfo.name" disabled maxlength="14">
                        <input v-else type="text" class="inputStyle float-left" v-model="buttonInfo.name" maxlength="14">
                      </td>
                      <td :class="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL' ? 'text-left' : 'text-left of_h'">
                        <h6 v-if="buttonInfo.linkType == 'DS'" class="float-left" v-html="buttonDSDescription"></h6>
                        <h6 v-if="buttonInfo.linkType == 'WL'" class="float-left" style="width:20%">Mobile*</h6>
                        <h6 v-if="buttonInfo.linkType == 'AL'" class="float-left" style="width:20%">Android*</h6>
                        <!-- <input v-if="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL'" type="text" class="inputStyle float-left" style="width:80%"> -->
                        <input v-if="buttonInfo.linkType == 'WL'" type="text" class="inputStyle float-left" style="width:80%" v-model="buttonInfo.linkMo" maxlength="200">
                        <input v-if="buttonInfo.linkType == 'AL'" type="text" class="inputStyle float-left" style="width:80%" v-model="buttonInfo.linkAnd" maxlength="200">

                      </td>
                      <td class="text-center end" :rowspan="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL' ? '2' : '1'">
                        <a @click="fnDelButton(idx)" class="btnStyle1 backLightGray">삭제</a>
                      </td>
                    </tr>
                    <tr v-if="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL'" :key="idx+'_sub'">
                      <td class="text-left">
                        <div v-if="buttonInfo.linkType == 'WL'">
                          <h6 class="float-left" style="width:20%">PC*</h6>
                          <input type="text" class="inputStyle float-left" style="width:80%" v-model="buttonInfo.linkPc" maxlength="200">
                        </div>
                        <div v-if="buttonInfo.linkType == 'AL'">
                          <h6 class="float-left" style="width:20%">IOS*</h6>
                          <input type="text" class="inputStyle float-left" style="width:80%" v-model="buttonInfo.linkIos" maxlength="200">
                        </div>
                      </td>
                    </tr>
                  </template>
                </tbody>
              </table>

            </div>
          </div>

          <div class="mt20 float-right">
            <a v-if="isInsert" href="#" @click.prevent="fnApprvReqTmplt" class="btnStyle2 backRed float-left ml10" title="등록요청">등록요청</a>
            <a 
              v-if="tmpltData.tmpltStatCode == 'T'" href="#" 
              @click.prevent="fnInspectReqTmplt" 
              class="btnStyle2 backRed float-left ml10" 
              title="검수요청"
            >검수요청</a>
            <a 
              v-if="tmpltData.tmpltStatCode == 'T' || tmpltData.tmpltStatCode == 'S'" 
              href="#" 
              @click.prevent="fnUpdateReqTmplt" 
              class="btnStyle2 backWhite float-left ml10" 
              title="수정요청"
            >수정요청</a>
            <router-link :to="{ name: 'alimTalkTemplateList' }" tag="a" class="btnStyle2 float-left ml10">취소</router-link>
          </div>

        </div>
      </div>
  </div>
</template>

<script>
import templateApi from "@/modules/template/service/templateApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  name: 'alimTalkTemplateManage',
  props: {
    tmpltCode: {
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
        return '알림톡 템플릿 관리';
      }
    },
  },
  data() {
    return {
      isInsert : true,
      senderKeyList : [],
      groupKeyList : [],
      kkoTmpltCatGrpList : [],
      kkoTmpltCatList : [],
      categoryGrpName : '',
      useCh : 'ALIMTALK',
      buttonLimitSize : 5,
      buttonACName : '채널 추가',
      buttonDSDescription : '카카오 메세지에 택배사 명과 송장번호를 기재한 후, 배송 조회 버튼을 추가하시면 메세지에서 택배사 명과 송장번호를 추출하여 배송 조회 카카오 검색페이지 링크가 자동으로 생성됩니다. 카카오에서 지원하는 택배사명과 운송장번호가 알림톡 메시지 내에 포함된 경우에만 배송조회 버튼이 표시됩니다. 배송 조회가 가능한 택배사는 <span style="color:#e11d21"><strong>카카오와 해당 택배사와의 계약 관계에 의해 변동될 수 있음을 유의해주시기 바랍니다.</strong></span>',
      buttonLinkTypeList : [
        {linkType:'DS', name:'배송 조회'},
        {linkType:'WL', name:'웹 링크'},
        {linkType:'AL', name:'앱 링크'},
        {linkType:'BK', name:'봇 키워드'},
        {linkType:'MD', name:'메시지 전달'},
        {linkType:'BC', name:'상담톡 전환'},
        {linkType:'BT', name:'봇 전환'},
        //{linkType:'AC', name:'채널 추가'},  //광고 추가/복합형만
      ],
      tmpltData : {
        senderKeyType : 'S',  //S:일반, G:그룹
        senderKey : '',
        tmpltName : '',
        emphasizeType: 'NONE',  //강조여부(NONE: 선택안함, TEXT:강조표기형)
        tmpltEmpsTitle: '',
        tmpltEmpsSubTitle: '',
        tmpltContent: '',
        categoryCode : '',
        tmpltMessageType : 'BA',  //BA: 기본형 고정
        buttonList:[],
      }
    }
  },
  mounted() {
    this.fnSelectKkoTmpltCatGrpList();
    this.fnSetTemplateInfo();
    if(this.isInsert){
      this.fnSelectSenderKeyList();
    }
  },
  methods: {
    //template 정보 조회
    fnSetTemplateInfo(){
      if(!this.$gfnCommonUtils.isEmpty(this.tmpltCode)){
        this.isInsert = false;
        this.fnSelectAlimTalkTmpltInfo();
      } else {
        this.isInsert = true;
        //this.fnAddButton();
      }
    },
    //템플릿 정보 조회
    fnSelectAlimTalkTmpltInfo(){
      const params = {tmpltCode: this.tmpltCode};
      templateApi.selectAlimTalkInfo(params).then(response => {
        const result = response.data;
        if(result.success) {
          if(result.data != null && result.data.length > 0){
            let rtnData = result.data[0];
            rtnData.buttonList = [];
            let tmpltInfo = JSON.parse(rtnData.tmpltInfo);
            rtnData.emphasizeType = tmpltInfo.templateEmphasizeType;
            rtnData.tmpltEmpsTitle = tmpltInfo.templateTitle;
            rtnData.tmpltEmpsSubTitle = tmpltInfo.templateSubtitle;
            rtnData.tmpltContent = tmpltInfo.templateContent;
            rtnData.buttonList = tmpltInfo.buttons;

            this.tmpltData = Object.assign({}, rtnData);
            this.tmpltData.tmpltInfo = '';

            //set categoryInfo
            this.categoryGrpName = this.tmpltData.categoryGrpName;
            this.fnSelectKkoTmpltCatList();
            this.tmpltData.categoryCode = this.tmpltData.tmpltCategoryCode;
          }
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
          this.tmpltData = {imgUrl:'', buttonList:[]};
          this.isInsert = true;
        }
      });
    },
    fnIsValidInspectReqTmplt(){
      if(this.$gfnCommonUtils.isEmpty(this.tmpltCode) || !this.tmpltData.senderKey){
        confirm.fnAlert(this.componentsTitle, '유효하지 않은 템플릿 정보입니다.');
        return false;
      }
    },
    fnInspectReqTmplt(){
      if(this.fnIsValidInspectReqTmplt() == false) return;
      eventBus.$on('callbackEventBus', this.fnProcInspectReqTmplt);
      confirm.fnConfirm(this.componentsTitle, "알림톡 템플릿을 검수요청 하시겠습니까?", "확인");
    },
    fnProcInspectReqTmplt(){
      let params = {
        tmpltCode: this.tmpltCode,
        senderKey: this.tmpltData.senderKey
      };
      templateApi.procInspectRequestKkoTmplt(params).then(response => {
        const result = response.data;
        if(result.success) {
          confirm.fnAlert(this.componentsTitle, '알림톡 템플릿을 검수요청 하였습니다.');
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnIsValidApprvReqTmplt(){
      if(this.isInsert && !this.tmpltData.senderKeyType){
        confirm.fnAlert(this.componentsTitle, '발신프로필타입을 선택해주세요.');
        return false;
      }
      if(!this.tmpltData.senderKey){
        confirm.fnAlert(this.componentsTitle, '발신프로필키를 선택해주세요.');
        return false;
      }
      if(!this.tmpltData.tmpltName){
        confirm.fnAlert(this.componentsTitle, '템플릿명을 입력해주세요.');
        return false;
      }
      if(!this.tmpltData.tmpltContent){
        confirm.fnAlert(this.componentsTitle, '템플릿내용을 입력해주세요.');
        return false;
      }
      if(!this.tmpltData.categoryCode){
        confirm.fnAlert(this.componentsTitle, '카테고리를 선택해주세요.');
        return false;
      }
      if(!this.tmpltData.tmpltContent){
        confirm.fnAlert(this.componentsTitle, '템플릿내용을 입력해주세요.');
        return false;
      }
      if(!this.tmpltData.emphasizeType){
        confirm.fnAlert(this.componentsTitle, '템플릿강조유형을 선택해주세요.');
        return false;
      }
      if(this.tmpltData.emphasizeType == 'TEXT'){
        if(!this.tmpltData.tmpltEmpsTitle){
          confirm.fnAlert(this.componentsTitle, '템플릿강조유형이 강조 표기형일때 템플릿강조제목은 필수입니다.');
          return false;
        }
        if(!this.tmpltData.tmpltEmpsTitle){
          confirm.fnAlert(this.componentsTitle, '템플릿강조유형이 강조 표기형일때 템플릿강조부제목은 필수입니다.');
          return false;
        }
      }
      const vm = this;
      let buttonValid = true;
      this.tmpltData.buttonList.forEach(function(buttonInfo){
        if(vm.$gfnCommonUtils.isEmpty(buttonInfo.linkType)){
          confirm.fnAlert(vm.componentsTitle, '버튼 타입을 선택해주세요.');
          buttonValid = false;
          return false;
        }
        if(vm.$gfnCommonUtils.isEmpty(buttonInfo.name)){
          confirm.fnAlert(vm.componentsTitle, '버튼 이름을 입력해주세요.');
          buttonValid = false;
          return false;
        }
        if(buttonInfo.linkType == 'WL' && vm.$gfnCommonUtils.isEmpty(buttonInfo['linkMo'])){
          confirm.fnAlert(vm.componentsTitle, 'Mobile 버튼링크를 입력해주세요.');
          buttonValid = false;
          return false;
        }
        if(buttonInfo.linkType == 'WL' && vm.$gfnCommonUtils.isEmpty(buttonInfo['linkPc'])){
          confirm.fnAlert(vm.componentsTitle, 'PC 버튼링크를 입력해주세요.');
          buttonValid = false;
          return false;
        }
        if(buttonInfo.linkType == 'AL' && vm.$gfnCommonUtils.isEmpty(buttonInfo['linkAnd'])){
          confirm.fnAlert(vm.componentsTitle, 'Android 버튼링크를 입력해주세요.');
          buttonValid = false;
          return false;
        }
        if(buttonInfo.linkType == 'AL' && vm.$gfnCommonUtils.isEmpty(buttonInfo['linkIos'])){
          confirm.fnAlert(vm.componentsTitle, 'IOS 버튼링크를 입력해주세요.');
          buttonValid = false;
          return false;
        }
      });
      if(!buttonValid) return false;
      
      return true;
    },
    fnUpdateReqTmplt(){
      if(this.fnIsValidApprvReqTmplt() == false) return;
      eventBus.$on('callbackEventBus', this.fnProcUpdateReqTmplt);
      confirm.fnConfirm(this.componentsTitle, "알림톡 템플릿을 수정요청 하시겠습니까?", "확인");
    },
    async fnProcUpdateReqTmplt(){
      //DATA Set
      let params = Object.assign({}, this.tmpltData);
      params.tmpltCode = this.tmpltCode;
      params.tmpltButtonsStr = JSON.stringify(this.tmpltData.buttonList);

      await templateApi.procUpdateRequestKkoTmplt(params).then(response => {
        const result = response.data;
        if(result.success) {
          confirm.fnAlert(this.componentsTitle, '알림톡 템플릿을 수정요청 하였습니다.');
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnApprvReqTmplt(){
      if(this.fnIsValidApprvReqTmplt() == false) return;
      eventBus.$on('callbackEventBus', this.fnProcApprvReqTmplt);
      confirm.fnConfirm(this.componentsTitle, "알림톡 템플릿을 등록요청 하시겠습니까?", "확인");
    },
    async fnProcApprvReqTmplt(){
      //DATA Set
      let params = Object.assign({}, this.tmpltData);
      params.tmpltButtonsStr = JSON.stringify(this.tmpltData.buttonList);

      await templateApi.procApprvRequestKkoTmplt(params).then(response => {
        const result = response.data;
        if(result.success) {
          eventBus.$on('callbackEventBus', this.fnMovePage);
          confirm.fnAlert(this.componentsTitle, '알림톡 템플릿을 등록요청 하였습니다.', 'CALLBACK');
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnMovePage(){
      if(this.isInsert){
        this.$router.push('alimTalkTemplateList')
      }
    },
    fnAddButton(){
      if(this.tmpltData.buttonList.length < this.buttonLimitSize){
        const baseButtonInfo = {
          name : '',
          linkType : 'DS',
        };
        this.tmpltData.buttonList.push(baseButtonInfo);
      } else {
        confirm.fnAlert(this.componentsTitle, '버튼은 최대 '+this.buttonLimitSize+'개 까지 입력 가능합니다.');
      }
    },
    fnDelButton(idx){
      if(idx <= this.tmpltData.buttonList.length){
        this.tmpltData.buttonList.splice(idx, 1);
      }
    },
    fnChgBtnLinkType(idx){
      const vm = this;
      Object.keys(this.tmpltData.buttonList[idx]).forEach(function(key){
        if(key != 'linkType'){
          if(key == 'name' && vm.tmpltData.buttonList[idx]['linkType'] == 'AC'){
            vm.tmpltData.buttonList[idx]['name'] = vm.buttonACName;
          } else {
            delete vm.tmpltData.buttonList[idx][key];
          }
        }
      });
    },
    //카카오톡 발신 프로필키 리스트 조회
    fnSelectSenderKeyList(){
      const params = {kkoSvc: this.useCh, senderKeyType: this.tmpltData.senderKeyType};
      templateApi.selectSenderKeyList(params).then(response => {
        const result = response.data;
        if(result.success) {
          this.tmpltData.senderKey = '';
          this.senderKeyList = Object.assign({}, result.data);
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    //카카오 템플릿 카테고리 그룹 목록 조회
    async fnSelectKkoTmpltCatGrpList(){
      const params = {};
      await templateApi.selectKkoTmpltCatGrpList(params).then(response => {
        const result = response.data;
        if(result.success) {
          this.kkoTmpltCatGrpList = Object.assign([], result.data);
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    //카카오 템플릿 카테고리 그룹 목록 조회
    async fnSelectKkoTmpltCatList(){
      this.tmpltData.categoryCode = '';
      const params = {categoryGrpName: this.categoryGrpName};
      await templateApi.selectKkoTmpltCatList(params).then(response => {
        const result = response.data;
        if(result.success) {
          this.kkoTmpltCatList = Object.assign([], result.data);
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
  }
}
</script>