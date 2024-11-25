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
<link rel="stylesheet" type="text/css" href="/resources/css/dsList.css">
</head>
<body>

<jsp:include page="../layout/header.jsp"></jsp:include>

<div class="wrapper">



	<div class="container">
	
	<div class="content">
	
	<div class="sale-title">수요조사폼 리스트</div>
	
	<c:choose>
		<c:when test="${not empty dsList }">
	<div id="sale-list" class="sale-list">
		<c:forEach var="list" items="${dsList }">
			
			<div class="box" data-sno=${list.dsidto.dsvo.dno }>
			<a href="/demandSurvey/detail?dno=${list.dsidto.dsvo.dno }">
				<div>
					<img alt="사진없음" class="listImg" 
					src="/upload/demandsurveyImg/${fn: replace(list.dsidto.dsivo.saveDir, '\\','/')}/${list.dsidto.dsivo.uuid }_${list.dsidto.dsivo.fileName}">
				</div>
				<div class="writer">
					<c:choose>
						<c:when test="${not empty list.uivo }">
							<img alt="사진없음" class="userImg" 
							src="/upload/userImg/${fn: replace(list.uivo.saveDir, '\\','/')}/${list.uivo.uuid }_${list.uivo.fileName}">
													
						</c:when>
						<c:otherwise>
							<img src="/resources/img/defaultUserImg.png" alt="" class="userImg">								
						</c:otherwise>
					</c:choose>
					${list.dsidto.dsvo.id }
				</div>
				<div class="title">${list.dsidto.dsvo.dtitle }</div>
				<div class="bottomBox">
					<div class="viewCount"><i class="fa-solid fa-eye"></i>
						${list.dsidto.dsvo.dviewCount }
					</div>
				</div>
			</a>
			</div>
			
		</c:forEach>
	</div>		
		
		</c:when>
		<c:when test="${empty dsList }">
			<div id="sale-list" class="sale-list">
				글이 없습니다.
			</div>
		</c:when>
	</c:choose>
	

	

<div class="paging">
<c:if test="${ph.prev }">
<a href="/demandSurvey/list?pageNo=${ph.startPage-1 }&qty=${ph.pgvo.qty}"> 이전</a>
</c:if>

<c:forEach begin="${ph.startPage }" end="${ph.endPage }" var="i">
        <c:choose>
            <c:when test="${i == ph.pgvo.pageNo}">
                <span class="current-page">${i}</span>
            </c:when>
            <c:otherwise>
                <a href="/demandSurvey/list?pageNo=${i }&qty=${ph.pgvo.qty}">${i }</a>
            </c:otherwise>
        </c:choose>
</c:forEach>

 <c:if test="${ph.next }">
<a href="/demandSurvey/list?pageNo=${ph.endPage+1 }&qty=${ph.pgvo.qty}"> 다음</a>
 
 </c:if>
</div>


	</div>
	</div><!-- container -->

</div> <!--wrapper  -->

<jsp:include page="../layout/footer.jsp"></jsp:include>


</body>
</html>