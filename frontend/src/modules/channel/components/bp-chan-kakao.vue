<template>
	<div id="regPopup" class="modal fade modalStyle" tabindex="-1" role="dialog" aria-hidden="true" @click="fnClose">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>발신 프로필 추가</h2>
						<hr>
						<div class="consoleCon">
							<div class="of_h">
								<h5 class="inline-block float-left" style="width:25%">플러스 친구 ID *</h5>
								<input type="text" class="inputStyle float-right" style="width:72%">
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block float-left" style="width:25%">관리자 연락처</h5>
								<input type="text" class="inputStyle float-right" style="width:72%">
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block float-left" style="width:25%">사업자 카테고리</h5>
								<div class="float-right" style="width:72%">
									<select name="admin030404_3" class="selectStyle2" style="width:70%">
										<option value="">대분류</option>
									</select>
									<select name="admin030404_4" class="selectStyle2 consolMarginTop" style="width:70%">
										<option value="">중분류</option>
									</select>
									<select name="admin030404_5" class="selectStyle2 consolMarginTop" style="width:70%">
										<option value="">소분류</option>
									</select>
								</div>
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:25%">타 프로젝트 사용여부</h5>
								<div class="inline-block float-right" style="width:72%">
									<input type="radio" name="use" value="Y" id="yes" checked=""> <label for="yes" class="mr30">공용</label>
									<input type="radio" name="use" value="N" id="no"> <label for="no">개별</label>		
								</div>
							</div>
							
						</div>
					
					</div>
					<div class="text-center mt20">
						<a class="btnStyle1 backBlack" data-toggle="modal">토큰요청</a>
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
      jQuery("#regPopup").modal('hide');
    }
  }
}
</script>