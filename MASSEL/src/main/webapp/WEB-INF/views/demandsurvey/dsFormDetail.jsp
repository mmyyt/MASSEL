<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/dsFormDetail.css">
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
			src="/upload/demandSurveyImg/${fn: replace(data.dsivo.saveDir, '\\','/')}/${data.dsivo.uuid }_${data.dsivo.fileName}">
            		</div>  <!--썸네일  -->
            		
            		<div class="rightDiv"> <!-- 제목, 기간, 상태, 버튼 -->
            		
            			<div class="state">
            				<div class="statusDiv">
								<c:choose>
									<c:when test="${data.dsvo.status == 'before' }">
										시작전
									</c:when>
									<c:when test="${data.dsvo.status == 'ongoing' }">
										진행중
									</c:when>
									<c:when test="${data.dsvo.status == 'ended' }">
										종료됨
									</c:when>
								</c:choose>	
            				</div>
            			</div>
            			<div class="title">${data.dsvo.dtitle }</div>
            			<div class="date">${data.dsvo.fullDate }</div>
            			
            			<div class="count">
            				<div class="viewCount">
            				<i class="fa-solid fa-eye"></i>
            					${data.dsvo.dviewCount}
            				</div>
            			</div>
            			
            			<div class="btnWrapper">
            				<a href="/demandSurvey/detail?dno=${data.dsvo.dno}"><button>보러가기</button></a>
            				<a href="/demandSurvey/edit?dno=${data.dsvo.dno }"><button>수정하기</button></a>
            				<button onclick="openDeleteModal()">삭제하기</button>
            			</div>
            			
            		</div>  <!-- rightDiv -->
            	
            	
            	</div> <!--topbox  -->
            	
            	
            	<div class="bottomBox">  <!-- bottomBox -->
            		
            		<div class="bottomBoxContent"> 
            		
            			<div class="contentBtn">
            				<button class="detailBtn underline">수요조사 상세보기</button>
							<button class="participantBtn">수요조사 현황보기</button>
						</div>
            			
            			<div class="detailDiv"> <!-- 상세정보  -->
            				
            				<div class="detailContent">
            					
            					<div class="detailContentArea">
            						<div> <!-- 기본정보영역 -->
		            				<div class="titleText">기본정보</div>
		            				
            						<ul class="info-list">
            							<li>등록일</li>
            							<li>${data.dsvo.dregDate }</li>
            						</ul>
            						<ul class="info-list">
            							<li>기한</li>
            							<li>${data.dsvo.fullDate }</li>
            						</ul>
            						<ul class="info-list">
            							<li>총 응답자 수</li>
            							<li>${data.participationCount } 명</li>
            						</ul>
            						</div>
            						
            						<div class="saleContentArea"> <!--판매상품영역  -->
            							<div class="titleText">수요조사 상품</div>
            							
            							<table>
            								<thead>
            									<tr>
            										<th>상품명</th>
            										<th>가격</th>
            								
            									</tr>
            								</thead>
            								<tbody>
          										<c:forEach items="${data.dspvo}" var="product">
            										<tr>
            											<td>${product.dpname }</td>
            											<td>${product.dpprice }</td>          										
            										</tr>
            									</c:forEach>

            								</tbody>
            							
            							</table>
            							
            						</div>
            					</div>
            					
            				</div> <!--detailContent끝  -->
            				
            			</div> <!--상세정보끝  -->
            			
            			<div class="participationForm hidden"> <!--참여정보  -->
            			
            				<div>
            					<c:choose>
            						<c:when test="${empty dsppvo }">
            							참여자가 없습니다.
            						</c:when>
            						<c:otherwise>
            					<table>
            						<thead>
            							<tr>
            								<th>아이디</th>
											<th>닉네임</th>
											<th>참여날짜</th>
											<th>상품/수량</th>
											<th>이메일</th>
            							</tr>
            						</thead>
            						<tbody>
            				<c:set var="currentId" value=""/>
       											 <c:forEach items="${dsppvo}" var="participant">

           											 <c:if test="${currentId != participant.id}">
                										<tr>
                    										<td>${participant.id}</td>
                  											<td>${participant.nickname }</td>
                 									   		<td>${participant.participationDate}</td>
                    										<td>
                        										<c:forEach items="${dsppvo}" var="p">
                            										<c:if test="${p.id == participant.id}">
                                										${p.dpname} / ${p.count} 개 <br/>
                            										</c:if>
                        										</c:forEach>
                    										</td>
                    										<td>${participant.email }</td>

                    									<tr>
                							<c:set var="currentId" value="${participant.id}"/>
            										</c:if>
       											</c:forEach>
            						</tbody>
            					</table>
            					</c:otherwise>
            				</c:choose>
            				</div>
            			
            			</div> <!--참여 정보 끝  -->
            		
            		
            		
            		</div>  <!--bottomBoxContent  -->
            		
            	</div>  <!--bottomBox  -->
            	
            
                  
        </div>  <!-- contentBox  -->
    </div>  <!-- content -->
    
    
</div><!--wrapper끝  -->


<div class="deleteModal-overlay" id="deleteModal">
    <div class="deleteModal">

        <div class="deleteModal-body">
            <p>글을 삭제하려면 확인 버튼을 눌러주세요.</p>
            <p>삭제된 글은 복구되지 않습니다.</p>
        </div>
        <div class="deleteModal-footer">
            <a href="/demandSurvey/delete?dno=${data.dsvo.dno }"><button class="deleteModal-btn deleteModal-confirm-btn" id="confirmDelete">확인</button></a>
            <button class="deleteModal-btn deleteModal-cancel-btn" id="cancelDelete" onclick="closeDeleteModal()">취소</button>
        </div>
    </div>
</div>

<jsp:include page="../layout/footer.jsp"></jsp:include>
<script type="text/javascript" src="/resources/js/dsFormDetail.js"></script>

</body>
</html>