window.onload = function(){

    if(sessionId) {
        connectNotification(sessionId);

    //페이지 로드때마다 notification에 is_read 가 0인게 있으면 종을 까맣게 변경시키기
    $.ajax({
        url : "/notification/checkIsRead",
        type : "get",
        success : function(count){
            //console.log(count);
            let bell = document.getElementById("bell");
            if(count>0){
                //console.log("읽지않은 메세지가 존재합미다");
                //안읽은 메세지 존재
                bell.classList.remove("fa-regular");
                bell.classList.add("fa-solid");
            }else{
                //console.log("메세지 다 읽음");
                bell.classList.remove("fa-solid");
                bell.classList.add("fa-regular");
            }
        },
        error : function(error){
            //console.log("값 가져오기 에러 > "+error);
        }
    })

    //chat_read에 isRead 0인거 있으면 빨간점 가져오기
    $.ajax({
        url : "/notification/chatIsRead",
        type : "get",
        success : function(count){
            //console.log("채팅창 isread = 0 개수 >> "+count);
            let dot = document.getElementById("chat-dot");
            if(count>0){
                //안읽은 채팅 존재
                //console.log("안읽은 채팅 존재");
                dot.style.display = "block";
            }else{
                //안읽은 채팅 없음
                //console.log("채팅 다 읽음");
                dot.style.display = "none";
            }
        },
        error : function(error){
            //console.log("채팅창 read 에러  > "+error);
        }
    })


    }
}

function connectNotification(sessionId){
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame){
        //console.log('websocket연결'+frame);

        stompClient.subscribe('/queue/user/'+sessionId, function(message){
            //console.log("구독완료");
            //메세지 JSON객체로 변환하기(문자열로 전달되었기 떄문)
            let messageDate = JSON.parse(message.body);
            //새로운 알람이 오면 종 모양바꾸기
            let bell = document.getElementById("bell");
            bell.classList.remove("fa-regular");
            bell.classList.add("fa-solid");

            // console.log("알람왔따~");
            // console.log(messageDate);
            // console.log(messageDate.writerId);
            // console.log(messageDate.sessionId);
            // console.log(messageDate.date);
            // console.log(messageDate.orderNo);
        });

        stompClient.subscribe(`/queue/user/chat/${sessionId}`, function(message){
            //console.log("채팅알람경로 구독");
            let notice = JSON.parse(message.body); 
            //console.log("알림 메시지:", notice.m);
            
            //새로운 채팅오면 빨강색 점 보여주기
            let chatDot = document.getElementById('chat-dot');
            chatDot.style.display = 'block';

        });
    })
}

//종을 클릭하면 is_read 1로 update시켜주고 종 색깔 바꾸기

let bell = document.getElementById("bell");

if(bell){

bell.addEventListener("click", ()=>{
    //console.log("isRead 1로 업데이트!");
    $.ajax({
        url : "/notification/updateIsRead",
        type : "post",
        success : function(isOk){
            //console.log("isOk >> "+isOk);
            if(isOk>0){
                //업데이트 성공
                //console.log("업데이트 성공");
                bell.classList.remove("fa-solid");
                bell.classList.add("fa-regular");
                window.location = "/notification/alarm";
            }else{
                //console.log("업데이트 할게 없슴");
                bell.classList.remove("fa-solid");
                bell.classList.add("fa-regular");
                window.location = "/notification/alarm";
            }
        },
        error : function(error){
            //alert("업데이트 오류");
        }
    })
})
}

function chatPopUp(){
		let url = '/chat/chatRoom';
		let urlOption = "width = 600px, height=700px, scrollbars=yes";
		window.open(url, 'pop', urlOption);
}

function searchReturn(event){
    let searchBtn = document.querySelector('.header-searchBtn');
    let searchInput = document.querySelector('.searchInput').value;

    if(searchInput === null || searchInput.trim().length === 0){
        alert('검색어를 한 단어 이상 입력해주세요.');
        event.preventDefault();
        return;
    }
   
}