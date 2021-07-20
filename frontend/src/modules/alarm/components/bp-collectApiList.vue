<template>
	<div class="modal fade modalStyle" id="collectApiListPopup" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>API 검색</h2>
						<hr>
						<div class="of_h">
							<div class="float-left" style="width:20%">
								<select name="API" class="selectStyle2 width100_" title="프로젝트명 선택란">
									<option value="">프로젝트명</option>
								</select>
							</div>
							<div class="of_h float-right" style="width:78%">
								<div class="float-left" style="width:75%"><input type="text" class="inputStyle" title="프로젝트명 입력란"></div>
								<div class="float-right" style="width:23%"><a class="btnStyle1 borderGray" style="width:100%" title="검색">검색</a></div>
							</div>
						</div>
						<div class="scroll-y5">
							<table  class="table_skin1 tbl-striped consolMarginTop" style="width:100%">
								<caption>프로젝트명, 선후불, API명, API Key를 제공하는 표</caption>
							<colgroup>
								<col style="width:10%">
								<col style="width:*">
								<col style="width:15%">
								<col style="width:20%">
								<col style="width:25%">
							</colgroup>
							<thead>
								<tr>
								<th class="text-center lc-1"></th>
								<th class="text-center lc-1">프로젝트 명</th>
								<th class="text-center lc-1">선/후불</th>
								<th class="text-center lc-1">API 명</th>
								<th class="text-center lc-1 end">API Key</th>
								</tr>
							</thead>
								<tbody>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check1" class="checkStyle2" value="check1"><label for="check1"></label></div></td>
									<td>제임스강 프로젝트</td>
									<td class="text-center">선불</td>
									<td class="text-center">구매발송 API</td>
									<td class="end">123551132551</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check2" class="checkStyle2" value="check2"><label for="check2"></label></div></td>
									<td>제임스강 프로젝트</td>
									<td class="text-center">선불</td>
									<td class="text-center">구매발송 API</td>
									<td class="end">123551132551</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check3" class="checkStyle2" value="check3"><label for="check3"></label></div></td>
									<td>제임스강 프로젝트</td>
									<td class="text-center">선불</td>
									<td class="text-center">구매발송 API</td>
									<td class="end">123551132551</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check4" class="checkStyle2" value="check4"><label for="check4"></label></div></td>
									<td>제임스강 프로젝트</td>
									<td class="text-center">선불</td>
									<td class="text-center">구매발송 API</td>
									<td class="end">123551132551</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check5" class="checkStyle2" value="check5"><label for="check5"></label></div></td>
									<td>제임스강 프로젝트</td>
									<td class="text-center">선불</td>
									<td class="text-center">구매발송 API</td>
									<td class="end">123551132551</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check1" class="checkStyle2" value="check1"><label for="check1"></label></div></td>
									<td>제임스강 프로젝트</td>
									<td class="text-center">선불</td>
									<td class="text-center">구매발송 API</td>
									<td class="end">123551132551</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check2" class="checkStyle2" value="check2"><label for="check2"></label></div></td>
									<td>제임스강 프로젝트</td>
									<td class="text-center">선불</td>
									<td class="text-center">구매발송 API</td>
									<td class="end">123551132551</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check3" class="checkStyle2" value="check3"><label for="check3"></label></div></td>
									<td>제임스강 프로젝트</td>
									<td class="text-center">선불</td>
									<td class="text-center">구매발송 API</td>
									<td class="end">123551132551</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check4" class="checkStyle2" value="check4"><label for="check4"></label></div></td>
									<td>제임스강 프로젝트</td>
									<td class="text-center">선불</td>
									<td class="text-center">구매발송 API</td>
									<td class="end">123551132551</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check5" class="checkStyle2" value="check5"><label for="check5"></label></div></td>
									<td>제임스강 프로젝트</td>
									<td class="text-center">선불</td>
									<td class="text-center">구매발송 API</td>
									<td class="end">123551132551</td>
								</tr>
								</tbody>
							</table>
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