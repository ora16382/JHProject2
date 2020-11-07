<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<c:set  var="articlesList"  value="${articlesMap.articlesList}" />
<c:set  var="commentCountList"  value="${articlesMap.commentCountList}" />
<c:set  var="totArticles"  value="${articlesMap.totArticles}" />
<c:set  var="section"  value="${articlesMap.section}" />
<c:set  var="pageNum"  value="${articlesMap.pageNum}" />

<%
  request.setCharacterEncoding("UTF-8");
%>  
<!DOCTYPE html>
<html>
<head>
<script>
 	function fn_articleForm(isLogOn,id,articleForm,loginForm){
 		if(isLogOn !=''&&id!=''){
 			location.href=articleForm;
 		}else{
 			alert('로그인 후 글쓰기가 가능합니다.');
 			location.href=loginForm+'?action=/hboard/articleForm.do';
 		}
 	}
 </script>

 <style>
   .no-uline {text-decoration:none;}
   .sel-page{text-decoration:none;}
   .cls1 {
   text-decoration:none;
   text-align:right;
   }
   .cls2{text-align:center; font-size:20px;margin-top: 30px;margin-bottom:0px;}

   .cls3{
      position:absolute;
   display: inline-block;
   float: right;
   right: 180px;
   font-size:20px;
   border: solid 1px grey;
   background-color: #F0F8FF;
   margin-top: 0px;
   }
  </style>
  <meta charset="UTF-8">
  <title>글목록창</title> 
</head>
<body>
<table align="center" border="1"  width="75%" style="display:inline-table">
  <tr height="10" align="center"  bgcolor="#F0F8FF">
     <td >글번호</td>
     <td >작성자</td>              
     <td >제목</td>
     <td >작성일</td>
     <td>조회수</td>
  </tr>
<c:choose>
	 <%--  articlesList 가 null, 혹은 리스트 객체는 있으나 요소가 없는경우에 해당 --%>
  <c:when test="${empty articlesList}" >  
    <tr  height="10">
      <td colspan="5">
         <p align="center">
            <b><span style="font-size:9pt;">등록된 글이 없습니다.</span></b>
        </p>
      </td>  
    </tr>
  </c:when>
  	<%-- 조회된 article 이 있는경우  --%>
  <c:when test="${!empty articlesList}" >
    <c:forEach  var="article" items="${articlesList }" varStatus="articleNum" >
     <tr align="center">
	<td width="5%">${article.articleNO}</td>
	<td width="10%">${article.id }</td>
	<td align='left'  width="35%">
	    <span style="padding-right:30px"></span>    
	   <c:choose>
	 		<%-- 조회된 article이 답변인 경우 제목 앞쪽 여백과 답변이란 부분 기입 --%>
	      <c:when test='${article.level > 1 }'>  
	         <c:forEach begin="1" end="${article.level }" step="1">
	             <span style="padding-left:10px"></span> 
	         </c:forEach>
	         <span style="font-size:12px;">[답변]</span>
                   <a class='cls1' href="${contextPath}/hboard/viewArticle.do?articleNO=${article.articleNO}">${article.title}</a>
	          </c:when>
	          <%-- 조회된 article이 답변이 아닌 본문인 경우, 제목 앞쪽 여백과  [답변] 이란 부분 생략--%>
	          <c:otherwise>
	            <a class='cls1' href="${contextPath}/hboard/viewArticle.do?articleNO=${article.articleNO}">${article.title }</a>
	          </c:otherwise>
	        </c:choose>
	  </td>
	  <td  width="10%"><fmt:formatDate value="${article.writeDate}" /></td>
	  <td  width="4%">${article.views}</td>  
	</tr>
    </c:forEach>
     </c:when>
     
    </c:choose>
</table>

<table align="center" border="1"  width="5%" style="display: inline-table; margin-left: 0px;">
 	<tr height="10" align="center"  bgcolor="#F0F8FF">
     <td>댓글수</td>
     </tr>
     <c:choose>
	 <%--  articlesList 가 null, 혹은 리스트 객체는 있으나 요소가 없는경우에 해당 --%>
  <c:when test="${empty commentCountList}" >  
    <tr  height="10">
      <td>
         <p align="center">
            <b><span style="font-size:9pt;">-</span></b>
        </p>
      </td>  
    </tr>
  </c:when>
  <c:otherwise>
  	 <c:forEach  var="comment" items="${commentCountList }" varStatus="articleNum" >
     <tr align="center">
	<td width="5%">${comment}</td>
	</tr>
	</c:forEach>
  </c:otherwise>
  </c:choose>
</table>

<div class="cls2">
 <c:if test="${totArticles != null }" >
      <c:choose>
      <%-- 글 개수가 100 초과인경우 --%>
        <c:when test="${totArticles >=100 }"> 
        	<%--  마지막 섹션일 경우에는 전체글 갯수 %100 해서 나머지 많큼 페이지 버튼 출력 --%>
          <c:choose>
       	  <c:when test="${section>(totArticles/100) }">	
	      <c:forEach   var="page" begin="1" end="${(totArticles % 100)/20 +1 }" step="1" >
	      <%--2섹션 이상일때 이전 섹션으로 가는 텍스트 출력 --%>
	         <c:if test="${section >1 && page==1 }">
	          <a class="no-uline" href="${contextPath }/hboard/listArticles.do?section=${section-1}&pageNum=10">&nbsp; Pre </a>
	         </c:if>
	         
	         
	          <c:choose>
	           <c:when test="${page==pageNum }">
	          <a class="sel-page" style="color:aqua" href="${contextPath }/hboard/listArticles.do?section=${section}&pageNum=${page}">${(section-1)*10 +page } </a>
	          </c:when>
	          <c:otherwise>
	            <a class="no-uline"  href="${contextPath }/hboard/listArticles.do?section=${section}&pageNum=${page}">${(section-1)*10 +page }  </a>
	          </c:otherwise>
	         </c:choose>
	          
	          
	         <c:if test="${page ==10 }">
	         	<c:if test="${totArticles >100 }">
	          	<a class="no-uline" href="${contextPath }/hboard/listArticles.do?section=${section+1}&pageNum=1">&nbsp; Next</a>
	         	 </c:if>
	         </c:if>
	        </c:forEach>
	       </c:when>
	
	       
			<%--마지막 섹션이 아닐때는 무조건 10개 페이지 버튼 출력,  --%>
	       <c:otherwise >
	         <c:forEach   var="page" begin="1" end="10" step="1" >
	      <%--2섹션 이상일때 이전 섹션으로 가는 텍스트 출력 --%>
	         <c:if test="${section >1 && page==1 }">
	          <a class="no-uline" href="${contextPath }/hboard/listArticles.do?section=${section-1}&pageNum=10">&nbsp; Pre </a>
	         </c:if>
	         
	         <c:choose>
	           <c:when test="${page==pageNum }">
	          <a class="sel-page" style="color:aqua" href="${contextPath }/hboard/listArticles.do?section=${section}&pageNum=${page}">${(section-1)*10 +page } </a>
	          </c:when>
	          <c:otherwise>
	           <a class="no-uline"  href="${contextPath }/hboard/listArticles.do?section=${section}&pageNum=${page}">${(section-1)*10 +page }  </a>
	          </c:otherwise>
	         </c:choose>
	          
	         <c:if test="${page ==10 }">
	         	<c:if test="${totArticles >100 }">
	         	<%-- 마지막 섹션이여도 section==(totArticles/100) 일 경우에는 이 조건문으로 오게되는데 그 경우엔 Next를 표시할 필요가 없으니 아래와 같이 해준다.  --%>
	         	<c:if test="${section!=(totArticles/100) }">
	          <a class="no-uline" href="${contextPath }/hboard/listArticles.do?section=${section+1}&pageNum=1">&nbsp; Next</a>
	          	</c:if>
	          </c:if>
	         </c:if>
	         
	      </c:forEach>
	      </c:otherwise>
	      </c:choose>
        </c:when>
        
        <%--등록된 글 개수가 100개 미만인 경우 --%>
        <c:when test="${totArticles< 100 }" > 
	      <c:forEach   var="page" begin="1" end="${totArticles/20 +1}" step="1" >
	         <c:choose>
	           <c:when test="${page==pageNum }">
	            <a class="sel-page" style="color:aqua" href="${contextPath }/hboard/listArticles.do?section=${section}&pageNum=${page}">${page } </a>
	          </c:when>
	          <c:otherwise>
	            <a class="no-uline"  href="${contextPath }/hboard/listArticles.do?section=${section}&pageNum=${page}">${page } </a>
	          </c:otherwise>
	        </c:choose>
	      </c:forEach>
        </c:when>
      </c:choose>
    </c:if>
</div>  

<br>
<a href="javascript:fn_articleForm('${isLogOn}','${member.id }','${contextPath }/hboard/articleForm.do','${contextPath }/member/loginForm.do')"><span class="cls3">글쓰기</span></a>
</body>
</html>