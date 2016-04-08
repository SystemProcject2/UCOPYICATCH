package kumoh.d445.ucopyicatch.database;


import java.sql.*;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import kumoh.d445.ucopyicatch.bookreport.BookReportData;
import kumoh.d445.ucopyicatch.bookreport.BookReportItemData;
import kumoh.d445.ucopyicatch.bookreport.Word;

public class BookReportDAO {
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private DataSource ds = null;
	
	public BookReportDAO() {
		try {
			InitialContext ctx = new InitialContext();
		    ds = (DataSource) ctx.lookup("java:comp/env/jdbc/mysql");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public boolean connect() {
		try {
			con=ds.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean disconnect() {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public BookReportData getBookReport(int code) {
		connect();
		BookReportData data = new BookReportData();
		
		try {
			String bookSql = "select * from book where code='"+code+"';";
			pstmt = con.prepareStatement(bookSql);
			ResultSet BookRs = pstmt.executeQuery();
			
			while(BookRs.next()) {
				BookReportItemData item = setBookReportItemData(BookRs);
				data.getItems().add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return data;
	}
	
	private BookReportItemData setBookReportItemData(ResultSet rs) {
		BookReportItemData item = new BookReportItemData();
		try {
			int bookid = rs.getInt(0);
			int bookCode = rs.getInt(1);
			String title = rs.getString(2);
			String link = rs.getString(3);
			String text = rs.getString(4);
			//sentence 다시 검색
			String sentenceSql = "select * from booksentence where bookid='"+bookid+"';";
			pstmt = con.prepareStatement(sentenceSql);
			ResultSet sentenceRs = pstmt.executeQuery();
			while(sentenceRs.next()) {
				item.setBookcode(bookCode);
				item.setTitle(title);
				item.setContent(text);
				item.setLink(link);
				item.getSentence().add(sentenceRs.getString(2));
				item.getContents().add(setWord(sentenceRs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}
	
	private ArrayList<Word> setWord(ResultSet rs) {
		ArrayList<Word> word = new ArrayList<Word>();
		try {
			int sentenceid = rs.getInt(0);
			int bookid = rs.getInt(1);
			//word 다시 검색
			String wordSql = "select * from booknountf where bookid='"+bookid+"' and sentenceid='"+sentenceid+"';";
			pstmt = con.prepareStatement(wordSql);
			ResultSet wordRs = pstmt.executeQuery();
			while(wordRs.next()) {
				Word item = new Word();
				item.setName(wordRs.getString(2));
				//item.setTfidf(tfidf); 값 넣어야됨
				word.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return word;
	}
}
