<template>
  <div v-show="selTab == 2">
    <!-- 본문 -->
    <h4 class="lc-1 mb20 mt40 font-size16">청구ID 조회(후불)</h4>
    <div class="of_h">
      <div class="float-right">
        <a href="#self" class="btnStyle3 gray font13 width120" data-toggle="modal" data-target="#Register_id">청구ID 등록</a>
      </div>
    </div>

    <div class="row">
      <div class="col-xs-12">									
        <!-- table -->
        <table class="table_skin1 bt-000 tbl-striped">
          <thead>
          <tr>
            <th class="text-center lc-1">가입번호</th>
            <th class="text-center lc-1">생성처</th>
            <th class="text-center lc-1 end">청구ID 생성일</th>
          </tr>
          </thead>
          <tbody>
            <tr>
            <td class="text-center">22222</td>
            <td class="text-center">U123456781</td>
            <td class="text-center end">2021-01-02</td>
            </tr>	
            <tr>
            <td class="text-center">33333</td>
            <td class="text-center">U123456781</td>
            <td class="text-center end">2021-01-02</td>
            </tr>
          </tbody>
        </table>
        <!-- //table -->
      </div>			
    </div>

    
    <h4 class="lc-1 mb20 mt40 font-size16">프로젝트별 정산</h4>
    <div class="row">
      <div class="col-xs-12">
        <!-- 15개씩 보기 -->
        <div class="of_h mb20">
          <div class="float-left">전체 : <span class="color1"><strong>20</strong></span>건
          </div>
        </div>
        <!-- //15개씩 보기 -->
        
        <!-- table -->
        <table  class="table_skin1 bt-000 tbl-striped">
          <colgroup>
            <col style="width:20%">
            <col>
            <col style="width:15%">
            <col style="width:15%">
            <col style="width:15%">
            <col style="width:15%">
          </colgroup>
          <thead>
          <tr>
            <th class="text-center lc-1">프로젝트 명</th>
            <th class="text-center lc-1">프로젝트 ID</th>
            <th class="text-center lc-1">프로젝트 생성일</th>
            <th class="text-center lc-1">사용여부</th>
            <th class="text-center lc-1">가입ID</th>
            <th class="text-center lc-1 end">청구ID</th>
          </tr>
          </thead>
          <tbody>
            <tr>
            <td class="text-left">엘지유플러스 통합메시징 서비스</td>
            <td class="text-center">A12545</td>
            <td class="text-center">2021-01-02</td>
            <td class="text-center">예</td>
            <td class="text-center">1234</td>
            <td class="text-center end">U123456782</td>
            </tr>	
            <tr>
            <td class="text-left">유플러스 메시징 서비스</td>
            <td class="text-center">A12546</td>
            <td class="text-center">2021-01-02</td>
            <td class="text-center">예</td>
            <td class="text-center">1235</td>
            <td class="text-center end">U123456782</td>
            </tr>	
            <tr>
            <td class="text-left">클라우드 통합 메시징 서비스</td>
            <td class="text-center">A12547</td>
            <td class="text-center">2021-01-02</td>
            <td class="text-center">예</td>
            <td class="text-center">1236</td>
            <td class="text-center end">U123456782</td>
            </tr>	
            <tr>
            <td class="text-left">테스트 프로젝트</td>
            <td class="text-center">A12548</td>
            <td class="text-center">2021-01-02</td>
            <td class="text-center">아니오</td>
            <td class="text-center">1237</td>
            <td class="text-center end">U123456782</td>
            </tr>	
          </tbody>
        </table>
        <!-- //table -->
      </div>			
    </div>

    <h4 class="lc-1 mb20 mt40 font-size16">부서별 정산</h4>
    <div class="of_h">
      <div class="float-right">
        <a href="#self" class="btnStyle3 gray font13 width120" data-toggle="modal" data-target="#Register_Department">부서 등록</a>
      </div>
    </div>

    <div class="row">
      <div class="col-xs-12">
        <!-- 15개씩 보기 -->
        <div class="of_h mb20">
          <div class="float-left">전체 : <span class="color1"><strong>20</strong></span>건
          </div>
        </div>
        <!-- //15개씩 보기 -->
        
        <!-- table -->
        <table  class="table_skin1 bt-000 tbl-striped">
          <colgroup>
            <col style="width:20%">
            <col>
            <col style="width:15%">
            <col style="width:15%">
            <col style="width:15%">
            <col style="width:15%">
          </colgroup>
          <thead>
          <tr>
            <th class="text-center lc-1">부서ID</th>
            <th class="text-center lc-1">부서명</th>
            <th class="text-center lc-1">부서 수정일</th>
            <th class="text-center lc-1">사용여부</th>
            <th class="text-center lc-1">청구ID</th>
            <th class="text-center lc-1 end">관리</th>
          </tr>
          </thead>
          <tbody>
            <tr>
            <td class="text-left">B12345</td>
            <td class="text-center">영업 1팀</td>
            <td class="text-center">2021-01-02</td>
            <td class="text-center">예</td>
            <td class="text-center">U123456782</td>
            <td class="text-center end"><a href="#self" class="btnStyle8 mr5">수정</a><a href="#self" class="btnStyle8 mr5">삭제</a></td>
            </tr>	
            <tr>
            <td class="text-left">B12346</td>
            <td class="text-center">영업 2팀</td>
            <td class="text-center">2021-01-02</td>
            <td class="text-center">예</td>
            <td class="text-center">U123456782</td>
            <td class="text-center end"><a href="#self" class="btnStyle8 mr5">수정</a><a href="#self" class="btnStyle8 mr5">삭제</a></td>
            </tr>	
            <tr>
            <td class="text-left">B12347</td>
            <td class="text-center">영업 3팀</td>
            <td class="text-center">2021-01-02</td>
            <td class="text-center">예</td>
            <td class="text-center">U123456782</td>
            <td class="text-center end"><a href="#self" class="btnStyle8 mr5">수정</a><a href="#self" class="btnStyle8 mr5">삭제</a></td>
            </tr>	
            <tr>
            <td class="text-left">B12348</td>
            <td class="text-center">프로젝트팀</td>
            <td class="text-center">2021-01-02</td>
            <td class="text-center">아니오</td>
            <td class="text-center">U123456782</td>
            <td class="text-center end"><a href="#self" class="btnStyle8 mr5">수정</a><a href="#self" class="btnStyle8 mr5">삭제</a></td>
            </tr>	
          </tbody>
        </table>
        <!-- //table -->
      </div>			
    </div>
  </div>
</template>

<script>
export default {
  name: 'deferredCash',
  data() {
    return {}
  },
  props: {
    selTab: Number
  },
  components: {
  },
  mounted() {
  },
  methods: {

  }
}
</script>