//console.log(msgSignUp);
if(msgSignUp != null){
    if(msgSignUp === 0 || msgSignUp === 1){
        showPopup(msgSignUp);
    }
}

function showPopup(message) {
    let popupMessage = document.getElementById("joinPopupMessage");
    if(message === 1){
        //console.log("성공");
        popupMessage.innerHTML = "🎉 회원가입에 성공 했습니다. 로그인을 해주세요. 🎉";
    }else if(message === 0){
        //console.log("실패");
        popupMessage.innerHTML = "❗ 회원가입에 실패 했습니다. 고객센터에 문의해주세요. ❗";
    }

    const popup = document.getElementById("joinPopup");

    popup.classList.remove("hidden"); // 팝업 표시
    popup.classList.add("visible");

    // 20초 후 자동 닫기
    setTimeout(hidePopup, 20000);
}

function hidePopup() {
    const popup = document.getElementById("joinPopup");
    popup.classList.add("hidden"); // 팝업 숨김
    popup.classList.remove("visible");
}
