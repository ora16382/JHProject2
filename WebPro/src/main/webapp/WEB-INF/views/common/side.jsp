<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("UTF-8");
%> 
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>

<html>
<head>
 <style>
   .no-underline{
      text-decoration:none;
      
   }
   
   .sideMenu{
   margin-top: 15px;
   	font-weight: bold;
   	font-size: 15px;
   	
   }
 
 </style>
  <meta charset="UTF-8">
  <title>사이드 메뉴</title>
</head>
<body>
	<h1>Side Menu</h1>
	
		<c:if test="${member.id=='wnsgud14'}">
		<div class="sideMenu"><a href="${contextPath }/member/listMembers.do"  class="no-underline">멤버 관리</a></div><br>
		</c:if>
	    <div class="sideMenu"><a href="${contextPath }/board/listArticles.do"  class="no-underline">자유 게시판</a></div><br>
	    <div class="sideMenu"><a href="${contextPath }/hboard/listArticles.do"  class="no-underline">유머 게시판</a></div><br>
	    <div class="sideMenu"><a href="${contextPath }/gboard/listArticles.do"  class="no-underline">게임 게시판</a></div><br>
	    <div class="sideMenu"><a href="${contextPath }/pboard/listArticles.do"   class="no-underline">정치 게시판</a></div><br>

	
</body>
</html>