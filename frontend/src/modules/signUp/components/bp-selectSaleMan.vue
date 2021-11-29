<template>
    <div class="modal modalStyle in" id="saleManPopup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="of_h">
                        <h2>영업사원</h2>
                        <hr>
                            <div class="of_h mt10">
                                <div class="float-left" style="width:22%"><h5>사원명</h5></div>
                                <div class="float-left" style="width:78%">
                                    <input type="text" class="inputStyle" placeholder="사원명" style="width:70%" v-model="srcSalesManNm" @keypress.enter="fnGetSalesMan">
									<div class="float-right"><button class="btnStyle1 backLightGray" style="width:100%" @click="fnGetSalesMan">검색</button></div>
                                </div>
							</div>
                        <div  class="of_h mt10" style="height:450px; overflow:auto;">
                            <table class="table_skin1 bt-000" id="selCorpTbl">
                                <colgroup>
									<col style="width:30%">
									<col style="width:70%">
								</colgroup>
								<thead>
									<tr>
                                        <th class="text-center lc-1">사원명</th>
                                        <th class="text-center lc-1">전화번호</th>
									</tr>
								</thead>
								<tbody>
                                    <tr v-for="(row, idx) in data" :key="idx" @click="fnSelectSaleManInfo(row,idx)" :class="index == idx ? 'selected' : ''" @dblclick="fnSelRow(row)">
                                        <td>{{ row.codeName1 }}</td>
                                        <td>{{ row.codeVal4 }}</td>
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
import confirm from "@/modules/commonUtil/service/confirm.js";
import signUpApi from "@/modules/signUp/service/api";

export default {
    name : 'selSalesManPopup',
    props : {
        popReset : {
            type : Number,
            require : false
        },
        salesMan : {
            type :String,
            require : false
        },
        selSalesMan : {
            type : Object,
            require : false
        }
    },
    data(){
        return {
            index : -1,
            data : [],
            selRowData : {},
            srcSalesManNm: ""
        }
    },
    watch : {
        popReset(){
            this.index = -1;
            
            this.srcSalesManNm = this.salesMan;
            this.selRowData = {};

            this.fnGetSalesMan();
        }
    },
    methods : {
        fnCloseLayer(){
            jQuery("#saleManPopup").modal("hide");
        },
        fnGetSalesMan(){
            if(this.$gfnCommonUtils.isEmpty(this.srcSalesManNm)){
                confirm.fnAlert("","영업사원의 이름을 입력해주세요.");
                return false;
            }
			var params = {
				codeTypeCd	: "SALES_MAN",
				useYN		: "Y",
				srcSalesManNm : this.srcSalesManNm
			};
			signUpApi.selectSalesManList(params).then(response =>{
                this.data = [];
				var result = response.data;
				if(result.success){
					if(result.data.length > 0){
						this.data = result.data;
					}
				}
			});
        },
        fnSelectSaleManInfo(rowData,idx){
            this.selRowData = rowData;
            this.index = idx;
        },
        fnSelRow(rowData){
            if(rowData.codeVal1 != undefined && rowData.codeVal1 != null && rowData.codeVal1 != ""){
                this.fnGetSelSalesManInfo(rowData);
            } else {
                confirm.fnAlert("","영업사원을 선택해주세요.");
            }
        },
        fnSelect(){
            var rowData = new Object();

             if(this.selRowData.codeVal1 != undefined && this.selRowData.codeVal1 != null && this.selRowData.codeVal1 != ""){
                rowData = this.selRowData;
                this.fnGetSelSalesManInfo(rowData);
            } else {
                confirm.fnAlert("","영업사원을 선택해주세요.");
            }
        },
        fnGetSelSalesManInfo(rowData){
            var vm = this;
            vm.$emit("update:selSalesMan", rowData);
            vm.fnCloseLayer();
        }
    }
}
</script>
<style>
.table_skin1 tr.selected td{background-color:#b0bed9}
</style>