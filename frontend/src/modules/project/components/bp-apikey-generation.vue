<template>
<div class="modal fade modalStyle" id="key" tabindex="-1" role="dialog" aria-hidden="true" style="z-index: 10000;">
		<div class="modal-dialog" style="width: 550px;">
			<div class="modal-content">
				<div class="modal-body">
					<h2>API Key 생성</h2>
					<hr>
					<div class="of_h text-center" style="padding:0 20px">
						<div class="of_h">
							<div class="float-left" style="width:40%"><h5>API KEY 명</h5></div>
							<div class="float-left" style="width:60%">
								<input type="text" class="inputStyle">
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:40%"><h5>API KEY 암호</h5></div>
							<div class="float-left" style="width:60%">
								<input type="password" maxlength="89" class="inputStyle">
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:40%"><h5>API KEY 암호 확인</h5></div>
							<div class="float-left" style="width:60%">
								<input type="password" maxlength="89" class="inputStyle">
							</div>
						</div>
					</div>

					<div class="of_h bgColor_f1 text-center consolMarginTop" style="padding:20px;">
						<div class="of_h">
							<div class="float-left" style="width:40%; margin-top:-7px"><h5>초당발송건수 TPS<br>입력범위 0 ~ 100</h5><p style="font-size: 10px; color: red;">(TPS 증속은 영업팀과<br>별도협의가 필요합니다)</p></div>
							<div class="float-left" style="width:60%">
								<input type="text" class="inputStyle">
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:40%"><h5>라인타입</h5><p style="font-size: 10px; color: red;">(라인타입 변경은 영업팀과<br>별도협의가 필요합니다.)</p></div>
							<div class="float-left" style="width:60%">
								<h5 class="color4">일반</h5>
							</div>
						</div>
					</div>

					<div class="of_h mt10 text-center" style="padding:0 20px">
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:40%"><h5>IP 체크</h5></div>
							<div class="float-left" style="width:60%">
								<input type="radio" name="ipChkYn" value="Y" id="ipChkY"><label for="ipChkY" class="mt5 ml5">사용</label><input type="radio" name="ipChkYn" value="N" id="ipChkN"><label for="ipChkN" class="mt5 ml50">미사용</label>
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:40%"><h5>고객사 접속 IP</h5></div>
							<div class="float-right" style="width:60%">
								<div class="float-right" style="width:15%">
									<a href="#self" class="btnStyle1 borderGray" style="padding:0 10px" title="고객사 접속IP 입력란 삭제"><i class="far fa-minus"></i></a>
								</div>
								<div class="float-right" style="width:70%"><input type="text" class="inputStyle"></div>
								<div class="float-right" style="width:15%">
									<a href="#self" class="btnStyle1 borderGray" style="padding:0 10px" title="고객사 접속IP 입력란 추가"><i class="far fa-plus"></i></a>
								</div>
							</div>
							<div class="float-right mt5" style="width:60%">	
								<div class="float-right" style="width:15%">
									<a href="#self" class="btnStyle1 borderGray" style="padding:0 10px" title="고객사 접속IP 입력란 삭제"><i class="far fa-minus"></i></a>
								</div>							
								<div class="float-right" style="width:70%"><input type="text" class="inputStyle"></div>
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<div class="float-left" style="width:40%"><h5>상태</h5></div>
							<div class="float-left" style="width:60%">
								<input type="radio" name="statusYn" value="USE" id="statsY"><label for="statsY" class="mt5 ml5">사용</label><input type="radio" name="statusYn" value="CANCEL" id="statsN"><label for="statsN" class="mt5 ml50">미사용</label>
							</div>
						</div>
						<div class="clear consolMarginTop">
							<div class="float-left" style="width:40%"><h5>결과수신 여부 <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px;">* 이미지는 jpg, jpeg 형식만 지원합니다. </span></i></h5></div>
							<div class="float-left" style="width:60%">
								<input type="radio" name="rptYn" value="Y" id="rptY"><label for="rptY" class="mt5 ml5">사용</label><input type="radio" name="rptYn" value="N" id="rptN"><label for="rptN" class="mt5 ml50">미사용</label>
							</div>
						</div>
						<div class="clear consolMarginTop">
							<div class="float-left" style="width:40%"><h5>중복체크 <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width: 250px;">* 이미지는 jpg, jpeg 형식만 지원합니다. </span></i></h5></div>
							<div class="float-left" style="width:60%">
								<input type="radio" name="dupChkYn" value="Y" id="dupChkY"><label for="dupChkY" class="mt5 ml5">사용</label><input type="radio" name="dupChkYn" value="N" id="dupChkN"><label for="dupChkN" class="mt5 ml50">미사용</label>
							</div>
						</div>
					</div>	
				
					<div class="text-center mt20">
						<a href="#self" class="btnStyle1 backBlack" data-toggle="modal" data-target="#Register">생성</a>
						<a href="#self" class="btnStyle1 backWhite" data-dismiss="modal">취소</a>						
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