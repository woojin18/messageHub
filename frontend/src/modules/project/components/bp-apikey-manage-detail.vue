<template>
	<!-- Register Modal -->
	<div class="modal modalStyle" id="apikeyManageDetail" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
<div class="of_h API_delete">
						<h2 class="lc-1 inline-block">API KEY 상세</h2>
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
								<input type="password" v-model="apikeyPwd" maxlength="89" class="inputStyle" style="margin-top: 7px;margin-bottom: 16px;width:182px"/>
							</div>
						</div>                        
						<div v-if="this.update == true" class="of_h mt10">
							<div style="width:52%" class="float-left color000">
                                <h4 class="font-normal" style="margin-top: 12px;margin-bottom: 0px;">API KEY 새암호</h4></div>
							<div style="width:48%" class="float-right">
								<input type="password" v-model="apikeyNewPwd" maxlength="89" class="inputStyle" style="margin-top: 7px;margin-bottom: 16px;width:182px"/>
							</div>
						</div>
						<div v-if="this.update == true" class="of_h mt10">
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
                                 <font style="font-size: 10px; color: red;">(TPS 증속은 영업팀과 별도협의가 필요합니다)</font>
                                </div>
							<div style="width:48%" class="float-right">
                                <h5 v-if="this.update == false" class="color4" style="margin-top: 23px">{{rowData.tps}}</h5>
								<input v-if="this.update == true" class="inputStyle" v-model="value" @input="e=>value=changeNum(e.target.value)" style="margin-top: 15px; width:182px;"/>
							</div>
						</div>
                        <div class="of_h mt10">
							<div style="width:52%;margin-bottom: 20px;" class="float-left color000"><h4 class="font-normal" style="margin-bottom: 0px; margin-top: 15px;">라인타입</h4>
                             <font style="font-size: 10px; color: red;">(라인타입 변경은 영업팀과 별도협의가 필요합니다.)</font>
                            </div>
							<div style="width:48%" class="float-right">
								<h5 class="color4" style="margin-top: 20px;">{{rowData.lineType}}</h5>
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
						    	<input  type="radio"  name="ipChkYn" value="Y" id="ipChkY" @change="fnIpValueChange($event)"> <label for="ipChkY" class="mt5 ml5">사용</label>
						    	<input  type="radio"  name="ipChkYn" value="N" id="ipChkN" @change="fnIpValueChange($event)"> <label for="ipChkN" class="mt5 ml50">미사용</label>
							</div>
						</div>
						<div v-if="this.ipChkYn == 'Y' && this.update == true" class="of_h mt10" style="margin-bottom: 20px;">
							<div style="width:50%" class="float-left color000"><h4 class="font-normal" style="margin-top: 6px;margin-bottom: 16px;">고객 접속 IP
							<a  @click="fnIpListPlus" class="btnStyle10 inline-block font-size14 urlAdd1_on ml10">추가</a>	
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
                            <div v-if="this.update == false" style="width:50%" class="float-right">
                                <h5  class="color4">{{rowData.rptYn}}</h5>
                            </div>	
							<div v-if="this.update == true" style="width:50%" class="float-right">
					        	<input type="radio" name="rptYn" value="Y" id="rptY"> <label for="rptY" class="mt5 ml5">사용</label>
						        <input type="radio"  name="rptYn" value="N" id="rptN"> <label for="rptN" class="mt5 ml50">미사용</label>
							</div>
						</div>
						<div class="of_h mt10">
							<div style="width:50%" class="float-left color000"><h4 class="font-normal" style="margin-top: 5px;margin-bottom: 16px;">중복체크
                                <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:350px">
						            * 이미지는 jpg, jpeg 형식만 지원합니다.
                                </span></i>    
                                </h4></div>
                           <div v-if="this.update == false" style="width:50%" class="float-right">
                                <h5  class="color4">{{rowData.dupChkYn}}</h5>
                            </div>	
							<div v-if="this.update == true" style="width:50%" class="float-right">
					        	<input type="radio"  name="dupChkYn" value="Y" id="dupChkY"> <label for="dupChkY" class="mt5 ml5">사용</label>
						        <input type="radio"  name="dupChkYn" value="N" id="dupChkN"> <label for="dupChkN" class="mt5 ml50">미사용</label>
							</div>
						</div>
					</div>	
				
					<div v-if="this.update == false" class="text-center mt30">
						<a @click="fnUpdate" class="btnStyle3 black font14" data-toggle="modal" data-target="#correction" title="수정" >수정</a>
						<a @click="fnClose" class="btnStyle3 white font14 ml5" title="닫기">닫기</a>						
					</div>
                    <div v-if="this.update == true" class="text-center mt30">
						<a @click="fnSave" class="btnStyle3 black font14" data-toggle="modal" data-target="#correction" title="저장" >저장</a>
						<a @click="fnCancel" class="btnStyle3 white font14 ml5" title="취소">취소</a>						
					</div>
				</div>
			</div>
		</div>
	</div>
</template>


<script>
import confirm from '@/modules/commonUtil/service/confirm';
import projectApi from '../service/projectApi'
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
        apikeyName      : '',
        apikeyPwd       : '',
        apikeyNewPwd    : '',
        apikeyNewPwdChk : '',
        ipChkYn         : '',
        ipList          : [''],
		value		    : '',
		projectId       : this.$parent.projectId,
        update          : false
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
        console.log(this.rowData)
        });

    }
},
methods:{
    fnClose(){
        jQuery('#apikeyManageDetail').modal('hide')
    },
    fnIpListPlus(){
		if(this.ipList.length > 9){
			alert("IP 주소는 10개까지 입력 할 수 있습니다.")
			return
		}	
				this.ipList.push('')
	   },
	   fnIpListMinus(index){
 			 this.ipList.splice(index, 1);
	},
    fnIpValueChange(event){
		if(jQuery('input:radio[name="ipChkYn"]:checked').val()== 'Y'){
			this.ipChkYn = 'Y'	  
		}else{
			this.ipChkYn = 'N'
		}       
        console.log(this.ipChkYn)
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

    }

}

}


	

</script>