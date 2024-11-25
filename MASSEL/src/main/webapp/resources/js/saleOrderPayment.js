// console.log("판매자 >> "+writer);
// console.log("구매자 >> "+sessionId);
//모달창
const body = document.querySelector('body');
document.querySelector('.confirm-btn').addEventListener('click', function() {
    //console.log("입금확인버튼");
    document.querySelector('.modal-confirm').style.display = 'flex';
    body.style.overflow = 'hidden';

    //console.log("입금완료 버튼 누름");
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame){
            //console.log('connected : '+frame);
            //console.log("연결완료");
            stompClient.send("/app/notification/user/"+writer, {}, JSON.stringify({
                'writer' : writer,
                'sessionId' : sessionId,
                'orderNo' : orderNo
            }));

        });
});

document.querySelector('.close-confirm').addEventListener('click', function() {
    document.querySelector('.modal-confirm').style.display = 'none';
    window.location = "/";
});


document.querySelector('.later-btn').addEventListener('click', function() {
    //console.log("나중에하기 버튼");
    document.querySelector('.modal-later').style.display = 'flex';
    body.style.overflow = 'hidden';
});

document.querySelector('.close-later').addEventListener('click', function() {
    document.querySelector('.modal-later').style.display = 'none';
    window.location = "/";
});
