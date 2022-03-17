<template>
  <div>
    <div class="contentHeader">
      <h2>{{componentsTitle}}&nbsp;<span style="font-size: 12px;color: red;">(친구톡 광고 메시지는 20시~8시 발송 시 실패 처리 됩니다.)</span></h2>
      <!-- <h2>{{componentsTitle}}&nbsp;<span style="font-size: 12px;color: red;">(친구톡 광고 메시지는 20시~8시 발송 시 실패 처리 됩니다.)</span><span v-if="nightSendYn == 'N'" class="ml20 font-size12 color1">야간 메시지 발송 제한으로 {{nightSendSthh}}:{{nightSendStmm}} ~ 다음날 {{nightSendEdhh}}:{{nightSendEdmm}} 까지 메시지 발송을 할 수 없습니다.<i class="fas fa-question-circle toolTip ml5"><span class="toolTipText" style="width:260px">야간 메시지 발송 제한 해제는 [관리자 콘솔] 프로젝트 기본정보에서 세팅 할 수 있습니다.</span></i></span></h2> -->
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
                  <div v-if="!$gfnCommonUtils.isEmpty(tmpltData.PUSH.title)" class="phoneText1">
                    <p>{{tmpltData.PUSH.title}}</p>
                  </div>
                  <div
                    v-if="tmpltData.msgType == 'IMAGE' && tmpltData.PUSH.ext && tmpltData.PUSH.ext.imageUrl"
                    class="phoneText2 mt10 text-center simulatorImg"
                    :style="'padding:65px;background-image: url('+tmpltData.PUSH.ext.imageUrl+');'"
                  >
                  </div>
                  <p class="consolMarginTop">
                    <pre>{{tmpltData.PUSH.msg}}</pre>
                    <!-- 템플릿에서 메시지 내용에 넣어줌
                    <br/>
                    {{tmpltData.PUSH.rcvblcInput}}
                    -->
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
                      v-if="tmpltData.msgType == 'IMAGE' && tmpltData.FRIENDTALK.image && tmpltData.FRIENDTALK.image.imgUrl"
                      class="phoneText2 text-center simulatorImg"
                      :style="'padding:65px;background-image: url('+tmpltData.FRIENDTALK.image.imgUrl+');'"
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
                  <div class="phoneText1 mb10">
                    <p>{{tmpltData.MMS.title}}</p>
                  </div>
                  <div v-if="tmpltData.msgType == 'IMAGE'">
                    <div v-for="(fileUrl, idx) in tmpltData.MMS.fileUrlLst" :key="idx" class="phoneText2 mt10 text-center simulatorImg"
                      :style="'padding:65px;background-image: url('+fileUrl+');'">
                    </div>
                  </div>

                  <div class="phoneText1">
                    <p class="font-size14 color4 mt10">{{tmpltData.MMS.msg}}</p>
                  </div>

                  <!--<p class="consolMarginTop"><pre>{{tmpltData.MMS.msg}}</pre></p>-->
                  <!-- 템플릿에서 메시지 내용에 넣어줌
                  <br v-if="tmpltData.msgKind == 'A'"/>
                  <span v-if="tmpltData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(tmpltData.MMS.rcvblcInput)">
                    {{tmpltData.MMS.rcvblcInput}}
                  </span>
                  -->
                </div>
              </div>
            </div>
            <!--// MMS -->
            <!-- SMS -->
            <div v-if="previewMessageType == 'SMS' && tmpltData.SMS" class="tab-pane active">
              <div class="phoneWrap">
                <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
                <div class="phoneTextWrap scroll-yc">
                  <div class="phoneText1 mb10">
                    <p>{{tmpltData.SMS.callback}}</p>
                  </div>
                  <div class="phoneText1">
                    <p class="font-size14 color4 mt10">{{tmpltData.SMS.msg}}</p>
                  </div>

                  
                  <!-- 템플릿에서 메시지 내용에 넣어줌
                  <br v-if="tmpltData.msgKind == 'A'"/>
                  <span v-if="tmpltData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(tmpltData.SMS.rcvblcInput)">
                    {{tmpltData.SMS.rcvblcInput}}
                  </span>
                  -->
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
                  <p class="color000">
                    <span>[WEB발신]</span>
                  </p>
                  <div class="phoneText1">
                    <p v-if="tmpltData.RCS.mergeData && tmpltData.RCS.mergeData.length > 0" class="mt15">
                      <pre>{{tmpltData.RCS.mergeData[0].description}}</pre>
                    </p>
                    <br v-if="tmpltData.msgKind == 'A'"/>
                    <span v-if="tmpltData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(tmpltData.RCS.footer)">
                      수신거부번호 : {{tmpltData.RCS.footer}}
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
              <!-- CELL -->
              <div v-if="tmpltData.RCS.rcsPrdType == 'CELL'" class="phoneWrap">
                <img src="@/assets/images/common/phoneMockup1.svg" alt="RCS 프리 템플릿">
                <div class="phoneTextWrap scroll-yc">
                  <div class="phoneText1 of_h">
                    <img :src="tmpltData.RCS.rcsStyleFormNm | getIconURlByFormNm" style="width:70px;">

                    <div v-if="tmpltData.RCS.mergeData && tmpltData.RCS.mergeData.length > 0">
                      <div v-for="(cellContent, idx) in tmpltData.RCS.mergeData[0].content" :key="idx">
                        <div class="of_h consolMarginTop">
                          <p v-if="cellContent.description0" :class="'lc-1'+ (cellContent.description1 ? ' inline-block' : '')">{{cellContent.description0}}</p>
                          <p v-if="cellContent.description1" class="lc-1 inline-block float-right">{{cellContent.description1}}</p>
                        </div>
                        <hr v-if="tmpltData.RCS.hrStyleArr && tmpltData.RCS.hrStyleArr.length > idx && tmpltData.RCS.hrStyleArr[idx]"/>
                      </div>
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
              <!--// CELL -->
              <!-- SMS -->
              <div v-if="tmpltData.RCS.rcsPrdType == 'SMS'" class="phoneWrap">
                <img src="@/assets/images/common/phoneMockup1.svg" alt="RCS 프리 템플릿">
                <div class="phoneTextWrap scroll-yc">
                  <p class="color000">
                    <span>[WEB발신]</span>
                  </p>
                  <div class="phoneText1">
                    <p v-if="tmpltData.RCS.mergeData && tmpltData.RCS.mergeData.length > 0" class="mt15"><pre>{{tmpltData.RCS.mergeData[0].description}}</pre></p>
                    <br v-if="tmpltData.msgKind == 'A'"/>
                    <span v-if="tmpltData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(tmpltData.RCS.footer)">
                      수신거부번호 : {{tmpltData.RCS.footer}}
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
                  <p class="color000">
                    <span>[WEB발신]</span>
                  </p>
                  <div class="phoneText1">
                    <h5 v-if="tmpltData.RCS.mergeData && tmpltData.RCS.mergeData.length > 0 && tmpltData.RCS.mergeData[0].title">{{tmpltData.RCS.mergeData[0].title}}</h5>
                    <p v-if="tmpltData.RCS.mergeData && tmpltData.RCS.mergeData.length > 0 && tmpltData.RCS.mergeData[0].description" class="mt15"><pre>{{tmpltData.RCS.mergeData[0].description}}</pre></p>
                    <br v-if="tmpltData.msgKind == 'A'"/>
                    <span v-if="tmpltData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(tmpltData.RCS.footer)">
                      수신거부번호 : {{tmpltData.RCS.footer}}
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
                  <p class="color000">
                    <span>[WEB발신]</span>
                  </p>
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
                        <br/>수신거부번호 : {{tmpltData.RCS.footer}}
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
              <div v-if="tmpltData.RCS.rcsPrdType == 'TALL'" class="phoneWrap">
                <img src="@/assets/images/common/phoneMockup1.svg" alt="RCS 프리 템플릿">
                <div class="phoneTextWrap scroll-yc">
                  <p class="color000">
                    <span>[WEB발신]</span>
                  </p>
                  <img
                    v-if="tmpltData.msgType == 'IMAGE'
                      && tmpltData.RCS.mergeData
                      && tmpltData.RCS.mergeData.length > 0
                      && tmpltData.RCS.mergeData[0].mediaUrl"
                    :src="tmpltData.RCS.mergeData[0].mediaUrl"
                    style="width: 240px;height: 300px;"
                    alt="프리 템플릿"
                  >
                  <img v-else src="@/assets/images/common/cardThumImg2_2.png" alt="카드 썸네일">
                  <div style="background:#fff; border-radius: 0 0 5px 5px; min-height:180px" class="pd20">
                    <h5 v-if="tmpltData.RCS.mergeData && tmpltData.RCS.mergeData.length > 0 && tmpltData.RCS.mergeData[0].title">{{tmpltData.RCS.mergeData[0].title}}</h5>
                    <div>
                      <p v-if="tmpltData.RCS.mergeData && tmpltData.RCS.mergeData.length > 0 && tmpltData.RCS.mergeData[0].description" class="mt15"><pre>{{tmpltData.RCS.mergeData[0].description}}</pre></p>
                      <span v-if="tmpltData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(tmpltData.RCS.footer)">
                        <br/>수신거부번호 : {{tmpltData.RCS.footer}}
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
              <!-- CSHORT -->
              <div v-if="tmpltData.RCS.rcsPrdType == 'CSHORT'" class="cardBxsliderWrap">
                <div class="phoneWrap">
                  <img src="@/assets/images/common/phoneMockup1.svg" alt="RCS 프리 템플릿">
                  <div class="phoneCardWrap">
                    <p class="color000">
                      <span>[WEB발신]</span>
                    </p>
                    <ul class="cardBxslider mt10" style="list-style-type:none;display:none">
                      <li v-for="(msgData, idx) in tmpltData.RCS.mergeData" :key="idx" class="slide cardBox">
                        <img
                          v-if="tmpltData.msgType == 'IMAGE' && msgData.mediaUrl"
                          :src="msgData.mediaUrl"
                          style="width: 204px;height: 172px;"
                          alt="프리 템플릿"
                        >
                        <div>
                          <div class="scroll-y">
                            <p class="color000 font-size13">{{msgData.title}}</p>
                            <p class="mt15"><pre>{{msgData.description}}</pre></p>
                          </div>
                        </div>
                        <div v-if="tmpltData.RCS.buttons && tmpltData.RCS.buttons.length > idx">
                          <p
                            v-for="(btn, sIdx) in tmpltData.RCS.buttons[idx].suggestions"
                            :key="sIdx"
                            class="text-center mt20"
                            style="color:#69C8FF"
                          >{{btn.action.displayText}}</p>
                        </div>
                        <p  v-if="tmpltData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(tmpltData.RCS.footer)" class="color3 font-size10 mt5">수신거부번호: {{tmpltData.RCS.footer}}</p>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
              <!--// CAROUSEL -->
              <!-- CTALL -->
              <div v-if="tmpltData.RCS.rcsPrdType == 'CTALL'" class="cardBxsliderWrap">
                <div class="phoneWrap">
                  <img src="@/assets/images/common/phoneMockup1.svg" alt="RCS 프리 템플릿">
                  <div class="phoneCardWrap">
                    <p class="color000">
                      <span>[WEB발신]</span>
                    </p>
                    <ul class="cardBxslider mt10" style="list-style-type:none;display:none">
                      <li v-for="(msgData, idx) in tmpltData.RCS.mergeData" :key="idx" class="slide cardBox scroll-yc">
                        <img
                          v-if="tmpltData.msgType == 'IMAGE' && msgData.mediaUrl"
                          :src="msgData.mediaUrl"
                          style="width: 204px;height: 255px;"
                          alt="프리 템플릿"
                        >
                        <div>
                          <div>
                            <p class="color000 font-size13">{{msgData.title}}</p>
                            <p class="mt15"><pre>{{msgData.description}}</pre></p>
                          </div>
                        </div>
                        <div v-if="tmpltData.RCS.buttons && tmpltData.RCS.buttons.length > idx">
                          <p
                            v-for="(btn, sIdx) in tmpltData.RCS.buttons[idx].suggestions"
                            :key="sIdx"
                            class="text-center mt20"
                            style="color:#69C8FF"
                          >{{btn.action.displayText}}</p>
                        </div>
                        <p  v-if="tmpltData.msgKind == 'A' && !$gfnCommonUtils.isEmpty(tmpltData.RCS.footer)" class="color3 font-size10 mt5">수신거부번호: {{tmpltData.RCS.footer}}</p>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
              <!--// CTALL -->

            </div>
            <!--// RCS -->
          </div>

          <div :class="'phone_04_btn7' + (showBxPage ? ' mt20' : '')">
            <ul>
              <li v-for="(chTypeInfo, idx) in tmpltData.chTypeList.slice(0, 3)" :key="idx" :class="(previewMessageType == chTypeInfo ? 'active' : '')">
                <a href="#" @click.prevent="fnChgPreviewMessageType(chTypeInfo)" :title="chTypeInfo">{{chInfo[chTypeInfo]  | lmsmms}}</a>
              </li>
            </ul>
            <ul v-if="tmpltData.chTypeList.length > 3" class="mt5">
              <li v-for="(chTypeInfo, idx) in tmpltData.chTypeList.slice(3, tmpltData.chTypeList.length)" :key="idx" :class="(previewMessageType == chTypeInfo ? 'active' : '')">
                <a href="#" @click.prevent="fnChgPreviewMessageType(chTypeInfo)" :title="chTypeInfo">{{chInfo[chTypeInfo]  | lmsmms}}</a>
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
                <h5>발송정책 : {{tmpltData.PUSH.serviceCode | pushSvcCd}}</h5>
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
                  <input ref="excelFile" type="file" style="display:none;" @change="fnReadFile" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
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
                <p class="float-right">발송 대상자 : {{recvCnt}}명<a @click="fnRemoveRecvInfo();" class="btnStyle1 small backWhite ml10" title="수신자 삭제">수신자 삭제</a></p>
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
                <Calendar @update-date="fnUpdateRsrvDate" calendarId="rsrvDate" classProps="datepicker inputStyle" :initDate="sendData.rsrvDate" :customOption="{minDate: '-0y'}"></Calendar>
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
                <input type="text" class="inputStyle" style="width:100%" placeholder="캠페인 ID를 입력해주세요.(숫자, 영문, '-', '_' 만 입력가능합니다.)" v-model="sendData.campaignId" v-campaignIdFilter maxlength="20">
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
            <!--<p>모든 채널에 메시지를 보냅니다.</p>-->
            <a href="#" @click.prevent="fnOpenTestSendInputPopup" class="btnStyle1 backLightGray consolMarginTop" title="테스트 발송" activity="SAVE">테스트 발송</a>
          </div>
        </div>
      <div v-if="feeType">
      <hr>
        <div class="of_h user-phone">
          <div style="width:24%" class="float-left">
            <h4>발송제한 금액</h4>
          </div>
          <div style="width:76%" class="float-left">
            <div class="of_h">
              <div style="width:40%" class="float-left">
                <h5 style="margin: 5px 0;">월 발송금액 / 월 발송제한금액</h5>
              </div>
              <div class="of_h" style="width:60%;">
                <p style="font-size: 14px; margin-top: 3px;">{{monthAmount | comma}} / {{monSenderLimitAmout | comma}}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
        <div class="mt20 float-right">
          <a href="#" @click.prevent="fnSendSmartMessage('N')" class="btnStyle2 backRed float-left mr10" title="발송" activity="SAVE">발송</a>
          <router-link v-if="tmpltType == 'S'" :to="{ name: 'smartSendList' }" tag="a" class="btnStyle2 float-left">목록</router-link>
          <router-link v-else :to="{ name: 'multiSendList' }" tag="a" class="btnStyle2 float-left">목록</router-link>
        </div>
      </div>
    </div>
    <DirectInputPopup :directInputOpen.sync="directInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid" :recvInfoLst="sendData.recvInfoLst"></DirectInputPopup>
    <AddressInputPopup :addressInputOpen.sync="addressInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid"></AddressInputPopup>
    <TestSendInputPopup :testSendInputOpen.sync="testSendInputOpen" :contsVarNms="sendData.contsVarNms" :requiredCuPhone="sendData.requiredCuPhone" :requiredCuid="sendData.requiredCuid" ref="testSendInputPopup"></TestSendInputPopup>
    <!-- <nightSendLimitPopup :nightSendLimitY.sync="nightSendLimitYn" :nightSendSthh="this.nightSendSthh" :nightSendStmm="this.nightSendStmm" :nightSendEdhh="this.nightSendEdhh" :nightSendEdmm="this.nightSendEdmm"/> -->
  </div>
</template>

<script>
import DirectInputPopup from "@/modules/message/components/bp-directInput.vue";
import AddressInputPopup from "@/modules/message/components/bp-addressInput.vue";
import Calendar from "@/components/Calendar.vue";
import TestSendInputPopup from "@/modules/message/components/bc-testSendInput.vue";
import XLSX from 'xlsx';
//import nightSendLimitPopup from "@/modules/message/components/bp-nightSendLimit.vue";

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
    TestSendInputPopup,
//    nightSendLimitPopup
  },
  props: {
    tmpltCodeP: {
      type: String,
      require: true,
    },
    tmpltType: {
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
  filters: {
    comma (val) {
      return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    }
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
      tempFile: [],
      beforeCuInputType: 'DICT',
      monthAmount : 0,
      monSenderLimitAmout : '없음',
      feeType : false,
      sendData: {
        requiredCuid: false,  //app 로그인 ID 필수여부
        requiredCuPhone: false,  //수신자 폰번호 필수여부
        cuInputType:'',  //DICT, ADDR, EXCEL
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
        excelLimitRow: 0
      },
      // nightSendSthh: '',
			// nightSendStmm: '',
			// nightSendEdhh: '',
			// nightSendEdmm: '',
      // nightSendYn : 'N',
      // nightSendLimitYn : false
    }
  },
  watch : {
	  recvCnt (newval, oldval) {
		  if(newval>30000) {
			  confirm.fnAlert(this.componentsTitle, "발송 최대 수신자 수는 30000명을 넘길 수 없습니다.");
			  this.fnRemoveRecvInfo();
		  }
	  }
  },
  updated() {
    if(this.previewMessageType == 'RCS' && this.tmpltData.RCS
      && (this.tmpltData.RCS.rcsPrdType == 'CSHORT' || this.tmpltData.RCS.rcsPrdType == 'CTALL')) {
      this.fnSetBxslider();
      this.showBxPage = true;
    } else {
      jQuery('.bx-wrapper').remove();
      this.showBxPage = false;
    }
  },
  mounted() {
    this.fnExistApiKey();
    this.fnGetTmpltInfo();
    //this.fnNightSendTime();
  },
  methods: {
    fnRemoveRecvInfo(){
      this.fnCallbackRecvInfoLst(null);
      this.$refs.excelFile.value = '';
      this.tempFile = [];
    },
    fnReadFile(){
      if(this.$refs.excelFile.files && this.$refs.excelFile.files.length > 0){
        const file = this.$refs.excelFile.files[0];
        let reader = new FileReader();

        reader.onload = (e) => {
          let data = reader.result;
          let workbook = XLSX.read(data, {type: 'binary'});
          let sheetName = '';
          let excelArray = [];

          if(workbook.SheetNames && workbook.SheetNames.length > 0){
            sheetName = workbook.SheetNames[0];
          }
          if(this.$gfnCommonUtils.isEmpty(sheetName) == false){
            excelArray = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName], {header : 1});
          }

          let requiredCuid = this.sendData.requiredCuid;
          let requiredCuPhone = this.sendData.requiredCuPhone;
          let recvArr = [];

          // 엑셀 구조에 따라 배열의 2번째 값부터 실 data이므로 해당 데이터부터 for문 처리
          // PUSH && etc 인 경우 Cuid, Phone 을 합쳐서 하나의 배열로 처리
          for(var i=2; i<excelArray.length; i++) {
            if(requiredCuid && requiredCuPhone) {
              recvArr.push(excelArray[i][0] + excelArray[i][1]);
            } else {
              recvArr.push(excelArray[i][0]);
            }
          }

			    recvArr = new Set(recvArr);

          this.recvCnt = recvArr.size;
          this.sendData.excelLimitRow = (excelArray.length-2);
        };
        reader.readAsBinaryString(file);
      } else {
        this.recvCnt = 0;
      }
    },
    fnReset(){
      Object.assign(this.$data, this.$options.data.apply(this));
      this.fnGetTmpltInfo();
    },
    async fnExistApiKey(){
      let params = {};
      await messageApi.selectApiKey(params).then(response =>{
        const result = response.data;
        if(result.success) {
          if(this.$gfnCommonUtils.isEmpty(result.data)){
            confirm.fnAlert(this.componentsTitle, '해당 프로젝트의 사용가능한 API 키가 존재하지 않습니다.\n메시지 발송하실 수 없습니다.');
          } else {
            // 사용가능한 api 키가 존재하면 발송제한 금액 세팅
            this.fnSetSentAmount();
          }
        }
      });
    },
    fnSetSentAmount() {
      let params = {};
      var vm = this;
      messageApi.setSentAmout(params).then(response =>{
        const result = response.data;
        if(result.success) {
          let resultData = result.data;
          vm.monthAmount = resultData.amountMap.month + "원";
          vm.monSenderLimitAmout = resultData.returnApiKeyMap.monSenderLimitAmount+"원";
          
          if(resultData.returnApiKeyMap.feeType == "PRE") {
            vm.feeType = false;
          } else {
            vm.feeType = true;
          }
        }
      });
    },
    fnSetBxslider(){
      jQuery('.cardBxslider').show().bxSlider({
        auto: false,
        autoControls: false,
        slideWidth: 204,
        minSlides: 1,
        maxSlides: 2,
        slideMargin: 10,
        controls: true,
        pager: true,
        pagerType: 'customPage',
        touchEnabled : (navigator.maxTouchPoints > 0),
        autoHover: false
      });
    },
    fnGetImageUrl(ch, fileId){
      let params = {ch: ch, fileId: fileId};
      return new Promise((resolve, reject) => {
        commonUtilApi.selectImageUrlInfo(params).then(response =>{
          var result = response.data;
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
      this.$refs.testSendInputPopup.fnSetTestRecvInfoLst(this.sendData.testRecvInfoLst);
      this.testSendInputOpen = true;
    },
    //테스트 발송 callback
    fnCallbackTestRecvInfoLst(testRecvInfoLst){
      if(testRecvInfoLst != null){
        this.sendData.testRecvInfoLst = Object.assign([], testRecvInfoLst);
        this.fnSendSmartMessage('Y');
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
          if(this.$refs.excelFile.value != 0){
            this.tempFile = [];
            this.tempFile.push.apply(this.tempFile, this.$refs.excelFile.files);
          }
          const uploadFile = this.tempFile[0];
          if(!uploadFile){
            confirm.fnAlert(this.componentsTitle, '엑셀파일을 등록해주세요.');
            return false;
          }
          const permitExten = 'xls,xlsx'.split(',');
          const extnIdx = uploadFile.name.lastIndexOf('.');
          const extn = uploadFile.name.substring(extnIdx+1);
          if((permitExten.indexOf(extn) < 0)){
            confirm.fnAlert(this.componentsTitle, '허용되지 않는 확장자입니다.');
            return false;
          }
        }
      }
      return true;
    },
    //통합 메시지 발송 처리
    fnSendSmartMessage(testSendYn){
      if(this.inProgress){
        confirm.fnAlert(this.componentsTitle, '메시지 발송 처리중입니다.');
        return;
      }

      //유효성 체크
      if(this.fnValidSendMsgData(testSendYn) == false) return;

      //if(this.fnNightSendCheck() == false) return;

      //친구톡 광고성 야간발송 확인
      if(this.tmpltData.msgKind == 'A' && this.fnContainsChannel('FRIENDTALK')){
        const msg = '친구톡 광고성 메시지는 20~8시 발송시 실패 처리 됩니다.\n메시지를 발송하시겠습니까?';
        if(
          (this.sendData.rsrvSendYn == 'Y' && this.$gfnCommonUtils.islimitAdMsgSendTime(this.sendData.rsrvHH))
          || (this.sendData.rsrvSendYn == 'N' && this.$gfnCommonUtils.islimitAdMsgSendTime())
        ){
          eventBus.$on('callbackEventBus', this.fnProcSendIntegratedMessage);
          confirm.fnConfirm(this.componentsTitle, msg, "확인", testSendYn);
          return;
        }
      }
      this.fnProcSendIntegratedMessage(testSendYn);

      // 발송제한 금액 세팅
      this.fnSetSentAmount();
    },
    async fnProcSendIntegratedMessage(testSendYn){
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
      if(this.sendData.cuInputType == 'EXCEL'){
        fd.append('file', this.tempFile[0]);
        this.$refs.excelFile.value = '';
        this.tempFile = [];
      }
      fd.append('paramString', JSON.stringify(params));

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
      this.fnReset();
    },
    fnUpdateRsrvDate(sltDate){
      this.sendData.rsrvDate = sltDate;
    },
    fnClickCuInputType(e){
      this.tempFile = [];
      this.tempFile.push.apply(this.tempFile, this.$refs.excelFile.files);
      this.$refs.excelFile.value = '';

      if(this.sendData.cuInputType == e.target.value){
        this.fnChgCuInputType('N');
      }
      this.beforeCuInputType = this.sendData.cuInputType;
    },
    //수신자 입력 타입 변경시
    fnChgCuInputType(chgYn){
      if(this.$gfnCommonUtils.defaultIfEmpty(chgYn, 'Y') == 'Y'){
        if((this.beforeCuInputType != 'DICT' && this.beforeCuInputType != 'ADDR')
          || (this.sendData.cuInputType != 'DICT' && this.sendData.cuInputType != 'ADDR')){
          this.fnCallbackRecvInfoLst(null);  //수신자 입력 타입 변경시 수신자 정보 초기화
        }
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
            conts = '';
            if(chTmpltInfo.rcsPrdType == 'CELL'){
              let styleContent = chTmpltInfo.mergeData[0];
              if(styleContent.content){
                styleContent.content.forEach(element => {
                  conts += this.$gfnCommonUtils.defaultIfEmpty(element.description0, '');
                  conts += this.$gfnCommonUtils.defaultIfEmpty(element.description1, '');
                });
              }
            } else {
              chTmpltInfo.mergeData.forEach(element => {
                conts += this.$gfnCommonUtils.defaultIfEmpty(element.title, '');
                conts += this.$gfnCommonUtils.defaultIfEmpty(element.description, '');
              });
            }
          }
        }

        if(ch == 'RCS'){
          conts.replace(/\{\{(([a-z|A-Z|0-9|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|_])+)\}\}/g, function($0, $1) {
            tempVarNms.push($1);
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
        this.sendData.contsVarNms = this.fnSetArrayRemoveDupliVal(varNms);
        this.sendData.chMappingVarList = Object.assign([], chMappingVarList);
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
              this.fnGetImageUrl('MMS', fileId).then(url => {tempData.MMS.fileUrlLst.push(url)});
            });
          }

          //RCS 스타일형 스타일 정보 Set
          if(tempData.rcsTmpltInfo
            && tempData.RCS
            && tempData.RCS.rcsPrdType == 'CELL'){
            this.fnSetRcsStyle(tempData);
          }

          this.tmpltData = Object.assign({}, tempData);
          this.previewMessageType = this.tmpltData.chTypeList[0];

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

        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnSetRcsStyle(tempData){
      const rcsTmpltInfo = JSON.parse(tempData.rcsTmpltInfo);

      let rcsLayout;
      try {
        rcsLayout = rcsTmpltInfo.formattedString.RCSMessage.openrichcardMessage.layout;
      } catch (error) {}

      //수평선
      if(rcsLayout && rcsLayout.children && rcsLayout.children.length > 1 && rcsLayout.children[1].children){
        const layoutInfo = rcsLayout.children[1].children;
        let hrStyleArr = [];
        let ne;

        layoutInfo.forEach((e, idx) => {
          if(e.widget && e.widget != 'LinearLayout'){
            if(e.visibility && e.visibility == 'visible'){
              hrStyleArr.push(true);
            } else {
              hrStyleArr.push(false);
            }
          } else {
            if(idx === layoutInfo.length - 1){
              ne = layoutInfo[idx+1];
              if(ne && ne.widget && ne.widget === 'LinearLayout'){
                hrStyleArr.push(false);
              }
            }
          }
        });
        tempData.RCS.hrStyleArr = hrStyleArr;
      }
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
    //야간 메시지 전송 체크
    fnNightSendCheck(){
      let params = {
        nightSendYn : this.nightSendYn,
        rsrvSendYn : this.sendData.rsrvSendYn,
        rsrvHH : this.sendData.rsrvHH,
        rsrvMM : this.sendData.rsrvMM,
        nightSendSthh : this.nightSendSthh,
        nightSendStmm : this.nightSendStmm,
        nightSendEdhh : this.nightSendEdhh,
        nightSendEdmm : this.nightSendEdmm
      }
      var nightSendLimitYn = messageApi.checkNightSendTime(params);

      if(nightSendLimitYn){
        this.nightSendLimitYn = nightSendLimitYn;
      }
      
      return !nightSendLimitYn;
    },
    // 야간 메시지 전송 시간 확인
		async fnNightSendTime() {
			let params = {
        isChk : "Y"
      };
			await messageApi.selectNightSendTime(params).then(response =>{
				var result = response.data;
				if(result.success) {
					this.nightSendSthh = result.data.nightSendSthh;
					this.nightSendStmm = result.data.nightSendStmm;
					this.nightSendEdhh = result.data.nightSendEdhh;
					this.nightSendEdmm = result.data.nightSendEdmm;
          this.nightSendYn = result.data.nightSendYn;
				} else {
					confirm.fnAlert(this.title, result.message);
				}
			});
		},
  }
}
</script>