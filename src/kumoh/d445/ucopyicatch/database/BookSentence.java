package kumoh.d445.ucopyicatch.database;

public class BookSentence {
	private int sentenceid;
	private int bookid;
	private String sentence;
	
	public int getSentenceid() {
		return sentenceid;
	}
	
	public void setSentenceid(int sentenceid) {
		this.sentenceid = sentenceid;
	}
	
	public int getBookid() {
		return bookid;
	}
	
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	
	public String getSentence() {
		return sentence;
	}
	
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
}
