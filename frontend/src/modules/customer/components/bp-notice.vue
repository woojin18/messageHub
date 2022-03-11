<template>
  <div id="layerPopup1" class="popStyle">	
		<h3>공지사항</h3>
		<div class="popBox">
			<div class="popTitle">
        <span v-if="!$gfnCommonUtils.isEmpty(noticeData.noticeTypeCdName)" :class="noticeData.noticeType | getNotiTypeClass">{{noticeData.noticeTypeCdName}}</span>
        <span>{{noticeData.title | unescapeXss}}</span>
        <span class="popDate">{{noticeData.regDt}}</span>
			</div>
			<div class="popCont">
				<p v-html="noticeData.content"></p>
			</div>
		</div>
		<input name="chkbox1" id="chkbox1" @click="fnStopPopup" type="checkbox" class="checkStyle2"><label for="chkbox1">하루 동안 보지 않음</label>
		<div class="text-center">
			<a href="#" @click.prevent="fnClose" class="btnStyle3 white font14" title="닫기">닫기</a>						
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
      document.getElementById('publicLayout').style.width = "760px"
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