package kumoh.d445.ucopyicatch.process;

import java.util.*;
import java.util.Map.Entry;

import kumoh.d445.ucopyicatch.analysis.KoreanAnalysis;
import kumoh.d445.ucopyicatch.bookreport.BookReportData;
import kumoh.d445.ucopyicatch.bookreport.BookReportItemData;
import kumoh.d445.ucopyicatch.daumdataminig.book.BookFile;
import kumoh.d445.ucopyicatch.daumdataminig.xml.XMLReader;

public class PreProcessor {
	public static void main(String[] args)
	{
		//1. 내용을 가져온다
		//2. 문장분석
		//3. 디비 저장
		KoreanAnalysis ka = new KoreanAnalysis();
	      BookFile bf = new BookFile();
	      XMLReader reader = new XMLReader();
	      
	      //책 목록과 책코드 읽어서 가져오기
	   //   HashMap<String, Integer> map = bf.readBookFile("C:/Users/dltnd/Desktop/booklist.csv");
	      
	  //    Set<Entry<String, Integer>> set = map.entrySet();
	   //   Iterator<Entry<String, Integer>> it = set.iterator();
	      ArrayList<BookReportItemData> data = new ArrayList<BookReportItemData>();
	      
	      //북코드 한개당 xml 여러개 일기
	   
	     //    Map.Entry<String, Integer> e = (Map.Entry<String, Integer>)it.next();
	        
	      	data = reader.readXML(10001,"백범일지");
//	         dao.insertBook(data.get(0).getBookcode(), data.get(0).getTitle(), data.get(0).getLink(), data.get(0).getContent());
	   //      ArrayList<Book> bookList = dao.selectBook(e.getValue());
	         
	   //      BookReportData brd = ka.divideBookReport(e.getKey(), e.getValue());
	   //      ka.calculateTFDF(brd);
	      
	   /*   BookReportData data = new ();
	      Korean
	      
	      xml로 파일하니씩 가져온다
	      for() {
	         data.getItems().add(arg0)
	      }
	      
	      dao
	*/

	}
}
