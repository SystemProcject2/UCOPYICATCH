package kumoh.d445.ucopyicatch.database;

public class Book {
	private int bookid;
	private int bookcode;
	private String title;
	private String link;
	private String text;
	
	public int getBookid() {
		return bookid;
	}
	
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	
	public int getBookcode() {
		return bookcode;
	}
	
	public void setBookcode(int bookcode) {
		this.bookcode = bookcode;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
}
