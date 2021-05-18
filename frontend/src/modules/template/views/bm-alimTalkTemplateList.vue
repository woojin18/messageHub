<template>
  <div>

    <div class="contentHeader">
      <h2>알림톡 템플릿 관리</h2>
      <!-- <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="알림톡 템플릿 관리 이용안내">이용안내 <i class="fal fa-book-open"></i></a> -->
    </div>

    <div class="row">
      <div class="col-xs-12">
        <div class="menuBox">
          <div class="of_h">
            <div class="inline-block" style="width:8%"><h4>검색조건</h4></div>
            <div class="inline-block" style="width:91%">
              <select name="userConsole_sub040401_1" class="selectStyle2" style="width:15%" title="검색조건 선택란">
                <option value="">템플릿명</option>
                <option value="">템플릿명</option>
              </select>
              <input type="text" class="inputStyle vertical-top ml10" style="width:37.5%" title="검색조건 입력란">
            </div>
          </div>

          <div class="of_h consolMarginTop">
            <div class="inline-block" style="width:8%"><h4>등록일자</h4></div>
            <div class="inline-block" style="width:91%">
              <input type="text" class="datepicker inputStyle maxWidth200" title="시작날짜 입력란"><span style="padding:0 11px">~</span><input type="text" class="datepicker inputStyle maxWidth200" title="마지막날짜 입력란">
              <ul class="tab_s2 ml20">
                <li><a href="#" title="오늘 날짜 등록일자 검색">오늘</a></li>
                <li class="active"><a href="#" title="1주일 등록일자 검색">1주일</a></li>
                <li><a href="#" title="15일 등록일자 검색">15일</a></li>
                <li><a href="#" title="1개월 등록일자 검색">1개월</a></li>
              </ul>      
            </div>
          </div>

          <div class="of_h consolMarginTop">
            <div class="inline-block" style="width:16%"><h4>타 프로젝트 사용여부</h4></div>
            <div class="inline-block" style="width:83%">
              <div class="consolCheck vertical-top">
                <input type="checkbox" id="otherUse_all" class="checkStyle2" value="전체" checked><label for="otherUse_all" class="mr30">전체</label>
              </div>
              <div class="consolCheck vertical-top">
                <input type="checkbox" id="otherUse1" class="checkStyle2" value="등록완료"><label for="otherUse1" class="mr30">등록완료</label>
                <input type="checkbox" id="otherUse2" class="checkStyle2" value="검수 중"><label for="otherUse2" class="mr30">검수 중</label>
                <input type="checkbox" id="otherUse3" class="checkStyle2" value="검수"><label for="otherUse3" class="mr30">검수</label>
                <input type="checkbox" id="otherUse4" class="checkStyle2" value="부결"><label for="otherUse4" class="mr30">부결</label>
                <input type="checkbox" id="otherUse5" class="checkStyle2" value="검수완료"><label for="otherUse5" class="mr30">검수완료</label>
                <input type="checkbox" id="otherUse6" class="checkStyle2" value="테스트 발송가능"><label for="otherUse6" class="mr30">테스트 발송가능</label>
                <input type="checkbox" id="otherUse7" class="checkStyle2" value="사용요청 중"><label for="otherUse7">사용요청 중</label><br>
                <input type="checkbox" id="otherUse8" class="checkStyle2" value="템플릿 운영이관 요청"><label for="otherUse8" class="mr30">템플릿 운영이관 요청</label>
                <input type="checkbox" id="otherUse9" class="checkStyle2" value="승인완료"><label for="otherUse9" class="mr30">승인완료</label>
                <input type="checkbox" id="otherUse10" class="checkStyle2" value="운영이관 완료"><label for="otherUse10">운영이관 완료</label>
              </div>
              <a href="#self" class="btnStyle1 float-right" title="통합발송 검색">검색</a>  
            </div>
          </div>
        </div>
      </div>        
    </div>
    

    <div class="row">
        <div class="col-xs-12 consolMarginTop">
          <div class="of_h inline">
            <div class="float-right">
              <a href="#self" class="btnStyle2 backBlack" title="템플릿 등록">템플릿 등록</a>
              <a href="#self" class="btnStyle2 borderGray ml10" title="삭제">삭제</a>
              <a href="#self" class="btnStyle2 borderGray ml10" title="엑셀 다운로드">엑셀 다운로드 <i class="fal fa-arrow-to-bottom"></i></a>
            </div>
          </div>
          <!-- 15개씩 보기 -->
          <div class="of_h inline">
            <div class="float-left">전체 : <span class="color1"><strong>20</strong></span>건
              <select name="userConsole_sub040401_2" class="selectStyle2 width120 ml20">
                <option value="">15개씩 보기</option>
                <option value="">30개씩 보기</option>
              </select>
            </div>
          </div>
          <!-- //15개씩 보기 -->
          
          <div class="row">
            <div class="col-xs-12 consolMarginTop">
              <!-- table -->
              <table class="table_skin1 bt-000 tbl-striped">
                <colgroup>
                  <col style="width:5%">
                  <col style="width:5%">
                  <col>
                  <col style="width:10%">
                  <col style="width:10%">
                  <col style="width:10%">
                  <col style="width:20%">
                  <col style="width:8%">
                  <col style="width:10%">
                  <col style="width:11%">
                </colgroup>
                <thead>
                  <tr>
                  <th class="text-center lc-1"><input type="checkbox" id="listCheck_all" class="boardCheckStyle" value="listCheck_all"><label for="listCheck_all"></label></th>
                  <th class="text-center lc-1">No.</th>
                  <th class="text-center lc-1">템플릿 ID</th>
                  <th class="text-center lc-1">템플릿명</th>
                  <th class="text-center lc-1">상태</th>
                  <th class="text-center lc-1">플러스친구ID</th>
                  <th class="text-center lc-1">최종수정일자</th>
                  <th class="text-center lc-1">변경이력</th>
                  <th class="text-center lc-1 end" colspan="2"></th>
                  </tr>
                </thead> 
                <tbody>
                  <tr>
                    <td class="text-center"><input type="checkbox" id="listCheck5" class="boardCheckStyle" value="check5"><label for="listCheck5"></label></td>
                    <td class="text-center">5</td>
                    <td class="text-center">
                      <router-link :to="{ name: 'alimTalkTemplateManage'}">TP202102010001</router-link>
                    </td>
                    <td class="text-center">푸시 템플릿1</td>
                    <td class="text-center">등록완료</td>
                    <td class="text-center">@LG유플러스</td>
                    <td class="text-center">2021.02.08 00:00:00</td>
                    <td class="text-center"><a href="#self" class="btnStyle1 backLightGray" data-toggle="modal" data-target="#stateMore" title="상세">상세</a></td>
                    <td class="text-center end"></td>
                    <td class="text-center end"></td>
                  </tr>  
                  <tr>
                    <td class="text-center"><input type="checkbox" id="listCheck4" class="boardCheckStyle" value="check4"><label for="listCheck4"></label></td>
                    <td class="text-center">4</td>
                    <td class="text-center">
                      <router-link :to="{ name: 'alimTalkTemplateManage'}">TP202102010001</router-link>
                    </td>
                    <td class="text-center">푸시 이미지 템플릿1</td>
                    <td class="text-center">검수중</td>
                    <td class="text-center">@LG유플러스</td>
                    <td class="text-center">2021.02.08 00:00:00</td>
                    <td class="text-center"><a href="#self" class="btnStyle1 backLightGray" data-toggle="modal" data-target="#stateMore" title="상세">상세</a></td>
                    <td class="text-center end"></td>
                    <td class="text-center end"><a href="#self" class="btnStyle1 backLightGray" title="테스트발송">테스트발송</a></td>
                  </tr>  
                  <tr>
                    <td class="text-center"><input type="checkbox" id="listCheck3" class="boardCheckStyle" value="check3"><label for="listCheck3"></label></td>
                    <td class="text-center">3</td>
                    <td class="text-center">
                      <router-link :to="{ name: 'alimTalkTemplateManage'}">TP202102010001</router-link>
                    </td>
                    <td class="text-center">푸시 템플릿1</td>
                    <td class="text-center">등록완료</td>
                    <td class="text-center">@LG유플러스</td>
                    <td class="text-center">2021.02.08 00:00:00</td>
                    <td class="text-center"><a href="#self" class="btnStyle1 backLightGray" data-toggle="modal" data-target="#stateMore" title="상세">상세</a></td>
                    <td class="text-center end"><a href="#self" class="btnStyle1 backLightGray" title="운영이관">운영이관</a></td>
                    <td class="text-center end"><a href="#self" class="btnStyle1 backLightGray" title="테스트발송">테스트발송</a></td>
                  </tr>  
                  <tr>
                    <td class="text-center"><input type="checkbox" id="listCheck2" class="boardCheckStyle" value="check2"><label for="listCheck2"></label></td>
                    <td class="text-center">2</td>
                    <td class="text-center">TP202102010001</td>
                    <td class="text-center">푸시 이미지 템플릿1</td>
                    <td class="text-center">검수중</td>
                    <td class="text-center">@LG유플러스</td>
                    <td class="text-center">2021.02.08 00:00:00</td>
                    <td class="text-center"><a href="#self" class="btnStyle1 backLightGray" data-toggle="modal" data-target="#stateMore" title="상세">상세</a></td>
                    <td class="text-center end"><a href="#self" class="btnStyle1 backLightGray" title="운영이관">운영이관</a></td>
                    <td class="text-center end"></td>
                  </tr>  
                  <tr>
                    <td class="text-center"><input type="checkbox" id="listCheck1" class="boardCheckStyle" value="check1"><label for="listCheck1"></label></td>
                    <td class="text-center">1</td>
                    <td class="text-center">
                      <router-link :to="{ name: 'alimTalkTemplateManage'}">TP202102010001</router-link>
                    </td>
                    <td class="text-center">푸시 이미지 템플릿1</td>
                    <td class="text-center">등록완료</td>
                    <td class="text-center">@LG유플러스</td>
                    <td class="text-center">2021.02.08 00:00:00</td>
                    <td class="text-center"><a href="#self" class="btnStyle1 backLightGray" data-toggle="modal" data-target="#stateMore" title="상세">상세</a></td>
                    <td class="text-center end"><a href="#self" class="btnStyle1 backLightGray" title="운영이관">운영이관</a></td>
                    <td class="text-center end"></td>
                  </tr>  
                </tbody>
              </table>
              <!-- //table -->
            </div>      
          </div>
        </div>      
      </div>

      <!-- pagination -->
      <div class="row mt40">
        <div class="col-xs-12">
          <div class="pagination1 text-center">
            <a href="#" title="10페이지 이전 페이지로 이동"><i class="far fa-chevron-double-left"></i></a>
            <a href="#" title="이전 페이지로 이동"><i class="far fa-chevron-left"></i></a>
            <a href="#" title="1페이지로 이동" class="number active">1</a>
            <a href="#" title="2페이지로 이동" class="number">2</a>
            <a href="#" title="3페이지로 이동" class="number">3</a>
            <a href="#" title="4페이지로 이동" class="number">4</a>
            <a href="#" title="5페이지로 이동" class="number">5</a>
            <a href="#" title="다음 페이지로 이동"><i class="far fa-chevron-right"></i></a>
            <a href="#" title="10페이지 다음 페이지로 이동"><i class="far fa-chevron-double-right"></i></a>
          </div>
        </div>
      </div>
  </div>
</template>