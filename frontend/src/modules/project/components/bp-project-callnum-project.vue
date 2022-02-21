<template>
	<!-- Register Modal -->
	<div class="modal modalStyle" id="projectPop" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog" style="width:650px">
			<div class="modal-content">
				<div class="modal-body">
					<div>
						<h2>연결된 프로젝트</h2>
						<hr>
						<div class="row consolMarginTop">
							<div class="col-xs-12">		
                <div class="row">
                  <div class="col-xs-12 consolMarginTop">
                    <table class="table_skin1 tbl-striped" style="width:100%">
                      <colgroup>
                        <col style="width:*">
                        <col style="width:33%">
                      </colgroup>
                      <thead>
                        <tr>
                          <th class="text-center lc-1">프로젝트</th>
                          <th class="text-center lc-1">프로젝트 ID</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(data, index) in projects" :key="index">
                          <td class="text-center">{{ data.projectName }}</td>
                          <td class="text-center lc-1">{{ data.projectId }}</td>
                        </tr>
                        <tr v-if="projects.length == 0">
                          <td class="text-center"></td>
                          <td class="text-center" colspan="4">검색된 내용이 없습니다.</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
                <!-- 리스트 End -->
              </div>
            </div>
          </div>		
					<div class="text-center mt20">
						<a @click="fnClose" class="btnStyle1 backWhite"  title="닫기">닫기</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	name: 'smsPop',
	components: {
	},
	props: {
		projects: {
			type: Array,
			require: true,
			default: false,
		},
	},
	data() {
		return {
		}
	},
	mounted() {
	},
	watch: {
		smsOpen: function() {
			this.fnSearch(1);
		}
	},
	methods: {
		// 닫기
		fnClose(){
			jQuery('#projectPop').modal('hide');
		}
	}
}
</script>