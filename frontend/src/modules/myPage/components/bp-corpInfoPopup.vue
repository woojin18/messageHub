<template>
	<div class="modal fade modalStyle in" id="corpInfoPopup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h">
						<h2>고객사 정보</h2>
						<hr>
						<h4>사업자 정보</h4>
						<div class="border-line mt20 Dashboard06">
							<div class="of_h">
								<h5 class="inline-block" style="width:20%">고객번호 <span class="color1">*</span></h5>
								<input type="text" class="inputStyle float-right" style="width:80%" title="고객번호 입력란" disabled>
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">사업자번호 <span class="color1">*</span></h5>
								<input type="text" class="inputStyle float-right" style="width:80%" title="사업자번호 입력란" v-model="regno" disabled>
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">고객유형 <span class="color1">*</span></h5>
								<select class="selectStyle2 float-right" style="width:80%" title="고객유형선택란" disabled>
									<option value="">개인사업자</option>
								</select>
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">생년월일/<br>법인번호 <span class="color1">*</span></h5>
								<input type="text" class="inputStyle float-right" style="width:80%" title="생년월일/법인번호 입력란" disabled>
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">사업자명 <span class="color1">*</span></h5>
								<input type="text" class="inputStyle float-right" style="width:80%" title="사업자명 입력란" v-model="corpName" disabled>
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">대표자명 <span class="color1">*</span></h5>
								<input type="text" class="inputStyle float-right" style="width:80%" title="대표자명 입력란" v-model="ceoName">
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">대표자명 <span class="color1">*</span></h5>
								<input type="text" class="inputStyle float-right" style="width:80%" title="대표자명 입력란" v-model="ceoName">
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">업태 <span class="color1">*</span></h5>
								<select class="selectStyle2 float-right" style="width:80%" title="업태 선택란" v-model="busitype">
									<option value=""></option>
								</select>
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">종목 <span class="color1">*</span></h5>
								<select class="selectStyle2 float-right" style="width:80%" title="종목 선택란" v-model="busiclass">
									<option value=""></option>
								</select>
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">사업장주소 <span class="color1">*</span></h5>
								<input type="text" class="inputStyle" style="width:58%" title="우편번호 입력란" placeholder="우편번호" disabled>
								<a href="#self" class="btnStyle1 backLightGray float-right" title="주소조회">주소조회</a>
								<input type="text" class="inputStyle consolMarginTop float-right" style="width:80%" title="사업장 주소 입력란" v-model="woplaceAddress" placeholder="주소" disabled>
								<input type="text" class="inputStyle consolMarginTop float-right" style="width:80%" title="사업장 상세주소 입력란" v-model="woplaceAddressDetail" placeholder="상세주소">
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">유선전화번호</h5>
								<input type="text" class="inputStyle float-right" style="width:80%" title="유선전화번호 입력란" v-model="wireTel" placeholder="-없이 입력">
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">사업자등록증 <span class="color1">*</span></h5>
								<input type="text" class="inputStyle" style="width:37%" title="사업자등록증 입력란" v-model="attachFileName" readonly="readonly">
								<input type="hidden" v-model="attachFilePath">
								<input type="file" ref="imageInput" style="display:none">
								<a class="btnStyle1 backLightGray ml15" title="파일첨부">파일첨부</a>
								<a class="btnStyle1 backLightGray float-right" title="다운로드">다운로드</a>
								<p class="mt10 lc-1 Modaltext2" style="margin-left:20%"><i class="far fa-info-circle"></i>PDF, JPG, JPEG, PNG 형식으로 등록해주세요. (최대용량: 5MB)</p>
							</div>
						</div>

						<h4>도메인 설정</h4>
						<div class="border-line mt20 Dashboard06">
							<div class="of_h">
								<h5 class="inline-block" style="width:20%">도메인 이름</h5>
								<h5 class="inline-block" style="width:55%">{{ domainName }}</h5>
							</div>	
						</div>						
					</div>
					<div class="text-center mt40">
						<a @click="fnSave" class="btnStyle2 backBlack" title="저장">저장</a>
						<a @click="fnCloseLayer" class="btnStyle2 backWhite ml10" title="닫기">닫기</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import myPageApi from '@/modules/myPage/service/myPageApi';

export default {
  name: 'corpInfoPopup',
  data() {
    return {
      regno : '',                   // 사업자번호
      corpName : '',                // 사업자명
      ceoName : '',                 // ceo명
      busitype : '',                // 업태
      busiclass : '',               // 종목
      woplaceAddress : '',          //사업장주소
      woplaceAddressDetail : '',    //사업장 상세주소
      wireTel : '',                 // 유선전화번호
      domainName : '',               // 도메인 명
      attachFileName : '',          // 사업자등록증 이미지명
      attachFilePath : '',           // 사업자등록증 이미지경로
      
    }
  },
  props: {
    corpInfo : {
      type : Object,
      require : true
    },
    popReset: {
      type: Number
    }
  },
  mounted() {
    this.fnReset();
  },
  watch: {
    popReset(){
      this.fnReset();
    },
    wireTel(){
      if(this.wireTel != undefined){
        return this.wireTel = this.wireTel.replace(/[^0-9]/g, '');
      }
    }
  },
  methods: {
      fnCloseLayer(){
          jQuery("#corpInfoPopup").modal("hide");
      },
      // 데이터 초기화
      fnReset(){
        this.regno = this.corpInfo.regno;
        this.corpName = this.corpInfo.corpName;
        this.ceoName = this.corpInfo.ceoName;
        this.busitype = this.corpInfo.busitype;
        this.busiclass = this.corpInfo.busiclass;
        this.woplaceAddress = this.corpInfo.woplaceAddress;
        this.woplaceAddressDetail = this.corpInfo.woplaceAddressDetail;
        this.wireTel = this.corpInfo.wireTel;
        this.domainName = this.corpInfo.domainName;
      },
      // 저장
      fnSave(){
        if(this.corpName == undefined ||this.corpName == ''){
          confirm.fnAlert("", "사업자명은 필수 입력사항입니다.");
          return;
        }
        if(this.ceoName == undefined ||this.ceoName == ''){
          confirm.fnAlert("", "대표자명은 필수 입력사항입니다.");
          return;
        }
        if(this.busitype == undefined ||this.busitype == ''){
          confirm.fnAlert("", "업태는 필수 입력사항입니다.");
          return;
        }
        if(this.busiclass == undefined ||this.busiclass == ''){
          confirm.fnAlert("", "종목은 필수 입력사항입니다.");
          return;
        }
        if(this.woplaceAddress == undefined ||this.woplaceAddress == ''){
          confirm.fnAlert("", "사업장 주소는 필수 입력사항입니다.");
          return;
        }
        if(this.woplaceAddressDetail == undefined ||this.woplaceAddressDetail == ''){
          confirm.fnAlert("", "사업장 상세주소는 필수 입력사항입니다.");
          return;
        }
        // 사업자등록증 추가

        eventBus.$on('callbackEventBus', this.fnSaveCallBack);
        confirm.fnConfirm( "고객사 정보 저장", "저장하시겠습니까?", "저장");
      },
      fnSaveCallBack(){
        var params = {
          corpId : this.corpInfo.corpId,
          corpName : this.corpName,
          ceoName : this.ceoName,
          busitype : this.busitype,
          busiclass : this.busiclass,
          woplaceAddress : this.woplaceAddress,
          woplaceAddressDetail : this.woplaceAddressDetail,
          wireTel : this.wireTel
        }

        myPageApi.saveCorpInfo(params).then(response =>{
          var result = response.data;
          if(result.success) {
            confirm.fnAlert( "저장되었습니다.", "");
            this.fnCloseLayer();
          } else {
            confirm.fnAlert(result.message, "");
          }
        });
      }
  }
}
</script>
