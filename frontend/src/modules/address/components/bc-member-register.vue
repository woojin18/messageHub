<template>
	<!-- member Modal -->
	<div class="modal fade modalStyle" id="memberRegisterLayer" tabindex="-1" role="dialog" aria-hidden="true">
	<!--div v-if="layerView" class="layerPopup"-->
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h">
						<h2>구성원 추가</h2>
						<hr>
						<div class="row">
							<div class="col-xs-12">
								<div class="menuBox">
									<div class="of_h">
										<select name="admin0601_3" class="selectStyle2" style="width:30%">
											<option value="cuName">수신자명</option>
											<option value="pushCuid">아이디</option>
											<option value="hpNumber">휴대폰번호</option>
										</select>
										<input type="text" class="inputStyle ml10" style="width:50%">

										<a @click='fnGetRcvrList()' class="btnStyle1 float-right">검색</a>
									</div>
								</div>
							</div>
						</div>

						<div class="row consolMarginTop">
							<div class="col-xs-12">
								<table  class="table_skin1 bt-000 tbl-striped">
									<thead>
										<tr>
										<th class="text-center lc-1">수신자명</th>
										<th class="text-center lc-1">아이디</th>
										<th class="text-center lc-1 end">휴대폰 번호</th>
										</tr>
									</thead>
									<tbody>
										<tr>
										<td class="text-center">홍길동</td>
										<td class="text-center">test@abc.net</td>
										<td class="text-center end">010-****-2612</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>

					</div>
					<div class="text-center mt20">
						<a href="#self" class="btnStyle1 backBlack"  data-toggle="modal" data-target="#Register_id_y">등록</a>
						<a href="#self" class="btnStyle1 backWhite" data-dismiss="modal">취소</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
/*
import addressApi from '../service/addressApi.js'
import confirm from "@/modules/commonUtil/service/confirm";
export default {
	name: 'MemberLayer',
	props: {
		row_data: {
			type: Object,
			require: true
		},
	},
	methods: {
		//주소 카테고리 구성원 조회
		fnSearchAddrCatgMem() {
			let params = {
				addressCategoryId: this.searchCategoryId,
				searchTextType: this.searchTextType,
				searchText: this.searchText,
				pageNo: this.pageNo,
				listSize: this.listSize
			};
			addressApi.selectCmCuList(params).then(response =>{
				const result = response.data;
				this.fnResetChkbox();

				if(result.success) {
					this.cmCuList = Object.assign([], result.data);
					this.totCnt = result.pageInfo.totCnt;
					const vm = this;
					this.cmCuList.forEach(function(cmCuInfo) {
						vm.contsVarNms.forEach(function(varNm) {
							cmCuInfo[varNm] = '';
						});
					});
				} else {
					confirm.fnAlert(this.componentsTitle, result.message);
				}
			});
		},
	},
}
*/
</script>