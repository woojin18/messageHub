<template>
  <div style="width:800px">
    <div>
      <div class="modal-body">
        <div>
          <h2>공지사항</h2>
          <hr>
          <div class="boardWrite">
            <div class="title">
              <p>
                <span v-if="!$gfnCommonUtils.isEmpty(noticeData.noticeTypeCdName)" class="new2">{{noticeData.noticeTypeCdName}}</span>
                {{noticeData.title}}
              </p>
              <span class="date">{{noticeData.regDt}}</span>
            </div>
            <div class="content scroll-y">
              <p v-html="noticeData.content"></p>
            </div>
          </div>
        </div>
        <div class="quiryAgree">
          <input type="checkbox" id="chk_stop" class="checkStyle2" @click="fnStopPopup">
          <label for="chk_stop">오늘 그만 보기</label><br/>
        </div>
        <div class="text-center">
          <a href="#" @click.prevent="fnClose" class="btnStyle1 backWhite" title="닫기">닫기</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import customereApi from "@/modules/customer/service/customerApi.js";

export default {
  name: 'noticePopup',
  data() {
    return {
      noticeId: '',
      noticeData: {}
    }
  },
  created() {
    let urlParams = new URLSearchParams(window.location.search);
    let noticeId = urlParams.get('noticeId');
    if(!this.$gfnCommonUtils.isEmpty(noticeId)){
      this.noticeId = noticeId;
      this.fnSelectNoticeInfo();
    }
  },
  mounted(){
    this.fnSetCss();
  },
  methods: {
    fnSetCss(){
      document.body.style.minWidth = "auto";
      document.getElementById('publicLayout').style.width = "800px"
    },
    fnSelectNoticeInfo(){
      if(!this.$gfnCommonUtils.isEmpty(this.noticeId)){
        const vm = this;
        const params = {
          noticeId: this.noticeId
        };
        customereApi.selectNoticeList(params).then(response =>{
          const result = response.data;
          if(result.success) {
            result.data.forEach(function(obj){
              vm.noticeData = obj;
            });
          } else {
            console.error(result.message);
          }
        });
      }
    },
    fnIsEmptyObj(obj){
      if(typeof obj === 'undefined') return true;
      if(Object.keys(obj).length === 0) return true;
      return false;
    },
    fnStopPopup(){
      const cookieKey = 'exceptNoticeIds';
      let today = new Date();
      let tomorrow = new Date(today.getFullYear(), today.getMonth(), today.getDate()+1);

      let noticeIdArr = [];
      let exceptNoticeIds = this.$cookies.get(cookieKey);
      if(!this.$gfnCommonUtils.isEmpty(exceptNoticeIds)){
        noticeIdArr = exceptNoticeIds.split(',');
      }
      noticeIdArr.push(this.noticeId);
      noticeIdArr = noticeIdArr.filter(function(item, pos) {
        return noticeIdArr.indexOf(item) == pos;
      });

      this.$cookies.set(cookieKey, noticeIdArr.toString(), tomorrow.toGMTString());
      this.fnClose();
    },
    fnClose(){
      window.close();
    }
  }
}
</script>