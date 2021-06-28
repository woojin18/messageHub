<template>
	<section v-if="isLogin == true && isErrPage == false">
		<header id="headerTop" class="bgColor_adminConsole">
			<h1><router-link to="/public/main"><img src="../../public/se2/images/logo.png" alt="LGU+ 통합메시징클라우드 로고"></router-link></h1>
			<p class="adminName">사용자 콘솔</p>
			<ul class="adminUtil">
				<li><router-link to="/ac/home" class="btnGuideBlack">관리자콘솔</router-link></li>
				<li><a href="#self" class="btnGuideBlack">이용 가이드</a></li>
				<li class="ml20"><a href="#self" class="bellIcon active"><i class="fas fa-bell"></i></a></li>
				<li>
					<a href="#self" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="fas fa-user-circle"></i> designhon@gmail.com</a>	
					<ul class="dropdown-menu userDrop" role="menu">
						<li @click="fnMyPage"><a data-toggle="modal" data-target="#Member-information">회원정보</a></li>
						<li><router-link to="/uc/qna">나의 문의내역</router-link></li>
						<li @click="clickLogout"><a href="#">로그아웃</a></li>
					</ul>
				</li>
			</ul>
			<!-- 회원정보 팝업-->
			<myPagePopup :memberInfo="memberInfo" :popReset="popReset"></myPagePopup>
			<!-- 회원정보 팝업 끝 -->
		</header>
	</section>
</template>

<script>
import loginApi from '@/modules/login/service/api';
import tokenSvc from '@/common/token-service';
import { mapGetters } from 'vuex';
import myPagePopup from '@/modules/myPage/components/bp-myPagePopup.vue';
import myPageApi from '@/modules/myPage/service/myPageApi';

export default {
	name: "webUcHeader",
	data() {
		return {
			menuList: null,
			isLogin: false,
			isErrPage: false,
			navActive: false,
			svcTypeCd: '',
			
			memberInfo : {},
			popReset : 0
		}
	},
	created() {
		this.isLogin = !!tokenSvc.getToken();
		this.svcTypeCd = tokenSvc.getToken().principal.svcTypeCd;
		let curPage = location.pathname;
		if (this.isLogin && curPage != "/login") {
			this.$store.commit("login/isLogin", true);
		} else {
			this.$store.commit("login/isLogin", false);
			this.isLogin = false;
			this.svcTypeCd = '';
		}
	},
	components : {
		myPagePopup
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
		}
	}
};
</script>