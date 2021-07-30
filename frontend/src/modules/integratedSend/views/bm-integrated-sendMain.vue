<template>
  <div>
    <div class="contentHeader">
      <h2>통합 발송</h2>
      <!-- <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="통합 발송 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
    </div>

    <!-- 본문 -->
    <div class="row">
      <div class="phone3 inline-block" style="width:30%">
        <div class="phoneFixed">
          <div class="tab-content">
            <!-- phoneWrap -->
            <!-- PUSH -->
            <div v-if="previewMessageType == 'PUSH' && tmpltData.PUSH" class="tab-pane active">
              <div class="phoneWrap">
                <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
                <div class="phoneTextWrap scroll-yc">
                  <div class="phoneText1">
                    <p v-if="$gfnCommonUtils.isEmpty(tmpltData.PUSH.title)">제목</p>
                    <p v-else>{{tmpltData.PUSH.title}}</p>
                  </div>
                  <div 
                    v-if="tmpltData.msgType == 'IMAGE' && tmpltData.PUSH.ext && tmpltData.PUSH.ext.imageUrl" 
                    class="phoneText2 mt10 text-center simulatorImg"
                    :style="'padding:65px;background-image: url('+tmpltData.PUSH.ext.imageUrl+');'"
                  >
                  </div>
                  <p class="consolMarginTop">
                    <pre>{{tmpltData.PUSH.msg}}</pre>
                    <br/>
                    {{tmpltData.msgKind == 'A' ? tmpltData.PUSH.rcvblcInput : ''}}
                  </p>
                </div>
              </div>
            </div>
            <!--// PUSH -->
            <!-- FRIENDTALK -->
            <div v-if="previewMessageType == 'FRIENDTALK' && tmpltData.FRIENDTALK" class="tab-pane active">
              <div class="phoneWrap">
                <img src="@/assets/images/common/phoneMockup2_1.svg" alt="프리 템플릿">
                <div class="phoneTextWrap4 scroll-yc">
                  <p v-if="tmpltData.msgKind == 'A'">[광고]</p>
                  <div class="mt5">
                    <div 
                      v-if="tmpltData.msgType == 'IMAGE' && tmpltData.FRIENDTALK.image && tmpltData.FRIENDTALK.image.imageUrl" 
                      class="phoneText2 text-center simulatorImg"
                      :style="'padding:65px;background-image: url('+tmpltData.FRIENDTALK.image.imageUrl+');'"
                    >
                    </div>
                    <div class="text-sub-wrap">
                      <p class="text-sub"><pre>{{tmpltData.FRIENDTALK.msg}}</pre></p>
                    </div>
                    <div v-for="(buttonInfo, idx) in tmpltData.FRIENDTALK.buttons" :key="idx">
                      <a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.name)" class="btnStyle1 backLightGray">{{buttonInfo.name}}</a>
                    </div>
                    <p v-if="tmpltData.msgKind == 'A'" class="text-sub_2">수신거부: 홈 &gt; 친구차단</p>
                  </div>
                </div>
              </div>
            </div>
            <!--// FRIENDTALK -->
            <!-- MMS -->
            <div v-if="previewMessageType == 'MMS' && tmpltData.MMS" class="tab-pane active">
              <div class="phoneWrap">
                <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
                <div class="phoneTextWrap scroll-yc">
                  <div class="phoneText1">
                    <p>{{tmpltData.MMS.title}}</p>
                  </div>
                  <div v-if="tmpltData.msgType == 'IMAGE'">
                    <div v-for="(fileUrl, idx) in tmpltData.MMS.fileUrlLst" :key="idx" class="phoneText2 mt10 text-center simulatorImg"
                      :style="'padding:65px;background-image: url('+fileUrl+');'">
                    </div>
                  </div>
                  <p class="consolMarginTop"><pre>{{tmpltData.MMS.msg}}</pre></p>
                  <br v-if="tmpltData.msgKind == 'A'"/>
                  <span v-if="tmpltData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(tmpltData.MMS.rcvblcInput)">
                    {{tmpltData.MMS.rcvblcInput}}
                  </span>
                </div>
              </div>
            </div>
            <!--// MMS -->
            <!-- SMS -->
            <div v-if="previewMessageType == 'SMS' && tmpltData.SMS" class="tab-pane active">
              <div class="phoneWrap">
                <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
                <div class="phoneTextWrap scroll-yc">
                  <div class="phoneText1">
                    <p>{{tmpltData.SMS.callback}}</p>
                  </div>
                  <p class="consolMarginTop"><pre>{{tmpltData.SMS.msg}}</pre></p>
                  <br v-if="tmpltData.msgKind == 'A'"/>
                  <span v-if="tmpltData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(tmpltData.SMS.rcvblcInput)">
                    {{tmpltData.SMS.rcvblcInput}}
                  </span>
                </div>
              </div>
            </div>
            <!--// SMS -->
            <!-- ALIMTALK -->
            <div v-if="previewMessageType == 'ALIMTALK' && tmpltData.ALIMTALK" class="tab-pane active">
              <div class="phoneWrap">
                <img src="@/assets/images/common/phoneMockup3.svg" alt="알림톡 템플릿">
                <div class="phoneTextWrap3 scroll-y">
                  <div>
                    <p class="text-main"><i class="fal fa-envelope-open-text"></i> 알림톡 도착</p>
                    <div v-if="tmpltData.ALIMTALK.subData && tmpltData.ALIMTALK.subData.emphasizeType == 'TEXT'" class="text-sub-wrap" style="padding:10px;">
                      <p v-if="!$gfnCommonUtils.isEmpty(tmpltData.ALIMTALK.subData.tmpltEmpsSubTitle)" class="text-sub_1">{{tmpltData.ALIMTALK.subData.tmpltEmpsSubTitle}}</p>
                      <p v-if="!$gfnCommonUtils.isEmpty(tmpltData.ALIMTALK.subData.tmpltEmpsTitle)" class="text-sub">{{tmpltData.ALIMTALK.subData.tmpltEmpsTitle}}</p>
                    </div>
                    <div class="text-sub-wrap" style="padding:10px;">
                      <span><pre>{{tmpltData.ALIMTALK.msg}}</pre></span>
                    </div>
                    <!-- <p class="text-sub_2">템플릿 테스트입니다.<br>템플릿 테스트 템플릿 테스트 템플릿 테스트 템플릿 테스트</p> -->
                    <div v-for="(buttonInfo, idx) in tmpltData.ALIMTALK.buttons" :key="idx">
                      <a v-if="!$gfnCommonUtils.isEmpty(buttonInfo.name)" class="btnStyle1 backLightGray">{{buttonInfo.name}}</a>
                    </div>
                  </div>
                  
                </div>
              </div>
            </div>
            <!--// ALIMTALK -->
            <!-- RCS -->
            <div v-if="previewMessageType == 'RCS' && tmpltData.RCS" class="tab-pane active">
              <!-- FREE -->
              <div v-if="tmpltData.RCS.rcsPrdType == 'FREE'" class="phoneWrap">
                <img src="@/assets/images/common/phoneMockup1.svg" alt="RCS 프리 템플릿">
                <div class="phoneTextWrap scroll-yc">
                  <div class="phoneText1">
                    <p v-if="tmpltData.RCS.mergeData && tmpltData.RCS.mergeData.length > 0" class="mt15"><pre>{{tmpltData.RCS.mergeData[0].description}}</pre></p>
                    <br v-if="tmpltData.msgKind == 'A'"/>
                    <span v-if="tmpltData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(tmpltData.RCS.footer)">
                      {{tmpltData.RCS.footer}}
                    </span>
                  </div>
                </div>
              </div>
              <!--// FREE -->
              <!-- DESCRIPTION -->
              <div v-if="tmpltData.RCS.rcsPrdType == 'DESCRIPTION'" class="phoneWrap">
                <img src="@/assets/images/common/phoneMockup1.svg" alt="RCS 프리 템플릿">
                <div class="phoneTextWrap scroll-yc">
                  <div class="phoneText1">
                    <img :src="tmpltData.RCS.rcsDesFormNm | getIconURlByFormNm" style="width:70px;">
                    <p v-if="tmpltData.RCS.mergeData && tmpltData.RCS.mergeData.length > 0" class="mt15"><pre>{{tmpltData.RCS.mergeData[0].description}}</pre></p>
                    <div v-if="tmpltData.RCS.buttons && tmpltData.RCS.buttons.length > 0">
                      <p 
                        v-for="(btn, idx) in tmpltData.RCS.buttons[0].suggestions" 
                        :key="idx" 
                        class="text-center mt20" 
                        style="color:#69C8FF"
                      >{{btn.action.displayText}}</p>
                    </div>
                  </div>
                </div>
              </div>
              <!--// DESCRIPTION -->
              <!-- SMS -->
              <div v-if="tmpltData.RCS.rcsPrdType == 'SMS'" class="phoneWrap">
                <img src="@/assets/images/common/phoneMockup1.svg" alt="RCS 프리 템플릿">
                <div class="phoneTextWrap scroll-yc">
                  <div class="phoneText1">
                    <p v-if="tmpltData.RCS.mergeData && tmpltData.RCS.mergeData.length > 0" class="mt15"><pre>{{tmpltData.RCS.mergeData[0].description}}</pre></p>
                    <br v-if="tmpltData.msgKind == 'A'"/>
                    <span v-if="tmpltData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(tmpltData.RCS.footer)">
                      {{tmpltData.RCS.footer}}
                    </span>
                    <div v-if="tmpltData.RCS.buttons && tmpltData.RCS.buttons.length > 0">
                      <p 
                        v-for="(btn, idx) in tmpltData.RCS.buttons[0].suggestions" 
                        :key="idx" 
                        class="text-center mt20" 
                        style="color:#69C8FF"
                      >{{btn.action.displayText}}</p>
                    </div>
                  </div>
                </div>
              </div>
              <!--// SMS -->
              <!-- LMS -->
              <div v-if="tmpltData.RCS.rcsPrdType == 'LMS'" class="phoneWrap">
                <img src="@/assets/images/common/phoneMockup1.svg" alt="RCS 프리 템플릿">
                <div class="phoneTextWrap scroll-yc">
                  <div class="phoneText1">
                    <h5 v-if="tmpltData.RCS.mergeData && tmpltData.RCS.mergeData.length > 0 && tmpltData.RCS.mergeData[0].title">{{tmpltData.RCS.mergeData[0].title}}</h5>
                    <p v-if="tmpltData.RCS.mergeData && tmpltData.RCS.mergeData.length > 0 && tmpltData.RCS.mergeData[0].description" class="mt15"><pre>{{tmpltData.RCS.mergeData[0].description}}</pre></p>
                    <br v-if="tmpltData.msgKind == 'A'"/>
                    <span v-if="tmpltData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(tmpltData.RCS.footer)">
                      {{tmpltData.RCS.footer}}
                    </span>
                    <div v-if="tmpltData.RCS.buttons && tmpltData.RCS.buttons.length > 0">
                      <p 
                        v-for="(btn, idx) in tmpltData.RCS.buttons[0].suggestions" 
                        :key="idx" 
                        class="text-center mt20" 
                        style="color:#69C8FF"
                      >{{btn.action.displayText}}</p>
                    </div>
                  </div>
                </div>
              </div>
              <!--// LMS -->
              <!-- 세로형 SHORT -->
              <!-- style="width: 240px;height: 200px;" => short, tall 해서 사이즈가 있으나 RCS는 2:1 로 이미지가 등록된다. -->
              <div v-if="tmpltData.RCS.rcsPrdType == 'SHORT'" class="phoneWrap">
                <img src="@/assets/images/common/phoneMockup1.svg" alt="RCS 프리 템플릿">
                <div class="phoneTextWrap scroll-yc">
                  <img 
                    v-if="tmpltData.msgType == 'IMAGE' 
                      && tmpltData.RCS.mergeData
                      && tmpltData.RCS.mergeData.length > 0
                      && tmpltData.RCS.mergeData[0].mediaUrl" 
                    :src="tmpltData.RCS.mergeData[0].mediaUrl" 
                    style="width: 240px;height: 120px;"
                    alt="프리 템플릿"
                  >
                  <img v-else src="@/assets/images/common/cardThumImg2_2.png" alt="카드 썸네일">
                  <div style="background:#fff; border-radius: 0 0 5px 5px; min-height:180px" class="pd20">
                    <h5 v-if="tmpltData.RCS.mergeData && tmpltData.RCS.mergeData.length > 0 && tmpltData.RCS.mergeData[0].title">{{tmpltData.RCS.mergeData[0].title}}</h5>
                    <div>
                      <p v-if="tmpltData.RCS.mergeData && tmpltData.RCS.mergeData.length > 0 && tmpltData.RCS.mergeData[0].description" class="mt15"><pre>{{tmpltData.RCS.mergeData[0].description}}</pre></p>
                      <span v-if="tmpltData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(tmpltData.RCS.footer)">
                        <br/>{{tmpltData.RCS.footer}}
                      </span>
                    </div>
                    <div v-if="tmpltData.RCS.buttons && tmpltData.RCS.buttons.length > 0">
                      <p 
                        v-for="(btn, idx) in tmpltData.RCS.buttons[0].suggestions" 
                        :key="idx" 
                        class="text-center mt20" 
                        style="color:#69C8FF"
                      >{{btn.action.displayText}}</p>
                    </div>
                  </div>
                </div>
              </div>
              <!--// 세로형 SHORT -->
              <!-- 세로형 TALL -->
              <!-- style="width: 240px;height: 300px;" => short, tall 해서 사이즈가 있으나 RCS는 2:1 로 이미지가 등록된다. -->
              <div v-if="tmpltData.RCS.rcsPrdType == 'TALL1'" class="phoneWrap">
                <img src="@/assets/images/common/phoneMockup1.svg" alt="RCS 프리 템플릿">
                <div class="phoneTextWrap scroll-yc">
                  <img 
                    v-if="tmpltData.msgType == 'IMAGE' 
                      && tmpltData.RCS.mergeData
                      && tmpltData.RCS.mergeData.length > 0
                      && tmpltData.RCS.mergeData[0].mediaUrl" 
                    :src="tmpltData.RCS.mergeData[0].mediaUrl" 
                    style="width: 240px;height: 120px;"
                    alt="프리 템플릿"
                  >
                  <img v-else src="@/assets/images/common/cardThumImg2_2.png" alt="카드 썸네일">
                  <div style="background:#fff; border-radius: 0 0 5px 5px; min-height:180px" class="pd20">
                    <h5 v-if="tmpltData.RCS.mergeData && tmpltData.RCS.mergeData.length > 0 && tmpltData.RCS.mergeData[0].title">{{tmpltData.RCS.mergeData[0].title}}</h5>
                    <div>
                      <p v-if="tmpltData.RCS.mergeData && tmpltData.RCS.mergeData.length > 0 && tmpltData.RCS.mergeData[0].description" class="mt15"><pre>{{tmpltData.RCS.mergeData[0].description}}</pre></p>
                      <span v-if="tmpltData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(tmpltData.RCS.footer)">
                        <br/>{{tmpltData.RCS.footer}}
                      </span>
                    </div>
                    <div v-if="tmpltData.RCS.buttons && tmpltData.RCS.buttons.length > 0">
                      <p 
                        v-for="(btn, idx) in tmpltData.RCS.buttons[0].suggestions" 
                        :key="idx" 
                        class="text-center mt20" 
                        style="color:#69C8FF"
                      >{{btn.action.displayText}}</p>
                    </div>
                  </div>
                </div>
              </div>
              <!--// 세로형 TALL -->



              




              

              <!-- CAROUSEL -->
              <div v-if="tmpltData.RCS.rcsPrdType == 'TALL'" class="cardBxsliderWrap">
              <!-- <div v-if="tmpltData.RCS.rcsPrdType == 'CAROUSEL'" class="cardBxsliderWrap"> -->
                <div class="phoneWrap">
                  <img src="@/assets/images/common/phoneMockup1.svg" alt="RCS 프리 템플릿">
                  <div class="phoneCardWrap">
                    <p class="color000">[WEB발신] (광고)</p>
                    <ul class="cardBxslider mt10">
                      <li class="slide cardBox">
                        <img :src="tmpltData.RCS.mergeData[0].mediaUrl" alt="카드 썸네일">
                        <div>
                          <div class="scroll-y">
                            <p class="color000 font-size13">타이틀 영역1</p>
                          </div>
                          <p class="color3 font-size10 mt5">무료수신거부:</p>
                        </div>
                      </li>
                      <li class="slide cardBox">
                        <img :src="tmpltData.RCS.mergeData[0].mediaUrl" alt="카드 썸네일">
                        <div>
                          <div class="scroll-y">
                            <p class="color000 font-size13">타이틀 영역2</p>
                          </div>
                          <p class="color3 font-size10 mt5">무료수신거부:</p>
                        </div>
                      </li>
                      <li class="slide cardBox">
                        <img :src="tmpltData.RCS.mergeData[0].mediaUrl" alt="카드 썸네일">
                        <div>
                          <div class="scroll-y">
                            <p class="color000 font-size13">타이틀 영역3</p>
                          </div>
                          <p class="color3 font-size10 mt5">무료수신거부:</p>
                        </div>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
              <!--// CAROUSEL -->

            </div>
            <!--// RCS -->
          </div>
          
          <div :class="'phone_04_btn7' + (showBxPage ? ' mt20' : '')">
            <ul>
              <li v-for="(chTypeInfo, idx) in tmpltData.chTypeList.slice(0, 3)" :key="idx" :class="(previewMessageType == chTypeInfo ? 'active' : '')">
                <a href="#" @click.prevent="fnChgPreviewMessageType(chTypeInfo)" :title="chTypeInfo">{{chInfo[chTypeInfo]}}</a>
              </li>
            </ul>
            <ul v-if="tmpltData.chTypeList.length > 3" class="mt5">
              <li v-for="(chTypeInfo, idx) in tmpltData.chTypeList.slice(3, tmpltData.chTypeList.length)" :key="idx" :class="(previewMessageType == chTypeInfo ? 'active' : '')">
                <a href="#" @click.prevent="fnChgPreviewMessageType(chTypeInfo)" :title="chTypeInfo">{{chInfo[chTypeInfo]}}</a>
              </li>
            </ul>
          </div>
          
          
        </div>
      </div>
      <div class="of_h inline-block vertical-top consoleCon" style="width:60%">
        <div class="of_h user-phone">
          <div class="float-left" style="width:24%">
            <h4>01  템플릿</h4>
          </div>
          <div class="float-left" style="width:76%">
            <div class="of_h">
              <div style="width:22%" class="float-left">
                <!-- <h5>텍스트  형,  정보 형</h5> -->
                <h5>{{tmpltData.msgTypeName}}, {{tmpltData.msgKindName}}</h5>
              </div>
              <div v-if="fnContainsChannel('PUSH')" style="width:78%">
                <h5>발송정책 : {{tmpltData.PUSH.serviceCode}}</h5>
                <!-- 
                <input type="radio" name="serviceCode" value="ALL" id="serviceCode_ALL" v-model="tmpltData.PUSH.serviceCode" disabled>
                <label for="serviceCode_ALL" class="mr30">ALL</label>
                <input type="radio" name="serviceCode" value="FCM" id="serviceCode_FCM" v-model="tmpltData.PUSH.serviceCode" disabled>
                <label for="serviceCode_FCM" class="mr30">FCM</label>
                <input type="radio" name="serviceCode" value="APNS" id="serviceCode_APNS" v-model="tmpltData.PUSH.serviceCode" disabled>
                <label for="serviceCode_APNS">APNS</label>
                -->
              </div>
              <div class="float-right" style="width:78%">
                <input type="text" class="inputStyle" :value="tmpltData.tmpltTitle" disabled>
              </div>
            </div>
          </div>
        </div>
        
        <hr>

        <div class="of_h user-phone">
          <div class="float-left" style="width:24%">
            <h4>02  수신자 선택</h4>
          </div>
          <div class="float-left" style="width:76%">
            <div class="of_h">
              <div style="width:22%" class="height40 float-left">
                <h5>수신자 *</h5>
              </div>
              <div style="width:100%">
                <div>
                  <!-- 
                  <input type="radio" name="Recipient" value="Directly" id="Directly" checked=""> <label for="Directly" class="mr30">수신자 직접입력</label>
                  <input type="radio" name="Recipient" value="search" id="search"> <label for="search" class="mr30">주소록 검색</label>
                  <input type="radio" name="Recipient" value="excel" id="excel"> <label for="excel" class="mr10">엑셀 업로드</label>
                  -->
                  <input type="radio" id="cuInputType_DICT" name="cuInputType" value="DICT" v-model="sendData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType" activity="READ">
                  <label for="cuInputType_DICT" class="mr30">수신자 직접입력</label>
                  <input type="radio" id="cuInputType_ADDR" name="cuInputType" value="ADDR" v-model="sendData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType" activity="READ">
                  <label for="cuInputType_ADDR" class="mr30">주소록 검색</label>
                  <input type="radio" id="cuInputType_EXCEL" name="cuInputType" value="EXCEL" v-model="sendData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType" activity="READ">
                  <label for="cuInputType_EXCEL" class="mr10">엑셀 업로드</label>
                  <a href="#" @click.prevent="fnExcelTmplteDownLoad" class="btnStyle1 backLightGray" title="샘플">샘플 <i class="far fa-arrow-to-bottom"></i></a>
                  <input ref="excelFile" type="file" style="display:none;">
                </div>
              </div>
            </div>
            <div v-if="fnContainsChannel('PUSH')" class="of_h consolMarginTop">
              <div style="width:22%" class="float-left">
                <h5>APP_ID</h5>
              </div>
              <div class="of_h" style="width:78%">
                <input type="text" class="inputStyle" style="width:100%" :value="tmpltData.PUSH.appId" disabled>
              </div>
            </div>
            <div class="of_h consolMarginTop">
              <div class="of_h float-right" style="width:78%">
                <div class="float-right" style="width:100%">
                  <textarea class="textareaStyle height120" v-model="sendData.cuInfo" disabled></textarea>
                </div>
                <p class="float-right">발송 대상자 : {{recvCnt}}명</p>
              </div>
            </div>
          </div>
          
        </div>
        <hr>

        <div class="of_h user-phone">
          <div style="width:22%" class="float-left">
            <h4>03  발송옵션 선택</h4>
          </div>
          <div style="width:78%" class="float-left">
            <div class="of_h">
              <div class="float-left" style="width:22%">
                <h5>발송시간 *</h5>
              </div>
              <div class="float-left" style="width:23%">
                <input type="radio" id="rsrvSendYn_N" value="N" v-model="sendData.rsrvSendYn">
                <label for="rsrvSendYn_N" class="mr20">즉시</label>
                <input type="radio" id="rsrvSendYn_Y" value="Y" v-model="sendData.rsrvSendYn">
                <label for="rsrvSendYn_Y">예약</label>
              </div>
              <div v-if="sendData.rsrvSendYn == 'Y'" class="float-left" style="width:18%">
                <Calendar @update-date="fnUpdateRsrvDate" calendarId="rsrvDate" classProps="datepicker inputStyle" :initDate="sendData.rsrvDate"></Calendar>
              </div>
              <div v-if="sendData.rsrvSendYn == 'Y'" class="float-right" style="width:36%">
                <select class="selectStyle2" style="width:47%" v-model="sendData.rsrvHH">
                  <option value="00">00</option>
                  <option v-for="hh in 23" :key="hh" :value="hh > 9 ? hh : '0'+hh">{{hh > 9 ? hh : '0'+hh}}</option>
                </select>
                : <select class="selectStyle2" style="width:47%" v-model="sendData.rsrvMM">
                  <option value="00">00</option>
                  <option v-for="mm in 5" :key="mm" :value="mm+'0'">{{mm+'0'}}</option>
                </select>
              </div>
            </div>
            <div class="of_h consolMarginTop">
              <div style="width:22%" class="float-left">
                <h5>태그</h5>
              </div>
              <div class="of_h" style="width:78%">
                <input type="text" class="inputStyle" style="width:100%" placeholder="캠페인 ID를 입력해주세요." v-model="sendData.campaignId" maxlength="20">
              </div>
            </div>
          </div>
        </div>

        <hr>

        <div class="of_h user-phone">
          <div class="float-left" style="width:22%">
            <h4>04  테스트 발송</h4>
          </div>
          <div class="float-left" style="width:78%">
            <p>모든 채널에 메시지를 보냅니다.</p>
            <a href="#" @click.prevent="fnOpenTestSendInputPopup" class="btnStyle1 backLightGray consolMarginTop" title="테스트 발송" activity="SAVE">테스트 발송</a>
          </div>
          
        </div>
        <div class="mt20 float-right">
          <a href="#" @click.prevent="fnSendIntegratedMessage('N')" class="btnStyle2 backRed float-left mr10" title="발송" activity="SAVE">발송</a>
          <router-link :to="{ name: 'integratedSend' }" tag="a" class="btnStyle2 float-left">목록</router-link>
        </div>
      </div>
    </div>
    <DirectInputPopup :directInputOpen.sync="directInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid" :recvInfoLst="sendData.recvInfoLst"></DirectInputPopup>
    <AddressInputPopup :addressInputOpen.sync="addressInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid"></AddressInputPopup>
    <TestSendInputPopup :testSendInputOpen.sync="testSendInputOpen" :contsVarNms="sendData.contsVarNms" :testRecvInfoLst="sendData.testRecvInfoLst" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid"></TestSendInputPopup>
  </div>
</template>

<script>
import DirectInputPopup from "@/modules/message/components/bp-directInput.vue";
import AddressInputPopup from "@/modules/message/components/bp-addressInput.vue";
import Calendar from "@/components/Calendar.vue";
import TestSendInputPopup from "@/modules/message/components/bc-testSendInput.vue";

//import integratedSendApi from '../service/integratedSendApi';
import messageApi from "@/modules/message/service/messageApi.js";
import commonUtilApi from "@/modules/commonUtil/service/commonUtilApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  name: 'sendIntegMain',
  components : {
    DirectInputPopup,
    AddressInputPopup,
    Calendar,
    TestSendInputPopup
  },
  props: {
    tmpltCodeP: {
      type: String,
      require: true,
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '통합 발송';
      }
    },
  },
  data() {
    return {
      directInputOpen: false,
      addressInputOpen: false,
      testSendInputOpen: false,
      recvCnt : 0,  //수신자명수
      showBxPage : false,
      tmpltData: {
        chTypeList: [],
      },
      chInfo: {
        PUSH: "PUSH",
        SMS: "SMS",
        MMS: "MMS",
        FRIENDTALK: "친구톡",
        ALIMTALK: "알림톡",
        RCS: "RCS",
      },
      inProgress: false,
      previewMessageType : '',
      sendData: {
        requiredCuid: false,  //app 로그인 ID 필수여부
        requiredCuPhone: false,  //수신자 폰번호 필수여부
        cuInputType:'DICT',  //DICT, ADDR, EXCEL
        cuInfo:'',
        rsrvSendYn:'N',  //예약발송여부
        rsrvDate:this.$gfnCommonUtils.getCurretDate(),
        rsrvHH:'00',
        rsrvMM:'00',
        campaignId: '',
        chMappingVarList: [],  //매핑변수 채널리스트
        contsVarNms: [], //메세지 내용 변수명
        recvInfoLst: [],  //수신자정보
        testRecvInfoLst: [],  //테스트 수신자정보
      }
    }
  },
  updated() {
    if(this.previewMessageType == 'RCS') {
      this.fnSetBxslider();
      this.showBxPage = true;
    } else {
      this.showBxPage = false;
    }
  },
  mounted() {
    this.fnGetTmpltInfo();
  },
  methods: {
    fnSetBxslider(){
      jQuery('.cardBxslider').bxSlider({
        auto: false,
        autoControls: false,
        slideWidth: 204,
        minSlides: 1,
        maxSlides: 2,
        slideMargin: 10,
        controls: true,
        pager: true,
        pagerType: 'full',
        touchEnabled : (navigator.maxTouchPoints > 0),
        autoHover: false,
      });
      return true;
    },
    fnGetImageUrl(ch, fileId){
      let params = {ch: ch, fileId: fileId};
      return new Promise((resolve, reject) => {
        commonUtilApi.selectImageUrlInfo(params).then(response =>{
          var result = response.data;
          console.log('result ===> ', result);
          if(result.success) {
            resolve(this.$gfnCommonUtils.defaultIfEmpty(result.data.chImgUrl));
          } else {
            reject('');
          }
        });
      });
    },
    //메세지 미리보기 변경
    fnChgPreviewMessageType(type){
      this.previewMessageType = type;
    },
    fnOpenTestSendInputPopup(){
      this.fnSetContsVarNms();
      this.testSendInputOpen = !this.testSendInputOpen;
    },
    //테스트 발송 callback
    fnCallbackTestRecvInfoLst(testRecvInfoLst){
      if(testRecvInfoLst != null){
        this.sendData.testRecvInfoLst = testRecvInfoLst;
        this.fnSendIntegratedMessage('Y');
      } else {
        this.sendData.testRecvInfoLst = [];
      }
    },
    //발송 정보 유효성 체크
    fnValidSendMsgData(testSendYn){
      if(this.fnSetContsVarNms() == false){
        return false;
      }
      if(!this.tmpltCodeP){
        confirm.fnAlert(this.componentsTitle, '유효하지 않은 템플릿 정보입니다.');
        return false;
      }
      if(testSendYn == 'Y'){
        if(!this.sendData.testRecvInfoLst == null || this.sendData.testRecvInfoLst.length == 0){
          confirm.fnAlert(this.componentsTitle, '테스트 수신자 정보를 입력해주세요.');
          return false;
        }
      } else {
        if(this.sendData.cuInputType == 'DICT' || this.sendData.cuInputType == 'ADDR'){
          if(!this.sendData.recvInfoLst == null || this.sendData.recvInfoLst.length == 0){
            confirm.fnAlert(this.componentsTitle, '수신자 정보를 입력해주세요.');
            return false;
          }
        }
        if(this.sendData.cuInputType == 'EXCEL'){
          const uploadFile = this.$refs.excelFile;
          if(uploadFile.value == 0){
            confirm.fnAlert(this.componentsTitle, '엑셀파일을 등록해주세요.');
            return false;
          }
          const permitExten = 'xls,xlsx'.split(',');
          const extnIdx = uploadFile.value.lastIndexOf('.');
          const extn = uploadFile.value.substring(extnIdx+1);
          if((permitExten.indexOf(extn) < 0)){
            confirm.fnAlert(this.componentsTitle, '허용되지 않는 확장자입니다.');
            return false;
          }
        }
      }
      return true;
    },
    //통합 메시지 발송 처리
    async fnSendIntegratedMessage(testSendYn){
      if(this.inProgress){
        confirm.fnAlert(this.componentsTitle, '통합 메시지 발송 처리중입니다.');
        return;
      }

      if(this.fnValidSendMsgData(testSendYn) == false) return;

      //발송처리
      let params = Object.assign({}, this.sendData);
      params.tmpltCode = this.tmpltCodeP;
      params.testSendYn = testSendYn;

      if(testSendYn == 'Y'){
        params.recvInfoLst = Object.assign([], this.sendData.testRecvInfoLst);
        params.cuInputType = 'DICT';
        //테스트 발송은 즉시발송만 가능
        params.rsrvSendYn = 'N';
      }

      let fd = new FormData();
      fd.append('paramString', JSON.stringify(params));
      if(this.sendData.cuInputType == 'EXCEL'){
        fd.append('file', this.$refs.excelFile.files[0]);
      }

      this.inProgress = true;
      const vm = this;

      await messageApi.sendSmartMessage(fd).then(response =>{
        this.inProgress = false;
        const result = response.data;
        
        if(result.success) {
          if(testSendYn == 'Y'){
            if(!this.$gfnCommonUtils.isEmpty(result.message)){
              confirm.fnAlert(this.componentsTitle, result.message);
            } else {
              confirm.fnAlert(this.componentsTitle, '발송하였습니다.');
            }
          } else {
            if(result.data != null && !this.$gfnCommonUtils.isEmpty(result.data.feeMsg)){
              eventBus.$on('callbackEventBus', this.fnAlertFeeMsgCallBack);
              confirm.fnAlert(this.componentsTitle, result.data.feeMsg, 'ALERT', result);
            } else {
              this.fnAlertFeeMsgCallBack(result);
            }
          }
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      })
      .catch(function () {
        vm.inProgress = false;
      });
    },
    fnAlertFeeMsgCallBack(result){
      if(this.$gfnCommonUtils.isEmpty(result.message)){
        confirm.fnAlert(this.componentsTitle, '발송 요청 처리 되었습니다.');
      } else {
        confirm.fnAlert(this.componentsTitle, result.message);
      }
    },
    fnUpdateRsrvDate(sltDate){
      this.sendData.rsrvDate = sltDate;
    },
    fnClickCuInputType(e){
      if(this.sendData.cuInputType == e.target.value){
        this.fnChgCuInputType('N');
      }
    },
    //수신자 입력 타입 변경시
    fnChgCuInputType(chgYn){
      if(this.$gfnCommonUtils.defaultIfEmpty(chgYn, 'Y') == 'Y'){
        this.fnCallbackRecvInfoLst(null);  //수신자 입력 타입 변경시 수신자 정보 초기화
      }
      if(this.fnSetContsVarNms() == false){
        return;
      }

      if(this.sendData.cuInputType == 'DICT'){  //직접입력
        //수신자 직접입력 팝업 호출
        this.directInputOpen = true;
      } else if(this.sendData.cuInputType == 'ADDR'){  //주소록
        //주소록 검색 팝업 호출
        this.addressInputOpen = true;
      } else if(this.sendData.cuInputType == 'EXCEL'){  //엑셀
        //엑셀파일찾기 호출
        this.$refs.excelFile.click();
      }
    },
    //수신자 정보 callback
    fnCallbackRecvInfoLst(recvInfoLst, addYn) {
      if(recvInfoLst != null){
        if(this.$gfnCommonUtils.defaultIfEmpty(addYn, 'N') == 'Y'){
          this.sendData.recvInfoLst = this.sendData.recvInfoLst.concat(recvInfoLst);
        } else {
          this.sendData.recvInfoLst = recvInfoLst;
        }
        //수신자 중복제거
        this.fnDelDuplRecvInfo();

        this.recvCnt = this.sendData.recvInfoLst.length;
        this.sendData.cuInfo = JSON.stringify(this.sendData.recvInfoLst);
      } else {
        this.recvCnt = 0;
        this.sendData.recvInfoLst = [];
        this.sendData.cuInfo = '';
      }
    },
    //수신자 중복 제거
    fnDelDuplRecvInfo(){
      const vm = this;
      let key, key2;
      this.sendData.recvInfoLst = this.sendData.recvInfoLst.filter(function(item, i){
        return (
          vm.sendData.recvInfoLst.findIndex((item2) => {
            key = '';
            if ('phone' in item) key += item.phone;
            if ('cuid' in item) key += item.cuid;
            key2 = '';
            if ('phone' in item2) key2 += item2.phone;
            if ('cuid' in item2) key2 += item2.cuid;
            return key === key2
          }) === i
        );
      });
    },
    fnSetContsVarNms(){
      const vm = this;
      let chMappingVarInfo = {};
      let chMappingVarList = [];
      let conts = '';
      let chTmpltInfo = {};
      let varNms = [];
      let tempVarNms = [];
      let containRsvNm = false;

      this.tmpltData.chTypeList.forEach(ch => {
        conts = '';
        chTmpltInfo = vm.tmpltData[ch];

        if(ch == 'PUSH' || ch == 'SMS' || ch == 'MMS' || ch == 'FRIENDTALK' || ch == 'ALIMTALK'){
          conts = chTmpltInfo.msg;
        } else if(ch == 'RCS'){
          if(chTmpltInfo.mergeData && chTmpltInfo.mergeData.length > 0){
            //TODO : 프리템플릿 일경우인데 다른 템플릿일 경우 다른수 있다.
            //FREE, SMS, LMS, 서술형
            conts = this.$gfnCommonUtils.defaultIfEmpty(chTmpltInfo.mergeData[0].title, '');
            conts += this.$gfnCommonUtils.defaultIfEmpty(chTmpltInfo.mergeData[0].description, '');
          }
        }

        console.log('conts ch ===> ', ch);
        console.log('conts ===> ', conts);

        if(ch == 'RCS'){
          conts.replace(/\{\{(([a-z|A-Z|0-9|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|_])+)\}\}/g, function($0, $1) {
            tempVarNms.push($1);
            console.log('conts rcs var ===> ', $1);
          });
        } else {
          conts.replace(/#\{(([a-z|A-Z|0-9|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|_])+)\}/g, function($0, $1) {
            tempVarNms.push($1);
          });
        }

        chMappingVarInfo = {};
        chMappingVarInfo.ch = ch;
        chMappingVarInfo.varNms = this.fnSetArrayRemoveDupliVal(tempVarNms);
        chMappingVarList.push(chMappingVarInfo);
        varNms = varNms.concat(tempVarNms);
        tempVarNms = [];
      });
      
      if(containRsvNm){
        confirm.fnAlert(this.componentsTitle, '발송 내용 변수 cuid, phone 은 예약어로 사용하실 수 없습니다.');
        return false;
      } else {
        console.log('varNms ===> ', varNms);
        this.sendData.contsVarNms = this.fnSetArrayRemoveDupliVal(varNms);
        this.sendData.chMappingVarList = Object.assign([], chMappingVarList);
        console.log('contsVarNms ===> ', this.sendData.contsVarNms);
        console.log('chMappingVarList ===> ', chMappingVarList);
        return true;
      }
    },
    //array에 중복 항목을 제거한다.
    fnSetArrayRemoveDupliVal(array){
      let seen = {};
      return array.filter(function(item) {
        return seen.hasOwnProperty(item) ? false : (seen[item] = true);
      });
    },
    //템플릿 정보 조회
    fnGetTmpltInfo(){
      let params = {tmpltCode: this.tmpltCodeP};
      messageApi.selectSmartTmpltInfo(params).then(response => {
        let result = response.data;
        if(result.success) {
          const targetField = ['tmpltTitle', 'tmpltInfo'];
          console.log('result ===>> ', Object.assign({}, result));
          let tempData = Object.assign({}, result.data);
          this.$gfnCommonUtils.unescapeXssFields(tempData, targetField);
          tempData.chTypeList = JSON.parse(tempData.chTypeList);
          tempData.tmpltInfo = JSON.parse(tempData.tmpltInfo);
          
          this.fnSetTmpltInfoByChannel(tempData);
          if(tempData.msgType == 'IMAGE' 
            && this.fnContainsChannel('MMS', tempData.chTypeList)
            && tempData.MMS.fileIdLst){
            tempData.MMS.fileUrlLst = [];
            tempData.MMS.fileIdLst.forEach(fileId => {
              this.fnGetImageUrl('MMS', fileId).then(url => {console.log('url ===>> ', url); tempData.MMS.fileUrlLst.push(url)});
            });
          }

          this.tmpltData = Object.assign({}, tempData);
          this.previewMessageType = this.tmpltData.chTypeList[0];
          console.log('this.tmpltData.chTypeList[0] ===>> ', this.tmpltData.chTypeList[0]);
          console.log('previewMessageType ===>> ', this.previewMessageType);

          if(this.fnContainsChannel('PUSH')){
            this.sendData.requiredCuid = true;
          }
          if(this.fnContainsChannel('SMS')
            || this.fnContainsChannel('MMS')
            || this.fnContainsChannel('FRIENDTALK')
            || this.fnContainsChannel('ALIMTALK')
            || this.fnContainsChannel('RCS')){
            this.sendData.requiredCuPhone = true;
          }
          
          console.log('requiredCuid ===>> ', this.sendData.requiredCuid);
          console.log('requiredCuPhone ===>> ', this.sendData.requiredCuPhone);
          console.log('tmpltData ===>> ', this.tmpltData);

        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnSetTmpltInfoByChannel(tempData){
      let chTypeList = tempData.chTypeList;
      let tmpltInfo = tempData.tmpltInfo;
      
      if(chTypeList != null && chTypeList.length > 0){
        chTypeList.forEach((chType, idx) => {
          tempData[chType] = tmpltInfo[idx]['data'];
        });
      }
    },
    //채널정보 존재 확인
    fnContainsChannel(ch, obj){
      if(obj) return obj.includes(ch);
      return this.tmpltData.chTypeList.includes(ch);
    },
    async fnExcelTmplteDownLoad(){
      if(this.fnSetContsVarNms() == false) return;
      var params = {
        contsVarNms : this.sendData.contsVarNms,
        requiredCuid: this.sendData.requiredCuid,
        requiredCuPhone: this.sendData.requiredCuPhone
      };
      await messageApi.excelDownSendSmartRecvTmplt(params);
    },
  }
}
</script>