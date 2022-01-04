<template>
    <div>
        <center>
            <p></p><p></p>
            본인인증이 실패하였습니다.<br>
            <table border=1>
                <tr>
                    <td>복호화한 시간</td>
                    <td>{{ sCipherTime }} (YYMMDDHHMMSS)</td>
                </tr>
                <tr>
                    <td>요청 번호</td>
                    <td>{{ sRequestNumber }}</td>
                </tr>      
                <tr>
                    <td>인증수단</td>
                    <td>{{ sAuthType}}</td>
                </tr>
                <tr>
                    <td>본인인증 실패 코드</td>
                    <td>{{ sErrorCode }}</td>
                </tr>
            </table>
            <br><br>        
            {{ sMessage }}
            <br>
        </center>
    </div>
</template>
<script>
import projectApi from '../service/projectApi';
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
    props: {
        EncodeData : String,
        require : true
    },
    data(){
        return {
            sCipherTime : '',
            sRequestNumber : '',
            sAuthType : '',
            sErrorCode : ''
        }
    },
    mounted(){
        this.fnGetNiceCheckFail();
    },
    methods : {
        fnGetNiceCheckFail(){
            var params = {
                EncodeData : this.EncodeData 
            };

            projectApi.getNiceCheckFail(params).then((response) => {
                var result = response.data;
                if(result.success){
                    this.sCipherTime = result.data.sCipherTime;
                    this.sRequestNumber = result.data.sRequestNumber;
                    this.sAuthType = result.data.sAuthType;
                    this.sErrorCode = result.data.sErrorCode;

                     confirm.fnAlert("본인인증 실패", "다시 시도해주시기 바랍니다.");
                     self.close();
                    
                } else {
                    confirm.fnAlert("본인인증 실패", result.message);
					self.close();
                }
            });
        }
    }
}
</script>