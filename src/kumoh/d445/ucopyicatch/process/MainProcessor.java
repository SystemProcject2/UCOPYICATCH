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
	private final static double eRate=0.6;
	private final static double cRate=0.4;
	private final static double tRate=0.2;

	public static void excute(int reportid) {
		//실행하기 전 하고 있다는 것을 업데이트
		BookReportDAO dao = new BookReportDAO();
		dao.updateReport(reportid);
		
		KoreanAnalysis ka = new KoreanAnalysis();
		
		BookReportItemData readbook = dao.loadReport(reportid);
		
		//어절 검사	 boolean 검사	
		BookReportData data = dao.getSeparate(readbook.getBookcode());
		BookReportItemData item = ka.divideSeparate(readbook.getBookcode(), readbook.getTitle(), readbook.getLink(), readbook.getContent());
		data.getItems().add(0, item);		
		TermSentence bts = new TermSentence(data,2,false);
		CopyCheck cc = new CopyCheck();
		cc.excute(bts, tRate ,cRate, eRate);
		double copyrate = (double)cc.getCosinevalue().getResult().size()/item.getSentence().size() *100;
		//빠른 검사 저장
		dao.insertCopySentence(reportid, cc.getCosinevalue(),false);
		dao.updateReport(reportid, copyrate);
		System.out.println("완료");
	}
	
	public static void excuteLSA(int reportid) {
		BookReportDAO dao = new BookReportDAO();
		dao.updateReport(reportid);
		
		KoreanAnalysis ka = new KoreanAnalysis();
		
		BookReportItemData readbook = dao.loadReport(reportid);
		
		//어절 검사	 boolean 검사	
		BookReportData data = dao.getSeparate(readbook.getBookcode());
		BookReportItemData item = ka.divideSeparate(readbook.getBookcode(), readbook.getTitle(), readbook.getLink(), readbook.getContent());
		data.getItems().add(0, item);		
		TermSentence bts = new TermSentence(data,2,true);
		CopyCheck cc = new CopyCheck();
		cc.excuteLSA(bts, tRate ,cRate, eRate);
		double copyrate = (double)cc.getLSAvalue().getResult().size()/item.getSentence().size() *100;
		//정밀 검사 저장
		dao.insertCopySentence(reportid, cc.getLSAvalue(),true);
		dao.updateReport(reportid, copyrate);
		System.out.println("완료");
	}
	
	public static void main(String[] args) {
		
		KoreanAnalysis ka = new KoreanAnalysis();
		BookReportDAO dao = new BookReportDAO();
		
		BookReportItemData readbook = BookFile.loadReport("10001_백범일지_20101008_이수인");	//이 내용을 디비에 저장된 내용을 불러오는 것으로 바꿔야함
		
		//어절 검사	 boolean 검사	
		BookReportData data2 = dao.getSeparate(readbook.getBookcode());
		BookReportItemData item2 = ka.divideSeparate(readbook.getBookcode(), readbook.getTitle(), readbook.getLink(), readbook.getContent());
		data2.getItems().add(0, item2);		
		TermSentence bts2 = new TermSentence(data2,2,false);
		CopyCheck cc = new CopyCheck();
		cc.excute(bts2, tRate ,cRate, eRate);
	}
}
