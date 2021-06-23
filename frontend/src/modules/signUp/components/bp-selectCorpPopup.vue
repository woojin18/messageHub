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
                                    <tr v-for="(row, idx) in data" :key="idx" @click="fnSelectCorpInfo(idx)" :class="index == idx ? 'selected' : ''" @dblclick="fnSelRow(row)">
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
            data : []
        }
    },
    watch : {
        popReset(){
            this.index = -1;
            this.selCorp = {};
            this.data = this.dataList;
        }
    },
    mounted(){ 

    },
    methods : {
        fnCloseLayer(){
            jQuery("#chkCorpPopup").modal("hide");
        },
        fnSelectCorpInfo(index){
            this.index = index;
        },
        fnSelRow(rowData){
            var params = {
                custNo : rowData.custNo
            };

            signUpApi.selectSelCorpCustInfo(params).then(function(response) {
                var result = response.data;
                if(result.success){
                    rowData.custKdCd = result.data.custKdCd;
                    rowData.custAddrZip = result.data.custAddrZip;
                    rowData.woplaceAddress = result.data.custVilgAbvAddr + result.data.custVilgBlwAddr;
                    rowData.wireTel = result.data.homeTel == "" ? result.data.offcTel.trim() : result.data.homeTel.trim();
                }
            });
            this.$emit("update:selCorp", rowData);
            this.fnCloseLayer();
        },
        fnSelect(){
            var rowData = new Object();
            var td = jQuery("#selCorpTbl tr.selected").children();
            rowData.custNo    = td.eq(0).text();
            rowData.custNm    = td.eq(1).text();
            rowData.custrnmNo = td.eq(2).text();
            rowData.bizCompNm      = td.eq(3).text();
            // rowData.bsRegNo     = this.bsRegNo;
            
            this.$emit("update:selCorp", rowData);
            this.fnCloseLayer();
        }
    }
}
</script>