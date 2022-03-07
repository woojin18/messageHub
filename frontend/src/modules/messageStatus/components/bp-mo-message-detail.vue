<template>
<div v-if="layerView" class="layerPopup">
		<div class="modal-dialog" style="width:370px">
			<div class="modal-content">
				<div class="modal-body" style="overflow-x: hidden;">
					<div>
						<h2>메시지 상세</h2>
						<hr>
					
						<div class="row row-no-margin">
							<div class="">
								<div class="phoneWrap" style="margin-left: 0px;">
									<img src="/se2/images/phoneMockup1.svg" alt="프리 템플릿">
									<div class="phoneTextWrap">
										<div class="phoneText1">
                                            <p>{{title}}</p>
										    <p class="color3">{{msg}}</p>
                                            <p v-for="n in btnCnt" class="text-center mt20" style="color:#69C8FF">{{btnNmArr[n-1]}}</p>
										</div>
									</div>
								</div>
                                <div class="text-center">
                                    <a class="btnStyle1 backBlack" @click.self="fnCloseLayer" title="닫기">닫기</a>	
                                </div>
							</div>
					    </div>											
				    </div>
		    	</div>
		    </div>
        </div>
	<!-- </div> -->
    <!-- //content -->
</div>
</template>

<script>
import messageStatusApi from '../service/messageStatusApi'

export default {
    name: 'UpdateLayer',
  props: {
    layerView: {
      type: Boolean,
      require: true,
      default: false
    },
    title: {
      type: String,
      require: false
    },
    detailMsgKey: {
      type: String,
      require: true
    }
  },
  data() {
    return {
      senderTypeNm : '',
      campaignId : '',
      phoneNumber : '',
      gwResultNm : '',
      regDt : '',
      title : '',
      msg : '',
      btnCnt : 0,
      btnNmArr : []
    }
  },
  watch: {
    layerView: function(newVal) {
        // LayerView가 true를 반환해 팝업이 출력됫을 시에만 값을 세팅하여 수정 팝업을 만든다.
        if(newVal) {
        
            console.log("this.detailMsgKey : "+this.detailMsgKey);
            var params = {
            "moKey": this.detailMsgKey
            }

             messageStatusApi.selectMoMessageDetail(params).then(response=> {

                console.log(response);
                var result = response.data.data;
                
                console.log(result);
                // var title         = result.title;
                // var msg           = result.msg;
                // var btnCnt        = result.btnCnt;
                // var btnNmArr      = result.btnNmArr;
                // var senderTypeNm  = result.senderTypeNm;
                // var campaignId    = result.campaignId;
                // var phoneNumber   = result.phoneNumber;
                // var gwResultNm    = result.gwResultNm;
                // var regDt         = result.regDt;

                // this.title        = title;
                // this.msg          = msg;
                // this.btnCnt       = btnCnt;
                // this.btnNmArr     = btnNmArr;
                // this.senderTypeNm = senderTypeNm;
                // this.campaignId   = campaignId;
                // this.phoneNumber  = phoneNumber;
                // this.gwResultNm   = gwResultNm;
                // this.regDt        = regDt;
            });                    
   
        }
    },
  },
  methods: {
    // 닫기
    fnCloseLayer(){
      this.$emit('update:layerView', false);
    },
    fnBack(){
      this.$router.go(-1);
    }
  }
}
</script>
