<template>
  <div>
    <ImageManagePopUp :imgMngOpen.sync="imgMngOpen" :useCh="useCh" ref="imgMng"></ImageManagePopUp>
    <ImageUploadPopUp :imgUploadOpen.sync="imgUploadOpen"></ImageUploadPopUp>
    <hr/>
    <span>푸시 템플릿 등록/상세</span>
    <br/>
    <hr/>

    <span>템플릿명*</span>
    <input id="tmpltName" name="tmpltName" v-model="rowData.tmpltName" maxlength="50">
    <hr/>

    <span>메시지타입*</span>
    <br/>
    <input type="radio" id="msgType_BASE" value="BASE" v-model="rowData.msgType">
    <label for="msgType_BASE">기본</label>
    <input type="radio" id="msgType_IMAGE" value="IMAGE" v-model="rowData.msgType">
    <label for="msgType_IMAGE">이미지</label>
    <hr/>

    <span>메시지구분*</span>
    <br/>
    <input type="radio" id="msgKind_A" value="A" v-model="rowData.msgKind">
    <label for="msgKind_A">광고성</label>
    <input type="radio" id="msgKind_I" value="I" v-model="rowData.msgKind">
    <label for="msgKind_I">정보성</label>
    <hr/>

    <span>타 프로젝트 사용여부*</span>
    <br/>
    <input type="radio" id="otherProjectUseYn_Y" value="Y" v-model="rowData.otherProjectUseYn">
    <label for="otherProjectUseYn_Y">공용</label>
    <input type="radio" id="otherProjectUseYn_N" value="N" v-model="rowData.otherProjectUseYn">
    <label for="otherProjectUseYn_N">전용</label>
    <hr/>

    <span>제목*</span>
    <input id="tmpltTitle" name="tmpltTitle" v-model="rowData.tmpltTitle" maxlength="45">
    <hr/>

    <span>내용*</span>
    <textarea v-model="rowData.tmpltContent" maxlength="2000"></textarea>
    <hr/>

    <div v-if="rowData.msgKind == 'A'">
      <span>광고성 메시지 발송시, 자동으로 (광고)가 표시되오니, 내용에 (광고)문구는 입력하지 않아도 됩니다.</span>
      <input id="rcvblcNumber" name="rcvblcNumber" v-model="rowData.rcvblcNumber" placeholder="설정 > 푸시 알림 설정 변경" maxlength="45">
      <span>푸시 수신거부 방법을 입력해주세요. 푸시 메시지에 (수신거부:거부 방법)이 포함됩니다.</span>
      <hr/>
    </div>

    <span>부가정보(EXT)</span>
    <input id="adtnInfo" name="adtnInfo" v-model="rowData.adtnInfo" maxlength="45">
    <hr/>

    <div v-if="rowData.msgType == 'IMAGE'">
      <span>이미지</span>
      <button @click="fnOpenImageManagePopUp">이미지선택</button>
      <span class="ellipsis-3">{{rowData.imgUrl}}</span>
      <hr/>
    </div>

    <button @click="fnSavePushTemplate">{{isInsert ? '등록' : '수정'}}</button>
    <router-link :to="{ name: 'pushTemplateList' }" tag="button">취소</router-link>

  </div>
</template>

<script>
import TemplateApi from "@/modules/template/service/templateApi.js";
import ImageManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import ImageUploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";
import TokenSvc from '@/common/token-service';

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
        return {}
      }
    }
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
    //푸시 템플릿 정보 조회
    fnSelectPushTmpltInfo(){
      var vm = this;
      var params = this.rowData;
      TemplateApi.selectPushTmpltInfo(params).then(response => {
        var result = response.data;
        if(result.success) {
          result.data.forEach(function(obj, idx){
            vm.rowData = obj;
          });
        } else {
          alert(result.message);
          //TODO : 이전 페이지로
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
      this.rowData.imgUrl = imgInfo.ATTACH_FILE_PATH;
    },
    //유효성 체크
    fnIsValid(){
      if(!this.rowData.tmpltName){
        alert('템플릿명을 입력해주세요.');
        return false;
      }
      if(!this.rowData.msgType){
        alert('메시지타입을 선택해주세요.');
        return false;
      }
      if(!this.rowData.msgKind){
        alert('메시지구분을 선택해주세요.');
        return false;
      }
      if(!this.rowData.otherProjectUseYn){
        alert('타 프로젝트 사용여부를 선택해주세요.');
        return false;
      }
      if(!this.rowData.tmpltTitle){
        alert('제목을 입력해주세요.');
        return false;
      }
      if(!this.rowData.tmpltContent){
        alert('내용을 입력해주세요.');
        return false;
      }
      if(this.rowData.msgKind == 'A' && !this.rowData.rcvblcNumber){
        alert('푸시 수신거부 방법을 입력해주세요.');
        return false;
      }
      if(!this.rowData.adtnInfo){
        alert('부가정보를 입력해주세요.');
        return false;
      }
      if(this.rowData.msgType == 'IMAGE' && !this.rowData.imgUrl){
        alert('이미지를 선택해주세요.');
        return false;
      }
      return true;
    },
    //저장
    async fnSavePushTemplate(){
      //유효성 검사
      if(this.fnIsValid() == false) return;

      var saveType = (this.isInsert ? '등록' : '수정');
      if(confirm('푸시 템플릿을 '+saveType+'하시겠습니까?') == false) return;

      //DATA Set
      var params = this.rowData;
      var loginId = TokenSvc.getToken().principal.userId;
      if(this.rowData.msgType != 'IMAGE') {
        params.imgUrl = '';
      }
      if(this.rowData.msgKind != 'A') {
        params.rcvblcNumber = '';
      }
      params.loginId = loginId;


      //저장처리
      await TemplateApi.savePushTmplt(params).then(response => {
        var result = response.data;
        if(result.success) {
          alert("성공하였습니다.");
          //재조회
          console.log(result);
        } else {
          alert(result.message);
        }
      });
    }
  }
}
</script>

<style lang="scss">
@mixin ellipsis($line-cnt, $line-height) {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: $line-cnt; /* 라인수 */
  -webkit-box-orient: vertical;
  word-wrap:break-word;
  line-height: $line-height;
  height: $line-height * $line-cnt; /* line-height 가 1.2em 이고 3라인을 자르기 때문에 height는 1.2em * 3 = 3.6em */
}

.ellipsis-3 {
  @include ellipsis(1, 1.3em);
}

input, table, th, td {
  border: 1px solid black;
}
</style>