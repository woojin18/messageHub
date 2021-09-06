<template>
  <div v-if="addressInputOpen" @click.self="fnClose" class="modalStyle" id="Address" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:821px">
      <div class="modal-content">
        <div class="modal-body">
          <h2>수신 주소록 검색</h2>
          <hr>
          <div class="of_h">
            <select name="userConsole02_1" class="selectStyle2 mr5" style="width:28%" v-model="searchTextType">
              <option value="cuName">수신자 명</option>
              <option v-if="requiredCuid" value="cuid">APP 로그인ID</option>
              <option v-if="requiredCuPhone" value="hpNumber">휴대폰 번호</option>
            </select>
            <input type="text" class="inputStyle" style="width:60%" v-model="searchText" @keypress.enter="fnSearchAddrMem">
            <a @click='fnSearchAddrMem' class="btnStyle1 backLightGray float-right" style="width:10%" title="검색">검색</a>
          </div>
          <h5 class="color5">조직 조회 후 체크된 사용자를 선택하면  수신자에 추가됩니다.</h5>

          <div class="row row-no-margin of_h">
            <div class="border-line2 float-left" style="height:260px; overflow-y:scroll; width:34%">
              <div style="padding:25px">
              <!-- <div style="padding:25px"> -->
                <!-- addList -->
                <!-- <ul class="addList"> -->
                <addr-tree-menu v-for="(addrTreeData, idx) in addrTreeList" :key="idx"
                  :item="addrTreeData.addressName"
                  :id="addrTreeData.addressCategoryId"
                  :subItems="addrTreeData.subItems"
                ></addr-tree-menu>
                <!-- </ul> -->
                <!-- //addList -->
              </div>
            </div>
            <div class="float-right" style="padding-right:0; width:63%">
              <div class="tableScrollxy">
                <!-- table -->
                <table class="table_skin1">
                  <caption>수신자 추가의 사용자명, 아이디, 이용권한을 제공하는 표</caption>
                  <colgroup>
                  <col style="width:60px">
                  <col v-if="requiredCuid" style="width:100px">
                  <col v-if="requiredCuPhone" style="width:120px">
                  <col v-for="varNm in contsVarNms" :key="varNm" style="width:100px">
                  </colgroup>
                  <thead>
                  <tr>
                    <th class="text-center lc-1">
                      <div class="consolCheck ml10">
                        <input type="checkbox" id="listCheck_all" class="checkStyle2" @change="fnListChkAll" v-model="listAllChecked">
                        <label for="listCheck_all"></label>
                      </div>
                    </th>
                    <th class="text-center lc-1">수신자명</th>
                    <th v-if="requiredCuid" class="text-center lc-1">APP 로그인ID</th>
                    <th v-if="requiredCuPhone" class="text-center lc-1">휴대폰번호</th>
                    <th v-for="varNm in contsVarNms" :key="varNm" class="text-center lc-1">{{varNm}}</th>
                  </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(cmCuInfo, idx) in cmCuList" :key="cmCuInfo.cuInfoId">
                      <td class="text-center">
                        <div class="consolCheck ml10">
                          <input 
                            type="checkbox" 
                            :id="'listCheck_'+idx" 
                            class="checkStyle2" 
                            :value="cmCuInfo.cuInfoId" 
                            v-model="listChkBox" 
                            :disabled="(requiredCuid && $gfnCommonUtils.isEmpty(cmCuInfo.cuid)) || (requiredCuPhone && $gfnCommonUtils.isEmpty(cmCuInfo.hpNumber))"
                          >
                          <label :for="'listCheck_'+idx"></label>
                        </div>
                      </td>
                      <td class="text-center">{{cmCuInfo.cuName}}</td>
                      <td v-if="requiredCuid" class="text-left">{{cmCuInfo.cuid}}</td>
                      <td v-if="requiredCuPhone" class="text-left">{{cmCuInfo.hpNumber | phoneNumAddDash}}</td>
                      <td v-for="varNm in contsVarNms" :key="varNm" class="text-center">
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
              </div>

              <!-- pagination -->
              <PageLayer @fnClick="fnPageClick" :listTotalCnt="totCnt" :selected="listSize" pageDivClass="row mt10" ref="pageLayer"></PageLayer>
              <!-- //pagination -->
            </div>
          </div>

          <div class="text-center mt20">
            <a @click="fnSelectCu" class="btnStyle1 backBlack" title="선택">선택</a>
            <a @click="fnClose" class="btnStyle1 backWhite" data-dismiss="modal" title="닫기">닫기</a>
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
import PageLayer from "@/components/PageLayer.vue";

export default {
  name: "addressInputPopup",
  components : {
    AddrTreeMenu,
    PageLayer
  },
  props: {
    addressInputOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    componentsTitle: {
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
      listAllChecked: false,
      listChkBox: [],
      searchTextType: 'cuName',
      searchText: '',
      searchCategoryId: 0,
      addrTreeList: [],
      cmCuList: [],
      listSize : 5,  // 리스트 출력 개수
      pageNo : 1,  // 현재 페이징 위치
      totCnt : 0,  //전체 리스트 수
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
    fnSelectCu(){
      //유효성 검사
      if(this.listChkBox == null || this.listChkBox.length == 0){
        confirm.fnAlert(this.componentsTitle, '수신자를 선택해주세요.');
        return;
      }

      const vm = this;
      let recvInfoLst = [];
      let recvInfo = {};
      let sltCuInfo = {};
      let isValid = true;
      let inValidVarNm = '';

      this.listChkBox.forEach(function(v){
        sltCuInfo = vm.fnCmCuListGetRowById(v);
        recvInfo = {phone:'',cuid:'',mergeData:{}};

        if(vm.requiredCuid) recvInfo.cuid = sltCuInfo.cuid;
        else delete recvInfo.cuid;
        if(vm.requiredCuPhone) recvInfo.phone = sltCuInfo.hpNumber;
        else delete recvInfo.phone;

        vm.contsVarNms.forEach(function(key){
          if(vm.$gfnCommonUtils.isEmpty(sltCuInfo[key])){
            inValidVarNm = key;
            return false;
          } else {
            recvInfo.mergeData[key] = sltCuInfo[key];
          }
        });
        if(vm.$gfnCommonUtils.isEmpty(inValidVarNm) == false) return false;
        recvInfoLst.push(recvInfo);
      });
      if(this.$gfnCommonUtils.isEmpty(inValidVarNm) == false){
        confirm.fnAlert(this.componentsTitle, '선택항목의 '+inValidVarNm+'을 모두 입력해주세요.');
        return;
      }

      this.$parent.fnCallbackRecvInfoLst(recvInfoLst, 'Y');
      confirm.fnAlert(this.componentsTitle, '수신자를 추가하였습니다.');
      this.fnResetChkbox()
    },
    fnCmCuListGetRowById(id){
      let rtnObj = null;
      this.cmCuList.forEach(function(cmCuInfo){
        if(cmCuInfo.cuInfoId == id){
          rtnObj = Object.assign({}, cmCuInfo);
          return false;
        }
      });
      return rtnObj;
    },
    //주소 카테고리 구성원 조회
    fnSearchAddrCatgMem(){
      let params = {
        addressCategoryId: this.searchCategoryId,
        searchTextType: this.searchTextType,
        searchText: (this.searchTextType == 'hpNumber' ? this.searchText.replace(/[^0-9]/gi, '') : this.searchText),
        pageNo: this.pageNo,
        listSize: this.listSize
      };
      MessageApi.selectCmCuList(params).then(response =>{
        const result = response.data;
        this.fnResetChkbox();

        if(result.success) {
          this.cmCuList = Object.assign([], result.data);
          this.totCnt = result.pageInfo.totCnt;

          const vm = this;
          this.cmCuList.forEach(function(cmCuInfo){
            vm.contsVarNms.forEach(function(varNm){
              cmCuInfo[varNm] = '';
            });
          });
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    fnSearchAddrMem(){
      this.fnGetAddrList();
      this.fnAddrCatgMem(this.searchCategoryId);
    },
    //주소 카테고리 클릭
    fnAddrCatgMem(addressCategoryId){
      if(this.$gfnCommonUtils.isEmpty(addressCategoryId)){
        this.cmCuList = [];
        return;
      }
      this.searchCategoryId = addressCategoryId;
      this.fnSearch();
    },
    //주소목록 조회
    async fnGetAddrList(){
      let params = {
        searchTextType: this.searchTextType,
        searchText: (this.searchTextType == 'hpNumber' ? this.searchText.replace(/[^0-9]/gi, '') : this.searchText)
      };
      await MessageApi.selectAddressList(params).then(response =>{
        const result = response.data;
        if(result.success) {
          this.fnSetAddrListToTree(result.data);
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
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
        addrTreeObj.addressName = o.addressCategoryGrpName+'('+o.projectName+')'
        addrTreeList.push(Object.assign({}, addrTreeObj));
        if(addrTreeList.length != 0){
          vm.fnSetSubItems(addrCtgyList, addrTreeList[addrTreeList.length-1], 'Y');
        }
      });

      this.addrTreeList = [];
      addrTreeList.forEach(function(addrTreeInfo){
        if(addrTreeInfo.subItems.length != 0){
          vm.addrTreeList.push(addrTreeInfo);
        }
      });
      //this.addrTreeList = Object.assign([], addrTreeList);
    },
    fnSetSubItems(addrCtgyList, target, targetGrpYn){
      const vm = this;
      const tId = target.addressCategoryId;
      const tGrpId = target.addressCategoryGrpId;
      let ctgyInfo;

      if (!('subItems' in target)) target.subItems = [];

      for(let ctgyIdx=0; ctgyIdx<addrCtgyList.length; ctgyIdx++){
        ctgyInfo = addrCtgyList[ctgyIdx];
        if(targetGrpYn == 'Y' && ctgyInfo.parAddressCategoryId == 0){
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
    //리스트 전체 체크박스
    fnListChkAll(){
      var vm = this;
      if(this.listAllChecked){
        this.cmCuList.forEach(function(cmCuInfo){
          vm.listChkBox.push(cmCuInfo.cuInfoId);
        });
      } else {
        this.listChkBox = [];
      }
    },
    fnSearch() {
      this.$refs.pageLayer.fnAllDecrease();
      this.fnSearchAddrCatgMem();
    },
    fnPageClick(pageNo) {
      this.pageNo = pageNo;
      this.fnSearchAddrCatgMem();
    },
    fnResetChkbox(){
      this.listAllChecked = false;
      this.listChkBox = [];
    },
    fnClose(){
      this.searchTextType = 'cuName';
      this.searchText = '';
      this.searchCategoryId = '';
      this.addrTreeList = [];
      this.cmCuList = [];
      this.listSize = 5;
      this.pageNo = 1;
      this.totCnt = 0;
      this.fnResetChkbox();
      this.$emit('update:addressInputOpen', false)
    }
  }
}
</script>