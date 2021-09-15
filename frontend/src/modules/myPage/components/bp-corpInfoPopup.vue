<template>
	<div class="modal modalStyle in" id="corpInfoPopup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h">
						<h2>고객사 정보</h2>
						<hr>
						<h4>사업자 정보</h4>
						<div class="border-line mt20 Dashboard06">
							<!-- <div class="of_h">
								<h5 class="inline-block" style="width:20%">고객번호</h5>
								<input type="text" class="inputStyle float-right" style="width:80%" title="고객번호 입력란" v-model="custNo" disabled>
							</div> -->
							<div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">사업자번호</h5>
								<input type="text" class="inputStyle float-right" style="width:80%" title="사업자번호 입력란" v-model="regno" disabled>
							</div>
							<!-- <div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">고객유형</h5>
								<select class="selectStyle2" style="width:80%" title="고객유형선택란" v-model="custKdCd" disabled>
									<option value="">선택하세요</option>
									<option  v-for="(row, index) in custTypeArr" :key="index" :value="row.codeVal1"> {{ row.codeName1 }} </option>
								</select>
							</div> -->
							<!-- <div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">생년월일/<br>법인번호</h5>
								<input type="text" class="inputStyle float-right" style="width:80%" title="생년월일/법인번호 입력란" v-model="custrnmNo" disabled>
							</div> -->
							<div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">사업자명</h5>
								<input type="text" class="inputStyle float-right" style="width:80%" title="사업자명 입력란" v-model="corpName" disabled>
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">대표자명</h5>
								<input type="text" class="inputStyle float-right" style="width:80%" title="대표자명 입력란" v-model="ceoName" disabled>
							</div>
							<!-- <div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">업태</h5>
								<input type="text" class="inputStyle" placeholder="업태" style="width:80%"  v-model="busitype" disabled>
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">종목</h5>
								<input type="text" class="inputStyle" placeholder="종목" style="width:80%"  v-model="busiclass" disabled>
							</div> -->
							<div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">사업장주소</h5>
								<input type="text" class="inputStyle" style="width:20%" title="우편번호 입력란" placeholder="우편번호" disabled v-model="zipcode">
								<!-- <a href="#self" class="btnStyle1 backLightGray float-right" title="주소조회">주소조회</a> -->
								<input type="text" class="inputStyle float-right" style="width:57%" title="사업장 주소 입력란" v-model="woplaceAddress" placeholder="주소" disabled>
								<input type="text" class="inputStyle consolMarginTop float-right" style="width:80%" title="사업장 상세주소 입력란" v-model="woplaceAddressDetail" placeholder="상세주소" disabled>
							</div>
							<!-- <div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">유선전화번호</h5>
								<input type="text" class="inputStyle float-right" style="width:80%" title="유선전화번호 입력란" v-model="wireTel" placeholder="-없이 입력" disabled>
							</div> -->
							<div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">첨부파일</h5>
								<input type="text" class="inputStyle" style="width:58%" title="첨부파일" v-model="attachFileName" disabled>
								<input type="hidden" v-model="attachFilePath">
								<input type="hidden" v-model="fileId">
								<!-- <input type="file" ref="imageInput" style="display:none"> -->
								<!-- <a class="btnStyle1 backLightGray ml15" title="파일첨부">파일첨부</a> -->
								<a href="#self" @click.prevent="fnFileDown(fileId)" class="btnStyle1 backLightGray float-right" title="다운로드">다운로드</a>
								<!-- <p class="mt10 lc-1 Modaltext2" style="margin-left:20%"><i class="far fa-info-circle"></i>PDF, JPG, JPEG, PNG 형식으로 등록해주세요. (최대용량: 5MB)</p> -->
							</div>

							<div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:20%">영업사원</h5>
								<select class="selectStyle2" style="width:80%" title="영업사원 선택란" v-model="salesMan" disabled>
									<option value="">선택하세요</option>
									<option  v-for="(row, index) in salesManArr" :key="index" :value="row.codeVal1"> {{ row.codeName1 }} </option>
								</select>
							</div>
						</div>

						<!-- <h4>도메인 설정</h4>
						<div class="border-line mt20 Dashboard06">
							<div class="of_h">
								<h5 class="inline-block" style="width:20%">도메인 이름</h5>
								<h5 class="inline-block" style="width:80%">{{ domainName }}</h5>
							</div>	
						</div>						 -->
					</div>
					<div class="text-center mt40">
						<a href="#self" @click.prevent="fnCloseLayer" class="btnStyle2 backWhite ml10" title="닫기">닫기</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import myPageApi from '@/modules/myPage/service/myPageApi';

import commonUtilApi from "@/modules/commonUtil/service/commonUtilApi";

export default {
  name: 'corpInfoPopup',
  data() {
    return {
      corpId :"",
      regno : '',                   // 사업자번호
      corpName : '',                // 사업자명
      ceoName : '',                 // ceo명
      // busitype : '',                // 업태
      // busiclass : '',               // 종목
      zipcode :"",                  // 우편번호
      woplaceAddress : '',          //사업장주소
      woplaceAddressDetail : '',    //사업장 상세주소
      // wireTel : '',                 // 유선전화번호
      // domainName : '',              // 도메인 명
      fileId : "",
      attachFileName : '',          // 사업자등록증 이미지명
      attachFilePath : '',          // 사업자등록증 이미지경로

      custNo : "",                  // 고객사 번호
      // custKdCd : "",                // 고객 유형
      // custrnmNo : "",               // 고객 식별 번호
      salesMan : "",                // 영업사원

      // custTypeArr : [],              // 고객 유형 코드값
      salesManArr : []
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
    // this.fnGetCustType();
    this.fnGetSalesMan();
  },
  watch: {
    popReset(){
      this.fnReset();
    },
    // wireTel(){
    //   if(this.wireTel != undefined){
    //     return this.wireTel = this.wireTel.replace(/[^0-9]/g, '');
    //   }
    // }
  },
  methods: {
    fnCloseLayer(){
        jQuery("#corpInfoPopup").modal("hide");
    },
    // 고객 유형 코드 값 조회
    fnGetCustType(){
      var params = {
        codeTypeCd	: "CORP_CUST_TYPE",
        useYN		: "Y"
      };
      commonUtilApi.selectCodeList(params).then(response =>{
        var result = response.data;
        if(result.success){
          this.custTypeArr = result.data;
        }
      });
    },
    fnGetSalesMan(){
      var params = {
        codeTypeCd	: "SALES_MAN",
        useYN		: "Y"
      };
      commonUtilApi.selectCodeList(params).then(response =>{
        var result = response.data;
        if(result.success){
          this.salesManArr = result.data;
        }
      });
    },
    // 데이터 초기화
    fnReset(){
      this.corpId = this.corpInfo.corpId;
      this.regno = this.corpInfo.regno;
      this.corpName = this.$gfnCommonUtils.unescapeXss(this.corpInfo.corpName);
      this.ceoName = this.$gfnCommonUtils.unescapeXss(this.corpInfo.ceoName);
      // this.busitype = this.corpInfo.busitype;
      // this.busiclass = this.corpInfo.busiclass;
      this.zipcode = this.corpInfo.zipcode;
      this.woplaceAddress = this.$gfnCommonUtils.unescapeXss(this.corpInfo.woplaceAddress);
      this.woplaceAddressDetail = this.$gfnCommonUtils.unescapeXss(this.corpInfo.woplaceAddressDetail);
      // this.wireTel = this.corpInfo.offcTel;
      this.fileId = this.corpInfo.fileId;
      this.attachFileName = this.corpInfo.attachFileName;
      this.attachFilePath = this.corpInfo.attachFilePath;
      // this.custKdCd = this.corpInfo.custKdCd;
      // this.custrnmNo = this.corpInfo.custrnmNo;
      this.custNo = this.corpInfo.custNo;
      // this.domainName = this.corpInfo.domainName;
      this.salesMan = !this.$gfnCommonUtils.isEmpty(this.corpInfo.salesId) ? this.corpInfo.salesId : "";
    },
    // 사업자 등록증 다운로드
    fnFileDown(fileId) {
      if(fileId != "") {
        var params = {
            fileId    : fileId
        };
        myPageApi.downloadRegCardImage(params);
      }
    },
  }
}
</script>