<template>
  <div>
    <div class="contentHeader">
      <h2>SMS 발송</h2>
      <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="SMS 발송 이용안내">이용안내 <i class="fal fa-book-open"></i></a>
    </div>
    <!-- 본문 -->
    <div class="row">
      <div class="phone3 inline-block" style="width:30%">
        <div class="phoneFixed">
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
            <a href="#self" class="btnStyle1 backBlack" title="Push">Push</a>
            <a href="#self" class="btnStyle1 backWhite" title="Push">SMS</a>
          </div>
        </div>
      </div>
      <div class="of_h inline-block vertical-top consoleCon" style="width:60%">
        <div class="of_h user-phone">
          <div class="float-left" style="width:24%">
            <h4>01  발송정보</h4>
          </div>
          <div class="float-left" style="width:76%">
            <a href="#self" class="btnStyle1 backLightGray" data-toggle="modal" data-target="#Tamplet" title="템플릿 불러오기">템플릿 불러오기</a>
            <div class="of_h consolMarginTop">
              <div style="width:18%" class="float-left">
                <h5>발송유형</h5>
              </div>
              <div>
                <input type="radio" name="send" value="SMS" id="SMS" checked=""> <label for="SMS" class="mr30">SMS</label>
                <input type="radio" name="send" value="MMS" id="MMS"> <label for="MMS">MMS</label>
              </div>
            </div>
            <div class="of_h">
              <div style="width:18%" class="float-left">
                <h5>메시지구분 *</h5>
              </div>
              <div>
                <input type="radio" name="Sortation" value="ad" id="ad" checked=""> <label for="ad" class="mr30">광고성</label>
                <input type="radio" name="Sortation" value="info" id="info"> <label for="info">정보성</label>
              </div>
            </div>
          </div>
          
        </div>
        <hr>
        <div class="of_h user-phone">
          <div class="float-left" style="width:24%">
            <h4>02  메시지 내용</h4>
          </div>
          <div class="float-left" style="width:76%">
            <a href="#self" class="btnStyle1 backLightGray" data-toggle="modal" data-target="#contents" title="메시지 내용입력">내용입력</a>
            <div class="of_h consolMarginTop">
              <div style="width:18%" class="float-left">
                <h5>이미지</h5>
              </div>
              <div class="of_h" style="width:82%">
                <div class="float-left" style="width:24%"><a href="#self" class="btnStyle1 backLightGray width100_" data-toggle="modal" data-target="#image" title="이미지선택">이미지선택</a></div>
                <ul class="float-right attachList" style="width:74%; padding:5px 15px">
                  <li><a href="#self">test_img.jpg <i class="fal fa-times"></i></a></li>
                </ul>
              </div>
            </div>
          </div>
            
        </div>
        <hr>
        <div class="of_h user-phone">
          <div class="float-left" style="width:24%">
            <h4>03  수신자 선택</h4>
          </div>
          <div class="float-left" style="width:76%">
            <div class="of_h">
              <div style="width:18%" class="float-left">
                <h5>수신자 *</h5>
              </div>
              <div style="width:100%">
                <div>
                  <input type="radio" name="Recipient" value="Directly" id="Directly" checked=""> <label for="Directly" class="mr30">수신자 직접입력</label>
                  <input type="radio" name="Recipient" value="search" id="search"> <label for="search" class="mr30">주소록 검색</label>
                  <input type="radio" name="Recipient" value="excel" id="excel"> <label for="excel" class="mr10">엑셀 업로드</label>
                  <a href="#self" class="btnStyle1 backLightGray" title="샘플">샘플 <i class="far fa-arrow-to-bottom"></i></a>
                </div>
              </div>
            </div>
            <div class="of_h">
              <div class="of_h float-right" style="width:82%">
                <p>수신자 : 0명</p>
                <div class="float-right consolMarginTop" style="width:100%">
                  <textarea class="textareaStyle height120" placeholder="Ex) [{“userId”, “phoneNumber”}] => 1명 발송 [{“aaa”, “0101231234”}]"></textarea>
                </div>
              </div>
            </div>
          </div>
          
        </div>
        <hr>
        <div class="of_h user-phone">
          <div style="width:24%" class="float-left">
            <h4>04  발송옵션 선택</h4>
          </div>
          <div style="width:76%" class="float-left">
            <div class="of_h">
              <div class="float-left" style="width:18%">
                <h5>발송시간 *</h5>
              </div>
              <div class="float-left" style="width:26%">
                <input type="radio" name="time" value="right" id="right" checked=""> <label for="right" class="mr30">즉시</label>
                <input type="radio" name="time" value="Reservation" id="Reservation"> <label for="Reservation">예약</label>
              </div>
              <div class="float-left" style="width:18%">
                <input type="text" class="datepicker inputStyle" title="시작날짜 입력란">
              </div>
              <div class="float-right" style="width:36%">
                <input type="time" name="stime1" class="inputStyle" style="width:47%" value=""> : <input type="time" name="etime1" class="inputStyle" style="width:47%" value="">
              </div>
            </div>
            <div class="of_h consolMarginTop">
              <div style="width:18%" class="float-left">
                <h5>태그</h5>
              </div>
              <div class="of_h" style="width:82%">
                <input type="text" class="inputStyle" style="width:100%" placeholder="캠페인 ID를 입력해주세요.">
              </div>
            </div>
          </div>
        </div>
        <div class="mt20 float-right">
          <a href="#self" class="btnStyle2 float-left" title="테스트 발송" data-toggle="modal" data-target="#test">테스트 발송</a>
          <a href="#self" class="btnStyle2 backRed float-left ml10" title="발송">발송</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "sendSmsMain",
}
</script>
