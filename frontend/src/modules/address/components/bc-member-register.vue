<template>
	<!-- member Modal -->
	<div class="modal fade modalStyle" id="member" tabindex="-1" role="dialog" aria-hidden="true">
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
											<option value="">수신자명</option>
										</select>
										<input type="text" class="inputStyle ml10" style="width:50%">

										<a href="#self" class="btnStyle1 float-right">검색</a>
									</div>
								</div>
							</div>
						</div>

						<div class="row consolMarginTop">
							<div class="col-xs-12">
								<!-- table -->
								<table  class="table_skin1 bt-000 tbl-striped">
									<thead>
										<tr>
										<th class="text-center lc-1">사용자명</th>
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
										<tr>
										<td class="text-center">김장수</td>
										<td class="text-center">uplus@test.net</td>
										<td class="text-center end">010-****-2613</td>
										</tr>
									</tbody>
								</table>
								<!-- //table -->
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
//import userApi from '../service/addressApi.js'
//import tokenSvc from '@/common/token-service';
//import confirm from "@/modules/commonUtil/service/confirm";
</script>