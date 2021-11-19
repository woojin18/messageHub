<template>
	<div id="layerPopup" class="modal modalStyle" tabindex="-1" role="dialog" aria-hidden="true" >
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>MO 수신번호 등록</h2>
						<hr>
						<div class="of_h">
        <!--       <h5 class="inline-block" style="width:20%">API KEY</h5>
              <div style="width:80%" class="float-right">
                <input type="text" class="inputStyle float-left" v-model="apiKey" placeholder="자동 채번됩니다." disabled>
              </div> -->
							<div class="of_h mt10">
								<h5 class="inline-block" style="width:20%">MO 수신번호</h5>
                <div style="width:80%" class="float-right">
                  <input type="text" class="inputStyle float-left" v-model="moNumber">
                </div>
							</div>
							<div class="of_h mt10">
								<h5 class="inline-block" style="width:20%">MO 유형</h5>
								<div class="inline-block">
									<input type="radio" id="SMS" value="SMSMO" class="cBox" v-model="moType"> <label for="SMS" class="payment mr30 font-size12">SMS MO</label>
									<input type="radio" id="MMS" value="MMSMO" class="cBox" v-model="moType"> <label for="MMS" class="payment mr30 font-size12">MMS MO</label>		
									<input type="radio" id="LMS" value="LMSMO" class="cBox" v-model="moType"> <label for="LMS" class="payment font-size12">LMS MO</label>		
								</div>
							</div>
							<div class="of_h mt10">
								<h5 class="inline-block" style="width:20%">웹훅 URL</h5>
								<div style="width:80%" class="float-right">
									<div style="width:70%" class="float-left">
										<input type="text" class="inputStyle float-left" v-model="webhookUrl" placeholder="[http:// 혹은 https://]를 포함한 URL" @change="fnChgConnStatus()">
									</div>
									<div style="width:30%" class="float-right">
										<button class="btnStyle1 backLightGray float-right width120" @click="fnCheckWebhookUrl">연결 확인</button>
									</div>
								</div>
							</div>
							<!-- <p class="mt30 lc-1 Modaltext font-size12" style="margin:0">
							<i class="far fa-info-circle"></i> MO 기본료 부과: 면제
							<br>
							<i class="far fa-info-circle"></i> 건당 수신료: SMS 7원/LMS 20원/MMS 50원
							</p> -->
						</div>
					
					</div>
					<div class="text-center mt40">
						<a v-if="this.save_status === 'C'" @click="fnSave('C')" class="btnStyle3 black font14" data-toggle="modal" activity="SAVE">등록</a>
						<a v-if="this.save_status === 'U'" @click="fnSave('U')" class="btnStyle3 black font14 ml10" data-toggle="modal" activity="SAVE">수정</a>
						<a v-if="this.save_status === 'U'" @click="fnSave('D')" class="btnStyle3 black font14 ml10" data-toggle="modal" activity="SAVE">삭제</a>
						<a @click="fnClose" ref="closeBtn" class="btnStyle3 white font14 ml10" data-dismiss="modal">취소</a>						
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import api from '../service/api'
import confirm from "@/modules/commonUtil/service/confirm"
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  name: 'bpChanMo',
  data() {
    return {
      apiKey    : "",
      moNumber  : "",
      moType    : "",
      pjtAllNo  : "",
      webhookUrl : "",
      sts : "",
      isConnWebhookUrl : false    // 웹훅url connection 여부
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
      this.webhookUrl = newVal.webhookUrl;
      this.isConnWebhookUrl = "";
      /* if( newVal.pjtAllNo != 'ALL' ){
        this.pjtAllNo = 'NO';
      } else {
        this.pjtAllNo = newVal.pjtAllNo;
      } */
      
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
    fnSave(sts){
      this.sts = sts;

      if(!this.$gfnCommonUtils.isEmpty(this.webhookUrl) && this.isConnWebhookUrl == false){
        confirm.fnAlert("", "웹훅URL 사용하시려면 '연결 확인' 버튼을 통해 해당 URL이 사용가능한지 확인하셔야합니다.\n연결이 확인된 웹훅URL만 사용 가능합니다.");
        return false;
      }

      eventBus.$on('callbackEventBus', this.fnSaveCallBack);
      confirm.fnConfirm("", "저장하시겠습니까?", "확인");
    },
    fnSaveCallBack(){
      var params = {
        "sts"         : this.sts,
        "apiKey"      : this.apiKey,
        "moNumber"    : this.moNumber,
        "moType"      : this.moType,
        "projectId"   : this.projectId,
        webhookUrl    : this.webhookUrl
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
    },
    fnCheckWebhookUrl(){
      if(this.$gfnCommonUtils.isEmpty(this.webhookUrl)){
        confirm.fnAlert("", "사용하려는 웹훅URL을 입력해주세요.");
        return false;
      }
      var params = {
        webhookUrl    : this.webhookUrl
      }
      api.checkWebhookUrl(params).then(response =>{
        var result = response.data;

        if(result.success) {
          this.isConnWebhookUrl = true;
          confirm.fnAlert("", "해당 웹훅 URL은 사용 가능합니다.");
        } else {
          this.isConnWebhookUrl = false;
          confirm.fnAlert("", result.message);
        }
      });
    },
    fnChgConnStatus(){
      this.isConnWebhookUrl = false;
    }
  }
}
</script>