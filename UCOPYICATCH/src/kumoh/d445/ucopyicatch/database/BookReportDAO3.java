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

public class BookReportDAO3 {
	private static final String JDBC_DRIVER = "org.gjt.mm.mysql.Driver";
	private static final String JDBC_URL = "jdbc:mysql://202.31.202.199:3306/ucopyicatch";
	private static final String USER = "root";
	private static final String PASSWD = "kle445";
	
	private Connection con = null;
	
	public BookReportDAO3() {
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
				item.setDF(selectDF(bookcode,wordRs.getString(2)));
				item.setTotal(selectBook(bookcode));
				item.calcTfidf();
				word.add(item);
			}
			wordRs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return word;
	}
	
	private int selectBook(int bookCode) {
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
	
	private int selectDF(int bookCode, String name) {
		Statement stmt = null;
		int df=0;
		try {
			String dfSql = "select * from booknoundf where code='"+bookCode+"' and noun='"+name+"';";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(dfSql);
			rs.next();
			df = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return df;
	}
	
	//삽입시 하나의 북코드에 대한 정보만 삽입(bookreportdata의 순서에 따라서 저장됨)
	public boolean insertBook(BookReportData data) {
		connect();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			//book테이블
			for(int i=0 ; i < data.getItems().size() ; i++) {
				BookReportItemData item = data.getItems().get(i);
				StringBuffer sql = new StringBuffer();
				sql.append("insert into book('code','title','link','text') values(");
				sql.append("'"+item.getBookcode()+"',");
				sql.append("'"+item.getTitle()+"',");
				sql.append("'"+item.getLink()+"',");
				sql.append("'"+item.getContent()+"'");
				sql.append(");");
				stmt.executeUpdate(sql.toString());
			}
			//bookid목록
			ArrayList<Integer> bookID = selectBookID(data.getItems().get(0).getBookcode());
			//sentence테이블
			for(int i=0 ; i < data.getItems().size() ; i++) {
				BookReportItemData item = data.getItems().get(i);
				for(int j=0 ; j < item.getSentence().size() ; j++) {
					StringBuffer sql = new StringBuffer();
					sql.append("insert into BookSentnece('bookid','sentence') values(");
					sql.append("'"+bookID.get(i)+"',");
					sql.append("'"+item.getSentence().get(j)+"'");
					sql.append(");");
					stmt.executeUpdate(sql.toString());
				}
			}
			
			//하나의 북코드의 해당하는 sentenceid와 bookid
			ArrayList<ReportID> reportID = selectSentenceID(bookID);
			HashMap<String,Integer> dfMap = new HashMap<String,Integer>();
			//tf df테이블
			for(int i=0 ; i < data.getItems().size() ; i++) {
				BookReportItemData item = data.getItems().get(i);
				for(int j=0 ; j < item.getSentence().size() ; j++) {
					ArrayList<Word> wordList = item.getContents().get(j);
					for(int k=0 ; k < wordList.size() ; k++) {
						StringBuffer sql = new StringBuffer();
						sql.append("insert into BookNounTf('bookid','sentenceid','noun','nounTF') values(");
						sql.append("'"+reportID.get(j).getBookid()+"',");
						sql.append("'"+reportID.get(j).getSentenceid()+"',");
						sql.append("'"+wordList.get(k).getName()+"',");
						sql.append("'"+wordList.get(k).getTF()+"'");
						sql.append(");");
						stmt.executeUpdate(sql.toString());
						
						dfMap.put(wordList.get(k).getName(), wordList.get(k).getDF());
					}
				}
			}
			
			//df테이블 삽입
			Set<Entry<String, Integer>> set = dfMap.entrySet();
			Iterator<Entry<String, Integer>> it = set.iterator();
			while (it.hasNext()) {	
				Map.Entry<String, Integer> e = (Map.Entry<String, Integer>)it.next();
				String noun = e.getKey();
				int a = e.getValue();
			}
			
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return true;
	}
	
	
	public ArrayList<Integer> selectBookID(int code) {
		ArrayList<Integer> bookID = new ArrayList<Integer>();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "select bookid from Book where code='"+code+"';";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				bookID.add(rs.getInt(0));
			}
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return bookID;
	}
	
	public ArrayList<ReportID> selectSentenceID(ArrayList<Integer> bookid) {
		ArrayList<ReportID> sentenceID = new ArrayList<ReportID>();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			for(int i=0 ; i < bookid.size() ; i++) {
				String sql = "select sentenceid, bookid from BookSentence where bookid='"+bookid.get(i)+"';";
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()) {
					ReportID id = new ReportID();
					id.setBookid(rs.getInt(1));
					id.setSentenceid(rs.getInt(0));
					sentenceID.add(id);
				}
				rs.close();
			}
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return sentenceID;
	}
}
