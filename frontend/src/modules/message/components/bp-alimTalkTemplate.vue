<template>
  <div v-if="alimTalkTemplateOpen" @click.self="fnClose" class="modalStyle" id="Tamplet" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:1120px">
      <div class="modal-content">
        <div class="modal-body">
          <div>
            <h2>템플릿 선택</h2>
            <hr>
            <div class="of_h consoleCon">
              <div style="width:11%" class="float-left">
                <h4>*발신프로필</h4>
              </div>
              <div style="width:42%">
                <select name="userConsole_sub0204_2" class="selectStyle2" style="width:53%" v-model="senderKey">
                  <option value="">선택하세요</option>
                  <option 
                    v-for="(senderKeyInfo, idx) in senderKeyList"
                    :key="idx"
                    value="senderKeyInfo.senderKey"
                  >{{senderKeyInfo.senderKey}}</option>
                </select>
              </div>
            </div>
            <div class="row row-no-margin">
              <div class="col-xs-4 border-line2 pd20" style="height:569px">
                <h4>템플릿 검색</h4>
                <div>
                  <input type="text" class="inputStyle" style="width:75%">
                  <a href="#self" class="btnStyle1 backLightGray float-right" title="메시지 내용 조회">조회</a>
                </div>
                <div>
                  <h4 class="inline-block">템플릿 명(템플릿 제목)</h4>
                  <h4 class="inline-block  float-right">총 15건</h4>
                </div>
                <div class="border-line2" style="height:354px; overflow-y:scroll;">
                  <ul class="color4">
                    <li class="bb-ddd pd5">알림톡 템플릿1(템플릿 제목)</li>
                    <li class="bb-ddd pd5">알림톡 템플릿1(템플릿 제목)</li>
                    <li class="bb-ddd pd5">알림톡 템플릿1(템플릿 제목)</li>
                    <li class="bb-ddd pd5">알림톡 템플릿1(템플릿 제목)</li>
                    <li class="bb-ddd pd5">알림톡 템플릿1(템플릿 제목)</li>
                    <li class="bb-ddd pd5">알림톡 템플릿1(템플릿 제목)</li>
                    <li class="bb-ddd pd5">알림톡 템플릿1(템플릿 제목)</li>
                    <li class="bb-ddd pd5">알림톡 템플릿1(템플릿 제목)</li>
                    <li class="bb-ddd pd5">알림톡 템플릿1(템플릿 제목)</li>
                    <li class="bb-ddd pd5">알림톡 템플릿1(템플릿 제목)</li>
                    <li class="bb-ddd pd5">알림톡 템플릿1(템플릿 제목)</li>
                    <li class="bb-ddd pd5">알림톡 템플릿1(템플릿 제목)</li>
                    <li class="bb-ddd pd5">알림톡 템플릿1(템플릿 제목)</li>
                    <li class="bb-ddd pd5">알림톡 템플릿1(템플릿 제목)</li>
                  </ul>                  
                </div>
              </div>
            
              <div class="col-xs-4 of_h">
                <div class="of_h">
                  <h5 style="width:41%" class="float-left ml30">플러스친구ID</h5>
                  <h5 style="width:40%" class="float-right ml30 color4 word-break-all">비트큐브</h5>
                </div>
                <div class="of_h">
                  <h5 style="width:41%" class="float-left ml30">템플릿명</h5>
                  <h5 style="width:40%" class="float-right ml30 color4 word-break-all">친구톡 템플릿1</h5>
                </div>
                <div class="of_h">
                  <h5 style="width:41%" class="float-left ml30">템플릿강조제목</h5>
                  <h5 style="width:40%" class="float-right ml30 color4 word-break-all">템플릿강조제목</h5>
                </div>
                <div class="of_h">
                  <h5 style="width:41%" class="float-left ml30">템플릿강조부제</h5>
                  <h5 style="width:40%" class="float-right ml30 color4 word-break-all">템플릿 강조 부제목</h5>
                </div>
                <div class="of_h">
                  <h5 style="width:41%" class="float-left ml30">내용</h5>
                  <h5 style="width:40%" class="float-right ml30 color4 word-break-all">템플릿 내용목</h5>
                </div>        
              </div>

              <div class="col-xs-4">
                <!-- phoneWrap -->
                <div class="phoneWrap">
                  <img src="@/assets/images/common/phoneMockup3.svg" alt="알림톡 템플릿">
                  <div class="phoneTextWrap3">
                    <div>
                      <p class="text-main"><i class="fal fa-envelope-open-text"></i> 알림톡 도착</p>
                      <div class="text-sub-wrap">
                        <p class="text-sub_1">부제목</p>
                        <p class="text-sub scroll-y3">템플릿 강조 제목</p>
                      </div>
                      <p class="text-sub_2">템플릿 테스트입니다.<br>템플릿 테스트 템플릿 테스트 템플릿 테스트 템플릿 테스트</p>
                    </div>
                  </div>
                </div>
                <!-- //phoneWrap -->
              </div>
            </div>  
            <div class="text-center">
              <a href="#self" class="btnStyle2 backBlack" data-toggle="modal" title="템플릿 선택">템플릿 선택</a>
              <a href="#" @click.prevent="fnClose" class="btnStyle2 backWhite" data-dismiss="modal" title="취소">취소</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import templateApi from "@/modules/template/service/templateApi.js";

export default {
  name: "alimTalkTemplatePopup",
  props: {
    alimTalkTemplateOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '알림톡 템플릿 검색';
      }
    },
  },
  data() {
    return {
      useCh : 'ALIMTALK',
      senderKeyType: 'S',
      senderKeyList: [],
      senderKey: '',
    }
  },
  methods: {
    //카카오톡 발신 프로필키 리스트 조회
    fnSelectSenderKeyList(){
      const params = {kkoSvc: this.useCh, senderKeyType: this.senderKeyType};
      templateApi.selectSenderKeyList(params).then(response => {
        const result = response.data;
        if(result.success) {
          console.log('fnSelectSenderKeyList result ==> ', result);
          this.senderKeyList = Object.assign([], result.data);
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    //팝업 닫기
    fnClose(){
      this.$emit('update:alimTalkTemplateOpen', false)
    }
  }
}
</script>