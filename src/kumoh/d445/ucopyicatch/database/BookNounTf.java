package kumoh.d445.ucopyicatch.database;

public class BookNounTf {
	private int bookid;
	private int sentenceid;
	private int nounid;
	private String noun;
	private int nounTF;
	
	public int getBookid() {
		return bookid;
	}
	
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	
	public int getSentenceid() {
		return sentenceid;
	}
	
	public void setSentenceid(int sentenceid) {
		this.sentenceid = sentenceid;
	}
	
	public int getNounid() {
		return nounid;
	}
	
	public void setNounid(int nounid) {
		this.nounid = nounid;
	}
	
	public String getNoun() {
		return noun;
	}
	
	public void setNoun(String noun) {
		this.noun = noun;
	}
	
	public int getNounTF() {
		return nounTF;
	}
	
	public void setNounTF(int nounTF) {
		this.nounTF = nounTF;
	}
}
