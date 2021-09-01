<template>
	<section v-if="isLogin == true && isErrPage == false">
		<header id="headerTop" class="bgColorGray">
			<h1><router-link to="/public/main"><img src="../../public/se2/images/logo.png" alt="LGU+ 통합메시징클라우드 로고"></router-link></h1>
			<p class="adminName">관리자 콘솔</p>
			<ul class="adminUtil">
				<li><router-link to="/uc/home" class="btnGuideBlack">사용자콘솔</router-link></li>
				<li><a @click="fnGoUserGuide" class="btnGuideBlack">이용 가이드</a></li>
				<!--li class="ml20"><router-link to="/public/main" class="bellIcon active"><i class="fas fa-bell"></i></router-link></li-->
				<li>
					<a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="fas fa-user-circle"></i> {{ this.loginId }}</a>	
					<ul class="dropdown-menu userDrop" role="menu">
						<li @click="fnCheckPwd('corp')"><a data-toggle="modal" data-target="#Client">고객사 정보</a></li>
						<li @click="fnCheckPwd('member')"><a data-toggle="modal" data-target="#Member-information">회원정보</a></li>
						<li><router-link to="/ac/qna">나의 문의내역</router-link></li>
						<li @click="clickLogout"><a href="#">로그아웃</a></li>
					</ul>
				</li>
			</ul>
			<!-- 회원정보 팝업-->
			<myPagePopup :memberInfo="memberInfo" :popReset="popReset"></myPagePopup>
			<!-- 회원정보 팝업 끝 -->
			<chkPwdPopup :popReset="popReset" :type="type"></chkPwdPopup>
			<!-- 고객사정보 팝업-->
			<corpInfoPopup :corpInfo="corpInfo" :popReset="popReset"></corpInfoPopup>
			<!-- 고객사정보 팝업 끝 -->
		</header>
	</section>
</template>

<script>
import loginApi from '@/modules/login/service/api';
import customerApi from '@/modules/customer/service/customerApi';
import tokenSvc from '@/common/token-service';
import { mapGetters } from 'vuex';
import myPagePopup from '@/modules/myPage/components/bp-myPagePopup.vue';
import corpInfoPopup from '@/modules/myPage/components/bp-corpInfoPopup.vue';
import chkPwdPopup from '@/modules/myPage/components/bp-chkPassword.vue';
import myPageApi from '@/modules/myPage/service/myPageApi';

export default {
	name: "webHeader",
	data() {
		return {
			menuList: null,
			isLogin: false,
			isErrPage: false,
			navActive: false,
			svcTypeCd: '',
			loginId: '',
			memberInfo : {},
			corpInfo : {},
			popReset : 0,
      		userGuideUrl : "www.naver.com",
			type : ""
		}
	},
	created() {
		this.isLogin = !!tokenSvc.getToken();
		this.svcTypeCd = tokenSvc.getToken().principal.svcTypeCd;
		this.loginId = tokenSvc.getToken().principal.loginId;
		let curPage = location.pathname;
		if (this.isLogin && curPage != "/login") {
			this.$store.commit("login/isLogin", true);
		} else {
			this.$store.commit("login/isLogin", false);
			this.isLogin = false;
			this.svcTypeCd = '';
		}
		customerApi.selectUserGuideUrl({}).then(response => {
			if (response.data.success) {
			this.userGuideUrl = response.data.data
			}
		});
	},
	components : {
		myPagePopup,
		corpInfoPopup,
		chkPwdPopup
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
		fnGoUserGuide(){
			var url = this.userGuideUrl
			var menusCd = jQuery('#M_menusCd').val()
			switch (menusCd) {
			  case 'AC_USER' :
				url = url + '/admin/user.html'
				break
			  case 'AC_PROJECT' :
				url = url + '/admin/project.html'
				break
			  case 'AC_USE_STATE' :
				url = url + '/admin/use-state.html'
				break
			  case 'AC_SETTLE_MNG' :
				url = url + '/admin/account.html'
				break
			  case 'AC_ADDR_LIST' :
				url = url + '/admin/address.html'
				break
			  case 'AC_RCVR_LIST' :
				url = url + '/admin/address.html#id7'
				break
			  case 'AC_STATIS_MON' :
				url = url + '/admin/statistics.html'
				break
			  case 'AC_AL_ALARM' :
				url = url + '/admin/alarm.html'
				break
			  case 'AC_AL_RECEPT_GRP' :
				url = url + '/admin/alarm.html#id9'
				break
			  case 'AC_AL_RECIPIENT' :
				url = url + '/admin/alarm.html#id12'
				break
			  default : 
			  	url = url + '/admin/summary.html'
				break
			}
			window.open(url)
		},
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
						//this.navClose();
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
		fnMyPage(){
			var params = {
				userId : tokenSvc.getToken().principal.userId
			};
			myPageApi.selectMemberInfo(params).then(response => {
				var result = response.data.data;
				this.memberInfo = result;
				this.popReset = this.popReset + 1;
			});

			jQuery("#myPagePopup").modal("show");
		},
		fnCheckPwd(type){
			this.popReset = this.popReset + 1;
			this.type = type;
			jQuery("#chkPwdPopup").modal("show");
		},
		fnMyCorp(){
			var params = {
				userId : tokenSvc.getToken().principal.userId
			};
			myPageApi.selectCorpInfo(params).then(response => {
				var result = response.data;
				if(result.success){
					this.corpInfo = result.data;
					this.popReset = this.popReset + 1;
				}
			});
			jQuery("#corpInfoPopup").modal("show");
		}
	}
};
</script>