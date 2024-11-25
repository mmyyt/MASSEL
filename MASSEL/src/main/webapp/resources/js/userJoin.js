let idflag;
let pwflag;
let pwcflag;
let emailflag;
let nicknameflag;

let signBtn = document.getElementById('signBtn');

function checkValidity(){

    if(idflag == true && pwflag == true && pwcflag == true && emailflag == true && nicknameflag == true){
        signBtn.disabled = false;

        signBtn.style.backgroundColor = "#beaae9";
        signBtn.style.color = "white";
        signBtn.style.cursor = "pointer";
        
    }else{
        signBtn.disabled = true;

        signBtn.style.backgroundColor = "#9F9F9F";
        signBtn.style.cursor = "not-allowed";

    }
}


//아이디 중복, 정규식 확인

const id = document.querySelector('#id');
const idMsg = document.querySelector('#idMsg');

id.addEventListener('keyup', function(){
    
    const pattern = /^(?=.*[a-zA-Z])[a-zA-Z0-9]{5,15}$/;
    // 영어 숫자 조합 5~15자리 영문자 최소 한개 포함되어야함

    $.ajax({
        url : "/member/idCheck",
        type : "POST",
        dataType : "JSON",
        data : {"id" : $("#id").val()},
        success : function(data) {

            if(id.value === ''){
                idMsg.innerHTML = `입력한 내용이 없습니다.`;
                idMsg.style.color = "#ff1a1a";
                idflag = false;
            }else {
                if(data == 1){
                    idMsg.innerHTML = `이미 존재하는 아이디입니다.`;
                    idMsg.style.color = "#ff1a1a";
                    idflag = false;

                }else if(!pattern.test(id.value)){
                idMsg.innerHTML = `영문, 숫자로 조합된 5~15자리여야 합니다.`;
                idMsg.style.color = "#ff1a1a";
                idflag = false;

            }else if(data == 0 && pattern.test(id.value)){
                idMsg.innerHTML = ``;
                idflag = true;
                //console.log("id flag : "+idflag);
            } 
        }
        checkValidity();
        }
    });
});

//비밀번호 정규식

const pw = document.querySelector('#pw');
const pwMsg = document.querySelector('#pwMsg');
const pwCheck = document.querySelector('#pwCheck');
const pwCheckMsg = document.querySelector('#pwCheckMsg');


function pwCheckFunction(event){

    const pattern = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,20}$/;
    //영문, 숫자, 특수문자 8-20자리 조합

    if(pw.value === ''){
        pwMsg.innerHTML = `입력한 내용이 없습니다.`;
        pwMsg.style.color = "#ff1a1a";
        pwflag = false;
    }else{
        if(!pattern.test(pw.value)){
            pwMsg.innerHTML = `영문, 숫자, 특수문자로 조합된 8~20자리여야 합니다.`;
            pwMsg.style.color = "#ff1a1a";
            pwflag = false;
    }else{
            pwMsg.innerHTML = ``;
            pwflag = true;
            console.log("pw flag : "+pwflag);
        }
    }
    //checkValidity();

        if(pwflag==true && pwCheck.value === ''){
            pwCheckMsg.innerHTML = `비밀번호를 재입력해주세요.`;
            pwCheckMsg.style.color = "#ff1a1a";
            pwcflag = false;
        }else{
            if(pwflag==true && pw.value !== pwCheck.value){
            pwCheckMsg.innerHTML = `비밀번호가 일치하지 않습니다.`;
            pwCheckMsg.style.color = "#ff1a1a";
            pwcflag = false;
            }else if(pwflag==true && pw.value === pwCheck.value){
            pwCheckMsg.innerHTML = ``;
            pwCheckMsg.style.color = "#ff1a1a";
            pwcflag = true;
            console.log("pw check flag : "+pwcflag);
            // }else if(pwflag==false && pw.value !== pwCheck.value){
            //     pwCheckMsg.innerHTML = `비밀번호가 일치하지 않습니다.`;
            //     pwCheckMsg.style.color = 'rgb(86, 83, 83)';
            //     pwcflag = false;
            //     //console.log("비밀번호 유효성검사 실패했을때>> pwflag : "+pwflag+" || pwcflag : "+pwflag);
            }else if(pw.value === '' && pw.value !== pwCheck.value){
                pwCheckMsg.innerHTML = `비밀번호가 일치하지 않습니다.`;
                pwCheckMsg.style.color = "#ff1a1a";
                pwcflag = false;
            }else if(pw.value !=='' && pw.value !== pwCheck.value){
                //pwCheckMsg.innerHTML = `비밀번호가 일치하지 않습니다.`;
                pwCheckMsg.style.color = "#ff1a1a";
                pwcflag = false;
            }

        }checkValidity();
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


//닉네임 중복 /정규식

const nickname = document.querySelector('#nickname');
const nicknameMsg = document.querySelector('#nicknameMsg');

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
                nicknameMsg.style.color= "#ff1a1a";
                nicknameflag = false;
            }else {
                if(data == 1){
                    nicknameMsg.innerHTML = `이미 존재하는 닉네임입니다.`;
                    nicknameMsg.style.color = "#ff1a1a";
                    nicknameflag = false;

                }else if(!pattern.test(nickname.value)){
                    nicknameMsg.innerHTML = `닉네임은 한글,영문,숫자를 이용한 2~20글자만 사용 가능합니다`;
                    nicknameMsg.style.color = "#ff1a1a";
                    nicknameflag = false;
                }else if(data == 0 && pattern.test(nickname.value)){
                    nicknameMsg.innerHTML = ``;
                    nicknameMsg.style.color = "#ff1a1a";
                    nicknameflag = true;
                    console.log("nickname flag : "+nicknameflag);
                }

            }checkValidity();
        }
    });//비동기 끝,,


});//nickname keyup function 끝..

//이메일 정규식 , 중복..

const emailInput = document.querySelector('#email');
const emailMsg = document.querySelector('#emailMsg');


emailInput.addEventListener('keyup', function(){
    
    const pattern = /^[a-z0-9_+.-]+@([a-z0-9-]+\.)+[a-z0-9]{2,4}$/;

    $.ajax({
        url : "/member/emailCheck",
        type : "POST",
        dataType : "JSON",
        data : {"email" : $("#email").val()},
        success : function(data) {
            
            if(emailInput.value ===''){
                emailMsg.innerHTML = `입력한 내용이 없습니다.`;
                emailMsg.style.color = "#ff1a1a";
                emailflag = false;

            }else{
                if(data == 1){
                    emailMsg.innerHTML = `이미 존재하는 이메일입니다.`;
                    emailMsg.style.color = "#ff1a1a";
                    emailflag = false;

                }else if(!pattern.test(emailInput.value)){
                    emailMsg.innerHTML = `이메일 형식이 올바르지 않습니다.`;
                    emailMsg.style.color = "#ff1a1a";
                    emailflag = false;

                }else if(data == 0 && pattern.test(emailInput.value)){
                    emailMsg.innerHTML = ``;
                    emailMsg.style.color = "#ff1a1a";
                    emailflag = true;
                    console.log("email flag : "+emailflag);
				
                }
            }checkValidity();
        }
    });
});

//유저 프로필 이미지
document.getElementById('profileImgDiv').addEventListener('click', function(){
    document.getElementById('userImgFile').click();
});

//썸네일 이미지 불러오기
function showUserImgFile(event){
    const profileImg = document.getElementById('profileImg');

    //기존 이미지 비우기
    profileImg.innerHTML = '';
    const file = event.target.files[0];
    
    const reader = new FileReader();
    reader.onload = function(event){
        const img = document.createElement("img");
        img.setAttribute("src", event.target.result);
        img.style.width = "100%";
        img.style.height = "100%";
        img.style.objectFit = "cover";
        profileImg.appendChild(img);
    };
    if(file){
        reader.readAsDataURL(file);
    }
}