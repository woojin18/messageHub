<template>
	<div id="grpMngPopup" class="modal modalStyle" tabindex="-1" role="dialog" aria-hidden="true" >
		<div class="modal-dialog"  style="width:800px; ">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>발신 프로필 그룹 관리</h2>
						<hr>
						<div class="consoleCon">
							<div class="of_h">
								<h5 class="inline-block float-left" style="width:25%">발신 프로필 그룹 ID</h5>
								<input type="text" v-model="grpKey" class="inputStyle float-right" style="width:72%" readonly>
							</div>	
							<div class="of_h mt20">
								<div style="width:48%" class="borderline inline-block">
									<div>
										* 그룹에 추가 가능한 발신 프로필
										<!-- <a @click="fnAddGroupProfile" class="btnStyle1 borderLightGray small float-right">추가</a> -->
										<div class="border-line2 pd10 mt20" >
											<div class="border-line2" style="height:100px; overflow-y:scroll">
												<ul class="color4">
													<li class="bb-ddd pd5" style="height : 25px;">@테스트프로필(23wejru7df6984h)<a class="btnStyle1 borderLightGray small float-right">추가</a></li>
													<li class="bb-ddd pd5">@제이프로필(45dfglfg7df6984h)</li>
													<li class="bb-ddd pd5">@제이프로필(45dfglfg7df6984h)</li>
													<li class="bb-ddd pd5">@제이프로필(45dfglfg7df6984h)</li>
													<li class="bb-ddd pd5">@제이프로필(45dfglfg7df6984h)</li>
													<li class="bb-ddd pd5">@제이프로필(45dfglfg7df6984h)</li>
													<li class="bb-ddd pd5">@제이프로필(45dfglfg7df6984h)</li>
													<li class="bb-ddd pd5">@제이프로필(45dfglfg7df6984h)</li>
													<li class="bb-ddd pd5">@제이프로필(45dfglfg7df6984h)</li>
												</ul>									
											</div>
										</div>
									</div>
								</div>
								<div style="width:48%" class="borderline inline-block float-right">
									<div>
										* 그룹에 추가된 발신 프로필
										<!-- <a @click="fnDeleteGroupProfile" class="btnStyle1 borderLightGray small float-right">제거</a> -->
										<div class="border-line2 pd10 mt20">
											<div class="border-line2" style="height:100px; overflow-y:scroll">
												<ul class="color4">
													<li class="bb-ddd pd5">@testrrr(23wejru7df6984h)</li>
													<li class="bb-ddd pd5">@제이2프로필(45dfglfg7df6984h)</li>
													<li class="bb-ddd pd5">@제이2프로필(45dfglfg7df6984h)</li>
													<li class="bb-ddd pd5">@제이2프로필(45dfglfg7df6984h)</li>
													<li class="bb-ddd pd5">@제이2프로필(45dfglfg7df6984h)</li>
													<li class="bb-ddd pd5">@제이2프로필(45dfglfg7df6984h)</li>
													<li class="bb-ddd pd5">@제이2프로필(45dfglfg7df6984h)</li>
													<li class="bb-ddd pd5">@제이2프로필(45dfglfg7df6984h)</li>
													<li class="bb-ddd pd5">@제이2프로필(45dfglfg7df6984h)</li>
												</ul>									
											</div>
										</div>
									</div>
								</div>
							</div>	
						</div>

					
					</div>
					<div class="text-center mt20">
						<a @click="fnClose" class="btnStyle1 backWhite" data-dismiss="modal">닫기</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import api from '../service/api'

export default {
	data() {
		return {
			grpKey : '',
			addList : [],
			deleteList : []
		}
	},
	props: {
		row_data : {
			type: Object,
			require: false,
		}
	},
	watch: {
		row_data : function(newVal, oldVal) {
			console.log(newVal);
			this.grpKey = newVal.grpKey;
		}
	},
	mounted() {

	},
	methods: {
		// 닫기
		fnClose(){
			jQuery("#grpMngPopup").modal('hide');
		},
		fnGetAddGroupProfileList(){

		},
		fnGetDeleteGroupProfileList(){
			
		},
		fnAddGroupProfile(){

		},
		fnDeleteGroupProfile(){

		}
	}
}
</script>