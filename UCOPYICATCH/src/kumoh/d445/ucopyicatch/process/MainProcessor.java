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
		// TODO Auto-generated method stub
		//1. 대상 독후감 받기
		//2. 대상 독후감 분석
		//2.1 df값 읽기
		//2.2 df값 조정accuredf, 대상 독후감 df값 넣기(tfidf도 계산해서 넣기)
		//3. 디비 읽기(대상 독후감과 디비 합치기)
		//4. 메트릭스 구성
		//5. 표절 검사(lsa부터 테스트)
		KoreanAnalysis ka = new KoreanAnalysis();
		BookReportDAO dao = new BookReportDAO();
		TFIDF.accureDF(dao.selectBookNounDf(10015));	//해당 북코드의 df값 읽기
		BookReportData data = dao.getBook(10015);
		
		//read 대상 독후감
		BookReportItemData readbook = BookFile.loadReport("10015_비교문서_20101008_이재승.txt");
		BookReportItemData item = ka.divideBookReport(readbook.getBookcode(), readbook.getTitle(), readbook.getLink(), readbook.getContent());
		data.getItems().add(0, item);
		
		//df값 적용
		data.setDF(TFIDF.getDfList());
		data.setTfIdf();
//		BookFile.writeReport(data,"디비 저장 정보.txt");

		TermSentence ts = new TermSentence(data);
		
//		CopyCheck cc = new CopyCheck();
//		cc.calcLSA(ts);
//		BookFile.writeBook(cc.getLSAvalue(), "LSA 결과값.txt");

		CopyCheck cc2 = new CopyCheck();
		cc2.calcCosine(ts);
		BookFile.writeBook(cc2.getLSAvalue(), "코사인 유사도 결과값.txt");
		String s = "사이버문화연구소의 김양은 소장은 “블로그는 뉴스의 연성화라는 흐름 속에 개인이 뉴스 생산자로 적극 나서고 있는 것”이라며 “대신 아직 신뢰성이 부족하기 때문에 보완이 필요하다”고 말했다.";
		String t = "사이버문화연구소의 김양은 소장은 “블로그는 뉴스의 연성화라는 흐름 속에 개인이 뉴스 생산자로 적극 나서고 있는 것”이라며 “대신 아직 신뢰성이 부족하기 때문에 보완이 필요하다”고 말했다.";
		System.out.println("Edit Distance 결과 : "+cc2.calcEditDistance(s, t));
	}

}
