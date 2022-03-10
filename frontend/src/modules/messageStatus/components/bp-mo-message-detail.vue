<template>
<div v-if="layerView" class="layerPopup">
		<div class="modal-dialog" style="width:370px">
			<div class="modal-content">
				<div class="modal-body" style="overflow-x: hidden;">
					<div>
						<h2>메시지 상세</h2>
						<hr>
					
						<div class="row row-no-margin">
							<div class="">
								<div class="phoneWrap" style="margin-left: 0px;">
									<img src="/se2/images/phoneMockup1.svg" alt="프리 템플릿">
									<div class="phoneTextWrap">
										<div class="phoneText1">
                        <p>{{title}}</p>
                        <div v-if="mmsImgCnt > 0">
                            <div v-for="(imgInfo, idx) in mmsImgList" :key="idx" class="phoneText2 mt10 text-center simulatorImg"
                            :style="'padding:65px;background-image: url('+imgInfo.imgUrl+');'">
                            </div>
                        </div>
										    <p class="color3">{{msg}}</p>
										</div>
									</div>
								</div>
                <div class="text-center">
                    <a class="btnStyle1 backBlack" @click.self="fnCloseLayer" title="닫기">닫기</a>	
                </div>
							</div>
					  </div>											
				  </div>
		    </div>
		  </div>
    </div>
	<!-- </div> -->
    <!-- //content -->
</div>
</template>

<script>
import messageStatusApi from '../service/messageStatusApi'

export default {
    name: 'UpdateLayer',
  props: {
    layerView: {
      type: Boolean,
      require: true,
      default: false
    },
    detailMsgKey: {
      type: String,
      require: true
    }
  },
  data() {
    return {
      title : '',
      msg : '',
      mmsImgCnt : 0,
      mmsImgList : []
    }
  },
  watch: {
    layerView: function(newVal) {
        // LayerView가 true를 반환해 팝업이 출력됫을 시에만 값을 세팅하여 수정 팝업을 만든다.
        if(newVal) {
            var params = {
            "moKey": this.detailMsgKey
            }

             messageStatusApi.selectMoMessageDetail(params).then(response=> {
                var result = response.data.data;

                var title = result.title;
                var msg = result.msg;
                var mmsImgCnt = result.contentCnt;
                var mmsImgList = result.mmsImg;

                this.title = title;
                this.msg = msg;
                this.mmsImgCnt = mmsImgCnt;
                this.mmsImgList = mmsImgList;

            });                    
   
        }
    },
  },
  methods: {
    // 닫기
    fnCloseLayer(){
      this.$emit('update:layerView', false);
    },
    fnBack(){
      this.$router.go(-1);
    }
  }
}
</script>
