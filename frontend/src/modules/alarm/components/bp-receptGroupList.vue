<template>
	<div class="modal fade modalStyle" id="receptGroupListPopup" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>수신자 검색</h2>
						<hr>
						<p class="color4">좌측에 그룹이 지정되면 우측에 그룹에 속해 있는 수신자가 검색됩니다.</p>
						<div class="row mt10">
							<div class="col-xs-6 consolMarginTop">
								<div class="of_h">
									<h5 class="inline-block" style="width:25%">그룹명</h5>
									<div style="width:75%" class="of_h inline-block float-right">
										<input type="text" class="inputStyle" style="width:100%" title="그룹명 입력란">
									</div>
								</div>
								<div class="scroll-y5">
									<table  class="table_skin1 consolMarginTop" style="width:100%">
										<caption>수신그룹별 수신자수를 제공하는 표</caption>
									<colgroup>
										<col style="width:10%">
										<col style="width:*">
										<col style="width:25%">
									</colgroup>
									<thead>
										<tr>
										<th class="text-center lc-1"></th>
										<th class="text-center lc-1">수신그룹</th>
										<th class="text-center lc-1 end">수신자 수</th>
										</tr>
									</thead>
										<tbody>
										<tr>										
											<td class="text-center bgColor_sky" colspan="2">수신자 전체</td>
											<td class="end bgColor_sky">5</td>
										</tr>
										<tr>
											<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check1" class="checkStyle2" value="check1"><label for="check1"></label></div></td>
											<td class="text-left">영업부</td>
											<td class="end">5</td>
										</tr>
										<tr>
											<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check1" class="checkStyle2" value="check1"><label for="check1"></label></div></td>
											<td class="text-left">영업부</td>
											<td class="end">5</td>
										</tr>
										<tr>
											<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check1" class="checkStyle2" value="check1"><label for="check1"></label></div></td>
											<td class="text-left">영업부</td>
											<td class="end">5</td>
										</tr>
										<tr>
											<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check1" class="checkStyle2" value="check1"><label for="check1"></label></div></td>
											<td class="text-left">영업부</td>
											<td class="end">5</td>
										</tr>
										</tbody>
									</table>
								</div>
							</div>

							<div class="col-xs-6 consolMarginTop">
								<div class="of_h">
									<h5 class="inline-block" style="width:25%">수신자명</h5>
									<div style="width:75%" class="of_h inline-block float-right">
										<input type="text" class="inputStyle" style="width:100%" title="수신자명 입력란">
									</div>
								</div>
								<div class="scroll-y5">
									<table  class="table_skin1 consolMarginTop" style="width:100%">
										<caption>수신자명 별 핸드폰번호를 제공하는 표</caption>
									<colgroup>
										<col style="width:10%">
										<col style="width:25%">
										<col style="width:*">
									</colgroup>
									<thead>
										<tr>
										<th class="text-center lc-1"></th>
										<th class="text-center lc-1">수신자명</th>
										<th class="text-center lc-1 end">핸드폰 번호</th>
										</tr>
									</thead>
										<tbody>
										<tr>
											<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check1" class="checkStyle2" value="check1"><label for="check1"></label></div></td>
											<td class="text-left">TPM0</td>
											<td class="end">010-4123-5678</td>
										</tr>
										<tr>
											<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check1" class="checkStyle2" value="check1"><label for="check1"></label></div></td>
											<td class="text-left">TPM0</td>
											<td class="end">010-4123-5678</td>
										</tr>
										<tr>
											<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check1" class="checkStyle2" value="check1"><label for="check1"></label></div></td>
											<td class="text-left">TPM0</td>
											<td class="end">010-4123-5678</td>
										</tr>
										<tr>
											<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check1" class="checkStyle2" value="check1"><label for="check1"></label></div></td>
											<td class="text-left">TPM0</td>
											<td class="end">010-4123-5678</td>
										</tr>
										<tr>
											<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check1" class="checkStyle2" value="check1"><label for="check1"></label></div></td>
											<td class="text-left">TPM0</td>
											<td class="end">010-4123-5678</td>
										</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="text-center mt40">
						<a class="btnStyle1 backBlack" data-toggle="modal" title="선택">선택</a>&nbsp;
						<a class="btnStyle1 backWhite" data-dismiss="modal" title="닫기">닫기</a>						
					</div>
				</div>
			</div>
		</div>
	</div>
</template>