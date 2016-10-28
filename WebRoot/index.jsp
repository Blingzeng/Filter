<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>过滤器——解决全栈乱码</title>
	
	
  </head>
  
  <body>
    <table>
    <h1>POST提交</h1><br>
    <form action="${pageContext.request.contextPath }/servlet/demo"  method="POST">
    	<input type="text" name="in"/>
    	<input type="submit" value="提交"/>
    	
    </form>
    <h1>get提交</h1><br>
     <form action="${pageContext.request.contextPath }/servlet/demo"  method="GET">
    	<input type="text" name="getin"/>
    	<input type="submit" value="提交"/>
    	
    </form>
    
    </table>
  </body>
</html>
