<template>
    <div>
        <article>
			<div class="contentHeader">
				<h2>알람 > 수신그룹</h2>				
			</div>

			<!-- 본문 -->
			<div class="row">
				<div class="col-xs-12">
					<div class="menuBox">						
						<div class="of_h">
							<h4 class="inline-block" style="width:6%">수신그룹명</h4>
							<input type="text" class="inputStyle" style="width:14%" title="수신그룹명 입력란" v-model="params1.srcReceptGroupName">
							<h4 class="inline-block ml30" style="width:6%">사용여부</h4>
							<select name="admin_sub14" class="selectStyle2" style="width:5%" title="사용여부 선택란" v-model="params1.srcUseYn">
                            <option value="">전체</option>
                            <option value="Y">Y</option>
                            <option value="N">N</option>
							</select>
							<a @click="fnSearch1" class="btnStyle1 float-right" activity="READ" title="검색">검색</a>
						</div>						
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-6 mt30">
					<div class="float-right mb20">						
						<a @click="fnAdd1" class="btnStyle2 backBlack" style="min-width:auto" activity="SAVE" title="수신그룹 등록">수신그룹 등록</a>&nbsp;
						<a @click="fnDel1" class="btnStyle2 borderGray" style="min-width:auto" activity="SAVE" title="수신그룹 삭제">수신그룹 삭제</a>
					</div>
					<div class="scroll-y5" style="width:100%">
						<table class="table_skin1" style="width:100%" id="list1">
							<caption>수신그룹명, 사용여부, 수신자수를 나타내는 표</caption>
							<colgroup>
								<col style="width:10%">
								<col style="width:*">
								<col style="width:20%">
								<col style="width:20%">
								<col>
							</colgroup>
							<thead>
								<tr>
								<th class="text-center lc-1">No</th>
								<th class="text-center lc-1">수신그룹명</th>
								<th class="text-center lc-1">사용여부</th>
								<th class="text-center lc-1 end">수신자 수</th>
								</tr>
							</thead>
							<tbody>
                            	<tr v-for="(data, idx) in list1" @click="fnSelect(idx)">
									<td v-bind:class="{'bgColor_sky':idx==0}" class="text-center">{{list1.length-data.rownum+1 | formatComma}}</td>
									<td v-bind:class="{'bgColor_sky':idx==0}"><a @click="fnEdit1(data)" class="linkStyle">{{data.receptGroupName}}</a></td>
									<td v-bind:class="{'bgColor_sky':idx==0}" class="text-center">{{data.useYn}}</td>
									<td v-bind:class="{'bgColor_sky':idx==0}" class="text-center end">{{data.recipientCnt | formatComma}}</td>
								</tr>	
								<tr v-if="list1.length == 0">
								  <td class="text-center" colspan="4">검색된 내용이 없습니다.</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="col-xs-6 mt30">
					<div class="float-right mb20">						
						<a @click="fnAdd2" class="btnStyle2 backBlack" style="min-width:auto" activity="SAVE" title="그룹 수신자 추가">그룹 수신자  추가</a>&nbsp;
						<a @click="fnDel2" class="btnStyle2 borderGray" style="min-width:auto" activity="SAVE" title="그룹 수신자 삭제">그룹 수신자 삭제</a>
					</div>
					<div class="scroll-y5" style="width:100%">
						<table class="table_skin1" style="width:100%">
							<caption>수신자명, 수신그룹명, 휴대폰번호를 나타내는 표</caption>
							<colgroup>
								<col style="width:10%">
								<col style="width:20%">
								<col style="width:*">
								<col style="width:20%">
								<col>
							</colgroup>
							<thead>
								<tr>
									<th class="text-center lc-1"><div class="consolCheck ml10"><input type="checkbox" id="chk_all" class="checkStyle2"><label for="chk_all"></label></div></th>
									<th class="text-center lc-1">수신자명</th>
									<th class="text-center lc-1">수신그룹명</th>
									<th class="text-center lc-1 end">휴대폰</th>
								</tr>
							</thead>
							<tbody>
                            	<tr v-for="(data, idx) in list2">
									<td><div class="consolCheck ml10"><input type="checkbox" name="chk" v-bind:id="'chk'+idx" class="checkStyle2" v-bind:value="data.recipientId"><label v-bind:for="'chk'+idx" class="trSelect"></label></div></td>
									<td class="text-left">{{data.recipientName}}</td>
									<td class="text-left">{{data.receptGroup}}</td>
									<td class="end">{{data.hpNumber | hpNumberAddDash}}</td>
								</tr>
								<tr v-if="list2.length == 0">
								  <td class="text-center" colspan="4">검색된 내용이 없습니다.</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</article>
      <receptGroupPopup :popupTitle="popupTitle1" :rowData="rowData1" :popReset="popReset1"/>
      <recipientListPopup :rowData="rowData2" :popReset="popReset2"/>
    </div>
</template>

<script>
import receptGroupApi from "@/modules/alarm/service/receptGroupApi"
import groupRecipientApi from "@/modules/alarm/service/groupRecipientApi"
import receptGroupPopup from "@/modules/alarm/components/bp-receptGroup"
import recipientListPopup from "@/modules/alarm/components/bp-recipientList"
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  data() {
    return {
	  selectedIndex: -1,
      params1: {
        srcReceptGroupName : null,
        srcUseYn : ''
      },
      list1: [], 
      rowData1: {},
      popupTitle1: '등록',
      popReset1: 0,

      params2: {
        srcReceptGroupId : null,
		recipientIds: []
      },
      list2: [],
      rowData2: {},
      popReset2: 0
    }
  },
  components: {
    receptGroupPopup,
    recipientListPopup
  },
  mounted() {
    this.fnInit()
  },
  methods: {
    fnInit() {
	  this.fnSearch1()
	  jQuery("#chk_all").click(function(){
		//클릭되었으면
		if(jQuery("#chk_all").prop("checked")){
		  jQuery("input[name=chk]").prop("checked",true);
		}else{
	      jQuery("input[name=chk]").prop("checked",false);
		}
	  })
    },
    async fnSearch1() {
	  this.selectedIndex = -1
      this.params2.srcReceptGroupId = null
	  jQuery("#chk_all").prop("checked", false)
      jQuery('#list1 tbody td').removeClass('bgColor_sky')
      var params = Object.assign({}, this.params1)
      await receptGroupApi.selectReceptGroupList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.list1 = result.data;
		  if (this.list1.length > 0) {
			  this.fnSelect(0)
		  }
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnSelect(idx) {
	  this.selectedIndex = idx
	  jQuery("#chk_all").prop("checked", false)
      this.params2.srcReceptGroupId = this.list1[idx].receptGroupId
      jQuery('#list1 tbody td').removeClass('bgColor_sky')
      jQuery(jQuery('#list1 tbody tr').get(idx)).find('td').addClass('bgColor_sky')
	  this.fnSearch2()
    },
    fnAdd1() {
        this.popupTitle1 = '등록'
        this.rowData1 = {}
        this.popReset1 = this.popReset1 + 1
        jQuery("#receptGroupPopup").modal("show")
    },
    fnEdit1(data) {
        this.popupTitle1 = '수정'
        this.rowData1 = data
        this.popReset1 = this.popReset1 + 1
        jQuery("#receptGroupPopup").modal("show")
    },
	fnDel1() {
		if (this.params2.srcReceptGroupId == null) {
			confirm.fnAlert( "수신그룹", "삭제하실 수신그룹을 선택해주세요.");
			return;
		}
		var vm = this
        this.rowData1 = this.list1[this.selectedIndex]
		var params = Object.assign({}, this.rowData1)
		receptGroupApi.delReceptGroup(params).then(response =>{
			var result = response.data;
			if(result.success) {
			confirm.fnAlert( "수신그룹", "삭제되었습니다.");
			// 리스트 조회
			this.fnSearch1();
			} else {
			confirm.fnAlert("", result.message);
			}
		});
	},
    async fnSearch2() {
      jQuery('#list2 tbody td').removeClass('bgColor_sky')
      var params = Object.assign({}, this.params2)
      await groupRecipientApi.selectGroupRecipientList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.list2 = result.data;
		  this.list1[this.selectedIndex].recipientCnt = this.list2.length
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnAdd2() {
		if (this.params2.srcReceptGroupId == null) {
			confirm.fnAlert( "수신그룹", "수신그룹을 먼저 선택해주세요.");
			return;
		}
        this.rowData2 = Object.assign({}, this.params2)
        this.popReset2 = this.popReset2 + 1
        jQuery("#recipientListPopup").modal("show")
    },
	fnDel2() {
		var checked = jQuery(":checkbox[name='chk']:checked");
		if( checked.length == 0 ){
			confirm.fnAlert( "수신그룹", "삭제하실 수신자를 선택해주세요.");
			return;
		} else {
			var vm = this
			vm.params2.recipientIds = []
			checked.each(function() {
                var value = jQuery(this).val();
                vm.params2.recipientIds.push(value);
            })
			var params = Object.assign({}, this.params2)
			groupRecipientApi.delGroupRecipient(params).then(response =>{
				var result = response.data;
			  if(result.success) {
				confirm.fnAlert( "수신그룹", "삭제되었습니다.");
				// 리스트 조회
				this.fnSearch2();
			  } else {
				confirm.fnAlert("", result.message);
			  }
			});
		}
	}
  }
}
</script>
