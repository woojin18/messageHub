<template>
	<section v-if="isLogin == true && isErrPage == false">
		<header id="headerTop" class="bgColor_adminConsole">
			<h1><router-link to="/public/main"><img src="../../public/se2/images/logo.png" alt="LGU+ 통합메시징클라우드 로고"></router-link></h1>
			<p class="adminName">사용자 콘솔</p>
			<ul class="adminUtil">
				<li><router-link to="/ac/home" class="btnGuideBlack">관리자콘솔</router-link></li>
				<li><a @click="fnGoUserGuide" class="btnGuideBlack">이용 가이드</a></li>
				<!--li class="ml20"><router-link to="/public/main" class="bellIcon active"><i class="fas fa-bell"></i></router-link></li-->
				<li>
					<a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="fas fa-user-circle"></i>{{ this.loginId }}</a>	
					<ul class="dropdown-menu userDrop" role="menu">
						<li @click="fnCheckPwd"><a data-toggle="modal" data-target="#Member-information">회원정보</a></li>
						<li><router-link to="/uc/qna">나의 문의내역</router-link></li>
						<li @click="clickLogout"><a href="#">로그아웃</a></li>
					</ul>
				</li>
			</ul>
			<!-- 회원정보 팝업-->
			<myPagePopup :memberInfo="memberInfo" :popReset="popReset"></myPagePopup>
			<!-- 회원정보 팝업 끝 -->
			<chkPwdPopup :popReset="popReset" :type="type"></chkPwdPopup>
		</header>
	</section>
</template>

<script>
import loginApi from '@/modules/login/service/api';
import customerApi from '@/modules/customer/service/customerApi';
import tokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import { mapGetters } from 'vuex';
import myPagePopup from '@/modules/myPage/components/bp-myPagePopup.vue';
import myPageApi from '@/modules/myPage/service/myPageApi';
import chkPwdPopup from '@/modules/myPage/components/bp-chkPassword.vue';


export default {
	name: "webUcHeader",
	data() {
		return {
			menuList: null,
			isLogin: false,
			isErrPage: false,
			navActive: false,
			svcTypeCd: '',
			loginId: '',
			memberInfo : {},
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
			  case 'UC_SND_SMS_MSG' :
				url = url + '/user/send-message.html'
				break
			  case 'UC_SND_ALIM_MSG' :
				url = url + '/user/send-message.html#id6'
				break
			  case 'UC_SND_FRND_MSG' :
				url = url + '/user/send-message.html#id12'
				break
			  case 'UC_SND_RCS_MSG' :
				url = url + '/user/send-message.html#rcs'
				break
			  case 'UC_SND_PUSH_MSG' :
				url = url + '/user/send-message.html#id20'
				break
			  case 'UC_SND_INTG_MSG' :
				url = url + '/user/send-message.html#id26'
				break
			  case 'UC_SND_SMRT_MSG' :
				url = url + '/user/send-message.html#id26'
				break
			  case 'UC_MEST_RCV' :
				url = url + '/user/send-state.html'
				break
			  case 'UC_WBSN_RCV' :
				url = url + '/user/send-state.html#id4'
				break
			  case 'UC_BKSN_RCV' :
				url = url + '/user/send-state.html#id5'
				break
			  case 'UC_MORE_RCV' :
				url = url + '/user/send-state.html#mo'
				break
			  case 'UC_SMS_TPL' :
				url = url + '/user/template.html'
				break
			  case 'UC_ALIM_TPL' :
				url = url + '/user/template.html#id4'
				break
			  case 'UC_FRND_TPL' :
				url = url + '/user/template.html#id9'
				break
			  case 'UC_RCS_TPL' :
				url = url + '/user/template.html#rcs'
				break
			  case 'UC_PUSH_TPL' :
				url = url + '/user/template.html#id20'
				break
			  case 'UC_INTG_TPL' :
				url = url + '/user/template.html#id24'
				break
			  case 'UC_SMRT_TPL' :
				url = url + '/user/template.html#id24'
				break
			  case 'UC_DAY_CH_STATIS' :
				url = url + '/user/statistics.html'
				break
			  case 'UC_MONTH_CH_STATIS' :
				url = url + '/user/statistics.html#id4'
				break
			  default : 
			  	url = url + '/user/summary.html'
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
			eventBus.$on('callbackEventBus', this.logoutPrc);
			confirm.fnConfirm("", "로그아웃 하시겠습니까?", "확인");
		},
		logoutPrc() {
			loginApi.logout().then(response => {
				if (response.data.success) {
					//this.navClose();
					this.$router.push({
						path: "/login"
					});
				}
			});
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
		fnCheckPwd(){
			this.popReset = this.popReset + 1;
			this.type = 'member';
			jQuery("#chkPwdPopup").modal("show");
		}
	}
};
</script>