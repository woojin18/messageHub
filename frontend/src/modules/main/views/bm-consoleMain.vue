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
            <input type="" class="form-control" id="" placeholder="이름">
            <input type="" class="form-control mt15" id="" placeholder="휴대폰 번호 ( - 없이 입력)">
            <input type="" class="form-control mt15" id="" placeholder="E-mail">
            <textarea class="form-textarea height180  mt15" placeholder="궁금하신 내용을 적어주세요."></textarea>
            <div class="quiryAgree">
              <input type="checkbox" id="agree1" class="checkStyle2" value="서비스 이용약관 동의"><label for="agree1">[필수] 개인정보 수집 및 이용 동의에 동의합니다.</label> <a href="#self" class="provisionMore">내용보기</a>
            </div>
            <a href="#self" class="btnStyle2 backRed" title="상담신청">상담신청</a>
          </div>
        </section>
      </article>
      <!-- //quiryWrap -->
    </div>
    <QuickRight></QuickRight>
    <NoticePopup 
      :noticePopupOpen.sync="noticePopupOpen" 
      ref="noticePopup"
    />
  </div>
</template>

<script>
import QuickRight from "@/modules/main/components/bc-quickRight.vue";
import NoticePopup from "@/modules/customer/components/bp-notice.vue";

import customereApi from "@/modules/customer/service/customerApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: 'consoleMain',
  components : {
    QuickRight,
    NoticePopup
  },
  data() {
    return {
      componentsTitle: '메인',
      noticePopupOpen : false,
      noticeInfoList: [],
      libraryInfoList: [],
    }
  },
  mounted() {
    this.fnSetSlider();
    //this.fnOpenNoticePopup();
    this.fnSelectNoticeList();
    this.fnSelectLibraryList();
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
    fnOpenNoticePopup(){
      const params = {
        sltPopupYn: this.sltPopupYn
      };
      customereApi.selectNoticeList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          console.log(result);
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });


      this.noticePopupOpen = true;
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