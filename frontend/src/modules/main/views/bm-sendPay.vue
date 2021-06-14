<template>
  <div>
    <!-- subVisualWrap02 -->
    <article id="subVisualWrap02">
      <section>
        <div class="text-center">
          <h2>요금제 안내</h2>
          <p>채널별 메시지 발송 요금을 확인하실 수 있습니다.</p>
        </div>
      </section>
    </article>
    <!-- //subVisualWrap02 -->
    
    <div id="contentWrap">
      <div class="sub02_conWrap">
        <div class="subVisualWrap02 wow animated fadeInUp">
          <section>
            <p class="tableTitle"><i class="fas fa-chevron-square-right"></i> Push</p>
            <p class="tableText">VAT 별도</p>
              <table class="user_table_skin1">
                <colgroup>
                  <col style="width:33%">
                  <col style="width:33%">
                  <col>
                </colgroup>
                <thead>
                  <tr>
                    <th>과금구분</th>
                    <th>과금기준</th>
                    <th class="end">요금</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>Push</td>
                    <td>발송 1건당</td>
                    <td class="end">0.04원</td>
                  </tr>
                </tbody>
              </table>
          </section>
        </div>

        <div class="subVisualWrap02 wow animated fadeInUp">
          <section>
            <p class="tableTitle"><i class="fas fa-chevron-square-right"></i> RCS</p>
            <p class="tableText">VAT 별도</p>
              <table class="user_table_skin1">
                <colgroup>
                  <col style="width:33%">
                  <col style="width:33%">
                  <col>
                </colgroup>
                <thead>
                  <tr>
                    <th>과금구분</th>
                    <th>과금기준</th>
                    <th class="end">요금</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>RCS SMS</td>
                    <td>수신 1건당</td>
                    <td class="end">0.04원</td>
                  </tr>
                  <tr>
                    <td>RCS LMS</td>
                    <td>수신 1건당</td>
                    <td class="end">0.04원</td>
                  </tr>
                  <tr>
                    <td>RCS MMS (기본형)</td>
                    <td>수신 1건당</td>
                    <td class="end">0.04원</td>
                  </tr>
                  <tr>
                    <td>RCS MMS (캐로셀형)</td>
                    <td>수신 1건당</td>
                    <td class="end">0.04원</td>
                  </tr>
                </tbody>
              </table>
          </section>
        </div>

        <div class="subVisualWrap02 wow animated fadeInUp">
          <section>
            <p class="tableTitle"><i class="fas fa-chevron-square-right"></i> SMS</p>
            <p class="tableText">VAT 별도</p>
              <table class="user_table_skin1">
                <colgroup>
                  <col style="width:33%">
                  <col style="width:33%">
                  <col>
                </colgroup>
                <thead>
                  <tr>
                    <th>과금구분</th>
                    <th>과금기준</th>
                    <th class="end">요금</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>SMS</td>
                    <td rowspan="3">수신 1건당</td>
                    <td class="end">9.9원</td>
                  </tr>
                  <tr>
                    <td>LMS</td>
                    <td class="end">30원</td>
                  </tr>
                  <tr>
                    <td>MMS</td>
                    <td class="end">100원</td>
                  </tr>
                </tbody>
              </table>
              <p class="infoText"><i class="fas fa-info-circle"></i> SMS 서비스에서 SMS는 메시지 길이 90바이트 이하의 단문 메시지이며, LMS는 메시지 길이 2,000바이트 이하의 장문 메시지, MMS는 멀티미디어 파일(사진, 컬러티콘, 배경 음악)이 첨부된 메시지를 의미합니다.</p>
          </section>
        </div>

        <div class="subVisualWrap02 wow animated fadeInUp">
          <section>
            <p class="tableTitle"><i class="fas fa-chevron-square-right"></i> KakaoTalk Bizmessage</p>
            <p class="tableText">VAT 별도</p>
              <table class="user_table_skin1">
                <colgroup>
                  <col style="width:25%">
                  <col style="width:25%">
                  <col style="width:25%">
                  <col>
                </colgroup>
                <thead>
                  <tr>
                    <th colspan="2">과금구분</th>
                    <th>과금기준</th>
                    <th class="end">요금</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td colspan="2">알림톡</td>
                    <td>수신 1건당</td>
                    <td class="end">7.9원</td>
                  </tr>
                  <tr>
                    <td rowspan="3">친구톡</td>
                    <td>텍스트</td>
                    <td>수신 1건당</td>
                    <td class="end">15원</td>
                  </tr>
                  <tr>
                    <td>이미지</td>
                    <td>수신 1건당</td>
                    <td class="end">24원</td>
                  </tr>
                  <tr>
                    <td>와이드 이미지</td>
                    <td>수신 1건당</td>
                    <td class="end">27원</td>
                  </tr>
                </tbody>
              </table>
          </section>
        </div>

      </div>
    </div>
    <QuickRight></QuickRight>
  </div>
</template>

<script>
import QuickRight from "@/modules/main/components/bc-quickRight.vue";

export default {
  name: 'sendPay',
  components : {
    QuickRight
  },
}
</script>