<template>
	<div id="confirmTmplt" class="modal modalStyle" tabindex="-1" role="dialog" aria-hidden="true" >
		<div class="modal-dialog" style="width:1200px">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2 class="lc-1">등록 템플릿 상세</h2>
						<hr>
						<div class="mt20 of_h">
							<div class="float-left" style="width:49%">
                브랜드명 : {{this.row_data.brandName}}
							</div>
							<div class="of_h float-right" style="width:49%">
								브랜드ID : {{this.row_data.brandId}}
							</div>
						</div>
            
            <div class="float-left">전체 : <span class="color1"><strong>{{totCnt | formatComma}}</strong></span>건
              <SelectLayer @fnSelected="fnSelected" classProps="selectStyle2 width120 ml20"></SelectLayer>
            </div>
            <table cellspacing="0" id="list" class="table_skin1 tbl-striped" style="width:100%">
              <thead>
                <th class="text-center lc-1">No.</th>
                <th class="text-center lc-1">포멧ID</th>
                <th class="text-center lc-1">템플릿명</th>
                <th class="text-center lc-1">속성</th>
                <th class="text-center lc-1">등록일</th>
                <th class="text-center lc-1">승인상태</th>
                <th class="text-center lc-1">승인요청일</th>
                <th class="text-center lc-1">승인완료일</th>
                <th class="text-center lc-1 end">사용여부</th>
              </thead>
              <tbody>
                <tr v-for="(data, index) in tmpltItems" :key="index">
                  <td class="text-center">
                    {{totCnt-offset-data.rownum+1 | formatComma}}
                  </td>
                  <td class="text-center">
                    {{ data.messagebaseId }}
                  </td>
                  <td class="text-center">
                    {{ data.tmpltName }}
                  </td>
                  <td class="text-center">
                    {{ data.cardType }}
                  </td>
                  <td class="text-center">
                    {{ data.regDt }} 
                  </td>
                  <td class="text-center">
                    {{ data.approvalStatus }} 
                  </td>
                  <td class="text-center">
                    {{ data.updDt }}
                  </td>
                  <td class="text-center">
                    {{ data.approvalDt }}
                  </td>
                  <td class="text-center end">
                    {{ data.useYn }}
                  </td>
                </tr>
              </tbody>
            </table>
          	<PageLayer @fnClick="fnSearchRegTmplt" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
			
					</div>
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
  name: 'bpChanRcsTmpltCnt',
  data() {
    return {
			listSize : 10,  // select 박스 value (출력 갯수 이벤트)
			pageNo : 1,  // 현재 페이징 위치
			totCnt : 0,  //전체 리스트 수
			offset : 0, //페이지 시작점
      tmpltItems : []
    }
  },
  components: {
		PageLayer,
		SelectLayer
  },
  props: {
    visibleTmplt: {
      type: Boolean,
      require: true,
      default: false
    },
    row_data : {
      type: Object,
      require: false,
    }
  },
  watch: {
    visibleTmplt: function(newVal, oldVal) {
      /* if(newVal) {
        jQuery("#confirmTmplt").modal("show");
      } else {
        jQuery("#confirmTmplt").modal("hide");
      } */
    },
    row_data: function(newVal, oldVal) {
      this.tmpltItems = [];
      this.$refs.updatePaging.fnAllDecrease();
    }
  },
  mounted() {
  },
  methods: {
		// select 박스 선택시 리스트 재출력
		fnSelected(listSize) {
			this.listSize = Number(listSize);
			this.$refs.updatePaging.fnAllDecrease();
		},
    // 닫기
    fnClose(){
      jQuery("#confirmTmplt").modal("hide");
    },
    // 조회
    fnSearchRegTmplt(pageNo){
      var params = {
        "brandId"    : this.row_data.brandId,
				"pageNo"		: (this.$gfnCommonUtils.defaultIfEmpty(pageNo, '1'))*1,
				"listSize"		: this.listSize
      };
      rcsApi.selectRcsRegTmpltList(params).then(response2 =>{
        this.tmpltItems = response2.data.data;
        this.totCnt = response2.data.pageInfo.totCnt;
        this.offset = response2.data.pageInfo.offset;
      });
    }
  }
}
</script>