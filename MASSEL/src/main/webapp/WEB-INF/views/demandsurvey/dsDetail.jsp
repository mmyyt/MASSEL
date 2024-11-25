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
<link rel="stylesheet" type="text/css" href="/resources/css/dsDetail.css">
</head>
<body>
<jsp:include page="../layout/header.jsp"></jsp:include>


<div class="wrap" id="wrap">


	<div class="dsDetail">
	
	
		<div class="leftwrap">
		
			<div class="leftHeader">
			<div class="status">
					<c:if test="${dsidto.dsvo.status == 'before'}">
						시작전
					 </c:if>
					 <c:if test="${dsidto.dsvo.status == 'ended'  }">
					  	종료됨
					 </c:if>
					 <c:if test = "${dsidto.dsvo.status == 'ongoing' }">
					  	진행중
					 </c:if>
			</div>
				<div class="title">${dsidto.dsvo.dtitle }</div>
				<div class="bottom">
					<div class="registerDate">${dsidto.dsvo.dregDate }</div>
					<div class="viewCount">
					<i class="fa-solid fa-eye"></i>
					${dsidto.dsvo.dviewCount } </div>
					<div class="btnArea">
						<c:if test="${ses.uvo.id  ne null && ses.uvo.id eq dsDetail.id }">
						<a href="/demandSurvey/edit?dno=${dsDetail.dno }"><button>수정하기</button></a>
						<button onclick="openDeleteModal()">삭제하기</button>
						</c:if>
					</div>			
				</div>

			</div>
			
			<div class="thumbnail">
				<img alt="사진없음" class="dsthumbnail" 
				src="/upload/demandSurveyImg/${fn: replace(dsidto.dsivo.saveDir, '\\','/')}/${dsidto.dsivo.uuid }_${dsidto.dsivo.fileName}">
			</div>
			
			<div class="date">
				<div class="dateText">수요조사 기간</div>
				<div class="dateTime">${dsidto.dsvo.fullDate }</div>
			</div>
			
<!-- 			<div class="tag">
				태그나..뭐 다른거 나중에 추가하기..
			</div> -->
			
			<div class="writerInfo">
				<div class="writerImg">
					<c:choose>
						<c:when test="${empty writer.uivo }">
							<img class="dsWriterimg" src="/resources/img/defaultUserImg.png" alt="">
						</c:when>
						<c:otherwise>
							<img alt="사진없음" class="dsWriterimg" 
							src="/upload/userImg/${fn: replace(writer.uivo.saveDir, '\\','/')}/${writer.uivo.uuid }_${writer.uivo.fileName}">
						</c:otherwise>
					</c:choose>
				</div>
				<div class="writerName">
					${dsDetail.id } 
				</div>
				<div class="message">
				<c:if test="${ses.uvo.id ne dsDetail.id }">
					<button type="button" class="messageBtn" onclick="openChat('${dsDetail.id}')">메세지 보내기</button>
				</c:if>
				</div>
				
			</div>
			
			<div class="detail">
				<div class="detailText">
					<div class="dt">상세정보</div>
				</div>
				<div class="detailContent">
					${dsidto.dsvo.ddetail }
				</div>
			</div>
		
		</div><!-- left끝  -->
		
		<!--오른쪽부분 폼 참여하는 부분  -->
		<div class="rightwrap" id="rightwrap">
   		 <div class="rightContent">
        <div class="rightHeader">
            <div class="rightHeaderText">수요조사 참여</div>
        </div>
        
        <div class="productList">
            <c:forEach items="${product}" var="product">
                <div class="productContainer" data-dpno=${product.dpno }>
<!--                     <div class="productImg">
                        <img src="" />
                    </div> -->
                    <div class="productName">
                        ${product.dpname }
                    </div>
                    <div class="productPrice">
                        ${product.dpprice }
                    </div>
                    <div class="productCount">
                        <button class="countBtnMinus" id="countBtnMinus" data-dpno="${product.dpno }">-</button>
                        <input type="text" class="countInput" value="0" id="countInput" data-dpno="${product.dpno }">
                        <button class="countBtnPlus" id="countBtnPlus" data-dpno="${product.dpno }">+</button>
                    </div>
                </div>
            </c:forEach>
        </div>
            <div class="btnWrapper">
        		<button type="button" class="btn" id="btn" onclick="checkMyProduct()">참여하기</button>
    		</div>
        
    </div> <!-- rightContent -->

</div>
	
	</div>





</div>

<!--모달창-->
<div id="modal" class="modal-overlay">

	<div class="modalBody">
	
		<div class="modalTitle">
			<div class="modalText">수요조사</div>
			<div class="closeBtn" id="closeBtn"> X </div>
		</div>
		
		<div class="modalContent">
		
			<div class="myProductList">
		 	
			</div>
			
			<div class="emailDiv">
				<div class="emailText">이메일</div>
				<div class="emailInput">
					<input type="text" name="email" class="emailAddress" value="${ses.uvo.email }">
				</div>

			</div>

			<div class="participationBtnWrapper">
				<button type="button" class="participationBtn">참여하기</button>
			</div>
		</div>
	</div>
	
</div>

<div class="deleteModal-overlay" id="deleteModal">
    <div class="deleteModal">

        <div class="deleteModal-body">
            <p>글을 삭제하려면 확인 버튼을 눌러주세요.</p>
            <p>삭제된 글은 복구되지 않습니다.</p>
        </div>
        <div class="deleteModal-footer">
            <a href="/demandSurvey/delete?dno=${dsDetail.dno }"><button class="deleteModal-btn deleteModal-confirm-btn" id="confirmDelete">확인</button></a>
            <button class="deleteModal-btn deleteModal-cancel-btn" id="cancelDelete" onclick="closeDeleteModal()">취소</button>
        </div>
    </div>
</div>

<jsp:include page="../layout/footer.jsp"></jsp:include>

<script type="text/javascript">
	sessionId = "${ses.uvo.id}";
	tmpDno = "${dsDetail.dno }";
	let nickname = "${ses.uvo.nickname}";
	let isParticipated = "${isParticipated}";
	let formStatus = "${dsDeatil.status}";
	let writer = "${dsDetail.id}"
	
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
<script type="text/javascript" src="/resources/js/dsDetail.js"></script>

</body>
</html>