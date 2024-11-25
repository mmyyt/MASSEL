<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/saleSellerDetail.css">
<script src="https://kit.fontawesome.com/4facc1d037.js" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
            	
            	
            	<div class="topBox"> <!-- 썸네일, 제목, 기간, 현재상태, 수정/삭제/보러가기 버튼   -->
            		
            		<div class="thumbnail"> <!--썸네일  -->
            			<img alt="사진없음" class="dsimg" 
						src="/upload/saleImg/${fn: replace(detail.sivo.saveDir, '\\','/')}
						/${detail.sivo.uuid }_${detail.sivo.fileName}">
            		</div>  <!--썸네일  -->
            		
            		<div class="rightDiv"> <!-- 제목, 기간, 상태, 버튼 -->
            		
            			<div class="state">
            				<div class="statusDiv">
								<c:choose>
									<c:when test="${detail.svo.status == 'before' }">
										시작전
									</c:when>
									<c:when test="${detail.svo.status == 'ongoing' }">
										진행중
									</c:when>
									<c:when test="${detail.svo.status == 'ended' }">
										종료됨
									</c:when>
								</c:choose>	
            				</div>
            			</div>
            			<div class="title">${detail.svo.stitle }</div>
            			<div class="date">${detail.svo.fullDate }</div>
            			<div class="count">
            				<div class="favoriteCount">
            					<i class="fa-solid fa-heart"></i>
            					${detail.svo.favoriteCount}
            				</div>
            				<div class="viewCount">
            				<i class="fa-solid fa-eye"></i>
            					${detail.svo.viewCount}
            				</div>
            			</div>
            			
            			<div class="btnWrapper">
            				<a href="/salePath/detail?sno=${detail.svo.sno }"><button>보러가기</button></a>
            				<a href="/salePath/editSale?sno=${detail.svo.sno }"><button>수정하기</button></a>
            				<a href="/salePath/delete?sno=${detail.svo.sno }"><button>삭제하기</button></a>
            			</div>
            			
            		</div>  <!-- rightDiv -->
            	
            	
            	</div> <!--topbox  -->
            	
            	
            	<div class="bottomBox">  <!-- bottomBox -->
            		
            		<div class="bottomBoxContent"> 
            		
            			<div class="contentBtn">
            				<button class="detailBtn underline">판매 폼 상세보기</button>
							<button class="participantBtn">구매 현황보기</button>
						</div>
            			
            			<div class="detailDiv"> <!-- 상세정보  -->
            				
            				<div class="detailContent">
            					
            					<div class="detailContentArea">
            						<div> <!-- 기본정보영역 -->
		            				<div class="titleText">기본정보</div>
		            				
            						<ul class="info-list">
            							<li>등록일</li>
            							<li>${detail.svo.sregDate }</li>
            						</ul>
            						<ul class="info-list">
            							<li>기한</li>
            							<li>${detail.svo.fullDate }</li>
            						</ul>
            						<ul class="info-list">
            							<li>구매수</li>
            							<li>${orderInfo.size()}</li>
            						</ul>
            						</div>
            						
            						<div class="saleContentArea"> <!--판매상품영역  -->
            							<div class="titleText">판매상품</div>
            							
            							<table>
            								<thead>
            									<tr>
            										<th>상품명</th>
            										<th>가격</th>
            										<th>재고</th> 
            									</tr>
            								</thead>
            								<tbody>
            									<c:forEach items="${detail.spdtoList}" var="product">
            										<tr>
            											<td>${product.spvo.spname }</td>
            											<td>${product.spvo.spprice }</td>
            											<td>${product.spvo.stock }</td>
            										</tr>
            									</c:forEach>
            								</tbody>
            							
            							</table>
            							
            						</div>
            					</div>
            					
            				</div> <!--detailContent끝  -->
            				
            			</div> <!--상세정보끝  -->
            			
            			<div class="participationForm hidden"> <!--참여정보  -->
            			
            				<div class="participationContent">
            				
            				
            				<div class="orderBtnWrapper">
           					
            					<label for="statusFilter"></label>
									<select id="statusFilter" onchange="filterOrders()">
										<option value="" disabled selected>주문현황</option>
    									<option value="ALL">전체 보기</option>
    									<option value="PAYMENT_COMPLETED">결제 완료</option>
    									<option value="PAYMENT_CONFIRMED">결제 확인</option>
    									<option value="PREPARING_SHIPMENT">배송 준비</option>
    									<option value="IN_TRANSIT">배송 중</option>
    									<option value="DELIVERED">배송 완료</option>
   										<option value="TRANSACTION_COMPLETED">거래 종료</option>
									</select>  
									
									
								<div class="orderBtnDiv">
									<a href="/excel/orderDownload?sno=${detail.svo.sno}"><button class="excelDown">엑셀 다운로드</button></a>
									<button class="orderDelete">선택삭제</button>
									<button class="orderEdit">주문현황 수정</button>
								</div>  
													       				
            				</div>
		       					<table>
            						<thead>
            							<tr>
            								<th></th>
            								<th>주문번호</th>
            								<th>주문현황</th>
            								<th>아이디</th>
            								<th>이름</th>
            								<th>연락처</th>
            								<th>이메일</th>
            								<th>상품 / 개수</th>
            								<th>총 가격</th>
            								<th>상세보기</th>
            							</tr>
            						</thead>
            						<tbody>
            							<c:forEach items="${orderInfo}" var="orderInfo">
            								<tr data-status="${orderInfo.orderStatus}" class="statusValue">
            									<td><input type="checkbox" value="${orderInfo.orderNo }" class="orderNoValue"></td>
            									<td>${orderInfo.orderNo}</td>
            									<td>
													<c:choose>
													    <c:when test="${orderInfo.orderStatus == 'PAYMENT_CANCELED'}">
        													결제 취소
    													</c:when>
    													<c:when test="${orderInfo.orderStatus == 'PAYMENT_COMPLETED'}">
   													     	결제 완료
   														</c:when>
   														<c:when test="${orderInfo.orderStatus == 'PAYMENT_CONFIRMED'}">
     														결제 확인
  														</c:when>
 														<c:when test="${orderInfo.orderStatus == 'PREPARING_SHIPMENT'}">
      														배송 준비
  														</c:when>
    													<c:when test="${orderInfo.orderStatus == 'IN_TRANSIT'}">
        													배송 중
    													</c:when>
    													<c:when test="${orderInfo.orderStatus == 'DELIVERED'}">
        													배송 완료
    													</c:when>
    													<c:when test="${orderInfo.orderStatus == 'TRANSACTION_COMPLETED'}">
        													거래 종료
    													</c:when>
    													<c:otherwise>
        													상태 미정
    													</c:otherwise>
													</c:choose>
            									</td>
            									<td>${orderInfo.id }</td>
            									<td>${orderInfo.ordererName}</td>
            									<td>${orderInfo.ordererPhone}</td>
            									<td>${orderInfo.ordererEmail}</td>
            									
            									<%-- <td>${orderInfo.shippingMethod}</td> --%>
            									<td>
            										<ul>
            											<c:forEach var="product" items="${fn:split(orderInfo.products, ', ')}">
                										<li>${product}</li>
            											</c:forEach>
            										</ul>
            									</td>
            									<td>${orderInfo.totalPrice }</td>
            									<td><button class="btn" data-orderNo=${orderInfo.orderNo }
            									onclick="getDetailInfo(this)">상세보기</button></td>	
            								</tr>
            							</c:forEach>
            						</tbody>
            					</table>
            				
            				</div>
            			
            			</div> <!--참여 정보 끝  -->
          		
            		</div>  <!--bottomBoxContent  -->
            		
            	</div>  <!--bottomBox  -->
               
        </div>  <!-- contentBox  -->
    </div>  <!-- content -->
    
    
</div><!--wrapper끝  -->


<!--모달창  -->
<div id="modal" class="modal-overlay">

	<div class="modalBody">
	
		<div class="modalTitle">
			<div class="modalText">상세보기</div>
			<div class="closeBtn" id="closeBtn"> 
				<i class="fas fa-light fa-xmark"></i>
			 </div>
		</div>
		
		<div class="modalContent">
		
			<div class="info">
				
			</div>
		
		</div>
	</div>
</div>



<!-- 바꿀 주문현황 선택하는 부분 -->
<div class="modal-order-overlay">
	<div class="modal-order-Body">
		<div class="modal-order-title">
			<div class="modal-order-text">주문상태 변경</div>
			<div class="order-closeBtn">
				<i class="fas fa-light fa-xmark"></i>
			</div>
		</div>
		
		<div class="modal-order-content">
			<div class="changeBtnWrapper">
				<button class="changeBtn" data-orderStatus="PAYMENT_CONFIRMED">결제확인</button>	
				<button class="changeBtn" data-orderStatus="PREPARING_SHIPMENT">배송준비</button>
				<button class="changeBtn" data-orderStatus="IN_TRANSIT">배송중</button>
				<button class="changeBtn" data-orderStatus="TRANSACTION_COMPLETED">배송완료</button>
			</div>
		</div>
		
	</div>

</div>


<!-- 주문 삭제 확인 모달  -->
<div class="modal-delete-overlay">
	<div class="modal-delete-body">
		<div class="modal-delete-title">
			<div class="modal-delete-text">주문 삭제</div>
			<div class="modal-delete-closeBtn">
				<i class="fas fa-light fa-xmark"></i>
			</div>
		</div>
		<div class="modal-delete-content">
			<div>이 주문을 정말 삭제하시겠습니까?</div>
			<div>삭제 버튼을 누르면 주문이 삭제됩니다.</div>
			
			<div class="deleteBtnWrapper">
				<button class="deleteBtn">삭제하기</button>
			</div>
		</div>
	
	</div>

</div>


<!--글삭제 모달창  -->

<div class="deleteModal-overlay" id="deleteModal">
    <div class="deleteModal">

        <div class="deleteModal-body">
            <p>글을 삭제하려면 확인 버튼을 눌러주세요.</p>
            <p>삭제된 글은 복구되지 않습니다.</p>
        </div>
        <div class="deleteModal-footer">
            <a href="/salePath/delete?sno=${detail.svo.sno }"><button class="deleteModal-btn deleteModal-confirm-btn" id="confirmDelete">확인</button></a>
            <button class="deleteModal-btn deleteModal-cancel-btn" id="cancelDelete" onclick="closeDeleteModal()">취소</button>
        </div>
    </div>
</div>


<jsp:include page="../layout/footer.jsp"></jsp:include>
<script type="text/javascript" src="/resources/js/saleSellerDetail.js"></script>

</body>
</html>