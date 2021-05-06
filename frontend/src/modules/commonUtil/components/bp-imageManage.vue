<template>
  <div v-if="imgMngOpen" @click.self="fnClose" class="modalStyle" id="image" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:785px">
      <div class="modal-content">
        <div class="modal-body">
          <h2>통합 이미지 관리</h2>
          <hr>
          <div class="of_h h4Minus">
            <h4 class="color000 inline-block">업로드 한 이미지</h4>
            <div class="float-right h4Button">
              <a @click="fnOpenImageUploadPopUp" class="btnStyle1 backBlack mr10" title="이미지 추가">이미지 추가</a>
              <a @click="fnDeleteImage" class="btnStyle1 backLightGray" title="삭제">삭제</a>
            </div>
          </div>

          <div class="row consolMarginTop">
            <div class="col-xs-12">
              <!-- table -->
              <table class="table_skin1" style="width:100%">
                <caption>수신자 추가의 사용자명, 아이디, 이용권한을 제공하는 표</caption>
                <colgroup>
                <col style="width:3%">
                <col style="width:10%">
                <col style="width:10%">
                <col style="width:18%">
                <col style="width:20%">
                <col>
                <col style="width:16%">
                </colgroup>
                <thead>
                <tr>
                  <th class="text-center lc-1">
                    <input type="checkbox" id="listCheck_all" class="boardCheckStyle" @change="fnListChkAll" v-model="listAllChecked">
                    <label for="listCheck_all"></label>
                  </th>
                  <th class="text-center lc-1">File ID</th>
                  <th class="text-center lc-1">파일명</th>
                  <th class="text-center lc-1">사용채널</th>
                  <th class="text-center lc-1">업로드일시</th>
                  <th class="text-center lc-1">미리보기</th>
                  <th class="text-center lc-1 end">선택</th>
                </tr>
                </thead>
                <tbody>
                  <tr v-for="(contant, idx) in contants" :key="idx">
                    <td class="text-center">
                      <input type="checkbox" :id="'listCheck_'+idx" class="boardCheckStyle" :value="contant.fileId" v-model="listChkBox">
                      <label :for="'listCheck_'+idx"></label>
                    </td>
                    <td class="text-center">{{contant.fileId}}</td>
                    <td class="text-left">{{contant.chImgNm}}</td>
                    <td class="text-left">{{contant.useChInfo}}</td>
                    <td class="text-center">{{contant.regDt}}</td>
                    <td class="text-center"><a @click="fnOpenImagePreviewPopUp(contant.chImgUrl)" title="미리보기"><i class="far fa-search"></i></a></td>
                    <td class="text-center end">
                      <a @click="fnSelectImage(idx)" class="btnStyle6 vertical-middle" style="min-width:auto; width:100%" title="선택">선택</a>
                    </td>
                  </tr>
                  <tr v-if="contants.length == 0">
                    <td class="text-center">
                      <input type="checkbox" :id="'listCheck_1'" class="boardCheckStyle">
                      <label :for="'listCheck_1'"></label>
                    </td>
                    <td class="text-center" colspan="6">업로드 한 이미지가 없습니다.</td>
                  </tr>
                </tbody>
              </table>
              <!-- //table -->
            </div>
          </div>

          <!-- pagination -->
          <div id="pageContent">
            <PageLayer @fnClick="fnClick" :listTotalCnt="totCnt" :selected="listSize" pageDivClass="row mt10" ref="updatePaging"></PageLayer>
          </div>
          <!-- //pagination -->

          <div class="border-line2 bgColor_f7 mt10 pd10">
            <ul class="color5">
              <li>•  이미지는 .jpg, .jpeg, .png, .gif 형식만 지원합니다.</li>
              <li>•  이미지 크기는 PUSH(1MB), RCS(1MB), 친구톡(Normal-500KB, Wide-2MB), MMS(300KB) 이하여야합니다. </li>
              <li>•  이미지는 가로 길이는 최소 500px, 세로 길이는 최소 250px 이상이어야합니다.</li>
              <li>•  이미지의 가로:세로 비율은 2:1 이상, 3:4 이하여야 합니다.</li>
            </ul>
          </div>

          <div class="text-center mt20">
            <a @click="fnClose" class="btnStyle1 backWhite" data-dismiss="modal" title="닫기">닫기</a>
          </div>

        </div>
      </div>
    </div>
    <ImageUploadPopUp :imgUploadOpen.sync="imgUploadOpen" ref="imgUpload"></ImageUploadPopUp>
    <ImagePreview :imgPreviewOpen.sync="imgPreviewOpen" ref="imgPreview"></ImagePreview>
  </div>
</template>

<script>
import PageLayer from '@/components/PageLayer.vue';
import CommonUtilApi from "@/modules/commonUtil/service/commonUtilApi.js";
import ImageUploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";
import ImagePreview from "@/modules/commonUtil/components/bp-imagePreview.vue";
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  name: "imageManagePopup",
  components : {
    ImageUploadPopUp,
    ImagePreview,
    PageLayer
  },
  props: {
    useCh: String,
    imgMngOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '통합 이미지 관리';
      }
    },
  },
  data() {
    return {
      contants: [],
      imgUploadOpen : false,
      imgPreviewOpen: false,
      listAllChecked: false,
      listChkBox: [],
      totCnt : 0,  //전체 리스트 수
      listSize : 10,  // select 박스 value (출력 갯수 이벤트)
      pageNo : 1,  // 현재 페이징 위치
    }
  },
  mounted() {
    if(this.imgMngOpen) this.fnSearch();
  },
  methods: {
    // 이미지 선택
    fnSelectImage(idx){
      this.fnClose();
      this.$emit('img-callback',this.contants[idx]);
    },
    // 조회
    async fnSearch(){
      const vm = this;
      const params = {
        'useChInfo':this.useCh,
        'pageNo':this.pageNo,
        'listSize':this.listSize
      };

      await CommonUtilApi.selectImageList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          this.contants = result.data;
          this.totCnt = result.pageInfo.totCnt;
        }

        this.contants.forEach(function(obj){
          obj['useChInfo'] = vm.fnSetUseChInfo(obj.useChInfo);
        });
      });
    },
    //사용채널 json -> string, code -> codeName
    fnSetUseChInfo(jsonStr){
      let useChStr = '';
      const useChArray = JSON.parse(jsonStr);
      const mappingCdNm = {
        'PUSH':'푸시',
        'RCS':'RCS',
        'FRIENDTALK':'친구톡',
        'MMS':'MMS'
      };

      try {
        useChArray.forEach(function(key){
          if(mappingCdNm[key]) useChStr += (useChStr == '' ? '' : ', ') + mappingCdNm[key];
        });
      } catch (error) {
        useChStr = '';
      }

      return useChStr;
    },
    //파일업로드팝업 초기화
    fnClose(){
      this.$refs.updatePaging.fnAllDecrease();
      this.$emit('update:imgMngOpen', false)
    },
    //이미지 추가 버튼 클릭시
    fnOpenImageUploadPopUp() {
      this.imgUploadOpen = !this.imgUploadOpen;
    },
    //이미지 미리보기 버튼 클릭시
    fnOpenImagePreviewPopUp(imgUrl) {
      this.$refs.imgPreview.fnOpenImg(imgUrl);
      this.imgPreviewOpen = !this.imgPreviewOpen;
    },
    //리스트 전체 체크박스
    fnListChkAll(){
      const vm = this;
      if(this.listAllChecked){
        this.contants.forEach(function(contant){
          vm.listChkBox.push(contant.fileId);
        });
      } else {
        this.listChkBox = [];
      }
    },
    // page 선택
    fnClick(pageNo) {
      this.pageNo = pageNo;
      this.fnSearch();
    },
    //이미지 삭제
    fnDeleteImage(){
      //유효성 검사
      if(this.listChkBox == null || this.listChkBox.length == 0){
        confirm.fnAlert(this.componentsTitle, '삭제할 항목을 선택해주세요.');
        return;
      }

      eventBus.$on('callbackEventBus', this.fnProcDeleteImage);
      confirm.fnConfirm(this.componentsTitle, "선택한 이미지를 삭제하시겠습니까?", "확인");
    },
    //이미지 삭제 처리
    async fnProcDeleteImage(){
      const params = {
        'fileIds':this.listChkBox,
        'useChInfo':this.useCh,
      };
      await CommonUtilApi.deleteImage(params).then(response =>{
        const result = response.data;
        if(result.success) {
          confirm.fnAlert(this.componentsTitle, '삭제되었습니다.');
          this.listChkBox = [];
          this.fnSearch();
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    }
  }
}
</script>
<style lang="scss">
$module: 'modalStyle';
.#{$module} {
  // This is modal bg
  background-color: rgba(0,0,0,.7);
  top: 0; right: 0; bottom: 0; left: 0;
  position: fixed;
  overflow: auto;
  margin: 0;
  z-index: 9999;
}
</style>