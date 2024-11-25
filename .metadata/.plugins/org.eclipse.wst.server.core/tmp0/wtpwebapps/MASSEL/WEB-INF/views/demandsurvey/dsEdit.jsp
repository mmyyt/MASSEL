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
<link rel="stylesheet" href="/resources/summernote/summernote-lite.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="/resources/css/dsEdit.css">
<script src="https://kit.fontawesome.com/4facc1d037.js" crossorigin="anonymous"></script>

</head>
<body>

<div class="topHeaderWrapper">
	<div class="topHeader">
		<div class="topHeader-arrow">
			<a href="/"><i class="fas fa-light fa-arrow-left-long"></i></a>
		</div>
		<div class="topHeader-title">수요조사 수정하기</div>
	</div>
</div>

<form action="/demandSurvey/edit" method="post" enctype="multipart/form-data">

<input type="hidden" name="dno" value="${dsedto.dsvo.dno }">
<input type="hidden" name="dno" value="${dsedto.dsvo.id }">

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
						<img alt="없음" class="dsimg"
						src="/upload/demandSurveyImg/${fn: replace(dsedto.dsivo.saveDir, '\\','/')}/${dsedto.dsivo.uuid }_${dsedto.dsivo.fileName}">
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
						<option value="1" ${dsedto.dsvo.categoryId == 1 ? 'selected':'' }>애니메이션/만화/웹툰</option>
						<option value="2" ${dsedto.dsvo.categoryId == 2 ? 'selected':'' } >아이돌/연예인</option>
						<option value="3" ${dsedto.dsvo.categoryId == 3 ? 'selected':'' }>드라마</option>
						<option value="4" ${dsedto.dsvo.categoryId == 4 ? 'selected':'' }>영화</option>
						<option value="5" ${dsedto.dsvo.categoryId == 5 ? 'selected':'' }>아이돌</option>
						<option value="6" ${dsedto.dsvo.categoryId == 6 ? 'selected':'' }>게임</option>
						<option value="7" ${dsedto.dsvo.categoryId == 7 ? 'selected':'' }>의류/패션/악세사리</option>
						<option value="8" ${dsedto.dsvo.categoryId == 8 ? 'selected':'' }>도서</option>
						<option value="9" ${dsedto.dsvo.categoryId == 9 ? 'selected':'' }>문구</option>
						<option value="10" ${dsedto.dsvo.categoryId == 10 ? 'selected':'' }>창작캐릭터</option>
						<option value="11" ${dsedto.dsvo.categoryId == 11 ? 'selected':'' }>기타</option>
					</select>
				</div>
				</div><!--카테고리div끝  -->
				
				<div  class="date"> <!-- 기간설정  -->
				<div> 
					<div class="startDate">
					<p class="startDatep">판매 시작 기간</p>
					<div><!--시작날짜-->
						<input type="text" id="startDay" name="startDay" placeholder="시작날짜" 
						autocomplete="off" readonly="readonly" value="${startDateStr }">
						<select id="startHour" name="startHour">
							<option selected disabled>시</option>
						</select>&nbsp;<span class="hourtext">시</span>
						<select id="startMinute" name="startMinute">
							<option selected disabled>분</option>
						</select>&nbsp;<span class="minutetext">분</span>
					</div><!--시작날짜 끝  -->
					</div><!--startDate끝  -->
					
					<div class="endDate">
					<p class="endDatep">판매 종료 기간</p>
					<div><!--종료날짜 -->
						<input type="text" id="endDay" name="endDay" placeholder="종료날짜" 
						autocomplete="off" readonly="readonly" value="${endDateStr }">
						<select id="endHour" name="endHour">
							<option selected disabled>시</option>
						</select>&nbsp;<span class="hourtext">시</span>
						<select id="endMinute" name="endMinute">
							<option selected disabled>분</option>
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
						 	<input type="text" name="dtitle" class="dtitle" 
						 	placeholder="수요조사 폼 제목을 입력해주세요."
						 	value="${dsedto.dsvo.dtitle }">
						 </div>
					</div>
				</div><!--제목div끝  -->
			
			<div class="editorDiv"><!--에디터 시작  -->
			
				<div class="editorTitle"> <!--에디터 타이틀  -->
					상세 내용
				</div>
				<div class="editorContent"><!--summernote editor  -->
				<textarea id="summernote" name="ddetail">
					${dsedto.dsvo.ddetail }
				</textarea>
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
    <button type="submit" onclick="sendProductList()" class="submitBtn">작성하기</button>
</div>



		</div> <!--saleContent -->

	</div> <!-- wrap  -->
<!-- @@@@@@@@@@@@@@@@수요조사 폼 끝@@@@@@@@@@@@@@@@  -->
</div>

</form>

<script src="/resources/summernote/summernote-lite.js"></script>
<script src="/resources/summernote/lang/summernote-ko-KR.js"></script>
<script>

	let originalProductList = JSON.parse('${jsonProductList}');
	console.log(originalProductList);
	
	let dno = ${dno};
</script>
<script type="text/javascript" src="/resources/js/dsEdit.js"></script>


</body>
</html>