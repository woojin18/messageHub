<template>
  <div>
    <!-- subVisualWrap01 -->
    <article id="subVisualWrap01">
      <section>
        <div class="text-center">
          <h2 class="user_sub_h2">U+ 메시지허브</h2>
          <p>메시지허브는 다양한 메시지 채널을 수용하여<br>발송부터 결과까지 한 번에 관리하는 기업 메시지 플랫폼 입니다.</p>
        </div>
      </section>
    </article>
    <!-- //subVisualWrap01 -->

    <div id="contentWrap">
      <div class="serviceCon01 wow animated fadeInUp">
        <section>			
          <h3 class="subTitle">U+ 메시지허브를 소개합니다.</h3>
          <ul class="CloudIcon">
            <li>
              <img src="/se2/images/user_sub01_icon03.png" alt="아이콘3">
              <p class="IconMainText">통합 채널 관리</p>
              <p class="IconSubText">문자+RCS, 카카오, PUSH<br>다수 메시지 채널을<br>메시지 허브로 편하게 관리 하세요.</p>
            </li>
            <li>
              <img src="/se2/images/user_sub01_icon02.png" alt="아이콘2">
              <p class="IconMainText">비용 절감</p>
              <p class="IconSubText">저가 우선 발송 기능을 통해<br>커버리지는 100%<br>Total 비용은 절감하세요.</p>
            </li>
            <li>
              <img src="/se2/images/user_sub01_icon05.png" alt="아이콘5">
              <p class="IconMainText">쉬운 서비스 연동</p>
              <p class="IconSubText">Rest API를 통해 <br>다양한 채널을 쉽게 연동하세요.</p>
            </li>
          </ul>
        </section>	
      </div>
      <hr>

      <div class="serviceCon03 wow animated fadeInUp">
        <section>	
          <div class="text-center"><img src="/se2/images/Message_icon_blue.png" alt="메세지아이콘 파란색"></div>
          <h3 class="subTitle">메시지 도입하고 운영하는데<br>소중한 리소스를 낭비하지 마세요.</h3>
          <p class="subText">메시지허브만 통하면 다양한 메시징 채널을 모두 활용할 수 있습니다.<br>계약, 운영, 정산 뿐만 아니라 장애 대응, 발송 모니터링, 통계까지 일원화 하여<br>한번에 관리하고 서비스 받으세요.</p>
          <div class="chartWrap">
            <div class="image-center"><img src="/se2/images/Message_Platform01.png" alt="메세지 플랫폼1"></div>
            <img src="/se2/images/people02.png" alt="사람2" class="people02">
            <img src="/se2/images/people03.png" alt="사람3" class="people03">
          </div>
        </section>	
      </div>

      <div class="serviceCon02 wow animated fadeInUp">
        <section>	
          <ul>
            <li>
              <img src="/se2/images/Message_icon_or.png" alt="메세지아이콘 주황색">
              <h3 class="subTitle">저가채널 우선 발송 기능으로<br>메시지 발송 비용을 절감하세요.</h3>
              <p class="subText">메시지 허브를 통해 다양한 채널로 메시지 순차 발송 및<br>저가 우선 발송을 통해 커버리지 100%를 지원하고<br>비용은 절감 됩니다.</p>
              <ul class="serviceCon02_list">
                <li>· SMS</li>
                <li>· RCS</li>
                <li>· Kakaotalk Bizmessage</li>
                <li>· Push</li>
              </ul>
            </li>
          </ul>
        </section>	
      </div>

      <div class="serviceCon03 wow animated fadeInUp">
        <section>	
          <div class="text-center"><img src="/se2/images/Message_icon_purple.png" alt="메세지아이콘 보라색"></div>
          <h3 class="subTitle">더 이상 메시지 발송을 위해<br>클라이언트 설치 운영하지 않아도 됩니다.</h3>
          <p class="subText">메시지 허브 Rest API를 통해 쉽게 도입하고,<br>메시지 발송에 불필요한 업무를 줄이세요.</p>
          <div class="chartWrap of_h">
            <img src="/se2/images/purple_chart1.png" alt="차트1">
            <img src="/se2/images/purple_chart2.png" alt="차트2" class="float-right">
          </div>
        </section>	
      </div>

      <div class="serviceCon05 wow animated fadeInUp">
        <section>	
          <h3 class="subTitle">U+ 메시지 허브를 지금 바로 사용해보세요!</h3>
          <p class="subText">하나로 이용할 수 있는 통합 메시징 시스템 U+ 메시지 허브 서비스로<br>다양한 발송 메시지를 저렴하게 이용해보세요.</p>
          <div class="btn">
            <a href="#" class="btnBcackRed" @click.prevent="signUp">서비스가입</a>					
          </div>
        </section>	
      </div>
    </div>
    <QuickRight></QuickRight>
  </div>
</template>

<script>
import QuickRight from "@/modules/main/components/bc-quickRight.vue";

export default {
  name: 'serviceIntro',
  components : {
    QuickRight
  },
  mounted() {
    this.fnSetSlider();
  },
  methods: {
    fnSetSlider(){
      jQuery('.serviceBxslider').bxSlider({
        pager: true,
        pagerCustom: '#bx-pager',
        touchEnabled : (navigator.maxTouchPoints > 0),
        pause: 6000
      });
    },
    signUp: function() {
			this.$router.push({name : "signUp"})
		}
  }
}
</script>