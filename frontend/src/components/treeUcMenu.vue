<template>
	<div id="treeUcMenu"><input id="M_svcTypeCd" type="hidden" value="UC"><input id="M_roleCd" type="hidden"><input id="M_menusCd" type="hidden">
		<!-- sidebar -->
		<nav id="sidebar">
			<!-- 메뉴바 접기 -->
			<a href="#" class="sideMenu" title="사이드메뉴 축소/확대">
				<img src="../../public/se2/images/SideMenuOff.png" class="SideMenuOff" alt="사이드메뉴 축소">
				<img src="../../public/se2/images/SideMenuOn.png" class="SideMenuOn" alt="사이드메뉴 확대">
			</a>
			<!-- 메뉴바 접기 -->

			<!-- consoleMenu -->
			<dl>
				<dt>
					<a :title="projectName"><i class="fal fa-folder navIcon"></i><span>{{projectName}}<i class="far fa-chevron-right navArrow"></i></span></a>
					<div class="consoleMenu">
						<ul class="box-shadow">
							<li v-for="(item, i) in prdData" :key="i" :class="{active : repPrdState(item.projectId,item.projectName)}"><a @click="chgProject(item.projectId)">{{item.projectName}}</a></li>
						</ul>
					</div>
				</dt>
				<dd style="display:none"></dd>
			</dl>
			<!-- //consoleMenu -->

			<!-- lnb -->
			<ul>
				<!-- 대메뉴 -->
				<li><router-link to="/uc/home" @click.native="fnOpenDepth2($event)"><i class="fal fa-tachometer-alt-fastest navIcon"></i><span>대시보드</span></router-link></li>
				<li v-for="(item, i) in treeData" :key="i">
					<a @click="fnOpenDepth2($event)">
						<i v-bind:class="item.imgTag"></i><span>{{item.menusName}}</span><i class="far fa-chevron-down navArrow"></i>
					</a>
					<!-- 중메뉴 -->
					<div class="depth2Lnb" :id="'depth2_' + i" :style="showOption">
						<ul>
								<li v-for="(item2, j) in item.children" :key="j" >
							<div v-if="fnCheckUseCh(item2)">
									<router-link  v-if="item2.webUrl != ''" @click.native="fnOpenDepth3($event)" v-bind:to="{path:item2.webUrl}" v-bind:id="'M_'+item2.menusCd" v-bind:r="item2.read" v-bind:w="item2.save">{{item2.menusName}}</router-link>		<!-- url 주소 있으면 페이지 이동 -->
									<a  v-if="item2.webUrl == ''" @click="fnOpenDepth3($event)">
										<i v-bind:class="item2.imgTag"></i><span{{item2.menusName}}</span><i class="far fa-chevron-down navArrow" style="font-size: 10px;position: absolute;right: 20px"></i>
									</a>
									<!-- 소메뉴 -->
									<div class="depth3Lnb" :id="'depth3_' + j">
										<ul>
											<li v-for="(item3, m) in item2.children" :key="m">
												<router-link v-bind:to="{path:item3.webUrl}" @click.native="fnOpenDepth4($event)" v-bind:id="'M_'+item3.menusCd" v-bind:r="item3.read" v-bind:w="item3.save">{{item3.menusName}}</router-link>
											</li>
										</ul>
									</div>
									<!-- 소메뉴 -->
							</div>
								</li>
						</ul>
					</div>
					<!-- 중메뉴 -->
				</li>
			</ul>
			<!-- //lnb -->
		</nav>
		<!-- //sidebar --> 
	</div>
</template>

<script>
import api from '@/modules/login/service/api';
import * as utils from '@/common/utils';
import { consts } from '@/common/config';
import tokenSvc from '@/common/token-service';
import VueCookies from 'vue-cookies'

export default {
	name: 'treeUcMenu',
	props: {
	},
	data() {
		return {
			treeData :  [
				{name: '',
					children: [
						{name: '',
						 urlPath: '',
						 children: [
								{name: '', urlPath: ''}
							]
						},
					]
				}
			],
			prdData : [],
			useChData : [],
			projectId : utils.getCookie(consts.projectId),
			projectName : null,
			showOption: {
				display: 'none'
			}
		}
	},
	mounted(){
		this.init();
		this.fnProjectChUseListForUser();
		this.fnProjectList();
		this.fnMenuList();
	},
	methods: {
		init() {
			//외부 영역 클릭시 닫기
			jQuery(document).click(function (e){
				var container = jQuery(".consoleMenu");
				if( container.has(e.target).length === 0)
				container.fadeOut('fast');
			});
			jQuery('#sidebar > dl > dt > a').click(function() {
				event.stopPropagation();
				jQuery('.consoleMenu').fadeToggle();
				jQuery('#sidebar > dl > dt').removeClass('active');
				jQuery(this).parent('li').addClass('active');
			});
			jQuery('.SideMenuOff').click(function() {
				setTimeout(function()
				{
					jQuery('#sidebar').addClass('thum');
					jQuery('.depth2Lnb').addClass('thum');
					jQuery('#sidebar').css('width','70px');
					jQuery('#sidebar > ul > li > a .navIcon').css('margin-right','0px');
					jQuery('#sidebar > ul > li > a').css('text-align','center');
					jQuery('#sidebar > dl > dt > a > span').hide();
					jQuery('#sidebar > ul > li > a > span').hide();
					jQuery('.SideMenuOff').hide();
					jQuery('.navArrow').hide();
					jQuery('.SideMenuOn').show();
				}, 300);
			});
			jQuery('.SideMenuOn').click(function() {
				setTimeout(function()
				{
					jQuery('#sidebar').removeClass('thum');
					jQuery('.depth2Lnb').removeClass('thum');
					jQuery('#sidebar').css('width','230px');
					jQuery('#sidebar > ul > li > a .navIcon').css('margin-right','10px');
					jQuery('#sidebar > ul > li > a').css('text-align','left');
					jQuery('#sidebar > dl > dt > a > span').show();
					jQuery('#sidebar > ul > li > a > span').show();
					jQuery('.SideMenuOn').hide();
					jQuery('.navArrow').show();
					jQuery('.SideMenuOff').show();		
				}, 300);
			});		
		},
		fnMenuList() {
			var roleCd = tokenSvc.getToken().principal.role == 'ADMIN' && tokenSvc.getToken().principal.svcTypeCd == 'AC' ? 'USER' : tokenSvc.getToken().principal.role;
			jQuery('#M_roleCd').val(roleCd);
			var params = {
				"user_id"    : tokenSvc.getToken().principal.userId,
				"role_cd"    : roleCd,
				"svc_type_cd" : 'UC'
			};

			api.getMenuForRole(params).then(response =>{
				var result = response.data;
				if (result.success && result.data != null) {
					this.treeData = result.data.children;
				}
			});
		},
		fnProjectChUseListForUser() {
			var params = {};

			api.getProjectChUseListForUser(params).then(response =>{
				var result = response.data;
				if (result.success && result.data != null) {
					this.useChData = result.data[0];
				}
			});
		},
		fnCheckUseCh(data){
			// 프로젝트 채널 사용 여부 확인해서 메뉴 생성
			var moYn = this.useChData.moYn;		// 메뉴 없음
			var kkoYn = this.useChData.kkoYn;
			var rcsYn = this.useChData.rcsYn;
			var pushYn = this.useChData.pushYn;
			var smsmmsYn = this.useChData.smsmmsYn;  
			

			if( kkoYn === 'N' ){
				if( data.menusCd === 'UC_SND_FRND_MSG' || data.menusCd === 'UC_SND_ALIM_MSG' ||  data.menusCd === 'UC_FRND_TPL' || data.menusCd === 'UC_ALIM_TPL' ){
					return false;
				}
			} else if( rcsYn === 'N' ){
				if( data.menusCd === 'UC_SND_RCS_MSG' || data.menusCd === 'UC_SND_RCS_MSG' ){
					return false;
				}
			} else if( pushYn === 'N' ){
				if( data.menusCd === 'UC_SND_PUSH_MSG' || data.menusCd === 'UC_PUSH_TPL'  ){
					return false;
				}
			} else if( smsmmsYn === 'N' ){
				if( data.menusCd === 'UC_SND_SMS_MSG' || data.menusCd === 'UC_SMS_TPL' ){
					return false;
				}
			}
			return true;
		},
		fnOpenDepth2(event) {
			var $this = event.currentTarget
			if (jQuery('.SideMenuOff').is('visible') == true && jQuery($this).parent('li').hasClass('active')) {
				return
			}
			if ($this.href.length > 0) {
				jQuery('#sidebar > ul > li .depth2Lnb > ul li.active').removeClass('active')
			}
			jQuery('#sidebar > ul > li.active > a').next('.depth2Lnb').hide()
			jQuery('#sidebar > ul > li.active').removeClass('active').children("a").find(".navArrow").removeClass("fa-chevron-up").addClass("fa-chevron-down")

			jQuery($this).parent('li').addClass('active').children("a").find(".navArrow").removeClass("fa-chevron-down").addClass("fa-chevron-up")
			if ($this.href.length == 0) {
				jQuery($this).next('.depth2Lnb').show()
			}
		},
		fnOpenDepth3(event) {
			var $this = event.currentTarget
			
			jQuery('#sidebar > ul > li .depth2Lnb > ul > li.active').removeClass('active').children("a").find(".navArrow").removeClass("fa-chevron-up").addClass("fa-chevron-down")
			jQuery($this).closest('li').addClass('active').children("a").find(".navArrow").removeClass("fa-chevron-down").addClass("fa-chevron-up")

			jQuery('.depth3Lnb').hide();
			jQuery($this).next('.depth3Lnb').fadeIn();
			jQuery('.depth2Lnb > ul > li').removeClass('active');
			jQuery($this).closest('li').addClass('active');
			jQuery('.depth2Lnb > ul > li > a > .depth2Lnb_icon').attr("class","far fa-plus-square depth2Lnb_icon");
			jQuery('.depth2Lnb > ul > li.active > a > .depth2Lnb_icon').attr("class","far fa-minus-square depth2Lnb_icon");
		},
		fnOpenDepth4(event) {
			var $this = event.currentTarget
			
			jQuery('#sidebar > ul > li .depth2Lnb > ul > li .depth3Lnb > ul > li.active').removeClass('active')
			jQuery($this).parent('li').addClass('active')
		},
		fnProjectList() {
			var params = {
			};

			api.getProjectForUser(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.prdData = result.data;
				}
			});
		},
		repPrdState(prdId, prdName) {
			if (prdId == this.projectId) {
				this.projectName = prdName
				return true;
			}
			else false;
		},
		chgProject(prdId) {
			VueCookies.set('project', prdId);
			jQuery('.consoleMenu').hide();
			jQuery('.box-shadow li').removeClass('active');
			jQuery(event.target).closest('li').addClass('active');
			this.$router.replace('/');
			this.$nextTick(() => this.$router.replace("/uc/home"));
		}
	}
}
</script>

<style lang="scss">
</style>