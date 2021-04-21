<template>
	<div id="layerPopup" class="modal fade modalStyle" tabindex="-1" role="dialog" aria-hidden="true" @click="fnClose">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h5 class="lc-1">MO 수신번호 상세</h5>
						<hr>
						<div class="mt20">
							<div>
								<h4 class="font-normal inline-block width120">MO ID</h4>
								<input type="text" class="inputStyle" style="width:76%">
							</div>
							<div class="mt10">
								<h4 class="font-normal inline-block width120">MO 수신번호</h4>
								<input type="text" class="inputStyle" style="width:76%">
							</div>
							<div class="mt20">
								<h4 class="font-normal inline-block width120">MO 유형</h4>
								<div class="inline-block">
									<input type="radio" name="MO" value="Y" class="cBox" id="SMS" checked=""> <label for="SMS" class="payment mr30 font-size12">SMS MO</label>
									<input type="radio" name="MO" value="N" class="cBox" id="MMS"> <label for="MMS" class="payment font-size12">MMS MO</label>		
								</div>
							</div>
							<div class="mt20">
								<h4 class="font-normal inline-block width120">타 프로젝트<br>사용여부</h4>
								<div class="inline-block">
									<input type="radio" name="use" value="Y" class="cBox" id="yes" checked=""> <label for="yes" class="payment mr30">공용</label>
									<input type="radio" name="use" value="N" class="cBox" id="no"> <label for="no" class="payment">전용</label>		
								</div>
							</div>
							<p class="mt30 lc-1 Modaltext font-size12" style="margin:0"><i class="far fa-info-circle"></i> MO 기본료 부과: 면제<br><i class="far fa-info-circle"></i> 건당 수신료: SMS 7원/LMS 20원/MMS 50원</p>
						</div>
					
					</div>
					<div class="text-center mt40">
						<a href="#self" class="btnStyle3 black font14" data-toggle="modal">토큰요청</a>
						<a href="#self" class="btnStyle3 white font14" data-dismiss="modal">취소</a>						
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import rcsApi from '../service/api'

export default {
  name: 'bpChanRcsMo',
  data() {
    return {
      // select 박스 value (출력 갯수 이벤트)
      selected : 10,
      // 현재 페이징 위치
      pagingCnt : 1,
      // 리스트 
      callbackItems : [],
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
  },
  mounted() {
    var vm = this;
  },
  methods: {
    // 닫기
    fnClose(){
      jQuery("#layerPopup").modal('hide');
    },
    // 조회
    fnSearchRegCallback(){
      var params = {
          "brand_id"    : this.row_data.brandId,
          "rows"        : 15,
          "paging"      : 1
      };

      rcsApi.selectRcsRegTmpltList(params).then(response =>{
        this.callbackItems = response.data.data;
      });
    }
  }
}
</script>