<template autocomplete="off">
  <div class="row row-no-margin">
    <div class="contentHeader">
      <h2>통합 발송</h2>
      <a href="#self" class="btnStyle2 backPink absolute top0 right0" onClick="window.location.reload()" title="이용안내">이용안내 <i class="fal fa-book-open"></i></a>
    </div>

	<!-- 본문 -->
	<div class="row">
		<div class="phone3 inline-block" style="width:30%">
			<div class="phoneFixed">
				<!-- phoneWrap -->
				<!-- PUSH -->
		          <div v-if="channelType == 'PUSH'" class="phoneWrap">
		            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
		            <div class="phoneTextWrap scroll-y">
						<div class="phoneText1">
              <p v-if="fnIsEmpty(rowData.pushTitle)">제목</p>
              <p v-else>{{rowData.pushTitle}}</p>
						</div>		            
            <div v-if="rowData.msgType == 'IMAGE' && fnIsEmpty(rowData.pushImgInfo.imgUrl)" class="phoneText2 mt10 text-center" style="padding:65px">
              <i class="fas fa-image-polaroid" style="font-size:38px; color:#D5D5D5"></i>
              <p class="font-size14 color3 mt15">이미지 영역</p>
            </div>
            <div v-if="rowData.msgType == 'IMAGE' && !fnIsEmpty(rowData.pushImgInfo.imgUrl)" class="phoneText2 mt10 text-center"
              :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.pushImgInfo.imgUrl+');'">
            </div>
            <div>
              <p class="font-size14 color4 mt10">
                <span v-html="$gfnCommonUtils.newLineToBr(rowData.pushContent)"></span>
                <br v-if="!fnIsEmpty(rowData.pushContent)"/>
                <span v-html="rowData.msgKind == 'A' ? rowData.pushHowToDenyReceipt : ''"></span>
              </p>
            </div>
<!--
		              <div class="phoneText1">
		                <p v-if="this.$gfnCommonUtils.isEmpty(rowData.pushTitle)">제목</p>
		                <p v-else>{{rowData.pushTitle}}</p>
		              </div>
		              <div v-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.pushImgInfo.imgUrl)" class="phoneText2 mt10 text-center" style="padding:65px">
		                <i class="fas fa-image-polaroid" style="font-size:38px; color:#D5D5D5"></i>
		                <p class="font-size14 color3 mt15">이미지 영역</p>
		              </div>
		              <div v-if="rowData.msgType == 'IMAGE' && !this.$gfnCommonUtils.isEmpty(rowData.pushImgInfo.imgUrl)" class="phoneText2 mt10 text-center"
		                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.pushImgInfo.imgUrl+');'">
		              </div>
		              <div class="scroll-y">
		                <pre v-if="this.$gfnCommonUtils.isEmpty(rowData.pushContent)" class="font-size14 color4 mt10">내용</pre>
		                <pre v-else class="font-size14 color4 mt10">{{rowData.pushContent}}</pre>
		              </div>
-->
		            </div>
		          </div>
		        <!--// PUSH -->

		        <!-- SMS -->
		          <div v-if="channelType == 'SMSMMS' && previewMessageType == 'SMS'" class="phoneWrap">
		            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
					<div class="phoneTextWrap scroll-y">
						<div class="phoneText1">
              <p class="font-size14 color4 mt10">
                <span v-html="$gfnCommonUtils.newLineToBr(rowData.smsContent)"></span>
              </p>
							<!-- <pre>11{{rowData.smsContent}}22</pre> -->
						</div>
					</div>
		          </div>
		        <!--// SMS -->

		        <!-- MMS -->
		          <div v-if="channelType == 'SMSMMS' && previewMessageType == 'MMS'" class="phoneWrap">
		            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
					<div class="phoneTextWrap scroll-y4">
            <div class="phoneText2 mb10">
              <p v-if="$gfnCommonUtils.isEmpty(rowData.smsTitle)">메시지 제목</p>
              <p v-else>{{rowData.smsTitle}}</p>
            </div>
            <div>
              <div v-for="(imgInfo, idx) in rowData.smsImgInfoList" 
                :key="idx" 
                class="phoneText2 mt10 text-center"
                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+imgInfo.imgUrl+');'">
              </div>
            </div>
						<div class="phoneText1">
							<p class="font-size14 color4 mt10">
                <span v-html="$gfnCommonUtils.newLineToBr(rowData.smsContent)"></span>
              </p>
						</div>
					</div>
		          </div>
		        <!--// MMS -->
		        
		        <!-- RCS -->
		        	<!-- free -->
			          <div v-if="channelType == 'RCS'  && previewRcsMessageType == 'FREE'" class="phoneWrap">
			          	<img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div class="phoneText1 scroll-y2">
								<pre>{{rowData.rcs0Content}}</pre>
							</div>
						</div>
			          </div>
			    	<!-- //free -->
			    	
		        	<!-- description -->
			          <div v-if="channelType == 'RCS'  && previewRcsMessageType == 'DESCRIPTION'" class="phoneWrap">
			            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div class="phoneText1 relative scroll-y4">
								<p><img src="@/assets/images/common/phone_Icon10.png" alt="주문 아이콘"></p>
								<div class="scroll-y5">
									<pre class="mt15 lc-1">{{rowData.rcs1Content}}</pre>
								</div>
								<div class="absolute" style="bottom:25px; left:85px">
									<pre class="text-center mt20" style="color:#69C8FF">{{rowData.rcs1Content}}</pre>
								</div>
							</div>
						</div>
			          </div>
			    	<!-- //description -->			    	
			    			        
		        	<!-- style -->
			          <div v-if="channelType == 'RCS'  && previewRcsMessageType == 'CELL'" class="phoneWrap">
						<img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div class="phoneText1 of_h">
								<p><img src="@/assets/images/common/phone_Icon08.png" alt="인증 아이콘"></p>
								<div class="scroll-y">
									<p class="mt15 lc-1">인증번호 안내</p>
								</div>
								<p class="mt10 lc-1 inline-block">인증번호</p>
								<p class="mt10 lc-1 inline-block float-right"></p>
								<p class="text-center mt30" style="color:#69C8FF">홈페이지 연결하기</p>
								<p class="text-center mt10" style="color:#69C8FF">인증번호 복사하기</p>
							</div>
						</div>
			          </div>
			    	<!-- //style -->

			        <!-- SMS -->
			          <div v-if="channelType == 'RCS'  && previewRcsMessageType == 'SMS'" class="phoneWrap">
			            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div class="phoneText1 scroll-y2">
								<pre>{{rowData.rcsSMSContent}}</pre>
							</div>
						</div>
			          </div>
			        <!--// SMS -->
		        			    				    			        
			        <!-- LMS -->
			          <div v-if="channelType == 'RCS'  && previewRcsMessageType == 'LMS'" class="phoneWrap">
			            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div class="phoneText1 scroll-y4">
								<pre>{{rowData.rcsLMSContent}}</pre>
							</div>
						</div>
			          </div>
			        <!--// LMS -->
			        		        			    				    			        
		        	<!-- short -->
			          <div v-if="channelType == 'RCS'  && previewRcsMessageType == 'SHORT'" class="phoneWrap">
			            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div v-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcsShortImgInfoList[0])" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg2_2.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcsShortImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg2_2.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && !this.$gfnCommonUtils.isEmpty(rowData.rcsShortImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
				                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcsShortImgInfoList[0].imgUrl+');'">
				            </div>
							<div style="background:#fff; border-radius: 0 0 5px 5px; min-height:180px" class="pd20">
								<h5>{{rowData.rcsShortTitle}}</h5>
								<div class="scroll-y3">
									<pre class="color6">{{rowData.rcsShortContent}}</pre>
								</div>								
							</div>
						</div>
			          </div>
			    	<!-- //short -->	
			    	<!-- tall -->
			          <div v-if="channelType == 'RCS'  && previewRcsMessageType == 'TALL'" class="phoneWrap">
			            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div v-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcsTallImgInfoList[0])" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcsTallImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
				                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
				            </div>
				            <div v-else-if="rowData.msgType == 'IMAGE' && !this.$gfnCommonUtils.isEmpty(rowData.rcsTallImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
				                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcsTallImgInfoList[0].imgUrl+');'">
				            </div>
							<div style="background:#fff; border-radius: 0 0 5px 5px; min-height:170px" class="pd20">
								<h5>{{rowData.rcsTallTitle}}</h5>
								<div class="scroll-y6">
									<pre class="color6">{{rowData.rcsTallContent}}</pre>
								</div>								
							</div>
						</div>
			          </div>
			    	<!-- //tall -->

		        	<!-- cshort -->
					<div v-if="channelType == 'RCS'  && previewRcsMessageType == 'CSHORT'" class="phoneWrap" style="height:660px">
						<img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneCardWrap">
							<p class="color000">[WEB발신] (광고)</p>
							<ul class="cardBxslider mt10">
								<li class="slide cardBox" v-show="rcs9TabFlag === 'STAB1'">
						            <div v-for="imgIdx in rcs90ImgLimitSize" :key="imgIdx">  
						            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs90ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
							                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs90ImgInfoList[imgIdx-1].imgUrl+');'">  
						            		</div>
						            		<div v-else>
						            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            		</div>
						            </div>
				            		<!--						
									<div v-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs90ImgInfoList[0])" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs90ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && !this.$gfnCommonUtils.isEmpty(rowData.rcs90ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
						                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs90ImgInfoList[0].imgUrl+');'">
						            </div>-->
									<div class="relative">
										<div class="scroll-y" style="min-height:150px">
											<p class="color000 font-size13">{{rowData.rcs90Title}}</p>
											<pre class="color3 mt5">{{rowData.rcs90Content}}</pre>
										</div>
										<!--<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs9HowToDenyReceipt}}</p>-->
										<p class="color4 font-size10 absolute" >무료수신거부:{{rowData.rcs9HowToDenyReceipt}}</p>
									</div>
								</li>
								<li class="slide cardBox" v-show="rcs9TabFlag === 'STAB2'">
						            <div v-for="imgIdx in rcs91ImgLimitSize" :key="imgIdx">  
						            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs91ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
							                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs91ImgInfoList[imgIdx-1].imgUrl+');'">  
						            		</div>
						            		<div v-else>
						            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            		</div>
						            </div>	
						            <!--							
									<div v-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs91ImgInfoList[0])" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs91ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && !this.$gfnCommonUtils.isEmpty(rowData.rcs91ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
						                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs91ImgInfoList[0].imgUrl+');'">
						            </div>-->
									<div class="relative">
										<div class="scroll-y" style="min-height:150px">
											<p class="color000 font-size13">{{rowData.rcs91Title}}</p>
											<p class="color3 mt5">{{rowData.rcs91Content}}</p>
										</div>
										<!--<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs9HowToDenyReceipt}}</p>-->
										<p class="color4 font-size10 absolute" >무료수신거부:{{rowData.rcs9HowToDenyReceipt}}</p>
									</div>
								</li>
								<li class="slide cardBox" v-show="rcs9TabFlag === 'STAB3'">
						            <div v-for="imgIdx in rcs92ImgLimitSize" :key="imgIdx">  
						            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs92ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
							                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs92ImgInfoList[imgIdx-1].imgUrl+');'">  
						            		</div>
						            		<div v-else>
						            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            		</div>
						            </div>		
						            <!--						
									<div v-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs92ImgInfoList[0])" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs92ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && !this.$gfnCommonUtils.isEmpty(rowData.rcs92ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
						                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs92ImgInfoList[0].imgUrl+');'">
						            </div>-->
									<div class="relative">
										<div class="scroll-y" style="min-height:150px">
											<p class="color000 font-size13">{{rowData.rcs92Title}}</p>
											<pre class="color3 mt5">{{rowData.rcs92Content}}</pre>
										</div>
										<!--<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs9HowToDenyReceipt}}</p>-->
										<p class="color4 font-size10 absolute" >무료수신거부:{{rowData.rcs9HowToDenyReceipt}}</p>
									</div>
								</li>
								<li class="slide cardBox" v-show="rcs9TabFlag === 'STAB4'">
						            <div v-for="imgIdx in rcs93ImgLimitSize" :key="imgIdx">  
						            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs93ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
							                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs93ImgInfoList[imgIdx-1].imgUrl+');'">  
						            		</div>
						            		<div v-else>
						            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            		</div>
						            </div>		
						            <!--						
									<div v-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs93ImgInfoList[0])" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs93ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && !this.$gfnCommonUtils.isEmpty(rowData.rcs93ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
						                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs93ImgInfoList[0].imgUrl+');'">
						            </div>-->
									<div class="relative">
										<div class="scroll-y" style="min-height:150px">
											<p class="color000 font-size13">{{rowData.rcs93Title}}</p>
											<pre class="color3 mt5">{{rowData.rcs93Content}}</pre>
										</div>
										<!--<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs9HowToDenyReceipt}}</p>-->
										<p class="color4 font-size10 absolute" >무료수신거부:{{rowData.rcs9HowToDenyReceipt}}</p>
									</div>
								</li>
								<li class="slide cardBox" v-show="rcs9TabFlag === 'STAB5'">
						            <div v-for="imgIdx in rcs94ImgLimitSize" :key="imgIdx">  
						            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs94ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
							                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs94ImgInfoList[imgIdx-1].imgUrl+');'">  
						            		</div>
						            		<div v-else>
						            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            		</div>
						            </div>		
						            <!--								
									<div v-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs94ImgInfoList[0])" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs94ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && !this.$gfnCommonUtils.isEmpty(rowData.rcs94ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
						                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs94ImgInfoList[0].imgUrl+');'">
						            </div>-->
									<div class="relative">
										<div class="scroll-y" style="min-height:150px">
											<p class="color000 font-size13">{{rowData.rcs94Title}}</p>
											<pre class="color3 mt5">{{rowData.rcs94Content}}</pre>
										</div>
										<!--<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs9HowToDenyReceipt}}</p>-->
										<p class="color4 font-size10 absolute" >무료수신거부:{{rowData.rcs9HowToDenyReceipt}}</p>
									</div>
								</li>
								<li class="slide cardBox" v-show="rcs9TabFlag === 'STAB6'">
						            <div v-for="imgIdx in rcs95ImgLimitSize" :key="imgIdx">  
						            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs95ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
							                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs95ImgInfoList[imgIdx-1].imgUrl+');'">  
						            		</div>
						            		<div v-else>
						            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            		</div>
						            </div>		
						            <!--								
									<div v-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs95ImgInfoList[0])" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs95ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && !this.$gfnCommonUtils.isEmpty(rowData.rcs95ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
						                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs95ImgInfoList[0].imgUrl+');'">
						            </div>-->
									<div class="relative">
										<div class="scroll-y" style="min-height:150px">
											<p class="color000 font-size13">{{rowData.rcs95Title}}</p>
											<pre class="color3 mt5">{{rowData.rcs95Content}}</pre>
										</div>
										<!--<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs9HowToDenyReceipt}}</p>-->
										<p class="color4 font-size10 absolute" >무료수신거부:{{rowData.rcs9HowToDenyReceipt}}</p>
										
									</div>
								</li>	
							</ul>
									<div class="phone_04_btn280">
										<div class="of_h consolMarginTop">
											<div class="float-left" style="width:29%" v-for="(tab, index) in rcs9Tab" :key="tab">
												<a v-if="index === 0" class="btnStyle1 backBlack" :id="tab" style="min-width:auto; width:95%"  @click="enablePreview9(tab)">{{tab}}</a>
												<a v-if="index !== 0" class="btnStyle1 backWhite" :id="tab" style="min-width:auto; width:95%"  @click="enablePreview9(tab)">{{tab}}</a>
											</div>
							            </div>
									</div>
						</div>
					</div>
			    	<!-- //cshort -->	
			    	<!-- ctall -->
			          <div v-if="channelType == 'RCS'  && previewRcsMessageType == 'CTALL'" class="phoneWrap" style="height:660px">
						<img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneCardWrap">
							<p class="color000">[WEB발신] (광고)</p>
							<ul class="cardBxslider mt10">
								<li class="slide cardBox"  v-show="rcs9TabFlag === 'TTAB1'">
									<!--<div v-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs100ImgInfoList[0])" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs100ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && !this.$gfnCommonUtils.isEmpty(rowData.rcs100ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
						                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs100ImgInfoList[0].imgUrl+');'">
						            </div>-->
						            
						            <div v-for="imgIdx in rcs100ImgLimitSize" :key="imgIdx">  
						            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs100ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
							                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs100ImgInfoList[imgIdx-1].imgUrl+');'">  
						            		</div>
						            		<div v-else>
						            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            		</div>
						            </div>
						            
									<div class="relative">
										<div class="scroll-y6" style="min-height:140px">
											<p class="color000 font-size13">{{rowData.rcs100Title}}</p>
											<pre class="color3 mt5">{{rowData.rcs100Content}}</pre>
										</div>
										<!--<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs10HowToDenyReceipt}}</p>-->
										<p class="color4 font-size10 absolute" >무료수신거부:{{rowData.rcs10HowToDenyReceipt}}</p>
									</div>
								</li>
								<li class="slide cardBox"  v-show="rcs9TabFlag === 'TTAB2'">
									<!--<div v-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs101ImgInfoList[0])" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs101ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && !this.$gfnCommonUtils.isEmpty(rowData.rcs101ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
						                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs101ImgInfoList[0].imgUrl+');'">
						            </div>-->
						            
						            <div v-for="imgIdx in rcs101ImgLimitSize" :key="imgIdx">  
						            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs101ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
							                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs101ImgInfoList[imgIdx-1].imgUrl+');'">  
						            		</div>
						            		<div v-else>
						            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            		</div>
						            </div>	
						            
									<div class="relative">
										<div class="scroll-y6" style="min-height:140px">
											<p class="color000 font-size13">{{rowData.rcs101Title}}</p>
											<pre class="color3 mt5">{{rowData.rcs101Content}}</pre>
										</div>
										<!--<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs10HowToDenyReceipt}}</p>-->
										<p class="color4 font-size10 absolute" >무료수신거부:{{rowData.rcs10HowToDenyReceipt}}</p>
									</div>
								</li>
								<li class="slide cardBox"  v-show="rcs9TabFlag === 'TTAB3'">
									<!--<div v-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs102ImgInfoList[0])" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs102ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && !this.$gfnCommonUtils.isEmpty(rowData.rcs102ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
						                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs102ImgInfoList[0].imgUrl+');'">
						            </div>-->
						            
						            <div v-for="imgIdx in rcs102ImgLimitSize" :key="imgIdx">  
						            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs102ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
							                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs102ImgInfoList[imgIdx-1].imgUrl+');'">  
						            		</div>
						            		<div v-else>
						            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            		</div>
						            </div>
						            
									<div class="relative">
										<div class="scroll-y6" style="min-height:140px">
											<p class="color000 font-size13">{{rowData.rcs102Title}}</p>
											<pre class="color3 mt5">{{rowData.rcs102Content}}</pre>
										</div>
										<!--<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs10HowToDenyReceipt}}</p>-->
										<p class="color4 font-size10 absolute" >무료수신거부:{{rowData.rcs10HowToDenyReceipt}}</p>
									</div>
								</li>
								<li class="slide cardBox"  v-show="rcs9TabFlag === 'TTAB4'">
									<!--<div v-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs103ImgInfoList[0])" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs103ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && !this.$gfnCommonUtils.isEmpty(rowData.rcs103ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
						                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs103ImgInfoList[0].imgUrl+');'">
						            </div>-->

						            <div v-for="imgIdx in rcs103ImgLimitSize" :key="imgIdx">  
						            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs103ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
							                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs103ImgInfoList[imgIdx-1].imgUrl+');'">  
						            		</div>
						            		<div v-else>
						            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            		</div>
						            </div>					            
									<div class="relative">
										<div class="scroll-y6" style="min-height:140px">
											<p class="color000 font-size13">{{rowData.rcs103Title}}</p>
											<pre class="color3 mt5">{{rowData.rcs103Content}}</pre>
										</div>
										<!--<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs10HowToDenyReceipt}}</p>-->
										<p class="color4 font-size10 absolute" >무료수신거부:{{rowData.rcs10HowToDenyReceipt}}</p>
									</div>
								</li>
								<li class="slide cardBox"  v-show="rcs9TabFlag === 'TTAB5'">
									<!--<div v-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs104ImgInfoList[0])" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs104ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && !this.$gfnCommonUtils.isEmpty(rowData.rcs104ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
						                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs104ImgInfoList[0].imgUrl+');'">
						            </div>-->
						            
						            <div v-for="imgIdx in rcs104ImgLimitSize" :key="imgIdx">  
						            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs104ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
							                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs104ImgInfoList[imgIdx-1].imgUrl+');'">  
						            		</div>
						            		<div v-else>
						            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            		</div>
						            </div>							            
									<div class="relative">
										<div class="scroll-y6" style="min-height:140px">
											<p class="color000 font-size13">{{rowData.rcs104Title}}</p>
											<pre class="color3 mt5">{{rowData.rcs104Content}}</pre>
										</div>
										<!--<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs10HowToDenyReceipt}}</p>-->
										<p class="color4 font-size10 absolute" >무료수신거부:{{rowData.rcs10HowToDenyReceipt}}</p>
									</div>
								</li>
								<li class="slide cardBox"  v-show="rcs9TabFlag === 'TTAB6'">
									<!--<div v-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs105ImgInfoList[0])" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && this.$gfnCommonUtils.isEmpty(rowData.rcs105ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center">
						                <img src="@/assets/images/common/cardThumImg2_1.png" alt="카드 썸네일">
						            </div>
						            <div v-else-if="rowData.msgType == 'IMAGE' && !this.$gfnCommonUtils.isEmpty(rowData.rcs105ImgInfoList[0].imgUrl)" class="phoneText2 mt10 text-center"
						                :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs105ImgInfoList[0].imgUrl+');'">
						            </div>-->
						            
						            <div v-for="imgIdx in rcs105ImgLimitSize" :key="imgIdx">  
						            		<div v-if="rowData.msgType == 'IMAGE' && rowData.rcs105ImgInfoList.length > imgIdx -1" class="phoneText2 mt10 text-center"
							                  :style="'padding:65px;background-repeat: no-repeat;background-size: cover;background-image: url('+rowData.rcs105ImgInfoList[imgIdx-1].imgUrl+');'">  
						            		</div>
						            		<div v-else>
						            			<img src="@/assets/images/common/cardThumImg.png" alt="카드 썸네일">
						            		</div>
						            </div>							            
									<div class="relative">
										<div class="scroll-y6" style="min-height:140px">
											<p class="color000 font-size13">{{rowData.rcs105Title}}</p>
											<pre class="color3 mt5">{{rowData.rcs105Content}}</pre>
										</div>
										<!--<p class="color4 font-size10 absolute" style="bottom:-20px">무료수신거부:{{rowData.rcs10HowToDenyReceipt}}</p>-->
										<p class="color4 font-size10 absolute" >무료수신거부:{{rowData.rcs10HowToDenyReceipt}}</p>
									</div>
								</li>
							</ul>
							<div class="phone_04_btn280">
								<div class="of_h consolMarginTop">
									<div class="float-left" style="width:29%" v-for="(tab, index) in rcs10Tab" :key="tab">
										<a v-if="index === 0" class="btnStyle1 backBlack" :id="tab" style="min-width:auto; width:95%"  @click="enablePreview10(tab)">{{tab}}</a>
										<a v-if="index !== 0" class="btnStyle1 backWhite" :id="tab" style="min-width:auto; width:95%"  @click="enablePreview10(tab)">{{tab}}</a>
									</div>
					            </div>
							</div>
						</div>

					</div>

			    	<!-- //ctall -->
			    				    	
		        	<!-- friendTalk -->
			          <div v-if="channelType == 'KAKAO' && rowData.kakaoPrdType == 'FRIENDTALK'" class="phoneWrap">
			            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div class="phoneText1 scroll-y2">
								<pre>{{rowData.friendTalkContent}}</pre>
							</div>
						</div>
			          </div>
			    	<!-- //friendTalk -->
			    	
		        	<!-- alimTalk -->
			          <div v-if="channelType == 'KAKAO' && rowData.kakaoPrdType == 'ALIMTALK'" class="phoneWrap">
			            <img src="@/assets/images/common/phoneMockup1.svg" alt="프리 템플릿">
						<div class="phoneTextWrap">
							<div class="phoneText1 scroll-y2">
								<pre>{{rowData.alimTalkContent}}</pre>
							</div>
						</div>
			          </div>
			    	<!-- alimTalk -->			    	
		        <!--// RCS -->
		                  		        
				<!-- //phoneWrap -->
				<div class="phone_04_btn6">
					<div class="of_h consolMarginTop">
						<div class="float-left" style="width:29%" v-for="(channel, index) in rowData.channelButtonArray" :key="channel">
							<a v-if="index === 0" class="btnStyle1 backBlack" :id="channel" style="min-width:auto; width:95%"  @click="enablePreview(channel)">{{channel}}</a>
							<a v-if="index !== 0" class="btnStyle1 backWhite" :id="channel" style="min-width:auto; width:95%"  @click="enablePreview(channel)">{{channel}}</a>
						</div>
		            </div>
				</div>
			</div>
		</div>
		<div class="of_h inline-block vertical-top consoleCon" style="width:60%">
			<div class="of_h user-phone">
				<div class="float-left" style="width:24%">
					<h4>01  템플릿</h4>
				</div>
				<div class="float-left" style="width:76%">
					<div class="of_h">
						<div style="width:22%" class="float-left">
							<div v-if="this.rowData.msgType == 'BASE' && this.rowData.msgKind == 'I'"><h5>텍스트 형, 정보 형</h5></div>
							<div v-if="this.rowData.msgType == 'IMAGE' && this.rowData.msgKind == 'I'"><h5>정보 형</h5></div>
							<div v-if="this.rowData.msgType == 'BASE' && this.rowData.msgKind == 'A'"><h5>텍스트 형</h5></div>
						</div>
						<div style="width:78%" v-if="this.rowData.containPushFlag == true">
							<input type="radio" name="send" value="ALL" id="ALL" 	v-model="rowData.pushSend" checked=""  > <label for="ALL" class="mr30">ALL</label>
							<input type="radio" name="send" value="FCM" id="FCM"  	v-model="rowData.pushSend"> <label for="FCM" class="mr30">FCM</label>
							<input type="radio" name="send" value="APNS" id="APNS"  v-model="rowData.pushSend"> <label for="APNS">APNS</label>
						</div>
						<div class="float-right" style="width:78%">
							<input type="text" class="inputStyle" v-model="rowData.tmpltTitle">
						</div>
					</div>													
				</div>						
			</div>
			
			<hr>

			<div class="of_h user-phone">
				<div class="float-left" style="width:24%">
					<h4>02  수신자 선택</h4>
				</div>
				<div class="float-left" style="width:76%">
					<div class="of_h">
						<div style="width:22%" class="height40 float-left">
							<h5>수신자 *</h5>
						</div>
						<div style="width:100%">
				                <div>
				                  <input type="radio" id="cuInputType_DICT" name="cuInputType" value="DICT" v-model="rowData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType">
				                  <label for="cuInputType_DICT" class="mr20">수신자 직접입력</label>
				                  <input type="radio" id="cuInputType_ADDR" name="cuInputType" value="ADDR" v-model="rowData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType">
				                  <label for="cuInputType_ADDR" class="mr20">주소록 검색</label>

				                  <input type="radio" id="cuInputType_EXCEL" name="cuInputType" value="EXCEL" v-model="rowData.cuInputType" @change="fnChgCuInputType()" @click="fnClickCuInputType">
				                  <label for="cuInputType_EXCEL">엑셀 업로드</label>
				                  <a @click="fnExcelTmplteDownLoad" class="btnStyle1 backLightGray ml20" title="샘플">샘플 <i class="far fa-arrow-to-bottom"></i></a>
				                  <input ref="excelFile" type="file" style="display:none;">
				                </div>
						</div>
					</div>
					<div class="of_h consolMarginTop" v-if="this.rowData.containPushFlag == true">
						<div style="width:22%" class="float-left">
							<h5>APP_ID</h5>
						</div>
						<div class="of_h" style="width:78%">
							<input type="text" class="inputStyle" style="width:100%" v-model="rowData.pushAppId">
						</div>
					</div>
					<div class="of_h consolMarginTop">
						<div class="of_h float-right" style="width:78%">
							<div class="float-right" style="width:100%">
								<textarea class="textareaStyle height120" v-model="rowData.cuInfo" disabled placeholder="Ex) [{“userId”, “phoneNumber”}] => 1명 발송 [{“aaa”, “0101231234”}]"></textarea>
							</div>
							<p class="float-right">발송 대상자 : {{recvCnt}}명</p>
						</div>
					</div>													
				</div>
				
			</div>
			<hr>

			<div class="of_h user-phone">
				<div style="width:22%" class="float-left">
					<h4>03  발송옵션 선택</h4>
				</div>
				<div style="width:78%" class="float-left">
					<div class="of_h">
						  <div class="float-left" style="width:22%">
							<h5>발송시간 *</h5>
						  </div>								
						  <div class="float-left" style="width:26%">
			                <input type="radio" id="rsrvSendYn_N" value="N" v-model="rowData.rsrvSendYn">
			                <label for="rsrvSendYn_N" class="mr30">즉시</label>
			                <input type="radio" id="rsrvSendYn_Y" value="Y" v-model="rowData.rsrvSendYn">
			                <label for="rsrvSendYn_Y">예약</label>
			              </div>
			              <div v-if="rowData.rsrvSendYn == 'Y'" class="float-left" style="width:20%">
			                <Calendar @update-date="fnUpdateRsrvDate" calendarId="rsrvDate" classProps="datepicker inputStyle" :initDate="rowData.rsrvDate"></Calendar>
			              </div>
			              <div v-if="rowData.rsrvSendYn == 'Y'" class="float-right" style="width:30%">
			                <select class="selectStyle2" style="width:47%" v-model="rowData.rsrvHH">
			                  <option value="00">00</option>
			                  <option v-for="hh in 23" :key="hh" :value="hh > 9 ? hh : '0'+hh">{{hh > 9 ? hh : '0'+hh}}</option>
			                </select>
			                : <select class="selectStyle2" style="width:47%" v-model="rowData.rsrvMM">
			                  <option value="00">00</option>
			                  <option v-for="mm in 5" :key="mm" :value="mm+'0'">{{mm+'0'}}</option>
			                </select>
			              </div>
					</div>
					<div class="of_h consolMarginTop">
						<div style="width:22%" class="float-left">
							<h5>태그</h5>
						</div>
						<div class="of_h" style="width:78%">
							<input type="text" class="inputStyle" style="width:100%"  v-model="rowData.campaignId" placeholder="캠페인 ID를 입력해주세요.">
						</div>
					</div>													
				</div>	
			</div>

			<hr>

			<div class="of_h user-phone">
				<div class="float-left" style="width:22%">
					<h4>04  테스트 발송</h4>
				</div>
				<div class="float-left" style="width:78%">
					<p>모든 채널에 메시지를 보냅니다.</p>
					<a @click="fnOpenTestSendInputPopup" class="btnStyle1 backLightGray consolMarginTop" title="테스트 발송" activity="SAVE">테스트 발송</a>	
				</div>
				
			</div>
			<div class="mt20 float-right">
				<a @click="fnSendIntegratedMessage('N')" class="btnStyle2 backRed float-left mr10" title="발송" activity="SAVE">발송</a>
				<router-link :to="{ name: 'integratedSend' }" tag="a" class="btnStyle2 float-left">목록</router-link>
			</div>
		</div>			
	</div>

    <DirectInputPopup 		:directInputOpen.sync="directInputOpen" 	:contsVarNms="rowData.contsVarNms" :requiredCuPhone="rowData.requiredCuPhone" :requiredCuid="rowData.requiredCuid" :recvInfoLst="rowData.recvInfoLst"></DirectInputPopup>
    <AddressInputPopup 		:addressInputOpen.sync="addressInputOpen" 	:contsVarNms="rowData.contsVarNms" :requiredCuPhone="rowData.requiredCuPhone" :requiredCuid="rowData.requiredCuid"></AddressInputPopup>
    <TestSendInputPopup 	:testSendInputOpen.sync="testSendInputOpen" :contsVarNms="rowData.contsVarNms" :testRecvInfoLst="rowData.testRecvInfoLst" :requiredCuPhone="rowData.requiredCuPhone" :requiredCuid="rowData.requiredCuid"></TestSendInputPopup>

	
	<!-- //본문 -->
  </div>

</template>



<script>
import integratedSendApi from "@/modules/integratedSend/service/integratedSendApi.js";
import integratedTemplateApi from "@/modules/template/service/templateApi.js";
import tokenSvc from '@/common/token-service';
import confirm from "@/modules/commonUtil/service/confirm";
import {eventBus} from "@/modules/commonUtil/service/eventBus";
import Calendar from "@/components/Calendar.vue";

import DirectInputPopup from "@/modules/message/components/bp-directInput.vue";
import AddressInputPopup from "@/modules/message/components/bp-addressInput.vue";
import TestSendInputPopup from "@/modules/message/components/bc-testSendInput.vue";


export default {
  name: 'integratedSendManage',

  components : {
    Calendar,
    DirectInputPopup,
    AddressInputPopup,
    TestSendInputPopup
  },
  props: {
    tmpltCodeP: {
      type: String,
      require: false,
      default: function() {
        return '';
      }
    },
    componentsTitle: {
      type: String,
      require: false,
      default: function() {
        return '통합 발송';
      }
    },
        
    rowData : {
      type: Object,
      require: false,
      default: function() {
        return {
                'checkedChannel':[] //통합템플릿에서 선택된 comma로 구분된 채널 리스트를 배열로 넣은값
              , 'channelButtonArray':[] //미리보기 아래 버튼구성용
              , 'chTypeList':''  //통합템플릿에서 선택된 comma로 구분된 세부 채널 리스트
              , 'tmpltCode':''
              , 'msgType':'BASE' //BASE: 텍스트, IMAGE:이미지
              , 'msgKind':'I' //정보성 I, 광고용: A
              , 'otherProjectUseYn':'Y' //Y:공용, N:전용
              , 'tmpltType':'M'  //tmpltType:통합발송 M, 스마트발송:S
              , 'tmpltTitle':'' //템플릿 명
              , 'containPushFlag':false //PUSH 포함여부
              , 'pushHowToDenyReceipt':''
              , 'pushAppId': ''
 
              , 'rcsSMSsHowToDenyReceipt':''
              , 'rcsLMSHowToDenyReceipt':''
              , 'rcsShortHowToDenyReceipt':''
              , 'rcsTallHowToDenyReceipt':''
              , 'rcs9HowToDenyReceipt':''
              , 'rcs10HowToDenyReceipt':''

              , 'pushImgInfo':{} //Push이미지정보  //하나의 이미지만 들어감..그래서 {}
              
              , 'rcsShortImgInfoList':[] //RCS 세로형 short 이미지정보
              , 'rcsTallImgInfoList':[] //RCS 세로형 tall 이미지정보

              , 'rcs90ImgInfoList':[] //RCS 캐러셀 카드1 이미지정보
              , 'rcs91ImgInfoList':[] //RCS 캐러셀 카드2 이미지정보
              , 'rcs92ImgInfoList':[] //RCS 캐러셀 카드3 이미지정보
              , 'rcs93ImgInfoList':[] //RCS 캐러셀 카드4 이미지정보
              , 'rcs94ImgInfoList':[] //RCS 캐러셀 카드5 이미지정보
              , 'rcs95ImgInfoList':[] //RCS 캐러셀 카드6 이미지정보

              , 'rcs100ImgInfoList':[] //RCS 캐러셀 카드1 이미지정보
              , 'rcs101ImgInfoList':[] //RCS 캐러셀 카드2 이미지정보
              , 'rcs102ImgInfoList':[] //RCS 캐러셀 카드3 이미지정보
              , 'rcs103ImgInfoList':[] //RCS 캐러셀 카드4 이미지정보
              , 'rcs104ImgInfoList':[] //RCS 캐러셀 카드5 이미지정보
              , 'rcs105ImgInfoList':[] //RCS 캐러셀 카드6 이미지정보
              
              , 'friendTalkImgInfo':{} //kakao friend talk 이미지정보
              , 'smsImgInfoList':[] //sms/mms이미지정보
               
              , 'smsSendType': 'S'  //sms/mms 발송유형
              , 'smsTitle':''
              , 'smsContent':''
              
              , 'recvInfoLst': []  //수신자정보
              , 'contsVarNms': [] //메세지 내용 변수명
	  		  , 'testRecvInfoLst': []  //테스트 수신자정보
	  		  , 'requiredCuid' : true  //app 로그인 ID 필수여부
	          , 'requiredCuPhone' : false  //수신자 폰번호 필수여부
	          , 'cuInputType':'ALL'  //DICT, ADDR, ALL, EXCEL
	          
	          , 'cuInfo':''
	          , 'rsrvSendYn':'N'  //예약발송여부
	          , 'rsrvDate':this.$gfnCommonUtils.getCurretDate()
	          , 'rsrvHH':'00'
	          , 'rsrvMM':'00'
	          
              } 
      }
    }

  },
  data() {
    return {
      channelType:'',  		//push/rcs/kakao/smsmms,  preview를 enable/diable시킨다
	  previewMessageType:'', 	//채널안의 tab(rcs의 경우 free, short, tall...)들에 대한 preview를 show한다.
	  previewKakaoMessageType:'', 	//채널안의 tab(rcs의 경우 free, short, tall...)들에 대한 preview를 show한다.
	  previewRcsMessageType:'', 	//채널안의 tab(rcs의 경우 free, short, tall...)들에 대한 preview를 show한다.
	  directInputOpen : false,
	  pushTemplateOpen : false,
	  addressInputOpen : false,
	  testSendInputOpen : false,
	  
	  
      channelTab: 9,  //채널설정 Push/RCS/카카오톡/SMSMMS 탭 show/hide
      rcsTemplateTable: 0, //채널설정 RCS탭 내 템플릿 구분 //저장등록시 유효성 체크 대상으로 사용
      rcsTemplateTableChecked: 0, //채널설정 RCS탭 내 템플릿 구분 //v-model이용 radio 선택표시용으로 사용 //rcsTemplateTable로 사용하려 했으나 작동하지 않음
      kakaoTemplateTable: 0, //채널설정 kakao내 템플릿 구분
      smsTemplateTable: 0, //채널설정 SMS/MMS내 구분
      cShortTab:0, //캐러셀 cshort 탭변경에 따른 미리보기 변경용
      cTallTab:0, //캐러셀 ctall 탭변경에 따른 미리보기 변경용
      checkedPush: false,   //채널선택에서 push 선택여부
      checkedRCS: false,    //채널선택에서 rcs 선택여부
      checkedKakao: false,  //채널선택에서 kakaotalk 선택여부
      checkedSmsMms: false, //채널선택에서 sms/mms 선택여부
      aplnIdList: {}, //app_id selectbox설정용
      kakaoChKeyList: {}, //카카오채널키 selectbox설정용
      indexData:0,
      itemDatas:[],
      rcs0CallbackList: [], //rcs 프리템플릿 발신번호 리스트
      rcs1CallbackList: [], //rcs 서술 발신번호 리스트
      rcs2CallbackList: [], //rcs 스타일 발신번호 리스트
      rcsSMSCallbackList: [], //rcs sms 발신번호 리스트
      rcsLMSCallbackList: [], //rcs lms 발신번호 리스트
      rcsShortCallbackList: [], //rcs short 발신번호 리스트
      rcsTallCallbackList: [], //rcs tall 발신번호 리스트

      rcs9CallbackList: [], //RCS 캐러셀 Short 카드 발신번호 리스트
      rcs10CallbackList: [], //RCS 캐러셀 Tall 카드 발신번호 리스트

      smsCallbackList: [], //smslms 발신번호 리스트

      detailTitle:'통합발송 템플릿',
      
      pushImgMngOpen : false, /* Push 이미지 */
      pushImgUploadOpen : false,
      pushUseCh : 'PUSH',
      pushImgLimitSize : 1,

      rcs90ImgMngOpen : false, /* RCS 캐러셀 Short 카드1 이미지 */
      rcs90ImgUploadOpen : false,
      rcs90UseCh : 'RCS',
      rcs90ImgLimitSize : 1,
      rcs91ImgMngOpen : false,
      rcs91ImgUploadOpen : false,
      rcs91UseCh : 'RCS',
      rcs91ImgLimitSize : 1,
      rcs92ImgMngOpen : false,
      rcs92ImgUploadOpen : false,
      rcs92UseCh : 'RCS',
      rcs92ImgLimitSize : 1,
      rcs93ImgMngOpen : false,
      rcs93ImgUploadOpen : false,
      rcs93UseCh : 'RCS',
      rcs93ImgLimitSize : 1,
      rcs94ImgMngOpen : false,
      rcs94ImgUploadOpen : false,
      rcs94UseCh : 'RCS',
      rcs94ImgLimitSize : 1,
      rcs95ImgMngOpen : false,
      rcs95ImgUploadOpen : false,
      rcs95UseCh : 'RCS',
      rcs95ImgLimitSize : 1,
      rcs9CardCount: 3, // rcs탭 캐러셀 Short 카드탭 갯수  //카드탭은 갯수만큼 service단에서 배열로 저장되어야 한다.

      rcs100ImgMngOpen : false, /* RCS 캐러셀 Tall 카드1 이미지 */
      rcs100ImgUploadOpen : false,
      rcs100UseCh : 'RCS',
      rcs100ImgLimitSize : 1,
      rcs101ImgMngOpen : false,
      rcs101ImgUploadOpen : false,
      rcs101UseCh : 'RCS',
      rcs101ImgLimitSize : 1,
      rcs102ImgMngOpen : false,
      rcs102ImgUploadOpen : false,
      rcs102UseCh : 'RCS',
      rcs102ImgLimitSize : 1,
      rcs103ImgMngOpen : false,
      rcs103ImgUploadOpen : false,
      rcs103UseCh : 'RCS',
      rcs103ImgLimitSize : 1,
      rcs104ImgMngOpen : false,
      rcs104ImgUploadOpen : false,
      rcs104UseCh : 'RCS',
      rcs104ImgLimitSize : 1,
      rcs105ImgMngOpen : false,
      rcs105ImgUploadOpen : false,
      rcs105UseCh : 'RCS',
      rcs105ImgLimitSize : 1,
      rcs10CardCount: 3, // rcs탭 캐러셀 Tall 카드탭 갯수  //카드탭은 갯수만큼 service단에서 배열로 저장되어야 한다.

	  rcs9TabList: 3, //preview에서 rcs tab을 보여주기 위해
	  rcs10TabList: 3,
	  rcs9Tab:[], //통합템플릿에서 선택된 comma로 구분된 TAB 리스트를 배열로 넣은값
      rcs10Tab:[], //통합템플릿에서 선택된 comma로 구분된 TAB 리스트를 배열로 넣은값
      rcs9TabFlag: 'STAB1',
      rcs10TabFlag: 'TTAB1',
	  
      rcsShortImgMngOpen : false, /* RCS 세로형 short 이미지 */
      rcsShortImgUploadOpen : false,
      rcsShortUseCh : 'RCS',
      rcsShortImgLimitSize : 1,

      rcsTallImgMngOpen : false, /* RCS 세로형 tall 이미지 */
      rcsTallImgUploadOpen : false,
      rcsTallUseCh : 'RCS',
      rcsTallImgLimitSize : 1,
      
      friendTalkImgMngOpen : false, /* MMS 이미지 */
      friendTalkImgUploadOpen : false,
      friendTalkUseCh : 'friendtalk',
      friendTalkImgLimitSize : 1, 
      
      friendTalkSenderKeyType: 'NOMAL',  //NOMAL, GROUP //friendTalk 발신프로필 그룹
      friendTalkSenderKeyList: [],
      
      smsImgMngOpen : false, /* MMS 이미지 */
      smsImgUploadOpen : false,
      smsUseCh : 'MMS',
      smsImgLimitSize : 2, /* 2개까지 가능 */
 
 
      recvCnt: 0,
      useYn : 'Y'

    }
  },


  watch: {

  },

  mounted() {
    
    this.fnSetIntegratedTemplateInfo();
    this.init();
  },

  methods: {
        
    init() {
		var slider = jQuery('.cardBxslider').bxSlider({
			auto: false,
			autoControls: false,
			slideWidth: 204,
			minSlides: 1,
			maxSlides: 2,
			slideMargin: 10,
			controls: true,
			pager: true,
			pagerType: 'short',
			touchEnabled : (navigator.maxTouchPoints > 0),
			autoHover: false,
			pause: 6000
		});    
    },
    
   async fnIntegratedSend(){
      var params = this.rowData;

      //console.log("fnSaveIntegratedTemplate params : ",params);

      //유효성 검사
      //if(this.fnIsValid() == false) return;

      params.tmpltStatus = 'SAVE';
      //params.projectId = this.testProjectId;
      params.rcsTemplateTable = this.rcsTemplateTable;//rcs일경우 사용한 템플릿 번호를 가져간다.
      params.rcs9CardCount = this.rcs9CardCount;//캐러셀 short 카드 수
      params.rcs10CardCount = this.rcs10CardCount;//캐러셀 tall 카드 수
      params.kakaoTemplateTable = this.kakaoTemplateTable;//kakao일 경우 friendTalk, alimTalk 구분값을 가져간다
      params.useYn = this.useYn; //사용여부

      await integratedTemplateApi.insertIntegratedTemplate(params).then(response =>{
        var result = response.data;
        if(result.success) {
          confirm.fnAlert(this.detailTitle, '저장 되었습니다.');
          this.$router.push('integratedTemplate')
        } else {
          confirm.fnAlert(this.detailTitle, result.message);
        }
      });
    },

    //유효성 체크
    fnIsValid(){
      if(!this.rowData.tmpltTitle){
        confirm.fnAlert(this.detailTitle, '템플릿명을 입력해주세요.');
        return false;
      }
      if(!this.rowData.msgType){
        confirm.fnAlert(this.detailTitle, '메시지타입을 선택해주세요.');
        return false;
      }
      if(!this.rowData.msgKind){
        confirm.fnAlert(this.detailTitle, '메시지구분을 선택해주세요.');
        return false;
      }
      if(!this.rowData.otherProjectUseYn){
        confirm.fnAlert(this.detailTitle, '타 프로젝트 사용여부를 선택해주세요.');
        return false;
      }
      if(this.rowData.checkedChannel.length == 0){
        confirm.fnAlert(this.detailTitle, '채널을 선택해주세요.');
        return false;
      }



      return true;
    },

    //임시저장 => 채널 설정관련 유효성 체크를  무시한다.
    save:function(){
      this.registYn = false;
      //유효성 검사
      if(this.fnIsValid() == false) return;
      
      eventBus.$on('callbackEventBus', this.fnSaveIntegratedTemplate);
      confirm.fnConfirm(this.detailTitle, "템플릿을 저장 하시겠습니까?", "확인");
    },

    //template 정보 조회
    fnSetIntegratedTemplateInfo(){
    	//console.log("this.tmpltCode : "+this.tmpltCodeP);
      if(!this.$gfnCommonUtils.isEmpty(this.tmpltCodeP)){
        this.fnSelectIntegratedTemplateInfo();
      }
    },

    //템플릿 정보 조회
    fnSelectIntegratedTemplateInfo(){
      const params = {tmpltCode: this.tmpltCodeP};
      integratedTemplateApi.integratedTemplateInfo(params).then(response => {
        const result = response.data;
        if(result.success) {
          console.log('result ===> ', result);
          if(result.data != null && result.data.length > 0){
          
          	let rtnData = result.data[0];
          	this.rowData.chTypeList = rtnData.chTypeList;
            this.rowData.checkedChannel = rtnData.chTypeList.split(',');
            
	       for(let i = 0; i < this.rowData.checkedChannel.length; i++) {
	       	  if(this.rowData.checkedChannel[i] === 'FRIENDTALK')  {
	       	  	this.rowData.channelButtonArray.push("친구톡");
	       	  }else if(this.rowData.checkedChannel[i] === 'ALIMTALK')  {
	       	  	this.rowData.channelButtonArray.push("알림톡");
	       	  }else{
	       	  	this.rowData.channelButtonArray.push(this.rowData.checkedChannel[i]);
	       	  }
	       	  
			  if(this.rowData.checkedChannel[i] === 'PUSH')  {
			    this.rowData.containPushFlag = true;
			  }
			}
		
            //console.log("containPushFlag : "+this.rowData.containPushFlag);
            //console.log("checkedChannel[0] : "+this.rowData.checkedChannel[0]);
            this.channelType = this.rowData.checkedChannel[0];
            this.previewRcsMessageType = rtnData.rcsPrdType;
            
            this.rowData.tmpltCode 		= rtnData.tmpltCode;
            this.rowData.msgType 		= rtnData.msgType;
            this.rowData.msgKind 		= rtnData.msgKind;
            this.rowData.tmpltTitle 	= rtnData.tmpltTitle;
            this.rowData.otherProjectUseYn = rtnData.otherProjectUseYn;
            
//PUSH DATA SET            
            this.rowData.pushHowToDenyReceipt 	= rtnData.pushHowToDenyReceipt;
            this.rowData.pushContent 			= rtnData.pushMsg;
            this.rowData.pushAppId 				= rtnData.pushAppId;
            this.rowData.pushTitle 				= rtnData.pushTitle;
            this.rowData.pushImgInfo.fileId 	= rtnData.pushFileId;
            this.rowData.pushImgInfo.imgUrl 	= rtnData.pushChImgUrl;
            this.rowData.pushSend 				= rtnData.pushSendType;
            this.rowData.pushHowToDenyReceipt 	= rtnData.pushRcvblcInput;//수신거부방법
            
            //console.log(">>>>>>>>>>>>>>>>this.rowData.pushAppId : "+this.rowData.pushAppId);
            //console.log(">>>>>>>>>>>>>>>>this.rowData.pushContent : "+this.rowData.pushContent);
            
//RCS DATA SET            
            
            if(rtnData.rcsPrdType == 'FREE'){
				this.rcsTemplateTable = 0;
				this.rcsTemplateTableChecked = 0;
			}else if(rtnData.rcsPrdType == 'DESCRIPTION'){
				this.rcsTemplateTable = 1;
				this.rcsTemplateTableChecked = 1;
			}else if(rtnData.rcsPrdType == 'CELL'){
				this.rcsTemplateTable = 2;
				this.rcsTemplateTableChecked = 2;
			}else if(rtnData.rcsPrdType == 'SMS'){
				this.rcsTemplateTable = 3;
				this.rcsTemplateTableChecked = 3;
			}else if(rtnData.rcsPrdType == 'LMS'){
				this.rcsTemplateTable = 4;
				this.rcsTemplateTableChecked = 4;
			}else if(rtnData.rcsPrdType == 'SHORT'){
				this.rcsTemplateTable = 5;
				this.rcsTemplateTableChecked = 5;
			}else if(rtnData.rcsPrdType == 'TALL'){
				this.rcsTemplateTable = 6;
				this.rcsTemplateTableChecked = 6;
			}else if(rtnData.rcsPrdType == 'CSHORT'){
				this.rcsTemplateTable = 9;
				this.rcsTemplateTableChecked = 9;
			}else if(rtnData.rcsPrdType == 'CTALL'){
				this.rcsTemplateTable = 10;
				this.rcsTemplateTableChecked = 10;
			}
			//console.log(">>>>>>>>>>>>>>>>this.rcsTemplateTable : "+this.rcsTemplateTable);
			
           if(rtnData.rcsPrdType == 'FREE'){
	 			this.rowData.rcs0Content 			= rtnData.rcsBodyDescription;
            }
            
            if(rtnData.rcsPrdType == 'SMS'){
	 			this.rowData.rcsSMSContent 			= rtnData.rcsBodyDescription;
	            this.rowData.rcsSMSHowToDenyReceipt = rtnData.rcsFooter;   //무료수신거부번호
	            this.rowData.rcsSMSCallback 		= rtnData.rcsCallback; //발신번호
            }
            

            
            if(rtnData.rcsPrdType == 'LMS'){
	            this.rowData.rcsLMSTitle			= rtnData.rcsBodyTitle;
	            this.rowData.rcsLMSContent 			= rtnData.rcsBodyDescription;
	            this.rowData.rcsLMSHowToDenyReceipt = rtnData.rcsFooter;   //무료수신거부번호
	            this.rowData.rcsLMSCallback 		= rtnData.rcsCallback; //발신번호
            }
            

            
            if(rtnData.rcsPrdType == 'SHORT'){
            
	            this.rowData.rcsShortTitle				= rtnData.rcsBodyTitle;
	            this.rowData.rcsShortContent 			= rtnData.rcsBodyDescription;
	            this.rowData.rcsShortHowToDenyReceipt 	= rtnData.rcsFooter;   //무료수신거부번호
	            this.rowData.rcsShortCallback 			= rtnData.rcsCallback; //발신번호
	            
	            this.rowData.rcsShortImgInfoList.push({'fileId':rtnData.rcsBodyMedia, 'imgUrl':rtnData.rcsBodyMediaUrl});   
	            
	            //console.log("rcs short rcsShortTitle"+this.rowData.rcsShortTitle);
	            //console.log("rcs short rcsShortContent"+this.rowData.rcsShortContent);
	            //console.log("rcs short rcsShortHowToDenyReceipt"+this.rowData.rcsShortHowToDenyReceipt);
	            //console.log("rcs short rcsShortCallback"+this.rowData.rcsShortCallback); 
			}


            
            if(rtnData.rcsPrdType == 'TALL'){
            	this.rowData.rcsTallTitle				= rtnData.rcsBodyTitle;
	            this.rowData.rcsTallContent 			= rtnData.rcsBodyDescription;
	            this.rowData.rcsTallHowToDenyReceipt 	= rtnData.rcsFooter;   //무료수신거부번호
	            this.rowData.rcsTallCallback 			= rtnData.rcsCallback; //발신번호
	            
	            this.rowData.rcsTallImgInfoList.push({'fileId':rtnData.rcsBodyMedia, 'imgUrl':rtnData.rcsBodyMediaUrl});
	            
	            //console.log("rcs short rcsTallTitle"+this.rowData.rcsTallTitle);
	            //console.log("rcs short rcsTallContent"+this.rowData.rcsTallContent);
	            //console.log("rcs short rcsTallHowToDenyReceipt"+this.rowData.rcsTallHowToDenyReceipt);
	            //console.log("rcs short rcsTallCallback"+this.rowData.rcsTallCallback); 
			}

            if(rtnData.rcsPrdType == 'CSHORT'){
	            this.rcs9CardCount				= rtnData.rcsCardCount;
	            
	            if(this.rcs9CardCount == 3) this.rcs9TabList = 'STAB1,STAB2,STAB3';
	            if(this.rcs9CardCount == 4) this.rcs9TabList = 'STAB1,STAB2,STAB3,STAB4';
	            if(this.rcs9CardCount == 5) this.rcs9TabList = 'STAB1,STAB2,STAB3,STAB4,STAB5';
	            if(this.rcs9CardCount == 6) this.rcs9TabList = 'STAB1,STAB2,STAB3,STAB4,STAB5,STAB6';
                this.rcs9Tab = this.rcs9TabList.split(',');
            
            
	            this.rowData.rcs9HowToDenyReceipt 		= rtnData.rcsFooter;   //무료수신거부번호
	            this.rowData.rcs9Callback 				= rtnData.rcsCallback; //발신번호
	            
	            this.rowData.rcs90Title					= rtnData.rcsBodyTitle;
	            this.rowData.rcs90Content				= rtnData.rcsBodyDescription;
	            this.rowData.rcs91Title					= rtnData.rcs1BodyTitle;
	            this.rowData.rcs91Content				= rtnData.rcs1BodyDescription;
	            this.rowData.rcs92Title					= rtnData.rcs2BodyTitle;
	            this.rowData.rcs92Content				= rtnData.rcs2BodyDescription;
	            this.rowData.rcs93Title					= rtnData.rcs3BodyTitle;
	            this.rowData.rcs93Content				= rtnData.rcs3BodyDescription;
	            this.rowData.rcs94Title					= rtnData.rcs4BodyTitle;
	            this.rowData.rcs94Content				= rtnData.rcs4BodyDescription;
	            this.rowData.rcs95Title					= rtnData.rcs5BodyTitle;
	            this.rowData.rcs95Content				= rtnData.rcs5BodyDescription;
	            
	            this.rowData.rcs90ImgInfoList.push({'fileId':rtnData.rcsBodyMedia,  'imgUrl':rtnData.rcsBodyMediaUrl});
	            this.rowData.rcs91ImgInfoList.push({'fileId':rtnData.rcs1BodyMedia, 'imgUrl':rtnData.rcs1BodyMediaUrl});
            	this.rowData.rcs92ImgInfoList.push({'fileId':rtnData.rcs2BodyMedia, 'imgUrl':rtnData.rcs2BodyMediaUrl});
	            this.rowData.rcs93ImgInfoList.push({'fileId':rtnData.rcs3BodyMedia, 'imgUrl':rtnData.rcs3BodyMediaUrl});
	            this.rowData.rcs94ImgInfoList.push({'fileId':rtnData.rcs4BodyMedia, 'imgUrl':rtnData.rcs4BodyMediaUrl});
	            this.rowData.rcs95ImgInfoList.push({'fileId':rtnData.rcs5BodyMedia, 'imgUrl':rtnData.rcs5BodyMediaUrl});
			}
            

           if(rtnData.rcsPrdType == 'CTALL'){
	            this.rcs10CardCount				= rtnData.rcsCardCount;
	            
	            if(this.rcs10CardCount == 3) this.rcs10TabList = 'TTAB1,TTAB2,TTAB3';
	            if(this.rcs10CardCount == 4) this.rcs10TabList = 'TTAB1,TTAB2,TTAB3,TTAB4';
	            if(this.rcs10CardCount == 5) this.rcs10TabList = 'TTAB1,TTAB2,TTAB3,TTAB4,TTAB5';
	            if(this.rcs10CardCount == 6) this.rcs10TabList = 'TTAB1,TTAB2,TTAB3,TTAB4,TTAB5,TTAB6';
                this.rcs10Tab = this.rcs10TabList.split(',');
                
	            this.rowData.rcs10HowToDenyReceipt 		= rtnData.rcsFooter;   //무료수신거부번호
	            this.rowData.rcs10Callback 				= rtnData.rcsCallback; //발신번호
	            
	            this.rowData.rcs100Title				= rtnData.rcsBodyTitle;
	            this.rowData.rcs100Content				= rtnData.rcsBodyDescription;
	            this.rowData.rcs101Title				= rtnData.rcs1BodyTitle;
	            this.rowData.rcs101Content				= rtnData.rcs1BodyDescription;
	            this.rowData.rcs102Title				= rtnData.rcs2BodyTitle;
	            this.rowData.rcs102Content				= rtnData.rcs2BodyDescription;
	            this.rowData.rcs103Title				= rtnData.rcs3BodyTitle;
	            this.rowData.rcs103Content				= rtnData.rcs3BodyDescription;
	            this.rowData.rcs104Title				= rtnData.rcs4BodyTitle;
	            this.rowData.rcs104Content				= rtnData.rcs4BodyDescription;
	            this.rowData.rcs105Title				= rtnData.rcs5BodyTitle;
	            this.rowData.rcs105Content				= rtnData.rcs5BodyDescription;
	            
	            this.rowData.rcs100ImgInfoList.push({'fileId':rtnData.rcsBodyMedia, 'imgUrl':rtnData.rcsBodyMediaUrl});
	            this.rowData.rcs101ImgInfoList.push({'fileId':rtnData.rcs1BodyMedia, 'imgUrl':rtnData.rcs1BodyMediaUrl});
	            this.rowData.rcs102ImgInfoList.push({'fileId':rtnData.rcs2BodyMedia, 'imgUrl':rtnData.rcs2BodyMediaUrl});
	            this.rowData.rcs103ImgInfoList.push({'fileId':rtnData.rcs3BodyMedia, 'imgUrl':rtnData.rcs3BodyMediaUrl});
	            this.rowData.rcs104ImgInfoList.push({'fileId':rtnData.rcs4BodyMedia, 'imgUrl':rtnData.rcs4BodyMediaUrl});
	            this.rowData.rcs105ImgInfoList.push({'fileId':rtnData.rcs5BodyMedia, 'imgUrl':rtnData.rcs5BodyMediaUrl});
			}
			
			

//FRIENDTALK DATA SET    
			      
            this.rowData.friendTalkSenderKeyType	= rtnData.friendTalkSenderKeyType;
            this.rowData.friendTalkSenderKey 		= rtnData.friendTalkSenderKey;
            this.rowData.friendTalkImageLink 		= rtnData.friendTalkImageLink;
            this.rowData.friendTalkContent 			= rtnData.friendTalkContent;
            this.rowData.friendTalkImgInfo.fileId 	= rtnData.friendTalkFileId;
            this.rowData.friendTalkImgInfo.imgUrl 	= rtnData.friendTalkImgUrl;
            
 			if(rtnData.friendTalkPrdType == 'FRIENDTALK'){
 				this.rowData.kakaoPrdType 				= rtnData.friendTalkPrdType; 
 			}

//NOTICETALK DATA SET
			if(rtnData.alimTalkPrdType == 'ALIMTALK'){
 				this.rowData.kakaoPrdType 				= rtnData.alimTalkPrdType;  
 			}
//SMS/MMS DATA SET

		
			if(rtnData.smsSendType == 'S'){
				this.previewMessageType = "SMS";
				this.smsTemplateTable = 0;
			}else if(rtnData.smsSendType == 'M'){
				this.previewMessageType = "MMS";
				this.smsTemplateTable = 1;
			}

            this.rowData.smsSendType 	= rtnData.smsSendType;
            this.rowData.smsTitle 		= rtnData.smsTitle;
            this.rowData.smsContent 	= rtnData.smsContent;

            if(rtnData.smsFileId0){
            	this.rowData.smsImgInfoList.push({'fileId':rtnData.smsFileId0
            	                                , 'imgUrl':rtnData.mmsChImgUrl0});
            }
            
            if(rtnData.smsFileId1){
            	this.rowData.smsImgInfoList.push({'fileId':rtnData.smsFileId1
            	                                , 'imgUrl':rtnData.mmsChImgUrl1});
            }
            
            this.rowData.smsCallback = rtnData.smsCallback;
            
           }

           console.log('this.rowData ===> ', this.rowData);


        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
          //this.rowData = {imgUrl:'', buttonList:[]};
        }
      });
    },

    enablePreview(channel){
    
      if(channel == '친구톡') channel = 'FRIENDTALK';
      if(channel == '알림톡') channel = 'ALIMTALK';
      
      this.channelType = channel;
      
      jQuery('#PUSH').attr('class','btnStyle1 backWhite');
      jQuery('#RCS').attr('class','btnStyle1 backWhite');
      jQuery('#KAKAO').attr('class','btnStyle1 backWhite');
      jQuery('#SMSMMS').attr('class','btnStyle1 backWhite');
      
      jQuery('#'+channel).attr('class','btnStyle1 backBlack')
    },


    enablePreview9(tab){
      this.rcs9TabFlag = tab;
      
      jQuery('#STAB1').attr('class','btnStyle1 backWhite');
      jQuery('#STAB2').attr('class','btnStyle1 backWhite');
      jQuery('#STAB3').attr('class','btnStyle1 backWhite');
      jQuery('#STAB4').attr('class','btnStyle1 backWhite');
      jQuery('#STAB5').attr('class','btnStyle1 backWhite');
      jQuery('#STAB6').attr('class','btnStyle1 backWhite');
      
      jQuery('#'+tab).attr('class','btnStyle1 backBlack')
    },

    enablePreview10(tab){
      this.rcs10TabFlag = tab;
      
      jQuery('#TTAB1').attr('class','btnStyle1 backWhite');
      jQuery('#TTAB2').attr('class','btnStyle1 backWhite');
      jQuery('#TTAB3').attr('class','btnStyle1 backWhite');
      jQuery('#TTAB4').attr('class','btnStyle1 backWhite');
      jQuery('#TTAB5').attr('class','btnStyle1 backWhite');
      jQuery('#TTAB6').attr('class','btnStyle1 backWhite');
      
      jQuery('#'+tab).attr('class','btnStyle1 backBlack')
    },
    
    fnClickCuInputType(e){
      if(this.rowData.cuInputType == e.target.value){
        this.fnChgCuInputType('N');
      }
    },
    
        
    //수신자 입력 타입 변경시
    fnChgCuInputType(chgYn){
      if(this.$gfnCommonUtils.defaultIfEmpty(chgYn, 'Y') == 'Y'){
        this.fnCallbackRecvInfoLst(null);  //수신자 입력 타입 변경시 수신자 정보 초기화
      }
      if(this.rowData.cuInputType == 'ALL'){  //전체발송
        return;
      }
//      if(!this.rowData.pushContent){
//        confirm.fnAlert(this.componentsTitle, '메시지 내용을 먼저 입력해주세요.');
//        this.rowData.cuInputType = 'ALL'
//        this.$refs.cuInputType_ALL.click();
//        return;
//      }

      if(this.fnSetContsVarNms() == false){
        this.rowData.cuInputType = 'ALL'
        this.$refs.cuInputType_ALL.click();
        return;
      }

      if(this.rowData.cuInputType == 'DICT'){  //직접입력
        //수신자 직접입력 팝업 호출
        this.directInputOpen = !this.directInputOpen;
      } else if(this.rowData.cuInputType == 'ADDR'){  //주소록
        //주소록 검색 팝업 호출
        this.addressInputOpen = !this.addressInputOpen;
      } else if(this.rowData.cuInputType == 'EXCEL'){  //엑셀
        //엑셀파일찾기 호출
        this.$refs.excelFile.click();
      }
    },

    //푸시 템플릿 엑셀 다운로드
    async fnExcelTmplteDownLoad(){
      if(this.fnSetContsVarNms() == false) return;
      var params = {
        contsVarNms : this.rowData.contsVarNms,
        requiredCuid: this.rowData.requiredCuid,
        requiredCuPhone: this.rowData.requiredCuPhone
      };
      await integratedSendApi.excelDownSendIntegratedRecvTmplt(params);
    },

    //수신자 정보 callback
    fnCallbackRecvInfoLst(recvInfoLst, addYn) {
      if(recvInfoLst != null){
        if(this.$gfnCommonUtils.defaultIfEmpty(addYn, 'N') == 'Y'){
          this.rowData.recvInfoLst = this.rowData.recvInfoLst.concat(recvInfoLst);
        } else {
          this.rowData.recvInfoLst = recvInfoLst;
        }
        //수신자 중복제거
        this.fnDelDuplRecvInfo();

        this.recvCnt = this.rowData.recvInfoLst.length;
        //console.log("rowData.recvInfoLst.length : "+this.rowData.recvInfoLst.length);
        this.rowData.cuInfo = JSON.stringify(this.rowData.recvInfoLst);
      } else {
        this.recvCnt = 0;
        this.rowData.recvInfoLst = [];
        this.rowData.cuInfo = '';
      }
    },
    //수신자 중복 제거
    fnDelDuplRecvInfo(){
      const vm = this;
      let key, key2;
      this.rowData.recvInfoLst = this.rowData.recvInfoLst.filter(function(item, i){
        return (
          vm.rowData.recvInfoLst.findIndex((item2) => {
            key = '';
            if ('phone' in item) key += item.phone;
            if ('cuid' in item) key += item.cuid;
            key2 = '';
            if ('phone' in item2) key2 += item2.phone;
            if ('cuid' in item2) key2 += item2.cuid;
            return key === key2
          }) === i
        );
      });
    },
        
    fnOpenTestSendInputPopup(){
      this.fnSetContsVarNms();
      this.testSendInputOpen = !this.testSendInputOpen;
    },
   
   fnSetContsVarNms(){
      const rsvNmSet = new Set(['cuid', 'phone']);
      const conts = (typeof this.rowData.pushContent 		=== 'undefined' ? '' : this.rowData.pushContent)
                  + (typeof this.rowData.rcs0Content 		=== 'undefined' ? '' : this.rowData.rcs0Content)
                  + (typeof this.rowData.rcs1Content 		=== 'undefined' ? '' : this.rowData.rcs1Content)
                  + (typeof this.rowData.rcs2Content3 		=== 'undefined' ? '' : this.rowData.rcs2Content3)
                  + (typeof this.rowData.rcsSMSContent 		=== 'undefined' ? '' : this.rowData.rcsSMSContent)
                  + (typeof this.rowData.rcsLMSContent 		=== 'undefined' ? '' : this.rowData.rcsLMSContent)
                  + (typeof this.rowData.rcsShortContent 	=== 'undefined' ? '' : this.rowData.rcsShortContent)
                  + (typeof this.rowData.rcsTallContent 	=== 'undefined' ? '' : this.rowData.rcsTallContent)
                  + (typeof this.rowData.rcs90Content 		=== 'undefined' ? '' : this.rowData.rcs90Content)
                  + (typeof this.rowData.rcs91Content 		=== 'undefined' ? '' : this.rowData.rcs91Content)
                  + (typeof this.rowData.rcs92Content 		=== 'undefined' ? '' : this.rowData.rcs92Content)
                  + (typeof this.rowData.rcs93Content 		=== 'undefined' ? '' : this.rowData.rcs93Content)
                  + (typeof this.rowData.rcs94Content 		=== 'undefined' ? '' : this.rowData.rcs94Content)
                  + (typeof this.rowData.rcs95Content 		=== 'undefined' ? '' : this.rowData.rcs95Content)
                  + (typeof this.rowData.rcs100Content 		=== 'undefined' ? '' : this.rowData.rcs100Content)
                  + (typeof this.rowData.rcs101Content 		=== 'undefined' ? '' : this.rowData.rcs101Content)
                  + (typeof this.rowData.rcs102Content 		=== 'undefined' ? '' : this.rowData.rcs102Content)
                  + (typeof this.rowData.rcs103Content 		=== 'undefined' ? '' : this.rowData.rcs103Content)
                  + (typeof this.rowData.rcs104Content 		=== 'undefined' ? '' : this.rowData.rcs104Content)
                  + (typeof this.rowData.rcs105Content 		=== 'undefined' ? '' : this.rowData.rcs105Content)
                  + (typeof this.rowData.friendTalkContent 	=== 'undefined' ? '' : this.rowData.friendTalkContent)
                  + (typeof this.rowData.alimTalkContent 	=== 'undefined' ? '' : this.rowData.alimTalkContent)
                  + (typeof this.rowData.smsContent 		=== 'undefined' ? '' : this.rowData.smsContent);
      let varNms = [];
      let containRsvNm = false;
      conts.replace(/\{\{(\w+)\}\}/g, function($0, $1) {
        if(rsvNmSet.has($1)){
          containRsvNm = true;
          return false;
        }
        varNms.push($1);
      });
      if(containRsvNm){
        confirm.fnAlert(this.componentsTitle, '발송 내용 변수 cuid, phone 은 예약어로 사용하실 수 없습니다.');
        return false;
      } else {
        this.rowData.contsVarNms = this.fnSetArrayRemoveDupliVal(varNms);
        return true;
      }
    },   
    //array에 중복 항목을 제거한다.
    fnSetArrayRemoveDupliVal(array){
      let seen = {};
      return array.filter(function(item) {
        return seen.hasOwnProperty(item) ? false : (seen[item] = true);
      });
    },  
    
    fnUpdateRsrvDate(sltDate){
      this.rowData.rsrvDate = sltDate;
    },  

    //발송 정보 유효성 체크
    fnValidSendMsgData(testSendYn){
      if(this.fnSetContsVarNms() == false){
        return false;
      }
      //if(!this.rowData.pushContent){
      //  confirm.fnAlert(this.componentsTitle, '푸시메시지 내용을 입력해주세요.');
      //  return false;
      //}
      //if(!this.rowData.appId){
      //  confirm.fnAlert(this.componentsTitle, 'APP ID를 입력해주세요.');
      //  return false;
      //}
      if(testSendYn == 'Y'){
        if(!this.rowData.testRecvInfoLst == null || this.rowData.testRecvInfoLst.length == 0){
          confirm.fnAlert(this.componentsTitle, '테스트 수신자 정보를 입력해주세요.');
          return false;
        }
      } else {
        if(this.rowData.cuInputType == 'DICT' || this.rowData.cuInputType == 'ADDR'){
          if(!this.rowData.recvInfoLst == null || this.rowData.recvInfoLst.length == 0){
            confirm.fnAlert(this.componentsTitle, '수신자 정보를 입력해주세요.');
            return false;
          }
        }
        if(this.rowData.cuInputType == 'EXCEL'){
          const uploadFile = this.$refs.excelFile;
          if(uploadFile.value == 0){
            confirm.fnAlert(this.componentsTitle, '엑셀파일을 등록해주세요.');
            return false;
          }
          const permitExten = 'xls,xlsx'.split(',');
          const extnIdx = uploadFile.value.lastIndexOf('.');
          const extn = uploadFile.value.substring(extnIdx+1);
          if((permitExten.indexOf(extn) < 0)){
            confirm.fnAlert(this.componentsTitle, '허용되지 않는 확장자입니다.');
            return false;
          }
        }
        //앱사용자 전체발송시 메시지 변수 사용금지
        //if(this.rowData.cuInputType == 'ALL'){
        //  if(this.rowData.contsVarNms.length > 0){
        //    confirm.fnAlert(this.componentsTitle, '앱사용자 전체발송시 메시지 내용에 변수를 사용하실 수 없습니다.');
        //    return false;
        //  }
        //}
        //if(this.rowData.rplcSendType != 'NONE'){
        //  if(!this.rowData.fbInfo.callback){
        //    confirm.fnAlert(this.componentsTitle, '대체발송시 대체발송 발신번호를 입력해주세요.');
        //    return false;
        //  }
        //  if(!this.rowData.fbInfo.msg){
        //    confirm.fnAlert(this.componentsTitle, '대체발송시 대체발송 내용을 입력해주세요.');
        //    return false;
        //  }
        //}
      }

      return true;
    },


    //테스트 발송 callback
    fnCallbackTestRecvInfoLst(testRecvInfoLst){
      if(testRecvInfoLst != null){
        this.rowData.testRecvInfoLst = testRecvInfoLst;
        this.fnSendIntegratedMessage('Y');
      } else {
        this.rowData.testRecvInfoLst = [];
      }
    },
    
            
    //통합 메시지 발송 처리
    async fnSendIntegratedMessage(testSendYn){
      if(this.inProgress){
        confirm.fnAlert(this.componentsTitle, '통합 메시지 발송 처리중입니다.');
        return;
      }
      
      if(this.fnValidSendMsgData(testSendYn) == false) return;

      //발송처리
      let params = Object.assign({}, this.rowData);
      params.testSendYn = testSendYn;

      if(testSendYn == 'Y'){
        params.recvInfoLst = Object.assign([], this.rowData.testRecvInfoLst);
        params.cuInputType = 'DICT';
        //테스트 발송은 대체발송 하지 않는다.
        params.rplcSendType = 'NONE'
        params.requiredCuPhone = false;
        params.fbInfo = {};
        //테스트 발송은 즉시발송만 가능
        params.rsrvSendYn = 'N';
      }

      let fd = new FormData();
      fd.append('paramString', JSON.stringify(params));
      if(this.rowData.cuInputType == 'EXCEL'){
        fd.append('file', this.$refs.excelFile.files[0]);
      }

      this.inProgress = true;
      const vm = this;
      await integratedSendApi.sendIntegratedMessage(fd).then(response =>{
        this.inProgress = false;
        const result = response.data;
        
        if(result.success) {
          if(testSendYn == 'Y'){
            if(!this.this.$gfnCommonUtils.isEmpty(result.message)){
              confirm.fnAlert(this.componentsTitle, result.message);
            } else {
              confirm.fnAlert(this.componentsTitle, '발송하였습니다.');
            }
          } else {
            if(result.data != null && !this.this.$gfnCommonUtils.isEmpty(result.data.feeMsg)){
              eventBus.$on('callbackEventBus', this.fnAlertFeeMsgCallBack);
              confirm.fnAlert(this.componentsTitle, result.data.feeMsg, 'ALERT', result);
            } else {
              this.fnAlertFeeMsgCallBack(result);
            }
          }
        } else {
          confirm.fnAlert(this.componentsTitle, result.message);
        }
      })
      .catch(function () {
        vm.inProgress = false;
      });
    },
    
    //get 문자열 byte
    getByte(str) {
      return str
        .split('')
        .map(s => s.charCodeAt(0))
        .reduce((prev, c) => (prev + ((c === 10) ? 2 : ((c >> 7) ? 2 : 1))), 0);
    },

    fnSubString(str, sIdx, length){
      let shortStr = ''
      if(!this.$gfnCommonUtils.isEmpty(str)){
        shortStr = str.toString();
        if(shortStr.length > length){
          shortStr = shortStr.substring(sIdx, length) + '...  ';
        } else {
          shortStr = shortStr + '  ';
        }
      }
      return shortStr;
    },


    fnTextLength(title, sid, tid, len){
      var val = jQuery(sid).val();
      //console.log("val : "+val.length);
      if(val.length > len){
        var msg = title + '의 최대 입력 길이는 ' + len + '입니다.';
        alert(msg);
        var temp = val.substr(0,len);
        //console.log("temp : "+temp.length);
        jQuery(sid).val(temp);
        val = jQuery(sid).val();
      }
      var text = "("+val.length+" / "+len+")";
      if(tid) jQuery(tid).html(text);
    },
    //빈값확인
    fnIsEmpty(str){
      if(str) return false;
      else return true
    },

    
    fnByteLength(title, sid, tid, len){
        var value = jQuery(sid).val();
	    var nBytes = 0;
	    var term = 0;
	    for (var idx = 0; idx < value.length; idx++) {
	        term = 0;
	        var oneChar = escape(value.charAt(idx));
	        if ( oneChar.length == 1 ) {
	            term = 1;
	        } else if (oneChar.indexOf("%u") != -1) {
	            term = 2;
	        } else if (oneChar.indexOf("%") != -1) {
	            term = 1;
	        }
	        nBytes += term;
	        if(nBytes > len){
	        	var temp = value.substr(0,idx);
	        	jQuery(sid).val(temp);
	        	nBytes -= term;
	        }
	    }
	    var text = ""+nBytes+" / "+len+"Byte";
        if(tid) jQuery(tid).html(text);
    },    
  }
}
</script>