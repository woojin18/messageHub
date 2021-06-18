
<template>
  <div>
    <CsMenuSubHeader></CsMenuSubHeader>

    <div id="contentWrap">
      <div class="sub03_conWrap">
        <div class="con01 wow animated fadeInUp">
          <section>

            <div class="boardTitle">
              <p class="titleText">공지사항</p>
              <p class="subText">공지 사항 및 신규 서비스에 관련된 소식을 전해드립니다.</p>
            </div>
            
            <table class="user_table_skin2 bt-000 NoticeTable">
              <colgroup>
                <col style="width:5%">
                <col>
                <col style="width:15%">
              </colgroup>
              <thead>
                <tr>
                  <th class="text-center">No.</th>
                  <th class="text-center">제목</th>
                  <th class="text-center end">날짜</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(contant, idx) in contants" :key="idx">
                  <td>{{totCnt-offset-contant.rownum+1}}</td>
                  <td class="text-left">
                    <a href="user_sub03_2_view.html">
                      <span v-if="!$gfnCommonUtils.isEmpty(contant.noticeTypeCdName)" class="new2">{{contant.noticeTypeCdName}}</span>
                      <router-link :to="{ name: 'noticeDetail', params: { noticeId: contant.noticeId }}">{{contant.title}}</router-link>
                    </a>
                  </td>
                  <td class="end">{{contant.regDt}}</td>
                </tr>
              </tbody>
            </table>
            
            <!-- pagination -->
            <div id="pageContent">
              <PageLayer @fnClick="fnSearch" :listTotalCnt="totCnt" :selected="listSize" :pageNum="pageNo" ref="updatePaging"></PageLayer>
            </div>
            <!-- //pagination -->
          </section>  
        </div>
      </div>
    </div>
    <QuickRight></QuickRight>
  </div>
</template>

<script>
import PageLayer from '@/components/PageLayer.vue';
import QuickRight from "@/modules/main/components/bc-quickRight.vue";
import CsMenuSubHeader from "@/modules/customer/components/bc-csMenuSubHeader.vue";

import customereApi from "@/modules/customer/service/customerApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: 'notice',
  components : {
    QuickRight,
    PageLayer,
    CsMenuSubHeader
  },
  props: {
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '공지사항';
      }
    },
  },
  data() {
    return {
      contants: [],
      listSize : 10,  // 한페이지 출력 리스트 수
      pageNo : 1,  // 현재 페이징 위치
      totCnt : 0,  //전체 리스트 수
      offset : 0, //페이지 시작점
    }
  },
  mounted() {
    this.fnSelectNoticeList();
  },
  methods: {
    async fnSelectNoticeList(){
      const params = {
        pageNo: this.pageNo,
        listSize:  this.listSize
      };
      await customereApi.selectNoticeList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          this.contants = result.data;
          this.totCnt = result.pageInfo.totCnt;
          this.offset = result.pageInfo.offset;
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnSearch(pageNo) {
      this.pageNo = (this.$gfnCommonUtils.defaultIfEmpty(pageNo, '1'))*1;
      this.fnSelectNoticeList();
    },
  }
}
</script>