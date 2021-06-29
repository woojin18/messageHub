<template>
    <div class="modal fade modalStyle" id="Tamplet" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<div class="of_h">
							<div class="float-left" style="width:15%"><h5>브랜드</h5></div>
							<div class="float-left" style="width:80%">
                <select v-model="brandId" name="userConsole_sub020202_3" class="selectStyle2" style="width:100%">
                  <option v-for="option in brandArr" v-bind:value="option.BRAND_ID">
                    {{option.BRAND_NAME}}
                  </option>
                </select>

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
						<a @click.prevent="fnAdd" href="#self" class="btnStyle2 backBlack" data-dismiss="modal" title="입력">입력</a>
						<a @click.prevent="fnClose" href="#self" class="btnStyle2 backWhite" data-dismiss="modal" title="닫기">닫기</a>																
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
  name: "rcsContentPop",
  components : {
  },
  props : {
        rcsContentPopOpen: {
            type: Boolean,
            require: true,
            default: false,
        }
  },
  data() {
    return { 
      holder : "변수로 설정하고자 하는 내용을 {{ }}표시로 작성해 주십시오.&#13;예) 이름과 출금일을 변수 설정: 예) {{고객}}님 {{YYMMDD}} 출금 예정입니다.",  // 내용 holder
      textCnt : 0,    // 글자 수 체크
      contents : "",  // 내용
      brandId : "",   // 브랜드
      brandArr : [],  // 브랜드 select
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
        });

    },

    // 업 닫기
    fnClose(){
      //데이터 초기화
      this.textCnt = 0;
      this.contents = "";
      this.brandId = "";
      this.brandArr = [];
      JQuery("#Tamplet").modal("hide");
    },

    // 입력 버튼
    fnAdd() {
      var vm = this;
      var contents = vm.contents;
      var textCnt = vm.textCnt;
      var params = {
        "brandId" : vm.brandId,
        "contents" : vm.contents
      };

      if(contents == "") {
        confirm.fnAlert("내용을 입력해주세요.","");
      } else if (textCnt > 90) {
        confirm.fnAlert("내용을 90자 이내로 입력해주세요.","");
      } else {
        this.$emit('fnAddResult', params);
      }
      this.fnClose();
    }
  }
}
</script>