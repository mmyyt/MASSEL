// if(sessionId === null || sessionId === ""){
//     console.log("로그인이 안되어있습니다");
// }else{
//     console.log("로그인이 되어있습니다");
// }

let dno = parseInt(tmpDno, 10);
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
                console.log("서버 응답 >> "+response);
                let roomId = response.roomId;
                console.log("roomId >>> "+roomId);
                let url = '/chat/room/'+roomId;
                let urlOption = "width = 600px, height=500px, scrollbars=yes";
                window.open(url, 'pop', urlOption);
            },
            error : function(error){
                console.log("방 생성 중 오류 발생 :", error);
            }
        })   
    }

}

document.querySelectorAll(".countBtnPlus").forEach(button => {
    button.addEventListener('click', (event)=>{
        let dpno = event.target.getAttribute('data-dpno');  //상품의 번호
        //console.log("plus btn dpno >>> "+dpno);
        let inputField = document.querySelector(`input[data-dpno="${dpno}"]`); //해당 dpno의 input창
        //console.log("inputField >> "+dpno+" >> "+inputField);
        let countValue = parseInt(inputField.value);
        countValue++;
        inputField.value = countValue;

        let minusButton = document.querySelector(`button.countBtnMinus[data-dpno="${dpno}"]`);
        minusButton.disabled = countValue <= 0;
    });
});

document.querySelectorAll(".countBtnMinus").forEach(button=>{
    button.addEventListener('click', (event)=>{
        let dpno = event.target.getAttribute('data-dpno');
        //console.log("minus btn dpno >>> "+dpno);
        let inputField = document.querySelector(`input[data-dpno="${dpno}"]`);
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
    let totalCount = 0;
    let productCountDiv = document.querySelectorAll('.productCount');
    productCountDiv.forEach(div=>{
    let count = parseInt(div.querySelector('.countInput').value); 
    totalCount += count;
    //console.log(totalCount);

})

    if(totalCount <= 0){
        alert('제품을 한개 이상 골라주세요.');
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

    let dpnoList = [];

    let countInput = document.querySelectorAll(".countInput");
    //1. 각 countInput 요소를 순회한다
    countInput.forEach(input =>{
        //2. countInput의 value를 가져온다
        let countValue = parseInt(input.value);

        // 3. 0보다 크면 dpno를 가져온다
        if(countValue > 0){
            //해당 data-dpno 속성값 가져옴
            let dpno = input.getAttribute('data-dpno');
            dpnoList.push(dpno);
        }
    })
    
    //console.log(dpnoList);
    //console.log(JSON.stringify({dpnoList: dpnoList}));
    //@@@@서버에다가 부탁해서 모달창에 뿌려주기@@@@
    $.ajax({
        url : '/demandSurvey/choosedList',
        type : 'post',
        contentType : 'application/json',
        data : JSON.stringify(dpnoList),  // dpnoList 자체를 JSON 배열로 전송
        success : function(response){
            displayProductList(response);
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
function displayProductList(products){
    let modalBody = document.querySelector('.myProductList');
    modalBody.innerHTML = ''; //기존내용 초기화

    products.forEach(product => {
        //countInput value 다시 가져오기..
        let countInput = document.querySelector(`input[data-dpno="${product.dpno}"]`);
        let countValue = parseInt(countInput.value);
        modalBody.innerHTML += `
            <div class="listProduct">
                <div class="listProductDpno" data-dpno=${product.dpno} style="display: none;"></div>
                <div class="listProductName" data-dpname="${product.dpname}">${product.dpname}</div>
                <div class="listProductPrice" data-dpprice="${product.dpprice}"> ${product.dpprice}원 </div>
                <div class="listProductCount" data-countValue=${countValue}> ${countValue} 개</div>
            </div>
        `;
    });
}

//참여버튼 
document.querySelector('.participationBtn').addEventListener('click', ()=>{
    //console.log("참여 회원 아이디 >> "+sessionId);
    let userProductList = []; //유저가 고른 상품 내역을 넣을 배열

    let email = document.querySelector('.emailAddress').value;

    //제품번호, 제품이름, 제품가격, 수량 가져오기  + 회원아이디도
    let listProduct = document.querySelectorAll('.listProduct');
    listProduct.forEach(product =>{
        let dpno = product.querySelector('.listProductDpno').getAttribute('data-dpno');
        let dpname = product.querySelector('.listProductName').getAttribute('data-dpname');
        let dpprice = product.querySelector('.listProductPrice').getAttribute('data-dpprice');
        let count = product.querySelector('.listProductCount').getAttribute('data-countValue');

        userProductList.push({
            dno : dno,
            dpno : dpno,
            dpname : dpname,
            dpprice : dpprice,
            count : count,
            id : sessionId,
            email : email,
            nickname : nickname

        });

    });
    //console.log("userProductList >> "+JSON.stringify(userProductList, null, 2));
    //value, replacer, space
    //replacer에 null을 넣은 의미 >> null은 모든 속성을 변환한다는 뜻

    //서버 전송해서 db넣기
    $.ajax({
        url : "/demandSurvey/participation",
        data : JSON.stringify(userProductList),
        dataType : 'text', //응답형식은 text
        type : 'post',
        contentType : 'application/json',
        success : function(response){
            console.log(response);
            window.location.href = `/demandSurvey/detail?dno=${dno}`;
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

window.onload = function(){
    let btn = document.getElementById("btn");
    //before, ended면 버튼ㅁ ㅏㄱ기
    
    if(formStatus === "before"){
        btn.disabled = true;
        btn.textContent = "폼이 시작되지 않았습니다.";
        btn.style.cursor = "not-allowed";
    }else if(formStatus === "ended"){
        btn.disabled = true;
        btn.textContent = "이미 종료된 폼입니다.";
        btn.style.cursor = "not-allowed";
    }

    if(isParticipated === "true"){
        btn.disabled = true;
        btn.textContet = "이미 참여한 폼입니다.";
        btn.style.cursor = "not-allowed";
    }

    if(sessionId === writer ){
        btn.disabled = true;
        btn.style.cursor = "not-allowed";
        btn.textContent = "자신의 글에 참여할 수 없습니다.";
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