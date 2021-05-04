<template>
	<div id="grpMngPopup" class="modal fade modalStyle" tabindex="-1" role="dialog" aria-hidden="true" @click="fnClose">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>발신 프로필 그룹 관리</h2>
						<hr>
						<div class="consoleCon">
							<div class="of_h">
								<h5 class="inline-block float-left" style="width:25%">발신 프로필 그룹 ID</h5>
								<input type="text" class="inputStyle float-right" style="width:72%">
							</div>	
							<div class="of_h mt20">
								<div style="width:48%" class="borderline inline-block">
									<div>
										* 그룹에 추가 가능한 발신 프로필
										<a class="btnStyle1 borderLightGray small float-right">추가</a>
										<div class="border-line2 pd10 mt20">
											<div class="border-line2" style="height:100px; overflow-y:scroll">
												<ul class="color4">
													<li class="bb-ddd pd5">@테스트프로필(23wejru7df6984h)</li>
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
										<a class="btnStyle1 borderLightGray small float-right">제거</a>
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
import rcsApi from '../service/api'

export default {
  name: 'bpChanKakao',
  data() {
    return {
    }
  },
  mounted() {
    var vm = this;
  },
  methods: {
    // 닫기
    fnClose(){
      jQuery("#grpMngPopup").modal('hide');
    }
  }
}
</script>