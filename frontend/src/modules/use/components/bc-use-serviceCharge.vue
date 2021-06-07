<template>
	<div id="content">
		<article>
			<div class="contentHeader">
				<h2>서비스 요금</h2>
			</div>
			<table class="table_skin1 dataTable">
				<colgroup>
					<col style="width:10%">
					<col style="width:15%">
					<col style="width:15%">
					<col style="width:15%">
					<col style="width:15%">
					<col style="width:15%">
					<col style="width:15%">
			</colgroup>
			<thead>
				<tr>
					<th class="text-center lc-1">채널구분</th>
					<th class="text-center lc-1">채널명</th>
					<th class="text-center lc-1">상품코드</th>
					<th class="text-center lc-1">상품별</th>
					<th class="text-center lc-1">선물 건별 요금<br/>(VAT별도)</th>
					<th class="text-center lc-1">후불 과금<br/>(월 수신 건)</th>
					<th class="text-center lc-1 end">후불 요금<br/>(VAT별도)</th>
				</tr>
			</thead>
				<tbody>
					<tr v-for="(row, index) in data" :key="index">
						<td class="text-center" :rowspan="row.tobeInfoLength" v-if="row.tobeInfoIdx == 0 ? true : false">{{ row.smartChProductYn }}</td>
						<td class="text-center" :rowspan="row.tobeInfoLength" v-if="row.tobeInfoIdx == 0 ? true : false">{{ row.chName}}</td>
						<td class="text-center" :rowspan="row.tobeInfoLength" v-if="row.tobeInfoIdx == 0 ? true : false">{{ row.productCode }}</td>
						<td class="text-center" :rowspan="row.tobeInfoLength" v-if="row.tobeInfoIdx == 0 ? true : false">{{ row.productName }}</td>
						<td class="text-center" :rowspan="row.tobeInfoLength" v-if="row.tobeInfoIdx == 0 ? true : false">
							<!--
							 <input type="text" class="inputStyle text-right" style="padding:0; height:20px;" v-model="row.preeFee" oninput="javascript: this.value = this.value.replace(/[^0-9.]/g, '');">
							 -->
							 {{ row.preeFee }}
						</td>
						<td class="text-right">{{ row.feeCnt }}</td>
						<td class="text-center">
							<!--
							<input type="text" class="inputStyle text-right" style="padding:0; height:20px;" v-model="row.tobeFee" oninput="javascript: this.value = this.value.replace(/[^0-9.]/g, '');">
							-->
							 {{ row.tobeFee }}
						</td>
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
			data : []
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
				console.log("debug point");
				if(result.success) {
					this.data = result.data;
				} else {
					confirm.fnAlert("", result.message);
				}
			});
		},
	}
}
</script>