<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="style4.css">

<script type="text/javascript">
function inpNum(ok){
	
	if(ok=='1'){
		alert("등록되었습니다..")
	}
	
	window.close();
}
</script>

<script language="javascript">
moveTo(0,0)
resizeTo(615,685)
</script>

</head>
<body>

<div>
  <form method="POST" enctype="multipart/form-data" action="TestServlet">
  <table>
  <tr>
  <td> <label for="fname">학번</label>
    <input type="text" id="sname" name="studentNumber"></td>
    <td>
     <label for="lname">이름</label>
    <input type="text" id="name" name="name"></td>
  </tr>
  
  <tr>
  <td><label for="lcode">코드</label>
    <input type="text" id="scode" name="code"></td>
    <td>
	<label for="lbookname">책 이름</label>
    <input type="text" id="sbname" name="title"></td>
  </tr>
  
  <tr>
  <td colspan=2> <label for="lupload">파일올리기</label>
    <input type="file" name="uploadFile">
 	
 	<TextArea  name="text"></TextArea><br></td>
 	
  </tr>
  
   <tr>
  <td colspan=2 align="center">
  <input type="radio" name="type" value="1">빠른검사
  <input type="radio" name="type" value="2">정밀검사
  </br></br>
  </td>
  </tr>
  
  <tr>
  <td colspan=2>
  <input type="submit" value="upload" onClick="window.close()"><br>
  </td>
  </tr>
  </table>
 
  
  </form>
</div>

</body>
</html>