<!-- provisionView2 Modal -->
<template>
    <div>
	<div class="modal fade modalStyle" id="provisionView2" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog width640">
			<div class="modal-content">
				<div class="modal-body pd60">
					<div>
						<h5 class="lc-1">개인정보 수집 및 이용 동의</h5>
						<hr>
						<div class="provisionWrap">
							<div v-html="contents"></div>
						</div>
					</div>
					<div class="text-center mt40">
						<a href="#self" @click.prevent="closePriPop" class="btnStyle3 white font14" data-dismiss="modal">취소</a>
					</div>
				</div>
			</div>
		</div>
	</div>
    </div>
</template>
<!-- //provisionView2 Modal -->
<script>
import signUpApi from "@/modules/signUp/service/api"

export default {
  props: {
      popReset : Number
  },
  data() {
    return {
      contents : ""
    }
  },
  watch: {
    popReset() {
		var vm = this;
		var params = {
			"srcTermsCd" : "PRI"
		}
		// 현재 계약서 
		signUpApi.selectUseTerms(params).then(function(response) { 
			var result = response.data;
			if(result.success){
				var resultData = result.data;
				vm.contents = resultData.useTerms.termsContent;
			}
		});
    }
  },

  methods: {
      closePriPop() {
        jQuery("#provisionView2").modal('hide');
      }
  }
}
</script>