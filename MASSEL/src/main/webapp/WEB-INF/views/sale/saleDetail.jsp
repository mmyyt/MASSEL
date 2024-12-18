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
<link rel="stylesheet" type="text/css" href="/resources/css/saleDetail.css">
<script src="https://kit.fontawesome.com/4facc1d037.js" crossorigin="anonymous"></script>
</head>
<body>
<jsp:include page="../layout/header.jsp"></jsp:include>



<div class="wrap" id="wrap">

	<div class="saleDetail">

		<div class="leftwrap">

			<div class="leftHeader">
				<div class="status">

					 <c:if test="${sdto.svo.status == 'before'}">
						시작전
					 </c:if>
					 <c:if test="${sdto.svo.status == 'ended'  }">
					  	종료됨
					 </c:if>
					 <c:if test = "${sdto.svo.status == 'ongoing' }">
					  	진행중
					 </c:if>

				</div>

				<div class="title">${sdto.svo.stitle }</div>
				<div class="bottom">
					<div class="registerDate">${sdto.svo.sregDate }</div>
					<div class="viewCount">
					<i class="fa-solid fa-eye"></i>
					${sdto.svo.viewCount } </div>
					<div class="favoriteCountDiv">
						<c:choose>
							<c:when test="${favoriteState == 'true'}">
								<!--사용자가 좋아요를 누른상태  -->
								<i class="fa-solid fa-heart" data-sno=${sdto.svo.sno }
					 			data-icon = "filled" onclick="toggleFavoriteCount(this)">
					 			</i> 
							</c:when>
							<c:otherwise>
								<i class="fa-regular fa-heart" data-sno=${sdto.svo.sno }
					 			data-icon = "empty" onclick="toggleFavoriteCount(this)">
					 			</i> 
							</c:otherwise>
						</c:choose>
						
					 	<span id="favoriteCount">${sdto.svo.favoriteCount}</span>
					</div>
					<div class="btnArea">
						<c:if test="${ses.uvo.id  ne null && ses.uvo.id eq sdto.svo.swriter }">
						<a href="/salePath/editSale?sno=${sdto.svo.sno }"><button>수정하기</button></a>
						<a href="/salePath/delete?sno=${sdto.svo.sno }"><button>삭제하기</button></a>
						</c:if>
					</div>			
				</div>

			</div>
			
			<div class="thumbnail">
				<img alt="사진없음" class="dsthumbnail" 
				src="/upload/saleImg/${fn: replace(sdto.sivo.saveDir, '\\','/')}/${sdto.sivo.uuid }_${sdto.sivo.fileName}">
			</div>
			
			<div class="date">
				<div class="dateText">판매 기간</div>
				<div class="dateTime">${sdto.svo.fullDate }</div>
								
			</div>

			
			<div class="writerInfo">
				<div class="writerImg">
				
					<c:choose>
						<c:when test="${empty writer.uivo }">
							<img class="saleWriterimg" src="/resources/img/defaultUserImg.png" alt="">
						</c:when>
						<c:otherwise>
							<img alt="사진없음" class="saleWriterimg" 
							src="/upload/userImg/${fn: replace(writer.uivo.saveDir, '\\','/')}/${writer.uivo.uuid }_${writer.uivo.fileName}">
						</c:otherwise>
					</c:choose>
				</div>
				<div class="writerName">
					${sdto.svo.swriter }
				</div>
				<div class="message">
				<c:if test="${ses.uvo.id ne sdto.svo.swriter }">
					<button type="button" class="messageBtn" onclick="openChat('${sdto.svo.swriter}')">메세지 보내기</button>
				</c:if>
				</div>
				
			</div>
			
			<div class="detail">
				<div class="detailText">
					<div class="dt">상세정보</div>
				</div>
				<div class="detailContent">
					${sdto.svo.sdetail }
				</div>
				
				<div class="info"> <!--교환/환불  -->
					<div class="info-title">교환/환불 안내사항</div>
					<div class="info-refund">${sdto.svo.refundMessage }</div>
				</div>
				<div class="info"> <!--배송안내사항  -->
					<div class="info-title">배송안내사항</div>
					<div class="info-shipping">${sdto.svo.shippingInstructions }</div>
				</div>
			</div>
		
		</div><!-- left끝  -->
		
		<!--오른쪽부분 폼 참여하는 부분  -->
		<div class="rightwrap" id="rightwrap">
   		 <div class="rightContent">
        	<div class="rightHeader">
            	<div class="rightHeaderText">상품 구매</div>
        	</div>
        
        	
        		<div class="productList">
            	<c:forEach items="${sdto.spdto }" var="product">
            	
                	<div class="productContainer" data-spno=${product.spvo.spno }>

                    	<div class="productImg">
                        	<img alt="사진없음" class="pImg" 
							src="/upload/saleProductImg/${fn: replace(product.spivo.saveDir, '\\','/')}/${product.spivo.uuid }_${product.spivo.fileName}">	
                    	</div>
                    <div class="productName">
                        ${product.spvo.spname }
                    </div>
                    <div class="productPrice">
                        ${product.spvo.spprice }
                    </div>
                    <div class="productSelect">
                    	<div class="limit">구매제한 : ${product.spvo.maxOrderQuantity}</div>
                    	
                    	<div class="productCount">
                    	
                    		<c:choose>
                    		<c:when test="${product.spvo.stock <= 0}">
                    			품절
                    		</c:when>
                    		<c:otherwise>
                    		<button class="countBtnMinus" id="countBtnMinus" data-spno="${product.spvo.spno }">-</button>
                        	<input type="text" class="countInput" value="0" id="countInput" data-spno="${product.spvo.spno }">
                        	<button class="countBtnPlus" id="countBtnPlus" data-spno="${product.spvo.spno }" data-limit = "${product.spvo.maxOrderQuantity }">+</button>
                    		</c:otherwise>
                    		</c:choose>
                    	</div>
                    	
                    </div>
                	</div>
            	</c:forEach>
        	</div>
            	  <div class="btnWrapper">
       				<button type="button" class="btn" id="btn" onclick="checkMyProduct()">참여하기</button>
    			</div>

        	
    	</div> <!--rightContent  -->
	</div>
	
	</div>

</div>

<!--모달창-->
<div id="modal" class="modal-overlay">

	<div class="modalBody">
	
		<div class="modalTitle">
			<div class="modalText">구매하기</div>
			<div class="closeBtn" id="closeBtn"> X </div>
		</div>
		
		<div class="modalContent">
		
			<div class="myProductList">
		 	
		 	
		 	   <div class="listProduct">
                <div class="listProductSpno" style="display: none;"></div>
                <div class="listProductImg">
 
                </div>
                <div class="listProductName">상품이름</div>
                <div class="listProductPrice">가격 </div>
                <div class="listProductCount">개수</div>
               </div>
		 	
			</div>
			
			
			
			<!--여기서부터 가져가면됨..!!  -->
			
			<div class="userInfoWrapper"> <!-- 정보 시작!  -->
			
				<div class="shipmentTitle">배송정보</div> <!--title  -->
				
				<div> <!-- 배송방법/주문자 적는 공간  -->
				
					<div class="shipmentMethodDiv"><!--배송방법  -->
						<div class="shipmentMethodText">배송방법</div>
						
						<div class="shipmentMethodRadio">
							<c:forEach items = "${sdto.ssvo}" var="shipment">
								<label>
									<input type="radio" value="${shipment.shippingMethod }, ${shipment.shippingCost}"
									name="shippingMethod" id="shipmentMethodBtn" class="shipmentMethodBtn">
									${shipment.shippingMethod } (${shipment.shippingCost}원)
								</label>
							</c:forEach>
						</div>
						
					</div><!--배송방법  -->
				
					<div class="ordererNameDiv"> <!--주문자이름  -->
						<div class="ordererNameText">주문자명</div>
						<div class="ordererNameInput"><input type="text" name="name" class="ordererName"></div>
					</div> <!--주문자이름  -->
					
					<div class="ordererPhoneDiv"> <!--주문자연락처  -->
						<div class="ordererPhoneText">주문자 연락처</div>
						<div class="ordererPhoneInput"><input type="text" name="phoneNumber" class="ordererPhone"></div>
					</div><!--주문자연락처  -->
					
					<div class="ordererEmailDiv"><!--주문자이메일  -->
						<div class="ordererEmailText">주문자 이메일</div>
						<div class="ordererEmailInput"><input type="text" name="email" class="emailAddress" value="${ses.uvo.email }"></div>
					</div><!--주문자이메일  -->
								
				</div><!-- 배송방법/주문자 적는 공간  -->
				
				<div> <!-- 수령인 정보 적는 공간 -->
				
					<div class="recipientNameDiv"> <!--수령인 이름  -->
						<div class="recipientNameText">수령인 이름</div>
						<div class="recipientNameInput"><input type="text" name="recipientName" class="recipientName"></div>
					</div>
					
					<div class="recipientPhoneDiv"> <!--수령인 폰버노  -->
						<div class="recipientPhoneText">수령인 연락처</div>
						<div class="recipientPhoneInput"><input type="text" name="recipientPhoneNumber" class="recipientPhoneNumber"></div>
					</div>
					
					<div class="postalCodeDiv"> <!--우편번호  -->
						<div class="postalCodeText">우편번호</div>
						<div class="postalCodeInput"><input type="text" name="recipientPostalCode" id="recipientPostalCode" class="recipientPostalCode"></div>
						<div class="postalCodeBtn"><button class="postalCodeSearchBtn" onclick="postCode()">우편번호찾기</button></div>
					</div>
					
					<div class="addressDiv"> <!--주소/상세  -->
						<div class="addressText">주소</div>
						    <div class="addressInputs">
        						<div class="addresesInput"><input type="text" placeholder="주소 입력" name="recipientAddress" id="recipientAddress" class="recipientAddress"></div>
        						<div class="addressDetailInput"><input type="text" placeholder="상세 주소 입력" name="recipientDetailAddress" class="recipientDetailAddress"></div>
    						</div>
					</div>
					
					<div class="shipmentNoteDiv"> <!--배송관련메모  -->
						<div>배송관련메모</div>
						<div class="shipmentNoteDivInput"><textarea rows="" cols="" name="shippingNote" class="shippingNote"></textarea></div>
					</div>
					
				
				</div><!-- 수령인 정보 -->
	
				<div class="refundTitle">환불 정보</div>
				<div> <!-- 환붋정보 -->
				
					<div class="refundBankDiv"> <!--환불은행  -->
						<div class="refundBankName">은행이름</div>
						<div class="refundBankInput"><input type="text" name="refundBank" class="refundBank"></div>
					</div>
					
					<div class="refundAccountDiv"><!--계좌번호  -->
						<div class="refundAccountText">계좌</div>
						<div class="refundAccountInput"><input type="text" name="refundAccount" class="refundAccount"></div>
					</div>
						
					<div class="refundAccountHolderDiv"><!--옉금주  -->
						<div class="refundAccountHolderText">예금주</div>
						<div class="refundAccountHolderInput"><input type="text" name="refundAccountHolder" class="refundAccountHolder"></div>
					</div>				
					
				
				</div><!--환불  -->

			
			</div> <!-- 배송정보 끝  -->
			
			<div> <!-- 최중금액보여주는 부분  --> 
				<div class="totalPriceTitle">최종주문금액</div>
				
				<div class="totalProductPriceDiv">
					<div class="totalProductPriceText">상품금액</div>
					<div class="totalProductPriceContent" id="totalProductPriceContent"></div>
				</div>
				<div class="shipmentPriceDiv">
					<div>배송비</div>
					<div class="shipmentCostPrice" id="shipmentCostPrice"></div>
				</div>
				
				<div class="totalPriceDiv">
					<div>최종금액</div>
					<div class="totalPrice"></div>
				</div>
			</div>
			
			<div class="participationBtnWrapper">
				<button type="button" class="participationBtn">구매하기</button>
				<!-- <button type="button" onclick ="check()">모든것을체크</button> -->
			</div>
		</div><!--modalcontent  -->
		
		
	</div> <!--modalbody  -->
	
</div>

<div class="deleteModal-overlay" id="deleteModal">
    <div class="deleteModal">

        <div class="deleteModal-body">
            <p>글을 삭제하려면 확인 버튼을 눌러주세요.</p>
            <p>삭제된 글은 복구되지 않습니다.</p>
        </div>
        <div class="deleteModal-footer">
            <a href="/salePath/delete?sno=${sdto.svo.sno }"><button class="deleteModal-btn deleteModal-confirm-btn" id="confirmDelete">확인</button></a>
            <button class="deleteModal-btn deleteModal-cancel-btn" id="cancelDelete" onclick="closeDeleteModal()">취소</button>
        </div>
    </div>
</div>


<jsp:include page="../layout/footer.jsp"></jsp:include>

<script type="text/javascript">
	sessionId = "${ses.uvo.id}";
	tmpSno = "${sdto.svo.sno }";
	isParticipated = "${isParticipated}";
	writer = "${sdto.svo.swriter}";
	formStatus = "${sdto.svo.status}";
	
	document.querySelectorAll('.status').forEach(function(div) {
	    const statusText = div.textContent.trim();

	    if (statusText === '진행중') {
	        div.style.backgroundColor = "rgba(255, 0, 0, 0.5)";
	    } else if (statusText === '시작전') {
	        div.style.backgroundColor = "rgba(0, 0, 255, 0.5)";
	    } else if (statusText === '종료됨') {
	        div.style.backgroundColor = "rgba(128, 128, 128, 0.3)";
	    }
	});
</script>
<script type="text/javascript" src="/resources/js/saleDetail.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
</body>
</html>