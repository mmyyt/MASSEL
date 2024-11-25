<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.min.js"></script>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script src="https://kit.fontawesome.com/4facc1d037.js" crossorigin="anonymous"></script>
</head>
<link rel="stylesheet" type="text/css" href="/resources/css/layout.css">

<body>

<nav class="navBar">

	<div class="navWrapper">
		
		<div class="logo">
			<a href="/">MASSEL</a>
		</div>
		
		
		<div class="searchDiv">
			<div class="search">

					<i class="fas fa-regular fa-magnifying-glass"></i>
					<form action="/salePath/search" method="get">
						<input type="text" class="searchInput" placeholder="닉네임, 제목을 검색하세요."
						name = keyword>
						<button type="submit" class="header-searchBtn" onclick="searchReturn(event)">검색</button>
					</form>
			</div>
		</div>
		
		
		<c:choose>
			<c:when test="${ses.uvo.id eq null}">
				<div class="nauth-user">
					<a href="/member/login"><button>로그인</button></a>
					<a href="/member/signUp"><button>회원가입</button></a>
				</div>
			</c:when>
			<c:otherwise>
				
				<div class="yauth-user">
				
					<div class="header-userInfo">
						<div class="header-userImg" onclick="toggleMenu()">
							<c:choose>
								<c:when test="${not empty ses.uivo }">
									<img alt="없음" class="userProfileImg" 
									src="/upload/userImg/${fn: replace(ses.uivo.saveDir, '\\','/')}/${ses.uivo.uuid }_${ses.uivo.fileName}">
								</c:when>
								<c:otherwise>
									<img class="userProfileImg" src="/resources/img/defaultUserImg.png" alt="">
								</c:otherwise>
							</c:choose>
							
						</div>
						<div class="header-userNickname" onclick="toggleMenu()">
							${ses.uvo.nickname }
						</div>
						<div class="dropdown-menu">
							<div>
								<div class="dropdown-info">나의 정보</div>
								<a href="/member/info">
									<span><i class="fas fa-light fa-user"></i></span>
									정보보기
								</a>
								<a href="/salePath/favoriteList">
									<span><i class="fa-regular fa-face-grin-hearts"></i></span>
									찜목록
								</a>
							</div>
							<hr>
							<div>
								<div class="dropdown-sale">판매 보기</div>
								<a href="/member/sellerForm">
									<span><i class="fa-solid fa-shop"></i></span>
									상품 판매
								</a>
								<a href="/member/sellerDemandForm">
									<span><i class="fas fa-regular fa-clipboard-list"></i></span>
									수요조사 진행
								</a>
							</div>
							<hr>
							<div>
								<div class="dropdown-buy">구매 보기</div>
								<a href="/member/userSaleForm">
									<span><i class="fas fa-regular fa-basket-shopping"></i></span>
									상품 구매
								</a>
								<a href="/member/userDemandForm">
									<span><i class="fa-regular fa-pen-to-square"></i></span>
									수요조사 참여
								</a>
							</div>
							<hr>
								<a href="/member/logout">
									<span><i class="fas fa-regular fa-arrow-right-from-bracket"></i></span>
									로그아웃
								</a>

    					</div>
					</div>
					
						<div class="user-icon">
							<div class="bellDiv"><i class="fa-regular fa-bell" id="bell"></i></div>
							<div class="chatDiv" onclick="chatPopUp()">
								<i class="fa-regular fa-comments" id="chat"></i>
								<span class="chat-dot" id="chat-dot"></span>
							</div>
						</div>
					
				
				
					<div class="header-registerBtn">
						<div><button onclick="toggleMenu2()">등록하기</button></div>
						<div class="register-dropdown">
							<div>
								<div>
									<a href="/salePath/saleRegister">상품 판매 등록</a>
								</div>
								<div>
									<a href="/demandSurvey/register">수요조사 등록</a>
								</div>
							</div>
						
						</div>
					</div>
					
					
					<c:if test="${ses.uvo.role eq 'admin' }">
					<div class="header-adminBtn">
						<div><button onclick="toggleMenu3()">관리자메뉴</button></div>	
						<div class="admin-dropdown">
							<div>
								<div>
									<a href="/member/defaultRegister">파일등록</a>
								</div>
							</div>
						</div>
					
					</div>
					</c:if>
					
				
				</div>
			
			</c:otherwise>
		
		</c:choose>

		
	</div>



</nav>


<script type="text/javascript" src="/resources/js/header.js"></script>
<script type="text/javascript">
	let sessionId = "${ses.uvo.id}";

    function toggleMenu() {
        const menu = document.querySelector('.dropdown-menu');
        menu.style.display = menu.style.display === 'block' ? 'none' : 'block';
    } 
    
    function toggleMenu2() {
        const menu2 = document.querySelector('.register-dropdown');
        menu2.style.display = menu2.style.display === 'block' ? 'none' : 'block';
    } 
    
    function toggleMenu3() {
        const menu3 = document.querySelector('.admin-dropdown');
        menu3.style.display = menu3.style.display === 'block' ? 'none' : 'block';
    } 
</script>
</body>
</html>