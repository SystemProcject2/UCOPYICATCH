<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<title>Insert title here</title>
</head>
<script language="javascript">
moveTo(0,0)
resizeTo(1100,500)
</script>
<body>
<%
String original = request.getParameter("original");
String compare = request.getParameter("compare");
String link = request.getParameter("link");
%>
<div class="w3-container w3-blue">
<h2>상세 정보</h2>
</div>

<form class="w3-container">

<p>
<label>표절의심 문장</label>
<input class="w3-input" type="text" value="<%=original %>" readonly></p>

<p>
<label>비교 문장</label>
<input class="w3-input" type="text" value="<%=compare %>" readonly></p>

<p>
<label>원본 출처</label>
<a href="<%=link %>"><input class="w3-input" type="text" value="<%=link %>" readonly></a></p>

</form>
</body>
</html>