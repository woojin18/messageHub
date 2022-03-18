<template>
  <div>
    <div class="contentHeader">
      <h2>발송 > 친구톡 <span v-if="nightSendYn == 'N'" class="ml20 font-size12 color1">야간 메시지 발송 제한으로 {{nightSendSthh}}:{{nightSendStmm}} ~ 다음날 {{nightSendEdhh}}:{{nightSendEdmm}} 까지 메시지 발송을 할 수 없습니다.<i class="fas fa-question-circle toolTip ml5"><span class="toolTipText" style="width:260px">야간 메시지 발송 제한 해제는 [관리자 콘솔] 프로젝트 기본정보에서 세팅 할 수 있습니다.</span></i></span></h2>
      <!-- <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="친구톡 발송 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
    </div>

    <!-- 본문 -->
    <div class="row">
      <div class="phone3 inline-block" style="width:30%">
        <div class="phoneFixed">
          <!-- phoneWrap -->
          <!-- 친구톡 -->
          <div v-if="previewMessageType == 'FRIENDTALK'" class="phoneWrap">
            <img src="@/assets/images/common/phoneMockup2_1.svg" alt="프리 템플릿">
            <div class="phoneTextWrap4 scroll-yc">
              <p v-if="sendData.msgKind == 'A'">[광고]</p>
              <div class="mt5">
                <div class="text-sub-wrap">
                  <!-- <p class="text-sub scroll-y"> -->
                    <div v-if="!$gfnCommonUtils.isEmpty(sendData.imgUrl)" class="phoneText2 mt10 text-center simulatorImg"
                      :style="'padding:65px;background-image: url('+sendData.imgUrl+');'">
                    </div>
                    <br v-if="$gfnCommonUtils.isEmpty(sendData.imgUrl)"/>
                    <span><pre>{{sendData.frndTalkContent}}</pre></span>
                    <div v-for="(buttonInfo, idx) in sendData.buttonList" :key="idx">
                      <a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.name)" class="btnStyle1 backLightGray">{{buttonInfo.name}}</a>
                    </div>
                  <!-- </p> -->
                </div>
                <p class="text-sub_2" v-if="sendData.msgKind == 'A'">수신거부: 홈 > 친구차단</p>
              </div>
            </div>
          </div>
          <!-- //친구톡 -->
          <!-- XMS -->
          <div v-if="previewMessageType == 'RPLC'" class="phoneWrap">
            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
            <div class="phoneTextWrap">
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
          <!-- //phoneWrap -->
          <div class="phone_04_btn">
            <a @click="fnChgPreviewMessageType('FRIENDTALK')" :class="'btnStyle1 '+(previewMessageType == 'FRIENDTALK' ? 'backBlack' : 'backWhite')" title="Push">친구톡</a>
            <a v-if="sendData.rplcSendType != 'NONE'" @click="fnChgPreviewMessageType('RPLC')" :class="'btnStyle1 '+(previewMessageType != 'FRIENDTALK' ? 'backBlack' : 'backWhite')" title="Push">{{sendData.rplcSendType}}</a>
          </div>
        </div>
      </div>
      <div class="of_h inline-block vertical-top consoleCon" style="width:60%">
        <div class="of_h user-phone">
          <div class="float-left" style="width:24%">
            <h4>01  발송정보</h4>
          </div>
          <div class="float-left" style="width:76%">
            <a @click="fnOpenFrndTalkTemplatePopup" class="btnStyle1 backLightGray" title="템플릿 불러오기" activity="READ">템플릿 불러오기</a>
            <div class="of_h consolMarginTop">
              <div style="width:20%" class="float-left">
                <h5>카카오 채널 *</h5>
              </div>
              <div style="width:80%">
                <select name="userConsole_sub0203_1" class="selectStyle2" style="width:42%" v-model="sendData.senderKey">
                  <option value="" selected>선택해주세요.</option>
                  <option v-for="senderKeyInfo in senderKeyList" :key="senderKeyInfo.senderKey" :value="senderKeyInfo.senderKey">{{senderKeyInfo.kkoChId}}</option>
                </select>
              </div>
            </div>
            <div class="of_h consolMarginTop">
              <div style="width:20%" class="float-left">
                <h5>메시지구분 *</h5>
              </div>
              <div style="width:80%">
                <input type="radio" name="msgKind" value="I" id="msgKind_I" v-model="sendData.msgKind">
                <label for="msgKind_I">정보성</label>
                <input type="radio" name="msgKind" value="A" id="msgKind_A" v-model="sendData.msgKind">
                <label for="msgKind_A" class="mr30">
                  광고성
                  <span style="font-size: 8px;color: red;">(광고 메시지는 20시~8시 발송 시 실패 처리 됩니다.)</span>
                </label>
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
            <a @click="fnOpenFrndTalkContentsPopup" :class="$gfnCommonUtils.isEmpty(sendData.frndTalkContent) ? 'btnStyle1 backLightGray mr5' : 'btnStyle1 backWhite mr5'" title="메시지 내용입력" activity="READ">내용입력</a>
            <a v-if="sendData.rplcSendType!='NONE'" @click="fnOpenReplacedSenderPopup" :class="fnIsEmptyObj(sendData.fbInfo.callback) ? 'btnStyle1 backLightGray' : 'btnStyle1 backWhite'" title="대체발송 내용입력" activity="READ">대체발송 내용입력</a>
            <!--
            <div class="of_h consolMarginTop">
              <div style="width:20%" class="float-left">
                <h5>메시지타입 *</h5>
              </div>
              <div style="width:80%">
                <input type="radio" name="content" value="txt" id="txt" checked=""> <label for="txt" class="mr30">기본</label>
                <input type="radio" name="content" value="img" id="img"> <label for="img">이미지</label>
              </div>
            </div>
            -->
            <div class="of_h consolMarginTop">
              <div style="width:20%" class="float-left">
                <h5>이미지</h5>
              </div>
              <div class="of_h" style="width:80%">
                <div class="float-left" style="width:24%"><a @click="fnOpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지 선택">이미지선택</a></div>
                <ul class="float-right attachList" style="width:73%; padding:5px 15px; height: 30px;">
                  <li v-if="sendData.imgUrl">
                    <a @click="fnDelImg">{{fnSubString(sendData.imgUrl, 0, 40)}} <i class="fal fa-times"></i></a>
                  </li>
                </ul>
              </div>
            </div>

            <div class="of_h consolMarginTop">
              <div style="width:20%" class="float-left">
                <h5>이미지 링크</h5>
              </div>
              <div class="of_h" style="width:80%">
                <!-- #10478 - 사용자콘솔 내 친구톡 발송(이미지링크) -->
                <!-- <input type="text" class="inputStyle" style="width:100%" placeholder="http://" v-model="sendData.imgLink" maxlength="200"> -->
                <input type="text" class="inputStyle" style="width:100%" v-model="sendData.imgLink" maxlength="200" placeholder="[http://, https://]를 포함한 URL" :disabled="this.$gfnCommonUtils.isEmpty(sendData.fileId)">
              </div>
            </div>

            <div class="of_h consolMarginTop">
              <div style="width:20%" class="float-left">
                <h5 class="inline-block mr10">버튼</h5>
              </div>
              <div class="of_h" style="width:80%">
                <a @click="fnAddButton" class="btnStyle1 backBlack">추가 +</a>
              </div>
              <div  class="of_h consolMarginTop">
                <table class="table_skin1" style="border-top: 1px solid #D5D5D5; border-bottom: 1px solid #D5D5D5;">
                  <colgroup>
                    <col style="width:110px">
                    <col style="width:17%">
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
                  <tbody class="of_h">
                    <template v-for="(buttonInfo, idx) in sendData.buttonList">
                    <tr :key="idx">
                      <td class="text-left" :rowspan="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL' ? '2' : '1'">
                        <select class="float-left selectStyle2" style="width:100%" v-model="buttonInfo.linkType" @change="fnChgBtnType(idx)">
                          <option v-for="bottonType in bottonTypeList" :key="bottonType.linkType" :value="bottonType.linkType">{{bottonType.name}}</option>
                        </select>
                      </td>
                      <td class="text-center" :rowspan="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL' ? '2' : '1'">
                        <input type="text" class="inputStyle float-left" v-model="buttonInfo.name" maxlength="20">
                      </td>
                      <td v-if="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL'" class="text-left of_h">
                        <div v-if="buttonInfo.linkType == 'WL'">
                          <h6 class="font-normal float-left" style="width:20%">Mobile</h6>
                          <input type="text" class="inputStyle float-right" style="width:78%;" v-model="buttonInfo['linkMo']" maxlength="200"  placeholder="[http://, https://]포함한 URL">
                        </div>
                        <div v-if="buttonInfo.linkType == 'AL'">
                          <h6 class="font-normal float-left" style="width:20%">Android</h6>
                          <input type="text" class="inputStyle float-right" style="width:78%;" v-model="buttonInfo['linkAnd']" maxlength="200" placeholder="[http://, https://]포함한 URL">
                        </div>
                      </td>
                      <td v-else>
                      </td>
                      <td class="text-center end" :rowspan="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL' ? '2' : '1'">
                        <a
													v-if="buttonInfo.linkType === 'WL'"
													class="btnStyle1 backBlack" 
													title="단축 URL+" 
													data-toggle="modal" 
													data-target="#shortened_URL"
													@click="selIdx = idx"
												>단축 URL+</a> 
                        <a @click="fnDelButton(idx)" class="btnStyle1 backLightGray" style="padding:0px 15px;">삭제</a>
                      </td>
                    </tr>
                    <tr v-if="buttonInfo.linkType == 'WL' || buttonInfo.linkType == 'AL'" :key="idx+'_sub'">
                      <td class="text-left of_h">
                        <div v-if="buttonInfo.linkType == 'WL'">
                          <h6 class="font-normal float-left" style="width:20%">PC</h6>
                          <input type="text" class="inputStyle float-right" style="width:78%;" v-model="buttonInfo['linkPc']" maxlength="200" placeholder="[http://, https://]포함한 URL">
                        </div>
                        <div v-if="buttonInfo.linkType == 'AL'">
                          <h6 class="font-normal float-left" style="width:20%">IOS</h6>
                          <input type="text" class="inputStyle float-right" style="width:78%;" v-model="buttonInfo['linkIos']" maxlength="200" placeholder="[http://, https://]포함한 URL">
                        </div>
                      </td>
                    </tr>
                  </template>
                  </tbody>
                </table>
              </div>
            </div>

            <div class="of_h consolMarginTop">
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
            <div v-if="!$gfnCommonUtils.isEmpty(sendData.fbInfo.callback)" class="of_h consolMarginTop">
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
              <div style="width:18%" class="height40 float-left">
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
                  <a @click="fnExcelTmplteDownLoad" class="btnStyle1 backLightGray" title="샘플" activity="READ">샘플 <i class="far fa-arrow-to-bottom"></i></a>
                  <input ref="excelFile" type="file" style="display:none;" @change="fnReadFile" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                </div>
              </div>
            </div>
            <div class="of_h">
              <div class="of_h float-right" style="width:82%">
                <p>수신자 : {{recvCnt}}명<a @click="fnRemoveRecvInfo();" class="btnStyle1 small backWhite ml10" title="수신자 삭제">수신자 삭제</a></p>
                <div class="float-right" style="width:100%">
                  <textarea class="textareaStyle height120 consolMarginTop" v-model="sendData.cuInfo" disabled></textarea>
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
        <div class="mt30 float-right">
          <a @click="fnOpenTestSendInputPopup" class="btnStyle2 float-left" title="테스트 발송" activity="SAVE">테스트 발송</a>
          <a @click="fnSendFrndTalkMessage('N')" class="btnStyle2 backRed float-left ml10" title="발송" activity="SAVE">발송</a>
        </div>
      </div>
    </div>

    <FrndTalkTemplatePopup :frndTalkTemplateOpen.sync="frndTalkTemplateOpen" ref="frndTalkTmplPopup"></FrndTalkTemplatePopup>
    <FrndTalkContentsPopup :frndTalkContsOpen.sync="frndTalkContsOpen" :sendData="sendData" ref="frndTalkContPopup"></FrndTalkContentsPopup>
    <ImageManagePopUp @img-callback="fnCallbackImgInfo" :imgMngOpen.sync="imgMngOpen" :useCh="sendData.ch" ref="imgMngPopup"></ImageManagePopUp>
    <ReplacedSenderPopup :rplcSendOpen.sync="rplcSendOpen" ref="rplcSendPopup"></ReplacedSenderPopup>
    <DirectInputPopup :directInputOpen.sync="directInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid" :recvInfoLst="sendData.recvInfoLst"></DirectInputPopup>
    <AddressInputPopup :addressInputOpen.sync="addressInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid"></AddressInputPopup>
    <TestSendInputPopup :testSendInputOpen.sync="testSendInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid" ref="testSendInputPopup"></TestSendInputPopup>

    <shortenedUrlListPopup @btnSelect="btnSelect" />
    <shortenedUrlAddPopup/>
    <nightSendLimitPopup :nightSendLimitY.sync="nightSendLimitYn" :nightSendSthh="this.nightSendSthh" :nightSendStmm="this.nightSendStmm" :nightSendEdhh="this.nightSendEdhh" :nightSendEdmm="this.nightSendEdmm"/>
  </div>
</template>

<script>
import FrndTalkTemplatePopup from "@/modules/message/components/bp-frndTalkTemplate.vue";
import FrndTalkContentsPopup from "@/modules/message/components/bp-frndTalkContents.vue";
import ImageManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import ReplacedSenderPopup from "@/modules/message/components/bp-replacedSender.vue";
import DirectInputPopup from "@/modules/message/components/bp-directInput.vue";
import AddressInputPopup from "@/modules/message/components/bp-addressInput.vue";
import Calendar from "@/components/Calendar.vue";
import TestSendInputPopup from "@/modules/message/components/bc-testSendInput.vue";
import XLSX from 'xlsx';
import nightSendLimitPopup from "@/modules/message/components/bp-nightSendLimit.vue";

import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import messageApi from "@/modules/message/service/messageApi.js";
import templateApi from "@/modules/template/service/templateApi.js";

import shortenedUrlListPopup from "@/modules/urlInfo/components/shortenedUrlListPopup"
import shortenedUrlAddPopup from "@/modules/urlInfo/components/shortenedUrlAddPopup"

export default {
  name: 'sendFrndTalkMain',
  components : {
    FrndTalkTemplatePopup,
    FrndTalkContentsPopup,
    ImageManagePopUp,
    ReplacedSenderPopup,
    DirectInputPopup,
    AddressInputPopup,
    Calendar,
    TestSendInputPopup,
    shortenedUrlListPopup,
    shortenedUrlAddPopup,
    nightSendLimitPopup
  },
  props: {
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '친구톡 발송';
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
      frndTalkTemplateOpen: false,
      frndTalkContsOpen: false,
      imgMngOpen: false,
      rplcSendOpen: false,
      directInputOpen: false,
      addressInputOpen: false,
      testSendInputOpen: false,
      buttonLimitSize: 5,
      recvCnt : 0,  //수신자명수
      inProgress: false,
      previewMessageType: 'FRIENDTALK',  //FRIENDTALK, RPLC
      bottonTypeList : [
        {linkType:'WL', name:'웹 링크'},
        {linkType:'AL', name:'앱 링크'},
        {linkType:'BK', name:'봇 키워드'},
        {linkType:'MD', name:'메시지전달'}
      ],
      senderKeyType: 'NOMAL',
      senderKeyList: [],
      tempFile: [],
      beforeCuInputType: 'DICT',
      monthAmount : 0,
      monSenderLimitAmout : '없음',
      feeType : false,
      sendData : {
        chGrp: 'KKO',
        ch: 'FRIENDTALK',
        requiredCuid : false,
        requiredCuPhone : true,
        senderKey: '',
        msgKind: 'A',
        frndTalkContent: '',
        imgUrl: '',
        fileId: '',
        wideImgYn: 'N',
        imgLink: '',
        rplcSendType: 'NONE',  //NONE, SMS, LMS, MMS
        cuInputType:'',  //DICT, ADDR, EXCEL
        cuInfo: '',
        rsrvSendYn: 'N',  //예약발송여부
        rsrvDate: this.$gfnCommonUtils.getCurretDate(),
        rsrvHH: '00',
        rsrvMM: '00',
        campaignId: '',
        contsVarNms: [], //메세지 내용 변수명
        recvInfoLst: [],  //수신자정보
        fbInfo: {},  //대체발송정보
        buttonList : [],
        testRecvInfoLst: [],  //테스트 수신자정보
        excelLimitRow: 0
      },
      selIdx : null,
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
    // await this.fnAddButton();
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
      // this.fnAddButton();
    },
    fnExistApiKey(){
      let params = {};
      messageApi.selectApiKey(params).then(response =>{
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
    //발송 정보 유효성 체크
    fnValidSendMsgData(testSendYn){
      const {
        senderKey, msgKind, frndTalkContent,
        fileId, imgUrl, imgLink,
        buttonList, fbInfo, testRecvInfoLst,
        cuInputType, recvInfoLst, rplcSendType,
      } = this.sendData

      if(this.fnSetContsVarNms() == false){
        return false;
      }
      if(!senderKey){
        confirm.fnAlert(this.componentsTitle, '카카오 채널을 선택해주세요.');
        return false;
      }
      if(!msgKind){
        confirm.fnAlert(this.componentsTitle, '메시지구분을 선택해주세요.');
        return false;
      }
      if(!frndTalkContent){
        confirm.fnAlert(this.componentsTitle, '메시지 내용을 입력해주세요.');
        return false;
      }

      if(!frndTalkContent){
        confirm.fnAlert(this.componentsTitle, '메시지 내용을 입력해주세요.');
        return false;
      }
      if(this.fnGetLimitLength() < frndTalkContent.length){
        const alertMsg = '메시지 내용은 '+this.fnGetLimitLength()+'자를 넘지 않아야됩니다.\n(현재 : '+frndTalkContent.length+'자)';
        confirm.fnAlert(this.componentsTitle, alertMsg);
        return false;
      }
      if(!this.$gfnCommonUtils.isEmpty(fileId)){
        if(!imgUrl){
          confirm.fnAlert(this.componentsTitle, '잘못된 이미지 정보입니다.');
          return false;
        }
        // if(!imgLink){
        //   confirm.fnAlert(this.componentsTitle, '이미지 파일 선택시 이미지 링크 URL은 필수입니다.');
        //   return false;
        // }
        if(!this.$gfnCommonUtils.isEmpty(imgLink) && (this.$gfnCommonUtils.isUrl(imgLink) == false)){
          confirm.fnAlert(this.componentsTitle, '유효하지 않은 이미지 링크 URL 입니다.');
          return false;
        }
      }
      if(buttonList.length > 0){
        // var buttonList = buttonList;
        var btnLength = buttonList.length;
        for(var i = 0; i < btnLength; i++){
          if(this.$gfnCommonUtils.isEmpty(buttonList[i].name)){
            confirm.fnAlert(this.componentsTitle, '버튼이름을 입력해주세요.');
            return false;
          }
          // 웹링크
          if(buttonList[i].linkType == "WL") {
            // 모바일 버튼링크만 있어도 전송 가능 (PC링크만 입력하면 전송 실패)
            if(this.$gfnCommonUtils.isEmpty(buttonList[i].linkMo)){
              confirm.fnAlert(this.componentsTitle, '버튼링크를 입력해주세요.');
              return false;
            }
            
            if(!this.$gfnCommonUtils.isEmpty(buttonList[i].linkMo) && !this.$gfnCommonUtils.isUrl(buttonList[i].linkMo)){
              confirm.fnAlert(this.componentsTitle, '유효하지 않은 버튼 링크 URL 입니다.\n[http://, https://]를 포함한 URL을 입력해주세요.');
              return false;
            }

            if(!this.$gfnCommonUtils.isEmpty(buttonList[i].linkPc) && !this.$gfnCommonUtils.isUrl(buttonList[i].linkPc)){
              confirm.fnAlert(this.componentsTitle, '유효하지 않은 버튼 링크 URL 입니다.\n[http://, https://]를 포함한 URL을 입력해주세요.');
              return false;
            }
          }
          // 앱링크
          if(buttonList[i].linkType == "AL") {
            if(this.$gfnCommonUtils.isEmpty(buttonList[i].linkAnd) || (this.$gfnCommonUtils.isEmpty(buttonList[i].linkIos))){
              confirm.fnAlert(this.componentsTitle, '버튼링크를 입력해주세요.');
              return false;
            }
            if(this.$gfnCommonUtils.isUrl(buttonList[i].linkAnd) && this.$gfnCommonUtils.isUrl(buttonList[i].linkIos)) {
              //
            } else {
              confirm.fnAlert(this.componentsTitle, '유효하지 않은 버튼 링크 URL 입니다.\n[http://, https://]를 포함한 URL을 입력해주세요.');
              return false;
            }
          }
        }
      }
      // 메시지 구분 : 광고성, 대체발송 : 미사용이 아니고, 수신거부번호 미입력 시
      if(msgKind && msgKind === 'A' && rplcSendType !== 'NONE'){
        if(!fbInfo.rcvblcNumber || fbInfo.rcvblcNumber === '') {
          confirm.fnAlert(this.componentsTitle, '대체발송 광고성 문자는 수신거부번호를 필수로 입력해야 합니다.');
          return false;
        }
      }

      if(testSendYn == 'Y'){
        if(!testRecvInfoLst == null || testRecvInfoLst.length == 0){
          confirm.fnAlert(this.componentsTitle, '테스트 수신자 정보를 입력해주세요.');
          return false;
        }
      } else {
        if(cuInputType == 'DICT' || cuInputType == 'ADDR'){
          if(!recvInfoLst == null || recvInfoLst.length == 0){
            confirm.fnAlert(this.componentsTitle, '수신자 정보를 입력해주세요.');
            return false;
          }
        }
        if(cuInputType == 'EXCEL'){
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
        if(rplcSendType != 'NONE'){
          if(!fbInfo.callback){
            confirm.fnAlert(this.componentsTitle, '대체발송시 대체발송 발신번호를 입력해주세요.');
            return false;
          }
          if(!fbInfo.msg){
            confirm.fnAlert(this.componentsTitle, '대체발송시 대체발송 내용을 입력해주세요.');
            return false;
          }
        }
      }

      return true;
    },
    //메시지 발송 처리
    fnSendFrndTalkMessage(testSendYn){
      if(this.inProgress){
        confirm.fnAlert(this.componentsTitle, '친구톡 메시지 발송 처리중입니다.');
        return;
      }

      //유효성 체크
      if(this.fnValidSendMsgData(testSendYn) == false) return;

      if(this.fnNightSendCheck() == false) return;

      //광고성 야간발송 확인
      if(this.sendData.msgKind == 'A'){
        const msg = '친구톡 광고성 메시지는 20~8시 발송시 실패 처리 됩니다.\n메시지를 발송하시겠습니까?';
        if(
          (this.sendData.rsrvSendYn == 'Y' && this.$gfnCommonUtils.islimitAdMsgSendTime(this.sendData.rsrvHH))
          || (this.sendData.rsrvSendYn == 'N' && this.$gfnCommonUtils.islimitAdMsgSendTime())
        ){
          eventBus.$on('callbackEventBus', this.fnProcSendFrndTalkMessage);
          confirm.fnConfirm(this.componentsTitle, msg, "확인", testSendYn);
          return;
        }
      }
      this.fnProcSendFrndTalkMessage(testSendYn);
    },
    fnProcSendFrndTalkMessage(testSendYn){
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

      messageApi.sendFrndTalkMessage(fd).then(response =>{
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

      // 발송제한 금액 세팅
      this.fnSetSentAmount();
    },
    fnAlertFeeMsgCallBack(result){
      if(this.$gfnCommonUtils.isEmpty(result.message)){
        confirm.fnAlert(this.componentsTitle, '발송 요청 처리 되었습니다.\n[메시지발송 성공/실패는 조회의 "전체" 메뉴에서 에서 확인이 가능합니다.]');
      } else {
        confirm.fnAlert(this.componentsTitle, result.message);
      }
      this.fnReset();
    },
    //메세지 미리보기 변경
    fnChgPreviewMessageType(type){
      this.previewMessageType = type;
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
    fnUpdateRsrvDate(sltDate){
      this.sendData.rsrvDate = sltDate;
    },
    //수신자 입력 타입 변경시
    fnChgCuInputType(chgYn){
      if(this.$gfnCommonUtils.defaultIfEmpty(chgYn, 'Y') == 'Y'){
        if((this.beforeCuInputType != 'DICT' && this.beforeCuInputType != 'ADDR')
          || (this.sendData.cuInputType != 'DICT' && this.sendData.cuInputType != 'ADDR')){
          this.fnCallbackRecvInfoLst(null);  //수신자 입력 타입 변경시 수신자 정보 초기화
        }
      }
      if(!this.sendData.frndTalkContent){
        confirm.fnAlert(this.componentsTitle, '메시지 내용을 먼저 입력해주세요.');
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
      const conts = this.sendData.frndTalkContent + (typeof this.sendData.fbInfo.msg === 'undefined' ? '' : this.sendData.fbInfo.msg);
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
    //대체발송 타입 변경시
    fnChgRplcSendType(){
      this.sendData.fbInfo = {};  //대체발송 정보 초기화
      this.fnCallbackRecvInfoLst(null);  //수신자 정보 초기화
      //this.$refs.cuInputType_ALL.click();
      if(this.sendData.rplcSendType == 'NONE'){
        this.previewMessageType = 'FRIENDTALK';
      }
    },
    fnDelButton(idx){
      if(idx <= this.sendData.buttonList.length){
        this.sendData.buttonList.splice(idx, 1);
      }
    },
    fnAddButton(){
      if(this.sendData.buttonList.length < this.buttonLimitSize){
        const baseButtonInfo = {
          name : '',
          linkType : 'WL',
          linkPc: '',
          linkMo: '',
        };
        this.sendData.buttonList.push(baseButtonInfo);
      } else {
        confirm.fnAlert(this.componentsTitle, '버튼은 최대 '+this.buttonLimitSize+'개 까지 입력 가능합니다.');
      }
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
      this.$refs.imgMngPopup.fnSearch();
      this.imgMngOpen = !this.imgMngOpen;
    },
    //이미지선택 callback
    fnCallbackImgInfo(imgInfo) {
      this.sendData.imgUrl = imgInfo.chImgUrl;
      this.sendData.fileId = imgInfo.fileId;
      this.sendData.wideImgYn = imgInfo.wideImgYn;
    },
    fnDelImg(){
      this.sendData.imgUrl = '';
      this.sendData.fileId = '';
      this.sendData.wideImgYn = 'N';
    },
    //템플릿 정보 Set
    fnSetTemplateInfo(templateInfo){
      this.sendData.msgKind = this.$gfnCommonUtils.defaultIfEmpty(templateInfo.msgKind, '');
      this.sendData.frndTalkContent = this.$gfnCommonUtils.defaultIfEmpty(templateInfo.tmpltContent, '');
      this.sendData.fileId = this.$gfnCommonUtils.defaultIfEmpty(templateInfo.fileId, '');
      this.sendData.imgUrl = this.$gfnCommonUtils.defaultIfEmpty(templateInfo.imgUrl, '');
      this.sendData.imgLink = this.$gfnCommonUtils.defaultIfEmpty(templateInfo.imgLink, '');
      this.sendData.wideImgYn = this.$gfnCommonUtils.defaultIfEmpty(templateInfo.wideImgYn, 'N');
      this.sendData.buttonList = JSON.parse(this.$gfnCommonUtils.defaultIfEmpty(templateInfo.tmpltButtons, ''));
    },
    fnOpenFrndTalkTemplatePopup(){
      this.$refs.frndTalkTmplPopup.fnSearch();
      this.frndTalkTemplateOpen = !this.frndTalkTemplateOpen;
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
        this.fnSendFrndTalkMessage('Y');
      } else {
        this.sendData.testRecvInfoLst = [];
      }
    },
    fnGetLimitLength(){
      if(this.$gfnCommonUtils.isEmpty(this.sendData.imgUrl)){
        return 1000;
      } else {
        if(this.sendData.wideImgYn == 'Y'){
          return 76;
        } else {
          return 400;
        }
      }
    },
    fnOpenFrndTalkContentsPopup(){
      this.$refs.frndTalkContPopup.fnGetLimitLength(this.fnGetLimitLength());
      this.frndTalkContsOpen = !this.frndTalkContsOpen;
    },
    //내용입력 callback
    fnSetFrndTalkInfo(data){
      if(this.sendData.frndTalkContent != data.frndTalkContent){
        this.fnCallbackRecvInfoLst(null);  //수신자 정보 초기화
      }
      this.sendData.frndTalkContent = data.frndTalkContent;
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
    //대체발송 정보 callback
    fnCallbackFbInfo(fbInfo) {
      this.sendData.fbInfo = fbInfo;
      this.fnCallbackRecvInfoLst(null);  //수신자 정보 초기화
    },
    //빈오브젝트확인
    fnIsEmptyObj(obj){
      if(typeof obj === 'undefined') return true;
      if(Object.keys(obj).length === 0) return true;
      return false;
    },
    //친구톡 템플릿 엑셀 다운로드
    async fnExcelTmplteDownLoad(){
      if(this.fnSetContsVarNms() == false) return;
      var params = {
        contsVarNms : this.sendData.contsVarNms,
        requiredCuid: this.sendData.requiredCuid,
        requiredCuPhone: this.sendData.requiredCuPhone
      };
      await messageApi.excelDownSendFrndTalkRecvTmplt(params);
    },
    //단축 URL 선택
    btnSelect(shortendUrl){
      if(this.sendData.buttonList.length > 0 && this.selIdx !== null && this.sendData.buttonList[this.selIdx]){
        // mobile link
        this.$set(this.sendData.buttonList[this.selIdx], 'linkMo', shortendUrl)

        // pc link
        this.$set(this.sendData.buttonList[this.selIdx], 'linkPc', shortendUrl)
      }

      this.selIdx = null
    },
    fnChgBtnType(idx){
      // const vm = this
      Object.keys(this.sendData.buttonList[idx]).forEach((key) => {
        if(key != 'linkType'){
          delete this.sendData.buttonList[idx][key];
        }
      })
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