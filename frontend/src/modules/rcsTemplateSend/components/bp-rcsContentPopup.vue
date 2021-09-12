<template>
    <div class="modal modalStyle" id="contentPop" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<div v-if="contentPopCnt==0" class="of_h">
							<div class="float-left" style="width:15%"><h5>브랜드</h5></div>
							<div class="float-left" style="width:80%">
                <select v-model="brandId" name="userConsole_sub020202_3" class="selectStyle2" style="width:100%">
                  <option v-for="option in brandArr" v-bind:value="option.BRAND_ID">
                    {{option.BRAND_NAME}}
                  </option>
                </select>

							</div>							
						</div>
            <div v-if="templateRadioBtn!='text' && templateRadioBtn!='SS000000' && templateRadioBtn!='SL000000'" class="of_h consolMarginTop">
							<div class="float-left" style="width:15%"><h5>이미지선택</h5></div>
							<div class="float-left" style="width:85%">
                <div class="float-left" style="width:24%"><a @click="fnOpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지 선택">이미지선택</a></div>
                  <ul class="float-right attachList" style="width: 61%; margin: 0px 25px 0px 0px;padding: 5px 15px; height: 30px;">
                    <li v-if="imgUrl">
                      <a @click="fnDelImg">{{fnSubString(imgUrl, 0, 40)}} <i class="fal fa-times"></i></a>
                    </li>
                  </ul>
              </div>
						
						</div>
            <div v-if="templateRadioBtn!='text' && templateRadioBtn!='SS000000'" class="of_h consolMarginTop">
							<div class="float-left" style="width:15%"><h5>제목</h5></div>
							<div class="float-left" style="width:80%">
								<input v-model="title" type="text" class="inputStyle" placeholder="타이틀 입력 (최대 30자)" title="제목 입력란">
							</div>							
						</div>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:15%"><h5>내용</h5></div>
							<div class="float-left" style="width:80%">
								<textarea v-model="contents" class="textareaStyle height120" :placeholder="holder"></textarea>
								<p class="color3"><i class="far fa-info-circle"></i> {{textCnt}}자 입력 / 변수 포함 최대 90글자 <i class="fas fa-question-circle toolTip"></i></p>
							</div>							
						</div>
					</div>	
					<div class="text-center mt20">
						<a @click.prevent="fnAdd" href="#self" class="btnStyle2 backBlack" title="입력">입력</a>
						<a @click.prevent="fnClose" href="#self" class="btnStyle2 backWhite ml20" data-dismiss="modal" title="닫기">닫기</a>																
					</div>
				</div>				
			</div>
		</div>
    <ImageManagePopUp @img-callback="fnCallbackImgInfo" :imgMngOpen.sync="imgMngOpen" useCh="RCS" ref="imgMngPopup"></ImageManagePopUp>
	</div>
</template>

<script>
import rcsTemplateSendApi from "@/modules/rcsTemplateSend/service/api.js";
import ImageManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: "rcsContentPop",
  components : {
    ImageManagePopUp
  },
  props : {
        templateRadioBtn: {
            type: String,
            require: true,
            default: "",
        },
        contentPopCnt: {
            type: Number,
            default: 0
        },
        sendData: {
            type: Object
        },
        dataSet: {
            type: Boolean,
            default: false
        }
  },
  data() {
    return { 
      holder : "변수로 설정하고자 하는 내용을 {{ }}표시로 작성해 주십시오. 예) 이름과 출금일을 변수 설정: 예) {{고객}}님 {{YYMMDD}} 출금 예정입니다.",  // 내용 holder
      textCnt : 0,    // 글자 수 체크
      title: "",      // 제목
      contents : "",  // 내용
      brandId : "",   // 브랜드
      brandArr : [],  // 브랜드 select
      imgMngOpen : false, // 이미지 팝업
      imgUrl : "",      // 이미지 URL
      fileId : "",      // FILE ID
      wideImgYn : "N",   
    }
  },
  watch : {
      contents : function(newVal, oldVal) {
        this.textCnt = newVal.length;
      }
  },

  methods: {
    // 팝업 기초 세팅 
    fnInit() {
        var vm = this;
        var params = {};

        rcsTemplateSendApi.rcsTemplatePopInit(params).then(response => {
            var result = response.data;
            vm.brandId = result.data[0].BRAND_ID;
            vm.brandArr = result.data;
            
            // 기존 입력 값이 있으면 data를 세팅
            if(vm.dataSet) {
              vm.fnSetData();
            }
        });
    },

    fnSetData() {
      var sendData = this.sendData;
      var contentPopCnt = this.contentPopCnt;

      // contentPopCnt가 0보다 큰 경우 carousel Type
      if(contentPopCnt > 0) {
        this.title = sendData.carouselObj.textTitle[contentPopCnt-1];
        this.contents = sendData.carouselObj.textContents[contentPopCnt-1];
        this.imgUrl = sendData.carouselObj.imgUrl[contentPopCnt-1];
        this.fileId = sendData.carouselObj.fileId[contentPopCnt-1];

      } else {
        var brandId = sendData.brandId;
        if(brandId != "" && brandId != null) {
          this.title = sendData.textTitle;
          this.contents = sendData.textContents;
          this.brandId = sendData.brandId;
          this.imgUrl = sendData.imgUrl;
          this.fileId = sendData.fileId;
        }
      }

    },

    // 업 닫기
    fnClose(){
      //데이터 초기화
      this.textCnt = 0;
      this.title = "";
      this.contents = "";
      this.brandId = "";
      this.brandArr = [];
      this.imgUrl = "";
      this.fileId = "";
      this.wideImgYn = "N"; 

      jQuery("#contentPop").modal("hide");
    },

    // 입력 버튼
    fnAdd() {
      var vm = this;
      var templateRadioBtn = vm.templateRadioBtn;
      var contents = vm.contents;
      var textCnt = vm.textCnt;
      var title = vm.title;
      var fileId = vm.fileId;
      var params = {
        "brandId" : vm.brandId,
        "title" : vm.title,
        "contents" : vm.contents,
        "imgUrl" : vm.imgUrl,
        "fileId" : vm.fileId,
        "wideImgYn" : vm.wideImgYn
      };

      var titleTf = false;
      var imgTf = false;

      // validation
      if(templateRadioBtn == "SMwThM00" || templateRadioBtn == "SMwThT00" ||
        templateRadioBtn == "carouselSmall" || templateRadioBtn == "carouselMedium" ) {
          titleTf = true;
          imgTf = true;
        }
      if(templateRadioBtn == "SL000000") {
        titleTf = true;
      }

      if(contents == "") {
        confirm.fnAlert("내용을 입력해주세요.","");
      } else if (textCnt > 90) {
        confirm.fnAlert("내용을 90자 이내로 입력해주세요.","");
      } else if(titleTf) {
        if(title == "") {
          confirm.fnAlert("제목을 입력해주세요.", "");
        } else {
          this.$emit('fnAddResult', params);
          this.fnClose();
        }
      } else {
        this.$emit('fnAddResult', params);
        this.fnClose();
      }
    },

    // IMG 팝업
    fnOpenImageManagePopUp(){
      this.$refs.imgMngPopup.fnSearch();
      this.imgMngOpen = !this.imgMngOpen;
    },

    fnCallbackImgInfo(imgInfo) {
      this.imgUrl = imgInfo.chImgUrl;
      this.fileId = imgInfo.fileId;
      this.wideImgYn = imgInfo.wideImgYn;
    },

    fnDelImg() {
      this.imgUrl = '';
      this.fileId = '';
      this.wideImgYn = 'N';
    },

    fnSubString(str, sIdx, length){
      let shortStr = ''
      if(!this.$gfnCommonUtils.isEmpty(str)){
        shortStr = str.toString();
        if(shortStr.length > length){
          shortStr = shortStr.substring(sIdx, length) + '...  ';
        } else {
          shortStr = shortStr + '  ';
        }
      }
      return shortStr;
    },
  }
}
</script>