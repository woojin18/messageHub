<template>
  <div v-if="imgUploadOpen" @click.self="fnClose" class="modalStyle" id="image-add" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:785px">
      <div class="modal-content">
        <div class="modal-body">
          <h5 class="lc-1">이미지 업로드</h5>
          <hr>
          <div class="of_h">
            <p class="font-size14 color000 inline-block mt15" style="font-weight:700">이미지 사용채널</p>
          </div>
          <div class="consolCheck vertical-top mt20" style="width:100%">
            <input type="checkbox" id="imgPush" class="checkStyle2" value="PUSH" v-model="chkboxUseCh">
            <label for="imgPush" class="font-size16" style="width:13%">푸시</label>
            <input type="checkbox" id="imgRCS" class="checkStyle2" value="RCS" v-model="chkboxUseCh">
            <label for="imgRCS" class="font-size16" style="width:13%">RCS</label>
            <input type="checkbox" id="imgtalk" class="checkStyle2" value="FRIENDTALK" v-model="chkboxUseCh">
            <label for="imgtalk" class="font-size16" style="width:13%">친구톡</label>
            <input type="checkbox" id="imgMMS1" class="checkStyle2" value="MMS" v-model="chkboxUseCh">
            <label for="imgMMS1" class="font-size16" style="width:13%">MMS</label>
          </div>

          <div class="border-line mt20" style="width:100%;">
            <p v-if="!imageUrl" class="font-size13 color5 text-center" style="margin:130px 0;">이미지 영역</p>
            <img v-if="imageUrl" :src="imageUrl" style="top:0;left:0;width:700px;height:260px;object-fit: contain;"/>
          </div>

          <div class="text-center mt60">
            <input ref="imageInput" type="file" @change="fnImagePreview" style="display:none;">
            <a @click="fnFindImgFile" class="btnStyle3 black font14 mr5" title="파일찾기">파일찾기</a>
            <a @click="fnUploadFile" class="btnStyle3 black font14 mr5" title="업로드">업로드</a>
            <a @click="fnClose" class="btnStyle3 white font14" data-dismiss="modal" title="닫기">닫기</a>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import tokenSvc from '@/common/token-service';
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
    }
  },
  methods: {
    //팝업 초기화
    fnClose(){
      this.files = [];
      this.chkboxUseCh = [];
      this.imageUrl = '';
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
      var uploadFile = this.$refs.imageInput;

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
      const permitExten = 'jpg,jpeg,png,gif'.split(',');
      const extnIdx = uploadFile.value.lastIndexOf('.');
      const extn = uploadFile.value.substring(extnIdx+1);
      if((permitExten.indexOf(extn) < 0)){
        confirm.fnAlert(this.componentsTitle, '허용되지 않는 확장자입니다.');
        return;
      }

      var fd = new FormData();
      fd.append('uploadFile', uploadFile.files[0]);
      fd.append('useCh', this.fnGetUsechJson());
      fd.append('loginId', tokenSvc.getToken().principal.userId);

      await axios.post('/api/public/common/uploadImage',
        fd, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }
      ).then( response => {
        if(response.data != null && response.data.success){
          confirm.fnAlert(this.componentsTitle, '등록되었습니다.');
          this.$parent.fnSearch();
          this.fnClose();
        } else {
          if(typeof(response.data.message) !== 'undefined' || response.data.message !== null) {
            confirm.fnAlert(this.componentsTitle, 'response.data.message');
          } else {
            confirm.fnAlert(this.componentsTitle, '등록에 실패했습니다.');
          }
        }
        
      })
      .catch(function () {
        confirm.fnAlert(this.componentsTitle, '등록에 실패했습니다.');
      });
    },
    //파일찾기 클릭시
    fnFindImgFile() {
      this.$refs.imageInput.click();
    },
    //이미지미리보기 클릭시
    fnImagePreview(e) {
      var file = e.target.files[0]; // Get first index in files
      this.imageUrl = URL.createObjectURL(file); // Create File URL
    }
  }
}
</script>