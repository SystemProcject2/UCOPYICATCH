package kumoh.d445.ucopyicatch.process;

import kumoh.d445.ucopyicatch.analysis.KoreanAnalysis;
import kumoh.d445.ucopyicatch.analysis.TFIDF;
import kumoh.d445.ucopyicatch.bookreport.BookReportData;
import kumoh.d445.ucopyicatch.bookreport.BookReportItemData;
import kumoh.d445.ucopyicatch.check.CopyCheck;
import kumoh.d445.ucopyicatch.check.TermSentence;
import kumoh.d445.ucopyicatch.database.BookReportDAO;
import kumoh.d445.ucopyicatch.daumdataminig.book.BookFile;

public class MainProcessor {

	public static void main(String[] args) {
		double eRate=0.6;
		double cRate=0.4;
		double tRate=0.2;
		
		KoreanAnalysis ka = new KoreanAnalysis();
		BookReportDAO dao = new BookReportDAO();
		
		BookReportItemData readbook = BookFile.loadReport("10001_백범일지_20101008_이수인");	//이 내용을 디비에 저장된 내용을 불러오는 것으로 바꿔야함
		
/*		
		TFIDF.accureDF(dao.selectBookNounDf(readbook.getBookcode()));	//해당 북코드의 df값 읽기
		BookReportData data = dao.getBook(readbook.getBookcode());
		//read 대상 독후감
		BookReportItemData item = ka.divideBookReport(readbook.getBookcode(), readbook.getTitle(), readbook.getLink(), readbook.getContent());
		data.getItems().add(0, item);
		//df값 적용
		data.setDF(TFIDF.getDfList());
		data.setTfIdf();

		//명사(용언포함) tf-idf 검사
		data.setBoolean();
		//data.setFrequency();
		TermSentence ts = new TermSentence(data,1);
		CopyCheck cc = new CopyCheck();
		//cc.excute(ts, cRate, eRate);
		cc.excute(ts, tRate ,cRate, eRate);
*/		
		//어절 검사	 boolean 검사	
		BookReportData data2 = dao.getSeparate(readbook.getBookcode());
		BookReportItemData item2 = ka.divideSeparate(readbook.getBookcode(), readbook.getTitle(), readbook.getLink(), readbook.getContent());
		data2.getItems().add(0, item2);		
		TermSentence bts2 = new TermSentence(data2,2);
		CopyCheck cc = new CopyCheck();
//		cc.excute(bts2, cRate, eRate);
		cc.excute(bts2, tRate ,cRate, eRate);
	}
}
