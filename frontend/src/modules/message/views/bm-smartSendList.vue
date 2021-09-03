<template>

  <div>
    <div class="contentHeader">
      <h2>스마트발송</h2>
    </div>

    <!-- 본문 -->
    <div class="border-line2 pd20">
      <p class="color4">스마트 발송은 템플릿을 통해서만 발송가능 합니다.<br>완료상태의 템플릿일 경우만 스마트메시지 발송 가능합니다.(스마트 템플릿관리를 클릭하여 템플릿을 등록해 주십시오.)<br>아래 정보는 완료상태의 템플릿만 조회됩니다.</p>
    </div>

    <div class="row mt15">
      <div class="col-xs-12">
        <div class="menuBox">
          <div>
            <h4 class="inline-block" style="width:8%">검색조건</h4>
            <select v-model="searchData.searchCondi" class="selectStyle2" style="width:15%" title="검색조건">
                <option value="templateName">템플릿명</option>
                <!-- <option value="templateChannel">템플릿채널</option> -->
            </select>
            <input type="text" class="inputStyle vertical-middle ml10" style="width:42%" title="검색조건 입력란" v-model="searchData.searchText" @keypress.enter="fnPageNoResetSearch">
          </div>
          <div class="of_h">
            <div class="inline-block" style="width:8%"><h4 class="font-normal mt15">메시지구분</h4></div>
            <div class="inline-block" style="width:91%">
              <div class="consolCheck consolMarginTop">
                <input type="checkbox" id="otherUse_all" class="checkStyle2" @change="fnSearchMsgKindCdChkAll" v-model="msgKindCdAllSelected">
                <label for="otherUse_all" class="mr30">전체</label>
                <input type="checkbox" id="searchMsgKindCd_A" class="checkStyle2" value="A" v-model="searchData.searchMsgKindCd">
                <label for="searchMsgKindCd_A" class="mr30">광고성</label>
                <input type="checkbox" id="searchMsgKindCd_I" class="checkStyle2" value="I" v-model="searchData.searchMsgKindCd">
                <label for="searchMsgKindCd_I" class="mr30">정보성</label>
              </div>
            </div>
          </div>
          <div class="of_h">
            <div class="inline-block" style="width:8%"><h4 class="font-normal mt20">템플릿채널</h4></div>
            <div class="inline-block" style="width:91%">
              <div class="consolCheck consolMarginTop">
                <input type="checkbox" id="searchMsgCh_all" class="checkStyle2" @change="fnSearchMsgChChkAll" v-model="msgChAllSelected">
                <label for="searchMsgCh_all" class="mr30">전체</label>
                <input type="checkbox" id="searchMsgCh_PUSH" class="checkStyle2" value="PUSH" v-model="searchData.searchMsgCh">
                <label for="searchMsgCh_PUSH" class="mr30">푸시</label>
                <input type="checkbox" id="searchMsgCh_SMS" class="checkStyle2" value="SMS" v-model="searchData.searchMsgCh">
                <label for="searchMsgCh_SMS" class="mr30">SMS</label>
                <input type="checkbox" id="searchMsgCh_MMS" class="checkStyle2" value="MMS" v-model="searchData.searchMsgCh">
                <label for="searchMsgCh_MMS" class="mr30">MMS</label>
                <input type="checkbox" id="searchMsgCh_FRIENDTALK" class="checkStyle2" value="FRIENDTALK" v-model="searchData.searchMsgCh">
                <label for="searchMsgCh_FRIENDTALK" class="mr30">친구톡</label>
                <input type="checkbox" id="searchMsgCh_ALIMTALK" class="checkStyle2" value="ALIMTALK" v-model="searchData.searchMsgCh">
                <label for="searchMsgCh_ALIMTALK" class="mr30">알림톡</label>
                <input type="checkbox" id="searchMsgCh_RCS" class="checkStyle2" value="RCS" v-model="searchData.searchMsgCh">
                <label for="searchMsgCh_RCS" class="mr30">RCS</label>
              </div>
            </div>
          </div>

          <div class="consolMarginTop">
            <h4 class="inline-block" style="width:8%">등록일자</h4>
            <Calendar @update-date="fnUpdateStartDate" calendarId="searchStartDate" classProps="datepicker inputStyle maxWidth160" :initDate="searchData.searchStartDate" ></Calendar>
            <span style="padding:0 11px">~</span>
            <Calendar @update-date="fnUpdateEndDate" calendarId="searchEndDate" classProps="datepicker inputStyle maxWidth160" :initDate="searchData.searchEndDate"></Calendar>
            <ul class="tab_s2 ml20">
              <li :class="this.searchDateInterval==0 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(0);" title="오늘 날짜 등록일자 검색">오늘</a></li>
              <li :class="this.searchDateInterval==7 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(7);" title="1주일 등록일자 검색">1주일</a></li>
              <li :class="this.searchDateInterval==15 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(15);" title="15일 등록일자 검색">15일</a></li>
              <li :class="this.searchDateInterval==30 ? 'active' : ''"><a @click="fnSetIntervalSearchDate(30);" title="1개월 등록일자 검색">1개월</a></li>
            </ul>
            <a href="#" @click.prevent="fnPageNoResetSearch()" class="btnStyle1 float-right" title="통합발송 검색">검색</a>
          </div>
        </div>
      </div>
    </div>
    <div>
      <h4>스마트발송 상품</h4>
      <ul class="of_h">
        <li v-for="(info, idx) in prdDatas" :key="idx" class="border-line2 of_h float-left pd20" style="width:252px">
          <ul class="color6 float-left mr10" style="font-weight:700">
            <li><h5>상품명</h5></li>
            <li><h5>채널구성</h5></li>
            <li><h5>채널타입</h5></li>
          </ul>
          <ul class="color6">
            <li><h5>{{info.productName}}</h5></li>
            <li><h5>{{info.smartChProduct | getChNm}}</h5></li>
            <li><h5>{{info.smartChProduct | getMsgKind}}</h5></li>
          </ul>
          <div class="mt10">
            <input :id="info.productCode" name="searchProductCode" type="radio" :value="info.productCode" class="radioStyle" v-model="searchData.searchProductCode">
            <label :for="info.productCode"></label>
          </div>
        </li>
      </ul>
    </div>
    
    <div class="row">
      <div class="col-xs-12 consolMarginTop">

        <div class="of_h inline">
          <div class="float-right">
            <router-link :to="{ path: '/uc/smartTemplate' }" tag="a" class="btnStyle2 borderGray"  activity="READ">스마트 발송 템플릿 관리</router-link>
            <a href="#" @click.prevent="fnMoveSmartSend" class="btnStyle2 backBlack ml10" title="스마트 메시지 발송">스마트 메시지 발송</a>
          </div>
        </div>

        <!-- 15개씩 보기 -->
        <div class="of_h inline">
          <div class="float-left">전체 : <span class="color1"><strong>{{totCnt}}</strong></span>건
            <SelectLayer @fnSelected="fnSelected" classProps="selectStyle2 width120 ml20"></SelectLayer>
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
                  <th class="text-center lc-1"></th>
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
                <tr v-for="(data, idx) in datas" :key="idx">
                  <td class="text-center">
                    <div class="boardCheck">
                      <input type="radio" :id="'listCheck_'+idx" class="radioStyle" :value="data.tmpltCode" v-model="chkBox"> 
                      <label :for="'listCheck_'+idx"></label>
                    </div>
                  </td>
                  <td class="text-center">{{totCnt-offset-data.rowNum+1}}</td>
                  <td class="text-center">
                    <u><router-link :to="{ name: 'smartSendMain', params: {'tmpltCodeP': data.tmpltCode, tmpltType: 'S', componentsTitle: '스마트발송'}}">{{data.tmpltCode}}</router-link></u>
                  </td>
                  <td class="text-center">{{data.tmpltTitle | unescapeXss}}</td>
                  <td class="text-center">{{data.msgKindName}}</td>
                  <td class="text-center">{{data.otherProjectUseYn}}</td>
                  <td class="text-center">{{data.regNm}}</td>
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
    <!-- //pagination -->
  </div>

</template>

<script>
import Calendar from "@/components/Calendar.vue";
import PageLayer from '@/components/PageLayer.vue';
import SelectLayer from '@/components/SelectLayer.vue';

import messageApi from "@/modules/message/service/messageApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: "smartSendList",
  components: {
    SelectLayer,
    Calendar,
    PageLayer
  },
  props: {
    searchData : {
      type: Object,
      require: false,
      default: function() {
        return {
          'searchTmpltType' : 'S',
          'searchCondi' : 'templateName',
          'searchText' : '',
          'searchMsgKindCd' : [],
          'searchMsgCh' : [],
          'searchProductCode' : '',
          'searchStartDate' : this.$gfnCommonUtils.getCurretDate(),
          'searchEndDate' : this.$gfnCommonUtils.getCurretDate()
        }
      }
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '스마트 발송 리스트';
      }
    },
  },
  data() {
    return {
      chInfo: {
        PUSH: "PUSH",
        SMS: "SMS",
        MMS: "MMS",
        FRIENDTALK: "친구톡",
        ALIMTALK: "알림톡",
        RCS: "RCS",
      },
      listAllChecked: false,
      listChkBox: [],
      listSize : 10,  // select 박스 value (출력 갯수 이벤트)
      pageNo : 1,  // 현재 페이징 위치
      totCnt : 0,  //전체 리스트 수
      offset : 0, //페이지 시작점
      searchDateInterval: 7,
      datas: [],
      prdDatas: [],
      msgKindCdAllSelected: true,
      msgChAllSelected: true,
      chkBox: ''
    }
  },
  mounted() {
    this.fnExistApiKey();
    this.fnSetIntervalSearchDate(this.searchDateInterval);
    this.fnSearchMsgKindCdChkAll();
    this.fnSearchMsgChChkAll();
    this.fnSelectSmartChProductList();
    this.fnPageNoResetSearch();
  },
  filters:{
    getMsgKind(val){
      if(!val) return val;
      val = val.toString();

      let rtn = '';
      const map = JSON.parse(val);
      
      if(map['msg_kind']){
        rtn = (map['msg_kind'] == 'A' ? '광고성' : '정보성');
      }
      return rtn;
    },
    getChNm(val){
      if(!val) return val;
      val = val.toString();

      let rtn = '';
      const map = JSON.parse(val);

      if(map['msg_ch']){
        const msgChList = map['msg_ch'];
        msgChList.forEach(e => {
          if(e.chType == 'PUSH'){
            rtn += ',푸시';
          } else if(e.chType == 'SMS/MMS'){
            if(e.spec == 'mms'){
              rtn += ',MMS';
            } else {
              rtn += ',SMS';
            }
          } else if(e.chType == 'KKO'){
            if(e.spec == 'alimtalk'){
              rtn += ',알림톡';
            } else {
              rtn += ',친구톡';
            }
          } else if(e.chType == 'RCS'){
            rtn += ',RCS';
          }
        });
      }
      return (rtn.indexOf(',') == 0 ? rtn.substring(1) : rtn);
    }
  },
  methods: {
    fnMoveSmartSend(){
      if(this.chkBox == null || this.chkBox == ''){
        confirm.fnAlert(this.componentsTitle, '전송할 항목을 선택해주세요.');
        return;
      }
      this.$router.push({name: 'smartSendMain', params: {'tmpltCodeP': this.chkBox, tmpltType: 'S', componentsTitle: '스마트발송'}});
    },
    async fnSelectSmartChProductList(){
      var params = {};
      await messageApi.selectSmartChProductList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          this.prdDatas = Object.assign([], result.data);
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    async fnExistApiKey(){
      let params = {};
      await messageApi.selectApiKey(params).then(response =>{
        const result = response.data;
        if(result.success) {
          if(this.$gfnCommonUtils.isEmpty(result.data)){
            confirm.fnAlert(this.componentsTitle, '해당 프로젝트의 API 키가 존재하지 않습니다.\n메시지 발송하실 수 없습니다.');
          }
        }
      });
    },
    //검색일자변경
    fnSetIntervalSearchDate(interval){
      this.searchDateInterval = interval;
      this.searchData.searchEndDate = this.$gfnCommonUtils.getCurretDate();
      this.searchData.searchStartDate = this.$gfnCommonUtils.strDateAddDay(this.searchData.searchEndDate, -this.searchDateInterval);
    },
    fnSearchMsgKindCdChkAll(){
      if(this.msgKindCdAllSelected){
        this.searchData.searchMsgKindCd = ['A', 'I'];
      } else {
        this.searchData.searchMsgKindCd = [];
      }
    },
    fnSearchMsgChChkAll(){
      if(this.msgChAllSelected){
        this.searchData.searchMsgCh = ['PUSH', 'SMS', 'MMS', 'FRIENDTALK', 'ALIMTALK', 'RCS'];
      } else {
        this.searchData.searchMsgCh = [];
      }
    },
    fnUpdateStartDate(sltDate) {
      this.searchData.searchStartDate = sltDate;
    },
    fnUpdateEndDate(sltDate) {
      this.searchData.searchEndDate = sltDate;
    },


    async fnSelectSmartTmpltList(){
      //유효성 검사
      if(this.searchData.searchStartDate && this.searchData.searchEndDate){
        if(this.searchData.searchStartDate.replace(/[^0-9]/g, '') > this.searchData.searchEndDate.replace(/[^0-9]/g, '')){
          confirm.fnAlert(this.componentsTitle, '시작일은 종료일보다 클 수 없습니다.');
          return false;
        }
      }
      
      var params = Object.assign({}, this.searchData);
      params.pageNo = this.pageNo;
      params.listSize = this.listSize;
      await messageApi.selectSmartTmpltList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          this.chkBox = ''
          this.datas = result.data;
          this.totCnt = result.pageInfo.totCnt;
          this.offset = result.pageInfo.offset;
          this.datas = Object.assign([], result.data);
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnSelected(listSize) {
      this.listSize = Number(listSize);
      this.$refs.updatePaging.fnAllDecrease();
    },
    fnPageNoResetSearch(){
      this.$refs.updatePaging.fnAllDecrease();
    },
    fnSearch(pageNum) {
      this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNum, '1'))*1;
      this.fnSelectSmartTmpltList();
    }
  }
}
</script>