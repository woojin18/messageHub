<template>
  <!-- shortened_URL Modal -->
  <div class="modal fade modalStyle" id="shortened_URL" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width: 890px">
      <div class="modal-content">
        <div class="modal-body">
          <h2>단축 URL+</h2>
          <hr />
          <div class="border-line2 bgColor_f7 mt10 pd10">
            <ul class="color5">
              <li>• 메시지에 링크된 단축URL+를 고객에게 보내고 고객이 클릭한 통계를 수치화하여 보여줍니다.</li>
              <li>• 단축 URL+를 생성한 후 [선택] 버튼을 클릭하면 메시지 내용에 단축 URL+가 입력됩니다.</li>
              <li>• 단축 URL+ 복사 버튼을 클릭하면 메시지 또는 버튼에 URL을 쉽게 붙일 수 있습니다.</li>
            </ul>
          </div>

          <div class="menuBox mt10">
            <h4 class="inline-block" style="width: 8%">제목</h4>
            <input
              type="text"
              class="inputStyle vertical-middle ml10"
              style="width: 42%"
              title="제목 입력란"
              v-model="title"
              @keypress.enter.prevent="selectUrlInfoList"
            />
            <a 
              class="btnStyle1 float-right" 
              title="통합발송 검색" 
              @click="selectUrlInfoList"
            >검색</a>
          </div>

          <div class="of_h mt20">
            <a
              class="float-right btnStyle1 backBlack"
              data-toggle="modal"
              data-target="#shortened_URL_add"
              title="단축 URL 생성"
              @click="initPop()"
            >단축 URL+ 생성</a>
          </div>

          <div class="row consolMarginTop">
            <div class="col-xs-12">
              <!-- table -->
              <table class="table_skin1" style="width: 100%">
                <caption>
                  단축 URL의 제목, 단축URL ID, 원본URL, 등록일, 만료일, 관리를 제공하는 표
                </caption>
                <colgroup>
                  <col style="" />
                  <col style="width: 72px" />
                  <col style="" />
                  <col style="" />
                  <col style="" />
                  <col style="" />
                  <col style="width: 230px" />
                </colgroup>
                <thead>
                  <tr>
                    <th class="text-center lc-1">NO</th>
                    <th class="text-center lc-1">제목</th>
                    <th class="text-center lc-1">단축URL+ ID</th>
                    <th class="text-center lc-1">원본URL</th>
                    <th class="text-center lc-1">등록일</th>
                    <th class="text-center lc-1">만료일</th>
                    <th class="text-center lc-1 end">관리</th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="(item, idx) in itemList"
                    :key="idx"
                  >
                    <td class="text-center">{{ item.rownum }}</td>  <!-- NO -->
                    <td class="text-center" style="word-break: break-all;">{{ item.title }}</td>   <!-- 제목 -->
                    <td class="text-center">{{ item.urlId }}</td>   <!-- URL ID -->
                    <td class="text-left" style="word-break: break-all;">{{ item.orgUrl }}</td>    <!-- 원본 URL -->
                    <td class="text-center">{{ $moment(item.regDt).format('YYYY.MM.DD') }}</td>   <!-- 등록일 -->
                    <td class="text-center">{{ $moment(item.expDt).format('YYYY.MM.DD') }}</td>   <!-- 만료일 -->
                    <td class="text-center end">
                      <a
                        @click="btnSelect(item.urlId)"
                        class="btnStyle1 backLightGray"
                        style="min-width: auto; width: 30%"
                        title="선택"
                      >선택</a>
                      <a
                        @click="btnCopy(item.urlId)"
                        class="btnStyle1 backLightGray"
                        style="min-width: auto; width: 30%;margin-left: 3px;"
                        title="복사"
                      >복사</a>
                      <a
                        @click="btnDelete(item.urlId)"
                        class="btnStyle1 backPink"
                        style="min-width: auto; width: 30%;margin-left: 3px;"
                        title="삭제"
                      >삭제</a>
                    </td>
                  </tr>
                </tbody>
              </table>
              <!-- //table -->
            </div>
          </div>

          <!-- pagination -->
          <div class="row mt10">
            <div class="col-xs-12">
              <div class="pagination1 text-center">
                <a
                  v-if="hasPrevPageList"
                  @click.prevent="goPage(pageInfo.pageNo - 10)"
                  href="#"
                  title="10페이지 이전 페이지로 이동"
                >
                  <i class="far fa-chevron-double-left"></i>
                </a>
                <a 
                  v-if="hasPrevPage"
                  @click.prevent="goPage(pageInfo.pageNo - 1)"
                  href="#" 
                  title="이전 페이지로 이동"
                >
                  <i class="far fa-chevron-left"></i>
                </a>
                <a
                  v-for="(item, idx) in pagingList"
                  :key="idx"
                  @click.prevent="goPage(item)"
                  href="#"
                  :title="`${item}페이지로 이동`"
                  class="number"
                  :class="item === pageInfo.pageNo ? 'active' : ''"
                  >{{ item }}</a
                >
                <a 
                  v-if="hasNextPage"
                  @click.prevent="goPage(pageInfo.pageNo + 1)"
                  href="#" 
                  title="다음 페이지로 이동"
                >
                  <i class="far fa-chevron-right"></i>
                </a>
                <a
                  v-if="hasNextPageList"
                  @click.prevent="goPage(pageInfo.pageNo - 10)"
                  href="#"
                  title="10페이지 다음 페이지로 이동"
                  ><i class="far fa-chevron-double-right"></i
                ></a>
              </div>
            </div>
          </div>
          <!-- //pagination -->

          <div class="text-center mt20">
            <a 
              id="btnClose"
              class="btnStyle1 backWhite" 
              data-dismiss="modal" 
              title="닫기"
            >닫기</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import confirm from '@/modules/commonUtil/service/confirm.js'
import urlInfoApi from '@/modules/urlInfo/service/urlInfoApi.js'
import {eventBus} from '@/modules/commonUtil/service/eventBus'

export default {
  name: 'shortenedUrlListPopup',
  components: {},
  props: {},
  computed: {
    shortendUrl() {
      let shortendUrl = 'url.msghub-qa.uplus.co.kr'
      if(location.host === 'msghub.uplus.co.kr')
        shortendUrl = 'm-hub.kr'

      if(this.urlId)
        return `https://${shortendUrl}/#URL{${this.urlId}}`
      else
        return ''
    },
    totPages() {
      return Math.ceil(this.pageInfo.totCnt / this.pageInfo.listSize)
    },
    hasPrevPage() {
      return this.pageInfo.pageNo-1 > 0
    },
    hasNextPage() {
      return this.pageInfo.pageNo+1 <= this.totPages
    },
    hasPrevPageList() {
      return this.pageInfo.pageNo - 10 > 0
    },
    hasNextPageList() {
      return this.pageInfo.pageNo + 10 <= this.totPages
    },
    pagingList() {
      let pagingList = []

      let idx = 1
      while(idx <= this.totPages) {
        pagingList.push(idx++)
      }

      return pagingList
    },
  },
  data() {
    return {
      pageInfo: {
        pageNo: 1,
        offset: 0,
        listSize: 10,
        totCnt: 0,
      },
      componentsTitle: '단축URL',
      title: '',
      urlId: '',
      itemList: [],
    };
  },
  mounted() {
    this.title = ''
    this.selectUrlInfoList()
  },
  methods: {
    async selectUrlInfoList() {
      let params = {};
      params.pageNo = this.pageInfo.pageNo;
      params.listSize = this.pageInfo.listSize;
      params.title = this.title;

      await urlInfoApi.selectUrlInfoList(params).then((response) => {
        const result = response.data;
        if (result.success) {
          this.itemList = result.data
          this.pageInfo = result.pageInfo
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    goPage(pageNo) {
      this.pageInfo.pageNo = pageNo
      this.selectUrlInfoList()
    },
    btnSelect(urlId) {
      this.urlId = urlId
      this.$emit('btnSelect', this.shortendUrl)
      confirm.fnAlert('', '메시지 내용 마지막에 [단축URL+] 가 입력 되었습니다.')
      jQuery("#btnClose").trigger("click")
    },
    btnCopy(urlId) {
      this.urlId = urlId
      
      var t = document.createElement('input')
      document.body.appendChild(t)
      t.value = this.shortendUrl
      t.select()
      t.focus()
      document.execCommand('copy')
      document.body.removeChild(t)

      confirm.fnAlert('', '단축URL+를 복사한 상태입니다. 메시지 또는 버튼 URL에 CTL+V 하십시오.')
    },
    btnDelete(urlId) {
      eventBus.$on('callbackEventBus', () => {
        let params = { urlId }

        urlInfoApi.deleteUrlInfo(params).then((res) => {
          const result = res.data
          if (result.success) {
            confirm.fnAlert(this.componentsTitle, '삭제 되었습니다.')
            this.title = ''
            this.selectUrlInfoList()
          } else {
            confirm.fnAlert(this.componentsTitle, result.message)
          }
        })
      })
      confirm.fnConfirm('단축URL 삭제', '삭제 시 클릭률 통계정보를 보실 수 없습니다. 삭제 하시겠습니까?', '확인')
    },
    initPop() {
      const filterList = this.$parent.$children.filter((item)=>{
        return item.initVal !== undefined
      })
      if(filterList.length > 0)
        filterList[0].initVal()
    },
  }
};
</script>

<style>
</style>