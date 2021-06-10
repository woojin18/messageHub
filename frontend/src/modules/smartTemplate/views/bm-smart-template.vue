<template>
<div class="row row-no-margin">
  <div class="contentHeader">
      <h2> > 스마트발송 템플릿</h2>
      <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="메시지 상세조회 이용안내">이용안내 <i class="fal fa-book-open"></i></a>
  </div>
  <!-- 본문 -->
  <div class="row">
    <div class="col-xs-12">
      <div class="menuBox">

        <div class="of_h">
          <div class="inline-block" style="width:8%"><h4 class="font-normal mt15">검색조건</h4></div>
          <div class="inline-block" style="width:91%">
            <select v-model="searchData.searchCondi" class="selectStyle2" style="width:15%" title="검색조건">
                <option value="templateName">템플릿명</option>
                <option value="templateChannel">템플릿채널</option>
            </select>
            <input type="text" class="inputStyle vertical-top ml10" id="searchText" name="searchText" v-model="searchData.searchText" style="width:37.5%" title="">
          </div>
        </div>
        <div class="of_h consolMarginTop">
          <div class="inline-block" style="width:8%"><h4 class="font-normal mt15">등록일자</h4></div>
          <div class="inline-block" style="width:91%">
            <Calendar @update-date="fnUpdateStartDate" calendarId="searchStartDate" classProps="datepicker inputStyle maxWidth200" :initDate="searchData.searchStartDate" ></Calendar>
            <span style="padding:0 11px">~</span>
            <Calendar @update-date="fnUpdateEndDate" calendarId="searchEndDate" classProps="datepicker inputStyle maxWidth200" :initDate="searchData.searchEndDate"></Calendar>
            <ul class="tab_s2 ml20">
                <li :class="this.searchDateInterval==0 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(0);" title="오늘 날짜 등록일자 검색">오늘</a></li>
                <li :class="this.searchDateInterval==7 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(7);" title="1주일 등록일자 검색">1주일</a></li>
                <li :class="this.searchDateInterval==15 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(15);" title="15일 등록일자 검색">15일</a></li>
                <li :class="this.searchDateInterval==30 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(30);" title="1개월 등록일자 검색">1개월</a></li>
            </ul>
            <a @click="fnSearch()" class="btnStyle2 float-right" title="검색">검색</a>
          </div>
        </div>
      </div>
    </div>
  </div>


	<div>
		<h4>스마트발송 상품</h4>
		<ul class="of_h">
			<!--<li class="border-line2 of_h float-left pd20" style="width:252px" v-for="(data, idx) in products" :key="data.row_num">-->
			<li class="border-line2 of_h float-left pd20" style="width:430px" v-for="(data, idx) in products" :key="data.row_num">
				<ul class="color6 float-left mr10" style="font-weight:700">
					<li><h5>상품명 : </h5></li>
					<li><h5>채널구성 : </h5></li>
					<li><h5>채널타입 : </h5></li>
				</ul>
				<ul class="color6">
					<li><h5>{{data.productName}}</h5></li>
					<li><h5>{{data.chTypeList}}</h5></li>
					<li><h5>{{data.msgKindName}}</h5></li>
				</ul>
				<div class="mt10">
					<input type="radio" name="smart" :value="data.productCode" :id="data.productCode" class="radioStyle" @change="fnSelectSmart($event)"><label :for="data.productCode"></label>
					<i class="fas fa-question-circle toolTip"></i>
				</div>
			</li>
		</ul>
	</div>
			

  <!-- 리스트 -->
  <div class="row">
    <div class="col-xs-12 consolMarginTop">
      <div class="of_h inline">
        <div class="float-right">
          <router-link :to="{ name: 'smartTemplateManage', params:{'productCodeP': productCode} }" tag="a" class="btnStyle2 borderGray" v-show="checkSmartProduct">템플릿 등록<i class="fal fa-arrow-to-bottom" ></i></router-link>
          <a @click="fnDelete" class="btnStyle2 borderGray" title="삭제">삭제 <i class="fal fa-arrow-to-bottom"></i></a>
          <a @click="fnExcelDownLoad" class="btnStyle2 borderGray" title="엑셀 다운로드">엑셀 다운로드 <i class="fal fa-arrow-to-bottom"></i></a>
        </div>
      </div>

      <!-- 15개씩 보기 -->
      <div class="of_h inline">
        <div class="float-left">전체 : <span class="color1"><strong>{{totCnt}}</strong></span>건
          <SelectLayer @fnSelected="fnSelected"></SelectLayer>
        </div>
      </div>
      <!-- //15개씩 보기 -->

      <div class="row">
        <div class="col-xs-12 consolMarginTop">
          <!-- table -->
          <table class="table_skin1 bt-000 tbl-striped">
            <colgroup>
	            <col style="width:5%">
				<col>
				<col style="width:12%">
				<col style="width:15%">
				<col style="width:20%">
				<col style="width:16%">
				<col style="width:11%">
				<col style="width:15%">
            </colgroup>
            <thead>
              <tr>
                <th class="text-center lc-1"><input type="checkbox" id="listCheck_all" class="boardCheckStyle" value="listCheck_all"><label for="listCheck_all"></label></th>
                <th class="text-center lc-1">No.</th>
                <th class="text-center lc-1">템플릿 ID</th>
                <th class="text-center lc-1">템플릿명</th>
                <th class="text-center lc-1">상품 유형</th>
                <th class="text-center lc-1">타 프로젝트 사용여부</th>
                <th class="text-center lc-1">등록자</th>
                <th class="text-center lc-1 end">등록일자</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(data, idx) in datas" :key="data.row_num">
                <td class="text-center"><input type="checkbox" :id="'listCheck_'+idx" class="boardCheckStyle" :value="data.tmpltCode" v-model="listChkBox">
                <label :for="'listCheck_'+idx"></label></td>
                <td>{{ idx + 1 }}</td>
                <td class="text-center"><router-link :to="{ name: 'smartTemplateManage', params: {'tmpltCodeP': data.tmpltCode }}">{{data.tmpltCode}}</router-link> </td>
                <td class="text-center">{{data.tmpltTitle}}</td>
                <td class="text-center">{{data.productName}}</td>
                <td class="text-center">{{data.otherProjectUseYn}}</td>
                <td class="text-center">{{data.regId}}</td>
                <td class="text-center end">{{data.regDt}}</td>
              </tr>
              <tr v-if="datas.length == 0">
                  <td class="text-center" colspan="8">검색된 내용이 없습니다.</td>
              </tr>
            </tbody>
          </table>
          <!-- //table -->
        </div>                  
      </div>
    </div>
  </div>
  <!-- pagination -->
  <div id="pageContent">
      <PageLayer @fnClick="fnSearch" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
  </div>
</div>
</template>


<script>
import smartTemplateApi from '../service/smartTemplateApi'
import PageLayer from '@/components/PageLayer.vue';
import SelectLayer from '@/components/SelectLayer.vue';
import Calendar from "@/components/Calendar.vue";
import tokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  components: {
    SelectLayer,
    PageLayer,
    Calendar
  },
  props: {
    searchData : {
      type: Object,
      require: false,
      default: function() {
        return {
          'searchCondi' : 'templateName',
          'searchText' : '',
          'searchStartDate' : this.$gfnCommonUtils.getCurretDate(),
          'searchEndDate' : this.$gfnCommonUtils.getCurretDate()
        }
      }
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '통합 템플릿 리스트';
      }
    },
  },
  data() {
    return {
      listAllChecked: false,
      listChkBox: [],
      listSize : 10,  // select 박스 value (출력 갯수 이벤트)
      pageNo : 1,  // 현재 페이징 위치
      totCnt : 0,  //전체 리스트 수
      offset : 0, //페이지 시작점
      searchDateInterval: 7,
      datas: [],
      checkSmartProduct: false, //스마트 템플릿 등록버튼 Show여부
      products: [],
      productCode: '',
    }
    
        
  },
  mounted() {
    this.fnSetIntervalSearchDate(this.searchDateInterval);
    this.fnSmartProductList();
    this.fnSearch();
  },
  methods: {
    //검색일자변경
    fnSetIntervalSearchDate(interval){
      this.searchDateInterval = interval;
      this.searchData.searchEndDate = this.$gfnCommonUtils.getCurretDate();
      this.searchData.searchStartDate = this.$gfnCommonUtils.strDateAddDay(this.searchData.searchEndDate, -this.searchDateInterval);
    },
    fnUpdateStartDate(sltDate) {
      this.searchData.searchStartDate = sltDate;
    },
    fnUpdateEndDate(sltDate) {
      this.searchData.searchEndDate = sltDate;
    },

    //템플릿 삭제
    fnDelete(){
      //유효성 검사
      if(this.listChkBox == null || this.listChkBox.length == 0){
        confirm.fnAlert(this.componentsTitle, '삭제할 항목을 선택해주세요.');
        return;
      }

      eventBus.$on('callbackEventBus', this.fnProcDeleteSmartTemplate);
      confirm.fnConfirm(this.componentsTitle, "선택한 템플릿을 삭제하시겠습니까?", "확인");
    },

    //통합 템플릿 삭제 처리
    async fnProcDeleteSmartTemplate(){
      var params = {'tmpltCodes' : this.listChkBox};
      await smartTemplateApi.deleteSmartTemplate(params).then(response =>{
        var result = response.data;
        if(result.success) {
          confirm.fnAlert(this.componentsTitle, '삭제되었습니다.');
          this.listChkBox = [];
          this.fnSearch();
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },


    //엑셀 다운로드
    fnExcelDownLoad(){
      var params = this.searchData;
      smartTemplateApi.excelDownloadSmartTemplate(params);
    },


    // 검색
    async fnSmartProductList() {
      var params = Object.assign({}, this.searchData);
      //params.loginId = tokenSvc.getToken().principal.userId;
      //params.roleCd = tokenSvc.getToken().principal.roleCd;

     await smartTemplateApi.selectSmartProductList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.products = result.data;
        } else {
          alert("스마트발송 상품 리스트 조회 : "+result.message);
        }
      });
    },
    
    fnSelectSmart(event){
        var productCodeTemp = event.target.value;
        this.productCode = productCodeTemp;
        this.checkSmartProduct = true;//스마트 템플릿 등록버튼 Show
        console.log("11>>>>>>>>>>>>>>>fnSelectSmart productCodeTemp : "+productCodeTemp);
    	this.fnSelectSmartTemplateList(productCodeTemp);
    },
    
    // 검색
    async fnSelectSmartTemplateList(productCode) {

      //유효성 검사
      if(this.searchData.searchStartDate && this.searchData.searchEndDate){
        if(this.searchData.searchStartDate.replace(/[^0-9]/g, '') > this.searchData.searchEndDate.replace(/[^0-9]/g, '')){
          alert('시작일은 종료일보다 클 수 없습니다.');
          return false;
        }
      }
          
      var params = Object.assign({}, this.searchData);
      
      if(!this.isEmpty(productCode)){
      	params.productCode = productCode;
      }else{
      	params.productCode = "XXXXXXXX";
      }
      params.pageNo = this.pageNo;
      params.listSize = this.listSize;

      params.loginId = tokenSvc.getToken().principal.userId;
      params.roleCd = tokenSvc.getToken().principal.roleCd
      await smartTemplateApi.selectSmartTemplateList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.datas = result.data;
          this.totCnt = result.pageInfo.totCnt;
          this.offset = result.pageInfo.offset;
        } else {
          alert("스마트발송 템플릿 리스트 조회 : "+result.message);
        }
      });
    },
 
    //리스트 전체 체크박스
    fnListChkAll(){
      var vm = this;
      if(this.listAllChecked){
        this.datas.forEach(function(data){
          vm.listChkBox.push(data.tmpltCode);
        });
      } else {
        this.listChkBox = []
      }
    },

    // select 박스 선택시 리스트 재출력
    fnSelected(listSize) {
      this.listSize = Number(listSize);
      this.$refs.updatePaging.fnAllDecrease();
    },
    fnSearch(pageNum) {
      this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNum, '1'))*1;
      this.fnSelectSmartTemplateList();
    },
    
    isEmpty(str){
        if(typeof str == "undefined" || str == null || str == "")
            return true;
        else
            return false ;
    }
  }
}
</script>