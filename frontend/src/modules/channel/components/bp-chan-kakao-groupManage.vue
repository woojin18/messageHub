<template>
	<div id="grpAddPopup" class="modal fade modalStyle" tabindex="-1" role="dialog" aria-hidden="true" @click="fnClose">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>발신 프로필 그룹 추가</h2>
						<hr>
						<p class="color6">발신 프로필 그룹을 추가 하는데는 3~4일이 소요됩니다. <br>(영업일 기준, 주말 및 공휴일 제외)</p>
						<div class="consoleCon">							
							<div class="of_h consolMarginTop">
								<h5 class="inline-block float-left" style="width:25%">발신 프로필 그룹 ID *</h5>
								<input type="text" class="inputStyle float-right" style="width:72%">
							</div>
						</div>
					</div>
					<div class="text-center mt20">
						<a class="btnStyle1 backBlack" data-toggle="modal">토큰요청</a>
						<a @click="fnClose" class="btnStyle1 backWhite" data-dismiss="modal">취소</a>						
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
      jQuery("#grpAddPopup").modal('hide');
    }
  }
}
</script>