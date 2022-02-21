<template>
	<div id="confirmCallback" class="modal modalStyle" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:1200px">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2 class="lc-1">등록 발신번호 상세</h2>
						<hr>
						<div class="mt20 of_h">
							<div class="float-left" style="width:49%">
                브랜드명 : {{this.callback_row_data.brandName}}
							</div>
							<div class="of_h float-right" style="width:49%">
								브랜드ID : {{this.callback_row_data.brandId}}
							</div>
						</div>
            
            <!-- 페이징 카운트 -->
            <div class="float-left">전체 : <span class="color1"><strong>{{totCnt | formatComma}}</strong></span>건
              <SelectLayer @fnSelected="fnSelected" classProps="selectStyle2 width120 ml20"></SelectLayer>
            </div>
            <!-- 페이징 카운트 -->
            <table cellspacing="0" id="list" class="table_skin1 tbl-striped" style="width:100%">
              <thead>
                <th class="text-center lc-1">No.</th>
                <th class="text-center lc-1">발신번호명</th>
                <th class="text-center lc-1">발신번호</th>
                <th class="text-center lc-1">SMSMO<br>사용여부</th>
                <th class="text-center lc-1">등록일</th>
                <th class="text-center lc-1">승인상태</th>
                <th class="text-center lc-1 end">승인일</th>
              </thead>
              <tbody>
                <tr v-for="(data, index) in callbackItems" :key="index">
                  <td class="text-center">
                    {{totCnt-offset-data.rownum+1 | formatComma}}
                  </td>
                  <td class="text-center">
                    {{ data.mainTitle }}
                  </td>
                  <td class="text-center">
                    {{ data.chatbotId | phoneNumAddDash}}
                  </td>
                  <td class="text-center">
                    {{ data.rcsReplyText }}
                  </td>
                  <td class="text-center">
                    {{ data.regDt }}
                  </td>
                  <td class="text-center">
                    {{ data.approvalStatus }}
                  </td>
                  <td class="text-center end">
                    {{ data.approvalDt }}
                  </td>
                </tr>
              </tbody>
            </table>
					</div>
          <!-- 페이징 -->
          <PageLayer @fnClick="fnSearchCallback" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
          <!-- 페이징 -->
					<div class="text-center mt40">
						<a @click="fnClose" class="btnStyle3 black font14" data-toggle="modal">닫기</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import rcsApi from '../service/api'

import SelectLayer from '@/components/SelectLayer.vue';
import PageLayer from '@/components/PageLayer.vue';

export default {
  components: {
		PageLayer,
		SelectLayer
  },
  data() {
    return {
      
      // 리스트 
      callbackItems : [],
			listSize : 10,  // select 박스 value (출력 갯수 이벤트)
			pageNo : 1,  // 현재 페이징 위치
			totCnt : 0,  //전체 리스트 수
			offset : 0, //페이지 시작점
    }
  },
  props: {
    visibleCallback: {
      type: Boolean,
      require: true,
      default: false
    },
    callback_row_data : {
      type: Object,
      require: false,
    }
  },
  watch: {
    visibleCallback: function(newVal, oldVal) {
      /* if(newVal) {
        jQuery("#confirmCallback").modal("show");
      } else {
        jQuery("#confirmCallback").modal("hide");
      } */
    },
    callback_row_data: function(newVal, oldVal) {
      this.callbackItems = [];
      this.fnSearchCallback(1);
    }
  },
  mounted() {
    //this.fnSearchCallback(1);
  },
  methods: {
		// select 박스 선택시 리스트 재출력
		fnSelected(listSize) {
			this.listSize = Number(listSize);
			this.$refs.updatePaging.fnAllDecrease();
		},
    // 닫기
    fnClose(){
      jQuery("#confirmCallback").modal("hide");
    },
    // 조회
    fnSearchCallback(pageNo){
      var params = {
          "brandId"    : this.callback_row_data.brandId,
          "projectId"  : this.callback_row_data.projectId,
          "pageNo"		: (this.$gfnCommonUtils.defaultIfEmpty(pageNo, '1'))*1,
				  "listSize"		: this.listSize
      };

      rcsApi.selectRcsCallbackList(params).then(response =>{
        var result = response.data;
				if(result.success) {
          this.callbackItems = result.data; 
          
					this.totCnt = result.pageInfo.totCnt;
          this.offset = result.pageInfo.offset;
				}
      });
    }
  }
}
</script>