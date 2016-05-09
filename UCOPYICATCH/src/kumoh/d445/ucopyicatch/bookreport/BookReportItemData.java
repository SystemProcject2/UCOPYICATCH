package kumoh.d445.ucopyicatch.bookreport;

import java.util.ArrayList;

public class BookReportItemData {

	private ArrayList<ArrayList<Word>> noun = new ArrayList<ArrayList<Word>>();
	private ArrayList<String> sentence = new ArrayList<String>();
	private ArrayList<ArrayList<String>> separateWord = new ArrayList<ArrayList<String>>();
	private String link;
	private String title;
	private String content;
	private int bookcode;
	
	public BookReportItemData() {
		
	}
	
	public BookReportItemData(int bookcode, String title, String link, String text) {
		this.bookcode=bookcode;
		this.title=title;
		this.link=link;
		this.content=text;
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
	public ArrayList<ArrayList<String>> getSeparateWord() {
		return separateWord;
	}
	public void setSeparateWord(ArrayList<ArrayList<String>> separateWord) {
		this.separateWord = separateWord;
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

	@Override
	public String toString() {
		return "BookReportItemData [link=" + link + ", title=" + title + ", content=" + content + ", bookcode="
				+ bookcode + "]";
	}
	

}
