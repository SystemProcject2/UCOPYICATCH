package kumoh.d445.ucopyicatch.bookreport;

import java.util.ArrayList;

public class BookReportItemData {

	private ArrayList<ArrayList<Word>> contents;
	private ArrayList<String> sentence;
	private ArrayList<ArrayList<String>> nouns;
	private String link;
	private String title;
	private String content;
	private int bookcode;

	public ArrayList<ArrayList<String>> getNouns() {
		return nouns;
	}

	public void setNouns(ArrayList<ArrayList<String>> nouns) {
		this.nouns = nouns;
	}

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
		return contents;
	}
	public void setContents(ArrayList<ArrayList<Word>> contents) {
		this.contents = contents;
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
