<template>
    <div>
		<article>
			<div class="contentHeader">
				<h2>RCS 템플릿</h2>
				<!-- <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="RCS 템플릿 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
			</div>

			<!-- 본문 -->
			<div class="border-line2 pd10">
				<p class="color4">RCS 템플릿은 서술형과 스타일형으로만 작성이 가능합니다.<br>RCS 템플릿은 승인과정을 통해서 등록이 됩니다. (저장 상태에서는 수정/삭제가 가능하고 승인요청 후에는 수정/삭제가 불가능 합니다.)<br>상태가 승인인 템플릿은 RCS 메시지 발송을 할 수 있습니다.</p>
			</div>

			<div class="row mt15">
				<div class="col-xs-12">
					<div class="menuBox">
						<div>
							<h4 style="width:10%" class="inline-block">템플릿 명</h4>
							<input v-model="templateNm" type="text" class="inputStyle" style="width:24%" title="템플릿 명 입력란"><p class="color3 inline-block ml10" style="width:10%">0/30자</p>
							<h4 class="inline-block" style="width:10%">템플릿코드</h4>
							<input v-model="templateCode" type="text" class="inputStyle" style="width:24%" title="템플릿 명 입력란" disabled>
						</div>
						<div class="consolMarginTop">
							<h4 class="inline-block" style="width:10%">브랜드명</h4>
							<select v-model="brandNm" name="userConsole_sub040202_1" class="selectStyle2" style="width:24%" title="브랜드명 선택란">
								<option v-for="option in brandNmList" v-bind:value="option.BRAND_ID">
									{{option.BRAND_NAME}}
								</option>
							</select>
						</div>
					</div>
				</div>				
			</div>

			<h4>RCS 상품</h4>
			<ul class="tab_s6" role="tablist">
				<li role="presentation" class="active" id="desTab"><a href="#productCate1" aria-controls="productCate1" role="tab" data-toggle="tab">서술형 <i class="fas fa-question-circle toolTip"><span class="toolTipText text-left" style="width:380px">문자메시지처럼 문장 형태의 텍스트를 입력할 수 있습니다.<br>에뮬레이터에 노출되는 이미지는 변경이 되지 않습니다.<br>최대 90자까지(공백포함) 입력이 가능합니다.</span></i></a></li>
				<li role="presentation" id="styleTab"><a href="#productCate2" id="styleTag" aria-controls="productCate2" role="tab" data-toggle="tab">스타일형 <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:470px">1행에 1개 또는 2개의 Cell을 선택적으로 사용할 수 있습니다.<br>최대 10행까지 입력이 가능합니다.<br>라인 체크박스 클릭 시 해당 행 하단에 라인을 삽입할 수 있습니다.<br>Cell 설정된 값은 좌측 에뮬레이터 영역에서 실시간으로 확인이 가능합니다.</span></i></a></li>
			</ul>

			<div class="tab-content">
				<!--  tab1 -->
				<div role="tabpanel" class="tab-pane active" id="productCate1">
					<div class="templateBox">
						<!-- templateList -->
						<div class="of_h mt30">
							<div class="phone3 inline-block" style="width:32%">
								<!-- phoneWrap -->
								<div class="phoneWrap">
									<img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
									<div class="phoneTextWrap">
										<div class="phoneText1">
											<p><img :src="desImgSrc" alt="주문 아이콘" style="width:70px;"></p>
											<div class="scroll-y">
												<p class="mt15 lc-1">{{desContentsExam}}</p>
											</div>
											<div v-for="n in btnCnt">
												<p class="text-center" style="color:#69C8FF">{{btnNm[n-1]}}</p>
											</div>
										</div>
									</div>
								</div>
								<!-- //phoneWrap -->
							</div>
							<div class="of_h inline-block vertical-top consoleCon" style="width:60%">
								<div class="of_h user-phone">
									<div class="float-left" style="width:22%">
										<h4>유형 *</h4>
									</div>
									<div class="float-left" style="width:78%">
										<div class="of_h">
											<select @change="fnSetDesformImg" v-model="desFormNm" name="userConsole_sub040202_2" class="selectStyle2">
												<option v-for="option in desFormNmList" v-bind:value="option.MESSAGEBASEFORM_ID">
													{{option.FORM_NAME}}
												</option>
											</select>
										</div>
									</div>									
								</div>
								<div class="of_h user-phone">
									<div class="float-left" style="width:22%">
										<h4>내용 *</h4>
									</div>
									<div class="float-left" style="width:78%">
										<div class="of_h">
											<textarea class="textareaStyle height120" :placeholder="desContentsPlaceHoder" v-model="desContents"></textarea>
										</div>
										<p class="float-right">{{desContentsCnt}}/90자</p>
									</div>									
								</div>
								<div class="of_h user-phone">
									<div class="float-left" style="width:22%">
										<h4 class="mr10 inline-block">버튼</h4>
										<a href="#self" @click.prevent="fnClickAddBtn" class="btnStyle1 backBlack">추가 +</a>
									</div>
									<div class="float-left" style="width:78%">
										<div class="of_h">
											<table class="table_skin1 mt0" style="width:100%">
												<colgroup>
													<col style="width:22%">
													<col style="width:20%">
													<col>
													<col style="width:15%">
												</colgroup>
												<thead>
													<tr>
													<th class="text-center">타입</th>
													<th class="text-center">버튼이름</th>
													<th class="text-center">내용</th>
													<th class="text-center end">구분</th>
													</tr>
												</thead>
												<tbody>
													<tr v-for="n in btnCnt">
														<td class="text-center">
															<select @change="fnBtnSelectEvent(n-1)" v-model="selectBtn[n-1]" name="userConsole04060202_1" class="selectStyle2" style="width:100%">
																<option value="urlAction">URL 링크</option>
																<option value="clipboardAction">복사하기</option>
																<option value="dialerAction">전화걸기</option>
																<option value="calendarAction">일정추가</option>
																<option value="mapAction">지도맵</option>
															</select>
														</td>
														<td class="text-left"><input v-model="btnNm[n-1]" type="text" class="inputStyle"></td>
														<td v-if="selectBtn[n-1]!='calendarAction'" class="text-center"><input v-model="contents[n-1]" type="text" class="inputStyle" :placeholder="btnInputHolder[n-1]" :disabled="selectBtn[n-1]=='mapAction'"></td>
														<td v-if="selectBtn[n-1]=='calendarAction'" class="text-center">
															<input v-model="calendarTitle[n-1]" type="text" class="inputStyle" placeholder="제목입력">
															<input v-model="calendarDes[n-1]" type="text" class="inputStyle consolMarginTop" placeholder="내용입력">
															<div class="consolMarginTop of_h">
																<span class="float-left mt5" style="width:20%">시작일</span>
																<div class="float-right" style="width:80%">
																	<Calendar @update-date="fnDesUpdateStartDate" :params="n-1" :calendarId="desStartDate[n-1]" classProps="datepicker inputStyle" :initDate="desInitStartDate[n-1]"></Calendar>
																</div>
															</div>
															<div class="consolMarginTop of_h">
																<span class="float-left mt5" style="width:20%">종료일</span>
																<div class="float-right" style="width:80%">
																	<Calendar @update-date="fnDesUpdateEndDate" :params="n-1" :calendarId="desEndDate[n-1]" classProps="datepicker inputStyle" :initDate="desInitEndDate[n-1]"></Calendar>
																</div>
															</div>
														</td>
														<td class="text-center end"><a href="#" @click.prevent="fnClickMinus(n-1)" title="이전버튼"><i class="far fa-minus channelBtn"></i></a></td>
													</tr>
												</tbody>
											</table>
											
										</div>
									</div>									
								</div>
								<div class="consolCheck consolMarginTop"><input type="checkbox" id="agree2" class="checkStyle2" value="agree2"><label for="agree2" class="color4">정보성 메시지만 보낼 수 있으며, 광고 등 정책에 위배되는 메시지 발송 시  템플릿 사용이 중지될 수 있음을 동의합니다.</label></div>
								<div class="of_h">
									<div class="mt20 text-right" style="width:100%">
										<a href="#self" v-if="deleteBtn" @click.prevent="rcsTemplateDelete" class="btnStyle2 backWhite ml10" title="삭제">삭제</a>
										<a href="#self" v-if="cancelBtn" @click.prevent="rcsTemplateCancel" class="btnStyle2 backWhite ml10" title="삭제">취소</a>
										<a href="#self" v-if="updateBtn" @click.prevent="recTemplateIns('UPT','des')" class="btnStyle2 backRed  ml10" title="수정요청">수정요청</a>
										<a href="#self" v-if="insertBtn" @click.prevent="recTemplateIns('INS','des')" class="btnStyle2 backRed  ml10" title="승인요청">승인요청</a>
										<a href="#self" @click.prevent="returnRcsTemplateList" class="btnStyle2 ml10" title="목록">목록</a>
									</div>
								</div>
							</div>							
						</div>	
						<!-- //templateList -->
					</div>
				</div>

				<!--  tab2 -->
				<div role="tabpanel" class="tab-pane" id="productCate2">
					<div class="templateBox">
						<!-- templateList -->
						<div class="of_h mt30">
							<div class="phone3 inline-block" style="width:32%">
								<!-- phoneWrap -->
								<div class="phoneWrap">
									<img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
									<div class="phoneTextWrap">
										<div class="phoneText1 of_h scroll-y4 relative" style="padding:20px 15px 70px 15px;">
											<p><img :src="styleImgSrc" alt="주문 아이콘" style="width:70px;"></p>
											<div v-for="n in styleContentCnt" class="scroll-y3">
												<div v-if="styleArr[n-1]==1">
													<p class="lc-1 inline-block">{{styleInput[n-1]}}</p>
												</div>
												<div v-if="styleArr[n-1]==2">
													<p class="lc-1 inline-block">{{styleInput[n-1]}}</p>
													<p class="lc-1 inline-block float-right">{{styleInputSec[n-1]}}</p>
												</div>
												<hr v-if="styleChk[n-1]==true">
												<div v-if="n==styleContentCnt" class="revert" style="bottom:25px; left:28%">
													<p v-for="n in btnCnt" class="text-center" style="color:#69C8FF">{{btnNm[n-1]}}</p>
												</div>
											</div>
										</div>
									</div>
								</div>
								<!-- //phoneWrap -->
							</div>
							<div class="of_h inline-block vertical-top consoleCon" style="width:60%">
								<div class="of_h user-phone">
									<div class="float-left" style="width:22%">
										<h4>유형 *</h4>
									</div>
									<div class="float-left" style="width:78%">
										<div class="of_h">
											<select @change="fnSetStyleformImg" v-model="styleFormNm" name="userConsole_sub040202_2" class="selectStyle2">
												<option v-for="option in styleFormNmList" v-bind:value="option.MESSAGEBASEFORM_ID">
													{{option.FORM_NAME}}
												</option>
											</select>
										</div>
									</div>									
								</div>
								<div class="of_h user-phone of_h">
									<div class="float-left" style="width:22%">
										<h4>내용 *</h4>
									</div>
									<div class="float-left" style="width:78%">
										<div class="of_h">
											<p class="color4">{{styleContentText}}</p>
											<p class="consolMarginTop">기변부 포함 90자 이내로 작성해 주세요.</p>
										</div>
										<div v-for="n in styleContentCnt">								
											<div v-if="styleArr[n-1]==1" class="consolMarginTop">
												<a @click.prevent="fnClickSquare(n, styleArr[n-1], false)" href="#_self"><img src="@/assets/images/common/square01.png" alt="사각형1"></a></a>
												<div class="inline-block ml10">
													<a @click.prevent="fnClickSquare(n, styleArr[n-1], true)" href="#_self"><img src="@/assets/images/common/square02.png" alt="사각형2"></a>
												</div>
												<input v-model="styleInput[n-1]" type="text" class="inputStyle ml10" style="width:50%">
												<div v-if="n!=10" class="consolCheck ml10"><input v-model="styleChk[n-1]" type="checkbox" :id="n-1" class="checkStyle2" value="line1" checked=""><label :for="n-1" class="color4">라인</label></div>
											</div>
											<div v-if="styleArr[n-1]==2" class="consolMarginTop">
												<a @click.prevent="fnClickSquare(n, styleArr[n-1], true)" href="#_self"><img src="@/assets/images/common/square02.png" alt="사각형2"></a>
												<div class="inline-block ml10">
													<a @click.prevent="fnClickSquare(n, styleArr[n-1], false)" href="#_self"><img src="@/assets/images/common/square03.png" alt="사각형3"></a>
												</div>
												<input v-model="styleInput[n-1]" type="text" class="inputStyle ml10" style="width:24%">
												<input v-model="styleInputSec[n-1]" type="text" class="inputStyle ml10" style="width:24%">
												<div class="consolCheck ml10"><input v-model="styleChk[n-1]" type="checkbox" :id="n-1" class="checkStyle2" value="line2"><label :for="n-1" class="color4">라인</label></div>
											</div>
										</div>	
										<div class="consolMarginTop float-right" style="width:54%; margin-bottom:15px;">
											<a href="#self" @click.prevent="fnCellAdd()" class="btnStyle1 backBlack">Cell 추가 &nbsp;&nbsp;&nbsp; +</a>
										</div>
									</div>										
								</div>
								<div class="of_h user-phone">
									<div class="float-left" style="width:22%">
										<h4 class="mr10 inline-block">버튼</h4>
										<a href="#self" @click.prevent="fnClickAddBtn" class="btnStyle1 backBlack">추가 +</a>
									</div>
									<div class="float-left" style="width:78%">
										<div class="of_h">
											<table class="table_skin1 mt0" style="width:100%">
												<colgroup>
													<col style="width:22%">
													<col style="width:20%">
													<col>
													<col style="width:15%">
												</colgroup>
												<thead>
													<tr>
													<th class="text-center">타입</th>
													<th class="text-center">버튼이름</th>
													<th class="text-center">내용</th>
													<th class="text-center end">구분</th>
													</tr>
												</thead>
												<tbody>
													<tr v-for="n in btnCnt">
														<td class="text-center">
															<select @change="fnBtnSelectEvent(n-1)" v-model="selectBtn[n-1]" name="userConsole04060202_1" class="selectStyle2" style="width:100%">
																<option value="urlAction">URL 링크</option>
																<option value="clipboardAction">복사하기</option>
																<option value="dialerAction">전화걸기</option>
																<option value="calendarAction">일정추가</option>
																<option value="mapAction">지도맵</option>
															</select>
														</td>
														<td class="text-left"><input v-model="btnNm[n-1]" type="text" class="inputStyle"></td>
														<td v-if="selectBtn[n-1]!='calendarAction'" class="text-center"><input v-model="contents[n-1]" type="text" class="inputStyle" :placeholder="btnInputHolder[n-1]" :disabled="selectBtn[n-1]=='mapAction'"></td>
														<td v-if="selectBtn[n-1]=='calendarAction'" class="text-center">
															<input v-model="calendarTitle[n-1]" type="text" class="inputStyle" placeholder="제목입력">
															<input v-model="calendarDes[n-1]" type="text" class="inputStyle consolMarginTop" placeholder="내용입력">
															<div class="consolMarginTop of_h">
																<span class="float-left mt5" style="width:20%">시작일</span>
																<div class="float-right" style="width:80%">
																	<Calendar :calendarId="styleStartDate[n-1]" classProps="datepicker inputStyle" :initDate="styleInitStartDate[n-1]"></Calendar>
																</div>
															</div>
															<div class="consolMarginTop of_h">
																<span class="float-left mt5" style="width:20%">종료일</span>
																<div class="float-right" style="width:80%">
																	<Calendar :calendarId="styleEndDate[n-1]" classProps="datepicker inputStyle" :initDate="styleInitEndDate[n-1]"></Calendar>
																</div>
															</div>
														</td>
														<td class="text-center end"><a href="#" @click.prevent="fnClickMinus(n-1)" title="이전버튼"><i class="far fa-minus channelBtn"></i></a></td>
													</tr>
												</tbody>
											</table>
											
										</div>
									</div>									
								</div>
								<div class="consolCheck consolMarginTop"><input type="checkbox" id="agree2" class="checkStyle2" value="agree2"><label for="agree2" class="color4">정보성 메시지만 보낼 수 있으며, 광고 등 정책에 위배되는 메시지 발송 시  템플릿 사용이 중지될 수 있음을 동의합니다.</label></div>
								<div class="of_h">
									<div class="mt20 text-right" style="width:100%">
										<a href="#self" v-if="deleteBtn" @click.prevent="rcsTemplateDelete" class="btnStyle2 backWhite ml10" title="삭제">삭제</a>
										<a href="#self" v-if="cancelBtn" @click.prevent="rcsTemplateCancel" class="btnStyle2 backWhite ml10" title="삭제">삭제</a>
										<a href="#self" v-if="updateBtn" @click.prevent="recTemplateIns('UPT','cell')" class="btnStyle2 backRed  ml10" title="수정요청">수정요청</a>
										<a href="#self" v-if="insertBtn" @click.prevent="recTemplateIns('INS','cell')" class="btnStyle2 backRed  ml10" title="승인요청">승인요청</a>
										<a href="#self" @click.prevent="returnRcsTemplateList" class="btnStyle2 ml10" title="목록">목록</a>
									</div>
								</div>
							</div>
							
						</div>	
						<!-- //templateList -->
					</div>
				</div>
			</div>
		</article>
    </div>
</template>

<script>
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import confirm from "@/modules/commonUtil/service/confirm";
import templateApi from "@/modules/template/service/templateApi.js";
import Calendar from "@/components/Calendar.vue";

export default {
  name: "rcsTemplateMod",
  components: {
	  Calendar
  },
  props: {
      status: {
          type: String,
          default: ""
      },
	  msgId: {
		  type: String,
		  default: ""
	  }
  },
  data() {
    return {
		desImgSrc : require("@/assets/images/common/delivery.png"),
		styleImgSrc : require("@/assets/images/common/delivery.png"),
		deleteBtn: false,
		cancelBtn: false,
		insertBtn: false,
		updateBtn: false,
		templateNm: "",			// 템플릿 명
		templateCode: "",		// 템플릿 코드
		brandNm: "",			// 브랜드 명
		brandNmList: [],		// 브랜드 명 selectBox
		// 서술형 Data 세팅
		desFormNm: "",			// 서술형 유형
		desFormNmList: [],		// 서술형 유형 selectBox
		desContentsExam: "{{name}}입니다.{{date}} 할인/특가 상품을 안내해 드립니다. 본 알림은 {{name}} 회원전용 서비스 입니다.",	// 서술형 sampleView
		desContents: "",	// 서술형 내용
		desContentsPlaceHoder: "변수로 설정하고자 하는 내용을 {{ }}표시로 작성해 주십시오. 예) 이름과 출금일을 변수 설정: 예) {{고객}}님 {{YYMMDD}} 출금 예정입니다.",	// 서술형 내용 holder
		desContentsCnt: 0,	// 글자 수
		// 스타일형 Data 세팅
		styleFormNm: "",			// 스타일형 유형
		styleFormNmList: [],		// 스타일형 유형 selectBox
		styleContentCnt: 2,			// 스타일형 inputLine count
		styleArr: [1,2],			// 스타일형 inputLine input count
		styleInput: [],				// 스타일형 첫 input
		styleInputSec: [],			// 스타일형 두번째 input
		styleChk: [true, false],	// 스타일형 lineChk
		styleContentText: "변수로 설정하고자 하는 내용을 {{ }}표시로 작성해 주십시오.<br>예) 이름과 출금일을 변수 설정: 예) {{고객}}님 {{YYMMDD}} 출금 예정입니다.",	// 스타일형 내용 Text
		// 버튼 세팅
		btnCnt: 0,			// 버튼 개수
		selectBtn: [],		// selectBox
		btnNm:[],			// 버튼 이름
		contents:[],		// 내용
		btnInputHolder:[],	// 내용 holder
		calendarTitle: [],	// 
		calendarDes: [],	//
		desStartDate: ["desFirstStartDate","desSecondStartDate"],	// 서술형 달력 id
		desEndDate: ["desFirstEndDate","desSecondEndDate"],			// 서술형 달력 id
		desInitStartDate: [this.$gfnCommonUtils.getCurretDate(),this.$gfnCommonUtils.getCurretDate()],	// 서술형 달력
		desInitEndDate: [this.$gfnCommonUtils.getCurretDate(),this.$gfnCommonUtils.getCurretDate()],	// 서술형 달력
		styleStartDate: ["desFirstStartDate","desSecondStartDate"],	// 서술형 달력 id
		styleEndDate: ["desFirstEndDate","desSecondEndDate"],			// 서술형 달력 id
		styleInitStartDate: [this.$gfnCommonUtils.getCurretDate(),this.$gfnCommonUtils.getCurretDate()],	// 서술형 달력
		styleInitEndDate: [this.$gfnCommonUtils.getCurretDate(),this.$gfnCommonUtils.getCurretDate()],	// 서술형 달력
		flag : '',
		paramCardType : ''

    }
  },
  mounted() {
	  this.init();  
  },

  watch: {
	  desContents: function(newVal, oldVal) {
		  this.desContentsExam = newVal;
		  this.desContentsCnt = newVal.length;
	  }
  },

  methods: {
	  init() {
		var params = {};
		var vm = this;

		// 승인요청 버튼 활성화
		if(vm.status == "INS") {
			vm.insertBtn = true;
		}
		templateApi.rcsTemplateInit(params).then(response => {
			var result = response.data;
			var resultData = result.data;
			var status = vm.status;
			// 생성, 복사인 경우 템플릿 코드 세팅
			if(status!="UPT") {
				vm.templateCode = resultData.templateCode;
			}
			vm.brandNm = resultData.brandList[0].BRAND_ID;
			vm.brandNmList = resultData.brandList;
			vm.desFormNm = resultData.desFormList[0].MESSAGEBASEFORM_ID;
			vm.desFormNmList = resultData.desFormList;
			vm.styleFormNm = resultData.styleFormList[0].MESSAGEBASEFORM_ID;
			vm.styleFormNmList = resultData.styleFormList;

			if(status=="UPT" || status=="CPY") {
				this.updateInit();
			}
		});
	  },

	  updateInit() {
		var vm = this;
		var status = vm.status;
		var params = {
			"msgId" : this.msgId
		};
		templateApi.rcsTemplateUpdateInit(params).then(response => {
			var result = response.data;
			var resultData = result.data;
			// 상태값에 따라서 버튼 표시 처리해야됨. (승인, 반려일 경우 수정요청 버튼 활성화, 모든 상태에서 삭제 버튼 활성화)
			// 수정버튼은 반려(수정) 상태에선 노출 X, 해당 상태에선 취소 버튼노출
			var approvalStatus = resultData.approvalStatus;
			if("UPT" == status) {
				if("승인" == approvalStatus || "반려" == approvalStatus) {
					vm.updateBtn = true;
				}
				if("반려(수정)" == approvalStatus) {
					vm.cancelBtn = true;
				}
				vm.deleteBtn = true;
			} else if("CPY" == status) {
				vm.insertBtn = true;
			}

			vm.templateNm = resultData.templateNm;
			if(vm.status=="UPT") vm.templateCode = resultData.templateCode;
			vm.brandNm = resultData.brandNm;
			var cardType = resultData.cardType;

			// 서술형, 스타일형 내용 세팅
			if(cardType=="description") {
				vm.desFormNm = resultData.desNm;
				vm.desContents = resultData.textContents;
				this.fnSetDesformImg();

				// Tab 이벤트 처리
				jQuery("#styleTab").remove();
			} else if(cardType=="cell") {
				vm.styleFormNm = resultData.styleNm;
				vm.styleContentCnt = resultData.styleContentCnt;
				vm.styleArr = resultData.styleArr;
				vm.styleInput = resultData.styleInput;
				vm.styleInputSec = resultData.styleInputSec;
				vm.styleChk = resultData.styleChk;
				this.fnSetStyleformImg();

				// Tab 이벤트 처리
				jQuery("#styleTab").tab("show");	
				jQuery("#desTab").remove();
				jQuery("#productCate1").removeClass("active");
				jQuery("#productCate2").addClass("active");
			}

			// 버튼 세팅
			vm.btnCnt = resultData.btnCnt;
			vm.selectBtn = resultData.selectBtn;
			vm.btnNm = resultData.btnNm;
			vm.contents = resultData.contents;
			vm.calendarTitle = resultData.calendarTitle;
			vm.calendarDes = resultData.calendarDes;
			if(cardType=="description") {
				vm.desInitStartDate = resultData.startDate;
				vm.desInitEndDate = resultData.endDate;
			} else if(cardType=="cell") {
				vm.styleInitStartDate = resultData.startDate;
				vm.styleInitEndDate = resultData.endDate;
			}
		});
	  },

	  // 추가 버튼 클릭 이벤트 처리  
	  fnClickAddBtn() {
		var vm = this;
		var btnCnt = this.btnCnt;
		if(btnCnt==0) {
			vm.btnCnt = 1;
			vm.selectBtn.push("urlAction");
			vm.btnInputHolder.push("URL입력(http:// 또는 https:// 필수입력)");
			vm.btnNm.push("");
			vm.contents.push("");
			vm.calendarTitle.push("");
			vm.calendarDes.push("");
		} else if(btnCnt==1) {
			vm.btnCnt = 2;
			vm.selectBtn.push("urlAction");
			vm.btnInputHolder.push("URL입력(http:// 또는 https:// 필수입력)");
			vm.btnNm.push("");
			vm.contents.push("");
			vm.calendarTitle.push("");
			vm.calendarDes.push("");
		}
	  },
	  // 버튼 타입 change 이벤트 처리
	  fnBtnSelectEvent(n) {
		var vm = this;
		if(vm.selectBtn[n]=="urlAction") {
			this.$set(vm.contents, n, "");
			this.$set(vm.btnInputHolder, n, "URL입력(http:// 또는 https:// 필수입력)");
		} else if(vm.selectBtn[n]=="clipboardAction") {
			this.$set(vm.contents, n, "");
			this.$set(vm.btnInputHolder, n, "복사할 값 입력");
		} else if(vm.selectBtn[n]=="dialerAction") {
			this.$set(vm.contents, n, "");
			this.$set(vm.btnInputHolder, n, "전화번호 입력");
		} else if(vm.selectBtn[n]=="mapAction") {
			this.$set(contents, n, "현재위치 공유");
			this.$set(btnInputHolder, n, "현재위치 공유");
		}
	  },
	  // 버튼 삭제
	  fnClickMinus(n) {
		var vm = this;
		if(n==0) {
			if(vm.btnCnt==1) {
				vm.btnCnt = 0;
				vm.selectBtn.pop();
				vm.btnInputHolder.pop();
				vm.btnNm.pop();
				vm.contents.pop();
				vm.calendarTitle.pop();
				vm.calendarDes.pop();
			} else if(vm.btnCnt==2) {
				vm.btnCnt = 1;
				this.$set(vm.selectBtn, n, vm.selectBtn[n+1]);
				this.$set(vm.btnInputHolder, n, vm.btnInputHolder[n+1]);
				this.$set(vm.btnNm, n, vm.btnNm[n+1]);
				this.$set(vm.contents, n, vm.contents[n+1]);
				this.$set(vm.calendarTitle, n, vm.calendarTitle[n+1]);
				this.$set(vm.calendarDes, n, vm.calendarDes[n+1]);
				vm.selectBtn.pop();
				vm.btnInputHolder.pop();
				vm.btnNm.pop();
				vm.contents.pop();
				vm.calendarTitle.pop();
				vm.calendarDes.pop();
			}

		} else if(n==1) {
			vm.btnCnt = 1;
			vm.selectBtn.pop();
			vm.btnInputHolder.pop();
			vm.btnNm.pop();
			vm.contents.pop();
			vm.calendarTitle.pop();
			vm.calendarDes.pop();
		}
	  },

	  // 스타일형 사각형 버튼 이벤트
	  fnClickSquare(n, style, styleFlag){
		var vm = this;
		if(styleFlag==true) {
			if(style==1) {
				this.$set(vm.styleArr, n-1, 2);
			} else if(style==2) {
				this.$set(vm.styleArr, n-1, 1);
			}
		}
	  },

	  // cell 추가
	  fnCellAdd() {
		var vm = this;
		var styleContentCnt = this.styleContentCnt;

		if(styleContentCnt<10) {
			this.$set(vm.styleArr, styleContentCnt, 1);
			this.$set(vm.styleInput, styleContentCnt, "");
			this.$set(vm.styleInputSec, styleContentCnt, "");
			this.$set(vm.styleChk, styleContentCnt, false);
			vm.styleContentCnt++;
		}		
	  },

	  //fnDesUpdateStartDate
	  fnDesUpdateStartDate(date, n) {
		var vm = this;
		this.$set(vm.desInitStartDate, n, date);
	  },
	  fnDesUpdateEndDate(n, m) {
		var vm = this;
		this.$set(vm.desInitEndDate, n, date);
	  },
	  fnStyleUpdateStartDate(n, m) {
		var vm = this;
		this.$set(vm.styleInitStartDate, n, date);
	  },
	  fnStyleUpdateEndDate(n, m) {
		var vm = this;
		this.$set(vm.styleInitEndDate, n, date);
	  },

	  // 템플릿 취소
	  rcsTemplateCancel() {
		eventBus.$on('callbackEventBus', this.fnRcsTemplateCancelApi);
      	confirm.fnConfirm("RCS 템플릿 취소", "RCS 템플릿을 취소 하시겠습니까?", "확인");
	  },

	  fnRcsTemplateCancelApi() {

	  },

	  // 템플릿 삭제
	  rcsTemplateDelete() {
		eventBus.$on('callbackEventBus', this.fnRcsTemplateDeleteApi);
      	confirm.fnConfirm("RCS 템플릿 삭제", "RCS 템플릿을 삭제 하시겠습니까?", "확인");
	  },

	  fnRcsTemplateDeleteApi() {
		var vm = this;
		var params = {
			"brandId" : vm.brandNm,
			"messagebaseId" : vm.templateCode
		}
		
		templateApi.rcsTemplateDeleteApi(params).then(response => {
			var result = response.data;
			var success = result.success;
			var message = result.message;
			if(success) {
				confirm.fnAlert("삭제요청이 완료되었습니다.","");
				this.$router.push({name : "rcsTemplateList"});
			} else {
				confirm.fnAlert(message,"");
				this.$router.push({name : "rcsTemplateList"});
			}
		});
	  },

	  fnSetDesformImg() {
		  var vm = this;
		  var desFormNm = this.desFormNm;
		  if("CC001D" == desFormNm) {	// 출고
			vm.desImgSrc = require("@/assets/images/common/delivery.png");
		  } else if("CC002D" == desFormNm) { // 주문
			vm.desImgSrc = require("@/assets/images/common/order.png");
		  } else if("CC003D" == desFormNm) { // 배송
			vm.desImgSrc = require("@/assets/images/common/ship.png");
		  } else if("EE001D" == desFormNm) { // 예약
			vm.desImgSrc = require("@/assets/images/common/reservation.png");
		  } else if("FF001D" == desFormNm) { // 승인
			vm.desImgSrc = require("@/assets/images/common/approve.png");
		  } else if("FF002D" == desFormNm) { // 입금
			vm.desImgSrc = require("@/assets/images/common/receipts.png");
		  } else if("FF003D" == desFormNm) { // 출금
			vm.desImgSrc = require("@/assets/images/common/payment.png");
		  } else if("FF004D" == desFormNm) { // 취소
			vm.desImgSrc = require("@/assets/images/common/cancel.png");
		  } else if("FF005D" == desFormNm) { // 명세서
			vm.desImgSrc = require("@/assets/images/common/specifications.png");
		  } else if("GG001D" == desFormNm) { // 회원가입
			vm.desImgSrc = require("@/assets/images/common/join.png");
		  } else if("GG002D" == desFormNm) { // 인증
			vm.desImgSrc = require("@/assets/images/common/certification.png");
		  } else if("GG003D" == desFormNm) { // 안내
			vm.desImgSrc = require("@/assets/images/common/infomation.png");
		  }
	  },

	  fnSetStyleformImg() {
		  var vm = this;
		  var styleFormNm = this.styleFormNm;
		  if("CC001C" == styleFormNm) {	// 출고
			vm.styleImgSrc = require("@/assets/images/common/delivery.png");
		  } else if("CC002C" == styleFormNm) { // 주문
			vm.styleImgSrc = require("@/assets/images/common/order.png");
		  } else if("CC003C" == styleFormNm) { // 배송
			vm.styleImgSrc = require("@/assets/images/common/ship.png");
		  } else if("EE001C" == styleFormNm) { // 예약
			vm.styleImgSrc = require("@/assets/images/common/reservation.png");
		  } else if("FF001C" == styleFormNm) { // 승인
			vm.styleImgSrc = require("@/assets/images/common/approve.png");
		  } else if("FF002C" == styleFormNm) { // 입금
			vm.styleImgSrc = require("@/assets/images/common/receipts.png");
		  } else if("FF003C" == styleFormNm) { // 출금
			vm.styleImgSrc = require("@/assets/images/common/payment.png");
		  } else if("FF004C" == styleFormNm) { // 취소
			vm.styleImgSrc = require("@/assets/images/common/cancel.png");
		  } else if("FF005C" == styleFormNm) { // 명세서
			vm.styleImgSrc = require("@/assets/images/common/specifications.png");
		  } else if("GG001C" == styleFormNm) { // 회원가입
			vm.styleImgSrc = require("@/assets/images/common/join.png");
		  } else if("GG002C" == styleFormNm) { // 인증
			vm.styleImgSrc = require("@/assets/images/common/certification.png");
		  } else if("GG003C" == styleFormNm) { // 안내
			vm.styleImgSrc = require("@/assets/images/common/infomation.png");
		  }
	  },

	  // 목록
	  returnRcsTemplateList() {
		  this.$router.push({name : "rcsTemplateList"});
	  },

	  // 템플릿 승인, 수정요청
	  recTemplateIns(flag, paramCardType) {
		this.flag = flag;
		this.paramCardType = paramCardType;

		if("INS" == flag) {
			eventBus.$on('callbackEventBus', this.fnRcsTemplateApi);
			confirm.fnConfirm("RCS 템플릿 승인", "RCS 템플릿을 승인 요청 하시겠습니까?", "승인");
		} else {
			eventBus.$on('callbackEventBus', this.fnRcsTemplateApi);
			confirm.fnConfirm("RCS 템플릿 수정", "RCS 템플릿을 수정 요청 하시겠습니까?", "수정");
		}
	  },

	  // 승인, 수정요청
	  fnRcsTemplateApi() {
		var vm = this;
		var flag = vm.flag;
		var paramCardType = vm.paramCardType;
		var messagebaseformId = paramCardType == "des" ? vm.desFormNm : vm.styleFormNm;
		var messagebaseId = vm.templateCode;
		var custTmpltId = vm.templateCode;
		if(flag == "UPT") {
			var custTmpltIdArr = custTmpltId.split("-");
			custTmpltId = custTmpltIdArr[1];
		}
		var tmpltName = vm.templateNm;
		var brandId = vm.brandNm;
	  	var agencyId = "uplus";

		// 서술형 내용
		var desContents = vm.desContents;
		// 스타일형 내용
		var styleContentCnt = vm.styleContentCnt;
		var styleArr = vm.styleArr;
		var styleInput = vm.styleInput;
		var styleInputSec = vm.styleInputSec;
		var styleChk = vm.styleChk;

		// 버튼 세팅
		var btnCnt = vm.btnCnt;
		var selectBtn = vm.selectBtn;
		var btnNm = vm.btnNm;
		var contents = vm.contents;
		var calendarTitle = vm.calendarTitle;
		var calendarDes = vm.calendarDes;
		var desInitStartDate = vm.desInitStartDate;
		var desInitEndDate = vm.desInitEndDate;
		var styleInitStartDate = vm.styleInitStartDate;
		var styleInitEndDate = vm.styleInitEndDate;

		var params = {
			// 구분
			"paramCardType" : paramCardType,
			"flag" : flag,
			// 공통 처리
			"messagebaseformId" : messagebaseformId,
			"messagebaseId" : messagebaseId,
			"custTmpltId" : custTmpltId,
			"tmpltName" : tmpltName,
			"brandId" : brandId,
			"agencyId" : agencyId,
			// 서술형 내용
			"desContents" : desContents,
			// 스타일형 내용
			"styleContentCnt": styleContentCnt,
			"styleArr": styleArr,
			"styleInput": styleInput,
			"styleInputSec": styleInputSec,
			"styleChk": styleChk,
			// 버튼 세팅
			"btnCnt" : btnCnt,
			"selectBtn" : selectBtn,
			"btnNm" : btnNm,
			"contents" : contents,
			"calendarTitle" : calendarTitle,
			"calendarDes" : calendarDes,
			"desInitStartDate" : desInitStartDate,
			"desInitEndDate" : desInitEndDate,
			"styleInitStartDate" : styleInitStartDate,
			"styleInitEndDate" : styleInitEndDate
		}
	  
	    templateApi.rcsTemplateApi(params).then(response => {
			var result = response.data;
			var success = result.success;
			var message = result.message;
			var flagNm = flag== "INS" ? "등록" : "수정";
			if(success) {
				confirm.fnAlert(flagNm + "이 완료되었습니다.","");
				this.$router.push({name : "rcsTemplateList"});
			} else {
				confirm.fnAlert(message,"");
				this.$router.push({name : "rcsTemplateList"});
			}
		});	  
	  }
  }
}
</script>