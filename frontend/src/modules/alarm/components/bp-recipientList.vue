<template>
	<div class="modal fade modalStyle" id="recipientListPopup" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>그룹 수신자 추가</h2>
						<hr>
						<div class="of_h">
							<h5 class="inline-block" style="width:20%">수신그룹 명 *</span></h5>
							<div class="of_h float-right" style="width:80%">								
								<div class="float-left" style="width:75%"><input type="text" class="inputStyle" title="수신그룹 명 입력란"></div>
								<div class="float-right" style="width:23%"><a class="btnStyle1 borderGray" style="width:100%" title="검색">검색</a></div>
							</div>
						</div>
						<div class="scroll-y5">
							<table  class="table_skin1 tbl-striped consolMarginTop" style="width:100%">
								<caption>수신자명, 휴대폰번호를 제공하는 표</caption>
							<colgroup>
								<col style="width:10%">
								<col style="width:*">
								<col style="width:30%">
							</colgroup>
							<thead>
								<tr>
								<th class="text-center lc-1"><div class="consolCheck ml10"><input type="checkbox" id="check1" class="checkStyle2" value="check1"><label for="check1"></label></div></th>
								<th class="text-center lc-1">수신자명</th>
								<th class="text-center lc-1 end">휴대폰 번호</th>
								</tr>
							</thead>
								<tbody>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check1" class="checkStyle2" value="check1"><label for="check1"></label></div></td>
									<td class="text-left">홍길동</td>
									<td class="end">010-4123-5678</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check1" class="checkStyle2" value="check1"><label for="check1"></label></div></td>
									<td class="text-left">홍길동</td>
									<td class="end">010-4123-5678</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check1" class="checkStyle2" value="check1"><label for="check1"></label></div></td>
									<td class="text-left">홍길동</td>
									<td class="end">010-4123-5678</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check1" class="checkStyle2" value="check1"><label for="check1"></label></div></td>
									<td class="text-left">홍길동</td>
									<td class="end">010-4123-5678</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check1" class="checkStyle2" value="check1"><label for="check1"></label></div></td>
									<td class="text-left">홍길동</td>
									<td class="end">010-4123-5678</td>
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