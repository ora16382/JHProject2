<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"%>
    
   <%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
 <style>
 	 
      #container {
        width: 100%;
        margin: 0px auto;
          text-align:center;
        border: 0px solid #bcbcbc;
      }
      #header {
        padding: 5px;
        margin-bottom: 5px;
        border: 0px solid #bcbcbc;
         background-color: #F0F8FF;
      }
      #sidebar-left {
        width: 15%;
        height:860px;
        padding: 5px;
        margin-right: 5px;
        margin-bottom: 5px;
        float: left;
         background-color: #F0F8FF;
        border: 0px solid #bcbcbc;
        font-size:10px;
      }
      #content {
        width: 81%;
         height:860px;
        padding: 5px;
        margin-right: 5px;
        float: left;
        border: 0px solid #bcbcbc;
        background-color:#fefeff;
      }
      #footer {
        clear: both;
        padding: 5px;
        border: 0px solid #bcbcbc;
         background-color: #F0F8FF;
      }
      
      a {
      	color:black;
      	text-decoration: none;
      }
      
      a:link{
      	color:balck;
      }
      a:visited{
      	color:black;
      }
      a:hover{
      	color:grey;
      }
      
    </style>
<meta charset="UTF-8">
<title><tiles:insertAttribute name="title"/></title>
</head>
<body>
<div id="container">
	<div id="header">
		<tiles:insertAttribute name="header" />
	</div>
	
	<div id="sidebar-left">
		<tiles:insertAttribute name="side"/>
	</div>
	
	<div id="content">
		<h3 align="left" style="border: solid 0.5px black; padding-bottom:3px; padding-top: 8px; padding-left: 20px;"><tiles:insertAttribute name="subTitle"/></h3>
		<tiles:insertAttribute name="body"/>
	</div>
	
	<div id="footer">
		<tiles:insertAttribute name="footer"/>
	</div>
</div>
</body>
</html>