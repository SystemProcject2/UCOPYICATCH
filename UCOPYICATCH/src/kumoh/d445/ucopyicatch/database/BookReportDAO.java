package kumoh.d445.ucopyicatch.database;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import kumoh.d445.ucopyicatch.analysis.TFIDF;
import kumoh.d445.ucopyicatch.bookreport.BookReportData;
import kumoh.d445.ucopyicatch.bookreport.BookReportItemData;
import kumoh.d445.ucopyicatch.bookreport.Word;

public class BookReportDAO {
	private static final String JDBC_DRIVER = "org.gjt.mm.mysql.Driver";
	private static final String JDBC_URL = "jdbc:mysql://202.31.202.199:3306/ucopyicatch";
	private static final String USER = "root";
	private static final String PASSWD = "kle445";
	
	private Connection con = null;
	
	public BookReportDAO() {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public boolean connect() {
		try {
			con=DriverManager.getConnection(JDBC_URL,USER,PASSWD);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean disconnect() {
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
	
	public BookReportData getBook(int code) {
		connect();
		BookReportData data = new BookReportData();
		Statement stmt = null;
		try {
			String bookSql = "select * from book where code='"+code+"';";
			stmt = con.createStatement();
			ResultSet BookRs = stmt.executeQuery(bookSql);
			
			while(BookRs.next()) {
				BookReportItemData item = setBookReportItemData(BookRs);
				data.getItems().add(item);
			}
			BookRs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return data;
	}
	
	private BookReportItemData setBookReportItemData(ResultSet rs) {
		BookReportItemData item = new BookReportItemData();
		Statement stmt = null;
		try {
			int bookid = rs.getInt(0);
			int bookCode = rs.getInt(1);
			String title = rs.getString(2);
			String link = rs.getString(3);
			String text = rs.getString(4);
			//sentence 다시 검색
			String sentenceSql = "select * from booksentence where bookid='"+bookid+"';";
			stmt = con.createStatement();
			ResultSet sentenceRs = stmt.executeQuery(sentenceSql);
			while(sentenceRs.next()) {
				item.setBookcode(bookCode);
				item.setTitle(title);
				item.setContent(text);
				item.setLink(link);
				item.getSentence().add(sentenceRs.getString(2));
				item.getContents().add(setWord(sentenceRs,bookCode));
			}
			sentenceRs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}
	
	private ArrayList<Word> setWord(ResultSet rs, int bookcode) {
		ArrayList<Word> word = new ArrayList<Word>();
		Statement stmt = null;
		try {
			int sentenceid = rs.getInt(0);
			int bookid = rs.getInt(1);
			//word 다시 검색
			String wordSql = "select * from booknountf where bookid='"+bookid+"' and sentenceid='"+sentenceid+"';";
			stmt = con.createStatement();
			ResultSet wordRs = stmt.executeQuery(wordSql);
			while(wordRs.next()) {
				Word item = new Word();
				item.setName(wordRs.getString(2));
				item.setTF(wordRs.getInt(4));
				item.setDF(selectBookNounDf(bookcode,wordRs.getString(2)).getNoundf());
				item.setTotal(selectBookCount(bookcode));
				item.setTfidf(TFIDF.tfIdf(item.getTF(), item.getDF(), item.getTotal()));
				word.add(item);
			}
			wordRs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return word;
	}
	
	private int selectBookCount(int bookCode) {
		Statement stmt = null;
		int total=0;
		try {
			String totalSql = "select count(*) from book where code='"+bookCode+"';";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(totalSql);
			rs.next();
			total = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public boolean insertBook(int bookcode,String title, String link, String text) {
		connect();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			StringBuffer sql = new StringBuffer();
			sql.append("insert into Book('code','title','link','text') values(");
			sql.append("'"+bookcode+"',");
			sql.append("'"+title+"',");
			sql.append("'"+link+"',");
			sql.append("'"+text+"'");
			sql.append(");");
			stmt.executeUpdate(sql.toString());
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;
	}
	
	public boolean insertBookSentece(int bookid, String sentence) {
		connect();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			StringBuffer sql = new StringBuffer();
			sql.append("insert into BookSentence('bookid','sentence') values(");
			sql.append("'"+bookid+"',");
			sql.append("'"+sentence+"'");
			sql.append(");");
			stmt.executeUpdate(sql.toString());
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;
	}
	
	public boolean insertBookNounTf(int bookid, int sentenceid, String noun, int nounTF) {
		connect();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			StringBuffer sql = new StringBuffer();
			sql.append("insert into BookNounTf('bookid','sentenceid','noun','nounTF') values(");
			sql.append("'"+bookid+"',");
			sql.append("'"+sentenceid+"',");
			sql.append("'"+noun+"',");
			sql.append("'"+nounTF+"'");
			sql.append(");");
			stmt.executeUpdate(sql.toString());
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;
	}
	
	public boolean insertBookNounDf(int noundf, String noun, int bookcode) {
		connect();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			StringBuffer sql = new StringBuffer();
			sql.append("insert into BookNounDf('noundf','noun','code') values(");
			sql.append("'"+noundf+"',");
			sql.append("'"+noun+"',");
			sql.append("'"+bookcode+"'");
			sql.append(");");
			stmt.executeUpdate(sql.toString());
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;
	}
	
	public ArrayList<Book> selectBook(int bookcode) {
		connect();
		ArrayList<Book> bookData = new ArrayList<Book>();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "select * from Book where code='"+bookcode+"';";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Book data = new Book();
				data.setBookid(rs.getInt(0));
				data.setBookcode(rs.getInt(1));
				data.setTitle(rs.getString(2));
				data.setLink(rs.getString(3));
				data.setText(rs.getString(4));
				bookData.add(data);
			}
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return bookData;
	}
	
	public ArrayList<BookSentence> selectBookSentence(int bookid) {
		connect();
		ArrayList<BookSentence> bookSentence = new ArrayList<BookSentence>();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "select * from BookSentence where bookid='"+bookid+"';";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				BookSentence data = new BookSentence();
				data.setSentenceid(rs.getInt(0));
				data.setBookid(rs.getInt(1));
				data.setSentence(rs.getString(2));
				bookSentence.add(data);
			}
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return bookSentence;
	}
	
	public ArrayList<BookNounTf> selectBookNounTf(int bookid, int sentenceid) {
		connect();
		ArrayList<BookNounTf> bookNounTf = new ArrayList<BookNounTf>();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "select * from bookNounTf where bookid='"+bookid+"'and sentenceid='"+sentenceid+"';";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				BookNounTf data = new BookNounTf();
				data.setBookid(rs.getInt(0));
				data.setSentenceid(rs.getInt(1));
				data.setNoun(rs.getString(2));
				data.setNounTF(rs.getInt(3));
				bookNounTf.add(data);
			}
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return bookNounTf;
	}
	
	public BookNounDf selectBookNounDf(int bookcode, String noun) {
		connect();
		BookNounDf bookNounDf = new BookNounDf();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "select * from bookNounDf where code='"+bookcode+"'and noun='"+noun+"';";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			bookNounDf.setNounid(rs.getInt(0));
			bookNounDf.setNoundf(rs.getInt(1));
			bookNounDf.setNoun(rs.getString(2));
			bookNounDf.setBookcode(rs.getInt(3));
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return bookNounDf;
	}
}
