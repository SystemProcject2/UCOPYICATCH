<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>�������� �򰡰��â</title>

<script type="text/javascript">

function inpNum(ok){
	
	if(ok=='1'){
		alert("�����ϼ̽��ϴ�.")
	}
	else if(ok=='0'){
		alert("�������� �����̽��ϴ�.")
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
<p>ǥ�� �˻� ���</p>

</br></br>
���������򰡸� �����Ͻðڽ��ϱ�?
</br>
<div>
</br></br>
<form method="POST" action="AssessServlet">
<input type="hidden" name="reportid" value="<%=reportid %>" />
<input type="submit" value="�Ϸ�" onClick="window.close()">
<input type="button" value="Ż��" name="btn_0" onClick="inpNum(0)">
</form>
</div>
</center>
</div>


</body>
</html>