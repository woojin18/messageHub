<template>
  <div>
    <!-- quickRight -->
    <div class="quickRightWrap">
      <ul class="quickRight">
        <li>
          <a href="#self" class="quick_inquiry" title="1:1 문의하기" data-toggle="modal" data-target="#Inquiry">
            <img src="@/assets/images/common/quickIcon_Inquiry.png" alt="">
          </a>
        </li>
        <li><a href="#" class="quick_top"><img src="@/assets/images/common/quickIcon_top.png" alt=""></a></li>
      </ul>
    </div>
    <!-- //quickRight -->

    <div id="contentWrap">
      <div class="serviceCon01 wow animated fadeInUp">
        <section>
          <h3 class="subTitle">쉽고 안전하고 스마트한 U+ 통합 메시징 클라우드를 소개합니다.</h3>
          <ul class="CloudIcon">
            <li>
              <img src="@/assets/images/common/user_sub01_icon01.png" alt="아이콘1">
              <p class="IconMainText">메시지 통합 플랫폼</p>
              <p class="IconSubText">푸시, 카카오톡, 문자 등<br>발송 채널 별로 분산된 시스템을<br>하나의 플랫폼으로 관리</p>
            </li>
            <li>
              <img src="@/assets/images/common/user_sub01_icon02.png" alt="아이콘2">
              <p class="IconMainText">비용 절감</p>
              <p class="IconSubText">정확한 타게팅 발송, 중복 발송 방지,<br>발송채널 우선순위 설정 등<br>누수되는 비용 지출 절감</p>
            </li>
            <li>
              <img src="@/assets/images/common/user_sub01_icon03.png" alt="아이콘3">
              <p class="IconMainText">메시지 발송 통계</p>
              <p class="IconSubText">도달률, 성공률, 수신확인여부 등<br>정확한 메시지 발송 성과 관리</p>
            </li>
            <li>
              <img src="@/assets/images/common/user_sub01_icon04.png" alt="아이콘4">
              <p class="IconMainText">통합 채널 관리</p>
              <p class="IconSubText">메시징 통합 채널 사용으로<br>모든 메시지 전송 및 발송 성공률 Up</p>
            </li>
          </ul>
        </section>
      </div>

      <div class="serviceCon02 wow animated fadeInUp">
        <section>
          <ul>
            <li>
              <img src="@/assets/images/common/Message_icon_or.png" alt="메세지아이콘 주황색">
              <h3 class="subTitle">모든 메시지 발송 채널을 담은<br>대체 불가 플랫폼</h3>
              <p class="subText">국내 최고 성능의 푸시 시스템을 기반으로<br>푸시, 카카오(알림톡/친구톡), RCS, SMS/LMS 순으로<br>하나의 플랫폼에서 다양한 채널로 메시지 순차 발송 및<br>비용절감 효과를 제공합니다.</p>
              <img src="@/assets/images/common/people01.png" alt="사람1" class="people01">
              <ul id="bx-pager">
                <li><a href="#" data-slide-index="0">Push</a></li>
                <li><a href="#" data-slide-index="1">Kakaotalk Bizmessage</a></li>
                <li><a href="#" data-slide-index="2">RCS</a></li>
                <li><a href="#" data-slide-index="3">SMS</a></li>
              </ul>
            </li>
            <li>
              <div class="serviceBxsliderWrap">
                <ul class="serviceBxslider">
                  <li><img  src="@/assets/images/common/phone_rolling_01.png" alt="핸드폰 롤링1"></li>
                  <li><img  src="@/assets/images/common/phone_rolling_02.png" alt="핸드폰 롤링2"></li>
                  <li><img  src="@/assets/images/common/phone_rolling_03.png" alt="핸드폰 롤링3"></li>
                  <li><img  src="@/assets/images/common/phone_rolling_04.png" alt="핸드폰 롤링4"></li>
                </ul>
              </div>
            </li>
          </ul>
        </section>
      </div>

      <div class="serviceCon03 wow animated fadeInUp">
        <section>
          <div class="image-center"><img src="@/assets/images/common/Message_icon_blue.png" alt="메세지아이콘 파란색"></div>
          <h3 class="subTitle">메시징 통합 플랫폼</h3>
          <p class="subText">하나의 플랫폼에서 다양한 유형의 메시지를<br>쉽고 편리하게 관리하세요.</p>
          <div class="chartWrap">
            <div class="image-center"><img src="@/assets/images/common/Message_Platform01.png" alt="메세지 플랫폼1"></div>
            <img src="@/assets/images/common/people02.png" alt="사람2" class="people02">
            <img src="@/assets/images/common/people03.png" alt="사람3" class="people03">
          </div>
        </section>
      </div>

      <div class="serviceCon04 wow animated fadeInUp">
        <section>
          <div class="textBox">
            <h3 class="subTitle">종단간 암호화기반<br>데이터 보안관리</h3>
            <p class="subTitle2">Amazon AWS</p>
            <p class="subText">U+ 통합 메시징 클라우드는 기업용 종단간 암호화 기반 데이터 보호체계인<br>E3(Enterprise Endpoint Encryption) 시스템을 기반으로 고객 데이터를<br>보호하고 관리합니다. 누구도 고객 데이터를 확인할 수 없어 안심하고 쓸 수 있는<br>안전한 클라우드 환경에서 U+ 통합 메시징 클라우드를 사용할 수 있습니다.</p>
          </div>
          <ul>
            <li>
              <img src="@/assets/images/common/AWS_icon01.png" alt="데이터 보안관리 아이콘1">
              <p class="AWS_MainText">다양한 보안 서비스 설정</p>
              <p class="AWS_SubText">기업들이 필요로 하는 모바일 환경에서의 보안 관리뿐만 아니라<br>파일 유출 관리 및 고급 보안 설정 기능을 준비중입니다.</p>
            </li>
            <li>
              <img src="@/assets/images/common/AWS_icon02.png" alt="데이터 보안관리 아이콘2">
              <p class="AWS_MainText">국내외 보안 인증 획득</p>
              <p class="AWS_SubText">U+ 통합 메시징 클라우드는 정보보호 관리체계에 있어 공인을 통한 신뢰 확보를 위해<br>다양한 국내외 인증을 획득하고 있습니다. ISO 27001, 27017, 27018,<br>27701, 27799 인증 및 CSA Star 인증을 통과하였습니다.</p>
            </li>
          </ul>
        </section>
      </div>

      <div class="serviceCon05 wow animated fadeInUp">
        <section>
          <h3 class="subTitle">U+ 통합메시징 Cloud를 지금 바로 사용해보세요!</h3>
          <p class="subText">하나로 이용할 수 있는 통합 메시징 시스템 U+ 통합 메시징 클라우드 서비스로<br>다양한 발송 메시지를 저렴하게 이용해보세요.</p>
          <div class="btn">
            <a href="#self" class="btnBcackRed">서비스가입</a>
            <a href="#self" class="btnBcackWhite">서비스가입</a>
          </div>
        </section>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'serviceIntro',
  mounted() {
    this.fnSetSlider();
  },
  methods: {
    fnSetSlider(){
      var slider = $('.serviceBxslider').bxSlider({
        pager: true,
        pagerCustom: '#bx-pager',
        touchEnabled : (navigator.maxTouchPoints > 0),
        pause: 6000
      });
    }
  }
}
</script>