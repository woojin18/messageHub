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
                  <div v-if="tmpltData.emphasizeYn == 'Y'" class="text-sub-wrap" style="padding:10px;">
                    <p v-if="!$gfnCommonUtils.isEmpty(tmpltData.tmpltEmpsSubTitle)" class="text-sub_1">{{tmpltData.tmpltEmpsSubTitle}}</p>
                    <p v-if="!$gfnCommonUtils.isEmpty(tmpltData.tmpltEmpsTitle)" class="text-sub scroll-y3">{{tmpltData.tmpltEmpsTitle}}</p>
                  </div>
                  <div class="text-sub-wrap" style="padding:10px;">
                    <span v-html="$gfnCommonUtils.newLineToBr(tmpltData.tmpltContent)"></span>
                  </div>
                  <!-- <p class="text-sub_2">템플릿 테스트입니다.<br>템플릿 테스트 템플릿 테스트 템플릿 테스트 템플릿 테스트</p> -->
                  <div v-for="(buttonInfo, idx) in tmpltData.buttonList" :key="idx">
                    <a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.name)" class="btnStyle1 backLightGray width100_">{{buttonInfo.name}}</a>
                  </div>
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
              <select class="float-left selectStyle2" style="width:20%" v-model="tmpltData.senderKeyType" @change="fnSelectSenderKeyList">
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
              <input type="radio" id="emphasizeYn_N" name="emphasizeYn" value="N" v-model="tmpltData.emphasizeYn">
              <label for="emphasizeYn_N" class="mr30">선택 안 함</label>
              <input type="radio" id="emphasizeYn_Y" name="emphasizeYn" value="Y" v-model="tmpltData.emphasizeYn">
              <label for="emphasizeYn_Y">강조 표기형</label>
            </div>
          </div>
          <div v-if="tmpltData.emphasizeYn == 'Y'" class="of_h">
            <div class="float-left" style="width:22%"><h4>템플릿강조제목 *</h4></div>
            <div class="float-left" style="width:78%">
              <input type="text" class="inputStyle" placeholder="최대 2줄 23자(24자부터 말줄임 처리, 권장하지 않음)" v-model="tmpltData.tmpltEmpsTitle" maxlength="50">
            </div>
          </div>
          <div v-if="tmpltData.emphasizeYn == 'Y'" class="of_h">
            <div class="float-left" style="width:22%"><h4>템플릿강조부제목 *</h4></div>
            <div class="float-left" style="width:78%">
              <input type="text" class="inputStyle" placeholder="최대 2줄 18자 (19자부터 말줄임 처리, 권장하지 않음)" v-model="tmpltData.tmpltEmpsSubTitle" maxlength="50">
            </div>
          </div>
          <div class="of_h">
            <div class="float-left" style="width:22%"><h4>내용 *</h4></div>
            <div class="float-left" style="width:78%">
              <!-- 템플릿 내용/부가 정보/광고성 메시지 합 최대 1,000자 -->
              <textarea class="textareaStyle height190" v-model="tmpltData.tmpltContent" placeholder="템플릿 내용 최대 1,000자" maxlength="1000"></textarea>
            </div>
          </div>
          <!-- TODO 카테고리 작업해야됨 -->
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
          <!--// TODO 카테고리 작업해야됨 -->
          <div class="of_h">
            <div class="float-left" style="width:22%">
              <h4 class="inline-block mr20">버튼</h4><a @click="fnAddButton" class="btnStyle1 backBlack">추가 +</a>
            </div>
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
                  <template v-for="(buttonInfo, idx) in tmpltData.buttonList">
                    <tr :key="idx">
                      <td class="text-left" :rowspan="buttonInfo.type == 'WL' || buttonInfo.type == 'AL' ? '2' : '1'">
                        <select class="float-left selectStyle2" style="width:100%" v-model="buttonInfo.type" @change="fnChgBtnType(idx)">
                          <option v-for="bottonType in bottonTypeList" :key="bottonType.type" :value="bottonType.type">{{bottonType.name}}</option>
                        </select>
                      </td>
                      <td class="text-center" :rowspan="buttonInfo.type == 'WL' || buttonInfo.type == 'AL' ? '2' : '1'">
                        <input v-if="buttonInfo.type == 'AC'" type="text" class="inputStyle float-left" v-model="buttonInfo.name" disabled maxlength="20">
                        <input v-else type="text" class="inputStyle float-left" v-model="buttonInfo.name" maxlength="20">
                      </td>
                      <td :class="buttonInfo.type == 'WL' || buttonInfo.type == 'AL' ? 'text-left' : 'text-left of_h'">
                        <h6 v-if="buttonInfo.type == 'DS'" class="float-left" v-html="bottonDSDescription"></h6>
                        <h6 v-if="buttonInfo.type == 'WL'" class="float-left" style="width:20%">Mobile*</h6>
                        <h6 v-if="buttonInfo.type == 'AL'" class="float-left" style="width:20%">Android*</h6>
                        <input v-if="buttonInfo.type == 'WL' || buttonInfo.type == 'AL'" type="text" class="inputStyle float-left" style="width:80%">
                      </td>
                      <td class="text-center end" :rowspan="buttonInfo.type == 'WL' || buttonInfo.type == 'AL' ? '2' : '1'">
                        <a @click="fnDelButton(idx)" class="btnStyle1 backLightGray">삭제</a>
                      </td>
                    </tr>
                    <tr v-if="buttonInfo.type == 'WL' || buttonInfo.type == 'AL'" :key="idx+'_sub'">
                      <td class="text-left">
                        <div v-if="buttonInfo.type == 'WL'">
                          <h6 class="float-left" style="width:20%">PC</h6>
                          <input type="text" class="inputStyle float-left" style="width:80%" v-model="buttonInfo['url_pc']" maxlength="200">
                        </div>
                        <div v-if="buttonInfo.type == 'AL'">
                          <h6 class="float-left" style="width:20%">IOS*</h6>
                          <input type="text" class="inputStyle float-left" style="width:80%" v-model="buttonInfo['scheme_ios']" maxlength="200">
                        </div>
                      </td>
                    </tr>
                  </template>
                </tbody>
              </table>
              
            </div>
          </div>
          
          <div class="mt20 float-right">
            <a href="#self" class="btnStyle2 backRed float-left ml10" title="승인요청">승인요청</a>
            <a href="#self" class="btnStyle2 backWhite float-left ml10" title="수정요청">수정요청</a>
            <a href="#self" class="btnStyle2 float-left ml10">취소</a>
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
      senderKeyList : [],
      groupKeyList : [],
      useCh : 'ALIMTALK',
      buttonLimitSize : 5,
      bottonACName : '채널 추가',
      bottonDSDescription : '카카오 메세지에 택배사 명과 송장번호를 기재한 후, 배송 조회 버튼을 추가하시면 메세지에서 택배사 명과 송장번호를 추출하여 배송 조회 카카오 검색페이지 링크가 자동으로 생성됩니다. 카카오에서 지원하는 택배사명과 운송장번호가 알림톡 메시지 내에 포함된 경우에만 배송조회 버튼이 표시됩니다. 배송 조회가 가능한 택배사는 <span style="color:#e11d21"><strong>카카오와 해당 택배사와의 계약 관계에 의해 변동될 수 있음을 유의해주시기 바랍니다.</strong></span>',
      bottonTypeList : [
        {type:'DS', name:'배송 조회'},
        {type:'WL', name:'웹 링크'},
        {type:'AL', name:'앱 링크'},
        {type:'BK', name:'봇 키워드'},
        {type:'MD', name:'메시지 전달'},
        {type:'BC', name:'상담톡 전환'},
        {type:'BT', name:'봇 전환'},
        {type:'AC', name:'채널 추가'},  //광고 추가/복합형만
      ],
      tmpltData : {
        senderKeyType : 'NOMAL',  //NOMAL, GROUP
        senderKey : '',
        tmpltName : '',
        emphasizeYn: 'N',  //강조여부
        tmpltEmpsTitle: '',
        tmpltEmpsSubTitle: '',
        tmpltContent: '',
        buttonList:[],
      }
    }
  },
  mounted() {
    this.fnSelectSenderKeyList();
  },
  methods: {
    fnAddButton(){
      if(this.tmpltData.buttonList.length < this.buttonLimitSize){
        const baseButtonInfo = {
          name : '',
          type : 'DS',
        };
        this.tmpltData.buttonList.push(baseButtonInfo);
      } else {
        confirm.fnAlert(this.componentsTitle, '버튼은 최대 '+this.buttonLimitSize+'개 까지 입력 가능합니다.');
      }
    },
    fnDelButton(idx){
      if(idx <= this.tmpltData.buttonList.length){
        this.tmpltData.buttonList.splice(idx, 1);
      }
    },
    fnChgBtnType(idx){
      const vm = this;
      Object.keys(this.tmpltData.buttonList[idx]).forEach(function(key){
        if(key != 'type'){
          if(key == 'name' && vm.tmpltData.buttonList[idx]['type'] == 'AC'){
            vm.tmpltData.buttonList[idx]['name'] = vm.bottonACName;
          } else {
            delete vm.tmpltData.buttonList[idx][key];
          }
        }
      });
    },
    //카카오톡 발신 프로필키 리스트 조회
    fnSelectSenderKeyList(){
      const params = {kkoSvc: this.useCh, senderKeyType: this.tmpltData.senderKeyType};
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