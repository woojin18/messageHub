<template>
  <div class="modal fade modalStyle" id="preRegExPop" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h">
						<h5 class="lc-1">사전 등록 예외 대상 사업자 등록</h5>
						<hr>
						<div class="mt20">
							<h4 class="font-normal inline-block width90">신청유형 *</h4>
							<select v-model="reqType" name="admin030502_1" class="selectStyle3 width414 font-size14" >
								<option value="spam">스팸문자 미발송 사업자</option>
                <option value="system">발신번호 사전등록 시스템을 갖춘 사업자</option>
                <option value="government">공공기관</option>
                <option value="special">특수부가통신사업자</option>
							</select>
						</div>
						<div class="mt15">
							<h4 class="font-normal inline-block width90">첨부파일1</h4>
							<input type="text" class="inputStyle width291" v-model="fileName1" @click="fnFileDownload(1)" readonly>
              <input ref="fileInput1" type="file" @change="fnFileUpload(1)" style="display:none;">
							<a @click="fnFileUploadClick(1)" class="btnStyle7">파일선택</a>
						</div>
						<div class="mt15">
							<h4 class="font-normal inline-block width90">첨부파일2</h4>
							<input type="text" class="inputStyle width291" v-model="fileName2" @click="fnFileDownload(2)" readonly>
              <input ref="fileInput2" type="file" @change="fnFileUpload(2)" style="display:none;">
							<a @click="fnFileUploadClick(2)" class="btnStyle7">파일선택</a>
						</div>
						<div class="mt15">
							<h4 class="font-normal inline-block width90">첨부파일3</h4>
							<input type="text" class="inputStyle width291" v-model="fileName3" @click="fnFileDownload(3)" readonly>
              <input ref="fileInput3" type="file" @change="fnFileUpload(3)" style="display:none;">
							<a @click="fnFileUploadClick(3)" class="btnStyle7">파일선택</a>
						</div>
						<div class="mt15">
							<h4 class="font-normal inline-block width90">첨부파일4</h4>
							<input type="text" class="inputStyle width291" v-model="fileName4" @click="fnFileDownload(4)" readonly>
              <input ref="fileInput4" type="file" @change="fnFileUpload(4)" style="display:none;">
							<a @click="fnFileUploadClick(4)" class="btnStyle7">파일선택</a>
						</div>
					</div>
					<div class="text-center mt40">
						<a @click="fnSave" class="btnStyle3 black font14" data-toggle="modal">신청</a>
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
import projectApi from '../service/projectApi'

export default {
  name: 'prePaidCashLayer',
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
    fnCloseLayer: function() {
      $("#preRegExPop").modal("hide");
    },
    // 요청
    fnSave: function() {

      if( this.fileName1 === '' || this.fileName1 === undefined ){
        alert("파일을 등록해주세요");
        return;
      }

      var params = {
        "reqType"   : this.reqType,
        "fileName1" : this.fileName1,
        "filePath1" : this.filePath1,
        "fileName2" : this.fileName2,
        "filePath2" : this.filePath2,
        "fileName3" : this.fileName3,
        "filePath3" : this.filePath3,
        "fileName4" : this.fileName4,
        "filePath4" : this.filePath4,
        "loginId"   : tokenSvc.getToken().principal.userId,
        "corpId"    : tokenSvc.getToken().principal.corpId
      }

      projectApi.savePreRegEx(params).then(response =>{
        var result = response.data;
        console.log(response);

        if(result.success) {
          alert("저장에 성공했습니다.");
          // 닫기 버튼
          this.$refs.closeBtn.click();
          // 발신번호관리 화면으로 이동
          this.$parent.fnMoveMainTab('callbackManage');
        } else {
          alert("저장에 실패했습니다.");
        }
      });
    },
    // 파일선택 클릭시
    fnFileUploadClick(number){
      if( number === 1 ){
        this.$refs.fileInput1.click();
      } else if( number === 2 ){
        this.$refs.fileInput2.click();
      } else if( number === 3 ){
        this.$refs.fileInput3.click();
      } else if( number === 4 ){
        this.$refs.fileInput4.click();
      }
    },
    // 파일 업로드
    async fnFileUpload(number){
      var fileInput;

      if( number === 1 ){
        fileInput = this.$refs.fileInput1;
      } else if( number === 2 ){
        fileInput = this.$refs.fileInput2;
      } else if( number === 3 ){
        fileInput = this.$refs.fileInput3;
      } else if( number === 4 ){
        fileInput = this.$refs.fileInput4;
      }
      
      console.log(fileInput.files[0]);

      if(fileInput.value == 0){
        alert("파일을 등록해주세요.");
        return;
      }

      var fd = new FormData();
      fd.append('uploadFile', fileInput.files[0]);
      fd.append('loginId', tokenSvc.getToken().principal.userId);

      await axios.post('/api/public/common/uploadFile',
        fd, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }
      ).then( response => {
        console.log(response);
        if(response.data != null && response.data.success){
          alert("등록되었습니다.");
          if( number === 1 ){
            this.fileName1 = response.data.data.attachFileName;
            this.filePath1 = response.data.data.attachFilePath;
          } else if( number === 2 ){
            this.fileName2 = response.data.data.attachFileName;
            this.filePath2 = response.data.data.attachFilePath;
          } else if( number === 3 ){
            this.fileName3 = response.data.data.attachFileName;
            this.filePath3 = response.data.data.attachFilePath;
          } else if( number === 4 ){
            this.fileName4 = response.data.data.attachFileName;
            this.filePath4 = response.data.data.attachFilePath;
          }
        } else {
          if(typeof(response.data.message) !== 'undefined' || response.data.message !== null) {
            alert(response.data.message);
          } else {
            alert("등록에 실패했습니다.");
          }
        }
      })
      .catch(function (e) {
        console.log(e);
        alert("등록에 실패했습니다.");
      });
    },
    // 다운로드... 직므 안됨
    fnFileDownload(number){

      var filePath, fileName;

      if( number === 1 ){
        filePath = this.filePath1;
        fileName = this.fileName1;
      } else if( number === 2 ){
        filePath = this.filePath2;
        fileName = this.fileName2;
      } else if( number === 3 ){
        filePath = this.filePath3;
        fileName = this.fileName3;
      } else if( number === 4 ){
        filePath = this.filePath4;
        fileName = this.fileName4;
      }

      var params = {
          filePath    : filePath,
          fileName    : fileName
      };
      /* projectApi.downloadFile(params).then(response =>{
          var result = response;
          console.log(response);

          const url = window.URL.createObjectURL(new Blob([response.data]));
          const link = document.createElement("a");
          link.href = url;
          link.setAttribute("download", fileName); //or any other extension
          document.body.appendChild(link);
          link.click();
      }); */
    }
  }
}
</script>
