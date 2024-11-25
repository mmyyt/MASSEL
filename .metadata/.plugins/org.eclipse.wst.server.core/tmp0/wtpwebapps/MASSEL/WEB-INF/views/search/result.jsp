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
          		<c:when test="${saleList ne null }">
          			<div class="item-grid">
          			<c:forEach items="${saleList }" var="saleList">
          			<a href="/salePath/detail?sno=${saleList.svo.sno }">
          				<div class="item-box">
                			<img alt="사진없음" class="" 
							src="/upload/saleImg/${fn: replace(saleList.sivo.saveDir, '\\','/')}/${saleList.sivo.uuid }_${saleList.sivo.fileName}">
                			<div class="item-nickname">${saleList.svo.swriter }</div>
                			<div class="item-title">${saleList.svo.stitle }</div>
                			<div>
                				<div class="viewCount">
                					<span><i class="fa-regular fa-eye"></i></span>
                					${saleList.svo.viewCount }
                				</div>
                				<div class="favoriteCount">
                					<span>❤️</span>${saleList.svo.favoriteCount }
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
          			<c:forEach items="${popularList }" var="popularList">
          			<a href="/salePath/detail?sno=${popularList.svo.sno }">
          				<div class="item-box">
                			<img alt="사진없음" class="" 
							src="/upload/saleImg/${fn: replace(popularList.sivo.saveDir, '\\','/')}/${popularList.sivo.uuid }_${popularList.sivo.fileName}">
                			<div class="item-nickname">${popularList.svo.swriter }</div>
                			<div class="item-title">${popularList.svo.stitle }</div>
                			<div>
                				<div class="viewCount">
                					<span><i class="fa-regular fa-eye"></i></span>
                					${popularList.svo.viewCount }
                				</div>
                				<div class="favoriteCount">
                					<span>❤️</span>${popularList.svo.favoriteCount }
                				</div>
                			</div>
           				 </div>      			
          			</a>
          			</c:forEach>
          			</div>
          			
          		</c:when>
          	</c:choose>

            <!-- saleList 반복문 끝 -->
    </div>

    <div class="demandSurveyList">
        <h2>수요조사 폼</h2>
        <hr>

              <c:choose>
          		<c:when test="${demandList ne null }">
          			<div class="item-grid">
          			<c:forEach items="${demandList }" var="demandList">
          			<a href="/demandSurvey/detail?dno=${demandList.dsvo.dno}">
          				<div class="item-box">
                			<img alt="사진없음" class="" 
							src="/upload/demandsurveyImg/${fn: replace(demandList.dsivo.saveDir, '\\','/')}/${demandList.dsivo.uuid }_${demandList.dsivo.fileName}">
                			<div class="item-nickname">${demandList.dsvo.id }</div>
                			<div class="item-title">${demandList.dsvo.dtitle }</div>
                			<div>
                				<div class="viewCount">
                					<span><i class="fa-regular fa-eye"></i></span>
                					${demandList.dsvo.dviewCount }
                				</div>
                			</div>
           				 </div>      			
          			</a>
          			</c:forEach>
          			</div>
          		</c:when>
          		<c:when test="${saleList eq null }">
          			<div class="noResult">
          				<div>'${keyword }' 에 일치하는 수요조사폼이 없습니다.</div>
          				<div>이런 수요조사폼은 어떠세요?</div>
          			</div>
          			<div class="item-grid">
          			<c:forEach items="${popularDemandList }" var="popularDemandList">
          			<a href="/demandSurvey/detail?dno=${popularDemandList.dsvo.dno}">
          				<div class="item-box">
                			<img alt="사진없음" class="" 
							src="/upload/demandsurveyImg/${fn: replace(popularDemandList.dsivo.saveDir, '\\','/')}/${popularDemandList.dsivo.uuid }_${popularDemandList.dsivo.fileName}">
                			<div class="item-nickname">${popularDemandList.dsvo.id }</div>
                			<div class="item-title">${popularDemandList.dsvo.dtitle }</div>
                			<div>
                				<div class="viewCount">
                					<span><i class="fa-regular fa-eye"></i></span>
                					${popularDemandList.dsvo.dviewCount }
                				</div>
                			</div>
           				 </div>      			
          			</a>
          			</c:forEach>
          			</div>
          			
          		</c:when>
          	</c:choose>
            
    </div>
    
    
    
</div> <!--container  -->


</div> <!--wrap  -->



<jsp:include page="../layout/footer.jsp"></jsp:include>
</body>
</html>