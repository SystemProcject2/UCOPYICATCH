<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>표절 검사</title>
<link rel="stylesheet" href="normalize.css">
<link rel="stylesheet" href="style.css">

<script language="javascript"> 
function OpenWindow(url,intWidth,intHeight) { 
      window.open(url, "_blank", "width="+intWidth+",height="+intHeight+",resizable=1,scrollbars=1") ; 
}
function openDetail(original,compare,link) {
	var url='detail.jsp?original='+original+'&compare='+compare+'&link='+link;
	window.open(url,'win','width=200','height=200','scrollbars=1');
}

function openAssessReport(reportid) {
	var url='AssessReport.jsp?reportid='+reportid;
	window.open(url,'win','width=200','height=200','scrollbars=1');
}
</script>
</head>
<body>
	<div class="container">
		<center>
			<h2>UCIC</h2>
		</center>
		<%@ page
			import="java.util.ArrayList, kumoh.d445.ucopyicatch.bookreport.CheckReport"%>
		<jsp:useBean id="bookReportdb"
			class="kumoh.d445.ucopyicatch.database.BookReportDAO" scope="page" />
		<table class="rules">
			<thead>
				<tr>
					<th>번호</th>
					<th>검사유형</th>
					<th>진행상태</th>
					<th width=100>표절률</th>
					<th>이름</th>
					<th>학번</th>
					<th>도서명</th>
					<th class="date">평가신청일</th>
					<th class="date">평가상태</th>
					<th class="last">평가</th>

				</tr>
			</thead>
			<%
				ArrayList<CheckReport> list = bookReportdb.selectCheckReport();
				int counter = list.size();
				if (counter > 0) {
			%>
			<% for(int i=0 ; i < counter ; i++) {%>
			<tbody>
				<tr class="record">
					<td><%=list.get(i).getReportid()%></td>
					<%String type=null;
					if(list.get(i).getType()==1) {
						type="빠른검사";
					}
					else if(list.get(i).getType()==2) {
						type="정밀검사";
					}%>
					<td><%=type%></td>
					<td>
						<%String opCheck=null;
					if(list.get(i).getOpcheck()==0) {
						opCheck="검사 전";
					}
					else if(list.get(i).getOpcheck()==1) {
						opCheck="검사 중";
					}
					else if(list.get(i).getOpcheck()==2) {
						opCheck="검사 완료";
					}%> <%=opCheck%>
					</td>
					<td class="rating"><%=list.get(i).getCopyrate()%></td>
					<td class="overflow"><%=list.get(i).getName()%></td>
					<td class="overflow"><%=list.get(i).getStudentNumber()%></td>
					<td class="overflow"><%=list.get(i).getTitle() %></td>
					<td class="date"><%=list.get(i).getDate()%></td>
					<%String eval=null;
					if(list.get(i).getEval()==1) {
						eval="미평가";
					}
					else if(list.get(i).getEval()==2) {
						eval="평가완료";
					}%>
					<td><%=eval %></td>
					<td class="last"><a
						href="javascript:openAssessReport('<%=list.get(i).getReportid()%>')"><img
							class="filter"
							src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABcAAAAYCAYAAAARfGZ1AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAvhJREFUeNqklU9I1EEUx/ePuxaVlEZRCdWlIOvQJSGhoH+HIokOHaKMMMqICMq6dNk1Okh2yDCL0BKtQ4cOhRUkBFZs3iI7FBkGLkQIlUgsurva57vMb5udfqtQDz68fTPvvXm/mTezwUQiEfAkGAwGpqen87+FkRJogOWQgSf4vQlYojg73gsqKtZCtajr1tQ22ArZmeJDPmMbYK1ll01NTZ2FgEUNC+9x4srcRG7lO6jyPjoCL+Al1DJW41NEq9mmn3AQn2p0HG7kt9na8y3wECoC/y7ax9PQlqs8m81vW9VMia3DLTg01w2O5ZNbQT1wAVb5BP2AxzAKC4jZVcRPcie/5+l0OldVKBQaR793gxi7RqUt6KQ1vBQOM97sNEXabO1fB1qJ80anFePhcDiWizJFaEvgWzQabUF/ZarbbIfXfeVsdVJdJWMhnMB4BCusduuHS0oqkANwWb5QOjExobF7JOq0YsLQrq7LVY7RQUX7ffbuJuNZBaEb0Ve8yk3bHWdR3dY27HorbjM+z9EdqrzafGoByJAS6wywY3aXoI+qdc0TMYI96sRK6kswwkVayzuoUpjjMz/far+wbwISt8O4c73Fat0B9Gfm79pz2APoV8auhHI3Hp936pYmjGcYF3XVrYUb+OQHegWZP69P5/d6GGa8Fb5rcfRJp+Bh9TrjXcG+vr6AnAiex+AnWGY5xpiLq5JIJPKnmeke7oXQm9JjtWLadEq/zqMklUp5Mb90kdzksAhaSJi03uslUMeizVZiyRfmX+uHirEv0W6cV/qcyxk4BL3e9YedOhMfX93udTA4OTlZ8HDVmc7wkwozP5to745AY/5tMdLNZ29HL/6fJxcZ9HtbejOZjKq7rb0zb4YO5BRs8snyAdVJQWvQ+2AunOMt6ir2T/SUoCoCxrwB9nYA9da+SHq4UHtpgCHj06SHDzsx23/omGN/5Is6dDYeJNPXDVk+I5CY7T+0QHTiRq6SUJ0SVcvyZbe8hXh6i8b/FmAAat7XYReSgx0AAAAASUVORK5CYII="
							width="26" /></a></td>
				</tr>
				<tr class="companion">
					<td class="output" colspan="10">
						<p>
							<%int cnt=0; %>
							<%for(int j=0 ; j < list.get(i).getReport().size() ; j++) { %>
							<% if(list.get(i).getCopySentece().isOriginalIndex(j)) { 
							int index = list.get(i).getCopySentece().getSuspendPlagiarismResultItemDataIndex(j);%>
							<a href="javascript:openDetail('<%=list.get(i).getCopySentece().getResult().get(index).getPartOfOriginalDocSentence()%>','<%=list.get(i).getCopySentece().getResult().get(index).getCompareDocSentence()%>','<%=list.get(i).getCopySentece().getResult().get(index).getCompareDocLink()%>')"><%=list.get(i).getReport().get(j)%></a>
							<%cnt++;} else {%><%=list.get(i).getReport().get(j)%>
							<%}
						}%></p>
					</td>
				</tr>
				<%		}	//for문
					}	//if문%>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="10"></td>
				</tr>
			</tfoot>
		</table>

		<!-- 		<a -->
		<!-- 						href="javascript:OpenWindow('AssessReport.jsp','470','340')"><img -->
		<!-- 							class="filter" -->
		<!-- 							src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABcAAAAYCAYAAAARfGZ1AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAvhJREFUeNqklU9I1EEUx/ePuxaVlEZRCdWlIOvQJSGhoH+HIokOHaKMMMqICMq6dNk1Okh2yDCL0BKtQ4cOhRUkBFZs3iI7FBkGLkQIlUgsurva57vMb5udfqtQDz68fTPvvXm/mTezwUQiEfAkGAwGpqen87+FkRJogOWQgSf4vQlYojg73gsqKtZCtajr1tQ22ArZmeJDPmMbYK1ll01NTZ2FgEUNC+9x4srcRG7lO6jyPjoCL+Al1DJW41NEq9mmn3AQn2p0HG7kt9na8y3wECoC/y7ax9PQlqs8m81vW9VMia3DLTg01w2O5ZNbQT1wAVb5BP2AxzAKC4jZVcRPcie/5+l0OldVKBQaR793gxi7RqUt6KQ1vBQOM97sNEXabO1fB1qJ80anFePhcDiWizJFaEvgWzQabUF/ZarbbIfXfeVsdVJdJWMhnMB4BCusduuHS0oqkANwWb5QOjExobF7JOq0YsLQrq7LVY7RQUX7ffbuJuNZBaEb0Ve8yk3bHWdR3dY27HorbjM+z9EdqrzafGoByJAS6wywY3aXoI+qdc0TMYI96sRK6kswwkVayzuoUpjjMz/far+wbwISt8O4c73Fat0B9Gfm79pz2APoV8auhHI3Hp936pYmjGcYF3XVrYUb+OQHegWZP69P5/d6GGa8Fb5rcfRJp+Bh9TrjXcG+vr6AnAiex+AnWGY5xpiLq5JIJPKnmeke7oXQm9JjtWLadEq/zqMklUp5Mb90kdzksAhaSJi03uslUMeizVZiyRfmX+uHirEv0W6cV/qcyxk4BL3e9YedOhMfX93udTA4OTlZ8HDVmc7wkwozP5to745AY/5tMdLNZ29HL/6fJxcZ9HtbejOZjKq7rb0zb4YO5BRs8snyAdVJQWvQ+2AunOMt6ir2T/SUoCoCxrwB9nYA9da+SHq4UHtpgCHj06SHDzsx23/omGN/5Is6dDYeJNPXDVk+I5CY7T+0QHTiRq6SUJ0SVcvyZbe8hXh6i8b/FmAAat7XYReSgx0AAAAASUVORK5CYII=" -->
		<!-- 							width="26" /></a> -->
		<div style="float: right">

			<input type="submit" value="등록" align="left"
				onClick="window.open('register.jsp','win','width=613','height=640')">

		</div>
	</div>
	<script
		src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

	<script src="index.js"></script>
</body>
</html>