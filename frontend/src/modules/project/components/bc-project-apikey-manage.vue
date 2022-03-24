<template>
	<!-- content -->
	<div>
		<article>
			<div class="row">
				<div class="Dashboard01 pd0 of_h"><h4 class="inline-block">API KEY 관리</h4>
                <div class="float-right h4Button">
                <a class="btnStyle2 borderGray" @click="fnApikeyGeneration">KEY 생성<i class="far fa-plus-circle ml10"></i></a></div>
                <p style="opacity: 0.5;">* 프로젝트 API Key는 5개까지 발급받을 수 있으며, 서비스 이용 시 인증에 사용됩니다.</p>
				</div>
           <table cellspacing="0" id="list" class="table_skin1 bt-000 tbl-striped" style="width:100%; margin-top : 10px;">
             <thead>
              <th class="text-center lc-1">API KEY</th>
              <th class="text-center lc-1">API KEY 명</th>
              <th class="text-center lc-1">TPS</th>
              <th class="text-center lc-1">라인타입</th>
              <th class="text-center lc-1">상태</th>
              <th class="text-center lc-1 end" style=" width: 200px;">생성일</th>
            </thead>
            <tbody>
              <tr v-for="(data, index) in rowData" :key="index"> 
                <td style="text-decoration: underline; cursor: pointer;">
                    <a @click="fnApikeyDetail(data)">{{data.apiKey}}</a>
                </td>
                <td>
                    {{data.apiKeyName}} 
                </td>
                <td>
                     {{data.tps}} 
                </td>
                <td>
                     {{data.lineType}}  
                </td>
                <td>
                     {{data.status}} 
                </td>
                <td  class="end">
                      {{data.regDt}}     
                </td>
              </tr>
               <tr v-if="rowData.length == 0">
                <td class="text-center" colspan="9">검색된 내용이 없습니다.</td>
              </tr> 
            </tbody>
          </table>
      <!-- 리스트 -->
      <apikeyGeneration />
      <apikeyDetail :apiKey="apiKey" />
              </div>
		</article>

	</div>
</template>

<script>
import confirm from '@/modules/commonUtil/service/confirm';
import projectApi from '../service/projectApi'
import apikeyGeneration from './bp-apikey-generation.vue'
import apikeyDetail from './bp-apikey-manage-detail.vue'
export default {
	components: {
    apikeyGeneration,
    apikeyDetail
	},
	props: {
		searchData : {
			type: Object,
			require: false,
			default: function() {
				return {
					'userName'			: '',
					'approvalStatus'	: '',
					'roleCd'			: '',
					'loginId'			: ''
				}
			}
		}
	},

	data() {
		return {
			memberList: [],
			listSize : 10,	// select 박스 value (출력 갯수 이벤트)
			pageNo : 1,		// 현재 페이징 위치
			totCnt : 0,		// 전체 리스트 수
			offset : 0,		// 페이지 시작점
			projectId : this.$parent.projectId,
      rowData : {},
      apiKey : ''
		}
	},
	mounted() {
      this.fnApikeyManageList()
      
    }
    ,
	watch: {
	},
	methods: {
    fnApikeyManageList(){
      var vm = this;

      var params = {
      projectId : this.projectId
      }
        projectApi.selectApikeyManageList(params).then(response =>{
        vm.rowData = response.data.data;
        });
	  },
    fnApikeyDetail(data){
      this.apiKey = data.apiKey
      jQuery('#apikeyManageDetail').show() 
    },
    fnApikeyGeneration(){
      jQuery('#key').show()
    }      
}

}

</script>