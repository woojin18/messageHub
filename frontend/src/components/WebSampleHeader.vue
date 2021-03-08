<template>
  <section v-if="isLogin == true && isErrPage == false">
    <div class="header_wrap" :class="{active: navActive}" @mouseleave="navClose">
      <header class="header">
        <h1 class="logo"><a href="javascript:void(0);" @click="clickMenu('/home', 'Y');"> <img src="../assets/images/common/logo.png" alt="Web Sample"></a></h1>
        <nav>
          <ul @mouseover="navOpen">
            <li v-for="child in menuList" :key="child.menuId" class="node1">
              <a href="javascript:void(0);" :data-menu-id="child.menuId" :data-edit-yn="child.editYn" :data-menu-url="child.url" v-if="child.useYn == 'Y'" @click="clickMenu(child.url, child.useYn);">{{ child.menuNm }}</a>
              <a href="javascript:void(0);" :data-menu-id="child.menuId" :data-edit-yn="child.editYn" :data-menu-url="child.url" v-if="runModeType == 'POC' && child.useYn == 'N'">{{ child.menuNm }}
                <div class="box_tooltip">
                  <p>서비스 준비중</p>
                </div>
              </a>

              <ul class="sub_menu" v-if="child.children.length > 0">
                <li v-for="child2 in child.children" :key="child2.menuId" class="node2">
                  <a href="javascript:void(0);" :data-menu-id="child2.menuId" :data-edit-yn="child2.editYn" :data-menu-url="child2.url" v-if="child2.useYn == 'Y'" @click="clickMenu(child2.url, child2.useYn);">{{ child2.menuNm }}</a>
                  <a href="javascript:void(0);" :data-menu-id="child2.menuId" :data-edit-yn="child2.editYn" :data-menu-url="child2.url" v-if="runModeType == 'POC' && child2.useYn == 'N'">{{ child2.menuNm }}
                    <div class="box_tooltip">
                      <p>서비스 준비중</p>
                    </div>
                  </a>
                </li>
              </ul>
            </li>
          </ul>
          <div class="empty_wrap"><span class="emptyspace"></span></div> <!-- 191214 추가 -->
          <div class="util_wrap"><a href="javascript:void(0);" class="btn_user logout" @click="clickLogout">로그아웃</a></div> <!-- 191214 수정 -->
        </nav>
      </header>
    </div>
    <div :class="{ active : navActive }" class="header_dimm"></div>
  </section>
</template>

<script>
import loginApi from "@/modules/login/service/api"
import tokenSvc from '@/common/token-service';
import { mapGetters } from 'vuex';

export default {
  name: "webSampleHeader",
  data() {
    return {
      menuList: null,
      isLogin: false,
      isErrPage: false,
      navActive: false,
    }
  },
  created() {
    this.isLogin = !!tokenSvc.getToken();
    let curPage = location.pathname;
    if (this.isLogin && curPage != "/login") {
      this.$store.commit("login/isLogin", true);
    } else {
      this.$store.commit("login/isLogin", false);
      this.isLogin = false;
    }
  },
  computed: {
    ...mapGetters({
      getLogin: 'login/isLogin',
      getErrorPage: 'login/isErrorPage',
    }),
  },
  watch: {
    getLogin(data) {
      if (data != null && data != '' && data == true) {
        this.isLogin = true;
      } else {
        this.isLogin = false;
        this.menuList = null;
      }
    },
    getErrorPage(data) {
      if (data != null && data != '' && data == true) {
        this.isErrPage = true;
      } else {
        this.isErrPage = false;
      }
    },
  },
  methods: {
    clickMenu(link, useYn) {
      if (useYn == "Y") {
        if (link != null && link != '') {
          this.navClose();

          if (location.pathname == link) {
            this.reload();
          } else {
          
            this.$router.push({
              path: link
            });
          }
        }
      } 
    },
    reload(){
        var location = this.$route.fullPath;
        if (location == "/" || location == "") {
          // nothing
        } else {
          this.$router.replace('/');
          this.$nextTick(() => this.$router.replace(location));
        }
    },
    clickLogout() {
      let result = confirm("로그아웃 하시겠습니까?");
      if (result) {
        loginApi.logout().then(response => {
          if (response.data.success) {
            this.navClose();
            this.$router.push({
              path: "/login"
            });
          }
        });
      }
      
    },
    navOpen: function() {
      this.navActive = true;
      var myHeaderWrap = document.querySelector('.header_wrap');
      var el = document.querySelector('header.header');
      var myHeight = el.offsetHeight;
      myHeaderWrap.setAttribute('style', 'height:'+myHeight+'px');
    },
    navClose: function() {
      this.navActive = false;
      var myHeaderWrap = document.querySelector('.header_wrap');
      myHeaderWrap.setAttribute('style', '');
    },
  }
};
</script>
