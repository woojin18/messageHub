<template>
	<div class="modal modalStyle fade" id="disRatioPop" @click.self="PopClose" tabindex="-1" aria-labelledby="myModalLabel" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<!-- table -->
					<div>
						<h5 class="lc-1">분배서비스 상세</h5>
						<hr>
						<div class="mt20 of_h">
							<h4 class="font-normal inline-block" style="width:20%">분배서비스명</h4>
							<input type="text" id="DS_NAME" name="DS_NAME" :value="rowData.SMART_CH_NAME" class="inputStyle input3 float-right" style="width:78%">
						</div>
						<div class="mt10 of_h">
							<div class="float-center">
								<table id="RSTab" class="table_skin2 mt10">
								  <colgroup>
									  <col style="width:20%">
									  <col style="width:50%">
									  <col style="width:30%">
								  </colgroup>
								</table>
							</div>
						</div>
						<div class="mt10 of_h">
							<h4 class="font-normal inline-block" style="width:20%">사용여부</h4>
							<div class="float-right" style="width:78%">
								<input type="radio" name="useYN" value="Y" class="cBox" :checked="rowData.USE_YN == 'Y'" id="useY"> <label for="useY" class="payment mr30 font-size14">예</label>
								<input type="radio" name="useYN" value="N" class="cBox" :checked="rowData.USE_YN == 'N'" id="useN"> <label for="useN" class="payment font-size14">아니오</label>
							</div>
						</div>
						<div class="mt15">
							<span style="color:red;" id="validationMsg"></span>
						</div>
					</div>
					<div class="text-center mt60">
						<button @click="ClickSave" class="btnStyle3 white font14" activity="SAVE">저장</button>
						<button @click="PopClose" class="btnStyle3 white font14 ml10">닫기</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>
<script>
import projectApi from '../service/projectApi'
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";


export default {
	name: 'distributionPopup',
	props: {
		insert_or_update : {
			type: String,
			require: false
		},
		corpid : {
			type: String,
			require: false
		},
		projectid : {
			type: String,
			require: false
		},
		rowData : {
			type: Object,
			require: false
		},
		smartChCode : {
			type: String,
			require: false
		},
	},
	mounted() {

	},
	methods : {
		PopClose(){
			jQuery("#disRatioPop").modal("hide");
		},
		ClickSave(){
			if(jQuery("#DS_NAME").val().trim().length == 0){
				jQuery("#validationMsg").text("분배서비스명은 필수입니다.");
				jQuery("#DS_NAME").focus();
				return false;
			}
			eventBus.$on('callbackEventBus', this.goSave);
			confirm.fnConfirm( "저장 하시겠습니까?", "", "저장");

		},
		goSave(){
			/* console.log('>>>> projectid : ' + this.projectid);
			console.log('>>>> smartChCode : ' + this.smartChCode); */
			var params = {
				"CORP_ID" : this.corpid,
				"SMART_CH_NAME" : jQuery('#DS_NAME').val(),
				"SMART_CH_CODE" : this.smartChCode,
				"PROJECT_ID" : this.projectid,
				"USE_YN" : jQuery('input:radio[name="useYN"]:checked').val(),
				"iou" : this.insert_or_update,
				"CH_DIST_INFO" : this.make_CHDISTINFO()
			}
			
			projectApi.saveDisRatio(params).then(response =>{
				var result = response.data;
				if(result.success) {
					confirm.fnAlert("등록 되었습니다.", "");
					// 창닫기
					jQuery("#disRatioPop").modal("hide");
					this.$parent.fnSearch();
					// this.PopClose();
				}else{
					confirm.fnAlert(result.message, "");
				}
			});
		},
		make_CHDISTINFO : function(){
			var rtnStr = '{';
			var tbodyobj = jQuery('#RSTab > tbody');
			for(var i=0; i<tbodyobj.length; i++){
				rtnStr += '"';
				rtnStr += tbodyobj.eq(i).find('tr').eq(0).find('input:text[name=chname]').val();
				rtnStr += '":';
				rtnStr += '[';
				var trobj = tbodyobj.eq(i).find('tr');
				for(var j=1; j<trobj.length; j++){
					var relayStr = trobj.eq(j).find('input:text[name=relay]').val();
					if(relayStr == ""){
						relayStr = "0";
					}
					var ratioStr = trobj.eq(j).find('input:text[name=ratio]').val();
					if(ratioStr == ""){
						ratioStr = "0";
					}
					var vendorYnStr = trobj.eq(j).find('input:hidden[name=vendorYn]').val();
					if(vendorYnStr == "" || vendorYnStr == null){
						vendorYnStr = "N";
					}
					rtnStr += '{';
					rtnStr += '"relayCh":"';
					rtnStr += relayStr;
					rtnStr += '",';
					rtnStr += '"distRatio":';
					rtnStr += ratioStr;
					rtnStr += ',';
					rtnStr += '"vendorYn":"';
					rtnStr += vendorYnStr;
					rtnStr += '"';
					rtnStr += '}';
					if((j+1)<trobj.length){
						rtnStr += ',';
					}
				}
				rtnStr += ']';
				if((i+1)<tbodyobj.length){
					rtnStr += ',';
				}
			}
			rtnStr += '}';

			return rtnStr;
		}
	}
}
</script>
