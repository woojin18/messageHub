<template>
	<div>
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
					<a href="#" title="비트큐브 하위메뉴 보기"><i class="fal fa-folder navIcon"></i><span>비트큐브<i class="fas fa-cog cog"></i> <i class="far fa-chevron-right navArrow"></i></span></a>
					<div class="consoleMenu">
						<ul class="box-shadow">
							<li v-for="(item, i) in prdData" :key="i" :class="{active : repPrdState(item.projectId)}"><a href="#">{{item.projectName}}</a></li>
						</ul>
					</div>
				</dt>
				<dd style="display:none"></dd>
			</dl>
			<!-- //consoleMenu -->

			<!-- lnb -->
			<ul>
				<li class="on"><a href="/ac/home"><i class="fal fa-tachometer-alt-fastest navIcon"></i><span>대시보드</span></a></li>
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
						<i v-html="item.IMG_TAG"></i><span>{{item.MENUS_NAME}}</span><i class="far fa-chevron-down navArrow"></i>
					</a>
					<div class="depth2Lnb" :id="'depth2_' + i" :style="showOption">
						<ul>
							<li v-for="(item2, j) in item.children" :key="j" >
								<a  v-if="item2.WEB_URL != ''" v-bind:href="item2.WEB_URL">{{item2.MENUS_NAME}}</a>		<!-- url 주소 있으면 페이지 이동 -->
								<a  v-if="item2.WEB_URL == ''" @click="fnOpenDepth3(i,j)">
									<i v-html="item2.IMG_TAG"></i><span>{{item2.MENUS_NAME}}</span><i class="far fa-chevron-down navArrow" style="font-size: 10px;position: absolute;right: 20px"></i>
								</a>
								<!-- 소메뉴 -->
								<div class="depth2Lnb" :id="'depth2_' + j">
									<ul>
										<li v-for="(item3, m) in item2.children" :key="m">
											<a v-bind:href="item3.WEB_URL">{{item3.MENUS_NAME}}</a>
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
		this.fnProjectList();
	},
	methods: {
		fnMenuList() {
			var params = {
				"user_id"    : tokenSvc.getToken().principal.userId,
				"role_cd"    : tokenSvc.getToken().principal.role,
				"svc_type_cd" : 'AC'
			};

			api.getMenuForRole(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.treeData = result.data[0].children;
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
		fnOpenDepth3(index1, index2){
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
		}
	}
}
</script>