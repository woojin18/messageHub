<template>
  <!-- 본문 -->
  <div class="row row-no-margin">
    <div class="phone3 inline-block" style="width:30%">
      <!-- phoneWrap -->
      <div class="phoneWrap">
        <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
        <div class="phoneTextWrap">
          <div class="phoneText1">
            <p>내용이 들어갑니다</p>
          </div>
        </div>
      </div>
      <!-- //phoneWrap -->
      <div class="phone_04_btn">
        <a href="#self" class="btnStyle3 black font-size14" title="Push">Push</a>
        <a href="#self" class="btnStyle3 white font-size14" title="Push">SMS</a>
      </div>
    </div>
    <div class="of_h inline-block vertical-top" style="width:60%">
      <div class="row user-phone of_h row-no-margin">
        <div style="width:24%" class="float-left">
          <p class="main-text">01  발송정보</p>
        </div>
        <div style="width:76%" class="float-left">
          <a @click="fnOpenPushTemplatePopup" class="btnStyle3_1 gray font-size13 minwidthAuto height45 width130" title="템플릿 불러오기">템플릿 불러오기</a>
          <div class="of_h mt30">
            <div style="width:18%" class="float-left">
              <p class="sub-text">발송정책 *</p>
            </div>
            <div style="width:82%">
              <input type="radio" name="send" value="ALL" class="cBox" id="pushSenderSet_ALL" v-model="sendData.pushSenderSet">
              <label for="pushSenderSet_ALL" class="payment mr30 radio mt0">ALL</label>
              <input type="radio" name="send" value="FCM" class="cBox" id="pushSenderSet_FCM" v-model="sendData.pushSenderSet">
              <label for="pushSenderSet_FCM" class="payment mr30 radio mt0">FCM</label>
              <input type="radio" name="send" value="APNS" class="cBox" id="pushSenderSet_APNS" v-model="sendData.pushSenderSet">
              <label for="pushSenderSet_APNS" class="payment radio mt0">APNS</label>
            </div>
          </div>
          <div class="of_h mt30">
            <div style="width:18%" class="float-left">
              <p class="sub-text">메시지구분 *</p>
            </div>
            <div style="width:82%">
              <input type="radio" name="Sortation" value="A" class="cBox" id="msgKind_A" v-model="sendData.msgKind">
              <label for="msgKind_A" class="payment mr30 radio mt0">광고성</label>
              <input type="radio" name="Sortation" value="I" class="cBox" id="msgKind_I" v-model="sendData.msgKind">
              <label for="msgKind_I" class="payment radio mt0">정보성</label>
            </div>
          </div>
        </div>

      </div>
      <hr class="mt50">

      <div class="row user-phone mt50 of_h row-no-margin">
        <div style="width:24%" class="float-left">
          <p class="main-text">02  메시지 내용</p>
        </div>
        <div style="width:76%" class="float-left">
          <a @click="fnOpenPushContentsPopup" class="btnStyle3_1 gray font-size13 minwidthAuto width130" title="메시지 내용입력">내용입력</a>
          <a v-if="sendData.rplcSendType!='NONE'" @click="fnOpenReplacedSenderPopup" class="btnStyle3_1 gray font-size13 minwidthAuto width130" title="대체발송 발신번호">대체발송 발신번호</a>
          <div class="of_h mt30">
            <div style="width:18%" class="float-left">
              <p class="sub-text">메시지타입 *</p>
            </div>
            <div style="width:82%">
              <input type="radio" class="cBox" id="msgType_BASE" value="BASE" v-model="sendData.msgType">
              <label for="msgType_BASE" class="payment mr30 radio mt0">기본</label>
              <input type="radio" class="cBox" id="msgType_IMAGE" value="IMAGE" v-model="sendData.msgType">
              <label for="msgType_IMAGE" class="payment radio mt0">이미지</label>
            </div>
          </div>
          <div v-if="sendData.msgType == 'IMAGE'" class="of_h mt30">
            <div style="width:18%" class="float-left">
              <p class="sub-text mt10">이미지</p>
            </div>
            <div class="of_h" style="width:82%">
              <a @click="fnOpenImageManagePopUp" class="btnStyle3_1 gray font-size13 minwidthAuto" style="width:21%" title="이미지선택">이미지선택</a>
              <input type="text" class="inputStyle float-right" style="width:77%" v-model="sendData.imgUrl">
            </div>
          </div>
          <div class="of_h mt30">
            <div style="width:18%" class="float-left">
              <p class="sub-text">대체발송</p>
            </div>
            <div>
              <input type="radio" class="cBox" id="rplcSendType_NONE" value="NONE" v-model="sendData.rplcSendType">
              <label for="rplcSendType_NONE" class="payment mr30 radio mt0">미사용</label>
              <input type="radio" class="cBox" id="rplcSendType_SMS" value="SMS" v-model="sendData.rplcSendType">
              <label for="rplcSendType_SMS" class="payment mr30 radio mt0">SMS</label>
              <input type="radio" class="cBox" id="rplcSendType_LMS" value="LMS" v-model="sendData.rplcSendType">
              <label for="rplcSendType_LMS" class="payment mr30 radio mt0">LMS</label>
              <input type="radio" class="cBox" id="rplcSendType_MMS" value="MMS" v-model="sendData.rplcSendType">
              <label for="rplcSendType_MMS" class="payment mr30 radio mt0">MMS</label>
            </div>
          </div>
        </div>

      </div>
      <hr class="mt50">

      <div class="row user-phone mt50 of_h row-no-margin">
        <div style="width:24%" class="float-left">
          <p class="main-text">03  수신자 선택</p>
        </div>
        <div style="width:76%" class="float-left">
          <div class="of_h">
            <div style="width:18%" class="height40 float-left">
              <p class="sub-text">수신자 *</p>
            </div>
            <div style="width:82%">
              <div>
                <input type="radio" class="cBox" id="cuInputType_DICT" name="cuInputType" value="DICT" v-model="sendData.cuInputType">
                <label for="cuInputType_DICT" class="payment mr30 radio mt0" @click="fnOpenDirectInputPopup">수신자 직접입력</label>
                <input type="radio" class="cBox" id="cuInputType_ADDR" name="cuInputType" value="ADDR" v-model="sendData.cuInputType">
                <label for="cuInputType_ADDR" class="payment mr30 radio mt0">주소록 검색</label>
              </div>
              <div class="mt5">
                <input type="radio" class="cBox" id="cuInputType_ALL" name="cuInputType" value="ALL" v-model="sendData.cuInputType">
                <label for="cuInputType_ALL" class="payment mr30 radio mt0">앱사용자 전체발송</label>
                <input type="radio" class="cBox" id="cuInputType_EXCEL" name="cuInputType" value="EXCEL" v-model="sendData.cuInputType">
                <label for="cuInputType_EXCEL" class="payment mr30 radio mt0">엑셀 업로드</label>
                <a href="#self" class="btnStyle3_1 gray font-size13 minwidthAuto ml20" title="샘플">샘플 <i class="far fa-arrow-to-bottom"></i></a>
              </div>
            </div>
          </div>
          <div class="of_h mt30">
            <div style="width:18%" class="float-left">
              <p class="sub-text mt10">APP_ID</p>
            </div>
            <div class="of_h" style="width:82%">
              <select class="selectStyle3 height45 font-size14" style="width:30%" v-model="sltAppId" @change="fnChangAppId">
                <option value="">APP_ID</option>
                <option v-for="aplnId in aplnIdList" :key="aplnId.aplnId" :value="aplnId.aplnId">{{aplnId.aplnId}}</option>
              </select>
              <input type="text" class="inputStyle float-right" style="width:68%" :placeholder="sltAppId != '' ? '' : 'APP ID를 입력하세요'" v-model="sendData.appId" :disabled="sendData.appId != ''">
              <p class="font-size16 mt10">수신자 : 0명</p>
              <div class="float-right" style="width:100%">
                <textarea class="textareaStyle height120 mt20" v-model="sendData.cuInfo" disabled></textarea>
              </div>
            </div>
          </div>
        </div>

      </div>
      <hr class="mt50">

      <div class="row user-phone mt50 of_h row-no-margin">
        <div style="width:24%" class="float-left">
          <p class="main-text mt5">04  발송옵션 선택</p>
        </div>
        <div style="width:76%" class="float-left">
          <div class="of_h">
            <div class="float-left" style="width:18%">
              <p class="sub-text mt10">발송시간 *</p>
            </div>
            <div class="float-left" style="width:25%">
              <input type="radio" class="cBox" id="cuInputType_IMMY" value="IMMY" v-model="sendData.senderType">
              <label for="cuInputType_IMMY" class="payment mr30 radio mt0">즉시</label>
              <input type="radio" class="cBox" id="cuInputType_RSRV" value="RSRV" v-model="sendData.senderType">
              <label for="cuInputType_RSRV" class="payment mr30 radio mt0">예약</label>
            </div>
            <div v-if="sendData.senderType == 'RSRV'" class="float-left" style="width:25%">
              <input type="text" class="datepicker inputStyle maxWidth160" title="시작날짜 입력란" v-model="sendData.rsrvDate">
            </div>
            <div v-if="sendData.senderType == 'RSRV'" class="float-right" style="width:32%">
              <input type="text" name="stime1" class="inputStyle vertical-middle" style="width:47%" v-model="sendData.rsrvHH"> : <input type="text" name="etime1" class="inputStyle vertical-middle" style="width:47%" v-model="sendData.rsrvMM">
            </div>
          </div>
          <div class="of_h mt30">
            <div style="width:18%" class="float-left">
              <p class="sub-text mt10">태그</p>
            </div>
            <div class="of_h" style="width:82%">
              <input type="text" class="inputStyle mt5 height45" style="width:100%" placeholder="캠페인 ID를 입력해주세요." v-model="sendData.campaignId">
            </div>
          </div>
        </div>
      </div>
      <div class="mt30 float-right">
        <a href="#self" class="btnStyle5 white float-left width120" title="테스트 발송" data-toggle="modal" data-target="#test">테스트 발송</a>
        <a href="#self" class="btnStyle5 red float-left ml10 width120" title="발송">발송</a>
      </div>
    </div>

    <PushTemplatePopup :pushTemplateOpen.sync="pushTemplateOpen" ref="pushTmplPopup"></PushTemplatePopup>
    <ImageManagePopUp :imgMngOpen.sync="imgMngOpen" :useCh="useCh" ref="imgMngPopup"></ImageManagePopUp>
    <PushContentsPopup :pushContsOpen.sync="pushContsOpen" :sendData="sendData"></PushContentsPopup>
    <ReplacedSenderPopup :rplcSendOpen.sync="rplcSendOpen" :rplcSendType="sendData.rplcSendType"></ReplacedSenderPopup>
    <DirectInputPopup :directInputOpen.sync="directInputOpen"></DirectInputPopup>
  </div>
  <!-- //본문 -->
</template>

<script>
import MessageApi from "@/modules/message/service/messageApi.js";
import ImageManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import PushContentsPopup from "@/modules/message/components/bp-pushContents.vue";
import ReplacedSenderPopup from "@/modules/message/components/bp-replacedSender.vue";
import DirectInputPopup from "@/modules/message/components/bp-directInput.vue";
import PushTemplatePopup from "@/modules/message/components/bp-pushTemplate.vue";

export default {
  name: "sendPushMain",
  components : {
    ImageManagePopUp,
    PushContentsPopup,
    ReplacedSenderPopup,
    DirectInputPopup,
    PushTemplatePopup
  },
  props: {
    sendData : {
      type: Object,
      require: false,
      default: function() {
        return {
          'pushSenderSet':'ALL', //발송정책(ALL, FCM, APNS)
          'msgKind':'A',  //A, I
          'msgType':'BASE',  //BASE, IMAGE
          'rplcSendType':'NONE',  //NONE, SMS, LMS, MMS
          'cuInputType':'ALL',  //DICT, ADDR, ALL, EXCEL
          'imgUrl' : '',
          'appId':'',
          'cuInfo':'',
          'senderType':'IMMY',  //발송타입 : IMMY, RSRV
          'rsrvDate':'',
          'rsrvHH':'',
          'rsrvMM':'',
          'campaignId':'',
          //내용입력 팝업
          'pushTitle':'',  //푸시제목(tmpltTitle)
          'pushContent':'',  //푸시내용(tmpltContent)
          'rcvblcNumber':'',  //수신거부
          'adtnInfo':'',  //부가정보
        }
      }
    }
  },
  data() {
    return {
      imgMngOpen : false,
      pushContsOpen : false,
      rplcSendOpen : false,
      directInputOpen : false,
      pushTemplateOpen : false,
      sltAppId : '',
      useCh : 'PUSH',
      contentsVarCnt : 0,  //default 0, 테스트를 위해 2로 둠, 추후 내용입력, 템플릿 가져오기 하면 set
      aplnIdList: {},
    }
  },
  mounted() {
    this.fnGetAppId();
  },
  methods: {
    //템플릿 정보 Set
    fnSetTemplateInfo(templateInfo){
      this.sendData.msgKind = templateInfo.msgKind;  //메세지구분
      this.sendData.msgType = templateInfo.msgType;  //메세지타입
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
      var params = {
        'corpId':'TEST_CORP',
        'projectId':'TEST_PROJECT'
      };
      MessageApi.selectAppIdList(params).then(response => {
        var result = response.data;
        if(result.success) {
          console.log(result);
          this.aplnIdList = result.data;
        } else {
          alert(result.message);
        }
      });
    },
    fnOpenImageManagePopUp(){
      this.$refs.imgMngPopup.fnSearch();
      this.imgMngOpen = !this.imgMngOpen;
    },
    fnOpenPushContentsPopup(){
      this.pushContsOpen = !this.pushContsOpen;
    },
    fnOpenReplacedSenderPopup(){
      this.rplcSendOpen = !this.rplcSendOpen;
    },
    fnOpenDirectInputPopup(){
      this.directInputOpen = !this.directInputOpen;
    },
    fnOpenPushTemplatePopup(){
      this.$refs.pushTmplPopup.fnSearch();
      this.pushTemplateOpen = !this.pushTemplateOpen;
    },
    fnSetImageInfo(imgInfo) {
      alert(imgInfo.originFileName);
    }
  }
}
</script>