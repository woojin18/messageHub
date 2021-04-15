<template>
  <div v-if="addressInputOpen" @click.self="fnClose" class="modalStyle" id="Address" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:821px">
      <div class="modal-content">
        <div class="modal-body">
          <h2>수신 주소록 검색</h2>
          <hr>
          <div class="of_h">
            <select name="userConsole02_1" class="selectStyle2" style="width:28%" v-model="searchTextType">
              <option value="cuid">APP_ID</option>
              <option value="cuName">수신자 명</option>
              <option value="hpNumber">휴대폰 번호</option>
            </select>
            <input type="text" class="inputStyle" style="width:60%" v-model="searchText">
            <a @click='fnGetAddrList' class="btnStyle backLightGray float-right" style="width:10%" title="검색">검색</a>
          </div>
          <h5 class="color5">조직 조회 후 체크된 사용자를 선택하면  수신자에 추가됩니다.</h5>
          
          <div class="row row-no-margin of_h">
            <div class="border-line2 float-left" style="height:195px; width:34%">
              <div  style="padding:5px">
              <!-- <div style="padding:25px"> -->
                <!-- addList -->
                <!-- <ul class="addList"> -->
                <addr-tree-menu v-for="addrTreeData in addrTreeList" :key="addrTreeData.loopKey" 
                  :item="addrTreeData.addressName" 
                  :id="addrTreeData.addressCategoryId" 
                  :subItems="addrTreeData.subItems"
                ></addr-tree-menu>
                <!-- </ul> -->
                <!-- //addList -->
              </div>
            </div>
            <div class="float-right" style="padding-right:0; width:63%">
              <!-- table -->
              <table class="table_skin1">
                <caption>수신자 추가의 사용자명, 아이디, 이용권한을 제공하는 표</caption>
                <colgroup>
                <col style="width:5%">
                <col style="width:18%">
                <col style="width:25%">
                <col>
                <col style="width:16%">
                </colgroup>
                <thead>
                <tr>
                  <th class="text-center lc-1"><div class="consolCheck ml10"><input type="checkbox" id="check6" class="checkStyle2" value="check6"><label for="check6"></label></div></th>
                  <th class="text-center lc-1">수신자명</th>
                  <th v-if="requiredCuid" class="text-center lc-1">APP 로그인ID</th>
                  <th v-if="requiredCuPhone" class="text-center lc-1">휴대폰번호</th>
                  <th v-for="(varNm, idx) in contsVarNms" :key="varNm" :class="idx!=Object.keys(contsVarNms).length-1 ? 'text-center lc-1' : 'text-center lc-1 end'">{{varNm}}</th>
                </tr>
                </thead>
                <tbody>
                  <tr v-for="cmCuInfo in cmCuList" :key="cmCuInfo.cuInfoId">
                    <td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check7" class="checkStyle2" value="check17"><label for="check7"></label></div></td>
                    <td class="text-center">{{cmCuInfo.cuName}}</td>
                    <td v-if="requiredCuid" class="text-left">{{cmCuInfo.cuid}}</td>
                    <td v-if="requiredCuPhone" class="text-left">{{cmCuInfo.hpNumber}}</td>
                    <td v-for="(varNm, idx) in contsVarNms" :key="varNm" :class="idx!=Object.keys(contsVarNms).length-1 ? 'text-center' : 'text-center end'">
                      <input type="text" class="inputStyle" v-model="cmCuInfo[varNm]">
                    </td>
                  </tr>

                  <tr v-if="Object.keys(cmCuList).length == 0">
                    <td class="text-center"><div class="consolCheck ml10"><input type="checkbox" id="check7" class="checkStyle2" value="check17"><label for="check7"></label></div></td>
                    <td class="text-center" :colspan="1+Object.keys(contsVarNms).length+(requiredCuid?1:0)+(requiredCuPhone?1:0)">검색된 내용이 없습니다.</td>
                  </tr>
                  
                </tbody>
              </table>
              <!-- //table -->

              <!-- pagination -->
              <div class="row mt10">
                <div class="col-xs-12">
                  <div class="pagination1 text-center">
                    <a href="#" title="10페이지 이전 페이지로 이동"><i class="far fa-chevron-double-left"></i></a>
                    <a href="#" title="이전 페이지로 이동"><i class="far fa-chevron-left"></i></a>
                    <a href="#" title="1페이지로 이동" class="number active">1</a>
                    <a href="#" title="2페이지로 이동" class="number">2</a>
                    <a href="#" title="3페이지로 이동" class="number">3</a>
                    <a href="#" title="4페이지로 이동" class="number">4</a>
                    <a href="#" title="5페이지로 이동" class="number">5</a>
                    <a href="#" title="다음 페이지로 이동"><i class="far fa-chevron-right"></i></a>
                    <a href="#" title="10페이지 다음 페이지로 이동"><i class="far fa-chevron-double-right"></i></a>
                  </div>
                </div>
              </div>
              <!-- //pagination -->
            </div>
          </div>

          <div class="text-center mt10">
            <a @click="fnSelectCu" class="btnStyle backBlack" title="선택">선택</a>
            <a @click="fnClose" class="btnStyle backWhite" data-dismiss="modal" title="닫기">닫기</a>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MessageApi from "@/modules/message/service/messageApi.js";
import AddrTreeMenu from "@/modules/message/components/bc-addressTree.vue";
import confirm from "@/modules/commonUtil/service/confirm.js"

export default {
  name: "addressInputPopup",
  components : {
    AddrTreeMenu
  },
  props: {
    addressInputOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    templateTitle: {
      type: String,
      require: false,
      default: function() {
        return '주소록 검색';
      }
    },
    requiredCuPhone: {
      type: Boolean,
      require: true,
      default: true,
    },
    requiredCuid: {
      type: Boolean,
      require: true,
      default: true,
    },
    contsVarNms : {
      type: Array,
      require: true
    },
  },
  data() {
    return {
      searchTextType: 'cuid',
      searchText: '',
      addrTreeList: [],
      cmCuList: []
    }
  },
  watch: {
    addressInputOpen(val){
      if(val){
        this.fnGetAddrList();
      } 
    }
  },
  methods: {
    //작업중
    fnSelectCu(){
      console.log(this.cmCuList);
    },
    //주소 카테고리 구성원 조회
    fnAddrCatgMem(addressCategoryId){
      if(this.$gfnCommonUtils.isEmpty(addressCategoryId)){
        this.cmCuList = [];
        return;
      }
      let params = {
        addressCategoryId:addressCategoryId
      };
      MessageApi.selectCmCuList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          this.cmCuList = Object.assign([], result.data);
          this.cmCuList.forEach(function(cmCuInfo){
            this.contsVarNms.forEach(function(varNm){
              cmCuInfo[varNm] = '';
            });
          });
        } else {
          confirm.fnAlert(this.templateTitle, result.message);
        }
      });
    },
    //주소목록 조회
    async fnGetAddrList(){
      let params = {
        searchTextType: this.searchTextType,
        searchText: this.searchText,
        corpId:'TEST_CORP',
        projectId:'TEST_PROJECT'
      };
      await MessageApi.selectAddressList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          this.fnSetAddrListToTree(result.data);
        } else {
          confirm.fnAlert(this.templateTitle, result.message);
        }
      });
    },
    //주소목록을 트리구조로 변경
    fnSetAddrListToTree(addrList){
      const vm = this;
      const addrGrpList = Object.assign([], addrList.addrGrpList);
      let addrCtgyList = Object.assign([], addrList.addrCtgyList);
      let addrTreeList = [];
      let addrTreeObj = {};

      //주소록 그룹 put
      addrGrpList.forEach(function(o){
        addrTreeObj = Object.assign({}, o);
        addrTreeList.push(Object.assign({}, addrTreeObj));
        if(addrTreeList.length != 0){
          vm.fnSetSubItems(addrCtgyList, addrTreeList[addrTreeList.length-1], 'Y');
        } 
      });

      this.addrTreeList = Object.assign([], addrTreeList);
      this.addrTreeList.forEach(function(addrTreeInfo, idx){
        if(addrTreeInfo.subItems.length == 0){
          vm.addrTreeList.splice(idx, 1);
        }
      });
    },
    fnSetSubItems(addrCtgyList, target, targetGrpYn){
      const vm = this;
      const tId = target.addressCategoryId;
      const tGrpId = target.addressCategoryGrpId;
      const loopList = Object.assign({}, addrCtgyList)
      let ctgyInfo;
      
      if (!('subItems' in target)) target.subItems = [];

      for(let ctgyIdx=0; ctgyIdx<addrCtgyList.length; ctgyIdx++){
        ctgyInfo = addrCtgyList[ctgyIdx];
        if(targetGrpYn == 'Y' 
          && (ctgyInfo.parAddressCategoryId == 0 || ctgyInfo.parAddressCategoryId == ctgyInfo.addressCategoryGrpId)){
          if(tGrpId == ctgyInfo.addressCategoryGrpId){
            target.subItems.push(ctgyInfo);
            if(target.subItems.length != 0) vm.fnSetSubItems(addrCtgyList, target.subItems[target.subItems.length - 1], 'N');
          }
        } else {
          if(tId == ctgyInfo.parAddressCategoryId){
            target.subItems.push(ctgyInfo);
            if(target.subItems.length != 0) vm.fnSetSubItems(addrCtgyList, target.subItems[target.subItems.length - 1], 'N');
          }
        }
      }
    },
    fnClose(){
      this.addrTreeList = [];
      this.cmCuList = [];
      this.$emit('update:addressInputOpen', false)
    }
  }
}
</script>