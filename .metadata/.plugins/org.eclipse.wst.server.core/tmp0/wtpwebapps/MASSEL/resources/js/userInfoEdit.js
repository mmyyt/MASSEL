let pw = document.querySelector('#pw');
let pwc = document.querySelector('#pwc');
let pwMsg = document.querySelector('#pwMsg');
let pwcMsg = document.querySelector('#pwCheckMsg');
let updateBtn = document.getElementById('updateBtn');

let pwflag = true;
pwMsg.innerHTML = ``;
pwcMsg.innerHTML = ``;

const profile_div = document.getElementById('profileImgDiv')
   
const imgChoiceBtn = document.getElementById('imgChoiceBtn');

const imgBox = document.querySelectorAll('.imgBox');
const profile_img = document.querySelectorAll('.profile_img');
const uuid = document.querySelector('#uuid');

   let selectedImage = null;
   const checkmarkIcon = document.getElementById("checkmarkIcon");
   const selectedImageWrapper = document.getElementById("selectedImageWrapper");

function pwCheck(){
    
    const pattern = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,20}$/;

    if(pw.value === ''){
        if(pwc.value === ''){
            pwflag = true;
            pwMsg.innerHTML = ``;
            pwcMsg.innerHTML = ``;
            //console.log("pw 빈값, pwc 빈값 >> pwflag = "+pwflag);
        }else if(pwc.value != ''){
            pwflag = false;
            pwcMsg.innerHTML = `비밀번호가 일치하지 않습니다.`;
            //console.log("pw 빈값 , pwc 값 있음 >> pwflag ="+pwflag);
        }
    }else if(pattern.test(pw.value)){
        if(pw.value === pwc.value){
            pwflag = true;
            pwMsg.innerHTML = ``;
            pwcMsg.innerHTML = ``;
            //console.log("pw 정규식 통과 , pw랑 pwc 값 같음 >>>pwflag : "+pwflag);
        }else if(pw.value !== pwc.value){
            pwflag = false;
            pwMsg.innerHTML = ``;
            pwcMsg.innerHTML = `비밀번호가 일치하지 않습니다.`;
            //console.log("pw정규식 통과, pw랑 pwc값 다름 >>>pwflag : "+pwflag);
        }
    }else if(!(pattern.test(pw.value))){
        pwflag =  false;
        pwMsg.innerHTML = `영문, 숫자, 특수문자로 조합된 8~20자리여야 합니다.`;
        //console.log("pw 정규식 X >> pwflag >> "+pwflag);
    }


    if(pwc.value !== ''){
        if((pattern.test(pw.value)) && pw.value === pwc.value){
            pwflag = true;
            pwMsg.innerHTML = ``;
            pwcMsg.innerHTML = ``;
        }
    }else if(pwc.value === ''){
        if(pw.value===''){
            pwflag = true;
            pwMsg.innerHTML = ``;
            pwcMsg.innerHTML = ``;
        }
    }

}

pw.addEventListener('keyup', pwCheck);
pwc.addEventListener('keyup', pwCheck);

let nickname = document.querySelector('#nickname');
let nickMsg = document.querySelector('#nickMsg');
let nickflag = true;

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
                    nickMsg.innerHTML = `입력한 내용이 없습니다.`;
                    nickMsg.style.color= 'rgb(86, 83, 83)';
                    nickflag = false;
                }else {
                    if(data == 1){
                        nickMsg.innerHTML = `이미 존재하는 닉네임입니다.`;
                        nickMsg.style.color = 'rgb(86, 83, 83)';
                        nickflag = false;
                    }else if(!pattern.test(nickname.value)){
                        nickMsg.innerHTML = `닉네임은 한글,영문,숫자를 이용한 2~20글자만 사용 가능합니다`;
                        nickMsg.style.color = 'rgb(86, 83, 83)';
                        nickflag = false;
                    }else if(data == 0 && pattern.test(nickname.value)){
                        nickMsg.innerHTML = ``;
                        nickMsg.style.color = 'rgb(86, 83, 83)';
                        nickflag = true;
                        //console.log("nickname flag : "+nickflag);
                    }
    
                }
            }
        });
    });


//모달창 부분

const body = document.querySelector("body");
const userUpdatePopup = document.getElementById("userUpdatePopup");

let modalMessage = document.getElementById('modalMessage');

function setMessage(text){
    modalMessage.innerText =  text;
}

function closePopup() {
    userUpdatePopup.style.display = "none";
    body.style.overflow = "auto";
}

document.querySelector(".userUpdateCloseBtn").addEventListener("click", closePopup);

userUpdatePopup.addEventListener('click', e =>{
    const eTarget = e.target
    if(eTarget.classList.contains('userUpdatePopup')){
        userUpdatePopup.style.display = 'none';
        body.style.overflow = 'auto';
   }
})

function update(){

    $.ajax({
        url : "/member/edit",
        type : "POST",
        dataType : "JSON",
        data : {
            "id" : $("#id").val(),
            "pw" : $("#pw").val(),
            "email" : $("#email").val(),
            "nickname" : $("#nickname").val(),
            "birth" : $("#birth").val(),
            "uuid" : uuid.value
        },
        success : function(data){
                //console.log("회원정보수정!!!!!!!!!!");
                
                userUpdatePopup.style.display = "block";
                body.style.overflow = "hidden";
                setMessage('회원정보가 수정되었습니다.');

                // alert('회원정보가 수정되었습니다');
                window.location.href = '/member/info'


        },
        error : function(request, status, error){
            alert("error발생! "+requestAnimationFrame.status+"\n"+"message"+request.responseText+"\n"+"error"+error);
        }
    
    });

};

function validation(){

    if(pwflag === true && nickflag == true){
        // console.log('버튼눌렀을때 pwflag ===>> '+pwflag)
        // console.log("hiddenUuid>>>>>>>>>>>>>>>>>>>>>>"+$("#uuid").val());
        // console.log("hiddenUuid>>>>>>>>>>>>>>>>>>>>>>"+uuid.value);
        update();
    }else if(pwflag === false){
        userUpdatePopup.style.display = "block";
        body.style.overflow = "hidden";
        setMessage('비밀번호를 다시 입력해주세요.');
        // alert('비밀번호를 다시 입력해주세요.');
        // console.log('버튼눌렀을때 pwflag ===>> '+pwflag)
        // console.log("nickname>>>>>>>>>>>>>>>>>>>>>>>"+$("#nickname").val());
        // console.log("hiddenUuid>>>>>>>>>>>>>>>>>>>>>>"+$("#uuid").val());
    }else if(nickflag == false){
        userUpdatePopup.style.display = "block";
        body.style.overflow = "hidden";
        setMessage('닉네임을 다시 입력해주세요.');
        // alert('닉네임을 다시 입력해주세요.');
        // console.log("nickname>>>>>>>>>>>>>>>>>>>>>>>"+$("#nickname").val());
        // console.log("hiddenUuid>>>>>>>>>>>>>>>>>>>>>>"+$("#uuid").val());
        // console.log('버튼눌렀을때 nicknameflag ===>> '+nickflag);
    }
};

updateBtn.addEventListener('click', validation);



$(document).ready(function () {
  $('.pwVisibility').on('click', function () {
    let pw = $(this).siblings('ul').find('input');
    let pwInputType = pw.attr('type');

    if (pwInputType === 'password') {
      pw.prop('type', 'text');
      $(this).html('visibility_off');
    } else {
      pw.prop('type', 'password');
      $(this).html('visibility');
    }
  });

  $('.pwcVisibility').on('click', function () {
    let pw = $(this).siblings('ul').find('input');
    let pwcInputType = pw.attr('type');

    if (pwcInputType === 'password') {
      pw.prop('type', 'text');
      $(this).html('visibility_off');
    } else {
      pw.prop('type', 'password');
      $(this).html('visibility');
    }
  });
});
