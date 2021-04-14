<!-- provisionView1 Modal -->
<template>
    <div>
	<div class="modal fade modalStyle" id="provisionView1" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog width640">
			<div class="modal-content">
				<div class="modal-body pd60">
					<div>
						<h5 class="lc-1">서비스 이용약관</h5>
						<hr>
						<div class="provisionWrap">
							<div v-html="contents"></div>
						</div>
					</div>
					<div class="text-center mt40">
						<a href="#self" class="btnStyle3 white font14" data-dismiss="modal">취소</a>						
					</div>
				</div>
			</div>
		</div>
	</div>
    </div>
</template>
	<!-- //provisionView1 Modal -->
<script>
import signUpApi from "@/modules/signUp/service/api"

export default {
  props: {
      svcFlag : Boolean
  },
  data() {
    return {
      contents : ""
    }
  },
  watch: {
    svcFlag (newSvc) {
        var vm = this;
        // 값이 true로 변경될 시에만 약관을 가져 오도록 처리
        if(newSvc) {
            var params = {
                "srcTermsCd" : "SVC"
            }
            // 현재 계약서 
            signUpApi.selectUseTerms(params).then(function(response) { 
                var resultData = response.data.data;
                vm.contents = resultData.useTerms.termsContent;
		    });
        }
    }
  },

  methods: {
      closePriPop() {
          this.$parent.svcPopViewClose();
      }
  }
}
</script>