<template>
	<!-- Register Modal -->
	<div class="modal modalStyle" id="conProject" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:650px">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>연결 프로젝트 상세</h2>
						<hr>

						<div class="row consolMarginTop">
							<div class="col-xs-12">		
                <!-- 리스트 Start -->
              
                <div class="of_h inline">
                  <div class="float-left">전체 : <span class="color1"><strong>{{totCnt}}</strong></span>건</div>
                </div>

                <div class="row">
                  <div class="col-xs-12 consolMarginTop">
                    <table class="table_skin1 tbl-striped" style="width:100%">
                      <colgroup>
                        <col>
                        <col style="width:33%">
                      </colgroup>
                      <thead>
                        <tr>
                          <th class="text-center lc-1">프로젝트 명</th>
                          <th class="text-center lc-1">프로젝트 ID</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(data, index) in conProjectList" :key="index">
                          <td class="text-center">{{ data.projectName }}</td>
                          <td class="text-center lc-1 end">{{ data.projectId }}</td>
                        </tr>
                        <tr v-if="conProjectList.length == 0">
                          <td class="text-center" colspan="2">검색된 내용이 없습니다.</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
                <!-- 리스트 End -->
	
                <!-- pagination Start -->
                <div id="pageContent">
                  <PageLayer @fnClick="fnPageClick" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
                </div>
                <!-- pagination End-->
              </div>
            </div>
          </div>		
					<div class="text-center mt20">
						<a @click="fnClose" class="btnStyle1 backWhite"  title="닫기">닫기</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import api from '../service/api'
import PageLayer from '@/components/PageLayer.vue'
import confirm from '@/modules/commonUtil/service/confirm'


export default {
	name: 'conProject',
	components: {
		PageLayer,
	},
	props: {
		brandId: {
			type: String,
			require: true,
			default: false
		},
		popCnt: {
			type: Number,
			require: true,
			default: false
		},
        
	},
	data() {
		return {
			conProjectList: [],
			listSize : 10,	// select 박스 value (출력 갯수 이벤트)
			pageNo : 1,		// 현재 페이징 위치
			totCnt : 0,		// 전체 리스트 수
			offset : 0,		// 페이지 시작점
			listMemAllChecked: false,
			listMemChkBox: [],
		}
	},
	mounted() {
		this.fnPageNoResetSearch()
	},
	watch: {
		popCnt() {
		this.$refs.updatePaging.fnAllDecrease();	
		this.fnConProjectList()
		}
	},
	methods: {
		fnPageNoResetSearch(){
			this.$refs.updatePaging.fnAllDecrease()
		},
		//연결 프로젝트 조회
		async fnConProjectList() {
			let params = {
				pageNo: this.pageNo,
				listSize: this.listSize,
				brandId: this.brandId 
			}

			await api.selectConProjectList(params).then(response =>{
				const result = response.data
				if(result.success) {
                    this.conProjectList = Object.assign([], result.data)
					this.totCnt = result.pageInfo.totCnt
					this.offset = result.pageInfo.offset
				} else {
					confirm.fnAlert('', result.message)
				}
			})
		},
		fnPageClick(pageNo) {
			this.pageNo = pageNo
            this.fnConProjectList()
		},
		// 닫기
		fnClose(){
			this.fnInit()
			jQuery('#conProject').modal('hide')
		},
		// 입력값 초기화
		fnInit() {
			this.pageNo = 1
			this.offset = 0
			this.totCnt = 0
		},
	}
}
</script>