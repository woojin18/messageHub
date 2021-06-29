<template>
	<div id="regPopup" class="modal fade modalStyle" tabindex="-1" role="dialog" aria-hidden="true">
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
									<select class="selectStyle2" style="width:72%" >
										<option v-for="(option, i) in category" v-bind:value="option.code" v-bind:key="i">
											{{ option.name }}
										</option>
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
import api from '../service/api'
import confirm from "@/modules/commonUtil/service/confirm"
import {eventBus} from "@/modules/commonUtil/service/eventBus";


export default {
  name: 'bpChanKakao',
  data() {
    return {
		phoneNumber : '',
		category : []
    }
  },
  mounted() {
    this.fnGetKkoCategory();
  },
  methods: {
    // 닫기
    fnClose(){
      jQuery("#regPopup").modal('hide');
    },
	// API 중복확인 및 상세정보 가져오기
	fnGetKkoCategory(){
		var params = {};
		api.getKkoCategory(params).then(response =>{
			var result = response.data;

console.log(result.data);

			/* if( response.success ){ */
				this.category = result.data.data;
			/* } else {
				confirm.fnAlert("", "카테고리 불러오기에 실패했습니다.");
			} */
		});
	},
  }
}
</script>