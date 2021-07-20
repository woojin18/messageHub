<template>
    <div>
        <article>
			<div class="contentHeader mb10">
				<h2>알람관리</h2>
			</div>

			<!-- 본문 -->
			<div class="row">
                <div class="col-xs-6 mt10" v-for="alarmType in alarmTypeList">			
                    <div class="mt20">
                        <div class="row">
                            <div class="col-xs-6 pr0">
                                <div class="menuBox pd20 height170">
                                    <h4>{{alarmType.alarmTypeName}}</h4>		
                                    <p class="color4 mt10">{{alarmType.alarmDesc}}</p>
                                    <div class="mt40">
                                        <router-link :to="{name:'alarmType', params:{typeCode:alarmType.alarmTypeCode}}" class="btnStyle2 backRed">등록</router-link>
                                    </div>			
                                </div>
                            </div>
                            <div class="col-xs-6 pl0">
                                <div class="pl0 border-line2 pd20 height170">
                                    <div class="scroll-y7">
                                        <div class="of_h">
                                            <router-link :to="{name:'updAlarmType', params:{typeCode:alarmType.alarmTypeCode, alarmId:1}}" class="inline-block float-left text-underline mt5" style="width:60%">구매부 알람</router-link>
                                            <div class="ilnine-block float-right of_h" style="width:40%"><a href="#self" class="btnStyle1 borderLightGray mr5 float-right">삭제</a></div>
                                        </div>
                                        <div class="of_h mt10">
                                            <p class="inline-block float-left text-underline mt5" style="width:60%">지원부 알람</p>
                                            <div class="ilnine-block float-right of_h" style="width:40%"><a href="#self" class="btnStyle1 borderLightGray mr5 float-right">삭제</a></div>
                                        </div>
                                        <div class="of_h mt10">
                                            <p class="inline-block float-left text-underline mt5" style="width:60%">지원부 알람</p>
                                            <div class="ilnine-block float-right of_h" style="width:40%"><a href="#self" class="btnStyle1 borderLightGray mr5 float-right">삭제</a></div>
                                        </div>
                                        <div class="of_h mt10">
                                            <p class="inline-block float-left text-underline mt5" style="width:60%">지원부 알람</p>
                                            <div class="ilnine-block float-right of_h" style="width:40%"><a href="#self" class="btnStyle1 borderLightGray mr5 float-right">삭제</a></div>
                                        </div>
                                    </div>
                                </div>
                            </div>	
                        </div>
                    </div>
                </div>
            </div>
		</article>
    </div>
</template>

<script>
import alarmApi from "@/modules/alarm/service/alarmApi"
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";

export default {
  data() {
    return {
      alarmTypeList: []
    }
  },
  components: {
  },
  mounted() {
    this.fnInit()
  },
  methods: {
    fnInit() {
      var vm = this
      alarmApi.selectAlarmTypeList({}).then(response => {
        var result = response.data
        if(result.success) {
          vm.alarmTypeList = result.data
        }
      })
    }
  }
}
</script>


