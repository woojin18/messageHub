<template>
  <div v-if="imgUploadOpen" @click.self="fnClose" class="modalStyle" id="image-add" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:785px">
      <div class="modal-content">
        <div class="modal-body">
          <h2>이미지 업로드</h2>
          <hr>
          <div class="of_h">
            <h4>이미지 사용채널</h4>
          </div>
          <div class="consolCheck" style="width:100%">
            <input type="checkbox" id="imgPush" class="checkStyle2" value="PUSH" v-model="chkboxUseCh">
            <label for="imgPush" class="mr10">푸시</label>
            <input type="checkbox" id="imgRCS" class="checkStyle2" value="RCS" v-model="chkboxUseCh">
            <label for="imgRCS" class="mr10">RCS</label>
            <input type="checkbox" id="imgtalk" class="checkStyle2" value="FRIENDTALK" v-model="chkboxUseCh">
            <label for="imgtalk" class="mr10">친구톡</label>

            <select v-if="chkboxUseCh.includes('FRIENDTALK')" v-model="wideYn" class="selectStyle1 vertical-middle mr10" style="width:15%">
              <option value="N">Normal</option>
              <option value="Y">Wide</option>
            </select>

            <input type="checkbox" id="imgMMS1" class="checkStyle2" value="MMS" v-model="chkboxUseCh">
            <label for="imgMMS1" class="mr10">MMS</label>
          </div>

          <div class="border-line mt10" style="width:100%;">
            <p v-if="!imageUrl" class="color5 text-center" style="margin:130px 0;">이미지 영역</p>
            <img v-if="imageUrl" :src="imageUrl" style="top:0;left:0;width:700px;height:260px;object-fit: contain;"/>
          </div>

          <div class="text-center mt20">
            <input ref="imageInput" type="file" @change="fnImagePreview" style="display:none;">
            <a @click="fnFindImgFile" class="btnStyle1 backBlack mr5" title="파일찾기" v-if="$route.meta.isSave">파일찾기</a>
            <a @click="fnUploadFile" class="btnStyle1 backBlack mr5" title="업로드" v-if="$route.meta.isSave">업로드</a>
            <a @click="fnClose" class="btnStyle1 backWhite" title="닫기">닫기</a>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import commonUtilApi from "@/modules/commonUtil/service/commonUtilApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js"

export default {
  name: "imageUploadPopup",
  props: {
    imgUploadOpen: {
      type: Boolean,
      require: true,
      default: false
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '이미지 업로드';
      }
    }
  },
  data() {
    return {
        files: [],
        chkboxUseCh: [],
        imageUrl: '',
        wideYn: 'N',
        inProgress: false
    }
  },
  methods: {
    //팝업 초기화
    fnClose(){
      this.files = [];
      this.chkboxUseCh = [];
      this.imageUrl = '';
      this.wideYn = 'N';
      this.inProgress = false;
      this.$emit('update:imgUploadOpen', false)
    },
    //체크값 json형식으로 변환
    fnGetUsechJson(){
      var chkedObj = {};
      this.chkboxUseCh.forEach(function(v){
        chkedObj[v] = 'Y';
      });
      return JSON.stringify(chkedObj);
    },

    //파일업로드
    async fnUploadFile() {
      if(this.inProgress){
        confirm.fnAlert(this.componentsTitle, '파일업로드 처리중입니다.');
        return;
      }

      const uploadFile = this.$refs.imageInput;

      //유효성체크
      //사용채널 선택 확인
      if(this.chkboxUseCh.length == 0){
        confirm.fnAlert(this.componentsTitle, '사용채널을 선택해주세요.');
        return;
      }
      //이미지파일 선택 확인
      if(uploadFile.value == 0){
        confirm.fnAlert(this.componentsTitle, '파일을 등록해주세요.');
        return;
      }
      //확장자 검사
      const permitExten = 'jpg,jpeg,png'.split(',');
      const extnIdx = uploadFile.value.lastIndexOf('.');
      const extn = uploadFile.value.substring(extnIdx+1).toLowerCase();

      if((permitExten.indexOf(extn) < 0) || (this.chkboxUseCh.includes('MMS') && extn == 'png')){
        confirm.fnAlert(this.componentsTitle, '허용되지 않는 확장자입니다.\n(이미지는 .jpg, .jpeg, .png(MMS 제외) 확장자 지원합니다.)');
        return;
      }

      const uploadInfo = {
        useCh: this.chkboxUseCh,
        wideYn: this.wideYn
      };

      let fd = new FormData();
      fd.append('file', uploadFile.files[0]);
      fd.append('paramString', JSON.stringify(uploadInfo));

      this.inProgress = true;
      const vm = this;
      await commonUtilApi.uploadImage(fd).then(response =>{
        this.inProgress = false;
        const result = response.data;
        if(result.success) {
          confirm.fnAlert(this.componentsTitle, '등록되었습니다.');
          this.$parent.fnSearch();
          this.fnClose();
        } else {
          if(result.message !== null || typeof(result.message) !== 'undefined') {
            confirm.fnAlert(this.componentsTitle, result.message);
          } else {
            confirm.fnAlert(this.componentsTitle, '등록에 실패했습니다.');
          }
        }
      }).catch(function () {
        vm.inProgress = false;
      });
    },
    //파일찾기 클릭시
    fnFindImgFile() {
      this.$refs.imageInput.click();
    },
    //이미지미리보기 클릭시
    fnImagePreview(e) {
      if(e.target.files && e.target.files.length > 0){
        var file = e.target.files[0];
        this.imageUrl = URL.createObjectURL(file);
      } else {
        this.imageUrl = '';
      }
    }
    
  }
}
</script>