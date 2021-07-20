<template>
    <div>
      <article>
        <div class="contentHeader">
          <h2>{{alarmType.alarmTypeName}}</h2>				
        </div>

        <!-- 본문 -->
        <div class="row">
          <div class="col-xs-12">
            <div class="menuBox">						
              <p class="colo4"><i class="fal fa-info-circle"></i> {{alarmType.alarmDesc}}</p>
            </div>
          </div>
        </div>

        <div class="row mt20">
          <div class="col-xs-6 of_h consoleCon">								
            <div class="of_h">
              <h4 style="width:28%" class="inline-block">알람명 *</h4>
              <input type="text" class="inputStyle float-right" style="width:72%" title="알람명 입력란">
            </div>
            <div class="of_h">
              <h4 style="width:28%" class="inline-block">수집 요일/시간 *</h4>
              <div class="inline-block float-right" style="width:72%">
                <div class="of_h">
                  <div class="consolCheck" style="width:30%"><input type="checkbox" id="all" class="checkStyle2" value="all"><label for="all" class="vertical-sub color000">전체</label></div>
                  <div class="inline-block float-right" style="width:70%">
                    <select name="admin_sub13_1_1" class="selectStyle2" style="width:44%">
                      <option value="">선택</option>
                    </select>
                    <span> 시 ~</span>
                    <select name="admin_sub13_1_2" class="selectStyle2" style="width:44%">
                      <option value="">선택</option>
                    </select>
                    <span> 시</span>
                  </div>
                </div>
                <hr class="mt15 mb15">
                <div class="of_h">
                  <div class="consolCheck" style="width:30%"><input type="checkbox" id="Monday" class="checkStyle2" value="Monday"><label for="Monday" class="vertical-sub color000">월요일</label></div>
                  <div class="inline-block float-right" style="width:70%">
                    <select name="admin_sub13_1_3" class="selectStyle2" style="width:44%">
                      <option value="">선택</option>
                    </select>
                    <span> 시 ~</span>
                    <select name="admin_sub13_1_4" class="selectStyle2" style="width:44%">
                      <option value="">선택</option>
                    </select>
                    <span> 시</span>
                  </div>
                </div>
                <div class="of_h consolMarginTop">
                  <div class="consolCheck" style="width:30%"><input type="checkbox" id="Tuesday" class="checkStyle2" value="Tuesday"><label for="Tuesday" class="vertical-sub color000">화요일</label></div>
                  <div class="inline-block float-right" style="width:70%">
                    <select name="admin_sub13_1_5" class="selectStyle2" style="width:44%">
                      <option value="">선택</option>
                    </select>
                    <span> 시 ~</span>
                    <select name="admin_sub13_1_6" class="selectStyle2" style="width:44%">
                      <option value="">선택</option>
                    </select>
                    <span> 시</span>
                  </div>
                </div>
                <div class="of_h consolMarginTop">
                  <div class="consolCheck" style="width:30%"><input type="checkbox" id="Wednesday" class="checkStyle2" value="Wednesday"><label for="Wednesday" class="vertical-sub color000">수요일</label></div>
                  <div class="inline-block float-right" style="width:70%">
                    <select name="admin_sub13_1_7" class="selectStyle2" style="width:44%">
                      <option value="">선택</option>
                    </select>
                    <span> 시 ~</span>
                    <select name="admin_sub13_1_8" class="selectStyle2" style="width:44%">
                      <option value="">선택</option>
                    </select>
                    <span> 시</span>
                  </div>
                </div>
                <div class="of_h consolMarginTop">
                  <div class="consolCheck" style="width:30%"><input type="checkbox" id="Thursday" class="checkStyle2" value="Thursday"><label for="Thursday" class="vertical-sub color000">목요일</label></div>
                  <div class="inline-block float-right" style="width:70%">
                    <select name="admin_sub13_1_9" class="selectStyle2" style="width:44%">
                      <option value="">선택</option>
                    </select>
                    <span> 시 ~</span>
                    <select name="admin_sub13_1_10" class="selectStyle2" style="width:44%">
                      <option value="">선택</option>
                    </select>
                    <span> 시</span>
                  </div>
                </div>
                <div class="of_h consolMarginTop">
                  <div class="consolCheck" style="width:30%"><input type="checkbox" id="Friday" class="checkStyle2" value="Friday"><label for="Friday" class="vertical-sub color000">금요일</label></div>
                  <div class="inline-block float-right" style="width:70%">
                    <select name="admin_sub13_1_11" class="selectStyle2" style="width:44%">
                      <option value="">선택</option>
                    </select>
                    <span> 시 ~</span>
                    <select name="admin_sub13_1_12" class="selectStyle2" style="width:44%">
                      <option value="">선택</option>
                    </select>
                    <span> 시</span>
                  </div>
                </div>
                <div class="of_h consolMarginTop">
                  <div class="consolCheck" style="width:30%"><input type="checkbox" id="Saturday" class="checkStyle2" value="Saturday"><label for="Saturday" class="vertical-sub color000">토요일</label></div>
                  <div class="inline-block float-right" style="width:70%">
                    <select name="admin_sub13_1_13" class="selectStyle2" style="width:44%">
                      <option value="">선택</option>
                    </select>
                    <span> 시 ~</span>
                    <select name="admin_sub13_1_14" class="selectStyle2" style="width:44%">
                      <option value="">선택</option>
                    </select>
                    <span> 시</span>
                  </div>
                </div>
                <div class="of_h consolMarginTop">
                  <div class="consolCheck" style="width:30%"><input type="checkbox" id="Sunday" class="checkStyle2" value="Sunday"><label for="Sunday" class="vertical-sub color000">일요일</label></div>
                  <div class="inline-block float-right" style="width:70%">
                    <select name="admin_sub13_1_15" class="selectStyle2" style="width:44%">
                      <option value="">선택</option>
                    </select>
                    <span> 시 ~</span>
                    <select name="admin_sub13_1_16" class="selectStyle2" style="width:44%">
                      <option value="">선택</option>
                    </select>
                    <span> 시</span>
                  </div>
                </div>
              </div>
            </div>	
            <div class="of_h consolMarginTop">
              <h4 style="width:28%" class="inline-block">수집 최소 건수 *</h4>
              <div class="float-right" style="width:72%">
                <input type="text" class="inputStyle" style="width:20%" title="수집 최소 건수 입력란"> 건 이상
              </div>
            </div>
            <div class="of_h consolMarginTop">
              <h4 style="width:28%" class="inline-block">발송 성공율 *</h4>
              <div class="float-right" style="width:72%">
                <input type="text" class="inputStyle" style="width:20%" title="발송 성공율 입력란"> % 이하
              </div>
            </div>
            <div class="of_h consolMarginTop">
              <h4 style="width:28%" class="inline-block">일 한도 금액 *</h4>
              <div class="float-right" style="width:72%">
                <input type="text" class="inputStyle" style="width:20%" title="발송 성공율 입력란"> <span> 원 이하</span>							
                <span>&nbsp;( </span>
                  <div class="consolCheck" style="width:10%"><input type="checkbox" id="70" class="checkStyle2" value="70"><label for="70" class=" color000">70%</label></div>
                  <div class="consolCheck" style="width:10%"><input type="checkbox" id="80" class="checkStyle2" value="80"><label for="80" class=" color000">80%</label></div>
                  <div class="consolCheck" style="width:10%"><input type="checkbox" id="90" class="checkStyle2" value="90"><label for="90" class=" color000">90%</label></div>
                  <div class="consolCheck" style="width:10%"><input type="checkbox" id="100" class="checkStyle2" value="100"><label for="100" class=" color000">100%</label></div>							
                <span> 초과 시)</span>
              </div>
            </div>
            <div class="of_h consolMarginTop">
              <h4 style="width:28%" class="inline-block">월 한도 금액 *</h4>
              <div class="float-right" style="width:72%">
                <input type="text" class="inputStyle" style="width:20%" title="발송 성공율 입력란"> <span> 원 이하</span>
                <span>&nbsp;( </span>
                  <div class="consolCheck" style="width:10%"><input type="checkbox" id="70" class="checkStyle2" value="70"><label for="70" class=" color000">70%</label></div>
                  <div class="consolCheck" style="width:10%"><input type="checkbox" id="80" class="checkStyle2" value="80"><label for="80" class=" color000">80%</label></div>
                  <div class="consolCheck" style="width:10%"><input type="checkbox" id="90" class="checkStyle2" value="90"><label for="90" class=" color000">90%</label></div>
                  <div class="consolCheck" style="width:10%"><input type="checkbox" id="100" class="checkStyle2" value="100"><label for="100" class=" color000">100%</label></div>							
                <span> 초과 시)</span>
              </div>						
            </div>	
          </div>
                
        </div>
          
        <hr>

        <div class="row">
          <div class="col-xs-6 consolMarginTop">
            <div class="of_h consoleCon">
              <h4 class="inline-block">수집 대상 API</h4>
              <a @click="fnAdd1" class="btnStyle1 backBlack float-right" style="min-width:auto" title="등록">등록</a>
            </div>
            <div class="scroll-y2">
              <table class="table_skin1 tbl-striped" style="width:100%">
                <caption>수집 대상 API의 프로젝트명, API명, API Key을 나타내는 표</caption>
                <colgroup>
                  <col style="width:25%">
                  <col style="width:25%">
                  <col style="width:25%">
                  <col style="width:25%">
                  <col>
                </colgroup>
                <thead>
                  <tr>
                  <th class="text-center lc-1">프로젝트명</th>
                  <th class="text-center lc-1">API 명</th>
                  <th class="text-center lc-1">API Key</th>
                  <th class="text-center lc-1 end">삭제</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>제임스강 프로젝트</td>
                    <td>구매발송 API</td>
                    <td>123551132551</td>
                    <td class="text-center end vertical-middle">
                      <a class="btnStyle1 backLightGray" title="삭제">삭제</a>
                    </td>
                  </tr>	
                  <tr>
                    <td>제임스강 프로젝트</td>
                    <td>구매발송 API</td>
                    <td>123551132551</td>
                    <td class="text-center end vertical-middle">
                      <a class="btnStyle1 backLightGray" title="삭제">삭제</a>
                    </td>
                  </tr>
                  <tr>
                    <td>제임스강 프로젝트</td>
                    <td>구매발송 API</td>
                    <td>123551132551</td>
                    <td class="text-center end vertical-middle">
                      <a class="btnStyle1 backLightGray" title="삭제">삭제</a>
                    </td>
                  </tr>
                  <tr>
                    <td>제임스강 프로젝트</td>
                    <td>구매발송 API</td>
                    <td>123551132551</td>
                    <td class="text-center end vertical-middle">
                      <a class="btnStyle1 backLightGray" title="삭제">삭제</a>
                    </td>
                  </tr>
                  <tr>
                    <td>제임스강 프로젝트</td>
                    <td>구매발송 API</td>
                    <td>123551132551</td>
                    <td class="text-center end vertical-middle">
                      <a class="btnStyle1 backLightGray" title="삭제">삭제</a>
                    </td>
                  </tr>
                  <tr>
                    <td>제임스강 프로젝트</td>
                    <td>구매발송 API</td>
                    <td>123551132551</td>
                    <td class="text-center end vertical-middle">
                      <a class="btnStyle1 backLightGray" title="삭제">삭제</a>
                    </td>
                  </tr>
                  <tr>
                    <td>제임스강 프로젝트</td>
                    <td>구매발송 API</td>
                    <td>123551132551</td>
                    <td class="text-center end vertical-middle">
                      <a class="btnStyle1 backLightGray" title="삭제">삭제</a>
                    </td>
                  </tr>
                  <tr>
                    <td>제임스강 프로젝트</td>
                    <td>구매발송 API</td>
                    <td>123551132551</td>
                    <td class="text-center end vertical-middle">
                      <a class="btnStyle1 backLightGray" title="삭제">삭제</a>
                    </td>
                  </tr>
                  <tr>
                    <td>제임스강 프로젝트</td>
                    <td>구매발송 API</td>
                    <td>123551132551</td>
                    <td class="text-center end vertical-middle">
                      <a class="btnStyle1 backLightGray" title="삭제">삭제</a>
                    </td>
                  </tr>
                  <tr>
                    <td>제임스강 프로젝트</td>
                    <td>구매발송 API</td>
                    <td>123551132551</td>
                    <td class="text-center end vertical-middle">
                      <a class="btnStyle1 backLightGray" title="삭제">삭제</a>
                    </td>
                  </tr>
                  <tr>
                    <td>제임스강 프로젝트</td>
                    <td>구매발송 API</td>
                    <td>123551132551</td>
                    <td class="text-center end vertical-middle">
                      <a class="btnStyle1 backLightGray" title="삭제">삭제</a>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div class="col-xs-6 consolMarginTop">
            <div class="of_h consoleCon">
              <h4 class="inline-block">알람 수신</h4>
              <a @click="fnAdd2" class="btnStyle1 backBlack float-right" style="min-width:auto" title="등록">등록</a>
            </div>
            <div class="scroll-y2">
              <table class="table_skin1" style="width:100%">
                <caption>수집 대상 API의 프로젝트명, API명, API Key을 나타내는 표</caption>
                <colgroup>
                  <col style="width:100%">
                  <col>
                </colgroup>						
                <tbody>
                  <tr>							
                    <td class="end of_h text-left">
                      <p class="inline-block vertical-sub">운영유지 (11명)</p>
                      <a class="btnStyle1 backLightGray float-right" title="삭제">삭제</a>
                    </td>
                  </tr>	
                  <tr>							
                    <td class="end of_h text-left">
                      <p class="inline-block vertical-sub">강알람 (010-4311-1234)</p>
                      <a class="btnStyle1 backLightGray float-right" title="삭제">삭제</a>
                    </td>
                  </tr>	
                  <tr>							
                    <td class="end of_h text-left">
                      <p class="inline-block vertical-sub">운영유지 (11명)</p>
                      <a class="btnStyle1 backLightGray float-right" title="삭제">삭제</a>
                    </td>
                  </tr>	
                  <tr>							
                    <td class="end of_h text-left">
                      <p class="inline-block vertical-sub">강알람 (010-4311-1234)</p>
                      <a class="btnStyle1 backLightGray float-right" title="삭제">삭제</a>
                    </td>
                  </tr>
                  <tr>							
                    <td class="end of_h text-left">
                      <p class="inline-block vertical-sub">운영유지 (11명)</p>
                      <a class="btnStyle1 backLightGray float-right" title="삭제">삭제</a>
                    </td>
                  </tr>	
                  <tr>							
                    <td class="end of_h text-left">
                      <p class="inline-block vertical-sub">강알람 (010-4311-1234)</p>
                      <a class="btnStyle1 backLightGray float-right" title="삭제">삭제</a>
                    </td>
                  </tr>
                  <tr>							
                    <td class="end of_h text-left">
                      <p class="inline-block vertical-sub">운영유지 (11명)</p>
                      <a class="btnStyle1 backLightGray float-right" title="삭제">삭제</a>
                    </td>
                  </tr>	
                  <tr>							
                    <td class="end of_h text-left">
                      <p class="inline-block vertical-sub">강알람 (010-4311-1234)</p>
                      <a class="btnStyle1 backLightGray float-right" title="삭제">삭제</a>
                    </td>
                  </tr>
                  <tr>							
                    <td class="end of_h text-left">
                      <p class="inline-block vertical-sub">운영유지 (11명)</p>
                      <a class="btnStyle1 backLightGray float-right" title="삭제">삭제</a>
                    </td>
                  </tr>	
                  <tr>							
                    <td class="end of_h text-left">
                      <p class="inline-block vertical-sub">강알람 (010-4311-1234)</p>
                      <a class="btnStyle1 backLightGray float-right" title="삭제">삭제</a>
                    </td>
                  </tr>
                  <tr>							
                    <td class="end of_h text-left">
                      <p class="inline-block vertical-sub">운영유지 (11명)</p>
                      <a class="btnStyle1 backLightGray float-right" title="삭제">삭제</a>
                    </td>
                  </tr>	
                  <tr>							
                    <td class="end of_h text-left">
                      <p class="inline-block vertical-sub">강알람 (010-4311-1234)</p>
                      <a class="btnStyle1 backLightGray float-right" title="삭제">삭제</a>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div class="float-right mt40">						
          <a @click="fnSave" class="btnStyle2 backRed ml10" title="등록">저장</a>
          <a @click="fnCancel" class="btnStyle2 ml10">취소</a>
        </div>
      </article>
      <collectApiListPopup :popupTitle="popupTitle1" :rowData="rowData1" :popReset="popReset1"/>
      <receptGroupListPopup :popupTitle="popupTitle2" :rowData="rowData2" :popReset="popReset2"/>
    </div>
</template>

<script>
import alarmApi from "@/modules/alarm/service/alarmApi"
import collectApiListPopup from "@/modules/alarm/components/bp-collectApiList"
import receptGroupListPopup from "@/modules/alarm/components/bp-receptGroupList"
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  data() {
    return {
      alarmType: {
        alarmTypeCode : null,
        alarmTypeName : null,
        alarmDesc : null
      },
      popupTitle1: '등록',
      rowData1: {},
      popReset1: 0,
      popupTitle2: '등록',
      rowData2: {},
      popReset2: 0
    }
  },
  components: {
    collectApiListPopup,
    receptGroupListPopup
  },
  mounted() {
    this.fnInit()
  },
  methods: {
    fnInit() {
      var params = {alarmTypeCode:this.$route.params.typeCode}
      var vm = this
      alarmApi.selectAlarmType(params).then(response => {
        var result = response.data
        if(result.success) {
          vm.alarmType = result.data
        }
      })
    },
    fnAdd1() {
        this.popupTitle1 = '등록'
        this.rowData1 = {}
        this.popReset1 = this.popReset1 + 1
        jQuery("#collectApiListPopup").modal("show")
    },
    fnAdd2() {
        this.popupTitle2 = '등록'
        this.rowData2 = {}
        this.popReset2 = this.popReset2 + 1
        jQuery("#receptGroupListPopup").modal("show")
    },
    fnSave() {
      this.$router.push('/ac/alarm/alarm')
    },
    fnCancel() {
      this.$router.go(-1)
    }
  }
}
</script>