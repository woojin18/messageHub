<template>
  <div>
    <regPopup :save_status="save_status" :row_data="row_data" :projectId="projectId"  />
    <grpMngPopup :projectId="projectId" />
    <grpAddPopup :row_data="row_data" :projectId="projectId" />
		<article>
			<div class="tab-content" style="border-top:1px solid #D5D5D5">
				<!--  tab1 -->
				<div role="tabpanel" v-if="this.$parent.selSubTab === 1">
					<div class="mt20">
						<!-- templateList -->
						<p class="color3"><i class="far fa-info-circle"></i> 카카오톡 비즈메시지를 발송하려면 카카오톡 채널을 먼저 등록해야 합니다.<br>
							<i class="far fa-info-circle"></i> 카카오톡 채널은 카카오톡 비즈니스 ID로, 카카오톡 홈페이지(https://center-pf.kakao.com)에서 무료로 만들 수 있습니다.<br>
							<i class="far fa-info-circle"></i> 담당자는 등록한 카카오톡 채널과 서류를 확인하고 본인 여부 등의 심사를 진행합니다.<br>
							<i class="far fa-info-circle"></i> 카카오 채널 추가 시, 카카오 채널에 등록된 관리자 연락처로 받은 인증번호 6자리를 입력하셔야 등록할 수 있습니다.<br>
						</p>

						<div class="menuBox mt10">						
							<div class="of_h">
								<h4 class="inline-block" style="width:10%">채널 검색용 아이디</h4>
								<input type="text" class="inputStyle" style="width:15%" v-model="srcKkoChId" @keypress.enter="fnSearch(1)">

								<!-- <h4 class="inline-block vertical-middle ml100" style="width:11%">프로필 상태</h4>
								<select name="admin030404_1" class="selectStyle2" style="width:15%" v-model="srcUseYn">
									<option value="">전체</option>
									<option value="Y">Y</option>
									<option value="N">N</option>
								</select> -->
								<a class="btnStyle1 float-right" @click="fnSearch(1)">검색</a>
							</div>						
						</div>

						<div class="consolMarginTop">
							<div class="of_h inline">
								<div class="float-right">
									<a @click="fnReg" class="btnStyle2 borderGray" data-toggle="modal" data-target="#Register">카카오 채널 추가</a>
									<a href="https://center-pf.kakao.com/" target="_blank" class="btnStyle2 borderGray ml10">카카오 채널 생성</a>
								</div>
							</div>
							<div class="float-left">전체 : <span class="color1"><strong>{{totCnt}}</strong></span>건
								<SelectLayer @fnSelected="fnSelected" classProps="selectStyle2 width120 ml20"></SelectLayer>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12 mt10">
								<table id="list" class="table_skin1 bt-000 tbl-striped">
									<thead>
										<th class="text-center lc-1">채널 검색용 아이디</th>
										<th class="text-center lc-1">발신키</th>
										<th class="text-center lc-1">휴면 여부</th>
										<th class="text-center lc-1">등록일시</th>
										<th class="text-center lc-1 end">비고</th>
									</thead>
									<tbody>
										<tr v-for="(row, index) in data" :key="index">
											<td>
												{{row.kkoChId}}
											</td>
											<td>
												{{row.senderKey}}
											</td>
											<td>
												{{row.useYnNm}}
											</td>
											<td>
												{{row.regDt}}
											</td>
											<td class="text-center lc-1 end">
												<button class="btnStyle1 borderLightGray small mr5" @click="fnKakaoRecover(row)" activity="SAVE"><a>휴먼상태해제</a></button>
											</td>
										</tr>
										<tr v-if="data.length == 0">
											<td class="text-center" colspan="5">검색된 내용이 없습니다.</td>
										</tr>
									</tbody>
								</table>
							</div>			
						</div>
						<div id="pageContent">
							<PageLayer @fnClick="fnSearch" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
						</div>
					</div>
				</div>
			</div>
		</article>
	</div>
</template>


<script>
import Api from '../service/api'
import regPopup from "./bp-chan-kakao.vue";
import grpMngPopup from "./bp-chan-kakao-groupManage.vue";
import grpAddPopup from "./bp-chan-kakao-groupAdd.vue";

import SelectLayer from '@/components/SelectLayer.vue';
import PageLayer from '@/components/PageLayer.vue';

import confirm from "@/modules/commonUtil/service/confirm"

export default {
  components: {
    regPopup,
    grpMngPopup,
    grpAddPopup,
	PageLayer,
	SelectLayer
  },
  data() {
    return {
		// 리스트 
		// 프로필 일반
		data : {},
		listSize : 10,  // select 박스 value (출력 갯수 이벤트)
		pageNo : 1,  // 현재 페이징 위치
		totCnt : 0,  //전체 리스트 수
		offset : 0, //페이지 시작점
		row_data : {},
		// 프로필 그룹
		data2 : {},
		row_data2 : {},
		// 프로젝트 정보
		projectId : '',
		projectName : '',
		save_status : '',
		// 검색조건
		srcKkoChId : '',
		srcUseYn : ''
    }
  },
  mounted() {
    this.projectId = this.$parent.projectId;
    this.projectName = this.$parent.projectName;

    this.fnSearch(1);
  },
  methods: {
	// select 박스 선택시 리스트 재출력
	fnSelected(listSize) {
		this.listSize = Number(listSize);
		this.$refs.updatePaging.fnAllDecrease();
	},
    // 검색
    fnSearch(pageNo) {
		var params = {
			"projectId"     : this.projectId,
			"srcKkoChId"  	: this.srcKkoChId,
			"srcUseYn"  	: this.srcUseYn,
			"pageNo"		: (this.$gfnCommonUtils.defaultIfEmpty(pageNo, '1'))*1,
			"listSize"		: this.listSize
		}
		
		Api.selectKkoCh(params).then(response =>{
			var result = response.data;
			if(result.success) {
				this.data = result.data; 
				this.totCnt = result.pageInfo.totCnt;
				this.offset = result.pageInfo.offset;
			}
		});
    },
    // 발신 프로필 그룹 추가
    fnReg(){
		this.save_status = "C";
		this.row_data = { 
			"kkoChId"			: "",
			"phoneNumber" 	    : "",
			"categoryId"  		: "",
			"otherProjectYn" 	: "Y" 
		}
		jQuery("#regPopup").modal('show');
    },
    fnMod(row_data){
		this.save_status = "U";
		this.row_data = row_data;
		jQuery("#regPopup").modal('show');
    },
    // 발신 프로필 그룹 추가
    fnGrpAdd(){
		jQuery("#grpAddPopup").modal('show');
    },
	// 발신 프로필 그룹 관리
	fnGrpMng(data){
		this.row_data = data;
		jQuery("#grpMngPopup").modal('show');
	},
	// 휴면상태 해제
	fnKakaoRecover(row){
		if(row.useYn === 'Y'){
			confirm.fnAlert("", "휴면상태만 해제 가능합니다.");
			return;
		}

		var params = {
			"senderKey"   : row.senderKey
		}

		Api.saveKkoChRecover(params).then(response =>{
			var result = response.data;

			if(result.success) {
				confirm.fnAlert("", "처리되었습니다.");
				// 재조회
				this.fnSearch(1);
			} else {
				confirm.fnAlert("", result.message);
			}
		});
	}
  }
}
</script>