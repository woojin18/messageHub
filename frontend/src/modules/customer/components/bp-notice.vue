<template>
  <div>
    TEST ===> {{noticeId}}
    <div class="btn_more">
      <hr/>
      제목 : 
      <span v-if="!$gfnCommonUtils.isEmpty(noticeData.noticeTypeCdName)" class="new2">{{noticeData.noticeTypeCdName}}</span>
      {{noticeData.title}}
      <hr/>
      내용 : 
      <p v-html="noticeData.content"></p>
      <hr/>
      등록일 : 
      <span class="date">{{noticeData.regDt}}</span>
      <hr/>

      <input type="checkbox" id="chk_stop" class="checkStyle2" @click="fnStopPopup">
      <label for="chk_stop">오늘 그만 보기</label><br/>
      <a href="#" @click.prevent="fnClose">닫기</a>
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
  methods: {
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