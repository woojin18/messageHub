<template>
  <div>

    <div class="contentHeader" style="margin-bottom:10px;">
      <h2>템플릿 > 알림톡</h2>
      <a href="#self" class="btnStyle2 backPink absolute top0 right0" @click="fnFileDown('FLET3TJ5Zz', 'alimtalk_template_guide.pdf')">알림톡 템플릿 검수 가이드 다운로드</a>
    </div>

    <!-- 본문 -->
    <div class="border-line2 pd10 mb10">
      <p class="color4">템플릿내용은 한/영 구분없이 1,000자까지 입력 가능합니다. 변수에 들어갈 내용의 최대 길이를 감안하여 작성해 주세요.<br>
                        버튼 등록 시 버튼명은 변수 입력이 불가하며, 버튼 url에는 변수 입력이 가능합니다. (예. http://lguplus.com/#{변수})<br>
                        버튼 url 등록 시 url_mobile, url_pc 링크에는 'http://', 'https://'가 포함 되어야 합니다.<br>
                        버튼명은 최대 14자리까지 입력이 가능합니다.<br>
                        알림톡 템플릿은 등록완료 -> 검수중 -> 승인의 단계를 가집니다. 우측 알림톡 템플릿 검수 가이드를 참고 하십시오.</p>
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
                  <div v-if="tmpltData.emphasizeType == 'IMAGE'">
                    <div v-if="this.tmpltData.tmpltImgUrl != ''" class="phoneText2 mt10 text-center simulatorImg"
                      :style="'padding:48px;border-radius:0px;background-image: url('+this.tmpltData.tmpltImgUrl+');'">
                    </div>
                  </div>
                  <div class="text-sub-wrap" style="padding:10px;">
                    <span><pre>{{tmpltData.tmpltContent}}</pre></span>
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
            <!-- 카카오채널그룹관리 기능 제외 -->
            <!-- <div class="float-left" style="width:22%"><h4>카카오채널/그룹 *</h4></div> -->
            <div class="float-left" style="width:22%"><h4>카카오 채널 *</h4></div>
            <div class="float-left" style="width:78%">
              <!-- 카카오채널그룹관리 기능 제외
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
              -->
              <select v-if="isInsert" class="float-left selectStyle2" style="width:100%" v-model="tmpltData.senderKey">
                <option value="">선택해주세요.</option>
                <option v-for="senderKeyInfo in senderKeyList" :key="senderKeyInfo.senderKey" :value="senderKeyInfo.senderKey">
                  <!-- {{tmpltData.senderKeyType == 'S' ? senderKeyInfo.kkoChId : senderKeyInfo.senderKey}} -->
                  {{senderKeyInfo.kkoChId}}
                </option>
              </select>
              <select v-else class="float-left selectStyle2" style="width:100%" disabled>
                <option value="">{{tmpltData.kkoChId}}</option>
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
              <label for="emphasizeType_NONE" class="mr30">기본형</label>
              <input type="radio" id="emphasizeType_TEXT" name="emphasizeType" value="TEXT" v-model="tmpltData.emphasizeType">
              <label for="emphasizeType_TEXT" class="mr30">강조 표기형</label>
              <input type="radio" id="emphasizeType_IMAGE" name="emphasizeType" value="IMAGE" v-model="tmpltData.emphasizeType">
              <label for="emphasizeType_IMAGE">이미지형</label>
              <span v-if="tmpltData.emphasizeType == 'IMAGE'" style="float:right; color:red; padding-bottom: 7px;">카카오톡 8.7.5 버전(안드로이드, iOS 공통) 이상에서만 노출됩니다.</span>
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
          <div v-if="tmpltData.emphasizeType == 'IMAGE'" class="of_h">
            <div class="float-left" style="width:22%"><h4>이미지 *</h4></div>
            <div class="float-left" style="width:78%">
              <div class="float-left" style="width:22%">
                <input ref="imageInput" type="file" @change="fnImagePreview" style="display:none;" accept=".jpg,.png">
                <img>
                <a @click="fnImgReg" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
              </div>
              <ul class="float-right attachList" style="width:75%; padding:5px 15px; height:30px;">
                <li>
                  <a href="#">{{fnSubString(this.tmpltData.tmpltImgPath, 0, 50)}}</a><a v-show="this.tmpltData.tmpltImgPath != ''" @click="fnDelImg"><i class="fal fa-times"></i></a>
                </li>
              </ul>
              <img ref="imgChk" src="" @load="fnImgLoad" style="display:none;">
            </div>
          </div>
          <div v-if="tmpltData.emphasizeType == 'IMAGE'" class="of_h">
            <div class="float-left" style="width: 22%;">
							<a href="https://kakaobusiness.gitbook.io/main/ad/bizmessage/notice-friend/content-guide/image" class="btnStyle1 backPink" target="_blank">이미지형 제작 가이드</a>
						</div>
						<div class="float-left" style="width: 78%;">
							<p>이미지 제작시 왼쪽의 ‘이미지 제작 가이드’를 참고해 주세요.</p>
							<p class="consolMarginTop mb10">이미지는 JPG, PNG 형식만 지원합니다.<br>
							이미지 용량은 500KB 이하여야 합니다.<br>
							이미지 권장 사이즈는 가로 800 x 세로 400px 입니다.<br>
							가로:세로 비율이 2:1이 아닐 경우, 가로 500px, 세로 250px 이하일 경우, 업로드가 불가능 합니다.</p>
						</div>
          </div>
          <div class="of_h">
            <div class="float-left" style="width:22%"><h4>내용 *</h4></div>
            <div class="float-left" style="width:78%">
              <!-- 템플릿 내용/부가 정보/광고성 메시지 합 최대 1,000자 -->
              <textarea class="textareaStyle height190" v-model="tmpltData.tmpltContent" :placeholder="contentAreaPlaceholder" :maxlength="msgLimitLength" @input="fnSetCurrLength"></textarea>
              <strong class="letter">({{msgCurrLength | formatComma}} / {{msgLimitLength | formatComma}})</strong>
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
                  <col style="width:120px">
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
                        <center v-if="buttonInfo.linkType == 'DS'">
                          <i class="fas fa-question-circle toolTip">
                            <span class="toolTipText" style="width:380px">'배송조회' 버튼은 메시지 내용에 '택배사' 명과 '송장번호 패턴'을<br>
                                                                          인식하여 자동으로 각 택배사의 배송조회 페이지로 이동하게끔 되어<br>
                                                                          있습니다. 택배사 명과 송장번호 패턴 인식 불가능 시 패송조회 버튼은<br>
                                                                          비활성화 됩니다.
                            </span>
                          </i>
                        </center>
                      </td>
                      <td class="text-center" :rowspan="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL' ? '2' : '1'">
                        <input v-if="buttonInfo.linkType == 'AC'" type="text" class="inputStyle float-left" v-model="buttonInfo.name" disabled maxlength="14" placeholder="공백 포함 최대 14자">
                        <input v-else type="text" class="inputStyle float-left" v-model="buttonInfo.name" maxlength="14" placeholder="공백 포함 최대 14자">
                      </td>
                      <td :class="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL' ? 'text-left' : 'text-left of_h'">
                        <h6 v-if="buttonInfo.linkType == 'DS'" class="float-left" v-html="buttonDSDescription"></h6>
                        <h6 v-if="buttonInfo.linkType == 'WL'" class="float-left" style="width:30%">Mobile*</h6>
                        <h6 v-if="buttonInfo.linkType == 'AL'" class="float-left" style="width:30%">Android*</h6>
                        <!-- <input v-if="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL'" type="text" class="inputStyle float-left" style="width:80%"> -->
                        <input v-if="buttonInfo.linkType == 'WL'" type="text" class="inputStyle float-left" style="width:70%" v-model="buttonInfo.linkMo" maxlength="200" placeholder="[http://, https://]를 포함한 URL">
                        <input v-if="buttonInfo.linkType == 'AL'" type="text" class="inputStyle float-left" style="width:70%" v-model="buttonInfo.linkAnd" maxlength="200">

                      </td>
                      <td class="text-center end" :rowspan="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL' ? '2' : '1'">
                        <!-- <a
                          v-if="buttonInfo.linkType === 'WL'" 
                          class="btnStyle1 backBlack" 
                          title="단축 URL+" 
                          data-toggle="modal" 
                          data-target="#shortened_URL"
                          @click="selIdx = idx"
                        >단축 URL+</a>
                        <p>&nbsp;</p> -->
                        <a @click="fnDelButton(idx)" class="btnStyle1 backLightGray">삭제</a>
                      </td>
                    </tr>
                    <tr v-if="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL'" :key="idx+'_sub'">
                      <td class="text-left">
                        <div v-if="buttonInfo.linkType == 'WL'">
                          <h6 class="float-left" style="width:30%">PC*</h6>
                          <input type="text" class="inputStyle float-left" style="width:70%" v-model="buttonInfo.linkPc" maxlength="200" placeholder="[http://, https://]를 포함한 URL">
                        </div>
                        <div v-if="buttonInfo.linkType == 'AL'">
                          <h6 class="float-left" style="width:30%">IOS*</h6>
                          <input type="text" class="inputStyle float-left" style="width:70%" v-model="buttonInfo.linkIos" maxlength="200">
                        </div>
                      </td>
                    </tr>
                  </template>
                </tbody>
              </table>

            </div>
          </div>

          <div class="mt20 float-right">
            <a
              v-if="isInsert"
              href="#"
              @click.prevent="fnApprvReqTmplt"
              class="btnStyle2 backRed float-left ml10"
              title="등록요청"
              activity="SAVE"
            >등록요청</a>
            <a
              v-if="tmpltData.tmpltStatCode == 'T'"
              href="#"
              @click.prevent="fnInspectReqTmplt"
              class="btnStyle2 backRed float-left ml10"
              title="검수요청"
              activity="SAVE"
            >검수요청</a>
            <a
              v-if="tmpltData.tmpltStatCode == 'T' || tmpltData.tmpltStatCode == 'S'"
              href="#"
              @click.prevent="fnUpdateReqTmplt"
              class="btnStyle2 backWhite float-left ml10"
              title="수정요청"
              activity="SAVE"
            >수정요청</a>
            <router-link :to="{ name: 'alimTalkTemplateList' }" tag="a" class="btnStyle2 float-left ml10">취소</router-link>
          </div>

        </div>
      </div>

      <shortenedUrlListPopup @btnSelect="btnSelect" />
      <shortenedUrlAddPopup/>
  </div>
</template>

<script>
import templateApi from "@/modules/template/service/templateApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

import commonUtilApi from "@/modules/commonUtil/service/commonUtilApi";

import shortenedUrlListPopup from "@/modules/urlInfo/components/shortenedUrlListPopup"
import shortenedUrlAddPopup from "@/modules/urlInfo/components/shortenedUrlAddPopup"

export default {
  name: 'alimTalkTemplateManage',
  components: {
		shortenedUrlListPopup,
    shortenedUrlAddPopup,
  },
  props: {
    tmpltKey: {
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
        return '알림톡 템플릿';
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
      contentAreaPlaceholder: '변수로 설정하고자 하는 내용을 #{ }표시로 작성해 주십시오.\n:예) 이름과 출금일을 변수 설정\n:예) #{name}님 #{yyyymmdd} 출금 예정입니다.',
      reflectionMsg: '반영까지 최대 5분의 시간이 소요될 수 있습니다.',
      msgCurrLength: 0,
      msgLimitLength: 1000,
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
        tmpltCode: '',
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
        tmpltImgUrl : '',
        tmpltImgPath : ''        
      },
      selIdx : null
    }
  },
  async mounted() {
    await this.fnExistApiKey();
    await this.fnValidUseChGrp();
    this.fnSelectKkoTmpltCatGrpList();
    this.fnSetTemplateInfo();
    if(this.isInsert){
      this.fnSelectSenderKeyList();
    }
  },
  methods: {
    fnSetCurrLength(){
      let body = this.$gfnCommonUtils.defaultIfEmpty(this.tmpltData.tmpltContent, '');
      this.msgCurrLength = body.length;
    },
    async fnExistApiKey(){
      let params = {};
      await templateApi.selectApiKey(params).then(response =>{
        const result = response.data;
        if(result.success) {
          if(this.$gfnCommonUtils.isEmpty(result.data)){
            confirm.fnAlert(this.componentsTitle, '해당 프로젝트의 사용가능한 API 키가 존재하지 않습니다.\n템플릿을 등록/수정/검수요청 하실 수 없습니다.');
          }
        }
      });
    },
    async fnValidUseChGrp(){
      let params = {chGrp: 'KKO'};
      await templateApi.selectValidUseChGrp(params).then(response =>{
        const result = response.data;
        if(result.success) {
          if(this.$gfnCommonUtils.isEmpty(result.data)){
            confirm.fnAlert(this.componentsTitle, '이용하실 수 없는 채널입니다.');
            this.$router.back();
          }
        } else {
          confirm.fnAlert(this.componentsTitle, '시스템 오류입니다. 잠시 후 다시 시도하세요.');
          this.$router.back();
        }
      });
    },
    //template 정보 조회
    fnSetTemplateInfo(){
      if(!this.$gfnCommonUtils.isEmpty(this.tmpltKey)){
        this.isInsert = false;
        this.fnSelectAlimTalkTmpltInfo();
      } else {
        this.isInsert = true;
        //this.fnAddButton();
      }
    },
    //템플릿 정보 조회
    fnSelectAlimTalkTmpltInfo(){
      const params = {tmpltKey: this.tmpltKey};
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
            rtnData.tmpltImgUrl = tmpltInfo.templateImageUrl;
            rtnData.tmpltImgPath = '';

            this.tmpltData = Object.assign({}, rtnData);
            this.tmpltData.tmpltInfo = '';
            this.fnSetCurrLength();

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
      if(this.$gfnCommonUtils.isEmpty(this.tmpltKey) || !this.tmpltData.senderKey){
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
        tmpltKey: this.tmpltKey,
        senderKey: this.tmpltData.senderKey
      };
      templateApi.procInspectRequestKkoTmplt(params).then(response => {
        const result = response.data;
        if(result.success) {
          eventBus.$on('callbackEventBus', this.fnMovePage);
          confirm.fnAlert(this.componentsTitle, '알림톡 템플릿을 검수요청 하였습니다.'+'\n'+this.reflectionMsg, 'CALLBACK');
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnIsValidApprvReqTmplt(){
      if(this.isInsert && !this.tmpltData.senderKeyType){
        confirm.fnAlert(this.componentsTitle, '카카오 채널타입을 선택해주세요.');
        return false;
      }
      if(!this.tmpltData.senderKey){
        confirm.fnAlert(this.componentsTitle, '카카오 채널키를 선택해주세요.');
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
      if(this.msgLimitLength < this.msgCurrLength){
        const alertMsg = '메시지 내용은 '+this.msgLimitLength+'자를 넘지 않아야됩니다.\n(현재 : '+this.msgCurrLength+'자)';
        confirm.fnAlert(this.componentsTitle, alertMsg);
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
      if(this.tmpltData.emphasizeType == 'IMAGE'){
        if(!this.tmpltData.tmpltImgPath){
          confirm.fnAlert(this.componentsTitle, '템플릿강조유형이 이미지형일때 이미지 선택은 필수입니다.');
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

      //예약어 사용여부
      const validContainRsvNm = this.$gfnCommonUtils.validContainRsvNm('ALIMTALK', this.tmpltData.tmpltContent);
      if(validContainRsvNm.success == false){
        confirm.fnAlert(this.componentsTitle, validContainRsvNm.message);
        return false;
      }

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
      params.tmpltKey = this.tmpltKey;
      params.tmpltButtonsStr = JSON.stringify(this.tmpltData.buttonList);

      if(this.tmpltData.emphasizeType == 'IMAGE'){
        let fd = this.fnParamSetting(params);
        await templateApi.procUpdateRequestKkoImgTmplt(fd).then(response => {
          const result = response.data;
          if(result.success) {
            eventBus.$on('callbackEventBus', this.fnMovePage);
            confirm.fnAlert(this.componentsTitle, '알림톡 템플릿을 수정요청 하였습니다.'+'\n'+this.reflectionMsg, 'CALLBACK');
          } else {
            confirm.fnAlert(this.componentsTitle, result.message);
          }
        });
      }else{
        await templateApi.procUpdateRequestKkoTmplt(params).then(response => {
          const result = response.data;
          if(result.success) {
            eventBus.$on('callbackEventBus', this.fnMovePage);
            confirm.fnAlert(this.componentsTitle, '알림톡 템플릿을 수정요청 하였습니다.'+'\n'+this.reflectionMsg, 'CALLBACK');
          } else {
            confirm.fnAlert(this.componentsTitle, result.message);
          }
        });
      }
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

      if(this.tmpltData.emphasizeType == 'IMAGE'){
        let fd = this.fnParamSetting(params);
        await templateApi.procApprvRequestKkoImgTmplt(fd).then(response => {
          const result = response.data;
          if(result.success) {
            eventBus.$on('callbackEventBus', this.fnMovePage);
            confirm.fnAlert(this.componentsTitle, '알림톡 템플릿을 등록요청 하였습니다.'+'\n'+this.reflectionMsg, 'CALLBACK');
          } else {
            confirm.fnAlert(this.componentsTitle, result.message);
          }
        });
      }else{
        await templateApi.procApprvRequestKkoTmplt(params).then(response => {
          const result = response.data;
          if(result.success) {
            eventBus.$on('callbackEventBus', this.fnMovePage);
            confirm.fnAlert(this.componentsTitle, '알림톡 템플릿을 등록요청 하였습니다.'+'\n'+this.reflectionMsg, 'CALLBACK');
          } else {
            confirm.fnAlert(this.componentsTitle, result.message);
          }
        });
      }
    },
    fnMovePage(){
      this.$router.push('alimTalkTemplateList');
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
    //카카오톡 카카오채널키 리스트 조회
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
    fnFileDown: function(fileId, name) {
      if(fileId != "") {
        var params = {
              fileId    : fileId,
              fileNm      : name
          };

        commonUtilApi.downloadFile(params).then(response =>{
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement("a");
            link.href = url;
            link.setAttribute("download", name); //or any other extension
            document.body.appendChild(link);
            link.click();
        });
      }
    },
    //단축 URL 선택
    btnSelect(shortendUrl){
      if(this.tmpltData.buttonList.length > 0 && this.selIdx !== null && this.tmpltData.buttonList[this.selIdx]){
        // mobile link
        this.$set(this.tmpltData.buttonList[this.selIdx], 'linkMo', shortendUrl)

        // pc link
        this.$set(this.tmpltData.buttonList[this.selIdx], 'linkPc', shortendUrl)
      }

      this.selIdx = null
    },
    //이미지선택 팝업
    fnImgReg(){
      this.$refs.imageInput.click();
    },
    fnDelImg(){
      this.tmpltData.tmpltImgUrl = '';
      this.tmpltData.tmpltImgPath = '';
      this.$refs.imageInput.value = '';
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
    //이미지선택 후 이벤트
    fnImagePreview(e) {
      if(e.target.files && e.target.files.length > 0){
        var file = e.target.files[0];
        const permitExten = 'jpg,png'.split(',');
        const extnIdx = file.name.lastIndexOf('.');
        const extn = file.name.substring(extnIdx+1).toLowerCase();

        if((permitExten.indexOf(extn) < 0)){
          confirm.fnAlert('', '허용되지 않는 확장자입니다.\n(이미지는 .jpg, .png 확장자 지원합니다.)');
          this.fnDelImg();
          return;
        }

        if(file.size > 512000){
          confirm.fnAlert("", "이미지 용량은 500KB 이하여야합니다.");
          this.fnDelImg();
          return;
        }

        //이미지 로드 후 비율 및 사이즈 체크
        var imgUrl = URL.createObjectURL(file);
        this.$refs.imgChk.src=imgUrl;

      } else {
        this.fnDelImg();
      }
    },
    fnImgLoad(){
      var img = this.$refs.imgChk;

      var imgWidth = img.width;
      var imgHeight = img.height;
      var imgRatio = img.height / img.width;

      if(imgRatio != 0.5){
        confirm.fnAlert("", "이미지 비율은 2:1 이여야합니다.");
        this.fnDelImg();
        return;
      }

      if(imgWidth <= 500 && imgHeight <= 250){
        confirm.fnAlert("", "이미지의 가로 길이 * 세로 길이는 500 * 250 초과 이여야합니다.");
        this.fnDelImg();
        return;
      }

      this.tmpltData.tmpltImgUrl = this.$refs.imgChk.src;
      this.tmpltData.tmpltImgPath = this.$refs.imgChk.src;
    },
    fnParamSetting(params){
      const uploadFile = this.$refs.imageInput;

      let fd = new FormData();
      fd.append('file', uploadFile.files[0]);
      fd.append('paramString', JSON.stringify(params));

      return fd;
    }
  }
}
</script>