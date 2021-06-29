<template>
	<div><input id="M_svcTypeCd" type="hidden" value="AC"><input id="M_roleCd" type="hidden"><input id="M_menusCd" type="hidden">
		<!-- sidebar -->
		<nav id="sidebar">
			<!-- 메뉴바 접기 -->
			<a href="#" class="sideMenu" title="사이드메뉴 축소/확대">
				<img src="../../public/se2/images/SideMenuOff.png" class="SideMenuOff" alt="사이드메뉴 축소">
				<img src="../../public/se2/images/SideMenuOn.png" class="SideMenuOn" alt="사이드메뉴 확대">
			</a>
			<!-- 메뉴바 접기 -->

			<!-- lnb -->
			<ul>
				<li class="on"><router-link to="/ac/home"><i class="fal fa-tachometer-alt-fastest navIcon"></i><span>대시보드</span></router-link></li>
				<li v-for="(item, i) in treeData" :key="i">
					<!--
					<a v-if="item.WEB_URL != ''" v-bind:href="item.WEB_URL">
						<i v-html="item.IMG_TAG"></i><span>{{item.MENUS_NAME}}</span><i class="far fa-chevron-down navArrow"></i>
					</a>
					<a v-if="item.WEB_URL == ''">
						<i v-html="item.IMG_TAG"></i><span>{{item.MENUS_NAME}}</span><i class="far fa-chevron-down navArrow"></i>
					</a>
					<div class="depth2Lnb" :id="'depth2_' + i">
						<ul>
							<li v-for="(item2, j) in item.children" :key="j">
								<a v-bind:href="item2.WEB_URL">{{item2.MENUS_NAME}}</a>
							</li>
						</ul>
					</div>
					-->
					<a @click="fnOpenDepth2(i)">
						<i v-bind:class="item.imgTag"></i><span>{{item.menusName}}</span><i class="far fa-chevron-down navArrow"></i>
					</a>
					<div class="depth2Lnb" :id="'depth2_' + i" :style="showOption">
						<ul>
							<li v-for="(item2, j) in item.children" :key="j" >
								<router-link  v-if="item2.webUrl != ''" v-bind:to="{path:item2.webUrl}" v-bind:id="'M_'+item2.menusCd" v-bind:r="item2.read" v-bind:w="item2.save">{{item2.menusName}}</router-link>		<!-- url 주소 있으면 페이지 이동 -->
								<a  v-if="item2.webUrl == ''" @click="fnOpenDepth3(i,j)">
									<i v-bind:class="item2.imgTag"></i><span>{{item2.menusName}}</span><i class="far fa-chevron-down navArrow" style="font-size: 10px;position: absolute;right: 20px"></i>
								</a>
								<!-- 소메뉴 -->
								<div class="depth2Lnb" :id="'depth2_' + j">
									<ul>
										<li v-for="(item3, m) in item2.children" :key="m">
											<router-link v-bind:to="{path:item3.webUrl}" v-bind:id="'M_'+item3.menusCd" v-bind:r="item3.read" v-bind:w="item3.save">{{item3.menusName}}</router-link>
										</li>
									</ul>
								</div>
								<!-- 소메뉴 -->
							</li>
						</ul>
					</div>
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
	name: 'treeMenu',
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
		this.fnMenuList();
	},
	methods: {
		fnMenuList() {
			var roleCd = tokenSvc.getToken().principal.role;
			jQuery('#M_roleCd').val(roleCd);
			var params = {
				"user_id"    : tokenSvc.getToken().principal.userId,
				"role_cd"    : roleCd,
				"svc_type_cd" : 'AC'
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
				jQuery(".depth2Lnb").css("display", "none");
				jQuery("#depth2_" + index).css("display", "block");
			}
		},
		fnOpenDepth3(index1, index2){
			jQuery("#depth2_" + index1).css("display", "block");
			var depth3Sts = jQuery("#depth3_" + index2).css("display");
			if (depth3Sts === 'block') {
				jQuery("#depth3_" + index2).css("display", "none");
			} else {
				jQuery("#depth3_" + index2).css("display", "block");
			}
		}
	}
}
</script>