<template>
  <div v-if="rplcSendOpen" @click.self="fnClose" class="modalStyle" id="send" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          
          <div class="of_h">
            <div class="float-left" style="width:32%"><h4 class="font-normal font-size14 mt15">대체발송 발신번호</h4></div>
            <select name="userConsole02_2" class="selectStyle3 height45 font-size14 float-right" style="width:66%">
              <option value="">선택하세요</option>
            </select>
          </div>

          <div class="of_h mt15">
            <div class="float-left" style="width:32%"><h4 class="font-normal font-size14 mt15">광고성메시지 수신거부번호</h4></div>
            <div class="float-right" style="width:66%">
              <input type="text" class="inputStyle" title="광고성메시지 수신거부번호 입력란">
            </div>
          </div>

          <div v-if="rplcSendType != 'SMS'" class="of_h mt15">
            <div class="float-left" style="width:32%"><h4 class="font-normal font-size14 mt15">제목</h4></div>
            <div class="float-right" style="width:66%">
              <input type="text" class="inputStyle" title="제목 입력란">
            </div>
          </div>

          <div class="of_h mt15">
            <div class="float-left" style="width:32%"><h4 class="font-normal font-size14 mt15">내용</h4></div>
            <div class="float-right" style="width:66%">
              <input type="text" class="inputStyle height120" title="내용 입력란">
            </div>
          </div>
          
          <div v-if="rplcSendType == 'MMS'" class="of_h mt15">
            <div class="float-left" style="width:32%"><h4 class="font-normal font-size14 mt15">이미지</h4></div>
            <div class="of_h float-right" style="width:66%">
              <a href="#self" class="btnStyle3_1 gray font-size13 minwidthAuto" style="width:28%" title="메시지 내용 이미지선택">이미지선택</a>
              <ul class="float-right attachList" style="width:68%; padding:11px 15px">
                <li><a href="#self">test_img.jpg <i class="fal fa-times"></i></a></li>
              </ul>
            </div>
          </div>

          <div class="text-center mt60">
            <a href="#self" class="btnStyle3 black font14" title="입력">입력</a>
            <a @click="fnClose" class="btnStyle3 white font14" data-dismiss="modal" title="닫기">닫기</a>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "pushContentsPopup",
  props: {
    rplcSendOpen: {
      type: Boolean,
      require: true,
      default: false,
    },
    rplcSendType: {
      type: String,
      require: true,
      default: function() {}
    },
  },
  methods: {
    //팝업 닫기
    fnClose(){
      //입력 데이터 넘기기 필요
      this.$emit('update:rplcSendOpen', false)
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