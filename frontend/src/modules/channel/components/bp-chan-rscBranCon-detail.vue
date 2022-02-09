<template>
	<!-- Register Modal -->
	<div class="modal modalStyle" id="rcsPop" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:650px">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>브랜드 연결</h2>
						<hr>
            <div class="of_h">
							<div class="menuBox">						
								<p class="colo4"><i class="far fa-info-circle vertical-baseline"></i> 다른 프로젝트에서 등록한 RCS 브랜드를 연결할 수 있습니다.(발신번호도 연결됩니다.</p>
								<p class="colo4"><i class="far fa-info-circle vertical-baseline"></i> 연결 하실 RCS 브랜드를 선택하고 [브랜드 연결] 버튼을 누르면 프로젝트와 연결됩니다.</p>
							</div>
						</div>
            
						<div class="row consolMarginTop">
							<div class="col-xs-12">		
                <!-- 리스트 Start -->
                <div class="of_h inline">
                  <div class="float-right">
                    <a  class="btnStyle2 borderGray">브랜드 연결</a>
                  </div>
                </div>
              
                <!-- 15개씩 보기 -->
                <div class="of_h inline">
                  <div class="float-left">전체 : <span class="color1"><strong>{{totCnt}}</strong></span>건</div>
                </div>
                <!-- //15개씩 보기 -->
                <div class="row">
                  <div class="col-xs-12 consolMarginTop">
                    <table class="table_skin1 tbl-striped" style="width:100%">
                      <colgroup>
                        <col>
                        <col style="width:33%">
                        <col style="width:33%">
                        <col style="width:*">
                      </colgroup>
                      <thead>
                        <tr>
                          <th class="text-center lc-1">
                            <div class="consolCheck ml10">
                              <input type="checkbox" id="listMemCheck" class="checkStyle2" @change="fnListMemChkAll" v-model="listMemAllChecked">
                              <label for="listMemCheck"></label>
                            </div>
                          </th>
                          <th class="text-center lc-1">브랜드</th>
                          <th class="text-center lc-1">발신번호 개수</th>
                          <th class="text-center lc-1 end">등록일자</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(data, index) in callNumList" :key="index">
                          <td class="text-cneter">
                            <div class="consolCheck ml10">
                              <input type="checkbox" :id="'listMemCheck_'+index" class="checkStyle2" :value="data.brandId" v-model="listMemChkBox">
                              <label :for="'listMemCheck_'+index"></label>
                            </div>
                          </td>
                          <td class="text-center">{{ data.brandName }}</td>
                          <td class="text-center lc-1">{{ data.mdnCnt }}</td>
                          <td class="text-center lc-1 end">{{ data.regDt }}</td>
                        </tr>
                        <tr v-if="callNumList.length == 0">
                          <td class="text-center"></td>
                          <td class="text-center" colspan="4">검색된 내용이 없습니다.</td>
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
import PageLayer from '@/components/PageLayer.vue';
import confirm from '@/modules/commonUtil/service/confirm';

export default {
	name: 'rcsPop',
	components: {
		PageLayer,
	},
	props: {
		projectId: {
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
			callNumList: [],
			listSize : 10,	// select 박스 value (출력 갯수 이벤트)
			pageNo : 1,		// 현재 페이징 위치
			totCnt : 0,		// 전체 리스트 수
			offset : 0,		// 페이지 시작점
			listMemAllChecked: false,
			listMemChkBox: [],
		}
	},
	mounted() {
		this.fnPageNoResetSearch();
	},
	watch: {
		popCnt() {
			this.fnSearch(1);
		}
	},
	methods: {
		fnSearch(pageNum) {
			this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNum, '1'))*1;
			this.fnSearchCallNumList();
		},
		fnPageNoResetSearch(){
			this.$refs.updatePaging.fnAllDecrease();
		},
		//사용자 조회
		async fnSearchCallNumList() {
			let params = {
				pageNo: this.pageNo,
				listSize: this.listSize,
				projectId: this.projectId 
			};

			await api.getRcsBrandConList(params).then(response =>{
				const result = response.data;
				this.fnResetChkbox();
				if(result.success) {
					this.callNumList = Object.assign([], result.data);
					this.totCnt = result.pageInfo.totCnt;
					this.offset = result.pageInfo.offset;
				} else {
					confirm.fnAlert('', result.message);
				}
			});
		},
		fnPageClick(pageNo) {
			this.pageNo = pageNo;
			this.fnSearchCallNumList();
			this.fnResetChkbox();
		},
		// 닫기
		fnClose(){
			this.fnInit();
			jQuery('#rcsPop').modal('hide');
		},
		// 입력값 초기화
		fnInit() {
			this.callNumList = [];
			this.pageNo = 1;
			this.offset = 0;
			this.totCnt = 0;
		},
		//리스트 전체 체크박스
		fnListMemChkAll() { 
			var vm = this;
			if(this.listMemAllChecked) {
				this.callNumList.forEach(function(data) {
					vm.listMemChkBox.push(data.brandId);
				});
			} else {
				this.listMemChkBox = [];
			}
		},
		// 발신번호 추가
		fnRegister() {
			//유효성 검사
			if(this.listMemChkBox == null || this.listMemChkBox.length == 0) {
				confirm.fnAlert('', '발신번호를 선택해주세요.');
				return false;
			}

			let params = {
				'projectId': this.projectId,
				'callNumList': this.listMemChkBox,
			};

			api.saveProjectCallNum(params).then(response =>{
				var result = response.data;
				if(result.success) {
					this.$parent.fnSearch(1);
					confirm.fnAlert('', '등록을 성공했습니다.');
				} else {
					confirm.fnAlert('', result.message);
				}
			});
			this.fnInit();
			this.fnResetChkbox();
			this.fnClose();
		},
		fnResetChkbox() {
			this.listMemAllChecked = false;
			this.listMemChkBox = [];
		},
	}
}
</script>