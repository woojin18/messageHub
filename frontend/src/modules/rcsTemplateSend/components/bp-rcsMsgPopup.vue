<template>
    <div v-if="rcsMsgPopOpen" @click.self="fnClose" class="modal fade modalStyle" id="Message" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:784px">
			<div class="modal-content">
				<div class="modal-body">
					<div class="of_h">
						<h2>RCS 메시지 보관함</h2>
						<hr>
						<div class="bgColor_f1 pd10">
							<select v-model="srcSelect" name="userConsole_sub020202_2" class="selectStyle2 ml5" style="width:28%">
								<option value="brand">브랜드명</option>
                                <option value="msg">메세지명</option>
							</select>
							<input v-model="srcInput" type="text" class="inputStyle vertical-unset ml5" style="width:34%">
							<a @click.prevent="fnSearch" href="#self" class="btnStyle1 backBlack float-right" style="width:10%" title="검색">검색</a>							
						</div>			
						<div class="float-right consolMarginTop">
							<a href="#self" class="btnStyle1 borderLightGray" title="삭제">삭제</a>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<!-- table -->
								<table class="table_skin1 consolMarginTop" style="width:100%">
								  <caption>브랜드	메시지명, 작성자명, 작성일시을 제공하는 표</caption>
								  <colgroup>
									<col style="width:9%">								
									<col style="width:26%">
									<col style="width:28%">
									<col>
									<col style="width:26%">
								  </colgroup>
								  <thead>
									<tr>
									  <th class="text-center lc-1"><input type="checkbox" id="listCheck_all" class="boardCheckStyle" value="listCheck_all"><label for="listCheck_all"></label></th>
									  <th class="text-center lc-1">브랜드</th>
									  <th class="text-center lc-1">메시지명</th>
									  <th class="text-center lc-1">작성자명</th>
									  <th class="text-center lc-1 end">작성일시</th>
									</tr>
								  </thead>
									<tbody>
									   <tr>
										<td class="text-center"><input type="checkbox" id="listCheck4" class="boardCheckStyle" value="check4"><label for="listCheck4"></label></td>
										<td class="text-left">BR_xiC5B8hdbo(LG유…</td>
										<td class="text-left">국민카드 승인</td>									
										<td class="text-center">홍길동</td>
										<td class="text-center end">2021-01-01 13:44:59</td>
									  </tr>
									   <tr>
										<td class="text-center"><input type="checkbox" id="listCheck3" class="boardCheckStyle" value="check3"><label for="listCheck3"></label></td>
										<td class="text-left">BR_xiC5B8hdbo(웹하드)</td>
										<td class="text-left">TEST</td>									
										<td class="text-center">홍길동</td>
										<td class="text-center end">2021-01-01 13:44:59</td>
									  </tr>
									  <tr>
										<td class="text-center"><input type="checkbox" id="listCheck2" class="boardCheckStyle" value="check2"><label for="listCheck2"></label></td>
										<td class="text-left">BR_xiC5B8hdbo(LG유…</td>
										<td class="text-left">RCS 샘플문자 안내</td>									
										<td class="text-center">홍길동</td>
										<td class="text-center end">2021-01-01 13:44:59</td>
									  </tr>
									  <tr>
										<td class="text-center"><input type="checkbox" id="listCheck1" class="boardCheckStyle" value="check1"><label for="listCheck1"></label></td>
										<td class="text-left">BR_xiC5B8hdbo(웹하드)</td>
										<td class="text-left">LG유플러스 프리템플릿 샘플</td>									
										<td class="text-center">홍길동</td>
										<td class="text-center end">2021-01-01 13:44:59</td>
									  </tr>
									  <tr>
										<td class="text-center"><input type="checkbox" id="listCheck0" class="boardCheckStyle" value="check0"><label for="listCheck0"></label></td>
										<td class="text-center">BR_xiC5B8hdbo(LG유…</td>
										<td class="text-left">프리템플릿 샘플</td>									
										<td class="text-center">홍길동</td>
										<td class="text-center end">2021-01-01 13:44:59</td>
									  </tr>
									</tbody>
								</table>
								<!-- //table -->

								<!-- pagination -->
								<div class="row mt20">
									<div class="col-xs-12">
										<div class="pagination1 text-center">
											<a href="#" title="10페이지 이전 페이지로 이동"><i class="far fa-chevron-double-left"></i></a>
											<a href="#" title="이전 페이지로 이동"><i class="far fa-chevron-left"></i></a>
											<a href="#" title="1페이지로 이동" class="number active">1</a>
											<a href="#" title="2페이지로 이동" class="number">2</a>
											<a href="#" title="3페이지로 이동" class="number">3</a>
											<a href="#" title="4페이지로 이동" class="number">4</a>
											<a href="#" title="5페이지로 이동" class="number">5</a>
											<a href="#" title="다음 페이지로 이동"><i class="far fa-chevron-right"></i></a>
											<a href="#" title="10페이지 다음 페이지로 이동"><i class="far fa-chevron-double-right"></i></a>
										</div>
									</div>
								</div>
								<!-- //pagination -->
							</div>								
						</div>
					</div>
					<div class="text-center mt20">
						<a href="#self" class="btnStyle2 backWhite" data-dismiss="modal" title="닫기">닫기</a>										
					</div>
				</div>
				
			</div>
		</div>
	</div>
</template>

<script>
import rcsTemplateSendApi from "@/modules/rcsTemplateSend/service/api.js";
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: "rcsMsgPop",
  components : {
  },
  props : {
        rcsMsgPopOpen: {
            type: Boolean,
            require: true,
            default: false,
        }
  },
  data() {
    return { 
        srcSelect : "brand",    // 검색 select box
        srcInput : ""           // 검색 input box
    }
  },
  methods: {
    // 팝업 기초 세팅 
    fnInit() {
        var vm = this;
        var params = {

        }
       // alert("test");
    },

    fnSearch() {

    },

    //팝업 닫기
    fnClose(){
      //데이터 초기화
      //this.templateList = [];
      //this.templateData = {};
      this.$emit('update:rcsMsgPopOpen', false);
    },
  }
}
</script>