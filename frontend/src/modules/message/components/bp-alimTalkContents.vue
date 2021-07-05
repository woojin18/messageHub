<template>
  <div v-if="alimTalkContsOpen" @click.self="fnClose" class="modalStyle" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">

          <div v-if="emphasizeType == 'TEXT'" class="of_h consolMarginTop">
            <div class="float-left" style="width:20%"><h5>템플릿강조제목</h5></div>
            <div class="float-right" style="width:79%">
              <input type="text" class="inputStyle" title="제목 입력란" :value="tmpltEmpsTitle" disabled>
            </div>
          </div>

          <div v-if="emphasizeType == 'TEXT'" class="of_h consolMarginTop">
            <div class="float-left" style="width:20%"><h5>템플릿강조부제목</h5></div>
            <div class="float-right" style="width:79%">
              <input type="text" class="inputStyle" title="제목 입력란" :value="tmpltEmpsSubTitle" disabled>
            </div>
          </div>
          
          <div class="of_h consolMarginTop">
            <div class="float-left" style="width:20%"><h5>내용</h5></div>
            <div class="float-right" style="width:79%">
              <textarea class="textareaStyle height120" :value="tmpltContent" disabled></textarea>
            </div>
          </div>

          <div class="text-center mt20">
            <a @click="fnClose" class="btnStyle2 backWhite" title="닫기">닫기</a>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "alimTalkContentsPopup",
  props: {
    alimTalkContsOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    sendData : {
      type: Object,
      require: true,
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '알림톡 발송 내용';
      }
    },
  },
  data() {
    return {
      emphasizeType: '',
      tmpltEmpsTitle : '',
      tmpltEmpsSubTitle: '',
      tmpltContent: '',
    }
  },
  watch: {
    alimTalkContsOpen(val){
      if(val) this.fnSetAlimTalkInfo();
    }
  },
  methods: {
    fnSetAlimTalkInfo(){
      this.emphasizeType = this.$gfnCommonUtils.defaultIfEmpty(this.sendData.emphasizeType, 'NONE');
      this.tmpltEmpsTitle = this.$gfnCommonUtils.defaultIfEmpty(this.sendData.tmpltEmpsTitle, '');
      this.tmpltEmpsSubTitle = this.$gfnCommonUtils.defaultIfEmpty(this.sendData.tmpltEmpsSubTitle, '');
      this.tmpltContent = this.$gfnCommonUtils.defaultIfEmpty(this.sendData.tmpltContent, '');
    },
    //팝업 닫기
    fnClose(){
      this.$emit('update:alimTalkContsOpen', false)
    }
  }
}
</script>