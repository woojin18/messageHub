<template>
  <div>
    <div class="contentHeader">
      <h2>친구톡 템플릿</h2>
      <!-- <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="친구톡 템플릿 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
    </div>

    <!-- 본문 -->
    <div class="of_h mt30">
      <div class="phone3 inline-block" style="width:30%">
        <div class="phoneFixed">
          <!-- phoneWrap -->
          <div class="phoneWrap">
            <img src="@/assets/images/common/phoneMockup2_1.svg" alt="프리 템플릿">
            <div class="phoneTextWrap4">
              <p>[광고]</p>
              <div class="mt5">
                <div class="text-sub-wrap">
                  <p class="text-sub scroll-y">친구톡 발송테스트</p>
                </div>
                <p class="text-sub_2">수신거부: 홈 > 친구차단</p>
              </div>
            </div>
          </div>
          <!-- //phoneWrap -->
        </div>
      </div>
      <div class="of_h inline-block vertical-top consoleCon" style="width:60%">
        <div class="of_h user-phone">
          <div class="float-left" style="width:27%">
            <h4>템플릿명 *</h4>
          </div>
          <div class="float-left" style="width:73%">
            <input type="text" class="inputStyle float-right" title="템플릿명 입력란" v-model="tmpltData.tmpltName" maxlength="100">
          </div>
        </div>
        <div class="of_h user-phone">
          <div class="float-left" style="width:27%">
            <h4>메시지구분 *</h4>
          </div>
          <div class="float-left" style="width:73%">
            <input type="radio" id="msgKind_A" name="msgKind" value="A" v-model="tmpltData.msgKind">
            <label for="msgKind_A" class="mr30">광고성</label>
            <input type="radio" id="msgKind_I" name="msgKind" value="I" v-model="tmpltData.msgKind">
            <label for="msgKind_I">정보성</label>
          </div>
        </div>
        <div class="of_h user-phone">
          <div class="float-left" style="width:27%">
            <h4>타 프로젝트 사용여부  *</h4>
          </div>
          <div class="float-left" style="width:73%">
            <input type="radio" id="otherProjectUseYn_Y" name="otherProjectUseYn" value="Y" v-model="tmpltData.otherProjectUseYn">
            <label for="otherProjectUseYn_Y" class="mr30">공용</label>
            <input type="radio" id="otherProjectUseYn_N" name="otherProjectUseYn" value="N" v-model="tmpltData.otherProjectUseYn">
            <label for="otherProjectUseYn_N">전용</label>
          </div>
        </div>
        <div class="of_h user-phone">
          <div class="float-left" style="width:27%">
            <h4>내용 *</h4>
          </div>
          <div class="float-left" style="width:73%">
            <textarea class="textareaStyle height120" v-model="tmpltData.tmpltContent" maxlength="2000"></textarea>
          </div>
        </div>

        <div class="of_h user-phone consolMarginTop">
          <div class="float-left" style="width:27%">
            <h4>이미지</h4>
          </div>
          <div class="float-left" style="width:73%">
            <div class="of_h">
              <div class="float-left" style="width:25%"><a @click="fnOpenImageManagePopUp" class="btnStyle1 backLightGray width100_" data-toggle="modal" data-target="#image" title="이미지선택">이미지선택</a></div>
              <ul class="float-right attachList" style="width:74%; padding:5px 15px">
                <li><a href="#self">test_img.jpg <i class="fal fa-times"></i></a></li>
              </ul>
            </div>
          </div>
        </div>
        
        <div class="of_h user-phone consolMarginTop">
          <div class="float-left" style="width:27%">
            <h4 class="inline-block mr20">버튼</h4>
            <a href="#self" class="btnStyle1 backBlack">추가 +</a>
          </div>
          <div class="float-left" style="width:73%">
            <div class="of_h">
              <table class="table_skin1" style="width:100%">
                <colgroup>
                  <col style="width:20%">
                  <col style="width:20%">
                  <col>
                  <col style="width:80px">
                </colgroup>
                <thead>
                <tr>
                  <th class="text-center">타입</th>
                  <th class="text-center">버튼이름</th>
                  <th class="text-center">버튼링크</th>
                  <th class="text-center end"></th>
                </tr>
                </thead>
                <tbody class="of_h">
                  
                  <tr>  
                  <td class="text-left" rowspan="2">
                    <select name="userConsole_sub040302_1" class="float-left selectStyle2" style="width:100%">
                      <option value="">웹 링크</option>
                    </select>
                  </td>
                  <td class="text-center" rowspan="2"><input type="text" class="inputStyle float-left"></td>
                  <td class="text-left of_h">
                    <h6 class="font-normal float-left" style="width:20%">Mobile</h6>
                    <input type="text" class="inputStyle float-right" style="width:68%">
                  </td>                      
                  <td class="text-center end" rowspan="2">
                    <a href="#self" class="btnStyle1 backLightGray">삭제</a>
                  </td>
                  </tr>
                  <tr>
                  <td class="text-left of_h">
                    <h6 class="font-normal float-left" style="width:20%">PC</h6>
                    <input type="text" class="inputStyle float-right" style="width:68%">
                  </td>
                  </tr>

                  <tr>  
                  <td class="text-left" rowspan="2">
                    <select name="userConsole_sub040302_2" class="float-left selectStyle2" style="width:100%">>
                      <option value="">앱 링크</option>
                    </select>
                  </td>
                  <td class="text-center" rowspan="2"><input type="text" class="inputStyle float-left"></td>
                  <td class="text-left of_h">
                    <h6 class="font-normal float-left" style="width:20%">Mobile</h6>
                    <input type="text" class="inputStyle float-right" style="width:68%">
                  </td>                      
                  <td class="text-center end" rowspan="2">
                    <a href="#self" class="btnStyle1 backLightGray">삭제</a>
                  </td>
                  </tr>
                  <tr>
                  <td class="text-left of_h">
                    <h6 class="font-normal float-left" style="width:20%">PC</h6>
                    <input type="text" class="inputStyle float-right" style="width:68%">
                  </td>
                  </tr>

                    <tr>  
                  <td class="text-left">
                    <select name="userConsole_sub040302_3" class="float-left selectStyle2" style="width:100%">>
                      <option value="">봇 키워드</option>
                    </select>
                  </td>
                  <td class="text-center"><input type="text" class="inputStyle float-left"></td>
                  <td></td>
                  <td class="text-center end">
                    <a href="#self" class="btnStyle1 backLightGray">삭제</a>
                  </td>
                  </tr>

                  <tr>  
                  <td class="text-left">
                    <select name="userConsole_sub040302_4" class="float-left selectStyle2" style="width:100%">>
                      <option value="">메시지 전달</option>
                    </select>
                  </td>
                  <td class="text-center"><input type="text" class="inputStyle float-left"></td>
                  <td></td>
                  <td class="text-center end">
                    <a href="#self" class="btnStyle1 backLightGray">삭제</a>
                  </td>
                  </tr>
                  
                </tbody>
              </table>
            </div>
          </div>  
          <div class="mt20 float-right">
            <a href="#self" class="btnStyle2 backRed float-left" title="등록">등록</a>
            <a href="#self" class="btnStyle2 float-left ml10" title="취소">취소</a>
            <a href="#self" class="btnStyle2 backWhite float-left ml10" title="수정">수정</a>
          </div>
        </div>
      </div>
      <ImageManagePopUp @img-callback="fnCallbackImgInfo" :imgMngOpen.sync="imgMngOpen" :useCh="useCh" ref="imgMng"></ImageManagePopUp>
    </div>
  </div>
</template>

<script>
import ImageManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";

export default {
  name: 'frdTalkTemplateManage',
  components : {
    ImageManagePopUp
  },
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
        return '친구톡 템플릿 관리';
      }
    },
  },
  data() {
    return {
      imgMngOpen : false,
      imgUploadOpen : false,
      buttonLimitSize : 5,
      useCh : 'FRIENDTALK',
      isInsert : true,
      tmpltData : {}
    }
  },
  methods: {
    fnOpenImageManagePopUp(){
      this.$refs.imgMng.fnSearch();
      this.imgMngOpen = !this.imgMngOpen;
    },
  }
}
</script>