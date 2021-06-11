<template>
  <div class="modal fade modalStyle" id="Inquiry" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          <div>
            <h2 class="color000 font-size18">1:1 문의</h2>
            <hr>
            <div class="of_h">
              <h5 class="inline-block" style="width:20%">이름입력</h5>
              <select name="admin0201_4" class="selectStyle2 float-right" style="width:80%" title="이름 선택란">
                <option value="">이름 입력</option>
              </select>
            </div>
            <div class="of_h consolMarginTop">
              <h5 class="inline-block" style="width:20%">휴대폰 번호</h5>
              <input type="text" class="inputStyle float-right" style="width:80%" title="휴대폰 번호 입력란">
            </div>
            <div class="of_h consolMarginTop">
              <h5 class="inline-block" style="width:20%">E-mail</h5>
              <div class="float-right" style="width:80%">
                <input type="text" class="inputStyle vertical-middle" style="width:24%" title="이메일 입력란"><span class="ml10 mr10">@</span>
                <input type="text" class="inputStyle" style="width:24%" title="직접입력">
                <select class="selectStyle2 float-right" style="width:40%" title="메일 선택란">
                <option value="">선택하세요</option>
              </select>
              </div>
            </div>
            <div class="of_h consolMarginTop">
              <h5 class="inline-block" style="width:20%">문의종류</h5>
              <select class="selectStyle2 float-right" style="width:80%" title="문의종류 선택란">
                <option value="">선택</option>
              </select>
            </div>
            <div class="of_h consolMarginTop">
              <h5 class="inline-block" style="width:20%">제목</h5>
              <select class="selectStyle2 float-right" style="width:80%" title="제목 선택란">
                <option value="">제목 입력</option>
              </select>
            </div>
            <div class="of_h consolMarginTop">
              <h5 class="inline-block" style="width:20%">내용</h5>
              <div class="float-right" style="width:80%">
                <textarea class="textareaStyle height120" placeholder=""></textarea>
              </div>
            </div>
            
          </div>
          <div class="text-center mt20">
            <a href="#self" class="btnStyle2 backBlack" data-toggle="modal" title="문의신청">문의신청</a>
          </div>
        </div>
      </div>  
      <div class="PopupBtn_Wrap">
        <a href="#self" class="PopupBtn" data-dismiss="modal">
          <img src="@/assets/images/common/btn_close.png" alt="팝업 닫기" class="PopupClose">
        </a>
      </div>
    </div>
    
  </div>
</template>

<script>
export default {
  name: "inquiryPopup"
}
</script>