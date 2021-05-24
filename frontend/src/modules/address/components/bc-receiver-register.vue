<template>
	<!-- Recipient Modal -->
	<div class="modal fade modalStyle" id="Recipient" tabindex="-1" role="dialog" aria-hidden="true">
	<!--div class="modal-dialog"-->
		<div class="modal-content">
			<div class="modal-body">
				<div class="of_h">
					<h2>수신자 등록</h2>
					<hr>
					<div class="of_h">
						<h5 class="inline-block" style="width:18%">수신자명 *</h5>
						<input type="text" class="inputStyle float-right" style="width:80%">
					</div>
					<div class="of_h consolMarginTop">
						<h5 class="inline-block" style="width:18%">수신자 아이디</h5>
						<input type="text" class="inputStyle float-right" style="width:80%">
					</div>
					<div class="of_h consolMarginTop">
						<h5 class="inline-block" style="width:18%">휴대폰 번호 *</h5>
						<input type="text" class="inputStyle float-right" style="width:80%">
					</div>
					<div class="of_h consolMarginTop">
						<h5 class="inline-block" style="width:18%">사용여부</h5>
						<input type="radio" name="use" value="Y" id="yes" checked=""> <label for="yes" class="mr30">사용</label>
						<input type="radio" name="use" value="N" id="no"> <label for="no">미사용</label>
					</div>
				</div>
				<div class="text-center mt20">
					<a href="#self" class="btnStyle1 backBlack"  data-toggle="modal">등록</a>
					<a href="#self" class="btnStyle1 backWhite" data-dismiss="modal">취소</a>
				</div>
			</div>
		</div>
	</div>
	<!--/div-->
</template>

<script>
//import userApi from '../service/addressApi.js'
//import tokenSvc from '@/common/token-service';
//import confirm from "@/modules/commonUtil/service/confirm";
</script>