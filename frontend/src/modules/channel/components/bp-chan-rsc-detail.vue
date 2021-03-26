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
                <h5 class="lc-1"> > 브랜드 등록</h5>
                <hr>
                <div class="mt15">
                <h4 class="font-normal inline-block width90">API Key *</h4>
                <!-- <input id="menus_cd" name="menus_cd" :value="rowData.MENUS_CD" class="inputStyle width291"> -->
                </div>
                <div class="mt15">
                <h4 class="font-normal inline-block width90">브랜드 명 *</h4>
                <!-- <input id="menus_name" name="menus_name" :value="rowData.MENUS_NAME" class="inputStyle width291"> -->
                </div>
                <div class="mt15">
                <h4 class="font-normal inline-block width90">브랜드 설명</h4>
                <textarea></textarea>
                <!-- <input id="img_tag" name="img_tag" :value="rowData.IMG_TAG" class="inputStyle width291"> -->
                </div>
                <div class="mt15">
                <h4 class="font-normal inline-block width90">타 프로젝트<br>사용여부</h4>
                <input type="radio">
                <!-- <input id="dis_order" name="dis_order" :value="rowData.DIS_ORDER" class="inputStyle width291"> -->
                </div>
                <div class="mt15">
                <h4 class="font-normal inline-block width90">백그라운드<br>이미지</h4>
                </div>
                
                <div class="mt15">
                <h4 class="font-normal inline-block width90">프로필<br>이미지</h4>
                </div>
                
                <div class="mt15">
                <h4 class="font-normal inline-block width90">메뉴버튼설정 *</h4>
                <input type="checkbox"/>
                - Web * <input >
                - Store * <input >
                </div>
                <div class="mt15">
                <h4 class="font-normal inline-block width90">카테고리1 *</h4>
                </div>
                
                <div class="mt15">
                <h4 class="font-normal inline-block width90">카테고리2 *</h4>
                </div>
                <div class="mt15">
                <h4 class="font-normal inline-block width90">카테고리3 *</h4>
                </div>
                <div class="mt15">
                <h4 class="font-normal inline-block width90">홈페이지 주소</h4>
                </div>
                <div class="mt15">
                <h4 class="font-normal inline-block width90">이메일</h4>
                <input> @ <input>
                </div>
                <div class="mt15">
                <h4 class="font-normal inline-block width90">주소</h4>
                </div>
            </div>
            <div>
                <h5 class="lc-1"> > 발신번호 등록</h5>
                * 웹 템플릿에서 등록한 브랜드와 승인 완료된 브랜드의 발신번호만 보여집니다.
                <hr>
                <div class="mt15">
                    <h4 class="font-normal inline-block width90">대표발신번호 *</h4>
                    <input type="checkbox"> 전화번호와 동일
                    <input class="inputStyle width291"> * 휴대폰번호 등록 불가
                </div>
                <div class="mt15">
                    <h4 class="font-normal inline-block width90">발신번호명 *</h4>
                    <input class="inputStyle width291" placeholder="RCS메시지 발송 시 고객에게 표시되는 이름으로 신중하게 작성해 주세요.">
                </div>
                <div class="mt15">
                    <h4 class="font-normal inline-block width90">대표번호 <br>문자수신서비스*</h4>
                    <input type="checkbox"> 사용중
                    <br>
                    대표번호 문자수신(MO)서비스를 현재 사용하고 계실 경우 반드시 해당 정보를 체크해주셔야 합니다.
                </div>
                <div class="mt15">
                    <h4 class="font-normal inline-block width90">추가발신번호</h4>
                    <input class="inputStyle width291">
                    <br>
                    추가발신번호가 있는경우 + 버튼을 클릭하세요. 10개까지 미사용등록 가능합니다.
                    <br>
                    최대열개 +
                </div>
                <div class="mt15">
                    <h4 class="font-normal inline-block width90">통신서비스<br>가입증명원 *</h4>
                    <input type="file"> 파일형식 : jpg, png, pdf, tiff, zip (최대 50MB)
                    <input type="checkbox"> 메시지 발송 시, 기본 템플릿(구.free템플릿)은 정보성에 한하여 이용가능합니다. 규정 위반시 서비스 이용에 제한될 수 있습니다,
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