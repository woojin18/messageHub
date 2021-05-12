<template>
	<div id="layerPopup" class="modal fade modalStyle" tabindex="-1" role="dialog" aria-hidden="true" >
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h5 class="lc-1">MO 수신번호 상세</h5>
						<hr>
						<div class="mt20">
							<div>
								<h4 class="font-normal inline-block width120">API KEY</h4>
								<input type="text" class="inputStyle" style="width:76%" v-model="apiKey" disabled>
							</div>
							<div class="mt10">
								<h4 class="font-normal inline-block width120">MO 수신번호</h4>
								<input type="text" class="inputStyle" style="width:76%" v-model="moNumber">
							</div>
							<div class="mt20">
								<h4 class="font-normal inline-block width120">MO 유형</h4>
								<div class="inline-block">
									<input type="radio" id="SMS" value="SMSMO" class="cBox" v-model="moType"> <label for="SMS" class="payment mr30 font-size12">SMS MO</label>
									<input type="radio" id="MMS" value="MMSMO" class="cBox" v-model="moType"> <label for="MMS" class="payment font-size12">MMS MO</label>		
								</div>
							</div>
							<div class="mt20">
								<h4 class="font-normal inline-block width120">타 프로젝트<br>사용여부</h4>
								<div class="inline-block">
									<input type="radio" id="ALL" value="ALL" class="cBox" v-model="pjtAllNo"> <label for="ALL" class="payment mr30">공용</label>
									<input type="radio" id="NO" value="NO" class="cBox" v-model="pjtAllNo"> <label for="NO" class="payment">전용</label>		
								</div>
							</div>
							<p class="mt30 lc-1 Modaltext font-size12" style="margin:0">
                <i class="far fa-info-circle"></i> MO 기본료 부과: 면제
                <br>
                <i class="far fa-info-circle"></i> 건당 수신료: SMS 7원/LMS 20원/MMS 50원
              </p>
						</div>
					
					</div>
					<div class="text-center mt40">
						<a @click="fnSave" class="btnStyle3 black font14" data-toggle="modal">토큰요청</a>
						<a @click="fnClose" ref="closeBtn" class="btnStyle3 white font14" data-dismiss="modal">취소</a>						
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import api from '../service/api'
import confirm from "@/modules/commonUtil/service/confirm"

export default {
  name: 'bpChanMo',
  data() {
    return {
      apiKey    : "",
      moNumber  : "",
      moType    : "",
      pjtAllNo  : ""
    }
  },
  props: {
    save_status: {
      type: String,
      require: true,
      default: 'C'
    },
    row_data : {
      type: Object,
      require: false,
    },
    projectId : {
      type : String,
      require : true
    }
  },
  watch: {
    row_data: function(newVal, oldVal) {
      this.apiKey = newVal.apiKey;
      this.moNumber = newVal.moNumber;
      this.moType = newVal.moType;
      this.pjtAllNo = newVal.pjtAllNo;
    }
  },
  mounted() {
    
  },
  methods: {
    // 닫기
    fnClose(){
      jQuery("#layerPopup").modal('hide');
    },
    // 저장
    fnSave(){
      // 타 프로젝트 사용 여부
      var projectStr = 'ALL';
      if(this.pjtAllNo != 'ALL' ){
        projectStr = this.projectId;
      }

      var params = {
        "sts"         : this.save_status,
        "apiKey"      : this.apiKey,
        "moNumber"    : this.moNumber,
        "moType"      : this.moType,
        "projectId"   : projectStr
      }

      api.saveMoCallback(params).then(response =>{
        var result = response.data;

        if(result.success) {
          confirm.fnAlert("", "저장되었습니다.");
          // 닫기 버튼
          this.$refs.closeBtn.click();
          // 부모창 리스트 조회
          this.$parent.fnSearch();
          // 창닫기
        } else {
          confirm.fnAlert("", result.message);
        }
      });
    }
  }
}
</script>