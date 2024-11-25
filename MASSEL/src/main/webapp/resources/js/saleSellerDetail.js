let participantBtn = document.querySelector('.participantBtn');
let detailBtn = document.querySelector('.detailBtn');

participantBtn.addEventListener('click', ()=>{
    //console.log("참여자버튼");
    document.querySelector('.detailDiv').classList.add('hidden');
    document.querySelector('.participationForm').classList.remove('hidden');
    
    //밑줄생기게
    participantBtn.classList.add('underline');
    detailBtn.classList.remove('underline');

});

detailBtn.addEventListener('click', ()=>{
    //console.log("디테일버튼");
    document.querySelector('.participationForm').classList.add('hidden');
    document.querySelector('.detailDiv').classList.remove('hidden');

    participantBtn.classList.remove('underline');
    detailBtn.classList.add('underline');
});

//모달창
const modal = document.getElementById("modal");
const body = document.querySelector('body');
const closeBtn = document.getElementById('closeBtn');

document.querySelectorAll('.btn').forEach(button=>{
    button.addEventListener('click',()=>{
        modal.style.display = 'block';
        body.style.overflow = 'hidden'; 
    })
})

closeBtn.addEventListener('click', ()=>{
    modal.style.display = 'none';
    body.style.overflow = 'auto';
});

//창 바깥부분을 클릭했을때 창이 닫히는것
modal.addEventListener('click', e =>{
    const eTarget = e.target;
    if(eTarget.classList.contains('modal-overlay')){  //까만화면 부분 클릭하면
        modal.style.display = 'none';
        body.style.overflow = 'auto';
    }
})

//orderNo주고 해당 orderNo에 대한 모든 정보 가져오기

function getDetailInfo(button){
    let orderNo = button.getAttribute('data-orderNo');
    //console.log(orderNo);

    $.ajax({
        url : "/salePath/getOrdererDetail",
        type : "post",
        data : JSON.stringify({
            orderNo : orderNo
        }),
        contentType : "application/json",
        success : function(response){
            //console.log(response);
            detailInfo(response);
        },
        error : function(xhr, status, error){
            console.error("Status: " + status);  // 상태 코드 확인
            console.error("Error: " + error);    // 에러 메시지 확인
            console.error("Response Text: " + xhr.responseText); 
        }
    })
}

//상세정보 모달창에 뿌려주기!
function detailInfo(data){
    let div = document.querySelector('.info');
    div.innerHTML = ''; //기존내용 초기화하기
    div.innerHTML = `
    <div class="infoTop">
    <div class="dataId">${data.id}</div>
    <div><button onclick="openChat('${data.id}')" class="sendChatBtn">메세지 보내기</button></div>
    </div>
    <table class="detailInfo">
        <tr>
            <td class="ft">주문번호</td>
            <td>${data.orderNo}</td>
            <td class="ft">주문자이름</td>
            <td>${data.ordererName}</td>
        </tr>
        <tr>
            <td class="ft">연락처</td>
            <td>${data.ordererPhone}</td>
            <td class="ft">이메일</td>
            <td>${data.ordererEmail}</td>
        </tr>
        <tr>
            <td class="ft">수령자이름</td>
            <td>${data.recipientName}</td>
            <td class="ft">수령자 연락처</td>
            <td>${data.recipientPhoneNumber}</td>
        </tr>
        <tr>
            <td class="ft">우편번호</td>
            <td>${data.recipientPostalCode}</td>
            <td class="ft">주소</td>
            <td>${data.recipientAddress} / ${data.recipientDetailAddress}</td>
        </tr>
        <tr>
            <td class="ft">배송방법 / 배송비</td>
            <td>${data.shippingMethod} / ${data.shippingCost}</td>
            <td class="ft">배송메모</td>
            <td>${data.shippingNote}</td>
        </tr>
        <tr>
            <td class="ft">주문상품 / 수량</td>
            <td>${data.products}</td>
            <td class="ft">총 가격(배송비포함)</td>
            <td>${data.totalPrice}</td>
        </tr>
        <tr>
            <td class="ft">환불은행/계좌</td>
            <td>${data.refundBank} / ${data.refundAccount}</td>
            <td class="ft">환불계좌 예금주</td>
            <td>${data.refundAccountHolder}</td>
        </tr>
        <tr>
            <td class="ft">주문날짜</td>
            <td>${data.orderDate}</td>
        </tr>
    </table>
    `;
};

//메세지 보내기
function openChat(targetUserId){
    //console.log("타겟 유저 >> "+targetUserId);
    //버튼 클릭 시 방 생성 요청하기
    $.ajax({
        url:'/chat/createRoom',
        type:'post',
        data : {targetUser : targetUserId},
        dataType : 'json',
        success : function(response){
            //방이 성공적으로 생성되면 방 id를 받아서 해당 방으로 이동함
            //console.log("서버 응답 >> "+response);
            let roomId = response.roomId;
            //console.log("roomId >>> "+roomId);
            let url = '/chat/room/'+roomId;
            let urlOption = "width = 600px, height=500px, scrollbars=yes";
            window.open(url, 'pop', urlOption);
        },
        error : function(error){
            console.log("방 생성 중 오류 발생 :", error);
        }
    })
}

//status색상바꾸기
document.querySelectorAll('.statusDiv').forEach(function(div){
    if(div.textContent.trim() === '진행중'){
        div.style.backgroundColor = "rgba(255, 0, 0, 0.5)";
    }else if(div.textContent.trim() === '시작전'){
        div.style.backgroundColor = "rgba(0, 0, 255, 0.5)";
    }
})


function filterOrders() {
    //console.log("select Btn");
    const filterValue = document.getElementById("statusFilter").value;

    //console.log("filterValue >>>"+filterValue);
    
    //filterValue랑 orderStatus랑 똑같으면 보여주고, 다른거면 숨기기
    document.querySelectorAll('.statusValue').forEach(tr=>{
        let status = tr.getAttribute("data-status");
        //console.log("status : "+status);

        switch(filterValue){
            case "PAYMENT_COMPLETED":
                tr.style.display = (status === "PAYMENT_COMPLETED") ? "" : "none";
                break;
            case "PAYMENT_CONFIRMED":
                tr.style.display = (status === "PAYMENT_CONFIRMED") ? "" : "none";
                break;
            case "PREPARING_SHIPMENT":
                tr.style.display = (status === "PREPARING_SHIPMENT") ? "" : "none";
                break;
            case "IN_TRANSIT":
                tr.style.display = (status === "IN_TRANSIT") ? "" : "none";
                break;
            case "DELIVERED":
                tr.style.display = (status === "DELIVERED") ? "" : "none";
                break;
            case "TRANSACTION_COMPLETED":
                tr.style.display = (status === "TRANSACTION_COMPLETED") ? "" : "none";
                break;
            default :
                tr.style.display = "";
                break;
        }
    });
}

let chekcedOrderNo = []; 
let deleteModal = document.querySelector('.modal-delete-overlay');
let deleteBtn = document.querySelector('.deleteBtn');

let checkboxes = document.querySelectorAll('.orderNoValue');

function isCheckBoxChecked(){
    return Array.from(checkboxes).some(checkbox => checkbox.checked);
}

//선택한 주문 삭제하기
document.querySelector('.orderDelete').addEventListener('click', ()=>{
    
    //orderNo가 선택되지 않았을경우 alert
    if(!isCheckBoxChecked()){
        alert("삭제할 주문 번호를 선택해주세요.");

    }else{

        chekcedOrderNo = [];  //orderNo비우기
        //tr을 순회하면서 checked 된 값 가져오기
        document.querySelectorAll('.statusValue').forEach(tr=>{
            let checkBox = tr.querySelector('.orderNoValue');
            if(checkBox && checkBox.checked){
                let orderNo = checkBox.value;
                //console.log("chekced orderNo >> "+orderNo);
                chekcedOrderNo.push(parseInt(orderNo));
            }
        });
    
        //모달창 띄워서 확인버튼 누르면
        deleteModal.style.display = 'flex';
        body.style.overflow = 'hidden';
    
        if(deleteBtn){
            deleteBtn.addEventListener('click', ()=>{
                //선택값들 서버로 보내서 삭제하기
    
                $.ajax({
                    url : "/salePath/deleteOrderNo",
                    type : "post",
                    traditional : true,
                    data : {
                        chekcedOrderNo : chekcedOrderNo
                    },
                    success : function(response){
                        //console.log(response);
                        if(response>0){
                            alert("주문 삭제가 완료되었습니다.");
                            location.reload();
                        }else{
                            alert("삭제에 실패했습니다.");
                        }
                    },
                    error : function(error){
                        console.log(error);
                    }
                })
            })
        }

    }

});

//delete 모달 닫기
let deleteCloseBtn = document.querySelector('.modal-delete-closeBtn');
deleteCloseBtn.addEventListener('click',()=>{
    deleteModal.style.display = 'none';
    body.style.overflow = 'auto';
})


let orderModal = document.querySelector('.modal-order-overlay');
let changeBtn = document.querySelectorAll('.changeBtn');

//주문수정
document.querySelector('.orderEdit').addEventListener('click', ()=>{


    if(!isCheckBoxChecked()){
        alert("수정할 주문 번호를 선택해주세요.");
    }else{

    chekcedOrderNo = []; //초기화
    //tr을 순회하며 checked 된 값 가져오기
    document.querySelectorAll('.statusValue').forEach(tr=>{
        let checkBox = tr.querySelector('.orderNoValue');
        if(checkBox && checkBox.checked){
            let orderNo = checkBox.value;
            chekcedOrderNo.push(parseInt(orderNo));
        }
    });

    //모달창 띄우기
    orderModal.style.display = 'block';
    body.style.overflow = 'hidden';

    if(changeBtn){
        //모든 changeBtn을 순회하면서 data-orderStatus를 가져오기?
        changeBtn.forEach(btn=>{
            btn.addEventListener('click', (event)=>{
                let orderStatus = event.target.getAttribute("data-orderStatus");
                //console.log("선택된 상태 >> "+orderStatus);
                //console.log(chekcedOrderNo);
                $.ajax({
                    url :"/salePath/updateBuyerOrder",
                    type : "post",
                    traditional : true,
                    data :{
                        chekcedOrderNo : chekcedOrderNo,
                        orderStatus : orderStatus
                    },
                    success : function(isOk){
                        if(isOk>0){
                            alert("주문 상태를 변경했습니다.");
                            location.reload();   
                        }else{
                            alert("변경에 실패했습니다. 계속 실패하면 고객센터에 문의해주세요.");
                        }
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.log("Status Code:", jqXHR.status);            // HTTP 상태 코드
                        console.log("Status Text:", textStatus);              // 상태 텍스트 (예: "error")
                        console.log("Error Thrown:", errorThrown);            // 예외 객체 정보
                        console.log("Response Text:", jqXHR.responseText);    // 서버에서 반환된 응답 메시지
                    }
                })
            })
        })
    }   
    }
    
});

let orderCloseBtn = document.querySelector('.order-closeBtn');
orderCloseBtn.addEventListener('click',()=>{
    orderModal.style.display = 'none';
    body.style.overflow = 'auto';
})

