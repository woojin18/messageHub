<template>
  <div class="modal modalStyle" id="preRegExPop" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h">
            <h2>SMS 사전 등록 예외 대상 사업자 등록</h2>
						<hr>
						<div class="mt20">
              <h5 class="inline-block" style="width:20%">신청유형 *</h5>
							<select v-model="reqType" name="admin030502_1" class="selectStyle3 width414 font-size14" >
								<option value="NOSPAM">스팸문자 미발송 사업자</option>
                <option value="REGNUM">발신번호 사전등록 시스템을 갖춘 사업자</option>
                <option value="PUBLIC">공공기관</option>
                <option value="SPECIAL">특수부가통신사업자</option>
							</select>
						</div>
            <div class="mt15">
              <h5 class="inline-block" style="width:20%">첨부파일1 *</h5>
              <input id="file1" ref="fileRef1" type="file" class="btnStyle7 minWidthAuto float" style="display : inline; width : 79%;">
						</div>
            <div class="mt15">
              <h5 class="inline-block" style="width:20%">첨부파일2</h5>
              <input id="file2" ref="fileRef2" type="file" class="btnStyle7 minWidthAuto float" style="display : inline; width : 79%;">
						</div>
            <div class="mt15">
              <h5 class="inline-block" style="width:20%">첨부파일3</h5>
              <input id="file3" ref="fileRef3" type="file" class="btnStyle7 minWidthAuto float" style="display : inline; width : 79%;">
						</div>
            <div class="mt15">
              <h5 class="inline-block" style="width:20%">첨부파일4</h5>
              <input id="file4" ref="fileRef4" type="file" class="btnStyle7 minWidthAuto float" style="display : inline; width : 79%;">
						</div>
					</div>
					<div class="text-center mt40">
            <a @click="fnSaveWithFile" class="btnStyle3 black font14" data-toggle="modal" activity="SAVE">신청</a>
						<a @click="fnCloseLayer" ref="closeBtn" class="btnStyle3 white font14" data-dismiss="modal">닫기</a>						
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
  name: 'preRegExPop',
  data() {
    return {
      fileName1 : "",
      filePath1 : "",
      fileName2 : "",
      filePath2 : "",
      fileName3 : "",
      filePath3 : "",
      fileName4 : "",
      filePath4 : "",
    }
  },
  props: {
    reqType : {
      type: String,
      require: true
    }
  },
  methods: {
    // 닫기
    fnCloseLayer: function() {
      jQuery("#preRegExPop").modal("hide");
    },
    // 파일 업로드와 같이 저장
    async fnSaveWithFile(){
      console.log(this.$refs.fileRef1.files[0]);
      if(this.$refs.fileRef1.files[0] === null || this.$refs.fileRef1.files[0] === undefined ){
				confirm.fnAlert("", "파일을 등록해주세요.");
        return;
      }

      var fd = new FormData();

      // 첨부파일 정리
      if( this.$refs.fileRef1.files[0] != null || this.$refs.fileRef1.files[0] != undefined ){
        fd.append('uploadFiles', this.$refs.fileRef1.files[0]);
      }
      if( this.$refs.fileRef2.files[0] != null || this.$refs.fileRef2.files[0] != undefined ){
        fd.append('uploadFiles', this.$refs.fileRef2.files[0]);
      }
      if( this.$refs.fileRef3.files[0] != null || this.$refs.fileRef3.files[0] != undefined ){
        fd.append('uploadFiles', this.$refs.fileRef3.files[0]);
      }
      if( this.$refs.fileRef4.files[0] != null || this.$refs.fileRef4.files[0] != undefined ){
        fd.append('uploadFiles', this.$refs.fileRef4.files[0]);
      }
      
      fd.append('userId', tokenSvc.getToken().principal.userId);
      fd.append('corpId', tokenSvc.getToken().principal.corpId);
      fd.append('reqType', this.reqType);

      await axios.post('/projectApi/manage/savePreRegExWithUploadFiles',
        fd, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }
      ).then( response => {
        var result = response.data;
        if(result.success) {
          confirm.fnAlert("", "저장에 성공했습니다.");
          // 닫기 버튼
          this.$refs.closeBtn.click();
          // 발신번호관리 화면으로 이동
          this.$parent.fnMoveMainTab('callbackManage');
        } else {
  				confirm.fnAlert("", "저장에 실패했습니다.");
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
