<template>
  <div>
    <CsMenuSubHeader></CsMenuSubHeader>

    <div id="contentWrap">
      <div class="sub03_conWrap">
        <div class="con01 wow animated fadeInUp">
          <section>    
            <div class="boardTitle">
              <p class="titleText">자료실</p>
              <p class="subText">서비스 이용에 관련된 자료들을 제공합니다.</p>
            </div>

            <div class="boardWrite">
              <div class="title">
                <p>{{libraryData.title}}</p>
                <span class="date">{{libraryData.regDt}}</span>
              </div>
              <div class="content2">
                <p>{{libraryData.content}}</p>
              </div>
              <div v-if="libraryData.existsFileYn == 'Y'" class="content-file">
                <a v-if="!$gfnCommonUtils.isEmpty(libraryData.fileId1)" href="#" @click.prevent="fnDownloadLibraryFile(libraryData.fileId1)">
                  <img src="/se2/images/user_sub03_1_fileicon.png" alt="파일 아이콘" class="fileIcon">
                  {{libraryData.fileNm1}}
                </a>
                <a v-if="!$gfnCommonUtils.isEmpty(libraryData.fileId2)" href="#" class="mt10" @click.prevent="fnDownloadLibraryFile(libraryData.fileId3)">
                  <img src="/se2/images/user_sub03_1_fileicon.png" alt="파일 아이콘" class="fileIcon">
                  {{libraryData.fileNm2}}
                </a>
                <a v-if="!$gfnCommonUtils.isEmpty(libraryData.fileId3)" href="#" class="mt10" @click.prevent="fnDownloadLibraryFile(libraryData.fileId2)">
                  <img src="/se2/images/user_sub03_1_fileicon.png" alt="파일 아이콘" class="fileIcon">
                  {{libraryData.fileNm3}}
                </a>
              </div>
            </div>
            <div class="btn_more">
              <router-link :to="{name: 'library'}" tag="a" title="목록">목록 ></router-link>
            </div>
          </section>
        </div>
      </div>
    </div>

    <QuickRight></QuickRight>
  </div>
</template>

<script>
import QuickRight from "@/modules/main/components/bc-quickRight.vue";
import CsMenuSubHeader from "@/modules/customer/components/bc-csMenuSubHeader.vue";

import customereApi from "@/modules/customer/service/customerApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: 'libraryDetail',
  components : {
    QuickRight,
    CsMenuSubHeader
  },
  props: {
    libraryId: {
      type: String,
      require: true,
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '자료실 상세';
      }
    },
  },
  data() {
    return {
      libraryData: {}
    }
  },
  mounted() {
    this.fnSelectLibraryInfo();
  },
  methods: {
    fnSelectLibraryInfo(){
      if(!this.$gfnCommonUtils.isEmpty(this.libraryId)){
        const vm = this;
        const params = {
          libraryId: this.libraryId
        };
        customereApi.selectLibraryList(params).then(response =>{
          const result = response.data;
          if(result.success) {
            result.data.forEach(function(obj){
              vm.libraryData = obj;
            });
          } else {
            confirm.fnAlert(this.componentsTitle, result.message);
          }
        });
      }
    },
    fnDownloadLibraryFile(fileId){
      if(!this.$gfnCommonUtils.isEmpty(fileId)){
        const params = {
          fileId: fileId
        };
        customereApi.procDownloadLibraryFile(params);
      }
    }
  }
}
</script>