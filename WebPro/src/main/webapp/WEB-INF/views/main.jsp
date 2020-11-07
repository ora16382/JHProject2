<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    <c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<script>
	//isManager 값은 서버에서는 "String" 이지만 웹페이지로 가면 String 이기 때문에 콜론이 필요하다.
	if('${isManager}'=='false'){
		alert("관리자만 계정관리에 접속 할 수 있습니다.");
	}
	
	if('${abnormalConnect}'=='true'){
		alert("비정상적인 접속입니다.");
	}
</script>
<style type="text/css">
	.main{
		
		display: inline-block;
		position:absolute;
		border: solid 0.5px black;
		width: 400px;
		height:250px;
		margin:10px;
		padding:10px;
		padding-top: 0px;
		
	}
	
	#div1, #div3{
		left:320px;
	}
	
	#div2, #div4{
		right:130px;
	}
	
	#div1, #div2{
		top:260px;
	}
	
	#div3, #div4{
		top:560px;
	}
	
	.smallTitle{
		margin-bottom: 2px;
	}
	
	.innerDiv1{
		display:inline-block;
		width:300px;
	}
	.innerDiv2{
		display:inline-block;
		width: 70px;
	}
	
</style>
<meta charset="UTF-8">
<title>메인 페이지</title>
<script src="http://code.jquery.com/jquery-latest.js" ></script>
</head>
<body>
	<div class="main" id="div1">
	<h3 style="border-bottom:solid 1px grey;">자유 게시판</h3>
		<div class="innerDiv1">
			<c:forEach var="article" items="${articlesMap.articlesList}" begin="0" end="8" varStatus="stat">
			 <h5 class="smallTitle">${stat.count}. <a class='cls1' href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title }</a>&nbsp;&nbsp; [조회 : ${article.views }]<br></h5>
			</c:forEach>
		</div>
		<div class="innerDiv2">
			<c:forEach var="count" items="${articlesMap.commentCountList}" begin="0" end="8" varStatus="stat">
			<h5 class="smallTitle">[댓글 : ${count }]<br></h5>
			</c:forEach>	
		</div>
	</div>
	
	<div class="main" id="div2">
	<h3 style="border-bottom:solid 1px grey;">유머 게시판</h3>
		<div class="innerDiv1">
				<c:forEach var="article" items="${harticlesMap.articlesList}" begin="0" end="8" varStatus="stat">
				 <h5 class="smallTitle">${stat.count}. <a class='cls1' href="${contextPath}/hboard/viewArticle.do?articleNO=${article.articleNO}">${article.title }</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; [조회 : ${article.views }]<br></h5>
				</c:forEach>
		</div>
		<div class="innerDiv2">
				<c:forEach var="count" items="${harticlesMap.commentCountList}" begin="0" end="8" varStatus="stat">
				<h5 class="smallTitle">[댓글 : ${count }]<br></h5>
				</c:forEach>	
		</div>
	</div>
	
	<br>
	
	<div class="main" id="div3">
	<h3 style="border-bottom:solid 1px grey;">게임 게시판</h3>
		<div class="innerDiv1">
			<c:forEach var="article" items="${garticlesMap.articlesList}" begin="0" end="8" varStatus="stat">
			 <h5 class="smallTitle">${stat.count}. <a class='cls1' href="${contextPath}/gboard/viewArticle.do?articleNO=${article.articleNO}">${article.title }</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; [조회 : ${article.views }]<br></h5>
			</c:forEach>
		</div>
		<div class="innerDiv2">
			<c:forEach var="count" items="${garticlesMap.commentCountList}" begin="0" end="8" varStatus="stat">
			<h5 class="smallTitle">[댓글 : ${count }]<br></h5>
			</c:forEach>	
		</div>
	</div>
	
	<div class="main" id="div4">
	<h3 style="border-bottom:solid 1px grey;">정치 게시판</h3>
		<div class="innerDiv1">
			<c:forEach var="article" items="${particlesMap.articlesList}" begin="0" end="8" varStatus="stat">
			 <h5 class="smallTitle">${stat.count}. <a class='cls1' href="${contextPath}/pboard/viewArticle.do?articleNO=${article.articleNO}">${article.title }</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; [조회 : ${article.views }]<br></h5>
			</c:forEach>
		</div>
		<div class="innerDiv2">
			<c:forEach var="count" items="${particlesMap.commentCountList}" begin="0" end="8" varStatus="stat">
			<h5 class="smallTitle">[댓글 : ${count }]<br></h5>
			</c:forEach>	
		</div>
	</div>

</body>
</html>