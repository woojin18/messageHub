<!-- provisionView1 Modal -->
<template>
    <div>
	<div class="modal fade modalStyle" data-backdrop="static" id="provisionView1" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog width640">
			<div class="modal-content">
				<div class="modal-body pd60">
					<div>
						<h5 class="lc-1">서비스 이용 약관</h5>
						<hr>
						<div class="provisionWrap mt20">
							<select name="provisionList" class="selectStyle3 font-size14" @change="fnChangeSvc">
								<option value="">이전 약관 보기</option>
								<option v-for="(item, index) in preVerList" :key="index" :value="item.termsContent"> {{item.reviseDay}} </option>
							</select>
							<!-- 내용 -->
							<div v-html="contents"></div>
						</div>
					</div>
					<div class="text-center mt40">
						<a href="#self" class="btnStyle3 white font14" data-dismiss="modal" @click.prevent="closeSvcPop()">닫기</a>						
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
      popReset : Number
  },
  data() {
    return {
      contents : "",
      preVerList : []
    }
  },
  watch: {
    popReset() {
    var vm = this;
        var params = {
            "srcTermsCd" : "SVC"
        }
        // 현재 계약서 
        signUpApi.selectUseTerms(params).then(function(response) { 
            var result = response.data;
            if(result.success){
				var resultData = result.data;
				vm.contents = resultData.useTerms.termsContent;
				vm.preVerList = resultData.preUseTerms;
            }
        });
    }
  },

  methods: {
      closeSvcPop() {
          jQuery("#provisionView1").modal('hide');
      },
      fnChangeSvc(onselect) {
          var vm = this;
          var priContent = onselect.target.value;
          if(priContent != "") {
              vm.contents = priContent;
          }
      }
  }
}
</script>