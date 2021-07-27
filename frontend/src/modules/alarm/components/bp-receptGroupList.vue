<template>
	<div class="modal fade modalStyle" id="receptGroupListPopup" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>수신자 검색</h2>
						<hr>
						<p class="color4">좌측에 그룹이 지정되면 우측에 그룹에 속해 있는 수신자가 검색됩니다.</p>
						<div class="row mt10">
							<div class="col-xs-6 consolMarginTop">
								<div class="of_h">
									<h5 class="inline-block" style="width:25%">그룹명</h5>
									<div style="width:75%" class="of_h inline-block float-right">
										<input type="text" class="inputStyle" style="width:100%" title="그룹명 입력란" v-model="params1.srcReceptGroupName" v-on:keyup.enter="fnSearch1">
									</div>
								</div>
								<div class="scroll-y5">
									<table id="list1" class="table_skin1 consolMarginTop" style="width:100%">
										<caption>수신그룹별 수신자수를 제공하는 표</caption>
									<colgroup>
										<col style="width:10%">
										<col style="width:*">
										<col style="width:25%">
									</colgroup>
									<thead>
										<tr>
										<th class="text-center lc-1"><div class="consolCheck ml10"><input type="checkbox" id="chk_all1" class="checkStyle2"><label for="chk_all1"></label></div></th>
										<th class="text-center lc-1">수신그룹</th>
										<th class="text-center lc-1 end">수신자 수</th>
										</tr>
									</thead>
										<tbody>
										<tr v-for="(data, idx) in list1" @click="fnSelect(idx)">
											<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" name="chk1" v-bind:id="'chk1'+idx" v-bind:value="idx" v-if="data.receptGroupId != null" class="checkStyle2"><label v-bind:for="'chk1'+idx"></label></div></td>
											<td v-bind:class="{'bgColor_sky':idx==0}">{{data.receptGroupName}}</td>
											<td v-bind:class="{'bgColor_sky':idx==0}" class="text-center end">{{data.recipientCnt}}</td>
										</tr>
										<tr v-if="list1.length == 0">
										<td class="text-center" colspan="3">검색된 내용이 없습니다.</td>
										</tr>
										</tbody>
									</table>
								</div>
							</div>

							<div class="col-xs-6 consolMarginTop">
								<div class="of_h">
									<h5 class="inline-block" style="width:25%">수신자명</h5>
									<div style="width:75%" class="of_h inline-block float-right">
										<input type="text" class="inputStyle" style="width:100%" title="수신자명 입력란" v-model="params2.srcRecipientName" v-on:keyup.enter="fnSearch2">
									</div>
								</div>
								<div class="scroll-y5">
									<table id="list2" class="table_skin1 consolMarginTop" style="width:100%">
										<caption>수신자명 별 핸드폰번호를 제공하는 표</caption>
									<colgroup>
										<col style="width:10%">
										<col style="width:25%">
										<col style="width:*">
									</colgroup>
									<thead>
										<tr>
											<th class="text-center lc-1"><div class="consolCheck ml10"><input type="checkbox" id="chk_all2" class="checkStyle2"><label for="chk_all2"></label></div></th>
											<th class="text-center lc-1">수신자명</th>
											<th class="text-center lc-1 end">핸드폰 번호</th>
										</tr>
									</thead>
										<tbody>
										<tr v-for="(data, idx) in list2">
											<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" name="chk2" v-bind:id="'chk2'+idx" v-bind:value="idx" class="checkStyle2"><label v-bind:for="'chk2'+idx"></label></div></td>
											<td class="text-left">{{data.recipientName}}</td>
											<td class="end">{{data.hpNumber}}</td>
										</tr>
										<tr v-if="list2.length == 0">
										<td class="text-center" colspan="3">검색된 내용이 없습니다.</td>
										</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="text-center mt40">
						<a @click="fnSave" class="btnStyle1 backBlack" title="선택">선택</a>&nbsp;
						<a @click="fnClose" class="btnStyle1 backWhite" title="닫기">닫기</a>						
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import receptGroupApi from "@/modules/alarm/service/receptGroupApi"
import recipientApi from "@/modules/alarm/service/recipientApi"
import confirm from "@/modules/commonUtil/service/confirm.js"

export default {
  name: 'receptGroupListPopup',
  props: {
    popReset :{
      type : Number  
    }
  },
  components : {
  },
  data() {
    return {
      params1 : {
        srcProjectName : null
      },
	  list1 : [],
	  
      params2: {
        srcReceptGroupId : null,
		srcRecipientName : null
      },
	  list2 : []
    }
  },
  components: {
  },
  watch : {
    popReset() {
      this.fnSearch1()
      this.fnSearch2()
    }
  },
  mounted() {
	this.fnInit()
  },
  methods: {
	fnInit() {
	  jQuery("#chk_all1").click(function(){
		//클릭되었으면
		if(jQuery("#chk_all1").prop("checked")){
		  jQuery("input[name=chk1]").prop("checked",true);
		}else{
	      jQuery("input[name=chk1]").prop("checked",false);
		}
	  })
	  jQuery("#chk_all2").click(function(){
		//클릭되었으면
		if(jQuery("#chk_all2").prop("checked")){
		  jQuery("input[name=chk2]").prop("checked",true);
		}else{
	      jQuery("input[name=chk2]").prop("checked",false);
		}
	  })
	},
    fnSearch1() {
	  jQuery("#chk_all1,:checkbox[name='chk1']").prop("checked", false)
      var params = Object.assign({}, this.params1)
      receptGroupApi.selectReceptGroupList4(params).then(response => {
        var result = response.data
        if(result.success) {
          this.list1 = result.data
		  if (this.list1.length > 0) {
			  this.fnSelect(0)
		  }
        }
      })
    },
    fnSelect(idx) {
	  this.selectedIndex = idx
      this.params2.srcReceptGroupId = this.list1[idx].receptGroupId
      jQuery('#list1 tbody td').removeClass('bgColor_sky')
      jQuery(jQuery('#list1 tbody tr').get(idx)).find('td').addClass('bgColor_sky')
	  this.fnSearch2()
    },
    fnSearch2() {
	  jQuery("#chk_all2,:checkbox[name='chk2']").prop("checked", false)
      var params = Object.assign({}, this.params2)
      recipientApi.selectRecipientList2(params).then(response => {
        var result = response.data
        if(result.success) {
          this.list2 = result.data
        }
      })
    },
	fnSave() {
		var checked1 = jQuery(":checkbox[name='chk1']:checked");
		var checked2 = jQuery(":checkbox[name='chk2']:checked");
		if(checked1.length == 0 && checked2.length == 0){
			confirm.fnAlert( "수신자 검색", "추가하실 수신그룹 또는 수신자를 선택해주세요.");
			return;
		} else {
			var vm = this
			var list1 = []
			checked1.each(function() {
                var idx = jQuery(this).val();
                list1.push(vm.list1[idx])
            })
			var list2 = []
			checked2.each(function() {
                var idx = jQuery(this).val();
                list2.push(vm.list2[idx])
            })
			this.$parent.fnAddReci(list1, list2)
			this.fnClose();
		}
	},
	fnClose() {
        jQuery("#receptGroupListPopup").modal("hide");
	}
  }
}
</script>
