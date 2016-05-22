package kumoh.d445.ucopyicatch.bookreport;

import java.util.ArrayList;
import java.util.HashMap;

import kumoh.d445.ucopyicatch.analysis.TFIDF;

public class BookReportData {

	private ArrayList<BookReportItemData> items = new ArrayList<BookReportItemData>();

	public void setDF(HashMap<String, Integer> dfList) {
		for(int i=0 ; i < items.size() ; i++) {
			for(int j=0 ; j < items.get(i).getContents().size() ; j++) {
				for(int k=0 ; k < items.get(i).getContents().get(j).size() ; k++) {
					String name = items.get(i).getContents().get(j).get(k).getName();
					int count = dfList.get(name);
					items.get(i).getContents().get(j).get(k).setDF(count);
					items.get(i).getContents().get(j).get(k).setTotal(items.size());
				}
			}
		}
	}
	
	public void setTfIdf() {
		for(int i=0 ; i < items.size() ; i++) {
			for(int j=0 ; j < items.get(i).getContents().size() ; j++) {
				for(int k=0 ; k < items.get(i).getContents().get(j).size() ; k++) {
					int tf = items.get(i).getContents().get(j).get(k).getTF();
					int df = items.get(i).getContents().get(j).get(k).getDF();
					int total = items.get(i).getContents().get(j).get(k).getTotal();
					items.get(i).getContents().get(j).get(k).setTfidf(TFIDF.tfIdf(tf, df, total));
				}
			}
		}
	}
	
	public void setFrequency() {
		for(int i=0 ; i < items.size() ; i++) {
			for(int j=0 ; j < items.get(i).getContents().size() ; j++) {
				for(int k=0 ; k < items.get(i).getContents().get(j).size() ; k++) {
					int tf = items.get(i).getContents().get(j).get(k).getTF();
					items.get(i).getContents().get(j).get(k).setTfidf(tf);
				}
			}
		}
	}
	
	public void setBoolean() {
		for(int i=0 ; i < items.size() ; i++) {
			for(int j=0 ; j < items.get(i).getContents().size() ; j++) {
				for(int k=0 ; k < items.get(i).getContents().get(j).size() ; k++) {
					items.get(i).getContents().get(j).get(k).setTfidf(1);
				}
			}
		}
	}
	
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
