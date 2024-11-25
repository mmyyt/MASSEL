<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/saleOrderPayment.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
</head>
<body>


<jsp:include page="../layout/header.jsp"></jsp:include>



<div class="wrapper">

	<div class="contentDiv">
	
	
	
		<div class="content">
   		 <div class="order-summary">
        <div class="order-number">주문번호: ${orderNo }</div>
        <div class="order-success-message">주문서 제출이 완료되었습니다</div>
        <div class="order-cancel-message">한 시간 내로 입금이 되지 않으면 주문이 자동으로 취소됩니다</div>
        
        <div class="account-info">
            <h3>입금정보</h3>
            <div class="info-item">
                <span class="info-label">은행명</span>
                <span class="info-value">${accountInfo.bankName }</span>
            </div>
            <div class="info-item">
                <span class="info-label">계좌번호</span>
                <span class="info-value">${accountInfo.accountNumber }</span>
            </div>
            <div class="info-item">
                <span class="info-label">예금주</span>
                <span class="info-value">${accountInfo.accountHolder }</span>
            </div>
             <div class="info-item">
                <span class="info-label">주문서 작성일</span>
                <span class="info-value">${orderInfo.orderDate }</span>
            </div>
                        <div class="info-item">
                <span class="info-label">총금액</span>
                <span class="info-value">${orderInfo.totalPrice } 원</span>
            </div>
        </div>
        
        <div class="action-buttons">
            <button class="later-btn">나중에 하기</button>
            <button class="confirm-btn">입금완료</button>
        </div>
    </div>
</div>
	
	
	

	</div>

</div>


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


<div class="modal-later">
	<div class="modal-content">
	
		<div class="modal-text">
			1시간 내로 입금을 완료해주세요. <br><br>
			주문서 작성시간으로부터 1시간 이내에 <br>
			입금을 완료하지 않으면 자동으로 주문이 취소됩니다.<br>
			판매자의 계좌정보는 마이페이지-구매-상품 에서<br>
			다시 확인할 수 있습니다.
		</div>

		<button class="close-later">확인</button>
	</div>

			

	
</div>


<jsp:include page="../layout/footer.jsp"></jsp:include>
<script type="text/javascript">
	const writer = "${writer}";
	 sessionId = "${ses.uvo.id}";
	const orderNo = "${orderNo}";
</script>
<script type="text/javascript" src="/resources/js/saleOrderPayment.js"></script>

</body>
</html>