<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.min.js"></script>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/room.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://kit.fontawesome.com/4facc1d037.js" crossorigin="anonymous"></script>

</head>
<body>

<div class="wrap">

	<div class="chatContent">
		
		<div class="chatHeader">
			<div class="previousPage">
				<a href="/chat/chatRoom">
					<i class="fa-solid fa-arrow-left"></i>
				</a>
			</div>
		
			<div class="recipientName">
				${recipient }
			</div>
			
			<c:if test="${not empty chatList}">
			<div class="exit">
				<button type="button" onclick="exitBtn()" class="exitBtn">
					나가기
				</button>
			</div>
			</c:if>
		</div>

<div class="chatMessage" id="chatMessage">
    <c:forEach items="${chatList}" var="chat">
        <div class="messageContainer">
            <c:choose>
                <c:when test="${chat.sender eq ses.uvo.id}"> <!-- 사용자가 보낸 메시지 -->
                    <div class="myMessage">
                        <div class="messageBubble">
                            ${chat.content}
                            <div class="sendTime">${chat.sendDate}</div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise> <!-- 상대방이 보낸 메시지 -->
                    <div class="otherMessage">
                        <div class="profilePic">
                             <img alt="사진없음" class="dsthumbnail" 
					src="/upload/userImg/${fn: replace(uivo.saveDir, '\\','/')}/${uivo.uuid }_${uivo.fileName}">
                        </div>
                        <div class="messageBubble">
                            ${chat.content}
                            <div class="sendTime">${chat.sendDate}</div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </c:forEach>
</div>		
		<div class="footer">
		<input type="text" id="messageInput" placeholder="메세지를 입력하세요">
		<button class="sendBtn" onclick="sendMessage()">메세지 보내기</button>
		</div>
	</div>

</div>
<script type="text/javascript">
	var roomId = '${roomId }';
	var recipient = '${recipient}';
	var sender = '${sender}';
	var sessionId = '${ses.uvo.id}';
	let exitCount = '${exitCount}';
</script>
<script type="text/javascript" src="/resources/js/room.js"></script>
</body>
</html>