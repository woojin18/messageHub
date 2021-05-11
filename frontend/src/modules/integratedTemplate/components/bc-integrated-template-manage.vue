<template>
  <div class="row row-no-margin">
    <div class="contentHeader">
      <h2>통합발송 템플릿</h2>
      <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="이용안내">이용안내 <i class="fal fa-book-open"></i></a>
    </div>

    <ImageManagePopUp :imgMngOpen.sync="pushImgMngOpen" ref="pushImgMng"></ImageManagePopUp>
    <ImageUploadPopUp :imgUploadOpen.sync="pushImgUploadOpen"></ImageUploadPopUp>

    <!-- 본문 -->
    <div class="row">
      <div class="col-xs-6" style="padding-right:100px">
        <h4 class="topH4">01 발송정보</h4>
        <div class="of_h">
          <div class="float-left" style="width:34%"><h5>메시지 구분*</h5></div>
          <div class="float-left" style="width:66%">
            <input type="radio" name="mSort" value="Y" id="mSort1" checked="" v-model="rowData.msgKind"> <label for="mSort1" class="mr20">정보성</label>
            <input type="radio" name="mSort" value="N" id="mSort2" v-model="rowData.msgKind"> <label for="mSort2">광고용</label>
            <span class="txtCaption color4">광고 메시지는 20시~8시 발송이 제한됩니다.</span>
          </div>
        </div>
        <div class="of_h">
          <div class="float-left" style="width:34%"><h5>메시지 타입*</h5></div>
          <div class="float-left" style="width:66%">
            <input type="radio" name="mType" value="Y" id="mType_text" checked="" v-model="rowData.msgType"> <label for="mType_text" class="mr20">텍스트</label>
            <input type="radio" name="mType" value="N" id="mType_image" v-model="rowData.msgType"> <label for="mType_image">이미지</label>
          </div>
        </div>
        <div class="of_h">
          <div class="float-left" style="width:34%"><h5>타 프로젝트 사용여부*</h5></div>
          <div class="float-left" style="width:66%">
            <input type="radio" name="otherUse" value="Y" id="otherUse1" checked="" v-model="rowData.otherProjectUseYn"> <label for="otherUse1" class="mr20">공용</label>
            <input type="radio" name="otherUse" value="N" id="otherUse2" v-model="rowData.otherProjectUseYn"> <label for="otherUse2">전용</label>
          </div>
        </div>
        <div class="of_h">
          <div class="float-left" style="width:34%"><h5>템플릿 명</h5></div>
          <div class="float-left" style="width:66%">
            <div class="float-left" style="width:68%"><input type="text" class="inputStyle" v-model="rowData.tmpltTitle"></div>
            <div class="float-right" style="width:30%"><a href="#self" class="btnStyle1 backLightGray" title="중복체크">중복체크</a></div>		
          </div>
        </div>
      </div>
      
      <div class="col-xs-5" style="border-left:1px solid #D5D5D5; padding-left:100px">
        <h4 class="topH4">02 채널 선택</h4>
        <div class="of_h">
          <div class="float-left" style="width:50%">
            <div class="of_h">
              <div class="float-right text-center" style="width:100%">&nbsp;</div>
            </div>
            <div class="of_h consolMarginTop">
              <div class="float-left" style="width:60%"><h5>Push</h5></div>
              <div class="float-left consolMarginTop" style="width:40%"><input type="checkbox" id="Push" class="checkStyle2" value="Push" v-model="rowData.checkedChannel" @click="toggleOnOffPush"><label for="Push"></label></div>
            </div>
            <div class="of_h consolMarginTop">
              <div class="float-left" style="width:60%"><h5>RCS</h5></div>
              <div class="float-left consolMarginTop" style="width:40%"><input type="checkbox" id="RCS" class="checkStyle2" value="RCS" v-model="rowData.checkedChannel" @click="toggleOnOffRCS"><label for="RCS"></label></div>
            </div>
            <div class="of_h consolMarginTop">
              <div class="float-left" style="width:60%"><h5>카카오톡</h5></div>
              <div class="float-left consolMarginTop" style="width:40%"><input type="checkbox" id="kakao" class="checkStyle2" value="kakao" v-model="rowData.checkedChannel" @click="toggleOnOffKakao"><label for="kakao"></label></div>
            </div>
                                    <div class="of_h consolMarginTop">
              <div class="float-left" style="width:60%"><h5>SMS/MMS</h5></div>
              <div class="float-left consolMarginTop" style="width:40%"><input type="checkbox" id="smsMms" class="checkStyle2" value="smsMms" v-model="rowData.checkedChannel" @click="toggleOnOffSmsMms"><label for="smsMms"></label></div>
            </div>
          </div>
          <div class="float-right text-center" style="width:50%">
            <div class="of_h">
              <div class="float-right text-center" style="width:100%">발송순서</div>
            </div>

            <div class="of_h consolMarginTop" v-for="(channel, index) in rowData.checkedChannel" :key="channel">
              <div class="float-left" style="width:100%"><a class="btnStyle1 borderLightGray " style="min-width:auto; width:100%"  @click="putData(index)">{{channel}}</a></div>
            </div>


<!--             
            <div class="of_h consolMarginTop" v-for="(element, index) in items" :key="element.name">
              <div v-if="element.checkedVal == 'checkedPush'">
                <div class="float-left" style="width:100%"><a class="btnStyle1 borderLightGray " style="min-width:auto; width:100%" v-if="checkedPush" @click="putData(index)">{{element.name}}</a></div>
              </div>
              <div v-if="element.checkedVal == 'checkedRCS'">
                <div class="float-left" style="width:100%"><a class="btnStyle1 borderLightGray " style="min-width:auto; width:100%" v-if="checkedRCS" @click="putData(index)">{{element.name}}</a></div>
              </div>
              <div v-if="element.checkedVal == 'checkedKakao'">
                <div class="float-left" style="width:100%"><a class="btnStyle1 borderLightGray " style="min-width:auto; width:100%" v-if="checkedKakao" @click="putData(index)">{{element.name}}</a></div>
              </div>
              <div v-if="element.checkedVal == 'checkedSmsMms'">
                <div class="float-left" style="width:100%"><a class="btnStyle1 borderLightGray " style="min-width:auto; width:100%" v-if="checkedSmsMms" @click="putData(index)">{{element.name}}</a></div>
              </div>
            </div>
 -->
          </div>
        </div>
        <div class="of_h consolMarginTop">
          <div class="float-right text-center" style="width:50%"><a @click="moveup()" title="위버튼"><i class="far fa-chevron-up channelBtn"></i></a> <a @click="movedown()" title="아래버튼"><i class="far fa-chevron-down channelBtn"></i></a></div>
        </div>
        <div class="of_h consolMarginTop">
          <div class="float-left" style="width:33%; padding:0 10px"><a @click="save()" class="btnStyle1 borderGray" style="min-width:auto; width:100%" title="저장">저장</a></div>
          <div class="float-left" style="width:33%; padding:0 10px"><a @click="complete()" class="btnStyle1 backBlack" style="min-width:auto; width:100%" title="등록">등록</a></div>
          <div class="float-left" style="width:33%; padding:0 10px"><a href="#self" class="btnStyle1 backWhite" style="min-width:auto; width:100%" title="취소">취소</a></div>
        </div>
      </div>


    </div>

    <hr>

    <h4>03 채널 설정</h4>
<!--     <div class="contentBody">
      <ul class="tab_s3">
        <li v-bind:class="[ channelTab === 0 ? 'active' : '']"><a title="Push 페이지로 이동"           v-on:click="channelTab=0" v-bind:class="[ channelTab === 0 ? 'active' : '']">Push</a></li>
        <li v-bind:class="[ channelTab === 1 ? 'active' : '']"><a title="RCS 페이지로 이동"            v-on:click="channelTab=1" v-bind:class="[ channelTab === 1 ? 'active' : '']">RCS</a></li>
        <li v-bind:class="[ channelTab === 2 ? 'active' : '']"><a title="카카오톡(알림톡) 페이지로 이동"   v-on:click="channelTab=2" v-bind:class="[ channelTab === 2 ? 'active' : '']">카카오톡(알림톡)</a></li>
        <li v-bind:class="[ channelTab === 3 ? 'active' : '']"><a title="카카오톡(친구톡) 페이지로 이동"   v-on:click="channelTab=3" v-bind:class="[ channelTab === 3 ? 'active' : '']">카카오톡(친구톡)</a></li>
        <li v-bind:class="[ channelTab === 4 ? 'active' : '']"><a title="SMS/MMS 페이지로 이동"        v-on:click="channelTab=4" v-bind:class="[ channelTab === 4 ? 'active' : '']">SMS/MMS</a></li>
      </ul>
    </div> -->

    <div class="contentBody">
      <ul class="tab_s3">
        <li v-bind:class="[ channelTab === 0 ? 'active' : '']"><a title="Push 페이지로 이동"           v-on:click="channelTab=0" v-bind:class="[ channelTab === 0 ? 'active' : '']" v-if="checkedPush">Push</a></li>
        <li v-bind:class="[ channelTab === 1 ? 'active' : '']"><a title="RCS 페이지로 이동"            v-on:click="channelTab=1" v-bind:class="[ channelTab === 1 ? 'active' : '']" v-if="checkedRCS">RCS</a></li>
        <li v-bind:class="[ channelTab === 2 ? 'active' : '']"><a title="카카오톡 페이지로 이동"         v-on:click="channelTab=2" v-bind:class="[ channelTab === 2 ? 'active' : '']" v-if="checkedKakao">카카오톡</a></li>
        <li v-bind:class="[ channelTab === 3 ? 'active' : '']"><a title="SMS/MMS 페이지로 이동"        v-on:click="channelTab=3" v-bind:class="[ channelTab === 3 ? 'active' : '']" v-if="checkedSmsMms">SMS/MMS</a></li>
      </ul>
    </div>



<!-- PUSH -->
    <div class="of_h mt20" v-if="channelTab === 0" v-show="checkedPush">
      <div class="float-left" style="width:28%">
        <!-- phoneWrap -->
        <div class="phoneWrap">
          <img src="../../../common/images/phoneMockup1.svg" alt="프리 템플릿">
          <div class="phoneTextWrap">
            <div class="phoneText1">
              <p>내용이 들어갑니다.</p>
            </div>
          </div>
        </div>
        <!-- //phoneWrap -->
      </div>
      <div class="float-left consoleCon" style="width:72%">
        <div class="of_h">
          <div class="float-left" style="width:13%"><h4>발송 정책 *</h4></div>
          <div class="float-left" style="width:57%">
            <input type="radio" name="sendP" value="ALL" id="sendP1" checked="" v-model="rowData.pushSend"> <label for="sendP1" class="mr30">ALL</label>
            <input type="radio" name="sendP" value="FCM" id="sendP2" v-model="rowData.pushSend"> <label for="sendP2" class="mr30">FCM</label>
            <input type="radio" name="sendP" value="APNS" id="sendP3" v-model="rowData.pushSend"> <label for="sendP3">APNS</label>
          </div>
        </div>
        <div class="of_h">
          <div class="float-left" style="width:13%"><h4>제목</h4></div>
          <div class="float-left" style="width:57%"><input type="text" class="inputStyle" name="titleP" v-model="rowData.pushTitle" placeholder="최대 50자 입력 가능합니다."></div>
        </div>
        <div class="of_h">
          <div class="float-left" style="width:13%"><h4>내용*</h4></div>
          <div class="float-left" style="width:57%">
            <textarea class="textareaStyle height190" name="contentP" v-model="rowData.pushContent" ></textarea>
            <p class="color5 txtCaption">광고성 메시지 발송시, 자동으로 (광고)가 표시되오니, 내용에 (광고)문구는 입력하지 않아도 됩니다.</p>
          </div>
        </div>
        <div class="of_h consolMarginTop">
          <div class="float-left" style="width:13%"><h4>수신거부방법 *</h4></div>
          <div class="float-left" style="width:57%">
            <input type="text" class="inputStyle" name="contentP" v-model="rowData.pushContent" placeholder="설정 > 푸시 알림 설정 변경">
            <p class="color5 txtCaption">광고성 메시지 발송시, 자동으로 (광고)가 표시되오니, 내용에 (광고)문구는 입력하지 않아도 됩니다.</p>
          </div>
        </div>
        <div class="of_h consolMarginTop">
          <div class="float-left" style="width:13%"><h4>이미지</h4></div>
          <div class="float-left" style="width:57%">
            <div class="float-left" style="width:25%">
              <a @click="fnPushOpenImageManagePopUp" class="btnStyle1 backLightGray width100_" title="이미지선택">이미지선택</a>
            </div>
            <ul class="float-right attachList" style="width:75%; padding:5px 15px height:30px;">
              <li><a @click="fnPushDelImg">{{fnSubString(rowData.pushImgUrl, 0, 35)}}  <i v-if="!fnIsEmpty(rowData.pushImgUrl)" class="fal fa-times"></i></a></li>
            </ul>	
          </div>
        </div>


        <div class="of_h consolMarginTop">
          <div class="float-left" style="width:13%"><h4>APP_ID</h4></div>
          <div class="float-left" style="width:57%">
            <div class="float-left" style="width:49%">
              <select name="userConsole020702_1" class="selectStyle2 width100_">
                <option value="">선택하세요</option>
              </select>
            </div>
            <div class="float-right" style="width:49%">
              <input type="text" class="inputStyle" placeholder="APP ID를 입력하세요">
            </div>
          </div>
        </div>
      </div>
    </div>	





<!-- RCS -->
    <div class="of_h mt20" v-if="channelTab === 1" v-show="checkedRCS">
      <div class="templateBox">
        <!-- templateList -->
        <ul class="templateList">
          <li>
            <img src="../../../common/images/pushTemplate1.svg" alt="프리 템플릿"><h6>프리 템플릿</h6>
            <div class="consolMarginTop">
              <input type="radio" name="pushTemplate1" value="0" id="pushTemplate1-1" class="radioStyle" checked="" v-on:click="rcsTemplateTable=0" ><label for="pushTemplate1-1"></label>
              <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
            </div>
          </li>
          <li>
            <img src="../../../common/images/pushTemplate2.svg" alt="템플릿 승인(서술)"><h6>템플릿 승인<br>(서술)</h6>
            <div class="consolMarginTop">
              <input type="radio" name="pushTemplate1" value="1" id="pushTemplate1-2" class="radioStyle" v-on:click="rcsTemplateTable=1" ><label for="pushTemplate1-2"></label>
              <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
            </div>
          </li>
          <li>
            <img src="../../../common/images/pushTemplate3.svg" alt="템플릿 승인(스타일)"><h6>템플릿 승인<br>(스타일)</h6>
            <div class="consolMarginTop">
              <input type="radio" name="pushTemplate1" value="2" id="pushTemplate1-3" class="radioStyle" v-on:click="rcsTemplateTable=2" ><label for="pushTemplate1-3"></label>
              <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
            </div>
          </li>
          <li>
            <img src="../../../common/images/pushTemplate4.svg" alt="SMS"><h6>SMS</h6>
            <div class="consolMarginTop">
              <input type="radio" name="pushTemplate1" value="3" id="pushTemplate1-4" class="radioStyle" ><label for="pushTemplate1-4"></label>
              <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
            </div>
          </li>
          <li>
            <img src="../../../common/images/pushTemplate5.svg" alt="LMS"><h6>LMS</h6>
            <div class="consolMarginTop">
              <input type="radio" name="pushTemplate1" value="4" id="pushTemplate1-5" class="radioStyle" ><label for="pushTemplate1-5"></label>
              <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
            </div>
          </li>
          <li>
            <img src="../../../common/images/pushTemplate6.svg" alt="세로형(short)"><h6>세로형<br>(short)</h6>
            <div class="consolMarginTop">
              <input type="radio" name="pushTemplate1" value="5" id="pushTemplate1-6" class="radioStyle" ><label for="pushTemplate1-6"></label>
              <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
            </div>
          </li>
          <li>
            <img src="../../../common/images/pushTemplate7.svg" alt="세로형(Tall)"><h6>세로형<br>(Tall)</h6>
            <div class="consolMarginTop">
              <input type="radio" name="pushTemplate1" value="6" id="pushTemplate1-7" class="radioStyle" ><label for="pushTemplate1-7"></label>
              <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
            </div>
          </li>
          <li>
            <img src="../../../common/images/pushTemplate8.svg" alt="가로형(왼쪽)"><h6>가로형<br>(왼쪽)</h6>
            <div class="consolMarginTop">
              <input type="radio" name="pushTemplate1" value="7" id="pushTemplate1-8" class="radioStyle" ><label for="pushTemplate1-8"></label>
              <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
            </div>
          </li>
          <li>
            <img src="../../../common/images/pushTemplate9.svg" alt="가로형(오른쪽)"><h6>가로형<br>(오른쪽)</h6>
            <div class="consolMarginTop">
              <input type="radio" name="pushTemplate1" value="7" id="pushTemplate1-9" class="radioStyle" ><label for="pushTemplate1-9"></label>
              <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
            </div>
          </li>
          <li>
            <img src="../../../common/images/pushTemplate10.svg" alt="캐러셀(short)"><h6>캐러셀<br>(short)</h6>
            <div class="consolMarginTop">
              <input type="radio" name="pushTemplate1" value="8" id="pushTemplate1-10" class="radioStyle" ><label for="pushTemplate1-10"></label>
              <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
            </div>
          </li>
          <li>
            <img src="../../../common/images/pushTemplate11.svg" alt="캐러셀(Tall)"><h6>캐러셀<br>(Tall)</h6>
            <div class="consolMarginTop">
              <input type="radio" name="pushTemplate1" value="9" id="pushTemplate1-11" class="radioStyle" v-on:click="rcsTemplateTable=10" v-bind:class="[ rcsTemplateTable === 10 ? 'active' : '']"><label for="pushTemplate1-11"></label>
              <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i>
            </div>
          </li>
        </ul>
        <!-- //templateList -->
      </div>


      <div v-if="rcsTemplateTable === 0">
        <h4>내용작성</h4>
        <div class="of_h mt20">
          <div class="float-left" style="width:28%">
            <!-- phoneWrap -->
            <div class="phoneWrap">
              <img src="../../../common/images/phoneMockup1.svg" alt="프리 템플릿">
              <div class="phoneTextWrap">
                <div class="phoneText1">
                  <p>내용이 들어갑니다.</p>
                </div>
              </div>
            </div>
            <!-- //phoneWrap -->
          </div>
          <div class="float-left consoleCon" style="width:72%">
            <div class="float-left" style="width:13%"><h4>내용*</h4></div>
            <div class="float-left" style="width:57%">
              <textarea class="textareaStyle height190" ></textarea>
            </div>




            <div class="float-right consolMarginTop" style="width:87%">
              <!-- templateList -->
              <ul class="templateList">
                <li>
                  <img src="../../../common/images/templateApp1.svg" alt="템플릿 승인(서술)"><h6>템플릿 승인(서술) <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i></h6>
                  <div class="consolMarginTop">
                    <input type="radio" name="templateApp1" value="Y" id="templateApp1-1" class="radioStyle" checked=""><label for="templateApp1-1"></label>
                  </div>
                </li>
                <li>
                  <img src="../../../common/images/templateApp2.svg" alt="템플릿 승인(스타일)"><h6>템플릿 승인(스타일) <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i></h6>
                  <div class="consolMarginTop">
                    <input type="radio" name="templateApp1" value="Y" id="templateApp1-2" class="radioStyle"><label for="templateApp1-2"></label>
                  </div>
                </li>
                <li>
                  <img src="../../../common/images/templateApp3.svg" alt="텍스트 미승인형"><h6>텍스트 미승인형 <i class="fas fa-question-circle toolTip"><span class="toolTipText" style="width:250px">메시지를 발송할 수 있습니다.</span></i></h6>
                  <div class="consolMarginTop">
                    <input type="radio" name="templateApp1" value="Y" id="templateApp1-3" class="radioStyle"><label for="templateApp1-3"></label>
                  </div>
                </li>
              </ul>
              <!-- //templateList -->
            </div>


            
          </div>
        </div>		
      </div>

      <div v-if="rcsTemplateTable === 1">11
      </div>

      <div v-if="rcsTemplateTable === 2">22
      </div>

      <div v-if="rcsTemplateTable === 3">33
      </div>

      <div v-if="rcsTemplateTable === 4">44
      </div>

      <div v-if="rcsTemplateTable === 5">55
      </div>

    </div>


<!-- KAKAO -->
    <div class="of_h mt20" v-if="channelTab === 2" v-show="checkedKakao">

    </div>


<!-- SMS/MMS -->
    <div class="of_h mt20" v-if="channelTab === 3" v-show="checkedSmsMms">

    </div>






  </div>
</template>

<script>
import integratedTemplateApi from "@/modules/integratedTemplate/service/integratedTemplateApi.js";
import ImageManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";
import ImageUploadPopUp from "@/modules/commonUtil/components/bp-imageUpload.vue";
import TokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

//  Array.prototype.move = function(from, to) {
//    this.splice(to, 0, this.splice(from, 1)[0]);
//    return this;
//  };

export default {
  name: 'integratedTemplateManage',

  components : {
    ImageManagePopUp,
    ImageUploadPopUp
  },
  props: {
    rowData : {
      type: Object,
      require: false,
      default: function() {
        return {'checkedChannel':[], 'msgType':'Y', 'msgKind':'Y', 'otherProjectUseYn':'Y','tmpltType':'M'} //tmpltType:통합발송 M, 스마트발송:S
      }
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '푸시 템플릿 관리';
      }
    },
  },
  data() {
    return {
      channelTab: 0,
      rcsTemplateTable: 0,
      checkedPush: false,
      checkedRCS: false,
      checkedKakao: false,
      checkedSmsMms: false,      
      items : [{'id':'1','name':'Push', 'checkedVal':'checkedPush'},{'id':'2','name':'RCS', 'checkedVal':'checkedRCS'},{'id':'3','name':'Kakao', 'checkedVal':'checkedKakao'},{'id':'4','name':'SmsMms', 'checkedVal':'checkedSmsMms'}],
      indexData:0,
      itemDatas:[],
      //checkedChannel:[],
      detailTitle:'통합발송 템플릿',
      //registYn : false,
      testProjectId: '313431323336706A74', //test용 projectId 관리방법이 정해지면 변경필요 (세션이 좋을듯)
      rcsRadioIndex: '', //rcs탭에서 선택한 템플릿을 기억해서 해당 내용만을 DB저장 대상으로 한다.
      kakaoRadioIndex: '', //kakao탭에서 선택한 템플릿을 기억해서 해당 내용만을 DB저장 대상으로 한다.
      smsMmsRadioIndex: '', //SMS/MMS탭에서 선택한 템플릿을 기억해서 해당 내용만을 DB저장 대상으로 한다.

      pushImgMngOpen : false,
      pushImgUploadOpen : false

    }
  },

  watch:{

  },

  mounted() {
    this.fnSetIntegratedTemplateInfo();
  },

  methods: {

    //채널선택에서 체크박스를 클릭하면 채널설정탭을 설정한다.
    toggleOnOffPush:function(){
      if(this.checkedPush == true){
        this.checkedPush = false;
      }else{
        this.checkedPush = true;
      }
      console.log("this.checkedPush : ",this.checkedPush);
    },
    toggleOnOffRCS:function(){
      if(this.checkedRCS == true){
        this.checkedRCS = false;
      }else{
        this.checkedRCS = true;
      }
    },
    toggleOnOffKakao:function(){
      if(this.checkedKakao == true){
        this.checkedKakao = false;
      }else{
        this.checkedKakao = true;
      }
    },
    toggleOnOffSmsMms:function(){
      if(this.checkedSmsMms == true){
        this.checkedSmsMms = false;
      }else{
        this.checkedSmsMms = true;
      }
    },

    showRcsTemplateTable(num){
      if(num == 0){

      }else if(num == 1){

      }else if(num == 2){

      }else if(num == 3){

      }else if(num == 4){

      }else if(num == 5){

      }else if(num == 6){

      }else if(num == 7){

      }else if(num == 8){

      }else if(num == 9){

      }else if(num == 10){

      }
    },
    putData:function(idx){//채널 발송순서 변경시 클릭한 채널 인덱스를 가져온다
      console.log("idxData : ", idx);
      this.idxData = idx;
    },
    
    moveup:function () {//발송순서를 위로
      console.log("start index : ",this.idxData);
      //Insert the item in the previous item
      if (this.idxData > 0) {
        this.rowData.checkedChannel.splice (this.idxData-1,0, (this.rowData.checkedChannel [this.idxData]));
        
        //Delete the next item
        this.rowData.checkedChannel.splice (this.idxData + 1,1);
      
        this.idxData = this.idxData -1;
      }
      console.log(this.rowData.checkedChannel);
    },  
    
    movedown:function () { //발송순서를 아래로
      console.log("start index : ",this.idxData);
      if (this.idxData < this.rowData.checkedChannel.length-1) {
        //Insert the item in the next item
        this.rowData.checkedChannel.splice (this.idxData + 2,0, (this.rowData.checkedChannel [this.idxData]));
        
        this.rowData.checkedChannel.splice (this.idxData, 1);
        //item.isshow=false;
     
        this.idxData = this.idxData +1;
      }
    },

    async fnSaveIntegratedTemplate(){
      var params = this.rowData;
      console.log("fnSaveIntegratedTemplate params : ",params);

      params.tmpltStatus = 'SAVE';//템플릿 상태값 저장:SAVE, 완료:COMPLETE
      params.projectId = this.testProjectId;

      await integratedTemplateApi.insertIntegratedTemplate(params).then(response =>{
        var result = response.data;
        if(result.success) {
          confirm.fnAlert(this.detailTitle, '저장 되었습니다.');
        } else {
          confirm.fnAlert(this.detailTitle, result.message);
        }
      });
    },

    async fnCompleteIntegratedTemplate(){
      var params = this.rowData;
      console.log("fnCompleteIntegratedTemplate params : ",params);

      //유효성 검사
      if(this.fnIsValid() == false) return;

      params.tmpltStatus = 'COMPLETE';//템플릿 상태값 저장:SAVE, 완료:COMPLETE
      params.projectId = this.testProjectId;

      await integratedTemplateApi.insertIntegratedTemplate(params).then(response =>{
        var result = response.data;
        if(result.success) {
          confirm.fnAlert(this.detailTitle, '등록 되었습니다.');
        } else {
          confirm.fnAlert(this.detailTitle, result.message);
        }
      });
    },

        //유효성 체크
    fnIsValid(){
      if(!this.rowData.tmpltName){
        confirm.fnAlert(this.componentsTitle, '템플릿명을 입력해주세요.');
        return false;
      }
      if(!this.rowData.msgType){
        confirm.fnAlert(this.componentsTitle, '메시지타입을 선택해주세요.');
        return false;
      }
      if(!this.rowData.msgKind){
        confirm.fnAlert(this.componentsTitle, '메시지구분을 선택해주세요.');
        return false;
      }
      if(!this.rowData.otherProjectUseYn){
        confirm.fnAlert(this.componentsTitle, '타 프로젝트 사용여부를 선택해주세요.');
        return false;
      }
      if(!this.rowData.tmpltTitle){
        confirm.fnAlert(this.componentsTitle, '제목을 입력해주세요.');
        return false;
      }
      if(!this.rowData.tmpltContent){
        confirm.fnAlert(this.componentsTitle, '내용을 입력해주세요.');
        return false;
      }
      if(this.rowData.msgKind == 'A' && !this.rowData.rcvblcNumber){
        confirm.fnAlert(this.componentsTitle, '푸시 수신거부 방법을 입력해주세요.');
        return false;
      }
      if(this.rowData.msgType == 'IMAGE' && !this.rowData.imgUrl){
        confirm.fnAlert(this.componentsTitle, '이미지를 선택해주세요.');
        return false;
      }
      if(this.rowData.msgType == 'IMAGE' && !this.rowData.fileId){
        confirm.fnAlert(this.componentsTitle, '이미지 정보가 잘못되었습니다. 다시 이미지를 선택해주세요.');
        return false;
      }
      return true;
    },

    //임시저장 => 채널 설정관련 유효성 체크를  무시한다.
    save:function(){
      this.registYn = false;
      eventBus.$on('callbackEventBus', this.fnSaveIntegratedTemplate);
      confirm.fnConfirm(this.detailTitle, "템플릿을 저장 하시겠습니까?", "확인");
    },
    //등록 => 채널 설정관련 유효성 체크 필수
    complete:function(){
      this.registYn = true;
      eventBus.$on('callbackEventBus', this.fnCompleteIntegratedTemplate);
      confirm.fnConfirm(this.detailTitle, "템플릿을 등록 하시겠습니까?", "확인");
    },


    //template 정보 조회
    fnSetIntegratedTemplateInfo(){
      if(!this.$gfnCommonUtils.isEmpty(this.tmpltId)){
        this.fnSelectIntegratedTemplateInfo();
      }
    },

    fnPushOpenImageManagePopUp(){
      this.$refs.pushImgMng.fnSearch();
      this.pushImgMngOpen = !this.pushImgMngOpen;
    },
    fnPushOpenImageUploadPopUp : function(){
      this.pushImgUploadOpen = !this.pushImgUploadOpen;
    },
    fnPushSetImageInfo(imgInfo) {
      this.rowData.pushImgUrl = imgInfo.pushChImgUrl;
      this.rowData.pushFileId = imgInfo.pushFileId;
    },
    fnPushDelImg(){
      this.rowData.pushImgUrl = '';
      this.rowData.pushFileId = '';
    },

    fnSubString(str, sIdx, length){
      var shortStr = ''
      if(!this.fnIsEmpty(str)){
        shortStr = str.toString();
        if(shortStr.length > length){
          shortStr = shortStr.substring(sIdx, length) + '...  ';
        } else {
          shortStr = shortStr + '  ';
        }
      }
      return shortStr;
    },
    //빈값확인
    fnIsEmpty(str){
      if(str) return false;
      else return true
    },
  }
}
</script>