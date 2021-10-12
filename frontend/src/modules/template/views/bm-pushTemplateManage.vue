<template>
  <!-- content -->
  <div class="row row-no-margin">
    <div class="contentHeader">
      <h2>템플릿 > 푸시</h2>
      <!-- <a href="#self" class="btnStyle1 absolute top0 right0" onClick="window.location.reload()" title="푸시 템플릿 등록/상세 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
    </div>

    <ImageManagePopUp @img-callback="fnCallbackImgInfo" :imgMngOpen.sync="imgMngOpen" :useCh="useCh" ref="imgMng"></ImageManagePopUp>
    <ImageUploadPopUp :imgUploadOpen.sync="imgUploadOpen"></ImageUploadPopUp>

    <!-- 본문 -->
    <div class="row">
      <div class="phone3 inline-block" style="width:30%">
        <div class="phoneFixed">
          <!-- phoneWrap -->
          <div class="phoneWrap">
            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
            <div class="phoneTextWrap scroll-yc">
              <div v-if="!fnIsEmpty(tmpltData.tmpltTitle)" class="phoneText2">
                <p><span v-if="tmpltData.msgKind == 'A'">(광고)</span>{{tmpltData.tmpltTitle}}</p>
              </div>
              <div v-if="tmpltData.msgType == 'IMAGE' && fnIsEmpty(tmpltData.imgUrl)" class="phoneText2 mt10 text-center" style="padding:65px">
                <i class="fas fa-image-polaroid" style="font-size:38px; color:#D5D5D5"></i>
                <p class="font-size14 color3 mt15">이미지 영역</p>
              </div>
              <div v-if="tmpltData.msgType == 'IMAGE' && !fnIsEmpty(tmpltData.imgUrl)" class="phoneText2 mt10 text-center simulatorImg"
                :style="'padding:65px;background-image: url('+tmpltData.imgUrl+');'">
              </div>
              <div>
                <p v-if="fnIsEmpty(tmpltData.tmpltContent) && (tmpltData.msgKind != 'A' || fnIsEmpty(tmpltData.rcvblcNumber))" class="font-size14 color4 mt10">템플릿 내용</p>
                <p v-else class="font-size14 color4 mt10">
                  <span><pre><span v-if="fnIsEmpty(tmpltData.tmpltTitle) && tmpltData.msgKind == 'A'">(광고)</span>{{tmpltData.tmpltContent}}</pre></span>
                  
                  <br v-if="!fnIsEmpty(tmpltData.tmpltContent)"/>
                  <span v-if="tmpltData.msgKind == 'A' && !fnIsEmpty(tmpltData.rcvblcNumber)">
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
        <div class="of_h user-phone">
          <div class="float-left" style="width:28%">
            <h4>템플릿명 *</h4>
          </div>
          <div class="float-left" style="width:72%">
            <input type="text" class="inputStyle float-right" title="템플릿명 입력란" v-model="tmpltData.tmpltName" maxlength="100">
          </div>
        </div>
        <div class="of_h user-phone">
          <div class="float-left" style="width:28%">
            <h4>메시지타입 *</h4>
          </div>
          <div class="float-left" style="width:72%">
            <input type="radio" id="msgType_BASE" name="msgType" value="BASE" v-model="tmpltData.msgType">
            <label for="msgType_BASE" class="mr30">기본</label>
            <input type="radio" id="msgType_IMAGE" name="msgType" value="IMAGE" v-model="tmpltData.msgType">
            <label for="msgType_IMAGE">이미지</label>
          </div>
        </div>
        <div class="of_h user-phone">
          <div class="float-left" style="width:28%">
            <h4>메시지구분 *</h4>
          </div>
          <div class="float-left" style="width:72%">
            <input type="radio" id="msgKind_A" name="msgKind" value="A" v-model="tmpltData.msgKind">
            <label for="msgKind_A" class="mr30">광고성</label>
            <input type="radio" id="msgKind_I" name="msgKind" value="I" v-model="tmpltData.msgKind">
            <label for="msgKind_I">정보성</label>
          </div>
        </div>
        <div class="of_h user-phone">
          <div class="float-left" style="width:28%">
            <h4>타 프로젝트 사용여부  *</h4>
          </div>
          <div class="float-left" style="width:72%">
            <input type="radio" id="otherProjectUseYn_Y" name="otherProjectUseYn" value="Y" v-model="tmpltData.otherProjectUseYn">
            <label for="otherProjectUseYn_Y" class="mr30">공용</label>
            <input type="radio" id="otherProjectUseYn_N" name="otherProjectUseYn" value="N" v-model="tmpltData.otherProjectUseYn">
            <label for="otherProjectUseYn_N">전용</label>
          </div>
        </div>
        <div class="of_h user-phone">
          <div class="float-left" style="width:28%">
            <h4>제목</h4>
          </div>
          <div class="float-left" style="width:72%">
            <input type="text" class="inputStyle float-right" title="제목 입력란" id="tmpltTitle" name="tmpltTitle" v-model="tmpltData.tmpltTitle" maxlength="40">
          </div>
        </div>
        <div class="of_h user-phone">
          <div class="float-left" style="width:28%">
            <h4>내용 *</h4>
          </div>
          <div class="float-left" style="width:72%">
            <textarea class="textareaStyle height120" :placeholder="contentAreaPlaceholder" v-model="tmpltData.tmpltContent" maxlength="2000"></textarea>
            <div v-if="tmpltData.msgKind == 'A'">
              <p class="color5">광고성 메시지 발송시, 자동으로 (광고)가 표시되오니, 내용에 (광고)문구는 입력하지 않아도 됩니다.</p>
              <input type="text" id="rcvblcNumber" name="rcvblcNumber" class="inputStyle float-right mt10" title="내용 입력란" v-model="tmpltData.rcvblcNumber" placeholder="설정 > 푸시 알림 설정 변경" maxlength="45">
              <p class="color5">푸시 수신거부 방법을 입력해주세요. 푸시 메시지에 (수신거부:거부 방법)이 포함됩니다.</p>
            </div>
          </div>
        </div>
        <div class="of_h user-phone">
            <div class="float-left" style="width:28%">
              <h4>부가정보(EXT)</h4>
            </div>
            <div class="float-left" style="width:72%">
              <input type="text" class="inputStyle float-right" title="부가정보(EXT) 입력란" v-model="tmpltData.adtnInfo" maxlength="45">
            </div>
          </div>
        <div v-if="tmpltData.msgType == 'IMAGE'" class="of_h user-phone">
          <div class="float-left" style="width:28%">
            <h4>이미지</h4>
          </div>
          <div class="float-left" style="width:72%">
            <div class="of_h">
              <div class="float-left" style="width:25%">
                <a @click="fnOpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택" activity="READ">이미지선택</a>
              </div>
              <ul class="float-right attachList" style="width:74%; padding:5px 15px; height:30px;">
                <li><a @click="fnDelImg">{{fnSubString(tmpltData.imgUrl, 0, 35)}}  <i v-if="!fnIsEmpty(tmpltData.imgUrl)" class="fal fa-times"></i></a></li>
              </ul>
            </div>
          </div>
        </div>
        <div class="mt30 float-right">
          <a @click="fnSavePushTemplate" class="btnStyle2" title="저장" activity="SAVE">{{isInsert ? '등록' : '수정'}}</a>
          <router-link :to="{ name: 'pushTemplateList' }" tag="a" class="btnStyle2 backRed ml10">취소</router-link>
        </div>
      </div>
    </div>
  </div>
  <!-- //content -->
</template>

<script>
import templateApi from "@/modules/template/service/templateApi.js";
import ImageManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import ImageUploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  name: 'pushTemplateManage',
  components : {
    ImageManagePopUp,
    ImageUploadPopUp
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
        return '푸시 템플릿 관리';
      }
    },
  },
  data() {
    return {
      imgMngOpen : false,
      imgUploadOpen : false,
      useCh : 'PUSH',
      isInsert : true,
      contentAreaPlaceholder: '변수로 설정하고자 하는 내용을 #{ }표시로 작성해 주십시오.\n:예) 이름과 출금일을 변수 설정\n:예) #{name}님 #{yyyymmdd} 출금 예정입니다.',
      tmpltData : {imgUrl:''}
    }
  },
  mounted() {
    this.fnValidUseChGrp();
    this.fnSetTemplateInfo();
  },
  methods: {
    async fnValidUseChGrp(){
      let params = {chGrp: 'PUSH'};
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
    fnDelImg(){
      this.tmpltData.imgUrl = '';
      this.tmpltData.fileId = '';
    },
    fnSubString(str, sIdx, length){
      var shortStr = ''
      if(!this.fnIsEmpty(str)){
        shortStr = str.toString();
        if(shortStr.length > length){
          shortStr = shortStr.substring(sIdx, length) + '...  ';
        } else {
          shortStr = shortStr + '  ';
        }
      }
      return shortStr;
    },
    //빈값확인
    fnIsEmpty(str){
      if(str) return false;
      else return true
    },
    //푸시 템플릿 정보 조회
    fnSelectPushTmpltInfo(){
      var vm = this;
      var params = {tmpltId: this.tmpltId};
      templateApi.selectPushTmpltInfo(params).then(response => {
        var result = response.data;
        if(result.success) {
          const targetField = ['tmpltName', 'tmpltTitle', 'tmpltContent', 'rcvblcNumber', 'adtnInfo'];
          let tempData = Object.assign({}, this.tmpltData);
          result.data.forEach(function(obj){
            tempData = obj;
            tempData.otherProjectUseYn = (obj.projectId == 'ALL' ? 'Y' : 'N');
            vm.$gfnCommonUtils.unescapeXssFields(tempData, targetField);
          });
          this.tmpltData = Object.assign({}, tempData);
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
          this.tmpltData = {};
          this.isInsert = true;
        }
      });
    },
    //template 정보 조회
    fnSetTemplateInfo(){
      if(!this.$gfnCommonUtils.isEmpty(this.tmpltId)){
        this.isInsert = false;
        this.fnSelectPushTmpltInfo();
      } else {
        this.isInsert = true;
      }
    },
    fnOpenImageManagePopUp(){
      this.$refs.imgMng.fnSearch();
      this.imgMngOpen = !this.imgMngOpen;
    },
    fnOpenImageUploadPopUp : function(){
      this.imgUploadOpen = !this.imgUploadOpen;
    },
    fnCallbackImgInfo(imgInfo) {
      this.tmpltData.imgUrl = imgInfo.chImgUrl;
      this.tmpltData.fileId = imgInfo.fileId;
    },
    //유효성 체크
    fnIsValid(){
      if(!this.tmpltData.tmpltName){
        confirm.fnAlert(this.componentsTitle, '템플릿명을 입력해주세요.');
        return false;
      }
      if(!this.tmpltData.msgType){
        confirm.fnAlert(this.componentsTitle, '메시지타입을 선택해주세요.');
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
      /*
      if(!this.tmpltData.tmpltTitle){
        confirm.fnAlert(this.componentsTitle, '제목을 입력해주세요.');
        return false;
      }
      */
      if(!this.tmpltData.tmpltContent){
        confirm.fnAlert(this.componentsTitle, '내용을 입력해주세요.');
        return false;
      }
      if(this.tmpltData.msgKind == 'A' && !this.tmpltData.rcvblcNumber){
        confirm.fnAlert(this.componentsTitle, '푸시 수신거부 방법을 입력해주세요.');
        return false;
      }
      if(this.tmpltData.msgType == 'IMAGE' && !this.tmpltData.imgUrl){
        confirm.fnAlert(this.componentsTitle, '이미지를 선택해주세요.');
        return false;
      }
      if(this.tmpltData.msgType == 'IMAGE' && !this.tmpltData.fileId){
        confirm.fnAlert(this.componentsTitle, '이미지 정보가 잘못되었습니다. 다시 이미지를 선택해주세요.');
        return false;
      }
      return true;
    },
    //저장
    fnSavePushTemplate(){
      //유효성 검사
      if(this.fnIsValid() == false) return;

      var saveType = (this.isInsert ? '등록' : '수정');
      eventBus.$on('callbackEventBus', this.fnProcSavePushTemplate);
      confirm.fnConfirm(this.componentsTitle, "푸시 템플릿을 "+saveType+"하시겠습니까?", "확인");
    },
    async fnProcSavePushTemplate(){
      //DATA Set
      var params = this.tmpltData;
      if(this.tmpltData.msgType != 'IMAGE') {
        params.imgUrl = '';
        params.fileId = '';
      }
      if(this.tmpltData.msgKind != 'A') {
        params.rcvblcNumber = '';
      }

      //저장처리
      await templateApi.savePushTmplt(params).then(response => {
        var result = response.data;
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
        this.$router.push('pushTemplateList')
      }
    }

  }
}
</script>