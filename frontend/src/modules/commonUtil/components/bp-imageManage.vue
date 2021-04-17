<template>
  <div v-if="imgMngOpen" @click.self="fnClose" class="modalStyle" id="image" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:785px">
      <div class="modal-content">
        <div class="modal-body">
          <h5 class="lc-1">통합 이미지 관리</h5>
          <hr>
          <div class="of_h">
            <p class="font-size14 color000 inline-block mt15" style="font-weight:700">업로드 한 이미지</p>
            <div class="float-right">
              <a @click="fnOpenImageUploadPopUp" class="btnStyle3 black font-size14 width120" title="이미지 추가">이미지 추가</a>
              <a @click="fnDeleteImage" class="btnStyle3 gray font-size14 width120" title="삭제">삭제</a>
            </div>
          </div>

          <div class="row mt10">
            <div class="col-xs-12">
              <!-- table -->
              <table class="table_skin1_1 mt20" style="width:100%">
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
                      <input type="checkbox" :id="'listCheck_'+idx" class="boardCheckStyle" :value="contant.imageFileSeq" v-model="listChkBox">
                      <label :for="'listCheck_'+idx"></label>
                    </td>
                    <td class="text-center">{{contant.imageFileSeq}}</td>
                    <td>{{contant.originFileName}}</td>
                    <td>{{contant.useChInfo}}</td>
                    <td>{{contant.regDt}}</td>
                    <td class="text-center"><a @click="fnOpenImagePreviewPopUp(contant.imageFilePath)" title="미리보기"><i class="far fa-search"></i></a></td>
                    <td class="text-center end">
                      <a @click="fnSelectImage(idx)" class="btnStyle6 vertical-middle" style="min-width:auto; width:100%" title="선택">선택</a>
                    </td>
                  </tr>
                </tbody>
              </table>
              <!-- //table -->
            </div>
          </div>

          <!-- pagination -->
          <div id="pageContent">
            <PageLayer @fnClick="fnClick" :listTotalCnt="totCnt" :selected="listSize" ref="updatePaging"></PageLayer>
          </div>
          <!-- //pagination -->

          <div class="border-line2 bgColor_f7 mt30" style="padding:20px">
            <ul class="font-size13 color5 line-height2em">
              <li>•  이미지는 .jpg, .jpeg, .png, .gif 형식만 지원합니다.</li>
              <li>•  이미지 크기는 일반 이미지 5MB 이하여야합니다. </li>
              <li>•  이미지는 가로 길이는 최소 500px, 세로 길이는 최소 250px 이상이어야합니다.</li>
              <li>•  이미지의 가로:세로 비율은 2:1 이상, 3:4 이하여야 합니다.</li>
            </ul>
          </div>

          <div class="text-center mt60">
            <a @click="fnClose" class="btnStyle3 white font14" data-dismiss="modal" title="닫기">닫기</a>
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
import confirm from "@/modules/commonUtil/service/confirm.js"
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
      this.$parent.fnSetImageInfo(this.contants[idx]);
    },
    // 조회
    async fnSearch(){
      var vm = this;
      var params = {
        'corpId':'TEST_CORP_ID',  //TODO : 로그인 완료되면 corp_id 가져오자
        'useChInfo':this.useCh,
        'pageNo':this.pageNo,
        'listSize':this.listSize
      };

      await CommonUtilApi.selectImageList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.contants = result.data;
          this.totCnt = result.pageInfo.totCnt;
        }

        this.contants.forEach(function(obj){
          //TODO : 이미지 서버로 업로드 위치 변경되서 수정
          //obj['imageFullPath'] = require("@/assets/images/uploadImage/"+obj.imageFileName);
          obj['imageFullPath'] = obj.imageFilePath;
          obj['useChInfo'] = vm.fnSetUseChInfo(obj.useChInfo);
        });
      });
    },
    //사용채널 json -> string, code -> codeName
    fnSetUseChInfo(jsonStr){
      var useChStr = '';
      var useChObj = JSON.parse(jsonStr);
      var mappingCdNm = {
        'PUSH':'푸시',
        'RCS':'RCS',
        'FRIENDTALK':'친구톡',
        'MMS':'MMS'
      };

      try {
        Object.keys(useChObj).forEach(function(key){
          if(useChObj[key]) useChStr += (useChStr == '' ? '' : ', ') + mappingCdNm[key];
        });
      } catch (error) {
        useChStr = '';
      }
      return useChStr;
    },
    //파일업로드팝업 초기화
    fnClose(){
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
      var vm = this;
      if(this.listAllChecked){
        this.contants.forEach(function(contant){
          vm.listChkBox.push(contant.imageFileSeq);
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
      var params = {
        'imageFileSeqs':this.listChkBox,
        'corpId':'TEST_CORP_ID',  //TODO : 로그인 완료되면 corp_id 가져오자
        'useChInfo':this.useCh,
      };
      await CommonUtilApi.deleteImage(params).then(response =>{
        var result = response.data;
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