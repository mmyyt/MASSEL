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
<link rel="stylesheet" type="text/css" href="/resources/css/saleBuyerDetail.css">
<script src="https://kit.fontawesome.com/4facc1d037.js" crossorigin="anonymous"></script>
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
            	
            	
           <div class="top">
           	<div class="top-img">
           
           		<img alt="사진없음" class="dsimg" 
				src="/upload/saleImg/${fn: replace(detailVo.sivo.saveDir, '\\','/')}/${detailVo.sivo.uuid }_${detailVo.sivo.fileName}">
          	</div>
          	<div>
          		<div class="top-title">
          			${detailVo.svo.stitle }
          		
          		</div>
          		<div class="top-btn">
          			<c:if test="${detailVo.svo.isDel == 0}">
          			<a href="/salePath/detail?sno=${detailVo.svo.sno }"><button>보러가기</button></a>
          			</c:if>
          			<c:if test="${detailVo.svo.isDel == 1}">
          				작성자에 의해 삭제된 폼입니다.
          			</c:if>
          			<button class="messageBtn" onclick="openChat('${detailVo.svo.swriter}')">메세지</button>
          		</div>
           </div>
           
           </div> 	<!-- top -->


			<div class="bottom">
			
				<div>
				
					<div class="orderStatusDiv"> <!-- 주문상태 -->
						
						<c:choose>
							<c:when test = "${detailInfo.orderStatus == 'PENDING_PAYMENT'}">
								<div class="payment_pending">
									<div class="pending_title">
										<div class="pending_text">결제대기</div>
										<div class="pending_btn">
											<button class="confirm-btn">입금완료</button>
											<button class="cancle-btn">주문취소</button>
										</div>
									</div>
									
									<div class="sellerInfo">
										<div class="bankName">
											<div class="bname">은행명</div>
											<div>${slvo.bankName }</div>
										</div>
										<div class="account">
											<div class="baccount">계좌번호</div>
											<div>${slvo.accountNumber }</div>
										</div>
										<div class="accountHolder">
											<div class="bholder">예금주</div>
											<div>${slvo.accountHolder }</div>
										</div>
									</div>
									
								</div>
							</c:when>
						
							<c:when test="${detailInfo.orderStatus == 'PAYMENT_CANCELED' }">
								<div class="payment_canceled">
									결제가 취소된 내역입니다.
								</div>
							</c:when>
							
							<c:otherwise>
								<div class="progress">
									<div class="step" data-status="PAYMENT_COMPLETED">
									<span class="circle"></span>
										<div class="step-content">
											<p>결제완료</p>
											<i class="fa-solid fa-circle-exclamation completedIcon">
												<div class="tooltip">
													결제완료 상태에서는 주문을 취소할 수 없습니다. <br>
													결제확인으로 상태가 변경되지 않는다면<br>
													판매자에게 직접 문의해주세요.
												</div>
											</i>
										</div>
									</div>
									
									<div class="step" data-status="PAYMENT_CONFIRMED">
									<span class="circle"></span>
									<p>결제확인</p>
									</div>						
									<div class="step" data-status="PREPARING_SHIPMENT">
									<span class="circle"></span>
									<p>배송준비</p>
									</div>		
									<div class="step" data-status="IN_TRANSIT">
									<span class="circle"></span>
									<p>배송중</p>
									</div>
									<div class="step" data-status="DELIVERED">
									<span class="circle"></span>
									<p>배송완료</p>
									</div>
									<div class="step" data-status="TRANSACTION_COMPLETED">
									<span class="circle"></span>
									<p>확정 및 종료</p>
									</div>	
								</div>	
							</c:otherwise>
						</c:choose>

						
					</div> <!-- 주문상태  -->
			
					<div class="byuerinfo"><!--구매정보  -->
						<div class="infoTitle">구매 정보</div>
						<div>
							<ul>
								<li>주문번호</li>
								<li>${detailInfo.orderNo }</li>
							</ul>
							<ul>
								<li>주문날짜</li>
								<li>${detailInfo.orderDate }</li>
							</ul>
							<ul>
								<li>이름</li>
								<li>${detailInfo.ordererName }</li>
							</ul>
							<ul>
								<li>연락처</li>
								<li>${detailInfo.ordererPhone }</li>
							</ul>
							<ul>
								<li>이메일</li>
								<li>${detailInfo.ordererEmail }</li>
							</ul>
							<ul>
								<li>환불계좌정보</li>
								<li>${detailInfo.refundBank } / ${detailInfo.refundAccount }
								  </li>
							</ul>
							<ul>
								<li>구매 상품</li>
								<li>${detailInfo.products }</li>
							</ul>
						</div>
					</div>
				
					<div class="shipmentinfo"> <!-- 배송정보 -->
						<div class="infoTitle">배송 정보</div>
						<div>
							<ul>
								<li>수령자 이름</li>
								<li>${detailInfo.recipientName }</li>
							</ul>
							<ul>
								<li>수령자 연락처</li>
								<li>${detailInfo.recipientPhoneNumber }</li>
							</ul>
							<ul>
								<li>주소</li>
								<li>${detailInfo.recipientPostalCode }
									${detailInfo.recipientAddress}
									${detailInfo.recipientDetailAddress}
								</li>
							</ul>
							<ul>
								<li>배송방법</li>
								<li>${detailInfo.shippingMethod }</li>
							</ul>
						</div>
						
					</div> <!--  -->
					
					<div class="paymentinfo"><!--결제정보  -->
						<div class="infoTitle">결제 정보</div>
						<div>
							<ul>
								<li>상품 금액</li>
								<li>${detailInfo.productPrice }</li>
							</ul>
							<ul>
								<li>배송비</li>
								<li>${detailInfo.shippingCost }</li>
							</ul>
							<ul>
								<li>총 결제 금액</li>
								<li>${detailInfo.totalPrice }</li>
							</ul>

						</div>
					</div>
				
				</div>
			
			
			</div> <!-- bottom -->
       
            
                  
        </div>  <!-- contentBox  -->
    </div>  <!-- content -->
    
    
</div><!--wrapper끝  -->


<!--모달창  -->
<div class="modal-confirm">
	<div class="modal-content">
		<div class="modal-text">
		입금이 완료되었습니다. <br><br>
		판매자가 입금을 확인하면 <br>
		결제 상태가 '입금 확인'으로 변경됩니다.<br>
		확인 후 배송 준비가 시작됩니다.
		</div>
		<button class="close-confirm">확인</button>
	</div>
</div>

<div class="modal-cancle">
	<div class="modal-content">
		<div class="modal-text">
		확인 버튼을 누르면 주문이 취소됩니다.<br><br>
		주문이 취소되면, 해당 주문은 다시 복구할 수 없습니다.<br>
		동일 상품은 상품 페이지에서 새로 주문할 수 있습니다.<br>
		</div>
		<button class="close-cancle">확인</button>
	</div>
</div>


<jsp:include page="../layout/footer.jsp"></jsp:include>

</body>
<script type="text/javascript">
	const writer = "${detailVo.svo.swriter}";
	 sessionId = "${ses.uvo.id}";
	const orderNo = "${detailInfo.orderNo}";
	 const orderStatus = "${detailInfo.orderStatus}";
	 
function openChat(targetUserId){
    console.log("타겟 유저 >> "+targetUserId);
    //버튼 클릭 시 방 생성 요청하기
    $.ajax({
        url:'/chat/createRoom',
        type:'post',
        data : {targetUser : targetUserId},
        dataType : 'json',
        success : function(response){
            //방이 성공적으로 생성되면 방 id를 받아서 해당 방으로 이동함
            console.log("서버 응답 >> "+response);
            let roomId = response.roomId;
            console.log("roomId >>> "+roomId);
            let url = '/chat/room/'+roomId;
            let urlOption = "width = 600px, height=500px, scrollbars=yes";
            window.open(url, 'pop', urlOption);
        },
        error : function(error){
            console.log("방 생성 중 오류 발생 :", error);
        }
    })
}
</script>
<script type="text/javascript" src="/resources/js/saleBuyerDetail.js"></script>
</html>