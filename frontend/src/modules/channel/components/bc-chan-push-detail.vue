<template>
  <div id="content">
		<article>
			<h4 class="mt40">Application 등록</h4>
			<div class="row mt30">
				<div class="col-xs-12">
					<div class="menuBox">						
						<div class="of_h">
							<h4 class="font-normal inline-block vertical-middle" style="width:15%">APP ID명*</h4>
							<select name="admin030403_1" class="selectStyle3 float-right" style="width:85%">
								<option value="">자동채번</option>
								<option value="">자동채번</option>
							</select>
						</div>						
					</div>
				</div>
			</div>
			
			<h4 class="mt40"><input type="checkbox" id="FCM" class="checkStyle2" value="FCM"><label for="FCM" class="font-size18 font-600">안드로이드(FCM)</label></h4>
			<p class="font-size12 color3 mt20 inline-block"><i class="far fa-info-circle"></i>  Android 푸시 메시지 발송에 필요한 API Key입니다. 등록시 유효성을 검증합니다.</p>
			<div class="row mt10">
				<div class="col-xs-12">
					<div class="menuBox">						
						<div>
							<h4 class="font-normal inline-block" style="width:15%">Package name</h4>
							<input type="text" class="inputStyle" style="width:72%">
						</div>
						<div class="mt10">
							<h4 class="font-normal inline-block" style="width:15%">FCM Server Key</h4>
							<input type="text" class="inputStyle" style="width:72%">
						</div>
						<div class="mt10">
							<h4 class="font-normal inline-block" style="width:15%">Sender ID</h4>
							<input type="text" class="inputStyle" style="width:72%">
							<a href="#self" class="btnStyle2 float-right">인증서 삭제</a>
						</div>				
					</div>
				</div>
			</div>
			
			<h4 class="mt40"><input type="checkbox" id="APNS" class="checkStyle2" value="APNS"><label for="APNS" class="font-size18 font-600">IOS(APNS)</label></h4>
			<div class="row mt10">
				<div class="col-xs-12">
					<div class="menuBox">						
						<div class="of_h">
							<h4 class="font-normal inline-block" style="width:15%">P8인증서</h4>
							<input type="text" class="inputStyle" style="width:62%">
							<a href="#self" class="btnStyle3 gray font13 width90 float-right" style="margin-right:13%; padding:12px 10px">파일선택</a>
						</div>
						<div class="mt10">
							<h4 class="font-normal inline-block" style="width:15%">Bundle ID</h4>
							<input type="text" class="inputStyle" style="width:72%">
							<div class="consolCheck display-block mt5" style="margin-left:15.5%"><input type="checkbox" id="ID" class="checkStyle2" value="ID"><label for="ID">APNS App ID 별도</label></div>
						</div>
						<div class="mt15">
							<h4 class="font-normal inline-block" style="width:15%">Team ID</h4>
							<input type="text" class="inputStyle" style="width:72%">
						</div>
						<div class="mt10">
							<h4 class="font-normal inline-block" style="width:15%">Key ID</h4>
							<input type="text" class="inputStyle" style="width:72%">
							<a href="#self" class="btnStyle2 float-right">인증서 삭제</a>
						</div>	
					</div>
				</div>
			</div>
			
			<p class="font-size12 color3 mt10 inline-block"><i class="far fa-info-circle"></i> 푸시 메시지를 이용할 OS 및 유형에 따라 각 인증서의 항목을 모두 입력해 주세요.</p>
			
			<div class="mt50 text-center">
				<a href="#self" class="btnStyle5 red width120">저장</a>
				<a @click="fnBack" class="btnStyle5 white width120">목록</a>
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
  },
  methods: {
    // 닫기
    fnClose(){
      this.$emit('update:visible', false);
    },
	fnBack(){
		this.$router.go(-1);
	},
    // 등록, 수정
    fnSaveRcsDetail(){
      var params = {
          /* "menus_cd"      : jQuery("#menus_cd").val(),
          "menus_name"    : jQuery("#menus_name").val(),
          "img_tag"      : jQuery("#img_tag").val(),
          "top_menus_cd" : parMenuRow.MENUS_CD,
          "par_menus_cd" : parMenuRow.PAR_MENUS_CD,
          "menus_level"  : (parMenuRow.MENUS_LEVEL) + 1,
          "dis_order"    : jQuery("#dis_order").val(),
          "svc_type_cd"  : jQuery("#svc_type_cd").val(),
          "fixed_menus_yn"   : 1,
          "use_yn"       : jQuery("#use_yn").val(),
          "web_url"      : jQuery("#web_url").val(), */
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
          "menus_cd" : jQuery("#menus_cd").val(),
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
  }
}
</script>