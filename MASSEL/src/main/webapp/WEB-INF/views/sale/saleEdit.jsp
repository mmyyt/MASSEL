<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">


<link rel="stylesheet" href="/resources/summernote/summernote-lite.css">
<link rel="stylesheet" type="text/css" href="/resources/css/saleEdit.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />

<script src="https://kit.fontawesome.com/4facc1d037.js" crossorigin="anonymous"></script>

</head>
<body>

<div class="topHeaderWrapper">
	<div class="topHeader">
		<div class="topHeader-arrow">
			<a href="/"><i class="fas fa-light fa-arrow-left-long"></i></a>
		</div>
		<div class="topHeader-title">상품 수정하기</div>
	</div>
</div>

<form action="/salePath/modifySale" id="saleForm" method="post" enctype="multipart/form-data">

<input type="hidden" name="swriter" value="${ses.uvo.id}"> <!--사용자 아이디 -->
<input type="hidden" name="sno" value="${dto.svo.sno }" id="sno">
<div>
<!-- @@@@@@@@@@@@@@@@수요조사 폼 시작@@@@@@@@@@@@@@@@  -->

	<div class="wrap"><!--wrap  -->

		<div class="saleContent"> <!--폼 내용 -->

				<div class="saleInfoTitle">
					<div>기본정보</div>
				</div>
			<div class="div1"> <!-- 썸네일,카테고리,기간설정 div -->
				
				
			<div class="div1Top"> <!--상단부분시작  -->
				<div class="thumbnail">


				<div class="thumbnailDiv">
					<div class="thumbnailArea" id="thumbnailArea">
						<img alt="없음" class="simg"
						src="/upload/saleImg/${fn: replace(dto.sivo.saveDir, '\\','/')}/${dto.sivo.uuid }_${dto.sivo.fileName}">
					</div>
					
						<input type="file" id="thumbnailFile" name="thumbnailFile" style="display:none;"
								onchange="showThumbnailImg(event)">
				</div><!--썸네일 div끝  -->
				<p class="thumbNailInfo">&nbsp;&nbsp;&nbsp;사이즈는 임의적으로 정해집니다. <br>
				&nbsp;&nbsp;&nbsp;규격과 다른 이미지는 무통보 삭제됩니다.
				</p>

				</div>
				
				<div class="right-section"><!--썸네일 오른쪽  -->
	
				<div class="categoryArea"><!--카테고리div  -->
				<div class="categoryTitle">
					<p>카테고리</p>
				</div>
				
				<div class="category"><!--카테고리div  -->
					<select id="categorySelect" name="category">
						<option value="" disabled selected>카테고리를 선택해주세요</option>
						<option value="1" ${dto.svo.category == 1 ? 'selected':'' }>애니메이션/만화/웹툰</option>
						<option value="2" ${dto.svo.category == 2 ? 'selected':'' }>아이돌/연예인</option>
						<option value="3" ${dto.svo.category == 3 ? 'selected':'' }>드라마</option>
						<option value="4" ${dto.svo.category == 4 ? 'selected':'' }>영화</option>
						<option value="5" ${dto.svo.category == 5 ? 'selected':'' }>아이돌</option>
						<option value="6" ${dto.svo.category == 6 ? 'selected':'' }>게임</option>
						<option value="7" ${dto.svo.category == 7 ? 'selected':'' }>의류/패션/악세사리</option>
						<option value="8" ${dto.svo.category == 8 ? 'selected':'' }>도서</option>
						<option value="9" ${dto.svo.category == 9 ? 'selected':'' }>문구</option>
						<option value="10" ${dto.svo.category == 10 ? 'selected':'' }>창작캐릭터</option>
						<option value="11" ${dto.svo.category == 11 ? 'selected':'' }>기타</option>
					</select>
				</div>
				</div><!--카테고리div끝  -->
				
				<div  class="date"> <!-- 기간설정  -->
				<div> 
					<div class="startDate">
					<p class="startDatep">판매 시작 기간</p>
					<div><!--시작날짜-->
						<input type="text" id="startDay" name="startDay" placeholder="시작날짜" autocomplete="off" 
						readonly="readonly" value="${startDateStr}">
						<select id="startHour" name="startHour">
							<option value="" selected disabled>시</option>
						</select>&nbsp;<span class="hourtext">시</span>
						<select id="startMinute" name="startMinute">
							<option value="" selected disabled>분</option>
						</select>&nbsp;<span class="minutetext">분</span>
					</div><!--시작날짜 끝  -->
					</div><!--startDate끝  -->
					
					<div class="endDate">
					<p class="endDatep">판매 종료 기간</p>
					<div><!--종료날짜 -->
						<input type="text" id="endDay" name="endDay" placeholder="종료날짜" autocomplete="off" 
						readonly="readonly" value="${endDateStr }">
						<select id="endHour" name="endHour">
							<option value="" selected disabled>시</option>
						</select>&nbsp;<span class="hourtext">시</span>
						<select id="endMinute" name="endMinute">
							<option value="" selected disabled>분</option>
						</select>&nbsp;<span class="minutetext">분</span>
		
					</div><!--종료날짜 끝 -->
					</div>
					<!-- <input type="checkbox" id="noEndDate" class="noEndDate"><span class="minutetext">종료기간 설정하지 않음</span> -->
				</div>
				</div><!--기간설정 끝  -->
				</div><!--오른쪽 구역 끝  -->
				
			</div> <!--상단부분끝  -->
				
				<div class="title"> <!--제목div  -->
					<div>
						<div class="titleText">제목</div>
						<div class="titleinput">
						 	<input type="text" id="stitle" name="stitle" class="stitle" 
						 	placeholder="상품 폼 제목을 입력해주세요." value="${dto.svo.stitle }">
						 </div>
					</div>
				</div><!--제목div끝  -->
			
			<div class="editorDiv"><!--에디터 시작  -->
			
				<div class="editorTitle"> <!--에디터 타이틀  -->
					상세 내용
				</div>
				<div class="editorContent"><!--summernote editor  -->
				<textarea id="summernote" name="sdetail">
					${dto.svo.sdetail}
				</textarea>
				</div>
			</div><!--에디터 종료  -->
			
			
			<div class="sellerInfo"> 
				<div class="sellerTitle">판매 계좌정보</div> 
<!-- 				<div class="sellerInfoTitle">
					<div>판매자 정보</div>
				</div> -->
			<div class="sellerAccountInfo">
					<div class="bankNameDiv">
						<div class="accountText">은행이름</div>
						<input type="text" class="bankName" id="bankName" name="bankName"
						placeholder="은행이름을 입력해주세요." value="${dto.slvo.bankName}">
					</div>
					<div class="accountNumberDiv">
						<div class="accountText">계좌번호</div>
						<input type="text" class="accountNumber" id="accountNumber"  name="accountNumber"
						placeholder="계좌번호를 입력해주세요.(숫자만 입력)" value="${dto.slvo.accountNumber}">
					</div>
					<div class="accountHolderDiv">
						<div class="accountText">예금주</div>
						<input type="text" class="accountHolder" id="accountHolder" name="accountHolder"
						placeholder="예금주를 입력해주세요." value="${dto.slvo.accountHolder}">
					</div>
				</div> 
			</div>
			
			</div><!--썸네일, 카테고리, 기간설정 div 끝  -->
			
			<div class="productInfo"><!--상품 항목 추가 -->
				<div class="productInfoTitle">
					<div>상품정보</div>
				</div>
				
				<div class="saleProductDiv">
					<div class="saleProductArea">
					
					
<!-- 						<div class="saleProductBox">   이게 추가
							<div class="productDelBtnWrap"><button class="productDelBtn">X</button></div>
							<div class="boxWrapper">
							
								<div> 상품이미지 
									<div>상품이미지</div>
									<div class="productImgWrapper">
										<div id="productImg" class="productImg">
											<i class="fa-regular fa-image"></i>
										</div>
										<input type="file" id="productImgFile" name="productImgFile" style="display:none;"
											onchange="showProductImg(event)" class="productImgFile">
									</div>
									
								</div>
								
								<div class="productContent">상품이름,가격,재고..
									<div class="productNameDiv">
										<div class="">상품명</div>
										<div><input type="text" class="pNameInput"></div>
									</div>
									<div class="productPriceDiv">
										<div>가격</div>
										<div><input type="text" class="pPriceInput"></div>
									</div>	
									<div class="productStockDiv">
										<div>재고</div>
										<div><input type="text" class="pStockInput"></div>
									</div>	
									<div class="productLimitDiv">
										<div>개수제한</div>
										<div><input type="text" class="pLimitInput"></div>
									</div>	
								</div>
							
							</div>
					
						</div> -->
						
				
					</div> <!--saleProductArea  -->

					<div class="productAddBtnWapper">
						<div><button type="button" class="productAddBtn" id="productAddBtn">상품추가</button></div>
					</div>
		
				</div>

				</div>


				<!-- 배송관련 -->
			<div class="shippingInfo"><!--배송관련  -->
				<div class="shippingInfoTitle">
					<div>배송정보</div>
				</div>
				
				<div class="shippingMethod">  <!-- 배송방법/가격 -->
	
					<div class="shippingMethodDiv"> 
<!-- 						<div class="shippingMethodBox">이게추가 
							<div class="shippingName">
								<div class="nameText">배송 방법 이름</div>
								<div><input type="text" class="shippingMethodInput" name="shippingMethod" id="shippingMethod" placeholder="배송방법을 입력해주세요."></div>	
							</div>
							<div class="shippingPrice">
								<div class="priceText">배송비</div>
								<div><input type="text" class="shippingCostInput" name="shippingCost" id="shippingCost" value="0"> 원</div>	
							</div>
							<div class="shippingMethodDelete">
								<div class="shippingDeleteBtn">X</div>
								<button type="button" class="shippingDeleteBtn">X</button>
							</div>	
						</div> -->
						
						
					</div>
					
					<div class="shippingMethodDiv">
						<button id="shippingMethodAddBtn">배송 방법 추가</button>
					</div>
				</div>
						
			</div><!--배송관련끝  -->


	<div> <!--배송날짜/환불/교환안내  -->
		<div class="refundTitle">
			<div>배송 및 환불 안내</div>
		</div>
		
				<div class="refundContent"> <!--배송안내 (배송시작날짜,배송관련 안내사항 -->
					
<!-- 					<div>
						<div class="shippingExpectedDate">
							<div class="shippingExText">배송예정날짜</div>
							<div><input type="text" id="shippingExdate" name="shippingExdate"
							placeholder="배송 예정 날짜를 입력해주세요."></div>
						</div>
					</div> -->
					
					<div>
						<div class="shippingInstructionDiv">
							<div class="shippingInstructionText">배송안내사항</div>
							<div>
								<textarea rows="" cols="" name="shippingInstructions" id="shippingInstructions" 
								style="overflow:hidden; resize:none;" placeholder="배송과 관련해 안내사항이 있다면 적어주세요.">${dto.svo.shippingInstructions }</textarea>
							</div>
						</div>
					</div>
				

				<div> <!--환불/교환관련 설명  -->
					<div class="refundMessageDiv">
						<div class="refundMessageText">환불/교환 안내사항</div>
						<div>
							<textarea rows="" cols="" name="refundMessage" id="refundMessage"
							style="overflow:hidden; resize:none;" placeholder="환불/교환과 관련해 안내사항이 있다면 적어주세요.">${dto.svo.refundMessage }</textarea>
						</div>
					</div>
				</div>	
			</div>

	</div>

<div class="submitBtnWrapper">
    <button type="submit" class="submitBtn" id="submitBtn">작성하기</button>
</div>


		</div> <!--saleContent -->

	</div> <!-- wrap  -->
<!-- @@@@@@@@@@@@@@@@수요조사 폼 끝@@@@@@@@@@@@@@@@  -->
</div>


</form>

<jsp:include page="../layout/footer.jsp"></jsp:include>

<script type="text/javascript">
	sessionId = "${ses.uvo.id}";
	
	let jsonString = '${jsonProductList}';
	jsonString = jsonString.replace(/\\/g, '\\\\');
	
	let originalProductList = JSON.parse(jsonString);
	
	let	originalShipmentList = JSON.parse('${jsonShipmentList}');
	
	const startHour = ${startHour};
	const startMinute = ${startMinute};
	const endHour = ${endHour};
	const endMinute = ${endMinute};
	
</script>
<script src="/resources/summernote/summernote-lite.js"></script>
<script src="/resources/summernote/lang/summernote-ko-KR.js"></script>
<script type="text/javascript" src="/resources/js/saleEdit.js"></script>


</body>
</html>