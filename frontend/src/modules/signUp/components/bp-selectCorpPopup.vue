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
                                        <td>{{ row.corpNum }}</td>
                                        <td>{{ row.ceoNm }}</td>
                                        <td>{{ row.customIdNum }}</td>
                                        <td>{{ row.corpNm }}</td>
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
        }
    },
    data(){
        return {
            index : -1,
            data : [
                { corpNum : '1', ceoNm : '홍길동', customIdNum : '730607-11541', corpNm : '서울지방교육'},
                { corpNum : '2', ceoNm : '김영희', customIdNum : '800608-23544', corpNm : '경기지방교육'},
            ]
        }
    },
    watch : {
        popReset(){
            this.index = -1;
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
            this.$emit("update:selCorp", rowData);
            this.fnCloseLayer();
        },
        fnSelect(){
            var rowData = new Object();
            var td = jQuery("#selCorpTbl tr.selected").children();
            rowData.corpNum    = td.eq(0).text();
            rowData.ceoNm       = td.eq(1).text();
            rowData.customIdNum = td.eq(2).text();
            rowData.corpNm      = td.eq(3).text();
            
            this.$emit("update:selCorp", rowData);
            this.fnCloseLayer();
        }
    }
}
</script>