<template>
  <div v-if="directInputOpen" @click.self="fnClose" class="modalStyle" id="Direct-input" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:821px">
      <div class="modal-content">
        <div class="modal-body">
          <h5 class="lc-1">수신자 직접입력</h5>
          <hr>
          <div class="of_h">
            <p class="font-size14 color000 inline-block mt15">입력 후 선택 버튼을 누르면 수신자에 추가됩니다. </p>
            <div class="float-right">
              <a href="#self" class="btnStyle3 black font-size14 width120" title="수신자 추가">수신자 추가</a>
            </div>
          </div>
          
          <div class="of_h mt20">
            <div class="inline-block float-left text-center" style="width:31.5%; background:#505050"><p class="inline-block colorfff" style="padding:12px">* APP 로그인 ID</p></div>
            <div class="inline-block float-left text-center ml20" style="width:31.5%; background:#505050"><p class="inline-block colorfff" style="padding:12px">* 휴대폰번호</p></div>
            <div class="inline-block float-left text-center ml20" style="width:31.5%; background:#505050"><p class="inline-block colorfff" style="padding:12px">* 변수1</p></div>
          </div>

          <div class="of_h mt10">
            <input type="text" class="inputStyle float-left" style="width:31.5%" title="APP 로그인ID 입력란1">
            <input type="text" class="inputStyle float-left ml20" style="width:31.5%" title="휴대폰번호 입력란1">
            <input type="text" class="inputStyle float-left ml20" style="width:31.5%" title="변수1 입력란1">
          </div>
          <div class="of_h mt10">
            <input type="text" class="inputStyle float-left" style="width:31.5%" title="APP 로그인ID 입력란2">
            <input type="text" class="inputStyle float-left ml20" style="width:31.5%" title="휴대폰번호 입력란2">
            <input type="text" class="inputStyle float-left ml20" style="width:31.5%" title="변수1 입력란2">
          </div>
          <div class="of_h mt10">
            <input type="text" class="inputStyle float-left" style="width:31.5%" title="APP 로그인ID 입력란3">
            <input type="text" class="inputStyle float-left ml20" style="width:31.5%" title="휴대폰번호 입력란3">
            <input type="text" class="inputStyle float-left ml20" style="width:31.5%" title="변수1 입력란3">
          </div>
          <div class="text-center mt60">
            <a href="#self" class="btnStyle3 black font14" title="선택">선택</a>
            <a @click.self="fnClose" class="btnStyle3 white font14" data-dismiss="modal" title="닫기">닫기</a>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "directInputPopup",
  props: {
    directInputOpen: {
      type: Boolean,
      require: true,
      default: false,
    }
  },
  methods: {
    //팝업 닫기
    fnClose(){
      //입력 데이터 넘기기 필요
      this.$emit('update:directInputOpen', false)
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