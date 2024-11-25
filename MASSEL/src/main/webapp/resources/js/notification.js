
let stompClient = null;
let isSubscribed = false;

function initializeNotification(sessionId) {
    if (stompClient && stompClient.connected) {
        //console.log("이미 WebSocket에 연결된 상태입니다.");
        return;
    }

    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        //console.log("WebSocket 연결 성공: " + frame);

        // 판매자 구독 경로 구독
        if (!isSubscribed) {
            stompClient.subscribe('/queue/user/' + sessionId, function(message) {
                //console.log("새 알림 도착:", message.body);

                // 커스텀 이벤트 발생 (header.jsp에서 수신)
                document.dispatchEvent(new CustomEvent("newNotification", {
                    detail: JSON.parse(message.body)
                }));
            });
            isSubscribed = true;
        }
    });
}