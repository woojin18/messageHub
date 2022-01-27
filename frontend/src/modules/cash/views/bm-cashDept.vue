<template>
    <div>	
    <article>
			<div class="contentHeader mb20">
				<h2>부서별 청구</h2>
			</div>

			<!-- 본문 -->
			<div class="bgColor_f1 pd20">
				<ul class="text-left dotList color4">
					<li>부서별 청구는 부서별 빌링을 [예]로 한 프로젝트에서 메시지 발송 시 등록한 부서 코드로 보낼 때 부서코드별로 청구됩니다. </li>
					<li>서로 다른 부서를 같은 청구서로 하시려면 동일한 청구서를 선택하시면 됩니다.</li>
					<li>부서 등록/수정/삭제는 Owner 권한 사용자만 가능합니다.</li>
				</ul>									
			</div>	

			<div class="row">
				<div class="col-xs-12">					
					<div class="of_h inline">
						<div class="float-right mt30">
							<a @click="fnAdd" class="btnStyle2 borderGray">부서 등록</a>
						</div>
					</div>
					
					<div class="row">
						<div class="col-xs-12 consolMarginTop">
							<!-- table -->
							<table  class="table_skin1 bt-000 tbl-striped">
							  <colgroup>
								  <col style="width:15%">
								  <col style="width:15%">
								  <col style="width:15%">
								  <col>
								  <col style="width:15%">
								  <col style="width:15%">
							  </colgroup>
							  <thead>
								<tr>
								  <th class="text-center lc-1">부서명</th>
								  <th class="text-center lc-1">부서코드</th>
								  <th class="text-center lc-1">사용여부</th>
								  <th class="text-center lc-1">청구계정명</th>
								  <th class="text-center lc-1">등록일</th>
								  <th class="text-center lc-1 end">관리</th>
								</tr>
							  </thead>
								<tbody>
								  <tr v-for="(data, idx) in list">
									<td class="text-center">{{ data.deptName }}</td>
									<td class="text-center">{{ data.deptCode }}</td>
									<td class="text-center">{{ data.useYnNm }}</td>
									<td class="text-center">
										<a @click="fnView(idx)" title="청구정보 보기" class="color1">{{ data.billName }}</a>
									</td>
									<td class="text-center">{{ data.regDt }}</td>
									<td class="text-center end"><a @click="fnEdit(idx)" class="btnStyle1 borderLightGray small mr5" >수정</a><a @click="fnDel(idx)" class="btnStyle1 borderLightGray small mr5">삭제</a></td>
								  </tr>	
                  <tr v-if="list.length == 0">
                    <td class="text-center" colspan="6">검색된 내용이 없습니다.</td>
                  </tr>
								</tbody>
							</table>
							<!-- //table -->
						</div>			
					</div>
				</div>			
			</div>
    </article>  
    <deptPop :popupTitle="popupTitle" :rowData="rowData" :popReset="popReset"/>
	<billPopup :popReset="popReset1" :isRead="isRead" :corpInfo="rowData"></billPopup>
    </div>
</template>

<script>
import deptPop from "@/modules/cash/components/bp-deptPop"
import PageLayer from '@/components/PageLayer.vue';
import SelectLayer from '@/components/SelectLayer.vue';
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import cashApi from "@/modules/cash/service/api"
import billPopup from "@/modules/acHome/components/bp-bill"
import tokenSvc from '@/common/token-service';

export default {
  data() {
    return {
	  roleCd : '',
      popupTitle: '등록',
      list: [],
      rowData: {},
      popReset: 0,
	  popReset1 : 0,				// 팝업 초기화할 num
	  isRead : true,
      popState: ""
    }
  },
  components: {
    deptPop,
    SelectLayer,
    PageLayer,
    billPopup
  },
  mounted() {
    this.fnSearch();
  },
  methods: {
    async fnSearch() {
	  this.roleCd = tokenSvc.getToken().principal.role;
      this.rowData = {}
      var params = Object.assign({}, this.params)
      await cashApi.selectDeptList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.list = result.data;
        } else {
          confirm.fnAlert("", result.message);
        }
      });
    },
    fnAdd() {
		if (this.roleCd != 'OWNER') {
			confirm.fnAlert("", "Owner 권한 사용자만 가능합니다");
			return
		}
        this.popupTitle = '등록'
        this.rowData = {}
        this.popReset = this.popReset + 1
        jQuery("#deptPop").modal("show")
    },
	fnView(idx){
		if (this.roleCd != 'OWNER') {
			confirm.fnAlert("", "Owner 권한 사용자만 가능합니다");
			return
		}
      	this.rowData = this.list[idx]
		this.isRead = true;
		this.popReset1 += 1;
		jQuery("#billPopup").modal("show");
	},
    fnEdit(idx) {
		if (this.roleCd != 'OWNER') {
			confirm.fnAlert("", "Owner 권한 사용자만 가능합니다");
			return
		}
        this.popupTitle = '수정'
        this.rowData = this.list[idx]
        this.rowData.isNew = false
        this.popReset = this.popReset + 1
        jQuery("#deptPop").modal("show")
    },
    fnDel(idx) {
		if (this.roleCd != 'OWNER') {
			confirm.fnAlert("", "Owner 권한 사용자만 가능합니다");
			return
		}
      this.rowData = this.list[idx]
      eventBus.$on('callbackEventBus', this.fnDelete)
      confirm.fnConfirm( "삭제 하시겠습니까?", "", "삭제")
    },
    fnDelete() {
      var params = Object.assign({}, this.rowData)
      cashApi.delDept(params).then(response =>{
				var result = response.data;
      	if(result.success) {
					confirm.fnAlert("삭제 되었습니다.", "");
					this.fnSearch();
				}else{
					confirm.fnAlert(result.message, "");
				}
      });
    }
  }
}
</script>

