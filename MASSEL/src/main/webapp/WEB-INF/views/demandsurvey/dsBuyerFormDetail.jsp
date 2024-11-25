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
<link rel="stylesheet" type="text/css" href="/resources/css/dsBuyerFormDetail.css">
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
			src="/upload/demandSurveyImg/${fn: replace(data.dsivo.saveDir, '\\','/')}/${data.dsivo.uuid }_${data.dsivo.fileName}">
          	</div>
          	<div>
          		<div class="top-title">
          			${data.dsvo.dtitle }
          		
          		</div>
          		<div class="top-btn">
          			<c:if test="${data.dsvo.disDel == 0 }">
          				<a href="/demandSurvey/detail?dno=${data.dsvo.dno }"><button>보러가기</button></a>
          			</c:if>
          			<c:if test="${data.dsvo.disDel == 1 }">
          				작성자에 의해 삭제된 폼입니다.
          			</c:if>
          			<button class="messageBtn" onclick="openChat('${data.dsvo.id}')">메세지</button>
          		</div>
           </div>
           
           </div> 	<!-- top -->


			<div class="bottom">
			
				<div>
			
					<div class="paticipation"><!--참여정보  -->
						<div class="pinfo">참여 기본 정보</div>
						<div>
							<ul>
								<li>참여 아이디</li>
								<li>${info.id }</li>
							</ul>
							<ul>
								<li>이메일</li>
								<li>${info.email }</li>
							</ul>
							<ul>
								<li>수요조사 참여일</li>
								<li>${info.participationDate }</li>
							</ul>

						</div>
					</div>
				
					<div class="participationProduct"> <!--참여상품정보  -->
						<div class="pinfo">참여 수요조사 정보</div>
						<div>
							<c:forEach items="${list }" var="list">
							<ul>
								<li>
									${list.dpname }  | ${list.count } 개
								</li>
								<li>
									<c:set var="totalPrice" value="${list.dpprice * list.count}" />
									총 가격 : <c:out value="${totalPrice}" /> 원
								</li>
							</ul>
						</c:forEach>
						</div>
						
					</div>
				
				</div>
			
			
			</div> <!-- bottom -->
       
            
                  
        </div>  <!-- contentBox  -->
    </div>  <!-- content -->
    
    
</div><!--wrapper끝  -->



<jsp:include page="../layout/footer.jsp"></jsp:include>
<script type="text/javascript" src="/resources/js/dsBuyerFormDetail.js"></script>
<script type="text/javascript">
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
</body>
</html>