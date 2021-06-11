<template>
  <div v-if="imgPreviewOpen" @click.self="fnClose" class="modalStyle" id="image-add" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:785px">
      <div class="modal-content">
        <div class="modal-body" style="text-align: center;">
          <img :src="imageUrl" style="top:0;left:0;max-width: 100%;height:auto;"/>
        </div>
        <div class="text-center mt10 mb10">
          <a @click="fnClose" class="btnStyle3 white font14" data-dismiss="modal" title="닫기">닫기</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "imageUploadPopup",
  props: {
    imgPreviewOpen: {
      type: Boolean,
      require: true,
      default: false
    }
  },
  data() {
    return {
      imageUrl : ''
    }
  },
  methods: {
    //이미지 파일 열기
    fnOpenImg(imageUrl){
      this.imageUrl = imageUrl;
      //this.imageUrl = require('@/assets/images/uploadImage/20210405_6596874356127434594.jpg')
    },
    //파일업로드팝업 초기화
    fnClose(){
      this.$emit('update:imgPreviewOpen', false)
    },
  }
}
</script>