package kumoh.d445.ucopyicatch.database;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import kumoh.d445.ucopyicatch.analysis.TFIDF;
import kumoh.d445.ucopyicatch.bookreport.BookReportData;
import kumoh.d445.ucopyicatch.bookreport.BookReportItemData;
import kumoh.d445.ucopyicatch.bookreport.Tag;
import kumoh.d445.ucopyicatch.bookreport.Word;

import java.util.Map.Entry;


public class BookReportDAO {
	private static final String JDBC_DRIVER = "org.gjt.mm.mysql.Driver";
	private static final String JDBC_URL = "jdbc:mysql://202.31.202.199:3306/ucopyicatch6";
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
	
	public void insertBookReportData(BookReportData brd)
	{
		connect();
		
		//모든 책 삽입
		for(int i=0; i< brd.getItems().size(); i++)
		{
//			System.out.println(brd.getItems().get(i).getLink());
			insertBook(brd.getItems().get(i).getBookcode(), brd.getItems().get(i).getTitle(),brd.getItems().get(i).getLink(), brd.getItems().get(i).getContent());
		}
		//sentence 삽입
		ArrayList<Book> bookList = selectBook(brd.getItems().get(0).getBookcode());
		for(int i=0; i< bookList.size(); i++)
		{
			Book book = bookList.get(i);
			ArrayList<String> sentenceList = brd.getItems().get(i).getSentence();
			
			for(int j=0; j<sentenceList.size(); j++)
			{
		//		System.out.println(sentenceList.get(j));
				insertBookSentece(book.getBookid(), sentenceList.get(j)); 
			}
		}
		//Noun 삽입
		for(int i=0; i<bookList.size(); i++)
		{
			Book book = bookList.get(i);
			ArrayList<BookSentence> sentenceList = selectBookSentence(book.getBookid());
			for(int j=0; j< sentenceList.size(); j++)
			{
				BookSentence bs = sentenceList.get(j);
				ArrayList<Word> nouns = brd.getItems().get(i).getContents().get(j);
				
				for(int k=0; k<nouns.size(); k++)
				{
					insertBookNounTf(book.getBookid(), bs.getSentenceid(), nouns.get(k).getName(),nouns.get(k).getTF());
				}
			}
		}
		
		Set<Entry<String, Integer>> set = TFIDF.getDfList().entrySet();
		Iterator<Entry<String, Integer>> it = set.iterator();
		
		 while(it.hasNext())
		 {
			 Map.Entry<String, Integer> e = (Map.Entry<String, Integer>)it.next();
			 insertBookNounDf(e.getValue(), e.getKey(),brd.getItems().get(0).getBookcode());
		 }
		
		disconnect();

	}
	
	public BookReportData getSeparate(int code) {
		connect();
		BookReportData data = new BookReportData();
		Statement stmt = null;
		try {
			String bookSql = "select * from book where code='"+code+"' order by bookid asc;";
			stmt = con.createStatement();
			ResultSet BookRs = stmt.executeQuery(bookSql);
			while(BookRs.next()) {
				BookReportItemData item = setSeparate(BookRs);
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
	
	public BookReportItemData setSeparate(ResultSet rs) {
		BookReportItemData item = new BookReportItemData();
		Statement stmt = null;
		try {
			int bookid = rs.getInt("bookid");
			int bookCode = rs.getInt("code");
			String title = rs.getString("title");
			String link = rs.getString("link");
			String text = rs.getString("text");
			//sentence 다시 검색
			String sentenceSql = "select sentenceid,sentence from booksentence where bookid='"+bookid+"' order by sentenceid asc;";
			
			stmt = con.createStatement();
			ResultSet sentenceRs = stmt.executeQuery(sentenceSql);
			ArrayList<Integer> temp = new ArrayList<Integer>();
			while(sentenceRs.next()) {
				item.setBookcode(bookCode);
				item.setTitle(title);
				item.setContent(text);
				item.setLink(link);
				item.getSentence().add(sentenceRs.getString("sentence"));
				temp.add(sentenceRs.getInt("sentenceid"));
			}
			sentenceRs.close();
			for(int i =0 ; i < temp.size() ; i++) {
				String joinSql = "select sentenceid, separate from separate where sentenceid='"+temp.get(i)+"';";
				ResultSet joinRs = stmt.executeQuery(joinSql);
				ArrayList<String> list = new ArrayList<String>();
				while(joinRs.next()) {
					list.add(joinRs.getString("separate"));
				}
				item.getSeparateWord().add(list);
				joinRs.close();
			}
			for(int i =0 ; i < temp.size() ; i++) {
				String joinSql = "select * from tag where sentenceid='"+temp.get(i)+"';";
				ResultSet joinRs = stmt.executeQuery(joinSql);
				ArrayList<Tag> tlist = new ArrayList<Tag>();
				while(joinRs.next()) {
					tlist.add(new Tag(joinRs.getString("tagName"),joinRs.getInt("tagCnt")));
				}
				item.getTagWord().add(tlist);
				joinRs.close();
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
		
	}
	
	public BookReportData getBook(int code) {
		connect();
		BookReportData data = new BookReportData();
		Statement stmt = null;
		try {
			String bookSql = "select * from book where code='"+code+"' order by bookid asc;";
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
			int bookid = rs.getInt("bookid");
			int bookCode = rs.getInt("code");
			String title = rs.getString("title");
			String link = rs.getString("link");
			String text = rs.getString("text");
			//sentence 다시 검색
			String sentenceSql = "select sentenceid,sentence from booksentence where bookid='"+bookid+"' order by sentenceid asc;";
			
			stmt = con.createStatement();
			ResultSet sentenceRs = stmt.executeQuery(sentenceSql);
			ArrayList<Integer> temp = new ArrayList<Integer>();
			while(sentenceRs.next()) {
				item.setBookcode(bookCode);
				item.setTitle(title);
				item.setContent(text);
				item.setLink(link);
				item.getSentence().add(sentenceRs.getString("sentence"));
				temp.add(sentenceRs.getInt("sentenceid"));
			}
			sentenceRs.close();
			for(int i =0 ; i < temp.size() ; i++) {
				String joinSql = "select sentenceid, nounid, noun, nountf from booknountf where sentenceid='"+temp.get(i)+"';";
				ResultSet joinRs = stmt.executeQuery(joinSql);
				ArrayList<Word> wlist = new ArrayList<Word>();
				while(joinRs.next()) {
					wlist.add(new Word(joinRs.getString("noun"),joinRs.getInt("nountf")));
					//System.out.println(joinRs.getString("noun"));
				}
				//System.out.println(item.getSentence().get(i));
				item.getContents().add(wlist);
				joinRs.close();
			}
			for(int i =0 ; i < temp.size() ; i++) {
				String joinSql = "select * from tag where sentenceid='"+temp.get(i)+"';";
				ResultSet joinRs = stmt.executeQuery(joinSql);
				ArrayList<Tag> tlist = new ArrayList<Tag>();
				while(joinRs.next()) {
					tlist.add(new Tag(joinRs.getString("tagName"),joinRs.getInt("tagCnt")));
				}
				item.getTagWord().add(tlist);
				joinRs.close();
			}
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
			int sentenceid = rs.getInt("sentenceid");
			int bookid = rs.getInt("bookid");
			//word 다시 검색
			String wordSql = "select * from booknountf where bookid='"+bookid+"' and sentenceid='"+sentenceid+"';";
			stmt = con.createStatement();
			ResultSet wordRs = stmt.executeQuery(wordSql);
			int count =0;
			while(wordRs.next()) {
				//System.out.println("word : "+count++);
				Word item = new Word();
				item.setName(wordRs.getString("noun"));
				item.setTF(wordRs.getInt("nounTF"));
				item.setTotal(selectBookCount(bookcode));
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
			total = rs.getInt("count(*)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
/*	public boolean insertBookList(BookReportData bookreport) {
		connect();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			ArrayList<BookReportItemData> bookreportList = bookreport.getItems();
			for(int i=0; i< bookreportList.size(); i++)
			{
				
				BookReportItemData brid = bookreportList.get(i);
				StringBuffer sql = new StringBuffer();
				sql.append("insert into Book(code,title,link,text) values(");
				sql.append("'"+brid.getBookcode()+"',");
				sql.append("'"+brid.getTitle()+"',");
				sql.append("'"+brid.getLink()+"',");
				sql.append("'"+brid.getContent().replace("\'", "\''").replace("\"", "\\\"") + "'");
				sql.append(");");
				System.out.println(sql);
				stmt.executeUpdate(sql.toString());
			}
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;
	}
	*/
	
	public boolean insertBook(int bookcode,String title, String link, String text) {
	//	connect();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			StringBuffer sql = new StringBuffer();
			sql.append("insert into Book(code,title,link,text) values(");
			sql.append("'"+bookcode+"',");
			sql.append("'"+title+"',");
			sql.append("'"+link+"',");
			sql.append("'"+text.replace("\'", "\''").replace("\"", "\\\"")+"'");
			sql.append(");");
			
			stmt.executeUpdate(sql.toString());
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
	//		disconnect();
		}
		return true;
	}
	
	public boolean insertBookSentece(int bookid, String sentence) {
	//	connect();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			StringBuffer sql = new StringBuffer();
			sql.append("insert into BookSentence(bookid,sentence) values(");
			sql.append("'"+bookid+"',");
			sql.append("'"+sentence.replace("\'", "\''").replace("\"", "\\\"")+"'");
			sql.append(");");
			stmt.executeUpdate(sql.toString());
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
		//	disconnect();
		}
		return true;
	}
	
	public boolean insertBookNounTf(int bookid, int sentenceid, String noun, int nounTF) {
	//	connect();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			StringBuffer sql = new StringBuffer();
			sql.append("insert into BookNounTf(bookid,sentenceid, noun,nounTF) values(");
			sql.append("'"+bookid+"',");
			sql.append("'"+sentenceid+"',");
			sql.append("'"+noun.replace("\'", "\''").replace("\"", "\\\"")+"',");
			sql.append("'"+nounTF+"'");
			sql.append(");");
			stmt.executeUpdate(sql.toString());
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
	//		disconnect();
		}
		return true;
	}
	
	public boolean insertBookNounDf(int noundf, String noun, int bookcode) {
	//	connect();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			StringBuffer sql = new StringBuffer();
			sql.append("insert into BookNounDf(noundf,noun,code) values(");
			sql.append("'"+noundf+"',");
			sql.append("'"+noun.replace("\'", "\''").replace("\"", "\\\"")+"',");
			sql.append("'"+bookcode+"'");
			sql.append(");");
			stmt.executeUpdate(sql.toString());
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
//			disconnect();
		}
		return true;
	}
	
	public ArrayList<Book> selectBook(int bookcode) {
	//	connect();
		ArrayList<Book> bookData = new ArrayList<Book>();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "select * from Book where code='"+bookcode+"';";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Book data = new Book();
				data.setBookid(rs.getInt("bookid"));
				data.setBookcode(rs.getInt("code"));
				data.setTitle(rs.getString("title"));
				data.setLink(rs.getString("link"));
				data.setText(rs.getString("text"));
				bookData.add(data);
			}
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
	//		disconnect();
		}
		return bookData;
	}
	
	public ArrayList<BookSentence> selectBookSentence(int bookid) {
	//	connect();
		ArrayList<BookSentence> bookSentence = new ArrayList<BookSentence>();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "select * from BookSentence where bookid='"+bookid+"';";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				BookSentence data = new BookSentence();
				data.setSentenceid(rs.getInt("sentenceid"));
				data.setBookid(rs.getInt("bookid"));
				data.setSentence(rs.getString("sentence"));
				bookSentence.add(data);
			}
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
	//		disconnect();
		}
		return bookSentence;
	}
	
	public ArrayList<BookNounTf> selectBookNounTf(int bookid, int sentenceid) {
	//	connect();
		ArrayList<BookNounTf> bookNounTf = new ArrayList<BookNounTf>();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "select * from bookNounTf where bookid='"+bookid+"'and sentenceid='"+sentenceid+"';";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				BookNounTf data = new BookNounTf();
				data.setBookid(rs.getInt("bookid"));
				data.setSentenceid(rs.getInt("sentenceid"));
				data.setNoun(rs.getString("noun"));
				data.setNounTF(rs.getInt("nounTF"));
				bookNounTf.add(data);
			}
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
	//		disconnect();
		}
		return bookNounTf;
	}
	
	public BookNounDf selectBookNounDf(int bookcode, String noun) {
	//	connect();
		BookNounDf bookNounDf = new BookNounDf();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "select * from bookNounDf where code='"+bookcode+"'and noun='"+noun+"';";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			bookNounDf.setNounid(rs.getInt("nounid"));
			bookNounDf.setNoundf(rs.getInt("noundf"));
			bookNounDf.setNoun(rs.getString("noun"));
			bookNounDf.setBookcode(rs.getInt("code"));
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
	//		disconnect();
		}
		return bookNounDf;
	}
	
	public HashMap<String, Integer> selectBookNounDf(int bookcode) {
		connect();
		HashMap<String, Integer> list = new HashMap<String, Integer>();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "select * from bookNounDf where code='"+bookcode+"';";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list.put(rs.getString("noun"), rs.getInt("noundf"));
			}
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
}
