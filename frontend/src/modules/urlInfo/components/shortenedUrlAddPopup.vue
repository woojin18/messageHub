<template>
  <!-- shortened_URL_add Modal -->
  <div class="modal fade modalStyle" id="shortened_URL_add" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          <h2>단축 URL+ 생성</h2>
          <hr/>
          <div class="of_h consolMarginTop">
            <div class="float-left" style="width: 25%"><h5>URL제목*</h5></div>
            <div class="float-right" style="width: 75%">
              <input 
                type="text" 
                class="inputStyle float-right" 
                title="URL제목 입력란"
                v-model="title"
                ref="title"
              />
            </div>
          </div>
          <div class="clear consolMarginTop">
            <div class="float-left" style="width: 25%">
              <h5>
                원본 URL*
                <i class="fas fa-question-circle toolTip ml5">
                  <span class="toolTipText" style="width: 250px">메시지에 단축URL+를 통해 접속되는 실제 URL 정보</span>
                </i>
              </h5>
            </div>
            <div class="float-right" style="width: 75%">
              <input 
                type="text" 
                class="inputStyle float-right" 
                title="원본 URL 입력란"
                v-model="orgUrl"
                ref="orgUrl"
              />
            </div>
          </div>
          <div class="of_h consolMarginTop">
            <div class="float-left" style="width: 25%"><h5>만료일*</h5></div>
            <div class="float-right" style="width: 75%">
              <!-- <input 
                type="text" 
                class="datepicker inputStyle maxWidth160" 
                title="만료일 입력란"
                v-model="expDt"
                ref="expDt"
              /> -->
              <Calendar
                @update-date="(pickDate) => { expDt = pickDate }"
                calendarId="expDt2"
                classProps="datepicker inputStyle maxWidth160"
                :initDate="expDt"
                :customOption="{ minDate: '-0y' }"
              ></Calendar>
            </div>
          </div>
          <div class="of_h mt20 text-center">
            <a 
              @click="btnCreate"
              class="btnStyle2 backBlack" 
              title="단축 URL 생성"
            >단축 URL+ 생성</a>
          </div>
          <div class="clear consolMarginTop mt30">
            <div class="float-left" style="width: 25%">
              <h5>
                단축 URL+
                <i class="fas fa-question-circle toolTip ml5">
                  <span class="toolTipText" style="width: 250px">메시지 길이의 제한이 있어 URL을 단축하여 제공합니다.</span>
                </i>
              </h5>
            </div>
            <div class="float-right" style="width: 75%">
              <input 
                type="text" 
                class="inputStyle float-right" 
                title="단축 URL+ 입력란"
                v-model="shortendUrl"
                readonly
              />
            </div>
          </div>
          <div class="text-center mt20">
            <a 
              class="btnStyle2 backWhite" 
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
import Calendar from '@/components/Calendar'

export default {
  name: 'shortenedUrlAddPopup',
  components: {
    Calendar,
  },
  props: {},
  computed: {
    shortendUrl() {
      if(this.urlId)
        return `https://url.${document.location.hostname}/#URL{${this.urlId}}`
      else
        return ''
    },
  },
  data() {
    return {
      title: '',
      orgUrl: '',
      expDt: this.$gfnCommonUtils.getCurretDate(),
      urlId: null,
    };
  },
  methods: {
    initVal() {
      this.title = ''
      this.orgUrl = ''
      this.expDt = this.$gfnCommonUtils.getCurretDate()
      this.urlId = null
    },
    btnCreate() {
      // 유효성 체크
      if(!this.checkValidation())
        return

      eventBus.$on('callbackEventBus', () => {
        let params = {}
        params.title  = this.title
        params.orgUrl = this.orgUrl
        params.expDt  = this.expDt

        urlInfoApi.insertUrlInfo(params).then((res) => {
          const result = res.data;      
          if (result.success) {
            this.urlId = result.data.urlId
            confirm.fnAlert(this.componentsTitle, `단축URL 생성을 완료하였습니다.\n생성된 단축 URL을 [선택]하면 메시지의 마지막에 단축 URL이 입력됩니다.\n또한 통계 메뉴에서 단축URL의 클릭률을 확인하실 수 있습니다.`)

            const filterList = this.$parent.$children.filter((item)=>{
              return item.selectUrlInfoList !== undefined
            })

            if(filterList.length > 0)
              filterList[0].selectUrlInfoList()

              jQuery('#shortened_URL_add').modal('hide')
          } else {
            confirm.fnAlert(this.componentsTitle, result.message);
          }
        })
      })

      confirm.fnConfirm('단축URL 생성', '단축URL을 생성하시겠습니까?', '확인')
    },
    // 유효성 체크
    checkValidation() {
      if (this.$gfnCommonUtils.isEmpty(this.title)) {
        confirm.fnAlert(this.componentsTitle, 'URL제목을 입력해주세요.')
        this.$refs.title.focus()
        return false
      } else if (this.$gfnCommonUtils.isEmpty(this.orgUrl)) {
        confirm.fnAlert(this.componentsTitle, '원본 URL을 입력해주세요.')
        this.$refs.orgUrl.focus()
        return false
      } else if (this.$gfnCommonUtils.isEmpty(this.expDt)) {
        confirm.fnAlert(this.componentsTitle, '만료일을 입력해주세요.')
        this.$refs.expDt.focus()
        return false
      } else
        return true
    },
  },
};
</script>

<style>
</style>