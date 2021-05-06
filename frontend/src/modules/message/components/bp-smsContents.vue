<template>
  <div v-if="smsContsOpen" @click.self="fnClose" class="modalStyle" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          
          <div class="of_h consolMarginTop">
            <div class="float-left" style="width:34%"><h5>제목</h5></div>
            <div class="float-right" style="width:66%">
              <input type="text" class="inputStyle" title="제목 입력란">
            </div>
          </div>

          <div class="of_h consolMarginTop">
            <div class="float-left" style="width:34%"><h5>내용 *</h5></div>
            <div class="float-right" style="width:66%">
              <textarea class="textareaStyle height120" placeholder=""></textarea>
            </div>            
          </div>
          <div class="of_h consolMarginTop">
            <div class="float-left" style="width:34%"><h5>광고성메시지 수신거부번호</h5></div>
            <div class="float-right" style="width:66%">
              <input type="text" class="inputStyle" title="광고성메시지 수신거부번호 입력란">
            </div>
          </div>
          <div class="text-center mt20">
            <a href="#self" class="btnStyle2 backBlack" title="입력">입력</a>
            <a href="#self" class="btnStyle2 backWhite" data-dismiss="modal" title="닫기">닫기</a>            
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "smsContentsPopup",
  props: {
    smsContsOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return 'SMS 발송 내용';
      }
    },
  },
  methods: {
    //팝업 닫기
    fnClose(){
      //입력 데이터 넘기기 필요
      this.$emit('update:smsContsOpen', false)
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