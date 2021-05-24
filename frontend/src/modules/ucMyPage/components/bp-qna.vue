<template>
  <div class="modal fade modalStyle in" id="acQnaPopup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          <div class="of_h">
						<h2>1:1 문의</h2>
						<hr>
						<div class="of_h">
							<h5 class="inline-block" style="width:18%">문의유형 <span class="color1" v-if="this.status != 'detail'">*</span></h5>
              <h5 v-if="this.status == 'detail'" class="font-normal inline-block float-right" style="width:80%">{{ qnaTypeStr }}</h5>
              <select v-else id="qnaType" name="qnaType" class="selectStyle2 float-right" style="width:80%">
							</select>
						</div>	
						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:18%">이메일 <span class="color1" v-if="this.status != 'detail'">*</span></h5>
              <h5 v-if="this.status == 'detail'" class="font-normal inline-block float-right" style="width:80%">{{ email }}</h5>
							<div v-else class="float-right" style="width:80%">
							  <input type="text" class="inputStyle" v-model="email">
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:18%">전화번호 <span class="color1" v-if="this.status != 'detail'">*</span></h5>
              <h5 v-if="this.status == 'detail'" class="font-normal inline-block float-right" style="width:80%">{{ hpNumber }}</h5>
							<div v-else class="float-right" style="width:80%">
								<input type="text" class="inputStyle" v-model="hpNumber">
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:18%">문의제목 <span class="color1" v-if="this.status != 'detail'">*</span></h5>
              <h5 v-if="this.status == 'detail'" class="font-normal inline-block float-right" style="width:80%">{{ title }}</h5>
							<input v-else type="text" class="inputStyle float-right" style="width:80%" placeholder="제목을 입력하세요(100자)" v-model="title">
						</div>
            <div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:18%">문의내용 <span class="color1" v-if="this.status != 'detail'">*</span></h5>
              <h5 v-if="this.status == 'detail'" class="font-normal inline-block float-right" style="width:80%"><pre>{{ content }}</pre></h5>
							<textarea v-else class="textareaStyle height120 float-right" style="width:80%" placeholder="문의내용을 입력하세요(500자)" v-model="content"></textarea>
						</div>
					</div>
          <div v-if="this.status == 'detail'" class="mt20">
            <div class="of_h consolMarginTop">
              <h5 class="inline-block" style="width:18%">문의상태</h5>
              <h5 class="font-normal inline-block float-right" style="width:80%">{{ qnaStatusStr }}</h5>
						</div>
            <div class="of_h consolMarginTop">
              <h5 class="inline-block" style="width:18%">답변 내용</h5>
              <h5 class="font-normal inline-block float-right" style="width:80%"><pre>{{ reply }}</pre></h5>
						</div>
					</div>
          <div class="text-center mt20">
						<a @click="fnSave" v-if="this.status != 'detail'" class="btnStyle1 backBlack" data-toggle="modal" data-target="">등록</a>
						<a @click="fnCloseLayer" class="btnStyle1 backWhite ml10" data-toggle="modal" data-target="">취소</a>
					</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import commonUtilApi from "@/modules/commonUtil/service/commonUtilApi";
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import myPageApi from '@/modules/ucMyPage/service/myPageApi';

export default {
  name: 'acQnaPopup',
  data() {
    return {
      qnaType : '',       // 문의 상태 유형
      qnaTypeStr : '',    // 문의 상태 유형 문자열
      email : '',         // 이메일
      hpNumber : '',      // 전화번호
      title : '',         // 제목
      content :'',        // 내용
      qnaStatusStr : '',  // 문의 상태
      reply : '',         // 답변

      memberInfo : {},
    }
  },
  props: {
    status : {
      type : String
    },
    selectRow: {
      type : Object,
      require : false
    },
    popReset: {
      type: Number
    }
  },
  mounted() {
    this.fnMemberInfo();
    this.fnQnaTypeInit();
  },
  watch: {
    popReset() {
      // 문의 유형의 option이 없는 경우 문의 유형 다시 조회
      if(jQuery("#qnaType option").size() == 0){
        this.fnQnaTypeInit();
      }
      if(this.selectRow.questType != undefined){
        jQuery("#qnaType").val(this.selectRow.questType);
      } else {
        jQuery("#qnaType").val("");
      }
      // 데이터 초기화
      this.fnDataReset();
    },
    hpNumber(){
      return this.hpNumber = this.hpNumber.replace(/[^0-9]/g, '');
    }
  },
  methods: {
      //QNA 문의 유형 코드
      fnQnaTypeInit(){
        var params = {
          codeTypeCd	: "QNA_TYPE",
          useYN		: "Y"
        };
        commonUtilApi.selectCodeList(params).then(response =>{
          var result = response.data.data;
          this.qnaType = result;
          jQuery("#qnaType").append('<option value="">선택</option>');
          for(var i = 0; i < result.length; i++){
            jQuery("#qnaType").append('<option value="'+result[i].codeVal1+'">'+result[i].codeName1+'</option>');
          }
          if(this.status == "edit"){
            jQuery("#qnaType").val(this.selectRow.questType);
          }
        });
      },
      fnCloseLayer(){
          jQuery("#acQnaPopup").modal("hide");
      },
      // 회원정보 이메일, 전화번호 조회
      fnMemberInfo(){
        var params = {};
        myPageApi.selectMemberInfo(params).then(response => {
          var result = response.data.data;
          this.memberInfo = result;
        });
      },
      // 데이터 초기화
      fnDataReset(){
        this.qnaType = this.selectRow.questType;
        this.qnaTypeStr = this.selectRow.questTypeStr;
        this.title = this.selectRow.title;
        this.hpNumber = this.status == "add" ? this.memberInfo.hpNumber : this.selectRow.hpNumber;    // 신규 문의 사항인 경우 로그인 회원의 전화번호
        this.email = this.status == "add" ? this.memberInfo.loginId : this.selectRow.email;           // 신규 문의 사항인 경우 로그인 회원의 이메일
        this.content = this.selectRow.content;
        this.qnaStatusStr = this.selectRow.questStatusStr;
        this.reply = this.selectRow.reply;
      },
      fnSave(){
        this.qnaType = jQuery("#qnaType").val();
        if(this.qnaType == undefined || this.qnaType == ''){
          confirm.fnAlert("", "문의유형은 필수선택항목입니다.");
          return;
        }
        if(this.email == undefined || this.email == ''){
          confirm.fnAlert("", "이메일은 필수항목입니다.");
          return;
        }
        // 이메일 정규식
        var regExp = RegExp(/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i);
        if(regExp.test(this.email) == false){
          confirm.fnAlert("", "이메일을 정확히 입력해주세요.", "", "");
          return;
        }

        if(this.hpNumber == undefined || this.hpNumber == ''){
          confirm.fnAlert("", "전화번호는 필수항목입니다.");
          return;
        }

        if(this.title == undefined || this.title == ''){
          confirm.fnAlert("", "문의제목은 필수항목입니다.");
          return;
        }
        if(this.content == undefined || this.content == ''){
          confirm.fnAlert("", "문의내용은 필수항목입니다.");
          return;
        }

        eventBus.$on('callbackEventBus', this.fnSaveCallBack);
        confirm.fnConfirm("문의내역 저장", "저장하시겠습니까?", "확인");
      },
      fnSaveCallBack(){
        var params = {
          questBoardId  : this.selectRow.questBoardId,      // 문의내역id
          questType     : this.qnaType,                     // 문의내역 유형
          email         : this.email,                       // 이메일
          hpNumber      : this.hpNumber,                    // 전화번호
          title         : this.title,                       // 제목
          content       : this.content                      // 내용
        }
        myPageApi.saveQnaInfo(params).then(response => {
          var result = response.data;
          if(result.success) {
            confirm.fnAlert( "저장되었습니다.", "");
            // 창닫기
            this.fnCloseLayer();
            // 부모창 리스트 조회
            this.$parent.fnSearch();
          } else {
            confirm.fnAlert(result.message, "");
          }
        })
      }
  }
}
</script>
