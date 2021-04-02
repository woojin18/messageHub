<template>
	<div id="confirmCallback" class="modal fade modalStyle" tabindex="-1" role="dialog" aria-hidden="true" @click="fnClose">
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
            <table cellspacing="0" id="list" class="table_skin1 tbl-striped" style="width:100%">
              <thead>
                <th>No.</th>
                <th>발신번호명</th>
                <th>템플릿명</th>
                <th>발신번호</th>
                <th>SMSMO<br>사용여부</th>
                <th>전시상태</th>
                <th>승인상태</th>
                <th>승인요청일</th>
                <th>승인완료일</th>
              </thead>
              <tbody>
                <tr v-for="(data, index) in items" :key="index">
                  <td>
                    {{ index + 1 }}
                  </td>
                  <td>
                    {{ data.PROJECT_NAME }}
                  </td>
                  <td>
                    {{ data.PROJECT_ID }}
                  </td>
                  <td>
                    {{ data.USE_CH_LIST }}
                  </td>
                  <td>
                    {{ data.REG_DT }}
                  </td>
                  <td>
                    {{ data.PAY_TYPE }}
                  </td>
                  <td>
                    {{ data.PROJECT_MEMBER_CNT }}
                  </td>
                  <td>
                    {{ data.REG_DT }}
                  </td>
                  <td>
                    {{ data.REG_DT }}
                  </td>
                </tr>
              </tbody>
            </table>
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

export default {
  name: 'bpChanRcsTmpltCnt',
  data() {
    return {
      // select 박스 value (출력 갯수 이벤트)
      selected : 10,
      // 현재 페이징 위치
      pagingCnt : 1,
      // 리스트 
      items : [],
      count : 0
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
        $("#confirmCallback").modal("show");
      } else {
        $("#confirmCallback").modal("hide");
      }
    },
    row_data: function(newVal, oldVal) {
    }
  },
  mounted() {
    var vm = this;
  },
  methods: {
    // 닫기
    fnClose(){
      this.$emit('update:visibleCallback', false);
    },
    // 등록, 수정
    fnSearchRegTmplt(){
      if( $("#checkYn").val() === 'N' && this.save_status === 'C' ) {
        alert("중복체크를 먼저 해주세요.");
        return;
      }

      var params = {
          "project_id"      : this.row_data.projectId,
          "brand_id"      : this.row_data.brandId,
      };

      rcsApi.selectRcsRegTmpltList(params).then(response =>{
        var result = response.data;

        if(result.success) {
          alert("저장되었습니다.");
          // 부모창 리스트 조회
          this.$parent.fnSearch();
          // 창닫기
          this.fnClose();
        } else {
          alert(result.message);
        }
      });
    }
  }
}
</script>