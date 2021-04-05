<template>
  <!-- 본문 -->
  <div class="row row-no-margin">
    <div class="phone3 inline-block" style="width:30%">
      <!-- phoneWrap -->
      <div class="phoneWrap">
        <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
        <div class="phoneTextWrap">
          <div class="phoneText1">
            <p>내용이 들어갑니다</p>
          </div>
        </div>
      </div>
      <!-- //phoneWrap -->
      <div class="phone_04_btn">
        <a href="#self" class="btnStyle3 black font-size14" title="Push">Push</a>
        <a href="#self" class="btnStyle3 white font-size14" title="Push">SMS</a>
      </div>
    </div>
    <div class="of_h inline-block vertical-top" style="width:60%">
      <div class="row user-phone of_h row-no-margin">
        <div style="width:24%" class="float-left">
          <p class="main-text">01  발송정보</p>
        </div>
        <div style="width:76%" class="float-left">
          <a href="#self" class="btnStyle3_1 gray font-size13 minwidthAuto height45 width130" data-toggle="modal" data-target="#Tamplet" title="템플릿 불러오기">템플릿 불러오기</a>
          <div class="of_h mt30">
            <div style="width:18%" class="float-left">
              <p class="sub-text">발송정책 *</p>
            </div>
            <div style="width:82%">
              <input type="radio" name="send" value="ALL" class="cBox" id="ALL" checked=""> <label for="ALL" class="payment mr30 radio mt0">광고성ALL</label>
              <input type="radio" name="send" value="FCM" class="cBox" id="FCM"> <label for="FCM" class="payment mr30 radio mt0">FCM</label>
              <input type="radio" name="send" value="APNS" class="cBox" id="APNS"> <label for="APNS" class="payment radio mt0">APNS</label>
            </div>
          </div>
          <div class="of_h mt30">
            <div style="width:18%" class="float-left">
              <p class="sub-text">메시지구분 *</p>
            </div>
            <div style="width:82%">
              <input type="radio" name="Sortation" value="ad" class="cBox" id="ad" checked=""> <label for="ad" class="payment mr30 radio mt0">광고성</label>
              <input type="radio" name="Sortation" value="info" class="cBox" id="info"> <label for="info" class="payment radio mt0">정보성</label>
            </div>
          </div>
        </div>

      </div>
      <hr class="mt50">

      <div class="row user-phone mt50 of_h row-no-margin">
        <div style="width:24%" class="float-left">
          <p class="main-text">02  메시지 내용</p>
        </div>
        <div style="width:76%" class="float-left">
          <a href="#self" class="btnStyle3_1 gray font-size13 minwidthAuto width130" data-toggle="modal" data-target="#contents" title="메시지 내용입력">내용입력</a>
          <a href="#self" class="btnStyle3_1 gray font-size13 minwidthAuto width130" data-toggle="modal" data-target="#send" title="대체발송 발신번호">대체발송 발신번호</a>
          <div class="of_h mt30">
            <div style="width:18%" class="float-left">
              <p class="sub-text">메시지타입 *</p>
            </div>
            <div style="width:82%">
              <input type="radio" name="content" value="txt" class="cBox" id="txt" checked=""> <label for="txt" class="payment mr30 radio mt0">기본</label>
              <input type="radio" name="content" value="img" class="cBox" id="img"> <label for="img" class="payment radio mt0">이미지</label>
            </div>
          </div>
          <div class="of_h mt30">
            <div style="width:18%" class="float-left">
              <p class="sub-text mt10">이미지</p>
            </div>
            <div class="of_h" style="width:82%">
              <a @click="fnOpenImageManagePopUp" class="btnStyle3_1 gray font-size13 minwidthAuto" style="width:21%" title="이미지선택">이미지선택</a>
              <input type="text" class="inputStyle float-right" style="width:77%">
            </div>
          </div>
          <div class="of_h mt30">
            <div style="width:18%" class="float-left">
              <p class="sub-text">대체발송</p>
            </div>
            <div>
              <input type="radio" name="alternative" value="none" class="cBox" id="none" checked=""> <label for="none" class="payment mr30 radio mt0">미사용</label>
              <input type="radio" name="alternative" value="SMS" class="cBox" id="SMS"> <label for="SMS" class="payment mr30 radio mt0">SMS</label>
              <input type="radio" name="alternative" value="LMS" class="cBox" id="LMS"> <label for="LMS" class="payment mr30 radio mt0">LMS</label>
              <input type="radio" name="alternative" value="MMS" class="cBox" id="MMS"> <label for="MMS" class="payment radio mt0">MMS</label>
            </div>
          </div>
        </div>

      </div>
      <hr class="mt50">

      <div class="row user-phone mt50 of_h row-no-margin">
        <div style="width:24%" class="float-left">
          <p class="main-text">03  수신자 선택</p>
        </div>
        <div style="width:76%" class="float-left">
          <div class="of_h">
            <div style="width:18%" class="height40 float-left">
              <p class="sub-text">수신자 *</p>
            </div>
            <div style="width:82%">
              <div>
                <input type="radio" name="Recipient" value="Directly" class="cBox" id="Directly" checked=""> <label for="Directly" class="payment mr30 radio mt0" data-toggle="modal" data-target="#Direct-input">수신자 직접입력</label>

                <input type="radio" name="Recipient" value="search" class="cBox" id="search"> <label for="search" class="payment radio mr30 mt0" data-toggle="modal" data-target="#Address">주소록 검색</label>
              </div>
              <div class="mt5">
                <input type="radio" name="Recipient" value="all" class="cBox" id="all"> <label for="all" class="payment radio mr30 mt0">앱사용자 전체발송</label>
                <input type="radio" name="Recipient" value="excel" class="cBox" id="excel"> <label for="excel" class="payment radio mt0">엑셀 업로드 <i class="far fa-arrow-to-top"></i></label>
              </div>
            </div>
          </div>
          <div class="of_h mt30">
            <div style="width:18%" class="float-left">
              <p class="sub-text mt10">APP_ID</p>
            </div>
            <div class="of_h" style="width:82%">
              <select name="userConsole_sub0201_1" class="selectStyle3 height45 font-size14" style="width:30%">
                <option value="">APP_ID</option>
              </select>
              <input type="text" class="inputStyle float-right" style="width:68%">
              <p class="font-size16 mt10">수신자 : 0명</p>
              <div class="float-right" style="width:100%">
                <textarea class="textareaStyle height120 mt20" placeholder='Ex) [{"userId", "phoneNumber"}] => 1명 발송 [{"aaa", "0101231234"}]'></textarea>
              </div>
            </div>
          </div>
        </div>

      </div>
      <hr class="mt50">

      <div class="row user-phone mt50 of_h row-no-margin">
        <div style="width:24%" class="float-left">
          <p class="main-text mt5">04  발송옵션 선택</p>
        </div>
        <div style="width:76%" class="float-left">
          <div class="of_h">
            <div class="float-left" style="width:18%">
              <p class="sub-text mt10">발송시간 *</p>
            </div>
            <div class="float-left" style="width:25%">
              <input type="radio" name="time" value="right" id="right" checked=""> <label for="right" class="payment mr30 radio">즉시</label>
              <input type="radio" name="time" value="Reservation" id="Reservation"> <label for="Reservation" class="payment radio">예약</label>
            </div>
            <div class="float-left" style="width:25%">
              <input type="text" class="datepicker inputStyle maxWidth160" title="시작날짜 입력란">
            </div>
            <div class="float-right" style="width:32%">
              <input type="time" name="stime1" class="inputStyle vertical-middle" style="width:47%" value=""> : <input type="time" name="etime1" class="inputStyle vertical-middle" style="width:47%" value="">
            </div>
          </div>
          <div class="of_h mt30">
            <div style="width:18%" class="float-left">
              <p class="sub-text mt10">태그</p>
            </div>
            <div class="of_h" style="width:82%">
              <input type="text" class="inputStyle mt5 height45" style="width:100%" placeholder="캠페인 ID를 입력해주세요.">
            </div>
          </div>
        </div>
      </div>
      <div class="mt30 float-right">
        <a href="#self" class="btnStyle5 white float-left width120" title="테스트 발송" data-toggle="modal" data-target="#test">테스트 발송</a>
        <a href="#self" class="btnStyle5 red float-left ml10 width120" title="발송">발송</a>
      </div>
    </div>
    <ImageManagePopUp :imgMngOpen.sync="imgMngOpen" :useCh="useCh" ref="imgMng"></ImageManagePopUp>
  </div>
  <!-- //본문 -->
</template>

<script>
import ImageManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
//import ImageUploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";

export default {
  name: "sendPushMain",
  components : {
    ImageManagePopUp,
    //ImageUploadPopUp
  },
  data() {
    return {
      imgMngOpen : false,
      //imgUploadOpen : false,
      useCh : 'PUSH',
    }
  },
  methods: {
    fnOpenImageManagePopUp(){
      this.$refs.imgMng.fnSearch();
      this.imgMngOpen = !this.imgMngOpen;
    },
/*     fnOpenImageUploadPopUp : function(){
      this.imgUploadOpen = !this.imgUploadOpen;
    }, */
    fnSetImageInfo(imgInfo) {
      alert(imgInfo.originFileName);
    }
  }
}
</script>