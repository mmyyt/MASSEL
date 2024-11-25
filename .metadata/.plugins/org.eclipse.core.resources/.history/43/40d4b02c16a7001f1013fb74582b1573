<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://kit.fontawesome.com/4facc1d037.js" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/saleList.css">
</head>
<body>

<jsp:include page="../layout/header.jsp"></jsp:include>

<div class="wrapper">



	<div class="container">
	
	<div class="content">
	
	<div class="sale-title">판매폼 리스트</div>
	
	<div id="sale-list" class="sale-list">
		<c:forEach var="saleList" items="${list }">
			
			<div class="box" data-sno=${saleList.sidto.svo.sno }>
			<a href="/salePath/detail?sno=${saleList.sidto.svo.sno }">
				<div>
					<img alt="사진없음" class="listImg" 
					src="/upload/saleImg/${fn: replace(saleList.sidto.sivo.saveDir, '\\','/')}/${saleList.sidto.sivo.uuid }_${saleList.sidto.sivo.fileName}">
				</div>
				<div class="writer">
					<c:choose>
						<c:when test="${not empty saleList.uivo }">
							<img alt="사진없음" class="userImg" 
							src="/upload/userImg/${fn: replace(saleList.uivo.saveDir, '\\','/')}/${saleList.uivo.uuid }_${saleList.uivo.fileName}">
													
						</c:when>
						<c:otherwise>
							<img src="/resources/img/defaultUserImg.png" alt="" class="userImg">								
						</c:otherwise>
					</c:choose>
					${saleList.sidto.svo.swriter }
				</div>
				<div class="title">${saleList.sidto.svo.stitle }</div>
				<div class="bottomBox">
					<div class="viewCount"><i class="fa-solid fa-eye"></i>
						${saleList.sidto.svo.viewCount }
					</div>
					<div class="favoriteCount"><i class="fa-solid fa-heart"></i>
						${saleList.sidto.svo.favoriteCount }
					</div>
				</div>
			</a>
			</div>
			
		</c:forEach>
	</div>
	

<div class="paging">
<c:if test="${ph.prev }">
<a href="/salePath/list?pageNo=${ph.startPage-1 }&qty=${ph.pgvo.qty}"> 이전</a>
</c:if>

<c:forEach begin="${ph.startPage }" end="${ph.endPage }" var="i">
<a href="/salePath/list?pageNo=${i }&qty=${ph.pgvo.qty}">${i }</a>
</c:forEach>

 <c:if test="${ph.next }">
<a href="/salePath/list?pageNo=${ph.endPage+1 }&qty=${ph.pgvo.qty}"> 다음</a>
 
 </c:if>
</div>


	</div>
	</div><!-- container -->

</div> <!--wrapper  -->

<jsp:include page="../layout/footer.jsp"></jsp:include>

<script type="text/javascript" src="/resources/js/saleList.js"></script>
</body>
</html>