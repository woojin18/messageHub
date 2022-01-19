<template>
  <div class="modal modalStyle" id="proxyPop" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h">
            <h2>대리인 등록 요청</h2>
						<hr>
            <div class="mt15">
              <h5 class="inline-block" style="width:27%">발신번호 변작방지 및<br>사전등록 동의서 *</h5>
              <input id="file1" ref="agreeFile" type="file" class="btnStyle7 minWidthAuto float" style="display : inline; width : 72%;">
						</div>
            <div class="mt15">
              <h5 class="inline-block" style="width:27%">대리인 등록 신청서 *</h5>
              <input id="file2" ref="reqFile" type="file" class="btnStyle7 minWidthAuto float" style="display : inline; width : 72%;">
						</div>
            <div class="mt15">
              <h5 class="inline-block" style="width:27%">인감증명서 *</h5>
              <input id="file3" ref="sealFile" type="file" class="btnStyle7 minWidthAuto float" style="display : inline; width : 72%;">
						</div>
            <div class="mt15">
              <h5 class="inline-block" style="width:27%">인감이 날인된 위임장 *</h5>
              <input id="file4" ref="authFile" type="file" class="btnStyle7 minWidthAuto float" style="display : inline; width : 72%;">
						</div>
            <div class="mt15">
              <h5 class="inline-block" style="width:27%">재직증명서 *</h5>
              <input id="file5" ref="workFile" type="file" class="btnStyle7 minWidthAuto float" style="display : inline; width : 72%;">
						</div>
            <p class="txtCaption mt20">담당자가 등록한 서류 심사 후 영업일 기준 1~2일 내 승인이 완료됩니다. (반려 후 재 신청 가능)</p>
            <p class="mt5">첨부 파일 확장자는 <strong>png, jpg, pdf, zip, tif, tiff</strong>만 허용됩니다.</p>
            <p class="txtCaption color3">(5M 이하 파일 가능)</p>
					</div>
					<div class="text-center mt40">
            <a @click="fnSaveWithFile" class="btnStyle3 black font14" activity="SAVE">승인요청</a>
						<a @click="fnCloseLayer" ref="closeBtn" class="btnStyle3 white font14">닫기</a>						
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import axios from 'axios'
import tokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm"

export default {
  name: 'proxyPop',
  data() {
    return {
    }
  },
  props: {
  },
  methods: {
    // 닫기
    fnCloseLayer: function() {
      jQuery("#proxyPop").modal("hide");
    },
    // 파일 업로드와 같이 저장
    async fnSaveWithFile(){
      
      if(this.$refs.agreeFile.files[0] === null || this.$refs.agreeFile.files[0] === undefined ){
				confirm.fnAlert("", "발신번호 변작방지 및 사전등록 동의서 파일을 등록해주세요.");
        return;
      }
      if(this.$refs.reqFile.files[0] === null || this.$refs.reqFile.files[0] === undefined ){
				confirm.fnAlert("", "대리인 등록 신청서 파일을 등록해주세요.");
        return;
      }
      if(this.$refs.sealFile.files[0] === null || this.$refs.sealFile.files[0] === undefined ){
				confirm.fnAlert("", "인감증명서 파일을 등록해주세요.");
        return;
      }
      if(this.$refs.authFile.files[0] === null || this.$refs.authFile.files[0] === undefined ){
				confirm.fnAlert("", "인감이 날인된 위임장 파일을 등록해주세요.");
        return;
      }
      if(this.$refs.workFile.files[0] === null || this.$refs.workFile.files[0] === undefined ){
				confirm.fnAlert("", "재직증명서 파일을 등록해주세요.");
        return;
      }

      var fd = new FormData();
      fd.append('userId', tokenSvc.getToken().principal.userId);
      fd.append('corpId', tokenSvc.getToken().principal.corpId);
      fd.append('agreeFile'		, this.$refs.agreeFile.files[0]);
      fd.append('reqFile'		, this.$refs.reqFile.files[0]);
      fd.append('sealFile'		, this.$refs.sealFile.files[0]);
      fd.append('authFile'		, this.$refs.authFile.files[0]);
      fd.append('workFile'		, this.$refs.workFile.files[0]);

      var parent = this.$parent
      await axios.post('/projectApi/manage/saveCallNumProxy',
        fd, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }
      ).then( response => {
        var result = response.data;
        if(result.success) {
          confirm.fnAlert("", "저장에 성공했습니다.");
          jQuery("#proxyPop").modal("hide");
          parent.proxyState = '10'
        } else {
          confirm.fnAlert("", result.message);
        }
      })
      .catch(function (e) {
        console.log(e);
				confirm.fnAlert("", "등록에 실패했습니다.");
      });
    }
  }
}
</script>
