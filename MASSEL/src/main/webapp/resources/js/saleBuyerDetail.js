const body = document.querySelector('body');

let confirmBtn = document.querySelector('.confirm-btn');
if(confirmBtn){
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
}

let cancleBtn = document.querySelector('.cancle-btn');
if(cancleBtn){
    cancleBtn.addEventListener('click',()=>{
        document.querySelector('.modal-cancle').style.display = 'flex';
        body.style.overflow = 'hidden';
    });
}

document.querySelector('.close-cancle').addEventListener('click',()=>{
    $.ajax({
        url : "/salePath/cancleOrder",
        type : "post",
        data : {orderNo : orderNo},
        success : function(isOk){
            //console.log(isOk);
            if(isOk>0){
                //alert("주문취소 성공");
                document.querySelector('.modal-cancle').style.display = 'none';
                location.reload();   
            }else{
                alert("주문취소 실패");
            }
        },
        error : function(error){
            //console.log(error);
        }
    })
});



document.querySelector('.close-confirm').addEventListener('click', function() {
    document.querySelector('.modal-confirm').style.display = 'none';
    location.reload();
});

document.addEventListener("DOMContentLoaded", function() {
    //console.log("orderStatus >>> "+orderStatus);
    //const orderStatus = "${detailInfo.orderStatus}"; // 현재 진행 상태를 가져옴 (ex.결제확인 1)
    const statusOrder = ["PAYMENT_COMPLETED", "PAYMENT_CONFIRMED", "PREPARING_SHIPMENT",
                         "IN_TRANSIT", "DELIVERED", "TRANSACTION_COMPLETED"];
    
    // 현재 상태까지 모든 step에 색상을 채움
    document.querySelectorAll(".step").forEach(step => {
        const stepStatus = step.getAttribute("data-status");
        if (statusOrder.indexOf(stepStatus) <= statusOrder.indexOf(orderStatus)) { 
            step.classList.add("completed"); // 완료된 단계에 색상을 채움
        }
    });
});
