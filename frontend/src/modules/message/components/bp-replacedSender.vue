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
          
          <!-- 2021-04-07 : G/W에서 이미지 발송 이슈로 주석처리
          <div v-if="fbInfo.ch == 'MMS'" class="of_h consolMarginTop">
            <div class="float-left" style="width:32%"><h5>이미지</h5></div>
            <div class="of_h float-right" style="width:66%">
              <a href="#self" class="btnStyle1 backLightGray" title="메시지 내용 이미지선택">이미지선택</a>
              <ul class="float-right attachList" style="width:68%; padding:5px 15px">
                <li><a href="#self">test_img.jpg <i class="fal fa-times"></i></a></li>
              </ul>
            </div>
          </div>
          -->

          <div class="text-center mt20">
            <a @click="fnCallbackInputData" class="btnStyle1 backBlack" title="입력">입력</a>
            <a @click="fnClose" class="btnStyle1 backWhite" data-dismiss="modal" title="닫기">닫기</a>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MessageApi from "@/modules/message/service/messageApi.js";
import confirm from "@/modules/commonUtil/service/confirm.js";

export default {
  name: "pushContentsPopup",
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
      callbackList: [],
      fbInfo: {
        callback:'',
        ch:'',
        title:'',
        msg:'',
        rcvblcNumber:''
      },
      recvAreapPlaceholder: '변수로 설정하고자 하는 내용을 {{ }}표시로 작성해 주십시오.\n:예) 이름과 출금일을 변수 설정:예) {{name}}님 {{yyyymmdd}} 출금 예정입니다.',
    }
  },
  watch: {
    rplcSendOpen(val){
      if(val) this.fnSelectCallbackList();
    }
  },
  methods: {
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