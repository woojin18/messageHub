<template>
	<section v-if="isLogin == true && isErrPage == false">
		<header id="headerTop" class="bgColor_adminConsole">
			<h1><img src="../../public/se2/images/logo.png" alt="LGU+ 통합메시징클라우드 로고"></h1>
			<p class="adminName">사용자 콘솔</p>
			<ul class="adminUtil">
				<li><a href="/ac/home" class="btnGuideBlack">관리자콘솔</a></li>
				<li><a href="#self" class="btnGuideBlack">이용 가이드</a></li>
				<li class="ml20"><a href="#self" class="bellIcon active"><i class="fas fa-bell"></i></a></li>
				<li>
					<a href="#self" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="fas fa-user-circle"></i> designhon@gmail.com</a>	
					<ul class="dropdown-menu userDrop" role="menu">
						<li><a href="#" data-toggle="modal" data-target="#Client">고객사 정보</a></li>
						<li><a href="#">캐시관리</a></li>
						<li><a href="#" data-toggle="modal" data-target="#Member-information">회원정보</a></li>
						<li><a href="#">나의 문의내역</a></li>
						<li><a href="#">공지사항</a></li>
						<li @click="clickLogout"><a href="#">로그아웃</a></li>
					</ul>
				</li>
			</ul>
		</header>
	</section>
</template>

<script>
import loginApi from '@/modules/login/service/api';
import tokenSvc from '@/common/token-service';
import { mapGetters } from 'vuex';

export default {
	name: "webUcHeader",
	data() {
		return {
			menuList: null,
			isLogin: false,
			isErrPage: false,
			navActive: false,
			svcTypeCd: ''
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
	}
};
</script>