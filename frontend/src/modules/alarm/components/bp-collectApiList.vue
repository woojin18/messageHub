<template>
	<div class="modal fade modalStyle" id="collectApiListPopup" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>API 검색</h2>
						<hr>
						<div class="of_h">
							<div class="float-left" style="width:20%">
								<select name="API" class="selectStyle2 width100_" title="프로젝트명 선택란">
									<option value="">프로젝트명</option>
								</select>
							</div>
							<div class="of_h float-right" style="width:78%">
								<div class="float-left" style="width:75%"><input type="text" class="inputStyle" title="프로젝트명 입력란" v-model="params.srcProjectName"></div>
								<div class="float-right" style="width:23%"><a @click="fnSearch" class="btnStyle1 borderGray" style="width:100%" title="검색">검색</a></div>
							</div>
						</div>
						<div class="scroll-y5">
							<table  class="table_skin1 tbl-striped consolMarginTop" style="width:100%">
								<caption>프로젝트명, 선후불, API명, API Key를 제공하는 표</caption>
							<colgroup>
								<col style="width:10%">
								<col style="width:*">
								<col style="width:15%">
								<col style="width:20%">
								<col style="width:25%">
							</colgroup>
							<thead>
								<tr>
								<th class="text-center lc-1"><div class="consolCheck ml10"><input type="checkbox" id="apiChk_all" class="checkStyle2"><label for="apiChk_all"></label></div></th>
								<th class="text-center lc-1">프로젝트 명</th>
								<th class="text-center lc-1">선/후불</th>
								<th class="text-center lc-1">API 명</th>
								<th class="text-center lc-1 end">API Key</th>
								</tr>
							</thead>
								<tbody>
								<tr>
								<tr v-for="(data, idx) in list">
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" name="apiChk" v-bind:id="'apiChk'+idx" v-bind:value="idx" class="checkStyle2"><label v-bind:for="'apiChk'+idx"></label></div></td>
									<td>{{data.projectName}}</td>
									<td class="text-center">{{data.payTypeNm}}</td>
									<td class="text-center">{{data.apiKeyName}}</td>
									<td class="end">{{data.apiKey}}</td>
								</tr>
								<tr v-if="list.length == 0">
								  <td class="text-center" colspan="5">검색된 내용이 없습니다.</td>
								</tr>
								</tbody>
							</table>
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
import alarmApi from "@/modules/alarm/service/alarmApi"
import confirm from "@/modules/commonUtil/service/confirm.js"

export default {
  name: 'collectApiListPopup',
  props: {
    popReset :{
      type : Number  
    }
  },
  components : {
  },
  data() {
    return {
      params: {
        srcProjectName : null
      },
	  list : []
    }
  },
  components: {
  },
  watch : {
    popReset() {
      this.fnSearch()
    }
  },
  mounted() {
	this.fnInit()
  },
  methods: {
	fnInit() {
	  jQuery("#apiChk_all").click(function(){
		//클릭되었으면
		if(jQuery("#apiChk_all").prop("checked")){
		  jQuery("input[name=apiChk]").prop("checked",true);
		}else{
	      jQuery("input[name=apiChk]").prop("checked",false);
		}
	  })
	},
    fnSearch() {
	  jQuery("#apiChk_all,input[name=apiChk]").prop("checked", false)
      var params = Object.assign({}, this.params)
      alarmApi.selectApikeyList(params).then(response => {
        var result = response.data
        if(result.success) {
          this.list = result.data
        }
      })
    },
	fnSave() {
		var checked = jQuery(":checkbox[name='apiChk']:checked");
		if( checked.length == 0 ){
			confirm.fnAlert( "API 검색", "추가하실 API를 선택해주세요.");
			return;
		} else {
			var vm = this
			var list = []
			checked.each(function() {
                var idx = jQuery(this).val();
                list.push(vm.list[idx])
            })
			this.$parent.fnAddApi(list)
			this.fnClose();
		}
	},
	fnClose() {
        jQuery("#collectApiListPopup").modal("hide");
	}
  }
}
</script>
