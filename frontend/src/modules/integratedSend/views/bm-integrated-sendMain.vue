<template>
  <div>
    <div class="contentHeader">
      <h2>통합 발송2</h2>
      <!-- <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="통합 발송 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
    </div>

    <!-- 본문 -->
    <div class="row">
      <div class="phone3 inline-block" style="width:30%">
        <div class="phoneFixed">
          <div class="tab-content">
            <!-- phoneWrap -->
            <div role="tabpanel" class="tab-pane active" id="productCate1">
              <div class="phoneWrap">
                <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
                <div class="phoneTextWrap">
                  <div class="phoneText1 scroll-y2">
                    <p>제목</p>
                  </div>
                  <p class="consolMarginTop">내용</p>
                </div>
              </div>
            </div>
            <div role="tabpanel" class="tab-pane" id="productCate2">
              <div class="phoneWrap">
                <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
                <div class="phoneTextWrap">
                  <div class="phoneText1 scroll-y2">
                    <p>제목</p>
                  </div>
                  <p class="consolMarginTop">내용</p>
                </div>
              </div>
            </div>
            <div role="tabpanel" class="tab-pane" id="productCate3">
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
            </div>
            <div role="tabpanel" class="tab-pane" id="productCate4">
              <div class="phoneWrap">
                <img src="@/assets/images/common/phoneMockup2_1.svg" alt="프리 템플릿">
                <div class="phoneTextWrap4">
                  <p>[광고]</p>
                  <div class="mt5">                  
                    <div class="text-sub-wrap">
                      <p class="text-sub scroll-y">친구톡 발송테스트</p>
                    </div>
                    <p class="text-sub_2">수신거부: 홈 &gt; 친구차단</p>
                  </div>
                </div>
              </div>
            </div>
            <div role="tabpanel" class="tab-pane" id="productCate5">
              <div class="phoneWrap">
                <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
                <div class="phoneTextWrap">
                  <div class="phoneText1 scroll-y2">
                    <p>발신번호</p>
                  </div>
                  <p class="consolMarginTop">내용</p>
                </div>
              </div>
            </div>
            <div role="tabpanel" class="tab-pane" id="productCate6">
              <div class="phoneWrap">
                <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
                <div class="phoneTextWrap">
                  <div class="phoneText1 scroll-y2">
                    <p>발신번호</p>
                  </div>
                  <div class="phoneText1 scroll-y3 consolMarginTop">
                    <p>제목</p>
                  </div>
                  <p class="consolMarginTop">내용</p>
                </div>
              </div>
            </div>
            <!-- //phoneWrap -->
          </div>
          
          <div class="phone_04_btn7">
            <ul>
              <li role="presentation" class="active"><a href="#productCate1" aria-controls="productCate1" role="tab" data-toggle="tab" title="Push">Push</a></li>
              <li role="presentation"><a href="#productCate2" aria-controls="productCate2" role="tab" data-toggle="tab" title="RCS">RCS</a></li>
              <li role="presentation"><a href="#productCate3" aria-controls="productCate3" role="tab" data-toggle="tab" title="알림톡">알림톡</a></li>
            </ul>
            <ul class="mt5">
              <li role="presentation"><a href="#productCate4" aria-controls="productCate4" role="tab" data-toggle="tab" title="친구톡">친구톡</a></li>
              <li role="presentation"><a href="#productCate5" aria-controls="productCate5" role="tab" data-toggle="tab" title="SMS">SMS</a></li>
              <li role="presentation"><a href="#productCate6" aria-controls="productCate6" role="tab" data-toggle="tab" title="MMS">MMS</a></li>
            </ul>
          </div>
          
          
        </div>
      </div>
      <div class="of_h inline-block vertical-top consoleCon" style="width:60%">
        <div class="of_h user-phone">
          <div class="float-left" style="width:24%">
            <h4>01  템플릿</h4>
          </div>
          <div class="float-left" style="width:76%">
            <div class="of_h">
              <div style="width:22%" class="float-left">
                <!-- <h5>텍스트  형,  정보 형</h5> -->
                <h5>{{tmpltData.msgTypeName}}, {{tmpltData.msgKindName}}</h5>
              </div>
              <div v-if="fnContainsChannel('PUSH')" style="width:78%">
                <h5>발송정책 : {{tmpltData.PUSH.sendType}}</h5>
                <!-- 
                <input type="radio" name="serviceCode" value="ALL" id="serviceCode_ALL" v-model="tmpltData.PUSH.sendType" disabled>
                <label for="serviceCode_ALL" class="mr30">ALL</label>
                <input type="radio" name="serviceCode" value="FCM" id="serviceCode_FCM" v-model="tmpltData.PUSH.sendType" disabled>
                <label for="serviceCode_FCM" class="mr30">FCM</label>
                <input type="radio" name="serviceCode" value="APNS" id="serviceCode_APNS" v-model="tmpltData.PUSH.sendType" disabled>
                <label for="serviceCode_APNS">APNS</label>
                -->
              </div>
              <div class="float-right" style="width:78%">
                <input type="text" class="inputStyle" :value="tmpltData.tmpltTitle" disabled>
              </div>
            </div>
          </div>
        </div>
        
        <hr>

        <div class="of_h user-phone">
          <div class="float-left" style="width:24%">
            <h4>02  수신자 선택</h4>
          </div>
          <div class="float-left" style="width:76%">
            <div class="of_h">
              <div style="width:22%" class="height40 float-left">
                <h5>수신자 *</h5>
              </div>
              <div style="width:100%">
                <div>
                  <!-- 
                  <input type="radio" name="Recipient" value="Directly" id="Directly" checked=""> <label for="Directly" class="mr30">수신자 직접입력</label>
                  <input type="radio" name="Recipient" value="search" id="search"> <label for="search" class="mr30">주소록 검색</label>
                  <input type="radio" name="Recipient" value="excel" id="excel"> <label for="excel" class="mr10">엑셀 업로드</label>
                  -->
                  <input type="radio" id="cuInputType_DICT" name="cuInputType" value="DICT" v-model="sendData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType" activity="READ">
                  <label for="cuInputType_DICT" class="mr30">수신자 직접입력</label>
                  <input type="radio" id="cuInputType_ADDR" name="cuInputType" value="ADDR" v-model="sendData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType" activity="READ">
                  <label for="cuInputType_ADDR" class="mr30">주소록 검색</label>
                  <input type="radio" id="cuInputType_EXCEL" name="cuInputType" value="EXCEL" v-model="sendData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType" activity="READ">
                  <label for="cuInputType_EXCEL" class="mr10">엑셀 업로드</label>
                  <a href="#" @click.prevent="fnExcelTmplteDownLoad" class="btnStyle1 backLightGray" title="샘플">샘플 <i class="far fa-arrow-to-bottom"></i></a>
                  <input ref="excelFile" type="file" style="display:none;">
                </div>
              </div>
            </div>
            <div v-if="fnContainsChannel('PUSH')" class="of_h consolMarginTop">
              <div style="width:22%" class="float-left">
                <h5>APP_ID</h5>
              </div>
              <div class="of_h" style="width:78%">
                <input type="text" class="inputStyle" style="width:100%" :value="tmpltData.PUSH.appId" disabled>
              </div>
            </div>
            <div class="of_h consolMarginTop">
              <div class="of_h float-right" style="width:78%">
                <div class="float-right" style="width:100%">
                  <textarea class="textareaStyle height120" v-model="sendData.cuInfo" disabled></textarea>
                </div>
                <p class="float-right">발송 대상자 : {{recvCnt}}명</p>
              </div>
            </div>
          </div>
          
        </div>
        <hr>

        <div class="of_h user-phone">
          <div style="width:22%" class="float-left">
            <h4>03  발송옵션 선택</h4>
          </div>
          <div style="width:78%" class="float-left">
            <div class="of_h">
              <div class="float-left" style="width:22%">
                <h5>발송시간 *</h5>
              </div>
              <div class="float-left" style="width:23%">
                <input type="radio" id="rsrvSendYn_N" value="N" v-model="sendData.rsrvSendYn">
                <label for="rsrvSendYn_N" class="mr20">즉시</label>
                <input type="radio" id="rsrvSendYn_Y" value="Y" v-model="sendData.rsrvSendYn">
                <label for="rsrvSendYn_Y">예약</label>
              </div>
              <div v-if="sendData.rsrvSendYn == 'Y'" class="float-left" style="width:18%">
                <Calendar @update-date="fnUpdateRsrvDate" calendarId="rsrvDate" classProps="datepicker inputStyle" :initDate="sendData.rsrvDate"></Calendar>
              </div>
              <div v-if="sendData.rsrvSendYn == 'Y'" class="float-right" style="width:36%">
                <select class="selectStyle2" style="width:47%" v-model="sendData.rsrvHH">
                  <option value="00">00</option>
                  <option v-for="hh in 23" :key="hh" :value="hh > 9 ? hh : '0'+hh">{{hh > 9 ? hh : '0'+hh}}</option>
                </select>
                : <select class="selectStyle2" style="width:47%" v-model="sendData.rsrvMM">
                  <option value="00">00</option>
                  <option v-for="mm in 5" :key="mm" :value="mm+'0'">{{mm+'0'}}</option>
                </select>
              </div>
            </div>
            <div class="of_h consolMarginTop">
              <div style="width:22%" class="float-left">
                <h5>태그</h5>
              </div>
              <div class="of_h" style="width:78%">
                <input type="text" class="inputStyle" style="width:100%" placeholder="캠페인 ID를 입력해주세요." v-model="sendData.campaignId" maxlength="20">
              </div>
            </div>
          </div>
        </div>

        <hr>

        <div class="of_h user-phone">
          <div class="float-left" style="width:22%">
            <h4>04  테스트 발송</h4>
          </div>
          <div class="float-left" style="width:78%">
            <p>모든 채널에 메시지를 보냅니다.</p>
            <a href="#self" class="btnStyle1 backLightGray consolMarginTop" title="테스트 발송">테스트 발송</a>
          </div>
          
        </div>
        <div class="mt20 float-right">
          <a href="#self" class="btnStyle2 backRed float-left mr10" title="발송">발송</a>
          <router-link :to="{ name: 'integratedSend' }" tag="a" class="btnStyle2 float-left">목록</router-link>
        </div>
      </div>
    </div>
    <DirectInputPopup :directInputOpen.sync="directInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid" :recvInfoLst="sendData.recvInfoLst"></DirectInputPopup>
    <AddressInputPopup :addressInputOpen.sync="addressInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid"></AddressInputPopup>
  </div>
</template>

<script>
import DirectInputPopup from "@/modules/message/components/bp-directInput.vue";
import AddressInputPopup from "@/modules/message/components/bp-addressInput.vue";
import Calendar from "@/components/Calendar.vue";

import integratedSendApi from '../service/integratedSendApi';
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: 'sendIntegMain',
  components : {
    DirectInputPopup,
    AddressInputPopup,
    Calendar
  },
  props: {
    tmpltCodeP: {
      type: String,
      require: true,
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '통합 발송';
      }
    },
  },
  data() {
    return {
      directInputOpen: false,
      addressInputOpen: false,
      recvCnt : 0,  //수신자명수
      tmpltData: {
        chTypeList: [],
      },
      sendData: {
        requiredCuid : false,  //app 로그인 ID 필수여부
        requiredCuPhone : false,  //수신자 폰번호 필수여부
        cuInputType:'DICT',  //DICT, ADDR, EXCEL
        cuInfo:'',
        tmpltCode: '',
        rsrvSendYn:'N',  //예약발송여부
        rsrvDate:this.$gfnCommonUtils.getCurretDate(),
        rsrvHH:'00',
        rsrvMM:'00',
        campaignId: '',
        contsVarNms: [], //메세지 내용 변수명
        recvInfoLst: [],  //수신자정보
      }
    }
  },
  mounted() {
    this.fnGetTmpltInfo();
  },
  methods: {
    fnUpdateRsrvDate(sltDate){
      this.sendData.rsrvDate = sltDate;
    },
    fnClickCuInputType(e){
      if(this.sendData.cuInputType == e.target.value){
        this.fnChgCuInputType('N');
      }
    },
    //수신자 입력 타입 변경시
    fnChgCuInputType(chgYn){
      if(this.$gfnCommonUtils.defaultIfEmpty(chgYn, 'Y') == 'Y'){
        this.fnCallbackRecvInfoLst(null);  //수신자 입력 타입 변경시 수신자 정보 초기화
      }
      if(this.fnSetContsVarNms() == false){
        return;
      }

      if(this.sendData.cuInputType == 'DICT'){  //직접입력
        //수신자 직접입력 팝업 호출
        this.directInputOpen = true;
      } else if(this.sendData.cuInputType == 'ADDR'){  //주소록
        //주소록 검색 팝업 호출
        this.addressInputOpen = true;
      } else if(this.sendData.cuInputType == 'EXCEL'){  //엑셀
        //엑셀파일찾기 호출
        this.$refs.excelFile.click();
      }
    },
    //수신자 정보 callback
    fnCallbackRecvInfoLst(recvInfoLst, addYn) {
      if(recvInfoLst != null){
        if(this.$gfnCommonUtils.defaultIfEmpty(addYn, 'N') == 'Y'){
          this.sendData.recvInfoLst = this.sendData.recvInfoLst.concat(recvInfoLst);
        } else {
          this.sendData.recvInfoLst = recvInfoLst;
        }
        //수신자 중복제거
        this.fnDelDuplRecvInfo();

        this.recvCnt = this.sendData.recvInfoLst.length;
        this.sendData.cuInfo = JSON.stringify(this.sendData.recvInfoLst);
      } else {
        this.recvCnt = 0;
        this.sendData.recvInfoLst = [];
        this.sendData.cuInfo = '';
      }
    },
    //수신자 중복 제거
    fnDelDuplRecvInfo(){
      const vm = this;
      this.sendData.recvInfoLst = this.sendData.recvInfoLst.filter(function(item, i){
        return (
          vm.sendData.recvInfoLst.findIndex((item2) => {
            return item.phone === item2.phone
          }) === i
        );
      });
    },
    fnSetContsVarNms(){
      const vm = this;
      const rsvNmSet = new Set(['cuid', 'phone']);
      let conts = '';
      let chTmpltInfo = {};
      let varNms = [];
      let containRsvNm = false;

      this.tmpltData.chTypeList.forEach(ch => {
        chTmpltInfo = vm.tmpltData[ch];

        if(ch == 'PUSH'){
          conts += chTmpltInfo.msg;
          console.log('conts PUSH ===> ', chTmpltInfo.msg);
        } else if(ch == 'SMSMMS'){
          if(chTmpltInfo.chType == 'SMS'){
            conts += chTmpltInfo.smsInfo.msg;
            console.log('conts SMS ===> ', chTmpltInfo.smsInfo.msg);
          } else if(chTmpltInfo.chType == 'MMS'){
            conts += chTmpltInfo.mmsInfo.msg;
            console.log('conts MMS ===> ', chTmpltInfo.mmsInfo.msg);
          }
        } else if(ch == 'KAKAO'){
          if(chTmpltInfo.chType == 'FRIENDTALK'){
            conts += chTmpltInfo.friendtalk.msg;
            console.log('conts FRIENDTALK ===> ', chTmpltInfo.friendtalk.msg);
          } else if(chTmpltInfo.chType == 'ALIMTALK'){  //TODO : 확인
            conts += chTmpltInfo.alimtalk.msg;
          }
        } else if(ch == 'RCS'){
          //TODO : 확인
        }
      });

      console.log('conts COMPLATE ===> ', conts);

      conts.replace(/#\{(([a-z|A-Z|0-9|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|_])+)\}/g, function($0, $1) {
        if(rsvNmSet.has($1)){
          containRsvNm = true;
          return false;
        }
        varNms.push($1);
      });
      if(this.fnContainsChannel('RCS')){
        conts.replace(/\{\{(([a-z|A-Z|0-9|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|_])+)\}\}/g, function($0, $1) {
          if(rsvNmSet.has($1)){
            containRsvNm = true;
            return false;
          }
          varNms.push($1);
        });
      }
      if(containRsvNm){
        confirm.fnAlert(this.componentsTitle, '발송 내용 변수 cuid, phone 은 예약어로 사용하실 수 없습니다.');
        return false;
      } else {
        console.log('varNms ===> ', varNms);
        this.sendData.contsVarNms = this.fnSetArrayRemoveDupliVal(varNms);
        console.log('contsVarNms ===> ', this.sendData.contsVarNms);
        return true;
      }
    },
    //array에 중복 항목을 제거한다.
    fnSetArrayRemoveDupliVal(array){
      let seen = {};
      return array.filter(function(item) {
        return seen.hasOwnProperty(item) ? false : (seen[item] = true);
      });
    },
    //템플릿 정보 조회
    fnGetTmpltInfo(){
      let params = {tmpltCode: this.tmpltCodeP};
      integratedSendApi.selectIntegTmpltInfo(params).then(response => {
        let result = response.data;
        if(result.success) {
          console.log('result ===>> ', Object.assign({}, result));
          let tempData = Object.assign({}, result.data);
          tempData.chTypeList = tempData.chTypeList.split(',').map(item => { return item.trim(); });
          tempData.tmpltInfo = JSON.parse(tempData.tmpltInfo);
          
          this.fnSetTmpltInfoByChannel(tempData);
          this.tmpltData = Object.assign({}, tempData);
          console.log('tmpltData ===>> ', this.tmpltData);

          if(this.fnContainsChannel('PUSH')){
            this.sendData.requiredCuid = true;
          }
          if(this.fnContainsChannel('SMSMMS')
            || this.fnContainsChannel('KAKAO')
            || this.fnContainsChannel('RCS')){
            this.sendData.requiredCuPhone = true;
          }

          console.log('requiredCuid ===>> ', this.sendData.requiredCuid);
          console.log('requiredCuPhone ===>> ', this.sendData.requiredCuPhone);

        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnSetTmpltInfoByChannel(tempData){
      let chTypeList = tempData.chTypeList;
      let tmpltInfo = tempData.tmpltInfo;
      
      if(chTypeList != null && chTypeList.length > 0){
        chTypeList.forEach((chType, idx) => {
          tempData[chType] = tmpltInfo[idx];
        });
      }
    },
    //채널정보 존재 확인
    fnContainsChannel(ch){
      return this.tmpltData.chTypeList.includes(ch);
    },
    async fnExcelTmplteDownLoad(){
      if(this.fnSetContsVarNms() == false) return;
      var params = {
        contsVarNms : this.sendData.contsVarNms,
        requiredCuid: this.sendData.requiredCuid,
        requiredCuPhone: this.sendData.requiredCuPhone
      };
      await integratedSendApi.excelDownSendIntegRecvTmplt(params);
    },
  }
}
</script>