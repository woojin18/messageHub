<template>
    <div>
        <center>
            <p></p><p></p>
            본인인증이 완료 되었습니다.<br>
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
                    <td>NICE응답 번호</td>
                    <td>{{ sResponseNumber }}</td>
                </tr>            
                <tr>
                    <td>인증수단</td>
                    <td>{{ sAuthType}}</td>
                </tr>
                <tr>
                    <td>성명</td>
                    <td>{{ sName }}</td>
                </tr>
                <tr>
                    <td>중복가입 확인값(DI)</td>
                    <td>{{ sDupInfo }}</td>
                </tr>
                <tr>
                    <td>연계정보 확인값(CI)</td>
                    <td>{{ sConnInfo }}</td>
                </tr>
                <tr>
                    <td>생년월일(YYYYMMDD)</td>
                    <td>{{ sBirthDate }}</td>
                </tr>
                <tr>
                    <td>성별</td>
                    <td>{{ sGender }}</td>
                </tr>
                        <tr>
                    <td>내/외국인정보</td>
                    <td>{{ sNationalInfo }}</td>
                </tr>
                <tr>
                    <td>휴대폰번호</td>
                    <td>{{ sMobileNo }}</td>
                </tr>
                <tr>
                    <td>통신사</td>
                    <td>{{ sMobileCo }}</td>
                </tr>
                <tr>
                    <td colspan="2">
                        인증 후 결과값은 내부 설정에 따른 값만 리턴받으실 수 있습니다. <br>
                        일부 결과값이 null로 리턴되는 경우 관리담당자 또는 계약부서(02-2122-4615)로 문의바랍니다.
                    </td>
                </tr>
            </table>
            <br><br>        
            {{ sMessage }}
            <br>
        </center>
    </div>
</template>
<script>
import signUpApi from "@/modules/signUp/service/api"
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
            sResponseNumber : '',
            sAuthType : '',
            sName : '',
            sDupInfo : '',
            sConnInfo : '',
            sBirthDate : '',
            sGender : '',
            sNationalInfo : '',
            sMobileNo : '',
            sMobileCo : '',
            sMessage : ''
        }
    },
    mounted(){
        this.fnGetNiceCheckSuccess();
    },
    methods : {
        fnGetNiceCheckSuccess(){
            var params = {
                EncodeData : this.EncodeData 
            };

            signUpApi.getNiceCheckSuccess(params).then((response) => {
                var result = response.data;
                if(result.success){
                    this.sCipherTime = result.data.sCipherTime;
                    this.sRequestNumber = result.data.sRequestNumber;
                    this.sResponseNumber = result.data.sResponseNumber;
                    this.sAuthType = result.data.sAuthType;
                    this.sName = result.data.sName;
                    this.sDupInfo = result.data.sDupInfo;
                    this.sConnInfo= result.data.sConnInfo;
                    this.sBirthDate = reesult.data.sBirthDate;
                    this.sGender = result.data.sGender;
                    this.sNationalInfo = result.data.sNationalInfo;
                    this.sMobileNo = result.data.sMobileNo;
                    this.sMobileCo = result.data.sMobileCo;
                    this.sMessage = result.data.sMessage;
                    
                } else {
                    confirm.fnAlert("", result.message);
                }
            });
        }
    }
}
</script>