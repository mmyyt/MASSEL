<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="/resources/css/demandSurveyRegister.css">
<link rel="stylesheet" href="/resources/summernote/summernote-lite.css">
<script src="https://kit.fontawesome.com/4facc1d037.js" crossorigin="anonymous"></script>

</head>
<body>
	<%-- <jsp:include page="../layout/header.jsp"></jsp:include> --%>

<div class="topHeaderWrapper">
	<div class="topHeader">
		<div class="topHeader-arrow">
			<a href="/"><i class="fas fa-light fa-arrow-left-long"></i></a>
		</div>
		<div class="topHeader-title">수요조사 등록하기</div>
	</div>
</div>

<input type="hidden" name="id" id="id" value="${ses.uvo.id}"> <!--사용자 아이디 -->
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
						썸네일 이미지를 등록해주세요.
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
					<select id="categorySelect" name="categoryId">
						<option value="" disabled selected>카테고리를 선택해주세요</option>
						<option value="1">애니메이션/만화/웹툰</option>
						<option value="2">아이돌/연예인</option>
						<option value="3">드라마</option>
						<option value="4">영화</option>
						<option value="5">아이돌</option>
						<option value="6">게임</option>
						<option value="7">의류/패션/악세사리</option>
						<option value="8">도서</option>
						<option value="9">문구</option>
						<option value="10">창작캐릭터</option>
						<option value="11">기타</option>
					</select>
				</div>
				</div><!--카테고리div끝  -->
				
				<div  class="date"> <!-- 기간설정  -->
				<div> 
					<div class="startDate">
					<p class="startDatep">수요조사 시작 기간</p>
	
					<div><!--시작날짜-->
						<input type="text" id="startDay" name="startDay" placeholder="시작날짜" autocomplete="off" readonly="readonly">
						<select id="startHour" name="startHour">
							<option value="" selected disabled>시</option>
						</select>&nbsp;<span class="hourtext">시</span>
						<select id="startMinute" name="startMinute">
							<option value="" selected disabled>분</option>
						</select>&nbsp;<span class="minutetext">분</span>
					</div><!--시작날짜 끝  -->
					</div><!--startDate끝  -->
					
					<div class="endDate">
					<p class="endDatep">수요조사 종료 기간</p>
					<div><!--종료날짜 -->
						<input type="text" id="endDay" name="endDay" placeholder="종료날짜" autocomplete="off" readonly="readonly">
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
						 	<input type="text" id="dtitle" name="dtitle" class="dtitle" placeholder="수요조사 폼 제목을 입력해주세요.">
						 </div>
					</div>
				</div><!--제목div끝  -->
			
			<div class="editorDiv"><!--에디터 시작  -->
			
				<div class="editorTitle"> <!--에디터 타이틀  -->
					상세 내용
				</div>
				<div class="editorContent"><!--summernote editor  -->
				<textarea id="summernote" name="ddetail"></textarea>
				</div>
			</div><!--에디터 종료  -->


			</div><!--썸네일, 카테고리, 기간설정 div 끝  -->

			<div class="productInfo"><!--상품 항목 추가 -->
				<div class="productInfoTitle">
					<div>상품정보</div>
				</div>
				<div class="saleProductDiv">
					<div class="saleProductArea">
						<div class="saleProduct">
						
						<div class="saleProductInfo">

							
							<div class="productInputDiv">
							
							 <div class="productInputDiv_0">
							 	<div class="label1">상품명</div>
							 	<div><input class="productInput" type="text" name="dpname" id="dpname" placeholder="상품명을 입력해주세요"></div>
							 </div>
							 
							 <div class="productInputDiv_1">
							 	<div class="p">
							 		<div class="label2">가격</div>
									<div><input class="productInput2" type="text" name="dpprice" id="dpprice" placeholder="가격을 입력해주세요"></div>
								 </div>
							</div>
								<div class="addListBtn">
									<button type="button" id="addList" class="addList">상품추가</button>
								</div>
								 
							 </div>
							 
						</div>
							
	
						</div>
						
				
						</div>
			
				<!--상품 리스트  -->
					<div class="dsProductList" id="dsProductList">
					
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

<jsp:include page="../layout/footer.jsp"></jsp:include>

<script src="/resources/summernote/summernote-lite.js"></script>
<script src="/resources/summernote/lang/summernote-ko-KR.js"></script>
<script type="text/javascript" src="/resources/js/demandSurveyRegister.js"></script>


</body>
</html>