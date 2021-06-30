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
								<input type="text" class="inputStyle float-right" style="width:72%" v-model="kkoChId">
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block float-left" style="width:25%">관리자 연락처</h5>
								<input type="text" class="inputStyle float-right" style="width:72%" v-model="phoneNumber">
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block float-left" style="width:25%">사업자 카테고리</h5>
								<div class="float-right" style="width:72%">
									<select class="selectStyle2" style="width:72%" v-model="categoryId">
										<option v-for="(option, i) in category" v-bind:value="option.code" v-bind:key="i">
											{{ option.name }}
										</option>
									</select>
								</div>
							</div>
							<div class="of_h consolMarginTop">
								<h5 class="inline-block" style="width:25%">타 프로젝트 사용여부</h5>
								<div class="inline-block float-right" style="width:72%">
									<input type="radio" name="use" value="Y" id="yes" v-model="otherProjectYn"> <label for="yes" class="mr30">공용</label>
									<input type="radio" name="use" value="N" id="no"  v-model="otherProjectYn"> <label for="no">개별</label>		
								</div>
							</div>
							
						</div>
					
					</div>
					<div class="text-center mt20">
						<a @click="fnSave" class="btnStyle1 backBlack ml10" data-toggle="modal">저장</a>
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
		kkoChId : '',
		phoneNumber : '',
		category : [],
		categoryId : '',
		otherProjectYn : 'Y'
    }
  },
  props: {
    save_status: {
      type: String,
      require: true,
      default: 'C'
    },
    row_data : {
      type: Object,
      require: false,
    },
    projectId : {
      type : String,
      require : true
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
	// 카카오톡 카테고리 불러오기
	fnGetKkoCategory(){
		var params = {};
		api.getKkoCategory(params).then(response =>{
			var result = response.data.data;

			if( result.success ){
				this.category = result.data;
				this.categoryId = result.data[0].code
			} else {
				confirm.fnAlert("", "카테고리 불러오기에 실패했습니다.");
			}
		});
	},
	fnSave(){
		var params = {
			"sts"			: this.save_status,
			"kkoChId"		: this.kkoChId,
			"phoneNumber"	: this.phoneNumber,
			"categoryId"	: this.categoryId,
			"projectId"		: this.projectId,
		};
		api.saveKkoChForApi(params).then(response =>{
			var result = response.data.data;

			if( result.success ){
				confirm.fnAlert("", "저장에 성공했습니다.");
			} else {
				confirm.fnAlert("", "저장에 실패했습니다.");
			}
		});
	}
  }
}
</script>