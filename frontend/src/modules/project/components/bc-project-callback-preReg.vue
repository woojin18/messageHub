<template>
  <div>
    <article>
		<div class="contentHeader mb20">
			<h2>비트큐브 프로젝트</h2>
		</div>

		<!-- 본문 -->
		<div class="contentBody mb40">
			<ul class="tab_s3">
				<li @click="fnMoveMainTab('dashBoard')"><a class="width120">대시보드</a></li>
				<li @click="fnMoveMainTab('info')"><a class="width120">기본정보</a></li>
				<li @click="fnMoveMainTab('member')"><a class="width120">멤버관리</a></li>
				<li @click="fnMoveMainTab('chan-rcs')"><a class="width120">채널관리</a></li>
				<li @click="fnMoveMainTab('callbackManage')" class="active"><a class="width120">발신번호관리</a></li>
				<li @click="fnMoveMainTab('spam')"><a class="width120">스팸관리</a></li>
			</ul>			
		</div>
		
		<h4 class="mt40">발신번호 사전 등록</h4>
		<p class="font-size12 color3 mt30 inline-block"><i class="far fa-info-circle"></i> 발신번호 미등록 시 메시지가 정상적으로 전송되지 않을 수 있습니다.<br><i class="far fa-info-circle"></i> 발신번호는 문자발송장비 사용자의 최대 3배까지만 등록할 수 있습니다.</p>

		<ul class="tabStyle tab6 bgColor_tapGray mt30">
			<li @click="fnMoveMainTab('callbackPreReg')" class="active"><a style="width:23%">발신번호 인증 등록</a></li>
			<li @click="fnMoveMainTab('callbackPreRegEx')"><a style="width:23%">사전 등록 예외 대상 사업자로 등록</a></li>
		</ul>

		<p class="font-size12 color3 mt10"><i class="far fa-info-circle"></i> 웹발송방식 및 서버발송방식을 통한 문자 발송 시 모두 이용 가능합니다.</p>

		<div class="row bgColor_f1 row-no-margin mt20">
			<div class="col-xs-3" style="padding: 16% 0; border-right:1px solid #fff">
				<h4 class="text-left ml60 line-height2em">발신번호 등록 안내</h4>
			</div>
			<div class="col-xs-9">					
				<div class="of_h" style="padding:40px 30px">
					<p class="font-size14 color4 line-height2em"><i class="far fa-info-circle"></i> 발신번호 등록은 다음의 절차로 진행됩니다.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1) RCS Biz Center에 서비스 신청을 합니다. (https://www.rcsbizcenter.com)<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2) 통합 클라우드 메시징 센터에서 브랜드 등록 및 발신번호 등록 신청을 합니다.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3) 승인 완료 된 발신번호는 사용 가능합니다.</p>
					<p class="font-size14 color4 mt20 line-height2em"><i class="far fa-info-circle"></i> 이미 등록된 브랜드가 있는 경우 추가 발신번호만 등록하실 수 있습니다.</p>	
					<p class="font-size14 color4 mt20 line-height2em"><i class="far fa-info-circle"></i> 통신서비스 이용 증명원 등록 후, RCS Biz Center(RBC) 에서 서류 심사 후 발신번호로 등록<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[통신서버스 이용증명원 발급방법안내]<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 등록할 <span class="color1">휴대폰 번호 또는 유선번호의 해당 통신사 고객센터를 통해 발급</span> 받을 수 있습니다.</p>	
					<p class="font-size14 color4 mt20 line-height2em"><i class="far fa-info-circle"></i> 발신번호별 검수로 서류심사가 늦어질 수 있는 점 양해 부탁드립니다.</p>	
				</div>
			</div>				
		</div>

		<div class="row bgColor_f1 row-no-margin mt10">
			<div class="col-xs-3" style="padding: 6% 0; border-right:1px solid #fff">
				<h4 class="text-left ml60 line-height2em">브랜드 등록이<br>필요한 경우</h4>
			</div>
			<div class="col-xs-9">					
				<div class="of_h" style="padding:40px 30px">
					<h5 class="font-size16">대리인으로 등록</h5>
					<p class="font-size14 color4 mt20 line-height2em inline-block"><i class="far fa-info-circle"></i> RCS Biz Center 에서 발급받은 API Key를 사용하여 브랜드와 발신번호를 등록 신청할 수 있습니다.</p>	
					<a @click="fnMoveMainTab('chan-rcs')" class="btnStyle4 white font13 width276 mt10 float-right">브랜드 등록 요청</a>
				</div>
			</div>				
		</div>

		<div class="row bgColor_f1 row-no-margin mt10">
			<div class="col-xs-3" style="padding: 4% 0; border-right:1px solid #fff">
				<h4 class="text-left ml60 line-height2em">브랜드 등록이<br>필요없는 경우</h4>
			</div>
			<div class="col-xs-9">					
				<div class="of_h" style="padding:40px 30px">
					<h5 class="font-size16">대리인으로 등록</h5>
					<p class="font-size14 color4 mt20 line-height2em inline-block"><i class="far fa-info-circle"></i> 이미 등록된 브랜드에 발신번호를 등록 신청할  수 있습니다.</p>	
					<a href="#self" class="btnStyle4 white font13 width276 mt10 float-right">추가 발신번호 등록 요청</a>
				</div>
			</div>				
		</div>

		<div class="row bgColor_f1 row-no-margin mt10">
			<div class="text-center mt40">
				<a @click="fnMoveMainTab('callbackManage')" class="btnStyle3 white font14" data-dismiss="modal">목록</a>						
			</div>
		</div>
		<!-- //본문 -->

		<footer>Copyright©LG Plus Corp. All Rights Reserved.</footer>
	</article>
  </div>
</template>


<script>
import projectApi from '../service/projectApi'
import tokenSvc from '@/common/token-service';

import modal from "./bp-project-manage-detail.vue";

export default {
  components: {
    modal
  },
  data() {
    return {
      // 프로젝트 정보
      projectId : '',
      projectName : '',
      
      visible : false,  // 레이어 팝업 
      modal_title : '', // 레이어 팝업 타이틀
      save_status : '', // 등록 수정 여부
      row_data : {},
      // 검색 조건
      col1 : "",
      col2 : "",
      // select 박스 value (출력 갯수 이벤트)
      selected : 10,
      // 현재 페이징 위치
      pagingCnt : 1,
      // 리스트 
      items : [],
      count : 0
    }
  },
  mounted() {
    var vm = this;

    this.projectId = this.$route.params.projectId;
    this.projectName = this.$route.params.projectName;
  },
  methods: {
    fnMoveMainTab(moveTabName){
      this.$router.push( {name:moveTabName, params:{"projectId" : this.projectId, "projectName" : this.projectName }} );
    },
	fnMoveTab(moveTabName){
      this.$router.push( {name:moveTabName, params:{}} );
    },
    // 검색
    fnSearch() {
      
      var vm = this;
      var params = {
        "src_project_name"  : $("#src_project_name").val(),
        "src_use_yn"        : $("#src_use_yn").val(),
        "rows"              : vm.selected,
        "paging"            : vm.pagingCnt
      }
       
      projectApi.selectProjectList(params).then(response =>{
        vm.items = response.data.data;
      });
    },
    // 등록창
    fnProjectReg : function(){
        this.visible = !this.visible;
        this.save_status = 'C';
        this.row_data = {};
      },
    // 상세창
    fnProjectDetail(data) {
      this.$router.push( {name:"chan-rcs",params:{"projectId" : data.PROJECT_ID, "projectName" : data.PROJECT_NAME}} );
    },
    // 수정창
    fnProjectUpdate(data) {
      this.visible = !this.visible;
      this.save_status = 'U';
      this.row_data = data;
    },
    fnProjectDelete(data){
      var params = {
        "project_id"  : data.PROJECT_ID,
        "sts"         : "D",
        "userDto"     : tokenSvc.getToken().principal    
      };

      projectApi.saveProject(params).then(response =>{
        var result = response.data;

        if(result.success) {
          alert("삭제되었습니다.");
          // 리스트 조회
          this.fnSearch();
        } else {
          alert("저장에 실패했습니다.");
        }
      }); 
    }



  }
}
</script>