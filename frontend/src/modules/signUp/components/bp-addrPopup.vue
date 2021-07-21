<template>
<div class="modal fade modalStyle in" id="addrPopup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>우편번호 조회</h2>
						<hr>
						<div>
							<div class="of_h mt10">
                                <div class="float-left" style="width:22%"><h5>도로명</h5></div>
                                <div class="float-left" style="width:78%">
                                    <input type="text" class="inputStyle" placeholder="도로명" style="width:65%" v-model="roadNm" @keypress.enter="fnSearch">
                                </div>
							</div>
                            <div class="of_h mt10">
                                <div class="float-left" style="width:22%"><h5>건물명</h5></div>
                                <div class="float-left" style="width:78%">
                                    <input type="text" class="inputStyle" placeholder="건물명" style="width:65%" v-model="sggBldNm" @keypress.enter="fnSearch">
                                </div>
							</div>
                            <div class="of_h mt10">
                                <div class="float-left" style="width:22%"><h5>건물지번</h5></div>
                                <div class="float-left" style="width:78%">
                                    <input type="text" class="inputStyle" placeholder="건물지번" style="width:65%" v-model="bldRpstNo" @keypress.enter="fnSearch">
									<div class="float-right"><button class="btnStyle1 backLightGray" style="width:100%" @click="fnSearch">검색</button></div>
                                </div>
							</div>
							<p class="mt20">도로명, 건물명, 건물지번으로 조회됩니다.</p>
							<p class="mt20">- 도로명 + 건물명 혹은 도로명 + 건물지번으로 조회해주시기 바랍니다. </p>
							<!-- <p class="mt10">지번 주소를 선택하신 경우, 읍/면/동으로 조회됩니다.</p> -->
							<p class="mt20">도로명을 모르실 경우, 도로명 주소 안내시스템(http://www.juso.go.kr)에서 확인하시<br>기 바랍니다.</p>
							<p class="color4 mt20">주소를 클릭하시면 자동으로 주소가 선택 됩니다.</p>

							<div class="mt10" style="max-height:203px; overflow-y:scroll; border-top: 1px solid #222; border-bottom: 1px solid #222">
								<table class="table_skin1" style="border:none">
									<tbody>
										<tr v-for="(row, index) in data" :key="index" @dblclick="fnSelectAddr(row)">
											<td class="text-left end">
												<p class="color1">{{ row.mailNo }}</p>
												<div class="of_h mt5">
													<span class="inline-block text-center" style="width:9%; font-size: 9px; color: #505050; border: 1px solid #D5D5D5;">도로명</span>
													<span class="float-right color000" style="width:89%">{{ row.roadMailFullAddr }} {{row.roadMailFullAddr2}}</span>
												</div>
												<div class="mt3">
													<span class="inline-block text-center" style="width:9%; font-size: 9px; color: #505050; border: 1px solid #D5D5D5;">지번</span>
													<span class="float-right color000" style="width:89%">{{ row.mailFullAddr }}</span>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
                        <div class="of_h consolMarginTop mt20">
                            <div class="text-center">
                                <button @click="fnCloseLayer" class="btnStyle1 backWhite ml10" title="닫기" data-dismiss="modal">닫기</button>
                            </div>
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
    name : 'addrPopup',
    props : {
        popReset : {
            type : Number,
            require : false
        },
        selAddr : {
            type : Object,
            require : false
        },
    },
    data(){
        return {
			addrType : "R",		// R: 도로명, A : 지번
			roadNm : "",		// 도로명
			sggBldNm : "",		// 건물명
			bldRpstNo : "",		// 건물지번
			town : "",			// 읍/면/동

            data : [],
			mailNo : "",				// 우편번호
			roadMailFullAddr : "",		// 도로명주소
			roadMailFullAddr2 : "",		// 도로명주소2
			mailFullAddr : ""			// 지번주소
        }
    },
    watch : {
        popReset(){
			this.roadNm = "",
			this.sggBldNm = "",
			this.bldRpstNo = "",
			this.data = []
        }
    },
    mounted(){ 

    },
    methods : {
        fnCloseLayer(){
            jQuery("#addrPopup").modal("hide");
        },
		fnSearch(){
			if(this.roadNm == "" || this.roadNm == undefined){
				confirm.fnAlert("", "도로명은 필수 입력값입니다.");
				return false;
			}
			if((this.sggBldNm == "" || this.sggBldNm == undefined)
				&& (this.bldRpstNo == "" || this.bldRpstNo == undefined)){
				confirm.fnAlert("", "건물명 혹은 건물 지번은 필수 입력값입니다.");
				return false;
			}

			var params = {
				roadNm : this.roadNm,			// 도로명
				sggBldNm : this.sggBldNm,		// 건물명
				bldRpstNo : this.bldRpstNo,		// 건물지번
				town : this.town				// 읍 면 동
			};

			signUpApi.selectCustAddr(params).then((response) => {
				var result = response.data;
				if(result.success){
					this.data = result.data;
				} else {
					confirm.fnAlert("", result.message);
				}
			});
		},
		fnSelectAddr(rowData){
			var vm = this;
			vm.$emit("update:selAddr", rowData);
			vm.fnCloseLayer();
		}
    }
}
</script>