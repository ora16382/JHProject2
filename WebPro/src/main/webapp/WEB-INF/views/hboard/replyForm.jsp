<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%
  request.setCharacterEncoding("UTF-8");
%> 
<head>
<meta charset="UTF-8">
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	if(${member == null}==true){
		alert("로그인 후에 작성할 수 있습니다.");
		location.href="${contextPath}${previous}";
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
 <style>
 	.imageSpan{
 		display:inline-block;
 		width: 150px;
 		height: 150px;
 		margin-right: 5px;
 	}
</style>
<title>답글쓰기 페이지</title>
</head>
<body>
<fieldset>
<legend>답글 쓰기</legend>
  <form name="frmReply" method="post"  action="${contextPath}/hboard/addNewArticle.do"   enctype="multipart/form-data">
    <table align="center">
    <tr>
			<td align="right">작성자 : </td>
			<td><input type="text" size="20" value="lee" disabled /></td>
		</tr>
		<tr>
			<td align="right">부모글 제목</td>
			<td><input type="text" name="parentTitle" value="${param.parentTitle}" disabled></td>
			<td><input type="hidden" name="parentNO" value="${param.parentNO}"></td>
		</tr>
		<tr>
			<td align="right">글제목 : </td>
			<td><input type="text" size="69"  maxlength="100" name="title" /></td>
		</tr>
		<tr>
			<td align="right" valign="top"><br>글내용 : </td>
			<td><textarea name="content" rows="10" cols="65" maxlength="4000"> </textarea> </td>
		</tr>
    <tr>
        <td align="right">이미지파일 첨부 : <br><input type="button" value="파일 추가" onClick="fn_addFile()"/></td>
	  	<td colspan=2><div id="d_file"></div></td>
		
	 </tr>
		<tr>
			<td align="right"> </td>
			<td>
				<input type=submit value="답글반영하기" />
				<input type=button value="취소"onClick="backToList(this.form)" />
				
			</td>
		</tr>
    </table>
  </form>
  </fieldset>
</body>
</html>