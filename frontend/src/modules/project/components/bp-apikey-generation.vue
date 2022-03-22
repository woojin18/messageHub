<template>
	<div class="modal modalStyle" id="apikeyGeneration" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:550px">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h API_delete">
						<h2 class="lc-1 inline-block">API KEY 생성</h2>
					</div>

					<div class="of_h mt10 text-center" style="padding:20px 0;">
						<div class="of_h">
							<div style="width:52%;" class="float-left color000"><h4 class="font-normal" style="margin-top: 12px;">API KEY 명</h4></div>
							<div style="width:48%" class="float-right">
								<input class="inputStyle" style="margin-top: 7px;margin-bottom: 16px;"/>
							</div>
						</div>
						<div class="of_h mt10">
							<div style="width:52%" class="float-left color000">
                                <h4 class="font-normal" style="margin-top: 12px;margin-bottom: 0px;">API KEY 암호</h4></div>
							<div style="width:48%" class="float-right">
								<input type="password" class="inputStyle" style="margin-top: 7px;margin-bottom: 16px;"/>
							</div>
						</div>
						<div class="of_h mt10">
							<div style="width:52%" class="float-left color000">
                                <h4 class="font-normal" style="margin-top: 12px;margin-bottom: 0px;">API KEY 암호 확인</h4></div>
							<div style="width:48%" class="float-right">
								<input type="password" class="inputStyle" style="margin-top: 7px;margin-bottom: 16px;"/>
							</div>
						</div>
                        <div class="of_h bgColor_f1 text-center" style="padding: 5px 0px;">
						<div class="of_h mt10">
							<div style="width:52%" class="float-left color000" >
                                <h4 class="font-normal" style="margin-top: 10px;margin-bottom: 0px;">초당발송건수 TPS <br>입력범위 0 ~ 100</h4>
                                 <font style="font-size: 10px; color: red;">(TPS 증속은 영업팀과 별도협의가 필요합니다)</font>
                                </div>
							<div style="width:48%" class="float-right">
								<input class="inputStyle" style="margin-top: 15px;"/>
							</div>
						</div>
                        <div class="of_h mt10">
							<div style="width:52%" class="float-left color000"><h4 class="font-normal" style="margin-bottom: 0px; margin-top: 15px;">라인타입</h4>
                             <font style="font-size: 10px; color: red;">(라인타입 변경은 영업팀과 별도협의가 필요합니다.)</font>
                            </div>
							<div style="width:48%" class="float-right">
								<input class="inputStyle" style="margin-top: 15px; margin-bottom:23px"/>
							</div>
						</div>
                        </div>
					</div>	

					<div class="of_h text-center">
						<div class="of_h">
							<div style="width:50%" class="float-left color000"><h4 class="font-normal" style="margin-top: 5px;margin-bottom: 16px;">IP 체크</h4></div>
							<div style="width:50%" class="float-right">
						    	<input type="radio"  name="ipChkYn" value="Y" id="ipChkY" @change="fnIpValueChange($event)"> <label for="ipChkY" class="mt5 ml5">사용</label>
						    	<input type="radio"  name="ipChkYn" value="N" id="ipChkN" @change="fnIpValueChange($event)"> <label for="ipChkN" class="mt5 ml50">미사용</label>
							</div>
						</div>
						<div class="of_h mt10">
							<div style="width:50%" class="float-left color000"><h4 class="font-normal" style="margin-top: 0px;margin-bottom: 16px;">고객 접속 IP</h4></div>
							<div style="width:50%" class="float-right">
							<div v-for="(ip,index) in ipList" :key="index">
							<input class="inputStyle mt5" style="margin-top: 15px;" v-model="ipList[index]"/>
							<a @click="fnIpListMinus(index)"><i class="far fa-minus channelBtn ml10" style="margin-bottom: 4px;"></i></a>
						    </div>
							</div>
						</div>
						<div class="of_h mt10">
							<div style="width:50%" class="float-left color000"><h4 class="font-normal" style="margin-top: 5px;margin-bottom: 16px;">상태</h4></div>
							<div style="width:50%" class="float-right">
								<input type="radio"  name="statusYn" value="USE" id="statsY"> <label for="statsY" class="mt5 ml5">사용</label>
					        	<input type="radio"  name="statusYn" value="CANCEL" id="statsN"> <label for="statsN" class="mt5 ml50">미사용</label>
							</div>
						</div>
						<div class="of_h mt10">
							<div style="width:50%" class="float-left color000"><h4 class="font-normal" style="margin-top: 5px;margin-bottom: 16px;">결과수신 여부
                                <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:350px">
						            * 이미지는 jpg, jpeg 형식만 지원합니다.
                                </span></i>       
                            </h4>
                            </div>
							<div style="width:50%" class="float-right">
					        	<input type="radio"  name="rptYn" value="Y" id="rptY"> <label for="rptY" class="mt5 ml5">사용</label>
						        <input type="radio"  name="rptYn" value="N" id="rptN"> <label for="rptN" class="mt5 ml50">미사용</label>
							</div>
						</div>
						<div class="of_h mt10">
							<div style="width:50%" class="float-left color000"><h4 class="font-normal" style="margin-top: 5px;margin-bottom: 16px;">중복체크
                                <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:350px">
						            * 이미지는 jpg, jpeg 형식만 지원합니다.
                                </span></i>    
                                </h4></div>
							<div style="width:50%" class="float-right">
					        	<input type="radio"  name="dupChkYn" value="사용" id="dupChkY"> <label for="dupChkY" class="mt5 ml5">사용</label>
						        <input type="radio"  name="dupChkYn" value="미사용" id="dupChkN"> <label for="dupChkN" class="mt5 ml50">미사용</label>
							</div>
						</div>
					</div>	
				
					<div class="text-center mt30">
						<a href="#self" class="btnStyle3 black font14" data-toggle="modal" data-target="#correction" title="수정">수정</a>
						<a @click="fnClose" class="btnStyle3 white font14" data-dismiss="modal" title="닫기">닫기</a>						
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>

export default {
  data() {
    return {
        apikeyName  : '',
        apikeyPw    : '',
        apikeyPwChk : '',
        TPS         : '',
        ipChk       : '',
        ipList      : [''],
        status      : '',
        rptYn       : '',
        dupChkYn    : ''
    }
  },
  components: {

  },
  mounted() {
  },
  methods: {
    // 닫기
    fnClose(){
      console.log(this.ipList)
    },fnIpListPlus(){
				this.ipList.push('')
	   },
	   fnIpListMinus(index){
 			 this.ipList.splice(index, 1);
	},
    fnIpValueChange(event){
        var chkValue = event.target.value;
		if(chkValue == '사용' && this.ipList == null){
			this.ipList.push('')			  
		}       
        console.log(this.ipList)
        console.log(jQuery('input:radio[name="ipChkYn"]:checked').val())
    }
 }
}

</script>