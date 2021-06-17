<template>
    <div v-if="rcsTemplatePopOpen" @click.self="fnClose" class="modalStyle" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal fade modalStyle" id="Tamplet" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog" style="width:1120px">
                <div class="modal-content">
                    <div class="modal-body">
                        <div>
                            <h2>RCS 템플릿</h2>
                            <hr>
                            <div class="of_h">
                                <div style="width:11%" class="float-left">
                                    <h4 class="consolMarginTop">브랜드</h4>
                                </div>
                                <div style="width:42%">
                                    <select v-model="brandId" name="userConsole_sub020201_2" class="selectStyle2" style="width:53%">
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
                                        <input type="text" class="inputStyle" style="width:70%">
                                        <a href="#self" class="btnStyle1 backLightGray float-right" title="메시지 내용 조회">조회</a>
                                    </div>
                                    <div>
                                        <h4 class="inline-block">템플릿 이름(템플릿 ID)</h4>
                                        <h4 class="color000 float-right">총 15건</h4>
                                    </div>
                                    <div class="border-line2" style="height:354px; overflow-y:scroll;">
                                        <ul class="color4">
                                            <li class="bb-ddd pd5">가입안내(JOIN_INFO)</li>
                                            <li class="bb-ddd pd5">가입안내(JOIN_INFO)</li>
                                            <li class="bb-ddd pd5">가입안내(JOIN_INFO)</li>
                                            <li class="bb-ddd pd5">가입안내(JOIN_INFO)</li>
                                            <li class="bb-ddd pd5">가입안내(JOIN_INFO)</li>
                                            <li class="bb-ddd pd5">가입안내(JOIN_INFO)</li>
                                            <li class="bb-ddd pd5">가입안내(JOIN_INFO)</li>
                                            <li class="bb-ddd pd5">가입안내(JOIN_INFO)</li>
                                            <li class="bb-ddd pd5">가입안내(JOIN_INFO)</li>
                                            <li class="bb-ddd pd5">가입안내(JOIN_INFO)</li>
                                            <li class="bb-ddd pd5">가입안내(JOIN_INFO)</li>
                                            <li class="bb-ddd pd5">가입안내(JOIN_INFO)</li>
                                            <li class="bb-ddd pd5">가입안내(JOIN_INFO)</li>
                                            <li class="bb-ddd pd5">가입안내(JOIN_INFO)</li>
                                            <li class="bb-ddd pd5">가입안내(JOIN_INFO)</li>
                                        </ul>									
                                    </div>
                                </div>
                            
                                <div class="col-xs-4 of_h">
                                    <div class="of_h">
                                        <h5 style="width:22%" class="float-left ml30 color000">템플릿 명</h5>
                                        <h5 style="width:59%" class="float-right color4 word-break-all">가입안내</h5>
                                    </div>
                                    <div class="of_h">
                                        <h5 style="width:22%" class="float-left ml30 color000">템플릿 ID</h5>
                                        <h5 style="width:59%" class="float-right color4 word-break-all">Temp0924203942304</h5>
                                    </div>
                                    <div class="of_h">
                                        <h5 style="width:22%" class="float-left ml30 font-size14 color000">유형</h5>
                                        <h5 style="width:59%" class="float-right color4 word-break-all">{{name}}입니다. {{date}} 할인/특가상품을 안내해드립니다.<br>본 알림은 {{name}} 회원전용 서비스입니다.</h5>
                                    </div>
                                    <div class="of_h">
                                        <h5 style="width:22%" class="float-left ml30 color000">이미지</h5>
                                        <h5 style="width:59%" class="float-right color4 word-break-all">
                                            <select name="userConsole_sub020201_3" class="selectStyle2 vertical-middle" style="width:60%">
                                                <option value="">선택하세요</option>
                                            </select>
                                            사이트 연결<br>http://sms.uplus.co.kr
                                        </h5>
                                    </div>						
                                </div>


                                <div class="col-xs-4">
                                    <div class="phoneWrap">
                                        <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
                                        <div class="phoneTextWrap">
                                            <div class="phoneText1 scroll-y2">
                                                <p>내용이 들어갑니다</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                            </div>						
                        </div>						
                        <div class="text-center">
                            <a href="#self" class="btnStyle1 backBlack" data-toggle="modal" title="템플릿 선택">템플릿 선택</a>
                            <a href="#self" class="btnStyle1 backWhite" data-dismiss="modal" title="취소">취소</a>						
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
        brandId : "",
        brandArr : [],
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


    },
    //팝업 닫기
    fnClose(){
      //데이터 초기화
      //this.templateList = [];
      //this.templateData = {};
      this.$emit('update:rcsTemplatePopOpen', false)
    }
  }
}
</script>