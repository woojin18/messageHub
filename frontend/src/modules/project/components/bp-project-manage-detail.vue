<template>
  <div  
    class="modal modalStyle" id="confirm"
    @click.self="fnClose"
    tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          <!-- 인풋 -->
          <div>
            <div>
              <h5 class="lc-1"> > 사용채널 선택</h5>
              <hr>
              <div class="mt15">
                <h4 class="font-normal inline-block width90">프로젝트 명 *</h4>
                <input id="project_name" name="project_name" class="inputStyle width291">
              </div>
              <div class="mt15">
                <h4 class="font-normal inline-block width90">프로젝트 설명</h4>
                <textarea></textarea>
              </div>
              <div class="mt15">
                <h4 class="font-normal inline-block width90">결제 유형 *</h4>
                <input type="radio">선불
                <input type="radio">후불
                * 프로젝트별 결제유형(선/후불)을 선택할 수 있으며, 프로젝트 등록 후 선택된 결제유형은 변경이 불가합니다.
              </div>
              <div class="mt15">
                <h4 class="font-normal inline-block width90">사용 여부 *</h4>
                <input type="radio">예
                <input type="radio">아니오
              </div>
            </div>
            <div>
                <h5 class="lc-1"> > 사용채널 선택</h5>
                <hr>
                <div class="mt15">
                  RCS
                  <input type="radio">사용
                  <input type="radio">미사용
                  <br>
                  SMS/MMS
                  <input type="radio">사용
                  <input type="radio">미사용
                  <br>
                  PUSH
                  <input type="radio">사용
                  <input type="radio">미사용
                  <br>
                  카카오톡
                  <input type="radio">사용
                  <input type="radio">미사용
                  <br>
                  MO
                  <input type="radio">사용
                  <input type="radio">미사용
                </div>
            </div>
        </div>
          <!-- 인풋 -->
          <!-- 버튼 -->
            <div class="text-center mt40">
                <button @click="fnSave">
                <a class="btnStyle3 black font14" data-toggle="modal">저장</a>
                </button>
                <button @click="fnDelete">
                <a class="btnStyle3 black font14" data-toggle="modal">삭제</a>
                </button>
                <button @click="$emit('update:visible', !visible)">
                <a href="#self" class="btnStyle3 white font14" data-dismiss="modal">닫기</a>
                </button>						
            </div>
          <!-- 버튼 -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
/* import MenuApi from "@/modules/system/service/MenuManageApi"; */

export default {
  name: 'MenuManagePopup',
  props: {
    visible: {
      type: Boolean,
      require: true,
      default: false
    },
    modal_title: {
      type: String,
      require: false,
    },
    save_status : {
      type: String,
      require: false,
    },
    rowData : {
      type: Object,
      require: false,
    },
    parRowData  : {
      type: Object,
      require: false,
    }
  },
  watch: {
    visible: function(newVal, oldVal) {
      if(newVal) {
        $("#confirm").modal("show");
      }
    }
  },
  mounted() {
  },
  methods: {
    // 닫기
    fnClose(){
      this.$emit('update:visible', false);
    },
    // 등록, 수정
    fnSave(){
      var parMenuRow = this.parRowData;
      var params = {
          /* "menus_cd"      : $("#menus_cd").val(),
          "menus_name"    : $("#menus_name").val(),
          "img_tag"      : $("#img_tag").val(),
          "top_menus_cd" : parMenuRow.MENUS_CD,
          "par_menus_cd" : parMenuRow.PAR_MENUS_CD,
          "menus_level"  : (parMenuRow.MENUS_LEVEL) + 1,
          "dis_order"    : $("#dis_order").val(),
          "svc_type_cd"  : $("#svc_type_cd").val(),
          "fixed_menus_yn"   : 1,
          "use_yn"       : $("#use_yn").val(),
          "web_url"      : $("#web_url").val(), */
          "sts" : this.save_status
      };

      /* MenuApi.saveMenuList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          alert("저장되었습니다.");
          // 창닫기
          this.$emit('update:visible', false);
          // 부모창 리스트 조회
          this.$parent.fnSearch();
        } else {
          alert("저장에 실패.");
        }
      }); */
    },
    // 삭제
    fnDelete(){
      var params = {
          "menus_cd" : $("#menus_cd").val(),
          "sts" : "D"
      };

      /* MenuApi.saveMenuList(params).then(response =>{
        var result = response.data;
        if(result.success) {
          alert("삭제되었습니다.");
          // 창닫기
          this.fnClose();
          // 부모창 리스트 조회
          this.$parent.fnSearch();
        } else {
          alert("저장에 실패.");
        }
      }); */
    }
  },
}
</script>