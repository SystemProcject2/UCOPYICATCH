package kumoh.d445.ucopyicatch.database;

public class BookNounDf {
	private int nounid;
	private int noundf;
	private String noun;
	private int bookcode;
	
	public int getNounid() {
		return nounid;
	}
	
	public void setNounid(int nounid) {
		this.nounid = nounid;
	}
	
	public int getNoundf() {
		return noundf;
	}
	
	public void setNoundf(int noundf) {
		this.noundf = noundf;
	}
	
	public String getNoun() {
		return noun;
	}
	
	public void setNoun(String noun) {
		this.noun = noun;
	}
	
	public int getBookcode() {
		return bookcode;
	}
	
	public void setBookcode(int bookcode) {
		this.bookcode = bookcode;
	}
}
