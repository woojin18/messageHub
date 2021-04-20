<template>
		<!-- sidebar -->
		<nav id="sidebar">
			<!-- 메뉴바 접기 -->
			<a href="#self" class="sideMenu" title="사이드메뉴 축소/확대">
				<img src="../../public/se2/images/SideMenuOff.png" class="SideMenuOff" alt="사이드메뉴 축소">
				<img src="../../public/se2/images/SideMenuOn.png" class="SideMenuOn" alt="사이드메뉴 확대">
			</a>
			<!-- 메뉴바 접기 -->

			<!-- consoleMenu -->
			<dl>
				<dt>
					<a href="#slef" title="비트큐브 하위메뉴 보기"><i class="fal fa-folder navIcon"></i><span>비트큐브<i class="fas fa-cog cog"></i> <i class="far fa-chevron-right navArrow"></i></span></a>
					<div class="consoleMenu">
						<ul class="box-shadow">
							<li><a href="#self" title="엘지 유플러스 페이지로 이동">엘지 유플러스</a></li>
							<li><a href="#self" title="통합 메시징 클라우드 페이지로 이동">통합 메시징 클라우드</a></li>
							<li><a href="#self" title="이커머스테크 페이지로 이동">이커머스테크</a></li>
							<li class="active"><a href="#self" title="비트큐브 페이지로 이동">비트큐브</a></li>
							<li><a href="#self" title="아이디알인터랙티브 페이지로 이동">아이디알인터랙티브</a></li>
						</ul>
					</div>
				</dt>
				<dd style="display:none"></dd>
			</dl>
			<!-- //consoleMenu -->

			<!-- lnb -->
			<ul>
				<li class="on"><a href="/uc/home"><i class="fal fa-tachometer-alt-fastest navIcon"></i><span>대시보드</span></a></li>
				<li v-for="(item, i) in treeData" :key="i" @click="fnOpenDepth2(i)">
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
					<a>
						<i v-html="item.IMG_TAG"></i><span>{{item.MENUS_NAME}}</span><i class="far fa-chevron-down navArrow"></i>
					</a>
					<div class="depth2Lnb" :id="'depth2_' + i">
						<ul>
							<li v-for="(item2, j) in item.children" :key="j">
								<a v-bind:href="item2.WEB_URL">{{item2.MENUS_NAME}}</a>
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
import api from "@/modules/login/service/api";
import tokenSvc from '@/common/token-service';

export default {
	name: 'TreeMenu',
	props: {
	},
	data() {
		return {
			treeData :  [
				{name: '',
					children: [
						{name: '', urlPath: ''}
					]
				}
			]
		}
	},
	mounted(){
		this.fnMenuList();
	},
	methods: {
		fnMenuList(){
			var params = {
				"user_id"    : tokenSvc.getToken().principal.userId,
				//"role_cd"    : tokenSvc.getToken().principal.role
				"role_cd"    : 'ADM_CD',
				"svc_type_cd" : 'UC'
			};

			api.getMenuForRole(params).then(response =>{
				var result = response.data;
				if (result.success) {
					this.treeData = result.data[0].children;
				}
			});
		},
		fnOpenDepth2(index){
			var depth2Sts = $("#depth2_" + index).css("display");
			if (depth2Sts === 'block') {
				$("#depth2_" + index).css("display", "none");
			} else {
				$("#depth2_" + index).css("display", "block");
			}
		}
	}
}
</script>