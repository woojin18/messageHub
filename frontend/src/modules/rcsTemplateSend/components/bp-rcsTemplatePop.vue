<template>
    <div id="templatePop" class="modal modalStyle" tabindex="-1" role="dialog" aria-hidden="true">
        <!--<div class="modal modalStyle" id="Tamplet" tabindex="-1" role="dialog" aria-hidden="true">-->
            <div class="modal-dialog" style="width:1120px">
                <div class="modal-content">
                    <div class="modal-body">
                        <div>
                            <h2>RCS</h2>
                            <hr>
                            <div class="of_h">
                                <div style="width:11%" class="float-left">
                                    <h4 class="consolMarginTop">브랜드</h4>
                                </div>
                                <div style="width:42%">
                                    <select @change="fnSearch" v-model="brandId" name="userConsole_sub020201_2" class="selectStyle2" style="width:53%">
                                        <option v-for="option in brandArr" v-bind:value="option.BRAND_ID">
                                            {{option.BRAND_NAME}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="row row-no-margin">
                                <div class="col-xs-4 border-line2" style="height:569px; padding:30px">
                                    <h4 class="color000">템플릿 검색</h4>
                                    <div>
                                        <input v-model="tmpltNm" type="text" class="inputStyle" style="width:70%">
                                        <a @click.prevent="fnSearch" href="#self" class="btnStyle1 backLightGray float-right" title="메시지 내용 조회">조회</a>
                                    </div>
                                    <div>
                                        <h4 class="inline-block">템플릿 이름(템플릿 ID)</h4>
                                        <h4 class="color000 float-right">총 {{msgCnt}}건</h4>
                                    </div>
                                    <div class="border-line2" style="height:354px; overflow-y:scroll;">
                                        <ul class="color4">
                                            <li v-for="content in messagebaseArr" class="bb-ddd pd5">
                                                <a @click.prevent="fnTemplateContent(content.MESSAGEBASE_ID)" href="#">
                                                    {{content.TMPLT_NAME}}
                                                </a>
                                            </li>
                                        </ul>									
                                    </div>
                                </div>
                            
                                <div class="col-xs-4 of_h">
                                    <div class="of_h">
                                        <h5 style="width:22%" class="float-left ml30 color000">템플릿 명</h5>
                                        <h5 style="width:59%" class="float-right color4 word-break-all">{{tmpltName}}</h5>
                                    </div>
                                    <div class="of_h">
                                        <h5 style="width:22%" class="float-left ml30 color000">템플릿 ID</h5>
                                        <h5 style="width:59%" class="float-right color4 word-break-all">{{tmpltId}}</h5>
                                    </div>
                                    <div class="of_h">
                                        <h5 style="width:22%" class="float-left ml30 font-size14 color000">유형</h5>
                                        <h5 style="width:59%" class="float-right color4 word-break-all">{{formNm}}</h5>
                                    </div>
                                    <div v-if="templateRadioBtn=='des'" class="of_h">
                                        <h5 style="width:22%" class="float-left ml30 font-size14 color000">내용</h5>
                                        <h5 style="width:59%" class="float-right color4 word-break-all">{{desContent}}</h5>
                                    </div>
                                    <div v-if="templateRadioBtn=='cell'" class="of_h">
                                        <h5 style="width:22%" class="float-left ml30 font-size14 color000">내용</h5>
										<div v-for="n in styleContentCnt" class="of_h consolMarginTop">
											<a class="color4 ml30">{{styleInput[n-1]}}</a>
											<a class="color4 float-right">{{styleInputSec[n-1]}}</a>
										</div>
                                    </div>
                                    <div v-for="n in btnCnt" class="of_h">
                                        <h5 style="width:22%" class="float-left ml30 font-size14 color000">버튼{{n}}</h5>
                                        <h5 style="width:59%" class="float-right color4 word-break-all">{{selectBtn[n-1]}}</h5>
                                        <h5 style="width:59%" class="float-right color4 word-break-all">{{btnNm[n-1]}}</h5>
                                    </div>					
                                </div>
                                <div class="col-xs-4">
                                    <div class="phoneWrap">
                                        <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
                                        <div class="phoneTextWrap">
                                            <div v-if="templateRadioBtn=='des'" class="phoneText1 scroll-y2">
                                                <p><pre>{{desContent}}</pre></p>
                                                <p v-for="n in btnCnt" class="text-center mt20" style="color:#69C8FF">{{btnNm[n-1]}}</p>
                                            </div>
                                            <div v-if="templateRadioBtn=='cell'" class="phoneText1 scroll-y2">
                                                <div v-for="n in styleContentCnt" class="of_h consolMarginTop">
                                                    <p class="lc-1 inline-block">{{styleInput[n-1]}}</p>
											        <p class="lc-1 inline-block float-right">{{styleInputSec[n-1]}}</p>
                                                    <hr v-if="styleChk[n-1]">
                                                </div>
                                                <p v-for="n in btnCnt" class="text-center mt20" style="color:#69C8FF">{{btnNm[n-1]}}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>						
                        </div>						
                        <div class="text-center">
                            <a href="#self" @click.prevent="fnTemplateChoice" class="btnStyle1 backBlack" data-dismiss="modal" title="템플릿 선택">템플릿 선택</a>
                            <a href="#self" @click.prevent="fnClose" class="btnStyle1 backWhite ml20" data-dismiss="modal" title="취소">취소</a>						
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import rcsTemplateSendApi from "@/modules/rcsTemplateSend/service/api.js";
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: "rcsTemplatePop",
  components : {
  },
  props : {
        rcsTemplatePopOpen: {
            type: Boolean,
            require: true,
            default: false,
        },
        templateRadioBtn: {
            type: String,
            require: true,
            dafault: ""
        }
  },
  data() {
    return {
        brandId : "",               // 브랜드 ID
        brandArr : [],              // 브랜드 Arr
        messagebaseId : "",         // 템플릿 ID
        messagebaseArr : [],        // 템플릿 Arr
        msgCnt : 0,                 // 템플릿 개수
        tmpltNm : "",               // 조회조건 템플릿 검색
        tmpltName : "",             // 템플릿 명 View
        tmpltId : "",               // 템플릿 ID
        messagebaseformId : "", // 유형ID
        formNm : "",                // 유형명
        desContent : "",            // 서술형 내용
        styleContentCnt: 0,			// 스타일형 inputLine count
		styleArr: [],			    // 스타일형 inputLine input count
		styleInput: [],				// 스타일형 첫 input
		styleInputSec: [],			// 스타일형 두번째 input
		styleChk: [],	            // 스타일형 lineChk
        btnCnt: 0,			        // 버튼 개수
        selectBtn: [],		        // selectBox
		btnNm:[],			        // 버튼 이름
		rcsButtons:[]				// 버튼 리스트   
    }
  },
  methods: {
    // 팝업 기초 세팅 
    fnInit() {
        var vm = this;
        var params = {

        }

        rcsTemplateSendApi.rcsTemplatePopInit(params).then(response => {
            var result = response.data;
            vm.brandId = result.data[0].BRAND_ID;
            vm.brandArr = result.data;
            vm.fnSearch();
        })
    },

    fnSearch(){
        var vm = this;
        var cardType = vm.templateRadioBtn == "des" ? "description" : "cell";
        var params = {
            "brandId" : vm.brandId,
            "cardType" : cardType,
            "tmpltNm" : vm.tmpltNm
        }

        rcsTemplateSendApi.rcsTemplateSearch(params).then(response => {
            var result = response.data;
            if(result.data.length == 0) {
                vm.messagebaseId = "";
                vm.messagebaseArr = [];
                vm.msgCnt = result.data.length;
            } else {
                vm.messagebaseId = result.data[0].MESSAGEBASE_ID;
                vm.messagebaseArr = result.data;
                vm.msgCnt = result.data.length;
            }    

            // 조회된 템플릿이 없는경우 messagebaseId 빈칸처리
            if(vm.msgCnt==0) {
                vm.fnTemplateContent("");
            } else {
                vm.fnTemplateContent(result.data[0].MESSAGEBASE_ID);
            }
        })

    },

    fnTemplateContent(messagebaseId) {
        var vm = this;
        vm.messagebaseId = messagebaseId;
        if(messagebaseId != "") {
            var params = {
                "messagebaseId" : messagebaseId
            };
            rcsTemplateSendApi.rcsTemplateDetail(params).then(response => {
                var result = response.data;
                var success = result.success;
                if(success) {
                    var data = result.data;
                    vm.tmpltName = data.tmpltName;
                    vm.tmpltId = data.tmpltId;
                    vm.formNm = data.formNm;
                    vm.desContent = data.textContents;

                    // input 세팅 (cell)
                    if (vm.templateRadioBtn == 'cell') {
                        vm.styleContentCnt = data.styleContentCnt;
                        vm.styleArr = data.styleArr;
                        vm.styleInput = data.styleInput;
                        vm.styleInputSec = data.styleInputSec;
                        vm.styleChk = data.styleChk;
                        vm.messagebaseformId = data.styleNm;
                    } else {
                    	vm.messagebaseformId = data.desNm;
                    }
                    // 버튼 세팅
                    vm.btnCnt = data.btnCnt;
                    vm.selectBtn = data.selectBtn;
                    vm.btnNm = data.btnNm;
					vm.rcsButtons = data.rcsButtons;
                }
               
            });
        } else {
            vm.tmpltName = "";
            vm.tmpltId = "";
            vm.messagebaseformId = "";
            vm.formNm = "";
            vm.desContent = "";
            vm.btnCnt = 0;
            vm.selectBtn = [];
            vm.btnNm = [];
        }
    },

    //팝업 닫기
    fnClose(){
      //데이터 초기화
        this.brandId = "",              // 브랜드 ID
        this.brandArr = [],             // 브랜드 Arr
        this.messagebaseId = "",        // 템플릿 ID
        this.messagebaseArr = [],       // 템플릿 Arr
        this.msgCnt = 0,                // 템플릿 개수
        this.tmpltNm = "",              // 조회조건 템플릿 검색
        this.tmpltName = "",            // 템플릿 명 View
        this.tmpltId = "",              // 템플릿 ID
        this.messagebaseformId = "", // 유형ID
        this.formNm = "",               // 유형명
        this.desContent = "",           // 서술형 내용
        this.styleContentCnt = 0,		// 스타일형 inputLine count
		this.styleArr = [],			    // 스타일형 inputLine input count
		this.styleInput = [],			// 스타일형 첫 input
		this.styleInputSec = [],		// 스타일형 두번째 input
		this.styleChk = [],	            // 스타일형 lineChk
        this.btnCnt = 0,			    // 버튼 개수
        this.selectBtn = [],		    // selectBox
		this.btnNm = [],			// 버튼 이름
		this.rcsButtons = []		// 버튼 리스트   

    },

    // 템플릿 선택
    fnTemplateChoice() {
        var vm = this;
        var messagebaseId = vm.messagebaseId;
        var data = new Object();
        data.radioBtn = vm.templateRadioBtn;
        data.messagebaseId = vm.messagebaseId;
        data.brandId = vm.brandId;
        data.messagebaseformId = vm.messagebaseformId;
        data.formNm = vm.formNm;
        data.desContent = vm.desContent;
        data.styleContentCnt = vm.styleContentCnt;
        data.styleInput = vm.styleInput;
        data.styleInputSec = vm.styleInputSec;
        data.styleChk = vm.styleChk;
        data.btnCnt = vm.btnCnt;
        data.selectBtn = vm.selectBtn;
        data.btnNm = vm.btnNm;
        data.rcsButtonList = vm.rcsButtons;
        
        if(messagebaseId == "") {
            confirm.fnAlert("템플릿을 선택해주세요.","");
        } else {
            vm.$emit('fnResult', data);
            vm.fnClose();
        }
    }
  }
}
</script>