package kumoh.d445.ucopyicatch.process;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kumoh.d445.ucopyicatch.analysis.KoreanAnalysis;
import kumoh.d445.ucopyicatch.database.BookReportDAO;
import kumoh.d445.ucopyicatch.daumdataminig.book.BookFile;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class registerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookReportDAO dao = new BookReportDAO();
	private KoreanAnalysis ka = new KoreanAnalysis();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public registerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		 
		    int postMaxSize = 10 * 1024 * 1024;
		    
		    String folderPath = "C:/Users/user/Documents/GitHub/UCOPYICATCH/UCOPYICATCH/down"; // 파일이 저장될 경로
		    String encoding = "UTF-8";
		 
		    MultipartRequest mRequest = new MultipartRequest(request, folderPath, postMaxSize, encoding, new DefaultFileRenamePolicy());
		    
		    int studentNum = Integer.parseInt(mRequest.getParameter("studentNumber"));
		    String name = mRequest.getParameter("name");
		    int code = Integer.parseInt( mRequest.getParameter("code"));
		    String title = mRequest.getParameter("title");
		    int type = Integer.parseInt(mRequest.getParameter("type"));

		    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		    String date =  formatter.format(new Date());
		    
		    File file = mRequest.getFile("uploadFile");
		    String text = "";
		    if(file == null)
		    {
		    	text = mRequest.getParameter("text");
		    }
		    else
		    {
		    	 System.out.println("file : " + file.getName());
		    	 System.out.println("filePath : " + file.getPath());
				   
		    	 text = BookFile.readTxtFile(file.getPath());
		    }
		    
		    dao.insertReport(studentNum, date, name, title, code, text,type);
		    int reportId = dao.selectReport(studentNum, date, name, title, code, text);
		    
		    ArrayList<String> sentenceList = ka.divideSentence(text);
		    for(int i=0; i< sentenceList.size(); i++)
		    {
		    	String str = sentenceList.get(i);
		    	dao.insertReportSentence(reportId, str);
		    }
		    
		    if(type==1) {
		    	MainProcessor.excute(reportId);
		    }
		    else if(type==2) {
		    	MainProcessor.excuteLSA(reportId);
		    }
		    
	}
}
