<template>
  <div>
    <div class="contentHeader">
      <h2>SMS 발송</h2>
      <!-- <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="SMS 발송 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
    </div>
    <!-- 본문 -->
    <div class="row">
      <div class="phone3 inline-block" style="width:30%">
        <div class="phoneFixed">
          <!-- phoneWrap -->
          <div class="phoneWrap">
            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
            <div class="phoneTextWrap">
              <div v-if="sendData.senderType == 'MMS'" class="phoneText2 mb10">
                <p v-if="$gfnCommonUtils.isEmpty(sendData.smsTitle)">메시지 제목</p>
                <p v-else>{{sendData.smsTitle}}</p>
              </div>
              <div class="phoneText1 scroll-y">
                <p v-if="$gfnCommonUtils.isEmpty(sendData.smsContent) && (sendData.msgKind != 'A' || $gfnCommonUtils.isEmpty(sendData.rcvblcNumber))" class="font-size14 color4 mt10">메시지 내용</p>
                <p v-else class="font-size14 color4 mt10">
                  <span v-html="$gfnCommonUtils.newLineToBr(sendData.smsContent)"></span>
                  <br v-if="!$gfnCommonUtils.isEmpty(sendData.smsContent)"/>
                  <span v-if="sendData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(sendData.rcvblcNumber)">
                    {{sendData.rcvblcNumber}}
                  </span>
                </p>
              </div>
            </div>
          </div>
          <!-- //phoneWrap -->
        </div>
      </div>
      <div class="of_h inline-block vertical-top consoleCon" style="width:60%">
        <div class="of_h user-phone">
          <div class="float-left" style="width:24%">
            <h4>01  발송정보</h4>
          </div>
          <div class="float-left" style="width:76%">
            <a @click="fnOpenSmsTemplatePopup" class="btnStyle1 backLightGray" title="템플릿 불러오기">템플릿 불러오기</a>
            <div class="of_h consolMarginTop">
              <div style="width:18%" class="float-left">
                <h5>발송유형</h5>
              </div>
              <div>
                <input type="radio" name="senderType" value="SMS" id="senderType_SMS" v-model="sendData.senderType">
                <label for="senderType_SMS" class="mr30">SMS</label>
                <input type="radio" name="senderType" value="MMS" id="senderType_MMS" v-model="sendData.senderType">
                <label for="senderType_MMS">MMS</label>
              </div>
            </div>
            <div class="of_h">
              <div style="width:18%" class="float-left">
                <h5>메시지구분 *</h5>
              </div>
              <div>
                <input type="radio" name="msgKind" value="A" id="msgKind_A" v-model="sendData.msgKind">
                <label for="msgKind_A" class="mr30">광고성</label>
                <input type="radio" name="msgKind" value="I" id="msgKind_I" v-model="sendData.msgKind">
                <label for="msgKind_I">정보성</label>

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
            <a @click="fnOpenSmsContentsPopup" class="btnStyle1 backLightGray" title="메시지 내용입력">내용입력</a>
            <div v-if="sendData.senderType == 'MMS'" class="of_h consolMarginTop">
              <div style="width:18%" class="float-left">
                <h5>이미지</h5>
              </div>
              <div class="of_h" style="width:82%">
                <div class="float-left" style="width:24%"><a @click="fnOpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a></div>
                <ul v-for="imgIdx in imgLimitSize" :key="imgIdx" class="float-right attachList" style="width:74%; padding:5px 15px; height:30px;">
                  <li v-if="sendData.imgInfoList.length > imgIdx-1">
                    <a @click="fnDelImg(idx)">{{fnSubString(sendData.imgInfoList[imgIdx-1].imgUrl, 0, 35)}} <i class="fal fa-times"></i></a>
                  </li>
                  <li v-else>
                    <a></a>
                  </li>
                </ul>
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
                  <input type="radio" id="cuInputType_DICT" name="cuInputType" value="DICT" v-model="sendData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType">
                  <label for="cuInputType_DICT" class="mr30">수신자 직접입력</label>
                  <input type="radio" id="cuInputType_ADDR" name="cuInputType" value="ADDR" v-model="sendData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType">
                  <label for="cuInputType_ADDR" class="mr30">주소록 검색</label>
                  <input type="radio" id="cuInputType_EXCEL" name="cuInputType" value="EXCEL" v-model="sendData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType">
                  <label for="cuInputType_EXCEL" class="mr10">엑셀 업로드</label>
                  <a @click="fnExcelTmplteDownLoad" class="btnStyle1 backLightGray" title="샘플">샘플 <i class="far fa-arrow-to-bottom"></i></a>
                  <input ref="excelFile" type="file" style="display:none;">
                </div>
              </div>
            </div>
            <div class="of_h">
              <div class="of_h float-right" style="width:82%">
                <p>수신자 : {{recvCnt}}명</p>
                <div class="float-right consolMarginTop" style="width:100%">
                  <textarea class="textareaStyle height120" v-model="sendData.cuInfo" disabled></textarea>
                </div>
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
                <Calendar @update-date="fnUpdateRsrvDate" calendarId="rsrvDate" classProps="datepicker inputStyle" :initDate="sendData.rsrvDate"></Calendar>
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
                <input type="text" class="inputStyle" style="width:100%" placeholder="캠페인 ID를 입력해주세요." v-model="sendData.campaignId">
              </div>
            </div>
          </div>
        </div>
        <div class="mt20 float-right">
          <a href="#self" class="btnStyle2 float-left" title="테스트 발송" data-toggle="modal" data-target="#test">테스트 발송</a>
          <a @click="fnSendSmsMessage('N')" class="btnStyle2 backRed float-left ml10" title="발송">발송</a>
        </div>
      </div>
    </div>

    <SmsTemplatePopup :smsTemplateOpen.sync="smsTemplateOpen" ref="smsTmplPopup"></SmsTemplatePopup>
    <SmsContentsPopup :smsContsOpen.sync="smsContsOpen" :sendData="sendData"></SmsContentsPopup>
    <ImageManagePopUp @img-callback="fnCallbackImgInfo" :imgMngOpen.sync="imgMngOpen" :useCh="sendData.senderType" ref="imgMngPopup"></ImageManagePopUp>
    <DirectInputPopup :directInputOpen.sync="directInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid" :recvInfoLst="sendData.recvInfoLst"></DirectInputPopup>
    <AddressInputPopup :addressInputOpen.sync="addressInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid"></AddressInputPopup>

  </div>
</template>

<script>
import SmsTemplatePopup from "@/modules/message/components/bp-smsTemplate.vue";
import SmsContentsPopup from "@/modules/message/components/bp-smsContents.vue";
import ImageManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import DirectInputPopup from "@/modules/message/components/bp-directInput.vue";
import AddressInputPopup from "@/modules/message/components/bp-addressInput.vue";
import Calendar from "@/components/Calendar.vue";

import messageApi from "@/modules/message/service/messageApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  name: "sendSmsMain",
  components : {
    SmsTemplatePopup,
    SmsContentsPopup,
    ImageManagePopUp,
    DirectInputPopup,
    Calendar,
    AddressInputPopup
  },
  props: {
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return 'SMS 발송';
      }
    },
  },
  data() {
    return {
      smsTemplateOpen : false,
      smsContsOpen : false,
      imgMngOpen : false,
      directInputOpen : false,
      addressInputOpen : false,
      imgLimitSize : 2,
      recvCnt : 0,  //수신자명수
      inProgress: false,
      sendData : {
        requiredCuid : false,  //app 로그인 ID 필수여부
        requiredCuPhone : true,  //수신자 폰번호 필수여부
        senderType: 'SMS',  //SMS, MMS
        msgKind: 'A',
        cuInputType:'DICT',  //DICT, ADDR, EXCEL
        smsTitle:'',  //제목
        smsContent:'',  //내용
        rcvblcNumber:'',  //수신거부
        cuInfo:'',
        rsrvSendYn:'N',  //예약발송여부
        rsrvDate:this.$gfnCommonUtils.getCurretDate(),
        rsrvHH:'00',
        rsrvMM:'00',
        campaignId:'',
        imgInfoList: [],
        recvInfoLst: [],  //수신자정보
        contsVarNms: [], //메세지 내용 변수명
        testRecvInfoLst: [],  //테스트 수신자정보
      }
    }
  },
  methods: {
    //푸시 메시지 발송 처리
    async fnSendSmsMessage(testSendYn){
      if(this.inProgress){
        confirm.fnAlert(this.componentsTitle, 'SMS 메시지 발송 처리중입니다.');
        return;
      }

      //유효성 체크
      //if(this.fnValidSendMsgData(testSendYn) == false) return;

      //발송처리
      let params = Object.assign({}, this.sendData);
      params.testSendYn = testSendYn;

      if(testSendYn == 'Y'){
        params.recvInfoLst = Object.assign([], this.sendData.testRecvInfoLst);
        params.cuInputType = 'DICT';
        params.rsrvSendYn = 'N';
      }

      let fd = new FormData();
      fd.append('paramString', JSON.stringify(params));
      if(this.sendData.cuInputType == 'EXCEL'){
        fd.append('excelFile', this.$refs.excelFile.files[0]);
      }

      this.inProgress = true;
      const vm = this;

      if(this.sendData.senderType == 'SMS'){
        await messageApi.sendSmsMessage(fd).then(response => this.fnSendCallBack(response, testSendYn))
        .catch(function () { vm.inProgress = false; });
      } else if(this.sendData.senderType == 'MMS'){
        await messageApi.sendMmsMessage(fd).then(response => this.fnSendCallBack(response, testSendYn))
        .catch(function () { vm.inProgress = false; });
      }
      
    },
    fnSendCallBack(response, testSendYn){
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
          if(!this.$gfnCommonUtils.isEmpty(result.data.feeMsg)){
            eventBus.$on('callbackEventBus', this.fnAlertFeeMsgCallBack);
            confirm.fnAlert(this.componentsTitle, result.data.feeMsg, 'ALERT', result);
          } else {
            this.fnAlertFeeMsgCallBack(result);
          }
        }
      } else {
        confirm.fnAlert(this.componentsTitle, result.message);
      }
    },
    fnAlertFeeMsgCallBack(result){
      if(this.$gfnCommonUtils.isEmpty(result.message)){
        confirm.fnAlert(this.componentsTitle, '발송 요청 처리 되었습니다.');
      } else {
        confirm.fnAlert(this.componentsTitle, result.message);
      }
    },
    fnClickCuInputType(e){
      if(this.sendData.cuInputType == e.target.value){
        this.fnChgCuInputType('N');
      }
    },
    //수신자 입력 타입 변경시
    fnChgCuInputType(chgYn){
      if(this.$gfnCommonUtils.defaultIfEmpty(chgYn, 'Y') == 'Y'){
        this.fnCallbackRecvInfoLst(null);  //수신자 입력 타입 변경시 수신자 정보 초기화
      }
      if(!this.sendData.smsContent){
        confirm.fnAlert(this.componentsTitle, '메시지 내용을 먼저 입력해주세요.');
        return;
      }
      if(this.fnSetContsVarNms() == false){
        return;
      }

      if(this.sendData.cuInputType == 'DICT'){  //직접입력
        //수신자 직접입력 팝업 호출
        this.directInputOpen = !this.directInputOpen;
      } else if(this.sendData.cuInputType == 'ADDR'){  //주소록
        //주소록 검색 팝업 호출
        this.addressInputOpen = !this.addressInputOpen;
      } else if(this.sendData.cuInputType == 'EXCEL'){  //엑셀
        //엑셀파일찾기 호출
        this.$refs.excelFile.click();
      }
    },
    fnSetContsVarNms(){
      const rsvNmSet = new Set(['phone']);
      const conts = this.sendData.smsContent;
      let varNms = [];
      let containRsvNm = false;
      conts.replace(/\{\{(\w+)\}\}/g, function($0, $1) {
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
    fnCallbackImgInfo(imgInfo){
      if(this.fnImgLimitSize() == false) return;
      let temp = {
        imgUrl: imgInfo.chImgUrl,
        fileId: imgInfo.fileId
      };
      this.sendData.imgInfoList.push(temp);
      this.fnDelDuplImgInfo();
    },
    fnDelDuplImgInfo(){
      const vm = this;
      this.sendData.imgInfoList = this.sendData.imgInfoList.filter(function(item, i){
        return (
          vm.sendData.imgInfoList.findIndex((item2) => {
            return item.fileId === item2.fileId;
          }) === i
        );
      });
    },
    fnOpenImageManagePopUp(){
      if(this.fnImgLimitSize() == false) return;
      this.$refs.imgMngPopup.fnSearch();
      this.imgMngOpen = !this.imgMngOpen;
    },
    fnImgLimitSize(){
      if(this.sendData.imgInfoList != null && this.sendData.imgInfoList.length >= this.imgLimitSize){
        confirm.fnAlert(this.componentsTitle, '이미지는 최대 2개까지 등록 가능합니다.');
        return false;
      }
    },
    //템플릿 정보 Set
    fnSetTemplateInfo(templateInfo){
      this.sendData.senderType = templateInfo.senderType;  //발송유형
      this.sendData.msgKind = templateInfo.msgKind;  //메시지구분
      this.sendData.smsContent = templateInfo.tmpltContent;  //메시지내용

      if(this.sendData.senderType != 'SMS'){
        this.sendData.smsTitle = templateInfo.tmpltTitle;  //메시지제목
        //이미지정보
        if(templateInfo.imgInfoList != null && templateInfo.imgInfoList.length > 0){
          this.sendData.imgInfoList = templateInfo.imgInfoList;
        } else {
          this.sendData.imgInfoList = [];
        }
      } else {
        this.sendData.smsTitle = '';  //메시지제목
      }
      
      //수신거부
      if(this.sendData.msgKind == 'A'){
        this.sendData.rcvblcNumber = templateInfo.rcvblcNumber;
      } else {
        this.sendData.rcvblcNumber = '';
      }
    },
    //내용입력 callback
    fnSetSmsInfo(data){
      if(this.sendData.smsContent != data.smsContent){
        this.fnCallbackRecvInfoLst(null);  //수신자 정보 초기화
      }
      this.sendData.smsTitle = data.smsTitle;
      this.sendData.smsContent = data.smsContent;
      this.sendData.rcvblcNumber = data.rcvblcNumber;
    },
    fnDelImg(idx){
      this.sendData.imgInfoList.splice(idx, 1);
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
    fnUpdateRsrvDate(sltDate){
      this.sendData.rsrvDate = sltDate;
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
    fnOpenSmsTemplatePopup(){
      this.$refs.smsTmplPopup.fnSearch();
      this.smsTemplateOpen = !this.smsTemplateOpen;
    },
    fnOpenSmsContentsPopup(){
      this.smsContsOpen = !this.smsContsOpen;
    },
    //SMS 템플릿 엑셀 다운로드
    async fnExcelTmplteDownLoad(){
      if(this.fnSetContsVarNms() == false) return;
      const params = {
        contsVarNms : this.sendData.contsVarNms
      };
      await messageApi.excelDownSendSmsRecvTmplt(params);
    },
  }
}
</script>