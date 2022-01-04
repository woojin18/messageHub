<template>
  <div class="modal modalStyle" id="allPop" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h">
            <h2>일괄등록</h2>
						<hr>
            <div class="row">
							<div class="col-xs-12 consoleCon">	
								<div class="of_h consolMarginTop">
									<h5><a @click="fnFileDown('FLE0CheTyB', '대리인등록.xlsx')" class="color-blue">업로드 샘플 다운로드</a></h5>
								</div>
								<div class="of_h consolMarginTop">
									<h5 class="inline-block vertical-middle" style="width:22%">파일 선택 *</h5>
									<div class="float-right" style="width:76%">
										<input id="allFile" type="file" class="btnStyle7 minWidthAuto float float-right" style="width:100%" ref="allFile"/>
									</div>
								</div>	
							</div>
						</div>
          </div>
          <div class="text-center mt40">
            <a @click="fnSaveWithFile" class="btnStyle3 black font14" activity="SAVE">일괄등록</a>
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
import commonUtilApi from "@/modules/commonUtil/service/commonUtilApi";

export default {
  name: 'allPop',
  data() {
    return {
    }
  },
  props: {
    srcProjectId : {
      type: String,
      require: true
    },
    detailCnt : {
      type : Number,
      require : true
    }
  },
  watch: {
    detailCnt (){
      jQuery("#allFile").val("");
    }
  },
  methods: {
    // 닫기
    fnCloseLayer: function() {
      jQuery("#allPop").modal("hide");
    },
    // 파일 업로드와 같이 저장
    async fnSaveWithFile(){
      if(this.$refs.allFile.files[0] === null || this.$refs.allFile.files[0] === undefined ){
				confirm.fnAlert("", "파일을 등록해주세요.");
        return;
      }

      var fd = new FormData();
      fd.append('corpId', tokenSvc.getToken().principal.corpId);
      fd.append('projectId', this.srcProjectId);
      fd.append('userId', tokenSvc.getToken().principal.userId);

      // 첨부파일 정리
      fd.append('allFile'		, this.$refs.allFile.files[0]);

      await axios.post('/projectApi/manage/saveCallNumAll',
        fd, {headers: {'Content-Type': 'multipart/form-data'}
      }).then( response => {
        var result = response.data;
        if(result.success) {
          confirm.fnAlert("", "저장에 성공했습니다.");
          jQuery("#allPop").modal("hide");
        } else {
					confirm.fnAlert('', result.message);
        }
      })
      .catch(function (e) {
        console.log(e);
				confirm.fnAlert("", "등록에 실패했습니다.");
      });
    },
	// 파일다운로드
    fnFileDown: function(fileId, name) {
      if(fileId != "") {
        var params = {
              fileId    : fileId,
              fileNm      : name
          };

        commonUtilApi.downloadFile(params).then(response =>{
            var result = response;

            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement("a");
            link.href = url;
            link.setAttribute("download", name); //or any other extension
            document.body.appendChild(link);
            link.click();
        });
      }
    },
  }
}
</script>
