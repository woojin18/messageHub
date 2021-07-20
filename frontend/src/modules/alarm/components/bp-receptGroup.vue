<template>
	<div class="modal fade modalStyle" id="receptGroupPopup" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>알람수신그룹 등록</h2>
						<hr>
						<div class="of_h">
							<h5 class="inline-block" style="width:20%">수신그룹 명 *</span></h5>
							<div class="of_h float-right" style="width:80%">								
								<div class="float-left" style="width:75%"><input type="text" class="inputStyle" title="수신그룹 명 입력란"></div>
								<div class="float-right" style="width:23%"><a class="btnStyle1 borderGray" style="width:100%" title="검색">검색</a></div>
							</div>
						</div>
						<div class="of_h consolMarginTop">
							<h5 class="inline-block" style="width:20%">수신그룹 명 *</span></h5>
							<div class="of_h float-right" style="width:80%">	
								<input type="radio" name="use" value="Y" id="yes" checked=""> <label for="yes" class="payment mt10 mr30">사용</label>
								<input type="radio" name="use" value="N" id="no"> <label for="no" class="payment mt10">미사용</label>		
							</div>
						</div>
					</div>
					<div class="text-center mt40">
						<a class="btnStyle1 backBlack" data-toggle="modal" title="저장">저장</a>&nbsp;
						<a class="btnStyle1 backWhite" data-dismiss="modal" title="닫기">닫기</a>						
					</div>
				</div>
			</div>
		</div>
	</div>
</template>