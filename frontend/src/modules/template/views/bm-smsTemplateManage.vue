<template>

  <div class="row row-no-margin">
    <div class="contentHeader">
      <h2>SMS/MMS 템플릿 관리</h2>
      <!-- <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="SMS/MMS 템플릿 관리 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
    </div>

    <!-- 본문 -->
    <div class="row">
      <div class="phone3 inline-block" style="width:30%">
        <div class="phoneFixed">
          <!-- phoneWrap -->
          <div class="phoneWrap">
            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
            <div class="phoneTextWrap scroll-y">
              <div v-if="tmpltData.senderType == 'MMS'" class="phoneText2 mb10">
                <p v-if="$gfnCommonUtils.isEmpty(tmpltData.tmpltTitle)">템플릿 제목</p>
                <p v-else>{{tmpltData.tmpltTitle}}</p>
              </div>
              <div v-if="tmpltData.senderType == 'MMS'">
                <div v-for="(imgInfo, idx) in tmpltData.imgInfoList" :key="idx" class="phoneText2 mt10 text-center"
                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+imgInfo.imgUrl+');'">
                </div>
              </div>
              <div class="phoneText1">
                <p v-if="$gfnCommonUtils.isEmpty(tmpltData.tmpltContent) && (tmpltData.msgKind != 'A' || $gfnCommonUtils.isEmpty(tmpltData.rcvblcNumber))" class="font-size14 color4 mt10">템플릿 내용</p>
                <p v-else class="font-size14 color4 mt10">
                  <span v-html="$gfnCommonUtils.newLineToBr(tmpltData.tmpltContent)"></span>
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
        <div v-if="tmpltData.senderType == 'MMS'" class="of_h">
          <div class="float-left" style="width:31%"><h4>제목 *</h4></div>
          <div class="float-left" style="width:69%">
            <input type="text" class="inputStyle" v-model="tmpltData.tmpltTitle" maxlength="45">
          </div>
        </div>
        <div class="of_h">
          <div class="float-left" style="width:31%"><h4>내용 *</h4></div>
          <div class="float-left" style="width:69%">
            <textarea class="textareaStyle height190" v-model="tmpltData.tmpltContent" maxlength="2000"></textarea>
          </div>
        </div>
        <div v-if="tmpltData.msgKind == 'A'" class="of_h consolMarginTop">
          <div class="float-left" style="width:31%">
            <h4>광고성메시지 수신거부번호</h4>
          </div>
          <div class="float-left" style="width:69%">
            <input type="text" class="inputStyle" v-model="tmpltData.rcvblcNumber" maxlength="10">
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
                  <a @click="fnDelImg(idx)">{{fnSubString(tmpltData.imgInfoList[imgIdx-1].imgUrl, 0, 35)}} <i class="fal fa-times"></i></a>
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

  </div>

</template>

<script>
import ImageManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import templateApi from "@/modules/template/service/templateApi.js";

export default {
  name: 'smsTemplateManage',
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
        return 'SMS 템플릿 관리';
      }
    },
  },
  data() {
    return {
      imgMngOpen : false,
      imgUploadOpen : false,
      imgLimitSize : 2,
      useCh : 'MMS',
      isInsert : true,
      tmpltData : {imgInfoList:[]}
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
          result.data.forEach(function(obj){
            vm.tmpltData = obj;
            if(!vm.$gfnCommonUtils.isEmpty(obj.imgInfoList)){
              vm.tmpltData.imgInfoList = JSON.parse(obj.imgInfoList);
            } else {
              vm.tmpltData.imgInfoList = [];
            }
            vm.tmpltData.otherProjectUseYn = (obj.projectId == 'ALL' ? 'Y' : 'N');
          });
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

      const msgLimitByte = (this.tmpltData.senderType == 'SMS' ? 80 : 2000);
      let totalMsg = this.tmpltData.tmpltContent;
      if(this.tmpltData.senderType != 'SMS'){
        totalMsg += this.tmpltData.tmpltTitle;
      }
      if(this.tmpltData.msgKind == 'A'){
        totalMsg += '\n' + this.tmpltData.rcvblcNumber;
      }
      const totByte = this.getByte(totalMsg);
      if(msgLimitByte < totByte){
        const alertMsg = (this.tmpltData.senderType == 'SMS' ? '' : '제목 + ') + '내용 + 광고성메시지 수신거부번호가 '+msgLimitByte+'를 넘지 않아야됩니다.\n(현재 : '+totByte+'byte)';
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
      if(this.tmpltData.senderType != 'MMS') {
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
    fnDelImg(idx){
      this.tmpltData.imgInfoList.splice(idx, 1);
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
      if(this.fnImgLimitSize() == false) return;
      let temp = {
        imgUrl: imgInfo.chImgUrl,
        fileId: imgInfo.fileId
      };
      this.tmpltData.imgInfoList.push(temp);
      this.fnDelDuplImgInfo();
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
        confirm.fnAlert(this.componentsTitle, '이미지는 최대 2개까지 등록 가능합니다.');
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