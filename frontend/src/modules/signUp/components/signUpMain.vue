<template>
	<div>
		<section class="userSection width540 mt70">
			<h2 class="text-center color333">회원가입</h2>

			<h4 class="mt70">로그인 정보</h4>
			<div class="joinBox mt10">
				<div class="of_h">
					<div class="float-left" style="width:22%"><h5>아이디</h5></div>
					<div class="float-left" style="width:78%">
						<p class="color1 font-size14">{{ loginId }}</p>
					</div>
				</div>

				<div class="of_h">
					<div class="float-left" style="width:22%"><h5>이름</h5></div>
					<div class="float-left" style="width:78%">
						<input type="text" class="inputStyle" id="userNm" placeholder="휴대폰 인증 후 자동 입력됩니다." v-model="userNm" disabled>
					</div>
				</div>

				<div class="of_h mt10">
					<div class="float-left" style="width:22%"><h5>비밀번호*</h5></div>
					<div class="float-left" style="width:78%">
						<input type="password" class="inputStyle" placeholder="비밀번호 [대/소문자,숫자,특수문자 조합(길이8~16자리)]" maxlength="16" v-model="password">
					</div>
				</div>
				
				<div class="of_h mt10">
					<div class="float-left" style="width:22%"><h5>비밀번호 확인 *</h5></div>
					<div class="float-left" style="width:78%">
						<input type="password" class="inputStyle" placeholder="비밀번호 확인" v-model="passwordChk" maxlength="16">
					</div>
				</div>

				<!-- <div class="of_h mt10">
					<div class="float-left" style="width:22%"><h5>SMS 인증여부*</h5></div>
					<div class="float-left" style="width:78%">
						<input type="radio" name="payment" value="Y" class="cBox" id="sms_agree01" v-model="smsCertifyYn"> <label for="sms_agree01" class="font-normal mr30">예</label>
						<input type="radio" name="payment" value="N" class="cBox" id="sms_agree02" v-model="smsCertifyYn"> <label for="sms_agree02" class="font-normal">아니요</label>
					</div>
				</div> -->
			</div>

			<h4 class="mt40">담당자 휴대폰 인증*</h4>
			<div class="joinBox mt10">
				<div class="of_h">
					<div class="float-left" style="width:22%">
						<a @click="fnPopup" class="btnStyle1 backLightGray vertical-middle" style="min-width:auto; width:100%">휴대폰 인증</a>
					</div>
					<div class="float-right" style="width:75%">
						<input type="text" id="phoneCerti" class="inputStyle" placeholder="" v-model="phoneCerti" disabled>
						<input type="hidden" id="gender" v-model="gender" disabled>
						<input type="hidden" id="coInfo" v-model="coInfo" disabled>
						<button id="nice" style="display:none;" @click="fnGetNiceCheckInfo"></button>
					</div>
				</div>
			</div>

			<h4 class="mt40">사업자정보</h4>
			<div class="of_h">
				<div class="float-left" style="width:18%"><h5>사업자번호*</h5></div>
				<div class="float-left" style="width:82%">
					<input type="text" class="inputStyle" placeholder="사업자번호 ( - 없이 입력 )" style="width:72%" v-model="regno" @keypress.enter="fnSelCorp">
					<input type="hidden" v-model="selRegno" disabled>
					<div class="float-right" style="width:25%">
						<a @click.prevent="fnSelCorp" class="btnStyle1 backLightGray" style="min-width:auto; width:100%">확인</a>
					</div>
				</div>
			</div>

			<div class="joinBox mt10">
				<div class="of_h mt10" v-if="selCorpCnt == 0">
					<div class="float-left" style="width:15%"><h5>고객번호*</h5></div>
					<div class="float-left" style="width:85%">
						<input type="text" class="inputStyle" placeholder="고객사 신규 등록의 경우 자동 생성됩니다."  v-model="custNo" disabled>
					</div>
				</div>

				<div class="of_h mt10" v-if="selCorpCnt == 0">
					<div class="float-left" style="width:15%"><h5>고객유형*</h5></div>
					<div class="float-right" style="width:85%">
						<select class="selectStyle2" title="고객유형선택란" v-model="custKdCd" :disabled="selCorpCnt > 0" style="width:50%">
							<option value="">선택하세요</option>
							<option  v-for="(row, index) in custTypeArr" :key="index" :value="row.codeVal1"> {{ row.codeName1 }} </option>
						</select>
						<input type="hidden" class="inputStyle" placeholder="고객유형"  v-model="custKdCd" :disabled="selCorpCnt > 0">
					</div>
				</div> 


				<div class="of_h mt10" v-if="selCorpCnt == 0">
					<div class="float-left" style="width:15%"><h5>생년월일/<br>법인번호</h5></div>
					<div class="float-left" style="width:85%">
						<input type="text" class="inputStyle" placeholder="개인사업자의 생년월일(YYYYMMDD) 또는 법인번호"  v-model="custrnmNo" :disabled="selCorpCnt > 0">
					</div>
				</div>
				<div class="of_h mt10">
					<div class="float-left" style="width:15%"><h5>사업자명*</h5></div>
					<div class="float-left" style="width:85%">
						<input type="text" class="inputStyle" placeholder="사업자명"  v-model="corpNm" :disabled="selCorpCnt > 0">
					</div>
				</div>

				<div class="of_h mt10">
					<div class="float-left" style="width:15%"><h5> 대표자명*</h5></div>
					<div class="float-left" style="width:85%">
						<input type="text" class="inputStyle" placeholder="대표자명"  v-model="rtnCustNm" :disabled="selCorpCnt > 0">
						<input type="hidden" class="inputStyle" placeholder="대표자명"  v-model="ceoNm">
					</div>
				</div>
				<div class="of_h mt10" v-if="selCorpCnt == 0">
					<div class="float-left" style="width:15%"><h5>업태*</h5></div>
					<div class="float-right" style="width:85%">
						<input type="text" class="inputStyle" placeholder="업태"  v-model="busiType" :disabled="selCorpCnt > 0">
					</div>
				</div>
				<div class="of_h mt10" v-if="selCorpCnt == 0">
					<div class="float-left" style="width:15%"><h5>종목*</h5></div>
					<div class="float-right" style="width:85%">
						<input type="text" class="inputStyle" placeholder="종목"  v-model="busiClass" :disabled="selCorpCnt > 0">
					</div>
				</div>
				<div class="of_h mt10">
					<div class="float-left" style="width:15%"><h5>사업장주소*</h5></div>
					<div class="float-left" style="width:85%">
						<div class="float-left" style="width:72%"><input type="text" class="inputStyle" placeholder="우편번호" v-model="custAddrZip" disabled ></div>
						<div class="float-right" style="width:25%"><a class="btnStyle1 backLightGray" style="min-width:auto; width:100%" @click="fnAddrPopup">주소 조회</a></div>					
					</div>
					<div class="float-right mt5" style="width:85%"><input type="text" class="inputStyle" placeholder="주소입력" v-model="woplaceAddress" disabled></div>
					<div class="float-right mt5" style="width:85%"><input type="text" class="inputStyle" placeholder="상세주소 입력" v-model="woplaceAddressDetail"  :disabled="selCorpCnt > 0"></div>
				</div>

				<div class="of_h mt10" v-if="selCorpCnt == 0">
					<div class="float-left" style="width:15%"><h5>유선전화번호*</h5></div>
					<div class="float-left" style="width:85%">
						<input type="text" class="inputStyle" placeholder="전화번호 ( - 없이 입력 )" v-model="wireTel" :disabled="selCorpCnt > 0">
					</div>
				</div>

				<div class="of_h mt10">
					<div class="float-left" style="width:15%"><h5>첨부파일 *</h5></div>
					<div class="float-left" style="width:85%">
						<div class="float-left" style="width:68%">
							<input type="text" class="inputStyle" placeholder="사업자등록 사본 1부" v-model="attachFileNm" disabled>
							<input type="file" id="attachCorpImg" @change="fnFileChange(0)" ref="attachFile" style="display:none;">
						</div>
						<div class="float-right" style="width:30%">
							<button @click="fnAttachCorpImg(0)" class="btnStyle1 backLightGray" style="min-width:auto; width:100%">파일첨부(필수)</button>
						</div>					
					</div>
				</div>
				<div class="of_h mt10">
					<div class="float-left" style="width:15%"><h5></h5></div>
					<div class="float-left" style="width:85%">
						<div class="float-left" style="width:68%">
							<input type="text" class="inputStyle" placeholder="통신사실 증명원(첨부된 사업자등록증과 같은 명의)" v-model="attachFileNm1" disabled>
							<input type="file" id="attachCorpImg1" @change="fnFileChange(1)" ref="attachFile1" style="display:none;">
						</div>
						<div class="float-right" style="width:30%">
							<button @click="fnAttachCorpImg(1)" class="btnStyle1 backLightGray" style="min-width:auto; width:100%">파일첨부(필수)</button>
						</div>					
					</div>
				</div>
				<div class="of_h mt10">
					<div class="float-left" style="width:15%"><h5></h5></div>
					<div class="float-left" style="width:85%">
						<div class="float-left" style="width:68%">
							<input type="text" class="inputStyle" placeholder="대리인 재직증명서 or 신분증 1부" v-model="attachFileNm2" disabled>
							<input type="file" id="attachCorpImg2" @change="fnFileChange(2)" ref="attachFile2" style="display:none;">
						</div>
						<div class="float-right" style="width:30%">
							<button @click="fnAttachCorpImg(2)" class="btnStyle1 backLightGray" style="min-width:auto; width:100%">파일첨부(필수)</button>
						</div>					
					</div>
				</div>
				<div class="of_h mt10">
					<div class="float-left" style="width:15%"><h5></h5></div>
					<div class="float-left" style="width:85%">
						<div class="float-left" style="width:68%">
							<input type="text" class="inputStyle" placeholder="대리인 위임장 1부" v-model="attachFileNm3" disabled>
							<input type="file" id="attachCorpImg3" @change="fnFileChange(3)" ref="attachFile3" style="display:none;">
						</div>
						<div class="float-right" style="width:30%">
							<button @click="fnAttachCorpImg(3)" class="btnStyle1 backLightGray" style="min-width:auto; width:100%">파일첨부</button>
						</div>					
					</div>
					<div class="float-right color3 mt5" style="width:85%">
						(가능 확장자 : jpg, jpeg, pdf, gif, png, tiff)
					</div>
				</div>
				

				<div class="of_h mt20">
					<div class="float-left" style="width:15%"><h5>영업사원</h5></div>
					<div class="float-right" style="width:85%">
						<div class="float-left" style="width:72%">
							<input type="text" class="inputStyle" title="영업사원" placeholder="영업사원명" v-model="salesMan" @keypress.enter="fnSalesManPopup" @change="salesManId=''">
						</div>
						<div class="float-right" style="width:25%"><a class="btnStyle1 backLightGray" style="min-width:auto; width:100%" @click="fnSalesManPopup">조회</a></div>
						<!-- <select class="selectStyle2" title="영업사원 선택란" v-model="salesMan" style="width:50%">
							<option value="">선택하세요</option>
							<option  v-for="(row, index) in  " :key="index" :value="row.codeVal1"> {{ row.codeName1 }} </option>
						</select> -->
					</div>
					<div class="float-right color3 mt5" style="width:85%">
						영업사원 조회 버튼을 통해 영업사원 팝업 창에서 영업사원을 선택해주세요.
					</div>
				</div>
				<div class="of_h mt10">
					<div class="float-left" style="width:22%"><h5>요금제*</h5></div>
					<div class="float-left" style="width:78%">
						<div class="float-left">
							<input type="radio" name="calling_plan" value="PRE"  v-model="feeType" id="sms_agree03"> <label for="sms_agree03" class="mr30">선불</label>
							<input type="radio" name="calling_plan" value="POST" v-model="feeType" id="sms_agree04"> <label for="sms_agree04">후불</label>
						</div>
						<div class="float-left ml10" v-if="feeType == 'POST'" style="width:25%"><a class="btnStyle1 backLightGray" style="min-width:auto; width:100%" data-toggle="modal" data-target="#information">청구 정보</a></div>	
					</div>					
					<div class="float-right color3 mt5" style="width:78%">메시지 사용료에 대한 요금제를 선택 해 주십시오.<br>
					선불 선택 시 선불 충전 후 메시지 발송이 가능합니다.<br>
					후불 선택 시 청구/납부 정보를 입력하셔야 가입완료 처리 할 수 있습니다.<br>이후 승인과정을 통해 사용이 가능합니다. (1~3일 소요)</div>
				</div>
			</div>

			<!-- <h4 class="mt40">도메인설정</h4>
			<div class="joinBox mt10">
				<div class="of_h mt10">
					<div class="float-left" style="width:20%"><h5>도메인 이름*</h5></div>
					<div class="float-left" style="width:78%">
						<div class="float-left" style="width:72%">
							<input type="text" class="inputStyle" placeholder="영어, 숫자 3자 이상 30자 이내" v-model="domainName" :disabled="this.domainNameChk==true" maxlength="30">
						</div>
						<div class="float-right" style="width:25%">
							<a class="btnStyle1 backLightGray" style="min-width:auto; width:100%" data-toggle="modal" data-target="#save" @click.prevent="domainNameChkBtn">중복체크</a>
						</div>					
					</div>
				</div>
			</div> -->
			<div class="text-center mt70"><a href="#" class="btn btn-login" @click.prevent="signUp">가입완료</a></div>
		</section>
		<chkCorpPopup :popReset="popReset1" :dataList="dataList" :selCorp.sync="selCorp"></chkCorpPopup>
		<addrPopup :popReset="popReset2"  :selAddr.sync="selAddr"></addrPopup>
		<selSalesManPopup :popReset="popReset3" :salesMan="salesMan" :selSalesMan.sync="selSalesMan"></selSalesManPopup>
		<!-- 본인인증 서비스 팝업을 호출하기 위해서는 다음과 같은 form이 필요합니다. -->
		<form name="form_chk" method="post">
			<input type="hidden" name="m" value="checkplusService">						<!-- 필수 데이타로, 누락하시면 안됩니다. -->
			<input type="hidden" name="EncodeData" v-model="sEncData">		<!-- 위에서 업체정보를 암호화 한 데이타입니다. -->
		</form>
		<div class="modal modalStyle" id="information" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body">
						<div>
							<h2>청구정보</h2>
							<hr>
							<h4 class="mt10">월 발송 예상 건수</h4>
							<div class="joinBox mt10">							
								<div class="of_h">
									<div class="float-left" style="width:45%">
										<div class="float-left" style="width:30%"><h5>문자</h5></div>
										<div class="float-left" style="width:70%">
											<input type="text" class="inputStyle" placeholder="발송 건수(숫자만 입력)" v-model="bill.smsExpCnt">
										</div>
									</div>
									<div class="float-right" style="width:45%">
										<div class="float-left" style="width:30%"><h5>RCS</h5></div>
										<div class="float-left" style="width:70%">
											<input type="text" class="inputStyle" placeholder="발송 건수(숫자만 입력)" v-model="bill.rcsExpCnt">
										</div>
									</div>
								</div>
								<div class="of_h mt10">
									<div class="float-left" style="width:45%">
										<div class="float-left" style="width:30%"><h5>카카오</h5></div>
										<div class="float-left" style="width:70%">
											<input type="text" class="inputStyle" placeholder="발송 건수(숫자만 입력)" v-model="bill.kkoExpCnt">
										</div>
									</div>
									<div class="float-right" style="width:45%">
										<div class="float-left" style="width:30%"><h5>Push</h5></div>
										<div class="float-left" style="width:70%">
											<input type="text" class="inputStyle" placeholder="발송 건수(숫자만 입력)" v-model="bill.pushExpCnt">
										</div>
									</div>
								</div>
							</div>

							<div class="of_h mt10">
								<div class="float-left" style="width:22%"><h5>청구서 수신</h5></div>
								<div class="float-left" style="width:78%">
									<input type="radio" name="sms_agree" value="Y" v-model="bill.billKind" id="sms_agree05"> <label for="sms_agree05" class="mr30">이메일</label>
									<input type="radio" name="sms_agree" value="C" v-model="bill.billKind" id="sms_agree06"> <label for="sms_agree06" class="mr30">문자</label>
									<input type="radio" name="sms_agree" value="N" v-model="bill.billKind" id="sms_agree07"> <label for="sms_agree07">우편</label>
								</div>
							</div>
							<div class="of_h mt10">
								<div class="float-left" style="width:22%"><h5>청구 사업자번호*</h5></div>
								<div class="float-left" style="width:78%">
									<input type="text" class="inputStyle" placeholder="사업자번호(-없이 입력)" v-model="bill.billRegNo">
								</div>
							</div>
							<div class="of_h mt10" v-show="bill.billKind == 'C'">
								<div class="float-left" style="width:22%"><h5>청구서 수신 휴대폰*</h5></div>
								<div class="float-left" style="width:78%">
									<input type="text" class="inputStyle" placeholder="휴대폰번호(-없이 입력)" v-model="bill.billPhone">
								</div>
							</div>
							<div class="of_h mt10" v-show="bill.billKind == 'Y'">
								<div class="float-left" style="width:22%"><h5>청구 이메일*</h5></div>
								<div class="float-left" style="width:78%">
									<input type="text" class="inputStyle" placeholder="ex) example@uplus.co.kr" v-model="bill.billEmail">
								</div>
							</div>

							<div class="of_h mt10" v-show="bill.billKind == 'N'">
								<div class="float-left" style="width:22%"><h5>청구 주소*</h5></div>
								<div class="float-left" style="width:78%">
									<div class="float-left" style="width:72%"><input type="text" class="inputStyle" disabled="disabled" v-model="bill.billZip"></div>
									<div class="float-right" style="width:25%"><a class="btnStyle1 backLightGray" style="min-width:auto; width:100%" @click="fnAddrPopup1">우편번호</a></div>	
								</div>
								<div class="float-right mt5" style="width:78%"><input type="text" class="inputStyle" disabled="disabled" v-model="bill.billJuso"></div>
								<div class="float-right mt5" style="width:78%"><input type="text" class="inputStyle" placeholder="상세주소 입력" v-model="bill.billJuso2"></div>
							</div>
							<div class="of_h mt10">
								<div class="float-left" style="width:22%"><h5>납부고객구분*</h5></div>
								<div class="float-left" style="width:78%">
									<select class="selectStyle2" style="width:50%" title="납부고객구분 선택란" v-model="bill.napCustKdCd">
										<option value="">선택하세요</option>
										<option  v-for="(row, index) in napCustKdCdArr" :key="index" :value="row.codeVal1"> {{ row.codeName1 }} </option>
									</select>
								</div>
							</div>
							<div class="of_h mt10">
								<div class="float-left" style="width:22%"><h5>납부방법*</h5></div>
								<div class="float-left" style="width:78%">
									<input type="radio" name="pay02" value="CM" v-model="bill.payMthdCd" id="pay04"> <label for="pay04" class="mr30">자동이체</label>
									<input type="radio" name="pay02" value="CC" v-model="bill.payMthdCd" id="pay05"> <label for="pay05" class="mr30">신용카드</label>
									<input type="radio" name="pay02" value="GR" v-model="bill.payMthdCd" id="pay06"> <label for="pay06">지로</label>
								</div>
							</div>
							<div class="of_h mt10" v-show="bill.payMthdCd != 'GR'">
								<div class="float-left" style="width:22%"><h5>납부일*</h5></div>
								<div class="float-left" style="width:78%">
									<select class="selectStyle2" style="width:50%" title="납부일 선택란" v-model="bill.payDt">
										<option value="">선택하세요</option>
										<option value="18">18일</option>
										<option value="22">22일</option>
										<option value="26">26일</option>
										<option value="LD">말일</option>
									</select>
								</div>
							</div>
							<div class="of_h mt10">
								<div class="float-left" style="width:22%"><h5>납부자명*</h5></div>
								<div class="float-left" style="width:78%">
									<input type="text" class="inputStyle" placeholder="납부자명" v-model="bill.napCmpNm">
								</div>
							</div>
							<div class="of_h mt10">
								<div class="float-left" style="width:22%"><h5>생년월일/<br>사업자번호*</h5></div>
								<div class="float-left" style="width:78%">
									<input type="text" class="inputStyle" placeholder="생년월일(YYYYMMDD)/사업자번호" v-model="bill.napJumin">
								</div>
							</div>
							<div class="of_h mt10" v-show="bill.payMthdCd == 'CM'">
								<div class="float-left" style="width:22%"><h5>은행*</h5></div>
								<div class="float-left" style="width:78%">
									<select class="selectStyle2" style="width:50%" title="은행 선택란" v-model="bill.bankCd">
										<option value="">선택하세요</option>
										<option  v-for="(row, index) in bankCdArr" :key="index" :value="row.codeVal1"> {{ row.codeName1 }} </option>
									</select>
								</div>
							</div>
							<div class="of_h mt10" v-show="bill.payMthdCd == 'CM'">
								<div class="float-left" style="width:22%"><h5>계좌번호*</h5></div>
								<div class="float-left" style="width:78%">
									<input type="text" class="inputStyle" placeholder="계좌번호(- 제외)" v-model="bill.bankNo">
									<a class="btnStyle1 backRed vertical-middle mt10" style="min-width:auto; width:24.5%">계좌 인증</a>
								</div>
							</div>

							<div class="of_h mt10" v-show="bill.payMthdCd == 'CC'">
								<div class="float-left" style="width:22%"><h5>카드종류*</h5></div>
								<div class="float-left" style="width:78%">
									<select class="selectStyle2" style="width:50%" title="카드종류 선택란" v-model="bill.cardCd">
										<option value="">선택하세요</option>
										<option  v-for="(row, index) in cardCdArr" :key="index" :value="row.codeVal1"> {{ row.codeName1 }} </option>
									</select>
								</div>
							</div>
							<div class="of_h mt10" v-show="bill.payMthdCd == 'CC'">
								<div class="float-left" style="width:22%"><h5>카드번호*</h5></div>
								<div class="float-left" style="width:78%">
									<input type="text" class="inputStyle" style="width:24.5%" v-model="bill.cardNo1">
									<input type="text" class="inputStyle" style="width:24.5%" v-model="bill.cardNo2">
									<input type="text" class="inputStyle" style="width:24.5%" v-model="bill.cardNo3">
									<input type="text" class="inputStyle" style="width:24.5%" v-model="bill.cardNo4">
								</div>
							</div>
							<div class="of_h mt10" v-show="bill.payMthdCd == 'CC'">
								<div class="float-left" style="width:22%"><h5>카드유효기간*</h5></div>
								<div class="float-left" style="width:78%">
									<div class="float-left" style="width:50%">
										<div class="float-left" style="width:49%">
											<input type="text" class="inputStyle" placeholder="4자리" v-model="bill.cardValdEndYymm1">
										</div>
										<div class="float-left ml10" style="width:10%"><h5>년</h5></div>
									</div>
									<div class="float-right" style="width:50%">
										<div class="float-left" style="width:49%">
											<input type="text" class="inputStyle" placeholder="2자리" v-model="bill.cardValdEndYymm2">
										</div>
										<div class="float-left ml10" style="width:10%"><h5>월</h5></div>
									</div>
									<a class="btnStyle1 backRed vertical-middle mt10" style="min-width:auto; width:24.5%">카드 인증</a>
								</div>
							</div>
							<div class="joinBox mt10" v-show="bill.payMthdCd != 'GR'">	
								<div class="checks">
									<input type="checkbox" id="chkID" class="checkStyle2" value="아이디 저장"><label for="chkID">본인은 서비스 이용요금을 매월 본인 또는 법인의 계좌나 카드에서 지정된 출금일 또는<br>결제일에 인출 하는 것에 동의합니다.<br>
									본인은 LG유플러스가 이용요금 청구 및 수납을 위해 뒷면에 기재된 개인정보 활용동의서의 내용에 따라 위의 정보를 수집 후 이용 또는 정보 위탁하는 것에 동의한다.</label>	
								</div>
							</div>

							<div class="text-center mt40">
								<a class="btnStyle3 black font14">확인</a>
								<a class="btnStyle3 white font14" data-dismiss="modal">닫기</a>						
							</div>
							<addrPopup1 :popReset="popReset4"  :selAddr.sync="selAddr1"></addrPopup1>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import confirm from "@/modules/commonUtil/service/confirm.js";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import signUpApi from "@/modules/signUp/service/api"
import chkCorpPopup from "@/modules/signUp/components/bp-selectCorpPopup"
import selSalesManPopup from "@/modules/signUp/components/bp-selectSaleMan"
import addrPopup from "@/modules/signUp/components/bp-addrPopup"
import addrPopup1 from "@/modules/signUp/components/bp-addrPopup1"

import customereApi from "@/modules/customer/service/customerApi.js";
import axios from 'axios'

export default {
	components: {
		//modal
		chkCorpPopup,
		addrPopup,
		addrPopup1, // 청구정보의 주소팝업, 팝업 내에서 팝업 호출시 뒤로 숨겨지는 현상때문에 재생성 
		selSalesManPopup
	},
	props: {
    	authKey : String,
		require : true
  	},
	data() {
		return {
			loginId : "",				// 아이디
			userNm : "",
			password : "",				// 비밀번호
			passwordChk : "",			// 비밀번호 체크
			// smsCertifyYn : "Y",			// sms 인증여부
			phoneCerti : "",			// 담당자 휴대폰 인증 전화번호
			phoneCertiChk : false,		// 담당자 휴대폰 인증 TF (모듈 추가후 defalte false로 변경해야됨)
			regno : "",					// 사업자번호
			corpNm : "",				// 사업자명
			ceoNm : "",					// 대표자명
			rtnCustNm : "",		
			busiType : "",				// 업태
			busiClass : "",				// 종목
			woplaceAddress : "",		// 주소
			woplaceAddressDetail : "",	// 상세주소
			wireTel : "",				// 유선 전화번호
			attachFileNm : "",			// 사업자등록증
			attachFileNm1 : "",			// 통신사실 증명원
			attachFileNm2 : "",			// 대리인 재직증명서 or 신분증
			attachFileNm3 : "",			// 대리인 위임장
			// domainName : "",			// 도메인 이름
			// domainNameChk : "N",		// 도메인 이름 TF
			custAddrZip : "",			// 우편번호
			custKdCd : "",				// 고객 유형
			gender : "",				// 성별
			coInfo : "",				// ci 값
			promotionYn : "",			// 홍보성 정보 동의 여부

			selCorp : {},				// 선택한 고객사 정보
			selAddr : {},				// 선택한 주소 정보
			selAddr1 : {},				// 선택한 주소 정보

			popReset1 : 0,				// 팝업 초기화할 num
			popReset2 : 0,				// 팝업 초기화할 num
			popReset3 : 0,				// 팝업 초기화할 num
			popReset4 : 0,				// 팝업 초기화할 num
			custNo : '',				// 고객번호
			custrnmNo : '',				// 고객식별번호
			selRegno : '',				// 사업자 등록 번호(input hidden)
			selCorpCnt : 1,				// 사업자 번호로 조회한 고객사의 수

			sEncData : '', 				// 나이스본인인증 암호화 정보
			dataList : [],
			
			custTypeArr : [],			// 고객 유형 select box
			
			selSalesMan : {},			// 선택한 영업사원
			salesMan : "",
			salesManId : "",
			feeType : 'PRE',
			bill : {
				  billRegNo : ''
				, billType : ''
				, billName : ''
				, billStatus : ''
				, napCustKdCd : ''
				, billKind : 'Y'
				, billEmail : ''
				, billPhone : ''
				, billZip : ''
				, billJuso : ''
				, billJuso2 : ''
				, payMthdCd : 'CM'
				, payDt : ''
				, napCmpNm : ''
				, napJumin : ''
				, bankCd : ''
				, bankNo : ''
				, cardCd : ''
				, cardNo1 : ''
				, cardNo2 : ''
				, cardNo3 : ''
				, cardNo4 : ''
				, cardValdEndYymm1 : ''
				, cardValdEndYymm2 : ''
				, serviceId : ''
				, smsExpCnt : null
				, rcsExpCnt : null
				, kkoExpCnt : null
				, pushExpCnt : null
				, monthExpAmount : 0
				, handleReason : ''
				, handleId : ''
				, handleDt : ''
			},
			napCustKdCdArr : [],
			bankCdArr : [],
			cardCdArr : []
		}
	},
	// 도메인 이름 영어(소문자), 숫자만 입력 가능하도록 처리
	// 핸드폰번호 사업자번호 숫자만 입력 가능하도록 처리
	watch: {
		// domainName : function() {
		// 	return this.domainName = this.domainName.replace(/[^a-z0-9]/g, '');
		// },
		phoneCerti : function() {
			return this.phoneCerti = this.phoneCerti.replace(/[^0-9]/g, '');
		},
		regno : function() {
			return this.regno = this.regno.replace(/[^0-9]/g, '');
		},
		wireTel : function() {
			return this.wireTel = this.wireTel.replace(/[^0-9]/g, '');
		},
		selCorp(){
			this.fnSetCorpInfo();
		},
		selAddr(){
			this.fnSetCorpAddr();
		},
		selSalesMan(){
			this.fnSetSalesMan();
		}
	},
	mounted() {
		this.fnChkMailCertify();
	},
	methods: {
		fnChkMailCertify(){
			var params = {
				authKey : this.authKey
			};

			signUpApi.certifyMailByAuthKey(params).then(response => {
				var result = response.data;
				var vm = this;
				if(result.success){
					this.loginId = result.data.email;
					this.promotionYn = result.data.promotionYn;
					this.fnGetNiceCheck();		// nice 본인인증 인증키 조회
					this.fnGetCode();		// 고객사 코드 값 조회
				} else {
					vm.$router.push({name : "chkCertifyFail"});
				}
			})
		},
		fnGetCode(){
			var params1 = {
				codeTypeCd	: "CORP_CUST_TYPE",
				useYN		: "Y"
			};
			customereApi.selectCodeList(params1).then(response =>{
				var result = response.data;
				if(result.success){
					if(result.data.length > 0){
						this.custTypeArr = result.data;
					}
				}
			});
			var params2 = {
				codeTypeCd	: "NAP_CUST_KD",
				useYN		: "Y"
			};
			customereApi.selectCodeList(params2).then(response =>{
				var result = response.data;
				if(result.success){
					if(result.data.length > 0){
						this.napCustKdCdArr = result.data;
					}
				}
			});
			var params3 = {
				codeTypeCd	: "BANK",
				useYN		: "Y"
			};
			customereApi.selectCodeList(params3).then(response =>{
				var result = response.data;
				if(result.success){
					if(result.data.length > 0){
						this.bankCdArr = result.data;
					}
				}
			});
			var params4 = {
				codeTypeCd	: "CARD",
				useYN		: "Y"
			};
			customereApi.selectCodeList(params4).then(response =>{
				var result = response.data;
				if(result.success){
					if(result.data.length > 0){
						this.cardCdArr = result.data;
					}
				}
			});
		},
		signUp () {
			// TODO : 개발 이후 Validation 처리
			// 기초 validation 처리
			// var defaultVali = true;
			// defaultVali = this.defaultVali();
			// if(!defaultVali) return false;

			// 신규 고객사 validation
			// if (this.selRegno == ""){
			// 	var custVali = true;
			// 	custVali = this.custVali();
			// 	if(!custVali) return false;
			// } 

			// var regno =  this.$gfnCommonUtils.isEmpty(this.selRegno) ? this.regno : this.selRegno;
			
			// // 사업자 번호 유효성 검사
			// var regnoVali = this.regnoVali(regno);
			// if(!regnoVali) return false;

			eventBus.$on('callbackEventBus', this.signUpSubmit);
     		confirm.fnConfirm( "", "가입하시겠습니까?", "확인");
		},
		// 기초 validation 처리
		defaultVali() {
			// 비밀번호 정책 validation
			// var reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,20}$/;
			var numReg = /^(?=.*?[a-z])(?=.*?[0-9]).{10,16}$/;
			var engReg = /^(?=.*?[a-z])(?=.*?[A-Z]).{10,16}$/;
			var speReg = /^(?=.*?[a-z])(?=.*?[?!@#$%^&*+=-_|,.]).{10,16}$/;

			var numReg2 = /^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[?!@#$%^&*+=-_|,.]).{8,16}$/;
			var engReg2 = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[?!@#$%^&*+=-_|,.]).{8,16}$/;
			var speReg2 = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[A-Z]).{8,16}$/;

			if(this.userNm == ""){
				confirm.fnAlert("", "이름을 입력해주세요.");
				return false;
			} else if(this.password == "" || this.passwordChk == ""){
				confirm.fnAlert("", "비밀번호를 입력해주세요.");
				return false;
			} else if (!numReg.test(this.password) && !engReg.test(this.password)&& !speReg.test(this.password)
						&& !numReg2.test(this.password) && !engReg2.test(this.password) && !speReg2.test(this.password)){
				confirm.fnAlert("", "비밀번호는 대/소문자, 숫자, 특수문자 중 2가지 이상을 조합하여 10~16자리\n또는 3가지 이상을 조합하여 8~16자리로 구성해주세요.\n(소문자 필수 입력)");
				return;
			} else if (this.password != this.passwordChk) {
				confirm.fnAlert("", "비밀번호가 일치하지 않습니다.");
				this.password = "";
				this.passwordChk = "";
				return false;
			} else if (this.phoneCert == "" || this.phoneCertiChk == false) {
				confirm.fnAlert("", "담당자 휴대폰 인증을 진행해 주세요.");
				return false;
			} else if (this.regno == "") {
				confirm.fnAlert("", "사업자 등록번호를 입력해주세요.");
				return false;
			} else if (this.attachFileNm == "") {
				confirm.fnAlert("", "사업자등록증 파일을 첨부해주세요.");
				return false;
			} else if (this.attachFileNm1 == "") {
				confirm.fnAlert("", "통신사실 증명원 파일을 첨부해주세요.");
				return false;
			} else if (this.attachFileNm2 == "") {
				confirm.fnAlert("", "대리인 재직증명서 또는 신분증 파일을 첨부해주세요.");
				return false;
			} else if (this.salesManId == "" && this.salesMan != "") {
				confirm.fnAlert("", "영업사원명을 수정하신 경우, 영업사원 조회 팝업에서 다시 선택해주세요.\n영업사원을 기본값으로 등록하시려면 빈칸으로 입력해주세요.");
				return false;
			} else {
				return true;
			}
		},
		// 신규 고객사 validation
		custVali(){
			if(this.custKdCd == ""){
				confirm.fnAlert("", "고객 유형을 선택해주세요.");
				return false;
			}
			if(this.custrnmNo != ""){
				if(this.custKdCd == "II" || this.custKdCd == "GEX"){
					var regex = /^(19[0-9][0-9]|20\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/;
					if(!regex.test(this.custrnmNo)) {
						confirm.fnAlert("", "개인 사업자의 생년월일(YYYYMMDD) 형식으로 입력해주세요.");
						return false;
					}
				} 
			}
			if (this.corpNm == "") {
				confirm.fnAlert("", "사업자명을 입력해주세요.");
				return false;	
			}
			if (this.rtnCustNm == "") {
				confirm.fnAlert("", "대표자명을 입력해주세요.");
				return false;
			}
			if (this.busiType == "") {
				confirm.fnAlert("", "업태를 입력해주세요.");
				return false;
			}
			if (this.busiClass == "") {
				confirm.fnAlert("", "종목을 입력해주세요.");
				return false;
			}
			if (this.custAddrZip == "") {
				confirm.fnAlert("", "우편번호를 입력해주세요.");
				return false;
			}
			if (this.woplaceAddress == "") {
				confirm.fnAlert("", "주소를 입력해주세요.");
				return false;
			}
			if (this.woplaceAddressDetail == "") {
				confirm.fnAlert("", "상세 주소를 입력해주세요.");
				return false;
			}
			if (this.wireTel == "") {
				confirm.fnAlert("", "유선 전화번호를 입력해주세요.");
				return false;
			}
			return true;
		},


		// 사업자 등록번호 validation
		regnoVali(value) {
			var valueMap = value.replace(/-/gi, '').split('').map(function(item) {
				return parseInt(item, 10);
			});

			if (valueMap.length === 10) {
				// var multiply = new Array(1, 3, 7, 1, 3, 7, 1, 3, 5);
				// var checkSum = 0;

				// for (var i = 0; i < multiply.length; ++i) {
				// 	checkSum += multiply[i] * valueMap[i];
				// }

				// checkSum += parseInt((multiply[8] * valueMap[8]) / 10, 10);

				// return Math.floor(valueMap[9]) === (10 - (checkSum % 10));
			} else {
				confirm.fnAlert("","사업자번호(10자리)를 정확히 입력해주세요.");
				return false;
			}
			return true;
		},

		// 도메인 중복 체크 function
		domainNameChkBtn() {
			var vm = this;
			var params = {
				"domainName": this.domainName
			}
			var domainName = vm.domainName;

			// 도메인 이름 3자 미만 validation 처리
			if(3>domainName.length) {
				confirm.fnAlert("", "도메인 이름은 3자 이상 30자 이내만 가능합니다.");
				return false;
			}
			
			// 도메인 이름 확인후 있으면 alert 없으면 domainNameChk 값 변경후 input disabled 처리
			signUpApi.domainChk(params).then(function(response) {
				var domainChkCnt = response.data;
				if(domainChkCnt>0) {
					confirm.fnAlert("", "이미 사용중인 도메인 이름입니다. 다시 입력해 주세요.");
				} else {
					confirm.fnAlert("", "사용가능한 도메인 이름입니다.");
					vm.domainNameChk = true;
				}
			});
		},
		async signUpSubmit() {
			var fd = new FormData();
			fd.append('loginId', this.loginId);								// 아이디
			fd.append('userNm', this.userNm);								// 이름
			fd.append('password', this.password);							// 비밀번호
			// fd.append('smsCertifyYn', this.smsCertifyYn);					// sms 인증 여부
			fd.append('phoneCerti', this.phoneCerti);						// 휴대폰 번호
			fd.append('regno', this.$gfnCommonUtils.isEmpty(this.selRegno) ? this.regno : this.selRegno);								// 사업자번호
			fd.append('custKdCd', this.custKdCd)							// 고객유형
			fd.append('custNo', this.custNo);								// 고객번호
			fd.append('corpNm', this.corpNm);								// 사업명
			fd.append('ceoNm', this.$gfnCommonUtils.isEmpty(this.ceoNm) ? this.rtnCustNm : this.ceoNm);									// 사업자 명
			fd.append('busiType', this.busiType);							// 업태
			fd.append('busiClass', this.busiClass);							// 업종
			fd.append('zipCode', this.custAddrZip);							// 우편번호
			fd.append('woplaceAddress', this.woplaceAddress);				// 주소
			fd.append('woplaceAddressDetail', this.woplaceAddressDetail);	// 상세주소
			fd.append('wireTel', this.wireTel);								// 유선 전화
			fd.append('attachFile', this.$refs.attachFile.files[0]);		// 사업자 등록증
			fd.append('attachFile1', this.$refs.attachFile1.files[0]);		// 통신사실 증명원
			fd.append('attachFile2', this.$refs.attachFile2.files[0]);		// 대리인 재직증명서 또는 신분증
			fd.append('attachFile3', this.$refs.attachFile3.files[0]);		// 대리인 위임장
			// fd.append('domainName', this.domainName);						// 도메인 주소
			fd.append('custrnmNo', this.custrnmNo);							// 고객 식별번호
			fd.append('coInfo', this.coInfo);								// 본인인증 토큰 (개인사업자 필수)
			fd.append('genderCode', this.gender);							// 성별 (1: 남성 / 2: 여성)
			fd.append('promotionYn', this.promotionYn);						// 홍보성 정보 수신 동의
			fd.append('salesMan', this.salesManId);							// 영업사원
			fd.append('feeType', this.feeType);

			fd.append('billRegNo', this.bill.billRegNo);
			fd.append('billType', this.bill.billType);        
			fd.append('billName', this.bill.billName);        
			fd.append('billStatus', this.bill.billStatus);      
			fd.append('napCustKdCd', this.bill.napCustKdCd);     
			fd.append('billKind', this.bill.billKind);        
			fd.append('billEmail', this.bill.billEmail);       
			fd.append('billPhone', this.bill.billPhone);       
			fd.append('billZip', this.bill.billZip);         
			fd.append('billJuso', this.bill.billJuso);        
			fd.append('billJuso2', this.bill.billJuso2);       
			fd.append('payMthdCd', this.bill.payMthdCd);       
			fd.append('payDt', this.bill.payDt);           
			fd.append('napCmpNm', this.bill.napCmpNm);        
			fd.append('napJumin', this.bill.napJumin);        
			fd.append('bankCd', this.bill.bankCd);          
			fd.append('bankNo', this.bill.bankNo);          
			fd.append('cardCd', this.bill.cardCd);          
			fd.append('cardNo1', this.bill.cardNo1);
			fd.append('cardNo2', this.bill.cardNo2);
			fd.append('cardNo3', this.bill.cardNo3);
			fd.append('cardNo4', this.bill.cardNo4);
			fd.append('cardValdEndYymm1', this.bill.cardValdEndYymm1);
			fd.append('cardValdEndYymm2', this.bill.cardValdEndYymm2); 
			fd.append('serviceId', this.bill.serviceId);       
			fd.append('smsExpCnt', this.bill.smsExpCnt);       
			fd.append('rcsExpCnt', this.bill.rcsExpCnt);       
			fd.append('kkoExpCnt', this.bill.kkoExpCnt);       
			fd.append('pushExpCnt', this.bill.pushExpCnt);      
			fd.append('monthExpAmount', this.bill.monthExpAmount);  
			fd.append('handleReason', this.bill.handleReason);    
			fd.append('handleId', this.bill.handleId);        
			fd.append('handleDt', this.bill.handleDt);     

			await axios.post('/api/public/signUp/insertSignUp',
				fd,
				{
					headers: {
					'Content-Type': 'multipart/form-data'
				}
			}).then( response => {
				var result = response.data;
				if(!result.success) {
				confirm.fnAlert("", result.message);
				} else {
					eventBus.$on('callbackEventBus', this.fnEmitLoginPage);
     				confirm.fnConfirm( "", "가입완료하였습니다. 확인 창을 누르면 로그인 창으로 이동합니다.", "확인");
				}
			}).catch(function () {
				confirm.fnAlert("", result.message);
			});
		},
		fnEmitLoginPage(){
			this.$router.push({name : "login"});
		},
		fnSelCorp(){
			// TODO : 개발 이후 Validation 처리
			// if(!this.phoneCertiChk){
			// 	confirm.fnAlert("","휴대폰 본인인증을 완료해주세요.");
			// 	return false;
			// }

			if(this.regno == ""){
				confirm.fnAlert("","사업자번호는 필수 입력입니다.");
				return false;
			}
			// 사업자번호 입력 후 조회하여 고객사 선택 고객사가 있으면 고객사 관련 정보 disabled 처리
			var params = {
				regno : this.regno
			}
			
			var vm = this;
			signUpApi.selectCorpCustList(params).then(response => {
				var result = response.data;
				if(result.success){
					var cnt = result.data.length;
					if(cnt > 0){
						vm.popReset1 += 1;
						vm.selCorpCnt = cnt;
						vm.selCorp = {};
						vm.dataList = result.data;

						jQuery("#chkCorpPopup").modal("show");
					} else {
						// 선택할 수 있는 고객사가 없는 경우 고객번호 제외 입력 가능
						confirm.fnAlert("","신규 고객사 정보를 입력해주세요.");
						vm.selCorpCnt = 0;
						vm.selCorp = {};
					}
				} else{
					confirm.fnAlert("", result.message);
				}
			});
		},
		fnSetCorpInfo(){
			this.custNo					= this.selCorp.custNo == undefined ? "" : this.selCorp.custNo;							// 고객번호
			this.custrnmNo				= this.selCorp.custrnmNo == undefined ? "" : this.selCorp.custrnmNo;					// 고객 식별번호
			this.ceoNm					= this.selCorp.custNm == undefined ? "" : this.selCorp.custNm;						// 대표자 명
			this.rtnCustNm				= this.selCorp.rtnCustNm == undefined ? "" : this.selCorp.rtnCustNm;
			this.corpNm					= this.selCorp.bizCompNm == undefined ? "" : this.selCorp.bizCompNm;					// 사업자명
			this.selRegno				= this.selCorp.bsRegNo == undefined ? "" : this.selCorp.bsRegNo;						// 선택한 사업자 번호(hidden)
			this.custAddrZip			= this.selCorp.custAddrZip == undefined ? "" : this.selCorp.custAddrZip;				// 우편번호
			this.woplaceAddress			= this.selCorp.custVilgAbvAddr == undefined ? "" : this.selCorp.custVilgAbvAddr;		// 주소1
			this.woplaceAddressDetail	= this.selCorp.custVilgBlwAddr == undefined ? "" : this.selCorp.custVilgBlwAddr;		// 상세주소
			this.wireTel				= this.selCorp.wireTel == undefined ? "" : this.selCorp.wireTel;						// 유선 전화번호
			this.busiType				= this.selCorp.bsstNm == undefined ? "" : this.selCorp.bsstNm;							// 업태
			this.busiClass				= this.selCorp.inkndNm == undefined ? "" : this.selCorp.inkndNm;						// 업종
			this.custKdCd				= this.selCorp.custKdCd == undefined ? "" : this.selCorp.custKdCd;						// 고객유형
		},
		// 본인인증 팝업
		fnPopup(){
				window.open('', 'popupChk', 'width=400, height=705, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
				document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
				document.form_chk.target = "popupChk";
				document.form_chk.submit();
		},
		// 본인인증시 사용될 데이터 조회
		fnGetNiceCheck(){
			let sEcnDataVal = '';

			signUpApi.getNiceCheck().then((response) => {
				var result = response.data;
				if(result.success){
					sEcnDataVal = result.data.sEncData;
					this.sEncData = sEcnDataVal;
				} else {
					confirm.fnAlert("", result.message);
				}
			});
		},
		// 본인인증 성공시 본인인증시 사용한 핸드폰 번호 입력
		fnGetNiceCheckInfo(){
			confirm.fnAlert("본인인증 성공", "본인인증에 성공하였습니다.");
			this.phoneCerti = jQuery("#phoneCerti").val();
			this.gender = jQuery("#gender").val();
			this.coInfo = jQuery("#coInfo").val();
			this.userNm = jQuery("#userNm").val();
			this.phoneCertiChk = true;
		},
		// 주소 조회 팝업
		fnAddrPopup(){
			if(this.selCorpCnt != 0){
				confirm.fnAlert("", "고객사를 신규로 입력하실 경우에만 사용 가능합니다.");
				return false;
			};
			this.popReset2 += 1;
			jQuery("#addrPopup").modal("show");
		},
		// 주소 조회 팝업
		fnAddrPopup1(){
			this.popReset4 += 1;
			jQuery("#addrPopup1").modal("show");
		},
		// 선택한 주소 set
		fnSetCorpAddr(){
			this.custAddrZip = this.selAddr.mailNo;
			this.woplaceAddress = this.selAddr.roadMailFullAddr + " " + this.selAddr.roadMailFullAddr2;
		},
		// 사업자 등록증 입력
		fnAttachCorpImg(num){
			if(num == 0) {
				jQuery("#attachCorpImg").click();
			} else if(num == 1) {
				jQuery("#attachCorpImg1").click();
			} else if(num == 2) {
				jQuery("#attachCorpImg2").click();
			} else if(num == 3) {
				jQuery("#attachCorpImg3").click();
			}
		},
		fnFileChange(num) {

			if(num == 0) {
				var attachFileNm = this.$refs.attachFile.files[0].name;
				// 확장자 valid
				var ext = attachFileNm.slice(attachFileNm.lastIndexOf(".") + 1).toLowerCase();
				if(!(ext && /^(jpg|jpeg|pdf|gif|png|tiff)$/.test(ext))) {
					confirm.fnAlert("", "필수 첨부서류들을 압축하여 등록해주세요.\n(가능 확장자 : jpg, jpeg, pdf, gif, png, tiff)");
					return false;
				}
				this.attachFileNm = attachFileNm;
			} else if(num == 1) {
				var attachFileNm1 = this.$refs.attachFile1.files[0].name;
				// 확장자 valid
				var ext = attachFileNm1.slice(attachFileNm1.lastIndexOf(".") + 1).toLowerCase();
				if(!(ext && /^(jpg|jpeg|pdf|gif|png|tiff)$/.test(ext))) {
					confirm.fnAlert("", "필수 첨부서류들을 압축하여 등록해주세요.\n(가능 확장자 : jpg, jpeg, pdf, gif, png, tiff)");
					return false;
				}
				this.attachFileNm1 = attachFileNm1;
			} else if(num == 2) {
				var attachFileNm2 = this.$refs.attachFile2.files[0].name;
				// 확장자 valid
				var ext = attachFileNm2.slice(attachFileNm2.lastIndexOf(".") + 1).toLowerCase();
				if(!(ext && /^(jpg|jpeg|pdf|gif|png|tiff)$/.test(ext))) {
					confirm.fnAlert("", "필수 첨부서류들을 압축하여 등록해주세요.\n(가능 확장자 : jpg, jpeg, pdf, gif, png, tiff)");
					return false;
				}
				this.attachFileNm2 = attachFileNm2;
			} else if(num == 3) {
				var attachFileNm3 = this.$refs.attachFile3.files[0].name;
				// 확장자 valid
				var ext = attachFileNm3.slice(attachFileNm3.lastIndexOf(".") + 1).toLowerCase();
				if(!(ext && /^(jpg|jpeg|pdf|gif|png|tiff)$/.test(ext))) {
					confirm.fnAlert("", "필수 첨부서류들을 압축하여 등록해주세요.\n(가능 확장자 : jpg, jpeg, pdf, gif, png, tiff)");
					return false;
				}
				this.attachFileNm3 = attachFileNm3;
			}
		},
		// 영업사원 조회 팝업
		fnSalesManPopup(){
            if(this.$gfnCommonUtils.isEmpty(this.salesMan)){
                confirm.fnAlert("","영업사원의 이름을 입력해주세요.");
                return false;
            }

			this.popReset3 += 1;
			jQuery("#saleManPopup").modal("show");
		},
		fnSetSalesMan(){
			this.salesManId = this.selSalesMan.codeVal1;
			this.salesMan = this.selSalesMan.codeName1;
		}
	}
}
</script>