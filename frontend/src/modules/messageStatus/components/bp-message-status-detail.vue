<template>
<div v-if="layerView" class="layerPopup">
<!-- <div v-if="layerView" class="layerPopup" @click.self="fnCloseLayer"> -->
  <!-- content -->
 <!-- Message Modal -->
	<!-- <div class="modal fade modalStyle" id="Message" tabindex="-1" role="dialog" aria-hidden="true"> -->
		<div class="modal-dialog" style="width:740px">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>메시지 상세</h2>
						<hr>
					
						<div class="row row-no-margin">
							<div class="col-xs-6">
								<div class="phoneWrap">
									<img src="/se2/images/phoneMockup1.svg" alt="프리 템플릿">
									<div class="phoneTextWrap">
										<div class="phoneText1">
											<p class="color3">{{body}}</p>
										</div>
									</div>
								</div>
							</div>
							
							<div class="col-xs-6 of_h">
								<div class="of_h">
									<h5 style="width:34%" class="float-left ml20 color000">발송타입</h5>
									<h5 style="width:47%" class="float-right ml20 color4 word-break-all">{{senderTypeNm}}</h5>
								</div>
								<div class="of_h">
									<h5 style="width:34%" class="float-left ml20 color000">태그</h5>
									<h5 style="width:47%" class="float-right ml20 color4 word-break-all">{{campaignId}}</h5>
								</div>
								<div class="of_h">
									<h5 style="width:34%" class="float-left ml20 color000">수신전화번호</h5>
									<h5 style="width:47%" class="float-right ml20 color4 word-break-all">{{phoneNumber}}</h5>
								</div>
								<div class="of_h">
									<h5 style="width:34%" class="float-left ml20 color000">결과</h5>
									<h5 style="width:47%" class="float-right ml20 color4 word-break-all">{{gwResultNm}}</h5>
								</div>
								<div class="of_h">
									<h5 style="width:34%" class="float-left ml20 color000">발송일시</h5>
									<h5 style="width:47%" class="float-right ml20 color4 word-break-all">{{regDt}}</h5>
								</div>						
							</div>
						</div>	
						<div class="text-center mt20">
							<a href="#self" class="btnStyle1 backBlack" @click.self="fnCloseLayer" title="닫기">닫기</a>	
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
      body : ''
    }
  },
 // mounted() {
 //   this.fnSetMessageStatusDetail();
 // },
  watch: {
    layerView: function(newVal) {
        // LayerView가 true를 반환해 팝업이 출력됫을 시에만 값을 세팅하여 수정 팝업을 만든다.
        if(newVal) {
        
            console.log("this.detailMsgKey : "+this.detailMsgKey);
            var params = {
            "msgKey": this.detailMsgKey
            }

             messageStatusApi.selectMessageStatusDetail(params).then(response=> {
                var result = response.data.data[0];

                //var title         = result.title;
                var body          = result.body;
                var senderTypeNm  = result.senderTypeNm;
                var campaignId    = result.campaignId;
                var phoneNumber   = result.phoneNumber;
                var gwResultNm    = result.gwResultNm;
                var regDt         = result.regDt;

                //this.title        = title;
                this.body         = body;
                this.senderTypeNm = senderTypeNm;
                this.campaignId   = campaignId;
                this.phoneNumber  = phoneNumber;
                this.gwResultNm   = gwResultNm;
                this.regDt        = regDt;
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
