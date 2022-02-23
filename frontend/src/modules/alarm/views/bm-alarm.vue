<template>
    <div>
        <article>
			<div class="contentHeader mb10">
				<h2>알람 > 알람</h2>
			</div>

			<!-- 본문 -->
			<div class="row mt10 Alarm">
                <div class="col-xs-6 mt10" v-for="(alarmType, idx1) in alarmTypeList">			
                    <div class="mt20">
                        <div class="row">
                            <div class="col-xs-6 pr0">
                                <div class="menuBox pd20" style="height:190px">
                                    <h4>{{alarmType.alarmTypeName}}</h4>		
                                    <p class="color4 mt10" style="overflow-y: visible;">{{alarmType.alarmDesc}}</p>
                                    <div class="mt40">
                                        <router-link activity="SAVE" v-if="(alarmType.alarmTypeCode == 'PRE' && alarmType.list.length == 0) || alarmType.alarmTypeCode != 'PRE'" :to="{name:'alarmType', params:{typeCode:alarmType.alarmTypeCode}}" class="btnStyle2 backRed">등록</router-link>
                                    </div>			
                                </div>
                            </div>
                            <div class="col-xs-6 pl0">
                                <div class="pl0 border-line2 pd20" style="overflow-y:auto; height:190px">
                                    <div class="scroll-y7">
                                        <div class="of_h" v-for="(data,idx2) in alarmType.list" v-bind:class="{'mt10':idx2>0}">
                                            <router-link :to="{name:'updAlarmType', params:{typeCode:alarmType.alarmTypeCode, alarmId:data.alarmId}}" class="inline-block float-left text-underline mt5" style="width:60%">{{data.alarmName}}</router-link>
                                            <div class="ilnine-block float-right of_h" style="width:40%"><a activity="SAVE" @click="fnDel(idx1, idx2)" class="btnStyle1 borderLightGray mr5 float-right">삭제</a></div>
                                        </div>
                                        <p v-if="alarmType.list.length==0">알람설정을 하지 않았습니다.<br>먼저 등록해 주십시오.</p>
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
      alarmTypeList: [],
      del1 : -1, del2 : -2
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
    },
    fnDel(idx1, idx2) {
      this.del1 = idx1
      this.del2 = idx2
			eventBus.$on('callbackEventBus', this.fnDelCallback);
			confirm.fnConfirm('알람 삭제', '삭제하시겠습니까?', '확인');
    },
    fnDelCallback() {
      let params = {'alarmId': this.alarmTypeList[this.del1].list[this.del2].alarmId}
			alarmApi.delAlarm(params).then(response =>{
				var result = response.data;
				if(result.success) {
          this.alarmTypeList[this.del1].list.splice(this.del2, 1)
					confirm.fnAlert('알람 삭제', '알람을 삭제 했습니다.');
				} else {
					confirm.fnAlert('알람 삭제', result.message);
				}
			});
    }
  }
}
</script>


