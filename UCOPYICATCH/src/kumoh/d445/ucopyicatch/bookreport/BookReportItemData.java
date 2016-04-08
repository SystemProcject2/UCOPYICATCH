package kumoh.d445.ucopyicatch.bookreport;

import java.util.ArrayList;

public class BookReportItemData {

	private ArrayList<ArrayList<Word>> noun = new ArrayList<ArrayList<Word>>();
	private ArrayList<String> sentence = new ArrayList<String>();
	private String link;
	private String title;
	private String content;
	private int bookcode;

	public int getBookcode() {
		return bookcode;
	}

	public void setBookcode(int bookcode) {
		this.bookcode = bookcode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ArrayList<ArrayList<Word>> getContents() {
		return noun;
	}
	public void setContents(ArrayList<ArrayList<Word>> noun) {
		this.noun = noun;
	}
	public ArrayList<String> getSentence() {
		return sentence;
	}
	public void setSentence(ArrayList<String> sentence) {
		this.sentence = sentence;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


}
