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
								<input class="inputStyle" v-model="apikeyName" maxlength="25" style="margin-top: 7px;margin-bottom: 16px; width:182px"/>
							</div>
						</div>
						<div class="of_h mt10">
							<div style="width:52%" class="float-left color000">
                                <h4 class="font-normal" style="margin-top: 12px;margin-bottom: 0px;">API KEY 암호</h4></div>
							<div style="width:48%" class="float-right">
								<input type="password" v-model="apikeyPwd" maxlength="89" class="inputStyle" style="margin-top: 7px;margin-bottom: 16px;width:182px"/>
							</div>
						</div>
						<div class="of_h mt10">
							<div style="width:52%" class="float-left color000">
                                <h4 class="font-normal" style="margin-top: 12px;margin-bottom: 0px;">API KEY 암호 확인</h4></div>
							<div style="width:48%" class="float-right">
								<input type="password" v-model="apikeyPwdChk" maxlength="89" class="inputStyle" style="margin-top: 7px;margin-bottom: 16px; width:182px"/>
							</div>
						</div>
                        <div class="of_h bgColor_f1 text-center" style="padding: 5px 0px;">
						<div class="of_h mt10">
							<div style="width:52%" class="float-left color000" >
                                <h4 class="font-normal" style="margin-top: 10px;margin-bottom: 0px;">초당발송건수 TPS <br>입력범위 0 ~ 100</h4>
                                 <font style="font-size: 10px; color: red;">(TPS 증속은 영업팀과 별도협의가 필요합니다)</font>
                                </div>
							<div style="width:48%" class="float-right">
								<input class="inputStyle" v-model="value" @input="e=>value=changeNum(e.target.value)" style="margin-top: 15px; width:182px;"/>
							</div>
						</div>
                        <div class="of_h mt10">
							<div style="width:52%;margin-bottom: 20px;" class="float-left color000"><h4 class="font-normal" style="margin-bottom: 0px; margin-top: 15px;">라인타입</h4>
                             <font style="font-size: 10px; color: red;">(라인타입 변경은 영업팀과 별도협의가 필요합니다.)</font>
                            </div>
							<div style="width:48%" class="float-right">
								<h5 class="color4" style="margin-top: 20px;">일반</h5>
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
						<div v-if="this.ipChkYn == 'Y'" class="of_h mt10" style="margin-bottom: 20px;">
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
							<div style="width:50%" class="float-right">
					        	<input type="radio"  name="dupChkYn" value="Y" id="dupChkY"> <label for="dupChkY" class="mt5 ml5">사용</label>
						        <input type="radio"  name="dupChkYn" value="N" id="dupChkN"> <label for="dupChkN" class="mt5 ml50">미사용</label>
							</div>
						</div>
					</div>	
				
					<div class="text-center mt30">
						<a class="btnStyle3 black font14" data-toggle="modal" data-target="#correction" title="수정" @click="fnApikeyGeneration">생성</a>
						<a @click="fnClose" class="btnStyle3 white font14 ml5" title="닫기">닫기</a>						
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import projectApi from '../service/projectApi'
import confirm from '@/modules/commonUtil/service/confirm';
export default {
  data() {
    return {
        apikeyName   : '',
        apikeyPwd    : '',
        apikeyPwdChk : '',
        ipChkYn      : '',
        ipList       : [''],
		value		 : '',
		projectId : this.$parent.projectId,
    }
  },
  components: {

  },
  mounted() {
  },
  methods: {
    fnClose(){
		this.fnInit()
		jQuery('#apikeyGeneration').modal('hide')
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
	fnInit(){
		this.apikeyName =''
      	this.apikeyPwd = ''
        this.apikeyPwdChk = ''
      	this.ipList = ['']
		this.value = ''
		jQuery('input:radio[name="ipChkYn"]:checked').prop('checked',false)
		jQuery('input:radio[name="statusYn"]:checked').prop('checked',false)
		jQuery('input:radio[name="rptYn"]:checked').prop('checked',false)
		jQuery('input:radio[name="dupChkYn"]:checked').prop('checked',false)
	},
	fnApikeyGeneration(){

		const ipChkYn = jQuery('input:radio[name="ipChkYn"]:checked').val()
		const status = jQuery('input:radio[name="statusYn"]:checked').val()
		const rptYn = jQuery('input:radio[name="rptYn"]:checked').val()
		const dupChkYn = jQuery('input:radio[name="dupChkYn"]:checked').val()
		const tps = parseInt(this.value.replaceAll(',',''))
		const apikeyNameChk = /[~!@#$%^&*()_+|<>?:{}]/;

		if(this.apikeyName == ''){
			alert('API KEY명을 입력해 주세요')
			return
		} 

		if(apikeyNameChk.test(this.apikeyName)){
			alert('API KEY명은 특수문자를 입력할 수 없습니다.')
			return
		} 

		if(this.apikeyPwd == ''){
			alert('API KEY 암호를 입력해 주세요')
			return
		} 

		 if(this.apikeyPwdChk == ''){
			alert('API KEY 암호 확인을 입력해 주세요')
			return
		} 	

		 if(this.apikeyPwd != this.apikeyPwdChk){
			alert('입력하신 API KEY 암호가 일치하지 않습니다')
			return
		} 		

		if(this.value == ''){
			alert('TPS를 입력해 주세요')
			return
		} 	

		if(tps > 100){
			alert('TPS 100을 초과하는 증속은 영업팀과 협의가 필요합니다.')
			return
		} 		
		
		if(ipChkYn == undefined){
			alert('IP 체크를 선택해 주세요')
			return
		} 	


    	const ipformat = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;

			if(ipChkYn == 'Y' && this.ipList != null){
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


		if(ipChkYn == 'Y' && this.ipList.length == 0){
			alert('고객 접속 IP를 추가 하거나 IP 체크를 미사용으로 선택해 주세요')
			return
		} 	

		if(status == undefined){
			alert('상태를 선택해 주세요.')
			return
		} 	

		if(rptYn == undefined){
			alert('결과수신을 선택해 주세요.')
			return
		} 	

		if(dupChkYn == undefined){
			alert('중복체크를 선택해 주세요.')
			return
		} 	

		let params ={
			apikeyName : this.apikeyName,
			apikeyPwd   : this.apikeyPwd,
			tps	   	   : tps,
			ipChkYn    : ipChkYn,
			ipList 	   : ipChkYn == 'Y' ? JSON.stringify(this.ipList): null,
			status     : status,
			rptYn	   : rptYn,
			dupChkYn   : dupChkYn,
			projectId  : this.projectId
		}  
		console.log(params)

		projectApi.saveApikeyManageGenerate(params).then(response =>{
				let result = response.data
				if(result.success) {
					this.$parent.fnApikeyManageList()
					jQuery('#apikeyGeneration').modal('hide')
					confirm.fnAlert('', 'API KEY 생성을 성공하였습니다.')
				this.fnInit()
				} else {
					confirm.fnAlert('', 'API KEY 생성을 실패하였습니다')
				}
        });

	}
 }
}

</script>