<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>독서인증 평가결과창</title>

<script type="text/javascript">

function inpNum(ok){
	
	if(ok=='1'){
		alert("수락하셨습니다.")
	}
	else if(ok=='0'){
		alert("수락하지 않으셨습니다.")
	}
	
	window.close();
}

</script>

<link rel="stylesheet" href="style2.css">
</head>
<%
	int reportid = Integer.parseInt(request.getParameter("reportid"));
%>
<body>

<div class="container">
<center>
<p>표절 검사 결과</p>

</br></br>
독서인증평가를 수락하시겠습니까?
</br>
<div>
</br></br>
<form method="POST" action="AssessServlet">
<input type="hidden" name="reportid" value="<%=reportid %>" />
<input type="submit" value="완료" onClick="window.close()">
<input type="button" value="탈락" name="btn_0" onClick="inpNum(0)">
</form>
</div>
</center>
</div>


</body>
</html>