<template>
  <div>

    <div class="contentHeader">
      <h2>발송 > 알림톡 <span v-if="nightSendYn == 'N'" class="ml20 font-size12 color1">야간 메시지 발송 제한으로 {{nightSendSthh}}:{{nightSendStmm}} ~ 다음날 {{nightSendEdhh}}:{{nightSendEdmm}} 까지 메시지 발송을 할 수 없습니다.<i class="fas fa-question-circle toolTip ml5"><span class="toolTipText" style="width:260px">야간 메시지 발송 제한 해제는 [관리자 콘솔] 프로젝트 기본정보에서 세팅 할 수 있습니다.</span></i></span></h2>
      <!-- <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="알림톡 발송 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
    </div>

    <div class="row">
      <div class="phone3 inline-block" style="width:30%">
        <div class="phoneFixed">
          <!-- phoneWrap -->
          <!-- 알림톡 -->
          <div v-if="previewMessageType == 'ALIMTALK'" class="phoneWrap">
            <img src="@/assets/images/common/phoneMockup3.svg" alt="알림톡 템플릿">
            <div class="phoneTextWrap3 scroll-y">
              <div>
                <p class="text-main"><i class="fal fa-envelope-open-text"></i> 알림톡 도착</p>
                <div v-if="sendData.emphasizeType == 'TEXT'" class="text-sub-wrap" style="padding:10px;">
                  <p v-if="!$gfnCommonUtils.isEmpty(sendData.tmpltEmpsSubTitle)" class="text-sub_1">{{sendData.tmpltEmpsSubTitle}}</p>
                  <p v-if="!$gfnCommonUtils.isEmpty(sendData.tmpltEmpsTitle)" class="text-sub scroll-y3">{{sendData.tmpltEmpsTitle}}</p>
                </div>
                <div v-if="sendData.emphasizeType == 'IMAGE'">
                    <div v-if="sendData.templateImageUrl != ''" class="phoneText2 mt10 text-center simulatorImg"
                      :style="'padding:48px;border-radius:0px;background-image: url('+sendData.templateImageUrl+');'">
                    </div>
                </div>
                <div class="text-sub-wrap" style="padding:10px;">
                  <span><pre>{{sendData.tmpltContent}}</pre></span>
                </div>
                <div v-for="(buttonInfo, idx) in sendData.buttonList" :key="idx">
                  <a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.name)" class="btnStyle1 backLightGray">{{buttonInfo.name}}</a>
                </div>
              </div>
            </div>
          </div>
          <!-- 알림톡 -->
          <!-- XMS -->
          <div v-if="previewMessageType == 'RPLC'" class="phoneWrap">
            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
            <div class="phoneTextWrap">
              <div class="phoneText1">
                <div v-if="sendData.rplcSendType == 'MMS'" :style="'background-image: url('+sendData.fbInfo.imgUrl+');padding:85px;'" class="mt10 text-center simulatorImg"> </div>
                <p v-if="sendData.rplcSendType != 'SMS'" class="mt15 font-size13">{{sendData.fbInfo.title}}</p>
                <p class="mt15"><pre>{{sendData.fbInfo.msg}}</pre></p>
              </div>
            </div>
          </div>
          <!--// XMS -->
          <!-- //phoneWrap -->
          <div class="phone_04_btn">
            <!--
            <a href="#self" class="btnStyle1 backBlack" title="알림톡">알림톡</a>
            <a href="#self" class="btnStyle1 backWhite" title="Push">SMS</a>
            -->
            <a @click="fnChgPreviewMessageType('ALIMTALK')" :class="'btnStyle1 '+(previewMessageType == 'ALIMTALK' ? 'backBlack' : 'backWhite')" title="알림톡">알림톡</a>
            <a v-if="sendData.rplcSendType != 'NONE'" @click="fnChgPreviewMessageType('RPLC')" :class="'btnStyle1 '+(previewMessageType != 'ALIMTALK' ? 'backBlack' : 'backWhite')" title="대체발송">{{sendData.rplcSendType}}</a>
          </div>
        </div>
      </div>
      <div class="of_h inline-block vertical-top consoleCon" style="width:60%">
        <div class="of_h user-phone">
          <div class="float-left" style="width:24%">
            <h4>01  발송정보</h4>
          </div>
          <div class="float-left" style="width:76%">
            <div class="of_h">
              <div style="width:20%" class="float-left">
                <h5>카카오 채널 *</h5>
              </div>
              <div style="width:80%">
                <select name="userConsole_sub0203_1" class="selectStyle2" style="width:42%" v-model="sendData.senderKey" :disabled="templateSet">
                  <option value="" selected>선택해주세요.</option>
                  <option v-for="senderKeyInfo in senderKeyList" :key="senderKeyInfo.senderKey" :value="senderKeyInfo.senderKey">{{senderKeyInfo.kkoChId}}</option>
                </select>
              </div>
            </div>
            <div class="of_h consolMarginTop">
              <div class="float-left" style="width: 20%;">
                <h5>템플릿</h5>
              </div>
              <div style="width: 80%;">
                <a href="#" @click.prevent="fnOpenAlimTalkTemplatePopup" class="btnStyle1 backLightGray" title="템플릿 불러오기" activity="READ">템플릿 선택</a>
              </div>
            </div>
          </div>
        </div>
        <hr>

        <div class="of_h user-phone">
          <div class="float-left" style="width:24%">
            <h4>02  메시지 내용</h4>
          </div>
          <div class="float-left" style="width:76%">
            <a href="#" @click.prevent="fnOpenAlimTalkContentsPopup" :class="fnIsEmptyObj(sendData.tmpltContent) ? 'btnStyle1 backLightGray mr5' : 'btnStyle1 backWhite mr5'" title="메시지 내용입력" activity="READ">내용보기</a>
            <div class="of_h consolMarginTop" style="min-height:33px;">
              <div style="width:20%" class="float-left">
                <h5 class="inline-block mr10">버튼</h5>
                <!-- <a href="#self" class="btnStyle1 backBlack">추가 +</a> -->
              </div>
              <div class="of_h" style="width:80%">
                <table class="table_skin1" style="width:100%">
                  <colgroup>
                    <col style="width:20%">
                    <col style="width:21%">
                    <col>
                  </colgroup>
                  <thead>
                  <tr>
                    <th class="text-center">타입</th>
                    <th class="text-center">버튼이름</th>
                    <th class="text-center end">버튼링크</th>
                  </tr>
                  </thead>
                  <tbody class="of_h">

                    <template v-for="(buttonInfo, idx) in sendData.buttonList">
                      <tr :key="idx">
                        <td class="text-left" :rowspan="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL' ? '2' : '1'">
                          <input type="text" class="inputStyle float-left" :value="fnGetButtonNm(buttonInfo.linkType)" disabled>
                        </td>
                        <td class="text-center" :rowspan="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL' ? '2' : '1'">
                          <input v-if="buttonInfo.linkType == 'AC'" type="text" class="inputStyle float-left" :value="buttonInfo.name" disabled>
                          <input v-else type="text" class="inputStyle float-left" :value="buttonInfo.name" disabled>
                        </td>
                        <td :class="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL' ? 'text-left' : 'text-left of_h'">
                          <!-- <h6 v-if="buttonInfo.linkType == 'DS'" class="float-left" v-html="buttonDSDescription"></h6> -->
                          <h6 v-if="buttonInfo.linkType == 'WL'" class="float-left" style="width:20%">Mobile</h6>
                          <h6 v-if="buttonInfo.linkType == 'AL'" class="float-left" style="width:20%">Android</h6>
                          <!-- <input v-if="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL'" type="text" class="inputStyle float-left" style="width:80%"> -->
                          <input v-if="buttonInfo.linkType == 'WL'" type="text" class="inputStyle float-left" style="width:80%" :value="buttonInfo.linkMo" disabled>
                          <input v-if="buttonInfo.linkType == 'AL'" type="text" class="inputStyle float-left" style="width:80%" :value="buttonInfo.linkAnd" disabled>
                        </td>
                      </tr>
                      <tr v-if="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL'" :key="idx+'_sub'">
                        <td class="text-left">
                          <div v-if="buttonInfo.linkType == 'WL'">
                            <h6 class="float-left" style="width:20%">PC</h6>
                            <input type="text" class="inputStyle float-left" style="width:80%" :value="buttonInfo.linkPc" disabled>
                          </div>
                          <div v-if="buttonInfo.linkType == 'AL'">
                            <h6 class="float-left" style="width:20%">IOS</h6>
                            <input type="text" class="inputStyle float-left" style="width:80%" :value="buttonInfo.linkIos" disabled>
                          </div>
                        </td>
                      </tr>
                    </template>
                  </tbody>
                </table>
              </div>
            </div>


            <div class="of_h consolMarginTop" style="min-height:33px;">
              <div style="width:20%" class="float-left">
                <h5>대체발송</h5>
              </div>
              <div>
                <input type="radio" id="rplcSendType_NONE" name="rplcSendType" value="NONE" v-model="sendData.rplcSendType" @change="fnChgRplcSendType">
                <label for="rplcSendType_NONE" class="mr30">미사용</label>
                <input type="radio" id="rplcSendType_SMS" name="rplcSendType" value="SMS" v-model="sendData.rplcSendType" @change="fnChgRplcSendType">
                <label for="rplcSendType_SMS" class="mr30">SMS</label>
                <input type="radio" id="rplcSendType_LMS" name="rplcSendType" value="LMS" v-model="sendData.rplcSendType" @change="fnChgRplcSendType">
                <label for="rplcSendType_LMS" class="mr30">LMS</label>
                <input type="radio" id="rplcSendType_MMS" name="rplcSendType" value="MMS" v-model="sendData.rplcSendType" @change="fnChgRplcSendType">
                <label for="rplcSendType_MMS">MMS</label>
              </div>
            </div>
            <div v-if="sendData.rplcSendType!='NONE'" class="of_h consolMarginTop" style="min-height:33px;">
              <div class="float-left" style="width: 20%;">
                <h5>대체발송 메시지</h5>
              </div>
              <div style="width: 80%;">
                <a @click="fnOpenReplacedSenderPopup" :class="fnIsEmptyObj(sendData.fbInfo.callback) ? 'btnStyle1 backLightGray' : 'btnStyle1 backWhite'" title="대체발송 내용입력" activity="READ">대체발송 내용입력</a>
              </div>
						</div>
            <div v-if="!$gfnCommonUtils.isEmpty(sendData.fbInfo.callback)" class="of_h consolMarginTop" style="min-height:33px;">
              <div style="width:20%" class="float-left">
                <h5>발신번호</h5>
              </div>
              <div style="width:80%" class="float-left">
                <h5>{{sendData.fbInfo.callback | hpNumberAddDash}}</h5>
              </div>
            </div>
          </div>

        </div>
        <hr>

        <div class="of_h user-phone">
          <div class="float-left" style="width:24%">
            <h4>03  수신자 선택</h4>
          </div>
          <div class="float-left" style="width:76%">
            <div class="of_h">
              <div style="width:18%" class="float-left">
                <h5>수신자 *</h5>
              </div>
              <div style="width:100%">
                <div>
                  <input type="radio" id="cuInputType_DICT" name="cuInputType" value="DICT" v-model="sendData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType" activity="READ">
                  <label for="cuInputType_DICT" class="mr30">수신자 직접입력</label>
                  <input type="radio" id="cuInputType_ADDR" name="cuInputType" value="ADDR" v-model="sendData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType" activity="READ">
                  <label for="cuInputType_ADDR" class="mr30">주소록 검색</label>
                  <input type="radio" id="cuInputType_EXCEL" name="cuInputType" value="EXCEL" v-model="sendData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType" activity="READ">
                  <label for="cuInputType_EXCEL" class="mr10">엑셀 업로드</label>
                  <a href="#" @click.prevent="fnExcelTmplteDownLoad" class="btnStyle1 backLightGray" title="샘플">
                    샘플 <i class="far fa-arrow-to-bottom"></i>
                  </a>
                  <input ref="excelFile" type="file" style="display:none;" @change="fnReadFile" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                </div>
              </div>
            </div>
            <div class="of_h">
              <div class="of_h float-right" style="width:82%">
                <p style="float:right;padding-top: 5px;">수신자 : {{recvCnt}}명</p>
                <div class="float-right consolMarginTop" style="width:100%;margin-top:1px;">
                  <textarea class="textareaStyle height120" v-model="sendData.cuInfo" disabled></textarea>
                </div>
                <p style="float:right;margin-top:6px;"><a @click="fnRemoveRecvInfo();" class="btnStyle1 small backWhite ml10" title="수신자 삭제">수신자 삭제</a></p>
              </div>
            </div>
          </div>

        </div>
        <hr>

        <div class="of_h user-phone">
          <div style="width:24%" class="float-left">
            <h4>04  발송옵션 선택</h4>
          </div>
          <div style="width:76%" class="float-left">
            <div class="of_h">
              <div class="float-left" style="width:18%">
                <h5>발송시간 *</h5>
              </div>
              <div class="float-left" style="width:26%">
                <input type="radio" id="rsrvSendYn_N" value="N" v-model="sendData.rsrvSendYn">
                <label for="rsrvSendYn_N" class="mr30">즉시</label>
                <input type="radio" id="rsrvSendYn_Y" value="Y" v-model="sendData.rsrvSendYn">
                <label for="rsrvSendYn_Y">예약</label>
              </div>
              <div v-if="sendData.rsrvSendYn == 'Y'" class="float-left" style="width:18%">
                <Calendar @update-date="fnUpdateRsrvDate" calendarId="rsrvDate" classProps="datepicker inputStyle" :initDate="sendData.rsrvDate" :customOption="{minDate: '-0y'}"></Calendar>
              </div>
              <div v-if="sendData.rsrvSendYn == 'Y'" class="float-right" style="width:36%">
                <select class="selectStyle2" style="width:47%" v-model="sendData.rsrvHH">
                  <option value="00">00</option>
                  <option v-for="hh in 23" :key="hh" :value="hh > 9 ? hh : '0'+hh">{{hh > 9 ? hh : '0'+hh}}</option>
                </select>
                : <select class="selectStyle2" style="width:47%" v-model="sendData.rsrvMM">
                  <option value="00">00</option>
                  <option v-for="mm in 5" :key="mm" :value="mm+'0'">{{mm+'0'}}</option>
                </select>
              </div>
            </div>
            <div class="of_h consolMarginTop">
              <div style="width:18%" class="float-left">
                <h5>태그</h5>
              </div>
              <div class="of_h" style="width:82%">
                <input type="text" class="inputStyle" style="width:100%" placeholder="캠페인 ID를 입력해주세요.(숫자, 영문, '-', '_' 만 입력가능합니다.)" v-model="sendData.campaignId" v-campaignIdFilter maxlength="20">
              </div>
            </div>
          </div>
        </div>
      <div v-if="feeType">
      <hr>
        <div class="of_h user-phone">
          <div style="width:24%" class="float-left">
            <h4>발송제한 금액</h4>
          </div>
          <div style="width:76%" class="float-left">
            <div class="of_h">
              <div style="width:40%" class="float-left">
                <h5 style="margin: 5px 0;">월 발송금액 / 월 발송제한금액</h5>
              </div>
              <div class="of_h" style="width:60%;">
                <p style="font-size: 14px; margin-top: 3px;">{{monthAmount | comma}} / {{monSenderLimitAmout | comma}}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
        <div class="mt20 float-right">
          <a href="#" @click.prevent="fnOpenTestSendInputPopup" class="btnStyle2 float-left" title="테스트 발송" activity="SAVE">테스트 발송</a>
          <a href="#" @click.prevent="fnSendAlimTalkMessage('N')" class="btnStyle2 backRed float-left ml10" title="발송" activity="SAVE">발송</a>
        </div>
      </div>
    </div>
	
    <AlimTalkTemplatePopup :propSenderKey.sync="sendData.senderKey" :alimTalkTemplateOpen.sync="alimTalkTemplateOpen" ref="alimTalkTmplPopup"></AlimTalkTemplatePopup>
    <AlimTalkContentsPopup :alimTalkContsOpen.sync="alimTalkContsOpen" :sendData="sendData"></AlimTalkContentsPopup>
    <ReplacedSenderPopup :rplcSendOpen.sync="rplcSendOpen" ref="rplcSendPopup"></ReplacedSenderPopup>
    <DirectInputPopup :directInputOpen.sync="directInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid" :recvInfoLst="sendData.recvInfoLst"></DirectInputPopup>
    <AddressInputPopup :addressInputOpen.sync="addressInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid"></AddressInputPopup>
    <TestSendInputPopup :testSendInputOpen.sync="testSendInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid" ref="testSendInputPopup"></TestSendInputPopup>
    <nightSendLimitPopup :nightSendLimitY.sync="nightSendLimitYn" :nightSendSthh="this.nightSendSthh" :nightSendStmm="this.nightSendStmm" :nightSendEdhh="this.nightSendEdhh" :nightSendEdmm="this.nightSendEdmm"/>
  </div>
</template>

<script>
import AlimTalkTemplatePopup from "@/modules/message/components/bp-alimTalkTemplate.vue";
import AlimTalkContentsPopup from "@/modules/message/components/bp-alimTalkContents.vue";
import ReplacedSenderPopup from "@/modules/message/components/bp-replacedSender.vue";
import DirectInputPopup from "@/modules/message/components/bp-directInput.vue";
import AddressInputPopup from "@/modules/message/components/bp-addressInput.vue";
import Calendar from "@/components/Calendar.vue";
import TestSendInputPopup from "@/modules/message/components/bc-testSendInput.vue";
import XLSX from 'xlsx';
import nightSendLimitPopup from "@/modules/message/components/bp-nightSendLimit.vue";

import confirm from "@/modules/commonUtil/service/confirm.js";
import messageApi from "@/modules/message/service/messageApi.js";
import templateApi from "@/modules/template/service/templateApi.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  name: 'sendAlimTalkMain',
  components : {
    AlimTalkTemplatePopup,
    AlimTalkContentsPopup,
    ReplacedSenderPopup,
    DirectInputPopup,
    AddressInputPopup,
    Calendar,
    TestSendInputPopup,
   nightSendLimitPopup
  },
  props: {
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '알림톡 발송';
      }
    },
  },
  filters: {
    comma (val) {
      return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    }
  },
  data() {
    return {
      templateSet: false,
      alimTalkTemplateOpen: false,
      alimTalkContsOpen: false,
      rplcSendOpen: false,
      directInputOpen: false,
      addressInputOpen: false,
      testSendInputOpen: false,
      recvCnt : 0,  //수신자명수
      inProgress: false,
      previewMessageType: 'ALIMTALK',  //ALIMTALK, RPLC
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
      buttonDSDescription : '카카오 메세지에 택배사 명과 송장번호를 기재한 후, 배송 조회 버튼을 추가하시면 메세지에서 택배사 명과 송장번호를 추출하여 배송 조회 카카오 검색페이지 링크가 자동으로 생성됩니다. 카카오에서 지원하는 택배사명과 운송장번호가 알림톡 메시지 내에 포함된 경우에만 배송조회 버튼이 표시됩니다. 배송 조회가 가능한 택배사는 <span style="color:#e11d21"><strong>카카오와 해당 택배사와의 계약 관계에 의해 변동될 수 있음을 유의해주시기 바랍니다.</strong></span>',
      tempFile: [],
      beforeCuInputType: 'DICT',
      monthAmount : 0,
      monSenderLimitAmout : '없음',
      feeType : false,
      senderKeyType: 'S', 
      senderKeyList: [],
      sendData : {
        chGrp: 'KKO',
        ch: 'ALIMTALK',
        requiredCuid : false,
        requiredCuPhone : true,
        msgKind: 'I',  //알림톡은 광고성을 보내지 않는다.
        rplcSendType: 'NONE',  //NONE, SMS, LMS, MMS
        cuInputType:'',  //DICT, ADDR, EXCEL
        rsrvSendYn: 'N',  //예약발송여부
        rsrvDate: this.$gfnCommonUtils.getCurretDate(),
        rsrvHH: '00',
        rsrvMM: '00',
        campaignId: '',
        cuInfo: '',
        senderKeyType : '',
        senderKey : '',
        tmpltCode : '',
        tmpltKey : '',
        emphasizeType: '',
        tmpltEmpsTitle: '',
        tmpltEmpsSubTitle: '',
        templateImageUrl : '',
        tmpltContent: '',
        contsVarNms: [], //메세지 내용 변수명
        buttonList : [],
        fbInfo: {},  //대체발송정보
        recvInfoLst: [],  //수신자정보
        testRecvInfoLst: [],  //테스트 수신자정보
        excelLimitRow: 0
      },
      nightSendSthh: '',
      nightSendStmm: '',
      nightSendEdhh: '',
      nightSendEdmm: '',
      nightSendYn : 'N',
      nightSendLimitYn : false
    }
  },
  watch : {
    recvCnt (newval) {
      if(newval>30000) {
        confirm.fnAlert(this.componentsTitle, "발송 최대 수신자 수는 30000명을 넘길 수 없습니다.");
        this.fnRemoveRecvInfo();
      }
    }
  },
  async mounted() {
    await this.fnExistApiKey();
    await this.fnValidUseChGrp();
    await this.fnGetSenderKeyList();
   await this.fnNightSendTime();
  },
  methods: {
    fnRemoveRecvInfo(){
      this.fnCallbackRecvInfoLst(null);
      this.$refs.excelFile.value = '';
      this.tempFile = [];
    },
    fnReadFile(){
      if(this.$refs.excelFile.files && this.$refs.excelFile.files.length > 0){
        const file = this.$refs.excelFile.files[0];
        let reader = new FileReader();

        reader.onload = () => {
          let data = reader.result;
          let workbook = XLSX.read(data, {type: 'binary'});
          let sheetName = '';
          let excelArray = [];

          if(workbook.SheetNames && workbook.SheetNames.length > 0){
            sheetName = workbook.SheetNames[0];
          }
          if(this.$gfnCommonUtils.isEmpty(sheetName) == false){
            excelArray = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName]);
          }

          let recvArr = [];

          for(var i=0; i<excelArray.length; i++) {
            if(i!=(excelArray.length-1)) recvArr[i] = excelArray[i+1].Template;
          }

          recvArr = new Set(recvArr);
          this.sendData.excelLimitRow = (excelArray.length-1);
          this.recvCnt = recvArr.size;
        };
        reader.readAsBinaryString(file);
      } else {
        this.recvCnt = 0;
      }
    },
    fnReset(){
      Object.assign(this.$data, this.$options.data.apply(this));
      this.fnGetSenderKeyList();
      this.fnNightSendTime();
    },
    async fnExistApiKey(){
      let params = {};
      await messageApi.selectApiKey(params).then(response =>{
        const result = response.data;
        if(result.success) {
          if(this.$gfnCommonUtils.isEmpty(result.data)){
            confirm.fnAlert(this.componentsTitle, '해당 프로젝트의 사용가능한 API 키가 존재하지 않습니다.\n메시지 발송하실 수 없습니다.');
          } else {
            // 사용가능한 api 키가 존재하면 발송제한 금액 세팅
            this.fnSetSentAmount();
          }
        } 
      });
    },
    async fnValidUseChGrp(){
      let params = {chGrp: this.sendData.chGrp};
      await messageApi.selectValidUseChGrp(params).then(response =>{
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
    fnSetSentAmount() {
      let params = {};
      var vm = this;
      messageApi.setSentAmout(params).then(response =>{
        const result = response.data;
        if(result.success) {
          let resultData = result.data;
          vm.monthAmount = resultData.amountMap.month + "원";
          vm.monSenderLimitAmout = resultData.returnApiKeyMap.monSenderLimitAmount+"원";
          
          if(resultData.returnApiKeyMap.feeType == "PRE") {
            vm.feeType = false;
          } else {
            vm.feeType = true;
          }
        }
      });
    },
    //메세지 미리보기 변경
    fnChgPreviewMessageType(type){
      this.previewMessageType = type;
    },
    fnOpenTestSendInputPopup(){
      this.fnSetContsVarNms();
      this.$refs.testSendInputPopup.fnSetTestRecvInfoLst(this.sendData.testRecvInfoLst);
      this.testSendInputOpen = !this.testSendInputOpen;
    },
    //테스트 발송 callback
    fnCallbackTestRecvInfoLst(testRecvInfoLst){
      if(testRecvInfoLst != null){
        this.sendData.testRecvInfoLst = Object.assign([], testRecvInfoLst);
        this.fnSendAlimTalkMessage('Y');
      } else {
        this.sendData.testRecvInfoLst = [];
      }
    },
    //발송 정보 유효성 체크
    fnValidSendMsgData(testSendYn){
      if(this.fnSetContsVarNms() == false){
        return false;
      }
      if(!this.sendData.senderKeyType
        || !this.sendData.senderKey
        || !this.sendData.tmpltCode
        || !this.sendData.tmpltContent){
        confirm.fnAlert(this.componentsTitle, '템플릿을 선택해주세요.');
        return false;
      }
      if(testSendYn == 'Y'){
        if(!this.sendData.testRecvInfoLst == null || this.sendData.testRecvInfoLst.length == 0){
          confirm.fnAlert(this.componentsTitle, '테스트 수신자 정보를 입력해주세요.');
          return false;
        }
      } else {
        if(this.sendData.cuInputType == 'DICT' || this.sendData.cuInputType == 'ADDR'){
          if(!this.sendData.recvInfoLst == null || this.sendData.recvInfoLst.length == 0){
            confirm.fnAlert(this.componentsTitle, '수신자 정보를 입력해주세요.');
            return false;
          }
        }
        if(this.sendData.cuInputType == 'EXCEL'){
          if(this.$refs.excelFile.value != 0){
            this.tempFile = [];
            this.tempFile.push.apply(this.tempFile, this.$refs.excelFile.files);
          }
          const uploadFile = this.tempFile[0];
          if(!uploadFile){
            confirm.fnAlert(this.componentsTitle, '엑셀파일을 등록해주세요.');
            return false;
          }
          const permitExten = 'xls,xlsx'.split(',');
          const extnIdx = uploadFile.name.lastIndexOf('.');
          const extn = uploadFile.name.substring(extnIdx+1);
          if((permitExten.indexOf(extn) < 0)){
            confirm.fnAlert(this.componentsTitle, '허용되지 않는 확장자입니다.');
            return false;
          }
        }
        if(this.sendData.rplcSendType != 'NONE'){
          if(!this.sendData.fbInfo.callback){
            confirm.fnAlert(this.componentsTitle, '대체발송시 대체발송 발신번호를 입력해주세요.');
            return false;
          }
          if(!this.sendData.fbInfo.msg){
            confirm.fnAlert(this.componentsTitle, '대체발송시 대체발송 내용을 입력해주세요.');
            return false;
          }
        }
      }
      return true;
    },
    //메시지 발송 처리
    async fnSendAlimTalkMessage(testSendYn){
      if(this.inProgress){
        confirm.fnAlert(this.componentsTitle, '알림톡 메시지 발송 처리중입니다.');
        return;
      }

      //유효성 체크
      if(this.fnValidSendMsgData(testSendYn) == false) return;

      if(testSendYn != 'Y' && this.fnNightSendCheck() == false) return;

      //발송처리
      let params = Object.assign({}, this.sendData);
      params.testSendYn = testSendYn;

      if(testSendYn == 'Y'){
        params.recvInfoLst = Object.assign([], this.sendData.testRecvInfoLst);
        params.cuInputType = 'DICT';
        //테스트 발송은 대체발송 하지 않는다.
        params.rplcSendType = 'NONE'
        params.fbInfo = {};
        //테스트 발송은 즉시발송만 가능
        params.rsrvSendYn = 'N';
      }

      let fd = new FormData();
      if(this.sendData.cuInputType == 'EXCEL'){
        fd.append('file', this.tempFile[0]);
        this.$refs.excelFile.value = '';
        this.tempFile = [];
      }
      fd.append('paramString', JSON.stringify(params));

      this.inProgress = true;
      const vm = this;

      await messageApi.sendAlimTalkMessage(fd).then(response =>{
        this.inProgress = false;
        const result = response.data;

        if(result.success) {
          if(testSendYn == 'Y'){
            if(!this.$gfnCommonUtils.isEmpty(result.message)){
              confirm.fnAlert(this.componentsTitle, result.message);
            } else {
              confirm.fnAlert(this.componentsTitle, '발송하였습니다.');
            }
          } else {
            if(result.data != null && !this.$gfnCommonUtils.isEmpty(result.data.feeMsg)){
              eventBus.$on('callbackEventBus', this.fnAlertFeeMsgCallBack);
              confirm.fnAlert(this.componentsTitle, result.data.feeMsg, 'ALERT', result);
            } else {
              this.fnAlertFeeMsgCallBack(result);
            }
          }
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      })
      .catch(function () {
        vm.inProgress = false;
      });
      
      // 발송 제한 금액 세팅
      this.fnSetSentAmount();
    },
    fnAlertFeeMsgCallBack(result){
      if(this.$gfnCommonUtils.isEmpty(result.message)){
        confirm.fnAlert(this.componentsTitle, '발송 요청 처리 되었습니다.');
      } else {
        confirm.fnAlert(this.componentsTitle, result.message);
      }
      this.fnReset();
    },
    fnUpdateRsrvDate(sltDate){
      this.sendData.rsrvDate = sltDate;
    },
    fnClickCuInputType(e){
      this.tempFile = [];
      this.tempFile.push.apply(this.tempFile, this.$refs.excelFile.files);
      this.$refs.excelFile.value = '';

      if(this.sendData.cuInputType == e.target.value){
        this.fnChgCuInputType('N');
      }
      this.beforeCuInputType = this.sendData.cuInputType;
    },
    //수신자 입력 타입 변경시
    fnChgCuInputType(chgYn){
      if(this.$gfnCommonUtils.defaultIfEmpty(chgYn, 'Y') == 'Y'){
        if((this.beforeCuInputType != 'DICT' && this.beforeCuInputType != 'ADDR')
          || (this.sendData.cuInputType != 'DICT' && this.sendData.cuInputType != 'ADDR')){
          this.fnCallbackRecvInfoLst(null);  //수신자 입력 타입 변경시 수신자 정보 초기화
        }
      }
      if(!this.sendData.tmpltContent){
        confirm.fnAlert(this.componentsTitle, '템플릿을 먼저 선택해주세요.');
        return;
      }
      if(this.fnSetContsVarNms() == false){
        return;
      }

      if(this.sendData.cuInputType == 'DICT'){  //직접입력
        //수신자 직접입력 팝업 호출
        this.directInputOpen = true;
      } else if(this.sendData.cuInputType == 'ADDR'){  //주소록
        //주소록 검색 팝업 호출
        this.addressInputOpen = true;
      } else if(this.sendData.cuInputType == 'EXCEL'){  //엑셀
        //엑셀파일찾기 호출
        this.$refs.excelFile.click();
      }
    },
    fnSetContsVarNms(){
      const rsvNmSet = new Set(['phone']);
      const conts = this.sendData.tmpltContent + (typeof this.sendData.fbInfo.msg === 'undefined' ? '' : this.sendData.fbInfo.msg);
      let varNms = [];
      let containRsvNm = false;
      conts.replace(/#\{(([a-z|A-Z|0-9|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|_])+)\}/g, function($0, $1) {
        if(rsvNmSet.has($1)){
          containRsvNm = true;
          return false;
        }
        varNms.push($1);
      });
      if(containRsvNm){
        confirm.fnAlert(this.componentsTitle, '발송 내용 변수 phone 은 예약어로 사용하실 수 없습니다.');
        return false;
      } else {
        this.sendData.contsVarNms = this.fnSetArrayRemoveDupliVal(varNms);
        return true;
      }
    },
    //array에 중복 항목을 제거한다.
    fnSetArrayRemoveDupliVal(array){
      let seen = {};
      return array.filter(function(item) {
        return seen.hasOwnProperty(item) ? false : (seen[item] = true);
      });
    },
    fnGetButtonNm(linkType){
      let buttonNm = '';
      this.buttonLinkTypeList.forEach(function(buttonInfo){
        if(buttonInfo.linkType == linkType){
          buttonNm = buttonInfo.name;
          return false;
        }
      });
      return buttonNm;
    },
    fnOpenReplacedSenderPopup(){
      this.sendData.fbInfo.ch = this.sendData.rplcSendType;
      this.$refs.rplcSendPopup.fnSetfbInfo(this.sendData);
      this.rplcSendOpen = !this.rplcSendOpen;
    },
    //대체발송 정보 callback
    fnCallbackFbInfo(fbInfo) {
      this.sendData.fbInfo = fbInfo;
      this.fnCallbackRecvInfoLst(null);  //수신자 정보 초기화
    },
    //대체발송 타입 변경시
    fnChgRplcSendType(){
      this.sendData.fbInfo = {};  //대체발송 정보 초기화
      this.fnCallbackRecvInfoLst(null);  //수신자 정보 초기화
      //this.$refs.cuInputType_ALL.click();
      if(this.sendData.rplcSendType == 'NONE'){
        this.previewMessageType = 'ALIMTALK';
      }
    },
    //템플릿 정보 Set
    fnSetTemplateInfo(templateInfo){
      this.templateSet = true;
      Object.assign(this.sendData, templateInfo);
    },
    fnOpenAlimTalkContentsPopup(){
      this.alimTalkContsOpen = true;
    },
    fnOpenAlimTalkTemplatePopup(){
      let senderKey = this.sendData.senderKey;
      if(senderKey == "") {
        confirm.fnAlert(this.componentsTitle, "카카오 채널을 선택해 주세요.");
        return;
      }
      this.$refs.alimTalkTmplPopup.fnSelectSenderKeyList();
      this.$refs.alimTalkTmplPopup.fnAlimTalkPopOpen();
      this.alimTalkTemplateOpen = true;
    },
    //빈오브젝트확인
    fnIsEmptyObj(obj){
      if(typeof obj === 'undefined') return true;
      if(Object.keys(obj).length === 0) return true;
      return false;
    },
    //수신자 정보 callback
    fnCallbackRecvInfoLst(recvInfoLst, addYn) {
      if(recvInfoLst != null){
        if(this.$gfnCommonUtils.defaultIfEmpty(addYn, 'N') == 'Y'){
          this.sendData.recvInfoLst = this.sendData.recvInfoLst.concat(recvInfoLst);
        } else {
          this.sendData.recvInfoLst = recvInfoLst;
        }
        //수신자 중복제거
        this.fnDelDuplRecvInfo();

        this.recvCnt = this.sendData.recvInfoLst.length;
        this.sendData.cuInfo = JSON.stringify(this.sendData.recvInfoLst);
      } else {
        this.recvCnt = 0;
        this.sendData.recvInfoLst = [];
        this.sendData.cuInfo = '';
      }
    },
    //수신자 중복 제거
    fnDelDuplRecvInfo(){
      const vm = this;
      this.sendData.recvInfoLst = this.sendData.recvInfoLst.filter(function(item, i){
        return (
          vm.sendData.recvInfoLst.findIndex((item2) => {
            return item.phone === item2.phone
          }) === i
        );
      });
    },
    async fnExcelTmplteDownLoad(){
      if(this.fnSetContsVarNms() == false) return;
      var params = {
        contsVarNms : this.sendData.contsVarNms,
        requiredCuid: this.sendData.requiredCuid,
        requiredCuPhone: this.sendData.requiredCuPhone
      };
      await messageApi.excelDownSendAlimTalkRecvTmplt(params);
    },
    fnGetSenderKeyList(){
      const params = {kkoSvc: this.sendData.ch, senderKeyType: this.senderKeyType};
      templateApi.selectSenderKeyList(params).then(response => {
        var result = response.data;
        if(result.success) {
          this.senderKeyList = result.data;
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    //야간 메시지 전송 체크
    fnNightSendCheck(){
      let params = {
        nightSendYn : this.nightSendYn,
        rsrvSendYn : this.sendData.rsrvSendYn,
        rsrvHH : this.sendData.rsrvHH,
        rsrvMM : this.sendData.rsrvMM,
        nightSendSthh : this.nightSendSthh,
        nightSendStmm : this.nightSendStmm,
        nightSendEdhh : this.nightSendEdhh,
        nightSendEdmm : this.nightSendEdmm
      }
      var nightSendLimitYn = messageApi.checkNightSendTime(params);

      if(nightSendLimitYn){
        this.nightSendLimitYn = nightSendLimitYn;
      }
      
      return !nightSendLimitYn;
    },
    // 야간 메시지 전송 시간 확인
		async fnNightSendTime() {
			let params = {
        isChk : "Y"
      };
			await messageApi.selectNightSendTime(params).then(response =>{
				var result = response.data;
				if(result.success) {
					this.nightSendSthh = result.data.nightSendSthh;
					this.nightSendStmm = result.data.nightSendStmm;
					this.nightSendEdhh = result.data.nightSendEdhh;
					this.nightSendEdmm = result.data.nightSendEdmm;
          this.nightSendYn = result.data.nightSendYn;
				} else {
					confirm.fnAlert(this.title, result.message);
				}
			});
		},
  }
}
</script>