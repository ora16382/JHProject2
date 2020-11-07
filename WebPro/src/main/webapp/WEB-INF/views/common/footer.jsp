<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("UTF-8");
%> 
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  /> 
<!DOCTYPE html>
<html>
<head>

<script>
	function logout(){
		location.href="${contextPath}/member/logout.do";
	}
	
	function sign_in(){
		location.href="${contextPath}/member/memberForm.do";
	}
</script>
<meta charset="UTF-8">
<title>하단 부분</title>
<style>
    p.foot {
     font-size:20px;
      text-align:right;
    }
  </style>
</head>
<body>
<p class="foot"> ver 20.11.03</p> 
<p class="foot"> Product By 정준형(ora16382@gmail.com) </p>
<div align="left">
<input type="button" value="회원가입" onclick="sign_in()">
<input type="button" value="로그아웃" onclick="logout()">
</div>
</body>
</html>