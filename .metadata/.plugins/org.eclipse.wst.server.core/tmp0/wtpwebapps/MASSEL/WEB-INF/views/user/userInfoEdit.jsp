<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/userInfoEdit.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />

</head>
<body>

<jsp:include page="../layout/header.jsp"></jsp:include>


<div class="wrap">

    <div class="sideBar">
        <div class="sideBarContent">
            <div class="myInfo">
                <div class="myInfoTop">
                    <p class="myInfoText">나의정보</p>
                </div>
                <ul>
                    <li><a href="/member/info">정보보기</a></li>
                    <li><a href="">정보수정</a></li>
                    <li><a href="/salePath/favoriteList">찜목록</a></li>
                </ul>
            </div>	

            <div class="seller">
                <div class="sellerTop">
                    <p class="sellerText">판매내역</p>
                </div>
                <ul>
                    <li><a href="/member/sellerForm">상품</a></li>
                    <li><a href="/member/sellerDemandForm">수요조사</a></li>
                </ul>
            </div>	

            <div class="buyer">
                <div class="buyerTop">
                    <p class="buyerText">구매내역</p>
                </div>
                <ul>
                    <li><a href="/member/userSaleForm">상품</a></li>
                    <li><a href="/member/userDemandForm">수요조사</a></li>
                </ul>
            </div>
        </div>
    </div><!--sidebar끝  -->


<form action="/member/edit" method="post">

		<div class="userUpdateArea">

			<div class="updateTextArea">
				<p class="updateText">회원정보 변경</p>
				<hr class="updateLine">
			</div>

			<!--=============================================  -->

			<div class="updateArea">



			<div class="userProfileImgArea">
				<div class="profileText">프로필</div>

				<div id="profileImgDiv" class="profileImgDiv">
					<div>
						<img alt="없음" id="${ses.uivo.uuid }"
						src="/upload/userImg/${fn: replace(ses.uivo.saveDir, '\\','/')}/${ses.uivo.uuid }_${ses.uivo.fileName}"
						class="SelecteduserImg">
					</div>

				</div>


			</div>

				<div class="idArea">
					<ul>
						<li><span class="idText">아이디</span></li>
						<li><input type="text" name="id" id="id" class="id"
							value="${ses.uvo.id }" readonly="readonly"></li>
					</ul>
				</div>

				<div class="pwArea">
					<ul>
						<li><span class="pwText">비밀번호</span></li>
						<li><input type="password" name="pw" id="pw" class="pw"
							autocomplete="off"></li>
					</ul>
					<span class="material-symbols-outlined pwVisibility">visibility
					</span> <span class="material-symbols-outlined pwVisibility_off"
						style="display: none;">visibility_off </span>
					<p id="pwMsg" class="pwMsg"></p>
				</div>

				<div class="pwcArea">
					<ul>
						<li><span class="pwcText">재입력</span></li>
						<li><input type="password" name="pwc" id="pwc" class="pwc"
							autocomplete="off"></li>
					</ul>
					<span class="material-symbols-outlined pwcVisibility">visibility
					</span> <span class="material-symbols-outlined pwcVisibility_off"
						style="display: none;">visibility_off </span>
					<p id="pwCheckMsg" class="pwCheckMsg"></p>
				</div>

				<div class="emailArea">
					<ul>
						<li><span class="emailText">이메일</span></li>
						<li><input type="text" name="email" id="email" class="email"
							value="${ses.uvo.email }" readonly="readonly"></li>
					</ul>
				</div>

				<div class="nicknameArea">
					<ul>
						<li><span class="nicknameText">닉네임</span></li>
						<li><input type="text" name="nickname" id="nickname"
							class="nickname" value="${ses.uvo.nickname }"></li>
					</ul>
					<p id="nickMsg" class="nickMsg"></p>
				</div>

				<div class="btnArea">
					<button type="button" id="updateBtn" class="updateBtn">수정하기</button>
				</div>

			</div>

		</div>
	</form>
</div>
	

<jsp:include page="../layout/footer.jsp"></jsp:include>

<script type="text/javascript" src="/resources/js/userInfoEdit.js"></script>

</body>
</html>