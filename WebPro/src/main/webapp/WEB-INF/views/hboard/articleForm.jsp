<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("UTF-8");
%> 
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  /> 
<head>
<meta charset="UTF-8">
<title>글쓰기창</title>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	if(${member == null}==true){
		alert("로그인 후에 작성할 수 있습니다.");
		location.href="${contextPath}/${previous}";
	}
	
   function readURL(input) {
	   
      if (input.files && input.files[0]) {
    	  
	      var reader = new FileReader();
	 
	      reader.onload = function (e) {                    //reader 가 file 객체를 로드완료 시에 이벤트리스너(콜백함수) 실행
	        $(input).prev().prev().attr('src', e.target.result);
          }  // e.target은 reader 이고 e 는 load 이벤트, result 는 reader 가 읽어들인 파일 
         reader.readAsDataURL(input.files[0]);
      }
  }  
   
  function backToList(obj){
    obj.action="${contextPath}/hboard/listArticles.do";
    obj.submit();
  }
  
  var cnt =1;
  
  function fn_addFile(){
	  if(cnt<4){
	  $('#d_file').append("<div class='imageSpan'><img  id='preview"+cnt+"' src='#'   width=150 height=150/><br> <input type='file' name='imageFileName"+cnt+"'  onchange='readURL(this);' /></div>");
	  cnt++;
	  }else{
		  alert('더 이상 추가 할 수 없어요.');
	  }
  }

</script>
 <title>새글 쓰기 창</title>
 <style>
 	.imageSpan{
 		display:inline-block;
 		width: 150px;
 		height: 150px;
 		margin-right: 5px;
 	}
 </style>
</head>
<body>
<fieldset>
<legend>새 글 쓰기</legend>
  <form name="articleForm" method="post"   action="${contextPath}/hboard/addNewArticle.do"   enctype="multipart/form-data">
    <table border="0" align="center">
    <tr>
	   <td align="right">작성자 : </td>
	   <td colspan="2" align="left"><input type="text" size="20" name="id"  maxlength="100" value="${member.name }" readonly/></td>
	 </tr>
     <tr>
	   <td align="right">글제목 : </td>
	   <td colspan="2"><input type="text" size="69"  maxlength="500" name="title" /></td>
	 </tr>
	 <tr>
		<td align="right" valign="top"><br>글내용 : </td>
		<td colspan=2><textarea name="content" rows="10" cols="64" maxlength="4000"></textarea> </td>
     </tr>
     <tr>
        <td align="right">이미지파일 첨부 :  <br><input type="button" value="파일 추가" onClick="fn_addFile()"/></td>
	  	<td colspan=2><div id="d_file"></div></td>
	
	 </tr>
	 <tr>
	    <td align="right"> </td>
	    <td colspan="2">
	       <input type="submit" value="글쓰기" />
	       <input type=button value="목록보기"onClick="backToList(document.articleForm)" />
	    </td>
     </tr>
    </table>
  </form>
  </fieldset>
</body>
</html>
