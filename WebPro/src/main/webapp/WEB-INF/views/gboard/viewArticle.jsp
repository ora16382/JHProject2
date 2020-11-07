<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("UTF-8");
%> 
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<c:set var="article"  value="${articleMap.article}"  />
<c:set var="imageFileList"  value="${articleMap.imageFileList}"  />
<c:set var="commentList"  value="${articleMap.commentList}"  />

<head>
   <meta charset="UTF-8">
   <title>글보기</title>
   <style>
     #tr_btn_modify{
       display:none;
     }
     #tr_file_upload{
       display:none;
     }
   
   img#preview {
   	width:150px;
   	height:150px;
   }
   
   
 	.imageSpan{
 		display:inline-block;
 		width: 150px;
 		height: 150px;
 		margin-right: 5px;
 	}
 
 	#commentWrite{
 	background-color:#F0F8FF;
 	 margin-right: 0px; 
 	 margin-top:20px;
 	 padding-left:30px;
 	 padding-top:5px; 
 	 padding-bottom:5px;
 	 padding-right:10px;
 	}
 	
 	.comment {
 		margin:3px;
 		padding:5px;
 		border-bottom: solid 1px black;
 		width:470px;
 	}
 	
 	.writer{
 	 	background-color:#F0F8FF;
 	 	margin:3px;
 	 	padding:5px;
 	 	text-align: left;
 	}
 	
 	 .admin{
 		color:yellow;
 	}
   </style>
   <script  src="http://code.jquery.com/jquery-latest.min.js"></script> 
   <script type="text/javascript" >
   	var cnt=1;
	var imageList = new Array();
	var oriCnt=0;
   	
   	//글 목록으로 가기
     function backToList(obj){
	    obj.action="${contextPath}/gboard/listArticles.do";
	    obj.submit();
     }
 
     //수정 화면으로 전환하기, 작성자 혹은 관리자만 가능
	 function fn_enable(obj){
		 document.getElementById("i_title").disabled=false;
		 document.getElementById("i_content").disabled=false;
		 for(var i=1; i<=cnt; i++){
			 if(document.getElementById("i_imageFileName"+i)!=null){
			 document.getElementById("i_imageFileName"+i).disabled=false;
			 }
		 }
		 
		 document.getElementById("tr_btn_modify").style.display="table-row";
		 if(document.getElementById("tr_file_upload")!=null){
		 document.getElementById("tr_file_upload").style.display="table-row";
		 }
		 document.getElementById("tr_btn").style.display="none";
	 }
	 
     //수정화면에서 다시 원래화면으로
     function fn_disable(){
    	 document.getElementById("i_title").disabled=true;
		 document.getElementById("i_content").disabled=true;
		 for(var i=1; i<=cnt; i++){
			 if(document.getElementById("i_imageFileName"+i)!=null){
			 document.getElementById("i_imageFileName"+i).disabled=true;
			 }
		 }
		 
		 for(var i=0; i<cnt-1; i++){
			 var img = document.getElementById("preview"+(i+1));
			 	if(imageList[i]!=null){
				  img.src= imageList[i].src;
			 	}
			$(img).next().next().val("");
				 
		 }
		 
		 for(var i=3; i>oriCnt; i--){
			 if(document.getElementById("preview"+i)!=null){
				 var obj = document.getElementById("preview"+i);
				 var parent = document.getElementById("preview"+i).parentElement;
				 parent.parentElement.removeChild(parent);
				 cnt--;
			 }
		 }
		 document.getElementById("tr_btn_modify").style.display="none";
		 if(document.getElementById("tr_file_upload")!=null){
		 document.getElementById("tr_file_upload").style.display="none";
		 }
		 document.getElementById("tr_btn").style.display="table-row";
     }
	//수정 반영하는것
	 function fn_modify_article(obj){
		 obj.action="${contextPath}/gboard/modArticle.do";
		 obj.submit();
	 }
	 
	 //삭제하기
	 function fn_remove_article(url,articleNO,id){
		 var form = document.createElement("form");
		 form.setAttribute("method", "post");
		 form.setAttribute("action", url);
	     var articleNOInput = document.createElement("input");
	     articleNOInput.setAttribute("type","hidden");
	     articleNOInput.setAttribute("name","articleNO");
	     articleNOInput.setAttribute("value", articleNO);
	     var idInput = document.createElement("input");
	     idInput.setAttribute("type","hidden");
	     idInput.setAttribute("name","id");
	     idInput.setAttribute("value",id);
	   
	     form.appendChild(articleNOInput);
	     form.appendChild(idInput);
	     //굳이 바디안에 첨부하는 이유?
	     document.body.appendChild(form);
	     form.submit();
	 
	 }
	 
	 //답글 달기
	 function fn_reply_form(url, parentNO, parentTitle){
		 var form = document.createElement("form");
		 form.setAttribute("method", "post");
		 form.setAttribute("action", url);
	     var parentNOInput = document.createElement("input");
	     parentNOInput.setAttribute("type","hidden");
	     parentNOInput.setAttribute("name","parentNO");
	     parentNOInput.setAttribute("value", parentNO);
	     var parentTitleInput = document.createElement("input");
	     parentTitleInput.setAttribute("type","hidden");
	     parentTitleInput.setAttribute("name","parentTitle");
	     parentTitleInput.setAttribute("value", parentTitle);
	     form.appendChild(parentNOInput);
	     form.appendChild(parentTitleInput);
	     document.body.appendChild(form);
		 form.submit();
	 }
	 
	 //파일 읽어들이기
	 function readURL(input) {
		 //첨부 파일이 바뀔때마다 이 함수는 실행되어짐, 있던 파일이 없어지던, 파일이 생기던
		 var inputFile= $(input).prev().prev();
		 $(inputFile).prev().prop("disabled",true);
		 //첨부된 파일 있을때만 아래 조건문 실행
	     if (input.files && input.files[0]) {
	    	 $(inputFile).prev().prop("disabled",false);
	         var reader = new FileReader();
	         reader.onload = function (e) {
	             $(inputFile).attr('src', e.target.result);
	         }
	         reader.readAsDataURL(input.files[0]);
	     }
	 }  
	 
	 
	 //파일 추가
	 function fn_addFile(){
		  if(cnt<4){
		  $('#d_file').append("<div class='imageSpan'><img  id='preview"+cnt+"' src='#'   width=150 height=150/><br> <input type='file' name='imageFileName"+cnt+"' id='i_imageFileName"+cnt+"'  onchange='readURL(this);' /></div>");
		  cnt++;
		  }else{
			  alert('더 이상 추가 할 수 없어요.');
		  }
		  resize(178);
	  }

	 // 콜백 함수에서는 전역변수나 그 밖에 변수를 접근은 가능하나 수정 할 수없음
	 //이미지 확대
	 function viewImage(img){
		 var im = new Image();
		 im.onload = function(){
			 var imgWin = window.open("","","width="+im.width+",height="+im.height);
			 imgWin.document.write("<html><head><title>Image 확대</title></head>");
			 imgWin.document.write("<body topmargin=0 leftmargin=0>");
			 imgWin.document.write("<img src='"+img.src+"' onclick='self.close()' style='cursor:pointer;' title='클릭하시면 창이 닫힙니다.'>");
			 imgWin.document.close();
		 }
		 im.src=img.src;
	 }
	 
	 //ajax로 코멘트 보내기
	 function add_comment(articleNO, obj){
		 if($(obj).prev().val()!=""){
		 $.ajax({
			url:"${contextPath}/gboard/addNewComment",
			type:"post",	 
			data : {articleNO:articleNO,
					body:$(obj).prev().val()},
		    success:function (data,textStatus){
		    	//AJAX 로 그냥 객체를 가져오는것은 불가능함 html에서 요청시에만 가능한거임
		    
		    	if(data=='noLogin'){
		    		alert("로그인해야 댓글을 작성할 수 있습니다.");
		    		return;
		    	}else if(data=='Failed'){
		    		alert("작성에 실패하였습니다.");
		    		return;
		    	}else{
		    		var comment = JSON.parse(data);
		    		resize(32);
		    		if(comment.id=='wnsgud14'){
		    			$('#commentTable').append("<tr><td class='writer' class='admin'>작성자 : "+"<span class='admin'>관리자</span>"+"</td><td class='comment'>"+comment.body+"</td></tr>");
		    		}else{
		    			$('#commentTable').append("<tr><td class='writer'>작성자 : "+comment.id+"</td><td class='comment'>"+comment.body+"</td></tr>");	
		    		}
		    	}
		    	
		    },
		    error:function (data,textStatus){
		    	
		    },
		    complete:function(data,textStatus){
		    	
		    }
		 });
		 }
	 }
	 
	 function resize(size){
			$("#content").height($("#content").height()+size);
			$("#sidebar-left").height($("#sidebar-left").height()+size);
	 }
	
 </script>
</head>
<body>
<h6 style="border: 1px solid grey; width: 75px; margin-bottom:10px; margin-left: 28em; padding: 1px; " align="right">조회수 :  ${article.views}</h6>
  <form name="frmArticle" method="post"  action="${contextPath}/gboard/modArticle.do"  enctype="multipart/form-data">
  <table  border=0  align="center">
  <tr>
   <td width=150 align="center" bgcolor=#F0F8FF>
      글번호
   </td>
   <td >
    <input type="text"  value="${article.articleNO }"  disabled />
    <input type="hidden" name="articleNO" value="${article.articleNO}"  />
   </td>
  </tr>
  <tr>
    <td width="150" align="center" bgcolor="#F0F8FF">
      작성자 아이디
   </td>
   <td >
    <input type=text value="${article.id }" name="writer"  disabled />
    <input type=hidden value="${article.id }" name="write"  />
   </td>
  </tr>
  <tr>
    <td width="150" align="center" bgcolor="#F0F8FF">
      제목 
   </td>
   <td>
    <input type=text value="${article.title }"  name="title"  id="i_title" disabled />
   </td>   
  </tr>
  <tr>
    <td width="150" align="center" bgcolor="#F0F8FF">
      내용
   </td>
   <td>
    <textarea rows="20" cols="60"  name="content"  id="i_content"  disabled />${article.content }</textarea>
   </td>  
  </tr>
 
 <c:choose>
 <%--이미지가 존재할 시 출력 --%>
<c:when test="${not empty imageFileList && imageFileList!=null }">
<tr>
<td width="150" align="center" bgcolor="#F0F8FF" >이미지</td>
<td><div id="d_file">  
	<c:forEach var="item" items="${imageFileList }" varStatus="status">
	<div class='imageSpan'>
	<input  type= "hidden"   name="originalFileName${status.count }" value="${item.imageFileName }" disabled/>
	<img  id='preview${status.count}' src='${contextPath}/download.do?articleNO=${article.articleNO}&imageFileName=${item.imageFileName}&directory=game'   width=150 height=150 onClick="viewImage(this)"/><br>
	 <input type='file' name='imageFileName${status.count }' id='i_imageFileName${status.count }'  onchange='readURL(this);'  disabled/>
	</div>
	<script>
		cnt = ${status.count+1};
		oriCnt = ${status.count};
		imageList[${status.index}] = new Image();
   		imageList[${status.index}].src='${contextPath}/download.do?articleNO=${article.articleNO}&imageFileName=${item.imageFileName}&directory=game';
	</script>
	</c:forEach>
	</div></td>
	</tr>  
	 <tr id="tr_file_upload">
        <td width="150" bgcolor="#F0F8FF" align="center">이미지파일 첨부 :  <br><input type="button" value="파일 추가" onClick="fn_addFile()"/></td>
	 </tr>
 </c:when>
 
 <%-- 이미지가 존재하지 않을 시 아래와 같이 출력--%>
 <c:otherwise>
   <tr id="tr_file_upload">
        <td width="150" bgcolor="#F0F8FF" align="center">이미지파일 첨부 :  <br><input type="button" value="파일 추가" onClick="fn_addFile()"/></td>
	  	<td colspan=2><div id="d_file"></div></td>
	
	 </tr>
 </c:otherwise>
 </c:choose>
 
  <tr>
	   <td width="150" align="center" bgcolor="#F0F8FF">
	      등록일자
	   </td>
	   <td>
	    <input type=text value="<fmt:formatDate value="${article.writeDate}" />" disabled />
	   </td>   
  </tr>
  
  <tr id="tr_btn_modify"  >
	   <td colspan="2"   align="center" >
	       <input type=button value="수정반영하기"   onClick="fn_modify_article(frmArticle)"  >
           <input type=button value="취소"  onClick="fn_disable()">
	   </td>   
  </tr>
    
  <tr  id="tr_btn"    >
   <td colspan="2" align="center">
   <c:if test="${member.id==article.id || member.id=='wnsgud14' }">
	    <input type=button value="수정하기" onClick="fn_enable(this.form)">
	    <input type=button value="삭제하기" onClick="fn_remove_article('${contextPath}/gboard/removeArticle.do', ${article.articleNO},'${article.id}')">
	</c:if>
	    <input type=button value="리스트로 돌아가기"  onClick="backToList(this.form)">
	     <input type=button value="답글쓰기"  onClick="fn_reply_form('${contextPath}/gboard/replyForm.do', ${article.articleNO},'${article.title }')">
   </td>
  </tr>
 </table>
 </form>
 <br>
 <label id="commentWrite">답글 작성 : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" value="" size="55">&nbsp;&nbsp;&nbsp;<input type="button" value="작성" onclick="add_comment(${article.articleNO},this)" /></label>
 <table border="0" align="center" id="commentTable">
	<c:forEach var="com" items="${commentList}" varStatus="status">
		<c:choose>
		<c:when test="${com.id=='wnsgud14' }">
		<tr><td class='writer'>작성자 : <span class="admin">관리자</span></td><td class='comment'>${com.body}</td></tr>
		</c:when>
		<c:otherwise>
		<tr><td class='writer' class="admin">작성자 : ${com.id}</td><td class='comment'>${com.body}</td></tr>
		</c:otherwise>
		</c:choose>
		<script> resize(32)</script>	
	</c:forEach>
 </table>
 
</body>
</html>