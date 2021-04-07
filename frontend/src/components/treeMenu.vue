<template>
  <div>
    <nav id="sidebar">
      <!-- 메뉴바 접기 -->
      <a href="#self" class="sideMenu">
        <img src="../../public/se2/images/SideMenuOff.png" class="SideMenuOff">
        <img src="../../public/se2/images/SideMenuOn.png" class="SideMenuOn">
      </a>
      <!-- 메뉴바 접기 -->
      <ul>
        <!-- 중메뉴 -->
        <li class="on"><a href="/home"><i class="fas fa-tv-alt navIcon"></i><span>대시보드</span></a></li>
        <li v-for="(item, i) in treeData" :key="i" @click="fnOpenDepth2(i)">
          <a v-if="item.WEB_URL != ''" v-bind:href="item.WEB_URL">
            <i v-html="item.IMG_TAG"></i>
            <span>{{item.MENUS_NAME}}</span>
            <i class="far fa-chevron-down navArrow"></i>
          </a>
          <a v-if="item.WEB_URL == ''">
            <i v-html="item.IMG_TAG"></i>
            <span>{{item.MENUS_NAME}}</span>
            <i class="far fa-chevron-down navArrow"></i>
          </a>
          <!-- 소메뉴 -->
          <div class="depth2Lnb" :id="'depth2_' + i">
            <ul>
              <li v-for="(item2, j) in item.children" :key="j">
                <a v-bind:href="item2.WEB_URL">{{item2.MENUS_NAME}}</a>
              </li>
            </ul>
          </div>
          <!-- 소메뉴 -->
        </li>
        <!-- 중메뉴 -->
      </ul>

    </nav>
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
        "svc_type_cd" : 'AC'
      };
      
      api.getMenuForRole(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.treeData = result.data[0].children;
        }
      });
    },
    fnOpenDepth2(index){
      var depth2Sts = $("#depth2_" + index).css("display");
      if( depth2Sts === 'block' ){
        $("#depth2_" + index).css("display", "none");
      } else {
        $("#depth2_" + index).css("display", "block");
      }
      
    }
  }
}
</script>