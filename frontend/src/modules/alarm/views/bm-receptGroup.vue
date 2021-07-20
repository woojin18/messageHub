<template>
    <div>
        <article>
			<div class="contentHeader">
				<h2>메시지 발송 성공율 알람</h2>				
			</div>

			<!-- 본문 -->
			<div class="row">
				<div class="col-xs-12">
					<div class="menuBox">						
						<div class="of_h">
							<h4 class="inline-block" style="width:6%">수신그룹명</h4>
							<input type="text" class="inputStyle" style="width:14%" title="수신그룹명 입력란">
							<h4 class="inline-block ml30" style="width:6%">사용여부</h4>
							<select name="admin_sub14" class="selectStyle2" style="width:5%" title="사용여부 선택란">
								<option value="">Y</option>
								<option value="">N</option>
							</select>
							<a class="btnStyle1 float-right" title="검색">검색</a>
						</div>						
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-6 mt30">
					<div class="float-right mb20">						
						<a @click="fnAdd1" class="btnStyle2 backBlack" style="min-width:auto" title="수신그룹 등록" data-toggle="modal" data-target="#add1">수신그룹 등록</a>&nbsp;
						<a class="btnStyle2 borderGray" style="min-width:auto" title="수신그룹 삭제">수신그룹 삭제</a>
					</div>
					<div class="scroll-y5" style="width:100%">
						<table class="table_skin1" style="width:100%">
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
								<tr>
									<td class="text-center">1</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center">Y</td>
									<td class="text-center end">5</td>
								</tr>	
								<tr>
									<td class="text-center">1</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center">Y</td>
									<td class="text-center end">5</td>
								</tr>	
								<tr>
									<td class="text-center">1</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center">Y</td>
									<td class="text-center end">5</td>
								</tr>	
								<tr>
									<td class="text-center">1</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center">Y</td>
									<td class="text-center end">5</td>
								</tr>	
								<tr>
									<td class="text-center">1</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center">Y</td>
									<td class="text-center end">5</td>
								</tr>	
								<tr>
									<td class="text-center">1</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center">Y</td>
									<td class="text-center end">5</td>
								</tr>	
								<tr>
									<td class="text-center bgColor_sky">1</td>
									<td class="bgColor_sky"><a class="linkStyle">영업부</a></td>
									<td class="text-center bgColor_sky">Y</td>
									<td class="text-center bgColor_sky end">5</td>
								</tr>	
								<tr>
									<td class="text-center">1</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center">Y</td>
									<td class="text-center end">5</td>
								</tr>	
								<tr>
									<td class="text-center">1</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center">Y</td>
									<td class="text-center end">5</td>
								</tr>	
								<tr>
									<td class="text-center">1</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center">Y</td>
									<td class="text-center end">5</td>
								</tr>	
								<tr>
									<td class="text-center">1</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center">Y</td>
									<td class="text-center end">5</td>
								</tr>	
								<tr>
									<td class="text-center">1</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center">Y</td>
									<td class="text-center end">5</td>
								</tr>	
								<tr>
									<td class="text-center">1</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center">Y</td>
									<td class="text-center end">5</td>
								</tr>	
							</tbody>
						</table>
					</div>
				</div>
				<div class="col-xs-6 mt30">
					<div class="float-right mb20">						
						<a @click="fnAdd2" class="btnStyle2 backBlack" style="min-width:auto" title="그룹 수신자  추가" data-toggle="modal" data-target="#add2">그룹 수신자  추가</a>&nbsp;
						<a class="btnStyle2 borderGray" style="min-width:auto" title="그룹 수신자 삭제">그룹 수신자 삭제</a>
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
									<th class="text-center lc-1"><div class="consolCheck ml10"><input type="checkbox" id="check1" class="checkStyle2" value="check1"><label for="check1"></label></div></th>
									<th class="text-center lc-1">수신자명</th>
									<th class="text-center lc-1">수신그룹명</th>
									<th class="text-center lc-1 end">휴대폰</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check2" class="checkStyle2" value="check2"><label for="check2" class="trSelect"></label></div></td>
									<td>홍길동</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center end">010-4123-5678</td>
								</tr>	
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check3" class="checkStyle2" value="check3"><label for="check3" class="trSelect"></label></div></td>
									<td>홍길동</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center end">010-4123-5678</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check4" class="checkStyle2" value="check4"><label for="check4" class="trSelect"></label></div></td>
									<td>홍길동</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center end">010-4123-5678</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check5" class="checkStyle2" value="check5"><label for="check5" class="trSelect"></label></div></td>
									<td>홍길동</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center end">010-4123-5678</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check6" class="checkStyle2" value="check6"><label for="check6" class="trSelect"></label></div></td>
									<td>홍길동</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center end">010-4123-5678</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check7" class="checkStyle2" value="check7"><label for="check7" class="trSelect"></label></div></td>
									<td>홍길동</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center end">010-4123-5678</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check8" class="checkStyle2" value="check8"><label for="check8" class="trSelect"></label></div></td>
									<td>홍길동</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center end">010-4123-5678</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check9" class="checkStyle2" value="check9"><label for="check9" class="trSelect"></label></div></td>
									<td>홍길동</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center end">010-4123-5678</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check10" class="checkStyle2" value="check10"><label for="check10" class="trSelect"></label></div></td>
									<td>홍길동</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center end">010-4123-5678</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check11" class="checkStyle2" value="check11"><label for="check11" class="trSelect"></label></div></td>
									<td>홍길동</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center end">010-4123-5678</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check10" class="checkStyle2" value="check10"><label for="check10" class="trSelect"></label></div></td>
									<td>홍길동</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center end">010-4123-5678</td>
								</tr>
								<tr>
									<td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check11" class="checkStyle2" value="check11"><label for="check11" class="trSelect"></label></div></td>
									<td>홍길동</td>
									<td><a class="linkStyle">영업부</a></td>
									<td class="text-center end">010-4123-5678</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</article>
      <receptGroupPopup :popupTitle="popupTitle1" :rowData="rowData1" :popReset="popReset1"/>
      <recipientListPopup :popupTitle="popupTitle2" :rowData="rowData2" :popReset="popReset2"/>
    </div>
</template>

<script>
import alarmApi from "@/modules/alarm/service/alarmApi"
import receptGroupPopup from "@/modules/alarm/components/bp-receptGroup"
import recipientListPopup from "@/modules/alarm/components/bp-recipientList"
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  data() {
    return {
      alarmType: {
        alarmTypeCode : null,
        alarmTypeName : null,
        alarmDesc : null
      },
      popupTitle1: '등록',
      rowData1: {},
      popReset1: 0,
      popupTitle2: '등록',
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
      var params = {alarmTypeCode:this.$route.params.typeCode}
      var vm = this
      alarmApi.selectAlarmType(params).then(response => {
        var result = response.data
        if(result.success) {
          vm.alarmType = result.data
        }
      })
    },
    fnAdd1() {
        this.popupTitle1 = '등록'
        this.rowData1 = {}
        this.popReset1 = this.popReset1 + 1
        jQuery("#receptGroupPopup").modal("show")
    },
    fnAdd2() {
        this.popupTitle2 = '등록'
        this.rowData2 = {}
        this.popReset2 = this.popReset2 + 1
        jQuery("#recipientListPopup").modal("show")
    }
  }
}
</script>
