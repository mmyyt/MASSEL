const body = document.querySelector('body');

//비밀번호 변경 모달
const pwModal = document.getElementById("pwModal");

document.querySelector('.pwEditBtn').addEventListener('click',()=>{
    pwModal.style.display = "block";
    body.style.overflow = "hidden";
})

document.querySelector(".pwCloseBtn").addEventListener("click", ()=>{
    pwModal.style.display = "none";
    body.style.overflow = 'auto';
});

let pwflag = false;
let pwcflag = false;

let pwChangeBtn = document.querySelector('.pwChangeSubmit');
function pwCheckValidity(){
    if(pwflag === false || pwcflag === false){
        pwChangeBtn.disabled = true;
        pwChangeBtn.style.opacity = '0.5'; 
        pwChangeBtn.style.cursor = 'not-allowed'; 
        pwChangeBtn.style.backgroundColor = "#ccc";
    }else if(pwflag === true && pwcflag === true){
        pwChangeBtn.disabled = false;
        pwChangeBtn.style.opacity = '1';
        pwChangeBtn.style.cursor = 'pointer';
        pwChangeBtn.style.backgroundColor = "#beaae9";
    }
}

const pw = document.getElementById('pw');
const pwMsg = document.querySelector('#pwMsg');
const pwCheck = document.getElementById('pwc');
const pwCheckMsg = document.querySelector('#pwcMsg');

//비밀번호 정규식
function pwCheckFunction(event){

    const pattern = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,20}$/;
    //영문, 숫자, 특수문자 8-20자리 조합

    if(pw.value === ''){
        pwMsg.innerHTML = `입력한 내용이 없습니다.`;
        pwMsg.style.color = 'rgb(86, 83, 83)';;
        pwflag = false;
    }else{
        if(!pattern.test(pw.value)){
            pwMsg.innerHTML = `영문, 숫자, 특수문자로 조합된 8~20자리여야 합니다.`;
            pwMsg.style.color = 'rgb(86, 83, 83)';;
            pwflag = false;
    }else{
            pwMsg.innerHTML = ``;
            pwflag = true;
            //console.log("pw flag : "+pwflag);
        }
    }
    //checkValidity();

        if(pwflag==true && pwCheck.value === ''){
            pwCheckMsg.innerHTML = `비밀번호를 재입력해주세요.`;
            pwCheckMsg.style.color = `rgb(86, 83, 83)`;
            pwcflag = false;
        }else{
            if(pwflag==true && pw.value !== pwCheck.value){
            pwCheckMsg.innerHTML = `비밀번호가 일치하지 않습니다.`;
            pwCheckMsg.style.color = 'rgb(86, 83, 83)';
            pwcflag = false;
            }else if(pwflag==true && pw.value === pwCheck.value){
            pwCheckMsg.innerHTML = ``;
            pwCheckMsg.style.color = 'rgb(86, 83, 83)';
            pwcflag = true;
            //console.log("pw check flag : "+pwcflag);
            // }else if(pwflag==false && pw.value !== pwCheck.value){
            //     pwCheckMsg.innerHTML = `비밀번호가 일치하지 않습니다.`;
            //     pwCheckMsg.style.color = 'rgb(86, 83, 83)';
            //     pwcflag = false;
            //     //console.log("비밀번호 유효성검사 실패했을때>> pwflag : "+pwflag+" || pwcflag : "+pwflag);
            }else if(pw.value === '' && pw.value !== pwCheck.value){
                pwCheckMsg.innerHTML = `비밀번호가 일치하지 않습니다.`;
                pwCheckMsg.style.color = 'rgb(86, 83, 83)';
                pwcflag = false;
            }else if(pw.value !=='' && pw.value !== pwCheck.value){
                //pwCheckMsg.innerHTML = `비밀번호가 일치하지 않습니다.`;
                pwCheckMsg.style.color = 'rgb(86, 83, 83)';
                pwcflag = false;
            }

        }pwCheckValidity();
}
        

pw.addEventListener('keyup', pwCheckFunction);
pwCheck.addEventListener('keyup', pwCheckFunction);

$(document).ready(function () {
    $('.pwVisibility').on('click', function () {
        let pwInput = $(this).prev('input');
        let pwInputType = pwInput.attr('type');

        if (pwInputType === 'password') {
            pwInput.prop('type', 'text');
            $(this).html('visibility_off');
        } else {
            pwInput.prop('type', 'password');
            $(this).html('visibility');
        }

    });
});

$(document).ready(function () {
    $('.pwcVisibility').on('click', function () {
        let pwcInput = $(this).prev('input');
        let pwcInputType = pwcInput.attr('type');

        if (pwcInputType === 'password') {
            pwcInput.prop('type', 'text');
            $(this).html('visibility_off');
        } else {
            pwcInput.prop('type', 'password');
            $(this).html('visibility');
        }
    });
});
//비밀번호 서버로 전송
function pwUpdate(){

    $.ajax({
        url : "/member/pwEdit",
        type : "POST",
        dataType : "JSON",
        data : {
            "pw" : $("#pw").val(),
        },
        success : function(data){

                //console.log("비밀번호 변경 결과 >> "+data);
                if(data>0){
                    //console.log("비밀번호 변경 성공");
                    window.location.href = '/member/info';
                }
        },
        error : function(request, status, error){
            alert("error발생! "+requestAnimationFrame.status+"\n"+"message"+request.responseText+"\n"+"error"+error);
        }
    
    });

};

//닉네임 모달
const nickModal = document.getElementById("nicknameModal");

document.querySelector('.nicknameEditBtn').addEventListener('click',()=>{
    nickModal.style.display = "block";
    body.style.overflow = "hidden";
})

document.querySelector(".nicknameCloseBtn").addEventListener("click", ()=>{
    nickModal.style.display = "none";
    body.style.overflow = 'auto';
});

//닉네임 중복/정귯기
const nickname = document.querySelector('#nickname');
const nicknameMsg = document.querySelector('#nicknameMsg');

let nicknameflag = false;

let nickChangeBtn = document.querySelector('.nicknameChangeSubmit');

function nicknameCheckValidity(){
    if(nicknameflag === false){
        nickChangeBtn.disabled = true;
        nickChangeBtn.style.opacity = '0.5'; 
        nickChangeBtn.style.cursor = 'not-allowed';
        nickChangeBtn.style.backgroundColor = "#ccc";
    }else if(nicknameflag === true){
        nickChangeBtn.disabled = false;
        nickChangeBtn.style.opacity = '1';
        nickChangeBtn.style.cursor = 'pointer';
        nickChangeBtn.style.backgroundColor = "#beaae9";
    }
}

nickname.addEventListener('keyup', function(){

    const pattern = /^[가-힣a-zA-Z0-9]{2,20}$/;
    //2-20글자 한글,영어,숫자 

    $.ajax({
        url : "/member/nicknameCheck",
        type : "POST",
        dataType : "JSON",
        data : {"nickname" : $("#nickname").val()},
        success : function(data){
            if(nickname.value === ''){
                nicknameMsg.innerHTML = `입력한 내용이 없습니다.`;
                nicknameMsg.style.color= 'rgb(86, 83, 83)';
                nicknameflag = false;
            }else {
                if(data == 1){
                    nicknameMsg.innerHTML = `이미 존재하는 닉네임입니다.`;
                    nicknameMsg.style.color = 'rgb(86, 83, 83)';
                    nicknameflag = false;

                }else if(!pattern.test(nickname.value)){
                    nicknameMsg.innerHTML = `닉네임은 한글,영문,숫자를 이용한 2~20글자만 사용 가능합니다`;
                    nicknameMsg.style.color = 'rgb(86, 83, 83)';
                    nicknameflag = false;
                }else if(data == 0 && pattern.test(nickname.value)){
                    nicknameMsg.innerHTML = ``;
                    nicknameMsg.style.color = 'rgb(86, 83, 83)';
                    nicknameflag = true;
                    //console.log("nickname flag : "+nicknameflag);
                }

            }nicknameCheckValidity();
        }
    });//비동기 끝,,


});//nickname keyup function 끝..

//닉네임 수정
function nicknameUpdate(){
    $.ajax({
        url : "/member/nickEdit",
        type : "POST",
        dataType : "JSON",
        data : {"nickname" : $("#nickname").val()},
        success : function(data){

                //console.log("닉네임 변경 결과 >> "+data);
                if(data>0){
                    //console.log("닉네임 변경 성공");
                    window.location.replace('/member/info');
                }
        },
        error : function(request, status, error){
            alert("error발생! "+requestAnimationFrame.status+"\n"+"message"+request.responseText+"\n"+"error"+error);
        }
    
    });
}


//이미지변경
const imgModal = document.getElementById("imgModal");

document.querySelector('.overlay').addEventListener('click',()=>{
    imgModal.style.display = "block";
    body.style.overflow = "hidden";
})

document.querySelector(".imgCloseBtn").addEventListener("click", ()=>{
    imgModal.style.display = "none";
    body.style.overflow = 'auto';
});

document.getElementById('thumbnailImg').addEventListener('click', function(){
    document.getElementById('userImgFile').click();
});

const regExpImg = new RegExp("\.(jpg|jpeg|png|gif|bmp)$", "i");

//이미지 불러오기
function showImg(event){
    const thumbnailImg = document.getElementById('thumbnailImg');

    //기존 이미지 비우기
    thumbnailImg.innerHTML = '';

    const file = event.target.files[0];

    if(!file){
        thumbnailArea.innerHTML = '썸네일 이미지를 등록해주세요.';
        return;
    }
    //이미지 파일만 업로드할 수 있게
    if(!regExpImg.test(file.name)){
        alert("이미지 파일만 업로드할 수 있습니다.");
        event.target.value = "";
        thumbnailArea.innerHTML = '썸네일 이미지를 등록해주세요.';
        return;
    }

    const reader = new FileReader();
    reader.onload = function(event){
        const img = document.createElement("img");
        img.setAttribute("src", event.target.result);
        img.style.width = "100%";
        img.style.height = "100%";
        img.style.objectFit = "cover";
        img.style.borderRadius = "50%";
        thumbnailImg.appendChild(img);
    };
    if(file){
        reader.readAsDataURL(file);
    }
}

function imgCheck(){
    const userImgFile = document.getElementById("userImgFile");
    const imgChangeSubmit = document.getElementById("imgChangeSubmit");
    if(userImgFile.files.length > 0){
        imgChangeSubmit.disabled = false;
        imgChangeSubmit.style.cursor = "pointer";
        imgChangeSubmit.style.backgroundColor = "#beaae9";
    }else{
        submitButton.disabled = true;
        submitButton.style.cursor = "not-allowed"; // 클릭 불가
    }
}

document.getElementById("userImgFile").addEventListener("change", imgCheck);