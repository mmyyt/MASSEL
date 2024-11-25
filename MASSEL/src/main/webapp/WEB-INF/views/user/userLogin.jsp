<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/userLogin.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
</head>
<body>

<jsp:include page="../layout/header.jsp"></jsp:include>

<div class="wrap">
		<div class="loginText">
			로그인
			<hr id="line">
		</div>
		<div class="loginBody">
		
		<form action="/member/login" method="post">
				<div class="idArea">
					<div class="idInput">
						<input type="text" name="id" id="id" class="idInputArea"
							placeholder="ID">
					</div>		
				</div>
	
	
				<div class="pwArea">
					<div class="pwInput">
						<input type="password" name="pw" id="pw" class="pwInputArea"
							placeholder="PASSWORD" autocomplete="off">
					</div>
				</div>


			<div class="btnArea">

<div class="loginButtonArea">
    <button type="submit" class="loginBtn">로그인</button>
    <div class="loginHoverArea">
      <button type="submit" class="loginBtnHover">MASSEL</button>
    </div>
 </div>	
 
 
<div class="joinButtonArea">
    <a href="/member/signUp"><button type="button" class="joinBtn">회원가입</button></a>
    <div class="joinHoverArea">
      <a href="/member/signUp"><button type="submit" class="joinBtnHover">MASSEL</button></a>
    </div>
 </div>	
 


			</div>
			<!--btnArea  -->

			</form>
			
		</div>
		<!--loginBody  -->
	</div>



	<div id="loginPopup" class="loginPopup">
		<div class="loinPopup_content">
			<div class="modalTitle">
			<div id="modalMessage"></div>
				<div class="closeBtn">
				<p class="closeText">확인</p></div>
			</div>
		</div>
	</div> 


<jsp:include page="../layout/footer.jsp"></jsp:include>

<script type="text/javascript">

   let msg_login = ${msg_login};
    const body = document.querySelector('body');

    let modalMessage = document.getElementById('modalMessage');
    const loginPopup = document.getElementById("loginPopup");

    function setMessage(text){
        modalMessage.innerText = text;
    }
    
    function closePopup() {
        loginPopup.style.display = "none";
        body.style.overflow = 'auto';
    }

    loginPopup.addEventListener('click', e =>{
        const eTarget = e.target
        if(eTarget.classList.contains('loginPopup')){
            loginPopup.style.display = 'none';
            body.style.overflow = 'auto';
       }
    })
    
    document.querySelector(".closeBtn").addEventListener("click", closePopup);
    
    if (msg_login === 0) {
        console.log("msg_login 0");

        loginPopup.style.display = "block";
        body.style.overflow = 'hidden';
        setMessage('일치하는 회원정보가 존재하지 않습니다.');
    }
    
    

</script> 
</body>
</html>