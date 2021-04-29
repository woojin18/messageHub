<template>
  <div>
	<h4 class="mt40">발신번호 사전 등록</h4>
	<div class="row">
		<div class="col-xs-9 ml40 mt30">				
			<div class="mt30 of_h">
				<h4 class="font-normal float-left" style="width:28%">브랜드명</h4>						
				<select id="cate1" class="selectStyle3" style="width:72%">
		<option v-for="(option, i) in brandItem" v-bind:value="option.brandId" v-bind:key="i">
			{{ option.brandName }}
		</option>
		</select>
				<p class="font-size13 color3 inline-block ml10 ml_28">발신번호 등록은 브랜드가 먼저 등록되어야 하며, 승인완료 된 브랜드만 보여집니다.</p>
			</div>
			<div class="mt30 of_h">
				<h4 class="font-normal float-left" style="width:28%">대표발신번호 *</h4>
				<input type="text" class="inputStyle" style="width:30%">
				<p class="font-size12 color3 inline-block ml10">휴대폰번호 등록 불가</p>
			</div>
			<div class="mt20 of_h">
				<h4 class="font-normal float-left" style="width:28%">발신 번호명 *</h4>
				<input type="text" class="inputStyle float-left" style="width:72%">
			</div>
			<div class="mt20 of_h">
				<h4 class="font-normal float-left line-height1-5em" style="width:28%">대표번호 문자수신 서비스 *</h4>
				<div class="consolCheck vertical-top" style="width:72%"><input type="checkbox" id="use" class="checkStyle2" value="use"><label for="use" class="font-size14">사용중</label>
				<p class="font-size13 color3 mt5">대표번호 문자수신(MO)서비스를 현재 사용하고 계실 경우 반드시 해당정보를 체크해 주셔야 합니다.</p>
				</div>						
			</div>
			<div class="mt20 of_h">
				<h4 class="font-normal float-left" style="width:28%">추가발신번호 *</h4>
				<input type="text" class="inputStyle" style="width:61%"><a href="#self" class="btnStyle7 vertical-middle ml10 minWidth46"><i class="far fa-plus"></i></a>
				<p class="font-size13 color3 mt5 ml_28">추가발신번호가 있는 경우  버튼을 클릭하세요. 최대 10개까지 사용등록 가능합니다.</p>
				<input type="text" class="inputStyle ml_28" style="width: 61%"><a href="#self" class="btnStyle7 ml10 minWidth46 mt10"><i class="far fa-minus"></i></a>
				<input type="text" class="inputStyle ml_28" style="width: 61%"><a href="#self" class="btnStyle7 ml10 minWidth46 mt10"><i class="far fa-minus"></i></a>
			</div>
			<div class="mt20 of_h">
				<h4 class="font-normal float-left vertical-middle line-height1-5em" style="width:28%">통신서비스 가입증명원 *</h4>
				<a href="#self" class="btnStyle7 vertical-middle float-left minWidthAuto" style="width:17%">파일선택</a>
				<input type="text" class="inputStyle float-right" style="width:54%">
				
				<div class="font-size12 color3 mt10 ml_28 float-left" style="width:73%">
					<div class="consolCheck float-left mb40"><input type="checkbox" id="Lmit" class="checkStyle2" value="Lmit"><label for="Lmit"></label></div>
					<p class="font-size14 lc-1" style="color:#000">메시지 발송 시 기본템플릿(구.Free템플릿)은 정보성에 한하여 이용 가능합니다. 규정위반 시 서비스 이용이 제한될 수 있습니다.</p>
				</div>						
			</div>
			<div class="mt10 of_h">
				<h4 class="font-normal float-left vertical-top" style="width:28%">요청사항</h4>
				<input type="text" class="inputStyle float-left" style="width:72%; height:160px">
			</div>
			<div class="mt30 float-right">
				<a href="#self" class="btnStyle5 red float-left width120">저장</a>
				<a href="#self" class="btnStyle5 red float-left ml10 width120">승인요청</a>
				<a href="#self" class="btnStyle5 white float-left ml10 width120">목록</a>
			</div>
		</div>
		<div class="col-xs-3">
		</div>
	</div>

  </div>
</template>


<script>
import projectApi from '../service/projectApi'
import channelApi from '../../../modules/channel/service/api'
import tokenSvc from '@/common/token-service';

export default {
  components: {
  },
  data() {
    return {
      // 프로젝트 정보
      projectId : '',
      projectName : '',
      // 리스트 
      brandItem : []
    }
  },
  mounted() {
    this.projectId = this.$route.params.projectId;
    this.projectName = this.$route.params.projectName;

    this.fnBrandSelectList();
  },
  methods: {
    fnMoveMainTab(moveTabName){
      this.$router.push( {name:moveTabName, params:{"projectId" : this.projectId, "projectName" : this.projectName }} );
    },
    // 브랜드 셀렉트 가져오기
    fnBrandSelectList() {
      var params = {
        "projectId"  : this.projectId
      }

console.log(params);

      channelApi.selectRcsBrandList(params).then(response =>{
        this.brandItem = response.data.data;
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