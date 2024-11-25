let sno = parseInt(tmpSno, 10);

$(document).ready(function() {
    var rightWrap = $('#rightwrap');
    var wrap = $('.wrap');
    
    // wrap의 하단 위치 계산
    var wrapBottom = wrap.offset().top + wrap.outerHeight();
    
    // rightwrap의 초기 위치 저장
    var rightWrapStartPos = rightWrap.offset().top;

    $(window).on('scroll', function() {
        var scrollTop = $(window).scrollTop(); // 현재 스크롤 위치
        var rightWrapHeight = rightWrap.outerHeight(); // rightwrap의 높이
        var rightWrapBottom = scrollTop + rightWrapHeight + 100; // rightwrap의 하단 위치

        // rightwrap이 wrap의 하단을 넘어가면 wrap의 하단에서 멈춤
        if (rightWrapBottom >= wrapBottom) {
            //console.log("바닥에닿음");
            rightWrap.css({
                'position': 'absolute',
                'top': (wrapBottom - rightWrapHeight - 100) + 'px'
            });
        } else if (scrollTop > rightWrapStartPos - 100) {
            // 스크롤 위치가 rightwrap의 초기 위치보다 크면 fixed로 설정
            //console.log("fixed.");
            rightWrap.css({
                'position': 'fixed',
                'top': '100px'
            });
        } else {
            // 스크롤이 위로 올라가면 다시 원래 위치로 되돌림
            //console.log("스크롤바 다시 위로");
            rightWrap.css({
                'position': 'absolute',
                'top': rightWrapStartPos + 'px'
            });
        }
    });
});



function openChat(targetUserId){
    //console.log("타겟 유저 >> "+targetUserId);
    //버튼 클릭 시 방 생성 요청하기
    if(sessionId === null || sessionId === ""){
        alert("로그인 후 이용해주세요.");
    }else{
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
                //console.log("방 생성 중 오류 발생 :", error);
            }
        })
    }

}

document.querySelectorAll(".countBtnPlus").forEach(button => {
    button.addEventListener('click', (event)=>{
        let spno = event.target.getAttribute('data-spno');  //상품의 번호
        let inputField = document.querySelector(`input[data-spno="${spno}"]`); //해당 spno의 input창

        let countValue = parseInt(inputField.value);
        
        //상품의 최대구매가능개수
        let limitCount = event.target.getAttribute('data-limit');
        if(inputField.value >= limitCount){
            alert('1인당 구매 가능한 개수를 확인해주세요.');
            return false;
        }else{
            countValue++;
            inputField.value = countValue;
        }        

        let minusButton = document.querySelector(`button.countBtnMinus[data-spno="${spno}"]`);
        minusButton.disabled = countValue <= 0;

    });
});

document.querySelectorAll(".countBtnMinus").forEach(button=>{
    button.addEventListener('click', (event)=>{
        let spno = event.target.getAttribute('data-spno');
        let inputField = document.querySelector(`input[data-spno="${spno}"]`);
        let countValue = parseInt(inputField.value);
        countValue--;
        inputField.value = countValue;

        let minusButton = event.target; // 현재 클릭된 - 버튼
        if (countValue <= 0) {
            countValue = 0; // 음수로 내려가지 않도록 설정
            inputField.value = countValue;
            minusButton.disabled = true; // 0이면 비활성화
        }
    });
});


//모달창 부분
const modal = document.getElementById("modal");
const body = document.querySelector('body');
const closeBtn = document.getElementById('closeBtn');
const btn = document.getElementById("btn");

btn.addEventListener('click', ()=>{
    //제품의 개수가 모두 0이면 alert
    let totalCount = 0;
    let productCountDiv = document.querySelectorAll('.productCount');
    productCountDiv.forEach(div=>{
    console.log(div);
    //품절인 경우 disabled를 했기때문에 value가 없으므로 따로 처리..
    let countInput = div.querySelector('.countInput');
    if(countInput && !countInput.disabled){
        let count = parseInt(countInput.value) || 0; 
        totalCount += count;
    }


})

    if(totalCount === 0){
        alert('제품을 한개 이상 골라야 합니다.');
        return;
    }else{
        modal.style.display = 'block';
        body.style.overflow = 'hidden'; 
    }

    
});

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

function checkMyProduct(){

    let spnoList = [];

    let countInput = document.querySelectorAll(".countInput");
    //1. 각 countInput 요소를 순회한다
    countInput.forEach(input =>{
        //2. countInput의 value를 가져온다
        let countValue = parseInt(input.value);

        // 3. 0보다 크면 spno를 가져온다
        if(countValue > 0){
            //해당 data-spno 속성값 가져옴
            let spno = input.getAttribute('data-spno');
            spnoList.push(spno);
        }
    })
    
    //console.log(spnoList);
    //console.log(JSON.stringify({spnoList: spnoList}));
    //@@@@서버에다가 부탁해서 모달창에 뿌려주기@@@@
    $.ajax({
        url : '/salePath/choosedList',
        type : 'post',
        contentType : 'application/json',
        data : JSON.stringify(spnoList),
        success : function(response){
            //console.log(response);

            for(let i=0; i<response.length; i++){
                //console.log(i+"나번째 상품 : "+response[i].spvo.spname);
                //console.log(i+"나번째 이미지 : "+response[i].spivo);

                //console.log(JSON.stringify(response[i].spvo, null, 2)); // 객체를 보기 쉽게 문자열로 출력

            }

            displayProductList(response);
            updateTotalPrice();
        },
        error: function(xhr, status, error) {
            //console.error("Error occurred while fetching products:", error);
            console.error("Error occurred while fetching products:");
            console.error("Status: " + status);  // 상태 코드 확인
            console.error("Error: " + error);    // 에러 메시지 확인
            console.error("Response Text: " + xhr.responseText);  // 응답 내용 확인
        }
    })

}

//@@@서버에다가 받은 productList 데이터를 모달창에 표시@@@
function displayProductList(products){ //products = 선택된 spno 리스트
    //products -> spvo, spivo
    let modalBody = document.querySelector('.myProductList');
    modalBody.innerHTML = ''; //기존내용 초기화

    //countInput value 다시 가져오기..

    let totalPrice = 0;
    //let index = 0;

    products.forEach(product => {

        //countInput value 다시 가져오기..
        let countInput = document.querySelector(`input[data-spno="${product.spvo.spno}"]`);
        let countValue = parseInt(countInput.value);

        let productPrice = product.spvo.spprice * countValue;
        totalPrice += productPrice;

        if(product.spivo){
            var imageUrl = `/upload/saleProductImg/${product.spivo.saveDir.replace(/\\/g, '/')}/${product.spivo.uuid}_${product.spivo.fileName}`;

            var imgElement = document.createElement('img');
            imgElement.src = `${imageUrl}`;
            imgElement.alt = "사진없음";
    
            modalBody.innerHTML += `
                <div class="listProduct">
                    <div class="listProductSpno" data-spno=${product.spvo.spno} style="display: none;"></div>
                    <div class="listProductImg">
    
                    ${imgElement.outerHTML}
                    
                    </div>
                    <div class="listProductName" data-spname="${product.spvo.spname}">${product.spvo.spname}</div>
                    <div class="listProductPrice" data-spprice="${product.spvo.spprice}"> ${product.spvo.spprice}원 </div>
                    <div class="listProductCount" data-countValue=${countValue}> ${countValue} 개</div>
                </div>
            `;
        }else {
            modalBody.innerHTML += `
                <div class="listProduct">
                    <div class="listProductSpno" data-spno=${product.spvo.spno} style="display: none;"></div>
                    <div class="listProductImg">
    
                    <img class="pImg" src="/resources/img/defaultProductImg.png" alt="기본 이미지">
                    
                    </div>
                    <div class="listProductName" data-spname="${product.spvo.spname}">${product.spvo.spname}</div>
                    <div class="listProductPrice" data-spprice="${product.spvo.spprice}"> ${product.spvo.spprice}원 </div>
                    <div class="listProductCount" data-countValue=${countValue}> ${countValue} 개</div>
                </div>
            `;            
        }
    });

    document.getElementById("totalProductPriceContent").textContent = totalPrice+" 원";

    updateTotalPrice();

}

//참여버튼 
document.querySelector('.participationBtn').addEventListener('click', ()=>{
    //console.log("참여 회원 아이디 >> "+sessionId);
    let userProductList = []; //유저가 고른 상품 내역을 넣을 배열

    //let email = document.querySelector('.emailAddress').value;

    //제품번호, 제품이름, 제품가격, 수량 가져오기  + 회원아이디도
    let listProduct = document.querySelectorAll('.listProduct');
    listProduct.forEach(product =>{
        let spno = product.querySelector('.listProductSpno').getAttribute('data-spno');
        let spname = product.querySelector('.listProductName').getAttribute('data-spname');
        let spprice = product.querySelector('.listProductPrice').getAttribute('data-spprice');
        let count = product.querySelector('.listProductCount').getAttribute('data-countValue');

        userProductList.push({
            sno : sno,
            spno : spno,
            spname : spname,
            spprice : spprice,
            count : count,
            id : sessionId
        });

        

    });
    //console.log("userProductList >> "+JSON.stringify(userProductList, null, 2));

    let ordererName = document.querySelector('.ordererName').value;
    let ordererPhone = document.querySelector('.ordererPhone').value;
    let ordererEmail = document.querySelector('.emailAddress').value;
    let refundAccount = document.querySelector('.refundAccount').value;
    let refundBank = document.querySelector('.refundBank').value;
    let refundAccountHolder = document.querySelector('.refundAccountHolder').value;
    //let nickname = nickname;
    let id = sessionId;
    
    // console.log("sale_orderer정보 확인 >>> "+
    //         ordererName+", "+ordererPhone+", "+ordererEmail+", "+refundAccount+", "+
    //         refundBank+", "+refundAccountHolder+", "+id
    //         );
    
    let saleOrdererData = {
            sno : sno,
            id : sessionId,
            name : ordererName,
            email : ordererEmail,
            phoneNumber : ordererPhone,
            refundAccount : refundAccount,
            refundBank : refundBank,
            refundAccountHolder : refundAccountHolder
        };

    //sale_orderer_shipment(배송방법/배송비/수령인이름/수령인연락처/우편번호/주소/상세주소/배송관련메모)
    //선택된 배송방법/배송비
    let selectedShippingMethodValue = document.querySelector('input[name="shippingMethod"]:checked');
    let selectedShippingMethod, selectedShippingCost;

    if(selectedShippingMethodValue){
        let values = selectedShippingMethodValue.value.split(',');
        selectedShippingMethod = values[0].trim(); //배송방법
        selectedShippingCost= values[1].trim(); //배송가격
    }else{
        alert('배송방법을 선택해주세요.');
        return;
    }

    let shippingMethod = selectedShippingMethod;
    let shippingCost = selectedShippingCost;
    let recipientName = document.querySelector('.recipientName').value;
    let recipientPhoneNumber = document.querySelector('.recipientPhoneNumber').value;
    let recipientPostalCode = document.querySelector('.recipientPostalCode').value;
    let recipientAddress = document.querySelector(".recipientAddress").value;
    let recipientDetailAddress = document.querySelector('.recipientDetailAddress').value;
    let shippingNote = document.querySelector('.shippingNote').value;

    let saleOrdererShippingData = {
   		sno : sno,
        id : sessionId,
        recipientName : recipientName,
        recipientPhoneNumber : recipientPhoneNumber,
        recipientPostalCode : recipientPostalCode,
        recipientAddress : recipientAddress,
        recipientDetailAddress : recipientDetailAddress,
        shippingNote : shippingNote,
        shippingMethod : shippingMethod,
        shippingCost : shippingCost

    };

    $.ajax({
        url : "/salePath/participation",
        data : JSON.stringify({
            userProductList : userProductList, 
            saleOrdererData : saleOrdererData, 
            saleOrdererShippingData : saleOrdererShippingData }),
        dataType : 'json',
        type : 'post',
        contentType : 'application/json',
        success : function(response){
            //console.log(response.orderNo);
            if(response.orderNo === -1){
                alert("실패");
            }else{
                window.location.href = `/salePath/orderPayment/${response.sno}/${response.orderNo}`;  
            }
        },
        error: function(xhr, status, error) {
            //console.error("Error occurred while fetching products:", error);
            console.error("Error occurred while fetching products:");
            console.error("Status: " + status);  // 상태 코드 확인
            console.error("Error: " + error);    // 에러 메시지 확인
            console.error("Response Text: " + xhr.responseText);  // 응답 내용 확인
        }

    });

});


//배송비 추가
document.querySelectorAll(".shipmentMethodBtn").forEach(function(btn){
    btn.addEventListener('click', (event)=>{
        //console.log("라디오 버튼 클릭햇디야~");
        //클릭할때마다 값 해당 값 가져오기
        let value = event.target.value;

        let shipmentMethod = value.split(',')[0].trim();
        let shipmentCost = value.split(',')[1].trim();

        //console.log("선택된 배송방법 & 가격 : "+shipmentMethod+" , "+shipmentCost);

        //배송비쪽에 값 보내기
        document.getElementById("shipmentCostPrice").textContent = shipmentCost+" 원";

        updateTotalPrice();
    });
});


function updateTotalPrice(){
    //총상품금액가져오기
    let totalProductPriceText = document.getElementById("totalProductPriceContent").textContent;
    let totalProductPrice = parseInt(totalProductPriceText.replace(/[^0-9]/g, '')); // 숫자만 추출

    //선택된 배송비 가져오기
    let shipmentCostText = document.getElementById("shipmentCostPrice").textContent;
    let shipmentCost = parseInt(shipmentCostText.replace(/[^0-9]/g, '')); // 숫자만 추출

    let finalTotalPrice = totalProductPrice + (shipmentCost || 0); // 만약 shipmentCost가 NaN이라면 0 처리

    // 최종 금액 표시
    document.querySelector(".totalPrice").textContent = `${finalTotalPrice} 원`;
}


//우편번호찾기
function postCode() {
    new daum.Postcode({
        oncomplete: function(data) {

            var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 도로명 조합형 주소 변수

            // 법정동명이 있을 경우 추가
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
               extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }
            // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
            if(fullRoadAddr !== ''){
                fullRoadAddr += extraRoadAddr;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('recipientPostalCode').value = data.zonecode; //5자리 새우편번호 사용
            document.getElementById('recipientAddress').value = fullRoadAddr;

        }
    }).open();
}


function toggleFavoriteCount(icon){
    let sno = icon.getAttribute("data-sno");
    let currentStatus = icon.getAttribute("data-icon");

    //console.log("현재 아이콘 클래스: " + icon.classList);
    //console.log("현재 아이콘 상태 >>> "+currentStatus);

    if(sessionId === null || sessionId === ""){
        alert("로그인 후 이용해주세요.");
    }else{
        //현재 아이콘 클래스에서 하트 상태를 확인하기
        if(currentStatus === "empty"){
        //하트가 비어있음 -> 좋아요 추가해줘야함
            $.ajax({
            url:"/salePath/toggleFavorite",
            type : "post",
            data : JSON.stringify({sno:sno, action:"add", id:sessionId}),
            contentType : "application/json",
            success : function(response){ 
                if(response.result>0){//성공하면 하트상태바꾸고 좋아요 개수바꿈
                    //console.log(response.result);
                    icon.setAttribute("data-icon", "filled");
                    icon.classList.remove("fa-regular", "fa-heart");
                    icon.classList.add("fa-solid", "fa-heart");
                    document.getElementById("favoriteCount").textContent = response.favoriteCount;
                }else{
                    //console.log(response.result);
                    alert("좋아요 업데이트 실패");
                }
            },
            error: function(xhr, status, error) {
                console.error("Error updating favorite:", error);
            }
        });
        }else if(currentStatus === "filled"){
            //꽉찬하트 상태 -> 삭제해줘야함
            $.ajax({
                url:"/salePath/toggleFavorite",
                type : "post",
                data : JSON.stringify({sno:sno, action:"remove", id:sessionId}),
                contentType : "application/json",
                success : function(response){
                    if(response.result>0){
                        //console.log(response.result);
                        icon.setAttribute("data-icon", "empty");
                        icon.classList.remove("fa-solid", "fa-heart");
                        icon.classList.add("fa-regular", "fa-heart");   
                        document.getElementById("favoriteCount").textContent = response.favoriteCount;
                    }else{
                        //console.log(response.result);
                        alert("좋아요 업데이트 실패");
                    }
                },
                error: function(xhr, status, error) {
                console.error("Error updating favorite:", error);
            }
        })
        }
    }
    
}

window.onload = function(){
    console.log(isParticipated);
    let btn = document.getElementById("btn");
    if(isParticipated === 'true'){
        btn.disabled = true;
        btn.textContent = "이미 참여한 폼입니다.";
        btn.style.cursor = "not-allowed";
    }

    console.log("sessionId = "+sessionId+" , writer = "+writer);
    if(sessionId === writer){
        btn.disabled = true;
        btn.style.cursor = "not-allowed";
        btn.textContent = '자신의 글에 참여할 수 없습니다.';
    }
    console.log("status >> "+formStatus);
    if(formStatus === "before"){
        btn.disabled = true;
        btn.textContent = '폼이 시작되지 않았습니다.';
        btn.style.cursor = "not-allowed";
        
    }else if(formStatus === "ended"){
        btn.disabled = true;
        btn.textContent = "이미 종료된 폼입니다.";
        btn.style.cursor = "not-allowed";
    }
    
        if(sessionId === null || sessionId === ""){
        //console.log("로그인이 안되어있습니다");
        btn.disabled = true;
        btn.style.cursor = "not-allowed";
        btn.textContent = "로그인을 해야 참여할 수 있습니다.";
    }

}

//삭제확인 모달창
function openDeleteModal() {
    document.getElementById('deleteModal').style.display = 'flex';
}

function closeDeleteModal() {
    document.getElementById('deleteModal').style.display = 'none';
}
