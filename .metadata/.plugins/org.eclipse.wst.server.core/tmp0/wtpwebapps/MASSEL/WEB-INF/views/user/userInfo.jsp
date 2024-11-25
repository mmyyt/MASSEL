<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/userInfo.css">
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


		<div class="profileInfo">

			<div class="profileInfoTitle">
				<p class="profileInfoText">회원정보</p>
				<hr class="line">
			</div>

			<div class="profileInfoArea">

				<div class="userImg">
					<div class="userImgArea">
						<img alt="사진없음" class="profileImg" 
						src="/upload/userImg/${fn: replace(ses.uivo.saveDir, '\\','/')}/${ses.uivo.uuid }_${ses.uivo.fileName}">
						<div class="overlay">	
						 	<span class="overlayText">변경</span>
						</div>
					</div>
				</div>

				<div class="id">
					<div class="idTextArea">
						<p class="idText">아이디</p>
					</div>
					<div class="idValueArea">
						<p class="idValue">${ses.uvo.id }</p>
					</div>
				</div>
				<hr class="idLine">

				<div class="pw">
					<div class="pwTextArea">
						<p class="pwText">비밀번호</p>
					</div>
					<div class="pwEditBtnWrap">
						<button class="pwEditBtn">비밀번호 변경</button>
					</div>
				</div>
				<hr class="pwLine">
				
				
				<div class="nickname">
					<div class="nicknameArea">
						<p class="nicknameText">닉네임</p>
					</div>
					<div class="nicknameValueArea">
						<p class="nicknameValue">${ses.uvo.nickname }</p>
					</div>
					<div class="nicknameBtnWrap">
						<button class="nicknameEditBtn">닉네임 변경</button>
					</div>
				</div>
				<hr class="nicknameLine">

				<div class="email">
					<div class="emailTextArea">
						<p class="emailText">이메일</p>
					</div>
					<div class="emailValueArea">
						<p class="emailValue">${ses.uvo.email }</p>
					</div>
				</div>
				<hr class="emailLine">



			</div>

		</div>

	</div>
	

<!-- 모달창 -->

<!--비밀번호변경  -->

	<div id="pwModal" class="pwModal">
		<div class="pwModal_content">
				<div class="pwModalTitle">
					<div class="pwT">비밀번호 변경</div>
					<div class="pwCloseBtn">X</div>
				</div>
			<div class="pwModalArea">
				<div class="pwModalContent">
					
					
					<div class="pwChangeArea">
						<span>비밀번호</span>
						<input type="password" name="pw" id="pw">
						<span class="material-symbols-outlined pwVisibility">visibility
					</span> <span class="material-symbols-outlined pwVisibility_off"
						style="display: none;">visibility_off </span>
					</div>
					<p id="pwMsg" class="pwMsg"></p>	
					
					<div class="pwcChangeArea">
						<span>재입력</span>
						<input type="password" name="pwc" id="pwc">
						<span class="material-symbols-outlined pwVisibility">visibility
					</span> <span class="material-symbols-outlined pwVisibility_off"
						style="display: none;">visibility_off </span>
					
					</div>
					<p id="pwcMsg" class="pwcMsg"></p>	
					
				</div>
				<div class="pwChangeBtn" onclick="pwUpdate()">
					<p class="pwChangeText">수정</p>
				</div>
			</div>
		</div>
	</div> 
	
	
	<!--닉네임변경  -->
	<div id="nicknameModal" class="nicknameModal">
		<div class="nicknameModal_content">
				<div class="nicknameModalTitle">
					<div class="nicknameT">비밀번호 변경</div>
					<div class="nicknameCloseBtn">X</div>
				</div>
			<div class="nicknameModalArea">
				<div class="nicknameModalContent">
					
					
					<div class="nicknameChangeArea">
						<span>닉네임</span>
						<input type="text" name="nickname" id="nickname">
					</div>
					<p id="nicknameMsg" class="nicknameMsg"></p>	

					
				</div>
				<div class="nicknameChangeBtn" onclick="nicknameUpdate()">
					<p class="nicknameChangeText">수정</p>
				</div>
			</div>
		</div>
	</div> 
	
	
	<!--사진변경  -->
	
	<div id="imgModal" class="imgModal">
		<div class="imgModal_content">
				<div class="imgModalTitle">
					<div class="imgT">이미지 변경</div>
					<div class="imgCloseBtn">X</div>
				</div>
			
			<form action="/member/imgUpdate" method="post" enctype="multipart/form-data">
			<div class="imgModalArea">
				<div class="imgModalContent">
					
					
					<div class="imgChangeArea">
						
						
						<div class="thumbnailImg" id="thumbnailImg">
						
						</div>
						<input type="file" id="userImgFile" name="userImgFile" style="display:none;"
							onchange="showImg(event)">				
					</div>
	
				</div>
				<div class="imgChangeBtn">
					<button class="imgChangeText">수정</button>
				</div>
			</div>
			</form>
			
		</div>
	</div> 

	
	
<jsp:include page="../layout/footer.jsp"></jsp:include>

<script type="text/javascript" src="/resources/js/usrInfo.js"></script>
</body>
</html>