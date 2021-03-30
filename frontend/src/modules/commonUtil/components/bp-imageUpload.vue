<template>
  <div class="imageUploadPopup"
    v-if="imgUploadOpen">
    <div class="imageUploadPopup__dialog">
      <hr/>
      <span>이미지 업로드</span>
      <br/>
      <hr/>
      <span>이미지 사용채널</span>
      <br/>
      <input type="checkbox" id="push" value="PUSH" v-model="chkboxUseCh">
      <label for="push">푸시</label>
      <input type="checkbox" id="rcs" value="RCS" v-model="chkboxUseCh">
      <label for="rcs">RCS</label>
      <input type="checkbox" id="friendtalk" value="FRIENDTALK" v-model="chkboxUseCh">
      <label for="friendtalk">친구톡</label>
      <input type="checkbox" id="mms" value="MMS" v-model="chkboxUseCh">
      <label for="mms">MMS</label>
      <hr/>


      <hr/>
      <input ref="imageInput" type="file" hidden @change="fnImagePreview">
      <div style="width: 500px; height: 250px">
        <img v-if="imageUrl" :src="imageUrl" style="width: 500px; height: 250px; object-fit: contain;"/>
        <!-- <img :src="require('@/img/uploadImg/20210329_4518486852014528442.jpg')" style="width: 500px; height: 250px; object-fit: contain;"/> -->
      </div>
      <hr/>

      <button type="button" @click="fnFindImgFile">파일찾기</button>
      <button @click="fnUploadFile">업로드</button>
      <button @click="fnUplaodCancle" color="primary" style="background-color : #1976d2 !important">취소</button>
      <hr/>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import tokenSvc from '@/common/token-service';

export default {
  name: "imageUploadPopup",
  props: {
    imgUploadOpen: {
      type: Boolean,
      require: true,
      default: false
    }
  },
  data() {
    return {
        files: [],
        chkboxUseCh: [],
        imageUrl: null,
    }
  },
  methods: {
    //팝업 초기화
    fnClose(){
      this.files = [];
      this.chkboxUseCh = [];
      this.imageUrl = null;
      this.$emit('update:imgUploadOpen', false)
    },
    fnUplaodCancle(){
      this.fnClose();
      this.$parent.fnOpenImageManagePopUp();
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
        alert("사용채널을 선택해주세요.");
        return;
      }
      //이미지파일 선택 확인
      if(uploadFile.value == 0){
        alert("파일을 등록해주세요.");
        return;
      }
      //확장자 검사

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
        console.log(response.data);

        if(response.data != null && response.data.success){
          alert("등록되었습니다.");
          // 창닫기
          // TODO: 로컬에서 이미지 파일 업로드 후 이미지읽어 오는 속도가 프로젝트 갱신속도가 빨라서 임시 주석
          //this.fnUplaodCancle();
        } else {
          if(typeof(response.data.message) !== 'undefined' || response.data.message !== null) {
            alert(response.data.message);
          } else {
            alert("등록에 실패했습니다.");
          }
        }
        
      })
      .catch(function () {
        alert("등록에 실패했습니다.");
      });

    },


    //파일찾기 클릭시
    fnFindImgFile() {
      this.$refs.imageInput.click();
    },
    //이미지미리보기 클릭시
    fnImagePreview(e) {
      //console.log(e.target.files)
      const file = e.target.files[0]; // Get first index in files
      this.imageUrl = URL.createObjectURL(file); // Create File URL
    }


  }
}
</script>

<style lang="scss">
$module: 'imageUploadPopup';
.#{$module} {
  // This is modal bg
  background-color: rgba(0,0,0,.7);
  top: 0; right: 0; bottom: 0; left: 0;
  position: fixed;
  overflow: auto;
  margin: 0;
  z-index: 9999;
  //This is modal layer
  &__dialog{
    left: 30%;
    top: 15%;
    width: 600px;
    position: absolute;
    background: #fff;
    margin-bottom: 50px;
  }

  &__header {
    font-size: 28px;
    font-weight: bold;
    line-height: 1.29;
    padding: 16px 16px 0 25px;
    position: relative;
  }
  &__body {
    padding: 25px;
    min-height: 150px;
    max-height: 412px;
    overflow-y: scroll;
  }
  &__btn {
    text-align: right; 
    padding-top: 10px;
  }
}
.example-drag .drop-active {
top: 0;
bottom: 0;
right: 0;
left: 0;
position: fixed;
z-index: 9999;
opacity: .6;
text-align: center;
width:100%;
background: #000;
}
.example-drag .drop-active h3 {
margin: -.5em 0 0;
position: absolute;
top: 50%;
left: 0;
right: 0;
-webkit-transform: translateY(-50%);
-ms-transform: translateY(-50%);
transform: translateY(-50%);
font-size: 40px;
color: #fff;
padding: 0;
}

input[type="checkbox"] {
  width: auto;
  height: auto;
  -webkit-appearance: auto;
}
</style>