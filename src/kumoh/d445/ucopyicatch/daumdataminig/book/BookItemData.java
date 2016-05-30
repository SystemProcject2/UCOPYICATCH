package kumoh.d445.ucopyicatch.daumdataminig.book;

public class BookItemData {
	private String title;
	private String bookCode;
	
	public BookItemData(){}
	
	public BookItemData(String bookCode, String title) {
		this.title = title;
		this.bookCode = bookCode;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getBookCode() {
		return bookCode;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setBookCode(String bookCode) {
		this.bookCode = bookCode;
	}
}
