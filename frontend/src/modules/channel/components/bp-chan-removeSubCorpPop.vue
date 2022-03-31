<template>
	<!-- Register Modal -->
	<div class="modal modalStyle" id="removeSubCorpPop" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:650px">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>하위고객 브랜드 수신 제외</h2>
						<hr>
                    </div>
                    <div class="menuBox mt10 mb10">		
                        <div>
                            <select id="srcBrandType" v-model="srcBrandType" class="selectStyle2" style="width:30%">
                            <option value="brandId">브랜드 아이디</option>
                            <option value="brandName">브랜드 명</option>
                            </select>
                            <input id="srcBrandText" type="text" class="inputStyle ml20 vertical-baseline" style="width:45%" v-model="srcBrandText" @keypress.enter="fnSearch(1)">
                            <a @click="fnSearch(1)" class="btnStyle2 float-right" activity="READ">검색</a>
                        </div>
                    </div>
                    
                    <table class="table_skin1 tbl-striped" style="width:100%">
                      <colgroup>
                        <col>
                        <col style="width:45%">
                        <col style="width:55%">
                      </colgroup>
                      <thead>
                        <tr>
                          <th class="text-center lc-1">
                            <div class="consolCheck ml10">
                              <input type="checkbox" id="listMemCheck" class="checkStyle2" @change="fnListChkAll" v-model="listAllChecked">
                              <label for="listMemCheck"></label>
                            </div>
                          </th>
                          <th class="text-center lc-1">브랜드 ID</th>
                          <th class="text-center lc-1">브랜드 명</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(data, index) in removeBrandList" :key="index">
                          <td class="text-cneter">
                            <div class="consolCheck ml10">
                              <input type="checkbox" :id="'listCheck_'+index" class="checkStyle2" :value="data.BRAND_ID" v-model="listChkBox">
                              <label :for="'listCheck_'+index"></label>
                            </div>
                          </td>
                          <td class="text-center">{{ data.BRAND_ID }}</td>
                          <td class="text-center lc-1">{{ data.BRAND_NAME }}</td>
                        </tr>
                        <tr v-if="removeBrandList.length == 0">
                          <td class="text-center"></td>
                          <td class="text-center" colspan="4">검색된 내용이 없습니다.</td>
                        </tr>
                      </tbody>
                    </table>
                    <div id="pageContent">
                      <PageLayer @fnClick="fnPageClick" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
                    </div>
					<div class="text-center mt20">
						<a @click.prevent="fnSave" class="btnStyle1 backRed mr20"  title="닫기">저장</a>
						<a @click.prevent="fnClose" class="btnStyle1 backWhite"  title="닫기">닫기</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import api from '../service/api'
import confirm from '@/modules/commonUtil/service/confirm'
import PageLayer from '@/components/PageLayer.vue'
import {eventBus} from "@/modules/commonUtil/service/eventBus"


export default {
	name: 'removeSubCorpPop',
	components: {
        PageLayer
	},
	props: {
		removeCorpPopCnt: {
			type: Number,
			require: true,
			default: false
		},
		setRbcLoginId: {
			type: String,
			require: true,
			default: false
		},
        setRbcApiKey: {
			type: String,
			require: true,
			default: false
		}
        
	},
	data() {
		return {
			contentTitle : "하위고객 브랜드 수신 제외",
            listSize : 10,	// select 박스 value (출력 갯수 이벤트)
			pageNo : 1,		// 현재 페이징 위치
			totCnt : 0,		// 전체 리스트 수
			offset : 0,		// 페이지 시작점
            srcBrandType : "brandId",
            srcBrandText : "",
            removeBrandList : [],
            listAllChecked : false,
            listChkBox : [],
            listAllChkBox : []

            
		}
	},
	watch: {
		removeCorpPopCnt() {	
            this.fnSearch();
		}
	},
	methods: {
		// 닫기
		fnClose(){
			this.fnInit();
			jQuery('#removeSubCorpPop').modal('hide');
		},

		// 입력값 초기화
		fnInit() {
            this.srcBrandType = "brandId";
            this.srcBrandText = "";
            this.removeBrandList = [];
            this.checkBrandList = [];
            this.listAllChecked = false;
            this.listChkBox = [];
            this.listAllChkBox = [];
		},

        fnPageClick(pageNo) {
			this.pageNo = pageNo;
			this.fnSearch();
		},

        fnSearch() {
            var vm = this;
            var params = {
                "setRbcLoginId" : vm.setRbcLoginId,
                "setRbcApiKey" : vm.setRbcApiKey,
                "pageNo": vm.pageNo,
				"listSize": vm.listSize,
                "srcBrandType" : vm.srcBrandType,
                "srcBrandText" : vm.srcBrandText,
            }

            api.selectRemoveSubCorpList(params).then(response =>{
                var result = response.data;
                if(result.success) {
					this.removeBrandList = Object.assign([], result.data.data);
                    this.checkBrandList = Object.assign([], result.data.checkBrandList); 
                    this.checkBrandList.forEach(function(data) {
                        vm.listChkBox.push(data.BRAND_ID)
                    })
					this.totCnt = result.pageInfo.totCnt
					this.offset = result.pageInfo.offset
                } else {
					confirm.fnAlert(vm.contentTitle, result.message);
				}
            });
        },

        fnListChkAll() { 
			var vm = this
			if(this.listAllChecked) {
				this.removeBrandList.forEach(function(data) {
					vm.listChkBox.push(data.BRAND_ID)
				})
			} else {
				this.listChkBox = []
			}
		},

        fnSave() {
            //유효성 검사
			if(this.listChkBox == null || this.listChkBox.length == 0) {
				confirm.fnAlert(this.contentTitle, '수신 제외할 하위고객 브랜드를 선택해주세요.')
				return false
			}
			eventBus.$on('callbackEventBus', this.fnSetRemoveBrand)
			confirm.fnConfirm(this.contentTitle, "선택하신 브랜드를 수신 제외 처리합니다.\n저장하시겠습니까?", "확인")
			
        },
        fnSetRemoveBrand() {
            var vm = this;

            this.removeBrandList.forEach(function(data) {
                vm.listAllChkBox.push(data.BRAND_ID)
            })


            let params = {
				'brandIdList': this.listChkBox,
                'brandIdAllList' : this.listAllChkBox,
			}
			api.setRemoveBrand(params).then(response =>{
				var result = response.data
				if(result.success) {

					confirm.fnAlert(vm.contentTitle, '선택하신 브랜드를 수신 제외 처리하였습니다.');
                    vm.fnClose();
				} else {
					confirm.fnAlert(vm.contentTitle, result.message)
				}
			})
        }
        
	}
}
</script>