<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/userBuyerSaleForm.css">
</head>
<body>
<jsp:include page="../layout/header.jsp"></jsp:include>

<div class="wrapper">


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


    <div class="content">
        <div class="centerBox">
  
			<div class="sellerContent">
				
				<div class="sellerFormTitle1">
					구매내역
				</div>
				<div class="sellerFormTitle2">
					<div>상품   | </div>
					<div> 목록 : ${fn:length(list)}</div>
				</div>


			<c:choose>
				<c:when test="${not empty list}">
				<div class="sellerFormList">
				<c:forEach items="${list}" var="list">
						<c:choose>
						<c:when test="${list.svo.isDel == 0 }">
					<a href="/salePath/saleBuyerFormDetail?orderNo=${list.orderNo }">
					 	<div class="sellerSaleForm">
							<div class="sellerSaleFormImg">
								<div class="statusDiv">
								<c:choose>
									<c:when test="${list.svo.status == 'before' }">
										시작전
									</c:when>
									<c:when test="${list.svo.status == 'ongoing' }">
										진행중
									</c:when>
									<c:when test="${list.svo.status == 'ended' }">
										종료됨
									</c:when>
								</c:choose>									
								
								</div>
								<img alt="사진없음" class="dsimg" 
								src="/upload/saleImg/${fn: replace(list.sivo.saveDir, '\\','/')}/${list.sivo.uuid }_${list.sivo.fileName}">
							</div>
							<div class="saleBox">
								<div class="saleFormTitle">${list.svo.stitle }</div>
								<div>주문번호 ${list.orderNo }</div>
								<div class="saleFormRegDate">구매일 ${list.orderDate }</div>
							</div>
							<div class="saleBox2">
								<div>

								</div>
							</div>

						</div>
					</a>
						</c:when>
						<c:when test="${list.svo.isDel == 1}" >
						<a href="/salePath/saleBuyerFormDetail?orderNo=${list.orderNo }">
						<div class="sellerSaleForm">
							<div class="sellerSaleFormImg">
								<div class="imgOverlay"></div>
    								<div class="statusDiv deleteStatus">
        								삭제됨
    								</div>
								<img alt="사진없음" class="dsimg" 
								src="/upload/saleImg/${fn: replace(list.sivo.saveDir, '\\','/')}/${list.sivo.uuid }_${list.sivo.fileName}">
							</div>
							<div class="saleBox">
								<div class="saleFormTitle">${list.svo.stitle }</div>
								<div>주문번호 ${list.orderNo }</div>
								<div class="saleFormRegDate">구매일 ${list.orderDate }</div>
							</div>
							<div class="saleBox2">
								<div>
								</div>
							</div>

						</div>
						</a>
						</c:when>
						</c:choose>
					</c:forEach>
					
				</div>				
				
				</c:when>
				<c:when test="${empty list}">
					<div class="sellerFormList">
						내역이 없습니다.
					</div>
				</c:when>
			</c:choose>
			

				
			</div>

            	
            	
            	
                  
        </div>  <!-- contentBox  -->
    </div>  <!-- content -->
    
    
</div><!--wrapper끝  -->

<script type="text/javascript">

	document.querySelectorAll('.statusDiv').forEach(function(div) {
		// div의 텍스트 내용이 '진행중'일 때 배경색을 빨간색으로 변경
		if (div.textContent.trim() === '진행중') {
			div.style.backgroundColor = "rgba(255, 0, 0, 0.5)";
		}else if(div.textContent.trim() === '시작전'){
			div.style.backgroundColor = "rgba(0, 0, 255, 0.5)";
		}
	});
</script>


<jsp:include page="../layout/footer.jsp"></jsp:include>
</body>
</html>