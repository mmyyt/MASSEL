<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/userJoin.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
</head>
<body>




	<jsp:include page="../layout/header.jsp"></jsp:include>


	<!--========================================================-->

	<form action="/member/signUp" method="post" id="joinForm" enctype="multipart/form-data">

		<div class="wrap">
			<!--전체를 감싸는 디브  -->

			<div class="div0">
			
				<div class="joinTitle">회원가입</div>

				<hr class="line">
			</div>

			<!--======================== id ============================  -->
			<div class="div1">
				<!--아이디 div  -->
				<div class="idText">아이디</div>
				<div class="idIput">
					<input type="text" id="id" name="id" class="idArea"
						placeholder="아이디를 입력해주세요.">
				</div>
				<p id="idMsg" class="idMsg"></p>
			</div>

			<!--======================= pw ===========================  -->

			<div class="div2">
				<!-- 비밀번호 div  -->
				<div class="pwText">비밀번호</div>
				<div class="pwInput">
					<input type="password" id="pw" name="pw" class="pwArea"
						placeholder="비밀번호를 입력해주세요." autocomplete="off"> <span
						class="material-symbols-outlined pwVisibility">visibility </span>
					<span class="material-symbols-outlined pwVisibility_off"
						style="display: none;">visibility_off </span>
				</div>
				<p id="pwMsg" class="pwMsg"></p>
			</div>

			<!--==========================pw check ========================  -->
			<div class="div3">
				<!--비밀번호 확인 div  -->
				<div class="pwcText">재입력</div>
				<div class="pwcInput">
					<input type="password" id="pwCheck" class="pwcArea"
						placeholder="비밀번호를 재입력해주세요." autocomplete="off"> <span
						class="material-symbols-outlined pwcVisibility">visibility
					</span> <span class="material-symbols-outlined pwcVisibility_off"
						style="display: none;">visibility_off </span>
				</div>
				<p id="pwCheckMsg" class="pwcMsg"></p>
			</div>

			<!--=========================== nickname =====================  -->

			<div class="div4">
				<!--닉네임  -->
				<div class="nickText">닉네임</div>
				<div class="nickInput">
					<input type="text" id="nickname" class="nickArea" name="nickname"
						placeholder="닉네임을 입력해주세요.">
				</div>
				<p id="nicknameMsg" class="nickMsg"></p>
			</div>

			<!--=================== userprofile =============== -->

			<div class="div5">
				<div class="profileText">프로필</div>

				<div id="profileImgDiv">
					<div class="profileImg" id="profileImg">
						
					</div>
						<input type="file" id="userImgFile" name="userImgFile" style="display:none;"
						onchange="showUserImgFile(event)">

				</div>


			</div>
			<!--div5끝  -->

			<!--======================== email ==============================  -->

			<div class="div6">
				<!--이메일 div  -->
				<div class="emailText">이메일</div>
				<div class="emailInput">
					<input type="text" id="email" name="email" class="emailArea"
						placeholder="이메일을 입력해주세요." autocomplete="off">
				</div>
				<p id="emailMsg" class="emailMsg"></p>
			</div>

			<!-- ================== button =========================  -->

			<div class="div9">

				<button type="submit" id="signBtn" class="signBtn"
					disabled="disabled">회원가입</button>
				
			</div>






		</div>
		<!-- 전체를 감싸는 div끝  -->
	</form>


<jsp:include page="../layout/footer.jsp"></jsp:include>

<script type="text/javascript" src="/resources/js/userJoin.js"></script>



</body>
</html>