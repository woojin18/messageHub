<!-- provisionView2 Modal -->
<template>
    <div>
	<div class="modal fade modalStyle" data-backdrop="static" id="provisionView2" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog width640">
			<div class="modal-content">
				<div class="modal-body pd60">
					<div>
						<h5 class="lc-1">개인정보 수집 및 이용동의</h5>
						<hr>
						<div class="provisionWrap">
                            <select name="provisionList" class="selectStyle3 font-size14" @change="fnChangePri">
								<option value="">이전 약관 보기</option>
                                <option v-for="item in preVerList" :value="item.termsContent" > {{item.reviseDay}} </option>
							</select>
							<!-- 내용 -->
                            <div v-html="contents"></div>
						</div>
					</div>
					<div class="text-center mt40">
						<a href="#self" class="btnStyle3 white font14" data-dismiss="modal" @click="closePriPop()">닫기</a>						
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
      priFlag : Boolean
  },
  data() {
    return {
      contents : "",
      preVerList : []
    }
  },
  watch: {
    priFlag (newPri) {
        var vm = this;
        // 값이 true로 변경될 시에만 약관을 가져 오도록 처리
        if(newPri) {
            var params = {
                "srcTermsCd" : "PRI"
            }
            // 현재 계약서 
            signUpApi.selectUseTerms(params).then(function(response) { 
                var resultData = response.data.data;
                vm.contents = resultData.useTerms.termsContent;
                vm.preVerList = resultData.preUseTerms;
		    });
        }
    }
  },

  methods: {
      closePriPop() {
          this.$parent.priPopViewClose();
      },
      fnChangePri(onselect) {
          var vm = this;
          var priContent = onselect.target.value;
          if(priContent != "") {
              vm.contents = priContent;
          }
      }
  }
}
</script>