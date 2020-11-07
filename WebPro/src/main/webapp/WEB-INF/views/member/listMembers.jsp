<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    isELIgnored="false"  %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<%
  request.setCharacterEncoding("UTF-8");
%>    


<html>
<head>
<meta charset=UTF-8">
<title>회원 정보 출력창</title>
</head>
<body>
<table border="1"  align="center"  width="80%">
    <tr align="center"   bgcolor="#F0F8FF">
      <td ><b>아이디</b></td>
      <td><b>비밀번호</b></td>
      <td><b>이름</b></td>
      <td><b>이메일</b></td>
      <td><b>가입일</b></td>
      <td><b>삭제</b></td>
      <td><b>수정</b></td>
   </tr>
 <c:forEach var="member" items="${requestScope.membersList}"  >     
   <tr align="center">
   	<c:choose>
   		<c:when test="${member.id == 'wnsgud14' }">
   			<td style="color: yellow; font-weight: bold;">[관리자]${member.id}</td>
   		</c:when>
      	<c:otherwise>
      		<td>${member.id}</td>
      	</c:otherwise>
    </c:choose>
      <td>${member.pwd}</td>
      <td>${member.name}</td>
      <td>${member.email}</td>
      <td>${member.joinDate}</td>
      <td><a href="${contextPath}/member/removeMember.do?id=${member.id}">삭제</a></td>
      <td><a href="${contextPath}/member/modMember.do?id=${member.id}">수정</a></td>
    </tr>
  </c:forEach>   
</table>
</body>
</html>