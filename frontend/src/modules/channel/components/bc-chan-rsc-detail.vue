<template>
  <div id="content">
		<article>
			<div class="contentHeader mb20">
				<h2>브랜드 등록</h2>				
			</div>

			<!-- 본문 -->
			<div class="row">
				<div class="col-xs-4 sub03_4_1_1_1">
					<!-- 미리보기 -->
					<div class="phoneWrap">
						<img src="common/images/phone_01.svg" alt="프리 템플릿">
						<div class="phoneTextWrap2">
							<div>
								<div class="text">
									<p class="text-main">전화번호</p>
									<p class="text-sub">02-333-3333</p>
								</div>
								<div class="text mt15">
									<p class="text-main">웹사이트</p>
									<p class="text-sub">http://www.uplus.co.kr</p>
								</div>
								<div class="text mt15">
									<p class="text-main">이메일</p>
									<p class="text-sub">test@uplus.co.kr</p>
								</div>
								<div class="text mt15">
									<p class="text-main">주소</p>
									<p class="text-sub">03909 서울특별시 마포구 매봉산로 45<br>(상암동, KBS미디어센터) 100호</p>
								</div>
							</div>
						</div>
					</div>
				</div>
        <!-- 미리보기 -->
        <!-- 인풋 -->
				<div class="col-xs-6 of_h">								
					<div class="of_h">
						<p class="float-left font-size18" style="width:28%">API Key</p>
						<input id="apiKey" type="text" class="inputStyle float-left" style="width:72%">
					</div>
          <div class="of_h">
						<p class="float-left font-size18" style="width:28%">API Secret Key</p>
						<input id="apiSecretKey" type="text" class="inputStyle float-left" style="width:54%">
            <a  class="btnStyle7 minWidthAuto float-right" style="width:17%">중복확인</a>
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">브랜드명 *</p>
						<input id="brandName" type="text" class="inputStyle float-left" style="width:72%" v-model="this.projectId">
					</div>								
					<div class="mt20 of_h">
						<p class="float-left vertical-top font-size18" style="width:28%">브랜드 설명</p>
						<input id="brandDesc" type="text" class="inputStyle float-left" style="width:72%; height:160px" v-model="this.save_status">
					</div>				
									
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">타 프로젝트<br>사용여부</p>
						<div class="float-left; width:72%">
							<input type="radio" name="otherProjectYn" value="Y" class="cBox" id="yes" checked=""> <label for="yes" class="payment mt10 mr30">공용</label>
							<input type="radio" name="otherProjectYn" value="N" class="cBox" id="no"> <label for="no" class="payment mt10">전용</label>		
						</div>
					</div>

					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">백그라운드 이미지</p>
						<input id="backImg" type="text" class="inputStyle float-left" style="width:54%">
						<a  class="btnStyle7 minWidthAuto float-right" style="width:17%">파일선택</a>						
					</div>
					<p class="font-size12 color3 mt10 ml_28"><i class="far fa-info-circle"></i>최대사이즈 : 1080X1080px / 1:1 비율 권장 / 파일형식 : jpg, png (최대 1MB)</p>

					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">프로필 이미지 *</p>
						<input id="profileImg" type="text" class="inputStyle float-left"  style="width:54%">
						<a  class="btnStyle7 minWidthAuto float-right" style="width:17%">파일선택</a>
						<p class="font-size12 color3 mt10 ml_28"><i class="far fa-info-circle"></i>최대사이즈 : 1080X1080px / 1:1 비율 권장 / 파일형식 : jpg, png (최대 1MB)</p>
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">전화번호 *</p>
						<input id="phoneNum" type="text" class="inputStyle float-left" style="width: 72%">
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">메뉴버튼설정 *</p>
						<div class="float-left" style="width:72%">
							<div class="consolCheck" style="width:19%"><input type="checkbox" id="Chat" class="checkStyle2" value="Chat"><label for="Chat">Chat</label></div>
							<div class="consolCheck" style="width:19%"><input type="checkbox" id="Call" class="checkStyle2" value="Call"><label for="Call">Call</label></div>
							<div class="consolCheck" style="width:19%"><input type="checkbox" id="Web" class="checkStyle2" value="Web"><label for="Web">Web</label></div>
							<div class="consolCheck" style="width:19%"><input type="checkbox" id="Store" class="checkStyle2" value="Store"><label for="Store">Store</label></div>
							<div class="consolCheck" style="width:19%"><input type="checkbox" id="Order" class="checkStyle2" value="Order"><label for="Order">Order</label></div>
						</div>
						<div class="ml_28 float-left mt10" style="width:72%">
							<div class="consolCheck" style="width:19%"><input type="checkbox" id="Buy" class="checkStyle2" value="Buy"><label for="Buy">Buy</label></div>
							<div class="consolCheck" style="width:19%"><input type="checkbox" id="Ticket" class="checkStyle2" value="Ticket"><label for="Ticket">Ticket</label></div>
							<div class="consolCheck" style="width:25%"><input type="checkbox" id="More-Info" class="checkStyle2" value="More-Info"><label for="More-Info">More Info</label></div>
						</div>
					</div>
					<div class="mt20 ml_28 of_h">
						<p class="inline-block font-size16 float-left mt10" style="width:15%">-Web *</p>
						<input type="text" class="inputStyle" style="width:85%">
					</div>
					<div class="mt20 ml_28 of_h">
						<p class="inline-block font-size16 float-left mt10" style="width:15%">-Store *</p>
						<input type="text" class="inputStyle" style="width:85%">
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">카테고리 1 *</p>
						<select id="cate1" class="float-left selectStyle3" style="width:72%">
							<option value="">카테고리 1</option>
							<option value="">카테고리 1</option>
						</select>
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">카테고리 2 *</p>
						<select id="cate2" class="selectStyle3" style="width:72%">
							<option value="">카테고리 2</option>
							<option value="">카테고리 2</option>
						</select>
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">카테고리 3 *</p>
						<input id="cate3" type="text" class="inputStyle" style="width:72%">
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">홈페이지 주소</p>
						<input id="homepageAddr" type="text" class="inputStyle" style="width:72%">
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">이메일</p>
						<input id="email1" type="text" class="inputStyle float-left mr20" style="width:31%">@<input id="email2" type="text" class="inputStyle ml20" style="width:31%; margin-left: 10px !important;">
						<select id="emailSelect" class="selectStyle3 ml_28 mt10" style="width:72%">
							<option value="1" selected>직접입력</option>
              <option value="naver.com">naver.com</option>
              <option value="hanmail.net">hanmail.net</option>
              <option value="hotmail.com">hotmail.com</option>
              <option value="nate.com">nate.com</option>
              <option value="yahoo.co.kr">yahoo.co.kr</option>
              <option value="empas.com">empas.com</option>
              <option value="dreamwiz.com">dreamwiz.com</option>
              <option value="freechal.com">freechal.com</option>
              <option value="lycos.co.kr">lycos.co.kr</option>
              <option value="korea.com">korea.com</option>
              <option value="gmail.com">gmail.com</option>
              <option value="hanmir.com">hanmir.com</option>
              <option value="paran.com">paran.com</option>
              <option value="daum.com">daum.com</option>
						</select>
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">주소</p>
						<input id="postAddr" type="text" class="inputStyle" style="width:32%">
						<a  class="btnStyle7 vertical-middle ml10 minWidthAuto" style="width:17%">우편번호</a>
					</div>
					<div class="ml_28">
						<input id="addr1" type="text" class="inputStyle mr10 mt10" style="width:65%">
						<input id="addr2" type="text" class="inputStyle mt10" style="width:32%">						
					</div>

					<hr class="mt60">
					<h2 class="mt60 font-size22">발신번호 등록</h2>
					<p class="font-size12 color3 mt10"><i class="far fa-info-circle"></i>웹템플릿에서 등록한 브랜드와 승인 완료된 브랜드의 발신번호만 보여집니다.</p>

					<div class="mt30 of_h">
						<p class="float-left font-size18" style="width:28%">대표발신번호 *</p>
						<div class="consolCheck" style="width:25%"><input type="checkbox" id="same" class="checkStyle2" value="same"><label for="same">전화번호와 동일</label></div>
						<input type="text" class="inputStyle" style="width:26%">
						<p class="font-size12 color3 inline-block ml10" style="width:17%">*휴대폰 등록 불가</p>
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">발신번호명 *</p>
						<input type="text" class="inputStyle float-left" style="width:72%">
					</div>
					<div class="mt20 of_h">
						<p class="float-left line-height1-5em font-size18" style="width:28%">대표번호<br>문자수신 서비스 *</p>
						<div class="consolCheck vertical-top"  style="width:72%"><input type="checkbox" id="use" class="checkStyle2" value="use"><label for="use">사용중</label>
						<p class="font-size12 color3 mt5">대표번호 문자수신(MO)서비스를 현재 사용하고 계실 경우 반드시 해당정보를 체크해 주셔야 합니다.</p>
						</div>						
					</div>
					<div class="mt20 of_h">
						<p class="float-left font-size18" style="width:28%">추가발신번호</p>
						<input type="text" class="inputStyle" style="width:61%"><a  class="btnStyle7 vertical-middle ml10 minWidth46"><i class="far fa-plus"></i></a>
						<p class="font-size12 color3 mt5 ml_28">추가발신번호가 있는 경우 + 버튼을 클릭하세요. 최대 10개까지 개별등록 가능합니다.</p>
						<input type="text" class="inputStyle ml_28" style="width: 61%"><a  class="btnStyle7 ml10 minWidth46 mt10"><i class="far fa-minus"></i></a>
						<input type="text" class="inputStyle ml_28" style="width: 61%"><a  class="btnStyle7 ml10 minWidth46 mt10"><i class="far fa-minus"></i></a>
					</div>
					<div class="mt20 of_h">
						<p class="float-left vertical-middle line-height1-5em font-size18" style="width:28%">통신서비스<br>가입증명원 *</p>
						<a  class="btnStyle7 vertical-middle float-left minWidthAuto" style="width:17%">파일선택</a>
						<input type="text" class="inputStyle float-right" style="width:54%">
						
						<div class="font-size12 color3 mt10 ml_28 float-left" style="width:73%">
							<div class="consolCheck float-left mb40"><input type="checkbox" id="Lmit" class="checkStyle2" value="Lmit"><label for="Lmit"></label></div>
							<p>메시지 발송 시 기본템플릿(구.Free템플릿)은 정보성에 한하여 이용 가능합니다.<br>규정위반 시 서비스 이용이 제한될 수 있습니다.</p>
						</div>						
					</div>
					<div class="mt20 float-right">
						<a v-if="this.save_status = 'C'" @click="fnSaveRcsDetail" class="btnStyle5 red float-left width120">저장</a>
						<a v-if="this.save_status = 'C'" @click="fnSaveRcsDetail" class="btnStyle5 red float-left ml10 width120">승인요청</a>
            <a v-if="this.save_status = 'U'" @click="fnSaveRcsDetail" class="btnStyle5 red float-left ml10 width120">수정요청</a>
            <a v-if="this.save_status = 'U'" @click="fnSaveRcsDetail" class="btnStyle5 red float-left ml10 width120">삭제요청</a>
						<a @click="fnSaveRcsDetail" class="btnStyle5 white float-left ml10 width120">목록</a>
					</div>
				</div>	
        <!-- 인풋 -->
				<p></p>
				<div class="col-xs-2">
				</div>			
			</div>
			<!-- //본문 -->

			<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
		</article>
	</div>
</template>

<script>
/* import MenuApi from "@/modules/system/service/MenuManageApi"; */

export default {
  name: 'bcChanRcsDetail',
  data() {
    return {
      save_status : '', // 등록 수정 여부
      projectId : ''
    }
  },
  mounted() {
    this.projectId = this.$route.params.projectId;
    this.save_status = this.$route.params.save_status;

    console.log(this.save_status);
  },
  methods: {
    // 닫기
    fnClose(){
      this.$emit('update:visible', false);
    },
    // 등록, 수정
    fnSaveRcsDetail(){
      var parMenuRow = this.parRowData;
      var params = {
          /* "menus_cd"      : $("#menus_cd").val(),
          "menus_name"    : $("#menus_name").val(),
          "img_tag"      : $("#img_tag").val(),
          "top_menus_cd" : parMenuRow.MENUS_CD,
          "par_menus_cd" : parMenuRow.PAR_MENUS_CD,
          "menus_level"  : (parMenuRow.MENUS_LEVEL) + 1,
          "dis_order"    : $("#dis_order").val(),
          "svc_type_cd"  : $("#svc_type_cd").val(),
          "fixed_menus_yn"   : 1,
          "use_yn"       : $("#use_yn").val(),
          "web_url"      : $("#web_url").val(), */
          "sts" : this.save_status
      };

      /* MenuApi.saveMenuList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          alert("저장되었습니다.");
          // 창닫기
          this.$emit('update:visible', false);
          // 부모창 리스트 조회
          this.$parent.fnSearch();
        } else {
          alert("저장에 실패.");
        }
      }); */
    },
    // 삭제
    fnDelete(){
      var params = {
          "menus_cd" : $("#menus_cd").val(),
          "sts" : "D"
      };

      /* MenuApi.saveMenuList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          alert("삭제되었습니다.");
          // 창닫기
          this.fnClose();
          // 부모창 리스트 조회
          this.$parent.fnSearch();
        } else {
          alert("저장에 실패.");
        }
      }); */
    }
  },
}
</script>