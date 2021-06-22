<template>
  <div>

    <article id="mainVisualWrap">
      <section>
        <ul class="mainBxslider">
          <li>
            <div class="mainVisualTitle">
              <div class="wow animated fadeInUp" data-wow-duration="1s">
                <h2>성공하는 기업들이 일하는<br>스마트한 방식<br>U+ 통합메시징 Cloud</h2>
                <router-link :to="{ name: 'intro' }" tag="a" class="mvBtn" title="서비스 소개">서비스 소개</router-link>
              </div>
            </div>
            <img src="@/assets/images/main/mainVisual1.jpg" alt="">
          </li>
          <li>
            <div class="mainVisualTitle">
              <div class="wow animated fadeInUp" data-wow-duration="1s">
                <h2 class="colorWhite">U+ 통합 메시징 클라우드<br>통합 메시지 발송, 대체발송 등<br>강력한 기능을<br>지금 사용해보세요.</h2>
                <router-link :to="{ name: 'signUp' }" tag="a" class="mvBtn" title="서비스 가입">서비스 가입</router-link>
              </div>
            </div>
            <img src="@/assets/images/main/mainVisual2.jpg" alt="">
          </li>
        </ul>
      </section>
    </article>
    <!-- //mainVisualWrap -->
    
    <div id="contentWrap">
      <!-- noticeWrap -->
      <article id="noticeWrap">
        <section>
          <div class="noticeList">
            <h3>공지사항
              <router-link :to="{name: 'notice'}" tag="a" class="more">
                <img src="@/assets/images/main/noticeMore.png" alt="더보기 아이콘"><span></span>
              </router-link>
            </h3>
            <ul class="listSt">
              <li v-for="(noticeInfo, idx) in noticeInfoList" :key="idx">
                <router-link :to="{ name: 'noticeDetail', params: { noticeId: noticeInfo.noticeId }}" title="해당 게시글로 이동">
                  <span class="noti_tt">
                    <span v-if="!$gfnCommonUtils.isEmpty(noticeInfo.noticeTypeCdName)" class="newIcon">{{noticeInfo.noticeTypeCdName}}</span> 
                    {{noticeInfo.title}}
                  </span>
                  <span class="noti_day">{{noticeInfo.regDt}}</span>
                </router-link>
              </li>
            </ul>
          </div>
          <div class="archivesList">
            <h3>자료실
              <router-link :to="{name: 'library'}" tag="a" class="more">
                <img src="@/assets/images/main/noticeMore.png" alt="더보기 아이콘">
              </router-link>
            </h3>
            <ul class="listSt">
              <li v-for="(libraryInfo, idx) in libraryInfoList" :key="idx">
                <router-link :to="{ name: 'libraryDetail', params: { libraryId: libraryInfo.libraryId }}" title="해당 게시글로 이동">
                  <span class="noti_tt">{{libraryInfo.title}}
                    <img v-if="libraryInfo.existsFileYn == 'Y'" src="@/assets/images/main/user_sub03_1_fileicon.png" alt="파일 아이콘" class="fileIcon">
                  </span>
                  <span class="noti_day">{{libraryInfo.regDt}}</span>
                </router-link>
              </li>
            </ul>
          </div>
        </section>
      </article>
      <!-- //noticeWrap -->

      <!-- mainConWrap1 -->
      <article id="mainConWrap1">
        <section>
          <h3 class="subTitle wow animated fadeInUp">쉽고 안전하고 스마트한 U+ 통합 메시징 클라우드를 소개합니다.</h3>
          <ul class="CloudIcon">
            <li class="wow animated fadeInLeft" data-wow-duration="1s">
              <img src="@/assets/images/main/mainCloud_icon01.png" alt="아이콘1">
              <p class="IconMainText">메시지 통합 플랫폼</p>
              <p class="IconSubText">푸시, 카카오톡, 문자 등<br>발송 채널 별로 분산된 시스템을<br>하나의 플랫폼으로 관리</p>
            </li>
            <li class="wow animated fadeInLeft" data-wow-delay=".3s" data-wow-duration="1s">
              <img src="@/assets/images/main/mainCloud_icon02.png" alt="아이콘2">
              <p class="IconMainText">비용 절감</p>
              <p class="IconSubText">정확한 타게팅 발송, 중복 발송 방지,<br>발송채널 우선순위 설정 등<br>누수되는 비용 지출 절감</p>
            </li>
            <li class="wow animated fadeInLeft" data-wow-delay=".6s" data-wow-duration="1s">
              <img src="@/assets/images/main/mainCloud_icon03.png" alt="아이콘3">
              <p class="IconMainText">메시지 발송 통계</p>
              <p class="IconSubText">도달률, 성공률, 수신확인여부 등<br>정확한 메시지 발송 성과 관리</p>
            </li>
            <li class="wow animated fadeInLeft" data-wow-delay=".9s" data-wow-duration="1s">
              <img src="@/assets/images/main/mainCloud_icon04.png" alt="아이콘4">
              <p class="IconMainText">통합 채널 관리</p>
              <p class="IconSubText">메시징 통합 채널 사용으로<br>모든 메시지 전송 및 발송 성공률 Up</p>
            </li>
          </ul>
        </section>
      </article>
      <!-- //mainConWrap1 -->

      <!-- quiryWrap -->
      <article id="quiryWrap" class="wow animated fadeInUp">
        <section>
          <div class="quiryList">
            <h3>U+ 통합 메시징 클라우드 문의</h3>
            <div class="of_h">
              <h5 class="inline-block  text-left float-left font-size16" style="width:20%">문의유형</h5>
              <div class="inline-block float-left">
                <template v-for="(inqueiryType, idx) in inqueiryTypeList">
                  <input :key="idx" 
                    type="radio" 
                    name="inqueiryType" 
                    :value="inqueiryType.codeVal1" 
                    :id="'inqueiryType_'+inqueiryType.codeVal1"
                    v-model="inqueiryInputData.questType"
                  >
                  <label :key="idx+'_sub'" 
                    :for="'inqueiryType_'+inqueiryType.codeVal1" 
                    class="mr20 font-size14"
                  >{{inqueiryType.codeName1}}</label>
                </template>
              </div>
            </div>
            <input type="text" class="form-control mt15" placeholder="이름" v-model="inqueiryInputData.inputName" maxlength="20">
            <input type="text" class="form-control mt15" placeholder="휴대폰 번호 ( - 없이 입력)" v-model="inqueiryInputData.hpNumber" maxlength="20">
            <input type="text" class="form-control mt15" placeholder="E-mail" v-model="inqueiryInputData.email" maxlength="40">
            <input type="text" class="form-control mt15" placeholder="제목" v-model="inqueiryInputData.title" maxlength="100">
            <textarea 
              class="form-textarea height180  mt15" 
              placeholder="궁금하신 내용을 적어주세요."
              v-model="inqueiryInputData.content" 
              maxlength="4000"
            ></textarea>
            <div class="quiryAgree">
              <input type="checkbox" id="agree1" class="checkStyle2" value="서비스 이용약관 동의" v-model="inqueiryInputData.agree">
              <label for="agree1">[필수] 개인정보 수집 및 이용 동의에 동의합니다.</label>
              <!-- <a href="#self" class="provisionMore">내용보기</a> -->
              
            </div>
            <a href="#self" @click.prevent="fnRegisterInquiry" class="btnStyle2 backRed" title="상담신청">상담신청</a>
          </div>
        </section>
      </article>
      <!-- //quiryWrap -->
    </div>
    <QuickRight></QuickRight>
  </div>
</template>

<script>
import QuickRight from "@/modules/main/components/bc-quickRight.vue";

import customereApi from "@/modules/customer/service/customerApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  name: 'consoleMain',
  components : {
    QuickRight
  },
  data() {
    return {
      componentsTitle: '메인',
      inqueiryTypeList: [],
      noticeInfoList: [],
      libraryInfoList: [],
      inqueiryInputData: {
        agree: false,
        inputName: '',
        hpNumber : '',
        emailId : '',
        emailDomain : '',
        questType : '',
        email : '',
        title : '',
        content :'',
      }
    }
  },
  watch: {
    'inqueiryInputData.hpNumber'(){
      return this.inqueiryInputData.hpNumber = this.inqueiryInputData.hpNumber.replace(/[^0-9]/g, '');
    }
  },
  mounted() {
    this.fnSetSlider();
    this.fnOpenNoticePopup();
    this.fnSelectNoticeList();
    this.fnSelectLibraryList();
    this.fnSelectFaqType();
  },
  methods: {
    fnSetSlider(){
      jQuery('.mainBxslider').bxSlider({
        mode: 'fade',
        pager: true,
        touchEnabled : (navigator.maxTouchPoints > 0),
        pause: 6000
      });
    },

    fnIsValid(){
      if(!this.inqueiryInputData.inputName){
        confirm.fnAlert(this.componentsTitle, '이름을 입력해주세요.');
        return false;
      }
      if(!this.inqueiryInputData.hpNumber){
        confirm.fnAlert(this.componentsTitle, '휴대폰 번호를 입력해주세요.');
        return false;
      }
      if(this.inqueiryInputData.hpNumber.length < 10){
        confirm.fnAlert(this.componentsTitle, '잘못된 휴대폰 번호 입니다.');
        return false;
      }
      if(!this.inqueiryInputData.email){
        confirm.fnAlert(this.componentsTitle, 'E-mail 을 입력해주세요.');
        return false;
      }
      if(!this.inqueiryInputData.questType){
        confirm.fnAlert(this.componentsTitle, '문의종류를 선택해주세요.');
        return false;
      }
      if(!this.inqueiryInputData.title){
        confirm.fnAlert(this.componentsTitle, '제목을 입력해주세요.');
        return false;
      }
      if(!this.inqueiryInputData.content){
        confirm.fnAlert(this.componentsTitle, '내용을 입력해주세요.');
        return false;
      }
      if(!this.inqueiryInputData.agree){
        confirm.fnAlert(this.componentsTitle, '개인정보 수집 및 이용 동의에 동의해주세요.');
        return false;
      }
      return true;
    },
    fnRegisterInquiry(){
      if(this.fnIsValid() == false) return;
      eventBus.$on('callbackEventBus', this.fnProcRegisterInquiry);
      confirm.fnConfirm(this.componentsTitle, "문의 하시겠습니까?", "확인");
    },
    async fnProcRegisterInquiry(){
      let params = Object.assign({}, this.inqueiryInputData);
      params.userId = this.inqueiryInputData.inputName;

      await customereApi.insertQuestBoard(params).then(response => {
        const result = response.data;
        if(result.success) {
          confirm.fnAlert(this.componentsTitle, '문의 되었습니다.');
          Object.assign(this.$data.inqueiryInputData, this.$options.data().inqueiryInputData);
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    async fnSelectFaqType(){
      const params = {
        codeTypeCd : "QNA_TYPE",
        useYN : "Y"
      };
      await customereApi.selectCodeList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          this.inqueiryTypeList = Object.assign({}, result.data);
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    async fnOpenNoticePopup(){
      let noticePopupList = [];
      let noticeIdArr = [];
      let exceptNoticeIds = this.$cookies.get('exceptNoticeIds');
      if(!this.$gfnCommonUtils.isEmpty(exceptNoticeIds)){
        noticeIdArr = exceptNoticeIds.split(',');
      }

      const params = {
        sltPopupYn: 'Y',
        exceptNoticeIds: noticeIdArr
      };
      await customereApi.selectNoticeList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          noticePopupList = result.data;
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });

      const vm = this;
      let routeData = '';
      const noticePopupNm = 'noticePopup';
      const popupOtion = 'width=600,height=400,left=200,top=200,scrollbars=yes';
      noticePopupList.forEach(function(info){
        routeData = vm.$router.resolve({name: noticePopupNm, query: {noticeId: info.noticeId}});
        vm.fnOpenWindowPop(routeData.href, noticePopupNm+info.noticeId, popupOtion);
      });
    },
    fnOpenWindowPop(url, name, specs){
      window.open(url, name, specs);
    },
    async fnSelectNoticeList(){
      const params = {
        pageNo: 1,
        listSize: 4
      };
      await customereApi.selectNoticeList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          this.noticeInfoList = result.data;
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    async fnSelectLibraryList(){
      const params = {
        pageNo: 1,
        listSize: 4
      };
      await customereApi.selectLibraryList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          this.libraryInfoList = result.data;
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
  }
}
</script>