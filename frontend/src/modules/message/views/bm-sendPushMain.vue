<template>
  <div>
    <ImageManagePopUp :imgMngOpen.sync="imgMngOpen" :useCh="useCh" ref="imgMng"></ImageManagePopUp>
    <ImageUploadPopUp :imgUploadOpen.sync="imgUploadOpen"></ImageUploadPopUp>
    <hr/>
    <span>푸시 발송</span>
    <br/>
    <button @click="fnOpenImageManagePopUp">이미지선택</button>
    <!-- 
    <br/>
    <button @click="fnOpenImageUploadPopUp">이미지등록</button>
    -->
    <hr/>

    

  </div>
</template>

<script>
import ImageManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import ImageUploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";

export default {
  name: "sendPushMain",
  components : {
    ImageManagePopUp,
    ImageUploadPopUp
  },
  data() {
    return {
      imgMngOpen : false,
      imgUploadOpen : false,
      useCh : 'PUSH',
    }
  },
  methods: {
    fnOpenImageManagePopUp(){
      this.$refs.imgMng.fnSearch();
      this.imgMngOpen = !this.imgMngOpen;
    },
    fnOpenImageUploadPopUp : function(){
      this.imgUploadOpen = !this.imgUploadOpen;
    },
    fnSetImageInfo(imgInfo) {
      console.log(imgInfo);
      alert(imgInfo.ORIGIN_FILE_NAME);
    }
  }
}
</script>