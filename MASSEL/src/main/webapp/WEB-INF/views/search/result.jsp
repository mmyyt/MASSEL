<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resources/css/result.css">
<script src="https://kit.fontawesome.com/4facc1d037.js" crossorigin="anonymous"></script>
</head>
<body>

<jsp:include page="../layout/header.jsp"></jsp:include>


<div class="wrapper">


<div class="container">

	<div class="keyword">
		<%-- <div class="keywordText">${keyword } 검색 결과입니다</div> --%>
	</div>

    <div class="saleList">
        <h2>판매폼</h2>
        <hr>

          	<c:choose>
          		<c:when test="${saleDtoList ne null }">
          			<div class="item-grid">
          			<c:forEach items="${saleDtoList }" var="saleList">
          			<a href="/salePath/detail?sno=${saleList.sidto.svo.sno }">
          				<div class="item-box">
                			<img alt="사진없음" class="" 
							src="/upload/saleImg/${fn: replace(saleList.sidto.sivo.saveDir, '\\','/')}/${saleList.sidto.sivo.uuid }_${saleList.sidto.sivo.fileName}">
                			<div class="item-nickname">
                			
                			<div>
 								<c:choose>
									<c:when test="${not empty saleList.uivo }">
										<img alt="없음" class="searchUserImg" 
										src="/upload/userImg/${fn: replace(saleList.uivo.saveDir, '\\','/')}/${saleList.uivo.uuid }_${saleList.uivo.fileName}">
									</c:when>
									<c:otherwise>
										<img class="searchUserImg" src="/resources/img/defaultUserImg.png" alt="">
									</c:otherwise>
								</c:choose> 
							 </div> 
							   
							 <div class="item-nicknameArea">
							          			
                				${saleList.sidto.svo.swriter }
                			</div>
                			
                			</div>
                			<div class="item-title">${saleList.sidto.svo.stitle }</div>
                			<div>
                				<div class="viewCount">
                					<span><i class="fa-regular fa-eye"></i></span>
                					${saleList.sidto.svo.viewCount }
                				</div>
                				<div class="favoriteCount">
                					<span>❤️</span>${saleList.sidto.svo.favoriteCount }
                				</div>
                			</div>
           				 </div>      			
          			</a>
          			</c:forEach>
          			</div>
          		</c:when>
          		<c:when test="${saleList eq null }">
          			<div class="noResult">
          				<div>'${keyword }' 에 일치하는 판매폼이 없습니다.</div>
          				<div>이런 판매폼은 어떠세요?</div>
          			</div>
          			<div class="item-grid">
          			<c:forEach items="${popularDtoList }" var="popularList">
          			<a href="/salePath/detail?sno=${popularList.sidto.svo.sno }">
          				<div class="item-box">
                			<img alt="사진없음" class="" 
							src="/upload/saleImg/${fn: replace(popularList.sidto.sivo.saveDir, '\\','/')}/${popularList.sidto.sivo.uuid }_${popularList.sidto.sivo.fileName}">
                			<div class="item-nickname">
                			
                			<div>
 								<c:choose>
									<c:when test="${not empty popularList.uivo }">
										<img alt="없음" class="searchUserImg" 
										src="/upload/userImg/${fn: replace(popularList.uivo.saveDir, '\\','/')}/${popularList.uivo.uuid }_${popularList.uivo.fileName}">
									</c:when>
									<c:otherwise>
										<img class="searchUserImg" src="/resources/img/defaultUserImg.png" alt="">
									</c:otherwise>
								</c:choose> 
							 </div>   
							 <div class="item-nicknameArea">
							          			
                				${popularList.sidto.svo.swriter }
                			</div>
                				
                			</div>
                			<div class="item-title">${popularList.sidto.svo.stitle }</div>
                			<div>
                				<div class="viewCount">
                					<span><i class="fa-regular fa-eye"></i></span>
                					${popularList.sidto.svo.viewCount }
                				</div>
                				<div class="favoriteCount">
                					<span>❤️</span>${popularList.sidto.svo.favoriteCount }
                				</div>
                			</div>
           				 </div>      			
          			</a>
          			</c:forEach>
          			</div>
          			
          		</c:when>
          	</c:choose>

            <!-- saleList 반복문 끝 -->
        <!-- </div>grid  -->
    </div>

    <div class="demandSurveyList">
        <h2>수요조사 폼</h2>
        <hr>

              <c:choose>
          		<c:when test="${demandList ne null }">
          			<div class="item-grid">
          			<c:forEach items="${demandList }" var="demandList">
          			<a href="/demandSurvey/detail?dno=${demandList.dsidto.dsvo.dno}">
          				<div class="item-box">
                			<img alt="사진없음" class="" 
							src="/upload/demandsurveyImg/${fn: replace(demandList.dsidto.dsivo.saveDir, '\\','/')}/${demandList.dsidto.dsivo.uuid }_${demandList.dsidto.dsivo.fileName}">
                			<div class="item-nickname">
                			
                			<div>
 								<c:choose>
									<c:when test="${not empty demandList.uivo }">
										<img alt="없음" class="searchUserImg" 
										src="/upload/userImg/${fn: replace(demandList.uivo.saveDir, '\\','/')}/${demandList.uivo.uuid }_${demandList.uivo.fileName}">
									</c:when>
									<c:otherwise>
										<img class="searchUserImg" src="/resources/img/defaultUserImg.png" alt="">
									</c:otherwise>
								</c:choose> 
							 </div> 
							   
							 <div class="item-nicknameArea">
							          			
                				${demandList.dsidto.dsvo.id }
                			</div>
                			
                			</div>
                			<div class="item-title">${demandList.dsidto.dsvo.dtitle }</div>
                			<div>
                				<div class="viewCount">
                					<span><i class="fa-regular fa-eye"></i></span>
                					${demandList.dsidto.dsvo.dviewCount }
                				</div>
                			</div>
           				 </div>      			
          			</a>
          			</c:forEach>
          			</div>
          		</c:when>
          		<c:when test="${demandList eq null }">
          			<div class="noResult">
          				<div>'${keyword }' 에 일치하는 수요조사폼이 없습니다.</div>
          				<div>이런 수요조사폼은 어떠세요?</div>
          			</div>
          			<div class="item-grid">
          			<c:forEach items="${popularDemandList }" var="popularDemandList">
          			<a href="/demandSurvey/detail?dno=${popularDemandList.dsidto.dsvo.dno}">
          				<div class="item-box">
                			<img alt="사진없음" class="" 
							src="/upload/demandsurveyImg/${fn: replace(popularDemandList.dsidto.dsivo.saveDir, '\\','/')}/${popularDemandList.dsidto.dsivo.uuid }_${popularDemandList.dsidto.dsivo.fileName}">
                			<div class="item-nickname">
                			
                			<div>
 								<c:choose>
									<c:when test="${not empty popularDemandList.uivo }">
										<img alt="없음" class="searchUserImg" 
										src="/upload/userImg/${fn: replace(popularDemandList.uivo.saveDir, '\\','/')}/${popularDemandList.uivo.uuid }_${popularDemandList.uivo.fileName}">
									</c:when>
									<c:otherwise>
										<img class="searchUserImg" src="/resources/img/defaultUserImg.png" alt="">
									</c:otherwise>
								</c:choose> 
							 </div> 
							   
							 <div class="item-nicknameArea">
							          			
                				${popularDemandList.dsidto.dsvo.id }
                			</div>
                			
                			</div>
                			<div class="item-title">${popularDemandList.dsidto.dsvo.dtitle }</div>
                			<div>
                				<div class="viewCount">
                					<span><i class="fa-regular fa-eye"></i></span>
                					${popularDemandList.dsidto.dsvo.dviewCount }
                				</div>
                			</div>
           				 </div>      			
          			</a>
          			</c:forEach>
          			</div>
          			
          		</c:when>
          	</c:choose>
        
        
        
<!--         
        <div class="item-grid">

            <div class="item-box">
                <img src="demand-image.jpg" alt="Demand Survey Image">
                <div class="item-title">Title Here</div>
                <div class="item-nickname">Nickname Here</div>
            </div>
           
        </div> -->
        
        
        
        
    </div>
    
    
    
</div> <!--container  -->


</div> <!--wrap  -->



<jsp:include page="../layout/footer.jsp"></jsp:include>
</body>
</html>