<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/chatRoomList.css">
</head>
<body>

<div class="wrap">

	<div class="chatListContent">
	
	<div class="title">
		<div class="titleText">
			${ses.uvo.id }님의 채팅 목록
		</div>
	</div>
	
	<div class="chatList">
	
		<ul class="chatRoomList">
    <c:forEach items="${cdto}" var="cdto">
    	<c:if test="${cdto.cvo ne null }">
        <li>
            <div class="chatRoom" onclick="navigateToRoom('${cdto.crvo.roomId}')">
                <div class="profilePic">
                    <img alt="사진없음" class="dsthumbnail" 
					src="/upload/userImg/${fn: replace(uivo.saveDir, '\\','/')}/${uivo.uuid }_${uivo.fileName}">
                </div>
                <div class="chatInfo">
                    <div class="name">
                        <c:choose>
                            <c:when test="${cdto.crvo.sender eq ses.uvo.id}">
                                ${cdto.crvo.recipient}
                            </c:when>
                            <c:otherwise>
                                ${cdto.crvo.sender}
                            </c:otherwise>
                        </c:choose>
                        <span class="newMessage">
                        	<!-- 새로운메시지! -->
                            <%-- <c:if test="${cdto.hasNewMessage}">New</c:if> --%>
                        </span>
                    </div>
                    <div class="lastMessage">
                        ${cdto.cvo.content}
                        <span class="lastMessageTime">${cdto.cvo.sendDate }</span>
                    </div>
                   
                   	<c:if test="${cdto.readCount > 0 }">
                   		<div class="newChat">
                   			
                   		</div>
                   	</c:if> 
                </div>
            </div>
        </li>
        </c:if>
    </c:forEach>
</ul>
	
	</div>
	
	
	</div>
</div>



<script>
    function navigateToRoom(roomId) {
        location.href = '/chat/room/' + roomId;
    }
</script>
<script type="text/javascript" src="/resources/js/chatRoomList.js"></script>
</body>
</html>