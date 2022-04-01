<template>
  <div v-if="rcsBtnOpen" @click.self="fnClose" class="modalStyle" id="recipient" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:670px">
			<div class="modal-content">
				<div class="modal-body">
					<table class="table_skin1" style="width:100%">
						<colgroup>
							<col style="width:18%">
							<col style="width:18%">
							<col>
							<col style="width:25%">
						</colgroup>
						<thead>
							<tr>
								<th class="text-center">타입</th>
								<th class="text-center">버튼이름</th>
								<th class="text-center">버튼링크</th>
								<th class="text-center end">구분</th>
							</tr>
						</thead>
						<tbody>
							<tr 
								v-for="(n, idx) in btnCnt"
								:key="idx"
							>
								<td class="text-center">
									<select @change="fnBtnSelectEvent(n-1)" v-model="selectBtn[n-1]" name="userConsole_sub020203_2" class="float-left selectStyle2" style="width:100%">
										<option value="urlAction">URL 링크</option>
										<option value="clipboardAction">복사하기</option>
										<option value="dialerAction">전화걸기</option>
										<option value="calendarAction">일정추가</option>
										<option value="mapAction">지도맵</option>
									</select>
								</td>
								<td class="text-left"><input v-model="btnNm[n-1]" type="text" class="inputStyle"></td>
								<td v-if="selectBtn[n-1]!='calendarAction'" class="text-center">
									<input v-model="contents[n-1]" type="text" class="inputStyle" :placeholder="btnInputHolder[n-1]" :disabled="selectBtn[n-1]=='mapAction'">
								</td>
								<td v-if="selectBtn[n-1]=='calendarAction'" class="text-center">
									<input v-model="calendarTitle[n-1]" type="text" class="inputStyle" placeholder="제목입력">
									<input v-model="calendarDes[n-1]" type="text" class="inputStyle consolMarginTop" placeholder="내용입력">
									<div class="consolMarginTop of_h">
										<span class="float-left mt5" style="width:20%">시작일</span>
										<div class="float-right" style="width:80%">
											<Calendar @update-date="fnUpdateStartDate" :params="n-1" :calendarId="startDate[n-1]" classProps="datepicker inputStyle" :initDate="initStartDate[n-1]"></Calendar>
										</div>
									</div>
									<div class="consolMarginTop of_h">
										<span class="float-left mt5" style="width:20%">종료일</span>
										<div class="float-right" style="width:80%">
											<Calendar @update-date="fnUpdateEndDate" :params="n-1" :calendarId="endDate[n-1]" classProps="datepicker inputStyle" :initDate="initEndDate[n-1]"></Calendar>
										</div>
									</div>
								</td>
								<td class="text-center end">
									<a
										v-if="selectBtn[n-1] === 'urlAction'" 
										class="btnStyle1 backBlack" 
										title="단축 URL+" 
										data-toggle="modal" 
										data-target="#shortened_URL"
										@click="selIdx = idx"
									>단축 URL+</a>
									<a href="#" @click.prevent="fnClickMinus(n-1)" title="이전버튼">
										<i class="far fa-minus channelBtn" :class="selectBtn[n-1] === 'urlAction' ? 'ml5' : ''" ></i>
									</a>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="text-center mt20">
            <a @click.prevent="fnClickAddBtn" href="#self" class="btnStyle2 backBlack mr10">버튼 추가+</a>
						<a @click.prevent="fnAdd" href="#self" class="btnStyle2 backBlack mr10" data-dismiss="modal" title="입력">입력</a>	
						<a @click.prevent="fnClose" href="#self" class="btnStyle2 backWhite" data-dismiss="modal" title="닫기">닫기</a>															
					</div>
				</div>
				
			</div>
		</div>

		<shortenedUrlListPopup @btnSelect="btnSelect" />
    <shortenedUrlAddPopup/>
	</div>
</template>

<script>
// import confirm from "@/modules/commonUtil/service/confirm.js";
import Calendar from "@/components/Calendar.vue";

import shortenedUrlListPopup from "@/modules/urlInfo/components/shortenedUrlListPopup"
import shortenedUrlAddPopup from "@/modules/urlInfo/components/shortenedUrlAddPopup"

export default {
  name: "rcsBtnPop",
  components: {
		Calendar,
		shortenedUrlListPopup,
    shortenedUrlAddPopup,
  },
  props : {
		templateRadioBtn: {
				type: String,
				require: true,
				default: "",
		},
		btnPopCnt: {
				type: Number,
				default: 0
		},
		rcsBtnOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
  },
  data() {
    return { 
      btnCnt : 1,                      // 버튼 갯수
      selectBtn: ["urlAction"],		   // selectBox
      btnNm:[""],			           // 버튼 이름
      contents:[""],		           // 내용
      btnInputHolder:["URL(http:// 또는 https:// 필수입력)"],	// 내용 holder
      calendarTitle: [""],	     
      calendarDes: [""],	    
      startDate: ["firstStartDate","secondStartDate","thirdStartDate"],	    // 달력 id
      endDate: ["firstEndDate","secondEndDate","thirdEndDate"],			// 달력 id
      initStartDate: [this.$gfnCommonUtils.getCurretDate(),this.$gfnCommonUtils.getCurretDate(),this.$gfnCommonUtils.getCurretDate()],	//  달력
      initEndDate: [this.$gfnCommonUtils.getCurretDate(),this.$gfnCommonUtils.getCurretDate(),this.$gfnCommonUtils.getCurretDate()],	//  달력
			selIdx: null,
    }
  },

  methods: {
    // 추가 버튼 클릭 이벤트 처리  
		fnClickAddBtn() {
			var vm = this;
			var btnCnt = this.btnCnt;
			var maxCnt = 0;
			var radioBtn = this.templateRadioBtn;

			// SMS 형은 버튼이 1개만, LMS는 3개, 나머지는 2개까지 처리

			if(radioBtn == "SS000000") {
					maxCnt = 1;
			} else if(radioBtn == "SL000000") {
					maxCnt = 3;
			} else {
					maxCnt = 2;
			}

			if(btnCnt<maxCnt) {
				vm.btnCnt++;
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
			this.$set(vm.contents, n, "현재위치 공유");
			this.$set(vm.btnInputHolder, n, "현재위치 공유");
		}
		},
		// 버튼 삭제
		fnClickMinus(n) {
			var vm = this;
			// 0번쨰 배열 - 클릭시
			if(vm.btnCnt==2) {
				if(n==0) {
					vm.btnCnt = 1;
					this.$set(vm.selectBtn, n, vm.selectBtn[n+1]);
					this.$set(vm.btnInputHolder, n, vm.btnInputHolder[n+1]);
					this.$set(vm.btnNm, n, vm.btnNm[n+1]);
					this.$set(vm.contents, n, vm.contents[n+1]);
					this.$set(vm.calendarTitle, n, vm.calendarTitle[n+1]);
					this.$set(vm.calendarDes, n, vm.calendarDes[n+1]);
					this.$set(vm.startDate, n, vm.startDate[n+1]);
					this.$set(vm.endDate, n, vm.endDate[n+1]);
					this.$set(vm.initStartDate, n, vm.initStartDate[n+1]);
					this.$set(vm.initEndDate, n, vm.initEndDate[n+1]);
					vm.selectBtn.pop();
					vm.btnInputHolder.pop();
					vm.btnNm.pop();
					vm.contents.pop();
					vm.calendarTitle.pop();
					vm.calendarDes.pop();
					vm.startDate.pop();
					vm.endDate.pop();
					vm.initStartDate.pop();
					vm.initEndDate.pop();
        } else if(n==1) {
          vm.btnCnt = 1;
					vm.selectBtn.pop();
					vm.btnInputHolder.pop();
					vm.btnNm.pop();
					vm.contents.pop();
					vm.calendarTitle.pop();
					vm.calendarDes.pop();
					vm.startDate.pop();
					vm.endDate.pop();
					vm.initStartDate.pop();
					vm.initEndDate.pop();
        }
			} else if (vm.btnCnt==3) {
					if(n==0) {
						vm.btnCnt = 2;
						this.$set(vm.selectBtn, n, vm.selectBtn[n+1]);
						this.$set(vm.btnInputHolder, n, vm.btnInputHolder[n+1]);
						this.$set(vm.btnNm, n, vm.btnNm[n+1]);
						this.$set(vm.contents, n, vm.contents[n+1]);
						this.$set(vm.calendarTitle, n, vm.calendarTitle[n+1]);
						this.$set(vm.calendarDes, n, vm.calendarDes[n+1]);
						this.$set(vm.startDate, n, vm.startDate[n+1]);
						this.$set(vm.endDate, n, vm.endDate[n+1]);
						this.$set(vm.initStartDate, n, vm.initStartDate[n+1]);
						this.$set(vm.initEndDate, n, vm.initEndDate[n+1]);

						this.$set(vm.selectBtn, n+1, vm.selectBtn[n+2]);
						this.$set(vm.btnInputHolder, n+1, vm.btnInputHolder[n+2]);
						this.$set(vm.btnNm, n+1, vm.btnNm[n+2]);
						this.$set(vm.contents, n+1, vm.contents[n+2]);
						this.$set(vm.calendarTitle, n+1, vm.calendarTitle[n+2]);
						this.$set(vm.calendarDes, n+1, vm.calendarDes[n+2]);
						this.$set(vm.startDate, n+1, vm.startDate[n+2]);
						this.$set(vm.endDate, n+1, vm.endDate[n+2]);
						this.$set(vm.initStartDate, n+1, vm.initStartDate[n+2]);
						this.$set(vm.initEndDate, n+1, vm.initEndDate[n+2]);
										
						vm.selectBtn.pop();
						vm.btnInputHolder.pop();
						vm.btnNm.pop();
						vm.contents.pop();
						vm.calendarTitle.pop();
						vm.calendarDes.pop();
						vm.startDate.pop();
						vm.endDate.pop();
						vm.initStartDate.pop();
						vm.initEndDate.pop();
					} else if(n==1) {
						vm.btnCnt = 2;
						this.$set(vm.selectBtn, n, vm.selectBtn[n+1]);
						this.$set(vm.btnInputHolder, n, vm.btnInputHolder[n+1]);
						this.$set(vm.btnNm, n, vm.btnNm[n+1]);
						this.$set(vm.contents, n, vm.contents[n+1]);
						this.$set(vm.calendarTitle, n, vm.calendarTitle[n+1]);
						this.$set(vm.calendarDes, n, vm.calendarDes[n+1]);
						this.$set(vm.startDate, n, vm.startDate[n+1]);
						this.$set(vm.endDate, n, vm.endDate[n+1]);
						this.$set(vm.initStartDate, n, vm.initStartDate[n+1]);
						this.$set(vm.initEndDate, n, vm.initEndDate[n+1]);
						vm.selectBtn.pop();
						vm.btnInputHolder.pop();
						vm.btnNm.pop();
						vm.contents.pop();
						vm.calendarTitle.pop();
						vm.calendarDes.pop();
						vm.startDate.pop();
						vm.endDate.pop();
						vm.initStartDate.pop();
						vm.initEndDate.pop();
					} else if(n==2) {
						vm.btnCnt = 2;
						vm.selectBtn.pop();
						vm.btnInputHolder.pop();
						vm.btnNm.pop();
						vm.contents.pop();
						vm.calendarTitle.pop();
						vm.calendarDes.pop();
						vm.startDate.pop();
						vm.endDate.pop();
						vm.initStartDate.pop();
						vm.initEndDate.pop();
					}
			}
		},
		fnClose() {
			this.btnCnt = 1;
			this.selectBtn = ["urlAction"];
			this.btnNm = [""];
			this.contents = [""];
			this.btnInputHolder = ["URL(http:// 또는 https:// 필수입력)"];
			this.calendarTitle = [""];	     
			this.calendarDes = [""];	    
			this.startDate = ["firstStartDate","secondStartDate","thirdStartDate"];
			this.endDate = ["firstEndDate","secondEndDate","thirdEndDate"];
			this.initStartDate = [this.$gfnCommonUtils.getCurretDate(),this.$gfnCommonUtils.getCurretDate(),this.$gfnCommonUtils.getCurretDate()];
			this.initEndDate = [this.$gfnCommonUtils.getCurretDate(),this.$gfnCommonUtils.getCurretDate(),this.$gfnCommonUtils.getCurretDate()];

			this.$emit('update:rcsBtnOpen', false)
		},
		fnAdd() {
			var vm = this;
			var params = new Object();
			params.btnCnt = this.btnCnt;
			params.selectBtn = this.selectBtn;
			params.btnNm = this.btnNm;
			params.contents = this.contents;
			params.calendarTitle = this.calendarTitle;
			params.calendarDes = this.calendarDes;
			params.initStartDate = this.initStartDate;
			params.initEndDate = this.initEndDate;

			vm.$emit('fnAddBtnResult', params);
			vm.fnClose();
		},
		// 날자 세팅
		fnUpdateStartDate(date, n) {
			var vm = this;
			this.$set(vm.initStartDate, n, date);
		},
		fnUpdateEndDate(date, n) {
			var vm = this;
			this.$set(vm.initEndDate, n, date);
		},
		//단축 URL 선택
    btnSelect(shortendUrl){
			if(this.contents.length > 0 && this.selIdx !== null)
        this.$set(this.contents, this.selIdx, shortendUrl)

			this.selIdx = null
    },
  }
}
</script>