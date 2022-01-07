<template>
	<div>
		<h4 class="inline-block">발신번호 사전등록제 시행 안내</h4>
		<p class="color5">전기통신사업법 제84조에 따라 2015년 10월 16일부로 <strong>'발신번호 사전등록제'</strong>가 시행되었습니다. '발신번호 사전 등록제'란, 거짓으로 표시된 전화번호로 인한 이용자 피해 예방을 위한 것으로, <strong>문자 메시지 발송 시 사전 인증된 발신 번호로만 사용할 수 있도록 등록하는 제도</strong> 입니다. 이에 따라, 전기통신사업법 제 84조 2항(전화번호의 거짓 표시 금지 및 이용자 보호)에 위반되지 않도록 자신의 발신번호를 사전에 등록하고, 등록된 번호로만 문자 메시지를 발송해야 합니다. 또한, 발신 번호는 기업, 기관 혹은 본인이 소유한 번호 여야 하며, 등록된 발신 번호로 전송한 메시지가 발신 번호 변조로 의심되어 KISA(한국인터넷진흥원)로부터 소명 요청을 받을 경우, 해당 고객의 발신 번호 소유를 증명할 수 있어야 합니다.</p>

		<div class="clear">
			<h4 class="inline-block">발신번호 등록 방식</h4>
			<p class="color3 inline-block float-right mt30">* 통신서비스 이용증명원 <i class="fas fa-question-circle toolTip toolRight"><span class="toolTipText" style="width:320px">이용자 본인이 사용하는 번호임을 증명하는 서류입니다.<br>가입한 통신사의 고객 센터에서 발급받을 수 있습니다.<br>자세한 발급 방법은 해당 통신사에 문의하시기 바랍니다.</span></i></p>				
		</div>

		<div class="row">
			<div class="col-xs-12">
				<table class="table_skin1 pdWide tbl-striped font-size13" style="width:100%">
					<caption>발신번호 등록 방법의 RCS 발신번호, 문자 발신번호를 제공하는 표</caption>
					<colgroup>
						<col style="width:30%">
						<col style="width:20%">
						<col style="width:20%">
						<col style="width:30%">
					</colgroup>
					<thead>
						<tr>
							<th rowspan="2" class="text-center">RCS 발신번호</th>
							<th colspan="3" class="text-center bb end">문자 발신번호 (문자 발신번호만 등록)</th>
						</tr>
						<tr>
							<th class="text-center">휴대폰 인증</th>
							<th class="text-center">서류 인증</th>
							<th class="text-center end">대리인 등록</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="text-left vertical-middle">
								<ol class="numList">
									<li>RCS 발신번호 등록을 위해서는 RCS Biz Center 가입 후 , 브랜드를 생성해야 합니다.</li>
									<li>발신번호는 브랜드 1개당 N개 추가 할 수 있습니다.</li>
									<li>RCS 발신번호는 RBC의 승인과정을 통해 등록됩니다.(1~3일 소요)</li>
									<li>RCS 발신번호가 등록되면 문자 발신번호도 동시에 등록됩니다.</li>
									<li>통신서비스 이용증명원이 필요합니다.</li>
								</ol>
							</td>
							<td class="text-left vertical-middle">
								<ol class="numList">
									<li>안심본인인증을 통해 등록됩니다.</li>
								</ol>
							</td>
							<td class="text-left vertical-middle">
								<ol class="numList">
									<li>전화번호를 입력하고 통신서비스 이용증명원을 첨부하시면 고객센터에서 서류 심사 후 발신번호 등록합니다. (1~3일 소요)</li>
								</ol>
							</td>
							<td class="text-left vertical-middle end">
								<ol class="numList">
									<li>대리인 등록 신청서를 작성하여 증빙서류를 보내시면 서류 검토 후 발신번호 등록 권한을 부여하여 드립니다.</li>
									<li>제출서류
										<ul>
											<li>- 발신번호 변작방지 및 사전등록 동의서</li>
											<li>- 대리인 등록신청서</li>
											<li>- 인감증명서</li>
											<li>- 인감이 날인된 위임장</li>
											<li>- 재직증명서</li>
										</ul>
									</li>
								</ol>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<div class="text-center mt40">
			<a @click="$emit('fnMoveTab', '2')" class="btnStyle2 borderGray ml10">발신번호 사전 등록</a>
		</div>
		<!-- //본문 -->
	</div>
</template>