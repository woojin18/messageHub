<template>
    <div class="modal fade modalStyle in" id="chkCorpPopup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="of_h">
                        <h2>사업자 확인</h2>
                        <hr>
                        <div class="tableScrollxy">
                            <table class="table_skin1 bt-000" id="selCorpTbl">
                                <colgroup>
									<col style="width:20%">
									<col style="width:20%">
									<col style="width:30%">
									<col style="width:30%">
								</colgroup>
								<thead>
									<tr>
                                        <th class="text-center lc-1">고객번호</th>
                                        <th class="text-center lc-1">고객명</th>
                                        <th class="text-center lc-1">고객식별번호</th>
                                        <th class="text-center lc-1">사업회사명</th>
									</tr>
								</thead>
								<tbody>
                                    <tr v-for="(row, idx) in data" :key="idx" @click="fnSelectCorpInfo(row)" :class="index == idx ? 'selected' : ''" @dblclick="fnSelRow(row)">
                                        <td>{{ row.custNo }}</td>
                                        <td>{{ row.custNm }}</td>
                                        <td>{{ row.custrnmNo }}</td>
                                        <td>{{ row.bizCompNm }}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>					
                    </div>
                    <div class="of_h consolMarginTop mt20">
                        <div class="text-center">
                            <a @click="fnSelect" class="btnStyle1 backBlack" title="선택" data-toggle="modal">선택</a>
                            <a @click="fnCloseLayer" class="btnStyle1 backWhite ml10" title="닫기" data-dismiss="modal">닫기</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import signUpApi from "@/modules/signUp/service/api"
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
    name : 'chkCorpPopup',
    props : {
        selCorp : {
            type : Object,
            require : false
        },
        popReset : {
            type : Number,
            require : false
        },
        dataList : {
            type : Array,
            require : false
        }
    },
    data(){
        return {
            index : -1,
            data : [],
            selRowData : {}
        }
    },
    watch : {
        popReset(){
            this.index = -1;
            this.data = this.dataList;
            this.selRowData = {};
        }
    },
    methods : {
        fnCloseLayer(){
            jQuery("#chkCorpPopup").modal("hide");
        },
        fnSelectCorpInfo(rowData){
            this.selRowData = rowData;
        },
        fnSelRow(rowData){
            if(rowData.custNo != undefined && rowData.custNo != null && rowData.custNo != ""){
                this.fnGetSelectCustInfo(rowData);
            } else {
                confirm.fnAlert("","고객사를 선택해주세요.");
            }
        },
        fnSelect(){
            var rowData = new Object();

            if(this.selRowData.custNo != undefined && this.selRowData.custNo != null && this.selRowData.custNo != ""){
                rowData = this.selRowData;
                this.fnGetSelectCustInfo(rowData);
            } else {
                confirm.fnAlert("","고객사를 선택해주세요.");
            }
        },
        fnGetSelectCustInfo(rowData){
            var vm = this;

            var params = {
                custNo : rowData.custNo
            };

            signUpApi.selectSelCorpCustInfo(params).then(function(response) {
                var result = response.data;
                if(result.success){
                    rowData.custNm          = result.data.custNm;              // 대표자명
                    rowData.bizCompNm       = result.data.bizCompNm;           // 사업자명
                    rowData.custrnmNo       = result.data.custrnmNo;           // 고객 식별번호
                    rowData.bsRegNo         = result.data.bsRegNo;             // 사업자번호
                    rowData.custKdCd        = result.data.custKdCd;            // 고객 유형
                    rowData.custAddrZip     = result.data.custAddrZip;         // 우편번호
                    rowData.custVilgAbvAddr = result.data.custVilgAbvAddr;     // 주소1
                    rowData.custVilgBlwAddr = result.data.custVilgBlwAddr;     // 상세주소
                    rowData.wireTel         = result.data.offcTel == "" ? result.data.offcTel : result.data.homeTel;     // 유선전화번호

                    vm.$emit("update:selCorp", rowData);
                    vm.fnCloseLayer();
                } else {
                    confirm.fnAlert("", result.message);
                }
            });
        }
    }
}
</script>