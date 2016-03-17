package kumoh.d445.ucopyicatch.daumdataminig.bookreport;

import java.util.ArrayList;

public class BookData {
	private ArrayList<BookItemData> items = new ArrayList<BookItemData>();
	
	public String toString()
	{
		String outStr = "";
		
		for(int i=0;i < getBookList().size();i++)
		{
			outStr += "item(" + i + ")\n";
			outStr += "item(" + i + ") title:" + items.get(i).getTitle() + "\n";
			outStr += "item(" + i + ") link:" + items.get(i).getBookCode() + "\n";
		}
		return outStr;
	}
	
	public ArrayList<BookItemData> getBookList() {
		return items;
	}
}
