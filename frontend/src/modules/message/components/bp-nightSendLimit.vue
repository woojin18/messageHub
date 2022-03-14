<template>
    <!-- send_fail Modal -->
	<div v-if="nightSendLimitY" class="modalStyle" id="nightSendLimit" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:500px">
			<div class="modal-content">
				<div class="modal-body">
					<h4 class="text-center mt0">야간 메시지 발송 제한으로 {{nightSendSthh}}:{{nightSendStmm}} ~ 다음날 {{nightSendEdhh}}:{{nightSendEdmm}} 까지<br>메시지를 발송 할 수 없습니다.</h4>
					<div class="text-center mt20">
						<a @click="fnCloseLayer" class="btnStyle2 backBlack" data-dismiss="modal" title="확인">확인</a>					
					</div>	
				</div>
			</div>
		</div>
	</div>
</template>

<script>

export default {
  name: "nightSendLimitPopup",
  props: {
    nightSendLimitY: {
        type: Boolean,
        require: true,
    },
    nightSendSthh: {
        type: String,
        require: true,
        default: function() {
            return '';
        }
    },
	nightSendStmm: {
        type: String,
        require: true,
        default: function() {
            return '';
        }
    },
	nightSendEdhh: {
        type: String,
        require: true,
        default: function() {
            return '';
        }
    },
	nightSendEdmm: {
        type: String,
        require: true,
        default: function() {
            return '';
        }
    }
  },
  methods:{
      // 닫기
    fnCloseLayer(){
      this.$emit('update:nightSendLimitY', false);
    },
  }
}
</script>
