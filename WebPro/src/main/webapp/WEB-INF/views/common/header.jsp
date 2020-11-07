<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("UTF-8");
%> 
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<c:set var="test" value="${requestScope.membersList}"/>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
	h6{
	margin:0;
	margin-bottom: 70px;
	}
	h5{
	margin:0;
	}
</style>
<meta charset="UTF-8">
<title>상단부</title>
</head>
<body>
<table border=0 width="100%">
	<tr>
		<td width="250">
			<a href="${contextPath}/main.do">
				<img src="${contextPath}/res/images/simson.gif" width=250 height=150>
			</a>
		</td>
		
		<td>
			<h1 style="padding-left:150px;"><font size=30>게시판</font></h1>
		</td>
		
		<td>
			<c:choose>
				<c:when test="${isLogOn == true && member != null}">
					<h5 align="right">반갑습니다. ${member.name}님</h5>
					<h6 align="right"><a href="${contextPath}/member/modMember.do?id=${member.id}">회원수정</a>&nbsp;&nbsp;<a href="${contextPath}/member/logout.do">로그아웃</a></h6>
				</c:when>
				<c:otherwise>
					<h6><a href="${contextPath }/member/loginForm.do">로그인</a></h6>
				</c:otherwise>
			</c:choose>
			
		</td>
	</tr>
</table>
</body>
</html>