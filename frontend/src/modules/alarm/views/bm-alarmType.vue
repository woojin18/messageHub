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
              <input type="text" class="inputStyle float-right" style="width:72%" title="알람명 입력란" v-model="alarm.alarmName">
            </div>
            <div class="of_h" v-if="alarmType.alarmTypeCode == 'MSG' || alarmType.alarmTypeCode == 'IN'">
              <h4 style="width:28%" class="inline-block">수집 요일/시간 *</h4>
              <div class="inline-block float-right" style="width:72%">
                <div class="of_h">
                  <div class="consolCheck" style="width:30%"><input type="checkbox" id="chk_all" v-model="chkAll" class="checkStyle2"><label for="chk_all" class="vertical-sub color000">전체</label></div>
                  <div class="inline-block float-right" style="width:70%">
                    <select name="admin_sub13_1_1" class="selectStyle2" style="width:32%" v-model="sttAll">
                      <option value="">선택</option>
                      <option v-for="n in 24" v-bind:value="n-1">{{n-1}}</option>
                    </select>
                    <span style="padding-right:4px"> 시 </span>
                    <span style="padding:0 7px;"> ~ </span>
                    <select name="admin_sub13_1_2" class="selectStyle2" style="width:32%" v-model="endAll">
                      <option value="">선택</option>
                      <option v-for="n in 24" v-bind:value="n-1">{{n-1}}</option>
                    </select>
                    <span style="padding-left:4px"> 시</span>
                  </div>
                </div>
                <hr class="mt15 mb15">
                <div class="of_h">
                  <div class="consolCheck" style="width:30%"><input type="checkbox" id="coltMonYn" v-model="alarm.coltDayWeek.coltMonYn" class="checkStyle2"><label for="coltMonYn" class="vertical-sub color000">월요일</label></div>
                  <div class="inline-block float-right" style="width:70%">
                    <select name="admin_sub13_1_3" class="selectStyle2" style="width:32%" v-model="alarm.coltDayWeek.coltMonStt">
                      <option value="">선택</option>
                      <option v-for="n in 24" v-bind:value="n-1">{{n-1}}</option>
                    </select>
                    <span style="padding-right:4px"> 시 </span>
                    <span style="padding:0 7px;"> ~ </span>
                    <select name="admin_sub13_1_4" class="selectStyle2" style="width:32%" v-model="alarm.coltDayWeek.coltMonEnd">
                      <option value="">선택</option>
                      <option v-for="n in 24" v-bind:value="n-1">{{n-1}}</option>
                    </select>
                    <span style="padding-left:4px"> 시</span>
                  </div>
                </div>
                <div class="of_h consolMarginTop">
                  <div class="consolCheck" style="width:30%"><input type="checkbox" id="coltTueYn" v-model="alarm.coltDayWeek.coltTueYn" class="checkStyle2"><label for="coltTueYn" class="vertical-sub color000">화요일</label></div>
                  <div class="inline-block float-right" style="width:70%">
                    <select name="admin_sub13_1_5" class="selectStyle2" style="width:32%" v-model="alarm.coltDayWeek.coltTueStt">
                      <option value="">선택</option>
                      <option v-for="n in 24" v-bind:value="n-1">{{n-1}}</option>
                    </select>
                    <span style="padding-right:4px"> 시 </span>
                    <span style="padding:0 7px;"> ~ </span>
                    <select name="admin_sub13_1_6" class="selectStyle2" style="width:32%" v-model="alarm.coltDayWeek.coltTueEnd">
                      <option value="">선택</option>
                      <option v-for="n in 24" v-bind:value="n-1">{{n-1}}</option>
                    </select>
                    <span style="padding-left:4px"> 시</span>
                  </div>
                </div>
                <div class="of_h consolMarginTop">
                  <div class="consolCheck" style="width:30%"><input type="checkbox" id="coltWedYn" v-model="alarm.coltDayWeek.coltWedYn" class="checkStyle2"><label for="coltWedYn" class="vertical-sub color000">수요일</label></div>
                  <div class="inline-block float-right" style="width:70%">
                    <select name="admin_sub13_1_7" class="selectStyle2" style="width:32%" v-model="alarm.coltDayWeek.coltWedStt">
                      <option value="">선택</option>
                      <option v-for="n in 24" v-bind:value="n-1">{{n-1}}</option>
                    </select>
                    <span style="padding-right:4px"> 시 </span>
                    <span style="padding:0 7px;"> ~ </span>
                    <select name="admin_sub13_1_8" class="selectStyle2" style="width:32%" v-model="alarm.coltDayWeek.coltWedEnd">
                      <option value="">선택</option>
                      <option v-for="n in 24" v-bind:value="n-1">{{n-1}}</option>
                    </select>
                    <span style="padding-left:4px"> 시</span>
                  </div>
                </div>
                <div class="of_h consolMarginTop">
                  <div class="consolCheck" style="width:30%"><input type="checkbox" id="coltThuYn" v-model="alarm.coltDayWeek.coltThuYn" class="checkStyle2"><label for="coltThuYn" class="vertical-sub color000">목요일</label></div>
                  <div class="inline-block float-right" style="width:70%">
                    <select name="admin_sub13_1_9" class="selectStyle2" style="width:32%" v-model="alarm.coltDayWeek.coltThuStt">
                      <option value="">선택</option>
                      <option v-for="n in 24" v-bind:value="n-1">{{n-1}}</option>
                    </select>
                    <span style="padding-right:4px"> 시 </span>
                    <span style="padding:0 7px;"> ~ </span>
                    <select name="admin_sub13_1_10" class="selectStyle2" style="width:32%" v-model="alarm.coltDayWeek.coltThuEnd">
                      <option value="">선택</option>
                      <option v-for="n in 24" v-bind:value="n-1">{{n-1}}</option>
                    </select>
                    <span style="padding-left:4px"> 시</span>
                  </div>
                </div>
                <div class="of_h consolMarginTop">
                  <div class="consolCheck" style="width:30%"><input type="checkbox" id="coltFriYn" v-model="alarm.coltDayWeek.coltFriYn" class="checkStyle2"><label for="coltFriYn" class="vertical-sub color000">금요일</label></div>
                  <div class="inline-block float-right" style="width:70%">
                    <select name="admin_sub13_1_11" class="selectStyle2" style="width:32%" v-model="alarm.coltDayWeek.coltFriStt">
                      <option value="">선택</option>
                      <option v-for="n in 24" v-bind:value="n-1">{{n-1}}</option>
                    </select>
                    <span style="padding-right:4px"> 시 </span>
                    <span style="padding:0 7px;"> ~ </span>
                    <select name="admin_sub13_1_12" class="selectStyle2" style="width:32%" v-model="alarm.coltDayWeek.coltFriEnd">
                      <option value="">선택</option>
                      <option v-for="n in 24" v-bind:value="n-1">{{n-1}}</option>
                    </select>
                    <span style="padding-left:4px"> 시</span>
                  </div>
                </div>
                <div class="of_h consolMarginTop">
                  <div class="consolCheck" style="width:30%"><input type="checkbox" id="coltSatYn" v-model="alarm.coltDayWeek.coltSatYn" class="checkStyle2"><label for="coltSatYn" class="vertical-sub color000">토요일</label></div>
                  <div class="inline-block float-right" style="width:70%">
                    <select name="admin_sub13_1_13" class="selectStyle2" style="width:32%" v-model="alarm.coltDayWeek.coltSatStt">
                      <option value="">선택</option>
                      <option v-for="n in 24" v-bind:value="n-1">{{n-1}}</option>
                    </select>
                    <span style="padding-right:4px"> 시 </span>
                    <span style="padding:0 7px;"> ~ </span>
                    <select name="admin_sub13_1_14" class="selectStyle2" style="width:32%" v-model="alarm.coltDayWeek.coltSatEnd">
                      <option value="">선택</option>
                      <option v-for="n in 24" v-bind:value="n-1">{{n-1}}</option>
                    </select>
                    <span style="padding-left:4px"> 시</span>
                  </div>
                </div>
                <div class="of_h consolMarginTop">
                  <div class="consolCheck" style="width:30%"><input type="checkbox" id="coltSunYn" v-model="alarm.coltDayWeek.coltSunYn" class="checkStyle2"><label for="coltSunYn" class="vertical-sub color000">일요일</label></div>
                  <div class="inline-block float-right" style="width:70%">
                    <select name="admin_sub13_1_15" class="selectStyle2" style="width:32%" v-model="alarm.coltDayWeek.coltSunStt">
                      <option value="">선택</option>
                      <option v-for="n in 24" v-bind:value="n-1">{{n-1}}</option>
                    </select>
                    <span style="padding-right:4px"> 시 </span>
                    <span style="padding:0 7px;"> ~ </span>
                    <select name="admin_sub13_1_16" class="selectStyle2" style="width:32%" v-model="alarm.coltDayWeek.coltSunEnd">
                      <option value="">선택</option>
                      <option v-for="n in 24" v-bind:value="n-1">{{n-1}}</option>
                    </select>
                    <span style="padding-left:4px"> 시</span>
                  </div>
                </div>
              </div>
            </div>	
            <div class="of_h consolMarginTop" v-if="alarmType.alarmTypeCode == 'MSG'">
              <h4 style="width:28%" class="inline-block">수집 최소 건수 *</h4>
              <div class="float-right" style="width:72%">
                <input type="text" class="inputStyle" style="width:20%" title="수집 최소 건수 입력란" v-model="alarm.coltMinQty"> 건 이상
              </div>
            </div>
            <div class="of_h consolMarginTop" v-if="alarmType.alarmTypeCode == 'IN'">
              <h4 style="width:28%" class="inline-block">인입 건수 *</h4>
              <div class="float-right" style="width:72%">
                <input type="text" class="inputStyle" style="width:20%" title="인입 건수 입력란" v-model="alarm.coltMinQty"> 건 이하
              </div>
            </div>
            <div class="of_h consolMarginTop" v-if="alarmType.alarmTypeCode == 'PRE'">
              <h4 style="width:28%" class="inline-block">알람 금액 *</h4>
              <div class="float-right" style="width:72%">
                <input type="text" class="inputStyle" style="width:20%" title="알람 금액 입력란" v-model="alarm.coltMinQty"> 원 이하
              </div>
            </div>
            <div class="of_h consolMarginTop" v-if="alarmType.alarmTypeCode == 'QTY'">
              <h4 style="width:28%" class="inline-block">알람 최소 건수 *</h4>
              <div class="float-right" style="width:72%">
                <input type="text" class="inputStyle" style="width:20%" title="알람 최소 건수 입력란" v-model="alarm.coltMinQty"> 건
                <p>당일 메시지 발송 건수가 알람 최소 건수와 전달 일 평균 건수보다 클 경우 알람을 보냅니다.</p>
                <p>ex) 입력한 알람 최소 건수가 1,100 건이고 전달 일 평균 건수가 1,000 건 이라 했을 때 당일 메시지가 1,100건을 넘길 경우 알람 전송</p>
              </div>
            </div>
            <div class="of_h consolMarginTop" v-if="alarmType.alarmTypeCode == 'MSG'">
              <h4 style="width:28%" class="inline-block">발송 성공율 *</h4>
              <div class="float-right" style="width:72%">
                <input type="text" class="inputStyle" style="width:20%" title="발송 성공율 입력란" v-model="alarm.sendSuccRate"> % 이하
              </div>
            </div>
            <div class="of_h consolMarginTop" v-if="alarmType.alarmTypeCode == 'SEND'">
              <h4 style="width:28%" class="inline-block">일 한도 금액 *</h4>
              <div class="float-right" style="width:72%">
                <input type="text" class="inputStyle" style="width:20%" title="일 한도 금액 입력란" v-model="alarm.coltMinQty"> <span> 원 이하</span>							
                <span>&nbsp;( </span>
                  <div class="consolCheck" style="width:13%"><input type="checkbox" id="day70" v-model="alarm.dayLimit.rate70" class="checkStyle2"><label for="day70" class=" color000">70%</label></div>
                  <div class="consolCheck" style="width:13%"><input type="checkbox" id="day80" v-model="alarm.dayLimit.rate80" class="checkStyle2"><label for="day80" class=" color000">80%</label></div>
                  <div class="consolCheck" style="width:13%"><input type="checkbox" id="day90" v-model="alarm.dayLimit.rate90" class="checkStyle2"><label for="day90" class=" color000">90%</label></div>
                  <div class="consolCheck" style="width:15%"><input type="checkbox" id="day100" v-model="alarm.dayLimit.rate100" class="checkStyle2"><label for="day100" class=" color000">100%</label></div>
                <span> 초과 시)</span>
              </div>
            </div>
            <div class="of_h consolMarginTop" v-if="alarmType.alarmTypeCode == 'SEND'">
              <h4 style="width:28%" class="inline-block">월 한도 금액 *</h4>
              <div class="float-right" style="width:72%">
                <input type="text" class="inputStyle" style="width:20%" title="발송 성공율 입력란" v-model="alarm.mthLimitAmt"> <span> 원 이하</span>			
                <span>&nbsp;( </span>
                  <div class="consolCheck" style="width:13%"><input type="checkbox" id="mth70" v-model="alarm.mthLimit.rate70" class="checkStyle2"><label for="mth70" class=" color000">70%</label></div>
                  <div class="consolCheck" style="width:13%"><input type="checkbox" id="mth80" v-model="alarm.mthLimit.rate80" class="checkStyle2"><label for="mth80" class=" color000">80%</label></div>
                  <div class="consolCheck" style="width:13%"><input type="checkbox" id="mth90" v-model="alarm.mthLimit.rate90" class="checkStyle2"><label for="mth90" class=" color000">90%</label></div>
                  <div class="consolCheck" style="width:15%"><input type="checkbox" id="mth100" v-model="alarm.mthLimit.rate100" class="checkStyle2"><label for="mth100" class=" color000">100%</label></div>
                <span> 초과 시)</span>
              </div>						
            </div>	
          </div>
                
        </div>
          
        <hr>

        <div class="row">
          <div class="col-xs-6 consolMarginTop" v-if="alarmType.alarmTypeCode != 'PRE'">
            <div class="of_h consoleCon">
              <h4 class="inline-block">수집 대상 API</h4>
              <a @click="fnAdd1" activity="SAVE" class="btnStyle1 backBlack float-right" style="min-width:auto" title="등록">등록</a>
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
                  <tr v-for="(data, idx) in alarm.list1">
                    <td>{{data.projectName}}</td>
                    <td>{{data.apiKeyName}}</td>
                    <td>{{data.apiKey}}</td>
                    <td class="text-center end vertical-middle">
                      <a class="btnStyle1 backLightGray" activity="SAVE" title="삭제" @click="fnDel1(idx)">삭제</a>
                    </td>
                  </tr>	
								  <tr v-if="alarm.list1.length == 0">
										<td class="text-center" colspan="4">검색된 내용이 없습니다.</td>
									</tr>
                </tbody>
              </table>
            </div>
          </div>
          <div class="col-xs-6 consolMarginTop">
            <div class="of_h consoleCon">
              <h4 class="inline-block">알람 수신</h4>
              <a @click="fnAdd2" class="btnStyle1 backBlack float-right" activity="SAVE" style="min-width:auto" title="등록">등록</a>
            </div>
            <div class="scroll-y2">
              <table class="table_skin1" style="width:100%">
                <caption>수집 대상 API의 프로젝트명, API명, API Key을 나타내는 표</caption>
                <colgroup>
                  <col style="width:100%">
                  <col>
                </colgroup>						
                <tbody>
                  <tr v-for="(data, idx) in alarm.list2">
                    <td class="end of_h text-left">
                      <p class="inline-block vertical-sub">{{data.receptGroupName}} ({{data.recipientCnt | formatComma }}명)</p>
                      <a class="btnStyle1 backLightGray float-right" title="삭제" activity="SAVE" @click="fnDel2(idx)">삭제</a>
                    </td>
                  </tr>	
                  <tr v-for="(data, idx) in alarm.list3">					
                    <td class="end of_h text-left">
                      <p class="inline-block vertical-sub">{{data.recipientName}} ({{data.hpNumber | hpNumberAddDash}})</p>
                      <a class="btnStyle1 backLightGray float-right" title="삭제" activity="SAVE" @click="fnDel3(idx)">삭제</a>
                    </td>
                  </tr>		
								  <tr v-if="alarm.list2.length == 0 && alarm.list3.length == 0">
										<td class="text-center">검색된 내용이 없습니다.</td>
									</tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div class="float-right mt40">						
          <a @click="fnSave" class="btnStyle2 backRed ml10" activity="SAVE" title="등록">저장</a>
          <a @click="fnCancel" class="btnStyle2 ml10">취소</a>
        </div>
      </article>
      <collectApiListPopup :popReset="popReset1"/>
      <receptGroupListPopup :popReset="popReset2"/>
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
      chkAll : false,
      sttAll : '',
      endAll : '',
      alarmType: {
        alarmTypeCode : null,
        alarmTypeName : null,
        alarmDesc : null
      },
      alarm: {
        alarmId : null,
        alarmTypeCode : null,
        alarmName : null,
        coltDayWeek : {
          coltMonYn : false,
          coltMonStt : '',
          coltMonEnd : '',
          coltTueYn : false,
          coltTueStt : '',
          coltTueEnd : '',
          coltWedYn : false,
          coltWedStt : '',
          coltWedEnd : '',
          coltThuYn : false,
          coltThuStt : '',
          coltThuEnd : '',
          coltFriYn : false,
          coltFriStt : '',
          coltFriEnd : '',
          coltSatYn : false,
          coltSatStt : '',
          coltSatEnd : '',
          coltSunYn : false,
          coltSunStt : '',
          coltSunEnd : '',
        },
        coltMinQty : null,
        sendSuccRate : null,
        dayLimit : {
          rate70 : false,
          rate80 : false,
          rate90 : false,
          rate100 : false
        },
        mthLimitAmt : null,
        mthLimit : {
          rate70 : false,
          rate80 : false,
          rate90 : false,
          rate100 : false
        },
        list1 : [],
        list2 : [],
        list3 : []
      },
      popReset1: 0,
      popReset2: 0
    }
  },
  components: {
    collectApiListPopup,
    receptGroupListPopup
  },
  watch : {
    chkAll(val) {
      this.alarm.coltDayWeek.coltMonYn = val
      this.alarm.coltDayWeek.coltTueYn = val
      this.alarm.coltDayWeek.coltWedYn = val
      this.alarm.coltDayWeek.coltThuYn = val
      this.alarm.coltDayWeek.coltFriYn = val
      this.alarm.coltDayWeek.coltSatYn = val
      this.alarm.coltDayWeek.coltSunYn = val
    },
    sttAll(val) {
      if (val === '') return
      if (this.alarm.coltDayWeek.coltMonYn) this.alarm.coltDayWeek.coltMonStt = val
      if (this.alarm.coltDayWeek.coltTueYn) this.alarm.coltDayWeek.coltTueStt = val
      if (this.alarm.coltDayWeek.coltWedYn) this.alarm.coltDayWeek.coltWedStt = val
      if (this.alarm.coltDayWeek.coltThuYn) this.alarm.coltDayWeek.coltThuStt = val
      if (this.alarm.coltDayWeek.coltFriYn) this.alarm.coltDayWeek.coltFriStt = val
      if (this.alarm.coltDayWeek.coltSatYn) this.alarm.coltDayWeek.coltSatStt = val
      if (this.alarm.coltDayWeek.coltSunYn) this.alarm.coltDayWeek.coltSunStt = val
    },
    endAll(val) {
      if (val === '') return
      if (this.alarm.coltDayWeek.coltMonYn) this.alarm.coltDayWeek.coltMonEnd = val
      if (this.alarm.coltDayWeek.coltTueYn) this.alarm.coltDayWeek.coltTueEnd = val
      if (this.alarm.coltDayWeek.coltWedYn) this.alarm.coltDayWeek.coltWedEnd = val
      if (this.alarm.coltDayWeek.coltThuYn) this.alarm.coltDayWeek.coltThuEnd = val
      if (this.alarm.coltDayWeek.coltFriYn) this.alarm.coltDayWeek.coltFriEnd = val
      if (this.alarm.coltDayWeek.coltSatYn) this.alarm.coltDayWeek.coltSatEnd = val
      if (this.alarm.coltDayWeek.coltSunYn) this.alarm.coltDayWeek.coltSunEnd = val
    },
    "alarm.coltMinQty"(){
      this.alarm.coltMinQty = this.$gfnCommonUtils.formatPrice(this.alarm.coltMinQty.replace(/[^0-9]/g, ''));
    },
    "alarm.sendSuccRate"(){
      this.alarm.sendSuccRate = this.$gfnCommonUtils.formatPrice(this.alarm.sendSuccRate.replace(/[^0-9]/g, ''));
    },
    "alarm.mthLimitAmt"(){
      this.alarm.mthLimitAmt = this.$gfnCommonUtils.formatPrice(this.alarm.mthLimitAmt.replace(/[^0-9]/g, ''));
    }
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
          vm.alarm.alarmTypeCode = vm.alarmType.alarmTypeCode
        }
      })
      var alarmId = this.$route.params.alarmId
      if (alarmId !== undefined) {
        params = {alarmId:alarmId}
        alarmApi.selectAlarm(params).then(response => {
          var result = response.data
          if(result.success) {
            vm.alarm = result.data
          }
        })
      }

    },
    fnAdd1() {
        this.popReset1 = this.popReset1 + 1
        jQuery("#collectApiListPopup").modal("show")
    },
    fnDel1(idx) {
      this.alarm.list1.splice(idx, 1)
    },
    fnAddApi(list1) {
	    var vm = this
	    var list = vm.alarm.list1;
	    list1.forEach(function(val1) {
		    var isExists = false
		    list.forEach(function(val2) {
		      if (val1.apiKey == val2.apiKey) {
			      isExists = true
			      return
		      }
		    })
		    if(isExists == false) {
			    vm.alarm.list1.push(val1)
		    }
	    })
    },
    fnAdd2() {
        this.popReset2 = this.popReset2 + 1
        jQuery("#receptGroupListPopup").modal("show")
    },
    fnDel2(idx) {
      this.alarm.list2.splice(idx, 1)
    },
    fnDel3(idx) {
      this.alarm.list3.splice(idx, 1)
    },
    fnAddReci(list2, list3) {
	    var vm = this
	    var list = vm.alarm.list2;
	    list2.forEach(function(val1) {
		    var isExists = false
		    list.forEach(function(val2) {
		      if (val1.receptGroupId == val2.receptGroupId) {
			      isExists = true
			      return
		      }
		    })
		    if(isExists == false) {
			    vm.alarm.list2.push(val1)
		    }
	    })
	    var list = vm.alarm.list3;
	    list3.forEach(function(val1) {
		    var isExists = false
		    list.forEach(function(val2) {
		      if (val1.recipientId == val2.recipientId) {
			      isExists = true
			      return
		      }
		    })
		    if(isExists == false) {
			    vm.alarm.list3.push(val1)
		    }
	    })
    },
    fnSave() {
      //필수항목 check
      if(jQuery.trim(this.alarm.alarmName).length == 0){
        confirm.fnAlert("", "알람명은 필수사항입니다.");
        return;
      }
      //수집 요일/시간
      if (this.alarmType.alarmTypeCode == 'MSG' || this.alarmType.alarmTypeCode == 'IN') {
        if (this.alarm.coltDayWeek.coltMonYn == false && this.alarm.coltDayWeek.coltTueYn == false && this.alarm.coltDayWeek.coltWedYn == false && this.alarm.coltDayWeek.coltThuYn == false
         && this.alarm.coltDayWeek.coltFriYn == false && this.alarm.coltDayWeek.coltSatYn == false && this.alarm.coltDayWeek.coltSunYn == false) {
          confirm.fnAlert("", "수집 요일을 하나 이상 선택해주세요.");
          return; 
         }
        if (this.alarm.coltDayWeek.coltMonYn == true && (jQuery.trim(this.alarm.coltDayWeek.coltMonStt).length == 0 || jQuery.trim(this.alarm.coltDayWeek.coltMonEnd).length == 0)) {
          confirm.fnAlert("", "수집 월요일 시간은 필수사항입니다.");
          return;
        }
        if (this.alarm.coltDayWeek.coltTueYn == true && (jQuery.trim(this.alarm.coltDayWeek.coltTueStt).length == 0 || jQuery.trim(this.alarm.coltDayWeek.coltTueEnd).length == 0)) {
          confirm.fnAlert("", "수집 화요일 시간은 필수사항입니다.");
          return;
        }
        if (this.alarm.coltDayWeek.coltWedYn == true && (jQuery.trim(this.alarm.coltDayWeek.coltWedStt).length == 0 || jQuery.trim(this.alarm.coltDayWeek.coltWedEnd).length == 0)) {
          confirm.fnAlert("", "수집 수요일 시간은 필수사항입니다.");
          return;
        }
        if (this.alarm.coltDayWeek.coltThuYn == true && (jQuery.trim(this.alarm.coltDayWeek.coltThuStt).length == 0 || jQuery.trim(this.alarm.coltDayWeek.coltThuEnd).length == 0)) {
          confirm.fnAlert("", "수집 목요일 시간은 필수사항입니다.");
          return;
        }
        if (this.alarm.coltDayWeek.coltFriYn == true && (jQuery.trim(this.alarm.coltDayWeek.coltFriStt).length == 0 || jQuery.trim(this.alarm.coltDayWeek.coltFriEnd).length == 0)) {
          confirm.fnAlert("", "수집 금요일 시간은 필수사항입니다.");
          return;
        }
        if (this.alarm.coltDayWeek.coltSatYn == true && (jQuery.trim(this.alarm.coltDayWeek.coltSatStt).length == 0 || jQuery.trim(this.alarm.coltDayWeek.coltSatEnd).length == 0)) {
          confirm.fnAlert("", "수집 토요일 시간은 필수사항입니다.");
          return;
        }
        if (this.alarm.coltDayWeek.coltSunYn == true && (jQuery.trim(this.alarm.coltDayWeek.coltSunStt).length == 0 || jQuery.trim(this.alarm.coltDayWeek.coltSunEnd).length == 0)) {
          confirm.fnAlert("", "수집 일요일 시간은 필수사항입니다.");
          return;
        }
      }
      if (this.alarmType.alarmTypeCode == 'MSG') {
        //필수항목 check
        if(jQuery.trim(this.alarm.coltMinQty).length == 0){
          confirm.fnAlert("", "수집 최소 건수는 필수사항입니다.");
          return;
        }
      }
      if (this.alarmType.alarmTypeCode == 'IN') {
        //필수항목 check
        if(jQuery.trim(this.alarm.coltMinQty).length == 0){
          confirm.fnAlert("", "인입 건수는 필수사항입니다.");
          return;
        }
      }
      if (this.alarmType.alarmTypeCode == 'PRE') {
        //필수항목 check
        if(jQuery.trim(this.alarm.coltMinQty).length == 0){
          confirm.fnAlert("", "알람 금액은 필수사항입니다.");
          return;
        }
      }
      if (this.alarmType.alarmTypeCode == 'QTY') {
        //필수항목 check
        if(jQuery.trim(this.alarm.coltMinQty).length == 0){
          confirm.fnAlert("", "알람 최소 건수는 필수사항입니다.");
          return;
        }
      }
      if (this.alarmType.alarmTypeCode == 'MSG') {
        //필수항목 check
        if(jQuery.trim(this.alarm.sendSuccRate).length == 0){
          confirm.fnAlert("", "발송 성공율은 필수사항입니다.");
          return;
        }
      }
      if (this.alarmType.alarmTypeCode == 'SEND') {
        //필수항목 check
        if(jQuery.trim(this.alarm.coltMinQty).length == 0){
          confirm.fnAlert("", "일 한도 금액은 필수사항입니다.");
          return;
        }
        if (this.alarm.dayLimit.rate70 == false && this.alarm.dayLimit.rate80 == false && this.alarm.dayLimit.rate90 == false && this.alarm.dayLimit.rate100 == false) {
          confirm.fnAlert("", "일 한도 초과 금액율을 하나 이상 선택해주세요.");
          return;
        }
        //필수항목 check
        if(jQuery.trim(this.alarm.mthLimitAmt).length == 0){
          confirm.fnAlert("", "월 한도 금액은 필수사항입니다.");
          return;
        }
        if (this.alarm.mthLimit.rate70 == false && this.alarm.mthLimit.rate80 == false && this.alarm.mthLimit.rate90 == false && this.alarm.mthLimit.rate100 == false) {
          confirm.fnAlert("", "월 한도 초과 금액율을 하나 이상 선택해주세요.");
          return;
        }
      }
      var params = Object.assign({}, this.alarm)
      params.coltMinQty = this.$gfnCommonUtils.formatUncomma(params.coltMinQty);
      params.sendSuccRate = this.$gfnCommonUtils.formatUncomma(params.sendSuccRate);
      params.mthLimitAmt = this.$gfnCommonUtils.formatUncomma(params.mthLimitAmt);
      
	    alarmApi.saveAlarm(params).then(response =>{
       	var result = response.data;
       	if(result.success) {
	   	    confirm.fnAlert( "알람 저장", "저장되었습니다.");
		      this.$router.push('/ac/alarm/alarm')
		    } else {
		      confirm.fnAlert("", result.message);
		    }
      });
    },
    fnCancel() {
      this.$router.go(-1)
    }
  }
}
</script>