<template>
    <div>	
        <article>
            <div class="contentHeader">
                <h2>수신자 관리</h2>				
            </div>

            <!-- 본문 -->
            <div class="row">
                <div class="col-xs-12">
                    <div class="menuBox">						
                        <div class="of_h">
                            <h4 class="inline-block" style="width:6%">알람그룹 명</h4>
                            <input type="text" class="inputStyle" style="width:14%" title="알람그룹 명 입력란" v-model="params.srcAlarmGroup">
                            <h4 class="inline-block ml20" style="width:6%">수신자명</h4>
                            <input type="text" class="inputStyle" style="width:14%" title="수신자명 입력란" v-model="params.srcRecipientName">
                            <h4 class="inline-block ml20" style="width:6%">휴대폰</h4>
                            <input type="text" class="inputStyle" style="width:14%" title="휴대폰 입력란" v-model="params.srcHpNumber">
                            <a @click="fnPageNoResetSearch" class="btnStyle1 float-right" title="검색">검색</a>
                        </div>						
                    </div>
                </div>
            </div>
            
            <div class="row">
                <div class="col-xs-12 mt30">
                    <div class="float-right mb20">						
                        <a @click="fnAdd" class="btnStyle2 backBlack" style="min-width:auto" title="그룹 수신자  추가">그룹 수신자 추가</a>&nbsp;
                        <a @click="fnDel" class="btnStyle2 borderGray" style="min-width:auto" title="그룹 수신자 삭제">그룹 수신자 삭제</a>
                    </div>
        <!-- 15개씩 보기 -->
        <div class="of_h inline">
          <div class="float-left">전체 : <span class="color1"><strong>{{totCnt}}</strong></span>건
            <SelectLayer @fnSelected="fnSelected" classProps="selectStyle2 width120 ml20"></SelectLayer>
          </div>
        </div>
        <!-- //15개씩 보기 -->
                    <table class="table_skin1" style="width:100%">
                        <caption>수신그룹명, 사용여부, 수신자수를 나타내는 표</caption>
                        <colgroup>
                            <col style="width:10%">
                            <col style="width:35%">
                            <col style="width:35%">
                            <col style="width:20%">
                            <col>
                        </colgroup>
                        <thead>
                            <tr>
                            <th class="text-center lc-1">No</th>
                            <th class="text-center lc-1">수신자명</th>
                            <th class="text-center lc-1">알랍그룹 명</th>
                            <th class="text-center lc-1 end">휴대폰</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(data, idx) in list" @click="fnSelect($event, data)">
                                <td class="text-center">{{totCnt-offset-data.rownum+1}}</td>
                                <td class="text-left"><a @click="fnEdit(idx)" class="linkStyle">{{data.recipientName}}</a></td>
                                <td class="text-left">{{data.receptGroup}}</td>
                                <td class="text-center end">{{data.hpNumber}}</td>
                            </tr>	
                            <tr v-if="list.length == 0">
                              <td class="text-center" colspan="4">검색된 내용이 없습니다.</td>
                            </tr>
                        </tbody>
                    </table>

                    <!-- pagination -->
                    <div id="pageContent">
                    <PageLayer @fnClick="fnSearch" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
                    </div>
                </div>				
            </div>

            <!-- //본문 -->

        </article>
        <recipientPopup :popupTitle="popupTitle" :rowData="rowData" :popReset="popReset"/>
    </div>
</template>

<script>
import recipientApi from "@/modules/alarm/service/recipientApi"
import recipientPopup from "@/modules/alarm/components/bp-recipient"
import PageLayer from '@/components/PageLayer.vue';
import SelectLayer from '@/components/SelectLayer.vue';
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  data() {
    return {
      params: {
        srcAlarmGroup : null,
        srcRecipientName : null,
        srcHpNumber : null
      },
      popupTitle: '등록',
      listSize : 10,  // select 박스 value (출력 갯수 이벤트)
      pageNo : 1,  // 현재 페이징 위치
      totCnt : 0,  //전체 리스트 수
      offset : 0, //페이지 시작점
      list: [],
      rowData: {},
      popReset: 0
    }
  },
  components: {
    SelectLayer,
    PageLayer,
    recipientPopup
  },
  mounted() {
    this.fnPageNoResetSearch();
  },
  methods: {
    fnSelected(listSize) {
      this.listSize = Number(listSize);
      this.$refs.updatePaging.fnAllDecrease();
    },
    fnPageNoResetSearch(){
      this.$refs.updatePaging.fnAllDecrease();
    },
    async fnSearch(pageNo) {
      this.rowData = {}
      jQuery('tbody td').removeClass('bgColor_sky')
      this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNo, '1'))*1;
      var params = Object.assign({}, this.params)
      params.pageNo = this.pageNo;
      params.listSize = this.listSize;
      await recipientApi.selectRecipientList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.list = result.data;
          this.totCnt = result.pageInfo.totCnt;
          this.offset = result.pageInfo.offset;
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnSelect(event, data) {
      this.rowData = data
      jQuery('tbody td').removeClass('bgColor_sky')
      jQuery(event.currentTarget).find('td').addClass('bgColor_sky')
    },
    fnAdd() {
        this.popupTitle = '등록'
        this.rowData = {}
        this.popReset = this.popReset + 1
        jQuery("#recipientPopup").modal("show")
    },
    fnEdit(idx) {
        this.popupTitle = '수정'
        this.rowData = this.list[idx]
        this.popReset = this.popReset + 1
        jQuery("#recipientPopup").modal("show")
    },
    fnDel() {
      if(this.rowData.recipientId == null) {
        confirm.fnAlert("삭제할 항목을 선택해주세요.", "")
        return false
      }
      eventBus.$on('callbackEventBus', this.fnDelete)
      confirm.fnConfirm( "삭제 하시겠습니까?", "", "삭제")
    },
    fnDelete() {
      var params = Object.assign({}, this.rowData)
      recipientApi.delRecipient(params).then(response =>{
				var result = response.data;
      	if(result.success) {
					confirm.fnAlert("삭제 되었습니다.", "");
					this.fnPageNoResetSearch();
				}else{
					confirm.fnAlert(result.message, "");
				}
      });
    }
  }
}
</script>

