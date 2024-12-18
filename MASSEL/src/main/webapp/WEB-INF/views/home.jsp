<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<html>
<head>
<title>Home</title>
<script src="https://kit.fontawesome.com/4facc1d037.js" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/home.css">
</head>
<body>

<jsp:include page="./layout/header.jsp"></jsp:include>


<div class="wrapper">
	
	<div class="content">
		
		<!-- 슬라이더 영역 -->
<!-- 		<div class="slider">
		
			<div>진짜슬라이더 
			
			</div>
		</div> -->
		<!--슬라이더 끝  -->	
		
		<div id="joinPopup" class="popup hidden">
			<span id="joinPopupClose" class="joinPopupClose" onclick="hidePopup()">X</span>
			<span id="joinPopupMessage"></span>
		</div>		
		
		<!--메인영역  -->
		<div class="main">
		
			<div class="mainContent">
			
				<div> <!-- 카테고리영역  -->
					
				</div>
					
				<div class="popular-form"> <!-- 현재 가장 인기있는 폼 (조회수) -->
					<div class="popularTitle">오늘의 인기 폼</div>
					<div class="popularDate">${month }월 ${day }일 기준</div>
					
					<div class="popularBox"> <!--인기폼 box  -->
						<div class="popular-left"><!--왼쪽에가장큰부분  -->
						
							<c:choose>
								<c:when test="${not empty popularList[0]}">
							<div class="viewBox1">
								<a href="/salePath/detail?sno=${popularList[0].svo.sno}">
								<div class="viewBox1-Img">
									<div class="ranking1">1</div>
									<img alt="사진없음" class="p1-img pimg" 
									src="/upload/saleImg/${fn: replace(popularList[0].sivo.saveDir, '\\','/')}/${popularList[0].sivo.uuid }_${popularList[0].sivo.fileName}">
								</div>
								<div class="viewBox1-content boxContent">
									<div class="popular-writer">
									
							<c:choose>
								<c:when test="${not empty popularUivoList[0]}">
									<img alt="사진없음" class="p1-userImg" 
										src="/upload/userImg/${fn: replace(popularUivoList[0].saveDir, '\\','/')}/${popularUivoList[0].uuid }_${popularUivoList[0].fileName}">
								</c:when>
								<c:otherwise>
									<img class="p1-userImg" src="/resources/img/defaultUserImg.png" alt="">
								</c:otherwise>
							</c:choose>
										
										${popularList[0].svo.swriter }
									</div>
									<div class="popular-title">${popularList[0].svo.stitle }</div>
									<div>
										<i class="fa-solid fa-eye"></i>
										${popularList[0].svo.viewCount }
									</div>
								</div>
								</a>
							</div>								
								</c:when>
								<c:when test="${empty popularList[0]}">
								<div class="viewBox1">
									글이 없습니다.
								</div>
								
								</c:when>
							</c:choose>

							
						</div> 
						<div class="popular-right"><!--오른쪽에여러개 -->
						
							<c:choose>
								<c:when test="${not empty popularList[1] }">
							<div class="viewBox2">
								<a href="/salePath/detail?sno=${popularList[1].svo.sno}">
								<div class="viewBox2-Img">
									<div class="ranking">2</div>
									<img alt="사진없음" class="p2-img pimg" 
									src="/upload/saleImg/${fn: replace(popularList[1].sivo.saveDir, '\\','/')}/${popularList[1].sivo.uuid }_${popularList[1].sivo.fileName}">
								</div>
								<div class="viewBox2-content boxContent">
									<div>
									
							<c:choose>
								<c:when test="${not empty popularUivoList[1]}">
									<img alt="사진없음" class="p1-userImg" 
										src="/upload/userImg/${fn: replace(popularUivoList[1].saveDir, '\\','/')}/${popularUivoList[1].uuid }_${popularUivoList[1].fileName}">
								</c:when>
								<c:otherwise>
									<img class="p1-userImg" src="/resources/img/defaultUserImg.png" alt="">
								</c:otherwise>
							</c:choose>	
										${popularList[1].svo.swriter }
									</div>
									<div class="popular-title">${popularList[1].svo.stitle }</div>
									<div>
										<i class="fa-solid fa-eye"></i>
										${popularList[1].svo.viewCount }	
									</div>
								</div>
								</a>
							</div>								
								
								</c:when>
								<c:when test="${empty popularList[1] }">
									<div class="viewBox2">
										글이 없습니다.
									</div>
								</c:when>	
							
							
							</c:choose>

							<c:choose>
								<c:when test="${not empty popularList[2]}">
							<div class="viewBox3">
								<a href="/salePath/detail?sno=${popularList[2].svo.sno}">
								<div class="viewBox3-Img">
									<div class="ranking">3</div>
									<img alt="사진없음" class="p3-img pimg" 
									src="/upload/saleImg/${fn: replace(popularList[2].sivo.saveDir, '\\','/')}/${popularList[2].sivo.uuid }_${popularList[2].sivo.fileName}">
								</div>
								<div class="viewBox3-content boxContent">
									<div>
							<c:choose>
								<c:when test="${not empty popularUivoList[2]}">
									<img alt="사진없음" class="p1-userImg" 
										src="/upload/userImg/${fn: replace(popularUivoList[2].saveDir, '\\','/')}/${popularUivoList[2].uuid }_${popularUivoList[2].fileName}">
								</c:when>
								<c:otherwise>
									<img class="p1-userImg" src="/resources/img/defaultUserImg.png" alt="">
								</c:otherwise>
							</c:choose>
										${popularList[2].svo.swriter }
									
									</div>
									<div class="popular-title">${popularList[2].svo.stitle }</div>
									<div>
										<i class="fa-solid fa-eye"></i>
										${popularList[2].svo.viewCount }
									</div>
								</div>
								</a>
							</div>								
								</c:when>
								<c:when test="${empty popularList[2] }">
									<div class="viewBox3">
										글이 없습니다.
									</div>
								</c:when>
							</c:choose>							
							
							<c:choose>
								<c:when test="${not empty popularList[3]}">
							<div class="viewBox4">
								<a href="/salePath/detail?sno=${popularList[3].svo.sno}">
								<div class="viewBox4-Img">
									<div class="ranking">4</div>
									<img alt="사진없음" class="p4-img pimg" 
									src="/upload/saleImg/${fn: replace(popularList[3].sivo.saveDir, '\\','/')}/${popularList[3].sivo.uuid }_${popularList[3].sivo.fileName}">
								</div>
								<div class="viewBox4-content boxContent">
									<div>
							<c:choose>
								<c:when test="${not empty popularUivoList[3]}">
									<img alt="사진없음" class="p1-userImg" 
										src="/upload/userImg/${fn: replace(popularUivoList[3].saveDir, '\\','/')}/${popularUivoList[3].uuid }_${popularUivoList[3].fileName}">
								</c:when>
								<c:otherwise>
									<img class="p1-userImg" src="/resources/img/defaultUserImg.png" alt="">
								</c:otherwise>
							</c:choose>
									${popularList[3].svo.swriter }
									</div>
									<div class="popular-title">${popularList[3].svo.stitle }</div>
									<div>
										<i class="fa-solid fa-eye"></i>
										${popularList[3].svo.viewCount }
									</div>
								</div>
								</a>
							</div>								
								</c:when>
								<c:when test="${empty popularList[3]}">
									<div class="viewBox4">
										글이 없습니다.
									</div>
								</c:when>
							</c:choose>							

							<c:choose>
								<c:when test="${not empty popularList[4]}">
							<div class="viewBox5">
								<a href="/salePath/detail?sno=${popularList[4].svo.sno}">
								<div class="viewBox5-Img">
								 	<div class="ranking">5</div>
									<img alt="사진없음" class="p5-img pimg" 
									src="/upload/saleImg/${fn: replace(popularList[4].sivo.saveDir, '\\','/')}/${popularList[4].sivo.uuid }_${popularList[4].sivo.fileName}">
								</div>
								<div class="viewBox5-content boxContent">
									<div>
							<c:choose>
								<c:when test="${not empty popularUivoList[4]}">
									<img alt="사진없음" class="p1-userImg" 
										src="/upload/userImg/${fn: replace(popularUivoList[4].saveDir, '\\','/')}/${popularUivoList[4].uuid }_${popularUivoList[4].fileName}">
								</c:when>
								<c:otherwise>
									<img class="p1-userImg" src="/resources/img/defaultUserImg.png" alt="">
								</c:otherwise>
							</c:choose>
									${popularList[4].svo.swriter }
									</div>
									<div class="popular-title">${popularList[4].svo.stitle }</div>
									<div>
										<i class="fa-solid fa-eye"></i>
										${popularList[4].svo.viewCount }
									</div>
								</div>
								</a>
							</div>								
								</c:when>
								<c:when test="${empty popularList[4] }">
									<div class="viewBox5">
										글이 없습니다.
									</div>
								</c:when>
							</c:choose>							

							
						</div> 
					</div>
				</div>
				
				
				<div class="favorite-form"> <!-- 찜 -->
					<div class="favoriteTitle">찜을 많이 받은 폼</div>
					<div class="favoriteDate">${month }월 ${day }일 기준</div>
				
					<div class="favoriteBox"> <!--찜폼box  -->
					

							<c:choose>
								<c:when test="${not empty favoriteList }">
								
						<div class="favoriteBoxDiv">
						
							<c:forEach items="${favoriteList }" var="flist">
								<a href="/salePath/detail?sno=${flist.sidto.svo.sno}">
								<div class="favoriteCard">
									<div class="favoriteImg">
										<img alt="사진없음" class="" 
										src="/upload/saleImg/${fn: replace(flist.sidto.sivo.saveDir, '\\','/')}/${flist.sidto.sivo.uuid }_${flist.sidto.sivo.fileName}">
									</div> 
									<div class="favoriteBoxContent card-content">
										<div class="card-writer">
											<c:choose>
												<c:when test="${not empty flist.uivo }">
													<img alt="사진없음" class="favoriteUserImg" 
											src="/upload/userImg/${fn: replace(flist.uivo.saveDir, '\\','/')}/${flist.uivo.uuid }_${flist.uivo.fileName}">
													
												</c:when>
												<c:otherwise>
													<img src="/resources/img/defaultUserImg.png" alt="" class="favoriteUserImg">
													
												</c:otherwise>
											</c:choose>
											${flist.sidto.svo.swriter }
										</div>
										<div class="favorite-title card-title">
											${flist.sidto.svo.stitle }
										</div>
										<div class="favorite-likes card-likes">
											<span>❤️</span>${flist.sidto.svo.favoriteCount }
										</div>
										
									</div>
								</div>
								</a>
							</c:forEach>
							

						</div>								
								</c:when>
								<c:when test="${empty favoriteList }">
									<div class="favoriteBoxDiv">
										상품 글이 없습니다.
									</div>
								</c:when>
							</c:choose>
						
					</div>
				
				</div> <!-- 찜 -->
				
				<hr class="line">
				
				<div class="latest-form"> <!-- 최신순 -->
					<div class="latestTitle">
						<div class="lt">방금 올라온 NEW! 상품</div>
						<div class="saleForm-more"><a href="/salePath/list">더보기</a></div>
					</div>
					
					<c:choose>
						<c:when test="${not empty latestList }">
					<div class="latestBox">
						
						<c:forEach items="${latestList}" var="llist">
							
							<div class="latestBoxDiv">
								<a href="/salePath/detail?sno=${llist.sidto.svo.sno}">
								<div class="latestImg">
									<img alt="사진없음" class="latestUserImg" 
										src="/upload/saleImg/${fn: replace(llist.sidto.sivo.saveDir, '\\','/')}/${llist.sidto.sivo.uuid }_${llist.sidto.sivo.fileName}">
								</div>
								<div class="latestContent">
									<div class="latest-writer">
									
									<c:choose>
										<c:when test="${not empty llist.uivo }">
											<img alt="사진없음" class="latestProfileImg" 
											src="/upload/userImg/${fn: replace(llist.uivo.saveDir, '\\','/')}/${llist.uivo.uuid }_${llist.uivo.fileName}">
										</c:when>
										<c:otherwise>
											<img src="/resources/img/defaultUserImg.png" alt="" class="latestProfileImg">
										</c:otherwise>
									
									</c:choose>
									
										${llist.sidto.svo.swriter }
									</div>
									<div class="latest-title">
										${llist.sidto.svo.stitle }
									</div>
									
									<div class="latest-vf">
										<div class="latest-viewCount">
											<i class="fa-solid fa-eye"></i>
											${llist.sidto.svo.viewCount }
										</div>
										<div class="latest-favoriteCount">
											<i class="fa-solid fa-heart"></i>
											${llist.sidto.svo.favoriteCount }
										</div>
									</div>
								</div>
								</a>
							</div>
						
						</c:forEach>

							
						
					</div>						
						
						</c:when>
						<c:when test="${empty latestList }">
							<div class="latestBox">
								상품 글이 없습니다.
							</div>
						</c:when>
					</c:choose>
					

					
				</div>
				
				<hr class="line">
				
				<div> <!--수요조사  -->
				
				
				
				<div class="demand-form"> <!-- 최신순 -->
					<div class="demandTitle">
						<div class="dt">New 수요조사</div>
						<div class="demandForm-more"><a href="/demandSurvey/list">더보기</a></div>
					</div>
					
					<c:choose>
						<c:when test="${not empty demandList }">
						
					<div class="demandFormBox">
						
						<c:forEach items="${demandList}" var="dlist">
							
							<div class="demandDiv">
								<a href="/demandSurvey/detail?dno=${dlist.dsidto.dsvo.dno}">
								<div class="demandImg">
									<img alt="사진없음" class="" 
										src="/upload/demandSurveyImg/${fn: replace(dlist.dsidto.dsivo.saveDir, '\\','/')}/${dlist.dsidto.dsivo.uuid }_${dlist.dsidto.dsivo.fileName}">
								</div>
								<div class="demandContent">
									<div class="demand-writer">
									<c:choose>
										<c:when test="${not empty dlist.uivo }">
											<img alt="사진없음" class="demandUserImg" 
											src="/upload/userImg/${fn: replace(dlist.uivo.saveDir, '\\','/')}/${dlist.uivo.uuid }_${dlist.uivo.fileName}">
										</c:when>
										<c:otherwise>
											<img src="/resources/img/defaultUserImg.png" alt="" class="demandUserImg">
										</c:otherwise>
									
									</c:choose>
										${dlist.dsidto.dsvo.id }
									</div>
									<div class="demand-title">
										${dlist.dsidto.dsvo.dtitle }
									</div>
									

									<div class="demand-viewCount">
										<i class="fa-solid fa-eye"></i>
										${dlist.dsidto.dsvo.dviewCount }
									</div>

								</div>
								</a>
							</div>
						
						</c:forEach>

							
						
					</div>						
						
						</c:when>
						<c:when test="${empty demandList }">
						<div class="demandFormBox">
						
							수요조사 글이 없습니다.
							
						</div>
					</c:when>						
					</c:choose>
					

					
				</div>
	
				</div>
				
						
			</div>
		
		
		</div>
		<!-- 메인영역끝 -->
	
	
	</div>

	<!-- 슬라이더 영역 -->









</div>

<jsp:include page="./layout/footer.jsp"></jsp:include>
<script>
	const msgSignUp = ${msg_signUp != null ? msg_signUp : 'null'};
</script>
<script type="text/javascript" src="/resources/js/home.js"></script>
</body>
</html>
