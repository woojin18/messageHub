<template>
  <div>

	<article id="mainVisualWrap">
		<section>
			<ul class="mainBxslider">
				<li>
					<div class="mainVisualTitle CloudIconlList">
						<div>
							<h2 class="wow animated fadeInUp" data-wow-duration="1s">모든 메시지를 한번에<br><strong>U+ 메시지허브</strong></h2>
							<div class="wow animated fadeInUp" data-wow-delay=".3s" data-wow-duration="1s">
                <router-link :to="{ name: 'signUp' }" class="mvBtn" title="서비스 가입" style="margin-right: 3px;">가입하기</router-link>
								<a class="mvBtn ml20" title="serviceCon05_2">도입문의</a>
							</div>
							<p class="font-size20 font-normal mt40 wow animated fadeInUp" data-wow-delay=".6s" data-wow-duration="1s">메시지허브는 다양한 메시지 채널을 수용하여<br>발송부터 결과까지 한 번에 관리하는 기업 메시지 플랫폼입니다.</p>
							<p class="font-size20 font-600 mt20 wow animated fadeInUp" data-wow-delay=".6s" data-wow-duration="1s">#문자<span class="ml20">#RCS</span><span class="ml20">#알림톡</span><span class="ml20">#친구톡</span><span class="ml20">#앱푸시</span></p>
					  </div>
					</div>
					<img src="/se2/images/mainVisual1.jpg" alt="">
				</li>
			</ul>
		</section>
	</article>

		<article id="mainConWrap2">
			<section>			
				<ul class="CloudIcon CloudIconlList">
					<li class="wow animated fadeInLeft" data-wow-duration="1s">
						<img src="/se2/images/CloudIconlList1.png" alt="아이콘1">
						<div class="IconMainBox">
							<p class="IconMainText">통합 채널 관리</p>
							<div class="btn2">
								<a  class="btnBcackWhite" title="userIndex1-1">자세히 보기</a>					
							</div>
						</div>
					</li>
					<li class="wow animated fadeInLeft" data-wow-delay=".3s" data-wow-duration="1s">
						<img src="/se2/images/CloudIconlList2.png" alt="아이콘2">
						<div class="IconMainBox">
							<p class="IconMainText">비용 절감</p>
							<div class="btn2">
								<a class="btnBcackWhite" title="userIndex1-2">자세히 보기</a>					
							</div>
						</div>
					</li>
					<li class="wow animated fadeInLeft" data-wow-delay=".6s" data-wow-duration="1s">
						<img src="/se2/images/CloudIconlList3.png" alt="아이콘3">
						<div class="IconMainBox">
							<p class="IconMainText">쉬운 서비스 연동</p>
							<div class="btn2">
								<a class="btnBcackWhite" title="userIndex1-3">자세히 보기</a>					
							</div>
						</div>
					</li>
				</ul>
			</section>
		</article>



  		<div class="serviceCon03_2 wow animated fadeInUp">
			<section id="userIndex1-1">	
				<img src="/se2/images/Message_icon_or2.png" alt="메세지아이콘 파란색">
	      <h3 class="subTitle">메시지 도입하고 운영하는데<br>소중한 리소스를 낭비하지 마세요.</h3>
				<p class="subText">메시지허브만 통하면 다양한 메시지 채널을 모두 활용할 수 있습니다.<br>
        계약, 운영, 정산 뿐만 아니라 장애 대응, 발송 모니터링, 통계까지<br>
        일원화하여 한 번에 관리하고 서비스 받으세요.</p>			
        <div class="chartWrap">					
					<p class="chartWrap_sub">*실제 웹 발송 화면 이미지입니다</p>
					<img src="/se2/images/Message_Platform02.png" alt="메세지 플랫폼1">
				</div>
			</section>	
		</div>
		<!-- //serviceCon03_2 -->
		
		<!-- serviceCon02_2 -->
		<div class="serviceCon02_2 wow animated fadeInUp">
			<section id="userIndex1-2">	
				<img src="/se2/images/Message_icon_blue2.png" alt="메세지아이콘 주황색">
				<h3 class="subTitle">저가채널 우선 발송 기능으로<br>메시지 발송 비용을 절감하세요.</h3>
				<p class="subText">다양한 채널에 발송 순서를 정하면 순차 발송이 가능하여 100% 커버리지를 지원하고, <br>저가 우선 발송을 통해 비용을 절감할 수 있습니다.</p>
				<p class="chartWrap_sub">*실제 웹 발송 화면 이미지입니다</p>
				<img src="/se2/images/send2.png" alt="메세지 플랫폼1">
     </section> 
		</div>
		<!-- //serviceCon02_2 -->
		
		<!-- serviceCon04_2 -->
		<div class="serviceCon04_2 wow animated fadeInUp">
			<section id="userIndex1-3">	
				<div><img src="/se2/images/Message_icon_purple.png" alt="메세지아이콘 보라색"></div>
			  <h3 class="subTitle">더 이상 메시지 발송을 위해<br>클라이언트를 설치 운영하지 않아도 됩니다.</h3>
				<p class="subText">메시지허브의 REST API를 통해 쉽게 도입하고,<br>메시지 발송에 불필요한 업무를 줄이세요.</p>
				<img src="/se2/images/purple_chart.png" alt="차트1">
			</section>	
		</div>

    <div class="serviceCon05_2 wow animated fadeInUp">
			<section id="serviceCon05_2">	
				<div><img src="/se2/images/Message_icon_pink.png" alt="메세지아이콘 핑크색"></div>
				<h3 class="subTitle">U+ 메시지허브를 지금 바로 사용해보세요!</h3>
				<div class="quiryList of_h">		
					<input type="text" class="form-control mt15 float-left" style="width:48%" placeholder="회사명" v-model="inqueiryInputData.corpName" maxlength="50">
					<input type="text" class="form-control mt15 float-right" style="width:48%" placeholder="이름" v-model="inqueiryInputData.inputName" maxlength="20">
					<input type="text" class="form-control mt15 float-left" style="width:48%" placeholder="휴대폰 번호 ( - 없이 입력)" v-model="inqueiryInputData.hpNumber" maxlength="20">
					<input type="text" class="form-control mt15 float-right" style="width:48%" placeholder="E-mail" v-model="inqueiryInputData.email" maxlength="40">
					<textarea class="form-textarea height180  mt15" placeholder="궁금하신 내용을 적어주세요." v-model="inqueiryInputData.content" maxlength="4000"></textarea>
					<div class="quiryAgree float-left">
						<input type="checkbox" id="agree1" class="checkStyle2" value="서비스 이용약관 동의" v-model="inqueiryInputData.agree">
            <label for="agree1">[필수] 개인정보 수집 및 이용 동의에 동의합니다.</label> 
					</div>
					<a class="btnStyle2 backRed float-right" @click.prevent="fnRegisterInquiry" title="도입문의">도입문의</a>
				</div>
			</section>	
		</div>


    
  <QuickRight></QuickRight>
   <NoticeLayer ref="noticeLayer"></NoticeLayer>
  </div>
</template>

<script>
import QuickRight from "@/modules/main/components/bc-quickRight.vue";
import NoticeLayer from "@/modules/customer/components/bp-noticeLayer.vue";

import customereApi from "@/modules/customer/service/customerApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import tokenSvc from '@/common/token-service';

export default {
  name: 'consoleMain',
  components : {
    QuickRight,
    NoticeLayer
  },
  data(){
    return{
      isLogin : false,
      componentsTitle: '메인',
      inqueiryTypeList : [],
      inqueiryInputData: {
        agree: false,
        inputName: '',
        hpNumber : '',
        emailId : '',
        emailDomain : '',
        questType : '999',
        email : '',
        title : '도입문의',
        content :'',
        corpName : ''
      }
    }
  },
  watch: {
    'inqueiryInputData.hpNumber'(){
      return this.inqueiryInputData.hpNumber = this.inqueiryInputData.hpNumber.replace(/[^0-9]/g, '');
    }
  },
  created(){
    this.isLogin = !!tokenSvc.getToken();
  },
  mounted(){
    this.fnSetSlider()

		jQuery(".CloudIconlList a").click(function(event){
			var obj=jQuery(this).attr('title')
			var offset =jQuery("#"+obj).offset()
			jQuery('html, body').animate({scrollTop:offset.top}, 'slow')
		})

    	var slider = jQuery('.mainBxslider').bxSlider({
			mode: 'fade',
			pager: true,
			touchEnabled : (navigator.maxTouchPoints > 0),
			pause: 6000
		});
    this.fnOpenNoticePopup()
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
		},
    fnIsValid(){
      if(!this.inqueiryInputData.corpName){
        confirm.fnAlert(this.componentsTitle, '회사명을 입력해주세요.');
        return false;
      }
      if(!this.inqueiryInputData.inputName){
        confirm.fnAlert(this.componentsTitle, '이름을 입력해주세요.');
        return false;
      }
      if(!this.inqueiryInputData.hpNumber){
        confirm.fnAlert(this.componentsTitle, '휴대폰 번호를 입력해주세요.');
        return false;
      }
      if(this.inqueiryInputData.hpNumber.length < 10){
        confirm.fnAlert(this.componentsTitle, '잘못된 휴대폰 번호 입니다.');
        return false;
      }
      if(!this.inqueiryInputData.email){
        confirm.fnAlert(this.componentsTitle, 'E-mail 을 입력해주세요.');
        return false;
      }
      if(!this.inqueiryInputData.content){
        confirm.fnAlert(this.componentsTitle, '내용을 입력해주세요.');
        return false;
      }
      if(!this.inqueiryInputData.agree){
        confirm.fnAlert(this.componentsTitle, '개인정보 수집 및 이용 동의에 동의해주세요.');
        return false;
      }
      return true;
    },
    fnRegisterInquiry(){
      if(this.fnIsValid() == false) return;
      eventBus.$on('callbackEventBus', this.fnProcRegisterInquiry);
      confirm.fnConfirm(this.componentsTitle, "문의 하시겠습니까?", "확인");
    },
    async fnProcRegisterInquiry(){
      let params = Object.assign({}, this.inqueiryInputData);
      params.userId = this.inqueiryInputData.inputName;

      await customereApi.insertQuestBoard(params).then(response => {
        const result = response.data;
        if(result.success) {
          confirm.fnAlert(this.componentsTitle, '문의 되었습니다.');
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
        async fnOpenNoticePopup(){
      let noticePopupList = [];
      let noticeIdArr = [];
      let exceptNoticeIds = this.$cookies.get('exceptNoticeIds');
      if(!this.$gfnCommonUtils.isEmpty(exceptNoticeIds)){
        noticeIdArr = exceptNoticeIds.split(',');
      }

      const params = {
        sltPopupYn: 'Y',
        exceptNoticeIds: noticeIdArr
      };
      await customereApi.selectNoticeList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          noticePopupList = result.data;
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });

      const vm = this;
      let routeData = '';
      const noticePopupNm = 'noticePopup';
      const popupOtion = 'width=760,height=580,left=20,top=20,scrollbars=yes';
      noticePopupList.forEach(function(info){
        routeData = vm.$router.resolve({name: noticePopupNm, query: {noticeId: info.noticeId}});
        vm.fnOpenWindowPop(routeData.href, noticePopupNm+info.noticeId, popupOtion);
      });
    },
    fnOpenWindowPop(url, name, specs){
      window.open(url, name, specs);
    },
  }
}
</script>

	<style>
		.foot_type_user {margin:20px 0}
	</style>