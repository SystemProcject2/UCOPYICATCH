package kumoh.d445.ucopyicatch.bookreport;

import java.util.ArrayList;

public class BookReportData {

	private ArrayList<BookReportItemData> items;

	public String toString()
	{
		String result = "";
		for(int i=0; i<items.size(); i++)
		{
			result += items.get(i);
			if( i != items.size()-1)
				result += " ";
		}
		return result;
	}

	public ArrayList<BookReportItemData> getItems() {
		return items;
	}

	public void setItems(ArrayList<BookReportItemData> items) {
		this.items = items;
	}
}
