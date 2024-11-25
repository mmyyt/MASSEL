<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/alarm.css">
</head>

<body>

<jsp:include page="../layout/header.jsp"></jsp:include>


<div class="wrap">

<div class="notification-container">

	<c:choose>
		<c:when test="${empty list}">
			알람함이 비었습니다.
		
		</c:when>
	</c:choose>

    <div class="notification-list">
    
    	<c:forEach items="${list }" var="list">
        <div class="notification-item">
            
            <div class="notification-content">
                <p>${list.messageContent }</p>
                <span class="notification-date">${list.recievedDate }</span>
            </div>
        </div>

		</c:forEach>
    </div>
</div>

</div>


<jsp:include page="../layout/footer.jsp"></jsp:include>
</body>
</html>