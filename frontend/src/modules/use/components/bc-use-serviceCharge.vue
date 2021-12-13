<template>
	<div class="mt20">
		<article>
			<h4 class="topH4 mt30">SMS/MMS</h4>
			<table class="table_skin1 bt-000 tbl-striped">
				<colgroup>
					<col style="width:50%">
					<col style="width:50%">
					<!-- <col style="width:25%"> -->
					<col>
				</colgroup>
				<thead>
					<tr>
						<th class="text-center lc-1">상품명</th>
						<th class="text-center lc-1 end">건별 요금<br>(VAT별도)</th>
						<!-- <th class="text-center lc-1 end" colspan="2">후불요금</th> -->
					</tr>
					<!-- <tr style="border-top: 1px solid #D5D5D5;">
						<th class="text-center lc-1">월 수신건</th>
						<th class="text-center lc-1 end">후불 수신 건별 요금<br>(VAT별도)</th>
					</tr> -->
				</thead>
				<tbody>
					<tr v-for="(row, index) in smsmmsList" :key="index">
						<td class="text-center" style="border-bottom: none;" :rowspan="row.tobeInfoLength" v-if="row.tobeInfoIdx == 0 ? true : false">{{ row.productName}}</td>
						<td class="text-center end" style="border-bottom: none;" :rowspan="row.tobeInfoLength" v-if="row.tobeInfoIdx == 0 ? true : false">
							{{ row.preeFee }}원
						</td>
						<!-- <td class="text-center">{{ row.feeCnt }}</td>
						<td class="text-center end">
							{{ row.tobeFee }}원
						</td> -->
					</tr>
				</tbody>
			</table>
			<h4 class="topH4 mt30">RCS</h4>
			<table class="table_skin1 bt-000 tbl-striped">
				<colgroup>
					<col style="width:50%">
					<col style="width:50%">
					<!-- <col style="width:25%"> -->
					<col>
				</colgroup>
				<thead>
					<tr>
						<th class="text-center lc-1">상품명</th>
						<th class="text-center lc-1 end">건별 요금<br>(VAT별도)</th>
						<!-- <th class="text-center lc-1 end" colspan="2">후불요금</th> -->
					</tr>
					<!-- <tr style="border-top: 1px solid #D5D5D5;">
						<th class="text-center lc-1">월 수신건</th>
						<th class="text-center lc-1 end">후불 수신 건별 요금<br>(VAT별도)</th>
					</tr> -->
				</thead>
				<tbody>
					<tr v-for="(row, index) in rcsList" :key="index">
						<td class="text-center" style="border-bottom: none;" :rowspan="row.tobeInfoLength" v-if="row.tobeInfoIdx == 0 ? true : false">{{ row.productName}}</td>
						<td class="text-center end" style="border-bottom: none;" :rowspan="row.tobeInfoLength" v-if="row.tobeInfoIdx == 0 ? true : false">
							{{ row.preeFee }}원
						</td>
						<!-- <td class="text-center">{{ row.feeCnt }}</td>
						<td class="text-center end">
							{{ row.tobeFee }}원
						</td> -->
					</tr>
				</tbody>
			</table>
			<h4 class="topH4 mt30">KakaoTalk Bizmessage</h4>
			<table class="table_skin1 bt-000 tbl-striped">
				<colgroup>
					<col style="width:50%">
					<col style="width:50%">
					<!-- <col style="width:25%"> -->
					<col>
				</colgroup>
				<thead>
					<tr>
						<th class="text-center lc-1">상품명</th>
						<th class="text-center lc-1 end">건별 요금<br>(VAT별도)</th>
						<!-- <th class="text-center lc-1 end" colspan="2">후불요금</th> -->
					</tr>
					<!-- <tr style="border-top: 1px solid #D5D5D5;">
						<th class="text-center lc-1">월 수신건</th>
						<th class="text-center lc-1 end">후불 수신 건별 요금<br>(VAT별도)</th>
					</tr> -->
				</thead>
				<tbody>
					<tr v-for="(row, index) in kkoList" :key="index">
						<td class="text-center" style="border-bottom: none;" :rowspan="row.tobeInfoLength" v-if="row.tobeInfoIdx == 0 ? true : false">{{ row.productName}}</td>
						<td class="text-center end" style="border-bottom: none;" :rowspan="row.tobeInfoLength" v-if="row.tobeInfoIdx == 0 ? true : false">
							{{ row.preeFee }}원
						</td>
						<!-- <td class="text-center">{{ row.feeCnt }}</td>
						<td class="text-center end">
							{{ row.tobeFee }}원
						</td> -->
					</tr>
				</tbody>
			</table>
			<h4 class="topH4 mt30">Push</h4>
			<table class="table_skin1 bt-000 tbl-striped">
				<colgroup>
					<col style="width:50%">
					<col style="width:50%">
					<!-- <col style="width:25%"> -->
					<col>
				</colgroup>
				<thead>
					<tr>
						<th class="text-center lc-1">상품명</th>
						<th class="text-center lc-1 end">건별 요금<br>(VAT별도)</th>
						<!-- <th class="text-center lc-1 end" colspan="2">후불요금</th> -->
					</tr>
					<!-- <tr style="border-top: 1px solid #D5D5D5;">
						<th class="text-center lc-1">월 수신건</th>
						<th class="text-center lc-1 end">후불 수신 건별 요금<br>(VAT별도)</th>
					</tr> -->
				</thead>
				<tbody>
					<tr v-for="(row, index) in pushList" :key="index">
						<td class="text-center" style="border-bottom: none;" :rowspan="row.tobeInfoLength" v-if="row.tobeInfoIdx == 0 ? true : false">{{ row.productName}}</td>
						<td class="text-center" style="border-bottom: none;" :rowspan="row.tobeInfoLength" v-if="row.tobeInfoIdx == 0 ? true : false">
							{{ row.preeFee }}원
						</td>
						<!-- <td class="text-center">{{ row.feeCnt }}</td>
						<td class="text-center end">
							{{ row.tobeFee }}원
						</td> -->
					</tr>	
				</tbody>
			</table>
		</article>
	</div>
</template>

<script>
import useApi from '../service/useApi'
import tokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm";

export default {
	name: 'productUnit',
	components: {	
	},
	props : {
	},
	data() {
		return {
			data : [],
			rcsList : [],
			pushList : [],
			smsmmsList : [],
			kkoList : [],
		}
	},
	mounted() {
		this.fnSearch();
	},
	methods : {
		 // 조회
		fnSearch() {
			var params = {
				corpId: tokenSvc.getToken().principal.corpId,
				//corpId: 'COM2104142258921',
			}
			useApi.selectCorpProductUnitDetail(params).then(response =>{
				var result = response.data;
				console.log(response);
				if(result.success) {
					this.data = result.data;
					this.rcsList = result.data.rcsList;
					this.pushList = result.data.pushList;
					this.smsmmsList = result.data.smsmmsList;
					this.kkoList = result.data.kkoList;
				} else {
					confirm.fnAlert("", result.message);
				}
			});
		},
	}
}
</script>