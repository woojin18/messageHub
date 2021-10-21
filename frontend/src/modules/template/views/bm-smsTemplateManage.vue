<template>

  <div class="row row-no-margin">
    <div class="contentHeader">
      <h2>템플릿 > 문자</h2>
      <!-- <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="SMS/MMS 템플릿 관리 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
    </div>

    <!-- 본문 -->
    <div class="row">
      <div class="phone3 inline-block" style="width:30%">
        <div class="phoneFixed">
          <!-- phoneWrap -->
          <div class="phoneWrap">
            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
            <div class="phoneTextWrap scroll-yc">
              <div v-if="tmpltData.senderType == 'MMS' || tmpltData.senderType == 'LMS'" class="phoneText2 mb10">
                <p v-if="$gfnCommonUtils.isEmpty(tmpltData.tmpltTitle)">템플릿 제목</p>
                <p v-else><span v-if="(tmpltData.senderType == 'MMS' || tmpltData.senderType == 'LMS') && tmpltData.msgKind == 'A'">(광고)</span>{{tmpltData.tmpltTitle}}</p>
              </div>
              <div v-if="tmpltData.senderType == 'MMS' || tmpltData.sendetType == 'LMS'">
                <div v-for="(imgInfo, idx) in tmpltData.imgInfoList" :key="idx" class="phoneText2 mt10 text-center simulatorImg"
                  :style="'padding:65px;background-image: url('+imgInfo.imgUrl+');'">
                </div>
              </div>
              <div class="phoneText1">
                <p v-if="$gfnCommonUtils.isEmpty(tmpltData.tmpltContent) && (tmpltData.msgKind != 'A' || $gfnCommonUtils.isEmpty(tmpltData.rcvblcNumber))" class="font-size14 color4 mt10">템플릿 내용</p>
                <p v-else class="font-size14 color4 mt10">
                  <span><pre><span v-if="tmpltData.senderType == 'SMS' && tmpltData.msgKind == 'A'">(광고)</span>{{tmpltData.tmpltContent}}</pre></span>
                  <br v-if="!$gfnCommonUtils.isEmpty(tmpltData.tmpltContent)"/>
                  <span v-if="tmpltData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(tmpltData.rcvblcNumber)">
                    {{tmpltData.rcvblcNumber}}
                  </span>
                </p>
              </div>
            </div>
          </div>
          <!-- //phoneWrap -->
        </div>
      </div>  
      <div class="of_h inline-block vertical-top consoleCon" style="width:60%">
        <div class="of_h">
          <div class="float-left" style="width:31%"><h4>템플릿명 *</h4></div>
          <div class="float-left" style="width:69%">
            <input type="text" class="inputStyle" v-model="tmpltData.tmpltName" maxlength="100">
          </div>
        </div>
        <div class="of_h">
          <div class="float-left" style="width:31%"><h4>템플릿설명</h4></div>
          <div class="float-left" style="width:69%">
            <input type="text" class="inputStyle" v-model="tmpltData.tmpltDesc" maxlength="100">
          </div>
        </div>
        <div class="of_h">
          <div class="float-left" style="width:31%"><h4>발송유형 *</h4></div>
          <div class="float-left" style="width:69%">
            <input type="radio" id="senderType_SMS" name="senderType" value="SMS" v-model="tmpltData.senderType">
            <label for="senderType_SMS" class="mr30">SMS</label>
            <input type="radio" id="senderType_LMS" name="senderType" value="LMS" v-model="tmpltData.senderType">
            <label for="senderType_LMS" class="mr30">LMS</label>
            <input type="radio" id="senderType_MMS" name="senderType" value="MMS" v-model="tmpltData.senderType">
            <label for="senderType_MMS">MMS</label>
          </div>
        </div>
        <div class="of_h">
          <div class="float-left" style="width:31%"><h4>타 프로젝트 사용여부 *</h4></div>
          <div class="float-left" style="width:69%">
            <input type="radio" id="otherProjectUseYn_Y" name="otherProjectUseYn" value="Y" v-model="tmpltData.otherProjectUseYn">
            <label for="otherProjectUseYn_Y" class="mr30">공용</label>
            <input type="radio" id="otherProjectUseYn_N" name="otherProjectUseYn" value="N" v-model="tmpltData.otherProjectUseYn">
            <label for="otherProjectUseYn_N">전용</label>
          </div>
        </div>
        <div class="of_h">
          <div class="float-left" style="width:31%"><h4>메시지구분 *</h4></div>
          <div class="float-left" style="width:69%">
            <input type="radio" id="msgKind_A" name="msgKind" value="A" v-model="tmpltData.msgKind">
            <label for="msgKind_A" class="mr30">광고성</label>
            <input type="radio" id="msgKind_I" name="msgKind" value="I" v-model="tmpltData.msgKind">
            <label for="msgKind_I">정보성</label>
          </div>
        </div>
        <div v-if="tmpltData.senderType == 'MMS' || tmpltData.senderType == 'LMS'" class="of_h">
          <div class="float-left" style="width:31%"><h4>제목 *</h4></div>
          <div class="float-left" style="width:69%">
            <input type="text" class="inputStyle" v-model="tmpltData.tmpltTitle" maxlength="45" @input="fnSetCurrByte">
          </div>
        </div>
        <div class="of_h">
          <div class="float-left" style="width:31%"><h4>내용 *</h4></div>
          <div class="float-left" style="width:69%">
            <textarea class="textareaStyle height190" :placeholder="contentAreaPlaceholder" v-model="tmpltData.tmpltContent" maxlength="2000" @input="fnSetCurrByte"></textarea>
            <strong class="letter">({{msgCurrByte}} / {{msgLimitByte}})</strong>
          </div>
        </div>
        <div v-if="tmpltData.msgKind == 'A'" class="of_h consolMarginTop">
          <div class="float-left" style="width:31%">
            <h4>광고성메시지 수신거부번호</h4>
            <a href="#" class="btnStyle1 backLightGray" @click.prevent="rcvblcNumOpen=true" title="수신거부번호 선택" activity="READ">선택</a>
          </div>
          <div class="float-left" style="width:69%">
            <input type="text" class="inputStyle" v-model="tmpltData.rcvblcNumber" maxlength="10" placeholder="ex) 수신거부번호 : 080-0000-0000" @input="fnSetCurrByte">
          </div>
        </div>

        <div v-if="tmpltData.senderType == 'MMS'" class="of_h user-phone">
          <div class="float-left" style="width:31%">
            <h4>이미지</h4>
          </div>
          <div class="float-left" style="width:69%">
            <div class="of_h">
              <div class="float-left" style="width:22%">
                <a @click="fnOpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
              </div>
              <ul v-for="imgIdx in imgLimitSize" :key="imgIdx" class="float-right attachList" style="width:75%; padding:5px 15px; height:30px;">
                <li v-if="tmpltData.imgInfoList.length > imgIdx-1">
                  <a @click="fnUpdateImg(imgIdx)">{{fnSubString(tmpltData.imgInfoList[imgIdx-1].imgUrl, 0, 35)}}</a><a @click="fnDelImg(imgIdx)"><i class="fal fa-times"></i></a>
                </li>
                <li v-else>
                  <a></a>
                </li>
              </ul>
            </div>
          </div>
        </div>
        
        <div class="mt20 float-right">
          <a v-if="isInsert" @click="fnSaveSmsTemplate" class="btnStyle2 backRed ml10" title="등록" activity="SAVE">등록</a>
          <a v-else @click="fnSaveSmsTemplate" class="btnStyle2 backWhite ml10" title="수정" activity="SAVE">수정</a>
          <router-link :to="{ name: 'smsTemplateList' }" tag="a" class="btnStyle2 ml10">취소</router-link>
        </div>
      </div>
    </div>

    <ImageManagePopUp @img-callback="fnCallbackImgInfo" :imgMngOpen.sync="imgMngOpen" :useCh="useCh" ref="imgMngPopup"></ImageManagePopUp>
    <RcvblcNumPopup @callback-func="fnCallbackRcvblcNum" :rcvblcNumOpen.sync="rcvblcNumOpen"></RcvblcNumPopup>
  </div>

</template>

<script>
import RcvblcNumPopup from "@/modules/message/components/bp-rcvblcNumManage.vue";
import ImageManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";

import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import templateApi from "@/modules/template/service/templateApi.js";

export default {
  name: 'smsTemplateManage',
  components : {
    RcvblcNumPopup,
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
        return '문자';
      }
    },
  },
  data() {
    return {
      rcvblcNumOpen: false,
      imgMngOpen : false,
      imgUploadOpen : false,
      imgLimitSize : 3,
      msgCurrByte: 0,
      msgLimitByte: 0,
      titleLimitByte: 40,
      useCh : 'MMS',
      beforeSenderTyep: 'SMS',
      beforemsgKind: 'I',
      isInsert : true,
      contentAreaPlaceholder: '변수로 설정하고자 하는 내용을 #{ }표시로 작성해 주십시오.\n:예) 이름과 출금일을 변수 설정\n:예) #{name}님 #{yyyymmdd} 출금 예정입니다.',
      tmpltData : {otherProjectUseYn: 'N', msgKind: 'I', senderType: 'SMS', imgInfoList:[]}
    }
  },
  watch: {
    tmpltData: {
      deep: true,
      handler(nData) {
        if(nData.senderType != this.beforeSenderTyep){
          this.beforeSenderTyep = nData.senderType;
          this.fnGetLimitByte();
        }
        if(nData.msgKind != this.beforemsgKind){
          this.beforemsgKind = nData.msgKind;
          this.fnSetCurrByte();
        }
      }
    }
  },
  mounted() {
    this.fnValidUseChGrp();
    this.fnSetTemplateInfo();
    this.fnGetLimitByte();
    this.fnSetCurrByte();
  },
  methods: {
    fnSetCurrByte(){
      let body = this.$gfnCommonUtils.defaultIfEmpty(this.tmpltData.tmpltContent, '');
      let rcvblcNum = this.$gfnCommonUtils.defaultIfEmpty(this.tmpltData.rcvblcNumber, '');
      let totalMsg = body ;
      if(this.tmpltData.msgKind == 'A'){
        totalMsg += '\n' + rcvblcNum + (this.tmpltData.senderType == 'SMS' ? '(광고)' : '');
      }
      this.msgCurrByte = this.getByte(totalMsg);
    },
    fnGetLimitByte(){
      if(this.tmpltData.senderType == 'SMS'){
        this.msgLimitByte = 90;
      } else if(this.tmpltData.senderType == 'LMS'){
        this.msgLimitByte = 1000;
      } else if(this.tmpltData.senderType == 'MMS'){
        this.msgLimitByte = 2000;
      }
    },
    fnCallbackRcvblcNum(rcvblcNum){
      this.tmpltData.rcvblcNumber = rcvblcNum;
      this.fnSetCurrByte();
    },
    async fnValidUseChGrp(){
      let params = {chGrp: 'SMS/MMS'};
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
      if(!this.$gfnCommonUtils.isEmpty(this.tmpltId)){
        this.isInsert = false;
        this.fnSelectSmsTmpltInfo();
      } else {
        this.isInsert = true;
      }
    },
    //템플릿 정보 조회
    fnSelectSmsTmpltInfo(){
      const vm = this;
      const params = {tmpltId: this.tmpltId};
      templateApi.selectSmsTmpltInfo(params).then(response => {
        const result = response.data;
        if(result.success) {
          const targetField = ['tmpltName', 'tmpltDesc', 'tmpltTitle', 'tmpltContent', 'rcvblcNumber'];
          let tempData = Object.assign({}, this.tmpltData);
          result.data.forEach(function(obj){
            tempData = obj;
            if(!vm.$gfnCommonUtils.isEmpty(obj.imgInfoList)){
              tempData.imgInfoList = JSON.parse(obj.imgInfoList);
            } else {
              tempData.imgInfoList = [];
            }
            tempData.otherProjectUseYn = (obj.projectId == 'ALL' ? 'Y' : 'N');
            vm.$gfnCommonUtils.unescapeXssFields(tempData, targetField);
          });
          this.tmpltData = Object.assign({}, tempData);
          this.fnSetCurrByte();
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
          this.tmpltData = {};
          this.isInsert = true;
        }
      });
    },
    //유효성 체크
    fnIsValid(){
      if(!this.tmpltData.tmpltName){
        confirm.fnAlert(this.componentsTitle, '템플릿명을 입력해주세요.');
        return false;
      }
      if(!this.tmpltData.senderType){
        confirm.fnAlert(this.componentsTitle, '발송유형을 선택해주세요.');
        return false;
      }
      if(!this.tmpltData.otherProjectUseYn){
        confirm.fnAlert(this.componentsTitle, '타 프로젝트 사용여부를 선택해주세요.');
        return false;
      }
      if(!this.tmpltData.msgKind){
        confirm.fnAlert(this.componentsTitle, '메시지구분을 선택해주세요.');
        return false;
      }
      if(this.tmpltData.senderType == 'MMS'){
        if(this.tmpltData.tmpltTitle == false){
          confirm.fnAlert(this.componentsTitle, '메시지 제목을 입력해주세요.');
          return false;
        }
        let title = this.$gfnCommonUtils.defaultIfEmpty(this.tmpltData.tmpltTitle, '');
        let totalTitle =  (this.tmpltData.msgKind == 'A' ? '(광고)' : '') + title;
        let titleCurrByte = this.getByte(totalTitle);
        if(this.titleLimitByte < titleCurrByte){
          const alertMsg = (this.tmpltData.msgKind != 'A' ? '' : '\'(광고)\' + ') + '제목이 '+this.titleLimitByte+'byte를 넘지 않아야됩니다.\n(현재 : '+titleCurrByte+'byte)';
          confirm.fnAlert(this.componentsTitle, alertMsg);
          return false;
        }
      }
      if(this.tmpltData.senderType == 'MMS' && !this.tmpltData.tmpltTitle){
        confirm.fnAlert(this.componentsTitle, '메시지 제목을 입력해주세요.');
        return false;
      }
      if(!this.tmpltData.tmpltContent){
        confirm.fnAlert(this.componentsTitle, '메시지 내용을 입력해주세요.');
        return false;
      }
      if(this.tmpltData.msgKind == 'A' && !this.tmpltData.rcvblcNumber){
        confirm.fnAlert(this.componentsTitle, '광고성메시지 수신거부번호를 입력해주세요.');
        return false;
      }
      if(this.msgLimitByte < this.msgCurrByte){
        const alertMsg = '내용 + 광고성메시지 수신거부번호가 '+this.msgLimitByte+'byte를 넘지 않아야됩니다.\n(현재 : '+this.msgCurrByte+'byte)';
        confirm.fnAlert(this.componentsTitle, alertMsg);
        return false;
      }

      return true;
    },
    //저장
    fnSaveSmsTemplate(){
      //유효성 검사
      if(this.fnIsValid() == false) return;

      const saveType = (this.isInsert ? '등록' : '수정');
      eventBus.$on('callbackEventBus', this.fnProcSaveSmsTemplate);
      confirm.fnConfirm(this.componentsTitle, "SMS 템플릿을 "+saveType+"하시겠습니까?", "확인");
    },
    async fnProcSaveSmsTemplate(){
      //DATA Set
      let params = Object.assign({}, this.tmpltData);
      if(this.tmpltData.senderType == 'SMS') {
        params.tmpltTitle = '';
      }
      if(this.tmpltData.msgKind != 'A') {
        params.rcvblcNumber = '';
      }
      if(this.tmpltData.imgInfoList.length == 0){
        params.imgInfoList = [];
      }else {
        params.imgInfoListStr = JSON.stringify(this.tmpltData.imgInfoList);
      }
      
      //저장처리
      await templateApi.saveSmsTmplt(params).then(response => {
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
        this.$router.push('smsTemplateList')
      }
    },
    fnUpdateImg(idx){
      let params = {updIdx: idx};
      this.$refs.imgMngPopup.fnSearch();
      this.$refs.imgMngPopup.fnSetRtnParams(params);
      this.imgMngOpen = !this.imgMngOpen;
    },
    fnDelImg(idx){
      this.tmpltData.imgInfoList.splice(idx-1, 1);
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
      if(this.fnImgLimitSize() == false) return;
      this.$refs.imgMngPopup.fnSearch();
      this.imgMngOpen = !this.imgMngOpen;
    },
    fnCallbackImgInfo(imgInfo){
      let temp = {
        imgUrl: imgInfo.chImgUrl,
        fileId: imgInfo.fileId
      };
      if(imgInfo.rtnParams && imgInfo.rtnParams.updIdx > 0){
        this.tmpltData.imgInfoList[imgInfo.rtnParams.updIdx-1] = temp;
      } else {
        if(this.fnImgLimitSize() == false) return;
        this.tmpltData.imgInfoList.push(temp);
      }
      //this.fnDelDuplImgInfo();
    },
    fnDelDuplImgInfo(){
      const vm = this;
      this.tmpltData.imgInfoList = this.tmpltData.imgInfoList.filter(function(item, i){
        return (
          vm.tmpltData.imgInfoList.findIndex((item2) => {
            return item.fileId === item2.fileId;
          }) === i
        );
      });
    },
    fnImgLimitSize(){
      if(this.tmpltData.imgInfoList != null && this.tmpltData.imgInfoList.length >= this.imgLimitSize){
        confirm.fnAlert(this.componentsTitle, '이미지는 최대 '+this.imgLimitSize+'개까지 등록 가능합니다.');
        return false;
      }
    },
    //get 문자열 byte
    getByte(str) {
      return str
        .split('')
        .map(s => s.charCodeAt(0))
        .reduce((prev, c) => (prev + ((c === 10) ? 2 : ((c >> 7) ? 2 : 1))), 0);
    },
  }
}
</script>