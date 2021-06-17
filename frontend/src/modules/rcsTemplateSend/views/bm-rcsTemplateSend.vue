<template>
		<article>
			<div class="contentHeader">
				<h2>RCS 발송</h2>
				<a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="이용안내">이용안내 <i class="fal fa-book-open"></i></a>
			</div>
			<!-- 본문 -->
			<div>
				<p class="color4 bgColor_f1 pd20">좌측 탭의 RCS 유형을 먼저 선택해 주십시오. 템플릿형은 광고성 메시지를 보내실 수 없습니다.</p>
			</div>
			<h4>RCS 상품</h4>
			<ul class="tab_s6" role="tablist">
				<li role="presentation" class="active"><a href="#productCate1" aria-controls="productCate1" role="tab" data-toggle="tab">템플릿형 <i class="fas fa-question-circle toolTip"><span class="toolTipText text-left" style="width:470px">정보성 메시지를 사전에 등록 및 승인을 받아 메시지를 발송할 수 있습니다.<br>스타일/서술 템플릿 형식은 승인완료된 양식만 발송 할 수 있습니다.<br>템플릿형은 광고성 메시지를 발송할 수 없습니다.</span></i></a></li>
				<li role="presentation"><a href="#productCate2" aria-controls="productCate2" role="tab" data-toggle="tab">포맷카드형 <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:380px">이미지와 버튼을 활용한 다양한 형태 구성이 가능합니다.<br>정보/안내/광고 등 모든 분야의 메시지 발송을 지원합니다.<br>8가지 레이아웃 구성을 제공합니다.</span></i></a></li>
			</ul>
			<div class="tab-content">
				<!--  tab1 -->
				<div role="tabpanel" class="tab-pane active" id="productCate1">
					<div class="templateBox">
						<!-- templateList -->
						<ul class="templateList">
							<li>
								<img src="@/assets/images/common/RCSTemplate1.png" alt="템플릿 승인(서술)"><h6>템플릿 승인(서술)</h6>
								<div class="consolMarginTop">
									<input v-model="templateRadioBtn" type="radio" name="RCSTemplate1" value="des" id="pushTemplate1-1" class="radioStyle" checked=""><label for="pushTemplate1-1"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:280px">브랜드 포탈에서 승인이 완료된<br>서술형 템플릿만 발송 할 수 있습니다.</span></i>
								</div>
							</li>
							<li>
								<img src="@/assets/images/common/RCSTemplate1.png" alt="템플릿 승인(스타일)"><h6>템플릿 승인(스타일)</h6>
								<div class="consolMarginTop">
									<input v-model="templateRadioBtn" type="radio" name="RCSTemplate1" value="style" id="pushTemplate1-2" class="radioStyle"><label for="pushTemplate1-2"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:280px">브랜드 포탈에서 승인이 완료된<br>스타일 템플릿만 발송 할 수 있습니다.</span></i>
								</div>
							</li>
							<li>
								<img src="@/assets/images/common/RCSTemplate2.png" alt="텍스트 미승인형"><h6>텍스트 미승인형</h6>
								<div class="consolMarginTop">
									<input v-model="templateRadioBtn" type="radio" name="RCSTemplate1" value="text" id="pushTemplate1-3" class="radioStyle"><label for="pushTemplate1-3"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">90자 이내의 단문의 안내성<br>메시지 발송에 적합합니다.</span></i>
								</div>
							</li>
							
						</ul>
						<!-- //templateList -->
					</div>
				</div>

				<!--  tab2 -->
				<div role="tabpanel" class="tab-pane" id="productCate2">
					<div class="templateBox">
						<!-- templateList -->
						<ul class="templateList">
							<li>
								<img src="@/assets/images/common/RCSTemplate3.png" alt="텍스트(SMS)"><h6>텍스트(SMS)</h6>
								<div class="consolMarginTop">
									<input type="radio" name="RCSTemplate2" value="Y" id="RCSTemplate2-1" class="radioStyle" checked=""><label for="RCSTemplate2-1"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
								</div>
							</li>
							<li>
								<img src="@/assets/images/common/RCSTemplate4.png" alt="텍스트(LMS)"><h6>텍스트(LMS)</h6>
								<div class="consolMarginTop">
									<input type="radio" name="RCSTemplate2" value="Y" id="RCSTemplate2-2" class="radioStyle"><label for="RCSTemplate2-2"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
								</div>
							</li>
							<li class="ml40">
								<img src="@/assets/images/common/RCSTemplate6.png" alt="세로형(short)"><h6>세로형 (short)</h6>
								<div class="consolMarginTop">
									<input type="radio" name="RCSTemplate2" value="Y" id="RCSTemplate2-6-1" class="radioStyle"><label for="RCSTemplate2-6-1"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
								</div>
							</li>
							<li class="ml40">
								<img src="@/assets/images/common/RCSTemplate6.png" alt="세로형(short)"><h6>세로형 (short)</h6>
								<div class="consolMarginTop">
									<input type="radio" name="RCSTemplate2" value="Y" id="RCSTemplate2-6-2" class="radioStyle"><label for="RCSTemplate2-6-2"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
								</div>
							</li>
							<li class="ml40">
								<img src="@/assets/images/common/RCSTemplate7.png" alt="세로형(Tall)"><h6>세로형 (Tall)</h6>
								<div class="consolMarginTop">
									<input type="radio" name="RCSTemplate2" value="Y" id="RCSTemplate2-7" class="radioStyle"><label for="RCSTemplate2-7"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
								</div>
							</li>
							<li class="ml40">
								<img src="@/assets/images/common/RCSTemplate8.png" alt="가로형(왼쪽)"><h6>가로형 (왼쪽)</h6>
								<div class="consolMarginTop">
									<input type="radio" name="RCSTemplate2" value="Y" id="RCSTemplate2-8" class="radioStyle"><label for="RCSTemplate2-8"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
								</div>
							</li>
							<li class="ml40">
								<img src="@/assets/images/common/RCSTemplate9.png" alt="가로형(오른쪽)"><h6>가로형 (오른쪽)</h6>
								<div class="consolMarginTop">
									<input type="radio" name="pushTemplate1" value="Y" id="RCSTemplate2-9" class="radioStyle"><label for="RCSTemplate2-9"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
								</div>
							</li>
							<li class="ml40">
								<img src="@/assets/images/common/RCSTemplate10.png" alt="캐러셀(short)"><h6>캐러셀 (short)</h6>
								<div class="consolMarginTop">
									<input type="radio" name="RCSTemplate2" value="Y" id="RCSTemplate2-10" class="radioStyle"><label for="RCSTemplate2-10"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
								</div>
							</li>
							<li>
								<img src="@/assets/images/common/RCSTemplate11.png" alt="캐러셀(Tall)"><h6>캐러셀 (Tall)</h6>
								<div class="consolMarginTop">
									<input type="radio" name="RCSTemplate2" value="Y" id="RCSTemplate2-11" class="radioStyle"><label for="RCSTemplate2-11"></label>
									<i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
								</div>
							</li>
						</ul>
						<!-- //templateList -->
					</div>
				</div>
			</div>
			<h4>내용입력</h4>
			<div class="of_h mt20">
				<div class="phone3 inline-block" style="width:30%">
					<!-- phoneWrap -->
					<div class="phoneWrap">
						<img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div class="phoneText1">
								<p><i class="fal fa-cart-arrow-down mr10"></i> 주문</p>
								<div class="scroll-y">
									<p class="mt15">테스트입니다.[한글]</p>
								</div>
								<p class="text-center mt20" style="color:#69C8FF">복사</p>
							</div>
						</div>
						<div class="phone_04_btn">
							<a href="#self" class="btnStyle1 backBlack" title="Push">Push</a>
							<a href="#self" class="btnStyle1 backWhite" title="Push">SMS</a>						
						</div>
					</div>
					<!-- //phoneWrap -->
				</div>
				<div class="of_h inline-block vertical-top consoleCon" style="width:60%">
					<div class="user-phone of_h">
						<div class="float-left" style="width:24%">
							<h4>01  메시지 내용</h4>
						</div>
						<div class="float-left" style="width:76%">
							<a v-if="templateRadioBtn!='text'" @click.prevent="fnOpenRcsTemplatePopup" activity="READ" href="#self" class="btnStyle1 backLightGray" data-toggle="modal" data-target="#Tamplet" title="RCS 템플릿 선택">RCS 템플릿 선택</a>
							<div class="of_h consolMarginTop">
								<div style="width:18%" class="float-left">
									<h5>복사가능여부</h5>
								</div>
								<div style="width:82%">
									<input type="radio" name="copy" value="no" id="no" checked=""> <label for="no" class="mr30">불가능</label>
									<input type="radio" name="copy" value="yes" id="yes"> <label for="yes" class="mr30">가능</label>
									<p class="txtCaption color4">※ 단말 내 메시지에 대한 복사가능여부 설정입니다.</p>
								</div>
							</div>
							<div class="of_h consolMarginTop">
								<div style="width:18%" class="float-left">
									<h5>발신번호 *</h5>
								</div>
								<div style="width:82%">
									<select name="userConsole_sub020201_1" class="selectStyle2" style="width:30%">
										<option value="">선택하세요</option>
									</select>
								</div>
							</div>
							<div class="of_h consolMarginTop">
								<div style="width:18%" class="float-left">
									<h5>대체발송</h5>
								</div>
								<div style="width:82%">
									<input type="radio" name="substitution" value="no2" id="no2" checked=""> <label for="no2" class="mr30">미사용</label>
									<input type="radio" name="substitution" value="SMS" id="SMS"> <label for="SMS" class="mr30">SMS</label>
									<input type="radio" name="substitution" value="LMS" id="LMS"> <label for="LMS" class="mr30" data-toggle="modal" data-target="#LMS2">LMS</label>
								</div>
							</div>	
						</div>	
					</div>
					<hr>
					<div class="user-phone of_h">
						<div class="float-left" style="width:24%">
							<h4>02  수신자 선택</h4>
						</div>
						<div class="float-left" style="width:76%">
							<div class="of_h">
								<div style="width:18%" class="float-left">
									<h5>수신자 *</h5>
								</div>
								<div class="of_h">
									<div>
										<input type="radio" name="Recipient" value="Directly" id="Directly" checked=""> <label for="Directly" class="mr30">수신자 직접입력</label>
										<input type="radio" name="Recipient" value="search" id="search"> <label for="search" class="mr30">주소록 검색</label>
										<input type="radio" name="Recipient" value="excel" id="excel"> <label for="excel">엑셀 업로드</label>
										<a href="#self" class="btnStyle1 backLightGray ml20" title="샘플">샘플 <i class="far fa-arrow-to-bottom"></i></a>
									</div>
									<div class="float-right consolMarginTop" style="width:100%">
										<textarea class="textareaStyle height120" placeholder="변수로 설정하고자 하는 내용을 {}표시로 작성해 주십시오.&#13;예) 이름과 출금일을 변수 설정: 예) {고객}님 {YYMMDD} 출금 예정입니다."></textarea>
										<p class="float-right">수신자 : 0명</p>
									</div>
								</div>
							</div>						
						</div>
					</div>
					<hr>
					<div class="user-phone of_h">
						<div class="float-left" style="width:24%">
							<h4>03  발송옵션 선택</h4>
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
					<div class="float-right mt20">
						<a href="#self" class="btnStyle2 float-left ml10" title="테스트 발송">테스트 발송</a>
						<a href="#self" class="btnStyle2 backRed float-left ml10" title="발송">발송</a>
					</div>
				</div>
			</div>	

			<RcsTemplatePopup :rcsTemplatePopOpen.sync="rcsTemplatePopOpen" :templateRadioBtn.sync="templateRadioBtn" ref="rcsTemplatePop"></RcsTemplatePopup>
		</article>
</template>

<script>
import RcsTemplatePopup from "@/modules/rcsTemplateSend/components/bp-rcsTemplatePop.vue";
import rcsTemplateSendApi from "@/modules/rcsTemplateSend/service/api.js";
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  name: "pushTemplateList",
  components: {
	  RcsTemplatePopup
  },
  data() {
    return {
		rcsTemplatePopOpen : false,		// RCS 템플릿 선택 버튼 
        templateRadioBtn : "des",       // 템플릿형 라디오 버튼
    }
  },
  mounted() {

  },
  methods: {
	fnOpenRcsTemplatePopup() {
		this.$refs.rcsTemplatePop.fnInit();
      	this.rcsTemplatePopOpen = !this.rcsTemplatePopOpen;
	}
  }
}
</script>