<template>
  <!-- content -->
  <div class="row row-no-margin">
    <div class="contentHeader">
      <h2>푸시 템플릿 관리</h2>
      <!-- <a href="#self" class="btnStyle1 absolute top0 right0" onClick="window.location.reload()" title="푸시 템플릿 등록/상세 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
    </div>

    <ImageManagePopUp :imgMngOpen.sync="imgMngOpen" :useCh="useCh" ref="imgMng"></ImageManagePopUp>
    <ImageUploadPopUp :imgUploadOpen.sync="imgUploadOpen"></ImageUploadPopUp>

    <!-- 본문 -->
    <div class="row">
      <div class="phone3 inline-block" style="width:30%">
        <div class="phoneFixed">
          <!-- phoneWrap -->
          <div class="phoneWrap">
            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
            <div class="phoneTextWrap">
              <div class="phoneText2">
                <p v-if="fnIsEmpty(rowData.tmpltTitle)">템플릿 제목</p>
                <p v-else>{{rowData.tmpltTitle}}</p>
              </div>
              <div v-if="rowData.msgType == 'IMAGE' && fnIsEmpty(rowData.imgUrl)" class="phoneText2 mt10 text-center" style="padding:65px">
                <i class="fas fa-image-polaroid" style="font-size:38px; color:#D5D5D5"></i>
                <p class="font-size14 color3 mt15">이미지 영역</p>
              </div>
              <div v-if="rowData.msgType == 'IMAGE' && !fnIsEmpty(rowData.imgUrl)" class="phoneText2 mt10 text-center"
                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.imgUrl+');'">
              </div>
              <p v-if="fnIsEmpty(rowData.tmpltContent) && (rowData.msgKind != 'A' || fnIsEmpty(rowData.rcvblcNumber))" class="font-size14 color4 mt10">템플릿 내용</p>
              <p v-else class="font-size14 color4 mt10">
                {{rowData.tmpltContent}}
                <br v-if="!fnIsEmpty(rowData.tmpltContent)"/>
                <span v-if="rowData.msgKind == 'A' && !fnIsEmpty(rowData.rcvblcNumber)">
                  {{rowData.rcvblcNumber}}
                </span>
              </p>
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
            <input type="text" class="inputStyle float-right" title="템플릿명 입력란" v-model="rowData.tmpltName" maxlength="50">
          </div>
        </div>
        <div class="of_h user-phone">
          <div class="float-left" style="width:28%">
            <h4>메시지타입 *</h4>
          </div>
          <div class="float-left" style="width:72%">
            <input type="radio" id="msgType_BASE" value="BASE" v-model="rowData.msgType">
            <label for="msgType_BASE" class="mr30">기본</label>
            <input type="radio" id="msgType_IMAGE" value="IMAGE" v-model="rowData.msgType">
            <label for="msgType_IMAGE">이미지</label>
          </div>
        </div>
        <div class="of_h user-phone">
          <div class="float-left" style="width:28%">
            <h4>메시지구분 *</h4>
          </div>
          <div class="float-left" style="width:72%">
            <input type="radio" id="msgKind_A" value="A" v-model="rowData.msgKind">
            <label for="msgKind_A" class="mr30">광고성</label>
            <input type="radio" id="msgKind_I" value="I" v-model="rowData.msgKind">
            <label for="msgKind_I">정보성</label>
          </div>
        </div>
        <div class="of_h user-phone">
          <div class="float-left" style="width:28%">
            <h4>타 프로젝트 사용여부  *</h4>
          </div>
          <div class="float-left" style="width:72%">
            <input type="radio" id="otherProjectUseYn_Y" value="Y" v-model="rowData.otherProjectUseYn">
            <label for="otherProjectUseYn_Y" class="mr30">공용</label>
            <input type="radio" id="otherProjectUseYn_N" value="N" v-model="rowData.otherProjectUseYn">
            <label for="otherProjectUseYn_N">전용</label>
          </div>
        </div>
        <div class="of_h user-phone">
          <div class="float-left" style="width:28%">
            <h4>제목</h4>
          </div>
          <div class="float-left" style="width:72%">
            <input type="text" class="inputStyle float-right" title="제목 입력란" id="tmpltTitle" name="tmpltTitle" v-model="rowData.tmpltTitle" maxlength="45">
          </div>
        </div>
        <div class="of_h user-phone">
          <div class="float-left" style="width:28%">
            <h4>내용 *</h4>
          </div>
          <div class="float-left" style="width:72%">
            <textarea class="textareaStyle height120" placeholder="" v-model="rowData.tmpltContent" maxlength="512"></textarea>
            <div v-if="rowData.msgKind == 'A'">
              <p class="color5">광고성 메시지 발송시, 자동으로 (광고)가 표시되오니, 내용에 (광고)문구는 입력하지 않아도 됩니다.</p>
              <input type="text" id="rcvblcNumber" name="rcvblcNumber" class="inputStyle float-right mt10" title="내용 입력란" v-model="rowData.rcvblcNumber" placeholder="설정 > 푸시 알림 설정 변경" maxlength="45">
              <p class="color5">푸시 수신거부 방법을 입력해주세요. 푸시 메시지에 (수신거부:거부 방법)이 포함됩니다.</p>
            </div>
          </div>
        </div>
        <div v-if="rowData.msgType == 'IMAGE'" class="of_h user-phone">
          <div class="float-left" style="width:28%">
            <h4>이미지</h4>
          </div>
          <div class="float-left" style="width:72%">
            <div class="of_h">
              <div class="float-left" style="width:25%">
                <a @click="fnOpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
              </div>
              <ul class="float-right attachList" style="width:74%; padding:5px 15px; height:30px;">
                <li><a @click="fnDelImg">{{fnSubString(rowData.imgUrl, 0, 35)}}  <i v-if="!fnIsEmpty(rowData.imgUrl)" class="fal fa-times"></i></a></li>
              </ul>
            </div>
          </div>
        </div>
        <div class="mt30 float-right">
          <a @click="fnSavePushTemplate" class="btnStyle2" title="저장">{{isInsert ? '등록' : '수정'}}</a>
          <router-link :to="{ name: 'pushTemplateList' }" tag="a" class="btnStyle2 backRed ml10">취소</router-link>
        </div>
      </div>
    </div>
  </div>
  <!-- //content -->
</template>

<script>
import TemplateApi from "@/modules/template/service/templateApi.js";
import ImageManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import ImageUploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";
import TokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  name: 'pushTemplateManage',
  components : {
    ImageManagePopUp,
    ImageUploadPopUp
  },
  props: {
    rowData : {
      type: Object,
      require: false,
      default: function() {
        return {'imgUrl':''}
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
      isInsert : true
    }
  },
  mounted() {
    this.fnSetTemplateInfo();
  },
  methods: {
    fnDelImg(){
      this.rowData.imgUrl = '';
      this.rowData.fileId = '';
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
      var params = this.rowData;
      TemplateApi.selectPushTmpltInfo(params).then(response => {
        var result = response.data;
        if(result.success) {
          result.data.forEach(function(obj){
            vm.rowData = obj;
          });
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
          this.rowData = {};
          this.isInsert = true;
        }
      });
    },
    //template 정보 조회
    fnSetTemplateInfo(){
      if(typeof(this.rowData.tmpltId) !== 'undefined' && this.rowData.tmpltId !== null){
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
    fnSetImageInfo(imgInfo) {
      this.rowData.imgUrl = imgInfo.chImgUrl;
      this.rowData.fileId = imgInfo.fileId;
    },
    //유효성 체크
    fnIsValid(){
      if(!this.rowData.tmpltName){
        confirm.fnAlert(this.componentsTitle, '템플릿명을 입력해주세요.');
        return false;
      }
      if(!this.rowData.msgType){
        confirm.fnAlert(this.componentsTitle, '메시지타입을 선택해주세요.');
        return false;
      }
      if(!this.rowData.msgKind){
        confirm.fnAlert(this.componentsTitle, '메시지구분을 선택해주세요.');
        return false;
      }
      if(!this.rowData.otherProjectUseYn){
        confirm.fnAlert(this.componentsTitle, '타 프로젝트 사용여부를 선택해주세요.');
        return false;
      }
      if(!this.rowData.tmpltTitle){
        confirm.fnAlert(this.componentsTitle, '제목을 입력해주세요.');
        return false;
      }
      if(!this.rowData.tmpltContent){
        confirm.fnAlert(this.componentsTitle, '내용을 입력해주세요.');
        return false;
      }
      if(this.rowData.msgKind == 'A' && !this.rowData.rcvblcNumber){
        confirm.fnAlert(this.componentsTitle, '푸시 수신거부 방법을 입력해주세요.');
        return false;
      }
      if(this.rowData.msgType == 'IMAGE' && !this.rowData.imgUrl){
        confirm.fnAlert(this.componentsTitle, '이미지를 선택해주세요.');
        return false;
      }
      if(this.rowData.msgType == 'IMAGE' && !this.rowData.fileId){
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
      var params = this.rowData;
      var loginId = TokenSvc.getToken().principal.userId;
      if(this.rowData.msgType != 'IMAGE') {
        params.imgUrl = '';
        params.fileId = '';
      }
      if(this.rowData.msgKind != 'A') {
        params.rcvblcNumber = '';
      }
      params.loginId = loginId;

      //저장처리
      await TemplateApi.savePushTmplt(params).then(response => {
        var result = response.data;
        if(result.success) {
          confirm.fnAlert(this.componentsTitle, '저장되었습니다.');
          //재조회
          if(this.isInsert){
            this.$router.push('pushTemplateList')
          }
        } else {
          alert(result.message);
        }
      });
    }

  }
}
</script>