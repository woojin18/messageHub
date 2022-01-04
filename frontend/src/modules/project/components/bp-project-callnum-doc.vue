<template>
<div class="modal modalStyle" id="docPop" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:650px">
			<div class="modal-content">
				<div class="modal-body">
          <div class="of_h">
						<h2>서류 인증</h2>
						<hr>
						<div class="row">
							<div class="col-xs-12 consoleCon">				
								<div class="of_h">
									<h5 class="inline-block" style="width:22%">등록할 발신번호 *</h5>				
									<div class="float-right" style="width:76%">
										<input type="text" class="inputStyle" v-model="callNum">
									</div>
								</div>
								<div class="of_h consolMarginTop">
									<h5 class="inline-block vertical-middle" style="width:22%">통신서비스<br>가입증명원 *</h5>
									<div class="float-right" style="width:76%">
										<input id="joinFile" type="file" class="btnStyle7 minWidthAuto float float-right" style="width:100%" ref="joinFile"/>
									</div>
								</div>							
								<p class="txtCaption color1 mt20">등록할 발신 번호에 해당하는 통신서비스 이용 증명원을 첨부하시기 바랍니다.</p>
								<p>담당자가 등록한 서류 심사 후 영업일 기준 1~2일 내 승인이 완료됩니다.</p>
								<p>첨부 파일 확장자는 <strong>png, jpg, pdf, zip, tif, tiff</strong>만 허용됩니다.</p>
								<p class="txtCaption color3">(5M 이하 파일 가능)</p>
								<h5>[통신서비스 이용 증명원 안내]</h5>
								<p>통신서비스 이용 증명원이란, 이용자 본인이 사용하는 번호임을 증명하는 서류입니다.</p>
								<p>가입한 통신사의 고객센터에서 발급받을 수 있습니다.</p>
								<p>자세한 발급방법은 해당 통신사에 문의하시기 바랍니다.</p>
							</div>
						</div>
					</div>					
					<div class="text-center mt20">
						<a @click="fnApproval" class="btnStyle1 backBlack" activity="SAVE">승인요청</a>
						<a @click="fnCloseLayer" class="btnStyle1 backWhite">닫기</a>						
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
  name: 'docPop',
  watch: {
    callNum () {
      this.callNum = this.callNum.replace(/[^0-9]/g, '')
    }
  },
  data() {
    return {
      callNum : ''
    }
  },
  mounted() {
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
      jQuery("#certiImgFile").val("");
    }
  },
  methods: {
    // 닫기
    fnCloseLayer: function() {
      jQuery("#docPop").modal("hide");
    },
    // 승인요청
    async fnApproval() {
      
      if(this.callNum == '') {
				confirm.fnAlert("", "등록할 발신번호를 입력해주세요.");
        return;
      }
      
      if(this.$refs.joinFile.files[0] === null || this.$refs.joinFile.files[0] === undefined ){
				confirm.fnAlert("", "통신서비스 가입증명원 파일을 등록해주세요.");
        return;
      }

      var fd = new FormData();
      fd.append('corpId', tokenSvc.getToken().principal.corpId);
      fd.append('projectId', this.srcProjectId);
      fd.append('callNum', this.callNum);
      fd.append('userId', tokenSvc.getToken().principal.userId);

      // 첨부파일 정리
      fd.append('joinFile'		, this.$refs.joinFile.files[0]);
      
      await axios.post('/projectApi/manage/saveCallNumDoc',
        fd, {headers: {'Content-Type': 'multipart/form-data'}
      }).then( response => {
        var result = response.data;
        if(result.success) {
          confirm.fnAlert("", "저장에 성공했습니다.");
          jQuery("#docPop").modal("hide");
        } else {
          confirm.fnAlert("", result.message);
        }
      }).catch(function () {
        confirm.fnAlert("", "저장에 실패했습니다.");
      });
    }
  }
}
</script>
