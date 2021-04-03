<template>
  <div class="imageManagePopup"
    v-if="imgMngOpen"
    @click.self="fnClose">
    <div class="imageManagePopup__dialog">
      <hr/>
      <span>통합 이미지 관리</span>
      <br/>
      <hr/>
      <button @click="fnAddImg">이미지 추가</button>
      <button @click="fnAddImg">삭제</button>
      <button @click="fnClose" color="primary" style="background-color : #1976d2 !important">Close</button>
      <hr/>

      <!-- 이미지 리스트 -->
      <div>
        <table id="list">
          <thead>
            <tr>
              <th>File ID</th>
              <th>파일명</th>
              <th>사용채널</th>
              <th>업로드 일시</th>
              <th>미리보기</th>
              <th>선택</th>
            </tr>
          </thead>
          <tbody id="contants">
            <tr v-for="(contant, idx) in contants" v-bind:key="contant">
              <td>{{contant.IMAGE_FILE_SEQ}}</td>
              <td>{{contant.ORIGIN_FILE_NAME}}</td>
              <td>{{contant.CORP_ID}}</td>
              <td>{{contant.REG_DT}}</td>
              <td>
                <!-- <img :src="contant.IMAGE_FILE_PATH" style="width: 30px; height: 30px; object-fit: contain;"/> -->

                <!-- <img :src="require('@/assets/images/msgSendImg/20210325_266588433653732025.jpg')" style="width: 500px; height: 250px; object-fit: contain;"/> -->
                <img :src="contant.imageFullPath" style="width: 30px; height: 30px; object-fit: contain;"/>
              </td>
              <td><button @click="fnSelectImage(idx)">선택</button></td>
            </tr>
          </tbody>
        </table>
      </div>
      <!--// 이미지 리스트 -->

    </div>
  </div>
</template>

<script>
import CommonUtilApi from "@/modules/commonUtil/service/commonUtilApi.js";

export default {
  name: "imageManagePopup",
  props: {
    useCh: String,
    imgMngOpen: {
      type: Boolean,
      require: true,
      default: false,
      contants: [],
      imagePath: []
    }
  },
  mounted() {
    this.fnSearch();
  },
  methods: {
    // 이미지 선택
    fnSelectImage(idx){
      this.fnClose();
      this.$parent.fnSetImageInfo(this.contants[idx]);
    },
    // 조회
    async fnSearch(){
      var params = {'use_ch_info':this.useCh}
      await CommonUtilApi.selectImageList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.contants = result.data;
        }

        this.contants.forEach(function(obj){
          //TODO : 이미지 서버로 업로드 위치 변경되서 수정
          //obj['imageFullPath'] = require("@/assets/images/uploadImage/"+obj.IMAGE_FILE_NAME);
          obj['imageFullPath'] = obj.IMAGE_FILE_PATH;
        });
      });
    },
    //파일업로드팝업 초기화
    fnClose(){
      this.$emit('update:imgMngOpen', false)
    },
    //이미지 추가 버튼 클릭시
    fnAddImg : function(){
      this.fnClose();
      this.$parent.fnOpenImageUploadPopUp();
    }
  },
}
</script>

<style lang="scss">
$module: 'imageManagePopup';
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
</style>