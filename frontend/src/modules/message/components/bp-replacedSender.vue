<template>
  <div v-if="rplcSendOpen" @click.self="fnClose" class="modalStyle" id="send" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          
          <div class="of_h">
            <div class="float-left" style="width:32%"><h5>대체발송 발신번호</h5></div>
            <select v-model="fbInfo.callback" name="userConsole02_2" class="selectStyle2 float-right" style="width:66%">
              <option v-for="info in callbackList" :key="info.callback" :value="info.callback">{{info.callback}}</option>
            </select>
          </div>

          <div class="of_h consolMarginTop">
            <div class="float-left" style="width:32%"><h5>광고성메시지 수신거부번호</h5></div>
            <div class="float-right" style="width:66%">
              <input type="text" class="inputStyle" title="광고성메시지 수신거부번호 입력란" v-model="fbInfo.rcvblcNumber" maxlength="20">
            </div>
          </div>

          <div v-if="fbInfo.ch != 'SMS'" class="of_h consolMarginTop">
            <div class="float-left" style="width:32%"><h5>제목</h5></div>
            <div class="float-right" style="width:66%">
              <input type="text" class="inputStyle" title="제목 입력란" v-model="fbInfo.title">
            </div>
          </div>

          <div class="of_h consolMarginTop">
            <div class="float-left" style="width:32%"><h5>내용</h5></div>
            <div class="float-right" style="width:66%">
              <textarea class="textareaStyle height120" :placeholder="recvAreapPlaceholder" v-model="fbInfo.msg"></textarea>
            </div>
          </div>

          <div v-if="fbInfo.ch == 'MMS'" class="of_h consolMarginTop">
            <div class="float-left" style="width:32%"><h5>이미지</h5></div>
            <div class="of_h float-right" style="width:66%">
              <a @click="fnOpenImageManagePopUp" class="btnStyle1 backLightGray" title="메시지 내용 이미지선택">이미지선택</a>
              <ul class="float-right attachList" style="width:68%; padding:5px 15px; height:30px;">
                <li><a @click="fnDelImg">{{shortImgUrl}} <i v-if="!fnIsEmpty(fbInfo.imgUrl)" class="fal fa-times"></i></a></li>
              </ul>
            </div>
          </div>

          <div class="text-center mt20">
            <a @click="fnCallbackInputData" class="btnStyle1 backBlack" title="입력">입력</a>
            <a @click="fnClose" class="btnStyle1 backWhite" data-dismiss="modal" title="닫기">닫기</a>
          </div>

        </div>
      </div>
    </div>

    <ImageManagePopUp :imgMngOpen.sync="imgMngOpen" :useCh="useCh" ref="imgMngPopup"></ImageManagePopUp>

  </div>
</template>

<script>
import MessageApi from "@/modules/message/service/messageApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";
import ImageManagePopUp from "@/modules/commonUtil/components/bp-imageManage.vue";

export default {
  name: "pushContentsPopup",
  components : {
    ImageManagePopUp
  },
  props: {
    rplcSendOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '대체발송';
      }
    },
  },
  data() {
    return {
      imgMngOpen : false,
      useCh : 'PUSH',
      callbackList: [],
      fbInfo: {
        callback:'',
        ch:'',
        title:'',
        msg:'',
        rcvblcNumber:'',
        fileId:'',
        imgUrl:'',
      },
      shortImgUrl:'',
      recvAreapPlaceholder: '변수로 설정하고자 하는 내용을 {{ }}표시로 작성해 주십시오.\n:예) 이름과 출금일을 변수 설정:예) {{name}}님 {{yyyymmdd}} 출금 예정입니다.',
    }
  },
  watch: {
    rplcSendOpen(val){
      if(val) this.fnSelectCallbackList();
    }
  },
  methods: {
    fnDelImg(){
      this.shortImgUrl = '';
      this.fbInfo.imgUrl = '';
      this.fbInfo.fileId = '';
    },
    //대체발송 정보 Set
    fnSetfbInfo(fbInfo){
      this.fbInfo = Object.assign({}, fbInfo);
    },
    //입력정보 callback
    fnCallbackInputData(){
      if(!this.fbInfo.callback){
        confirm.fnAlert(this.componentsTitle, '대체발송 발신번호를 선택해주세요.');
        return;
      }
      if(!this.fbInfo.msg){
        confirm.fnAlert(this.componentsTitle, '내용을 입력해주세요.');
        return;
      }

      const msgLimitByte = (this.fbInfo.ch == 'SMS' ? 80 : 2000);
      const totalMsg = this.fbInfo.title + this.fbInfo.msg + '\n' + this.fbInfo.rcvblcNumber;
      const totByte = this.getByte(totalMsg);

      if(msgLimitByte < totByte){
        const alertMsg = (this.fbInfo.ch == 'SMS' ? '' : '제목 + ') + '내용 + 광고성메시지 수신거부번호가 '+msgLimitByte+'를 넘지 않아야됩니다.\n(현재 : '+totByte+'byte)';
        confirm.fnAlert(this.componentsTitle, alertMsg);
        return;
      }
      this.$parent.fnCallbackFbInfo(this.fbInfo);
      this.fnClose();
    },
    //발신번호 리스트 조회
    async fnSelectCallbackList(){
      var params = {
        'corpId':'TEST_CORP'
      };
      await MessageApi.selectCallbackList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          this.callbackList = result.data;
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      });
    },
    //팝업 닫기
    fnClose(){
      //입력 데이터 넘기기 필요
      this.$emit('update:rplcSendOpen', false)
    },
    //get 문자열 byte
    getByte(str) {
      return str
        .split('') 
        .map(s => s.charCodeAt(0))
        .reduce((prev, c) => (prev + ((c === 10) ? 2 : ((c >> 7) ? 2 : 1))), 0);
    },
    //문자열 byte cut
    sliceByByte(str, maxByte, i, b, c) {
      for(let b=i=0;b<maxByte;i++) {
        c=str.charCodeAt(i);
        b+=c>>7?2:1;
      }
      return str.substring(0,i);
    },
    fnOpenImageManagePopUp(){
      this.$refs.imgMngPopup.fnSearch();
      this.imgMngOpen = !this.imgMngOpen;
    },
    //이미지선택 callback
    fnSetImageInfo(imgInfo) {
      this.fbInfo.imgUrl = imgInfo.chImgUrl;
      this.fbInfo.fileId = imgInfo.fileId;
      this.shortImgUrl = this.fnSubString(imgInfo.chImgUrl, 0, 30);
    },
    fnSubString(str, sIdx, length){
      var shortStr = ''
      if(!this.fnIsEmpty(str)){
        shortStr = str.toString();
        if(shortStr.length > length){
          shortStr = shortStr.substring(sIdx, length) + '...  ';
        } else {
          shortStr = shortStr + '  ';
        }
      }
      return shortStr;
    },
    //빈값확인
    fnIsEmpty(str){
      if(str) return false;
      else return true
    }
  }
}
</script>
<style lang="scss">
$module: 'modalStyle';
.#{$module} {
  // This is modal bg
  background-color: rgba(0,0,0,.7);
  top: 0; right: 0; bottom: 0; left: 0;
  position: fixed;
  overflow: auto;
  margin: 0;
  z-index: 9999;
}
</style>