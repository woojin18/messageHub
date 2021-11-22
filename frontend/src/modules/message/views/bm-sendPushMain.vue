<template>
  <div>
    <div class="contentHeader">
      <h2>발송 > 푸시</h2>
    </div>

    <!-- 본문 -->
    <div class="row row-no-margin">
      <div class="phone3 inline-block" style="width:30%">
        <div class="phoneFixed">
          <!-- phoneWrap -->
          <!-- PUSH -->
          <div v-if="previewMessageType == 'PUSH'" class="phoneWrap">
            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
            <div class="phoneTextWrap scroll-yc">
              <div v-if="!fnIsEmpty(sendData.pushTitle)" class="phoneText1">
                <p><span v-if="sendData.msgKind == 'A'">(광고)</span>{{sendData.pushTitle}}</p>
              </div>
              <div v-if="sendData.msgType == 'IMAGE' && fnIsEmpty(sendData.imgUrl)" class="phoneText2 mt10 text-center" style="padding:65px">
                <i class="fas fa-image-polaroid" style="font-size:38px; color:#D5D5D5"></i>
                <p class="font-size14 color3 mt15">이미지 영역</p>
              </div>
              <div v-if="sendData.msgType == 'IMAGE' && !fnIsEmpty(sendData.imgUrl)" class="phoneText2 mt10 text-center simulatorImg"
                :style="'padding:65px;background-image: url('+sendData.imgUrl+');'">
              </div>
              <div>
                <p class="font-size14 color4 mt10">
                  <span><pre><span v-if="fnIsEmpty(sendData.pushTitle) && sendData.msgKind == 'A'">(광고)</span>{{sendData.pushContent}}</pre></span>
                  <br v-if="!fnIsEmpty(sendData.pushContent)"/>
                  <span v-if="sendData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(sendData.rcvblcNumber)">
                    {{sendData.rcvblcNumber}}
                  </span>
                </p>
              </div>
            </div>
          </div>
          <!--// PUSH -->
          <!-- XMS -->
          <div v-if="previewMessageType == 'RPLC'" class="phoneWrap">
            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
            <div class="phoneTextWrap scroll-yc">
              <div class="phoneText1">
                <div v-if="sendData.rplcSendType == 'MMS'" :style="'background-image: url('+sendData.fbInfo.imgUrl+');padding:85px;'" class="mt10 text-center simulatorImg"> </div>
                <p v-if="sendData.rplcSendType != 'SMS'" class="mt15 font-size13">{{sendData.fbInfo.title}}</p>
                <p class="mt15"><span><pre><span v-if="sendData.rplcSendType == 'SMS' && sendData.msgKind == 'A'">(광고)</span>{{sendData.fbInfo.msg}}</pre></span></p>
                <span v-if="sendData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(sendData.fbInfo.rcvblcNumber)">
                  {{sendData.fbInfo.rcvblcNumber}}
                </span>
              </div>
            </div>
          </div>
          <!--// XMS -->
          <div class="phone_04_btn">
            <a @click="fnChgPreviewMessageType('PUSH')" :class="'btnStyle1 '+(previewMessageType == 'PUSH' ? 'backBlack' : 'backWhite')" title="Push">Push</a>
            <a v-if="sendData.rplcSendType != 'NONE'" @click="fnChgPreviewMessageType('RPLC')" :class="'btnStyle1 '+(previewMessageType != 'PUSH' ? 'backBlack' : 'backWhite')" title="Push">{{sendData.rplcSendType}}</a>
          </div>
          <!-- //phoneWrap -->
        </div>

      </div>
      <div class="of_h inline-block vertical-top consoleCon" style="width:60%">
        <div class="row user-phone of_h row-no-margin">
          <div style="width:24%" class="float-left">
            <h4>01 발송정보</h4>
          </div>
          <div style="width:76%" class="float-left">
            <a @click="fnOpenPushTemplatePopup" class="btnStyle1 backLightGray" title="템플릿 불러오기" activity="READ">템플릿 불러오기</a>

            <div class="of_h consolMarginTop">
              <div style="width:18%" class="float-left">
                <h5>발송정책 *</h5>
              </div>
              <div style="width:82%">
                <input type="radio" name="send" value="ALL" id="serviceCode_ALL" v-model="sendData.serviceCode">
                <label for="serviceCode_ALL" class="mr30">ALL</label>
                <input type="radio" name="send" value="FCM" id="serviceCode_FCM" v-model="sendData.serviceCode">
                <label for="serviceCode_FCM" class="mr30">FCM(안드로이드)</label>
                <input type="radio" name="send" value="APNS" id="serviceCode_APNS" v-model="sendData.serviceCode">
                <label for="serviceCode_APNS">APNS(IOS)</label>
              </div>
            </div>

            <div class="of_h">
              <div style="width:18%" class="float-left">
                <h5 >메시지구분 *</h5>
              </div>
              <div style="width:82%">
                <input type="radio" name="Sortation" value="A" id="msgKind_A" v-model="sendData.msgKind">
                <label for="msgKind_A" class="mr30">광고성</label>
                <input type="radio" name="Sortation" value="I" id="msgKind_I" v-model="sendData.msgKind">
                <label for="msgKind_I">정보성</label>
              </div>
            </div>
          </div>

        </div>
        <hr>

        <div class="row user-phone of_h row-no-margin">
          <div style="width:24%" class="float-left">
            <h4>02  메시지 내용</h4>
          </div>
          <div style="width:76%" class="float-left">
            <a @click="fnOpenPushContentsPopup" :class="fnIsEmpty(sendData.pushContent) ? 'btnStyle1 backLightGray mr5' : 'btnStyle1 backWhite mr5'" title="메시지 내용입력" activity="READ">내용입력</a>
            <a v-if="sendData.rplcSendType!='NONE'" @click="fnOpenReplacedSenderPopup" :class="fnIsEmptyObj(sendData.fbInfo.callback) ? 'btnStyle1 backLightGray' : 'btnStyle1 backWhite'" title="대체발송 내용입력" activity="READ">대체발송 내용입력</a>
            <div class="of_h consolMarginTop">
              <div style="width:18%" class="float-left">
                <h5>메시지타입 *</h5>
              </div>
              <div style="width:82%">
                <input type="radio" id="msgType_BASE" value="BASE" v-model="sendData.msgType">
                <label for="msgType_BASE" class="mr30">기본</label>
                <input type="radio" id="msgType_IMAGE" value="IMAGE" v-model="sendData.msgType">
                <label for="msgType_IMAGE">이미지</label>
              </div>
            </div>
            <div v-if="sendData.msgType == 'IMAGE'" class="of_h">
              <div style="width:18%" class="float-left">
                <h5>이미지</h5>
              </div>
              <div class="of_h" style="width:82%">
                <div class="float-left" style="width:24%"><a @click="fnOpenImageManagePopUp" class="btnStyle1 backLightGray width100_" style="width:24%" title="이미지선택" activity="READ">이미지선택</a></div>
                <input type="text" class="inputStyle float-right" style="width:75%; padding:5px 15px" v-model="sendData.imgUrl" disabled>
              </div>
            </div>
            <div class="of_h">
              <div style="width:18%" class="float-left">
                <h5>대체발송</h5>
              </div>
              <div>
                <input type="radio" id="rplcSendType_NONE" value="NONE" v-model="sendData.rplcSendType" @change="fnChgRplcSendType">
                <label for="rplcSendType_NONE" class="mr30">미사용</label>
                <input type="radio" id="rplcSendType_SMS" value="SMS" v-model="sendData.rplcSendType" @change="fnChgRplcSendType">
                <label for="rplcSendType_SMS" class="mr30">SMS</label>
                <input type="radio" id="rplcSendType_LMS" value="LMS" v-model="sendData.rplcSendType" @change="fnChgRplcSendType">
                <label for="rplcSendType_LMS" class="mr30">LMS</label>
                <input type="radio" id="rplcSendType_MMS" value="MMS" v-model="sendData.rplcSendType" @change="fnChgRplcSendType">
                <label for="rplcSendType_MMS">MMS</label>
              </div>
              <div v-if="!$gfnCommonUtils.isEmpty(sendData.fbInfo.callback)" class="of_h consolMarginTop">
                <div style="width:20%" class="float-left">
                  <h5>발신번호</h5>
                </div>
                <div style="width:80%" class="float-left">
                  <h5>{{sendData.fbInfo.callback}}</h5>
                </div>
              </div>
            </div>
          </div>

        </div>
        <hr>

        <div class="row user-phone of_h row-no-margin">
          <div style="width:24%" class="float-left">
            <h4>03  수신자 선택</h4>
          </div>
          <div style="width:76%" class="float-left">
            <div class="of_h">
              <div style="width:18%; height:50px" class="float-left">
                <h5>수신자 *</h5>
              </div>
              <div style="width:82%">
                <div>
                  <input type="radio" id="cuInputType_DICT" name="cuInputType" value="DICT" v-model="sendData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType" activity="READ">
                  <label for="cuInputType_DICT" class="mr30">수신자 직접입력</label>
                  <input type="radio" id="cuInputType_ADDR" name="cuInputType" value="ADDR" v-model="sendData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType" activity="READ">
                  <label for="cuInputType_ADDR">주소록 검색</label>
                </div>
                <div>
                  <input ref="cuInputType_ALL" type="radio" id="cuInputType_ALL" name="cuInputType" value="ALL" v-model="sendData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType" activity="READ">
                  <label for="cuInputType_ALL" class="mr30">앱사용자 전체발송</label>
                  <input type="radio" id="cuInputType_EXCEL" name="cuInputType" value="EXCEL" v-model="sendData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType" activity="READ">
                  <label for="cuInputType_EXCEL">엑셀 업로드</label>
                  <a @click="fnExcelTmplteDownLoad" class="btnStyle1 backLightGray ml20" title="샘플" activity="READ">샘플 <i class="far fa-arrow-to-bottom"></i></a>
                  <input ref="excelFile" type="file" style="display:none;" @change="fnReadFile" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                </div>
              </div>
            </div>
            <div class="of_h consolMarginTop">
              <div style="width:18%" class="float-left">
                <h5>APP_ID *</h5>
              </div>
              <div class="of_h" style="width:82%">
                <select class="selectStyle2" style="width:30%" v-model="sltAppId" @change="fnChangAppId">
                  <option value="">직접입력</option>
                  <option v-for="aplnId in aplnIdList" :key="aplnId.aplnId" :value="aplnId.aplnId">{{aplnId.aplnId}}</option>
                </select>
                <input type="text" class="inputStyle float-right" style="width:68%" :placeholder="sltAppId != '' ? '' : 'APP ID를 입력하세요'" v-model="sendData.appId" :disabled="sltAppId != ''">
                <h6><strong>수신자 : {{recvCnt}}명</strong><a @click="fnRemoveRecvInfo();" class="btnStyle1 small backWhite ml10" title="수신자 삭제">수신자 삭제</a></h6>
                <div class="float-right" style="width:100%">
                  <textarea class="textareaStyle height120" v-model="sendData.cuInfo" disabled></textarea>
                </div>
              </div>
            </div>
          </div>

        </div>
        <hr>

        <div class="row user-phone of_h row-no-margin">
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
              <div v-if="sendData.rsrvSendYn == 'Y'" class="float-left" style="width:20%">
                <Calendar @update-date="fnUpdateRsrvDate" calendarId="rsrvDate" classProps="datepicker inputStyle" :initDate="sendData.rsrvDate" :customOption="{minDate: '-0y'}"></Calendar>
              </div>
              <div v-if="sendData.rsrvSendYn == 'Y'" class="float-right" style="width:34%">
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
      <hr>
        <div class="of_h user-phone">
          <div style="width:24%" class="float-left">
            <h4>발송제한 금액</h4>
          </div>
          <div style="width:76%" class="float-left">
            <div class="of_h">
              <div style="width:40%" class="float-left">
                <h5 style="margin: 5px 0;">일 발송금액 / 일 발송제한금액</h5>
              </div>
              <div class="of_h" style="width:60%;">
                <p style="font-size: 14px; margin-top: 3px;">{{dayAmount | comma}} / {{daySenderLimitAmout | comma}}</p>
              </div>
            </div>
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
        <div class="mt30 float-right">
          <a @click="fnOpenTestSendInputPopup" class="btnStyle2 float-left" title="테스트 발송" data-toggle="modal" data-target="#test" activity="SAVE">테스트 발송</a>
          <a @click="fnSendPushMessage('N')" class="btnStyle2 backRed float-left ml10" title="발송" activity="SAVE">발송</a>
        </div>
      </div>
    </div>

    <PushTemplatePopup :pushTemplateOpen.sync="pushTemplateOpen" ref="pushTmplPopup"></PushTemplatePopup>
    <ImageManagePopUp @img-callback="fnCallbackImgInfo" :imgMngOpen.sync="imgMngOpen" :useCh="sendData.ch" ref="imgMngPopup"></ImageManagePopUp>
    <PushContentsPopup :pushContsOpen.sync="pushContsOpen" :sendData="sendData"></PushContentsPopup>
    <ReplacedSenderPopup :rplcSendOpen.sync="rplcSendOpen" ref="rplcSendPopup"></ReplacedSenderPopup>
    <DirectInputPopup :directInputOpen.sync="directInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid" :recvInfoLst="sendData.recvInfoLst"></DirectInputPopup>
    <AddressInputPopup :addressInputOpen.sync="addressInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid"></AddressInputPopup>
    <TestSendInputPopup :testSendInputOpen.sync="testSendInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="false" :requiredCuid="sendData.requiredCuid" ref="testSendInputPopup"></TestSendInputPopup>
  </div>
  <!-- //본문 -->
</template>

<script>
import MessageApi from "@/modules/message/service/messageApi.js";
import ImageManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import PushContentsPopup from "@/modules/message/components/bp-pushContents.vue";
import ReplacedSenderPopup from "@/modules/message/components/bp-replacedSender.vue";
import PushTemplatePopup from "@/modules/message/components/bp-pushTemplate.vue";
import DirectInputPopup from "@/modules/message/components/bp-directInput.vue";
import AddressInputPopup from "@/modules/message/components/bp-addressInput.vue";
import TestSendInputPopup from "@/modules/message/components/bc-testSendInput.vue";
import Calendar from "@/components/Calendar.vue";
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import XLSX from 'xlsx';

export default {
  name: "sendPushMain",
  components : {
    ImageManagePopUp,
    PushContentsPopup,
    ReplacedSenderPopup,
    DirectInputPopup,
    PushTemplatePopup,
    AddressInputPopup,
    TestSendInputPopup,
    Calendar
  },
  props: {
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '푸시 발송';
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
      imgMngOpen : false,
      pushContsOpen : false,
      rplcSendOpen : false,
      directInputOpen : false,
      pushTemplateOpen : false,
      addressInputOpen : false,
      testSendInputOpen : false,
      sltAppId : '',
      aplnIdList: {},
      recvCnt : 0,  //수신자명수
      previewMessageType : 'PUSH',  //메세지미리보기 타입(PUSH, RPLC)
      inProgress: false,
      tempFile: [],
      beforeCuInputType: 'ALL',
      monthAmount : 0,
      dayAmount : 0,
      apiKeyName : '',
      monSenderLimitAmout : '없음',
      daySenderLimitAmout : '없음',
      sendData : {
        chGrp: 'PUSH',
        ch: 'PUSH',
        serviceCode:'ALL', //발송정책(ALL, FCM, APNS)
        requiredCuid : true,  //app 로그인 ID 필수여부
        requiredCuPhone : false,  //수신자 폰번호 필수여부
        msgKind:'A',  //A, I
        msgType:'BASE',  //BASE, IMAGE
        rplcSendType:'NONE',  //NONE, SMS, LMS, MMS
        cuInputType:'',  //DICT, ADDR, ALL, EXCEL
        fileId : '',
        imgUrl : '',
        appId:'',
        cuInfo:'',
        rsrvSendYn:'N',  //예약발송여부
        rsrvDate:this.$gfnCommonUtils.getCurretDate(),
        rsrvHH:'00',
        rsrvMM:'00',
        campaignId:'',
        pushTitle:'',  //푸시제목
        pushContent:'',  //푸시내용
        rcvblcNumber:'',  //수신거부
        adtnInfo:'',  //부가정보
        recvInfoLst: [],  //수신자정보
        fbInfo: {},  //대체발송정보
        contsVarNms: [], //메세지 내용 변수명
        testRecvInfoLst: [],  //테스트 수신자정보
        excelLimitRow: 0
      }
    }
  },
  watch : {
	  recvCnt (newval, oldval) {
		  if(newval>30000) {
			  confirm.fnAlert(this.componentsTitle, "발송 최대 수신자 수는 30000명을 넘길 수 없습니다.");
			  this.fnRemoveRecvInfo();
		  }
	  }
  },
  async mounted() {
    await this.fnExistApiKey();
    await this.fnValidUseChGrp();
    await this.fnGetAppId();

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

        reader.onload = (e) => {
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
      this.fnGetAppId();
    },
    async fnExistApiKey(){
      let params = {};
      await MessageApi.selectApiKey(params).then(response =>{
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
      await MessageApi.selectValidUseChGrp(params).then(response =>{
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
      MessageApi.setSentAmout(params).then(response =>{
        const result = response.data;
        if(result.success) {
          let resultData = result.data;
          this.monthAmount = resultData.amountMap.month + "원";
          this.dayAmount = resultData.amountMap.day + "원";
          this.apiKeyName = resultData.returnApiKeyMap.apiKey;
          this.monSenderLimitAmout = resultData.returnApiKeyMap.monSenderLimitAmount=="없음" ? resultData.returnApiKeyMap.monSenderLimitAmount : resultData.returnApiKeyMap.monSenderLimitAmount+"원";
          this.daySenderLimitAmout = resultData.returnApiKeyMap.daySenderLimitAmount=="없음" ? resultData.returnApiKeyMap.daySenderLimitAmount : resultData.returnApiKeyMap.daySenderLimitAmount+"원";
        }
      });
    },
    //발송 정보 유효성 체크
    fnValidSendMsgData(testSendYn){
      if(this.fnSetContsVarNms() == false){
        return false;
      }
      if(!this.sendData.pushContent){
        confirm.fnAlert(this.componentsTitle, '푸시메시지 내용을 입력해주세요.');
        return false;
      }
      if(!this.sendData.appId){
        confirm.fnAlert(this.componentsTitle, 'APP ID를 입력해주세요.');
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
        //앱사용자 전체발송시 메시지 변수 사용금지
        if(this.sendData.cuInputType == 'ALL'){
          if(this.sendData.contsVarNms.length > 0){
            confirm.fnAlert(this.componentsTitle, '앱사용자 전체발송시 메시지 내용에 변수를 사용하실 수 없습니다.');
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
    //푸시 메시지 발송 처리
    async fnSendPushMessage(testSendYn){
      if(this.inProgress){
        confirm.fnAlert(this.componentsTitle, '푸시 메시지 발송 처리중입니다.');
        return;
      }

      if(this.fnValidSendMsgData(testSendYn) == false) return;

      //발송처리
      let params = Object.assign({}, this.sendData);
      params.testSendYn = testSendYn;

      if(testSendYn == 'Y'){
        params.recvInfoLst = Object.assign([], this.sendData.testRecvInfoLst);
        params.cuInputType = 'DICT';
        //테스트 발송은 대체발송 하지 않는다.
        params.rplcSendType = 'NONE'
        params.requiredCuPhone = false;
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
      await MessageApi.sendPushMessage(fd).then(response =>{
        this.inProgress = false;
        const result = response.data;

        if(result.success) {
          if(testSendYn == 'Y'){
            if(!this.fnIsEmpty(result.message)){
              confirm.fnAlert(this.componentsTitle, result.message);
            } else {
              confirm.fnAlert(this.componentsTitle, '발송하였습니다.');
            }
          } else {
            if(result.data != null && !this.fnIsEmpty(result.data.feeMsg)){
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
      this.fnSetSentAmount();
    },
    fnAlertFeeMsgCallBack(result){
      if(this.fnIsEmpty(result.message)){
        confirm.fnAlert(this.componentsTitle, '발송 요청 처리 되었습니다.');
      } else {
        confirm.fnAlert(this.componentsTitle, result.message);
      }
      this.fnReset();
    },
    fnUpdateRsrvDate(sltDate){
      this.sendData.rsrvDate = sltDate;
    },
    //템플릿 정보 Set
    fnSetTemplateInfo(templateInfo){
      this.sendData.msgKind = templateInfo.msgKind;  //메세지구분
      this.sendData.msgType = templateInfo.msgType;  //메세지타입
      this.sendData.fileId = templateInfo.fileId;  //파일ID
      this.sendData.imgUrl = templateInfo.imgUrl;  //이미지URL
      this.sendData.pushTitle = templateInfo.tmpltTitle;  //푸시제목
      this.sendData.pushContent = templateInfo.tmpltContent;  //푸시내용
      this.sendData.rcvblcNumber = templateInfo.rcvblcNumber;  //수신거부
      this.sendData.adtnInfo = templateInfo.adtnInfo;  //부가정보
    },
    fnChangAppId(){
      this.sendData.appId = this.sltAppId;
    },
    fnGetAppId(){
      var params = {};
      MessageApi.selectAppIdList(params).then(response => {
        var result = response.data;
        if(result.success) {
          this.aplnIdList = result.data;
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    //array에 중복 항목을 제거한다.
    fnSetArrayRemoveDupliVal(array){
      let seen = {};
      return array.filter(function(item) {
        return seen.hasOwnProperty(item) ? false : (seen[item] = true);
      });
    },
    fnOpenTestSendInputPopup(){
      this.fnSetContsVarNms();
      this.$refs.testSendInputPopup.fnSetTestRecvInfoLst(this.sendData.testRecvInfoLst);
      this.testSendInputOpen = !this.testSendInputOpen;
    },
    fnOpenImageManagePopUp(){
      this.$refs.imgMngPopup.fnSearch();
      this.imgMngOpen = !this.imgMngOpen;
    },
    fnOpenPushContentsPopup(){
      this.pushContsOpen = !this.pushContsOpen;
    },
    fnOpenReplacedSenderPopup(){
      this.sendData.fbInfo.ch = this.sendData.rplcSendType;
      
      if(this.sendData.fbInfo.ch != 'SMS' && this.$gfnCommonUtils.isEmpty(this.sendData.fbInfo.title) && this.sendData.msgKind == 'A'){
        this.sendData.fbInfo.title = '(광고)';
      } else if (this.sendData.msgKind == "I" && (this.$gfnCommonUtils.isEmpty(this.sendData.fbInfo.title) || this.sendData.fbInfo.title == "(광고)")){
        this.sendData.fbInfo.title = '';
      }
      this.$refs.rplcSendPopup.fnSetfbInfo(this.sendData);
      this.rplcSendOpen = !this.rplcSendOpen;
    },
    fnOpenPushTemplatePopup(){
      this.$refs.pushTmplPopup.fnSearch();
      this.pushTemplateOpen = !this.pushTemplateOpen;
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
      if(this.sendData.cuInputType == 'ALL'){  //전체발송
        return;
      }
      if(!this.sendData.pushContent){
        confirm.fnAlert(this.componentsTitle, '메시지 내용을 먼저 입력해주세요.');
        this.sendData.cuInputType = 'ALL'
        this.$refs.cuInputType_ALL.click();
        return;
      }
      if(this.fnSetContsVarNms() == false){
        this.sendData.cuInputType = 'ALL'
        this.$refs.cuInputType_ALL.click();
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
      const rsvNmSet = new Set(['cuid', 'phone']);
      const conts = this.sendData.pushContent + (typeof this.sendData.fbInfo.msg === 'undefined' ? '' : this.sendData.fbInfo.msg);
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
        confirm.fnAlert(this.componentsTitle, '발송 내용 변수 cuid, phone 은 예약어로 사용하실 수 없습니다.');
        return false;
      } else {
        this.sendData.contsVarNms = this.fnSetArrayRemoveDupliVal(varNms);
        return true;
      }
    },
    //내용입력 callback
    fnSetPushInfo(pushTitle, pushContent, rcvblcNumber, adtnInfo){
      if(this.sendData.pushContent != pushContent){
        this.fnCallbackRecvInfoLst(null);  //수신자 정보 초기화
      }
      this.sendData.pushTitle = pushTitle;
      this.sendData.pushContent = pushContent;
      this.sendData.rcvblcNumber = rcvblcNumber;
      this.sendData.adtnInfo = adtnInfo;
    },
    //이미지선택 callback
    fnCallbackImgInfo(imgInfo) {
      this.sendData.imgUrl = imgInfo.chImgUrl;
      this.sendData.fileId = imgInfo.fileId;
    },
    //테스트 발송 callback
    fnCallbackTestRecvInfoLst(testRecvInfoLst){
      if(testRecvInfoLst != null){
        this.sendData.testRecvInfoLst = Object.assign([], testRecvInfoLst);
        this.fnSendPushMessage('Y');
      } else {
        this.sendData.testRecvInfoLst = [];
      }
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
      let key, key2;
      this.sendData.recvInfoLst = this.sendData.recvInfoLst.filter(function(item, i){
        return (
          vm.sendData.recvInfoLst.findIndex((item2) => {
            key = '';
            if ('phone' in item) key += item.phone;
            if ('cuid' in item) key += item.cuid;
            key2 = '';
            if ('phone' in item2) key2 += item2.phone;
            if ('cuid' in item2) key2 += item2.cuid;
            return key === key2
          }) === i
        );
      });
    },
    //대체발송 정보 callback
    fnCallbackFbInfo(fbInfo) {
      this.sendData.fbInfo = fbInfo;
      this.fnCallbackRecvInfoLst(null);  //수신자 정보 초기화
    },
    //대체발송 타입 변경시
    fnChgRplcSendType(){
      if(this.sendData.rplcSendType == 'NONE'){
        this.sendData.requiredCuPhone = false;
      } else {
        this.sendData.requiredCuPhone = true;
      }
      this.sendData.fbInfo = {};  //대체발송 정보 초기화
      this.fnCallbackRecvInfoLst(null);  //수신자 정보 초기화
      this.$refs.cuInputType_ALL.click();
    },
    //메세지 미리보기 변경
    fnChgPreviewMessageType(type){
      this.previewMessageType = type;
    },
    //빈값확인
    fnIsEmpty(str){
      if(str) return false;
      else return true
    },
    //빈오브젝트확인
    fnIsEmptyObj(obj){
      if(typeof obj === 'undefined') return true;
      if(Object.keys(obj).length === 0) return true;
      return false;
    },
    //푸시 템플릿 엑셀 다운로드
    async fnExcelTmplteDownLoad(){
      if(this.fnSetContsVarNms() == false) return;
      var params = {
        contsVarNms : this.sendData.contsVarNms,
        requiredCuid: this.sendData.requiredCuid,
        requiredCuPhone: this.sendData.requiredCuPhone
      };
      await MessageApi.excelDownSendPushRecvTmplt(params);
    },
  }
}
</script>