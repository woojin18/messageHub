<template>
	<div id="confirmCallback" class="modal fade modalStyle" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:1200px">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h5 class="lc-1">등록 발신번호 상세</h5>
						<hr>
						<div class="mt20 of_h">
							<div class="float-left" style="width:49%">
                브랜드명 : {{this.row_data.brandName}}
							</div>
							<div class="of_h float-right" style="width:49%">
								브랜드ID : {{this.row_data.brandId}}
							</div>
						</div>
            
            <!-- 페이징 카운트 -->
						<PagingCnt :pageInfo.sync="pageInfo" />
            <!-- 페이징 카운트 -->
            <table cellspacing="0" id="list" class="table_skin1 tbl-striped" style="width:100%">
              <thead>
                <th>No.</th>
                <th>발신번호명</th>
                <th>발신번호</th>
                <th>SMSMO<br>사용여부</th>
                <th>등록일</th>
                <th>승인상태</th>
                <th>승인일</th>
              </thead>
              <tbody>
                <tr v-for="(data, index) in callbackItems" :key="index">
                  <td>
                    {{ index + 1 }}
                  </td>
                  <td>
                    {{ data.subTitle }}
                  </td>
                  <td>
                    {{ data.chatbotId }}
                  </td>
                  <td>
                    {{ data.rcsReplyText }}
                  </td>
                  <td>
                    {{ data.regDt }}
                  </td>
                  <td>
                    {{ data.approvalStatus }}
                  </td>
                  <td>
                    {{ data.approvalDt }}
                  </td>
                </tr>
              </tbody>
            </table>
					</div>
          <!-- 페이징 -->
          <Paging :pageInfo.sync="pageInfo" />
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

import Paging from "@/modules/commonUtil/components/bc-paging"
import PagingCnt from "@/modules/commonUtil/components/bc-pagingCnt"

export default {
  components: {
      Paging
    , PagingCnt
  },
  data() {
    return {
      
      // 리스트 
      callbackItems : [],
      pageInfo: {}
    }
  },
  props: {
    visibleCallback: {
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
    visibleCallback: function(newVal, oldVal) {
      if(newVal) {
        jQuery("#confirmCallback").modal("show");
      } else {
        jQuery("#confirmCallback").modal("hide");
      }
    },
    row_data: function(newVal, oldVal) {
      this.fnSearch();
    }
  },
  mounted() {
    this.pageInfo = {
			"pageCnt"   : [10, 30, 50],  //표시할 개수 리스트
			"selPageCnt": 10,          //선택한 표시 개수
			"selPage"   : 1,          //선택한 페이지
			"rowNum"    : 1           //총개수
		};

  },
  methods: {
    // 닫기
    fnClose(){
      this.$emit('update:visibleCallback', false);
    },
    // 조회
    fnSearch(){
      var params = {
          "brandId"    : this.row_data.brandId,
          "pageInfo"    	: this.pageInfo
      };

      rcsApi.selectRcsCallbackList(params).then(response =>{
        var result = response.data;
				if(result.success) {
          this.callbackItems = result.data; 
          this.pageInfo = result.pageInfo;
				}
      });
    }
  }
}
</script>