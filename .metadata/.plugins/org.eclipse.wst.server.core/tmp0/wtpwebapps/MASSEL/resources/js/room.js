
// console.log("방번호 : "+roomId);
// console.log("sender : "+sender);
// console.log("recipient : " + recipient);
// console.log("it's me! : "+sessionId);
// console.log(exitCount);

let realExitCount = parseInt(exitCount);

//websocket 연결 설정
function connect(){
    //console.log()
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame){
        //console.log('Connected : '+frame);
        //console.log("연결 성립중");

        //사용자가 방에 입장했음을 서버에 전송
        stompClient.send("/app/chat.joinRoom", {}, JSON.stringify({
            'roomId' : roomId,
            'userId' : sessionId //현재 로그인중인 사용자
        }));

        //특정 방의 topic구독(채팅방 구독)
        stompClient.subscribe('/topic/room/'+roomId, function(messageOutput){
            //showMessage(JSON.parse(messageOutput.body).content);
            var message = JSON.parse(messageOutput.body);

            //상대방이 채팅방을 나갔을때
            if(message.action === "exit"){
                //console.log("상대방이 채팅방을 나감");
                document.querySelector('.sendBtn').disabled = true;
                alert("채팅방이 종료되었습니다. 더 이상 메세지를 보낼 수 없습니다");
                return;
            }

            showMessage(message);  //메세지 화면에 표시
        });
    });
}

//메세지 보내기
function sendMessage(){
    //console.log("메세지 보낸다");
    //console.log("메세지 보낸사람 : "+sender+", 받는 사람 : "+recipient);
    var messageContent = document.getElementById("messageInput").value;
    //console.log("메세지 내용 > "+messageContent);
    if(messageContent && stompClient){
        stompClient.send("/app/chat.sendMessage/"+roomId, {}, JSON.stringify({
            'sender': sender, //현재 세션에 로그인되어있는 id(보내는사람)
            'recipient' : recipient,  //받는 사람
            'content': messageContent
        }));

        //메세지를 보내면서 상대방의 고유 경로로도 보내기(알람용)
        if(sessionId.trim() === sender.trim()){
            stompClient.send(`/app/notification/chat/${roomId}/${recipient}`, {}, JSON.stringify({
                'sender': sender, //현재 세션에 로그인되어있는 id(보내는사람)
                'recipient' : recipient,  //받는 사람
                'content': messageContent
            }));
        }else if(sessionId.trim() === recipient.trim()){
            stompClient.send(`/app/notification/chat/${roomId}/${sender}`, {}, JSON.stringify({
                'sender': sender, //현재 세션에 로그인되어있는 id(보내는사람)
                'recipient' : recipient,  //받는 사람
                'content': messageContent
            }));
        }


        document.getElementById("messageInput").value = ""; //입력 창 비우기
    }
}

let lastDate = null; //마지막으로 메세지가 출력된 날짜 저장

//채팅 메시지 화면에 출력하기
function showMessage(message){
    //console.log("메세지 출력한다");
    //console.log("메세지 보낸사람 >>> "+message.sender);
    //console.log("메세지 내용 >>> "+message.content);
    //console.log("메세지 보낸 날자 >>> "+message.sendDate);
    let sendTime = message.sendDate.split("T")[1].substring(0,5);

    var chatMessage = document.getElementById("chatMessage");
    var messageElement = document.createElement("div");
    var messageContainer = document.createElement("div"); // 메시지 컨테이너
    messageContainer.classList.add("messageContainer"); // CSS 클래스 추가

    //채팅창에 날짜 나타내기(메세지를 보낸 날짜에서 가져오기
    let currentDate = message.sendDate.split("T")[0]; //2024-10-10

    if (sessionId === message.sender) {
        // 사용자가 보낸 메시지
        var myMessage = document.createElement("div");
        myMessage.classList.add("myMessage"); // 사용자 메시지 클래스 추가
        
        var messageBubble = document.createElement("div");
        messageBubble.classList.add("messageBubble"); // 메시지 말풍선 클래스 추가
        messageBubble.innerHTML = message.content + "<div class='sendTime'>" + sendTime + "</div>"; // 보낸 시간 추가
        
        myMessage.appendChild(messageBubble);
        messageContainer.appendChild(myMessage);
    } else {
        // 상대방이 보낸 메시지
        var otherMessage = document.createElement("div");
        otherMessage.classList.add("otherMessage"); // 상대방 메시지 클래스 추가

        var profilePic = document.createElement("div");
        profilePic.classList.add("profilePic");
        profilePic.innerHTML = "<img src='' alt='Profile Picture' />"; // 상대방 프로필 사진

        var messageBubble = document.createElement("div");
        messageBubble.classList.add("messageBubble"); // 메시지 말풍선 클래스 추가
        messageBubble.innerHTML = message.content + "<div class='sendTime'>" + sendTime + "</div>"; // 보낸 시간 추가

        otherMessage.appendChild(profilePic);
        otherMessage.appendChild(messageBubble);
        messageContainer.appendChild(otherMessage);
    }

    //chatMessage.appendChild(messageElement);
    chatMessage.appendChild(messageContainer);

    //메세지가 추가되면 스크롤 맨 아래로 이동
    chatMessage.scrollTop = chatMessage.scrollHeight;
}

//페이지 로드 시 webSocket연결
window.onload = function(){
    connect();
    document.getElementById("chatMessage").scrollTop = document.getElementById("chatMessage").scrollHeight;

    //isExit count확인
    if(realExitCount>0){  //행이 1개 이상이면 누가 나갔다는 의미니까
        alert('채팅이 종료되었습니다. 나가기 버튼을 눌러주세요.');
        let messageInput = document.getElementById('messageInput');
        messageInput.disabled = true;
        let sendBtn = document.querySelector('.sendBtn');
        sendBtn.disabled = true;
    }

};


//나가기 버튼
function exitBtn(){
    //console.log(" session id : "+sessionId+", roomId : "+roomId);

    //아이디, 방번호 주고 isExit = 1로 update
    
    $.ajax({
        url : "/chat/exit",
        data : { id : sessionId, roomId: roomId},
        type : "post",
        dataType : "json",
        success : function(response){
            if(response.status === "success"){
                //console.log(response.status);
                //console.log(response.user);
            //roomList로 이동(만약 session id가 나면)
                if(response.user === "self"){
                    window.location.href = "/chat/chatRoom";
                }
            }else{
                //console.log(response.status);
                //console.log(response.user);
                alert("업데이트 실패");
            }

        },
        error : function(error){
            //console.log("error : "+error);
        }
    });
}

//유저가 뒤로가거나 닫기버튼 등으로 페이지를 떠날떄 websocket 종료
window.addEventListener("beforeunload", function(){
    if(stompClient){
        stompClient.send("/app/chat.leaveRoom", {}, JSON.stringify({
            'roomId' : roomId,
            'userId' : userId
        }));

        //websocket 연결끊기
        stompClient.disconnect(function(){
            //console.log("websocket 연결 끊김");
        })
    }
})