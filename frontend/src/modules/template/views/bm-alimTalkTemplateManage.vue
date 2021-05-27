<template>
  <div>

    <div class="contentHeader">
      <h2>알림톡 템플릿 등록/상세</h2>
      <!-- <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="알림톡 템플릿 등록/상세 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
    </div>

    <div class="row">
        <div class="phone3 inline-block" style="width:30%">
          <div class="phoneFixed">
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
        <div class="of_h inline-block vertical-top consoleCon" style="width:60%">
          <div class="of_h">
            <div class="float-left" style="width:22%"><h4>발신 프로필/그룹 *</h4></div>
            <div class="float-left" style="width:78%">
              <select class="float-left selectStyle2" style="width:20%" v-model="senderKeyType" @change="fnSelectSenderKeyList">
                <option value="NOMAL">일반</option>
                <option value="GROUP">그룹</option>
              </select>
              <select class="float-left selectStyle2" style="width:80%" v-model="tmpltData.senderKey">
                <option value="">선택해주세요.</option>
                <option v-for="senderKeyInfo in senderKeyList" :key="senderKeyInfo.senderKey" :value="senderKeyInfo.senderKey">{{senderKeyInfo.senderKey}}</option>
              </select>
            </div>
          </div>
          <div class="of_h">
            <div class="float-left" style="width:22%"><h4>템플릿명 *</h4></div>
            <div class="float-left" style="width:78%">
              <input type="text" class="inputStyle" placeholder="20자 이내 입력" v-model="tmpltData.tmpltName" maxlength="20">
            </div>
          </div>
          <div class="of_h">
            <div class="float-left" style="width:22%"><h4>템플릿강조유형</h4></div>
            <div class="float-left" style="width:78%">
              <input type="radio" id="emphasizeYn_N" name="emphasizeYn" value="Y" v-model="tmpltData.emphasizeYn">
              <label for="emphasizeYn_N" class="mr30">선택 안 함</label>
              <input type="radio" id="emphasizeYn_Y" name="emphasizeYn" value="N" v-model="tmpltData.emphasizeYn">
              <label for="emphasizeYn_Y">강조 표기형</label>
            </div>
          </div>
          <div class="of_h">
            <div class="float-left" style="width:22%"><h4>템플릿강조제목 *</h4></div>
            <div class="float-left" style="width:78%">
              <input type="text" class="inputStyle" placeholder="최대 2줄 23자(24자부터 말줄임 처리, 권장하지 않음)" v-model="tmpltData.tmpltEmpsTitle" maxlength="50">
            </div>
          </div>
          <div class="of_h">
            <div class="float-left" style="width:22%"><h4>템플릿강조부제목 *</h4></div>
            <div class="float-left" style="width:78%">
              <input type="text" class="inputStyle" placeholder="최대 2줄 18자 (19자부터 말줄임 처리, 권장하지 않음)" v-model="tmpltData.tmpltEmpsSubTitle" maxlength="50">
            </div>
          </div>
          <div class="of_h">
            <div class="float-left" style="width:22%"><h4>내용 *</h4></div>
            <div class="float-left" style="width:78%">
              <textarea class="textareaStyle height190" v-model="tmpltData.tmpltContent" placeholder="템플릿 내용/부가 정보/광고성 메시지 합 최대 1,000자" maxlength="1000"></textarea>
            </div>
          </div>
          <div class="of_h consolMarginTop">
            <div class="float-left" style="width:22%"><h4>카테고리 *</h4></div>
            <div class="float-left" style="width:78%">
              <div class="float-left" style="width:49.5%">
                <select name="userConsole_sub040402_2" class="float-left selectStyle2" style="width:100%">
                  <option value="">대분류</option>
                </select>
              </div>
              <div class="float-right" style="width:49.5%">
                <select name="userConsole_sub040402_3" class="float-left selectStyle2" style="width:100%">
                  <option value="">중분류</option>
                </select>
              </div>
            </div>
          </div>
          <div class="of_h">
            <div class="float-left" style="width:22%"><h4 class="inline-block mr20">버튼</h4><a href="#self" class="btnStyle1 backBlack">추가 +</a></div>
            <div class="float-left" style="width:78%">
              <table class="table_skin1">
                <colgroup>
                  <col style="width:20%">
                  <col style="width:20%">
                  <col>
                  <col style="width:18%">
                </colgroup>
                <thead>
                  <tr>
                  <th class="text-center">타입</th>
                  <th class="text-center">버튼이름</th>
                  <th class="text-center">버튼링크</th>
                  <th class="text-center end"></th>
                  </tr>
                </thead>
                <tbody>
                  <tr>            
                    <td class="text-left">
                      <select name="userConsole_sub040402_4" class="float-left selectStyle2" style="width:100%">
                        <option value="">배송조회</option>
                      </select>
                    </td>
                    <td class="text-center"><input type="text" class="inputStyle float-left"></td>
                    <td class="text-left of_h">
                      <h6 class="float-left" style="width:20%">Description</h6>
                    </td>
                    <td class="text-center end"><a href="#self" class="btnStyle1 backLightGray">삭제</a></td>
                  </tr>
                  <tr>  
                    <td class="text-left" rowspan="2">
                      <select name="userConsole_sub040402_5" class="float-left selectStyle2" style="width:100%">
                        <option value="">웹 링크</option>
                      </select>
                    </td>
                    <td class="text-center" rowspan="2"><input type="text" class="inputStyle float-left"></td>
                    <td class="text-left">
                      <h6 class="float-left" style="width:20%">Mobile</h6>
                      <input type="text" class="inputStyle float-left" style="width:80%">
                    </td>                      
                    <td class="text-center end" rowspan="2"><a href="#self" class="btnStyle1 backLightGray">삭제</a></td>
                  </tr>
                  <tr>
                    <td class="text-left">
                      <h6 class="float-left" style="width:20%">PC</h6>
                      <input type="text" class="inputStyle float-left" style="width:80%">
                    </td>
                  </tr>
                  <tr>  
                    <td class="text-left" rowspan="2">
                      <select name="userConsole_sub040402_6" class="float-left selectStyle2" style="width:100%">
                        <option value="">앱 링크</option>
                      </select>
                    </td>
                    <td class="text-center" rowspan="2"><input type="text" class="inputStyle float-left"></td>
                    <td class="text-left">
                      <h6 class="float-left" style="width:20%">Android</h6>
                      <input type="text" class="inputStyle float-left" style="width:80%">
                    </td>                      
                    <td class="text-center end" rowspan="2"><a href="#self" class="btnStyle1 backLightGray">삭제</a></td>
                  </tr>
                  <tr>
                    <td class="text-left">
                      <h6 class="float-left" style="width:20%">IOS</h6>
                      <input type="text" class="inputStyle float-left" style="width:80%">
                    </td>
                  </tr>
                  <tr>            
                    <td class="text-left">
                      <select name="userConsole_sub040402_7" class="float-left selectStyle2" style="width:100%">
                        <option value="">봇 키워드</option>
                      </select>
                    </td>
                    <td class="text-center"><input type="text" class="inputStyle float-left"></td>
                    <td class="text-left of_h"></td>
                    <td class="text-center end"><a href="#self" class="btnStyle1 backLightGray">삭제</a></td>
                  </tr>
                  <tr>            
                    <td class="text-left">
                      <select name="userConsole_sub040402_8" class="float-left selectStyle2" style="width:100%">
                        <option value="">메시지 전달</option>
                      </select>
                    </td>
                    <td class="text-center"><input type="text" class="inputStyle float-left"></td>
                    <td class="text-left of_h"></td>
                    <td class="text-center end"><a href="#self" class="btnStyle1 backLightGray">삭제</a></td>
                  </tr>
                  <tr>            
                    <td class="text-left">
                      <select name="userConsole_sub040402_9" class="float-left selectStyle2" style="width:100%">
                        <option value="">상담톡전환</option>
                      </select>
                    </td>
                    <td class="text-center"><input type="text" class="inputStyle float-left"></td>
                    <td class="text-left of_h"></td>
                    <td class="text-center end"><a href="#self" class="btnStyle1 backLightGray">삭제</a></td>
                  </tr>
                  <tr>            
                    <td class="text-left">
                      <select name="userConsole_sub040402_10" class="float-left selectStyle2" style="width:100%">
                        <option value="">봇 전환</option>
                      </select>
                    </td>
                    <td class="text-center"><input type="text" class="inputStyle float-left"></td>
                    <td class="text-left of_h"></td>
                    <td class="text-center end"><a href="#self" class="btnStyle1 backLightGray">삭제</a></td>
                  </tr>
                  <tr>            
                    <td class="text-left">
                      <select name="userConsole_sub040402_11" class="float-left selectStyle2" style="width:100%">
                        <option value="">채널추가</option>
                      </select>
                    </td>
                    <td class="text-center"><input type="text" class="inputStyle float-left"></td>
                    <td class="text-left of_h"></td>
                    <td class="text-center end"><a href="#self" class="btnStyle1 backLightGray">삭제</a></td>
                  </tr>
                </tbody>
              </table>
              
            </div>
          </div>
          
          <div class="mt20 float-right">
            <a href="#self" class="btnStyle2 backWhite float-left">승인요청</a>
            <a href="#self" class="btnStyle2 backWhite float-left ml10">임시저장</a>
            <a @click="fnTest" class="btnStyle2 backRed float-left ml10" data-toggle="modal" data-target="#Regist" title="등록">등록</a>
            <a href="#self" class="btnStyle2 float-left ml10">취소</a>
            <a href="#self" class="btnStyle2 backWhite float-left ml10" data-toggle="modal" data-target="#Modify" title="수정">수정</a>
          </div>

        </div>
      </div>
  </div>
</template>

<script>
import templateApi from "@/modules/template/service/templateApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: 'alimTalkTemplateManage',
  props: {
    tmpltId: {
      type: String,
      require: false,
      default: function() {
        return '';
      }
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '알림톡 템플릿 관리';
      }
    },
  },
  data() {
    return {
      senderKeyType : 'NOMAL',  //NOMAL, GROUP
      senderKeyList : [],
      groupKeyList : [],
      useCh : 'ALIMTALK',
      tmpltData : {
        senderKey : '',
        tmpltName : '',
        emphasizeYn: 'N',
        tmpltEmpsTitle: '',
        tmpltEmpsSubTitle: '',
        tmpltContent: '',
      }
    }
  },
  mounted() {
    this.fnSelectSenderKeyList();
  },
  methods: {
    fnTest(){
      alert(this.tmpltData.senderKey);
    },
    //카카오톡 발신 프로필키 리스트 조회
    fnSelectSenderKeyList(){
      const params = {kkoSvc: this.useCh, senderKeyType: this.senderKeyType};
      templateApi.selectSenderKeyList(params).then(response => {
        const result = response.data;
        if(result.success) {
          this.tmpltData.senderKey = '';
          this.senderKeyList = Object.assign({}, result.data);
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
  }
}
</script>