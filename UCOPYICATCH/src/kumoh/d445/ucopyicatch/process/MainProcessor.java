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
		KoreanAnalysis ka = new KoreanAnalysis();
		BookReportDAO dao = new BookReportDAO();
		
		BookReportItemData readbook = BookFile.loadReport("10067_모리와 함께 한 화요일_20101008_이재승.txt");
		
		TFIDF.accureDF(dao.selectBookNounDf(readbook.getBookcode()));	//해당 북코드의 df값 읽기
		///BookReportData data = dao.getBook(readbook.getBookcode());
		BookReportData data = dao.getSeperate(readbook.getBookcode());
		
		//read 대상 독후감
		BookReportItemData item = ka.divideBookReport(readbook.getBookcode(), readbook.getTitle(), readbook.getLink(), readbook.getContent());
		data.getItems().add(0, item);
		
		//df값 적용
//		data.setDF(TFIDF.getDfList());
//		data.setTfIdf();
/*
		TermSentence ts = new TermSentence(data,1);
		
		CopyCheck cc = new CopyCheck();
		cc.calcLSA(ts);
		BookFile.writeBook(cc.getLSAvalue(), "TFIDF LSA 결과값.txt");

		CopyCheck cc2 = new CopyCheck();
		cc2.calcCosine(ts);
		BookFile.writeBook(cc2.getLSAvalue(), "TFIDF 코사인 유사도 결과값.txt");
		
		
		data.setFrequency();
		TermSentence fts = new TermSentence(data,1);
		CopyCheck cc3 = new CopyCheck();
		cc3.calcCosine(fts);
		BookFile.writeBook(cc3.getLSAvalue(), "빈도수 LSA 유사도 결과값.txt");
		
		CopyCheck cc4 = new CopyCheck();
		cc3.calcCosine(fts);
		BookFile.writeBook(cc4.getLSAvalue(), "빈도수 코사인 유사도 결과값.txt");
		
		data.setBoolean();
		TermSentence bts = new TermSentence(data,1);
		CopyCheck cc5 = new CopyCheck();
		cc5.calcCosine(bts);
		BookFile.writeBook(cc5.getLSAvalue(), "불린 LSA 유사도 결과값.txt");
		
		CopyCheck cc6 = new CopyCheck();
		cc6.calcCosine(bts);
		BookFile.writeBook(cc6.getLSAvalue(), "불린 코사인 유사도 결과값.txt");
		*/
		
		data.setBoolean();
		TermSentence bts = new TermSentence(data,2);
		CopyCheck cc7 = new CopyCheck();
		cc7.calcCosine(bts);
		BookFile.writeBook(cc7.getLSAvalue(), "어절 LSA 유사도 결과값.txt");
		
		CopyCheck cc8 = new CopyCheck();
		cc8.calcCosine(bts);
		BookFile.writeBook(cc8.getLSAvalue(), "어절 코사인 유사도 결과값.txt");
		
	}

}
