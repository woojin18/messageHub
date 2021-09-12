<template>
	<div class="modal modalStyle" id="disRatioPopNew" @click.self="PopClose" tabindex="-1" aria-labelledby="myModalLabel" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<!-- table -->
					<div>
						<h5 class="lc-1">분배정책 상세</h5>
						<hr>
						<div class="mt20 of_h">
							<h4 class="font-normal inline-block" style="width:20%">분배정책명</h4>
							<input type="text" id="DS_NAME" name="DS_NAME" class="inputStyle input3 float-right" style="width:78%">
						</div>
						<div class="mt10 of_h">
							<div class="float-center">
								<table id="RSTab" class="table_skin2 mt10">
									<colgroup>
										<col style="width:20%">
										<col style="width:50%">
										<col style="width:30%">
									</colgroup>
								</table>
							</div>
						</div>
						<div class="mt15">
							<span style="color:red;" id="validationMsg"></span>
						</div>
					</div>
					<div class="text-center mt60">
						<button @click="PopClose" class="btnStyle3 white font14 ml10">닫기</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>
<script>

export default {
	name: 'distributionPopupNew',
	props: {
		smartChCode : {
			type: String,
			require: false
		},
	},
	mounted() {

	},
	methods : {
		PopClose(){
			jQuery("#disRatioPopNew").modal("hide");
		},
	}
}
</script>
