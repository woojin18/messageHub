<template>
	<!-- Register Modal -->
	<div class="modal fade modalStyle in" id="apikeyManageDetail" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h API_delete">
						<h2 v-if="this.update == false" class="lc-1 inline-block">API KEY 상세</h2>
						<h2 v-if="this.update == true" class="lc-1 inline-block">API KEY 수정</h2>
					</div>

					<div class="of_h mt10 text-center" style="padding:20px 0;">
						<div class="of_h">
							<div style="width:52%;" class="float-left color000"><h4 class="font-normal" style="margin-top: 12px;">API KEY 명</h4></div>
							<div style="width:48%" class="float-right">
								<h5 class="color4" style="margin-top: 12px;">{{rowData.apiKeyName}}</h5>
							</div>
						</div>
						<div class="of_h">
							<div style="width:52%;" class="float-left color000"><h4 class="font-normal" style="margin-top: 12px;">API KEY</h4></div>
							<div style="width:48%" class="float-right">
								<h5 class="color4" style="margin-top: 12px">{{rowData.apiKey}}</h5>
							</div>
						</div>
						<div v-if="this.update == true" class="of_h mt10">
							<div style="width:52%" class="float-left color000">
                                <h4 class="font-normal" style="margin-top: 12px;margin-bottom: 0px;">API KEY 암호</h4></div>
							<div style="width:48%" class="float-right">
								<input type="password" v-model="apikeyPwd" maxlength="89" class="inputStyle" style="margin-top: 7px;margin-bottom: 16px;width:131px"/>
								<button @click="fnNewPwd" class="btnStyle1 borderLightGray small mr5" style=" margin-left: 5px; margin-bottom: 18px;">변경</button>
							</div>
						</div>                        
						<div v-if="this.update == true && this.newPwd == true" class="of_h mt10">
							<div style="width:52%" class="float-left color000">
                                <h4 class="font-normal" style="margin-top: 12px;margin-bottom: 0px;">API KEY 새 암호</h4></div>
							<div style="width:48%" class="float-right">
								<input type="password" v-model="apikeyNewPwd" maxlength="89" class="inputStyle" style="margin-top: 7px;margin-bottom: 16px;width:182px"/>
							</div>
						</div>
						<div v-if="this.update == true && this.newPwd == true" class="of_h mt10">
							<div style="width:52%" class="float-left color000">
                                <h4 class="font-normal" style="margin-top: 12px;margin-bottom: 0px;">API KEY 새 암호 확인</h4></div>
							<div style="width:48%" class="float-right">
								<input type="password" v-model="apikeyNewPwdChk" maxlength="89" class="inputStyle" style="margin-top: 7px;margin-bottom: 16px; width:182px"/>
							</div>
						</div>
                        <div class="of_h bgColor_f1 text-center" style="padding: 5px 0px;">
						<div class="of_h mt10">
							<div style="width:52%" class="float-left color000" >
                                <h4 class="font-normal" style="margin-top: 10px;margin-bottom: 0px;">초당발송건수 TPS <br>입력범위 0 ~ 100</h4>
                                 
                                </div>
							<div v-if="this.update == false" style="width:48%" class="float-right">
                                <h5 class="color4" style="margin-top: 10px">{{rowData.tps}}</h5>
								<font v-if="this.rowData.webSenderYn == '미사용'" style="font-size: 10px; color: red;">(TPS 증속은 영업팀과 별도협의가 필요합니다)</font>
							</div>
							<div v-if="this.update == true" style="width:48%" class="float-right">
								<input  class="inputStyle" v-model="value" @input="e=>value=changeNum(e.target.value)" style="margin-top: 15px; width:182px;"/>
								<br>
								<font v-if="this.rowData.webSenderYn == '미사용'" style="font-size: 10px; color: red;">(TPS 증속은 영업팀과 별도협의가 필요합니다)</font>
							</div>


						</div>
                        <div class="of_h mt10" style="margin-bottom: 20px;">
							<div style="width:52%;" class="float-left color000"><h4 class="font-normal" style="margin-bottom: 0px; margin-top: 15px;">라인타입</h4>
                            </div>
							<div style="width:48%" class="float-right">
								<h5 class="color4" style="margin-top: 15px;">{{rowData.lineType}}</h5>
								<font v-if="this.rowData.webSenderYn == '미사용'" style="font-size: 10px; color: red;">(라인타입 변경은 영업팀과 별도협의가 필요합니다.)</font>
							</div>
						</div>
                        </div>
					</div>	

					<div class="of_h text-center">
						<div class="of_h">
							<div style="width:50%" class="float-left color000"><h4 class="font-normal" style="margin-top: 5px;margin-bottom: 16px;">IP 체크</h4></div>
							<div v-if="this.update == false" style="width:50%" class="float-right">
                                <h5  class="color4">{{rowData.ipChkYn}}</h5>
                            </div>
							<div v-if="this.update == true" style="width:50%" class="float-right">
						    	<input  type="radio"  v-model="rowData.ipChkYn" name="ipChkYn" value="사용" id="ipY" @change="fnIpValueChange($event)"> <label for="ipY" class="mt5 ml5">사용</label>
						    	<input  type="radio"  v-model="rowData.ipChkYn" name="ipChkYn" value="미사용" id="ipN" @change="fnIpValueChange($event)"> <label for="ipN" class="mt5 ml50">미사용</label>
							</div>
						</div>
						<div v-if="this.rowData.ipChkYn == '사용' && this.update == false" class="of_h mt10" style="margin-bottom: 20px;">
							<div style="width:50%" class="float-left color000"><h4 class="font-normal" style="margin-top: 6px;margin-bottom: 16px;">고객 접속 IP
							</h4>
							</div>
							<div style="width:50%" class="float-right">
							<div v-for="(ip,index) in ipList" :key="index">
								<h5  class="color4">{{ip}}</h5>		
						    </div>
							</div>
						</div>


						<div v-if="this.rowData.ipChkYn == '사용' && this.update == true" class="of_h mt10" style="margin-bottom: 20px;">
							<div style="width:50%" class="float-left color000"><h4 class="font-normal" style="margin-top: 6px;margin-bottom: 16px;">고객 접속 IP
							<a  @click="fnIpListPlus"><i class="far fa-plus channelBtn ml10" style="margin-bottom: 4px;"></i></a>	
							</h4>
							</div>
							<div style="width:50%" class="float-right">
							<div v-for="(ip,index) in ipList" :key="index">
							<input class="inputStyle" style="margin-top: 3px;width:140px;" v-model="ipList[index]"/>
							<a @click="fnIpListMinus(index)"><i class="far fa-minus channelBtn ml10" style="margin-bottom: 4px;"></i></a>
						    </div>
							</div>
						</div>

						<div class="of_h mt10">
							<div style="width:50%" class="float-left color000"><h4 class="font-normal" style="margin-top: 5px;margin-bottom: 16px;">상태</h4></div>
							<div v-if="this.update == false" style="width:50%" class="float-right">
                                <h5  class="color4">{{rowData.status}}</h5>
                            </div>							
                            <div v-if="this.update == true" style="width:50%" class="float-right">
								<input type="radio" v-model="rowData.status" name="statusYn" value="사용" id="statsYnY"> <label for="statsYnY" class="mt5 ml5">사용</label>
					        	<input type="radio" v-model="rowData.status" name="statusYn" value="미사용" id="statsYnN"> <label for="statsYnN" class="mt5 ml50">미사용</label>
							</div>
						</div>
						<div class="clear consolMarginTop">
							<div style="width:50%" class="float-left color000"><h4 class="font-normal" style="margin-top: 5px;margin-bottom: 16px;">결과수신 여부
                                <i class="fas fa-question-circle toolTip">
									<span class="toolTipText" style="width:270px;">
										* 결과 수신 여부를 사용으로 설정 시 
										<br>  &nbsp; 리포트 처리 결과를 API로 받을 수 있습니다.
									</span>
								</i>       
                            </h4>
                            </div>
                            <div v-if="this.update == false" style="width:50%" class="float-right">
                                <h5  class="color4">{{rowData.rptYn}}</h5>
                            </div>	
							<div v-if="this.update == true" style="width:50%" class="float-right">
					        	<input type="radio" v-model="rowData.rptYn" name="rptYn" value="사용" id="rptYnY"> <label for="rptYnY" class="mt5 ml5">사용</label>
						        <input type="radio" v-model="rowData.rptYn" name="rptYn" value="미사용" id="rptYnN"> <label for="rptYnN" class="mt5 ml50">미사용</label>
							</div>
						</div>
						<div class="clear consolMarginTop">
							<div style="width:50%" class="float-left color000"><h4 class="font-normal" style="margin-top: 5px;margin-bottom: 16px;">중복체크
                                <i class="fas fa-question-circle toolTip">
									<span class="toolTipText" style="width:330px">
						            * 중복체크를 사용으로 설정 시 API로 메시지가 발송 될 때 	
									<br> &nbsp; 1시간 내 'CLIKEY' 가 동일하면 실패로 처리됩니다.
                               		</span>
								</i>    
                                </h4></div>
                           <div v-if="this.update == false" style="width:50%" class="float-right">
                                <h5  class="color4">{{rowData.dupChkYn}}</h5>
                            </div>	
							<div v-if="this.update == true" style="width:50%" class="float-right">
					        	<input type="radio" v-model="rowData.dupChkYn" name="dupChkYn" value="사용" id="dupY"> <label for="dupY" class="mt5 ml5">사용</label>
						        <input type="radio" v-model="rowData.dupChkYn" name="dupChkYn" value="미사용" id="dupN"> <label for="dupN" class="mt5 ml50">미사용</label>
							</div>
						</div>
					</div>	

	                <div v-if="this.rowData.webSenderYn == '사용'" class="text-center mt30">
                            <font style="size: 14px; color: red;"> 자동으로 생성된 '웹 APIKEY 기본'은 수정이 불가능합니다.</font>
                     </div>	

					<div v-if="this.update == false" class="text-center mt30">
						<a v-if="this.rowData.webSenderYn == '미사용'" @click="fnUpdate" class="btnStyle3 white font14" data-toggle="modal" data-target="#correction" title="수정" >수정</a>
						<a href="#self" class="btnStyle3 black font14 ml5" title="닫기">닫기</a>						
					</div>
                    <div v-if="this.update == true" class="text-center mt30">
						<a @click="fnSave" class="btnStyle3 white font14" data-toggle="modal" data-target="#correction" title="저장" >저장</a>
						<a @click="fnCancel" class="btnStyle3 black font14 ml5" title="취소">취소</a>						
					</div>
				</div>
			</div>
		</div>
	</div>
</template>


<script>
import confirm from '@/modules/commonUtil/service/confirm';
import projectApi from '../service/projectApi'
import {eventBus} from "@/modules/commonUtil/service/eventBus"

export default {

props: {
		apiKey: {
			type: String,
			require: true
		},
	},
mounted(){
     
},
data(){
    return{
        rowData         : {},
        apikeyPwd       : '',
        apikeyNewPwd    : '',
        apikeyNewPwdChk : '',
        ipChkYn         : '',
        ipList          : {},
		value		    : '',
		projectId       : this.$parent.projectId,
        update          : false,
		newPwd			: false
    }
},
watch:{
    apiKey(){
        let params = {
            apiKey : this.apiKey,
            projectId : this.$parent.projectId,
        }

        projectApi.selectApikeyManageList(params).then(response =>{
        this.rowData = response.data.data[0];

		if(this.rowData.ipChkYn == '사용' && this.ipList.length == 0){
			this.ipList.push('')
		}
		else if(this.rowData.ipChkYn == '사용' && this.ipList != null){
			this.ipList = JSON.parse(this.rowData.ipList)
		}
		this.value  = this.rowData.tps

        });

    }
},
methods:{
    fnClose(){
        jQuery('#apikeyManageDetail').modal('hide')
    },
    fnIpListPlus(){
		if(this.ipList.length > 9){
			alert("IP 주소는 10개까지 입력 가능합니다.")
			return
		}	
		   	else if(this.rowData.ipChkYn == '사용' && this.ipList == null){
				this.ipList = []
				this.ipList.push('')
			}else{
				this.ipList.push('')
			}
	   },
	   fnIpListMinus(index){
 			 this.ipList.splice(index, 1);
	},
    fnIpValueChange(event){
		const chkValue = event.target.value;
			  if(chkValue == '사용' && this.ipList == null){
				this.ipList = []
				this.ipList.push('')	
			  }     
    },
	changeNum(value){
          return value = this.comma(this.uncomma(value));
    },
	comma(str) {
           str = String(str);
           return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
	},
	uncomma(str) {
         str = String(str);
         return str.replace(/[^\d]+/g, '');
  	},
    fnUpdate(){
        this.update = true
    },
    fnCancel(){
        this.update = false
    },
    fnSave(){
		const tps = parseInt(this.value.replaceAll(',',''))
    	const ipformat = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;

		if(this.apikeyPwd == ''){
			alert("API KEY 암호를 입력해 주세요.")
			return
		}

		if(this.newPwd == true && this.apikeyNewPwd == ''){
				alert("API KEY 새 암호를 입력해 주세요.")
			return		
		}

		if(this.newPwd == true && this.apikeyNewPwdChk == ''){
				alert("API KEY 새 암호 확인을 입력해 주세요.")
			return		
		}

		if(this.newPwd == true && (this.apikeyNewPwd != this.apikeyNewPwdChk)){
				alert("API KEY 새 암호가 일치하지 않습니다.")
			return		
		}

		if(tps > 100){
				alert("TPS 100을 초과하는 증속은 영업팀과 협의가 필요합니다.")
				return
		}
			if(Array.isArray(this.ipList) == null){
				this.ipList = null
			}

			if(this.rowData.ipChkYn == '사용' && this.ipList != null){
				for(let i = 0; i<this.ipList.length;i++){
					if(this.ipList[i] == ''){
						alert("IP 주소를 입력하거나 빈 입력창을 제거해 주시기 바랍니다.")
						return
					}else if(ipformat.test(this.ipList[i]) == false){
						alert("유효한 IP 주소 형식이 아닙니다.")
						return
					}
				}
			}
			if(this.rowData.ipChkYn == '사용' && this.ipList.length == 0){
				alert("고객 접속 IP 추가. 또는 IP 체크를 미사용으로 변경해 주시기 바랍니다.")
				return
			}
		let params = {
				apiPwd 	  : this.apikeyPwd,
				apiNewPwd :	this.newPwd == true ? this.apikeyNewPwd : null,
				cps   	  : this.value,
				ipChkYn	  : this.rowData.ipChkYn  =='사용'? 'Y' : 'N',
				ipList	  : JSON.stringify(this.ipList),
				status	  : this.rowData.status  =='사용'? 'USE' : 'CANCEL',
				rptYn	  : this.rowData.rptYn =='사용'? 'Y' : 'N',
				dupChkYn  : this.rowData.dupChkYn =='사용'? 'Y' : 'N',
				apikey    : this.rowData.apiKey,
				pwdChk	  : this.this.newPwd == true ? 'Y' : 'N'
			}


		console.log(params)	
		let result = {}
        projectApi.checkApiKeyPwd(params).then(response =>{
        result = response.data
		console.log(result)
			if(result.success){
				confirm.fnAlert('', '수정 되었습니다.')
				this.$parent.fnApikeyManageList()
                jQuery('#apikeyManageDetail').modal('hide')
			}else{
			alert(result.message)
			}
		})

		// console.log(result.success)
		// if(result.success == false){
		// 	return
		// }else if(result.success == true){
		// 	projectApi.updateApikeyManage(params).then(response =>{
        //    	 let result = response.data
		// 	 console.log(result)
        //    	 if(result.success) {
		// 		confirm.fnAlert('', '수정 되었습니다.')
		// 		this.$parent.fnApikeyManageList()
        //         jQuery('#apikeyManageDetail').modal('hide')
        //     }else{
		// 			confirm.fnAlert('', '수정을 실패하였습니다.\n')
		// 		}
       	// 	})
		// }



    },
	fnNewPwd(){
		this.newPwd = !this.newPwd
	}

}

}


	

</script>