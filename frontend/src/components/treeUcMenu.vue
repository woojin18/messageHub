<template>
	<div id="treeUcMenu">
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
					<a href="#" title="비트큐브"><i class="fal fa-folder navIcon"></i><span>비트큐브<i class="fas fa-cog cog"></i> <i class="far fa-chevron-right navArrow"></i></span></a>
					<div class="consoleMenu">
						<ul class="box-shadow">
							<li v-for="(item, i) in prdData" :key="i" :class="{active : repPrdState(item.projectId)}"><a @click="chgProject(item.projectId)">{{item.projectName}}</a></li>
						</ul>
					</div>
				</dt>
				<dd style="display:none"></dd>
			</dl>
			<!-- //consoleMenu -->

			<!-- lnb -->
			<ul>
				<!-- 대메뉴 -->
				<li class="on"><router-link to="/uc/home"><i class="fal fa-tachometer-alt-fastest navIcon"></i><span>대시보드</span></router-link></li>
				<li v-for="(item, i) in treeData" :key="i">
					<a @click="fnOpenDepth2(i)">
						<i v-html="item.imgTag"></i><span>&nbsp;{{item.menusName}}</span><i class="far fa-chevron-down navArrow"></i>
					</a>
					<!-- 중메뉴 -->
					<div class="depth2Lnb" :id="'depth2_' + i" :style="showOption">
						<ul>
							<li v-for="(item2, j) in item.children" :key="j" >
								<router-link  v-if="item2.webUrl != ''" v-bind:to="{path:item2.webUrl}">{{item2.menusName}}</router-link>		<!-- url 주소 있으면 페이지 이동 -->
								<a  v-if="item2.webUrl == ''" @click="fnOpenDepth3(i,j)">
									<i v-html="item2.imgTag"></i><span>&nbsp;{{item2.menusName}}</span><i class="far fa-chevron-down navArrow" style="font-size: 10px;position: absolute;right: 20px"></i>
								</a>
								<!-- 소메뉴 -->
								<div class="depth3Lnb" :id="'depth3_' + j">
									<ul>
										<li v-for="(item3, m) in item2.children" :key="m">
											<router-link v-bind:to="{path:item3.webUrl}">{{item3.menusName}}</router-link>
										</li>
									</ul>
								</div>
								<!-- 소메뉴 -->
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
			projectId : utils.getCookie(consts.projectId),
			showOption: {
				display: 'none'
			}
		}
	},
	mounted(){
		this.init();
		this.fnMenuList();
		this.fnProjectList();
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
			var params = {
				"user_id"    : tokenSvc.getToken().principal.userId,
				"role_cd"    : tokenSvc.getToken().principal.role == 'ADMIN' && tokenSvc.getToken().principal.svcTypeCd == 'AC' ? 'USER' : tokenSvc.getToken().principal.role,
				"svc_type_cd" : 'UC'
			};

			api.getMenuForRole(params).then(response =>{
				var result = response.data;
				if (result.success && result.data != null) {
					this.treeData = result.data.children;
				}
			});
		},
		fnOpenDepth2(index) {
			var depth2Sts = jQuery("#depth2_" + index).css("display");
			if (depth2Sts === 'block') {
				jQuery("#depth2_" + index).css("display", "none");
			} else {
				jQuery("#depth2_" + index).css("display", "block");
			}
		},
		fnOpenDepth3(index1, index2) {
			jQuery("#depth2_" + index1).css("display", "block");
			var depth3Sts = jQuery("#depth3_" + index2).css("display");
			if (depth3Sts === 'block') {
				jQuery("#depth3_" + index2).css("display", "none");
			} else {
				jQuery("#depth3_" + index2).css("display", "block");
			}
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
		repPrdState(prdId) {
			if (prdId == this.projectId) return true;
			else false;
		},
		chgProject(prdId) {
			VueCookies.set('project', prdId);
			jQuery('.consoleMenu').hide();
			jQuery('.box-shadow li').removeClass('active');
			jQuery(event.target).closest('li').addClass('active');
		}
	}
}
</script>

<style lang="scss">
</style>